package com.ani.anivn.ChiTietPhim;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.Config.Get_ChiTietPhim;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Episode.Episode_Fragment;
import com.ani.anivn.Model.ChiTietPhim_Model;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.YeuThich_Model;
import com.ani.anivn.Model.YeuThich_ModelDao;
import com.ani.anivn.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.net.URL;

import es.dmoral.toasty.Toasty;

/**
 * Created by sev_user on 04/05/2018.
 */

public class ChiTietPhim_Fragment extends Fragment {

    View view;

    Bundle bundle;
    ChiTietPhim_Model chiTietPhim_model;
    String linkthongtinphim = "";
    String toolbartitle = "";

    ImageView img_hinhanh, img_anhnen;
    TextView tv_xemphim, tv_thich, tv_bothich, tv_namphathanh, tv_theloai, tv_thoiluong, tv_noidung;
    ProgressBar progressBar;

    Get_ChiTietPhim get_chiTietPhim;
    AppCompatActivity activity;

    DaoSession daoSession;
    YeuThich_ModelDao yeuthich_dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_chitietphim, container, false);

        activity = (AppCompatActivity) view.getContext();

        GetBundle();

        //  Admod();

        initSQL();

        FindViewById();

        GetDataServer();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        yeuthich_dao = daoSession.getYeuThich_ModelDao();
    }

    private void FindViewById() {

        img_hinhanh = view.findViewById(R.id.img_hinhanh_chitietphim);
        img_anhnen = view.findViewById(R.id.img_anhnen_chitietphim);
        tv_xemphim = view.findViewById(R.id.tv_xemphim_chitietphim);
        tv_thich = view.findViewById(R.id.tv_thich_chitietphim);
        tv_bothich = view.findViewById(R.id.tv_bothich_chitietphim);
        tv_namphathanh = view.findViewById(R.id.tv_namphathanh_chitietphim);
        tv_theloai = view.findViewById(R.id.tv_theloai_chitietphim);
        tv_thoiluong = view.findViewById(R.id.tv_thoiluong_chitietphim);
        tv_noidung = view.findViewById(R.id.tv_noidung_chitietphim);

        progressBar = view.findViewById(R.id.progressbar_chitietphim);

        get_chiTietPhim = new Get_ChiTietPhim(getContext());

        tv_xemphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();

                String URL = chiTietPhim_model.getLinkphim();

                if (isValid(URL)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("URL", URL);

                    Episode_Fragment episode_fragment = new Episode_Fragment();

                    episode_fragment.setArguments(bundle);

                    progressBar.setVisibility(View.GONE);

                    FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, episode_fragment, "Episode_Fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack("Episode_Fragment")
                            .commit();
                } else {
                    try {
                        Toasty.error(getContext(), URL.replace("javascript:alert('", "").replace("')", ""), Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {
                        Toasty.error(getContext(), URL, Toast.LENGTH_SHORT, true).show();
                    }

                    progressBar.setVisibility(View.GONE);
                }


            }
        });

        tv_thich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long countyeuThich = yeuthich_dao.queryBuilder().where(YeuThich_ModelDao.Properties.Tenphim.eq(chiTietPhim_model.getTenphim())).count();
                if (countyeuThich == 0) {

                    YeuThich_Model yeuThich_model = new YeuThich_Model();
                    yeuThich_model.setTenphim(chiTietPhim_model.getTenphim());
                    yeuThich_model.setHinhanh(chiTietPhim_model.getHinhanh());
                    yeuThich_model.setLinkthongtinphim(linkthongtinphim);
                    yeuThich_model.setNam(chiTietPhim_model.getNamphathanh());
                    yeuThich_model.setMota(chiTietPhim_model.getMota());

                    yeuthich_dao.save(yeuThich_model);

                    Toasty.success(getContext(), "Lưu thành công !", Toast.LENGTH_SHORT, true).show();

                    tv_thich.setVisibility(View.GONE);
                    tv_bothich.setVisibility(View.VISIBLE);

                }
            }
        });

        tv_bothich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YeuThich_Model yeuThich = yeuthich_dao.queryBuilder().where(YeuThich_ModelDao.Properties.Tenphim.eq(chiTietPhim_model.getTenphim())).build().uniqueOrThrow();
                if (yeuThich != null) {

                    yeuthich_dao.delete(yeuThich);

                    Toasty.success(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT, true).show();

                    tv_thich.setVisibility(View.VISIBLE);
                    tv_bothich.setVisibility(View.GONE);
                }

            }
        });

    }

    /* Returns true if url is valid */
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

