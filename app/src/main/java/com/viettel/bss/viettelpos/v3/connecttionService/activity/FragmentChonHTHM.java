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
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ReasonFullDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.HTHMMobileHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetHTHMAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChonHTHM extends GPSTracker implements
		OnItemClickListener, View.OnClickListener {

	private EditText edtsearch;
	private ListView lvItem;
	private GetHTHMAdapter adapter;
	private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<HTHMBeans>();
	private String offerId;
	private String serviceType;
	private String payType;
	private String subType;
	private String custType;
	private AreaObj area;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private String technology;
	private String CHECK_CHANGE_TECH;
	private boolean isCombo;
	private String subId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.connectionmobile_layout_lst_pakage);
		ButterKnife.bind(this);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			arrHthmBeans = (ArrayList<HTHMBeans>) b
					.getSerializable("arrHTHMKey");
			offerId = b.getString("offerIdKey");
			serviceType = b.getString("serviceTypeKey");
			payType = b.getString("payTypeKey");
			subType = b.getString("subTypeKey");
			custType = b.getString("custTypeKey");
			technology = b.getString("technology","");
			area = (AreaObj) b.getSerializable("areaObjKey");
			CHECK_CHANGE_TECH = b.getString("CHECK_CHANGE_TECH","");
			isCombo = b.getBoolean("isCombo",false);
			subId = b.getString("subIdKey",subId);
		}
		EditText edtsearch = (EditText) findViewById(R.id.edtsearch);
		edtsearch.setHint(R.string.nhap_hthm_tim_kiem);
		TextView tvLabel = (TextView) findViewById(R.id.txtinfo);
		tvLabel.setText(R.string.ds_hthm);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(getString(R.string.chonhthm));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		unit();
		if (CommonActivity.isNullOrEmpty(arrHthmBeans)) {
			GetHTHMAsyn asy = new GetHTHMAsyn(this);
			asy.execute();
		} else {
			adapter = new GetHTHMAdapter(arrHthmBeans, this);
			lvItem.setAdapter(adapter);
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
	public class GetHTHMAsyn extends
			AsyncTask<String, Void, ArrayList<HTHMBeans>> {

		ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetHTHMAsyn(Context context) {
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
		protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
			return getListHTHM();
//			return getReasonConnect();
		}

		@Override
		protected void onPostExecute(ArrayList<HTHMBeans> result) {

			// TODO get hinh thuc hoa mang
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (arrHthmBeans != null && !arrHthmBeans.isEmpty()) {
					arrHthmBeans = new ArrayList<HTHMBeans>();
				}
				if (result != null && result.size() > 0) {
					arrHthmBeans = result;
					adapter = new GetHTHMAdapter(arrHthmBeans, context);
					lvItem.setAdapter(adapter);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(context,
							getResources().getString(R.string.noththm),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin(FragmentChonHTHM.this,
							description);
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		// ==input String offerId, String serviceType, String province ,String
		// district, String precinct)
		private ArrayList<HTHMBeans> getListHTHM() {
			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListRegTypeConnectSP");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListRegTypeConnectSP>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<offerId>" + offerId);
				rawData.append("</offerId>");
				rawData.append("<serviceType>" + serviceType);
				rawData.append("</serviceType>");
				rawData.append("<payType>" + payType);
				rawData.append("</payType>");

				rawData.append("<subType>").append(subType);
				rawData.append("</subType>");

				rawData.append("<custType>").append(custType);
				rawData.append("</custType>");

				rawData.append("<province>").append(area.getProvince());
				rawData.append("</province>");

				rawData.append("<district>").append(area.getDistrict());
				rawData.append("</district>");

				rawData.append("<precinct>").append(area.getPrecinct());
				rawData.append("</precinct>");
				rawData.append("</input>");
				rawData.append("</ws:getListRegTypeConnectSP>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
						"mbccs_getListRegTypeConnectSP");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================

				HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
						.parseXMLHandler(new HTHMMobileHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (output.getErrorCode() != null) {
					errorCode = output.getErrorCode();
				}
				if (output.getDescription() != null) {
					description = output.getDescription();
				}
				lstHthmBeans = handler.getLstData();
				// ====== parse xml ===================
			} catch (Exception e) {
				Log.d("getListHTHM", e.toString());
			}

			return lstHthmBeans;
		}

		private ArrayList<HTHMBeans> getReasonFullIncludeSpec() {
			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getReasonFullIncludeSpec");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReasonFullIncludeSpec>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
					rawData.append("<isChangeTech>" + CHECK_CHANGE_TECH);
					rawData.append("</isChangeTech>");
				}

				rawData.append("<offerId>" + offerId);
				rawData.append("</offerId>");
				rawData.append("<serviceType>" + serviceType);
				rawData.append("</serviceType>");
				rawData.append("<payType>" + payType);
				rawData.append("</payType>");

				rawData.append("<subType>").append(subType);
				rawData.append("</subType>");

				rawData.append("<custType>").append(custType);
				rawData.append("</custType>");

				rawData.append("<province>").append(area.getProvince());
				rawData.append("</province>");

				rawData.append("<district>").append(area.getDistrict());
				rawData.append("</district>");

				rawData.append("<precinct>").append(area.getPrecinct());
				rawData.append("</precinct>");

				rawData.append("<technology>" + technology);
				rawData.append("</technology>");

//				rawData.append("<levelSub>" + levelSub);
//				rawData.append("</levelSub>");


				if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.groupsDTO) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.groupsDTO.getGroupId())){
					rawData.append("<groupId>").append(TabThueBaoHopDongManager.instance.groupsDTO.getGroupId());
					rawData.append("</groupId>");
				}

				if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit)){
					if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode())){
						rawData.append("<stationCodes>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode());
						rawData.append("</stationCodes>");
						rawData.append("<nodeCode>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode());
						rawData.append("</nodeCode>");
					}
				}
				if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm())){
					if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode())){
						rawData.append("<vendor>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode());
						rawData.append("</vendor>");
					}
				}
				rawData.append("<isComboProduct>" + isCombo);
				rawData.append("</isComboProduct>");
