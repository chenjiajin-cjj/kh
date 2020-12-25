package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Blacklist;
import io.hk.webApp.Domain.FriendApply;
import io.hk.webApp.Domain.Friends;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IFriendsService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.FactoryFriendsDTO;
import io.hk.webApp.vo.CustomerBlackListVO;
import io.hk.webApp.vo.CustomerBatchDeleteVO;
import io.hk.webApp.vo.ProductShareVO;
import io.hk.webApp.vo.UpdatePriceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户管理
 */
@RestController
@RequestMapping("api/v1/Factory/Customer/")
public class FactoryFriendsController {


    @Autowired
    private IFriendsService friendsService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 展示客户审核列表
     *
     * @return
     */
    @GetMapping("search")
    public Result search() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(friendsService.search(pagePars, user.getId()));
    }

    /**
     * 通过审核
     */
    @PostMapping("consent")
    public Result consent(@RequestBody Friends friends) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isEmpty(friends.getId())) {
            throw new OtherExcetion("请选择要通过的客户");
        }
        if (StringUtils.isAnyEmpty(user.getTel(), user.getName(), user.getCompanyName(), user.getProvince(), user.getAddr())) {
            throw new OtherExcetion("完善用户资料后方可进行操作");
        }
        return friendsService.check(friends, BaseType.Consent.PASS.getCode(), user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }


    /**
     * 拒绝审核
     */
    @PostMapping("refuse")
    public Result refuse(@RequestBody Friends friends) {
        User user = systemUtil.getUser(httpServletRequest);

        if (StringUtils.isEmpty(friends.getId())) {
            throw new OtherExcetion("请选择要拒绝的客户");
        }
        return friendsService.check(friends, BaseType.Consent.REFUSE.getCode(), user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("delete")
    public Result delete(@RequestBody CustomerBatchDeleteVO vo) {
        return friendsService.batchDelete(vo.getIds()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询审核通过的客户列表
     */
    @GetMapping("searchForPass")
    public Result searchForPass() {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        User user = systemUtil.getUser(httpServletRequest);
        PageData<FactoryFriendsDTO> pageData = friendsService.searchForPass(pagePars, user.getId());
        return Result.succeed(pageData);
    }

    /**
     * 修改备注
     */
    @PostMapping("remark")
    public Result remark(@RequestBody Friends friends) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isEmpty(friends.getId())) {
            throw new OtherExcetion("请选择要修改的经销商");
        }
        if (StringUtils.isEmpty(friends.getRemark())) {
            throw new OtherExcetion("请输入要备注的内容");
        }
        Friends friends1 = new Friends();
        friends1.setId(friends.getId());
        friends1.setRemark(friends.getRemark());
        friends = friends.getById(friends.getId());
        if (null == friends || !user.getId().equals(friends.getUserId())) {
            throw new OtherExcetion("不可操作");
        }
        return friends1.updateById() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 添加经销商
     */
    @PostMapping("addFriend")
    public Result addFriend(@RequestBody Friends friends) {
        User user = systemUtil.getUser(httpServletRequest);
        friends.setUserId(user.getId());
        return friendsService.addFriend(friends) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 添加黑名单
     */
    @PostMapping("addBlack")
    public Result addBlackList(@RequestBody CustomerBlackListVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return friendsService.addBlackList(vo.getBlackId(), user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示黑名单列表
     */
    @GetMapping("searchBlackList")
    public Result searchBlackList() {
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(friendsService.searchBlackList(user.getId()));
    }

    /**
     * 移除黑名单
     */
    @DeleteMapping("removeBlack")
    public Result removeBlackList(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        Blacklist blacklist = new Blacklist().getById(id);
        if (null == blacklist || !user.getId().equals(blacklist.getUserId())) {
            throw new OtherExcetion("无法操作");
        }
        return blacklist.deleteById() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 供应商请求与经销商合作
     */
    @PostMapping("cooperation")
    public Result cooperation(@RequestBody FriendApply friendApply) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isAnyEmpty(user.getTel(), user.getName(), user.getCompanyName(), user.getProvince(), user.getAddr())) {
            throw new OtherExcetion("完善用户资料后方可进行操作");
        }
        return friendsService.cooperation(friendApply, user, BaseType.UserType.FACTORY.getCode()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示发送报价页的商品清单
     */
    @GetMapping("searchProducts")
    public Result searchProducts(String salerId) {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(friendsService.searchProducts(salerId, user, pagePars));
    }

    /**
     * 发送报价
     */
    @PostMapping("shareProducts")
    public Result share(@RequestBody ProductShareVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return friendsService.share(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改已发送报价的商品的价格
     */
    @PostMapping("updatePrice")
    public Result updatePrice(@RequestBody UpdatePriceVO vo){
        User user = systemUtil.getUser(httpServletRequest);
        return friendsService.updatePrice(vo,user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }
}