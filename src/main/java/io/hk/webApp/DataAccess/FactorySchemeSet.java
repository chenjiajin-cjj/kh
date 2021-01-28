package io.hk.webApp.DataAccess;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.FactoryScheme;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;

@Repository(value="FactorySchemeSet")
@Scope(value="prototype")
public class FactorySchemeSet extends Set<FactoryScheme> {

    public FactorySchemeSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<FactoryScheme> getType() {
        return FactoryScheme.class;
    }

    /**
     * 查询供应商方案列表
     * @param pars
     * @param pageSize
     * @param pageIndex
     * @param userId
     * @return
     */
    public PageData<FactoryScheme> search(Hashtable<String, Object> pars, int pageSize, int pageIndex, String userId) {
        BasicDBObject whereBson = buildWhere(pars,userId);
        IMongoQuery<FactoryScheme> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        PageData<FactoryScheme> data =new PageData<>();
        data.rows = query.Limit(pageSize,pageIndex).ToList();
        data.total = this.Where(whereBson).Count();
        return data;
    }


    /**
     * 构建查询商品列表的 where
     * @param where
     * @return
     * @throws Exception
     */
    private BasicDBObject buildWhere(Hashtable<String, Object> where, String factoryId){
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(ExpCal.Analysis("factoryId=?",factoryId));
        if(null == where || where.size() == 0){
            whereBson.append("$and",values);
            return whereBson;
        }
        for (String key : where.keySet()){
            switch (key){
                case "name":{
                    values.add(ExpCal.Analysis("name like?",where.get(key).toString()));
                    break;
                }
                case "beginTime":{
                    values.add(ExpCal.Analysis("validity>=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
                case "endTime":{
                    values.add(ExpCal.Analysis("validity<=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
            }
        }
        whereBson.append("$and",values);
        return whereBson;
    }
}
