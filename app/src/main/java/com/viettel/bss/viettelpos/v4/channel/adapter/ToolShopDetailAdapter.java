package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.AssetDetailHistoryOJ;

class ToolShopDetailAdapter extends BaseAdapter {
	private final ArrayList<AssetDetailHistoryOJ> arrayListManager;
	private final Context mContext;

	public ToolShopDetailAdapter(ArrayList<AssetDetailHistoryOJ> arrayList,
			Context context) {
		this.arrayListManager = arrayList;
		this.mContext = context;
	}

	public interface onSelectListenner {
		void onSelect(int positionTask, int positonSalePoint);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayListManager.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView tvNote;
		TextView tvNumber;
		
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
        if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_tool_shop_detail, arg2, false);
			holder = new ViewHolder();
//			holder.tvNote = (TextView) mView
//					.findViewById(R.id.tvNote);
//			holder.tvNumber = (TextView) mView
//					.findViewById(R.id.tvNumber);
			

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		AssetDetailHistoryOJ obj = arrayListManager.get(arg0);

		if (obj != null) {
			holder.tvNote.setText(obj.getNote());
			holder.tvNumber.setText(obj.getQty().toString());
		}
		return mView;
	}

}
