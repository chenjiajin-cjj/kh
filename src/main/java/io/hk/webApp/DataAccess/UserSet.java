package io.hk.webApp.DataAccess;

import io.framecore.Tool.Md5Help;
import io.hk.webApp.Domain.Auth;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import io.framecore.Aop.Holder;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.User;

import java.util.Date;
import java.util.UUID;


@Repository(value = "UserSet")
@Scope(value = "prototype")
public class UserSet extends Set<User> {

    @Autowired
    private AuthSet authSet;

    public UserSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    public boolean register(User user){
        User user1 = user.getUserByPhone(user.getPhone());
        if(null != user1){
            throw new OtherExcetion("用户已存在");
        }
        if(StringUtils.isEmpty(user.getType())){
            user.setType(BaseType.UserType.SALER.getCode());
        }
        user.setPassword(Md5Help.toMD5(user.getPassword()));
        user.setPhoneCode(null);
        this.Add(user);
        return true;
    }

    /**
     * 忘记密码
     * @param user
     * @return
     */
    public boolean forget(User user) {
        User user1 = user.getUserByPhone(user.getPhone());
        if(null == user1){
            throw new OtherExcetion("用户不存在");
        }
        user1.setPassword(Md5Help.toMD5(user.getPassword()));
        return this.Update(user1.getId(),user1) > 0;
    }
}
