package io.hk.webApp.dto;

import io.hk.webApp.Domain.Product;

public class ProductDTO {

    private Product product;

    private double SalerNewMoney;

    private double factoryNewMoney;

    private String factorySchemeId;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "product=" + product +
                ", SalerNewMoney=" + SalerNewMoney +
                ", factoryNewMoney=" + factoryNewMoney +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                '}';
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSalerNewMoney() {
        return SalerNewMoney;
    }

    public void setSalerNewMoney(double salerNewMoney) {
        SalerNewMoney = salerNewMoney;
    }

    public double getFactoryNewMoney() {
        return factoryNewMoney;
    }

    public void setFactoryNewMoney(double factoryNewMoney) {
        this.factoryNewMoney = factoryNewMoney;
    }
}
