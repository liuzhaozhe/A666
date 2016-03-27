package com.life.a666;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.life.server.Token;


import com.life.application.MyApplication;
import com.life.util.toast;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MessageFragment extends Fragment {

    private String TAG = "MessageFragment";
    Button btnChat;
    Button btnConversationList;

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
        btnConversationList = (Button) view.findViewById(R.id.btnConversationList);
        btnConversationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//启动会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                {"code":200,"userId":"67890","token":"UZtUu+2bnIbg6bY0TTpfyjm82Pi6yIC0RkdBQY4WWnI/qhlR3idk/Ohvtmv+C3rwx1Yyr4TkVaUXY7MiLrLt9g=="}

//                String Token = "UZtUu+2bnIbg6bY0TTpfyjm82Pi6yIC0RkdBQY4WWnI/qhlR3idk/Ohvtmv+C3rwx1Yyr4TkVaUXY7MiLrLt9g==";//test
                String userName = "";
                if (null != MyApplication.getInstance().getCurrentUser())
                    userName = MyApplication.getInstance().getCurrentUser().getUsername();
                else
                    toast.ShowText("请先登录",getContext());

                    String Token = null;
                try {
                    Token = com.life.server.Token.ServerGetToken(userName, userName);
                    Log.i(TAG, Token);
                } catch (Exception e) {
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
//                        Log.e(“MainActivity”, “——onError— -”+errorCode);
                        }
                    });
                } else
                    Log.e(TAG, "无法获取Token");
            }
//                startActivity(new Intent(getActivity(), ConversationActivity.class));
//            }
        });
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

    /**
     * 使用useListView
     */
    private void useListView() {
    }
}
