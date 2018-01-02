package com.viettel.bss.viettelpos.v4.customer.manage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

public class RegisterUploadImageFragment extends Fragment implements
		OnClickListener {
	private View mView;
	private Button btnHome;
	private EditText mEdtTypePaper;
	private EditText mEdtNumberPaper;
	private EditText mEdtNumberSubscribers;

    @Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        Activity mActivity = getActivity();
		if (mView == null) {
			mView = inflater.inflate(R.layout.register_upload_image_fragment,
					container, false);
			mEdtTypePaper = (EditText) mView.findViewById(R.id.edtTypePaper);
			mEdtNumberPaper = (EditText) mView
					.findViewById(R.id.edtNumberPaper);
			mEdtNumberSubscribers = (EditText) mView
					.findViewById(R.id.edtNumberSubscribers);
            Button mBtnCheck = (Button) mView.findViewById(R.id.btnKiemtra);
			mBtnCheck.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					checkInfo();
				}
			});
		}
		return mView;
	}

	private void checkInfo() {
		String typePaper = mEdtTypePaper.getText().toString();
		String numberPaper = mEdtNumberPaper.getText().toString();
		String numberSubscribers = mEdtNumberSubscribers.getText().toString();

		// InfoCustomerFragment fragmentMenuHome = new InfoCustomerFragment();
		// ReplaceFragment.replaceFragment(mActivity, fragmentMenuHome, true);
		showDiaLogDKQuaHan();
	}

	private void showDiaLogDKQuaHan() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		@SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_dk_qua_han, null);

		builder.setView(dialogView);
		final Dialog dialog = builder.create();

		Button btnDongY = (Button) dialogView.findViewById(R.id.btnDongY);
		btnDongY.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        MainActivity.getInstance().setTitleActionBar(R.string.customer2);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnHome:
//			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
//					true);
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;

		default:
			break;
		}

	}

}
