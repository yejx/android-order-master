package com.yjx.order.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yjx on 15/8/28.
 */
public class BaseVolleyJsonRequest extends JsonObjectRequest {
//    private String tag_request = ""; //请求标签
//    private int timeout = 5000;//超时时间

    private static final Map<String, String> headers = new HashMap<String, String>();

    public BaseVolleyJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public BaseVolleyJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

//    @Override
//    protected Map<String, String> getParams() throws AuthFailureError {
//        return super.getParams();
//    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");

        return headers;
    }
}
