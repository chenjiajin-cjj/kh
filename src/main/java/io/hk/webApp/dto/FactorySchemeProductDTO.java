package io.hk.webApp.dto;

import io.hk.webApp.Domain.Product;

public class FactorySchemeProductDTO {

    private String productIds;

    private Product product;

    private double factoryNewMoney;

    private double salerNewMoney;

    @Override
    public String toString() {
        return "FactorySchemeProductDTO{" +
                "productIds='" + productIds + '\'' +
                ", product=" + product +
                ", factoryNewMoney=" + factoryNewMoney +
                ", salerNewMoney=" + salerNewMoney +
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

    public double getFactoryNewMoney() {
        return factoryNewMoney;
    }

    public void setFactoryNewMoney(double factoryNewMoney) {
        this.factoryNewMoney = factoryNewMoney;
    }

    public double getSalerNewMoney() {
        return salerNewMoney;
    }

    public void setSalerNewMoney(double salerNewMoney) {
        this.salerNewMoney = salerNewMoney;
    }
}
