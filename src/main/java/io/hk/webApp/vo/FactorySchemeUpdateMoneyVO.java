package io.hk.webApp.vo;

import io.hk.webApp.dto.FactorySchemeProductDTO;

public class FactorySchemeUpdateMoneyVO extends FactorySchemeProductDTO {

    private String factorySchemeId;

    @Override
    public String toString() {
        return "FactorySchemeUpdateMoneyVO{" +
                "factorySchemeId='" + factorySchemeId + '\'' +
                '}';
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }
}
