package com.kirill.kochnev.homewardrope.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrope.db.WardropeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksTable {

    public static final String LOOKS_TABLE = "looks";
    public static final String LOOK_NAME = "look_name";
    public static final String LOOK_TAG = "look_tag";
    public static final String LOOK_FULL_IMAGE_PATH = "look_full_image_path";
    public static final String LOOK_ICON_IMAGE_PATH = "look_icon_image_path";

    public static final String CREATE_LOOKS_TABLE = "CREATE TABLE " + LOOKS_TABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL," +
            LOOK_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            LOOK_TAG + " TEXT, " +
            LOOK_FULL_IMAGE_PATH + " TEXT, " +
            LOOK_ICON_IMAGE_PATH + " TEXT)";

}
