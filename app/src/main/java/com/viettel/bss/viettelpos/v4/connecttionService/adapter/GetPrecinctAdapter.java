package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetPrecinctAdapter extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<AreaBean> arrAreaBeans = new ArrayList<>();
    private final ArrayList<AreaBean> arraylist = new ArrayList<>();

	public GetPrecinctAdapter(ArrayList<AreaBean> arr, Context context) {
		if (arr != null) {
			this.arraylist.addAll(arr);
			this.arrAreaBeans.addAll(arr);
		}
		mContext = context;
	}

	@Override
	public int getCount() {
		return arrAreaBeans.size();
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
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage, arg2, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AreaBean customerGroupBeans = arrAreaBeans.get(arg0);
		if (customerGroupBeans != null && !CommonActivity.isNullOrEmpty(customerGroupBeans.getNamePrecint())) {
			holder.txtpstn.setText(customerGroupBeans.getPrecinct() + " "+customerGroupBeans.getNamePrecint());
		}

		return mView;
	}

	public ArrayList<AreaBean> SearchInput(String chartext) {
		chartext = chartext.toLowerCase();
		arrAreaBeans.clear();
		if (chartext.isEmpty()) {
			Log.d("size arraylist", "" + arraylist.size());
			arrAreaBeans.addAll(arraylist);
		} else {
			for (AreaBean customerGroupBeans : arraylist) {
				if (CommonActivity.convertCharacter1(customerGroupBeans.getPrecinct()).toLowerCase()
						.contains(CommonActivity.convertCharacter1(chartext))
						|| CommonActivity.convertCharacter1(customerGroupBeans.getNamePrecint()).toLowerCase()
								.contains(CommonActivity.convertCharacter1(chartext))) {
					arrAreaBeans.add(customerGroupBeans);
				}
			}
		}

		notifyDataSetChanged();
		return arrAreaBeans;
	}

}
