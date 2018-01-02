package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerGroupBeans;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetCusGroupAdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<CustomerGroupBeans> arrCustomerGroupBeans  = new ArrayList<>();
    private final ArrayList<CustomerGroupBeans> arraylist = new ArrayList<>();
	public GetCusGroupAdapter(ArrayList<CustomerGroupBeans> arr, Context context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrCustomerGroupBeans.addAll(arr);
		}
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrCustomerGroupBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView txtpstn;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage, arg2,
					false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		CustomerGroupBeans customerGroupBeans = arrCustomerGroupBeans.get(arg0);
		if(customerGroupBeans != null && !CommonActivity.isNullOrEmpty(customerGroupBeans.getNameCusGroup())){
			holder.txtpstn.setText(customerGroupBeans.getNameCusGroup());
		}
		
		
		return mView;
	}
	
	public ArrayList<CustomerGroupBeans> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrCustomerGroupBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrCustomerGroupBeans.addAll(arraylist);
		}else{
			for (CustomerGroupBeans customerGroupBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(customerGroupBeans.getNameCusGroup()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrCustomerGroupBeans.add(customerGroupBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrCustomerGroupBeans;
	}
	
	
	
}
