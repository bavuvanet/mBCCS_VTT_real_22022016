package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChooseSubType extends GPSTracker implements
		OnItemClickListener, View.OnClickListener {

	private EditText edtsearch;
	private ListView lvItem;
	private ArrayAdapter<SubTypeBeans> adapter;
	private ArrayList<SubTypeBeans> lstData = new ArrayList<SubTypeBeans>();
	private String productCode;
	private String serviceType;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.connectionmobile_layout_lst_pakage);
        ButterKnife.bind(this);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			lstData = (ArrayList<SubTypeBeans>) b
					.getSerializable("lstSubTypeKey");
			serviceType = b.getString("serviceTypeKey");
			productCode = b.getString("productCodeKey");

		}
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.chonloaithuebao));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		EditText edtsearch = (EditText) findViewById(R.id.edtsearch);
		edtsearch.setHint(R.string.nhap_loai_thue_bao_tim_kiem);
		TextView tvLabel = (TextView) findViewById(R.id.txtinfo);
		tvLabel.setText(R.string.ds_loai_thue_bao);
		unit();
		if (CommonActivity.isNullOrEmpty(lstData)) {
			GetListSubType asy = new GetListSubType(this);
			asy.execute();
		} else {
			// adapter = new GetHTHMAdapter(arrHthmBeans, this);
			adapter = new ArrayAdapter<SubTypeBeans>(this,
					R.layout.spinner_item,R.id.spinner_value, lstData);

			lvItem.setAdapter(adapter);
		}
		super.onCreate(savedInstanceState);
	}

	// =========== webservice danh sach khuyen mai =================
	public class GetListSubType extends
			AsyncTask<String, Void, ArrayList<SubTypeBeans>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListSubType(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<SubTypeBeans> doInBackground(String... arg0) {
			return getListSubTypeBeans();
		}

		@Override
		protected void onPostExecute(ArrayList<SubTypeBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					lstData = result;
					adapter = new ArrayAdapter<SubTypeBeans>(context,
							R.layout.spinner_item,R.id.spinner_value, lstData);
					lvItem.setAdapter(adapter);
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					CommonActivity.BackFromLogin(FragmentChooseSubType.this,
							description);
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentChooseSubType.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<SubTypeBeans> getListSubTypeBeans() {
			ArrayList<SubTypeBeans> lisSubTypeBeans = new ArrayList<SubTypeBeans>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getLsSubTypesByTelService");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLsSubTypesByTelService>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<telService>" + serviceType);
				rawData.append("</telService>");
				rawData.append("<productCode>" + productCode);
				rawData.append("</productCode>");

				rawData.append("</input>");
				rawData.append("</ws:getLsSubTypesByTelService>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
						"mbccs_getLsSubTypesByTelService");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ===============parse xml =========================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstSubTypeDTOs");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SubTypeBeans subTypeBeans = new SubTypeBeans();
						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						subTypeBeans.setName(name);
						String subType = parse.getValue(e1, "subType");
						Log.d("subType", subType);
						subTypeBeans.setSubType(subType);

						lisSubTypeBeans.add(subTypeBeans);
					}
				}

			} catch (Exception e) {
				Log.d("getListSubTypeBeans", e.toString());
			}
			return lisSubTypeBeans;
		}

	}

	public void unit() {
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvItem = (ListView) findViewById(R.id.lstpakage);
		lvItem.setOnItemClickListener(this);
		lvItem.setTextFilterEnabled(true);
		edtsearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// String input = edtsearch.getText().toString()
				// .toLowerCase(Locale.getDefault());

				onSearchData();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void onSearchData() {

		String keySearch = edtsearch.getText().toString().trim();
		if (keySearch.length() == 0) {
			adapter = new ArrayAdapter<SubTypeBeans>(this,
					R.layout.spinner_item,R.id.spinner_value, lstData);
			lvItem.setAdapter(adapter);
			// GetPromotionAdapter itemsAdapter = new
			// GetPromotionAdapter(arrPromotionTypeBeans, this);
			// lv_kmai.setAdapter(itemsAdapter);

		} else {
			ArrayList<SubTypeBeans> items = new ArrayList<SubTypeBeans>();
			for (SubTypeBeans bean : lstData) {
				if (CommonActivity.convertUnicode2Nosign(bean.getSubType().toLowerCase())
						.contains(CommonActivity.convertUnicode2Nosign(keySearch.toLowerCase())) || CommonActivity.convertUnicode2Nosign(bean.getName().toLowerCase())
						.contains(CommonActivity.convertUnicode2Nosign(keySearch.toLowerCase()))) {
					items.add(bean);
				}
			}
			adapter = new ArrayAdapter<SubTypeBeans>(this,
					R.layout.spinner_item,R.id.spinner_value, items);
			lvItem.setAdapter(adapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("selectedSubTypeKey",
                adapter.getItem(arg2));
		mBundle.putSerializable("lstSubTypeKey", lstData);
		data.putExtras(mBundle);
		setResult(Activity.RESULT_OK, data);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.relaBackHome:
				onBackPressed();
				break;
			default:
				break;
		}
	}
}
