package io.hk.webApp.DataAccess;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Choose;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="ChooseSet")
@Scope(value="prototype")
public class ChooseSet extends Set<Choose> {

    public ChooseSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Choose> getType() {
        return Choose.class;
    }

}
