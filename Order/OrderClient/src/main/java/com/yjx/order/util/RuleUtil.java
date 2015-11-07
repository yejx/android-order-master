package com.yjx.order.util;

import android.text.InputFilter;

import java.util.regex.Pattern;

/**
 * 业务规则相关工具类
 *
 * Created by jerry on 14-1-8.
 */
public class RuleUtil {

    private RuleUtil(){}

    /**
     * 获取长度限制
     *
     * @param max
     * @return
     */
    public static InputFilter[] getLengthFilter(int max){
        return new InputFilter[]{new InputFilter.LengthFilter(max)};
    }

    /**
     * 密码强度枚举类
     */
    public enum PWLevel{
        BAD("001"), GENERAL("002"), GOOD("003");

        private String value;

        PWLevel(String i) {
            this.value = i;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 检测密码强度
     *
     * @param password
     * @return 好：GOOD 一般：GENERAL 坏：BAD
     */
    public static PWLevel checkPWLevel(String password) {
        if (password == null || password.equals(""))
            return PWLevel.BAD;

        PWLevel pwLevel = null;
        int count = 0;
        if (Pattern.compile("(?i)[a-zA-Z]").matcher(password).find()) {
            count += 10;
        }
        if (Pattern.compile("(?i)[0-9]").matcher(password).find()) {
            count += 10;
        }
        if (Pattern.compile("(?i)[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]").matcher(password).find()) {
            count += 10;
        }
        if (count == 10) {
            pwLevel = PWLevel.BAD;
        } else if (count == 20) {
            pwLevel = PWLevel.GENERAL;
        } else if (count == 30) {
            pwLevel = PWLevel.GOOD;
        }
        return pwLevel;
    }

}
