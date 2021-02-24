package io.hk.webApp.Controller;

import io.framecore.Frame.Result;
import io.hk.webApp.DataAccess.AuthSet;
import io.hk.webApp.DataAccess.PhoneMsgSet;
import io.hk.webApp.Domain.Auth;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IUserService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.vo.UserPasswordUpdateVO;
import io.hk.webApp.vo.UserPhoneUpdateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/User")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private AuthSet authSet;

    @Autowired
    PhoneMsgSet phoneMsgSet;

    /**
     * 发送验证码,带签名，防刷
     */
    @RequestMapping("getValidCode")
    @AccessToken(Token = false)
    public Result getValidCode(String phone, String sign) {

//		if(!sign.equals(Md5Help.toMD5("Index.sendValidCode.58fdjj*^YGjy6Q"+phone)))
//		{
//			return Result.failure("发送验证码失败，签名错误");
//		}
//		if(CacheHelp.get("Index.sendValidCode.ex:"+phone)!=null)
//		{
//			return Result.failure("发送验证码失败， 60秒内不能重复发送");
//		}
        if (StringUtils.isEmpty(phone)) {
            throw new OtherExcetion("请输入手机号");
        }
        String validCode = String.valueOf((int) (Math.random() * 9 + 1) * 100000);
//        CacheHelp.set(BaseType.Code.PHONE_CODE.getCode() + phone, validCode, 5 * 60); //5分钟过期
//        CacheHelp.set(BaseType.Code.PHONE_CODE_EX.getCode() + phone, validCode, 53); // 1分钟内不能重复发送。
        phoneMsgSet.addValid(phone, validCode);

        System.out.println("验证码：" + validCode);
        return Result.succeed(validCode);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    @AccessToken(Token = false)
    public Result register(@RequestBody User user) {
        return iUserService.register(user) ? Result.succeed("注册成功") : Result.failure("注册失败");
    }

    /**
     * 登录
     */
    @PostMapping("login")
    @AccessToken(Token = false)
    public Result login(@RequestBody User user) {
        if (StringUtils.isAnyEmpty(user.getPhone(), user.getPassword())) {
            throw new OtherExcetion("请完善必填项");
        }
        User us = iUserService.login(user, httpServletRequest.getRemoteAddr());
        Map<String, String> map = new HashMap<>();
        if (null != us) {
            map.put("loginKey", us.getLoginKey());
            map.put("type", StringUtils.isEmpty(us.getFatherId()) ? us.getType() : BaseType.UserType.SON.getCode());
            map.put("name", us.getName());
            map.put("img", us.getImg());
            char[] args = us.getPhone().toCharArray();
            if (args.length > 7) {
                args[3] = '*';
                args[4] = '*';
                args[5] = '*';
                args[6] = '*';
            }
            map.put("phone", new String(args));
            return Result.succeed(map);
        }
        return Result.failure("登录失败！");
    }

    /**
     * 忘记密码
     */
    @PostMapping("forgetPassword")
    @AccessToken(Token = false)
    public Result forgetPassword(@RequestBody User user) {
        return iUserService.forget(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询账号认证
     */
    @GetMapping("getAuth")
    public Result getAuth() {
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(new Auth().getById(user.getId()));
    }

    /**
     * 修改账号认证资料
     */
    @PostMapping("updateAuthById")
    public Result updateAuthById(@RequestBody Auth auth) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号没有权限操作");
        }
        Auth auth1 = authSet.Get(auth.getId());
        if (null == auth1 || !user.getId().equals(auth1.getUserId())) {
            throw new OtherExcetion("无法操作");
        }
        auth.setUserId(user.getId());
        return auth.updateById() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询基本设置
     */
    @GetMapping("getUserMsg")
    public Result getUserMsg() {
        User user = systemUtil.getUser(httpServletRequest);
        if (!BaseType.Status.YES.getCode().equals(user.getStatus())) {
            throw new OtherExcetion("该账号已被封禁");
        }
//        if(StringUtils.isNotEmpty(user.getFatherId())){
//            user = user.getById(user.getFatherId());
//            user.setPassword(null);
//            user.setPhone(null);
//            return Result.succeed(user);
//        }
        user.setPassword(null);
        user.setPhone(null);
        return Result.succeed(user);
    }

    /**
     * 修改基本设置
     */
    @PostMapping("updateUserMsg")
    public Result updateUserMsg(@RequestBody User user) {
        User user1 = systemUtil.getUser(httpServletRequest);
        user.setId(user1.getId());
        user.setPassword(user1.getPassword());
        user.setPhone(user1.getPhone());
        user.setLoginKey(user1.getLoginKey());
        user.setType(user1.getType());
        return iUserService.updateUserMsg(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改密码
     */
    @PostMapping("updatePassword")
    public Result updateUserPassword(@RequestBody UserPasswordUpdateVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isAnyEmpty(vo.getOldPassword(), vo.getNewPassword())) {
            throw new OtherExcetion("请完善必填项");
        }
        return iUserService.updatePassword(vo, user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改手机
     */
    @PostMapping("updatePhone")
    public Result updateUserPhone(@RequestBody UserPhoneUpdateVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        if (StringUtils.isAnyEmpty(vo.getPhone(), vo.getPhoneCode())) {
            throw new OtherExcetion("请完善必填项");
        }
        char[] args = vo.getPhone().toCharArray();
        args[3] = '*';
        args[4] = '*';
        args[5] = '*';
        args[6] = '*';
        return iUserService.updatePhone(vo, user) ? Result.succeed(new String(args)) : Result.failure("操作失败");
    }

    /**
     * 退出
     */
    @GetMapping("loginout")
    public Result loginout() {
        User user = systemUtil.getUser(httpServletRequest);
        return iUserService.loginout(user) ? Result.succeed("登出成功") : Result.failure("登出失败");
    }

    /**
     * 添加子账号
     *
     * @param user
     * @return
     */
    @PostMapping("addSon")
    public Result addSon(@RequestBody User user) {
        User father = systemUtil.getUser(httpServletRequest);
        if (!BaseType.UserType.FACTORY.getCode().equals(father.getType())) {
            throw new OtherExcetion("供应商才能操作");
        }
        if (StringUtils.isNotEmpty(father.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        user.setFatherId(father.getId());
        return iUserService.addSon(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询子账号列表
     */
    @GetMapping("searchSon")
    public Result searchSon() {
        User user = systemUtil.getUser(httpServletRequest);
        if (!BaseType.UserType.FACTORY.getCode().equals(user.getType())) {
            throw new OtherExcetion("供应商才能操作");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(iUserService.searchSon(user, pagePars));
    }

    /**
     * 修改子账号
     */
    @PostMapping("updateSon")
    public Result updateSon(@RequestBody User user) {
        User father = systemUtil.getUser(httpServletRequest);
        if (!BaseType.UserType.FACTORY.getCode().equals(father.getType())) {
            throw new OtherExcetion("供应商才能操作");
        }
        if (StringUtils.isNotEmpty(father.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return iUserService.updateSon(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 删除子账号
     */
    @DeleteMapping("deleteSon")
    public Result deleteSon(String id) {
        User father = systemUtil.getUser(httpServletRequest);
        if (!BaseType.UserType.FACTORY.getCode().equals(father.getType())) {
            throw new OtherExcetion("供应商才能操作");
        }
        if (StringUtils.isNotEmpty(father.getFatherId())) {
            throw new OtherExcetion("子账号无权操作");
        }
        return iUserService.deleteSon(id) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 绑定手机号
     */
    @PostMapping("bindingPhone")
    @AccessToken(Token = false)
    public Result deleteSon(@RequestBody User user) {
        User us = iUserService.bindingPhone(user);
        Map<String, String> map = new HashMap<>();
        if (null != us) {
            map.put("loginKey", us.getLoginKey());
            map.put("type", StringUtils.isEmpty(us.getFatherId()) ? us.getType() : BaseType.UserType.SON.getCode());
            map.put("name", us.getName());
            map.put("img", us.getImg());
            char[] args = us.getPhone().toCharArray();
            if (args.length > 7) {
                args[3] = '*';
                args[4] = '*';
                args[5] = '*';
                args[6] = '*';
            }
            map.put("phone", new String(args));
            return Result.succeed(map);
        }
        return Result.failure();
    }

    /**
     * 用户发送认证申请
     */
    @PostMapping("sendAuthApply")
    public Result sendAuthApply() {
        User user = systemUtil.getUser(httpServletRequest);
        return iUserService.sendAuthApply(user) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }


}
