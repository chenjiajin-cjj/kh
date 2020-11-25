package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.PricePush;
 
 

@Repository(value="PricePushSet")
@Scope(value="prototype")
public class PricePushSet extends Set<PricePush> {

	public PricePushSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<PricePush> getType() {
		return PricePush.class;
	}
	

	

}
