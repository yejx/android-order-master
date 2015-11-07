package com.yjx.order.http.request;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yjx.order.common.ApplicationEx;
import com.yjx.order.common.Parameters;
import com.yjx.order.http.HttpResponseListener;
import com.yjx.order.http.ResponseHandler;
import com.yjx.order.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ServiceManager{

    private final int TIMEOUT = 30; //超时时间
    private static ServiceManager ourInstance = new ServiceManager();

    public static ServiceManager getInstance() {
        return ourInstance;
    }

    private RequestQueue requestQueue;

    private ServiceManager() {

        if(requestQueue == null)
//            requestQueue = Volley.newRequestQueue(ApplicationEx.getInstance());
            requestQueue = ApplicationEx.getInstance().getmRequestQueue();

    }

    public static final String BASE_URL = Parameters.serviceURL;

    public void sendJSONRequest(JSONObject requestParams, RequestUrlEnum requestUrlEnum, final HttpResponseListener
            httpResponseListener){

        StringBuffer stringBuffer = new StringBuffer(BASE_URL);
        stringBuffer.append(requestUrlEnum.value());
        String fullUrl = stringBuffer.toString();
        try {
            requestParams.put("operate",requestUrlEnum.operate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.print("Request Url = " + fullUrl + "\nRequest JSON = " + requestParams.toString());
        if(httpResponseListener != null){
            httpResponseListener.onStart();
        }
        requestQueue.add(new BaseVolleyJsonRequest(fullUrl, requestParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                LogUtil.print("Response =" + response);

                if (httpResponseListener != null) {
                    httpResponseListener.onFinished(new ResponseHandler(response.optString("retCode"), response.optString("retMsg"), response.optJSONObject("retData")));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponseListener != null)
                    httpResponseListener.onErrorResponse(error);
            }
        })
                .setRetryPolicy(new DefaultRetryPolicy(TIMEOUT * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))
                .setTag(requestUrlEnum.value()));//请求标签

    }

    public void sendStringRequest(Map<String, String> requestParams, RequestUrlEnum requestUrlEnum, final HttpResponseListener
            httpResponseListener){

        StringBuffer stringBuffer = new StringBuffer(BASE_URL);
        stringBuffer.append(requestUrlEnum.value());
        String fullUrl = stringBuffer.toString();

        requestParams.put("operate",requestUrlEnum.operate());

        LogUtil.print("Request Url = " + fullUrl + "\nRequest JSON = " + requestParams.toString());
        if(httpResponseListener != null){
            httpResponseListener.onStart();
        }
        requestQueue.add(new BaseVolleyStringRequest(fullUrl, requestParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                LogUtil.print("Response =" + response);

                if (httpResponseListener != null) {
                    httpResponseListener.onFinished(new ResponseHandler(response.optString("retCode"), response.optString("retMsg"), response.optJSONObject("retData")));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpResponseListener != null)
                    httpResponseListener.onErrorResponse(error);
            }
        })
                .setRetryPolicy(new DefaultRetryPolicy(TIMEOUT * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))
                .setTag(requestUrlEnum.value()));//请求标签

    }

}
