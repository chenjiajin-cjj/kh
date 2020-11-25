package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Orm.ViewStore;

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

}
