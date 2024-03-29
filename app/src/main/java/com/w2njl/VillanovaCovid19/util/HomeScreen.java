package com.w2njl.VillanovaCovid19.util;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.w2njl.VillanovaCovid19.ConnectionDetector;
import com.w2njl.VillanovaCovid19.MainActivity;
import com.w2njl.VillanovaCovid19.R;
import com.w2njl.VillanovaCovid19.adapters.LazyAdapter;
import com.w2njl.VillanovaCovid19.models.RowItem;

import org.apache.commons.math3.util.Precision;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.w2njl.VillanovaCovid19.CovidService.finished;
import static com.w2njl.VillanovaCovid19.CovidService.running;
import static com.w2njl.VillanovaCovid19.util.RISActivity.reff;

public class HomeScreen extends AppCompatActivity implements LazyAdapter.clickInterface {

    static {
        System.loadLibrary("native-lib17");
    }

    private static final String ratings_fileName = "ratingAgain";
    private static Integer[] images = {R.drawable.idealweight, R.drawable.bmi};
    private static Integer[] themes = {R.style.OrangeTheme, R.style.BlueTheme};
    private static Integer[] arrows = {R.drawable.arrow_orange, R.drawable.arrow_blue};
    private static Integer[] colors = {R.color.orangecolorPrimary, R.color.bluecolorPrimary, R.color.yellowcolorPrimary, R.color.cyancolorPrimary, R.color.pinkcolorPrimary, R.color.graycolorPrimary, R.color.orangecolorPrimary
            , R.color.bluecolorPrimary, R.color.graycolorPrimary, R.color.pinkcolorPrimary, R.color.yellowcolorPrimary,
            R.color.cyancolorPrimary, R.color.orangecolorPrimary
    };
    SharedPreferences ratePrefs;
    List<RowItem> rowItems;
    LazyAdapter lazyAdapter;
    RecyclerView listView;
    ImageView timeOfDayImg;
    Toolbar toolbar;
    TextView greeting, RISinfo;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private int RIS;
    Query lastQuery;
    private static CovidFeatures patient1 = new CovidFeatures();

    ConnectionDetector cd;
    boolean interstitialCanceled;
    InterstitialAd mInterstitialAd;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);


        initDB();
        init();
        initCovid();
                ratePrefs = getSharedPreferences(ratings_fileName, 0);
//        ((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());
        listView = findViewById(R.id.myList);
        rowItems = new ArrayList<>();
        String[] strArr = {getResources().getString(R.string.villanova), getResources().getString(R.string.health)};

        String[] strArr2 = {getResources().getString(R.string.villanova_desc), getResources().getString(R.string.health_desc)};
        for (int i = 0; i < strArr.length; i++) {
            this.rowItems.add(new RowItem(images[i], strArr[i], strArr2[i], themes[i], arrows[i], colors[i]));
        }
        lazyAdapter = new LazyAdapter(this.rowItems, this);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        listView.setAdapter(lazyAdapter);
        lazyAdapter.setListeners(this);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String age = userProfile.age;
                    LocalDateTime now = LocalDateTime.now();
                    int timeOfDay = now.getHour();

                    if(timeOfDay >= 5 && timeOfDay < 12){
                        timeOfDayImg.setImageResource(R.drawable.sunshine);
                        greeting.setText("Good Morning, " + fullName + "!");
                    }else if(timeOfDay >= 12 && timeOfDay < 16){
                        timeOfDayImg.setImageResource(R.drawable.sunshine);
                        greeting.setText("Good Afternoon, " + fullName + "!");
                    }else if(timeOfDay >= 16 && timeOfDay < 24){
                        timeOfDayImg.setImageResource(R.drawable.night);
                        greeting.setText("Good Evening, " + fullName + "!");
                    }else if(timeOfDay >= 0 && timeOfDay < 5){
                        timeOfDayImg.setImageResource(R.drawable.night);
                        greeting.setText("Good Evening, " + fullName + "!");
                    }

                    RISinfo.setText("Your last RIS score was " + patient1.getDanger());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeScreen.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        timeOfDayImg = findViewById(R.id.timeOfDay);
        toolbar = findViewById(R.id.toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        greeting = findViewById(R.id.greeting);
        RISinfo = findViewById(R.id.risInfo);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.app_name));

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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="));
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

    public void onBackPressed() {
        finishAffinity();
    }


    @Override
    public void onRecItemClick(View view, int i) {
        switch (i) {
            case 0:
//                ProgressDialog nDialog;
//        nDialog = new ProgressDialog(HomeScreen.this);
//        nDialog.setMessage("Loading..");
//        nDialog.setTitle("Get Data");
//        nDialog.setIndeterminate(false);
//        nDialog.setCancelable(true);
//        nDialog.show();
                if (!interstitialCanceled) {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();

                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(RISActivity.class);
                            }
                        });
                    } else {
                        passIntent(RISActivity.class);
                    }
                }
//                passIntent(IdealWeightCalc.class);
                return;
            case 1:
                if (!interstitialCanceled) {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();

                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(MainActivity.class);
                            }
                        });
                    } else {
                        passIntent(MainActivity.class);
                    }
                }
                return;
            default:
        }
    }

    public void passIntent(Class aClass) {
//        if (!interstitialCanceled) {
//            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//
//                mInterstitialAd.setAdListener(new AdListener() {
//                    public void onAdClosed() {
////                    ContinueIntent();
//                        startActivity(new Intent(this, aClass));
//                    }
//                });
//            } else {
//                ContinueIntent();
//            }
//        }

        startActivity(new Intent(this, aClass));
    }

    @Override
    protected void onPause() {
        mInterstitialAd = null;
        interstitialCanceled = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        interstitialCanceled = false;
        if (getResources().getString(R.string.ADS_VISIBILITY).equals("YES")) {
            CallNewInsertial();
        }
        super.onResume();
    }

    private void initCovid(){
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    int HR = child.child("hr").getValue(Integer.class);
                    double temp = child.child("temp").getValue(Double.class);
                    int spO2 = child.child("spO2").getValue(Integer.class);
                    int TV = child.child("tv").getValue(Integer.class);
                    int RR = child.child("rr").getValue(Integer.class);



                    temp = Precision.round(temp, 1);
                    RIS = child.child("ris").getValue(Integer.class);


                    String date = child.child("currentTime").getValue().toString();


                    patient1 = new CovidFeatures(getDanger(RIS), 1, RIS, HR, spO2, temp,
                            TV, RR, date);


                    lastQuery.removeEventListener(this);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lastQuery.removeEventListener(this);
            }
        });
    }

    private void initDB(){
        if(reff == null){
           FirebaseDatabase.getInstance().setPersistenceEnabled(true);
           reff = FirebaseDatabase.getInstance().getReference().child("Patient");
        }
        lastQuery = reff.orderByKey().limitToLast(1);

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void CallNewInsertial() {
        cd = new ConnectionDetector(HomeScreen.this);
        if (cd.isConnectingToInternet()) {
            mInterstitialAd = new InterstitialAd(HomeScreen.this);
            mInterstitialAd.setAdUnitId(getString(R.string.interestial_ad_id));
            requestNewInterstitial();
//            mInterstitialAd.setAdListener(new AdListener() {
//                public void onAdClosed() {
////                    ContinueIntent();
//                    startActivity(new Intent(this, aClass));
//                }
//            });

        }
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
}
