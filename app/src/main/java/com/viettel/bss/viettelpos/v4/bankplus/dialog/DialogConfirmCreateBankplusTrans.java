package com.viettel.bss.viettelpos.v4.bankplus.dialog;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customview.MyTagHandler;

public class DialogConfirmCreateBankplusTrans extends DialogFragment {

	private View imgClose, btnPayment, btnCloseConfirm;
	private TextView tvPayment;
	private TextView tvTitle;
	private ImageView imgDetail;

	private static OnClickListener onPaymentClick;

	public static DialogConfirmCreateBankplusTrans newInstance(String title,
			String confirmContent, int resourceImage, OnClickListener onClick) {
		DialogConfirmCreateBankplusTrans f = new DialogConfirmCreateBankplusTrans();
		Bundle args = new Bundle();

		args.putString("title", title);
		args.putString("confirmContent", confirmContent);
		args.putInt("resourceImage", resourceImage);

		f.setArguments(args);
		onPaymentClick = onClick;
		return f;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getDialog().getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pick a style based on the num.
		setStyle(DialogFragment.STYLE_NO_TITLE,
				android.R.style.Theme_Holo_Light_Dialog);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_confirm_payment, container, false);

		String message = getArguments().getString("confirmContent");
		String title = getArguments().getString("title");
		int resourceImage = getArguments().getInt("resourceImage");

		tvPayment = (TextView) v.findViewById(R.id.tvPayment);
		tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvPayment.setText(Html.fromHtml(message), TextView.BufferType.SPANNABLE);
		imgDetail = (ImageView) v.findViewById(R.id.imgDetail);

		tvPayment.setText(Html.fromHtml(message, null, new MyTagHandler()));
		imgClose = v.findViewById(R.id.imgClose);
		btnCloseConfirm = v.findViewById(R.id.btnCloseConfirm);
		imgClose.setOnClickListener(onClick);
		btnCloseConfirm.setOnClickListener(onClick);
		tvTitle.setText(title);
		btnPayment = (TextView) v.findViewById(R.id.btnPayment);
		if (onPaymentClick == null) {
			btnPayment.setVisibility(View.GONE);
		} else {
			btnPayment.setVisibility(View.VISIBLE);
			btnPayment.setOnClickListener(onClick);
		}
		if (resourceImage != 0) {
			imgDetail.setVisibility(View.VISIBLE);
			imgDetail.setBackgroundResource(resourceImage);
		} else {
			imgDetail.setVisibility(View.GONE);
		}
		return v;
	}

	OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgClose:
				dismiss();
				break;
			case R.id.btnCloseConfirm:
				dismiss();
				break;
			case R.id.btnPayment:
				dismiss();
				onPaymentClick.onClick(v);
				break;
			default:
				break;
			}
		}
	};
}
