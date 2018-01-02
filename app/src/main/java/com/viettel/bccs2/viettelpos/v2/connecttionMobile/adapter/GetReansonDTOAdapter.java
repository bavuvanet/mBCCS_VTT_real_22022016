package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GetReansonDTOAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<ReasonDTO> arrReasonDTO = new ArrayList<ReasonDTO>();
	private ArrayList<ReasonDTO> arraylist = new ArrayList<ReasonDTO>();
	
	
	public GetReansonDTOAdapter(ArrayList<ReasonDTO> arr, Context context) {
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
		final ReasonDTO reasonDTO = arrReasonDTO.get(arg0);
		
	
		holder.btninfo.setVisibility(View.GONE);
		
		
		if(!CommonActivity.isNullOrEmpty(reasonDTO.toString())){
			holder.txtpstn.setText(reasonDTO.toString());
		}
		
		return mView;
	}

	public ArrayList<ReasonDTO> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrReasonDTO.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrReasonDTO.addAll(arraylist);
		}else{
			for (ReasonDTO promotionTypeBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(promotionTypeBeans.toString()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrReasonDTO.add(promotionTypeBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrReasonDTO;
	}
	
}
