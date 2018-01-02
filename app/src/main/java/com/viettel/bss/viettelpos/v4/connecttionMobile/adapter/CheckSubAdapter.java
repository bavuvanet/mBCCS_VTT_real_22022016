package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

public class CheckSubAdapter extends BaseAdapter {

	private final Context context;
	private List<SubscriberDTO> lsSubscriberDTO= new ArrayList<>();
	private List<SubscriberDTO> arrayList;
	private final OnChangeCheckedSub onChangeCheckedState;

	public interface OnChangeCheckedSub {
		void onChangeChecked(SubscriberDTO subscriberDTO);
	}

	public CheckSubAdapter(Context context, List<SubscriberDTO> lstData,
			OnChangeCheckedSub onChangeCheckedState) {
		this.context = context;
		this.arrayList = new ArrayList<>();
		if (lsSubscriberDTO != null) {
			this.lsSubscriberDTO.addAll(lstData);
			this.arrayList = lstData;
		}
		
		this.onChangeCheckedState = onChangeCheckedState;
	}

	@Override
	public int getCount() {
		if (lsSubscriberDTO == null) {
			return 0;
		} else {
			return lsSubscriberDTO.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return lsSubscriberDTO.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.checkbox_sub_item,
					viewGroup, false);
			holder = new ViewHoder();
			holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		final SubscriberDTO item = lsSubscriberDTO.get(position);
		if (item != null) {
			holder.checkBox.setText(item.getIsdn()+"("+item.getProductCode()+")");
			holder.checkBox.setChecked(item.isHasVerifiedOwner());
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onChangeCheckedState.onChangeChecked(item);
				}
			});
		}
		return row;
	}

	public void SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		lsSubscriberDTO = new ArrayList<>();
		if(chartext.length() == 0){
			Log.d("size arraylist", ""+arrayList.size());
			lsSubscriberDTO.addAll(arrayList);
		}else{
			for (SubscriberDTO subscriberDTO : arrayList) {
				if(CommonActivity.convertCharacter1(subscriberDTO.getIsdn()).contains(CommonActivity.convertCharacter1(chartext))){
					lsSubscriberDTO.add(subscriberDTO);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	
}
