package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.PlatformSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

import javax.print.attribute.standard.MediaSize;

public class Platform extends ViewStore {

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

    /* 平台名称 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 平台标题 title */
    @JsonProperty(value = "title")
    public String getTitle() {
        return (String) get("title");
    }

    @JsonProperty(value = "title")
    public void setTitle(String title) {
        set("title", title);
    }

    /* 平台描述 details */
    @JsonProperty(value = "details")
    public String getDetails() {
        return (String) get("details");
    }

    @JsonProperty(value = "details")
    public void setDetails(String details) {
        set("details", details);
    }

    /* 关键词 keyword */
    @JsonProperty(value = "keyword")
    public String getKeyword() {
        return (String) get("keyword");
    }

    @JsonProperty(value = "keyword")
    public void setKeyword(String keyword) {
        set("keyword", keyword);
    }

    /* 平台LOGO logo */
    @JsonProperty(value = "logo")
    public String getLogo() {
        return (String) get("logo");
    }

    @JsonProperty(value = "logo")
    public void setLogo(String logo) {
        set("logo", logo);
    }

    /* 页脚 footer */
    @JsonProperty(value = "footer")
    public String getFooter() {
        return (String) get("footer");
    }

    @JsonProperty(value = "footer")
    public void setFooter(String footer) {
        set("footer", footer);
    }

    /* 注册用户协议 footer */
    @JsonProperty(value = "agreement")
    public String getAgreement() {
        return (String) get("agreement");
    }

    @JsonProperty(value = "agreement")
    public void setAgreement(String agreement) {
        set("agreement", agreement);
    }


    public boolean updateById(){
        PlatformSet set = Holder.getBean(PlatformSet.class);
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择");
        }
        return set.Update(this.getId(),this) > 0;
    }


}
