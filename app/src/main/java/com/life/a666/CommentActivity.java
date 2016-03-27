package com.life.a666;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.life.Adapter.CommentAdapter;
import com.life.a666.base.BaseActivity;
import com.life.entity.AllContent;
import com.life.entity.Comment;
import com.life.entity.User;
import com.life.util.ActivityUtil;
import com.life.util.Constant;
import com.life.util.toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.InitListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentActivity extends BaseActivity implements View.OnClickListener {
//    private ActionBar actionbar;
//    private ListView commentList;
//    private TextView footer;

    private EditText commentContent;
    private Button commentCommit;
    private TextView userName;
    private TextView commentItemContent;
    //    private TextView comment;
    private TextView share;
    private TextView love;
    private TextView footer;

    private ImageView commentItemImage;
    private ImageView userLogo;
    private ImageView favMark;

    private ListView lvComment;
    private CommentAdapter mAdapter;
    private List<Comment> ListComments = new ArrayList<Comment>();
    private AllContent entity;
    private Context context;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null == mAdapter) {
                mAdapter = new CommentAdapter(context, ListComments);
                lvComment.setAdapter(mAdapter);
            }
            Log.i("handleMessage", "onSuccess获取动态成功" + ListComments.size());
            if (ListComments.size() > 0)
                for (int i = 0; i < ListComments.size(); i++)
                    Log.i("mListItems", "内容" + ListComments.get(i).getCommentContent());
            mAdapter.notifyDataSetChanged();
        }
    };
//    private TextView hate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_comment);
    }

    @Override
    protected void initView() {
        lvComment = (ListView) findViewById(R.id.comment_list);
        footer = (TextView) findViewById(R.id.loadmore);
        commentContent = (EditText) findViewById(R.id.comment_content);
        commentCommit = (Button) findViewById(R.id.comment_commit);

        userName = (TextView) findViewById(R.id.user_name);
        commentItemContent = (TextView) findViewById(R.id.content_text);
//        comment = (TextView) findViewById(R.id.item_action_comment);
//        share = (TextView) findViewById(R.id.item_action_share);
//        love = (TextView) findViewById(R.id.item_action_love);

        commentItemImage = (ImageView) findViewById(R.id.content_image);
        userLogo = (ImageView) findViewById(R.id.user_logo);
//        favMark = (ImageView) findViewById(R.id.item_action_fav);

    }

    @Override
    protected void initEvent() {
        showAllContent();
        fetchComment();
        initListener();
    }

    private void initListener() {
        commentCommit.setOnClickListener(this);
        userLogo.setOnClickListener(this);
        lvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                ActivityUtil.show(CommentActivity.this, "您点击的评论是第 " + position + " 楼");
            }
        });
    }

    private void fetchComment() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereRelatedTo("relationComment", new BmobPointer(entity));
        query.include("user");
        query.order("createdAt");
        query.setLimit(Constant.NUMBERS_PER_PAGE);
