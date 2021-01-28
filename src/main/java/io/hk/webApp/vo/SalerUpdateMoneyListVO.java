package io.hk.webApp.vo;

import java.util.List;

public class SalerUpdateMoneyListVO {

    private List<SalerUpdateMoneyVO> list;

    @Override
    public String toString() {
        return "SalerUpdateMoneyListVO{" +
                "list=" + list +
                '}';
    }

    public List<SalerUpdateMoneyVO> getList() {
        return list;
    }

    public void setList(List<SalerUpdateMoneyVO> list) {
        this.list = list;
    }
}
