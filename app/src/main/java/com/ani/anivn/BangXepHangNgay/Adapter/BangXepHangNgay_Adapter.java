package com.ani.anivn.BangXepHangNgay.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ani.anivn.ChiTietPhim.ChiTietPhim_Fragment;
import com.ani.anivn.Model.BangXepHangNgay_Model;
import com.ani.anivn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sev_user on 04/05/2018.
 */

public class BangXepHangNgay_Adapter extends RecyclerView.Adapter<BangXepHangNgay_Adapter.MyViewHolder> {

    List<BangXepHangNgay_Model> itemsList;
    Context context;
    AppCompatActivity activity;
    ProgressBar progressBar;

    public BangXepHangNgay_Adapter(Context context, List<BangXepHangNgay_Model> itemsList, ProgressBar progressBar) {
        this.itemsList = itemsList;
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((context)).inflate(R.layout.custom_layout_cardview_bangxephangngay, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BangXepHangNgay_Model film = itemsList.get(position);
        holder.tv_tenphim_cardview_phim_bxhn.setText(film.getTenphim());
        holder.tv_luotxem_cardview_phim_bxhn.setText(film.getLuotxem());

        if (film.getHinhanh().length() > 0)
            Picasso.with(context).load(film.getHinhanh()).fit().centerCrop().error(R.drawable.default_fail_img).into(holder.img_cardview_phim_bxhn);
        else
            Picasso.with(context).load(R.drawable.default_fail_img).fit().centerCrop().error(R.drawable.default_fail_img).into(holder.img_cardview_phim_bxhn);

        holder.cardview_phim_bxhn.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_tenphim_cardview_phim_bxhn, tv_luotxem_cardview_phim_bxhn;
        ImageView img_cardview_phim_bxhn;
        LinearLayout cardview_phim_bxhn;


        public MyViewHolder(View view) {
            super(view);

            cardview_phim_bxhn = view.findViewById(R.id.cardview_phim_bxhn);
            img_cardview_phim_bxhn = view.findViewById(R.id.img_cardview_phim_bxhn);
            tv_tenphim_cardview_phim_bxhn = view.findViewById(R.id.tv_tenphim_cardview_phim_bxhn);
            tv_luotxem_cardview_phim_bxhn = view.findViewById(R.id.tv_luotxem_cardview_phim_bxhn);

        }


    }
}