//				private String levelSub;
//				private boolean isFirstONT = false;

				rawData.append("</input>");
				rawData.append("</ws:getReasonFullIncludeSpec>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
						"mbccs_getReasonFullIncludeSpec");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================
				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					ArrayList<ReasonFullDTO> arrayList = parseOuput.getLstReasonFullDTOs();
					if(!CommonActivity.isNullOrEmpty(arrayList)){
						for (ReasonFullDTO item: arrayList) {
							HTHMBeans hthmBeans = new HTHMBeans();
							hthmBeans.setName(item.getReasonDTO().getName());
							hthmBeans.setCode(item.getReasonDTO().getReasonCode());
							hthmBeans.setReasonId(item.getReasonDTO().getReasonId());
							hthmBeans.setOnt(isOntItem(item.getLstPackageOffer()));
							lstHthmBeans.add(hthmBeans);

						}
					}
				} else {
					errorCode = Constant.ERROR_CODE;
					description = FragmentChonHTHM.this.getString(R.string.no_data_return);
				}
//				HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
//						.parseXMLHandler(new HTHMMobileHandler(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				lstHthmBeans = handler.getLstData();
				// ====== parse xml ===================
			} catch (Exception e) {
				Log.d("getListHTHM", e.toString());
			}

			return lstHthmBeans;
		}

		private ArrayList<HTHMBeans> getReasonConnect() {
			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getReasonConnect");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReasonConnect>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
					rawData.append("<isChangeTech>" + CHECK_CHANGE_TECH);
					rawData.append("</isChangeTech>");
				}

				rawData.append("<offerId>" + offerId);
				rawData.append("</offerId>");
				rawData.append("<serviceType>" + serviceType);
				rawData.append("</serviceType>");
				rawData.append("<payType>" + payType);
				rawData.append("</payType>");

				rawData.append("<subType>").append(subType);
				rawData.append("</subType>");

				rawData.append("<custType>").append(custType);
				rawData.append("</custType>");

				rawData.append("<province>").append(area.getProvince());
				rawData.append("</province>");

				rawData.append("<district>").append(area.getDistrict());
				rawData.append("</district>");

				rawData.append("<precinct>").append(area.getPrecinct());
				rawData.append("</precinct>");

				rawData.append("<technology>" + technology);
				rawData.append("</technology>");


				if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit)){
					if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode())){
						rawData.append("<stationCodes>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode());
						rawData.append("</stationCodes>");
						rawData.append("<nodeCode>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode());
						rawData.append("</nodeCode>");
					}
				}

				rawData.append("<isCombo>" + isCombo);
				rawData.append("</isCombo>");

				rawData.append("<subId>" + subId);
				rawData.append("</subId>");


//				private String levelSub;
//				private boolean isFirstONT = false;

				rawData.append("</input>");
				rawData.append("</ws:getReasonConnect>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
						"mbccs_getReasonConnect");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================
				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					ArrayList<ReasonDTO> arrayList = parseOuput.getLstReasonDTOs();
					if(!CommonActivity.isNullOrEmpty(arrayList)){
						for (ReasonDTO item: arrayList) {
							if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
								if(!CommonActivity.isNullOrEmpty(item.getLstCharacterCode()) && item.getLstCharacterCode().contains("CHUYEN_CN_CD")){
									HTHMBeans hthmBeans = new HTHMBeans();
									hthmBeans.setName(item.getName());
									hthmBeans.setCode(item.getCode());
									hthmBeans.setReasonId(item.getReasonId());
									lstHthmBeans.add(hthmBeans);
								}
							}else{
								if(CommonActivity.isNullOrEmpty(item.getLstCharacterCode()) || !item.getLstCharacterCode().contains("CHUYEN_CN_CD")){
									HTHMBeans hthmBeans = new HTHMBeans();
									hthmBeans.setName(item.getName());
									hthmBeans.setCode(item.getCode());
									hthmBeans.setReasonId(item.getReasonId());
									lstHthmBeans.add(hthmBeans);
								}

							}
						}
					}
				} else {
					errorCode = Constant.ERROR_CODE;
					description = FragmentChonHTHM.this.getString(R.string.no_data_return);
				}
