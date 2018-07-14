package com.kirill.kochnev.homewardrobe.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrobe.db.WardrobeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 03.04.17.
 */

public class WardrobeTable implements BaseColumns {

    public static final String WARDROBE_TABLE = "wardrobes";
    public static final String WARDROBE_NAME = "wardrobe_name";
    public static final String WARDROBE_LOOKS_COUNT = "looks_count";
    public static final String WARDROBE_THINGS_COUNT = "things_count";

    public static final String CREATE_WARDROBES_TABLE = "CREATE TABLE " + WARDROBE_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY NOT NULL, " +
            WARDROBE_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            WARDROBE_LOOKS_COUNT + " INTEGER, " +
            WARDROBE_THINGS_COUNT + " INTEGER " +
            ")";


}
