package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterCriterial;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;

import java.util.ArrayList;
public class FragmentCriteria extends Fragment
	implements OnItemClickListener, OnClickListener {


    private Button btnHome;
    private Dialog dialogMonth= null;
    private ArrayList<ObjectDetailGroup> lstObjs = new ArrayList<>();
@Override
public void onActivityCreated(Bundle savedInstanceState) {
	Log.d("TAG", "onActivityCreated FragmentCriteria");
    MainActivity.getInstance().setTitleActionBar(R.string.job_manager_4);
	super.onActivityCreated(savedInstanceState);
}

@Override
public void onAttach(Activity activity) {
	Log.d("TAG", "onAttach FragmentCriteria");
	super.onAttach(activity);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
//	Bundle mBundle = getArguments();
//	aObj =(AreaObj) mBundle.getSerializable(Define.KEY_AREA);
//	Log.d("TAG", "onCreateView FragmentCriteria");
//	
	View mView = inflater.inflate(R.layout.layout_criteria_detail_list,
			container, false);
	unit(mView);
	
	return mView;
}

@Override
public void onDestroy() {
	Log.d("TAG", "onDestroy FragmentCriteria");
	// TODO Auto-generated method stub
	super.onDestroy();
}

@Override
public void onDestroyView() {
	Log.d("TAG", "onDestroyView FragmentCriteria");

	super.onDestroyView();
}

@Override
public void onDetach() {
	Log.d("TAG", "onDetach FragmentCriteria");
	// TODO Auto-generated method stub
	super.onDetach();
}

@Override
public void onPause() {
	Log.d("TAG", "onPause FragmentCriteria");
	// TODO Auto-generated method stub
	super.onPause();
}

@Override
public void onResume() {
	Log.d("TAG", "onResume FragmentCriteria");
	// TODO Auto-generated method stub
	super.onResume();
}

@Override
public void onStart() {
	Log.d("TAG", "onStart FragmentCriteria");
	// TODO Auto-generated method stub
	super.onStart();
}

@Override
public void onStop() {
	Log.d("TAG", "onStop FragmentCriteria");
	// TODO Auto-generated method stub
	super.onStop();
}
private void unit(View v) {
	try {
		Log.e("E", "MUC GOC");
        ListView lvCriteria = (ListView) v.findViewById(R.id.lvCriteria);
        JobDal mJobDal = new JobDal(getActivity());
		mJobDal.open();

        String TABLE_NAME = "object_detail_group";
        lstObjs = mJobDal.getObjectGroupDetail(TABLE_NAME, 0, 20);

        AdapterCriterial mAdapter = new AdapterCriterial(getActivity(), lstObjs);
		lvCriteria.setAdapter(mAdapter);
		lvCriteria.setOnItemClickListener(this);

		mJobDal.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}


@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	Log.e("TAG", "Ten thuoc tinh " + lstObjs.get(arg2).getName()
			+ " have_collected_info = " + lstObjs.get(arg2).getHaveOinfo()
			+ " have_childrend = " + lstObjs.get(arg2).getHaveChild());
/*	String areaCode = "";
	AreaObj area = null;
	Bundle getBundle = getArguments();
	if (getBundle != null) {
		area =  (AreaObj) getBundle.getSerializable(Define.KEY_AREA);
		areaCode = area.getAreaCode();
	}
	
	Bundle mBundle = new Bundle();
	mBundle.putSerializable(Define.KEY_CRITERIA, lstObjs.get(arg2));
	mBundle.putSerializable(Define.KEY_AREA,  area);*/
	if(lstObjs.get(arg2).getHaveChild().equals("1") && lstObjs.get(arg2).getHaveOinfo().equals("1")){
		FragmentListCollectedInfo mFragmentListCollectedInfo = new FragmentListCollectedInfo();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Define.KEY_CRITERIA, lstObjs.get(arg2));
		mFragmentListCollectedInfo.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(),
				mFragmentListCollectedInfo, true);

	}else{
		FragmentUpdateCriteria mFragmentUpdateCriteria = new FragmentUpdateCriteria();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Define.KEY_CRITERIA, lstObjs.get(arg2));
		mFragmentUpdateCriteria.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(),
				mFragmentUpdateCriteria, true);
	}
	
	

}

@Override
public void onClick(View arg0) {
	switch (arg0.getId()) {
	
	case R.id.btnHome:
		CommonActivity.callphone(getActivity(), Constant.phoneNumber);
//		FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//		ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,true);
		break;
	case R.id.relaBackHome:
		getActivity().onBackPressed();
		break;		}

}


}
