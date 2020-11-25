package io.hk.webApp;

 
import io.framecore.Aop.Holder;
import io.framecore.Frame.JFrameException;
import io.framecore.Tool.HttpHelp;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;

public class Test {
	
	public static void main(String[] args) throws JFrameException, InterruptedException {
		 
	}
	
	
 
	
	
	
	
	//创建 factory
	public static void addFactory()
	{
		
		UserSet uset = Holder.getBean(UserSet.class);
		
		User user = new User();
		user.setIdentity(1);
		user.setPhone("15836465656");
		user.setPassword("123456");

		String uid = String.valueOf(uset.Add(user)) ;
		
		FactorySet set = Holder.getBean(FactorySet.class);
		
		Factory factory = new Factory();
		factory.setAddress("厦门一号路");
		factory.setAreaCode("1205");
		factory.setBusinessRank("电子数码");
		factory.setContactName("王总");
		factory.setContactPhone("15836465656");
		factory.setFatherFactoryId(null);
		factory.setHeadImg("aaa");
		factory.setName("厦门xx电子有限公司");
		factory.setUserId(uid);
		
		set.Add(factory);
		
	}
	
 
	
	//创建分类
	public static void addCatory()
	{
		CategorySet set = Holder.getBean(CategorySet.class);
		Category c1 =new Category();
		
		c1.setCode("dzsm");
		c1.setName("电子数码");
		
		set.Add(c1);
		
		Category c2 =new Category();
		
		c2.setCode("jydq");
		c2.setName("家用电器");
		
		set.Add(c2);
		
		
	}
	
	
	

}
