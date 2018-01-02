package com.viettel.bss.viettelpos.v4.bankplus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.object.FormBean;

public class ListPriceAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<FormBean> data;

	public ListPriceAdapter(ArrayList<FormBean> data, Context context) {
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (data != null && !data.isEmpty()) {
			return data.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHolder {
		TextView tvPrice;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		View gridView = arg1;

		if (gridView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.bankplus_grid_view_price_item,
					arg2, false);

			holder = new ViewHolder();
			gridView.setTag(holder);
		} else {
			holder = (ViewHolder) gridView.getTag();
		}
		FormBean item = data.get(arg0);
		holder.tvPrice = (TextView) gridView.findViewById(R.id.tvPrice);
		holder.tvPrice.setText(StringUtils.formatMoney(item.getCode()));

		if (item.getChecked()) {
			holder.tvPrice
					.setBackgroundResource(R.drawable.rectangle_button_press);
			holder.tvPrice.setTextColor(context.getResources().getColor(
					R.color.white));
		} else {
			holder.tvPrice.setBackgroundResource(R.drawable.rectangle_button);
			holder.tvPrice.setTextColor(context.getResources().getColor(
					R.color.black));
		}
		return gridView;
	}
}
