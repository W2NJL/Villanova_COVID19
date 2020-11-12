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

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class BodyFatMale extends AppCompatActivity {

    double bf;
    boolean check = false;
    public boolean cms = true;
    public boolean cms2 = true;
    public boolean cms3 = true;
    EditText edHeight;
    EditText edHeight2;
    LinearLayout tvInputHeight2;
    EditText edNeck;
    EditText edWaist;
    EditText edWeight;
    double height;
    double height2;
    Spinner heightSp;
    int[] height_img = {R.drawable.height, R.drawable.height};

    public boolean isKG = true;
    double neck;
    Spinner neckSp;
    int[] neck_img = {R.drawable.height, R.drawable.height};

    NumberFormat numberFormat;
    double result1;
    double result2;
    String str_assess;
    String str_bf;
    TextView tvHeight;
    TextView tvNeck;
    TextView tvWaist;
    TextView tvWeight;
    TextView tvcm;
    TextView tvin;
    double waist;
    Spinner waistSp;
    int[] waist_img = {R.drawable.height, R.drawable.height};
    double weight;
    Spinner weightSp;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};
    Toolbar toolbar;

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
            String[] strArr = {BodyFatMale.this.getResources().getString(R.string.centimeters), BodyFatMale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatMale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatMale.this.height_img[i]);
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
            String[] strArr = {BodyFatMale.this.getResources().getString(R.string.kilograms), BodyFatMale.this.getResources().getString(R.string.pounds)};
            View inflate = BodyFatMale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatMale.this.weight_img[i]);
            return inflate;
        }
    }

    public class MyAdapter4 extends ArrayAdapter<String> {
        MyAdapter4(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatMale.this.getResources().getString(R.string.centimeters), BodyFatMale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatMale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatMale.this.waist_img[i]);
            return inflate;
        }
    }

    public class MyAdapter5 extends ArrayAdapter<String> {
        MyAdapter5(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, @Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatMale.this.getResources().getString(R.string.centimeters), BodyFatMale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatMale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatMale.this.neck_img[i]);
            return inflate;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.bodyfatmale);
        String[] strArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr2 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr3 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.inches)};
        String[] strArr4 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.inches)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        init();

        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        heightSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr));
        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr2));
        waistSp.setAdapter(new MyAdapter4(this, R.layout.spinner_down_blue, strArr3));
        neckSp.setAdapter(new MyAdapter5(this, R.layout.spinner_down_blue, strArr4));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatMale.this.heightSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatMale bodyFatMale = BodyFatMale.this;
                    bodyFatMale.cms = true;
                    bodyFatMale.edHeight.setEnabled(true);
                    BodyFatMale.this.edHeight2.setEnabled(false);
                    BodyFatMale.this.tvInputHeight2.setVisibility(View.GONE);
                    BodyFatMale.this.tvcm.setText(BodyFatMale.this.getResources().getString(R.string.cm));
                    BodyFatMale.this.tvin.setText("");
                    return;
                }
                BodyFatMale bodyFatMale2 = BodyFatMale.this;
                bodyFatMale2.cms = false;
                bodyFatMale2.edHeight.setEnabled(true);
                BodyFatMale.this.edHeight2.setEnabled(true);
                BodyFatMale.this.tvcm.setText(BodyFatMale.this.getResources().getString(R.string.ft));
                BodyFatMale.this.tvin.setText(BodyFatMale.this.getResources().getString(R.string.in));
                BodyFatMale.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        waistSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (BodyFatMale.this.waistSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatMale bodyFatMale = BodyFatMale.this;
                    bodyFatMale.cms2 = true;
                    return;
                }
                BodyFatMale bodyFatMale2 = BodyFatMale.this;
                bodyFatMale2.cms2 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        neckSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatMale.this.neckSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatMale bodyFatMale = BodyFatMale.this;
                    bodyFatMale.cms3 = true;
                    return;
                }
                BodyFatMale bodyFatMale2 = BodyFatMale.this;
                bodyFatMale2.cms3 = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BodyFatMale.this.isKG = BodyFatMale.this.weightSp.getSelectedItem().toString().equals(BodyFatMale.this.getResources().getString(R.string.kilograms));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                BodyFatMale.this.edHeight.setText(str);
                BodyFatMale.this.edHeight2.setText(str);
                BodyFatMale.this.edWeight.setText(str);
                BodyFatMale.this.edWaist.setText(str);
                BodyFatMale.this.edNeck.setText(str);
                BodyFatMale.this.edHeight.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    BodyFatMale.this.height = Double.parseDouble(BodyFatMale.this.edHeight.getText().toString());
                } catch (NumberFormatException unused) {
                    BodyFatMale.this.check = true;
                }
                try {
                    BodyFatMale.this.height2 = Double.parseDouble(BodyFatMale.this.edHeight2.getText().toString());
                } catch (NumberFormatException unused2) {
                    BodyFatMale.this.height2 = 0.0d;
                }
                try {
                    BodyFatMale.this.weight = Double.parseDouble(BodyFatMale.this.edWeight.getText().toString());
                } catch (NumberFormatException unused3) {
                    BodyFatMale.this.check = true;
                }
                try {
                    BodyFatMale.this.waist = Double.parseDouble(BodyFatMale.this.edWaist.getText().toString());
                } catch (NumberFormatException unused4) {
                    BodyFatMale.this.check = true;
                }
                try {
                    BodyFatMale.this.neck = Double.parseDouble(BodyFatMale.this.edNeck.getText().toString());
                } catch (NumberFormatException unused5) {
                    BodyFatMale.this.check = true;
                }
                if (BodyFatMale.this.check) {
                    Toast.makeText(BodyFatMale.this.getApplicationContext(), BodyFatMale.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    BodyFatMale.this.check = false;
                    return;
                }
                if (BodyFatMale.this.isKG) {
                    BodyFatMale.this.weight *= 2.20462d;
                }
                if (BodyFatMale.this.cms) {
                    BodyFatMale.this.height *= 0.393701d;
                } else {
                    BodyFatMale.this.height *= 12.0d;
                    BodyFatMale.this.height += BodyFatMale.this.height2;
                }
                if (BodyFatMale.this.cms2) {
                    BodyFatMale.this.waist *= 0.393701d;
                }
                if (BodyFatMale.this.cms3) {
                    BodyFatMale.this.neck *= 0.393701d;
                }
                BodyFatMale bodyFatMale4 = BodyFatMale.this;
                bodyFatMale4.result1 = (bodyFatMale4.weight * 1.082d) + 94.42d;
                BodyFatMale bodyFatMale5 = BodyFatMale.this;
                bodyFatMale5.result2 = bodyFatMale5.result1 - (BodyFatMale.this.waist * 4.15d);
                BodyFatMale bodyFatMale6 = BodyFatMale.this;
                bodyFatMale6.bf = ((bodyFatMale6.weight - BodyFatMale.this.result2) * 100.0d) / BodyFatMale.this.weight;
                if (BodyFatMale.this.bf >= 2.0d && BodyFatMale.this.bf <= 5.0d) {
                    BodyFatMale bodyFatMale7 = BodyFatMale.this;
                    bodyFatMale7.str_assess = bodyFatMale7.getResources().getString(R.string.essential);
                } else if (BodyFatMale.this.bf >= 6.0d && BodyFatMale.this.bf <= 13.0d) {
                    BodyFatMale bodyFatMale8 = BodyFatMale.this;
                    bodyFatMale8.str_assess = bodyFatMale8.getResources().getString(R.string.typicalathlete);
                } else if (BodyFatMale.this.bf >= 14.0d && BodyFatMale.this.bf <= 17.0d) {
                    BodyFatMale bodyFatMale9 = BodyFatMale.this;
                    bodyFatMale9.str_assess = bodyFatMale9.getResources().getString(R.string.physicallyfit);
                } else if (BodyFatMale.this.bf < 18.0d || BodyFatMale.this.bf > 24.0d) {
                    BodyFatMale bodyFatMale10 = BodyFatMale.this;
                    bodyFatMale10.str_assess = bodyFatMale10.getResources().getString(R.string.obese);
                } else {
                    BodyFatMale bodyFatMale11 = BodyFatMale.this;
                    bodyFatMale11.str_assess = bodyFatMale11.getResources().getString(R.string.acceptable);
                }
                BodyFatMale bodyFatMale12 = BodyFatMale.this;
                bodyFatMale12.str_bf = bodyFatMale12.numberFormat.format(BodyFatMale.this.bf);
                Intent intent = new Intent(BodyFatMale.this, BodyFatResult.class);
                intent.putExtra(ConditionalUserProperty.VALUE, BodyFatMale.this.str_bf);
                intent.putExtra("value2", BodyFatMale.this.str_assess);
                BodyFatMale.this.startActivity(intent);
            }
        });
    }

    int primaryColor;

    private void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bodyfat));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edHeight = findViewById(R.id.edHeight);
        edWeight = findViewById(R.id.edWeight);
        edHeight2 = findViewById(R.id.edHeight2);
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);
        edWaist = findViewById(R.id.edWaist);
        edNeck = findViewById(R.id.edNeck);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvWaist = findViewById(R.id.tvWaist);
        tvNeck = findViewById(R.id.tvNeck);
        tvcm = findViewById(R.id.tvcm);
        tvin = findViewById(R.id.tvin);
        weightSp = findViewById(R.id.weightSp);
        heightSp = findViewById(R.id.heightSp);
        waistSp = findViewById(R.id.waistSp);
        neckSp = findViewById(R.id.neckSp);
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);

        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.bluecolorPrimary);

        ImageView img_height = findViewById(R.id.img_height);
        ImageView img_waist = findViewById(R.id.img_waist);
        ImageView img_weight = findViewById(R.id.img_weight);
        ImageView img_centimeter = findViewById(R.id.img_centimeter);
        ImageView img_inch = findViewById(R.id.img_inch);
        ImageView img_neck = findViewById(R.id.img_neck);
        setThemeColor(img_waist);
        setThemeColor(img_centimeter);
        setThemeColor(img_height);
        setThemeColor(img_neck);
        setThemeColor(img_weight);
        setThemeColor(img_inch);


    }

    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }

}
