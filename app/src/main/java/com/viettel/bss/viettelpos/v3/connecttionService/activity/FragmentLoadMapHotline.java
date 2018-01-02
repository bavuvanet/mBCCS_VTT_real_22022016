package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetConectorCodeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SysUsersBO;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.SurveyOnline;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
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

public class FragmentLoadMapHotline extends GPSTracker implements OnClickListener {

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
	ArrayList<SurveyOnline> lisSurveyOnlines = new ArrayList<SurveyOnline>();
	SurveyOnline surveyOnlineSelect;

	public SurveyOnline surveyOnlineInit = new SurveyOnline();
	public SysUsersBO sysUsersBOInit = new SysUsersBO();

	private String msType = "";
	public TextView txtnamequanlyADSL;
	public EditText txtdtlienheADSL;

	public TextView txtnamequanlyFTTH;
	public EditText txtdtlienheFTTH;

	public TextView txtnamequanlyPSTN;
	public EditText txtdtlienhePSTN;

	public TextView txtnamequanlyTHC;
	public EditText txtdtlienheTHC;

	public TextView txtnamequanlyGPON;
	public EditText txtdtlienheGPON;

	private ArrayList<SurveyOnline> arrSurveyOnlines = new ArrayList<SurveyOnline>();

	private Dialog dialogGPON;
	private Dialog dialogA;
	private Dialog dialogP;
	private Dialog dialogF;
	private Dialog dialogT;

	private String isFTTB = "";
	private String groupProductId = "";
	private String mstNoRf = "";

	private Spinner spinner_loaiks;
	private LinearLayout lndiaban;
	private EditText edtprovince, edtdistrict, edtprecinct;

	private String province = "";
	private String district = "";
	private String precinct = "";
	private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();

	private String vtmapCode = "";

	private Marker markerinit = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addActionBar();
		setContentView(R.layout.connection_layout_map);
		// get bunder
		// gpsTracker = new GPSTracker(FragmentLoadMapHotline.this);
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			serviceID = mBundle.getString("serviceType");
			Log.d("serviceID", serviceID);

			techchologyID = mBundle.getString("techologyKey", "");
			Log.d("techchologyID", techchologyID);
			if (!techchologyID.equals("") && techchologyID.equals("4")) {
				parValueTechology = "GPON";
			}

