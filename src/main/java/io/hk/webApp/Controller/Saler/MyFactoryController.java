package io.hk.webApp.Controller.Saler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Saler/MyFactory/")
public class MyFactoryController {
	
	
	/**
	 * 经销商审核
	 */
	@RequestMapping("search")
	public String search() {
		
	 
		return "aa";
	}
	
	
	/**
	 * 列表, 置顶
	 */
	@RequestMapping("indexUp")
	public String indexUp() {
		
	 
		return "aa";
	}
	
	/**
	 * 列表, 置顶
	 */
	@RequestMapping("indexCancel")
	public String indexCancel() {
		
	 
		return "aa";
	}
	
	/**
	 * 列表，删除
	 */
	@RequestMapping("remove")
	public String remove() {
		
	 
		return "aa";
	}
	

}
