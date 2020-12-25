package io.hk.webApp.DataAccess;


import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Admin;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="AdminSet")
@Scope(value="prototype")
public class AdminSet extends Set<Admin> {

    public AdminSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Admin> getType() {
        return Admin.class;
    }

}
