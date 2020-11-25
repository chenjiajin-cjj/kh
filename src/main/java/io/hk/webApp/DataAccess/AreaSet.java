package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Area;
 
 

@Repository(value="AreaSet")
@Scope(value="prototype")
public class AreaSet extends Set<Area> {

	public AreaSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Area> getType() {
		return Area.class;
	}
	

	

}
