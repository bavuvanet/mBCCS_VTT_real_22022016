package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.AccountBookBankplusDTO;

public class AdapterTransHisDetail extends BaseAdapter {

	private final Context context;
	private final List<AccountBookBankplusDTO> lstData;

	public AdapterTransHisDetail(Context context,
			List<AccountBookBankplusDTO> lstData) {

		this.context = context;
		this.lstData = lstData;

	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		}
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (CommonActivity.isNullOrEmpty(lstData)) {
			return null;
		}
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolders {
		public TextView tvRequestTypeName;
		public TextView tvAmountRequest;
		public TextView tvOpeningBalance;
		public TextView tvClosingBalance;
		public TextView tvCreateDate;
		public TextView tvRequestId;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHolders holder;
		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.bank_trans_his_item, viewGroup,
					false);
			holder = new ViewHolders();
			holder.tvRequestTypeName = (TextView) row
					.findViewById(R.id.tvRequestTypeName);
			holder.tvRequestId = (TextView) row.findViewById(R.id.tvRequestId);
			holder.tvAmountRequest = (TextView) row
					.findViewById(R.id.tvAmountRequest);
			holder.tvOpeningBalance = (TextView) row
					.findViewById(R.id.tvOpeningBalance);
			holder.tvClosingBalance = (TextView) row
					.findViewById(R.id.tvClosingBalance);
			holder.tvCreateDate = (TextView) row
					.findViewById(R.id.tvCreateDate);
			row.setTag(holder);
		} else {
			holder = (ViewHolders) row.getTag();
		}

		final AccountBookBankplusDTO item = lstData.get(position);
		holder.tvAmountRequest.setText(context.getString(
				R.string.AmountRequest,
				StringUtils.formatMoney(item.getAmountRequest())));
		String tmp = (position + 1) + "/" + lstData.size() +".";
		holder.tvRequestTypeName.setText(tmp + " " + item.getRequestTypeName());

		holder.tvOpeningBalance.setText(context.getString(
				R.string.OpeningBalance,
				StringUtils.formatMoney(item.getOpeningBalance())));
		holder.tvClosingBalance.setText(context.getString(
				R.string.ClosingBalances,
				StringUtils.formatMoney(item.getClosingBalance())));
		Date createDate = DateTimeUtils.convertDateFromSoap(item
				.getCreateDate());
		holder.tvCreateDate.setText(context.getString(R.string.createTransDate,
				DateTimeUtils.convertDateTimeToString(createDate,
						"dd/MM/yyyy HH:mm:ss")));
		holder.tvRequestId.setText(context.getString(R.string.request_id,
				item.getRequestId()));
		return row;
	}

}
