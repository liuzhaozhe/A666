package com.life.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.life.entity.AllContent;
import com.life.entity.User;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.bmob.v3.BmobUser;
import io.rong.imkit.RongIM;
//import io.rong.imkit.RongIM;

public class MyApplication extends Application{

	public static String TAG;
	private static MyApplication myApplication = null;
	private static int primaryColor = 0xff03a9f4;        //默认基本色 light blue
	private AllContent currentAllContent = null;

	//定义sharepreferences保存全局变量
	private static String SETTING_INFO = "setting_info";
	private static SharedPreferences settingPreference;
	private static SharedPreferences.Editor settingEditor;

	public static MyApplication getInstance(){
		return myApplication;
	}

//	获取现在的用户BmobUser.getCurrentUser
	public User getCurrentUser() {
		
		User user = BmobUser.getCurrentUser(myApplication, User.class);
		if(user!=null){
			return user;
		}
		return null;
	}
	@Override
	public void onCreate() {

		// TODO Auto-generated method stub
		super.onCreate();
		/**
		 * 初始化融云
		 */
		RongIM.init(this);
		TAG = this.getClass().getSimpleName();
		settingPreference = getApplicationContext().getSharedPreferences(SETTING_INFO, 0);
		//由于Application类本身已经单例，所以直接按以下处理即可。
		myApplication = this;
		initImageLoader();
	}

	
	
	public AllContent getCurrentQiangYu() {
		return currentAllContent;
	}

	public void setCurrentQiangYu(AllContent currentQiangYu) {
		this.currentAllContent = currentQiangYu;
	}

//	public void addActivity(Activity ac){
//		ActivityManagerUtils.getInstance().addActivity(ac);
//	}
	
//	public void exit(){
//		ActivityManagerUtils.getInstance().removeAllActivity();
//	}
	
//	public Activity getTopActivity(){
//		return ActivityManagerUtils.getInstance().getTopActivity();
//	}
	
	/**
	 * 初始化imageLoader，加载网络图片的初始化工作
	 */
	public void initImageLoader( ){
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
										.memoryCache(new LruMemoryCache(5*1024*1024))
										.memoryCacheSize(10*1024*1024)
										.discCache(new UnlimitedDiscCache(cacheDir))
										.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
										.build();
					
//		sdk包提供
		ImageLoader.getInstance().init(config);
	}
	
	public DisplayImageOptions getOptions(int drawableId){
		return new DisplayImageOptions.Builder()
		.showImageOnLoading(drawableId)
		.showImageForEmptyUri(drawableId)
		.showImageOnFail(drawableId)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
	public void setPrimaryColor(int primaryColor) {
		this.primaryColor = primaryColor;
		settingEditor = settingPreference.edit();
		settingEditor.putInt("primaryColor", primaryColor);
		settingEditor.apply();
	}

	public int getPrimaryColor() {
		primaryColor=settingPreference.getInt("primaryColor",0xff03a9f4);
		return primaryColor;
	}
}
