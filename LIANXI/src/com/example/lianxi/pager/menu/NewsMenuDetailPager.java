package com.example.lianxi.pager.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lianxi.R;
import com.example.lianxi.activity.MainActivity;
import com.example.lianxi.domain.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {
	private ViewPager mViewPager;
	private ArrayList<TabDetailPager> mPagerList;
	private ArrayList<NewsTabData> mNewsTabData;
	private TabPageIndicator mIndicator;
	private ImageButton btnNext;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsTabData> children) {
		super(activity);

		mNewsTabData = children;
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		ViewUtils.inject(this, view);
		// btnNext = (ImageButton) view.findViewById(R.id.btn_next);
		mIndicator.setOnPageChangeListener(this);
		return view; // wufa
	}

	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		// 初始化页签数据
		for (int i = 0; i < mNewsTabData.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,
					mNewsTabData.get(i));
			mPagerList.add(pager);
		}

		mViewPager.setAdapter(new MenuDetailAdapter());
		mIndicator.setViewPager(mViewPager); // 设置完adapter后才能调用
		// btnNext.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// int currentItem = mViewPager.getCurrentItem();
		// mViewPager.setCurrentItem(++currentItem);
		//
		// }
		// });
	}

	// 跳转下一个页面
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	class MenuDetailAdapter extends PagerAdapter {
		/**
		 * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
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
		// TODO Auto-generated method stub
		MainActivity mainui = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainui.getSlidingMenu();
		if (arg0 == 0) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
}
