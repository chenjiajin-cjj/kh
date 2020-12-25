package io.hk.webApp.vo;

import java.util.Arrays;

public class SchemeInformVO extends BaseVO{

    private String [] productIds;

    @Override
    public String toString() {
        return "SchemeInformVO{" +
                "productIds=" + Arrays.toString(productIds) +
                '}';
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }
}
