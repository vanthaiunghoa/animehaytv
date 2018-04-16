package com.ani.anivn.CNAnimation.Adapter;

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
import com.ani.anivn.Model.CNAnimation_Model;
import com.ani.anivn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sev_user on 04/06/2018.
 */

public class CNAnimation_Adapter extends RecyclerView.Adapter<CNAnimation_Adapter.MyViewHolder> {

    List<CNAnimation_Model> itemsList;
    Context context;
    AppCompatActivity activity;
    ProgressBar progressBar;

    public CNAnimation_Adapter(Context context, List<CNAnimation_Model> itemsList, ProgressBar progressBar) {
        this.itemsList = itemsList;
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((context)).inflate(R.layout.custom_layout_cardview_phim, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CNAnimation_Model film = itemsList.get(position);
        holder.tv_tenphim_cardview_phim.setText(film.getTenphim());
        holder.tv_tap_cardview_phim.setText(film.getTap());

        if (film.getHinhanh().length() > 0)
            Picasso.with(context).load(film.getHinhanh()).fit().centerCrop().error(R.drawable.default_fail_img).into(holder.img_cardview_phim);
        else
            Picasso.with(context).load(R.drawable.default_fail_img).fit().centerCrop().error(R.drawable.default_fail_img).into(holder.img_cardview_phim);

        holder.cardview_phim.setOnClickListener(new View.OnClickListener() {
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

        TextView tv_tenphim_cardview_phim, tv_tap_cardview_phim;
        ImageView img_cardview_phim;
        LinearLayout cardview_phim;


        public MyViewHolder(View view) {
            super(view);

            cardview_phim = view.findViewById(R.id.cardview_phim);
            img_cardview_phim = view.findViewById(R.id.img_cardview_phim);
            tv_tenphim_cardview_phim = view.findViewById(R.id.tv_tenphim_cardview_phim);
            tv_tap_cardview_phim = view.findViewById(R.id.tv_tap_cardview_phim);

        }


    }
}
