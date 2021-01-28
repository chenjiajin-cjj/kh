package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.ICategoryService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.vo.CategorySortVO;
import org.apache.commons.lang3.StringUtils;
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
    public Result add(@RequestBody Category category) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return categoryService.add(category) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }

    /**
     * 展示单个子分类
     */
    @GetMapping("searchById")
    public Result searchById(String id) {
        return Result.succeed(new Category().getById(id));
    }

    /**
     * 重命名子分类
     */
    @PostMapping("rename")
    public Result rename(@RequestBody Category category) {
        User user = systemUtil.getUser(httpServletRequest);
        Category category1 = new Category().getById(category.getId());
        if (null == category1 || !user.getId().equals(category1.getFactoryId())) {
            throw new OtherExcetion("只能修改自己发布的分类");
        }
        if(StringUtils.isAnyEmpty(category.getId(),category.getName())){
            throw new OtherExcetion("请完善必填项");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        category1.setName(category.getName());
        return category1.updateById() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 子分类排序
     */
    @PostMapping("sort")
    public Result sort(@RequestBody CategorySortVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return categoryService.sort(vo) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 隐藏显示子分类
     */
    @PostMapping("showOrHide")
    public Result showOrHide(@RequestBody Category category) {
        User user = systemUtil.getUser(httpServletRequest);
        Category category1 = new Category().getById(category.getId());
        if (null == category1 || !user.getId().equals(category1.getFactoryId())) {
            throw new OtherExcetion("只能修改自己发布的分类");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return categoryService.showOrHide(category) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 删除子分类
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        Category category1 = new Category().getById(id);
        if (null == category1 || !user.getId().equals(category1.getFactoryId())) {
            throw new OtherExcetion("只能修改自己发布的分类");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return category1.deleteById() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示子分类列表
     */
    @PostMapping("search")
    public Result search() {
        return null;
    }

    /**
     * 转移商品
     */
    @PostMapping("transfer")
    public Result transfer() {
        return null;
    }

}
