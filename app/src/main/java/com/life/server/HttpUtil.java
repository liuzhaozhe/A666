package com.life.server;

/**
 * Created by 惟我独尊 on 2015/8/22.
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.life.server.SdkHttpResult;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONObject;
public class HttpUtil {

    private static final String APPKEY = "RC-App-Key";
    private static final String NONCE = "RC-Nonce";
    private static final String TIMESTAMP = "RC-Timestamp";
    private static final String SIGNATURE = "RC-Signature";

    // 设置body体
    public static void setBodyParameter(StringBuilder sb, HttpURLConnection conn)
            throws IOException {
        //得到输出流，奇怪的是为什么conn都还没有开始连接啊，没有调用connect()方法 ，看来不需要显示的调用connect()方法，再问下学长吧，我不清楚
        /**
         * 输出流就是可以传递参数给目标网站
         * 输入流就是可以把数据读取到本地啦！
         * */
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(sb.toString());//将参数写入到流中
        out.flush();//将流中的数据全部写入
        out.close();//关闭流
    }

    // 添加签名header
    public static HttpURLConnection CreatePostHttpConnection(String appKey,
                                                             String appSecret, String uri) throws MalformedURLException,
            IOException, ProtocolException {
        String nonce = String.valueOf(Math.random() * 1000000);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        StringBuilder toSign = new StringBuilder(appSecret).append(nonce)
                .append(timestamp);
        String sign = CodeUtil.hexSHA1(toSign.toString());

        /**
         * 这是使用HttpUrlConnection的POST方法访问网络
         * 步骤如下：
         * 1，新建URL
         * 2.获得HttpURLConnection连接对象
         * 3.设置连接对象，注意设置请求方法为POST
         * 4.获得输出流，写入数据】】
         * 5.获得输入流，读取返回的数据
         * 6.关闭流
         * 在这个CreatePostHttpConnection方法里面还没有获取数据，只是返回了一个HttpURLConnection对象*/
        //1，新建URL
        URL url = new URL(uri);//这个URI就是根据网站上提示构建的
        //URL：https://api.cn.ronghub.com/user/getToken.[format]，是固定的，只有最后一个format不是固定的
        //[format] 表示返回格式，可以为 json 或 xml，注意不要带 [ ]。
        //2.获得HttpURLConnection连接对象
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //3.设置连接对象，注意设置请求方法为POST
        conn.setUseCaches(false);//是否允许使用缓存，注意post方法不可以使用缓存
        conn.setDoInput(true);//设置该连接允许读取
        conn.setDoOutput(true);//设置该连接允许写入
        conn.setRequestMethod("POST");//设置请求方法为POST
        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(30000);//设置超时时间
        conn.setReadTimeout(30000);

        /**setRequestProperty用来设置请求头文报属性
         比如Cookie、User-Agent（浏览器类型）等等，具体可以看HTTP头相关的材料
         至于要设置什么这个要看服务器端的约定*/
        conn.setRequestProperty(APPKEY, appKey);
        conn.setRequestProperty(NONCE, nonce);
        conn.setRequestProperty(TIMESTAMP, timestamp);
        conn.setRequestProperty(SIGNATURE, sign);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        return conn;
    }

    //读取输入流，在returnResult(HttpURLConnection conn)方法里面被调用
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();

       
        return data;
    }

    public static SdkHttpResult returnResult(HttpURLConnection conn)
            throws Exception, IOException {
        String result;
        InputStream input = null;
        if (conn.getResponseCode() == 200) {
            //获取输入流
            input = conn.getInputStream();
        } else {
            input = conn.getErrorStream();
        }
        //封装了readInputStream方法读取输入流
        result = new String(readInputStream(input));
        
        
        return new SdkHttpResult(conn.getResponseCode(), result);
    }
}

