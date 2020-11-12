package com.w2njl.VillanovaCovid19;

import android.content.Intent;

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

public class Heartrateresult extends AppCompatActivity {
    Animation rotate;
    TextView tvBeats;
    TextView tvBeats2;
    TextView tvMhr;
    TextView tvMhrValue;
    TextView tvThr;
    TextView tvThrValue;
    Toolbar toolbar;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.YellowTheme);

        setContentView(R.layout.heartrate_result);
        init();
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        ImageView imageView = findViewById(R.id.ivRotate);
        this.rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        Intent intent = getIntent();
        this.tvMhrValue.setText(intent.getStringExtra(ConditionalUserProperty.VALUE));
        String string = intent.getStringExtra("value1");
        String string2 = intent.getStringExtra("value2");
        TextView textView = this.tvThrValue;
        String sb = string +
                " - " +
                string2;
        textView.setText(sb);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.targethrrate));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.tvMhr = findViewById(R.id.tvMhr);
        this.tvMhrValue = findViewById(R.id.tvMhrValue);
        this.tvThr = findViewById(R.id.tvThr);
        this.tvThrValue = findViewById(R.id.tvThrValue);
        this.tvBeats = findViewById(R.id.tvBeats);
        this.tvBeats2 = findViewById(R.id.tvBeats2);
    }
}
