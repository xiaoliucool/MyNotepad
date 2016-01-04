package com.xiaoliu.mynotepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.xiaoliu.adapter.MyAdapter;
import com.xiaoliu.db.ImgDBHelper;
import com.xiaoliu.db.dao.ImgDao;
import com.xiaoliu.db.dao.NotesDao;

public class MainActivity extends Activity implements View.OnClickListener{
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
        addBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new MyAdapter(this);
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
