package com.kirill.kochnev.homewardrobe.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

import javax.inject.Inject;

public class FilePathBuilder {

    private Context context;

    @Inject
    public FilePathBuilder(Context context) {
        this.context = context;
    }

    public Uri buildFilePath(File path) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Uri.fromFile(path);
        } else {
            File file = new File(path.getPath());
            return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        }
    }
}
