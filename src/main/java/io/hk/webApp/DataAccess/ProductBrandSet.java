package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.ProductBrand;
 
@Repository(value="ProductBrandSet")
@Scope(value="prototype")
public class ProductBrandSet extends Set<ProductBrand> {

	public ProductBrandSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<ProductBrand> getType() {
		return ProductBrand.class;
	}
	

	

}
