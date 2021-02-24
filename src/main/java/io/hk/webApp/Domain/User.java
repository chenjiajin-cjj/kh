package io.hk.webApp.Domain;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.framecore.Tool.Md5Help;
import io.hk.webApp.DataAccess.LoginLogSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.iparea.IPSeekers;
import org.apache.commons.lang3.StringUtils;

public class User extends ViewStore {
    // _id
    @JsonProperty(value = "_id")
    public String getId() {
        if (get("_id") == null) {
            return null;
        }
        return get("_id").toString();
    }

    @JsonProperty(value = "_id")
    public void setId(String Id) {
        set("_id", Id);
    }

    /* 手机账号 phone */
    @JsonProperty(value = "phone")
    public String getPhone() {
        return (String) get("phone");
    }

    @JsonProperty(value = "phone")
    public void setPhone(String phone) {
        set("phone", phone);
    }

    /* 真实手机账号 realPhone */
    @JsonProperty(value = "realPhone")
    public String getRealPhone() {
        return (String) get("realPhone");
    }

    @JsonProperty(value = "realPhone")
    public void setRealPhone(String realPhone) {
        set("realPhone", realPhone);
    }

    /* identity  , 1: factory  2:saler */
    @JsonProperty(value = "identity")
    public Integer getIdentity() {
        return (Integer) get("identity");
    }

    @JsonProperty(value = "identity")
    public void setIdentity(Integer identity) {
        set("identity", identity);
    }


    /* loginKey */
    @JsonProperty(value = "loginKey")
    public String getLoginKey() {
        return (String) get("loginKey");
    }

    @JsonProperty(value = "loginKey")
    public void setLoginKey(String loginKey) {
        set("loginKey", loginKey);
    }

    /* 上次登录时间 lastloginTime */
    @JsonProperty(value = "lastloginTime")
    public Long getLastloginTime() {
        return (Long) get("lastloginTime");
    }

    @JsonProperty(value = "lastloginTime")
    public void setLastloginTime(Long lastloginTime) {
        set("lastloginTime", lastloginTime);
    }

    /* 创建时间 _ctime */
    @JsonProperty(value = "_ctime")
    public Date getCTime() {
        return (Date) get("_ctime");
    }

    @JsonProperty(value = "_ctime")
    public void setCTime(Date _ctime) {
        set("_ctime", _ctime);
    }

    /* 用户身份 1是供应商  2是经销商  type */
    @JsonProperty(value = "type")
    public String getType() {
        return (String) get("type");
    }

    @JsonProperty(value = "type")
    public void setType(String type) {
        set("type", type);
    }

    /* 联系电话 tel */
    @JsonProperty(value = "tel")
    public String getTel() {
        return (String) get("tel");
    }

    @JsonProperty(value = "tel")
    public void setTel(String tel) {
        set("tel", tel);
    }

    /* 密码 password */
    @JsonProperty(value = "password")
    public String getPassword() {
        return (String) get("password");
    }

    @JsonProperty(value = "password")
    public void setPassword(String password) {
        set("password", password);
    }

    /* 企业或个人名称 companyName */
    @JsonProperty(value = "companyName")
    public String getCompanyName() {
        return (String) get("companyName");
    }

    @JsonProperty(value = "companyName")
    public void setCompanyName(String companyName) {
        set("companyName", companyName);
    }

    /* 联系人 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 国家或地区 state */
    @JsonProperty(value = "state")
    public String getState() {
        return (String) get("state");
    }

    @JsonProperty(value = "state")
    public void setState(String state) {
        set("state", state);
    }

    /* 省市 province */
    @JsonProperty(value = "province")
    public String getProvince() {
        return (String) get("province");
    }

    @JsonProperty(value = "province")
    public void setProvince(String province) {
        set("province", province);
    }

    /* 详细地址 addr */
    @JsonProperty(value = "addr")
    public String getAddr() {
        return (String) get("addr");
    }

    @JsonProperty(value = "addr")
    public void setAddr(String addr) {
        set("addr", addr);
    }

    /* 经营范围 manage */
    @JsonProperty(value = "manage")
    public String getManage() {
        return (String) get("manage");
    }

    @JsonProperty(value = "manage")
    public void setManage(String manage) {
        set("manage", manage);
    }

    /* 头像 img */
    @JsonProperty(value = "img")
    public String getImg() {
        return (String) get("img");
    }

