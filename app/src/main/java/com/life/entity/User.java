package com.life.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;



/**
 * @author kingofglory email: kingofglory@yeah.net blog: http:www.google.com
 * @date 2014-3-14 TODO
 */

public class User extends BmobUser{

	public static final String TAG = "User";
	
	private String signature;
	private BmobFile avatar;
//	关联数据类型
//	收藏
	private BmobRelation favorite;
//	点赞

	public BmobRelation getLove() {
		return love;
	}

	public void setLove(BmobRelation love) {
		this.love = love;
	}

	private BmobRelation love;
	private String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BmobRelation getFavorite() {
		return favorite;
	}

	public void setFavorite(BmobRelation favorite) {
		this.favorite = favorite;
	}

	public BmobFile getAvatar() {
		return avatar;
	}

	public void setAvatar(BmobFile avatar) {
		this.avatar = avatar;
	}


	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}


}
