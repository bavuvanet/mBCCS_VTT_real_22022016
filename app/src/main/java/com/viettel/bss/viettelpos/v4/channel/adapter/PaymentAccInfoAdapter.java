package com.viettel.bss.viettelpos.v4.channel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.PaymentAccOJ;

public class PaymentAccInfoAdapter extends BaseAdapter {
	private final PaymentAccOJ paymentAccOJ;
	private final Context mContext;

	public PaymentAccInfoAdapter(PaymentAccOJ paymentAccOJ, Context context) {
		this.paymentAccOJ = paymentAccOJ;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return paymentAccOJ.getArrMProduct().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return paymentAccOJ.getArrMProduct().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ViewHolder {
		TextView txtName;
		TextView txtNum;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// return null;
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_payment, arg2, false);
			holder = new ViewHolder();
			holder.txtName = (TextView) mView.findViewById(R.id.txt_name);
			holder.txtNum = (TextView) mView.findViewById(R.id.txt_num);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.txtName.setText(paymentAccOJ.getArrMProduct().get(arg0)
				.getProduct().getName()
				+ "");
		holder.txtNum.setText(paymentAccOJ.getArrMProduct().get(arg0).getNum()
				+ "");
		return mView;
	}

}
