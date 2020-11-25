package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Friends;
 
 

@Repository(value="FriendsSet")
@Scope(value="prototype")
public class FriendsSet extends Set<Friends> {

	public FriendsSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Friends> getType() {
		return Friends.class;
	}
	

	

}
