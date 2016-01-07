package com.xiaoliu.mynotepad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.xiaoliu.adapter.MyDetailGridViewAdapter;
import com.xiaoliu.db.dao.ImgDao;
import com.xiaoliu.domain.Img;
import com.xiaoliu.utils.ImgUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class DetailActivity extends Activity implements View.OnClickListener{
    //控件
    private TextView contentTv;
    private Button expandBt;
    private GridView photoGv;
    //gridView的适配器
    private MyDetailGridViewAdapter adapter;
    private List<Bitmap> photoslist;
    private ImgDao dao ;
    //意图中携带的数据
    private String content;
    private int imgID;
    //判断展开按钮的状态
    private boolean isExpand = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        imgID = intent.getIntExtra("imgID", 0);
        System.out.println("获取的图片ID:"+imgID);
        dao = new ImgDao(this);
        initViews();
    }

    public void initViews(){
        contentTv = (TextView) findViewById(R.id.detail_tv);
        expandBt = (Button) findViewById(R.id.expand);
        photoGv = (GridView) findViewById(R.id.gridView_detail);
        contentTv.setText(content);
        expandBt.setOnClickListener(this);
        photoslist = new ArrayList<>();
        Img img = dao.find(imgID);
        if (!TextUtils.isEmpty(img.getUrl1())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl1(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl2())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl2(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl3())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl3(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl4())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl4(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl5())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl5(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl6())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl6(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl7())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl7(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl8())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl8(),100,100));
        }
        if (!TextUtils.isEmpty(img.getUrl9())){
            photoslist.add(ImgUtils.decodeSampledBitmapFromFile(img.getUrl9(),100,100));
        }
        adapter = new MyDetailGridViewAdapter(this, photoslist,R.layout.grid_detail_item);
        photoGv.setAdapter(adapter);
        photoGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DetailActivity.this, ImageDetailsActivity.class);
                intent.putExtra("image_position",i);
                intent.putExtra("imgID", imgID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (!isExpand) {
            expandBt.setText("关闭");
            photoGv.setVisibility(View.VISIBLE);
        }else{
            expandBt.setText("展开");
            photoGv.setVisibility(View.GONE);
        }
        isExpand = !isExpand;
    }
}
