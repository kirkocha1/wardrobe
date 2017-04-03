package com.kirill.kochnev.homewardrope.db;

import android.content.Context;
import android.util.Log;

import com.kirill.kochnev.homewardrope.db.models.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class WardropeSqlHelper extends DaoMaster.DevOpenHelper {
    public static final String TAG = "WardropeSqlHelper";

    public WardropeSqlHelper(Context context, String name) {
        super(context, name);
        Log.d(TAG, "constructor");
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);

    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
