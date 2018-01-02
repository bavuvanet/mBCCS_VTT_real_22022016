package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.IsdnAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.IsdnObject;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUploadObj;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class FragmentSearchCV extends Fragment implements OnClickListener,
		OnItemClickListener {

	private Activity activity;
	private View mView;
	private EditText edt_Isdn;
	private Button btn_search;
	private ListView listview_cv;
	private TextView txtNameActionBar;
	private Button btnHome;

	private ArrayList<IsdnObject> mListIsdn = new ArrayList<>();
	private IsdnAdapter isdnAdapter;
	private Integer checkBunder = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if (bundle != null) {
			checkBunder = bundle.getInt(Define.KEY_JOB);
			Log.d("checkBunder", "" + checkBunder);
		}
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_customer_search_cv,
					container, false);
			edt_Isdn = (EditText) mView.findViewById(R.id.edt_Isdn);
			btn_search = (Button) mView.findViewById(R.id.btn_search);
			listview_cv = (ListView) mView.findViewById(R.id.listview_cv);
		}
		btn_search.setOnClickListener(this);
		listview_cv.setOnItemClickListener(this);

		if (checkBunder.compareTo(2) == 0) {
			onSearchCV();
		}

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.search_cv);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			onSearchCV();
			break;
		case R.id.btnHome:
			CommonActivity.callphone(activity, Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
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

	private void onSearchCV() {
		String isdn = edt_Isdn.getText().toString().trim();
		CommonActivity.hideKeyboard(btn_search, activity);
		mListIsdn = new ArrayList<>();
		isdnAdapter = new IsdnAdapter(activity, mListIsdn);
		listview_cv.setAdapter(isdnAdapter);

		// if (isdn.length() > 0) {
		if (CommonActivity.isNetworkConnected(getActivity())) {
			isdn = CommonActivity.checkStardardIsdn(isdn);
			AsynctaskSearchIsdn asyntaskSearchIsdn = new AsynctaskSearchIsdn(
					activity, isdn);
			asyntaskSearchIsdn.execute();
		} else {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}
		// } else {
		// AsynctaskSearchIsdn asyntaskSearchIsdn = new
		// AsynctaskSearchIsdn(activity, isdn);
		// asyntaskSearchIsdn.execute();
		// }
	}

	@SuppressWarnings("unused")
	private class AsynctaskSearchIsdn extends
			AsyncTask<Void, Void, ArrayList<IsdnObject>> {

		final ProgressDialog progress;
		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String isdn = "";

		public AsynctaskSearchIsdn(Activity mActivity, String isdn) {
			this.isdn = isdn;
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.searching));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<IsdnObject> doInBackground(Void... params) {

			return doGetListIsdnObject();
		}

		@Override
		protected void onPostExecute(ArrayList<IsdnObject> result) {
			super.onPostExecute(result);
			progress.dismiss();
			Log.d(Constant.TAG,
					"FragmentSearchCV AsynctaskSearchIsdn onPostExecute result: "
							+ result.size());
			if (errorCode.equals("0")) {
				String isdn = edt_Isdn.getText().toString().trim();
				if (isdn.length() > 0) {
					if (result.size() > 0) {
						IsdnObject isdnObject = result.get(0);
						showFragmentUpdateCV(isdnObject);
					} else {
						CommonActivity.createAlertDialog(
								mActivity,
								getString(R.string.no_profile_remain_account,
										isdn), getString(R.string.app_name))
								.show();
					}
				} else {
					if (result.size() == 0) {
						CommonActivity.createAlertDialog(mActivity,
								getString(R.string.not_result_search),
								getString(R.string.app_name)).show();
					}
					mListIsdn = result;
					Log.d(Constant.TAG,
							"FragmentSearchCV AsynctaskSearchIsdn onPostExecute result: "
									+ result.size());
					isdnAdapter = new IsdnAdapter(mActivity, mListIsdn);
					listview_cv.setAdapter(isdnAdapter);
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<IsdnObject> doGetListIsdnObject() {
			ArrayList<IsdnObject> arrListIsdn = new ArrayList<>();
			ArrayList<ProfileUploadObj> listProfileUpload = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLstRecord");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstRecord>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("</input>");
				rawData.append("</ws:getLstRecord>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getLstRecord");
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
					nodechild = doc.getElementsByTagName("lstProfileUpload");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ProfileUploadObj profileUploadObj = new ProfileUploadObj();
						profileUploadObj.setProfileUploadId(parse.getValue(e1,
								"profileUploadId"));
						profileUploadObj.setStaffCode(parse.getValue(e1,
								"staffCode"));
						profileUploadObj.setAccount(parse.getValue(e1,
								"account"));
						profileUploadObj.setActionAuditId(parse.getValue(e1,
								"actionAuditId"));
						profileUploadObj.setActionCode(parse.getValue(e1,
								"actionCode"));
						profileUploadObj
								.setStatus(parse.getValue(e1, "status"));
						profileUploadObj.setReasonId(parse.getValue(e1,
								"reasonId"));
						profileUploadObj.setRecordCode(parse.getValue(e1,
								"recordCode"));
						profileUploadObj.setRecordName(parse.getValue(e1,
								"recordName"));
						profileUploadObj.setFileLength(parse.getValue(e1,
								"fileLength"));
						profileUploadObj.setFilePath(parse.getValue(e1,
								"filePath"));
						profileUploadObj.setCreateTime(StringUtils
								.convertDate(parse.getValue(e1, "createTime")));
						profileUploadObj.setUpdateTime(parse.getValue(e1,
								"updateTime"));
						profileUploadObj.setDescription(parse.getValue(e1,
								"description"));

						// if(!"10".equals(profileUploadObj.getActionCode())){
						listProfileUpload.add(profileUploadObj);
						// }
					}
				}

				HashMap<String, ArrayList<ProfileUploadObj>> hasmapListProfile = new HashMap<>();

				if (listProfileUpload.size() > 0) {
					Log.d(Constant.TAG,
							"FragmentSearchCV doGetListIsdnObject size listProfileUpload: "
									+ listProfileUpload.size());
					for (ProfileUploadObj profileUploadObj : listProfileUpload) {
						String key = profileUploadObj.getAccount();
						Log.d(Constant.TAG,
								"FragmentSearchCV doGetListIsdnObject key: "
										+ key + " recode name: "
										+ profileUploadObj.getRecordName());
						if (hasmapListProfile.containsKey(key)) {
							ArrayList<ProfileUploadObj> arrProfileploadObj = hasmapListProfile
									.get(key);
							arrProfileploadObj.add(profileUploadObj);
							hasmapListProfile.put(key, arrProfileploadObj);
						} else {
							ArrayList<ProfileUploadObj> arrProfileploadObj = new ArrayList<>();
							arrProfileploadObj.add(profileUploadObj);
							hasmapListProfile.put(key, arrProfileploadObj);
						}
					}
				}

				if (hasmapListProfile.size() > 0) {
					for (Entry<String, ArrayList<ProfileUploadObj>> entry : hasmapListProfile
							.entrySet()) {
						ArrayList<ProfileUploadObj> arrProfile = entry
								.getValue();
						ProfileUploadObj profileUploadObj = arrProfile.get(0);
						IsdnObject isdnObject = new IsdnObject();
						isdnObject.setAccount(profileUploadObj.getAccount());
						isdnObject.setCreateName(
								profileUploadObj.getActionCode(), mActivity);
						isdnObject.setCreateDate(profileUploadObj
								.getCreateTime());
						isdnObject.setListProfileUploadObj(arrProfile);
						arrListIsdn.add(isdnObject);
					}
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return arrListIsdn;
		}

		// private ArrayList<IsdnOb
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		IsdnObject isdnObject = mListIsdn.get(position);
		showFragmentUpdateCV(isdnObject);
	}

	@SuppressWarnings("unused")
	private void showFragmentUpdateCV(IsdnObject isdnObject) {
		Log.d(Constant.TAG,
				"FragmentSearchCV showFragmentUpdateCV listFileObj: "
						+ isdnObject.getListProfileUploadObj().size());
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("listFileObj",
				isdnObject.getListProfileUploadObj());
		FragmentCustomerUpdateCV fragmentCustomerUpdateCV = new FragmentCustomerUpdateCV();
		fragmentCustomerUpdateCV.setArguments(mBundle);
		fragmentCustomerUpdateCV.setTargetFragment(FragmentSearchCV.this, 100);
		ReplaceFragment.replaceFragment(activity, fragmentCustomerUpdateCV,
				false);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 100) {
				onSearchCV();
			}
		}
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		AlarmUploadImageReceiver.startServiceUploadImage(activity);
	}

}
