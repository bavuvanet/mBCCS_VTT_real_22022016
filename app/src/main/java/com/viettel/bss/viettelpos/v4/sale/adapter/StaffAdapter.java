package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class StaffAdapter extends BaseAdapter implements Filterable{
	private final Context context;
	private ArrayList<Staff> lstData;
	private final ArrayList<Staff> filterLstData;
	private ItemFilter filter = new ItemFilter();

	public StaffAdapter(Context context, ArrayList<Staff> lstData) {
		this.context = context;
		this.lstData = lstData;
		this.filterLstData = lstData;
	}

	public ArrayList<Staff> getLstData() {
		return lstData;
	}

	@Override
	public int getCount() {
		if (lstData != null) {
			return lstData.size();
		} else {
			return 0;
		}
	}

	public void setLstData(ArrayList<Staff> lstData) {
		this.lstData = lstData;
	}

	@Override
	public Object getItem(int arg0) {
		if (lstData != null && !lstData.isEmpty()) {
			return lstData.get(arg0);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvStaffCode;
		TextView tvStaffName;
		TextView tvISDNAgent;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		ViewHoder hoder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.bhld_item, arg2, false);
			hoder = new ViewHoder();
			hoder.tvStaffCode = (TextView) mView.findViewById(R.id.tvStaffCode);
			hoder.tvStaffName = (TextView) mView.findViewById(R.id.tvStaffName);
			hoder.tvISDNAgent = (TextView) mView.findViewById(R.id.tvISDNAgent);

			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final Staff item = lstData.get(position);
		if (item != null) {
//			String staffCode = ;
			if (item.getIsdnAgent() != null && !item.getIsdnAgent().trim().isEmpty()) {
//				staffCode = staffCode + " (" +  + ")";
				hoder.tvISDNAgent.setVisibility(View.VISIBLE);
				hoder.tvISDNAgent.setText(item.getIsdnAgent().trim());
			}else{
				
			}
			hoder.tvStaffCode.setText(item.getStaffCode());
			hoder.tvStaffName.setText(item.getName());
		}
		return mView;
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if(filter == null){
			filter = new ItemFilter();
		}
		return filter;
	}
	
	private class ItemFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			String filterString = constraint.toString();
			
			FilterResults results = new FilterResults();
			
			if(filterString == null || filterString.trim().isEmpty()){
				results.values = filterLstData;
				results.count = filterLstData.size();
				return results;
			}
			
			ArrayList<Staff> filterLstStaff = new ArrayList<>();
			Staff staff;
			for(int i = 0; i < filterLstData.size() ; i++){
				staff = filterLstData.get(i);
				
				if(staff.getName().replaceAll(" ", "").toUpperCase().contains(filterString.replaceAll(" ", "").toUpperCase())
						|| staff.getStaffCode().replaceAll(" ", "").toUpperCase().contains(filterString.replaceAll(" ", "").toUpperCase())){
					filterLstStaff.add(staff);
				}
			}
			
			results.values = filterLstStaff;
			results.count = filterLstStaff.size();
			
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence arg0, FilterResults results) {
			// TODO Auto-generated method stub
			lstData = (ArrayList<Staff>) results.values;
			notifyDataSetChanged();
		}
		
	}
}
