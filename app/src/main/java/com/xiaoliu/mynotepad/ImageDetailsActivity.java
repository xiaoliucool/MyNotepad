package com.xiaoliu.mynotepad;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoliu.db.dao.ImgDao;
import com.xiaoliu.domain.Img;
import com.xiaoliu.utils.ImageLoader;
import com.xiaoliu.utils.ImgUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public class ImageDetailsActivity extends Activity implements ViewPager.OnPageChangeListener {

    /**
     * 用于管理图片的滑动
     */
    private ViewPager viewPager;

    /**
     * 显示当前图片的页数
     */
    private TextView pageText;
    //
    private ImgDao dao ;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_details);
        int imagePosition = getIntent().getIntExtra("image_position", 0);
        int imgID = getIntent().getIntExtra("imgID", 0);
        getImgUrls(imgID);
        pageText = (TextView) findViewById(R.id.page_text);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(imagePosition);
        viewPager.setOnPageChangeListener(this);
        // 设定当前的页数和总页数
        pageText.setText((imagePosition + 1) + "/" + list.size());
    }

    /**
     * 获取图片路径
     * @param imgID
     */
    public void getImgUrls(int imgID){
        dao = new ImgDao(this);
        Img img = dao.find(imgID);
        list = new ArrayList<>();
        if (!TextUtils.isEmpty(img.getUrl1())){
            list.add(img.getUrl1());
        }
        if (!TextUtils.isEmpty(img.getUrl2())){
            list.add(img.getUrl2());
        }
        if (!TextUtils.isEmpty(img.getUrl3())){
            list.add(img.getUrl3());
        }
        if (!TextUtils.isEmpty(img.getUrl4())){
            list.add(img.getUrl4());
        }
        if (!TextUtils.isEmpty(img.getUrl5())){
            list.add(img.getUrl5());
        }
        if (!TextUtils.isEmpty(img.getUrl6())){
            list.add(img.getUrl6());
        }
        if (!TextUtils.isEmpty(img.getUrl7())){
            list.add(img.getUrl7());
        }
        if (!TextUtils.isEmpty(img.getUrl8())){
            list.add(img.getUrl8());
        }
        if (!TextUtils.isEmpty(img.getUrl9())){
            list.add(img.getUrl9());
        }
    }

    /**
     * ViewPager的适配器
     *
     * @author guolin
     */
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String imagePath = list.get(position);
            Bitmap bitmap = ImageLoader.getInstance().getBitmapFromMemoryCache(imagePath);
            //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            System.out.println("新载入");
            if (bitmap == null) {
                bitmap = ImgUtils.decodeSampledBitmapFromFile(imagePath, 200, 200);
                ImageLoader.getInstance().addBitmapToCache(imagePath,bitmap);
                /*bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.gridview_addpic);*/
            }
            View view = LayoutInflater.from(ImageDetailsActivity.this).inflate(
                    R.layout.zoom_image, null);
            ImageView zoomImageView = (ImageView) view
                    .findViewById(R.id.photo_iv);
            zoomImageView.setImageBitmap(bitmap);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int currentPage) {
        // 每当页数发生改变时重新设定一遍当前的页数和总页数
        pageText.setText((currentPage + 1) + "/" + list.size());
    }

}
