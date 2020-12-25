package io.hk.webApp.vo;

import io.hk.webApp.Domain.Friends;

public class SchemeFriendsDTO {

    private Friends friends;

    private boolean flag;

    @Override
    public String toString() {
        return "SchemeFriendsDTO{" +
                "friends=" + friends +
                ", flag=" + flag +
                '}';
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
