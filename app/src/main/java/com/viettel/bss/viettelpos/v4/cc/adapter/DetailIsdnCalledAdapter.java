package com.viettel.bss.viettelpos.v4.cc.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.cc.object.DetailCalledDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter;

import java.util.List;

public class DetailIsdnCalledAdapter extends BaseAdapter {

	private final String logTag = StockModelAdapter.class.getName();
	private final Context context;
	private List<DetailCalledDTO> lstData;
	private final OnRemoveIsdn onRemoveIsdn;

    public interface OnRemoveIsdn {
		void onRemoveIsdn(DetailCalledDTO item);
	}

	public interface OnEditIsdn {
		void onEditIsdn(DetailCalledDTO item);
	}

	public List<DetailCalledDTO> getLstData() {
		return lstData;
	}

	public void setLstData(List<DetailCalledDTO> lstData) {
		this.lstData = lstData;
	}

	public DetailIsdnCalledAdapter(Context context,
			List<DetailCalledDTO> lstData, OnRemoveIsdn onRemoveIsdn,
			OnEditIsdn onEditIsdn) {
		this.context = context;
        this.onRemoveIsdn = onRemoveIsdn;
		this.lstData = lstData;

	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		} else {
			return lstData.size();
		}
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

	private static class ViewHoder {
		EditText tvIsdn;
		TextView tvNumCall;
		TextView tvNumSms;
		TextView tvLastCall;
		View imgDelete;
		View imgOk;
		View imgFail;
		View lnNumCall;
		View lnNumSms;
		View lnLastRequest;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		// final ViewHoder holder;
		// if (mView == null) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		mView = inflater.inflate(R.layout.item_sub_usually_call, arg2, false);
		// holder = new ViewHoder();
		// holder.imgDelete = mView.findViewById(R.id.imgDelete);
		// holder.lnNumCall = mView.findViewById(R.id.lnNumCall);
		// holder.lnNumSms = mView.findViewById(R.id.lnNumSms);
		// holder.lnLastRequest = mView.findViewById(R.id.lnLastRequest);
		// holder.tvIsdn = (EditText) mView.findViewById(R.id.tvIsdn);
		// holder.tvNumCall = (TextView) mView.findViewById(R.id.tvNumCall);
		// holder.tvNumSms = (TextView) mView.findViewById(R.id.tvNumSms);
		// holder.tvLastCall = (TextView) mView.findViewById(R.id.tvLastCall);
		// holder.imgFail = mView.findViewById(R.id.imgFail);
		// holder.imgOk = mView.findViewById(R.id.imgOk);
		final View imgDelete = mView.findViewById(R.id.imgDelete);
		final View lnNumCall = mView.findViewById(R.id.lnNumCall);
		final View lnNumSms = mView.findViewById(R.id.lnNumSms);
		final View lnLastRequest = mView.findViewById(R.id.lnLastRequest);
		final EditText tvIsdn = (EditText) mView.findViewById(R.id.tvIsdn);
		final TextView tvNumCall = (TextView) mView
				.findViewById(R.id.tvNumCall);
		final TextView tvNumSms = (TextView) mView.findViewById(R.id.tvNumSms);
		final TextView tvLastCall = (TextView) mView
				.findViewById(R.id.tvLastCall);
		final View imgFail = mView.findViewById(R.id.imgFail);
		final View imgOk = mView.findViewById(R.id.imgOk);
		// mView.setTag(holder);
		// } else {
		// holder = (ViewHoder) mView.getTag();
		// }
		final DetailCalledDTO item = lstData.get(position);

		if (CommonActivity.isNullOrEmpty(item.getIsdnCalled())
				|| !item.isCheck()) {
			// holder.lnLastRequest.setVisibility(View.GONE);
			// holder.lnNumCall.setVisibility(View.GONE);
			// holder.lnNumSms.setVisibility(View.GONE);
			// holder.imgFail.setVisibility(View.GONE);
			// holder.imgOk.setVisibility(View.GONE);
			lnLastRequest.setVisibility(View.GONE);
			lnNumCall.setVisibility(View.GONE);
			lnNumSms.setVisibility(View.GONE);
			imgFail.setVisibility(View.GONE);
			imgOk.setVisibility(View.GONE);
		} else {
			if (CommonActivity.isNullOrEmpty(item.getLsDate())) {
				// holder.imgFail.setVisibility(View.VISIBLE);
				// holder.lnLastRequest.setVisibility(View.GONE);
				// holder.lnNumCall.setVisibility(View.GONE);
				// holder.lnNumSms.setVisibility(View.GONE);
				// holder.imgOk.setVisibility(View.GONE);
				imgFail.setVisibility(View.VISIBLE);
				lnLastRequest.setVisibility(View.GONE);
				lnNumCall.setVisibility(View.GONE);
				lnNumSms.setVisibility(View.GONE);
				imgOk.setVisibility(View.GONE);
			} else {
				// holder.lnLastRequest.setVisibility(View.VISIBLE);
				// holder.lnNumCall.setVisibility(View.VISIBLE);
				// holder.lnNumSms.setVisibility(View.VISIBLE);
				// holder.imgOk.setVisibility(View.VISIBLE);
				// holder.imgFail.setVisibility(View.GONE);
				// holder.tvNumCall.setText((item.getNumCallGt6() + item
				// .getNumCallLt6()) + "");
				// holder.tvNumSms.setText(item.getNumSms() + "");
				// holder.tvLastCall.setText(item.getLsDate() + "");
				lnLastRequest.setVisibility(View.VISIBLE);
				lnNumCall.setVisibility(View.VISIBLE);
				lnNumSms.setVisibility(View.VISIBLE);
				imgOk.setVisibility(View.VISIBLE);
				imgFail.setVisibility(View.GONE);
				tvNumCall.setText((item.getNumCallGt6() + item.getNumCallLt6())
						+ "");
				tvNumSms.setText(item.getNumSms() + "");
				tvLastCall.setText(item.getLsDate() + "");
			}

		}
		tvIsdn.setText(item.getIsdnCalled());
		imgDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// lstData.remove(item);
				// notifyDataSetChanged();
				onRemoveIsdn.onRemoveIsdn(item);
			}
		});
		tvIsdn.setInputType(InputType.TYPE_CLASS_PHONE);
		tvIsdn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				item.setIsdnCalled(arg0.toString());
				item.setBgDate(null);
				item.setLsDate(null);
				item.setNumCallGt6(0);
				item.setNumCallLt6(0);
				item.setNumSms(0);
				lnLastRequest.setVisibility(View.GONE);
				lnNumCall.setVisibility(View.GONE);
				lnNumSms.setVisibility(View.GONE);
				imgFail.setVisibility(View.GONE);
				imgOk.setVisibility(View.GONE);

			}
		});
		return mView;
	}
}
