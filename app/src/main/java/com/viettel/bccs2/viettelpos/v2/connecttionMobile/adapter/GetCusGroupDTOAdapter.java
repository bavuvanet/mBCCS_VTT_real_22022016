package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import java.util.ArrayList;

public class GetCusGroupDTOAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<Spin> arrReasonDTO = new ArrayList<Spin>();
	private ArrayList<Spin> arraylist = new ArrayList<Spin>();


	public GetCusGroupDTOAdapter(ArrayList<Spin> arr, Context context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrReasonDTO.addAll(arr);
		}
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrReasonDTO.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrReasonDTO.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder {
		TextView txtpstn;
		Button btninfo;
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
			holder.btninfo = (Button) mView.findViewById(R.id.btninfo);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final Spin reasonDTO = arrReasonDTO.get(arg0);
		
	
		holder.btninfo.setVisibility(View.GONE);
		
		
		if(!CommonActivity.isNullOrEmpty(reasonDTO.getName())){
			holder.txtpstn.setText(reasonDTO.getName());
		}
		
		return mView;
	}


	
}
