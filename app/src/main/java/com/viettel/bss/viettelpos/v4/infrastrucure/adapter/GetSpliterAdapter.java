package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import java.util.ArrayList;
import java.util.List;

public class GetSpliterAdapter extends BaseAdapter{
	private final Activity mContext;
	private final List<Spin> arrPakageChargeBeans  = new ArrayList<>();
    private final List<Spin> arraylist = new ArrayList<>();
	public GetSpliterAdapter(List<Spin> arr, Activity context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrPakageChargeBeans.addAll(arr);
		}
		
		
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrPakageChargeBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arraylist.get(arg0);
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
			mView = inflater.inflate(R.layout.item_search_node, arg2,
					false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.txtConnector);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		Spin pakageChargeBeans = arrPakageChargeBeans.get(arg0);
		if(pakageChargeBeans != null && !CommonActivity.isNullOrEmpty(pakageChargeBeans.getSplitterCode())){
			holder.txtpstn.setText(pakageChargeBeans.getSplitterCode());
		}
		return mView;
	}
	
	public List<Spin> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrPakageChargeBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrPakageChargeBeans.addAll(arraylist);
		}else{
			for (Spin pakageChargeBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(pakageChargeBeans.getNodeCode()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrPakageChargeBeans.add(pakageChargeBeans);
				}
			}
		}
		notifyDataSetChanged();
		return arrPakageChargeBeans;
	}
	
	
	
}
