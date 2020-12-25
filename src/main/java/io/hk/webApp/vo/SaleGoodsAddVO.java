package io.hk.webApp.vo;

import java.util.Arrays;

public class SaleGoodsAddVO {

    private String [] productIds;

    private String factoryId;


    @Override
    public String toString() {
        return "SaleGoodsAddVO{" +
                "productIds=" + Arrays.toString(productIds) +
                ", factoryId='" + factoryId + '\'' +
                '}';
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }
}
