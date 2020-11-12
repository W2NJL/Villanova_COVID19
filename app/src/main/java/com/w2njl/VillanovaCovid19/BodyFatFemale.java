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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.text.NumberFormat;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class BodyFatFemale extends AppCompatActivity {

    double bf;
    boolean check = false;
    public boolean cms = true;
    public boolean cms2 = true;
    public boolean cms3 = true;
    public boolean cms4 = true;
    public boolean cms5 = true;
    public boolean cms6 = true;
    EditText edForearm;
    int primaryColor;
    EditText edHeight;
    EditText edHeight2;
    LinearLayout tvInputHeight2;
    EditText edHip;
    EditText edNeck;
    EditText edWaist;
    EditText edWeight;
    EditText edWrist;
    double forearm;
    Spinner forearmSp;
    int[] forearm_img = {R.drawable.height, R.drawable.height};
    double height;
    double height2;
    Spinner heightSp;
    int[] height_img = {R.drawable.height, R.drawable.height};
    double hip;
    Spinner hipSp;
    int[] hip_img = {R.drawable.height, R.drawable.height};

    /* renamed from: kg */
    public boolean isKG = true;
    double neck;
    Spinner neckSp;
    int[] neck_img = {R.drawable.height, R.drawable.height};

    /* renamed from: nf */
    NumberFormat numberFormat;
    double result3;
    String str_assess;
    String str_bf;
    TextView tvForearm;
    TextView tvHeight;
    TextView tvHip;
    TextView tvNeck;
    TextView tvWaist;
    TextView tvWeight;
    TextView tvWrist;
    TextView tvcm;
    //    TextView tvcm2;
//    TextView tvcm3;
//    TextView tvcm4;
//    TextView tvcm5;
//    TextView tvcm6;
    TextView tvin;
    double waist;
    Spinner waistSp;
    int[] waist_img = {R.drawable.height, R.drawable.height};
    double weight;
    Spinner weightSp;
    int[] weight_img = {R.drawable.weight, R.drawable.weight};
    double wrist;
    Spinner wristSp;
    int[] wrist_img = {R.drawable.height, R.drawable.height};
    Toolbar toolbar;


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
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.height_img[i]);
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
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.kilograms), BodyFatFemale.this.getResources().getString(R.string.pounds)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.weight_img[i]);
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
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.waist_img[i]);
            return inflate;
        }
    }

    public class MyAdapter5 extends ArrayAdapter<String> {
        MyAdapter5(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.neck_img[i]);
            return inflate;
        }
    }

    public class MyAdapter6 extends ArrayAdapter<String> {
        MyAdapter6(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.forearm_img[i]);
            return inflate;
        }
    }

    public class MyAdapter7 extends ArrayAdapter<String> {
        MyAdapter7(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.wrist_img[i]);
            return inflate;
        }
    }

    public class MyAdapter8 extends ArrayAdapter<String> {
        MyAdapter8(Context context, int i, String[] strArr) {
            super(context, i, strArr);
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getCustomView(i, viewGroup);
        }

        public View getCustomView(int i, ViewGroup viewGroup) {
            String[] strArr = {BodyFatFemale.this.getResources().getString(R.string.centimeters), BodyFatFemale.this.getResources().getString(R.string.feets)};
            View inflate = BodyFatFemale.this.getLayoutInflater().inflate(R.layout.spinner_down_blue, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.currency)).setText(strArr[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(BodyFatFemale.this.hip_img[i]);
            return inflate;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.bodyfatfemale);
        String[] strArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] strArr2 = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
        String[] strArr3 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr4 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr5 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr6 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        String[] strArr7 = {getResources().getString(R.string.centimeters), getResources().getString(R.string.in)};
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        init();
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);

        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(true, primaryColor));
        heightSp.setAdapter(new MyAdapter2(this, R.layout.spinner_down_blue, strArr));
        weightSp.setAdapter(new MyAdapter3(this, R.layout.spinner_down_blue, strArr2));
        waistSp.setAdapter(new MyAdapter4(this, R.layout.spinner_down_blue, strArr4));
        neckSp.setAdapter(new MyAdapter5(this, R.layout.spinner_down_blue, strArr5));
        forearmSp.setAdapter(new MyAdapter6(this, R.layout.spinner_down_blue, strArr6));
        wristSp.setAdapter(new MyAdapter7(this, R.layout.spinner_down_blue, strArr3));
        hipSp.setAdapter(new MyAdapter8(this, R.layout.spinner_down_blue, strArr7));


        heightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.heightSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms = true;
                    bodyFatFemale.edHeight.setEnabled(true);
                    BodyFatFemale.this.edHeight2.setEnabled(false);
                    BodyFatFemale.this.tvInputHeight2.setVisibility(View.GONE);
                    BodyFatFemale.this.tvcm.setText(BodyFatFemale.this.getResources().getString(R.string.cm));
                    BodyFatFemale.this.tvin.setText("");
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms = false;
                bodyFatFemale2.edHeight.setEnabled(true);
                BodyFatFemale.this.edHeight2.setEnabled(true);
                BodyFatFemale.this.tvcm.setText(BodyFatFemale.this.getResources().getString(R.string.ft));
                BodyFatFemale.this.tvin.setText(BodyFatFemale.this.getResources().getString(R.string.in));
                BodyFatFemale.this.tvInputHeight2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        waistSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.waistSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms2 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms2 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        neckSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.neckSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms3 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms3 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        forearmSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.forearmSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms4 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms4 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        wristSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.wristSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms5 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms5 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        hipSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (BodyFatFemale.this.hipSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.centimeters))) {
                    BodyFatFemale bodyFatFemale = BodyFatFemale.this;
                    bodyFatFemale.cms6 = true;
                    return;
                }
                BodyFatFemale bodyFatFemale2 = BodyFatFemale.this;
                bodyFatFemale2.cms6 = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weightSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BodyFatFemale.this.isKG = BodyFatFemale.this.weightSp.getSelectedItem().toString().equals(BodyFatFemale.this.getResources().getString(R.string.kilograms));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                BodyFatFemale.this.edHeight.setText(str);
                BodyFatFemale.this.edHeight2.setText(str);
                BodyFatFemale.this.edWeight.setText(str);
                BodyFatFemale.this.edWaist.setText(str);
                BodyFatFemale.this.edNeck.setText(str);
                BodyFatFemale.this.edForearm.setText(str);
                BodyFatFemale.this.edHip.setText(str);
                BodyFatFemale.this.edWrist.setText(str);
                BodyFatFemale.this.edHeight.requestFocus();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    BodyFatFemale.this.height = Double.parseDouble(BodyFatFemale.this.edHeight.getText().toString());
                } catch (NumberFormatException unused) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.height2 = Double.parseDouble(BodyFatFemale.this.edHeight2.getText().toString());
                } catch (NumberFormatException unused2) {
                    BodyFatFemale.this.height2 = 0.0d;
                }
                try {
                    BodyFatFemale.this.weight = Double.parseDouble(BodyFatFemale.this.edWeight.getText().toString());
                } catch (NumberFormatException unused3) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.waist = Double.parseDouble(BodyFatFemale.this.edWaist.getText().toString());
                } catch (NumberFormatException unused4) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.neck = Double.parseDouble(BodyFatFemale.this.edNeck.getText().toString());
                } catch (NumberFormatException unused5) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.forearm = Double.parseDouble(BodyFatFemale.this.edForearm.getText().toString());
                } catch (NumberFormatException unused6) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.wrist = Double.parseDouble(BodyFatFemale.this.edWrist.getText().toString());
                } catch (NumberFormatException unused7) {
                    BodyFatFemale.this.check = true;
                }
                try {
                    BodyFatFemale.this.hip = Double.parseDouble(BodyFatFemale.this.edHip.getText().toString());
                } catch (NumberFormatException unused8) {
                    BodyFatFemale.this.check = true;
                }
                if (BodyFatFemale.this.check) {
                    Toast.makeText(BodyFatFemale.this.getApplicationContext(), BodyFatFemale.this.getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    BodyFatFemale.this.check = false;
                    return;
                }
                if (BodyFatFemale.this.isKG) {
                    BodyFatFemale.this.weight *= 2.20462d;
                }
                if (BodyFatFemale.this.cms) {
                    BodyFatFemale.this.height *= 0.393701d;
                } else {
                    BodyFatFemale.this.height *= 12.0d;
                    BodyFatFemale.this.height += BodyFatFemale.this.height2;
                }
                if (BodyFatFemale.this.cms2) {
                    BodyFatFemale.this.waist *= 0.393701d;
                }
                if (BodyFatFemale.this.cms3) {
                    BodyFatFemale.this.neck *= 0.393701d;
                }
                if (BodyFatFemale.this.cms4) {
                    BodyFatFemale.this.forearm *= 0.393701d;
                }
                if (BodyFatFemale.this.cms5) {
                    BodyFatFemale.this.wrist *= 0.393701d;
                }
                if (BodyFatFemale.this.cms6) {
                    BodyFatFemale.this.hip *= 0.393701d;
                }
                BodyFatFemale bodyFatFemale7 = BodyFatFemale.this;
                bodyFatFemale7.result3 = (((((bodyFatFemale7.weight * 0.0732d) + 8.987d) + (BodyFatFemale.this.wrist / 3.14d)) - (BodyFatFemale.this.waist * 0.157d)) - (BodyFatFemale.this.hip * 0.249d)) + (BodyFatFemale.this.forearm * 0.434d);
                BodyFatFemale bodyFatFemale8 = BodyFatFemale.this;
                bodyFatFemale8.bf = ((bodyFatFemale8.weight - BodyFatFemale.this.result3) * 100.0d) / BodyFatFemale.this.weight;
                if (BodyFatFemale.this.bf >= 10.0d && BodyFatFemale.this.bf <= 13.0d) {
                    BodyFatFemale bodyFatFemale9 = BodyFatFemale.this;
                    bodyFatFemale9.str_assess = bodyFatFemale9.getResources().getString(R.string.essential);
                } else if (BodyFatFemale.this.bf >= 14.0d && BodyFatFemale.this.bf <= 20.0d) {
                    BodyFatFemale bodyFatFemale10 = BodyFatFemale.this;
                    bodyFatFemale10.str_assess = bodyFatFemale10.getResources().getString(R.string.typicalathlete);
                } else if (BodyFatFemale.this.bf >= 21.0d && BodyFatFemale.this.bf <= 24.0d) {
                    BodyFatFemale bodyFatFemale11 = BodyFatFemale.this;
                    bodyFatFemale11.str_assess = bodyFatFemale11.getResources().getString(R.string.physicallyfit);
                } else if (BodyFatFemale.this.bf < 25.0d || BodyFatFemale.this.bf > 31.0d) {
                    BodyFatFemale bodyFatFemale12 = BodyFatFemale.this;
                    bodyFatFemale12.str_assess = bodyFatFemale12.getResources().getString(R.string.obese);
                } else {
                    BodyFatFemale bodyFatFemale13 = BodyFatFemale.this;
                    bodyFatFemale13.str_assess = bodyFatFemale13.getResources().getString(R.string.acceptable);
                }
                BodyFatFemale bodyFatFemale14 = BodyFatFemale.this;
                bodyFatFemale14.str_bf = bodyFatFemale14.numberFormat.format(BodyFatFemale.this.bf);
                Intent intent = new Intent(BodyFatFemale.this, BodyFatResult.class);
                intent.putExtra(ConditionalUserProperty.VALUE, BodyFatFemale.this.str_bf);
                intent.putExtra("value2", BodyFatFemale.this.str_assess);
                BodyFatFemale.this.startActivity(intent);
            }
        });
    }

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
        tvInputHeight2 = findViewById(R.id.tvInputHeight2);
        edHeight2 = findViewById(R.id.edHeight2);
        edWaist = findViewById(R.id.edWaist);
        edWrist = findViewById(R.id.edWrist);
        edNeck = findViewById(R.id.edNeck);
        edForearm = findViewById(R.id.edForearm);
        edHip = findViewById(R.id.edHip);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvWaist = findViewById(R.id.tvWaist);
        tvWrist = findViewById(R.id.tvWrist);
        tvNeck = findViewById(R.id.tvNeck);
        tvForearm = findViewById(R.id.tvForearm);
        tvHip = findViewById(R.id.tvHip);
        tvcm = findViewById(R.id.tvcm);
        tvin = findViewById(R.id.tvin);
        weightSp = findViewById(R.id.weightSp);
        heightSp = findViewById(R.id.heightSp);
        waistSp = findViewById(R.id.waistSp);
        neckSp = findViewById(R.id.neckSp);
        wristSp = findViewById(R.id.wristSp);
        BodyFatFemale.this.tvInputHeight2.setVisibility(View.GONE);
        forearmSp = findViewById(R.id.forearmSp);
        hipSp = findViewById(R.id.hipSp);

        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.bluecolorPrimary);

        ImageView img_waist = findViewById(R.id.img_waist);
        ImageView img_height = findViewById(R.id.img_height);
        ImageView img_centimeter = findViewById(R.id.img_centimeter);
        ImageView img_weight = findViewById(R.id.img_weight);
        ImageView img_forearm = findViewById(R.id.img_forearm);
        ImageView img_inch = findViewById(R.id.img_inch);
        ImageView img_hip = findViewById(R.id.img_hip);
        ImageView img_neck = findViewById(R.id.img_neck);
        ImageView img_wrist = findViewById(R.id.img_wrist);
        setThemeColor(img_waist);
        setThemeColor(img_height);
        setThemeColor(img_centimeter);
        setThemeColor(img_neck);
        setThemeColor(img_forearm);
        setThemeColor(img_hip);
        setThemeColor(img_inch);
        setThemeColor(img_weight);
        setThemeColor(img_wrist);


    }

    public void setThemeColor(ImageView imageView) {
        imageView.getDrawable().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
    }

}
