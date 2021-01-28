package io.hk.webApp.Controller.Saler;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.FriendApply;
import io.hk.webApp.Domain.Friends;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IFriendsService;
import io.hk.webApp.Service.ISaleGoodsService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.FactoryFriendsDTO;
import io.hk.webApp.vo.CustomerBatchDeleteVO;
import io.hk.webApp.vo.FriendsQuotesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 经销商的厂商列表
 */
@RestController
@RequestMapping("api/v1/Saler/SalerFactory/")
public class SalerFriendsController {

    @Autowired
    private IFriendsService friendsService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private ISaleGoodsService iSaleGoodsService;


    /**
     * 展示待审核的供应商
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
            throw new OtherExcetion(-10, "完善用户资料后方可进行操作");
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
        User user = systemUtil.getUser(httpServletRequest);
        if (vo.getIds().length != 1) {
            throw new OtherExcetion("暂时只支持一个一个解除合作");
        }
        return friendsService.salerDelete(vo.getIds()[0],user) ? Result.succeed("操作成功") : Result.failure("操作失败");
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
            throw new OtherExcetion("请选择要修改的供应商");
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
     * 设为/取消核心
     */
    @GetMapping("kernel")
    public Result kernel(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        return friendsService.kernel(id, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询合作厂商的分享给经销商的商品
     */
    @GetMapping("searchProducts")
    public Result searchProducts(String factoryId) {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(iSaleGoodsService.searchProducts(factoryId, user, pagePars));
    }


    /**
     * 一键索要报价
     */
    @PostMapping("quotes")
    public Result quotes(@RequestBody FriendsQuotesVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return friendsService.quotes(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }


}
