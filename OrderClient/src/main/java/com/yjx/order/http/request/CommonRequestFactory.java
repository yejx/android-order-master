package com.yjx.order.http.request;

import com.yjx.order.http.HttpResponseListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yjx on 2015/10/24.
 */
public class CommonRequestFactory {

    /**
     * 用户登录
     *
     * @param mobile       手机号码
     * @param pwd          登录密码
     */
    public static void login(String mobile, String pwd, HttpResponseListener listener) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("mobile", mobile);
        requestParams.put("pwd", pwd);
        ServiceManager.getInstance().sendStringRequest(requestParams, RequestUrlEnum.USER_LOGIN, listener);

    }

    /**
     * 用户注册
     *
     * @param mobile       手机号码
     * @param pwd          登录密码
     */
    public static void register(String mobile, String pwd, HttpResponseListener listener) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("mobile", mobile);
        requestParams.put("pwd", pwd);
        ServiceManager.getInstance().sendStringRequest(requestParams, RequestUrlEnum.USER_REGISTER, listener);

    }

    /**
     * 用户修改密码
     *
     * @param mobile       手机号码
     * @param pwd          新的登录密码
     */
    public static void updatePwd(String mobile, String pwd, HttpResponseListener listener) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("mobile", mobile);
        requestParams.put("pwd", pwd);
        ServiceManager.getInstance().sendStringRequest(requestParams, RequestUrlEnum.USER_UPDATE_PWD, listener);

    }
}
