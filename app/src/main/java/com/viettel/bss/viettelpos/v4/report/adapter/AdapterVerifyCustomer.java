package com.viettel.bss.viettelpos.v4.report.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerOJ;
import com.viettel.bss.viettelpos.v4.report.arr.ArrVerifyCustomerOJ;
import com.viettel.bss.viettelpos.v4.report.object.TypeVerOJ;
import com.viettel.bss.viettelpos.v4.report.object.VerifyCustomerOJ;

import java.util.ArrayList;

public class AdapterVerifyCustomer extends BaseExpandableListAdapter
		implements
			OnClickListener {
	private Activity activity;

	private LayoutInflater inflater;
	private final ArrayList<ArrVerifyCustomerOJ> childtems;// ok
	private final ArrayList<TypeVerOJ> parentItems;
	private final ExpandableListView list;
	public AdapterVerifyCustomer(Activity act, ExpandableListView list,
			ArrayList<TypeVerOJ> arrTypeVerifyCustomerOJs,
			ArrayList<ArrVerifyCustomerOJ> arrList) {
		this.activity = act;
		this.list = list;
		parentItems = arrTypeVerifyCustomerOJs;
		childtems = arrList;

	}
	public void setInflater(LayoutInflater inflater, Activity activity2) {
		this.inflater = inflater;
		this.activity = activity2;
	}
	private final int COLOR_DONE = R.color.blue_light;
	private final int COLOR_NOT_DONE = R.color.brown_light;
	private final int ID_TEXT_NOT_DONE = R.string.na;
	@SuppressLint("InflateParams")
    @Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Log.e(tag, "get child view " + groupPosition + "---" + childPosition);
		// TODO Auto-generated method stub
		TypeVerOJ typeVerifyCustomerOJ = parentItems.get(groupPosition);
		ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs = typeVerifyCustomerOJ
				.getArrVerifyCustomerOJs();
		final VerifyCustomerOJ verifyCustomerOJ = arrVerifyCustomerOJs
				.get(childPosition);
		TextView tvIsdn, tvAddr, tvLclEmpl, tvDateVrfied;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_verify_customer, null);
		} else {
			// return convertView;
		}
		tvIsdn = (TextView) convertView.findViewById(R.id.tv_cus_name);
		tvAddr = (TextView) convertView.findViewById(R.id.tv_cus_care_line2);
		tvLclEmpl = (TextView) convertView.findViewById(R.id.tv_cus_care_line3);
		tvDateVrfied = (TextView) convertView
				.findViewById(R.id.tv_cus_care_line4);
		CustomerOJ customerOJ = verifyCustomerOJ.getCustomerOJ();
		tvIsdn.setText(customerOJ.getName());
		tvAddr.setText(customerOJ.getAddr());
		String idLclEmployee = verifyCustomerOJ.getIdLocalEmployee();
		String text = activity.getString(R.string.ver_id_nvdb) + ": ";
		int colorId = COLOR_NOT_DONE;
		if (idLclEmployee != null && !idLclEmployee.isEmpty()) {
			text += idLclEmployee;
			// colorId = COLOR_DONE;
		} else {
			text += activity.getString(ID_TEXT_NOT_DONE)
			// .toLowerCase()
			;
		}
		tvLclEmpl.setText(text);
		String text1 = activity.getString(R.string.ver_date) + ": ";
		String dateVer = verifyCustomerOJ.getDateVerified();
		if (dateVer != null && !dateVer.isEmpty()) {
			text1 += dateVer;
			colorId = COLOR_DONE;
		} else {
			text1 += activity.getString(ID_TEXT_NOT_DONE)
			// .toLowerCase()
			;
		}
		tvDateVrfied.setText(text1);
		tvLclEmpl.setTextColor(MainActivity.getInstance()
				.getColor(colorId));
		tvDateVrfied.setTextColor(MainActivity.getInstance()
				.getColor(colorId));
		// convertView.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View view) {
		// Log.e(tag, "CLICK child= ");
		// FragmentReportInfoVerCustomer frag = new
		// FragmentReportInfoVerCustomer();
		// // Log.e(tag, taskObject_.getAllContent());
		// // FragmentPolicyDetail frag = new FragmentPolicyDetail();
		// Bundle mBundle = new Bundle();
		// mBundle.putSerializable(Define.KEY_VER_CUSTOMER,
		// verifyCustomerOJ);
		// frag.setArguments(mBundle);
		// ReplaceFragment.replaceFragment(activity, frag, true);
		// }
		// });
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return childtems.get(groupPosition).getArrVerifyCustomerOJ().size();
	}
	@Override
	public int getGroupCount() {
		return parentItems.size();
	}
	private final int id_expand = R.drawable.down;
	private final int id_collapse = R.drawable.right;
	private final String tag = "adapter policy";
	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		Log.e(tag, "get group view");
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
			convertView = inflater.inflate(R.layout.item_group_ver_customer,
					null);
			// convertView = inflater.inflate(R.layout.item_tool_shop, null);
		} else {
			// return convertView;
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

			final TypeVerOJ typeVerifyCustomerOJ = parentItems
					.get(groupPosition);
			int colorId = typeVerifyCustomerOJ.getColorId();
			int n = typeVerifyCustomerOJ.getArrVerifyCustomerOJs().size();
			// SET COLOR FOR TYPE
			// if (colorId != -1 && n > 0 && !typeVerifyCustomerOJ.getDone()) {
			// tvTypeName.setTextColor(MainActivity.getInstance()
			// .getResources().getColor(colorId));
			// }

			tvTypeName.setText(typeVerifyCustomerOJ.getName() + ": " + n);
			if (typeVerifyCustomerOJ.getArrVerifyCustomerOJs().isEmpty()) {
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
					if (!typeVerifyCustomerOJ.getArrVerifyCustomerOJs()
							.isEmpty()) {
						if (isExpanded) {
							list.collapseGroup(groupPosition);
							btnShowPolicy.setBackgroundResource(id_collapse);
						} else {
							// list.expandGroup(groupPosition);
							list.expandGroup(groupPosition, true);
							btnShowPolicy.setBackgroundResource(id_expand);
						}
					} else {

						// Toast.makeText(activity,
						// activity.getString(R.string.policy_empty_list),
						// 1).show();
						CommonActivity.createAlertDialog(activity,
								R.string.empty_list, R.string.app_name)
								.show();
					}

				}
			};
			layout.setOnClickListener(onClickListener);
			btnShowPolicy.setOnClickListener(onClickListener);

		}
		return convertView;
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
	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
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

	}

}
