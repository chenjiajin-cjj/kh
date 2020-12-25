package io.hk.webApp.Controller;

import io.hk.webApp.Tools.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.framecore.Frame.Result;
import io.hk.webApp.DataAccess.UserSet;

@RestController
@RequestMapping("api/v1/index/")
public class IndexController {
	
	
	@Autowired
	UserSet userSet;
	

	
	 
	/**
	 * 注册
	 */
	@GetMapping("register")
	@AccessToken(Token = false)
	public String register(String id ,String msg) {

//		WebSocketServer.sendMessage(id,msg);

		return "aa";
	}
	
	

	
	/**
	 * 手机号码登陆
	 */
	@RequestMapping("loginByPhone")
	public Result loginByPhone(String phone,String password) {
		return null;
//		User user = userSet.getUserByPhone(phone);
//		if(user==null)
//		{
//			return Result.failure("用户不存在");
//		}
//
//		if(!user.getPassword().equals(password))
//		{
//			return Result.failure("密码错误");
//		}
//
//		String loginKey= userSet.login(user.getId());
//
//		return Result.succeed(loginKey);
	}
	
	/**
	 * 密码找回
	 */
	@RequestMapping("findPassword")
	public String findPassword(String phone,String validCode, String password,String password2) {
		
	 
		return "aa";
	}


}
