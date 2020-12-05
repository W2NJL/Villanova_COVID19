package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.w2njl.VillanovaCovid19.CovidService;
import com.w2njl.VillanovaCovid19.LoginActivity;
import com.w2njl.VillanovaCovid19.R;

import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

import static com.w2njl.VillanovaCovid19.CovidService.running;

public class RISWeekArchiveActivity extends AppCompatActivity {
    private static final String TAG = null;
    Toolbar toolbar;
    public static ViewPager viewPager;
    DatabaseReference reff;
    private static CovidFeatures patient1 = new CovidFeatures();
    TextView risTableData, tempTableData, O2TableData, RRTableData, TVTableData, HRTableData, risHeader;
    LocalDate currentDate;
    LocalDate aWeekAgo;
    TabLayout tabLayout;
    TabItem weekTab, monthTab, yearTab;
    WeekFragment weekFragment;
    Week2Fragment week2Fragment;
    Week3Fragment week3Fragment;
    Week4Fragment week4Fragment;
    Week5Fragment week5Fragment;
    Week6Fragment week6Fragment;
    Week7Fragment week7Fragment;
    MonthFragment monthFragment;
    YearFragment yearFragment;
    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    private Intent mIntent;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_r_i_s_archive);


        init();

//        initDB();



        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        for(int i=0; i<7; i++){

            fragName(i);

        }



//        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
//        badgeDrawable.setVisible(true);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fragName(int i) {

        currentDate = LocalDate.now().minusDays(7*i);
        aWeekAgo = LocalDate.now().minusWeeks(i+1).plusDays(1);
        Month m = currentDate.getMonth();
        int monthNum = currentDate.getMonthValue();
        int monthNumWeekAgo = aWeekAgo.getMonthValue();
        String month = m.toString();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();
        int d = currentDate.getDayOfMonth();
        int dAWeekAgo = aWeekAgo.getDayOfMonth();
        int year = currentDate.getYear();
        int yearAWeekAgo = aWeekAgo.getYear();





        String title = monthNumWeekAgo + "/" + dAWeekAgo + "/" + yearAWeekAgo + " - " + monthNum + "/" + d + "/" + year;
        switch(i){
            case 0:
                viewPagerAdapter.addFragment(weekFragment, title);

                break;
            case 1:
                viewPagerAdapter.addFragment(week2Fragment, title);

                break;
            case 2:
                viewPagerAdapter.addFragment(week3Fragment, title);

                break;
            case 3:
                viewPagerAdapter.addFragment(week4Fragment, title);

                break;
            case 4:
                viewPagerAdapter.addFragment(week5Fragment, title);

                break;
            case 5:
                viewPagerAdapter.addFragment(week6Fragment, title);

                break;
            case 6:
                viewPagerAdapter.addFragment(week7Fragment, title);

                break;
            default:
                break;
        }


        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initDB() {
        reff = FirebaseDatabase.getInstance().getReference().child("Patient");

        reff.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date;
                LocalDate now;
                LocalDate toweek = LocalDate.now();
                int risSum = 0;
                int HRSum = 0;
                int O2Sum = 0;
                int TVSum = 0;
                int RRSum = 0;
                double tempSum = 0.0;
                int count = 0;



                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    date = snapshot2.child("currentTime").getValue(String.class);
                    date = date.substring(0, date.indexOf('T'));
                    now = LocalDate.parse(date);

                    if (now.equals(toweek)){
                        risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
                        HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
                        O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
                        TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
                        RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
                        tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
                        count++;}
                }

                if(count==0){
                    Toast.makeText(RISWeekArchiveActivity.this, "There is no data available for toweek", Toast.LENGTH_SHORT).show();
                }

                else {
                    risTableData.setText(String.valueOf(risSum / count));
                    tempTableData.setText(String.valueOf(Precision.round(tempSum / count, 1)) + "°F");
                    O2TableData.setText(String.valueOf(O2Sum / count) + "%");
                    RRTableData.setText(String.valueOf(RRSum / count));
                    TVTableData.setText(String.valueOf(TVSum / count));
                    HRTableData.setText(String.valueOf(HRSum / count) + " bpm");

                    GraphView graph = (GraphView) findViewById(R.id.graph);

                    graph.setTitle("RIS values by hour");

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-50);
                    graph.getViewport().setMaxY(30);


                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(24);

                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    staticLabelsFormatter.setHorizontalLabels(new String[] {"12AM", "5AM", "10AM", "5PM", "10PM"});
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    series.setTitle("Filtered data");
                    series.setColor(Color.GREEN);
                    DataPoint point;
                    LocalDateTime lt;
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                    int hour;
                    int minute;
                    double time;
                    String temp;

                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        date = snapshot2.child("currentTime").getValue(String.class);
                        date = date.substring(0, date.indexOf('Z'));
                        lt = LocalDateTime.parse(date, formatter);
                        date = date.substring(0, date.indexOf('T'));
                        now = LocalDate.parse(date);
                        hour = lt.getHour();
                        minute = lt.getMinute();


                        if (minute >=0 && minute <10) {
                            temp = hour + ".0" + minute;
                        }
                        else
                            temp = hour + "." + minute;


                        time = Double.parseDouble(temp);




                        if (now.equals(toweek)){
                            risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
                            HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
                            O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
                            TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
                            RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
                            tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
                            Log.d(TAG, "onDataChange: " + time);


                            point = new DataPoint(time, snapshot2.child("ris").getValue(Integer.class));
                            series.appendData(point, true, 1440);
                        }


                    }

                    graph.addSeries(series);



