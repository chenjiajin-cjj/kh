package io.hk.webApp.vo;

import io.hk.webApp.Domain.Friends;

public class SchemeFriendsDTO {

    private Friends friends;

    private String cname;

    private String name;

    private String realPhone;

    private String tel;

    private String addr;

    private boolean flag;

    private String img;

    @Override
    public String toString() {
        return "SchemeFriendsDTO{" +
                "friends=" + friends +
                ", cname='" + cname + '\'' +
                ", name='" + name + '\'' +
                ", realPhone='" + realPhone + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", flag=" + flag +
                ", img='" + img + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealPhone() {
        return realPhone;
    }

    public void setRealPhone(String realPhone) {
        this.realPhone = realPhone;
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

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
