package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Orm.ViewStore;

public class Blacklist extends ViewStore {

    // _id
    @JsonProperty(value = "_id")
    public String getId() {
        if (get("_id") == null) {
            return null;
        }
        return get("_id").toString();
    }

    @JsonProperty(value = "_id")
    public void setId(String Id) {
        set("_id", Id);
    }

    /* factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
    }

    /* salerId */
    @JsonProperty(value = "salerId")
    public String getSalerId() {
        return (String) get("salerId");
    }

    @JsonProperty(value = "salerId")
    public void setSalerId(String salerId) {
        set("salerId", salerId);
    }

}
