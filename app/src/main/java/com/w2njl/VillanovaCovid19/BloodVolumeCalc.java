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

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;


import java.text.NumberFormat;
import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class BloodVolumeCalc extends AppCompatActivity {

    double bloodVol;
    boolean check = false;
    public boolean cms = true;
    EditText edHeight;
    EditText edHeight2;
    EditText edWeight;
    Spinner genderSp;
    int[] gender_img = {R.drawable.man, R.drawable.girl};
    double height;
    double height2;
    Spinner heightSp;
    int[] height_img = {R.drawable.height, R.drawable.height};

    public boolean isKG = true;
    LinearLayout tvInputHeight2;
    public boolean male = true;

    NumberFormat numberFormat;
    String str_bv;
    TextView tvGender;
    TextView tvHeight;
    TextView tvWeight;
    TextView tvcm;
    TextView tvin;
    double weight;
    EditText edAge;
    Spinner weightSp;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};
    Toolbar toolbar;
    LinearLayout lnrAge;

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
            String[] strArr = {BloodVolumeCalc.this.getResources().getString(R.string.male), BloodVolumeCalc.this.getResources().getString(R.string.female)};
            View inflate = BloodVolumeCalc.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodVolumeCalc.this.gender_img[i]);
            return inflate;
        }
    }

    public class MyAdapter2 extends ArrayAdapter<String> {
        MyAdapter2(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BloodVolumeCalc.this.getResources().getString(R.string.centimeters), BloodVolumeCalc.this.getResources().getString(R.string.feets)};
            View inflate = BloodVolumeCalc.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodVolumeCalc.this.height_img[i]);
            return inflate;
        }
    }

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
            String[] strArr = {BloodVolumeCalc.this.getResources().getString(R.string.kilograms), BloodVolumeCalc.this.getResources().getString(R.string.pounds)};
            View inflate = BloodVolumeCalc.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BloodVolumeCalc.this.weight_img[i]);
            return inflate;
        }
    }

    int primaryColor;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.CyanTheme);
        setContentView(R.layout.bloodvolume);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr3 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        init();
        lnrAge.setVisibility(View.GONE);