//            query.setSkip(Constant.NUMBERS_PER_PAGE * (pageNum++));
        query.findObjects(this, new FindListener<Comment>() {

            @Override
            public void onSuccess(List<Comment> data) {
                // TODO Auto-generated method stub
//                    LogUtils.i(TAG, "get comment success!" + data.size());
                if (data.size() != 0 && data.get(data.size() - 1) != null) {
                    toast.ShowText("成功加载评论数量--" + data.size(), context);
                    footer.setVisibility(View.GONE);
                    ListComments.addAll(data);
                    handler.sendEmptyMessage(1);
//                        if (data.size() < Constant.NUMBERS_PER_PAGE) {
//                            ActivityUtil.show(mContext, "已加载完所有评论~");
//                            footer.setText("暂无更多评论~");
//                        }

//                        mAdapter.getDataList().addAll(data);
                    mAdapter.notifyDataSetChanged();
//                        setListViewHeightBasedOnChildren(commentList);
//                        LogUtils.i(TAG, "refresh");
                } else {
                    toast.ShowText("唉，呜呜，还没有评论--", context);
                    footer.setText("还没有评论");
//                        ActivityUtil.show(mContext, "暂无更多评论~");
//                        footer.setText("暂无更多评论~");
//                        pageNum--;
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
//                    ActivityUtil.show(CommentActivity.this, "获取评论失败。请检查网络~");
//                    pageNum--;
                Log.e("error", "获取评论失败" + arg1);
            }
        });

//        ListComments
    }

    //  展示该动态的数据以及处理点击事件
    private void showAllContent() {
        context = CommentActivity.this;
        final User user = BmobUser.getCurrentUser(context, User.class);
//        获取对应的entity数据
        entity = (AllContent) getIntent().getExtras().get("data");
//        显示动态数据
        userName.setText(entity.getAuthor().getUsername());
        commentItemContent.setText(entity.getContent());

        //        分已经收藏和未收藏2种情况设置图片
//        if(null==user){
//            favMark
//                    .setImageResource(R.drawable.ic_action_fav_normal);
//            entity.setMyFav(false);
//        }
//        else  if (null == user.getFavorite())
//            favMark
//                    .setImageResource(R.drawable.ic_action_fav_normal);
//        else if (isCollected(user, entity)) {
//            favMark
//                    .setImageResource(R.drawable.ic_action_fav_choose);
//            entity.setMyFav(true);
//        } else {
//            favMark
//                    .setImageResource(R.drawable.ic_action_fav_normal);
//            entity.setMyFav(false);
//        }

        //根据是否被登录用户点赞过设置颜色
//        love.setText(entity.getLove() + "");
//        if(null==user){
//            entity.setMyLove(false);
//        }
//        else if (null == entity.getBeLoved())
//            entity.setMyLove(false);
//        else if (isLoved(entity, user)) {
//            love.setTextColor(Color.parseColor("#D95555"));
//            Log.i("", "变色");
//            entity.setMyLove(true);
//        } else {
//            entity.setMyLove(false);
//            Log.i("", "默认颜色");
//        }
        if (null == entity.getContentfigureurl()) {
            commentItemImage.setVisibility(View.GONE);
        } else {
            Log.i("图片url", "图片" + entity.getContentfigureurl().getFileUrl(context));
            commentItemImage.setVisibility(View.VISIBLE);
            //第一个参数是url,第二个参数是需要显示出来的ImageView
            ImageLoader.getInstance().displayImage(entity.getContentfigureurl().getFileUrl(context), commentItemImage);
        }
        //        点击收藏
//        favMark.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                onClickFav(v, entity, user);
//            }
//        });
        //        点赞
//        love.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickLove(v, entity, user);
//            }
//        });
//        //        分享
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toast.ShowText("分享！", CommentActivity.this);
//            }
//        });
    }

    //处理收藏点击事件
    private void onClickFav(final View v, final AllContent entity, User user) {

//        user.getSessionToken()是什么鬼？
        if (user != null && user.getSessionToken() != null) {
//            BmobRelation favRelaton = new BmobRelation();
            BmobRelation favRelaton;
            if (null != user.getFavorite())
                favRelaton = user.getFavorite();
            else
                favRelaton = new BmobRelation();
            //判断登录用户收藏的动态list是否包含这条动态
            Log.i("", "收藏" + entity.toString());

            if (!entity.getMyFav()) {
                favRelaton.add(entity);
                user.setFavorite(favRelaton);
                user.update(context, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        ((ImageView) v)
                                .setImageResource(R.drawable.ic_action_fav_choose);
                        entity.setMyFav(true);
                        toast.ShowText("收藏成功", context);

                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
//                        ActivityUtil.show(context, "收藏失败。请检查网络~" + arg0);
                        toast.ShowText("收藏失败。请检查网络~" + arg0, context);
                    }
                });

            } else {

                favRelaton.remove(entity);
                user.setFavorite(favRelaton);
//                ActivityUtil.show(context, "取消收藏。");
                user.update(context, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        ((ImageView) v)
                                .setImageResource(R.drawable.ic_action_fav_normal);
                        // TODO Auto-generated method stub
//                        DatabaseUtil.getInstance(mContext).deleteFav(entity);
//                        LogUtils.i(TAG, "取消收藏。");
                        toast.ShowText("取消收藏成功", context);
                        // try get fav to see if fav success
                        // getMyFavourite();
                        entity.setMyFav(false);
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
//                        LogUtils.i(TAG, "取消收藏失败。请检查网络~");
                        toast.ShowText("取消收藏失败。请检查网络~" + arg0, context);
//                        ActivityUtil.show(context, "取消收藏失败。请检查网络~" + arg0);
                    }
                });
            }
        } else {
            // 前往登录注册界面
//            ActivityUtil.show(context, "收藏前请先登录。");
            toast.ShowText("收藏前请先登录", context);
//            Intent intent = new Intent();
//            intent.setClass(context, RegisterAndLoginActivity.class);
//            MyApplication.getInstance().getTopActivity()
//                    .startActivityForResult(intent, SAVE_FAVOURITE);
        }
    }

    //处理点赞点击事件
    private void onClickLove(final View v, final AllContent entity, final User user) {
        if (user != null && user.getSessionToken() != null) {
            if (entity.getMyLove())
                toast.ShowText("你已经赞过啦！", context);
            else {
                //点赞加1
                entity.setLove(entity.getLove() + 1);
                love.setTextColor(Color.parseColor("#D95555"));
                love.setText(entity.getLove() + "");
                ////在AllContent表记录被谁点赞
//                BmobRelation beLoved = new BmobRelation();
                BmobRelation beLoved;
                if (null != entity.getBeLoved())
                    beLoved = entity.getBeLoved();
                else
                    beLoved = new BmobRelation();
                beLoved.add(user);
                entity.setBeLoved(beLoved);
//          给指定的字段自动增加指定的数量，原子计数器
                entity.increment("love", 1);
                entity.update(context, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        Log.i("给这条动态点赞的人数是 ", "" + entity.getBeLoved().getObjects().size());
//                        Log.i("love成功", isLoved(entity, user) + "");
                        // TODO Auto-generated method stub
                        entity.setMyLove(true);
//                        entity.setMyFav(oldFav);
//                        DatabaseUtil.getInstance(mContext).insertFav(entity);
                        // DatabaseUtil.getInstance(mContext).queryFav();
//                        LogUtils.i(TAG, "点赞成功~");
                        toast.ShowText("点赞成功", context);
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
//                        entity.setMyLove(true);
//                        entity.setMyFav(oldFav);
                        toast.ShowText("点赞失败-" + arg1, context);
                    }
                });
                //在user表记录点赞了该动态
//                BmobRelation love = new BmobRelation();
                BmobRelation love = user.getLove();
                love.add(entity);
                user.setLove(love);
                user.update(context);
            }
        } else {
            // 前往登录注册界面
//            ActivityUtil.show(context, "收藏前请先登录。");
            toast.ShowText("收藏前请先登录", context);
//            Intent intent = new Intent();
//            intent.setClass(context, RegisterAndLoginActivity.class);
//            MyApplication.getInstance().getTopActivity()
//                    .startActivityForResult(intent, SAVE_FAVOURITE);
        }
    }

    //判断是否登录的用户已经收藏该条动态
    private boolean isCollected(User user, AllContent entity) {
        if (user.getFavorite().getObjects().size() == 0) {
            Log.e("isCollected", "length=0");
            return false;
        }
        Log.i("登录用户为 ", user.getUsername());
        Log.i("登录用户收藏的动态个数为   ", "" + user.getFavorite().getObjects().size());
        for (int i = 0; i < user.getFavorite().getObjects().size(); i++) {
            Log.i("登录用户收藏的动态的 id列举", "" + user.getFavorite().getObjects().get(i).getObjectId());
            if (user.getFavorite().getObjects().get(i).getObjectId().equals(entity.getObjectId())) {
                return true;
            }
        }
        return false;
    }

    //判断用户是否已经点赞过这条动态啦
    private boolean isLoved(AllContent entity, User user) {
//        boolean isLoved = false;

        if (null != user.getLove()) {
            if (user.getLove().getObjects().size() == 0) {
                Log.e("动态没有被登录用户点赞过", "length=0");
                return false;
            }
            for (int i = 0; i < user.getLove().getObjects().size(); i++) {
                Log.i("登录用户点赞的动态的 id列举 ", "" + user.getLove().getObjects().get(i).getObjectId());
                if (user.getLove().getObjects().get(i).getObjectId().equals(entity.getObjectId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //提交评论
            case R.id.comment_commit:
                if (BmobUser.getCurrentUser(context, User.class) != null) {
                    String commentEdit = commentContent.getText().toString().trim();
                    if (TextUtils.isEmpty(commentEdit)) {
                        toast.ShowText("评论内容不能为空", context);
                        return;
                    }
                    final Comment comment = new Comment();
                    comment.setCommentContent(commentEdit);
                    comment.setUser(BmobUser.getCurrentUser(context, User.class));
                    comment.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast.ShowText("评论成功", context);
                            footer.setVisibility(View.GONE);
                            if (null == mAdapter) {
                                mAdapter = new CommentAdapter(context, ListComments);
                                lvComment.setAdapter(mAdapter);
                            }
                            ListComments.add(comment);
//                        toast.ShowText("添加一楼" +ListComments.size(), context);
                            mAdapter.notifyDataSetChanged();
                            // 将该评论添加到这条动态
                            BmobRelation relationComment = new BmobRelation();
                            relationComment.add(comment);
                            Log.i("entity", entity.getContent());
                            entity.setRelationComment(relationComment);
                            entity.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    toast.ShowText("更新评论成功", context);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    toast.ShowText("更新评论失败", context);
                                }
                            });
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast.ShowText("评论失败" + s, context);
                        }
                    });
                    commentContent.setText("");
                } else
                    toast.ShowText("发表评论前请先登录", context);
                break;
            case R.id.user_logo:
                startActivity(new Intent(CommentActivity.this,PersonalActivity.class));
                break;
//            case R.id.user_logo:
//
//                break;
//            case R.id.user_logo:
//
//                break;
        }
    }
}
