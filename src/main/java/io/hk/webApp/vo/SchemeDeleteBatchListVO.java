package io.hk.webApp.vo;

import java.util.List;

public class SchemeDeleteBatchListVO {

    private String schemeId;

    private List<SchemeDeleteBatchVO> list;

    @Override
    public String toString() {
        return "SchemeDeleteBatchListVO{" +
                "schemeId='" + schemeId + '\'' +
                ", list=" + list +
                '}';
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public List<SchemeDeleteBatchVO> getList() {
        return list;
    }

    public void setList(List<SchemeDeleteBatchVO> list) {
        this.list = list;
    }
}
