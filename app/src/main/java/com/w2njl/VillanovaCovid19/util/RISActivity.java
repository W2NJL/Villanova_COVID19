package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.w2njl.VillanovaCovid19.CovidService;
import com.w2njl.VillanovaCovid19.R;

import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.w2njl.VillanovaCovid19.CovidService.running;
import static com.w2njl.VillanovaCovid19.CovidService.serviceTime;

public class RISActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    private static final String TAG = "RISActivity";
    public static final String RIS_ID_KEY = "risId";
    private static final String PATIENT_KEY = "patient1";

    private TextView txtRIS, txtHR, txtSpO2, txtTemp, txtTV, txtRR, txtRisk, txtTimeStamp;
    private Button btnGraph, btnShare, btnArchive, btnMonitoring;
    
    Toolbar toolbar;
    public static DatabaseReference reff = null;
        long timeDiff;
    int sum = 0;
    private static CovidFeatures patient1 = new CovidFeatures();
    Snackbar snackbar;
    String message;
    private SwipeRefreshLayout mSwipeRefreshLayout;
       private Intent mIntent;
    Query lastQuery;



    ScheduledExecutorService exec = null;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

// set an exit transition
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_r_i_s);

        txtRIS = findViewById(R.id.txtRIS);
        txtHR = findViewById(R.id.txtHR2);
        txtSpO2 = findViewById(R.id.txtSp02Level);
        txtTemp = findViewById(R.id.txtTempValue);
        txtRR = findViewById(R.id.txtRR);
        txtTV = findViewById(R.id.txtTV);
        txtRisk = findViewById(R.id.txtDanger);
        txtTimeStamp = findViewById(R.id.timeStamp);

        btnArchive = findViewById(R.id.btnViewArchives);
        btnGraph = findViewById(R.id.btnViewSound);
        btnShare = findViewById(R.id.btnShare);
        btnMonitoring = findViewById(R.id.btnMonitoring);
//        if(!running) {
//            btnMonitoring.setText(R.string.startmonitoring);
//            btnMonitoring.setTag(1);
//        }
//        else {
//
//            btnMonitoring.setText(R.string.stopmonitoring);
//            btnMonitoring.setTag(0);
//            Date date = new Date();
//            long currentTime = date.getTime();
//            timeDiff = currentTime - serviceTime;
//            timeDiff = timeDiff / 1000;
//            timeDiff = 60 - timeDiff;
//
//            Log.d(TAG, "onCreate: timediff " + timeDiff);
//            Log.d(TAG, "onCreate: DONE!");
//
//
//
//           exec = Executors.newSingleThreadScheduledExecutor();         exec.scheduleAtFixedRate(() -> {             initViews();      }, timeDiff, 60, TimeUnit.SECONDS);
//
//
//
//
//
//        }




//                    reff.child(String.valueOf(maxid+1)).setValue(incomingCovid);


        mIntent = new Intent(this, CovidService.class);






//        if (sum ==0) {
//            init();
//            initDB();
//            initViews();
//
//
//        }



//        Thread two = new Thread(new Runnable() {
//            public void run()
//            {
//                initCrush();
//
//                Log.d(TAG, "run2: Done!");
//            }
//        });





//        boolean done = false;
//        while (!done) {
//            try {
//                one.join();
//
//                done = true;
//            } catch (InterruptedException e) {
//                // Handle interrupt determine if need to exit.
//            }
//
//
//        }

//        two.start();


        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {


            initViews();
            setData(patient1);
            myCustomSnackbar(patient1);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        });


        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RISActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        btnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(RISActivity.this, view);
                popup.setOnMenuItemClickListener(RISActivity.this);
                popup.inflate(R.menu.menu_archive);
                popup.show();
            }
        });







        btnMonitoring.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final int status =(Integer) v.getTag();
                if(!running) {
                    try {
                        startService(v);
                        btnMonitoring.setText(R.string.stopmonitoring);
                        v.setTag(0); //pause
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    stopService(v);
                   btnMonitoring.setText(R.string.startmonitoring);

                    v.setTag(1); //pause
                }
            }
        });


