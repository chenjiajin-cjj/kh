package io.hk.webApp.Service.impl;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Tools.NumberUtils;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private ProductSet productSet;


    /**
     * 添加商品
     * @param product
     * @return
     */
    @Override
    public boolean addProduct(Product product) {
        if(StringUtils.isAnyEmpty(product.get("name").toString(),
                product.get("brandId").toString(),
                product.get("model").toString(),
                product.get("baseDetail").toString(),
                product.get("sellingPoint").toString(),
                product.get("detail").toString())){
            throw new OtherExcetion("请完善必填项");
        }

        if(NumberUtils.isDoubleMinus((Double)product.get("priceBegin1"),
                (Double)product.get("priceEnd1"),
                (Double)product.get("priceBegin2"),
                (Double)product.get("priceEnd2"),
                (Double)product.get("supplyPriceTax"),
                (Double)product.get("supplyPriceTaxNo"),
                (Double)product.get("retailPriceTax"),
                (Double)product.get("retailPriceTaxNo"))){
            throw new OtherExcetion("价格输入有误");
        }

        return productSet.addProduct(product);
    }

    /**
     * 修改商品
     * @param product
     * @return
     */
    @Override
    public boolean updateProduct(Product product) {
        if(StringUtils.isEmpty(product.getId())){
            throw new OtherExcetion("请选择要修改的商品");
        }
        return productSet.Update(product.getId(),product) > 0;
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    @Override
    public boolean deleteProductById(String id) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要修改的商品");
        }
        return productSet.Delete(id)>0;
    }

    /**
     * 列表查询
     * @param pagePars
     * @return
     */
    @Override
    public PageData<Product> search(TablePagePars pagePars) {
        return productSet.search(pagePars.Pars,pagePars.PageSize,pagePars.PageIndex,pagePars.Order);
    }

    /**
     * 根据 id 查询单个
     * @param id
     * @return
     */
    @Override
    public Product searchById(String id) {
        return productSet.Get(id);
    }

}
