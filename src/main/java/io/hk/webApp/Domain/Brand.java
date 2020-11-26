package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.BrandSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

/**
 * 品牌管理
 */
public class Brand extends ViewStore {

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

    /* 用户id userId */
    @JsonProperty(value = "userId")
    public String getUserId() {
        return (String) get("userId");
    }

    @JsonProperty(value = "userId")
    public void setUserId(String userId) {
        set("userId", userId);
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

    /* 商标及授权证书 icon */
    @JsonProperty(value = "icon")
    public String getIcon() {
        return (String) get("icon");
    }

    @JsonProperty(value = "icon")
    public void setIcon(String icon) {
        set("icon", icon);
    }

    /* 有效期 time */
    @JsonProperty(value = "time")
    public Long getTime() {
        return (Long) get("time");
    }

    @JsonProperty(value = "time")
    public void setTime(Long time) {
        set("time", time);
    }

    /* ppt模板 ppt */
    @JsonProperty(value = "ppt")
    public String getPpt() {
        return (String) get("ppt");
    }

    @JsonProperty(value = "ppt")
    public void setPpt(String ppt) {
        set("ppt", ppt);
    }

    /* 状态 1是通过 2是待审核 3是驳回 status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 驳回原因 cause */
    @JsonProperty(value = "cause")
    public String getCause() {
        return (String) get("cause");
    }

    @JsonProperty(value = "cause")
    public void setCause(String cause) {
        set("cause", cause);
    }


    /**
     * 修改品牌
     */
    public boolean updateById(Brand brand){
        if(StringUtils.isEmpty(brand.getId())){
            throw new OtherExcetion("请选择要修改的品牌");
        }
        BrandSet brandSet = Holder.getBean(BrandSet.class);
        return brandSet.Update(brand.getId(),brand) > 0;
    }
    /**
     * 删除品牌
     */
    public boolean deleteById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要删除的品牌");
        }
        BrandSet brandSet = Holder.getBean(BrandSet.class);
        return brandSet.Delete(id) > 0;
    }
    /**
     * 根据id查询单个品牌
     */
    public Brand getById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要查询的品牌");
        }
        BrandSet brandSet = Holder.getBean(BrandSet.class);
        return brandSet.Get(id);
    }

}
