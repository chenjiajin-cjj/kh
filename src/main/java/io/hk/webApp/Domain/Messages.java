package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;

import java.util.List;

public class Messages extends ViewStore {
    // _id
    @JsonProperty(value = "_id")
    public String getId() {
        if (get("_id") == null) {
            return null;
        }
        return get("_id").toString();
    }

    @JsonProperty(value = "_id")
    public void setId(String Id) {
        set("_id", Id);
    }


    /* 标题 1是审核信息 2是合作信息 3是提报信息 4是系统信息 title */
    @JsonProperty(value = "title")
    public String getTitle() {
        return (String) get("title");
    }

    @JsonProperty(value = "title")
    public void setTitle(String title) {
        set("title", title);
    }

    /* 内容 content */
    @JsonProperty(value = "content")
    public String getContent() {
        return (String) get("content");
    }

    @JsonProperty(value = "content")
    public void setContent(String content) {
        set("content", content);
    }


    /* 来源于哪个用户 source */
    @JsonProperty(value = "source")
    public String getSource() {
        return (String) get("source");
    }

    @JsonProperty(value = "source")
    public void setSource(String source) {
        set("source", source);
    }

    /* 时间 time */
    @JsonProperty(value = "time")
    public Long getTime() {
        return (Long) get("time");
    }

    @JsonProperty(value = "time")
    public void setTime(Long time) {
        set("time", time);
    }

    /* 发送人id userId */
    @JsonProperty(value = "userId")
    public String getUserId() {
        return (String) get("userId");
    }

    @JsonProperty(value = "userId")
    public void setUserId(String userId) {
        set("userId", userId);
    }

    /* 接收人id receiveId */
    @JsonProperty(value = "receiveId")
    public String getReceiveId() {
        return (String) get("receiveId");
    }

    @JsonProperty(value = "receiveId")
    public void setReceiveId(String receiveId) {
        set("receiveId", receiveId);
    }

    /* 携带参数 data */
    @JsonProperty(value = "data")
    public Object getData() {
        return (Object) get("data");
    }

    @JsonProperty(value = "data")
    public void setData(Object data) {
        set("data", data);
    }

    /* 状态 1是已读  2是未读  status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 缩略图  img */
    @JsonProperty(value = "img")
    public List<String> getImg() {
        return (List<String>) get("img");
    }

    @JsonProperty(value = "img")
    public void setImg(List<String> img) {
        set("img", img);
    }

    /* 标题内容  titleMessage */
    @JsonProperty(value = "titleMessage")
    public String getTitleMessage() {
        return (String) get("titleMessage");
    }

    @JsonProperty(value = "titleMessage")
    public void setTitleMessage(String titleMessage) {
        set("titleMessage", titleMessage);
    }

}
