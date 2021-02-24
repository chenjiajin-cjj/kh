package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.FriendApplySet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 好友申请表
 */
public class FriendApply  extends ViewStore {

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

    /* userId */
    @JsonProperty(value = "userId")
    public String getUserId() {
        return (String) get("userId");
    }

    @JsonProperty(value = "userId")
    public void setUserId(String userId) {
        set("userId", userId);
    }

    /* friendId */
    @JsonProperty(value = "friendId")
    public String getFriendId() {
        return (String) get("friendId");
    }

    @JsonProperty(value = "friendId")
    public void setFriendId(String friendId) {
        set("friendId", friendId);
    }

    /* 朋友申请理由 apply */
    @JsonProperty(value = "apply")
    public String getApply() {
        return (String) get("apply");
    }

    @JsonProperty(value = "apply")
    public void setApply(String apply) {
        set("apply", apply);
    }

    /* 申请状态 1是通过 2是未审核 3是拒绝 status */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 经营品牌 */
    @JsonProperty(value = "brand")
    public List<Brand> getBrand() {
        return (List<Brand>) get("brand");
    }

    @JsonProperty(value = "brand")
    public void setBrand(List<Brand> brand) {
        set("brand", brand);
    }

    /**
     * 修改申请表
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的");
        }
        FriendApplySet friendApplySet = Holder.getBean(FriendApplySet.class);
        return friendApplySet.Update(this.getId(),this) > 0;
    }

    /**
     * 删除申请表
     */
    public boolean deleteById(String id){
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要修改的商品");
        }
        FriendApplySet friendApplySet = Holder.getBean(FriendApplySet.class);
        return friendApplySet.Delete(id) > 0;
    }

    /**
     * 查询单个
     */
    public FriendApply getById(String id){
        FriendApplySet friendApplySet = Holder.getBean(FriendApplySet.class);
        return friendApplySet.Get(id);
    }


}
