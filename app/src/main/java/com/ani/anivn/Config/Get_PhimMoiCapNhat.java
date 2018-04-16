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
import com.ani.anivn.Model.PhimMoiCapNhat_Model;
import com.ani.anivn.Model.PhimMoiCapNhat_ModelDao;

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

public class Get_PhimMoiCapNhat {
    Context context;

    final String URL = "http://animehay.tv";

    public Get_PhimMoiCapNhat(Context context) {
        this.context = context;
    }

    public interface PhimMoiCapNhat_Callback {
        void onSuccess(List<PhimMoiCapNhat_Model> list_data);

        void onFail(String message);
    }

    public void PhimMoiCapNhat(final PhimMoiCapNhat_ModelDao dao, final Get_PhimMoiCapNhat.PhimMoiCapNhat_Callback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            List<PhimMoiCapNhat_Model> list_data = new ArrayList<>();
                            Document doc = Jsoup.parse(response);

                            Elements elements_film = doc.select("div.ah-row-film > div.ah-col-film > div.ah-pad-film");

                            if (elements_film != null) {
                                for (Element item : elements_film) {

                                    String tenphim = "", tap = "", hinhanh = "", linkthongtinphim = "", nam = "", mota = "";

                                    item.select("div.ah-effect-film").remove();

                                    Element a = item.selectFirst("a");
                                    if (a != null) {

                                        linkthongtinphim = a.attr("href");
                                        Element img = a.selectFirst("img");
                                        if (img != null) {
                                            String REMOVE = "https://images2-focus-opensocial.googleusercontent.com/gadgets/proxy?container=focus&amp;gadget=a&amp;no_expand=1&amp;refresh=604800&amp;url=";
                                            hinhanh = img.attr("src").replace(REMOVE, "");
                                        }
                                        Element ep = a.selectFirst("span.number-ep-film");
                                        if (ep != null) {
                                            tap = "Tập " + ep.text();
                                        }
                                        Element name = a.selectFirst("span.name-film");
                                        if (name != null) {
                                            tenphim = name.text();
                                        }

                                        Element hover = a.selectFirst("div.ah-film-hover");
                                        if (hover != null) {
                                            Element year = hover.selectFirst("span.ah-year-hover");
                                            if (year != null)
                                                nam = year.text();

                                            Element des = hover.selectFirst("span.ah-des-hover");
                                            if (des != null)
                                                mota = des.text();
                                        }


                                        PhimMoiCapNhat_Model p = new PhimMoiCapNhat_Model();
                                        p.setTenphim(tenphim);
                                        p.setTap(tap);
                                        p.setHinhanh(hinhanh);
                                        p.setLinkthongtinphim(linkthongtinphim);
                                        p.setNam(nam);
                                        p.setMota(mota);

                                        list_data.add(p);
                                        dao.save(p);

//                                         Log.d("TESTAPI", tenphim + "\n" + tap + "\n" + hinhanh + "\n" + linkthongtinphim+"\n"+nam+"\n"+mota+"\n");

                                    }
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
