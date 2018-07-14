package com.kirill.kochnev.homewardrobe.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrobe.db.WardrobeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 03.04.17.
 */

public class ThingsTable implements BaseColumns {

    public static final String THINGS_TABLE = "things";
    public static final String THING_NAME = "thing_name";
    public static final String THING_TAG = "thing_tag";
    public static final String THING_FULL_IMAGE_PATH = "thing_full_image_path";
    public static final String THING_ICON_IMAGE_PATH = "thing_icon_image_path";

    public static final String CREATE_THINGS_TABLE = "CREATE TABLE " + THINGS_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY NOT NULL," +
            THING_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            THING_TAG + " TEXT, " +
            THING_FULL_IMAGE_PATH + " TEXT, " +
            THING_ICON_IMAGE_PATH + " TEXT)";
}
