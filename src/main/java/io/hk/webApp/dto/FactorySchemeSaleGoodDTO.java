package io.hk.webApp.dto;

import io.hk.webApp.Domain.SaleGoods;

public class FactorySchemeSaleGoodDTO {

    private String saleGoodId;

    private double salerNewMoneyTax;

    private double salerNewMoneyTaxNo;

    private SaleGoods saleGoods;

    @Override
    public String toString() {
        return "FactorySchemeSaleGoodDTO{" +
                "saleGoodId='" + saleGoodId + '\'' +
                ", salerNewMoneyTax=" + salerNewMoneyTax +
                ", salerNewMoneyTaxNo=" + salerNewMoneyTaxNo +
                ", saleGoods=" + saleGoods +
                '}';
    }

    public String getSaleGoodId() {
        return saleGoodId;
    }

    public void setSaleGoodId(String saleGoodId) {
        this.saleGoodId = saleGoodId;
    }

    public double getSalerNewMoneyTax() {
        return salerNewMoneyTax;
    }

    public void setSalerNewMoneyTax(double salerNewMoneyTax) {
        this.salerNewMoneyTax = salerNewMoneyTax;
    }

    public double getSalerNewMoneyTaxNo() {
        return salerNewMoneyTaxNo;
    }

    public void setSalerNewMoneyTaxNo(double salerNewMoneyTaxNo) {
        this.salerNewMoneyTaxNo = salerNewMoneyTaxNo;
    }

    public SaleGoods getSaleGoods() {
        return saleGoods;
    }

    public void setSaleGoods(SaleGoods saleGoods) {
        this.saleGoods = saleGoods;
    }
}
