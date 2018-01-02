package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.TaskAssignBO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class FragmentTaskReassign extends FragmentCommon {

	private Activity activity;


    private Spinner spnStaff;
    private EditText edtNote;

	private String staffCode;
    private TaskAssignBO taskAssignBO;
	
	private Button btnTaskReassign;

    private TextView taskCode;
	private TextView taskName;
	private TextView account;
    private TextView serviceName;
    private TextView mngtCode;
    private TextView tvStaffCode;
    private TextView assignDate;
    private TextView endAssignDate;
    private TextView description;
    private TextView tvStatus;
    private TextView tvReasonName;

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.task_reassign);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		idLayout = R.layout.layout_task_reassign;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

		mBundle = getArguments();
		if (mBundle != null) {
			taskAssignBO = (TaskAssignBO) mBundle.getSerializable("taskAssignBO");
//			lstStaff = (List<Spin>) mBundle.getSerializable("lstStaff");
		}
	}

	@Override
	public void unit(View v) {
		spnStaff = (Spinner) v.findViewById(R.id.spnStaff);
        Spinner spnReason = (Spinner) v.findViewById(R.id.spnReason);
		edtNote = (EditText) v.findViewById(R.id.edtNote);

		btnTaskReassign = (Button) v.findViewById(R.id.btnTaskReassign);
		btnTaskReassign.setOnClickListener(this);

        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

        List<Spin> lstStaff = FragmentTaskAssignManager.lstStaff;
		
		Utils.setDataSpinner(act, lstStaff, spnStaff);
		
		List<Spin> lstSpin = (new ApParamDAL(act)).getAppParam("REASON_CODE");
		Utils.setDataSpinner(act, lstSpin, spnReason);
		
		 taskCode = (TextView) v.findViewById(R.id.taskCode);
		 taskName = (TextView) v.findViewById(R.id.taskName);
	     account = (TextView) v.findViewById(R.id.account);
	     serviceName = (TextView) v.findViewById(R.id.serviceName);
	     mngtCode = (TextView) v.findViewById(R.id.mngtCode);
	     tvStaffCode = (TextView) v.findViewById(R.id.staffCode);
	     assignDate = (TextView) v.findViewById(R.id.assignDate);
	     endAssignDate = (TextView) v.findViewById(R.id.endAssignDate);
	     description = (TextView) v.findViewById(R.id.description);
	     tvStatus = (TextView) v.findViewById(R.id.status);
	     tvReasonName = (TextView) v.findViewById(R.id.tvReasonName);
	     
	     taskCode.setText(taskAssignBO.getTaskCode());
	     taskName.setText(taskAssignBO.getTaskName());
	     account.setText(taskAssignBO.getAccount());
	     serviceName.setText(taskAssignBO.getServiceName());
	     mngtCode.setText(taskAssignBO.getMngtCode());
	     tvStaffCode.setText(taskAssignBO.getStaffCode());
	     assignDate.setText(taskAssignBO.getAssignDate());
	     endAssignDate.setText(taskAssignBO.getEndAssignDate());
	     description.setText(taskAssignBO.getDescription());
	     
	     for (Spin spin : lstSpin) {
			if(spin.getId().equalsIgnoreCase(taskAssignBO.getReasonId())) {
				if(!taskAssignBO.getReasonId().isEmpty() && !taskAssignBO.getReasonId().equalsIgnoreCase("-1")) {
					tvReasonName.setText(spin.getValue());
				}				
				break;
			}
	     }
	     
	     String[] task_assign = getResources().getStringArray(R.array.task_assign);
	     int iStatus = Integer.parseInt(taskAssignBO.getStatus());
	     tvStatus.setText(task_assign[iStatus + 1]);

        LinearLayout lnStaff = (LinearLayout) v.findViewById(R.id.lnStaff);
        LinearLayout lnNote = (LinearLayout) v.findViewById(R.id.lnNote);
	     if("0".equalsIgnoreCase(taskAssignBO.getStatus()) || "3".equalsIgnoreCase(taskAssignBO.getStatus())) {
	    	 lnStaff.setVisibility(View.VISIBLE);
	    	 lnNote.setVisibility(View.VISIBLE);
	    	 btnTaskReassign.setVisibility(View.VISIBLE);
	     } else {
	    	 lnStaff.setVisibility(View.GONE);
	    	 lnNote.setVisibility(View.GONE);
	    	 btnTaskReassign.setVisibility(View.GONE);
	     }
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btnCancel:
			Log.d(Constant.TAG, "FragmentTaskReassign onClick btnCancel");
			act.onBackPressed();
			break;
		case R.id.btnTaskReassign:
			if (CommonActivity.isNetworkConnected(activity)) {
				Log.d(Constant.TAG, "FragmentTaskReassign lookUpTaskAssign");

//				Spin reason = (Spin) spnReason.getSelectedItem();
//				reasonId = reason.getId();
				
				if (StringUtils.CheckCharSpecical(edtNote.getText().toString())) {
					Toast.makeText(
							getActivity(),
							getString(R.string.checkcharspecical),
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				Spin spin = (Spin) spnStaff.getSelectedItem();
				if(spin == null || spin.getId() == null || spin.getId().isEmpty()) {
					CommonActivity.createAlertDialog(act, getString(R.string.not_staff),
							getString(R.string.app_name)).show();
				} else {
					staffCode = spin.getId();
					
					CommonActivity.hideKeyboard(btnTaskReassign, activity);
					
					CommonActivity.createDialog(activity, getString(R.string.message_confirm_update),
							getString(R.string.app_name), getString(R.string.say_ko), getString(R.string.say_co), null,
							confirmChargeAct).show();
				}
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

	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
                String reasonId = "";
                AsyncTaskReassign async = new AsyncTaskReassign(activity, taskAssignBO.getTaskAssignId(), reasonId, staffCode);
				async.execute();
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
		}
	};

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
	
	private class AsyncTaskReassign extends AsyncTask<String, Void, Void> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		
		private final String _staffCode;
		private final String _taskAssignId;
		private final String _reasonId;
		
		public AsyncTaskReassign(Activity mActivity, String taskAssignId, String reasonId, String staffCode) {
			this.mActivity = mActivity;
			this._staffCode = staffCode;
			this._taskAssignId = taskAssignId;
			this._reasonId = reasonId;
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
		protected Void doInBackground(String... params) {
			return taskReAssign();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if(description == null || description.isEmpty()) {
					description = getResources().getString(R.string.updatesucess);
				}
				CommonActivity.createAlertDialog(mActivity, description,
						getString(R.string.app_name)).show();
			} else {
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

		private Void taskReAssign() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_taskReAssign");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:taskReAssign>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<taskAssignId>").append(_taskAssignId).append("</taskAssignId>");
				rawData.append("<reasonId>").append(_reasonId).append("</reasonId>");
				rawData.append("<staffCode>").append(_staffCode).append("</staffCode>");
				rawData.append("<description>").append(edtNote.getText().toString().trim()).append("</description>");

				rawData.append("</input>");
				rawData.append("</ws:taskReAssign>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_taskReAssign");
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
//					nodechild = doc.getElementsByTagName("lstTaskAssignBO");
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentTaskAssign taskReAssign", e);
			}
			return null;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
	
}
