package com.imcore.yunmingdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.imcore.yunmingdemo.model.ProductCatagory;
import com.imcore.yunmingdemo.model.User;
import com.imcore.yunmingdemo.util.ConnectivityUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

public class MyApplication extends Application{

	public List<String> imgUrlList = new ArrayList<String>();
	public User user = new User();
	public List<ProductCatagory> allCatagory = new ArrayList<ProductCatagory>();
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		if (!ConnectivityUtil.isOnline(this)) {
			ToastUtil.showToast(this, "请检查网络");
			return;
		}
	}
}	
	

