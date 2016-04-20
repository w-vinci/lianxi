package com.example.lianxi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.example.lianxi.R;
import com.example.lianxi.Utils.PrefUtils;

public class SplashActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
		startAnim();
	}

	private void startAnim() {

		// 渐变动画
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(2000);// 动画时间
		alpha.setFillAfter(true);// 保持动画状态
		
		alpha.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				jumpNextPage();
			}

		});
		rlRoot.startAnimation(alpha);
		
	}

	private void jumpNextPage() {
		// 判断之前有没有显示过新手引导
				boolean userGuide = PrefUtils.getBoolean(this, "is_user_guide_showed",
						false);

				if (!userGuide) {
					// 跳转到新手引导页
					startActivity(new Intent(SplashActivity.this, GuideActivity.class));
				} else {
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
				}

				finish();
		
	}

}
