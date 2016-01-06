package com.xiaoliu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xiaoliu.mynotepad.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class MyDetailGridViewAdapter extends BaseAdapter {
    private List<Bitmap> list;
    private LayoutInflater inflater;
    private Context context;
    private int resourceID;
    public MyDetailGridViewAdapter(Context context, List<Bitmap> list, int resourceID){
        this.context = context;
        this.list = list;
        this.resourceID = resourceID;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(resourceID,parent,false);
            holder.imgView = (ImageView) convertView.findViewById(R.id.grid_detail_iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgView.setImageBitmap(list.get(position));
        return convertView;
    }
    public class ViewHolder {
        ImageView imgView;
    }
}
