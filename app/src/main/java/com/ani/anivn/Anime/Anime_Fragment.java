package com.ani.anivn.Anime;

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

import com.ani.anivn.Anime.Adapter.Anime_Adapter;
import com.ani.anivn.Anime.Adapter.Anime_Trang_Adapter;
import com.ani.anivn.Config.Constant;
import com.ani.anivn.Config.Get_Anime;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.Anime_LuuTrang_Model;
import com.ani.anivn.Model.Anime_LuuTrang_ModelDao;
import com.ani.anivn.Model.Anime_Model;
import com.ani.anivn.Model.Anime_ModelDao;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.Luu_Model;
import com.ani.anivn.Model.Luu_ModelDao;
import com.ani.anivn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 04/06/2018.
 */

public class Anime_Fragment extends Fragment {
    View view;

    RecyclerView recyclerView, recyclerView_trang;
    ProgressBar progressBar;

    List<Anime_Model> listItem;
    List<Anime_LuuTrang_Model> listTrang;
    Anime_Adapter adapter;
    Anime_Trang_Adapter trang_adapter;

    DaoSession daoSession;
    Anime_ModelDao dao;
    Anime_LuuTrang_ModelDao trang_dao;
    Luu_ModelDao luu_dao;

    Get_Anime get_anime;

    CharSequence[] listCharCNAnime = {"Tất Cả", "Hành động", "Tình Cảm", "Lịch sử", "Hài Hước", "Viễn Tưởng", "Võ Thuật", "Giả tưởng", "Kinh Dị", "Phiêu Lưu", "Học Đường", "Đời Thường", "Siêu nhiên",
            "Harem", "Ecchi", "Shounen", "Phép Thuật", "Trò chơi", "Thám Tử", "Mystery", "Drama", "Seinen", "Ác quỷ", "Ma cà rồng", "Psychological", "Shoujo", "Shounen Ai", "Tragedy", "Mecha",
            "Siêu năng lực", "Parody", "Quân đội", "Live Action", "Shoujo AI", "Josei", "Thể Thao", "Âm nhạc", "Samurai", "Tokusatsu", "Space", "Thriller"};
    int index_choose = 0;
    int temp_index = index_choose;
    String trang = "1";
    Luu_Model luu_model_anime;
    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_anime, container, false);

        activity=(AppCompatActivity)view.getContext();
        activity.getSupportActionBar().setTitle("Anime");

        initSQL();

        FindViewById();

        CheckDaLuu();

        GetDataSQL();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        dao = daoSession.getAnime_ModelDao();
        trang_dao = daoSession.getAnime_LuuTrang_ModelDao();
        luu_dao = daoSession.getLuu_ModelDao();

    }

    private void FindViewById() {

        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setTitle("Anime");

        progressBar = view.findViewById(R.id.progressbar_anime);
        recyclerView = view.findViewById(R.id.recyclerview_anime);
        recyclerView_trang = view.findViewById(R.id.recyclerview_anime_trang);

        get_anime = new Get_Anime(getContext());

    }

    private void CheckDaLuu() {
        luu_model_anime = luu_dao.queryBuilder().where(Luu_ModelDao.Properties.TAG.eq(Constant.TAG_LUU_ANIME)).build().uniqueOrThrow();
        if (luu_model_anime != null) {
            index_choose = luu_model_anime.getIndex_choose();
            temp_index = index_choose;
            trang = luu_model_anime.getTrang_daluu();

        }
    }

    private void GetDataSQL() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setTitle("Anime" + " - " + listCharCNAnime[index_choose]);


        listItem = new ArrayList<>();

        listItem = dao.queryBuilder().where(Anime_ModelDao.Properties.Trang.eq(trang), Anime_ModelDao.Properties.Index_list.eq(index_choose)).build().list();

        if (listItem.size() > 0) {

            SetupRecyclerview();

            listTrang = new ArrayList<>();

            listTrang = trang_dao.queryBuilder().where(Anime_LuuTrang_ModelDao.Properties.Tranghientai.eq(trang), Anime_LuuTrang_ModelDao.Properties.Index_list.eq(index_choose)).build().list();

            SetupRecyclerview_Trang();

            progressBar.setVisibility(View.GONE);

        } else {

            GetDataServer();

        }


    }

    private void GetDataServer() {
        get_anime.Anime(index_choose, trang, dao, trang_dao, new Get_Anime.Anime_Callback() {
            @Override
            public void onSuccess(List<Anime_Model> list_data, List<Anime_LuuTrang_Model> list_trang) {

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
        adapter = new Anime_Adapter(getContext(), listItem, progressBar);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    private void SetupRecyclerview_Trang() {
        recyclerView_trang.setHasFixedSize(true);
        trang_adapter = new Anime_Trang_Adapter(getContext(), listTrang, progressBar, dao, trang_dao, recyclerView, adapter, listItem, index_choose, luu_dao);
        recyclerView_trang.setItemAnimator(new DefaultItemAnimator());
        recyclerView_trang.setLayoutManager(new GridLayoutManager(getContext(), 8, GridLayoutManager.VERTICAL, false));
        recyclerView_trang.setAdapter(trang_adapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_anime, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_list_anime:

                AlertDialog.Builder builder;
                AlertDialog alert;

                temp_index = index_choose;

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Danh Sách Anime");
                builder.setSingleChoiceItems(listCharCNAnime, temp_index, new DialogInterface.OnClickListener() {
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


                            if (luu_model_anime != null) {
                                luu_model_anime.setIndex_choose(index_choose);
                                luu_model_anime.setTrang_daluu("1");
                                luu_dao.update(luu_model_anime);
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

