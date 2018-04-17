package com.ani.anivn.Login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ani.anivn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

/**
 * Created by Admin on 4/16/2018.
 */

public class Login_Fragment extends Fragment {
    View view;
    EditText input_email, input_password;
    Button btn_login;
    TextView link_signup;
    ProgressBar progressBar;

    AppCompatActivity activity;

    FirebaseAuth auth;
    DatabaseReference ref;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        activity = (AppCompatActivity) view.getContext();
        activity.getSupportActionBar().setTitle("Đăng Nhập Tài Khoản");

        FindViewById();

        return view;
    }
 
    private void FindViewById() {
        input_email = view.findViewById(R.id.input_email);
        input_password = view.findViewById(R.id.input_password);
        btn_login = view.findViewById(R.id.btn_login);
        link_signup = view.findViewById(R.id.link_signup);
        progressBar = view.findViewById(R.id.progressBar_login);
        
         input_email.setText("");
         input_password.setText("");

         link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 input_email.setText("");
                 input_password.setText("");
                                                
                  FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                  fragmentTransaction.replace(R.id.content_frame, new Signup_Fragment(), "Signup_Fragment")
                                      .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                       addToBackStack("Signup_Fragment")
                                        .commit();
            }
        });
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toasty.error(getContext(), "Hãy nhập email !", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toasty.error(getContext(), "Hãy nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toasty.error(getContext(), "Mật khẩu tối thiểu 6 kí tự !", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                
                                if (task.isSuccessful()) {

                                    String idfirebase=auth.getCurrentUser().getUid();
                                    
                                    Login_Model login_model = new Login_Model();
                                     login_model.setEmail(email);
                                     login_model.setIdfirebase(idfirebase);
                                     login_model.setPassword(password);
                                    
                                    ref.child("users").child(idfirebase).setValue(login_model);
                                     
                                    progressBar.setVisibility(View.GONE);
                                    
                                    Toasty.success(getContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT, true).show();

                                } else {
                                    
                                    progressBar.setVisibility(View.GONE);

                                    Toasty.success(getContext(), "Đăng nhập thất bại !", Toast.LENGTH_SHORT, true).show();

                                }
                            }
                        });

            }

        });

    }
}
