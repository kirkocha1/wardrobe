package com.kirill.kochnev.homewardrope.db;

import android.content.Context;
import android.util.Log;

import com.kirill.kochnev.homewardrope.db.models.DaoMaster;
import com.kirill.kochnev.homewardrope.db.models.DaoSession;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;

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
        testData(db);
    }

    private void testData(Database db) {
        DaoSession session = new DaoMaster(db).newSession();
        session.getThingDao().insertOrReplace(new Thing("T-short"));
        session.getThingDao().insertOrReplace(new Thing("skirt"));
        session.getThingDao().insertOrReplace(new Thing("Thousers"));
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
