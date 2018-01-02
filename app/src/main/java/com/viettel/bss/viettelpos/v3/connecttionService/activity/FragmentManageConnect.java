package com.viettel.bss.viettelpos.v3.connecttionService.activity;

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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter.ViewHolder;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetFeeServiceAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListProductAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListRequestAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoConnectionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RequestBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.manage.AsyncTaskUpdateImageOfline;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.savelog.SaveLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentManageConnect extends Fragment implements OnClickListener {

	private TextView txtmayeucau, txtisdnoraccount, txtidhopdong, txtservice,
			txtgoicuoc;
	private ExpandableHeightListView listfreeService, listproduct;
	private LinearLayout lnlistfreeService, lndshanghoa;
	private Button btndaunoi;
	private Bundle mBundle = null;
	private InfoConnectionBeans infoConnectionBeans = new InfoConnectionBeans();
	private GetFeeServiceAdapter adapFeeServiceAdapter;
	private GetListProductAdapter adapListProductAdapter;
	private String status = "";

	private ListView lvUploadImage;
	private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<String, ArrayList<FileObj>>();
	private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;

	public RequestBeans requestBeansItem;

	private BCCSGateway mBccsGateway = new BCCSGateway();

	private Activity activity;

	private CheckBox checkBox;
	private Boolean isCheck = false;

	private Date timeStart = null;
	private Date timeEnd = null;
	private EditText edt_hthm;

	private ArrayList<FileObj> arrFileBackUp;

	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(
				R.layout.connection_layout_info_manager_request, container,
				false);
		unit(mView);
		return mView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9", "FragmentManageConnect onActivityResult requestCode : "
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
						"FragmentManageConnect onActivityResult() imageId: "
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
									"FragmentManageConnect onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
											+ mapListRecordPrepaid.size()
											+ " "
											+ mapListRecordPrepaid);
						}
					} else {
						Log.d(Constant.TAG,
								"FragmentManageConnect onActivityResult() holder NULL");
					}
				}
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onResume() {
		Log.e(Constant.TAG, "FragmentManageConnect onResume");
		addActionBar();
		super.onResume();
	}

	private void unit(View v) {
		mBundle = getArguments();
		if (mBundle != null) {
			try {
				infoConnectionBeans = (InfoConnectionBeans) mBundle
						.getSerializable("ConnectKey");

				status = mBundle.getString("statusKey", "");

				requestBeansItem = (RequestBeans) mBundle
						.getSerializable("requestBeansKey");
			} catch (Exception e) {
				e.toString();
			}
		}
		checkBox = (CheckBox) v.findViewById(R.id.cbIsDeploy);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isCheck = isChecked;
			}
		});
		txtmayeucau = (TextView) v.findViewById(R.id.txtmayeucau);
		if (infoConnectionBeans.getIdRequest() != null
				&& !infoConnectionBeans.getIdRequest().isEmpty()) {
			txtmayeucau.setText(infoConnectionBeans.getIdRequest());
		}
		txtisdnoraccount = (TextView) v.findViewById(R.id.txtisdnoraccount);
		if (infoConnectionBeans.getIsdnOrAccount() != null
				&& !infoConnectionBeans.getIsdnOrAccount().isEmpty()) {
			txtisdnoraccount.setText(infoConnectionBeans.getIsdnOrAccount());
		}
		txtidhopdong = (TextView) v.findViewById(R.id.txtidhopdong);
		if (infoConnectionBeans.getIdContractNo() != null
				&& !infoConnectionBeans.getIdContractNo().isEmpty()) {
			txtidhopdong.setText(infoConnectionBeans.getIdContractNo());

		}
		txtservice = (TextView) v.findViewById(R.id.txtservice);
		if (infoConnectionBeans.getServiceName() != null
				&& !infoConnectionBeans.getServiceName().isEmpty()) {
			txtservice.setText(infoConnectionBeans.getServiceName());
		}
		txtgoicuoc = (TextView) v.findViewById(R.id.txtgoicuoc);
		if (infoConnectionBeans.getPakageCharge() != null
				&& !infoConnectionBeans.getPakageCharge().isEmpty()) {
			txtgoicuoc.setText(infoConnectionBeans.getPakageCharge());
		}
		lnlistfreeService = (LinearLayout) v
				.findViewById(R.id.idlistfreeService);
		lnlistfreeService.setVisibility(View.GONE);
		lndshanghoa = (LinearLayout) v.findViewById(R.id.lndshanghoa);
		lndshanghoa.setVisibility(View.GONE);
		btndaunoi = (Button) v.findViewById(R.id.btndaunoi);
		btndaunoi.setOnClickListener(this);
		listfreeService = (ExpandableHeightListView) v
				.findViewById(R.id.listfreeService);
		listfreeService.setExpanded(true);

		lvUploadImage = (ListView) v.findViewById(R.id.lvUploadImage);
		if (status != null && !status.equals("")) {
			if (status.equals(getActivity().getResources().getString(
					R.string.status3))) {
				btndaunoi.setVisibility(View.GONE);
				lvUploadImage.setVisibility(View.GONE);
			} else {
				btndaunoi.setVisibility(View.VISIBLE);
				lvUploadImage.setVisibility(View.VISIBLE);
			}
		}

		listproduct = (ExpandableHeightListView) v
				.findViewById(R.id.listproduct);
		listproduct.setExpanded(true);
		if (infoConnectionBeans.getLisMpServiceFeeBeans().size() > 0) {
			lnlistfreeService.setVisibility(View.VISIBLE);
			adapFeeServiceAdapter = new GetFeeServiceAdapter(
					infoConnectionBeans.getLisMpServiceFeeBeans(),
					getActivity());
			listfreeService.setAdapter(adapFeeServiceAdapter);
		}

		if (infoConnectionBeans.getLisSubStockModelRelReqBeans().size() > 0) {
			lndshanghoa.setVisibility(View.VISIBLE);
			adapListProductAdapter = new GetListProductAdapter(
					infoConnectionBeans.getLisSubStockModelRelReqBeans(),
					getActivity());
			listproduct.setAdapter(adapListProductAdapter);
		}

		if (CommonActivity.isNetworkConnected(activity)) {
			GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask(
					infoConnectionBeans.getRegReasonId(),
					infoConnectionBeans.getPakageCharge(), activity);
			getLisRecordPrepaidTask.execute();
		} else {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}
	}

	private void addActionBar() {

		ActionBar mActionBar = getActivity().getActionBar();
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
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(
				R.string.manager_customer_connecttion));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btndaunoi:
			if (CommonActivity.isNetworkConnected(activity) == true) {
				if (isUploadImage()) {
					CommonActivity.createDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checksubconnect),
							getActivity().getResources().getString(
									R.string.app_name),
							getActivity().getResources().getString(
									R.string.buttonOk),
							getActivity().getResources().getString(
									R.string.cancel), subConnectClick, null)
							.show();
				} else {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.checkthieuanhhoso),
							getString(R.string.app_name)).show();
				}

			} else {
				CommonActivity.createAlertDialog(
						getActivity(),
						getActivity().getResources().getString(
								R.string.errorNetwork),
						getActivity().getResources().getString(
								R.string.app_name)).show();
			}

			break;
		default:
			break;
		}

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

	OnClickListener subConnectClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			timeStart = new Date();
			// =============== click subConnect===================
			SubConnectAsyn subConnectAsyn = new SubConnectAsyn(getActivity());
			subConnectAsyn.execute();
		}
	};

	OnClickListener subConnectSucessClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// ================ remove item from arrRequestBeans
			// ===========================
			FragmentManageRequest.fragInstance.arrRequestBeans
					.remove(FragmentManageRequest.fragInstance.requestBeansItem);

			FragmentManageRequest.fragInstance.adaRequestAdapter = new GetListRequestAdapter(
					FragmentManageRequest.fragInstance.arrRequestBeans,
					getActivity(), FragmentManageRequest.fragInstance);
			FragmentManageRequest.fragInstance.lvRequest
					.setAdapter(FragmentManageRequest.fragInstance.adaRequestAdapter);
			FragmentManageRequest.fragInstance.adaRequestAdapter
					.notifyDataSetChanged();
			getActivity().onBackPressed();

		}
	};

	private OnClickListener imageListenner = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.d(Constant.TAG, "view.getId() : " + view.getId());
			ImagePreviewActivity.pickImage(activity, hashmapFileObj,
					view.getId()+"");
		}
	};

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
				mapListRecordPrepaid = parseResultListRecordPrepaid(result);

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

		stringBuilder.append("<serviceType>"
				+ infoConnectionBeans.getServiceType() + "</serviceType>");
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

	public static Map<String, ArrayList<RecordPrepaid>> parseResultListRecordPrepaid(
			String result) {

		Map<String, ArrayList<RecordPrepaid>> map = new HashMap<String, ArrayList<RecordPrepaid>>();

		if (result != null) {
			try {
				Log.e("TAG69", result);
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);

				NodeList nodeListErrorCode = document
						.getElementsByTagName("lstProfileRecord");

				for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
					Node mNode = nodeListErrorCode.item(i);
					Element element = (Element) mNode;
					String id = domParse.getValue(element, "id");
					String code = domParse.getValue(element, "code");
					String name = domParse.getValue(element, "name");
					String require = domParse.getValue(element, "require");

					RecordPrepaid recordPrepaid = new RecordPrepaid(id, code,
							name, require);

					if (map.containsKey(id)) {
						ArrayList<RecordPrepaid> arrayList = map.get(id);
						arrayList.add(recordPrepaid);
						map.put(id, arrayList);
					} else {
						ArrayList<RecordPrepaid> arrayList = new ArrayList<RecordPrepaid>();
						arrayList.add(recordPrepaid);
						map.put(id, arrayList);
					}
				}

				Log.d("TAG69", "map : " + map.size() + " " + map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;
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
							mContext, result, subConnectSucessClick,
							getString(R.string.connectSuccess) + "\n");
					uploadImageAsy.execute();
				}
			} else {
				if (result != null && result.size() > 0) {
					for (FileObj fileObj : result) {
						File tmp = new File(fileObj.getPath());
						tmp.delete();
					}
				}

				CommonActivity.createAlertDialog(getActivity(), description,
						getActivity().getString(R.string.app_name)).show();
			}

		}
	}

	// ========== Asyntask sub connect ===================
	public class SubConnectAsyn extends AsyncTask<Void, String, String> {

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
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			progress.setMessage(String.valueOf(values[0]));
			Log.i(Constant.TAG,
					"onProgressUpdate(): " + String.valueOf(values[0]));
		}

		@Override
		protected String doInBackground(Void... arg0) {
			try {
				// errorCode = "0";
				// description = "" + new Date().getTime();
				// return errorCode;
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
									+ System.currentTimeMillis() + "_" + spinnerCode
									+ ".zip";
							lstFilePath.add(zipFilePath);
						}else if(listFileObjs.size() == 1){
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_" + listFileObjs.get(0).getCodeSpinner()
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
			try {
				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
						Session.userName, "mbccs_connectSub", CommonActivity
								.findMyLocation(context).getX(), CommonActivity
								.findMyLocation(context).getY());
				timeEnd = new Date();

				saveLog.saveLogRequest(
						errorCode,
						timeStart,
						timeEnd,
						Session.userName + "_"
								+ CommonActivity.getDeviceId(context) + "_"
								+ System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (errorCode.equals("0")) {

				// CommonActivity.createAlertDialog(
				// getActivity(),
				// getActivity().getResources().getString(
				// R.string.daunoithanhcong),
				// getActivity().getResources().getString(
				// R.string.app_name), subConnectSucessClick)
				// .show();
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
							getActivity(),
							hashmapFileObj, lstFilePath, description);
					asynZipFile.execute();
				}

			} else {

//				if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
//					for (FileObj fileObj : arrFileBackUp) {
//						File tmp = new File(fileObj.getPath());
//						tmp.delete();
//					}
//				}

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
							getActivity(), description, getResources()
									.getString(R.string.app_name));
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

				rawData.append("<fileContent>");
				rawData.append("");
				rawData.append("</fileContent>");

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
						Constant.BCCS_GW_URL, getActivity(), "mbccs_connectSub");
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

}
