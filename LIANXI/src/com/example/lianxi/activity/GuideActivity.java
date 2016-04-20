package com.example.lianxi.activity;

import com.example.lianxi.R;
import com.example.lianxi.Utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class GuideActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
	}
	public void start(View v){
		PrefUtils.setBoolean(GuideActivity.this,
				"is_user_guide_showed", true);
		startActivity(new Intent(GuideActivity.this, MainActivity.class));
		finish();
	}
}
