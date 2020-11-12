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

public class IdealWeightCalc extends AppCompatActivity {
    double age;
    boolean check = false;
    public boolean cms = true;
    EditText edAge;
    EditText edHeight;
    EditText edHeight2;
    Spinner genderSp;
    int[] gender_img = {R.drawable.man, R.drawable.girl};
    double height;
    double height2;
    Spinner heightSp;
    int[] height_img = {R.drawable.height, R.drawable.height};

    double id;
    public boolean male = true;

    NumberFormat numberFormat;
    String str_id;
    String str_id2;
    int primaryColor;
    public boolean toast = false;
    TextView tvAge;
    TextView tvGender;
    TextView tvHeight;
    LinearLayout tvInputHeight2;
    TextView tvcm;
    TextView tvin;
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
            String[] strArr = {IdealWeightCalc.this.getResources().getString(R.string.male), IdealWeightCalc.this.getResources().getString(R.string.female)};
            View inflate = IdealWeightCalc.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(IdealWeightCalc.this.gender_img[i]);


            return inflate;
        }
    }

    public class MyAdapter2 extends ArrayAdapter<String> {
        public MyAdapter2(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view,@Nullable ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {IdealWeightCalc.this.getResources().getString(R.string.centimeters), IdealWeightCalc.this.getResources().getString(R.string.feets)};
            View inflate = IdealWeightCalc.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(IdealWeightCalc.this.height_img[i]);
            return inflate;
        }
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.OrangeTheme);
        setContentView(R.layout.idealweightcalc);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        findViewById(R.id.chart).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IdealWeightCalc.this.startActivity(new Intent(IdealWeightCalc.this, ChartIdeal.class));
            }
        });
        init();
        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));
        Button chart = findViewById(R.id.chart);
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);


        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        chart.setBackground(getShapeDrawble(false, primaryColor));


        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));
        heightSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr2));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (IdealWeightCalc.this.heightSp.getSelectedItem().toString().equals(IdealWeightCalc.this.getResources().getString(R.string.centimeters))) {
                    IdealWeightCalc idealWeightCalc = IdealWeightCalc.this;
                    idealWeightCalc.cms = true;
                    idealWeightCalc.edHeight.setEnabled(true);
                    IdealWeightCalc.this.edHeight2.setEnabled(false);
                    IdealWeightCalc.this.tvInputHeight2.setVisibility(View.GONE);
                    IdealWeightCalc.this.tvcm.setText(IdealWeightCalc.this.getResources().getString(R.string.cm));
                    IdealWeightCalc.this.tvin.setText("");
                    return;
                }
                IdealWeightCalc idealWeightCalc2 = IdealWeightCalc.this;
                idealWeightCalc2.cms = false;
                idealWeightCalc2.edHeight.setEnabled(true);
                IdealWeightCalc.this.edHeight2.setEnabled(true);
                IdealWeightCalc.this.tvcm.setText(IdealWeightCalc.this.getResources().getString(R.string.ft));
                IdealWeightCalc.this.tvin.setText(IdealWeightCalc.this.getResources().getString(R.string.in));
                IdealWeightCalc.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IdealWeightCalc.this.male = IdealWeightCalc.this.genderSp.getSelectedItem().toString().equals(IdealWeightCalc.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                IdealWeightCalc.this.edHeight.setText(str);
                IdealWeightCalc.this.edAge.setText(str);
                IdealWeightCalc.this.edHeight2.setText(str);
                IdealWeightCalc.this.edAge.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    try {
                        IdealWeightCalc.this.height = Double.parseDouble(IdealWeightCalc.this.edHeight.getText().toString());
                    } catch (NumberFormatException unused) {
                        IdealWeightCalc.this.check = true;
                    }
                    try {
                        IdealWeightCalc.this.age = Double.parseDouble(IdealWeightCalc.this.edAge.getText().toString());

                    } catch (NumberFormatException unused2) {
                        IdealWeightCalc.this.check = true;
                    }
                    try {
                        IdealWeightCalc.this.height2 = Double.parseDouble(IdealWeightCalc.this.edHeight2.getText().toString());
                    } catch (NumberFormatException unused3) {
                        IdealWeightCalc.this.height2 = 0.0d;
                    }
                    if (IdealWeightCalc.this.check) {
                        Toast.makeText(IdealWeightCalc.this.getApplicationContext(), IdealWeightCalc.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                        IdealWeightCalc.this.check = false;
                        return;
                    } else if (IdealWeightCalc.this.cms) {
                        if (IdealWeightCalc.this.height < 153.0d) {
                            Toast.makeText(IdealWeightCalc.this.getApplicationContext(), IdealWeightCalc.this.getResources().getString(R.string.entercm), Toast.LENGTH_SHORT).show();
                            IdealWeightCalc.this.toast = true;
                            return;
                        } else {
                            IdealWeightCalc.this.height *= 0.393701d;
                            IdealWeightCalc.this.height -= 60.0d;
                        }
                    } else if (IdealWeightCalc.this.height < 5.0d) {
                        Toast.makeText(IdealWeightCalc.this.getApplicationContext(), IdealWeightCalc.this.getResources().getString(R.string.enterfeet), Toast.LENGTH_SHORT).show();
                        IdealWeightCalc.this.toast = true;
                        return;
                    } else {
                        IdealWeightCalc.this.height *= 12.0d;
                        IdealWeightCalc.this.height += IdealWeightCalc.this.height2;
                        IdealWeightCalc.this.height -= 60.0d;
                    }
                    if (IdealWeightCalc.this.male) {
                        IdealWeightCalc idealWeightCalc = IdealWeightCalc.this;
                        idealWeightCalc.id = (idealWeightCalc.height * 1.9d) + 52.0d;
                    } else {
                        IdealWeightCalc idealWeightCalc2 = IdealWeightCalc.this;
                        idealWeightCalc2.id = (idealWeightCalc2.height * 1.7d) + 49.0d;
                    }
                    if (IdealWeightCalc.this.toast) {
                        IdealWeightCalc idealWeightCalc3 = IdealWeightCalc.this;
                        idealWeightCalc3.toast = false;
                        idealWeightCalc3.id = 0.0d;
                    }
                    try {
                        PrefData.setAge(getApplicationContext(), Integer.parseInt(edAge.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    IdealWeightCalc idealWeightCalc4 = IdealWeightCalc.this;
                    idealWeightCalc4.str_id = idealWeightCalc4.numberFormat.format(IdealWeightCalc.this.id);
                    IdealWeightCalc.this.id *= 2.20462d;
                    IdealWeightCalc idealWeightCalc5 = IdealWeightCalc.this;
                    idealWeightCalc5.str_id2 = idealWeightCalc5.numberFormat.format(IdealWeightCalc.this.id);
                    Intent intent = new Intent(IdealWeightCalc.this, IdealWeightResult.class);
                    intent.putExtra(ConditionalUserProperty.VALUE, IdealWeightCalc.this.str_id);
                    intent.putExtra("value2", IdealWeightCalc.this.str_id2);
                    IdealWeightCalc.this.startActivity(intent);
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
        text_title.setText(getResources().getString(R.string.idealweight));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edHeight = findViewById(R.id.edHeight);
        edAge = findViewById(R.id.edAge);
        edHeight2 = findViewById(R.id.edHeight2);
        tvHeight = findViewById(R.id.tvHeight);
        tvAge = findViewById(R.id.tvAge);
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);
        tvGender = findViewById(R.id.tvGender);
        tvcm = findViewById(R.id.tvcm);
        tvin = findViewById(R.id.tvin);
        heightSp = findViewById(R.id.heightSp);
        genderSp = findViewById(R.id.genderSp);

        tvInputHeight2.setVisibility(View.GONE);

        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.orangecolorPrimary);

        ImageView img_age = findViewById(R.id.image_age);
        ImageView img_height = findViewById(R.id.img_height);
        ImageView img_centimeter = findViewById(R.id.img_centimeter);
        ImageView img_inch = findViewById(R.id.img_inch);
        setThemeColor(img_age);
        setThemeColor(img_height);
        setThemeColor(img_centimeter);
        setThemeColor(img_inch);

    }


    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }
}
