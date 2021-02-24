package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Saler;

@Repository(value="SalerSet")
@Scope(value="prototype")
public class SalerSet extends Set<Saler> {

	public SalerSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Saler> getType() {
		return Saler.class;
	}
	
	
	public Result addByUser(String userId)
	{
		Saler saler = new Saler();
		
		saler.setUserId(userId);
		
		this.Add(saler);
		
		return Result.succeed();
		
	}
	

}
