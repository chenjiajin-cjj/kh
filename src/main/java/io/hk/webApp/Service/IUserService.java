package io.hk.webApp.Service;

import io.hk.webApp.Domain.User;
import io.hk.webApp.vo.UserPasswordUpdateVO;
import io.hk.webApp.vo.UserPhoneUpdateVO;

public interface IUserService {
    /**
     * 注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 修改密码
     * @param vo
     * @return
     */
    boolean updatePassword(UserPasswordUpdateVO vo,User user);

    /**
     * 修改手机号
     * @param vo
     * @param user
     * @return
     */
    boolean updatePhone(UserPhoneUpdateVO vo, User user);

    /**
     * 退出
     * @param user
     * @return
     */
    boolean loginout(User user);

    /**
     * 忘记密码
     * @param user
     * @return
     */
    boolean forget(User user);
}
