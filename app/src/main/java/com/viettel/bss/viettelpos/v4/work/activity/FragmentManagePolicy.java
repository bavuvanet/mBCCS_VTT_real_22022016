package com.viettel.bss.viettelpos.v4.work.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterPolicy;
import com.viettel.bss.viettelpos.v4.work.arr.ArrTaskOJ;
import com.viettel.bss.viettelpos.v4.work.dal.DalPolicy;
import com.viettel.bss.viettelpos.v4.work.object.TaskObject_;
import com.viettel.bss.viettelpos.v4.work.object.TaskTypeOJ;

import java.util.ArrayList;

public class FragmentManagePolicy extends FragmentCommon {

	private ExpandableListView listPolicy;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getStaff();
        setTitleActionBar(mStaff.getNameStaff());
		if (!CommonActivity.isNullOrEmpty(mStaff.getAddressStaff())) {
			setSubTitleActionBar(mStaff.getAddressStaff());
		}

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getStaff();
		idLayout = R.layout.layout_task_manage_policy;
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	private Staff mStaff;
	private void getStaff() {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			// String timeCreateIdNo, birthday, idno;
			mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		}
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		listPolicy = (ExpandableListView) v.findViewById(R.id.lv_list_policy);

		listPolicy.setDividerHeight(2);
		// listPolicy.setGroupIndicator(null);
		// listPolicy.setClickable(true);
		// listPolicy.setOnItemClickListener(this);
		setData4Views(setData4Test());
	}

	private final ArrayList<TaskTypeOJ> p = new ArrayList<>();
	private final ArrayList<ArrTaskOJ> c = new ArrayList<>();
	// String []arrTaskStatus = new String[]{"false", "true"};
	// boolean[] arrTaskStatus = new boolean[]{false, true};
	// TODO Auto-generated method stub

	private ArrayList<TaskObject_> getArrTask(int progress) {
		DalPolicy dalPolicy = new DalPolicy(getActivity());
		dalPolicy.open();
		// Staff staff = dalPolicy.getStaffByUsername(Define.TABLE_NAME_STAFF,
		// Session.userName);
		// // dalPolicy.close();
		// Log.e(tag, staff.getStaffId() + "");
		// return dalPolicy.foo(10, "");// mStaff.getStaffId() + ""
		return dalPolicy.getArrTask(Constant.POLICY_TASK_TYPE,
				mStaff.getStaffId() + "", progress);
	}
	// private ArrayList<TaskObject> getArrTask_(int status) {
	// // TODO Auto-generated method stub
	// ArrayList<TaskObject> arrTaskObjects = new ArrayList<TaskObject>();
	// arrTaskObjects.add(new TaskObject("tastId", "taskTypeId", "nameTask",
	// "contentTask", "createdDateTask", "imageTask", "videoTask",
	// status
	// // arrTaskStatus[status]
	// ));
	// arrTaskObjects.add(new TaskObject("tastId", "taskTypeId", "nameTask",
	// "contentTask", "createdDateTask", "imageTask", "videoTask",
	// status
	// // arrTaskStatus[status]
	// ));
	// arrTaskObjects.add(new TaskObject("tastId", "taskTypeId", "nameTask",
	// "contentTask", "createdDateTask", "imageTask", "videoTask",
	// status
	// // arrTaskStatus[status]
	// ));
	// return arrTaskObjects;
	// }

	private ArrayList<TaskTypeOJ> setData4Test() {
		// TODO Auto-generated method stub
		ArrayList<TaskTypeOJ> arrTaskTypeOJs_res = new ArrayList<>();
		ArrayList<TaskObject_> arrTaskObjectsDone = getArrTask(Constant.POLICY_DONE);
		ArrayList<TaskObject_> arrTaskObjectsNotDone = getArrTask(Constant.POLICY_NOT_DONE);
		// public TaskTypeOJ(String name, long num,
		// ArrayList<TaskObject> arrTaskObjects) {
		arrTaskTypeOJs_res.add(new TaskTypeOJ(act
				.getString(R.string.policy_not_done)
		// "xxx"
				, arrTaskObjectsNotDone.size(), arrTaskObjectsNotDone));
		arrTaskTypeOJs_res.add(new TaskTypeOJ(act
				.getString(R.string.policy_done)
		// "yyy"
				, arrTaskObjectsDone.size(), arrTaskObjectsDone));
		return arrTaskTypeOJs_res;
	}

    private void setData4Views(ArrayList<TaskTypeOJ> arrTaskTypeOJs) {
		p.clear();
		c.clear();
		// Toast.makeText(act, arrTaskTypeOJs.size() + "", 1).show();
		for (TaskTypeOJ obj : arrTaskTypeOJs) {
			p.add(obj);
			// Toast.makeText(act, obj.getLstTaskObjects().size() + "",
			// 1).show();
			ArrTaskOJ child = new ArrTaskOJ();
			child.setArrTaskObjects(obj.getLstTaskObjects());
			c.add(child);
		}
		AdapterPolicy adapter = new AdapterPolicy(act, mStaff, listPolicy, p, c);
		// ToolShopExpandAdapter adapter = new
		// ToolShopExpandAdapter(parentItems,
		// childItems);
		// adapter

		adapter.setInflater(
				(LayoutInflater) getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE), getActivity());
		listPolicy.setAdapter(adapter);
		// listPolicy.ex
		for (int count = 0; count < arrTaskTypeOJs.size(); count++) {
			listPolicy.expandGroup(count);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// Toast.makeText(act, "---" + arg2, 1).show();
	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
