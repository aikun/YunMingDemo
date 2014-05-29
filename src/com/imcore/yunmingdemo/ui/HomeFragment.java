package com.imcore.yunmingdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.imcore.myphonedemo.R;

public class HomeFragment extends Fragment implements OnClickListener{

	private View view;
	private ImageButton imgBtnNew,imgBtnHot,imgBtnInfo,imgBtnStore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, null);
		
		initialImgBtn();

		return view;
	}

	//初始化按钮
	private void initialImgBtn() {
		imgBtnHot = (ImageButton) view.findViewById(R.id.img_btn_home_hot);
		imgBtnNew = (ImageButton) view.findViewById(R.id.img_btn_home_new); 
		imgBtnInfo = (ImageButton) view.findViewById(R.id.img_btn_home_info);
		imgBtnStore = (ImageButton) view.findViewById(R.id.img_btn_home_store);
		
		imgBtnHot.setOnClickListener(this);
		imgBtnNew.setOnClickListener(this);
		imgBtnInfo.setOnClickListener(this);
		imgBtnStore.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.img_btn_home_hot:
			//
			break;
		case R.id.img_btn_home_info:
			//
			break;
		case R.id.img_btn_home_new:
			//
			break;
		case R.id.img_btn_home_store:
			//
			break;
		}
	}
	
	

}
