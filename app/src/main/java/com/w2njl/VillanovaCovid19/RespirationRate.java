package com.w2njl.VillanovaCovid19;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class RespirationRate extends AppCompatActivity {
    TextView tvdatechosenval;
    Button reset, calc;
    int primaryColor;
    ImageView img_back;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.CyanTheme);
        setContentView(R.layout.breath_count);
        init();
        final Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        int i2 = instance.get(Calendar.MONTH);
        int i3 = instance.get(Calendar.DATE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final DatePickerDialog.OnDateSetListener r6 = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                instance.set(Calendar.YEAR, i);
                instance.set(Calendar.MONTH, i2);
                instance.set(Calendar.DATE, i3);
                String[] split = simpleDateFormat.format(instance.getTime()).split("/");
                String str = " ";
                switch (Integer.parseInt(split[0])) {
                    case 1:
                        TextView textView = RespirationRate.this.tvdatechosenval;
                        String sb = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.jan) +
                                str +
                                split[2];
                        textView.setText(sb);
                        break;
                    case 2:
                        TextView textView2 = RespirationRate.this.tvdatechosenval;
                        String sb2 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.feb) +
                                str +
                                split[2];
                        textView2.setText(sb2);
                        break;
                    case 3:
                        TextView textView3 = RespirationRate.this.tvdatechosenval;
                        String sb3 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.mar) +
                                str +
                                split[2];
                        textView3.setText(sb3);
                        break;
                    case 4:
                        TextView textView4 = RespirationRate.this.tvdatechosenval;
                        String sb4 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.apr) +
                                str +
                                split[2];
                        textView4.setText(sb4);
                        break;
                    case 5:
                        TextView textView5 = RespirationRate.this.tvdatechosenval;
                        String sb5 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.may) +
                                str +
                                split[2];
                        textView5.setText(sb5);
                        break;
                    case 6:
                        TextView textView6 = RespirationRate.this.tvdatechosenval;
                        String sb6 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.jun) +
                                str +
                                split[2];
                        textView6.setText(sb6);
                        break;
                    case 7:
                        TextView textView7 = RespirationRate.this.tvdatechosenval;
                        String sb7 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.jul) +
                                str +
                                split[2];
                        textView7.setText(sb7);
                        break;
                    case 8:
                        TextView textView8 = RespirationRate.this.tvdatechosenval;
                        String sb8 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.aug) +
                                str +
                                split[2];
                        textView8.setText(sb8);
                        break;
                    case 9:
                        TextView textView9 = RespirationRate.this.tvdatechosenval;
                        String sb9 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.sep) +
                                str +
                                split[2];
                        textView9.setText(sb9);
                        break;
                    case 10:
                        TextView textView10 = RespirationRate.this.tvdatechosenval;
                        String sb10 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.oct) +
                                str +
                                split[2];
                        textView10.setText(sb10);
                        break;
                    case 11:
                        TextView textView11 = RespirationRate.this.tvdatechosenval;
                        String sb11 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.nov) +
                                str +
                                split[2];
                        textView11.setText(sb11);
                        break;
                    case 12:
                        TextView textView12 = RespirationRate.this.tvdatechosenval;
                        String sb12 = split[1] +
                                str +
                                RespirationRate.this.getResources().getString(R.string.dec) +
                                str +
                                split[2];
                        textView12.setText(sb12);
                        break;
                }
            }
        };

        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        tvdatechosenval.setText(simpleDateFormat.format(instance.getTime()));

        View.OnClickListener r0 = new View.OnClickListener() {
            public void onClick(View view) {
                instance.set(i4, i5, i6);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RespirationRate.this, r6, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE));
                datePickerDialog.show();
            }
        };
        reset.setOnClickListener(r0);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar instanceNew = Calendar.getInstance();
                if (simpleDateFormat.format(instance.getTime()).equals(simpleDateFormat.format(instanceNew.getTime())) || instance.getTime().after(instanceNew.getTime())) {
                    Toast.makeText(RespirationRate.this, getResources().getString(R.string.valid_date), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    getDiffBetweenDate(instance);
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void getDiffBetweenDate(Calendar dateBirth) {
//        Calendar dateCurrent=Calendar.getInstance();
        long msDiff = Calendar.getInstance().getTimeInMillis() - dateBirth.getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

//        Calendar birthDay = dateBirth;
//        Calendar birthDay = new GregorianCalendar(1955, Calendar.MAY, 19);
        Calendar today = Calendar.getInstance();
//        today.setTime(new Date());
        Log.e("yearrrrrget==", "--" + simpleDateFormat.format(dateBirth.getTime()));
        int yearsInBetween = today.get(Calendar.YEAR)
                - dateBirth.get(Calendar.YEAR);
        int monthsDiff = today.get(Calendar.MONTH)
                - dateBirth.get(Calendar.MONTH);
//        long ageInMonths = yearsInBetween*12 + monthsDiff;
//        long age = yearsInBetween;

        Date dateBefore = dateBirth.getTime();
        Date dateAfter = today.getTime();
        long difference = dateAfter.getTime() - dateBefore.getTime();
        int daysBetween = (int) (difference / (1000 * 60 * 60 * 24));

        Log.e("year,month-day==", "--" + yearsInBetween + "," + monthsDiff + "," + daysBetween);

        float daysAdd = 0;
        int totalDays = daysBetween;

//        long diff1 = Date.UTC(today.YEAR, today.MONTH- 1,today.DAY_OF_YEAR, 0, 0, 0) - Date.UTC(dateBirth.YEAR, dateBirth.MONTH - 1,dateBirth.DAY_OF_YEAR, 0, 0, 0);
        long diff1 = difference;
        Log.e("difff==", "---" + diff1);
        float secleft = diff1 / 1000 / 60;
        float hrsleft = secleft / 60;
        float daysleft = (hrsleft / 24) + daysAdd;
        int breaths = (int) daysleft;
        if (yearsInBetween >= 1 && yearsInBetween <= 5) {

            breaths = breaths * 25 * 1440;


        } else if (yearsInBetween > 5) {
            breaths = breaths * 11 * 1440;

        } else {
            if (monthsDiff < 6) {
                breaths = breaths * 45 * 1440;
            } else {
                breaths = breaths * 27 * 1440;
            }

        }
        Intent intent = new Intent(this, RespirationRateResult.class);
        intent.putExtra(AppMeasurementSdk.ConditionalUserProperty.VALUE, String.valueOf(breaths));
        startActivity(intent);
        Log.e("breathcounts===", "0---" + breaths);


    }

    private void init() {
        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.cyancolorPrimary);
        img_back = findViewById(R.id.img_back);
        reset = findViewById(R.id.reset);
        calc = findViewById(R.id.calc);
        tvdatechosenval = findViewById(R.id.tvdatechosenval);
        reset.setBackground(getShapeDrawble(false, primaryColor));
        calc.setBackground(getShapeDrawble(false, primaryColor));

    }

}
