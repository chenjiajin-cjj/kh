package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Mongodb.Set;
import io.framecore.Orm.ViewStore;

/**
 *
 */
public class ProductBrand extends ViewStore {

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
	
	
	/* BrandId */
	@JsonProperty(value = "BrandId")
	public String getBrandId() {
		return (String) get("BrandId");
	}

	@JsonProperty(value = "BrandId")
	public void setBrandId(String brandId) {
		set("BrandId", brandId);
	}
	
	
	public Brand getBrand()
	{
		Set<Brand> brandSet = (Set<Brand>)Holder.getBean("BrandSet");
		
		return brandSet.Get(this.getBrandId());
	}

}
