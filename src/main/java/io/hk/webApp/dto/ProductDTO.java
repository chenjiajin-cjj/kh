package io.hk.webApp.dto;

import io.hk.webApp.Domain.Product;

public class ProductDTO {

    private Product product;

    private double salerNewMoneyTax;

    private double salerNewMoneyTaxNo;

    private double factoryNewMoneyTax;

    private double factoryNewMoneyTaxNo;

    private String factorySchemeId;
    // 1是供应商 2是经销商
    private String type;

    private String cname;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "product=" + product +
                ", salerNewMoneyTax=" + salerNewMoneyTax +
                ", salerNewMoneyTaxNo=" + salerNewMoneyTaxNo +
                ", factoryNewMoneyTax=" + factoryNewMoneyTax +
                ", factoryNewMoneyTaxNo=" + factoryNewMoneyTaxNo +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                ", type='" + type + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public double getFactoryNewMoneyTax() {
        return factoryNewMoneyTax;
    }

    public void setFactoryNewMoneyTax(double factoryNewMoneyTax) {
        this.factoryNewMoneyTax = factoryNewMoneyTax;
    }

    public double getFactoryNewMoneyTaxNo() {
        return factoryNewMoneyTaxNo;
    }

    public void setFactoryNewMoneyTaxNo(double factoryNewMoneyTaxNo) {
        this.factoryNewMoneyTaxNo = factoryNewMoneyTaxNo;
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
