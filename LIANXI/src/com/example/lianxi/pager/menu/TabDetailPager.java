package com.example.lianxi.pager.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lianxi.R;
import com.example.lianxi.R.drawable;
import com.example.lianxi.domain.NewsData.NewsTabData;
import com.example.lianxi.domain.TabData;
import com.example.lianxi.domain.TabData.TabNewsData;
import com.example.lianxi.domain.TabData.TopNewsData;
import com.example.lianxi.global.GlobalContants;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;

public class TabDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	private NewsTabData mTabData;
	private TextView tvText;
	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合
	private View view;
	private String mUrl;
	private ViewPager mViewPager;
	private TabData mTabDetailData;
	private TextView tvTitle;
	private CirclePageIndicator mIndicator;
	private ListView lvList; // 新闻列表
	private ArrayList<TabNewsData> mNewsList;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mTabData = newsTabData;
		mUrl = GlobalContants.SERVER_URL + mTabData.url;
	}

	@Override
	public View initViews() {
		view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_news);
		mViewPager.setOnPageChangeListener(this);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.cp_indicator);
		lvList = (ListView) view.findViewById(R.id.lv_list);
		return view;
	}

	@Override
	public void initData() {
		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
			}
		});

	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		mTopNewsList = mTabDetailData.data.topnews;

		mNewsList = mTabDetailData.data.news;
		if (mTopNewsList != null) {
			mViewPager.setAdapter(new TopNewsAdapter());
			tvTitle.setText(mTopNewsList.get(0).title);
			mIndicator.setViewPager(mViewPager);
			mIndicator.setSnap(true);
			mIndicator.onPageSelected(0);
		}

		if (mNewsList != null) {
			lvList.setAdapter(new mNewsAdapter());
		}

	}

	/**
	 * 头条新闻适配器
	 * 
	 * @author Kevin
	 * 
	 */
	class TopNewsAdapter extends PagerAdapter {

		private BitmapUtils utils;

		public TopNewsAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);// 设置默认图片
		}

		@Override
		public int getCount() {
			return mTopNewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			image.setScaleType(ScaleType.FIT_XY);// 基于控件大小填充图片

			TopNewsData topNewsData = mTopNewsList.get(position);
			utils.display(image, topNewsData.topimage);// 传递imagView对象和图片地址
			// image.setImageResource(drawable.topnews_item_default);
			container.addView(image);

			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class mNewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mNewsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		TopNewsData topNewsData = mTopNewsList.get(arg0);
		tvTitle.setText(topNewsData.title);
	}

}
