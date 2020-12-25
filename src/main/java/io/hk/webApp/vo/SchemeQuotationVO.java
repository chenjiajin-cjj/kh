package io.hk.webApp.vo;

public class SchemeQuotationVO {

    private String factoryId;

    private String schemeId;

    @Override
    public String toString() {
        return "SchemeQuotationVO{" +
                "factoryId='" + factoryId + '\'' +
                ", schemeId='" + schemeId + '\'' +
                '}';
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
