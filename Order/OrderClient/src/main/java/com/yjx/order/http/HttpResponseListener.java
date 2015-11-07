package com.yjx.order.http;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by yjx on 15/9/2.
 */
public interface HttpResponseListener {

    abstract void onStart();

    abstract void onFinished(ResponseHandler response);

    abstract void onErrorResponse(VolleyError error);

}
