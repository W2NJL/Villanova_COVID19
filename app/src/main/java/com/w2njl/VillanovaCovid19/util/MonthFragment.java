package com.w2njl.VillanovaCovid19.util;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.w2njl.VillanovaCovid19.R;

import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "holler";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference reff;
    private static CovidFeatures patient1 = new CovidFeatures();
    TextView risTableData, tempTableData, O2TableData, RRTableData, TVTableData, HRTableData, risHeader;
    LocalDate currentDate;

    public MonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthFragment newInstance(String param1, String param2) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        initDB();
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(View view) {
        risTableData = view.findViewById(R.id.risTableData);
        tempTableData = view.findViewById(R.id.tempTableData);
        O2TableData = view.findViewById(R.id.spO2TableData);
        RRTableData = view.findViewById(R.id.RRTableData);
        TVTableData = view.findViewById(R.id.MVTableData);
        HRTableData = view.findViewById(R.id.HRTableData);
        risHeader = view.findViewById(R.id.risHeader);


        currentDate = LocalDate.now();
        LocalDate weekToday = currentDate.minusWeeks(7).plusDays(1);
        Month m = currentDate.getMonth();
        String month = m.toString();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();
        int d = weekToday.getDayOfMonth();
        int year = currentDate.getYear();
        risHeader.setText("RIS data for " + month + " " + String.valueOf(year));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        init(view);

        return view;
    }

    private void initDB() {
        reff = FirebaseDatabase.getInstance().getReference().child("Patient");

        reff.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date;
                LocalDate now;
                LocalDate today = LocalDate.now();
                int risSum = 0;
                int HRSum = 0;
                int O2Sum = 0;
                int TVSum = 0;
                int RRSum = 0;
                double tempSum = 0.0;
                int count = 0;
                double[][] risArray = new double[7][7];
                double[][] RRArray = new double[7][7];
                double[][] O2Array = new double[7][7];
                double[][] HRArray = new double[7][7];
                double[][] TempArray = new double[7][7];
                LocalDate rollingMonth;
                LocalDate weekToday;
                LocalDate curMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

                Log.d(TAG, "onDataChange: test");


                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    date = snapshot2.child("currentTime").getValue(String.class);
                    date = date.substring(0, date.indexOf('T'));
                    now = LocalDate.parse(date);


                    if (today.getMonth().equals(now.getMonth())) {
                        risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
                        HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
                        O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
                        TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
                        RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
                        tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);

                        count++;

                        for (int i = 0; i < 5; i++) {
                            rollingMonth = curMonth.plusWeeks(i);
                            weekToday = rollingMonth.plusWeeks(1).minusDays(1);
                            Log.d(TAG, "onDataChange: Yeehaw " + rollingMonth.getMonth() + " " + rollingMonth.getDayOfMonth() + " " + weekToday.getMonthValue() + "/" + weekToday.getDayOfMonth());

                            if (rollingMonth.compareTo(now) <= 0 && now.compareTo(weekToday) < 0) {
                                risArray[i][0] = risArray[i][0] + snapshot2.child("ris").getValue(Integer.class);
                                RRArray[i][0] = RRArray[i][0] + snapshot2.child("rr").getValue(Integer.class);
                                O2Array[i][0] = O2Array[i][0] + snapshot2.child("spO2").getValue(Integer.class);
                                HRArray[i][0] = HRArray[i][0] + snapshot2.child("hr").getValue(Integer.class);
                                TempArray[i][0] = TempArray[i][0] + snapshot2.child("temp").getValue(Integer.class);
                                Log.d(TAG, "onDataChange: Yeehaw " + rollingMonth.getDayOfMonth() + " " + now.getMonthValue() + "/" + now.getDayOfMonth());
                                risArray[i][1]++;
                                RRArray[i][1]++;
                                O2Array[i][1]++;
                                HRArray[i][1]++;
                                TempArray[i][1]++;


//                            point = new DataPoint(time, snapshot2.child("ris").getValue(Integer.class));
//                            series.appendData(point, true, 1440);
                            }


                        }
                    }
                }

                if (count == 0) {

                    Toast.makeText(getActivity().getBaseContext(), "There is no data available for this month", Toast.LENGTH_SHORT).show();
                } else {
                    risTableData.setText(String.valueOf(risSum / count));
                    tempTableData.setText(String.valueOf(Precision.round(tempSum / count, 1)) + "°F");
                    O2TableData.setText(String.valueOf(O2Sum / count) + "%");
                    RRTableData.setText(String.valueOf(RRSum / count));
                    TVTableData.setText(String.valueOf(TVSum / count));
                    HRTableData.setText(String.valueOf(HRSum / count) + " bpm");

                    GraphView graph = (GraphView) getView().findViewById(R.id.graph);

                    graph.setTitle("RIS values by week");

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-50);
                    graph.getViewport().setMaxY(30);


                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(4);

                    //Get RR graph data
                    GraphView graphRR = (GraphView) getView().findViewById(R.id.graphRR);

                    graphRR.setTitle("RR values by week");

                    graphRR.getViewport().setYAxisBoundsManual(true);
                    graphRR.getViewport().setMinY(5);
                    graphRR.getViewport().setMaxY(20);


                    graphRR.getViewport().setXAxisBoundsManual(true);
                    graphRR.getViewport().setMinX(0);
                    graphRR.getViewport().setMaxX(4);

                    LineGraphSeries<DataPoint> seriesRR = new LineGraphSeries<>();
                    seriesRR.setThickness(7);
                    seriesRR.setTitle("Filtered data");

                    seriesRR.setColor(Color.BLUE);
                    DataPoint pointRR;

                    //Get spO2 graph data
                    GraphView graphspO2 = (GraphView) getView().findViewById(R.id.graphspO2);

                    graphspO2.setTitle("Oxygen percentage by week");

                    graphspO2.getViewport().setYAxisBoundsManual(true);
                    graphspO2.getViewport().setMinY(85);
                    graphspO2.getViewport().setMaxY(100);


                    graphspO2.getViewport().setXAxisBoundsManual(true);
                    graphspO2.getViewport().setMinX(0);
                    graphspO2.getViewport().setMaxX(4);

                    LineGraphSeries<DataPoint> seriesspO2 = new LineGraphSeries<>();
                    seriesspO2.setThickness(7);
                    seriesspO2.setTitle("Filtered data");

                    seriesRR.setColor(Color.MAGENTA);
                    DataPoint pointspO2;

                    //Get HR graph data
                    GraphView graphHR = (GraphView) getView().findViewById(R.id.graphHR);

                    graphHR.setTitle("Heart rate by week");

                    graphHR.getViewport().setYAxisBoundsManual(true);
                    graphHR.getViewport().setMinY(50);
                    graphHR.getViewport().setMaxY(180);


                    graphHR.getViewport().setXAxisBoundsManual(true);
                    graphHR.getViewport().setMinX(0);
                    graphHR.getViewport().setMaxX(4);

                    LineGraphSeries<DataPoint> seriesHR = new LineGraphSeries<>();
                    seriesHR.setThickness(7);
                    seriesHR.setTitle("Filtered data");

                    seriesHR.setColor(Color.RED);
                    DataPoint pointsHR;

                    //Get temperature graph data
                    GraphView graphTemp = (GraphView) getView().findViewById(R.id.graphTemp);

                    graphTemp.setTitle("Temperature (F) by hour");

                    graphTemp.getViewport().setYAxisBoundsManual(true);
                    graphTemp.getViewport().setMinY(97.0);
                    graphTemp.getViewport().setMaxY(104.0);


                    graphTemp.getViewport().setXAxisBoundsManual(true);
                    graphTemp.getViewport().setMinX(0);
                    graphTemp.getViewport().setMaxX(4);

                    LineGraphSeries<DataPoint> seriesTemp = new LineGraphSeries<>();
                    seriesTemp.setThickness(7);
                    seriesTemp.setTitle("Filtered data");

                    seriesTemp.setColor(Color.CYAN);
                    DataPoint pointsTemp;
