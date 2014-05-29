package com.imcore.yunmingdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.data.SharedPreferencesUtil;
import com.imcore.yunmingdemo.model.User;

public class SplashActivity extends Activity {

	private static final String USER_INFO = "user_info";
//	private static final String USER_ID = "user_id";
//	private static final String TOKEN = "token";
	private static final String IS_SAVED = "isSaved";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler handler = new Handler();
		handler.postDelayed(new SplashHandler(), 500);
	}
	
	class SplashHandler implements Runnable {
        public void run() {
//        	User user = new SharedPreferencesUtil(SplashActivity.this).getUserInfo();
//        	if(user != null) {
//        		startActivity(new Intent(getApplication(), MainActivity.class));
//        		SplashActivity.this.finish();
//        	} else {
//        		startActivity(new Intent(getApplication(), LoginActivity.class));
//        		SplashActivity.this.finish();
//        	}
        	
        	
        	SharedPreferences sp = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        	User user = new User();
        	user.isSaved = sp.getBoolean(IS_SAVED, false);
        	if(user.isSaved) {
        		startActivity(new Intent(getApplication(), MainActivity.class));
        		SplashActivity.this.finish();
        	} else {
        		startActivity(new Intent(getApplication(), LoginActivity.class));
        		SplashActivity.this.finish();
        	}
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
