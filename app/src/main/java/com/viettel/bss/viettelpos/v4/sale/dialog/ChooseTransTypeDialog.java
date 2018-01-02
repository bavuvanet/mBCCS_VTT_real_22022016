package com.viettel.bss.viettelpos.v4.sale.dialog;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.adapter.TransTypeAdapter;

public class ChooseTransTypeDialog extends Dialog {
	private final List<FormBean> lstTransType;
	private final Context context;
	private final TextView tv;
    private TransTypeAdapter adapter;

	public ChooseTransTypeDialog(Context context, List<FormBean> lstTransType,
			TextView tv) {
		super(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
		this.tv = tv;
		this.context = context;
		this.lstTransType = lstTransType;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup_choose_trans_type);
		setCancelable(false);
		Button btnLeft = (Button) findViewById(R.id.btnLeft);
		Button btnRight = (Button) findViewById(R.id.btnRight);
		btnRight.setText(context.getString(R.string.btn_close));
		final CheckedTextView title = (CheckedTextView) findViewById(R.id.tvDialogTitle);
        ListView lv = (ListView) findViewById(R.id.lvItem);
		adapter = new TransTypeAdapter(lstTransType, context, title);
		lv.setAdapter(adapter);
		boolean allCheck = true;
		for (FormBean tmp : lstTransType) {
			if (!tmp.getChecked()) {
				allCheck = false;
				break;
			}
		}
		if (allCheck) {
			title.setChecked(true);

		} else {
			title.setChecked(false);
		}
		title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				title.setChecked(!title.isChecked());
				for (FormBean item : lstTransType) {
					item.setChecked(title.isChecked());
				}
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			}
		});

		btnLeft.setVisibility(View.GONE);
		btnRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean check = false;
				String text = "";
				for (FormBean bean : lstTransType) {
					if (bean.getChecked()) {
						check = true;
						text = text + bean.getName() + "; ";
					}
				}
				if (!check) {
					Toast.makeText(
							context,
							context.getString(R.string.choose_atleast_one_trans_type),
							Toast.LENGTH_SHORT).show();
					return;
				}
				text = text.substring(0, text.length() - 1);
				tv.setText(text);
				dismiss();
			}
		});

	}
}
