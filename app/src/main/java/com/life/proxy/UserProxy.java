package com.life.proxy;

/**
 * Created by 惟我独尊 on 2015/12/13.
 */

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.life.entity.User;
import com.life.server.HttpPostInteract;
import com.life.util.Constant;

import java.io.IOException;

public class UserProxy {

    public static final String TAG = "UserProxy";

    private Context mContext;

    public UserProxy(Context context) {
        this.mContext = context;
    }

    //    登录
    public void login(String userName, String password) {
        final BmobUser user = new BmobUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.login(mContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                if (loginListener != null) {
                    loginListener.onLoginSuccess();
                } else {
                    Log.i(TAG, "login listener is null,you must set one!");
                }
            }

            @Override
            public void onFailure(int arg0, String msg) {
                // TODO Auto-generated method stub
                if (loginListener != null) {
                    loginListener.onLoginFailure(msg);
                } else {
                    Log.i(TAG, "login listener is null,you must set one!");
                }
            }
        });
    }

    public interface ILoginListener {
        void onLoginSuccess();

        void onLoginFailure(String msg);
    }

    private ILoginListener loginListener;

    public void setOnLoginListener(ILoginListener loginListener) {
        this.loginListener = loginListener;
    }

    //注册
    public void signUp(String userName, String password, String email) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setSex(Constant.SEX_FEMALE);
        user.setSignature("这个家伙很懒，什么也不说。。。");
        user.signUp(mContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                if (signUpLister != null) {
                    signUpLister.onSignUpSuccess();
                } else {
                    Log.i(TAG, "signup listener is null,you must set one!");
                }
            }

            @Override
            public void onFailure(int arg0, String msg) {
                // TODO Auto-generated method stub
                if (signUpLister != null) {
                    signUpLister.onSignUpFailure(msg);
                } else {
                    Log.i(TAG, "signup listener is null,you must set one!");
                }
            }
        });
    }

    //	注册接口
    public interface ISignUpListener {
        void onSignUpSuccess();

        void onSignUpFailure(String msg);
    }

    private ISignUpListener signUpLister;

    public void setOnSignUpListener(ISignUpListener signUpLister) {
        this.signUpLister = signUpLister;
    }

    //    改密码
    public void resetPassword(String email) {
        BmobUser.resetPasswordByEmail(mContext, email, new ResetPasswordByEmailListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                if (resetPasswordListener != null) {
                    resetPasswordListener.onResetSuccess();
                } else {
                    Log.i(TAG, "reset listener is null,you must set one!");
                }
            }

            @Override
            public void onFailure(int arg0, String msg) {
                // TODO Auto-generated method stub
                if (resetPasswordListener != null) {
                    resetPasswordListener.onResetFailure(msg);
                } else {
                    Log.i(TAG, "reset listener is null,you must set one!");
                }
            }
        });
    }

    public interface IResetPasswordListener {
        void onResetSuccess();

        void onResetFailure(String msg);
    }

    private IResetPasswordListener resetPasswordListener;

    public void setOnResetPasswordListener(IResetPasswordListener resetPasswordListener) {
        this.resetPasswordListener = resetPasswordListener;
    }

}
