package io.hk.webApp.dto;

import io.hk.webApp.Domain.FriendApply;

public class FriendApplyDTO {


    private String name;

    private String companyName;

    private String phone;

    private String img;

    private String addr;

    private FriendApply friendApply;

    @Override
    public String toString() {
        return "FriendApplyDTO{" +
                "name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", phone='" + phone + '\'' +
                ", img='" + img + '\'' +
                ", addr='" + addr + '\'' +
                ", friendApply=" + friendApply +
                '}';
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public FriendApply getFriendApply() {
        return friendApply;
    }

    public void setFriendApply(FriendApply friendApply) {
        this.friendApply = friendApply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
