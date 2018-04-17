package com.ani.anivn.Episode.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import com.ani.anivn.Config.Get_Video;
import com.ani.anivn.Exoplayer.Exoplayer_Fragment;
import com.ani.anivn.Model.Episode_Model;
import com.ani.anivn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 04/06/2018.
 */

public class Episode_Adapter extends RecyclerView.Adapter<Episode_Adapter.MyViewHolder> {

    List<Episode_Model> itemsList;
    Context context;
    AppCompatActivity activity;
    ProgressBar progressBar;
    TextView tv_chontap_episode;

    public Episode_Adapter(Context context, List<Episode_Model> itemsList, ProgressBar progressBar, TextView tv_chontap_episode) {
        this.itemsList = itemsList;
        this.context = context;
        this.progressBar = progressBar;
        this.tv_chontap_episode = tv_chontap_episode;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((context)).inflate(R.layout.custom_layout_cardview_trang, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Episode_Model film = itemsList.get(position);
        holder.tv_trang.setText(film.getTen_tap());

        holder.cardview_trang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();

                tv_chontap_episode.setText("Đang chọn tập : " + film.getTen_tap());

                holder.cardview_trang.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));

                Get_Video get_video = new Get_Video(context);
                get_video.Video(film.getLink_tap(), new Get_Video.Episode_Callback() {
                    @Override
                    public void onSuccess(ArrayList<String> list_data) {

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("listURL", list_data);

                        Exoplayer_Fragment exoplayer_fragment = new Exoplayer_Fragment();

                        exoplayer_fragment.setArguments(bundle);

                        progressBar.setVisibility(View.GONE);

                        activity = (AppCompatActivity) v.getContext();
                        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame_episode, exoplayer_fragment, "Exoplayer_Fragment")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
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
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
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


