package io.hk.webApp.DataAccess;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Tools.TablePagePars;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;

@Repository(value = "OperationLogSet")
@Scope(value = "prototype")
public class OperationLogSet extends Set<OperationLog> {

    public OperationLogSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<OperationLog> getType() {
        return OperationLog.class;
    }

    /**
     * 查询操作记录
     *
     * @param admin
     * @param pagePars
     * @return
     */
    public PageData<OperationLog> search(Admin admin, TablePagePars pagePars) {
        BasicDBObject whereBson = buildWhere(pagePars.Pars);
        IMongoQuery<OperationLog> query = this.Where(whereBson);
        query.OrderByDesc("ctime");
        PageData<OperationLog> data = new PageData<>();
        data.rows = query.Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
        data.total = this.Where(whereBson).Count();
        return data;
    }

    /**
     * 构建查询商品列表的 where
     *
     * @param where
     * @return
     * @throws Exception
     */
    private BasicDBObject buildWhere(Hashtable<String, Object> where) {
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        if (null == where || where.size() == 0) {
            return whereBson;
        }
        for (String key : where.keySet()) {
            switch (key) {
                case "account": {
                    values.add(ExpCal.Analysis("account=?", where.get(key).toString()));
                    break;
                }
                case "beginTime": {
                    values.add(ExpCal.Analysis("ctime>=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
                case "endTime": {
                    values.add(ExpCal.Analysis("ctime<=?", Long.parseLong(where.get(key).toString())));
                    break;
                }
            }
        }
        whereBson.append("$and", values);
        return whereBson;
    }
}
