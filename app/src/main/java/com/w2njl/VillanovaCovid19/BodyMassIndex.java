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

import android.widget.LinearLayout;
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

public class BodyMassIndex extends AppCompatActivity {
    double age;
    double bmi;
    Button chart;
    boolean check = false;
    public boolean cms = true;
    EditText edAge;
    EditText edHeight;
    EditText edHeight2;
    LinearLayout tvInputHeight2;
    EditText edWeight;
    Spinner genderSp;
    int[] gender_img = {R.drawable.man, R.drawable.girl};
    double height;
    double height2;
    Spinner heightSp;
    int[] height_img = {R.drawable.height, R.drawable.height};
    public boolean isKG = true;
    NumberFormat numberFormat;
    String str_bmi;
    TextView tvAge;
    TextView tvGender;
    TextView tvHeight;
    TextView tvWeight;
    TextView tvcm;
    TextView tvin;
    double weight;
    Spinner weightSp;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};
    Toolbar toolbar;

    public class MyAdapter1 extends ArrayAdapter<String> {
        MyAdapter1(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyMassIndex.this.getResources().getString(R.string.male), BodyMassIndex.this.getResources().getString(R.string.female)};
            View inflate = BodyMassIndex.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyMassIndex.this.gender_img[i]);
            return inflate;
        }
    }

    int primaryColor;

    public class MyAdapter2 extends ArrayAdapter<String> {
        MyAdapter2(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyMassIndex.this.getResources().getString(R.string.centimeters), BodyMassIndex.this.getResources().getString(R.string.feets)};
            View inflate = BodyMassIndex.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyMassIndex.this.height_img[i]);
            return inflate;
        }
    }

    public class MyAdapter3 extends ArrayAdapter<String> {
        MyAdapter3(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyMassIndex.this.getResources().getString(R.string.kilograms), BodyMassIndex.this.getResources().getString(R.string.pounds)};
            View inflate = BodyMassIndex.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyMassIndex.this.weight_img[i]);
            return inflate;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.bodymassindex);

        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr3 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        Button chart = findViewById(R.id.chart);
        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        chart.setBackground(getShapeDrawble(false, primaryColor));

        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        chart = findViewById(R.id.chart);
        chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BodyMassIndex.this.startActivity(new Intent(BodyMassIndex.this, Chart.class));
            }
        });
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);


        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));


        heightSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr2));

        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr3));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyMassIndex.this.heightSp.getSelectedItem().toString().equals(BodyMassIndex.this.getResources().getString(R.string.centimeters))) {
                    BodyMassIndex bodyMassIndex = BodyMassIndex.this;
                    bodyMassIndex.cms = true;
                    bodyMassIndex.edHeight.setEnabled(true);
                    BodyMassIndex.this.edHeight2.setEnabled(false);
                    BodyMassIndex.this.tvInputHeight2.setVisibility(View.GONE);
                    BodyMassIndex.this.tvcm.setText(BodyMassIndex.this.getResources().getString(R.string.cm));
                    BodyMassIndex.this.tvin.setText("");
                    return;
                }
                BodyMassIndex bodyMassIndex2 = BodyMassIndex.this;
                bodyMassIndex2.cms = false;
                bodyMassIndex2.edHeight.setEnabled(true);
                BodyMassIndex.this.edHeight2.setEnabled(true);
                BodyMassIndex.this.tvcm.setText(BodyMassIndex.this.getResources().getString(R.string.ft));
                BodyMassIndex.this.tvin.setText(BodyMassIndex.this.getResources().getString(R.string.in));
                BodyMassIndex.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BodyMassIndex.this.isKG = BodyMassIndex.this.weightSp.getSelectedItem().toString().equals(BodyMassIndex.this.getResources().getString(R.string.kilograms));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                BodyMassIndex.this.edHeight.setText(str);
                BodyMassIndex.this.edWeight.setText(str);
                BodyMassIndex.this.edAge.setText(str);
                BodyMassIndex.this.edHeight2.setText(str);
                BodyMassIndex.this.edAge.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    try {
                        BodyMassIndex.this.height = Double.parseDouble(BodyMassIndex.this.edHeight.getText().toString());
                    } catch (NumberFormatException unused) {
                        BodyMassIndex.this.check = true;
                    }
                    try {
                        BodyMassIndex.this.weight = Double.parseDouble(BodyMassIndex.this.edWeight.getText().toString());
                    } catch (NumberFormatException unused2) {
                        BodyMassIndex.this.check = true;
                    }
                    try {
                        BodyMassIndex.this.age = Double.parseDouble(BodyMassIndex.this.edAge.getText().toString());
                    } catch (NumberFormatException unused3) {
                        BodyMassIndex.this.check = true;
                    }
                    try {
                        BodyMassIndex.this.height2 = Double.parseDouble(BodyMassIndex.this.edHeight2.getText().toString());
                    } catch (NumberFormatException unused4) {
                        BodyMassIndex.this.height2 = 0.0d;
                    }
                    if (BodyMassIndex.this.check) {
                        Toast.makeText(BodyMassIndex.this.getApplicationContext(), BodyMassIndex.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                        BodyMassIndex.this.check = false;
                        return;
                    }
                    if (BodyMassIndex.this.cms) {
                        BodyMassIndex.this.height /= 100.0d;
                    } else {
                        BodyMassIndex.this.height *= 12.0d;
                        BodyMassIndex.this.height += BodyMassIndex.this.height2;
                        BodyMassIndex.this.height *= 0.0254d;
                    }

                    if (!BodyMassIndex.this.isKG) {
                        BodyMassIndex.this.weight *= 0.453592d;
                    }

                    try {
                        PrefData.setAge(getApplicationContext(), Integer.parseInt(edAge.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    BodyMassIndex bodyMassIndex2 = BodyMassIndex.this;
                    bodyMassIndex2.bmi = (bodyMassIndex2.weight / (BodyMassIndex.this.height * BodyMassIndex.this.height));
                    BodyMassIndex bodyMassIndex3 = BodyMassIndex.this;
                    bodyMassIndex3.str_bmi = bodyMassIndex3.numberFormat.format(BodyMassIndex.this.bmi);
                    Intent intent = new Intent(BodyMassIndex.this, Result.class);
                    intent.putExtra(ConditionalUserProperty.VALUE, BodyMassIndex.this.str_bmi);
                    BodyMassIndex.this.startActivity(intent);
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
        text_title.setText(getResources().getString(R.string.bmi_title));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edHeight = findViewById(R.id.edHeight);
        edWeight = findViewById(R.id.edWeight);
        edAge = findViewById(R.id.edAge);
        edHeight2 = findViewById(R.id.edHeight2);
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);
        edHeight2 = findViewById(R.id.edHeight2);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvcm = findViewById(R.id.tvcm);
        tvin = findViewById(R.id.tvin);
        weightSp = findViewById(R.id.weightSp);
        heightSp = findViewById(R.id.heightSp);
        genderSp = findViewById(R.id.genderSp);
        tvInputHeight2.setVisibility(View.GONE);

        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.bluecolorPrimary);
        ImageView img_age = findViewById(R.id.image_age);
        ImageView img_height = findViewById(R.id.img_height);
        ImageView img_centimeter = findViewById(R.id.img_centimeter);
        ImageView img_inch = findViewById(R.id.img_inch);
        ImageView img_weight = findViewById(R.id.img_weight);
        setThemeColor(img_age);
        setThemeColor(img_height);
        setThemeColor(img_centimeter);
        setThemeColor(img_weight);
        setThemeColor(img_inch);


    }

    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }

}
