package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;

public class SubGoodsInfoAdapter extends BaseAdapter {
	private final Context context;
	private List<SubGoods> subGoods = new ArrayList();

	public SubGoodsInfoAdapter(Context context, List<SubGoods> lstData) {
		this.context = context;
		this.subGoods = lstData;
	}

	@Override
	public int getCount() {
		if (subGoods == null) {
			return 0;
		} else {
			return subGoods.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return subGoods.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tv_stockModelCodeName;
		TextView tv_serial;
		TextView tv_reclaimPayMethodName;
		LinearLayout ll_serialToRetrieve;
		EditText edt_serialToRetrieve;
		RadioButton cb_selectGoods;
	}

	@Override
	public View getView(final int position, View convertview,
			ViewGroup viewGroup) {
		View row = convertview;
		final SubGoods item = subGoods.get(position);
		final ViewHoder holder;
		// if (row == null) {
		holder = new ViewHoder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(R.layout.layout_subscriber_good_row, viewGroup,
				false);
		holder.tv_stockModelCodeName = (TextView) row
				.findViewById(R.id.tv_stockModelCodeName);
		holder.tv_serial = (TextView) row.findViewById(R.id.tv_serial);
		holder.tv_reclaimPayMethodName = (TextView) row
				.findViewById(R.id.tv_reclaimPayMethodName);
		holder.ll_serialToRetrieve = (LinearLayout) row
				.findViewById(R.id.ll_serialToRetrieve);
		holder.edt_serialToRetrieve = (EditText) row
				.findViewById(R.id.edt_serialToRetrieve);

		holder.cb_selectGoods = (RadioButton) row
				.findViewById(R.id.cb_selectGoods);
		holder.edt_serialToRetrieve.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				item.setSerialToRetrieve(arg0.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

		});
		holder.cb_selectGoods.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (holder.cb_selectGoods.isChecked()) {
					for (SubGoods tmp : subGoods) {
						tmp.setRetrieveGoods(false);
					}
					item.setRetrieveGoods(true);
				} else {
					item.setRetrieveGoods(false);
				}
				notifyDataSetChanged();
			}
		});
		// row.setTag(holder);
		// } else {
		// holder = (ViewHoder) row.getTag();
		// }

		if (item != null) {

			// holder.tv_stockModelCodeName.setText(item.getStockModelCode() +
			// item.getStockModelName());
			holder.tv_stockModelCodeName.setText(item.getStockModelName());
			holder.tv_reclaimPayMethodName.setText(item
					.getReclaimPayMethodName());
			if (item.isVirtualGoods()) {
				holder.tv_serial.setText(context
						.getString(R.string.virtualSerial) + item.getSerial());
				holder.ll_serialToRetrieve.setVisibility(View.GONE);
			} else {
				holder.tv_serial.setText(context.getString(R.string.serial)
						+ item.getSerial());
				holder.ll_serialToRetrieve.setVisibility(View.VISIBLE);
				holder.edt_serialToRetrieve.setText(item.getSerialToRetrieve());
			}

			holder.cb_selectGoods.setChecked(item.isRetrieveGoods());
			if (!item.isVirtualGoods() && item.isAllowSerial()
					&& item.isRetrieveGoods()) {
				holder.edt_serialToRetrieve.setEnabled(true);
			} else {
				holder.edt_serialToRetrieve.setEnabled(false);
			}

		}

		return row;
	}

}
