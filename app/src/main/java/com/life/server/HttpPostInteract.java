package com.life.server;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.life.application.MyApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 惟我独尊 on 2015/8/30.
 * 这是使用HttpClient的交互方式，避免了HttpUrlConnection的繁琐的设置，把post方法封装为HttpPost类
 * 把Get方法封装为HttpGet类
 * 这个类是使用HttpClient的post方法
 * interact这个类使用的是传统的post方法
 */
public class HttpPostInteract {

    public static String TAG = "HttpPostInteract";
    //public static HttpURLConnection conn;
    public static HttpPost httpRequest = null;
    public static HttpClient httpClient = null;
    public static String url = null;

    //    public static String IP = null;
    public static Void setPath(String IP,String serverName, String serveletName) {
        //url=url+IP+":8888/server/hello";//不能这样用字符串的叠加方式，这样的话第一次确实是对的，但是如果程序没有重新启动，第二次继续运行。
        //url这个字符串会越加越长，导致访问不到页面。所以改成url="http://"+IP+":8888/server/hello";
        //Log.i("setPath",url);
        url = "http://" + IP + ":8080/"+serverName+"/" + serveletName;
//        this.IP=
        setHttpRequest(url);
        return null;
    }

    public static Void setHttpRequest(String url) {
        Log.i(TAG, url);
        httpRequest = new HttpPost(url);
        return null;
    }

    public static Void Send(String NickName, String ID) throws IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        BasicNameValuePair pair1 = new BasicNameValuePair("NickName", NickName);
        BasicNameValuePair pair2 = new BasicNameValuePair("ID", ID);
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("NickName", NickName);
//        params.put("ID", ID);
        params.add(pair1);
        params.add(pair2);
        return sendPOSTRequest(params, "UTF-8");
    }

    public static Void Send(String NickName) throws IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        BasicNameValuePair pair1 = new BasicNameValuePair("NickName", NickName);
//        BasicNameValuePair pair2=new BasicNameValuePair("ID",ID);
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("NickName", NickName);
//        params.put("ID", ID);
        params.add(pair1);
//        params.add(pair2);
        return sendPOSTRequest(params, "UTF-8");
    }


    public static Void sendPOSTRequest(List<NameValuePair> params, String encoding) throws IOException {

        HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        httpRequest.setEntity(entity);
        httpClient = new DefaultHttpClient();
        new myAnyctask().execute();
        return null;
    }

    //定义一个内部类
    static class myAnyctask extends AsyncTask<Void, Void, Void> {

        //该方法运行子线程，进行耗时操作
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //错误.这是因为没有添加网络权限
                //org.apache.http.conn.HttpHostConnectException: Connection to refused
                HttpResponse httpResponse = httpClient.execute(httpRequest);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    //获取结果
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    Log.i(TAG, "这是获取到的服务器的结果" + result);
                    saveToken(result);
                } else {
                    Log.e(TAG, "访问错误" + httpResponse.getStatusLine().toString());
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                e.printStackTrace();
            }
            return null;
        }

        private void saveToken(String token) {
            Log.i(TAG,"获取到的"+token);
            //                      第一步，获得SharedPreferences对象，第一个参数指定存储数据的文件名称。第二个参数代表模式，一般默认Activity.MODE_PRIVATE
            SharedPreferences SPsavaData = MyApplication.getInstance().getSharedPreferences(MyApplication.getInstance().getCurrentUser().getUsername(), Activity.MODE_PRIVATE);
//                      第二步，获得editor对象
            SharedPreferences.Editor editor = SPsavaData.edit();
//                      第三步，存储数据
            editor.putString("Token", token);
//                      第四步，提交操作，类似于数据库
            editor.commit();
        }
    }
}
