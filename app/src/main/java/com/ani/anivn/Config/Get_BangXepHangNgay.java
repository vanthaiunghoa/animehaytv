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
import com.ani.anivn.Model.BangXepHangNgay_Model;
import com.ani.anivn.Model.BangXepHangNgay_ModelDao;

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
 * Created by sev_user on 04/05/2018.
 */

public class Get_BangXepHangNgay {
    Context context;
    final String URL = "http://animehay.tv";

    public Get_BangXepHangNgay(Context context) {
        this.context = context;
    }

    public interface BangXepHangNgay_Callback {
        void onSuccess(List<BangXepHangNgay_Model> list_data);

        void onFail(String message);
    }

    public void BangXepHangNgay(final BangXepHangNgay_ModelDao dao, final Get_BangXepHangNgay.BangXepHangNgay_Callback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            List<BangXepHangNgay_Model> list_data = new ArrayList<>();
                            Document doc = Jsoup.parse(response);

                            Elements elements_film = doc.select("div.ah-widget-rank.ah-box-widget.ah-clear-both > ul > li"); // Khong duoc sua j ca

                            if (elements_film != null) {
                                for (Element item : elements_film) {
                                    String tenphim = "", hinhanh = "", linkthongtinphim = "", luotxem = "";

                                    Element img = item.selectFirst("img");
                                    if (img != null) {
                                        String REMOVE = "https://images2-focus-opensocial.googleusercontent.com/gadgets/proxy?container=focus&amp;gadget=a&amp;no_expand=1&amp;refresh=604800&amp;url=";
                                        hinhanh = img.attr("src").replace(REMOVE, "");
                                    }

                                   // Log.d("TESTAPI", item.html());

                                    Element infor = item.selectFirst("div.ah-float-left.w-70");
                                    if (infor != null) {

                                        Element a = infor.selectFirst("a");
                                        if (a != null) {
                                            linkthongtinphim = a.attr("href");
                                            tenphim = a.text();
                                        }

                                        Element div_lx = infor.select("div").last();
                                        if (div_lx != null) {
                                            luotxem = div_lx.text();
                                        }

                                    }

                                    BangXepHangNgay_Model b = new BangXepHangNgay_Model();
                                    b.setTenphim(tenphim);
                                    b.setHinhanh(hinhanh);
                                    b.setLinkthongtinphim(linkthongtinphim);
                                    b.setLuotxem(luotxem);

                                    list_data.add(b);
                                    dao.save(b);

                                    //Log.d("TESTAPI",tenphim+"\n"+hinhanh+"\n"+linkthongtinphim+"\n"+luotxem);
                                }
                            }

                            callback.onSuccess(list_data);
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
