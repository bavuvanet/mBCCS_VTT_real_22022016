package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;

public class CopyOfFragmentInfrastructureManager extends Fragment implements
		OnClickListener {
	private View mView;
    private Button btnHome;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.infrastructure_manager_layout,
					container, false);
            LinearLayout linInfrastructure_1 = (LinearLayout) mView
                    .findViewById(R.id.linInfrastructure_1);
            LinearLayout linInfrastructure_2 = (LinearLayout) mView
                    .findViewById(R.id.linInfrastructure_2);
            LinearLayout linInfrastructure_3 = (LinearLayout) mView
                    .findViewById(R.id.linInfrastructure_3);
			linInfrastructure_1.setOnClickListener(this);
			linInfrastructure_2.setOnClickListener(this);
			linInfrastructure_3.setOnClickListener(this);
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
		case R.id.linInfrastructure_1:
			FragmentInfoIDNumberManager idNumberManager = new FragmentInfoIDNumberManager();
			ReplaceFragment.replaceFragment(getActivity(), idNumberManager,
					false);
			break;
		case R.id.linInfrastructure_2:
			FragmentInfrastructureOnline mInfrastructureOnline = new FragmentInfrastructureOnline();
			ReplaceFragment.replaceFragment(getActivity(),
					mInfrastructureOnline, false);
			break;
		case R.id.linInfrastructure_3:
			FragmentShowDataInfrastructure mDataInfrastructure = new FragmentShowDataInfrastructure();
			ReplaceFragment.replaceFragment(getActivity(), mDataInfrastructure,
					false);
			break;

		default:
			break;
		}

	}

}
