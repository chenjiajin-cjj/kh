package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.framecore.Orm.ViewStore;

/**
 * 供应商信息
 */
public class Factory extends ViewStore {
	

	// _id
	@JsonProperty(value = "_id")
	public String getId() {
		if(get("_id")==null)
		{
			return null;
		}
		return get("_id").toString();
	}
	@JsonProperty(value = "_id")
	public void setId(String Id) {
		set("_id", Id);
	}
	
	/* fatherFactoryId */
	@JsonProperty(value = "fatherFactoryId")
	public String getFatherFactoryId() {
		return (String) get("fatherFactoryId");
	}

	@JsonProperty(value = "fatherFactoryId")
	public void setFatherFactoryId(String fatherFactoryId) {
		set("fatherFactoryId", fatherFactoryId);
	}
	
	public Factory getFatherFactory()
	{
		Set<Factory> factorySet = (Set<Factory>)Holder.getBean("FactorySet");
		
		return factorySet.Get(getFatherFactoryId());
	}
	
	
	/* userId */
	@JsonProperty(value = "userId")
	public String getUserId() {
		return (String) get("userId");
	}

	@JsonProperty(value = "userId")
	public void setUserId(String userId) {
		set("userId", userId);
	}
	
	public User getUser()
	{
		Set<User> userSet = (Set<User>)Holder.getBean("UserSet");
		
		return userSet.Get(getUserId());
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
	
	
	/* contactName */
	@JsonProperty(value = "contactName")
	public String getContactName() {
		return (String) get("contactName");
	}

	@JsonProperty(value = "contactName")
	public void setContactName(String contactName) {
		set("contactName", contactName);
	}
	
	
	/* contactPhone */
	@JsonProperty(value = "contactPhone")
	public String getContactPhone() {
		return (String) get("contactPhone");
	}

	@JsonProperty(value = "contactPhone")
	public void setContactPhone(String contactPhone) {
		set("contactPhone", contactPhone);
	}
	

	/* areaCode */
	@JsonProperty(value = "areaCode")
	public String getAreaCode() {
		return (String) get("areaCode");
	}

	@JsonProperty(value = "contactPhone")
	public void setAreaCode(String areaCode) {
		set("areaCode", areaCode);
	}
	
	/* address */
	@JsonProperty(value = "address")
	public String getAddress() {
		return (String) get("address");
	}

	@JsonProperty(value = "address")
	public void setAddress(String address) {
		set("address", address);
	}
	
	
	/* businessRank */
	@JsonProperty(value = "businessRank")
	public String getBusinessRank() {
		return (String) get("businessRank");
	}

	@JsonProperty(value = "businessRank")
	public void setBusinessRank(String businessRank) {
		set("businessRank", businessRank);
	}
	
	/* headImg */
	@JsonProperty(value = "headImg")
	public String getHeadImg() {
		return (String) get("headImg");
	} 

	@JsonProperty(value = "headImg")
	public void setHeadImg(String headImg) {
		set("headImg", headImg);
	}
	
	//基础设置
	public Result baseSetting()
	{
		Set<Factory> factorySet = (Set<Factory>)Holder.getBean("FactorySet");
		
		 
		 
		
		factorySet.Update(this.getId(), this);
		
		return Result.succeed();
	}
	
	//认证
	
	//添加子账号
	
 
	
	

}
