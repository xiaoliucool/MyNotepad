package com.xiaoliu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoliu.db.dao.NotesDao;
import com.xiaoliu.domain.Note;
import com.xiaoliu.mynotepad.R;

import java.util.List;


/**
 * Created by Administrator on 2015/12/29.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Note> list;
    public MyAdapter(Context context, List<Note> list){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contentTextView = (TextView) convertView.findViewById(R.id.item_tv_content);
            viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.item_tv_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String content = list.get(position).getContent();
        String time = list.get(position).getTime();
        viewHolder.contentTextView.setText(content);
        viewHolder.timeTextView.setText(time);
        return convertView;
    }
    public final class ViewHolder {
        TextView contentTextView;
        TextView timeTextView;
    }
}
