package io.hk.webApp.vo;

import io.hk.webApp.Domain.Category;

import java.util.List;

public class CategorySortVO {

    private List<Category> list;

    @Override
    public String toString() {
        return "CategorySortVO{" +
                "list=" + list +
                '}';
    }

    public List<Category> getList() {
        return list;
    }

    public void setList(List<Category> list) {
        this.list = list;
    }
}
