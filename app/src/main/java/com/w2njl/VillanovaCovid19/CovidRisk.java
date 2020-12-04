package com.w2njl.VillanovaCovid19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class CovidRisk extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Villanova COVID-19 Monitor Values");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_risk);
        Intent i = getIntent();


        TextView tz = findViewById(R.id.wavFile);

        TextView txtMax = findViewById(R.id.txtMax);
        TextView txtMin = findViewById(R.id.txtMin);
        TextView txtMean = findViewById(R.id.txtMean);
        TextView txtSkew = findViewById(R.id.txtSkew);
        TextView txtKurt = findViewById(R.id.txtKurt);

        // Read in the WAV file
//        WavFile j = new WavFile("32mm.wav", this.getApplicationContext());
//
//        ArrayList<Double> jz = j.getSamples();
//
//        Double[] dblArray = new Double[jz.size()];
//
//        dblArray = jz.toArray(dblArray);
//
//        tz.setText(j.fileName());
//
//        //Convert WAV file ArrayList to array of double.  THIS is the array that needs to be sent to C for filtering.
//        double[] unboxed = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
//        double[] zz = new double[3];
//
//        addArray(unboxed, zz);

//Use Double from JNI function as a test from transferring array from C to Java

////        double [] z = doublefromJNI();
//
//
//
//        double mean = zz[0];
//        double skew = zz[1];
//        double kurt = zz[2];
//
//        double max = Arrays.stream(unboxed).max().getAsDouble();
//        double min = Arrays.stream(unboxed).min().getAsDouble();
//
//        txtMax.setText("The maximum value of the filtered array is: " + max);
//        txtMin.setText("The minimum value of the filtered array is: " + min);
//        txtMean.setText("The mean value of the raw array is: " + mean);
//        txtSkew.setText("The skewness of the raw array is: " + skew);
//        txtKurt.setText("The kurtosis of the raw array is: " + kurt);

    }

    public static native double addArray(double []arr, double []arr2, double []arr3, double []arr4);
}