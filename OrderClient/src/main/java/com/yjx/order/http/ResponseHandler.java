package com.yjx.order.http;

/**
 * Created by yjx on 15/9/2.
 */
public class ResponseHandler {

    public String retCode;
    public String retMsg;
    public Object retData;

    public ResponseHandler(String retCode, String retMsg,  Object retData) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retData = retData;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public  Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    public boolean isSuccess(){
        return "TS0000".equals(retCode) ||"000000".equals(retCode);
    }

}
