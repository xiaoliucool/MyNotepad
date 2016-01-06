package com.xiaoliu.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.xiaoliu.db.ImgDBHelper;
import com.xiaoliu.db.NotesDBHelper;
import com.xiaoliu.domain.Img;
import com.xiaoliu.domain.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片存储数据库
 * Created by Administrator on 2015/12/29.
 */
public class ImgDao {
    private ImgDBHelper imgDBHelper;

    public ImgDao(Context context) {
        imgDBHelper = new ImgDBHelper(context);
    }

    /**
     * 向数据库里添加数据
     */
    public void add(String imgUrl1, String imgUrl2, String imgUrl3, String imgUrl4, String imgUrl5, String imgUrl6, String imgUrl7, String imgUrl8, String imgUrl9) {
        SQLiteDatabase db = imgDBHelper.getWritableDatabase();
        db.execSQL("insert into imgs (img_url1,img_url2,img_url3,img_url4,img_url5,img_url6,img_url7,img_url8,img_url9) " +
                        "values (?,?, ?,?,?,?,?, ?,?)",
                new Object[]{imgUrl1, imgUrl2, imgUrl3, imgUrl4, imgUrl5, imgUrl6, imgUrl7, imgUrl8, imgUrl9});
        Log.i("AddNewNoteActivity","插入图片成功");
        db.close();
    }

    /**
     * 查询所有数据
     */
    public List<Img> findAll() {
        SQLiteDatabase db = imgDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from imgs", null);
        ArrayList<Img> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Img img = getImg(cursor);
            list.add(img);
        }
        db.close();
        return list;
    }

    @NonNull
    private Img getImg(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ImgDBHelper.ID));
        String url1 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL1));
        String url2 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL2));
        String url3 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL3));
        String url4 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL4));
        String url5 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL5));
        String url6 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL6));
        String url7 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL7));
        String url8 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL8));
        String url9 = cursor.getString(cursor.getColumnIndex(ImgDBHelper.IMGURL9));

        Img img = new Img();
        System.out.println("图片路径:"+url1);
        System.out.println("图片路径2:"+url2);
        img.setId(id);
        img.setUrl1(url1);
        img.setUrl2(url2);
        img.setUrl3(url3);
        img.setUrl4(url4);
        img.setUrl5(url5);
        img.setUrl6(url6);
        img.setUrl7(url7);
        img.setUrl8(url8);
        img.setUrl9(url9);
        return img;
    }

    /**
     * 查询单条数据
     */
    public Img find(int id){
        SQLiteDatabase db = imgDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from imgs where _id = " + id, null);
        cursor.moveToFirst();
        Img img = getImg(cursor);
        if(img!=null){
            System.out.println("图片查询成功！");

        }
        db.close();
        return img;
    }
    /**
     * 删除数据库的某条数据
     */
    public void delete(int id) {
        SQLiteDatabase db = imgDBHelper.getWritableDatabase();
        db.execSQL("delete from imgs where _id = ?", new Object[]{id});
        db.close();
    }

    /**
     * 修改数据库里某条数据
     */
    public void update(int id, String imgUrl1, String imgUrl2, String imgUrl3, String imgUrl4, String imgUrl5, String imgUrl6, String imgUrl7, String imgUrl8, String imgUrl9) {
        SQLiteDatabase db = imgDBHelper.getWritableDatabase();
        db.execSQL("update notes set img_url1=?,img_url2=?,img_url3=?,img_url4=?,img_url5=?,img_url6=?,img_url7=?,img_url8=?,img_url9=? where _id = ?", new Object[]{imgUrl1,imgUrl2,imgUrl3,imgUrl4,imgUrl5,imgUrl6,imgUrl7,imgUrl8,imgUrl9, id});
        db.close();
    }
    /**
     * 获取数据库内容条数
     */
    public int getCount(){
        SQLiteDatabase db  = imgDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from imgs", null);
        cursor.getCount();
        System.out.println("图片数据库的条数:" + cursor.getCount());
        return cursor.getCount();
    }
}
