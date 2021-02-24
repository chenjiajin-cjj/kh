package io.hk.webApp.Service;

import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.UserPasswordUpdateVO;
import io.hk.webApp.vo.UserPhoneUpdateVO;

public interface IUserService {
    /**
     * 注册
     *
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 登录
     *
     * @param user
     * @return
     */
    User login(User user, String ip);

    /**
     * 修改密码
     *
     * @param vo
     * @return
     */
    boolean updatePassword(UserPasswordUpdateVO vo, User user);

    /**
     * 修改手机号
     *
     * @param vo
     * @param user
     * @return
     */
    boolean updatePhone(UserPhoneUpdateVO vo, User user);

    /**
     * 退出
     *
     * @param user
     * @return
     */
    boolean loginout(User user);

    /**
     * 忘记密码
     *
     * @param user
     * @return
     */
    boolean forget(User user);

    /**
     * 添加子账号
     *
     * @param user
     * @return
     */
    boolean addSon(User user);

    /**
     * 查询子账号列表
     *
     * @param user
     * @param pagePars
     * @return
     */
    Object searchSon(User user, TablePagePars pagePars);

    /**
     * 修改子账号
     *
     * @param user
     * @return
     */
    boolean updateSon(User user);

    /**
     * 删除子账号
     *
     * @param id
     * @return
     */
    boolean deleteSon(String id);

    /**
     * 绑定手机号
     *
     * @param user
     * @return
     */
    User bindingPhone(User user);

    /**
     * 用户发送认证申请
     *
     * @param authApply
     * @param user
     * @return
     */
    boolean sendAuthApply(User user);

    /**
     * 修改基本设置
     * @param user
     * @return
     */
    boolean updateUserMsg(User user);
}
