package io.hk.webApp.vo;

public class ProductSharePriceVO {

    private String productId;

    private double newMoney;

    private double newMoneyTax;

    @Override
    public String toString() {
        return "ProductSharePriceVO{" +
                "productId='" + productId + '\'' +
                ", newMoney=" + newMoney +
                ", newMoneyTax=" + newMoneyTax +
                '}';
    }

    public double getNewMoneyTax() {
        return newMoneyTax;
    }

    public void setNewMoneyTax(double newMoneyTax) {
        this.newMoneyTax = newMoneyTax;
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
