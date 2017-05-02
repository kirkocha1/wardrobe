package com.kirill.kochnev.homewardrope.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by kirill on 02.05.17.
 */

public class DialogHelper {
    public static void showErrorSnackBar(Context context, int message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
        snackbar.show();
    }
}
