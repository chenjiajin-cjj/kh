package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.AuthSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

/**
 * 账号认证
 */
public class Auth extends ViewStore {

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

    /* 用户id userId */
    @JsonProperty(value = "userId")
    public String getUserId() {
        return (String) get("userId");
    }

    @JsonProperty(value = "userId")
    public void setUserId(String userId) {
        set("userId", userId);
    }

    /* 认证类型 企业/个人 authType */
    @JsonProperty(value = "authType")
    public String getAuthType() {
        return (String) get("authType");
    }

    @JsonProperty(value = "authType")
    public void setAuthType(String authType) {
        set("authType", authType);
    }
    /* 企业名称 company */
    @JsonProperty(value = "company")
    public String getCompany() {
        return (String) get("company");
    }

    @JsonProperty(value = "company")
    public void setCompany(String company) {
        set("company", company);
    }
    /* 营业执照url businessImg */
    @JsonProperty(value = "businessImg")
    public String getBusinessImg() {
        return (String) get("businessImg");
    }

    @JsonProperty(value = "businessImg")
    public void setBusinessImg(String businessImg) {
        set("businessImg", businessImg);
    }
    /* 法人代表姓名 legalName */
    @JsonProperty(value = "legalName")
    public String getLegalName() {
        return (String) get("legalName");
    }

    @JsonProperty(value = "legalName")
    public void setLegalName(String legalName) {
        set("legalName", legalName);
    }
    /* 法人代表电话 legalPhone */
    @JsonProperty(value = "legalPhone")
    public String getLegalPhone() {
        return (String) get("legalPhone");
    }

    @JsonProperty(value = "legalPhone")
    public void setLegalPhone(String legalPhone) {
        set("legalPhone", legalPhone);
    }
    /* 法人代表身份证号 legalIdCard */
    @JsonProperty(value = "legalIdCard")
    public String getLegalIdCard() {
        return (String) get("legalIdCard");
    }

    @JsonProperty(value = "legalIdCard")
    public void setLegalIdCard(String legalIdCard) {
        set("legalIdCard", legalIdCard);
    }
    /* 法人代表身份证url legalIdCardImg */
    @JsonProperty(value = "legalIdCardImg")
    public String getLegalIdCardImg() {
        return (String) get("legalIdCardImg");
    }

    @JsonProperty(value = "legalIdCardImg")
    public void setLegalIdCardImg(String legalIdCardImg) {
        set("legalIdCardImg", legalIdCardImg);
    }
    /* 公司实景url companyImg */
    @JsonProperty(value = "companyImg")
    public String getCompanyImg() {
        return (String) get("companyImg");
    }

    @JsonProperty(value = "companyImg")
    public void setCompanyImg(String companyImg) {
        set("companyImg", companyImg);
    }


    /**
     * 根据userId获取
     */
    public Auth getById(String userId){
        if(StringUtils.isEmpty(userId)){
            throw new OtherExcetion("操作失败");
        }
        AuthSet authSet = Holder.getBean(AuthSet.class);
        Auth auth = authSet.Where("userId=?",userId).First();
        if(null == auth){
            auth = new Auth();
            auth.setUserId(userId);
            authSet.Add(auth);
        }
        return auth;
    }

    /**
     * 根据Id修改
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("操作失败");
        }
        AuthSet authSet = Holder.getBean(AuthSet.class);
        return authSet.Update(this.getId(),this) > 0;
    }

}
