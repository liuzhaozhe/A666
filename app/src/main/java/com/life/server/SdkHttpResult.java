package com.life.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 惟我独尊 on 2015/8/22.
 */
//对server sdk返回的封装
public class SdkHttpResult {

    private int code;
    private String result;
    private String token;

    public SdkHttpResult(int code, String result) throws JSONException {
        this.code = code;
        this.result = result;
        setToken(result);
    }

    public int getHttpCode() {
        return code;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":\"%s\",\"result\":%s}", code,
                result);
    }
 /*解析获得的服务器json数据，
  * 例如：{"code":200, "userId":"jlk456j5", "token":"sfd9823ihufi"}
  * 获取token
  * */
    public void setToken(String result) throws JSONException{
    	 //JsonObject jsonObject = new JsonObject(result); 
        /***********JsonObject***JSONObject****这是不一样的
         * JSONObject来自org.json包
         * JsonObject来自com.google.gson.JsonObject;*/
        JSONObject root=new JSONObject(result);
        System.out.println("这是解析得到的Token"+root.getString("token"));
        token=root.getString("token");
    }
    public String getToken(){
    	return token;
    }
}

