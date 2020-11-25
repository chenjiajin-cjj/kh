package io.hk.webApp.Controller.Factory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Factory/MySaler/")
public class MySalerController {
	
	
	
	
	/**
	 * 经销商审核
	 */
	@RequestMapping("Checking")
	public String checkingList() {
		
	 
		return "aa";
	}
	

	/**
	 * 经销商审核,同意
	 */
	@RequestMapping("Checking/agree")
	public String checkingAgree() {
		
	 
		return "aa";
	}
	
	
	/**
	 * 经销商审核，拒绝
	 */
	@RequestMapping("Checking/refuse")
	public String checkingRefuse() {
		
	 
		return "aa";
	}
	
	/**
	 * 黑名单，列表
	 */
	@RequestMapping("blacklist")
	public String blacklist() {
		
	 
		return "aa";
	}
	

	/**
	 * 黑名单，拉黑
	 */
	@RequestMapping("blacklist/add")
	public String blacklistadd() {
		
	 
		return "aa";
	}
	
	/**
	 * 黑名单，移除
	 */
	@RequestMapping("blacklist/remove")
	public String blacklistRemove() {
		
	 
		return "aa";
	}
	
	
	/**
	 * 列表
	 */
	@RequestMapping("list")
	public String list() {
		
	 
		return "aa";
	}
	
	
	/**
	 * 列表, 置顶
	 */
	@RequestMapping("list/indexUp")
	public String listIndexUp() {
		
	 
		return "aa";
	}
	
	/**
	 * 列表, 置顶
	 */
	@RequestMapping("list/indexCancel")
	public String listIndexCancel() {
		
	 
		return "aa";
	}
	
	/**
	 * 列表，删除
	 */
	@RequestMapping("list/remove")
	public String listRemove() {
		
	 
		return "aa";
	}
	
	/**
	 * 列表，备注
	 */
	@RequestMapping("list/comment")
	public String listComment() {
		
	 
		return "aa";
	}
	
	/**
	 * 列表，发送报价
	 */
	@RequestMapping("list/PricePushOne")
	public String PricePushOne() {
		
	 
		return "aa";
	}
	
	
	/**
	 * 发送报价,群发
	 */
	@RequestMapping("PricePushMany")
	public String PricePushMany() {
		
	 
		return "aa";
	}
	

}
