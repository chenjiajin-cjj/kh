package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.CategorySet;
import io.hk.webApp.DataAccess.GroupSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

public class Category extends ViewStore {

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

	/* code */
	@JsonProperty(value = "code")
	public String getCode() {
		return (String) get("code");
	}

	@JsonProperty(value = "code")
	public void setCode(String code) {
		set("code", code);
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

	/* pcode */
	@JsonProperty(value = "pcode")
	public String getPcode() {
		return (String) get("pcode");
	}

	@JsonProperty(value = "pcode")
	public void setPcode(String pcode) {
		set("pcode", pcode);
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

	/* 上级分组的id fatherId */
	@JsonProperty(value = "fatherId")
	public String getFatherId() {
		return (String) get("fatherId");
	}

	@JsonProperty(value = "fatherId")
	public void setFatherId(String fatherId) {
		set("fatherId", fatherId);
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
	public Integer getNumber() {
		return (Integer) get("number");
	}

	@JsonProperty(value = "number")
	public void setNumber(Integer number) {
		set("number", number);
	}

	/**
	 * 修改子分组
	 */
	public boolean updateById(){
		if(StringUtils.isEmpty(this.getId())){
			throw new OtherExcetion("请选择要修改的子分组");
		}
		CategorySet categorySet = Holder.getBean(CategorySet.class);
		return categorySet.Update(this.getId(),this) > 0;
	}

	/**
	 * 删除子分组
	 */
	public boolean deleteById(){
		if(StringUtils.isEmpty(this.getId())){
			throw new OtherExcetion("请选择要删除的子分组");
		}
		CategorySet categorySet = Holder.getBean(CategorySet.class);
		return categorySet.Delete(this.getId()) > 0;
	}

	/**
	 * 查询单个子分组
	 */
	public Category getById(String id){
		if(StringUtils.isEmpty(id)){
			throw new OtherExcetion("请选择要查询的子分组");
		}
		CategorySet categorySet = Holder.getBean(CategorySet.class);
		return categorySet.Get(id);
	}

}
