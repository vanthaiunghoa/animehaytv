package com.ani.anivn.NamPhatHanh;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ani.anivn.Config.Constant;
import com.ani.anivn.Config.Get_NamPhatHanh;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.Luu_Model;
import com.ani.anivn.Model.Luu_ModelDao;
import com.ani.anivn.Model.NamPhatHanh_LuuTrang_Model;
import com.ani.anivn.Model.NamPhatHanh_LuuTrang_ModelDao;
import com.ani.anivn.Model.NamPhatHanh_Model;
import com.ani.anivn.Model.NamPhatHanh_ModelDao;
import com.ani.anivn.NamPhatHanh.Adapter.NamPhatHanh_Adapter;
import com.ani.anivn.NamPhatHanh.Adapter.NamPhatHanh_Trang_Adapter;
import com.ani.anivn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 04/06/2018.
 */

public class NamPhatHanh_Fragment extends Fragment {
    View view;

    RecyclerView recyclerView, recyclerView_trang;
    ProgressBar progressBar;

    List<NamPhatHanh_Model> listItem;
    List<NamPhatHanh_LuuTrang_Model> listTrang;
    NamPhatHanh_Adapter adapter;
    NamPhatHanh_Trang_Adapter trang_adapter;

    DaoSession daoSession;
    NamPhatHanh_ModelDao dao;
    NamPhatHanh_LuuTrang_ModelDao trang_dao;
    Luu_ModelDao luu_dao;

    Get_NamPhatHanh get_namPhatHanh;

    CharSequence[] listCharNamPhatHanh = {"2018", "2017", "2016", "2015", "2014", "2013", "2012", "Trước 2012"};
    int index_choose = 0;
    int temp_index = index_choose;
    String trang = "1";
    Luu_Model luu_model_namphathanh;
    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_namphathanh, container, false);

         activity=(AppCompatActivity)view.getContext();
        activity. getSupportActionBar().setTitle("Năm Phát Hành");

        initSQL();

        FindViewById();

        CheckDaLuu();

        GetDataSQL();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        dao = daoSession.getNamPhatHanh_ModelDao();
        trang_dao = daoSession.getNamPhatHanh_LuuTrang_ModelDao();
        luu_dao = daoSession.getLuu_ModelDao();

    }

    private void FindViewById() {

        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setTitle("Năm Phát Hành");

        progressBar = view.findViewById(R.id.progressbar_namphathanh);
        recyclerView = view.findViewById(R.id.recyclerview_namphathanh);
        recyclerView_trang = view.findViewById(R.id.recyclerview_namphathanh_trang);

        get_namPhatHanh = new Get_NamPhatHanh(getContext());

    }

    private void CheckDaLuu() {
        luu_model_namphathanh = luu_dao.queryBuilder().where(Luu_ModelDao.Properties.TAG.eq(Constant.TAG_LUU_NAMPHATHANH)).build().unique();
        if (luu_model_namphathanh != null) {
            index_choose = luu_model_namphathanh.getIndex_choose();
            temp_index = index_choose;
            trang = luu_model_namphathanh.getTrang_daluu();

        }
    }

    private void GetDataSQL() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setTitle("Năm Phát Hành" + " - " + listCharNamPhatHanh[index_choose]);


        listItem = new ArrayList<>();

        listItem = dao.queryBuilder().where(NamPhatHanh_ModelDao.Properties.Trang.eq(trang), NamPhatHanh_ModelDao.Properties.Index_list.eq(index_choose)).build().list();

        if (listItem.size() > 0) {

            SetupRecyclerview();

            listTrang = new ArrayList<>();

            listTrang = trang_dao.queryBuilder().where(NamPhatHanh_LuuTrang_ModelDao.Properties.Tranghientai.eq(trang), NamPhatHanh_LuuTrang_ModelDao.Properties.Index_list.eq(index_choose)).build().list();

            SetupRecyclerview_Trang();

            progressBar.setVisibility(View.GONE);

        } else {

            GetDataServer();

        }


    }

    private void GetDataServer() {
        get_namPhatHanh.NamPhatHanh(index_choose, trang, dao, trang_dao, new Get_NamPhatHanh.NamPhatHanh_Callback() {
            @Override
            public void onSuccess(List<NamPhatHanh_Model> list_data, List<NamPhatHanh_LuuTrang_Model> list_trang) {

                listItem = list_data;
                SetupRecyclerview();

                listTrang = list_trang;
                SetupRecyclerview_Trang();


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
    }

    private void SetupRecyclerview() {

        recyclerView.setHasFixedSize(true);
        adapter = new NamPhatHanh_Adapter(getContext(), listItem, progressBar);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    private void SetupRecyclerview_Trang() {
        recyclerView_trang.setHasFixedSize(true);
        trang_adapter = new NamPhatHanh_Trang_Adapter(getContext(), listTrang, progressBar, dao, trang_dao, recyclerView, adapter, listItem, index_choose, luu_dao);
        recyclerView_trang.setItemAnimator(new DefaultItemAnimator());
        recyclerView_trang.setLayoutManager(new GridLayoutManager(getContext(), 8, GridLayoutManager.VERTICAL, false));
        recyclerView_trang.setAdapter(trang_adapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_namphathanh, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_list_namphathanh:

                AlertDialog.Builder builder;
                AlertDialog alert;

                temp_index = index_choose;

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Danh Sách Năm Phát Hành");
                builder.setSingleChoiceItems(listCharNamPhatHanh, temp_index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        temp_index = i;

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if (temp_index != index_choose) {
                            index_choose = temp_index;
                            if (listItem != null) {
                                listItem.clear();

                            }
                            if (listTrang != null) {
                                listTrang.clear();

                            }


                            if (luu_model_namphathanh != null) {
                                luu_model_namphathanh.setIndex_choose(index_choose);
                                luu_model_namphathanh.setTrang_daluu("1");
                                luu_dao.update(luu_model_namphathanh);
                            }
                            trang_adapter.notifyDataSetChanged();
                            adapter.notifyDataSetChanged();
                            GetDataSQL();

                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();

                    }
                });
                alert = builder.create();
                alert.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.action_search_fragment);
//        if (item != null)
//            item.setVisible(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
