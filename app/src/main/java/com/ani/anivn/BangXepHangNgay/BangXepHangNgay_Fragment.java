package com.ani.anivn.BangXepHangNgay;

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

import com.ani.anivn.BangXepHangNgay.Adapter.BangXepHangNgay_Adapter;
import com.ani.anivn.Config.Get_BangXepHangNgay;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.BangXepHangNgay_Model;
import com.ani.anivn.Model.BangXepHangNgay_ModelDao;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 04/05/2018.
 */

public class BangXepHangNgay_Fragment extends Fragment {
    View view;

    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    BangXepHangNgay_Adapter adapter;
    List<BangXepHangNgay_Model> listItem;

    Get_BangXepHangNgay get_bangXepHangNgay;

    DaoSession daoSession;
    BangXepHangNgay_ModelDao bangXepHangNgay_modelDao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_bangxephangngay, container, false);

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Bảng Xếp Hạng Ngày");

        FindViewById();

        initSQL();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        bangXepHangNgay_modelDao = daoSession.getBangXepHangNgay_ModelDao();
    }

    private void FindViewById() {
        progressBar = view.findViewById(R.id.progressbar_bxhn);
        recyclerView = view.findViewById(R.id.recyclerview_bxhn);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout_bxhn);

        get_bangXepHangNgay = new Get_BangXepHangNgay(getContext());

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
//                if (listItem != null)
//                    adapter.notifyDataSetChanged();
//                bangXepHangNgay_modelDao.deleteAll();
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
        listItem = bangXepHangNgay_modelDao.queryBuilder().build().list();
        if (listItem.size() > 0) {

            SetupRecyclerview();

            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

        } else {

            GetDataServer_API();

        }

    }

    private void GetDataServer_API() {

        get_bangXepHangNgay.BangXepHangNgay_API(bangXepHangNgay_modelDao, new Get_BangXepHangNgay.BangXepHangNgay_Callback() {
            @Override
            public void onSuccess(List<BangXepHangNgay_Model> list_data) {

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
        adapter = new BangXepHangNgay_Adapter(getContext(), listItem, progressBar);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
