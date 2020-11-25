package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import io.framecore.Aop.Holder;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.User;

import java.util.Date;
import java.util.UUID;


@Repository(value="UserSet")
@Scope(value="prototype")
public class UserSet extends Set<User> {

	public UserSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}
	
	
	public Result addUser(String phone,String password,Integer identity)
	{
		User uer =getUserByPhone(phone);
		if(uer!=null)
		{
			return Result.failure("该手机号码已经被注册。如果密码忘记，请找回密码。");
		}
		
		User user =new User();
		
		user.setIdentity(identity);
		user.setPhone(phone);
		user.setPassword(password);
		
		String userid = this.Add(user).toString();
		
		if(identity==1)
		{
			FactorySet factorySet =Holder.getBean(FactorySet.class);
			factorySet.addByUser(userid);
		}
		else
		{
			SalerSet salerSet =Holder.getBean(SalerSet.class);
			salerSet.addByUser(userid);
		}	
		
		
		return Result.succeed(phone);
	}

	public String login(String id)
	{
		String loginKey = UUID.randomUUID().toString().replace("-", "");

		User user = new User();
		user.setLoginKey(loginKey);
		user.setLastloginTime(new Date());

		this.Update(id, user);

		return loginKey;
	}

	
	public User getUserByPhone(String phone)
	{
		User uer =this.Where("phone=?", phone).First();
		
		return uer;
		 
	}

	public User getUserByLoginKey(String loginKey) {
		User user =this.Where("loginKey=?", loginKey).First();
		return user;

	}
	
	
}
