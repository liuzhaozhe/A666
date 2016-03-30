package com.life.a666;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.life.application.MyApplication;
import com.life.util.toast;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MessageFragment extends Fragment {

    /**
     * 页面集合
     */
    List<Fragment> fragmentList;
    private String TAG = "MessageFragment";
    Button btnChat;
    //用于消息列表和联系人列表之间切换
    private ViewPager mViewPager;
    //viewPager的所有view
    private ArrayList<View> mViewPagerContent = new ArrayList<View>(2);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //第一个参数是填充的布局
        View view = inflater.inflate(R.layout.tab03, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        useListView();
    }

    public void initView(View view) {
        btnChat = (Button) view.findViewById(R.id.btnChat);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_container);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                {"code":200,"userId":"67890","token":"UZtUu+2bnIbg6bY0TTpfyjm82Pi6yIC0RkdBQY4WWnI/qhlR3idk/Ohvtmv+C3rwx1Yyr4TkVaUXY7MiLrLt9g=="}

//                String Token = "UZtUu+2bnIbg6bY0TTpfyjm82Pi6yIC0RkdBQY4WWnI/qhlR3idk/Ohvtmv+C3rwx1Yyr4TkVaUXY7MiLrLt9g==";//test
                String userName = "";
//                if (null != MyApplication.getInstance().getCurrentUser())
//                    userName = MyApplication.getInstance().getCurrentUser().getUsername();
//                else
//                    toast.ShowText("请先登录",getContext());

                String Token = null;
                try {
                    //为了方便调试IM功能，暂时写死Token，免得获取token很麻烦
//                    SharedPreferences SPgetData = MyApplication.getInstance().getSharedPreferences(userName, Activity.MODE_PRIVATE);
//                      第二步，获取数据，这里和存储数据不一样，不需要editor对象，只需要SharedPreferences对象即可获取值啦！传入key,获取数据，第二个参数是默认返回值
//                    Token=SPgetData.getString("Token","");
                    Token = "UZtUu+2bnIbg6bY0TTpfyjm82Pi6yIC0RkdBQY4WWnI/qhlR3idk/Ohvtmv+C3rwx1Yyr4TkVaUXY7MiLrLt9g==";

                    Log.i(TAG, Token);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    e.printStackTrace();
                }
                if (Token != null & Token != "") {
                    Log.i(TAG, Token);
                    /**
                     * IMKit SDK调用第二步
                     *
                     * 建立与服务器的连接
                     *
                     */
                    RongIM.connect(Token, new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {
                            //Connect Token 失效的状态处理，需要重新获取 Token
                        }

                        @Override
                        public void onSuccess(String userId) {
                            /**
                             * 启动单聊
                             * context - 应用上下文。
                             * targetUserId - 要与之聊天的用户 Id。
                             * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                             */
//                        {"code":200, "userId":"12345",
//                                "token":
//                            "qLJJvjCzDWVRJkx65LHyUx7iozBA2NK8M4ntPTJeFk5RrKHlNBYsIWpzADK+c3XzkewYOPpO3Sv36c1n0fnD5Q=="}

                            if (RongIM.getInstance() != null) {
                                RongIM.getInstance().startPrivateChat(getActivity(), "12345", "聊天么");
                            }
//                        Log.e(“MainActivity”, “——onSuccess— -”+userId);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.e("MessageFragment", errorCode.toString());
                        }
                    });
                } else
                    Log.e(TAG, "无法获取Token");
            }
//                startActivity(new Intent(getActivity(), ConversationActivity.class));
//            }
        });

        showMessageList();

//        tvGuide = (TextView) view.findViewById(R.id.tvGuide);
//        tvGuide.setText(Environment.getExternalStorageDirectory().getPath() + "\n"
//                + "data--" + Environment.getDataDirectory().getAbsolutePath());
        /**
         * 使用系统文件管理器来显示该文件位置
         * */
//        File file = new File(Environment.getDataDirectory().getAbsolutePath());
//        // String path=file.getParent();
//        File parentFlie = new File(file.getParent());
//        Intent intent = new Intent(
//                Intent.ACTION_GET_CONTENT);
//        intent.setDataAndType(Uri.fromFile(parentFlie),
//                "*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivity(intent);
//        Environment.getDataDirectory().getAbsolutePath()
//        tvGuide.setText("香甜可口的草莓蛋糕");
        Log.i("", Environment.getExternalStorageDirectory().getPath());
    }

    //使用rongyun 来显示消息列表
    public void showMessageList() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .build();
        fragment.setUri(uri);

        initViewPager(fragment);

    }

    private void initViewPager(ConversationListFragment fragment) {
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(fragment);
        fragments.add(new FindFragment());
        FragAdapter adapter = new FragAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
    }

    /**
     * FragmentPager适配器
     *
     * @author wwj
     */
    public class FragAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