//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                try {
//                    startService(view);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopService(view);
//            }
//        });

    }

    private void initCrush() {

       sum=0;
       reff.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                                          for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                              sum = sum + snapshot2.child("ris").getValue(Integer.class);

                                          }

//                    txtRR.setText(String.valueOf(sum));

                                          GraphView graph = (GraphView) findViewById(R.id.graph);
                                          graph.removeAllSeries();

                                          StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                                          staticLabelsFormatter.setHorizontalLabels(new String[]{"", ""});
                                          graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                                          graph.getViewport().setYAxisBoundsManual(true);
                                          graph.getViewport().setMinY(-50);
                                          graph.getViewport().setMaxY(30);


                                          graph.getViewport().setXAxisBoundsManual(true);
                                          graph.getViewport().setMinX(0);
                                          graph.getViewport().setMaxX(3);

                                          Log.d(TAG, "onDataChange: sum " + sum);
                                          Log.d(TAG, "onDataChange: childs " + snapshot.getChildrenCount());

                                          double average2 = (double) sum / (double) snapshot.getChildrenCount();

                                          int average = (int) Math.round(average2);

                                          Log.d(TAG, "onDataChange: avg " + average);



                                          BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                                                  new DataPoint(1, average),

                                          });
                                          graph.addSeries(series);
                                          BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
                                                  new DataPoint(2, patient1.getRIS()),


                                          });
                                          graph.addSeries(series2);
                                          series.setColor(Color.GREEN);
                                          series.setTitle("Average RIS");
                                          series2.setTitle("Current RIS");
                                          graph.getLegendRenderer().setVisible(true);
                                          graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
// styling
//                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//                        @Override
//                        public int get(DataPoint data) {
//                            return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//                        }
//                    });

                                          series.setSpacing(50);
                                          series2.setSpacing(50);

// draw values on top
                                          series.setDrawValuesOnTop(true);
                                          series.setValuesOnTopColor(Color.RED);
                                          series2.setDrawValuesOnTop(true);
                                          series2.setValuesOnTopColor(Color.RED);

                                          reff.removeEventListener(this);
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {
                                          reff.removeEventListener(this);
                                      }
                                  });

