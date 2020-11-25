package io.hk.webApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.framecore.Aop.Holder;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.framecore.Tool.AesHelp;
import io.framecore.Tool.Md5Help;
import io.framecore.Tool.PropertiesHelp;
import io.framecore.Tool.RSAHelp;
import io.framecore.redis.CacheHelp;
import io.hk.webApp.DataAccess.PhoneMsgSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Domain.Factory;
import io.hk.webApp.Domain.PhoneMsg;
import io.hk.webApp.Domain.User;

@RestController
@RequestMapping("api/v1/index/")
public class IndexController {
	
	
	@Autowired
	UserSet userSet;
	
	@Autowired
	PhoneMsgSet phoneMsgSet;
	
	 
	/**
	 * 注册
	 */
	@RequestMapping("register")
	public String register(String phone,String validCode, String password,String password2,Integer identity) {
		
		
		
	 
		return "aa";
	}
	
	
	/**
	 * 发送验证码,带签名，防刷
	 */
	@RequestMapping("getValidCode")
	public Result getValidCode(String phone, String sign) {
		
		if(!sign.equals(Md5Help.toMD5("Index.sendValidCode.58fdjj*^YGjy6Q"+phone)))
		{
			return Result.failure("发送验证码失败，签名错误");
		}
		if(CacheHelp.get("Index.sendValidCode.ex:"+phone)!=null)
		{
			return Result.failure("发送验证码失败， 60秒内不能重复发送");
		}
		
		
		String validCode= String.valueOf((int)(Math.random()*9+1)*100000) ;
		
		CacheHelp.set("Index.sendValidCode:"+phone, validCode, 5*60); //5分钟过期
		CacheHelp.set("Index.sendValidCode.ex:"+phone, validCode, 53); // 1分钟内不能重复发送。
		

		phoneMsgSet.addValid(phone, validCode);
		
	 
		return Result.succeed();
	}
	
	/**
	 * 手机号码登陆
	 */
	@RequestMapping("loginByPhone")
	public Result loginByPhone(String phone,String password) {
		
		User user = userSet.getUserByPhone(phone);
		if(user==null)
		{
			return Result.failure("用户不存在");
		}
		
		if(!user.getPassword().equals(password))
		{
			return Result.failure("密码错误");
		}
		
		String loginKey= userSet.login(user.getId());
		
		
	 
		return Result.succeed(loginKey);
	}
	
	/**
	 * 密码找回
	 */
	@RequestMapping("findPassword")
	public String findPassword(String phone,String validCode, String password,String password2) {
		
	 
		return "aa";
	}


}
