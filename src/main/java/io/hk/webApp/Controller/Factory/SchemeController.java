package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IFactorySchemeService;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.FactorySchemeSubmissionVO;
import io.hk.webApp.vo.FactorySchemeUpdateMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 供应商方案
 */
@RestController("factorySchemeController")
@RequestMapping("api/v1/Factory/Scheme/")
public class SchemeController {
    @Autowired
    private IFactorySchemeService factorySchemeService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 展示方案列表
     */
    @GetMapping("search")
    public Result search() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(factorySchemeService.search(user.getId(), pagePars));
    }

    /**
     * 删除方案
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        return factorySchemeService.delete(id, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 提报
     */
    @PostMapping("submission")
    public Result submission(@RequestBody FactorySchemeSubmissionVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return factorySchemeService.submission(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询方案详情
     */
    @GetMapping("getDetails")
    public Result getDetails(String factorySchemeId) {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(factorySchemeService.getDetails(factorySchemeId, pagePars));
    }

    /**
     * 修改商品价格
     */
    @PostMapping("updateMoney")
    public Result updateMoney(@RequestBody FactorySchemeUpdateMoneyVO vo) {
        return factorySchemeService.updateMoney(vo) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
}
