package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.picker.Config;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentContractVerifyUpdateDetail extends FragmentCommon {

	private Activity activity;

	private LinearLayout lnVerifyNote;

    private Spinner spnVerifyType, spnReasonVerify, spnCustType, spnVerifyNote;

	private ImageButton ibUploadImage;

    private ChargeContractItem chargContractItem;

	private List<ReasonBean> lstReasonBean = new ArrayList<>();
	private List<ReasonBean> lstCustType = new ArrayList<>();

	private MapView mapview;

	private Location location;

	private String lat = "0";
	private String lon = "0";

	// private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new
	// HashMap<String, ArrayList<FileObj>>();
	private ArrayList<FileObj> lstFile = new ArrayList<>();

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.contract_verify_update_detail);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_contract_verify_update_detail;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		Bundle bundle = getArguments();
		if (bundle != null) {
			chargContractItem = (ChargeContractItem) bundle
					.getSerializable("chargContractItem");
			Log.d(Constant.TAG, "FragmentContractVerifyUpdateDetail "
					+ chargContractItem.toString());
		} else {
			Log.d(Constant.TAG,
					"FragmentContractVerifyUpdateDetail bundle NULL");
		}
	}

	@Override
	public void unit(final View v) {
        TextView tvContractId = (TextView) v.findViewById(R.id.tvContractId);
		tvContractId.setText(chargContractItem.getContractNo());

		spnVerifyType = (Spinner) v.findViewById(R.id.spnVerifyType);
		spnReasonVerify = (Spinner) v.findViewById(R.id.spnReasonVerify);
		spnCustType = (Spinner) v.findViewById(R.id.spnCustType);

		lnVerifyNote = (LinearLayout) v.findViewById(R.id.lnVerifyNote);
		spnVerifyNote = (Spinner) v.findViewById(R.id.spnVerifyNote);
		ibUploadImage = (ImageButton) v.findViewById(R.id.ibUploadImage);
        ImageButton ibMap = (ImageButton) v.findViewById(R.id.ibMap);
        Button btn_update = (Button) v.findViewById(R.id.btn_update);
		mapview = (MapView) v.findViewById(R.id.mapview);
		// init map
		// MapConfig.changeSRS(SRSType.SRS_4326);
		MapConfig.changeSRS(SRSType.SRS_900913);
		mapview.setZoom(16);
		mapview.setGPSControlEnabled(true);
		location = CommonActivity.findMyLocation(act);
		if (location != null) {
			lat = location.getX();
			lon = location.getY();
		}
		spnVerifyType.setSelected(false);
		spnVerifyType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d(Constant.TAG,
								"spnVerifyType onItemClick position:"
										+ position);
						if (position == 0) {
							lnVerifyNote.setVisibility(View.GONE);
							v.findViewById(R.id.lnReason).setVisibility(View.GONE);
						} else {
							lnVerifyNote.setVisibility(View.VISIBLE);
							v.findViewById(R.id.lnReason).setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						Log.d(Constant.TAG, "spnVerifyType onNothingSelected");
					}
				});

		ibUploadImage.setOnClickListener(this);
		ibMap.setOnClickListener(this);
		btn_update.setOnClickListener(this);

		AsyncReasonVerify asyncReasonVerify = new AsyncReasonVerify(activity);
		asyncReasonVerify.execute();

		AsyncCustType asyncCustType = new AsyncCustType(activity);
		asyncCustType.execute();
	}

	private void loadMap() {
		if (location == null) {
			location = CommonActivity.findMyLocation(act);
		}

		lat = location.getX();
		lon = location.getY();

		Double x = 21.027737;
		Double y = 105.852364;

		try {
			x = Double.parseDouble(lat);
			y = Double.parseDouble(lon);
		} catch (Exception e) {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.not_update_location),
					getString(R.string.app_name)).show();
			e.printStackTrace();
		}

		LatLng latLng = new LatLng(x, y);

		MarkerOptions markerOptions = new MarkerOptions().icon(
				BitmapFactory
						.decodeResource(getResources(), R.drawable.iconmap))
				.position(latLng);
		Marker marker = mapview.addMarker(markerOptions);
		marker.setDraggable(true);
		mapview.setCenter(latLng);

		mapview.setVisibility(View.VISIBLE);

		mapview.setMarkerDragListener(new MapLayer.OnDragListener() {

			@Override
			public boolean onDrag(Point arg0, LatLng arg1, MapObject arg2) {
				// Log.d(Constant.TAG, "mapview onDrag latitude : " + lat +
				// " longtitude : " + lon);
				return true;
			}

			@Override
			public boolean onDragEnd(Point arg0, LatLng arg1, MapObject arg2) {
				lat = String.valueOf(arg1.getLatitude());
				lon = String.valueOf(arg1.getLongitude());

				Log.d(Constant.TAG, "mapview onDragEnd latitude : " + lat
						+ " longtitude : " + lon);
				return true;
			}

			@Override
			public boolean onDragStart(Point arg0, LatLng arg1, MapObject arg2) {
				return true;
			}
		});

		mapview.setMarkerClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {

				return true;
			}

		});

		mapview.setInfoWindowClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				return true;
			}

		});
	}

    // confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (CommonActivity.isNetworkConnected(activity)) {
				AsyncUpdateVerify asyncUpdateVerify = new AsyncUpdateVerify(
						activity, contractId, reasonId, verifyType, custType,
						lat, lon, note);

				Log.d(Constant.TAG,
						"FragmentContractVerifyUpdateDetail onClick btn_update "
								+ asyncUpdateVerify.toString());

				asyncUpdateVerify.execute();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	private String contractId;
	private String reasonId;
	private String verifyType;
	private String custType;
	private String note;

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.ibUploadImage:
			if (CommonActivity.isNetworkConnected(activity)) {
				int imageId = ibUploadImage.getId();
				Log.d(Constant.TAG,
						"FragmentContractVerifyUpdateDetail onClick ibUploadImage imageId: "
								+ imageId);

				if (!CommonActivity.isNullOrEmpty(lstFile)) {

					Intent intent = new Intent(activity,
							ImagePreviewActivity.class);

					intent.putExtra("imageId", imageId);
					intent.putExtra("fileObjs", lstFile);
					intent.putExtra("limitImage", 3);

					activity.startActivityForResult(intent,
							CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
				} else {
					Config config = new Config.Builder().setSelectionLimit(3)
							.build();

					Intent intent = new Intent(activity,
							ImagePickerActivity.class);
					intent.putExtra("imageId", imageId);

					ImagePickerActivity.setConfig(config);
					activity.startActivityForResult(intent,
							CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
				}
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		case R.id.ibMap:
			if (CommonActivity.isNetworkConnected(activity)) {
				Log.d(Constant.TAG,
						"FragmentContractVerifyUpdateDetail onClick ibMap");
				loadMap();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		case R.id.btn_update:

			if (CommonActivity.isNullOrEmpty(lstFile)) {
				CommonActivity.createAlertDialog(activity,
						R.string.xac_minh_chon_file, R.string.app_name).show();
				return;
			}

			for (FileObj file : lstFile) {
				if (file.getFile() == null || file.getFile().length() < 0) {
					CommonActivity.createAlertDialog(activity,
							R.string.xac_minh_chon_file, R.string.app_name)
							.show();
					return;
				}
			}
			if (CommonActivity.isNetworkConnected(activity)) {
				if (checkUpdate()) {
					contractId = chargContractItem.getContractId();

					int posReasonVerify = spnReasonVerify
							.getSelectedItemPosition();

					ReasonBean reasonBean = lstReasonBean.get(posReasonVerify);
					reasonId = "";
					if (reasonBean != null) {
						reasonId = reasonBean.getId();
					}

					int posVerifyType = spnVerifyType.getSelectedItemPosition();
					verifyType = activity.getResources().getStringArray(
							R.array.verify_type_value)[posVerifyType];

					int posCustType = spnCustType.getSelectedItemPosition();
					ReasonBean custTypeBean = lstCustType.get(posCustType);
					custType = custTypeBean.getId();

					note = posVerifyType == 0 ? "" : spnVerifyNote
							.getSelectedItem().toString();

					CommonActivity.createDialog(activity,
							getString(R.string.message_confirm_update),
							getString(R.string.app_name),
							getString(R.string.say_ko),
							getString(R.string.say_co), null, confirmChargeAct)
							.show();
				}

			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}

	private boolean checkUpdate() {
		if (lstReasonBean == null || lstReasonBean.isEmpty()) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checklydoxacminh),
					getString(R.string.app_name)).show();
			return false;
		}
		if (lstCustType == null || lstCustType.isEmpty()) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkdoituongxacminh),
					getString(R.string.app_name)).show();
			return false;
		}

		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9",
				"FragmentContractVerifyUpdateDetail onActivityResult requestCode : "
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
					Picasso.with(activity)
							.load(new File(uris[uris.length - 1].toString()))
							.centerCrop().resize(100, 100).into(ibUploadImage);

					String spinnerCode = System.currentTimeMillis() + "";
					ArrayList<FileObj> fileObjs = new ArrayList<>();
					for (int i = 0; i < uris.length; i++) {
						File uriFile = new File(uris[i].getPath());
						String fileNameServer = spinnerCode + "_" + (i + 1)
								+ "_" + uris.length + ".jpg";
						FileObj obj = new FileObj(spinnerCode, uriFile,
								uris[i].getPath(), fileNameServer);
						fileObjs.add(obj);
					}
					// hashmapFileObj.put(String.valueOf(imageId), fileObjs);
					lstFile = fileObjs;
				}
				break;

			default:
				break;
			}
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

	private class AsyncReasonVerify extends
			AsyncTask<String, Void, List<ReasonBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsyncReasonVerify(Activity mActivity) {

			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected List<ReasonBean> doInBackground(String... params) {
			return getReasonVerify();
		}

		@Override
		protected void onPostExecute(List<ReasonBean> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.checklydoxacminh),
							getString(R.string.app_name)).show();
				} else {
					lstReasonBean = result;
					List<String> spinnerArray = new ArrayList<>();
					for (ReasonBean reasonBean : lstReasonBean) {
						spinnerArray.add(reasonBean.getName());
					}
					ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                            mActivity, android.R.layout.simple_spinner_item,
                            spinnerArray);
					spinnerArrayAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnReasonVerify.setAdapter(spinnerArrayAdapter);
				}
			} else {
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

		private List<ReasonBean> getReasonVerify() {
			List<ReasonBean> lstReasonBean = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getReasonVerify");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReasonVerify>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:getReasonVerify>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getReasonVerify");
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
					nodechild = doc.getElementsByTagName("lstReasonBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReasonBean obj = new ReasonBean();
						obj.setId(parse.getValue(e1, "id"));
						obj.setCode(parse.getValue(e1, "code"));
						obj.setName(parse.getValue(e1, "name"));
						lstReasonBean.add(obj);
					}
				}
				Log.i(Constant.TAG, "getReasonVerify lstReasonBean "
						+ lstReasonBean.size());
			} catch (Exception e) {
				Log.e("getReasonVerify", e.toString(), e);
			}
			return lstReasonBean;
		}
	}

	private class AsyncCustType extends
			AsyncTask<String, Void, List<ReasonBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsyncCustType(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected List<ReasonBean> doInBackground(String... params) {
			return getCustType();
		}

		@Override
		protected void onPostExecute(List<ReasonBean> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.checkdoituongxacminh),
							getString(R.string.app_name)).show();
				} else {
					lstCustType = result;

					List<String> list = new ArrayList<>();
					for (ReasonBean reasonBean : result) {
						list.add(reasonBean.getName());
					}
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                            mActivity, android.R.layout.simple_spinner_item,
                            list);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnCustType.setAdapter(dataAdapter);
				}
			} else {
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

		private List<ReasonBean> getCustType() {
			List<ReasonBean> lstReasonBean = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustType>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");
				rawData.append("<pageIndex>" + Constant.PAGE_INDEX
						+ "</pageIndex>");
				rawData.append("<pageSize>" + Constant.PAGE_SIZE
						+ "</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:getCustType>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input
						.sendRequest(envelope, Constant.BCCS_GW_URL,
								getActivity(), "mbccs_getCustType");
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
					nodechild = doc.getElementsByTagName("lstApDomainBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReasonBean obj = new ReasonBean();
						obj.setId(parse.getValue(e1, "value"));
						obj.setCode(parse.getValue(e1, "code"));
						obj.setName(parse.getValue(e1, "name"));
						lstReasonBean.add(obj);
					}
				}
				Log.i(Constant.TAG, "getCustType lstReasonBean "
						+ lstReasonBean.size());
			} catch (Exception e) {
				Log.e("getCustType", e.toString(), e);
			}
			return lstReasonBean;
		}
	}

	private class AsyncUpdateVerify extends AsyncTask<String, Void, Boolean> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String errorDescription = "";
		private ProgressDialog progress;

		private final String contractId;
		private final String reasonId;
		private final String verifyType;
		private final String custType;
		private final String xpos;
		private final String ypos;
		private final String note;

		public AsyncUpdateVerify(Activity mActivity, String contractId,
				String reasonId, String verifyType, String custType,
				String xpos, String ypos, String note) {
			super();
			this.mActivity = mActivity;
			this.contractId = contractId;
			this.reasonId = reasonId;
			this.verifyType = verifyType;
			this.custType = custType;
			this.xpos = xpos;
			this.ypos = ypos;
			this.note = note;
		}

		@Override
		protected void onPreExecute() {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected Boolean doInBackground(String... params) {
			String base64 = "";
			// Thuc hien zip file

			try {
				File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
				if (!folder.exists()) {
					folder.mkdir();
				}
				String isdn = "";
				if (chargContractItem != null
						&& chargContractItem.getIsdn() != null) {
					isdn = chargContractItem.getIsdn();
				}
				String zipFilePath = folder.getPath() + File.separator + "PXM_"
						+ isdn + new Date().getTime() + ".zip";
				List<File> files = new ArrayList<>();
				for (FileObj file : lstFile) {
					Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
					
					Bitmap bitmapImage = CommonActivity.getResizedBitmap(
							bitmap, Constant.SIZE_IMAGE_SCALE);
					File fileDes = new File(folder.getPath() + File.separator
							+ file.getFile().getName());
					FileOutputStream out = null;
					out = new FileOutputStream(fileDes);
					bitmapImage.compress(Bitmap.CompressFormat.JPEG,
							Constant.DEFAULT_JPEG_QUALITY, out);
					out.close();
					files.add(fileDes);
				}

				File isdnZip = FileUtils.zip(files, zipFilePath);

				for (File file : files) {
					file.delete();
				}
				// bitmapImage.compress(Bitmap.CompressFormat.PNG,
				// 100, out);

				// Bitmap bitmap =
				// BitmapFactory.decodeFile(fileImage.getPath());

				// Bitmap bitmapImage = CommonActivity.getResizedBitmap(bitmap,
				// 720);
				//
				// ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// bitmapImage.compress(Bitmap.CompressFormat.JPEG,
				// Constant.DEFAULT_JPEG_QUALITY, stream);
				// byte[] byteArray = stream.toByteArray();

				byte[] byteArray = FileUtils.fileToBytes(isdnZip);
				base64 = Base64.encodeToString(byteArray,
						Activity.TRIM_MEMORY_BACKGROUND);
				// byte bytes[] = FileUtils.fileToBytes(fileImage);

			} catch (Exception e) {
				return false;
			}

			return updateVerify(base64);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(mActivity, errorDescription,
						getString(R.string.app_name)).show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									mActivity,
									errorDescription,
									mActivity.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (errorDescription == null || errorDescription.isEmpty()) {
						errorDescription = mActivity
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), errorDescription, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private Boolean updateVerify(String base64) {
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateVerify");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateVerify>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<staffCode>TT_TESTER</staffCode>");
				// rawData.append("<staffCode>" +
				// Session.loginUser.getStaffCode() + "</staffCode>");

				rawData.append("<contractId>").append(contractId).append("</contractId>");
				rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
				rawData.append("<verifyType>").append(verifyType).append("</verifyType>");
				rawData.append("<custType>").append(custType).append("</custType>");
				rawData.append("<description>").append(note).append("</description>");
				rawData.append("<xpos>").append(xpos).append("</xpos>");
				rawData.append("<ypos>").append(ypos).append("</ypos>");

				rawData.append("<fileContent>").append(base64).append("</fileContent>");

				if (chargContractItem != null
						&& chargContractItem.getIsdn() != null) {
					rawData.append("<isdn>").append(chargContractItem.getIsdn()).append("</isdn>");
				}

				rawData.append("</input>");
				rawData.append("</ws:updateVerify>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateVerify");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					errorDescription = parse.getValue(e2, "description");
					Log.d(Constant.TAG,
							"FragmentContractVerifyUpdateDetail updateVerify errorCode: "
									+ errorCode + " errorDescription: "
									+ errorDescription);
					if ("0".equalsIgnoreCase(errorCode))
						;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG,
						"FragmentContractVerifyUpdateDetail updateVerify", e);
			}
			return false;
		}

		@Override
		public String toString() {
            return "{\"AsyncUpdateVerify\":{\"contractId\":\"" +
                    contractId + "\", \"reasonId\":\"" +
                    reasonId + "\", \"verifyType\":\"" +
                    verifyType + "\", \"custType\":\"" +
                    custType + "\", \"xpos\":\"" + xpos +
                    "\", \"ypos\":\"" + ypos +
                    "\", \"note\":\"" + note + "\"}}";
		}
	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";verify_update;";
	}
}
