package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.IsdnObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IsdnAdapter extends BaseAdapter {

	private final Activity mActivity;
	private final ArrayList<IsdnObject> listIsdn;
	
	
	public IsdnAdapter(Activity mActivity,ArrayList<IsdnObject> listIsdn) {
		this.listIsdn = listIsdn;
		this.mActivity = mActivity;
	}
	
	@Override
	public int getCount() { 
		return listIsdn.size();
	}

	@Override
	public Object getItem(int position) { 
		return listIsdn.get(position);
	}

	@Override
	public long getItemId(int position) { 
		return 0;
	}

	static class ViewHolder {
		TextView tvIsdnCode;
		TextView tvCreateDate;
		TextView tvRecodeName;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		IsdnObject isdnObject = listIsdn.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_customer_isdn,parent, false);
			holder = new ViewHolder();
			holder.tvIsdnCode = (TextView) mView
					.findViewById(R.id.tvIsdnCode);
			holder.tvCreateDate = (TextView) mView
					.findViewById(R.id.tvCreateDate);
			holder.tvRecodeName = (TextView) mView
					.findViewById(R.id.tvRecodeName);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.tvIsdnCode.setText(isdnObject.getAccount());
		holder.tvCreateDate.setText(isdnObject.getCreateDate());
		holder.tvRecodeName.setText(isdnObject.getActionName());

		return mView; 
	}

}
