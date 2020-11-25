package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Blacklist;
 
 

@Repository(value="BlacklistSet")
@Scope(value="prototype")
public class BlacklistSet extends Set<Blacklist> {

	public BlacklistSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Blacklist> getType() {
		return Blacklist.class;
	}
	

	

}
