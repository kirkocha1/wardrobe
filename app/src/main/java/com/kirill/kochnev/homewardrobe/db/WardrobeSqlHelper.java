package com.kirill.kochnev.homewardrobe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.kirill.kochnev.homewardrobe.db.tables.LooksTable.CREATE_LOOKS_TABLE;
import static com.kirill.kochnev.homewardrobe.db.tables.ThingsTable.CREATE_THINGS_TABLE;
import static com.kirill.kochnev.homewardrobe.db.tables.WardrobeTable.CREATE_WARDROBES_TABLE;
import static com.kirill.kochnev.homewardrobe.db.tables.manytomany.LooksThingsTable.CREATE_LOOKS_THINGS_TABLE;
import static com.kirill.kochnev.homewardrobe.db.tables.manytomany.ThingsWardropesTable.CREATE_THINGS_WARDROBES_TABLE;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class WardrobeSqlHelper extends SQLiteOpenHelper {
    private static final String TAG = "WardrobeSqlHelper";

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "wardrobe.db";

    public static final String CREATION_DATE = "creation_date";

    public WardrobeSqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDb(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDb(db, oldVersion, newVersion);
    }

    private void updateDb(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            switch (oldVersion) {
                case 0:
                    createTables(db);
                    oldVersion++;
                    break;
                case 1:
                    createLooks(db);
                    oldVersion++;
                    break;
            }
            updateDb(db, oldVersion, newVersion);
        }
    }

    private void createLooks(SQLiteDatabase db) {
        Log.d(TAG, CREATE_LOOKS_TABLE);
        Log.d(TAG, CREATE_LOOKS_THINGS_TABLE);

        db.execSQL(CREATE_LOOKS_TABLE);
        db.execSQL(CREATE_LOOKS_THINGS_TABLE);
    }

    private void createTables(SQLiteDatabase db) {
        Log.d(TAG, "DB CREATION");
        Log.d(TAG, CREATE_THINGS_TABLE);
        Log.d(TAG, CREATE_WARDROBES_TABLE);
        Log.d(TAG, CREATE_THINGS_WARDROBES_TABLE);

        db.execSQL(CREATE_THINGS_TABLE);
        db.execSQL(CREATE_WARDROBES_TABLE);
        db.execSQL(CREATE_THINGS_WARDROBES_TABLE);
    }
}
