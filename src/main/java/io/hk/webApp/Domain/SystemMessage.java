package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;

public class SystemMessage extends ViewStore {
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

    /* 标题内容  titleMessage */
    @JsonProperty(value = "titleMessage")
    public String getTitleMessage() {
        return (String) get("titleMessage");
    }

    @JsonProperty(value = "titleMessage")
    public void setTitleMessage(String titleMessage) {
        set("titleMessage", titleMessage);
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

    /* 发送人id adminId */
    @JsonProperty(value = "adminId")
    public String getAdminId() {
        return (String) get("adminId");
    }

    @JsonProperty(value = "adminId")
    public void setAdminId(String adminId) {
        set("adminId", adminId);
    }

    /* 发送人昵称 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 发布时间 ctime */
    @JsonProperty(value = "ctime")
    public Long getCtime() {
        return (Long) get("ctime");
    }

    @JsonProperty(value = "ctime")
    public void setCtime(Long ctime) {
        set("ctime", ctime);
    }

    /* 接收对象 count */
    @JsonProperty(value = "count")
    public Integer getCount() {
        return (Integer) get("count");
    }

    @JsonProperty(value = "count")
    public void setCount(Integer count) {
        set("count", count);
    }

}
