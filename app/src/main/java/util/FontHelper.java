package util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

public class FontHelper {

    public static void setDefaultFont(String nameOfFont) {


    }


    public static void changeBackground(Activity activity, int layoutId, int color) {
        RelativeLayout r1 = (RelativeLayout) activity.findViewById(layoutId);
        r1.setBackgroundColor(color);
    }
}
