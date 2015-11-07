package com.yjx.order.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vinchaos api on 13-12-12.
 * 手机号工具类
 */
public class PhoneNumberUtil {

    public static final String SCHEME_TEL = "tel:";

    /**
     * formatPhoneNum
     * 手机号格式化,去" ","-",",";
     *
     * @param phoneNumber
     * @return
     */
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return "";
        String newString = phoneNumber.replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll(",", "");
        return newString;
    }

    /**
     * checkPhoneNum
     * 手机号验证处理
     *
     * @param phoneNumber
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.equals("")) return false;

        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNumber
     *            电话号码
     */
    public static void callPhone(final Context context, final String phoneNumber) {
        if (context == null){
            throw new IllegalArgumentException("context can not be null.");
        }
        try {
            final Uri uri = Uri.parse(SCHEME_TEL + phoneNumber);
            final Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.print(e);
        }
    }


    /**
     * 发送短信息
     * @param phoneNumber  接收号码
     * @param content      短信内容
     */
    private void toSendSMS(Context context, String phoneNumber,String content){
        if (context == null){
            throw new IllegalArgumentException("context can not be null.");
        }
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();

        if(content.length() >= 70){
            //短信字数大于70，自动分条
            List<String> ms = smsManager.divideMessage(content);
            for(String str : ms ){
                //短信发送
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        }
        else{
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }

}
