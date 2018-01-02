package com.viettel.bss.viettelpos.v4.work.activity;

import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.adapter.TaskAssignAdapter;
import com.viettel.bss.viettelpos.v4.work.object.TaskAssignBO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentTaskAssignManager extends FragmentCommon {

	private Activity activity;

	private Button btnSearch;
	private final ArrayList<TaskAssignBO> lstTaskAssignBO = new ArrayList<>();
	
	public static List<Spin> lstStaff = new ArrayList<>();
	
	private static List<Spin> lstStaffPayment = new ArrayList<>();
//	private static List<Spin> lstStaffSmartphone = new ArrayList<Spin>();

    private Spinner spnTaskCode;
	private String taskCode;

	private Spinner spnStaff;
	private String staffCode = "-1";
	
//	private Spinner spnStatus;
	private String status;
	
	private EditText edtFromDate;
	private EditText edtToDate;
	
	private String fromDate;
	private String toDate;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private final Map<String, String> taskAssignCodes = new HashMap<>();
	
	private EditText edtAccount;

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.task_assign_manager);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		idLayout = R.layout.layout_task_assign_manager;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}
	
	private LinearLayout lnTaskNew;
	private LinearLayout lnTaskUpdateSuccess;
	private LinearLayout lnTaskExpired;
	private LinearLayout lnTaskUpdateFail;
	
	private Button btnTaskNew;
	private Button btnTaskUpdateSuccess;
	private Button btnTaskExpired;
	private Button btnTaskUpdateFail;

	private Long detail = 0L;
	private Long taskNew = 0L;
	private Long taskUpdateSuccess = 0L;
	private Long taskExpired = 0L;
	private Long taskUpdateFail = 0L;

	@Override
	public void unit(View v) {
		spnTaskCode = (Spinner) v.findViewById(R.id.spnTaskCode);

		spnStaff = (Spinner) v.findViewById(R.id.spnStaff);
//		spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
		
		edtFromDate = (EditText) v.findViewById(R.id.edtFromDate);
		edtToDate = (EditText) v.findViewById(R.id.edtToDate);
		
		edtAccount = (EditText) v.findViewById(R.id.edtAccount);
		
		edtFromDate.setOnClickListener(editTextListener);
		edtToDate.setOnClickListener(editTextListener);
		
		Calendar cal = Calendar.getInstance();
		
		String toDateDefault = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -30);
		String fromDateDefault = sdf.format(cal.getTime());
		
		edtFromDate.setText(fromDateDefault);
		edtToDate.setText(toDateDefault);
		
		btnSearch = (Button) v.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		
		lnTaskNew = (LinearLayout) v.findViewById(R.id.lnTaskNew);
		lnTaskUpdateSuccess = (LinearLayout) v.findViewById(R.id.lnTaskUpdateSuccess);
		lnTaskExpired = (LinearLayout) v.findViewById(R.id.lnTaskExpired);
		lnTaskUpdateFail = (LinearLayout) v.findViewById(R.id.lnTaskUpdateFail);
		
		lnTaskNew.setVisibility(View.GONE);
		lnTaskUpdateSuccess.setVisibility(View.GONE);
		lnTaskExpired.setVisibility(View.GONE);
		lnTaskUpdateFail.setVisibility(View.GONE);
		
		btnTaskNew = (Button) v.findViewById(R.id.btnTaskNew);
		btnTaskUpdateSuccess = (Button) v.findViewById(R.id.btnTaskUpdateSuccess);
		btnTaskExpired = (Button) v.findViewById(R.id.btnTaskExpired);
		btnTaskUpdateFail = (Button) v.findViewById(R.id.btnTaskUpdateFail);
		
		btnTaskNew.setOnClickListener(this);
		btnTaskUpdateSuccess.setOnClickListener(this);
		btnTaskExpired.setOnClickListener(this);
		btnTaskUpdateFail.setOnClickListener(this);

        String[] task_assign_value = getResources().getStringArray(R.array.task_assign_value);

        List<Spin> lstTaskAssignCode = (new ApParamDAL(act)).getAppParam("TASK_ASSIGN_CODE");
		for (Spin item : lstTaskAssignCode)  {
			taskAssignCodes.put(item.getId(), item.getValue());
		}
		
		Spin spin = new Spin("", getString(R.string.txt_select));
		lstTaskAssignCode.add(0, spin);
		
		Utils.setDataSpinner(act, lstTaskAssignCode, spnTaskCode);
		
		
		AsyncGetStaffByMngt asyncGetStaffByMngt = new AsyncGetStaffByMngt();
		asyncGetStaffByMngt.execute();
		
		
		
		
		spnTaskCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Spin spin = (Spin) parent.getAdapter().getItem(position);
				String taskCode = spin.getId();
				if(position == 0) { 
					lstStaff = new ArrayList<>();
				} else {
					// thu cuoc pay ment
					lstStaff = lstStaffPayment;
					Log.d(this.getClass().getSimpleName(), "lstStaffPayment " + lstStaff.size() + " taskCode: " + taskCode);
				} 
