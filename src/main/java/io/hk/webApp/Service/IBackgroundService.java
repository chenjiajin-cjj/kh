package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;

public interface IBackgroundService {
    /**
     * 获取所有品牌
     * @return
     */
    PageData<Brand> getAllBrand( TablePagePars pagePars);

    /**
     * 修改品牌
     * @param brand1
     * @return
     */
    boolean update(Brand brand1);

    /**
     * 审核品牌
     * @param brand1
     * @return
     */
    boolean audit(Brand brand1);

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    Object login(Admin admin);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    boolean addAdmin(Admin admin);

    /**
     * 展示管理员列表
     * @return
     */
    PageData<Admin> searchAdmin();

    /**
     * 删除某个管理员
     * @param id
     * @return
     */
    boolean deleteAdmin(String id,String adminId);

    /**
     * 禁用/启用管理员
     * @param id
     * @return
     */
    boolean updateStatus(String id,String adminId);

    /**
     * 展示用户列表
     * @param pagePars
     * @return
     */
    PageData<User> searchUser(TablePagePars pagePars);

    /**
     * 禁用/启用用户
     * @param id
     * @return
     */
    boolean updateUserStatus(String id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean addUser(User user);
}
