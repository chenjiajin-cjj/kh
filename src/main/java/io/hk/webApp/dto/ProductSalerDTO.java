package io.hk.webApp.dto;

import io.hk.webApp.Domain.Product;

public class ProductSalerDTO {

    private Product product;

    private double price;
    //1是已接受 2是未接受 3是对方还没有这个商品 4是已拒绝
    private String status;

    @Override
    public String toString() {
        return "ProductSalerDTO{" +
                "product=" + product +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
