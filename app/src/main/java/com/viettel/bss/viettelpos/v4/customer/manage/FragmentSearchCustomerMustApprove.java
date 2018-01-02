package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.CustomerMustApproveAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerApproveObj;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FragmentSearchCustomerMustApprove extends Fragment implements
		OnClickListener {
	private Activity activity;
    private View mView;
	private Spinner spnActionType, spnStatus;
	private EditText edtIsdnOrAccount, edtStartDate, edtEndDate;
	private ImageButton btnSearch;
	private ListView lvCustomer;
	private Button btnMoveApprove, btnApprove, btnCancelRecord;
	private CustomerMustApproveAdapter mAdapter;
	private List<CustomerApproveObj> lstCustomer = new ArrayList<>();
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
	private String toDate = "";
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final int TYPE_APPROVE = 2;
	private static final int TYPE_MOVE_APPROVE = 3;
	private static final int TYPE_CANCEL_RECORD = 0;
	private int TYPE;
	private Spinner spnReason;
	private final ArrayList<Spin> lstReason = new ArrayList<>();
	private final ArrayList<String> lstSubId = new ArrayList<>();
	private ProgressBar prbSearchReason;
	private List<CustomerApproveObj> lstCusTemp = new ArrayList<>();
	private boolean hasVtApprovedDocument = false;
	private boolean hasVtApprovedDocumentVtt = false;
	private Spin actionItem;
	private Spin actionStatus;
	private CustomerApproveObj customerApproveObj;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
        InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.search_customer_must_approve,
					container, false);
			init(mView);
		}
		setDefaultSpinner();
		// setDataTest();
		initValueDate();
		btnApprove.setOnClickListener(this);
		if (hasVtApprovedDocumentVtt) {
			btnMoveApprove.setVisibility(View.GONE);
		} else {
			btnMoveApprove.setOnClickListener(this);
		}
		btnCancelRecord.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		edtStartDate.setOnClickListener(this);
		edtEndDate.setOnClickListener(this);
		// spnActionType.setEnabled(false);
		// spnSubType.setEnabled(false);

		// lvCustomer.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// CustomerApproveObj obj = lstCustomer.get(position);
		// showDialogDetails(obj);
		//
		// }
		// });
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.approve_change_sim);
	}

	private void setOnClickDowload() {
		if (mAdapter != null) {
			mAdapter.setOnClickDownload(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.vl_item_download_image:
						int pos = (Integer) v.getTag();
						customerApproveObj = lstCustomer.get(pos);
						if (customerApproveObj.getFileLink() == null
								|| customerApproveObj.getFileLink().equals("")) {
							AsyntaskDowloadFile dowloadFile = new AsyntaskDowloadFile(
									activity, customerApproveObj);
							dowloadFile.execute();
						} else {
							openFile(customerApproveObj.getFileLink());
						}
						break;

					default:
						break;
					}

				}
			});
		}
	}

	private void setDataTest() {
		CustomerApproveObj obj = new CustomerApproveObj("0987653612",
				"BSS_HUYPQ1", "15/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj2 = new CustomerApproveObj("0987653612",
				"BSS_HUNGND22", "20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj3 = new CustomerApproveObj("0987653612", "OBJ3",
				"20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj4 = new CustomerApproveObj("0987653612", "OBJ4",
				"20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj5 = new CustomerApproveObj("0987653612", "OBJ5",
				"20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj6 = new CustomerApproveObj("0987653612", "OBJ6",
				"20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj7 = new CustomerApproveObj("0987653612", "OBJ7",
				"20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");
		CustomerApproveObj obj8 = new CustomerApproveObj("0987653612", "OBJ8",
				"20/10/2015", "Ä�Ã£ quÃ¡ háº¡n");

		lstCustomer.add(obj);
		lstCustomer.add(obj2);
		lstCustomer.add(obj3);
		lstCustomer.add(obj4);
		lstCustomer.add(obj5);
		lstCustomer.add(obj6);
		lstCustomer.add(obj7);
		lstCustomer.add(obj8);
		mAdapter = new CustomerMustApproveAdapter(activity, lstCustomer);
		lvCustomer.setAdapter(mAdapter);
	}

	private void init(View v) {
		spnActionType = (Spinner) v.findViewById(R.id.spnActionType);
		spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
		edtIsdnOrAccount = (EditText) v
				.findViewById(R.id.edt_search_isdn_account);
		edtStartDate = (EditText) v.findViewById(R.id.edtFromDate);
		edtEndDate = (EditText) v.findViewById(R.id.edtToDate);
		btnSearch = (ImageButton) v.findViewById(R.id.btn_search);
		lvCustomer = (ListView) v.findViewById(R.id.lv_customer_must_approve);
		btnApprove = (Button) v.findViewById(R.id.btn_approve);
		btnMoveApprove = (Button) v.findViewById(R.id.btn_move_approve);
		btnCancelRecord = (Button) v.findViewById(R.id.btn_cancel_record);

		// Check quyen_start
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		if (name.contains(";menu_approve;")) {
			btnApprove.setVisibility(View.VISIBLE);
		} else {
			btnApprove.setVisibility(View.GONE);
		}

		if (name.contains(";menu_trans_approve;")) {
			btnMoveApprove.setVisibility(View.VISIBLE);
		} else {
			btnMoveApprove.setVisibility(View.GONE);
		}

		if (name.contains(";menu_cancel_profile;")) {
			btnCancelRecord.setVisibility(View.VISIBLE);
		} else {
			btnCancelRecord.setVisibility(View.GONE);
		}

	}

	private void setDefaultSpinner() {
		ArrayList<Spin> lstActionType = new ArrayList<>();
		lstActionType.add(new Spin("11", getString(R.string.txt_change_sim)));
		Utils.setDataSpinner(activity, lstActionType, spnActionType);

		// Check quyen_start
		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		if (name.contains(";menu_approve;")
				&& name.contains(";menu_trans_approve;")
				&& name.contains(";menu_cancel_profile;")) {
			hasVtApprovedDocument = true;
		}

        hasVtApprovedDocumentVtt = name.contains(";menu_approve;")
                && name.contains(";menu_cancel_profile;")
                && !name.contains(";menu_trans_approve;");

		ArrayList<Spin> lstStatus = new ArrayList<>();
		lstStatus.add(new Spin("1", getString(R.string.txt_wait_approve)));
		if (hasVtApprovedDocumentVtt) {
			lstStatus.add(new Spin("3",
					getString(R.string.txt_trans_wait_approve)));
		}

		// lstStatus.add(new Spin("2", getString(R.string.txt_approved)));
		// lstStatus.add(new Spin("4",
		// getString(R.string.txt_approve_success)));
		// lstStatus.add(new Spin("0", getString(R.string.txt_cancel)));
		// lstStatus.add(new Spin("-1", getString(R.string.txt_quahan)));
		Utils.setDataSpinner(activity, lstStatus, spnStatus);
	}

	private void initValueDate() {
		Calendar cal = Calendar.getInstance();
		fromYear = cal.get(Calendar.YEAR);
		fromMonth = cal.get(Calendar.MONTH);
		fromDay = cal.get(Calendar.DAY_OF_MONTH);
		edtStartDate.setText(DateTimeUtils.convertDateTimeToString(
				cal.getTime(), "dd/MM/yyyy"));

		fromDate = edtStartDate.getText().toString();

		dateFromString = String.valueOf(fromYear) + "-" +
                (fromMonth + 1) + "-" + fromDay;
		try {

			dateFrom = sdf.parse(dateFromString);
			currentDate = sdf.parse(dateFromString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		toYear = cal.get(Calendar.YEAR);
		toMonth = cal.get(Calendar.MONTH);
		toDay = cal.get(Calendar.DAY_OF_MONTH);
		dateToString = String.valueOf(toYear) + "-" +
                (toMonth + 1) + "-" + toDay;
		try {
			dateTo = sdf.parse(dateToString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		edtEndDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));
		toDate = edtEndDate.getText().toString();
	}

	private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

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

			dateFromString = String.valueOf(fromYear) + "-" +
                    (fromMonth + 1) + "-" + fromDay;
			try {
				dateFrom = sdf.parse(dateFromString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edtStartDate.setText(strDate);
		}
	};
	private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

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
			toDate = strDate.toString();

			dateToString = String.valueOf(toYear) + "-" +
                    (toMonth + 1) + "-" + toDay;
			try {
				dateTo = sdf.parse(dateToString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edtEndDate.setText(strDate);
		}
	};

	// lay ra khoang cach giua 2 ngay
	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return diffInMillies / (24 * 60 * 60 * 1000);
	}

	private boolean validateSearch() {
		if (dateFrom != null && dateTo != null) {
			if (dateFrom.after(dateTo)) {
				Dialog dialog = CommonActivity.createAlertDialog(
						getActivity(),
						getResources().getString(
								R.string.starttime_small_endtime),
						getResources().getString(R.string.app_name));
				dialog.show();
				return false;
			} else if (currentDate.before(dateFrom)) {
				Dialog dialog = CommonActivity.createAlertDialog(
						getActivity(),
						getResources().getString(
								R.string.report_warn_fromdate_now),
						getResources().getString(R.string.app_name));
				dialog.show();
				return false;
			} else if (currentDate.before(dateTo)) {
				Dialog dialog = CommonActivity.createAlertDialog(
						getActivity(),
						getResources().getString(
								R.string.report_warn_todate_now),
						getResources().getString(R.string.app_name));
				dialog.show();
				return false;
			} else if (dateFrom.before(currentDate)) {
				long day = getDateDiff(dateFrom, new Date(), TimeUnit.MINUTES);
				if (day > 90) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(
									R.string.starttime_90_current_time),
							getResources().getString(R.string.app_name));
					dialog.show();
					return false;
				}
			} else if (dateFrom.before(dateTo)) {
				long day = getDateDiff(dateFrom, dateTo, TimeUnit.MINUTES);
				if (day > 30) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.checkrequest30),
							getResources().getString(R.string.app_name));
					dialog.show();
					return false;

				}
			}
		}

		return true;
	}

	private void showDialogDetails(CustomerApproveObj obj) {
		final Dialog dialog = new Dialog(activity);
		dialog.setCancelable(false);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.customer_must_approve_details);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		Window window = dialog.getWindow();
		lp.copyFrom(window.getAttributes());
		// This makes the dialog take up the full width
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);

		TextView vlCusName = (TextView) dialog
				.findViewById(R.id.vl_detail_staff_name);
		TextView vlCusType = (TextView) dialog
				.findViewById(R.id.vl_detail_customer_type);
		TextView vlCusCode = (TextView) dialog
				.findViewById(R.id.vl_detail_customer_code);
		TextView vlCusBirthday = (TextView) dialog
				.findViewById(R.id.vl_detail_birthday);
		TextView vlCusSex = (TextView) dialog.findViewById(R.id.vl_detail_sex);
		TextView vlCusCountry = (TextView) dialog
				.findViewById(R.id.vl_detail_country);
		TextView vlBussinessNumber = (TextView) dialog
				.findViewById(R.id.vl_detail_bussiness_number);
		TextView vlDocumentType = (TextView) dialog
				.findViewById(R.id.vl_detail_document_type);
		TextView vlIdNo = (TextView) dialog.findViewById(R.id.vl_detail_id_no);
		TextView vlLocationSupplyId = (TextView) dialog
				.findViewById(R.id.vl_detail_location_supply);
		TextView vlDateSupplyId = (TextView) dialog
				.findViewById(R.id.vl_detail_date_supply);
		TextView vlTaxCode = (TextView) dialog
				.findViewById(R.id.vl_detail_taxcode);
		TextView vlHouseHold = (TextView) dialog
				.findViewById(R.id.vl_detail_household);
		TextView vlLocationHouseHold = (TextView) dialog
				.findViewById(R.id.vl_detail_location_supply_household);
		TextView vlDateSuppleHouseHold = (TextView) dialog
				.findViewById(R.id.vl_detail_date_supply_household);
		TextView vlFullAddress = (TextView) dialog
				.findViewById(R.id.vl_detail_address);
		TextView vlPhoneNumber = (TextView) dialog
				.findViewById(R.id.vl_detail_phone);
		TextView vlNote = (TextView) dialog.findViewById(R.id.vl_detail_note);
		Button btnCancel = (Button) dialog.findViewById(R.id.btn_detail_cancel);
		TextView vlTittle = (TextView) dialog
				.findViewById(R.id.vl_detail_tittle);
		vlTittle.setText(activity.getResources().getString(
				R.string.txt_info_details));

		if (obj != null) {
			vlCusName.setText(obj.getCustomerName());
			vlCusType.setText(obj.getCustomerType());
			vlCusCode.setText(obj.getCustomerCode());

			if (obj.getBirthday() != null && !obj.getBirthday().isEmpty()) {
				vlCusBirthday
						.setText(StringUtils.convertDate(obj.getBirthday()));
			}
			vlCusSex.setText(obj.getSex());
			vlCusCountry.setText(obj.getCountry());
			vlBussinessNumber.setText(obj.getBusinessLicense());
			vlDocumentType.setText(obj.getDocumentType());
			vlIdNo.setText(obj.getIdNo());
			vlLocationSupplyId.setText(obj.getLocationSuppleId());
			vlDateSupplyId.setText(obj.getDateSuppleId());
			vlTaxCode.setText(obj.getTaxCode());
			vlHouseHold.setText(obj.getHouseHold());
			vlLocationHouseHold.setText(obj.getLocationSupplyHouseHold());
			vlDateSuppleHouseHold.setText(obj.getDateSupplyHouseHold());
			vlFullAddress.setText(obj.getFullAddress());
			vlPhoneNumber.setText(obj.getPhoneNumber());
			vlNote.setText(obj.getNote());
		}
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		dialog.show();
	}

	private final OnClickListener approveConfirmCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				Log.d("LOG", "Type update:  " + TYPE);
				AsyntaskApprove updateChannel = new AsyntaskApprove(activity,
						TYPE);
				updateChannel.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}
		}
	};

	private void showDialogReason(final int type) {
		final Dialog dialog = new Dialog(activity);
		dialog.setCancelable(false);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_select_reason);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		Window window = dialog.getWindow();
		lp.copyFrom(window.getAttributes());
		// This makes the dialog take up the full width
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);

		TextView vlTittle = (TextView) dialog
				.findViewById(R.id.vl_detail_tittle);
		vlTittle.setText(activity.getResources().getString(
				R.string.choose_reason));
		prbSearchReason = (ProgressBar) dialog
				.findViewById(R.id.prb_search_reason);

		spnReason = (Spinner) dialog.findViewById(R.id.spn_chose_reason);
		Button btnAccept = (Button) dialog.findViewById(R.id.btn_reason_accept);
		Button btnCancel = (Button) dialog.findViewById(R.id.btn_reason_cancel);

		// ArrayList<Spin> lstReason = new ArrayList<Spin>();
		// lstReason.add(new Spin("-1", getString(R.string.txt_select_reason)));
		// Utils.setDataSpinner(activity, lstReason, spnReason);
		if (CommonActivity.isNetworkConnected(activity)) {
			if (lstReason == null || lstReason.isEmpty()) {
				AsyntaskGetReason getReason = new AsyntaskGetReason(activity);
				getReason.execute();
			} else {
				Utils.setDataSpinner(activity, lstReason, spnReason);
			}
		} else {
			Dialog dialog2 = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name));
			dialog2.show();
		}
		btnAccept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Spin spin = (Spin) spnReason.getSelectedItem();
				Log.d("LOG", "--dialog---" + spin.getValue());
				if (spin.getId().equalsIgnoreCase("-1")) {

					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkreasonacept),
							getString(R.string.app_name)).show();

				} else {
					TYPE = type;
					String str = "";
					if (type == TYPE_APPROVE) {
						str = getString(R.string.message_confirm_approve);
					} else if (type == TYPE_MOVE_APPROVE) {
						str = getString(R.string.message_confirm_move_approve);
					} else {
						str = getString(R.string.message_confirm_cancel_record);
					}
					CommonActivity.createDialog(activity, str,
							getString(R.string.app_name),
							getString(R.string.say_ko),
							getString(R.string.say_co), null,
							approveConfirmCallBack).show();
					dialog.dismiss();

				}

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		dialog.show();
	}

	// search_start
	private class AsyntaskSearchCustomer extends
			AsyncTask<String, Void, ParseOuput> {
		final ProgressDialog progress;
		private Context context = null;
		private int type;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskSearchCustomer(Context context) {
			this.context = context;
            this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			if (lstCustomer != null && lstCustomer.size() > 0) {
				lstCustomer.clear();
			}
		}

		@Override
		protected ParseOuput doInBackground(String... params) {
			return searchCustomer();
		}

		@Override
		protected void onPostExecute(ParseOuput result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				lstCustomer = result.getLstCustomerApproveObjs();
				lstCusTemp = result.getLstCustomerApproveObjs();

				mAdapter = new CustomerMustApproveAdapter(activity,
						lstCustomer, actionStatus.getValue());

				lvCustomer.setAdapter(mAdapter);
				setOnClickDowload();
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

		private ParseOuput searchCustomer() {
			actionItem = (Spin) spnActionType.getSelectedItem();
			actionStatus = (Spin) spnStatus.getSelectedItem();

			String isdn = CommonActivity.checkStardardIsdn(edtIsdnOrAccount
					.getText().toString().trim());
			// Check quyen_start
			// SharedPreferences preferences =
			// getActivity().getSharedPreferences(
			// Define.PRE_NAME, Activity.MODE_PRIVATE);
			//
			// String name = preferences.getString(Define.KEY_MENU_NAME, "");
			// if (name.contains(";menu_approve;")
			// && name.contains(";menu_trans_approve;")
			// && name.contains(";menu_cancel_profile;")) {
			// hasVtApprovedDocument = true;
			// }
			//
			// if (name.contains(";menu_approve;")
			// && name.contains(";menu_cancel_profile;")
			// && !name.contains(";menu_trans_approve;")) {
			// hasVtApprovedDocumentVtt = true;
			// } else {
			// hasVtApprovedDocumentVtt = false;
			// }

			// check quyen_end

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListSubInfoFileAction");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListSubInfoFileAction>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<actionCode>").append(actionItem.getId()).append("</actionCode>");
				rawData.append("<status>").append(actionStatus.getId()).append("</status>");
				rawData.append("<startDate>").append(edtStartDate.getText().toString()).append("</startDate>");
				rawData.append("<endDate>").append(edtEndDate.getText().toString()).append("</endDate>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<hasVtApprovedDocument>").append(hasVtApprovedDocument).append("</hasVtApprovedDocument>");
				rawData.append("<hasVtApprovedDocumentVtt>").append(hasVtApprovedDocumentVtt).append("</hasVtApprovedDocumentVtt>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListSubInfoFileAction>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop: " + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_findListSubInfoFileAction");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
				}

				return parseOuput;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
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
			// Intent i = new Intent();
			// getTargetFragment().onActivityResult(getTargetRequestCode(),
			// Activity.RESULT_OK, i);

			if (lstCustomer != null && !lstCustomer.isEmpty()) {
				lstCustomer.clear();
			}

			if (lstCusTemp != null && !lstCusTemp.isEmpty()) {
				lstCusTemp.clear();
			}

			if (lstSubId != null && !lstSubId.isEmpty()) {
				lstSubId.clear();
			}

			mAdapter.notifyDataSetChanged();

			AsyntaskSearchCustomer asyntaskSearchCustomer = new AsyntaskSearchCustomer(
					getActivity());
			asyntaskSearchCustomer.execute();

			// getActivity().onBackPressed();
		}
	};

	// search_end

	// get_reason_start
	private class AsyntaskGetReason extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReason(Context context) {
			this.context = context;
			prbSearchReason.setVisibility(View.VISIBLE);
			// this.progress = new ProgressDialog(this.context);
			// // check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(R.string.waitting));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfo();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			// progress.dismiss();
			prbSearchReason.setVisibility(View.GONE);
			if (errorCode.equalsIgnoreCase("0")) {
				// CommonActivity.createAlertDialog(getActivity(),
				// getString(R.string.capnhatthanhcong),
				// getString(R.string.app_name), backClick).show();
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

		private ArrayList<Spin> getReasonInfo() {
			ArrayList<Spin> lstReason = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListReasonCommon");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonCommon>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<telecomServiceId>" + "1"
						+ "</telecomServiceId>");
				rawData.append("<dbType>" + "2" + "</dbType>");
				rawData.append("<actionCode>" + 913 + "</actionCode>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonCommon>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonCommon");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
				}

				for (ReasonBean reasonBean : parseOuput.getLstReasonBeans()) {
					Spin spin = new Spin();
					spin.setId(reasonBean.getId());
					spin.setName(reasonBean.getName());

					lstReason.add(spin);
				}

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
					spin.setId(parse.getValue(e1, "reasonId"));
					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	// get_reason_end
	// Approve_start
	private class AsyntaskApprove extends AsyncTask<String, Void, String> {
		final ProgressDialog progress;
		private Context context = null;
		private final int type;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String desSucess = "";

		public AsyntaskApprove(Context context, int type) {
			this.context = context;
			this.type = type;
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
			approveCustomer(type);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				// Iterator<CustomerApproveObj> it = lstCustomer.iterator();
				// while (it.hasNext()) {
				// CustomerApproveObj obj = it.next();
				// for (String str : lstSubId) {
				// if (obj.getSubId().equals(str)) {
				// it.remove();
				// }
				// }

				if (type == TYPE_MOVE_APPROVE) {
					desSucess = activity.getString(R.string.moveapprovesuccess);
				}
				if (type == TYPE_APPROVE) {
					desSucess = activity.getString(R.string.approvesuccess);
				}
				if (type == TYPE_CANCEL_RECORD) {
					desSucess = activity
							.getString(R.string.cancelprofilesuccess);
				}

				CommonActivity.createAlertDialog(getActivity(), desSucess,
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
									.getString(R.string.app_name), backClick);
					dialog.show();

				}
			}
		}

		private void approveCustomer(int type) {
			Spin item = (Spin) spnReason.getSelectedItem();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateSubInfoFileActionNew");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateSubInfoFileActionNew>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<type>").append(type).append("</type>");
				rawData.append("<reasonId>").append(item.getId()).append("</reasonId>");

				for (int i = 0; i < lstSubId.size(); i++) {
					rawData.append("<lstSubInfoFileActionDTOs>");
					rawData.append("<subInfoId>").append(lstSubId.get(i)).append("</subInfoId>");
					rawData.append("</lstSubInfoFileActionDTOs>");
				}
				// rawData.append("<hasVtApprovedDocument>" + true +
				// "</hasVtApprovedDocument>");
				// rawData.append("<hasVtApprovedDocumentVtt>" + true +
				// "</hasVtApprovedDocumentVtt>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:updateSubInfoFileActionNew>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateSubInfoFileActionNew");
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

	// Aprove_end

	// dowload file_start
	private class AsyntaskDowloadFile extends
			AsyncTask<String, Void, ParseOuput> {
		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final CustomerApproveObj objCus;

		public AsyntaskDowloadFile(Context context, CustomerApproveObj obj) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			this.objCus = obj;
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
		protected ParseOuput doInBackground(String... params) {
			return dowloadInfo();
		}

		@Override
		protected void onPostExecute(ParseOuput result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				// CommonActivity.createAlertDialog(getActivity(),
				// getString(R.string.capnhatthanhcong),
				// getString(R.string.app_name), backClick).show();
				try {
					if (result != null && objCus != null && !result.equals("")) {
						Log.d("result.getFileAttach()", ""
								+ result.getFileAttach().length());

						byte[] fileByte = Base64.decode(result.getFileAttach(),
								Base64.DEFAULT);

						// byte[] fileByte =
						// result.getCustomerApproveObj().getFileAttach().getBytes();
						File file = new File(
								Environment.getExternalStorageDirectory(),
								objCus.getFileName());
						FileOutputStream fos = new FileOutputStream(
								file.getPath());
						fos.write(fileByte);
						fos.flush();
						fos.close();
						String strFile = file.getPath();
						objCus.setFileLink(strFile);
						openFile(strFile);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

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
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ParseOuput dowloadInfo() {
			String strResult = "";
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getAprovalOrWaitSubInfoFileAction");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAprovalOrWaitSubInfoFileAction>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(customerApproveObj.getIsdn()).append("</isdn>");
				rawData.append("<actionCode>").append(actionItem.getId()).append("</actionCode>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getAprovalOrWaitSubInfoFileAction>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getAprovalOrWaitSubInfoFileAction");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
				}

				return parseOuput;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	// dowload file_end

	// open file_start
	private void openFile(String fileName) {
		File file = new File(fileName);
		Log.d("LOG", "Link file: " + fileName);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.setDataAndType(Uri.fromFile(apkFile),
		// "application/pdf");
		String extension = android.webkit.MimeTypeMap
				.getFileExtensionFromUrl(Uri.fromFile(file).toString());
		String mimetype = android.webkit.MimeTypeMap.getSingleton()
				.getMimeTypeFromExtension(extension);
		if (extension.equalsIgnoreCase("") || mimetype == null) {
		} else {
			intent.setDataAndType(Uri.fromFile(file), mimetype);
		}

		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Intent intent2 = Intent.createChooser(intent, "Open File");
		try {
			startActivity(intent2);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	// open file_end

	private boolean validateUpdate() {
		if (lstSubId != null && lstSubId.size() > 0) {
			lstSubId.clear();
		}
		for (CustomerApproveObj obj : lstCustomer) {
			if (obj.isChecked()) {
				lstSubId.add(obj.getSubFileId());
			}
		}
		if (lstSubId == null || lstSubId.size() == 0) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.message_check_account),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_approve:
			if (validateUpdate()) {
				showDialogReason(TYPE_APPROVE);
			}
			break;
		case R.id.btn_move_approve:
			if (validateUpdate()) {
				showDialogReason(TYPE_MOVE_APPROVE);
			}
			break;

		case R.id.btn_cancel_record:

			if (validateUpdate()) {
				showDialogReason(TYPE_CANCEL_RECORD);
			}
			break;

		case R.id.btn_search:

			if (lstCustomer != null && lstCustomer.size() > 0) {
				lstCustomer.clear();
			}
			if (lstCusTemp != null && lstCusTemp.size() > 0) {
				lstCusTemp.clear();
			}
			if (mAdapter != null) {
				mAdapter.notifyDataSetChanged();
			}

			if (validateSearch()) {
				if (CommonActivity.isNetworkConnected(activity)) {
					AsyntaskSearchCustomer searchCus = new AsyntaskSearchCustomer(
							activity);
					searchCus.execute();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
			break;
		case R.id.edtFromDate:
			DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
					getActivity(), AlertDialog.THEME_HOLO_LIGHT,fromDatePickerListener, fromYear, fromMonth,
					fromDay);
			fromDateDialog.show();
			break;
		case R.id.edtToDate:
			DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
					AlertDialog.THEME_HOLO_LIGHT,toDatePickerListener, toYear, toMonth, toDay);
			toDateDialog.show();
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;
		default:
			break;
		}

	}

}
