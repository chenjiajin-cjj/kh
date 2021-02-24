package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.BlacklistSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

/**
 * 黑名单
 */
public class Blacklist extends ViewStore {

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

    /* 被拉入黑名单的id blackId */
    @JsonProperty(value = "blackId")
    public String getBlackId() {
        return (String) get("blackId");
    }

    @JsonProperty(value = "blackId")
    public void setBlackId(String blackId) {
        set("blackId", blackId);
    }

    /**
     * 修改
     */
    public boolean updateById(Dynamic dynamic){
        if(StringUtils.isEmpty(dynamic.getId())){
            throw new OtherExcetion("请选择要修改的黑名单");
        }
        BlacklistSet blacklistSet = Holder.getBean(BlacklistSet.class);
        return blacklistSet.Update(dynamic.getId(),dynamic) > 0;
    }

    /**
     * 删除
     */
    public boolean deleteById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要删除的动态");
        }
        BlacklistSet blacklistSet = Holder.getBean(BlacklistSet.class);
        return blacklistSet.Delete(this.getId()) > 0;
    }

    /**
     * 查询单个
     */
    public Blacklist getById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择黑名单");
        }
        BlacklistSet blacklistSet = Holder.getBean(BlacklistSet.class);
        return blacklistSet.Get(id);
    }

}
