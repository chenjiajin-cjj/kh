package io.hk.webApp.vo;

public class SalerUpdateMoneyVO {

    private String schemeId;

    private String factorySchemeId;

    private String productId;

    private double salerNewMoney;

    @Override
    public String toString() {
        return "SalerUpdateMoneyVO{" +
                "schemeId='" + schemeId + '\'' +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                ", productId='" + productId + '\'' +
                ", salerNewMoney=" + salerNewMoney +
                '}';
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getSalerNewMoney() {
        return salerNewMoney;
    }

    public void setSalerNewMoney(double salerNewMoney) {
        this.salerNewMoney = salerNewMoney;
    }
}
