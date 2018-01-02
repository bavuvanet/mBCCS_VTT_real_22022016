package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentManagePolicy;

import java.util.ArrayList;

//import com.viettel.bss.viettelpos.v4.work.activity.FragmentManagePolicy;

public class FragmentChanelFunction extends Fragment implements
		OnItemClickListener, OnClickListener {
	private GridView lvManager;
	private ArrayList<Manager> arrayListManager;
	private Button btnHome;
	private Bundle mBundle;
	Button btnshowpopup;
	ActionBar mActionBar;
	GridMenuAdapter mAdapterManager;
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {

//		btnHome.setVisibility(View.GONE);
		super.onActivityCreated(savedInstanceState);

		mBundle = getArguments();
		if (mBundle != null) {

			Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);

			if (mStaff != null) {
//				Log.e("mStaff.getAddressStaff()", "mStaff.getAddressStaff() = "
				// "mStaff.getAddressStaff() = "
//						+ mStaff.getAddressStaff()
//						+ "mStaff.getAddressStaff().lengt = "
//						+ mStaff.getAddressStaff().length());
				String address = "";
				if (mStaff.getAddressStaff() != null
						&& mStaff.getAddressStaff().trim().length() > 0) {
					address = mStaff.getAddressStaff();
				} else {
					address = getResources().getString(R.string.not_address);
				}
				Log.e("dia chi", "dc = " + address);
				MainActivity.getInstance().setTitleActionBar(mStaff.getNameStaff());
				MainActivity.getInstance().setSubTitleActionBar(address);
			}
		}

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mView = inflater.inflate(R.layout.chanel_menu_function, container,
				false);
		unit(mView);
		/*
		 * btnshowpopup.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { Log.d("btnshowpopup",
		 * "btnshowpopup"); Intent intent = new
		 * Intent(getActivity(),DiaLogChooseChanel.class);
		 * startActivity(intent); } });
		 */

		return mView;

	}

	private void unit(View v) {
		// btnshowpopup = (Button) v.findViewById(R.id.btnshowpopup);
		lvManager = (GridView) v.findViewById(R.id.lv_chanel_menu_function);
		arrayListManager = new ArrayList<>();

	}

	private void addManagerList() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		// Thong tin kenh
		if (name.contains(Constant.ChannelMenu.CHANNEL_INFO) || name.contains(Constant.ChannelMenu.CHANNEL_INFO_MBCCS2)) {
			arrayListManager.add(new Manager(R.drawable.channel_info,
					getResources().getString(R.string.info_chanel), 0,
					Constant.ChannelMenu.CHANNEL_INFO));
		}
		// Cap nhat toa do
		if (name.contains(Constant.ChannelMenu.CHANNEL_LOCATION) || name.contains(Constant.ChannelMenu.CHANNEL_LOCATION_MBCCS2)) {

			arrayListManager.add(new Manager(R.drawable.update_location,
					getResources().getString(R.string.update_location), 0,
					Constant.ChannelMenu.CHANNEL_LOCATION));

		}
		// ban dut
		if (name.contains(Constant.ChannelMenu.SALE_SALING) || name.contains(Constant.VSAMenu.SALE_SALING_MBCCS2)) {
			arrayListManager.add(new Manager(R.drawable.sale_retail,
					getResources().getString(R.string.sale_saling), 0,
					Constant.ChannelMenu.SALE_SALING));
		}
		// ban dat coc
		if (name.contains(Constant.ChannelMenu.SALE_DEPOSIT) || name.contains(Constant.VSAMenu.SALE_DEPOSIT_MBCCS2)) {

			arrayListManager.add(new Manager(R.drawable.sale_deposit,
					getResources().getString(R.string.sale_deposit), 0,
					Constant.ChannelMenu.SALE_DEPOSIT));
		}
		// Truyen thong chinh sach
		if (name.contains(Constant.ChannelMenu.WORK_COMMUNICATION) || name.contains(Constant.ChannelMenu.WORK_COMMUNICATION_MBCCS2)) {

			arrayListManager.add(new Manager(R.drawable.ic_cv_truyenthong_chinhsach,
					getResources().getString(R.string.job_manager_5), 0,
					Constant.ChannelMenu.WORK_COMMUNICATION));
		}
		mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		arrayListManager = new ArrayList<>();
		if(mAdapterManager != null){
			mAdapterManager.notifyDataSetChanged();
		}
		addManagerList();
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			// getTargetFragment().onActivityResult(getTargetRequestCode(),
			// Activity.RESULT_OK, null);

			getActivity().onBackPressed();
			break;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		String menuName = arrayListManager.get(arg2).getKeyMenuName();
        switch (menuName) {
            case Constant.ChannelMenu.CHANNEL_INFO:

                // Thong tin kenh
                FragmentChanelInfo mChanelInfo = new FragmentChanelInfo();
                mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
                mBundle.putSerializable(Define.KEY_STAFF, mStaff);
                mChanelInfo.setArguments(mBundle);

                ReplaceFragment.replaceFragment(getActivity(), mChanelInfo, false);
                break;
            case Constant.ChannelMenu.CHANNEL_LOCATION:

                com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
                        .findMyLocationGPS(MainActivity.getInstance(),
                                "updateLocation");
                if ("0".equals(myLocation.getX()) || "0".equals(myLocation.getY())) {
                    CommonActivity.DoNotLocation(getActivity());
                    return;
                }
                // Cap nhat toa do
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    android.support.v4.app.DialogFragment newFragment = new DialogUpdateLocation();
                    mBundle.putSerializable(Define.KEY_STAFF, mStaff);
                    newFragment.setArguments(mBundle);
                    newFragment.show(getFragmentManager(), "dialog");
                } else {
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), title);
                    dialog.show();
                }

                break;
            case Constant.ChannelMenu.SALE_SALING: {
                // Ban hang cho kenh
                Bundle bundle = new Bundle();

                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALING);
                bundle.putLong("staffId", mStaff.getStaffId());
                bundle.putString("staffCode", mStaff.getStaffCode());
                bundle.putString("staffName", mStaff.getNameStaff());
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case Constant.ChannelMenu.SALE_DEPOSIT: {
                // Ban dat coc
                Bundle bundle = new Bundle();
                bundle.putInt("functionType",
                        FragmentSaleSaling.FUNCTION_SALE_DEPOSIT);
                bundle.putLong("staffId", mStaff.getStaffId());
                bundle.putString("staffCode", mStaff.getStaffCode());
                bundle.putString("staffName", mStaff.getNameStaff());
                FragmentSaleSaling fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(), fragment, false);

                break;
            }
            case Constant.ChannelMenu.WORK_COMMUNICATION:
                // Ban dat coc
                mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
                FragmentManagePolicy frag = new FragmentManagePolicy();
                mBundle.putSerializable(Define.KEY_STAFF, mStaff);
                frag.setArguments(mBundle);
                ReplaceFragment.replaceFragment(getActivity(), frag, true);
                break;
        }
	}

	public void setTargetFragment(
			com.viettel.maps.layers.MapLayer.OnClickListener onClickListener, int i) {
    }
}
