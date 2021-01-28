package io.hk.webApp.dto;

public class SystemDataDTO {
    /**
     * 商家数量
     */
    private String factorys;
    /**
     * 商品数量
     */
    private String products;
    /**
     * 经销商数量
     */
    private String salers;
    /**
     * 累计方案数量
     */
    private String schemes;
    /**
     * 品牌审核
     */
    private String brands;
    /**
     * 用户认证审核
     */
    private String auths;
    /**
     * 已上架
     */
    private String onLineProducts;
    /**
     * 已下架
     */
    private String downLineProducts;
    /**
     * 今日新增用户数
     */
    private String newAddUsers;
    /**
     * 昨日新增用户数
     */
    private String yesterdayUsers;
    /**
     * 本月新增用户数
     */
    private String monthUsers;
    /**
     * 系统会员总数
     */
    private String allUsers;
    /**
     * 本周方案数
     */
    private String weekSchemes;
    /**
     * 本月方案数
     */
    private String monthSchemes;

    /**
     * 会员同比上月百分比
     */
    private String compareUserMonth;
    /**
     * 会员同比上周百分比
     */
    private String compareUserWeek;
    /**
     * 方案同比上月百分比
     */
    private String compareSchemeMonth;
    /**
     * 方案同比上周百分比
     */
    private String compareSchemeWeek;

    @Override
    public String toString() {
        return "SystemDataDTO{" +
                "factorys='" + factorys + '\'' +
                ", products='" + products + '\'' +
                ", salers='" + salers + '\'' +
                ", schemes='" + schemes + '\'' +
                ", brands='" + brands + '\'' +
                ", auths='" + auths + '\'' +
                ", onLineProducts='" + onLineProducts + '\'' +
                ", downLineProducts='" + downLineProducts + '\'' +
                ", newAddUsers='" + newAddUsers + '\'' +
                ", yesterdayUsers='" + yesterdayUsers + '\'' +
                ", monthUsers='" + monthUsers + '\'' +
                ", allUsers='" + allUsers + '\'' +
                ", weekSchemes='" + weekSchemes + '\'' +
                ", monthSchemes='" + monthSchemes + '\'' +
                ", compareUserMonth='" + compareUserMonth + '\'' +
                ", compareUserWeek='" + compareUserWeek + '\'' +
                ", compareSchemeMonth='" + compareSchemeMonth + '\'' +
                ", compareSchemeWeek='" + compareSchemeWeek + '\'' +
                '}';
    }

    public String getFactorys() {
        return factorys;
    }

    public void setFactorys(String factorys) {
        this.factorys = factorys;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getSalers() {
        return salers;
    }

    public void setSalers(String salers) {
        this.salers = salers;
    }

    public String getSchemes() {
        return schemes;
    }

    public void setSchemes(String schemes) {
        this.schemes = schemes;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getAuths() {
        return auths;
    }

    public void setAuths(String auths) {
        this.auths = auths;
    }

    public String getOnLineProducts() {
        return onLineProducts;
    }

    public void setOnLineProducts(String onLineProducts) {
        this.onLineProducts = onLineProducts;
    }

    public String getDownLineProducts() {
        return downLineProducts;
    }

    public void setDownLineProducts(String downLineProducts) {
        this.downLineProducts = downLineProducts;
    }

    public String getNewAddUsers() {
        return newAddUsers;
    }

    public void setNewAddUsers(String newAddUsers) {
        this.newAddUsers = newAddUsers;
    }

    public String getYesterdayUsers() {
        return yesterdayUsers;
    }

    public void setYesterdayUsers(String yesterdayUsers) {
        this.yesterdayUsers = yesterdayUsers;
    }

    public String getMonthUsers() {
        return monthUsers;
    }

    public void setMonthUsers(String monthUsers) {
        this.monthUsers = monthUsers;
    }

    public String getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(String allUsers) {
        this.allUsers = allUsers;
    }

    public String getWeekSchemes() {
        return weekSchemes;
    }

    public void setWeekSchemes(String weekSchemes) {
        this.weekSchemes = weekSchemes;
    }

    public String getMonthSchemes() {
        return monthSchemes;
    }

    public void setMonthSchemes(String monthSchemes) {
        this.monthSchemes = monthSchemes;
    }

    public String getCompareUserMonth() {
        return compareUserMonth;
    }

    public void setCompareUserMonth(String compareUserMonth) {
        this.compareUserMonth = compareUserMonth;
    }

    public String getCompareUserWeek() {
        return compareUserWeek;
    }

    public void setCompareUserWeek(String compareUserWeek) {
        this.compareUserWeek = compareUserWeek;
    }

    public String getCompareSchemeMonth() {
        return compareSchemeMonth;
    }

    public void setCompareSchemeMonth(String compareSchemeMonth) {
        this.compareSchemeMonth = compareSchemeMonth;
    }

    public String getCompareSchemeWeek() {
        return compareSchemeWeek;
    }

    public void setCompareSchemeWeek(String compareSchemeWeek) {
        this.compareSchemeWeek = compareSchemeWeek;
    }
}
