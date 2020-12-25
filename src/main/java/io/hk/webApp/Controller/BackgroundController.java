package io.hk.webApp.Controller;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IBackgroundService;
import io.hk.webApp.Tools.AccessToken;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/background")
public class BackgroundController {

    @Autowired
    private IBackgroundService backgroundService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 获取所有品牌
     *
     * @return
     */
    @GetMapping("getAllBrand")
    public Result getAllBrand() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.getAllBrand(pagePars));
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
        return backgroundService.audit(brand1) ? Result.succeed("操作成功") : Result.failure("操作失败");
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
        return backgroundService.audit(brand1) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 后台管理员登录
     */
    @PostMapping("login")
    @AccessToken(Token = false)
    public Result login(@RequestBody Admin admin){
        return Result.succeed(backgroundService.login(admin));
    }

    /**
     * 添加管理员
     */
    @PostMapping("addAdmin")
    public Result addAdmin(@RequestBody Admin admin) {
        return backgroundService.addAdmin(admin) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示管理员列表
     */
    @GetMapping("searchAdmin")
    public Result searchAdmin() {
        return Result.succeed(backgroundService.searchAdmin());
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("deleteAdmin")
    public Result deleteAdmin(String id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.deleteAdmin(id,admin.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 禁用/启用管理员
     */
    @GetMapping("updateAdminStatus")
    public Result updateAdminStatus(String id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateStatus(id,admin.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示用户列表
     */
    @GetMapping("searchUser")
    public Result searchUser() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchUser(pagePars));
    }
    /**
     * 禁用启用用户
     */
    @GetMapping("updateUserStatus")
    public Result updateUserStatus(String id){
        return backgroundService.updateUserStatus(id) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 添加用户
     */
    @PostMapping("addUser")
    public Result addUser(@RequestBody User user){
        return backgroundService.addUser(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
}
