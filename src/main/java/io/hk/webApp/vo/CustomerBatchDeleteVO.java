package io.hk.webApp.vo;

import java.util.Arrays;

public class CustomerBatchDeleteVO {

    private String [] ids;

    @Override
    public String toString() {
        return "CustomerBatchDeleteVO{" +
                "ids=" + Arrays.toString(ids) +
                '}';
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
