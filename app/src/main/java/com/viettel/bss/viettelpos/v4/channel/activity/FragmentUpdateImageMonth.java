package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterUpdateImageMonth;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterUpdateImageMonth.OnCancelSignImageMonth;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterUpdateImageMonth.OnChangeInfoSignImageMonth;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterUpdateImageMonth.OnchangeSignImageMonth;
import com.viettel.bss.viettelpos.v4.channel.object.AppParam;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectCatBO;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectTypeBO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OkHttpUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.savelog.SaveLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentUpdateImageMonth extends Fragment implements
		OnClickListener, OnChangeInfoSignImageMonth, OnchangeSignImageMonth,
		OnCancelSignImageMonth {

	private Button btnHome;
	private TextView txtNameActionBar;
	private ExpandableListView lvdanhmuc;
    private ArrayList<ObjectTypeBO> lstObjectTypeBOs = new ArrayList<>();

	private AdapterUpdateImageMonth adapterUpdateImageMonth;

	private Uri mCaptureNewUri;
	private final ArrayList<ObjectCatBO> arrCatBOsAll = new ArrayList<>();
	// khai bao doi tuong objectcat va grouppositio
	private ObjectCatBO objectCatBOIntansce = null;
	private int groupPositionIntansce = -1;
	private int childPossitionIntance = -1;

	private String mPathFileZ = "";

	private final ArrayList<ObjectCatBO> mArraCatBOs = new ArrayList<>();
	private ArrayList<AppParam> mAppParams = new ArrayList<>();

	private String staffId;
    private final HashMap<String, ObjectCatBO> mapObjecMap = new HashMap<>();

	private int dem = 0;
	private com.viettel.bss.viettelpos.v4.object.Location myLocation;

	private Date timeStart = null;

    private Date timeStartZip = null;
	private Date timeEndZip = null;

	private boolean isCheckOffline = false;

	public static FragmentUpdateImageMonth create(String key) {
		Bundle args = new Bundle();
		args.putString(Define.ARG_KEY, key);
		args.putSerializable("staffIdKey", FragmentCusCareByDay.staff);

		FragmentUpdateImageMonth fragment = new FragmentUpdateImageMonth();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		myLocation = CommonActivity.findMyLocationGPS(
				MainActivity.getInstance(), "FragmentUpdateImageMonth");

		View mView = inflater.inflate(R.layout.layout_update_image_month,
				container, false);
		unitView(mView);
		return mView;
	}

	private void unitView(View mView) {
		lvdanhmuc = (ExpandableListView) mView
				.findViewById(R.id.lvdsdbUpdateImageMonth);
        Button btncapnhat = (Button) mView.findViewById(R.id.btncapnhatmonth);
		btncapnhat.setOnClickListener(this);
        Button btnhuy = (Button) mView.findViewById(R.id.btnhuymonth);
		btnhuy.setOnClickListener(this);

		if (getActivity() instanceof FragmentCusCareByDay) {
			btnhuy.setVisibility(View.GONE);
		}

		// truong hop cham soc diem ban
		if (getActivity() instanceof FragmentCusCareByDay) {
			mView.findViewById(R.id.llHeaderUIM)
					.setVisibility(View.VISIBLE);
		}

		Bundle bundle = getArguments();
		try {
            Staff mstaff = (Staff) bundle.getSerializable("staffIdKey");
			if (mstaff != null) {
				staffId = "" + mstaff.getStaffId();
				Log.d("staff iDDDDDDDDD", staffId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (CommonActivity.isNetworkConnected(getActivity())) {
			GetListSignImageMonthAsyn getListSignImageAsyn = new GetListSignImageMonthAsyn(
					getActivity());
			getListSignImageAsyn.execute();
			// lay danh sach tinh trang bien hieu
			GetLstStatusAsyn getLstStatusAsyn = new GetLstStatusAsyn(
					getActivity());
			getLstStatusAsyn.execute();
		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getActivity() instanceof FragmentCusCareByDay) {
			Log.d(this.getClass().getSimpleName(),
					"getActivity() is instanceof FragmentCusCareByDay");
		} else {
			MainActivity.getInstance().setTitleActionBar(R.string.capnhathinhanhhangthang);
		}
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9", "requestCode : " + requestCode);
		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {
			case 0:
				break;
			case 1010:
				// lay ra uri
				Uri selectedImageUri = null;
				if (data != null) {
					// Bundle bundle = data.getExtras();
					selectedImageUri = data.getData();
				}

				String path = getPath(selectedImageUri, getActivity());
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(path, btmapOptions);
				// ivImage.setImageBitmap(bm);

				String currentFileName = path.substring(0,
						path.lastIndexOf("/"));

				// lay ra name file
				String nameFile = path.substring(path.lastIndexOf("/"),
						path.length());

				// lay ra file tu path
				File finalFile = new File(path);

				for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(
						groupPositionIntansce).getArrObjectCatBOs()) {
					if (obCatBO.getId().equalsIgnoreCase(
							objectCatBOIntansce.getId())) {
						obCatBO.setFile(finalFile);
						obCatBO.setImageLink(path);
						obCatBO.setImageName(nameFile);

						break;
					}
				}
				MediaScannerConnection.scanFile(getActivity(),
						new String[] { currentFileName },
						new String[] { "image/jpeg" }, null);

				cropImageFromUri(path);

				break;
			// case CHANNEL_UPDATE_IMAGE.CROP_PIC:
			//
			// cropImage(data);
			// break;
			case CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA:

				cropImageFromUri(filePath);
				// performCrop_(mCaptureNewUri);
				break;
			default:
				break;
			}
		}
	}

	private File bitmap2File(Bitmap bitmap, String idChanel) {

		// ExternalStorageDirectory()
		File pathEV = new File(Constant.MBCCS_TEMP_FOLDER + "/VT_UploadImage");

		// File pathEV =
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		String path = pathEV + "/" + idChanel + ".jpg";
		File f = new File(path);

		// neu file ton tai thi xoa di
		if (f.exists()) {
			f.delete();
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 60 /* ignored for PNG */,
				bos);
		byte[] bitmapdata = bos.toByteArray();

		// write the bytes in file
		FileOutputStream fos;
		try {
			f.getParentFile().mkdirs();
			f.createNewFile();
			fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// copyFile(f);

        for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(groupPositionIntansce)
				.getArrObjectCatBOs()) {
			if (obCatBO.getId().equalsIgnoreCase(objectCatBOIntansce.getId())) {
				obCatBO.setImageName(idChanel);
				break;
			}
		}

		return f;
	}

	private Bitmap decodeFile(String path) {
		try {
			File f = new File(path);

			// Decode image size
			// BitmapFactory.Options o = new BitmapFactory.Options();
			// o.inJustDecodeBounds = true;
			// BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			//
			// // The new size we want to scale to
			// final int REQUIRED_SIZE = Constant.REQUEST_MAX_SIZE_IMAGE;
			//
			// // Find the correct scale value. It should be the power of 2.
			// int scale = 1;
			// while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight /
			// scale / 2 >= REQUIRED_SIZE) {
			// scale *= 2;
			// }
			//
			// // Decode with inSampleSize
			// BitmapFactory.Options o2 = new BitmapFactory.Options();
			// o2.inSampleSize = scale;
			Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());
			Bitmap bitmapImage = null;
			try {
				bitmapImage = CommonActivity.getResizedBitmap(bitmap,
						Constant.SIZE_IMAGE_SCALE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmapImage;
		} catch (Exception e) {
			Log.d("exxxxxx", e.toString());
		}
		return null;
	}

	private void cropImageFromUri(String pathData) {

		if (pathData != null) {
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 8;
			// Bitmap bmpResize1 = BitmapFactory
			// .decodeFile(pathData, btmapOptions);

			// Bitmap bmpResize = Bitmap.createScaledBitmap(bmpResize1,
			// (int)(bmpResize1.getWidth()*0.8),
			// (int)(bmpResize1.getWidth()*0.8), true);
			Bitmap bmpResize = decodeFile(pathData);

			if (bmpResize != null) {
				File file = bitmap2File(bmpResize, objectCatBOIntansce.getId());

				for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(
						groupPositionIntansce).getArrObjectCatBOs()) {
					if (obCatBO.getId().equalsIgnoreCase(
							objectCatBOIntansce.getId())) {
						obCatBO.setBmpImage(bmpResize);
						obCatBO.setFile(file);
						break;
					}
				}

				if (adapterUpdateImageMonth != null) {
					adapterUpdateImageMonth.notifyDataSetChanged();
				}

			}
		} else {

		}
	}

	// crop image
	private void cropImage(Intent data) {
		Bundle extrasCrop = null;
		if (data != null) {
			extrasCrop = data.getExtras();
		}

		if (extrasCrop != null) {
			Bitmap bitmap = extrasCrop.getParcelable("data");
			File file = bitmap2File(bitmap, objectCatBOIntansce.getId());

			for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(
					groupPositionIntansce).getArrObjectCatBOs()) {
				if (obCatBO.getId().equalsIgnoreCase(
						objectCatBOIntansce.getId())) {
					obCatBO.setBmpImage(bitmap);
					obCatBO.setFile(file);
					break;
				}
			}

			if (adapterUpdateImageMonth != null) {
				adapterUpdateImageMonth.notifyDataSetChanged();
			}
		} else {

		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// MainActivity.getInstance().finish();
			LoginDialog dialog = new LoginDialog(getActivity(),
					";channel.management;");
			dialog.show();
		}
	};

	private class GetLstStatusAsyn extends
			AsyncTask<Void, Void, ArrayList<AppParam>> {

		// ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetLstStatusAsyn(Context context) {
			this.context = context;
		}

		@Override
		protected ArrayList<AppParam> doInBackground(Void... arg0) {
			return getLstStatus();
		}

		@Override
		protected void onPostExecute(ArrayList<AppParam> result) {
			if (errorCode.equals("0")) {
				if (result != null && !result.isEmpty()) {
					mAppParams = result;
				} else {
					CommonActivity
							.createAlertDialog(
									getActivity(),
									context.getResources().getString(
											R.string.checknottinhtrang),
									context.getResources().getString(
											R.string.app_name)).show();
				}
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
							getActivity(), description, context.getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<AppParam> getLstStatus() {
			String original = "";
			ArrayList<AppParam> lsAppParams = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getStatusOfObject");
                String rawData = "<ws:getStatusOfObject>" +
                        "<input>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "</input>" +
                        "</ws:getStatusOfObject>";

                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getStatusOfObject");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = e2.getElementsByTagName("listAppParams");
					for (int k = 0; k < nodechild.getLength(); k++) {
						Element e1 = (Element) nodechild.item(k);
						AppParam appParam = new AppParam();
						String name = parse.getValue(e1, "name");
						appParam.setName(name);
						String value = parse.getValue(e1, "value");
						appParam.setValue(value);

						lsAppParams.add(appParam);
					}
				}

			} catch (Exception ignored) {

			}
			return lsAppParams;
		}

	}

	private class GetListSignImageMonthAsyn extends
			AsyncTask<String, Void, ArrayList<ObjectTypeBO>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListSignImageMonthAsyn(Context context) {
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
		protected ArrayList<ObjectTypeBO> doInBackground(String... arg0) {
			return getLstObjectType();
		}

		@Override
		protected void onPostExecute(ArrayList<ObjectTypeBO> result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				if (result != null && !result.isEmpty()) {
					lstObjectTypeBOs = result;

					adapterUpdateImageMonth = new AdapterUpdateImageMonth(
							getActivity(), lstObjectTypeBOs,
							FragmentUpdateImageMonth.this,
							FragmentUpdateImageMonth.this,
							FragmentUpdateImageMonth.this);

					lvdanhmuc.setAdapter(adapterUpdateImageMonth);
				} else {

					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checklaydanhmuc),
							getString(R.string.app_name)).show();
				}
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

		private ArrayList<ObjectTypeBO> getLstObjectType() {
			ArrayList<ObjectTypeBO> arrObjectTypeBOs = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListBoardMonthlyByStaffIdNoImage");
                String rawData = "<ws:getListBoardMonthlyByStaffIdNoImage>" +
                        "<input>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "<staffId>" + staffId +
                        "</staffId>" +
                        "</input>" +
                        "</ws:getListBoardMonthlyByStaffIdNoImage>";
                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListBoardMonthlyByStaffIdNoImage");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildObjCat = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					if (parse.getValue(e2, "isCheckOffline") != null) {
						isCheckOffline = Boolean.parseBoolean(parse.getValue(
								e2, "isCheckOffline"));
					}
					nodechild = e2.getElementsByTagName("lstObjTypeBO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e3 = (Element) nodechild.item(j);
						ObjectTypeBO objectTypeBO = new ObjectTypeBO();
						ArrayList<ObjectCatBO> arrObjectCatBOs = new ArrayList<>();
						nodechildObjCat = e3
								.getElementsByTagName("listObjCatBO");

						for (int k = 0; k < nodechildObjCat.getLength(); k++) {

							Element e1 = (Element) nodechildObjCat.item(k);
							ObjectCatBO objectCatBO = new ObjectCatBO();
							String requestType = parse.getValue(e1,
									"requestType");
							objectCatBO.setRequestType(requestType);
							String broadTypeName = parse.getValue(e1,
									"broadTypeName");
							objectCatBO.setBroadTypeName(broadTypeName);
							String broadTypeValue = parse.getValue(e1,
									"broadTypeValue");
							objectCatBO.setBroadTypeValue(broadTypeValue);
							String code = parse.getValue(e1, "code");
							objectCatBO.setCode(code);
							String haveImage = parse.getValue(e1, "haveImage");
							objectCatBO.setHaveImage(haveImage);
							String id = parse.getValue(e1, "id");
							objectCatBO.setId(id);
							String name = parse.getValue(e1, "name");
							objectCatBO.setName(name);
							String manageAssetId = parse.getValue(e1,
									"manageAssetId");
							objectCatBO.setManageAssetId(manageAssetId);
							arrObjectCatBOs.add(objectCatBO);
						}

						// add list expad
						if (arrObjectCatBOs != null
								&& !arrObjectCatBOs.isEmpty()) {
							objectTypeBO.setArrObjectCatBOs(arrObjectCatBOs);
							objectTypeBO.setBroadTypeName(arrObjectCatBOs
									.get(0).getBroadTypeName());
							objectTypeBO.setBroadTypeValue(arrObjectCatBOs.get(
									0).getBroadTypeValue());
							arrObjectTypeBOs.add(objectTypeBO);
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrObjectTypeBOs;
		}

	}

	// upload file
