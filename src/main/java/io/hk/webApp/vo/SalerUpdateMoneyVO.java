package io.hk.webApp.vo;

public class SalerUpdateMoneyVO {

    private String schemeId;

    private String factorySchemeId;

    private String productId;

    private double salerNewMoneyTax;

    private double salerNewMoneyTaxNo;

    @Override
    public String toString() {
        return "SalerUpdateMoneyVO{" +
                "schemeId='" + schemeId + '\'' +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                ", productId='" + productId + '\'' +
                ", salerNewMoneyTax=" + salerNewMoneyTax +
                ", salerNewMoneyTaxNo=" + salerNewMoneyTaxNo +
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

    public double getSalerNewMoneyTax() {
        return salerNewMoneyTax;
    }

    public void setSalerNewMoneyTax(double salerNewMoneyTax) {
        this.salerNewMoneyTax = salerNewMoneyTax;
    }

    public double getSalerNewMoneyTaxNo() {
        return salerNewMoneyTaxNo;
    }

    public void setSalerNewMoneyTaxNo(double salerNewMoneyTaxNo) {
        this.salerNewMoneyTaxNo = salerNewMoneyTaxNo;
    }
}
