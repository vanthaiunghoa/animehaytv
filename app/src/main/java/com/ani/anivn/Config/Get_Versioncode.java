package com.ani.anivn.Config;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sev_user on 02/24/2018.
 */

public class Get_Versioncode {

    Context context;

    public Get_Versioncode(Context context) {
        this.context = context;
    }

    public interface VersionCode_Callback {
        void onSuccess(String versioncode);

        void onFail(String message);
    }

    public void GetVersionCode(final VersionCode_Callback callback) {

        String URL = "http://www.phamdinhhai0810.club/api/anivn/versioncode";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String versioncode = "";
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray VersionCode = jsonObject.getJSONArray("VersionCode");
                            if (VersionCode.length() > 0) {
                                versioncode = VersionCode.getJSONObject(0).getString("versioncode");
                                Log.d("CODE", versioncode);
                            }
                            if (versioncode.length() > 0)
                                callback.onSuccess(versioncode);
                            else
                                callback.onFail("Error Check Version App.");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFail("Error Check Version App.");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }

                        if (message != null)
                            callback.onFail(message);
                        else
                            callback.onFail("Error");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                return map;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
