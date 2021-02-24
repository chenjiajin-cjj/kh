package io.hk.webApp.DataAccess;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import java.util.Hashtable;

/**
 * 方案
 */
@Repository(value="SchemeSet")
@Scope(value="prototype")
public class SchemeSet extends Set<Scheme> {
    public SchemeSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Scheme> getType() {
        return Scheme.class;
    }

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    /**
     * 查询经销商自己的方案列表
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public PageData<Scheme> search(int pageSize, int pageIndex, Hashtable<String, Object> where,String salerId) {
        BasicDBObject whereBson = buildWhere(where,salerId);
        IMongoQuery<Scheme> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        PageData<Scheme> data = new PageData<>();
        data.rows = query.Limit(pageSize,pageIndex).ToList();
        data.total = this.Where(whereBson).Count();
        return data;
    }


    /**
     * 构建查询商品列表的 where
     * @param where
     * @return
     */
    private BasicDBObject buildWhere(Hashtable<String, Object> where, String salerId){
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(ExpCal.Analysis("salerId=?",salerId));
        if(null == where || where.size() == 0){
            whereBson.append("$and",values);
            return whereBson;
        }
        for (String key : where.keySet()){
            switch (key){
                case "beginTime":{
                    values.add(ExpCal.Analysis("validity>=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
                case "endTime":{
                    values.add(ExpCal.Analysis("validity<=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
                case "status":{
                    values.add(ExpCal.Analysis("status=?",where.get(key).toString()));
                    break;
                }
                case "name":{
                    values.add(ExpCal.Analysis("name like?",where.get(key).toString()));
                    break;
                }
            }
        }
        whereBson.append("$and",values);
        return whereBson;
    }


}