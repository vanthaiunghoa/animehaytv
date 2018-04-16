package com.ani.anivn.Anime.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ani.anivn.Config.Constant;
import com.ani.anivn.Config.Get_Anime;
import com.ani.anivn.Model.Anime_LuuTrang_Model;
import com.ani.anivn.Model.Anime_LuuTrang_ModelDao;
import com.ani.anivn.Model.Anime_Model;
import com.ani.anivn.Model.Anime_ModelDao;
import com.ani.anivn.Model.Luu_Model;
import com.ani.anivn.Model.Luu_ModelDao;
import com.ani.anivn.R;

import java.util.List;

/**
 * Created by sev_user on 04/06/2018.
 */

public class Anime_Trang_Adapter extends RecyclerView.Adapter<Anime_Trang_Adapter.MyViewHolder> {

    List<Anime_LuuTrang_Model> listTrang;
    Context context;
    AppCompatActivity activity;
    ProgressBar progressBar;

    Get_Anime get_anime;

    Anime_ModelDao dao;
    Anime_LuuTrang_ModelDao trang_dao;
    Luu_ModelDao luu_dao;

    RecyclerView recyclerView;
    List<Anime_Model> listItem;
    Anime_Adapter adapter;

    int index_choose;

    public Anime_Trang_Adapter(Context context, List<Anime_LuuTrang_Model> listTrang, ProgressBar progressBar, Anime_ModelDao dao, Anime_LuuTrang_ModelDao trang_dao, RecyclerView recyclerView, Anime_Adapter adapter, List<Anime_Model> listItem, int index_choose, Luu_ModelDao luu_dao) {
        this.listTrang = listTrang;
        this.context = context;
        this.progressBar = progressBar;
        this.dao = dao;
        this.trang_dao = trang_dao;
        this.adapter = adapter;
        this.listItem = listItem;
        this.recyclerView = recyclerView;
        this.index_choose = index_choose;
        this.luu_dao = luu_dao;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((context)).inflate(R.layout.custom_layout_cardview_trang, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Anime_LuuTrang_Model film = listTrang.get(position);
        holder.tv_trang.setText(film.getTrang());

        if (film.getTrang().equals(film.getTranghientai())) {
            holder.cardview_trang.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.cardview_trang.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }

        holder.cardview_trang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trang = film.getTrang();

                if (trang.equals("...")) {

                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.bringToFront();

                    if (trang == null || trang.equals(""))
                        trang = "1";

                    if (listItem != null)
                        listItem.clear();
                    if (listTrang != null)
                        listTrang.clear();


                    Luu_Model luu_model_anime = luu_dao.queryBuilder().where(Luu_ModelDao.Properties.TAG.eq(Constant.TAG_LUU_ANIME)).build().unique();
                    if (luu_model_anime != null) {
                        luu_model_anime.setTrang_daluu(trang);
                        luu_model_anime.setIndex_choose(index_choose);
                        luu_dao.update(luu_model_anime);
                    }

                    adapter.notifyDataSetChanged();
                    notifyDataSetChanged();


                    GetDataSQL(trang);

                }


            }
        });

    }

    private void GetDataSQL(String trang) {

        listItem = dao.queryBuilder().where(Anime_ModelDao.Properties.Trang.eq(trang), Anime_ModelDao.Properties.Index_list.eq(index_choose)).build().list();

        if (listItem.size() > 0) {

            adapter = new Anime_Adapter(context, listItem, progressBar);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            notifyDataSetChanged();

            listTrang = trang_dao.queryBuilder().where(Anime_LuuTrang_ModelDao.Properties.Tranghientai.eq(trang), Anime_LuuTrang_ModelDao.Properties.Index_list.eq(index_choose)).build().list();

            notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);

        } else {

            GetDataServer(trang);

        }


    }

    private void GetDataServer(String trang) {
        get_anime = new Get_Anime(context);

        get_anime.Anime(index_choose, trang, dao, trang_dao, new Get_Anime.Anime_Callback() {
            @Override
            public void onSuccess(List<Anime_Model> list_data, List<Anime_LuuTrang_Model> list_trang) {
                if (list_data.size() > 0) {

                    listItem = list_data;

                    adapter = new Anime_Adapter(context, listItem, progressBar);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    listTrang = list_trang;
                    notifyDataSetChanged();

                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFail(String message) {
                progressBar.setVisibility(View.GONE);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    @Override
    public int getItemCount() {
        return (null != listTrang ? listTrang.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trang;
        CardView cardview_trang;


        public MyViewHolder(View view) {
            super(view);

            cardview_trang = view.findViewById(R.id.cardview_trang);
            tv_trang = view.findViewById(R.id.tv_trang_cardview_trang);

        }


    }
}


