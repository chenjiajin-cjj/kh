package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.ProductGroup;
 
 

@Repository(value="ProductGroupSet")
@Scope(value="prototype")
public class ProductGroupSet extends Set<ProductGroup> {

	public ProductGroupSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<ProductGroup> getType() {
		return ProductGroup.class;
	}
	

	

}
