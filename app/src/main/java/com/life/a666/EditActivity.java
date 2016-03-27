package com.life.a666;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.life.a666.base.BaseActivity;
import com.life.entity.AllContent;
import com.life.entity.User;
import com.life.util.CacheUtils;
import com.life.util.toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class EditActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private static final int REQUEST_CODE_ALBUM = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    // Button commitButton;
    String dateTime;
    Context mContext;
    EditText edit_content;
//    Button btnOk;
    //打开相册
    LinearLayout openLayout;
    //拍照
    LinearLayout takeLayout;
    //获取图片之后把得到的图片缩略图显示在这2个ImageView上
    ImageView albumPic;
    ImageView takePic;
    String targeturl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_edit);

    }

    @Override
    protected void initView() {
        edit_content = (EditText) findViewById(R.id.edit_content);
        openLayout = (LinearLayout) findViewById(R.id.open_layout);
        takeLayout = (LinearLayout) findViewById(R.id.take_layout);

        albumPic = (ImageView) findViewById(R.id.open_pic);
        takePic = (ImageView) findViewById(R.id.take_pic);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        int system = getResources().getColor(R.color.system);
        mToolbar.setBackgroundColor(system);
    }

    @Override
    protected void initEvent() {
        mContext = this;


//        btnOk.setOnClickListener(this);

        openLayout.setOnClickListener(this);
        takeLayout.setOnClickListener(this);

        albumPic.setOnClickListener(this);
        takePic.setOnClickListener(this);
        mToolbar.setTitle("编辑动态");
//        //设置整体的actionbar
//        ActionBarView.initActionbar(this, mToolbar);
//        mToolbar
    }

    //发表无图片动态
    private void publishWithoutFigure(final String commitContent,
                                      final BmobFile figureFile) {
        User user = BmobUser.getCurrentUser(mContext, User.class);

        final AllContent allContent = new AllContent();
        if (user != null) {
            allContent.setAuthor(user);
            Toast.makeText(EditActivity.this, "user!=null" + user.getUsername(), Toast.LENGTH_SHORT).show();
        }

        allContent.setContent(commitContent);
        if (figureFile != null) {
            allContent.setContentfigureurl(figureFile);
        }
        allContent.setLove(0);
        allContent.setHate(0);
        allContent.setShare(0);
        allContent.setComment(0);
        allContent.setPass(true);
        allContent.save(mContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub

//                ActivityUtil.show(EditActivity.this, "发表成功！");
//                LogUtils.i(TAG, "创建成功。")
//                setResult(RESULT_OK);
//                finish();
                Toast.makeText(EditActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
//                ActivityUtil.show(EditActivity.this, "发表失败！yg" + arg1);
//                LogUtils.i(TAG, "创建失败。" + arg1);
                Toast.makeText(EditActivity.this, "onFailure" + arg1, Toast.LENGTH_SHORT).show();
                Log.e("" + arg0, "onFailure" + arg1);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            打开相册添加图片
            case R.id.open_layout:
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, REQUEST_CODE_ALBUM);
                break;
            case R.id.take_layout:
//                Date date = new Date(System.currentTimeMillis());
//                dateTime = date.getTime() + "";
//                File f = new File(CacheUtils.getCacheDirectory(mContext, true,
//                        "pic") + dateTime);
//                if (f.exists()) {
//                    f.delete();
//                }
//                try {
//                    f.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Uri uri = Uri.fromFile(f);
//                Log.e("uri", uri + "");
//                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(camera, REQUEST_CODE_CAMERA);
//                break;
            default:
                break;
        }

    }

    //    onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("", "get album:");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
//                    文件绝对路径，比如/storage/emulated/0/DCIM/Camera/IMG_20151226_131653.jpg
                    String filePath = null;
                    if (data != null) {
//                        originalUri的值可以是content://media/external/images/media/11944
                        Uri originalUri = data.getData();
                        Log.i("", "get album: originalUri " + originalUri);
//                        外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                        ContentResolver cr = getContentResolver();
                        Cursor cursor = cr.query(originalUri, null, null, null,
                                null);
                        if (cursor.moveToFirst()) {
                            do {
                                filePath = cursor.getString(cursor
                                        .getColumnIndex("_data"));
                                Log.i("", "get album: filePath " + filePath);
                            } while (cursor.moveToNext());
                        }
                        Log.i("查看filePath",filePath);
//                        查看filePath﹕ /storage/emulated/0/tmp/share_qq_ba157c4801c663abb5b930d223032c2f.jpg
                        Bitmap bitmap = compressImageFromFile(filePath);
                        targeturl = saveToSdCard(bitmap);
                        Log.i("查看targeturl",targeturl);
//                        查看targeturl﹕ /data/data/com.life.a666/cache1452511371049_11.jpg
                        albumPic.setBackgroundDrawable(new BitmapDrawable(bitmap));
                        takeLayout.setVisibility(View.GONE);
                    }
                    break;
//            case REQUEST_CODE_CAMERA:
//                String files = CacheUtils.getCacheDirectory(mContext, true,
//                        "pic") + dateTime;
//                File file = new File(files);
//                if (file.exists()) {
//                    Bitmap bitmap = compressImageFromFile(files);
//                    targeturl = saveToSdCard(bitmap);
//                    takePic.setBackgroundDrawable(new BitmapDrawable(bitmap));
//                    openLayout.setVisibility(View.GONE);
//                } else {
//
//                }
//                break;
                default:
                    break;
            }
        }
    }
    private Bitmap compressImageFromFile(String srcPath) {
//        加载和显示图片是很消耗内存的一件事，BitmapFactory.Options 类,  允许我们定义图片以何种方式如何读到内存，
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
//        使图片变成原来的1/inSampleSize.
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }
    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(mContext, true, "pic")
                + dateTime + "_11.jpg";
        Log.i("查看files",files);
//        查看files﹕ /data/data/com.life.a666/cache1452511371049_11.jpg
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("", file.getAbsolutePath());
        return file.getAbsolutePath();
    }
    /*
	 * 发表带图片
	 */
    private void publish(final String commitContent) {

        // final BmobFile figureFile = new BmobFile(QiangYu.class, new
        // File(targeturl));

        final BmobFile figureFile = new BmobFile(new File(targeturl));

        figureFile.upload(mContext, new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("","上传文件成功。" + figureFile.getFileUrl(EditActivity.this));
                publishWithoutFigure(commitContent, figureFile);
            }

            @Override
            public void onProgress(Integer arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.i("", "上传文件失败。" + arg1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            发动态
            case R.id.action_ok:
                User user = BmobUser.getCurrentUser(mContext, User.class);
                if(user==null)
                    toast.ShowText("请先登录",mContext);
                else{
                    String commitContent = edit_content.getText().toString().trim();
                    if (TextUtils.isEmpty(commitContent)) {
                        Toast.makeText(EditActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                        return super.onOptionsItemSelected(item);
                    }
//				如果不带图片
                    if (targeturl == null) {
                        Log.i("","targeturl== null");
                        publishWithoutFigure(commitContent, null);
                        finish();
                    } else {
                        Log.i("","targeturl==  "+targeturl);
                        publish(commitContent);
                        finish();
                    }


                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
