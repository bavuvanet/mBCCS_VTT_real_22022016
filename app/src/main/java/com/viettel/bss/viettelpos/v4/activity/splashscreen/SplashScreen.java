package com.viettel.bss.viettelpos.v4.activity.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;

public class SplashScreen extends Activity implements AnimationListener {
	
	private ImageView imSplash;
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	private Animation mAnimation;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		imSplash = (ImageView) findViewById(R.id.im_splash);

		mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.bounce);

		mAnimation.setAnimationListener(this);
		imSplash.setAnimation(mAnimation);
		
		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// imSplash.setAnimation(null);
		// Intent i = new Intent(SplashScreen.this, LoginActivity.class);
		// startActivity(i);
		// finish();
		// }
		// }, SPLASH_TIME_OUT);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// if (animation == mAnimation) {
		imSplash.setAnimation(null);
		Intent i = new Intent(SplashScreen.this, LoginActivity.class);
		startActivity(i);
		finish();
		// }
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}
