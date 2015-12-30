package com.xiaoliu.mynotepad;

import android.test.AndroidTestCase;

import com.xiaoliu.db.dao.NotesDao;
import com.xiaoliu.utils.TimeUtils;

/**
 * Created by Administrator on 2015/12/29.
 */
public class TestDB extends AndroidTestCase {
    /*public void testAdd(){
        NotesDao dao = new NotesDao(getContext());
        for (int i= 0;i<10;i++){
            dao.add("我是测试数据"+i,"2015/5/6 11:21:45","我是图片的url","我是视频的url");
        }
        System.out.println("测试完成！");
    }*/
    public void testGetImgName(){
        TimeUtils.getTime();
    }
}
