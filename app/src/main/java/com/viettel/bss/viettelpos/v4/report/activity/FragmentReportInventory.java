package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterExportInventory;
import com.viettel.bss.viettelpos.v4.report.object.ReportOutletBean;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentReportInventory extends Fragment implements OnClickListener {

	private View mView;
	private Activity activity;
	private Spinner spiner_amazing;
	private ListView lv_report;
	private Button btn_report;

	private Button btnHome;
	private TextView txtNameActionBar;

	private final ArrayList<AreaObj> listAmazing = new ArrayList<>(); // ky thu
	private ArrayList<ReportOutletBean> listReportObject;
	private AdapterExportInventory adapterExportInventory;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_report_inventory, container, false);
			lv_report = (ListView) mView.findViewById(R.id.lv_report);
			spiner_amazing = (Spinner) mView.findViewById(R.id.spiner_amazing);
			btn_report = (Button) mView.findViewById(R.id.btn_report);
			addItemAmazingToSpiner();
		}
		btn_report.setOnClickListener(this);
		return mView;
	}

	private void addItemAmazingToSpiner() {

		Calendar calendar = Calendar.getInstance();
		String curentYear = calendar.get(Calendar.YEAR) + "";
		String curentMonth = calendar.get(Calendar.MONTH) + 1 + "";
		String curentDay = calendar.get(Calendar.DAY_OF_MONTH) + "";

		int lastCurentDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Log.d(Constant.TAG, "last day of month: " + lastCurentDayOfMonth);
		AreaObj areaObject = new AreaObj();

		if (Integer.parseInt(curentDay) < 10) {
			curentDay = "0" + curentDay;
		}

		if (Integer.parseInt(curentMonth) < 10) {
			curentMonth = "0" + curentMonth;
		}

		areaObject.setName(curentMonth + "/" + curentYear);
		areaObject.setFromDate("01" + "/" + curentMonth + "/" + curentYear);
		areaObject.setToDate(lastCurentDayOfMonth + "/" + curentMonth + "/" + curentYear);
		listAmazing.add(areaObject);

		for (int i = 1; i < 16; i++) {
			calendar.add(Calendar.MONTH, -1);
			String befor1MonthYear = calendar.get(Calendar.YEAR) + "";
			String befor1Month = calendar.get(Calendar.MONTH) + 1 + "";
			String befor1MonthDay = calendar.get(Calendar.DAY_OF_MONTH) + "";

			int lastDayBefor1Month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			if (Integer.parseInt(befor1MonthDay) < 10) {
				befor1MonthDay = "0" + befor1MonthDay;
			}

			if (Integer.parseInt(befor1Month) < 10) {
				befor1Month = "0" + befor1Month;
			}

			AreaObj areabeforObject = new AreaObj();
			areabeforObject.setName(befor1Month + "/" + befor1MonthYear);

			areabeforObject.setFromDate("01" + "/" + befor1Month + "/" + befor1MonthYear);
			areabeforObject.setToDate(lastDayBefor1Month + "/" + befor1Month + "/" + befor1MonthYear);

			listAmazing.add(areabeforObject);
		}

		AdapterProvinceSpinner adapterAmazing = new AdapterProvinceSpinner(listAmazing, activity);
		spiner_amazing.setAdapter(adapterAmazing);
	}

	@Override
	public void onResume() {

		MainActivity.getInstance().setTitleActionBar(R.string.report_inventory);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_report:
			onExportReport();
			break;

		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;

		default:
			break;
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

	private void onExportReport() { 

		 listReportObject = new ArrayList<>();
		 adapterExportInventory = new AdapterExportInventory(listReportObject,
		 activity);
		 lv_report.setAdapter(adapterExportInventory);
		 int positionAmazing = spiner_amazing.getSelectedItemPosition();
		 AreaObj areaObjectAmazing = listAmazing.get(positionAmazing);
		 if (CommonActivity.isNetworkConnected(activity)) {
		 AsyntaskExportReport asynctaskExportReport = new
		 AsyntaskExportReport(areaObjectAmazing, activity);
		 asynctaskExportReport.execute();
		 } else {
		 CommonActivity.createAlertDialog(activity,
		 getResources().getString(R.string.errorNetwork),
		 getResources().getString(R.string.app_name)).show();
		 }
	}

	@SuppressWarnings("unused")
	private class AsyntaskExportReport extends AsyncTask<Void, Void, ArrayList<ReportOutletBean>> {

		private final Activity mActivity;
		private final AreaObj areaBillDate;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyntaskExportReport(AreaObj areaBillDate, Activity mActivity) {
			this.areaBillDate = areaBillDate;
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ReportOutletBean> doInBackground(Void... params) {

			return doGetListReportObject();
		}

		@Override
		protected void onPostExecute(ArrayList<ReportOutletBean> result) {

			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					listReportObject = result;
				} else {
					listReportObject.clear();
					CommonActivity.createAlertDialog(mActivity, getResources().getString(R.string.not_result_search),
							getResources().getString(R.string.app_name)).show();
				}

				adapterExportInventory = new AdapterExportInventory(listReportObject, mActivity);
				lv_report.setAdapter(adapterExportInventory);

			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<ReportOutletBean> doGetListReportObject() {
			ArrayList<ReportOutletBean> listReportObject = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getUnsoldGoods");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getUnsoldGoods>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<fromDate>").append(areaBillDate.getFromDate()).append("</fromDate>");
				rawData.append("<toDate>").append(areaBillDate.getToDate()).append("</toDate>");
				rawData.append("</input>");
				rawData.append("</ws:getUnsoldGoods>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getUnsoldGoods");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstReportOutletBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReportOutletBean reportOutletBean = new ReportOutletBean();
						reportOutletBean.setStockTypeName(parse.getValue(e1, "stockTypeName"));
						reportOutletBean.setStockTypeId(parse.getValue(e1, "stockTypeId"));
						reportOutletBean.setExportOtherQuantity(parse.getValue(e1, "exportOtherQuantity"));
						reportOutletBean.setExportCusQuantity(parse.getValue(e1, "exportCusQuantity"));
						reportOutletBean.setUnsoldGood(parse.getValue(e1, "unsoldGood"));
						reportOutletBean.setImportQuantity(parse.getValue(e1, "importQuantity"));
						listReportObject.add(reportOutletBean);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listReportObject;

		}

	}

}
