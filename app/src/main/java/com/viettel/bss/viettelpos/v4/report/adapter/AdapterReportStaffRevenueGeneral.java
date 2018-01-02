package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.report.object.SmartPhoneBO;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterReportStaffRevenueGeneral extends BaseAdapter {
	private final Context mContext;
	private final List<SmartPhoneBO> arr;

	public AdapterReportStaffRevenueGeneral(Context context, List<SmartPhoneBO> _arr) {
		this.mContext = context;
		this.arr = _arr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
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
	
	/**
	§  stockTypeId
	§  stockTypeName
	§  Số lượng
	§  tiền trước thuế
	§  Tiền thuế
	§  Tiền sau thuế
	§  Chiết khấu
	 <lstSmartPhoneBO>
       <amount>200000</amount>
       <discountAmount>0</discountAmount>
       <quantity>2</quantity>
       <stockTypeId>6</stockTypeId>
       <stockTypeName>Thẻ cào</stockTypeName>
       <taxAmount>18182</taxAmount>
       <vat>10</vat>
	</lstSmartPhoneBO>
	 **/

	static class ViewHolder {
		TextView stockTypeName, quantity, amount, discountAmount, taxAmount, amountNotTax;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		SmartPhoneBO obj = arr.get(position);
		Log.d(Constant.TAG, "getView position: " + position + " "+ obj);

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_report_staff_revenue_general, viewGroup, false);
			holder = new ViewHolder();
			
			holder.stockTypeName = (TextView) mView.findViewById(R.id.stockTypeName); 
			holder.quantity = (TextView) mView.findViewById(R.id.quantity);
			holder.discountAmount = (TextView) mView.findViewById(R.id.discountAmount);
			holder.amountNotTax = (TextView) mView.findViewById(R.id.amountNotTax); 
			holder.taxAmount = (TextView) mView.findViewById(R.id.taxAmount);
			holder.amount = (TextView) mView.findViewById(R.id.amount);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		if (position != 0 && obj.getStockTypeName().equalsIgnoreCase(mContext.getString(R.string.sum_money))) {
			holder.stockTypeName.setTypeface(null, Typeface.BOLD);
			holder.quantity.setTypeface(null, Typeface.BOLD);
			holder.discountAmount.setTypeface(null, Typeface.BOLD);
			holder.amountNotTax.setTypeface(null, Typeface.BOLD);
			holder.taxAmount.setTypeface(null, Typeface.BOLD);
			holder.amount.setTypeface(null, Typeface.BOLD);
			 
		} else {
			holder.stockTypeName.setTypeface(null, Typeface.NORMAL);
			holder.quantity.setTypeface(null, Typeface.NORMAL);
			holder.discountAmount.setTypeface(null, Typeface.NORMAL);
			holder.amountNotTax.setTypeface(null, Typeface.NORMAL);
			holder.taxAmount.setTypeface(null, Typeface.NORMAL);
			holder.amount.setTypeface(null, Typeface.NORMAL);
		}
		
		
		
		holder.stockTypeName.setText(obj.getStockTypeName()); 
		holder.quantity.setText(obj.getQuantity().toString());
		holder.discountAmount.setText(StringUtils.formatMoney(obj.getDiscountAmount().toString()));
		holder.amountNotTax.setText(StringUtils.formatMoney(obj.getAmountNotTax().toString()));
		holder.taxAmount.setText(StringUtils.formatMoney(obj.getTaxAmount().toString()));
		holder.amount.setText(StringUtils.formatMoney(obj.getAmount().toString()));
		
		return mView;
	}
}
