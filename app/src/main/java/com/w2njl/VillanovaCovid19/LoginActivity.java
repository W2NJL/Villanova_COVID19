package com.w2njl.VillanovaCovid19;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.w2njl.VillanovaCovid19.util.ForgotPassword;
import com.w2njl.VillanovaCovid19.util.HomeScreen;
import com.w2njl.VillanovaCovid19.util.Prevalent;
import com.w2njl.VillanovaCovid19.util.User;

import java.util.Objects;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    Toolbar toolbar;
    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CheckBox chkBoxRememberMe;
    private static final String TAG = "LoginActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.activity_login);
//        ((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());

        init();

        register.setOnClickListener(this);
        signIn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        Paper.init(this);

        String UserEmailKey = Paper.book().read(Prevalent.userEmail);
        String UserPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (UserEmailKey != "" && UserPasswordKey != ""){
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)){
                allowAccess(UserEmailKey, UserPasswordKey); 
            }
        }
    }

    private void allowAccess(String userEmailKey, String userPasswordKey) {

        mAuth.signInWithEmailAndPassword(userEmailKey, userPasswordKey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {




                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        startActivity(new Intent(LoginActivity.this, HomeScreen.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed to login!  Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void init() {

        register = findViewById(R.id.register);
        forgotPassword = findViewById(R.id.forgot);
        mAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.loginBtn2);
        toolbar = findViewById(R.id.toolbar);
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.loading);
        chkBoxRememberMe = findViewById(R.id.checkBox);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.covid_assessment));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.loginBtn2:
                Log.d(TAG, "onClick: You clicked it!");
                userLogin(); 
                break;
            case R.id.forgot:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(chkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.userEmail, email);
            Paper.book().write(Prevalent.userPasswordKey, password);

        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Password length must be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {




                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        startActivity(new Intent(LoginActivity.this, HomeScreen.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed to login!  Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}