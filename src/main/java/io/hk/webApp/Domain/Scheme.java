package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.DataAccess.SchemeSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.Date;
import java.util.List;

/**
 * 经销商的方案
 */
public class Scheme extends ViewStore {

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

    /* 经销商的id salerId */
    @JsonProperty(value = "salerId")
    public String getSalerId() {
        return (String) get("salerId");
    }

    @JsonProperty(value = "salerId")
    public void setSalerId(String salerId) {
        set("salerId", salerId);
    }

    /* 方案名 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 有效期 validity */
    @JsonProperty(value = "validity")
    public Long getValidity() {
        return (Long)get("validity");
    }

    @JsonProperty(value = "validity")
    public void setValidity(Long validity) {
        set("validity", validity);
    }

    /* 预计数量 estimatedNumber */
    @JsonProperty(value = "estimatedNumber")
    public Long getEstimatedNumber() {
        return (Long)get("estimatedNumber");
    }

    @JsonProperty(value = "estimatedNumber")
    public void setEstimatedNumber(Long estimatedNumber) {
        set("estimatedNumber", estimatedNumber);
    }

    /* 单价预算 budget */
    @JsonProperty(value = "budget")
    public Double getBudget() {
        return (Double)get("budget");
    }

    @JsonProperty(value = "budget")
    public void setBudget(Double budget) {
        set("budget", budget);
    }

    /* 分组 groupId */
    @JsonProperty(value = "groupId")
    public String getGroupId() {
        return (String) get("groupId");
    }

    @JsonProperty(value = "groupId")
    public void setGroupId(String groupId) {
        set("groupId", groupId);
    }

    /* 简介 synopsis */
    @JsonProperty(value = "synopsis")
    public String getSynopsis() {
        return (String) get("synopsis");
    }

    @JsonProperty(value = "synopsis")
    public void setSynopsis(String synopsis) {
        set("synopsis", synopsis);
    }

    /* 经销商商品们的实体 salesGoods */
    @JsonProperty(value = "salesGoods")
    public List<Document> getSalesGoods() {
        return (List<Document>) get("salesGoods");
    }

    @JsonProperty(value = "salesGoods")
    public void setSalesGoods(List<Document> salesGoods) {
        set("salesGoods", salesGoods);
    }

    /* 供应商商品们的实体 factoryGoods */
    @JsonProperty(value = "factoryGoods")
    public List<Document> getFactoryGoods() {
        return (List<Document>) get("factoryGoods");
    }

    @JsonProperty(value = "factoryGoods")
    public void setFactoryGoods(List<Document> factoryGoods) {
        set("factoryGoods", factoryGoods);
    }

    /* 此方案已分享的供应商的id factoryIds */
    @JsonProperty(value = "factoryIds")
    public List<String> getFactoryIds() {
        return (List<String>) get("factoryIds");
    }

    @JsonProperty(value = "factoryIds")
    public void setFactoryIds(List<String> factoryIds) {
        set("factoryIds", factoryIds);
    }

    /* 提报过来的供应商们的方案的id factorySchemeIds */
//    @JsonProperty(value = "factorySchemeIds")
//    public List<String> getFactorySchemeIds() {
//        return (List<String>) get("factorySchemeIds");
//    }
//
//    @JsonProperty(value = "factorySchemeIds")
//    public void setFactorySchemeIds(List<String> factorySchemeIds) {
//        set("factorySchemeIds", factorySchemeIds);
//    }

    /* 方案状态 1进行中 2已过期 3已完成 4已结束 */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }


    @JsonProperty(value = "_ctime")
    public Date getCreatetime() {
        return (Date) get("_ctime");
    }

    @JsonProperty(value = "number")
    public Long getNumber() {
        return (Long) get("number");
    }

    @JsonProperty(value = "number")
    public void setNumber(Long number) {
        set("number", number);
    }


    /* 创建时间 */
    @JsonProperty(value = "time")
    public Long getTime() {
        return (Long) get("time");
    }

    @JsonProperty(value = "time")
    public void setTime(Long time) {
        set("time", time);
    }

    /**
     * 修改方案
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的方案");
        }
        SchemeSet schemeSet = Holder.getBean(SchemeSet.class);
        return schemeSet.Update(this.getId(),this) > 0;
    }

}
