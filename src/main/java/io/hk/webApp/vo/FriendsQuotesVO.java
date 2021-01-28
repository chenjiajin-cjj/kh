package io.hk.webApp.vo;

import java.util.List;

public class FriendsQuotesVO {

    private List<String> factoryIds;

    @Override
    public String toString() {
        return "FriendsQuotesVO{" +
                "factoryIds=" + factoryIds +
                '}';
    }

    public List<String> getFactoryIds() {
        return factoryIds;
    }

    public void setFactoryIds(List<String> factoryIds) {
        this.factoryIds = factoryIds;
    }
}
