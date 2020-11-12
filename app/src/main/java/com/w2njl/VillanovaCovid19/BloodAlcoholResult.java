package com.w2njl.VillanovaCovid19;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.util.Objects;

public class BloodAlcoholResult extends AppCompatActivity {
    Animation rotate;
    TextView tvBloodAlcohol;
    TextView tvBloodAlcoholVal;
    TextView tvPercent;
    Toolbar toolbar;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.GrayTheme);
        setContentView(R.layout.bloodalcoholresult);
        init();

        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());

        ImageView imageView = findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        tvBloodAlcoholVal.setText(getIntent().getStringExtra(ConditionalUserProperty.VALUE));
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bloodalcohol));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvBloodAlcohol = findViewById(R.id.tvBloodAlcohol);
        tvBloodAlcoholVal = findViewById(R.id.tvBloodAlcoholVal);
        tvPercent = findViewById(R.id.tvPercent);
    }

}
