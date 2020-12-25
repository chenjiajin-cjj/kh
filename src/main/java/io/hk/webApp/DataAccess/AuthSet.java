package io.hk.webApp.DataAccess;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Auth;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="AuthSet")
@Scope(value="prototype")
public class AuthSet extends Set<Auth> {

    public AuthSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Auth> getType() {
        return Auth.class;
    }

}
