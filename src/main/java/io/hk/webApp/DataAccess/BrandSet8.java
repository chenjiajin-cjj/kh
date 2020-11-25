package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Brand;
 
 

@Repository(value="BrandSet8")
@Scope(value="prototype")
public class BrandSet8 extends Set<Brand> {

	public BrandSet8() {
		super(_db.getDbName());
	}

	@Override
	public Class<Brand> getType() {
		return Brand.class;
	}
	

	

}
