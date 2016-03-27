package com.life.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.life.a666.R;
import com.life.entity.Comment;

import java.util.List;

/**
 * Created by 惟我独尊 on 2016/1/10.
 */
public class CommentAdapter extends BaseAdapter {
    protected Context context;
    protected List<Comment> dataList ;

    public CommentAdapter(Context context, List<Comment> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, null);
            viewHolder.userName = (TextView)convertView.findViewById(R.id.userName_comment);
            viewHolder.commentContent = (TextView)convertView.findViewById(R.id.content_comment);
            viewHolder.index = (TextView)convertView.findViewById(R.id.index_comment);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final Comment comment = dataList.get(position);
        if(comment.getUser()!=null){
            viewHolder.userName.setText(comment.getUser().getUsername());
//            LogUtils.i("CommentActivity","NAME:"+comment.getUser().getUsername());
        }else{
            viewHolder.userName.setText("未知");
        }
        viewHolder.index.setText((position+1)+"楼");
        viewHolder.commentContent.setText(comment.getCommentContent());

        return convertView;
    }

    public static class ViewHolder{
        public TextView userName;
        public TextView commentContent;
        public TextView index;
    }
}
