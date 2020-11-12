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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.w2njl.VillanovaCovid19.util.PrefData;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class BodyFatHome extends AppCompatActivity {
    double age;
    boolean check = false;
    EditText edAge;
    Spinner genderSp;
    int[] gender_img = {R.drawable.man, R.drawable.girl};
    public boolean male = true;
    TextView tvAge;
    TextView tvGender;
    Toolbar toolbar;

    public class MyAdapter1 extends ArrayAdapter<String> {
        MyAdapter1(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatHome.this.getResources().getString(R.string.male), BodyFatHome.this.getResources().getString(R.string.female)};
            View inflate = BodyFatHome.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatHome.this.gender_img[i]);
            return inflate;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);

        setContentView(R.layout.bodyfathome);
        String[] strArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        findViewById(R.id.chart).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BodyFatHome.this.startActivity(new Intent(BodyFatHome.this, Chart_Fat.class));
            }
        });
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);

        init();
        edAge.setText(String.valueOf(PrefData.getAge(getApplicationContext())));

        Button chart = findViewById(R.id.chart);
        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        chart.setBackground(getShapeDrawble(false, primaryColor));


        genderSp.setAdapter(new MyAdapter1(this, R.layout.spinner_down_blue, strArr));


        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                BodyFatHome.this.male = BodyFatHome.this.genderSp.getSelectedItem().toString().equals(BodyFatHome.this.getResources().getString(R.string.male));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BodyFatHome.this.edAge.setText("");
                BodyFatHome.this.edAge.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    try {
                        BodyFatHome.this.age = Double.parseDouble(BodyFatHome.this.edAge.getText().toString());
                    } catch (NumberFormatException unused) {
                        BodyFatHome.this.check = true;
                    }
                    try {
                        PrefData.setAge(getApplicationContext(), Integer.parseInt(edAge.getText().toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (BodyFatHome.this.check) {
                        Toast.makeText(BodyFatHome.this.getApplicationContext(), BodyFatHome.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                        BodyFatHome.this.check = false;
                    } else if (BodyFatHome.this.male) {
                        BodyFatHome.this.startActivity(new Intent(BodyFatHome.this, BodyFatMale.class));
                    } else {
                        BodyFatHome.this.startActivity(new Intent(BodyFatHome.this, BodyFatFemale.class));
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    int primaryColor;

    private void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.bodyfat));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edAge = findViewById(R.id.edAge);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        genderSp = findViewById(R.id.genderSp);

        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.bluecolorPrimary);
        ImageView img_age = findViewById(R.id.image_age);

        setThemeColor(img_age);


    }


    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }

}
