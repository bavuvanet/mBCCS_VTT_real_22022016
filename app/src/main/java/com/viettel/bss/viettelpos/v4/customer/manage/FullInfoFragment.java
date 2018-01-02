package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

public class FullInfoFragment extends Fragment implements
		OnClickListener {
	private View mView;
    private LinearLayout mLnComplateInfo;
	private Button btnHome;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.manager_customer_fragment,
					container, false);
            LinearLayout mLnRegisterInfo = (LinearLayout) mView
                    .findViewById(R.id.lnRegisterInfo);
//			mLnComplateInfo = (LinearLayout) mView
//					.findViewById(R.id.lnComplateInfo);
			mLnRegisterInfo.setOnClickListener(this);
			mLnComplateInfo.setOnClickListener(this);
		}
		return mView;
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
		case R.id.lnRegisterInfo:
			break;

		default:
			break;
		}

	}

}
