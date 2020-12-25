package io.hk.webApp.dto;

import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.Friends;

import java.util.List;

public class FactoryFriendsDTO {

    private Friends friends;

    private String type;

    private String sonAccount;

    private String schemeNumber;

    private String name;

    private String companyName;

    private String img;

    private String tel;

    private String addr;

    private List<Brand> list;

    @Override
    public String toString() {
        return "FactoryFriendsDTO{" +
                "friends=" + friends +
                ", type='" + type + '\'' +
                ", sonAccount='" + sonAccount + '\'' +
                ", schemeNumber='" + schemeNumber + '\'' +
                ", name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", img='" + img + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", list=" + list +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<Brand> getList() {
        return list;
    }

    public void setList(List<Brand> list) {
        this.list = list;
    }

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSonAccount() {
        return sonAccount;
    }

    public void setSonAccount(String sonAccount) {
        this.sonAccount = sonAccount;
    }

    public String getSchemeNumber() {
        return schemeNumber;
    }

    public void setSchemeNumber(String schemeNumber) {
        this.schemeNumber = schemeNumber;
    }
}
