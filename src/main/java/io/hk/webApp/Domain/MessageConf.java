package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.MessageConfSet;

import java.util.ArrayList;
import java.util.List;

public class MessageConf extends ViewStore {

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

    //触发条件 name
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    //消息类型
    @JsonProperty(value = "type")
    public String getType() {
        return (String) get("type");
    }

    @JsonProperty(value = "type")
    public void setType(String type) {
        set("type", type);
    }

    //站内消息是否开启 1是2否
    @JsonProperty(value = "instation")
    public String getInstation() {
        return (String) get("instation");
    }

    @JsonProperty(value = "instation")
    public void setInstation(String instation) {
        set("instation", instation);
    }

    //站内消息标题
    @JsonProperty(value = "instationTitle")
    public String getInstationTitle() {
        return (String) get("instationTitle");
    }

    @JsonProperty(value = "instationTitle")
    public void setInstationTitle(String instationTitle) {
        set("instationTitle", instationTitle);
    }

    //站内消息内容
    @JsonProperty(value = "instationContext")
    public String getInstationContext() {
        return (String) get("instationContext");
    }

    @JsonProperty(value = "instationContext")
    public void setInstationContext(String instationContext) {
        set("instationContext", instationContext);
    }

    //站内消息内容必须包含的标签
    @JsonProperty(value = "instationContextTag")
    public List<String> getInstationContextTag() {
        return (List<String>) get("instationContextTag");
    }

    @JsonProperty(value = "instationContextTag")
    public void setInstationContextTag(List<String> instationContextTag) {
        set("instationContextTag", instationContextTag);
    }

    //---------

    //小程序消息是否开启 1是2否
    @JsonProperty(value = "small")
    public String getSmall() {
        return (String) get("small");
    }

    @JsonProperty(value = "small")
    public void setSmall(String small) {
        set("small", small);
    }

    //小程序消息标题
    @JsonProperty(value = "smallTitle")
    public String getSmallTitle() {
        return (String) get("smallTitle");
    }

    @JsonProperty(value = "smallTitle")
    public void setSmallTitle(String smallTitle) {
        set("smallTitle", smallTitle);
    }

    //小程序消息内容
    @JsonProperty(value = "smallContext")
    public String getSmallContext() {
        return (String) get("smallContext");
    }

    @JsonProperty(value = "smallContext")
    public void setSmallContext(String smallContext) {
        set("smallContext", smallContext);
    }

    //小程序消息内容必须包含的标签
    @JsonProperty(value = "smallContextTag")
    public List<String> getSmallContextTag() {
        return (List<String>) get("smallContextTag");
    }

    @JsonProperty(value = "smallContextTag")
    public void setSmallContextTag(List<String> smallContextTag) {
        set("smallContextTag", smallContextTag);
    }

    //消息唯一标签
    @JsonProperty(value = "code")
    public String getCode() {
        return (String) get("code");
    }

    @JsonProperty(value = "code")
    public void setCode(String code) {
        set("code", code);
    }


}
