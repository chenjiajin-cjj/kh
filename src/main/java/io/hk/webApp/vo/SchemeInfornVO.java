package io.hk.webApp.vo;

public class SchemeInfornVO {

    private String productId;

    private String factorySchemeId;

    @Override
    public String toString() {
        return "SchemeInfornVO{" +
                "productId='" + productId + '\'' +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }
}
