package io.hk.webApp.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.framecore.Orm.ViewStore;

public class DoMain extends ViewStore {

    /**
     * id
     * 账号
     * 密码
     */

    private String _id;
    private String account;
    private String password;

    @JsonProperty(value = "_id")
    public String get_id() {
        if (get("_id") == null) {
            return null;
        }
        return get("_id").toString();
    }
    @JsonProperty(value = "_id")
    public void set_id(String _id) {
        this._id = _id;
        set("_id", _id);
    }
    @JsonProperty(value = "account")
    public String getAccount() {
        return get("account").toString();
    }
    @JsonProperty(value = "account")
    public void setAccount(String account) {
        this.account = account;
        set("account", account);
    }
    @JsonProperty(value = "password")
    public String getPassword() {
        return get("password").toString();
    }
    @JsonProperty(value = "password")
    public void setPassword(String password) {
        this.password = password;
        set("password", password);
    }

    @Override
    public String toString() {
        return "DoMain{" +
                "_id='" + _id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
