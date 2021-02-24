package io.hk.webApp.Controller;

import com.alibaba.fastjson.JSONObject;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IBackgroundService;
import io.hk.webApp.Service.IClassifyService;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.vo.*;
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

    @Autowired
    private IClassifyService iClassifyService;

    @Autowired
    private IProductService iProductService;

    /**
     * 获取待审核的所有品牌
     *
     * @return
     */
    @GetMapping("getAllUnCheckBrand")
    public Result getAllUnCheckBrand() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.getAllUnCheckBrand(pagePars));
    }

    /**
     * 审核品牌
     */
    @PostMapping("pass")
    public Result pass(@RequestBody Brand brand) {
        if (StringUtils.isEmpty(brand.getId())) {
            throw new OtherExcetion("请选择要审核通过的品牌");
        }
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.audit(brand, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 新增品牌
     */
    @PostMapping("addSystemBrand")
    public Result addSystemBrand(@RequestBody BrandSystem brandSystem) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.addSystemBrand(admin, brandSystem, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询系统品牌列表
     */
    @GetMapping("searchSystemBrands")
    public Result searchSystemBrands() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchSystemBrands(pagePars));
    }

    /**
     * 修改品牌
     */
    @PostMapping("updateSystemBrand")
    public Result updateSystemBrand(@RequestBody BrandSystem brandSystem) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateSystemBrand(admin, brandSystem, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 删除品牌
     */
    @DeleteMapping("deleteSystemBrand")
    public Result deleteSystemBrand(String _id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.deleteSystemBrand(admin, _id, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
//
//    /**
//     * 添加用户
//     */
//    @PostMapping("addUser")
//    public Result addUser(@RequestBody User user){
//        return backgroundService.addUser(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
//    }
//

    /**
     * 后台管理员登录
     */
    @PostMapping("login")
    @AccessToken(Token = false)
    public Result login(@RequestBody Admin admin) {
        return Result.succeed(backgroundService.login(admin, httpServletRequest.getRemoteAddr()));
    }

    /**
     * 获取系统首页所有数据
     */
    @GetMapping("searchSystemData")
    public Result searchSystemData(Long userStartTime, Long userEndsTime, Long schemeStartTime, Long schemeEndTime) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return Result.succeed(backgroundService.searchSystemData(admin, userStartTime, userEndsTime, schemeStartTime, schemeEndTime));
    }

    /**
     * 修改当前登录的管理员密码
     */
    @PostMapping("updateAdminPassword")
    public Result updateAdmin(@RequestBody Admin admin) {
        Admin admin1 = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateAdmin(admin, admin1, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
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
    public Result updateUserStatus(String id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateUserStatus(id, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询用户详情
     */
    @GetMapping("getUserDeatils")
    public Result getUserDeatils(String id) {
        return Result.succeed(backgroundService.getUserDeatils(id));
    }

    /**
     * 直接修改用户密码
     */
    @PostMapping("updateUserPasswordByUserId")
    public Result updateUserPasswordByUserId(@RequestBody User user) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateUserPasswordByUserId(user, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示认证列表
     */
    @GetMapping("searchAuthApplyList")
    public Result searchAuthApplyList() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchAuthApplyList(pagePars));
    }

    /**
     * 通过认证
     */
    @PostMapping("passAuth")
    public Result passAuth(@RequestBody PassAuthVO vo) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.passAuth(vo, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 删除申请记录
     */
    @PostMapping("deleteAuthApply")
    public Result deleteAuthApply(@RequestBody String json) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        String[] ids = JSONObject.parseObject(json).getObject("ids", String[].class);
        return backgroundService.deleteAuthApply(ids, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 发送站内消息
     */
    @PostMapping("sendSystemMessage")
    public Result sendSystemMessage(@RequestBody SendSystemMessageVO vo) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.sendSystemMessage(admin, vo, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询登录日志
     */
    @GetMapping("searchLoginLog")
    public Result searchLoginLog() {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchLoginLog(admin, pagePars));
    }

    /**
     * 获取平台设置
     */
    @GetMapping("getPlatform")
    public Result getPlatform() {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return Result.succeed(backgroundService.getPlatform(admin));
    }

    /**
     * 修改平台设置
     */
    @PostMapping("updatePlatform")
    public Result updatePlatform(@RequestBody Platform platform) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updatePlatform(admin, platform, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 添加管理员
     */
    @PostMapping("addAdmin")
    public Result addAdmin(@RequestBody Admin admin) {
        Admin admin1 = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.addAdmin(admin, httpServletRequest.getRemoteAddr(), admin1) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 添加管理员
     */
    @PostMapping("updateAdminMsg")
    public Result updateAdminMsg(@RequestBody Admin admin) {
        Admin admin1 = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateAdminMsg(admin, httpServletRequest.getRemoteAddr(), admin1) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示管理员列表
     */
    @GetMapping("searchAdmin")
    public Result searchAdmin() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchAdmin(pagePars));
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("deleteAdmin")
    public Result deleteAdmin(String id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.deleteAdmin(id, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 禁用/启用管理员
     */
    @GetMapping("updateAdminStatus")
    public Result updateAdminStatus(String id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateStatus(id, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询操作记录
     */
    @GetMapping("searchOperationLog")
    public Result searchOperationLog() {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchOperationLog(admin, pagePars));
    }


    /**
     * 清除操作记录
     */
    @PostMapping("deleteOperationLog")
    public Result deleteOperationLog(@RequestBody String json) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        String type = JSONObject.parseObject(json).getString("type");
        DeleteOperationVO vo = new DeleteOperationVO();
        long time = System.currentTimeMillis();
        switch (type) {
            case "week": {
                vo.setEndTime(time - 7L * 86400000);
                break;
            }
            case "month": {
                vo.setEndTime(time - 30L * 86400000);
                break;
            }
            case "threeMonth": {
                vo.setEndTime(time - 90L * 86400000);
                break;
            }
            case "halfYear": {
                vo.setEndTime(time - 180L * 86400000);
                break;
            }
            case "year": {
                vo.setEndTime(time - 365L * 86400000);
                break;
            }
        }
        return backgroundService.deleteOperationLog(vo, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 清除操作记录
     */
    @PostMapping("batchOperationLog")
    public Result batchOperationLog(@RequestBody String json) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        String[] ids = JSONObject.parseObject(json).getObject("ids", String[].class);
        return backgroundService.batchOperationLog(ids, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询站内信消息
     */
    @GetMapping("searchMessage")
    public Result searchMessage() {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(backgroundService.searchMessage(admin, pagePars));
    }

    /**
     * 删除站内信消息
     */
    @PostMapping("deleteSystemMessage")
    public Result deleteSystemMessage(@RequestBody String json) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        String[] ids = JSONObject.parseObject(json).getObject("ids", String[].class);
        return backgroundService.deleteSystemMessage(ids, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 退出登录
     */
    @GetMapping("loginOut")
    public Result loginOut() {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.loginOut(admin) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 添加分类
     */
    @PostMapping("addClassify")
    public Result addClassify(@RequestBody Classify classify) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iClassifyService.addClassify(classify, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.succeed("操作失败");
    }

    /**
     * 修改分类
     */
    @PostMapping("updateClassify")
    public Result updateClassify(@RequestBody Classify classify) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iClassifyService.updateClassify(classify, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.succeed("操作失败");
    }

    /**
     * 查询分类
     */
    @GetMapping("searchClassifyOne")
    public Result searchClassifyOne(String type, String fatherId) {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(iClassifyService.searchClassifyOne(pagePars, type, fatherId));
    }

    /**
     * 删除分类
     */
    @DeleteMapping("deleteClassify")
    public Result deleteClassify(String id) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iClassifyService.deleteClassify(admin, httpServletRequest.getRemoteAddr(), id) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 用户导出到excel
     */
    @PostMapping("exportUsers")
    public Result exportUsers(@RequestBody ExportUsersVO vo) {
        if (StringUtils.isEmpty(vo.getType())) {
            throw new OtherExcetion("请选择导出类型");
        }
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return Result.succeed(backgroundService.exportUsers(vo, admin, httpServletRequest.getRemoteAddr()));
    }

    /**
     * 后台查询商品
     */
    @GetMapping("searchProducts")
    public Result searchProducts() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(iProductService.searchBackGroupProducts(pagePars));
    }

    /**
     * 违规上下架
     */
    @PostMapping("illegal")
    public Result illegal(@RequestBody Product product) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iProductService.illegal(product, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改商品
     */
    @PostMapping("updateProduct")
    public Result updateProduct(@RequestBody Product product) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iProductService.update(product, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改后台商品上架
     */
    @PostMapping("updateProductOnline")
    public Result updateProductOnline(@RequestBody Product product) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iProductService.updateProductOnline(product, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改商品标签
     */
    @PostMapping("updateProductTag")
    public Result updateProductTag(@RequestBody UpdateProductTagVO vo) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return iProductService.updateProductTag(vo, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量启用用户
     */
    @PostMapping("batchOnlineUsers")
    public Result batchOnlineUsers(@RequestBody String json) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        String[] ids = JSONObject.parseObject(json).getObject("ids", String[].class);
        return backgroundService.batchlineUsers(admin, ids, httpServletRequest.getRemoteAddr(), BaseType.Status.YES.getCode()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量停用用户
     */
    @PostMapping("batchDownlineUsers")
    public Result batchDownlineUsers(@RequestBody String json) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        String[] ids = JSONObject.parseObject(json).getObject("ids", String[].class);
        return backgroundService.batchlineUsers(admin, ids, httpServletRequest.getRemoteAddr(), BaseType.Status.NO.getCode()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询消息设置
     */
    @GetMapping("searchMessageConf")
    public Result searchMessageConf() {
        return Result.succeed(backgroundService.searchMessageConf());
    }

    /**
     * 站内消息是否发送
     */
    @PostMapping("updateMessageConfInstation")
    public Result updateMessageConfInstation(@RequestBody BaseVO vo) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateMessageConfSendOrNo(1, vo.getId(), admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 小程序消息是否发送
     */
    @PostMapping("updateMessageConfSmall")
    public Result updateMessageConfSmall(@RequestBody BaseVO vo) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateMessageConfSendOrNo(0, vo.getId(), admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑站内消息内容
     */
    @PostMapping("updateMessageConfInstationContext")
    public Result updateMessageConfInstationContext(@RequestBody MessageConf messageConf) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateMessageConfInstationContext(1, messageConf, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑小程序消息内容
     */
    @PostMapping("updateMessageConfSmallContext")
    public Result updateMessageConfSmallContext(@RequestBody MessageConf messageConf) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.updateMessageConfInstationContext(0, messageConf, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据用户手机号发送消息
     */
    @PostMapping("sendMessageForUsersPhone")
    public Result sendMessageForUsersPhone(@RequestBody SendSystemMessageVO vo) {
        Admin admin = systemUtil.getAdmin(httpServletRequest);
        return backgroundService.sendMessageForUsersPhone(vo, admin, httpServletRequest.getRemoteAddr()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询商品头部的商品数量
     */
    @GetMapping("searchHeadProductsNumber")
    public Result searchHeadProductsNumber() {
        return Result.succeed(backgroundService.searchHeadProductsNumber());
    }
}
