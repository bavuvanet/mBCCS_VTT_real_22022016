package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.TelecomServiceObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterInfraTypeSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterServiceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.LockBoxInfoAdapter;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ApParamObj;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.LockBoxInfoObj;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.Station;
import com.viettel.bss.viettelpos.v4.login.adapter.AdapterLocationService;
import com.viettel.bss.viettelpos.v4.login.object.LocationService;
import com.viettel.bss.viettelpos.v4.login.object.Optional;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentInfoIDNumberManager extends Fragment implements
		OnClickListener {
	private Button btnHome;
	private View mView;
    private Spinner infraType;

    private ArrayList<LocationService> arrayListLocation;
	private ArrayList<LocationService> arrayListLocationAll;

	private AdapterLocationService mAdapterLocationService;
	private BCCSGateway mBccsGateway;
	private int serviceSelect = 0;
	private ProgressBar mProgressBar;

	private ArrayList<TelecomServiceObj> mListTelecom;
	private ArrayList<ApParamObj> mParamObjs;
	// private ArrayList<ApParamObj> mParamOfAP;
	// private ArrayList<ApParamObj> mParamOfIF;
	private ArrayList<ApParamObj> mInfraType;

	private EditText mEdtSearch;
	// private String shop[];

	// private ChargeDB mChargeDB;
	private InfrastrucureDB mInfrastrucureDB;
	private CustomDialogShowData dialogShowData;
	private String mPrecinct = "";

	private ArrayList<AreaObj> disCode1 = new ArrayList<>();
	private ArrayList<AreaObj> disCode2 = new ArrayList<>();
	private ArrayList<AreaObj> disCode3 = new ArrayList<>();

	private String serviceTypeCode;// dich vu
	private String locationCodeStr;
	private Dialog dialog = null;
	private Dialog dialogLockBoxInfo = null;
	private String serviceName;
	private String serviceTypeName;// cong nghe: cap dong, cap quang ...
	private String mConnectorId;

	private String mTitleStr;
	private String mLockCap;
	private String mTotalPort = "";
	private int mPositionLvStation = 0;

	private ArrayList<LockBoxInfoObj> mListLockBoxInfo;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(getResources().getString(
				R.string.infrastructure_2));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInfrastrucureDB = new InfrastrucureDB(getActivity());

		if (mView == null) {
			mBccsGateway = new BCCSGateway();
			mView = inflater.inflate(R.layout.info_id_num_manager_layout,
					container, false);
			getData();
			unit(mView);
			// new LoadSurveyOnline(). execute();
		}
		return mView;
	}

	private void getData() {

		getListSpinerDialog();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		if (loadSurveyOnline != null) {
			loadSurveyOnline.cancel(true);
		}
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:

			CommonActivity.callphone(getActivity(), Constant.phoneNumber);

			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	private LoadSurveyOnline loadSurveyOnline;

	private void unit(View v) {
		mListLockBoxInfo = new ArrayList<>();
		dialogShowData = new CustomDialogShowData(getActivity());
		// shop = new String[2];

		mListTelecom = new ArrayList<>();
		mParamObjs = new ArrayList<>();
		mInfraType = new ArrayList<>();

        Spinner spServiceIdNum = (Spinner) v.findViewById(R.id.spServiceIDNum);
		infraType = (Spinner) v.findViewById(R.id.infraType);

		mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		mProgressBar.setVisibility(View.GONE);

		mListTelecom = mInfrastrucureDB.getListTelecomService();
		AdapterServiceSpinner adapterServiceSpinner = new AdapterServiceSpinner(
				mListTelecom, getActivity());

		if (mListTelecom.size() > 0) {
			serviceTypeCode = mListTelecom.get(0).getCode();
		}
		spServiceIdNum.setAdapter(adapterServiceSpinner);

		mParamObjs = mInfrastrucureDB.getListParam();

		// shop = mInfrastrucureDB.getProvince();
		String disCodeStr = Session.province + Session.district;

		spServiceIdNum.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				serviceSelect = position;
				TelecomServiceObj obj = mListTelecom.get(position);
				serviceTypeCode = obj.getCode();
				serviceName = obj.getTelServiceName();

				changeSpinerInfratype(serviceSelect);

				if (locationCodeStr == null || locationCodeStr.equals("")) {

				} else {
					loadListInfaType();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				serviceSelect = 0;
			}
		});
		changeSpinerInfratype(serviceSelect);

		infraType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				serviceTypeName = mInfraType.get(position).getParType();
				if (locationCodeStr == null || locationCodeStr.equals("")) {

				} else {
					loadListInfaType();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

        ListView lvStation = (ListView) v.findViewById(R.id.lvStationIdNum);
		arrayListLocation = new ArrayList<>();
		arrayListLocationAll = new ArrayList<>();

		mAdapterLocationService = new AdapterLocationService(getActivity(),
				arrayListLocation);
		lvStation.setAdapter(mAdapterLocationService);
		lvStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPositionLvStation = position;

				mConnectorId = arrayListLocation.get(position).getConnectorId();

				// mConnectorId = "337744";
				// serviceTypeName = "CCN";
				// serviceTypeCode = "A";

				viewLockBoxInfo(mConnectorId, serviceTypeName, serviceTypeCode);

				// dialogInfrastructure(position);
			}
		});

		mEdtSearch = (EditText) v.findViewById(R.id.edtSearchIDNum);
		mEdtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				arrayListLocation.clear();
				String search = mEdtSearch.getText().toString();
				if (TextUtils.isEmpty(search)) {
					arrayListLocation.addAll(arrayListLocationAll);
				} else {
					for (int i = 0; i < arrayListLocationAll.size(); i++) {
						if (arrayListLocationAll.get(i).getNameLocation()
								.toUpperCase().contains(search.toUpperCase())) {
							arrayListLocation.add(arrayListLocationAll.get(i));
						}
					}
				}

				mAdapterLocationService.notifyDataSetChanged();
			}
		});

        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogShowData.show();
			}
		});
	}

	private void lockBox(String numberPort) {
		new LockBoxTask(serviceTypeName, numberPort, serviceTypeCode,
				Session.userName).execute();
		Log.d("",
				"serviceTypeCode, numberPort, serviceTypeName, userName, mConnectorId :"
						+ serviceTypeCode + ", " + numberPort + ", "
						+ serviceTypeName + ", " + Session.userName + ", "
						+ mConnectorId);
	}

	private void changeSpinerInfratype(int spinerSelect) {
		mInfraType.clear();
		if (spinerSelect == 2 || spinerSelect == 3) {
			mInfraType.add(mParamObjs.get(1));
			// mInfraType = mParamOfAP;

		} else {
			mInfraType.add(mParamObjs.get(0));
			mInfraType.add(mParamObjs.get(2));
			// mInfraType = mParamOfIF;
		}
		setListSpiner(mInfraType);
	}

	private void setListSpiner(ArrayList<ApParamObj> param) {
		serviceTypeName = param.get(0).getParType();
		AdapterInfraTypeSpinner adapterInfraType = new AdapterInfraTypeSpinner(
				param, getActivity());
		infraType.setAdapter(adapterInfraType);
	}

	private void loadListInfaType() {
		loadSurveyOnline = new LoadSurveyOnline(serviceTypeCode,
				serviceTypeName, locationCodeStr);
		loadSurveyOnline.execute();
	}

	public class LoadSurveyOnline extends AsyncTask<Void, Void, String> {

		private final String serviceType;
		private final String infraType;
		private final String locationCode;
		private final ProgressDialog dialog;

		public LoadSurveyOnline(String serviceType, String infraType,
				String locationCode) {
			this.serviceType = serviceType;
			this.infraType = infraType;
			this.locationCode = locationCode;
			dialog = new ProgressDialog(getActivity());
		}

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			this.dialog.show();
			// mProgressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String original = "";
			String reponse = null;
			String xml = mBccsGateway.buildInputGatewayWithRawData2(
					createXML(infraType, locationCode, serviceType),
					"mbccs_surveyBoxByLocationCode");
			Log.e("TAG3", "xml :" + xml);

			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {
				try {
					reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,getActivity(),"mbccs_surveyBoxByLocationCode");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (reponse != null) {
					CommonOutput commonOutput;
					try {
						commonOutput = mBccsGateway.parseGWResponse(reponse);
						original = commonOutput.getOriginal();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
			Log.e("TAG3", "original : " + original);
			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			mProgressBar.setVisibility(View.GONE);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			Log.e("TAG3", "result : " + result);

			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);
				parseResult(result);
			}

			super.onPostExecute(result);
		}
	}

	private void getListSpinerDialog() {

		disCode1 = mInfrastrucureDB.getListDis(Session.province);

		String disCodeStr1 = Session.province + Session.district;
		disCode2 = mInfrastrucureDB.getListDis(disCodeStr1);

		disCode3 = new ArrayList<>();
		AreaObj areaObj = new AreaObj();
		areaObj.setName(getString(R.string.all));
		disCode3 = mInfrastrucureDB.getListStreet(disCodeStr1);
		disCode3.add(0, areaObj);

	}

	private void viewLockBoxInfo(String connectorId, String serviceTypeName,
			String serviceTypeCode) {
		new ViewLockBoxInfoTask(connectorId, serviceTypeName, serviceTypeCode)
				.execute();
	}

	class ViewLockBoxInfoTask extends AsyncTask<Void, Void, String> {

		final String connectorId;
		final String serviceTypeName;
		final String serviceTypeCode;
		private final ProgressDialog dialog;

		public ViewLockBoxInfoTask(String connectorId, String serviceTypeName,
				String serviceTypeCode) {
			this.connectorId = connectorId;
			this.serviceTypeName = serviceTypeName;
			this.serviceTypeCode = serviceTypeCode;
			dialog = new ProgressDialog(getActivity());
		}

		@Override
		protected String doInBackground(Void... params) {

			String original = "";

			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {
				original = requestSeviceViewLockInfo(connectorId,
						serviceTypeName, serviceTypeCode);
			} 

			Log.e("TAG3", "original LockBox : " + original);
			return original;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				Log.e("TAG3", "result LockBox info : " + result);

				parseResultError(result);
				parseResultLockBoxInfo(result);
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			super.onPostExecute(result);
		}
	}

	class LockBoxTask extends AsyncTask<Void, Void, String> {

		final String infraType;
		final String numberOfPort;
		final String serviceType;
		final String userLock;
		private final ProgressDialog dialog;

		public LockBoxTask(String infraType, String numberOfPort,
				String serviceType, String userLock) {
			this.infraType = infraType;
			this.numberOfPort = numberOfPort;
			this.serviceType = serviceType;
			this.userLock = userLock;
			dialog = new ProgressDialog(getActivity());
		}

		@Override
		protected String doInBackground(Void... params) {

			String original = "";

			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {
				original = requestSevice(infraType, numberOfPort, serviceType,
						userLock);
			} 

			Log.e("TAG3", "original LockBox : " + original);
			return original;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			Log.e("TAG3", "result LockBox : " + result);
			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);
				parseResultLockBox(result);
			}

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			super.onPostExecute(result);
		}
	}

	private String requestSevice(String infraType2, String numberOfPort,
                                 String serviceType, String userLock) {
		String reponse = "";
		String original = "";
		String xmlLockBox = lockBoxByUser(infraType2, numberOfPort,
				serviceType, userLock);
		Log.e("TAG3", "xmlLockBox : " + xmlLockBox);
		try {
			reponse = mBccsGateway.sendRequest(xmlLockBox, Constant.BCCS_GW_URL,getActivity(),"mbccs_lockBoxByUser");
			Log.e("TAG3", "reponse LockBox : " + reponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (reponse != null) {
			CommonOutput commonOutput;
			try {
				commonOutput = mBccsGateway.parseGWResponse(reponse);
				original = commonOutput.getOriginal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return original;

	}

	private void parseResultLockBoxInfo(String result) {
		if (result != null) {
			try {
				Log.e("TAG31", result);
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);
				NodeList nodeList = document.getElementsByTagName("lockList");
				Log.e("TAG31", "nodeList size : " + nodeList.getLength());

				NodeList totalPort = document
						.getElementsByTagName("viewLockBoxForm");

				for (int i = 0; i < totalPort.getLength(); i++) {
					Node mNode = totalPort.item(i);
					Element element = (Element) mNode;
					mTotalPort = domParse.getValue(element, "totalPort");
					Log.e("TAG31", "mTotalPort : " + mTotalPort);
				}
				if (mListLockBoxInfo != null) {
					mListLockBoxInfo.clear();
				}
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node mNode = nodeList.item(i);
					Element element = (Element) mNode;

					String connectorId = domParse.getValue(element,
							"connectorId");
					String infraType = domParse.getValue(element, "infraType");
					String rootConnectorId = domParse.getValue(element,
							"rootConnectorId");
					String serviceType = domParse.getValue(element,
							"serviceType");
					String stationId = domParse.getValue(element, "stationId");
					String userLock = domParse.getValue(element, "userLock");
					String idStr = domParse.getValue(element, "id");
					String numberOfPortStr = domParse.getValue(element,
							"numberOfPort");

					int id = Integer.parseInt(idStr);
					int numberOfPort = Integer.parseInt(numberOfPortStr);
					LockBoxInfoObj lockBoxObj = new LockBoxInfoObj(id,
							numberOfPort, connectorId, infraType,
							rootConnectorId, serviceType, stationId, userLock);

					mListLockBoxInfo.add(lockBoxObj);
				}
				Log.e("TAG31",
						"mListLockBoxInfo size : " + mListLockBoxInfo.size());

				// mAdapterLocationService.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		dialogInfrastructure(mPositionLvStation);
	}

	// Lock Box user
    private void dialogInfrastructure(int position) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LocationService locationService = arrayListLocation.get(position);
		Log.d("locationService", "locationService :" + locationService);

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		@SuppressLint("InflateParams") View dialogView = inflater.inflate(
				R.layout.dialog_infrastructure_detail, null);

		builder.setView(dialogView);
		dialog = builder.create();

		TextView topText = (TextView) dialogView.findViewById(R.id.tv_title);
		TextView tvConnrctorCode = (TextView) dialogView
				.findViewById(R.id.tv_connectorCode);
		TextView tvNumberPortService = (TextView) dialogView
				.findViewById(R.id.tv_number_port_service);
		TextView tvNumberPort = (TextView) dialogView
				.findViewById(R.id.tv_number_port);
		TextView tvNumberPortKeep = (TextView) dialogView
				.findViewById(R.id.tv_number_port_keep);
		tvNumberPortKeep.setText(getActivity().getResources().getString(
				R.string.tv_number_port_keep)
				+ " : " + mTotalPort);
		final EditText edtNumberPort = (EditText) dialogView
				.findViewById(R.id.edt_number_port);
		Button btnKeep = (Button) dialogView.findViewById(R.id.btn_keep);
		Button btnClose = (Button) dialogView.findViewById(R.id.btn_colse);

		ImageView lockImage = (ImageView) dialogView
				.findViewById(R.id.lock_image);

		mTitleStr = locationService.getNameLocation() + "-"
				+ locationService.getAddress();
		mLockCap = locationService.getDeptCode();
		topText.setText(mTitleStr);
		tvConnrctorCode.setText(mLockCap);
		tvNumberPortService.setText(getActivity().getString(
				R.string.dialog_inflas_detail_bottom)
				+ " " + locationService.getCountNetTV());
		// tvNumberPortService.setText(locationService.getCountNetTV());
		tvNumberPort.setText(serviceName);
		btnKeep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String numberPort = edtNumberPort.getText().toString();
				lockBox(numberPort);
				dialog.dismiss();
			}
		});
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		lockImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Lock Box By User

				dialogLockBoxInfo(mListLockBoxInfo);
				// viewLockBoxInfo(mConnectorId, serviceTypeName,
				// serviceTypeCode);s
			}
		});

		dialog.show();

	}

	private void dialogLockBoxInfo(
			final ArrayList<LockBoxInfoObj> listLockBoxInfo) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		@SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_lock_box_info, null);
		builder.setView(dialogView);
		
		dialogLockBoxInfo = builder.create();

		TextView topText = (TextView) dialogView.findViewById(R.id.tv_title);
		TextView tvConnrctorCode = (TextView) dialogView
				.findViewById(R.id.tv_connectorCode);
		Button btnClose = (Button) dialogView.findViewById(R.id.btn_colse);
		ListView lvListLockBox = (ListView) dialogView
				.findViewById(R.id.lvLockBoxInfo);
		LockBoxInfoAdapter adapterBoxInfo = new LockBoxInfoAdapter(
				getActivity(), listLockBoxInfo);
		lvListLockBox.setAdapter(adapterBoxInfo);

		topText.setText(mTitleStr);
		tvConnrctorCode.setText(mLockCap);

		// tvNumberPortService.setText(locationService.getCountNetTV());
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogLockBoxInfo.dismiss();
			}
		});
		lvListLockBox.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LockBoxInfoObj obj = listLockBoxInfo.get(position);
			}
		});

		dialogLockBoxInfo.show();

	}

	private String requestSeviceViewLockInfo(String connectorId,
                                             String serviceTypeName2, String serviceTypeCode2) {
		String reponse = "";
		String original = "";
		String xmlLockBox = lockBoxInfo(connectorId, serviceTypeName2,
				serviceTypeCode2);
		Log.e("TAG3", "xml view LockBoxInfo : " + xmlLockBox);
		try {
			reponse = mBccsGateway.sendRequest(xmlLockBox, Constant.BCCS_GW_URL,getActivity(),"mbccs_viewLockBoxInfo");
			Log.e("TAG3", "reponse LockBox : " + reponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (reponse != null) {
			CommonOutput commonOutput;
			try {
				commonOutput = mBccsGateway.parseGWResponse(reponse);
				original = commonOutput.getOriginal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return original;
	}

	private String lockBoxInfo(String connectorId, String serviceTypeName2,
			String serviceTypeCode2) {
		String xml = mBccsGateway.getXmlLockBox(
				createXMLLockBoxInfo(connectorId, serviceTypeName2,
						serviceTypeCode2), "mbccs_viewLockBoxInfo");
		Log.d("", "xml view lockBoxInfo : " + xml);
		return xml;
	}

	private String createXMLLockBoxInfo(String connectorId,
			String serviceTypeName2, String serviceTypeCode2) {

		StringBuilder stringBuilder = new StringBuilder("<ws:viewLockBoxInfo>");
		stringBuilder.append("<nimsInput>");

		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");

		stringBuilder.append("<count>" + 10 + "</count>");
		stringBuilder.append("<start>" + 0 + "</start>");
		stringBuilder.append("<locationCode>" + "</locationCode>");
		stringBuilder.append("<lockBoxForm>");
		ArrayList<Optional> arrayList = new ArrayList<>();
		arrayList.add(new Optional("connectorId", connectorId));
		arrayList.add(new Optional("infraType", serviceTypeName2));
		arrayList.add(new Optional("serviceType", serviceTypeCode2));
		stringBuilder.append(mBccsGateway.crateXml(arrayList));
		stringBuilder.append("</lockBoxForm>");
		stringBuilder.append("</nimsInput>");
		stringBuilder.append("</ws:viewLockBoxInfo>");
		return stringBuilder.toString();
	}

	private void parseResultLockBox(String result) {
		if (result != null) {
			XmlDomParse domParse = new XmlDomParse();
			Document document = domParse.getDomElement(result);
			NodeList nodeList = document.getElementsByTagName("resultForm");
			String resultStr = "";
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node mNode = nodeList.item(i);
				Element element = (Element) mNode;
				resultStr = domParse.getValue(element, "result");
				Log.e("resultStr ", "resultStr :" + resultStr);

			}
			if (resultStr.equals("OK")) {
				Toast.makeText(
						getActivity(),
						""
								+ getActivity().getResources().getString(
										R.string.lock_box_success),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(
						getActivity(),
						""
								+ getActivity().getResources().getString(
										R.string.lock_box_failed),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void parseResult(String result) {

		if (result != null) {
			try {
				Log.e("TAG31", result);
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);
				NodeList nodeList = document
						.getElementsByTagName("lstResultSurveyOnlineForm");
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node mNode = nodeList.item(i);
					Element element = (Element) mNode;
					Station mStation = new Station();
					mStation.setAddress(domParse.getValue(element, "address"));
					mStation.setConnectorCode(domParse.getValue(element,
							"connectorCode"));// connectorCode
					mStation.setResourceConnector(domParse.getValue(element,
							"resourceConnector"));
					mStation.setDeptCode(domParse.getValue(element, "deptCode"));
					mStation.setResourceDevice(domParse.getValue(element,
							"resourceDevice"));
					mStation.setStationId(domParse.getValue(element,
							"stationId"));
					mStation.setConnectorId(domParse.getValue(element,
							"connectorId"));
					LocationService locationService = new LocationService(
							mStation.getConnectorCode(),
							mStation.getResourceConnector(),
							mStation.getResourceDevice(),
							mStation.getResourceConnector(),
							mStation.getAddress(), mStation.getAddress(),
							mStation.getDeptCode(), mStation.getConnectorId());
					arrayListLocation.add(locationService);
					arrayListLocationAll.add(locationService);
				}

				mAdapterLocationService.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String lockBoxByUser(String infraType, String numberOfPort,
			String serviceType, String userLock) {
		String xml = mBccsGateway
				.getXmlLockBox(
						createXMLLockBox(infraType, numberOfPort, serviceType
                        ), "mbccs_lockBoxByUser");
		Log.d("", "xml lockBoxByUser : " + xml);
		return xml;
	}

	private String createXML(String infraType, String locationCode,
			String serviceType) {
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:surveyBoxByLocationCode>");
		stringBuilder.append("<nimsInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<count>" + 10 + "</count>");
		stringBuilder.append("<start>" + 0 + "</start>");
		stringBuilder.append("<locationCode>").append(locationCode).append("</locationCode>");
		stringBuilder.append("<surveyOnlineForm>");
		ArrayList<Optional> arrayList = new ArrayList<>();
		arrayList.add(new Optional("infraType", infraType));
		arrayList.add(new Optional("locationCode", locationCode));
		arrayList.add(new Optional("serviceType", serviceType));
		// arrayList.add(new Optional("surveyRadius", ""));
		stringBuilder.append(mBccsGateway.crateXml(arrayList));
		stringBuilder.append("</surveyOnlineForm>");
		stringBuilder.append("</nimsInput>");
		stringBuilder.append("</ws:surveyBoxByLocationCode>");
		return stringBuilder.toString();
	}

	private String createXMLLockBox(String infraType, String locationCode,
                                    String serviceType) {

        String userLock = Session.userName;

		StringBuilder stringBuilder = new StringBuilder("<ws:lockBoxByUser>");
		stringBuilder.append("<nimsInput>");

		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<count>" + 10 + "</count>");
		stringBuilder.append("<start>" + 0 + "</start>");
		stringBuilder.append("<locationCode>").append(locationCode).append("</locationCode>");
		stringBuilder.append("<lockBoxForm>");
		ArrayList<Optional> arrayList = new ArrayList<>();
		arrayList.add(new Optional("connectorId", mConnectorId));
		arrayList.add(new Optional("infraType", infraType));
		arrayList.add(new Optional("numberOfPort", locationCode));
		arrayList.add(new Optional("serviceType", serviceType));
		arrayList.add(new Optional("userLock", userLock));
		stringBuilder.append(mBccsGateway.crateXml(arrayList));
		stringBuilder.append("</lockBoxForm>");
		stringBuilder.append("</nimsInput>");
		stringBuilder.append("</ws:lockBoxByUser>");
		return stringBuilder.toString();
	}

	// show tinh , huyen, xa
	private class CustomDialogShowData extends Dialog {
		private TextView provinceSpinner;
		private TextView districtSpinner;
		private Spinner streetSpinner;
		private Button search;

		private String provinceText;
		private String districtText;
		private String streetText;

		public CustomDialogShowData(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_search);

			provinceSpinner = (TextView) findViewById(R.id.spinnerProvince);
			districtSpinner = (TextView) findViewById(R.id.spinnerDistrict);
			streetSpinner = (Spinner) findViewById(R.id.spinnertram);

			// thanh pho
			if (disCode1.size() > 0) {
				provinceSpinner.setText(disCode1.get(0).getName());
			}
			// AdapterProvinceSpinner adapterSpinner = new
			// AdapterProvinceSpinner(
			// disCode1, getActivity());
			// provinceSpinner.setAdapter(adapterSpinner);
			// provinceSpinner.setEnabled(false);

			// Quan
			if (disCode2.size() > 0) {
				districtSpinner.setText(disCode2.get(0).getName());
			}
			// AdapterProvinceSpinner adapterSpinner1 = new
			// AdapterProvinceSpinner(
			// disCode2, getActivity());
			// districtSpinner.setAdapter(adapterSpinner1);
			// districtSpinner.setEnabled(false);

			// Huyen
			AdapterProvinceSpinner adapterSpinner3 = new AdapterProvinceSpinner(
					disCode3, getActivity());
			streetSpinner.setAdapter(adapterSpinner3);

			search = (Button) findViewById(R.id.btnSearch_Dialog_ShowData);

			search.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					locationCodeStr = Session.province + Session.district
							+ mPrecinct;
					mEdtSearch.setText(locationCodeStr);
					loadListInfaType();

					dialogShowData.dismiss();

				}
			});

			streetSpinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							if (position == 0) {
								mPrecinct = "";
							} else {
								mPrecinct = disCode3.get(position)
										.getPrecinct();
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
						}
					});

		}
	}

	private void parseResultError(String result) {
		if (result != null) {
			try {
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);

				NodeList nodeListErrorCode = document
						.getElementsByTagName("return");

				for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
					Node mNode = nodeListErrorCode.item(i);
					Element element = (Element) mNode;
					String errorCode = domParse.getValue(element, "errorCode");
					String description = domParse.getValue(element,
							"description");
					String token = domParse.getValue(element, "token");
					if (token == null || token.equals("")) {

					} else {
						Session.setToken(token);
					}
					if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
						CommonActivity
								.BackFromLogin(getActivity(), description,"");
					} else if (errorCode.equals("0")) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
