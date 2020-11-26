package io.hk.webApp.vo;

import io.hk.webApp.Domain.Group;

import java.util.List;

public class GroupSortVO {


    private List<Group> list;

    @Override
    public String toString() {
        return "GroupSortVO{" +
                "list=" + list +
                '}';
    }

    public List<Group> getList() {
        return list;
    }

    public void setList(List<Group> list) {
        this.list = list;
    }
}

