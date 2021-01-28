package io.hk.webApp.dto;

import io.hk.webApp.Domain.Product;

public class FactorySchemeProductDTO {

    private String productIds;

    private Product product;

    private double factoryNewMoneyTax;

    private double factoryNewMoneyTaxNo;

    private double salerNewMoneyTax;

    private double salerNewMoneyTaxNo;

    @Override
    public String toString() {
        return "FactorySchemeProductDTO{" +
                "productIds='" + productIds + '\'' +
                ", product=" + product +
                ", factoryNewMoneyTax=" + factoryNewMoneyTax +
                ", factoryNewMoneyTaxNo=" + factoryNewMoneyTaxNo +
                ", salerNewMoneyTax=" + salerNewMoneyTax +
                ", salerNewMoneyTaxNo=" + salerNewMoneyTaxNo +
                '}';
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