			msType = mBundle.getString("msTypeKey", "");
			isFTTB = mBundle.getString("isFTTBKey", "");
			Log.d("isFTTBBBBBBBBBBBBBBBBBBB", isFTTB);
			groupProductId = mBundle.getString("groupProductIdKey", "");
			mstNoRf = mBundle.getString("mstNoRfKey", "");
		}

		Location location = CommonActivity.findMyLocation(FragmentLoadMapHotline.this);
		if (location != null) {

			lat = location.getX();
			lon = location.getY();
		} else {
			lat = "21.0287592";
			lon = "105.8501718";
		}

		List<Spin> lstSpin = new ArrayList<Spin>();
		lstSpin.add(new Spin("1", this.getString(R.string.kstheotoado)));
		lstSpin.add(new Spin("2", this.getString(R.string.kstheodiaban)));
		spinner_loaiks = (Spinner) findViewById(R.id.spinner_loaiks);
		Utils.setDataSpinner(FragmentLoadMapHotline.this, lstSpin, spinner_loaiks);
		lndiaban = (LinearLayout) findViewById(R.id.lndiaban);
		edtprovince = (EditText) findViewById(R.id.edtprovince);
		edtprovince.setEnabled(false);

		province = Session.province;
		district = Session.district;
		if (!CommonActivity.isNullOrEmpty(province)) {
			initDistrict(province);
			try {
				GetAreaDal dal = new GetAreaDal(FragmentLoadMapHotline.this);
				dal.open();
				edtprovince.setText(dal.getNameProvince(province));
				dal.close();
			} catch (Exception e) {
			}
		}

		edtdistrict = (EditText) findViewById(R.id.edtdistrict);
		if (!CommonActivity.isNullOrEmpty(province)
				&& !CommonActivity.isNullOrEmpty(district)) {
			initPrecinct(province, district);
			try {
				GetAreaDal dal = new GetAreaDal(FragmentLoadMapHotline.this);
				dal.open();
				edtdistrict.setText(dal.getNameDistrict(province, district));
				dal.close();
			} catch (Exception e) {
			}
		}
		edtdistrict.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FragmentLoadMapHotline.this,
						FragmentSearchLocation.class);
				intent.putExtra("arrDistrictKey", arrDistrict);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "2");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 107);

			}
		});
		edtprecinct = (EditText) findViewById(R.id.edtprecinct);
		edtprecinct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FragmentLoadMapHotline.this,
						FragmentSearchLocation.class);
				intent.putExtra("arrPrecinctKey", arrPrecinct);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "3");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 108);
			}
		});
		spinner_loaiks.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				if (item != null) {
					if ("1".equals(item.getId())) {
						lndiaban.setVisibility(View.GONE);
						if (lat.equals("0") || lon.equals("0")) {
							CommonActivity
									.createAlertDialog(
											FragmentLoadMapHotline.this,
											getResources().getString(
													R.string.checkgps),
											getResources().getString(
													R.string.app_name)).show();
						} else {
							MarkerOptions markerOptions = new MarkerOptions()
									.icon(BitmapFactory.decodeResource(
											getResources(), R.drawable.iconmap))
									.position(
											new LatLng(Double.parseDouble(lat),
													Double.parseDouble(lon)));
							markerinit = mapview.addMarker(markerOptions);
							markerinit.setDraggable(true);
							mapview.setCenter(new LatLng(Double
									.parseDouble(lat), Double.parseDouble(lon)));
						}

					} else {
						
						if(markerinit != null){
							mapview.removeMarker(markerinit);
							mapview.refresh();
						}
						
						
						lndiaban.setVisibility(View.VISIBLE);
						district = Session.district;
						// TODO XU LY CHO NAY
						if (!CommonActivity.isNullOrEmpty(province)
								&& !CommonActivity.isNullOrEmpty(district)) {
							initPrecinct(province, district);
							try {
								GetAreaDal dal = new GetAreaDal(
										FragmentLoadMapHotline.this);
								dal.open();
								edtdistrict.setText(dal.getNameDistrict(
										province, district));
								dal.close();
							} catch (Exception e) {
							}
						}
						edtprecinct.setText("");
						precinct = "";
//						int type = 1;
//						try {
//							GetAreaDal dal = new GetAreaDal(
//									FragmentLoadMapHotline.this);
//							dal.open();
//							vtmapCode = dal.getmAPVTMap(province + district);
//							if (CommonActivity.isNullOrEmpty(vtmapCode)) {
//								vtmapCode = dal.getmAPVTMap(province);
//								type = 0;
//							}
//							dal.close();
//						} catch (Exception e) {
//							Log.d(FragmentLoadMapHotline.class.getSimpleName(),
//									e.toString());
//						}
//						if (!CommonActivity.isNullOrEmpty(vtmapCode)) {
//							String vtmap = CommonActivity
//									.checkVTmapCode(vtmapCode);
//							focusArea(vtmap, type);
//						}
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
		// config ip map viettelmaps.vn
//		AppInfo.setServerAddress("http", "10.60.106.250", 80);
		// config ip map viettelmaps.vn
//		AppInfo.setServerAddress("http", "203.190.170.250", 80);
		// init map
		MapConfig.changeSRS(SRSType.SRS_4326);

		mapview.setZoom(16);

		mapview.setGPSControlEnabled(true);

		if (serviceID.equals("A") || serviceID.equals("P")) {
			editBKKS.setText("500");
		}
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
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(R.string.movetoado) + " X = "
								+ lat + " Y = " + lon, Toast.LENGTH_SHORT)
						.show();
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
				surveyOnlineSelect = (SurveyOnline) marker.getUserData();
				if (arrSurveyOnlines.size() > 0) {
					arrSurveyOnlines.clear();
				}
				if (surveyOnlineSelect != null) {
					if (surveyOnlineSelect.getDistance() != null) {
						for (SurveyOnline surveyOnline : lisSurveyOnlines) {
							if (surveyOnline.getDistance().equals(
									surveyOnlineSelect.getDistance())) {
								arrSurveyOnlines.add(surveyOnline);
							}
						}

					}
					if (!parValueTechology.equals("")
							&& parValueTechology.equals("GPON")) {

						if (arrSurveyOnlines.size() > 1) {
							// show popup
							dialogGPON = new Dialog(FragmentLoadMapHotline.this);
							dialogGPON
									.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialogGPON
									.setContentView(R.layout.connection_layout_lst_conectorcode);
							ListView lstpstn = (ListView) dialogGPON
									.findViewById(R.id.lstpstn);

							GetConectorCodeAdapter adapCodeAdapter = new GetConectorCodeAdapter(
									arrSurveyOnlines, FragmentLoadMapHotline.this);
							lstpstn.setAdapter(adapCodeAdapter);
							lstpstn.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// dialog.dismiss();
									showPopupGPON(arrSurveyOnlines.get(arg2));
								}
							});

							Button btnhuy = (Button) dialogGPON
									.findViewById(R.id.btnhuy);
							btnhuy.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									dialogGPON.dismiss();
								}
							});
							dialogGPON.show();

						} else {
							showPopupGPON(surveyOnlineSelect);
						}

					} else {
						if (serviceID.equals("F") || serviceID.equals("L")
								|| serviceID.equals("N")) {

							if (arrSurveyOnlines.size() > 1) {

								dialogF = new Dialog(FragmentLoadMapHotline.this);
								dialogF.requestWindowFeature(Window.FEATURE_NO_TITLE);
								dialogF.setContentView(R.layout.connection_layout_lst_conectorcode);
								ListView lstpstn = (ListView) dialogF
										.findViewById(R.id.lstpstn);

								GetConectorCodeAdapter adapCodeAdapter = new GetConectorCodeAdapter(
										arrSurveyOnlines, FragmentLoadMapHotline.this);
								lstpstn.setAdapter(adapCodeAdapter);
								lstpstn.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// dialogF.dismiss();
										showPopupFTTH(arrSurveyOnlines
												.get(arg2));
									}
								});

								Button btnhuy = (Button) dialogF
										.findViewById(R.id.btnhuy);
								btnhuy.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										dialogF.dismiss();
									}
								});
								dialogF.show();

							} else {
								showPopupFTTH(surveyOnlineSelect);
							}
						}

						if (serviceID.equals("A")) {

							// show popup
							if (arrSurveyOnlines.size() > 1) {
								dialogA = new Dialog(FragmentLoadMapHotline.this);
								dialogA.requestWindowFeature(Window.FEATURE_NO_TITLE);
								dialogA.setContentView(R.layout.connection_layout_lst_conectorcode);
								ListView lstpstn = (ListView) dialogA
										.findViewById(R.id.lstpstn);

								GetConectorCodeAdapter adapCodeAdapter = new GetConectorCodeAdapter(
										arrSurveyOnlines, FragmentLoadMapHotline.this);
								lstpstn.setAdapter(adapCodeAdapter);
								lstpstn.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// dialog.dismiss();
										showPopupADSL(arrSurveyOnlines
												.get(arg2));
									}
								});

								Button btnhuy = (Button) dialogA
										.findViewById(R.id.btnhuy);
								btnhuy.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										dialogA.dismiss();
									}
								});
								dialogA.show();

							} else {
								showPopupADSL(surveyOnlineSelect);
							}

						}

						if (serviceID.equals("P")) {
							// show popup

							if (arrSurveyOnlines.size() > 1) {
								dialogP = new Dialog(FragmentLoadMapHotline.this);
								dialogP.requestWindowFeature(Window.FEATURE_NO_TITLE);
								dialogP.setContentView(R.layout.connection_layout_lst_conectorcode);
								ListView lstpstn = (ListView) dialogP
										.findViewById(R.id.lstpstn);

								GetConectorCodeAdapter adapCodeAdapter = new GetConectorCodeAdapter(
										arrSurveyOnlines, FragmentLoadMapHotline.this);
								lstpstn.setAdapter(adapCodeAdapter);
								lstpstn.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// dialog.dismiss();
										showPopupPSTN(arrSurveyOnlines
												.get(arg2));
									}
								});

								Button btnhuy = (Button) dialogP
										.findViewById(R.id.btnhuy);
								btnhuy.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										dialogP.dismiss();
									}
								});
								dialogP.show();

							} else {
								showPopupPSTN(surveyOnlineSelect);
							}

						}

						if (serviceID.equals("T") || serviceID.equals("I")
								|| serviceID.equals("U")) {

							// show popup
							if (arrSurveyOnlines.size() > 1) {
								dialogT = new Dialog(FragmentLoadMapHotline.this);
								dialogT.requestWindowFeature(Window.FEATURE_NO_TITLE);
								dialogT.setContentView(R.layout.connection_layout_lst_conectorcode);
								ListView lstpstn = (ListView) dialogT
										.findViewById(R.id.lstpstn);

								GetConectorCodeAdapter adapCodeAdapter = new GetConectorCodeAdapter(
										arrSurveyOnlines, FragmentLoadMapHotline.this);
								lstpstn.setAdapter(adapCodeAdapter);
								lstpstn.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// dialog.dismiss();
										showPopupTHC(arrSurveyOnlines.get(arg2));
									}
								});

								Button btnhuy = (Button) dialogT
										.findViewById(R.id.btnhuy);
								btnhuy.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										dialogT.dismiss();
									}
								});
								dialogT.show();

							} else {
								showPopupTHC(surveyOnlineSelect);
							}
						}
					}
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

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 107:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras()
							.getSerializable("districtKey");
					district = areaBean.getDistrict();
					initPrecinct(province, district);
					edtdistrict.setText(areaBean.getNameDistrict());
					edtprecinct.setText("");
					precinct = "";