//    private void Admod() {
//        try {
//            final InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
//            mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_interstitial));
//            AdRequest adRequestInter = new AdRequest.Builder().build();
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    mInterstitialAd.show();
//                }
//
//                @Override
//                public void onAdClosed() {
//
//                }
//
//                @Override
//                public void onAdFailedToLoad(int i) {
//
//                }
//            });
//            mInterstitialAd.loadAd(adRequestInter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void SetItem() {
        Picasso.with(getContext()).load(chiTietPhim_model.getHinhanh()).fit().centerCrop().error(R.drawable.default_fail_img).into(img_hinhanh);
        Picasso.with(getContext()).load(chiTietPhim_model.getAnhnen()).fit().centerCrop().error(R.drawable.default_fail_img).into(img_anhnen);

        tv_theloai.setText(chiTietPhim_model.getTheloai());
        tv_namphathanh.setText(chiTietPhim_model.getNamphathanh());
        tv_thoiluong.setText(chiTietPhim_model.getThoiluong());

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tv_noidung.setText(Html.fromHtml(chiTietPhim_model.getMota(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                tv_noidung.setText(Html.fromHtml(chiTietPhim_model.getMota()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long count = yeuthich_dao.queryBuilder().where(YeuThich_ModelDao.Properties.Tenphim.eq(chiTietPhim_model.getTenphim())).count();

        if (count > 0) {
            tv_thich.setVisibility(View.GONE);
            tv_bothich.setVisibility(View.VISIBLE);
        } else {
            tv_thich.setVisibility(View.VISIBLE);
            tv_bothich.setVisibility(View.GONE);
        }

        tv_xemphim.setVisibility(View.VISIBLE);

    }

    private void GetDataServer() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        if (linkthongtinphim.length() > 0) {


            get_chiTietPhim.ChiTietPhim(linkthongtinphim, new Get_ChiTietPhim.ChiTietPhim_Callback() {
                @Override
                public void onSuccess(String linkphim, String anhnen, String namphathanh, String theloai, String thoiluong, String mota) {

                    chiTietPhim_model.setLinkphim(linkphim);
                    chiTietPhim_model.setAnhnen(anhnen);
                    chiTietPhim_model.setNamphathanh(namphathanh);
                    chiTietPhim_model.setTheloai(theloai);
                    chiTietPhim_model.setThoiluong(thoiluong);
                    chiTietPhim_model.setMota(mota);


                    SetItem();

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFail(String message) {
                    progressBar.setVisibility(View.GONE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        } else {
            Toasty.error(getContext(), "Lỗi không tìm thấy phim.", Toast.LENGTH_SHORT, true).show();
        }
    }


    private void GetBundle() {

        chiTietPhim_model = new ChiTietPhim_Model();
        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("tenphim")) {
                chiTietPhim_model.setTenphim(bundle.getString("tenphim"));
                toolbartitle = bundle.getString("tenphim");
            }
            if (bundle.containsKey("hinhanh"))
                chiTietPhim_model.setHinhanh(bundle.getString("hinhanh"));
            if (bundle.containsKey("linkthongtinphim"))
                linkthongtinphim = bundle.getString("linkthongtinphim");

            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle(toolbartitle);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nothing, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search_fragment);
        if (item != null)
            item.setVisible(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
