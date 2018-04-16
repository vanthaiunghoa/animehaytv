package com.ani.anivn.YeuThich.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.ChiTietPhim.ChiTietPhim_Fragment;
import com.ani.anivn.Model.YeuThich_Model;
import com.ani.anivn.Model.YeuThich_ModelDao;
import com.ani.anivn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by sev_user on 04/05/2018.
 */

public class YeuThich_Adapter extends RecyclerView.Adapter<YeuThich_Adapter.MyViewHolder> {

    List<YeuThich_Model> itemsList;
    Context context;
    AppCompatActivity activity;
    ProgressBar progressBar;
    TextView tv_empty_yeuthich;
    YeuThich_ModelDao yeuthich_dao;

    public YeuThich_Adapter(Context context, List<YeuThich_Model> itemsList, ProgressBar progressBar, TextView tv_empty_yeuthich, YeuThich_ModelDao yeuthich_dao) {
        this.itemsList = itemsList;
        this.context = context;
        this.progressBar = progressBar;
        this.tv_empty_yeuthich = tv_empty_yeuthich;
        this.yeuthich_dao = yeuthich_dao;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((context)).inflate(R.layout.custom_layout_cardview_yeuthich, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final YeuThich_Model film = itemsList.get(position);
        holder.tv_tenphim_cardview_yeuthich.setText(film.getTenphim());
        // holder.tv_tap_cardview_phim.setText(film.getTap());

        if (film.getHinhanh().length() > 0)
            Picasso.with(context).load(film.getHinhanh()).fit().centerCrop().error(R.drawable.default_fail_img).into(holder.img_cardview_yeuthich);
        else
            Picasso.with(context).load(R.drawable.default_fail_img).fit().centerCrop().error(R.drawable.default_fail_img).into(holder.img_cardview_yeuthich);

        holder.cardview_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();

                Bundle bundle = new Bundle();
                bundle.putString("tenphim", film.getTenphim());
                bundle.putString("hinhanh", film.getHinhanh());
                bundle.putString("linkthongtinphim", film.getLinkthongtinphim());

                ChiTietPhim_Fragment chiTietPhim_fragment = new ChiTietPhim_Fragment();

                chiTietPhim_fragment.setArguments(bundle);

                progressBar.setVisibility(View.GONE);

                activity = (AppCompatActivity) v.getContext();
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, chiTietPhim_fragment, "ChiTietPhim_Fragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("ChiTietPhim_Fragment")
                        .commit();


            }
        });

        holder.img_more_cardview_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, holder.img_more_cardview_yeuthich);
                //inflating menu from xml resource
                popup.inflate(R.menu.popup_yeuthich);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.popup_item_xoa_yeuthich:

                                yeuthich_dao.delete(film);
                                itemsList.remove(position);
                                notifyDataSetChanged();

                                if (itemsList.size() > 0) {
                                    tv_empty_yeuthich.setVisibility(View.GONE);
                                } else {
                                    tv_empty_yeuthich.setVisibility(View.VISIBLE);
                                }

                                Toasty.success(context, "Bỏ thích thành công !", Toast.LENGTH_SHORT,true).show();

                                break;
                        }

                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_tenphim_cardview_yeuthich, tv_tap_cardview_yeuthich;
        ImageView img_cardview_yeuthich, img_more_cardview_yeuthich;
        LinearLayout cardview_yeuthich;


        public MyViewHolder(View view) {
            super(view);

            cardview_yeuthich = view.findViewById(R.id.cardview_yeuthich);
            img_cardview_yeuthich = view.findViewById(R.id.img_cardview_yeuthich);
            tv_tenphim_cardview_yeuthich = view.findViewById(R.id.tv_tenphim_cardview_yeuthich);
            tv_tap_cardview_yeuthich = view.findViewById(R.id.tv_tap_cardview_yeuthich);
            img_more_cardview_yeuthich = view.findViewById(R.id.img_more_cardview_yeuthich);

        }


    }
}

