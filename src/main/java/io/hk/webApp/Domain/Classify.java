package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.ClassifySet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 分类
 */

public class Classify extends ViewStore {

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

    /* name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }


    /* 排序 sort */
    @JsonProperty(value = "sort")
    public Integer getSort() {
        return (Integer) get("sort");
    }

    @JsonProperty(value = "sort")
    public void setSort(Integer sort) {
        set("sort", sort);
    }

    /* 是否显示 1显示 2不显示 status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 上级id fatherId */
    @JsonProperty(value = "fatherId")
    public String getFatherId() {
        return (String) get("fatherId");
    }

    @JsonProperty(value = "fatherId")
    public void setFatherId(String fatherId) {
        set("fatherId", fatherId);
    }

    /* 级别 lv 1是一级 2是二级*/
    @JsonProperty(value = "lv")
    public String getLv() {
        return (String) get("lv");
    }

    @JsonProperty(value = "lv")
    public void setLv(String lv) {
        set("lv", lv);
    }

    /* 商品数量 productNumber*/
    @JsonProperty(value = "productNumber")
    public Long getProductNumber() {
        return (Long) get("productNumber");
    }

    @JsonProperty(value = "productNumber")
    public void setProductNumber(Long productNumber) {
        set("productNumber", productNumber);
    }


    /* 下级分类 展示用 sons*/
    @JsonProperty(value = "sons")
    public List<Classify> getSons() {
        return (List<Classify>) get("sons");
    }

    @JsonProperty(value = "sons")
    public void setSons(List<Classify> sons) {
        set("sons", sons);
    }


    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的分类");
        }
        ClassifySet classifySet = Holder.getBean(ClassifySet.class);
        return classifySet.Update(this.getId(),this) > 0;
    }

}
