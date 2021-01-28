package io.hk.webApp.vo;

import io.hk.webApp.dto.ProductDTO;

import java.util.List;

public class PptVO {

    /**
     * 主标题
     */
    private String mainTitle;
    /**
     * 副标题
     */
    private String subhead;
    /**
     * 联系人
     */
    private String userName;
    /**
     * 职位
     */
    private String position;
    /**
     * 电话
     */
    private String phone;
    /**
     * QQ
     */
    private String qq;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 方案id
     */
    private String schemeId;
    /**
     * 商品id
     */
    private List<String> productIds;

    private List<ProductDTO> productDTOS;

    @Override
    public String toString() {
        return "PptVO{" +
                "mainTitle='" + mainTitle + '\'' +
                ", subhead='" + subhead + '\'' +
                ", userName='" + userName + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", schemeId='" + schemeId + '\'' +
                ", productIds=" + productIds +
                ", productDTOS=" + productDTOS +
                '}';
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }

    public void setProductDTOS(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
