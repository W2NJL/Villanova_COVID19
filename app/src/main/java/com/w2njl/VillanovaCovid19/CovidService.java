package com.w2njl.VillanovaCovid19;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.w2njl.VillanovaCovid19.util.CovidFeatures;
import com.w2njl.VillanovaCovid19.util.RISActivity;
import com.w2njl.VillanovaCovid19.util.RISArchiveActivity;
import com.w2njl.VillanovaCovid19.util.TestActivity;

import org.apache.commons.math3.util.Precision;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import static com.w2njl.VillanovaCovid19.App.CHANNEL_1_ID;
import static com.w2njl.VillanovaCovid19.App.CHANNEL_ID;
import static com.w2njl.VillanovaCovid19.CovidRisk.addArray;
import static com.w2njl.VillanovaCovid19.util.SettingsActivity.SHARED_PREFS;
import static com.w2njl.VillanovaCovid19.util.SettingsActivity.SHARED_TEXT;


public class CovidService extends Service {

    private static final String TAG = "ExampleJobService";
    public static boolean running;
    public static long serviceTime;
    public static long serviceInterval;
    public static final String SHARED_PREFS_SMS = "sharedPrefSMS";
    public static final String SHARED_TEXT_SMS = "SMS";
    public static final String SHARED_PREFS_PHONE = "sharedPrefSMSPHONE";
    public static final String SHARED_TEXT_PHONE = "PHONE";
    String ACTION_STOP_SERVICE= "STOP";
    Bitmap artwork;
    SharedPreferences sharedPreferences, sharedSMS, sharedPhone;
    boolean alertingSMS;
    String tel = null;

    private Thread thread;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Patient");
    private double[] unboxed;
    private double[] unboxed2;
    //    boolean thread1Start;
//    boolean thread2Start;
//    private Thread thread1;
//    private Thread thread2;
    private double[] zz;

    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        running = false;
        Log.d(TAG, "onDestroy: Complete");

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String input = intent.getStringExtra("inputExtra");

        loadData();

        Intent notificationIntent = new Intent(this, RISActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (ACTION_STOP_SERVICE.equals(intent.getAction())) {
            Log.d(TAG,"called to cancel service");
            stopSelf();
        }

        Intent stopSelf = new Intent(this, CovidService.class);
        stopSelf.setAction(ACTION_STOP_SERVICE);

        PendingIntent pStopSelf = PendingIntent
                .getService(this, 0, stopSelf
                        ,PendingIntent.FLAG_CANCEL_CURRENT);  // That you should change this part in your code

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Villanova COVID-19 Monitoring")
                .setContentText(getString(R.string.covidmonitor))
                .setSmallIcon(R.drawable.ic_stat_1200px_villanova_wildcats_logo_svg)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.parseColor("#3AB8E5"))
                .addAction(R.drawable.ic_cocktail,"Stop Monitoring", pStopSelf)
                .build();








        startForeground(1, notification);

        thread=new Thread(new Runnable(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run()
            {

                running = true;
                // TODO: Implement this method
                while(running)
                {

                    generateCovid();

                    Log.d(TAG, "Job finished");
                    try
                    {
                        Thread.sleep((long)serviceInterval);
                    }
                    catch (InterruptedException e)
                    {}
                }


            }
        });
        thread.start();



