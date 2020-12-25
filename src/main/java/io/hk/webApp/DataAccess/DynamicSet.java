package io.hk.webApp.DataAccess;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Dynamic;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="DynamicSet")
@Scope(value="prototype")
public class DynamicSet extends Set<Dynamic> {

    public DynamicSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Dynamic> getType() {
        return Dynamic.class;
    }

}
