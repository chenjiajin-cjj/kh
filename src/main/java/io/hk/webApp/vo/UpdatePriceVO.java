package io.hk.webApp.vo;

public class UpdatePriceVO extends ProductSharePriceVO {

    private String salerId;

    @Override
    public String toString() {
        return "UpdatePriceVO{" +
                "salerId='" + salerId + '\'' +
                '}';
    }

    public String getSalerId() {
        return salerId;
    }

    public void setSalerId(String salerId) {
        this.salerId = salerId;
    }
}
