package com.imcore.yunmingdemo.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.MyApplication;
import com.imcore.yunmingdemo.http.HttpHelper;
import com.imcore.yunmingdemo.http.HttpMethod;
import com.imcore.yunmingdemo.http.RequestEntity;
import com.imcore.yunmingdemo.http.ResponseJsonEntity;
import com.imcore.yunmingdemo.image.ImageFetcher;
import com.imcore.yunmingdemo.model.User;
import com.imcore.yunmingdemo.util.JsonUtil;
import com.imcore.yunmingdemo.util.TextUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

public class MineFragment extends Fragment implements OnClickListener {
	
	private static final String USER_INFO = "user_info";
	private static final String USER_ID = "user_id";
	private static final String TOKEN = "token";

	private ImageView imgPic;
	private TextView tvName, tvLever, tvCredit;
	private ImageButton btnEdit;
	private RelativeLayout rlShopRecord, rlShopCar, rlFavorite, rlPushInfo,
			rlChange;
	private View view;
	private ProgressDialog pgDialog;
	private ImageFetcher imgFetcher;
	private MyApplication mApplication = new MyApplication();
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, null);
		
		initial();
		
		new getMineInfoAsync(getActivity()).execute();

		return view;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 20) {
				imgFetcher.fetch(HttpHelper.DOMAIN_IMAGE_URL + "/" + user.avatarUrl, imgPic);
				tvName.setText(user.name);
				tvCredit.setText("等级 ： " + user.grade);
				tvLever.setText("积分： " + user.totalPoint);
			}
		};
	};

	// 初始化控件
	private void initial() {
		
		imgFetcher = new ImageFetcher();
		
		imgPic = (ImageView) view.findViewById(R.id.img_mine_pic);
		tvName = (TextView) view.findViewById(R.id.tv_mine_name);
		tvCredit = (TextView) view.findViewById(R.id.tv_mine_credit);
		tvLever = (TextView) view.findViewById(R.id.tv_mine_lever);
		btnEdit = (ImageButton) view.findViewById(R.id.btn_mine_deit);

		rlChange = (RelativeLayout) view.findViewById(R.id.rl_mine_change);
		rlShopRecord = (RelativeLayout) view
				.findViewById(R.id.rl_mine_shopping_record);
		rlShopCar = (RelativeLayout) view
				.findViewById(R.id.rl_mine_shopping_car);
		rlFavorite = (RelativeLayout) view.findViewById(R.id.rl_mine_favorite);
		rlPushInfo = (RelativeLayout) view
				.findViewById(R.id.rl_mine_push_notification);

		btnEdit.setOnClickListener(this);
		rlChange.setOnClickListener(this);
		rlShopRecord.setOnClickListener(this);
		rlShopCar.setOnClickListener(this);
		rlFavorite.setOnClickListener(this);
		rlPushInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_mine_deit:
			//
			break;
		case R.id.rl_mine_change:
			//
			break;
		case R.id.rl_mine_favorite:
			//
			break;
		case R.id.rl_mine_push_notification:
			//
			break;
		case R.id.rl_mine_shopping_car:
			//
			break;
		case R.id.rl_mine_shopping_record:
			//
			break;
		}

	}

	private class getMineInfoAsync extends AsyncTask<Void, Void, String> {

		private Context mContext;
		
		@Override
		protected void onPreExecute() {
			pgDialog = ProgressDialog.show(getActivity(), "请稍候……", "正在加载个人信息……");
			super.onPreExecute();
		}
		
		public getMineInfoAsync(Context context) {
			mContext = context;
		}
		
		@Override
		protected String doInBackground(Void... params) {

			String url = " /user/get.do";
			
			SharedPreferences sp = mContext.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

			// 把请求参数装到map中
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("userId", sp.getInt(USER_ID, 0));
			args.put("token", sp.getString(TOKEN, ""));

			// 构造RequestEntity
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
			
			pgDialog.dismiss();
			pgDialog = null;
			
			if (TextUtil.isEmptyString(result)) {
				ToastUtil.showToast(getActivity(), "服务器响应错误！");
				return;
			}

			// 响应回来之后，构造ResponseEntity
			ResponseJsonEntity resJsonEntity = ResponseJsonEntity
					.fromJSON(result);
			if(resJsonEntity.getStatus() == 200) {
				String jsonData = resJsonEntity.getData();
				Log.i("userInfo", jsonData);
				mApplication.user = JsonUtil.toObject(jsonData, User.class);
				user = mApplication.user;
						
				Message msg = Message.obtain();
				msg.what = 20;
				handler.sendMessage(msg);
				
			} else {
				ToastUtil.showToast(getActivity(),
						resJsonEntity.getMessage());
				Log.i("userInfo", resJsonEntity.getMessage());
			}
			
			

			super.onPostExecute(result);
		}

	}
}
