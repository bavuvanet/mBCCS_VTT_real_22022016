package com.viettel.bss.viettelpos.v4.sale.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleByOrder;
import com.viettel.bss.viettelpos.v4.sale.object.BankplusOrderBO;

import java.util.ArrayList;
import java.util.List;

public class BankplusOrderAdapter extends BaseAdapter {

	private final Context context;
	private List<BankplusOrderBO> data = new ArrayList<>();
	private String status;

	public BankplusOrderAdapter(Context context, List<BankplusOrderBO> data,
			String status) {
		super();
		this.context = context;
		this.data = data;
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	static class ViewHoder {
		TextView tvOrderCode;
		// TextView tvStockModelName;
		TextView tvStaffCode;
		TextView tvStaffName;
		TextView tvSerialWarring;
		RadioButton radioButton;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final ViewHoder hoder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.sale_bankplus_sale_order_row,
					parent, false);
			hoder = new ViewHoder();
			hoder.tvOrderCode = (TextView) row.findViewById(R.id.tv_order_code);
			hoder.tvStaffCode = (TextView) row.findViewById(R.id.tv_staff_code);
			hoder.tvStaffName = (TextView) row.findViewById(R.id.tv_staff_name);
			hoder.radioButton = (RadioButton) row
					.findViewById(R.id.rb_orderBO_select);
			row.setTag(hoder);
		} else {
			hoder = (ViewHoder) row.getTag();
		}

		BankplusOrderBO item = data.get(position);
		if (item != null) {
			hoder.radioButton.setChecked(item.isChecked());
			hoder.tvOrderCode.setText(item.getOrderCode());
			hoder.tvStaffCode.setText(item.getStaffCode() + ", "
					+ item.getStaffName() + ", " + item.getBankPlusMobile());

			if (status.equals(FragmentSaleByOrder.STATUS_PENDING)) {
				// hoder.tvStaffName.setTextSize(context.getResources()
				// .getDimension(R.dimen.text_size_nomal));
				hoder.tvStaffName.setText(item.getDescription());
			} else if (status.equals(FragmentSaleByOrder.STATUS_HAVE_HANDLED)) {
				String strSaleTransStatus = item.getSaleTransStatus();
				hoder.tvStaffName.setTypeface(hoder.tvStaffName.getTypeface(),
						Typeface.BOLD);
				if ("1".equals(strSaleTransStatus)) {
					hoder.tvStaffName.setText(context.getResources().getString(
							R.string.pay_bank_plus_pending));
				} else if ("2".equals(strSaleTransStatus)
						|| "3".equals(strSaleTransStatus)) {
					hoder.tvStaffName.setTextColor(context
							.getColor(R.color.blue_light));
					hoder.tvStaffName.setText(context.getResources().getString(
							R.string.pay_bank_plus_ok));
				} else if ("4".equals(strSaleTransStatus)) {
					hoder.tvStaffName.setTextColor(context
							.getColor(R.color.red));
					hoder.tvStaffName.setText(context.getResources().getString(
							R.string.pay_bank_plus_cancel));
				} else {
					hoder.tvStaffName.setTextColor(context
							.getColor(R.color.red));
					hoder.tvStaffName.setText(context.getResources().getString(
							R.string.pay_bank_plus_invalid));
				}
			}
		}
		return row;
	}

}
