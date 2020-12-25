package io.hk.webApp.dto;

import java.util.List;

public class SalerFactorySchemeDTO {

    private String factoryName;

    private String salerName;

    private String schemeName;

    private String factorySchemeId;

    private List<String> imgList;

    @Override
    public String toString() {
        return "SalerFactorySchemeDTO{" +
                "factoryName='" + factoryName + '\'' +
                ", salerName='" + salerName + '\'' +
                ", schemeName='" + schemeName + '\'' +
                ", factorySchemeId='" + factorySchemeId + '\'' +
                ", imgList=" + imgList +
                '}';
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getFactorySchemeId() {
        return factorySchemeId;
    }

    public void setFactorySchemeId(String factorySchemeId) {
        this.factorySchemeId = factorySchemeId;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
