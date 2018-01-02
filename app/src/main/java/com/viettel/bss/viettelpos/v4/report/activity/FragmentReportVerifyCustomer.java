package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerOJ;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.process.PrcDateCouple;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterVerifyCustomer;
import com.viettel.bss.viettelpos.v4.report.arr.ArrVerifyCustomerOJ;
import com.viettel.bss.viettelpos.v4.report.object.TypeVerOJ;
import com.viettel.bss.viettelpos.v4.report.object.VerifyCustomerOJ;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;

public class FragmentReportVerifyCustomer extends FragmentCommon {
	private EditText tvDateFrom;
    private EditText tvDateTo;
	private Button btnSearch/* , btnDelDateFrom, btnDelDateTo */;
	private TextView tvTitleList;
	private ExpandableListView exListVerCustomer;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.ver_report_customer);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getTask();
		idLayout = R.layout.layout_verify_customer;
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		tvDateFrom = (EditText) v.findViewById(R.id.txt_date_from);
		tvDateTo = (EditText) v.findViewById(R.id.txt_date_to);
		btnSearch = (Button) v.findViewById(R.id.btn_search);
		tvTitleList = (TextView) v.findViewById(R.id.txt_list_title);
		exListVerCustomer = (ExpandableListView) v
				.findViewById(R.id.list_ver_customer);
		exListVerCustomer.setDividerHeight(2);
		// exListVerCustomer.setOnItemClickListener(this);
		btnSearch.setOnClickListener(this);
		tvDateFrom.setOnClickListener(this);
		tvDateTo.setOnClickListener(this);

		prcDateCouple = new PrcDateCouple(getActivity(), tvDateFrom, tvDateTo,
				true, null, null);
		// setData4Views(setData4Test());
	}
	private int sum = 0;
	private final ArrayList<TypeVerOJ> p = new ArrayList<>();
	private final ArrayList<ArrVerifyCustomerOJ> c = new ArrayList<>();
	private void fillList(ArrayList<TypeVerOJ> arrTypeVer) {

		p.clear();
		c.clear();

		// Toast.makeText(act, arrTaskTypeOJs.size() + "", 1).show();
		for (TypeVerOJ obj : arrTypeVer) {
			p.add(obj);
			// Toast.makeText(act, obj.getLstTaskObjects().size() + "",
			// 1).show();
			ArrVerifyCustomerOJ child = new ArrVerifyCustomerOJ();
			child.setArrVerifyCustomerOJ(obj.getArrVerifyCustomerOJs());
			c.add(child);
			sum += child.getArrVerifyCustomerOJ().size();
		}
		tvTitleList.setText(act.getString(R.string.ver_all_must_) + ": " + sum);
		adapterVerifyCustomer = new AdapterVerifyCustomer(act,
				exListVerCustomer, p, c);

		adapterVerifyCustomer.setInflater((LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				getActivity());
		exListVerCustomer.setAdapter(adapterVerifyCustomer);
		// for (int count = 0; count < arrTypeVerifyCustomerOJs.size(); count++)
		// {
		// exListVerCustomer.expandGroup(count);
		// }
	}

	private PrcDateCouple prcDateCouple;
	private final ArrayList<VerifyCustomerOJ> arrVerOk = new ArrayList<>();
	private final ArrayList<VerifyCustomerOJ> arrVerNotOk = new ArrayList<>();
	private AdapterVerifyCustomer adapterVerifyCustomer;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private final String[] arrName = new String[]{"789", "987", "897",};
	private ArrayList<VerifyCustomerOJ> getArrReport(int size,
			String idLocalEmployee, String date) {
		// TODO Auto-generated method stub
		ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			// public CustomerOJ(String name, String addr, String tel) {
			CustomerOJ customerOJ = new CustomerOJ("012345"
					+ arrName[i % arrName.length],
					"Xuan Mai - Dong Da- Ha Noi", "0123456789");
			arrVerifyCustomerOJs.add(new VerifyCustomerOJ(customerOJ,
					idLocalEmployee, date));
		}
		return arrVerifyCustomerOJs;
	}

	// private ArrayList<TypeVerOJ> setData4Test() {
	// ArrayList<TypeVerOJ> arrTypeVer = new ArrayList<TypeVerOJ>();
	// ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs = getArrReport(4,
	// "id1234", "12/02/2015");
	// ArrayList<VerifyCustomerOJ> arrVerifyCustomerOJs_not_done = getArrReport(
	// 6, null, null);
	// arrTypeVer.add(new TypeVerOJ(0L, act
	// .getString(R.string.ver_not_done_),
	// arrVerifyCustomerOJs_not_done, false, R.color.brown_light));
	// arrTypeVer.add(new TypeVerOJ(0L, act
	// .getString(R.string.ver_all_done_), arrVerifyCustomerOJs, true,
	// -1));
	// return arrTypeVer;
	// }
    private int count;
	private final String tag = "verify customer";
	private void submit() {
		if (!CommonActivity.isNetworkConnected(act)) {
			CommonActivity.createAlertDialog(act, R.string.errorNetwork,
					R.string.app_name).show();
			return;
		}
		try {
			if (!prcDateCouple.isFullData()) {
				return;
			}
			if (!prcDateCouple.checkValidDate(true, Constant.arrResIdWarnDate)) {
				return;
			}
			if (!prcDateCouple.checkDayBetween(30, R.string.checktg)) {
				return;
			}
			if (// count > 0 &&
			prcDateCouple.checkOldDate()) {
				if (count > 0) {
					return;
				}

			}

			Log.e(tag, prcDateCouple.textDateFrom + "----"
					+ prcDateCouple.textDateTo
					+ "-----------------------submit ");
			// toast(prcDateCouple.textDateFrom + "----"
			// + prcDateCouple.textDateTo
			// + "-----------------------submit ");
			// toast("-----------------------submit 1");

			resetList();
			executeAsync();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void resetList() {
		// TODO Auto-generated method stub
		if (arrVerOk != null) {
			arrVerOk.clear();
		}
		if (arrVerNotOk != null) {
			arrVerNotOk.clear();
		}
		if (adapterVerifyCustomer != null) {
			adapterVerifyCustomer.notifyDataSetChanged();

		}
		adapterVerifyCustomer = null;
		sum = 0;
		tvTitleList.setText(act.getString(R.string.ver_all_must_) + ": " + sum);
	}
	private AsyncTaskLoadVerifiedCustomer asyncTaskLoadVerifiedCustomer;
	private void executeAsync() {
		Log.e(tag, "ex async");
		// TODO Auto-generated method stub
		asyncTaskLoadVerifiedCustomer = new AsyncTaskLoadVerifiedCustomer(act,
				false);
		asyncTaskLoadVerifiedCustomer.execute();
		count++;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // CommonActivity.hideKeyboard(txtDateGift, act);
		// cusCareObj = arrCusCareObjs.get(posistion);
		// Log.e(tag, cusCareObj]]]]=.getCustomerOJ().getAllContent());
		// Bundle mBundle = new Bundle();
		// mBundle.putSerializable(KEY_CUS_CARE, cusCareObj);
		// fragmentCusCareInfo = new FragmentCusCareInfoSolu();
		// fragmentCusCareInfo.setArguments(mBundle);
		// ReplaceFragment.replaceFragment(getActivity(), fragmentCusCareInfo,
		// false);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
			Log.e(tag, "net disconn");
			CommonActivity.createAlertDialog(getActivity(),
					R.string.errorNetwork, R.string.app_name).show();
			return;
		} else {
			Log.e(tag, "net ok");
		}
		int id = arg0.getId();
		switch (id) {
			case R.id.txt_date_from :
				// dpdCancel = false;
				Date mDateFrom = prcDateCouple.mDateFrom;
				DatePickerDialog dpd = new FixedHoloDatePickerDialog(act, AlertDialog.THEME_HOLO_LIGHT,
						prcDateCouple.onDateSetListenerFrom,
						mDateFrom.getYear(), mDateFrom.getMonth(),
						mDateFrom.getDate());
				dpd.show();
				if (Build.VERSION.SDK_INT >= 18
				// Build.VERSION_CODES.JELLY_BEAN
				) {
					dpd.setCancelable(false);
				}

				break;
			case R.id.txt_date_to :
				Date mDateTo = prcDateCouple.mDateTo;
				DatePickerDialog dpd1 = new FixedHoloDatePickerDialog(act,AlertDialog.THEME_HOLO_LIGHT,
						prcDateCouple.onDateSetListenerTo, mDateTo.getYear(),
						mDateTo.getMonth(), mDateTo.getDate());
				dpd1.show();
				if (Build.VERSION.SDK_INT >= 18
				// Build.VERSION_CODES.JELLY_BEAN
				) {
					dpd1.setCancelable(false);
				}
				break;
			case R.id.btn_search :
				submit();
				break;
			// case R.id.btn_del_from :
			// initTimeFrom();
			// btnDelDateFrom.setVisibility(View.GONE);
			// break;
			// case R.id.btn_del_to :
			// initTimeTo();
			// btnDelDateTo.setVisibility(View.GONE);
			// break;
			default :
				break;
		}

	}

	private class AsyncTaskLoadVerifiedCustomer
			extends
				AsyncTask<Void, Void, String> {
		private final Context context;
		final ProgressDialog progress;
		final boolean first;
		private String original;
		final XmlDomParse parse = new XmlDomParse();
		String description = "";
		String errorCode = "";
		// String areaCode, textDate;
		public AsyncTaskLoadVerifiedCustomer(Context context, boolean first) {
			this.context = context;
			this.first = first;
			// this.areaCode = areaCode;
			// this.textDate = textDate;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected String doInBackground(Void... params) {

			obtainListCusCareGift();
			return null;

		}

		private ArrayList<TypeVerOJ> setArrTypes() {
			ArrayList<TypeVerOJ> arrTypeVerifyCustomerOJs = new ArrayList<>();

			arrTypeVerifyCustomerOJs.add(new TypeVerOJ(0L, act
					.getString(R.string.ver_not_done_), arrVerNotOk, false,
					R.color.brown_light));
			arrTypeVerifyCustomerOJs.add(new TypeVerOJ(0L, act
					.getString(R.string.ver_all_done_), arrVerOk, true, -1));
			return arrTypeVerifyCustomerOJs;
		}

		@Override
		protected void onPostExecute(String result) {
			// Log.e("TAG", " Ket qua cap nhat cham soc " + result);
			// Log.e(tag, areaCode + " ... on post");
			this.progress.dismiss();

			// setData4Test();
			if (errorCode.equals(Constant.INVALID_TOKEN2)
					&& description != null && !description.isEmpty()) {

				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						description, getResources()
								.getString(R.string.app_name), moveLogInAct);
				dialog.show();
			}
			if (arrVerOk.size() == 0 && arrVerNotOk.size() == 0) {
				CommonActivity.createAlertDialog(act, R.string.no_data,
						R.string.app_name).show();
				return;
			}

			fillList(setArrTypes());
			// //////////////////////////
			// if (result == null) {
			// return;
			// }
			// if (result.size() > 0) {
			// // === fill data for list report
			// // khai bao adapter va set adapter vao cai list
			// // result.get(0)
			// adapterCusCare = new AdapterCusCare(act, result,
			// Constant.CUSCARE_GIFT);
			// lvCusCare.setAdapter(adapterCusCare);
			// if (count > 1) {
			// tvTitleList.setText(R.string.result_search);
			// }
			// } else {
			// if (errorCode.equals(Constant.INVALID_TOKEN2)
			// && description != null && !description.isEmpty()) {
			//
			// Dialog dialog = CommonActivity
			// .createAlertDialog(
			// getActivity(),
			// description,
			// getResources().getString(R.string.app_name),
			// moveLogInAct);
			// dialog.show();
			// } else {
			// // countOrderItem = 0;
			// // addManagerList();
			// CommonActivity.createAlertDialog(act, R.string.no_data,
			// R.string.app_name).show();
			// }
			// }
		}

		private void obtainListCusCareGift() {
			// TODO Auto-generated method stub
			Log.e(tag, "send request");
			// ArrayList<CusCareObj> arrCareObjs = new ArrayList<CusCareObj>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListVerContract");
				input.addParam("token", Session.getToken());
				// input.addParam("areaCode", prcArea.areaCode);
				// <taskRoadId>?</taskRoadId>
				input.addParam("fromDate", prcDateCouple.textDateFrom);
				input.addParam("toDate", prcDateCouple.textDateTo);
				//
				// input.addParam("cellId", "");

				// public CustomerOJ(String name, String addr, String tel) {

				String envelope = input.buildInputGateway();
				Log.e("envlop VER", envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getListVerContract");
				Log.e("response VER", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Document doc = parse.getDomElement(original);
				NodeList nlReturn = doc.getElementsByTagName("return");
				// NodeList nlCustomer = doc.getElementsByTagName("ccCustomer");
				NodeList nlVerOk = doc.getElementsByTagName("lstVerifyOK");
				NodeList nlVerNotOk = doc.getElementsByTagName("lstVerifyNOK");
				Log.e(tag, nlReturn.getLength() + "...return size 1");
				// Log.e(tag, nlCustomer.getLength() + "...nodeCustomer leng");
				for (int i = 0; i < nlReturn.getLength(); i++) {
					// RETURN
					Element elReturn = (Element) nlReturn.item(i);
					errorCode = parse.getValue(elReturn, "errorCode");
					Log.e("errorCode", errorCode);
					description = parse.getValue(elReturn, "description");
					Log.e("description", description);
					// listGift


					boolean isContOk = true;
					boolean isContNotOk = true;
					for (int j = 0, j_ = 0; /*
											 * j <
											 * nlVerOk.getLength()||j_<nlVerNotOk
											 * .getLength()
											 */; j++, j_++) {
						Log.e(tag, j + "...j");

						if (j < nlVerOk.getLength()) {
							xx(arrVerOk, nlVerOk, j, parse);
						} else {
							isContOk = false;
						}

						if (j_ < nlVerNotOk.getLength()) {
							xx(arrVerNotOk, nlVerNotOk, j_, parse);
						} else {
							isContNotOk = false;
						}

						if (!isContNotOk && !isContOk) {
							break;
						}
						// Element e1 = (Element) nlVerOk.item(j);
						//
						// Element elCustomer = (Element) nlVerOk.item(j);
						// String cusName = parse.getValue(elCustomer,
						// "contractNo");//HIEN THI DAM NHAT
						// String cusAddr = parse.getValue(elCustomer,
						// "address");
						// // String cusTel = parse.getValue(elCustomer, "");
						// Log.e(tag, cusName + ",," + cusAddr + ",,"
						// + "...........customer ");
						//
						// String idLocalEmployee = parse.getValue(e1,
						// "collectionStaffCode");
						// String dateVerified = parse
						// .getValue(e1, "verifyDate");
						// // String cusGiftList = parse.getValue(e1,
						// // "cusSoluContent");
						//
						// CustomerOJ customerOJ = new CustomerOJ(cusName,
						// cusAddr, "09..12");
						//
						// VerifyCustomerOJ verifyCustomerOJ = new
						// VerifyCustomerOJ(
						// customerOJ, idLocalEmployee, dateVerified);
						// // Log.e(tag, cusCareObj.getAllContent());
						// arrVerOk.add(verifyCustomerOJ);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// return arrVerOk;
		}
		// boolean[] arrCusGiftProgress = new boolean[]{false, true};

	}
	// ArrayList<VerifyCustomerOJ> arr
	private void xx(ArrayList<VerifyCustomerOJ> arrVerOk, NodeList nlVerOk,
			int j, XmlDomParse parse) {
		// TODO Auto-generated method stub
		Element e1 = (Element) nlVerOk.item(j);

		Element elCustomer = (Element) nlVerOk.item(j);
		String cusName = parse.getValue(elCustomer, "contractNo");// HIEN THI
																	// DAM NHAT
		String cusAddr = parse.getValue(elCustomer, "address");
		// String cusTel = parse.getValue(elCustomer, "");
		Log.e(tag, cusName + ",," + cusAddr + ",," + "...........customer ");

		String idLocalEmployee = parse.getValue(e1, "collectionStaffCode");
		String dateVerified = parse.getValue(e1, "verifyDate");
		// String cusGiftList = parse.getValue(e1,
		// "cusSoluContent");

		CustomerOJ customerOJ = new CustomerOJ(cusName, cusAddr, "09..12");

		VerifyCustomerOJ verifyCustomerOJ = new VerifyCustomerOJ(customerOJ,
				idLocalEmployee, dateVerified);
		// Log.e(tag, cusCareObj.getAllContent());
		arrVerOk.add(verifyCustomerOJ);
	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
