package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.AdminSet;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class Admin extends ViewStore {

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

    /* 账号 account */
    @JsonProperty(value = "account")
    public String getAccount() {
        return (String) get("account");
    }

    @JsonProperty(value = "account")
    public void setAccount(String account) {
        set("account", account);
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

    /* 状态 status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
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
    public Date getLastloginTime() {
        return (Date) get("lastloginTime");
    }

    @JsonProperty(value = "lastloginTime")
    public void setLastloginTime(Date lastloginTime) {
        set("lastloginTime", lastloginTime);
    }


    /**
     * 修改单个管理员
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的商品");
        }
        AdminSet adminSet = Holder.getBean(AdminSet.class);
        return adminSet.Update(this.getId(),this) > 0;
    }

    /**
     * 删除单个管理员
     */
    public boolean deleteById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要修改的商品");
        }
        AdminSet adminSet = Holder.getBean(AdminSet.class);
        return adminSet.Delete(id) > 0;
    }

    /**
     * 查询单个管理员
     */
    public Admin getById(String id){
        AdminSet adminSet = Holder.getBean(AdminSet.class);
        return adminSet.Get(id);
    }


}
