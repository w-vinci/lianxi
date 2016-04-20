package com.example.lianxi.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.lianxi.R;
import com.example.lianxi.pager.BasePager;
import com.example.lianxi.pager.GovAffairsPager;
import com.example.lianxi.pager.HomePager;
import com.example.lianxi.pager.NewsCenterPager;
import com.example.lianxi.pager.SettingPager;
import com.example.lianxi.pager.SmartServicePager;

public class ContentFragment extends BaseFragment {

	private RadioGroup rgGroup;
	private ArrayList<BasePager> mPagerList;
	private ViewPager mViewPager;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_content);
		return view;
	}

	@Override
	public void initData() {
		rgGroup.check(R.id.rb_home);// 默认勾选首页

		// 初始化5个子页面
		mPagerList = new ArrayList<BasePager>();
		// for (int i = 0; i < 5; i++) {
		// BasePager pager = new BasePager(mActivity);
		// mPagerList.add(pager);
		// }

		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new GovAffairsPager(mActivity));
		mPagerList.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());

		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					// mViewPager.setCurrentItem(0);// 设置当前页面
					mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1, false);// 设置当前页面
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2, false);// 设置当前页面
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3, false);// 设置当前页面
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4, false);// 设置当前页面
					break;

				default:
					break;
				}
			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mPagerList.get(arg0).initData();// 获取当前被选中的页面, 初始化该页面数据
				switch (arg0) {
				case 0:
					rgGroup.check(R.id.rb_home);
					break;
				case 1:
					rgGroup.check(R.id.rb_news);
					break;
				case 2:
					rgGroup.check(R.id.rb_smart);
					break;
				case 3:
					rgGroup.check(R.id.rb_gov);
					break;
				case 4:
					rgGroup.check(R.id.rb_setting);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		mPagerList.get(0).initData();// 初始化首页数据
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(mPagerList.get(position).mRootView);
			return mPagerList.get(position).mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
	/**
	 * 获取新闻中心页面
	 * 
	 * @return
	 */
	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) mPagerList.get(1);
	}

}
