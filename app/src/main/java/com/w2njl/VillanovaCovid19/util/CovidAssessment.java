package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.w2njl.VillanovaCovid19.R;
import com.w2njl.VillanovaCovid19.WavFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static com.w2njl.VillanovaCovid19.CovidRisk.addArray;

public class CovidAssessment extends AppCompatActivity {

    TextView tv1;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_covid_assessment);
        init();

        ((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());

        WavFile j = new WavFile("32mm.wav", this.getApplicationContext());

        ArrayList<Double> jz = j.getSamples();

        Double[] dblArray = new Double[jz.size()];

        dblArray = jz.toArray(dblArray);



        //Convert WAV file ArrayList to array of double.  THIS is the array that needs to be sent to C for filtering.
        double[] unboxed = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
        double[] zz = new double[3];

        addArray(unboxed, zz);

//Use Double from JNI function as a test from transferring array from C to Java

//        double [] z = doublefromJNI();



        double mean = zz[0];
        double skew = zz[1];
        double kurt = zz[2];

        double max = Arrays.stream(unboxed).max().getAsDouble();
        double min = Arrays.stream(unboxed).min().getAsDouble();

        tv1.setText("The WAVfile being analyzed is: ");
    }

    private void init() {


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.canidonate));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv1 = findViewById(R.id.tv1);


    }
}