    @JsonProperty(value = "img")
    public void setImg(String img) {
        set("img", img);
    }

    /* 验证码 phoneCode */
    @JsonProperty(value = "phoneCode")
    public String getPhoneCode() {
        return (String) get("phoneCode");
    }

    @JsonProperty(value = "phoneCode")
    public void setPhoneCode(String phoneCode) {
        set("phoneCode", phoneCode);
    }

    /* 状态 status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 上级id fatherId */
    @JsonProperty(value = "fatherId")
    public String getFatherId() {
        return (String) get("fatherId");
    }

    @JsonProperty(value = "fatherId")
    public void setFatherId(String fatherId) {
        set("fatherId", fatherId);
    }

    /* 创建时间 */
    @JsonProperty(value = "createTime")
    public Long getCreateTime() {
        return (Long) get("createTime");
    }

    @JsonProperty(value = "createTime")
    public void setCreateTime(Long createTime) {
        set("createTime", createTime);
    }

    /* 是否认证 1是 2否 auth */
    @JsonProperty(value = "auth")
    public String getAuth() {
        return (String) get("auth");
    }

    @JsonProperty(value = "auth")
    public void setAuth(String auth) {
        set("auth", auth);
    }

    /* 方案数量 scheme */
    @JsonProperty(value = "scheme")
    public Long getScheme() {
        return (Long) get("scheme");
    }

    @JsonProperty(value = "scheme")
    public void setScheme(Long scheme) {
        set("scheme", scheme);
    }


    /**
     * 登录
     *
     * @return
     */
    public User login(String ip) {
        String loginKey = UUID.randomUUID().toString().replace("-", "");
        UserSet userSet = Holder.getBean(UserSet.class);
        User user = this.getUserByPhone(this.getPhone());
        if (null == user) {
            throw new OtherExcetion("用户不存在");
        }
        if (!Md5Help.toMD5(this.getPassword()).equals(user.getPassword())) {
            throw new OtherExcetion("密码错误");
        }
        if (!BaseType.Status.YES.getCode().equals(user.getStatus())) {
            throw new OtherExcetion("账号已被封禁");
        }
        if (StringUtils.isNotEmpty(user.getFatherId())) {
            if (StringUtils.isEmpty(user.getRealPhone()) && BaseType.UserType.FACTORY.getCode().equals(user.getType())) {
                throw new OtherExcetion(-3, "业务员请先绑定手机号");
            }
        }
        {
            LoginLogSet loginLogSet = Holder.getBean(LoginLogSet.class);
            LoginLog loginLog = new LoginLog();
            loginLog.setCTime(System.currentTimeMillis());
            loginLog.setPhone(user.getPhone());
            loginLog.setName(null == user.getName() ? "" : user.getName());
            loginLog.setStatus("1");
            loginLog.setType(user.getType());
            loginLog.setIp(ip);
            String addr = IPSeekers.getInstance().getAddress(ip);
            loginLog.setAddr(null == addr ? "未知" : addr);
            loginLog.setFacility("未知");
            loginLogSet.Add(loginLog);
        }
        user.setLoginKey(loginKey);
        user.setLastloginTime(System.currentTimeMillis());
        return userSet.Update(user.getId(), user) > 0 ? this.getUserByPhone(this.getPhone()) : null;
    }

    /**
     * 修改
     */
    public boolean updateById() {
        if (StringUtils.isEmpty(this.getId())) {
            throw new OtherExcetion("请选择要修改的用户");
        }
        UserSet userSet = Holder.getBean(UserSet.class);
        return userSet.Update(this.getId(), this) > 0;
    }

    /**
     * 删除
     */
    public boolean deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的用户");
        }
        UserSet userSet = Holder.getBean(UserSet.class);
        return userSet.Delete(id) > 0;
    }

    /**
     * 查询单个
     */
    public User getById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要查询的用户");
        }
        UserSet userSet = Holder.getBean(UserSet.class);
        User uer = userSet.Get(id);
        return uer;
    }

    public User getUserByPhone(String phone) {
        UserSet userSet = Holder.getBean(UserSet.class);
        User uer = userSet.Where("phone=?", phone).First();
        return uer;
    }

    public User getUserByLoginKey(String loginKey) {
        UserSet userSet = Holder.getBean(UserSet.class);
        User user = userSet.Where("loginKey=?", loginKey).First();
        return user;
    }

}
