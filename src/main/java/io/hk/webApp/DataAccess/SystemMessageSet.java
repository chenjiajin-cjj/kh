package io.hk.webApp.DataAccess;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Messages;
import io.hk.webApp.Domain.SystemMessage;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.TablePagePars;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Hashtable;

@Repository(value = "SystemMessageSet")
@Scope(value = "prototype")
public class SystemMessageSet extends Set<SystemMessage> {

    public SystemMessageSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<SystemMessage> getType() {
        return SystemMessage.class;
    }


    public Object searchMessage(Admin admin, TablePagePars pagePars) {
        BasicDBObject whereBson = buildWhere(pagePars.Pars);
        IMongoQuery<SystemMessage> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        PageData<SystemMessage> data =new PageData<>();
        data.rows = query.Limit(pagePars.PageSize,pagePars.PageIndex).ToList();
        data.total = this.Where(whereBson).Count();
        return data;
    }

    private BasicDBObject buildWhere(Hashtable<String, Object> where){
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        if(null == where || where.size() == 0){
            return whereBson;
        }
        for (String key : where.keySet()){
            switch (key){
                case "adminId":{
                    values.add(ExpCal.Analysis("adminId=?",where.get(key).toString()));
                    break;
                }
                case "titleMessage":{
                    values.add(ExpCal.Analysis("titleMessage like?",where.get(key).toString()));
                    break;
                }
                case "beginTime":{
                    values.add(ExpCal.Analysis("ctime>=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
                case "endTime":{
                    values.add(ExpCal.Analysis("ctime<=?",Long.parseLong(where.get(key).toString())));
                    break;
                }
            }
        }
        whereBson.append("$and",values);
        return whereBson;
    }

}
