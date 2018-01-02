package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterInfraTypeSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterServiceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.LockBoxInfoAdapter;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.DalInfrastructure;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ApParamObj;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.AreaObject;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.LockBoxInfoObj;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ServiceObject;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.SurveyOnline;
import com.viettel.bss.viettelpos.v4.login.object.LocationService;
import com.viettel.bss.viettelpos.v4.login.object.Optional;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.maps.MapView;
import com.viettel.maps.MapView.MapEventListener;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.InfoWindow;
import com.viettel.maps.objects.InfoWindowOptions;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentInfrastructureOnline extends Fragment implements
		OnClickListener {

	private static final String TAG = FragmentInfrastructureOnline.class
			.getName();

	private View mView;
	private Button btnHome;
	private MapView mapView;
	private final ArrayList<SurveyOnline> arSurveyOnlines = new ArrayList<>();
    private AdapterServiceSpinner mAdapterServiceSpinner;
	private DalInfrastructure dalInfrastructure;
	private ArrayList<LockBoxInfoObj> mListLockBoxInfo;
    private BCCSGateway mBccsGateway;
	private LoadSurveyOnline loadSurveyOnline;

    private SurveyOnline surveyOnlineSelect;
	private Dialog dialog = null;
	private String locationCodeStr;
	private Spinner spServiceIdNum;
	private Spinner infraType;
	private String mPrecinct = "";
	private ArrayList<AreaObj> disCode1 = new ArrayList<>();
	private ArrayList<AreaObj> disCode2 = new ArrayList<>();
	private ArrayList<AreaObj> disCode3 = new ArrayList<>();
	private ArrayList<TelecomServiceObj> mListTelecom;
	private InfrastrucureDB mInfrastrucureDB;
	private String mLockCap;
	private String serviceTypeCode;
	private String techTypeCode;
	private ArrayList<ApParamObj> mInfraType;
	private ArrayList<ApParamObj> mParamObjs;
	private TextView tvNumberPortKeep;
	private String mConnectorId = null;
	private String serviceTypeName = "";
	private String techTypeName = "";
	private Dialog dialogLockBoxInfo = null;
	private String mTitleStr;
	// private Button btnSearch = null;
	private int serviceSelect = 0;
    private CustomDialogShowData dialogShowData;

    @Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("TAG", "KHAO SAT HA TANG ONLINE");
		if (mView == null) {
			dalInfrastructure = new DalInfrastructure(getActivity());
			mView = inflater.inflate(R.layout.infrastructure_online_layout,
					container, false);
			mInfrastrucureDB = new InfrastrucureDB(getActivity());
			mInfraType = new ArrayList<>();
			mParamObjs = new ArrayList<>();

			mParamObjs = mInfrastrucureDB.getListParam();

			mListLockBoxInfo = new ArrayList<>();
			spServiceIdNum = (Spinner) mView.findViewById(R.id.spServiceIDNum);
			infraType = (Spinner) mView.findViewById(R.id.infraType);
			getListSpinner();

			spServiceIdNum
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							serviceSelect = position;

							TelecomServiceObj obj = mListTelecom.get(position);
							serviceTypeCode = obj.getCode();

							serviceTypeName = obj.getTelServiceName(); // serviceTypeName
																		// techTypeName
							changeSpinerInfratype(serviceSelect);
							// loadListInfaType();
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							serviceSelect = 0;
						}
					});
			changeSpinerInfratype(0);

			infraType.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					techTypeCode = mInfraType.get(position).getParType(); //
					techTypeName = mInfraType.get(position).getParName();
					// loadListInfaType();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});

			unit(mView);

			loadSurveyTask();

		}
		return mView;
	}

	private void loadSurveyTask() {
		boolean isNetwork = CommonActivity.isNetworkConnected(getActivity());
		if (isNetwork) {
			loadSurveyOnline = new LoadSurveyOnline();
			loadSurveyOnline.execute();
		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name)).show();
		}
	}

	private void loadListInfaType() {
		loadSurveyOnline = new LoadSurveyOnline();
		loadSurveyOnline.execute();
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
		techTypeCode = param.get(0).getParType();
		AdapterInfraTypeSpinner adapterInfraType = new AdapterInfraTypeSpinner(
				param, getActivity());
		infraType.setAdapter(adapterInfraType);
	}

	private void getListSpinner() {
		disCode1 = mInfrastrucureDB.getListDis(Session.province);

		String disCodeStr1 = Session.province + Session.district;
		disCode2 = mInfrastrucureDB.getListDis(disCodeStr1);

		disCode3 = new ArrayList<>();
		AreaObj areaObj = new AreaObj();
		areaObj.setName(getString(R.string.all));
		disCode3 = mInfrastrucureDB.getListStreet(disCodeStr1);
		disCode3.add(0, areaObj);

		setListSpinner();
	}

	private void getData() {
		getListSpinerDialog();
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

	private void setListSpinner() {
		mListTelecom = new ArrayList<>();
		mListTelecom = mInfrastrucureDB.getListTelecomService();
		AdapterServiceSpinner adapterServiceSpinner = new AdapterServiceSpinner(
				mListTelecom, getActivity());

		serviceTypeCode = mListTelecom.get(0).getCode();
		spServiceIdNum.setAdapter(adapterServiceSpinner);
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.infrastructure_1);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mapView.setMarkerClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				Marker marker = (Marker) obj;
				InfoWindowOptions opts = new InfoWindowOptions(marker
						.getTitle(), marker.getPosition());
				opts.snippet(marker.getDescription());
				opts.anchor(new Point(0, -marker.getIcon().getHeight()));
				InfoWindow info = mapView.showInfoWindow(opts);
				surveyOnlineSelect = (SurveyOnline) marker.getUserData();
				Log.i("tag", "setMarkerClickListener");

				return true;
			}

		});

		mapView.setInfoWindowClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {

				InfoWindow info = (InfoWindow) obj;
				dialogInfrastructure(surveyOnlineSelect);

				Log.e("TAG", "NAME = " + surveyOnlineSelect.getAddress());
				return true;
			}

		});
		// mapView.

		mapView.setMapEventListener(new MapEventListener() {
			LatLng latLngMapCenter;

			@Override
			public boolean onSingleTap(LatLng latLng, Point arg1) {
				// TODO Auto-generated method stub
				Log.d("TAG Map", "LatLng : " + latLng);
				latLngMapCenter = latLng;
				return false;
			}

			@Override
			public void onMapUpdateData(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.d("TAG Map", "onMapUpdateData LatLng : " + latLngMapCenter
						+ ", " + arg0 + ", " + arg1);
			}

			@Override
			public boolean onLongTap(LatLng latLng, Point arg1) {
				// TODO Auto-generated method stub
				Log.d("TAG Map", "onLongTap LatLng : " + latLng);
				return false;
			}

			@Override
			public void onIdle() {
				// TODO Auto-generated method stub
				Log.d("TAG Map", "onIdle LatLng : " + latLngMapCenter);
			}

			@Override
			public boolean onDoubleTap(LatLng arg0, Point arg1) {
				// TODO Auto-generated method stub
				Log.d("TAG Map", "onDoubleTap LatLng : " + latLngMapCenter);
				return false;
			}

			@Override
			public void onCenterChanged() {
				// onSingleTap(latLng, arg1);
				Log.d("TAG Map", "onCenterChanged LatLng : " + latLngMapCenter);
			}

			@Override
			public void onBoundChanged() {
				// TODO Auto-generated method stub
				Log.d("TAG Map", "onBoundChanged LatLng : ");
			}
		});
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if (loadSurveyOnline != null) {
			loadSurveyOnline.cancel(true);
		}
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		if (mapView != null) {
			mapView.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	private void unit(View v) {
        ArrayList<ServiceObject> arrayList1 = new ArrayList<>();
        ArrayList<AreaObject> provinceArray = new ArrayList<>();
        ArrayList<AreaObject> districtArray = new ArrayList<>();
        ArrayList<AreaObject> streetArray = new ArrayList<>();
        ArrayList<AreaObj> disCode = new ArrayList<>();
		ArrayList<ServiceObject> arrayList = dalInfrastructure
				.getAllServiceSpinner(Define.TABLE_NAME_SERVICE)

		;
		if (arrayList != null) {
			for (int i = 0; i < arrayList.size(); i++) {
				ServiceObject serviceObject = arrayList.get(i);
				String service = serviceObject.getTel_service_name();
				if ("ADSL".equals(service) || "FTTH".equals(service)
						|| "PSTN".equals(service) || "NetTV".equals(service)) {
					arrayList1.add(arrayList.get(i));
				}
			}
			// mAdapterServiceSpinner = new
			// AdapterServiceSpinner(this.arrayList,
			// getActivity());
			// spService.setAdapter(mAdapterServiceSpinner);
		}
		mapView = (MapView) v.findViewById(R.id.idMapView);
		CommonActivity.checkConnectionMap(getActivity());
		// MapConfig.changeSRS(SRSType.SRS_4326);
		MapConfig.changeSRS(SRSType.SRS_900913);
		mapView.setZoom(16);
		mapView.setGPSControlEnabled(true);
        ArrayList<LocationService> arrayListLocation = new ArrayList<>();
		mBccsGateway = new BCCSGateway();
		// mEdtSearch = (EditText) v.findViewById(R.id.edtSearchIDNum);
		// btnSearch = (Button) v.findViewById(R.id.btnSearch);
		dialogShowData = new CustomDialogShowData(getActivity());
		String disCodeStr = Session.province + Session.district;
		disCode = mInfrastrucureDB.getListDis(disCodeStr);
		getData();

		// btnSearch.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// dialogShowData.show();
		// }
		// });
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

	private ProgressDialog progressDialog;

	private class LoadSurveyOnline extends AsyncTask<String, String, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();

		Activity activity;
		ProgressDialog progress;

		public LoadSurveyOnline() {
			super();
			// TODO Auto-generated constructor stub
		}

		public LoadSurveyOnline(String response, String original,
				Activity activity, ProgressDialog progress) {
			super();
			this.response = response;
			this.original = original;
			this.activity = activity;
			this.progress = progress;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Loading...");
			if (!progressDialog.isShowing()) {
				progressDialog.setCancelable(false);
				// progressDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

            String mSurveyRadius = "500";
            String xml = mBccsGateway
					.buildInputGatewayWithRawData2(
							createXML(techTypeCode, "", serviceTypeCode,
                                    mSurveyRadius), "mbccs_surveyOnline");
			Log.e("TAG", "XML" + xml.toString());
			CommonOutput output = null;
			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {

				try {
					response = mBccsGateway.sendRequest(xml,
							Constant.BCCS_GW_URL, getActivity(),
							"mbccs_surveyOnline");

					output = input.parseGWResponse(response);
					original = output.getOriginal();
					Log.e("TAG", "reponse " + original.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				original = null;
			}
			return original;
		}

		@Override
		protected void onPostExecute(String result) {

			parseResultError(result);

			if (result != null) {

				try {
					Log.e("TAG", "result = " + result);
					Document doc = parse.getDomElement(result);
					NodeList nl = doc.getElementsByTagName("return");
					NodeList nodelstchild = null;

					for (int i = 0; i < nl.getLength(); i++) {
						nodelstchild = doc
								.getElementsByTagName("lstResultSurveyOnlineForm");
						for (int j = 0; j < nodelstchild.getLength(); j++) {
							SurveyOnline surveyOnline = new SurveyOnline();
							Element e1 = (Element) nodelstchild.item(j);
							String address = parse.getValue(e1, "address");
							String connectorCode = parse.getValue(e1,
									"connectorCode");
							String connectorType = parse.getValue(e1,
									"connectorType");
							String deptCode = parse.getValue(e1, "deptCode");
							String deptName = parse.getValue(e1, "deptName");
							String lat = parse.getValue(e1, "lat");
							String lng = parse.getValue(e1, "lng");
							String projectCode = parse.getValue(e1,
									"projectCode");
							String projectId = parse.getValue(e1, "projectId");
							String connectorId = parse.getValue(e1,
									"connectorId");
							String projectName = parse.getValue(e1,
									"projectName");
							String resourceConnector = parse.getValue(e1,
									"resourceConnector");
							String resourceDevice = parse.getValue(e1,
									"resourceDevice");
							String resourceRootCable = parse.getValue(e1,
									"resourceRootCable");
							String stationCode = parse.getValue(e1,
									"stationCode");
							String stationId = parse.getValue(e1, "stationId");
							surveyOnline.setAddress(address);
							surveyOnline.setConnectorCode(connectorCode);
							surveyOnline.setConnectorType(connectorType);
							surveyOnline.setDeptCode(deptCode);
							surveyOnline.setDeptName(deptName);
							surveyOnline.setLat(lat);
							surveyOnline.setLng(lng);
							surveyOnline
									.setResourceConnector(resourceConnector);
							surveyOnline.setResourceDevice(resourceDevice);
							surveyOnline.setStationCode(stationCode);
							surveyOnline.setStationId(stationId);
							// surveyOnline.setProjectCode(projectCode);
							// surveyOnline.setProjectId(projectId);
							// surveyOnline.setProjectName(projectName);
							surveyOnline.setConnectorId(connectorId);
							MarkerOptions markerOptions = new MarkerOptions()
									.icon(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.icon_location))
									.position(
											new LatLng(Double.parseDouble(lat),
													Double.parseDouble(lng)))
									.title(address).description(deptName);

							Marker marker = mapView.addMarker(markerOptions);
							marker.setUserData(surveyOnline);
							arSurveyOnlines.add(surveyOnline);

						}
					}

					progressDialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			}

			super.onPostExecute(result);
		}
	}

	// Lock Box user
    private void dialogInfrastructure(SurveyOnline surveyOnline) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// LocationService locationService = arSurveyOnlines.get(position);
		// Log.d("locationService", "locationService :" + locationService);

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
		tvNumberPortKeep = (TextView) dialogView
				.findViewById(R.id.tv_number_port_keep);

		tvNumberPort.setText(serviceTypeName);
		final EditText edtNumberPort = (EditText) dialogView
				.findViewById(R.id.edt_number_port);
		Button btnKeep = (Button) dialogView.findViewById(R.id.btn_keep);
		Button btnClose = (Button) dialogView.findViewById(R.id.btn_colse);

		ImageView lockImage = (ImageView) dialogView
				.findViewById(R.id.lock_image);

		mTitleStr = surveyOnline.getDeptName() + "-"
				+ surveyOnline.getAddress();
		mLockCap = surveyOnline.getConnectorCode();
		topText.setText(mTitleStr);
		tvConnrctorCode.setText(mLockCap);
		tvNumberPortService.setText(getActivity().getString(
				R.string.dialog_inflas_detail_bottom)
				+ " " + surveyOnline.getResourceDevice());

		mConnectorId = surveyOnline.getConnectorId();
		// tvNumberPortService.setText(locationService.getCountNetTV());
		// tvNumberPort.setText(serviceName);

		/*
		 * Lay so cong da giu serviceTypeCode techTypeCode
		 */

		new ViewLockBoxInfoTask(surveyOnline.getConnectorId(), techTypeCode,
				serviceTypeCode).execute();

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
		// LocationService locationService = arrayListLocation.get(position);

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

	private void lockBox(String numberPort) {
		new LockBoxTask(techTypeCode, numberPort, serviceTypeCode,
				Session.userName).execute();
		Log.d("",
				"serviceTypeCode, numberPort, serviceTypeName, userName, mConnectorId :"
						+ serviceTypeCode + ", " + numberPort + ", "
						+ serviceTypeCode + ", " + Session.userName + ", "
						+ mConnectorId);
	}

	private String createXML(String infraType, String noResouce,
			String serviceType, String surveyRadius) {
		StringBuilder stringBuilder = new StringBuilder("<ws:surveyOnline>");
		stringBuilder.append("<nimsInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<surveyOnlineForm>");

		ArrayList<Optional> arrayList = new ArrayList<>();
		arrayList.add(new Optional("infraType", infraType));

		double lat = 0;
		double longDou = 0;
		com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
				.findMyLocation(getActivity());
		// if (myLocation != null) {
		// lat = myLocation.getLatitude();
		// longDou = myLocation.getLongitude();
		// }
		lat = Double.parseDouble(myLocation.getX());
		longDou = Double.parseDouble(myLocation.getY());
		arrayList.add(new Optional("lat", Double.toString(lat)));
		arrayList.add(new Optional("lng", Double.toString(longDou)));

		arrayList.add(new Optional("noResouce", noResouce));
		arrayList.add(new Optional("serviceType", serviceType));
		arrayList.add(new Optional("surveyRadius", surveyRadius));
		stringBuilder.append(mBccsGateway.crateXml(arrayList));
		stringBuilder.append("</surveyOnlineForm>");
		stringBuilder.append("</nimsInput>");
		stringBuilder.append("</ws:surveyOnline>");
		return stringBuilder.toString();
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
			Log.e("TAG3", "result LockBox info : " + result);
			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);
				parseResultLockBoxInfo(result);
			}

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			super.onPostExecute(result);
		}
	}

	private String requestSeviceViewLockInfo(String connectorId,
                                             String serviceTypeName2, String serviceTypeCode2) {
		String reponse = "";
		String original = "";
		String xmlLockBox = lockBoxInfo(connectorId, serviceTypeName2,
				serviceTypeCode2);
		Log.e("TAG3", "xml view LockBoxInfo : " + xmlLockBox);
		try {
			reponse = mBccsGateway
					.sendRequest(xmlLockBox, Constant.BCCS_GW_URL,
							getActivity(), "mbccs_viewLockBoxInfo");
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
					String mTotalPort = domParse.getValue(element, "totalPort");
					tvNumberPortKeep.setText(getActivity().getResources()
							.getString(R.string.tv_number_port_keep)
							+ " : "
							+ mTotalPort);
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
			} else {
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

	private String lockBoxByUser(String infraType, String numberOfPort,
			String serviceType, String userLock) {
		String xml = mBccsGateway
				.getXmlLockBox(
						createXMLLockBox(infraType, numberOfPort, serviceType
                        ), "mbccs_lockBoxByUser");
		Log.d("", "xml lockBoxByUser : " + xml);
		return xml;
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

	private String requestSevice(String infraType2, String numberOfPort,
                                 String serviceType, String userLock) {
		String reponse = "";
		String original = "";
		String xmlLockBox = lockBoxByUser(infraType2, numberOfPort,
				serviceType, userLock);
		Log.e("TAG3", "xmlLockBox : " + xmlLockBox);
		try {
			reponse = mBccsGateway.sendRequest(xmlLockBox,
					Constant.BCCS_GW_URL, getActivity(), "mbccs_lockBoxByUser");
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

	// show tinh , huyen, xa
	private class CustomDialogShowData extends Dialog {
		private Spinner provinceSpinner;
		private Spinner districtSpinner;
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

			Log.e("TAG", " NHAY VAO DAY");

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_search);
			provinceSpinner = (Spinner) findViewById(R.id.spinnerProvince);
			districtSpinner = (Spinner) findViewById(R.id.spinnerDistrict);
			streetSpinner = (Spinner) findViewById(R.id.spinnertram);

			// thanh pho
			AdapterProvinceSpinner adapterSpinner = new AdapterProvinceSpinner(
					disCode1, getActivity());
			provinceSpinner.setAdapter(adapterSpinner);
			provinceSpinner.setEnabled(false);
			// Quan
			AdapterProvinceSpinner adapterSpinner1 = new AdapterProvinceSpinner(
					disCode2, getActivity());
			districtSpinner.setAdapter(adapterSpinner1);
			districtSpinner.setEnabled(false);

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
					// mEdtSearch.setText(locationCodeStr);
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
				Log.e("TAG69 PC", " xml parseResultObjectByProductCode : "
						+ result);
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
						CommonActivity.BackFromLogin(getActivity(),
								description, "");
					} else if (errorCode.equals("0")) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
