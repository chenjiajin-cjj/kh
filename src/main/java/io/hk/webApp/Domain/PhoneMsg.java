package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Orm.ViewStore;

/**
 * 短信信息
 */
public class PhoneMsg extends ViewStore {
	
	final static int MSG_TYPE_VALID=1;

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

	/* phone */
	@JsonProperty(value = "phone")
	public String getPhone() {
		return (String) get("phone");
	}

	@JsonProperty(value = "phone")
	public void setPhone(String phone) {
		set("phone", phone);
	}

	/* message */
	@JsonProperty(value = "message")
	public String getMessage() {
		return (String) get("message");
	}

	@JsonProperty(value = "message")
	public void setMessage(String message) {
		set("message", message);
	}

	/* status :0 未发生，1 已发生 */
	@JsonProperty(value = "status")
	public Integer getStatus() {
		return (Integer) get("status");
	}

	@JsonProperty(value = "status")
	public void setStatus(Integer status) {
		set("status", status);
	}

}
