package io.hk.webApp.vo;

import io.hk.webApp.Domain.Product;

public class SaleGoodsUpdateProductVO {

    private String saleGoodsId;

    private Product product;

    @Override
    public String toString() {
        return "SaleGoodsUpdateProductVO{" +
                "saleGoodsId='" + saleGoodsId + '\'' +
                ", product=" + product +
                '}';
    }

    public String getSaleGoodsId() {
        return saleGoodsId;
    }

    public void setSaleGoodsId(String saleGoodsId) {
        this.saleGoodsId = saleGoodsId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
