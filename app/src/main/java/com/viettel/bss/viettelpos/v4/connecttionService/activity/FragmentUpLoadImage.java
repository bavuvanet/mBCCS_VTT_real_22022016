package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionRecordBean;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListRequestAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RequestBeans;
import com.viettel.bss.viettelpos.v4.customer.object.ImageObj;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FragmentUpLoadImage extends Fragment implements OnClickListener {
	private RequestBeans requestBeans = new RequestBeans();

    private EditText edtngayscan;

	// define time ngay bat dau va ngay het thuc
	private Calendar cal;
	private int day;
	private int month;
	private int year;

	private String mNgaycaidat = "";

    private Date dateBD = null;

    private final List<ImageObj> mListIvO = new ArrayList<>();
	private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
	private List<ArrayList<ActionRecordBean>> arrayOfArrayList;
	private List<Spinner> listSpinner;
	private Activity activity;
	
	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
        String dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
		try {
            Date dateNow = new Date(dateNowString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		View mView = inflater.inflate(R.layout.layout_upload_image, container,
				false);
		
		initView(mView);
		return mView;
	}

	private void initView(View mView) {
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			try {
				requestBeans = (RequestBeans) mBundle.getSerializable("RequestBeanKey");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

        TextView mLnUpImage = (TextView) mView.findViewById(R.id.tv20);
        LinearLayout mLnUploadImage = (LinearLayout) mView.findViewById(R.id.lnUploadImage);

		edtngayscan = (EditText) mView.findViewById(R.id.edtngayscan);
		edtngayscan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerDialog(edtngayscan);
			}
		});

        Button btncapnhat = (Button) mView.findViewById(R.id.btncapnhat);
        Button btnhuy = (Button) mView.findViewById(R.id.btnhuy);
		btncapnhat.setOnClickListener(this);
		btnhuy.setOnClickListener(this);

		if (CommonActivity.isNetworkConnected(activity)) {

			GetListImageProfileAsyn getListImageProfileAsyn = new GetListImageProfileAsyn(
					activity);
			getListImageProfileAsyn.execute(requestBeans.getActionProfileId());

		} else {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.ds_select_image);
	}

	private void showDatePickerDialog(final EditText edtngayscan) {
		OnDateSetListener callback = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year;
				edtngayscan.setText(mNgaycaidat);
				try {
					dateBD = new Date(mNgaycaidat);
				} catch (Exception e) {
					e.printStackTrace();
				}
				cal.set(year, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, callback,
				year, month, day);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("TAG 9", "requestCode : " + requestCode);
		if (resultCode == activity.RESULT_OK) {
			switch (requestCode) {

			case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
				Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");

				Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

				if (parcelableUris == null) {
					return;
				}
				// Java doesn't allow array casting, this is a little hack
				Uri[] uris = new Uri[parcelableUris.length];
				System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

				int imageId = data.getExtras().getInt("imageId", -1);

				if (uris != null && uris.length > 0) {
//					mListIvO.get(imageId).getImage().setImageBitmap(bitmap);
					mListIvO.get(imageId).setSetIm(true);
//					mListIvO.get(imageId).getImage().setBackgroundColor(color.white);
					
					ImageView imageView = mListIvO.get(imageId).getImage();
					Picasso.with(activity).load(new File(uris[0].toString())).centerCrop().resize(100, 100)
					.into(imageView);

					Spinner spinner = listSpinner.get(imageId);
					int position = spinner.getSelectedItemPosition();
					Log.i(Constant.TAG, "imageId: " + imageId + " spinner position: " + position);
					if (position < 0) {
						position = 0;
					}
					ActionRecordBean actionRecordBean = arrayOfArrayList.get(imageId).get(position);
					String spinnerCode = actionRecordBean.getRecordCode();
					ArrayList<FileObj> fileObjs = new ArrayList<>();

					for (int i = 0; i < uris.length; i++) {
						Log.i(Constant.TAG, "spinnerCode: " + spinnerCode + " uri: " + uris[i]);
						File uriFile = new File(uris[i].getPath());
						String fileNameServer = spinnerCode + "-" + (i + 1) + ".jpg";
						FileObj obj = new FileObj(spinnerCode, uriFile, uris[i].getPath(), fileNameServer);
						fileObjs.add(obj);
					}
					hashmapFileObj.put(String.valueOf(imageId), fileObjs);
				}
				break;

			default:
				break;
			}
		}
	}

	private final OnClickListener moveLogInAct = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(activity, LoginActivity.class);
			startActivity(intent);
			activity.finish();
			MainActivity.getInstance().finish();
		}
	};
	
	private OnClickListener imageListenner = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.d(Constant.TAG, "view.getId() : " + view.getId());
			ImagePreviewActivity.pickImage(activity, hashmapFileObj, view.getId());
		}
	};

	private class GetListImageProfileAsyn extends
			AsyncTask<String, Void, List<ArrayList<ActionRecordBean>>> {

		private Context context = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public GetListImageProfileAsyn(Context context) {
			this.context = context;
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
		protected List<ArrayList<ActionRecordBean>> doInBackground(String... arg0) {
			return getLstObjectImageProfile(arg0[0]);
		}

		@Override
		protected void onPostExecute(List<ArrayList<ActionRecordBean>> result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				arrayOfArrayList = result;
				
				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.checklayimageprofile),
							getString(R.string.app_name)).show();
				} else {
					ArrayList<ArrayList<String>> aa = new ArrayList<>();
					for (ArrayList<ActionRecordBean> arrayList : result) {
						ArrayList<String> a = new ArrayList<>();
						for (ActionRecordBean actionRecordBean : arrayList) {
							a.add(actionRecordBean.getRecordName());
						}
						aa.add(a);
					}
//					listSpinner = ImagePreviewActivity.initSpinnerUploadImage(activity, aa, mLnUploadImage, mLnUpImage, mListIvO, imageListenner);
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									activity,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							activity, description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private List<ArrayList<ActionRecordBean>> getLstObjectImageProfile (
				String actionProfileId) {
			String original = "";
			
			List<ArrayList<ActionRecordBean>> listOfArrayList = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListActionRecordByActionProfileId");
                String rawData = "<ws:getListActionRecordByActionProfileId>" +
                        "<qlhsInput>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "<actionProfileId>" + actionProfileId +
                        "</actionProfileId>" +
                        "</qlhsInput>" +
                        "</ws:getListActionRecordByActionProfileId>";
                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_getListActionRecordByActionProfileId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nlReturn = doc.getElementsByTagName("return");
				Log.i("Responseeeeeeeeee", response);
				
				for (int i = 0; i < nlReturn.getLength(); i++) {
					Element e2 = (Element) nlReturn.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					NodeList lstActionRecordResult = e2.getElementsByTagName("lstActionRecordResult");
					
					for (int j = 0; j < lstActionRecordResult.getLength(); j++) {
						Element e3 = (Element) lstActionRecordResult.item(j);
						
						ArrayList<ActionRecordBean> lsActionRecordBeans = new ArrayList<>();
						NodeList lstActionRecords = e3.getElementsByTagName("lstActionRecords");
						
						for (int k = 0; k < lstActionRecords.getLength(); k++) {
							Element e4 = (Element) lstActionRecords.item(k);
							ActionRecordBean actionRecordBean = new ActionRecordBean();
							String recordCode = parse.getValue(e4, "recordCode");
							actionRecordBean.setRecordCode(recordCode);

							String recordName = parse.getValue(e4, "recordName");
							actionRecordBean.setRecordName(recordName);

							String recordTypeId = parse
									.getValue(e4, "recordTypeId");
							actionRecordBean.setRecordTypeId(recordTypeId);

							String reqScan = parse.getValue(e4, "reqScan");
							actionRecordBean.setReqScan(reqScan);

							String sourceRecordTypeId = parse.getValue(e4, "sourceRecordTypeId");
							actionRecordBean
									.setSourceRecordTypeId(sourceRecordTypeId);

							lsActionRecordBeans.add(actionRecordBean);
						}
						listOfArrayList.add(lsActionRecordBeans);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listOfArrayList;
		}
	}

	private final OnClickListener onBackrequest = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (FragmentManageRequest.fragInstance != null) {
				if (FragmentManageRequest.fragInstance.arrRequestBeans != null
						&& !FragmentManageRequest.fragInstance.arrRequestBeans
								.isEmpty()) {
					for (RequestBeans reBeans : FragmentManageRequest.fragInstance.arrRequestBeans) {
						if (requestBeans.getIdRequest().equalsIgnoreCase(
								reBeans.getIdRequest())) {
							reBeans.setActionProfileStatus("1");
							reBeans.setActionProfileId(requestBeans
									.getActionProfileId());
							break;
						}
					}
					FragmentManageRequest.fragInstance.adaRequestAdapter = new GetListRequestAdapter(
							FragmentManageRequest.fragInstance.arrRequestBeans,
							FragmentManageRequest.fragInstance.getActivity(),
							FragmentManageRequest.fragInstance);
					FragmentManageRequest.fragInstance.lvRequest
							.setAdapter(FragmentManageRequest.fragInstance.adaRequestAdapter);
					FragmentManageRequest.fragInstance.adaRequestAdapter
							.notifyDataSetChanged();
					activity.onBackPressed();
				}
			}
		}
	};

	private class AsynUploadImageProfile extends
			AsyncTask<String, String, String> {
		private Context context = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public AsynUploadImageProfile(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			progress.setMessage(String.valueOf(values[0]));
			Log.i(Constant.TAG, "onProgressUpdate(): " + String.valueOf(values[0]));
		}

		@Override
		protected String doInBackground(String... arg0) {
			String base64 = "";
			File folder = new File(Constant.MBCCS_TEMP_FOLDER);
			if (!folder.exists()) {
				folder.mkdir();
			}
			String fileZipPath = Constant.MBCCS_TEMP_FOLDER + File.separator + "QLHS.zip";
			
			publishProgress(context.getResources().getString(R.string.progress_zip_image));
			base64 = FileUtils.zipFileToBase64(activity, hashmapFileObj, fileZipPath);
			
			Log.d(Constant.TAG, "base64 length: " + base64.length());
			
			publishProgress(context.getResources().getString(R.string.progress_upload));
			return uploadImageProfile(arg0[0], arg0[1], base64);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();

			if ("0".equalsIgnoreCase(errorCode)) {

				CommonActivity.createAlertDialog(activity,
						getString(R.string.capnhathososucces),
						getString(R.string.app_name), onBackrequest).show();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									activity,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null && description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							activity, description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private String uploadImageProfile(String actionProfileId, String startDateScan, String base64) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateActionProfile");
                String rawData = "<ws:updateActionProfile>" +
                        "<qlhsInput>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "<fileContent>" +
                        base64 +
                        "</fileContent>" +
                        "<actionProfileId>" + actionProfileId +
                        "</actionProfileId>" +
                        "<scanDatetime>" + startDateScan +
                        "</scanDatetime>" +
                        "</qlhsInput>" +
                        "</ws:updateActionProfile>";

                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_updateActionProfile");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
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
				e.printStackTrace();
			}
			return errorCode;
		}
	}
	
	private boolean isUploadImage(List<Spinner> listSpinner) {
		boolean isUploadImage = true;
		for (Spinner spinner : listSpinner) {
			int imageId = spinner.getId();
			int position = spinner.getSelectedItemPosition();
			Log.i(Constant.TAG, "imageId: " + imageId + " spinner position: " + position);
			if (position < 0) {
				position = 0;
			}
			ActionRecordBean actionRecordBean = arrayOfArrayList.get(imageId).get(position);
			
			ArrayList<FileObj> fileObjs = hashmapFileObj.get(String.valueOf(imageId));
			
			if("1".equalsIgnoreCase(actionRecordBean.getReqScan()) 
					&& (fileObjs == null || fileObjs.isEmpty()) ) {
				isUploadImage = false;
				break;
			}
		}
		return isUploadImage;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btncapnhat:
			if (CommonActivity.isNetworkConnected(activity)) {
				if (isUploadImage(listSpinner)) {
					CommonActivity.createDialog(activity,
							getString(R.string.confirmcapnhat),
							getString(R.string.app_name),
							getString(R.string.ok), getString(R.string.cancel),
							confirmupLoadClick, null).show();
				} else {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.checkthieuanhhoso),
							getString(R.string.app_name)).show();
				}
			}
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;
		case R.id.btnhuy:
			activity.onBackPressed();
			break;
		default:
			break;
		}
	}

	private final OnClickListener confirmupLoadClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Log.e(Constant.TAG, "confirmupLoadClick");
			AsynUploadImageProfile asynUploadImageProfile = new AsynUploadImageProfile(
					activity);
			asynUploadImageProfile.execute(requestBeans.getActionProfileId(), edtngayscan.getText().toString().trim());
		}
	};
}
