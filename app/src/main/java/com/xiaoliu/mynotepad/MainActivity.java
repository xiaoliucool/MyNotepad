package com.xiaoliu.mynotepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.xiaoliu.adapter.MyAdapter;
import com.xiaoliu.db.ImgDBHelper;
import com.xiaoliu.db.dao.ImgDao;
import com.xiaoliu.db.dao.NotesDao;
import com.xiaoliu.domain.Note;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    //listView的适配器
    private MyAdapter adapter;
    //主界面上的控件
    private ListView listView;
    private Button addBtn;
    //数据库访问
    private NotesDao dao;
    private List<Note> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    public void initViews(){
        listView = (ListView) findViewById(R.id.lv);
        addBtn = (Button) findViewById(R.id.btn);
        addBtn.setOnClickListener(this);
        dao = new NotesDao(this);
        list = dao.findAll();
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                System.out.println("listview positon:"+i);
                Note note = list.get(i);
                String content = note.getContent();
                int imgID = note.getImg();
                intent.putExtra("content", content);
                intent.putExtra("imgID", imgID);
                System.out.println("文本:" + content);
                System.out.println("图片的ID:"+imgID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = dao.findAll();
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn :
                Intent intent = new Intent(this, AddNewNoteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
