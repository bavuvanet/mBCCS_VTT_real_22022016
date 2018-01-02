package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterDetailWork;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.ItemDetailWork;

import java.util.ArrayList;

public class FragmentDetailWorkManager extends Fragment
		implements
			OnClickListener {
	Button btnHome;
	TextView txtNameActionBar;
	private ListView listworkdetail;
	private ArrayList<ItemDetailWork> lisDetailWorks = new ArrayList<>();
	private AdapterDetailWork adapterDetailWork = null;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        MainActivity.getInstance().setTitleActionBar(R.string.listworkdetails);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.list_detail_manager, container,
				false);
		Unit(mView);
		return mView;
	}

	private void Unit(View view) {
		listworkdetail = (ListView) view.findViewById(R.id.listdetail);
		try {
			JobDal jobDal = new JobDal(getActivity());
			jobDal.open();
			lisDetailWorks = jobDal.getListItemDetail();
			adapterDetailWork = new AdapterDetailWork(lisDetailWorks,
					getActivity());
			listworkdetail.setAdapter(adapterDetailWork);
			jobDal.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.relaBackHome :
				getActivity().onBackPressed();
				break;
			case R.id.btnHome:
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
				break;
			default :
				break;
		}
	}

}
