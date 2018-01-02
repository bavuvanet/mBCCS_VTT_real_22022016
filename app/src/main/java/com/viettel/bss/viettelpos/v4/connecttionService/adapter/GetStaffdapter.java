package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetStaffdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<Staff> arrStaff  = new ArrayList<>();
    private final ArrayList<Staff> arraylist = new ArrayList<>();
	
	
	public GetStaffdapter(ArrayList<Staff> arr, Context context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrStaff.addAll(arr);
		}
		mContext = context;
	}
	
	@Override
	public int getCount() {
		
		if(arrStaff == null || arrStaff.size() == 0){
			return 0;
		}else{
			return arrStaff.size();
		}
		
	}

	@Override
	public Object getItem(int arg0) {
		return arrStaff.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
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
		Staff hthmBeans = arrStaff.get(arg0);
		if(hthmBeans != null && !hthmBeans.toString().isEmpty()){
			holder.txtpstn.setText(hthmBeans.toString());
		}
		
		
		return mView;
	}
	
	public ArrayList<Staff> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrStaff.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrStaff.addAll(arraylist);
		}else{
			for (Staff hTBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(hTBeans.toString()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrStaff.add(hTBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrStaff;
	}
	
	
	
}
