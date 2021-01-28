package io.hk.webApp.vo;

public class SchemeDeleteBatchVO {


    private String factorySchemeId;

    private String productId;

    private String type;
    @Override
    public String toString() {
        return "SchemeDeleteBatchVO{" +
                "factorySchemeId='" + factorySchemeId + '\'' +
                ", productId='" + productId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
