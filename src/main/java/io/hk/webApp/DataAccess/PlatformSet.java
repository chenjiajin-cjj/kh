package io.hk.webApp.DataAccess;


import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Platform;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="PlatformSet")
@Scope(value="prototype")
public class PlatformSet extends Set<Platform> {

    public PlatformSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Platform> getType() {
        return Platform.class;
    }

}
