package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.AssetDetailHistoryOJ;
import com.viettel.bss.viettelpos.v4.channel.object.AssetTypeOJ;

class ToolShopAdapter extends BaseAdapter {
	private final ArrayList<AssetTypeOJ> arrayListManager;
	private final Context mContext;
	
	public ToolShopAdapter(ArrayList<AssetTypeOJ> arrayList,
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
		TextView tvType;
		TextView txtNumber;
		ListView lvDetail;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
        if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_tool_shop, arg2, false);
			holder = new ViewHolder();
			holder.tvType = (TextView) mView
					.findViewById(R.id.tv_tool_shop_type);
			holder.txtNumber = (TextView) mView
					.findViewById(R.id.txt_number_tool_shop);
			//holder.lvDetail = (ListView) mView.findViewById(R.id.lvDetail);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AssetTypeOJ obj = arrayListManager.get(arg0);
		ArrayList<AssetDetailHistoryOJ> arrDetailHistoryOJs = obj.getLstAssetDetailOJ();
		
		Log.e("TAG",obj.getName() + "arrDetailHistoryOJs " + arrDetailHistoryOJs.size());
		
		if (obj != null) {
			holder.txtNumber.setText(obj.getQty().toString());
			holder.tvType.setText(obj.getName());

		}
		ToolShopDetailAdapter mAdapter;
		if(!arrDetailHistoryOJs.isEmpty()){
			mAdapter = new ToolShopDetailAdapter(arrDetailHistoryOJs, mContext);
			holder.lvDetail.setAdapter(mAdapter);
		}
		
		return mView;
	}

}
