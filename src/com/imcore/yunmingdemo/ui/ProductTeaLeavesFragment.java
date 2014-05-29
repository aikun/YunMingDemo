package com.imcore.yunmingdemo.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.MyApplication;
import com.imcore.yunmingdemo.http.HttpHelper;
import com.imcore.yunmingdemo.http.HttpMethod;
import com.imcore.yunmingdemo.http.RequestEntity;
import com.imcore.yunmingdemo.http.ResponseJsonEntity;
import com.imcore.yunmingdemo.image.ImageFetcher;
import com.imcore.yunmingdemo.model.ProductCatagory;
import com.imcore.yunmingdemo.util.ConnectivityUtil;
import com.imcore.yunmingdemo.util.JsonUtil;
import com.imcore.yunmingdemo.util.TextUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

public class ProductTeaLeavesFragment extends Fragment {
	
	private static final String INFO_FRAGMENT_2_FRAGMENT_BUNDLE = "InfoFragment2FragmentBundel";
	
	private int i = 2;//用于接收mallFragment传过来的值
	
	private ImageView mImageView;
	private TextView mTvExList;
	private ListView mListView;
	private View view;
	private ProgressDialog pgDialog;
	private ProductDetialInfoAdapter adapter;
	//用于存放产品类别里的产品详细信息
	private MyApplication mApplication = new MyApplication();
	List<ProductCatagory> mTeaList = mApplication.allCatagory;
	private ImageFetcher mImageFecher;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mall_product_tea, null);
		
		Bundle args = getArguments();
		if(args != null) {
//			i = args.getInt(INFO_FRAGMENT_2_FRAGMENT_BUNDLE);
		}
		
		mListView = (ListView) view.findViewById(R.id.lv_mall_product_tea);
		
		mImageView = (ImageView) view.findViewById(R.id.img_mall_product_tea);
		mTvExList = (TextView) view.findViewById(R.id.tv_mall_product_tea);
		initialData();
		
		ProductCatagory pc  = mTeaList.get(i);
		if(pc != null) {
		mTvExList.setText(pc.categoryName);
		mImageFecher = new ImageFetcher();
		mImageFecher.fetch(pc.imageUrl, mImageView);
		}
		
		mListView.setAdapter(adapter);
		
		return view;
	}

	private void initialData() {
		if (!ConnectivityUtil.isOnline(getActivity())) {
			ToastUtil.showToast(getActivity(), "请检查网络");
			return;
		}
		new GetProductDetialInfoAsync().execute();
	}
	
	
	
	private class GetProductDetialInfoAsync extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			pgDialog = ProgressDialog.show(getActivity(), "请稍后", "正在加载数据");
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			
			 String url = "/category/list.do?id";
			 
			 Map<String, Object> args = new HashMap<String, Object>();
			 args.put("Id", i);
			 
			 RequestEntity entity = new RequestEntity(url,HttpMethod.GET,args);
			 
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
			if(TextUtil.isEmptyString(result)) {
				ToastUtil.showToast(getActivity(), "服务器响应错误！");
				return;
			}
			
			ResponseJsonEntity resJsonEntity = ResponseJsonEntity.fromJSON(result);
			if(resJsonEntity.getStatus() == 200) {
				String jsonData = resJsonEntity.getData();
				mTeaList = JsonUtil.toObjectList(jsonData, ProductCatagory.class);
				
				if(mTeaList != null) {
					Message msg = Message.obtain();
					msg.what = 100;
					
				}
			}
			
			super.onPostExecute(result);
		}
		
	}
	
	private class ProductDetialInfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mTeaList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mTeaList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return mTeaList.get(arg0).id;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = arg1;
			ViewHolder vh = null;
			if(view == null) {
				view = getActivity().getLayoutInflater().inflate(R.layout.fragment_mall_product_tea, null);
				vh = new ViewHolder();
				vh.imgMallProductTeaLv = (ImageView) view.findViewById(R.id.img_mall_product_tea_lv);
				vh.tvMallProductTeaLv = (TextView) view.findViewById(R.id.tv_mall_product_tea_lv);
				
				view.setTag(vh);
				
			} else {
				vh = (ViewHolder) view.getTag();
			}
			
			ProductCatagory pc = mTeaList.get(arg0);
			if(pc != null) {
				vh.tvMallProductTeaLv.setText(pc.categoryName);
				mImageFecher.fetch(pc.imageUrl, vh.imgMallProductTeaLv);
			}
			
			return view;
		}
		
	}
	
	private class ViewHolder {
		ImageView imgMallProductTeaLv;
		TextView tvMallProductTeaLv;
	}

}
