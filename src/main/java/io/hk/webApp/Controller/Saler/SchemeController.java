package io.hk.webApp.Controller.Saler;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Scheme;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.ISchemeService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 经销商方案
 */
@RestController("salerSchemeController")
@RequestMapping("api/v1/Saler/Scheme/")
public class SchemeController {

    @Autowired
    private ISchemeService schemeService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 新建方案
     */
    @PostMapping("add")
    public Result add(@RequestBody Scheme scheme) {
        User user = systemUtil.getUser(httpServletRequest);
        if (null != user) {
            scheme.setSalerId(user.getId());
        }
        return schemeService.add(scheme) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询经销商自己的方案
     */
    @GetMapping("search")
    public Result search() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(schemeService.search(user.getId(), pagePars));
    }

    /**
     * 添加商品到方案
     */
    @PostMapping("addProduct")
    public Result addProduct(@RequestBody SchemeAddProductVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return schemeService.addProduct(vo, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 重命名方案
     */
    @PostMapping("rename")
    public Result rename(@RequestBody Scheme scheme) {
        User user = systemUtil.getUser(httpServletRequest);
        return schemeService.rename(scheme, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 结束方案/删除方案
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        return schemeService.delete(id) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询邀请报价页面的合作厂商信息
     */
    @GetMapping("searchFactorys")
    public Result searchFactorys(String schemeId) {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(schemeService.searchFactorys(user.getId(), pagePars, schemeId));
    }

    /**
     * 邀请厂商报价
     */
    @PostMapping("quotation")
    public Result quotation(@RequestBody SchemeQuotationVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isAnyEmpty(user.getAddr(), user.getProvince(), user.getCompanyName())) {
            throw new OtherExcetion("请完善用户资料");
        }
        return schemeService.quotation(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询方案详情
     */
    @GetMapping("getDetails")
    public Result getDetails(String schemeId) {
        return Result.succeed(schemeService.getDetails(schemeId));
    }

    /**
     * 查询供应商提报的方案
     */
    @GetMapping("searchFactorySchemes")
    public Result searchFactorySchemes() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(schemeService.searchFactorySchemes(user.getId(), pagePars));
    }

    /**
     * 查询供应商提报的方案详情
     */
    @GetMapping("getFactorySchemeDetails")
    public Result getFactorySchemeDetails(String factorySchemeId) {
        return Result.succeed(schemeService.getFactorySchemeDetails(factorySchemeId));
    }

    /**
     * 删除提报商品管理的方案
     */
    @DeleteMapping("deleteFactoryScheme")
    public Result deleteFactoryScheme(String factorySchemeId) {
        return schemeService.deleteFactoryScheme(factorySchemeId) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改价格
     */
    @PostMapping("updateMoneyForAll")
    public Result updateMoneyForAll(@RequestBody SalerUpdateMoneyVO vo) {
        return schemeService.updateMoneyForAll(vo) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通知供应商来提报商品
     */
    @PostMapping("inform")
    public Result inform(@RequestBody SchemeInformVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return schemeService.inform(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
}
