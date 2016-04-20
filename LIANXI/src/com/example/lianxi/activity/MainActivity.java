package com.example.lianxi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.example.lianxi.R;
import com.example.lianxi.fragment.ContentFragment;
import com.example.lianxi.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	private long exitTime = 0;
	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	private static final String FRAGMENT_CONTENT = "fragment_content";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initVIew();
		initFragment();
	}

	private void initVIew() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		setBehindContentView(R.layout.left_menu);// 设置侧边栏
		SlidingMenu slidingMenu = getSlidingMenu();// 获取侧边栏对象
//		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置全屏触摸
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setBehindOffset(200);// 设置预留屏幕的宽度

	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();// 开启事务

		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),FRAGMENT_LEFT_MENU);// 用fragment替换framelayout
		transaction.replace(R.id.fl_content, new ContentFragment(),FRAGMENT_CONTENT);

		transaction.commit();// 提交事务

	}
	
	// 获取侧边栏fragment
		public LeftMenuFragment getLeftMenuFragment() {
			FragmentManager fm = getSupportFragmentManager();
			LeftMenuFragment fragment = (LeftMenuFragment) fm
					.findFragmentByTag(FRAGMENT_LEFT_MENU);

			return fragment;
		}

		// 获取主页面fragment
		public ContentFragment getContentFragment() {
			FragmentManager fm = getSupportFragmentManager();
			ContentFragment fragment = (ContentFragment) fm
					.findFragmentByTag(FRAGMENT_CONTENT);

			return fragment;
		}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(MainActivity.this, "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				MainActivity.this.finish();
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			// 其他
		} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			// 其他
		} else {
			// 其他
		}
		return flag;
	}

}
