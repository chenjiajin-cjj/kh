package io.hk.webApp.vo;

import io.hk.webApp.Domain.Choose;

import java.util.List;

public class AddToChooseVO {

    private List<Choose> list;

    @Override
    public String toString() {
        return "AddToChooseVO{" +
                "list=" + list +
                '}';
    }

    public List<Choose> getList() {
        return list;
    }

    public void setList(List<Choose> list) {
        this.list = list;
    }
}
