package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.customer.object.ObjPrepairIdNo;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customview.obj.LoaiGiayToObj;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentPrepairIdNo extends Fragment implements OnClickListener {
	private Activity activity;
	private InfrastrucureDB mInfrastrucureDB;
	private View mView;
	private Spinner spnSubType, spnCusType, spnDocumentType, spnReason,
			spnService;
	private TextView vlIdNoError, vlBusinessLicenseError, vlNameOfSale;
	private EditText edtIdNo, edtBusinessLicense, edtHouseHold,
			edtLocationSupply, edtEndDate, edtDateSupply, edtIsdn;
	private Button btnApply, btnCancel;
	private ImageButton btnSearch;
	private LinearLayout ll_prepair_info;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private int toYear;
	private int toMonth;
	private int toDay;
	private Date dateFrom = null;
	private Date dateTo = null;
	private Date currentDate = null;
	private String dateFromString = "";
	private String dateToString = "";
	private String fromDate = "";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final ArrayList<Spin> lstCustomerType = new ArrayList<>();
	private final ArrayList<Spin> lstDocumentType = new ArrayList<>();
	private final ArrayList<Spin> lstReason = new ArrayList<>();
	private final ObjPrepairIdNo objPre = new ObjPrepairIdNo();
	private int dbType = 1;
    private ObjPrepairIdNo objPrepairIdNo = new ObjPrepairIdNo();
	private String reasonId = "";
	private String typePaper = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		mInfrastrucureDB = new InfrastrucureDB(activity);

		Calendar cal = Calendar.getInstance();

		

		fromYear = cal.get(Calendar.YEAR);
		fromMonth = cal.get(Calendar.MONTH);
		fromDay = cal.get(Calendar.DAY_OF_MONTH);

		toYear = cal.get(Calendar.YEAR);
		toMonth = cal.get(Calendar.MONTH);
		toDay = cal.get(Calendar.DAY_OF_MONTH);
		
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		try {
			currentDate = cal.getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.activity_prepare_id_no,
					container, false);
			init(mView);
		}
		initTelecomService();
		edtDateSupply.setOnClickListener(this);
		edtEndDate.setOnClickListener(this);
		btnApply.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnSearch.setOnClickListener(this);

		ArrayList<Spin> lstSubType = new ArrayList<>();
		lstSubType.add(new Spin("1", getString(R.string.tv_tra_truoc)));
		lstSubType.add(new Spin("2", getString(R.string.tv_tra_sau)));
		Utils.setDataSpinner(activity, lstSubType, spnSubType);

		getListDocumentType();
		spnService.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				resetData();
				Spin item = (Spin) arg0.getItemAtPosition(arg2);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spnSubType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				resetData();

				Spin item = (Spin) parent.getItemAtPosition(position);
				dbType = Integer.parseInt(item.getId());

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spnReason.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				reasonId = item.getId();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// lstCustomerType.add(new Spin("-1",
		// getString(R.string.spn_customer_type)));
		// Utils.setDataSpinner(activity, lstCustomerType, spnCusType);
		// lstReason.add(new Spin("-1", getString(R.string.txt_select_reason)));
		// Utils.setDataSpinner(activity, lstReason, spnReason);

		return mView;
	}

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.repair_id_no);
    }

    private void init(View v) {
		spnSubType = (Spinner) v.findViewById(R.id.spn_sub_type);
		spnCusType = (Spinner) v.findViewById(R.id.spn_customer_type);
		spnDocumentType = (Spinner) v.findViewById(R.id.spn_document_type);
		spnService = (Spinner) v.findViewById(R.id.spnService);
		spnReason = (Spinner) v.findViewById(R.id.spn_reason_fail);
		vlIdNoError = (TextView) v.findViewById(R.id.vl_id_no_error);
		vlBusinessLicenseError = (TextView) v
				.findViewById(R.id.vl_bussiness_license_error);
		vlNameOfSale = (TextView) v.findViewById(R.id.vl_name_of_sale);
		edtIdNo = (EditText) v.findViewById(R.id.edt_id_no);
		edtHouseHold = (EditText) v.findViewById(R.id.edt_number_of_household);
		edtBusinessLicense = (EditText) v
				.findViewById(R.id.edt_bussiness_license);
		edtDateSupply = (EditText) v.findViewById(R.id.edt_date_supply);
		edtLocationSupply = (EditText) v.findViewById(R.id.edt_location_supply);
		edtEndDate = (EditText) v.findViewById(R.id.edt_document_end_date);
		edtIsdn = (EditText) v.findViewById(R.id.edt_account);
		btnApply = (Button) v.findViewById(R.id.btn_apply);
		btnCancel = (Button) v.findViewById(R.id.btn_cancel);
		btnSearch = (ImageButton) v.findViewById(R.id.btn_search);
		ll_prepair_info = (LinearLayout) v.findViewById(R.id.ll_prepair_info);
		
		spnDocumentType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				typePaper = item.getId();
				if(item.getId().equals("1")){
					edtIdNo.setInputType(InputType.TYPE_CLASS_NUMBER);
				}else{
					edtIdNo.setInputType(InputType.TYPE_CLASS_TEXT);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
	}

	private void resetData() {

		vlNameOfSale.setText("");
		if (lstCustomerType != null && !lstCustomerType.isEmpty()) {
			lstCustomerType.clear();
		}
		vlIdNoError.setText("");
		edtIdNo.setText("");
		edtEndDate.setText("");
		edtDateSupply.setText("");
		edtHouseHold.setText("");
		edtLocationSupply.setText("");
		vlBusinessLicenseError.setText("");
		edtBusinessLicense.setText("");

		if (lstReason != null && !lstReason.isEmpty()) {
			lstReason.clear();
		}

		objPrepairIdNo = new ObjPrepairIdNo();
		ll_prepair_info.setVisibility(View.GONE);

	}

	// lay dich vu
	private void initTelecomService() {
		GetServiceDal dal = new GetServiceDal(getActivity());
		try {
			dal.open();
            ArrayList<TelecomServiceBeans> lstTelecomService = dal.getlisServiceMobile();
			if (lstTelecomService != null && !lstTelecomService.isEmpty()) {
				ArrayList<Spin> lstService = new ArrayList<>();
				for (TelecomServiceBeans telecomServiceBeans : lstTelecomService) {
					Spin item = new Spin();
					item.setValue(telecomServiceBeans.getTele_service_name());
					item.setId(telecomServiceBeans.getServiceAlias());
					lstService.add(item);
				}
				Utils.setDataSpinner(activity, lstService, spnService);
			}
		} catch (Exception e) {
			Log.e("initTelecomService", e.toString());
		} finally {
			dal.close();
		}
	}

	private final DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			fromYear = selectedYear;
			fromMonth = selectedMonth;
			fromDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (fromDay < 10) {
				strDate.append("0");
			}
			strDate.append(fromDay).append("/");
			if (fromMonth < 9) {
				strDate.append("0");
			}
			strDate.append(fromMonth + 1).append("/");
			strDate.append(fromYear);
			fromDate = strDate.toString();

			Log.d("fromDate", fromDate);
			dateFromString = String.valueOf(fromYear) + "-" +
                    (fromMonth + 1) + "-" + fromDay;
			Log.d("dateFromString", dateFromString);
			try {
				dateFrom = sdf.parse(dateFromString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edtEndDate.setText(strDate);
		}
	};
	private final DatePickerDialog.OnDateSetListener supplyDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			toYear = selectedYear;
			toMonth = selectedMonth;
			toDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (toDay < 10) {
				strDate.append("0");
			}
			strDate.append(toDay).append("/");
			if (toMonth < 9) {
				strDate.append("0");
			}
			strDate.append(toMonth + 1).append("/");
			strDate.append(toYear);
            String toDate = strDate.toString();

			Log.d("toDate", toDate);
			dateToString = String.valueOf(toYear) + "-" +
                    (toMonth + 1) + "-" + toDay;
			Log.d("dateToString", dateToString);
			try {
				dateTo = sdf.parse(dateToString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edtDateSupply.setText(strDate);
		}
	};

	private void setDefaultValue(ObjPrepairIdNo obj) {
		if (obj != null) {
			vlNameOfSale.setText(obj.getAccountName());
			vlIdNoError.setText(obj.getIdNoError());
			vlBusinessLicenseError.setText(obj.getBusinessNumberError());

			if (obj.getDateSupply() != null && !obj.getDateSupply().isEmpty()) {
				edtDateSupply.setText(StringUtils.convertDate(obj
						.getDateSupply()));

				String dateSupply = edtDateSupply.getText().toString().trim();
				String[] arrDateSubply = dateSupply.split("/");
				if (arrDateSubply != null)

					if (arrDateSubply.length == 3) {

						if (arrDateSubply[0] != null
								&& !arrDateSubply[0].isEmpty()) {
							toDay = Integer.parseInt(arrDateSubply[0]);
						}

						if (arrDateSubply[1] != null
								&& !arrDateSubply[1].isEmpty()) {
							toMonth = Integer.parseInt(arrDateSubply[1]);
						}
						if (arrDateSubply[2] != null
								&& !arrDateSubply[2].isEmpty()) {
							toYear = Integer.parseInt(arrDateSubply[2]);
						}
						dateToString = String.valueOf(toYear) +
                                "-" + toMonth + "-" +
                                toDay;
						Log.d("dateToString", dateToString);
						try {
							dateTo = sdf.parse(dateToString);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

			}

			if (obj.getEndDateDocument() != null
					&& !obj.getEndDateDocument().isEmpty()) {
				edtEndDate.setText(StringUtils.convertDate(obj
						.getEndDateDocument()));

				String dateSupply = edtEndDate.getText().toString().trim();
				String[] arrDateSubply = dateSupply.split("/");
				if (arrDateSubply != null)
					if (arrDateSubply.length == 3) {
						if (arrDateSubply[0] != null
								&& !arrDateSubply[0].isEmpty()) {
							fromDay = Integer.parseInt(arrDateSubply[1]);
						}
						if (arrDateSubply[1] != null
								&& !arrDateSubply[1].isEmpty()) {
							fromMonth = Integer.parseInt(arrDateSubply[1]);
						}
						if (arrDateSubply[2] != null
								&& !arrDateSubply[2].isEmpty()) {
							fromYear = Integer.parseInt(arrDateSubply[2]);
						}

						Log.d("fromDate", fromDate);
						dateFromString = String.valueOf(fromYear) +
                                "-" + fromMonth + "-" +
                                fromDay;
						Log.d("dateFromString", dateFromString);
						try {
							dateFrom = sdf.parse(dateFromString);
						} catch (Exception ignored) {
						}

					}
			}
			edtBusinessLicense.setText(obj.getBusinessNumberError());
			edtIdNo.setText(obj.getIdNoError());
			edtHouseHold.setText(obj.getHouseHold());
			edtLocationSupply.setText(obj.getLocationSupply());

			if (lstDocumentType != null && lstDocumentType.size() > 0) {
				for (int i = 0; i < lstDocumentType.size(); i++) {
					Spin item = lstDocumentType.get(i);
					if (item.getId().equals(obj.getDocumentTypeId())) {
						spnDocumentType.setSelection(i);
					}
				}
			}

			// TODO SET LOAI KHACH HANG O CHO NAY
			obj.getLstBusType().add(0,
					new Spin("-1", getString(R.string.spn_customer_type)));
			Utils.setDataSpinner(activity, obj.getLstBusType(), spnCusType);
			if (obj.getLstBusType() != null && obj.getLstBusType().size() > 0) {
				for (int i = 0; i < obj.getLstBusType().size(); i++) {
					Spin item = obj.getLstBusType().get(i);
					if (item.getId().equals(obj.getCustomerType())) {
						spnCusType.setSelection(obj.getLstBusType().indexOf(
								item));
					}
				}
			}
		}
	}

	private void getListDocumentType() {
		ArrayList<LoaiGiayToObj> lstDoc = new ArrayList<>();
		Spin item1 = new Spin();
		item1.setValue(getString(R.string.select_loaigiayto));
		item1.setId("");
		lstDocumentType.add(0, item1);
		lstDoc = mInfrastrucureDB.getListLoaiGiayTo();
		if (lstDoc != null && lstDoc.size() > 0) {
			for (LoaiGiayToObj obj : lstDoc) {
				Spin item = new Spin();
				item.setId(obj.getParType());
				item.setValue(obj.getParValue());
				lstDocumentType.add(item);
			}
		}

		if (lstDocumentType != null) {
			Utils.setDataSpinner(activity, lstDocumentType, spnDocumentType);
		}
	}

	// search_isdn pre
	private class AsyntaskGetSubscriberByIsdnPre extends
			AsyncTask<String, Void, ObjPrepairIdNo> {
		final ProgressDialog progress;
		private Context context = null;
		private int type;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetSubscriberByIsdnPre(Context context) {
			this.context = context;
            this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ObjPrepairIdNo doInBackground(String... params) {
			return searchCustomerPre();
		}

		@Override
		protected void onPostExecute(ObjPrepairIdNo result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				ll_prepair_info.setVisibility(View.VISIBLE);
				objPrepairIdNo = result;
				setDefaultValue(result);

				// if (result.getLstBusType() != null
				// && result.getLstBusType().size() > 0) {
				// for (int i = 0; i < result.getLstBusType().size(); i++) {
				// Spin item = (Spin) result.getLstBusType().get(i);
				// if (item.getValue().equals(result.getCustomerType())) {
				// spnCusType.setSelection(result.getLstBusType().indexOf(item));
				// }
				// }
				// }

				AsyntaskGetReasonPre asyntaskGetReasonPre = new AsyntaskGetReasonPre(
						getActivity());
				asyntaskGetReasonPre.execute("" + dbType);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ObjPrepairIdNo searchCustomerPre() {
			Spin subItem = (Spin) spnSubType.getSelectedItem();
			String isdn = CommonActivity.checkStardardIsdn(edtIsdn.getText()
					.toString().trim());
			Spin subService = (Spin) spnService.getSelectedItem();
			ObjPrepairIdNo objSub = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustomerInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerInfo>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<dbType>").append(subItem.getId()).append("</dbType>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<serviceType>").append(subService.getId()).append("</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getCustomerInfo>");
				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCustomerInfo");
				Log.i("LOG", "Respone: " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original " + response);

				// parser

				objSub = parserListSearchPre(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return objSub;
		}

		public ObjPrepairIdNo parserListSearchPre(String original) {
			ObjPrepairIdNo objSub = new ObjPrepairIdNo();
			ArrayList<Spin> lstBusType = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			NodeList nodeChildBusType = null;
			NodeList nodesubscriberPre = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);

				// lay ra thong khach hang
				nodechild = doc.getElementsByTagName("customerPre");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					objSub.setAccountName(parse.getValue(e1, "name"));
					objSub.setCustomerType(parse.getValue(e1, "busType"));
					Log.d("busType Customer", parse.getValue(e1, "busType"));

					objSub.setDocumentTypeId(parse.getValue(e1, "idType"));
					objSub.setIdNoError(parse.getValue(e1, "idNo"));
					objSub.setEndDateDocument(parse
							.getValue(e1, "idExpireDate"));
					objSub.setDateSupply(parse.getValue(e1, "popIssueDate"));
					objSub.setHouseHold(parse.getValue(e1, "popNo"));
					objSub.setLocationSupply(parse.getValue(e1, "popIssuePlace"));
					objSub.setBusinessNumberError(parse.getValue(e1,
							"busPermitNo"));
				}
				// lay thong tin thue bao
				nodesubscriberPre = doc.getElementsByTagName("subscriber");
				for (int k = 0; k < nodesubscriberPre.getLength(); k++) {
					Element e4 = (Element) nodesubscriberPre.item(k);
					objSub.setSubId(parse.getValue(e4, "subId"));

				}
				// lay ra thong tin loai khach hang
				nodeChildBusType = doc.getElementsByTagName("lstBusTypePre");
				for (int j = 0; j < nodeChildBusType.getLength(); j++) {
					Element e3 = (Element) nodeChildBusType.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e3, "busTypeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e3, "busType"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstBusType.add(spin);
				}

				// lay danh sach loai kh tra truoc
				if (lstBusType != null && lstBusType.size() > 0) {
					objSub.setLstBusType(lstBusType);
				}
			}
			return objSub;
		}

	}

	// search_isdn pos
	private class AsyntaskGetSubscriberByIsdnPos extends
			AsyncTask<String, Void, ObjPrepairIdNo> {
		final ProgressDialog progress;
		private Context context = null;
		private int type;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetSubscriberByIsdnPos(Context context) {
			this.context = context;
            this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ObjPrepairIdNo doInBackground(String... params) {
			return searchCustomerPos();
		}

		@Override
		protected void onPostExecute(ObjPrepairIdNo result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				ll_prepair_info.setVisibility(View.VISIBLE);
				objPrepairIdNo = result;
				setDefaultValue(result);

				// TODO SET LOAI KHACH HANG O CHO NAY
//				result.getLstBusType().add(0,
//						new Spin("-1", getString(R.string.spn_customer_type)));
//				Utils.setDataSpinner(activity, result.getLstBusType(),
//						spnCusType);

				// if (result.getLstBusType() != null
				// && result.getLstBusType().size() > 0) {
				// for (int i = 0; i < result.getLstBusType().size(); i++) {
				// Spin item = (Spin) result.getLstBusType().get(i);
				// if (item.getValue().equals(result.getCustomerType())) {
				// spnCusType.setSelection(result.getLstBusType().indexOf(item));
				// }
				// }
				// }

				AsyntaskGetReasonPos asyntaskGetReasonPos = new AsyntaskGetReasonPos(
						getActivity());
				asyntaskGetReasonPos.execute("" + dbType);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ObjPrepairIdNo searchCustomerPos() {
			Spin subItem = (Spin) spnSubType.getSelectedItem();
			Spin subService = (Spin) spnService.getSelectedItem();
			String isdn = CommonActivity.checkStardardIsdn(edtIsdn.getText()
					.toString().trim());

			ObjPrepairIdNo objSub = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getSubscriberByIsdnPos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSubscriberByIsdnPos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<dbType>").append(subItem.getId()).append("</dbType>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<serviceType>").append(subService.getId()).append("</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getSubscriberByIsdnPos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_ws:getSubscriberByIsdnPos");
				Log.i("LOG", "Respone: " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original " + response);

				// parser

				objSub = parserListSearchPos(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return objSub;
		}

		public ObjPrepairIdNo parserListSearchPos(String original) {
			ObjPrepairIdNo objSub = new ObjPrepairIdNo();
			ArrayList<Spin> lstBusType = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			NodeList nodeChildBusType = null;
			NodeList nodesubscriberPos = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);

				// lay ra thong khach hang
				nodechild = doc.getElementsByTagName("customerPos");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					objSub.setAccountName(parse.getValue(e1, "name"));
					objSub.setCustomerType(parse.getValue(e1, "busType"));
					Log.d("busType Customer", parse.getValue(e1, "busType"));
					objSub.setDocumentTypeId(parse.getValue(e1, "idType"));
					objSub.setIdNoError(parse.getValue(e1, "idNo"));
					objSub.setEndDateDocument(parse
							.getValue(e1, "idExpireDate"));
					objSub.setDateSupply(parse.getValue(e1, "popIssueDate"));
					objSub.setHouseHold(parse.getValue(e1, "popNo"));
					objSub.setLocationSupply(parse.getValue(e1, "popIssuePlace"));
					objSub.setBusinessNumberError(parse.getValue(e1,
							"busPermitNo"));
				}

				// lay thong tin thue bao
				nodesubscriberPos = doc.getElementsByTagName("subscriberPos");
				for (int k = 0; k < nodesubscriberPos.getLength(); k++) {
					Element e4 = (Element) nodesubscriberPos.item(k);
					objSub.setSubId(parse.getValue(e4, "subId"));

				}

				// lay ra thong tin loai khach hang
				nodeChildBusType = doc.getElementsByTagName("lstBusType");
				for (int j = 0; j < nodeChildBusType.getLength(); j++) {
					Element e3 = (Element) nodeChildBusType.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e3, "busTypeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e3, "busType"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstBusType.add(spin);
				}

				// lay danh sach loai kh tra truoc
				if (lstBusType != null && lstBusType.size() > 0) {
					objSub.setLstBusType(lstBusType);
				}
			}
			return objSub;
		}

	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};
	private final OnClickListener backClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			resetData();
			edtIsdn.setText("");

			// Intent i = new Intent();
			//
			// String strChannelName = edtChannelName.getText().toString();
			// String strChannelPhone = edtPhone.getText().toString();
			//
			// i.putExtra("CHANNEL_NAME", strChannelName);
			// i.putExtra("CHANNEL_PHONE", strChannelPhone);
			// getTargetFragment().onActivityResult(getTargetRequestCode(),
			// Activity.RESULT_OK, i);
			// getActivity().onBackPressed();
		}
	};

	// lay thong tin loai khach hang_start
	// private class AsyntaskGetCustomerType extends AsyncTask<String, Void,
	// ArrayList<Spin>> {
	// ProgressDialog progress;
	// private Context context = null;
	// XmlDomParse parse = new XmlDomParse();
	// String errorCode = "";
	// String description = "";
	//
	// public AsyntaskGetCustomerType(Context context) {
	// this.context = context;
	// this.progress = new ProgressDialog(this.context);
	// // check font
	// this.progress.setCancelable(false);
	// this.progress.setMessage(context.getResources().getString(R.string.waitting));
	// if (!this.progress.isShowing()) {
	// this.progress.show();
	// }
	// if (lstCustomerType != null && lstCustomerType.size() > 0) {
	// lstCustomerType.clear();
	// }
	// }
	//
	// @Override
	// protected ArrayList<Spin> doInBackground(String... params) {
	// return getCustomerType();
	// }
	//
	// @Override
	// protected void onPostExecute(ArrayList<Spin> result) {
	// progress.dismiss();
	// if (errorCode.equalsIgnoreCase("0")) {
	// lstCustomerType.add(new Spin("-1",
	// getString(R.string.spn_customer_type)));
	// lstCustomerType.addAll(result);
	// Utils.setDataSpinner(activity, lstCustomerType, spnCusType);
	//
	// if(lstCustomerType != null && lstCustomerType.size() > 0){
	// for( int i = 0; i< lstCustomerType.size(); i++){
	// Spin item = (Spin) lstCustomerType.get(i);
	// if(item.getValue().equals(objPre.getCustomerType())){
	// spnCusType.setSelection(i);
	// }
	// }
	// }
	//
	// } else {
	// if (errorCode.equals(Constant.INVALID_TOKEN2)) {
	// Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
	// description,
	// context.getResources().getString(R.string.app_name), moveLogInAct);
	// dialog.show();
	// } else {
	// if (description == null || description.isEmpty()) {
	// description = context.getString(R.string.checkdes);
	// }
	// lstCustomerType.add(new Spin("-1",
	// getString(R.string.spn_customer_type)));
	// Utils.setDataSpinner(activity, lstCustomerType, spnCusType);
	// Dialog dialog = CommonActivity.createAlertDialog(activity, description,
	// getResources().getString(R.string.app_name));
	// dialog.show();
	//
	// }
	// }
	// }
	//
	// private ArrayList<Spin> getCustomerType() {
	// ArrayList<Spin> lstReason = null;
	// String original = "";
	// try {
	// BCCSGateway input = new BCCSGateway();
	// input.addValidateGateway("username", Constant.BCCSGW_USER);
	// input.addValidateGateway("password", Constant.BCCSGW_PASS);
	// input.addValidateGateway("wscode", "mbccs_getCustomerTypeByDbType");
	// StringBuilder rawData = new StringBuilder();
	// rawData.append("<ws:getCustomerTypeByDbType>");
	// rawData.append("<cmMobileInput>");
	// rawData.append("<token>" + Session.getToken() + "</token>");
	// rawData.append("<dbType>" + String.valueOf(dbType) + "</dbType>");
	// rawData.append("<actionCode>" + 913 + "</actionCode>");
	// rawData.append("</cmMobileInput>");
	// rawData.append("</ws:getCustomerTypeByDbType>");
	//
	// Log.i("LOG", "raw data" + rawData.toString());
	// String envelope = input.buildInputGatewayWithRawData(rawData.toString());
	// Log.d("LOG", "Send evelop" + envelope);
	// Log.i("LOG", Constant.BCCS_GW_URL);
	// String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
	// getActivity(),
	// "mbccs_getCustomerTypeByDbType");
	// Log.i("LOG", "Respone:  " + response);
	// CommonOutput output = input.parseGWResponse(response);
	// original = output.getOriginal();
	// Log.i("LOG", "Responseeeeeeeeee Original  " + response);
	//
	// // parser
	//
	// lstReason = parserListGroup(original);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return lstReason;
	// }
	//
	// public ArrayList<Spin> parserListGroup(String original) {
	// ArrayList<Spin> lstReason = new ArrayList<Spin>();
	// Document doc = parse.getDomElement(original);
	// NodeList nl = doc.getElementsByTagName("return");
	// NodeList nodechild = null;
	// for (int i = 0; i < nl.getLength(); i++) {
	// Element e2 = (Element) nl.item(i);
	// errorCode = parse.getValue(e2, "errorCode");
	// description = parse.getValue(e2, "description");
	// Log.d("errorCode", errorCode);
	// nodechild = doc.getElementsByTagName("customerType");
	// for (int j = 0; j < nodechild.getLength(); j++) {
	// Element e1 = (Element) nodechild.item(j);
	// Spin spin = new Spin();
	// spin.setValue(parse.getValue(e1, "name"));
	// Log.d("LOG", "value: " + spin.getValue());
	// spin.setId(parse.getValue(e1, "code"));
	// Log.d("LOG", "Idddd: " + spin.getId());
	// lstReason.add(spin);
	// }
	// }
	//
	// return lstReason;
	// }
	//
	// }
	//
	// lay thong tin ly do tra truoc
	// get reason start
	private class AsyntaskGetReasonPre extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReasonPre(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPre(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				lstReason.add(new Spin("-1",
						getString(R.string.txt_select_reason)));
				lstReason.addAll(result);
				Utils.setDataSpinner(activity, lstReason, spnReason);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(activity, lstReason, spnReason);
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPre(String dbType) {

			Spin serviceItem = (Spin) spnService.getSelectedItem();
			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonByTelService");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelService>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<dbType>").append(dbType).append("</dbType>");
				rawData.append("<actionCode>" + 151 + "</actionCode>");
				rawData.append("<type>").append(serviceItem.getId()).append("</type>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelService>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonByTelService");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstReason = parserListGroup(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReason;
		}

		public ArrayList<Spin> parserListGroup(String original) {
			ArrayList<Spin> lstReason = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstReason");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "name"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e1, "reasonId"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	// lay thong tin ly do tra sau
	// get reason start
	private class AsyntaskGetReasonPos extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReasonPos(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				lstReason.add(new Spin("-1",
						getString(R.string.txt_select_reason)));
				lstReason.addAll(result);
				Utils.setDataSpinner(activity, lstReason, spnReason);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(activity, lstReason, spnReason);
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos(String dbType) {

			Spin serviceItem = (Spin) spnService.getSelectedItem();
			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonByTelServicePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelServicePos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<actionCode>" + 151 + "</actionCode>");
				rawData.append("<serviceType>").append(serviceItem.getId()).append("</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelServicePos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonByTelService");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstReason = parserListGroupPos(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReason;
		}

		public ArrayList<Spin> parserListGroupPos(String original) {
			ArrayList<Spin> lstReason = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstReasonPos");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "codeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e1, "reasonId"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	// get_reason_end
	// cap nhat thong tin cmnd_start
	private class AsyntaskUpdateRepairPrepaidErrorForSubPre extends
			AsyncTask<String, Void, String> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskUpdateRepairPrepaidErrorForSubPre(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			UpdateIdNo();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.suassaithanhcong),
						getString(R.string.app_name), backClick).show();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private void UpdateIdNo() {

			Spin itemReason = (Spin) spnReason.getSelectedItem();
			Spin itemDbType = (Spin) spnSubType.getSelectedItem();
			Spin itemCusType = (Spin) spnCusType.getSelectedItem();
			Spin itemDocumentType = (Spin) spnDocumentType.getSelectedItem();
			
			
			Spin subService = (Spin) spnService.getSelectedItem();
			String idNo = "";
			String businessNo = "";
			String dateSupply = "";
			String endDate = "";
			if (edtIdNo.getText().toString().trim().equals("")) {
				idNo = objPre.getIdNoError();
			} else {
				idNo = edtIdNo.getText().toString().trim();
			}
			if (edtBusinessLicense.getText().toString().trim().equals("")) {
				businessNo = objPre.getBusinessNumberError();
			} else {
				businessNo = edtBusinessLicense.getText().toString().trim();
			}
			if (edtDateSupply.getText().toString().trim().equals("")) {
				dateSupply = objPre.getDateSupply();
			} else {
				dateSupply = edtDateSupply.getText().toString().trim();
			}
			if (edtEndDate.getText().toString().trim().equals("")) {
				endDate = objPre.getEndDateDocument();
			} else {
				endDate = edtEndDate.getText().toString().trim();
			}

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				if ("1".equals(itemDbType.getId())) {
					input.addValidateGateway("wscode", "mbccs_repairChangeIDNo");
				} else {
					input.addValidateGateway("wscode",
							"mbccs_updateRepairPrepaidErrorForSubPos");
				}

				StringBuilder rawData = new StringBuilder();
				if ("1".equals(itemDbType.getId())) {
					rawData.append("<ws:repairChangeIDNo>");
				} else {
					rawData.append("<ws:updateRepairPrepaidErrorForSubPos>");
				}

				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<dbType>").append(itemDbType.getId()).append("</dbType>");
				rawData.append("<busType>").append(itemCusType.getId()).append("</busType>");
				rawData.append("<idType>").append(itemDocumentType.getId()).append("</idType>");

				if (idNo != null && !idNo.isEmpty()) {
					rawData.append("<idNo>").append(idNo).append("</idNo>");
				} else {
					rawData.append("<idNo>" + "" + "</idNo>");
				}
				if (businessNo != null && !businessNo.isEmpty()) {
					rawData.append("<busPermitNo>").append(businessNo).append("</busPermitNo>");
				} else {
					rawData.append("<busPermitNo>" + "" + "</busPermitNo>");
				}
				if(dateSupply == null || dateSupply.isEmpty()){
					dateSupply = "";
				}
				
				rawData.append("<popIssueDate>").append(dateSupply).append("</popIssueDate>");
				if(endDate == null || endDate.isEmpty()){
					endDate = "";
				}
				rawData.append("<expireDate>").append(endDate).append("</expireDate>");
				
				rawData.append("<popNo>").append(edtHouseHold.getText().toString().trim()).append("</popNo>");
				rawData.append("<popIssuePlace>").append(edtLocationSupply.getText().toString().trim()).append("</popIssuePlace>");
				rawData.append("<reasonId>").append(itemReason.getId()).append("</reasonId>");
				rawData.append("<serviceType>").append(subService.getId()).append("</serviceType>");
				rawData.append("<subId>").append(objPrepairIdNo.getSubId()).append("</subId>");
				rawData.append("</cmMobileInput>");

				if ("1".equals(itemDbType.getId())) {
					rawData.append("</ws:repairChangeIDNo>");
				} else {
					rawData.append("</ws:updateRepairPrepaidErrorForSubPos>");
				}

				Log.i("LOG", "raw data" + rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String method = "";
				if ("1".equals(itemDbType.getId())) {
					method = "mbccs_repairChangeIDNo";
				} else {
					method = "mbccs_updateRepairPrepaidErrorForSubPos";
				}
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), method);
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("LOG", "erroeCode:  " + errorCode);
					Log.d("LOG", "description:  " + description);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// validate cap nhat thong tin
	private boolean validateUpdate() {

		if (vlBusinessLicenseError.getText() != null
				&& !vlBusinessLicenseError.getText().toString().isEmpty()) {
			if (edtBusinessLicense.getText() == null
					|| edtBusinessLicense.getText().toString().isEmpty()) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checksoGPKDdung),
						getString(R.string.app_name)).show();
				return false;
			}
		}

		if (vlIdNoError.getText() != null
				&& !vlIdNoError.getText().toString().isEmpty()) {
			if (edtIdNo.getText() == null
					|| edtIdNo.getText().toString().isEmpty()) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkidnoempty),
						getString(R.string.app_name)).show();
				return false;

			} else {
				if(typePaper != null && !typePaper.isEmpty()){
					if(typePaper.equals("1")){
						if (edtIdNo.getText().toString().trim().length() == 9
								|| edtIdNo.getText().toString().trim().length() == 12) {
						return true;
						}else{
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.checkCMT),
									getString(R.string.app_name)).show();
							return false;
						}
					}
				}
				
			}
		}

		if (dateFrom != null) {
			if (dateFrom.before(currentDate)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkngayhktsuasai),
						getString(R.string.app_name)).show();
				return false;

			}
		}
		if (dateTo != null) {

			if (currentDate.before(dateTo)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkngaycap),
						getString(R.string.app_name)).show();
				return false;
			}

		}

		if ("-1".equals(reasonId)) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkreason),
					getString(R.string.app_name)).show();
			return false;
		}
		return true;
	}

	// cap nhat thong tin cmnd_end
	private boolean validateSearch() {
		String isdn = edtIsdn.getText().toString().trim();
		if (isdn.equals("")) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.message_not_input_isdn),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		return true;
	}

	private final OnClickListener updateConfirmCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyntaskUpdateRepairPrepaidErrorForSubPre updateInfo = new AsyntaskUpdateRepairPrepaidErrorForSubPre(
						activity);
				updateInfo.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edt_document_end_date:
			DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
					getActivity(), AlertDialog.THEME_HOLO_LIGHT, endDatePickerListener, fromYear, fromMonth,
					fromDay);
			fromDateDialog.show();
			break;
		case R.id.edt_date_supply:
			DatePickerDialog supplyDateDialog = new FixedHoloDatePickerDialog(
					getActivity(),AlertDialog.THEME_HOLO_LIGHT, supplyDatePickerListener, toYear, toMonth,
					toDay);
			supplyDateDialog.show();
			break;
		case R.id.btn_apply:

			if (validateUpdate()) {
				CommonActivity.createDialog(activity,
						getString(R.string.message_confirm_update),
						getString(R.string.app_name),
						getString(R.string.say_ko), getString(R.string.say_co),
						null, updateConfirmCallBack).show();
			}
			break;
		case R.id.btn_cancel:
			activity.onBackPressed();
			break;
		case R.id.btn_search:

			resetData();

			if (validateSearch()) {
				if (CommonActivity.isNetworkConnected(activity)) {
					if (dbType == 1) {
						AsyntaskGetSubscriberByIsdnPre getSub = new AsyntaskGetSubscriberByIsdnPre(
								activity);
						getSub.execute();
					} else {
						AsyntaskGetSubscriberByIsdnPos getSubPos = new AsyntaskGetSubscriberByIsdnPos(
								activity);
						getSubPos.execute();
					}

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;
		default:
			break;
		}
	}

}
