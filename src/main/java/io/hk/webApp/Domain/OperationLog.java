package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;


/**
 * 操作日志
 */
public class OperationLog extends ViewStore {


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

    /* 操作人账号 account */
    @JsonProperty(value = "account")
    public String getAccount() {
        return (String) get("account");
    }

    @JsonProperty(value = "account")
    public void setAccount(String account) {
        set("account", account);
    }

    /* 操作人昵称 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 创建时间 ctime */
    @JsonProperty(value = "ctime")
    public Long getCtime() {
        return (Long) get("ctime");
    }

    @JsonProperty(value = "ctime")
    public void setCtime(Long ctime) {
        set("ctime", ctime);
    }

    /* ip ip */
    @JsonProperty(value = "ip")
    public String getIp() {
        return (String) get("ip");
    }

    @JsonProperty(value = "ip")
    public void setIp(String ip) {
        set("ip", ip);
    }

    /* 地区 addr */
    @JsonProperty(value = "addr")
    public String getAddr() {
        return (String) get("addr");
    }

    @JsonProperty(value = "addr")
    public void setAddr(String addr) {
        set("addr", addr);
    }

    /* 操作内容 addr */
    @JsonProperty(value = "content")
    public String getContent() {
        return (String) get("content");
    }

    @JsonProperty(value = "content")
    public void setContent(String content) {
        set("content", content);
    }


}
