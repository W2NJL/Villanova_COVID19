package com.w2njl.VillanovaCovid19;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class OvulationCalc extends AppCompatActivity {

    int anInt = 0;
    String result;
    int primaryColor;
    String result2;
    TextView tvdatechosen;
    TextView tvdatechosenval;
    Toolbar toolbar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.YellowTheme);
        setContentView(R.layout.ovulation);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        findViewById(R.id.chart).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                OvulationCalc.this.startActivity(new Intent(OvulationCalc.this, ChartOvu.class));
            }
        });
        Button button = findViewById(R.id.calc);
        Button button2 = findViewById(R.id.reset);
        Button chart = findViewById(R.id.chart);
        init();

        button.setBackground(getShapeDrawble(false, primaryColor));
        button2.setBackground(getShapeDrawble(false, primaryColor));
        chart.setBackground(getShapeDrawble(false, primaryColor));


        final Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        int i2 = instance.get(Calendar.MONTH);
        int i3 = instance.get(Calendar.DATE);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String[] split = simpleDateFormat.format(instance.getTime()).split("/");
        String str = " ";
        switch (Integer.parseInt(split[0])) {
            case 1:
                TextView textView = this.tvdatechosenval;
                String sb = split[1] +
                        str +
                        getResources().getString(R.string.jan) +
                        str +
                        split[2];
                textView.setText(sb);
                break;
            case 2:
                TextView textView2 = this.tvdatechosenval;
                String sb2 = split[1] +
                        str +
                        getResources().getString(R.string.feb) +
                        str +
                        split[2];
                textView2.setText(sb2);
                break;
            case 3:
                TextView textView3 = this.tvdatechosenval;
                String sb3 = split[1] +
                        str +
                        getResources().getString(R.string.mar) +
                        str +
                        split[2];
                textView3.setText(sb3);
                break;
            case 4:
                TextView textView4 = this.tvdatechosenval;
                String sb4 = split[1] +
                        str +
                        getResources().getString(R.string.apr) +
                        str +
                        split[2];
                textView4.setText(sb4);
                break;
            case 5:
                TextView textView5 = this.tvdatechosenval;
                String sb5 = split[1] +
                        str +
                        getResources().getString(R.string.may) +
                        str +
                        split[2];
                textView5.setText(sb5);
                break;
            case 6:
                TextView textView6 = this.tvdatechosenval;
                String sb6 = split[1] +
                        str +
                        getResources().getString(R.string.jun) +
                        str +
                        split[2];
                textView6.setText(sb6);
                break;
            case 7:
                TextView textView7 = this.tvdatechosenval;
                String sb7 = split[1] +
                        str +
                        getResources().getString(R.string.jul) +
                        str +
                        split[2];
                textView7.setText(sb7);
                break;
            case 8:
                TextView textView8 = this.tvdatechosenval;
                String sb8 = split[1] +
                        str +
                        getResources().getString(R.string.aug) +
                        str +
                        split[2];
                textView8.setText(sb8);
                break;
            case 9:
                TextView textView9 = this.tvdatechosenval;
                String sb9 = split[1] +
                        str +
                        getResources().getString(R.string.sep) +
                        str +
                        split[2];
                textView9.setText(sb9);
                break;
            case 10:
                TextView textView10 = this.tvdatechosenval;
                String sb10 = split[1] +
                        str +
                        getResources().getString(R.string.oct) +
                        str +
                        split[2];
                textView10.setText(sb10);
                break;
            case 11:
                TextView textView11 = this.tvdatechosenval;
                String sb11 = split[1] +
                        str +
                        getResources().getString(R.string.nov) +
                        str +
                        split[2];
                textView11.setText(sb11);
                break;
            case 12:
                TextView textView12 = this.tvdatechosenval;
                String sb12 = split[1] +
                        str +
                        getResources().getString(R.string.dec) +
                        str +
                        split[2];
                textView12.setText(sb12);
                break;
        }
        final OnDateSetListener r10 = new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                instance.set(Calendar.YEAR, i);
                instance.set(Calendar.MONTH, i2);
                instance.set(Calendar.DATE, i3);
                String[] split = simpleDateFormat.format(instance.getTime()).split("/");
                String str = " ";
                switch (Integer.parseInt(split[0])) {
                    case 1:
                        TextView textView = OvulationCalc.this.tvdatechosenval;
                        String sb = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.jan) +
                                str +
                                split[2];
                        textView.setText(sb);
                        break;
                    case 2:
                        TextView textView2 = OvulationCalc.this.tvdatechosenval;
                        String sb2 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.feb) +
                                str +
                                split[2];
                        textView2.setText(sb2);
                        break;
                    case 3:
                        TextView textView3 = OvulationCalc.this.tvdatechosenval;
                        String sb3 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.mar) +
                                str +
                                split[2];
                        textView3.setText(sb3);
                        break;
                    case 4:
                        TextView textView4 = OvulationCalc.this.tvdatechosenval;
                        String sb4 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.apr) +
                                str +
                                split[2];
                        textView4.setText(sb4);
                        break;
                    case 5:
                        TextView textView5 = OvulationCalc.this.tvdatechosenval;
                        String sb5 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.may) +
                                str +
                                split[2];
                        textView5.setText(sb5);
                        break;
                    case 6:
                        TextView textView6 = OvulationCalc.this.tvdatechosenval;
                        String sb6 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.jun) +
                                str +
                                split[2];
                        textView6.setText(sb6);
                        break;
                    case 7:
                        TextView textView7 = OvulationCalc.this.tvdatechosenval;
                        String sb7 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.jul) +
                                str +
                                split[2];
                        textView7.setText(sb7);
                        break;
                    case 8:
                        TextView textView8 = OvulationCalc.this.tvdatechosenval;
                        String sb8 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.aug) +
                                str +
                                split[2];
                        textView8.setText(sb8);
                        break;
                    case 9:
                        TextView textView9 = OvulationCalc.this.tvdatechosenval;
                        String sb9 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.sep) +
                                str +
                                split[2];
                        textView9.setText(sb9);
                        break;
                    case 10:
                        TextView textView10 = OvulationCalc.this.tvdatechosenval;
                        String sb10 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.oct) +
                                str +
                                split[2];
                        textView10.setText(sb10);
                        break;
                    case 11:
                        TextView textView11 = OvulationCalc.this.tvdatechosenval;
                        String sb11 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.nov) +
                                str +
                                split[2];
                        textView11.setText(sb11);
                        break;
                    case 12:
                        TextView textView12 = OvulationCalc.this.tvdatechosenval;
                        String sb12 = split[1] +
                                str +
                                OvulationCalc.this.getResources().getString(R.string.dec) +
                                str +
                                split[2];
                        textView12.setText(sb12);
                        break;
                }
                instance.add(Calendar.DATE, 12);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                OvulationCalc.this.result = simpleDateFormat.format(instance.getTime());
                instance.add(Calendar.DATE, 4);
                OvulationCalc.this.result2 = simpleDateFormat.format(instance.getTime());
            }
        };
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        OnClickListener r4 = new OnClickListener() {
            public void onClick(View view) {
                OvulationCalc.this.anInt++;
                instance.set(i4, i5, i6);
                DatePickerDialog datePickerDialog = new DatePickerDialog(OvulationCalc.this, r10, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE));
                datePickerDialog.show();
            }
        };
        button2.setOnClickListener(r4);
        OnClickListener r42 = new OnClickListener() {
            public void onClick(View view) {
                int i = OvulationCalc.this.anInt;
                String str = "value2";
                String str2 = ConditionalUserProperty.VALUE;
                if (i == 0) {
                    instance.set(i4, i5, i6);
                    instance.add(Calendar.DATE, 12);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    OvulationCalc.this.result = simpleDateFormat.format(instance.getTime());
                    instance.add(Calendar.DATE, 4);
                    OvulationCalc.this.result2 = simpleDateFormat.format(instance.getTime());
                    Intent intent = new Intent(OvulationCalc.this, OvulationCalcRes.class);
                    intent.putExtra(str2, OvulationCalc.this.result);
                    intent.putExtra(str, OvulationCalc.this.result2);
                    OvulationCalc.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(OvulationCalc.this, OvulationCalcRes.class);
                intent2.putExtra(str2, OvulationCalc.this.result);
                intent2.putExtra(str, OvulationCalc.this.result2);
                OvulationCalc.this.startActivity(intent2);
            }
        };
        button.setOnClickListener(r42);
    }

    private void init() {

        tvdatechosen = findViewById(R.id.tvdatechosen);
        tvdatechosenval = findViewById(R.id.tvdatechosenval);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView text_title = findViewById(R.id.text_title);
        text_title.setText(getResources().getString(R.string.ovulation));
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.yellowcolorPrimary);

    }
}
