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

    TextView tv_taikhoan_email, tv_taikhoan_thoat, tv_taikhoan_upload, tv_taikhoan_download;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        FindViewById();

        return view;
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

        } else if (id == R.id.tv_taikhoan_upload) {

        } else if (id == R.id.tv_taikhoan_download) {

        }
    }
}
