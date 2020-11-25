package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Orm.ViewStore;

public class ProductGroup extends ViewStore {

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
	
	

	// groupName
	@JsonProperty(value = "groupName")
	public String getGroupName() {
		return (String)get("groupName");
	}
	@JsonProperty(value = "groupName")
	public void setGroupName(String groupName) {
		set("groupName", groupName);
	}
	
	

}
