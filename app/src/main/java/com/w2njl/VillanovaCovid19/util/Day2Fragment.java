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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Day2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Day2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference reff;
    private static CovidFeatures patient1 = new CovidFeatures();
    TextView risTableData, tempTableData, O2TableData, RRTableData, TVTableData, HRTableData, risHeader;
    LocalDate currentDate;
    boolean hasRun = false;

    public Day2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Day2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Day2Fragment newInstance(String param1, String param2) {
        Day2Fragment fragment = new Day2Fragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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


        currentDate = LocalDate.now().minusDays(1);
        Month m = currentDate.getMonth();
        String month = m.toString();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();
        int d = currentDate.getDayOfMonth();
        int year = currentDate.getYear();
        risHeader.setText("RIS data for " + month + " " + String.valueOf(d) + ", " + String.valueOf(year));
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
                LocalDate today =  LocalDate.now().minusDays(1);
                int risSum = 0;
                int HRSum = 0;
                int O2Sum = 0;
                int TVSum = 0;
                int RRSum = 0;
                int count = 0;
                double tempSum = 0.0;



                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    date = snapshot2.child("currentTime").getValue(String.class);
                    date = date.substring(0, date.indexOf('T'));
                    now = LocalDate.parse(date);

                    if (now.equals(today)) {
                        risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
                        HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
                        O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
                        TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
                        RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
                        tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
                       hasRun = true;
                       count++;

                    }
                }

                if (count==0) {

                   Toast.makeText(getActivity(), "There is no data available for today", Toast.LENGTH_SHORT).show();

                } else {
                    risTableData.setText(String.valueOf(risSum / count));
                    tempTableData.setText(String.valueOf(Precision.round(tempSum / count, 1)) + "°F");
                    O2TableData.setText(String.valueOf(O2Sum / count) + "%");
                    RRTableData.setText(String.valueOf(RRSum / count));
                    TVTableData.setText(String.valueOf(TVSum / count));
                    HRTableData.setText(String.valueOf(HRSum / count) + " bpm");

                    GraphView graph = (GraphView) getView().findViewById(R.id.graph);

                    graph.setTitle("RIS values by hour");

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-50);
                    graph.getViewport().setMaxY(30);


                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(24);

                    //Get RR graph data
                    GraphView graphRR = (GraphView) getView().findViewById(R.id.graphRR);

                    graphRR.setTitle("RR values by hour");

                    graphRR.getViewport().setYAxisBoundsManual(true);
                    graphRR.getViewport().setMinY(5);
                    graphRR.getViewport().setMaxY(20);


                    graphRR.getViewport().setXAxisBoundsManual(true);
                    graphRR.getViewport().setMinX(0);
                    graphRR.getViewport().setMaxX(24);

                    LineGraphSeries<DataPoint> seriesRR = new LineGraphSeries<>();
                    seriesRR.setThickness(7);
                    seriesRR.setTitle("Filtered data");

                    seriesRR.setColor(Color.BLUE);
                    DataPoint pointRR;

                    //Get spO2 graph data
                    GraphView graphspO2 = (GraphView) getView().findViewById(R.id.graphspO2);

                    graphspO2.setTitle("Oxygen percentage by hour");

                    graphspO2.getViewport().setYAxisBoundsManual(true);
                    graphspO2.getViewport().setMinY(85);
                    graphspO2.getViewport().setMaxY(100);


                    graphspO2.getViewport().setXAxisBoundsManual(true);
                    graphspO2.getViewport().setMinX(0);
                    graphspO2.getViewport().setMaxX(24);

                    LineGraphSeries<DataPoint> seriesspO2 = new LineGraphSeries<>();
                    seriesspO2.setThickness(7);
                    seriesspO2.setTitle("Filtered data");

                    seriesRR.setColor(Color.MAGENTA);
                    DataPoint pointspO2;

                    //Get HR graph data
                    GraphView graphHR = (GraphView) getView().findViewById(R.id.graphHR);

                    graphHR.setTitle("Heart rate by hour");

                    graphHR.getViewport().setYAxisBoundsManual(true);
                    graphHR.getViewport().setMinY(50);
                    graphHR.getViewport().setMaxY(180);


                    graphHR.getViewport().setXAxisBoundsManual(true);
                    graphHR.getViewport().setMinX(0);
                    graphHR.getViewport().setMaxX(24);

                    LineGraphSeries<DataPoint> seriesHR = new LineGraphSeries<>();
                    seriesHR.setThickness(7);
                    seriesHR.setTitle("Filtered data");

                    seriesHR.setColor(Color.RED);
                    DataPoint pointsHR;

                    //Get temperature graph data
                    GraphView graphTemp = (GraphView) getView().findViewById(R.id.graphTemp);

                    graphTemp.setTitle("Temperature (F) by hour");

                    graphTemp.getViewport().setYAxisBoundsManual(true);
                    graphTemp.getViewport().setMinY(96.0);
                    graphTemp.getViewport().setMaxY(103.0);


                    graphTemp.getViewport().setXAxisBoundsManual(true);
                    graphTemp.getViewport().setMinX(0);
                    graphTemp.getViewport().setMaxX(24);

                    LineGraphSeries<DataPoint> seriesTemp = new LineGraphSeries<>();
                    seriesTemp.setThickness(7);
                    seriesTemp.setTitle("Filtered data");

                    seriesTemp.setColor(Color.CYAN);
                    DataPoint pointsTemp;

//                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                    staticLabelsFormatter.setHorizontalLabels(new String[]{"12AM", "5AM", "10AM", "5PM", "10PM"});
//                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    series.setThickness(8);
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


                        if (minute >= 0 && minute < 10) {
                            temp = hour + ".0" + minute;
                        } else
                            temp = hour + "." + minute;


                        time = Double.parseDouble(temp);


                        if (now.equals(today)) {
                            risSum = risSum + snapshot2.child("ris").getValue(Integer.class);
                            HRSum = HRSum + snapshot2.child("hr").getValue(Integer.class);
                            O2Sum = O2Sum + snapshot2.child("spO2").getValue(Integer.class);
                            TVSum = TVSum + snapshot2.child("tv").getValue(Integer.class);
                            RRSum = RRSum + snapshot2.child("rr").getValue(Integer.class);
                            tempSum = tempSum + snapshot2.child("temp").getValue(Double.class);
                            Log.d(TAG, "onDataChange: " + time);


                            point = new DataPoint(time, snapshot2.child("ris").getValue(Integer.class));
                            series.appendData(point, true, 1440);

                            pointRR = new DataPoint(time, snapshot2.child("rr").getValue(Integer.class));
                            seriesRR.appendData(pointRR, true, 1440);

                            pointspO2 = new DataPoint(time, snapshot2.child("spO2").getValue(Integer.class));
                            seriesspO2.appendData(pointspO2, true, 1440);

                            pointsHR = new DataPoint(time, snapshot2.child("hr").getValue(Integer.class));
                            seriesHR.appendData(pointsHR, true, 1440);

                            pointsTemp = new DataPoint(time, snapshot2.child("temp").getValue(Integer.class));
                            seriesTemp.appendData(pointsTemp, true, 1440);
                        }


                    }

                    graph.addSeries(series);
                    graphRR.addSeries(seriesRR);
                    graphspO2.addSeries(seriesspO2);
                    graphHR.addSeries(seriesHR);
                    graphTemp.addSeries(seriesTemp);


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
