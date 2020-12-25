package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;
import org.bson.Document;

import java.util.List;

public class ShareProduces extends ViewStore {

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

    /* 经销商的id salerId */
    @JsonProperty(value = "salerId")
    public String getSalerId() {
        return (String) get("salerId");
    }

    @JsonProperty(value = "salerId")
    public void setSalerId(String salerId) {
        set("salerId", salerId);
    }

    /* 供应商id factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
    }

    /* 方案状态 1进行中 2已完成 */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 分享的商品们 */
    @JsonProperty(value = "products")
    public List<Document> getProducts() {
        return (List<Document> ) get("products");
    }

    @JsonProperty(value = "products")
    public void setProducts(List<Document>  products) {
        set("products", products);
    }

    /* 供应商名 展示查询用 */
    @JsonProperty(value = "factoryName")
    public String getFactoryName() {
        return (String) get("factoryName");
    }

    @JsonProperty(value = "factoryName")
    public void setFactoryName(String factoryName) {
        set("factoryName", factoryName);
    }


}
