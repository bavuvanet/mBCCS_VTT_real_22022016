package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetProvinceCDAdapter extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<AreaObj> arrAreaObjs = new ArrayList<>();
    private final ArrayList<AreaObj> arraylist = new ArrayList<>();

	public GetProvinceCDAdapter(ArrayList<AreaObj> arr, Context context) {
		if (arr != null) {
			this.arraylist.addAll(arr);
			this.arrAreaObjs.addAll(arr);
		}
		mContext = context;
	}

	@Override
	public int getCount() {
		return arrAreaObjs.size();
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
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage,
					arg2, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AreaObj customerGroupBeans = arrAreaObjs.get(arg0);
		if (customerGroupBeans != null
				&& !CommonActivity.isNullOrEmpty(customerGroupBeans
						.getName())) {
			holder.txtpstn.setText(customerGroupBeans.getProvince() +" "+ customerGroupBeans.getName());
		}

		return mView;
	}

	public ArrayList<AreaObj> SearchInput(String chartext) {
		chartext = chartext.toLowerCase();
		arrAreaObjs.clear();
		if (chartext.isEmpty()) {
			Log.d("size arraylist", "" + arraylist.size());
			arrAreaObjs.addAll(arraylist);
		} else {
			for (AreaObj customerGroupBeans : arraylist) {
				if ( CommonActivity
						.convertCharacter1(
								customerGroupBeans.getProvince())
						.toLowerCase()
						.contains(
								CommonActivity.convertCharacter1(chartext)) || CommonActivity
						.convertCharacter1(
								customerGroupBeans.getName())
						.toLowerCase()
						.contains(
								CommonActivity.convertCharacter1(chartext))) {
					arrAreaObjs.add(customerGroupBeans);
				}
			}
		}

		notifyDataSetChanged();
		return arrAreaObjs;
	}

}
