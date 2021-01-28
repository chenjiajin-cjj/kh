package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.DeleteOperationVO;
import io.hk.webApp.vo.ExportUsersVO;
import io.hk.webApp.vo.SendSystemMessageVO;

public interface IBackgroundService {
    /**
     * 获取所有品牌
     *
     * @return
     */
    PageData<Brand> getAllUnCheckBrand(TablePagePars pagePars);

    /**
     * 修改品牌
     *
     * @param brand1
     * @return
     */
    boolean update(Brand brand1);

    /**
     * 审核品牌
     *
     * @param brand1
     * @return
     */
    boolean audit(Brand brand1, Admin admin, String ip);

    /**
     * 管理员登录
     *
     * @param admin
     * @return
     */
    Object login(Admin admin, String ip);

    /**
     * 添加管理员
     *
     * @param admin
     * @return
     */
    boolean addAdmin(Admin admin, String ip, Admin admin1);

    /**
     * 展示管理员列表
     *
     * @return
     */
    PageData<Admin> searchAdmin(TablePagePars pagePars);

    /**
     * 删除某个管理员
     *
     * @param id
     * @return
     */
    boolean deleteAdmin(String id, Admin admin, String ip);

    /**
     * 禁用/启用管理员
     *
     * @param id
     * @return
     */
    boolean updateStatus(String id, Admin admin1, String ip);

    /**
     * 展示用户列表
     *
     * @param pagePars
     * @return
     */
    PageData<User> searchUser(TablePagePars pagePars);

    /**
     * 禁用/启用用户
     *
     * @param id
     * @return
     */
    boolean updateUserStatus(String id, Admin admin, String ip);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    boolean addUser(User user, Admin admin, String ip);

    /**
     * 获取系统首页所有数据
     *
     * @param admin
     * @return
     */
    Object searchSystemData(Admin admin, Long userStartTime, Long userEndsTime, Long schemeStartTime, Long schemeEndTime);

    /**
     * 修改当前登录的管理员密码
     *
     * @param admin
     * @param admin1
     * @return
     */
    boolean updateAdmin(Admin admin, Admin admin1, String ip);

    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    Object getUserDeatils(String id);

    /**
     * 直接修改用户密码
     *
     * @param user
     * @return
     */
    boolean updateUserPasswordByUserId(User user, Admin admin, String ip);

    /**
     * 展示认证列表
     *
     * @return
     */
    Object searchAuthApplyList(TablePagePars pagePars);

    /**
     * 通过认证
     *
     * @param authApply
     * @return
     */
    boolean passAuth(AuthApply authApply, Admin admin, String ip);

    /**
     * 删除申请记录
     *
     * @param id
     * @return
     */
    boolean deleteAuthApply(String id, Admin admin, String ip);

    /**
     * 发送站内消息
     *
     * @param admin
     * @param vo
     * @return
     */
    boolean sendSystemMessage(Admin admin, SendSystemMessageVO vo, String ip);

    /**
     * 查询登录日志
     *
     * @param admin
     * @return
     */
    Object searchLoginLog(Admin admin, TablePagePars pagePars);

    /**
     * 获取平台配置
     *
     * @param admin
     * @return
     */
    Object getPlatform(Admin admin);

    /**
     * 修改平台配置
     *
     * @param admin
     * @param platform
     * @return
     */
    boolean updatePlatform(Admin admin, Platform platform, String ip);

    /**
     * 查询操作记录
     *
     * @param admin
     * @param pagePars
     * @return
     */
    Object searchOperationLog(Admin admin, TablePagePars pagePars);

    /**
     * @param vo
     * @param admin
     * @return
     */
    boolean deleteOperationLog(DeleteOperationVO vo, Admin admin,String ip);

    /**
     * 查询站内信消息
     *
     * @param admin
     * @param pagePars
     * @return
     */
    Object searchMessage(Admin admin, TablePagePars pagePars);

    /**
     * 删除站内信消息
     *
     * @param id
     * @param admin
     * @return
     */
    boolean deleteSystemMessage(String id, Admin admin, String ip);

    /**
     * 新增系统品牌
     *
     * @param admin
     * @param brandSystem
     * @return
     */
    boolean addSystemBrand(Admin admin, BrandSystem brandSystem, String ip);

    /**
     * 查询系统品牌列表
     *
     * @param pagePars
     * @return
     */
    Object searchSystemBrands(TablePagePars pagePars);

    /**
     * 修改系统品牌
     *
     * @param admin
     * @param brandSystem
     * @return
     */
    boolean updateSystemBrand(Admin admin, BrandSystem brandSystem, String ip);

    /**
     * 删除品牌
     *
     * @param admin
     * @param id
     * @param ip
     * @return
     */
    boolean deleteSystemBrand(Admin admin, String id, String ip);

    /**
     * 修改管理员信息
     *
     * @param admin
     * @param remoteAddr
     * @param admin1
     * @return
     */
    boolean updateAdminMsg(Admin admin, String remoteAddr, Admin admin1);

    /**
     * 退出登录
     *
     * @param admin
     * @return
     */
    boolean loginOut(Admin admin);

    /**
     * 删除操作日志
     * @param ids
     * @param admin
     * @return
     */
    boolean batchOperationLog(String[] ids, Admin admin,String ip);

    /**
     * 导出用户数据
     * @param vo
     * @param admin
     * @param remoteAddr
     * @return
     */
    String exportUsers(ExportUsersVO vo, Admin admin, String remoteAddr);
}
