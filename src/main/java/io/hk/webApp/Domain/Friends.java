package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.FriendsSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

/**
 * 供应商：对经销商的好友关系
 */
public class Friends extends ViewStore {

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

	/* 用户id userId */
	@JsonProperty(value = "userId")
	public String getUserId() {
		return (String) get("userId");
	}

	@JsonProperty(value = "userId")
	public void setUserId(String userId) {
		set("userId", userId);
	}

	/* 朋友id friendId */
	@JsonProperty(value = "friendId")
	public String getFriendId() {
		return (String) get("friendId");
	}

	@JsonProperty(value = "friendId")
	public void setFriendId(String friendId) {
		set("friendId", friendId);
	}

	/* 朋友主营品牌 mainLy  */
	@JsonProperty(value = "mainLy")
	public Object getMainLy() {
		return get("mainLy");
	}

	@JsonProperty(value = "mainLy")
	public void setMainLy(Object mainLy) {
		set("mainLy", mainLy);
	}

	/* 朋友备注 remark */
	@JsonProperty(value = "remark")
	public String getRemark() {
		return (String) get("remark");
	}

	@JsonProperty(value = "remark")
	public void setRemark(String remark) {
		set("remark", remark);
	}

	/* 是否核心 1是核心 kernel */
	@JsonProperty(value = "kernel")
	public String getKernel() {
		return (String) get("kernel");
	}

	@JsonProperty(value = "kernel")
	public void setKernel(String kernel) {
		set("kernel", kernel);
	}

	/* 好友分组 1是默认 2是普通 3是vip 4是特邀 group */
	@JsonProperty(value = "group")
	public String getGroup() {
		return (String) get("group");
	}

	@JsonProperty(value = "group")
	public void setGroup(String group) {
		set("group", group);
	}


	/**
	 * 修改
	 */
	public boolean updateById(){
		if(StringUtils.isEmpty(this.getId())){
			throw new OtherExcetion("请选择要修改的");
		}
		FriendsSet friendsSet = Holder.getBean(FriendsSet.class);
		return friendsSet.Update(this.getId(), this) > 0;
	}

	/**
	 * 删除
	 */
	public boolean deleteById(String id){
		if(StringUtils.isEmpty(id)){
			throw new OtherExcetion("请选择要修改的");
		}
		FriendsSet friendsSet = Holder.getBean(FriendsSet.class);
		return friendsSet.Delete(id) > 0;
	}

	/**
	 * 查询单个
	 */
	public Friends getById(String id){
		if(StringUtils.isEmpty(id)){
			throw new OtherExcetion("请选择要查询的客户");
		}
		FriendsSet friendsSet = Holder.getBean(FriendsSet.class);
		return friendsSet.Get(id);
	}

}