package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter.ViewHolder;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetFeeServiceAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListProductAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoConnectionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.MpServiceFeeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubStockModelRelReqBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.manage.AsyncTaskUpdateImageOfline;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

public class FragmentConnectFromSignContractHotline extends GPSTracker
		implements OnClickListener {

	private TextView txtmayeucau, txtisdnoraccount, txtidhopdong, txtservice,
			txtgoicuoc;
	private ExpandableHeightListView listfreeService, listproduct;
	private LinearLayout lnlistfreeService, lndshanghoa;
	private Button btndaunoi;
	private Bundle mBundle = null;
	private InfoConnectionBeans infoConnectionBeans = new InfoConnectionBeans();
	private ArrayList<MpServiceFeeBeans> lisMpServiceFeeBeans = new ArrayList<MpServiceFeeBeans>();
	private ArrayList<SubStockModelRelReqBeans> lisSubStockModelRelReqBeans = new ArrayList<SubStockModelRelReqBeans>();
	private GetFeeServiceAdapter adapFeeServiceAdapter;
	private GetListProductAdapter adapListProductAdapter;
	private String serviceType = "";

	private ListView lvUploadImage;
	private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<String, ArrayList<FileObj>>();
	private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;

	private BCCSGateway mBccsGateway = new BCCSGateway();

	private Activity activity;
	private CheckBox checkBox;
	private Boolean isCheck = false;
	private Date timeStart = null;
	private Date timeEnd = null;
	private ArrayList<FileObj> arrFileBackUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_layout_info_manager_request);
		activity = FragmentConnectFromSignContractHotline.this;
		uniView();
	}

	private void uniView() {
		if (lisMpServiceFeeBeans.size() > 0) {
			lisMpServiceFeeBeans.clear();
		}
		if (lisSubStockModelRelReqBeans.size() > 0) {
			lisSubStockModelRelReqBeans.clear();
		}
		// CommonActivity.hideSoftKeyboard(FragmentConnectFromSignContract.this);
		mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			infoConnectionBeans = (InfoConnectionBeans) mBundle
					.getSerializable("ConnectKey");
			serviceType = mBundle.getString("serviceTypeKey", "");
		}
		txtmayeucau = (TextView) findViewById(R.id.txtmayeucau);
		if (infoConnectionBeans.getIdRequest() != null
				&& !infoConnectionBeans.getIdRequest().isEmpty()) {
			txtmayeucau.setText(infoConnectionBeans.getIdRequest());
			if (CommonActivity
					.isNetworkConnected(FragmentConnectFromSignContractHotline.this) == true) {
				GetDetailRequestAsyn getDetailRequestAsyn = new GetDetailRequestAsyn(
						FragmentConnectFromSignContractHotline.this);
				getDetailRequestAsyn
						.execute(infoConnectionBeans.getIdRequest());
			} else {
				CommonActivity.createAlertDialog(
						FragmentConnectFromSignContractHotline.this,
						FragmentConnectFromSignContractHotline.this
								.getResources()
								.getString(R.string.errorNetwork),
						FragmentConnectFromSignContractHotline.this
								.getResources().getString(R.string.app_name))
						.show();
			}
		}
		checkBox = (CheckBox) findViewById(R.id.cbIsDeploy);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isCheck = isChecked;
			}
		});
		txtisdnoraccount = (TextView) findViewById(R.id.txtisdnoraccount);
		if (infoConnectionBeans.getIsdnOrAccount() != null
				&& !infoConnectionBeans.getIsdnOrAccount().isEmpty()) {
			txtisdnoraccount.setText(infoConnectionBeans.getIsdnOrAccount());
		}
		txtidhopdong = (TextView) findViewById(R.id.txtidhopdong);
		if (infoConnectionBeans.getIdContractNo() != null
				&& !infoConnectionBeans.getIdContractNo().isEmpty()) {
			txtidhopdong.setText(infoConnectionBeans.getIdContractNo());

		}
		txtservice = (TextView) findViewById(R.id.txtservice);

		txtgoicuoc = (TextView) findViewById(R.id.txtgoicuoc);
		if (infoConnectionBeans.getPakageCharge() != null
				&& !infoConnectionBeans.getPakageCharge().isEmpty()) {
			txtgoicuoc.setText(infoConnectionBeans.getPakageCharge());
		}
		lnlistfreeService = (LinearLayout) findViewById(R.id.idlistfreeService);
		lnlistfreeService.setVisibility(View.GONE);
		lndshanghoa = (LinearLayout) findViewById(R.id.lndshanghoa);
		lndshanghoa.setVisibility(View.GONE);
		btndaunoi = (Button) findViewById(R.id.btndaunoi);
		btndaunoi.setOnClickListener(this);
		listfreeService = (ExpandableHeightListView) findViewById(R.id.listfreeService);
		listfreeService.setExpanded(true);
		listproduct = (ExpandableHeightListView) findViewById(R.id.listproduct);
		listproduct.setExpanded(true);

		lvUploadImage = (ListView) findViewById(R.id.lvUploadImage);

	}

	@Override
	protected void onResume() {
		addActionBar();
		super.onResume();
	}

	private void addActionBar() {

		ActionBar mActionBar = this.getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(
						FragmentConnectFromSignContractHotline.this,
						Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(
				R.string.create_new_request_title));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			onBackPressed();
			break;
		case R.id.btndaunoi:
			if (CommonActivity
					.isNetworkConnected(FragmentConnectFromSignContractHotline.this) == true) {
				if (isUploadImage()) {

					CommonActivity.createDialog(
							FragmentConnectFromSignContractHotline.this,
							FragmentConnectFromSignContractHotline.this
									.getResources().getString(
											R.string.checksubconnect),
							FragmentConnectFromSignContractHotline.this
									.getResources()
									.getString(R.string.app_name),
							FragmentConnectFromSignContractHotline.this
									.getResources()
									.getString(R.string.buttonOk),
							FragmentConnectFromSignContractHotline.this
									.getResources().getString(R.string.cancel),
							subConnectClick, null).show();

				} else {
					CommonActivity.createAlertDialog(
							FragmentConnectFromSignContractHotline.this,
							getString(R.string.checkthieuanhhoso),
							getString(R.string.app_name)).show();
				}

			} else {
				CommonActivity.createAlertDialog(
						FragmentConnectFromSignContractHotline.this,
						FragmentConnectFromSignContractHotline.this
								.getResources()
								.getString(R.string.errorNetwork),
						FragmentConnectFromSignContractHotline.this
								.getResources().getString(R.string.app_name))
						.show();
			}

			break;
		default:
			break;
		}

	}

	OnClickListener subConnectClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			timeStart = new Date();
			// =============== click subConnect===================
			SubConnectAsyn subConnectAsyn = new SubConnectAsyn(
					FragmentConnectFromSignContractHotline.this);
			subConnectAsyn.execute();
		}
	};

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(
					FragmentConnectFromSignContractHotline.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			if (FragmentConnectionInfoSettingHotLine.instance != null) {
				FragmentConnectionInfoSettingHotLine.instance.finish();
			}
			if (MainActivity.getInstance() != null) {
				MainActivity.getInstance().finish();
			}

		}
	};

	private OnClickListener imageListenner = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.d(Constant.TAG, "view.getId() : " + view.getId());
			ImagePreviewActivity.pickImage(activity, hashmapFileObj,
					view.getId() + "");
		}
	};

	public class GetLisRecordPrepaidTask extends AsyncTask<Void, Void, String> {

		private String productCode;
		private ProgressDialog dialog;
		private Context context;
		private String reasonId;

		public GetLisRecordPrepaidTask(String _reasonId, String _productCode,
				Context context) {
			this.reasonId = _reasonId;
			this.productCode = _productCode;
			this.context = context;
			dialog = new ProgressDialog(context);
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			// this.dialog.show();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String original = null;
			boolean isNetwork = CommonActivity.isNetworkConnected(activity);
			if (isNetwork) {
				original = requestSevice(reasonId, productCode);
			} else {
			}
			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);
				mapListRecordPrepaid = FragmentManageConnect
						.parseResultListRecordPrepaid(result);

				hashmapFileObj.clear();
				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
						mapListRecordPrepaid.values());
				RecordPrepaidAdapter adapter = new RecordPrepaidAdapter(
						activity, arrayOfArrayList, imageListenner);
				lvUploadImage.setAdapter(adapter);

				UI.setListViewHeightBasedOnChildren(lvUploadImage);
			}
			Log.e("TAG6", "result List productCode : " + result);

			super.onPostExecute(result);
		}
	}

	public String requestSevice(String reasonId, String productCode) {

		String reponse = null;
		String original = null;

		String xml = mBccsGateway.getXmlCustomer(
				createXML(reasonId, productCode), "mbccs_getListRecordPrepaid");
		Log.e("TAG8", "xml getListRecordPrepaid" + xml);
		try {
			reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
					activity, "mbccs_getListRecordPrepaid");
			Log.e("TAG8", "reponse getListRecordPrepaid" + reponse);
		} catch (NotFoundException e) {
			e.printStackTrace();
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

	private String createXML(String reasonId, String productCode) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ws:getListRecordPrepaid>");
		stringBuilder.append("<cmInput>");
		stringBuilder.append("<isConnect>");
		stringBuilder.append(true);
		stringBuilder.append("</isConnect>");
		stringBuilder.append("<isPospaid>" + true + "</isPospaid>");

		stringBuilder.append("<serviceType>" + serviceType + "</serviceType>");
		stringBuilder.append("<token>" + Session.getToken() + "</token>");
		stringBuilder.append("<productCode>" + productCode + "</productCode>");
		stringBuilder.append("<reasonId>" + reasonId + "</reasonId>");
		stringBuilder.append("</cmInput>");
		stringBuilder.append("</ws:getListRecordPrepaid>");
		return stringBuilder.toString();
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
						CommonActivity.BackFromLogin(activity, description);
					} else if (errorCode.equals("0")) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9",
				"FragmentConnectFromSignContract onActivityResult requestCode : "
						+ requestCode);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {

			case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
				Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");

				Parcelable[] parcelableUris = data
						.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

				if (parcelableUris == null) {
					return;
				}
				// Java doesn't allow array casting, this is a little hack
				Uri[] uris = new Uri[parcelableUris.length];
				System.arraycopy(parcelableUris, 0, uris, 0,
						parcelableUris.length);

				int imageId = data.getExtras().getInt("imageId", -1);

				Log.d(Constant.TAG,
						"FragmentConnectFromSignContract onActivityResult() imageId: "
								+ imageId);

				if (uris != null && uris.length > 0 && imageId >= 0) {
					ViewHolder holder = null;
					for (int i = 0; i < lvUploadImage.getChildCount(); i++) {
						View rowView = lvUploadImage.getChildAt(i);
						ViewHolder h = (ViewHolder) rowView.getTag();
						if (h != null) {
							int id = h.ibUploadImage.getId();
							if (imageId == id) {
								holder = h;
								break;
							}
						}
					}
					if (holder != null) {
						Picasso.with(activity)
								.load(new File(uris[0].toString()))
								.centerCrop().resize(100, 100)
								.into(holder.ibUploadImage);

						int position = holder.spUploadImage
								.getSelectedItemPosition();

						if (position < 0) {
							position = 0;
						}

						ArrayList<RecordPrepaid> recordPrepaids = mapListRecordPrepaid
								.get(String.valueOf(imageId));

						if (recordPrepaids != null) {
							RecordPrepaid recordPrepaid = recordPrepaids
									.get(position);

							String spinnerCode = recordPrepaid.getCode();

							Log.i(Constant.TAG, "imageId: " + imageId
									+ " spinner position: " + position
									+ " spinnerCode: " + spinnerCode
									+ " uris: " + uris.length);

							ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
							for (int i = 0; i < uris.length; i++) {
								File uriFile = new File(uris[i].getPath());
								String fileNameServer = spinnerCode + "-"
										+ (i + 1) + ".jpg";
								FileObj obj = new FileObj(spinnerCode, uriFile,
										uris[i].getPath(), fileNameServer);
								fileObjs.add(obj);
							}
							hashmapFileObj.put(String.valueOf(imageId),
									fileObjs);
						} else {
							Log.d(Constant.TAG,
									"FragmentConnectFromSignContract onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
											+ mapListRecordPrepaid.size()
											+ " "
											+ mapListRecordPrepaid);
						}
					} else {
						Log.d(Constant.TAG,
								"FragmentConnectFromSignContract onActivityResult() holder NULL");
					}
				}
				break;

			default:
				break;
			}
		}
	}

	private boolean isUploadImage() {
		Log.d(Constant.TAG,
				"isUploadImage() hashmapFileObj: " + hashmapFileObj.size()
						+ " " + hashmapFileObj);
		Log.d(Constant.TAG, "isUploadImage() mapListRecordPrepaid: "
				+ mapListRecordPrepaid.size() + " " + mapListRecordPrepaid);

		boolean isUploadImage = !hashmapFileObj.isEmpty()
				&& hashmapFileObj.size() == mapListRecordPrepaid.size();

		return isUploadImage;
	}

	private class AsynZipFile extends
			AsyncTask<String, Void, ArrayList<FileObj>> {

		private HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
		private Context mContext;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";
		private ArrayList<String> lstFilePath = new ArrayList<String>();
		private String actionAuditId = "";

		public AsynZipFile(Context context,
				HashMap<String, ArrayList<FileObj>> hasMapFile,
				ArrayList<String> mlstFilePath, String actionaudit) {
			this.mContext = context;
			this.mHashMapFileObj = hasMapFile;
			this.lstFilePath = mlstFilePath;
			this.actionAuditId = actionaudit;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(mContext);
			// check font
			this.progress.setMessage(mContext.getResources().getString(
					R.string.progress_zip));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<FileObj> doInBackground(String... arg0) {
			ArrayList<FileObj> arrFileBackUp1 = null;
			try {
				arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext,
						mHashMapFileObj, lstFilePath);
				errorCode = "0";
				return arrFileBackUp1;
			} catch (Exception e) {
				errorCode = "1";
				description = "Error when zip file: " + e.toString();
				return arrFileBackUp1;
			}
		}

		@Override
		protected void onPostExecute(ArrayList<FileObj> result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null && !result.isEmpty()) {
					for (FileObj fileObj2 : result) {
						fileObj2.setActionCode("00"); // đấu nối : 00
						// đăng ký thông
						// tin 04
						fileObj2.setReasonId(infoConnectionBeans
								.getRegReasonId());
						fileObj2.setIsdn(infoConnectionBeans.getIsdnOrAccount());
						fileObj2.setActionAudit(actionAuditId);
						fileObj2.setPageSize(0 + "");
					}
					AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(
							mContext, result, moveFragmentManageClick,
							getString(R.string.connectSuccess) + "\n");
					uploadImageAsy.execute();
				}
			} else {
				// if (result != null && result.size() > 0) {
				// for (FileObj fileObj : result) {
				// File tmp = new File(fileObj.getPath());
				// tmp.delete();
				// }
				// }

				CommonActivity.createAlertDialog(
						FragmentConnectFromSignContractHotline.this,
						description,
						FragmentConnectFromSignContractHotline.this
								.getString(R.string.app_name)).show();
			}

		}
	}

	// ========== Asyntask sub connect ===================
	public class SubConnectAsyn extends AsyncTask<Void, Void, String> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ArrayList<String> lstFilePath = new ArrayList<String>();

		public SubConnectAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.subconnecting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Void... arg0) {
			try {
				// arrFileBackUp = FileUtils.getArrFileBackUp(context,
				// hashmapFileObj);
				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {

					File folder = new File(
							Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
					if (!folder.exists()) {
						folder.mkdir();
					}
					Log.d("Log",
							"Folder zip file create: "
									+ folder.getAbsolutePath());
					for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
							.entrySet()) {
						ArrayList<FileObj> listFileObjs = entry.getValue();
						String zipFilePath = "";
						if (listFileObjs.size() > 1) {
							String spinnerCode = "";
							for (FileObj fileObj : listFileObjs) {
								spinnerCode = fileObj.getCodeSpinner();
							}
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_"
									+ spinnerCode + ".zip";
							lstFilePath.add(zipFilePath);
						} else if (listFileObjs.size() == 1) {
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_"
									+ listFileObjs.get(0).getCodeSpinner()
									+ ".jpg";
							lstFilePath.add(zipFilePath);
						}
					}
				}
				return subConnect(lstFilePath);
			} catch (Exception e) {
				errorCode = "1";
				description = "Error when zip file: " + e.toString();
				return "1";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();

			if (errorCode.equals("0")) {

				// CommonActivity.createAlertDialog(FragmentConnectFromSignContractHotline.this,
				// FragmentConnectFromSignContractHotline.this.getResources().getString(R.string.daunoithanhcong),
				// FragmentConnectFromSignContractHotline.this.getResources().getString(R.string.app_name),
				// moveFragmentManageClick).show();
				//
				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
				// for (FileObj fileObj : arrFileBackUp) {
				// fileObj.setActionCode("00"); // đấu nối : 00 đăng ký
				// thông tin 04
				// fileObj.setReasonId(infoConnectionBeans.getRegReasonId());
				// fileObj.setIsdn(infoConnectionBeans.getIsdnOrAccount());
				// fileObj.setActionAudit(description);
				// fileObj.setPageSize(0 + "");
				// fileObj.setStatus(0);
				// }
				// FileUtils.insertFileBackUpToDataBase(arrFileBackUp,
				// activity.getApplicationContext());
				// }

				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {

					// File folder = new
					// File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
					// if (!folder.exists()) {
					// folder.mkdir();
					// }
					// Log.d("Log", "Folder zip file create: " +
					// folder.getAbsolutePath());
					// ArrayList<String> lstZipFilePath = new
					// ArrayList<String>();
					// for (java.util.Map.Entry<String, ArrayList<FileObj>>
					// entry : hashmapFileObj
					// .entrySet()) {
					// ArrayList<FileObj> listFileObjs = entry.getValue();
					// if (listFileObjs.size() > 1) {
					// String spinnerCode = "";
					// for (FileObj fileObj : listFileObjs) {
					// spinnerCode = fileObj.getCodeSpinner();
					// }
					// String zipFilePath = folder.getPath() + File.separator
					// + System.currentTimeMillis() + "_" + spinnerCode
					// + ".zip";
					// lstZipFilePath.add(zipFilePath);
					// }
					// }

					AsynZipFile asynZipFile = new AsynZipFile(
							FragmentConnectFromSignContractHotline.this,
							hashmapFileObj, lstFilePath, description);
					asynZipFile.execute();
				}

			} else {

				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
				// for (FileObj fileObj : arrFileBackUp) {
				// File tmp = new File(fileObj.getPath());
				// tmp.delete();
				// }
				// }

				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectFromSignContractHotline.this,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		public String subConnect(ArrayList<String> mlstFilePath) {

			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_connectSub");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:connectSub>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (infoConnectionBeans.getIdRequest() != null
						&& !infoConnectionBeans.getIdRequest().isEmpty()) {
					rawData.append("<subReqId>"
							+ infoConnectionBeans.getIdRequest());
					rawData.append("</subReqId>");
				}

				for (String fileObj : mlstFilePath) {
					rawData.append("<lstFilePath>");
					rawData.append(fileObj);
					rawData.append("</lstFilePath>");
				}

				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
						mapListRecordPrepaid.values());
				for (int j = 0; j < arrayOfArrayList.size(); j++) {
					ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList
							.get(j);
					View rowView = lvUploadImage.getChildAt(j);
					ViewHolder h = (ViewHolder) rowView.getTag();
					if (h != null) {
						int indexSelection = h.spUploadImage
								.getSelectedItemPosition();
						RecordPrepaid recordPrepaid = listRecordPrepaid
								.get(indexSelection);
						rawData.append("<lstRecordName>");
						rawData.append(recordPrepaid.getName());
						rawData.append("</lstRecordName>");
					}
				}

				rawData.append("<isDeploy>" + isCheck + "");
				rawData.append("</isDeploy>");
				rawData.append("<isdn>"
						+ infoConnectionBeans.getIsdnOrAccount());
				rawData.append("</isdn>");
				rawData.append("<reasonId>"
						+ infoConnectionBeans.getRegReasonId());
				rawData.append("</reasonId>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:connectSub>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectFromSignContractHotline.this,
						"mbccs_connectSub");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}
			} catch (Exception e) {
				Log.d("subConnect", e.toString());
			}
			return errorCode;
		}
	}

	OnClickListener moveFragmentManageClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(
					FragmentConnectFromSignContractHotline.this,
					ActivityCreateNewRequestHotLine.class);
			startActivity(intent);
			FragmentConnectionInfoSettingHotLine.instance.finish();
			if (ActivityCreateNewRequestHotLine.instance != null) {
				ActivityCreateNewRequestHotLine.instance.finish();
			}
			finish();
		}
	};

	// ==================ws get detail request=========================
	private class GetDetailRequestAsyn extends
			AsyncTask<String, Void, InfoConnectionBeans> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetDetailRequestAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected InfoConnectionBeans doInBackground(String... arg0) {
			return getDetailRequestBean(arg0[0]);
		}

		@Override
		protected void onPostExecute(InfoConnectionBeans result) {
			progress.dismiss();
			// ======== succces =================
			if (errorCode.equals("0")) {
				if (result.getIdRequest() != null
						|| result.getIdContractNo() != null) {
					infoConnectionBeans = result;
					if (infoConnectionBeans.getServiceName() != null
							&& !infoConnectionBeans.getServiceName().isEmpty()) {
						txtservice
								.setText(infoConnectionBeans.getServiceName());
					}
					if (infoConnectionBeans.getLisMpServiceFeeBeans().size() > 0) {
						lnlistfreeService.setVisibility(View.VISIBLE);
						adapFeeServiceAdapter = new GetFeeServiceAdapter(
								infoConnectionBeans.getLisMpServiceFeeBeans(),
								FragmentConnectFromSignContractHotline.this);
						listfreeService.setAdapter(adapFeeServiceAdapter);
					}

					if (infoConnectionBeans.getLisSubStockModelRelReqBeans()
							.size() > 0) {
						lndshanghoa.setVisibility(View.VISIBLE);
						adapListProductAdapter = new GetListProductAdapter(
								infoConnectionBeans
										.getLisSubStockModelRelReqBeans(),
								FragmentConnectFromSignContractHotline.this);
						listproduct.setAdapter(adapListProductAdapter);
					}

					if (CommonActivity.isNetworkConnected(activity)) {
						GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask(
								result.getRegReasonId(),
								infoConnectionBeans.getPakageCharge(), activity);
						getLisRecordPrepaidTask.execute();
					} else {
						CommonActivity.createAlertDialog(activity,
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}

				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectFromSignContractHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null && description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectFromSignContractHotline.this,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private InfoConnectionBeans getDetailRequestBean(String idRequest) {
			InfoConnectionBeans inConnectionBeans = new InfoConnectionBeans();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_viewDetailRequest");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:viewDetailRequest>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("token", Session.getToken());
				rawData.append(input.buildXML(param));
				rawData.append("<subReqId>" + idRequest);
				rawData.append("</subReqId>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:viewDetailRequest>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectFromSignContractHotline.this,
						"mbccs_viewDetailRequest");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// =======parse xml =================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nlsubRequest = null;
				NodeList nlstockModelRelReqs = null;
				NodeList nlmpServiceFeeList = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nlsubRequest = doc.getElementsByTagName("subRequest");
					for (int j = 0; j < nlsubRequest.getLength(); j++) {
						Element e1 = (Element) nlsubRequest.item(j);
						String subReqId = parse.getValue(e1, "subReqId");
						Log.d("subReqId", subReqId);
						inConnectionBeans.setIdRequest(subReqId);
						String isdn = parse.getValue(e1, "isdn");
						Log.d("isdn", isdn);
						String account = parse.getValue(e1, "account");
						Log.d("account", account);
						if (account != null && !account.isEmpty()) {
							inConnectionBeans.setIsdnOrAccount(account);
						} else {
							inConnectionBeans.setIsdnOrAccount(isdn);
						}
						String contractNo = parse.getValue(e1, "contractNo");
						Log.d("contractNo", contractNo);
						inConnectionBeans.setIdContractNo(contractNo);
						String cusId = parse.getValue(e1, "custId");
						inConnectionBeans.setCustId(cusId);
						String serviceType = parse.getValue(e1, "serviceType");

						inConnectionBeans.setServiceType(serviceType);
						String reasonId = parse.getValue(e1, "regReasonId");
						inConnectionBeans.setRegReasonId(reasonId);

						if (serviceType != null && !serviceType.isEmpty()) {
							try {
								GetServiceDal getServiceDal = new GetServiceDal(
										FragmentConnectFromSignContractHotline.this);
								getServiceDal.open();
								String serviceName = getServiceDal
										.getDescription(serviceType);
								Log.d("serviceName", serviceName);
								inConnectionBeans.setServiceName(serviceName);
								getServiceDal.close();
							} catch (Exception e) {
								Log.d("getlistRequest", e.toString());
							}
						}

						String pakageCharge = parse.getValue(e1, "productCode");
						Log.d("pakageCharge", pakageCharge);
						inConnectionBeans.setPakageCharge(pakageCharge);

						// parse mpServiceFeeList
						nlmpServiceFeeList = doc
								.getElementsByTagName("mpServiceFeeList");
						for (int k = 0; k < nlmpServiceFeeList.getLength(); k++) {
							Element e3 = (Element) nlmpServiceFeeList.item(k);
							MpServiceFeeBeans mpServiceFeeBeans = new MpServiceFeeBeans();
							String amount = parse.getValue(e3, "amount");
							Log.d("amount", amount);

							mpServiceFeeBeans.setAmount(amount);
							String feeCode = parse.getValue(e3, "feeCode");
							Log.d("feeCode", feeCode);
							mpServiceFeeBeans.setFeeCode(feeCode);

							String feeName = parse.getValue(e3, "feeName");
							Log.d("feeName", feeName);
							mpServiceFeeBeans.setFeeName(feeName);

							String priceId = parse.getValue(e3, "priceId");
							Log.d("priceId", priceId);
							mpServiceFeeBeans.setPriceId(priceId);

							String realStep = parse.getValue(e3, "realStep");
							Log.d("realStep", realStep);
							mpServiceFeeBeans.setRealStep(realStep);

							String revenueObj = parse
									.getValue(e3, "revenueObj");
							Log.d("revenueObj", revenueObj);
							mpServiceFeeBeans.setRevenueObj(revenueObj);

							String saleServiceId = parse.getValue(e3,
									"saleServiceId");
							Log.d("saleServiceId", saleServiceId);
							mpServiceFeeBeans.setSaleServiceId(saleServiceId);

							String stockModelId = parse.getValue(e3,
									"stockModelId");
							Log.d("stockModelId", stockModelId);
							mpServiceFeeBeans.setStockModelId(stockModelId);

							String vat = parse.getValue(e3, "vat");
							Log.d("vat", vat);
							mpServiceFeeBeans.setVat(vat);

							lisMpServiceFeeBeans.add(mpServiceFeeBeans);
						}
						// ==========add setLisMpServiceFeeBeans ===============
						inConnectionBeans
								.setLisMpServiceFeeBeans(lisMpServiceFeeBeans);

						// parse stockModelRelReqs
						nlstockModelRelReqs = doc
								.getElementsByTagName("stockModelRelReqs");
						for (int m = 0; m < nlstockModelRelReqs.getLength(); m++) {
							Element e4 = (Element) nlstockModelRelReqs.item(m);
							SubStockModelRelReqBeans subModelRelReqBeans = new SubStockModelRelReqBeans();
							String reclaimAmount = parse.getValue(e4,
									"reclaimAmount");
							Log.d("reclaimAmount", reclaimAmount);
							subModelRelReqBeans.setReclaimAmount(reclaimAmount);

							String reclaimCommitmentCode = parse.getValue(e4,
									"reclaimCommitmentCode");
							Log.d("reclaimCommitmentCode",
									reclaimCommitmentCode);
							subModelRelReqBeans
									.setReclaimCommitmentCode(reclaimCommitmentCode);

							String reclaimCommitmentName = parse.getValue(e4,
									"reclaimCommitmentName");
							Log.d("reclaimCommitmentName",
									reclaimCommitmentName);
							subModelRelReqBeans
									.setReclaimCommitmentName(reclaimCommitmentName);

							String serial = parse.getValue(e4, "serial");
							Log.d("serial", serial);
							subModelRelReqBeans.setSerial(serial);

							String status = parse.getValue(e4, "status");
							Log.d("status", status);
							subModelRelReqBeans.setStatus(status);

							String stockModelId = parse.getValue(e4,
									"stockModelId");
							Log.d("stockModelId", stockModelId);
							subModelRelReqBeans.setStockModelId(stockModelId);

							String stockModelName = parse.getValue(e4,
									"stockModelName");
							Log.d("stockModelName", stockModelName);
							subModelRelReqBeans
									.setStockModelName(stockModelName);

							String stockTypeId = parse.getValue(e4,
									"stockTypeId");
							Log.d("stockTypeId", stockTypeId);
							subModelRelReqBeans.setStockTypeId(stockTypeId);

							String stockTypeName = parse.getValue(e4,
									"stockTypeName");
							Log.d("stockTypeName", stockTypeName);
							subModelRelReqBeans.setStockTypeName(stockTypeName);

							String subId = parse.getValue(e4, "subId");
							Log.d("subId", subId);
							subModelRelReqBeans.setSubId(subId);

							String subStockModelRelId = parse.getValue(e4,
									"subStockModelRelId");
							Log.d("subStockModelRelId", subStockModelRelId);
							subModelRelReqBeans
									.setSubStockModelRelId(subStockModelRelId);

							lisSubStockModelRelReqBeans
									.add(subModelRelReqBeans);
						}
						// ===============add list product ==========
						inConnectionBeans
								.setLisSubStockModelRelReqBeans(lisSubStockModelRelReqBeans);
					}
				}

			} catch (Exception e) {
				Log.d("getDetailRequestBean", e.toString());
			}

			return inConnectionBeans;
		}

	}

}
