package com.kirill.kochnev.homewardrope.db.tables;

import android.provider.BaseColumns;

import static com.kirill.kochnev.homewardrope.db.WardrobeSqlHelper.CREATION_DATE;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksTable implements BaseColumns {

    public static final String LOOKS_TABLE = "looks";
    public static final String LOOK_NAME = "look_name";
    public static final String LOOK_TAG = "look_tag";
    public static final String LOOK_WARDROBE_ID = "look_wardrobe_id";
    public static final String LOOK_FULL_IMAGE_PATH = "look_full_image_path";
    public static final String LOOK_ICON_IMAGE_PATH = "look_icon_image_path";

    public static final String CREATE_LOOKS_TABLE = "CREATE TABLE " + LOOKS_TABLE + " (" +
            _ID + " INTEGER PRIMARY KEY NOT NULL," +
            LOOK_NAME + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            LOOK_TAG + " TEXT, " +
            LOOK_FULL_IMAGE_PATH + " TEXT, " +
            LOOK_ICON_IMAGE_PATH + " TEXT, " +
            LOOK_WARDROBE_ID + " TEXT, " +
            "FOREIGN KEY (" + LOOK_WARDROBE_ID + ") REFERENCES " + WardrobeTable.WARDROBE_TABLE + "(" + WardrobeTable._ID + ")" +
            ")";

    public static String updateWardrobeId(String range, Long wardropeId) {
        return "UPDATE " + LooksTable.LOOKS_TABLE +
                " SET " + LooksTable.LOOK_WARDROBE_ID + " = " + wardropeId +
                " WHERE " + LooksTable._ID + " IN (" + range + ")";
    }

    public static String dropWardrobeId(Long wardropeId) {
        return "UPDATE " + LooksTable.LOOKS_TABLE +
                " SET " + LooksTable.LOOK_WARDROBE_ID + " = " + null +
                " WHERE " + LooksTable.LOOK_WARDROBE_ID + " = " + wardropeId;
    }

}
