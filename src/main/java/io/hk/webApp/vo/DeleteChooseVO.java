package io.hk.webApp.vo;

import java.util.List;

public class DeleteChooseVO {

    private List<String> chooseIds;

    @Override
    public String toString() {
        return "DeleteChooseVO{" +
                "chooseIds=" + chooseIds +
                '}';
    }

    public List<String> getChooseIds() {
        return chooseIds;
    }

    public void setChooseIds(List<String> chooseIds) {
        this.chooseIds = chooseIds;
    }
}
