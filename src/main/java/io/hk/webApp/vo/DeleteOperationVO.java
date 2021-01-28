package io.hk.webApp.vo;


public class DeleteOperationVO {

    private Long beginTime;

    private Long endTime;

    @Override
    public String toString() {
        return "DeleteOperationVO{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
