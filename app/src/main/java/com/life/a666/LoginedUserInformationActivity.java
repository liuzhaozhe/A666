package com.life.a666;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.life.a666.base.BaseActivity;
import com.life.application.MyApplication;
import com.life.entity.User;
import com.life.util.ActivityUtil;
import com.life.util.CacheUtils;
import com.life.util.toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class LoginedUserInformationActivity extends BaseActivity implements View.OnClickListener {

    Context context;
    AlertDialog albumDialog;
    String dateTime;
    RelativeLayout personal_icon;
    ImageView userIcon;
    String iconUrl;
    User currentUser;
    private static final int REQUEST_CODE_ALBUM = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_logined_user_information);
    }

    @Override
    protected void initView() {
        personal_icon = (RelativeLayout) findViewById(R.id.personal_icon);
        userIcon = (ImageView) findViewById(R.id.personal_icon_content);

        currentUser = MyApplication.getInstance().getCurrentUser();
        context = LoginedUserInformationActivity.this;
//        获取头像
        BmobFile avatarFile = currentUser.getAvatar();

        if (null != avatarFile) {
            Log.i("查看avatarFile链接",avatarFile.getFileUrl(context));
            ImageLoader.getInstance().displayImage(avatarFile.getFileUrl(context), userIcon);
//            ImageLoader.getInstance().displayImage(avatarFile.getFileUrl(context), userIcon,
//                    MyApplication.getInstance().getOptions(
//                            R.drawable.ic_launcher),
//                    new SimpleImageLoadingListener() {
//
//                        @Override
//                        public void onLoadingComplete(String imageUri,
//                                                      View view, Bitmap loadedImage) {
//                            // TODO Auto-generated method stub
//                            super.onLoadingComplete(imageUri, view, loadedImage);
//                        }
//
//                    });
        }
    }

    @Override
    protected void initEvent() {
//        currentUser= MyApplication.getInstance().getCurrentUser();

        personal_icon.setOnClickListener(this);
    }

    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(context).create();
        albumDialog.setCanceledOnTouchOutside(false);
        View v = LayoutInflater.from(context).inflate(
                R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);

        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
//		相册
        albumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
//                Date date1 = new Date(System.currentTimeMillis());
//                dateTime = date1.getTime() + "";
//                getAvataFromAlbum();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, REQUEST_CODE_ALBUM);
            }
        });
//		拍照
        cameraPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                albumDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                getAvataFromCamera();
            }
        });
    }

    private void getAvataFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(context, true, "icon")
                + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, 1);
    }

    private void getAvataFromAlbum() {
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        startActivityForResult(intent2, 2);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 锟斤拷锟斤拷锟斤拷锟絚rop=true锟斤拷锟斤拷锟斤拷锟节匡拷锟斤拷锟斤拷Intent锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷VIEW锟缴裁硷拷
        // aspectX aspectY 锟角匡拷叩谋锟斤拷锟�
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 锟角裁硷拷图片锟斤拷锟�
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去锟斤拷锟节憋拷
        intent.putExtra("scaleUpIfNeeded", true);// 去锟斤拷锟节憋拷
        // intent.putExtra("noFaceDetection", true);//锟斤拷锟斤拷识锟斤拷
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);

    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(context, true, "icon")
