package io.hk.webApp.vo;

import java.util.List;

public class ProductShareVO {

    private List<ProductSharePriceVO> list;

    private String salerId;

    @Override
    public String toString() {
        return "ProductShareVO{" +
                "list=" + list +
                ", salerId='" + salerId + '\'' +
                '}';
    }

    public List<ProductSharePriceVO> getList() {
        return list;
    }

    public void setList(List<ProductSharePriceVO> list) {
        this.list = list;
    }

    public String getSalerId() {
        return salerId;
    }

    public void setSalerId(String salerId) {
        this.salerId = salerId;
    }
}
