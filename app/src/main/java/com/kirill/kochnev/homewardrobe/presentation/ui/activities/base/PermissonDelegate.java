package com.kirill.kochnev.homewardrobe.presentation.ui.activities.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;


public final class PermissonDelegate {

    private final int PERMISSION_REQUEST_CODE = 1;
    private final String LOG_TAG = PermissonDelegate.class.getSimpleName();

    public interface PermissionHandler {
        void handlePermission(String permission);
    }

    private final @NonNull
    Activity activity;


    public PermissonDelegate(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public void askPermisson(@NonNull final String... permissons) {
        ActivityCompat.requestPermissions(activity, permissons, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(
            final int requestCode,
            @NonNull final String[] permissions,
            @NonNull final int[] grantResults,
            @NonNull final PermissionHandler onPermissonGranted,
            @NonNull final PermissionHandler onPermissonRejected
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.length != 0)) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        try {
                            onPermissonGranted.handlePermission(permissions[i]);
                        } catch (Exception e) {
                            Log.e(LOG_TAG, e.getLocalizedMessage());
                        }
                    } else {
                        try {
                            onPermissonRejected.handlePermission(permissions[i]);
                        } catch (Exception e) {
                            Log.e(LOG_TAG, e.getLocalizedMessage());
                        }
                    }
                }
            }
        }
    }
}