//
//                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                    staticLabelsFormatter.setHorizontalLabels(new String[]{weekToday.getMonthValue() + "/" + weekToday.getDayOfMonth(), weekToday.plusDays(2).getMonthValue() + "/" + weekToday.plusDays(2).getDayOfMonth(), weekToday.plusDays(4).getMonthValue() + "/" + weekToday.plusDays(4).getDayOfMonth(), weekToday.plusDays(6).getMonthValue() + "/" + weekToday.plusDays(6).getDayOfMonth(), "Null"});
//                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    series.setTitle("Filtered data");
                    series.setColor(Color.GREEN);
                    series.setThickness(7);
                    DataPoint point;
                    LocalDateTime lt;
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                    int hour;
                    int minute;
                    double time;
                    String temp;


//                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
//                        date = snapshot2.child("currentTime").getValue(String.class);
//                        date = date.substring(0, date.indexOf('Z'));
//                        lt = LocalDateTime.parse(date, formatter);
//                        date = date.substring(0, date.indexOf('T'));
//                        now = LocalDate.parse(date);
//                        hour = lt.getHour();
//                        minute = lt.getMinute();



//                        if (minute >= 0 && minute < 10) {
//                            temp = hour + ".0" + minute;
//                        } else
//                            temp = hour + "." + minute;

//
//                        time = Double.parseDouble(temp);


                    int counter = 0;
                    for (int i = 0; i < 4; i++) {
                        Log.d(TAG, "onDataChange: dumb " + counter + " " + risArray[i][1]);
                        if(risArray[i][1] > 0) {
                            point = new DataPoint(counter, risArray[i][0] / risArray[i][1]);
                            pointRR = new DataPoint(counter, RRArray[i][0] / RRArray[i][1]);
                            pointspO2 = new DataPoint(counter, O2Array[i][0] / O2Array[i][1]);
                            pointsHR = new DataPoint(counter, HRArray[i][0] / HRArray[i][1]);
                            pointsTemp = new DataPoint(counter, TempArray[i][0] / TempArray[i][1]);

                            series.appendData(point, true, 1440);
                            seriesRR.appendData(pointRR, true, 1440);
                            seriesspO2.appendData(pointspO2, true, 1440);
                            seriesHR.appendData(pointsHR, true, 1440);
                            seriesTemp.appendData(pointsTemp, true, 1440);
                        }

                        counter++;

                    }

                    graph.addSeries(series);
                    graphRR.addSeries(seriesRR);
                    graphspO2.addSeries(seriesspO2);
                    graphHR.addSeries(seriesHR);
                    graphTemp.addSeries(seriesTemp);
                    Log.d(TAG, "risArray Week 1 sum: " + risArray[0][0]);
                    Log.d(TAG, "risArray Week 1 count: " + risArray[0][1]);
                    Log.d(TAG, "risArray Week 2 sum: " + risArray[1][0]);
                    Log.d(TAG, "risArray Week 2 count: " + risArray[1][1]);
                    Log.d(TAG, "risArray Day 3 sum: " + risArray[2][0]);
                    Log.d(TAG, "risArray Day 3 count: " + risArray[2][1]);
//


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
//                LocalDate today = LocalDate.now();
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
//                    if (now.equals(today)){
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
//                    Toast.makeText(RISArchiveActivity.this, "There is no data available for today", Toast.LENGTH_SHORT).show();
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
//                        if (now.equals(today)){
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
}