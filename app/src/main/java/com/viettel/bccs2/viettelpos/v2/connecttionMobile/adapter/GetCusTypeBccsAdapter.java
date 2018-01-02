package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetCusTypeBccsAdapter extends BaseAdapter{
	private  Context mContext;
	private ArrayList<CustTypeDTO> arrCustTypeDTOs  = new ArrayList<CustTypeDTO>();;
	private ArrayList<CustTypeDTO> arraylist = new ArrayList<CustTypeDTO>();
	public GetCusTypeBccsAdapter(ArrayList<CustTypeDTO> arr, Context context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrCustTypeDTOs.addAll(arr);
		}
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrCustTypeDTOs.size();
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
		CustTypeDTO custTypeDTO = arrCustTypeDTOs.get(arg0);
		if(custTypeDTO != null && !CommonActivity.isNullOrEmpty(custTypeDTO.getName())){
			holder.txtpstn.setText(custTypeDTO.getCustType() + "-" +custTypeDTO.getName());
		}
		return mView;
	}
	
	public ArrayList<CustTypeDTO> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrCustTypeDTOs.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrCustTypeDTOs.addAll(arraylist);
		}else{
			for (CustTypeDTO custTypeDTO : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(custTypeDTO.getCustType().toLowerCase()).contains(CommonActivity.convertUnicode2Nosign(chartext)) || CommonActivity.convertUnicode2Nosign(custTypeDTO.getName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrCustTypeDTOs.add(custTypeDTO);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrCustTypeDTOs;
	}
	
	
	
}
