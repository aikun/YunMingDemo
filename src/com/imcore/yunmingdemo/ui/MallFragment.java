package com.imcore.yunmingdemo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabWidget;

import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.ITransitionInfo;
import com.imcore.yunmingdemo.MyApplication;
import com.imcore.yunmingdemo.http.HttpHelper;
import com.imcore.yunmingdemo.http.HttpMethod;
import com.imcore.yunmingdemo.http.RequestEntity;
import com.imcore.yunmingdemo.http.ResponseJsonEntity;
import com.imcore.yunmingdemo.model.ProductCatagory;
import com.imcore.yunmingdemo.util.JsonUtil;
import com.imcore.yunmingdemo.util.TextUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

/**
 * 使用TabWidget和ViewPager实现可滑动的Tab
 * 
 * @author Administrator
 * 
 */
public class MallFragment extends Fragment {

	private static final String INFO_FRAGMENT_2_FRAGMENT_BUNDLE = "InfoFragment2FragmentBundel";

	private MyApplication mApplication = new MyApplication();
//	private List<ProductCatagory> allCatagory;// 所有大的分类信息的集合

	private ViewPager mViewpager;
	private TabWidget mTabWidget;
	private View view;
	private MyViewPagerAdapter mMyViewPagerAdapter;
	private Button[] mBtnTab;
	
	private ITransitionInfo transInfo;
	
	@Override
	public void onAttach(Activity activity) {
		try {
			transInfo = (ITransitionInfo) activity;
		} catch (ClassCastException ex) {
			Log.e("frgmt_demo", "包含NewsListFragment的Activity必须实现ITransitionInfo接口");
		}
//		transInfo = (ITransitionInfo) activity;
		super.onAttach(activity);
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mall, null);

		mViewpager = (ViewPager) view.findViewById(R.id.view_pager_mall);
		new MallTitileAsyncTask().execute();

		return view;
	}

	private Handler handler = new Handler() {
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			if (msg.what == 10) {

				mBtnTab = new Button[mApplication.allCatagory.size()];

				for (int i = 0; i < mApplication.allCatagory.size(); i++) {
					ProductCatagory pc = mApplication.allCatagory.get(i);
					mTabWidget = (TabWidget) view.findViewById(R.id.tab_widget);
					mTabWidget.setStripEnabled(false);
					mBtnTab[i] = new Button(getActivity());
					mBtnTab[i].setFocusable(true);
					mBtnTab[i].setText(pc.categoryName);
					mBtnTab[i].setBackground(getResources().getDrawable(
							R.drawable.mall_title_btn_color));
					mTabWidget.addView(mBtnTab[i]);
					/*
					 * Listener必须在mTabWidget.addView()之后再加入，用于覆盖默认的Listener，
					 * mTabWidget.addView()中默认的Listener没有NullPointer检测。
					 */
					mBtnTab[i].setOnClickListener(mTabClickListener);
				}

				mMyViewPagerAdapter = new MyViewPagerAdapter(
						getFragmentManager());
				mViewpager.setAdapter(mMyViewPagerAdapter);

				mViewpager.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						mTabWidget.setCurrentTab(arg0);

					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

				mTabWidget.setCurrentTab(0);
			}
		};
	};

	private OnClickListener mTabClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// if(v == mBtnTab[0]) {
			// mViewpager.setCurrentItem(0);
			// } else if(v == mBtnTab[1]) {
			// mViewpager.setCurrentItem(1);
			// } else if(v == mBtnTab[2]) {
			// mViewpager.setCurrentItem(2);
			// }
			for (int i = 0; i < mApplication.allCatagory.size(); i++) {

				while (v == mBtnTab[i]) {
					mViewpager.setCurrentItem(i);
				}
			}

		}
	};

	// ViewAdapter適配器
	private class MyViewPagerAdapter extends FragmentStatePagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// if(arg0 == 0) {
			// ProductTeaLeavesFragment productTealeavesFragment = new
			// ProductTeaLeavesFragment();
			// return productTealeavesFragment;
			// }
			// if(arg0 == 1) {
			// ProductTeaSetFragment productTeaSetFragment = new
			// ProductTeaSetFragment();
			// return productTeaSetFragment;
			// }
			// if(arg0 == 2) {
			// ProductTeaFoodFragment productTeaFoodFragment = new
			// ProductTeaFoodFragment();
			// return productTeaFoodFragment;
			// }
			// if(arg0 == 3) {
			// ProductTeaOtherFraggment productTeaOtherFragment = new
			// ProductTeaOtherFraggment();
			// return productTeaOtherFragment;
			// }

			// for(int i = 0; i < allCatagory.size(); i++ ) {
			// if(i == arg0) {
			ProductTeaLeavesFragment productTealeavesFragment = new ProductTeaLeavesFragment();
//			transInfo.transitionInfo(arg0);
//			Bundle args = new Bundle();
//			args.putInt(INFO_FRAGMENT_2_FRAGMENT_BUNDLE, arg0);
//			productTealeavesFragment.setArguments(args);
			return productTealeavesFragment;
			// }
			//
			// }
			//
			// return null;
		}

		@Override
		public int getCount() {
			return mApplication.allCatagory.size();
		}

		private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mTabWidget.setCurrentTab(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		};

	}

	// 异步获取大的分类信息
	private class MallTitileAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String url = "/category/list.do";

			Map<String, Object> args = new HashMap<String, Object>();
			args.put("Id", 0);

			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, args);

			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonResponse;
		}

		@Override
		protected void onPostExecute(String result) {
			if (TextUtil.isEmptyString(result)) {
				ToastUtil.showToast(getActivity(), "result为空服务器响应错误！");
				return;
			}

			ResponseJsonEntity resJsonEntity = ResponseJsonEntity
					.fromJSON(result);
			if (resJsonEntity.getStatus() == 200) {
				String jsonData = resJsonEntity.getData();

				mApplication.allCatagory = new ArrayList<ProductCatagory>();
				mApplication.allCatagory = JsonUtil.toObjectList(jsonData,
						ProductCatagory.class);

				if (mApplication.allCatagory != null) {
					Message msg = Message.obtain();
					msg.what = 10;
					handler.sendMessage(msg);
				}

			} else {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				ToastUtil.showToast(getActivity(), "22服务器响应错误！");
			}

		}

	}

	

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.fragment_mall);
	// mTabWidget = (TabWidget) findViewById(R.id.tab_widget);
	// mTabWidget.setStripEnabled(false);
	// mBtnTab[0] = new Button(this);
	// mBtnTab[0].setFocusable(true);
	// mBtnTab[0].setText(title[0]);
	// mTabWidget.addView(mBtnTab[0]);
	// /*
	// * Listener必须在mTabWidget.addView()之后再加入，用于覆盖默认的Listener，
	// * mTabWidget.addView()中默认的Listener没有NullPointer检测。
	// */
	// mBtnTab[0].setOnClickListener(mTabClickListener);
	//
	// mBtnTab[1] = new Button(this);
	// mBtnTab[1].setFocusable(true);
	// mBtnTab[1].setText(title[1]);
	// mTabWidget.addView(mBtnTab[1]);
	// mBtnTab[1].setOnClickListener(mTabClickListener);
	//
	// mBtnTab[2] = new Button(this);
	// mBtnTab[2].setFocusable(true);
	// mBtnTab[2].setText(title[2]);
	// mTabWidget.addView(mBtnTab[2]);
	// mBtnTab[2].setOnClickListener(mTabClickListener);
	//
	// mViewpager = (ViewPager) findViewById(R.id.view_pager_mall);
	//
	//
	// }
}
