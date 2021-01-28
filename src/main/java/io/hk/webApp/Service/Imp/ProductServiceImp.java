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

import javax.print.attribute.standard.MediaSize;
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
    @Autowired
    private SaleGoodsSet saleGoodsSet;
    @Autowired
    private ClassifySet classifySet;
    @Autowired
    private AuthSet authSet;

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
                product.getCategoryCode(),
                product.getClassifyId())) {
            throw new OtherExcetion("请完善必填项");
        }

        if (null == product.getSupplyPriceTax() ||
                null == product.getSupplyPriceTaxNo() ||
                null == product.getRetailPriceTax() ||
                null == product.getRetailPriceTaxNo() ||
                NumberUtils.isDoubleMinus(product.getSupplyPriceTaxNo(), product.getSupplyPriceTax(), product.getRetailPriceTax(), product.getRetailPriceTaxNo())) {
            throw new OtherExcetion("价格有误");
        }
        if (null != product.getPriceBegin1() && product.getPriceBegin1() > product.getRetailPriceTax()) {
            throw new OtherExcetion("预设价格1不含税不能比零售价不含税高");
        }
        if (null != product.getPriceEnd1() && product.getPriceEnd1() > product.getRetailPriceTaxNo()) {
            throw new OtherExcetion("预设价格1含税不能比零售价含税高");
        }

        if (null != product.getPriceBegin2() && product.getPriceBegin2() > product.getRetailPriceTax()) {
            throw new OtherExcetion("预设价格2不含税不能比零售价不含税高");
        }
        if (null != product.getPriceEnd2() && product.getPriceEnd2() > product.getRetailPriceTaxNo()) {
            throw new OtherExcetion("预设价格2含税不能比零售价含税高");
        }

        if (NumberUtils.isDoubleMinus(product.getTax())) {
            throw new OtherExcetion("请输入税率");
        }
        product.setIllegal(BaseType.Status.YES.getCode());
        product.setStatus(BaseType.Status.YES.getCode());
        product.setCreateTime(System.currentTimeMillis());
        product.setShield(BaseType.Status.YES.getCode());
        product.setTagHot(StringUtils.isEmpty(product.getTagHot()) ? "2" : product.getTagHot());
        product.setTagRec(StringUtils.isEmpty(product.getTagRec()) ? "2" : product.getTagRec());
        product.setTagNew(StringUtils.isEmpty(product.getTagNew()) ? "2" : product.getTagNew());
        return productSet.addProduct(product);
    }

    /**
     * 列表查询
     *
     * @param pagePars
     * @return
     */
    @Override
    public PageData<Product> search(TablePagePars pagePars, User factory) {
        pagePars.Pars.put("shield", BaseType.Status.YES.getCode());
        if(null != factory && StringUtils.isNotEmpty(factory.getId())){
            pagePars.Pars.put("factoryId", factory.getId());
        }
        PageData<Product> pageData = productSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order,factory);
        return pageData;
    }

    /**
     * 查询首页列表的分组
     *
     * @return
     */
    @Override
    public Object searchGroups(String factoryId) {
        List<Group> groupList = groupSet.Where("(factoryId=?)and(status=?)", factoryId, BaseType.Status.YES.getCode()).OrderBy("sort").ToList();
        List<Object> list = new ArrayList<>();
        groupList.forEach((a) -> {
            Map<String, Object> map = new HashMap<>();
            List<Category> categories = categorySet.Where("(fatherId=?)and(status=?)", a.getId(), BaseType.Status.YES.getCode()).OrderBy("sort").ToList();
            map.put("groupName", a.getName());
            map.put("categorys", categories);
            list.add(map);
        });
        return list;
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
    public boolean update(Product product, User user) {
        boolean result = false;
        try {
            result = product.updateProduct();
        } catch (Exception e) {
            throw new OtherExcetion("商品:[" + product.getName() + "]已存在");
        }
        List<SaleGoods> saleGoods = saleGoodsSet.Where("(factoryId=?)and(productId=?)", user.getId(), product.getId()).ToList();
        saleGoods.forEach((a) -> {
            boolean res = false;
            //以下做冗余经销商商品检索条件用
            if (StringUtils.isNotEmpty(product.getTagRec())) {
                a.setTagRec(product.getTagRec());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getTagNew())) {
                a.setTagNew(product.getTagNew());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getTagHot())) {
                a.setTagHot(product.getTagHot());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getProductGroup())) {
                a.setProductGroup(product.getProductGroup());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getBrandId())) {
                a.setBrandId(product.getBrandId());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getCategoryCode())) {
                a.setCategoryCode(product.getCategoryCode());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getName())) {
                a.setName(product.getName());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getClassifyId())) {
                a.setClassifyId(product.getClassifyId());
                res = true;
            }
            if (res) {
                a.updateById();
            }
        });
        return result;
    }

    /**
     * 屏蔽/取消屏蔽商品
     *
     * @param productId
     * @return
     */
    @Override
    public boolean shield(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new OtherExcetion("请选择要操作的商品");
        }
        Product product = productSet.Get(productId);
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
        product.setShield(BaseType.Status.YES.getCode().equals(product.getShield()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        return product.updateProduct();
    }

    /**
     * 根据id查询单个商品
     *
     * @param id
     * @return
     */
    @Override
    public Object getOne(String id, User user) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要查询的商品");
        }
        Product product = productSet.Get(id);
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
//        if (!BaseType.Status.YES.getCode().equals(product.getIllegal())) {
//            throw new OtherExcetion("该商品已被违规下架，请联系后台管理员");
//        }
        Brand brand = brandSet.Get(product.getBrandId());
        if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
            product.setBrandName(brand.getName());
        } else {
            product.setBrandName("无");
        }
        Category category = categorySet.Get(product.getCategoryCode());
        if (null != category && StringUtils.isNotEmpty(category.getName())) {
            product.setCategoryName(category.getName());
        } else {
            product.setCategoryName("无");
        }
        Classify classify = classifySet.Get(product.getClassifyId());
        if (null != classify && StringUtils.isNotEmpty(classify.getName())) {
            product.setClassifyName(classify.getName());
        } else {
            product.setClassifyName("无");
        }
        if (StringUtils.isNotEmpty(user.getCompanyName())) {
            product.setCname(user.getCompanyName());
        } else {
            product.setCname("无");
        }
        if (StringUtils.isNotEmpty(user.getName())) {
            product.setPname(user.getName());
        } else {
            product.setPname("无");
        }
        if (StringUtils.isNotEmpty(user.getTel())) {
            product.setTel(user.getTel());
        } else {
            product.setTel("无");
        }
        product.setAuthentication(BaseType.Status.YES.getCode().endsWith(user.getAuth()));
        return product;
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的商品");
        }
        //删除分享给经销商的商品
        List<SaleGoods> list = saleGoodsSet.Where("productId=?", id).ToList();
        list.forEach((a) -> {
            if (StringUtils.isNotEmpty(a.getId())) {
                saleGoodsSet.Delete(a.getId());
            }
        });
        return productSet.Delete(id) > 0;
    }

    /**
     * 查询后台商品
     * @param pagePars
     * @return
     */
    @Override
    public Object searchBackGroupProducts(TablePagePars pagePars) {
        return this.search(pagePars,null);
    }

}
