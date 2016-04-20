package com.example.lianxi.pager;

import java.util.ArrayList;

import com.example.lianxi.activity.MainActivity;
import com.example.lianxi.domain.NewsData;
import com.example.lianxi.domain.NewsData.NewsMenuData;
import com.example.lianxi.fragment.LeftMenuFragment;
import com.example.lianxi.global.GlobalContants;
import com.example.lianxi.pager.menu.BaseMenuDetailPager;
import com.example.lianxi.pager.menu.InteractMenuDetailPager;
import com.example.lianxi.pager.menu.NewsMenuDetailPager;
import com.example.lianxi.pager.menu.PhotoMenuDetailPager;
import com.example.lianxi.pager.menu.TopicMenuDetailPager;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.widget.Toast;

public class NewsCenterPager extends BasePager {
	private NewsData mNewsData;
	private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合

	public NewsCenterPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		tvTitle.setText("新闻");
		setSlidingMenuEnable(true);// 打开侧边栏
		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = (String) responseInfo.result;
						parseData(result);
						// System.out.println(result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						error.printStackTrace();
					}
				});

	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);

		// 刷新测边栏的数据
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment(); // 获取侧边栏对象
		leftMenuFragment.setMenuData(mNewsData);

		// 准备4个菜单详情页
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,
				mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity));
		mPagers.add(new InteractMenuDetailPager(mActivity));

		setCurrentMenuDetailPager(0);// 设置菜单详情页-新闻为默认当前页
	}

	/**
	 * 设置当前菜单详情页
	 */
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
		flContent.removeAllViews();// 清除之前的布局
		flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局

		// 设置当前页的标题
		NewsMenuData menuData = mNewsData.data.get(position);
		tvTitle.setText(menuData.title);

		pager.initData();// 初始化当前页面的数据
	}
}