//                + dateTime + "_12";
                + dateTime + "_12.jpg";
        File file = new File(files);
        Log.i("Logined查看files",files);

        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
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
//        LogUtils.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private Bitmap compressImageFromFile(String srcPath) {
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
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }

    private void setAvata(String avataPath) {
//        if (avataPath != null) {
        final BmobFile file = new BmobFile(new File(iconUrl));
        file.upload(context, new UploadFileListener() {

            @Override
            public void onSuccess() {
                toast.ShowText("图片上传成功", context);
                // TODO Auto-generated method stub
//                    LogUtils.i(TAG, "上传文件成功。" + file.getFileUrl(mContext));
                currentUser.setAvatar(file);
//                    if (!TextUtils.isEmpty(signatureEdit.getText().toString()
//                            .trim())) {
//                        currentUser.setSignature(signatureEdit.getText()
//                                .toString().trim());
//                    }
//                    currentUser.setSex(newSex);
                currentUser.update(context, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        toast.ShowText("更新头像成功", context);
                        // TODO Auto-generated method stub
//                            ActivityUtil.show(getActivity(), "更新信息成功。");
//                            currentUser = BmobUser.getCurrentUser(
//                                    getActivity(), User.class);
//                            LogUtils.i(
//                                    TAG,
//                                    "new url:"
//                                            + BmobUser
//                                            .getCurrentUser(
//                                                    getActivity(),
//                                                    User.class)
//                                            .getAvatar()
//                                            .getFileUrl(mContext));
//                            getActivity().setResult(Activity.RESULT_OK);
//                            getActivity().finish();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
//                            ActivityUtil.show(getActivity(), "更新信息失败。请检查网络~");
//                            LogUtils.i(TAG, "更新失败2-->" + arg0);
                        toast.ShowText("更新头像失败" + arg1, context);
                    }
                });
            }

            @Override
            public void onProgress(Integer arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
//                    LogUtils.i(TAG, "上传文件失败。" + arg1);
                toast.ShowText("上传文件失败" + arg1, context);
            }
        });
//        } else {
//            currentUser.setSex(newSex);
//            if (!TextUtils.isEmpty(signatureEdit.getText().toString().trim())) {
//                currentUser.setSignature(signatureEdit.getText().toString()
//                        .trim());
//            }
//            currentUser.update(mContext, new UpdateListener() {
//
//                @Override
//                public void onSuccess() {
//                    // TODO Auto-generated method stub
//                    ActivityUtil.show(getActivity(), "更新信息成功。");
//                    LogUtils.i(TAG, "更新信息成功。");
//                    getActivity().setResult(Activity.RESULT_OK);
//                    getActivity().finish();
//                }
//
//                @Override
//                public void onFailure(int arg0, String arg1) {
//                    // TODO Auto-generated method stub
//                    ActivityUtil.show(getActivity(), "更新信息失败。请检查网络~");
//                    LogUtils.i(TAG, "更新失败1-->" + arg1);
//                }
//            });
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_icon:
                showAlbumDialog();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
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
                        Log.i("Logined查看filePath",filePath);
                        Bitmap bitmap = compressImageFromFile(filePath);
                        iconUrl = saveToSdCard(bitmap);
                        Log.i("Logined查看iconUrl",iconUrl);
                        userIcon.setImageBitmap(bitmap);
//                        userIcon.setBackgroundDrawable(new BitmapDrawable(bitmap));
//                        takeLayout.setVisibility(View.GONE);
                    }
                    Log.i("bitmap",iconUrl);
                    setAvata(iconUrl);
                    break;
//                case 1:
//                    String files = CacheUtils.getCacheDirectory(context, true,
//                            "icon") + dateTime;
//                    File file = new File(files);
//                    if (file.exists() && file.length() > 0) {
//                        Uri uri = Uri.fromFile(file);
//                        startPhotoZoom(uri);
//                    } else {
//
//                    }
//                    break;
//                case 2:
//                    if (data == null) {
//                        return;
//                    }
//                    startPhotoZoom(data.getData());
//                    break;
//                case 3:
//                    if (data != null) {
//                        Bundle extras = data.getExtras();
//                        if (extras != null) {
//                            Bitmap bitmap = extras.getParcelable("data");
//                            // 锟斤拷锟斤拷图片
//                            iconUrl = saveToSdCard(bitmap);
//                            userIcon.setImageBitmap(bitmap);
//                            setAvata(iconUrl);
//                        }
//                    }
//
//                    break;
                default:
                    break;
            }

        }
    }
}