//				else {
//					// ban hang
//					lstStaff = lstStaffSmartphone;
//					Log.d(this.getClass().getSimpleName(), "lstStaffSmartphone " + lstStaff.size() + " taskCode: " + taskCode);
//				}
				
				List<Spin> list = new ArrayList<>();
				if(lstStaff != null && !lstStaff.isEmpty()) {
					Spin all = new Spin("-1",getString(R.string.all));
					list.add(all);
					list.addAll(lstStaff);
				}
				Utils.setDataSpinner(act, list, spnStaff);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		super.onItemClick(adapterView, view, position, id);
        TaskAssignBO taskAssignBO = (TaskAssignBO) adapterView.getAdapter().getItem(position);
		
		Log.d(Constant.TAG, "FragmentTaskAssignManager onItemClick taskAssignBO " + taskAssignBO.toString());
		
		dialogLoadMore.dismiss();
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("taskAssignBO", taskAssignBO); 
//		bundle.putSerializable("lstStaff", lstStaff);
		
		FragmentTaskReassign fragment = new FragmentTaskReassign();
		fragment.setArguments(bundle);
		fragment.setTargetFragment(FragmentTaskAssignManager.this, 100);
		ReplaceFragment.replaceFragment(activity, fragment, true);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btnSearch:
			if (CommonActivity.isNetworkConnected(activity)) {
				status = ""; 
				Spin spin = (Spin) spnStaff.getSelectedItem();
				staffCode = spin == null ? "-1" : spin.getId();
				
				int pos_taskCode = spnTaskCode.getSelectedItemPosition();
				Spin spinTaskCode = (Spin) spnTaskCode.getSelectedItem();
				taskCode = spinTaskCode.getId();
				
				if(StringUtils.CheckCharSpecical(edtAccount.getText().toString())){
					CommonActivity
					.createAlertDialog(activity, getString(R.string.checkaccountspecial), getString(R.string.app_name))
					.show();
					return;
				}
				
				if(pos_taskCode == 0) {
					CommonActivity
					.createAlertDialog(activity, getString(R.string.please_input_work), getString(R.string.app_name))
					.show();
					return;
				}
				
				try {
					Date from = sdf.parse(edtFromDate.getText().toString());
					Date to = sdf.parse(edtToDate.getText().toString());
					
					if (from.after(to)) {
						Toast.makeText(activity,
								getString(R.string.checktimeupdatejob),
								Toast.LENGTH_LONG).show();
						return;
					} 
					fromDate = edtFromDate.getText().toString();
					toDate = edtToDate.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Log.d(Constant.TAG, "FragmentTaskAssign lookUpTaskAssign");
				CommonActivity.hideKeyboard(btnSearch, activity);
				
				lstTaskAssignBO.clear();
				detail = 0L;
				AsynctaskLookUpTaskAssign async = new AsynctaskLookUpTaskAssign(activity, taskCode, status, staffCode, fromDate, toDate, detail, Constant.PAGE_INDEX, Constant.PAGE_SIZE);
				async.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
			
		case R.id.btnTaskNew:
			if (CommonActivity.isNetworkConnected(activity)) {
				status = "0";

				Spin spin = (Spin) spnStaff.getSelectedItem();
				staffCode = spin == null ? "-1" : spin.getId();
				
				int pos_taskCode = spnTaskCode.getSelectedItemPosition();
				Spin spinTaskCode = (Spin) spnTaskCode.getSelectedItem();
				taskCode = spinTaskCode.getId();
				
				if(pos_taskCode == 0) {
					CommonActivity
					.createAlertDialog(activity, getString(R.string.please_input_work), getString(R.string.app_name))
					.show();
					return;
				}
				
				try {
					Date from = sdf.parse(edtFromDate.getText().toString());
					Date to = sdf.parse(edtToDate.getText().toString());
					
					if (from.after(to)) {
						Toast.makeText(activity,
								getString(R.string.checktimeupdatejob),
								Toast.LENGTH_LONG).show();
						return;
					} 
					fromDate = edtFromDate.getText().toString();
					toDate = edtToDate.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Log.d(Constant.TAG, "FragmentTaskAssign lookUpTaskAssign");
				CommonActivity.hideKeyboard(btnSearch, activity);
				
				lstTaskAssignBO.clear();
				detail = 1L;
				AsynctaskLookUpTaskAssign async = new AsynctaskLookUpTaskAssign(activity, taskCode, status, staffCode, fromDate, toDate, detail, Constant.PAGE_INDEX, Constant.PAGE_SIZE);
				async.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
			
		case R.id.btnTaskUpdateSuccess:
			if (CommonActivity.isNetworkConnected(activity)) {
				status = "1";

				Spin spin = (Spin) spnStaff.getSelectedItem();
				staffCode = spin == null ? "-1" : spin.getId();
				
				int pos_taskCode = spnTaskCode.getSelectedItemPosition();
				Spin spinTaskCode = (Spin) spnTaskCode.getSelectedItem();
				taskCode = spinTaskCode.getId();
				
				if(pos_taskCode == 0) {
					CommonActivity
					.createAlertDialog(activity, getString(R.string.please_input_work), getString(R.string.app_name))
					.show();
					return;
				}
				
				try {
					Date from = sdf.parse(edtFromDate.getText().toString());
					Date to = sdf.parse(edtToDate.getText().toString());
					
					if (from.after(to)) {
						Toast.makeText(activity,
								getString(R.string.checktimeupdatejob),
								Toast.LENGTH_LONG).show();
						return;
					} 
					fromDate = edtFromDate.getText().toString();
					toDate = edtToDate.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Log.d(Constant.TAG, "FragmentTaskAssign lookUpTaskAssign");
				CommonActivity.hideKeyboard(btnSearch, activity);
				
				lstTaskAssignBO.clear();
				detail = 1L;
				AsynctaskLookUpTaskAssign async = new AsynctaskLookUpTaskAssign(activity, taskCode, status, staffCode, fromDate, toDate, detail, Constant.PAGE_INDEX, Constant.PAGE_SIZE);
				async.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
		case R.id.btnTaskUpdateFail:
			if (CommonActivity.isNetworkConnected(activity)) {
				status = "2";

				Spin spin = (Spin) spnStaff.getSelectedItem();
				staffCode = spin == null ? "-1" : spin.getId();
				
				int pos_taskCode = spnTaskCode.getSelectedItemPosition();
				Spin spinTaskCode = (Spin) spnTaskCode.getSelectedItem();
				taskCode = spinTaskCode.getId();
				
				if(pos_taskCode == 0) {
					CommonActivity
					.createAlertDialog(activity, getString(R.string.please_input_work), getString(R.string.app_name))
					.show();
					return;
				}
				
				try {
					Date from = sdf.parse(edtFromDate.getText().toString());
					Date to = sdf.parse(edtToDate.getText().toString());
					
					if (from.after(to)) {
						Toast.makeText(activity,
								getString(R.string.checktimeupdatejob),
								Toast.LENGTH_LONG).show();
						return;
					} 
					fromDate = edtFromDate.getText().toString();
					toDate = edtToDate.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Log.d(Constant.TAG, "FragmentTaskAssign lookUpTaskAssign");
				CommonActivity.hideKeyboard(btnSearch, activity);
				
				lstTaskAssignBO.clear();
				detail = 1L;
				AsynctaskLookUpTaskAssign async = new AsynctaskLookUpTaskAssign(activity, taskCode, status, staffCode, fromDate, toDate, detail, Constant.PAGE_INDEX, Constant.PAGE_SIZE);
				async.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
		case R.id.btnTaskExpired:
			if (CommonActivity.isNetworkConnected(activity)) {
				status = "3";

				Spin spin = (Spin) spnStaff.getSelectedItem();
				staffCode = spin == null ? "-1" : spin.getId();
				
				int pos_taskCode = spnTaskCode.getSelectedItemPosition();
				Spin spinTaskCode = (Spin) spnTaskCode.getSelectedItem();
				taskCode = spinTaskCode.getId();
				
				if(pos_taskCode == 0) {
					CommonActivity
					.createAlertDialog(activity, getString(R.string.please_input_work), getString(R.string.app_name))
					.show();
					return;
				}
				
				try {
					Date from = sdf.parse(edtFromDate.getText().toString());
					Date to = sdf.parse(edtToDate.getText().toString());
					
					if (from.after(to)) {
						Toast.makeText(activity,
								getString(R.string.checktimeupdatejob),
								Toast.LENGTH_LONG).show();
						return;
					} 
					fromDate = edtFromDate.getText().toString();
					toDate = edtToDate.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Log.d(Constant.TAG, "FragmentTaskAssign lookUpTaskAssign");
				CommonActivity.hideKeyboard(btnSearch, activity);
				
				lstTaskAssignBO.clear();
				detail = 1L;
				AsynctaskLookUpTaskAssign async = new AsynctaskLookUpTaskAssign(activity, taskCode, status, staffCode, fromDate, toDate, detail, Constant.PAGE_INDEX, Constant.PAGE_SIZE);
				async.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
			break;
			
		default:
			break;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(activity, LoginActivity.class);
			startActivity(intent);
			activity.finish();
			MainActivity.getInstance().finish();

		}
	};
	
	private final View.OnClickListener editTextListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			Calendar cal = Calendar.getInstance();

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog datePicker = new FixedHoloDatePickerDialog(activity,
					AlertDialog.THEME_HOLO_LIGHT,datePickerListener, year, month, day);

			datePicker.getDatePicker().setTag(view);
			datePicker.show();
		}
	};

	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
						+ selectedYear);
			}
		}
	};
	
	private Dialog dialogLoadMore;
	private LoadMoreListView loadMoreListView;
	private void showDialogLoadMoreListView(ArrayAdapter adapter) {
		dialogLoadMore = new Dialog(activity);
		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLoadMore.setContentView(R.layout.dialog_listview);
		dialogLoadMore.setCancelable(true);
		dialogLoadMore.setTitle(getString(R.string.task_assign_manager));
		
		loadMoreListView = (LoadMoreListView) dialogLoadMore.findViewById(R.id.loadMoreListView);
		Button btnCancel = (Button) dialogLoadMore.findViewById(R.id.btnCancel);
		
		loadMoreListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		loadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				Log.i(this.getClass().getSimpleName(), "loadMoreListView onLoadMore");
				if(detail == 1) {
					AsynctaskLookUpTaskAssign async = new AsynctaskLookUpTaskAssign(activity, taskCode, status, staffCode, fromDate, toDate, detail, lstTaskAssignBO.size(), Constant.PAGE_SIZE);
					async.execute();
				}
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogLoadMore.dismiss();
			}
		});
		
		loadMoreListView.setOnItemClickListener(this);
		
		dialogLoadMore.show();
	}
	
	private class AsynctaskLookUpTaskAssign extends AsyncTask<String, Void, ArrayList<TaskAssignBO>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		
		private final String _taskCode;
		
		private final String _staffCode;
		private final String _status;
		
		private final String _fromDate;
		private final String _toDate;
		private final Long _detail;
		
		private final int _pageIndex;
		private final int _pageSize;
		
		public AsynctaskLookUpTaskAssign(Activity mActivity, String taskCode, String status, String staffCode, String fromDate, String toDate, Long detail, int pageIndex, int pageSize) {
			this.mActivity = mActivity;
			this._taskCode = taskCode;
			this._status = status;
			this._staffCode = staffCode;
			this._fromDate = fromDate;
			this._toDate = toDate;
			this._detail = detail;
			this._pageIndex = pageIndex;
			this._pageSize = pageSize;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<TaskAssignBO> doInBackground(String... params) {
			return lookUpTaskAssign();
		}

		@Override
		protected void onPostExecute(ArrayList<TaskAssignBO> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				
				if(detail == 0) {
					lnTaskNew.setVisibility(View.VISIBLE);
					lnTaskUpdateSuccess.setVisibility(View.VISIBLE);
					lnTaskExpired.setVisibility(View.VISIBLE);
					lnTaskUpdateFail.setVisibility(View.VISIBLE);
					
					btnTaskNew.setText(taskNew.toString());
					btnTaskUpdateSuccess.setText(taskUpdateSuccess.toString());
					btnTaskExpired.setText(taskExpired.toString());
					btnTaskUpdateFail.setText(taskUpdateFail.toString());
				} else {
//					lnTaskNew.setVisibility(View.GONE);
//					lnTaskUpdateSuccess.setVisibility(View.GONE);
//					lnTaskExpired.setVisibility(View.GONE);
//					lnTaskUpdateFail.setVisibility(View.GONE);
					
					if (result == null || result.size() == 0) {
						CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_work),
								getString(R.string.app_name)).show();
					}
				}
				
				if (result == null || result.size() == 0) {
//					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_work),
//							getString(R.string.app_name)).show();
				} else {
					lstTaskAssignBO.addAll(result);
					TaskAssignAdapter adapter = new TaskAssignAdapter(mActivity, lstTaskAssignBO);
					if(dialogLoadMore == null) {
						showDialogLoadMoreListView(adapter);
					} else if(!dialogLoadMore.isShowing()) {
						showDialogLoadMoreListView(adapter);
					} else {
						loadMoreListView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<TaskAssignBO> lookUpTaskAssign() {
			ArrayList<TaskAssignBO> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_lookUpTaskAssign");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:lookUpTaskAssign>");
				rawData.append("<taskAssignInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<staffCode>").append(_staffCode).append("</staffCode>");
				rawData.append("<taskCode>").append(_taskCode).append("</taskCode>");
				rawData.append("<status>").append(_status).append("</status>");
				rawData.append("<fromDate>").append(_fromDate).append("</fromDate>");
				rawData.append("<toDate>").append(_toDate).append("</toDate>");
				rawData.append("<detail>").append(_detail).append("</detail>");
				rawData.append("<pageIndex>").append(_pageIndex).append("</pageIndex>");
				rawData.append("<pageSize>").append(_pageSize).append("</pageSize>");
				if(!CommonActivity.isNullOrEmpty(edtAccount.getText().toString())){
					rawData.append("<account>").append(StringUtils.escapeHtml(edtAccount.getText().toString().trim())).append("</account>");
				}
				rawData.append("</taskAssignInput>");
				rawData.append("</ws:lookUpTaskAssign>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
						"mbccs_lookUpTaskAssign");
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
					/**
					 *  private Long taskNew =0L;
					    private Long taskExpired = 0L;
					    private Long taskUpdateSuccess = 0L;
					    private Long taskUpdateFail = 0L;
					 */
					
					taskNew = Long.parseLong(parse.getValue(e2, "taskNew"));
					taskExpired = Long.parseLong(parse.getValue(e2, "taskExpired"));
					taskUpdateSuccess = Long.parseLong(parse.getValue(e2, "taskUpdateSuccess"));
					taskUpdateFail = Long.parseLong(parse.getValue(e2, "taskUpdateFail"));

					nodechild = doc.getElementsByTagName("lstTaskAssignBO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						TaskAssignBO obj = new TaskAssignBO();
						/**
						 *  private Long taskAssignId;
						    private String account;
						    private String serviceType;
						    private String serviceName;
						    private String mngtCode;
						    private String staffCode;
						    private Long status;
						    private Long reasonId;
						    private Date assignDate;
						    private Date updateDate;
						    private Date endAssignDate;
						    private String description;
						 */
						obj.setTaskAssignId(parse.getValue(e1, "taskAssignId"));
						String taskCode = parse.getValue(e1, "taskCode");
						obj.setTaskCode(taskCode);
						String taskName = taskAssignCodes.get(taskCode);
						obj.setTaskName(taskName);
						
						obj.setAccount(parse.getValue(e1, "account"));
						obj.setServiceType(parse.getValue(e1, "serviceType"));
						obj.setServiceName(parse.getValue(e1, "serviceName"));
						obj.setMngtCode(parse.getValue(e1, "mngtCode"));
						obj.setStaffCode(parse.getValue(e1, "staffCode"));
						obj.setReasonId(parse.getValue(e1, "reasonId"));
						obj.setAssignDate(parse.getValue(e1, "assignDateStr"));
						obj.setUpdateDate(parse.getValue(e1, "updateDateStr"));
						obj.setEndAssignDate(parse.getValue(e1, "endAssignDateStr"));
						obj.setDescription(parse.getValue(e1, "description"));
						obj.setStatus(parse.getValue(e1, "status"));
						
						list.add(obj);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentTaskAssign lookUpTaskAssign", e);
			}
			return list;
		}
	}
	
	private class AsyncGetStaffByMngt extends AsyncTask<String, Void, List<Spin>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(act);
			this.progress.setCancelable(false);
			this.progress.setMessage(act.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected List<Spin> doInBackground(String... params) {

			return getStaffByMngt();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);
			
			progress.dismiss();
			
//			AsyncGetLstStaffByMngt asyncGetLstStaffByMngt = new AsyncGetLstStaffByMngt();
//			asyncGetLstStaffByMngt.execute();
			
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					lstStaffPayment = result;
				} else {
					if(description != null && !description.isEmpty()) {
						CommonActivity.createAlertDialog(activity, description,
								getString(R.string.app_name)).show();
					}
				}
				Log.d(this.getClass().getSimpleName() , "onPostExecute: result.size()" + result.size());
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(act, description,
							act.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = act.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Spin> getStaffByMngt() {
			List<Spin> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getStaffByMngt");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStaffByMngt>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getStaffByMngt>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
						"mbccs_getStaffByMngt");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodeLstStaffBean = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					String token = parse.getValue(e2, "token");
					Session.setToken(token);
					Log.d(this.getClass().getSimpleName() , "errorCode: " + errorCode + " description: " + description);
					
					nodeLstStaffBean = doc.getElementsByTagName("lstStaffBean");
					for (int j = 0; j < nodeLstStaffBean.getLength(); j++) {
						Element e1 = (Element) nodeLstStaffBean.item(j);
						Spin spin = new Spin();
						spin.setId(parse.getValue(e1, "staffCode"));
						spin.setValue(parse.getValue(e1, "name"));
						list.add(spin);
					}
				}
			} catch (Exception e) {
				Log.e(this.getClass().getSimpleName(), "getStaffByMngt", e);
			}
			return list;
		}
	}
