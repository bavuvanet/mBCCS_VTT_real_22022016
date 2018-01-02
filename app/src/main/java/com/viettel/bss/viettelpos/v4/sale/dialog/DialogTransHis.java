package com.viettel.bss.viettelpos.v4.sale.dialog;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterTransHisDetail;
import com.viettel.bss.viettelpos.v4.sale.object.AccountBookBankplusDTO;

public class DialogTransHis extends Dialog {
	private final List<AccountBookBankplusDTO> lstData;
	private final Context context;

	public DialogTransHis(Context context, List<AccountBookBankplusDTO> lstData) {
		super(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
		this.context = context;
		this.lstData = lstData;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_single_list_view);
		setCancelable(false);
		Button btnClose = (Button) findViewById(R.id.btnClose);
		TextView title = (TextView) findViewById(R.id.tvDialogTitle);
		title.setText(R.string.trans_his_info);
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		ListView lv = (ListView) findViewById(R.id.lvTransHis);
		AdapterTransHisDetail adapter = new AdapterTransHisDetail(context,
				lstData);
		lv.setAdapter(adapter);
	}

}
