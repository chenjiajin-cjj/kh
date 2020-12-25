package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.dto.ShareProduceDTO;
import io.hk.webApp.vo.ProductShareVO;
import org.apache.commons.lang3.StringUtils;
import org.bson.BSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private ProductSet productSet;
    @Autowired
    private GroupSet groupSet;
    @Autowired
    private CategorySet categorySet;
    @Autowired
    private BrandSet brandSet;
    @Autowired
    private ShareProducesSet shareProducesSet;

    /**
     * 添加商品
     *
     * @param product
     * @return
     */
    @Override
    public boolean addProduct(Product product) {
        if (StringUtils.isAnyEmpty(product.get("name").toString(),
                product.getBrandId(),
                product.getModel(),
                product.getBaseDetail(),
                product.getSellingPoint(),
                product.getDetail(),
                product.getBrandId(),
                product.getCategoryCode())) {
            throw new OtherExcetion("请完善必填项");
        }

        if (NumberUtils.isDoubleMinus(product.getPriceBegin1(),
                product.getPriceEnd1(),
                product.getSupplyPriceTax(),
                product.getSupplyPriceTaxNo())) {
            throw new OtherExcetion("价格输入有误");
        }

        if (NumberUtils.isDoubleMinus(product.getTax())) {
            throw new OtherExcetion("请输入税率");
        }
        product.setStatus(BaseType.Status.YES.getCode());
        product.setCreateTime(System.currentTimeMillis());
        return productSet.addProduct(product);
    }

    /**
     * 列表查询
     *
     * @param pagePars
     * @return
     */
    @Override
    public PageData<Product> search(TablePagePars pagePars, String factoryId) {
        PageData<Product> pageData = productSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, factoryId);
        pageData.rows.forEach((a) -> {
            Category category = categorySet.Get(a.getCategoryCode());
            String categoryName = "无";
            if (null != category && StringUtils.isNotEmpty(category.getName())) {
                categoryName = category.getName();
            }
            Brand brand = brandSet.Get(a.getBrandId());
            String brandName = "无";
            if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
                brandName = brand.getName();
            }
            a.setCategoryName(categoryName);
            a.setBrandName(brandName);
        });
        return pageData;
    }

    /**
     * 查询首页列表的分组
     *
     * @return
     */
    @Override
    public List<Category> searchGroups(String factoryId) {
        List<Group> groupList = groupSet.Where("factoryId=?", factoryId).OrderBy("sort").ToList();
        if (groupList.size() == 0) {
            List<Category> list = new ArrayList<>();
            return list;
        } else if (groupList.size() == 1) {
            List<Category> list = categorySet.Where("(fatherId=?)and(status=?)", groupList.get(0).getId(), BaseType.Status.YES.getCode()).OrderBy("sort").ToList();
            return list;
        } else {
            List<Category> list = categorySet.Where("(fatherId=?)and(status=?)", groupList.get(1).getId(), BaseType.Status.YES.getCode()).OrderBy("sort").ToList();
            return list;
        }
    }

    /**
     * 列表查询商品页的品牌
     *
     * @param id
     * @return
     */
    @Override
    public List<Brand> searchBrand(String id) {
        return brandSet.Where("(userId=?)and(status=?)", id, BaseType.Status.YES.getCode()).OrderByDesc("time").ToList();
    }

    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    @Override
    public boolean update(Product product) {
        boolean result = false;
        try {
            result = product.updateProduct();
        } catch (Exception e) {
            throw new OtherExcetion("商品:[" + product.getName() + "]已存在");
        }
        return result;
    }

}
