package com.life.a666;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.life.a666.base.BaseActivity;
import com.life.proxy.UserProxy;
import com.life.util.toast;
import com.life.view.DeletableEditText;

public class RegisterAndLoginActivity extends BaseActivity implements UserProxy.ISignUpListener,UserProxy.ILoginListener,UserProxy.IResetPasswordListener,View.OnClickListener {
//用户代理，其中设置了多个接口
    UserProxy userProxy;
    private Toolbar mToolbar;
    TextView loginTitle;
    TextView registerTitle;
    TextView resetPassword;

    DeletableEditText userNameInput;
    DeletableEditText userPasswordInput;
    DeletableEditText userEmailInput;
    Button btnAction;

    Context mContext=RegisterAndLoginActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private enum UserOperation{
        LOGIN,REGISTER,RESET_PASSWORD
    }

    UserOperation operation = UserOperation.LOGIN;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_register_and_login);
    }
    protected void initView() {

        loginTitle = (TextView)findViewById(R.id.login_menu);
        registerTitle = (TextView)findViewById(R.id.register_menu);
        resetPassword = (TextView)findViewById(R.id.reset_password_menu);

        userNameInput = (DeletableEditText)findViewById(R.id.user_name_input);
        userPasswordInput = (DeletableEditText)findViewById(R.id.user_password_input);
        userEmailInput = (DeletableEditText)findViewById(R.id.user_email_input);

        btnAction = (Button)findViewById(R.id.btnAction);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        int system = getResources().getColor(R.color.system);
        mToolbar.setBackgroundColor(system);
        mToolbar.setTitle("登录注册");
    }



    protected void initEvent() {
        loginTitle.setOnClickListener(this);
        registerTitle.setOnClickListener(this);
        resetPassword.setOnClickListener(this);

        btnAction.setOnClickListener(this);

        userProxy = new UserProxy(RegisterAndLoginActivity.this);
//        ActionBarView.initActionbar(this, mToolbar);
    }

    @Override
    public void onSignUpSuccess() {
        toast.ShowText("注册成功" , mContext);
        finish();
    }

    @Override
    public void onSignUpFailure(String msg) {
        toast.ShowText("注册失败"+msg, mContext);
    }
    @Override
    public void onLoginSuccess() {

        toast.ShowText("登录成功",mContext);
        finish();
    }

    @Override
    public void onLoginFailure(String msg) {
        toast.ShowText("登录失败 "+msg,mContext);
    }

    @Override
    public void onResetSuccess() {
        toast.ShowText("重置密码成功" , mContext);
        finish();
    }

    @Override
    public void onResetFailure(String msg) {
        toast.ShowText("重置密码失败 "+msg , mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            注册
            case R.id.btnAction:
                if(operation == UserOperation.LOGIN){
                    if(TextUtils.isEmpty(userNameInput.getText())){
                        userNameInput.setShakeAnimation();
                        Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(userPasswordInput.getText())){
                        userPasswordInput.setShakeAnimation();
                        Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    userProxy.setOnLoginListener(this);
                    Log.i("", "login begin....");
//                    progressbar.setVisibility(View.VISIBLE);
                    userProxy.login(userNameInput.getText().toString().trim(), userPasswordInput.getText().toString().trim());

                }else if(operation == UserOperation.REGISTER){
                    if(TextUtils.isEmpty(userNameInput.getText())){
                        userNameInput.setShakeAnimation();
                        Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(userPasswordInput.getText())){
                        userPasswordInput.setShakeAnimation();
                        Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(userEmailInput.getText())){
                        userEmailInput.setShakeAnimation();
                        Toast.makeText(mContext, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    if(!StringUtils.isValidEmail(userEmailInput.getText())){
//                        userEmailInput.setShakeAnimation();
//                        Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                    userProxy.setOnSignUpListener(this);
//                    LogUtils.i(TAG,"register begin....");
//                    progressbar.setVisibility(View.VISIBLE);
                    userProxy.signUp(userNameInput.getText().toString().trim(),
                            userPasswordInput.getText().toString().trim(),
                            userEmailInput.getText().toString().trim());
                }else{
                    if(TextUtils.isEmpty(userEmailInput.getText())){
                        userEmailInput.setShakeAnimation();
                        Toast.makeText(mContext, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    if(!StringUtils.isValidEmail(userEmailInput.getText())){
//                        userEmailInput.setShakeAnimation();
//                        Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                    userProxy.setOnResetPasswordListener(this);
//                    LogUtils.i(TAG,"reset password begin....");
//                    progressbar.setVisibility(View.VISIBLE);
                    userProxy.resetPassword(userEmailInput.getText().toString().trim());
                }

                break;

            case R.id.login_menu:
                operation = UserOperation.LOGIN;
                updateLayout(operation);
                break;
            case R.id.register_menu:
                operation = UserOperation.REGISTER;
                updateLayout(operation);
                break;
            case R.id.reset_password_menu:
                operation = UserOperation.RESET_PASSWORD;
                updateLayout(operation);
                break;
            default:
                break;

        }
    }
    private void updateLayout(UserOperation op){
        if(op == UserOperation.LOGIN){
            loginTitle.setTextColor(Color.parseColor("#D95555"));
            loginTitle.setBackgroundResource(R.drawable.bg_login_tab);
            loginTitle.setPadding(16, 16, 16, 16);
            loginTitle.setGravity(Gravity.CENTER);


            registerTitle.setTextColor(Color.parseColor("#888888"));
            registerTitle.setBackgroundDrawable(null);
            registerTitle.setPadding(16, 16, 16, 16);
            registerTitle.setGravity(Gravity.CENTER);

            resetPassword.setTextColor(Color.parseColor("#888888"));
            resetPassword.setBackgroundDrawable(null);
            resetPassword.setPadding(16, 16, 16, 16);
            resetPassword.setGravity(Gravity.CENTER);

            userNameInput.setVisibility(View.VISIBLE);
            userPasswordInput.setVisibility(View.VISIBLE);
            userEmailInput.setVisibility(View.GONE);
            btnAction.setText("登录");
        }else if(op == UserOperation.REGISTER){
            loginTitle.setTextColor(Color.parseColor("#888888"));
            loginTitle.setBackgroundDrawable(null);
            loginTitle.setPadding(16, 16, 16, 16);
            loginTitle.setGravity(Gravity.CENTER);

            registerTitle.setTextColor(Color.parseColor("#D95555"));
            registerTitle.setBackgroundResource(R.drawable.bg_login_tab);
            registerTitle.setPadding(16, 16, 16, 16);
            registerTitle.setGravity(Gravity.CENTER);

            resetPassword.setTextColor(Color.parseColor("#888888"));
            resetPassword.setBackgroundDrawable(null);
            resetPassword.setPadding(16, 16, 16, 16);
            resetPassword.setGravity(Gravity.CENTER);

            userNameInput.setVisibility(View.VISIBLE);
            userPasswordInput.setVisibility(View.VISIBLE);
            userEmailInput.setVisibility(View.VISIBLE);
            btnAction.setText("注册");
        }else{
            loginTitle.setTextColor(Color.parseColor("#888888"));
            loginTitle.setBackgroundDrawable(null);
            loginTitle.setPadding(16, 16, 16, 16);
            loginTitle.setGravity(Gravity.CENTER);

            registerTitle.setTextColor(Color.parseColor("#888888"));
            registerTitle.setBackgroundDrawable(null);
            registerTitle.setPadding(16, 16, 16, 16);
            registerTitle.setGravity(Gravity.CENTER);

            resetPassword.setTextColor(Color.parseColor("#D95555"));
            resetPassword.setBackgroundResource(R.drawable.bg_login_tab);
            resetPassword.setPadding(16, 16, 16, 16);
            resetPassword.setGravity(Gravity.CENTER);


            userNameInput.setVisibility(View.GONE);
            userPasswordInput.setVisibility(View.GONE);
            userEmailInput.setVisibility(View.VISIBLE);
            btnAction.setText("找回密码");
        }
    }
}
