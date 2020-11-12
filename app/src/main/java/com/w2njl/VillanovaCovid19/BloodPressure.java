package com.w2njl.VillanovaCovid19;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.material.textfield.TextInputLayout;

import static com.w2njl.VillanovaCovid19.util.Constant.getShapeDrawble;

public class BloodPressure extends AppCompatActivity {


    /*
     * lower bp=1.5 or lower
     *
     *
     * */
    int primaryColor;
    EditText edSystolicBp, edDiastolicBp;
    Button calc, reset;
    TextInputLayout txtInputStstabolic;
    ImageView img_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        init();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edDiastolicBp.setText("");
                edSystolicBp.setText("");
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edSystolicBp.getText().toString())) {
//                    txtInputStstabolic.setError(getResources().getString(R.string.valid));
                    edSystolicBp.setError(getResources().getString(R.string.valid));

                    return;
                } else if (TextUtils.isEmpty(edDiastolicBp.getText().toString())) {
                    edDiastolicBp.setError(getResources().getString(R.string.valid));
                    return;
                } else {
                    float sBp = Float.parseFloat(edSystolicBp.getText().toString());
                    float dBp = Float.parseFloat(edDiastolicBp.getText().toString());
                    String result = "";
                    if (sBp > 180 || dBp > 110) {
                        result = getResources().getString(R.string.hypertensive_crisis);
                    } else if (sBp >= 160 || dBp >= 100) {
                        result = getResources().getString(R.string.high_bp_stage2);
                    } else if (sBp > 140 || dBp > 90) {
                        result = getResources().getString(R.string.high_bp_stage1);
                    } else if (sBp > 120 || dBp > 80) {
                        result = getResources().getString(R.string.prehypertension);
                    } else if (sBp > 80 && dBp > 60) {
                        result = getResources().getString(R.string.normal_bp);
                    } else {
                        result = getResources().getString(R.string.low_bp);
                    }
                    Intent intent = new Intent(getApplicationContext(), BpResult.class);
                    intent.putExtra(AppMeasurementSdk.ConditionalUserProperty.VALUE, result);
                    startActivity(intent);


//                    if (sBp < 80 && dBp < 60) {
//                        result = getResources().getString(R.string.low_bp);
//                    }
//                    else if ()

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void init() {
        primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.orangecolorPrimary);
        edSystolicBp = findViewById(R.id.edSystolicBp);
        edDiastolicBp = findViewById(R.id.edDiastolicBp);
        img_back = findViewById(R.id.img_back);
        reset = findViewById(R.id.reset);
        calc = findViewById(R.id.calc);
        txtInputStstabolic = findViewById(R.id.txtInputStstabolic);
        reset.setBackground(getShapeDrawble(false, primaryColor));
        calc.setBackground(getShapeDrawble(false, primaryColor));
    }
}
