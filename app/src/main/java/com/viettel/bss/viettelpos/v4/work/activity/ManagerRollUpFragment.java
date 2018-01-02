package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;
import com.viettel.bss.viettelpos.v4.work.fragment.FragmentRollUp;
import com.viettel.bss.viettelpos.v4.work.fragment.FragmentRollUpHistory;

public class ManagerRollUpFragment extends GPSTrackerFragment implements
		OnClickListener {

	TabHost tHost;

	public static ManagerRollUpFragment instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_create_new_request1);
		addActionBarTitle();
		unitView();
		instance = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	// create unitView
	public void unitView() {
		tHost = (TabHost) findViewById(android.R.id.tabhost);
		tHost.setup();

		/**
		 * Defining Tab Change Listener event. This is invoked when tab is
		 * changed
		 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				FragmentRollUp fmRollUp = (FragmentRollUp) fm
						.findFragmentByTag(getString(R.string.roll_up));
				FragmentRollUpHistory fmHis = (FragmentRollUpHistory) fm
						.findFragmentByTag(getString(R.string.rolL_up_history));
				android.support.v4.app.FragmentTransaction ft = fm
						.beginTransaction();

				/** Detaches the infoFragmentInfoCustomer if exists */
				if (fmRollUp != null)
					ft.detach(fmRollUp);

				if (fmHis != null)
					ft.detach(fmHis);

				/** If current tab is android */
				if (tabId.equalsIgnoreCase(getString(R.string.roll_up))) {

					if (fmRollUp == null) {
						/**
						 * Create infoFragmentInfoCustomer and adding to
						 * fragmenttransaction
						 */
						ft.add(R.id.realtabcontent, new FragmentRollUp(),
								getString(R.string.roll_up));
					} else {
						/**
						 * Bring to the front, if already exists in the
						 * fragmenttransaction
						 */
						ft.attach(fmRollUp);
					}

				} else {
					if (fmHis == null) {
						/**
						 * Create infoFragmentInfoExamine and adding to
						 * fragmenttransaction
						 */
						ft.add(R.id.realtabcontent,
								new FragmentRollUpHistory(),
								getString(R.string.rolL_up_history));
					} else {
						/**
						 * Bring to the front, if already exists in the
						 * fragmenttransaction
						 */
						ft.attach(fmHis);
					}
				}
				ft.commit();
			}
		};

		/** Setting tabchangelistener for the tab */
		tHost.setOnTabChangedListener(tabChangeListener);
		setTab();

	}

	private void setTab() {
		TabHost.TabSpec tSpecInfoCus = tHost
				.newTabSpec(getString(R.string.roll_up));
		tSpecInfoCus.setIndicator(getString(R.string.roll_up), getResources()
				.getDrawable(R.drawable.sale_channel));
		tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));
		tHost.addTab(tSpecInfoCus);

		TabHost.TabSpec tSpecInfoExamine = tHost
				.newTabSpec(getString(R.string.rolL_up_history));
		tSpecInfoExamine.setIndicator(getString(R.string.rolL_up_history),
				getResources().getDrawable(R.drawable.sale_deposit));
		tSpecInfoExamine.setContent(new DummyTabContent(getBaseContext()));
		tHost.addTab(tSpecInfoExamine);
	}

	// add actionbar title
	private void addActionBarTitle() {

		ActionBar mActionBar = ManagerRollUpFragment.this.getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(ManagerRollUpFragment.this,
						Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getString(R.string.roll_up));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			onBackPressed();
			break;
		default:
			break;
		}

	}

}
