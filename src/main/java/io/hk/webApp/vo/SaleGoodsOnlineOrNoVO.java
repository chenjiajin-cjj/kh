package io.hk.webApp.vo;

public class SaleGoodsOnlineOrNoVO {

    private String id;

    @Override
    public String toString() {
        return "SaleGoodsOnlineOrNoVO{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
