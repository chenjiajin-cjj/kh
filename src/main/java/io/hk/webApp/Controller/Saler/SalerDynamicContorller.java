package io.hk.webApp.Controller.Saler;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.DataAccess.FriendsSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IFriendsService;
import io.hk.webApp.Service.IDynamicService;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Service.IUserService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/Saler/Dynamic/")
public class SalerDynamicContorller {


    @Autowired
    private IDynamicService dynamicService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private IFriendsService friendsService;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private IProductService productService;

    /**
     * 查询动态
     */
    @GetMapping("search")
    public Result search(String type) {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(dynamicService.search(pagePars, type, user.getId()));
    }

    /**
     * 经销商向供应商请求合作
     */
    @PostMapping("cooperation")
    public Result cooperation(@RequestBody FriendApply friendApply) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isAnyEmpty(user.getTel(), user.getName(), user.getCompanyName(), user.getProvince(), user.getAddr())) {
            throw new OtherExcetion(-10,"完善用户资料后方可进行操作");
        }
        friendApply.setApply("无");
        return friendsService.cooperation(friendApply, user, BaseType.UserType.SALER.getCode()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 厂商动态的商品列表
     */
    @GetMapping("getFactoryProducts")
    public Result getFactoryProducts(String factoryId) {
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        User factory = new User().getById(factoryId);
        PageData<Product> pageData = productService.search(pagePars, factory);
        return Result.succeed(pageData);
    }

    /**
     * 厂商动态的列表查询商品页的品牌和分组
     */
    @GetMapping("getProutBrandAndGroup")
    public Result getProutBrandAndGroup(String factoryId) {
        Map<String, Object> map = new HashMap<>();
        List<Brand> list = productService.searchBrand(factoryId);
        List<Brand> brandList = new ArrayList<>();
        list.forEach((a) -> {
            if ("1".equals(a.getPerpetual()) || a.getTime() >= System.currentTimeMillis()) {
                brandList.add(a);
            }
        });
        map.put("brand", brandList);
        map.put("category", productService.searchGroups(factoryId));
        {
            List<Map<String, String>> tagList = new ArrayList<>();
            Map<String, String> tag = new HashMap<>();
            tag.put("name", "推荐");
            tag.put("code", "tagRec");
            tagList.add(tag);
            tag = new HashMap<>();
            tag.put("name", "热销");
            tag.put("code", "tagHot");
            tagList.add(tag);
            tag = new HashMap<>();
            tag.put("name", "新品");
            tag.put("code", "tagNew");
            tagList.add(tag);
            map.put("tag", tagList);
        }
        return Result.succeed(map);
    }

    /**
     * 厂商信息
     */
    @GetMapping("getFactoryMsg")
    public Result getFactoryMsg(String factoryId) {
        return Result.succeed(dynamicService.getUserMsg(factoryId));
    }
}