//					int type = 1;
//					try {
//						GetAreaDal dal = new GetAreaDal(FragmentLoadMapHotline.this);
//						dal.open();
//						vtmapCode = dal.getmAPVTMap(province + district);
//						if (CommonActivity.isNullOrEmpty(vtmapCode)) {
//							vtmapCode = dal.getmAPVTMap(province);
//							type = 0;
//						}
//						dal.close();
//					} catch (Exception e) {
//						Log.d(FragmentLoadMapHotline.class.getSimpleName(),
//								e.toString());
//					}
//					if (!CommonActivity.isNullOrEmpty(vtmapCode)) {
//						String vtmap = CommonActivity.checkVTmapCode(vtmapCode);
//						focusArea(vtmap, type);
//					}
				}
				break;

			case 108:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras()
							.getSerializable("precinctKey");
					precinct = areaBean.getPrecinct();
					edtprecinct.setText(areaBean.getNamePrecint());
					int type = 2;
					try {
						GetAreaDal dal = new GetAreaDal(FragmentLoadMapHotline.this);
						dal.open();
						vtmapCode = dal.getmAPVTMap(province + district
								+ precinct);
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
						Log.d(FragmentLoadMapHotline.class.getSimpleName(),
								e.toString());
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

	// show popup pstn
	private void showPopupPSTN(SurveyOnline surveyOnline) {

		surveyOnlineInit = surveyOnline;

		final Dialog dialog = new Dialog(FragmentLoadMapHotline.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_pstn);

		final TextView txtmahopcap = (TextView) dialog
				.findViewById(R.id.txtmahopcap);
		TextView txtdiachihopcap = (TextView) dialog
				.findViewById(R.id.txtdiachihopcap);
		final TextView txttnhopcap = (TextView) dialog
				.findViewById(R.id.txttnhopcap);
		TextView txtdodaituyencap = (TextView) dialog
				.findViewById(R.id.txtdodaituyencap);
		final TextView txttnportDLU = (TextView) dialog
				.findViewById(R.id.txttnportDLU);
		final TextView txttncapgoc = (TextView) dialog
				.findViewById(R.id.txttncapgoc);

		TextView txttodoiquanly = (TextView) dialog
				.findViewById(R.id.txttodoiquanly);

		final TextView txtvendor = (TextView) dialog
				.findViewById(R.id.txtloaivendor);
		if (surveyOnline.getVendorCode() != null
				&& !surveyOnline.getVendorCode().isEmpty()) {
			txtvendor.setText(surveyOnline.getVendorCode());
		}
		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);
		txtnamequanlyPSTN = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienhePSTN = (EditText) dialog.findViewById(R.id.txtdtlienhe);

		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);
		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienhePSTN.getText().toString() != null
						&& !txtdtlienhePSTN.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienhePSTN.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(FragmentLoadMapHotline.this,
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		if (surveyOnline.getConnectorCode() != null
				&& !surveyOnline.getConnectorCode().isEmpty()) {
			txtmahopcap.setText(surveyOnline.getConnectorCode());
		}
		if (surveyOnline.getAddress() != null
				&& !surveyOnline.getAddress().isEmpty()) {
			txtdiachihopcap.setText(surveyOnline.getAddress());
		}
		if (surveyOnline.getResourceConnector() != null
				&& !surveyOnline.getResourceConnector().isEmpty()) {
			txttnhopcap.setText(surveyOnline.getResourceConnector());
		}
		if (surveyOnline.getResourceDevice() != null
				&& !surveyOnline.getResourceDevice().isEmpty()) {
			txttnportDLU.setText(surveyOnline.getResourceDevice());
		}
		if (surveyOnline.getCableLength() != null
				&& !surveyOnline.getCableLength().isEmpty()) {
			txtdodaituyencap.setText(Html.fromHtml(surveyOnline
					.getCableLength() + " <font color=\"red\"> m" + "</font>"));
		}
		if (surveyOnline.getDeptName() != null
				&& !surveyOnline.getDeptName().isEmpty()) {
			txttodoiquanly.setText(surveyOnline.getDeptName());
		}
		if (surveyOnline.getResourceRootCable() != null
				&& !surveyOnline.getResourceRootCable().isEmpty()) {
			txttncapgoc.setText(surveyOnline.getResourceRootCable());
		}

		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (txtmahopcap.getText() != null
						&& !txtmahopcap.getText().toString().isEmpty()) {
					if (CommonActivity.isNetworkConnected(FragmentLoadMapHotline.this) == true) {
						GetUserFromCableBoxPSTN getUserFromCableBoxAsyn = new GetUserFromCableBoxPSTN(
								FragmentLoadMapHotline.this);
						getUserFromCableBoxAsyn.execute(txtmahopcap.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
					}
				}

				if ((txttnportDLU.getText().toString() != null && !txttnportDLU
						.getText().toString().isEmpty())
						&& (txttnhopcap.getText().toString() != null && !txttnhopcap
								.getText().toString().isEmpty())) {
					String checktntbi = txttnportDLU.getText().toString();
					String checktnhopcap = txttnhopcap.getText().toString();
					// String checktncapgoc = txttncapgoc.getText().toString();
					String[] splitntb = checktntbi.split("/", 2);
					String[] splittnhc = checktnhopcap.split("/", 2);
					// String[] splittncapgoc = checktncapgoc.split("/", 2);
					if (splitntb.length == 2
							&& (Integer.parseInt(splitntb[0]) < Integer
									.parseInt(splitntb[1]))) {
						if (splittnhc.length == 2
								&& (Integer.parseInt(splittnhc[0]) < Integer
										.parseInt(splittnhc[1]))) {
							// if (splittncapgoc.length == 2
							// && (Integer.parseInt(splittncapgoc[0]) < Integer
							// .parseInt(splittncapgoc[1]))) {
							// if(txtvendor.getText() != null &&
							// !txtvendor.getText().toString().isEmpty()){
							txtcheck.setText("OK");
							// }else{
							// txtcheck.setText("NOK");
							// Toast.makeText(
							// FragmentLoadMapHotline.this,
							// getResources().getString(
							// R.string.thieuvendor),
							// Toast.LENGTH_SHORT).show();
							// }

							// ======call webservice =============

							// } else {
							// txtcheck.setText("NOK");
							// Toast.makeText(
							// FragmentLoadMapHotline.this,
							// getResources().getString(
							// R.string.checktncapgoc),
							// Toast.LENGTH_SHORT).show();
							// }

						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									FragmentLoadMapHotline.this,
									getResources().getString(
											R.string.checktnhopcap),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						txtcheck.setText("NOK");
						Toast.makeText(
								FragmentLoadMapHotline.this,
								getResources().getString(
										R.string.checktnportDLU),
								Toast.LENGTH_SHORT).show();
					}

				} else {
					txtcheck.setText("NOK");
				}

			}
		});

		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (surveyOnlineInit.getConnectorCode() != null
						&& !surveyOnlineInit.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (dialogP != null) {
							dialogP.dismiss();
						}
						Intent data = new Intent();
						Bundle mBundle = new Bundle();
						surveyOnlineInit.setSurfaceRadius(editBKKS.getText()
								.toString());
						mBundle.putSerializable("ServeyKey", surveyOnlineInit);
						mBundle.putString("checkConnectorCode", "1000");
						data.putExtras(mBundle);

						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checkNotOk),
								Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				onBackPressed();
			}
		});

		dialog.show();

	}

	// show popup adsl
	private void showPopupADSL(SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(FragmentLoadMapHotline.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_adsl);

		surveyOnlineInit = surveyOnline;

		final TextView txtmahopcap = (TextView) dialog
				.findViewById(R.id.txtmahopcap);
		TextView txtdiachihopcap = (TextView) dialog
				.findViewById(R.id.txtdiachihopcap);
		final TextView txttnhopcap = (TextView) dialog
				.findViewById(R.id.txttnhopcap);
		TextView txtdodaituyencap = (TextView) dialog
				.findViewById(R.id.txtdodaituyencap);
		final TextView txttnport = (TextView) dialog
				.findViewById(R.id.txttnport);
		final TextView txttncapgoc = (TextView) dialog
				.findViewById(R.id.txttncapgoc);

		TextView txttodoiquanly = (TextView) dialog
				.findViewById(R.id.txttodoiquanly);
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);
		txtnamequanlyADSL = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienheADSL = (EditText) dialog.findViewById(R.id.txtdtlienhe);
		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);

		final TextView txtvendor = (TextView) dialog
				.findViewById(R.id.txtloaivendor);
		if (surveyOnline.getVendorCode() != null
				&& !surveyOnline.getVendorCode().isEmpty()) {
			txtvendor.setText(surveyOnline.getVendorCode());
		}

		if (surveyOnline.getConnectorCode() != null
				&& !surveyOnline.getConnectorCode().isEmpty()) {
			txtmahopcap.setText(surveyOnline.getConnectorCode());
		}
		if (surveyOnline.getAddress() != null
				&& !surveyOnline.getAddress().isEmpty()) {
			txtdiachihopcap.setText(surveyOnline.getAddress());
		}
		if (surveyOnline.getResourceConnector() != null
				&& !surveyOnline.getResourceConnector().isEmpty()) {
			txttnhopcap.setText(surveyOnline.getResourceConnector());
		}
		if (surveyOnline.getResourceDevice() != null
				&& !surveyOnline.getResourceDevice().isEmpty()) {
			txttnport.setText(surveyOnline.getResourceDevice());
		}
		if (surveyOnline.getCableLength() != null
				&& !surveyOnline.getCableLength().isEmpty()) {
			txtdodaituyencap.setText(Html.fromHtml(surveyOnline
					.getCableLength() + " <font color=\"red\"> m" + "</font>"));
		}
		if (surveyOnline.getDeptName() != null
				&& !surveyOnline.getDeptName().isEmpty()) {
			txttodoiquanly.setText(surveyOnline.getDeptName());
		}
		if (surveyOnline.getResourceRootCable() != null
				&& !surveyOnline.getResourceRootCable().isEmpty()) {
			txttncapgoc.setText(surveyOnline.getResourceRootCable());
		}
		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);
		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienheADSL.getText().toString() != null
						&& !txtdtlienheADSL.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienheADSL.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(FragmentLoadMapHotline.this,
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (surveyOnlineInit.getConnectorCode() != null
						&& !surveyOnlineInit.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (dialogA != null) {
							dialogA.dismiss();
						}
						Intent data = new Intent();
						Bundle mBundle = new Bundle();
						surveyOnlineInit.setSurfaceRadius(editBKKS.getText()
								.toString());
						mBundle.putSerializable("ServeyKey", surveyOnlineInit);
						mBundle.putString("checkConnectorCode", "1000");
						data.putExtras(mBundle);
						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checkNotOk),
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (surveyOnlineInit != null) {
					surveyOnlineInit = null;
				}
				onBackPressed();
			}
		});

		// check tn
		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (txtmahopcap.getText() != null
						&& !txtmahopcap.getText().toString().isEmpty()) {
					// ======call webservice =============
					if (CommonActivity.isNetworkConnected(FragmentLoadMapHotline.this) == true) {
						GetUserFromCableBoxADSL getUserFromCableBoxAsyn = new GetUserFromCableBoxADSL(
								FragmentLoadMapHotline.this);
						getUserFromCableBoxAsyn.execute(txtmahopcap.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
					}
				}

				if ((txttnport.getText().toString() != null && !txttnport
						.getText().toString().isEmpty())
						&& (txttnhopcap.getText().toString() != null && !txttnhopcap
								.getText().toString().isEmpty())) {
					String checktntbi = txttnport.getText().toString();
					String checktnhopcap = txttnhopcap.getText().toString();
					String checktncapgoc = txttncapgoc.getText().toString();
					String[] splitntb = checktntbi.split("/", 2);
					String[] splittnhc = checktnhopcap.split("/", 2);
					// String[] splittncapgoc = checktncapgoc.split("/", 2);
					if (splitntb.length == 2
							&& (Integer.parseInt(splitntb[0]) < Integer
									.parseInt(splitntb[1]))) {
						if (splittnhc.length == 2
								&& (Integer.parseInt(splittnhc[0]) < Integer
										.parseInt(splittnhc[1]))) {
							// if (splittncapgoc.length == 2
							// && (Integer.parseInt(splittncapgoc[0]) < Integer
							// .parseInt(splittncapgoc[1]))) {
							// if(txtvendor.getText() != null &&
							// !txtvendor.getText().toString().isEmpty()){
							txtcheck.setText("OK");
							// }else{
							// txtcheck.setText("NOK");
							// Toast.makeText(
							// FragmentLoadMapHotline.this,
							// getResources().getString(
							// R.string.thieuvendor),
							// Toast.LENGTH_SHORT).show();
							// }
							// } else {
							// txtcheck.setText("NOK");
							// Toast.makeText(
							// FragmentLoadMapHotline.this,
							// getResources().getString(
							// R.string.checktncapgoc),
							// Toast.LENGTH_SHORT).show();
							// }

						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									FragmentLoadMapHotline.this,
									getResources().getString(
											R.string.checktnhopcap),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						txtcheck.setText("NOK");
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checktnport),
								Toast.LENGTH_SHORT).show();
					}

				} else {
					txtcheck.setText("NOK");
				}
			}
		});
		dialog.show();
	}

	// show popup THC
	private void showPopupTHC(SurveyOnline surveyOnline) {

		surveyOnlineInit = surveyOnline;

		final Dialog dialog = new Dialog(FragmentLoadMapHotline.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_thc);

		final TextView txtmatab = (TextView) dialog.findViewById(R.id.txtmatab);
		TextView txtdiachitab = (TextView) dialog
				.findViewById(R.id.txtdiachitab);
		final TextView txttntab = (TextView) dialog.findViewById(R.id.txttntab);
		TextView txtmanotquang = (TextView) dialog
				.findViewById(R.id.txtmanotquang);
		TextView txtmacotdien = (TextView) dialog
				.findViewById(R.id.txtmacotdien);
		TextView txttodoiquanly = (TextView) dialog
				.findViewById(R.id.txttodoiquanly);

		Button btnok = (Button) dialog.findViewById(R.id.btnok);
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);
		txtnamequanlyTHC = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienheTHC = (EditText) dialog.findViewById(R.id.txtdtlienhe);

		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);

		final TextView txtvendor = (TextView) dialog
				.findViewById(R.id.txtloaivendor);
		if (surveyOnline.getVendorCode() != null
				&& !surveyOnline.getVendorCode().isEmpty()) {
			txtvendor.setText(surveyOnline.getVendorCode());
		}

		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienheTHC.getText().toString() != null
						&& !txtdtlienheTHC.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienheTHC.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(FragmentLoadMapHotline.this,
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		if (surveyOnline.getConnectorCode() != null
				&& !surveyOnline.getConnectorCode().isEmpty()) {
			txtmatab.setText(surveyOnline.getConnectorCode());
		}
		if (surveyOnline.getAddress() != null
				&& !surveyOnline.getAddress().isEmpty()) {
			txtdiachitab.setText(surveyOnline.getAddress());
		}
		if (surveyOnline.getResourceConnector() != null
				&& !surveyOnline.getResourceConnector().isEmpty()) {
			txttntab.setText(surveyOnline.getResourceConnector());
		}
		if (surveyOnline.getNodeOpticalCode() != null
				&& !surveyOnline.getNodeOpticalCode().isEmpty()) {
			txtmanotquang.setText(surveyOnline.getNodeOpticalCode());
		}
		if (surveyOnline.getPillarCode() != null
				&& !surveyOnline.getPillarCode().isEmpty()) {
			txtmacotdien.setText(surveyOnline.getPillarCode());
		}
		if (surveyOnline.getDeptName() != null
				&& !surveyOnline.getDeptName().isEmpty()) {
			txttodoiquanly.setText(surveyOnline.getDeptName());
		}

		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtmatab.getText() != null
						&& !txtmatab.getText().toString().isEmpty()) {
					// ======call webservice =============
					if (CommonActivity.isNetworkConnected(FragmentLoadMapHotline.this) == true) {
						GetUserFromCableBoxTHC getUserFromCableBoxAsyn = new GetUserFromCableBoxTHC(
								FragmentLoadMapHotline.this);
						getUserFromCableBoxAsyn.execute(txtmatab.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
					}
				}

				if (txttntab.getText().toString() != null
						&& !txttntab.getText().toString().isEmpty()) {
					String tntab = txttntab.getText().toString();
					String[] splitntb = tntab.split("/", 2);
					if (splitntb.length == 2
							&& (Integer.parseInt(splitntb[0]) < Integer
									.parseInt(splitntb[1]))) {
						// if(txtvendor.getText() != null &&
						// !txtvendor.getText().toString().isEmpty()){
						txtcheck.setText("OK");
						// }else{
						// txtcheck.setText("NOK");
						// Toast.makeText(
						// FragmentLoadMapHotline.this,
						// getResources().getString(
						// R.string.thieuvendor),
						// Toast.LENGTH_SHORT).show();
						// }

					} else {
						txtcheck.setText("NOK");
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checktntab1),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					txtcheck.setText("NOK");
				}

			}
		});

		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (surveyOnlineInit.getConnectorCode() != null
						&& !surveyOnlineInit.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (dialogT != null) {
							dialogT.dismiss();
						}
						Intent data = new Intent();
						Bundle mBundle = new Bundle();
						surveyOnlineInit.setSurfaceRadius(editBKKS.getText()
								.toString());
						mBundle.putSerializable("ServeyKey", surveyOnlineInit);
						mBundle.putString("checkConnectorCode", "1000");
						data.putExtras(mBundle);
						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checkNotOk),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				onBackPressed();
			}
		});

		dialog.show();

	}

	// show pop up Gpon
	private void showPopupGPON(SurveyOnline surveyOnline) {

		surveyOnlineInit = surveyOnline;

		final Dialog dialog = new Dialog(FragmentLoadMapHotline.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_gpon);

		final TextView txtmaSpiliter = (TextView) dialog
				.findViewById(R.id.txtmaSpiliter);
		TextView txtdiachisplitter = (TextView) dialog
				.findViewById(R.id.txtdiachisplitter);
		final TextView txttnspiliter = (TextView) dialog
				.findViewById(R.id.txttnspiliter);
		TextView txtdodaituyencap = (TextView) dialog
				.findViewById(R.id.txtdodaituyencap);
		final TextView txttainguyentbi = (TextView) dialog
				.findViewById(R.id.txttainguyentbi);
		TextView txtdvquanlyketcuoi = (TextView) dialog
				.findViewById(R.id.txtdvquanlyketcuoi);
		TextView txttodoiqlynhatram = (TextView) dialog
				.findViewById(R.id.txttodoiqlynhatram);
		final TextView txtsoluongportrong = (TextView) dialog
				.findViewById(R.id.txtsoluongportrong);
		TextView txtsluongportdangtkmoi = (TextView) dialog
				.findViewById(R.id.txtsluongportdangtkmoi);
		TextView txttenOLTganketcuoi = (TextView) dialog
				.findViewById(R.id.txttenOLTganketcuoi);
		TextView txtportOLTganketcuoi = (TextView) dialog
				.findViewById(R.id.txtportOLTganketcuoi);
		TextView txtdungluongOLT = (TextView) dialog
				.findViewById(R.id.txtdungluongOLT);
		TextView txtdungluongtkmoi = (TextView) dialog
				.findViewById(R.id.txtdungluongtkmoi);

		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);
		txtnamequanlyGPON = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienheGPON = (EditText) dialog.findViewById(R.id.txtdtlienhe);

		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);

		final TextView txtvendor = (TextView) dialog
				.findViewById(R.id.txtloaivendor);
		if (surveyOnline.getVendorCode() != null
				&& !surveyOnline.getVendorCode().isEmpty()) {
			txtvendor.setText(surveyOnline.getVendorCode());
		}

		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienheGPON.getText().toString() != null
						&& !txtdtlienheGPON.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienheGPON.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(FragmentLoadMapHotline.this,
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		if (surveyOnline.getConnectorCode() != null
				&& !surveyOnline.getConnectorCode().isEmpty()) {
			txtmaSpiliter.setText(surveyOnline.getConnectorCode());
		}
		if (surveyOnline.getAddress() != null
				&& !surveyOnline.getAddress().isEmpty()) {
			txtdiachisplitter.setText(surveyOnline.getAddress());
		}
		if (surveyOnline.getCableLength() != null
				&& !surveyOnline.getCableLength().isEmpty()) {
			txtdodaituyencap.setText(Html.fromHtml(surveyOnline
					.getCableLength() + " <font color=\"red\"> m" + "</font>"));
		}
		if (surveyOnline.getResourceConnector() != null
				&& !surveyOnline.getResourceConnector().isEmpty()) {
			txttnspiliter.setText(surveyOnline.getResourceConnector());
		}
		if (surveyOnline.getResourceDevice() != null
				&& !surveyOnline.getResourceDevice().isEmpty()) {
			txttainguyentbi.setText(surveyOnline.getResourceDevice());
		}

		if (surveyOnline.getDeptName() != null
				&& !surveyOnline.getDeptName().isEmpty()) {
			txtdvquanlyketcuoi.setText(surveyOnline.getDeptName());
		}
		if (surveyOnline.getLocationName() != null
				&& !surveyOnline.getLocationName().isEmpty()) {
			txttodoiqlynhatram.setText(surveyOnline.getLocationName());
		}
		if (surveyOnline.getConnectorFreePort() != null
				&& !surveyOnline.getConnectorFreePort().isEmpty()) {
			txtsoluongportrong.setText(surveyOnline.getConnectorFreePort());
		}
		if (surveyOnline.getConnectorLockPort() != null
				&& !surveyOnline.getConnectorLockPort().isEmpty()) {
			txtsluongportdangtkmoi.setText(surveyOnline.getConnectorLockPort());
		}
		if (surveyOnline.getDeviceCode() != null
				&& !surveyOnline.getDeviceCode().isEmpty()) {
			txttenOLTganketcuoi.setText(surveyOnline.getDeviceCode());
		}
		if (surveyOnline.getPortCode() != null
				&& !surveyOnline.getPortCode().isEmpty()) {
			txtportOLTganketcuoi.setText(surveyOnline.getPortCode());
		}
		if (surveyOnline.getSizePortForCable() != null
				&& !surveyOnline.getSizePortForCable().isEmpty()) {
			txtdungluongOLT.setText(surveyOnline.getSizePortForCable());
		}
		if (surveyOnline.getSizeKeepDeployAccount() != null
				&& !surveyOnline.getSizeKeepDeployAccount().isEmpty()) {
			txtdungluongtkmoi.setText(surveyOnline.getSizeKeepDeployAccount());
		}

		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (txtmaSpiliter.getText() != null
						&& !txtmaSpiliter.getText().toString().isEmpty()) {
					// ======call webservice =============
					if (CommonActivity.isNetworkConnected(FragmentLoadMapHotline.this) == true) {
						GetUserFromCableBoxGPON getUserFromCableBoxAsyn = new GetUserFromCableBoxGPON(
								FragmentLoadMapHotline.this);
						getUserFromCableBoxAsyn.execute(txtmaSpiliter.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();

					}
				}

				if (txtsoluongportrong.getText().toString() != null
						&& !txtsoluongportrong.getText().toString().isEmpty()) {

					if (!txtsoluongportrong.getText().toString().equals("0")) {

						// (1:52:55 PM) haunx: nhung thang FTTH
						// (1:53:00 PM) haunx: NEXTV
						// (1:53:02 PM) haunx: NGN
						// (1:53:07 PM) haunx: Multiscren 2C
						if ("F".equals(serviceID) || "N".equals(serviceID)
								|| "I".equals(serviceID)
								|| "23".equals(groupProductId)) {
							if (txtvendor.getText() != null
									&& !txtvendor.getText().toString()
											.isEmpty()) {
								txtcheck.setText("OK");
							} else {
								txtcheck.setText("NOK");
								Toast.makeText(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.thieuvendor),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							txtcheck.setText("OK");
						}

					} else {
						txtcheck.setText("NOK");
					}
				} else {
					txtcheck.setText("NOK");
				}

			}
		});

		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (surveyOnlineInit.getConnectorCode() != null
						&& !surveyOnlineInit.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (dialogGPON != null) {
							dialogGPON.dismiss();
						}
						Intent data = new Intent();
						Bundle mBundle = new Bundle();
						surveyOnlineInit.setSurfaceRadius(editBKKS.getText()
								.toString());
						mBundle.putSerializable("ServeyKey", surveyOnlineInit);
						mBundle.putString("checkConnectorCode", "1000");
						data.putExtras(mBundle);
						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checkNotOk),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				onBackPressed();
			}
		});

		dialog.show();
	}

	// show popup connection FTTH

	private void showPopupFTTH(final SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(FragmentLoadMapHotline.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_odf);

		surveyOnlineInit = surveyOnline;

		// === init view ======
		final TextView txtmaodf = (TextView) dialog.findViewById(R.id.txtmaodf);
		TextView txtdiachiodf = (TextView) dialog
				.findViewById(R.id.txtdiachiodf);
		final TextView txttnodf = (TextView) dialog.findViewById(R.id.txttnodf);
		TextView txtdodaituyencap = (TextView) dialog
				.findViewById(R.id.txtdodaituyencap);
		final TextView txttntbi = (TextView) dialog.findViewById(R.id.txttntbi);
		TextView txtdvquanlyketcuoi = (TextView) dialog
				.findViewById(R.id.txtdvquanlyketcuoi);
		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);
		txtnamequanlyFTTH = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienheFTTH = (EditText) dialog.findViewById(R.id.txtdtlienhe);
		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);

		final TextView txtvendor = (TextView) dialog
				.findViewById(R.id.txtloaivendor);
		if (surveyOnline.getVendorCode() != null
				&& !surveyOnline.getVendorCode().isEmpty()) {
			txtvendor.setText(surveyOnline.getVendorCode());
		}

		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienheFTTH.getText().toString() != null
						&& !txtdtlienheFTTH.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienheFTTH.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(FragmentLoadMapHotline.this,
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		// set data for view

		if (surveyOnline.getConnectorCode() != null) {
			txtmaodf.setText(surveyOnline.getConnectorCode());
		}
		if (surveyOnline.getAddress() != null) {
			txtdiachiodf.setText(surveyOnline.getAddress());
		}
		if (surveyOnline.getResourceDevice() != null) {
			txttntbi.setText(surveyOnline.getResourceDevice());
		}
		if (surveyOnline.getCableLength() != null) {
			txtdodaituyencap.setText(Html.fromHtml(surveyOnline
					.getCableLength() + " <font color=\"red\"> m" + "</font>"));
		}
		if (surveyOnline.getDeptName() != null) {

			txtdvquanlyketcuoi.setText(surveyOnline.getDeptName());
		}

		if (surveyOnline.getResourceConnector() != null) {
			txttnodf.setText(surveyOnline.getResourceConnector());
		}

		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (txtmaodf.getText() != null
						&& !txtmaodf.getText().toString().isEmpty()) {
					// ======call webservice =============
					if (CommonActivity.isNetworkConnected(FragmentLoadMapHotline.this) == true) {
						GetUserFromCableBoxFTTH getUserFromCableBoxAsyn = new GetUserFromCableBoxFTTH(
								FragmentLoadMapHotline.this);
						getUserFromCableBoxAsyn.execute(txtmaodf.getText()
								.toString(), surveyOnline.getConnectorType());
					} else {
						CommonActivity
								.createAlertDialog(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
					}
				}

				// bo sung doan validate
				// Khi khảo sát kết cuối là mã trạm (connector_type =
				// STATION)
				// thì chỉ validate tài nguyên port thiết bị,
				// Ko validate tài nguyên port ngoại vi ODF.
				// Khi khảo sát kết cuối là ODF (connector_type =
				// CONNECTOR) thì
				// validate cả port thiết bị & port ngoại vi.

				if ("STATION".equals(surveyOnline.getConnectorType())) {
					if (txttntbi.getText().toString() != null
							&& !txttntbi.getText().toString().isEmpty()) {
						String checktntbi = txttntbi.getText().toString();
						String[] splittntbi = checktntbi.split("/", 2);
						if (splittntbi.length == 2
								&& (Integer.parseInt(splittntbi[0]) < Integer
										.parseInt(splittntbi[1]))) {
							txtcheck.setText("OK");
						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									FragmentLoadMapHotline.this,
									getResources().getString(
											R.string.checktntbi),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						txtcheck.setText("NOK");
					}
				}
				if ("CONNECTOR".equals(surveyOnline.getConnectorType())) {
					if ((txttnodf.getText().toString() != null && !txttnodf
							.getText().toString().isEmpty())
							&& (txttntbi.getText().toString() != null && !txttntbi
									.getText().toString().isEmpty())) {
						String checktnOdf = txttnodf.getText().toString();
						String checktntbi = txttntbi.getText().toString();
						String[] splittnOdf = checktnOdf.split("/", 2);
						String[] splittntbi = checktntbi.split("/", 2);
						if (splittnOdf.length == 2
								&& (Integer.parseInt(splittnOdf[0]) < Integer
										.parseInt(splittnOdf[1]))) {
							if (splittntbi.length == 2
									&& (Integer.parseInt(splittntbi[0]) < Integer
											.parseInt(splittntbi[1]))) {
								txtcheck.setText("OK");
								// }else{
								// txtcheck.setText("NOK");
								// Toast.makeText(
								// FragmentLoadMapHotline.this,
								// getResources().getString(
								// R.string.thieuvendor),
								// Toast.LENGTH_SHORT).show();
								// }

							} else {
								txtcheck.setText("NOK");
								Toast.makeText(
										FragmentLoadMapHotline.this,
										getResources().getString(
												R.string.checktntbi),
										Toast.LENGTH_SHORT).show();
							}

						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									FragmentLoadMapHotline.this,
									getResources().getString(
											R.string.checktnOdf),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						txtcheck.setText("NOK");
					}
				}
			}
		});

		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (surveyOnlineInit.getConnectorCode() != null
						&& !surveyOnlineInit.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (dialogF != null) {
							dialogF.dismiss();
						}
						Intent data = new Intent();
						Bundle mBundle = new Bundle();
						surveyOnlineInit.setSurfaceRadius(editBKKS.getText()
								.toString());
						mBundle.putString("checkConnectorCode", "1000");
						mBundle.putSerializable("ServeyKey", surveyOnlineInit);
						data.putExtras(mBundle);
						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checkNotOk),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				onBackPressed();
			}
		});

		dialog.show();
	}

	@Override
	public void onResume() {
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
				CommonActivity.callphone(FragmentLoadMapHotline.this,
						Constant.phoneNumber);
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
					CommonActivity.DoNotLocation(FragmentLoadMapHotline.this);
				}
			} else {

				Toast.makeText(this,
						getResources().getString(R.string.checkserviceType),
						Toast.LENGTH_SHORT).show();

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
		if (surfaceRadius == null || surfaceRadius.isEmpty()) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.checksufaceradius),
					Toast.LENGTH_SHORT).show();
		} else {
			if (Long.parseLong(surfaceRadius) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.checkradius2000),
						Toast.LENGTH_SHORT).show();
			} else {
				if (CommonActivity.isNetworkConnected(this) == true) {
					// == run asyntask
					if (lisSurveyOnlines.size() > 0) {
						lisSurveyOnlines.clear();
						mapview.refresh();
					}
					GetListSurfaceOnlineAsyn getListSurfaceOnlineAsyn = new GetListSurfaceOnlineAsyn(
							this);
					getListSurfaceOnlineAsyn.execute();
				} else {
					CommonActivity.createAlertDialog(this,
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name)).show();
				}
			}
		}

	}

	// @SuppressWarnings("unused")
	// private Location getMyLocation() {
	// GPSTracker gps = new GPSTracker(FragmentLoadMapHotline.this);
	//
	// // check if GPS enabled
	// if (gps.canGetLocation()) {
	// double latitude = gps.getLatitude(); // x 21.022566
	// // double latitude = 21.022566;
	// double longitude = gps.getLongitude();// y 105.799560
	// // double longitude = 105.799560;
	// Log.e("latitude, longitude", "latitude, longitude : " + latitude
	// + ", " + longitude);
	// Toast.makeText(
	// FragmentLoadMapHotline.this,
	// "" + getResources().getString(R.string.curr_location)
	// + " X " + latitude + " - Y " + longitude,
	// Toast.LENGTH_LONG).show();
	// Location location = new Location("my location");
	// location.setLatitude(latitude);
	// location.setLongitude(longitude);
	// return location;
	//
	// } else {
	// CommonActivity.DoNotLocation(FragmentLoadMapHotline.this);
	// return null;
	// }
	// }

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FragmentLoadMapHotline.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
		}
	};

	// asyn getCableBox

	public class GetUserFromCableBoxADSL extends
			AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetUserFromCableBoxADSL(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getUserFromCableBoxADSL(arg0[0]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null
						&& !result.getFullname().isEmpty()) {
					sysUsersBOInit = result;
					txtnamequanlyADSL.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheADSL.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this,
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentLoadMapHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this, description, getResources()
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getUserFromCableBoxADSL(String cableCode) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getUserByCableBoxCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getUserByCableBoxCode>");
				rawData.append("<qlctktInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<cableBoxCode>" + cableCode);
				rawData.append("</cableBoxCode>");
				rawData.append("<infraType>" + techchologyID);
				rawData.append("</infraType>");
				rawData.append("</qlctktInput>");
				rawData.append("</ws:getUserByCableBoxCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentLoadMapHotline.this,
						"mbccs_getUserByCableBoxCode");
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

	public class GetUserFromCableBoxFTTH extends
			AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetUserFromCableBoxFTTH(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getUserFromCableBoxFTTH(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null
						&& !result.getFullname().isEmpty()) {
					sysUsersBOInit = result;
					txtnamequanlyFTTH.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheFTTH.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this,
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentLoadMapHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this, description, getResources()
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getUserFromCableBoxFTTH(String cableCode,
				String connectorType) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getUserByCableBoxCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getUserByCableBoxCode>");
				rawData.append("<qlctktInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<cableBoxCode>" + cableCode);
				rawData.append("</cableBoxCode>");

				if ("STATION".equals(connectorType)) {
					rawData.append("<infraType>" + "6");
					rawData.append("</infraType>");
				} else {
					rawData.append("<infraType>" + techchologyID);
					rawData.append("</infraType>");
				}

				rawData.append("</qlctktInput>");
				rawData.append("</ws:getUserByCableBoxCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentLoadMapHotline.this,
						"mbccs_getUserByCableBoxCode");
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

	public class GetUserFromCableBoxPSTN extends
			AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetUserFromCableBoxPSTN(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getUserFromCableBoxPSTN(arg0[0]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null
						&& !result.getFullname().isEmpty()) {
					sysUsersBOInit = result;
					txtnamequanlyPSTN.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienhePSTN.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this,
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentLoadMapHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this, description, getResources()
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getUserFromCableBoxPSTN(String cableCode) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getUserByCableBoxCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getUserByCableBoxCode>");
				rawData.append("<qlctktInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<cableBoxCode>" + cableCode);
				rawData.append("</cableBoxCode>");
				rawData.append("<infraType>" + techchologyID);
				rawData.append("</infraType>");
				rawData.append("</qlctktInput>");
				rawData.append("</ws:getUserByCableBoxCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentLoadMapHotline.this,
						"mbccs_getUserByCableBoxCode");
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

	public class GetUserFromCableBoxTHC extends
			AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetUserFromCableBoxTHC(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getUserFromCableBoxTHC(arg0[0]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null
						&& !result.getFullname().isEmpty()) {
					sysUsersBOInit = result;
					txtnamequanlyFTTH.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheFTTH.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this,
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentLoadMapHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this, description, getResources()
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getUserFromCableBoxTHC(String cableCode) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getUserByCableBoxCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getUserByCableBoxCode>");
				rawData.append("<qlctktInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<cableBoxCode>" + cableCode);
				rawData.append("</cableBoxCode>");
				rawData.append("<infraType>" + techchologyID);
				rawData.append("</infraType>");
				rawData.append("</qlctktInput>");
				rawData.append("</ws:getUserByCableBoxCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentLoadMapHotline.this,
						"mbccs_getUserByCableBoxCode");
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

	public class GetUserFromCableBoxGPON extends
			AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetUserFromCableBoxGPON(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getUserFromCableBoxGPON(arg0[0]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null
						&& !result.getFullname().isEmpty()) {
					sysUsersBOInit = result;
					txtnamequanlyGPON.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheGPON.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this,
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentLoadMapHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this, description, getResources()
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getUserFromCableBoxGPON(String cableCode) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getUserByCableBoxCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getUserByCableBoxCode>");
				rawData.append("<qlctktInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<cableBoxCode>" + cableCode);
				rawData.append("</cableBoxCode>");
				rawData.append("<infraType>" + techchologyID);
				rawData.append("</infraType>");
				rawData.append("</qlctktInput>");
				rawData.append("</ws:getUserByCableBoxCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentLoadMapHotline.this,
						"mbccs_getUserByCableBoxCode");
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

	// asyntask SurfaceOnline
	public class GetListSurfaceOnlineAsyn extends
			AsyncTask<Void, Void, ArrayList<SurveyOnline>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ArrayList<MarkerOptions> arrMarkerOptions = new ArrayList<MarkerOptions>();

		public GetListSurfaceOnlineAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<SurveyOnline> doInBackground(Void... params) {
			return getListSurfaceOnline();
		}

		@Override
		protected void onPostExecute(ArrayList<SurveyOnline> result) {
			// check errorcode
			progress.dismiss();
			CommonActivity.hideKeyboard(editBKKS, context);
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					Log.d("getListSurfaceOnlineAsyn", "co du lieu");
					lisSurveyOnlines = result;
					mapview.setCenter(new LatLng(Double.parseDouble(result.get(
							0).getLat()), Double.parseDouble(result.get(0)
							.getLng())));
				} else {

					CommonActivity.createAlertDialog(FragmentLoadMapHotline.this,
							getResources().getString(R.string.no_data),
							getResources().getString(R.string.app_name)).show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentLoadMapHotline.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentLoadMapHotline.this, description, getResources()
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		private ArrayList<SurveyOnline> getListSurfaceOnline() {
			ArrayList<SurveyOnline> lisSurveyOnlines = new ArrayList<SurveyOnline>();

			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_surveyOnline");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:surveyOnline>");
				rawData.append("<nimsInput>");

				HashMap<String, String> param = new HashMap<String, String>();

				// ===add token ===
				param.put("token", Session.getToken());

				// == add maxResult = 5 ==
				param.put("maxResult", "5");

				// == add surveyOnlineForm
				HashMap<String, String> rawDataItem = new HashMap<String, String>();
				if (!techchologyID.equals("")) {
					rawDataItem.put("infraType", parValueTechology);
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
				if (msType != null && !msType.isEmpty()) {
					if (("23".equals(groupProductId) && "U".equals(serviceID))
							|| ("22".equals(groupProductId) && "U"
									.equals(serviceID))) {
						if (!CommonActivity.isNullOrEmpty(mstNoRf)) {
							rawDataItem.put("msType", mstNoRf);
						} else {
							rawDataItem.put("msType", msType);
						}
					} else {
						rawDataItem.put("msType", msType);
					}
				}
				if (surfaceRadius != null || !surfaceRadius.isEmpty()) {
					rawDataItem.put("surveyRadius", surfaceRadius);
				}

				if (isFTTB != null && !isFTTB.isEmpty()) {

					rawDataItem.put("isFttb", isFTTB);
				}

				param.put("surveyOnlineForm", input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));
				rawData.append("</nimsInput>");
				rawData.append("</ws:surveyOnline>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentLoadMapHotline.this,
						"mbccs_surveyOnline");
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
					nodechild = doc
							.getElementsByTagName("lstResultSurveyOnlineForm");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SurveyOnline surveyOnline = new SurveyOnline();
						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						surveyOnline.setAddress(address);

						String cableLength = parse.getValue(e1, "cableLength");
						Log.d("cableLength", cableLength);
						surveyOnline.setCableLength(cableLength);

						String connectorCode = parse.getValue(e1,
								"connectorCode");
						Log.d("connectorCode", connectorCode);
						surveyOnline.setConnectorCode(connectorCode);

						String connectorId = parse.getValue(e1, "connectorId");
						Log.d("connectorId", connectorId);
						surveyOnline.setConnectorId(connectorId);

						String connectorType = parse.getValue(e1,
								"connectorType");
						Log.d("connectorType", connectorType);
						surveyOnline.setConnectorType(connectorType);

						String deptCode = parse.getValue(e1, "deptCode");
						Log.d("deptCode", deptCode);
						surveyOnline.setDeptCode(deptCode);

						String deptName = parse.getValue(e1, "deptName");
						Log.d("deptName", deptName);
						surveyOnline.setDeptName(deptName);

						String distance = parse.getValue(e1, "distance");
						Log.d("distance", distance);
						if (distance != null && !distance.isEmpty()) {
							surveyOnline.setDistance(distance);
						}

						String lat1 = parse.getValue(e1, "lat");
						Log.d("lat", lat1);
						surveyOnline.setLat(lat1);

						String lng1 = parse.getValue(e1, "lng");
						Log.d("lng", lng1);
						surveyOnline.setLng(lng1);

						surveyOnline.setCableBoxPosition(lat1 + "_" + lng1);
						surveyOnline.setCustomerPossition(lat + "_" + lon);
						surveyOnline.setPolylineData(lat + "_" + lon + ";"
								+ lat1 + "_" + lng1);
						String resourceConnector = parse.getValue(e1,
								"resourceConnector");
						Log.d("resourceConnector", resourceConnector);
						surveyOnline.setResourceConnector(resourceConnector);

						String resourceDevice = parse.getValue(e1,
								"resourceDevice");
						Log.d("resourceDevice", resourceDevice);
						surveyOnline.setResourceDevice(resourceDevice);

						String resourceRootCable = parse.getValue(e1,
								"resourceRootCable");
						Log.d("resourceRootCable", resourceRootCable);
						surveyOnline.setResourceRootCable(resourceRootCable);

						String stationCode = parse.getValue(e1, "stationCode");
						Log.d("stationCode", stationCode);
						surveyOnline.setStationCode(stationCode);

						String stationId = parse.getValue(e1, "stationId");
						Log.d("stationId", stationId);
						surveyOnline.setStationId(stationId);

						String connectorFreePort = parse.getValue(e1,
								"connectorFreePort");
						Log.d("connectorFreePort", connectorFreePort);
						surveyOnline.setConnectorFreePort(connectorFreePort);

						String connectorLockPort = parse.getValue(e1,
								"connectorLockPort");
						Log.d("connectorLockPort", connectorLockPort);
						surveyOnline.setConnectorLockPort(connectorLockPort);

						String deviceCode = parse.getValue(e1, "deviceCode");
						Log.d("deviceCode", deviceCode);
						surveyOnline.setDeviceCode(deviceCode);

						String portCode = parse.getValue(e1, "portCode");
						Log.d("portCode", portCode);
						surveyOnline.setPortCode(portCode);

						String sizePortForCable = parse.getValue(e1,
								"sizePortForCable");
						Log.d("sizePortForCable", sizePortForCable);
						surveyOnline.setSizePortForCable(sizePortForCable);

						String sizeKeepDeployAccount = parse.getValue(e1,
								"sizeKeepDeployAccount");
						Log.d("sizeKeepDeployAccount", sizeKeepDeployAccount);
						surveyOnline
								.setSizeKeepDeployAccount(sizeKeepDeployAccount);

						String locationName = parse
								.getValue(e1, "locationName");
						Log.d("locationName", locationName);
						surveyOnline.setLocationName(locationName);

						String locationCode = parse
								.getValue(e1, "locationCode");
						Log.d("locationCode", locationCode);
						surveyOnline.setLocationCode(locationCode);

						String teamCode = parse.getValue(e1, "teamCode");
						surveyOnline.setTeamCode(teamCode);

						String nodeOpticalCode = parse.getValue(e1,
								"nodeOpticalCode");
						Log.d("nodeOpticalCode", nodeOpticalCode);
						surveyOnline.setNodeOpticalCode(nodeOpticalCode);

						String pillarCode = parse.getValue(e1, "pillarCode");
						Log.d("pillarCode", pillarCode);
						surveyOnline.setPillarCode(pillarCode);

						// vendorCode, vendorName
						String vendorCode = parse.getValue(e1, "vendorCode");
						surveyOnline.setVendorCode(vendorCode);

						String vendorName = parse.getValue(e1, "vendorName");
						surveyOnline.setVendorName(vendorName);

						MarkerOptions markerOptions = new MarkerOptions()
								.icon(BitmapFactory.decodeResource(
										getResources(),
										R.drawable.icon_location))
								.position(
										new LatLng(Double.parseDouble(lat1),
												Double.parseDouble(lng1)))
								.title(address).description(deptName);

						Marker marker = mapview.addMarker(markerOptions);
						marker.setUserData(surveyOnline);

						lisSurveyOnlines.add(surveyOnline);

					}
				}

			} catch (Exception e) {

				Log.d("getListSurfaceOnlineAsyn", e.toString());

			}

			return lisSurveyOnlines;
		}

	}

	// lay huyen/quan theo tinh
	private void initDistrict(String province) {
		try {
			GetAreaDal dal = new GetAreaDal(FragmentLoadMapHotline.this);
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
			GetAreaDal dal = new GetAreaDal(FragmentLoadMapHotline.this);
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
						LatLng latlon = arg0.getItem(0).getBoundary()
								.getCenter();
						if(latlon != null){
							lat = latlon.getLatitude() + "";
							lon = latlon.getLongitude() + "";
							Log.d("lattt", latlon.getLatitude() + "");
							Log.d("longgggggg", latlon.getLongitude() + "");
							if (lat.equals("0") || lon.equals("0")) {
								CommonActivity
										.createAlertDialog(
												FragmentLoadMapHotline.this,
												getResources().getString(
														R.string.checkareadb),
												getResources().getString(
														R.string.app_name)).show();
							} else {
								MarkerOptions markerOptions = new MarkerOptions()
										.icon(BitmapFactory.decodeResource(
												getResources(), R.drawable.iconmap))
										.position(
												new LatLng(Double.parseDouble(lat),
														Double.parseDouble(lon)));
								if(markerinit != null){
									mapview.removeMarker(markerinit);
									mapview.refresh();
								}
								
								
								markerinit = mapview.addMarker(markerOptions);
								markerinit.setDraggable(true);
								mapview.setCenter(new LatLng(Double
										.parseDouble(lat), Double.parseDouble(lon)));
							}
						}else{
							CommonActivity.createAlertDialog(FragmentLoadMapHotline.this,
									getResources().getString(R.string.checkareadb),
									getResources().getString(R.string.app_name))
									.show();
						}
					
					} else {
						CommonActivity.createAlertDialog(FragmentLoadMapHotline.this,
								getResources().getString(R.string.checkareadb),
								getResources().getString(R.string.app_name))
								.show();
					}
				} else {
					CommonActivity.createAlertDialog(FragmentLoadMapHotline.this,
							getResources().getString(R.string.checkareadb),
							getResources().getString(R.string.app_name)).show();
				}

			}
		});

	}

}
