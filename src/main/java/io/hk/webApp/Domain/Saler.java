package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Orm.ViewStore;

/**
 * 经销商
 */
public class Saler  extends ViewStore  {
	
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
		

		/* userId */
		@JsonProperty(value = "userId")
		public String getUserId() {
			return (String) get("userId");
		}

		@JsonProperty(value = "userId")
		public void setUserId(String userId) {
			set("userId", userId);
		}
		
		
}
