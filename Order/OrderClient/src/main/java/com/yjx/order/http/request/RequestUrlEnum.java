package com.yjx.order.http.request;

/**
 * Created by yjx on 15/9/2.
 *
 * 业务请求地址枚举
 *
 */
public enum RequestUrlEnum {

    USER_LOGIN("UserServlet", "login.do"),
    USER_REGISTER("UserServlet", "register.do"),
    USER_UPDATE_PWD("UserServlet", "update.do");

    private String value;
    private String operate;

    RequestUrlEnum(String value, String operate) {
        this.value = value;
        this.operate = operate;
    }

    public String value(){
        return value;
    }

    public String operate(){
        return operate;
    }
}
