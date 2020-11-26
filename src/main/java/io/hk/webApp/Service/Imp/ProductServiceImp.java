package io.hk.webApp.Service.Imp;

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
                product.getBrandId(),
                product.getModel(),
                product.getBaseDetail(),
                product.getSellingPoint(),
                product.getDetail())){
            throw new OtherExcetion("请完善必填项");
        }

        if(NumberUtils.isDoubleMinus(product.getPriceBegin1(),
                product.getPriceEnd1(),
                product.getPriceBegin2(),
                product.getPriceEnd2(),
                product.getSupplyPriceTax(),
                product.getSupplyPriceTaxNo(),
                product.getRetailPriceTax(),
                product.getRetailPriceTaxNo())){
            throw new OtherExcetion("价格输入有误");
        }
        product.setCreateTime(System.currentTimeMillis());
        return productSet.addProduct(product);
    }

    /**
     * 列表查询
     * @param pagePars
     * @return
     */
    @Override
    public PageData<Product> search(TablePagePars pagePars,String factoryId) {
        return productSet.search(pagePars.Pars,pagePars.PageSize,pagePars.PageIndex,pagePars.Order,factoryId);
    }

}
