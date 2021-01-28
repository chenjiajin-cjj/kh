package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;


public class Choose extends ViewStore {

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

    /* 选择的商品的id saleGoodsId */
    @JsonProperty(value = "saleGoodsId")
    public String getSaleGoodsId() {
        return (String) get("saleGoodsId");
    }

    @JsonProperty(value = "saleGoodsId")
    public void setSaleGoodsId(String saleGoodsId) {
        set("saleGoodsId", saleGoodsId);
    }

    /* 选择的商品的图片 productImg */
    @JsonProperty(value = "productImg")
    public String getProductImg() {
        return (String) get("productImg");
    }

    @JsonProperty(value = "productImg")
    public void setProductImg(String productImg) {
        set("productImg", productImg);
    }

}
