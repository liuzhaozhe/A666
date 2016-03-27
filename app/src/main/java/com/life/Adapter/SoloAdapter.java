package com.life.Adapter;

/**
 * Created by 惟我独尊 on 2016/1/10.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.life.a666.CommentActivity;
import com.life.a666.PersonalActivity;
import com.life.a666.R;
import com.life.application.MyApplication;
import com.life.entity.AllContent;

import java.util.ArrayList;

import com.life.entity.User;
import com.life.util.ActivityUtil;
import com.life.util.toast;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 惟我独尊 on 2015/12/13.
 */
public class SoloAdapter extends BaseAdapter {

    public ArrayList<AllContent> mListItems;
    public Context context;

    public SoloAdapter(ArrayList<AllContent> mListItems, Context context) {
        this.mListItems = mListItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mListItems.size() > 0)
            return mListItems.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {

        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ai_item, null);
            viewHolder.userName = (TextView) convertView
                    .findViewById(R.id.user_name);
            viewHolder.userLogo = (ImageView) convertView
                    .findViewById(R.id.user_logo);
//            viewHolder.favMark = (ImageView) convertView
//                    .findViewById(R.id.item_action_fav);
            viewHolder.contentText = (TextView) convertView
                    .findViewById(R.id.content_text);
            viewHolder.contentImage = (ImageView) convertView
                    .findViewById(R.id.content_image);
//            viewHolder.love = (TextView) convertView
//                    .findViewById(R.id.item_action_love);
//            viewHolder.hate = (TextView) convertView
//                    .findViewById(R.id.item_action_hate);
//            viewHolder.share = (TextView) convertView
//                    .findViewById(R.id.item_action_share);
//            viewHolder.comment = (TextView) convertView
//                    .findViewById(R.id.item_action_comment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AllContent entity = mListItems.get(position);
        /**
         * 呈现数据
         * */
//        if (null != user.getAvatar())
        if(null!=entity.getAuthor().getAvatar())
            ImageLoader.getInstance().displayImage(entity.getAuthor().getAvatar().getFileUrl(context), viewHolder.userLogo);
        viewHolder.userName.setText(entity.getAuthor().getUsername());
        viewHolder.contentText.setText(entity.getContent());
//        viewHolder.love.setText(entity.getLove() + "");
        //        获取用户
//        final User user = BmobUser.getCurrentUser(context, User.class);
        //根据是否被登录用户点赞过设置颜色
//        if(null==user){
//            entity.setMyLove(false);
//        }
//        else if (null == entity.getBeLoved())
//            entity.setMyLove(false);
//        else if (isLoved(entity, user)) {
//            viewHolder.love.setTextColor(Color.parseColor("#D95555"));
//            Log.i("", "变色");
//            entity.setMyLove(true);
//        } else {
//            entity.setMyLove(false);
//            Log.i("", "默认颜色");
//        }

//        分已经收藏和未收藏2种情况设置图片
//        if(null==user){
//            viewHolder.favMark
//                    .setImageResource(R.drawable.ic_action_fav_normal);
//            entity.setMyFav(false);
//        }
//        else if (null == user.getFavorite())
//            viewHolder.favMark
//                    .setImageResource(R.drawable.ic_action_fav_normal);
//        else if (isCollected(user, entity)) {
//            viewHolder.favMark
//                    .setImageResource(R.drawable.ic_action_fav_choose);
//            entity.setMyFav(true);
//        } else {
//            viewHolder.favMark
//                    .setImageResource(R.drawable.ic_action_fav_normal);
//            entity.setMyFav(false);
//        }
//       内容图片
        if (null == entity.getContentfigureurl()) {
            //            把图片所占用的位置移除，布局会好看点
            viewHolder.contentImage.setVisibility(View.GONE);
        } else {
            /**
             * entity.getContentfigureurl().getFileUrl(context)是图片的url
             * 通过ImageLoader来加载，这是官方提供的工具，专门加载网络图片使用
             * 需要在application里面初始化ImageLoader
             * 同时别忘记在manifest里面application加上name属性
             * http://blog.sina.com.cn/s/blog_9ac333de0101gptn.html
             * */
            Log.i("图片url", "图片" + entity.getContentfigureurl().getFileUrl(context));
            viewHolder.contentImage.setVisibility(View.VISIBLE);
            //第一个参数是url,第二个参数是需要显示出来的ImageView

            ImageLoader.getInstance().displayImage(entity.getContentfigureurl().getFileUrl(context), viewHolder.contentImage);
        }
        /**点击事件*/
        viewHolder.userLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                if (MyApplication.getInstance().getCurrentUser() == null) {
//                    toast.ShowText("收藏前请先登录", context);
//                    ActivityUtil.show(context, "请先登录");
//                    Intent intent = new Intent();
//                    intent.setClass(context, RegisterAndLoginActivity.class);
//                    MyApplication.getInstance().getTopActivity()
//                            .startActivity(intent);
//                    return;
//                }
//                MyApplication.getInstance().setCurrentQiangYu(entity);
                // User currentUser =
                // BmobUser.getCurrentUser(mContext,User.class);
                // if(currentUser != null){//已登录
                Intent intent = new Intent();
//                intent.setClass(MyApplication.getInstance().getTopActivity(),
                intent.setClass(context,
                        PersonalActivity.class);
                context.startActivity(intent);
            }
        });
