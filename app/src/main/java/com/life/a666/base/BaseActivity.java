package com.life.a666.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.life.util.Constant;

import cn.bmob.v3.Bmob;

public abstract class BaseActivity extends ActionBarActivity {

//	protected static String TAG ;

//	protected MyApplication mMyApplication;
//	protected Sputil sputil;
//	protected Resources mResources;
//	protected Context mContext;

    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-generated method stub
        super.onCreate(bundle);
//        初始化bmob
        Bmob.initialize(this, Constant.BMOB_APP_ID);
        setLayoutView();
        initView();
        initEvent();

//		TAG = this.getClass().getSimpleName();
//		initConfigure();
//		PushAgent.getInstance(mContext).onAppStart();
    }

    protected abstract  void setLayoutView() ;
    protected abstract  void initView() ;



    protected abstract void initEvent();
//
//	private void initConfigure() {
//		mContext = this;
//		if(null == mMyApplication){
//			mMyApplication = MyApplication.getInstance();
//		}
//		mMyApplication.addActivity(this);
//		if(null == sputil){
//			sputil = new Sputil(this, Constant.PRE_NAME);
//		}
//		sputil.getInstance().registerOnSharedPreferenceChangeListener(this);
//		mResources = getResources();
//	}


//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
//			String key) {
//		// TODO Auto-generated method stub
//		//可用于监听设置参数，然后作出响应
//	}

//    /**
//     * Activity跳转
//     *
//     * @param context
//     * @param targetActivity
//     * @param bundle
//     */
//	public void redictToActivity(Context context,Class<?> targetActivity,Bundle bundle){
//		Intent intent = new Intent(context, targetActivity);
//		if(null != bundle){
//			intent.putExtras(bundle);
//		}
//		startActivity(intent);
//	}
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		什么意思
//		MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		MobclickAgent.onPause(this);
    }

}
