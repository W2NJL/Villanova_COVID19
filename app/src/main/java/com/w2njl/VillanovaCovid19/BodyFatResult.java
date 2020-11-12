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


public class BodyFatResult extends AppCompatActivity {
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
    TextView row31;
    TextView row32;
    TextView row33;
    TextView row41;
    TextView row42;
    TextView row43;
    TextView row51;
    TextView row52;
    TextView row53;
    TextView tvAssessment;
    TextView tvAssessmentVal;
    TextView tvBodyFat;
    TextView tvBodyFatVal;
    TextView tvPercent;
    Toolbar toolbar;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.bodyfatresult);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        ImageView imageView = findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        Intent intent = getIntent();
        String string = intent.getStringExtra(ConditionalUserProperty.VALUE);
        String string2 = intent.getStringExtra("value2");
        tvBodyFatVal.setText(string);
        tvAssessmentVal.setText(string2);
    }

    private void init() {


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bodyfat));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvBodyFat = findViewById(R.id.tvBodyFat);
        tvBodyFatVal = findViewById(R.id.tvBodyFatVal);
        tvPercent = findViewById(R.id.tvPercent);
        tvAssessment = findViewById(R.id.tvAssessment);
        tvAssessmentVal = findViewById(R.id.tvAssessmentVal);
        header1 = findViewById(R.id.header1);
        header2 = findViewById(R.id.header2);
        header3 = findViewById(R.id.header3);
        row11 = findViewById(R.id.row11);
        row12 = findViewById(R.id.row12);
        row13 = findViewById(R.id.row13);
        row21 = findViewById(R.id.row21);
        row22 = findViewById(R.id.row22);
        row23 = findViewById(R.id.row23);
        row31 = findViewById(R.id.row31);
        row32 = findViewById(R.id.row32);
        row33 = findViewById(R.id.row33);
        row41 = findViewById(R.id.row41);
        row42 = findViewById(R.id.row42);
        row43 = findViewById(R.id.row43);
        row51 = findViewById(R.id.row51);
        row52 = findViewById(R.id.row52);
        row53 = findViewById(R.id.row53);

    }
}
