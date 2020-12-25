package io.hk.webApp.dto;

import io.hk.webApp.Domain.Dynamic;

public class DynamicDTO {

    private Dynamic dynamic;

    private String userImg;
    //1是合作中 2是请求中 3是请求合作
    private String cooperation;

    //距离发布时间已经过去多少ms
    private long time;

    private String name;

    @Override
    public String toString() {
        return "DynamicDTO{" +
                "dynamic=" + dynamic +
                ", userImg='" + userImg + '\'' +
                ", cooperation='" + cooperation + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getCooperation() {
        return cooperation;
    }

    public void setCooperation(String cooperation) {
        this.cooperation = cooperation;
    }
}
