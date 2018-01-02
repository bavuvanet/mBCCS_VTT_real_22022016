package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetListOrderAdapter extends BaseAdapter {
	private ArrayList<GetOrderObject> lisGetOrderObjects = new ArrayList<>();
	private Context context = null;

	public GetListOrderAdapter(ArrayList<GetOrderObject> arrGetOrderObjects,
			Context context) {
		this.context = context;
		this.lisGetOrderObjects = arrGetOrderObjects;
	}

	@Override
	public int getCount() {
		return lisGetOrderObjects.size();
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
		TextView txtorder;
		TextView tvWarning;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GetOrderObject getOrderObject = this.lisGetOrderObjects
				.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.listorder_item, parent, false);
			holder = new ViewHolder();
			holder.txtorder = (TextView) mView.findViewById(R.id.txtorderotem);
			holder.tvWarning = (TextView) mView.findViewById(R.id.tv_warning);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtorder.setText(context.getResources().getString(
				R.string.orderid)
				+ "  "
				+ getOrderObject.getStockOrderCode()
				+ "\n"
				+ context.getResources().getString(R.string.nameorderid)
				+ "  "
				+ getOrderObject.getStaffName()
				+ "\n"
				+ context.getResources().getString(R.string.timeorder)
				+ "  "
				+ DateTimeUtils.convertDate(getOrderObject.getCreateDate()
						+ "\n\n"));
		if (getOrderObject.getWarning() != null
				&& !getOrderObject.getWarning().isEmpty()) {
			holder.tvWarning.setText(getOrderObject.getWarning());
			holder.tvWarning.setVisibility(View.VISIBLE);
		} else {
			holder.tvWarning.setVisibility(View.GONE);
		}
		return mView;
	}

}
