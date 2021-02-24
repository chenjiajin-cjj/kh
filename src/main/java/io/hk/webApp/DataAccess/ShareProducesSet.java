package io.hk.webApp.DataAccess;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.ShareProduces;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value = "ShareProducesSet")
@Scope(value = "prototype")
public class ShareProducesSet extends Set<ShareProduces> {

    public ShareProducesSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<ShareProduces> getType() {
        return ShareProduces.class;
    }

}
