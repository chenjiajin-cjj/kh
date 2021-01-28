package io.hk.webApp.Tools;

public class BaseType {

    public enum Status {

        YES("1", "显示"),
        NO("2", "不显示");

        private String code;
        private String msg;

        Status(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public enum UserType {

        FACTORY("1", "供应商"),
        SALER("2", "经销商"),
        SON("3", "子账号");

        private String code;
        private String msg;

        UserType(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    public enum Consent {

        PASS("1", "审核通过"),
        BASE("2", "未审核"),
        REFUSE("3", "审核拒绝");

        private String code;
        private String msg;

        Consent(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public enum Code {

        PHONE_CODE("Index.sendValidCode:", "短信验证码"),
        PHONE_CODE_EX("Index.sendValidCode.ex:", "重复发送");

        private String code;
        private String msg;

        Code(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public enum FactorySchemeStatus {

        YES("1", "已提报"),
        NO("2", "未提报");

        private String code;
        private String msg;

        FactorySchemeStatus(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public enum SchemeStatus {

        UNDERWAY("1", "进行中"),
        PAST("2", "已过期"),
        COMPLETE("3", "已完成"),
        OVER("4", "已结束");

        private String code;
        private String msg;

        SchemeStatus(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

    }

    public enum Message {

        CHECK("1", "审核信息"),
        COOPERATION("2", "合作信息"),
        SUBMISSION("3", "提报信息"),
        SYSTEM("4", "系统信息"),
        MESSAGE("5", "站内信消息");
        private String code;
        private String msg;

        Message(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

    }

    public enum FriendGroup {

        BASE("1", "默认"),
        COMMON("2", "普通"),
        VIP("3", "vip"),
        INVITE("4", "特邀");

        private String code;
        private String msg;

        FriendGroup(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

    }

    public enum ShareProductStatus {

        ACCEPT("1", "已接受"),
        UNACCEPT("2", "未接受"),
        NO_HAVE("3", "对方还没有这个商品"),
        REFUSE("4", "已拒绝");

        private String code;
        private String msg;

        ShareProductStatus(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

    }

    public enum Time {
        WEEK("1","一周前"),
        MONTH("2","一月前"),
        THREE_MONTH("3","三月前"),
        HALF_YEAR("4","半年前"),
        YEAR("5","一年前");
        private String code;
        private String msg;

        Time(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
