package com.viettel.bss.viettelpos.v4.cc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.cc.asynctask.AsyncUpdateGiftGiving;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.cc.object.SubscriberCareBean;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.object.FormBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogUpdateGift extends Dialog {
	private Context context;
	private SubscriberCareBean bean;
	private OnPostExecute<CCOutput> onPostExecute;
	private TextView tvCusName;
	private TextView tvCusPhone;
	private TextView tvGiftName;
	private TextView tvGiftType;
	private TextView tvGiftDate;
	private TextView tvGiftAllowType;
	private TextView tvCareType;
	private Spinner spnGiftStatus;
	private Spinner spnReason;
	private EditText edtNote;
	private EditText edtCusName;

	public DialogUpdateGift(Context context, SubscriberCareBean bean,
							OnPostExecute<CCOutput> onPostExecute) {
		super(context, android.R.style.Theme_Light);
		this.onPostExecute = onPostExecute;
		this.bean = bean;
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_dialog_update_gift);
		tvCusName = (TextView) findViewById(R.id.tvCusName);
		tvCusPhone = (TextView) findViewById(R.id.tvCusPhone);
		tvGiftName = (TextView) findViewById(R.id.tvGiftName);
		tvGiftType = (TextView) findViewById(R.id.tvGiftType);
		tvGiftDate = (TextView) findViewById(R.id.tvGiftDate);
		tvGiftAllowType = (TextView) findViewById(R.id.tvGiftAllowType);
		tvCareType = (TextView) findViewById(R.id.tvCareType);
		edtNote = (EditText) findViewById(R.id.edtNote);
		edtCusName = (EditText) findViewById(R.id.edtCusName);
		spnGiftStatus = (Spinner) findViewById(R.id.spnGiftStatus);
		spnReason = (Spinner) findViewById(R.id.spnReason);
		tvCusName.setText(bean.getCustName());
		edtCusName.setText(bean.getCustName());
		tvCusPhone.setText(bean.getIsdn());
		Date giftDate = DateTimeUtils.convertDateFromSoap(bean.getGiftDate());
		tvGiftDate.setText(context.getString(R.string.update_date_plan,
				DateTimeUtils.convertDateTimeToString(giftDate, "dd/MM/yyyy")));
		tvGiftName.setText(context.getString(R.string.gift_name,
				bean.getGiftName()));
		tvGiftType.setText(context.getString(R.string.gift_type,
				bean.getGiftType()));
		tvGiftAllowType.setText(context.getString(R.string.gift_allow_type,
				bean.getGiftAllowType()));
		String careType = bean.getCareType();
		String careTypeName = context.getString(R.string.gift_birthday);
		if ("2".equals(careType)) {
			careTypeName = context.getString(R.string.gift_local);
		}
		tvCareType.setText(context.getString(R.string.care_type, careTypeName));

		final List<FormBean> lstStatus = new ArrayList<FormBean>();
		FormBean success = new FormBean();
		success.setId("1");
		success.setName(context.getString(R.string.updated_gift));
		lstStatus.add(success);

		FormBean fail = new FormBean();
		fail.setId("0");
		fail.setName(context.getString(R.string.update_gift_fail));
		lstStatus.add(fail);
		ArrayAdapter<FormBean> statusAdapter = new ArrayAdapter<FormBean>(
				context, R.layout.spinner_item, lstStatus);
		spnGiftStatus.setAdapter(statusAdapter);
		spnGiftStatus.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				FormBean selected = lstStatus.get(arg2);
				if ("1".equals(selected.getId())) {

					findViewById(R.id.lnReason).setVisibility(View.GONE);
				} else {
					findViewById(R.id.lnReason).setVisibility(View.VISIBLE);
				}
				bean.setStatus(selected.getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		initReasonSpinner();
		findViewById(R.id.btnUpdateGift).setOnClickListener(onclick);
		findViewById(R.id.btnCancel).setOnClickListener(onclick);

	}

	private void initReasonSpinner() {

		ArrayList<FormBean> lstReason = new ArrayList<FormBean>();

		FormBean first = new FormBean();

		first.setName(context.getString(R.string.txt_select_reason));
		lstReason.add(first);

		FormBean form1 = new FormBean();
		form1.setId("LL");
		form1.setName(context.getString(R.string.gift_reason_ll));
		lstReason.add(form1);
		FormBean form2 = new FormBean();
		form2.setId("HL");
		form2.setName(context.getString(R.string.gift_reason_hl));
		lstReason.add(form2);

		FormBean form3 = new FormBean();
		form3.setId("GQ");
		form3.setName(context.getString(R.string.gift_reason_gq));
		lstReason.add(form3);

		FormBean form4 = new FormBean();
		form4.setId("LQ");
		form4.setName(context.getString(R.string.gift_reason_lq));
		lstReason.add(form4);

		FormBean form5 = new FormBean();
		form5.setId("KH");
		form5.setName(context.getString(R.string.gift_reason_kh));
		lstReason.add(form5);
		ArrayAdapter<FormBean> adapter = new ArrayAdapter<FormBean>(context,
				R.layout.spinner_item, lstReason);
		spnReason.setAdapter(adapter);
	}

	private android.view.View.OnClickListener onclick = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
				case R.id.btnCancel:
					dismiss();
					break;
				case R.id.btnUpdateGift:
					FormBean status = (FormBean) spnGiftStatus.getSelectedItem();
					FormBean reason = (FormBean) spnReason.getSelectedItem();
					String custName = edtCusName.getText().toString().trim();
					if (CommonActivity.isNullOrEmpty(custName)) {
						CommonActivity.createAlertDialog(
								MainActivity.getInstance(),
								R.string.cus_receive_real_empty, R.string.app_name)
								.show();
						edtCusName.requestFocus();
						return;
					}
					if ("0".equals(status.getId())) {
						if (CommonActivity.isNullOrEmpty(reason.getId())) {
							CommonActivity.createAlertDialog(
									MainActivity.getInstance(),
									R.string.gift_must_input_reason,
									R.string.app_name).show();
							return;
						}
					}
					String note = edtNote.getText().toString().trim();
					if (CommonActivity.isNullOrEmpty(note)) {
						CommonActivity.createAlertDialog(
								MainActivity.getInstance(),
								R.string.gift_must_input_note, R.string.app_name)
								.show();
						edtNote.requestFocus();
						return;
					}

				CommonActivity.createDialog(MainActivity.getInstance(),
						context.getString(R.string.update_gift_confirm),
						context.getString(R.string.app_name),
						context.getString(R.string.cancel),
						context.getString(R.string.ok), null,confirmClick )
						.show();
				break;
			default:
				break;
			}

		}
	};
	private android.view.View.OnClickListener confirmClick = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			FormBean reason = (FormBean) spnReason.getSelectedItem();
			String reasonNotGift = reason.getId();
			AsyncUpdateGiftGiving asy = new AsyncUpdateGiftGiving(bean, edtNote
					.getText().toString().trim(), reasonNotGift, edtCusName.getText().toString().trim(), onPostExecute,
					context);
			asy.execute();
		}
	};

}
