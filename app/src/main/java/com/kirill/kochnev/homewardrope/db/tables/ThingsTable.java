package com.kirill.kochnev.homewardrope.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrope.db.WardropeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 03.04.17.
 */

public class ThingsTable {

    public static final String THINGS_TABLE = "things";
    public static final String THING_NAME = "thing_name";
    public static final String THING_TAG = "thing_tag";
    public static final String THING_FULL_IMAGE_PATH = "thing_full_image_path";
    public static final String THING_ICON_IMAGE_PATH = "thing_icon_image_path";

    public static final String CREATE_THINGS_TABLE = "CREATE TABLE " + THINGS_TABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL," +
            THING_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            THING_TAG + " TEXT, " +
            THING_FULL_IMAGE_PATH + " TEXT, " +
            THING_ICON_IMAGE_PATH + " TEXT)";
}
