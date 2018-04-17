package com.ani.anivn;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ani.anivn.Anime.Anime_Fragment;
import com.ani.anivn.BangXepHangNgay.BangXepHangNgay_Fragment;
import com.ani.anivn.CNAnimation.CNAnimation_Fragment;
import com.ani.anivn.Episode.Episode_Fragment;
import com.ani.anivn.Exoplayer.Exoplayer_Fragment;
import com.ani.anivn.HoatHinh.HoatHinh_Fragment;
import com.ani.anivn.Login.Login_Fragment;
import com.ani.anivn.NamPhatHanh.NamPhatHanh_Fragment;
import com.ani.anivn.PhimMoiCapNhat.PhimMoiCapNhat_Fragment;
import com.ani.anivn.Signup.Signup_Fragment;
import com.ani.anivn.TaiKhoan.TaiKhoan_Fragment;
import com.ani.anivn.TimKiem.TimKiem_Fragment;
import com.ani.anivn.YeuThich.YeuThich_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    boolean twice = false;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    DatabaseReference ref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // let the fragment manager know that MainActivity will be listening to events
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        /*
         AppBar setup
         */
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    onBackPressed();
            }
        });

        Change_BackGround_Header_Drawer();

        AddDefaultFragment();
    }

    private void AddDefaultFragment() {

        getSupportActionBar().setTitle("Phim Mới Cập Nhật");

        PhimMoiCapNhat_Fragment phimMoiCapNhat_fragment = new PhimMoiCapNhat_Fragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, phimMoiCapNhat_fragment, "PhimMoiCapNhat_Fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void Change_BackGround_Header_Drawer() {
        View header = navigationView.getHeaderView(0);
        LinearLayout sideNavLayout = (LinearLayout) header.findViewById(R.id.sideNavLayout);
        sideNavLayout.setBackgroundResource(R.drawable.bg_drawer);

        CircularImageView imageView_nav_bar = header.findViewById(R.id.imageView_nav_bar);
        Picasso.with(MainActivity.this).load(R.drawable.img_drawer).fit().centerCrop().placeholder(R.drawable.default_fail_img).into(imageView_nav_bar);

    }

    @Override
    public void onBackPressed() {

        Episode_Fragment episodeFragment = (Episode_Fragment) getSupportFragmentManager().findFragmentByTag("Episode_Fragment");
        Exoplayer_Fragment exoplayerFragment = (Exoplayer_Fragment) getSupportFragmentManager().findFragmentByTag("Exoplayer_Fragment");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int totalFragmentsInBackstack = getSupportFragmentManager().getBackStackEntryCount();
            if (totalFragmentsInBackstack == 0) {
                if (twice == true) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
                twice = true;

                Toasty.warning(getApplicationContext(), "Press back again to exit !", Toast.LENGTH_SHORT, true).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        twice = false;
                    }
                }, 3000);
            } else if (episodeFragment != null && episodeFragment.isVisible()) {

                if (exoplayerFragment != null && exoplayerFragment.isVisible()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(exoplayerFragment)
                            .commit();
                }
                super.onBackPressed();

            } else {

                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search_fragment) {

            TimKiem_Fragment timKiem_fragment = new TimKiem_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, timKiem_fragment, "TimKiem_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("TimKiem_Fragment")
                    .commit();

            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_phimmoicapnhat) {

            PhimMoiCapNhat_Fragment phimMoiCapNhat_fragment = new PhimMoiCapNhat_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, phimMoiCapNhat_fragment, "PhimMoiCapNhat_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (id == R.id.nav_bxhn) {

            BangXepHangNgay_Fragment bangXepHangNgay_fragment = new BangXepHangNgay_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, bangXepHangNgay_fragment, "BangXepHangNgay_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else if (id == R.id.nav_yeuthich) {

            YeuThich_Fragment yeuThich_fragment = new YeuThich_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, yeuThich_fragment, "YeuThich_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();


        } else if (id == R.id.nav_cnanimation) {

            CNAnimation_Fragment cnAnimation_fragment = new CNAnimation_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, cnAnimation_fragment, "CNAnimation_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else if (id == R.id.nav_anime) {

            Anime_Fragment anime_fragment = new Anime_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, anime_fragment, "Anime_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else if (id == R.id.nav_hoathinh) {

            HoatHinh_Fragment hoatHinh_fragment = new HoatHinh_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, hoatHinh_fragment, "HoatHinh_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (id == R.id.nav_namphathanh) {

            NamPhatHanh_Fragment namPhatHanh_fragment = new NamPhatHanh_Fragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, namPhatHanh_fragment, "NamPhatHanh_Fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (id == R.id.nav_moreapp) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Phim247Studio")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Phim247Studio")));
            }

        } else if (id == R.id.nav_rateapp) {
            RateApp();
        } else if (id == R.id.nav_lienhe) {

            String YourPageURL123 = "https://www.facebook.com/n/?Animevn-316529195504845";
            Intent browserIntent123 = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL123));

            startActivity(browserIntent123);

        } else if (id == R.id.nav_taikhoan) {
            if (auth.getCurrentUser() != null) {
                TaiKhoan_Fragment taiKhoan_fragment = new TaiKhoan_Fragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, taiKhoan_fragment, "TaiKhoan_Fragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                Login_Fragment login_fragment = new Login_Fragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, login_fragment, "Login_Fragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void RateApp() {

        String title = getString(R.string.rate_title);
        ForegroundColorSpan foregroundColorSpan_title = new ForegroundColorSpan(getResources().getColor(R.color.green_2));
        SpannableStringBuilder ssBuilder_title = new SpannableStringBuilder(title);
        ssBuilder_title.setSpan(foregroundColorSpan_title, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String message = getString(R.string.rate_message);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.green_2));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(message);
        ssBuilder.setSpan(foregroundColorSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(ssBuilder_title)
                .setMessage(ssBuilder)
                .setCancelable(false)
                .setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ////////////////////////////////
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }


                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();

    }

    @Override
    public void onBackStackChanged() {
        int totalFragmentsInBackstack = getSupportFragmentManager().getBackStackEntryCount();

        // if there are not more entries on the back stack it means we are in the root. Drawer should be visible
        if (totalFragmentsInBackstack == 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            // else show the back arrow function. NOTICE: order of these methods is IMPORTANT
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }
}
