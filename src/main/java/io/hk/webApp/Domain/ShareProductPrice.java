package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;

public class ShareProductPrice extends ViewStore {
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

    /* 经销商id salerId */
    @JsonProperty(value = "salerId")
    public String getSalerId() {
        return (String) get("salerId");
    }

    @JsonProperty(value = "salerId")
    public void setSalerId(String salerId) {
        set("salerId", salerId);
    }

    /* 商品的id productId */
    @JsonProperty(value = "productId")
    public String getProductId() {
        return (String) get("productId");
    }

    @JsonProperty(value = "productId")
    public void setProductId(String productId) {
        set("productId", productId);
    }

    /* 商品的价格 price */
    @JsonProperty(value = "price")
    public double getPrice() {
        return (double) get("price");
    }

    @JsonProperty(value = "price")
    public void setPrice(double price) {
        set("price", price);
    }
}
