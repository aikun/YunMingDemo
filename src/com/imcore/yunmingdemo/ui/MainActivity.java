package com.imcore.yunmingdemo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;



import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.ITransitionInfo;

public class MainActivity extends ActionBarActivity {

	private static final String INFO_FRAGMENT_2_FRAGMENT_BUNDLE = "InfoFragment2FragmentBundel";
	
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;

	private String[] mNaviItemText;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;

	private final static String NAVI_ITEM_TEXT = "text";
	private final static String NAVI_ITEM_ICON = "icon";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initDrawerLayout();
		selectNaviItem(0);
	}

	private void initDrawerLayout() {
		mNaviItemText = getResources().getStringArray(R.array.navi_items);
		mDrawerTitle = getResources().getString(R.string.app_name);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put(NAVI_ITEM_TEXT, mNaviItemText[0]);
		item1.put(NAVI_ITEM_ICON, R.drawable.tab_home_normal);

		Map<String, Object> item2 = new HashMap<String, Object>();
		item2.put(NAVI_ITEM_TEXT, mNaviItemText[1]);
		item2.put(NAVI_ITEM_ICON, R.drawable.tab_mall_normal);

		Map<String, Object> item3 = new HashMap<String, Object>();
		item3.put(NAVI_ITEM_TEXT, mNaviItemText[2]);
		item3.put(NAVI_ITEM_ICON, R.drawable.tab_mine_normal);


		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data.add(item1);
		data.add(item2);
		data.add(item3);

		String[] from = new String[] { NAVI_ITEM_TEXT, NAVI_ITEM_ICON };
		int[] to = new int[] { R.id.tv_navi_item_text, R.id.iv_navi_item_icon };
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new SimpleAdapter(this, data,
				R.layout.view_navi_drawer_item, from, to));
		mDrawerList
				.setOnItemClickListener(new NaviDrawerListItemOnClickListner());

		initialDrawerListener();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	private void initialDrawerListener() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				getSupportActionBar().setTitle(mTitle);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private class NaviDrawerListItemOnClickListner implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectNaviItem(position);
		}
	}

	private void selectNaviItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new MallFragment();
			break;
		case 2:
			fragment = new MineFragment();
			break;
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.main_content, fragment);
		ft.commit();

		mDrawerList.setItemChecked(position, true);
		setTitle(mNaviItemText[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		//
		return super.onOptionsItemSelected(item);
	}

//	@Override
//	public void transitionInfo(int i) {
//		ProductTeaLeavesFragment productTealeavesFragment = null;
//
//		Bundle args = new Bundle();
//		args.putInt(INFO_FRAGMENT_2_FRAGMENT_BUNDLE, i);
//
//		productTealeavesFragment = new ProductTeaLeavesFragment();
//		// 传递参数
//		productTealeavesFragment.setArguments(args);
//
//	}
}
