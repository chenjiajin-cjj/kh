package io.hk.webApp.dto;

import java.util.List;

public class FactorySchemeDTO {

    private String schemeId;

    private String factorySchemeId;

    private String name;

    private String details;

    private long createTime;

    private long validity;

    private long numbers;

    private String status;

    private List<ProductDTO> productList;

    private int total;

    @Override
    public String toString() {
        return "FactorySchemeDTO{" +
                "schemeId='" + schemeId + '\'' +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", createTime=" + createTime +
                ", validity=" + validity +
                ", numbers=" + numbers +
                ", status='" + status + '\'' +
                ", productList=" + productList +
                ", total=" + total +
                '}';
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getValidity() {
        return validity;
    }

    public void setValidity(long validity) {
        this.validity = validity;
    }

    public long getNumbers() {
        return numbers;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }
}
