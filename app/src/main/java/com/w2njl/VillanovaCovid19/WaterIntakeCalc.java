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

public class WaterIntakeCalc extends AppCompatActivity {
    double age;
    boolean check = false;
    double dwi;
    int dwi_int;
    EditText edAge;
    EditText edWeight;
    public boolean isKG = true;
    NumberFormat numberFormat;
    String str_dwi;
    TextView tvAge;
    TextView tvWeight;
    double weight;
    Spinner weightSp;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};
    Toolbar toolbar;

    public class MyAdapter3 extends ArrayAdapter<String> {
        MyAdapter3(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {WaterIntakeCalc.this.getResources().getString(R.string.kilograms), WaterIntakeCalc.this.getResources().getString(R.string.pounds)};
            View inflate = WaterIntakeCalc.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(WaterIntakeCalc.this.weight_img[i]);
            return inflate;
        }
    }

    int primaryColor;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.OrangeTheme);
        setContentView(R.layout.waterintakecalc);
        String[] strArr = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        init();
        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));

        findViewById(R.id.chart).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WaterIntakeCalc.this.startActivity(new Intent(WaterIntakeCalc.this, Chart_Water.class));
            }
        });
        Button chart = findViewById(R.id.chart);


        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        chart.setBackground(getShapeDrawble(false, primaryColor));

        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr));


        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                WaterIntakeCalc.this.isKG = WaterIntakeCalc.this.weightSp.getSelectedItem().toString().equals(WaterIntakeCalc.this.getResources().getString(R.string.kilograms));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                WaterIntakeCalc.this.edWeight.setText(str);
                WaterIntakeCalc.this.edAge.setText(str);
                WaterIntakeCalc.this.edWeight.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    try {
                        WaterIntakeCalc.this.weight = Double.parseDouble(WaterIntakeCalc.this.edWeight.getText().toString());
                    } catch (NumberFormatException unused) {
                        WaterIntakeCalc.this.check = true;
                    }
                    try {
                        WaterIntakeCalc.this.age = Double.parseDouble(WaterIntakeCalc.this.edAge.getText().toString());
                    } catch (NumberFormatException unused2) {
                        WaterIntakeCalc.this.check = true;
                    }
                    if (WaterIntakeCalc.this.check) {
                        Toast.makeText(WaterIntakeCalc.this.getApplicationContext(), WaterIntakeCalc.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                        WaterIntakeCalc.this.check = false;
                        return;
                    }
                    if (!WaterIntakeCalc.this.isKG) {
                        WaterIntakeCalc.this.weight /= 2.2d;
                    }
                    if (WaterIntakeCalc.this.age <= 30.0d) {
                        WaterIntakeCalc waterIntakeCalc2 = WaterIntakeCalc.this;
                        waterIntakeCalc2.dwi = waterIntakeCalc2.weight * 40.0d;
                    } else if (WaterIntakeCalc.this.age > 55.0d) {
                        WaterIntakeCalc waterIntakeCalc3 = WaterIntakeCalc.this;
                        waterIntakeCalc3.dwi = waterIntakeCalc3.weight * 30.0d;
                    } else {
                        WaterIntakeCalc waterIntakeCalc4 = WaterIntakeCalc.this;
                        waterIntakeCalc4.dwi = waterIntakeCalc4.weight * 35.0d;
                    }

                    try {
                        PrefData.setAge(getApplicationContext(), Integer.parseInt(edAge.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    WaterIntakeCalc.this.dwi /= 28.3d;
                    WaterIntakeCalc.this.dwi /= 8.0d;
                    WaterIntakeCalc waterIntakeCalc5 = WaterIntakeCalc.this;
                    waterIntakeCalc5.dwi_int = (int) waterIntakeCalc5.dwi;
                    WaterIntakeCalc waterIntakeCalc6 = WaterIntakeCalc.this;
                    waterIntakeCalc6.str_dwi = String.valueOf(waterIntakeCalc6.dwi_int);
                    Intent intent = new Intent(WaterIntakeCalc.this, WaterIntakeResult.class);
                    intent.putExtra(ConditionalUserProperty.VALUE, WaterIntakeCalc.this.str_dwi);
                    WaterIntakeCalc.this.startActivity(intent);
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
        text_title.setText(getResources().getString(R.string.waterintake));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edWeight = findViewById(R.id.edWeight);
        edAge = findViewById(R.id.edAge);
        tvWeight = findViewById(R.id.tvWeight);
        tvAge = findViewById(R.id.tvAge);
        weightSp = findViewById(R.id.weightSp);
        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.orangecolorPrimary);

        ImageView img_age = findViewById(R.id.image_age);
        ImageView img_weight = findViewById(R.id.img_weight);

        setThemeColor(img_age);
        setThemeColor(img_weight);


    }


    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }
}
