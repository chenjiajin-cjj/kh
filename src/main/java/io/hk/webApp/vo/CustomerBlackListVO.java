package io.hk.webApp.vo;

public class CustomerBlackListVO {

    private String blackId;

    @Override
    public String toString() {
        return "CustomerAddBlackListVO{" +
                "blackId='" + blackId + '\'' +
                '}';
    }

    public String getBlackId() {
        return blackId;
    }

    public void setBlackId(String blackId) {
        this.blackId = blackId;
    }
}
