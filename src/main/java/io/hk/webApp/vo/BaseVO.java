package io.hk.webApp.vo;

public class BaseVO {

    private String id;

    @Override
    public String toString() {
        return "BaseVO{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
