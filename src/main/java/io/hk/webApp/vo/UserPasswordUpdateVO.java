package io.hk.webApp.vo;

public class UserPasswordUpdateVO {

    private String oldPassword;

    private String newPassword;

    @Override
    public String toString() {
        return "UserPasswordUpdateVO{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
