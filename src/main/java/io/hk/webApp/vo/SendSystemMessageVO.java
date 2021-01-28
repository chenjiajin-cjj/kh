package io.hk.webApp.vo;

import io.hk.webApp.Domain.Messages;

import java.util.List;

public class SendSystemMessageVO {

    private List<String> userIds;

    private Messages messages;

    @Override
    public String toString() {
        return "SendSystemMessageVO{" +
                "userIds=" + userIds +
                ", messages=" + messages +
                '}';
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
