package io.hk.webApp.vo;

public class UserDTO {

    private String phone;

    private String auth;

    private String type;

    private Long schemeCount;

    private String createTime;

    private String lastLoginTime;

    private String status;

    @Override
    public String toString() {
        return "UserDTO{" +
                "phone='" + phone + '\'' +
                ", auth='" + auth + '\'' +
                ", type='" + type + '\'' +
                ", schemeCount='" + schemeCount + '\'' +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", status='" + status + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSchemeCount() {
        return schemeCount;
    }

    public void setSchemeCount(Long schemeCount) {
        this.schemeCount = schemeCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