//        点击收藏
//        viewHolder.favMark.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                 TODO Auto-generated method stub
//                onClickFav(v, entity, user);
//            }
//        });
//        点赞
//        viewHolder.love.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickLove(v, entity, user, viewHolder);
//            }
//        });
        //        分享
//        viewHolder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toast.ShowText("分享！", context);
//            }
//        });
//        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(context,
//                        CommentActivity.class);
//                entity数据传递到CommentActivity过去
//                intent.putExtra("data", entity);
//                context.startActivity(intent);
//            }
//        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,
                        CommentActivity.class);
//                entity数据传递到CommentActivity过去
                intent.putExtra("data", entity);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    //处理收藏点击事件
//    private void onClickFav(final View v, final AllContent entity, User user) {
//
////        user.getSessionToken()是什么鬼？
//        if (user != null && user.getSessionToken() != null) {
////            BmobRelation favRelaton = new BmobRelation();
//            BmobRelation favRelaton;
//            if (null != user.getFavorite())
//                favRelaton = user.getFavorite();
//            else
//                favRelaton = new BmobRelation();
//            //判断登录用户收藏的动态list是否包含这条动态
//            Log.i("", "收藏" + entity.toString());
//
//            if (!entity.getMyFav()) {
//                favRelaton.add(entity);
//                user.setFavorite(favRelaton);
//                user.update(context, new UpdateListener() {
//
//                    @Override
//                    public void onSuccess() {
//                        ((ImageView) v)
//                                .setImageResource(R.drawable.ic_action_fav_choose);
//                        entity.setMyFav(true);
//                        toast.ShowText("收藏成功", context);
//
//                    }
//
//                    @Override
//                    public void onFailure(int arg0, String arg1) {
////                        ActivityUtil.show(context, "收藏失败。请检查网络~" + arg0);
//                        toast.ShowText("收藏失败。请检查网络~" + arg0, context);
//                    }
//                });
//
//            } else {
//
//                favRelaton.remove(entity);
//                user.setFavorite(favRelaton);
////                ActivityUtil.show(context, "取消收藏。");
//                user.update(context, new UpdateListener() {
//
//                    @Override
//                    public void onSuccess() {
//                        ((ImageView) v)
//                                .setImageResource(R.drawable.ic_action_fav_normal);
//                        // TODO Auto-generated method stub
////                        DatabaseUtil.getInstance(mContext).deleteFav(entity);
////                        LogUtils.i(TAG, "取消收藏。");
//                        toast.ShowText("取消收藏成功", context);
//                        // try get fav to see if fav success
//                        // getMyFavourite();
//                        entity.setMyFav(false);
//                    }
//
//                    @Override
//                    public void onFailure(int arg0, String arg1) {
//                        // TODO Auto-generated method stub
////                        LogUtils.i(TAG, "取消收藏失败。请检查网络~");
//                        toast.ShowText("取消收藏失败。请检查网络~" + arg0, context);
////                        ActivityUtil.show(context, "取消收藏失败。请检查网络~" + arg0);
//                    }
//                });
//            }
//        } else {
//            // 前往登录注册界面
////            ActivityUtil.show(context, "收藏前请先登录。");
//            toast.ShowText("收藏前请先登录", context);
////            Intent intent = new Intent();
////            intent.setClass(context, RegisterAndLoginActivity.class);
////            MyApplication.getInstance().getTopActivity()
////                    .startActivityForResult(intent, SAVE_FAVOURITE);
//        }
//    }

    //处理点赞点击事件
