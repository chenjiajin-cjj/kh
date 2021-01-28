package io.hk.webApp.DataAccess;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.hk.webApp.Domain.FriendApply;
import io.hk.webApp.Domain.Scheme;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Friends;

import java.util.Hashtable;
import java.util.List;

@Repository(value = "FactoryFriendsSet")
@Scope(value = "prototype")
public class FriendsSet extends Set<Friends> {

    public FriendsSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Friends> getType() {
        return Friends.class;
    }

    /**
     * 查询客户审核列表
     * @param pars
     * @param pageSize
     * @param pageIndex
     * @param order
     * @return
     */
    public PageData<Friends> search(Hashtable<String, Object> pars, int pageSize, int pageIndex, String order, String userId) {
        List<Friends> list = this.Where("(userId=?)and(status=?)",userId).Limit(pageSize,pageIndex).OrderByDesc("_ctime").ToList();
        long count = this.Where("(userId=?)and(status=?)",userId).Count();
        PageData<Friends> pageData = new PageData<>();
        pageData.rows = list;
        pageData.total = count;
        return pageData;
    }

    public List<Friends> searchFactorys(Hashtable<String, Object> where, int pageSize, int pageIndex, String userId) {
        BasicDBObject whereBson = buildWhere(where,userId);
        IMongoQuery<Friends> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        return query.Limit(pageSize,pageIndex).ToList();
    }

    /**
     * 构建查询商品列表的 where
     * @param where
     * @return
     */
    private BasicDBObject buildWhere(Hashtable<String, Object> where, String userId){
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(ExpCal.Analysis("userId=?",userId));
        if(null == where || where.size() == 0){
            whereBson.append("$and",values);
            return whereBson;
        }

        for (String key : where.keySet()){
            switch (key){
                case "kernel":{
                    values.add(ExpCal.Analysis("kernel=?",where.get(key).toString()));
                    break;
                }
            }
        }
        whereBson.append("$and",values);
        return whereBson;
    }
}
