package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.FactorySchemeSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.List;

public class FactoryScheme extends ViewStore {

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

    /* 供应商的id factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
    }

    /* 经销商的id salerId */
    @JsonProperty(value = "salerId")
    public String getSalerId() {
        return (String) get("salerId");
    }

    @JsonProperty(value = "salerId")
    public void setSalerId(String salerId) {
        set("salerId", salerId);
    }

    /* 方案的id schemeId */
    @JsonProperty(value = "schemeId")
    public String getSchemeId() {
        return (String) get("schemeId");
    }

    @JsonProperty(value = "schemeId")
    public void setSchemeId(String schemeId) {
        set("schemeId", schemeId);
    }

    /* 供应商的名 salerName */
    @JsonProperty(value = "salerName")
    public String getSalerName() {
        return (String) get("salerName");
    }

    @JsonProperty(value = "salerName")
    public void setSalerName(String salerName) {
        set("salerName", salerName);
    }

    /* 发货地 addr */
    @JsonProperty(value = "addr")
    public String getAddr() {
        return (String) get("addr");
    }

    @JsonProperty(value = "addr")
    public void setAddr(String addr) {
        set("addr", addr);
    }

    /* 商品数量 number */
    @JsonProperty(value = "number")
    public String getNumber() {
        return (String) get("number");
    }

    @JsonProperty(value = "number")
    public void setNumber(String number) {
        set("number", number);
    }

    /* 有效期 validity */
    @JsonProperty(value = "validity")
    public Long getValidity() {
        return (Long) get("validity");
    }

    @JsonProperty(value = "validity")
    public void setValidity(Long validity) {
        set("validity", validity);
    }

    /* 提报的商品们的id factoryProductIds */
    @JsonProperty(value = "factoryProductIds")
    public List<Document> getFactoryProductIds() {
        return (List<Document>) get("factoryProductIds");
    }

    @JsonProperty(value = "factoryProductIds")
    public void setFactoryProductIds(List<Document> factoryProductIds) {
        set("factoryProductIds", factoryProductIds);
    }

    /* 方案的创建时间  主要用来提报后重命名来排序 updateTime */
    @JsonProperty(value = "updateTime")
    public Long getUpdateTime() {
        return (Long) get("updateTime");
    }

    @JsonProperty(value = "updateTime")
    public void setUpdateTime(Long updateTime) {
        set("updateTime", updateTime);
    }

    /* 方案是否在经销商显示 1是2否  status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /*   name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }


    /* 方案是否已经被通知 1是2否  scheme */
    @JsonProperty(value = "scheme")
    public String getScheme() {
        return (String) get("scheme");
    }

    @JsonProperty(value = "scheme")
    public void setScheme(String scheme) {
        set("scheme", scheme);
    }

    /**
     * 修改供应商方案
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的商品");
        }
        FactorySchemeSet factorySchemeSet = Holder.getBean(FactorySchemeSet.class);
        return factorySchemeSet.Update(this.getId(),this) > 0;
    }

    /**
     * 删除供应商方案单个商品
     */
    public boolean deleteById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的商品");
        }
        FactorySchemeSet factorySchemeSet = Holder.getBean(FactorySchemeSet.class);
        return factorySchemeSet.Delete(this.getId()) > 0;
    }

    /**
     * 查询单个方案
     */
    public FactoryScheme getById(String id){
        FactorySchemeSet factorySchemeSet = Holder.getBean(FactorySchemeSet.class);
        return factorySchemeSet.Get(id);
    }

}
