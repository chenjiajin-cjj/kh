package io.hk.webApp.vo;

import java.util.Arrays;

public class SchemeAddProductVO {

    private String [] saleGoodIds;

    private String schemeId;

    @Override
    public String toString() {
        return "SchemeAddProductVO{" +
                "saleGoodIds=" + Arrays.toString(saleGoodIds) +
                ", schemeId='" + schemeId + '\'' +
                '}';
    }

    public String[] getSaleGoodIds() {
        return saleGoodIds;
    }

    public void setSaleGoodIds(String[] saleGoodIds) {
        this.saleGoodIds = saleGoodIds;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
