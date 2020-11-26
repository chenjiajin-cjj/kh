package io.hk.webApp.Controller.Factory;

import javax.servlet.http.HttpServletRequest;

import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Tools.TablePagePars;

/**
 * 供应商商品管理
 */
@RestController
@RequestMapping("api/v1/Factory/Product/")
public class ProductController {

	@Autowired
	private IProductService productService;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	private SystemUtil systemUtil;

	/**
	 * 新增
	 */
	@PostMapping("add")
	public Result add(@RequestBody Product product) {
		User user = systemUtil.getUser(httpServletRequest);
		if (null != user){
			product.setFactoryId(user.getId());
		}else{
			throw new OtherExcetion("登录凭证已失效");
		}
		return productService.addProduct(product) ? Result.succeed("添加成功") : Result.failure("添加失败");
	}

	/**
	 * 修改
	 */
	@PostMapping("modify")
	public Result modify(@RequestBody Product product) {
		return product.updateProduct(product) ? Result.succeed("修改成功") : Result.failure("修改失败");
	}

	/**
	 * 删除
	 */
	@DeleteMapping("delete")
	public Result delete(String id) {
		return new Product().deleteById(id) ? Result.succeed("删除成功") : Result.failure("删除失败");
	}

	/**
	 * 上架
	 */
	@PostMapping("online")
	public Result online(String id) {
		if(StringUtils.isEmpty(id)){
			throw new OtherExcetion("请选择要上架的商品");
		}
		Product product = new Product();
		product.setId(id);
		product.setStatus("1");
		return product.updateProduct(product) ? Result.succeed("操作成功") : Result.failure("操作失败");
	}

	/**
	 * 下架
	 */
	@PostMapping("downline")
	public Result downline(String id) {
		if(StringUtils.isEmpty(id)){
			throw new OtherExcetion("请选择要下架的商品");
		}
		Product product = new Product();
		product.setId(id);
		product.setStatus("2");
		return product.updateProduct(product) ? Result.succeed("操作成功") : Result.failure("操作失败");
	}

	/**
	 * 根据id查询单个商品
	 */
	@GetMapping("searchById")
	public Result searById(String id) {
		return Result.succeed(new Product().getById(id));
	}

	/**
	 * 列表查询
	 */
	@GetMapping("search")
	public Result search() {
		User user = systemUtil.getUser(httpServletRequest);
		if (null == user){
			throw new OtherExcetion("登录凭证已失效");
		}
		TablePagePars pagePars = new TablePagePars(httpServletRequest);
		PageData<Product> pageData = productService.search(pagePars,user.getId());
		return Result.succeed(pageData);
	}
	

}
