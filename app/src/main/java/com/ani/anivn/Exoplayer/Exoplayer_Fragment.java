package com.ani.anivn.Exoplayer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by sev_user on 02/22/2018.
 */

public class Exoplayer_Fragment extends Fragment {
    View view;
    //private Anime_SourceVideo_Model sourcevideo;
    List<String> listURL;
    int INDEX_URL = 0;
    Bundle bundle;
    private String URL = "";
    private boolean ADS_STATE_START = true;
    private boolean ADS_STATE_END = true;
    ProgressBar progressbar_exoplayer;
    // Anime_Lichsu_Model anime_lichsu_model;

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_exoplayer, container, false);
        progressbar_exoplayer = (ProgressBar) view.findViewById(R.id.progressbar_exoplayer);

        GetBundle();

        //  GetDataEvenBus();

        try {
            if (savedInstanceState != null) {
                mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
                mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
                mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
            }
        }catch (Exception e){
            Toasty.error(getContext(),"SavedInstanceState "+ e.getMessage(), Toast.LENGTH_SHORT,true).show();
        }


        return view;
    }

    private void GetBundle() {
        try {
            bundle = getArguments();
            listURL = new ArrayList<>();
            if (bundle != null) {
                if (bundle.containsKey("listURL")) {
                    listURL = bundle.getStringArrayList("listURL");
                    if (listURL.size() > 0) {
                        URL = listURL.get(INDEX_URL);
                    }
                }
            }
        }catch (Exception e){
            Toasty.error(getContext(), "Bundle " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        try {
            outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
            outState.putLong(STATE_RESUME_POSITION, mResumePosition);
            outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);


            super.onSaveInstanceState(outState);
        }catch (Exception e){
            Toasty.error(getContext(), "onSaveInstanceState " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) view.findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_expand));
    }

    private void initFullscreenButton() {

        try {
            PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
            mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
            mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
            mFullScreenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mExoPlayerFullscreen)
                        openFullscreenDialog();
                    else
                        closeFullscreenDialog();
                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), "Error fullscreen " + e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initExoPlayer() {
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
            mExoPlayerView.setPlayer(player);

            boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

            if (haveResumePosition) {
                mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
            }

            mExoPlayerView.getPlayer().prepare(mVideoSource);
            mExoPlayerView.getPlayer().setPlayWhenReady(true);
            mExoPlayerView.getPlayer().addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING) {
                        progressbar_exoplayer.setVisibility(View.VISIBLE);
                        progressbar_exoplayer.bringToFront();
                    } else {
                        progressbar_exoplayer.setVisibility(View.GONE);
                    }

                    if (playbackState == ExoPlayer.STATE_READY && ADS_STATE_START == true) {
                        // Admod();

                    }
                    if (playbackState == ExoPlayer.STATE_ENDED && ADS_STATE_END == true) {
                        //Admod1();
                    }

                    if (playbackState == ExoPlayer.STATE_READY) {
                        //AddDataDaXem();
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    INDEX_URL = INDEX_URL + 1;
                    if (INDEX_URL < listURL.size() && listURL.size() > 0) {
                        URL = listURL.get(INDEX_URL);

                        if (mExoPlayerView == null) {

                            mExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer);
                            initFullscreenDialog();
                            initFullscreenButton();

                            String userAgent = Util.getUserAgent(getContext(), getActivity().getApplicationInfo().packageName);
                            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
                            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), null, httpDataSourceFactory);
                            Uri daUri = Uri.parse(URL);
                            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                            mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory, null, null);
                        }

                        initExoPlayer();

                        if (mExoPlayerFullscreen) {
                            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
                            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_skrink));
                            mFullScreenDialog.show();
                        }

                    } else {
                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage(R.string.exoplayer_error_load_video);
                            builder.setCancelable(false);
                            builder.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.show();
                        } catch (Exception e) {
                            Toasty.error(getContext(), getString(R.string.exoplayer_error_load_video), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }

                @Override
                public void onPositionDiscontinuity() {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }
            });
        } catch (Exception e) {
            Toasty.error(getContext(), "Error initplayer " + e.getMessage(), Toast.LENGTH_SHORT ,true).show();
        }
    }

    private void AddDataDaXem() {
//        anime_lichsu_model = new Anime_Lichsu_Model();
//        anime_lichsu_model = EventBus.getDefault().getStickyEvent(Anime_Lichsu_Model.class);
//        Anime_Lichsu_ModelDao lichsuDao = ((SqlApp) getActivity().getApplication()).getDaoSession().getAnime_Lichsu_ModelDao();
//        if(anime_lichsu_model != null)
//            lichsuDao.save(anime_lichsu_model);
    }

    @Override
    public void onResume() {

        super.onResume();

        try {
            if (mExoPlayerView == null) {

                mExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer);
                initFullscreenDialog();
                initFullscreenButton();

                String userAgent = Util.getUserAgent(getContext(), getActivity().getApplicationInfo().packageName);
                DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), null, httpDataSourceFactory);
                Uri daUri = Uri.parse(URL);
                DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                mVideoSource = new ExtractorMediaSource(daUri, dataSourceFactory, extractorsFactory, null, null);
            }

            initExoPlayer();

            if (mExoPlayerFullscreen) {
                ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
                mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_skrink));
                mFullScreenDialog.show();
            }

           // Toast.makeText(getContext(), "" + URL, Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toasty.error(getContext(), "Resume " + e.getMessage(), Toast.LENGTH_SHORT,true).show();
        }

    }


    @Override
    public void onPause() {

        super.onPause();
        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();

        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }

    private void Admod() {
        try {
            final InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_interstitial));
            AdRequest adRequestInter = new AdRequest.Builder().build();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                    ADS_STATE_START = false;
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

    private void Admod1() {
        try {
            final InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_interstitial));
            AdRequest adRequestInter = new AdRequest.Builder().build();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                    ADS_STATE_END = false;
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

//    public void GetDataEvenBus() {
//        sourcevideo = EventBus.getDefault().getStickyEvent(Anime_SourceVideo_Model.class);
//        URL = sourcevideo.getFile();
//    }


}