//                    graph.getLegendRenderer().setVisible(true);
//                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                }

//                    txtRR.setText(String.valueOf(sum));

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
                reff.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
//        reff.addListenerForSingleValueEvent(new ValueEventListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    CovidFeatures covid2 = RISUtils.getPatient1();
//                String date;
//                LocalDate now;
//                LocalDate toweek = LocalDate.now();
//                int risSum = 0;
//                int HRSum = 0;
//                int O2Sum = 0;
//                int TVSum = 0;
//                int RRSum = 0;
//                double tempSum = 0.0;
//                int count = 0;
//
//
//
//                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
//                   date = snapshot2.child("currentTime").getValue(String.class);
//                   date = date.substring(0, date.indexOf('T'));
//                    now = LocalDate.parse(date);
//
//                    if (now.equals(toweek)){
//                        risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
//                    HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
//                    O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
//                    TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
//                    RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
//                    tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
//                    count++;}
//                }
//
//                if(count==0){
//                    Toast.makeText(RISArchiveActivity.this, "There is no data available for toweek", Toast.LENGTH_SHORT).show();
//                }
//
//                else {
//                    risTableData.setText(String.valueOf(risSum / count));
//                    tempTableData.setText(String.valueOf(Precision.round(tempSum / count, 1)) + "°F");
//                    O2TableData.setText(String.valueOf(O2Sum / count) + "%");
//                    RRTableData.setText(String.valueOf(RRSum / count));
//                    TVTableData.setText(String.valueOf(TVSum / count));
//                    HRTableData.setText(String.valueOf(HRSum / count) + " bpm");
//
//                    GraphView graph = (GraphView) findViewById(R.id.graph);
//
//                    graph.getViewport().setYAxisBoundsManual(true);
//                    graph.getViewport().setMinY(-50);
//                    graph.getViewport().setMaxY(30);
//
//
//                    graph.getViewport().setXAxisBoundsManual(true);
//                    graph.getViewport().setMinX(0);
//                    graph.getViewport().setMaxX(24);
//
//                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//                    series.setTitle("Filtered data");
//                    series.setColor(Color.GREEN);
//                    DataPoint point;
//                    LocalDateTime lt;
//                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//                    int hour;
//                    int minute;
//                    double time;
//                    String temp;
//
//                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
//                        date = snapshot2.child("currentTime").getValue(String.class);
//                        date = date.substring(0, date.indexOf('Z'));
//                        lt = LocalDateTime.parse(date, formatter);
//                        date = date.substring(0, date.indexOf('T'));
//                        now = LocalDate.parse(date);
//                        hour = lt.getHour();
//                        minute = lt.getMinute();
//
//
//                        if (minute >=0 && minute <10) {
//                            temp = hour + ".0" + minute;
//                        }
//                        else
//                            temp = hour + "." + minute;
//
//
//                        time = Double.parseDouble(temp);
//
//
//
//
//                        if (now.equals(toweek)){
//                            risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
//                            HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
//                            O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
//                            TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
//                            RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
//                            tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
//                            Log.d(TAG, "onDataChange: " + time);
//
//
//                            point = new DataPoint(time, snapshot2.child("ris").getValue(Integer.class));
//                            series.appendData(point, true, 1440);
//                    }
//
//
//                    }
//
//                    graph.addSeries(series);
//
//
//
////                    graph.getLegendRenderer().setVisible(true);
////                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//                }
//
////                    txtRR.setText(String.valueOf(sum));
//
////                GraphView graph = (GraphView) findViewById(R.id.graph);
////                graph.removeAllSeries();
////
////                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
////                staticLabelsFormatter.setHorizontalLabels(new String[]{"", ""});
////                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
////
////                graph.getViewport().setYAxisBoundsManual(true);
////                graph.getViewport().setMinY(-50);
////                graph.getViewport().setMaxY(30);
////
////
////                graph.getViewport().setXAxisBoundsManual(true);
////                graph.getViewport().setMinX(0);
////                graph.getViewport().setMaxX(3);
////
////                Log.d(TAG, "onDataChange: sum " + sum);
////                Log.d(TAG, "onDataChange: childs " + snapshot.getChildrenCount());
////
////                double average2 = (double) sum / (double) snapshot.getChildrenCount();
////
////                int average = (int) Math.round(average2);
////
////
////
////                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
////                        new DataPoint(1, average),
////
////                });
////                graph.addSeries(series);
////                BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
////                        new DataPoint(2, patient1.getRIS()),
////
////
////                });
////                graph.addSeries(series2);
////                series.setColor(Color.GREEN);
////                series.setTitle("Average RIS");
////                series2.setTitle("Current RIS");
////                graph.getLegendRenderer().setVisible(true);
////                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
////// styling
//////                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//////                        @Override
//////                        public int get(DataPoint data) {
//////                            return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//////                        }
//////                    });
////
////                series.setSpacing(50);
////                series2.setSpacing(50);
////
////// draw values on top
////                series.setDrawValuesOnTop(true);
////                series.setValuesOnTopColor(Color.RED);
////                series2.setDrawValuesOnTop(true);
////                series2.setValuesOnTopColor(Color.RED);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {


