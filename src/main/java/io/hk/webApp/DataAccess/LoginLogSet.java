package io.hk.webApp.DataAccess;


import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.LoginLog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="LoginLogSet")
@Scope(value="prototype")
public class LoginLogSet extends Set<LoginLog> {

    public LoginLogSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<LoginLog> getType() {
        return LoginLog.class;
    }

}
