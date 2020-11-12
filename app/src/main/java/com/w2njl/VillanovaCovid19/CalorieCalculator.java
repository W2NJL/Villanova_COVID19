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
import com.github.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;
import static com.github.pdfviewer.PDFViewPager.VERTICAL;

public class CalorieCalculator extends AppCompatActivity {
    Spinner activitySp;
    int[] activity_img = {R.drawable.activity, R.drawable.activity, R.drawable.activity, R.drawable.activity, R.drawable.activity};
    double age;
    double bmr;
    Button chart;
    boolean check = false;
    public boolean cms = true;
    EditText edAge;
    EditText edHeight;
    EditText edHeight2;
    EditText edWeight;
    double factor = 0.0d;
    Spinner genderSp;
    LinearLayout tvInputHeight2;
    int[] gender_img = {R.drawable.man, R.drawable.girl};
    double height;
    double height2;
    Spinner heightSp;
    int[] height_img = {R.drawable.height, R.drawable.height};

    public boolean isKG = true;
    public boolean male = true;

    NumberFormat numberFormat;
    Button pdf;
    String str_bmr;
    TextView tvActivity;
    TextView tvAge;
    TextView tvGender;
    TextView tvHeight;
    TextView tvWeight;
    TextView tvcm;
    TextView tvin;
    double weight;
    Spinner weightSp;
    Toolbar toolbar;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};

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
            String[] strArr = {CalorieCalculator.this.getResources().getString(R.string.male), CalorieCalculator.this.getResources().getString(R.string.female)};
            View inflate = CalorieCalculator.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(CalorieCalculator.this.gender_img[i]);
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
            String[] strArr = {CalorieCalculator.this.getResources().getString(R.string.centimeters), CalorieCalculator.this.getResources().getString(R.string.feets)};
            View inflate = CalorieCalculator.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(CalorieCalculator.this.height_img[i]);
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
            String[] strArr = {CalorieCalculator.this.getResources().getString(R.string.kilograms), CalorieCalculator.this.getResources().getString(R.string.pounds)};
            View inflate = CalorieCalculator.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(CalorieCalculator.this.weight_img[i]);
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
            String[] strArr = {CalorieCalculator.this.getResources().getString(R.string.sedentary), CalorieCalculator.this.getResources().getString(R.string.lightly_active), CalorieCalculator.this.getResources().getString(R.string.moderately_active), CalorieCalculator.this.getResources().getString(R.string.very_active), CalorieCalculator.this.getResources().getString(R.string.extremely_active)};
            View inflate = CalorieCalculator.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(CalorieCalculator.this.activity_img[i]);
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
        setContentView(R.layout.calorie_calc);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] strArr2 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr3 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr4 = {getResources().getString(R.string.sedentary), getResources().getString(R.string.lightly_active), getResources().getString(R.string.moderately_active), getResources().getString(R.string.very_active), getResources().getString(R.string.extremely_active)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        Button chart = findViewById(R.id.chart);
        Button pdf = findViewById(R.id.pdf);

        chart.setBackground(getShapeDrawble(false, primaryColor));
        pdf.setBackground(getShapeDrawble(false, primaryColor));
        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));

        edHeight2.setEnabled(false);
        edHeight.setEnabled(true);
        tvcm.setText(getResources().getString(R.string.cm));
        tvin.setText("");
        tvInputHeight2.setVisibility(View.GONE);
        chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CalorieCalculator.this.startActivity(new Intent(CalorieCalculator.this, Chart_Calorie.class));
            }
        });
        pdf.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final String SAMPLE_FILE = "food_calorie_list.pdf";
                try {
                    File file = new File(getCacheDir(), SAMPLE_FILE);
                    if (!file.exists()) {

                        try {
                            InputStream asset = getAssets().open(SAMPLE_FILE);
                            FileOutputStream output;
                            output = new FileOutputStream(file);
                            final byte[] buffer = new byte[1024];
                            int size;
                            while ((size = asset.read(buffer)) != -1) {
                                output.write(buffer, 0, size);
                            }
                            asset.close();
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    PDFView.with(CalorieCalculator.this).setfilepath(file.getAbsolutePath())
                            .setSwipeOrientation(VERTICAL)
                            .start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));
        heightSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr2));
        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr3));
        activitySp.setAdapter(new MyAdapter4(this, R.layout.spinner_down_blue, strArr4));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (CalorieCalculator.this.heightSp.getSelectedItem().toString().equals(CalorieCalculator.this.getResources().getString(R.string.centimeters))) {
                    CalorieCalculator calorieCalculator = CalorieCalculator.this;
                    calorieCalculator.cms = true;
                    calorieCalculator.edHeight.setEnabled(true);
                    CalorieCalculator.this.edHeight2.setEnabled(false);
                    CalorieCalculator.this.tvInputHeight2.setVisibility(View.GONE);
                    CalorieCalculator.this.tvcm.setText(CalorieCalculator.this.getResources().getString(R.string.cm));
                    CalorieCalculator.this.tvin.setText("");
                    return;
                }
                CalorieCalculator calorieCalculator2 = CalorieCalculator.this;
                calorieCalculator2.cms = false;
                calorieCalculator2.edHeight.setEnabled(true);
                CalorieCalculator.this.edHeight2.setEnabled(true);
                CalorieCalculator.this.tvcm.setText(CalorieCalculator.this.getResources().getString(R.string.ft));
                CalorieCalculator.this.tvin.setText(CalorieCalculator.this.getResources().getString(R.string.in));
                CalorieCalculator.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CalorieCalculator.this.isKG = CalorieCalculator.this.weightSp.getSelectedItem().toString().equals(CalorieCalculator.this.getResources().getString(R.string.kilograms));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = CalorieCalculator.this.activitySp.getSelectedItem().toString();
                if (obj.equals(CalorieCalculator.this.getResources().getString(R.string.sedentary))) {
                    CalorieCalculator.this.factor = 1.2d;
                } else if (obj.equals(CalorieCalculator.this.getResources().getString(R.string.lightly_active))) {
                    CalorieCalculator.this.factor = 1.375d;
                } else if (obj.equals(CalorieCalculator.this.getResources().getString(R.string.moderately_active))) {
                    CalorieCalculator.this.factor = 1.55d;
                } else if (obj.equals(CalorieCalculator.this.getResources().getString(R.string.very_active))) {
                    CalorieCalculator.this.factor = 1.725d;
                } else {
                    CalorieCalculator.this.factor = 1.9d;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CalorieCalculator.this.male = CalorieCalculator.this.genderSp.getSelectedItem().toString().equals(CalorieCalculator.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                CalorieCalculator.this.edHeight.setText(str);
                CalorieCalculator.this.edWeight.setText(str);
                CalorieCalculator.this.edAge.setText(str);
                CalorieCalculator.this.edHeight2.setText(str);
                CalorieCalculator.this.edAge.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    try {
                        CalorieCalculator.this.height = Double.parseDouble(CalorieCalculator.this.edHeight.getText().toString());
                    } catch (NumberFormatException unused) {
                        CalorieCalculator.this.check = true;
                    }
                    try {
                        CalorieCalculator.this.weight = Double.parseDouble(CalorieCalculator.this.edWeight.getText().toString());
                    } catch (NumberFormatException unused2) {
                        CalorieCalculator.this.check = true;
                    }
                    try {
                        CalorieCalculator.this.age = Double.parseDouble(CalorieCalculator.this.edAge.getText().toString());
                    } catch (NumberFormatException unused3) {
                        CalorieCalculator.this.check = true;
                    }
                    try {
                        CalorieCalculator.this.height2 = Double.parseDouble(CalorieCalculator.this.edHeight2.getText().toString());
                    } catch (NumberFormatException unused4) {
                        CalorieCalculator.this.height2 = 0.0d;
                    }
                    if (CalorieCalculator.this.check) {
                        Toast.makeText(CalorieCalculator.this.getApplicationContext(), CalorieCalculator.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                        CalorieCalculator.this.check = false;
                        return;
                    }
                    if (!CalorieCalculator.this.cms) {
                        CalorieCalculator.this.height *= 12.0d;
                        CalorieCalculator.this.height += CalorieCalculator.this.height2;
                        CalorieCalculator.this.height *= 2.54d;
                    }
                    if (!CalorieCalculator.this.isKG) {
                        CalorieCalculator.this.weight *= 0.453592d;
                    }
                    if (CalorieCalculator.this.male) {
                        CalorieCalculator.this.weight *= 13.7d;
                        CalorieCalculator.this.weight += 66.0d;
                        CalorieCalculator.this.height *= 5.0d;
                        CalorieCalculator.this.age *= 6.8d;
                    } else {
                        CalorieCalculator.this.weight *= 9.6d;
                        CalorieCalculator.this.weight += 655.0d;
                        CalorieCalculator.this.height *= 1.8d;
                        CalorieCalculator.this.age *= 4.7d;
                    }

                    try {
                        PrefData.setAge(getApplicationContext(), Integer.parseInt(edAge.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    CalorieCalculator calorieCalculator3 = CalorieCalculator.this;
                    calorieCalculator3.bmr = (calorieCalculator3.weight + CalorieCalculator.this.height) - CalorieCalculator.this.age;
                    CalorieCalculator.this.bmr *= CalorieCalculator.this.factor;
                    CalorieCalculator calorieCalculator4 = CalorieCalculator.this;
                    calorieCalculator4.str_bmr = calorieCalculator4.numberFormat.format(CalorieCalculator.this.bmr);
                    Intent intent = new Intent(CalorieCalculator.this, Calorie_result.class);
                    intent.putExtra(ConditionalUserProperty.VALUE, CalorieCalculator.this.str_bmr);
                    CalorieCalculator.this.startActivity(intent);
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
        text_title.setText(getResources().getString(R.string.calories));
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
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvcm = findViewById(R.id.tvcm);
        tvin = findViewById(R.id.tvin);
        tvActivity = findViewById(R.id.tvActivity);
        weightSp = findViewById(R.id.weightSp);
        heightSp = findViewById(R.id.heightSp);
        genderSp = findViewById(R.id.genderSp);
        activitySp = findViewById(R.id.activitySp);
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);
        chart = findViewById(R.id.chart);
        pdf = findViewById(R.id.pdf);


        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.graycolorPrimary);

        ImageView img_age = findViewById(R.id.image_age);
        ImageView img_height = findViewById(R.id.img_height);
        ImageView img_centimeter = findViewById(R.id.img_centimeter);
        ImageView img_inch = findViewById(R.id.img_inch);
        ImageView img_weight = findViewById(R.id.img_weight);
        ImageView img_activity = findViewById(R.id.img_activity);
        setThemeColor(img_age);
        setThemeColor(img_height);
        setThemeColor(img_centimeter);
        setThemeColor(img_inch);
        setThemeColor(img_weight);
        setThemeColor(img_activity);


    }

    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }
}
