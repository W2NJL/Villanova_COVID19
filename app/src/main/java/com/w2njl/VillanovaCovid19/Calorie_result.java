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
import androidx.core.content.ContextCompat;

import com.w2njl.VillanovaCovid19.util.Constant;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.util.Objects;

public class Calorie_result extends AppCompatActivity {
    TextView header1;
    TextView header2;
    TextView header3;
    Animation rotate;
    TextView row11;
    TextView row12;
    TextView row13;
    TextView row21;
    TextView row22;
    TextView row23;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tvCalories;
    TextView tvTdee;
    TextView tvTdeeValue;
    Toolbar toolbar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.GrayTheme);
        setContentView(R.layout.calorie_result);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        ImageView imageView = findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(rotate);
        tvTdeeValue.setText(getIntent().getStringExtra(ConditionalUserProperty.VALUE));
    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.calories));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvTdee = findViewById(R.id.tvTdee);
        tvTdeeValue = findViewById(R.id.tvTdeeValue);
        tvCalories = findViewById(R.id.tvCalories);
        header1 = findViewById(R.id.header1);
        header2 = findViewById(R.id.header2);
        header3 = findViewById(R.id.header3);
        row11 = findViewById(R.id.row11);
        row12 = findViewById(R.id.row12);
        row13 = findViewById(R.id.row13);
        row21 = findViewById(R.id.row21);
        row22 = findViewById(R.id.row22);
        row23 = findViewById(R.id.row23);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);


        findViewById(R.id.layout).setBackground(Constant.getShapeDrawble(true, ContextCompat.getColor(getApplicationContext(), R.color.graycolorPrimary)));

    }

}
