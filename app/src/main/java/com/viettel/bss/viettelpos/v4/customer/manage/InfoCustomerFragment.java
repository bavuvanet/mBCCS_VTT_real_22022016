package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.RegisterAdapter;

public class InfoCustomerFragment extends Fragment implements OnClickListener {
	private View mView;
	private Button btnHome;
	private ListView mLvInfoCustomer;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.info_customer_fragment,
					container, false);
			mLvInfoCustomer = (ListView) mView
					.findViewById(R.id.lvInfoCustomer);

			addObj();
		}
		return mView;
	}

	private void addObj() {
		// ArrayList<InfoCustomerObj> arrayList = new
		// ArrayList<InfoCustomerObj>();
		//
		// String name[] = { "Thúy Kiều", "Thúy Vân", "Thúy Nga" };
		// String ngaySinhKH[] = { "1-1-1001", "1-1-1001", "1-1-1001" };
		// for (int i = 0; i < ngaySinhKH.length; i++) {
		// String nameKH = name[i];
		// String ngaySinh = ngaySinhKH[i];
		// InfoCustomerObj customerObj = new InfoCustomerObj(nameKH, ngaySinh,
		// false);
		// arrayList.add(customerObj);
		// }
		RegisterAdapter registerAdapter = new RegisterAdapter(getActivity(),
				RegisterInfoFragment.arrayListCustomer);
		mLvInfoCustomer.setAdapter(registerAdapter);
		mLvInfoCustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), "asasfd", Toast.LENGTH_LONG)
						.show();
				
			}
		});

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
