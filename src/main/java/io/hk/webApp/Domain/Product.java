package io.hk.webApp.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.framecore.Aop.Holder;
import io.framecore.Orm.ViewStore;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品表
 */
public class Product extends ViewStore {

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

    /* 供应商id factoryId */
    @JsonProperty(value = "factoryId")
    public String getFactoryId() {
        return (String) get("factoryId");
    }

    @JsonProperty(value = "factoryId")
    public void setFactoryId(String factoryId) {
        set("factoryId", factoryId);
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

    /* code */
//	@JsonProperty(value = "code")
//	public String getCode() {
//		return (String) get("code");
//	}
//
//	@JsonProperty(value = "code")
//	public void setCode(String code) {
//		set("code", code);
//	}

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

    /* 主图 image1 */
    @JsonProperty(value = "image1")
    public String getImage1() {
        return (String) get("image1");
    }

    @JsonProperty(value = "image1")
    public void setImage1(String image1) {
        set("image1", image1);
    }

    /* 小图 image2 */
    @JsonProperty(value = "image2")
    public String getImage2() {
        return (String) get("image2");
    }

    @JsonProperty(value = "image2")
    public void setImage2(String image2) {
        set("image2", image2);
    }

    /* 型号 model */
    @JsonProperty(value = "model")
    public String getModel() {
        return (String) get("model");
    }

    @JsonProperty(value = "model")
    public void setModel(String model) {
        set("model", model);
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
    public String getProductGroup() {
        return (String) get("productGroup");
    }

    @JsonProperty(value = "productGroup")
    public void setProductGroup(String productGroup) {
        set("productGroup", productGroup);
    }

    /* 商品标签 saleTagList, saleTag_{code} */
//	@JsonProperty(value = "saleTagList")
//	public Map<String,Boolean> getSaleTagList() {
//		return (Map<String,Boolean>) get("saleTagList");
//	}
//
//	@JsonProperty(value = "saleTagList")
//	public void setSaleTagList(Map<String,Boolean> saleTagList) {
//		set("saleTagList", saleTagList);
//
//		//冗余数据
//		for (String saleTag : saleTagList.keySet()) {
//			set("name"+saleTag, saleTagList.get(saleTag));
//		}
//	}

    /* 热销标签 1是2否 tagHot */
    @JsonProperty(value = "tagHot")
    public String getTagHot() {
        return (String) get("tagHot");
    }

    @JsonProperty(value = "tagHot")
    public void setTagHot(String tagHot) {
        set("tagHot", tagHot);
    }

    /* 推荐标签 1是2否 tagRec */
    @JsonProperty(value = "tagRec")
    public String getTagRec() {
        return (String) get("tagRec");
    }

    @JsonProperty(value = "tagRec")
    public void setTagRec(String tagRec) {
        set("tagRec", tagRec);
    }

    /* 新品标签 1是2否 tagNew */
    @JsonProperty(value = "tagNew")
    public String getTagNew() {
        return (String) get("tagNew");
    }

    @JsonProperty(value = "tagNew")
    public void setTagNew(String tagNew) {
        set("tagNew", tagNew);
    }


    /* 京东链接 urlJd */
    @JsonProperty(value = "urlJd")
    public String getUrlJd() {
        return (String) get("urlJd");
    }

    @JsonProperty(value = "urlJd")
    public void setUrlJd(String urlJd) {
        set("urlJd", urlJd);
    }

    /* 淘宝链接 urlTaobao */
    @JsonProperty(value = "urlTaobao")
    public String getUrlTaobao() {
        return (String) get("urlTaobao");
    }

    @JsonProperty(value = "urlTaobao")
    public void setUrlTaobao(String urlTaobao) {
        set("urlTaobao", urlTaobao);
    }


    /* 预设价格1开始 priceBegin1 */
    @JsonProperty(value = "priceBegin1")
    public Double getPriceBegin1() {
        return (Double) get("priceBegin1");
    }

    @JsonProperty(value = "priceBegin1")
    public void setPriceBegin1(Double priceBegin1) {
        set("priceBegin1", priceBegin1);
    }

    /* 预设价格1结束 priceEnd1 */
    @JsonProperty(value = "priceEnd1")
    public Double getPriceEnd1() {
        return (Double) get("priceEnd1");
    }

    @JsonProperty(value = "priceEnd1")
    public void setPriceEnd1(Double priceEnd1) {
        set("priceEnd1", priceEnd1);
    }


    /* 预设价格2开始 priceBegin2 */
    @JsonProperty(value = "priceBegin2")
    public Double getPriceBegin2() {
        return (Double) get("priceBegin2");
    }

    @JsonProperty(value = "priceBegin2")
    public void setPriceBegin2(Double priceBegin2) {
        set("priceBegin2", priceBegin2);
    }

    /* 预设价格2结束 priceEnd2 */
    @JsonProperty(value = "priceEnd2")
    public Double getPriceEnd2() {
        return (Double) get("priceEnd2");
    }

    @JsonProperty(value = "priceEnd2")
    public void setPriceEnd2(Double priceEnd2) {
        set("priceEnd2", priceEnd2);
    }

    /* 供货价含税 supplyPriceTax */
    @JsonProperty(value = "supplyPriceTax")
    public Double getSupplyPriceTax() {
        return (Double) get("supplyPriceTax");
    }

    @JsonProperty(value = "supplyPriceTax")
    public void setSupplyPriceTax(Double supplyPriceTax) {
        set("supplyPriceTax", supplyPriceTax);
    }

    /* 供货价不含税 supplyPriceTaNo */
    @JsonProperty(value = "supplyPriceTaxNo")
    public Double getSupplyPriceTaxNo() {
        return (Double) get("supplyPriceTaxNo");
    }

    @JsonProperty(value = "supplyPriceTaxNo")
    public void setSupplyPriceTaxNo(Double supplyPriceTaxNo) {
        set("supplyPriceTaxNo", supplyPriceTaxNo);
    }

    /* 零售价含税 retailPriceTax */
    @JsonProperty(value = "retailPriceTax")
    public Double getRetailPriceTax() {
        return (Double) get("retailPriceTax");
    }

    @JsonProperty(value = "retailPriceTax")
    public void setRetailPriceTax(Double retailPriceTax) {
        set("retailPriceTax", retailPriceTax);
    }

    /* 零售价不含税 retailPriceTaxNo */
    @JsonProperty(value = "retailPriceTaxNo")
    public Double getRetailPriceTaxNo() {
        return (Double) get("retailPriceTaxNo");
    }

    @JsonProperty(value = "retailPriceTaxNo")
    public void setRetailPriceTaxNo(Double retailPriceTaxNo) {
        set("retailPriceTaxNo", retailPriceTaxNo);
    }

    /* 税率 tax */
    @JsonProperty(value = "tax")
    public Double getTax() {
        return (Double) get("tax");
    }

    @JsonProperty(value = "tax")
    public void setTax(Double tax) {
        set("tax", tax);
    }

    /* PPT展示图 pptImage */
    @JsonProperty(value = "pptImage")
    public String getPptImage() {
        return (String) get("pptImage");
    }

    @JsonProperty(value = "pptImage")
    public void setPptImage(String pptImage) {
        set("pptImage", pptImage);
    }

    /* 基本信息 baseDetail */
    @JsonProperty(value = "baseDetail")
    public String getBaseDetail() {
        return (String) get("baseDetail");
    }

    @JsonProperty(value = "baseDetail")
    public void setBaseDetail(String baseDetail) {
        set("baseDetail", baseDetail);
    }

    /* 卖点 sellingPoint */
    @JsonProperty(value = "sellingPoint")
    public String getSellingPoint() {
        return (String) get("sellingPoint");
    }

    @JsonProperty(value = "sellingPoint")
    public void setSellingPoint(String sellingPoint) {
        set("sellingPoint", sellingPoint);
    }

    /* status, 1:上架，2下架 */
    @JsonProperty(value = "status")
    public String getStatus() {
        return (String) get("status");
    }

    @JsonProperty(value = "status")
    public void setStatus(String status) {
        set("status", status);
    }

    /* 商品详情 Detail */
    @JsonProperty(value = "detail")
    public String getDetail() {
        return (String) get("detail");
    }

    @JsonProperty(value = "detail")
    public void setDetail(String detail) {
        set("detail", detail);
    }

    /* 创建时间 createTime */
    @JsonProperty(value = "createTime")
    public Long getCreateTime() {
        return (Long) get("createTime");
    }

    @JsonProperty(value = "createTime")
    public void setCreateTime(Long createTime) {
        set("createTime", createTime);
    }

    /* 品牌名 brandName */
    @JsonProperty(value = "brandName")
    public String getBrandName() {
        return (String) get("brandName");
    }

    @JsonProperty(value = "brandName")
    public void setBrandName(String brandName) {
        set("brandName", brandName);
    }

    /* 分组名 categoryName */
    @JsonProperty(value = "categoryName")
    public String getCategoryName() {
        return (String) get("categoryName");
    }

    @JsonProperty(value = "categoryName")
    public void setCategoryName(String categoryName) {
        set("categoryName", categoryName);
    }

    /* 是否不屏蔽 1是2否 categoryName */
    @JsonProperty(value = "shield")
    public String getShield() {
        return (String) get("shield");
    }

    @JsonProperty(value = "shield")
    public void setShield(String shield) {
        set("shield", shield);
    }

    /* 分类 classifyId */
    @JsonProperty(value = "classifyId")
    public String getClassifyId() {
        return (String) get("classifyId");
    }

    @JsonProperty(value = "classifyId")
    public void setClassifyId(String classifyId) {
        set("classifyId", classifyId);
    }

    /* 分类名 classifyName */
    @JsonProperty(value = "classifyName")
    public String getClassifyName() {
        return (String) get("classifyName");
    }

    @JsonProperty(value = "classifyName")
    public void setClassifyName(String classifyName) {
        set("classifyName", classifyName);
    }

    /* 公司名 cname */
    @JsonProperty(value = "cname")
    public String getCname() {
        return (String) get("cname");
    }

    @JsonProperty(value = "cname")
    public void setCname(String cname) {
        set("cname", cname);
    }

    /* 联系人 pname */
    @JsonProperty(value = "pname")
    public String getPname() {
        return (String) get("pname");
    }

    @JsonProperty(value = "pname")
    public void setPname(String pname) {
        set("pname", pname);
    }

    /* 电话 tel */
    @JsonProperty(value = "tel")
    public String getTel() {
        return (String) get("tel");
    }

    @JsonProperty(value = "tel")
    public void setTel(String tel) {
        set("tel", tel);
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

    /* 是否违规 1不违规 2违规 illegal */
    @JsonProperty(value = "illegal")
    public String getIllegal() {
        return (String) get("illegal");
    }

    @JsonProperty(value = "illegal")
    public void setIllegal(String illegal) {
        set("illegal", illegal);
    }

    /**
     * 供应商修改商品
     */
    public boolean updateProduct() {
        if (StringUtils.isEmpty(this.getId())) {
            throw new OtherExcetion("请选择要修改的商品");
        }
        ProductSet productSet = Holder.getBean(ProductSet.class);
        return productSet.Update(this.getId(), this) > 0;
    }

    /**
     * 供应商删除商品
     */
    public boolean deleteById() {
        if (StringUtils.isEmpty(this.getId())) {
            throw new OtherExcetion("请选择要修改的商品");
        }
        ProductSet productSet = Holder.getBean(ProductSet.class);
        return productSet.Delete(this.getId()) > 0;
    }

    /**
     * 查询供应商自己的商品
     */
    public Product getById(String id) {
        ProductSet productSet = Holder.getBean(ProductSet.class);
        return productSet.Get(id);
    }

}