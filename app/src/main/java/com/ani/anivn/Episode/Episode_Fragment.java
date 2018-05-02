package com.ani.anivn.Episode;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ani.anivn.Config.Constant;
import com.ani.anivn.Config.Get_Episode;
import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Episode.Adapter.Episode_Adapter;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.Episode_Model;
import com.ani.anivn.Model.Luu_Checkbox_Exoplayer_Videoview_Model;
import com.ani.anivn.Model.Luu_Checkbox_Exoplayer_Videoview_ModelDao;
import com.ani.anivn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 04/06/2018.
 */

public class Episode_Fragment extends Fragment {
    View view;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView tv_chontap_episode;
    CheckBox checkBox;

    Episode_Adapter adapter;
    List<Episode_Model> listItem;

    Get_Episode get_episode;

    Bundle bundle;
    String URL = "";
    boolean isChecked = false;

    DaoSession daoSession;
    Luu_Checkbox_Exoplayer_Videoview_ModelDao luu_videoview_exo_dao;
    Luu_Checkbox_Exoplayer_Videoview_Model luu_videoview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episode, container, false);

        FindViewById();

        initSQL();

        GetBundle();

        GetDataServer_Episode();

        return view;
    }

    private void initSQL() {

        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        luu_videoview_exo_dao = daoSession.getLuu_Checkbox_Exoplayer_Videoview_ModelDao();

        luu_videoview = luu_videoview_exo_dao.queryBuilder().where(Luu_Checkbox_Exoplayer_Videoview_ModelDao.Properties.Tag.eq(Constant.TAG_EXOPLAYER_VIDEOVIEW)).build().unique();
        if (luu_videoview != null) {
            isChecked = luu_videoview.getIsChecked();

        } else {
            luu_videoview = new Luu_Checkbox_Exoplayer_Videoview_Model();
            luu_videoview.setTag(Constant.TAG_EXOPLAYER_VIDEOVIEW);
            luu_videoview.setIsChecked(false);
            luu_videoview_exo_dao.save(luu_videoview);

            isChecked = false;

        }
        checkBox.setChecked(isChecked);
        checkBox.setVisibility(View.VISIBLE);
    }

    private void FindViewById() {
        progressBar = view.findViewById(R.id.progressbar_episode);
        recyclerView = view.findViewById(R.id.recyclerview_episode);
        tv_chontap_episode = view.findViewById(R.id.tv_chontap_episode);
        checkBox = view.findViewById(R.id.checkbox_episode);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isChecked = b;

                Log.d("TESTCHECKBOX",isChecked + "");

                luu_videoview = luu_videoview_exo_dao.queryBuilder().where(Luu_Checkbox_Exoplayer_Videoview_ModelDao.Properties.Tag.eq(Constant.TAG_EXOPLAYER_VIDEOVIEW)).build().unique();
                if (luu_videoview != null) {
                    luu_videoview.setIsChecked(isChecked);
                    luu_videoview_exo_dao.update(luu_videoview);

                } else {
                    luu_videoview = new Luu_Checkbox_Exoplayer_Videoview_Model();
                    luu_videoview.setTag(Constant.TAG_EXOPLAYER_VIDEOVIEW);
                    luu_videoview.setIsChecked(isChecked);
                    luu_videoview_exo_dao.save(luu_videoview);
                }

            }
        });
        get_episode = new Get_Episode(getContext());
    }

    private void GetBundle() {

        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("URL"))
                URL = bundle.getString("URL");
        }
    }

    private void GetDataServer_Episode() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        get_episode.Episode(URL, new Get_Episode.Episode_Callback() {
            @Override
            public void onSuccess(List<Episode_Model> list_data) {

                listItem = new ArrayList<Episode_Model>();
                listItem = list_data;
                SetupRecyclerview();

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
        adapter = new Episode_Adapter(getContext(), listItem, progressBar, tv_chontap_episode,luu_videoview_exo_dao);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nothing, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search_fragment);
        if (item != null)
            item.setVisible(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
}
