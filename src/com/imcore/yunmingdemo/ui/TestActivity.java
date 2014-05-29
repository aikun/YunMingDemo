package com.imcore.yunmingdemo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.imcore.myphonedemo.R;
import com.imcore.yunmingdemo.http.HttpHelper;
import com.imcore.yunmingdemo.http.HttpMethod;
import com.imcore.yunmingdemo.http.RequestEntity;
import com.imcore.yunmingdemo.http.ResponseJsonEntity;
import com.imcore.yunmingdemo.image.ImageFetcher;
import com.imcore.yunmingdemo.model.HomePicInfo;
import com.imcore.yunmingdemo.util.JsonUtil;
import com.imcore.yunmingdemo.util.TextUtil;
import com.imcore.yunmingdemo.util.ToastUtil;

public class TestActivity extends Activity {

	private ImageView img;
	private List<HomePicInfo> mHomePicInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		img = (ImageView) findViewById(R.id.img_view_1);
		mHomePicInfoList = new ArrayList<HomePicInfo>();
		new GetImageUrlAsync().execute();

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 33) {
				ImageFetcher imgFetcher = new ImageFetcher();
				imgFetcher.fetch(
						HttpHelper.DOMAIN_IMAGE_URL + "/"
								+ mHomePicInfoList.get(3).imageUrl, img);
				Log.i("homePic", HttpHelper.DOMAIN_IMAGE_URL + "/"
						+ mHomePicInfoList.get(0).imageUrl);
			}

		};
	};

	private class GetImageUrlAsync extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String url = "/topline/list.do";
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, null);
			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(entity);
				Log.i("homePic", jsonResponse);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(String result) {
			if (TextUtil.isEmptyString(result)) {
				ToastUtil.showToast(TestActivity.this, "服务器响应错误！");
				return;
			}
			ResponseJsonEntity resJsonEntity = ResponseJsonEntity
					.fromJSON(result);
			if (resJsonEntity.getStatus() == 200) {
				String jsonData = resJsonEntity.getData();
				Log.i("homePic", jsonData);
				if (jsonData != null) {
					mHomePicInfoList = JsonUtil.toObjectList(jsonData,
							HomePicInfo.class);
				}
			} else {
				ToastUtil.showToast(TestActivity.this,
						resJsonEntity.getMessage());
				Log.i("homePic", resJsonEntity.getMessage());
			}

			Message msg = Message.obtain();
			msg.what = 33;
			handler.sendMessage(msg);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
