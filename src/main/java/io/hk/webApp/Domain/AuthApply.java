package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.AuthApplySet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

public class AuthApply extends ViewStore {

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


    /* 账号 phone */
    @JsonProperty(value = "phone")
    public String getPhone() {
        return (String) get("phone");
    }

    @JsonProperty(value = "phone")
    public void setPhone(String phone) {
        set("phone", phone);
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


    /* 创建时间 createTime */
    @JsonProperty(value = "createTime")
    public Long getCreateTime() {
        return (Long) get("createTime");
    }

    @JsonProperty(value = "createTime")
    public void setCreateTime(Long createTime) {
        set("createTime", createTime);
    }

    /* 状态 status 1是通过 2是未审核 3是已拒绝*/
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 反馈详情  feedback*/
    @JsonProperty(value = "feedback")
    public String getFeedback() {
        return (String) get("feedback");
    }

    @JsonProperty(value = "feedback")
    public void setFeedback(String feedback) {
        set("feedback", feedback);
    }
    /**
     * 根据id修改
     *
     * @return
     */
    public boolean updateById() {
        if (StringUtils.isEmpty(this.getId())) {
            throw new OtherExcetion("请选择");
        }
        AuthApplySet authApplySet = Holder.getBean(AuthApplySet.class);
        return authApplySet.Update(this.getId(), this) > 0;
    }
}
