package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Dynamic;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IDynamicService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.DynamicDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 供应商动态
 */
@RestController
@RequestMapping("api/v1/Factory/Dynamic/")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 供应商发布动态
     *
     * @param dynamic
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody Dynamic dynamic) {
        User user = systemUtil.getUser(httpServletRequest);
        if (!BaseType.Status.YES.getCode().equals(user.getAuth())) {
            return Result.failure("完成认证申请通过后才可发布");
        }
        dynamic.setFactoryId(user.getId());
        return dynamicService.add(dynamic) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }

    /**
     * 供应商修改动态
     *
     * @param dynamic
     * @return
     */
    @PostMapping("modify")
    public Result modify(@RequestBody Dynamic dynamic) {
        User user = systemUtil.getUser(httpServletRequest);
        if (null != user) {
            dynamic.setFactoryId(user.getId());
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        if (user.getId().equals(dynamic.getFactoryId())) {
            return dynamic.updateDynamic() ? Result.succeed("修改成功") : Result.failure("修改失败");
        }

        return Result.failure("修改失败");
    }

    /**
     * 供应商删除自己的动态
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public Result deleteById(String id) {
        Dynamic dynamic = new Dynamic().getById(id);
        User user = systemUtil.getUser(httpServletRequest);
        if (null != user) {
            dynamic.setFactoryId(user.getId());
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        if (user.getId().equals(dynamic.getFactoryId())) {
            return dynamic.deleteById() ? Result.succeed("删除成功") : Result.failure("删除失败");
        }
        return Result.failure("删除失败");
    }

    /**
     * 查询单个动态
     *
     * @param id
     * @return
     */
    @GetMapping("getById")
    public Result getById(String id) {
        return Result.succeed(new Dynamic().getById(id));
    }

    /**
     * 查询供应商发布的动态
     *
     * @return
     */
    @GetMapping("search")
    public Result search() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        PageData<DynamicDTO> pageData = dynamicService.search(pagePars, user);
        return Result.succeed(pageData);
    }
}
