package com.yjx.order.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by yjx on 2015/10/25.
 */
public class BaseVolleyStringRequest extends Request<JSONObject> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    //post请求的参数
    Map<String, String> requestParams ;
    private final Response.Listener<JSONObject> mListener;

    /**
     * 这里的method必须是Method.POST，也就是必须带参数。
     * 如果不想带参数，可以用JsonObjectRequest，给它构造参数传null。GET方式请求。
     * @param requestParams
     */
    public BaseVolleyStringRequest(String url, Map<String, String> requestParams, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(requestParams == null ? Method.GET : Method.POST, url, errorListener);
        this.requestParams = requestParams;
        mListener = listener;
    }

//    public BaseVolleyStringRequest(String url, Map<String, String> requestParams, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
//        super(requestParams == null ? Method.GET : Method.POST, url, null, listener, errorListener);
//        this.requestParams = requestParams;
//
//    }

//    @Override
//    public String getBodyContentType() {
//        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
//    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return requestParams;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
