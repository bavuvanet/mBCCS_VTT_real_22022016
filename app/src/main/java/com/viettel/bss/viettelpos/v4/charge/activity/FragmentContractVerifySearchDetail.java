package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.ContractVerifyAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.ZipUtils;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.services.RoutingOptions;
import com.viettel.maps.services.RoutingRenderer;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FragmentContractVerifySearchDetail extends FragmentCommon {

	private Activity activity;

    private ListView lvListCustomer;
    private ArrayList<ChargeContractItem> arrChargeItemObjectDels = new ArrayList<>();
	private ChargeContractItem chargContractItem;

	private MapView mapview;

	private Double lat = 0.0D;
	private Double lon = 0.0D;

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.contract_verify_search);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		idLayout = R.layout.layout_contract_verify_serach_detail;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		Bundle bundle = getArguments();
		if (bundle != null) {
			chargContractItem = (ChargeContractItem) bundle.getSerializable("chargContractItem");
			Log.d(Constant.TAG, "FragmentContractVerifySearchDetail bundle " + chargContractItem.toString());
		} else {
			Log.d(Constant.TAG, "FragmentContractVerifySearchDetail bundle NULL");
		}
	}

	@Override
	public void unit(View v) {
        TextView tvISDN = (TextView) v.findViewById(R.id.tvISDN);
		tvISDN.setText(chargContractItem.getContractNo());

        ImageButton ibImageContract = (ImageButton) v.findViewById(R.id.ibImageContract);
		ibImageContract.setOnClickListener(this);

		lvListCustomer = (ListView) v.findViewById(R.id.lv_info_acc);
		lvListCustomer.setOnItemClickListener(this);

		mapview = (MapView) v.findViewById(R.id.idmapview);
		
		CommonActivity.checkConnectionMap(getActivity());
		
		// init map
		MapConfig.changeSRS(SRSType.SRS_4326);

		mapview.setZoom(16);

		mapview.setGPSControlEnabled(true);

		Location location = CommonActivity.findMyLocation(act);
		if (location != null) {
			lat = Double.parseDouble(location.getX());
			lon = Double.parseDouble(location.getY());
			Log.d(Constant.TAG, "FragmentContractVerifySearchDetail unit location lat: " + lat + " lon: " + lon);

		} else {
			Log.d(Constant.TAG, "FragmentContractVerifySearchDetail unit location NULL");
		}

		getVerifyStatus();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		super.onItemClick(adapter, view, pos, id);
		Log.d(Constant.TAG, "FragmentContractVerifySearchDetail onItemClick pos: " + pos);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);

		switch (view.getId()) {
		case R.id.ibImageContract:
			showDialogImage();
			break;
		default:
			break;
		}
	}

    private Dialog dialog;

	private void showDialogImage() {
		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.image_layout);

		Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);

		btnOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		if(arrChargeItemObjectDels != null && !arrChargeItemObjectDels.isEmpty()) {
			ChargeContractItem obj = arrChargeItemObjectDels.get(0);
			if(obj.getVerifyDate() != null && !obj.getVerifyDate().isEmpty()) {
				AsyncViewFileVerificationContract async = new AsyncViewFileVerificationContract(activity);
				async.execute(chargContractItem.getContractId(), obj.getVerifyDate());
			} else {
				CommonActivity.createAlertDialog(activity, getString(R.string.ver_not_done),
						getString(R.string.app_name)).show();
			}
		} else {
			CommonActivity.createAlertDialog(activity, getString(R.string.ver_not_done),
					getString(R.string.app_name)).show();
		}
		
