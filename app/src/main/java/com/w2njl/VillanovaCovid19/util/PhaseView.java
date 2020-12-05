package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.w2njl.VillanovaCovid19.R;
import com.w2njl.VillanovaCovid19.WavFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static com.w2njl.VillanovaCovid19.CovidRisk.addArray;
import static com.w2njl.VillanovaCovid19.CovidService.exhale;
import static com.w2njl.VillanovaCovid19.CovidService.filtered;
import static com.w2njl.VillanovaCovid19.CovidService.inhale;
import static com.w2njl.VillanovaCovid19.CovidService.unfiltered;


public class PhaseView extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    //    private Handler mainHandler = new Handler();
//    private Handler mainHandler2 = new Handler();
    private double[] unboxed;
    private double[] unboxed2;
    //    boolean thread1Start;
//    boolean thread2Start;
//    private Thread thread1;
//    private Thread thread2;
    private double[] zz;



    Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);
        ((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());

        init();

        Thread one = new Thread(new Runnable() {
            public void run()
            {
                loadfromC();
                Log.d(TAG, "run1: Done!");
            }
        });

        Thread two = new Thread(new Runnable() {
            public void run()
            {
                loadGraph();
                Log.d(TAG, "run2: Done!");
            }
        });

        one.start();



        boolean done = false;
        while (!done) {
            try {
                one.join();

                done = true;
            } catch (InterruptedException e) {
                // Handle interrupt determine if need to exit.
            }


        }

        two.start();
//        double entropy = zz[0];
//        double skew = zz[1];
//        double kurt = zz[2];
//        Toast.makeText(TestActivity.this, "The entropy is: " + entropy + "\nThe skewness : " + skew + "\nThe kurtosis is: " + kurt, Toast.LENGTH_LONG).show();










//        Toast.makeText(TestActivity.this, "The entropy is: " + entropy + "\nThe skewness : " + skew + "\nThe kurtosis is: " + kurt, Toast.LENGTH_LONG).show();

//        ExampleRunnable runnable = new ExampleRunnable(4, getApplicationContext());
//        new Thread(runnable).start();




//                    TextView tz = findViewById(R.id.txtMax);
//                    TextView ty = findViewById(R.id.txtMin);
//                    TextView tw = findViewById(R.id.txtSkew);
//                    TextView tt = findViewById(R.id.txtKurt);











//        ExampleRunnable2 runnable2 = new ExampleRunnable2(4, getApplicationContext());
//        new Thread(runnable2).start();






    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadfromC(){
//        WavFile j = new WavFile("32mm.wav", TestActivity.this);
//
//        ArrayList<Double> jz = j.getSamples();
//
//        Double[] dblArray = new Double[jz.size()];
//
//        dblArray = jz.toArray(dblArray);
//
//        unboxed = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
//        unboxed2 = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
//
//        zz = new double[4];
//
//          double mean = 0;
//   for (int i=0; i<2890080; i++) {
//       mean = mean + unboxed[i];
//   }
//
//    mean = mean/2890080;
//
//  for(int z=0; z<2890080; z++){
//        unboxed[z] = unboxed[z] - mean;}
//
//
////
////        addArray(unboxed, zz);
//
////        Toast.makeText(TestActivity.this, "The thing is is: " + unboxed[0], Toast.LENGTH_LONG).show();
//
//        double max = Arrays.stream(unboxed).max().getAsDouble();
//        double min = Arrays.stream(unboxed).min().getAsDouble();
    }


    private void loadGraph() {



//                    tz.setText("The mean value of the raw array is: " + mean);
//                    ty.setText("The entropy of one breathing phase is: " + entropy);
//                    tw.setText(("The skewness of the raw array is: " + skew));
//                    tt.setText(("The kurtosis of the raw array is: " + kurt));


        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(70000);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        series.setTitle("Filtered data");
        series.setColor(Color.GREEN);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        int z = 0;
        for(int x=0; x<31020; x++) {
            DataPoint point = new DataPoint(z, inhale[x]);
            series.appendData(point, true, 31020);
            z++;
        }
        graph.addSeries(series);


        for(int w=0; w<38514; w++) {
            DataPoint point2 = new DataPoint(z, exhale[w]);
            series2.appendData(point2, true, 38514);
            z++;
        }
        graph.addSeries(series2);
        series.setTitle("Inhale");
        series2.setTitle("Exhale");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

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


//    class ExampleRunnable implements Runnable{
//        private final Context context;
//        int seconds;
//
//
//        ExampleRunnable(int seconds, Context context){
//            this.seconds = seconds;
//            this.context = context;
//        }
//
//        @Override
//        public void run() {
////            for (int i = 0; i < 10; i++){
////            if( i == 5){
//            mainHandler.post(new Runnable() {
//                @RequiresApi(api = Build.VERSION_CODES.N)
//                @Override
//                public void run() {
//
//
////                    tz.setText("The mean value of the raw array is: " + mean);
////                    ty.setText("The entropy of one breathing phase is: " + entropy);
////                    tw.setText(("The skewness of the raw array is: " + skew));
////                    tt.setText(("The kurtosis of the raw array is: " + kurt));
//
//
//                    GraphView graph = (GraphView) findViewById(R.id.graph);
//
//                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//                    series.setTitle("Filtered data");
//                    series.setColor(Color.GREEN);
//                    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
//                    int z = 0;
//                    for(int x=540296; x<578270; x++) {
//                        DataPoint point = new DataPoint(z, unboxed[x]);
//                        series.appendData(point, true, 37973);
//                        z++;
//                    }
//                    graph.addSeries(series);
//
//                    for(int w=540296; w<578270; w++) {
//                        DataPoint point2 = new DataPoint(z, unboxed2[w]);
//                        series2.appendData(point2, true, 37973);
//                        z++;
//                    }
//                    graph.addSeries(series2);
//                    series.setTitle("Filtered");
//                    series2.setTitle("Unfiltered");
//                    graph.getLegendRenderer().setVisible(true);
//                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//
//
//                }
//            });
//
//
////            Log.d(TAG, "startThread: "+ i);
//            try {
//
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    class ExampleRunnable2 implements Runnable{
//        private final Context context;
//        int seconds;
//
//
//        ExampleRunnable2(int seconds, Context context){
//            this.seconds = seconds;
//            this.context = context;
//        }
//
//        @Override
//        public void run() {
////            for (int i = 0; i < 10; i++){
////            if( i == 5){
//            mainHandler2.post(new Runnable() {
//                @RequiresApi(api = Build.VERSION_CODES.N)
//                @Override
//                public void run() {
//
//
////                    tz.setText("The mean value of the raw array is: " + mean);
////                    ty.setText("The entropy of one breathing phase is: " + entropy);
////                    tw.setText(("The skewness of the raw array is: " + skew));
////                    tt.setText(("The kurtosis of the raw array is: " + kurt));
//
//
//                    WavFile j = new WavFile("32mm.wav", TestActivity.this);
//
//                    ArrayList<Double> jz = j.getSamples();
//
//                    Double[] dblArray = new Double[jz.size()];
//
//                    dblArray = jz.toArray(dblArray);
//
//                    unboxed = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
//                    unboxed2 = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
//
//                    double[] zz = new double[4];
//
//                    addArray(unboxed, zz);
//
//                    double entropy = zz[0];
//                    double skew = zz[1];
//                    double kurt = zz[2];
//
//
//
//
//
//                    double max = Arrays.stream(unboxed).max().getAsDouble();
//                    double min = Arrays.stream(unboxed).min().getAsDouble();
//
//
//                }
//            });
//
//
////            Log.d(TAG, "startThread: "+ i);
//            try {
//
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
}