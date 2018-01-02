package com.viettel.bss.viettelpos.v4.report.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterResultEquiment;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterSalePointImage;
import com.viettel.bss.viettelpos.v4.report.object.ReportImagePayment;
import com.viettel.bss.viettelpos.v4.report.object.ResultEquipments;
import com.viettel.bss.viettelpos.v4.report.object.SalePointImageObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint("SimpleDateFormat")
public class FragmentReportImagePayment extends Fragment implements OnClickListener, OnItemClickListener {

	private TextView txtNameActionBar;
	private Button btnHome;
	private Spinner spinnerTypeObject;
	private Button btn_export_report;
	private View mView; 
	private Activity activity;
	private final ArrayList<AreaObj> mListTypeReport = new ArrayList<>();
	private ArrayList<ReportImagePayment> mListReportImagePayment = new ArrayList<>();
	private ArrayList<ResultEquipments> mListResultEquipments = new ArrayList<>();

	private TextView tv_sale_point_not_update;
	private TextView tv_transaction_not_update;
	private TextView tv_employer_not_update;
	private TextView tv_sale_point_update;
	private TextView tv_transaction_update;
	private TextView tv_employer_update;

	private LinearLayout linealayout_sale_point_not_update;
	private LinearLayout linealayout_transaction_not_update;
	private LinearLayout linealayout_employer_not_update;
	private LinearLayout linealayout_sale_point_update;
	private LinearLayout linealayout_transaction_update;
	private LinearLayout linealayout_employer_update;
	private ReportImagePayment reportImagePaymentObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.report_layout_image_payment, container, false);
			spinnerTypeObject = (Spinner) mView.findViewById(R.id.spinnerTypeObject);
			btn_export_report = (Button) mView.findViewById(R.id.btn_export_report); 

			tv_sale_point_not_update = (TextView) mView.findViewById(R.id.tv_sale_point_not_update);
			tv_transaction_not_update = (TextView) mView.findViewById(R.id.tv_transaction_not_update);
			tv_employer_not_update = (TextView) mView.findViewById(R.id.tv_employer_not_update);
			tv_sale_point_update = (TextView) mView.findViewById(R.id.tv_sale_point_update);
			tv_transaction_update = (TextView) mView.findViewById(R.id.tv_transaction_update);
			tv_employer_update = (TextView) mView.findViewById(R.id.tv_employer_update);

			linealayout_sale_point_not_update = (LinearLayout) mView
					.findViewById(R.id.linealayout_sale_point_not_update);
			linealayout_transaction_not_update = (LinearLayout) mView
					.findViewById(R.id.linealayout_transaction_not_update);
			linealayout_employer_not_update = (LinearLayout) mView.findViewById(R.id.linealayout_employer_not_update);
			linealayout_sale_point_update = (LinearLayout) mView.findViewById(R.id.linealayout_sale_point_update);
			linealayout_transaction_update = (LinearLayout) mView.findViewById(R.id.linealayout_transaction_update);
			linealayout_employer_update = (LinearLayout) mView.findViewById(R.id.linealayout_employer_update);
		}
		
		linealayout_sale_point_not_update.setOnClickListener(this);
		linealayout_transaction_not_update.setOnClickListener(this);
		linealayout_employer_not_update.setOnClickListener(this);
		linealayout_sale_point_update.setOnClickListener(this);
		linealayout_transaction_update.setOnClickListener(this);
		linealayout_employer_update.setOnClickListener(this);

		btn_export_report.setOnClickListener(this);
		addItemToSpinner();

		return mView;
	}

	private void addItemToSpinner() {
		mListTypeReport.clear();

		AreaObj areaTypeReportExpression = new AreaObj();
		areaTypeReportExpression.setName(getResources().getString(R.string.title_Expression));
		areaTypeReportExpression.setAreaCode("1");
		mListTypeReport.add(areaTypeReportExpression);

		AreaObj areaTypeReportTool = new AreaObj();
		areaTypeReportTool.setName(getResources().getString(R.string.title_type_tool));
		areaTypeReportTool.setAreaCode("2");
		mListTypeReport.add(areaTypeReportTool);

		AdapterProvinceSpinner adapterContractType = new AdapterProvinceSpinner(mListTypeReport, getActivity());
		spinnerTypeObject.setAdapter(adapterContractType);

		spinnerTypeObject.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.title_report_detail);
		super.onResume();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_export_report: 
			if (CommonActivity.isNetworkConnected(activity)) { 
				reportImagePaymentObject = null;
				int indexSelection = spinnerTypeObject.getSelectedItemPosition();
				AreaObj areaObject = mListTypeReport.get(indexSelection);
				AsyncTaskExportReport asyntaskExportReport = new AsyncTaskExportReport(activity,
						areaObject.getAreaCode());
				asyntaskExportReport.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
		case R.id.btnHome:
			CommonActivity.callphone(activity, Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;
		case R.id.linealayout_sale_point_not_update:
			if (reportImagePaymentObject != null && Integer.parseInt(reportImagePaymentObject.getNoDb()) > 0) {
				onShowListInfo(reportImagePaymentObject, "2", "false", getString(R.string.sale_point_not_update));
			}

			break;
		case R.id.linealayout_transaction_not_update:

			if (reportImagePaymentObject != null && Integer.parseInt(reportImagePaymentObject.getNoGdx()) > 0) {
				onShowListInfo(reportImagePaymentObject, "1", "false", getString(R.string.transaction_not_update));
			}

			break;
		case R.id.linealayout_employer_not_update:
			if (reportImagePaymentObject != null && Integer.parseInt(reportImagePaymentObject.getNoNvdb()) > 0) {
				onShowListInfo(reportImagePaymentObject, "3", "false", getString(R.string.employer_not_update));
			}

			break;
		case R.id.linealayout_sale_point_update:
			if (reportImagePaymentObject != null && Integer.parseInt(reportImagePaymentObject.getNumDb()) > 0) {
				onShowListInfo(reportImagePaymentObject, "2", "true", getString(R.string.sale_point_update));
			}

			break;
		case R.id.linealayout_transaction_update:
			if (reportImagePaymentObject != null && Integer.parseInt(reportImagePaymentObject.getNumGdx()) > 0) {
				onShowListInfo(reportImagePaymentObject, "1", "true", getString(R.string.transaction_update));
			}

			break;
		case R.id.linealayout_employer_update:
			if (reportImagePaymentObject != null && Integer.parseInt(reportImagePaymentObject.getNumNvdb()) > 0) {
				onShowListInfo(reportImagePaymentObject, "3", "true", getString(R.string.employer_update));
			}

			break;

		default:
			break;
		}
	}

	// holder.linealayout_sale_point_not_update.setOnClickListener(new
	// View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// int noDbUpdate = Integer.parseInt(objectReport.getNoDb());
	// if (noDbUpdate > 0) {
	// onClickReport.onShowListInfo(objectReport,"2",
	// "false",mActivity.getString(R.string.sale_point_not_update));
	// }
	// }
	// });
	//
	//
	// holder.linealayout_transaction_not_update.setOnClickListener(new
	// View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// int noGDXUpdate = Integer.parseInt(objectReport.getNoGdx());
	// if (noGDXUpdate > 0) {
	// onClickReport.onShowListInfo(objectReport,"1",
	// "false",mActivity.getString(R.string.transaction_not_update));
	// }
	// }
	// });
	// holder.linealayout_employer_not_update.setOnClickListener(new
	// OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// int noNVDUpdate = Integer.parseInt(objectReport.getNoNvdb());
	// if (noNVDUpdate > 0) {
	// onClickReport.onShowListInfo(objectReport,"3",
	// "false",mActivity.getString(R.string.employer_not_update));
	// }
	// }
	// });
	// holder.linealayout_sale_point_update.setOnClickListener(new
	// OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// int numDBUpdate = Integer.parseInt(objectReport.getNumDb());
	// if (numDBUpdate > 0) {
	// onClickReport.onShowListInfo(objectReport,"2",
	// "true",mActivity.getString(R.string.sale_point_update));
	// }
	//
	//
	// }
	// });
	// holder.linealayout_transaction_update.setOnClickListener(new
	// OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	//
	// int numGDXUpdate = Integer.parseInt(objectReport.getNumGdx());
	// if (numGDXUpdate > 0) {
	// onClickReport.onShowListInfo(objectReport,"1",
	// "true",mActivity.getString(R.string.transaction_update));
	// }
	// }
	// });
	// holder.linealayout_employer_update.setOnClickListener(new
	// OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// int numEmployUpdate = Integer.parseInt(objectReport.getNumNvdb());
	// if (numEmployUpdate > 0) {
	// onClickReport.onShowListInfo(objectReport,"3",
	// "true",mActivity.getString(R.string.employer_update));
	// }
	// }
	// });

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ResultEquipments resultEquipments = mListResultEquipments.get(position);
		if (!resultEquipments.equals("1") && resultEquipments.getIsEquipment().equalsIgnoreCase("true")
				&& resultEquipments.getArrSalePoint().size() > 0) {
			showListImageSalePoint(resultEquipments.getArrSalePoint());
		}
	}

	@SuppressWarnings("unused")
	private void showListImageSalePoint(ArrayList<SalePointImageObject> arrSalePoint) {

		final Dialog diaLog = new Dialog(activity);
		diaLog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		diaLog.setContentView(R.layout.layout_list_image_sale_point);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(diaLog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		diaLog.getWindow().setAttributes(lp);

		ListView lvSalePointImage = (ListView) diaLog.findViewById(R.id.lvSalePointImage);
		Button btnClose = (Button) diaLog.findViewById(R.id.btnClose);

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				diaLog.dismiss();

			}
		});
		diaLog.show();
		AdapterSalePointImage adapterSalePointImage = new AdapterSalePointImage(arrSalePoint, activity);
		lvSalePointImage.setAdapter(adapterSalePointImage);
	}

	private ListView lvInfoReport;

	private void onShowListInfo(ReportImagePayment reportImagePayment, String chanelType, String isEquipment,
                                String keyClick) {

		final Dialog diaLog = new Dialog(activity);
		diaLog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		diaLog.setContentView(R.layout.layout_list_info_report);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(diaLog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		diaLog.getWindow().setAttributes(lp);

		lvInfoReport = (ListView) diaLog.findViewById(R.id.lvInfoReport);
		Button btnClose = (Button) diaLog.findViewById(R.id.btnClose);

		lvInfoReport.setOnItemClickListener(this);

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				diaLog.dismiss();

			}
		});

		diaLog.show();

		Log.d(Constant.TAG, "FragmentReportImagePayment onShowListInfo: "
				+ reportImagePayment.getmHasmapResultEquipment().containsKey(keyClick) + " key click: " + keyClick
				+ " size hashmap: " + reportImagePayment.getmHasmapResultEquipment().entrySet().size());

		if (reportImagePayment.getmHasmapResultEquipment().containsKey(keyClick)) {
			mListResultEquipments = reportImagePayment.getmHasmapResultEquipment().get(keyClick);
			AdapterResultEquiment adapterResultEquiment = new AdapterResultEquiment(mListResultEquipments, activity);
			lvInfoReport.setAdapter(adapterResultEquiment);
		} else {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyntaskGetReportDetail asyntaskGetReportDetail = new AsyntaskGetReportDetail(activity,
						reportImagePayment, chanelType, isEquipment, keyClick);
				asyntaskGetReportDetail.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
		}

	}

	public class AsyncTaskExportReport extends AsyncTask<Void, Void, ReportImagePayment> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		private final String objectType;

		public AsyncTaskExportReport(Context context, String objectType) {
			this.context = context;
			this.objectType = objectType;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ReportImagePayment doInBackground(Void... params) {
			return doExportReport(objectType);
		}

		@Override
		protected void onPostExecute(ReportImagePayment reportImagePayment) {
			super.onPostExecute(reportImagePayment);
			progress.dismiss();
			if (errorCode.equals("0")) {
				reportImagePaymentObject = reportImagePayment;
				if (reportImagePayment != null) {
					tv_sale_point_not_update.setText(reportImagePayment.getNoDb());
					tv_transaction_not_update.setText(reportImagePayment.getNoGdx());
					tv_employer_not_update.setText(reportImagePayment.getNoNvdb());
					tv_sale_point_update.setText(reportImagePayment.getNumDb());
					tv_transaction_update.setText(reportImagePayment.getNumGdx());
					tv_employer_update.setText(reportImagePayment.getNumNvdb());
				}

				// AdapterReportImagePayment adapterReportImagePayment = new
				// AdapterReportImagePayment(
				// mListReportImagePayment, activity,
				// FragmentReportImagePayment.this);
				// listview_report.setAdapter(adapterReportImagePayment);
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.reporttskenh));
					dialog.show();

				}
			}
		}

		public ReportImagePayment doExportReport(String objectType) {
			ReportImagePayment reportImagePayment = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getEquipmentGeneralByStaffId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getEquipmentGeneralByStaffId>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<objectType>").append(objectType).append("</objectType>");
				rawData.append("</input>");
				rawData.append("</ws:getEquipmentGeneralByStaffId>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getEquipmentGeneralByStaffId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("resultEquipment");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						reportImagePayment = new ReportImagePayment();
						reportImagePayment.setObjectType(objectType);
						reportImagePayment.setNoDb(parse.getValue(e1, "noDb"));
						reportImagePayment.setNoGdx(parse.getValue(e1, "noGdx"));
						reportImagePayment.setNoNvdb(parse.getValue(e1, "noNvdb"));
						reportImagePayment.setNumDb(parse.getValue(e1, "numDb"));
						reportImagePayment.setNumGdx(parse.getValue(e1, "numGdx"));
						reportImagePayment.setNumNvdb(parse.getValue(e1, "numNvdb"));
						reportImagePayment.setStaffCode(parse.getValue(e1, "staffCode"));
						reportImagePayment.setStaffId(parse.getValue(e1, "staffId"));
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return reportImagePayment;
		}
	}

	@SuppressWarnings("unused")
	private class AsyntaskGetReportDetail extends AsyncTask<Void, Void, ArrayList<ResultEquipments>> {
		final ProgressDialog progress;
		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ReportImagePayment reportObject;
		private final String channelType;
		private final String isEquipment;
		private int typeRequetDetail;
		private final String keyClick;

		public AsyntaskGetReportDetail(Activity mActivity, ReportImagePayment reportObject, String channelType,
				String isEquipment, String keyClick) {
			this.mActivity = mActivity;
			this.reportObject = reportObject;
			this.channelType = channelType;
			this.isEquipment = isEquipment;
			this.keyClick = keyClick;

			this.progress = new ProgressDialog(this.mActivity);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ResultEquipments> doInBackground(Void... params) {

			return getListResultEquipment(reportObject.getObjectType(), channelType, isEquipment);
		}

		@Override
		protected void onPostExecute(ArrayList<ResultEquipments> result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result.size() == 0) {
					CommonActivity
							.createAlertDialog(activity, getString(R.string.not_result), getString(R.string.app_name))
							.show();
				}
				reportObject.getmHasmapResultEquipment().put(keyClick, result);
				Log.d(Constant.TAG,
						"FragmentReportImagePayment onPostExecute: "
								+ reportObject.getmHasmapResultEquipment().entrySet().size() + " keyclick: " + keyClick
								+ " size hasmap: " + reportObject.getmHasmapResultEquipment().entrySet().size());
				mListResultEquipments = result;
				AdapterResultEquiment adapterResultEquiment = new AdapterResultEquiment(mListResultEquipments,
						activity);
				lvInfoReport.setAdapter(adapterResultEquiment);
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.reporttskenh));
					dialog.show();

				}
			}

		}

		private ArrayList<ResultEquipments> getListResultEquipment(String objectType, String channelType,
				String isEquipment) {
			ArrayList<ResultEquipments> arrResultEquipments = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getEquipmentDetailByStaffId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getEquipmentDetailByStaffId>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<objectType>").append(objectType).append("</objectType>");
				rawData.append("<channelType>").append(channelType).append("</channelType>");
				rawData.append("<isEquipment>").append(isEquipment).append("</isEquipment>");
				rawData.append("</input>");
				rawData.append("</ws:getEquipmentDetailByStaffId>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getEquipmentDetailByStaffId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstResultEquipments");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ResultEquipments resultEquipments = new ResultEquipments();
						resultEquipments.setAddress(parse.getValue(e1, "address"));
						resultEquipments.setAssetId(parse.getValue(e1, "assetId"));
						resultEquipments.setAssetName(parse.getValue(e1, "assetName"));
						resultEquipments.setManageAssetId(parse.getValue(e1, "manageAssetId"));
						resultEquipments.setQty(parse.getValue(e1, "qty"));
						resultEquipments.setStaffCode(parse.getValue(e1, "staffCode"));
						resultEquipments.setStaffId(parse.getValue(e1, "staffId"));
						resultEquipments.setStatus(parse.getValue(e1, "status"));
						resultEquipments.setStaffName(parse.getValue(e1, "staffName"));
						resultEquipments.setIsEquipment(isEquipment);
						resultEquipments.setObjectType(objectType);
						boolean isHasResultEquipments = false;
						Iterator itr = arrResultEquipments.iterator();
						while (itr.hasNext()) {
							ResultEquipments objectRequirement = (ResultEquipments) itr.next();
							if (resultEquipments.getStaffId().equalsIgnoreCase(objectRequirement.getStaffId())) {
								isHasResultEquipments = true;
								SalePointImageObject salePointImageObject = new SalePointImageObject();
								salePointImageObject.setSaleName(resultEquipments.getAssetName());
								salePointImageObject.setQuality(resultEquipments.getQty());
								salePointImageObject.setStatus(resultEquipments.getStatus());
								objectRequirement.getArrSalePoint().add(salePointImageObject);
							}
						}
						if (!isHasResultEquipments) {
							SalePointImageObject salePointImageObject = new SalePointImageObject();
							salePointImageObject.setSaleName(resultEquipments.getAssetName());
							salePointImageObject.setQuality(resultEquipments.getQty());
							salePointImageObject.setStatus(resultEquipments.getStatus());
							resultEquipments.getArrSalePoint().add(salePointImageObject);
							arrResultEquipments.add(resultEquipments);
						}
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return arrResultEquipments;
		}
	}
}
