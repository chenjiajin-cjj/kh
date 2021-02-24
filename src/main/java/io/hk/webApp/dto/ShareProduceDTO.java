package io.hk.webApp.dto;

import io.hk.webApp.Domain.Product;

public class ShareProduceDTO {
    /**
     * 商品id
     */
    private String productId;
    /**
     * 1是已接受  2是未接受
     */
    private String status;

    private Double newMoney;

    private Double newMoneyTax;

    private String imgs;

    private Product product;

    @Override
    public String toString() {
        return "ShareProduceDTO{" +
                "productId='" + productId + '\'' +
                ", status='" + status + '\'' +
                ", newMoney=" + newMoney +
                ", newMoneyTax=" + newMoneyTax +
                ", imgs='" + imgs + '\'' +
                ", product=" + product +
                '}';
    }

    public Double getNewMoneyTax() {
        return newMoneyTax;
    }

    public void setNewMoneyTax(Double newMoneyTax) {
        this.newMoneyTax = newMoneyTax;
    }

    public Double getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
