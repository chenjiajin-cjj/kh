package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IBrandService;
import io.hk.webApp.Tools.*;
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
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        if(StringUtils.isEmpty(user.getCompanyName())){
            throw new OtherExcetion(-10,"完善用户资料后方可进行操作");
        }
        return brandService.addBrand(brand,user) ? Result.succeed("添加成功") : Result.failure("添加失败");
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
//        User user = systemUtil.getUser(httpServletRequest);
//        Brand bran1 = new Brand().getById(brand.getId());
//        if(null == bran1 || !user.getId().equals(bran1.getUserId())){
//            throw new OtherExcetion("只能修改自己发布的品牌");
//        }
//        if (StringUtils.isNotEmpty(user.getFatherId())) {
//            throw new OtherExcetion("子账号无权操作");
//        }
//        brand.setStatus(BaseType.Consent.BASE.getCode());
//        return brand.updateById() ? Result.succeed("操作成功,等待审核") : Result.failure("操作失败");
        return Result.succeed("操作成功");
    }

    /**
     * 删除品牌
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        Brand brand = new Brand().getById(id);
        if(null == brand || !user.getId().equals(brand.getUserId())){
            throw new OtherExcetion("只能删除自己发布的品牌");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return brand.deleteById() ? Result.succeed("删除成功") : Result.failure("删除失败");
    }

    /**
     * 查询品牌列表
     */
    @GetMapping("search")
    public Result search() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        PageData<Brand> pageData = brandService.search(pagePars,user);
        return Result.succeed(pageData);
    }

}
