package com.ani.anivn.TaiKhoan;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Login.Login_Fragment;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.YeuThich_Model;
import com.ani.anivn.Model.YeuThich_ModelDao;
import com.ani.anivn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by sev_user on 04/17/2018.
 */

public class TaiKhoan_Fragment extends Fragment implements View.OnClickListener {
    View view;

    AppCompatActivity activity;

    DaoSession daoSession;
    YeuThich_ModelDao yeuthich_dao;

    DatabaseReference ref;
    FirebaseAuth auth;

    TextView tv_taikhoan_email, tv_taikhoan_thoat, tv_taikhoan_upload, tv_taikhoan_download;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Tài Khoản");

        initSQL();

        FindViewById();

        CheckUser();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        yeuthich_dao = daoSession.getYeuThich_ModelDao();

    }

    private void FindViewById() {
        tv_taikhoan_email = (TextView) view.findViewById(R.id.tv_taikhoan_email);
        tv_taikhoan_thoat = (TextView) view.findViewById(R.id.tv_taikhoan_thoat);
        tv_taikhoan_upload = (TextView) view.findViewById(R.id.tv_taikhoan_upload);
        tv_taikhoan_download = (TextView) view.findViewById(R.id.tv_taikhoan_download);

        tv_taikhoan_thoat.setOnClickListener(this);
        tv_taikhoan_upload.setOnClickListener(this);
        tv_taikhoan_download.setOnClickListener(this);
    }

    private void CheckUser() {
        if (auth.getCurrentUser() != null) {
            tv_taikhoan_email.setText("Xin Chào :" + auth.getCurrentUser().getEmail());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_taikhoan_thoat) {

            auth.signOut();

            try {
                Toasty.success(getContext(), "Thoát tài khoản thành công !", Toast.LENGTH_SHORT, true).show();
            } catch (Exception e) {

            }
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("Login_Fragment")
                    .commit();


        } else if (id == R.id.tv_taikhoan_upload) {

            if (auth.getCurrentUser() != null) {

                ref.child("savedata").child(auth.getCurrentUser().getUid()).setValue(null);

                List<YeuThich_Model> listYeuThich = new ArrayList<>();
                listYeuThich = yeuthich_dao.queryBuilder().orderAsc(YeuThich_ModelDao.Properties.Idsql).build().list();
                if (listYeuThich.size() > 0) {

                    ref.child("savedata").child(auth.getCurrentUser().getUid()).setValue(listYeuThich);

                    try {
                        Toasty.success(getContext(), "Upload Phim Yêu Thích lên server !", Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {

                    }
                } else {
                    try {
                        Toasty.error(getContext(), "Chưa có bất kì phim nào trong mục yêu thích !", Toast.LENGTH_SHORT, true).show();
                    } catch (Exception e) {

                    }
                }
            } else {
                try{
                Toasty.error(getContext(), "Bạn cần phải đăng nhập tài khoản !", Toast.LENGTH_SHORT, true).show();
                }catch (Exception e){

                }
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("Login_Fragment")
                        .commit();
            }


        } else if (id == R.id.tv_taikhoan_download) {

            if (auth.getCurrentUser() != null) {

                ref.child("savedata").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                          /* This method is called once with the initial value and again whenever data at this location is updated.*/

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            YeuThich_Model y = postSnapshot.getValue(YeuThich_Model.class);

                            Long check = yeuthich_dao.queryBuilder().where(YeuThich_ModelDao.Properties.Tenphim.eq(y.getTenphim())).count();
                            if (check == 0) {

                                YeuThich_Model yeuThich_model = new YeuThich_Model();
                                yeuThich_model.setTenphim(y.getTenphim());
                                yeuThich_model.setTap(y.getTap());
                                yeuThich_model.setHinhanh(y.getHinhanh());
                                yeuThich_model.setLinkthongtinphim(y.getLinkthongtinphim());
                                yeuThich_model.setNam(y.getNam());
                                yeuThich_model.setMota(y.getMota());

                                yeuthich_dao.save(yeuThich_model);

                            }
                        }

                        try {
                            Toasty.success(getContext(), "Đã lưu phim !", Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //  Log.w(TAG, "Failed to read value.", error.toException());
                        try {
                            Toasty.success(getContext(), "Lỗi không thể lấy dữ liệu !", Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {

                        }

                    }
                });

            } else {
                try {
                    Toasty.error(getContext(), "Bạn cần phải đăng nhập tài khoản !", Toast.LENGTH_SHORT, true).show();
                } catch (Exception e) {

                }


                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("Login_Fragment")
                        .commit();
            }
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
}
