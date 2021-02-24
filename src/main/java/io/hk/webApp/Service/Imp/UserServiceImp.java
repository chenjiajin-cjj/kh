package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.framecore.Tool.Md5Help;
import io.hk.webApp.DataAccess.AuthApplySet;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Domain.AuthApply;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IUserService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.UserPasswordUpdateVO;
import io.hk.webApp.vo.UserPhoneUpdateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户
 */
@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private UserSet userSet;

    @Autowired
    private AuthApplySet authApplySet;

    @Autowired
    private ProductSet productSet;

    /**
     * 注册
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean register(User user) {
        if (StringUtils.isAnyEmpty(user.getPhone(), user.getPassword(), user.getPhoneCode())) {
            throw new OtherExcetion("请完善必填项");
        }
//        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode() + user.getPhone());
//        if (StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(user.getPhoneCode())) {
//            throw new OtherExcetion("验证码已失效");
//        }
        user.setCreateTime(System.currentTimeMillis());
        user.setStatus(BaseType.Status.YES.getCode());
        user.setRealPhone(user.getPhone());
        user.setAuth(BaseType.Status.NO.getCode());
        userSet.register(user);
        return true;
    }

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public User login(User user, String ip) {
        return user.login(ip);
    }

    /**
     * 修改密码
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean updatePassword(UserPasswordUpdateVO vo, User user) {
        if (!user.getPassword().equals(Md5Help.toMD5(vo.getOldPassword()))) {
            throw new OtherExcetion("请输入正确的原密码");
        }
        if (user.getPassword().equals(Md5Help.toMD5(vo.getNewPassword()))) {
            throw new OtherExcetion("新密码和旧密码一致");
        }
        user.setPassword(Md5Help.toMD5(vo.getNewPassword()));
        return user.updateById();
    }

    /**
     * 修改手机号
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean updatePhone(UserPhoneUpdateVO vo, User user) {
        User user1 = userSet.Where("phone=?", vo.getPhone()).First();
        if (null != user1) {
            throw new OtherExcetion("该手机已存在");
        }
        if (user.getPhone().equals(vo.getPhone())) {
            throw new OtherExcetion("新旧手机号码一致");
        }
//        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode() + vo.getPhone());
//        if (StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(vo.getPhoneCode())) {
//            throw new OtherExcetion("验证码已失效");
//        }
        user.setRealPhone(vo.getPhone());
        return user.updateById();
    }

    /**
     * 退出
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean loginout(User user) {
        user.setLoginKey(null);
        return user.updateById();
    }

    /**
     * 忘记密码
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean forget(User user) {
        if (StringUtils.isAnyEmpty(user.getPhone(), user.getPassword(), user.getPhoneCode())) {
            throw new OtherExcetion("请完善必填项");
        }
//        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode() + user.getPhone());
//        if (StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(user.getPhoneCode())) {
//            throw new OtherExcetion("验证码已失效");
//        }
        return userSet.forget(user);
    }

    /**
     * 添加子账号
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean addSon(User user) {
        User info = user.getUserByPhone(user.getPhone());
        if (null != info) {
            throw new OtherExcetion("此账号已存在");
        }
        if (StringUtils.isAnyEmpty(user.getPhone(), user.getPassword())) {
            throw new OtherExcetion("请完善必填项");
        }
//        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode() + user.getPhone());
//        if (StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(user.getPhoneCode())) {
//            throw new OtherExcetion("验证码已失效");
//        }
        user.setType(BaseType.UserType.FACTORY.getCode());
        user.setStatus(BaseType.Status.YES.getCode());
        user.setCreateTime(System.currentTimeMillis());
        user.setAuth(BaseType.Status.NO.getCode());
        userSet.register(user);
        return true;
    }

    /**
     * 查询子账号列表
     *
     * @param user     用户对象
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public Object searchSon(User user, TablePagePars pagePars) {
        List<User> list = userSet.Where("fatherId=?", user.getId()).Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
        list.forEach((a) -> {
            a.setPassword(null);
            a.setLoginKey(null);
        });
        long count = userSet.Where("fatherId=?", user.getId()).Count();
        PageData<User> pageData = new PageData<>();
        pageData.total = count;
        pageData.rows = list;
        return pageData;
    }

    /**
     * 修改子账号
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean updateSon(User user) {
        if (StringUtils.isEmpty(user.getId())) {
            throw new OtherExcetion("请选择要修改的用户");
        }
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(Md5Help.toMD5(user.getPassword()));
        }
        return user.updateById();
    }

    /**
     * 删除子账号
     *
     * @param id 用户id
     * @return
     */
    @Override
    public boolean deleteSon(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的子账号");
        }
        return userSet.Delete(id) > 0;
    }

    /**
     * 绑定手机号
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public User bindingPhone(User user) {
        if (StringUtils.isEmpty(user.getRealPhone())) {
            throw new OtherExcetion("请输入手机号");
        }
        User info = user.getUserByPhone(user.getPhone());
//        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode() + user.getRealPhone());
//        if (StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(user.getPhoneCode())) {
//            throw new OtherExcetion("验证码已失效");
//        }
        info.setRealPhone(user.getRealPhone());
        String loginKey = UUID.randomUUID().toString().replace("-", "");
        info.setLoginKey(loginKey);
        info.setLastloginTime(System.currentTimeMillis());

        return info.updateById() ? info : null;
    }

    /**
     * 用户发送认证申请
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean sendAuthApply(User user) {
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion("完善用户资料方可进行认证");
        }
        User info = userSet.Get(user.getId());
        if (null != info && BaseType.Status.YES.getCode().equals(info.getAuth())) {
            throw new OtherExcetion("已通过认证的用户无需重复操作");
        }
        AuthApply authApply = authApplySet.Where("(phone=?)and(status=?)", user.getPhone(), BaseType.Consent.PASS.getCode()).First();
        if (null != authApply) {
            throw new OtherExcetion("已通过审核的不能修改");
        }
        authApply = authApplySet.Where("(phone=?)and(status=?)", user.getPhone(), BaseType.Consent.BASE.getCode()).First();
        if (null != authApply) {
            throw new OtherExcetion("已在审核中，无需重复提交");
        }
        authApply = new AuthApply();
        authApply.setCompany(user.getCompanyName());
        authApply.setPhone(user.getPhone());
        authApply.setCreateTime(System.currentTimeMillis());
        authApply.setStatus(BaseType.Consent.BASE.getCode());
        authApplySet.Add(authApply);
        return true;
    }

    /**
     * 修改基本设置
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean updateUserMsg(User user) {
        List<Product> products;
        if (BaseType.UserType.FACTORY.getCode().equals(user.getType())) {
            products = productSet.Where("factoryId=?", user.getId()).ToList();
        } else {
            products = productSet.Where("salerId=?", user.getId()).ToList();
        }
        products.forEach((a) -> {
            a.setCname(StringUtils.isEmpty(user.getCompanyName()) ? "未知" : user.getCompanyName());
        });
        return user.updateById();
    }
}
