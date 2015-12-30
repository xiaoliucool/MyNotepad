package com.xiaoliu.mynotepad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.xiaoliu.adapter.MyAdapter;
import com.xiaoliu.db.dao.NotesDao;
import com.xiaoliu.domain.Note;

import java.util.List;

public class MainActivity extends Activity {
    //listView的适配器
    private MyAdapter adapter;
    //主界面上的控件
    private ListView listView;
    private Button addBtn;
    //数据库访问
    private NotesDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    public void initViews(){
        listView = (ListView) findViewById(R.id.lv);
        addBtn = (Button) findViewById(R.id.btn);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }
}
