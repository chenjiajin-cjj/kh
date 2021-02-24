package io.hk.webApp.vo;

import io.hk.webApp.Domain.Messages;

import java.util.List;

public class SendSystemMessageVO {

    private List<String> userIds;

    private String type;

    private Messages messages;

    @Override
    public String toString() {
        return "SendSystemMessageVO{" +
                "userIds=" + userIds +
                ", type='" + type + '\'' +
                ", messages=" + messages +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
