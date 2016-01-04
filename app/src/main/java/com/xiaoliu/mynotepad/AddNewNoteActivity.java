package com.xiaoliu.mynotepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.xiaoliu.adapter.MyGridViewAdapter;
import com.xiaoliu.db.dao.ImgDao;
import com.xiaoliu.db.dao.NotesDao;
import com.xiaoliu.utils.ImgUtils;
import com.xiaoliu.utils.TimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
public class AddNewNoteActivity extends Activity implements View.OnClickListener {
    //适配器和数据
    private List<Bitmap> list;
    private MyGridViewAdapter adapter;
    private List<String> imgUrls;
    //控件
    private GridView gridView;
    private EditText noteEt;
    private Button saveBtn;
    private Button cancelBtn;
    //图片路径
    private String imageFilePath;
    //图片的数量
    private static int photos = 0;
    //数据库
    private ImgDao imgDao;
    private NotesDao notesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        initView();
    }

    public void initView() {
        gridView = (GridView) findViewById(R.id.gridView);
        noteEt = (EditText) findViewById(R.id.note_et);
        saveBtn = (Button) findViewById(R.id.save_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        list = new ArrayList<>(9);
        imgUrls = new ArrayList<>(9);
        for(int i=0;i<9;i++){
            imgUrls.add(i,"");
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
        list.add(bitmap);
        adapter = new MyGridViewAdapter(this, list, R.layout.grid_item);
        imgDao = new ImgDao(this);
        notesDao = new NotesDao(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new MyGridViewAdapter(this, list, R.layout.grid_item);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.size() > 9) {
                    Toast.makeText(AddNewNoteActivity.this, "您所添加的图片太多，请删除几张", Toast.LENGTH_LONG).show();
                }
                if (position == 0) {
                    selectCameraOrPhoto();
                    //Toast.makeText(AddNewNoteActivity.this, "添加照片", Toast.LENGTH_LONG).show();
                } else {
                    deleteImage(position);
                    Toast.makeText(AddNewNoteActivity.this, "删除照片", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void selectCameraOrPhoto() {
        final String items[] = {"拍照", "从相册里选择"};
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("请选择操作"); //设置标题
        //builder.setMessage("是否确认退出?"); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+"mynote"+ File.separator + TimeUtils.getImgName();
                        System.out.println("图片暂存路径："+imageFilePath);
                        File temp = new File(imageFilePath);
                        if(!temp.getParentFile().exists())
                            System.out.println("创建新的目录成功！");
                            temp.getParentFile().mkdirs();
                        try {
                            temp.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("AddNewActivity", imageFilePath);
                        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                        it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
                        startActivityForResult(it, 102);
                        break;
                    case 1:
                        //选择图片
                        Intent intent2Album = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent2Album, 100);
                        break;
                }
                Toast.makeText(AddNewNoteActivity.this, items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void deleteImage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewNoteActivity.this);
        builder.setMessage("确认要删除图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                list.remove(position);
                imgUrls.remove(position);
                photos--;
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                String imgURL = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                Bitmap bitmap = ImgUtils.decodeSampledBitmapFromFile(imgURL, 100, 100);
                //Bitmap bitmap = BitmapFactory.decodeFile(imgURL);
                list.add(bitmap);
                photos++;
                imgUrls.add(photos, imgURL);
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && requestCode == 102) {
            Log.i("AddNewActivity", imageFilePath);
            Log.i("AddNewActivity", requestCode + "");
            Bitmap bitmap = ImgUtils.decodeSampledBitmapFromFile(imageFilePath, 100, 100);
            list.add(bitmap);
            photos++;
            imgUrls.add(photos, imageFilePath);
            adapter.notifyDataSetChanged();
            Log.i("AddNewActivity", "添加数据成功");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_btn:
                saveData();
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 保存数据，插入到数据库中
     */
    private void saveData() {
        String text = noteEt.getText().toString().trim();
        int imgID = imgDao.getCount()+1;
        imgDao.add(imgUrls.get(0),imgUrls.get(1),imgUrls.get(2),imgUrls.get(3),imgUrls.get(4),imgUrls.get(5),imgUrls.get(6),imgUrls.get(7),imgUrls.get(8));
        notesDao.add(text,TimeUtils.getTime(),imgID,"");
    }
}
