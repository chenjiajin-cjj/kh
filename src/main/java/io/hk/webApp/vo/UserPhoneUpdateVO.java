package io.hk.webApp.vo;

public class UserPhoneUpdateVO {

    private String phone;
    private String phoneCode;

    @Override
    public String toString() {
        return "UserPhoneUpdateVO{" +
                "phone='" + phone + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }
}
