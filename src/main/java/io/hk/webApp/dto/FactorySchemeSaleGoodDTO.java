package io.hk.webApp.dto;

import io.hk.webApp.Domain.SaleGoods;

public class FactorySchemeSaleGoodDTO {

    private String saleGoodId;

    private double SalerNewMoney;

    private SaleGoods saleGoods;

    @Override
    public String toString() {
        return "FactorySchemeSaleGoodDTO{" +
                "saleGoodId='" + saleGoodId + '\'' +
                ", SalerNewMoney=" + SalerNewMoney +
                ", saleGoods=" + saleGoods +
                '}';
    }

    public String getSaleGoodId() {
        return saleGoodId;
    }

    public void setSaleGoodId(String saleGoodId) {
        this.saleGoodId = saleGoodId;
    }

    public double getSalerNewMoney() {
        return SalerNewMoney;
    }

    public void setSalerNewMoney(double salerNewMoney) {
        SalerNewMoney = salerNewMoney;
    }

    public SaleGoods getSaleGoods() {
        return saleGoods;
    }

    public void setSaleGoods(SaleGoods saleGoods) {
        this.saleGoods = saleGoods;
    }
}
