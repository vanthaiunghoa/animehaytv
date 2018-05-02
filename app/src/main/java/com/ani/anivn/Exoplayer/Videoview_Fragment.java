package com.ani.anivn.Exoplayer;

import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.ani.anivn.R;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMemberNames
@KeepClassMembers
public class Videoview_Fragment extends AppCompatActivity {
    ProgressBar progressBar;
    VideoView videoView;
    MediaController mediaController;

    List<String> listURL;
    int INDEX_URL = 0;
    Intent intent;
    Bundle bundle;
    private String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_videoview);
        Fabric.with(this, new Crashlytics());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

        FindViewById();

        GetBundle();

        SetupVideoView();

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoView.getLayoutParams();
//        params.width = metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        videoView.setLayoutParams(params);



    }

    private void GetBundle() {
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        listURL = new ArrayList<>();
        if (bundle != null) {
            if (bundle.containsKey("listURL")) {
                listURL = bundle.getStringArrayList("listURL");
                if (listURL.size() > 0) {
                    URL = listURL.get(INDEX_URL);
                }
            }
        }

    }

    private void FindViewById() {
        progressBar = findViewById(R.id.progressbar_videoview);
        videoView = findViewById(R.id.video_view);
    }

    private void SetupVideoView() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(URL));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressBar.setVisibility(View.GONE);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                INDEX_URL = INDEX_URL + 1;
                if (INDEX_URL < listURL.size()) {

                    URL = listURL.get(INDEX_URL);

                    if (mediaController == null)
                        mediaController = new MediaController(Videoview_Fragment.this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(Uri.parse(URL));
                    videoView.start();
                } else {
                    Toast.makeText(Videoview_Fragment.this, "Loi ", Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
    }
}
