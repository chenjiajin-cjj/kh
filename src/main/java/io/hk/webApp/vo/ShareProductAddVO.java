package io.hk.webApp.vo;

import java.util.List;

public class ShareProductAddVO {

    private String shareProductId;

    private List<ProductSharePriceVO> list;

    @Override
    public String toString() {
        return "ShareProductAddVO{" +
                "shareProductId='" + shareProductId + '\'' +
                ", list=" + list +
                '}';
    }

    public String getShareProductId() {
        return shareProductId;
    }

    public void setShareProductId(String shareProductId) {
        this.shareProductId = shareProductId;
    }

    public List<ProductSharePriceVO> getList() {
        return list;
    }

    public void setList(List<ProductSharePriceVO> list) {
        this.list = list;
    }
}
