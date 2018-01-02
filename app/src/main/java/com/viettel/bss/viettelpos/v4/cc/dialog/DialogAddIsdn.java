package com.viettel.bss.viettelpos.v4.cc.dialog;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.cc.adapter.DetailIsdnCalledAdapter;
import com.viettel.bss.viettelpos.v4.cc.object.DetailCalledDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class DialogAddIsdn extends Dialog {
	private EditText edtIsdn;
	private List<DetailCalledDTO> lstIsdn;
	private Context context;
	private DetailIsdnCalledAdapter adapter;
    private DetailCalledDTO item;

	public DialogAddIsdn(Context context) {
		super(context);
	}

	public DialogAddIsdn(Context context, List<DetailCalledDTO> lstIsdn,
			ListView listView, DetailIsdnCalledAdapter adapter) {
		super(context);
		this.adapter = adapter;
		this.context = context;
        this.lstIsdn = lstIsdn;
	}

	public List<DetailCalledDTO> getLstIsdn() {
		return lstIsdn;
	}

	public void setLstIsdn(List<DetailCalledDTO> lstIsdn) {
		this.lstIsdn = lstIsdn;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_add_edittext_dialog);
		edtIsdn = (EditText) findViewById(R.id.edtInput);

		findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String msg = validateEdtIsdn();
				if (!CommonActivity.isNullOrEmpty(msg)) {
					CommonActivity.createAlertDialog(context, msg,
							context.getString(R.string.app_name)).show();
					edtIsdn.requestFocus();

					return;

				}
				DetailCalledDTO dto;
				String newIsdn = CommonActivity.checkStardardIsdn(edtIsdn
						.getText().toString().trim());
				if (item != null) {
					item.setIsdnCalled(newIsdn);
				} else {
					dto = new DetailCalledDTO();
					dto.setIsdnCalled(newIsdn);
					lstIsdn.add(dto);
				}
				adapter.notifyDataSetChanged();
				CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
				dismiss();

			}
		});
		findViewById(R.id.btnCancel).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dismiss();

					}
				});
	}

	private String validateEdtIsdn() {
		if (CommonActivity.isNullOrEmpty(edtIsdn.getText().toString().trim())) {
			return context.getString(R.string.must_input_isdn);
		}
		if (!CommonActivity.validateIsdn(edtIsdn.getText().toString().trim())) {
			return context.getString(R.string.phone_number_invalid_format);
		}
		String isdn = CommonActivity.checkStardardIsdn(edtIsdn.getText()
				.toString().trim());

		for (DetailCalledDTO item : lstIsdn) {
			if (!CommonActivity.isNullOrEmpty(item.getIsdnCalled())
					&& item.getIsdnCalled().equals(isdn)) {
				return context.getString(R.string.isdn_is_exists);
			}
		}
		return "";
	}

	public void setIsdnText(DetailCalledDTO item) {
		this.item = item;
		edtIsdn.setText("");
	}
}