//	ContentBody cbFile = null;

	private String uploadFile(File file) {

		try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("token", Session.getToken())
                    .addFormDataPart("file", "image.png", RequestBody.create(MediaType.parse("image/png"), file))
                    .addFormDataPart("uploadType", "2")
                    .build();


            Request request = new Request.Builder()
                    .url(Constant.LINK_WS_UPLOAD_IMAGE)
                    .post(requestBody)
                    .build();

            Response response = OkHttpUtils.getClient().newCall(request).execute();
            String result = response.body().string();
            Log.d("FragmentUpdateImageMonth", "response = " + result);
//
//			HttpClient httpclient = new DefaultHttpClient();
//			httpclient.getParams().setParameter(
//					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//			HttpPost httppost = new HttpPost(Constant.LINK_WS_UPLOAD_IMAGE);
//
//			MultipartEntity mpEntity = new MultipartEntity();
//			ContentBody token = new StringBody(Session.getToken());
//			ContentBody uploadType = new StringBody("2");
//			if (file != null) {
//				cbFile = new FileBody(file);
//			}
//			Log.i("10", "call upload file");
//			mpEntity.addPart("token", token);
//			mpEntity.addPart("file", cbFile);
//			mpEntity.addPart("uploadType", uploadType);
//
//			httppost.setEntity(mpEntity);
//
//			System.out
//					.println("executing request " + httppost.getRequestLine());
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//
//			System.out.println(response.getStatusLine());
//
//			String result = EntityUtils.toString(resEntity);
//			System.out.println(result);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.ERROR_CODE;
		}
	}

	private class AsynUpLoadImageMonth extends AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		File fileZip = null;
		String isdn = "";

		public AsynUpLoadImageMonth(Context context) {
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
		protected String doInBackground(String... arg0) {
			File folder = new File(Constant.MBCCS_TEMP_FOLDER);
			if (!folder.exists()) {
				folder.mkdir();
			}
			mPathFileZ = Constant.MBCCS_TEMP_FOLDER + "BSS_UploadImage"
					+ System.currentTimeMillis() + "_" + Constant.CNCCDCHT
					+ ".zip";// +

			fileZip = zipFileImage(mArraCatBOs, mPathFileZ);

			String base64 = "";
			if (!isCheckOffline) {
				byte[] buffer = FileUtils.fileToBytes(fileZip);
				// xoa file zip khi chuyen qua buffer
				// isdnZip.delete();
				if (buffer != null && buffer.length > 0) {
					base64 = Base64.encodeToString(buffer,
							Activity.TRIM_MEMORY_BACKGROUND);
					Log.d(Constant.TAG,
							"UploadImageIntentService onHandleIntent service running encodeToString"
									+ buffer.length);
				}
			}

			return uploadImage(base64);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();

			try {
				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
						Session.userName, "mbccs_updateListImageMonthlyOfSale",
						CommonActivity.findMyLocation(context).getX(),
						CommonActivity.findMyLocation(context).getY());
                Date timeEnd = new Date();

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

			if (errorCode.equalsIgnoreCase("0")) {
				FragmentCusCareByDay.updateImage = true;

				OnClickListener backClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// refresh lai man hinh

						if (lstObjectTypeBOs != null
								&& !lstObjectTypeBOs.isEmpty()) {
							lstObjectTypeBOs.clear();
						}

						GetListSignImageMonthAsyn getListSignImageAsyn = new GetListSignImageMonthAsyn(
								getActivity());
						getListSignImageAsyn.execute();

						if (mArraCatBOs != null && mArraCatBOs.size() > 0) {
							mArraCatBOs.clear();
						}

						if (adapterUpdateImageMonth != null) {
							adapterUpdateImageMonth.notifyDataSetChanged();
						}

						dem = 0;

					}
				};
				// them vao db
				if (isCheckOffline) {
					FileObj fileObj = new FileObj();
					fileObj.setActionCode("10");
					fileObj.setReasonId(1234 + "");
					fileObj.setIsdn(isdn);
					fileObj.setActionAudit(staffId);
					fileObj.setPageSize(0 + "");
					fileObj.setStatus(0);
					fileObj.setRecodeName(getActivity().getResources()
							.getString(R.string.capnhathinhanhhangthang));
					fileObj.setCodeSpinner(Constant.CNCCDCHT);
					fileObj.setPath(mPathFileZ);

					FileUtils
							.insertFileBackUpToDataBase(fileObj, getActivity());
				}

				int sum = dem + mArraCatBOs.size();
				String des = getString(R.string.capnhatthanhcong) + " "
						+ mArraCatBOs.size() + "/" + sum + " "
						+ getString(R.string.danhmuc);

				CommonActivity.createAlertDialog(getActivity(), des,
						getString(R.string.app_name), backClick).show();
			} else {
				if (fileZip != null) {
					fileZip.delete();
				}

				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null && description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private String uploadImage(String fileContent) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateListImageMonthlyOfSale");
				StringBuilder rawData = new StringBuilder();
				HashMap<String, String> param = new HashMap<>();
				rawData.append("<ws:updateListImageMonthlyOfSale>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				isdn = System.currentTimeMillis() + "";
				rawData.append("<isdn>").append(isdn);
				rawData.append("</isdn>");
				rawData.append("<staffId>").append(staffId);
				rawData.append("</staffId>");

				if (!CommonActivity.isNullOrEmpty(fileContent)
						&& !isCheckOffline) {
					rawData.append("<fileContent>");
					rawData.append(fileContent);
					rawData.append("</fileContent>");
				} else {
					rawData.append("<lstFilePath>");
					rawData.append(mPathFileZ);
					rawData.append("</lstFilePath>");
					rawData.append("<lstRecordName>");
					rawData.append(getActivity().getResources().getString(
							R.string.capnhathinhanhhangthang));
					rawData.append("</lstRecordName>");
				}

				if (myLocation != null) {
					rawData.append("<xUpdate>").append(myLocation.getX());
					rawData.append("</xUpdate>");
					rawData.append("<yUpdate>").append(myLocation.getY());
					rawData.append("</yUpdate>");
				}
				for (ObjectCatBO item : mArraCatBOs) {
					HashMap<String, String> rawDataItem = new HashMap<>();
					rawDataItem.put("id", item.getId());
					rawDataItem.put("code", item.getCode());
					rawDataItem.put("requestType", item.getRequestType());
					rawDataItem.put("statusObj", item.getStatusObj());
					if (!CommonActivity.isNullOrEmpty(item.getReason())) {
						rawDataItem.put("reason", item.getReason());
					}
					rawDataItem.put("imageName", item.getImageName());
					if (!CommonActivity.isNullOrEmpty(item.getManageAssetId())) {
						rawDataItem.put("manageAssetId",
								item.getManageAssetId());
					}
					param.put("listObjCatBO", input.buildXML(rawDataItem));
					rawData.append(input.buildXML(param));
				}
				rawData.append("</input>");
				rawData.append("</ws:updateListImageMonthlyOfSale>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"pmbccs_updateListImageMonthlyOfSale");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// =======parse xml =================
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

	private final OnClickListener onclickConfirmUpdate = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			timeStart = new Date();
			AsynUpLoadImageMonth asynUpLoadImage = new AsynUpLoadImageMonth(
					getActivity());
			asynUpLoadImage.execute();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btncapnhatmonth:

			dem = 0;
			if (mArraCatBOs != null && mArraCatBOs.size() > 0) {
				mArraCatBOs.clear();
			}

			if (arrCatBOsAll != null && !arrCatBOsAll.isEmpty()) {
				arrCatBOsAll.clear();
			}

			if (mapObjecMap != null && !mapObjecMap.isEmpty()) {
				mapObjecMap.clear();
			}

			for (ObjectTypeBO objectTypeBO : lstObjectTypeBOs) {
				arrCatBOsAll.addAll(objectTypeBO.getArrObjectCatBOs());
			}

			if (groupPositionIntansce > -1) {
				for (ObjectCatBO objectCatBO : arrCatBOsAll) {
					if (objectCatBO.getBmpImage() != null
							&& !CommonActivity.isNullOrEmpty(objectCatBO
									.getStatusObj())) {
						mapObjecMap.put(objectCatBO.getId(), objectCatBO);
					} else {
						if (!CommonActivity.isNullOrEmpty(objectCatBO
								.getStatusObj())) {
							dem++;
						}
					}
				}
			}

			if (mapObjecMap != null && !mapObjecMap.isEmpty()) {
				mArraCatBOs.addAll(mapObjecMap.values());
			}

			// if (!myLocation.getX().equals("0")
			// && !myLocation.getY().equals("0")) {
			// Location location = new Location("");
			// location.setLatitude(Double.parseDouble(myLocation.getX()));
			// location.setLongitude(Double.parseDouble(myLocation.getY()));
			// Location locationStaff = new Location("Location Staff");
			//
			//
			// if (mstaff.getX() != 0 && mstaff.getY() != 0) {
			//
			// locationStaff.setLatitude(mstaff.getX());
			// locationStaff.setLongitude(mstaff.getY());
			//
			// float distance = location.distanceTo(locationStaff);
			//
			// if(distance <= Constant.DISTANCE_VALID){

			if (mArraCatBOs != null && mArraCatBOs.size() > 0) {
				Log.d("size mArraCatBOs.size", "" + mArraCatBOs.size());

				if (dem > 0) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkupdateimage),
							getString(R.string.app_name)).show();
				} else {
					CommonActivity.createDialog(getActivity(),
							getString(R.string.confirmmesage),
							getString(R.string.app_name),
							getString(R.string.cancel), getString(R.string.ok),
							null, onclickConfirmUpdate).show();
				}

			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkitnhat1dscapnhat),
						getString(R.string.app_name)).show();

			}

			// }else{
			// String smsDialog = getResources().getString(
			// R.string.distance_not_valid);
			//
			// String title = getString(R.string.app_name);
			// Dialog dialog = CommonActivity.createAlertDialog(
			// getActivity(), smsDialog, title);
			// dialog.show();
			// }
			// }else{
			//
			// String smsDialog = getResources().getString(
			// R.string.staff_have_not_locatation);
			// String title = getString(R.string.app_name);
			// Dialog dialog = CommonActivity.createAlertDialog(
			// getActivity(), smsDialog, title);
			// dialog.show();
			//
			// }
			// }else{
			//
			// CommonActivity.DoNotLocation(getActivity());
			// }
			break;
		case R.id.btnhuymonth:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}

	}

	private AlertDialog alert;

	private void dialogUploadImage(final ObjectCatBO objectCatBO,
                                   final int groupPosition, final int childPosition) {
		final CharSequence[] items = { getString(R.string.new_upload) };// getString(R.string.browser)

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getString(R.string.choose_type_upload));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {
				// Do something with the selection
				switch (position) {
				case 0:
					// chup anh moi
					takeCapture(objectCatBO, groupPosition, childPosition);
					break;
				// case 1:
				// // chon anh da co
				// selectFromGallery(objectCatBO, groupPosition, childPosition);
				// break;

				default:
					break;
				}
				alert.dismiss();
			}
		});
		alert = builder.create();
		alert.show();
	}

	private void selectFromGallery(ObjectCatBO objectCatBO, int groupPosition,
			int childPosition) {

		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				1010);

	}

	private String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private File zipFileImage(List<ObjectCatBO> _files, String zipFileName) {
		File zipfile = new File(zipFileName);
		// FileOutputStream dest = new FileOutputStream(zipFileName);

		try {
			BufferedInputStream origin = null;
			// ZipOutputStream out = new ZipOutputStream(new
			// BufferedOutputStream(
			// dest));
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			byte data[] = new byte[1024];

			for (int i = 0; i < _files.size(); i++) {
				Log.v("Compress", "Adding: " + _files.get(i));
				String path = _files.get(i).getFile().getPath();
				FileInputStream fi = new FileInputStream(path);
				origin = new BufferedInputStream(fi, 1024);

				ZipEntry entry = new ZipEntry(path.substring(path
						.lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;

				while ((count = origin.read(data, 0, 1024)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

			out.close();
			return zipfile;

		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}

	private void performCrop_(Uri picUri) {
		// take care of exceptions
		try {
			// call the standard crop action intent (the user device may not
			// support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", Constant.MAX_SIZE_CROP_IMG);
			cropIntent.putExtra("outputY", Constant.MAX_SIZE_CROP_IMG);
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, CHANNEL_UPDATE_IMAGE.CROP_PIC);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			Toast toast = Toast.makeText(getActivity(),
					"This device doesn't support the crop action!",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	// take picture
	private String filePath = "";
	private void takeCapture(ObjectCatBO objectCatBO, int groupPosition,
							 int childPosition) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MBBCSCameraApp");

		if(!mediaStorageDir.exists()){
			mediaStorageDir.mkdirs();
		}


		filePath = mediaStorageDir.getPath() + "/"
				+ objectCatBO.getId() + ".jpg";
		File newfile = new File(filePath);
		if (newfile.exists()) {

			// xoa anh cu
			newfile.delete();
			try {
				newfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// dat ten khac
		} else {
			try {
				newfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		mCaptureNewUri = Uri.fromFile(newfile);
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			mCaptureNewUri = Uri.fromFile(newfile);
		} else {
			mCaptureNewUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".fileprovider", newfile);
		}

		// put vao extras
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mCaptureNewUri);
		try {
			intent.putExtra("return-data", true);

			startActivityForResult(intent,
					CHANNEL_UPDATE_IMAGE.PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getRealPathFromURI(Uri uri) {
		Cursor cursor = getActivity().getContentResolver().query(uri, null,
				null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	@Override
	public void onCancelSignImageMonth(ObjectCatBO objectCatBO,
			int groupPosition, int childPosition) {
		objectCatBOIntansce = objectCatBO;
		groupPositionIntansce = groupPosition;
		childPossitionIntance = childPosition;

		for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(groupPosition)
				.getArrObjectCatBOs()) {
			if (obCatBO.getId().equalsIgnoreCase(objectCatBO.getId())) {

				obCatBO.setStatusObj("");
				obCatBO.setReason("");
				// set bitmap = null ; set lai link image
				obCatBO.setBmpImage(null);
				obCatBO.setImageLink("");
				obCatBO.setImageName("");
				obCatBO.setFile(null);
			}
		}

		if (adapterUpdateImageMonth != null) {
			adapterUpdateImageMonth.notifyDataSetChanged();
		}

	}

	@Override
	public void onChangeSignImageMonth(ObjectCatBO objectCatBO,
			int groupPosition, int childPosition) {
		objectCatBOIntansce = objectCatBO;
		groupPositionIntansce = groupPosition;
		childPossitionIntance = childPosition;

		dialogUploadImage(objectCatBOIntansce, groupPositionIntansce,
				childPossitionIntance);

	}

	@Override
	public void onChangeInfoSignImageMonth(ObjectCatBO objectCatBO,
			int groupPosition, int childPosition) {

		objectCatBOIntansce = objectCatBO;
		groupPositionIntansce = groupPosition;
		childPossitionIntance = childPosition;

		if (mAppParams != null && !mAppParams.isEmpty()) {
			showPopupChangeInfoSignImageMonth(objectCatBOIntansce,
					groupPositionIntansce, childPossitionIntance);
		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checknottinhtrang),
					getString(R.string.app_name)).show();
		}

	}

	private String statusObj = "";

	private void showPopupChangeInfoSignImageMonth(
			final ObjectCatBO objectCatBO, final int groupPosition,
			final int childPosition) {
		objectCatBOIntansce = objectCatBO;
		groupPositionIntansce = groupPosition;
		childPossitionIntance = childPosition;

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_popup_update_month);
		TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);
		txttitle.setText(objectCatBO.getBroadTypeName());

		final LinearLayout lnlido = (LinearLayout) dialog
				.findViewById(R.id.lnlido);

		final EditText edtlido = (EditText) dialog.findViewById(R.id.edtlido);

		Spinner spintintrang = (Spinner) dialog
				.findViewById(R.id.spinner_tinhtrangbienhieu);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		for (AppParam appParam : mAppParams) {
			adapter.add(appParam.getName());
		}

		spintintrang.setAdapter(adapter);

		if (!CommonActivity.isNullOrEmpty(objectCatBO.getStatusObj())) {
			for (int i = 0; i < mAppParams.size(); i++) {
				if (objectCatBO.getStatusObj().equalsIgnoreCase(
						mAppParams.get(i).getValue())) {
					spintintrang.setSelection(mAppParams.indexOf(mAppParams
							.get(i)));
					break;
				}
			}

		}
		if (!CommonActivity.isNullOrEmpty(objectCatBO.getReason())) {
			edtlido.setText(objectCatBO.getReason());
		}

		spintintrang.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				statusObj = mAppParams.get(arg2).getValue();

				if ("1".equalsIgnoreCase(statusObj)) {
					lnlido.setVisibility(View.GONE);
					// edtlido.setText("");
				} else {
					lnlido.setVisibility(View.VISIBLE);
					// edtlido.setText("");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		Button btnok = (Button) dialog.findViewById(R.id.btncapnhat);
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("1".equalsIgnoreCase(statusObj)) {
					for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(
							groupPosition).getArrObjectCatBOs()) {
						if (obCatBO.getId().equalsIgnoreCase(
								objectCatBO.getId())) {
							obCatBO.setStatusObj(statusObj);
						}
					}

					if (adapterUpdateImageMonth != null) {
						adapterUpdateImageMonth.notifyDataSetChanged();
					}

				} else {
					if (edtlido.getText() != null
							&& !edtlido.getText().toString().isEmpty()) {
						for (ObjectCatBO obCatBO : lstObjectTypeBOs.get(
								groupPosition).getArrObjectCatBOs()) {
							if (obCatBO.getId().equalsIgnoreCase(
									objectCatBO.getId())) {
								obCatBO.setStatusObj(statusObj);
								obCatBO.setReason(edtlido.getText().toString()
										.trim());
							}
						}

						if (adapterUpdateImageMonth != null) {
							adapterUpdateImageMonth.notifyDataSetChanged();
						}

					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checklydo),
								getString(R.string.app_name)).show();
					}
				}

				dialog.dismiss();

			}
		});
		Button btncanel = (Button) dialog.findViewById(R.id.btnhuy);
		btncanel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}

}
