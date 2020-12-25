package io.hk.webApp.vo;

public class ProductSharePriceVO {

    private String productId;

    private double newMoney;

    @Override
    public String toString() {
        return "ProductSharePriceVO{" +
                "productId='" + productId + '\'' +
                ", newMoney='" + newMoney + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(double newMoney) {
        this.newMoney = newMoney;
    }
}
