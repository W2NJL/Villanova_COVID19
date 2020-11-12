package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.w2njl.VillanovaCovid19.CovidService;
import com.w2njl.VillanovaCovid19.R;

import java.util.Objects;

import static com.w2njl.VillanovaCovid19.CovidService.running;
import static com.w2njl.VillanovaCovid19.CovidService.serviceInterval;
import static com.w2njl.VillanovaCovid19.CovidService.serviceTime;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Slider slider;
    long temp;
    long serviceTemp;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_TEXT = "interval";
    SharedPreferences sharedPreferences;
    private Intent mIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_settings);

        slider = findViewById(R.id.slider);

        mIntent = new Intent(this,CovidService .class);


            sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            serviceTemp = sharedPreferences.getLong(SHARED_TEXT, 60000);
          setSlider();


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

        init();
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
                editor.putLong(SHARED_TEXT, 60000);

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
}