package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.report.object.SmartPhoneBO;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterReportStaffRevenueDetail extends BaseAdapter {
	private final Context mContext;
	private final List<SmartPhoneBO> arr;

	public AdapterReportStaffRevenueDetail(Context context, List<SmartPhoneBO> _arr) {
		this.mContext = context;
		this.arr = _arr;
	}

	@Override
	public int getCount() {
		return arr.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * § stockModelId § stockModelName § stockModelCode § Số lượng § tiền trước
	 * thuế § Tiền thuế § Tiền sau thuế § Chiết khấu
	 * 
	 * § stockTypeId § stockTypeName § Số lượng § tiền trước thuế § Tiền thuế §
	 * Tiền sau thuế § Chiết khấu <lstSmartPhoneBO> <amount>200000</amount>
	 * <discountAmount>0</discountAmount> <quantity>2</quantity>
	 * <stockTypeId>6</stockTypeId> <stockTypeName>Thẻ cào</stockTypeName>
	 * <taxAmount>18182</taxAmount> <vat>10</vat> </lstSmartPhoneBO>
	 * 
	 * <lstSmartPhoneBO> <amount>200000</amount>
	 * <discountAmount>0</discountAmount> <quantity>2</quantity>
	 * <stockModelCode>TC100</stockModelCode> <stockModelId>120</stockModelId>
	 * <stockModelName>THE CAO MG 100</stockModelName>
	 * <taxAmount>18182</taxAmount> <vat>10</vat> </lstSmartPhoneBO>
	 * 
	 **/

	static class ViewHolder {
		TextView stockModelCode, quantity, amount, discountAmount, taxAmount, amountNotTax;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		SmartPhoneBO obj = arr.get(position);
		Log.d(Constant.TAG, "getView position: " + position + " " + obj);

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_report_staff_revenue_detail, viewGroup, false);
			holder = new ViewHolder();

			holder.stockModelCode = (TextView) mView.findViewById(R.id.stockModelCode);

			holder.quantity = (TextView) mView.findViewById(R.id.quantity);
			holder.discountAmount = (TextView) mView.findViewById(R.id.discountAmount);
			holder.amountNotTax = (TextView) mView.findViewById(R.id.amountNotTax);
			holder.taxAmount = (TextView) mView.findViewById(R.id.taxAmount);
			holder.amount = (TextView) mView.findViewById(R.id.amount);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.stockModelCode.setText(obj.getStockModelCode());

		holder.quantity.setText(StringUtils.formatMoney(obj.getQuantity().toString()));
		holder.discountAmount.setText(StringUtils.formatMoney(obj.getDiscountAmount().toString()));
		holder.amountNotTax.setText(StringUtils.formatMoney(obj.getAmountNotTax().toString()));

		holder.taxAmount.setText(StringUtils.formatMoney(obj.getTaxAmount().toString()));
		holder.amount.setText(StringUtils.formatMoney(obj.getAmount().toString()));

		return mView;
	}
}
