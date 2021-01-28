package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;

import java.util.Date;

public class LoginLog extends ViewStore {


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

    /* 联系人 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 手机账号 phone */
    @JsonProperty(value = "phone")
    public String getPhone() {
        return (String) get("phone");
    }

    @JsonProperty(value = "phone")
    public void setPhone(String phone) {
        set("phone", phone);
    }

    /* 用户身份 1是供应商  2是经销商  type */
    @JsonProperty(value = "type")
    public String getType() {
        return (String) get("type");
    }

    @JsonProperty(value = "type")
    public void setType(String type) {
        set("type", type);
    }

    /* 创建时间 ctime */
    @JsonProperty(value = "ctime")
    public Long getCTime() {
        return (Long) get("ctime");
    }

    @JsonProperty(value = "ctime")
    public void setCTime(Long ctime) {
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

    /* 登录设备 facility */
    @JsonProperty(value = "facility")
    public String getFacility() {
        return (String) get("facility");
    }

    @JsonProperty(value = "facility")
    public void setFacility(String facility) {
        set("facility", facility);
    }

    /* 登录身份 1是会员 2是管理员 status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

}
