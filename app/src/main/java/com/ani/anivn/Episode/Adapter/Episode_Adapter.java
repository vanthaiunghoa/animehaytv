package com.ani.anivn.Episode.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.Config.Constant;
import com.ani.anivn.Config.Get_Video;
import com.ani.anivn.Exoplayer.Exoplayer_Fragment;
import com.ani.anivn.Exoplayer.Videoview_Fragment;
import com.ani.anivn.Model.Episode_Model;
import com.ani.anivn.Model.Luu_Checkbox_Exoplayer_Videoview_Model;
import com.ani.anivn.Model.Luu_Checkbox_Exoplayer_Videoview_ModelDao;
import com.ani.anivn.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

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
    Luu_Checkbox_Exoplayer_Videoview_ModelDao luu_videoview_exo_dao;

    public Episode_Adapter(Context context, List<Episode_Model> itemsList, ProgressBar progressBar, TextView tv_chontap_episode, Luu_Checkbox_Exoplayer_Videoview_ModelDao luu_videoview_exo_dao) {
        this.itemsList = itemsList;
        this.context = context;
        this.progressBar = progressBar;
        this.tv_chontap_episode = tv_chontap_episode;
        this.luu_videoview_exo_dao = luu_videoview_exo_dao;
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
                activity = (AppCompatActivity) v.getContext();

                Admod(activity);

                try {

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.bringToFront();

                    tv_chontap_episode.setText("Đang chọn tập : " + film.getTen_tap());

                    holder.cardview_trang.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));

                    Get_Video get_video = new Get_Video(context);
                    get_video.Video(film.getLink_tap(), new Get_Video.Episode_Callback() {
                        @Override
                        public void onSuccess(ArrayList<String> list_data) {

                            //   Log.d("TESTAPI", list_data.toString());

                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("listURL", list_data);
                            Luu_Checkbox_Exoplayer_Videoview_Model luu_videoview = luu_videoview_exo_dao.queryBuilder().where(Luu_Checkbox_Exoplayer_Videoview_ModelDao.Properties.Tag.eq(Constant.TAG_EXOPLAYER_VIDEOVIEW)).build().unique();
                            boolean isChecked = false;
                            if (luu_videoview != null) {
                                isChecked = luu_videoview.getIsChecked();
                            }
                          //  Log.d("TESTCHECKBOX", isChecked + "");

                            progressBar.setVisibility(View.GONE);

                            if (isChecked) {
                                Intent intent = new Intent(activity, Videoview_Fragment.class);
                                intent.putExtra("bundle", bundle);

                                activity.startActivity(intent);
                            } else {
                                Exoplayer_Fragment exoplayer_fragment = new Exoplayer_Fragment();
//
                                exoplayer_fragment.setArguments(bundle);

                                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_frame_episode, exoplayer_fragment, "Exoplayer_Fragment")
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                        .commit();
                            }
//

//                        Intent intent = new Intent(activity, ExoPlayerActivity.class);
//                        intent.putExtra("bundle",bundle);
//
//                        activity.startActivity(intent);

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

                } catch (Exception e) {
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Admod(AppCompatActivity activity) {
        try {

            final InterstitialAd mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(activity.getString(R.string.ads_interstitial));
            AdRequest adRequestInter = new AdRequest.Builder().build();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();

                }

                @Override
                public void onAdClosed() {

                }

                @Override
                public void onAdFailedToLoad(int i) {

                }
            });
            mInterstitialAd.loadAd(adRequestInter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


