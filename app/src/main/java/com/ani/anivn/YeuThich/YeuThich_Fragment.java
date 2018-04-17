package com.ani.anivn.YeuThich;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.Database.SqlApp;
import com.ani.anivn.Model.DaoSession;
import com.ani.anivn.Model.YeuThich_Model;
import com.ani.anivn.Model.YeuThich_ModelDao;
import com.ani.anivn.R;
import com.ani.anivn.YeuThich.Adapter.YeuThich_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by sev_user on 04/05/2018.
 */

public class YeuThich_Fragment extends Fragment {
    View view;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView tv_empty_yeuthich;

    DaoSession daoSession;
    YeuThich_ModelDao yeuthich_dao;

    List<YeuThich_Model> listItem;
    YeuThich_Adapter adapter;

    DatabaseReference ref;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_yeuthich, container, false);

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Yêu Thích");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        FindViewById();

        initSQL();

        GetDataSQL();

        return view;
    }

    private void initSQL() {
        daoSession = ((SqlApp) getActivity().getApplication()).getDaoSession();
        yeuthich_dao = daoSession.getYeuThich_ModelDao();

    }

    private void FindViewById() {
        progressBar = view.findViewById(R.id.progressbar_yeuthich);
        recyclerView = view.findViewById(R.id.recyclerview_yeuthich);
        tv_empty_yeuthich = view.findViewById(R.id.tv_empty_yeuthich);
    }

    private void GetDataSQL() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        listItem = new ArrayList<>();
        listItem = yeuthich_dao.queryBuilder().orderDesc(YeuThich_ModelDao.Properties.Idsql).build().list();
        if (listItem.size() > 0) {

            SetupRecyclerview();
            tv_empty_yeuthich.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        } else {
            tv_empty_yeuthich.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

    private void SetupRecyclerview() {
        recyclerView.setHasFixedSize(true);
        adapter = new YeuThich_Adapter(getContext(), listItem, progressBar, tv_empty_yeuthich, yeuthich_dao);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_yeuthich, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_delete_yeuthich:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc chắn muốn xóa tất cả ?");
                builder.setCancelable(false);
                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                for (int i = listItem.size() - 1; i >= 0; i--) {
                                    yeuthich_dao.delete(listItem.get(i));
                                    listItem.remove(i);
                                }

                                adapter.notifyDataSetChanged();

                                tv_empty_yeuthich.setVisibility(View.VISIBLE);

                                Toasty.success(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT, true).show();

                            }
                        });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.action_search_fragment);
//        if (item != null)
//            item.setVisible(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
