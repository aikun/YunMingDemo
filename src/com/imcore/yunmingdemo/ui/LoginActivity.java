package com.imcore.yunmingdemo.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.MyApplication;
import com.imcore.yunmingdemo.data.SharedPreferencesUtil;
import com.imcore.yunmingdemo.http.HttpHelper;
import com.imcore.yunmingdemo.http.RequestEntity;
import com.imcore.yunmingdemo.http.ResponseJsonEntity;
import com.imcore.yunmingdemo.model.User;
import com.imcore.yunmingdemo.util.ConnectivityUtil;
import com.imcore.yunmingdemo.util.JsonUtil;
import com.imcore.yunmingdemo.util.TextUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

public class LoginActivity extends Activity implements OnClickListener {
	
	private static final String USER_INFO = "user_info";
	private static final String USER_NAME = "user_name";
	private static final String PASSWORD = "passsword";

	private EditText etUserName, etPassword;
	private Button btnLogging, btnRegistration;
	private ImageButton imgBtn;
	private ProgressDialog pgDialog;
	private MyApplication mApplication = new MyApplication();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etPassword = (EditText) findViewById(R.id.et_password);
		etUserName = (EditText) findViewById(R.id.et_user_name);
		
		SharedPreferences sp = getSharedPreferences(USER_INFO, MODE_PRIVATE);
		
		if(sp != null) {
			etPassword.setText(sp.getString(PASSWORD, ""));
			etUserName.setText(sp.getString(USER_NAME, ""));
			
		}
		
		
		btnLogging = (Button) findViewById(R.id.btn_logging);
		btnRegistration = (Button) findViewById(R.id.btn_registration);
		imgBtn = (ImageButton) findViewById(R.id.img_btn);

		btnRegistration.setOnClickListener(this);
		btnLogging.setOnClickListener(this);
		imgBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_logging:
			doLogin();
			break;
		case R.id.btn_registration:
			doRegistration();
			break;
		case R.id.img_btn:
			doRegistration();
			break;
		}

	}

	/**
	 * 跳转到注册页面
	 */
	private void doRegistration() {
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
	}

	/**
	 * 登录
	 */
	private void doLogin() {
		if (!ConnectivityUtil.isOnline(this)) {
			ToastUtil.showToast(this, "请检查网络");
			return;
		}

		String userName = etUserName.getText().toString();
		String password = etPassword.getText().toString();

		if (!valiDateInput(userName, password)) {
			return;
		}
		// 通过AsyncTask异步请求网络服务,通过构造函数传递参数数据
		new doLoginAsyncTask(userName, password).execute();
	}

	/**
	 * 登录成功 跳到主页面
	 */
	private void gotoHome() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	// 登录操作
	private class doLoginAsyncTask extends AsyncTask<Void, Void, String> {

		private String mUserName, mPassword;

		public doLoginAsyncTask(String userName, String password) {
			mPassword = password;
			mUserName = userName;
		}

		@Override
		protected void onPreExecute() {
			pgDialog = ProgressDialog.show(LoginActivity.this, "请稍候", "正在登录");
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			String url = "/passport/login.do";

			// 把请求参数装到map中
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("phoneNumber", mUserName);
			args.put("password", mPassword);
			args.put("client", "android");

			// 构造RequestEntity
			RequestEntity entity = new RequestEntity(url, args);

			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(String result) {
			pgDialog.dismiss();
			pgDialog = null;
			// 响应回来之后，构造ResponseEntity
			if (TextUtil.isEmptyString(result)) {
				ToastUtil.showToast(LoginActivity.this, "服务器响应错误！");
				return;
			}

			ResponseJsonEntity resJsonEntity = ResponseJsonEntity
					.fromJSON(result);
			if (resJsonEntity.getStatus() == 200) {
				String jsonData = resJsonEntity.getData();
				Log.i("user", jsonData);

				mApplication.user = JsonUtil.toObject(jsonData, User.class);
				mApplication.user.password = mPassword;
				mApplication.user.isSaved = true;
				
				// 保存账号密码
				if (mApplication.user.token != null) {
					SharedPreferencesUtil spUtil = new SharedPreferencesUtil(
							LoginActivity.this);
					spUtil.saveUserInfo(mApplication.user);
				}
				gotoHome();
				finish();
			} else {
				ToastUtil.showToast(LoginActivity.this,
						resJsonEntity.getMessage());
				Log.i("user", resJsonEntity.getMessage());
			}
		
		}

	}

	/**
	 * 判断用户名、密码是否合法
	 * 
	 * @param userName
	 * @param password
	 * 
	 * @return true 合法
	 * 
	 */
	private boolean valiDateInput(String userName, String password) {
		if (TextUtil.isEmptyString(userName)) {
			ToastUtil.showToast(this, "用户名不能为空");
			return false;
		}
		if (TextUtil.isEmptyString(password)) {
			ToastUtil.showToast(this, "密码不能为空");
			return false;
		}
		if (!TextUtil.isPhoneNumber(userName)) {
			ToastUtil.showToast(this, "用户名格式不正确");
			return false;
		}
		if (!TextUtil.isNumber(password)) {
			ToastUtil.showToast(this, "密码为纯数字");
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
