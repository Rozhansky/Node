package com.example.alexander.node.Concrete;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alexander.node.Const;

/**
 * Created by W11B on 29.10.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "BDNotes",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Const.LOG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("CREATE TABLE note ("
                + "id integer primary key autoincrement,"
                + "id_parent integer,"
                + "str text,"
                + "rate integer,"
                + "favorite integer"
                + ");");
        db.execSQL("CREATE TABLE image ("
                + "id integer primary key autoincrement,"
                + "pos integer,"
                + "path string,"
                + "id_note integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
