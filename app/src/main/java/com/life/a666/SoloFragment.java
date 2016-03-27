package com.life.a666;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.life.Adapter.SoloAdapter;
import com.life.entity.AllContent;
import com.life.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoloFragment extends Fragment {
//    ImageButton btnPublish;
    String TAG="SoloFragment";
    ListView lvMoments;
    protected ArrayList<AllContent> mListItems = new ArrayList<AllContent>();
    SoloAdapter momentAdapter;
    Context mContext;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Switch(msg.what)
//                    case :
            if (null == momentAdapter) {
                momentAdapter = new SoloAdapter(mListItems, mContext);
                lvMoments.setAdapter(momentAdapter);
            }
            Log.i("handleMessage", "onSuccess获取动态成功" + mListItems.size());
            if(mListItems.size()>0)
                for(int i=0;i<mListItems.size();i++)
                    //如果用户还没有登录，就直接发动态，那么这条动态的用户不存在，在这里就会有空指针异常
//                所以以后发动态要先判断用户是否已经登录过了
                    Log.i("mListItems", "内容" + mListItems.get(i).getContent() + "user " + mListItems.get(i).getAuthor().getUsername() + "id" +
                            mListItems.get(i).getAuthor().getObjectId());
//            ShowText("设置momentAdapter：  " + mListItems.size());
//        momentAdapter.notifyDataSetChanged();

            momentAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab02, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initEvent();
    }

    protected void initView(View view) {
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(getActivity(), EditActivity.class));
            }
        });
        lvMoments = (ListView) view.findViewById(R.id.lvMoments);
//        btnPublish = (ImageButton) view.findViewById(R.id.btnPublish);

    }

    protected void initEvent() {

//        btnPublish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(MainActivity.this, "onOptionsItemSelected add_item1", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(), EditActivity.class));
//            }
//        });
        mContext = getActivity();
        //        初始化bmob
        Bmob.initialize(mContext, Constant.BMOB_APP_ID);
        fetchData();
    }

    public void fetchData() {
//        setState(LOADING);
//        User user = BmobUser.getCurrentUser(mContext, User.class);

        BmobQuery<AllContent> query = new BmobQuery<AllContent>();
//        query.addWhereRelatedTo("favorite", new BmobPointer(user));
        query.order("-createdAt");
//        query.setLimit(Constant.NUMBERS_PER_PAGE);
//        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
//        query.addWhereLessThan("createdAt", date);
//        query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
//
//        http://wenda.bmob.cn/?/question/1364
        /**
         * 在查询数据的时候使用include("author")这个方法，
         * 这样才会将用户数据也一并返回的，比如获取该动态发布者名称的时候如果没有
         * query.include("author");就只能获取到null值
         * */
        query.include("author");
//        query.findObjects(getActivity(), new FindListener<AllContent>() {
        query.findObjects(mContext, new FindListener<AllContent>() {

            @Override
            public void onSuccess(List<AllContent> list) {
                if (list.size() != 0 && list.get(list.size() - 1) != null) {
//                    if(mRefreshType==RefreshType.REFRESH){
//                        mListItems.clear();
//                    }
                    if (list.size() < Constant.NUMBERS_PER_PAGE) {
//                        ActivityUtil.show(getActivity(), "已加载完所有数据~");
                    }
                    ShowText("onSuccess获取动态成功：  " + list.size());
                    Log.i(TAG, "onSuccess获取动态成功" + list.size());
                    mListItems.addAll(list);
                    handler.sendEmptyMessage(1);

//                    LogUtils.i(TAG,"DD"+(mListItems.get(mListItems.size()-1)==null));
//                    setState(LOADING_COMPLETED);
//                    mPullRefreshListView.onRefreshComplete();
                } else {
//                    ActivityUtil.show(getActivity(), "暂无更多数据~");
                    if (list.size() == 0 && mListItems.size() == 0) {

//                        networkTips.setText("暂无收藏。快去首页收藏几个把~");
//                        setState(LOADING_FAILED);
//                        pageNum--;
//                        mPullRefreshListView.onRefreshComplete();
//
//                        LogUtils.i(TAG,"SIZE:"+list.size()+"ssssize"+mListItems.size());
                        return;
                    }
//                    pageNum--;
//                    setState(LOADING_COMPLETED);
//                    mPullRefreshListView.onRefreshComplete();
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
//                LogUtils.i(TAG,"find failed."+arg1);
//                pageNum--;
//                setState(LOADING_FAILED);
//                mPullRefreshListView.onRefreshComplete();
//                toast.ShowText("获取动态失败",mContext);
                ShowText("获取动态失败");
//                Toast.makeText(mContext, "获取动态失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowText(String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();

    }
}

