package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IBrandService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 品牌管理
 */
@RestController
@RequestMapping("api/v1/Factory/brand/")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 添加品牌
     *
     * @param brand
     * @return
     */
    @PostMapping("add")
    public Result addBrand(@RequestBody Brand brand) {
        User user = systemUtil.getUser(httpServletRequest);
        if (null != user) {
            brand.setUserId(user.getId());
        } else {
            throw new OtherExcetion("登录凭证已失效");
        }
        return brandService.addBrand(brand) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }

    /**
     * 根据id查询单个品牌
     *
     * @return
     */
    @GetMapping("selectBrandById")
    public Result selectBrandById(String id) {
        return Result.succeed(new Brand().getById(id));
    }

    /**
     * 修改品牌
     */
    @PostMapping("modify")
    public Result modify(@RequestBody Brand brand) {
        return brand.updateById(brand) ? Result.succeed("修改成功") : Result.failure("修改失败");
    }

    /**
     * 删除品牌
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        return new Brand().deleteById(id) ? Result.succeed("删除成功") : Result.failure("删除失败");
    }

    /**
     * 审核通过品牌
     */
    @PostMapping("pass")
    public Result pass(@RequestBody Brand brand) {
        if (StringUtils.isEmpty(brand.getId())) {
            throw new OtherExcetion("请选择要审核通过的品牌");
        }
        Brand brand1 = new Brand();
        brand1.setId(brand.getId());
        brand1.setStatus("1");
        return brand1.updateById(brand1) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 驳回品牌
     */
    @PostMapping("refuse")
    public Result refuse(@RequestBody Brand brand) {
        if (StringUtils.isEmpty(brand.getId())) {
            throw new OtherExcetion("请选择要驳回的品牌");
        }
        if (StringUtils.isEmpty(brand.getCause())) {
            throw new OtherExcetion("请输入驳回原因");
        }
        Brand brand1 = new Brand();
        brand1.setId(brand.getId());
        brand1.setCause(brand.getCause());
        brand1.setStatus("3");
        return brand1.updateById(brand1) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询品牌列表
     */
    @GetMapping("search")
    public Result search() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        PageData<Brand> pageData = brandService.search(pagePars);
        return Result.succeed(pageData);
    }

}
