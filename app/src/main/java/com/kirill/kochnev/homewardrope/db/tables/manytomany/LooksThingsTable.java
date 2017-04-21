package com.kirill.kochnev.homewardrope.db.tables.manytomany;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.tables.LooksTable;
import com.kirill.kochnev.homewardrope.db.tables.ThingsTable;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksThingsTable {

    public static final String LOOKS_THINGS_TABLE = "looks_things";
    public static final String LOOKS_THINGS_THING_ID = "thing_id";
    public static final String LOOKS_THINGS_LOOK_ID = "look_id";

    public static final String CREATE_LOOKS_THINGS_TABLE = "CREATE TABLE " + LOOKS_THINGS_TABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
            LOOKS_THINGS_THING_ID + " INTEGER NOT NULL, " +
            LOOKS_THINGS_LOOK_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + LOOKS_THINGS_LOOK_ID + ") REFERENCES " + LooksTable.LOOKS_TABLE + "(" + BaseColumns._ID + "), "
            + "FOREIGN KEY(" + LOOKS_THINGS_THING_ID + ") REFERENCES " + ThingsTable.THINGS_TABLE + "(" + BaseColumns._ID + ")" + ")";
}