//	
//	private class AsyncGetLstStaffByMngt extends AsyncTask<String, Void, List<Spin>> {
//
//		private XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private ProgressDialog progress;
//		
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			this.progress = new ProgressDialog(act);
//			this.progress.setCancelable(false);
//			this.progress.setMessage(act.getResources().getString(R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		};
//
//		@Override
//		protected List<Spin> doInBackground(String... params) {
//
//			return getLstStaffByMngt();
//		}
//
//		@Override
//		protected void onPostExecute(List<Spin> result) {
//			super.onPostExecute(result);
//
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					lstStaffSmartphone = result;
//				} else {
//					if(description != null && !description.isEmpty()) {
//						CommonActivity.createAlertDialog(activity, description,
//								getString(R.string.app_name)).show();
//					}
//				}
//				Log.d(this.getClass().getSimpleName() , "onPostExecute: result.size()" + result.size());
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity.createAlertDialog(act, description,
//							act.getResources().getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = act.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private List<Spin> getLstStaffByMngt() {
//			List<Spin> list = new ArrayList<Spin>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getLstStaffByMngt");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getLstStaffByMngt>");
//				rawData.append("<input>");
//				rawData.append("<token>" + Session.getToken() + "</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getLstStaffByMngt>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
//						"mbccs_getLstStaffByMngt");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Responseeeeeeeeee Original group", response);
//
//				// ==== parse xml list ip
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodeLstStaffBean = null;
//
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					String token = parse.getValue(e2, "token");
//					Session.setToken(token);
//					Log.d(this.getClass().getSimpleName() , "errorCode: " + errorCode + " description: " + description);
//					
//					nodeLstStaffBean = doc.getElementsByTagName("lstStaff");
//					for (int j = 0; j < nodeLstStaffBean.getLength(); j++) {
//						Element e1 = (Element) nodeLstStaffBean.item(j);
//						Spin spin = new Spin();
//						spin.setId(parse.getValue(e1, "staffCode"));
//						spin.setValue(parse.getValue(e1, "name"));
//						list.add(spin);
//					}
//				}
//			} catch (Exception e) {
//				Log.e(this.getClass().getSimpleName(), "getLstStaffByMngt", e);
//			}
//			return list;
//		}
//	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

}
