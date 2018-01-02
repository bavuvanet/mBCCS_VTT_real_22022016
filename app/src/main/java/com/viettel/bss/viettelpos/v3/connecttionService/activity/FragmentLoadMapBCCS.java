package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.*;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentLoadMap;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SysUsersBO;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetConectorCodeAdapterBCCS;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultGetVendorByConnectorForm;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.services.AdminLevelType;
import com.viettel.maps.services.AdminReturnType;
import com.viettel.maps.services.AdminService;
import com.viettel.maps.services.AdminService.AdminServiceListener;
import com.viettel.maps.services.AdminServiceResult;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentLoadMapBCCS extends GPSTracker implements OnClickListener {

	// init view
	EditText editBKKS;
	Button btnkhaosat;
	MapView mapview;

	String serviceID = "";
	String techchologyID = "";
	String parValueTechology = "";
	String surfaceRadius = "";

	String lat = "0";
	String lon = "0";

	Location myLocation;
	// 21.022536, 105.799549
	ArrayList<ResultSurveyOnlineForBccs2Form> lisSurveyOnlines = new ArrayList<ResultSurveyOnlineForBccs2Form>();
	ResultSurveyOnlineForBccs2Form surveyOnlineSelect;

	public ResultSurveyOnlineForBccs2Form surveyOnlineInit = new ResultSurveyOnlineForBccs2Form();

	public TextView txtnamequanlyGPON;
	public EditText txtdtlienheGPON;

	private ArrayList<ResultSurveyOnlineForBccs2Form> arrSurveyOnlines = new ArrayList<ResultSurveyOnlineForBccs2Form>();

	private Dialog dialogGPON;

	private String isFTTB = "";
	private String mstNoRf = "";

	private Spinner spinner_loaiks, spinner_congnghe;
	private LinearLayout lndiaban;
	private EditText edtprovince, edtdistrict, edtprecinct;

	private String province = "";
	private String district = "";
	private String precinct = "";
	private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();
	private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();

	private String vtmapCode = "";

	private Marker markerinit = null;

	// thong tin ha tang tu duong day
	private ResultGetVendorByConnectorForm resultGetVendorByConnectorForm;

	private TextView txtnamequanly;
	private TextView txtChucVu;
	private TextView txtdtlienhe;

	// thong tin vendor
	private LinearLayout lnVendor;
	private TextView txtloaivendor;

	private ArrayList<ProductCatalogDTO> lstProductCatalog = new ArrayList<ProductCatalogDTO>();
	private AreaBean areaBean;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private String CHECK_CHANGE_TECH="";
	private Spin techSelect = new Spin();
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		addActionBar();
		CommonActivity.checkConnectionMap(FragmentLoadMapBCCS.this);
		setContentView(R.layout.connection_layout_map_v3);
		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// get bunder
		// gpsTracker = new GPSTracker(FragmentLoadMap.this);
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			serviceID = mBundle.getString("serviceType");
			CHECK_CHANGE_TECH = mBundle.getString("CHECK_CHANGE_TECH","");
			Log.d("serviceID", serviceID);
			lstProductCatalog = (ArrayList<ProductCatalogDTO>) mBundle.getSerializable("lstProductCatalog");
			techSelect = (Spin) mBundle.getSerializable("techSelect");
			areaBean = (AreaBean) mBundle.getSerializable("areaBeanMainKey");
		}

		Location location = CommonActivity.findMyLocation(FragmentLoadMapBCCS.this);
		if (location != null) {

			lat = location.getX();
			lon = location.getY();
		} else {
			lat = "21.0287592";
			lon = "105.8501718";
		}

		List<Spin> lstSpin = new ArrayList<Spin>();
		if(!CommonActivity.isNullOrEmpty(areaBean) && !CommonActivity.isNullOrEmpty(areaBean.getProvince())){
			lstSpin.add(new Spin("2", this.getString(R.string.kstheodiaban)));
			lstSpin.add(new Spin("1", this.getString(R.string.kstheotoado)));
		}else{
			lstSpin.add(new Spin("1", this.getString(R.string.kstheotoado)));
			lstSpin.add(new Spin("2", this.getString(R.string.kstheodiaban)));

		}

		spinner_loaiks = (Spinner) findViewById(R.id.spinner_loaiks);
		Utils.setDataSpinner(FragmentLoadMapBCCS.this, lstSpin, spinner_loaiks);
		lndiaban = (LinearLayout) findViewById(R.id.lndiaban);
		edtprovince = (EditText) findViewById(R.id.edtprovince);
		edtprovince.setEnabled(true);
		edtdistrict = (EditText) findViewById(R.id.edtdistrict);
		edtprecinct = (EditText) findViewById(R.id.edtprecinct);
		if(!CommonActivity.isNullOrEmptyArray(arrProvince)){
			arrProvince = new ArrayList<>();
		}
		initProvince();

		if(!CommonActivity.isNullOrEmpty(areaBean) && !CommonActivity.isNullOrEmpty(areaBean.getProvince()) && !CommonActivity.isNullOrEmpty(areaBean.getDistrict()) && !CommonActivity.isNullOrEmpty(areaBean.getPrecinct())){
			province = areaBean.getProvince();
			district = areaBean.getDistrict();
			precinct = areaBean.getPrecinct();


			int type = 2;
			try {
				GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
				dal.open();
				vtmapCode = dal.getmAPVTMap(province + district + precinct);
				if (CommonActivity.isNullOrEmpty(vtmapCode)) {
					vtmapCode = dal.getmAPVTMap(province + district);
					type = 1;
				}
				if (CommonActivity.isNullOrEmpty(vtmapCode)) {
					vtmapCode = dal.getmAPVTMap(province);
					type = 0;
				}
				dal.close();
			} catch (Exception e) {
				Log.d(FragmentLoadMapBCCS.class.getSimpleName(), e.toString());
			}
			if (!CommonActivity.isNullOrEmpty(vtmapCode)) {
				String vtmap = CommonActivity.checkVTmapCode(vtmapCode);
				focusArea(vtmap, type);
			}

		}else{
			province = Session.province;
			district = Session.district;
		}
		if (!CommonActivity.isNullOrEmpty(province)) {
			initDistrict(province);
			try {
				GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
				dal.open();
				edtprovince.setText(dal.getNameProvince(province));
				dal.close();
			} catch (Exception e) {
			}
		}
		edtprovince.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FragmentLoadMapBCCS.this, com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation.class);
				intent.putExtra("arrProvincesKey", arrProvince);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "1");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 106);
			}
		});

		if (!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district)) {
			initPrecinct(province, district);
			try {
				GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
				dal.open();
				edtdistrict.setText(dal.getNameDistrict(province, district));
				dal.close();
			} catch (Exception e) {
			}
		}
		edtdistrict.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FragmentLoadMapBCCS.this, com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation.class);
				intent.putExtra("arrDistrictKey", arrDistrict);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "2");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 107);

			}
		});
		if (!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district) && !CommonActivity.isNullOrEmpty(precinct)) {
			try {
				GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
				dal.open();
				edtprecinct.setText(dal.getNamePrecint(province, district,precinct));
				dal.close();
			} catch (Exception e) {
			}
		}
		edtprecinct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FragmentLoadMapBCCS.this, com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation.class);
				intent.putExtra("arrPrecinctKey", arrPrecinct);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "3");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 108);
			}
		});
		spinner_loaiks.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				if (item != null) {
					if ("1".equals(item.getId())) {
						Location location = CommonActivity.findMyLocation(FragmentLoadMapBCCS.this);
						if (location != null) {

							lat = location.getX();
							lon = location.getY();
						} else {
							lat = "21.0287592";
							lon = "105.8501718";
						}
						lndiaban.setVisibility(View.GONE);
						if (lat.equals("0") || lon.equals("0")) {
							CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
									getResources().getString(R.string.checkgps),
									getResources().getString(R.string.app_name)).show();
						} else {
							MarkerOptions markerOptions = new MarkerOptions()
									.icon(BitmapFactory.decodeResource(getResources(), R.drawable.iconmap))
									.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));
							markerinit = mapview.addMarker(markerOptions);
							markerinit.setDraggable(true);
							mapview.setCenter(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));
						}

					} else {

//						if (markerinit != null) {
//							mapview.removeMarker(markerinit);
//							mapview.refresh();
//						}

						lndiaban.setVisibility(View.VISIBLE);
						// TODO XU LY CHO NAY
//						if (!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district)) {
//							initPrecinct(province, district);
//							try {
//								GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
//								dal.open();
//								edtdistrict.setText(dal.getNameDistrict(province, district));
//								dal.close();
//							} catch (Exception e) {
//							}
//						}
						if(!CommonActivity.isNullOrEmpty(areaBean) && !CommonActivity.isNullOrEmpty(areaBean.getProvince()) && !CommonActivity.isNullOrEmpty(areaBean.getDistrict()) && !CommonActivity.isNullOrEmpty(areaBean.getPrecinct())){
							province = areaBean.getProvince();
							district = areaBean.getDistrict();
							precinct = areaBean.getPrecinct();


							int type = 2;
							try {
								GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
								dal.open();
								vtmapCode = dal.getmAPVTMap(province + district + precinct);
								if (CommonActivity.isNullOrEmpty(vtmapCode)) {
									vtmapCode = dal.getmAPVTMap(province + district);
									type = 1;
								}
								if (CommonActivity.isNullOrEmpty(vtmapCode)) {
									vtmapCode = dal.getmAPVTMap(province);
									type = 0;
								}
								dal.close();
							} catch (Exception e) {
								Log.d(FragmentLoadMapBCCS.class.getSimpleName(), e.toString());
							}
							if (!CommonActivity.isNullOrEmpty(vtmapCode)) {
								String vtmap = CommonActivity.checkVTmapCode(vtmapCode);
								focusArea(vtmap, type);
							}

						}else{
							province = Session.province;
							district = Session.district;
						}
//						edtprecinct.setText("");
//						precinct = "";
					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// init view
		editBKKS = (EditText) findViewById(R.id.edit_bkks);
		btnkhaosat = (Button) findViewById(R.id.btn_ks);
		mapview = (MapView) findViewById(R.id.idmapview);
		List<Spin> lstMapType = new ArrayList<>();
		lstMapType.add(new Spin("" + MapConfig.MapType.TRANSPORT, this.getString(R.string.transport)));
		lstMapType.add(new Spin("" + MapConfig.MapType.ADMIN, this.getString(R.string.gsafelite)));
		SharedPreferences preferences = this.getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		if(name.contains(";menu_select_googlemap;")){
			lstMapType.add(new Spin(""+ MapConfig.MapType.GTRANSPORT, this.getString(R.string.gtransport)));
		}


		Spinner spinner_loaibando = (Spinner) findViewById(R.id.spinner_loaibando);
		Utils.setDataSpinner(FragmentLoadMapBCCS.this, lstMapType, spinner_loaibando);
		spinner_loaibando.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				if(item != null){
					mapview.setMapType(Integer.parseInt(item.getId()), true);

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		// init map
		MapConfig.changeSRS(SRSType.SRS_900913);

		mapview.setZoom(16);

		mapview.setGPSControlEnabled(true);

//		if (serviceID.equals("A") || serviceID.equals("P")) {
			editBKKS.setText("500");
//		}
		// set onclick for button ks

		btnkhaosat.setOnClickListener(this);

		mapview.setMarkerDragListener(new MapLayer.OnDragListener() {

			@Override
			public boolean onDrag(Point arg0, LatLng arg1, MapObject arg2) {
				Log.d("onDrag", "onDrag");
				return true;
			}

			@Override
			public boolean onDragEnd(Point arg0, LatLng arg1, MapObject arg2) {
				lat = String.valueOf(arg1.getLatitude());
				lon = String.valueOf(arg1.getLongitude());
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.movetoado) + " X = " + lat + " Y = " + lon,
						Toast.LENGTH_SHORT).show();
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
				Marker marker = (Marker) obj;
				surveyOnlineSelect = (ResultSurveyOnlineForBccs2Form) marker.getUserData();
				if (arrSurveyOnlines.size() > 0) {
					arrSurveyOnlines.clear();
				}
				if (surveyOnlineSelect != null) {
					if (surveyOnlineSelect.getDistance() != 0) {
						for (ResultSurveyOnlineForBccs2Form surveyOnline : lisSurveyOnlines) {
							if (surveyOnline.getDistance() == surveyOnlineSelect.getDistance()) {
								arrSurveyOnlines.add(surveyOnline);
							}
						}

					}
					// if (!parValueTechology.equals("")
					// && parValueTechology.equals("GPON")) {

					if (arrSurveyOnlines.size() > 1) {
						// show popup
						dialogGPON = new Dialog(FragmentLoadMapBCCS.this);
						dialogGPON.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialogGPON.setContentView(R.layout.connection_layout_lst_conectorcode);
						ListView lstpstn = (ListView) dialogGPON.findViewById(R.id.lstpstn);

						GetConectorCodeAdapterBCCS adapCodeAdapter = new GetConectorCodeAdapterBCCS(arrSurveyOnlines,
								FragmentLoadMapBCCS.this);
						lstpstn.setAdapter(adapCodeAdapter);
						lstpstn.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								// showPopupGPON(arrSurveyOnlines.get(arg2));

								surveyOnlineSelect = arrSurveyOnlines.get(arg2);
								OptionSetValueDTO optionSetValueDTO = (OptionSetValueDTO) spinner_congnghe
										.getSelectedItem();
								if (surveyOnlineSelect != null && "4".equals(surveyOnlineSelect.getInfraType())) {

									if (serviceID.contains("F") || serviceID.contains("N") || serviceID.contains("I")
											|| serviceID.contains("2")) {

										GetVendorByConnectorAsyn getVendorByConnectorAsyn = new GetVendorByConnectorAsyn(
												FragmentLoadMapBCCS.this);
										getVendorByConnectorAsyn.execute(surveyOnlineSelect.getConnectorId() + "",
												surveyOnlineSelect.getConnectorCode(), "");

									} else {
										showDialogInfratructer(optionSetValueDTO.getName(), surveyOnlineSelect);
									}
								} else {

									showDialogInfratructer(optionSetValueDTO.getName(), surveyOnlineSelect);

								}

							}
						});

						Button btnhuy = (Button) dialogGPON.findViewById(R.id.btnhuy);
						btnhuy.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								dialogGPON.dismiss();
							}
						});
						dialogGPON.show();

					} else {

						OptionSetValueDTO optionTech = (OptionSetValueDTO) spinner_congnghe.getSelectedItem();

						if (optionTech != null) {
							if (surveyOnlineSelect != null && "4".equals(surveyOnlineSelect.getInfraType())) {

								if (serviceID.contains("F") || serviceID.contains("N") || serviceID.contains("I")
										|| serviceID.contains("2")) {

									GetVendorByConnectorAsyn getVendorByConnectorAsyn = new GetVendorByConnectorAsyn(
											FragmentLoadMapBCCS.this);
									getVendorByConnectorAsyn.execute(surveyOnlineSelect.getConnectorId() + "",
											surveyOnlineSelect.getConnectorCode(), "");

								} else {
									showDialogInfratructer(optionTech.getName(), surveyOnlineSelect);
								}
							} else {

								showDialogInfratructer(optionTech.getName(), surveyOnlineSelect);

							}
						}
					}

					// }
					Log.i("tag", "setMarkerClickListener");
				}

				return true;
			}

		});

		mapview.setInfoWindowClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				return true;
			}

		});

		spinner_congnghe = (Spinner) findViewById(R.id.spinner_congnghe);
		if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
			spinner_congnghe.setEnabled(false);
		}
		GetListTechnologyAsyn getListTechnologyAsyn = new GetListTechnologyAsyn(FragmentLoadMapBCCS.this);
		getListTechnologyAsyn.execute(serviceID);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case 106:
					if (data != null) {
						AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");

						province = areaBean.getProvince();
						initDistrict(province);
						edtprovince.setText(areaBean.getNameProvince());
						edtdistrict.setText("");
						edtprecinct.setText("");
						district = "";
						precinct = "";
					}
					break;
			case 107:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
					district = areaBean.getDistrict();
					initPrecinct(province, district);
					edtdistrict.setText(areaBean.getNameDistrict());
					edtprecinct.setText("");
					precinct = "";
				}
				break;

			case 108:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("precinctKey");
					precinct = areaBean.getPrecinct();
					edtprecinct.setText(areaBean.getNamePrecint());
					int type = 2;
					try {
						GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
						dal.open();
						vtmapCode = dal.getmAPVTMap(province + district + precinct);
						if (CommonActivity.isNullOrEmpty(vtmapCode)) {
							vtmapCode = dal.getmAPVTMap(province + district);
							type = 1;
						}
						if (CommonActivity.isNullOrEmpty(vtmapCode)) {
							vtmapCode = dal.getmAPVTMap(province);
							type = 0;
						}
						dal.close();
					} catch (Exception e) {
						Log.d(FragmentLoadMapBCCS.class.getSimpleName(), e.toString());
					}
					if (!CommonActivity.isNullOrEmpty(vtmapCode)) {
						String vtmap = CommonActivity.checkVTmapCode(vtmapCode);
						focusArea(vtmap, type);
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
//		addActionBar();
		super.onResume();
		getSupportActionBar().setTitle(getString(R.string.create_new_request_title));
		if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)){
			getSupportActionBar().setTitle(getString(R.string.button_change_tech));
		}
	}

	private void addActionBar() {

		ActionBar mActionBar = this.getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(FragmentLoadMapBCCS.this, Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.manager_customer_connecttion));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			onBackPressed();
			break;
		case R.id.btn_ks:
			// khao sat tren ban do

			surfaceRadius = editBKKS.getText().toString();
			Log.d("surfaceRadius", surfaceRadius);

			if (serviceID != null || !serviceID.isEmpty()) {
				if (!lat.equals("0.0") && !lon.equals("0.0")) {
					checkRadius();
				} else {
					CommonActivity.DoNotLocation(FragmentLoadMapBCCS.this);
				}
			} else {

				Toast.makeText(this, getResources().getString(R.string.checkserviceType), Toast.LENGTH_SHORT).show();

			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public void checkRadius() {
		OptionSetValueDTO optionTech = (OptionSetValueDTO) spinner_congnghe.getSelectedItem();
		if (optionTech == null) {
			CommonActivity
					.createAlertDialog(this, this.getString(R.string.checkinfra), this.getString(R.string.app_name))
					.show();
			return;
		}

		if (surfaceRadius == null || surfaceRadius.isEmpty()) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.checksufaceradius),
					Toast.LENGTH_SHORT).show();
		} else {
			if (Long.parseLong(surfaceRadius) > 500) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.checkradius2000),
						Toast.LENGTH_SHORT).show();
			} else {
				if (CommonActivity.isNetworkConnected(this) == true) {
					// == run asyntask
					if (lisSurveyOnlines.size() > 0) {
						lisSurveyOnlines.clear();
						mapview.refresh();
					}
					GetSurveyOnlineForBccs2Asyn getListSurfaceOnlineAsyn = new GetSurveyOnlineForBccs2Asyn(this);
					getListSurfaceOnlineAsyn.execute();
				} else {
					CommonActivity.createAlertDialog(this, getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name)).show();
				}
			}
		}

	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FragmentLoadMapBCCS.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	};

	// asyntask SurfaceOnline
	public class GetSurveyOnlineForBccs2Asyn extends AsyncTask<Void, Void, ArrayList<ResultSurveyOnlineForBccs2Form>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ArrayList<MarkerOptions> arrMarkerOptions = new ArrayList<MarkerOptions>();

		public GetSurveyOnlineForBccs2Asyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ResultSurveyOnlineForBccs2Form> doInBackground(Void... params) {
			return getListSurfaceOnline();
		}

		@Override
		protected void onPostExecute(ArrayList<ResultSurveyOnlineForBccs2Form> result) {
			// check errorcode
			progress.dismiss();
			CommonActivity.hideKeyboard(editBKKS, context);
			if (errorCode.equals("0")) {

				if (result != null && result.size() > 0) {
					Log.d("getListSurfaceOnlineAsyn", "co du lieu");
					lisSurveyOnlines = result;
					mapview.setCenter(new LatLng(result.get(0).getLat(), result.get(0).getLng()));
				} else {

					CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
							getResources().getString(R.string.no_data), getResources().getString(R.string.app_name))
							.show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					CommonActivity.BackFromLogin(FragmentLoadMapBCCS.this, description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this, description,
							getResources().getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		private ArrayList<ResultSurveyOnlineForBccs2Form> getListSurfaceOnline() {
			ArrayList<ResultSurveyOnlineForBccs2Form> lisSurveyOnlines = new ArrayList<ResultSurveyOnlineForBccs2Form>();

			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getSurveyOnlineForBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSurveyOnlineForBccs2>");
				rawData.append("<input>");

				HashMap<String, String> param = new HashMap<String, String>();

				// ===add token ===
				param.put("token", Session.getToken());

				// == add maxResult = 5 ==
				param.put("maxResult", "10");

				// == add surveyOnlineForm
				HashMap<String, String> rawDataItem = new HashMap<String, String>();

				rawDataItem.put("maxResult", "10");
				OptionSetValueDTO opTech = (OptionSetValueDTO) spinner_congnghe.getSelectedItem();

				if (opTech != null) {
					
//					public static final String IN_FRATYPE_CCN = "CCN";// cong nghe cap dong =>1
//					public static final String IN_FRATYPE_FCN = "FCN";// cong nghe cap quang =>3
//					public static final String IN_FRATYPE_CATV = "CATV";// cong nghe cap dong truc, truyen hinh cap =>2
//					public static final String IN_FRATYPE_GPON = "GPON";// cong nghe bang rong co dinh gpon =>4
					String infraType = "";
					if("1".equals(opTech.getValue())){
						infraType = Constant.IN_FRATYPE_CCN;
					}else if("2".equals(opTech.getValue())){
						infraType = Constant.IN_FRATYPE_CATV;
					}else if("3".equals(opTech.getValue())){
						infraType = Constant.IN_FRATYPE_FCN;
					}else if("4".equals(opTech.getValue())){
						infraType = Constant.IN_FRATYPE_GPON;
					}
					rawDataItem.put("infraType", infraType);
				}

				if (lat != null || !lat.isEmpty()) {
					rawDataItem.put("lat", lat);
				}
				if (lon != null || !lon.isEmpty()) {
					rawDataItem.put("lng", lon);
				}
				if (serviceID != null || !serviceID.isEmpty()) {
					rawDataItem.put("serviceType", serviceID);
				}

				if (surfaceRadius != null || !surfaceRadius.isEmpty()) {
					rawDataItem.put("surveyRadius", surfaceRadius);
				}

				if (isFTTB != null && !isFTTB.isEmpty()) {

					rawDataItem.put("isFttb", isFTTB);
				}

				param.put("surveyOnlineForBccs2Form", input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));
				rawData.append("</input>");
				rawData.append("</ws:getSurveyOnlineForBccs2>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentLoadMapBCCS.this,
						"mbccs_surveyOnline");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ====== parse xml ============
				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
						lisSurveyOnlines = parseOuput.getArrResultSurveyOnline();

						if (lisSurveyOnlines != null && lisSurveyOnlines.size() > 0) {
							for (ResultSurveyOnlineForBccs2Form item : lisSurveyOnlines) {
								item.setResult("OK");
								MarkerOptions markerOptions = new MarkerOptions()
										.icon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_location))
										.position(new LatLng(item.getLat(), item.getLng())).title(item.getAddress())
										.description(item.getDeptName());

								Marker marker = mapview.addMarker(markerOptions);
								marker.setUserData(item);
							}
						}

				}

			} catch (Exception e) {

				Log.d("getListSurfaceOnlineAsyn", e.toString());

			}

			return lisSurveyOnlines;
		}

	}
	// lay ma tinh/thanhpho
	private void initProvince() {
		try {
			GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
			dal.open();
			arrProvince = dal.getLstProvince();
			dal.close();
		} catch (Exception ex) {
			Log.e("initProvince", ex.toString());
		}
	}
	// lay huyen/quan theo tinh
	private void initDistrict(String province) {
		try {
			GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
			dal.open();
			arrDistrict = dal.getLstDistrict(province);
			dal.close();
		} catch (Exception ex) {
			Log.e("initDistrict", ex.toString());
		}
	}

	// lay phuong/xa theo tinh,qhuyen
	private void initPrecinct(String province, String district) {
		try {
			GetAreaDal dal = new GetAreaDal(FragmentLoadMapBCCS.this);
			dal.open();
			arrPrecinct = dal.getLstPrecinct(province, district);
			dal.close();
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
		}
	}

	private void focusArea(String vtmap, int type) {

		AdminService admin1 = new AdminService();
		admin1.setReturnType(AdminReturnType.BOUND.toInt());
		if (type == 0) {
			// Tinh
			admin1.setLevelType(AdminLevelType.PROVINCE);
		}
		if (type == 1) {
			// huyen
			admin1.setLevelType(AdminLevelType.DISTRICT);
		}
		if (type == 2) {
			// xa
			admin1.setLevelType(AdminLevelType.COMMUNE);
		}
		admin1.getFeature(vtmap, new AdminServiceListener() {

			@Override
			public void onAdminServicePreProcess(AdminService arg0) {

			}

			@Override
			public void onAdminServiceCompleted(AdminServiceResult arg0) {
				if (arg0 != null) {
					if (arg0.getItems() != null && arg0.getItems().size() > 0) {
						LatLng latlon = arg0.getItem(0).getBoundary().getCenter();
						if (latlon != null) {
							lat = latlon.getLatitude() + "";
							lon = latlon.getLongitude() + "";
							Log.d("lattt", latlon.getLatitude() + "");
							Log.d("longgggggg", latlon.getLongitude() + "");
							if (lat.equals("0") || lon.equals("0")) {
								CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
										getResources().getString(R.string.checkareadb),
										getResources().getString(R.string.app_name)).show();
							} else {
								MarkerOptions markerOptions = new MarkerOptions()
										.icon(BitmapFactory.decodeResource(getResources(), R.drawable.iconmap))
										.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));
								if (markerinit != null) {
									mapview.removeMarker(markerinit);
									mapview.refresh();
								}

								markerinit = mapview.addMarker(markerOptions);
								markerinit.setDraggable(true);
								mapview.setCenter(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));
							}
						} else {
							CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
									getResources().getString(R.string.checkareadb),
									getResources().getString(R.string.app_name)).show();
						}

					} else {
						CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
								getResources().getString(R.string.checkareadb),
								getResources().getString(R.string.app_name)).show();
					}
				} else {
					CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
							getResources().getString(R.string.checkareadb), getResources().getString(R.string.app_name))
							.show();
				}

			}
		});
	}

	// show chi tiet thong tin ha tang
	private void showDialogInfratructer(String techName,
			final ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form) {

		surveyOnlineInit = resultSurveyOnlineForBccs2Form;

		final Dialog dialog = new Dialog(FragmentLoadMapBCCS.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_dialog);
		TextView tvconnectorCode = (TextView) dialog.findViewById(R.id.tvconnectorCode);
		tvconnectorCode.setText(resultSurveyOnlineForBccs2Form.getConnectorCode());
		TextView txttnketcuoi = (TextView) dialog.findViewById(R.id.txttnketcuoi);
		TextView txtTaiNguyenThietBi = (TextView) dialog.findViewById(R.id.txtTaiNguyenThietBi);
		TextView txtMaTram = (TextView) dialog.findViewById(R.id.txtMaTram);
		TextView txtDonViQuanLyKetCuoi = (TextView) dialog.findViewById(R.id.txtDonViQuanLyKetCuoi);

		TextView txtsoluongportrong = (TextView) dialog.findViewById(R.id.txtsoluongportrong);
		TextView txtsluongportdangtkmoi = (TextView) dialog.findViewById(R.id.txtsluongportdangtkmoi);

		TextView txtPortRF = (TextView) dialog.findViewById(R.id.txtPortRF);
		txtPortRF.setText(resultSurveyOnlineForBccs2Form.getPortCode());
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);

		// thong tin quan ly nha tram
		txtnamequanly = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtChucVu = (TextView) dialog.findViewById(R.id.txtChucVu);
		txtdtlienhe = (TextView) dialog.findViewById(R.id.txtdtlienhe);

		// thong tin vendor
		lnVendor = (LinearLayout) dialog.findViewById(R.id.lnVendor);
		txtloaivendor = (TextView) dialog.findViewById(R.id.txtloaivendor);
		if (resultSurveyOnlineForBccs2Form != null
				&& resultSurveyOnlineForBccs2Form.getResultGetVendorByConnectorForm() != null) {
			lnVendor.setVisibility(View.VISIBLE);
			txtloaivendor.setText(resultSurveyOnlineForBccs2Form.getResultGetVendorByConnectorForm().getVendorCode());
		} else {
			lnVendor.setVisibility(View.GONE);
			txtloaivendor.setText("");
		}

		TextView txtCongNghe = (TextView) dialog.findViewById(R.id.txtCongNghe);
		switch (Integer.parseInt(resultSurveyOnlineForBccs2Form.getInfraType())) {
		case 1:
			// cap dong
			txtCongNghe.setText(this.getString(R.string.capdong));
			break;
		case 2:
			txtCongNghe.setText(this.getString(R.string.capdongtruc));
			break;
		case 3:
			txtCongNghe.setText(this.getString(R.string.capquang));
			break;
		case 4:
			txtCongNghe.setText(this.getString(R.string.quanggpon));
			break;
		default:
			break;
		}
		TextView txtDiaChiKetCuoi = (TextView) dialog.findViewById(R.id.txtDiaChiKetCuoi);
		if (resultSurveyOnlineForBccs2Form != null
				&& !CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form.getAddress())) {
			txtDiaChiKetCuoi.setText(resultSurveyOnlineForBccs2Form.getAddress());
		}

		LinearLayout lncapquang = (LinearLayout) dialog.findViewById(R.id.lnQuang);
		LinearLayout lnquangGPON = (LinearLayout) dialog.findViewById(R.id.lnGPON);
		if ("4".equals(resultSurveyOnlineForBccs2Form.getInfraType())) {
			lnquangGPON.setVisibility(View.VISIBLE);
			lncapquang.setVisibility(View.GONE);
			txtsoluongportrong.setText(resultSurveyOnlineForBccs2Form.getGponConnectorFreePort() + "");
			txtsluongportdangtkmoi.setText(resultSurveyOnlineForBccs2Form.getGponConnectorLockPort() + "");
		} else {
			lnquangGPON.setVisibility(View.GONE);
			lncapquang.setVisibility(View.VISIBLE);
			txttnketcuoi.setText(resultSurveyOnlineForBccs2Form.getResourceConnector());
			txtTaiNguyenThietBi.setText(resultSurveyOnlineForBccs2Form.getResourceDevice());
		}

		txtcheck.setText(resultSurveyOnlineForBccs2Form.getResult());
		txtMaTram.setText(resultSurveyOnlineForBccs2Form.getStationCode());
		txtDonViQuanLyKetCuoi.setText(resultSurveyOnlineForBccs2Form.getDeptName());

		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);
		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// lay thong tin nguoi quan ly o day
				
				GetListStaffInfraConnector getListStaffInfraConnector = new GetListStaffInfraConnector(FragmentLoadMapBCCS.this);
				getListStaffInfraConnector.execute(resultSurveyOnlineForBccs2Form.getConnectorCode());
			}
		});

		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (surveyOnlineInit.getConnectorCode() != null && !surveyOnlineInit.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty() && txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (dialog != null) {
							dialog.dismiss();
						}
						Intent data = new Intent();
						Bundle mBundle = new Bundle();
						surveyOnlineInit.setResult("OK");
						surveyOnlineInit.setSurveyRadius(editBKKS.getText().toString());
						mBundle.putSerializable("ServeyKey", surveyOnlineInit);
						mBundle.putString("checkConnectorCode", "1000");
						data.putExtras(mBundle);
						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(FragmentLoadMapBCCS.this, getResources().getString(R.string.checkNotOk),
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				onBackPressed();
			}
		});
		
		
		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);
		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienhe.getText().toString() != null
						&& !txtdtlienhe.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienhe.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(FragmentLoadMapBCCS.this,
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		dialog.show();

	}

	// ham lay thong tin vendor
	private class GetVendorByConnectorAsyn extends AsyncTask<String, Void, ResultGetVendorByConnectorForm> {

		private String errorCode;
		private String description;
		private Context context;
		private ProgressDialog progress;

		public GetVendorByConnectorAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ResultGetVendorByConnectorForm doInBackground(String... params) {
			return getVendorByConnector(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ResultGetVendorByConnectorForm result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null) {
					resultGetVendorByConnectorForm = result;

					OptionSetValueDTO optionSetValueDTO = (OptionSetValueDTO) spinner_congnghe.getSelectedItem();

					surveyOnlineSelect.setResultGetVendorByConnectorForm(result);

					showDialogInfratructer(optionSetValueDTO.getName(), surveyOnlineSelect);

				} else {
					CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
							FragmentLoadMapBCCS.this.getString(R.string.notubinfra),
							FragmentLoadMapBCCS.this.getString(R.string.app_name)).show();
				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

					CommonActivity.BackFromLogin(FragmentLoadMapBCCS.this, description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ResultGetVendorByConnectorForm getVendorByConnector(String connectorId, String connectorCode,
				String accountGline) {
			String original = null;
			ResultGetVendorByConnectorForm resultGetVendorByConnectorForm = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getVendorByConnector");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getVendorByConnector>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<connectorId>" + connectorId);
				rawData.append("</connectorId>");
				rawData.append("<connectorCode>" + connectorCode);
				rawData.append("</connectorCode>");
				rawData.append("<glineAccount>" + accountGline);
				rawData.append("</glineAccount>");
				rawData.append("</input>");
				rawData.append("</ws:getVendorByConnector>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentLoadMapBCCS.this,
						"mbccs_getVendorByConnector");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					resultGetVendorByConnectorForm = parseOuput.getResultGetVendorByConnectorForm();
				}

				return resultGetVendorByConnectorForm;

			} catch (Exception e) {
				Log.d("getVendorByConnector", e.toString());
			}
			return resultGetVendorByConnectorForm;
		}
	}

	private class GetListTechnologyAsyn extends AsyncTask<String, Void, ArrayList<OptionSetValueDTO>> {

		private Context context;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";

		public GetListTechnologyAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<OptionSetValueDTO> doInBackground(String... params) {
			return getListTechnology(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<OptionSetValueDTO> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null) {
					if (result.size() > 0) {
						if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)){
							result = Utils.filterTech(FragmentLoadMapBCCS.this, result, techSelect);
						}
						Utils.setDataSpinnerOptionSet(FragmentLoadMapBCCS.this, result, spinner_congnghe);
					} else {
						if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)){
							result = Utils.filterTech(FragmentLoadMapBCCS.this, result, techSelect);
						}
						Utils.setDataSpinnerOptionSet(FragmentLoadMapBCCS.this, result, spinner_congnghe);

						if (description != null && !description.isEmpty()) {
							Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this, description,
									getResources().getString(R.string.app_name));
							dialog.show();
						} else {
							Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
									getResources().getString(R.string.no_data),
									getResources().getString(R.string.app_name));
							dialog.show();
						}
					}
				} else {
					if(!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)){
						result = Utils.filterTech(FragmentLoadMapBCCS.this, result, techSelect);
					}
					Utils.setDataSpinnerOptionSet(FragmentLoadMapBCCS.this, result, spinner_congnghe);
					if (description != null && !description.isEmpty()) {
						CommonActivity.BackFromLogin(FragmentLoadMapBCCS.this, description,";cm.connect_sub_bccs2;");
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this,
								getResources().getString(R.string.no_data),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<OptionSetValueDTO> getListTechnology(String serviceType) {
			String original = null;

			ArrayList<OptionSetValueDTO> arrayList = new ArrayList<OptionSetValueDTO>();

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListTechnology");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListTechnology>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				
				
				if(lstProductCatalog != null && lstProductCatalog.size() > 0){
					for (ProductCatalogDTO item : lstProductCatalog) {
						rawData.append("<telService>" + item.getTelServiceAlias());
						rawData.append("</telService>");
					}
				}
				

				rawData.append("</input>");
				rawData.append("</ws:getListTechnology>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentLoadMapBCCS.this,
						"mbccs_getListTechnology");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					arrayList = parseOuput.getLstOptionSetValueDTOs();
				}
				return arrayList;

			} catch (Exception e) {
				Log.d("getListTechnology", e.toString());
			}
			return arrayList;
		}

	}
	
	
	// lay thong tin quan ly tu ma ket cuoi
	public class GetListStaffInfraConnector extends AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListStaffInfraConnector(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getListStaffInfraConnector(arg0[0]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null && !result.getFullname().isEmpty()) {
					txtnamequanly.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienhe.setText(result.getPhone());
					}
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					CommonActivity.BackFromLogin(FragmentLoadMapBCCS.this, description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentLoadMapBCCS.this, description,
							getResources().getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getListStaffInfraConnector(String cableCode) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStaffInfraConnector");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStaffInfraConnector>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<connectorCode>" + cableCode);
				rawData.append("</connectorCode>");
//				rawData.append("<infraType>" + techchologyID);
//				rawData.append("</infraType>");
				rawData.append("</input>");
				rawData.append("</ws:getListStaffInfraConnector>");
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentLoadMapBCCS.this,
						"mbccs_getListStaffInfraConnector");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ====== parse xml ============
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstSysUsersBO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String fullname = parse.getValue(e1, "fullname");
						Log.d("fullname", fullname);
						sysUsersBO.setFullname(fullname);
						String phone = parse.getValue(e1, "phone");
						Log.d("phone", phone);
						sysUsersBO.setPhone(phone);
					}
				}

			} catch (Exception e) {
				Log.d("GetUserFromCableBox", e.toString());
			}

			return sysUsersBO;

		}

	}

}
