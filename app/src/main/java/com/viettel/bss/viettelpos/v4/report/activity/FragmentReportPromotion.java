package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.report.asynctask.GetListCommTransaction;
import com.viettel.bss.viettelpos.v4.report.asynctask.GetTotalMoneyBonus;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentReportPromotion extends Fragment implements
		OnClickListener {

	private Activity activity;
	private View mView;
	private Spinner spiner_amazing;
	private Spinner spiner_reportType;
	private Button btn_report;
	private Button btnHome;
	private TextView tv_money_having;
	private TextView tv_money_anypay;

	private TextView txtNameActionBar;
	private View btn_info;
	private final ArrayList<AreaObj> listAmazing = new ArrayList<>(); // ky thu
	private final ArrayList<AreaObj> listReportType = new ArrayList<>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.activity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_report_promotion,
					container, false);
			spiner_amazing = (Spinner) mView.findViewById(R.id.spiner_amazing);
			spiner_reportType = (Spinner) mView
					.findViewById(R.id.spiner_reportType);
			btn_report = (Button) mView.findViewById(R.id.btn_report);
			btn_info = mView.findViewById(R.id.lnInfoMoney);
			tv_money_having = (TextView) mView
					.findViewById(R.id.tv_money_having);
			tv_money_anypay = (TextView) mView
					.findViewById(R.id.tv_money_anypay);
			addAmazingAndReportTypeToSpiner();
		}
		btn_info.setOnClickListener(this);
		btn_report.setOnClickListener(this);

		return mView;
	}

	private void addAmazingAndReportTypeToSpiner() {

		Calendar calendar = Calendar.getInstance();
		String curentYear = calendar.get(Calendar.YEAR) + "";
		String curentMonth = calendar.get(Calendar.MONTH) + 1 + "";
		String curentDay = calendar.get(Calendar.DAY_OF_MONTH) + "";

		AreaObj areaObject = new AreaObj();

		if (Integer.parseInt(curentDay) < 10) {
			curentDay = "0" + curentDay;
		}

		if (Integer.parseInt(curentMonth) < 10) {
			curentMonth = "0" + curentMonth;
		}

		int lastCurentDayOfMonth = calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH);
		Log.d(Constant.TAG, "last day of month: " + lastCurentDayOfMonth);
		areaObject.setName(curentMonth + "/" + curentYear);
		areaObject.setAreaCode(curentDay + curentMonth + curentYear);
		areaObject.setFromDate("01" + "/" + curentMonth + "/" + curentYear);
		areaObject.setToDate(lastCurentDayOfMonth + "/" + curentMonth + "/"
				+ curentYear);
		listAmazing.add(areaObject);

		for (int i = 1; i < 16; i++) {
			calendar.add(Calendar.MONTH, -1);
			String befor1MonthYear = calendar.get(Calendar.YEAR) + "";
			String befor1Month = calendar.get(Calendar.MONTH) + 1 + "";
			String befor1MonthDay = calendar.get(Calendar.DAY_OF_MONTH) + "";

			int lastDayBefor1Month = calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH);

			if (Integer.parseInt(befor1MonthDay) < 10) {
				befor1MonthDay = "0" + befor1MonthDay;
			}

			if (Integer.parseInt(befor1Month) < 10) {
				befor1Month = "0" + befor1Month;
			}

			AreaObj areabeforObject = new AreaObj();
			areabeforObject.setName(befor1Month + "/" + befor1MonthYear);
			areabeforObject.setAreaCode(befor1MonthDay + befor1Month
					+ befor1MonthYear);

			areabeforObject.setFromDate("01" + "/" + befor1Month + "/"
					+ befor1MonthYear);
			areabeforObject.setToDate(lastDayBefor1Month + "/" + befor1Month
					+ "/" + befor1MonthYear);

			listAmazing.add(areabeforObject);
		}

		AdapterProvinceSpinner adapterAmazing = new AdapterProvinceSpinner(
				listAmazing, activity);
		spiner_amazing.setAdapter(adapterAmazing);

		// 0 tam tinh 1 da chot
		AreaObj reportProvisional = new AreaObj();
		reportProvisional.setName(getResources().getString(
				R.string.provisional_reportType));
		reportProvisional.setAreaCode("0");
		listReportType.add(reportProvisional);

		AreaObj reportFinalyzed = new AreaObj();
		reportFinalyzed.setName(getResources().getString(
				R.string.finalyzed_reportType));
		reportFinalyzed.setAreaCode("1");
		listReportType.add(reportFinalyzed);

		AdapterProvinceSpinner adapterReportType = new AdapterProvinceSpinner(
				listReportType, activity);
		spiner_reportType.setAdapter(adapterReportType);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lnInfoMoney:
			if ("0".equals(tv_money_having.getText().toString())
					|| CommonActivity.isNullOrEmpty(tv_money_having.getText()
							.toString())) {
				return;
			}
			int positionAmazing = spiner_amazing.getSelectedItemPosition();
			int positionFinalized = spiner_reportType.getSelectedItemPosition();
			AreaObj areaObjectAmazing = listAmazing.get(positionAmazing);
			AreaObj areaFinalyzed = listReportType.get(positionFinalized);
			// tv_money_having.setText("0");
			// tv_money_anypay.setText("0");

			// GetTotalMoneyBonus asy = new GetTotalMoneyBonus(activity,
			// areaObjectAmazing.getAreaCode(), areaFinalyzed.getAreaCode(),
			// areaObjectAmazing.getFromDate(), areaObjectAmazing.getToDate(),
			GetListCommTransaction asy = new GetListCommTransaction(
					getActivity(), areaObjectAmazing.getAreaCode(),
					areaFinalyzed.getAreaCode());
			asy.execute();
			break;
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

	// private ListView lvInfoReportMoney;

	@SuppressWarnings("unused")
	// private void showListInfoMoneyhaving() {
	// final Dialog dialog = new Dialog(activity);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// dialog.setContentView(R.layout.layout_list_info_report_money_having);
	// WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	// lp.copyFrom(dialog.getWindow().getAttributes());
	// lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	// lp.height = WindowManager.LayoutParams.MATCH_PARENT;
	// dialog.getWindow().setAttributes(lp);
	//
	// lvInfoReportMoney = (ListView) dialog
	// .findViewById(R.id.lvInfoReportMoney);
	// Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
	// btnClose.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// dialog.dismiss();
	// }
	// });
	// dialog.show();
	// }
	@Override
	public void onResume() {

		MainActivity.getInstance().setTitleActionBar(R.string.report_promotion);
		super.onResume();
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	@SuppressWarnings("unused")
	private void onExportReport() {
		int positionAmazing = spiner_amazing.getSelectedItemPosition();
		int positionFinalized = spiner_reportType.getSelectedItemPosition();
		AreaObj areaObjectAmazing = listAmazing.get(positionAmazing);
		AreaObj areaFinalyzed = listReportType.get(positionFinalized);
		tv_money_having.setText("0");
		tv_money_anypay.setText("0");
		// BonusCommTransaction bonusCommtransaction = new
		// BonusCommTransaction();
		// bonusCommtransaction.setBillDate(areaObjectAmazing.getAreaCode()); //
		// ky
		// // thu
		// bonusCommtransaction.setService(areaFinalyzed.getAreaCode()); // loai
		// // bao
		// // cao
		// bonusCommtransaction.setFromDate(areaObjectAmazing.getFromDate());
		// bonusCommtransaction.setToDate(areaObjectAmazing.getToDate());
		//
		// AsyntaskExportReport asyntaskExportReport = new AsyntaskExportReport(
		// bonusCommtransaction, activity);
		// asyntaskExportReport.execute();

		GetTotalMoneyBonus asy = new GetTotalMoneyBonus(activity,
				areaObjectAmazing.getAreaCode(), areaFinalyzed.getAreaCode(),
				areaObjectAmazing.getFromDate(), areaObjectAmazing.getToDate(),
				tv_money_anypay, tv_money_having);
		asy.execute();
	}

}
