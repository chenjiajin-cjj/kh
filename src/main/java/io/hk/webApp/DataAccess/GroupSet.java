package io.hk.webApp.DataAccess;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Group;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="GroupsSet")
@Scope(value="prototype")
public class GroupSet extends Set<Group> {

    public GroupSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Group> getType() {
        return Group.class;
    }

}
