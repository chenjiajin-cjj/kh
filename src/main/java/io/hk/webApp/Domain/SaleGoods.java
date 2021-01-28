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

    /* 供应商id factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
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
    @JsonProperty(value = "productBean")
    public Product getProductBean() {
        return (Product) get("productBean");
    }

    @JsonProperty(value = "productBean")
    public void setProductBean(Product productBean) {
        set("productBean", productBean);
    }


    /* 自有商品的标记 1就是自有2就不是 type */
    @JsonProperty(value = "type")
    public String getType() {
        return (String) get("type");
    }

    @JsonProperty(value = "type")
    public void setType(String type) {
        set("type", type);
    }

    /* 设为主推 1是主推2不是  */
    @JsonProperty(value = "recommend")
    public String getRecommend() {
        return (String) get("recommend");
    }

    @JsonProperty(value = "recommend")
    public void setRecommend(String recommend) {
        set("recommend", recommend);
    }

    /* 商品的价格不含税 price */
    @JsonProperty(value = "price")
    public Double getPrice() {
        return (Double) get("price");
    }

    @JsonProperty(value = "price")
    public void setPrice(Double price) {
        set("price", price);
    }

    /* 商品的价格含税 priceTax */
    @JsonProperty(value = "priceTax")
    public Double getPriceTax() {
        return (Double) get("priceTax");
    }

    @JsonProperty(value = "priceTax")
    public void setPriceTax(Double priceTax) {
        set("priceTax", priceTax);
    }

    /* 是否显示1是2否 shield */
    @JsonProperty(value = "shield")
    public String getShield() {
        return (String) get("shield");
    }

    @JsonProperty(value = "shield")
    public void setShield(String shield) {
        set("shield", shield);
    }


    /* 是否认证1是2否 authentication */
    @JsonProperty(value = "authentication")
    public Boolean getAuthentication() {
        return (Boolean) get("authentication");
    }

    @JsonProperty(value = "authentication")
    public void setAuthentication(Boolean authentication) {
        set("authentication", authentication);
    }

    /* 公司名  cname */
    @JsonProperty(value = "cname")
    public String getCName() {
        return (String) get("cname");
    }

    @JsonProperty(value = "cname")
    public void setCName(String cname) {
        set("cname", cname);
    }

    /* 品牌 brandId */
    @JsonProperty(value = "brandId")
    public String getBrandId() {
        return (String) get("brandId");
    }

    @JsonProperty(value = "brandId")
    public void setBrandId(String brandId) {
        set("brandId", brandId);
    }

    /* 商品分组 productGroup */
    @JsonProperty(value = "productGroup")
    public String getProductGroup(){
        return (String) get("productGroup");
    }

    @JsonProperty(value = "productGroup")
    public void setProductGroup(String productGroup){
        set("productGroup",productGroup);
    }

    /* 热销标签 1是2否 tagHot */
    @JsonProperty(value = "tagHot")
    public String getTagHot(){
        return (String) get("tagHot");
    }
    @JsonProperty(value = "tagHot")
    public void setTagHot(String tagHot){
        set("tagHot",tagHot);
    }

    /* 推荐标签 1是2否 tagRec */
    @JsonProperty(value = "tagRec")
    public String getTagRec(){
        return (String) get("tagRec");
    }
    @JsonProperty(value = "tagRec")
    public void setTagRec(String tagRec){
        set("tagRec",tagRec);
    }

    /* 新品标签 1是2否 tagNew */
    @JsonProperty(value = "tagNew")
    public String getTagNew(){
        return (String) get("tagNew");
    }
    @JsonProperty(value = "tagNew")
    public void setTagNew(String tagNew){
        set("tagNew",tagNew);
    }

    /* categoryCode */
    @JsonProperty(value = "categoryCode")
    public String getCategoryCode() {
        return (String) get("categoryCode");
    }

    @JsonProperty(value = "categoryCode")
    public void setCategoryCode(String categoryCode) {
        set("categoryCode", categoryCode);
    }

    /* 商品名称 name */
    @JsonProperty(value = "name")
    public String getName() {
        return (String) get("name");
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        set("name", name);
    }

    /* 分类 classifyId */
    @JsonProperty(value = "classifyId")
    public String getClassifyId(){
        return (String) get("classifyId");
    }
    @JsonProperty(value = "classifyId")
    public void setClassifyId(String classifyId){
        set("classifyId",classifyId);
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

