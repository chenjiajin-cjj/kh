package io.hk.webApp.Service.Imp;


import io.framecore.Aop.Holder;
import io.framecore.Tool.Md5Help;
import io.framecore.redis.CacheHelp;
import io.hk.webApp.DataAccess.PhoneMsgSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IUserService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.vo.UserPasswordUpdateVO;
import io.hk.webApp.vo.UserPhoneUpdateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private UserSet userSet;



    @Autowired
    private PhoneMsgSet phoneMsgSet;

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        if(StringUtils.isAnyEmpty(user.getPhone(),user.getPassword(),user.getPhoneCode())){
            throw new OtherExcetion("请完善必填项");
        }
        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode()+user.getPhone());
        if(StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(user.getPhoneCode())){
            throw new OtherExcetion("验证码已失效");
        }
        user.setStatus(BaseType.Status.YES.getCode());
        userSet.register(user);
        return true;
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return user.login();
    }

    /**
     * 修改密码
     * @param vo
     * @return
     */
    @Override
    public boolean updatePassword(UserPasswordUpdateVO vo,User user) {
        if(!user.getPassword().equals(Md5Help.toMD5(vo.getOldPassword()))){
            throw new OtherExcetion("请输入正确的原密码");
        }
        if(user.getPassword().equals(Md5Help.toMD5(vo.getNewPassword()))){
            throw new OtherExcetion("新密码和旧密码一致");
        }
        user.setPassword(Md5Help.toMD5(vo.getNewPassword()));
        return user.updateById();
    }

    /**
     * 修改手机号
     * @param vo
     * @param user
     * @return
     */
    @Override
    public boolean updatePhone(UserPhoneUpdateVO vo, User user) {
        User user1 = userSet.Where("phone=?",vo.getPhone()).First();
        if(null != user1){
            throw new OtherExcetion("该手机已存在");
        }
        if(user.getPhone().equals(vo.getPhone())){
            throw new OtherExcetion("新旧手机号码一致");
        }
        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode()+vo.getPhone());
        if(StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(vo.getPhoneCode())){
            throw new OtherExcetion("验证码已失效");
        }
        user.setPhone(vo.getPhone());
        return user.updateById();
    }

    /**
     * 退出
     * @param user
     * @return
     */
    @Override
    public boolean loginout(User user) {
        user.setLoginKey(null);
        return user.updateById();
    }

    /**
     * 忘记密码
     * @param user
     * @return
     */
    @Override
    public boolean forget(User user) {
        if(StringUtils.isAnyEmpty(user.getPhone(),user.getPassword(),user.getPhoneCode())){
            throw new OtherExcetion("请完善必填项");
        }
        String redisPhoneCode = CacheHelp.get(BaseType.Code.PHONE_CODE.getCode()+user.getPhone());
        if(StringUtils.isEmpty(redisPhoneCode) || !redisPhoneCode.equals(user.getPhoneCode())){
            throw new OtherExcetion("验证码已失效");
        }
        return userSet.forget(user);
    }
}
