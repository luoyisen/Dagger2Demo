package com.example.i.dagger2demo;

import android.content.Context;

/***
 * Created by I on 2017/9/25.
 */

public class DisplayMetricsConvert {

    public static int pxToDp(Context context, int px) {
        double density = context.getResources().getDisplayMetrics().density;
        return (int) ((px + 0.5) * density);
    }
}