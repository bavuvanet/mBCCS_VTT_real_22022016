package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;


public class SubGoodsNewInfoAdapter extends BaseAdapter {
	private final Context context;
	private List<SubGoods> subGoods = new ArrayList();
	private final OnChangeGoodsNewInfo onChange;

	public interface OnChangeGoodsNewInfo {
		void onChange(SubGoods goodsNew, int position);
	}

	public SubGoodsNewInfoAdapter(Context context, List<SubGoods> lstData, OnChangeGoodsNewInfo onChange) {
		this.context = context;
		this.subGoods = lstData;
		this.onChange = onChange;
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
		TextView tv_saleServiceModelName;
		TextView tv_stockModelCodeName;
		TextView tv_supplyMethod;
		TextView tv_supplyProgram;
		TextView tv_programMonth;
		TextView tv_payAmount;
		TextView tv_payMethod;
		TextView tv_serial;
		LinearLayout ll_wholeRow;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_subscriber_goods_new_row,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tv_saleServiceModelName = (TextView) row.findViewById(R.id.tv_saleServiceModelName);
			holder.tv_stockModelCodeName = (TextView) row.findViewById(R.id.tv_stockModelCodeName);
			holder.tv_supplyMethod = (TextView) row.findViewById(R.id.tv_supplyMethod);
			holder.tv_supplyProgram = (TextView) row.findViewById(R.id.tv_supplyProgram);
			holder.tv_programMonth = (TextView) row.findViewById(R.id.tv_programMonth);
			holder.tv_payAmount = (TextView) row.findViewById(R.id.tv_payAmount);
			holder.tv_payMethod = (TextView) row.findViewById(R.id.tv_payMethod);
			holder.tv_serial = (TextView) row.findViewById(R.id.tv_serial);
			holder.ll_wholeRow = (LinearLayout) row.findViewById(R.id.ll_wholeRow);
			

			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		
		final SubGoods item = subGoods.get(position);
		if (item != null) {
			holder.tv_saleServiceModelName.setText(item.getSaleServiceModelName());
			holder.tv_stockModelCodeName.setText(item.getStockModelName());
			holder.tv_supplyMethod.setText(context.getString(R.string.supplyMethod) 
					+ ": " + (item.getSupplyMethodName() != null ? item.getSupplyMethodName() : ""));
			holder.tv_supplyProgram.setText(context.getString(R.string.supplyProgram) 
					+ ": " + (item.getSupplyProgramName() != null ? item.getSupplyProgramName() : ""));
			holder.tv_programMonth.setText(context.getString(R.string.programMonth) 
					+ ": " + (item.getProgramMonth() != null ? item.getProgramMonth() : ""));
			holder.tv_payAmount.setText(context.getString(R.string.payAmount) 
					+ ": " + (item.getPayAmount() != null ? item.getPayAmount() : ""));
			holder.tv_payMethod.setText(context.getString(R.string.payMethod) 
					+ ": " + (item.getPayMethod() != null ? item.getPayMethodName() : ""));
			holder.tv_serial.setText(context.getString(R.string.serialNoSpace) 
					+ ": " + (item.getSerial() != null ? item.getSerial() : ""));
			
			holder.ll_wholeRow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					onChange.onChange(item, position);
				}
			});
		}
		
		return row;
	}

	
}

