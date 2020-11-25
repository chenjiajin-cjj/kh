package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Factory;
 

@Repository(value="FactorySet")
@Scope(value="prototype")
public class FactorySet extends Set<Factory> {

	public FactorySet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Factory> getType() {
		return Factory.class;
	}
	
	public Result addByUser(String userId)
	{
		Factory factory = new Factory();
		
		factory.setUserId(userId);

		
		this.Add(factory);
		return Result.succeed();
		
	}
	

}
