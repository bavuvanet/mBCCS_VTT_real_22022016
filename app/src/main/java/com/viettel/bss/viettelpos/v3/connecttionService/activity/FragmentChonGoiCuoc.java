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
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetPakageBundleBccs2Adapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.ProductOfferringHanlder;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChonGoiCuoc extends GPSTracker implements
		OnItemClickListener, View.OnClickListener {

	private EditText edtsearch;
	private ListView lvpakage;
	private GetPakageBundleBccs2Adapter adapter;
	private ArrayList<ProductOfferingDTO> lstGoiCuoc = new ArrayList<ProductOfferingDTO>();
	private Long telecomServiceId;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
	private ArrayList<ProductOfferingDTO> lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.connectionmobile_layout_lst_pakage);
        ButterKnife.bind(this);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			lstGoiCuoc = (ArrayList<ProductOfferingDTO>) b
					.getSerializable("arrGoiCuocKey");
			telecomServiceId = b.getLong("telecomServiceIdKey");
		}
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.chongoicuoc));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		unit();

		if (CommonActivity.isNullOrEmpty(lstGoiCuoc)) {
			GetListPakageAsyn asy = new GetListPakageAsyn(this, false);
			asy.execute();
		}else{
			adapter = new GetPakageBundleBccs2Adapter(lstGoiCuoc,
					this);
			lvpakage.setAdapter(adapter);
		}
		super.onCreate(savedInstanceState);
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

	/**
	 * Lay danh sach goi cuoc
	 * 
	 * @author huypq15
	 * 
	 */

	public class GetListPakageAsyn extends AsyncTask<String, Void, ArrayList<ProductOfferingDTO>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		boolean isSmartSim = false;

		public GetListPakageAsyn(Context context, boolean isSmart) {
			this.isSmartSim = isSmart;
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ProductOfferingDTO> doInBackground(String... params) {
			return sendRequestGetListService();
		}

		@Override
		protected void onPostExecute(ArrayList<ProductOfferingDTO> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (!CommonActivity.isNullOrEmpty(result)) {
					lstGoiCuoc = result;
					adapter = new GetPakageBundleBccs2Adapter(lstGoiCuoc,
							context);
					lvpakage.setAdapter(adapter);
				} else {
					lstGoiCuoc = new ArrayList<>();
					Dialog dialog = CommonActivity.createAlertDialog(context,
							getResources().getString(R.string.notgoicuoc),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				lstGoiCuoc = new ArrayList<>();
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					CommonActivity.BackFromLogin(FragmentChonGoiCuoc.this,
							description);
				} else {

					if (CommonActivity.isNullOrEmpty(description)) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
			}
		public ArrayList<ProductOfferingDTO> sendRequestGetListService() {
			String original = null;
			FileInputStream in = null;
			lstProductOfferingDTOHasAtrr  = new ArrayList<ProductOfferingDTO>();
			ArrayList<ProductOfferingDTO> lisPakageChargeBeans = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				StringBuilder rawData = new StringBuilder();
				rawData.append(
						"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

				rawData.append("<soapenv:Header/>");
				rawData.append("<soapenv:Body>");
				rawData.append("<ws:getProductCodeByMapActiveInfo>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<telecomServiceId>" + telecomServiceId);
				rawData.append("</telecomServiceId>");
				rawData.append("<payType>" + "1");
				rawData.append("</payType>");

				rawData.append("<actionCode>" + "00");
				rawData.append("</actionCode>");

				rawData.append("</input>");
				rawData.append("</ws:getProductCodeByMapActiveInfo>");
				rawData.append("</soapenv:Body>");
				rawData.append("</soapenv:Envelope>");
				Log.i("RowData", rawData.toString());
				String envelope = rawData.toString();
				Log.e("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);

				String fileName = input.sendRequestWriteToFile(envelope, Constant.WS_PAKAGE_DATA,
						Constant.PAKAGE_FILE_NAME);
				if (fileName != null) {

					try {

						File file = new File(fileName);
//                        if (!file.mkdirs()) {
//                            file.createNewFile();
//                        }
						in = new FileInputStream(file);
						ProductOfferringHanlder handler = (ProductOfferringHanlder) CommonActivity
								.parseXMLHandler(new ProductOfferringHanlder(), new InputSource(in));
//                        file.delete();
						if (handler != null) {
							if (handler.getItem().getToken() != null && !handler.getItem().getToken().isEmpty()) {
								Session.setToken(handler.getItem().getToken());
							}

							if (handler.getItem().getErrorCode() != null) {
								errorCode = handler.getItem().getErrorCode();
							}
							if (handler.getItem().getDescription() != null) {
								description = handler.getItem().getDescription();
							}
							lisPakageChargeBeans = handler.getLstData();

							if(!CommonActivity.isNullOrEmpty(handler) && !CommonActivity.isNullOrEmptyArray(handler.getLstProductOfferingDTOHasAtrr())){
								lstProductOfferingDTOHasAtrr  = handler.getLstProductOfferingDTOHasAtrr();
							}else{
								lstProductOfferingDTOHasAtrr  = new ArrayList<ProductOfferingDTO>();
							}

//                            if (!CommonActivity.isNullOrEmptyArray(lisPakageChargeBeans)) {
//                                String key1 = subType + "-" + serviceType;
//                                hashmapProductOffer.put(key1, lisPakageChargeBeans);
//                            }
						}
					} catch (Exception e) {
						Log.e("Exception", e.toString(), e);
					} finally {
						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								Log.e("Exception", e.toString(), e);
							}
						}
					}
				}

			} catch (Exception e) {
				Log.e("Exception", e.toString(), e);
			}

			return lisPakageChargeBeans;
		}
		}


	public void unit() {
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvpakage = (ListView) findViewById(R.id.lstpakage);
		lvpakage.setOnItemClickListener(this);
		lvpakage.setTextFilterEnabled(true);
		edtsearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				if (adapter != null) {
					// lstGoiCuoc = adapter.SearchInput(input);
					// lvpakage.setAdapter(adapter);
					adapter.SearchInput(input);
					// adapter.notifyDataSetChanged();
				}

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("selectedGoiCuocKey",
				(ProductOfferingDTO) adapter.getItem(arg2));
		mBundle.putSerializable("lstGoiCuocKey", lstGoiCuoc);
		data.putExtras(mBundle);
		setResult(Activity.RESULT_OK, data);
		finish();
		// getTargetFragment().onActivityResult(getTargetRequestCode(),
		// Activity.RESULT_OK, data);
		// getActivity().onBackPressed();
	}

}
