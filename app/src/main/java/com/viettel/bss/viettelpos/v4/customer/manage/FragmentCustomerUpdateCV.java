package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.viettel.bss.viettelpos.v4.customer.adapter.AdapterUpdateCV;
import com.viettel.bss.viettelpos.v4.customer.adapter.AdapterUpdateCV.OnchangeImageFile;
import com.viettel.bss.viettelpos.v4.customer.adapter.AdapterUpdateCV.ViewHolder;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUploadObj;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCustomerUpdateCV extends Fragment implements OnClickListener, OnchangeImageFile {

	private Activity activity;
	private View mView;
	private ListView lv_customer_update_cv;
	private TextView txtNameActionBar;
	private Button btnHome;
	private Button btn_update;

	private ArrayList<ProfileUploadObj> listProfileUploadObj;
	private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
    private ArrayList<FileObj> arrFileUpdate;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		listProfileUploadObj = (ArrayList<ProfileUploadObj>) getArguments().getSerializable("listFileObj");
		if (listProfileUploadObj == null) {
			listProfileUploadObj = new ArrayList<>();
		} else {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_customer_update_cv, container, false);
			lv_customer_update_cv = (ListView) mView.findViewById(R.id.lv_customer_update_cv);
			btn_update = (Button) mView.findViewById(R.id.btn_update);
		}

        AdapterUpdateCV adapterUpdateCV = new AdapterUpdateCV(activity, listProfileUploadObj, imageListenner,
                FragmentCustomerUpdateCV.this);
		lv_customer_update_cv.setAdapter(adapterUpdateCV);
		btn_update.setOnClickListener(this);

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.update_cv);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(activity, Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;
		case R.id.btn_update:

			CommonActivity
					.createDialog(activity, getString(R.string.messageConfirm_update_cv), getString(R.string.app_name),
							getString(R.string.say_ko), getString(R.string.say_co), null, updateConfirmCallBack)
					.show();
			break;

		default:
			break;
		}
	}

	// confirm update
    private final OnClickListener updateConfirmCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!CommonActivity.isNetworkConnected(activity)) {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			} else {
				
			
					onUpdateCvToServer();
				
				
			}
		}
	};

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

	private void onUpdateCvToServer() {
		// update file to server
		if (arrFileUpdate != null && arrFileUpdate.size() > 0) {
			// thực hiện update
			AsyncTaskUpdateImageOfline asynTaskUploadImageOffline = new AsyncTaskUpdateImageOfline(activity,
					arrFileUpdate);
			asynTaskUploadImageOffline.execute();
		} else {
			arrFileUpdate = FileUtils.getArrFileImageUploadFail(activity, hashmapFileObj);
			if(arrFileUpdate != null && !arrFileUpdate.isEmpty()){
				// if (arrFileUpdate.size() > 0) {
				// thực hiện update
				ProfileUploadObj profileUplodObj = listProfileUploadObj.get(0);
				if (arrFileUpdate != null && arrFileUpdate.size() > 0) {
					for (FileObj fileObj : arrFileUpdate) {
						fileObj.setActionCode(profileUplodObj.getActionCode()); 
						fileObj.setReasonId(profileUplodObj.getReasonId());
						fileObj.setIsdn(profileUplodObj.getAccount());
						fileObj.setActionAudit(profileUplodObj.getActionAuditId());
						fileObj.setStatus(0);
					}
				}

				AsyncTaskUpdateImageOfline asynTaskUploadImageOffline = new AsyncTaskUpdateImageOfline(activity,
						arrFileUpdate);
				asynTaskUploadImageOffline.execute();
			}else{
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.requireProfile), getActivity().getString(R.string.app_name)).show();
			}
		
		}
	}

	

	private final OnClickListener imageListenner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(Constant.TAG, "view.getId() : " + v.getId());
			int viewTag = Integer.parseInt((String) v.getTag());
			ImagePreviewActivity.pickImage(activity, hashmapFileObj, viewTag);

		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9", "FragmentManageConnect onActivityResult requestCode : " + requestCode);
		if (resultCode == Activity.RESULT_OK) {
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

				Log.d(Constant.TAG, "FragmentManageConnect onActivityResult() imageId: " + imageId);

				if (uris != null && uris.length > 0 && imageId >= 0) {
					ViewHolder holder = null;
					for (int i = 0; i < lv_customer_update_cv.getChildCount(); i++) {
						View rowView = lv_customer_update_cv.getChildAt(i);
						ViewHolder h = (ViewHolder) rowView.getTag();
						if (h != null) {
							int id = Integer.parseInt((String) h.imgCV.getTag());
							if (imageId == id) {
								holder = h;
								break;
							}
						}
					}
					if (holder != null) {
						Picasso.with(activity).load(new File(uris[0].toString())).centerCrop().resize(100, 100)
								.into(holder.imgCV);
						ProfileUploadObj profileUploadObj = listProfileUploadObj.get(holder.position);
						Log.d(Constant.TAG, "FragmentCustomerUpdateCV onActivityResult profileUploadObj name "
								+ profileUploadObj.getRecordName());

						ArrayList<FileObj> fileObjs = new ArrayList<>();
						for (int i = 0; i < uris.length; i++) {
							File uriFile = new File(uris[i].getPath());
							String fileNameServer = profileUploadObj.getRecordCode() + "-" + (i + 1) + ".jpg";
							FileObj obj = new FileObj(profileUploadObj.getRecordCode(), uriFile, uris[i].getPath(),
									fileNameServer);
							obj.setRecodeName(profileUploadObj.getRecordName());
							fileObjs.add(obj);
						}
						hashmapFileObj.put(String.valueOf(imageId), fileObjs);
					} else {
						Log.d(Constant.TAG, "FragmentManageConnect onActivityResult() holder NULL");
					}
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onchangeImageFileZip(ArrayList<File> listFile, int position, String imageId,
			ProfileUploadObj profileUploadObj) {
		ArrayList<FileObj> fileObjs = new ArrayList<>();
		for (File fileUnzip : listFile) {
			FileObj fileObj = new FileObj(profileUploadObj.getRecordCode(), fileUnzip, fileUnzip.getPath(),
					fileUnzip.getName());
			fileObj.setRecodeName(profileUploadObj.getRecordName());
			fileObjs.add(fileObj);
		}
		hashmapFileObj.put(String.valueOf(imageId), fileObjs);
	}

	@SuppressWarnings("unused")
	private class AsyncTaskUpdateImageOfline extends AsyncTask<Void, Void, Integer> {

		private final Activity mActivity;
		private final ArrayList<FileObj> listFileUploadImage;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyncTaskUpdateImageOfline(Activity mActivity, ArrayList<FileObj> listFileUploadImage) {
			this.mActivity = mActivity;
			this.listFileUploadImage = listFileUploadImage;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.update_image_offline));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Integer doInBackground(Void... params) {
			return onProcessDataToUpload(listFileUploadImage);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (result > 0) {
				CommonActivity.createAlertDialog(mActivity, description, getString(R.string.app_name)).show();
				getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
				mActivity.onBackPressed();
			} else {
				CommonActivity.createAlertDialog(mActivity, description, mActivity.getString(R.string.app_name)).show();
			}
		}

		private int onProcessDataToUpload(ArrayList<FileObj> listFileUploadImage) {
			String messageUpload = "";
			int numberSuccess = 0;
			for (FileObj fileObj2 : listFileUploadImage) {
				File isdnZip = new File(fileObj2.getPath());
				byte[] buffer = FileUtils.fileToBytes(isdnZip);
				if (buffer != null && buffer.length > 0) {
					Log.d(Constant.TAG, "FragmentCustomerUpdateCV onProcessDataToUpload service running fileToBytes"
							+ buffer.length);
					String base64 = Base64.encodeToString(buffer, Activity.TRIM_MEMORY_BACKGROUND);
					Log.d(Constant.TAG, "FragmentCustomerUpdateCV onProcessDataToUpload service running encodeToString"
							+ buffer.length);
					Log.d(Constant.TAG,
							"FragmentCustomerUpdateCV onProcessDataToUpload Bat dau tien trinh upload co file");
					String error = doUploadImage(fileObj2, base64);
					if (error.equals("0")) { // token invalid
						messageUpload += getString(R.string.message_update_cv_success) + " "+ fileObj2.getRecodeName() + " \n";
						isdnZip.delete();
						numberSuccess++;
					} else {
						messageUpload += getString(R.string.message_update_cv_fail) + fileObj2.getRecodeName() + " "; 
					}
				} else {
					Log.d(Constant.TAG, "FragmentCustomerUpdateCV onProcessDataToUpload khong co file");
				}
			}

			description = messageUpload;
			return numberSuccess;
		}

		private String doUploadImage(FileObj fileObject, String fileContent) {
			Log.d("Log", "doUploadImage upload image to server " + fileObject.getPath() + " filecontent:  "
					+ fileContent.length() + " " + fileObject.toString());

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_uploadImageOffline");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:uploadImageOffline>");
				rawData.append("<input>");
				rawData.append("<token>").append(com.viettel.bss.viettelpos.v4.commons.Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(fileObject.getIsdn()).append("</isdn>");
				rawData.append("<recordCode>").append(fileObject.getCodeSpinner()).append("</recordCode>");
				rawData.append("<actionCode>").append(fileObject.getActionCode()).append("</actionCode>");
				rawData.append("<reasonId>").append(fileObject.getReasonId()).append("</reasonId>");
				rawData.append("<actionAudit>").append(fileObject.getActionAudit()).append("</actionAudit>");
				rawData.append("<pageIndex>").append(fileObject.getPageIndex()).append("</pageIndex>");
				rawData.append("<status>").append(fileObject.getStatus()).append("</status>");
				rawData.append("<filePath>").append(fileObject.getPath()).append("</filePath>");
				rawData.append("<fileLength>").append(fileObject.getFileLength()).append("</fileLength>");
				rawData.append("<fileLengthOrigin>").append(fileObject.getFileLengthOrigin()).append("</fileLengthOrigin>");
				rawData.append("<fileContent>").append(fileContent).append("</fileContent>");
				rawData.append("</input>");
				rawData.append("</ws:uploadImageOffline>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("uploadImageOffline Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity,
						"mbccs_uploadImageOffline");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("uploadImageOffline Responseeeeeeeeee Original ", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d(Constant.TAG, errorCode);
				}
				return errorCode;
			} catch (Exception e) {
				Log.e("Log", "Upload fail ", e);
			}
			return "1";
		}

	}

}
