package io.hk.webApp.vo;

import java.util.List;

public class TransferVO {

    private List<String> salerIds;

    private String sonId;

    @Override
    public String toString() {
        return "TransferVO{" +
                "salerIds=" + salerIds +
                ", sonId='" + sonId + '\'' +
                '}';
    }

    public List<String> getSalerIds() {
        return salerIds;
    }

    public void setSalerIds(List<String> salerIds) {
        this.salerIds = salerIds;
    }

    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }
}
