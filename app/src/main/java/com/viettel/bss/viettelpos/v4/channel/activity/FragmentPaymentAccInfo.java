package com.viettel.bss.viettelpos.v4.channel.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.PaymentAccInfoAdapter;
import com.viettel.bss.viettelpos.v4.channel.object.PaymentAccOJ;

public class FragmentPaymentAccInfo extends Fragment implements
		android.view.View.OnClickListener {
	private TextView txtPaymentRemain;
	private TextView txtPaymentMilestone;
	private TextView txtPaymentTotal;
	private ListView lvListPayment;
	private EditText edittextMoneyCharge;
	private Button btnCharge;
	private PaymentAccInfoAdapter paymentAccInfoAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mView = inflater.inflate(R.layout.layout_payment_acc_info,
				container, false);
		Unit(mView);
		return mView;

	}

	private void Unit(View v) {
		txtPaymentRemain = (TextView) v.findViewById(R.id.txt_payment_remain);
		txtPaymentMilestone = (TextView) v
				.findViewById(R.id.txt_payment_milestone);
		txtPaymentTotal = (TextView) v.findViewById(R.id.txt_payment_total);
		lvListPayment = (ListView) v.findViewById(R.id.lv_list_payment);
		edittextMoneyCharge = (EditText) v
				.findViewById(R.id.edittext_money_charge);
		btnCharge = (Button) v.findViewById(R.id.btnCharge);
		btnCharge.setOnClickListener(this);
		setData4Views();
	}

	private PaymentAccOJ paymentOJ;

	private void setData4Views() {
		// TODO Auto-generated method stub
		paymentOJ = new PaymentAccOJ(2000, 3000, 5000);
		txtPaymentRemain.setText(paymentOJ.getMoneyRemain() + "");
		txtPaymentMilestone.setText(paymentOJ.getMoneyMilestone() + "");
		txtPaymentTotal.setText(paymentOJ.getMoneyTotal() + "");
		paymentAccInfoAdapter = new PaymentAccInfoAdapter(paymentOJ,
				getActivity());

		lvListPayment.setAdapter(paymentAccInfoAdapter);
	}

	@Override
	public void onClick(View arg0) {

	}
}
