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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.customview.adapter.SpnLoaiThueBaoAdapter;

public class SearchInfoFragment extends Fragment implements OnClickListener {
	private View mView;
	private Button btnHome;
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
			mView = inflater.inflate(R.layout.register_search_info_fragment,
					container, false);
			mEdtNumberSubscribers = (EditText) mView
					.findViewById(R.id.edtNumberSubscribers);
            Spinner mSpLoaiThueBao = (Spinner) mView.findViewById(R.id.spLoaiThueBao);

			// set spiner loai thue bao
			String mLoaiThueBao[] = {
					mActivity.getResources().getString(R.string.tv_tra_truoc),
					mActivity.getResources().getString(R.string.tv_tra_sau) };
			SpnLoaiThueBaoAdapter thueBaoAdapter = new SpnLoaiThueBaoAdapter(
					mLoaiThueBao, mActivity);
			mSpLoaiThueBao.setAdapter(thueBaoAdapter);
			mSpLoaiThueBao.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});

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
		String numberSubscribers = mEdtNumberSubscribers.getText().toString();

//		RegisterNewFragment registerNewFragment = new RegisterNewFragment();
//		ReplaceFragment.replaceFragment(mActivity, registerNewFragment, true);
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
				// TODO Auto-generated method stub
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
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
//			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
//					true);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;

		default:
			break;
		}

	}

}
