package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ConTractBean;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetContractAdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<ConTractBean> arrTractBeans  = new ArrayList<>();
    private final ArrayList<ConTractBean> arraylist = new ArrayList<>();
	public GetContractAdapter(ArrayList<ConTractBean> arr, Context context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrTractBeans.addAll(arr);
		}
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrTractBeans.size();
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
		TextView tvContractNo,tvdatesignal,tvpayer,tvaddress;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_contract_cd, arg2,
					false);
			holder = new ViewHolder();
			holder.tvContractNo = (TextView) mView.findViewById(R.id.tvContractNo);
			holder.tvdatesignal = (TextView) mView.findViewById(R.id.tvdatesignal);
			holder.tvpayer = (TextView) mView.findViewById(R.id.tvpayer);
			holder.tvaddress = (TextView) mView.findViewById(R.id.tvaddress);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ConTractBean conTractBean = arrTractBeans.get(arg0);
		holder.tvContractNo.setText(conTractBean.getContractNo());
		holder.tvdatesignal.setText(conTractBean.getSignDate());
		holder.tvpayer.setText(conTractBean.getPayer());
		holder.tvaddress.setText(conTractBean.getAddress());
		return mView;
	}
	
	public ArrayList<ConTractBean> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrTractBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrTractBeans.addAll(arraylist);
		}else{
			for (ConTractBean conTractBean : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(conTractBean.getContractNo()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrTractBeans.add(conTractBean);
				}
			}
		}
		notifyDataSetChanged();
		return arrTractBeans;
	}
	
	
	
}
