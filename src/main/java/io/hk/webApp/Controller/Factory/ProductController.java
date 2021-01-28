package io.hk.webApp.Controller.Factory;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.Dynamic;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IClassifyService;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.vo.BaseVO;
import io.hk.webApp.vo.ProductShareVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Tools.TablePagePars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private IClassifyService iClassifyService;

    /**
     * 新增
     */
    @PostMapping("add")
    public Result add(@RequestBody Product product) {
        User user = systemUtil.getUser(httpServletRequest);
        if (null != user) {
            product.setFactoryId(user.getId());
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return productService.addProduct(product) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }

    /**
     * 修改
     */
    @PostMapping("modify")
    public Result modify(@RequestBody Product product) {
        Product product1 = new Product().getById(product.getId());
        User user = systemUtil.getUser(httpServletRequest);
        if (null == product1) {
            throw new OtherExcetion("不存在的商品");
        }
        if (!product1.getFactoryId().equals(user.getId())) {
            throw new OtherExcetion("只能修改自己的发布的商品");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return productService.update(product,user) ? Result.succeed("修改成功") : Result.failure("修改失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return productService.delete(id) ? Result.succeed("删除成功") : Result.failure("删除失败");
    }

    /**
     * 上架
     */
    @PostMapping("online")
    public Result online(@RequestBody BaseVO vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new OtherExcetion("请选择要上架的商品");
        }
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        Product product = new Product();
        product.setId(vo.getId());
        product.setStatus("1");
        return product.updateProduct() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 下架
     */
    @PostMapping("downline")
    public Result downline(@RequestBody BaseVO vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new OtherExcetion("请选择要下架的商品");
        }
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        Product product = new Product();
        product.setId(vo.getId());
        product.setStatus("2");
        return product.updateProduct() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据id查询单个商品
     */
    @GetMapping("searchById")
    public Result searById(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(productService.getOne(id,user));
    }

    /**
     * 列表查询商品信息
     */
    @GetMapping("search")
    public Result search() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        PageData<Product> pageData = productService.search(pagePars, user);
        return Result.succeed(pageData);
    }

    /**
     * 列表查询商品页的品牌和分组
     */
    @GetMapping("getProutBrandAndGroup")
    public Result getProutBrandAndGroup() {
        User user = systemUtil.getUser(httpServletRequest);
        String userId;
        if(StringUtils.isEmpty(user.getFatherId())){
            userId = user.getId();
        }else{
            userId = user.getFatherId();
        }
        Map<String, Object> map = new HashMap<>();
        List<Brand> list = productService.searchBrand(userId);
        List<Brand> brandList = new ArrayList<>();
        list.forEach((a) -> {
            if ("1".equals(a.getPerpetual()) || a.getTime() >= System.currentTimeMillis()) {
                brandList.add(a);
            }
        });
        {
            List<Map<String, String>> tagList = new ArrayList<>();
            Map<String, String> tag = new HashMap<>();
            tag.put("name", "推荐");
            tag.put("code", "tagRec");
            tagList.add(tag);
            tag = new HashMap<>();
            tag.put("name", "热销");
            tag.put("code", "tagHot");
            tagList.add(tag);
            tag = new HashMap<>();
            tag.put("name", "新品");
            tag.put("code", "tagNew");
            tagList.add(tag);
            map.put("tag", tagList);
        }
        map.put("brand", brandList);
        map.put("group", productService.searchGroups(userId));
        map.put("classify",iClassifyService.searchClassifyName());
        return Result.succeed(map);
    }


    /**
     * 屏蔽/取消屏蔽商品
     */
    @GetMapping("shield")
    public Result shield(String productId) {
        return productService.shield(productId) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }


}
