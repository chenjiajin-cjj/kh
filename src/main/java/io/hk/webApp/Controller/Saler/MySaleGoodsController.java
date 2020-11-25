package io.hk.webApp.Controller.Saler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Saler/MySaleGoods/")
public class MySaleGoodsController {
	
	/**
	 * 商品查询
	 */
	@RequestMapping("productSearch")
	public String productSearch() {
		
	 
		return "aa";
	}
	
	/**
	 * 商品查询
	 */
	@RequestMapping("mySaleGoodsSearch")
	public String mySaleGoodsSearch() {
		
	 
		return "aa";
	}
	

}
