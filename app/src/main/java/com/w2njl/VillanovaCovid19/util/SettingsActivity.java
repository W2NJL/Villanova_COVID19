package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.w2njl.VillanovaCovid19.CovidService;
import com.w2njl.VillanovaCovid19.LoginActivity;
import com.w2njl.VillanovaCovid19.R;

import java.util.Objects;

import io.paperdb.Paper;

import static com.w2njl.VillanovaCovid19.CovidService.SHARED_PREFS_PHONE;
import static com.w2njl.VillanovaCovid19.CovidService.SHARED_PREFS_SMS;
import static com.w2njl.VillanovaCovid19.CovidService.SHARED_TEXT_PHONE;
import static com.w2njl.VillanovaCovid19.CovidService.SHARED_TEXT_SMS;
import static com.w2njl.VillanovaCovid19.CovidService.running;
import static com.w2njl.VillanovaCovid19.CovidService.serviceInterval;
import static com.w2njl.VillanovaCovid19.CovidService.serviceTime;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Slider slider;
    SwitchMaterial switchMaterial;
    long temp;
    long serviceTemp;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_TEXT = "interval";
    SharedPreferences sharedPreferences, sharedSMS, sharedPhone;
    SharedPreferences.Editor editor2, editor3;
    private Intent mIntent;
    Button phoneSubmit;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;
    Boolean alertingSMS = false;
    String tel;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        slider = findViewById(R.id.slider);
        switchMaterial = findViewById(R.id.mmsSwitch);
        textInputLayout = findViewById(R.id.textField);
        textInputEditText = findViewById(R.id.txtPhone);
        phoneSubmit = findViewById(R.id.phonesubmit);

        mIntent = new Intent(this,CovidService .class);


            sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            serviceTemp = sharedPreferences.getLong(SHARED_TEXT, 60000);
          setSlider();

        init();

if(alertingSMS){
        switchMaterial.setChecked(true);
        textInputEditText.setText(tel);}
else {
    switchMaterial.setChecked(false);
    textInputLayout.setVisibility(View.GONE);
    phoneSubmit.setVisibility(View.GONE);
}

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                temp = (long) value;


                changeService();





                if(running){
                    stopService(mIntent);
                    startService(mIntent);
                }

            }
        });

        switchMaterial.setOnCheckedChangeListener(new SwitchMaterial.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // TODO: 11/29/2020 Handle enabling of SMS
                    textInputLayout.setVisibility(View.VISIBLE);
                    phoneSubmit.setVisibility(View.VISIBLE);
                    alertingSMS = true;

                    editor2.putBoolean(SHARED_TEXT_SMS, true);

                }
                else {
                textInputLayout.setVisibility(View.GONE);
                phoneSubmit.setVisibility(View.GONE);
                    alertingSMS = false;

                    editor2.putBoolean(SHARED_TEXT_SMS, false);

                }
                editor2.apply();
            }
        });

        phoneSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tel = textInputEditText.getText().toString().trim();
                if(tel.isEmpty()){
                    textInputEditText.setError("Email is required!");
                    textInputEditText.requestFocus();
                    return;
                }

                if(!Patterns.PHONE.matcher(tel).matches()){
                    textInputEditText.setError("Please provide valid phone number!");
                    textInputEditText.requestFocus();
                    return;
                }
                if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                }

                if((ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(SettingsActivity.this, "Number stored for SMS alerts", Toast.LENGTH_LONG).show();

                editor3.putString(SHARED_TEXT_PHONE, tel);

                editor3.apply();}

            }
        });

    }



//    public void saveData() {
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putLong(SHARED_TEXT, temp);
//
//        editor.apply();
//    }

    public void changeService(){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch ((int) temp ){
            case 0:
                temp = 45000;
                editor.putLong(SHARED_TEXT, temp);

                editor.apply();

                Toast.makeText(SettingsActivity.this, "You have changed the RIS interval to 1 minute", Toast.LENGTH_SHORT).show();
                break;
            case 30:
                temp = 60 * 1000 * temp;
                editor.putLong(SHARED_TEXT, temp);

                editor.apply();
                Toast.makeText(SettingsActivity.this, "You have changed the RIS interval to 30 minutes", Toast.LENGTH_SHORT).show();
                break;
            case 60:
                temp = 60 * 1000 * temp;
                editor.putLong(SHARED_TEXT, temp);

                editor.apply();
                Toast.makeText(SettingsActivity.this, "You have changed the RIS interval to one hour", Toast.LENGTH_SHORT).show();
                break;
            case 90:
                temp = 60 * 1000 * temp;
                editor.putLong(SHARED_TEXT, temp);

                editor.apply();
                Toast.makeText(SettingsActivity.this, "You have changed the RIS interval to 90 minutes", Toast.LENGTH_SHORT).show();
            case 120:
                temp = 60 * 1000 * temp;
                editor.putLong(SHARED_TEXT, temp);

                editor.apply();
                Toast.makeText(SettingsActivity.this, "You have changed the RIS interval to two hours", Toast.LENGTH_SHORT).show();

        }

    }

    public void setSlider(){

        switch ((int) serviceTemp/60/1000){
            case 1:
                slider.setValue(0);
                break;
            case 30:
                slider.setValue(30);
                break;
            case 60:
                slider.setValue(60);
                break;
            case 90:
                slider.setValue(90);
                break;
            case 120:
                slider.setValue(120);
                break;
            default:
                break;}

        }



    private void init() {
        toolbar = findViewById(R.id.toolbar);
        sharedSMS = getSharedPreferences(SHARED_PREFS_SMS, MODE_PRIVATE);
        alertingSMS = sharedSMS.getBoolean(SHARED_TEXT_SMS, false);

        sharedPhone = getSharedPreferences(SHARED_PREFS_PHONE, MODE_PRIVATE);
        tel = sharedPhone.getString(SHARED_TEXT_PHONE, "2164100513");
        editor2 = sharedSMS.edit();
        editor3 = sharedPhone.edit();
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.covid_assessment));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRate:
                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.actionFeedback:
                sendFeedBack();
                break;
            case R.id.actionLogout:
                mIntent = new Intent(this, CovidService.class);
                if(running){
                    stopService(mIntent);
                }
                Paper.init(this);
                Paper.book().destroy();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                break;
            case R.id.actionUser:
                startActivity(new Intent(SettingsActivity.this, UserProfile.class));
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void sendFeedBack() {
        Intent localIntent = new Intent(Intent.ACTION_SEND);
        localIntent.putExtra(Intent.EXTRA_EMAIL, R.string.mail_feedback_email);
        localIntent.putExtra(Intent.EXTRA_CC, "");
        String str;
        try {
            str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            localIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_msg));
            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n" + getResources().getString(R.string.device_os) +
                    Build.VERSION.RELEASE + "\n" + getResources().getString(R.string.app_version) + str + "\n Device Brand: " + Build.BRAND +
                    "\n" + getResources().getString(R.string.device_model) + Build.MODEL + "\n" + getResources().getString(R.string.manufacturer) + Build.MANUFACTURER);
            localIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(localIntent, getResources().getString(R.string.email_client)));
        } catch (Exception ignored) {
        }
    }
}