//        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));



        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));

        heightSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr2));

        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr3));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BloodVolumeCalc.this.heightSp.getSelectedItem().toString().equals(BloodVolumeCalc.this.getResources().getString(R.string.centimeters))) {
                    BloodVolumeCalc bloodVolumeCalc = BloodVolumeCalc.this;
                    bloodVolumeCalc.cms = true;
                    bloodVolumeCalc.edHeight.setEnabled(true);
                    BloodVolumeCalc.this.edHeight2.setEnabled(false);
                    BloodVolumeCalc.this.tvInputHeight2.setVisibility(View.GONE);
                    BloodVolumeCalc.this.tvcm.setText(BloodVolumeCalc.this.getResources().getString(R.string.cm));
                    BloodVolumeCalc.this.tvin.setText("");
                    tvInputHeight2.setVisibility(View.GONE);
                    return;
                }
                BloodVolumeCalc bloodVolumeCalc2 = BloodVolumeCalc.this;
                bloodVolumeCalc2.cms = false;
                tvInputHeight2.setVisibility(View.VISIBLE);
                bloodVolumeCalc2.edHeight.setEnabled(true);
                BloodVolumeCalc.this.edHeight2.setEnabled(true);
                BloodVolumeCalc.this.tvcm.setText(BloodVolumeCalc.this.getResources().getString(R.string.ft));
                BloodVolumeCalc.this.tvin.setText(BloodVolumeCalc.this.getResources().getString(R.string.in));
                BloodVolumeCalc.this.edHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodVolumeCalc.this.isKG = BloodVolumeCalc.this.weightSp.getSelectedItem().toString().equals(BloodVolumeCalc.this.getResources().getString(R.string.kilograms));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodVolumeCalc.this.male = BloodVolumeCalc.this.genderSp.getSelectedItem().toString().equals(BloodVolumeCalc.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                BloodVolumeCalc.this.edHeight.setText(str);
                BloodVolumeCalc.this.edWeight.setText(str);
                BloodVolumeCalc.this.edHeight2.setText(str);
                BloodVolumeCalc.this.edHeight.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    BloodVolumeCalc.this.height = Double.parseDouble(BloodVolumeCalc.this.edHeight.getText().toString());
                } catch (NumberFormatException unused) {
                    BloodVolumeCalc.this.check = true;
                }
                try {
                    BloodVolumeCalc.this.weight = Double.parseDouble(BloodVolumeCalc.this.edWeight.getText().toString());
                } catch (NumberFormatException unused2) {
                    BloodVolumeCalc.this.check = true;
                }
                try {
                    BloodVolumeCalc.this.height2 = Double.parseDouble(BloodVolumeCalc.this.edHeight2.getText().toString());
                } catch (NumberFormatException unused3) {
                    BloodVolumeCalc.this.height2 = 0.0d;
                }
                if (BloodVolumeCalc.this.check) {
                    Toast.makeText(BloodVolumeCalc.this.getApplicationContext(), BloodVolumeCalc.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    BloodVolumeCalc.this.check = false;
                    return;
                }
                if (BloodVolumeCalc.this.cms) {
                    BloodVolumeCalc.this.height /= 100.0d;
                } else {
                    BloodVolumeCalc.this.height *= 12.0d;
                    BloodVolumeCalc.this.height += BloodVolumeCalc.this.height2;
                    BloodVolumeCalc.this.height *= 2.54d;
                    BloodVolumeCalc.this.height /= 100.0d;
                }
                if (!BloodVolumeCalc.this.isKG) {
                    BloodVolumeCalc.this.weight *= 0.453592d;
                }
                if (BloodVolumeCalc.this.male) {
                    BloodVolumeCalc.this.weight *= 0.03219d;
                    BloodVolumeCalc.this.weight += 0.6041d;
                    BloodVolumeCalc bloodVolumeCalc2 = BloodVolumeCalc.this;
                    bloodVolumeCalc2.height = bloodVolumeCalc2.height * BloodVolumeCalc.this.height * BloodVolumeCalc.this.height;
                    BloodVolumeCalc.this.height *= 0.3669d;
                } else {
                    BloodVolumeCalc.this.weight *= 0.03308d;
                    BloodVolumeCalc.this.weight += 0.1833d;
                    BloodVolumeCalc bloodVolumeCalc3 = BloodVolumeCalc.this;
                    bloodVolumeCalc3.height = bloodVolumeCalc3.height * BloodVolumeCalc.this.height * BloodVolumeCalc.this.height;
                    BloodVolumeCalc.this.height *= 0.3561d;
                }
                BloodVolumeCalc bloodVolumeCalc4 = BloodVolumeCalc.this;
                bloodVolumeCalc4.bloodVol = bloodVolumeCalc4.weight + BloodVolumeCalc.this.height;
                BloodVolumeCalc bloodVolumeCalc5 = BloodVolumeCalc.this;
                bloodVolumeCalc5.str_bv = bloodVolumeCalc5.numberFormat.format(BloodVolumeCalc.this.bloodVol);
                Intent intent = new Intent(BloodVolumeCalc.this, BloodVolResult.class);
                intent.putExtra(ConditionalUserProperty.VALUE, BloodVolumeCalc.this.str_bv);
                BloodVolumeCalc.this.startActivity(intent);
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bloodvol));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        lnrAge = findViewById(R.id.lnrAge);
        edAge = findViewById(R.id.edAge);
        edHeight = findViewById(R.id.edHeight);
        edWeight = findViewById(R.id.edWeight);
        edHeight2 = findViewById(R.id.edHeight2);
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvGender = findViewById(R.id.tvGender);
        tvcm = findViewById(R.id.tvcm);
        tvin = findViewById(R.id.tvin);
        weightSp = findViewById(R.id.weightSp);
        heightSp = findViewById(R.id.heightSp);
        genderSp = findViewById(R.id.genderSp);


        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.cyancolorPrimary);
        ImageView img_age = findViewById(R.id.image_age);
        ImageView img_height = findViewById(R.id.img_height);
        ImageView img_centimeter = findViewById(R.id.img_centimeter);
        ImageView img_inch = findViewById(R.id.img_inch);
        ImageView img_weight = findViewById(R.id.img_weight);
        setThemeColor(img_age);
        setThemeColor(img_height);
        setThemeColor(img_centimeter);
        setThemeColor(img_inch);
        setThemeColor(img_weight);

    }


    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }

}
