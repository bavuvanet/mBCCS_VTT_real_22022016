package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ExamineJoinBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.GroupAccountForCusBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HostAccountBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.MainSubGponBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.NameProductBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PakageChargeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RelationGponBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SysUsersBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TechologyBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetExamineJoinDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetTechologyDal;
import com.viettel.bss.viettelpos.v4.connecttionService.handler.HostAccountHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.handler.HostAccountHandlerIEN;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.SurveyOnline;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentInfoExamine extends Fragment implements OnClickListener {
    private Spinner spinner_name_product, spinner_service,
			spinner_pakage_charge, spinner_congnghe, spinner_hatang_ghep,
			spinner_thuebaochu, spinner_duongday, spinner_lienket,
			spinner_thuebaochinh;
	private EditText edit_maketcuoi;

	private String nameProductId = "";
	private String serviceType = "";
	private String pakageCharge = "";
	private String examineJoin = "";
	private String techology = "";
	private String serviceId;
	private String productCode = "";
	private String idDuongDay = "";
	private String offerId = "";
	private String accHostAcc = "";
	private String isdnHostAcc = "";
	private String paramId = "";
	private String relationType = "";
	private String mainSubId = "";
	private String msType = "";
	private String productCodeMt = "";
	private int selectNameProduct = 0;
	private int selectService = 0;
	private int selectPakageCharge = 0;
	private int selectTechology = 0;

	private LinearLayout linearkhacGpon, linearGpon, linearhatangghep,
			lnLoadmap, lnthuebaochu, lnthuebaochinh;

	private ArrayList<NameProductBeans> arrNameProductBeans = new ArrayList<>();
	private ArrayList<ServiceBeans> arrServiceBeans = new ArrayList<>();
	private ArrayList<PakageChargeBeans> arrPakageChargeBeans = new ArrayList<>();
	private ArrayList<TechologyBeans> arrTechologyBeans = new ArrayList<>();

	// =================== init list hang ghep -- thu bao chu(account
	// host)=================================
	private ArrayList<ExamineJoinBeans> arrExamineJoinBeans = new ArrayList<>();
	private ArrayList<HostAccountBeans> arrHostAccountBeans = new ArrayList<>();

	// ===================== init list duong day ==================
	private ArrayList<GroupAccountForCusBean> arrAccountForCusBeans = new ArrayList<>();
	private final ArrayList<RelationGponBean> arrRelationGponBeans = new ArrayList<>();

	// ======================= init thue bao chinh ============================
	private ArrayList<MainSubGponBeans> arrMainSubGponBeans = new ArrayList<>();

	// bien checkConnectorCode dung de check get connectorcode from ban do
	private String checkConnectorCode = "";
	private Button btncheckConectorCode;
	private View mView;

	private ArrayAdapter<String> adapterRealation = null;

	private String connectorCode = "";
	private String locationCode = "";
	private String thuebaochu = "";
	private String lineType = "";
	private SurveyOnline surveyOnline = new SurveyOnline();
	private String mText = "";
	private LinearLayout lnspinerTechnologgy, lncongnghe;
	private EditText txtcongnghe;
	// === multiscreen =========
	// private String lat = "10.972315643160748";
	// private String lon = "106.86721730206045";

	// == test thc GPON
	// <lat>10.9720658294695</lat>
	// <!--Optional:-->
	// <lng>106.868431835651</lng>

	// String lat = "10.9720658294695";
	// String lon = "106.868431835651";
	// test FGPON
	// private String lat = "20.954895305106145";
	// private String lon = "105.83960737286361";
    private String lat = "0.0";
	private String lon = "0.0";
	// String lat = "20.53597";
	// String lon = "105.90319";

	// String lat = "10.7980947244718";
	// String lon = "106.729896124542";

	private String cusID = "";
	private String account = "";

	// CHECK conector by code
	private TextView txtnamequanlyADSL;
	private EditText txtdtlienheADSL;

	private TextView txtnamequanlyFTTH;
	private EditText txtdtlienheFTTH;

	private TextView txtnamequanlyPSTN;
	private EditText txtdtlienhePSTN;

    private EditText txtdtlienheTHC;

	private TextView txtnamequanlyGPON;
	private EditText txtdtlienheGPON;

	private String isFTTB = "";
	public static String groupProductId = "";
	
	private String  mstNoRf = "";
	
	// bo sung thong tin mo hinh
	private LinearLayout lnmohinh;
	private Spinner spinner_mohinh;

	public static String mohinh = "";

	private Button btnsanpham, btnservice;
	private ProgressBar prbsanpham, prbservice;
	private Button btninfo;

	private PakageChargeBeans pakageChargeBeans = null;

	
	private String province = "";
	private String district = "";
	private String precinct = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.connection_layout_info_examine,
					container, false);
			unit(mView);
		} else {
//			((ViewGroup) mView.getParent()).removeAllViews();
			// if( FragmentInfoCustomer.cusId != null && !
			// FragmentInfoCustomer.cusId.isEmpty()){
			cusID = FragmentInfoCustomer.cusId;
			// Log.d("cusidddddddddddddd", cusID);
			// }
			if (FragmentInfoCustomer.account != null
					&& !FragmentInfoCustomer.account.isEmpty()) {
				account = FragmentInfoCustomer.account;
				Log.d("cusnameeeeeeeeeeeeee", account);

				// thinhhq1 modify 28/12/2015 --- them thong tin dai dien cho
				// KHDN
				if (!CommonActivity
						.isNullOrEmpty(FragmentInfoCustomer.custommer
								.getBusPermitNo())) {
					if (CommonActivity
							.isNullOrEmpty(FragmentInfoCustomer.custommer
									.getCustomerAttribute().getIdNo())) {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkthongtindaidien),
								getString(R.string.app_name), movetabInfoCus)
								.show();
					}
				}

			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkcusid),
						getString(R.string.app_name), movetabInfoCus).show();
			}
		}
		return mView;
	}

	private final OnClickListener movetabInfoCus = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ActivityCreateNewRequest.instance.tHost.setCurrentTab(0);

		}
	};

	private void unit(View v) {

		btninfo = (Button) v.findViewById(R.id.btninfo);

		btninfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (pakageChargeBeans != null
						&& !CommonActivity.isNullOrEmpty(pakageChargeBeans
								.getDescription())) {
					CommonActivity.createAlertDialog(getActivity(),
							pakageChargeBeans.getDescription(),
							getActivity().getString(R.string.app_name)).show();
				}

			}
		});

		Location location = CommonActivity.findMyLocation(getActivity());
		if (location != null) {
			lat = location.getX();
			lon = location.getY();
			if (lat.equals("0") || lon.equals("0")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.checkgps),
						getResources().getString(R.string.app_name)).show();
			}

		}
		// if( FragmentInfoCustomer.cusId != null && !
		// FragmentInfoCustomer.cusId.isEmpty()){
		cusID = FragmentInfoCustomer.cusId;
		// Log.d("cusidddddddddddddd", cusID);
		// }
		if (FragmentInfoCustomer.account != null
				&& !FragmentInfoCustomer.account.isEmpty()) {
			account = FragmentInfoCustomer.account;
			Log.d("cusnameeeeeeeeeeeeee", account);

			// thinhhq1 modify 28/12/2015 --- them thong tin dai dien cho KHDN
			if (!CommonActivity.isNullOrEmpty(FragmentInfoCustomer.custommer
					.getBusPermitNo())) {
				if (CommonActivity.isNullOrEmpty(FragmentInfoCustomer.custommer
						.getCustomerAttribute().getIdNo())) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkthongtindaidien),
							getString(R.string.app_name), movetabInfoCus)
							.show();
				}
			}

		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkcusid),
					getString(R.string.app_name), movetabInfoCus).show();
		}

		lnthuebaochu = (LinearLayout) v.findViewById(R.id.lnthuebaochu);
		linearkhacGpon = (LinearLayout) v.findViewById(R.id.linearkhacGpon);
		linearkhacGpon.setVisibility(View.GONE);
		linearGpon = (LinearLayout) v.findViewById(R.id.linGpon);
		linearGpon.setVisibility(View.GONE);
		lnthuebaochinh = (LinearLayout) v.findViewById(R.id.lnthuebaochinh);
		linearhatangghep = (LinearLayout) v.findViewById(R.id.linearhatangghep);
        Button btnloadbando = (Button) v.findViewById(R.id.loadbando);
        Button btnupdate = (Button) v.findViewById(R.id.btn_update);
		lnLoadmap = (LinearLayout) v.findViewById(R.id.lnLoadmap);

		lnspinerTechnologgy = (LinearLayout) v
				.findViewById(R.id.lnspinerTechnologgy);
		// linear layout for cong nghe lay theo thue bao chu
		lncongnghe = (LinearLayout) v.findViewById(R.id.lncongnghe);
		spinner_name_product = (Spinner) v
				.findViewById(R.id.spinner_name_product);
		spinner_service = (Spinner) v.findViewById(R.id.spinner_service);
		spinner_pakage_charge = (Spinner) v
				.findViewById(R.id.spinner_pakage_charge);
		spinner_congnghe = (Spinner) v.findViewById(R.id.spinner_congnghe);
		spinner_hatang_ghep = (Spinner) v
				.findViewById(R.id.spinner_hatang_ghep);
		spinner_thuebaochu = (Spinner) v.findViewById(R.id.spinner_thuebaochu);

		spinner_duongday = (Spinner) v.findViewById(R.id.spinner_duongday);
		spinner_lienket = (Spinner) v.findViewById(R.id.spinner_lienket);
		spinner_thuebaochinh = (Spinner) v
				.findViewById(R.id.spinner_thuebaochinh);
		edit_maketcuoi = (EditText) v.findViewById(R.id.edit_maketcuoi);
		txtcongnghe = (EditText) v.findViewById(R.id.txtcongnghe);

		btnsanpham = (Button) v.findViewById(R.id.btnsanpham);
		btnsanpham.setOnClickListener(this);
		btnservice = (Button)v.findViewById(R.id.btnservice);
				btnservice.setOnClickListener(this);
				btnservice.setVisibility(
						View.GONE);
				prbsanpham = (ProgressBar) v.findViewById(R.id.prbsanpham);
				prbservice = (ProgressBar) v.findViewById(R.id.prbservice);
		// TODO bo sung thong tin mo hinh
		lnmohinh = (LinearLayout) v.findViewById(R.id.lnmohinhtrienkhai);
		lnmohinh.setVisibility(View.GONE);
		spinner_mohinh = (Spinner) v.findViewById(R.id.spinner_mohinh);
		spinner_mohinh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				if (item != null) {
					if (!CommonActivity.isNullOrEmpty(item.getId())) {
						mohinh = item.getId();
						Log.d("mohinhhhhhhhhhhhhhhhhhhhhhhhhhhhh", mohinh);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		if (arrMohinh != null && !arrMohinh.isEmpty()) {
			arrMohinh = new ArrayList<>();
		}
		initMohinh();

		// btncheckConectorCode
		btncheckConectorCode = (Button) v.findViewById(R.id.btnkiemtra);
		btncheckConectorCode.setOnClickListener(this);
		edit_maketcuoi.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (mText != null && !mText.toString().isEmpty()) {
					if (s != null
							&& !s.equals(mText)
							&& ((s.length() < mText.length()) || (s.length() >= mText
									.length()))) {
						Log.d("TAG", "Xoa xoa");
						checkConnectorCode = "1002";
						btncheckConectorCode.setVisibility(View.VISIBLE);
					} else {
						btncheckConectorCode.setVisibility(View.GONE);
					}
				} else {
					// btncheckConectorCode.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				Log.d("TAG", "Xoa xoa");
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				Log.d("TAG", "Xoa xoa");
			}
		});

		if (CommonActivity.isNetworkConnected(getActivity())) {
			GetListNameProductAsyn getListNameProductAsyn = new GetListNameProductAsyn(
					getActivity());
			getListNameProductAsyn.execute();
		} else {
			CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.errorNetwork),
					getActivity().getResources().getString(R.string.app_name))
					.show();
		}
		spinner_name_product
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						selectNameProduct = position;

						if (selectNameProduct > 0) {
							nameProductId = arrNameProductBeans.get(
									selectNameProduct).getGroupProductId();
							Log.d("nameProductId", nameProductId);
							if (nameProductId != null
									|| !nameProductId.isEmpty()) {
								if (CommonActivity
                                        .isNetworkConnected(getActivity())) {
									GetListServiceAsyn getListServiceAsyn = new GetListServiceAsyn(
											getActivity());
									getListServiceAsyn.execute(nameProductId);

								} else {
									CommonActivity
											.createAlertDialog(
													getActivity(),
													getActivity()
															.getResources()
															.getString(
																	R.string.errorNetwork),
													getActivity()
															.getResources()
															.getString(
																	R.string.app_name))
											.show();
								}
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						selectNameProduct = 0;

					}
				});
		spinner_service.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selectService = arg2;

				if (selectService > 0) {
					edit_maketcuoi.setText("");
					locationCode = "";
					serviceId = arrServiceBeans.get(selectService)
							.getTelecomServiceId();
					Log.d("telecomserviceId", serviceId);
					serviceType = arrServiceBeans.get(selectService)
							.getServiceType();
					Log.d("serviceType", serviceType);
					if (serviceType.equals("F")) {
						lnthuebaochu.setVisibility(View.GONE);
					} else {
						lnthuebaochu.setVisibility(View.VISIBLE);
					}

					if (serviceType.equals("A") || serviceType.equals("P")) {
						linearhatangghep.setVisibility(View.VISIBLE);
					} else {
						linearhatangghep.setVisibility(View.GONE);
					}
					if (CommonActivity.isNetworkConnected(getActivity())) {
						if (serviceType != null && !serviceType.isEmpty()) {
							if (arrTechologyBeans != null
									&& arrTechologyBeans.size() > 0) {
								arrTechologyBeans.clear();
							}

							// ===== check thue bao chu ==============
							if (cusID == null || cusID.equals("")) {
								if ("I".equalsIgnoreCase(serviceType)
										|| "N".equalsIgnoreCase(serviceType)) {
									initTechologyIN(serviceType);
								} else {
									initTechology(serviceType);
								}
							} else {
								initTechology(serviceType);
							}

							GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(
									getActivity());
							getListPakageAsyn.execute(serviceId);
							if (arrHostAccountBeans != null
									&& arrHostAccountBeans.size() > 0) {
								arrHostAccountBeans.clear();
							}
							if (serviceType.equals("E")
									|| serviceType.equals("I")
									|| serviceType.equals("N")
									|| serviceType.equals("T") || serviceType.equals("U")) {
								if (cusID != null && !cusID.equals("")) {

									GetListHostAccountIEN getListHostAccount = new GetListHostAccountIEN(
											getActivity());
									getListHostAccount
											.execute(cusID, serviceId);
								} else {

									if (serviceType.equals("E")) {
										if (cusID == null) {
											CommonActivity
													.createAlertDialog(
															getActivity(),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.checkcusidOld),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.app_name))
													.show();
										} else {
											CommonActivity
													.createAlertDialog(
															getActivity(),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.checkcusid),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.app_name))
													.show();
										}

									}

								}

							}

						}
					} else {
						CommonActivity.createAlertDialog(
								getActivity(),
								getActivity().getResources().getString(
										R.string.errorNetwork),
								getActivity().getResources().getString(
										R.string.app_name)).show();
					}

				} else {

					pakageCharge = "";
					examineJoin = "";
					techology = "";
					productCode = "";
					idDuongDay = "";
					offerId = "";
					serviceId = "";
					accHostAcc = "";
					isdnHostAcc = "";
					paramId = "";
					relationType = "";
					mainSubId = "";
					msType = "";
					productCodeMt = "";
					serviceType = "";
					if (arrPakageChargeBeans != null
							&& arrPakageChargeBeans.size() > 0) {
						arrPakageChargeBeans.clear();
						initpakagecharge();
					}

					if (arrTechologyBeans != null
							&& arrTechologyBeans.size() > 0) {
						arrTechologyBeans.clear();
						ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getActivity(),
                                android.R.layout.simple_dropdown_item_1line,
                                android.R.id.text1);
						spinner_congnghe.setAdapter(adapter);
					}

					linearGpon.setVisibility(View.GONE);
					linearkhacGpon.setVisibility(View.GONE);
					lnLoadmap.setVisibility(View.VISIBLE);
					lncongnghe.setVisibility(View.GONE);
					lnspinerTechnologgy.setVisibility(View.VISIBLE);
					edit_maketcuoi.setText("");
					btncheckConectorCode.setVisibility(View.VISIBLE);
					checkConnectorCode = "";
					edit_maketcuoi.setEnabled(true);

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				selectService = 0;
			}
		});

		spinner_pakage_charge
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						selectPakageCharge = arg2;
						if (selectPakageCharge > 0) {

							pakageChargeBeans = arrPakageChargeBeans
									.get(selectPakageCharge);
							if (pakageChargeBeans != null
									&& !CommonActivity.isNullOrEmpty(pakageChargeBeans
											.getDescription())) {
//								CommonActivity.createAlertDialog(getActivity(),
//										pakageChargeBeans.getDescription(),
//										getActivity().getString(R.string.app_name)).show();
								btninfo.setVisibility(View.VISIBLE);
							}else{
								btninfo.setVisibility(View.GONE);
							}
							
							
							// check mo hinh va check vendor
							groupProductId = arrPakageChargeBeans.get(
									selectPakageCharge).getGroupProductId();
							Log.d("groupProductIdddddddddddddd", groupProductId);
							if ( ("23".equals(groupProductId) && "U"
											.equals(serviceType))) {
								lnmohinh.setVisibility(View.VISIBLE);
								// TODO XU LY CHO NAY
							} 
							mstNoRf = arrPakageChargeBeans.get(
									selectPakageCharge).getMstNoRf();
//							else {
//								lnmohinh.setVisibility(View.GONE);
//							}

							pakageCharge = arrPakageChargeBeans.get(
									selectPakageCharge).getOfferCode();
							Log.d("pakageCharge", pakageCharge);

							productCodeMt = arrPakageChargeBeans.get(
									selectPakageCharge).getProductCode();
							msType = arrPakageChargeBeans.get(
									selectPakageCharge).getMsType();
							productCode = arrPakageChargeBeans.get(
									selectPakageCharge).getOfferCode();
							// Log.d("productCode", productCode);
							offerId = arrPakageChargeBeans.get(
									selectPakageCharge).getOfferId();
							// Log.d("offerId", offerId);

							if (techology != null && !techology.isEmpty()) {
								if (techology.equalsIgnoreCase("4")) {
									getLisGroupForCusAndRelation();
								}
							}

							isFTTB = arrPakageChargeBeans.get(
									selectPakageCharge).getIsFTTB();

						} else {

							
							btninfo.setVisibility(View.GONE);
							pakageChargeBeans = null;

							groupProductId = "";
							msType = "";
							offerId = "";
							productCodeMt = "";
							pakageCharge = "";
							isFTTB = "";
							mstNoRf="";
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						selectPakageCharge = 0;
					}
				});

		spinner_congnghe
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						idDuongDay = "";
						relationType = "";
						mainSubId = "";
						selectTechology = arg2;
						Log.d("selectTechology", "" + selectTechology);

						techology = arrTechologyBeans.get(selectTechology)
								.getParValue();
						if (techology != null && !techology.equals("")) {
							if (techology.equals("4")) {
								// loai gpon
								linearGpon.setVisibility(View.VISIBLE);
								linearkhacGpon.setVisibility(View.GONE);
								// call webservice lay thong tin duong day
								getLisGroupForCusAndRelation();

								// bo sung mo hinh
								if ("F".equals(serviceType)
										|| "N".equals(serviceType)
										|| "I".equals(serviceType)
										|| ("23".equals(groupProductId) && "U"
												.equals(serviceType))) {
									lnmohinh.setVisibility(View.VISIBLE);
									// TODO XU LY CHO NAY
								} else {
									lnmohinh.setVisibility(View.GONE);
								}

							} else {
								lnmohinh.setVisibility(View.GONE);

								linearGpon.setVisibility(View.GONE);
								linearkhacGpon.setVisibility(View.VISIBLE);
								lnLoadmap.setVisibility(View.VISIBLE);
								edit_maketcuoi.setText("");
								edit_maketcuoi.setEnabled(true);
								// === fill data for ha tang ghep======
								initExamineJoinSpin();

								if (serviceType.equals("I")
										|| serviceType.equals("N")) {
									if (cusID != null && !cusID.isEmpty()) {

									} else {
										if (cusID == null) {
											CommonActivity
													.createAlertDialog(
															getActivity(),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.checkcusidOld),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.app_name))
													.show();
										} else {
											CommonActivity
													.createAlertDialog(
															getActivity(),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.checkcusid),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.app_name))
													.show();
										}
									}

								}

							}
						} else {

							lnmohinh.setVisibility(View.GONE);
							pakageCharge = "";
							examineJoin = "";
							techology = "";
							productCode = "";
							idDuongDay = "";
							offerId = "";
							serviceId = "";
							accHostAcc = "";
							isdnHostAcc = "";
							paramId = "";
							relationType = "";
							mainSubId = "";
							msType = "";
							productCodeMt = "";

							linearGpon.setVisibility(View.GONE);
							linearkhacGpon.setVisibility(View.GONE);
							lnLoadmap.setVisibility(View.VISIBLE);
							lncongnghe.setVisibility(View.GONE);
							edit_maketcuoi.setText("");
							btncheckConectorCode.setVisibility(View.VISIBLE);
							checkConnectorCode = "";
							edit_maketcuoi.setEnabled(true);
						}

						// } else {
						// techology = "";
						// if(examineJoin != null || !examineJoin.isEmpty()){
						// examineJoin = "";
						// }
						// if(idDuongDay != null || !idDuongDay.isEmpty()){
						// idDuongDay = "";
						// }
						// if(accHostAcc != null || !accHostAcc.isEmpty()){
						// accHostAcc = "";
						// }
						// if(mainSubId != null || !mainSubId.isEmpty()){
						// mainSubId = "";
						// }
						// if(relationType != null || !relationType.isEmpty()){
						// relationType = "";
						// }
						// linearGpon.setVisibility(View.GONE);
						// linearkhacGpon.setVisibility(View.GONE);
						// // // === fill data for ha tang ghep======
						// // initExamineJoinSpin();
						// }

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						selectTechology = 0;
					}
				});

		spinner_hatang_ghep
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 > 0) {
							examineJoin = arrExamineJoinBeans.get(arg2)
									.getParType();
							Log.d("examineJoin", examineJoin);

							if (examineJoin.equals("A/P")
									|| examineJoin.equals("P/A")
									|| examineJoin.equals("A+P")) {
								// ================= Get Asyntask Load
								// Examine Join =====
								// ====telecomserviceId================
								// ====== cusid=======
								if (arrHostAccountBeans != null
										&& arrHostAccountBeans.size() > 0) {
									arrHostAccountBeans.clear();
								}
								lnthuebaochu.setVisibility(View.VISIBLE);
								if (cusID != null && !cusID.equals("")) {
									if (CommonActivity
                                            .isNetworkConnected(getActivity())) {
										GetListHostAccount getListHostAccount = new GetListHostAccount(
												getActivity());
										getListHostAccount.execute(cusID,
												examineJoin);
									} else {

										CommonActivity
												.createAlertDialog(
														getActivity(),
														getActivity()
																.getResources()
																.getString(
																		R.string.errorNetwork),
														getActivity()
																.getResources()
																.getString(
																		R.string.app_name))
												.show();
									}
								} else {

									if (cusID == null) {
										CommonActivity
												.createAlertDialog(
														getActivity(),
														getActivity()
																.getResources()
																.getString(
																		R.string.checkcusidOld),
														getActivity()
																.getResources()
																.getString(
																		R.string.app_name))
												.show();
									} else {
										CommonActivity
												.createAlertDialog(
														getActivity(),
														getActivity()
																.getResources()
																.getString(
																		R.string.checkcusid),
														getActivity()
																.getResources()
																.getString(
																		R.string.app_name))
												.show();
									}

								}
							} else {
								if (arrTechologyBeans != null
										&& arrTechologyBeans.size() > 0) {
									techology = arrTechologyBeans.get(0)
											.getParValue();
								}

								checkConnectorCode = "";
								btncheckConectorCode
										.setVisibility(View.VISIBLE);
								edit_maketcuoi.setText("");
								edit_maketcuoi.setEnabled(true);
								lnLoadmap.setVisibility(View.VISIBLE);
								lncongnghe.setVisibility(View.GONE);
								lnspinerTechnologgy.setVisibility(View.VISIBLE);
								lnthuebaochu.setVisibility(View.GONE);
								if (arrHostAccountBeans != null
										&& arrHostAccountBeans.size() > 0) {
									arrHostAccountBeans.clear();
									initListHostAcount();
								}

							}

						} else {
							checkConnectorCode = "";
							btncheckConectorCode.setVisibility(View.VISIBLE);
							edit_maketcuoi.setText("");
							examineJoin = "";
							edit_maketcuoi.setEnabled(true);
							lnLoadmap.setVisibility(View.VISIBLE);
							lncongnghe.setVisibility(View.GONE);
							lnspinerTechnologgy.setVisibility(View.VISIBLE);
							lnthuebaochu.setVisibility(View.GONE);
							if (arrHostAccountBeans != null
									&& arrHostAccountBeans.size() > 0) {
								arrHostAccountBeans.clear();
								initListHostAcount();
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		spinner_duongday
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// /============ chon id ma duong day ===========
						idDuongDay = arrAccountForCusBeans.get(arg2).getId();
						if (idDuongDay != null && !idDuongDay.isEmpty()) {
							Log.d("idDuongDay", idDuongDay);
							if (idDuongDay.equals("-1")) {
								lnLoadmap.setVisibility(View.VISIBLE);
								lnthuebaochinh.setVisibility(View.GONE);
								edit_maketcuoi.setText("");
								edit_maketcuoi.setEnabled(true);
								checkConnectorCode = "";
								btncheckConectorCode
										.setVisibility(View.VISIBLE);
								initListRelationNew(idDuongDay);
								spinner_lienket.setEnabled(false);
								if (mainSubId != null && !mainSubId.isEmpty()) {
									mainSubId = "";
								}
								if ("F".equals(serviceType)
										|| "N".equals(serviceType)
										|| "I".equals(serviceType)
										|| ("23".equals(groupProductId) && "U"
												.equals(serviceType))) {
									spinner_mohinh.setEnabled(true);
								}
							} else {
								lnLoadmap.setVisibility(View.GONE);
								spinner_lienket.setEnabled(true);
								String mainSubIdPrivate = arrAccountForCusBeans
										.get(arg2).getRepresentSubId();
								if (CommonActivity
                                        .isNetworkConnected(getActivity())) {
									edit_maketcuoi.setText("");
									GetInfraByForSubAsyn getInfraByForSubAsyn = new GetInfraByForSubAsyn(
											getActivity());
									getInfraByForSubAsyn.execute(
											mainSubIdPrivate, serviceType,
											lineType);

									if (relationType != null
											&& !relationType.isEmpty()) {
										if (relationType.equals("GPDL")) {
											mainSubId = "";
											lnthuebaochinh
													.setVisibility(View.GONE);
											if (arrMainSubGponBeans != null
													&& arrMainSubGponBeans
															.size() > 0) {
												arrMainSubGponBeans.clear();
												initListMainSubId();
											}
										} else {
											if (arrMainSubGponBeans != null
													&& arrMainSubGponBeans
															.size() > 0) {
												arrMainSubGponBeans.clear();
											}
											GetListMainSubGpon getListMainSubGponAsyn = new GetListMainSubGpon(
													getActivity());
											getListMainSubGponAsyn.execute(
													serviceType, idDuongDay,
													relationType);
										}
									}

								} else {
									lineType = "";
									CommonActivity
											.createAlertDialog(
													getActivity(),
													getActivity()
															.getResources()
															.getString(
																	R.string.errorNetwork),
													getActivity()
															.getResources()
															.getString(
																	R.string.app_name))
											.show();
								}

							}

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		spinner_lienket.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// if (arg2 > 0) {
				relationType = arrRelationGponBeans.get(arg2).getParamValue();
				// Log.d("relationType", relationType);
				lineType = relationType;
				if (idDuongDay != null && !idDuongDay.isEmpty()) {
					if (idDuongDay.equals("-1")) {
						if (relationType.equals("GPDL")) {

						} else {
							CommonActivity.createAlertDialog(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checklienketmoi),
									getActivity().getResources().getString(
											R.string.app_name)).show();
						}
					} else {
						if (relationType.equals("GPDL")) {
							// duong day cu va GPDL thi ko can check thue bao
							// chinh
							lnthuebaochinh.setVisibility(View.GONE);
							edit_maketcuoi.setEnabled(true);
							if (mainSubId != null && !mainSubId.isEmpty()) {
								mainSubId = "";
							}
						} else {
							edit_maketcuoi.setEnabled(false);
							lnthuebaochinh.setVisibility(View.VISIBLE);
							if (CommonActivity
                                    .isNetworkConnected(getActivity())) {
								if (arrMainSubGponBeans != null
										&& arrMainSubGponBeans.size() > 0) {
									arrMainSubGponBeans.clear();
								}
								GetListMainSubGpon getListMainSubGponAsyn = new GetListMainSubGpon(
										getActivity());
								getListMainSubGponAsyn.execute(serviceType,
										idDuongDay, relationType);
							} else {
								CommonActivity.createAlertDialog(
										getActivity(),
										getActivity().getResources().getString(
												R.string.errorNetwork),
										getActivity().getResources().getString(
												R.string.app_name)).show();
							}
						}

					}
				}

				// }

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spinner_thuebaochinh
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						if (arrMainSubGponBeans != null
								&& arrMainSubGponBeans.size() > 0) {
							mainSubId = arrMainSubGponBeans.get(arg2)
									.getParentSubId();
						}
						// if(CommonActivity.isNetworkConnected(getActivity())
						// == true){
						// GetInfraByForSubAsyn getInfraByForSubAsyn = new
						// GetInfraByForSubAsyn(getActivity());
						// getInfraByForSubAsyn.execute(mainSubId);
						// }else{
						// CommonActivity.createAlertDialog(getActivity(),
						// getActivity().getResources().getString(R.string.errorNetwork),
						// getActivity().getResources().getString(R.string.app_name)).show();
						// }
						//

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		spinner_thuebaochu
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						if (arg2 > 0) {
							accHostAcc = arrHostAccountBeans.get(arg2)
									.getSubId();
							if (accHostAcc != null && !accHostAcc.isEmpty()) {
								lncongnghe.setVisibility(View.VISIBLE);
								lnspinerTechnologgy.setVisibility(View.GONE);
							} else {
								lnspinerTechnologgy.setVisibility(View.VISIBLE);
								lncongnghe.setVisibility(View.GONE);
							}
							Log.d("accHostAcc", accHostAcc);
							techology = arrHostAccountBeans.get(arg2)
									.getTehcology();
							try {
								GetTechologyDal dal = new GetTechologyDal(
										getActivity());
								dal.open();
								if (techology != null && !techology.isEmpty()) {
									String nameTech = dal.getNameTechology(
											serviceType, techology);
									dal.close();
									if (nameTech != null && !nameTech.isEmpty()) {
										lncongnghe.setVisibility(View.VISIBLE);
										lnspinerTechnologgy
												.setVisibility(View.GONE);
										txtcongnghe.setText(nameTech);
									} else {
										lncongnghe.setVisibility(View.VISIBLE);
										lnspinerTechnologgy
												.setVisibility(View.GONE);
										txtcongnghe.setText("");
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

							if (CommonActivity
                                    .isNetworkConnected(getActivity())) {
								edit_maketcuoi.setText("");
								GetInfraByForSubAsyn getInfraByForSubAsyn = new GetInfraByForSubAsyn(
										getActivity());
								getInfraByForSubAsyn.execute(accHostAcc,
										serviceType, lineType);
							} else {

								CommonActivity.createAlertDialog(
										getActivity(),
										getActivity().getResources().getString(
												R.string.errorNetwork),
										getActivity().getResources().getString(
												R.string.app_name)).show();
							}
						}else{
							accHostAcc = "";
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		btnupdate.setOnClickListener(this);
		btnloadbando.setOnClickListener(this);

	}

	@Override
	public void onResume() {

		super.onResume();
	}

	// lay danh sach duong day va lien ket
    private void getLisGroupForCusAndRelation() {

		// if (cusID != null
		// && !cusID.isEmpty()) {
		if (serviceType != null && !serviceType.isEmpty()) {

			// === WEBSERVICE LAY THONG TIN DUONG
			// DAY ==========
			if (arrAccountForCusBeans != null
					&& arrAccountForCusBeans.size() > 0) {
				arrAccountForCusBeans.clear();
			}
			if (arrRelationGponBeans != null && arrRelationGponBeans.size() > 0) {
				arrRelationGponBeans.clear();
			}

			GetListGroupAccAndRelationAsyn getAccountForCustAsyn = new GetListGroupAccAndRelationAsyn(
					getActivity());
			getAccountForCustAsyn.execute(cusID, serviceType);

		} else {
			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checkserviceType), Toast.LENGTH_SHORT)
					.show();
		}

		// }
		// else {
		// // ========= check cusid
		// Toast.makeText(
		// getActivity(),
		// getActivity().getResources().getString(R.string.checkcusid),
		// Toast.LENGTH_SHORT).show();
		// }

	}

	// init get list host account (thue bao chu)
    private void initListHostAcount() {

		HostAccountBeans hostAccountBeans = new HostAccountBeans();
		hostAccountBeans.setAccount(this.getResources().getString(
				R.string.chonthuebaochu));
		arrHostAccountBeans.add(0, hostAccountBeans);

		if (arrHostAccountBeans != null && arrHostAccountBeans.size() > 0) {

			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (HostAccountBeans hosAccountBeans : arrHostAccountBeans) {
				adapter.add(hosAccountBeans.getAccount());
			}
			spinner_thuebaochu.setAdapter(adapter);
		}
	}

	// ============== init not data host account ===========
    private void initListHostAcountNotData() {

		HostAccountBeans hostAccountBeans = new HostAccountBeans();
		hostAccountBeans.setAccount(this.getResources().getString(
				R.string.chonthuebaochu));
		ArrayList<HostAccountBeans> lisHostAccountBeans = new ArrayList<>();
		lisHostAccountBeans.add(0, hostAccountBeans);

		if (lisHostAccountBeans != null && lisHostAccountBeans.size() > 0) {

			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (HostAccountBeans hosAccountBeans : lisHostAccountBeans) {
				adapter.add(hosAccountBeans.getAccount());
			}
			spinner_thuebaochu.setAdapter(adapter);
		}
	}

	private final OnClickListener ListHostAcountNotDataclicknotData = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			initListHostAcountNotData();
			// linearhatangghep.setVisibility(View.VISIBLE);
		}
	};
	private final OnClickListener ListHostAcountNotDataclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			initListHostAcountNotData();
		}
	};

	// init spiner hang tang ghep

	private void initExamineJoinSpin() {
		GetExamineJoinDal dal = new GetExamineJoinDal(getActivity());
		try {
			dal.open();
			arrExamineJoinBeans = dal.getListExamine(serviceType);
			dal.close();
		} catch (Exception e) {
			Log.d("initExamineJoinSpin", e.toString());
		}
		ExamineJoinBeans examineJoinBeans = new ExamineJoinBeans();
		examineJoinBeans.setParValue(getActivity().getResources().getString(
				R.string.chonhatangghep));
		arrExamineJoinBeans.add(0, examineJoinBeans);
		if (arrExamineJoinBeans != null && arrExamineJoinBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (ExamineJoinBeans exBeans : arrExamineJoinBeans) {
				adapter.add(exBeans.getParValue());
			}
			spinner_hatang_ghep.setAdapter(adapter);
		}
	}

	// init spiner NameProduct
    private void initNameProduct() {
		NameProductBeans nameProductBean = new NameProductBeans();
		nameProductBean.setGroupName(getActivity().getResources().getString(
				R.string.chonsanpham));
		arrNameProductBeans.add(0, nameProductBean);
		if (arrNameProductBeans != null && arrNameProductBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (NameProductBeans nameProductBeans : arrNameProductBeans) {
				adapter.add(nameProductBeans.getGroupName());
			}
			spinner_name_product.setAdapter(adapter);
		}
	}

	// init techchology1
    private void initTechology(String serviceType) {
		if (serviceType != null || !serviceType.equals("")) {
			GetTechologyDal dal = new GetTechologyDal(getActivity());
			try {
				dal.open();
				arrTechologyBeans = dal.getListTechology(serviceType);
				dal.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (arrTechologyBeans != null && arrTechologyBeans.size() > 0) {
			ArrayAdapter<String> adapterTech = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (TechologyBeans techologyBeans : arrTechologyBeans) {
				adapterTech.add(techologyBeans.getDescription());
			}
			spinner_congnghe.setAdapter(adapterTech);
		}
	}

	// init techchology1
    private void initTechologyIN(String serviceType) {
		GetTechologyDal dal = new GetTechologyDal(getActivity());
		try {
			dal.open();
			arrTechologyBeans = dal.getListTechology(serviceType);
			dal.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (arrTechologyBeans != null && arrTechologyBeans.size() > 0) {
			for (int i = 0; i < arrTechologyBeans.size(); i++) {
				if ("4".equals(arrTechologyBeans.get(i).getParValue())) {
					arrTechologyBeans.add(0, arrTechologyBeans.get(i));
					if (arrTechologyBeans.size() > 4) {
						arrTechologyBeans.remove(4);

					}
					break;
				} else {

				}
			}

		}

		if (arrTechologyBeans != null && arrTechologyBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (TechologyBeans techologyBeans : arrTechologyBeans) {
				adapter.add(techologyBeans.getDescription());
			}
			spinner_congnghe.setAdapter(adapter);
		}

	}

	// init spiner Service
    private void initService() {

		ServiceBeans serviceBeans = new ServiceBeans();
		serviceBeans.setGroupName(getActivity().getResources().getString(
				R.string.chondichvu));
		arrServiceBeans.add(0, serviceBeans);
		if (arrServiceBeans != null && arrServiceBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (ServiceBeans pakageChargeBeans : arrServiceBeans) {
				adapter.add(pakageChargeBeans.getGroupName());
			}
			spinner_service.setAdapter(adapter);
		}
	}

	// init spiner pakagecharge
    private void initpakagecharge() {
		PakageChargeBeans paBeans = new PakageChargeBeans();
		paBeans.setOfferName(getActivity().getResources().getString(
				R.string.chongoicuoc));
		arrPakageChargeBeans.add(0, paBeans);
		if (arrPakageChargeBeans != null && arrPakageChargeBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), R.layout.pakage_charge_item, R.id.txtpakage);
			adapter.setDropDownViewResource(R.layout.pakage_charge_item);
			for (PakageChargeBeans pakageChargeBeans : arrPakageChargeBeans) {
				adapter.add(pakageChargeBeans.getOfferName());
			}
			spinner_pakage_charge.setAdapter(adapter);
		}
	}

	// init spinner thuebaochinh
	private void initListMainSubId() {
		// MainSubGponBeans mainGponBeans = new MainSubGponBeans();
		// mainGponBeans.setAccount(getActivity().getResources().getString(
		// R.string.chonthuebaochinh));
		// arrMainSubGponBeans.add(0, mainGponBeans);
		if (arrMainSubGponBeans != null && arrMainSubGponBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (MainSubGponBeans mainBeans : arrMainSubGponBeans) {
				adapter.add(mainBeans.getAccount());
			}
			spinner_thuebaochinh.setAdapter(adapter);
		}
	}

	// init spinner thuebaochinh ko data
	private void initListMainSubIdNotData() {

		if (arrMainSubGponBeans != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);

			spinner_thuebaochinh.setAdapter(adapter);
		}
	}

	// init spinner danh sach duong day
	private void initListGroupGponAccount() {
		if (arrAccountForCusBeans != null && arrAccountForCusBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (GroupAccountForCusBean cusForCusBean : arrAccountForCusBeans) {
				adapter.add(cusForCusBean.getAccount());
			}
			spinner_duongday.setAdapter(adapter);
		}
	}

	// init spinner getListRelationGponForServiceType
	// init spiner danh sach lien kiet
	private void initListRelationGponForServiceType() {
		spinner_lienket.setVisibility(View.VISIBLE);
		if (arrRelationGponBeans != null && arrRelationGponBeans.size() > 0) {
			adapterRealation = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (RelationGponBean reGponBean : arrRelationGponBeans) {
				adapterRealation.add(reGponBean.getParamName());
			}
			spinner_lienket.setAdapter(adapterRealation);
		}
	}

	private void initListRelationNew(String idDuongday) {
		if (arrRelationGponBeans != null && arrRelationGponBeans.size() > 0) {
			for (int i = 0; i < arrRelationGponBeans.size(); i++) {
				if ("GPDL".equals(arrRelationGponBeans.get(i).getParamValue())) {
					arrRelationGponBeans.add(0, arrRelationGponBeans.get(i));
					arrRelationGponBeans.remove(i);
					break;
				} else {
				}
			}
		}
		if (arrRelationGponBeans != null && arrRelationGponBeans.size() > 0) {
			adapterRealation = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (RelationGponBean reGponBean : arrRelationGponBeans) {
				adapterRealation.add(reGponBean.getParamName());
			}
			spinner_lienket.setAdapter(adapterRealation);
		}

	}

	// init spinner danh sach thue bao chinh

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			if (MainActivity.getInstance() != null) {
//				MainActivity.getInstance().finish();
//			}
			  LoginDialog dialog = new LoginDialog(getActivity(),
                      ";cm.connect_sub_CD;");

              dialog.show();
		}
	};

	// ============== webservice get list thue bao chinh ---- getListMainSubGpon
	// input : serviceType: dá»‹ch vá»¥ (Báº¯t buá»™c) (A,F
	// â€¦)
	// gponGroupAccountId: mÃ£ Ä‘Æ°á»�ng dÃ¢y
	// relationType:LiÃªn káº¿t

	public class GetListMainSubGpon extends
			AsyncTask<String, Void, ArrayList<MainSubGponBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListMainSubGpon(Context context) {
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
		protected ArrayList<MainSubGponBeans> doInBackground(String... arg0) {
			return getLisMainSubGponBeans(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<MainSubGponBeans> result) {
			this.progress.dismiss();
			if (errorCode.equals("0")) {
				// arrMainSubGponBeans = result;
				if (result != null && result.size() > 0) {
					arrMainSubGponBeans = result;

					initListMainSubId();
				} else {
					arrMainSubGponBeans = new ArrayList<>();
					mainSubId = "";
					initListMainSubIdNotData();
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checknotthuebaochinh),
							getActivity().getResources().getString(
									R.string.app_name)).show();
				}

			} else {
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

		// ma duong day, service type, ma lien ket
		public ArrayList<MainSubGponBeans> getLisMainSubGponBeans(
				String serviceType, String gponGroupAccountId,
				String relationType) {
			ArrayList<MainSubGponBeans> lstMainSubGponBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListMainSubGpon");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListMainSubGpon>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("<gponGroupAccountId>").append(gponGroupAccountId);
				rawData.append("</gponGroupAccountId>");

				rawData.append("<relationType>").append(relationType);
				rawData.append("</relationType>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListMainSubGpon>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListMainSubGpon");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml from getlistMain
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = e2.getElementsByTagName("lstSubscriber");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						MainSubGponBeans subMainSubGponBeans = new MainSubGponBeans();
						String account = parse.getValue(e1, "account");
						Log.d("account", account);
						subMainSubGponBeans.setAccount(account);
						String parentSubId = parse.getValue(e1, "subId");
						Log.d("parentSubId", parentSubId);
						subMainSubGponBeans.setParentSubId(parentSubId);
						lstMainSubGponBeans.add(subMainSubGponBeans);
					}
				}

			} catch (Exception e) {
				Log.d("GetListMainSubGpon", e.toString());
			}

			return lstMainSubGponBeans;

		}

	}

	// ======= webservice gop lay duong day - lien ket

	public class GetListGroupAccAndRelationAsyn extends
			AsyncTask<String, Void, Void> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListGroupAccAndRelationAsyn(Context context) {
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
		protected Void doInBackground(String... arg0) {
			GetListGroupAccAndRelation(arg0[0], arg0[1]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (arrRelationGponBeans.size() > 0) {
					initListRelationGponForServiceType();
				} else {
					// initListRelationGponForServiceTypeNotData();
				}
				if (arrAccountForCusBeans.size() > 0) {
					initListGroupGponAccount();
				} else {
					// initListGroupGponAccountNotdata();
				}

			} else {
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

		private void GetListGroupAccAndRelation(String cusId, String serviceType) {

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListGroupAccountForCust");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListGroupAccountForCust>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				// ====== add cusid and telecomserviceId ====
				if (cusId == null || cusId.equals("")) {
					rawData.append("<custId>" + "");
					rawData.append("</custId>");
				} else {
					rawData.append("<custId>").append(cusId);
					rawData.append("</custId>");
				}

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListGroupAccountForCust>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListGroupAccountForCust");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// parse xml from original

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodeRelation = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = e2.getElementsByTagName("lstGroupAccount");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						GroupAccountForCusBean grouAccountForCusBean = new GroupAccountForCusBean();
						String account = parse.getValue(e1, "account");
						Log.d("account", account);
						grouAccountForCusBean.setAccount(account);

						String custId = parse.getValue(e1, "custId");
						Log.d("custId", custId);
						grouAccountForCusBean.setCustId(custId);

						String groupType = parse.getValue(e1, "groupType");
						Log.d("groupType", groupType);
						grouAccountForCusBean.setGroupType(groupType);

						String id = parse.getValue(e1, "id");
						Log.d("id", id);
						grouAccountForCusBean.setId(id);

						String representSubId = parse.getValue(e1,
								"representSubId");
						Log.d("representSubId", representSubId);
						grouAccountForCusBean.setRepresentSubId(representSubId);

						String serviceType1 = parse.getValue(e1, "serviceType");
						Log.d("serviceType", serviceType);
						grouAccountForCusBean.setServiceType(serviceType1);

						String status = parse.getValue(e1, "status");
						Log.d("status", status);
						grouAccountForCusBean.setStatus(status);

						arrAccountForCusBeans.add(grouAccountForCusBean);
					}
					nodeRelation = e2.getElementsByTagName("lstApParam");
					for (int j = 0; j < nodeRelation.getLength(); j++) {
						Element e3 = (Element) nodeRelation.item(j);

						RelationGponBean relationGponBean = new RelationGponBean();
						String paramId = parse.getValue(e3, "paramId");
						Log.d("paramId", paramId);
						relationGponBean.setParamId(paramId);

						String paramType = parse.getValue(e3, "paramType");
						Log.d("paramType", paramType);
						relationGponBean.setParamType(paramType);

						String paramCode = parse.getValue(e3, "paramCode");
						Log.d("paramCode", paramCode);
						relationGponBean.setParamCode(paramCode);

						String paramName = parse.getValue(e3, "paramName");
						Log.d("paramName", paramName);
						relationGponBean.setParamName(paramName);

						String paramValue = parse.getValue(e3, "paramValue");
						Log.d("paramValue", paramValue);
						relationGponBean.setParamValue(paramValue);

						String status = parse.getValue(e3, "status");
						Log.d("status", status);
						relationGponBean.setStatus(status);

						arrRelationGponBeans.add(relationGponBean);
					}
				}
			} catch (Exception e) {
				Log.d("getListGroupAccountForCust", e.toString());
			}
		}
	}

	// ============== webservice get list duong day ---
	// getListGroupAccountForCust
	// input : custId: MÃ£ khÃ¡ch hÃ ng
	// serviceType: dá»‹ch vá»¥ (A,F â€¦)

	public class GetListGroupAccountForCustAsyn extends
			AsyncTask<String, Void, ArrayList<GroupAccountForCusBean>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListGroupAccountForCustAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context); // check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<GroupAccountForCusBean> doInBackground(
				String... arg0) {
			return getListGroupAccountForCust(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<GroupAccountForCusBean> result) {

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					arrAccountForCusBeans = result;
					initListGroupGponAccount();
				} else {
					// initListGroupGponAccountNotdata();
				}

			} else {
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

		private ArrayList<GroupAccountForCusBean> getListGroupAccountForCust(
				String cusId, String serviceType) {

			ArrayList<GroupAccountForCusBean> lstGroupAccountForCusBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListGroupAccountForCust");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListGroupAccountForCust>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken)); // ====== add cusid
															// and
				rawData.append("<custId>").append(cusId);
				rawData.append("</custId>");
				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListGroupAccountForCust>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListGroupAccountForCust");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml from original

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = e2.getElementsByTagName("lstGroupAccount");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						GroupAccountForCusBean grouAccountForCusBean = new GroupAccountForCusBean();
						String account = parse.getValue(e1, "account");
						Log.d("account", account);
						grouAccountForCusBean.setAccount(account);

						String custId = parse.getValue(e1, "custId");
						Log.d("custId", custId);
						grouAccountForCusBean.setCustId(custId);

						String groupType = parse.getValue(e1, "groupType");
						Log.d("groupType", groupType);
						grouAccountForCusBean.setGroupType(groupType);

						String id = parse.getValue(e1, "id");
						Log.d("id", id);
						grouAccountForCusBean.setId(id);

						String rdRt = parse.getValue(e1, "rdRt");
						Log.d("rdRt", rdRt);
						grouAccountForCusBean.setRdRt(rdRt);

						String representIsdn = parse.getValue(e1,
								"representIsdn");
						Log.d("representIsdn", representIsdn);
						grouAccountForCusBean.setRepresentIsdn(representIsdn);

						String representSubId = parse.getValue(e1,
								"representSubId");
						Log.d("representSubId", representSubId);
						grouAccountForCusBean.setRepresentSubId(representSubId);

						String serviceType1 = parse.getValue(e1, "serviceType");
						Log.d("serviceType", serviceType);
						grouAccountForCusBean.setServiceType(serviceType1);

						String status = parse.getValue(e1, "status");
						Log.d("status", status);
						grouAccountForCusBean.setStatus(status);

						String vrfName = parse.getValue(e1, "vrfName");
						Log.d("vrfName", vrfName);
						grouAccountForCusBean.setVrfName(vrfName);

						lstGroupAccountForCusBeans.add(grouAccountForCusBean);
					}
				}
			} catch (Exception e) {
				Log.d("getListGroupAccountForCust", e.toString());
			}

			return lstGroupAccountForCusBeans;

		}

	}

	// get list thue bao chu theo dich vu i,e,n,T
	public class GetListHostAccountIEN extends
			AsyncTask<String, Void, ArrayList<HostAccountBeans>> {
		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListHostAccountIEN(Context context) {
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
		protected ArrayList<HostAccountBeans> doInBackground(String... arg0) {
			return getListHostAcount(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<HostAccountBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					// =========== data list host account ============
					arrHostAccountBeans = result;
					initListHostAcount();
					linearhatangghep.setVisibility(View.GONE);

				} else {
					// =========== not data host acount ==========
					if (serviceType.equals("T")) {

					} else {
						CommonActivity.createAlertDialog(
								getActivity(),
								getActivity().getResources().getString(
										R.string.notHostAcc),
								getActivity().getResources().getString(
										R.string.app_name),
								ListHostAcountNotDataclicknotData).show();
					}

				}
			} else {
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

		private ArrayList<HostAccountBeans> getListHostAcount(String cusid,
				String telecomserviceId) {
			ArrayList<HostAccountBeans> lisAccountBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListHostAccount");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListHostAccount>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				// ====== add cusid and telecomserviceId ====
				rawData.append("<custId>").append(cusid);
				rawData.append("</custId>");
				rawData.append("<telecomServiceId>").append(telecomserviceId);
				rawData.append("</telecomServiceId>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListHostAccount>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListHostAccount");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);

				// ==== parse xml from original ===========

				HostAccountHandlerIEN handler = (HostAccountHandlerIEN) CommonActivity
						.parseXMLHandler(new HostAccountHandlerIEN(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (output.getErrorCode() != null) {
					errorCode = output.getErrorCode();
				}
				if (output.getDescription() != null) {
					description = output.getDescription();
				}
				lisAccountBeans = handler.getLstData();

				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// Log.d("errorCode", errorCode);
				// description = parse.getValue(e2, "description");
				// Log.d("description", description);
				// nodechild = e2.getElementsByTagName("lstAllTelServiceSub");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				// Element e1 = (Element) nodechild.item(j);
				// HostAccountBeans hostAccountBeans = new HostAccountBeans();
				// String account = parse.getValue(e1, "account");
				// Log.d("account", account);
				// hostAccountBeans.setAccount(account);
				//
				// String actStatus = parse.getValue(e1, "actStatus");
				// Log.d("actStatus", actStatus);
				// hostAccountBeans.setActStatus(actStatus);
				//
				// String actStatusBits = parse.getValue(e1,
				// "actStatusBits");
				// Log.d("actStatusBits", actStatusBits);
				// hostAccountBeans.setActStatusBits(actStatusBits);
				//
				// String contractId = parse.getValue(e1, "contractId");
				// Log.d("contractId", contractId);
				// hostAccountBeans.setContractId(contractId);
				//
				// String isdn = parse.getValue(e1, "isdn");
				// Log.d("isdn", isdn);
				// hostAccountBeans.setIsdn(isdn);
				//
				// String productCode = parse.getValue(e1, "productCode");
				// Log.d("productCode", productCode);
				// hostAccountBeans.setProductCode(productCode);
				//
				// String service = parse.getValue(e1, "service");
				// Log.d("service", service);
				// hostAccountBeans.setService(service);
				//
				// String serviceType = parse.getValue(e1, "serviceType");
				// Log.d("serviceType", serviceType);
				// hostAccountBeans.setServiceType(serviceType);
				//
				// String status = parse.getValue(e1, "status");
				// Log.d("status", status);
				// hostAccountBeans.setStatus(status);
				//
				// String statusId = parse.getValue(e1, "statusId");
				// Log.d("statusId", statusId);
				// hostAccountBeans.setStatusId(statusId);
				//
				// String subId = parse.getValue(e1, "subId");
				// Log.d("subId", subId);
				// hostAccountBeans.setSubId(subId);
				//
				// String techology = parse.getValue(e1, "technology");
				// hostAccountBeans.setTehcology(techology);
				// // add list account bean
				// lisAccountBeans.add(hostAccountBeans);
				//
				// }
				// }
			} catch (Exception e) {
				Log.d("GetListHostAccount", e.toString());
			}
			return lisAccountBeans;
		}

	}

	// ============= get list thue bao chu ================
	public class GetListHostAccount extends
			AsyncTask<String, Void, ArrayList<HostAccountBeans>> {
		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListHostAccount(Context context) {
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
		protected ArrayList<HostAccountBeans> doInBackground(String... arg0) {
			return getListHostAcount(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<HostAccountBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					// =========== data list host account ============
					arrHostAccountBeans = result;
					initListHostAcount();
				} else {
					// =========== not data host acount ==========
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.notHostAcc),
							getActivity().getResources().getString(
									R.string.app_name),
							ListHostAcountNotDataclick).show();
				}
			} else {
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

		private ArrayList<HostAccountBeans> getListHostAcount(String cusid,
				String offerId) {
			ArrayList<HostAccountBeans> lisAccountBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListHostAccountByLineType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListHostAccountByLineType>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				// ====== add cusid and telecomserviceId ====
				rawData.append("<custId>").append(cusid);
				rawData.append("</custId>");
				rawData.append("<lineType>").append(offerId);
				rawData.append("</lineType>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListHostAccountByLineType>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListHostAccountByLineType");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ==== parse xml from original ===========

				HostAccountHandler handler = (HostAccountHandler) CommonActivity
						.parseXMLHandler(new HostAccountHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (output.getErrorCode() != null) {
					errorCode = output.getErrorCode();
				}
				if (output.getDescription() != null) {
					description = output.getDescription();
				}
				lisAccountBeans = handler.getLstData();

				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// Log.d("errorCode", errorCode);
				// description = parse.getValue(e2, "description");
				// Log.d("description", description);
				// nodechild = e2.getElementsByTagName("lstAllTelServiceSub");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				// Element e1 = (Element) nodechild.item(j);
				// HostAccountBeans hostAccountBeans = new HostAccountBeans();
				// String account = parse.getValue(e1, "account");
				// Log.d("account", account);
				// hostAccountBeans.setAccount(account);
				//
				// String actStatus = parse.getValue(e1, "actStatus");
				// Log.d("actStatus", actStatus);
				// hostAccountBeans.setActStatus(actStatus);
				//
				// String actStatusBits = parse.getValue(e1,
				// "actStatusBits");
				// Log.d("actStatusBits", actStatusBits);
				// hostAccountBeans.setActStatusBits(actStatusBits);
				//
				// String contractId = parse.getValue(e1, "contractId");
				// Log.d("contractId", contractId);
				// hostAccountBeans.setContractId(contractId);
				//
				// String isdn = parse.getValue(e1, "isdn");
				// Log.d("isdn", isdn);
				// hostAccountBeans.setIsdn(isdn);
				//
				// String productCode = parse.getValue(e1, "productCode");
				// Log.d("productCode", productCode);
				// hostAccountBeans.setProductCode(productCode);
				//
				// String service = parse.getValue(e1, "service");
				// Log.d("service", service);
				// hostAccountBeans.setService(service);
				//
				// String serviceType = parse.getValue(e1, "serviceType");
				// Log.d("serviceType", serviceType);
				// hostAccountBeans.setServiceType(serviceType);
				//
				// String status = parse.getValue(e1, "status");
				// Log.d("status", status);
				// hostAccountBeans.setStatus(status);
				//
				// String statusId = parse.getValue(e1, "statusId");
				// Log.d("statusId", statusId);
				// hostAccountBeans.setStatusId(statusId);
				//
				// String subId = parse.getValue(e1, "subId");
				// Log.d("subId", subId);
				// hostAccountBeans.setSubId(subId);
				//
				// // add list account bean
				// lisAccountBeans.add(hostAccountBeans);
				//
				// }
				// }
			} catch (Exception e) {
				Log.d("GetListHostAccount", e.toString());
			}
			return lisAccountBeans;
		}

	}

	// ======= get list PakageCharge getListProductByTelecomService
	public class GetListPakageAsyn extends
			AsyncTask<String, Void, ArrayList<PakageChargeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListPakageAsyn(Context context) {
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
		protected ArrayList<PakageChargeBeans> doInBackground(String... params) {
			return sendRequestGetListService(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<PakageChargeBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {

					arrPakageChargeBeans = result;

					initpakagecharge();

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notgoicuoc),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
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

		// =====method get list service ========================
		public ArrayList<PakageChargeBeans> sendRequestGetListService(
				String telecomserviceId) {
			String original = null;
			ArrayList<PakageChargeBeans> lisPakageChargeBeans = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListProductByTelecomService");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListProductByTelecomService>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<telecomServiceId>").append(telecomserviceId);
				rawData.append("</telecomServiceId>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListProductByTelecomService>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListProductByTelecomService");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original.toString());
				// ========parser xml get employ from server

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = e2.getElementsByTagName("lstProductOfferCM");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						PakageChargeBeans paChargeBeans = new PakageChargeBeans();
						String groupProductId = parse.getValue(e1,
								"groupProductId");
						Log.d("groupProductId", groupProductId);
						paChargeBeans.setGroupProductId(groupProductId);
						String offerCode = parse.getValue(e1, "offerCode");
						Log.d("offerCode", offerCode);
						paChargeBeans.setOfferCode(offerCode);
						String offerId = parse.getValue(e1, "offerId");
						Log.d("offerId", offerId);
						paChargeBeans.setOfferId(offerId);
						String offerName = parse.getValue(e1, "offerName");
						Log.d("offerName", offerName);
						paChargeBeans.setOfferName(offerName);
						String productId = parse.getValue(e1, "productId");
						paChargeBeans.setProductId(productId);
						String msType = parse.getValue(e1, "mstType");
						paChargeBeans.setMsType(msType);

						String productCode = parse.getValue(e1, "productCode");
						paChargeBeans.setProductCode(productCode);

						String isFttb = parse.getValue(e1, "isFTTB");
						Log.d("isFttb_pasre", isFttb);
						paChargeBeans.setIsFTTB(isFttb);
						
						String des = parse.getValue(e1, "description");
						if(CommonActivity.isNullOrEmpty(paChargeBeans.getDescription())){
							paChargeBeans.setDescription(des);
						}
						String mstNoRf = parse.getValue(e1, "mstNoRf");
						paChargeBeans.setMstNoRf(mstNoRf);
						lisPakageChargeBeans.add(paChargeBeans);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisPakageChargeBeans;
		}

	}

	// ====== get list Service getListGroupProductByParentId

	public class GetListServiceAsyn extends
			AsyncTask<String, Void, ArrayList<ServiceBeans>> {

		ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListServiceAsyn(Context context) {
			this.context = context;
			btnservice
					.setVisibility(View.GONE);
			prbservice.setVisibility(
					View.VISIBLE);
			// this.progress = new ProgressDialog(this.context);
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		@Override
		protected ArrayList<ServiceBeans> doInBackground(String... params) {
			return sendRequestGetListService(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ServiceBeans> result) {
			// progress.dismiss();
			prbservice
					.setVisibility(View.GONE);
			btnservice.setVisibility(
					View.GONE);

			if (errorCode.equals("0")) {
				if (result.size() > 0 && !result.isEmpty()) {

					arrServiceBeans = result;

					initService();

					// reload data
					// reloadData(1);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notdichvu),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
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

		// =====method get list service ========================
		public ArrayList<ServiceBeans> sendRequestGetListService(String parentID) {
			String original = null;
			ArrayList<ServiceBeans> lsPakageChargeBeans = new ArrayList<>();
			try {

//				lsPakageChargeBeans = new CacheDatabaseManager(context)
//						.getListService();
//				if (lsPakageChargeBeans != null
//						&& !lsPakageChargeBeans.isEmpty()) {
//					errorCode = "0";
//					return lsPakageChargeBeans;
//				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListGroupProductByParentId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListGroupProductByParentId>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<parentId>").append(parentID);
				rawData.append("</parentId>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListGroupProductByParentId>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListGroupProductByParentId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ========parser xml get employ from server

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = e2
							.getElementsByTagName("lstGroupProductTelecomService");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ServiceBeans pakageChargeBeans = new ServiceBeans();
						String groupName = parse.getValue(e1, "groupName");
						Log.d("groupName", groupName);
						pakageChargeBeans.setGroupName(groupName);
						String groupProductId = parse.getValue(e1,
								"groupProductId");
						Log.d("groupProductId", groupProductId);
						pakageChargeBeans.setGroupProductId(groupProductId);
						String parentId = parse.getValue(e1, "parentId");
						Log.d("parentId", parentId);
						pakageChargeBeans.setParentId(parentId);
						String telecomServiceId = parse.getValue(e1,
								"telecomServiceId");
						Log.d("telecomServiceId", telecomServiceId);
						pakageChargeBeans.setTelecomServiceId(telecomServiceId);
						String serviceType = parse.getValue(e1, "serviceType");
						Log.d("serviceType", serviceType);
						pakageChargeBeans.setServiceType(serviceType);
						lsPakageChargeBeans.add(pakageChargeBeans);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

//			new CacheDatabaseManager(context)
//					.insertService(lsPakageChargeBeans);

			return lsPakageChargeBeans;
		}

	}

	// ================ get list name product ================
	public class GetListNameProductAsyn extends
			AsyncTask<Void, Void, ArrayList<NameProductBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListNameProductAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }

			btnsanpham
					.setVisibility(View.GONE);
			prbsanpham.setVisibility(
					View.VISIBLE);

		}

		@Override
		protected ArrayList<NameProductBeans> doInBackground(Void... params) {
			return sendRequestGetNameProduct();
		}

		@Override
		protected void onPostExecute(ArrayList<NameProductBeans> result) {
			// progress.dismiss();

			btnsanpham.setVisibility(
					View.VISIBLE);
			prbsanpham
					.setVisibility(View.GONE);
			if (errorCode.equals("0")) {
				if (result.size() > 0 && !result.isEmpty()) {
					arrNameProductBeans = result;
					initNameProduct();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notsanpham),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
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

		// =====method get list staff ========================
		public ArrayList<NameProductBeans> sendRequestGetNameProduct() {
			String original = null;
			ArrayList<NameProductBeans> lisNameProductBeans = new ArrayList<>();
			try {

				lisNameProductBeans = new CacheDatabaseManager(context)
						.getListNameProduct();
				if (lisNameProductBeans != null
						&& !lisNameProductBeans.isEmpty()) {
					errorCode = "0";
					return lisNameProductBeans;
				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListGroupProductByParentIdIsNull");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListGroupProductByParentIdIsNull>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListGroupProductByParentIdIsNull>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListGroupProductByParentIdIsNull");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ========parser xml get employ from server

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstGroupProduct");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						NameProductBeans namProductBeans = new NameProductBeans();
						String groupName = parse.getValue(e1, "groupName");
						Log.d("groupName", groupName);
						namProductBeans.setGroupName(groupName);
						String groupProductId = parse.getValue(e1,
								"groupProductId");
						Log.d("groupProductId", groupProductId);
						namProductBeans.setGroupProductId(groupProductId);
						lisNameProductBeans.add(namProductBeans);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			new CacheDatabaseManager(context)
					.insertNameProduct(lisNameProductBeans);
			return lisNameProductBeans;
		}
	}

	// ================ get location code from conectorcode
	public class CheckConnectorCodeAsyn extends
			AsyncTask<String, Void, SurveyOnline> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public CheckConnectorCodeAsyn(Context context) {
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
		protected SurveyOnline doInBackground(String... arg0) {
			return getLocationFromConnectorCode(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(SurveyOnline result) {
			this.progress.dismiss();
			if (errorCode.equals("0")) {

				if ((result.getConnectorCode() != null && !result
						.getConnectorCode().isEmpty())
						&& (result.getConnectorId() != null && !result
								.getConnectorId().isEmpty())
						&& (result.getLocationCode() != null && !result
								.getLocationCode().isEmpty())) {
					surveyOnline = result;
					if (result != null) {
						if (techology.equals("4")) {
							showPopupGPON(result);
						} else {
							if (serviceType.equals("F")
									|| serviceType.equals("L")
									|| serviceType.equals("N")) {
								showPopupFTTH(result);
							}
							if (serviceType.equals("A")) {
								showPopupADSL(result);
							}
							if (serviceType.equals("P")) {
								showPopupPSTN(result);
							}
							if (serviceType.equals("T")
									|| serviceType.equals("I")
									|| serviceType.equals("U")) {
								showPopupTHC(result);
							}
						}
						Log.i("tag", "setMarkerClickListener");
					}

				} else {
					checkConnectorCode = "";
					CommonActivity
							.createDialog(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfoconector),
									getActivity().getResources().getString(
											R.string.app_name),
									getActivity().getResources().getString(
											R.string.ok),
									getActivity().getResources().getString(
											R.string.buttonCancel),
									MoveScreenKSOnclick, null).show();
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
					checkConnectorCode = "";
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

		// ======= method get location code from conectorcode ================

		private SurveyOnline getLocationFromConnectorCode(String cableCode,
				String serviceType) {
			SurveyOnline surveyOnlinePrivate = new SurveyOnline();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_surveyConnectorByCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:surveyConnectorByCode>");
				rawData.append("<qlctktInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<cableBoxCode>").append(cableCode);
				rawData.append("</cableBoxCode>");
				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				if (techology != null && !techology.equals("")) {
					if (techology.equals("4")) {
						rawData.append("<infraType>" + "GPON");
						rawData.append("</infraType>");
					} else {

					}

				}

				if (isFTTB != null && !isFTTB.isEmpty()) {
					rawData.append("<isFTTB>").append(isFTTB);
					rawData.append("</isFTTB>");
				}
				
				
				if (msType != null && !msType.isEmpty()) {
					if ( ("23".equals(groupProductId) && "U"
							.equals(serviceType)) || ("22".equals(groupProductId) && "U"
									.equals(serviceType))) {
						if(!CommonActivity.isNullOrEmpty(mstNoRf)){
							rawData.append("<msType>").append(mstNoRf);
							rawData.append("</msType>");
						}else{
							rawData.append("<msType>").append(msType);
							rawData.append("</msType>");
						}
					}else{
						rawData.append("<msType>").append(msType);
						rawData.append("</msType>");
					}
				}
				
				
//				if ( ("23".equals(groupProductId) && "U"
//						.equals(serviceType)) || ("22".equals(groupProductId) && "U"
//								.equals(serviceType))) {
//					if(!CommonActivity.isNullOrEmpty(mstNoRf)){
//						rawData.append("<msType>" + mstNoRf);
//						rawData.append("</msType>");
//					}
//				} 
				
				rawData.append("</qlctktInput>");
				rawData.append("</ws:surveyConnectorByCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_surveyConnectorByCode");
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
					nodechild = e2
							.getElementsByTagName("resultSurveyOnlineForm");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						if (description == null || description.equals("")) {
							description = parse.getValue(e1, "resultMessage");
						}

						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						surveyOnlinePrivate.setAddress(address);

						String cableLength = parse.getValue(e1, "cableLength");
						Log.d("cableLength", cableLength);
						surveyOnlinePrivate.setCableLength(cableLength);

						String connectorCode = parse.getValue(e1,
								"connectorCode");
						Log.d("connectorCode", connectorCode);
						surveyOnlinePrivate.setConnectorCode(connectorCode);

						String connectorId = parse.getValue(e1, "connectorId");
						Log.d("connectorId", connectorId);
						surveyOnlinePrivate.setConnectorId(connectorId);

						String connectorType = parse.getValue(e1,
								"connectorType");
						Log.d("connectorType", connectorType);
						surveyOnlinePrivate.setConnectorType(connectorType);

						String deptCode = parse.getValue(e1, "deptCode");
						Log.d("deptCode", deptCode);
						surveyOnlinePrivate.setDeptCode(deptCode);

						String deptName = parse.getValue(e1, "deptName");
						Log.d("deptName", deptName);
						surveyOnlinePrivate.setDeptName(deptName);

						String distance = parse.getValue(e1, "distance");
						Log.d("distance", distance);
						if (distance != null && !distance.isEmpty()) {
							surveyOnline.setDistance(distance);
						}

						String lat1 = parse.getValue(e1, "lat");
						Log.d("lat1", lat1);
						surveyOnlinePrivate.setLat(lat1);

						String lng1 = parse.getValue(e1, "lng");
						Log.d("lng1", lng1);
						surveyOnlinePrivate.setLng(lng1);

						surveyOnlinePrivate.setCableBoxPosition(lat1 + "_"
								+ lng1);
						surveyOnlinePrivate.setCustomerPossition(lat + "_"
								+ lon);
						surveyOnlinePrivate.setPolylineData(lat + "_" + lon
								+ ";" + lat1 + "_" + lng1);

						String resourceConnector = parse.getValue(e1,
								"resourceConnector");
						Log.d("resourceConnector", resourceConnector);
						surveyOnlinePrivate
								.setResourceConnector(resourceConnector);

						String resourceDevice = parse.getValue(e1,
								"resourceDevice");
						Log.d("resourceDevice", resourceDevice);
						surveyOnlinePrivate.setResourceDevice(resourceDevice);

						String resourceRootCable = parse.getValue(e1,
								"resourceRootCable");
						Log.d("resourceRootCable", resourceRootCable);
						surveyOnlinePrivate
								.setResourceRootCable(resourceRootCable);

						String stationCode = parse.getValue(e1, "stationCode");
						Log.d("stationCode", stationCode);
						surveyOnlinePrivate.setStationCode(stationCode);

						String stationId = parse.getValue(e1, "stationId");
						Log.d("stationId", stationId);
						surveyOnlinePrivate.setStationId(stationId);

						String connectorFreePort = parse.getValue(e1,
								"connectorFreePort");
						Log.d("connectorFreePort", connectorFreePort);
						surveyOnlinePrivate
								.setConnectorFreePort(connectorFreePort);

						String connectorLockPort = parse.getValue(e1,
								"connectorLockPort");
						Log.d("connectorLockPort", connectorLockPort);
						surveyOnlinePrivate
								.setConnectorLockPort(connectorLockPort);

						String deviceCode = parse.getValue(e1, "deviceCode");
						Log.d("deviceCode", deviceCode);
						surveyOnlinePrivate.setDeviceCode(deviceCode);

						String portCode = parse.getValue(e1, "portCode");
						Log.d("portCode", portCode);
						surveyOnlinePrivate.setPortCode(portCode);

						String sizePortForCable = parse.getValue(e1,
								"sizePortForCable");
						Log.d("sizePortForCable", sizePortForCable);
						surveyOnlinePrivate
								.setSizePortForCable(sizePortForCable);

						String sizeKeepDeployAccount = parse.getValue(e1,
								"sizeKeepDeployAccount");
						Log.d("sizeKeepDeployAccount", sizeKeepDeployAccount);
						surveyOnlinePrivate
								.setSizeKeepDeployAccount(sizeKeepDeployAccount);

						String locationName = parse
								.getValue(e1, "locationName");
						Log.d("locationName", locationName);
						surveyOnlinePrivate.setLocationName(locationName);

						String locationCode = parse
								.getValue(e1, "locationCode");
						Log.d("locationCode", locationCode);
						surveyOnlinePrivate.setLocationCode(locationCode);

						String nodeOpticalCode = parse.getValue(e1,
								"nodeOpticalCode");
						Log.d("nodeOpticalCode", nodeOpticalCode);
						surveyOnlinePrivate.setNodeOpticalCode(nodeOpticalCode);

						String pillarCode = parse.getValue(e1, "pillarCode");
						Log.d("pillarCode", pillarCode);
						surveyOnlinePrivate.setPillarCode(pillarCode);

						// vendorCode, vendorName
						String vendorCode = parse.getValue(e1, "vendorCode");
						surveyOnlinePrivate.setVendorCode(vendorCode);

						String vendorName = parse.getValue(e1, "vendorName");
						surveyOnlinePrivate.setVendorName(vendorName);
					}
				}

			} catch (Exception e) {
				Log.d("GetInfoFromCableBox", e.toString());
			}

			return surveyOnlinePrivate;

		}

	}

	// ham chuyen sang man hinh ks
    private final OnClickListener MoveScreenKSOnclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (serviceId != null && !serviceId.isEmpty()) {
				if (mText != null && !mText.equals("")) {
					mText = "";
					edit_maketcuoi.setText("");
				}
				if (serviceId.equals("U")) {
					if (pakageCharge != null && !pakageCharge.isEmpty()) {
						startActityMap();
					} else {
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.checkpakecharge),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					startActityMap();
				}

			} else {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.checkserviceTypeInfoExamine),
						Toast.LENGTH_SHORT).show();
			}

		}
	};

	// ws lay thong tin ha tang tu thue bao chu
	private class GetInfraByForSubAsyn extends
			AsyncTask<String, Void, ArrayList<SurveyOnline>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String resultMessage = "";
		String deployModelOntSfu = "";
		String deployModelOntSfuName = "";

		public GetInfraByForSubAsyn(Context context) {
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
		protected ArrayList<SurveyOnline> doInBackground(String... arg0) {
			return getConnectorCodeFromAccHost(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<SurveyOnline> result) {

			this.progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null && result.size() > 0) {
					surveyOnline = result.get(0);
					if (("F".equals(serviceType) && techology.equals("4"))
							|| ("N".equals(serviceType) && techology.equals("4"))
							|| ("I".equals(serviceType) && techology.equals("4"))
							|| (("23".equals(groupProductId) && "U"
									.equals(serviceType) && techology.equals("4")))) {
						if (surveyOnline.getVendorCode() != null
								&& !surveyOnline.getVendorCode().isEmpty()) {
							surveyOnline.setSurfaceRadius("0");
							edit_maketcuoi.setText(result.get(0)
									.getConnectorCode());
							checkConnectorCode = "1001";
							edit_maketcuoi.setEnabled(false);
							btncheckConectorCode.setVisibility(View.GONE);
							lnLoadmap.setVisibility(View.GONE);
						} else {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.checkvendor),
									getString(R.string.app_name)).show();
						}

						// TODO XU LY THEM CHO NAY
						lnmohinh.setVisibility(View.VISIBLE);
						if (!CommonActivity.isNullOrEmpty(deployModelOntSfu)) {
							if (arrMohinh != null && !arrMohinh.isEmpty()) {
								for (Spin item : arrMohinh) {
									if (deployModelOntSfu.equals(item.getId())) {
										spinner_mohinh.setSelection(arrMohinh
												.indexOf(item));
										spinner_mohinh.setEnabled(false);
										break;
									}
								}
							}

						} else {
							spinner_mohinh.setEnabled(true);
						}

					} else {

						lnmohinh.setVisibility(View.GONE);

						surveyOnline.setSurfaceRadius("0");
						edit_maketcuoi
								.setText(result.get(0).getConnectorCode());
						checkConnectorCode = "1001";
						edit_maketcuoi.setEnabled(false);
						btncheckConectorCode.setVisibility(View.GONE);
						lnLoadmap.setVisibility(View.GONE);
					}

				} else {
					lineType = "";

					if (techology != null) {
						if (techology.equals("4")) {
							if (resultMessage != null
									&& !resultMessage.isEmpty()) {
								CommonActivity.createAlertDialog(
										getActivity(),
										resultMessage,
										context.getResources().getString(
												R.string.app_name)).show();
							} else {
								CommonActivity.createAlertDialog(
										getActivity(),
										context.getResources().getString(
												R.string.checkhatang1),
										context.getResources().getString(
												R.string.app_name)).show();
							}

						} else {

							if (resultMessage != null
									&& !resultMessage.isEmpty()) {
								CommonActivity.createAlertDialog(
										getActivity(),
										resultMessage,
										context.getResources().getString(
												R.string.app_name)).show();
							} else {
								CommonActivity.createAlertDialog(
										getActivity(),
										context.getResources().getString(
												R.string.checkhatang),
										context.getResources().getString(
												R.string.app_name)).show();
							}

						}

					} else {

						if (resultMessage != null && !resultMessage.isEmpty()) {
							CommonActivity.createAlertDialog(
									getActivity(),
									resultMessage,
									context.getResources().getString(
											R.string.app_name)).show();
						} else {
							CommonActivity.createAlertDialog(
									getActivity(),
									context.getResources().getString(
											R.string.checkhatang),
									context.getResources().getString(
											R.string.app_name)).show();
						}

					}
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

		private ArrayList<SurveyOnline> getConnectorCodeFromAccHost(
				String parentId, String serviceType, String lineType) {
			ArrayList<SurveyOnline> lstSurveyOnlines = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getInfraByForSub");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInfraByForSub>");
				rawData.append("<cmFixServiceInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<parentId>").append(parentId);
				rawData.append("</parentId>");
				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				
				rawData.append("<productCode>").append(productCodeMt);
				rawData.append("</productCode>");
				
				if (lineType != null && !lineType.isEmpty()) {
					rawData.append("<lineType>").append(lineType);
					rawData.append("</lineType>");
				} else {
					rawData.append("<lineType>").append(serviceType);
					rawData.append("</lineType>");
				}
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getInfraByForSub>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getInfraByForSub");
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
					// bo sung thong tin "deployModelOntSfu",
					// "deployModelOntSfuName"
					deployModelOntSfu = parse.getValue(e2, "deployModelOntSfu");
					Log.d("deployModelOntSfu", deployModelOntSfu);
					deployModelOntSfuName = parse.getValue(e2,
							"deployModelOntSfuName");
					nodechild = e2
							.getElementsByTagName("resultSurveyOnlineForm");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SurveyOnline surveyOnlinePrivate = new SurveyOnline();

						resultMessage = parse.getValue(e1, "resultMessage");

						String connectorCode = parse.getValue(e1,
								"connectorCode");
						Log.d("connectorCode", connectorCode);
						surveyOnlinePrivate.setConnectorCode(connectorCode);

						
						String connectorType = parse.getValue(e1,
								"connectorType");
						Log.d("connectorType", connectorType);
						surveyOnlinePrivate.setConnectorType(connectorType);
						
						String connectorId = parse.getValue(e1, "connectorId");
						Log.d("connectorId", connectorId);
						surveyOnlinePrivate.setConnectorId(connectorId);

						String radiusCust = parse.getValue(e1, "radiusCust");
						Log.d("radiusCust", radiusCust);
						surveyOnlinePrivate.setSurfaceRadius(radiusCust);

						String teamCode = parse.getValue(e1, "teamCode");
						surveyOnlinePrivate.setDeptCode(teamCode);

						String resourceConnector = parse.getValue(e1,
								"resourceConnector");
						Log.d("resourceConnector", resourceConnector);
						surveyOnlinePrivate
								.setResourceConnector(resourceConnector);

						String resourceDevice = parse.getValue(e1,
								"resourceDevice");
						Log.d("resourceDevice", resourceDevice);
						surveyOnlinePrivate.setResourceDevice(resourceDevice);

						String resourceRootCable = parse.getValue(e1,
								"resourceRootCable");
						Log.d("resourceRootCable", resourceRootCable);
						surveyOnlinePrivate
								.setResourceRootCable(resourceRootCable);

						String stationCode = parse.getValue(e1, "stationCode");
						Log.d("stationCode", stationCode);
						surveyOnlinePrivate.setStationCode(stationCode);

						String stationId = parse.getValue(e1, "stationId");
						Log.d("stationId", stationId);
						surveyOnlinePrivate.setStationId(stationId);

						String deptCode = parse.getValue(e1, "deptCode");
						surveyOnlinePrivate.setDeptCode(deptCode);
						// String province = parse.getValue(e1, "province");
						// String district = parse.getValue(e1, "district");
						// String precinct = parse.getValue(e1, "precinct");
						String lat1 = parse.getValue(e1, "lat");
						Log.d("lat1", lat1);
						surveyOnlinePrivate.setLat(lat1);

						String lng1 = parse.getValue(e1, "lng");
						Log.d("lng1", lng1);
						surveyOnlinePrivate.setLng(lng1);

						surveyOnlinePrivate.setCableBoxPosition(lat1 + "_"
								+ lng1);
						surveyOnlinePrivate.setCustomerPossition(lat + "_"
								+ lon);
						surveyOnlinePrivate.setPolylineData(lat + "_" + lon
								+ ";" + lat1 + "_" + lng1);

						String locationCode = parse
								.getValue(e1, "locationCode");
						Log.d("locationCode", locationCode);
						surveyOnlinePrivate.setLocationCode(locationCode);

						// vendorCode, vendorName
						String vendorCode = parse.getValue(e1, "vendorCode");
						surveyOnlinePrivate.setVendorCode(vendorCode);

						String vendorName = parse.getValue(e1, "vendorName");
						surveyOnlinePrivate.setVendorName(vendorName);

						if (surveyOnlinePrivate.getConnectorCode() != null
								&& !surveyOnlinePrivate.getConnectorCode()
										.isEmpty()) {
							lstSurveyOnlines.add(surveyOnlinePrivate);
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return lstSurveyOnlines;
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_update:

			// validate update
			// === check info KH ======
			connectorCode = edit_maketcuoi.getText().toString();
			if (account != null && !account.equals("")) {

				if (nameProductId != null && !nameProductId.equals("")) {

					if (serviceType != null && !serviceType.equals("")) {

						if (pakageCharge != null && !pakageCharge.equals("")) {

							if (techology != null && !techology.equals("")) {

								if (techology.equals("4")) {
									// == GPON ===
									if (!idDuongDay.equals("")
											&& idDuongDay != null) {

										// === neu la duong day moi thi chi duoc
										// chon lien ket doc lap va k can thue
										// bao chinh
										if (idDuongDay.equals("-1")) {
											checklienketdoclap(idDuongDay);
										} else {
											// check lien ket khac gpon doc lap
											checklienket();
										}
									} else {
										Toast.makeText(
												getActivity(),
												getActivity()
														.getResources()
														.getString(
																R.string.checkduongday),
												Toast.LENGTH_SHORT).show();
									}
								} else {
									if (examineJoin != null
											&& !examineJoin.equals("")) {

										if (examineJoin.equals("A/P")
												|| examineJoin.equals("P/A")) {
											checkthuebaochu();
										} else {
											checkConectorCode();
										}
									} else {

										if (serviceType.equals("I")
												|| serviceType.equals("E")
												|| serviceType.equals("N")) {
											checkthuebaochu();
										} else {
											if (serviceType.equals("A")
													|| serviceType.equals("P")) {
												Toast.makeText(
														getActivity(),
														getActivity()
																.getResources()
																.getString(
																		R.string.checkhatangghep),
														Toast.LENGTH_SHORT)
														.show();
											} else {
												checkConectorCode();

											}
										}
									}
								}

							} else {
								techology = "";
								Toast.makeText(
										getActivity(),
										getActivity().getResources().getString(
												R.string.checkinfotech),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							offerId = "";
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkpakage),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						serviceType = "";
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.checkserviceType),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					nameProductId = "";
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checknameproduct),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.checkinfoCus), Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case R.id.loadbando:
			if (serviceType != null && !serviceType.isEmpty()) {

				if (techology != null || !techology.isEmpty()) {

					if (mText != null && !mText.equals("")) {
						mText = "";
						edit_maketcuoi.setText("");
					}
					if (serviceType.equals("U")) {
						if (msType != null && !msType.isEmpty()) {
							startActityMap();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkpakecharge),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						startActityMap();
					}

				} else {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkinfotech), Toast.LENGTH_SHORT)
							.show();

				}

			} else {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.checkserviceTypeInfoExamine),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btnkiemtra:

			if (CommonActivity.isNetworkConnected(getActivity())) {
				if (!edit_maketcuoi.getText().toString().isEmpty()) {
					if (StringUtils.CheckCharSpecical(edit_maketcuoi.getText()
                            .toString())) {
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.checkcharspecical),
								Toast.LENGTH_SHORT).show();
					} else {
						if (serviceType != null && !serviceType.equals("")) {
							// TODO modify tech
							if ("A".equals(serviceType)
									|| "P".equals(serviceType)) {
								if (examineJoin != null
										&& !examineJoin.isEmpty()) {
									if (techology != null
											&& !techology.isEmpty()) {
										if (CommonActivity
                                                .isNetworkConnected(getActivity())) {
											CheckConnectorCodeAsyn checkConnectorCodeAsyn = new CheckConnectorCodeAsyn(
													getActivity());
											checkConnectorCodeAsyn.execute(
													edit_maketcuoi.getText()
															.toString()
															.toUpperCase(),
													serviceType);
										} else {
											CommonActivity
													.createAlertDialog(
															getActivity(),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.errorNetwork),
															getActivity()
																	.getResources()
																	.getString(
																			R.string.app_name))
													.show();
										}

									} else {
										Toast.makeText(
												getActivity(),
												getActivity()
														.getResources()
														.getString(
																R.string.checkinfotech),
												Toast.LENGTH_SHORT).show();
									}

								} else {
									Toast.makeText(
											getActivity(),
											getActivity()
													.getResources()
													.getString(
															R.string.checkhatangghep),
											Toast.LENGTH_SHORT).show();
								}
							} else {
								if (techology != null && !techology.isEmpty()) {
									if (CommonActivity
                                            .isNetworkConnected(getActivity())) {
										CheckConnectorCodeAsyn checkConnectorCodeAsyn = new CheckConnectorCodeAsyn(
												getActivity());
										checkConnectorCodeAsyn.execute(
												edit_maketcuoi.getText()
														.toString()
														.toUpperCase(),
												serviceType);
									} else {
										CommonActivity
												.createAlertDialog(
														getActivity(),
														getActivity()
																.getResources()
																.getString(
																		R.string.errorNetwork),
														getActivity()
																.getResources()
																.getString(
																		R.string.app_name))
												.show();
									}

								} else {
									Toast.makeText(
											getActivity(),
											getActivity()
													.getResources()
													.getString(
															R.string.checkinfotech),
											Toast.LENGTH_SHORT).show();
								}
							}

						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkserviceType),
									Toast.LENGTH_SHORT).show();
						}
					}

				} else {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkconnectornotdata),
							Toast.LENGTH_SHORT).show();
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

		case R.id.btnsanpham:

			new CacheDatabaseManager(getActivity()).insertNameProduct(null);
			if (CommonActivity.isNetworkConnected(getActivity())) {

				GetListNameProductAsyn getListNameProductAsyn = new GetListNameProductAsyn(getActivity());
				getListNameProductAsyn.execute();
				
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}

			break;
		case R.id.btnservice:

//			new CacheDatabaseManager(getActivity()).insertService(null);
//			if (CommonActivity.isNetworkConnected(getActivity())) {
//				if(!CommonActivity.isNullOrEmpty(nameProductId)){
//					GetListServiceAsyn getListNameProductAsyn = new GetListServiceAsyn(getActivity());
//					getListNameProductAsyn.execute(nameProductId);
//				}else{
//					CommonActivity.createAlertDialog(getActivity(),
//							getString(R.string.checknhomsp),
//							getString(R.string.app_name)).show();
//				}
//			} else {
//				CommonActivity.createAlertDialog(getActivity(),
//						getString(R.string.errorNetwork),
//						getString(R.string.app_name)).show();
//			}
			
			
			break;
		default:
			break;
		}

	}

	// CHECK LIEN KET GPON DOC LAP -1
	private void checklienketdoclap(String duongday) {

		if (!relationType.equals("") && relationType != null) {
			// check lien ket gpon doc lap
			if (relationType.equalsIgnoreCase("GPDL")) {
				checkconnectorCodeGpon();
			} else {
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.checkduongdaymoi), Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			relationType = "";
			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checklienket), Toast.LENGTH_SHORT).show();
		}

	}

	// CHECK LIEN KET KHAC GPON DOC LAP
	private void checklienket() {
		if (relationType != null && !relationType.equals("")) {
			if (relationType.equals("GPDL")) {
				checkconnectorCodeGpon();
			} else {
				// check thue bao chinh
				if (mainSubId != null && !mainSubId.equals("")) {
					// check connector Code gpon
					checkconnectorCodeGpon();
				} else {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkthuebaochinh1),
							Toast.LENGTH_SHORT).show();
				}
			}

		} else {
			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checklienket), Toast.LENGTH_SHORT).show();
		}

	}

	// CHECK CONNETORCODE GPON
	private void checkconnectorCodeGpon() {
		if (!connectorCode.equals("")) {

			if (checkConnectorCode.equals("1000")
					|| checkConnectorCode.equals("1001")) {
				if (StringUtils.CheckCharSpecical(connectorCode)) {

					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkcharspecical),
							Toast.LENGTH_SHORT).show();

				} else {
					if (!serviceId.equals("") && !offerId.equals("")) {
						Intent intent = new Intent(getActivity(),
								FragmentConnectionInfoSetting.class);
						Bundle mBundle1 = new Bundle();
						mBundle1.putString("idDuongDayKey", idDuongDay);
						mBundle1.putString("relationTypeKey", relationType);
						mBundle1.putString("mainSubIdKey", mainSubId);
						mBundle1.putString("techologyKey", techology);
						mBundle1.putString("ExamineJoin", examineJoin);
						mBundle1.putString("TeleServiceKey", serviceId);
						Log.d("TeleServiceKey", serviceId);
						if(!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district) && !CommonActivity.isNullOrEmpty(precinct)){
							mBundle1.putString("province", province);
							mBundle1.putString("district", district);
							mBundle1.putString("precinct", precinct);
						}
						if (locationCode != null && !locationCode.isEmpty()) {
							mBundle1.putString("locationCodeKey", locationCode);
							Log.d("locationCodeKey", locationCode);
							if (surveyOnline.getConnectorCode() != null
									&& !surveyOnline.getConnectorCode()
											.isEmpty()) {
								mBundle1.putSerializable("ServeyKeyUpdate",
										surveyOnline);
								intent.putExtras(mBundle1);
							}
						} else {
							// ====== check
							// location
							// code =====
							if (surveyOnline.getConnectorCode() != null
									&& !surveyOnline.getConnectorCode()
											.isEmpty()) {
								mBundle1.putSerializable("ServeyKeyUpdate",
										surveyOnline);
								intent.putExtras(mBundle1);
							}
						}
						if (!serviceType.equals("")) {
							mBundle1.putString("serviceTypeKey", serviceType);
						}
						if (!offerId.equals("")) {
							mBundle1.putString("offerIdKey", offerId);
						}
						if (productCode != null && !productCode.equals("")) {
							mBundle1.putString("productCodeKey", productCode);
						}
						intent.putExtras(mBundle1);
						startActivity(intent);
					}

				}

			} else {

				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.firstcheckConectorcode),
						Toast.LENGTH_SHORT).show();

			}

		} else {
			// check ma ket cuoi
			connectorCode = "";
			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checkconnectorCode), Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void checkthuebaochu() {
		if (accHostAcc != null && !accHostAcc.equals("")) {
			checkConectorCode();
		} else {
			accHostAcc = "";
			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checkthuebaochu), Toast.LENGTH_SHORT)
					.show();

		}

	}

	private void checkConectorCode() {

		if (!connectorCode.equals("")) {

			if (StringUtils.CheckCharSpecical(connectorCode)) {

				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.checkcharspecical), Toast.LENGTH_SHORT)
						.show();

			} else {

				if (checkConnectorCode.equals("1000")
						|| checkConnectorCode.equals("1001")) {

					if (!serviceId.equals("") && !offerId.equals("")) {
						Intent intent = new Intent(getActivity(),
								FragmentConnectionInfoSetting.class);
						Bundle mBundle1 = new Bundle();
						mBundle1.putString("techologyKey", techology);
						mBundle1.putString("ExamineJoin", examineJoin);
						mBundle1.putString("TeleServiceKey", serviceId);
						
						if(!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district) && !CommonActivity.isNullOrEmpty(precinct)){
							mBundle1.putString("province", province);
							mBundle1.putString("district", district);
							mBundle1.putString("precinct", precinct);
						}
						
						
						Log.d("TeleServiceKey", serviceId);
						if (locationCode != null && !locationCode.isEmpty()) {
							mBundle1.putString("locationCodeKey", locationCode);
							Log.d("locationCodeKey", locationCode);
							if (surveyOnline.getConnectorCode() != null
									&& !surveyOnline.getConnectorCode()
											.isEmpty()) {
								mBundle1.putSerializable("ServeyKeyUpdate",
										surveyOnline);
								intent.putExtras(mBundle1);
							}
						} else {
							// ====== check location
							// code =====
							if (surveyOnline.getConnectorCode() != null
									&& !surveyOnline.getConnectorCode()
											.isEmpty()) {
								mBundle1.putSerializable("ServeyKeyUpdate",
										surveyOnline);
								intent.putExtras(mBundle1);
							}

						}
						if (!accHostAcc.equals("")) {
							mBundle1.putString("accHostAccKey", accHostAcc);
						}
						if (!serviceType.equals("")) {
							mBundle1.putString("serviceTypeKey", serviceType);
						}
						if (!offerId.equals("")) {
							mBundle1.putString("offerIdKey", offerId);
						}
						if (!productCode.equals("")) {
							mBundle1.putString("productCodeKey", productCode);
						}
						intent.putExtras(mBundle1);
						startActivity(intent);
					}
				} else {

					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.firstcheckConectorcode),
							Toast.LENGTH_SHORT).show();

				}

			}
		} else {
			// check ma ket cuoi
			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checkconnectorCode), Toast.LENGTH_SHORT)
					.show();
		}

	}

	private void moveFragmentConectinSetting() {

		checkConnectorCode = "1000";
		surveyOnline.setSurfaceRadius("0");
		if (!serviceId.equals("") && !offerId.equals("")) {
			Intent intent = new Intent(getActivity(),
					FragmentConnectionInfoSetting.class);
			Bundle mBundle1 = new Bundle();

			mBundle1.putString("idDuongDayKey", idDuongDay);
			mBundle1.putString("relationTypeKey", relationType);
			mBundle1.putString("mainSubIdKey", mainSubId);
			mBundle1.putString("techologyKey", techology);
			mBundle1.putString("ExamineJoin", examineJoin);
			mBundle1.putString("TeleServiceKey", serviceId);
			Log.d("TeleServiceKey", serviceId);
			if (locationCode != null && !locationCode.isEmpty()) {
				mBundle1.putString("locationCodeKey", locationCode);
				Log.d("locationCodeKey", locationCode);
				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					mBundle1.putSerializable("ServeyKeyUpdate", surveyOnline);
					intent.putExtras(mBundle1);
				}
			} else {
				// ====== check location
				// code =====
				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					mBundle1.putSerializable("ServeyKeyUpdate", surveyOnline);
					intent.putExtras(mBundle1);
				}

			}
			if (accHostAcc != null && !accHostAcc.equals("")) {
				mBundle1.putString("accHostAccKey", accHostAcc);
			}
			if (serviceType != null && !serviceType.equals("")) {
				mBundle1.putString("serviceTypeKey", serviceType);
			}
			if (offerId != null && !offerId.equals("")) {
				mBundle1.putString("offerIdKey", offerId);
			}
			if (productCodeMt != null && !productCodeMt.equals("")) {
				mBundle1.putString("productCodeKey", productCodeMt);
			}
			
			if(!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district) && !CommonActivity.isNullOrEmpty(precinct)){
				mBundle1.putString("province", province);
				mBundle1.putString("district", district);
				mBundle1.putString("precinct", precinct);
			}
			
			intent.putExtras(mBundle1);
			startActivity(intent);
		}

	}

	// show popup pstn
	private void showPopupPSTN(final SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(getActivity());
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
		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);
		final TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);
		txtnamequanlyPSTN = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienhePSTN = (EditText) dialog.findViewById(R.id.txtdtlienhe);

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
				if (txtdtlienhePSTN.getText().toString() != null
						&& !txtdtlienhePSTN.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:"
							+ txtdtlienhePSTN.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(),
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
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetUserFromCableBoxPSTN getUserFromCableBoxAsyn = new GetUserFromCableBoxPSTN(
								getActivity());
						getUserFromCableBoxAsyn.execute(txtmahopcap.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										getActivity(),
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
							// if(splittncapgoc.length == 2 &&
							// (Integer.parseInt(splittncapgoc[0]) <
							// Integer.parseInt(splittncapgoc[1]))){
							// if(txtvendor.getText() != null &&
							// !txtvendor.getText().toString().isEmpty()){
							txtcheck.setText("OK");
							// }else{
							// txtcheck.setText("NOK");
							// Toast.makeText(
							// getActivity(),
							// getResources().getString(
							// R.string.thieuvendor),
							// Toast.LENGTH_SHORT).show();
							// }
							// ======call webservice =============

							// }else{
							// txtcheck.setText("NOK");
							// Toast.makeText(getActivity(),
							// getResources().getString(R.string.checktncapgoc),
							// Toast.LENGTH_SHORT).show();
							// }

						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.checktnhopcap),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						txtcheck.setText("NOK");
						Toast.makeText(
								getActivity(),
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
				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();

						if (account != null && !account.equals("")) {
							moveFragmentConectinSetting();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfoCus),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						Toast.makeText(getActivity(),
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
			}
		});

		dialog.show();

	}

	// show popup adsl
	private void showPopupADSL(final SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_adsl);

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
		final TextView txtvendor = (TextView) dialog
				.findViewById(R.id.txtloaivendor);
		if (surveyOnline.getVendorCode() != null
				&& !surveyOnline.getVendorCode().isEmpty()) {
			txtvendor.setText(surveyOnline.getVendorCode());
		}
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
					Toast.makeText(getActivity(),
							getResources().getString(R.string.checkphone),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (account != null && !account.equals("")) {
							moveFragmentConectinSetting();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfoCus),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
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
			}
		});

		// check tn
		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (txtmahopcap.getText() != null
						&& !txtmahopcap.getText().toString().isEmpty()) {
					// ======call webservice =============
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetUserFromCableBoxADSL getUserFromCableBoxAsyn = new GetUserFromCableBoxADSL(
								getActivity());
						getUserFromCableBoxAsyn.execute(txtmahopcap.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										getActivity(),
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
							// if(splittncapgoc.length == 2 &&
							// (Integer.parseInt(splittncapgoc[0]) <
							// Integer.parseInt(splittncapgoc[1]))){
							// if(txtvendor.getText() != null &&
							// !txtvendor.getText().toString().isEmpty()){
							txtcheck.setText("OK");
							// }else{
							// txtcheck.setText("NOK");
							// Toast.makeText(
							// getActivity(),
							// getResources().getString(
							// R.string.thieuvendor),
							// Toast.LENGTH_SHORT).show();
							// }

							// }else{
							// txtcheck.setText("NOK");
							// Toast.makeText(getActivity(),
							// getResources().getString(R.string.checktncapgoc),
							// Toast.LENGTH_SHORT).show();
							// }

						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.checktnhopcap),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						txtcheck.setText("NOK");
						Toast.makeText(getActivity(),
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
	private void showPopupTHC(final SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(getActivity());
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
        TextView txtnamequanlyTHC = (TextView) dialog.findViewById(R.id.txtnamequanly);
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
					Toast.makeText(getActivity(),
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
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetUserFromCableBoxTHC getUserFromCableBoxAsyn = new GetUserFromCableBoxTHC(
								getActivity());
						getUserFromCableBoxAsyn.execute(txtmatab.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										getActivity(),
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
						if (txtvendor.getText() != null
								&& !txtvendor.getText().toString().isEmpty()) {
							txtcheck.setText("OK");
						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.thieuvendor),
									Toast.LENGTH_SHORT).show();
						}

					} else {
						txtcheck.setText("NOK");
						Toast.makeText(getActivity(),
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

				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (account != null && !account.equals("")) {
							moveFragmentConectinSetting();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfoCus),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
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
			}
		});

		dialog.show();

	}

	// show pop up Gpon
	private void showPopupGPON(final SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(getActivity());
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
					Toast.makeText(getActivity(),
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
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetUserFromCableBoxGPON getUserFromCableBoxAsyn = new GetUserFromCableBoxGPON(
								getActivity());
						getUserFromCableBoxAsyn.execute(txtmaSpiliter.getText()
								.toString());
					} else {
						CommonActivity
								.createAlertDialog(
										getActivity(),
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
						if ("F".equals(serviceType)
								|| "N".equals(serviceType)
								|| "I".equals(serviceType)
								|| (("23".equals(groupProductId) && "U"
										.equals(serviceType)))) {
							if (txtvendor.getText() != null
									&& !txtvendor.getText().toString()
											.isEmpty()) {
								txtcheck.setText("OK");
							} else {
								txtcheck.setText("NOK");
								Toast.makeText(
										getActivity(),
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
				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (account != null && !account.equals("")) {
							moveFragmentConectinSetting();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfoCus),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
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
			}
		});

		dialog.show();
	}

	// show popup connection FTTH

	private void showPopupFTTH(final SurveyOnline surveyOnline) {

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_odf);

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
					Toast.makeText(getActivity(),
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
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetUserFromCableBoxFTTH getUserFromCableBoxAsyn = new GetUserFromCableBoxFTTH(
								getActivity());
						getUserFromCableBoxAsyn.execute(txtmaodf.getText()
								.toString(),surveyOnline.getConnectorType());
					} else {
						CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
					}
				}

				// bo sung doan validate
				// Khi khảo sát kết cuối là mã trạm (connector_type = STATION)
				// thì chỉ validate tài nguyên port thiết bị,
				// Ko validate tài nguyên port ngoại vi ODF.
				// Khi khảo sát kết cuối là ODF (connector_type = CONNECTOR) thì
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
									getActivity(),
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
							} else {
								txtcheck.setText("NOK");
								Toast.makeText(
										getActivity(),
										getResources().getString(
												R.string.checktntbi),
										Toast.LENGTH_SHORT).show();
							}

						} else {
							txtcheck.setText("NOK");
							Toast.makeText(
									getActivity(),
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
				if (surveyOnline.getConnectorCode() != null
						&& !surveyOnline.getConnectorCode().isEmpty()) {
					if (!txtcheck.getText().toString().isEmpty()
							&& txtcheck.getText().toString().equals("OK")) {
						dialog.dismiss();
						if (account != null && !account.equals("")) {
							moveFragmentConectinSetting();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfoCus),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
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
			}
		});

		dialog.show();
	}

	// asyn getCableBox

	public class GetUserFromCableBoxADSL extends
			AsyncTask<String, Void, SysUsersBO> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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
					txtnamequanlyADSL.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheADSL.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
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
                String rawData = "<ws:getUserByCableBoxCode>" +
                        "<qlctktInput>" +
                        "<token>" + Session.getToken() + "</token>" +
                        "<cableBoxCode>" + cableCode +
                        "</cableBoxCode>" +
                        "<infraType>" + techology +
                        "</infraType>" +
                        "</qlctktInput>" +
                        "</ws:getUserByCableBoxCode>";
                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
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

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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
			return getUserFromCableBoxFTTH(arg0[0],arg0[1]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null
						&& !result.getFullname().isEmpty()) {
					txtnamequanlyFTTH.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheFTTH.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
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
									.getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getUserFromCableBoxFTTH(String cableCode, String connectorType) {
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
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<cableBoxCode>").append(cableCode);
				rawData.append("</cableBoxCode>");
				if("STATION".equals(connectorType)){
					rawData.append("<infraType>" + "6");
					rawData.append("</infraType>");
				}else{
					rawData.append("<infraType>").append(techology);
					rawData.append("</infraType>");
				}
				rawData.append("</qlctktInput>");
				rawData.append("</ws:getUserByCableBoxCode>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
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

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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
					txtnamequanlyPSTN.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienhePSTN.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
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
                String rawData = "<ws:getUserByCableBoxCode>" +
                        "<qlctktInput>" +
                        "<token>" + Session.getToken() + "</token>" +
                        "<cableBoxCode>" + cableCode +
                        "</cableBoxCode>" +
                        "<infraType>" + techology +
                        "</infraType>" +
                        "</qlctktInput>" +
                        "</ws:getUserByCableBoxCode>";
                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
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

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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
					txtnamequanlyFTTH.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheFTTH.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
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
                String rawData = "<ws:getUserByCableBoxCode>" +
                        "<qlctktInput>" +
                        "<token>" + Session.getToken() + "</token>" +
                        "<cableBoxCode>" + cableCode +
                        "</cableBoxCode>" +
                        "<infraType>" + techology +
                        "</infraType>" +
                        "</qlctktInput>" +
                        "</ws:getUserByCableBoxCode>";
                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
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

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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
					txtnamequanlyGPON.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienheGPON.setText(result.getPhone());
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notuser),
							getResources().getString(R.string.ksbando));
					dialog.show();
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
                String rawData = "<ws:getUserByCableBoxCode>" +
                        "<qlctktInput>" +
                        "<token>" + Session.getToken() + "</token>" +
                        "<cableBoxCode>" + cableCode +
                        "</cableBoxCode>" +
                        "<infraType>" + techology +
                        "</infraType>" +
                        "</qlctktInput>" +
                        "</ws:getUserByCableBoxCode>";
                String envelope = input.buildInputGatewayWithRawData(rawData);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
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

	private void startActityMap() {
		Intent intent1 = new Intent(getActivity(), FragmentLoadMap.class);
		Bundle mBundle = new Bundle();
		mBundle.putString("serviceType", serviceType);
		Log.d("serviceType", serviceType);
		if (techology != null && !techology.isEmpty()) {
			mBundle.putString("techologyKey", techology);
			mBundle.putString("isFTTBKey", isFTTB);
		}
		mBundle.putString("isFTTBKey", isFTTB);
		Log.d("isFTTBKeyisFTTBKeyisFTTBKey", isFTTB);
		if (msType != null && !msType.equals("")) {
			mBundle.putString("msTypeKey", msType);
		}
		mBundle.putString("groupProductIdKey", groupProductId);
		mBundle.putString("mstNoRfKey", mstNoRf);
		intent1.putExtras(mBundle);

		startActivityForResult(intent1, 10001);
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 10001 && resultCode == getActivity().RESULT_OK) {
			Log.d("TAG RESULT_OK", "RESULT_OK");

			if (data != null) {
				surveyOnline = (SurveyOnline) data
						.getSerializableExtra("ServeyKey");
				checkConnectorCode = data.getStringExtra("checkConnectorCode");
//				mBundle.putString("province", province);
//				mBundle.putString("district", district);
//				mBundle.putString("precinct", precinct);
				province = data.getStringExtra("province");
				district = data.getStringExtra("district");
				precinct = data.getStringExtra("precinct");
				
				if (checkConnectorCode != null
						&& !checkConnectorCode.equals("")) {
					Log.d("checkConnectorCode", checkConnectorCode);
					if (checkConnectorCode.equals("1000")) {
						btncheckConectorCode.setVisibility(View.GONE);
					} else {
						btncheckConectorCode.setVisibility(View.VISIBLE);
					}
				}
			}

			if (surveyOnline != null) {
				if (surveyOnline.getConnectorCode() != null) {
					edit_maketcuoi.setText(surveyOnline.getConnectorCode());
					connectorCode = edit_maketcuoi.getText().toString();
					mText = edit_maketcuoi.getText().toString();
					Log.d("connectorCode", connectorCode);
					if (surveyOnline.getLocationCode() != null) {
						locationCode = surveyOnline.getLocationCode();
						Log.d("locationCode", locationCode);
					}

				} else {
					edit_maketcuoi.setText("");
				}
			}
		}
	}

	// khoi tao danh sach mo hinh
	private ArrayList<Spin> arrMohinh;

	private void initMohinh() {
		arrMohinh = new ArrayList<>();
		arrMohinh.add(new Spin("ONT", getString(R.string.ont)));
		arrMohinh.add(new Spin("ONT_BRIDGE", getString(R.string.ont_bridge)));
		arrMohinh.add(new Spin("SFU", getString(R.string.sfu)));
		Utils.setDataSpinner(getActivity(), arrMohinh, spinner_mohinh);
	}

}
