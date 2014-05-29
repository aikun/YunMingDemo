package com.imcore.yunmingdemo.ui;

import com.imcore.myphonedemo.R;
import com.imcore.myphonedemo.R.id;
import com.imcore.myphonedemo.R.layout;
import com.imcore.myphonedemo.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity {
	
	private EditText etGetSecurityCode;
	private Button btnGetSecurityCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		etGetSecurityCode = (EditText) findViewById(R.id.et_get_security_code);
		btnGetSecurityCode = (Button) findViewById(R.id.btn_get_security_code);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