//				HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
//						.parseXMLHandler(new HTHMMobileHandler(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				lstHthmBeans = handler.getLstData();
				// ====== parse xml ===================
			} catch (Exception e) {
				Log.d("getListHTHM", e.toString());
			}

			return lstHthmBeans;
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
		mBundle.putSerializable("selectedHTHMKey",
				(HTHMBeans) adapter.getItem(arg2));
		mBundle.putSerializable("arrHthmBeansKey", arrHthmBeans);
		data.putExtras(mBundle);
		setResult(Activity.RESULT_OK, data);
		finish();
		// getTargetFragment().onActivityResult(getTargetRequestCode(),
		// Activity.RESULT_OK, data);
		// context.onBackPressed();
	}
	public static boolean isOntItem(ArrayList<ProductOfferingDTO> arrProductOfferingDTOs) {
		if(CommonActivity.isNullOrEmptyArray(arrProductOfferingDTOs)){
			return false;
		}
		try {
			for (ProductOfferingDTO productOfferingDTO : arrProductOfferingDTOs) {
				ArrayList<ProductSpecCharDTO> lstProductOfferingCharacterFullDTO = productOfferingDTO.getLstProductSpecCharDTOs();
				if (!CommonActivity.isNullOrEmptyArray(lstProductOfferingCharacterFullDTO)) {
					for (ProductSpecCharDTO item : lstProductOfferingCharacterFullDTO) {
						if ("ONT".equals(item.getCode())) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
