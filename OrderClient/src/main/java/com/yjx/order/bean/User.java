package com.yjx.order.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息相关bean
 * <p/>
 * Created by jerry on 14-1-7.
 */
public class User {

    private String mobile = "";
    private String pwd = "";
    private String userName = "";

    public User() {

    }

    /**
     * 通过接口返回的参数，构造一个user对象
     *
     * @param data    数据
     */

    public static User initAttrWithJson(JSONObject data){
        User user = new User();
        if (data.has("mobile")) {
            user.setMobile(data.optString("mobile"));
        }
        if (data.has("pwd")) {
            user.setPwd(data.optString("pwd"));
        }
        if (data.has("userName")) {
            user.setUserName(data.optString("userName"));
        }

        return  user;
    }

    public static JSONObject initLoginJSON(String mobile, String pwd){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("pwd", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map<String, String> initLoginParams(String mobile, String pwd){
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("pwd", pwd);
        return  map;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 保存用户信息到数据库
     */
    public void save() {
//        UserDao.getInstance().saveUser(this);
    }
}
