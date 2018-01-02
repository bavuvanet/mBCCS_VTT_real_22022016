package com.viettel.bss.viettelpos.v4.work.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentPolicyDetail;
import com.viettel.bss.viettelpos.v4.work.arr.ArrTaskOJ;
import com.viettel.bss.viettelpos.v4.work.object.TaskObject_;
import com.viettel.bss.viettelpos.v4.work.object.TaskTypeOJ;

import java.util.ArrayList;

//import android.widget.////Toast;

public class AdapterPolicy extends BaseExpandableListAdapter
		implements
			OnClickListener {
	private Activity activity;

	private LayoutInflater inflater;
	private final ArrayList<ArrTaskOJ> childtems;// ok
	private final ArrayList<TaskTypeOJ> parentItems;
	private ArrayList<TaskObject_> child;
	private final ExpandableListView list;
	private final Staff mStaff;

	public AdapterPolicy(Activity act, Staff mStaff, ExpandableListView list,
			ArrayList<TaskTypeOJ> arrTaskTypeOJs, ArrayList<ArrTaskOJ> arr) {
		this.list = list;
		parentItems = arrTaskTypeOJs;
		childtems = arr;
		this.mStaff = mStaff;
		this.act = act;
	}

	public void setInflater(LayoutInflater inflater, Activity activity2) {
		this.inflater = inflater;
		this.activity = activity2;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final TaskTypeOJ type = parentItems.get(groupPosition);
		ArrayList<TaskObject_> arrTaskObject_s = type.getLstTaskObjects();
		final TaskObject_ taskObject_ = arrTaskObject_s.get(childPosition);
		TextView tvName = null;
		TextView txtStatus = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_policy, null);
		}
		tvName = (TextView) convertView.findViewById(R.id.txt_policy_name);
		txtStatus = (TextView) convertView.findViewById(R.id.txt_policy_status);
		tvName.setText(taskObject_.getNameTask());
		// String textStatus = activity.getString(R.string.chua_thong) + " "
		// + taskObject_.getFinishedDateTask();
		// textStatus = taskObject_.getProgressText();
		//
		// String date = taskObject_.getFinishedDateTask();
		// if (date != null && !date.isEmpty()) {
		// textStatus += "\n" + activity.getString(R.string.date)
		// + taskObject_.getFinishedDateTask();
		// }

		Log.e(tag, taskObject_.getColorId() + "---color id");
		// txtStatus.setText(textStatus);
		// txtStatus.setTextColor(Color.parseColor("#00FF00"));
		if (taskObject_.getColorId() != 0) {
			txtStatus.setTextColor(LoginActivity.getInstance().getColor(taskObject_.getColorId()));

		}

		txtStatus.setText(taskObject_.getProgressText());
		// txtStatus.setTextColor(taskObject_.getColorId());

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.e(tag, "CLICK name= " + type.getName() + " size="
						+ type.getLstTaskObjects().size());
				Log.e(tag, taskObject_.getAllContent());
				FragmentPolicyDetail frag = new FragmentPolicyDetail();
				Bundle mBundle = new Bundle();
				mBundle.putSerializable(Define.KEY_TASK, taskObject_);
				mBundle.putSerializable(Define.KEY_STAFF, mStaff);
				frag.setArguments(mBundle);
				ReplaceFragment.replaceFragment(activity, frag, true);
			}
		});
		return convertView;
	}// child

	@SuppressLint("InflateParams")
    public View getChildView_(final int groupPosition, final int childPosition,
                              boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		child = childtems.get(groupPosition).getArrTaskObjects();
		// TaskTypeOJ type =parentItems.get(groupPosition);
		TextView tvName = null;
		TextView txtStatus = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_policy, null);
		}

		tvName = (TextView) convertView.findViewById(R.id.txt_policy_name);
		txtStatus = (TextView) convertView.findViewById(R.id.txt_policy_status);

		tvName.setText(child.get(childPosition).getNameTask());
		// txtStatus.setText(child.get(childPosition).getStatuTask());
		// String textStatus = child.get(childPosition).getStatus() ? activity
		// .getString(R.string.da_thong) : activity
		// .getString(R.string.chua_thong);
		String textStatus = activity.getString(R.string.chua_thong);
		if (child.get(childPosition).getStatus()) {
			textStatus = activity.getString(R.string.da_thong) + "\n"
					+ activity.getString(R.string.date)
					+ child.get(childPosition).getFinishedDateTask();

		}
		txtStatus.setText(textStatus);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				// TaskObject_ task = child.get(childPosition);
				// Log.e(tag, "CLICK " + task.getAllContent());
				TaskTypeOJ type = parentItems.get(groupPosition);
				Log.e(tag, "CLICK name= " + type.getName() + " size="
						+ type.getLstTaskObjects().size());
				Log.e(tag, "CLICK groupPos = " + groupPosition + " childPos="
						+ childPosition + " childSize = " + child.size());
				if (childPosition < child.size()) {
					TaskObject_ task = child.get(childPosition);
					Log.e(tag, task.getAllContent());
				}
				// FragmentPolicyDetail frag = new FragmentPolicyDetail();
				// Bundle mBundle = new Bundle();
				//
				// mBundle.putSerializable(Define.KEY_TASK,
				// child.get(childPosition));
				// mBundle.putSerializable(Define.KEY_STAFF, mStaff);
				// frag.setArguments(mBundle);
				// ReplaceFragment.replaceFragment(activity, frag, true);
			}
		});

		return convertView;
	}// child

	@Override
	public int getChildrenCount(int groupPosition) {
		return childtems.get(groupPosition).getArrTaskObjects().size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (parentItems == null) {
			// //Toast.makeText(activity, "parent null", 1).show();
			return null;
		}
		if (childtems == null) {
			// //Toast.makeText(activity, "childs null", 1).show();
			return null;
		}
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_group_policy, null);
			// convertView = inflater.inflate(R.layout.item_tool_shop, null);
		}
		LinearLayout layout = null;
		TextView tvTypeName = null;
		final ImageView btnShowPolicy;
		layout = (LinearLayout) convertView
				.findViewById(R.id.layout_parent_policy);
		tvTypeName = (TextView) convertView
				.findViewById(R.id.txt_policy_status_name);
		btnShowPolicy = (ImageView) convertView
				.findViewById(R.id.btn_show_policy);
		if (tvTypeName != null) {
			final TaskTypeOJ type = parentItems.get(groupPosition);
			tvTypeName.setText(type.getName() + " (" + type.getQty() + ")");
			if (type.getLstTaskObjects().isEmpty()) {
				btnShowPolicy.setVisibility(View.INVISIBLE);
				btnShowPolicy.setBackgroundResource(id_collapse);
			} else {
				if (isExpanded) {
					btnShowPolicy.setBackgroundResource(id_expand);
				} else {
					btnShowPolicy.setBackgroundResource(id_collapse);
				}
			}

			OnClickListener onClickListener = new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// ////Toast.makeText(
					// activity,
					// "", 1).show();
					Log.e(tag, "onclick");
					// list.
					// list.collapseGroup(groupPosition);
					if (!type.getLstTaskObjects().isEmpty()) {
						if (isExpanded) {
							list.collapseGroup(groupPosition);
							btnShowPolicy.setBackgroundResource(id_collapse);
						} else {
							list.expandGroup(groupPosition);
							btnShowPolicy.setBackgroundResource(id_expand);
						}
					} else {

						// Toast.makeText(activity,
						// activity.getString(R.string.policy_empty_list),
						// 1).show();
						CommonActivity.createAlertDialog(act,
								R.string.empty_list, R.string.app_name)
								.show();
					}

					// list.
					// convertView.
					// isExpanded = !isExpanded;

				}
			};
			layout.setOnClickListener(onClickListener);
			btnShowPolicy.setOnClickListener(onClickListener);
		}

		return convertView;
	}// parent

	private final Activity act;
	// int id_expand = R.drawable.background_arrow_down;
	// int id_collapse = R.drawable.background_arrow_right;;
    private final int id_expand = R.drawable.down;
	private final int id_collapse = R.drawable.right;
	private final String tag = "adapter policy";

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// int id = arg0.getId();
		// switch (id) {
		// case R.id.btn_show :
		//
		// break;
		//
		// default :
		// break;
		// }
	}
}
