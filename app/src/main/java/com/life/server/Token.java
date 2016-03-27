package com.life.server;

public class Token {
	static String key = "qd46yzrf47sjf";
	static String secret = "9blRxnzzht";
	static SdkHttpResult httpResult = null;
	public static String  ServerGetToken(String userId, String userName) throws Exception{
		/*public static SdkHttpResult getToken
		 * (String appKey, String appSecret,String userId, String userName, String portraitUri,
		FormatType format)
		这里测试的时候我自己在网站添加了一个用户*/
		httpResult = ApiHttpClient.getToken(key, secret, userId, userName,
				//"http://aa.com/a.png", FormatType.json);
				"http://aa.com/a.png", FormatType.json);
		/**
		 * result是一个SdkHttpResult对象，输出时就会转为string类型，就会调用SdkHttpResult类里面重写的toString（）*/
		System.out.println("class Example{} 这里是获取gettoken=" + httpResult.getToken());
		return httpResult.getToken();
	}
}
