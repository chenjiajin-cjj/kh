package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.SaleGoodsSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

/**
 * 分销商销售商品
 */
public class SaleGoods extends ViewStore {

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

    /* 商品的数量 number */
    @JsonProperty(value = "number")
    public Long getNumber() {
        return (Long) get("number");
    }

    @JsonProperty(value = "number")
    public void setNumber(Long number) {
        set("number", number);
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

    /* 商品的文档对象 product */
    @JsonProperty(value = "product")
    public Document getProduct() {
        return (Document) get("product");
    }

    @JsonProperty(value = "product")
    public void setProduct(Document product) {
        set("product", product);
    }

    /* status, 1:上架，2下架 */
    @JsonProperty(value = "status")
    public String getStatus(){
        return (String) get("status");
    }
    @JsonProperty(value = "status")
    public void setStatus(String status){
        set("status",status);
    }


    /* 商品的实体对象 productBean */
//    @JsonProperty(value = "productBean")
//    public Product getProductBean() {
//        return (Product) get("productBean");
//    }
//
//    @JsonProperty(value = "productBean")
//    public void setProductBean(Product productBean) {
//        set("productBean", productBean);
//    }


    /* 自有商品的标记 1就是自有2就不是 type */
    @JsonProperty(value = "type")
    public String getType() {
        return (String) get("type");
    }

    @JsonProperty(value = "type")
    public void setType(String type) {
        set("type", type);
    }

    /* 设为主推  */
    @JsonProperty(value = "recommend")
    public Boolean getRecommend() {
        return (Boolean) get("recommend");
    }

    @JsonProperty(value = "recommend")
    public void setRecommend(Boolean recommend) {
        set("recommend", recommend);
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



    /**
     * 修改
     */
    public boolean updateById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要修改的商品");
        }
        SaleGoodsSet saleGoodsSet = Holder.getBean(SaleGoodsSet.class);
        return saleGoodsSet.Update(this.getId(),this) > 0;
    }

    /**
     * 删除
     */
    public boolean deleteById(){
        if(StringUtils.isEmpty(this.getId())){
            throw new OtherExcetion("请选择要删除的商品");
        }
        SaleGoodsSet saleGoodsSet = Holder.getBean(SaleGoodsSet.class);
        return saleGoodsSet.Delete(this.getId()) > 0;
    }

}

