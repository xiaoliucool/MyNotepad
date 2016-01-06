package com.xiaoliu.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.xiaoliu.db.NotesDBHelper;
import com.xiaoliu.domain.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class NotesDao {
    private NotesDBHelper notesDBHelper;

    public NotesDao(Context context) {
        notesDBHelper = new NotesDBHelper(context);
    }

    /**
     * 向数据库里添加数据
     */
    public void add(String content, String time, int img, String video) {
        SQLiteDatabase db = notesDBHelper.getWritableDatabase();
        db.execSQL("insert into notes (content, time, img, video) values (?,?, ?,?)", new Object[]{content, time, img, video});
        db.close();
    }

    /**
     * 查询所有数据
     */
    public List<Note> findAll() {
        SQLiteDatabase db = notesDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from notes", null);
        ArrayList<Note> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Note note = getNote(cursor);
            list.add(note);
        }
        db.close();
        return list;
    }

    @NonNull
    private Note getNote(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(NotesDBHelper.ID));
        String content = cursor.getString(cursor.getColumnIndex(NotesDBHelper.CONTENT));
        String time = cursor.getString(cursor.getColumnIndex(NotesDBHelper.TIME));
        int img = cursor.getInt(cursor.getColumnIndex(NotesDBHelper.IMG));
        System.out.println("note中图片的id"+img);
        String video = cursor.getString(cursor.getColumnIndex(NotesDBHelper.VIDEO));
        Note note = new Note();
        note.setId(id);
        note.setContent(content);
        note.setImg(img);
        note.setTime(time);
        note.setVideo(video);
        return note;
    }

    /**
     * 根据id获取单条数据
     */
    public Note find(int id){
        SQLiteDatabase db = notesDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from notes where _id = " + id, null);
        cursor.moveToFirst();
        Note note = getNote(cursor);
        db.close();
        return note;
    }

    /**
     * 删除数据库的某条数据
     */
    public void delete(int id) {
        SQLiteDatabase db = notesDBHelper.getWritableDatabase();
        db.execSQL("delete from notes where _id = ?", new Object[]{id});
        db.close();
    }

    /**
     * 修改数据库里某条数据
     */
    public void update(int id, String content, String time, String img, String video) {
        SQLiteDatabase db = notesDBHelper.getWritableDatabase();
        db.execSQL("update notes set content=?, time=?, img=?, video=? where _id = ?", new Object[]{content, time, img, video, id});
        db.close();
    }
}
