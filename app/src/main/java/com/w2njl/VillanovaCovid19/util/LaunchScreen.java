package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.w2njl.VillanovaCovid19.ConnectionDetector;
import com.w2njl.VillanovaCovid19.LoginActivity;
import com.w2njl.VillanovaCovid19.R;

import io.paperdb.Paper;

public class LaunchScreen extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

// set an exit transition
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_launch_screen);
        init();

        progressBar.setVisibility(View.VISIBLE);

        Paper.init(this);

        String UserEmailKey = Paper.book().read(Prevalent.userEmail);
        String UserPasswordKey = Paper.book().read(Prevalent.userPasswordKey);
        cd = new ConnectionDetector(this);

        if (UserEmailKey != "" && UserPasswordKey != ""){
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)){
                if(cd.isConnectingToInternet()) {
                    allowAccess(UserEmailKey, UserPasswordKey);
                }
                else{
                    startActivity(new Intent(LaunchScreen.this, HomeScreen.class));
                }
            }
            else{
                startActivity(new Intent(LaunchScreen.this, LoginActivity.class));
            }
        }


    }

    private void init() {
        progressBar = findViewById(R.id.loading);
        mAuth = FirebaseAuth.getInstance();
    }

    private void allowAccess(String userEmailKey, String userPasswordKey) {
        mAuth.signInWithEmailAndPassword(userEmailKey, userPasswordKey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {




                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        Intent intent = new Intent(LaunchScreen.this, HomeScreen.class);
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(LaunchScreen.this).toBundle());
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LaunchScreen.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LaunchScreen.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}