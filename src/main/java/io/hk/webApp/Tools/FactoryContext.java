package io.hk.webApp.Tools;

import org.springframework.context.annotation.Configuration;

import io.hk.webApp.Domain.Factory;
import io.hk.webApp.Domain.User;

@Configuration(value = "FactoryContext")
public class FactoryContext {
	
	//是否主账号
	public Boolean isMain()
	{
		return null;
		
	}
	
	
	public User getUser()
	{
		return null;
		
	}
	
	public Factory getFactory()
	{
		return null;
		
	}

}
