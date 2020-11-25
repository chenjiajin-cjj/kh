package io.hk.webApp.Domain;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Mongodb.Set;
import io.framecore.Orm.ViewStore;

public class User extends ViewStore {
	

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
	
	/* phone */
	@JsonProperty(value = "phone")
	public String getPhone() {
		return (String) get("phone");
	}

	@JsonProperty(value = "phone")
	public void setPhone(String phone) {
		set("phone", phone);
	}
	
	/* password */
	@JsonProperty(value = "password")
	public String getPassword() {
		return (String) get("password");
	}

	@JsonProperty(value = "password")
	public void setPassword(String password) {
		set("password", password);
	}
	
	/* identity  , 1: factory  2:saler */
	@JsonProperty(value = "identity")
	public Integer getIdentity() {
		return (Integer) get("identity");
	}

	@JsonProperty(value = "identity")
	public void setIdentity(Integer identity) {
		set("identity", identity);
	}
	
	
	/* loginKey */
	@JsonProperty(value = "loginKey")
	public String getLoginKey() {
		return (String) get("loginKey");
	}

	@JsonProperty(value = "loginKey")
	public void setLoginKey(String loginKey) {
		set("loginKey", loginKey);
	}
	
	/* lastloginTime */
	@JsonProperty(value = "lastloginTime")
	public Date getLastloginTime() {
		return (Date) get("lastloginTime");
	}

	@JsonProperty(value = "lastloginTime")
	public void setLastloginTime(Date lastloginTime) {
		set("lastloginTime", lastloginTime);
	}

	
	public String login()
	{
		String loginKey =UUID.randomUUID().toString().replace("-", "");
		
		Set<User> userSet = (Set<User>)Holder.getBean("UserSet");

		this.setLoginKey(loginKey);
		this.setLastloginTime(new Date());
		
		userSet.Update(this.getId(), this);
		
		return loginKey;
	}
	
	// 修改密码
	
	//修改手机号码
	
	//退出
}
