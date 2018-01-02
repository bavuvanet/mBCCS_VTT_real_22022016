package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SaleTransBankplus;

public class SaleTransBankplusAdapter extends BaseAdapter {
	private final Context context;
	private List<SaleTransBankplus> lsTransBankplus = new ArrayList<>();
	private final OnChangeCheckedStateBplus onChangeCheckedState;

	public interface OnChangeCheckedStateBplus {
		void onChangeChecked(SaleTransBankplus saleTrans);
	}

	public SaleTransBankplusAdapter(Context context, List<SaleTransBankplus> lstData,
			OnChangeCheckedStateBplus onChangeCheckedState) {
		this.context = context;
		this.lsTransBankplus = lstData;
		
		this.onChangeCheckedState = onChangeCheckedState;
	}

	@Override
	public int getCount() {
		if (lsTransBankplus == null) {
			return 0;
		} else {
			return lsTransBankplus.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return lsTransBankplus.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvCode;
		TextView tvsodienthoai;
		TextView tvbalance;
		TextView tvfee;
		TextView tvcreateDate;
		TextView tvdateApprove;
		CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.create_checkout_trans_row,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tvCode = (TextView) row.findViewById(R.id.tvCode);

			holder.tvsodienthoai = (TextView) row.findViewById(R.id.tvsodienthoai);
			holder.tvbalance = (TextView) row.findViewById(R.id.tvbalance);
			holder.tvfee = (TextView) row.findViewById(R.id.tvfee);
			holder.tvcreateDate = (TextView) row.findViewById(R.id.tvcreateDate);
			holder.tvdateApprove = (TextView) row
					.findViewById(R.id.tvdateApprove);
			holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		
//		<string name="sdtbplus">Sá»‘ Ä‘iá»‡n thoáº¡i : </string>
//		<string name="sotienbplus">Sá»‘ tiá»�n :</string>
//		<string name="createDatebplus">NgÃ y láº­p : </string>
//		<string name="dateApprovebplus">NgÃ y duyá»‡t : </string>
		
		final SaleTransBankplus item = lsTransBankplus.get(position);
		if (item != null) {
			holder.tvCode.setText(item.getSaleTransBankPlusID());
			holder.tvsodienthoai.setText(context.getResources().getString(
					R.string.sdtbplus)
					+ " " + item.getIsdnBankPlus());
			holder.tvbalance.setText(context.getResources().getString(
					R.string.sotienbplus)
					+ ": " + StringUtils.formatMoney(item.getBalance()));
			if (!CommonActivity.isNullOrEmpty(item.getFeeAmount())) {
				holder.tvfee.setText(context.getResources().getString(
						R.string.sotienamountfee)
						+ ": " + StringUtils.formatAbsMoney(item.getFeeAmount()));
			} else {
				holder.tvfee.setText(context.getResources().getString(
						R.string.sotienamountfee)
						+ ": 0");
			}
			
			holder.tvcreateDate.setText(context.getResources().getString(
					R.string.createDatebplus)
					+ ": " + item.getCreateDate());
			holder.tvdateApprove.setText(context.getResources().getString(
					R.string.trangthaikhongbatbuoc)
					+ ": " + item.getStatusName());
			if(!CommonActivity.isNullOrEmpty(item.getStatus())){
				if("0".equals(item.getStatus()) || "1".equals(item.getStatus())){
					holder.checkBox.setVisibility(View.VISIBLE);
				}else{
					holder.checkBox.setVisibility(View.GONE);
				}
			}
			
			
			holder.checkBox.setChecked(item.isChecked());
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onChangeCheckedState.onChangeChecked(item);
				}
			});
		}
		return row;
	}

	
}
