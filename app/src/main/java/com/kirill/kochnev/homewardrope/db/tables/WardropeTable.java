package com.kirill.kochnev.homewardrope.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrope.db.WardropeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 03.04.17.
 */

public class WardropeTable {

    public static final String WARDROPE_TABLE = "wardropes";
    public static final String WARDROPE_NAME = "wardrope_name";
    public static final String WARDROPE_COUNT = "wardrope_count";

    public static final String CREATE_WARDROPES_TABLE = "CREATE TABLE " + WARDROPE_TABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
            WARDROPE_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            WARDROPE_COUNT + " TEXT)";


}
