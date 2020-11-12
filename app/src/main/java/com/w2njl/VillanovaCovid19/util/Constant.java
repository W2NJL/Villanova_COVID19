package com.w2njl.VillanovaCovid19.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class Constant {
    public static GradientDrawable getShapeDrawble(boolean isStroke,int primaryColor) {


        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{8, 8, 8, 8, 8, 8, 8, 8});
        shape.setColor(primaryColor);

        if (isStroke) {
            shape.setStroke(3, primaryColor);
            shape.setColor(Color.WHITE);
        }

        return shape;
    }


}
