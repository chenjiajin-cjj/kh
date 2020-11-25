package io.hk.webApp.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.framecore.Aop.Holder;
import io.framecore.Mongodb.Set;
import io.hk.webApp.DataAccess.FactorySet;
import io.hk.webApp.Domain.Factory;

@RestController
@RequestMapping("api/v1/test/")
public class TestController {
	
 
	
	@RequestMapping("t1")
	public String test() {
		
		Set<Factory> factorySet = (Set<Factory>)Holder.getBean("FactorySet");
		
		Factory factory = factorySet.First();
		return "aa";
	}

}
