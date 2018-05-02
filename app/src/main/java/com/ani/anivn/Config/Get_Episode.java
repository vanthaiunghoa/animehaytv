package com.ani.anivn.Config;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ani.anivn.Model.Episode_Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sev_user on 04/06/2018.
 */

public class Get_Episode {
    Context context;

    public Get_Episode(Context context) {
        this.context = context;
    }

    public interface Episode_Callback {
        void onSuccess(List<Episode_Model> list_data);

        void onFail(String message);
    }

    public void Episode(String URL, final Get_Episode.Episode_Callback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            List<Episode_Model> list_data = new ArrayList<>();
                            Document doc = Jsoup.parse(response);

                            Elements li_episode = doc.select("div.ah-wf-le.ah-bg-bd > ul > li");

                            if (li_episode != null) {
                                for (Element item : li_episode) {

                                    String ten_tap = "", link_tap = "";

                                    Element a = item.selectFirst("a");
                                    if (a != null) {
                                        link_tap = a.attr("href");
                                        ten_tap = a.text();

                                        Episode_Model ep = new Episode_Model();
                                        ep.setTen_tap(ten_tap);
                                        ep.setLink_tap(link_tap);

                                        list_data.add(ep);
                                    }
                                }
                            }

                            callback.onSuccess(list_data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFail("Error get data !" + e.getMessage());
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

                        callback.onFail(message+ " ");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                return map;
            }

//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                try {
//                    String utf8String = new String(response.data, "UTF-8");
//                    return Response.success(new String(utf8String), HttpHeaderParser.parseCacheHeaders(response));
//                } catch (UnsupportedEncodingException e) {
//                    // log error
//                    return Response.error(new ParseError(e));
//                }
//            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
