package com.life.a666;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.life.application.MyApplication;


/**
 * 注意因为有的手机没有menu按键，想要有menu功能
 * 则必须使用ActionBarActivity
 * ActionBar整合了menu功能导致android3.0以上的menu按钮只能现实在ActionBar上
 */

//public class MainActivity extends FragmentActivity implements OnClickListener {
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;
    private LinearLayout bottom;

    private ImageButton imgMessage;
    private ImageButton imgFind;
    private ImageButton imgDance;
    private ImageButton imgMe;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(savedInstanceState);
        initEvent();
        setSelect(0);
    }

    private void initView(Bundle savedInstanceState) {
/**  用于解决重建activity时fragment重叠问题
 *  http://my.oschina.net/wangxnn/blog/417581
 *
 */
        FragmentManager fManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mTab01 = (MessageFragment) fManager.findFragmentByTag("MessageFragment");
            mTab02 = (OtherFragment) fManager.findFragmentByTag("OtherFragment");
            mTab03 = (SoloFragment) fManager.findFragmentByTag("SoloFragment");
            mTab04 = (SettingFragment) fManager.findFragmentByTag("SettingFragment");
        }


        //四个tab
        mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
        mTabSettings = (LinearLayout) findViewById(R.id.id_tab_settings);

        bottom = (LinearLayout) findViewById(R.id.bottom);

        //四个imagebutton
        imgMessage = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        imgFind = (ImageButton) findViewById(R.id.id_tab_frd_img);
        imgDance = (ImageButton) findViewById(R.id.id_tab_address_img);
        imgMe = (ImageButton) findViewById(R.id.id_tab_settings_img);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        int system = getResources().getColor(R.color.system);
        mToolbar.setBackgroundColor(system);
    }

    private void initEvent() {
//        mToolbar.setTitle("title");
        //设置整体的actionbar
//        ActionBarView.initActionbar(this, mToolbar);
        //自定义的底部颜色
//        bottom.setBackgroundColor(MyApplication.getInstance().getPrimaryColor());
//        bottom.setBackgroundColor(MyApplication.getInstance().getPrimaryColor());

        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    public void onClick(View v) {
        resetImgs();
        switch (v.getId()) {
            case R.id.id_tab_weixin:
                mToolbar.setTitle("消息");
                setSelect(0);
                break;
            case R.id.id_tab_frd:
                mToolbar.setTitle("solo");
                setSelect(1);
                break;
            case R.id.id_tab_address:
                mToolbar.setTitle("其他");
                setSelect(2);
                break;
            case R.id.id_tab_settings:
                mToolbar.setTitle("设置");
                setSelect(3);
                break;
            default:
                break;
        }
    }
/**
 * 这里有一个值得注意的地方就是Fragment的切换问题
 * 直接用replace是很不负责任的方式，那样的话，被替换的Fragment下次又要重新实例化
 * 所以用hide和show方法来解决切换问题。
 * http://www.yrom.net/blog/2013/03/10/fragment-switch-not-restart/
 * */
    private void setSelect(int i) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //隐藏所有显示过的Fragment
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new MessageFragment();
                    /**这里要 注意Fragment的导入包要统一，本文件的Fragment如果 是import android.support.v4.app.Fragment;
                     *那么WeixinFragment.java的导入包也是import android.support.v4.app.Fragment;不能是import android.app.Fragment;
                     * */
//                    transaction.remove();
//                    transaction.replace(R.id.id_content, mTab01);
                    transaction.add(R.id.id_content, mTab01, "MessageFragment");//把mTab01那个fragment添加到id_content这个布局里面。
                    /**如果使用android.app.Fragment，add 和show 方法都会有问题*/
                } else {
                    transaction.show(mTab01);
                }
                imgMessage.setImageResource(R.drawable.message_pressed);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new OtherFragment();
                    transaction.add(R.id.id_content, mTab02, "OtherFragment");
                } else {
                    transaction.show(mTab02);
                }
                imgFind.setImageResource(R.drawable.find_pressed);
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new SoloFragment();
                    transaction.add(R.id.id_content, mTab03, "SoloFragment");
                } else {
                    transaction.show(mTab03);
                }
                imgDance.setImageResource(R.drawable.dance_pressed);
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new SettingFragment();
                    transaction.add(R.id.id_content, mTab04, "SettingFragment");
                } else {
                    transaction.show(mTab04);
                }  
                imgMe.setImageResource(R.drawable.me_pressed);

                break;

            default:
                break;
        }

//        提交事物
        transaction.commit();

    }

    /**
     * 全部设置为没有按下的图片
     */
    private void resetImgs() {
        imgMessage.setImageResource(R.drawable.message);
        imgFind.setImageResource(R.drawable.find);
        imgDance.setImageResource(R.drawable.dance);
        imgMe.setImageResource(R.drawable.me);
    }

    /**
     * 把所有的Fragment隐藏起来了
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /**
         * 注意因为有的手机没有menu按键，想要有menu功能
         * 则必须使用ActionBarActivity
         * ActionBar整合了menu功能导致android3.0以上的menu按钮只能现实在ActionBar上*/
        /**把xml文件定义的菜单实例化为菜单对象*/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.de_ic_add));
        //menu.getItem(0).getSubMenu().getItem(2).setIcon(getResources().getDrawable(R.drawable.de_btn_main_contacts));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            发动态
            case R.id.add_item1:
                Toast.makeText(MainActivity.this, "onOptionsItemSelected add_item1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EditActivity.class));
                break;
//            登陆注册改密码
            case R.id.add_item2:

                startActivity(new Intent(this, RegisterAndLoginActivity.class));
                break;
            case R.id.add_item3://通讯录
//                startActivity(new Intent(this, MomentsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23) {
            int primaryColor = data.getIntExtra("primaryColor", 0);
            ((MyApplication) getApplication()).setPrimaryColor((primaryColor));
            this.recreate();
        }
    }
}
