package io.hk.webApp.vo;

import java.util.List;

public class ExportUsersVO {

    private List<String> ids;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExportUsersVO{" +
                "ids=" + ids +
                ", type='" + type + '\'' +
                '}';
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
