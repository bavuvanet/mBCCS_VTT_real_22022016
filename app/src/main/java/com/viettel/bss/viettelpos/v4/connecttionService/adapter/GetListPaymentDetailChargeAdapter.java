package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RequestBeans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetListPaymentDetailChargeAdapter extends BaseAdapter {
	private final Context mContext;
	private ArrayList<PaymentPrePaidDetailBeans> arrPaymentPrePaidDetailBeans = new ArrayList<>();

	public interface OnCancelRequest {
		void onCancelRequest(RequestBeans requestBeans);
	}

	public GetListPaymentDetailChargeAdapter(
			ArrayList<PaymentPrePaidDetailBeans> arr, Context context) {
		this.arrPaymentPrePaidDetailBeans = arr;
		mContext = context;
	}

	@Override
	public int getCount() {
		if (arrPaymentPrePaidDetailBeans != null) {
			return arrPaymentPrePaidDetailBeans.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		if (arrPaymentPrePaidDetailBeans != null
				&& !arrPaymentPrePaidDetailBeans.isEmpty()) {
			return arrPaymentPrePaidDetailBeans.get(arg0);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private class ViewHolder {
		TextView txtmonthPrePaid;
		TextView txtpromAmount;
		TextView txttongsotiencuoc;
		TextView txtmoneyUnit;
		TextView txtdiscount;
	}

	@Override
	public View getView(int possition, View view, ViewGroup arg2) {

		View mView = view;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater layoutInflater = ((Activity) mContext)
					.getLayoutInflater();
			mView = layoutInflater.inflate(R.layout.connection_precharge_item,
					arg2, false);
			holder = new ViewHolder();
			holder.txtmonthPrePaid = (TextView) mView
					.findViewById(R.id.txtmonthPrePaid);
			holder.txtpromAmount = (TextView) mView
					.findViewById(R.id.txtpromAmount);
			holder.txttongsotiencuoc = (TextView) mView
					.findViewById(R.id.txttongsotiencuoc);
			holder.txtmoneyUnit = (TextView) mView
					.findViewById(R.id.txtmoneyUnit);
			holder.txtdiscount = (TextView) mView
					.findViewById(R.id.txtdiscount);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final PaymentPrePaidDetailBeans paymentPrePaidDetailBeans = arrPaymentPrePaidDetailBeans
				.get(possition);
		holder.txtmonthPrePaid.setText(paymentPrePaidDetailBeans.getSubMonth());
		holder.txtpromAmount.setText(paymentPrePaidDetailBeans.getPromAmount());
		holder.txttongsotiencuoc.setText(paymentPrePaidDetailBeans
				.getTotalMoney());
		holder.txtmoneyUnit.setText(paymentPrePaidDetailBeans.getMoneyUnit());
		holder.txtdiscount.setText(mContext.getString(R.string.giamtrucuoctu)
				+ " " + paymentPrePaidDetailBeans.getStartMonth() + " "
				+ mContext.getString(R.string.denthang) + " "
				+ paymentPrePaidDetailBeans.getEndMonth());
		return mView;
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			((Activity) mContext).finish();
			MainActivity.getInstance().finish();

		}
	};

}
