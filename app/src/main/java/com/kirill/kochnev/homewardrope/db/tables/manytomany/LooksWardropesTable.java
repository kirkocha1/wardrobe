package com.kirill.kochnev.homewardrope.db.tables.manytomany;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.tables.LooksTable;
import com.kirill.kochnev.homewardrope.db.tables.WardropeTable;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksWardropesTable {

    public static final String LOOKS_WARDROPES_TABLE = "looks_wardropes";
    public static final String LOOKS_WARDROPES_LOOK_ID = "look_id";
    public static final String LOOKS_WARDROPES_WARDROPES_ID = "wardrope_id";

    public static final String CREATE_LOOKS_WARDROPES_TABLE = "CREATE TABLE " + LOOKS_WARDROPES_TABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
            LOOKS_WARDROPES_LOOK_ID + " INTEGER NOT NULL, " +
            LOOKS_WARDROPES_WARDROPES_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + LOOKS_WARDROPES_WARDROPES_ID + ") REFERENCES " + WardropeTable.WARDROPE_TABLE + "(" + BaseColumns._ID + "), "
            + "FOREIGN KEY(" + LOOKS_WARDROPES_LOOK_ID + ") REFERENCES " + LooksTable.LOOKS_TABLE + "(" + BaseColumns._ID + ")" + ")";
}
