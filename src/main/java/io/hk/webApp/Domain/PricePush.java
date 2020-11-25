package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Orm.ViewStore;

/**
 * 供应商价格推送
 */
public class PricePush  extends ViewStore {
	
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

}