//		AsyncViewFileVerificationContract async = new AsyncViewFileVerificationContract(activity);
//		async.execute("3068785", "09/01/2015");
	}

	private void getVerifyStatus() {
		String contractId = chargContractItem.getContractId();
		if (contractId.isEmpty()) {
			CommonActivity.createAlertDialog(activity, getString(R.string.message_please_insert_phonenumber_contractid),
					getString(R.string.app_name)).show();
		} else {
			AsynctaskGetVerifyStatus asynctaskSearchContract = new AsynctaskGetVerifyStatus(activity);
			asynctaskSearchContract.execute(contractId);
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
	
	private String fileExt(String url) {
	    if (url.contains("?")) {
	        url = url.substring(0, url.indexOf("?"));
	    }
	    if (url.lastIndexOf(".") == -1) {
	        return null;
	    } else {
	        String ext = url.substring(url.lastIndexOf("."));
	        if (ext.contains("%")) {
	            ext = ext.substring(0, ext.indexOf("%"));
	        }
	        if (ext.contains("/")) {
	            ext = ext.substring(0, ext.indexOf("/"));
	        }
	        return ext.toLowerCase();
	    }
	}

	private class AsynctaskGetVerifyStatus extends AsyncTask<String, Void, ArrayList<ChargeContractItem>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskGetVerifyStatus(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected ArrayList<ChargeContractItem> doInBackground(String... params) {
			return getVerifyStatus(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ChargeContractItem> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_contract),
							getString(R.string.app_name)).show();
					return;
				}

				arrChargeItemObjectDels = result;
                ContractVerifyAdapter chargeDelAdapter = new ContractVerifyAdapter(arrChargeItemObjectDels, activity);
				lvListCustomer.setAdapter(chargeDelAdapter);

				ChargeContractItem obj = arrChargeItemObjectDels.get(0);

				Double x = 21.027737;
				Double y = 105.852364;
				
				if(!obj.getxPos().isEmpty() && !obj.getyPos().isEmpty()) {
					try {
						x = Double.parseDouble(obj.getxPos());
						y = Double.parseDouble(obj.getyPos());
					} catch (Exception e) {
						x = lat;
						y = lon;
						
						CommonActivity.createAlertDialog(mActivity, getString(R.string.not_update_location),
								getString(R.string.app_name)).show();
						e.printStackTrace();
					}
				} else {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_update_location),
							getString(R.string.app_name)).show();
				}

				LatLng startPoint = new LatLng(lat, lon);
				LatLng endpoint = new LatLng(x, y);

				MarkerOptions markerOptions = new MarkerOptions()
						.icon(BitmapFactory.decodeResource(getResources(), R.drawable.iconmap)).position(endpoint);
				Marker marker = mapview.addMarker(markerOptions);
				marker.setDraggable(false);
				mapview.setCenter(endpoint);

				RoutingRenderer routingRenderer = new RoutingRenderer(mapview);
				RoutingOptions opts = routingRenderer.getOptions();
				routingRenderer.setOptions(opts);
				routingRenderer.routing(startPoint, endpoint);

				mapview.setVisibility(View.VISIBLE);

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

		private ArrayList<ChargeContractItem> getVerifyStatus(String contractId) {
			ArrayList<ChargeContractItem> listChargeContractItem = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getVerifyStatus");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getVerifyStatus>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<contractId>").append(contractId).append("</contractId>");

				rawData.append("</input>");
				rawData.append("</ws:getVerifyStatus>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getVerifyStatus");
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
					nodechild = doc.getElementsByTagName("lstMContractBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ChargeContractItem chargeItemObject = new ChargeContractItem();
//						chargeItemObject.setAddress(parse.getValue(e1, "address"));
//						chargeItemObject.setContractFormMngt(parse.getValue(e1, "contractFormMngt"));
//						chargeItemObject.setContractFormMngtName(parse.getValue(e1, "contractFormMngtName"));
//						chargeItemObject.setContractId(parse.getValue(e1, "contractId"));
//						chargeItemObject.setContractNo(parse.getValue(e1, "contractNo"));
//						chargeItemObject.setDebit(parse.getValue(e1, "debit"));
//						chargeItemObject.setHotCharge(parse.getValue(e1, "hotCharge"));
//						chargeItemObject.setIsdn(parse.getValue(e1, "isdn"));
//						chargeItemObject.setPayMethodCode(parse.getValue(e1, "payMethodCode"));
//						chargeItemObject.setPayMethodName(parse.getValue(e1, "payMethodName"));
//						chargeItemObject.setPayer(parse.getValue(e1, "payer"));
//						String s = parse.getValue(e1, "paymentStatus");
//						if (s == null || s.isEmpty()) {
//							s = "0";
//						}
//						chargeItemObject.setPaymentStatus(s);
//						chargeItemObject.setPriorDebit(parse.getValue(e1, "priorDebit"));
//						chargeItemObject.setTelFax(parse.getValue(e1, "telFax"));

						chargeItemObject.setTotCharge(parse.getValue(e1, "totCharge"));
						chargeItemObject.setVerifyStatus(parse.getValue(e1, "verifyStatus"));
						chargeItemObject.setxPos(parse.getValue(e1, "xPos"));
						chargeItemObject.setyPos(parse.getValue(e1, "yPos"));
						String verifyDate = parse.getValue(e1, "verifyDate");
						if(verifyDate != null && !verifyDate.isEmpty()) {
							String tmp[] = verifyDate.split(" ");
							chargeItemObject.setVerifyDate(tmp[0].trim());
						}
//						<verifyDate>12/01/2016 17:02:03</verifyDate>
						
						listChargeContractItem.add(chargeItemObject);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentContractVerifySearch getVerifyStatus", e);
			}
			return listChargeContractItem;
		}
	}

	private class AsyncViewFileVerificationContract extends AsyncTask<String, Void, String> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsyncViewFileVerificationContract(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected String doInBackground(String... params) {
			return viewFileVerificationContract(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(final String base64) {
//			super.onPostExecute(base64);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (base64 == null || base64.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_contract),
							getString(R.string.app_name)).show();
                } else {
					Log.d(Constant.TAG, "AsyncViewFileVerificationContract onPostExecute base64.length() " + base64.length() + " " + base64.charAt(base64.length() - 1));
//					byte[] bytes = Base64.decodeBase64(base64);
					byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
					Log.d(Constant.TAG, "AsyncViewFileVerificationContract onPostExecute bytes.length " + bytes.length);

					File tmpFolder = new File(Constant.MBCCS_TEMP_FOLDER);
					if(!tmpFolder.exists()) {
						tmpFolder.mkdir();
					}
					
					File zipFolder = new File(Constant.MBCCS_TEMP_FOLDER);
					if(!zipFolder.exists()) {
						zipFolder.mkdir();
					}
					
					File fileZip = new File(Constant.MBCCS_TEMP_FOLDER + File.separator + "viewFileVerificationContract.zip");
					
					BufferedOutputStream bos;
					List<File> lstFile = null;
					File unzipFolder = new File(Constant.MBCCS_TEMP_FOLDER + "unzip_" + System.currentTimeMillis() + File.separator);
					try {
						bos = new BufferedOutputStream(new FileOutputStream(fileZip));
						bos.write(bytes);
						bos.close();
						Log.d(Constant.TAG, "AsyncViewFileVerificationContract onPostExecute file " + fileZip.getPath() + " " + fileZip.length());

						if(!unzipFolder.exists()) {
							unzipFolder.mkdir();
						}
						
					    lstFile = ZipUtils.unzip(fileZip, unzipFolder);
					} catch (Exception e) {
						Log.e(Constant.TAG, e.toString(), e);
					    Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
					}
					
					for (final File fileEntry : unzipFolder.listFiles()) {
				        if (fileEntry.isFile()) {

                            Log.d(Constant.TAG, "AsyncViewFileVerificationContract onPostExecute fileUnzipped " + fileEntry.length() + " " + fileEntry.getAbsolutePath());
							
							MimeTypeMap myMime = MimeTypeMap.getSingleton();
							Intent newIntent = new Intent(Intent.ACTION_VIEW);
							String mimeType = myMime.getMimeTypeFromExtension(fileExt(fileEntry.getAbsolutePath()).substring(1));
							newIntent.setDataAndType(Uri.fromFile(fileEntry), mimeType);
							newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							try {
							    startActivity(newIntent);
							} catch (ActivityNotFoundException e) {
							    Toast.makeText(activity, "No handler for this type of file.", Toast.LENGTH_LONG).show();
							}
							break;
				        }
				    }
					
//				    Bitmap bitmap = BitmapFactory.decodeFile(lstFile.get(0).getPath());
//					Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//					if(bitmap != null)
//						Log.d(Constant.TAG, "AsyncViewFileVerificationContract onPostExecute bitmap: " + bitmap.getByteCount());
//					else
//						Log.d(Constant.TAG, "AsyncViewFileVerificationContract onPostExecute bitmap NULL");
//					imageView.setImageBitmap(bitmap);
//					dialog.show();
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
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private String viewFileVerificationContract(String contractId, String verifyDate) {
			String original = "";
			String fileContent = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_viewFileVerificationContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:viewFileVerificationContract>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<contractId>").append(contractId).append("</contractId>");
				if(verifyDate == null || verifyDate.isEmpty())
					verifyDate = "";
				rawData.append("<verifyDate>").append(verifyDate).append("</verifyDate>");

				rawData.append("</input>");
				rawData.append("</ws:viewFileVerificationContract>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_viewFileVerificationContract");
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
					description = parse.getValue(e2, "description");

					fileContent = parse.getValue(e2, "fileContent");

					Log.d("errorCode", errorCode);
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentContractVerifySearch getVerifyStatus", e);
			}
			return fileContent;
		}
	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";verify_search;";
	}
}
