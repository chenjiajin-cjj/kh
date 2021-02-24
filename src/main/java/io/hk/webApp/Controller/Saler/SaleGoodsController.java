package io.hk.webApp.Controller.Saler;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Domain.SaleGoods;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.ISaleGoodsService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 经销商的商品库
 */
@RestController
@RequestMapping("api/v1/Saler/SaleGoods/")
public class SaleGoodsController {

    @Autowired
    private ISaleGoodsService saleGoodsService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;


    /**
     * 获得由供应商分享过来的商品
     */
    @PostMapping("addShareProduct")
    public Result addShareProduct(@RequestBody ShareProductAddVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return saleGoodsService.add(vo, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 经销商添加自有商品
     */
    @PostMapping("addProduct")
    public Result addProduct(@RequestBody Product product) {
        User user = systemUtil.getUser(httpServletRequest);
        if(StringUtils.isEmpty(user.getCompanyName())){
            throw new OtherExcetion(-10,"完成基本设置后可操作");
        }
        product.setCname(user.getCompanyName());
        return saleGoodsService.addProduct(product) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }

    /**
     * 查新商品列表
     *
     * @param type
     * @return
     */
    @GetMapping("search")
    public Result search(String type) {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        pagePars.Pars.put("illegal", BaseType.Status.YES.getCode());
        PageData<SaleGoods> pageData = saleGoodsService.search(pagePars, user, type);
        return Result.succeed(pageData);
    }

    /**
     * 上下架自有商品
     */
    @PostMapping("onlineOrNo")
    public Result onlineOrno(@RequestBody SaleGoodsOnlineOrNoVO vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            throw new OtherExcetion("请选择要操作的商品");
        }
        User user = systemUtil.getUser(httpServletRequest);
        return saleGoodsService.onlineOrNo(vo, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改自有商品
     */
    @PostMapping("update")
    public Result onlineOrno(@RequestBody SaleGoodsUpdateProductVO vo) {
        if (StringUtils.isEmpty(vo.getSaleGoodsId())) {
            throw new OtherExcetion("请选择要操作的商品");
        }
        User user = systemUtil.getUser(httpServletRequest);
        return saleGoodsService.update(vo, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 删除自有商品
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的商品");
        }
        User user = systemUtil.getUser(httpServletRequest);
        return saleGoodsService.delete(id, user.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据id查询自有商品
     */
    @GetMapping("getById")
    public Result getById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要查询的商品");
        }
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(saleGoodsService.getById(id,user));
    }


    /**
     * 设为主推商品
     */
    @GetMapping("recommend")
    public Result recommend(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要设置的商品");
        }
        return saleGoodsService.recommend(id) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }


    /**
     * 查询所有合作厂商通过审核且未过期的品牌
     */
//    @GetMapping("searchBrandForFactory")
//    public Result searchBrandForFactory(){
//        User user = systemUtil.getUser(httpServletRequest);
//        return Result.succeed(saleGoodsService.searchBrandForFactory(user.getId()));
//    }

    /**
     * 获取合作厂商的品牌 不重复
     */
    @GetMapping("getBrandForFriends")
    public Result getBrandForFriends() {
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(saleGoodsService.getBrandForFriends(user.getId()));
    }


    /**
     * 展示分享的商品列表
     */
    @GetMapping("searchShare")
    public Result searchShare() {
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(saleGoodsService.searchShare(pagePars, user.getId()));
    }

    /**
     * 查询分享的商品的详情
     */
    @GetMapping("searchShareDetails")
    public Result searchShareDetails(String shareProductId) {
        return Result.succeed(saleGoodsService.searchShareDetails(shareProductId));
    }

    /**
     * 添加商品到选品库
     */
    @PostMapping("addToChoose")
    public Result addToChoose(@RequestBody AddToChooseVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return saleGoodsService.addToChoose(vo,user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询选品库
     */
    @GetMapping("searchChoose")
    public Result searchChoose() {
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(saleGoodsService.searchChoose(user.getId()));
    }

    /**
     * 删除选品库的商品
     */
    @PostMapping("deleteChoose")
    public Result deleteChoose(@RequestBody DeleteChooseVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return saleGoodsService.deleteChoose(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

}
