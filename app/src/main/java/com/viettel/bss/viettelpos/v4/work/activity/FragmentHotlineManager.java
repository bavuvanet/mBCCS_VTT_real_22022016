package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentAsignJob;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentUpdateHotLine;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.adapter.GridMenuAdapter;

import java.util.ArrayList;

public class FragmentHotlineManager extends Fragment
		implements
			OnItemClickListener,
			OnClickListener {
	private GridView lvManager;
	private ArrayList<Manager> arrayListManager;
    private Button btnHome;

	private final String ID_UPDATE_HOTLINE = "ID_UPDATE_HOTLINE";
	private final String ID_HOTLINE_NEW = "ID_HOTLINE_NEW";
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentChannelManager");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentChannelManager");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentChannelManager");
		View mView = inflater.inflate(R.layout.job_manager_layout_main,
				container, false);
		unit(mView);
		addChanelManagerList();
		return mView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.job);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	private void unit(View v) {
		lvManager = (GridView) v.findViewById(R.id.lvJobManager);
		arrayListManager = new ArrayList<>();

	}

	private void addChanelManagerList() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		// dau noi dich vu hotline
		if (name.contains(";update_hotline;") || name.contains(";update_hotline_mbccs2;")) {
			arrayListManager.add(new Manager(R.drawable.ic_rotate,
					getResources().getString(R.string.updatehotline), 0,
					ID_UPDATE_HOTLINE));
		}
		if (name.contains(";cm.create_req_cd;")) {
			arrayListManager.add(new Manager(
					R.drawable.thu_thap_tt_thi_truong_03, getResources()
					.getString(R.string.tiepnhancdcn), 0,
					Constant.JobMenu.REQUEST_RECEIPT_CHANGE_TECHNOLOGY));
		}

		arrayListManager.add(new Manager(
				R.drawable.thu_thap_tt_thi_truong_03, getResources()
				.getString(R.string.giaoviechotline), 0,
				Constant.JobMenu.REQUEST_ASIGN_WORK_HOTLINE));
		if (name.contains(";cm.create_req_cd;") || name.contains(";cm.create_req_cd_mbccs2;")) {
			arrayListManager.add(new Manager(
					R.drawable.ic_headset, getResources()
					.getString(R.string.tiepnhancdcnnew), 0,
					ID_HOTLINE_NEW));
		}



        GridMenuAdapter mAdapterManager = new GridMenuAdapter(arrayListManager, getActivity());
		lvManager.setAdapter(mAdapterManager);
		lvManager.setOnItemClickListener(this);
	}

	public static boolean policy = false;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String menuName = arrayListManager.get(arg2).getKeyMenuName();

		if (menuName.equals(ID_UPDATE_HOTLINE)) {
			ReplaceFragment.replaceFragment(getActivity(),
					new FragmentUpdateHotLine(), true);
		}
		if(menuName.equals(Constant.JobMenu.REQUEST_ASIGN_WORK_HOTLINE)) {
			FragmentAsignJob fragment = new FragmentAsignJob();
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
		}
		
		if(menuName.equals(Constant.JobMenu.REQUEST_RECEIPT_CHANGE_TECHNOLOGY)) {

			FragmentRequestChangeHotLine fragment = new FragmentRequestChangeHotLine();

			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
		}
		if(menuName.equals(ID_HOTLINE_NEW)) {
			Bundle bundle = new Bundle();
			bundle.putString("ID_HOTLINE_NEW","ID_HOTLINE_NEW");
			FragmentRequestChangeHotLine fragment = new FragmentRequestChangeHotLine();
			fragment.setArguments(bundle);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
		}
		

	}
	@Override
	public void onClick(View arg0) {
	}

	// public interface OnBackListener {
	// void onKeyBackListener();
	// }
	// public

}
