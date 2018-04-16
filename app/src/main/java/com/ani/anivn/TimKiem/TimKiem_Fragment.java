package com.ani.anivn.TimKiem;

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

import com.ani.anivn.Config.Get_TimKiem;
import com.ani.anivn.Model.TimKiem_Model;
import com.ani.anivn.R;
import com.ani.anivn.TimKiem.Adapter.TimKiem_Adapter;

import java.util.List;

/**
 * Created by Admin on 4/7/2018.
 */

public class TimKiem_Fragment extends Fragment implements android.widget.SearchView.OnQueryTextListener {
    View view;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView tv_empty;

    TimKiem_Adapter adapter;
    List<TimKiem_Model> listItem;

    Get_TimKiem get_timKiem;
    AppCompatActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.fragment_timkiem, container, false);

         activity=(AppCompatActivity)view.getContext();
        activity.getSupportActionBar().setTitle("Tìm Kiếm");

        FindViewById();

        return view;
    }

    private void FindViewById() {
        progressBar = view.findViewById(R.id.progressbar_timkiem);
        recyclerView = view.findViewById(R.id.recyclerview_timkiem);
        tv_empty = view.findViewById(R.id.tv_empty_timkiem);

        get_timKiem = new Get_TimKiem(getContext());
    }

    private void GetDataServer(final String keyword) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        get_timKiem.TimKiem(keyword, new Get_TimKiem.TimKiem_Callback() {
            @Override
            public void onSuccess(List<TimKiem_Model> list_data) {


                listItem = list_data;
                SetupRecyclerview();

                if (list_data.size() > 0) {
                    tv_empty.setVisibility(View.GONE);
                } else {
                    tv_empty.setText("Không tìm thấy phim với từ khóa : " + keyword + " .");
                    tv_empty.setVisibility(View.VISIBLE);
                }

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
        adapter = new TimKiem_Adapter(getContext(), listItem, progressBar);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s != null && s.length() > 0) {
            GetDataServer(s);
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Nhập tên phim cần tìm ");

        super.onCreateOptionsMenu(menu, inflater);

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
