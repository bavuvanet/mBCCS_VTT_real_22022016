package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListCustomerEditableAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerTypeByCustGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TypePaperBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetTypePaperDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author TuanTD7 CONNECTION Ä�áº¥u ná»‘i hotline (cá»‘ Ä‘á»‹nh)
 * 
 */
public class FragmentInfoCustomerHotLine extends Fragment implements
		OnClickListener, OnItemClickListener {
	private Button btnnhapmoi, btnsearch;
	private EditText edit_isdnacount;
	private Spinner spinner_service, spinner_type_giay_to;
	private ExpandableHeightListView lvCustomer;
	// defind dialog
	private Spinner spinner_nhomkh;
	private Spinner spinner_loaikh;
	private String custTypeIdMain = "";
	private String cusGroupId = "";
	private String cusType = "";
	private View prbCustType;
	private ArrayList<TypePaperBeans> arrTypePaperBeans = new ArrayList<TypePaperBeans>();
	private ArrayList<CustomerGroupBeans> arrCustomerGroupBeans = new ArrayList<CustomerGroupBeans>();
	private ArrayList<CustomerTypeByCustGroupBeans> arrCustomerTypeByCustGroupBeans = new ArrayList<CustomerTypeByCustGroupBeans>();

	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();

	private ArrayList<CustommerByIdNoBean> arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();

	private GetListCustomerEditableAdapter adaGetListCusAdapter;
	// private GetListCusAdapter adaGetListCusAdapter2;

	private View btnRefreshCusType;
	// === input for ws get list customer
	private String serviceType = "";
	private String idsnOrAcc = "";
	private String numberPaper = "";
	private String typePaper = "";
	private String busType = "";
	public static String cusId = "";
	public static String account = "";
	// public static String idcusGroup = "";
	// public static String idcusType = "";
	public static CustommerByIdNoBean custommer = new CustommerByIdNoBean();
	private View mView;

	private Dialog dialogInsertNew;
	private EditText edit_tenKH;
	private EditText edit_socmnd;
	// private EditText edit_soGQ;
	public EditText edit_sogpkd, edit_sogiayto;
	private String busPermitNo = "";
	private EditText edit_ngaycap;
	private EditText edit_noicap;

	private String dateNowString = null;
	private Date issueDate = null;
	private Date dateNow = null;

	// bong sung ngay sinh nhat
	private Date birthDateCus = null;
	private Calendar calBirthCus;
	private int dayBirthCus;
	private int monthBirthCus;
	private int yearBirthCus;

	// define time ngay bat dau va ngay het thuc
	private Calendar cal;
	private int day;
	private int month;
	private int year;

	private String mNgaycaidat = "";

	private Calendar calBirth;
	private int dayBirth;
	private int monthBirth;
	private int yearBirth;

	private Calendar calATT;
	private int dayATT;
	private int monthATT;
	private int yearATT;

	private Date issueDateATT = null;
	private Date birthDateATT = null;

	private LinearLayout lnmasothue;
	private EditText edit_masothue;

	private Activity activity;

	private String idType = "";
	private LinearLayout ln_sex;

	private ArrayList<Spin> lstReason = new ArrayList<Spin>();
	private Spinner spn_reason_fail;
	private ProgressBar prbreason;
	private CustommerByIdNoBean custommerByIdNoBeanEdit;
	private LinearLayout linearsoGPKD;
	private EditText edit_soGQ;
	private LinearLayout linearCMT;
	private LinearLayout ln_tin;
	private EditText edt_tin;
	private LinearLayout lnsogiayto;
	private String reasonId;
	private EditText edtOTPIsdn, edtOTPCode;
	private LinearLayout lnOTP;
	private Button btnSendOTP;
	private ProgressBar prbreasonBtn;
	private String otp = "";
	private String isdnSendOtp = "";
	private boolean permissionChangeInfoCustNoSubActive = false;
	private boolean permissionChangeInfoCustHaveSubActive = false;
	private String custId = "";
	private Dialog dialogEditCustomer;
	private Button btnEdit;
	private ProgressBar prbStreetBlock;
	private Button btnRefreshStreetBlock;
	private final String subType = "2";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		calBirthCus = Calendar.getInstance();
		birthDateCus = calBirthCus.getTime();
		dayBirthCus = cal.get(Calendar.DAY_OF_MONTH);
		monthBirthCus = cal.get(Calendar.MONTH);
		yearBirthCus = cal.get(Calendar.YEAR);

		calBirth = Calendar.getInstance();
		birthDateATT = calBirth.getTime();
		dayBirth = cal.get(Calendar.DAY_OF_MONTH);
		monthBirth = cal.get(Calendar.MONTH);
		yearBirth = cal.get(Calendar.YEAR);

		calATT = Calendar.getInstance();
		issueDateATT = calATT.getTime();
		dayATT = calATT.get(Calendar.DAY_OF_MONTH);
		monthATT = calATT.get(Calendar.MONTH);
		yearATT = calATT.get(Calendar.YEAR);

		Calendar calendar = Calendar.getInstance();
		issueDate = calendar.getTime();

		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		try {
			dateNow = calendar.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
		if (mView == null) {
			mView = inflater.inflate(R.layout.connection_layout_info_customer,
					container, false);
			unit(mView);
		} else {
			((ViewGroup) mView.getParent()).removeAllViews();
		}

		// tuantd7();

		return mView;
	}

	private void tuantd7() {
		// if (Session.loginUser != null &&
		// "tuantm".equalsIgnoreCase(Session.loginUser.getStaffCode()
		// .toLowerCase())) {
		edit_sogiayto.setText("178176176");
		// }
	}

	public void unit(View v) {
		removeCus();
		btnsearch = (Button) v.findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(this);
		btnnhapmoi = (Button) v.findViewById(R.id.btnnhapmoi);
		btnnhapmoi.setOnClickListener(this);
		edit_isdnacount = (EditText) v.findViewById(R.id.edit_isdnacount);
		edit_sogiayto = (EditText) v.findViewById(R.id.edit_sogiayto);
		edit_sogpkd = (EditText) v.findViewById(R.id.edit_sogpkd);
		// edit_sogiayto.setText("248258258");

		spinner_service = (Spinner) v.findViewById(R.id.spinner_service);

		lvCustomer = (ExpandableHeightListView) v
				.findViewById(R.id.listcustomer);
		lvCustomer.setExpanded(true);
		lvCustomer.setOnItemClickListener(this);
		// fill data for type paper
		// initTypePaper();
		initTelecomService();
		// === spiner service
		spinner_service.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 > 0) {
					serviceType = arrTelecomServiceBeans.get(arg2)
							.getServiceAlias();
					Log.d("serviceType", serviceType);
				} else {
					serviceType = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		// lay mac dinh khach hang cu
		if (ActivityCreateNewRequestHotLine.instance.infoSubChild != null
				&& ActivityCreateNewRequestHotLine.instance.infoSubChild
						.getCustommerByIdNoBean() != null) {
			arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();
			arrCustommerByIdNoBeans
					.add(ActivityCreateNewRequestHotLine.instance.infoSubChild
							.getCustommerByIdNoBean());
			adaGetListCusAdapter = new GetListCustomerEditableAdapter(activity,
					arrCustommerByIdNoBeans, null);
			lvCustomer.setAdapter(adaGetListCusAdapter);
			Log.d(this.getClass().getName(),
					"FragmentInfoCustomerHotLine imageListenner NULL arrCustommerByIdNoBeans.size() "
							+ arrCustommerByIdNoBeans.size()
							+ " "
							+ arrCustommerByIdNoBeans.get(0));
			// adaGetListCusAdapter2 = new
			// GetListCusAdapter(arrCustommerByIdNoBeans, activity);
			// lvCustomer.setAdapter(adaGetListCusAdapter2);
			btnsearch.setEnabled(false);
			btnnhapmoi.setEnabled(false);
			edit_isdnacount.setEnabled(false);
			edit_sogiayto.setEnabled(false);
			edit_sogpkd.setEnabled(false);
		} else {
			edit_isdnacount.setEnabled(true);
			edit_sogiayto.setEnabled(true);
			edit_sogpkd.setEnabled(true);
			btnsearch.setEnabled(true);
			btnnhapmoi.setEnabled(true);
		}

		SharedPreferences preferences = getActivity().getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);
		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		if (name.contains(";permissionChangeInfoCustNoSubActive;")) {
			permissionChangeInfoCustNoSubActive = true;
		}
		if (name.contains(";permissionChangeInfoCustHaveSubActive;")) {
			permissionChangeInfoCustHaveSubActive = true;
		}
		// === spinner typePaper

	}

	// init typepaper
	private ArrayAdapter<String> initTypePaper() {
		GetTypePaperDal dal = null;
		ArrayAdapter<String> adapter = null;
		try {
			dal = new GetTypePaperDal(getActivity());
			dal.open();
			arrTypePaperBeans = dal.getLisTypepaper();
			dal.close();
			if (arrTypePaperBeans != null && !arrTypePaperBeans.isEmpty()) {
				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						android.R.id.text1);
				for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
					adapter.add(typePaperBeans.getParValues());
				}
				spinner_type_giay_to.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		} finally {
			if (dal != null) {
				dal.close();
			}
		}
		return adapter;
	}

	// init typepaper
	private void initTelecomService() {
		try {

			GetServiceDal dal = new GetServiceDal(getActivity());
			dal.open();
			arrTelecomServiceBeans = dal.getlisTelecomServiceBeans();
			dal.close();
			TelecomServiceBeans serviceBeans = new TelecomServiceBeans();
			serviceBeans.setTele_service_name(getActivity().getResources()
					.getString(R.string.chondichvu));
			arrTelecomServiceBeans.add(0, serviceBeans);
			if (arrTelecomServiceBeans != null
					&& !arrTelecomServiceBeans.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						android.R.id.text1);
				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					adapter.add(telecomServiceBeans.getTele_service_name());
				}
				spinner_service.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// if (adaGetListCusAdapter != null && arrCustommerByIdNoBeans.size() >
		// 0) {
		// Log.d(this.getClass().getName(),
		// "onResume arrCustommerByIdNoBeans.size() " +
		// arrCustommerByIdNoBeans.size());
		//
		// adaGetListCusAdapter = new GetListCustomerEditableAdapter(activity,
		// arrCustommerByIdNoBeans, imageListenner);
		// // adaGetListCusAdapter = new
		// GetListCusAdapter(arrCustommerByIdNoBeans, activity);
		// lvCustomer.setAdapter(adaGetListCusAdapter);
		// }

		// else if (adaGetListCusAdapter2 != null &&
		// arrCustommerByIdNoBeans.size() > 0) {
		// adaGetListCusAdapter2 = new
		// GetListCusAdapter(arrCustommerByIdNoBeans, activity);
		// lvCustomer.setAdapter(adaGetListCusAdapter2);
		// }
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 101:
				if (data != null) {
					CustomerGroupBeans customerGroupBeansKey = (CustomerGroupBeans) data
							.getExtras().getSerializable(
									"customerGroupBeansKey");
					edtloaikh.setText("");
					busType = "";
					edtloaikh.setHint(getString(R.string.chonloaiKH));
					edtnhomkh.setText(customerGroupBeansKey.getNameCusGroup());

					cusGroupId = customerGroupBeansKey.getIdCusGroup();
					Log.d("IDMain", cusGroupId);
					custTypeIdMain = customerGroupBeansKey.getCustTypeId();
					Log.d("custTypeIdMain", custTypeIdMain);
					// busType =
					// arrCustomerGroupBeans.get(arg2).getCode();
					if (custTypeIdMain.equals("1")) {
						lnmasothue.setVisibility(View.VISIBLE);
						lngioitinh.setVisibility(View.GONE);
						linearGPK.setVisibility(View.VISIBLE);
						linearCMT.setVisibility(View.GONE);
						lnsogiayto.setVisibility(View.GONE);
					} else {
						lngioitinh.setVisibility(View.VISIBLE);
						lnmasothue.setVisibility(View.GONE);
						lnsogiayto.setVisibility(View.VISIBLE);
						linearCMT.setVisibility(View.VISIBLE);
						linearGPK.setVisibility(View.GONE);
					}
					if (cusGroupId != null || !cusGroupId.isEmpty()) {
						if (CommonActivity.isNetworkConnected(getActivity())) {
							GetCustomerTypeByCustGroupIdAsyn getByCustGroupIdAsyn = new GetCustomerTypeByCustGroupIdAsyn(
									getActivity());
							getByCustGroupIdAsyn.execute(cusGroupId);
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
				break;
			case 102:
				if (data != null) {

					CustomerTypeByCustGroupBeans customerTypeByCustGroupBeans = (CustomerTypeByCustGroupBeans) data
							.getExtras()
							.getSerializable("customerTypeBeansKey");
					busType = customerTypeByCustGroupBeans.getCode();
					String codeName = customerTypeByCustGroupBeans.getCode()
							+ "-" + customerTypeByCustGroupBeans.getName();
					edtloaikh.setText(codeName);
				}

				break;
			case 106:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras()
							.getSerializable("provinceKey");

					province = areaBean.getProvince();
					initDistrict(province);
					edtprovince.setText(areaBean.getNameProvince());
					edtdistrict.setText("");
					edtprecinct.setText("");
					edtStreetBlock.setText("");
				}
				break;
			case 107:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras()
							.getSerializable("districtKey");
					district = areaBean.getDistrict();
					initPrecinct(province, district);
					edtdistrict.setText(areaBean.getNameDistrict());
					edtprecinct.setText("");
					edtStreetBlock.setText("");
				}
				break;

			case 108:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras()
							.getSerializable("precinctKey");
					precinct = areaBean.getPrecinct();
					edtprecinct.setText(areaBean.getNamePrecint());
					edtStreetBlock.setText("");
				}
				break;
			case 109:
				if (data != null) {
					AreaObj streetBlockKey = (AreaObj) data.getExtras()
							.getSerializable("streetBlockKey");
					streetBlock = streetBlockKey.getAreaCode();
					edtStreetBlock.setText(streetBlockKey.getName());
				}
				break;
			default:
				break;
			}
		}
	}

	private View.OnClickListener imageListenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Object obj = v.getTag();
			if (obj instanceof CustommerByIdNoBean) {
				CustommerByIdNoBean customer = (CustommerByIdNoBean) obj;

				custId = customer.getCustId();

				boolean haveSubActive = customer.getHaveSubActive() != null
						&& "true".equalsIgnoreCase(customer.getHaveSubActive());
				Log.d(Constant.TAG,
						"FragmentInfoCustomerHotLine onItemClick() haveSubActive: "
								+ haveSubActive
								+ " permissionChangeInfoCustNoSubActive: "
								+ permissionChangeInfoCustNoSubActive
								+ " permissionChangeInfoCustHaveSubActive: "
								+ permissionChangeInfoCustHaveSubActive);
				if (!CommonActivity.isNullOrEmpty(customer.getBusPermitNo())) {
					CommonActivity.toast(activity,
							R.string.edit_customer_busPermitNo);
				} else if (haveSubActive && permissionChangeInfoCustNoSubActive
						&& permissionChangeInfoCustHaveSubActive) {
					showPopupEditCustomer(customer);
				} else if (!haveSubActive
						&& permissionChangeInfoCustNoSubActive) {
					showPopupEditCustomer(customer);
				} else {
					CommonActivity.toast(activity,
							R.string.edit_customer_no_permission);
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btnsearch:
			idsnOrAcc = edit_isdnacount.getText().toString();
			Log.d("idsnOrAcc", idsnOrAcc);
			numberPaper = edit_sogiayto.getText().toString();
			Log.d("numberPaper", numberPaper);
			busPermitNo = edit_sogpkd.getText().toString();

			if (serviceType != null && !serviceType.isEmpty()) {

				if (edit_isdnacount.getText().toString() != null
						&& !edit_isdnacount.getText().toString().isEmpty()) {
					getlistgroup();
				} else {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkaccount), Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				if (edit_isdnacount.getText().toString() != null
						&& !edit_isdnacount.getText().toString().isEmpty()) {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.checkserviceType),
							Toast.LENGTH_SHORT).show();
				} else {
					if (edit_sogiayto.getText().toString() != null
							&& !edit_sogiayto.getText().toString().isEmpty()) {
						getlistgroup();
					} else {
						if (edit_sogpkd.getText().toString() != null
								&& !edit_sogpkd.getText().toString().isEmpty()) {
							getlistgroup();
						} else {
							Toast.makeText(
									getActivity(),
									getActivity().getResources().getString(
											R.string.checkinfocus),
									Toast.LENGTH_SHORT).show();
						}

					}
				}
			}

			break;
		case R.id.btnnhapmoi:
			dialogInsertNew = null;
			if (CommonActivity.isNetworkConnected(getActivity())) {

				GetListCusGroupAsyn getListCusGroupAsyn = new GetListCusGroupAsyn(
						getActivity());
				getListCusGroupAsyn.execute();
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

	private void getlistgroup() {

		if (CommonActivity.isNetworkConnected(getActivity()) == true) {
			if (arrCustommerByIdNoBeans != null
					&& arrCustommerByIdNoBeans.size() > 0
					&& adaGetListCusAdapter != null) {
				arrCustommerByIdNoBeans.clear();
				adaGetListCusAdapter.notifyDataSetChanged();
			}
			GetListCustomerAsyn getCustomerAsyn = new GetListCustomerAsyn(
					getActivity());
			getCustomerAsyn.execute();
		} else {
			CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.errorNetwork),
					getActivity().getResources().getString(R.string.app_name))
					.show();
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

	public class CheckCustomerAsyn extends
			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public CheckCustomerAsyn(Context context) {
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
		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
			return getListCustomerIdNo(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
			CommonActivity.hideKeyboard(edit_soGQ, context);
			CommonActivity.hideKeyboard(edit_tenKH, context);
			CommonActivity.hideKeyboard(edit_soGQ, context);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.checkcus),
							getResources().getString(R.string.app_name));
					dialog.show();
				} else {
					String nameCustomer = edit_tenKH.getText().toString();
					Log.d("nameCustomer", nameCustomer);
					String socmt = edit_socmnd.getText().toString();
					Log.d("socmt", socmt);
					String soGPKQ = edit_soGQ.getText().toString();
					Log.d("soGPKQ", soGPKQ);
					CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
					custommerByIdNoBean.setNameCustomer(nameCustomer);
					custommerByIdNoBean.setIdType(typePaper);
					if (socmt != null && !socmt.isEmpty()) {
						custommerByIdNoBean.setIdNo(socmt);
					} else {
						custommerByIdNoBean.setBusPermitNo(soGPKQ);
					}
					custommerByIdNoBean.setTin(edit_masothue.getText()
							.toString().trim());
					custommerByIdNoBean.setCustId(null);
					custommerByIdNoBean.setIscheckCus(false);
					custommerByIdNoBean.setCusGroupId(cusGroupId);
					custommerByIdNoBean.setCusType(busType);
					custommerByIdNoBean.setBirthdayCus(edit_ngaysinhCus
							.getText().toString().trim());
					custommerByIdNoBean.setAddreseCus("");
					custommerByIdNoBean.setIdIssueDate(edit_ngaycap.getText()
							.toString().trim());
					custommerByIdNoBean.setIdIssuePlace(edit_noicap.getText()
							.toString().trim());
					custommerByIdNoBean.setProvince(province);
					custommerByIdNoBean.setPrecint(precinct);
					custommerByIdNoBean.setDistrict(district);

					custommerByIdNoBean.setStreet_block(streetBlock);
					custommerByIdNoBean.setStreet(edt_streetName.getText()
							.toString().trim());
					custommerByIdNoBean.setHome(edtHomeNumber.getText()
							.toString().trim());
					custommerByIdNoBean.setAreaCode(province + district
							+ precinct + streetBlock);

					custommerByIdNoBean.setSex(sex);
					custommerByIdNoBean.setStrIdExpire(editngayhethan.getText()
							.toString());
					try {
						GetAreaDal dal = new GetAreaDal(getActivity());
						dal.open();
						String fulladdress = edtHomeNumber.getText().toString()
								.trim()
								+ " "
								+ edt_streetName.getText().toString().trim()
								+ " "
								+ edtStreetBlock.getText()
								+ " "
								+ dal.getfulladddress(province + district
										+ precinct);
						custommerByIdNoBean.setAddreseCus(fulladdress);
						dal.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (arrCustommerByIdNoBeans != null
							&& arrCustommerByIdNoBeans.size() > 0) {
						arrCustommerByIdNoBeans.clear();

					}

					arrCustommerByIdNoBeans.add(custommerByIdNoBean);
					if (adaGetListCusAdapter != null) {
						adaGetListCusAdapter.notifyDataSetChanged();
						dialogInsertNew.dismiss();
					} else {
						adaGetListCusAdapter = new GetListCustomerEditableAdapter(
								activity, arrCustommerByIdNoBeans,
								imageListenner);
						// adaGetListCusAdapter = new
						// GetListCusAdapter(arrCustommerByIdNoBeans, activity);
						lvCustomer.setAdapter(adaGetListCusAdapter);
						dialogInsertNew.dismiss();
					}

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private ArrayList<CustommerByIdNoBean> getListCustomerIdNo(
				String nameKH, String idNo, String soGPKD) {
			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustomerByIdNo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerByIdNo>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				// if (idsnOrAcc != null && !idsnOrAcc.isEmpty()) {
				// rawData.append("<account>" + nameKH);
				// rawData.append("</account>");
				// } else {
				// rawData.append("<account>" + "");
				// rawData.append("</account>");
				// }
				if (idNo != null && !idNo.isEmpty()) {
					rawData.append("<idNo>" + idNo);
					rawData.append("</idNo>");
				} else {
					rawData.append("<idNo>" + "");
					rawData.append("</idNo>");
				}

				if (soGPKD != null && !soGPKD.isEmpty()) {
					rawData.append("<busPermitNo>" + soGPKD);
					rawData.append("</busPermitNo>");
				} else {
					rawData.append("<busPermitNo>" + "");
					rawData.append("</busPermitNo>");
				}

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getCustomerByIdNo>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCustomerByIdNo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodeCusAttribute = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstCustomer");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						custommerByIdNoBean.setAddreseCus(address);
						String custId = parse.getValue(e1, "custId");
						Log.d("custId", custId);
						custommerByIdNoBean.setCustId(custId);
						String nameCus = parse.getValue(e1, "name");
						Log.d("nameCus", nameCus);
						custommerByIdNoBean.setNameCustomer(nameCus);
						String idNoCus = parse.getValue(e1, "idNo");
						Log.d("idNo", idNoCus);
						custommerByIdNoBean.setIdNo(idNoCus);

						String busPermitNo = parse.getValue(e1, "busPermitNo");
						Log.d("busPermitNo", busPermitNo);
						custommerByIdNoBean.setBusPermitNo(busPermitNo);

						String birthDate = parse.getValue(e1, "birthDate");
						Log.d("birthDate", birthDate);
						custommerByIdNoBean.setBirthdayCus(birthDate);

						String busType = parse.getValue(e1, "busType");
						Log.d("busType", busType);
						custommerByIdNoBean.setCusType(busType);

						String custGroupId = parse.getValue(e1, "custGroupId");
						Log.d("custGroupId", custGroupId);
						custommerByIdNoBean.setCusGroupId(custGroupId);

						custommerByIdNoBean.setProvince(parse.getValue(e1,
								"province"));
						custommerByIdNoBean.setPrecint(parse.getValue(e1,
								"precinct"));
						custommerByIdNoBean.setDistrict(parse.getValue(e1,
								"district"));

						custommerByIdNoBean.setIdType(parse.getValue(e1,
								"idType"));
						custommerByIdNoBean
								.setIdIssueDate(StringUtils.convertDate(parse
										.getValue(e1, "idIssueDate")));
						custommerByIdNoBean.setIdIssuePlace(parse.getValue(e1,
								"idIssuePlace"));

						String haveSubActive = parse.getValue(e1,
								"haveSubActive");
						String requestSendOTP = parse.getValue(e1,
								"requestSendOTP");
						custommerByIdNoBean.setHaveSubActive(haveSubActive);
						custommerByIdNoBean.setRequestSendOTP(requestSendOTP);

						nodeCusAttribute = e1
								.getElementsByTagName("customerAttribute");
						for (int k = 0; k < nodeCusAttribute.getLength(); k++) {
							Element e3 = (Element) nodeCusAttribute.item(k);
							String birthDateAtt = parse.getValue(e3,
									"birthDate");
							Log.d("birthDateAtt", birthDateAtt);
							custommerByIdNoBean.getCustomerAttribute()
									.setBirthDate(
											StringUtils
													.convertDate(birthDateAtt));
							custommerByIdNoBean.getCustomerAttribute()
									.setCustId(parse.getValue(e3, "custId"));
							custommerByIdNoBean.getCustomerAttribute().setId(
									parse.getValue(e3, "id"));
							custommerByIdNoBean.getCustomerAttribute().setIdNo(
									parse.getValue(e3, "idNo"));
							custommerByIdNoBean.getCustomerAttribute()
									.setIdType(parse.getValue(e3, "idType"));
							custommerByIdNoBean
									.getCustomerAttribute()
									.setIssueDate(
											StringUtils.convertDate(parse
													.getValue(e3, "issueDate")));
							custommerByIdNoBean.getCustomerAttribute()
									.setIssuePlace(
											parse.getValue(e3, "issuePlace"));
							custommerByIdNoBean.getCustomerAttribute().setName(
									parse.getValue(e3, "name"));
						}
						lisCustommerByIdNoBeans.add(custommerByIdNoBean);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisCustommerByIdNoBeans;
		}

	}

	// ================ ws get list customerbyIdNo

	public class GetListCustomerAsyn extends
			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListCustomerAsyn(Context context) {
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
		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
			return getListCustomerIdNo();
		}

		@Override
		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
			CommonActivity.hideKeyboard(edit_isdnacount, context);
			CommonActivity.hideKeyboard(edit_sogiayto, context);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					arrCustommerByIdNoBeans = result;

					adaGetListCusAdapter = new GetListCustomerEditableAdapter(
							activity, arrCustommerByIdNoBeans, imageListenner);
					// adaGetListCusAdapter = new
					// GetListCusAdapter(arrCustommerByIdNoBeans, activity);
					lvCustomer.setAdapter(adaGetListCusAdapter);
					// btnnhapmoi.setVisibility(View.GONE);
				} else {
					// btnnhapmoi.setVisibility(View.VISIBLE);

					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.notkh),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private ArrayList<CustommerByIdNoBean> getListCustomerIdNo() {
			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustomerByIdNo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerByIdNo>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				// add tham so input
				if (serviceType != null && !serviceType.isEmpty()) {
					rawData.append("<serviceType>" + serviceType);
					rawData.append("</serviceType>");
				} else {
					// rawData.append("<serviceType>" + "");
					// rawData.append("</serviceType>");
				}
				if (idsnOrAcc != null && !idsnOrAcc.isEmpty()) {
					rawData.append("<account>" + idsnOrAcc);
					rawData.append("</account>");
				} else {
					// rawData.append("<account>" + "");
					// rawData.append("</account>");
				}
				if (numberPaper != null && !numberPaper.isEmpty()) {
					rawData.append("<idNo>" + numberPaper);
					rawData.append("</idNo>");
				} else {
					// rawData.append("<idNo>" + "");
					// rawData.append("</idNo>");
				}
				if (busPermitNo != null && !busPermitNo.isEmpty()) {
					rawData.append("<busPermitNo>" + busPermitNo);
					rawData.append("</busPermitNo>");
				} else {
					// rawData.append("<busPermitNo>" + "");
					// rawData.append("</busPermitNo>");
				}

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getCustomerByIdNo>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCustomerByIdNo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);
				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodeCusAttribute = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstCustomer");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						CustommerByIdNoBean obj = new CustommerByIdNoBean();
						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						obj.setAddreseCus(address);
						String custId = parse.getValue(e1, "custId");
						Log.d("custId", custId);
						obj.setCustId(custId);
						String nameCus = parse.getValue(e1, "name");
						Log.d("nameCus", nameCus);
						obj.setNameCustomer(nameCus);
						String idNoCus = parse.getValue(e1, "idNo");
						Log.d("idNo", idNoCus);
						String tin = parse.getValue(e1, "tin");
						obj.setTin(tin);
						obj.setIdNo(idNoCus);
						String busPermitNo1 = parse.getValue(e1, "busPermitNo");
						Log.d("busPermitNo1", busPermitNo1);
						obj.setBusPermitNo(busPermitNo1);
						String birthDate = parse.getValue(e1, "birthDate");
						Log.d("birthDate", birthDate);
						obj.setBirthdayCus(StringUtils.convertDate(birthDate));

						String busType = parse.getValue(e1, "busType");
						Log.d("busType", busType);
						obj.setCusType(busType);

						String custGroupId = parse.getValue(e1, "custGroupId");
						Log.d("custGroupId", custGroupId);
						obj.setCusGroupId(custGroupId);

						String idType = parse.getValue(e1, "idType");
						obj.setIdType(idType);
						String sex = parse.getValue(e1, "sex");
						obj.setSex(sex);

						String province = parse.getValue(e1, "province");
						obj.setProvince(province);
						String district = parse.getValue(e1, "district");
						obj.setDistrict(district);
						String precinct = parse.getValue(e1, "precinct");
						obj.setPrecint(precinct);

						String street_block = parse.getValue(e1, "streetBlock");
						obj.setStreet_block(street_block);
						String street = parse.getValue(e1, "streetName");
						obj.setStreet(street);
						String home = parse.getValue(e1, "home");
						obj.setHome(home);

						String idIssueDate = parse.getValue(e1, "idIssueDate");
						obj.setIdIssueDate(StringUtils.convertDate(idIssueDate));
						String idIssuePlace = parse
								.getValue(e1, "idIssuePlace");
						obj.setIdIssuePlace(idIssuePlace);

						String idExpireDate = parse
								.getValue(e1, "idExpireDate");
						obj.setIdExpireDate(StringUtils
								.convertDate(idExpireDate));

						String haveSubActive = parse.getValue(e1,
								"haveSubActive");
						String requestSendOTP = parse.getValue(e1,
								"requestSendOTP");
						obj.setHaveSubActive(haveSubActive);
						obj.setRequestSendOTP(requestSendOTP);

						nodeCusAttribute = e1
								.getElementsByTagName("customerAttribute");
						for (int k = 0; k < nodeCusAttribute.getLength(); k++) {
							Element e3 = (Element) nodeCusAttribute.item(k);
							String birthDateAtt = parse.getValue(e3,
									"birthDate");
							obj.getCustomerAttribute().setBirthDate(
									StringUtils.convertDate(birthDateAtt));
							obj.getCustomerAttribute().setCustId(
									parse.getValue(e3, "custId"));
							obj.getCustomerAttribute().setId(
									parse.getValue(e3, "id"));
							obj.getCustomerAttribute().setIdType(
									parse.getValue(e3, "idType"));
							obj.getCustomerAttribute().setIdNo(
									parse.getValue(e3, "idNo"));
							obj.getCustomerAttribute().setIssueDate(
									StringUtils.convertDate(parse.getValue(e3,
											"issueDate")));
							obj.getCustomerAttribute().setIssuePlace(
									parse.getValue(e3, "isssuePlace"));
							obj.getCustomerAttribute().setName(
									parse.getValue(e3, "name"));

						}
						lisCustommerByIdNoBeans.add(obj);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisCustommerByIdNoBeans;
		}

	}

	SimpleDateFormat dbUpdateDateTime = new SimpleDateFormat("dd/MM/yyyy");

	private void showDatePickerDialog(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				// day = dayOfMonth;
				// month = monthOfYear + 1;
				// year = year1;
				mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year1;
				edtngaycap.setText(mNgaycaidat);

				try {

					issueDate = dbUpdateDateTime.parse(mNgaycaidat);
				} catch (Exception e) {
					e.printStackTrace();
				}

				cal.set(year1, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback,
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback, year, month,
					day);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();

	}

	// bo dung them ngay sinh nhat cho KHDN
	private EditText edit_ngaysinhCus;
	private Spinner spinner_province;
	private Spinner spinner_district;
	private Spinner spinner_precint;
	private Spinner spinner_sex;
	private LinearLayout lngioitinh;
	private EditText editngayhethan;
	// arrlist province
	private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();
	private String province = "";
	// arrlist district
	private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
	private String district = "";
	// arrlist precinct
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();
	private String precinct = "";

	private EditText edtStreetBlock;
	private EditText edt_streetName;
	private EditText edtHomeNumber;
	private String streetBlock = "";

	private ArrayList<SexBeans> arrSexBeans = new ArrayList<SexBeans>();
	private String sex = "";

	// private LinearLayout linearCMT;

	private LinearLayout linearGPK;

	// private LinearLayout lnsogiayto;
	private EditText edtnhomkh, edtloaikh;
	private CustomerTypeByCustGroupBeans customerTypeBeansKey = null;
	private EditText edtprovince, edtdistrict, edtprecinct;

	// ================ get list group cusname ================
	public class GetListCusGroupAsyn extends
			AsyncTask<Void, Void, ArrayList<CustomerGroupBeans>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListCusGroupAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<CustomerGroupBeans> doInBackground(Void... params) {
			return sendRequestGetNameProduct();
		}

		@Override
		protected void onPostExecute(ArrayList<CustomerGroupBeans> result) {
			progress.dismiss();

			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					arrCustomerGroupBeans = result;

					if (dialogInsertNew == null) {
						dialogInsertNew = new Dialog(getActivity());
						dialogInsertNew
								.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialogInsertNew
								.setContentView(R.layout.connection_layout_insert_newinfo);

						dialogInsertNew.setCancelable(false);
						// dialogInsertNew.findViewById(R.id.prbCustGroup)
						// .setVisibility(View.GONE);
						dialogInsertNew.findViewById(R.id.btnRefreshCustGroup)
								.setVisibility(View.VISIBLE);
						prbCustType = dialogInsertNew
								.findViewById(R.id.prbCustType);
						// dialogInsertNew.findViewById(R.id.prbCustGroup).setVisibility(View.GONE);
						dialogInsertNew.findViewById(R.id.btnRefreshCustGroup)
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {

										if (CommonActivity
												.isNetworkConnected(getActivity())) {
											// dialogInsertNew.findViewById(
											// R.id.btnRefreshCustGroup)
											// .setVisibility(View.GONE);
											// dialogInsertNew
											// .findViewById(
											// R.id.prbCustGroup)
											// .setVisibility(View.GONE);
											new CacheDatabaseManager(context)
													.insertCusGroup(null);
											GetListCusGroupAsyn getListCusGroupAsyn = new GetListCusGroupAsyn(
													getActivity());
											getListCusGroupAsyn.execute();
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
								});
						btnRefreshCusType = dialogInsertNew
								.findViewById(R.id.btnRefreshCustType);
						btnRefreshCusType
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										if (cusGroupId != null
												|| !cusGroupId.isEmpty()) {
											if (CommonActivity
													.isNetworkConnected(getActivity())) {
												new CacheDatabaseManager(
														context)
														.insertCusTypeCache(
																cusGroupId,
																null);
												btnRefreshCusType
														.setVisibility(View.VISIBLE);
												GetCustomerTypeByCustGroupIdAsyn getByCustGroupIdAsyn = new GetCustomerTypeByCustGroupIdAsyn(
														getActivity());
												getByCustGroupIdAsyn
														.execute(cusGroupId);
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
								});

						edit_tenKH = (EditText) dialogInsertNew
								.findViewById(R.id.edit_tenKH);
						edit_socmnd = (EditText) dialogInsertNew
								.findViewById(R.id.edit_socmnd);
						edit_soGQ = (EditText) dialogInsertNew
								.findViewById(R.id.edit_soGQ);
						spinner_type_giay_to = (Spinner) dialogInsertNew
								.findViewById(R.id.spinner_type_giay_to);
						linearCMT = (LinearLayout) dialogInsertNew
								.findViewById(R.id.linearCMT);
						linearCMT.setVisibility(View.GONE);
						linearGPK = (LinearLayout) dialogInsertNew
								.findViewById(R.id.linearsoGPKD);
						linearGPK.setVisibility(View.GONE);
						lnsogiayto = (LinearLayout) dialogInsertNew
								.findViewById(R.id.lnsogiayto);
						lnmasothue = (LinearLayout) dialogInsertNew
								.findViewById(R.id.lnmasothue);
						edit_masothue = (EditText) dialogInsertNew
								.findViewById(R.id.edit_masothue);

						// spinner_nhomkh = (Spinner) dialogInsertNew
						// .findViewById(R.id.spinner_nhomkh);
						// spinner_loaikh = (Spinner) dialogInsertNew
						// .findViewById(R.id.spinner_loaikh);
						// private EditText edtnhomkh,edtloaikh;
						// private LinearLayout lnnhomkh,lnloaikh;
						// private ListView lvnhomkh,lvloaikh;
						edtnhomkh = (EditText) dialogInsertNew
								.findViewById(R.id.edtnhomkh);
						edtnhomkh.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								if (arrCustomerGroupBeans != null
										&& !arrCustomerGroupBeans.isEmpty()) {
									Intent intent = new Intent(getActivity(),
											FragmentSearchCusGroupMobile.class);
									intent.putExtra("arrCustomerGroupBeansKey",
											arrCustomerGroupBeans);
									startActivityForResult(intent, 101);
								}
							}
						});
						edtloaikh = (EditText) dialogInsertNew
								.findViewById(R.id.edtloaikh);
						edtloaikh.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								if (arrCustomerTypeByCustGroupBeans != null
										&& !arrCustomerTypeByCustGroupBeans
												.isEmpty()) {
									Intent intent = new Intent(getActivity(),
											FragmentSearchCusTypeMobile.class);
									intent.putExtra("arrCustomerTypeBeansKey",
											arrCustomerTypeByCustGroupBeans);
									startActivityForResult(intent, 102);
								}

							}
						});
						edit_ngaycap = (EditText) dialogInsertNew
								.findViewById(R.id.edit_ngaycap);
						edit_ngaycap.setText(dateNowString);
						edit_noicap = (EditText) dialogInsertNew
								.findViewById(R.id.edtnoicap);

						// spinner_province = (Spinner) dialogInsertNew
						// .findViewById(R.id.spinner_province);
						// spinner_district = (Spinner) dialogInsertNew
						// .findViewById(R.id.spinner_quanhuyen);
						// spinner_precint = (Spinner) dialogInsertNew
						// .findViewById(R.id.spinner_phuongxa);
						// khoi tao danh sach tinh
						initProvince();
						edtprovince = (EditText) dialogInsertNew
								.findViewById(R.id.edtprovince);
						if (!CommonActivity.isNullOrEmpty(Session.province)) {
							initDistrict(Session.province);
							try {
								GetAreaDal dal = new GetAreaDal(getActivity());
								dal.open();
								edtprovince.setText(dal
										.getNameProvince(Session.province));
								province = Session.province;
								dal.close();
							} catch (Exception e) {
							}
						}

						edtprovince.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								Intent intent = new Intent(getActivity(),
										FragmentSearchLocation.class);
								intent.putExtra("arrProvincesKey", arrProvince);
								Bundle mBundle = new Bundle();
								mBundle.putString("checkKey", "1");
								intent.putExtras(mBundle);
								startActivityForResult(intent, 106);

							}
						});
						edtdistrict = (EditText) dialogInsertNew
								.findViewById(R.id.edtdistrict);
						// edtdistrict.setText(Session.district);
						if (!CommonActivity.isNullOrEmpty(Session.province)
								&& !CommonActivity
										.isNullOrEmpty(Session.district)) {
							initPrecinct(Session.province, Session.district);
							try {
								GetAreaDal dal = new GetAreaDal(getActivity());
								dal.open();
								edtdistrict.setText(dal.getNameDistrict(
										Session.province, Session.district));
								district = Session.district;
								dal.close();
							} catch (Exception e) {
							}
						}
						edtdistrict.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(getActivity(),
										FragmentSearchLocation.class);
								intent.putExtra("arrDistrictKey", arrDistrict);
								Bundle mBundle = new Bundle();
								mBundle.putString("checkKey", "2");
								intent.putExtras(mBundle);
								startActivityForResult(intent, 107);

							}
						});
						edtprecinct = (EditText) dialogInsertNew
								.findViewById(R.id.edtprecinct);
						edtprecinct.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(getActivity(),
										FragmentSearchLocation.class);
								intent.putExtra("arrPrecinctKey", arrPrecinct);
								Bundle mBundle = new Bundle();
								mBundle.putString("checkKey", "3");
								intent.putExtras(mBundle);
								startActivityForResult(intent, 108);

							}
						});

						edtStreetBlock = (EditText) dialogInsertNew
								.findViewById(R.id.edtStreetBlock); // to
						edt_streetName = (EditText) dialogInsertNew
								.findViewById(R.id.edt_streetName); // duong
						edtHomeNumber = (EditText) dialogInsertNew
								.findViewById(R.id.edtHomeNumber); // so nha
						edtStreetBlock
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										Intent intent = new Intent(
												getActivity(),
												FragmentSearchLocation.class);
										Bundle mBundle = new Bundle();
										mBundle.putString("checkKey", "4");
										mBundle.putString("province", province);
										mBundle.putString("district", district);
										mBundle.putString("precinct", precinct);
										intent.putExtras(mBundle);
										startActivityForResult(intent, 109);
									}
								});

						spinner_sex = (Spinner) dialogInsertNew
								.findViewById(R.id.spinner_gioitinh);
						lngioitinh = (LinearLayout) dialogInsertNew
								.findViewById(R.id.lngioitinh);

						final LinearLayout lnngayhethan = (LinearLayout) dialogInsertNew
								.findViewById(R.id.lnngayhethan);
						editngayhethan = (EditText) dialogInsertNew
								.findViewById(R.id.edit_ngayhethan);
						editngayhethan.setText(dateNowString);
						editngayhethan.setOnClickListener(editTextListener);

						if (arrSexBeans != null && arrSexBeans.size() > 0) {
							arrSexBeans.clear();
						}
						initSex();

						// spinner_province
						// .setOnItemSelectedListener(new
						// OnItemSelectedListener() {
						//
						// @Override
						// public void onItemSelected(
						// AdapterView<?> arg0, View arg1,
						// int arg2, long arg3) {
						// province = arrProvince.get(arg2)
						// .getProvince();
						// initDistrict(province);
						// }
						//
						// @Override
						// public void onNothingSelected(
						// AdapterView<?> arg0) {
						//
						// }
						// });

						// spinner_district
						// .setOnItemSelectedListener(new
						// OnItemSelectedListener() {
						//
						// @Override
						// public void onItemSelected(
						// AdapterView<?> arg0, View arg1,
						// int arg2, long arg3) {
						// district = arrDistrict.get(arg2)
						// .getDistrict();
						// initPrecinct(province, district);
						// }
						//
						// @Override
						// public void onNothingSelected(
						// AdapterView<?> arg0) {
						// }
						// });

						// spinner_precint
						// .setOnItemSelectedListener(new
						// OnItemSelectedListener() {
						//
						// @Override
						// public void onItemSelected(
						// AdapterView<?> arg0, View arg1,
						// int arg2, long arg3) {
						// precinct = arrPrecinct.get(arg2)
						// .getPrecinct();
						//
						// }
						//
						// @Override
						// public void onNothingSelected(
						// AdapterView<?> arg0) {
						//
						// }
						// });

						spinner_sex
								.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										sex = arrSexBeans.get(arg2).getValues();

									}

									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {

									}
								});
						edit_ngaysinhCus = (EditText) dialogInsertNew
								.findViewById(R.id.edit_ngaysinhCus);
						edit_ngaysinhCus
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										showDatePickerBirthCus(edit_ngaysinhCus);

									}
								});
						edit_ngaysinhCus.setText(dateNowString);

						edit_ngaycap.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								showDatePickerDialog(edit_ngaycap);
							}
						});

						edit_ngaycap.setText(dateNowString);

						initTypePaper();
						// if (arrCustomerGroupBeans != null
						// && arrCustomerGroupBeans.size() > 0) {
						// getCusGroupAdapter = new GetCusGroupAdapter(
						// arrCustomerGroupBeans, getActivity());
						// lvnhomkh.setAdapter(getCusGroupAdapter);
						// ArrayAdapter<String> adapter = new
						// ArrayAdapter<String>(
						// getActivity(),
						// android.R.layout.simple_dropdown_item_1line,
						// android.R.id.text1);
						// for (CustomerGroupBeans customerGroupBeans :
						// arrCustomerGroupBeans) {
						// adapter.add(customerGroupBeans
						// .getNameCusGroup());
						// }
						// if (spinner_nhomkh != null) {
						// spinner_nhomkh.setAdapter(adapter);
						// }
						// }

						// lvnhomkh.setOnItemClickListener(new
						// OnItemClickListener() {
						//
						// @Override
						// public void onItemClick(AdapterView<?> arg0,
						// View arg1, int arg2, long arg3) {
						//
						// edtnhomkh.setText(arrCustomerGroupBeans.get(
						// arg2).getNameCusGroup());
						//
						// cusGroupId = arrCustomerGroupBeans.get(arg2)
						// .getIdCusGroup();
						// Log.d("IDMain", cusGroupId);
						// custTypeIdMain = arrCustomerGroupBeans
						// .get(arg2).getCustTypeId();
						// Log.d("custTypeIdMain", custTypeIdMain);
						// // busType =
						// // arrCustomerGroupBeans.get(arg2).getCode();
						// if (custTypeIdMain.equals("1")) {
						// lnmasothue.setVisibility(View.VISIBLE);
						// lngioitinh.setVisibility(View.GONE);
						// linearGPK.setVisibility(View.VISIBLE);
						// linearCMT.setVisibility(View.GONE);
						// lnsogiayto.setVisibility(View.GONE);
						// } else {
						// lngioitinh.setVisibility(View.VISIBLE);
						// lnmasothue.setVisibility(View.GONE);
						// lnsogiayto.setVisibility(View.VISIBLE);
						// linearCMT.setVisibility(View.VISIBLE);
						// linearGPK.setVisibility(View.GONE);
						// }
						// if (cusGroupId != null || !cusGroupId.isEmpty()) {
						// if (CommonActivity
						// .isNetworkConnected(getActivity())) {
						// GetCustomerTypeByCustGroupIdAsyn getByCustGroupIdAsyn
						// = new GetCustomerTypeByCustGroupIdAsyn(
						// getActivity());
						// getByCustGroupIdAsyn
						// .execute(cusGroupId);
						// } else {
						// CommonActivity
						// .createAlertDialog(
						// getActivity(),
						// getActivity()
						// .getResources()
						// .getString(
						// R.string.errorNetwork),
						// getActivity()
						// .getResources()
						// .getString(
						// R.string.app_name))
						// .show();
						// }
						//
						// }
						//
						// }
						// });
						// lvloaikh.setOnItemClickListener(new
						// OnItemClickListener() {
						//
						// @Override
						// public void onItemClick(AdapterView<?> arg0,
						// View arg1, int arg2, long arg3) {
						// busType = arrCustomerTypeByCustGroupBeans.get(
						// arg2).getCode();
						// if (arrCustomerTypeByCustGroupBeans != null
						// && !arrCustomerTypeByCustGroupBeans
						// .isEmpty()) {
						// String codeName = arrCustomerTypeByCustGroupBeans
						// .get(arg2).getCode()
						// + "-"
						// + arrCustomerTypeByCustGroupBeans
						// .get(arg2).getName();
						// edtloaikh.setText(codeName);
						// }
						// }
						// });
						// chon nhom KH
						// spinner_nhomkh
						// .setOnItemSelectedListener(new
						// OnItemSelectedListener() {
						// @Override
						// public void onItemSelected(
						// AdapterView<?> arg0, View arg1,
						// int arg2, long arg3) {
						//

						//
						// }
						//
						// @Override
						// public void onNothingSelected(
						// AdapterView<?> arg0) {
						// }
						// });

						// ===== chon Loai KH theo nhom kh
						// spinner_loaikh
						// .setOnItemSelectedListener(new
						// OnItemSelectedListener() {
						//
						// @Override
						// public void onItemSelected(
						// AdapterView<?> arg0, View arg1,
						// int arg2, long arg3) {
						//

						//
						// }
						//
						// @Override
						// public void onNothingSelected(
						// AdapterView<?> arg0) {
						//
						// }
						// });

						spinner_type_giay_to
								.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										typePaper = arrTypePaperBeans.get(arg2)
												.getParType();

										if (typePaper.equals("1")) {
											edit_socmnd
													.setInputType(InputType.TYPE_CLASS_NUMBER);
										} else {

											if ("3".equalsIgnoreCase(typePaper)) {
												lnngayhethan
														.setVisibility(View.VISIBLE);
											} else {
												lnngayhethan
														.setVisibility(View.GONE);
											}

											edit_socmnd
													.setInputType(InputType.TYPE_CLASS_TEXT);
										}
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {

									}
								});

						dialogInsertNew.findViewById(R.id.btnthemmoi)
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										// insert new infoCustomer

										if (validateCusGr()) {
											if ((edit_tenKH.getText()
													.toString() != null && !edit_tenKH
													.getText().toString()
													.isEmpty())
													&& (edit_socmnd.getText()
															.toString() != null && !edit_socmnd
															.getText()
															.toString()
															.isEmpty())
													|| (edit_tenKH.getText()
															.toString() != null && !edit_tenKH
															.getText()
															.toString()
															.isEmpty())
													&& (edit_soGQ.getText()
															.toString() != null && !edit_soGQ
															.getText()
															.toString()
															.isEmpty())) {

												if (typePaper
														.equalsIgnoreCase("1")
														&& !custTypeIdMain
																.equals("1")) {

													if (edit_socmnd.getText()
															.toString()
															.length() == 9
															|| edit_socmnd
																	.getText()
																	.toString()
																	.length() == 12) {

														if (StringUtils
																.CheckCharSpecical(edit_tenKH
																		.getText()
																		.toString())) {
															Toast.makeText(
																	getActivity(),
																	getResources()
																			.getString(
																					R.string.checkcharspecical),
																	Toast.LENGTH_SHORT)
																	.show();
														} else {
															if ((edit_tenKH
																	.getText()
																	.toString() != null && !edit_tenKH
																	.getText()
																	.toString()
																	.isEmpty())
																	&& (edit_socmnd
																			.getText()
																			.toString() != null && !edit_socmnd
																			.getText()
																			.toString()
																			.isEmpty())) {

																// validate ngay
																// sinh
																// nhat
																if (validateBirthCus()) {

																	if (issueDate
																			.after(dateNow)) {
																		Toast.makeText(
																				getActivity(),
																				getActivity()
																						.getResources()
																						.getString(
																								R.string.ngaycapnhohonngayhientai),
																				Toast.LENGTH_SHORT)
																				.show();

																	} else {
																		if (DateTimeUtils
																				.compareAge(
																						issueDate,
																						15)) {
																			Toast.makeText(
																					getActivity(),
																					getString(R.string.checkDatengaycap),
																					Toast.LENGTH_SHORT)
																					.show();
																		} else {
																			// =====
																			// check
																			// noi
																			// cap
																			// =================

																			if (issueDate
																					.after(birthDateCus)
																					|| issueDate
																							.equals(birthDateCus)) {
																				if (!CommonActivity
																						.isNullOrEmpty(edit_noicap
																								.getText()
																								.toString())) {

																					CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
																							getActivity());
																					checkCustomerAsyn
																							.execute(
																									edit_tenKH
																											.getText()
																											.toString(),
																									edit_socmnd
																											.getText()
																											.toString(),
																									"");
																				} else {
																					Toast.makeText(
																							getActivity(),
																							getString(R.string.checknoicap),
																							Toast.LENGTH_SHORT)
																							.show();
																				}
																			} else {
																				Toast.makeText(
																						getActivity(),
																						getString(R.string.nsnhongaycap),
																						Toast.LENGTH_SHORT)
																						.show();
																			}

																		}
																	}

																} else {

																	// if
																	// (validateBirthCus())
																	// {
																	if (issueDate
																			.after(dateNow)) {
																		Toast.makeText(
																				getActivity(),
																				getActivity()
																						.getResources()
																						.getString(
																								R.string.ngaycapnhohonngayhientai),
																				Toast.LENGTH_SHORT)
																				.show();

																	} else {

																		if (issueDate
																				.after(birthDateCus)
																				|| issueDate
																						.equals(birthDateCus)) {
																			if (edit_noicap
																					.getText()
																					.toString() != null
																					&& !edit_noicap
																							.getText()
																							.toString()
																							.isEmpty()) {

																				if (!CommonActivity
																						.isNullOrEmpty(edit_masothue
																								.getText()
																								.toString())) {
																					CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
																							getActivity());
																					checkCustomerAsyn
																							.execute(
																									edit_tenKH
																											.getText()
																											.toString(),
																									"",
																									edit_soGQ
																											.getText()
																											.toString());
																				} else {
																					Toast.makeText(
																							getActivity(),
																							getString(R.string.checkmst),
																							Toast.LENGTH_SHORT)
																							.show();

																				}

																			} else {
																				Toast.makeText(
																						getActivity(),
																						getString(R.string.checknoicap),
																						Toast.LENGTH_SHORT)
																						.show();
																			}
																		} else {
																			Toast.makeText(
																					getActivity(),
																					getString(R.string.nsnhongaycap),
																					Toast.LENGTH_SHORT)
																					.show();
																		}
																	}

																}

															}
														}

													} else {

														Toast.makeText(
																getActivity(),
																getResources()
																		.getString(
																				R.string.checkCMT),
																Toast.LENGTH_SHORT)
																.show();

													}
												} else {

													if (StringUtils
															.CheckCharSpecical(edit_tenKH
																	.getText()
																	.toString()) == true) {
														Toast.makeText(
																getActivity(),
																getResources()
																		.getString(
																				R.string.checkcharspecical),
																Toast.LENGTH_SHORT)
																.show();
													} else {
														if ((edit_tenKH
																.getText()
																.toString() != null && !edit_tenKH
																.getText()
																.toString()
																.isEmpty())
																&& (edit_socmnd
																		.getText()
																		.toString() != null && !edit_socmnd
																		.getText()
																		.toString()
																		.isEmpty())) {
															if (issueDate
																	.after(dateNow)) {
																Toast.makeText(
																		getActivity(),
																		getActivity()
																				.getResources()
																				.getString(
																						R.string.ngaycapnhohonngayhientai),
																		Toast.LENGTH_SHORT)
																		.show();

															} else {
																if (DateTimeUtils
																		.compareAge(
																				issueDate,
																				15)) {
																	Toast.makeText(
																			getActivity(),
																			getString(R.string.checkDatengaycap),
																			Toast.LENGTH_SHORT)
																			.show();
																} else {

																	if (issueDate
																			.after(birthDateCus)
																			|| issueDate
																					.equals(birthDateCus)) {
																		// =====
																		// check
																		// noi
																		// cap
																		// =================
																		if (edit_noicap
																				.getText()
																				.toString() != null
																				&& !edit_noicap
																						.getText()
																						.toString()
																						.isEmpty()) {

																			CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
																					getActivity());
																			checkCustomerAsyn
																					.execute(
																							edit_tenKH
																									.getText()
																									.toString(),
																							edit_socmnd
																									.getText()
																									.toString(),
																							"");
																		} else {
																			Toast.makeText(
																					getActivity(),
																					getString(R.string.checknoicap),
																					Toast.LENGTH_SHORT)
																					.show();
																		}
																	} else {
																		Toast.makeText(
																				getActivity(),
																				getString(R.string.nsnhongaycap),
																				Toast.LENGTH_SHORT)
																				.show();
																	}
																}
															}

														} else {

															if (validateBirthCus()) {
																if (issueDate
																		.after(dateNow)) {
																	Toast.makeText(
																			getActivity(),
																			getActivity()
																					.getResources()
																					.getString(
																							R.string.ngaycapnhohonngayhientai),
																			Toast.LENGTH_SHORT)
																			.show();

																} else {

																	if (issueDate
																			.after(birthDateCus)
																			|| issueDate
																					.equals(birthDateCus)) {
																		if (edit_noicap
																				.getText()
																				.toString() != null
																				&& !edit_noicap
																						.getText()
																						.toString()
																						.isEmpty()) {
																			if (!CommonActivity
																					.isNullOrEmpty(edit_masothue
																							.getText()
																							.toString())) {
																				CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
																						getActivity());
																				checkCustomerAsyn
																						.execute(
																								edit_tenKH
																										.getText()
																										.toString(),
																								"",
																								edit_soGQ
																										.getText()
																										.toString());
																			} else {
																				Toast.makeText(
																						getActivity(),
																						getString(R.string.checkmst),
																						Toast.LENGTH_SHORT)
																						.show();

																			}
																		} else {
																			Toast.makeText(
																					getActivity(),
																					getString(R.string.checknoicap),
																					Toast.LENGTH_SHORT)
																					.show();
																		}

																	} else {
																		Toast.makeText(
																				getActivity(),
																				getString(R.string.nsnhongaycap),
																				Toast.LENGTH_SHORT)
																				.show();
																	}

																}
															}
														}

													}

												}

											} else {
												Toast.makeText(
														getActivity(),
														getActivity()
																.getResources()
																.getString(
																		R.string.thieu_tt),
														Toast.LENGTH_SHORT)
														.show();
											}
										}
									}

								});
						dialogInsertNew.findViewById(R.id.btncancel)
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										dialogInsertNew.dismiss();

									}
								});
						// if (!dialogInsertNew.isShowing()) {
						dialogInsertNew.show();
						// }
					}

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// =====method get list CustomerGroupBeans ========================
		public ArrayList<CustomerGroupBeans> sendRequestGetNameProduct() {
			String original = null;
			ArrayList<CustomerGroupBeans> liCustomerGroupBeans = new ArrayList<CustomerGroupBeans>();
			try {

				liCustomerGroupBeans = new CacheDatabaseManager(context)
						.getListCustGroupInCache();
				if (liCustomerGroupBeans != null
						&& !liCustomerGroupBeans.isEmpty()) {
					errorCode = "0";
					return liCustomerGroupBeans;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustomerGroup");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerGroup>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getCustomerGroup>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCustomerGroup");
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
					nodechild = doc.getElementsByTagName("lstCustomerGroup");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						CustomerGroupBeans customerGroupBeans = new CustomerGroupBeans();
						String code = parse.getValue(e1, "code");
						Log.d("code", code);
						customerGroupBeans.setCode(code);
						String custTypeId = parse.getValue(e1, "custTypeId");
						Log.d("custTypeId", custTypeId);
						customerGroupBeans.setCustTypeId(custTypeId);
						String depsciption = parse.getValue(e1, "depsciption");
						Log.d("depsciption", depsciption);
						customerGroupBeans.setDepsciption(depsciption);
						String id = parse.getValue(e1, "id");
						Log.d("id", id);
						customerGroupBeans.setIdCusGroup(id);
						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						customerGroupBeans.setNameCusGroup(name);
						String status = parse.getValue(e1, "status");
						Log.d("status", status);
						customerGroupBeans.setStatus(status);
						String updateDate = parse.getValue(e1, "updateDate");
						customerGroupBeans.setUpdateDate(updateDate);
						String updateUser = parse.getValue(e1, "updateUser");
						Log.d("updateUser", updateUser);
						customerGroupBeans.setUpdateUser(updateUser);
						liCustomerGroupBeans.add(customerGroupBeans);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			new CacheDatabaseManager(context)
					.insertCusGroup(liCustomerGroupBeans);
			return liCustomerGroupBeans;
		}
	}

	// ===== webservice getCustomerTypeByCustGroupId === lay theo custGroupId
	public class GetCustomerTypeByCustGroupIdAsyn extends
			AsyncTask<String, Void, ArrayList<CustomerTypeByCustGroupBeans>> {

		// ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetCustomerTypeByCustGroupIdAsyn(Context context) {
			this.context = context;
			// this.progress = new ProgressDialog(this.context);
			prbCustType.setVisibility(View.VISIBLE);
			// check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		@Override
		protected ArrayList<CustomerTypeByCustGroupBeans> doInBackground(
				String... params) {
			return sendRequestCustomerTypeByCustGroupBeans(params[0]);
		}

		@Override
		protected void onPostExecute(
				ArrayList<CustomerTypeByCustGroupBeans> result) {
			// progress.dismiss();
			prbCustType.setVisibility(View.GONE);
			btnRefreshCusType.setVisibility(View.VISIBLE);
			if (errorCode.equals("0")) {
				if (result.size() > 0 && !result.isEmpty()) {

					arrCustomerTypeByCustGroupBeans = result;

					if (arrCustomerTypeByCustGroupBeans != null
							&& arrCustomerTypeByCustGroupBeans.size() > 0) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getActivity(),
								android.R.layout.simple_dropdown_item_1line,
								android.R.id.text1);
						for (CustomerTypeByCustGroupBeans customerTypeByCustGroupBeans : arrCustomerTypeByCustGroupBeans) {
							adapter.add(customerTypeByCustGroupBeans.getName()
									+ "-"
									+ customerTypeByCustGroupBeans.getCode());
						}
						if (spinner_loaikh != null) {
							spinner_loaikh.setAdapter(adapter);
						}
					}
				} else {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getActivity(),
							android.R.layout.simple_dropdown_item_1line,
							android.R.id.text1);
					spinner_loaikh.setAdapter(adapter);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notinfokh),
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
					if (description == null || description.equals("")) {
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
		public ArrayList<CustomerTypeByCustGroupBeans> sendRequestCustomerTypeByCustGroupBeans(
				String custGroupId) {
			String original = null;
			ArrayList<CustomerTypeByCustGroupBeans> lisCustomerTypeByCustGroupBeans = new ArrayList<CustomerTypeByCustGroupBeans>();
			try {

				lisCustomerTypeByCustGroupBeans = new CacheDatabaseManager(
						context).getListCustTypeInCache(cusGroupId);
				if (lisCustomerTypeByCustGroupBeans != null
						&& !lisCustomerTypeByCustGroupBeans.isEmpty()) {
					errorCode = "0";
					return lisCustomerTypeByCustGroupBeans;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getCustomerTypeByCustGroupId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerTypeByCustGroupId>");
				rawData.append("<getCustomerTypeByCustGroupId>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<custGroupId>" + custGroupId);
				rawData.append("</custGroupId>");
				rawData.append("</getCustomerTypeByCustGroupId>");
				rawData.append("</ws:getCustomerTypeByCustGroupId>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCustomerTypeByCustGroupId");
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
					nodechild = doc.getElementsByTagName("lstCustomerType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						CustomerTypeByCustGroupBeans cusByCustGroupBeans = new CustomerTypeByCustGroupBeans();
						String addressRequired = parse.getValue(e1,
								"addressRequired");
						Log.d("addressRequired", addressRequired);
						cusByCustGroupBeans.setAddressRequired(addressRequired);
						String birthdayRequired = parse.getValue(e1,
								"birthdayRequired");
						Log.d("birthdayRequired", birthdayRequired);
						cusByCustGroupBeans
								.setBirthdayRequired(birthdayRequired);
						String code = parse.getValue(e1, "code");
						Log.d("code", code);
						cusByCustGroupBeans.setCode(code);
						String custGroupId1 = parse.getValue(e1, "custGroupId");
						Log.d("custGroupId1", custGroupId1);
						cusByCustGroupBeans.setCustGroupId(custGroupId1);
						String districtRequired = parse.getValue(e1,
								"districtRequired");
						Log.d("districtRequired", districtRequired);
						cusByCustGroupBeans
								.setDistrictRequired(districtRequired);
						String homeRequired = parse
								.getValue(e1, "homeRequired");
						Log.d("homeRequired", homeRequired);
						cusByCustGroupBeans.setHomeRequired(homeRequired);
						String id = parse.getValue(e1, "id");
						Log.d("id", id);
						cusByCustGroupBeans.setId(id);
						String idNoCARequired = parse.getValue(e1,
								"idNoCARequired");
						Log.d("idNoCARequired", idNoCARequired);
						cusByCustGroupBeans.setIdNoCARequired(idNoCARequired);
						String idNoRequired = parse
								.getValue(e1, "idNoRequired");
						Log.d("idNoRequired", idNoRequired);
						cusByCustGroupBeans.setIdNoRequired(idNoRequired);
						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						cusByCustGroupBeans.setName(name);
						String nameRequired = parse
								.getValue(e1, "nameRequired");
						Log.d("nameRequired", nameRequired);
						cusByCustGroupBeans.setNameRequired(nameRequired);
						String nationalityRequired = parse.getValue(e1,
								"nationalityRequired");
						Log.d("nationalityRequired", nationalityRequired);
						cusByCustGroupBeans
								.setNationalityRequired(nationalityRequired);
						String otherRequired = parse.getValue(e1,
								"otherRequired");
						Log.d("otherRequired", otherRequired);
						cusByCustGroupBeans.setOtherRequired(otherRequired);
						String passportRequired = parse.getValue(e1,
								"passportRequired");
						Log.d("passportRequired", passportRequired);
						cusByCustGroupBeans
								.setPassportRequired(passportRequired);
						String permitRequired = parse.getValue(e1,
								"permitRequired");
						Log.d("permitRequired", permitRequired);
						cusByCustGroupBeans.setPermitRequired(permitRequired);
						String popRequired = parse.getValue(e1, "popRequired");
						Log.d("popRequired", popRequired);
						cusByCustGroupBeans.setPopRequired(popRequired);
						String precinctRequired = parse.getValue(e1,
								"precinctRequired");
						cusByCustGroupBeans
								.setPrecinctRequired(precinctRequired);
						String provinceRequired = parse.getValue(e1,
								"provinceRequired");
						cusByCustGroupBeans
								.setProvinceRequired(provinceRequired);
						String sexRequired = parse.getValue(e1, "sexRequired");
						cusByCustGroupBeans.setSexRequired(sexRequired);
						String status = parse.getValue(e1, "status");
						cusByCustGroupBeans.setStatus(status);
						String streetBlockRequired = parse.getValue(e1,
								"streetBlockRequired");
						cusByCustGroupBeans
								.setStreetBlockRequired(streetBlockRequired);
						String streetRequired = parse.getValue(e1,
								"streetRequired");
						cusByCustGroupBeans.setStreetRequired(streetRequired);
						String tax = parse.getValue(e1, "tax");
						cusByCustGroupBeans.setTax(tax);
						String tinRequired = parse.getValue(e1, "tinRequired");
						cusByCustGroupBeans.setTinRequired(tinRequired);
						String updateUser = parse.getValue(e1, "updateUser");
						cusByCustGroupBeans.setUpdateUser(updateUser);
						lisCustomerTypeByCustGroupBeans
								.add(cusByCustGroupBeans);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			new CacheDatabaseManager(context).insertCusTypeCache(custGroupId,
					lisCustomerTypeByCustGroupBeans);
			return lisCustomerTypeByCustGroupBeans;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		final int p = position;
		// TUANTD7 EDIT HERE
		for (CustommerByIdNoBean item : arrCustommerByIdNoBeans) {
			item.setIscheckCus(false);
		}
		arrCustommerByIdNoBeans.get(p).setIscheckCus(true);
		adaGetListCusAdapter.notifyDataSetChanged();
		for (CustommerByIdNoBean cusBean : arrCustommerByIdNoBeans) {
			if (cusBean.isIscheckCus() == true) {
				custommer = cusBean;
				account = cusBean.getNameCustomer();
				Log.d("account", account);
				cusId = cusBean.getCustId();

				if (custommer != null
						&& !CommonActivity.isNullOrEmpty(custommer
								.getBusPermitNo())) {
					if (custommer.getCustomerAttribute() != null
							&& !CommonActivity.isNullOrEmpty(custommer
									.getCustomerAttribute().getIdNo())) {
						ActivityCreateNewRequestHotLine.instance.tHost
								.setCurrentTab(1);
					} else {
						showInsertAttribute();
					}
				} else {
					ActivityCreateNewRequestHotLine.instance.tHost
							.setCurrentTab(1);
				}
				break;
			}
		}
	}

	// showpopup insert new info customer
	// TuanTD7
	private void showPopupEditCustomer(final CustommerByIdNoBean customer) {
		Log.d(this.getClass().getSimpleName(), "showPopupEditCustomer "
				+ customer);

		if (dialogEditCustomer == null) {
			dialogEditCustomer = new Dialog(activity);
			dialogEditCustomer.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogEditCustomer
					.setContentView(R.layout.connectionmobile_layout_edit_customer_info);
			dialogEditCustomer.setCancelable(true);
			dialogEditCustomer.setTitle(R.string.edit_customer_hotline);
		}

		TextView tvTitle = (TextView) dialogEditCustomer
				.findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.edit_customer_hotline);

		spn_reason_fail = (Spinner) dialogEditCustomer
				.findViewById(R.id.spn_reason_fail);
		prbreason = (ProgressBar) dialogEditCustomer
				.findViewById(R.id.prbreason);
		prbreason.setVisibility(View.GONE);

		prbreasonBtn = (ProgressBar) dialogEditCustomer
				.findViewById(R.id.prbreasonBtn);
		prbreasonBtn.setVisibility(View.GONE);

		lnOTP = (LinearLayout) dialogEditCustomer.findViewById(R.id.lnOTP);

		if (customer.getRequestSendOTP() != null
				&& "true".equalsIgnoreCase(customer.getRequestSendOTP())) {
			lnOTP.setVisibility(View.VISIBLE);
		} else {
			lnOTP.setVisibility(View.GONE);
		}

		edtOTPIsdn = (EditText) dialogEditCustomer
				.findViewById(R.id.edtOTPIsdn);
		edtOTPCode = (EditText) dialogEditCustomer
				.findViewById(R.id.edtOTPCode);
		edtOTPIsdn.setText("");
		edtOTPCode.setText("");
		btnSendOTP = (Button) dialogEditCustomer.findViewById(R.id.btnSendOTP);
		btnSendOTP.setEnabled(true);
		btnSendOTP.setVisibility(View.VISIBLE);
		btnSendOTP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String isdnOTP = edtOTPIsdn.getText().toString();
				if (CommonActivity.isNullOrEmpty(isdnOTP)) {
					CommonActivity.toast(activity, R.string.edtOTPIsdn_require);
					edtOTPIsdn.requestFocus();
				} else if (StringUtils.CheckCharSpecical(isdnOTP)) {
					CommonActivity.toast(activity, R.string.edtOTPIsdn_spec);
					edtOTPIsdn.requestFocus();
				} else if (isdnOTP.length() < 9) {
					CommonActivity.toast(activity, R.string.edtOTPIsdn_invalid);
					edtOTPIsdn.requestFocus();
				} else {
					String message = String.format(
							getString(R.string.sendOTP_confirm), isdnOTP);
					CommonActivity.createDialog(activity, message,
							getString(R.string.app_name),
							getString(R.string.say_ko),
							getString(R.string.say_co), null, confirmSendOTP)
							.show();
				}
			}
		});

		cusGroupId = customer.getCusGroupId();
		if ("1".equalsIgnoreCase(subType)) {
			// tra truoc
			// if (lstReasonPre != null && lstReasonPre.size() > 0) {
			// Utils.setDataSpinner(activity, lstReasonPre, spn_reason_fail);
			// } else {
			// AsyntaskGetReasonPre async = new AsyntaskGetReasonPre(activity,
			// "152", prbreason, spn_reason_fail);
			// async.execute();
			// }
		} else {
			// tra sau
			if (lstReason != null && lstReason.size() > 0) {
				Utils.setDataSpinner(activity, lstReason, spn_reason_fail);
			} else {
				AsyntaskGetReasonPos async = new AsyntaskGetReasonPos(activity,
						"04", prbreason, spn_reason_fail);
				async.execute();
			}
		}

		final LinearLayout lnngayhethan = (LinearLayout) dialogEditCustomer
				.findViewById(R.id.lnngayhethan);
		editngayhethan = (EditText) dialogEditCustomer
				.findViewById(R.id.edit_ngayhethan);
		editngayhethan.setText(customer.getIdExpireDate());

		edtloaikh = (EditText) dialogEditCustomer.findViewById(R.id.edtloaikh);
		edtloaikh.setText(customer.getCusType());
		edtloaikh.setEnabled(false);
		busType = customer.getCusType();

		edit_tenKH = (EditText) dialogEditCustomer
				.findViewById(R.id.edit_tenKH);
		edit_tenKH.setText(customer.getNameCustomer());
		edit_socmnd = (EditText) dialogEditCustomer
				.findViewById(R.id.edit_socmnd);
		edit_socmnd.setText(customer.getIdNo());
		edit_socmnd.setEnabled(false);
		edit_ngaycap = (EditText) dialogEditCustomer
				.findViewById(R.id.edit_ngaycap);
		edit_ngaycap.setText(customer.getIdIssueDate());
		if (customer.getIdIssueDate() != null) {
			try {
				issueDate = dbUpdateDateTime.parse(customer.getIdIssueDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		edit_noicap = (EditText) dialogEditCustomer
				.findViewById(R.id.edit_noicap);
		edit_noicap.setText(customer.getIdIssuePlace());
		edit_ngaysinh = (EditText) dialogEditCustomer
				.findViewById(R.id.edit_ngaysinh);
		edit_ngaysinh.setText(customer.getBirthdayCus());
		if (customer.getBirthdayCus() != null) {
			try {
				birthDateCus = dbUpdateDateTime
						.parse(customer.getBirthdayCus());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		initProvince();

		edtprovince = (EditText) dialogEditCustomer
				.findViewById(R.id.edtprovince);
		if (!CommonActivity.isNullOrEmpty(customer.getProvince())) {
			initDistrict(customer.getProvince());
			try {
				GetAreaDal dal = new GetAreaDal(activity);
				dal.open();
				edtprovince
						.setText(dal.getNameProvince(customer.getProvince()));
				province = customer.getProvince();
				dal.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		edtprovince.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,
						FragmentSearchLocation.class);
				intent.putExtra("arrProvincesKey", arrProvince);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "1");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 106);
			}
		});

		edtdistrict = (EditText) dialogEditCustomer
				.findViewById(R.id.edtdistrict);
		if (!CommonActivity.isNullOrEmpty(customer.getProvince())
				&& !CommonActivity.isNullOrEmpty(customer.getDistrict())) {
			initPrecinct(customer.getProvince(), customer.getDistrict());
			try {
				GetAreaDal dal = new GetAreaDal(activity);
				dal.open();
				edtdistrict.setText(dal.getNameDistrict(customer.getProvince(),
						customer.getDistrict()));
				district = customer.getDistrict();
				dal.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		edtdistrict.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,
						FragmentSearchLocation.class);
				intent.putExtra("arrDistrictKey", arrDistrict);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "2");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 107);
			}
		});

		edtprecinct = (EditText) dialogEditCustomer
				.findViewById(R.id.edtprecinct);
		if (!CommonActivity.isNullOrEmpty(customer.getPrecint())) {
			for (AreaBean areaBean : arrPrecinct) {
				if (customer.getPrecint().equalsIgnoreCase(
						areaBean.getPrecinct())) {
					edtprecinct.setText(areaBean.getNamePrecint());
					precinct = customer.getPrecint();
					break;
				}
			}
		}

		edtprecinct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,
						FragmentSearchLocation.class);
				intent.putExtra("arrPrecinctKey", arrPrecinct);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "3");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 108);
			}
		});

		edtStreetBlock = (EditText) dialogEditCustomer
				.findViewById(R.id.edtStreetBlock); // to
		edtStreetBlock.setText(customer.getStreet_block());

		edt_streetName = (EditText) dialogEditCustomer
				.findViewById(R.id.edt_streetName); // duong
		edt_streetName.setText(customer.getStreet());
		edtHomeNumber = (EditText) dialogEditCustomer
				.findViewById(R.id.edtHomeNumber); // so nha
		edtHomeNumber.setText(customer.getHome());

		edtStreetBlock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,
						FragmentSearchLocation.class);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "4");
				mBundle.putString("province", province);
				mBundle.putString("district", district);
				mBundle.putString("precinct", precinct);
				intent.putExtras(mBundle);
				startActivityForResult(intent, 109);
			}
		});

		prbStreetBlock = (ProgressBar) dialogEditCustomer
				.findViewById(R.id.prbStreetBlock);
		prbStreetBlock.setVisibility(View.GONE);
		btnRefreshStreetBlock = (Button) dialogEditCustomer
				.findViewById(R.id.btnRefreshStreetBlock);
		btnRefreshStreetBlock.setVisibility(View.VISIBLE);
		btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new CacheDatabaseManager(MainActivity.getInstance())
						.insertCacheStreetBlock(null,
								customer.getProvince() + customer.getDistrict()
										+ customer.getPrecint());
				GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
						activity, prbStreetBlock, customer.getStreet_block());
				async.execute(customer.getProvince() + customer.getDistrict()
						+ customer.getPrecint());
			}
		});
		if (!CommonActivity.isNullOrEmpty(customer.getProvince())
				&& !CommonActivity.isNullOrEmpty(customer.getDistrict())
				&& !CommonActivity.isNullOrEmpty(customer.getPrecint())
				&& !CommonActivity.isNullOrEmpty(customer.getStreet_block())) {
			streetBlock = customer.getStreet_block();
			GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
					activity, prbStreetBlock, customer.getStreet_block());
			async.execute(customer.getProvince() + customer.getDistrict()
					+ customer.getPrecint());
		}

		spinner_type_giay_to = (Spinner) dialogEditCustomer
				.findViewById(R.id.spinner_type_giay_to);
		spinner_sex = (Spinner) dialogEditCustomer
				.findViewById(R.id.spinner_gioitinh);
		ln_sex = (LinearLayout) dialogEditCustomer.findViewById(R.id.ln_sex);
		// bo sung giay phep kinh doanh cho khach hang doanh nghiep
		linearsoGPKD = (LinearLayout) dialogEditCustomer
				.findViewById(R.id.linearsoGPKD);
		linearCMT = (LinearLayout) dialogEditCustomer
				.findViewById(R.id.linearCMT);
		edit_soGQ = (EditText) dialogEditCustomer.findViewById(R.id.edit_soGQ);
		edit_soGQ.setText(customer.getBusPermitNo());
		edit_soGQ.setEnabled(false);
		ln_tin = (LinearLayout) dialogEditCustomer.findViewById(R.id.ln_tin);
		edt_tin = (EditText) dialogEditCustomer.findViewById(R.id.edt_tin);
		edt_tin.setText(customer.getTin());
		lnsogiayto = (LinearLayout) dialogEditCustomer
				.findViewById(R.id.lnsogiayto);

		if (customer.getBusPermitNo() == null
				|| customer.getBusPermitNo().isEmpty()) {
			// ca nhan
			linearCMT.setVisibility(View.VISIBLE);
			linearsoGPKD.setVisibility(View.GONE);
			lnsogiayto.setVisibility(View.VISIBLE);
			ln_tin.setVisibility(View.GONE);
			ln_sex.setVisibility(View.VISIBLE);
		} else {
			// doanh nghiep
			linearCMT.setVisibility(View.GONE);
			linearsoGPKD.setVisibility(View.VISIBLE);
			lnsogiayto.setVisibility(View.GONE);
			ln_tin.setVisibility(View.VISIBLE);
			ln_sex.setVisibility(View.GONE);
		}

		ArrayAdapter<String> adapterTypePaper = initTypePaper();
		spinner_type_giay_to.setAdapter(adapterTypePaper);
		if (!CommonActivity.isNullOrEmpty(customer.getIdType())) {
			for (int i = 0; i < arrTypePaperBeans.size(); i++) {
				TypePaperBeans typePaperBean = arrTypePaperBeans.get(i);
				if (customer.getIdType().equalsIgnoreCase(
						typePaperBean.getParType())) {
					Log.d(this.getClass().getSimpleName(), customer.getIdType()
							+ " == " + typePaperBean.getParType() + " i: " + i);
					spinner_type_giay_to.setSelection(i);
					spinner_type_giay_to.setSelected(true);
					idType = customer.getIdType();

					if ("3".equalsIgnoreCase(idType)) {
						lnngayhethan.setVisibility(View.VISIBLE);
					} else {
						lnngayhethan.setVisibility(View.GONE);
					}

					break;
				}
			}
		}
		spinner_type_giay_to.setEnabled(false);

		// spinner_type_giay_to.setOnItemSelectedListener(new
		// OnItemSelectedListener() {
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1, int
		// position, long id) {
		// typePaper = arrTypePaperBeans.get(position).getParType();
		// if (typePaper.equals("1")) {
		// edit_socmnd.setInputType(InputType.TYPE_CLASS_NUMBER);
		// } else {
		// if ("3".equalsIgnoreCase(typePaper)) {
		// lnngayhethan.setVisibility(View.VISIBLE);
		// } else {
		// lnngayhethan.setVisibility(View.GONE);
		// }
		// edit_socmnd.setInputType(InputType.TYPE_CLASS_TEXT);
		// }
		// }
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });

		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			arrSexBeans.clear();
		}
		initSex();
		if (!CommonActivity.isNullOrEmpty(customer.getSex())) {
			for (int i = 0; i < arrSexBeans.size(); i++) {
				SexBeans sexBean = arrSexBeans.get(i);
				if (customer.getSex().equalsIgnoreCase(sexBean.getValues())) {
					Log.d(this.getClass().getSimpleName(), customer.getSex()
							+ " == " + sexBean.getValues() + " i: " + i);
					spinner_sex.setSelection(i);
					spinner_sex.setSelected(true);
					break;
				}
			}
		}

		edit_ngaysinh.setOnClickListener(editTextListener);
		edit_ngaycap.setOnClickListener(editTextListener);
		editngayhethan.setOnClickListener(editTextListener);

		Button btnCancel = (Button) dialogEditCustomer
				.findViewById(R.id.btncancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialogEditCustomer.dismiss();
				// ActivityCreateNewRequestHotLine.instance.tHost.setCurrentTab(1);
				// setCurrentTabConnection(position);
			}
		});

		btnEdit = (Button) dialogEditCustomer.findViewById(R.id.btnEdit);
		btnEdit.setEnabled(true);
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustommerByIdNoBean obj = null;
				// Edit new infoCustomer
				if (validateCusGr()) {
					if ((edit_tenKH.getText().toString() != null && !edit_tenKH
							.getText().toString().isEmpty())
							&& (edit_socmnd.getText().toString() != null && !edit_socmnd
									.getText().toString().isEmpty())
							|| (edit_tenKH.getText().toString() != null && !edit_tenKH
									.getText().toString().isEmpty())
							&& (edit_soGQ.getText().toString() != null && !edit_soGQ
									.getText().toString().isEmpty())) {
						if (typePaper.equalsIgnoreCase("1")
								&& !custTypeIdMain.equals("1")) {
							if (edit_socmnd.getText().toString().length() == 9
									|| edit_socmnd.getText().toString()
											.length() == 12) {
								if (StringUtils.CheckCharSpecical(edit_tenKH
										.getText().toString())) {
									CommonActivity.toast(activity,
											R.string.checkcharspecical);
								} else {
									if ((edit_tenKH.getText().toString() != null && !edit_tenKH
											.getText().toString().isEmpty())
											&& (edit_socmnd.getText()
													.toString() != null && !edit_socmnd
													.getText().toString()
													.isEmpty())) {
										// validate ngay sinh nhat
										if (validateBirthCus()) {
											if (issueDate.after(dateNow)) {
												CommonActivity
														.toast(activity,
																R.string.ngaycapnhohonngayhientai);
											} else {
												if (DateTimeUtils.compareAge(
														issueDate, 15)) {
													CommonActivity
															.toast(activity,
																	R.string.checkDatengaycap);
												} else {
													// check noi cap
													if (issueDate
															.after(birthDateCus)
															|| issueDate
																	.equals(birthDateCus)) {
														if (!CommonActivity
																.isNullOrEmpty(edit_noicap
																		.getText()
																		.toString())) {
															obj = getCustomerFromInput(customer);
														} else {
															CommonActivity
																	.toast(activity,
																			R.string.checknoicap);
														}
													} else {
														CommonActivity
																.toast(activity,
																		R.string.nsnhongaycap);
													}
												}
											}
										} else {
											// (validateBirthCus())
											if (issueDate.after(dateNow)) {
												CommonActivity
														.toast(activity,
																R.string.ngaycapnhohonngayhientai);
											} else {
												if (issueDate
														.after(birthDateCus)
														|| issueDate
																.equals(birthDateCus)) {
													if (edit_noicap.getText()
															.toString() != null
															&& !edit_noicap
																	.getText()
																	.toString()
																	.isEmpty()) {
														if (!CommonActivity
																.isNullOrEmpty(edit_masothue
																		.getText()
																		.toString())) {
															obj = getCustomerFromInput(customer);
														} else {
															CommonActivity
																	.toast(activity,
																			R.string.checkmst);
														}
													} else {
														CommonActivity
																.toast(activity,
																		R.string.checknoicap);
													}
												} else {
													CommonActivity
															.toast(activity,
																	R.string.nsnhongaycap);
												}
											}
										}
									}
								}
							} else {
								CommonActivity.toast(activity,
										R.string.checkCMT);
							}
						} else {
							if (StringUtils.CheckCharSpecical(edit_tenKH
									.getText().toString()) == true) {
								CommonActivity.toast(activity,
										R.string.checkcharspecical);
							} else {
								if ((edit_tenKH.getText().toString() != null && !edit_tenKH
										.getText().toString().isEmpty())
										&& (edit_socmnd.getText().toString() != null && !edit_socmnd
												.getText().toString().isEmpty())) {
									if (issueDate.after(dateNow)) {
										CommonActivity
												.toast(activity,
														R.string.ngaycapnhohonngayhientai);
									} else {
										if (DateTimeUtils.compareAge(issueDate,
												15)) {
											CommonActivity.toast(activity,
													R.string.checkDatengaycap);
										} else {
											if (issueDate.after(birthDateCus)
													|| issueDate
															.equals(birthDateCus)) {
												if (edit_noicap.getText()
														.toString() != null
														&& !edit_noicap
																.getText()
																.toString()
																.isEmpty()) {
													obj = getCustomerFromInput(customer);
												} else {
													CommonActivity
															.toast(activity,
																	R.string.checknoicap);
												}
											} else {
												CommonActivity.toast(activity,
														R.string.nsnhongaycap);
											}
										}
									}
								} else {
									if (validateBirthCus()) {
										if (issueDate.after(dateNow)) {
											CommonActivity
													.toast(activity,
															R.string.ngaycapnhohonngayhientai);
										} else {
											if (issueDate.after(birthDateCus)
													|| issueDate
															.equals(birthDateCus)) {
												if (edit_noicap.getText()
														.toString() != null
														&& !edit_noicap
																.getText()
																.toString()
																.isEmpty()) {
													if (!CommonActivity
															.isNullOrEmpty(edit_masothue
																	.getText()
																	.toString())) {
														obj = getCustomerFromInput(customer);
													} else {
														CommonActivity
																.toast(activity,
																		R.string.checkmst);
													}
												} else {
													CommonActivity
															.toast(activity,
																	R.string.checknoicap);
												}
											} else {
												CommonActivity.toast(activity,
														R.string.nsnhongaycap);
											}
										}
									}
								}
							}
						}
					} else {
						CommonActivity.toast(activity,
								R.string.chua_nhap_ten_kh);
					}
				}
				if (obj != null) {
					Spin spin = (Spin) spn_reason_fail.getSelectedItem();
					reasonId = spin.getId();
					String isdn = edtOTPIsdn.getText().toString();
					if ("3".equalsIgnoreCase(idType)
							&& CommonActivity.isNullOrEmpty(editngayhethan
									.getText().toString())) {
						CommonActivity.toast(activity,
								R.string.editngayhethan_require);
					} else if (spn_reason_fail.getSelectedItemPosition() == 0) {
						CommonActivity.toast(activity,
								R.string.message_not_sellect_reasoon);
					} else if (obj.getRequestSendOTP() != null
							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
							&& CommonActivity.isNullOrEmpty(isdn)) {
						CommonActivity.toast(activity,
								R.string.edtOTPIsdn_require);
						edtOTPIsdn.requestFocus();
					} else if (obj.getRequestSendOTP() != null
							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
							&& StringUtils.CheckCharSpecical(isdn)) {
						CommonActivity
								.toast(activity, R.string.edtOTPIsdn_spec);
						edtOTPIsdn.requestFocus();
					} else if (obj.getRequestSendOTP() != null
							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
							&& CommonActivity.isNullOrEmpty(edtOTPCode
									.getText().toString())) {
						CommonActivity.toast(activity,
								R.string.edtOTPCode_require);
						edtOTPCode.requestFocus();
					} else if (obj.getRequestSendOTP() != null
							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
							&& StringUtils.CheckCharSpecical(edtOTPCode
									.getText().toString())) {
						CommonActivity
								.toast(activity, R.string.edtOTPCode_spec);
						edtOTPCode.requestFocus();
					} else {
						obj.setCustId(customer.getCustId());
						Log.d(Constant.TAG, "btnEdit " + obj);
						String message = getString(R.string.confirm_changeCustomerInfoPos);
						Log.d(Constant.TAG,
								"btnEdit Tra Sau" + obj.getCusType());

						custommerByIdNoBeanEdit = obj;
						otp = edtOTPCode.getText().toString();
						isdnSendOtp = edtOTPIsdn.getText().toString();

						CommonActivity.createDialog(activity, message,
								getString(R.string.app_name),
								getString(R.string.say_ko),
								getString(R.string.say_co), null,
								confirmChargeAct).show();
					}
				}
			}
		});

		dialogEditCustomer.show();
	}

	private OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				ChangeCustomerInfoPosAsyn async = new ChangeCustomerInfoPosAsyn(
						activity, custommerByIdNoBeanEdit);
				async.execute(reasonId, otp, isdnSendOtp);
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	private OnClickListener confirmSendOTP = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyntaskSendOTP async = new AsyntaskSendOTP(activity,
						prbreasonBtn, custId, edtOTPIsdn.getText().toString(),
						subType);
				async.execute();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	private CustommerByIdNoBean getCustomerFromInput(CustommerByIdNoBean other) {
		CustommerByIdNoBean obj = new CustommerByIdNoBean();
		if (other != null) {
			obj = other.clone();
		}
		// =========== them moi kh tra TRUOC =========
		String nameCustomer = edit_tenKH.getText().toString();
		Log.d("nameCustomer", nameCustomer);
		String socmt = edit_socmnd.getText().toString();
		Log.d("socmt", socmt);
		String soGPKQ = edit_soGQ.getText().toString();
		Log.d("soGPKQ", soGPKQ);
		String birthDate = edit_ngaysinh.getText().toString();
		Log.d("birthDate", birthDate);
		String ngaycap = edit_ngaycap.getText().toString();
		Log.d("ngaycap", ngaycap);
		String noicap = edit_noicap.getText().toString();
		Log.d("noicap", noicap);
		String tin = edt_tin.getText().toString();
		Log.d("tin", tin);
		SexBeans sexBean = arrSexBeans.get(spinner_sex
				.getSelectedItemPosition());
		Log.d("sex", sexBean.getValues());

		TypePaperBeans typePaperBean = arrTypePaperBeans
				.get(spinner_type_giay_to.getSelectedItemPosition());
		Log.d("idType", typePaperBean.getParType());

		obj.setTin(tin);
		obj.setNameCustomer(nameCustomer);
		obj.setBirthdayCus(birthDate);
		obj.setIdNo(socmt);
		obj.setBusPermitNo(soGPKQ);
		obj.setProvince(province);
		obj.setDistrict(district);
		obj.setPrecint(precinct);
		obj.setIdIssuePlace(noicap);
		obj.setIdIssueDate(ngaycap);
		obj.setIdType(typePaperBean.getParType());

		obj.setStreet_block(streetBlock);
		obj.setStreet(edt_streetName.getText().toString().trim());
		obj.setHome(edtHomeNumber.getText().toString().trim());
		obj.setAreaCode(province + district + precinct + streetBlock);

		obj.setSex(sexBean.getValues());
		obj.setStrIdExpire(editngayhethan.getText().toString());
		try {
			GetAreaDal dal = new GetAreaDal(activity);
			dal.open();
			String fulladdress = "";
			if (!CommonActivity.isNullOrEmpty(edtHomeNumber.getText()
					.toString())
					&& !CommonActivity.isNullOrEmpty(edtHomeNumber.getText()
							.toString())) {
				fulladdress = edtHomeNumber.getText().toString().trim() + " "
						+ edt_streetName.getText().toString().trim() + " "
						+ edtStreetBlock.getText() + " ";
			}
			fulladdress = fulladdress
					+ dal.getfulladddress(province + district + precinct);
			obj.setAddreseCus(fulladdress);
			dal.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d(this.getClass().getSimpleName(), " getCustomerFromInput " + obj);

		return obj;
	}

	private void removeCus() {
		if (cusId != null && !cusId.isEmpty()) {
			cusId = "";
		}
		if (account != null && !account.isEmpty()) {
			account = "";
		}
		// if(idcusGroup != null && !idcusGroup.isEmpty()){
		// idcusGroup = "";
		// }
		// if(idcusType != null && !idcusType.isEmpty()){
		// idcusType = "";
		// }
		if (custommer != null) {
			custommer = null;
		}
	}

	// bo sung khach hang doanh nghiep
	// Dialog dialogInsertCusAtt ;
	// Spinner spinner_loaigiayto ;
	EditText edit_tendaidien, edit_sogiaytodd, edit_ngaysinh, edit_ngaycapdd,
			edtnoicapdd;
	private ArrayList<TypePaperBeans> arrTypePaperBeansAtt = new ArrayList<TypePaperBeans>();

	// init typepaper
	private void initTypePaperAtt() {
		GetTypePaperDal dal = null;
		try {
			dal = new GetTypePaperDal(getActivity());
			dal.open();
			arrTypePaperBeansAtt = dal.getLisTypepaper();
			dal.close();
			if (arrTypePaperBeansAtt != null && !arrTypePaperBeansAtt.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						android.R.id.text1);
				for (TypePaperBeans typePaperBeans : arrTypePaperBeansAtt) {
					adapter.add(typePaperBeans.getParValues());
				}
				spinner_loaigiaytoAtt.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		} finally {
			if (dal != null) {
				dal.close();
			}
		}
	}

	private boolean validateCusGr() {
		if (CommonActivity.isNullOrEmpty(cusGroupId)) {
			Toast.makeText(getActivity(), getString(R.string.confirmnhomkh),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(busType)) {
			Toast.makeText(getActivity(), getString(R.string.confirmloaikh),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edtprovince.getText().toString())) {
			Toast.makeText(getActivity(), getString(R.string.provinceEmpty),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edtdistrict.getText().toString())) {
			Toast.makeText(getActivity(), getString(R.string.districtEmpty),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edtprecinct.getText().toString())) {
			Toast.makeText(getActivity(), getString(R.string.precinctEmpty),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edtStreetBlock.getText().toString())) {
			Toast.makeText(getActivity(), getString(R.string.streetBlockEmpty),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (StringUtils.CheckCharSpecical(edt_streetName.getText().toString())) {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.checkcharspecical),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (StringUtils.CheckCharSpecical(edtHomeNumber.getText().toString())) {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.checkcharspecical),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	Spinner spinner_loaigiaytoAtt;
	String typePaperAtt;

	private void showInsertAttribute() {
		final Dialog dialogInsertCusAtt = new Dialog(getActivity());
		dialogInsertCusAtt.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogInsertCusAtt
				.setContentView(R.layout.connection_layout_insert_info_parent_cus);
		dialogInsertCusAtt.setCancelable(false);

		spinner_loaigiaytoAtt = (Spinner) dialogInsertCusAtt
				.findViewById(R.id.spinner_loaigiaytodd);
		edit_tendaidien = (EditText) dialogInsertCusAtt
				.findViewById(R.id.edit_tendaidien);
		edit_sogiaytodd = (EditText) dialogInsertCusAtt
				.findViewById(R.id.edit_sogiaytodd);
		edit_ngaysinh = (EditText) dialogInsertCusAtt
				.findViewById(R.id.edit_ngaysinh);
		edit_ngaysinh.setText(dateNowString);
		edit_ngaysinh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerBirth(edit_ngaysinh);
			}
		});

		edit_ngaycapdd = (EditText) dialogInsertCusAtt
				.findViewById(R.id.edit_ngaycap);
		edit_ngaycapdd.setText(dateNowString);
		edit_ngaycapdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerIssueATT(edit_ngaycapdd);
			}
		});
		edtnoicapdd = (EditText) dialogInsertCusAtt
				.findViewById(R.id.edtnoicap);
		Button btnthem = (Button) dialogInsertCusAtt
				.findViewById(R.id.btnthemmoi);
		Button btncancel = (Button) dialogInsertCusAtt
				.findViewById(R.id.btncancel);

		initTypePaperAtt();
		spinner_loaigiaytoAtt
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						typePaperAtt = arrTypePaperBeansAtt.get(arg2)
								.getParType();

						// if (typePaperAtt.equals("1")) {
						// edit_sogiaytodd
						// .setInputType(InputType.TYPE_CLASS_NUMBER);
						// } else {
						// edit_sogiaytodd
						// .setInputType(InputType.TYPE_CLASS_TEXT);
						// }

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		btnthem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (validateUpdate()) {
					custommer.getCustomerAttribute().setBirthDate(
							edit_ngaysinh.getText().toString().trim());
					custommer.getCustomerAttribute().setIdType(typePaperAtt);
					custommer.getCustomerAttribute().setName(
							edit_tendaidien.getText().toString().trim());
					custommer.getCustomerAttribute().setIdNo(
							edit_sogiaytodd.getText().toString().trim());
					custommer.getCustomerAttribute().setBirthDate(
							edit_ngaysinh.getText().toString().trim());
					custommer.getCustomerAttribute().setIssueDate(
							edit_ngaycapdd.getText().toString().trim());
					custommer.getCustomerAttribute().setIssuePlace(
							edtnoicapdd.getText().toString().trim());
					dialogInsertCusAtt.dismiss();
					ActivityCreateNewRequestHotLine.instance.tHost
							.setCurrentTab(1);
				}

			}
		});
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogInsertCusAtt.dismiss();

			}
		});
		dialogInsertCusAtt.show();

	}

	private void showDatePickerIssueATT(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// dayATT = dayOfMonth;
				// monthATT = monthOfYear + 1;
				// yearATT = year;
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year;
				edtngaycap.setText(mNgaycaidat);

				try {

					issueDateATT = dbUpdateDateTime.parse(mNgaycaidat);
				} catch (Exception e) {
					e.printStackTrace();
				}

				calATT.set(year, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback,
				yearATT, monthATT, dayATT);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();

	}

	private void showDatePickerBirth(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);

				try {

					birthDateATT = dbUpdateDateTime.parse(mNgaycaidat);
				} catch (Exception e) {
					e.printStackTrace();
				}

				calBirth.set(year1, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback,
				yearBirth, monthBirth, dayBirth);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	private void showDatePickerBirthCus(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				// dayBirthCus = dayOfMonth;
				// monthBirthCus = monthOfYear + 1;
				// yearBirthCus = year1;
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);

				try {

					birthDateCus = dbUpdateDateTime.parse(mNgaycaidat);
				} catch (Exception e) {
					e.printStackTrace();
				}

				calBirthCus.set(year1, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback,
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
		} else {

		}
		pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback, yearBirthCus,
				monthBirthCus, dayBirthCus);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();

	}

	// thinhhq1 bo sung ngay sinh nhat
	private boolean validateBirthCus() {
		if (CommonActivity.isNullOrEmpty(edit_ngaysinhCus.getText().toString())) {

			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.ngaysinhnotempty),
					getString(R.string.app_name)).show();
			return false;
		}
		if (birthDateCus.after(dateNow)) {

			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.nsnhohonhtai),
					getString(R.string.app_name)).show();
			return false;
		}
		if (issueDate != null) {
			if (birthDateCus.after(issueDate)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.nsnhongaycap),
						getString(R.string.app_name)).show();
				return false;
			}
		}

		return true;
	}

	// validate them moi thong tin dai dien khach hang
	private boolean validateUpdate() {

		if (CommonActivity.isNullOrEmpty(edit_tendaidien.getText().toString()
				.trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checktendd),
					getString(R.string.app_name)).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edit_sogiaytodd.getText().toString()
				.trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checksogiaytodd),
					getString(R.string.app_name)).show();
			return false;
		} else {
			if (typePaperAtt.equals("1")) {
				if (edit_sogiaytodd.getText().toString().trim().length() == 9
						|| edit_sogiaytodd.getText().toString().trim().length() == 12) {

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkCMT),
							getString(R.string.app_name)).show();
					return false;
				}
			}
		}

		if (birthDateATT == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkbirthempty),
					getString(R.string.app_name)).show();
			return false;
		}

		if (birthDateATT.after(dateNow)) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkbirthdd),
					getString(R.string.app_name)).show();
			return false;
		}
		if (!DateTimeUtils.compareAge(birthDateATT, Constant.TEEN)) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.check14), getString(R.string.app_name))
					.show();
			return false;
		}

		if (issueDateATT == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkissueempty),
					getString(R.string.app_name)).show();
			return false;
		} else {
			if (issueDateATT.after(dateNow)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.ngaycapnhohonngayhientai),
						getString(R.string.app_name)).show();
				return false;
			} else {
				if (issueDateATT.before(birthDateATT)) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkngaycapvsns),
							getString(R.string.app_name)).show();
					return false;
				} else {
					if (typePaperAtt.equalsIgnoreCase("1")
							&& DateTimeUtils.compareAge(issueDateATT, 15)) {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkDatengaycap),
								getString(R.string.app_name)).show();
						return false;
					}
				}
			}
		}

		if (CommonActivity.isNullOrEmpty(edtnoicapdd.getText().toString()
				.trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checknoicap),
					getString(R.string.app_name)).show();
			return false;
		}
		return true;
	}

	private void initProvince() {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrProvince = dal.getLstProvince();
			dal.close();

			// if (arrProvince != null && arrProvince.size() > 0) {
			// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			// getActivity(),
			// android.R.layout.simple_dropdown_item_1line,
			// android.R.id.text1);
			// for (AreaBean areaBean : arrProvince) {
			// adapter.add(areaBean.getNameProvince());
			// }
			// spinner_province.setAdapter(adapter);

			// TODO vÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­ dÃƒÆ’Ã‚Â¡Ãƒâ€šÃ‚Â»Ãƒâ€šÃ‚Â¥ add spinner
			// for (AreaBean areaBean : arrProvince) {
			// if(areaBean.getNameProvince().equalsIgnoreCase("HÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  NÃƒÆ’Ã‚Â¡Ãƒâ€šÃ‚Â»ÃƒÂ¢Ã¢â‚¬Å¾Ã‚Â¢i")){
			// spinner_province.setSelection(arrProvince.indexOf(areaBean));
			// }
			// }

			// }
		} catch (Exception ex) {
			Log.e("initProvince", ex.toString());
		}
	}

	// lay huyen/quan theo tinh
	private void initDistrict(String province) {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrDistrict = dal.getLstDistrict(province);
			dal.close();

			// if (arrDistrict != null && arrDistrict.size() > 0) {
			// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			// getActivity(),
			// android.R.layout.simple_dropdown_item_1line,
			// android.R.id.text1);
			// for (AreaBean areaBean : arrDistrict) {
			// adapter.add(areaBean.getNameDistrict());
			// }
			// spinner_district.setAdapter(adapter);
			// }
		} catch (Exception ex) {
			Log.e("initDistrict", ex.toString());
		}
	}

	// lay phuong/xa theo tinh,qhuyen
	private void initPrecinct(String province, String district) {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrPrecinct = dal.getLstPrecinct(province, district);
			dal.close();

			// if (arrPrecinct != null && arrPrecinct.size() > 0) {
			// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			// getActivity(),
			// android.R.layout.simple_dropdown_item_1line,
			// android.R.id.text1);
			// for (AreaBean areaBean : arrPrecinct) {
			// adapter.add(areaBean.getNamePrecint());
			// }
			// spinner_precint.setAdapter(adapter);
			// }
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
		}
	}

	// init gioi tinh
	private void initSex() {
		arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
		arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_dropdown_item_1line,
					android.R.id.text1);
			for (SexBeans sexBeans : arrSexBeans) {
				adapter.add(sexBeans.getName());
			}
			spinner_sex.setAdapter(adapter);
		}
	}

	private View.OnClickListener editTextListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			EditText edt = (EditText) view;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt.getText()
						.toString(), "dd/MM/yyyy");

				cal.setTime(date);

			}
			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT,
					datePickerListener, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			pic.getDatePicker().setTag(view);
			pic.show();
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
						+ selectedYear);

				int id = editText.getId();

				Calendar cal = Calendar.getInstance();
				cal.set(selectedYear, selectedMonth, selectedDay);
				Date date = cal.getTime();

				if (edit_ngaycap != null && id == edit_ngaycap.getId()) {
					issueDate = date;
				} else if (edit_ngaycapdd != null
						&& editText.getId() == edit_ngaycapdd.getId()) {
					issueDateATT = date;
				} else if (edit_ngaysinhCus != null
						&& editText.getId() == edit_ngaysinhCus.getId()) {
					birthDateATT = date;
				} else if (edit_ngaysinh != null
						&& editText.getId() == edit_ngaysinh.getId()) {
					birthDateCus = date;
				} else if (editngayhethan != null
						&& editText.getId() == editngayhethan.getId()) {
					// ngay het han
				}
			}
		}
	};

	private class ChangeCustomerInfoPosAsyn extends
			AsyncTask<String, String, String> {

		private ProgressDialog progress;
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private CustommerByIdNoBean obj;

		public ChangeCustomerInfoPosAsyn(Context context,
				CustommerByIdNoBean other) {
			this.context = context;
			obj = other;

			Log.d(Constant.TAG, "ChangeCustomerInfoPosAsyn " + obj);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.changeCustomerInfoPos));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return changeCustomerInfoPos(obj, params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (description == null || description.isEmpty()) {
					description = context
							.getString(R.string.success_changeCustomerInfoPos);
				}
				Dialog dialog = CommonActivity.createAlertDialog(activity,
						description, getResources()
								.getString(R.string.app_name));
				dialog.show();

				if (btnEdit != null && btnEdit.isEnabled()) {
					btnEdit.setEnabled(false);
				}

				// if(dialogEditCustomer != null &&
				// dialogEditCustomer.isShowing()) {
				// dialogEditCustomer.dismiss();
				// }
				// setCurrentTabConnection(position);
				// ActivityCreateNewRequestHotLine.instance.tHost.setCurrentTab(1);
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
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private String changeCustomerInfoPos(
				CustommerByIdNoBean custommerByIdNoBean, String reasonId,
				String otp, String isdnSendOtp) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changeCustomerInfoPos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changeCustomerInfoPos>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				HashMap<String, String> param = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<smartphoneInputBOCM>");

				if (!CommonActivity.isNullOrEmpty(otp)) {
					rawData.append("<otp>" + otp + "</otp>");
				}
				if (!CommonActivity.isNullOrEmpty(isdnSendOtp)) {
					rawData.append("<isdnSendOtp>" + isdnSendOtp
							+ "</isdnSendOtp>");
				}
				if (!CommonActivity.isNullOrEmpty(reasonId)) {
					rawData.append("<reasonId>" + reasonId + "</reasonId>");
				}

				// ========customer ===============
				rawData.append("<customer>");
				// TuanTD7
				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getCustId())) {
					rawData.append("<custId>" + custommerByIdNoBean.getCustId());
					rawData.append("</custId>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getCusType())) {
					rawData.append("<busType>"
							+ custommerByIdNoBean.getCusType());
					rawData.append("</busType>");
				}

				if (custommerByIdNoBean.getIdType() != null
						&& !custommerByIdNoBean.getIdType().isEmpty()
						&& !(custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
								.getBusPermitNo().isEmpty())) {
					rawData.append("<idType>" + custommerByIdNoBean.getIdType());
					rawData.append("</idType>");
				}

				String busPermitNo = custommerByIdNoBean.getBusPermitNo();
				Log.d(Constant.TAG, "FragmentConnectionMobile busPermitNo: "
						+ busPermitNo);
				if (!CommonActivity.isNullOrEmpty(busPermitNo)) {
					rawData.append("<busPermitNo>" + busPermitNo);
					rawData.append("</busPermitNo>");
				} else if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getIdNo())) {
					rawData.append("<idNo>" + custommerByIdNoBean.getIdNo());
					rawData.append("</idNo>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getTin())) {
					rawData.append("<tin>" + custommerByIdNoBean.getTin());
					rawData.append("</tin>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getSex())) {
					rawData.append("<sex>" + custommerByIdNoBean.getSex());
					rawData.append("</sex>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getNameCustomer())) {
					rawData.append("<name>"
							+ custommerByIdNoBean.getNameCustomer());
					rawData.append("</name>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getAddreseCus())) {
					rawData.append("<address>"
							+ custommerByIdNoBean.getAddreseCus());
					rawData.append("</address>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getAreaCode())) {
					rawData.append("<areaCode>"
							+ custommerByIdNoBean.getAreaCode());
					rawData.append("</areaCode>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getBirthdayCus())) {
					rawData.append("<strBirthDate>"
							+ custommerByIdNoBean.getBirthdayCus());
					rawData.append("</strBirthDate>");
					rawData.append("<birthDateStr>"
							+ custommerByIdNoBean.getBirthdayCus());
					rawData.append("</birthDateStr>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getStrIdExpire())) {
					rawData.append("<strIdExpireDate>"
							+ custommerByIdNoBean.getStrIdExpire());
					rawData.append("</strIdExpireDate>");
					rawData.append("<idExpireDateStr>"
							+ custommerByIdNoBean.getStrIdExpire());
					rawData.append("</idExpireDateStr>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getProvince())) {
					rawData.append("<province>"
							+ custommerByIdNoBean.getProvince());
					rawData.append("</province>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getDistrict())) {
					rawData.append("<district>"
							+ custommerByIdNoBean.getDistrict());
					rawData.append("</district>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getPrecint())) {
					rawData.append("<precinct>"
							+ custommerByIdNoBean.getPrecint());
					rawData.append("</precinct>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getStreet_block())) {
					rawData.append("<streetBlock>"
							+ custommerByIdNoBean.getStreet_block());
					rawData.append("</streetBlock>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getStreet())) {
					rawData.append("<streetName>"
							+ custommerByIdNoBean.getStreet());
					rawData.append("</streetName>");
				}

				if (!CommonActivity
						.isNullOrEmpty(custommerByIdNoBean.getHome())) {
					rawData.append("<home>" + custommerByIdNoBean.getHome());
					rawData.append("</home>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getAreaCode())) {
					rawData.append("<areaCode>"
							+ custommerByIdNoBean.getAreaCode());
					rawData.append("</areaCode>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getIdIssueDate())) {
					rawData.append("<idIssueDateStr>"
							+ custommerByIdNoBean.getIdIssueDate());
					rawData.append("</idIssueDateStr>");
					// rawData.append("<IdIssueDateStr>" +
					// custommerByIdNoBean.getIdIssueDate());
					// rawData.append("</IdIssueDateStr>");
					rawData.append("<strIdIssueDate>"
							+ custommerByIdNoBean.getIdIssueDate());
					rawData.append("</strIdIssueDate>");
				}

				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
						.getIdIssuePlace())) {
					rawData.append("<idIssuePlace>"
							+ custommerByIdNoBean.getIdIssuePlace());
					rawData.append("</idIssuePlace>");
				}

				rawData.append("</customer>");

				rawData.append("</smartphoneInputBOCM>");
				rawData.append("</input>");
				rawData.append("</ws:changeCustomerInfoPos>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_changeCustomerInfoPos");
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
				Log.e("Exception", e.toString(), e);
			}
			return errorCode;
		}
	}

	private class AsyntaskGetReasonPos extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		private ProgressBar prbreason;
		private Spinner spn_reason_fail;

		private String actionCode;

		public AsyntaskGetReasonPos(Context context, String actionCode,
				ProgressBar prbreason, Spinner spn_reason_fail) {
			this.context = context;
			this.actionCode = actionCode;
			this.prbreason = prbreason;
			this.spn_reason_fail = spn_reason_fail;

			Log.d(Constant.TAG, "AsyntaskGetReasonPos " + actionCode);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (this.prbreason != null) {
				this.prbreason.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getListReasonByTelServicePos();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			if (this.prbreason != null) {
				this.prbreason.setVisibility(View.GONE);
			}
			lstReason.clear();
			if (errorCode.equalsIgnoreCase("0")) {
				lstReason.add(new Spin("-1",
						getString(R.string.txt_select_reason)));
				lstReason.addAll(result);
				Utils.setDataSpinner(activity, lstReason, spn_reason_fail);
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
					Dialog dialog = CommonActivity
							.createAlertDialog(activity, description, context
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<Spin> getListReasonByTelServicePos() {
			// Spin serviceItem = (Spin) spnService.getSelectedItem();
			ArrayList<Spin> lstReasonPos = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonByTelServicePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelServicePos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<actionCode>" + actionCode + "</actionCode>");
				rawData.append("<serviceType></serviceType>");
				// rawData.append("<serviceType>" + serviceType +
				// "</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelServicePos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_getListReasonByTelServicePos");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
				// parser
				lstReasonPos = parserListGroupPos(original);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReasonPos;
		}

		public ArrayList<Spin> parserListGroupPos(String original) {
			ArrayList<Spin> lstReasonPos = new ArrayList<Spin>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstReasonPos");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "codeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e1, "reasonId"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstReasonPos.add(spin);
				}
			}
			return lstReasonPos;
		}

	}

	private class AsyntaskSendOTP extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		private ProgressBar prbreason;

		private String custId;
		private String isdn;
		private String type;

		/**
		 * custId: mÃ£ khÃ¡ch hÃ ng tÃ¬m kiáº¿m Ä‘Æ°á»£c
		 * typeSystem=SYSTEM_SMARTPHONE isdn=Sá»‘ thuÃª bao gá»­i OTP (Sá»‘
		 * thuÃª bao náº±m trong danh sÃ¡ch sá»‘ thuÃª bao cá»§a khÃ¡ch hÃ ng
		 * cÃ³ thá»ƒ nháº­n tin nháº¯n) type=1 tráº£ trÆ°á»›c type=2 tráº£ sau
		 */

		public AsyntaskSendOTP(Context context, ProgressBar prbreason,
				String custId, String isdn, String type) {
			this.context = context;
			this.prbreason = prbreason;
			this.custId = custId;
			this.isdn = isdn;
			this.type = type;

			Log.d(Constant.TAG, "AsyntaskSendOTP custId " + custId + " isdn "
					+ isdn + " type " + type);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (this.prbreason != null) {
				this.prbreason.setVisibility(View.VISIBLE);
			}
			btnSendOTP.setEnabled(false);
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return sendOTP();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			if (this.prbreason != null) {
				this.prbreason.setVisibility(View.GONE);
			}
			if (errorCode.equalsIgnoreCase("0")) {
				if (description == null || description.isEmpty()) {
					description = context.getString(R.string.sendOTP_success);
				}
				CommonActivity.toast(activity, description);
			} else {
				btnSendOTP.setEnabled(true);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(activity, description, context
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<Spin> sendOTP() {
			// Spin serviceItem = (Spin) spnService.getSelectedItem();
			ArrayList<Spin> lstReasonPos = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_sendOTP");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:sendOTP>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");

				rawData.append("<paramCustomerInfoBO>");

				rawData.append("<custId>" + custId + "</custId>");
				rawData.append("<typeSystem>SYSTEM_SMARTPHONE</typeSystem>");
				rawData.append("<isdn>" + isdn + "</isdn>");
				rawData.append("<type>" + type + "</type>");

				rawData.append("</paramCustomerInfoBO>");

				rawData.append("</input>");
				rawData.append("</ws:sendOTP>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity, "mbccs_sendOTP");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
				// parser
				lstReasonPos = parserListGroupPos(original);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReasonPos;
		}

		public ArrayList<Spin> parserListGroupPos(String original) {
			ArrayList<Spin> lstReasonPos = new ArrayList<Spin>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
			}
			return lstReasonPos;
		}

	}

	private class GetListGroupAdressAsyncTask extends
			AsyncTask<String, Void, ArrayList<AreaObj>> {
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressBar progessBar = null;
		private String streetBlock;

		public GetListGroupAdressAsyncTask(Context context,
				ProgressBar progessBar, String streetBlock) {
			this.context = context;
			this.progessBar = progessBar;
			this.streetBlock = streetBlock;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (progessBar != null) {
				progessBar.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {
			return getListGroupAddress(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {
			super.onPostExecute(result);
			if (progessBar != null) {
				progessBar.setVisibility(View.GONE);
			}
			if (btnRefreshStreetBlock != null) {
				btnRefreshStreetBlock.setVisibility(View.VISIBLE);
			}
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					for (AreaObj areaObj : result) {
						if (streetBlock.equalsIgnoreCase(areaObj.getAreaCode())) {
							edtStreetBlock.setText(areaObj.getName());
							break;
						}
					}
				} else {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.notStreetBlock),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(activity, description, context
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
			ArrayList<AreaObj> listGroupAdress = null;
			String original = "";
			listGroupAdress = new CacheDatabaseManager(
					MainActivity.getInstance())
					.getListCacheStreetBlock(parentCode);
			if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
				errorCode = "0";
				return listGroupAdress;
			}
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListArea");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListArea>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<parentCode>" + parentCode + "</parentCode>");
				rawData.append("</input>");
				rawData.append("</ws:getListArea>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity, "mbccs_getListArea");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				listGroupAdress = parserListGroup(original);
				listGroupAdress = parserListGroup(original);
				if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
					new CacheDatabaseManager(MainActivity.getInstance())
							.insertCacheStreetBlock(listGroupAdress, parentCode);
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return listGroupAdress;
		}

		public ArrayList<AreaObj> parserListGroup(String original) {
			ArrayList<AreaObj> listGroupAdress = new ArrayList<AreaObj>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstArea");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					AreaObj areaObject = new AreaObj();
					areaObject.setName(parse.getValue(e1, "name"));
					Log.d("name area: ", areaObject.getName());
					areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
					listGroupAdress.add(areaObject);
				}
			}
			return listGroupAdress;
		}
	}

}
