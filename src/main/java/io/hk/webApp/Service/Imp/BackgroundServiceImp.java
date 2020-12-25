package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.framecore.Tool.Md5Help;
import io.hk.webApp.DataAccess.AdminSet;
import io.hk.webApp.DataAccess.BrandSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IBackgroundService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class BackgroundServiceImp implements IBackgroundService {

    @Autowired
    private AdminSet adminSet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private UserSet userSet;

    /**
     * 获取所有品牌
     * @return
     */
    @Override
    public PageData<Brand> getAllBrand(TablePagePars pagePars) {
        return brandSet.search(pagePars.Pars,pagePars.PageSize,pagePars.PageIndex,pagePars.Order);
    }

    /**
     * 修改品牌
     * @param brand
     * @return
     */
    @Override
    public boolean update(Brand brand) {
        return brandSet.Update(brand.getId(),brand) > 0;
    }

    /**
     * 审核品牌
     * @param brand1
     * @return
     */
    @Override
    public boolean audit(Brand brand1) {
        Brand brand = brandSet.Get(brand1.getId());
        if(brand == null || !BaseType.Consent.BASE.getCode().equals(brand.getStatus())){
            throw new OtherExcetion("不可操作");
        }
        return brand1.updateById();
    }

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    @Override
    public Object login(Admin admin) {
        if(StringUtils.isAnyEmpty(admin.getAccount(),admin.getPassword())){
            throw new OtherExcetion("请输入账号或密码");
        }
        Admin info = adminSet.Where("(account=?)and(password=?)",admin.getAccount(), Md5Help.toMD5(admin.getPassword())).First();
        if(null == info){
            throw new OtherExcetion("账号或密码错误");
        }
        if(!BaseType.Status.YES.getCode().equals(info.getStatus())){
            throw new OtherExcetion("该账号已被封禁");
        }
        String loginKey = UUID.randomUUID().toString().replace("-", "");
        info.setLoginKey(loginKey);
        info.setLastloginTime(new Date());
        if (admin.updateById()) {
            info.setPassword(null);
            return info;
        }
        return null;
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Override
    public boolean addAdmin(Admin admin) {
        if(StringUtils.isAnyEmpty(admin.getAccount(),admin.getPassword())){
            throw new OtherExcetion("请输入账号或密码");
        }
        Admin info = adminSet.Where("account=?",admin.getAccount()).First();
        if(null != info){
            throw new OtherExcetion("该账号已存在");
        }
        admin.setPassword(Md5Help.toMD5(admin.getPassword()));
        admin.setStatus(BaseType.Status.YES.getCode());
        String loginKey = UUID.randomUUID().toString().replace("-", "");
        admin.setLoginKey(loginKey);
        return null != adminSet.Add(admin);
    }

    /**
     * 展示管理员列表
     * @return
     */
    @Override
    public PageData<Admin> searchAdmin() {
        PageData<Admin> pageData = new PageData<>();
        pageData.rows = adminSet.ToList();
        pageData.total = adminSet.Count();
        pageData.rows.forEach((a)->{
            a.setPassword(null);
            a.setLoginKey(null);
        });
        return pageData;
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @Override
    public boolean deleteAdmin(String id,String adminId) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要删除的管理员");
        }
        Admin admin = adminSet.Get(id);
        if(admin.getId().equals(adminId)){
            throw new OtherExcetion("不能删除自己");
        }
        return adminSet.Delete(id) > 0;
    }

    /**
     * 禁用启用管理员
     * @param id
     * @return
     */
    @Override
    public boolean updateStatus(String id,String adminId) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要操作的管理员");
        }
        Admin admin = adminSet.Get(id);
        if(null == admin){
            throw new OtherExcetion("不存在的管理员");
        }
        if(admin.getId().equals(adminId)){
            throw new OtherExcetion("不能操作自己");
        }
        admin.setStatus(BaseType.Status.YES.getCode().equals(admin.getStatus()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        admin.setLoginKey(null);
        return admin.updateById();
    }

    /**
     * 展示所有用户
     * @param pagePars
     * @return
     */
    @Override
    public PageData<User> searchUser(TablePagePars pagePars) {
        PageData<User> pageData = new PageData<>();
        pageData.rows = userSet.Limit(pagePars.PageSize,pagePars.PageIndex).ToList();
        pageData.total = userSet.Count();
        pageData.rows.forEach((a)->{
            a.setPassword(null);
            a.setLoginKey(null);
        });
        return pageData;
    }

    /**
     * 禁用/启用用户
     * @param id
     * @return
     */
    @Override
    public boolean updateUserStatus(String id) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要禁用的用户");
        }
        User user = userSet.Get(id);
        if(null == user){
            throw new OtherExcetion("不存在的用户");
        }
        user.setStatus(BaseType.Status.YES.getCode().equals(user.getStatus()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        user.setLoginKey(null);
        return user.updateById();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public boolean addUser(User user) {
        if(StringUtils.isAnyEmpty(user.getType(),user.getPhone(),user.getPassword(),user.getName())){
            throw new OtherExcetion("请完善必填项");
        }
        user.setPassword(Md5Help.toMD5(user.getPassword()));
        user.setStatus(BaseType.Status.YES.getCode());
        return null != userSet.Add(user);
    }

}