//
//        reff.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    CovidFeatures covid2 = RISUtils.getPatient1();
//
//
//                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
//                    sum = sum + snapshot2.child("ris").getValue(Integer.class);
//
//                }
//
////                    txtRR.setText(String.valueOf(sum));
//
//                GraphView graph = (GraphView) findViewById(R.id.graph);
//                graph.removeAllSeries();
//
//                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                staticLabelsFormatter.setHorizontalLabels(new String[]{"", ""});
//                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//
//                graph.getViewport().setYAxisBoundsManual(true);
//                graph.getViewport().setMinY(-50);
//                graph.getViewport().setMaxY(30);
//
//
//                graph.getViewport().setXAxisBoundsManual(true);
//                graph.getViewport().setMinX(0);
//                graph.getViewport().setMaxX(3);
//
//                Log.d(TAG, "onDataChange: sum " + sum);
//                Log.d(TAG, "onDataChange: childs " + snapshot.getChildrenCount());
//
//                double average2 = (double) sum / (double) snapshot.getChildrenCount();
//
//                int average = (int) Math.round(average2);
//
//                Log.d(TAG, "onDataChange: avg " + average);
//
//
//
//                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
//                        new DataPoint(1, average),
//
//                });
//                graph.addSeries(series);
//                BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
//                        new DataPoint(2, patient1.getRIS()),
//
//
//                });
//                graph.addSeries(series2);
//                series.setColor(Color.GREEN);
//                series.setTitle("Average RIS");
//                series2.setTitle("Current RIS");
//                graph.getLegendRenderer().setVisible(true);
//                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
//// styling
////                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
////                        @Override
////                        public int get(DataPoint data) {
////                            return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
////                        }
////                    });
//
//                series.setSpacing(50);
//                series2.setSpacing(50);
//
//// draw values on top
//                series.setDrawValuesOnTop(true);
//                series.setValuesOnTopColor(Color.RED);
//                series2.setDrawValuesOnTop(true);
//                series2.setValuesOnTopColor(Color.RED);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }


    private void initDB() {

        reff = FirebaseDatabase.getInstance().getReference().child("Patient");
        reff.keepSynced(true);
        lastQuery = reff.orderByKey().limitToLast(1);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setData(CovidFeatures covid) {
        LocalDateTime covidTime;
        String tempDate;
        String realMinute;
        tempDate = covid.getCurrentTime();
        tempDate = tempDate.substring(0, tempDate.indexOf('Z'));
        covidTime = LocalDateTime.parse(tempDate);

        Month m = covidTime.getMonth();
        String month = m.toString();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();
        int d = covidTime.getDayOfMonth();
        int year = covidTime.getYear();
        int hour = covidTime.getHour();
        int minute = covidTime.getMinute();

        if (minute >=0 && minute <10) {
            realMinute = "0" + minute;
        }
        else
            realMinute = "" + minute;

        int seconds = covidTime.getSecond();

        txtRIS.setText(String.valueOf(covid.getRIS()));
        txtHR.setText(String.valueOf(covid.getHR()) + " bpm");
        txtTemp.setText(String.valueOf(covid.getTemp()) + "\u00B0F");
        txtRR.setText(String.valueOf(covid.getRR()));
        txtTV.setText(String.valueOf(covid.getTV()));
        txtRisk.setText(String.valueOf(covid.getDanger()));
//        txtTimeStamp.setText((String.valueOf(covid.getCurrentTime())));
        txtTimeStamp.setText("RIS sample taken on " + month + " " + String.valueOf(d) +", " + String.valueOf(year) + " at " + String.valueOf(hour) + ":" + realMinute
        + ":" + String.valueOf(seconds));

        setDangerColor(txtRisk, covid);

        txtSpO2.setText(String.valueOf(covid.getSpO2()) + "%");

//        Glide.with(this)
//                .asBitmap().load(book.getImageUrl())
//                .into(bookImage);


    }

    private void setDangerColor(TextView txtRisk, CovidFeatures covid) {


        switch (covid.getDanger()) {
            case "ELEVATED":
                txtRisk.setTextColor(Color.parseColor("#9700FF"));
                break;
            case "MODERATE":
                txtRisk.setTextColor(Color.parseColor("FF8300"));
                break;
            case "HIGH":
                txtRisk.setTextColor(Color.parseColor("#FF0000"));
                break;
            default:
                break;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRate:
                Intent intent = new Intent(RISActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.actionFeedback:
                sendFeedBack();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void sendFeedBack() {
        Intent localIntent = new Intent(Intent.ACTION_SEND);
        localIntent.putExtra(Intent.EXTRA_EMAIL, R.string.mail_feedback_email);
        localIntent.putExtra(Intent.EXTRA_CC, "");
        String str;
        try {
            str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            localIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_msg));
            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n" + getResources().getString(R.string.device_os) +
                    Build.VERSION.RELEASE + "\n" + getResources().getString(R.string.app_version) + str + "\n Device Brand: " + Build.BRAND +
                    "\n" + getResources().getString(R.string.device_model) + Build.MODEL + "\n" + getResources().getString(R.string.manufacturer) + Build.MANUFACTURER);
            localIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(localIntent, getResources().getString(R.string.email_client)));
        } catch (Exception ignored) {
        }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews() {

        sum = 0;
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        graph.removeAllSeries();


        Log.d(TAG, "onDataChange Russell Shepard");

        SharedPreferences sharedPreferences = null;
        ArrayList<CovidFeatures> covids = new ArrayList<>();

//        Random random = new Random();
//        int HR2;
//        int HR = random.nextInt(170 - 70) + 70;
//        double temp = 96.4 + (102.5 - 96.4) * random.nextDouble();
//        int spO2 = random.nextInt(99 - 87) + 87;
//        int TV = random.nextInt(10000 - 5000) + 5000;
//        int RR = random.nextInt(20 - 10) + 10;
//
//        LocalDateTime now = LocalDateTime.now();
//        String date = DateTimeFormatter.ISO_INSTANT.format(now.toInstant(ZoneOffset.UTC));
//
//
//        temp = Precision.round(temp, 1);
//
//
//
//        int RIS = genRIS(getO2risk(spO2), getHRrisk(HR), getTempRisk(temp), getMVrisk(RR, TV));
//
//        patient1 = new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
//                TV, RR, date);



        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    int HR = child.child("hr").getValue(Integer.class);
                    double temp = child.child("temp").getValue(Double.class);
                    int spO2 = child.child("spO2").getValue(Integer.class);
                    int TV = child.child("tv").getValue(Integer.class);
                    int RR = child.child("rr").getValue(Integer.class);
                    Log.d(TAG, "User key" + child.getKey());
                    Log.d(TAG, "onDataChange HR: " + HR);


                    temp = Precision.round(temp, 1);
                    int RIS = child.child("ris").getValue(Integer.class);

                    Log.d(TAG, "onDataChange RIS: " + getDanger(RIS));
                    String date = child.child("currentTime").getValue().toString();


                    patient1 = new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
                            TV, RR, date);

                    setData(patient1);


                    myCustomSnackbar(patient1);
                    lastQuery.removeEventListener(this);
                }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lastQuery.removeEventListener(this);
            }
        });
