package com.viettel.bss.viettelpos.v4.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;

public abstract class FragmentCommonV4 extends Fragment
		implements
			OnItemClickListener,
			OnClickListener,
			Define {
	protected Button btnHome;
	protected Bundle mBundle;
	protected TextView txtNameActionBar;
	protected TextView txtAddressActionBar;
	protected Activity act;
	protected String permission = "";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		act = getActivity();
//		ActionBar mActionBar = getActivity().getActionBar();
//		mActionBar.setDisplayHomeAsUpEnabled(false);
//		mActionBar.setDisplayShowHomeEnabled(false);
//		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
//		btnHome = (Button) mActionBar.getCustomView()
//				.findViewById(R.id.btnHome);
////		btnHome.setVisibility(View.GONE);
//		btnHome.setOnClickListener(this);
//		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
//				.findViewById(R.id.relaBackHome);
//		relaBackHome.setOnClickListener(this);
//		txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(
//				R.id.txtNameActionbar);
//		// txtNameActionBar.setText(getResources().getString(R.string.chargeable));
//		txtAddressActionBar = (TextView) mActionBar.getCustomView()
//				.findViewById(R.id.txtAddressActionbar);
//		txtAddressActionBar.setVisibility(View.GONE);

	}

	protected int idLayout = R.layout.layout_charge_new;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		act = getActivity();
		if (mView == null) {
			mView = inflater.inflate(idLayout, container, false);
			unit(mView);
		} else {
			
		}

		// addManagerList();
		return mView;

	}
	public View mView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}



	public abstract void unit(View v);

	public boolean actionbarClicked = false;
	@Override
	public void onClick(View arg0) {
		actionbarClicked = false;
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.btnHome :
				actionbarClicked = true;
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
				break;

			case R.id.relaBackHome :
				actionbarClicked = true;
				if (getActivity() == null) {
					onClickListenerBack.onClick(arg0);
					return;
				} else {

				}

				getActivity().onBackPressed();
				CacheDataCharge.getInstance().setLisArrayListRe(null);
				CacheDataCharge.getInstance().setLisArrayList(null);
				break;
			default :
				actionbarClicked = false;
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	public void goHome() {
		FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
		ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome, true);
	}

	public void toast(String str) {
		Toast.makeText(getActivity(), str, 1).show();
	}

	public OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
	};

	public OnClickListener onClickListenerBack = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (act != null) {
				act.onBackPressed();
			} else {
				LoginActivity.getInstance().onBackPressed();
				// MainActivity.getInstance().onBackPressed();
			}

		}
	};
}
