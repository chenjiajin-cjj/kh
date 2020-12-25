package io.hk.webApp.Tools;

public class OtherExcetion extends RuntimeException {

    private Integer status;

    private String message;

    private Object data;

    public OtherExcetion(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public OtherExcetion(String message) {
        super(message);
        this.status = 400;
        this.message = message;
    }

    public OtherExcetion(Integer status, String message,Object data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public OtherExcetion(String message,Object data) {
        super(message);
        this.status = 400;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
