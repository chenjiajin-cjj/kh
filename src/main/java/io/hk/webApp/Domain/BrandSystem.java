package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;

/**
 * 品牌管理
 */
public class BrandSystem extends ViewStore {

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

    /* 品牌名 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 品牌简介 details */
    @JsonProperty(value = "details")
    public String getDetails() {
        return (String) get("details");
    }

    @JsonProperty(value = "details")
    public void setDetails(String details) {
        set("details", details);
    }

    /* logo图片 logo */
    @JsonProperty(value = "logo")
    public String getLogo() {
        return (String) get("logo");
    }

    @JsonProperty(value = "logo")
    public void setLogo(String logo) {
        set("logo", logo);
    }


    /* 用户名 userName */
    @JsonProperty(value = "userName")
    public String getUserName() {
        return (String) get("userName");
    }

    @JsonProperty(value = "userName")
    public void setUserName(String userName) {
        set("userName", userName);
    }


}
