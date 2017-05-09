package com.iu.ajmiyash.i399_ajmiyash_smartscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Adam on 3/5/2017.
 */

public class DBHandler extends SQLiteAssetHelper {
    //private static final String DATABASE_NAME="universityDB.db";

    private static final String DATABASE_NAME="classDB.db";
    private static final int DATABASE_VERSION=1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }

}
