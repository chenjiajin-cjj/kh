package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.GroupSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 分组表
 */
public class Group extends ViewStore {

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


    /* 分组名 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 分组排序 sort */
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

    /* 供应商的id factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
    }

    /* 商品数量 */
    @JsonProperty(value = "number")
    public Long getNumber() {
        return (Long) get("number");
    }

    @JsonProperty(value = "number")
    public void setNumber(Long number) {
        set("number", number);
    }

    /**
     * 修改分组
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的分组");
        }
        GroupSet groupSet = Holder.getBean(GroupSet.class);
        return groupSet.Update(this.getId(),this) > 0;
    }

    /**
     * 删除分组
     */
    public boolean deleteById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要删除的分组");
        }
        GroupSet groupSet = Holder.getBean(GroupSet.class);
        return groupSet.Delete(id) > 0;
    }

    /**
     * 查询单个分组
     */
    public Group getById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要查询的分组");
        }
        GroupSet groupSet = Holder.getBean(GroupSet.class);
        return groupSet.Get(id);
    }

    private List<Category> sonList;

    public List<Category> getSonList() {
        return sonList;
    }

    public void setSonList(List<Category> sonList) {
        this.sonList = sonList;
    }
}
