package io.hk.webApp.dto;

import java.util.List;

public class FactorySchemeDTO {

    private String factorySchemeId;

    private String name;

    private String details;

    private long createTime;

    private long numbers;

    private List<ProductDTO> productList;

    @Override
    public String toString() {
        return "FactorySchemeDTO{" +
                "factorySchemeId='" + factorySchemeId + '\'' +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", createTime=" + createTime +
                ", numbers=" + numbers +
                ", productList=" + productList +
                '}';
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
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

    public long getNumbers() {
        return numbers;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }
}
