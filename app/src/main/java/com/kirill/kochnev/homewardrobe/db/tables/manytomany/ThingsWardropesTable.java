package com.kirill.kochnev.homewardrobe.db.tables.manytomany;

import android.provider.BaseColumns;

/**
 * Created by kirill on 03.04.17.
 */

public class ThingsWardropesTable implements BaseColumns {

    public static final String THINGS_WARDROBES_TABLE = "things_wardrobes";
    public static final String THINGS_WARDROBES_THING_ID = "thing_id";
    public static final String THINGS_WARDROBES_WARDROBES_ID = "wardrobe_id";

    public static final String CREATE_THINGS_WARDROBES_TABLE = "CREATE TABLE " + THINGS_WARDROBES_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY NOT NULL, " +
            THINGS_WARDROBES_THING_ID + " INTEGER NOT NULL, " +
            THINGS_WARDROBES_WARDROBES_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + THINGS_WARDROBES_WARDROBES_ID + ") REFERENCES wardrobes(" + BaseColumns._ID + "), "
            + "FOREIGN KEY(" + THINGS_WARDROBES_THING_ID + ") REFERENCES things(" + BaseColumns._ID + ")" + ")";
}