//    private void onClickLove(final View v, final AllContent entity, final User user, ViewHolder viewHolder) {
//        if (user != null && user.getSessionToken() != null) {
//            if (entity.getMyLove())
//                toast.ShowText("你已经赞过啦！", context);
//            else {
//                点赞加1
//                entity.setLove(entity.getLove() + 1);
//                viewHolder.love.setTextColor(Color.parseColor("#D95555"));
//                viewHolder.love.setText(entity.getLove() + "");
//                在AllContent表记录被谁点赞
//                BmobRelation beLoved = new BmobRelation();
//                BmobRelation beLoved;
//                if (null != entity.getBeLoved())
//                    beLoved = entity.getBeLoved();
//                else
//                    beLoved = new BmobRelation();
//                beLoved.add(user);
//                entity.setBeLoved(beLoved);
//          给指定的字段自动增加指定的数量，原子计数器
//                entity.increment("love", 1);
//                entity.update(context, new UpdateListener() {

//                    @Override
//                    public void onSuccess() {
//                        Log.i("给这条动态点赞的人数是 ", "" + entity.getBeLoved().getObjects().size());
//                        Log.i("love成功", isLoved(entity, user) + "");
                        // TODO Auto-generated method stub
//                        entity.setMyLove(true);
//                        entity.setMyFav(oldFav);
//                        DatabaseUtil.getInstance(mContext).insertFav(entity);
                        // DatabaseUtil.getInstance(mContext).queryFav();
//                        LogUtils.i(TAG, "点赞成功~");
//                        toast.ShowText("点赞成功", context);
//                    }

//                    @Override
//                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
//                        entity.setMyLove(true);
//                        entity.setMyFav(oldFav);
//                        toast.ShowText("点赞失败-" + arg1, context);
//                    }
//                });
                //在user表记录点赞了该动态
//                BmobRelation love;
//                if(null==user.getLove())
//                     love = new BmobRelation();
//                else
//                     love = user.getLove();
//                love.add(entity);
//                user.setLove(love);
//                user.update(context);
//            }
//        } else {
//             前往登录注册界面
//            ActivityUtil.show(context, "收藏前请先登录。");
//            toast.ShowText("收藏前请先登录", context);
//            Intent intent = new Intent();
//            intent.setClass(context, RegisterAndLoginActivity.class);
//            MyApplication.getInstance().getTopActivity()
//                    .startActivityForResult(intent, SAVE_FAVOURITE);
//        }
//    }

    //判断用户是否已经点赞过这条动态啦
//    private boolean isLoved(AllContent entity, User user) {
////        boolean isLoved = false;
//
//        if (null != user.getLove()) {
//            if (user.getLove().getObjects().size() == 0) {
//                Log.e("动态没有被登录用户点赞过", "length=0");
//                return false;
//            }
//            for (int i = 0; i < user.getLove().getObjects().size(); i++) {
//                Log.i("登录用户点赞的动态的 id列举 ", "" + user.getLove().getObjects().get(i).getObjectId());
//                if (user.getLove().getObjects().get(i).getObjectId().equals(entity.getObjectId())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    //判断是否登录的用户已经收藏该条动态
//    private boolean isCollected(User user, AllContent entity) {
//        if (user.getFavorite().getObjects().size() == 0) {
//            Log.e("isCollected", "length=0");
//            return false;
//        }
//        Log.i("登录用户为 ", user.getUsername());
//        Log.i("登录用户收藏的动态个数为   ", "" + user.getFavorite().getObjects().size());
//        for (int i = 0; i < user.getFavorite().getObjects().size(); i++) {
//            Log.i("登录用户收藏的动态的 id列举", "" + user.getFavorite().getObjects().get(i).getObjectId());
//            if (user.getFavorite().getObjects().get(i).getObjectId().equals(entity.getObjectId())) {
//                return true;
//            }
//        }
//        return false;
//    }


    public static class ViewHolder {
        public ImageView userLogo;
        public TextView userName;
        public TextView contentText;
        public ImageView contentImage;

//        public ImageView favMark;
//        public TextView love;
//        public TextView hate;
//        public TextView share;
//        public TextView comment;
    }

}

