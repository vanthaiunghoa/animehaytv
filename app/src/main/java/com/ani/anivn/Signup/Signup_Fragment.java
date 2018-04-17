package com.ani.anivn.Signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.Login.Login_Fragment;
import com.ani.anivn.Model.Login_Model;
import com.ani.anivn.Model.YeuThich_Model;
import com.ani.anivn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Admin on 4/16/2018.
 */

public class Signup_Fragment extends Fragment {
    View view;

    EditText input_email, input_password;
    Button btn_signup;
    TextView link_login;
    ProgressBar progressBar;

    DatabaseReference ref;
    FirebaseAuth auth;

    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Đăng Kí Tài Khoản");

        FindViewById();

        return view;
    }

    private void FindViewById() {
        input_email = view.findViewById(R.id.input_email);
        input_password = view.findViewById(R.id.input_password);
        btn_signup = view.findViewById(R.id.btn_signup);
        link_login = view.findViewById(R.id.link_login);
        progressBar = view.findViewById(R.id.progressBar_signup);

         input_email.setText("");
         input_password.setText("");
        
          link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 input_email.setText("");
                 input_password.setText("");
                                                
                  FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                  fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                                      .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                       .addToBackStack("Login_Fragment")
                                        .commit();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = input_email.getText().toString().trim();
                final String password = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toasty.error(getContext(), "Hãy nhập địa chỉ email !", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toasty.error(getContext(), "Mật khẩu không được rỗng!", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if (password.length() < 6) {
                    Toasty.error(getContext(), "Mật khẩu tối thiểu 6 kí tự !", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if(!isValidEmailAddress(email)){
                    Toasty.error(getContext(), "Hãy nhập email chính xác !", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();

                auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(activity, new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        if (task.getResult().getSignInMethods().isEmpty()) {

                            auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            progressBar.setVisibility(View.GONE);

                                            if (task.isSuccessful()) {

                                                Toasty.success(getContext(), "Đăng kí thành công !", Toast.LENGTH_SHORT, true).show();

                                                input_email.setText("");
                                                input_password.setText("");
                                                
                                                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.content_frame, new Login_Fragment(), "Login_Fragment")
                                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                        .addToBackStack("Login_Fragment")
                                                        .commit();

                                            } else {

                                                Toasty.success(getContext(), "Đăng kí thất bại !", Toast.LENGTH_SHORT, true).show();

                                            }
                                        }
                                    });
                        } else {
                            Toasty.success(getContext(), "Email đã sử dụng để đăng kí tài khoản khác !", Toast.LENGTH_SHORT, true).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
