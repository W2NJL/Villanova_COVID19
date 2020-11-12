package com.w2njl.VillanovaCovid19;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class WaterIntakeResult extends AppCompatActivity {
    Button chart;
    Animation rotate;
    TextView tvDwi;
    TextView tvDwiDesc;
    TextView tvDwiVal;
    TextView tvGlasses;
    Toolbar toolbar;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.OrangeTheme);
        setContentView(R.layout.waterintakeresult);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        ImageView imageView = findViewById(R.id.ivRotate);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(this.rotate);
        tvDwiVal.setText(getIntent().getStringExtra(ConditionalUserProperty.VALUE));
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.waterintake));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvDwi = findViewById(R.id.tvDwi);
        tvDwiVal = findViewById(R.id.tvDwiVal);
        tvDwiDesc = findViewById(R.id.tvDwiDesc);
        tvGlasses = findViewById(R.id.tvGlasses);

        tvDwiDesc.setBackground(getShapeDrawble(true, ContextCompat.getColor(getApplicationContext(), R.color.orangecolorPrimary)));
    }
}
