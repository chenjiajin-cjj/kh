package io.hk.webApp.DataAccess;


import io.framecore.Frame.PageData;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.FriendApply;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;
import java.util.List;

@Repository(value="FriendApplySet")
@Scope(value="prototype")
public class FriendApplySet extends Set<FriendApply> {

    public FriendApplySet() {
        super(_db.getDbName());
    }

    @Override
    public Class<FriendApply> getType() {
        return FriendApply.class;
    }

    /**
     * 查询客户未审核列表
     * @param pars
     * @param pageSize
     * @param pageIndex
     * @param order
     * @return
     */
    public PageData<FriendApply> searchNo(Hashtable<String, Object> pars, int pageSize, int pageIndex, String order, String userId, String status) {
        List<FriendApply> list = this.Where("(friendId=?)and(status!=?)",userId, status).Limit(pageSize,pageIndex).OrderByDesc("_ctime").ToList();
        long count = this.Where("(friendId=?)and(status!=?)",userId,status).Count();
        PageData<FriendApply> pageData = new PageData<>();
        pageData.rows = list;
        pageData.total = count;
        return pageData;
    }

}
