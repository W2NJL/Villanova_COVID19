package com.w2njl.VillanovaCovid19;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.io.PrintStream;
import java.util.Objects;

public class Result extends AppCompatActivity {
    Animation animBlink;
    Button bmi;
    TextView case1;
    TextView case2;
    TextView case3;
    TextView case4;
    TextView case5;
    TextView case6;
    TextView case7;
    TextView casy1;
    TextView casy2;
    TextView casy3;
    TextView casy4;
    TextView casy5;
    TextView casy6;
    TextView casy7;
    ImageView image;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    ImageView image7;
    double myNum = 0.0d;
    int num = 0;

    ProgressBar progressBar;
    String strOne;
    TextView sub1;
    TextView sub2;
    TextView sub3;
    TextView sub4;
    TextView sub5;
    TextView sub6;
    TextView tvCat;
    TextView tvOne;
    TextView tvTwo;
    Toolbar toolbar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.result);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        init();
        strOne = getIntent().getStringExtra(ConditionalUserProperty.VALUE);
        tvTwo.setText(this.strOne);
        try {
            myNum = Double.valueOf(this.tvTwo.getText().toString());
            num = (int) this.myNum;
            if (num < 16) {
                num = 1;
                tvCat.setText(getResources().getString(R.string.exunder));
                image1.setVisibility(View.VISIBLE);
                image1.startAnimation(this.animBlink);
            } else if (this.num > 40) {
                num = 100;
                tvCat.setText(getResources().getString(R.string.morbid));
                image7.setVisibility(View.VISIBLE);
                image7.startAnimation(this.animBlink);
            } else {
                if (myNum >= 16.0d && this.myNum <= 18.5d) {
                    tvCat.setText(getResources().getString(R.string.underweight));
                    image2.setVisibility(View.VISIBLE);
                    image2.startAnimation(this.animBlink);
                } else if (this.myNum > 18.5d && this.myNum <= 25.0d) {
                    tvCat.setText(getResources().getString(R.string.normalweight));
                    image3.setVisibility(View.VISIBLE);
                    image3.startAnimation(this.animBlink);
                } else if (this.myNum > 25.0d && this.myNum <= 30.0d) {
                    tvCat.setText(getResources().getString(R.string.overweight));
                    image4.setVisibility(View.VISIBLE);
                    image4.startAnimation(this.animBlink);
                } else if (this.myNum > 30.0d && this.myNum <= 35.0d) {
                    tvCat.setText(getResources().getString(R.string.obeseone));
                    image5.setVisibility(View.VISIBLE);
                    image5.startAnimation(this.animBlink);
                } else if (this.myNum > 35.0d && this.myNum <= 40.0d) {
                    tvCat.setText(getResources().getString(R.string.obesetwo));
                    image6.setVisibility(View.VISIBLE);
                    image6.startAnimation(this.animBlink);
                } else if (this.myNum < 16.0d) {
                    tvCat.setText(getResources().getString(R.string.exunder));
                    image1.setVisibility(View.VISIBLE);
                    image1.startAnimation(this.animBlink);
                } else if (this.myNum > 40.0d) {
                    tvCat.setText(getResources().getString(R.string.morbid));
                    image7.setVisibility(View.VISIBLE);
                    image7.startAnimation(this.animBlink);
                }
                num -= 15;
                num *= 4;
            }
        } catch (NumberFormatException e) {
            PrintStream printStream = System.out;
            String sb = "Could not parse " +
                    e;
            printStream.println(sb);
            num = 100;
            tvCat.setText(getResources().getString(R.string.morbid));
            image7.setVisibility(View.VISIBLE);
            image7.startAnimation(this.animBlink);
        }
        progressBar.setProgress(this.num);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bmi_title));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar = findViewById(R.id.progressBar2);
        image = findViewById(R.id.image);
        tvOne = findViewById(R.id.tvOne);
        tvTwo = findViewById(R.id.tvTwo);
        bmi = findViewById(R.id.bmi);
        bmi.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Result.this.startActivity(new Intent(Result.this, UnderstandBMI.class));
            }
        });
        tvCat = findViewById(R.id.cat);
        sub1 = findViewById(R.id.sub1);
        sub2 = findViewById(R.id.sub2);
        sub3 = findViewById(R.id.sub3);
        sub4 = findViewById(R.id.sub4);
        sub5 = findViewById(R.id.sub5);
        sub6 = findViewById(R.id.sub6);
        case1 = findViewById(R.id.case1);
        case2 = findViewById(R.id.case2);
        case3 = findViewById(R.id.case3);
        case4 = findViewById(R.id.case4);
        case5 = findViewById(R.id.case5);
        case6 = findViewById(R.id.case6);
        case7 = findViewById(R.id.case7);
        casy1 = findViewById(R.id.casy1);
        casy2 = findViewById(R.id.casy2);
        casy3 = findViewById(R.id.casy3);
        casy4 = findViewById(R.id.casy4);
        casy5 = findViewById(R.id.casy5);
        casy6 = findViewById(R.id.casy6);
        casy7 = findViewById(R.id.casy7);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
    }

}
