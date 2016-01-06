package com.xiaoliu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/1/4.
 */
public class ImgDBHelper extends SQLiteOpenHelper{
    //数据库表相关列名
    public static final String TABLE_NAME = "imgs";
    public static final String ID = "_id";
    public static final String IMGURL1 = "img_url1";
    public static final String IMGURL2 = "img_url2";
    public static final String IMGURL3 = "img_url3";
    public static final String IMGURL4 = "img_url4";
    public static final String IMGURL5 = "img_url5";
    public static final String IMGURL6 = "img_url6";
    public static final String IMGURL7 = "img_url7";
    public static final String IMGURL8 = "img_url8";
    public static final String IMGURL9 = "img_url9";

    public ImgDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("IMG","success!");
        db.execSQL("create table " + TABLE_NAME + "("
                        + ID + " integer primary key autoincrement, "
                        + IMGURL1 + " text, "
                        + IMGURL2 + " text, "
                        + IMGURL3 + " text, "
                        + IMGURL4 + " text, "
                        + IMGURL5 + " text, "
                        + IMGURL6 + " text, "
                        + IMGURL7 + " text, "
                        + IMGURL8 + " text, "
                        + IMGURL9 + " text)"
        );
        Log.i("IMG","图片数据库创建成功!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