        return START_NOT_STICKY;

    }

    public void loadData() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        serviceInterval = sharedPreferences.getLong(SHARED_TEXT, 60000);

        sharedSMS = getSharedPreferences(SHARED_PREFS_SMS, MODE_PRIVATE);
        alertingSMS = sharedSMS.getBoolean(SHARED_TEXT_SMS, false);

        sharedPhone = getSharedPreferences(SHARED_PREFS_PHONE, MODE_PRIVATE);
        tel = sharedPhone.getString(SHARED_TEXT_PHONE, null);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateCovid() {

        loadfromC();
        CovidFeatures patient1 = initCovidData();
        reff.push().setValue(patient1);

        Intent notificationIntent = new Intent(this, RISActivity.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);





        if (patient1.getRIS() > 0) {
            Notification notificationAlert = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                    .setContentTitle("COVID-19 risk is elevated!")
                    .setContentText("Your Covid RIS score is " + patient1.getRIS())
                    .setSmallIcon(R.drawable.ic_stat_1200px_villanova_wildcats_logo_svg)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_EVENT)
                    .setLargeIcon(artwork)
                    .setColor(Color.RED)
                    .addAction(R.drawable.ic_cocktail,"Tap for more information", pendingIntent)
                    .build();

            startForeground(2, notificationAlert);


            if(alertingSMS == true && tel != null) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(tel, null, "My Covid risk is\n"+patient1.getDanger() ,  null, null);
            }
        }

        Date date = new Date();
        serviceTime = date.getTime();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private CovidFeatures initCovidData() {
        LocalDateTime now = LocalDateTime.now();
        String date = DateTimeFormatter.ISO_INSTANT.format(now.toInstant(ZoneOffset.UTC));
        ArrayList<CovidFeatures> covids = new ArrayList<>();
        Random random = new Random();
        int HR = random.nextInt(170 - 70) + 70;
        double temp = 96.4 + (102.5 - 96.4) * random.nextDouble();
        int spO2 = random.nextInt(99 - 87) + 87;
        int TV = random.nextInt(10000 - 5000) + 5000;
        int RR = (int) ((int) zz[0] + zz[1]);


        temp = Precision.round(temp, 1);

        int RIS = genRIS(getO2risk(spO2), getHRrisk(HR), getTempRisk(temp), getMVrisk(RR, TV));



        artwork = findMax(getO2risk(spO2), getHRrisk(HR), getTempRisk(temp), getMVrisk(RR, TV));




        CovidFeatures patient1 = new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
                TV, RR, date);

        return patient1;
    }

    private int getMVrisk(int RR, int TV){

        int risk = RR * TV;

        if (risk >= 10000 && risk < 12750)
            return 2;
        else if (risk >= 12750)
            return 5;
        else if (risk >= 4950 && risk <6000)
            return 8;
        else if (risk >=6000 && risk <8000)
            return 4;
        else if (risk >=8000 && risk <10000)
            return 7;
        else if (risk <4950)
            return 10;

        return 0;
    }

    private int getTempRisk(double temp){


        if (temp <= 95.0 || temp>=100.0)
            return 8;

        else return -8;
    }

    private int getHRrisk(int HR){
        if (HR >= 100)
            return 5;

        else return -5;
    }

    private int getO2risk(int O2){
        if (O2 < 90)
            return 10;
        else return -10;
    }

    private int genRIS(int O2risk, int HRrisk, int tempRisk, int MVrisk){
        return O2risk + HRrisk + tempRisk + MVrisk;
    }


    private String getDanger(int RIS){


        if (RIS >0 && RIS <50)
            return "ELEVATED";

        if (RIS >=50 && RIS <100)
            return "MODERATE";

        if (RIS >=100)
            return "HIGH";

        return "LOW";
    }

    private Bitmap findMax(int... vals) {
        int max = Integer.MIN_VALUE;
        int count = 0;
        int maxCount = 0;

        for (int d : vals) {
            count++;
            if (d > max){ max = d;
            maxCount = count;}
        }

        if (maxCount == 1){
        return BitmapFactory.decodeResource(getResources(), R.drawable.o2);}
        else if (maxCount == 2){
            return BitmapFactory.decodeResource(getResources(), R.drawable.heartrate);
        }
        else if (maxCount == 3){
            return BitmapFactory.decodeResource(getResources(), R.drawable.thermometer);
        }
        else {
            return BitmapFactory.decodeResource(getResources(), R.drawable.oxygen);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadfromC(){

        //Load in WAV file
        Log.d(TAG, "loadfromC:  Start");
        WavFile j = new WavFile("32mm.wav", this);

        //Convert to ArrayList
        ArrayList<Double> jz = j.getSamples();


        Double[] dblArray = new Double[jz.size()];

        //Convert to Double Array
        dblArray = jz.toArray(dblArray);

        unboxed = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();
        unboxed2 = Stream.of(dblArray).mapToDouble(Double::doubleValue).toArray();

        //Store some features
        zz = new double[2];

        //Subtract the mean from the unfiltered data
        double mean = 0;
        for (int i=0; i<2890080; i++) {
            mean = mean + unboxed[i];
        }

        mean = mean/2890080;

        for(int z=0; z<2890080; z++){
            unboxed[z] = unboxed[z] - mean;}


        //Call C function
        addArray(unboxed, zz);

        Log.d(TAG, "loadfromC:  In elements: " + zz[0]);
        Log.d(TAG, "loadfromC:  Out elements: " + zz[1]);


//
//        double max = Arrays.stream(unboxed).max().getAsDouble();
//        double min = Arrays.stream(unboxed).min().getAsDouble();
    }
}
