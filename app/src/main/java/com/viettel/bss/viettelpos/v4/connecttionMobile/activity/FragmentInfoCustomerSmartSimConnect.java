package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.BusTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.BusTypePreBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TypePaperBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetTypePaperDal;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FragmentInfoCustomerSmartSimConnect extends Fragment implements
		OnClickListener {

    private Spinner spinner_type_giay_to;
	private EditText edit_tenKH;
	private EditText edit_socmnd;
	private EditText edit_ngaycap;
	private EditText editngayhethan;
	private EditText edit_noicap;
	private EditText edit_ngaysinh;
	// private Spinner spinner_loaikh;
	private Spinner spinner_province;
	private Spinner spinner_district;
	private Spinner spinner_precint;
	private Spinner spinner_sex;

	private String dateBirth = "";
	private String dateIdNo = "";
	private int dateBirthYear;
	private int dateBirthMonth;
	private int dateBirthDay;
	private int dateIdNoYear;
	private int dateIdNoMonth;
	private int dateIdNoDay;
	private Date dateBirthDate = null;
	private Date dateIdNoDate = null;
	private String dateBirthString = "";
	private String dateIdNoString = "";

	private int dateHethanYear;
	private int dateHethanMonth;
	private int dateHethanDay;
	private Date dateHethanDate = null;
	private String dateHethan = "";
	private String dateHethanString = "";

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String busType = "";

	private String subType = "";

	public CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();

	// ========ds loai kh tra truoc ==================
	private ArrayList<BusTypeBean> arrBusTypeBeans = new ArrayList<>();

	// =======ds loai kh tra sau ================
	private ArrayList<BusTypePreBean> arrBusTypePreBeans = new ArrayList<>();

	// arrlist province
	private ArrayList<AreaBean> arrProvince = new ArrayList<>();
	private String province = "";
	// arrlist district
	private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
	private String district = "";
	// arrlist precinct
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
	private String precinct = "";

	private ArrayList<TypePaperBeans> arrTypePaperBeans = new ArrayList<>();
	private String idType = "";

	private final ArrayList<SexBeans> arrSexBeans = new ArrayList<>();
	private String sex = "";

	private Staff staff = null;

	private View view = null;

	private EditText edtloaikh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// LAY DU LIEU BUNDLE
		getdataBundle();
		if (view == null) {
			view = inflater.inflate(
					R.layout.connectionmobile_layout_insert_smartsim, container,
					false);
			unitView(view);
		} else {

		}

		return view;
	}

	private void getdataBundle() {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			staff = (Staff) mBundle.getSerializable("staffKey");
			subType = mBundle.getString("subTypeKey", "");
		}
	}

	private void unitView(View view) {

		
		Button btnRefreshCustType = (Button) view.findViewById(R.id.btnRefreshCustType);
		btnRefreshCustType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if("1".equals(subType)){
					
					new CacheDatabaseManager(getActivity())
					.insertBusTypePre(null);
					
					GetListTypeCusPreAsyn getListTypeCusPreAsyn = new GetListTypeCusPreAsyn(getActivity());
					getListTypeCusPreAsyn.execute();
					
				}else{
					
					new CacheDatabaseManager(getActivity())
					.insertBusType(null);
					
					GetListTypeCusAsyn getListTypeCusAsyn = new GetListTypeCusAsyn(getActivity());
					getListTypeCusAsyn.execute();
					
				}
			}
		});
		
		final LinearLayout lnngayhethan = (LinearLayout) view
				.findViewById(R.id.lnngayhethan);
		LinearLayout lntitle = (LinearLayout) view.findViewById(R.id.lntitle);
		lntitle.setVisibility(View.GONE);

		Button btncancel = (Button) view.findViewById(R.id.btncancel);
		btncancel.setVisibility(View.GONE);

		editngayhethan = (EditText) view.findViewById(R.id.edit_ngayhethan);
		// spinner_loaikh = (Spinner) view.findViewById(R.id.spinner_loaikh);

		edtloaikh = (EditText) view.findViewById(R.id.edtloaikh);
		edtloaikh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("1".equals(subType)) {
					if (arrBusTypePreBeans != null
							&& !arrBusTypePreBeans.isEmpty()) {
						Intent intent = new Intent(getActivity(),
								FragmentSearchBusTypeMobile.class);
						intent.putExtra("arrBusTypePreBeansKey",
								arrBusTypePreBeans);
						Bundle mBundle = new Bundle();
						mBundle.putString("checkSubKey", "11");
						intent.putExtras(mBundle);
						startActivityForResult(intent, 104);
					}
				} else {
					if (arrBusTypeBeans != null && !arrBusTypeBeans.isEmpty()) {
						Intent intent = new Intent(getActivity(),
								FragmentSearchBusTypeMobile.class);
						intent.putExtra("arrBusTypeBeansKey", arrBusTypeBeans);
						startActivityForResult(intent, 105);
					}
				}
			}
		});

		edit_tenKH = (EditText) view.findViewById(R.id.edit_tenKH);

		edit_tenKH.setText(staff.getName());

		edit_socmnd = (EditText) view.findViewById(R.id.edit_socmnd);
		edit_socmnd.setText(staff.getId_no());
		edit_socmnd.setEnabled(false);

		edit_ngaycap = (EditText) view.findViewById(R.id.edit_ngaycap);
		edit_ngaycap.setText(StringUtils.convertDate(staff.getId_issue_date()));

		edit_noicap = (EditText) view.findViewById(R.id.edit_noicap);
		edit_noicap.setText(staff.getId_issue_place());

		edit_ngaysinh = (EditText) view.findViewById(R.id.edit_ngaysinh);
		edit_ngaysinh.setText(StringUtils.convertDate(staff.getBirthday()));

		spinner_province = (Spinner) view.findViewById(R.id.spinner_province);
		spinner_province.setClickable(false);
		spinner_district = (Spinner) view.findViewById(R.id.spinner_district);
		spinner_district.setClickable(false);
		spinner_precint = (Spinner) view.findViewById(R.id.spinner_precint);
		spinner_precint.setClickable(false);
		spinner_type_giay_to = (Spinner) view
				.findViewById(R.id.spinner_type_giay_to);
		spinner_sex = (Spinner) view.findViewById(R.id.spinner_gioitinh);

        Button btnthemmoi = (Button) view.findViewById(R.id.btnthemmoi);
		btnthemmoi.setOnClickListener(this);

		// show pop up cho kh them moi
		if (arrBusTypeBeans != null && arrBusTypeBeans.size() > 0) {
			arrBusTypeBeans.clear();
		}
		if (arrBusTypePreBeans != null && arrBusTypePreBeans.size() > 0) {
			arrBusTypePreBeans.clear();
		}
		if ("1".equals(subType)) {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				GetListTypeCusPreAsyn getListTypeCusPreAsyn = new GetListTypeCusPreAsyn(
						getActivity());
				getListTypeCusPreAsyn.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		} else {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				GetListTypeCusAsyn getListTypeCusAsyn = new GetListTypeCusAsyn(
						getActivity());
				getListTypeCusAsyn.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}

		initTypePaper();
		initValue();

		initProvince();

		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			arrSexBeans.clear();
		}
		initSex();

		// spinner_loaikh.setOnItemSelectedListener(new OnItemSelectedListener()
		// {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// if ("1".equals(subType)) {
		// busType = arrBusTypePreBeans.get(arg2).getBusType();
		// } else {
		// busType = arrBusTypeBeans.get(arg2).getBusType();
		// }
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });

		spinner_province
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						province = arrProvince.get(arg2).getProvince();
						initDistrict(province);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		spinner_district
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						district = arrDistrict.get(arg2).getDistrict();
						initPrecinct(province, district);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		spinner_precint.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				precinct = arrPrecinct.get(arg2).getPrecinct();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		edit_ngaysinh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog birthDateDialog = new FixedHoloDatePickerDialog(
						getActivity(), AlertDialog.THEME_HOLO_LIGHT, birthDatePickerListener, dateBirthYear,
						dateBirthMonth, dateBirthDay);
				birthDateDialog.show();

			}
		});

		edit_ngaycap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog NgaycapDateDialog = new FixedHoloDatePickerDialog(
						getActivity(),AlertDialog.THEME_HOLO_LIGHT, NgayCapDatePickerListener, dateIdNoYear,
						dateIdNoMonth, dateIdNoDay);
				NgaycapDateDialog.show();

			}
		});

		editngayhethan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog hethanDateDiaLog = new FixedHoloDatePickerDialog(
						getActivity(),AlertDialog.THEME_HOLO_LIGHT, HethanDatePickerListener,
						dateHethanYear, dateHethanMonth, dateHethanDay);
				hethanDateDiaLog.show();
			}
		});

		spinner_type_giay_to
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						idType = arrTypePaperBeans.get(arg2).getParType();
						if ("3".equalsIgnoreCase(idType)) {
							lnngayhethan.setVisibility(View.VISIBLE);
						} else {
							lnngayhethan.setVisibility(View.GONE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		spinner_sex.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				sex = arrSexBeans.get(arg2).getValues();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			MainActivity.getInstance().finish();
			LoginDialog dialog = new LoginDialog(getActivity(),
					";connect_mobile;");

			dialog.show();
		}
	};

	// ==============ws get type kh tra truoc ===========
	private class GetListTypeCusPreAsyn extends
			AsyncTask<String, Void, ArrayList<BusTypePreBean>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListTypeCusPreAsyn(Context context) {
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
		protected ArrayList<BusTypePreBean> doInBackground(String... params) {
			return getListBusTypePre();
		}

		@Override
		protected void onPostExecute(ArrayList<BusTypePreBean> result) {

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0 && !result.isEmpty()) {

					arrBusTypePreBeans = result;
					// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					// getActivity(),
					// android.R.layout.simple_dropdown_item_1line,
					// android.R.id.text1);
					// for (BusTypePreBean busTypePreBean : result) {
					// adapter.add(busTypePreBean.getName());
					// }
					// if (spinner_loaikh != null) {
					// spinner_loaikh.setAdapter(adapter);
					// }

				} else {

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.checkTypeCus),
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

		private ArrayList<BusTypePreBean> getListBusTypePre() {
			ArrayList<BusTypePreBean> lstBusTypePreBeans = new ArrayList<>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListBusTypePre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListBusTypePre>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListBusTypePre>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListBusTypePre");
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
					nodechild = doc.getElementsByTagName("lstPrepaidBusType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						BusTypePreBean busPreBean = new BusTypePreBean();
						Element e1 = (Element) nodechild.item(j);
						String busType = parse.getValue(e1, "busType");
						Log.d("busType", busType);
						busPreBean.setBusType(busType);

						String cusType = parse.getValue(e1, "cusType");
						busPreBean.setCusType(cusType);

						String name = parse.getValue(e1, "name");
						busPreBean.setName(name);
						lstBusTypePreBeans.add(busPreBean);
					}
				}

			} catch (Exception e) {
				Log.d("exception", e.toString());
			}

			return lstBusTypePreBeans;
		}
	}

	// ==============ws lay danh sach loai kh tra sau

	private class GetListTypeCusAsyn extends
			AsyncTask<String, Void, ArrayList<BusTypeBean>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListTypeCusAsyn(Context context) {
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
		protected ArrayList<BusTypeBean> doInBackground(String... params) {

			return getListBusType();
		}

		@Override
		protected void onPostExecute(ArrayList<BusTypeBean> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0 && !result.isEmpty()) {

					arrBusTypeBeans = result;

					// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					// getActivity(),
					// android.R.layout.simple_dropdown_item_1line,
					// android.R.id.text1);
					// for (BusTypeBean busTypeBean : result) {
					// adapter.add(busTypeBean.getName());
					// }
					// if (spinner_loaikh != null) {
					// spinner_loaikh.setAdapter(adapter);
					// }

				} else {

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.checkTypeCus),
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

		private ArrayList<BusTypeBean> getListBusType() {
			ArrayList<BusTypeBean> lstBusTypeBeans = new ArrayList<>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListBusType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListBusType>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListBusType>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListBusType");
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
					nodechild = doc.getElementsByTagName("lstBusType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						BusTypeBean busTypeBean = new BusTypeBean();
						Element e1 = (Element) nodechild.item(j);
						String busType = parse.getValue(e1, "busType");
						Log.d("busType", busType);
						busTypeBean.setBusType(busType);

						String cusType = parse.getValue(e1, "cusType");
						busTypeBean.setCusType(cusType);

						String name = parse.getValue(e1, "name");
						busTypeBean.setName(name);
						lstBusTypeBeans.add(busTypeBean);
					}
				}

			} catch (Exception e) {
				Log.d("exception", e.toString());
			}

			return lstBusTypeBeans;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.daunoismartsim);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 104:
				if (data != null) {
					BusTypePreBean busTypePreBeansKey = (BusTypePreBean) data
							.getExtras().getSerializable("busTypePreBeansKey");
					busType = busTypePreBeansKey.getBusType();

					edtloaikh.setText(busTypePreBeansKey.getName());

					Log.d(Constant.TAG,
							"spinner_loaikh onItemSelected Tra Truoc subType: "
									+ subType + " busType: " + busType);
				}
				break;

			case 105:
				if (data != null) {

					BusTypeBean busTypeBeansKey = (BusTypeBean) data
							.getExtras().getSerializable("busTypeBeansKey");
					edtloaikh.setText(busTypeBeansKey.getName());
					busType = busTypeBeansKey.getBusType();
					Log.d(Constant.TAG,
							"spinner_loaikh onItemSelected Tra Sau subType: "
									+ subType + " busType: " + busType);
				}
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:

			getActivity().onBackPressed();

			break;
		case R.id.btnthemmoi:
			// ws check kh ton tai hay chua
			if (CommonActivity.isNullOrEmpty(busType)) {
				Toast.makeText(getActivity(),
						getString(R.string.confirmloaikh), Toast.LENGTH_SHORT)
						.show();
			} else if (edit_tenKH.getText().toString() != null
					&& !edit_tenKH.getText().toString().isEmpty()) {
				if (StringUtils.CheckCharSpecical(edit_tenKH.getText()
                        .toString())) {
					Toast.makeText(
							getActivity(),
							getResources()
									.getString(R.string.checkcharspecical),
							Toast.LENGTH_SHORT).show();
				} else {
					if (edit_socmnd.getText().toString() != null
							&& !edit_socmnd.getText().toString().isEmpty()) {
						// ===check ngay cap ==========
						if (dateBirthDate.after(dateIdNoDate)
								|| dateBirthDate.equals(dateIdNoDate)) {
							Toast.makeText(getActivity(),
									getString(R.string.checkcmtngaycap),
									Toast.LENGTH_SHORT).show();
						} else {
							// mt quan doi
							if (idType.equalsIgnoreCase("3")
									|| idType.equalsIgnoreCase("4")) {

								if (idType.equalsIgnoreCase("3")) {
									if (dateHethanDate.before(dateIdNoDate)
											|| dateHethanDate
													.equals(dateIdNoDate)) {
										Toast.makeText(
												getActivity(),
												getString(R.string.checkhethanngaycap),
												Toast.LENGTH_SHORT).show();
									} else {
										if (edit_noicap.getText().toString() != null
												&& !edit_noicap.getText()
														.toString().isEmpty()) {

											// TODO move sang man hinh dau noi
											// smartsim
											insertCus();

										} else {
											Toast.makeText(
													getActivity(),
													getString(R.string.checknoicap),
													Toast.LENGTH_SHORT).show();
										}
									}
								} else {
									if (edit_noicap.getText().toString() != null
											&& !edit_noicap.getText()
													.toString().isEmpty()) {
										// TODO move sang man hinh dau noi
										// smartsim
										insertCus();
									} else {
										Toast.makeText(
												getActivity(),
												getString(R.string.checknoicap),
												Toast.LENGTH_SHORT).show();
									}
								}
							} else {
								if (!DateTimeUtils.compareAge(dateIdNoDate, 15)) {
									// ===== check noi cap =================
									if (edit_noicap.getText().toString() != null
											&& !edit_noicap.getText()
													.toString().isEmpty()) {

										// TODO move sang man hinh dau noi
										// smartsim
										insertCus();

									} else {
										Toast.makeText(
												getActivity(),
												getString(R.string.checknoicap),
												Toast.LENGTH_SHORT).show();
									}
								} else {
									Toast.makeText(
											getActivity(),
											getString(R.string.checkDatengaycap),
											Toast.LENGTH_SHORT).show();
								}
							}
						}
					} else {
						Toast.makeText(getActivity(),
								getString(R.string.checkcmt),
								Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Toast.makeText(getActivity(), getString(R.string.checknameKH),
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	}

	private void insertCus() {
		// =========== them moi kh tra TRUOC =========
		String nameCustomer = edit_tenKH.getText().toString();
		Log.d("nameCustomer", nameCustomer);
		String socmt = edit_socmnd.getText().toString();
		Log.d("socmt", socmt);
		String birthDate = edit_ngaysinh.getText().toString();
		Log.d("birthDate", birthDate);
		String ngaycap = edit_ngaycap.getText().toString();
		Log.d("ngaycap", ngaycap);
		String noicap = edit_noicap.getText().toString();
		Log.d("noicap", noicap);
		CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
		custommerByIdNoBean.setNameCustomer(nameCustomer);
		custommerByIdNoBean.setBirthdayCusNew(birthDate);
		custommerByIdNoBean.setIdNo(socmt);
		custommerByIdNoBean.setCusType(busType);
		custommerByIdNoBean.setCustId("");
		custommerByIdNoBean.setProvince(staff.getProvince());
		custommerByIdNoBean.setPrecint(staff.getPrecinct());
		custommerByIdNoBean.setDistrict(staff.getDistrict());
		custommerByIdNoBean.setIdIssuePlace(noicap);
		custommerByIdNoBean.setIdIssueDate(ngaycap);
		custommerByIdNoBean.setAreaCode(staff.getProvince()
				+ staff.getDistrict() + staff.getPrecinct());
		custommerByIdNoBean.setIdType(idType);
		custommerByIdNoBean.setSex(sex);
		custommerByIdNoBean.setStrIdExpire(editngayhethan.getText().toString());
		// try{
		// GetAreaDal dal = new GetAreaDal(getActivity());
		// dal.open();
		// String fulladdress = dal.getfulladddress(province+district+precinct);
		custommerByIdNoBean.setAddreseCus(staff.getAddress());
		custommerByIdNoBean.setStreet_block(staff.getStreet_block());
		custommerByIdNoBean.setStreet(staff.getStreet());
		custommerByIdNoBean.setHome(staff.getHome());
		// dal.close();
		// }catch(Exception e){
		// e.printStackTrace();
		// }

		// TODO XU LY CHO NAY CHUYEN QUAN MAN HINH DAU NOI MOBILE

		Bundle bundle = new Bundle();
		bundle.putSerializable("custommerKey", custommerByIdNoBean);
		bundle.putString("subTypeKey", subType);
		bundle.putSerializable("staffKey", staff);
		bundle.putString("objectCodeKey", staff.getStaffCode());
		FragmentConnectionMobileSmartSimConnect fragCustomerSmartSimConnect = new FragmentConnectionMobileSmartSimConnect();
		fragCustomerSmartSimConnect.setArguments(bundle);
		ReplaceFragment.replaceFragment(getActivity(),
				fragCustomerSmartSimConnect, false);

	}

	private void initValue() {
		if (!staff.getBirthday().equals("")) {
			if (staff.getBirthday().split("-").length == 3) {
				dateBirthDay = Integer
						.parseInt(staff.getBirthday().split("-")[2].split(" ")[0]);
				dateBirthMonth = Integer.parseInt(staff.getBirthday()
						.split("-")[1]) - 1;
				dateBirthYear = Integer
						.parseInt(staff.getBirthday().split("-")[0]);

				edit_ngaysinh.setText(StringUtils.convertDate(staff
						.getBirthday()));
				try {
					dateBirthDate = sdf.parse(staff.getBirthday());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			Calendar cal = Calendar.getInstance();
			dateBirthYear = cal.get(Calendar.YEAR);
			dateBirthMonth = cal.get(Calendar.MONTH);
			dateBirthDay = cal.get(Calendar.DAY_OF_MONTH);
			edit_ngaysinh.setText(DateTimeUtils.convertDateTimeToString(
					cal.getTime(), "dd/MM/yyyy"));

			dateBirth = edit_ngaysinh.getText().toString();
			Log.d("dateBirthInit", dateBirth);

			dateBirthString = String.valueOf(dateBirthYear) +
                    "-" + (dateBirthMonth + 1) + "-" +
                    dateBirthDay;
			Log.d("dateBirthString", dateBirthString);
			try {
				dateBirthDate = sdf.parse(dateBirthString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!staff.getId_issue_date().equals("")) {

			if (staff.getId_issue_date().split("-").length == 3) {
				dateIdNoDay = Integer.parseInt(staff.getId_issue_date().split(
						"-")[2].split(" ")[0]);
				dateIdNoMonth = Integer.parseInt(staff.getId_issue_date()
						.split("-")[1]) - 1;
				dateIdNoYear = Integer.parseInt(staff.getId_issue_date().split(
						"-")[0]);

				edit_ngaycap.setText(StringUtils.convertDate(staff
						.getId_issue_date()));
				try {
					dateIdNoDate = sdf.parse(staff.getId_issue_date());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			Calendar cal = Calendar.getInstance();
			dateIdNoYear = cal.get(Calendar.YEAR);
			dateIdNoMonth = cal.get(Calendar.MONTH);
			dateIdNoDay = cal.get(Calendar.DAY_OF_MONTH);
			dateIdNoString = String.valueOf(dateIdNoYear) +
                    "-" + (dateIdNoMonth + 1) + "-" +
                    dateIdNoDay;
			Log.d("dateIdNoString", dateIdNoString);
			try {
				dateIdNoDate = sdf.parse(dateIdNoString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edit_ngaycap.setText(DateTimeUtils.convertDateTimeToString(
					cal.getTime(), "dd/MM/yyyy"));
			dateIdNo = edit_ngaycap.getText().toString();
			Log.d("dateIdNoInit", dateIdNo);

		}

		Calendar cal = Calendar.getInstance();
		dateHethanYear = cal.get(Calendar.YEAR);
		dateHethanMonth = cal.get(Calendar.MONTH);
		dateHethanDay = cal.get(Calendar.DAY_OF_MONTH);
		dateHethanString = String.valueOf(dateHethanYear) +
                "-" + (dateHethanMonth + 1) + "-" +
                dateHethanDay;
		Log.d("dateHethanString", dateHethanString);
		try {
			dateHethanDate = sdf.parse(dateHethanString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		editngayhethan.setText(DateTimeUtils.convertDateTimeToString(
				cal.getTime(), "dd/MM/yyyy"));
		dateHethan = editngayhethan.getText().toString();
		Log.d("dateHethan", dateHethan);

	}

	private final DatePickerDialog.OnDateSetListener birthDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			dateBirthYear = selectedYear;
			dateBirthMonth = selectedMonth;
			dateBirthDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (dateBirthDay < 10) {
				strDate.append("0");
			}
			strDate.append(dateBirthDay).append("/");
			if (dateBirthMonth < 9) {
				strDate.append("0");
			}
			strDate.append(dateBirthMonth + 1).append("/");
			strDate.append(dateBirthYear);

			dateBirth = strDate.toString();

			Log.d("dateBirth", dateBirth);

			dateBirthString = String.valueOf(dateBirthYear) +
                    "-" + (dateBirthMonth + 1) + "-" +
                    dateBirthDay;
			Log.d("dateBirthString", dateBirthString);
			try {
				dateBirthDate = sdf.parse(dateBirthString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edit_ngaysinh.setText(strDate);
		}
	};
	private final DatePickerDialog.OnDateSetListener NgayCapDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			dateIdNoYear = selectedYear;
			dateIdNoMonth = selectedMonth;
			dateIdNoDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (dateIdNoDay < 10) {
				strDate.append("0");
			}
			strDate.append(dateIdNoDay).append("/");
			if (dateIdNoMonth < 9) {
				strDate.append("0");
			}
			strDate.append(dateIdNoMonth + 1).append("/");
			strDate.append(dateIdNoYear);

			dateIdNo = strDate.toString();

			Log.d("dateIdNo", dateIdNo);

			dateIdNoString = String.valueOf(dateIdNoYear) +
                    "-" + (dateIdNoMonth + 1) + "-" +
                    dateIdNoDay;
			Log.d("dateIdNoString", dateIdNoString);
			try {

				dateIdNoDate = sdf.parse(dateIdNoString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			edit_ngaycap.setText(strDate);
		}
	};

	private final DatePickerDialog.OnDateSetListener HethanDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			dateHethanYear = selectedYear;
			dateHethanMonth = selectedMonth;
			dateHethanDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (dateHethanDay < 10) {
				strDate.append("0");
			}
			strDate.append(dateHethanDay).append("/");
			if (dateHethanMonth < 9) {
				strDate.append("0");
			}
			strDate.append(dateHethanMonth + 1).append("/");
			strDate.append(dateHethanYear);

			dateHethan = strDate.toString();

			Log.d("dateHethan", dateHethan);

			dateHethanString = String.valueOf(dateHethanYear) +
                    "-" + (dateHethanMonth + 1) + "-" +
                    dateHethanDay;
			Log.d("dateHethanString", dateHethanString);
			try {

				dateHethanDate = sdf.parse(dateHethanString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			editngayhethan.setText(strDate);
		}
	};

	// lay ma tinh/thanhpho

	private void initProvince() {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrProvince = dal.getLstProvince();
			dal.close();

			if (arrProvince != null && arrProvince.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (AreaBean areaBean : arrProvince) {
					adapter.add(areaBean.getNameProvince());
				}
				spinner_province.setAdapter(adapter);

				// TODO vÃ­ dá»¥ add spinner
				if (staff.getProvince() != null
						&& !staff.getProvince().isEmpty()) {
					for (AreaBean areaBean : arrProvince) {
						if (areaBean.getProvince().equalsIgnoreCase(
								staff.getProvince())) {
							spinner_province.setSelection(arrProvince
									.indexOf(areaBean));
							spinner_province.setClickable(false);
							break;
						}
					}

				} else {
					spinner_province.setClickable(true);
				}

			}
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

			if (arrDistrict != null && arrDistrict.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (AreaBean areaBean : arrDistrict) {
					adapter.add(areaBean.getNameDistrict());
				}
				spinner_district.setAdapter(adapter);

				if (staff.getDistrict() != null
						&& !staff.getDistrict().isEmpty()) {
					for (AreaBean areaBean : arrDistrict) {
						if (areaBean.getDistrict().equalsIgnoreCase(
								staff.getDistrict())) {
							spinner_district.setSelection(arrDistrict
									.indexOf(areaBean));
							spinner_district.setClickable(false);
							break;
						}
					}

				} else {
					spinner_district.setClickable(true);
				}

			}
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

			if (arrPrecinct != null && arrPrecinct.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (AreaBean areaBean : arrPrecinct) {
					adapter.add(areaBean.getNamePrecint());
				}
				spinner_precint.setAdapter(adapter);

				if (staff.getPrecinct() != null
						&& !staff.getPrecinct().isEmpty()) {
					for (AreaBean areaBean : arrPrecinct) {
						if (areaBean.getDistrict().equalsIgnoreCase(
								staff.getPrecinct())) {
							spinner_precint.setSelection(arrPrecinct
									.indexOf(areaBean));
							spinner_precint.setClickable(false);
							break;
						}
					}
				} else {
					spinner_precint.setClickable(true);
				}

			}
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
		}
	}

	// init gioi tinh
	private void initSex() {
		arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
		arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (SexBeans sexBeans : arrSexBeans) {
				adapter.add(sexBeans.getName());
			}
			spinner_sex.setAdapter(adapter);
		}
	}

	// init typepaper
	private void initTypePaper() {
		GetTypePaperDal dal = null;
		try {
			dal = new GetTypePaperDal(getActivity());
			dal.open();
			arrTypePaperBeans = dal.getLisTypepaper();
			dal.close();
			if (arrTypePaperBeans != null && !arrTypePaperBeans.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
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
	}

}