//        risTableData = findViewById(R.id.risTableData);
//        tempTableData = findViewById(R.id.tempTableData);
//        O2TableData = findViewById(R.id.spO2TableData);
//        RRTableData = findViewById(R.id.RRTableData);
//        TVTableData = findViewById(R.id.MVTableData);
//        HRTableData = findViewById(R.id.HRTableData);
//        risHeader = findViewById(R.id.risHeader);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
                monthTab = findViewById(R.id.monthTab);
        yearTab = findViewById(R.id.yearTab);
        viewPager = findViewById(R.id.viewPager);
        weekFragment = new WeekFragment();
        monthFragment = new MonthFragment();
        yearFragment = new YearFragment();
        week2Fragment = new Week2Fragment();
        week3Fragment = new Week3Fragment();
        week4Fragment = new Week4Fragment();
        week5Fragment = new Week5Fragment();
        week6Fragment = new Week6Fragment();
        week7Fragment = new Week7Fragment();

//        currentDate = LocalDate.now();
//        Month m = currentDate.getMonth();
//        String month = m.toString();
//        month = month.substring(0, 1) + month.substring(1).toLowerCase();
//        int d = currentDate.getWeekOfMonth();
//        int year = currentDate.getYear();
//        risHeader.setText("RIS data for "  + month + " " +  String.valueOf(d) + ", " + String.valueOf(year));

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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRate:
                Intent intent = new Intent(RISWeekArchiveActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.actionFeedback:
                sendFeedBack();
                break;
            case R.id.actionLogout:
                mIntent = new Intent(this, CovidService.class);
                if(running){
                    stopService(mIntent);
                }
                Paper.init(this);
                Paper.book().destroy();
                startActivity(new Intent(RISWeekArchiveActivity.this, LoginActivity.class));
                break;
            case R.id.actionUser:
                startActivity(new Intent(RISWeekArchiveActivity.this, UserProfile.class));
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}