package io.hk.webApp.DataAccess;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.BrandSystem;
import io.hk.webApp.Tools.TablePagePars;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;

@Repository(value = "AdminSet")
@Scope(value = "prototype")
public class AdminSet extends Set<Admin> {

    public AdminSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Admin> getType() {
        return Admin.class;
    }

    public PageData<Admin> search(TablePagePars pagePars) {
        BasicDBObject whereBson = buildWhere(pagePars.Pars);
        IMongoQuery<Admin> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        PageData<Admin> data = new PageData<>();
        data.rows = query.Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
        data.rows.forEach((a) -> {
            a.setPassword(null);
            a.setLoginKey(null);
        });
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
                case "name": {
                    values.add(ExpCal.Analysis("name like?", where.get(key).toString()));
                    break;
                }
            }
        }
        if(where.size() > 0){
            whereBson.append("$and", values);
        }
        return whereBson;
    }
}
