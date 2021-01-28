package io.hk.webApp.Service.Imp;

import cn.hutool.core.date.DateUtil;
import io.framecore.Frame.PageData;
import io.framecore.Tool.Md5Help;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IBackgroundService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.ExcelUtil;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.Tools.iparea.IPSeekers;
import io.hk.webApp.dto.SystemDataDTO;
import io.hk.webApp.vo.DeleteOperationVO;
import io.hk.webApp.vo.ExportUsersVO;
import io.hk.webApp.vo.SendSystemMessageVO;
import io.hk.webApp.vo.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BackgroundServiceImp implements IBackgroundService {

    @Autowired
    private AdminSet adminSet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private UserSet userSet;

    @Autowired
    private ProductSet productSet;

    @Autowired
    private SchemeSet schemeSet;

    @Autowired
    private FactorySchemeSet factorySchemeSet;

    @Autowired
    private AuthSet authSet;

    @Autowired
    private AuthApplySet authApplySet;

    @Autowired
    private MessagesSet messagesSet;

    @Autowired
    private LoginLogSet loginLogSet;

    @Autowired
    private PlatformSet platformSet;

    @Autowired
    private OperationLogSet operationLogSet;

    @Autowired
    private SystemMessageSet systemMessageSet;

    @Autowired
    private BrandSystemSet brandSystemSet;


    /**
     * 获取所有品牌
     *
     * @return
     */
    @Override
    public PageData<Brand> getAllUnCheckBrand(TablePagePars pagePars) {
        return brandSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order);
    }

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @Override
    public boolean update(Brand brand) {
        boolean result = brandSet.Update(brand.getId(), brand) > 0;
        if (result) {
//            OperationLog operationLog = new OperationLog();
//            operationLog.setAccount();
        }
        return true;
    }

    /**
     * 审核品牌
     *
     * @param brand
     * @return
     */
    @Override
    public boolean audit(Brand brand, Admin admin, String ip) {
        if (StringUtils.isEmpty(brand.getStatus())) {
            throw new OtherExcetion("请定义通过状态");
        }
        if (StringUtils.isEmpty(brand.getYesOrNo())) {
            throw new OtherExcetion("请选择是否平台品牌");
        }
        String status = brand.getStatus();
        String feedback = brand.getFeedback();
        String cause = "";
        if (StringUtils.isNotEmpty(brand.getCause())) {
            cause = brand.getCause();
        }
        String systemId = "";
        if (BaseType.Status.YES.getCode().equals(brand.getYesOrNo())) {
            if (StringUtils.isNotEmpty(brand.getSystemId())) {
                systemId = brand.getSystemId();
            } else {
                throw new OtherExcetion("请选择平台品牌");
            }
        }
        brand = brandSet.Get(brand.getId());
        if (brand == null || !BaseType.Consent.BASE.getCode().equals(brand.getStatus())) {
            throw new OtherExcetion("不可操作");
        }
        brand.setStatus(status);
        brand.setFeedback(feedback);
        if (status.equals(BaseType.Consent.PASS.getCode())) {
            if (BaseType.Status.YES.getCode().equals(brand.getYesOrNo())) {
                BrandSystem info = new BrandSystem();
                info.setName(brand.getName());
                info.setLogo(brand.getLogo());
                info.setDetails(brand.getDetails());
                Object id = brandSystemSet.Add(info);
                brand.setSystemId(id.toString());
            } else {
                BrandSystem system = brandSystemSet.Get(systemId);
                if (null != system) {
                    brand.setName(system.getName());
                    brand.setSystemId(systemId);
                }
            }
            addOperationLog(admin, ip, "通过品牌");
        } else {
            brand.setCause(cause);
            addOperationLog(admin, ip, "拒绝品牌");
        }
        return brand.updateById();
    }

    /**
     * 管理员登录
     *
     * @param admin
     * @return
     */
    @Override
    public Object login(Admin admin, String ip) {
        if (StringUtils.isAnyEmpty(admin.getAccount(), admin.getPassword())) {
            throw new OtherExcetion("请输入账号或密码");
        }
        Admin info = adminSet.Where("(account=?)and(password=?)", admin.getAccount(), Md5Help.toMD5(admin.getPassword())).First();
        if (null == info) {
            throw new OtherExcetion("账号或密码错误");
        }
        if (!BaseType.Status.YES.getCode().equals(info.getStatus())) {
            throw new OtherExcetion("该账号已被封禁");
        }
        String loginKey = UUID.randomUUID().toString().replace("-", "");
        info.setLoginKey(loginKey);
        info.setLastloginTime(System.currentTimeMillis());
        if (info.updateById()) {
            info.setPassword(null);
            {
                //记录登录日志
                LoginLog loginLog = new LoginLog();
                loginLog.setCTime(System.currentTimeMillis());
                loginLog.setPhone(info.getAccount());
                loginLog.setName(null == admin.getName() ? "" : admin.getName());
                loginLog.setStatus("1");
                loginLog.setType("0");
                loginLog.setIp(ip);
                loginLog.setFacility("未知");
                String addr = IPSeekers.getInstance().getAddress(ip);
                loginLog.setAddr(StringUtils.isEmpty(addr) ? "未知" : addr);
                loginLogSet.Add(loginLog);
            }
            addOperationLog(admin, ip, "管理员登录");
            return info;
        }
        return null;
    }

    /**
     * 添加管理员
     *
     * @param admin
     * @return
     */
    @Override
    public boolean addAdmin(Admin admin, String ip, Admin admin1) {
        if (StringUtils.isAnyEmpty(admin.getAccount(), admin.getPassword())) {
            throw new OtherExcetion("请输入账号或密码");
        }
        if (StringUtils.isEmpty(admin.getName())) {
            throw new OtherExcetion("请输入管理员名");
        }
        Admin info = adminSet.Where("account=?", admin.getAccount()).First();
        if (null != info) {
            throw new OtherExcetion("该账号已存在");
        }
        admin.setPassword(Md5Help.toMD5(admin.getPassword()));
        admin.setStatus(BaseType.Status.YES.getCode());
        String loginKey = UUID.randomUUID().toString().replace("-", "");
        admin.setLoginKey(loginKey);
        admin.setLastloginTime(System.currentTimeMillis());
        admin.setCtime(System.currentTimeMillis());
        addOperationLog(admin1, ip, "添加管理员");
        return null != adminSet.Add(admin);
    }

    /**
     * 展示管理员列表
     *
     * @return
     */
    @Override
    public PageData<Admin> searchAdmin(TablePagePars pagePars) {
        return adminSet.search(pagePars);
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteAdmin(String id, Admin admin1, String ip) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的管理员");
        }
        Admin admin = adminSet.Get(id);
        if (admin.getId().equals(admin1.getId())) {
            throw new OtherExcetion("不能删除自己");
        }
        addOperationLog(admin1, ip, "删除管理员");
        return adminSet.Delete(id) > 0;
    }

    /**
     * 禁用启用管理员
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateStatus(String id, Admin admin1, String ip) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要操作的管理员");
        }
        Admin admin = adminSet.Get(id);
        if (null == admin) {
            throw new OtherExcetion("不存在的管理员");
        }
        if (admin.getId().equals(admin1.getId())) {
            throw new OtherExcetion("不能操作自己");
        }
        admin.setStatus(BaseType.Status.YES.getCode().equals(admin.getStatus()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        admin.setLoginKey(null);
        String context = BaseType.Status.YES.getCode().equals(admin.getStatus()) ? "启用管理员" : "禁用管理员";
        addOperationLog(admin, ip, context);
        return admin.updateById();
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean addUser(User user, Admin admin, String ip) {
        if (StringUtils.isAnyEmpty(user.getType(), user.getPhone(), user.getPassword(), user.getName())) {
            throw new OtherExcetion("请完善必填项");
        }
        user.setPassword(Md5Help.toMD5(user.getPassword()));
        user.setStatus(BaseType.Status.YES.getCode());
        addOperationLog(admin, ip, "添加会员");
        return null != userSet.Add(user);
    }

    /**
     * 获取系统首页所有数据
     *
     * @param admin
     * @return
     */
    @Override
    public Object searchSystemData(Admin admin, Long userStartTime, Long userEndsTime, Long schemeStartTime, Long schemeEndTime) {

        //获取当前年月日
        Calendar now = Calendar.getInstance();
        String nowDay = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        long nowTime = formatDate(nowDay);
        long nextWeekTime = nowTime - (86400000L * 7L);
        long nowMonthTime = formatDate(now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-1");
        long nextMonthTime = nowMonthTime + (86400000L * 30L);

        SystemDataDTO dto = new SystemDataDTO();
        dto.setFactorys(userSet.Where("type=?", BaseType.UserType.FACTORY.getCode()).Count() + "");
        dto.setProducts(productSet.Count() + "");
        dto.setSalers(userSet.Where("type=?", BaseType.UserType.SALER.getCode()).Count() + "");
        dto.setSchemes(schemeSet.Count() + "");
        dto.setBrands(brandSet.Where("status=?", BaseType.Consent.BASE.getCode()).Count() + "");
        dto.setDownLineProducts(productSet.Where("status=?", BaseType.Status.NO.getCode()).Count() + "");
        dto.setOnLineProducts(productSet.Where("status=?", BaseType.Status.YES.getCode()).Count() + "");
        dto.setAllUsers(userSet.Count() + "");
        dto.setNewAddUsers(userSet.Where("(createTime>=?)and(createTime<=?)", nowTime, (nowTime + 86400000 - 1)).Count() + "");
        dto.setYesterdayUsers(userSet.Where("(createTime>=?)and(createTime<?)", (nowTime - 86400000), nowTime).Count() + "");
        long monthUsers = userSet.Where("(createTime>=?)and(createTime<=?)", nowMonthTime, nextMonthTime).Count();
        dto.setMonthUsers(monthUsers + "");
        long weekSchemes = schemeSet.Where("(time>=?)and(time<=?)", nextWeekTime, nowTime).Count();
        dto.setWeekSchemes(weekSchemes + "");
        long monthSchmes = schemeSet.Where("(time>=?)and(time<=?)", nowMonthTime, nextMonthTime).Count();
        dto.setMonthSchemes(monthSchmes + "");
        //用户同比上*增加比率
        try {
            dto.setCompareUserMonth(monthUsers / userSet.Where("(createTime>=?)and(createTime<=?)", nowMonthTime - (86400000L * 30L), nowMonthTime).Count() + "");
        } catch (Exception e) {
            dto.setCompareUserMonth(1 + "");
        }
        //方案同比上*增加比率
        try {
            dto.setCompareSchemeMonth(monthSchmes / schemeSet.Where("(time>=?)and(time<=?)", nowMonthTime - (86400000L * 30L), nowMonthTime).Count() + "");
        } catch (Exception e) {
            dto.setCompareSchemeMonth(1 + "");
        }
        try {
            dto.setCompareSchemeWeek(weekSchemes / schemeSet.Where("(time>=?)and(time<=?)", nowTime - (86400000 * 7), nowTime).Count() + "");
        } catch (Exception e) {
            dto.setCompareSchemeWeek(1 + "");
        }
        return dto;
    }

    /**
     * 修改管理员
     *
     * @param admin
     * @param admin1
     * @return
     */
    @Override
    public boolean updateAdmin(Admin admin, Admin admin1, String ip) {
        if (StringUtils.isAnyEmpty(admin.getOldPwd(), admin.getPassword())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (!Md5Help.toMD5(admin.getOldPwd()).equals(admin1.getPassword())) {
            throw new OtherExcetion("新旧密码不一致");
        }
        admin1.setPassword(Md5Help.toMD5(admin.getPassword()));
        admin1.setLoginKey(null);
        if (StringUtils.isNotEmpty(admin.getImg())) {
            admin1.setImg(admin.getImg());
        }
        addOperationLog(admin1, ip, "修改管理员基本信息");
        return admin1.updateById();
    }

    /**
     * 展示所有用户
     *
     * @param pagePars
     * @return
     */
    @Override
    public PageData<User> searchUser(TablePagePars pagePars) {
        PageData<User> pageData = userSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order);
        pageData.rows.forEach((a) -> {
            a.setPassword(null);
            a.setLoginKey(null);
            if (BaseType.UserType.FACTORY.getCode().equals(a.getType())) {
                a.setScheme(factorySchemeSet.Where("factoryId=?", a.getId()).Count());
            } else {
                a.setScheme(schemeSet.Where("salerId=?", a.getId()).Count());
            }
        });
        return pageData;
    }

    /**
     * 禁用/启用用户
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateUserStatus(String id, Admin admin, String ip) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要禁用的用户");
        }
        User user = userSet.Get(id);
        if (null == user) {
            throw new OtherExcetion("不存在的用户");
        }
        user.setStatus(BaseType.Status.YES.getCode().equals(user.getStatus()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        user.setLoginKey(null);
        String context = BaseType.Status.YES.getCode().equals(user.getStatus()) ? "启用会员" : "禁用会员";
        addOperationLog(admin, ip, context);
        return user.updateById();
    }


    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    @Override
    public Object getUserDeatils(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要查询的用户");
        }
        User user = userSet.Get(id);
        Auth auth = new Auth().getById(id);
        Map<String, Object> map = new HashMap<>();
        user.setPassword(null);
        user.setLoginKey(null);
        map.put("user", user);
        map.put("auth", auth);
        return map;
    }

    /**
     * 直接修改用户密码
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUserPasswordByUserId(User user, Admin admin, String ip) {
        if (StringUtils.isAnyEmpty(user.getId(), user.getPassword())) {
            throw new OtherExcetion("请完善必填项");
        }
        User user1 = userSet.Get(user.getId());
        if (null == user1) {
            throw new OtherExcetion("不存在的用户");
        }
        user1.setLoginKey(null);
        user1.setPassword(Md5Help.toMD5(user.getPassword()));
        addOperationLog(admin, ip, "管理员修改会员密码");
        return user1.updateById();
    }

    /**
     * 展示认证列表
     *
     * @return
     */
    @Override
    public Object searchAuthApplyList(TablePagePars pagePars) {
        return authApplySet.search(pagePars);
    }

    /**
     * 通过认证
     *
     * @param authApply
     * @return
     */
    @Override
    public boolean passAuth(AuthApply authApply, Admin admin, String ip) {
        if (StringUtils.isEmpty(authApply.getId())) {
            throw new OtherExcetion("请选择要通过的用户");
        }
        String status = BaseType.Consent.PASS.getCode();
        authApply = authApplySet.Get(authApply.getId());
        if (null == authApply) {
            throw new OtherExcetion("不存在");
        }
        User user = userSet.Where("phone=?", authApply.getPhone()).First();
        if (null == user) {
            throw new OtherExcetion("用户不存在");
        }
        if (status.equals(BaseType.Consent.PASS.getCode())) {
            authApply.setStatus(BaseType.Consent.PASS.getCode());
        } else {
            authApply.setStatus(BaseType.Consent.REFUSE.getCode());
        }
        user.setAuth(BaseType.Status.YES.getCode());
        addOperationLog(admin, ip, "通过会员认证");
        return authApply.updateById() && user.updateById();
    }

    /**
     * 删除申请记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteAuthApply(String id, Admin admin, String ip) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的记录");
        }
        AuthApply authApply = authApplySet.Get(id);
        if (null != authApply) {
            User user = userSet.Where("phone=?", authApply.getPhone()).First();
            if (null != user) {
                user.setStatus(BaseType.Status.NO.getCode());
                user.updateById();
            }
        }
        addOperationLog(admin, ip, "删除申请记录");
        return authApplySet.Delete(id) > 0;
    }

    /**
     * 发送站内消息
     *
     * @param admin
     * @param vo
     * @return
     */
    @Override
    public boolean sendSystemMessage(Admin admin, SendSystemMessageVO vo, String ip) {
        if (StringUtils.isAnyEmpty(vo.getMessages().getTitleMessage(), vo.getMessages().getContent())) {
            throw new OtherExcetion("请完善消息内容");
        }
        if (vo.getUserIds().size() == 0) {
            throw new OtherExcetion("请选择要发送的用户");
        }
        vo.getMessages().setTime(System.currentTimeMillis());
        vo.getMessages().setSource("管理员");
        vo.getMessages().setStatus(BaseType.Status.NO.getCode());
        vo.getMessages().setTitle(BaseType.Message.MESSAGE.getCode());
        vo.getMessages().setUserId(admin.getId());
        vo.getUserIds().forEach((a) -> {
            vo.getMessages().setReceiveId(a);
            messagesSet.Add(vo.getMessages());
        });
        {
            SystemMessage systemMessage = new SystemMessage();
            systemMessage.setContent(vo.getMessages().getContent());
            systemMessage.setTitleMessage(vo.getMessages().getTitleMessage());
            systemMessage.setCount(vo.getUserIds().size());
            systemMessage.setCtime(System.currentTimeMillis());
            systemMessage.setAdminId(admin.getId());
            systemMessage.setName(admin.getName());
            systemMessageSet.Add(systemMessage);
        }
        addOperationLog(admin, ip, "发送站内信消息");
        return true;
    }

    /**
     * 查询登录日志
     *
     * @param admin
     * @return
     */
    @Override
    public Object searchLoginLog(Admin admin, TablePagePars pagePars) {
        PageData<LoginLog> pageData = new PageData<>();
        pageData.rows = loginLogSet.Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_ctime").ToList();
        pageData.total = loginLogSet.Count();
        return pageData;
    }

    /**
     * 获取平台配置
     *
     * @param admin
     * @return
     */
    @Override
    public Object getPlatform(Admin admin) {
        List<Platform> list = platformSet.ToList();
        if (list.size() == 0) {
            list = new ArrayList<>();
            Platform platform = new Platform();
            platformSet.Add(platform);
            list.add(platform);
        }
        return list.get(0);
    }

    /**
     * 修改平台配置
     *
     * @param admin
     * @param platform
     * @return
     */
    @Override
    public boolean updatePlatform(Admin admin, Platform platform, String ip) {
        addOperationLog(admin, ip, "修改平台配置");
        return platform.updateById();
    }

    /**
     * 查询操作记录
     *
     * @param admin
     * @param pagePars
     * @return
     */
    @Override
    public PageData<OperationLog> searchOperationLog(Admin admin, TablePagePars pagePars) {
        return operationLogSet.search(admin, pagePars);
    }

    /**
     * 清除操作日志
     *
     * @param vo
     * @param admin
     * @return
     */
    @Override
    public boolean deleteOperationLog(DeleteOperationVO vo, Admin admin,String ip) {
        TablePagePars pagePars = new TablePagePars();
        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put("endTime", vo.getEndTime());
        pagePars.Pars = hashtable;
        pagePars.PageSize = 0;
        pagePars.PageIndex = 99999;
        PageData<OperationLog> pageData = this.searchOperationLog(admin, pagePars);
        pageData.rows.forEach(a -> operationLogSet.Delete(a.getId()));
        addOperationLog(admin,ip,"删除操作日志");
        return true;
    }

    /**
     * 查询站内信消息
     *
     * @param admin
     * @param pagePars
     * @return
     */
    @Override
    public Object searchMessage(Admin admin, TablePagePars pagePars) {
        return systemMessageSet.searchMessage(admin, pagePars);
    }

    /**
     * 删除站内信消息
     *
     * @param id
     * @param admin
     * @return
     */
    @Override
    public boolean deleteSystemMessage(String id, Admin admin, String ip) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的消息");
        }
        addOperationLog(admin, ip, "删除站内信消息");
        return systemMessageSet.Delete(id) > 0;
    }

    /**
     * 新增系统品牌
     *
     * @param admin
     * @param brandSystem
     * @return
     */
    @Override
    public boolean addSystemBrand(Admin admin, BrandSystem brandSystem, String ip) {
        if (StringUtils.isAnyEmpty(brandSystem.getName(), brandSystem.getLogo())) {
            throw new OtherExcetion("请完善必填项");
        }
        BrandSystem info = brandSystemSet.Where("name=?", brandSystem.getName()).First();
        if (null != info) {
            throw new OtherExcetion("此品牌已存在");
        }
        addOperationLog(admin, ip, "新增系统品牌");
        return null != brandSystemSet.Add(brandSystem);
    }

    /**
     * 查询系统品牌列表
     *
     * @param pagePars
     * @return
     */
    @Override
    public Object searchSystemBrands(TablePagePars pagePars) {
        return brandSystemSet.search(pagePars);
    }

    /**
     * 删除品牌
     *
     * @param admin
     * @param id
     * @param ip
     * @return
     */
    @Override
    public boolean deleteSystemBrand(Admin admin, String id, String ip) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要修改的品牌");
        }
        addOperationLog(admin, ip, "删除系统品牌");
        return brandSystemSet.Delete(id) > 0;
    }

    /**
     * 修改别人信息
     *
     * @param admin
     * @param remoteAddr
     * @param admin1
     * @return
     */
    @Override
    public boolean updateAdminMsg(Admin admin, String remoteAddr, Admin admin1) {
        if (StringUtils.isEmpty(admin.getId())) {
            throw new OtherExcetion("请选择要修改的管理员");
        }
        Admin info = adminSet.Get(admin.getId());
        if (null == info) {
            throw new OtherExcetion("不存在的账户");
        }
        if (StringUtils.isEmpty(admin.getPassword())) {
            admin.setPassword(info.getPassword());
        }
        admin.setLoginKey(null);
        addOperationLog(admin, remoteAddr, "修改管理员信息");
        return admin.updateById();
    }

    /**
     * 退出登录
     *
     * @param admin
     * @return
     */
    @Override
    public boolean loginOut(Admin admin) {
        admin.setLoginKey(null);
        return admin.updateById();
    }

    /**
     * 删除操作日志
     *
     * @param ids
     * @param admin
     * @return
     */
    @Override
    public boolean batchOperationLog(String[] ids, Admin admin,String ip) {
        if (ids.length == 0) {
            return true;
        }
        int count = 0;
        for (int i = 0; i < ids.length; i++) {
            count += operationLogSet.Delete(ids[i]);
        }
        addOperationLog(admin,ip,"删除操作日志");
        return count > 0 && count == ids.length;
    }

    /**
     * 导出用户数据
     *
     * @param vo
     * @param admin
     * @param ip
     * @return
     */
    @Override
    public String exportUsers(ExportUsersVO vo, Admin admin, String ip) {
        if("0".equals(vo.getType())){
            List<String> list = new ArrayList<>();
            userSet.ToList().forEach((a)->{
                list.add(a.getId());
            });
            vo.setIds(list);
        }
        List<UserDTO> list = new ArrayList<>();
        if (vo.getIds().size() == 0) {
            throw new OtherExcetion("请选择要导出的用户");
        }
        vo.getIds().forEach((a) -> {
            User user = userSet.Get(a);
            if (null != user) {
                UserDTO userDTO = new UserDTO();
                userDTO.setPhone(user.getPhone());
                userDTO.setAuth(BaseType.Status.YES.getCode().endsWith(user.getAuth()) ? "是" : "否");
                userDTO.setType(BaseType.UserType.FACTORY.getCode().equals(user.getType()) ? "供应商" : "经销商");
                if (BaseType.UserType.FACTORY.getCode().equals(user.getType())) {
                    userDTO.setSchemeCount(factorySchemeSet.Where("factoryId=?", user.getId()).Count());
                } else {
                    userDTO.setSchemeCount(schemeSet.Where("salerId=?", user.getId()).Count());
                }
                userDTO.setCreateTime(DateUtil.format(DateUtil.date(null == user.getCreateTime() ? System.currentTimeMillis() : user.getCreateTime()), "yyyy-MM-dd"));
                userDTO.setLastLoginTime(DateUtil.format(DateUtil.date(null == user.getLastloginTime() ? System.currentTimeMillis() : user.getLastloginTime()), "yyyy-MM-dd"));
                userDTO.setStatus(BaseType.Status.YES.getCode().equals(user.getStatus()) ? "是" : "否");
                list.add(userDTO);
            }
        });
        return ExcelUtil.createExcel(list);
    }


    /**
     * 修改系统品牌
     *
     * @param admin
     * @param brandSystem
     * @return
     */
    @Override
    public boolean updateSystemBrand(Admin admin, BrandSystem brandSystem, String ip) {
        if (StringUtils.isEmpty(brandSystem.getId())) {
            throw new OtherExcetion("请选择要修改的品牌");
        }
        BrandSystem info = brandSystemSet.Get(brandSystem.getId());
        if (null == info) {
            throw new OtherExcetion("不存在的品牌");
        }
        addOperationLog(admin, ip, "修改系统品牌");
        return brandSystemSet.Update(brandSystem.getId(), brandSystem) > 0;
    }

    /**
     * 时间格式转换成时间戳
     *
     * @param date
     * @return
     */
    public static long formatDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date parse;
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            parse = new Date();
        }
        return parse.getTime();
    }


    /**
     * 添加操作记录
     *
     * @param admin
     * @param ip
     * @param content
     */
    public void addOperationLog(Admin admin, String ip, String content) {
        OperationLog operationLog = new OperationLog();
        operationLog.setAccount(admin.getAccount());
        operationLog.setIp(ip);
        String addr = IPSeekers.getInstance().getAddress(ip);
        operationLog.setAddr(StringUtils.isEmpty(addr) ? "未知" : addr);
        operationLog.setName(admin.getName());
        operationLog.setCtime(System.currentTimeMillis());
        operationLog.setContent(content);
        operationLogSet.Add(operationLog);
    }

    public static void main(String[] args) {
        String format = DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy-MM-dd");
        System.out.println(format);
    }
}
