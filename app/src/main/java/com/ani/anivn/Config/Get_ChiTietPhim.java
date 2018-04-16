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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sev_user on 04/05/2018.
 */

public class Get_ChiTietPhim {
    Context context;

    public Get_ChiTietPhim(Context context) {
        this.context = context;
    }

    public interface ChiTietPhim_Callback {
        void onSuccess(String linkphim, String anhnen, String namphathanh, String theloai, String thoiluong, String mota);

        void onFail(String message);
    }

    public void ChiTietPhim(String URL, final Get_ChiTietPhim.ChiTietPhim_Callback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            String linkphim = "", anhnen = "", namphathanh = "", theloai = "", thoiluong = "", mota = "";

                            Document doc = Jsoup.parse(response);

                            Element xemphim = doc.selectFirst("a.button-one");
                            if (xemphim != null) {
                                linkphim = xemphim.attr("href");
                            }

                            Element cover = doc.select("div.ah-pif-fcover > img").first();
                            if (cover != null) {
                                String REMOVE = "https://images2-focus-opensocial.googleusercontent.com/gadgets/proxy?container=focus&amp;gadget=a&amp;no_expand=1&amp;refresh=604800&amp;url=";
                                anhnen = cover.attr("src").replace(REMOVE, "");
                            }

                            Elements detail = doc.select("div.ah-pif-fdetails > ul > li");


                            if (detail != null) {
                                for (Element item : detail) {

                                    Element strong = item.select("strong").first();
                                    if (strong != null) {
                                        String str = strong.text();
                                        if (str.startsWith("Năm")) {
                                            namphathanh = item.text().replace(str, "");
                                        } else if (str.startsWith("Thể")) {

                                            Elements span = item.select("span");
                                            if (span != null) {
                                                for (Element e : span) {
                                                    if (e.select("a") != null)
                                                        theloai = theloai + e.select("a").text() + ", ";
                                                }
                                            }

                                        } else if (str.startsWith("Thời")) {
                                            thoiluong = item.text().replace(str, "");
                                        }

                                    }

                                }
                            }

                            Element content = doc.selectFirst("div.ah-pif-fcontent");
                            if (content != null) {
                                Element p = content.select("p").first();
                                if (p != null) {
                                    mota = p.toString();
                                }
                            }

                           // Log.d("TESTAPI", linkphim + "\n" + anhnen + "\n" + namphathanh + "\n" + theloai + "\n" + thoiluong + "\n" + mota);

                            callback.onSuccess(linkphim, anhnen, namphathanh, theloai, thoiluong, mota);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFail("Error get data !");

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
                            callback.onFail("Something Wrong here!");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(new String(utf8String), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
