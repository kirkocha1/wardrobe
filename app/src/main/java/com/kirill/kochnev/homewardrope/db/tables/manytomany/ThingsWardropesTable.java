package com.kirill.kochnev.homewardrope.db.tables.manytomany;

import android.provider.BaseColumns;

/**
 * Created by kirill on 03.04.17.
 */

public class ThingsWardropesTable implements BaseColumns {

    public static final String THINGS_WARDROPES_TABLE = "things_wardropes";
    public static final String THINGS_WARDROPES_THING_ID = "thing_id";
    public static final String THINGS_WARDROPES_WARDROPES_ID = "wardrope_id";

    public static final String CREATE_THINGS_WARDROPES_TABLE = "CREATE TABLE " + THINGS_WARDROPES_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY NOT NULL, " +
            THINGS_WARDROPES_THING_ID + " INTEGER NOT NULL, " +
            THINGS_WARDROPES_WARDROPES_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + THINGS_WARDROPES_WARDROPES_ID + ") REFERENCES wardrobes(" + BaseColumns._ID + "), "
            + "FOREIGN KEY(" + THINGS_WARDROPES_THING_ID + ") REFERENCES things(" + BaseColumns._ID + ")" + ")";
}