//
//        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child: dataSnapshot.getChildren()) {
//                    int HR = child.child("hr").getValue(Integer.class);
//                    double temp = child.child("temp").getValue(Double.class);
//                    int spO2 = child.child("spO2").getValue(Integer.class);
//                    int TV = child.child("tv").getValue(Integer.class);
//                    int RR = child.child("rr").getValue(Integer.class);
//                    Log.d(TAG, "User key" + child.getKey());
//                    Log.d(TAG, "onDataChange HR: " + HR);
//
//
//                    temp = Precision.round(temp, 1);
//                    int RIS = child.child("ris").getValue(Integer.class);
//
//                    Log.d(TAG, "onDataChange RIS: " + getDanger(RIS));
//                    String date = child.child("currentTime").getValue().toString();
//
//
//           patient1 = new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
//                        TV, RR, date);
//
//                    setData(patient1);
//
                   initCrush();
//
//                    myCustomSnackbar(patient1);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors.
//            }
//        });

//        if(futureTaskRunning){
//        changeReadInterval(60);}

//        patient1.setRIS(66);
//        patient1.setDanger(getDanger(RIS));
//        patient1.setId(1);
//        patient1.setHR(HR);
//        patient1.setSpO2(spO2);
//        patient1.setTemp(temp);
//        patient1.setTV(TV);
//        patient1.setRR(RR);
//        patient1.setCurrentTime(date);


//        covids.add(new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
//                TV, RR, date));


//
//        reff.push().setValue(patient1);

//        CovidFeatures j = new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
//                TV, RR);
//
//        reff = FirebaseDatabase.getInstance().getReference().child("Patient");
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    maxid=(snapshot.getChildrenCount());
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        SharedPreferences.Editor editor = sharedPreferences.edit();
////        reff.child(String.valueOf(maxid+1)).setValue(j);
//        Gson gson = new Gson();
//        editor.putString(PATIENT_KEY, gson.toJson(covids));
//        editor.commit();




