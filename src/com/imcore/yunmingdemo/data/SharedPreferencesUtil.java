package com.imcore.yunmingdemo.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.imcore.yunmingdemo.model.User;
import com.imcore.yunmingdemo.util.TextUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

/**
 * 保存用户信息与读取
 * 
 * @author Administrator
 * 
 */
public class SharedPreferencesUtil {

	private static final String USER_INFO = "user_info";
	private static final String USER_ID = "user_id";
	private static final String TOKEN = "token";
	private static final String USER_NAME = "user_name";
	private static final String PASSWORD = "passsword";
	private static final String IS_SAVED = "isSaved";

	private Context mContext;

	// 无参构造方法
	public SharedPreferencesUtil() {
	}

	public SharedPreferencesUtil(Context context) {
		mContext = context;
	}

	/**
	 * 读取用户名与密码
	 * 
	 * @return
	 */
//	public User getUserInfo() {
//		User user = null;
//
//		SharedPreferences sp = mContext.getSharedPreferences(USER_INFO,
//				Context.MODE_PRIVATE);
////		if (!TextUtil.isEmptyString(sp.getString(USER_ID, ""))
////				&& !TextUtil.isEmptyString(sp.getString(TOKEN, ""))) {
//			user = new User();
//			user.id = sp.getString(USER_ID, "");
//			user.token = sp.getString(TOKEN, "");
//			user.userName = sp.getString(USER_NAME, "");
//			user.password = sp.getString(PASSWORD, "");
////		}
//		return user;
//	}

	/**
	 * 保存用户信息
	 * 
	 * @param phoneNumber
	 * @param password
	 */
	public void saveUserInfo(User user) {
		SharedPreferences sp = mContext.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		boolean isSaved = sp.edit().putInt(USER_ID, user.id)
				.putString(TOKEN, user.token).putString(USER_NAME, user.phoneNumber)
				.putString(PASSWORD, user.password).putBoolean(IS_SAVED, user.isSaved).commit();
		if (isSaved) {
//			ToastUtil.showToast(mContext, "账户信息保存成功");
		}
	}

}
