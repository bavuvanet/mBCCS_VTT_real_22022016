package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.work.object.OjUri;

public class FragmentViewVideo extends FragmentCommon {
	private VideoView myVideoView;
	private final int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;
	private boolean finished = false;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getStaff();
		Log.e(tag, mStaff.getUrl());
        setTitleActionBar(mStaff.getName());
		// txtAddressActionBar.setVisibility(View.VISIBLE);
		// txtAddressActionBar.setText(mStaff.getAddressStaff());

	}

	public void onDestroy() {
		super.onDestroy();
		Log.e(tag, "on destroy");
		// if(asy != null){
		//
		// }
		if (!loaded) {
			myVideoView = null;
		}
	}
	public boolean getFinished() {
		return finished;
	}
	private OjUri mStaff;
	private final String tag = "fragment view video";
	private void getStaff() {
		if (mStaff != null) {
			return;
		}
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			// String timeCreateIdNo, birthday, idno;
			mStaff = (OjUri) mBundle.getSerializable(Define.KEY_OJ_URI);
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getStaff();
		idLayout = R.layout.layout_policy_view_video;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	private boolean error = false;
	public void unit(View v) {
		// TODO Auto-generated method stub
		if (error) {
			act.onBackPressed();
			return;
		}
		if (mediaControls == null) {
			mediaControls = new MediaController(act);
		}

		// Find your VideoView in your video_main.xml layout
		myVideoView = (VideoView) v.findViewById(R.id.video_view);

		// Create a progressbar
		progressDialog = new ProgressDialog(act);
		// Set progressbar title
		// progressDialog.setTitle("JavaCodeGeeks Android Video View Example");
		// Set progressbar message
		progressDialog.setMessage(act.getString(R.string.loading));
		// progressDialog.setCancelable(false);
		// Show progressbar
		// progressDialog.seton
		progressDialog.show();

		try {
			myVideoView.setMediaController(mediaControls);
			mediaControls.setAnchorView(myVideoView);
			String url = "http://www.ebookfrenzy.com/android_book/movie.mp4";
			url = "http://ia902302.us.archive.org/27/items/Pbtestfilemp4videotestmp4/video_test_512kb.mp4";
			url = mStaff.getUrl();
			Log.d("urllllllllll", url);
			if(url != null || !url.isEmpty()){
				myVideoView.requestFocus();
				myVideoView.setVideoURI(Uri.parse(url));
				myVideoView.start();

			}
		
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		// play finished
		myVideoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer vmp) {
						Log.e(tag, "finish");
						if (!error) {
							finished = true;
							act.onBackPressed();
						}

					}
				});
		// Log.e(tag, mediaControls.get + "...");

		OnErrorListener onErrorListener = new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.e(tag, "error");
				error = true;
				if (dialog == null) {
					dialog = CommonActivity.createAlertDialog(act,
							R.string.cannot_display_video, R.string.app_name,
							onClickListenerBackPressed);
				}
				if (!dialog.isShowing()) {
					dialog.show();
				}
				return error;
			}
		};

		myVideoView.setOnErrorListener(onErrorListener);
		myVideoView.requestFocus();
		myVideoView.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
				progressDialog.dismiss();
				loaded = true;
				myVideoView.seekTo(position);
				if (position == 0) {
					myVideoView.start();
				} else {
					myVideoView.pause();
				}
			}
		});
	}

	private Dialog dialog;
	private boolean loaded = false;
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
		// mediaControls.seton
	}

	// @Override
	// public void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// position = savedInstanceState.getInt("Position");
	// myVideoView.seekTo(position);
	// }
	// move login

	private final OnClickListener onClickListenerBackPressed = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (myVideoView != null) {
				myVideoView = null;
			}
			if (progressDialog != null) {
				progressDialog.dismiss();
			}

			act.onBackPressed();

		}
	};
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

}
