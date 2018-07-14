package com.kirill.kochnev.homewardrobe.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.kirill.kochnev.homewardrobe.R;

/**
 * Created by kirill on 02.05.17.
 */

public class DialogHelper {
    public static void showErrorSnackBar(Context context, int message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
        snackbar.show();
    }

    public static void showOKCancelDialog(Context context, String title, View view, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener cancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        builder.setView(view).setPositiveButton(R.string.yes, ok).setNegativeButton(R.string.no, cancel).create().show();
    }
}
