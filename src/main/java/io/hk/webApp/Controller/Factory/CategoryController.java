package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.ICategoryService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.vo.CategorySortVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 子分类管理
 */
@RestController
@RequestMapping("api/v1/Factory/Category/")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;


    /**
     * 添加子分类
     */
    @PostMapping("add")
    public Result add(@RequestBody Category category){
        User user = systemUtil.getUser(httpServletRequest);
        if (null == user) {
            throw new OtherExcetion("登录凭证已失效");
        }
        return categoryService.add(category) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }
    /**
     * 展示单个子分类
     */
    @GetMapping("searchById")
    public Result searchById(String id){
        return Result.succeed(new Category().getById(id));
    }
    /**
     * 重命名子分类
     */
    @PostMapping("rename")
    public Result rename(@RequestBody Category category){
        return categoryService.rename(category) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
    /**
     * 子分类排序
     */
    @PostMapping("sort")
    public Result sort(@RequestBody CategorySortVO vo){
        return categoryService.sort(vo) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
    /**
     *展示子分类列表
     */
    @PostMapping("search")
    public Result search(){
        return null;
    }
    /**
     * 隐藏显示子分类
     */
    @PostMapping("showOrHide")
    public Result showOrHide(@RequestBody Category category){
        return categoryService.showOrHide(category) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 转移商品
     */
    @PostMapping("transfer")
    public Result transfer(){
        return null;
    }
}
