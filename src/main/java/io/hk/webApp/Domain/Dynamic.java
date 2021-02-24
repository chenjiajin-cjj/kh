package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.DynamicSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

/**
 * 供应商动态表
 */
public class Dynamic extends ViewStore {


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

    /* 供应商id factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
    }

    /* 动态内容 details */
    @JsonProperty(value = "details")
    public String getDetails() {
        return (String) get("details");
    }

    @JsonProperty(value = "details")
    public void setDetails(String details) {
        set("details", details);
    }

    /* 内容图片 img */
    @JsonProperty(value = "img")
    public String getImg() {
        return (String) get("img");
    }

    @JsonProperty(value = "img")
    public void setImg(String img) {
        set("img", img);
    }

    /**
     * 供应商修改动态
     */
    public boolean updateDynamic(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的动态");
        }
        DynamicSet dynamicSet = Holder.getBean(DynamicSet.class);
        return dynamicSet.Update(this.getId(),this) > 0;
    }

    /**
     * 供应商删除动态
     */
    public boolean deleteById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要删除的商品");
        }
        DynamicSet dynamicSet = Holder.getBean(DynamicSet.class);
        return dynamicSet.Delete(this.getId()) > 0;
    }

    /**
     * 查询单个动态
     */
    public Dynamic getById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请传入id");
        }
        DynamicSet dynamicSet = Holder.getBean(DynamicSet.class);
        return dynamicSet.Get(id);
    }

}
