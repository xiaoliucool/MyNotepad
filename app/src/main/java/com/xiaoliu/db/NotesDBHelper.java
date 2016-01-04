package com.xiaoliu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/12/29.
 */
public class NotesDBHelper extends SQLiteOpenHelper {

    //数据库表相关列名
    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String ID = "_id";
    public static final String IMG = "img";
    public static final String VIDEO = "video";
    public static final String TIME = "time";

    public NotesDBHelper(Context context) {
        super(context, "notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                        + ID + " integer primary key autoincrement, "
                        + CONTENT + " text not null, "
                        + TIME + " text not null, "
                        + IMG + " integer, "
                        + VIDEO + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
