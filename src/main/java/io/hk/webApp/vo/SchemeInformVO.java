package io.hk.webApp.vo;

import java.util.List;

public class SchemeInformVO extends BaseVO{

    private List<SchemeInfornVO> list;

    @Override
    public String toString() {
        return "SchemeInformVO{" +
                "list=" + list +
                '}';
    }

    public List<SchemeInfornVO> getList() {
        return list;
    }

    public void setList(List<SchemeInfornVO> list) {
        this.list = list;
    }
}
