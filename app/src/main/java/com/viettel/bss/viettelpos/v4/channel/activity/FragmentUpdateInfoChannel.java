package com.viettel.bss.viettelpos.v4.channel.activity;

import com.viettel.bss.viettelpos.v4.channel.activity.DialogCreateAddress.OnAcceptListener;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateAddress;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentUpdateInfoChannel extends Fragment implements
		OnClickListener {
	private static final String TAG = FragmentUpdateInfoChannel.class.getSimpleName();
	private Activity activity;
	private InfrastrucureDB mInfrastrucureDB;
	private View mView;
	private Spinner spnContractMethod, spnBusinessMethod, spnPointofSaleType;
	private EditText edtChannelName, edtPhone;
    private Button btnUpdate, btnCancel;
	private Button btnHome;
	private TextView txtNameActionBar;
	private final ArrayList<AreaObj> mListContractType = new ArrayList<>();
	private ArrayList<AreaObj> mListPointOfSalteType = new ArrayList<>();
	private final ArrayList<AreaObj> mListBusinessMethod = new ArrayList<>();
	private AreaObj areaProvicial;
	private AreaObj areaDistrist;
	private AreaObj areaPrecint = new AreaObj();
	private AreaObj areaGroup = new AreaObj();
	private Staff mStaff;
	private String street = ""; // duong
	private String streetBlock = ""; // to
	private String province = ""; // tinh
	private String precinct = ""; // xa
	private String home = ""; // so nha
	private String district = ""; // huyen
	private boolean isChangeAddress = false;
    private SQLiteDatabase database = null;
	private AreaObj areaContractObject;
	private AreaObj areaPointOfSaleType;
	private AreaObj areaBusinessMethod;
	private EditText edtAddress;
	private StringBuilder address;

	private LinearLayout lnSogiayto;
	private EditText edtIdNumber, edtDateRange, edtPlaceGive;

	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private Date dateNow = null;
	private Date issueDateDD = null;
	private String dateNowString = "";

	private Calendar calissueDateDD;
	private int dayissueDateDD;
	private int monissueDateDD;
	private int yearissueDateDD;

	private final SimpleDateFormat dbUpdateDateTime = new SimpleDateFormat(
			"dd/MM/yyyy");
//	private String timeCreateIdNo, birthday, idno;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Calendar calendar = Calendar.getInstance();
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

		calissueDateDD = Calendar.getInstance();
		issueDateDD = calissueDateDD.getTime();
		dayissueDateDD = calissueDateDD.get(Calendar.DAY_OF_MONTH);
		monissueDateDD = calissueDateDD.get(Calendar.MONTH);
		yearissueDateDD = calissueDateDD.get(Calendar.YEAR);

		activity = getActivity();
		mInfrastrucureDB = new InfrastrucureDB(activity);
		Bundle b = getArguments();
		mStaff = (Staff) b.getSerializable("STAFF");
//		timeCreateIdNo = b.getString("timeCreateIdNo","");
//		birthday = b.getString("birthday","");
//		idno = b.getString("idno","");
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.update_info_chanel, container,
					false);
			init(mView);

			btnCancel.setOnClickListener(this);
			btnUpdate.setOnClickListener(this);

			// Lay thong tin dia chi
			edtAddress.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String strProvince = Session.province;
					String strDistris = Session.district;
					Bundle mBundle = new Bundle();
					mBundle.putString("strProvince", strProvince);
					mBundle.putString("strDistris", strDistris);
					mBundle.putString("areaFlow", street);
					mBundle.putString("areaHomeNumber", home);
					mBundle.putSerializable("areaPrecint", areaPrecint);
					mBundle.putSerializable("areaGroup", areaGroup);
					mBundle.putLong("TYPE", 1L);
					
					if(getActivity() instanceof FragmentCusCareByDay){
						DialogCreateAddress dialogFragment = new DialogCreateAddress();
						dialogFragment.setArguments(mBundle);
						dialogFragment.setOnAcceptListener(onAcceptListener);
						
						dialogFragment.show(getFragmentManager(), getResources().getString(R.string.createAddress));
						
					} else {
						FragmentCreateAddress fragmentCreateAddress = new FragmentCreateAddress();
						fragmentCreateAddress.setArguments(mBundle);
						fragmentCreateAddress.setTargetFragment(
								FragmentUpdateInfoChannel.this, 100);
						ReplaceFragment.replaceFragment(activity,
								fragmentCreateAddress, false);
					}
				}
			});
		}

		addListItemSpinner();
		setDefaultData();

		Log.d("LOG",
				"contractMethod: " + mStaff.getContractMethod()
						+ " --businessMethod: " + mStaff.getBusinessMethod()
						+ "--pointOfSale: " + mStaff.getPointOfSaleType()
						+ " --province: " + mStaff.getProvince()
						+ " --district: " + mStaff.getDistrict()
						+ " -- precinct: " + mStaff.getPrecinct()
						+ "--street: " + mStaff.getStreet() + "--streetBlock: "
						+ mStaff.getStreetBlock() + "--home: "
						+ mStaff.getHome());
		return mView;
	}

	@Override
	public void onResume() {
		if(getActivity() instanceof FragmentCusCareByDay){
			Log.d(this.getClass().getSimpleName(), "getActivity() is instanceof FragmentCusCareByDay");
		} else {
        MainActivity.getInstance().setTitleActionBar(R.string.txt_update_channel_info);

			if (address != null && address.length() > 0 && isChangeAddress) {
				edtAddress.setText(address.toString());
			}
		}

		super.onResume();
	}

	// private Date issueDateDD = null;
	private void showDatePickerissueDateDD(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);

				try {

					issueDateDD = dbUpdateDateTime.parse(mNgaycaidat);
				} catch (Exception e) {
					e.printStackTrace();
				}

				calissueDateDD.set(year1, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,callback,
				yearissueDateDD, monissueDateDD, dayissueDateDD);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	private void setDefaultData() {
		if (mStaff != null) {
			ChannelDAL mChannelDAL = new ChannelDAL(getActivity());
			mStaff = mChannelDAL.getStaffByStaffCode(
					Define.TABLE_NAME_STAFF, mStaff.getStaffCode(), null);
			mChannelDAL.close();
			
			edtChannelName.setText(mStaff.getNameStaff());
			edtPhone.setText(mStaff.getTel());
			if (mStaff.getAddressStaff() != null
					&& !mStaff.getAddressStaff().equals("")
					&& (!isChangeAddress)) {
				Log.d("Thuytv3", "address 111111");
				edtAddress.setText(mStaff.getAddressStaff());

				// String[] strAddress = mStaff.getAddressStaff().split("-");
				// Log.d("LOG", "size strAddress: "+ strAddress.length);
				// if(strAddress.length == 6){
				// home = strAddress[0];
				// street = strAddress[1];
				// Log.d("LOG", "to: "+strAddress[2] );
				// Log.d("LOG", "xa: "+ strAddress[3]);
				// areaGroup.setName(strAddress[2]);
				// areaPrecint.setName(strAddress[3]);
				// }
			}
			if (mListContractType != null && mListContractType.size() > 0) {
				for (int i = 0; i < mListContractType.size(); i++) {
					AreaObj obj = mListContractType.get(i);
					if (obj.getAreaCode().equals(mStaff.getContractMethod())) {
						Log.d("Log", "check null spinner: " + spnContractMethod);
						spnContractMethod.setSelection(i);
						break;
					}
				}
			}
			if (mListPointOfSalteType != null
					&& mListPointOfSalteType.size() > 0) {
				for (int i = 0; i < mListPointOfSalteType.size(); i++) {
					AreaObj obj = mListPointOfSalteType.get(i);
					if (obj.getAreaCode().equals(mStaff.getPointOfSaleType())) {
						spnPointofSaleType.setSelection(i);
						break;
					}
				}
			}
			if (mListBusinessMethod != null && mListBusinessMethod.size() > 0) {
				for (int i = 0; i < mListBusinessMethod.size(); i++) {
					AreaObj obj = mListBusinessMethod.get(i);
					if (obj.getAreaCode().equals(mStaff.getBusinessMethod())) {
						spnBusinessMethod.setSelection(i);
						break;
					}
				}
			}
			if (mStaff.getProvince() != null && (!isChangeAddress)) {
				province = mStaff.getProvince();
			}
			if (mStaff.getDistrict() != null && (!isChangeAddress)) {
				district = mStaff.getDistrict();
			}
			if (mStaff.getPrecinct() != null && (!isChangeAddress)) {
				precinct = mStaff.getPrecinct();
				areaPrecint.setPrecinct(precinct);
			}
			if (mStaff.getStreet() != null && (!isChangeAddress)) {
				street = mStaff.getStreet();
			}
			if (mStaff.getStreetBlock() != null && (!isChangeAddress)) {
				streetBlock = mStaff.getStreetBlock();
				areaGroup.setAreaCode(streetBlock);
			}
			if (mStaff.getHome() != null && (!isChangeAddress)) {
				home = mStaff.getHome();
			}
			
			edtPlaceGive.setText(mStaff.getIdIssuePlace());
			
			if(!CommonActivity.isNullOrEmpty(mStaff.getIdUser_no())){
				edtIdNumber.setText(mStaff.getIdUser_no());
			}
//			private String timeCreateIdNo, birthday, idno;
			if(!CommonActivity.isNullOrEmpty(mStaff.getIdIssueDate())){
				edtDateRange.setText(DateTimeUtils.convertDate(mStaff.getIdIssueDate()));
			}
		}
	}

	private void init(View v) {
		spnBusinessMethod = (Spinner) v.findViewById(R.id.spn_hinhthuc_banhang);
		spnContractMethod = (Spinner) v.findViewById(R.id.spn_hinhthuc_hopdong);
		spnContractMethod
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 == 2) {
							lnSogiayto.setVisibility(View.GONE);
						} else {
							lnSogiayto.setVisibility(View.VISIBLE);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		spnPointofSaleType = (Spinner) v.findViewById(R.id.spn_vitri_diemban);
		edtChannelName = (EditText) v.findViewById(R.id.edt_ten_diemban);
		edtPhone = (EditText) v.findViewById(R.id.edt_sdt_lienhe);
        TextView txtAddress = (TextView) v.findViewById(R.id.tvAddress);
		btnUpdate = (Button) v.findViewById(R.id.btn_update_info);
		btnCancel = (Button) v.findViewById(R.id.btn_cancel_info);
		edtAddress = (EditText) v.findViewById(R.id.edtAddress);

		lnSogiayto = (LinearLayout) v.findViewById(R.id.lnSogiayto);
		edtIdNumber = (EditText) v.findViewById(R.id.edtIdNumber);
		edtDateRange = (EditText) v.findViewById(R.id.edtDateRange);
		edtDateRange.setText(dateNowString);
		edtDateRange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				showDatePickerissueDateDD(edtDateRange);
				CommonActivity.showDatePickerDialog(getActivity(), edtDateRange);
			}
		});
		edtPlaceGive = (EditText) v.findViewById(R.id.edtPlaceGive);

		
		LinearLayout lnHeader = (LinearLayout) v.findViewById(R.id.lnHeader);
		if(getActivity() instanceof FragmentCusCareByDay){
			lnHeader.setVisibility(View.VISIBLE);
		} else {
			lnHeader.setVisibility(View.GONE);
		}
	}

	private void addListItemSpinner() {
		if (mListContractType != null && mListContractType.size() > 0) {
			mListContractType.clear();
		}
		if (mListPointOfSalteType != null && mListPointOfSalteType.size() > 0) {
			mListPointOfSalteType.clear();
		}
		if (mListBusinessMethod != null && mListBusinessMethod.size() > 0) {
			mListBusinessMethod.clear();
		}

		// hinh thuc hop dong
		AreaObj areaContractItem1 = new AreaObj();
		areaContractItem1.setName(getResources().getString(
				R.string.contractype_item_1));
		areaContractItem1.setAreaCode("1");
		mListContractType.add(areaContractItem1);

		AreaObj areaContractItem2 = new AreaObj();
		areaContractItem2.setName(getResources().getString(
				R.string.contractype_item_2));
		areaContractItem2.setAreaCode("2");
		mListContractType.add(areaContractItem2);

		AreaObj areaContractItem3 = new AreaObj();
		areaContractItem3.setName(getResources().getString(
				R.string.contractype_item_3));
		areaContractItem3.setAreaCode("3");
		mListContractType.add(areaContractItem3);

		AdapterProvinceSpinner adapterContractType = new AdapterProvinceSpinner(
				mListContractType, getActivity());
		Log.d("addListItemSpinner", "addAppter" + adapterContractType);
		spnContractMethod.setAdapter(adapterContractType);

		// vi tri ban hang
		AreaObj areaPositionSellItem1 = new AreaObj();
		areaPositionSellItem1.setName(getResources().getString(
				R.string.positionsell_item_1));
		areaPositionSellItem1.setAreaCode("1");
		mListBusinessMethod.add(areaPositionSellItem1);

		AreaObj areaPositionSellItem2 = new AreaObj();
		areaPositionSellItem2.setName(getResources().getString(
				R.string.positionsell_item_2));
		areaPositionSellItem2.setAreaCode("2");
		mListBusinessMethod.add(areaPositionSellItem2);

		AdapterProvinceSpinner adapterPositionSellType = new AdapterProvinceSpinner(
				mListBusinessMethod, getActivity());
		spnBusinessMethod.setAdapter(adapterPositionSellType);

		// loai diem ban
		mListPointOfSalteType = mInfrastrucureDB
				.getListTypeOfSale("POINT_OF_SALES_TYPE");
		AdapterProvinceSpinner adapterTypeOffSale = new AdapterProvinceSpinner(
				mListPointOfSalteType, getActivity());
		spnPointofSaleType.setAdapter(adapterTypeOffSale);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 100) {
				isChangeAddress = true;
				areaProvicial = (AreaObj) data.getExtras().getSerializable(
						"areaProvicial");
				areaDistrist = (AreaObj) data.getExtras().getSerializable(
						"areaDistrist");
				areaPrecint = (AreaObj) data.getExtras().getSerializable(
						"areaPrecint");
				areaGroup = (AreaObj) data.getExtras().getSerializable(
						"areaGroup");

				String areaFlow = data.getExtras().getString("areaFlow");
				String areaHomeNumber = data.getExtras().getString(
						"areaHomeNumber");

				address = new StringBuilder();

				if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
					address.append(areaHomeNumber).append(" - ");
					home = areaHomeNumber;
				}
				if (areaFlow != null && areaFlow.length() > 0) {
					address.append(areaFlow).append(" - ");
					street = areaFlow;
				}
				if (areaGroup != null && areaGroup.getName() != null
						&& areaGroup.getName().length() > 0) {
					address.append(areaGroup.getName()).append(" - ");
					streetBlock = areaGroup.getAreaCode();
				}
				if (areaPrecint != null && areaPrecint.getName() != null
						&& areaPrecint.getName().length() > 0) {

					address.append(areaPrecint.getName()).append(" - ");
					precinct = areaPrecint.getPrecinct();
				}
				if (areaDistrist != null && areaDistrist.getName() != null
						&& areaDistrist.getName().length() > 0) {

					address.append(areaDistrist.getName()).append(" - ");
					district = areaDistrist.getDistrict();
				}
				if (areaProvicial != null && areaProvicial.getName() != null
						&& areaProvicial.getName().length() > 0) {

					address.append(areaProvicial.getName());
					province = areaProvicial.getProvince();
				}

			}
		}
	}

	private class AsyntaskUpdateInfoChanel extends
			AsyncTask<String, Void, String> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		

		public AsyntaskUpdateInfoChanel(Context context) {
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
		protected String doInBackground(String... params) {
			updateInfoChanel();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				try {
					mStaff.setNameStaff(edtChannelName.getText().toString());
					mStaff.setTel(edtPhone.getText().toString());
					mStaff.setAddressStaff(edtAddress.getText().toString());
					mStaff.setProvince(province);
					mStaff.setDistrict(district);
					mStaff.setPrecinct(precinct);
					mStaff.setStreet(street);
					mStaff.setStreetBlock(streetBlock);
					mStaff.setHome(home);
					mStaff.setContractMethod(areaContractObject.getAreaCode());
					mStaff.setBusinessMethod(areaBusinessMethod.getAreaCode());
					mStaff.setPointOfSaleType(areaPointOfSaleType.getAreaCode());
					mStaff.setIdIssuePlace(edtPlaceGive.getText().toString());
					mStaff.setIdIssueDate(DateTimeUtils.formatDateInsert( edtDateRange.getText().toString()));
					mStaff.setIdUser_no(edtIdNumber.getText().toString());

                    DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
					database = dbOpenHelper.getWritableDatabase();
					
					StaffDAL dal = new StaffDAL(database);
					boolean re = dal.updateStaff(mStaff);
					
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.capnhatthanhcong),
							getString(R.string.app_name), backClick).show();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (database != null) {
						database.close();
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

		private void updateInfoChanel() {
			areaContractObject = mListContractType.get(spnContractMethod
					.getSelectedItemPosition());
			areaPointOfSaleType = mListPointOfSalteType.get(spnPointofSaleType
					.getSelectedItemPosition());
			areaBusinessMethod = mListBusinessMethod.get(spnBusinessMethod
					.getSelectedItemPosition());
			String strPhone = CommonActivity.checkStardardIsdn(edtPhone
					.getText().toString());

			int index = 0;
			if (mListContractType.size() > 0) {
				index = spnContractMethod.getSelectedItemPosition();
				Log.d("Log", "index selected: " + index);
			}

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateStaff>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				
				if (index != 2) {
					rawData.append("<issueDate>").append(edtDateRange.getText().toString()).append("</issueDate>");
				}
			
				rawData.append("<staff>");
				
				rawData.append("<address>").append(edtAddress.getText().toString()).append("</address>");
				rawData.append("<district>").append(district).append("</district>");
				rawData.append("<home>").append(home).append("</home>");
				rawData.append("<precinct>").append(precinct).append("</precinct>");
				rawData.append("<province>").append(province).append("</province>");
				rawData.append("<street>").append(street).append("</street>");
				rawData.append("<streetBlock>").append(streetBlock).append("</streetBlock>");
				rawData.append("<businessMethod>").append(areaBusinessMethod.getAreaCode()).append("</businessMethod>");
				rawData.append("<name>").append(edtChannelName.getText().toString().trim()).append("</name>");
				rawData.append("<contractMethod>").append(areaContractObject.getAreaCode()).append("</contractMethod>");
				rawData.append("<tel>").append(strPhone).append("</tel>");
				rawData.append("<pointOfSaleType>").append(areaPointOfSaleType.getAreaCode()).append("</pointOfSaleType>");
				rawData.append("<staffCode>").append(mStaff.getStaffCode()).append("</staffCode>");

				if (index != 2) {
					rawData.append("<idNo>").append(edtIdNumber.getText().toString().trim()).append("</idNo>");
					rawData.append("<idIssuePlace>").append(edtPlaceGive.getText().toString().trim()).append("</idIssuePlace>");
				}else{
					rawData.append("<idNo>"
							+ ""
							+ "</idNo>");
					rawData.append("<idIssuePlace>"
							+ ""
							+ "</idIssuePlace>");
					rawData.append("<issueDate>"
							+ ""
							+ "</issueDate>");
				}

				rawData.append("</staff>");
				rawData.append("</input>");
				rawData.append("</ws:updateStaff>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input
						.sendRequest(envelope, Constant.BCCS_GW_URL,
								getActivity(), "mbccs_updateStaff");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("LOG", "erroeCode:  " + errorCode);
					Log.d("LOG", "description:  " + description);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			MainActivity.getInstance().finish();
			LoginDialog dialog = new LoginDialog(getActivity(), ";update_channel;");
			dialog.show();

		}
	};
	private final OnClickListener backClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if(getActivity() instanceof FragmentCusCareByDay){
				Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
				getActivity().findViewById(R.id.llViewPager).setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.llContainer).setVisibility(View.GONE);
			} else {
				Log.d(TAG, "activity dont instanceof FragmentCusCareByDay");
				Intent i = new Intent();

				String strChannelName = edtChannelName.getText().toString();
				String strChannelPhone = edtPhone.getText().toString();

				i.putExtra("CHANNEL_NAME", strChannelName);
				i.putExtra("CHANNEL_PHONE", strChannelPhone);
				getTargetFragment().onActivityResult(getTargetRequestCode(),
						Activity.RESULT_OK, i);
				getActivity().onBackPressed();
			}
			
		}
	};

	private boolean validateUpdate() {
		if (edtChannelName.getText().toString().trim().equals("")) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.txt_staff_name_notnull),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		if (edtPhone.getText().toString().trim().equals("")) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.txt_phone_notnull),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}

		if (!CommonActivity.validateIsdn(edtPhone.getText().toString().trim())) {
			Dialog dialog = CommonActivity.createAlertDialog(
					getActivity(),
					getResources().getString(
							R.string.message_pleass_input_phone_number),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		if (edtAddress.getText().toString()
				.equals(getResources().getString(R.string.my_wards))) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.txt_address_notnull),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		if (province.equals("") || district.equals("")) {
			Dialog dialog = CommonActivity.createAlertDialog(
					getActivity(),
					getResources().getString(
							R.string.message_address_staff_error),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}
		if (precinct.equals("") || street.equals("") || streetBlock.equals("")
				|| home.equals("")) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.meassage_address_fail),
					getResources().getString(R.string.app_name));
			dialog.show();
			return false;
		}

		int index = 0;
		if (mListContractType.size() > 0) {
			index = spnContractMethod.getSelectedItemPosition();
			Log.d("Log", "index selected: " + index);
		}
		if (index != 2) {
			if (issueDateDD.after(dateNow)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.ngaycapnhohonngayhientai),
						getString(R.string.app_name)).show();
				return false;
			}
			if(CommonActivity.isNullOrEmpty(edtIdNumber.getText().toString().trim())){
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.chua_nhap_giay_to),
						getString(R.string.app_name)).show();
				return false;
			}
			if(edtIdNumber.getText().toString().trim().length() == 9 || edtIdNumber.getText().toString().trim().length() == 12){
				
			}else{
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checksoidno),
						getString(R.string.app_name)).show();
				return false;
			}
			if(CommonActivity.isNullOrEmpty(edtPlaceGive.getText().toString())){
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.message_pleass_input_place_give),
						getString(R.string.app_name)).show();
				return false;
			}
		}
		return true;
	}

	private final OnClickListener updateConfirmCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyntaskUpdateInfoChanel updateChannel = new AsyntaskUpdateInfoChanel(
						activity);
				updateChannel.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(activity, Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
//			activity.onBackPressed();
			if(getActivity() instanceof FragmentCusCareByDay){
				Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
				getActivity().findViewById(R.id.llViewPager).setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.llContainer).setVisibility(View.GONE);
			} else {
				Log.d(TAG, "activity dont instanceof FragmentCusCareByDay");
				getActivity().onBackPressed();
			}
			break;
		case R.id.btn_update_info:
			if (validateUpdate()) {
				CommonActivity.createDialog(activity,
						getString(R.string.message_confirm_update),
						getString(R.string.app_name),
						getString(R.string.say_ko), getString(R.string.say_co),
						null, updateConfirmCallBack).show();
			}
			break;
		case R.id.btn_cancel_info:
//			activity.onBackPressed();
			if(getActivity() instanceof FragmentCusCareByDay){
				Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
				getActivity().findViewById(R.id.llViewPager).setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.llContainer).setVisibility(View.GONE);
			} else {
				Log.d(TAG, "activity dont instanceof FragmentCusCareByDay");
				getActivity().onBackPressed();
			}
			break;
		default:
			break;
		}

	}
	
	private final OnAcceptListener onAcceptListener = new OnAcceptListener() {
		
		@Override
		public void accept(AreaObj areaProvicial, AreaObj areaDistrist,
				AreaObj areaPrecint, AreaObj areaGroup, String areaFlow,
				String areaHomeNumber) {
			// TODO Auto-generated method stub
			isChangeAddress = true;
			
			setAreaProvicial(areaProvicial);
			setAreaDistrist(areaDistrist);
			setAreaPrecint(areaPrecint);
			setAreaGroup(areaGroup);
			
			address = new StringBuilder();

			if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
				address.append(areaHomeNumber).append(" - ");
				home = areaHomeNumber;
			}
			if (areaFlow != null && areaFlow.length() > 0) {
				address.append(areaFlow).append(" - ");
				street = areaFlow;
			}
			if (areaGroup != null && areaGroup.getName() != null
					&& areaGroup.getName().length() > 0) {
				address.append(areaGroup.getName()).append(" - ");
				streetBlock = areaGroup.getAreaCode();
			}
			if (areaPrecint != null && areaPrecint.getName() != null
					&& areaPrecint.getName().length() > 0) {

				address.append(areaPrecint.getName()).append(" - ");
				precinct = areaPrecint.getPrecinct();
			}
			if (areaDistrist != null && areaDistrist.getName() != null
					&& areaDistrist.getName().length() > 0) {

				address.append(areaDistrist.getName()).append(" - ");
				district = areaDistrist.getDistrict();
			}
			if (areaProvicial != null && areaProvicial.getName() != null
					&& areaProvicial.getName().length() > 0) {

				address.append(areaProvicial.getName());
				province = areaProvicial.getProvince();
			}
			
			edtAddress.setText(address.toString());
			
		}
	};
	
	public AreaObj getAreaProvicial() {
		return areaProvicial;
	}

	private void setAreaProvicial(AreaObj areaProvicial) {
		this.areaProvicial = areaProvicial;
	}

	public AreaObj getAreaDistrist() {
		return areaDistrist;
	}

	private void setAreaDistrist(AreaObj areaDistrist) {
		this.areaDistrist = areaDistrist;
	}

	public AreaObj getAreaPrecint() {
		return areaPrecint;
	}

	private void setAreaPrecint(AreaObj areaPrecint) {
		this.areaPrecint = areaPrecint;
	}

	public AreaObj getAreaGroup() {
		return areaGroup;
	}

	private void setAreaGroup(AreaObj areaGroup) {
		this.areaGroup = areaGroup;
	}
	
	
	
}
