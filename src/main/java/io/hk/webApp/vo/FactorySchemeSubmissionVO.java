package io.hk.webApp.vo;

import io.hk.webApp.dto.FactorySchemeProductDTO;

import java.util.List;

public class FactorySchemeSubmissionVO {

    private List<FactorySchemeProductDTO> productIds;

    private String factorySchemeId;

    @Override
    public String toString() {
        return "FactorySchemeSubmissionVO{" +
                "productIds=" + productIds +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                '}';
    }

    public List<FactorySchemeProductDTO> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<FactorySchemeProductDTO> productIds) {
        this.productIds = productIds;
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }
}