//        risImage = findViewById(R.id.RISImage);
    }

    private int getMVrisk(int RR, int TV) {

        int risk = RR * TV;

        if (risk >= 10000 && risk < 12750)
            return 2;
        else if (risk >= 12750)
            return 5;
        else if (risk >= 4950 && risk < 6000)
            return 8;
        else if (risk >= 6000 && risk < 8000)
            return 4;
        else if (risk >= 8000)
            return 7;
        else return 10;


    }

    private int getTempRisk(double temp) {


        if (temp <= 95.0 || temp >= 100.0)
            return 8;

        else return -8;
    }

    private int getHRrisk(int HR) {
        if (HR >= 100)
            return 5;

        else return -5;
    }

    private int getO2risk(int O2) {
        if (O2 < 90)
            return 10;
        else return -10;
    }

    private int genRIS(int O2risk, int HRrisk, int tempRisk, int MVrisk) {
        return O2risk + HRrisk + tempRisk + MVrisk;
    }


    private String getDanger(int RIS) {


        if (RIS > 0 && RIS < 50)
            return "ELEVATED";

        if (RIS >= 50 && RIS < 100)
            return "MODERATE";

        if (RIS >= 100)
            return "HIGH";

        return "LOW";
    }

    public void myCustomSnackbar(CovidFeatures covid) {
        message = getString(R.string.covid_risk);
        String message2 = covid.getDanger();

        // Create the Snackbar
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        snackbar = Snackbar.make(this.findViewById(android.R.id.content), message2, Snackbar.LENGTH_INDEFINITE);

        // Get the Snackbar layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Set snackbar layout params
        int navbarHeight = getNavBarHeight(this);
        FrameLayout.LayoutParams parentParams = (FrameLayout.LayoutParams) layout.getLayoutParams();
        parentParams.setMargins(0, 0, 0, 0 - navbarHeight + 200);
        layout.setLayoutParams(parentParams);
        layout.setPadding(0, 0, 0, 0);
        layout.setLayoutParams(parentParams);

        // Inflate our custom view
        View snackView = getLayoutInflater().inflate(R.layout.my_snackbar, null);

        // Configure our custom view
        TextView messageTextView = (TextView) snackView.findViewById(R.id.message_text_view);
        messageTextView.setText(message);
        TextView messageTextView2 = (TextView) snackView.findViewById(R.id.message_text_view2);
        messageTextView2.setText(message2);
        setDangerColor(messageTextView2, covid);

        TextView textViewOne = (TextView) snackView.findViewById(R.id.first_text_view);
        textViewOne.setText("Learn More");
        textViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(RISActivity.this, LearnMoreActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(RISActivity.this).toBundle());
            }
        });

        TextView textViewTwo = (TextView) snackView.findViewById(R.id.second_text_view);
        textViewTwo.setText("Dismiss");
        textViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deny", "showTwoButtonSnackbar() : deny clicked");
                snackbar.dismiss();
            }
        });

        // Add our custom view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams);

        // Show the Snackbar
        snackbar.show();
    }

    public static int getNavBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startService(View v) throws InterruptedException {

//        GraphView graph = (GraphView) findViewById(R.id.graph);


                startService(mIntent);
//        graph.removeAllSeries();

        exec = Executors.newSingleThreadScheduledExecutor();         exec.scheduleAtFixedRate(() -> {             initViews();      }, 1, 60, TimeUnit.SECONDS);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        if (!running) {
            btnMonitoring.setText(R.string.startmonitoring);
            btnMonitoring.setTag(1);
        } else {

            btnMonitoring.setText(R.string.stopmonitoring);
            btnMonitoring.setTag(0);
            Date date = new Date();
            long currentTime = date.getTime();
            timeDiff = currentTime - serviceTime;
            timeDiff = timeDiff / 1000;
            timeDiff = 60 - timeDiff;

            Log.d(TAG, "onResume: timediff " + timeDiff);
            Log.d(TAG, "onResume: DONE!");


            exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                initViews();
            }, timeDiff, 60, TimeUnit.SECONDS);




        }
        init();
        initDB();
        initViews();
        super.onResume();
    }

    @Override
    protected void onPause() {

        if(exec !=null){
            if(!exec.isShutdown())
        exec.shutdown();}
        super.onPause();
    }

    

    public void stopService(View v) {

exec.shutdown();



        stopService(mIntent);
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.dayMenu:
                intent = new Intent(RISActivity.this, RISArchiveActivity.class);
                startActivity(intent);
                return true;
            case R.id.weekMenu:
                intent = new Intent(RISActivity.this, RISWeekArchiveActivity.class);
                startActivity(intent);
                return true;
            default:
                intent = new Intent(RISActivity.this, RISMonthArchiveActivity.class);
                startActivity(intent);
                return false;
        }
    }

}

