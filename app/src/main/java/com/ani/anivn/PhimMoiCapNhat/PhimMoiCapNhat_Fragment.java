package com.ani.anivn.PhimMoiCapNhat;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ani.anivn.Config.Get_PhimMoiCapNhat;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.PhimMoiCapNhat_Model;
import com.ani.anivn.Model.PhimMoiCapNhat_ModelDao;
import com.ani.anivn.PhimMoiCapNhat.Adapter.PhimMoiCapNhat_Adapter;
import com.ani.anivn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 04/05/2018.
 */

public class PhimMoiCapNhat_Fragment extends Fragment {
    View view;

    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    PhimMoiCapNhat_Adapter adapter;
    List<PhimMoiCapNhat_Model> listItem;

    Get_PhimMoiCapNhat get_phimMoiCapNhat;

    DaoSession daoSession;
    PhimMoiCapNhat_ModelDao phimmoicapnhat_dao;
    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_phimmoicapnhat, container, false);

        activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Phim Mới Cập Nhật");

        FindViewById();

        initSQL();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        phimmoicapnhat_dao = daoSession.getPhimMoiCapNhat_ModelDao();
    }

    private void FindViewById() {
        progressBar = view.findViewById(R.id.progressbar_phimmoicapnhat);
        recyclerView = view.findViewById(R.id.recyclerview_phimmoicapnhat);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout_phimmoicapnhat);

        get_phimMoiCapNhat = new Get_PhimMoiCapNhat(getContext());

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                GetDataSQL();

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Showing refresh animation before making http call

//                listItem.clear();
//                adapter.notifyDataSetChanged();
//                phimmoicapnhat_dao.deleteAll();
//
//                GetDataServer();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void GetDataSQL() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        swipeRefreshLayout.setRefreshing(true);

        listItem = new ArrayList<>();
        listItem = phimmoicapnhat_dao.queryBuilder().build().list();
        if (listItem.size() > 0) {

            SetupRecyclerview();

            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

        } else {

            GetDataServer();

        }

    }

    private void GetDataServer() {

        get_phimMoiCapNhat.PhimMoiCapNhat(phimmoicapnhat_dao, new Get_PhimMoiCapNhat.PhimMoiCapNhat_Callback() {
            @Override
            public void onSuccess(List<PhimMoiCapNhat_Model> list_data) {

                if (list_data.size() > 0) {

                    listItem = list_data;
                    SetupRecyclerview();

                }

                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFail(String message) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

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

    }

    private void SetupRecyclerview() {
        recyclerView.setHasFixedSize(true);
        adapter = new PhimMoiCapNhat_Adapter(getContext(), listItem, progressBar);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
