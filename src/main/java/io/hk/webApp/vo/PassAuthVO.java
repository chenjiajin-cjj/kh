package io.hk.webApp.vo;

import io.hk.webApp.Domain.AuthApply;

import java.util.List;

public class PassAuthVO {

    private List<AuthApply> list;

    @Override
    public String toString() {
        return "PassAuthVO{" +
                "list=" + list +
                '}';
    }

    public List<AuthApply> getList() {
        return list;
    }

    public void setList(List<AuthApply> list) {
        this.list = list;
    }
}
