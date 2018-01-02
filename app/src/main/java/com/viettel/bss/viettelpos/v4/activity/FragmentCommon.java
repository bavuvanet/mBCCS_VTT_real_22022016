package com.viettel.bss.viettelpos.v4.activity;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterNewFragment;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;

import butterknife.ButterKnife;

public abstract class FragmentCommon extends Fragment implements
		OnItemClickListener, OnClickListener, Define {

	protected Bundle mBundle;
	protected boolean skipBackStack = false;
	protected Activity act;
	protected String permission = "";

	public FragmentCommon() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		act = getActivity();
	}

	protected int idLayout = R.layout.layout_charge_new;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		act = getActivity();
		if (mView == null) {
			mView = inflater.inflate(idLayout, container, false);
			ButterKnife.bind(this, mView);
			unit(mView);
		} else {
			// do nothing
		}
		setPermission();
		// addManagerList();
		return mView;
	}

	protected View mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	protected abstract void unit(View v);

	protected abstract void setPermission();

	protected boolean actionbarClicked = false;

	@Override
	public void onClick(View arg0) {
		actionbarClicked = false;
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			default:
				actionbarClicked = false;
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	public void goHome() {
		FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
		ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome, true);
	}

	protected void toast(String str) {
		Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
	}

	protected final OnClickListener moveLogInAct = new OnClickListener() {
		@Override
		public void onClick(View v) {
			LoginDialog dialog = new LoginDialog(getActivity(), permission);
			dialog.show();
		}
	};

	protected final OnClickListener onClickListenerBack = new OnClickListener() {

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

	protected void setTitleActionBar(String title) {
		MainActivity.getInstance().setTitleActionBar(title);
	}

	protected void setTitleActionBar(int title) {
		MainActivity.getInstance().setTitleActionBar(title);
	}

	protected void setSubTitleActionBar(String subTitleActionBar) {
		MainActivity.getInstance().setSubTitleActionBar(subTitleActionBar);
	}

	protected void setSubTitleActionBar(int subTitleActionBar) {
		MainActivity.getInstance().setSubTitleActionBar(subTitleActionBar);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public void removeFragment() {
		try {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack();
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (skipBackStack) {
			removeFragment();
		}
	}

	public void setSkipBackStack(boolean skipBackStack) {
		this.skipBackStack = skipBackStack;
	}
}
