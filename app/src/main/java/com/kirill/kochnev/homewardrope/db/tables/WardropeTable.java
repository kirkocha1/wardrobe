package com.kirill.kochnev.homewardrope.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrope.db.WardropeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 03.04.17.
 */

public class WardropeTable implements BaseColumns {

    public static final String WARDROPE_TABLE = "wardrobes";
    public static final String WARDROPE_NAME = "wardrope_name";
    public static final String WARDROPE_LOOKS_COUNT = "looks_count";
    public static final String WARDROPE_THINGS_COUNT = "things_count";

    public static final String CREATE_WARDROPES_TABLE = "CREATE TABLE " + WARDROPE_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY NOT NULL, " +
            WARDROPE_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            WARDROPE_LOOKS_COUNT + " INTEGER, " +
            WARDROPE_THINGS_COUNT + " INTEGER " +
            ")";


}
