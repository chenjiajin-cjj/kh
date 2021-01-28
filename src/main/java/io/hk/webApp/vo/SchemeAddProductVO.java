package io.hk.webApp.vo;

public class SchemeAddProductVO {

    private String schemeId;

    @Override
    public String toString() {
        return "SchemeAddProductVO{" +
                "schemeId='" + schemeId + '\'' +
                '}';
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
