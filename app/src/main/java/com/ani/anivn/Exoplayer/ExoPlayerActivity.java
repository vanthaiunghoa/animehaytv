//package com.ani.anivn.Exoplayer;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.crashlytics.android.Crashlytics;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayer;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.PlaybackParameters;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//
//import com.google.android.exoplayer2.upstream.TransferListener;
//import com.google.android.exoplayer2.util.Util;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;
//
//import com.ani.anivn.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import es.dmoral.toasty.Toasty;
//import io.fabric.sdk.android.Fabric;
//import proguard.annotation.Keep;
//import proguard.annotation.KeepClassMemberNames;
//import proguard.annotation.KeepClassMembers;
//
//@Keep
//@KeepClassMembers
//@KeepClassMemberNames
//public class ExoPlayerActivity extends AppCompatActivity {
//    private SimpleExoPlayerView simpleExoPlayerView;
//    private SimpleExoPlayer player;
//
//    private DataSource.Factory mediaDataSourceFactory;
//    private DefaultTrackSelector trackSelector;
//    private boolean shouldAutoPlay;
//    private BandwidthMeter bandwidthMeter;
//    private DefaultExtractorsFactory extractorsFactory;
//    private MediaSource mediaSource;
//    private ProgressBar progressBar_exoplayer_activity;
//    private Intent intent;
//    private Bundle bundle;
//    private boolean ADS_STATE_START = false;
//    private boolean ADS_STATE_END = false;
//    List<String> listURL;
//    int indexUrl = 0;
//    String URL = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exo_player);
//        Fabric.with(this, new Crashlytics());
//
//        //  ExoPlayerActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//        View decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener
//                (new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        // Note that system bars will only be "visible" if none of the
//                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
//                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                            // TODO: The system bars are visible. Make any desired
//                            // adjustments to your UI, such as showing the action bar or
//                            // other navigational controls.
//                            new Handler().postDelayed(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    // This method will be executed once the timer is over
//                                    // Start your app main activity
//
//                                    ExoPlayerActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//                                }
//                            }, 3000);
//                        } else {
//                            // TODO: The system bars are NOT visible. Make any desired
//                            // adjustments to your UI, such as hiding the action bar or
//                            // other navigational controls.
//
////                            new Handler().postDelayed(new Runnable() {
////
////                                @Override
////                                public void run() {
////                                    // This method will be executed once the timer is over
////                                    // Start your app main activity
////
////                                    ExoPlayerActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
////
////                                }
////                            }, 3000);
//                        }
//                    }
//                });
//
//        progressBar_exoplayer_activity = (ProgressBar) findViewById(R.id.progressBar_exoplayer_activity);
//
//        GetIntent();
//
//        shouldAutoPlay = true;
//        bandwidthMeter = new DefaultBandwidthMeter();
//        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoplayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
//
//
//    }
//
//    private void Admod_Start() {
//        final InterstitialAd mInterstitialAd = new InterstitialAd(ExoPlayerActivity.this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_interstitial));
//        AdRequest adRequestInter = new AdRequest.Builder().build();
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                mInterstitialAd.show();
//                ADS_STATE_START = true;
//            }
//
//            @Override
//            public void onAdClosed() {
//                ADS_STATE_START = true;
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//
//            }
//        });
//        mInterstitialAd.loadAd(adRequestInter);
//
//    }
//
//    private void Admod_End() {
//        final InterstitialAd mInterstitialAd = new InterstitialAd(ExoPlayerActivity.this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ads_interstitial));
//        AdRequest adRequestInter = new AdRequest.Builder().build();
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                mInterstitialAd.show();
//                ADS_STATE_END = true;
//            }
//
//            @Override
//            public void onAdClosed() {
//                ADS_STATE_END = true;
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//
//            }
//        });
//        mInterstitialAd.loadAd(adRequestInter);
//
//    }
//
//    private void GetIntent() {
//        intent = getIntent();
//        bundle = intent.getBundleExtra("bundle");
//        if (bundle != null) {
//
//            listURL = new ArrayList<>();
//            listURL = bundle.getStringArrayList("listURL");
//
//            if (listURL != null) {
//                if (listURL.size() > 0) {
//                    URL = listURL.get(indexUrl);
//                    indexUrl += 1;
//                }
//            }
//        }
//    }
//
//    private void initializePlayer() {
//
//        //   ExoPlayerActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//
//        progressBar_exoplayer_activity.setVisibility(View.VISIBLE);
//        progressBar_exoplayer_activity.bringToFront();
//
//        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
//
//
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//
//        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//        simpleExoPlayerView.setKeepScreenOn(true);
//        simpleExoPlayerView.setPlayer(player);
//
//        player.setPlayWhenReady(shouldAutoPlay);
//
//        extractorsFactory = new DefaultExtractorsFactory();
//
//
//        mediaSource = new ExtractorMediaSource(Uri.parse(URL), mediaDataSourceFactory, extractorsFactory, null, null);
//
//        player.prepare(mediaSource);
//        player.addListener(new ExoPlayer.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest) {
//
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                if (playbackState == ExoPlayer.STATE_BUFFERING) {
//                    progressBar_exoplayer_activity.setVisibility(View.VISIBLE);
//                    progressBar_exoplayer_activity.bringToFront();
//                } else {
//                    progressBar_exoplayer_activity.setVisibility(View.GONE);
//                }
//
//                if (playbackState == ExoPlayer.STATE_READY && ADS_STATE_START == false) {
//                    // Admod_Start();
//                }
//                if (playbackState == ExoPlayer.STATE_ENDED && ADS_STATE_END == false) {
//                    Admod_End();
//                }
//
//
//            }
//
//            @Override
//            public void onRepeatModeChanged(int repeatMode) {
//
//
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//
//                if (listURL != null && listURL.size() > 0 && indexUrl < listURL.size() - 1) {
//
//                    URL = listURL.get(indexUrl);
//                    indexUrl += 1;
//
//                    initializePlayer();
//
//
//                } else {
//                    try {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ExoPlayerActivity.this);
//                        builder.setMessage(R.string.exoplayer_error_load_video);
//                        builder.setCancelable(false);
//                        builder.setPositiveButton(
//                                "OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//
//                                    }
//                                });
//
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    } catch (Exception e) {
//                        Toasty.error(ExoPlayerActivity.this, getString(R.string.exoplayer_error_load_video), Toast.LENGTH_SHORT, true).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onPositionDiscontinuity() {
//
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        //Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            getWindow().setFlags(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION, View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//            //  ExoPlayerActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
//
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    private void releasePlayer() {
//        if (player != null) {
//            shouldAutoPlay = player.getPlayWhenReady();
//            player.release();
//            player = null;
//            trackSelector = null;
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (Util.SDK_INT > 23) {
//            initializePlayer();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if ((Util.SDK_INT <= 23 || player == null)) {
//            initializePlayer();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (Util.SDK_INT > 23) {
//            releasePlayer();
//        }
//    }
//}
