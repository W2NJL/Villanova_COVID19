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

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.w2njl.VillanovaCovid19.R;

import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RISMonthArchiveActivity extends AppCompatActivity {
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
    MonthFragment monthFragment;
    Month2Fragment month2Fragment;
    Month3Fragment month3Fragment;
    Month4Fragment month4Fragment;
    Month5Fragment month5Fragment;
    Month6Fragment month6Fragment;
    Month7Fragment month7Fragment;
    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


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

        currentDate = LocalDate.now().minusMonths(i);
        aWeekAgo = LocalDate.now().minusWeeks(i+1).plusDays(1);
        Month m = currentDate.getMonth();
        String month = m.toString();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();
           int year = currentDate.getYear();






        String title = month + " " + year;
        switch(i){
            case 0:
                viewPagerAdapter.addFragment(monthFragment, title);

                break;
            case 1:
                viewPagerAdapter.addFragment(month2Fragment, title);

                break;
            case 2:
                viewPagerAdapter.addFragment(month3Fragment, title);

                break;
            case 3:
                viewPagerAdapter.addFragment(month4Fragment, title);

                break;
            case 4:
                viewPagerAdapter.addFragment(month5Fragment, title);

                break;
            case 5:
                viewPagerAdapter.addFragment(month6Fragment, title);

                break;
            case 6:
                viewPagerAdapter.addFragment(month7Fragment, title);

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

                    if (now.getMonth().equals(toweek.getMonth())){
                        risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
                        HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
                        O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
                        TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
                        RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
                        tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
                        count++;}
                }

                if(count==0){
                    Toast.makeText(RISMonthArchiveActivity.this, "There is no data available for toweek", Toast.LENGTH_SHORT).show();
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

        monthFragment = new MonthFragment();
        month2Fragment = new Month2Fragment();
        month3Fragment = new Month3Fragment();
        month4Fragment = new Month4Fragment();
        month5Fragment = new Month5Fragment();
        month6Fragment = new Month6Fragment();
        month7Fragment = new Month7Fragment();
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