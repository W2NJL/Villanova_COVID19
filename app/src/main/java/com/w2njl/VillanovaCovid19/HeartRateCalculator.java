package com.w2njl.VillanovaCovid19;

import android.content.Context;
import android.content.Intent;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.w2njl.VillanovaCovid19.util.PrefData;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.text.NumberFormat;
import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class HeartRateCalculator extends AppCompatActivity {
    Spinner activitySp;
    int[] activity_img = {R.drawable.activity, R.drawable.activity, R.drawable.activity, R.drawable.activity};
    double age;
    Button chart;
    boolean check = false;
    EditText edAge;
    EditText edRhr;
    double factor1;
    double factor2;
    Spinner genderSp;
    int[] gender_img = {R.drawable.man, R.drawable.girl};
    public boolean male = true;
    double max;
    double mhr;
    double min;

    NumberFormat numberFormat;
    double rhr;
    String str_max;
    String str_mhr;
    String str_min;
    TextView tvActivity;
    TextView tvAge;
    TextView tvGender;
    TextView tvRhr;
    Toolbar toolbar;

    public class MyAdapter1 extends ArrayAdapter<String> {
        MyAdapter1(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {HeartRateCalculator.this.getResources().getString(R.string.male), HeartRateCalculator.this.getResources().getString(R.string.female)};
            View inflate = HeartRateCalculator.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(HeartRateCalculator.this.gender_img[i]);
            return inflate;
        }
    }

    public class MyAdapter4 extends ArrayAdapter<String> {
        MyAdapter4(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {HeartRateCalculator.this.getResources().getString(R.string.moderate), HeartRateCalculator.this.getResources().getString(R.string.little_diff), HeartRateCalculator.this.getResources().getString(R.string.moderately_diff), HeartRateCalculator.this.getResources().getString(R.string.hard)};
            View inflate = HeartRateCalculator.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(HeartRateCalculator.this.activity_img[i]);
            return inflate;
        }
    }

    int primaryColor;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.YellowTheme);

        setContentView(R.layout.heartrate);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.moderate), getResources().getString(R.string.little_diff), getResources().getString(R.string.moderately_diff), getResources().getString(R.string.hard)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));

        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);


        init();
        Button chart = findViewById(R.id.chart);


        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        chart.setBackground(getShapeDrawble(false, primaryColor));


        chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HeartRateCalculator.this.startActivity(new Intent(HeartRateCalculator.this, Chart_heart.class));
            }
        });
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));
        activitySp.setAdapter(new MyAdapter4(this, R.layout.spinner_down_blue, strArr2));


        activitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = HeartRateCalculator.this.activitySp.getSelectedItem().toString();
                if (obj.equals(HeartRateCalculator.this.getResources().getString(R.string.moderate))) {
                    HeartRateCalculator heartRateCalculator = HeartRateCalculator.this;
                    heartRateCalculator.factor1 = 0.6d;
                    heartRateCalculator.factor2 = 0.65d;
                } else if (obj.equals(HeartRateCalculator.this.getResources().getString(R.string.little_diff))) {
                    HeartRateCalculator heartRateCalculator2 = HeartRateCalculator.this;
                    heartRateCalculator2.factor1 = 0.65d;
                    heartRateCalculator2.factor2 = 0.7d;
                } else if (obj.equals(HeartRateCalculator.this.getResources().getString(R.string.moderately_diff))) {
                    HeartRateCalculator heartRateCalculator3 = HeartRateCalculator.this;
                    heartRateCalculator3.factor1 = 0.7d;
                    heartRateCalculator3.factor2 = 0.75d;
                } else {
                    HeartRateCalculator heartRateCalculator4 = HeartRateCalculator.this;
                    heartRateCalculator4.factor1 = 0.75d;
                    heartRateCalculator4.factor2 = 0.8d;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HeartRateCalculator.this.male = HeartRateCalculator.this.genderSp.getSelectedItem().toString().equals(HeartRateCalculator.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                HeartRateCalculator.this.edAge.setText(str);
                HeartRateCalculator.this.edRhr.setText(str);
                HeartRateCalculator.this.edAge.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    try {
                        HeartRateCalculator.this.age = Double.parseDouble(HeartRateCalculator.this.edAge.getText().toString());
                    } catch (NumberFormatException unused) {
                        HeartRateCalculator.this.check = true;
                    }
                    try {
                        HeartRateCalculator.this.rhr = Double.parseDouble(HeartRateCalculator.this.edRhr.getText().toString());
                    } catch (NumberFormatException unused2) {
                        HeartRateCalculator.this.check = true;
                    }
                    if (HeartRateCalculator.this.check) {
                        Toast.makeText(HeartRateCalculator.this.getApplicationContext(), HeartRateCalculator.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                        HeartRateCalculator.this.check = false;
                        return;
                    }
                    if (HeartRateCalculator.this.male) {
                        HeartRateCalculator.this.age *= 0.8d;
                        HeartRateCalculator heartRateCalculator = HeartRateCalculator.this;
                        heartRateCalculator.mhr = 214.0d - heartRateCalculator.age;
                    } else {
                        HeartRateCalculator.this.age *= 0.9d;
                        HeartRateCalculator heartRateCalculator2 = HeartRateCalculator.this;
                        heartRateCalculator2.mhr = 209.0d - heartRateCalculator2.age;
                    }
                    try {
                        PrefData.setAge(getApplicationContext(), Integer.parseInt(edAge.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    HeartRateCalculator heartRateCalculator3 = HeartRateCalculator.this;
                    heartRateCalculator3.str_mhr = heartRateCalculator3.numberFormat.format(HeartRateCalculator.this.mhr);
                    HeartRateCalculator.this.mhr -= HeartRateCalculator.this.rhr;
                    HeartRateCalculator heartRateCalculator4 = HeartRateCalculator.this;
                    heartRateCalculator4.min = (heartRateCalculator4.mhr * HeartRateCalculator.this.factor1) + HeartRateCalculator.this.rhr;
                    HeartRateCalculator heartRateCalculator5 = HeartRateCalculator.this;
                    heartRateCalculator5.max = (heartRateCalculator5.mhr * HeartRateCalculator.this.factor2) + HeartRateCalculator.this.rhr;
                    HeartRateCalculator heartRateCalculator6 = HeartRateCalculator.this;
                    heartRateCalculator6.str_min = heartRateCalculator6.numberFormat.format(HeartRateCalculator.this.min);
                    HeartRateCalculator heartRateCalculator7 = HeartRateCalculator.this;
                    heartRateCalculator7.str_max = heartRateCalculator7.numberFormat.format(HeartRateCalculator.this.max);
                    Intent intent = new Intent(HeartRateCalculator.this, Heartrateresult.class);
                    intent.putExtra(ConditionalUserProperty.VALUE, HeartRateCalculator.this.str_mhr);
                    intent.putExtra("value1", HeartRateCalculator.this.str_min);
                    intent.putExtra("value2", HeartRateCalculator.this.str_max);
                    HeartRateCalculator.this.startActivity(intent);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.heartrate));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edAge = findViewById(R.id.edAge);
        edRhr = findViewById(R.id.edRhr);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvRhr = findViewById(R.id.tvRhr);
        tvActivity = findViewById(R.id.tvActivity);
        genderSp = findViewById(R.id.genderSp);
        activitySp = findViewById(R.id.activitySp);
        chart = findViewById(R.id.chart);
        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.yellowcolorPrimary);

        ImageView img_age = findViewById(R.id.image_age);
        ImageView image_heart = findViewById(R.id.image_heart);
        ImageView img_itensity = findViewById(R.id.img_itensity);
        setThemeColor(img_age);
        setThemeColor(image_heart);
        setThemeColor(img_itensity);


    }

    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }
}
