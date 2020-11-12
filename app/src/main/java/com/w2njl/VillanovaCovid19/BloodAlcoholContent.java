package com.w2njl.VillanovaCovid19;

import android.content.Context;
import android.content.Intent;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.text.NumberFormat;
import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class BloodAlcoholContent extends AppCompatActivity {
    double alcohollevel;
    double bac;
    boolean check = false;
    EditText edAlcoholLevel;
    EditText edTime;
    EditText edVolDrinked;
    EditText edWeight;
    int factor = 0;
    int factor2 = 0;
    Spinner genderSp;
    int[] gender_img = {R.drawable.man, R.drawable.girl};

    /* renamed from: kg */
    public boolean kgs = true;
    public boolean male = true;

    /* renamed from: nf */
    NumberFormat numberFormat;
    String str_bac;
    double time;
    Spinner timeSp;
    int[] time_img = {R.drawable.time, R.drawable.time, R.drawable.time};
    TextView tvAlcoholLevel;
    TextView tvGender;
    TextView tvPercent;
    TextView tvTime;
    TextView tvVolDrinked;
    TextView tvWeight;
    double volDrinked;
    int[] vol_img = {R.drawable.volume, R.drawable.volume, R.drawable.volume};
    Spinner volumeSp;
    double weight;
    Spinner weightSp;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};
    Toolbar toolbar;


    public class MyAdapter1 extends ArrayAdapter<String> {
        public MyAdapter1(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BloodAlcoholContent.this.getResources().getString(R.string.male), BloodAlcoholContent.this.getResources().getString(R.string.female)};
            View inflate = BloodAlcoholContent.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodAlcoholContent.this.gender_img[i]);
            return inflate;
        }
    }

    public class MyAdapter2 extends ArrayAdapter<String> {
        MyAdapter2(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BloodAlcoholContent.this.getResources().getString(R.string.ounces), BloodAlcoholContent.this.getResources().getString(R.string.ml), BloodAlcoholContent.this.getResources().getString(R.string.cup)};
            View inflate = BloodAlcoholContent.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodAlcoholContent.this.vol_img[i]);
            return inflate;
        }
    }

    public class MyAdapter3 extends ArrayAdapter<String> {
        MyAdapter3(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BloodAlcoholContent.this.getResources().getString(R.string.kilograms), BloodAlcoholContent.this.getResources().getString(R.string.pounds)};
            View inflate = BloodAlcoholContent.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodAlcoholContent.this.weight_img[i]);
            return inflate;
        }
    }

    public class MyAdapter4 extends ArrayAdapter<String> {
        MyAdapter4(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BloodAlcoholContent.this.getResources().getString(R.string.hour), BloodAlcoholContent.this.getResources().getString(R.string.minute), BloodAlcoholContent.this.getResources().getString(R.string.day)};
            View inflate = BloodAlcoholContent.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodAlcoholContent.this.time_img[i]);
            return inflate;
        }
    }

    int primaryColor;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.GrayTheme);
        setContentView(R.layout.bloodalcoholcalc);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr3 = {getResources().getString(R.string.hour), getResources().getString(R.string.minute), getResources().getString(R.string.day)};
        String[] strArr4 = {getResources().getString(R.string.ounces), getResources().getString(R.string.ml), getResources().getString(R.string.cup)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        Button chart = findViewById(R.id.chart);

        init();


        chart.setBackground(getShapeDrawble(false, primaryColor));
        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));


        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(3);
        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));
        volumeSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr4));
        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr2));
        timeSp.setAdapter(new MyAdapter4(this, R.layout.spinner_down_blue, strArr3));
        findViewById(R.id.chart).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BloodAlcoholContent.this.startActivity(new Intent(BloodAlcoholContent.this, Chart_Alcohol.class));
            }
        });


        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodAlcoholContent.this.kgs = BloodAlcoholContent.this.weightSp.getSelectedItem().toString().equals(BloodAlcoholContent.this.getResources().getString(R.string.kilograms));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodAlcoholContent.this.male = BloodAlcoholContent.this.genderSp.getSelectedItem().toString().equals(BloodAlcoholContent.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        volumeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = BloodAlcoholContent.this.volumeSp.getSelectedItem().toString();
                if (obj.equals(BloodAlcoholContent.this.getResources().getString(R.string.ounces))) {
                    BloodAlcoholContent.this.factor = 1;
                } else if (obj.equals(BloodAlcoholContent.this.getResources().getString(R.string.ml))) {
                    BloodAlcoholContent.this.factor = 2;
                } else {
                    BloodAlcoholContent.this.factor = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        timeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = BloodAlcoholContent.this.timeSp.getSelectedItem().toString();
                if (obj.equals(BloodAlcoholContent.this.getResources().getString(R.string.hour))) {
                    BloodAlcoholContent.this.factor2 = 1;
                } else if (obj.equals(BloodAlcoholContent.this.getResources().getString(R.string.minute))) {
                    BloodAlcoholContent.this.factor2 = 2;
                } else {
                    BloodAlcoholContent.this.factor2 = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                BloodAlcoholContent.this.edAlcoholLevel.setText(str);
                BloodAlcoholContent.this.edWeight.setText(str);
                BloodAlcoholContent.this.edTime.setText(str);
                BloodAlcoholContent.this.edAlcoholLevel.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    BloodAlcoholContent.this.alcohollevel = Double.parseDouble(BloodAlcoholContent.this.edAlcoholLevel.getText().toString());
                } catch (NumberFormatException unused) {
                    BloodAlcoholContent.this.check = true;
                }
                try {
                    BloodAlcoholContent.this.volDrinked = Double.parseDouble(BloodAlcoholContent.this.edVolDrinked.getText().toString());
                } catch (NumberFormatException unused2) {
                    BloodAlcoholContent.this.check = true;
                }
                try {
                    BloodAlcoholContent.this.weight = Double.parseDouble(BloodAlcoholContent.this.edWeight.getText().toString());
                } catch (NumberFormatException unused3) {
                    BloodAlcoholContent.this.check = true;
                }
                try {
                    BloodAlcoholContent.this.time = Double.parseDouble(BloodAlcoholContent.this.edTime.getText().toString());
                } catch (NumberFormatException unused4) {
                    BloodAlcoholContent.this.check = true;
                }
                if (BloodAlcoholContent.this.check) {
                    Toast.makeText(BloodAlcoholContent.this.getApplicationContext(), BloodAlcoholContent.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    BloodAlcoholContent.this.check = false;
                    return;
                }
                if (BloodAlcoholContent.this.kgs) {
                    BloodAlcoholContent.this.weight *= 2.20462d;
                } else {
                }
                if (BloodAlcoholContent.this.factor == 1) {
                } else if (BloodAlcoholContent.this.factor == 2) {
                    BloodAlcoholContent.this.volDrinked *= 0.033814d;
                } else {
                    BloodAlcoholContent.this.volDrinked *= 8.0d;
                }
                BloodAlcoholContent.this.alcohollevel /= 100.0d;
                BloodAlcoholContent.this.volDrinked *= BloodAlcoholContent.this.alcohollevel;
                if (BloodAlcoholContent.this.factor2 == 1) {
                } else if (BloodAlcoholContent.this.factor2 == 2) {
                    BloodAlcoholContent.this.time *= 0.0166d;
                } else {
                    BloodAlcoholContent.this.time *= 24.0d;
                }
                BloodAlcoholContent bloodAlcoholContent4 = BloodAlcoholContent.this;
                bloodAlcoholContent4.weight = 5.14d / bloodAlcoholContent4.weight;
                BloodAlcoholContent.this.time *= 0.015d;
                if (BloodAlcoholContent.this.male) {
                    BloodAlcoholContent bloodAlcoholContent5 = BloodAlcoholContent.this;
                    bloodAlcoholContent5.bac = ((bloodAlcoholContent5.volDrinked * BloodAlcoholContent.this.weight) * 0.73d) - BloodAlcoholContent.this.time;
                } else {
                    BloodAlcoholContent bloodAlcoholContent6 = BloodAlcoholContent.this;
                    bloodAlcoholContent6.bac = ((bloodAlcoholContent6.volDrinked * BloodAlcoholContent.this.weight) * 0.66d) - BloodAlcoholContent.this.time;
                }
                BloodAlcoholContent bloodAlcoholContent7 = BloodAlcoholContent.this;
                bloodAlcoholContent7.str_bac = bloodAlcoholContent7.numberFormat.format(BloodAlcoholContent.this.bac);
                Intent intent = new Intent(BloodAlcoholContent.this, BloodAlcoholResult.class);
                intent.putExtra(ConditionalUserProperty.VALUE, BloodAlcoholContent.this.str_bac);
                BloodAlcoholContent.this.startActivity(intent);
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bloodalcohol));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edTime = findViewById(R.id.edTime);
        edWeight = findViewById(R.id.edWeight);
        edAlcoholLevel = findViewById(R.id.edAlcoholLevel);
        edVolDrinked = findViewById(R.id.edVolDrinked);
        tvPercent = findViewById(R.id.tvPercent);
        tvWeight = findViewById(R.id.tvWeight);
        tvVolDrinked = findViewById(R.id.tvVolDrinked);
        tvGender = findViewById(R.id.tvGender);
        tvAlcoholLevel = findViewById(R.id.tvAlcoholLevel);
        tvTime = findViewById(R.id.tvTime);
        weightSp = findViewById(R.id.weightSp);
        volumeSp = findViewById(R.id.volumeSp);
        genderSp = findViewById(R.id.genderSp);
        timeSp = findViewById(R.id.timeSp);
        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.graycolorPrimary);


        ImageView img_gender = findViewById(R.id.img_gender);
        ImageView img_cocktail = findViewById(R.id.img_cocktail);
        ImageView img_drink = findViewById(R.id.img_drink);
        ImageView img_time = findViewById(R.id.img_time);
        ImageView img_weight = findViewById(R.id.img_weight);

        setThemeColor(img_cocktail);
        setThemeColor(img_gender);
        setThemeColor(img_drink);
        setThemeColor(img_time);
        setThemeColor(img_weight);


    }


    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }

}
