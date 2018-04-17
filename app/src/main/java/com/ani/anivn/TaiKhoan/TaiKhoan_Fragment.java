package com.phamhai.testimage.TaiKhoan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phamhai.testimage.R;

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
        view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

         //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        
        activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Tài Khoản");
        
          initSQL();
        
        FindViewById();

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_taikhoan_thoat) {

               auth.signOut() ;
               
                  Toasty.success(getContext(), "Thoát tài khoản thành công !", Toast.LENGTH_SHORT, true).show();
                    
                   FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                  fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                                      .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                       addToBackStack("Login_Fragment")
                                        .commit();
         
          
        } else if (id == R.id.tv_taikhoan_upload) {
            
            if(auth.getCurrentUser() != null){
                List<YeuThich_Model> listYeuThich= new ArrayList<>();
                 listYeuThich = yeuthich_dao.queryBuilder().orderAsc(YeuThich_ModelDao.Properties.Idsql).build().list();
                if(listYeuThich . size() > 0){
                    
                    ref.child("savedata").child(auth.getCurrentUser().getUid()).setValue(listdata);
                    
                    Toasty.success(getContext(), "Upload Phim Yêu Thích lên server !", Toast.LENGTH_SHORT, true).show();
                    
                }else{
                    Toasty.error(getContext(), "Chưa có bất kì phim nào trong mục yêu thích !", Toast.LENGTH_SHORT, true).show();
                }
            }else{
                 Toasty.error(getContext(), "Bạn cần phải đăng nhập tài khoản !", Toast.LENGTH_SHORT, true).show();
                 FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                  fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                                      .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                       addToBackStack("Login_Fragment")
                                        .commit();
            }
            
             
        } else if (id == R.id.tv_taikhoan_download) {
            
//              if(auth.getCurrentUser() != null){
//                 List<YeuThich_Model> listYeuThich= new ArrayList<>();
//                  listYeuThich = yeuthich_dao.queryBuilder().orderAsc(YeuThich_ModelDao.Properties.Idsql).build().list();
//                 if(listYeuThich . size() > 0){
                    
//                     ref.child("savedata").child(auth.getCurrentUser().getUid()).setValue(listdata);
                    
//                     Toasty.success(getContext(), "Upload Phim Yêu Thích lên server !", Toast.LENGTH_SHORT, true).show();
                    
//                 }else{
//                     Toasty.error(getContext(), "Chưa có bất kì phim nào trong mục yêu thích !", Toast.LENGTH_SHORT, true).show();
//                 }
//             }else{
//                  Toasty.error(getContext(), "Bạn cần phải đăng nhập tài khoản !", Toast.LENGTH_SHORT, true).show();
//                  FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
//                   fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
//                                       .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                                        addToBackStack("Login_Fragment")
//                                         .commit();
//             }
        }
    }
}
