package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.SaleTransFull;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterTransDetail extends BaseAdapter {

	private final Context mContext;
	private ArrayList<SaleTransFull> arrTransDetails = new ArrayList<>();

	public AdapterTransDetail(Context context, ArrayList<SaleTransFull> arrDetails) {

		this.mContext = context;
		this.arrTransDetails = arrDetails;

	}

	@Override
	public int getCount() {
		if (arrTransDetails == null) {
			return 0;
		}
		return arrTransDetails.size();
	}

	@Override
	public Object getItem(int arg0) {

		return arrTransDetails.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolders {
		public TextView tvtockModelCode;
		public TextView tvtockModelName;
		public TextView tvQuantity;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHolders holder;
		if (row == null) {

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.item_layout_saletransfull_info, viewGroup, false);
			holder = new ViewHolders();
			holder.tvtockModelCode = (TextView) row.findViewById(R.id.tvtockModelCode);
			holder.tvtockModelName = (TextView) row.findViewById(R.id.tvModelName);
			holder.tvQuantity = (TextView) row.findViewById(R.id.tvQuantity);

			row.setTag(holder);
		} else {
			holder = (ViewHolders) row.getTag();
		}

		final SaleTransFull transDetail = arrTransDetails.get(position);
		String strModelCode = transDetail.getStockModelCode();
		holder.tvtockModelCode.setText(strModelCode);
		holder.tvQuantity
				.setText(mContext.getResources().getString(R.string.tv_quantity) + ": " + transDetail.getQuantity());
		holder.tvtockModelName.setText(transDetail.getName());

		return row;
	}

}
