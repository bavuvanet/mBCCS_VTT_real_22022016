package com.viettel.bss.viettelpos.v4.sale.activity;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.BlankWhiteAdapter;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentCreateChanel extends Fragment implements OnClickListener {

	private View mView;
    private TextView txtNameActionBar;

	private Button btnHome;

    private Spinner spContractType;
	private Spinner spPositionSell;
	private Spinner spTypeOfSale;
	private Spinner spInfoChanel;

	private EditText edtIdNumber;
	private EditText edtDateRange;
	private EditText edtBirthDay;
	private EditText edtPlaceGive;
	private EditText edtTaxCode;
	private EditText edtPhoneNumber;
	private EditText edtAddress;
	private EditText edtChanelName;

	private ScrollView mScrollView;
	private LinearLayout lnIdNumber;
	private LinearLayout lnPlaceGive;
	private LinearLayout lnDateRange;

	private AreaObj areaProvicial;
	private AreaObj areaDistrist;
	private AreaObj areaPrecint;
	private AreaObj areaGroup;
	private String areaFlow;
	private String areaHomeNumber;
	private StringBuilder address;

	private Calendar cal;
	private int day;
	private int month;
	private int year;

	private Activity activity;
	private InfrastrucureDB mInfrastrucureDB;

	private final ArrayList<AreaObj> mListContractType = new ArrayList<>();
	private final ArrayList<AreaObj> mListPositionSell = new ArrayList<>();
	private ArrayList<AreaObj> mListTypeOffSale = new ArrayList<>();
	private final ArrayList<AreaObj> mListBlankStaff = new ArrayList<>();

	private Date issueDate = null;
	private Date birthDate = null;
	private Date dateNow = null;
    private BlankWhiteAdapter adapterBlankStaff;

	private void showDatePickerDialog(final EditText tvNgay, final Activity activity, final Calendar myCalendar) {

		OnDateSetListener callback = new OnDateSetListener() {
			private Dialog dialog = null;

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// Date

				Calendar calendar = Calendar.getInstance();
				int mYear = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				String curentDate = (day) + "/" + (month + 1) + "/" + mYear;
				String mDateRange = (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year;

				try {
					issueDate = DateTimeUtils.convertStringToTime(mDateRange, "dd/MM/yyyy");

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (curentDate.equals(mDateRange) && dialog == null) {
					dialog = CommonActivity.createAlertDialog(activity,
							getResources().getString(R.string.message_pleass_input_date_different_curentdate),
							getString(R.string.app_name));
					dialog.show();

				} else if (issueDate.after(dateNow) && dialog == null) {

					String message = "";
					if (tvNgay == edtDateRange) {
						message = getString(R.string.ngaycapnhohonngayhientai);
					} else {
						message = getString(R.string.ngaysinhnhohonngayhientai);
					}
					dialog = CommonActivity.createAlertDialog(activity, message,
							getResources().getString(R.string.app_name));
					dialog.show();
				} else if (tvNgay.getId() == R.id.edtBirthday && dialog == null) {
					String strDateRange = edtDateRange.getText().toString().trim();
					if (strDateRange.length() > 0) {
						Date dateCMT = DateTimeUtils.convertStringToTime(strDateRange, "dd/MM/yyyy");
						if (issueDate.after(dateCMT)) {
							dialog = CommonActivity.createAlertDialog(activity,
									getString(R.string.ngaysinhnhohonngaycapcmt),
									getResources().getString(R.string.app_name));
							dialog.show();
						} else {
							edtBirthDay.setText(mDateRange);
						}
					} else {
						edtBirthDay.setText(mDateRange);
					}
				} else if (tvNgay.getId() == R.id.edtDateRange && dialog == null) {
					String strbirthDay = edtBirthDay.getText().toString().trim();
					if (strbirthDay.length() > 0) {
						Date dateCMT = DateTimeUtils.convertStringToTime(strbirthDay, "dd/MM/yyyy");
						if (issueDate.before(dateCMT)) {
							dialog = CommonActivity.createAlertDialog(activity,
									getString(R.string.ngayhonngaycapcmtlonhonngaysinh),
									getResources().getString(R.string.app_name));
							dialog.show();
						} else {
							edtDateRange.setText(mDateRange);
						}
					} else {
						edtDateRange.setText(mDateRange);
					}
				}

				else {
					if (dialog != null)
						return;
					switch (tvNgay.getId()) {
					case R.id.edtDateRange:
						edtDateRange.setText(mDateRange);
						break;
					case R.id.edtBirthday:
						edtBirthDay.setText(mDateRange);
						break;
					default:
						break;
					}
				}

				Log.d("Log", "check null calendar" + cal);
				// myCalendar.set(year, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT,callback, year, month, day);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		activity = getActivity();
		mInfrastrucureDB = new InfrastrucureDB(activity);
		getListDataBlackStaffFromServer();

		Calendar calendar = Calendar.getInstance();
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

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_create_chanel, container, false);

			spContractType = (Spinner) mView.findViewById(R.id.spContractType);
			spPositionSell = (Spinner) mView.findViewById(R.id.spPositionSell);
			spInfoChanel = (Spinner) mView.findViewById(R.id.spInfoChanel);
			spTypeOfSale = (Spinner) mView.findViewById(R.id.spTypeOfSale);
			edtChanelName = (EditText) mView.findViewById(R.id.edtChanelName);
			edtIdNumber = (EditText) mView.findViewById(R.id.edtIdNumber);
			edtDateRange = (EditText) mView.findViewById(R.id.edtDateRange);
			edtBirthDay = (EditText) mView.findViewById(R.id.edtBirthday);
			edtPlaceGive = (EditText) mView.findViewById(R.id.edtPlaceGive);
			edtTaxCode = (EditText) mView.findViewById(R.id.edtTaxCode);
			edtPhoneNumber = (EditText) mView.findViewById(R.id.edtPhoneNumber);
			edtAddress = (EditText) mView.findViewById(R.id.edtAddress);

			lnIdNumber = (LinearLayout) mView.findViewById(R.id.lnIdNumber);
			lnPlaceGive = (LinearLayout) mView.findViewById(R.id.lnPlaceGive);
			lnDateRange = (LinearLayout) mView.findViewById(R.id.lnDateRange);
			mScrollView = (ScrollView) mView.findViewById(R.id.scrollView1);

            Button btnCreateChanel = (Button) mView.findViewById(R.id.btnSaveInfo);
            TextView txtAddress = (TextView) mView.findViewById(R.id.tvAddress);

			btnCreateChanel.setOnClickListener(this);
			edtIdNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

			edtDateRange.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getId() == R.id.edtDateRange) {

						Calendar calendar = Calendar.getInstance();
						year = calendar.get(Calendar.YEAR);

						month = calendar.get(Calendar.MONTH);
						day = calendar.get(Calendar.DAY_OF_MONTH);
						showDatePickerDialog(edtDateRange, activity, cal);
					}
				}
			});

			edtBirthDay.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getId() == R.id.edtBirthday) {

						Calendar calendar = Calendar.getInstance();
						year = calendar.get(Calendar.YEAR);

						month = calendar.get(Calendar.MONTH);
						day = calendar.get(Calendar.DAY_OF_MONTH);
						showDatePickerDialog(edtBirthDay, activity, cal);
					}
				}
			});

			edtAddress.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					String strProvince = Session.province;
					String strDistris = Session.district;

					if (strProvince == null || strProvince.length() == 0 || strDistris == null
							|| strDistris.length() == 0) {
						CommonActivity.createAlertDialog(activity, getString(R.string.message_address_staff_error),
								getString(R.string.app_name)).show();
						return;
					}

					Bundle mBundle = new Bundle();
					mBundle.putString("strProvince", strProvince);
					mBundle.putString("strDistris", strDistris);
					mBundle.putString("areaFlow", areaFlow);
					mBundle.putString("areaHomeNumber", areaHomeNumber);

					mBundle.putSerializable("areaPrecint", areaPrecint);
					mBundle.putSerializable("areaGroup", areaGroup);

					FragmentCreateAddress fragmentCreateAddress = new FragmentCreateAddress();
					fragmentCreateAddress.setArguments(mBundle);
					fragmentCreateAddress.setTargetFragment(FragmentCreateChanel.this, 100);
					ReplaceFragment.replaceFragment(activity, fragmentCreateAddress, false);
				}
			});
		}

		addListItemSpinner();

		return mView;
	}

	private void getListDataBlackStaffFromServer() {
		if (!CommonActivity.isNetworkConnected(activity)) {
			CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
					.show();
		} else {
			if (mListBlankStaff != null && mListBlankStaff.size() > 0 && adapterBlankStaff != null) {
				mListBlankStaff.clear();
				adapterBlankStaff.notifyDataSetChanged();
			}
			GetListBlankStaffAsynctask getListBlackStaffAsyncTask = new GetListBlankStaffAsynctask(activity, 1);
			Log.d("getListBlackStaff", "GetListBlankStaffAsynctask" + getListBlackStaffAsyncTask);
			getListBlackStaffAsyncTask.execute();
		}
	}

	private void addListItemSpinner() {

		mListContractType.clear();
		mListPositionSell.clear();
		mListTypeOffSale.clear();

		// hinh thuc hop dong
		AreaObj areaContractItem1 = new AreaObj();
		areaContractItem1.setName(getResources().getString(R.string.contractype_item_1));
		areaContractItem1.setAreaCode("1");
		mListContractType.add(areaContractItem1);

		AreaObj areaContractItem2 = new AreaObj();
		areaContractItem2.setName(getResources().getString(R.string.contractype_item_2));
		areaContractItem2.setAreaCode("2");
		mListContractType.add(areaContractItem2);

		AreaObj areaContractItem3 = new AreaObj();
		areaContractItem3.setName(getResources().getString(R.string.contractype_item_3));
		areaContractItem3.setAreaCode("3");
		mListContractType.add(areaContractItem3);

		AdapterProvinceSpinner adapterContractType = new AdapterProvinceSpinner(mListContractType, getActivity());
		Log.d("addListItemSpinner", "addAppter" + adapterContractType);
		spContractType.setAdapter(adapterContractType);

		spContractType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				if (arg2 == 2) {
					lnIdNumber.setVisibility(View.GONE);
					lnPlaceGive.setVisibility(View.GONE);
					lnDateRange.setVisibility(View.GONE);
				} else {
					lnIdNumber.setVisibility(View.VISIBLE);
					lnPlaceGive.setVisibility(View.VISIBLE);
					lnDateRange.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// vi tri ban hang
		AreaObj areaPositionSellItem1 = new AreaObj();
		areaPositionSellItem1.setName(getResources().getString(R.string.positionsell_item_1));
		areaPositionSellItem1.setAreaCode("1");
		mListPositionSell.add(areaPositionSellItem1);

		AreaObj areaPositionSellItem2 = new AreaObj();
		areaPositionSellItem2.setName(getResources().getString(R.string.positionsell_item_2));
		areaPositionSellItem2.setAreaCode("2");
		mListPositionSell.add(areaPositionSellItem2);

		AdapterProvinceSpinner adapterPositionSellType = new AdapterProvinceSpinner(mListPositionSell, getActivity());
		spPositionSell.setAdapter(adapterPositionSellType);

		mListTypeOffSale = mInfrastrucureDB.getListTypeOfSaleCreateChanel("POINT_OF_SALES_TYPE");
		AdapterProvinceSpinner adapterTypeOffSale = new AdapterProvinceSpinner(mListTypeOffSale, getActivity());
		spTypeOfSale.setAdapter(adapterTypeOffSale);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.add_news_chanel);

		if (address != null) {
			edtAddress.setText(address);
		}

		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 100) {

				areaProvicial = (AreaObj) data.getExtras().getSerializable("areaProvicial");
				areaDistrist = (AreaObj) data.getExtras().getSerializable("areaDistrist");
				areaPrecint = (AreaObj) data.getExtras().getSerializable("areaPrecint");
				areaGroup = (AreaObj) data.getExtras().getSerializable("areaGroup");

				areaFlow = data.getExtras().getString("areaFlow");
				areaHomeNumber = data.getExtras().getString("areaHomeNumber");

				address = new StringBuilder();

				if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
					address.append(areaHomeNumber).append(" - ");
				}
				if (areaFlow != null && areaFlow.length() > 0) {
					address.append(areaFlow).append(" - ");
				}
				if (areaGroup != null && areaGroup.getName() != null && areaGroup.getName().length() > 0) {
					address.append(areaGroup.getName()).append(" - ");
				}
				if (areaPrecint != null && areaPrecint.getName() != null && areaPrecint.getName().length() > 0) {

					address.append(areaPrecint.getName()).append(" - ");
				}
				if (areaDistrist != null && areaDistrist.getName() != null && areaDistrist.getName().length() > 0) {

					address.append(areaDistrist.getName()).append(" - ");
				}
				if (areaProvicial != null && areaProvicial.getName() != null && areaProvicial.getName().length() > 0) {

					address.append(areaProvicial.getName());
				}
				Log.d("Log", "Check edit address null: " + edtAddress + "adess :" + address);
				edtAddress.setText(address);
			}
		}
	}

	private void createChanel() {
		Dialog dialogError = null;
		String mDateRange = edtDateRange.getText().toString().trim();
		// String birthDay = edtBirthDay.getText().toString().trim();
		int index = 0;
		if (mListContractType.size() > 0) {
			index = spContractType.getSelectedItemPosition();
			Log.d("Log", "index selected: " + index);
		}

		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = activity.getCurrentFocus();
		if (view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}

		try {
			issueDate = DateTimeUtils.convertStringToTime(mDateRange, "dd/MM/yyyy");
			// birthDate = DateTimeUtils.convertStringToTime(birthDay,
			// "dd/MM/yyyy");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String phoneNumber = edtPhoneNumber.getText().toString().trim();
		String numberCMT = edtIdNumber.getText().toString().trim();

		if (mListBlankStaff == null || mListBlankStaff.size() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_blank_staff),
					getResources().getString(R.string.app_name));
		} else if (edtChanelName.getText().toString().trim().length() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_name_chanel),
					getResources().getString(R.string.app_name));
			edtChanelName.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			mScrollView.scrollTo(0, edtChanelName.getBottom());
		} else if (phoneNumber.length() == 0 || !CommonActivity.validateIsdn(phoneNumber)) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_phone_number),
					getResources().getString(R.string.app_name));
			edtPhoneNumber.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			mScrollView.scrollTo(0, edtPhoneNumber.getBottom());
		} else if (index != 2 && (numberCMT.length() == 0 || (numberCMT.length() != 9 && numberCMT.length() != 12))) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_idNumber),
					getResources().getString(R.string.app_name));
			edtIdNumber.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			mScrollView.scrollTo(0, edtIdNumber.getBottom());
		} else if (index != 2 && mDateRange.length() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_date_give),
					getResources().getString(R.string.app_name));
			edtDateRange.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			mScrollView.scrollTo(0, edtDateRange.getBottom());
		} else if (index != 2 && issueDate.after(dateNow)) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.ngaycapnhohonngayhientai),
					getResources().getString(R.string.app_name));
		}
		// else if (birthDay.length() == 0) {
		// dialogError = CommonActivity.createAlertDialog(activity,
		// getResources().getString(R.string.message_pleass_input_birth_day),
		// getResources().getString(R.string.app_name));
		// }
		else if (index != 2 && edtPlaceGive.getText().toString().trim().length() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_place_give),
					getResources().getString(R.string.app_name));
			edtPlaceGive.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			mScrollView.scrollTo(0, edtPlaceGive.getBottom());
		} else if (areaProvicial == null) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_province),
					getResources().getString(R.string.app_name));
		} else if (areaDistrist == null) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_distrist),
					getResources().getString(R.string.app_name));
		} else if (areaPrecint == null) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_precint),
					getResources().getString(R.string.app_name));
		} else if (areaGroup == null) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_group),
					getResources().getString(R.string.app_name));
		} else if (areaFlow == null || areaFlow.length() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_street),
					getResources().getString(R.string.app_name));
		} else if (areaHomeNumber == null || areaHomeNumber.length() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_home),
					getResources().getString(R.string.app_name));
		} else if (edtAddress.getText().toString().trim().length() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_province),
					getResources().getString(R.string.app_name));
		} else if (edtTaxCode.getText().toString().length() > 0
				&& StringUtils.CheckCharSpecical(edtTaxCode.getText().toString().trim())) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_input_taxcode_error),
					getResources().getString(R.string.app_name));
			edtTaxCode.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			mScrollView.scrollTo(0, edtTaxCode.getBottom());
		} else if (mListTypeOffSale == null || mListTypeOffSale.size() == 0) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.message_pleass_input_sell_type),
					getResources().getString(R.string.app_name));
		}

		if (dialogError != null) {
			dialogError.show();
			return;
		}

		CommonActivity.createDialog(activity, getString(R.string.message_confirm_update), getString(R.string.app_name),
				getString(R.string.say_ko), getString(R.string.say_co), null, updateConfirmCallBack).show();
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
		case R.id.btnSaveInfo: {
			createChanel();

		}

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
				GetListBlankStaffAsynctask getListBlackStaffAsynctask = new GetListBlankStaffAsynctask(activity, 2);
				getListBlackStaffAsynctask.execute();
			}
		}
	};

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			MainActivity.getInstance().finish();
            String permission = Constant.VSAMenu.MENU_CREATE_CHANNEL;
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
			dialog.show();

		}
	};

	private class GetListBlankStaffAsynctask extends AsyncTask<Void, Void, Void> {

		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final int typeRequest;

		public GetListBlankStaffAsynctask(Context context, int typeRequest) {
			this.typeRequest = typeRequest;
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			if (typeRequest == 1) {
				this.progress.setMessage(context.getResources().getString(R.string.getdatablankstaff));
			} else {
				this.progress.setMessage(context.getResources().getString(R.string.loading_updating));
			}
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			if (typeRequest == 1) {
				getListBlackStaff();
			} else if (typeRequest == 2) {
				updateBlankChanel();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			this.progress.dismiss();
			Log.d("onPostExecute", "value errorCode" + errorCode);
			if (errorCode.equals("0")) {

				switch (typeRequest) {
				case 1: // lấy list mã trắng
				{
					if (mListBlankStaff != null && mListBlankStaff.size() > 0) {
						Log.d("onPostExecute", "size result: " + mListBlankStaff.size());
						adapterBlankStaff = new BlankWhiteAdapter(mListBlankStaff, activity); // AdapterProvinceSpinner(mListBlankStaff,
																								// activity);
						spInfoChanel.setAdapter(adapterBlankStaff);
						spInfoChanel.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
								Log.d("FragmentCreateChannel", "Selection spinner onItemSelected");
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								Log.d("FragmentCreateChannel", "Selection spinner onNothingSelected");
							}
						});
					} else {
						CommonActivity.createAlertDialog(getActivity(), getString(R.string.ko_co_dl_blank_staff),
								getString(R.string.app_name)).show();
					}
				}
					break;
				case 2: // cap nhap kenh trang
				{

					CommonActivity
							.createAlertDialog(getActivity(), getString(R.string.message_update_blank_white_success),
									getString(R.string.app_name), updateBlackSuccessBlock)
							.show();

				}
					break;

				default:
					break;
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		final OnClickListener updateBlackSuccessBlock = new OnClickListener() {

			@Override
			public void onClick(View v) {
				refreshData();
			}
		};

		public void refreshData() {

			edtChanelName.setText("");
			edtIdNumber.setText("");
			edtDateRange.setText("");
			edtBirthDay.setText("");

			edtPlaceGive.setText("");
			edtTaxCode.setText("");
			edtPhoneNumber.setText("");
			edtAddress.setText("");

			areaProvicial = null;
			areaDistrist = null;
			areaPrecint = null;
			areaGroup = null;

			areaFlow = null;
			areaHomeNumber = null;
			address = null;
			addListItemSpinner();
			getListDataBlackStaffFromServer();

		}

		public void getListBlackStaff() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLstBlankStaffBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstBlankStaff>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getLstBlankStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_getLstBlankStaffBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Response Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstStaffDTOs");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						AreaObj areaObject = new AreaObj();
						areaObject.setName(parse.getValue(e1, "channelTypeName"));
						areaObject.setAreaCode(parse.getValue(e1, "staffCode"));
						mListBlankStaff.add(areaObject);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
		}

		public void updateBlankChanel() {

			int index = 0;
			if (mListContractType.size() > 0) {
				index = spContractType.getSelectedItemPosition();
				Log.d("Log", "index selected: " + index);
			}

			AreaObj areaInfoChanelObject = mListBlankStaff.get(spInfoChanel.getSelectedItemPosition());// (AreaObj)spInfoChanel.getSelectedItem();
			AreaObj areaTypeContractObject = mListContractType.get(spContractType.getSelectedItemPosition());
			AreaObj areaPositionsaleObject = mListPositionSell.get(spPositionSell.getSelectedItemPosition());
			AreaObj areasaleTypeObject = mListTypeOffSale.get(spTypeOfSale.getSelectedItemPosition());

			String mDateRange = edtDateRange.getText().toString().trim();
			// String birthDay = edtBirthDay.getText().toString().trim();
			String phoneNumber = CommonActivity.checkStardardIsdn(edtPhoneNumber.getText().toString().trim());

			Log.d("Log", "check phone number" + phoneNumber);

			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateStaffBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateStaff>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				if (index != 2) {
					rawData.append("<issueDate>").append(mDateRange).append("</issueDate>");

				}
				rawData.append("<isCreate>" + "true" + "</isCreate>");

				// rawData.append("<birthDate>" + birthDay + "</birthDate>");
				rawData.append("<staff>");
				rawData.append("<staffCode>").append(areaInfoChanelObject.getAreaCode()).append("</staffCode>");
				rawData.append("<name>").append(edtChanelName.getText().toString().trim()).append("</name>");
				rawData.append("<businessMethod>").append(areaPositionsaleObject.getAreaCode()).append("</businessMethod>");
				rawData.append("<contractMethod>").append(areaTypeContractObject.getAreaCode()).append("</contractMethod>");

				if (index != 2) {
					rawData.append("<idNo>").append(edtIdNumber.getText().toString().trim()).append("</idNo>");
					rawData.append("<idIssuePlace>").append(edtPlaceGive.getText().toString().trim()).append("</idIssuePlace>");
				}

				// rawData.append("<staffId>" +
				// edtPlaceGive.getText().toString().trim() + "</staffId>");

				rawData.append("<home>").append(areaHomeNumber).append("</home>");
				rawData.append("<district>").append(areaDistrist.getDistrict()).append("</district>");
				rawData.append("<precinct>").append(areaPrecint.getPrecinct()).append("</precinct>");
				rawData.append("<province>").append(areaProvicial.getProvince()).append("</province>");
				rawData.append("<street>").append(areaFlow).append("</street>");
				rawData.append("<streetBlock>").append(areaGroup.getAreaCode()).append("</streetBlock>");
				rawData.append("<address>").append(edtAddress.getText().toString().trim()).append("</address>");

				rawData.append("<isdn>").append(phoneNumber).append("</isdn>");
				rawData.append("<tel>").append(phoneNumber).append("</tel>");
				rawData.append("<tin>").append(edtTaxCode.getText().toString().trim()).append("</tin>");
				rawData.append("<pointOfSaleType>").append(areasaleTypeObject.getAreaCode()).append("</pointOfSaleType>");

				rawData.append("</staff>");
				rawData.append("</input>");
				rawData.append("</ws:updateStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_updateStaff");
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
					nodechild = doc.getElementsByTagName("lstArea");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						AreaObj areaObject = new AreaObj();
						areaObject.setName(parse.getValue(e1, "name"));
						Log.d("name area: ", areaObject.getName());
						areaObject.setAreaCode(parse.getValue(e1, "areaCode"));
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

		}
	}

}
