package com.ani.anivn;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ani.anivn.Config.Constant;
import com.ani.anivn.Config.Get_Versioncode;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.Anime_LuuTrang_ModelDao;
import com.ani.anivn.Model.Anime_ModelDao;
import com.ani.anivn.Model.BangXepHangNgay_ModelDao;
import com.ani.anivn.Model.CNAnimation_LuuTrang_ModelDao;
import com.ani.anivn.Model.CNAnimation_ModelDao;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.HoatHinh_LuuTrang_ModelDao;
import com.ani.anivn.Model.HoatHinh_ModelDao;
import com.ani.anivn.Model.Luu_Model;
import com.ani.anivn.Model.Luu_ModelDao;
import com.ani.anivn.Model.NamPhatHanh_LuuTrang_ModelDao;
import com.ani.anivn.Model.NamPhatHanh_ModelDao;
import com.ani.anivn.Model.PhimMoiCapNhat_ModelDao;

public class SplashScreenActivity extends AppCompatActivity {

    DaoSession daoSession;

    PhimMoiCapNhat_ModelDao phimMoiCapNhat_modelDao;

    BangXepHangNgay_ModelDao bangXepHangNgay_modelDao;

    CNAnimation_ModelDao cnAnimation_modelDao;
    CNAnimation_LuuTrang_ModelDao cnAnimation_luuTrang_modelDao;

    Anime_ModelDao anime_modelDao;
    Anime_LuuTrang_ModelDao anime_luuTrang_modelDao;

    HoatHinh_ModelDao hoatHinh_modelDao;
    HoatHinh_LuuTrang_ModelDao hoatHinh_luuTrang_modelDao;

    NamPhatHanh_ModelDao namPhatHanh_modelDao;
    NamPhatHanh_LuuTrang_ModelDao namPhatHanh_luuTrang_modelDao;

    Luu_ModelDao luu_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initSQL();

        Check_Versioncode();

    }

    private void initSQL() {
        daoSession = ((SqlApp) getApplication()).getDaoSession();

        phimMoiCapNhat_modelDao = daoSession.getPhimMoiCapNhat_ModelDao();
        phimMoiCapNhat_modelDao.deleteAll();

        bangXepHangNgay_modelDao = daoSession.getBangXepHangNgay_ModelDao();
        bangXepHangNgay_modelDao.deleteAll();

        cnAnimation_modelDao = daoSession.getCNAnimation_ModelDao();
        cnAnimation_modelDao.deleteAll();

        cnAnimation_luuTrang_modelDao = daoSession.getCNAnimation_LuuTrang_ModelDao();
        cnAnimation_luuTrang_modelDao.deleteAll();

        anime_modelDao = daoSession.getAnime_ModelDao();
        anime_modelDao.deleteAll();

        anime_luuTrang_modelDao = daoSession.getAnime_LuuTrang_ModelDao();
        anime_luuTrang_modelDao.deleteAll();

        hoatHinh_modelDao = daoSession.getHoatHinh_ModelDao();
        hoatHinh_luuTrang_modelDao = daoSession.getHoatHinh_LuuTrang_ModelDao();

        namPhatHanh_modelDao = daoSession.getNamPhatHanh_ModelDao();
        namPhatHanh_modelDao.deleteAll();

        namPhatHanh_luuTrang_modelDao = daoSession.getNamPhatHanh_LuuTrang_ModelDao();
        namPhatHanh_luuTrang_modelDao.deleteAll();

        luu_dao = daoSession.getLuu_ModelDao();

        luu_dao.deleteAll();

        Luu_Model luu_model_cnanimation = new Luu_Model();
        luu_model_cnanimation.setIndex_choose(0);
        luu_model_cnanimation.setTAG(Constant.TAG_LUU_CNANIMATION);
        luu_model_cnanimation.setTrang_daluu("1");
        luu_dao.save(luu_model_cnanimation);


        Luu_Model luu_model_anime = new Luu_Model();
        luu_model_anime.setIndex_choose(0);
        luu_model_anime.setTAG(Constant.TAG_LUU_ANIME);
        luu_model_anime.setTrang_daluu("1");
        luu_dao.save(luu_model_anime);


        Luu_Model luu_model_hoathinh = new Luu_Model();
        luu_model_hoathinh.setIndex_choose(0);
        luu_model_hoathinh.setTAG(Constant.TAG_LUU_HOATHINH);
        luu_model_hoathinh.setTrang_daluu("1");
        luu_dao.save(luu_model_hoathinh);


        Luu_Model luu_model_namphathanh = new Luu_Model();
        luu_model_namphathanh.setIndex_choose(0);
        luu_model_namphathanh.setTAG(Constant.TAG_LUU_NAMPHATHANH);
        luu_model_namphathanh.setTrang_daluu("1");
        luu_dao.save(luu_model_namphathanh);


    }


    private void OpenGoogleStore() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    private void GoToMain() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                // close this activity
                finish();
            }
        }, 3000);
    }

    private void Check_Versioncode() {
        Get_Versioncode get_versioncode = new Get_Versioncode(SplashScreenActivity.this);
        get_versioncode.GetVersionCode(new Get_Versioncode.VersionCode_Callback() {
            @Override
            public void onSuccess(String versioncode) {
                int VERSION_CODE = BuildConfig.VERSION_CODE;
                if (versioncode.length() > 0) {

                    if (VERSION_CODE != Integer.parseInt(versioncode)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                        builder.setTitle(R.string.update_title);
                        builder.setMessage(R.string.update_message);
                        builder.setCancelable(false);
                        builder.setPositiveButton(
                                "Update now",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        OpenGoogleStore();
                                    }
                                });
                        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                GoToMain();


                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {


                        GoToMain();
                    }

                } else {

                    GoToMain();
                }


            }

            @Override
            public void onFail(String message) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    GoToMain();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    GoToMain();
                }
            }

        });
    }
}
