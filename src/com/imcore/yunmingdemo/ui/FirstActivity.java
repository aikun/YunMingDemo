package com.imcore.yunmingdemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.imcore.myphonedemo.R;

public class FirstActivity extends ActionBarActivity {
	
	private ViewPager viewPager;
	private ActionBar mActionBar;
	private String[] tabTitle = new String[] {"首页","商城","我的",};
	private FragmentAdapter mFragmentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		mFragmentAdapter = new FragmentAdapter();
		viewPager.setAdapter(mFragmentAdapter);
		
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowHomeEnabled(false);
		
		viewPager.setOnPageChangeListener(mOnpageChangeListener);
		
		for(String str:tabTitle) {
			Tab tab = mActionBar.newTab();
			tab.setText(str);
//			tab.setIcon(R.drawable.tab_home_normal);
			tab.setTabListener(mTabListener);
			mActionBar.addTab(tab);
			
		}
		
		
	}
	
	// ViewPager切换页面时的监听器
	private OnPageChangeListener mOnpageChangeListener = new OnPageChangeListener() {
		
		@SuppressLint("NewApi")
		@Override
		public void onPageSelected(int arg0) {
			getActionBar().setSelectedNavigationItem(arg0);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	
	// Tab监听器
	private TabListener mTabListener = new TabListener() {
		
		@Override
		public void onTabReselected(Tab arg0,
				android.support.v4.app.FragmentTransaction arg1) {
			
		}

		@Override
		public void onTabSelected(Tab arg0,
				android.support.v4.app.FragmentTransaction arg1) {
			int position = arg0.getPosition();
			viewPager.setCurrentItem(position);
			
		}

		@Override
		public void onTabUnselected(Tab arg0,
				android.support.v4.app.FragmentTransaction arg1) {
			
		}
	};
	
	// ViewPager适配器
	private class FragmentAdapter extends FragmentStatePagerAdapter {

		public FragmentAdapter() {
			super(getSupportFragmentManager());
		}

		@Override
		public Fragment getItem(int arg0) {
			if (arg0 == 0) {
				HomeFragment homeFragment = new HomeFragment();
				return homeFragment;
			}
			if (arg0 == 1) {
				MallFragment mallFragment = new MallFragment();
				return mallFragment;
			}
			if (arg0 == 2) {
				MineFragment mineFragment = new MineFragment();
				return mineFragment;
			}
			return null;
		}

		@Override
		public int getCount() {
			return tabTitle.length;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
