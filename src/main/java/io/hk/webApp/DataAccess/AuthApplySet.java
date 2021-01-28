package io.hk.webApp.DataAccess;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.AuthApply;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Tools.TablePagePars;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;

@Repository(value = "AuthApply")
@Scope(value = "prototype")
public class AuthApplySet extends Set<AuthApply> {

    public AuthApplySet() {
        super(_db.getDbName());
    }

    @Override
    public Class<AuthApply> getType() {
        return AuthApply.class;
    }

    public Object search(TablePagePars pagePars) {
        PageData<AuthApply> pageData = new PageData<>();
        BasicDBObject whereBson = buildWhere(pagePars.Pars);
        IMongoQuery<AuthApply> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        pageData.rows = query.Limit(pagePars.PageSize,pagePars.PageIndex).ToList();
        pageData.total = this.Where(whereBson).Count();
        return pageData;
    }


    private BasicDBObject buildWhere(Hashtable<String, Object> where){
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        if(null == where || where.size() == 0){
            return whereBson;
        }
        for (String key : where.keySet()){
            switch (key){
                case "phone":{
                    values.add(ExpCal.Analysis("phone like?",where.get(key).toString()));
                    break;
                }
                case "status":{
                    values.add(ExpCal.Analysis("status=?",where.get(key).toString()));
                    break;
                }
            }
        }
        whereBson.append("$and",values);
        return whereBson;
    }

}
