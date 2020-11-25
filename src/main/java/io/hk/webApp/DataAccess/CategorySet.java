package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Category;
 
 

@Repository(value="CategorySet")
@Scope(value="prototype")
public class CategorySet extends Set<Category> {

	public CategorySet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Category> getType() {
		return Category.class;
	}
	

	

}
