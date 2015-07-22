package com.example.matchcontroller.services;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matchcontroller.activitys.MainActivity;

/**
 * Created by BAKA on 2015/7/22.
 */
public class SQLiteService {
    private static SQLiteDatabase db;

    public static boolean hasData(Activity activity) {
        db = activity.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='match';", null);
        boolean result = false;
        if(c.moveToNext()){
            int count = c.getInt(0);
            if(count>0){
                result = true;
            }
        }
        c.close();
        db.close();
        return result;
    }

    public static void createNewMatch(Activity activity, ContentValues cv) {
        db = activity.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE match (id INTEGER PRIMARY KEY AUTOINCREMENT, name1 VARCHAR, name2, VARCHAR,set INT, set1 INT, set2 INT)");
        db.insert("match", null, cv);
        db.close();
    }

    public static void saveMatchData(Activity activity, ContentValues cv) {
        db = activity.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.update("match", cv, "id=?", new String[]{"1"});
        db.close();
    }






}
