package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

/**
 * 
 * @author ThinhHQ1 CONNECTION Đấu nối di động trả trước, trả sau
 * 
 */
@SuppressLint("SimpleDateFormat")
public class FragmentInfoCustomerMobile extends Fragment  {
//
//	private final String tag = FragmentInfoCustomerMobile.class.getName();
//	private Activity activity;
//
//	// Define View
//	private Spinner spinner_typethuebao, spinner_service, spinner_type_giay_to;
//	private EditText edit_isdnacount, edit_sogiayto;
//    private ExpandableHeightListView lvCustomer;
//
//	// Define View Dialog
//	private EditText edit_tenKH;
//	private EditText edit_socmnd;
//	private EditText edit_ngaycap;
//	private EditText editngayhethan;
//	private EditText edit_noicap;
//	private EditText edit_ngaysinh;
//	// private Spinner spinner_loaikh;
//
//	private EditText edtloaikh;
//
//	// private Spinner spinner_province;
//	// private Spinner spinner_district;
//	// private Spinner spinner_precint;
//
//	private EditText edtprovince, edtdistrict, edtprecinct;
//	private EditText edtStreetBlock;
//	private EditText edt_streetName;
//	private EditText edtHomeNumber;
//	private String streetBlock = "";
//
//	private Spinner spinner_sex;
//	private LinearLayout ln_sex;
//	private Dialog dialogInsertNew;
//
//	// Defile Insert New Info Parent
//	private Spinner spinner_loaigiayto;
//	private EditText edit_tendaidien_p;
//	private EditText edit_socmnd_p;
//	private EditText edit_ngaysinh_p;
//	private EditText edit_ngaycap_p;
//	private EditText edit_noicap_p;
//
//	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<>();
//	private ArrayList<CustommerByIdNoBean> arrCustommerByIdNoBeans = new ArrayList<>();
//	private GetListCusMobileEditableAdapter adaGetListCusMobileAdapter;
//
//	// get list Sub
//	private final ArrayList<SubTypeBean> arrSubTypeBeans = new ArrayList<>();
//
//	// === input for ws get list customer
//	private String serviceType = "";
//	private String idsnOrAcc = "";
//	private String numberPaper = "";
//
//	public static String subType = "";
//	private String busType = "";
//	private String cusType = "";
//	private String busPermitNo = "";
//
//	public static CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
//	private View mView = null;
//
//	// ========ds loai kh tra truoc ==================
//	private ArrayList<BusTypeBean> arrBusTypeBeans = new ArrayList<>();
//
//	// =======ds loai kh tra sau ================
//	private ArrayList<BusTypePreBean> arrBusTypePreBeans = new ArrayList<>();
//
//	// arrlist province
//	private ArrayList<AreaBean> arrProvince = new ArrayList<>();
//	private String province = "";
//	// arrlist district
//	private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
//	private String district = "";
//	// arrlist precinct
//	private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
//	private String precinct = "";
//
//	private ArrayList<TypePaperBeans> arrTypePaperBeans = new ArrayList<>();
//	private String idType = "";
//
//	private final ArrayList<SexBeans> arrSexBeans = new ArrayList<>();
//	// private String sex = "";
//
//	private EditText edit_sogpkd;
//
//    private String dateNowString;
//
//	private final ArrayList<Spin> lstReason = new ArrayList<>();
//	private final ArrayList<Spin> lstReasonPre = new ArrayList<>();
//	private Spinner spn_reason_fail;
//    private CustommerByIdNoBean custommerByIdNoBeanEdit;
//	private LinearLayout linearsoGPKD;
//	private EditText edit_soGQ;
//	private LinearLayout linearCMT;
//	private LinearLayout ln_tin;
//	private EditText edt_tin;
//	private LinearLayout lnsogiayto;
//	private String reasonId;
//	private EditText edtOTPIsdn, edtOTPCode;
//    private Button btnSendOTP;
//	private ProgressBar prbreasonBtn;
//	private String otp = "";
//	private String isdnSendOtp = "";
//	private boolean permissionChangeInfoCustNoSubActive = false;
//	private boolean permissionChangeInfoCustHaveSubActive = false;
//	private String custId = "";
//	private Dialog dialogEditCustomer;
//	private Button btnEdit;
//	private ProgressBar prbStreetBlock;
//
//
//    // them thong tin ghi chu
//	private EditText edtnote;
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		this.activity = activity;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		Calendar calendar = Calendar.getInstance();
//
//		int fromYear = calendar.get(Calendar.YEAR);
//		int fromMonth = calendar.get(Calendar.MONTH) + 1;
//		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//		calendar.add(Calendar.DATE, 1);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		try {
//            Date dateNow = calendar.getTime();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
//
//		if (mView == null) {
//			mView = inflater.inflate(
//					R.layout.connectionmobile_layout_info_customer, container,
//					false);
//
//			UnitView(mView);
//		} else {
//			((ViewGroup) mView.getParent()).removeAllViews();
//		}
//
//		return mView;
//	}
//
//	@Override
//	public void onResume() {
//		Log.e(tag, "onResume");
//
//		if (adaGetListCusMobileAdapter != null
//				&& arrCustommerByIdNoBeans.size() > 0) {
//			// adaGetListCusMobileAdapter = new GetListCusMobileAdapter(
//			// arrCustommerByIdNoBeans, activity);
//			adaGetListCusMobileAdapter = new GetListCusMobileEditableAdapter(
//					activity, arrCustommerByIdNoBeans, imageListenner);
//			lvCustomer.setAdapter(adaGetListCusMobileAdapter);
//		}
//		MainActivity.getInstance().setTitleActionBar(R.string.servicemobile);
//		super.onResume();
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == Activity.RESULT_OK) {
//			switch (requestCode) {
//			case 104:
//				if (data != null) {
//					BusTypePreBean busTypePreBeansKey = (BusTypePreBean) data
//							.getExtras().getSerializable("busTypePreBeansKey");
//					busType = busTypePreBeansKey.getBusType();
//					cusType = busTypePreBeansKey.getCusType();
//
//					edtloaikh.setText(busTypePreBeansKey.getName());
//
//					Log.d(Constant.TAG,
//							"spinner_loaikh onItemSelected Tra Truoc subType: "
//									+ subType + " busType: " + busType
//									+ " cusType: " + cusType);
//					if (!CommonActivity.isNullOrEmpty(cusType)
//							&& "1".equals(cusType)) {
//						linearCMT.setVisibility(View.GONE);
//						linearsoGPKD.setVisibility(View.VISIBLE);
//						lnsogiayto.setVisibility(View.GONE);
//						ln_tin.setVisibility(View.VISIBLE);
//						ln_sex.setVisibility(View.GONE);
//					} else {
//						linearCMT.setVisibility(View.VISIBLE);
//						linearsoGPKD.setVisibility(View.GONE);
//						lnsogiayto.setVisibility(View.VISIBLE);
//						ln_tin.setVisibility(View.GONE);
//						ln_sex.setVisibility(View.VISIBLE);
//					}
//				}
//				break;
//
//			case 105:
//				if (data != null) {
//
//					BusTypeBean busTypeBeansKey = (BusTypeBean) data
//							.getExtras().getSerializable("busTypeBeansKey");
//					edtloaikh.setText(busTypeBeansKey.getName());
//					busType = busTypeBeansKey.getBusType();
//					cusType = busTypeBeansKey.getCusType();
//					Log.d(Constant.TAG,
//							"spinner_loaikh onItemSelected Tra Sau subType: "
//									+ subType + " busType: " + busType
//									+ " cusType: " + cusType);
//
//					if (!CommonActivity.isNullOrEmpty(cusType)
//							&& "1".equals(cusType)) {
//						linearCMT.setVisibility(View.GONE);
//						linearsoGPKD.setVisibility(View.VISIBLE);
//						lnsogiayto.setVisibility(View.GONE);
//						ln_tin.setVisibility(View.VISIBLE);
//						ln_sex.setVisibility(View.GONE);
//					} else {
//						linearCMT.setVisibility(View.VISIBLE);
//						linearsoGPKD.setVisibility(View.GONE);
//						lnsogiayto.setVisibility(View.VISIBLE);
//						ln_tin.setVisibility(View.GONE);
//						ln_sex.setVisibility(View.VISIBLE);
//					}
//				}
//				break;
//
//			case 106:
//				if (data != null) {
//					AreaBean areaBean = (AreaBean) data.getExtras()
//							.getSerializable("provinceKey");
//
//					province = areaBean.getProvince();
//					initDistrict(province);
//					edtprovince.setText(areaBean.getNameProvince());
//					edtdistrict.setText("");
//					edtprecinct.setText("");
//					edtStreetBlock.setText("");
//				}
//				break;
//			case 107:
//				if (data != null) {
//					AreaBean areaBean = (AreaBean) data.getExtras()
//							.getSerializable("districtKey");
//					district = areaBean.getDistrict();
//					initPrecinct(province, district);
//					edtdistrict.setText(areaBean.getNameDistrict());
//					edtprecinct.setText("");
//					edtStreetBlock.setText("");
//				}
//				break;
//
//			case 108:
//				if (data != null) {
//					AreaBean areaBean = (AreaBean) data.getExtras()
//							.getSerializable("precinctKey");
//					precinct = areaBean.getPrecinct();
//					edtprecinct.setText(areaBean.getNamePrecint());
//					edtStreetBlock.setText("");
//				}
//				break;
//			case 109:
//				if (data != null) {
//					AreaObj streetBlockKey = (AreaObj) data.getExtras()
//							.getSerializable("streetBlockKey");
//					streetBlock = streetBlockKey.getAreaCode();
//					edtStreetBlock.setText(streetBlockKey.getName());
//				}
//				break;
//			default:
//				break;
//
//			}
//		}
//
//	}
//
//	// UnitView
//	private void UnitView(View v) {
//		removeCus();
//		spinner_typethuebao = (Spinner) v
//				.findViewById(R.id.spinner_typethuebao);
//		spinner_service = (Spinner) v.findViewById(R.id.spinner_service);
//		edit_isdnacount = (EditText) v.findViewById(R.id.edit_isdnacount);
//		edit_sogiayto = (EditText) v.findViewById(R.id.edit_sogiayto);
//        Button btnsearch = (Button) v.findViewById(R.id.btnsearch);
//		btnsearch.setOnClickListener(this);
//        Button btnnhapmoi = (Button) v.findViewById(R.id.btnnhapmoimobile);
//		btnnhapmoi.setOnClickListener(this);
//		lvCustomer = (ExpandableHeightListView) v
//				.findViewById(R.id.listcustomer);
//		lvCustomer.setExpanded(true);
//		lvCustomer.setOnItemClickListener(this);
//
//		edit_sogpkd = (EditText) v.findViewById(R.id.edit_sogpkd);
//
//		// init service
//		initTelecomService();
//
//		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
//			arrSubTypeBeans.clear();
//		}
//		initSub();
//
//		spinner_service.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				if (arg2 > 0) {
//					serviceType = arrTelecomServiceBeans.get(arg2)
//							.getServiceAlias();
//				} else {
//					serviceType = "";
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
//
//		spinner_typethuebao
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//						reloadData();
//						subType = arrSubTypeBeans.get(arg2).getValue();
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//
//					}
//				});
//
//		SharedPreferences preferences = getActivity().getSharedPreferences(
//				Define.PRE_NAME, Activity.MODE_PRIVATE);
//		String name = preferences.getString(Define.KEY_MENU_NAME, "");
//		if (name.contains(";permissionChangeInfoCustNoSubActive;")) {
//			permissionChangeInfoCustNoSubActive = true;
//		}
//		if (name.contains(";permissionChangeInfoCustHaveSubActive;")) {
//			permissionChangeInfoCustHaveSubActive = true;
//		}
//
//		// tuantd7();
//	}
//
//	private void tuantd7() {
//		// if (Session.loginUser != null &&
//		// "tuantm".equalsIgnoreCase(Session.loginUser.getStaffCode()
//		// .toLowerCase())) {
//		// edit_sogpkd.setText("testqq");
//		edit_sogiayto.setText("111222444");
//		// }
//	}
//
//	private void showPopupInsertInfoParent(final Integer position) {
//		final Dialog dialog = new Dialog(activity);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(R.layout.connection_layout_insert_info_parent);
//		dialog.setCancelable(false);
//
//		spinner_loaigiayto = (Spinner) dialog
//				.findViewById(R.id.spinner_loaigiayto);
//		edit_tendaidien_p = (EditText) dialog
//				.findViewById(R.id.edit_tendaidien);
//
//		edit_socmnd_p = (EditText) dialog.findViewById(R.id.edit_socmnd);
//		edit_ngaysinh_p = (EditText) dialog.findViewById(R.id.edit_ngaysinh);
//		edit_ngaycap_p = (EditText) dialog.findViewById(R.id.edit_ngaycap);
//		edit_noicap_p = (EditText) dialog.findViewById(R.id.edit_noicap);
//
//		Button btnthemmoi = (Button) dialog.findViewById(R.id.btnthemmoi);
//		Button btncancel = (Button) dialog.findViewById(R.id.btncancel);
//
//		ArrayAdapter<String> adapterTypePaper = initTypePaper();
//		spinner_loaigiayto.setAdapter(adapterTypePaper);
//
//		edit_ngaysinh_p.setOnClickListener(editTextListener);
//		edit_ngaycap_p.setOnClickListener(editTextListener);
//
//		CustommerByIdNoBean obj = arrCustommerByIdNoBeans.get(position);
//		CustomerAttribute customerAttribute = obj.getCustomerAttribute();
//		if (customerAttribute != null) {
//			edit_tendaidien_p.setText(customerAttribute.getName());
//			edit_socmnd_p.setText(customerAttribute.getIdNo());
//			edit_ngaysinh_p.setText(customerAttribute.getBirthDate());
//			edit_noicap_p.setText(customerAttribute.getIssuePlace());
//			edit_ngaycap_p.setText(customerAttribute.getIssueDate());
//		}
//
//		btnthemmoi.setTag(position);
//		btnthemmoi.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// ws check kh ton tai hay chua
//				boolean valid = true;
//
//				int spinner_loaigiayto_position = spinner_loaigiayto
//						.getSelectedItemPosition();
//				String customerAttributeIdType = arrTypePaperBeans.get(
//						spinner_loaigiayto_position).getParType();
//
//				if (StringUtils.CheckCharSpecical(edit_tendaidien_p.getText()
//                        .toString())) {
//					Toast.makeText(
//							activity,
//							getResources()
//									.getString(R.string.checkcharspecical),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edit_tendaidien_p.getText()
//						.toString().trim())) {
//					valid = false;
//					edit_tendaidien_p.requestFocus();
//					Toast.makeText(activity, getString(R.string.checknameKH),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edit_socmnd_p.getText()
//						.toString().trim())) {
//					valid = false;
//					edit_socmnd_p.requestFocus();
//					Toast.makeText(activity, getString(R.string.checkcmt),
//							Toast.LENGTH_LONG).show();
//				} else if (StringUtils.CheckCharSpecical(edit_socmnd_p
//                        .getText().toString())) {
//					valid = false;
//					edit_socmnd_p.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.checkcharspecical),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edit_ngaysinh_p.getText()
//						.toString().trim())) {
//					valid = false;
//					edit_ngaysinh_p.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.message_pleass_input_birth_day),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edit_noicap_p.getText()
//						.toString().trim())) {
//					valid = false;
//					edit_noicap_p.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.chua_nhap_noi_cap),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edit_ngaycap_p.getText()
//						.toString().trim())) {
//					valid = false;
//					edit_ngaycap_p.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.message_pleass_input_date_give),
//							Toast.LENGTH_LONG).show();
//				} else if ("1".equalsIgnoreCase(customerAttributeIdType)
//						&& (edit_socmnd_p.getText().toString().trim().length() != 9 && edit_socmnd_p
//								.getText().toString().trim().length() != 12)) {
//					valid = false;
//					edit_socmnd_p.requestFocus();
//					Toast.makeText(activity, getString(R.string.checksoidno),
//							Toast.LENGTH_LONG).show();
//				} else {
//					try {
//						Date birthDate = sdf.parse(edit_ngaysinh_p.getText()
//								.toString());
//						Date issueDate = sdf.parse(edit_ngaycap_p.getText()
//								.toString());
//
//						Calendar cal = Calendar.getInstance();
//						int year = cal.get(Calendar.YEAR);
//						int month = cal.get(Calendar.MONTH);
//						int day = cal.get(Calendar.DAY_OF_MONTH);
//						cal.set(year, month, day);
//
//						Date current = cal.getTime();
//
//						cal.add(Calendar.YEAR, -Constant.TEEN);
//						Date date14 = cal.getTime();
//
//						if (!birthDate.before(issueDate)) {
//							valid = false;
//							Toast.makeText(activity,
//									getString(R.string.checkcmtngaycap),
//									Toast.LENGTH_LONG).show();
//						} else if (!birthDate.before(date14)) {
//							valid = false;
//							Toast.makeText(activity,
//									getString(R.string.check14Plus),
//									Toast.LENGTH_LONG).show();
//						} else if (!issueDate.before(current)) {
//							valid = false;
//							Toast.makeText(
//									activity,
//									getString(R.string.ngaycapnhohonngayhientai),
//									Toast.LENGTH_LONG).show();
//						}
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//				}
//
//				if (valid) {
//					CustomerAttribute customerAttribute = new CustomerAttribute();
//
//					customerAttribute.setIdType(customerAttributeIdType);
//					customerAttribute.setIdNo(edit_socmnd_p.getText()
//							.toString());
//					customerAttribute.setName(edit_tendaidien_p.getText()
//							.toString());
//					customerAttribute.setBirthDate(edit_ngaysinh_p.getText()
//							.toString());
//					customerAttribute.setIssuePlace(edit_noicap_p.getText()
//							.toString());
//					customerAttribute.setIssueDate(edit_ngaycap_p.getText()
//							.toString());
//
//					int position = (Integer) v.getTag();
//					arrCustommerByIdNoBeans.get(position).setCustomerAttribute(
//							customerAttribute);
//
//					dialog.dismiss();
//
//					setCurrentTabConnection(position);
//				}
//			}
//		});
//
//		btncancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				dialog.dismiss();
//			}
//		});
//
//		dialog.show();
//	}
//
//	private void setCurrentTabConnection(int position) {
//		// CHUYEN SANG MAN HINH DAU NOI
//		for (CustommerByIdNoBean item : arrCustommerByIdNoBeans) {
//			item.setIscheckCus(false);
//		}
//		arrCustommerByIdNoBeans.get(position).setIscheckCus(true);
//		adaGetListCusMobileAdapter.notifyDataSetChanged();
//		for (CustommerByIdNoBean cusBean : arrCustommerByIdNoBeans) {
//			if (cusBean.isIscheckCus()) {
//				custommerByIdNoBean = cusBean;
//
//				Log.d(Constant.TAG, "setCurrentTabConnection subType: "
//						+ subType + " busType: " + busType
//						+ " custommerByIdNoBean.getCusType() "
//						+ custommerByIdNoBean.getCusType()
//						+ " custommerByIdNoBean.getBusPermitNo() "
//						+ custommerByIdNoBean.getBusPermitNo());
//
//				ActivityCreateNewRequestMobile.instance.tHost.setCurrentTab(1);
//				break;
//			}
//		}
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> adapterView, View view,
//			int position, long id) {
//		CustommerByIdNoBean obj = arrCustommerByIdNoBeans.get(position);
//		String busPermitNo = obj.getBusPermitNo();
//
//		Log.d(Constant.TAG,
//				"FragmentInfoCustomerMobile onItemClick() position: "
//						+ position + " busPermitNo: " + busPermitNo + " " + obj);
//
//		if (busPermitNo != null && !busPermitNo.isEmpty()
//				&& obj.getCustomerAttribute().getIdNo() == null) {
//			showPopupInsertInfoParent(position);
//		} else {
//			setCurrentTabConnection(position);
//		}
//	}
//
//	// TuanTD7
//	private void showPopupEditCustomer(final CustommerByIdNoBean customer) {
//		Log.d(this.getClass().getSimpleName(), "showPopupEditCustomer "
//				+ customer);
//
//		if (dialogEditCustomer == null) {
//			dialogEditCustomer = new Dialog(activity);
//			dialogEditCustomer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialogEditCustomer
//					.setContentView(R.layout.connectionmobile_layout_edit_customer_info);
//			dialogEditCustomer.setCancelable(true);
//			dialogEditCustomer.setTitle(R.string.edit_customer_mobile);
//		}
//
//		TextView tvTitle = (TextView) dialogEditCustomer
//				.findViewById(R.id.tvTitle);
//		tvTitle.setText(R.string.edit_customer_mobile);
//
//		final EditText edtnotesedit = (EditText) dialogEditCustomer.findViewById(R.id.edtnotesedit);
//		edtnotesedit.setText(customer.getNotes());
//		spn_reason_fail = (Spinner) dialogEditCustomer
//				.findViewById(R.id.spn_reason_fail);
//        ProgressBar prbreason = (ProgressBar) dialogEditCustomer
//                .findViewById(R.id.prbreason);
//		prbreason.setVisibility(View.GONE);
//
//		prbreasonBtn = (ProgressBar) dialogEditCustomer
//				.findViewById(R.id.prbreasonBtn);
//		prbreasonBtn.setVisibility(View.GONE);
//
//        LinearLayout lnOTP = (LinearLayout) dialogEditCustomer.findViewById(R.id.lnOTP);
//
//		if (customer.getRequestSendOTP() != null
//				&& "true".equalsIgnoreCase(customer.getRequestSendOTP())) {
//			lnOTP.setVisibility(View.VISIBLE);
//		} else {
//			lnOTP.setVisibility(View.GONE);
//		}
//
//		edtOTPIsdn = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtOTPIsdn);
//		edtOTPCode = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtOTPCode);
//		edtOTPIsdn.setText("");
//		edtOTPCode.setText("");
//		btnSendOTP = (Button) dialogEditCustomer.findViewById(R.id.btnSendOTP);
//		btnSendOTP.setVisibility(View.VISIBLE);
//		btnSendOTP.setEnabled(true);
//		btnSendOTP.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				String isdnOTP = edtOTPIsdn.getText().toString();
//				if (CommonActivity.isNullOrEmpty(isdnOTP)) {
//					CommonActivity.toast(activity, R.string.edtOTPIsdn_require);
//					edtOTPIsdn.requestFocus();
//				} else if (StringUtils.CheckCharSpecical(isdnOTP)) {
//					CommonActivity.toast(activity, R.string.edtOTPIsdn_spec);
//					edtOTPIsdn.requestFocus();
//				} else if (isdnOTP.length() < 9) {
//					CommonActivity.toast(activity, R.string.edtOTPIsdn_invalid);
//					edtOTPIsdn.requestFocus();
//				} else {
//					String message = String.format(
//							getString(R.string.sendOTP_confirm), isdnOTP);
//					CommonActivity.createDialog(activity, message,
//							getString(R.string.app_name),
//							getString(R.string.say_ko),
//							getString(R.string.say_co), null, confirmSendOTP)
//							.show();
//				}
//			}
//		});
//
//		if ("1".equalsIgnoreCase(subType)) {
//			// tra truoc
//			if (lstReasonPre != null && lstReasonPre.size() > 0) {
//				Utils.setDataSpinner(activity, lstReasonPre, spn_reason_fail);
//			} else {
//				AsyntaskGetReasonPre async = new AsyntaskGetReasonPre(activity,
//						"152", prbreason, spn_reason_fail);
//				async.execute();
//			}
//		} else {
//			// tra sau
//			if (lstReason != null && lstReason.size() > 0) {
//				Utils.setDataSpinner(activity, lstReason, spn_reason_fail);
//			} else {
//				AsyntaskGetReasonPos async = new AsyntaskGetReasonPos(activity,
//						"04", prbreason, spn_reason_fail);
//				async.execute();
//			}
//		}
//
//		final LinearLayout lnngayhethan = (LinearLayout) dialogEditCustomer
//				.findViewById(R.id.lnngayhethan);
//		editngayhethan = (EditText) dialogEditCustomer
//				.findViewById(R.id.edit_ngayhethan);
//		editngayhethan.setText(customer.getIdExpireDate());
//
//		edtloaikh = (EditText) dialogEditCustomer.findViewById(R.id.edtloaikh);
//		edtloaikh.setText(customer.getCusType());
//		edtloaikh.setEnabled(false);
//		busType = customer.getCusType();
//
//		edit_tenKH = (EditText) dialogEditCustomer
//				.findViewById(R.id.edit_tenKH);
//		edit_tenKH.setText(customer.getNameCustomer());
//		edit_socmnd = (EditText) dialogEditCustomer
//				.findViewById(R.id.edit_socmnd);
//		edit_socmnd.setText(customer.getIdNo());
//		edit_socmnd.setEnabled(false);
//		edit_ngaycap = (EditText) dialogEditCustomer
//				.findViewById(R.id.edit_ngaycap);
//		edit_ngaycap.setText(customer.getIdIssueDate());
//		edit_noicap = (EditText) dialogEditCustomer
//				.findViewById(R.id.edit_noicap);
//		edit_noicap.setText(customer.getIdIssuePlace());
//		edit_ngaysinh = (EditText) dialogEditCustomer
//				.findViewById(R.id.edit_ngaysinh);
//		edit_ngaysinh.setText(customer.getBirthdayCus());
//
//		initProvince();
//
//		edtprovince = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtprovince);
//		if (!CommonActivity.isNullOrEmpty(customer.getProvince())) {
//			initDistrict(customer.getProvince());
//			try {
//				GetAreaDal dal = new GetAreaDal(activity);
//				dal.open();
//				edtprovince
//						.setText(dal.getNameProvince(customer.getProvince()));
//				province = customer.getProvince();
//				dal.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		edtprovince.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				intent.putExtra("arrProvincesKey", arrProvince);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "1");
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 106);
//			}
//		});
//
//		edtdistrict = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtdistrict);
//		if (!CommonActivity.isNullOrEmpty(customer.getProvince())
//				&& !CommonActivity.isNullOrEmpty(customer.getDistrict())) {
//			initPrecinct(customer.getProvince(), customer.getDistrict());
//			try {
//				GetAreaDal dal = new GetAreaDal(activity);
//				dal.open();
//				edtdistrict.setText(dal.getNameDistrict(customer.getProvince(),
//						customer.getDistrict()));
//				district = customer.getDistrict();
//				dal.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		edtdistrict.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				intent.putExtra("arrDistrictKey", arrDistrict);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "2");
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 107);
//			}
//		});
//
//		edtprecinct = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtprecinct);
//		if (!CommonActivity.isNullOrEmpty(customer.getPrecint())) {
//			for (AreaBean areaBean : arrPrecinct) {
//				if (customer.getPrecint().equalsIgnoreCase(
//						areaBean.getPrecinct())) {
//					edtprecinct.setText(areaBean.getNamePrecint());
//					precinct = customer.getPrecint();
//					break;
//				}
//			}
//		}
//
//		edtprecinct.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				intent.putExtra("arrPrecinctKey", arrPrecinct);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "3");
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 108);
//			}
//		});
//
//		edtStreetBlock = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtStreetBlock); // to
//		edtStreetBlock.setText(customer.getStreet_block());
//		edt_streetName = (EditText) dialogEditCustomer
//				.findViewById(R.id.edt_streetName); // duong
//		edt_streetName.setText(customer.getStreet());
//		edtHomeNumber = (EditText) dialogEditCustomer
//				.findViewById(R.id.edtHomeNumber); // so nha
//		edtHomeNumber.setText(customer.getHome());
//
//		edtStreetBlock.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "4");
//				mBundle.putString("province", province);
//				mBundle.putString("district", district);
//				mBundle.putString("precinct", precinct);
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 109);
//			}
//		});
//
//		prbStreetBlock = (ProgressBar) dialogEditCustomer
//				.findViewById(R.id.prbStreetBlock);
//		prbStreetBlock.setVisibility(View.GONE);
//
//        Button btnRefreshStreetBlock = (Button) dialogEditCustomer
//                .findViewById(R.id.btnRefreshStreetBlock);
//		btnRefreshStreetBlock.setVisibility(View.VISIBLE);
//		btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				new CacheDatabaseManager(MainActivity.getInstance())
//						.insertCacheStreetBlock(null,
//								customer.getProvince() + customer.getDistrict()
//										+ customer.getPrecint());
//				GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//						activity, prbStreetBlock, customer.getStreet_block());
//				async.execute(customer.getProvince() + customer.getDistrict()
//						+ customer.getPrecint());
//
//			}
//		});
//		if (!CommonActivity.isNullOrEmpty(customer.getProvince())
//				&& !CommonActivity.isNullOrEmpty(customer.getDistrict())
//				&& !CommonActivity.isNullOrEmpty(customer.getPrecint())
//				&& !CommonActivity.isNullOrEmpty(customer.getStreet_block())) {
//			streetBlock = customer.getStreet_block();
//			GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//					activity, prbStreetBlock, customer.getStreet_block());
//			async.execute(customer.getProvince() + customer.getDistrict()
//					+ customer.getPrecint());
//		}
//
//		spinner_type_giay_to = (Spinner) dialogEditCustomer
//				.findViewById(R.id.spinner_type_giay_to);
//		spinner_sex = (Spinner) dialogEditCustomer
//				.findViewById(R.id.spinner_gioitinh);
//		ln_sex = (LinearLayout) dialogEditCustomer.findViewById(R.id.ln_sex);
//		// bo sung giay phep kinh doanh cho khach hang doanh nghiep
//		linearsoGPKD = (LinearLayout) dialogEditCustomer
//				.findViewById(R.id.linearsoGPKD);
//		linearCMT = (LinearLayout) dialogEditCustomer
//				.findViewById(R.id.linearCMT);
//		edit_soGQ = (EditText) dialogEditCustomer.findViewById(R.id.edit_soGQ);
//		edit_soGQ.setText(customer.getBusPermitNo());
//		edit_soGQ.setEnabled(false);
//		ln_tin = (LinearLayout) dialogEditCustomer.findViewById(R.id.ln_tin);
//		edt_tin = (EditText) dialogEditCustomer.findViewById(R.id.edt_tin);
//		edt_tin.setText(customer.getTin());
//		lnsogiayto = (LinearLayout) dialogEditCustomer
//				.findViewById(R.id.lnsogiayto);
//
//		if (customer.getBusPermitNo() == null
//				|| customer.getBusPermitNo().isEmpty()) {
//			// ca nhan
//			linearCMT.setVisibility(View.VISIBLE);
//			linearsoGPKD.setVisibility(View.GONE);
//			lnsogiayto.setVisibility(View.VISIBLE);
//			ln_tin.setVisibility(View.GONE);
//			ln_sex.setVisibility(View.VISIBLE);
//		} else {
//			// doanh nghiep
//			linearCMT.setVisibility(View.GONE);
//			linearsoGPKD.setVisibility(View.VISIBLE);
//			lnsogiayto.setVisibility(View.GONE);
//			ln_tin.setVisibility(View.VISIBLE);
//			ln_sex.setVisibility(View.GONE);
//		}
//
//		ArrayAdapter<String> adapterTypePaper = initTypePaper();
//		spinner_type_giay_to.setAdapter(adapterTypePaper);
//		if (!CommonActivity.isNullOrEmpty(customer.getIdType())) {
//			for (int i = 0; i < arrTypePaperBeans.size(); i++) {
//				TypePaperBeans typePaperBean = arrTypePaperBeans.get(i);
//				if (customer.getIdType().equalsIgnoreCase(
//						typePaperBean.getParType())) {
//					Log.d(this.getClass().getSimpleName(), customer.getIdType()
//							+ " == " + typePaperBean.getParType() + " i: " + i);
//					spinner_type_giay_to.setSelection(i);
//					spinner_type_giay_to.setSelected(true);
//					idType = customer.getIdType();
//
//					if ("3".equalsIgnoreCase(idType)) {
//						lnngayhethan.setVisibility(View.VISIBLE);
//					} else {
//						lnngayhethan.setVisibility(View.GONE);
//					}
//
//					break;
//				}
//			}
//		}
//		spinner_type_giay_to.setEnabled(false);
//
//		if (arrSexBeans != null && arrSexBeans.size() > 0) {
//			arrSexBeans.clear();
//		}
//		initSex();
//		if (!CommonActivity.isNullOrEmpty(customer.getSex())) {
//			for (int i = 0; i < arrSexBeans.size(); i++) {
//				SexBeans sexBean = arrSexBeans.get(i);
//				if (customer.getSex().equalsIgnoreCase(sexBean.getValues())) {
//					Log.d(this.getClass().getSimpleName(), customer.getSex()
//							+ " == " + sexBean.getValues() + " i: " + i);
//					spinner_sex.setSelection(i);
//					spinner_sex.refreshDrawableState();
//					break;
//				}
//			}
//		}
//
//		edit_ngaysinh.setOnClickListener(editTextListener);
//		edit_ngaycap.setOnClickListener(editTextListener);
//		editngayhethan.setOnClickListener(editTextListener);
//
//		Button btnCancel = (Button) dialogEditCustomer
//				.findViewById(R.id.btncancel);
//		btnCancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				dialogEditCustomer.dismiss();
//				// ActivityCreateNewRequest.instance.tHost.setCurrentTab(1);
//				// setCurrentTabConnection(positionCustomer);
//			}
//		});
//
//		btnEdit = (Button) dialogEditCustomer.findViewById(R.id.btnEdit);
//		btnEdit.setEnabled(true);
//		btnEdit.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				CustommerByIdNoBean obj = null;
//				// ws check kh ton tai hay chua
//				try {
//					if (validateCusGr()) {
//						if (CommonActivity.isNullOrEmpty(busType)) {
//							CommonActivity.toast(activity,
//									R.string.confirmloaikh);
//						} else if (edit_tenKH.getText().toString() == null
//								|| edit_tenKH.getText().toString().isEmpty()) {
//							CommonActivity
//									.toast(activity, R.string.checknameKH);
//							edit_tenKH.requestFocus();
//						} else if (StringUtils.CheckCharSpecical(edit_tenKH
//                                .getText().toString())) {
//							CommonActivity.toast(activity,
//									R.string.checkcharspecical);
//							edit_tenKH.requestFocus();
//						} else if (StringUtils.CheckCharSpecical(edit_soGQ
//                                .getText().toString())) {
//							CommonActivity.toast(activity,
//									R.string.checkcharspecical);
//							edit_soGQ.requestFocus();
//						} else if ("1".equalsIgnoreCase(cusType)
//								&& StringUtils.CheckCharSpecical(edit_socmnd
//                                .getText().toString())) {
//							CommonActivity.toast(activity,
//									R.string.checkcharspecical);
//							edit_socmnd.requestFocus();
//						} else if (edit_ngaysinh.getText().toString() == null
//								|| edit_ngaysinh.getText().toString().isEmpty()) {
//							CommonActivity.toast(activity,
//									R.string.message_pleass_input_birth_day);
//							edit_ngaysinh.requestFocus();
//						} else if (edit_ngaycap.getText().toString() == null
//								|| edit_ngaycap.getText().toString().isEmpty()) {
//							CommonActivity.toast(activity,
//									R.string.message_pleass_input_date_give);
//							edit_ngaycap.requestFocus();
//						} else if ("1".equalsIgnoreCase(cusType)
//								&& (edt_tin.getText().toString() == null || edt_tin
//										.getText().toString().isEmpty())) {
//							CommonActivity.toast(activity, R.string.checkmst);
//							edt_tin.requestFocus();
//						} else if ("1".equalsIgnoreCase(cusType)
//								&& StringUtils.CheckCharSpecical(edt_tin
//                                .getText().toString())) {
//							CommonActivity.toast(activity,
//									R.string.custin_special_character);
//							edt_tin.requestFocus();
//						} else {
//							if (!CommonActivity.isNullOrEmpty(edit_socmnd
//									.getText().toString())
//									|| !CommonActivity.isNullOrEmpty(edit_soGQ
//											.getText().toString())) {
//								if (!CommonActivity.isNullOrEmpty(edit_socmnd
//										.getText().toString())) {
//									if ("1".equals(idType)
//											&& "0".equals(cusType)) {
//										if (edit_socmnd.getText().toString()
//												.length() == 9
//												|| edit_socmnd.getText()
//														.toString().length() == 12) {
//											obj = validateCMT(customer);
//										} else {
//											CommonActivity.toast(activity,
//													R.string.checksoidno);
//										}
//									} else {
//										obj = validateCMT(customer);
//									}
//								} else {
//									obj = validateKHDN(customer);
//								}
//							} else {
//								if (CommonActivity.isNullOrEmpty(edit_socmnd
//										.getText().toString())) {
//									CommonActivity.toast(activity,
//											R.string.checkcmt);
//								} else {
//									CommonActivity.toast(activity,
//											R.string.checksogpkd);
//								}
//							}
//						}
//					}
//				} catch (ParseException e) {
//					Log.e(Constant.TAG, "btnEdit " + e.toString(), e);
//				}
//				if (obj != null) {
//					Spin spin = (Spin) spn_reason_fail.getSelectedItem();
//					reasonId = spin.getId();
//					String isdn = edtOTPIsdn.getText().toString();
//					if ("3".equalsIgnoreCase(idType)
//							&& CommonActivity.isNullOrEmpty(editngayhethan
//									.getText().toString())) {
//						CommonActivity.toast(activity,
//								R.string.editngayhethan_require);
//					} else if (spn_reason_fail.getSelectedItemPosition() == 0) {
//						CommonActivity.toast(activity,
//								R.string.message_not_sellect_reasoon);
//					} else if (obj.getRequestSendOTP() != null
//							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
//							&& CommonActivity.isNullOrEmpty(isdn)) {
//						CommonActivity.toast(activity,
//								R.string.edtOTPIsdn_require);
//						edtOTPIsdn.requestFocus();
//					} else if (obj.getRequestSendOTP() != null
//							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
//							&& StringUtils.CheckCharSpecical(isdn)) {
//						CommonActivity
//								.toast(activity, R.string.edtOTPIsdn_spec);
//						edtOTPIsdn.requestFocus();
//					} else if (obj.getRequestSendOTP() != null
//							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
//							&& CommonActivity.isNullOrEmpty(edtOTPCode
//									.getText().toString())) {
//						CommonActivity.toast(activity,
//								R.string.edtOTPCode_require);
//						edtOTPCode.requestFocus();
//					} else if (obj.getRequestSendOTP() != null
//							&& "true".equalsIgnoreCase(obj.getRequestSendOTP())
//							&& StringUtils.CheckCharSpecical(edtOTPCode
//									.getText().toString())) {
//						CommonActivity
//								.toast(activity, R.string.edtOTPCode_spec);
//						edtOTPCode.requestFocus();
//					} else {
//						obj.setCustId(customer.getCustId());
//						Log.d(Constant.TAG, "btnEdit " + obj);
//						String message = "";
//						if ("1".equalsIgnoreCase(subType)) {
//							Log.d(Constant.TAG,
//									"btnEdit Tra Truoc" + obj.getCusType());
//							message = getString(R.string.confirm_changeCustomerInfoPre);
//						} else {
//							Log.d(Constant.TAG,
//									"btnEdit Tra Sau" + obj.getCusType());
//							message = getString(R.string.confirm_changeCustomerInfoPos);
//						}
//
////						if(!CommonActivity.isNullOrEmpty(edtnotesedit.getText().toString())){
//							obj.setNotes(edtnotesedit.getText().toString());
////						}
//						custommerByIdNoBeanEdit = obj;
//						otp = edtOTPCode.getText().toString();
//						isdnSendOtp = edtOTPIsdn.getText().toString();
//
//						CommonActivity.createDialog(activity, message,
//								getString(R.string.app_name),
//								getString(R.string.say_ko),
//								getString(R.string.say_co), null,
//								confirmChargeAct).show();
//					}
//				}
//			}
//		});
//
//		dialogEditCustomer.show();
//	}
//
//	private final OnClickListener confirmChargeAct = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			if (CommonActivity.isNetworkConnected(activity)) {
//				if ("1".equalsIgnoreCase(subType)) {
//					ChangeCustomerInfoPreAsyn async = new ChangeCustomerInfoPreAsyn(
//							activity, custommerByIdNoBeanEdit);
//					async.execute(reasonId, otp, isdnSendOtp);
//				} else {
//					ChangeCustomerInfoPosAsyn async = new ChangeCustomerInfoPosAsyn(
//							activity, custommerByIdNoBeanEdit);
//					async.execute(reasonId, otp, isdnSendOtp);
//				}
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.errorNetwork),
//						getString(R.string.app_name)).show();
//			}
//		}
//	};
//
//	private final OnClickListener confirmSendOTP = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			if (CommonActivity.isNetworkConnected(activity)) {
//				AsyntaskSendOTP async = new AsyntaskSendOTP(activity,
//						prbreasonBtn, custId, edtOTPIsdn.getText().toString(),
//						subType);
//				async.execute();
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.errorNetwork),
//						getString(R.string.app_name)).show();
//			}
//		}
//	};
//
//	private CustommerByIdNoBean getCustomerFromInput(CustommerByIdNoBean other) {
//		CustommerByIdNoBean obj = new CustommerByIdNoBean();
//		if (other != null) {
//			obj = other.clone();
//		}
//		// =========== them moi kh tra TRUOC =========
//		String nameCustomer = StringUtils.escapeHtml(edit_tenKH.getText().toString());
//		Log.d("nameCustomer", nameCustomer);
//		String socmt = edit_socmnd.getText().toString();
//		Log.d("socmt", socmt);
//		String soGPKQ = edit_soGQ.getText().toString();
//		Log.d("soGPKQ", soGPKQ);
//		String birthDate = edit_ngaysinh.getText().toString();
//		Log.d("birthDate", birthDate);
//		String ngaycap = edit_ngaycap.getText().toString();
//		Log.d("ngaycap", ngaycap);
//		String noicap = StringUtils.escapeHtml(edit_noicap.getText().toString());
//		Log.d("noicap", noicap);
//		String tin = edt_tin.getText().toString();
//		Log.d("tin", tin);
//		SexBeans sexBean = arrSexBeans.get(spinner_sex
//				.getSelectedItemPosition());
//		Log.d("sex", sexBean.getValues());
//
//		TypePaperBeans typePaperBean = arrTypePaperBeans
//				.get(spinner_type_giay_to.getSelectedItemPosition());
//		Log.d("idType", typePaperBean.getParType());
//
//		obj.setTin(tin);
//		obj.setNameCustomer(nameCustomer);
//		obj.setBirthdayCus(birthDate);
//		obj.setIdNo(socmt);
//		obj.setBusPermitNo(soGPKQ);
//		obj.setProvince(province);
//		obj.setDistrict(district);
//		obj.setPrecint(precinct);
//		obj.setStreet_block(streetBlock);
//		obj.setIdIssuePlace(noicap);
//		obj.setIdIssueDate(ngaycap);
//		obj.setIdType(typePaperBean.getParType());
//		obj.setStreet(StringUtils.escapeHtml(edt_streetName.getText().toString().trim()));
//		obj.setHome(StringUtils.escapeHtml(edtHomeNumber.getText().toString().trim()));
//		obj.setAreaCode(province + district + precinct + streetBlock);
//
//
//
//		obj.setSex(sexBean.getValues());
//		obj.setStrIdExpire(editngayhethan.getText().toString());
//		try {
//			GetAreaDal dal = new GetAreaDal(activity);
//			dal.open();
//			String fulladdress = "";
//			if (!CommonActivity.isNullOrEmpty(edtHomeNumber.getText()
//					.toString())
//					&& !CommonActivity.isNullOrEmpty(edtHomeNumber.getText()
//							.toString())) {
//				fulladdress = StringUtils.escapeHtml(edtHomeNumber.getText().toString().trim()) + " "
//						+ StringUtils.escapeHtml(edt_streetName.getText().toString().trim()) + " "
//						+ StringUtils.escapeHtml(edtStreetBlock.getText().toString()) + " ";
//			}
//			fulladdress = fulladdress
//					+ dal.getfulladddress(province + district + precinct);
//			obj.setAddreseCus(fulladdress);
//			dal.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Log.d(this.getClass().getSimpleName(), " getCustomerFromInput " + obj);
//
//		return obj;
//	}
//
//	private final View.OnClickListener imageListenner = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			Object obj = v.getTag();
//			if (obj instanceof CustommerByIdNoBean) {
//				CustommerByIdNoBean customer = (CustommerByIdNoBean) obj;
//
//				custId = customer.getCustId();
//
//				boolean haveSubActive = customer.getHaveSubActive() != null
//						&& "true".equalsIgnoreCase(customer.getHaveSubActive());
//				Log.d(Constant.TAG,
//						"FragmentInfoCustomerMobile onItemClick() haveSubActive: "
//								+ haveSubActive
//								+ " permissionChangeInfoCustNoSubActive: "
//								+ permissionChangeInfoCustNoSubActive
//								+ " permissionChangeInfoCustHaveSubActive: "
//								+ permissionChangeInfoCustHaveSubActive);
//				if (!CommonActivity.isNullOrEmpty(customer.getBusPermitNo())) {
//					CommonActivity.toast(activity,
//							R.string.edit_customer_busPermitNo);
//				} else if (haveSubActive && permissionChangeInfoCustNoSubActive
//						&& permissionChangeInfoCustHaveSubActive) {
//					showPopupEditCustomer(customer);
//				} else if (!haveSubActive
//						&& permissionChangeInfoCustNoSubActive) {
//					showPopupEditCustomer(customer);
//				} else {
//					CommonActivity.toast(activity,
//							R.string.edit_customer_no_permission);
//				}
//			}
//		}
//	};
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.relaBackHome:
//			activity.onBackPressed();
//			removeCus();
//			break;
//
//		case R.id.btnsearch:
//			if (FragmentConnectionMobile.lnButton != null) {
//				FragmentConnectionMobile.lnButton.setVisibility(View.VISIBLE);
//			}
//			idsnOrAcc = edit_isdnacount.getText().toString().trim();
//			Log.d("idsnOrAcc", idsnOrAcc);
//			numberPaper = edit_sogiayto.getText().toString().trim();
//			Log.d("numberPaper", numberPaper);
//			busPermitNo = edit_sogpkd.getText().toString().trim();
//
//			if (serviceType != null && !serviceType.isEmpty()) {
//
//				if (edit_isdnacount.getText().toString() != null
//						&& !edit_isdnacount.getText().toString().isEmpty()) {
//					getlistgroup();
//				} else {
//					Toast.makeText(
//							activity,
//							activity.getResources().getString(
//									R.string.checkaccount), Toast.LENGTH_SHORT)
//							.show();
//				}
//			} else {
//				if (edit_isdnacount.getText().toString() != null
//						&& !edit_isdnacount.getText().toString().isEmpty()) {
//					Toast.makeText(
//							activity,
//							activity.getResources().getString(
//									R.string.checkserviceType),
//							Toast.LENGTH_SHORT).show();
//				} else {
//					if (edit_sogiayto.getText().toString() != null
//							&& !edit_sogiayto.getText().toString().isEmpty()) {
//						getlistgroup();
//					} else {
//						if (edit_sogpkd.getText().toString() != null
//								&& !edit_sogpkd.getText().toString().isEmpty()) {
//							getlistgroup();
//						} else {
//							Toast.makeText(
//									activity,
//									activity.getResources().getString(
//											R.string.checkinfocusmobile),
//									Toast.LENGTH_SHORT).show();
//						}
//
//					}
//				}
//			}
//			break;
//		case R.id.btnnhapmoimobile:
//			// show pop up cho kh them moi
//			dialogInsertNew = null;
//
//			if (arrBusTypeBeans != null && arrBusTypeBeans.size() > 0) {
//				arrBusTypeBeans.clear();
//			}
//			if (arrBusTypePreBeans != null && arrBusTypePreBeans.size() > 0) {
//				arrBusTypePreBeans.clear();
//			}
//			if ("1".equals(subType)) {
//				if (CommonActivity.isNetworkConnected(activity)) {
//					GetListTypeCusPreAsyn getListTypeCusPreAsyn = new GetListTypeCusPreAsyn(
//							activity);
//					getListTypeCusPreAsyn.execute();
//				} else {
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.errorNetwork),
//							getString(R.string.app_name)).show();
//				}
//			} else {
//				if (CommonActivity.isNetworkConnected(activity)) {
//					GetListTypeCusAsyn getListTypeCusAsyn = new GetListTypeCusAsyn(
//							activity);
//					getListTypeCusAsyn.execute();
//				} else {
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.errorNetwork),
//							getString(R.string.app_name)).show();
//				}
//			}
//
//			break;
//		default:
//			break;
//		}
//	}
//
//	// reload data
//	private void reloadData() {
//		if (arrCustommerByIdNoBeans != null
//				&& arrCustommerByIdNoBeans.size() > 0
//				&& adaGetListCusMobileAdapter != null) {
//			arrCustommerByIdNoBeans.clear();
//			adaGetListCusMobileAdapter.notifyDataSetChanged();
//		}
//		if (custommerByIdNoBean != null) {
//			custommerByIdNoBean = new CustommerByIdNoBean();
//		}
//	}
//
//	private void getlistgroup() {
//
//		if (CommonActivity.isNetworkConnected(activity)) {
//			if (arrCustommerByIdNoBeans != null
//					&& arrCustommerByIdNoBeans.size() > 0
//					&& adaGetListCusMobileAdapter != null) {
//				arrCustommerByIdNoBeans.clear();
//				adaGetListCusMobileAdapter.notifyDataSetChanged();
//			}
//			// == thue bao tra truoc ===========
//			if ("2".equals(subType)) {
//				GetListCustomerPreAsyn getListCustomerPreAsyn = new GetListCustomerPreAsyn(
//						activity);
//				getListCustomerPreAsyn.execute();
//			} else {
//				// ==== thue bao tra sau
//				GetListCustomerAsyn getCustomerAsyn = new GetListCustomerAsyn(
//						activity);
//				getCustomerAsyn.execute();
//			}
//
//		} else {
//			CommonActivity.createAlertDialog(activity,
//					activity.getResources().getString(R.string.errorNetwork),
//					activity.getResources().getString(R.string.app_name))
//					.show();
//		}
//	}
//
//	// lay thue bao
//	private void initSub() {
//		arrSubTypeBeans.add(new SubTypeBean(getString(R.string.subfirst), "2"));
//		arrSubTypeBeans.add(new SubTypeBean(getString(R.string.sublast), "1"));
//		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
//			ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                    android.R.layout.simple_dropdown_item_1line,
//                    android.R.id.text1);
//			for (SubTypeBean subTypeBean : arrSubTypeBeans) {
//				adapter.add(subTypeBean.getName());
//			}
//			spinner_typethuebao.setAdapter(adapter);
//		}
//
//	}
//
//	// lay dich vu
//	private void initTelecomService() {
//		try {
//			GetServiceDal dal = new GetServiceDal(activity);
//			dal.open();
//			arrTelecomServiceBeans = dal.getlisServiceMobile();
//			dal.close();
//			TelecomServiceBeans serviceBeans = new TelecomServiceBeans();
//			serviceBeans.setTele_service_name(activity.getResources()
//					.getString(R.string.chondichvu));
//			arrTelecomServiceBeans.add(0, serviceBeans);
//			if (arrTelecomServiceBeans != null
//					&& !arrTelecomServiceBeans.isEmpty()) {
//				ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                        activity, android.R.layout.simple_dropdown_item_1line,
//                        android.R.id.text1);
//				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
//					adapter.add(telecomServiceBeans.getTele_service_name());
//				}
//				spinner_service.setAdapter(adapter);
//			}
//		} catch (Exception e) {
//			Log.e("initTelecomService", e.toString());
//		}
//	}
//
//	// ==============ws get type kh tra truoc ===========
//	private class GetListTypeCusPreAsyn extends
//			AsyncTask<String, Void, ArrayList<BusTypePreBean>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListTypeCusPreAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<BusTypePreBean> doInBackground(String... params) {
//			return getListBusTypePre();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<BusTypePreBean> result) {
//
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result.size() > 0 && !result.isEmpty()) {
//
//					arrBusTypePreBeans = result;
//
//					if (dialogInsertNew == null) {
//						showPopupInsertNewCus();
//					}
//
//				} else {
//
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.checkTypeCus),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.equals("")) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			}
//
//		}
//
//		private ArrayList<BusTypePreBean> getListBusTypePre() {
//			ArrayList<BusTypePreBean> lstBusTypePreBeans = new ArrayList<>();
//			String original = null;
//			try {
//
//				lstBusTypePreBeans = new CacheDatabaseManager(context)
//						.getListBusTypePreCache();
//				if (lstBusTypePreBeans != null && !lstBusTypePreBeans.isEmpty()) {
//					errorCode = "0";
//					return lstBusTypePreBeans;
//				}
//
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListBusTypePre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListBusTypePre>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListBusTypePre>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListBusTypePre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.d("original", original);
//				// ========parser xml get employ from server
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstPrepaidBusType");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						BusTypePreBean busPreBean = new BusTypePreBean();
//						Element e1 = (Element) nodechild.item(j);
//						String busType = parse.getValue(e1, "busType");
//						Log.d("busType", busType);
//						busPreBean.setBusType(busType);
//
//						String cusType = parse.getValue(e1, "custType");
//						Log.d("cusType", cusType);
//						busPreBean.setCusType(cusType);
//
//						String name = parse.getValue(e1, "name");
//						busPreBean.setName(name);
//						lstBusTypePreBeans.add(busPreBean);
//					}
//				}
//
//			} catch (Exception e) {
//				Log.d("exception", e.toString());
//			}
//			new CacheDatabaseManager(context)
//					.insertBusTypePre(lstBusTypePreBeans);
//			return lstBusTypePreBeans;
//		}
//	}
//
//	// ==============ws lay danh sach loai kh tra sau
//
//	private class GetListTypeCusAsyn extends
//			AsyncTask<String, Void, ArrayList<BusTypeBean>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListTypeCusAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<BusTypeBean> doInBackground(String... params) {
//
//			return getListBusType();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<BusTypeBean> result) {
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result.size() > 0 && !result.isEmpty()) {
//
//					arrBusTypeBeans = result;
//					if (dialogInsertNew == null) {
//						showPopupInsertNewCus();
//
//					}
//
//				} else {
//
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.checkTypeCus),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.equals("")) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			}
//
//		}
//
//		private ArrayList<BusTypeBean> getListBusType() {
//			ArrayList<BusTypeBean> lstBusTypeBeans = new ArrayList<>();
//			String original = null;
//			try {
//
//				lstBusTypeBeans = new CacheDatabaseManager(context)
//						.getListBusTypeCache();
//				if (lstBusTypeBeans != null && !lstBusTypeBeans.isEmpty()) {
//					errorCode = "0";
//					return lstBusTypeBeans;
//				}
//
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListBusType");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListBusType>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListBusType>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity, "mbccs_getListBusType");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.d("original", original);
//				// ========parser xml get employ from server
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstBusType");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						BusTypeBean busTypeBean = new BusTypeBean();
//						Element e1 = (Element) nodechild.item(j);
//						String busType = parse.getValue(e1, "busType");
//						Log.d("busType", busType);
//						busTypeBean.setBusType(busType);
//
//						String cusType = parse.getValue(e1, "custType");
//						busTypeBean.setCusType(cusType);
//
//						String name = parse.getValue(e1, "name");
//						busTypeBean.setName(name);
//						lstBusTypeBeans.add(busTypeBean);
//					}
//				}
//
//			} catch (Exception e) {
//				Log.d("exception", e.toString());
//			}
//			new CacheDatabaseManager(context).insertBusType(lstBusTypeBeans);
//
//			return lstBusTypeBeans;
//		}
//	}
//
//	// === CHECK THEM MOI KH TRA TRUOC ===========
//
//	private class CheckCustomerPreAsyn extends
//			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public CheckCustomerPreAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
//			return getListCustomerPreIdNo(arg0[0], arg0[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
//			CommonActivity.hideKeyboard(edit_tenKH, context);
//			CommonActivity.hideKeyboard(edit_socmnd, context);
//			CommonActivity.hideKeyboard(edit_noicap, context);
//			progress.dismiss();
//			try{
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.checkcus),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				} else {
//					// =========== them moi kh tra TRUOC =========
//					String nameCustomer = CommonActivity.getNormalText(edit_tenKH.getText().toString());
//					Log.d("nameCustomer", nameCustomer);
//					String socmt = edit_socmnd.getText().toString();
//					Log.d("socmt", socmt);
//					String soGPKQ = edit_soGQ.getText().toString();
//					Log.d("soGPKQ", soGPKQ);
//					String birthDate = edit_ngaysinh.getText().toString();
//					Log.d("birthDate", birthDate);
//					String ngaycap = edit_ngaycap.getText().toString();
//					Log.d("ngaycap", ngaycap);
//					String noicap = CommonActivity.getNormalText(edit_noicap.getText().toString());
//					Log.d("noicap", noicap);
//
//					String tin = edt_tin.getText().toString();
//					Log.d("Ma So Thue", tin);
//
//					SexBeans sexBean = arrSexBeans.get(spinner_sex
//							.getSelectedItemPosition());
//					Log.d(Constant.TAG,
//							"CheckCustomerPreAsyn onPostExecute sex: "
//									+ sexBean.getValues());
//
//					CustommerByIdNoBean obj = new CustommerByIdNoBean();
//					obj.setTin(tin);
//					obj.setNameCustomer(nameCustomer);
//					obj.setBirthdayCusNew(birthDate);
//					obj.setIdNo(socmt);
//					obj.setBusPermitNo(soGPKQ);
//					obj.setCusType(busType);
//					obj.setCustId("");
//					obj.setProvince(province);
//					obj.setPrecint(precinct);
//					obj.setDistrict(district);
//					obj.setIdIssuePlace(noicap);
//					obj.setIdIssueDate(ngaycap);
//					obj.setIdType(idType);
//
//					obj.setStreet_block(streetBlock);
//					obj.setStreet(CommonActivity.getNormalText(edt_streetName.getText().toString().trim()));
//					obj.setHome(CommonActivity.getNormalText(edtHomeNumber.getText().toString().trim()));
//					obj.setAreaCode(province + district + precinct
//							+ streetBlock);
//
//					obj.setSex(sexBean.getValues());
//					obj.setStrIdExpire(editngayhethan.getText().toString());
//					try {
//						GetAreaDal dal = new GetAreaDal(activity);
//						dal.open();
//						String fulladdress = "";
//						if (!CommonActivity.isNullOrEmpty(edtHomeNumber
//								.getText().toString())
//								&& !CommonActivity.isNullOrEmpty(edtHomeNumber
//										.getText().toString())) {
//							fulladdress = edtHomeNumber.getText().toString()
//									.trim()
//									+ " "
//									+ edt_streetName.getText().toString()
//											.trim()
//									+ " "
//									+ edtStreetBlock.getText() + " ";
//						}
//						fulladdress = fulladdress
//								+ dal.getfulladddress(province + district
//										+ precinct);
//						obj.setAddreseCus(CommonActivity.getNormalText(fulladdress));
//						dal.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					if(!CommonActivity.isNullOrEmpty(edtnote.getText().toString())){
//						obj.setNotes(CommonActivity.getNormalText(edtnote.getText().toString()));
//					}
//
//					if (arrCustommerByIdNoBeans != null
//							&& arrCustommerByIdNoBeans.size() > 0) {
//						arrCustommerByIdNoBeans.clear();
//					}
//
//					arrCustommerByIdNoBeans.add(obj);
//					if (adaGetListCusMobileAdapter != null) {
//						adaGetListCusMobileAdapter.notifyDataSetChanged();
//						dialogInsertNew.dismiss();
//					} else {
//						// adaGetListCusMobileAdapter = new
//						// GetListCusMobileAdapter(
//						// arrCustommerByIdNoBeans, activity);
//						adaGetListCusMobileAdapter = new GetListCusMobileEditableAdapter(
//								activity, arrCustommerByIdNoBeans,
//								imageListenner);
//						lvCustomer.setAdapter(adaGetListCusMobileAdapter);
//						dialogInsertNew.dismiss();
//					}
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)
//						&& description != null && !description.isEmpty()) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			}
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		private ArrayList<CustommerByIdNoBean> getListCustomerPreIdNo(
//				String idNo, String soGPKD) {
//			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<>();
//			String original = null;
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getListPrepaidCustomer");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListPrepaidCustomer>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				if (idNo != null && !idNo.isEmpty()) {
//					rawData.append("<idNo>").append(idNo);
//					rawData.append("</idNo>");
//				} else {
//					// rawData.append("<idNo>" + "");
//					// rawData.append("</idNo>");
//				}
//
//				if (soGPKD != null && !soGPKD.isEmpty()) {
//					rawData.append("<busPermitNo>").append(soGPKD);
//					rawData.append("</busPermitNo>");
//				} else {
//					rawData.append("<busPermitNo>" + "");
//					rawData.append("</busPermitNo>");
//				}
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListPrepaidCustomer>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListPrepaidCustomer");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//
//				// parse xml
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstPrepaidCustomer");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						CustommerByIdNoBean obj = new CustommerByIdNoBean();
//						String address = parse.getValue(e1, "address");
//						Log.d("address", address);
//						obj.setAddreseCus(address);
//						String custId = parse.getValue(e1, "custId");
//						Log.d("custId", custId);
//						obj.setCustId(custId);
//						String nameCus = parse.getValue(e1, "name");
//						Log.d("nameCus", nameCus);
//						obj.setNameCustomer(nameCus);
//						String idNoCus = parse.getValue(e1, "idNo");
//						Log.d("idNo", idNoCus);
//						obj.setIdNo(idNoCus);
//						String birthDate = parse.getValue(e1, "birthDate");
//						Log.d("birthDate", birthDate);
//						obj.setBirthdayCus(StringUtils.convertDate(birthDate));
//						String areaCode = parse.getValue(e1, "areaCode");
//						obj.setAreaCode(areaCode);
//						String idType = parse.getValue(e1, "idType");
//						obj.setIdType(idType);
//						String sex = parse.getValue(e1, "sex");
//						obj.setSex(sex);
//						String province = parse.getValue(e1, "province");
//						obj.setProvince(province);
//						String district = parse.getValue(e1, "district");
//						obj.setDistrict(district);
//						String precinct = parse.getValue(e1, "precinct");
//						obj.setPrecint(precinct);
//
//						String busType = parse.getValue(e1, "busType");
//						Log.d("busType", busType);
//						obj.setCusType(busType);
//
//						String custGroupId = parse.getValue(e1, "custGroupId");
//						Log.d("custGroupId", custGroupId);
//						obj.setCusGroupId(custGroupId);
//
//						lisCustommerByIdNoBeans.add(obj);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return lisCustommerByIdNoBeans;
//		}
//
//	}
//
//	// === CHECK THEM MOI KH TRA SAU ===========
//
//	private class CheckCustomerAsyn extends
//			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public CheckCustomerAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
//			return getListCustomerIdNo(arg0[0], arg0[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
//			CommonActivity.hideKeyboard(edit_tenKH, context);
//			CommonActivity.hideKeyboard(edit_socmnd, context);
//			CommonActivity.hideKeyboard(edit_noicap, context);
//			progress.dismiss();
//			try{
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.checkcus),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				} else {
//
//					// =========== them moi kh tra sau =========
//					String nameCustomer = CommonActivity.getNormalText(edit_tenKH.getText().toString());
//					Log.d("nameCustomer", nameCustomer);
//					String socmt = edit_socmnd.getText().toString();
//					Log.d("socmt", socmt);
//					String soGPKQ = edit_soGQ.getText().toString();
//					Log.d("soGPKQ", soGPKQ);
//					String birthDate = edit_ngaysinh.getText().toString();
//					Log.d("birthDate", birthDate);
//					String ngaycap = edit_ngaycap.getText().toString();
//					Log.d("ngaycap", ngaycap);
//					String noicap = CommonActivity.getNormalText(edit_noicap.getText().toString());
//					Log.d("noicap", noicap);
//
//					String tin = edt_tin.getText().toString();
//					Log.d("Ma So Thue", tin);
//
//					SexBeans sexBean = arrSexBeans.get(spinner_sex
//							.getSelectedItemPosition());
//					Log.d(Constant.TAG,
//							"CheckCustomerPreAsyn onPostExecute sex: "
//									+ sexBean.getValues());
//
//					CustommerByIdNoBean obj = new CustommerByIdNoBean();
//					obj.setTin(tin);
//
//					// obj.setCustTypeId(custTypeId);
//
//					obj.setNameCustomer(nameCustomer);
//					obj.setBirthdayCusNew(birthDate);
//					obj.setIdNo(socmt);
//					obj.setBusPermitNo(soGPKQ);
//					obj.setCusType(busType);
//					obj.setCustId("");
//					obj.setProvince(province);
//					obj.setPrecint(precinct);
//					obj.setDistrict(district);
//					obj.setIdIssuePlace(noicap);
//					obj.setIdIssueDate(ngaycap);
//					obj.setAreaCode(province + district + precinct);
//					obj.setIdType(idType);
//
//					obj.setStreet_block(streetBlock);
//					obj.setStreet(edt_streetName.getText().toString().trim());
//					obj.setHome(edtHomeNumber.getText().toString().trim());
//					obj.setAreaCode(province + district + precinct
//							+ streetBlock);
//
//					obj.setSex(sexBean.getValues());
//					obj.setStrIdExpire(editngayhethan.getText().toString());
//					try {
//						GetAreaDal dal = new GetAreaDal(activity);
//						dal.open();
//						String fulladdress = "";
//						if (!CommonActivity.isNullOrEmpty(edtHomeNumber
//								.getText().toString())
//								&& !CommonActivity.isNullOrEmpty(edtHomeNumber
//										.getText().toString())) {
//							fulladdress = CommonActivity.getNormalText(edtHomeNumber.getText().toString())
//									.trim()
//									+ " "
//									+ CommonActivity.getNormalText(edt_streetName.getText().toString())
//											.trim()
//									+ " "
//									+ CommonActivity.getNormalText(edtStreetBlock.getText().toString()) + " ";
//						}
//						fulladdress = fulladdress
//								+ dal.getfulladddress(province + district
//										+ precinct);
//						obj.setAddreseCus(fulladdress);
//						dal.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					if(!CommonActivity.isNullOrEmpty(edtnote.getText().toString())){
//						obj.setNotes(CommonActivity.getNormalText(edtnote.getText().toString()));
//					}
//					if (arrCustommerByIdNoBeans != null
//							&& arrCustommerByIdNoBeans.size() > 0) {
//						arrCustommerByIdNoBeans.clear();
//					}
//
//					arrCustommerByIdNoBeans.add(obj);
//					if (adaGetListCusMobileAdapter != null) {
//						adaGetListCusMobileAdapter.notifyDataSetChanged();
//						dialogInsertNew.dismiss();
//					} else {
//						// adaGetListCusMobileAdapter = new
//						// GetListCusMobileAdapter(
//						// arrCustommerByIdNoBeans, activity);
//						adaGetListCusMobileAdapter = new GetListCusMobileEditableAdapter(
//								activity, arrCustommerByIdNoBeans,
//								imageListenner);
//						lvCustomer.setAdapter(adaGetListCusMobileAdapter);
//						dialogInsertNew.dismiss();
//					}
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)
//						&& description != null && !description.isEmpty()) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			}
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		private ArrayList<CustommerByIdNoBean> getListCustomerIdNo(String idNo,
//				String soGPKD) {
//			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<>();
//			String original = null;
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getCustomerByIdNo");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getCustomerByIdNo>");
//				rawData.append("<cmFixServiceInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				if (idNo != null && !idNo.isEmpty()) {
//					rawData.append("<idNo>").append(idNo);
//					rawData.append("</idNo>");
//				} else {
//					// rawData.append("<idNo>" + "");
//					// rawData.append("</idNo>");
//				}
//				if (soGPKD != null && !soGPKD.isEmpty()) {
//					rawData.append("<busPermitNo>").append(soGPKD);
//					rawData.append("</busPermitNo>");
//				} else {
//					rawData.append("<busPermitNo>" + "");
//					rawData.append("</busPermitNo>");
//				}
//				rawData.append("</cmFixServiceInput>");
//				rawData.append("</ws:getCustomerByIdNo>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getCustomerByIdNo");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//
//				// parse xml
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstCustomer");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						CustommerByIdNoBean obj = new CustommerByIdNoBean();
//						String address = parse.getValue(e1, "address");
//						Log.d("address", address);
//						obj.setAddreseCus(address);
//						String custId = parse.getValue(e1, "custId");
//						Log.d("custId", custId);
//						obj.setCustId(custId);
//						String nameCus = parse.getValue(e1, "name");
//						Log.d("nameCus", nameCus);
//						obj.setNameCustomer(nameCus);
//						String idNoCus = parse.getValue(e1, "idNo");
//						Log.d("idNo", idNoCus);
//						obj.setIdNo(idNoCus);
//						String birthDate = parse.getValue(e1, "birthDate");
//						Log.d("birthDate", birthDate);
//						obj.setBirthdayCus(birthDate);
//						String sex = parse.getValue(e1, "sex");
//						obj.setSex(sex);
//						String busType = parse.getValue(e1, "busType");
//						Log.d("busType", busType);
//						obj.setCusType(busType);
//
//						String custGroupId = parse.getValue(e1, "custGroupId");
//						Log.d("custGroupId", custGroupId);
//						obj.setCusGroupId(custGroupId);
//
//						lisCustommerByIdNoBeans.add(obj);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return lisCustommerByIdNoBeans;
//		}
//
//	}
//
//	// ================ ws get list customerbyIdNo
//
//	private class GetListCustomerAsyn extends
//			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListCustomerAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
//			return getListCustomerIdNo();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
//			CommonActivity.hideKeyboard(edit_isdnacount, context);
//			CommonActivity.hideKeyboard(edit_sogiayto, context);
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					arrCustommerByIdNoBeans = result;
//
//					// adaGetListCusMobileAdapter = new GetListCusMobileAdapter(
//					// arrCustommerByIdNoBeans, activity);
//					adaGetListCusMobileAdapter = new GetListCusMobileEditableAdapter(
//							activity, arrCustommerByIdNoBeans, imageListenner);
//					lvCustomer.setAdapter(adaGetListCusMobileAdapter);
//				} else {
//					if (description != null && !description.isEmpty()) {
//						Dialog dialog = CommonActivity.createAlertDialog(
//								activity, description, getResources()
//										.getString(R.string.app_name));
//						dialog.show();
//					} else {
//						Dialog dialog = CommonActivity.createAlertDialog(
//								activity,
//								getResources().getString(R.string.notkh),
//								getResources().getString(R.string.app_name));
//						dialog.show();
//					}
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)
//						&& description != null && !description.isEmpty()) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			}
//		}
//
//		private ArrayList<CustommerByIdNoBean> getListCustomerIdNo() {
//			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<>();
//			String original = null;
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getCustomerByIdNo");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getCustomerByIdNo>");
//				rawData.append("<cmFixServiceInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				// add tham so input
//				if (serviceType != null && !serviceType.isEmpty()) {
//					rawData.append("<serviceType>").append(serviceType);
//					rawData.append("</serviceType>");
//				} else {
//					// rawData.append("<serviceType>" + "");
//					// rawData.append("</serviceType>");
//				}
//				if (idsnOrAcc != null && !idsnOrAcc.isEmpty()) {
//					rawData.append("<account>").append(idsnOrAcc);
//					rawData.append("</account>");
//				} else {
//					// rawData.append("<account>" + "");
//					// rawData.append("</account>");
//				}
//				if (numberPaper != null && !numberPaper.isEmpty()) {
//					rawData.append("<idNo>").append(numberPaper);
//					rawData.append("</idNo>");
//				} else {
//					// rawData.append("<idNo>" + "");
//					// rawData.append("</idNo>");
//				}
//				if (busPermitNo != null && !busPermitNo.isEmpty()) {
//					rawData.append("<busPermitNo>").append(busPermitNo);
//					rawData.append("</busPermitNo>");
//				} else {
//					// rawData.append("<busPermitNo>" + "");
//					// rawData.append("</busPermitNo>");
//				}
//				rawData.append("</cmFixServiceInput>");
//				rawData.append("</ws:getCustomerByIdNo>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getCustomerByIdNo");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//
//				// parse xml
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstCustomer");
//					NodeList nodeCusAttribute = null;
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						CustommerByIdNoBean obj = new CustommerByIdNoBean();
//						String address = parse.getValue(e1, "address");
//						Log.d("address", address);
//						obj.setAddreseCus(address);
//						String custId = parse.getValue(e1, "custId");
//						Log.d("custId", custId);
//						obj.setCustId(custId);
//						String areaCode = parse.getValue(e1, "areaCode");
//						obj.setAreaCode(areaCode);
//						String nameCus = parse.getValue(e1, "name");
//						Log.d("nameCus", nameCus);
//						obj.setNameCustomer(nameCus);
//						String idNoCus = parse.getValue(e1, "idNo");
//						Log.d("idNo", idNoCus);
//						obj.setIdNo(idNoCus);
//						String birthDate = parse.getValue(e1, "birthDate");
//						Log.d("birthDate", birthDate);
//						obj.setBirthdayCus(StringUtils.convertDate(birthDate));
//
//						String busType = parse.getValue(e1, "busType");
//						Log.d("busType", busType);
//						obj.setCusType(busType);
//
//						String busPermitNo = parse.getValue(e1, "busPermitNo");
//						Log.d("busPermitNo", busPermitNo);
//						obj.setBusPermitNo(busPermitNo);
//
//						String custGroupId = parse.getValue(e1, "custGroupId");
//						Log.d("custGroupId", custGroupId);
//						obj.setCusGroupId(custGroupId);
//
//						String idType = parse.getValue(e1, "idType");
//						obj.setIdType(idType);
//						String sex = parse.getValue(e1, "sex");
//						obj.setSex(sex);
//
//						String province = parse.getValue(e1, "province");
//						obj.setProvince(province);
//						String district = parse.getValue(e1, "district");
//						obj.setDistrict(district);
//						String precinct = parse.getValue(e1, "precinct");
//						obj.setPrecint(precinct);
//
//						String street_block = parse.getValue(e1, "streetBlock");
//						obj.setStreet_block(street_block);
//						String street = parse.getValue(e1, "streetName");
//						obj.setStreet(street);
//						String home = parse.getValue(e1, "home");
//						obj.setHome(home);
//
//
//						String notes = parse.getValue(e1, "notes");
//						obj.setNotes(notes);
//
//						String idIssueDate = parse.getValue(e1, "idIssueDate");
//						obj.setIdIssueDate(StringUtils.convertDate(idIssueDate));
//						String idIssuePlace = parse
//								.getValue(e1, "idIssuePlace");
//						obj.setIdIssuePlace(idIssuePlace);
//
//						String idExpireDate = parse
//								.getValue(e1, "idExpireDate");
//						obj.setIdExpireDate(StringUtils
//								.convertDate(idExpireDate));
//
//						String haveSubActive = parse.getValue(e1,
//								"haveSubActive");
//						String requestSendOTP = parse.getValue(e1,
//								"requestSendOTP");
//						obj.setHaveSubActive(haveSubActive);
//						obj.setRequestSendOTP(requestSendOTP);
//
//						nodeCusAttribute = e1
//								.getElementsByTagName("customerAttribute");
//						for (int k = 0; k < nodeCusAttribute.getLength(); k++) {
//							Element e3 = (Element) nodeCusAttribute.item(k);
//							String birthDateAtt = parse.getValue(e3,
//									"birthDate");
//							obj.getCustomerAttribute().setBirthDate(
//									StringUtils.convertDate(birthDateAtt));
//							obj.getCustomerAttribute().setCustId(
//									parse.getValue(e3, "custId"));
//							obj.getCustomerAttribute().setId(
//									parse.getValue(e3, "id"));
//							obj.getCustomerAttribute().setIdType(
//									parse.getValue(e3, "idType"));
//							obj.getCustomerAttribute().setIdNo(
//									parse.getValue(e3, "idNo"));
//							obj.getCustomerAttribute().setIssueDate(
//									StringUtils.convertDate(parse.getValue(e3,
//											"issueDate")));
//							obj.getCustomerAttribute().setIssuePlace(
//									parse.getValue(e3, "isssuePlace"));
//							obj.getCustomerAttribute().setName(
//									parse.getValue(e3, "name"));
//						}
//						lisCustommerByIdNoBeans.add(obj);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return lisCustommerByIdNoBeans;
//		}
//
//	}
//
//	// lay thong tin khach hang tra truoc
//
//	public class GetListCustomerPreAsyn extends
//			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListCustomerPreAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
//			return getListCustomerPreIdNo();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
//			CommonActivity.hideKeyboard(edit_isdnacount, context);
//			CommonActivity.hideKeyboard(edit_sogiayto, context);
//			CommonActivity.hideKeyboard(edit_sogpkd, context);
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					arrCustommerByIdNoBeans = result;
//
//					// adaGetListCusMobileAdapter = new GetListCusMobileAdapter(
//					// arrCustommerByIdNoBeans, activity);
//					adaGetListCusMobileAdapter = new GetListCusMobileEditableAdapter(
//							activity, arrCustommerByIdNoBeans, imageListenner);
//					lvCustomer.setAdapter(adaGetListCusMobileAdapter);
//				} else {
//					if (description != null && !description.isEmpty()) {
//						Dialog dialog = CommonActivity.createAlertDialog(
//								activity, description, getResources()
//										.getString(R.string.app_name));
//						dialog.show();
//					} else {
//						Dialog dialog = CommonActivity.createAlertDialog(
//								activity,
//								getResources().getString(R.string.notkh),
//								getResources().getString(R.string.app_name));
//						dialog.show();
//					}
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)
//						&& description != null && !description.isEmpty()) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			}
//		}
//
//		private ArrayList<CustommerByIdNoBean> getListCustomerPreIdNo() {
//			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<>();
//			String original = null;
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getListPrepaidCustomer");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListPrepaidCustomer>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				// add tham so input
//				if (serviceType != null && !serviceType.isEmpty()) {
//					rawData.append("<serviceType>").append(serviceType);
//					rawData.append("</serviceType>");
//				} else {
//					// rawData.append("<serviceType>" + "");
//					// rawData.append("</serviceType>");
//				}
//				if (idsnOrAcc != null && !idsnOrAcc.isEmpty()) {
//					rawData.append("<isdn>").append(idsnOrAcc);
//					rawData.append("</isdn>");
//				} else {
//					// rawData.append("<account>" + "");
//					// rawData.append("</account>");
//				}
//				if (numberPaper != null && !numberPaper.isEmpty()) {
//					rawData.append("<idNo>").append(numberPaper);
//					rawData.append("</idNo>");
//				} else {
//					// rawData.append("<idNo>" + "");
//					// rawData.append("</idNo>");
//				}
//				if (busPermitNo != null && !busPermitNo.isEmpty()) {
//					rawData.append("<busPermitNo>").append(busPermitNo);
//					rawData.append("</busPermitNo>");
//				} else {
//					// rawData.append("<busPermitNo>" + "");
//					// rawData.append("</busPermitNo>");
//				}
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListPrepaidCustomer>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListPrepaidCustomer");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//
//				// parse xml
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstPrepaidCustomer");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						CustommerByIdNoBean obj = new CustommerByIdNoBean();
//						String address = parse.getValue(e1, "address");
//						Log.d("address", address);
//						obj.setAddreseCus(address);
//						String areaCode = parse.getValue(e1, "areaCode");
//						obj.setAreaCode(areaCode);
//						String custId = parse.getValue(e1, "custId");
//						Log.d("custId", custId);
//						obj.setCustId(custId);
//						String nameCus = parse.getValue(e1, "name");
//						Log.d("nameCus", nameCus);
//						obj.setNameCustomer(nameCus);
//						String idNoCus = parse.getValue(e1, "idNo");
//						Log.d("idNo", idNoCus);
//						obj.setIdNo(idNoCus);
//						String birthDate = parse.getValue(e1, "birthDate");
//						Log.d("birthDate", birthDate);
//						obj.setBirthdayCus(StringUtils.convertDate(birthDate));
//
//						String busType = parse.getValue(e1, "busType");
//						Log.d("busType", busType);
//						obj.setCusType(busType);
//
//						String busPermitNo = parse.getValue(e1, "busPermitNo");
//						Log.d("busPermitNo", busPermitNo);
//						obj.setBusPermitNo(busPermitNo);
//
//						String custGroupId = parse.getValue(e1, "custGroupId");
//						Log.d("custGroupId", custGroupId);
//						obj.setCusGroupId(custGroupId);
//
//						String idType = parse.getValue(e1, "idType");
//						obj.setIdType(idType);
//						String sex = parse.getValue(e1, "sex");
//						obj.setSex(sex);
//
//						String province = parse.getValue(e1, "province");
//						obj.setProvince(province);
//						String district = parse.getValue(e1, "district");
//						obj.setDistrict(district);
//						String precinct = parse.getValue(e1, "precinct");
//						obj.setPrecint(precinct);
//
//						String street_block = parse.getValue(e1,
//								"streetBlockName");
//						obj.setStreet_block(street_block);
//						String street = parse.getValue(e1, "streetName");
//						obj.setStreet(street);
//						String home = parse.getValue(e1, "home");
//						obj.setHome(home);
//
//						String notes = parse.getValue(e1, "notes");
//						obj.setNotes(notes);
//
//
//						String idIssueDate = parse.getValue(e1, "idIssueDate");
//						obj.setIdIssueDate(StringUtils.convertDate(idIssueDate));
//						String idIssuePlace = parse
//								.getValue(e1, "idIssuePlace");
//						obj.setIdIssuePlace(idIssuePlace);
//
//						String idExpireDate = parse
//								.getValue(e1, "idExpireDate");
//						obj.setIdExpireDate(StringUtils
//								.convertDate(idExpireDate));
//
//						String haveSubActive = parse.getValue(e1,
//								"haveSubActive");
//						String requestSendOTP = parse.getValue(e1,
//								"requestSendOTP");
//						obj.setHaveSubActive(haveSubActive);
//						obj.setRequestSendOTP(requestSendOTP);
//
//						lisCustommerByIdNoBeans.add(obj);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return lisCustommerByIdNoBeans;
//		}
//
//	}
//
//	private final View.OnClickListener editTextListener = new View.OnClickListener() {
//
//		@Override
//		public void onClick(View view) {
//			EditText edt = (EditText) view;
//			Calendar cal = Calendar.getInstance();
//			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
//				Date date = DateTimeUtils.convertStringToTime(edt.getText()
//						.toString(), "dd/MM/yyyy");
//
//				cal.setTime(date);
//
//			}
//			DatePickerDialog pic = new DatePickerDialog(activity,
//					AlertDialog.THEME_HOLO_LIGHT,datePickerListener, cal.get(Calendar.YEAR),
//					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
//			pic.getDatePicker().setTag(view);
//			pic.show();
//		}
//	};
//
//	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//		@Override
//		public void onDateSet(DatePicker view, int selectedYear,
//				int selectedMonth, int selectedDay) {
//			Object obj = view.getTag();
//			if (obj != null && obj instanceof EditText) {
//				EditText editText = (EditText) obj;
//				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
//						+ selectedYear);
//
//				int id = editText.getId();
//
//				Calendar cal = Calendar.getInstance();
//				cal.set(selectedYear, selectedMonth, selectedDay);
//				Date date = cal.getTime();
//
//				// if(edit_ngaycap != null && id == edit_ngaycap.getId()) {
//				// issueDate = date;
//				// } else if(edit_ngaycapdd != null && editText.getId() ==
//				// edit_ngaycapdd.getId()) {
//				// issueDateATT = date;
//				// } else if(edit_ngaysinhCus != null && editText.getId() ==
//				// edit_ngaysinhCus.getId()) {
//				// birthDateATT = date;
//				// } else if(edit_ngaysinh != null && editText.getId() ==
//				// edit_ngaysinh.getId()) {
//				// birthDateCus = date;
//				// } else if(editngayhethan != null && editText.getId() ==
//				// editngayhethan.getId()) {
//				// // ngay het han
//				// }
//
//			}
//		}
//	};
//
//	// lay ma tinh/thanhpho
//	private void initProvince() {
//		try {
//			GetAreaDal dal = new GetAreaDal(activity);
//			dal.open();
//			arrProvince = dal.getLstProvince();
//			dal.close();
//		} catch (Exception ex) {
//			Log.e("initProvince", ex.toString());
//		}
//	}
//
//	// lay huyen/quan theo tinh
//	private void initDistrict(String province) {
//		try {
//			GetAreaDal dal = new GetAreaDal(activity);
//			dal.open();
//			arrDistrict = dal.getLstDistrict(province);
//			dal.close();
//		} catch (Exception ex) {
//			Log.e("initDistrict", ex.toString());
//		}
//	}
//
//	// lay phuong/xa theo tinh,qhuyen
//	private void initPrecinct(String province, String district) {
//		try {
//			GetAreaDal dal = new GetAreaDal(activity);
//			dal.open();
//			arrPrecinct = dal.getLstPrecinct(province, district);
//			dal.close();
//		} catch (Exception ex) {
//			Log.e("initPrecinct", ex.toString());
//		}
//	}
//
//	// init gioi tinh
//	private void initSex() {
//		arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
//		arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
//		if (arrSexBeans != null && arrSexBeans.size() > 0) {
//			ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                    android.R.layout.simple_dropdown_item_1line,
//                    android.R.id.text1);
//			for (SexBeans sexBeans : arrSexBeans) {
//				adapter.add(sexBeans.getName());
//			}
//			spinner_sex.setAdapter(adapter);
//		}
//	}
//
//	// init typepaper
//	private ArrayAdapter<String> initTypePaper() {
//		GetTypePaperDal dal = null;
//		ArrayAdapter<String> adapter = null;
//		try {
//			dal = new GetTypePaperDal(activity);
//			dal.open();
//			arrTypePaperBeans = dal.getLisTypepaper();
//			dal.close();
//			if (arrTypePaperBeans != null && !arrTypePaperBeans.isEmpty()) {
//				adapter = new ArrayAdapter<>(activity,
//                        android.R.layout.simple_dropdown_item_1line,
//                        android.R.id.text1);
//				for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
//					adapter.add(typePaperBeans.getParValues());
//				}
//				// spinner_type_giay_to.setAdapter(adapter);
//			}
//		} catch (Exception e) {
//			Log.e("initTypePaper", e.toString());
//		} finally {
//			if (dal != null) {
//				dal.close();
//			}
//		}
//		return adapter;
//	}
//
//	// showpopup insert new info customer
//	// TuanTD7
//	private void showPopupInsertNewCus() {
//		dialogInsertNew = new Dialog(activity);
//		dialogInsertNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogInsertNew
//				.setContentView(R.layout.connectionmobile_layout_insert_newinfo);
//		dialogInsertNew.setCancelable(false);
//		final LinearLayout lnngayhethan = (LinearLayout) dialogInsertNew
//				.findViewById(R.id.lnngayhethan);
//		editngayhethan = (EditText) dialogInsertNew
//				.findViewById(R.id.edit_ngayhethan);
//
//		dialogInsertNew.findViewById(R.id.btnRefreshStreetBlock).setVisibility(
//				View.GONE);
//
//		edtloaikh = (EditText) dialogInsertNew.findViewById(R.id.edtloaikh);
//		edtloaikh.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				if ("1".equals(subType)) {
//					if (arrBusTypePreBeans != null
//							&& !arrBusTypePreBeans.isEmpty()) {
//						Intent intent = new Intent(activity,
//								FragmentSearchBusTypeMobile.class);
//						intent.putExtra("arrBusTypePreBeansKey",
//								arrBusTypePreBeans);
//						Bundle mBundle = new Bundle();
//						mBundle.putString("checkSubKey", "11");
//						intent.putExtras(mBundle);
//						startActivityForResult(intent, 104);
//					}
//				} else {
//					if (arrBusTypeBeans != null && !arrBusTypeBeans.isEmpty()) {
//						Intent intent = new Intent(activity,
//								FragmentSearchBusTypeMobile.class);
//						intent.putExtra("arrBusTypeBeansKey", arrBusTypeBeans);
//						startActivityForResult(intent, 105);
//					}
//				}
//			}
//		});
//		edtloaikh = (EditText) dialogInsertNew.findViewById(R.id.edtloaikh);
//		edit_tenKH = (EditText) dialogInsertNew.findViewById(R.id.edit_tenKH);
//		edit_socmnd = (EditText) dialogInsertNew.findViewById(R.id.edit_socmnd);
//		edit_ngaycap = (EditText) dialogInsertNew
//				.findViewById(R.id.edit_ngaycap);
//		edit_ngaycap.setText(dateNowString);
//		edit_noicap = (EditText) dialogInsertNew.findViewById(R.id.edit_noicap);
//		edit_ngaysinh = (EditText) dialogInsertNew
//				.findViewById(R.id.edit_ngaysinh);
//		edit_ngaysinh.setText(dateNowString);
//
//		initProvince();
//
//		edtprovince = (EditText) dialogInsertNew.findViewById(R.id.edtprovince);
//		if (!CommonActivity.isNullOrEmpty(Session.province)) {
//			initDistrict(Session.province);
//			try {
//				GetAreaDal dal = new GetAreaDal(activity);
//				dal.open();
//				edtprovince.setText(dal.getNameProvince(Session.province));
//				province = Session.province;
//				dal.close();
//			} catch (Exception ignored) {
//			}
//		}
//
//		edtprovince.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				intent.putExtra("arrProvincesKey", arrProvince);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "1");
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 106);
//
//			}
//		});
//		edtdistrict = (EditText) dialogInsertNew.findViewById(R.id.edtdistrict);
//		if (!CommonActivity.isNullOrEmpty(Session.province)
//				&& !CommonActivity.isNullOrEmpty(Session.district)) {
//			initPrecinct(Session.province, Session.district);
//			try {
//				GetAreaDal dal = new GetAreaDal(activity);
//				dal.open();
//				edtdistrict.setText(dal.getNameDistrict(Session.province,
//						Session.district));
//				district = Session.district;
//				dal.close();
//			} catch (Exception ignored) {
//			}
//		}
//		edtdistrict.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				intent.putExtra("arrDistrictKey", arrDistrict);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "2");
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 107);
//
//			}
//		});
//		edtprecinct = (EditText) dialogInsertNew.findViewById(R.id.edtprecinct);
//		edtprecinct.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				intent.putExtra("arrPrecinctKey", arrPrecinct);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "3");
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 108);
//			}
//		});
//
//		edtStreetBlock = (EditText) dialogInsertNew
//				.findViewById(R.id.edtStreetBlock); // to
//		edt_streetName = (EditText) dialogInsertNew
//				.findViewById(R.id.edt_streetName); // duong
//		edtHomeNumber = (EditText) dialogInsertNew
//				.findViewById(R.id.edtHomeNumber); // so nha
//
//		edtStreetBlock.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchLocation.class);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("checkKey", "4");
//				mBundle.putString("province", province);
//				mBundle.putString("district", district);
//				mBundle.putString("precinct", precinct);
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 109);
//			}
//		});
//
//		spinner_type_giay_to = (Spinner) dialogInsertNew
//				.findViewById(R.id.spinner_type_giay_to);
//		spinner_sex = (Spinner) dialogInsertNew
//				.findViewById(R.id.spinner_gioitinh);
//		ln_sex = (LinearLayout) dialogInsertNew.findViewById(R.id.ln_sex);
//		// bo sung giay phep kinh doanh cho khach hang doanh nghiep
//		linearsoGPKD = (LinearLayout) dialogInsertNew
//				.findViewById(R.id.linearsoGPKD);
//		linearCMT = (LinearLayout) dialogInsertNew.findViewById(R.id.linearCMT);
//		edit_soGQ = (EditText) dialogInsertNew.findViewById(R.id.edit_soGQ);
//		linearsoGPKD.setVisibility(View.GONE);
//		ln_tin = (LinearLayout) dialogInsertNew.findViewById(R.id.ln_tin);
//		edt_tin = (EditText) dialogInsertNew.findViewById(R.id.edt_tin);
//		lnsogiayto = (LinearLayout) dialogInsertNew
//				.findViewById(R.id.lnsogiayto);
//
//		Button btnRefreshCustType = (Button) dialogInsertNew
//				.findViewById(R.id.btnRefreshCustType);
//		btnRefreshCustType.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				if ("1".equals(subType)) {
//					new CacheDatabaseManager(activity).insertBusTypePre(null);
//					GetListTypeCusPreAsyn getListTypeCusPreAsyn = new GetListTypeCusPreAsyn(
//							activity);
//					getListTypeCusPreAsyn.execute();
//				} else {
//					new CacheDatabaseManager(activity).insertBusType(null);
//					GetListTypeCusAsyn getListTypeCusAsyn = new GetListTypeCusAsyn(
//							activity);
//					getListTypeCusAsyn.execute();
//				}
//			}
//		});
//
//		ArrayAdapter<String> adapterTypePaper = initTypePaper();
//		spinner_type_giay_to.setAdapter(adapterTypePaper);
//
//		if (arrSexBeans != null && arrSexBeans.size() > 0) {
//			arrSexBeans.clear();
//		}
//		initSex();
//
//		edit_ngaysinh.setOnClickListener(editTextListener);
//		edit_ngaycap.setOnClickListener(editTextListener);
//		editngayhethan.setText(dateNowString);
//		editngayhethan.setOnClickListener(editTextListener);
//
//		spinner_type_giay_to
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View view,
//							int position, long id) {
//						idType = arrTypePaperBeans.get(position).getParType();
//						if ("3".equalsIgnoreCase(idType)) {
//							lnngayhethan.setVisibility(View.VISIBLE);
//						} else {
//							lnngayhethan.setVisibility(View.GONE);
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//				});
//		edtnote = (EditText) dialogInsertNew.findViewById(R.id.edtnote);
//		Button btnInserNew = (Button) dialogInsertNew
//				.findViewById(R.id.btnthemmoi);
//		btnInserNew.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// ws check kh ton tai hay chua
//				try {
//					if (validateCusGr()) {
//						if (CommonActivity.isNullOrEmpty(busType)) {
//							Toast.makeText(activity,
//									getString(R.string.confirmloaikh),
//									Toast.LENGTH_SHORT).show();
//						} else if (edit_tenKH.getText().toString() == null
//								|| edit_tenKH.getText().toString().isEmpty()) {
//							Toast.makeText(activity,
//									getString(R.string.checknameKH),
//									Toast.LENGTH_SHORT).show();
//							edit_tenKH.requestFocus();
//						} else if (StringUtils.CheckCharSpecical(edit_tenKH
//                                .getText().toString())) {
//							Toast.makeText(
//									activity,
//									getResources().getString(
//											R.string.checkcharspecical),
//									Toast.LENGTH_SHORT).show();
//							edit_tenKH.requestFocus();
//						} else if (StringUtils.CheckCharSpecical(edit_soGQ
//                                .getText().toString())) {
//							Toast.makeText(
//									activity,
//									getResources().getString(
//											R.string.checkcharspecical),
//									Toast.LENGTH_SHORT).show();
//							edit_soGQ.requestFocus();
//						} else if ("1".equalsIgnoreCase(cusType)
//								&& StringUtils.CheckCharSpecical(edit_socmnd
//                                .getText().toString())) {
//							Toast.makeText(
//									activity,
//									getResources().getString(
//											R.string.checkcharspecical),
//									Toast.LENGTH_SHORT).show();
//							edit_socmnd.requestFocus();
//						} else if (edit_ngaysinh.getText().toString() == null
//								|| edit_ngaysinh.getText().toString().isEmpty()) {
//							Toast.makeText(
//									activity,
//									getString(R.string.message_pleass_input_birth_day),
//									Toast.LENGTH_SHORT).show();
//							edit_ngaysinh.requestFocus();
//						} else if (edit_ngaycap.getText().toString() == null
//								|| edit_ngaycap.getText().toString().isEmpty()) {
//							Toast.makeText(
//									activity,
//									getString(R.string.message_pleass_input_date_give),
//									Toast.LENGTH_SHORT).show();
//							edit_ngaycap.requestFocus();
//						} else if ("1".equalsIgnoreCase(cusType)
//								&& (edt_tin.getText().toString() == null || edt_tin
//										.getText().toString().isEmpty())) {
//							Toast.makeText(activity,
//									getString(R.string.checkmst),
//									Toast.LENGTH_SHORT).show();
//							edt_tin.requestFocus();
//						} else if ("1".equalsIgnoreCase(cusType)
//								&& StringUtils.CheckCharSpecical(edt_tin
//                                .getText().toString())) {
//							Toast.makeText(
//									activity,
//									getResources().getString(
//											R.string.custin_special_character),
//									Toast.LENGTH_SHORT).show();
//							edt_tin.requestFocus();
//						} else {
//							if (!CommonActivity.isNullOrEmpty(edit_socmnd
//									.getText().toString())
//									|| !CommonActivity.isNullOrEmpty(edit_soGQ
//											.getText().toString())) {
//								if (!CommonActivity.isNullOrEmpty(edit_socmnd
//										.getText().toString())) {
//									if ("1".equals(idType)
//											&& "0".equals(cusType)) {
//										if (edit_socmnd.getText().toString()
//												.length() == 9
//												|| edit_socmnd.getText()
//														.toString().length() == 12) {
//											validateCMT(null);
//										} else {
//											Toast.makeText(
//													activity,
//													getString(R.string.checksoidno),
//													Toast.LENGTH_SHORT).show();
//										}
//									} else {
//										validateCMT(null);
//									}
//								} else {
//									validateKHDN(null);
//								}
//							} else {
//								if (CommonActivity.isNullOrEmpty(edit_socmnd
//										.getText().toString())) {
//									Toast.makeText(activity,
//											getString(R.string.checkcmt),
//											Toast.LENGTH_SHORT).show();
//								} else {
//									Toast.makeText(activity,
//											getString(R.string.checksogpkd),
//											Toast.LENGTH_SHORT).show();
//								}
//							}
//						}
//
//					}
//				} catch (ParseException e) {
//					Log.e(Constant.TAG, "btnInserNew " + e.toString(), e);
//				}
//			}
//		});
//
//		Button btnCancel = (Button) dialogInsertNew
//				.findViewById(R.id.btncancel);
//		btnCancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				dialogInsertNew.dismiss();
//			}
//		});
//
//
//
//		dialogInsertNew.show();
//	}
//
//	private boolean validateCusGr() {
//		if (CommonActivity.isNullOrEmpty(edtprovince.getText().toString())) {
//			CommonActivity.toast(activity, R.string.provinceEmpty);
//			return false;
//		}
//		if (CommonActivity.isNullOrEmpty(edtdistrict.getText().toString())) {
//			CommonActivity.toast(activity, R.string.districtEmpty);
//			return false;
//		}
//		if (CommonActivity.isNullOrEmpty(edtprecinct.getText().toString())) {
//			CommonActivity.toast(activity, R.string.precinctEmpty);
//			return false;
//		}
//		if (CommonActivity.isNullOrEmpty(edtStreetBlock.getText().toString())) {
//			CommonActivity.toast(activity, R.string.streetBlockEmpty);
//			return false;
//		}
//		if (StringUtils.CheckCharSpecical(edt_streetName.getText().toString())) {
//			CommonActivity.toast(activity, R.string.checkcharspecical);
//			return false;
//		}
//		if (StringUtils.CheckCharSpecical(edtHomeNumber.getText().toString())) {
//			CommonActivity.toast(activity, R.string.checkcharspecical);
//			return false;
//		}
//		return true;
//	}
//
//	// ==== validate CMT ============
//	private CustommerByIdNoBean validateCMT(CustommerByIdNoBean other)
//			throws ParseException {
//		CustommerByIdNoBean o = null;
//
//		Date birthDate = sdf.parse(edit_ngaysinh.getText().toString());
//		Date issueDate = sdf.parse(edit_ngaycap.getText().toString());
//		Date expiredDate = null;
//		if (!editngayhethan.getText().toString().isEmpty()) {
//			expiredDate = sdf.parse(editngayhethan.getText().toString());
//		}
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int day = cal.get(Calendar.DAY_OF_MONTH);
//		cal.set(year, month, day);
//
//		Date current = cal.getTime();
//
//		cal.add(Calendar.YEAR, -Constant.TEEN);
//		Date date14 = cal.getTime();
//
//		// ===check ngay cap ==========
//		if (!birthDate.before(issueDate)) {
//			CommonActivity.toast(activity, R.string.checkcmtngaycap);
//		} else if (!birthDate.before(date14)) {
//			CommonActivity.toast(activity, R.string.check14Plus);
//		} else if (!issueDate.before(current)) {
//			CommonActivity.toast(activity, R.string.ngaycapnhohonngayhientai);
//		} else {
//			// mt quan doi
//			if (idType.equalsIgnoreCase("3") || idType.equalsIgnoreCase("4")) {
//				if (idType.equalsIgnoreCase("3")) {
//					if (expiredDate != null && !current.before(expiredDate)) {
//						CommonActivity.toast(activity,
//								R.string.checkhethanhientai);
//					} else {
//						if (edit_noicap.getText().toString() != null
//								&& !edit_noicap.getText().toString().isEmpty()) {
//							if ("1".equals(subType)) {
//								if (other != null) {
//									o = getCustomerFromInput(other);
//								} else {
//									CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//											activity);
//									checkPreAsyn.execute(edit_socmnd.getText()
//											.toString(), "");
//								}
//							} else {
//								if (other != null) {
//									o = getCustomerFromInput(other);
//								} else {
//									CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//											activity);
//									checkCustomerAsyn.execute(edit_socmnd
//											.getText().toString(), "");
//								}
//							}
//						} else {
//							CommonActivity
//									.toast(activity, R.string.checknoicap);
//						}
//					}
//				} else {
//					if (edit_noicap.getText().toString() != null
//							&& !edit_noicap.getText().toString().isEmpty()) {
//						if ("1".equals(subType)) {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//										activity);
//								checkPreAsyn.execute(edit_socmnd.getText()
//										.toString(), "");
//							}
//						} else {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//										activity);
//								checkCustomerAsyn.execute(edit_socmnd.getText()
//										.toString(), "");
//							}
//						}
//					} else {
//						CommonActivity.toast(activity, R.string.checknoicap);
//					}
//				}
//			} else {
//				if (!DateTimeUtils.compareAge(issueDate, 15)) {
//					// ===== check noi cap =================
//					if (edit_noicap.getText().toString() != null
//							&& !edit_noicap.getText().toString().isEmpty()) {
//						if ("1".equals(subType)) {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//										activity);
//								checkPreAsyn.execute(edit_socmnd.getText()
//										.toString(), "");
//							}
//						} else {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//										activity);
//								checkCustomerAsyn.execute(edit_socmnd.getText()
//										.toString(), "");
//							}
//						}
//					} else {
//						CommonActivity.toast(activity, R.string.checknoicap);
//					}
//				} else {
//					CommonActivity.toast(activity, R.string.checkDatengaycap);
//				}
//			}
//		}
//
//		return o;
//	}
//
//	private CustommerByIdNoBean validateKHDN(CustommerByIdNoBean other)
//			throws ParseException {
//		CustommerByIdNoBean o = null;
//
//		Date birthDate = sdf.parse(edit_ngaysinh.getText().toString());
//		Date issueDate = sdf.parse(edit_ngaycap.getText().toString());
//		Date expiredDate = null;
//		if (!editngayhethan.getText().toString().isEmpty()) {
//			expiredDate = sdf.parse(editngayhethan.getText().toString());
//		}
//
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int day = cal.get(Calendar.DAY_OF_MONTH);
//		cal.set(year, month, day);
//		Date date = cal.getTime();
//
//		// ===check ngay cap ==========
//		if (birthDate.before(issueDate) || birthDate.equals(issueDate)) {
//
//		} else {
//			CommonActivity.toast(activity, R.string.checkcmtngaycap);
//		}
//		if (!birthDate.before(date)) {
//			CommonActivity.toast(activity, R.string.ngaysinhnhohonngayhientai);
//		} else if (!issueDate.before(date)) {
//			CommonActivity.toast(activity, R.string.ngaycapnhohonngayhientai);
//		} else {
//			// mt quan doi
//			if ((idType.equalsIgnoreCase("3") || idType.equalsIgnoreCase("4")) && !"1".equals(cusType)) {
//				if (idType.equalsIgnoreCase("3")) {
//					if (edit_noicap.getText().toString() != null
//							&& !edit_noicap.getText().toString().isEmpty()) {
//						if ("1".equals(subType)) {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//										activity);
//								checkPreAsyn.execute(edit_socmnd.getText()
//										.toString(), "");
//							}
//						} else {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//										activity);
//								checkCustomerAsyn.execute(edit_socmnd.getText()
//										.toString(), "");
//							}
//						}
//					}
//					else {
//						CommonActivity.toast(activity, R.string.checknoicap);
//					}
//				} else {
//					if (edit_noicap.getText().toString() != null
//							&& !edit_noicap.getText().toString().isEmpty()) {
//						if ("1".equals(subType)) {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//										activity);
//								checkPreAsyn.execute("", edit_soGQ.getText()
//										.toString().trim());
//							}
//						} else {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//										activity);
//								checkCustomerAsyn.execute("", edit_soGQ
//										.getText().toString().trim());
//							}
//						}
//					} else {
//						CommonActivity.toast(activity, R.string.checknoicap);
//					}
//				}
//			} else {
//				if ("1".equals(cusType)) {
//					// ===== check noi cap =================
//					if (edit_noicap.getText().toString() != null
//							&& !edit_noicap.getText().toString().isEmpty()) {
//						if ("1".equals(subType)) {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//										activity);
//								checkPreAsyn.execute("", edit_soGQ.getText()
//										.toString().trim());
//							}
//						} else {
//							if (other != null) {
//								o = getCustomerFromInput(other);
//							} else {
//								CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//										activity);
//								checkCustomerAsyn.execute("", edit_soGQ
//										.getText().toString().trim());
//							}
//						}
//					} else {
//						CommonActivity.toast(activity, R.string.checknoicap);
//					}
//				} else {
//					if (!DateTimeUtils.compareAge(issueDate, 15)) {
//						// ===== check noi cap =================
//						if (edit_noicap.getText().toString() != null
//								&& !edit_noicap.getText().toString().isEmpty()) {
//							if ("1".equals(subType)) {
//								if (other != null) {
//									o = getCustomerFromInput(other);
//								} else {
//									CheckCustomerPreAsyn checkPreAsyn = new CheckCustomerPreAsyn(
//											activity);
//									checkPreAsyn.execute("", edit_soGQ
//											.getText().toString().trim());
//								}
//							} else {
//								if (other != null) {
//									o = getCustomerFromInput(other);
//								} else {
//									CheckCustomerAsyn checkCustomerAsyn = new CheckCustomerAsyn(
//											activity);
//									checkCustomerAsyn.execute("", edit_soGQ
//											.getText().toString().trim());
//								}
//							}
//						} else {
//							CommonActivity
//									.toast(activity, R.string.checknoicap);
//						}
//					} else {
//						CommonActivity.toast(activity,
//								R.string.checkDatengaycap);
//					}
//				}
//			}
//		}
//
//		return o;
//	}
//
//	// move login
//    private final OnClickListener moveLogInAct = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			LoginDialog dialog = new LoginDialog(getActivity(),
//					";connect_mobile;");
//
//			dialog.show();
//
//		}
//	};
//
//	private void removeCus() {
//		if (custommerByIdNoBean != null) {
//			custommerByIdNoBean = null;
//		}
//		if (subType != null && !subType.isEmpty()) {
//			subType = "";
//		}
//
//	}
//
//	private class ChangeCustomerInfoPosAsyn extends
//			AsyncTask<String, String, String> {
//
//		private ProgressDialog progress;
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final CustommerByIdNoBean obj;
//
//		public ChangeCustomerInfoPosAsyn(Context context,
//				CustommerByIdNoBean other) {
//			this.context = context;
//			obj = other;
//
//			Log.d(Constant.TAG, "ChangeCustomerInfoPosAsyn " + obj);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			this.progress = new ProgressDialog(this.context);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.changeCustomerInfoPos));
//			this.progress.setCancelable(false);
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			return changeCustomerInfoPos(obj, params[0], params[1], params[2]);
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (description == null || description.isEmpty()) {
//					description = context
//							.getString(R.string.success_changeCustomerInfoPos);
//				}
//				Dialog dialog = CommonActivity.createAlertDialog(activity,
//						description, getResources()
//								.getString(R.string.app_name));
//				dialog.show();
//
//				if (btnEdit != null && btnEdit.isEnabled()) {
//					btnEdit.setEnabled(false);
//				}
//
//				// if(dialogEditCustomer != null &&
//				// dialogEditCustomer.isShowing()) {
//				// dialogEditCustomer.dismiss();
//				// }
//				// setCurrentTabConnection(position);
//				// ActivityCreateNewRequestHotLine.instance.tHost.setCurrentTab(1);
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private String changeCustomerInfoPos(
//				CustommerByIdNoBean custommerByIdNoBean, String reasonId,
//				String otp, String isdnSendOtp) {
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_changeCustomerInfoPos");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:changeCustomerInfoPos>");
//				rawData.append("<input>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				HashMap<String, String> param = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<smartphoneInputBOCM>");
//
//				if (!CommonActivity.isNullOrEmpty(otp)) {
//					rawData.append("<otp>").append(otp).append("</otp>");
//				}
//				if (!CommonActivity.isNullOrEmpty(isdnSendOtp)) {
//					rawData.append("<isdnSendOtp>").append(isdnSendOtp).append("</isdnSendOtp>");
//				}
//				if (!CommonActivity.isNullOrEmpty(reasonId)) {
//					rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
//				}
//
//				// ========customer ===============
//				rawData.append("<customer>");
//				// TuanTD7
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getCustId())) {
//					rawData.append("<custId>").append(custommerByIdNoBean.getCustId());
//					rawData.append("</custId>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getCusType())) {
//					rawData.append("<busType>").append(custommerByIdNoBean.getCusType());
//					rawData.append("</busType>");
//				}
//
//				if (custommerByIdNoBean.getIdType() != null
//						&& !custommerByIdNoBean.getIdType().isEmpty()
//						&& !(custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//								.getBusPermitNo().isEmpty())) {
//					rawData.append("<idType>").append(custommerByIdNoBean.getIdType());
//					rawData.append("</idType>");
//				}
//
//				String busPermitNo = custommerByIdNoBean.getBusPermitNo();
//				Log.d(Constant.TAG, "FragmentConnectionMobile busPermitNo: "
//						+ busPermitNo);
//				if (!CommonActivity.isNullOrEmpty(busPermitNo)) {
//					rawData.append("<busPermitNo>").append(busPermitNo);
//					rawData.append("</busPermitNo>");
//				} else if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getIdNo())) {
//					rawData.append("<idNo>").append(custommerByIdNoBean.getIdNo());
//					rawData.append("</idNo>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getTin())) {
//					rawData.append("<tin>").append(custommerByIdNoBean.getTin());
//					rawData.append("</tin>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getSex())) {
//					rawData.append("<sex>").append(custommerByIdNoBean.getSex());
//					rawData.append("</sex>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getNameCustomer())) {
//					rawData.append("<name>").append(custommerByIdNoBean.getNameCustomer());
//					rawData.append("</name>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getAddreseCus())) {
//					rawData.append("<address>").append(custommerByIdNoBean.getAddreseCus());
//					rawData.append("</address>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getAreaCode())) {
//					rawData.append("<areaCode>").append(custommerByIdNoBean.getAreaCode());
//					rawData.append("</areaCode>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getBirthdayCus())) {
//					rawData.append("<strBirthDate>").append(custommerByIdNoBean.getBirthdayCus());
//					rawData.append("</strBirthDate>");
//					rawData.append("<birthDateStr>").append(custommerByIdNoBean.getBirthdayCus());
//					rawData.append("</birthDateStr>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getStrIdExpire())) {
//					rawData.append("<strIdExpireDate>").append(custommerByIdNoBean.getStrIdExpire());
//					rawData.append("</strIdExpireDate>");
//					rawData.append("<idExpireDateStr>").append(custommerByIdNoBean.getStrIdExpire());
//					rawData.append("</idExpireDateStr>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getProvince())) {
//					rawData.append("<province>").append(custommerByIdNoBean.getProvince());
//					rawData.append("</province>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getDistrict())) {
//					rawData.append("<district>").append(custommerByIdNoBean.getDistrict());
//					rawData.append("</district>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getPrecint())) {
//					rawData.append("<precinct>").append(custommerByIdNoBean.getPrecint());
//					rawData.append("</precinct>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getStreet_block())) {
//					rawData.append("<streetBlock>").append(custommerByIdNoBean.getStreet_block());
//					rawData.append("</streetBlock>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getStreet())) {
//					rawData.append("<streetName>").append(custommerByIdNoBean.getStreet());
//					rawData.append("</streetName>");
//				}
//
//				if (!CommonActivity
//						.isNullOrEmpty(custommerByIdNoBean.getHome())) {
//					rawData.append("<home>").append(custommerByIdNoBean.getHome());
//					rawData.append("</home>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getAreaCode())) {
//					rawData.append("<areaCode>").append(custommerByIdNoBean.getAreaCode());
//					rawData.append("</areaCode>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getIdIssueDate())) {
//					rawData.append("<idIssueDateStr>").append(custommerByIdNoBean.getIdIssueDate());
//					rawData.append("</idIssueDateStr>");
//					// rawData.append("<IdIssueDateStr>" +
//					// custommerByIdNoBean.getIdIssueDate());
//					// rawData.append("</IdIssueDateStr>");
//					rawData.append("<strIdIssueDate>").append(custommerByIdNoBean.getIdIssueDate());
//					rawData.append("</strIdIssueDate>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getIdIssuePlace())) {
//					rawData.append("<idIssuePlace>").append(custommerByIdNoBean.getIdIssuePlace());
//					rawData.append("</idIssuePlace>");
//				}
//
//
////				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
////						.getNotes())) {
//					rawData.append("<notes>").append(custommerByIdNoBean.getNotes());
//					rawData.append("</notes>");
////				}
//
//				rawData.append("</customer>");
//
//				rawData.append("</smartphoneInputBOCM>");
//				rawData.append("</input>");
//				rawData.append("</ws:changeCustomerInfoPos>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_changeCustomerInfoPos");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//				}
//
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//			return errorCode;
//		}
//	}
//
//	private class AsyntaskGetReasonPos extends
//			AsyncTask<String, Void, ArrayList<Spin>> {
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//
//		private final ProgressBar prbreason;
//		private final Spinner spn_reason_fail;
//
//		private final String actionCode;
//
//		public AsyntaskGetReasonPos(Context context, String actionCode,
//				ProgressBar prbreason, Spinner spn_reason_fail) {
//			this.context = context;
//			this.actionCode = actionCode;
//			this.prbreason = prbreason;
//			this.spn_reason_fail = spn_reason_fail;
//
//			Log.d(Constant.TAG, "AsyntaskGetReasonPos " + actionCode);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (this.prbreason != null) {
//				this.prbreason.setVisibility(View.VISIBLE);
//			}
//		}
//
//		@Override
//		protected ArrayList<Spin> doInBackground(String... params) {
//			return getListReasonByTelServicePos();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<Spin> result) {
//			if (this.prbreason != null) {
//				this.prbreason.setVisibility(View.GONE);
//			}
//			lstReason.clear();
//			if (errorCode.equalsIgnoreCase("0")) {
//				lstReason.add(new Spin("-1",
//						getString(R.string.txt_select_reason)));
//				lstReason.addAll(result);
//				Utils.setDataSpinner(activity, lstReason, spn_reason_fail);
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private ArrayList<Spin> getListReasonByTelServicePos() {
//			// Spin serviceItem = (Spin) spnService.getSelectedItem();
//			ArrayList<Spin> lstReasonPos = null;
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getListReasonByTelServicePos");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListReasonByTelServicePos>");
//				rawData.append("<cmMobileInput>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<actionCode>").append(actionCode).append("</actionCode>");
//				rawData.append("<serviceType></serviceType>");
//				// rawData.append("<serviceType>" + serviceType +
//				// "</serviceType>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListReasonByTelServicePos>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListReasonByTelServicePos");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//				// parser
//				lstReasonPos = parserListGroupPos(original);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstReasonPos;
//		}
//
//		public ArrayList<Spin> parserListGroupPos(String original) {
//			ArrayList<Spin> lstReasonPos = new ArrayList<>();
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			NodeList nodechild = null;
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//				nodechild = doc.getElementsByTagName("lstReasonPos");
//				for (int j = 0; j < nodechild.getLength(); j++) {
//					Element e1 = (Element) nodechild.item(j);
//					Spin spin = new Spin();
//					spin.setValue(parse.getValue(e1, "codeName"));
//					Log.d("LOG", "value: " + spin.getValue());
//					spin.setId(parse.getValue(e1, "reasonId"));
//					Log.d("LOG", "Idddd: " + spin.getId());
//					lstReasonPos.add(spin);
//				}
//			}
//			return lstReasonPos;
//		}
//
//	}
//
//	private class AsyntaskGetReasonPre extends
//			AsyncTask<String, Void, ArrayList<Spin>> {
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//
//		private final ProgressBar prbreason;
//		private final Spinner spn_reason_fail;
//
//		private final String actionCode;
//
//		public AsyntaskGetReasonPre(Context context, String actionCode,
//				ProgressBar prbreason, Spinner spn_reason_fail) {
//			this.context = context;
//			this.actionCode = actionCode;
//			this.prbreason = prbreason;
//			this.spn_reason_fail = spn_reason_fail;
//
//			Log.d(Constant.TAG, "AsyntaskGetReasonPre " + actionCode);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (this.prbreason != null) {
//				this.prbreason.setVisibility(View.VISIBLE);
//			}
//		}
//
//		@Override
//		protected ArrayList<Spin> doInBackground(String... params) {
//			return getListReasonByTelServicePre();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<Spin> result) {
//			if (this.prbreason != null) {
//				this.prbreason.setVisibility(View.GONE);
//			}
//			lstReasonPre.clear();
//			if (errorCode.equalsIgnoreCase("0")) {
//				lstReasonPre.add(new Spin("-1",
//						getString(R.string.txt_select_reason)));
//				lstReasonPre.addAll(result);
//				Utils.setDataSpinner(activity, lstReasonPre, spn_reason_fail);
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private ArrayList<Spin> getListReasonByTelServicePre() {
//			// Spin serviceItem = (Spin) spnService.getSelectedItem();
//			ArrayList<Spin> lstReasonPre = null;
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getListReasonByTelServicePre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListReasonByTelServicePre>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<actionCode>").append(actionCode).append("</actionCode>");
//				rawData.append("<serviceType></serviceType>");
//				// rawData.append("<serviceType>" + serviceType +
//				// "</serviceType>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListReasonByTelServicePre>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListReasonByTelServicePre");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//				// parser
//				lstReasonPre = parserListGroupPre(original);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstReasonPre;
//		}
//
//		public ArrayList<Spin> parserListGroupPre(String original) {
//			ArrayList<Spin> lstReasonPre = new ArrayList<>();
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			NodeList nodechild = null;
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//				nodechild = doc.getElementsByTagName("lstReasonPre");
//				for (int j = 0; j < nodechild.getLength(); j++) {
//					Element e1 = (Element) nodechild.item(j);
//					Spin spin = new Spin();
//					spin.setValue(parse.getValue(e1, "codeName"));
//					Log.d("LOG", "value: " + spin.getValue());
//					spin.setId(parse.getValue(e1, "reasonId"));
//					Log.d("LOG", "Idddd: " + spin.getId());
//					lstReasonPre.add(spin);
//				}
//			}
//			return lstReasonPre;
//		}
//
//	}
//
//	private class ChangeCustomerInfoPreAsyn extends
//			AsyncTask<String, String, String> {
//
//		private ProgressDialog progress;
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final CustommerByIdNoBean obj;
//
//		public ChangeCustomerInfoPreAsyn(Context context,
//				CustommerByIdNoBean other) {
//			this.context = context;
//			obj = other;
//
//			Log.d(Constant.TAG, "ChangeCustomerInfoPreAsyn " + obj);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			this.progress = new ProgressDialog(this.context);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.changeCustomerInfoPre));
//			this.progress.setCancelable(false);
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			return changeCustomerInfoPre(obj, params[0], params[1], params[2]);
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (description == null || description.isEmpty()) {
//					description = context
//							.getString(R.string.success_changeCustomerInfoPre);
//				}
//				Dialog dialog = CommonActivity.createAlertDialog(activity,
//						description, getResources()
//								.getString(R.string.app_name));
//				dialog.show();
//
//				if (btnEdit != null && btnEdit.isEnabled()) {
//					btnEdit.setEnabled(false);
//				}
//
//				// if(dialogEditCustomer != null &&
//				// dialogEditCustomer.isShowing()) {
//				// dialogEditCustomer.dismiss();
//				// }
//				// setCurrentTabConnection(position);
//				// ActivityCreateNewRequest.instance.tHost.setCurrentTab(1);
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(
//									(Activity) context,
//									description,
//									context.getResources().getString(
//											R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private String changeCustomerInfoPre(
//				CustommerByIdNoBean custommerByIdNoBean, String reasonId,
//				String otp, String isdnSendOtp) {
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_changeCustomerInfoPre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:changeCustomerInfoPre>");
//				rawData.append("<input>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				HashMap<String, String> param = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<subscriberConnectedPreBO>");
//
//				if (!CommonActivity.isNullOrEmpty(otp)) {
//					rawData.append("<otp>").append(otp).append("</otp>");
//				}
//				if (!CommonActivity.isNullOrEmpty(isdnSendOtp)) {
//					rawData.append("<isdnSendOtp>").append(isdnSendOtp).append("</isdnSendOtp>");
//				}
//				if (!CommonActivity.isNullOrEmpty(reasonId)) {
//					rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
//				}
//
//				// ========customer ===============
//				rawData.append("<customer>");
//				// TuanTD7
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getCustId())) {
//					rawData.append("<custId>").append(custommerByIdNoBean.getCustId());
//					rawData.append("</custId>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getCusType())) {
//					rawData.append("<busType>").append(custommerByIdNoBean.getCusType());
//					rawData.append("</busType>");
//				}
//
//				if (custommerByIdNoBean.getIdType() != null
//						&& !custommerByIdNoBean.getIdType().isEmpty()
//						&& !(custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//								.getBusPermitNo().isEmpty())) {
//					rawData.append("<idType>").append(custommerByIdNoBean.getIdType());
//					rawData.append("</idType>");
//				}
//
//				String busPermitNo = custommerByIdNoBean.getBusPermitNo();
//				Log.d(Constant.TAG, "FragmentConnectionMobile busPermitNo: "
//						+ busPermitNo);
//				if (!CommonActivity.isNullOrEmpty(busPermitNo)) {
//					rawData.append("<busPermitNo>").append(busPermitNo);
//					rawData.append("</busPermitNo>");
//				} else if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getIdNo())) {
//					rawData.append("<idNo>").append(custommerByIdNoBean.getIdNo());
//					rawData.append("</idNo>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getTin())) {
//					rawData.append("<tin>").append(custommerByIdNoBean.getTin());
//					rawData.append("</tin>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getSex())) {
//					rawData.append("<sex>").append(custommerByIdNoBean.getSex());
//					rawData.append("</sex>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getNameCustomer())) {
//					rawData.append("<name>").append(custommerByIdNoBean.getNameCustomer());
//					rawData.append("</name>");
//				}
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getAddreseCus())) {
//					rawData.append("<address>").append(custommerByIdNoBean.getAddreseCus());
//					rawData.append("</address>");
//				}
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getAreaCode())) {
//					rawData.append("<areaCode>").append(custommerByIdNoBean.getAreaCode());
//					rawData.append("</areaCode>");
//				}
//				if (custommerByIdNoBean.getBirthdayCus() != null
//						&& !custommerByIdNoBean.getBirthdayCus().isEmpty()) {
//					rawData.append("<strBirthDate>").append(custommerByIdNoBean.getBirthdayCus());
//					rawData.append("</strBirthDate>");
//					rawData.append("<birthDateStr>").append(custommerByIdNoBean.getBirthdayCus());
//					rawData.append("</birthDateStr>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getStrIdExpire())) {
//					rawData.append("<strIdExpireDate>").append(custommerByIdNoBean.getStrIdExpire());
//					rawData.append("</strIdExpireDate>");
//					rawData.append("<idExpireDateStr>").append(custommerByIdNoBean.getStrIdExpire());
//					rawData.append("</idExpireDateStr>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getProvince())) {
//					rawData.append("<province>").append(custommerByIdNoBean.getProvince());
//					rawData.append("</province>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getDistrict())) {
//					rawData.append("<district>").append(custommerByIdNoBean.getDistrict());
//					rawData.append("</district>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getPrecint())) {
//					rawData.append("<precinct>").append(custommerByIdNoBean.getPrecint());
//					rawData.append("</precinct>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getStreet_block())) {
//					rawData.append("<streetBlockName>").append(custommerByIdNoBean.getStreet_block());
//					rawData.append("</streetBlockName>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getStreet())) {
//					rawData.append("<streetName>").append(custommerByIdNoBean.getStreet());
//					rawData.append("</streetName>");
//				}
//
//				if (!CommonActivity
//						.isNullOrEmpty(custommerByIdNoBean.getHome())) {
//					rawData.append("<home>").append(custommerByIdNoBean.getHome());
//					rawData.append("</home>");
//				}
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getAreaCode())) {
//					rawData.append("<areaCode>").append(custommerByIdNoBean.getAreaCode());
//					rawData.append("</areaCode>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getIdIssueDate())) {
//					rawData.append("<idIssueDateStr>").append(custommerByIdNoBean.getIdIssueDate());
//					rawData.append("</idIssueDateStr>");
//					// rawData.append("<IdIssueDateStr>" +
//					// custommerByIdNoBean.getIdIssueDate());
//					// rawData.append("</IdIssueDateStr>");
//					rawData.append("<strIdIssueDate>").append(custommerByIdNoBean.getIdIssueDate());
//					rawData.append("</strIdIssueDate>");
//				}
//
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getIdIssuePlace())) {
//					rawData.append("<idIssuePlace>").append(custommerByIdNoBean.getIdIssuePlace());
//					rawData.append("</idIssuePlace>");
//				}
//
////				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
////						.getNotes())) {
//					rawData.append("<notes>").append(custommerByIdNoBean.getNotes());
//					rawData.append("</notes>");
////				}
//
//				rawData.append("</customer>");
//
//				rawData.append("</subscriberConnectedPreBO>");
//				rawData.append("</input>");
//				rawData.append("</ws:changeCustomerInfoPre>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_changeCustomerInfoPre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//				}
//
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//			return errorCode;
//		}
//	}
//
//	private class AsyntaskSendOTP extends
//			AsyncTask<String, Void, ArrayList<Spin>> {
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//
//		private final ProgressBar prbreason;
//
//		private final String custId;
//		private final String isdn;
//		private final String type;
//
//		/**
//		 * custId: mã khách hàng tìm kiếm được typeSystem=SYSTEM_SMARTPHONE
//		 * isdn=Số thuê bao gửi OTP (Số thuê bao nằm trong danh sách số thuê bao
//		 * của khách hàng có thể nhận tin nhắn) type=1 trả trước type=2 trả sau
//		 */
//
//		public AsyntaskSendOTP(Context context, ProgressBar prbreason,
//				String custId, String isdn, String type) {
//			this.context = context;
//			this.prbreason = prbreason;
//			this.custId = custId;
//			this.isdn = isdn;
//			this.type = type;
//
//			Log.d(Constant.TAG, "AsyntaskSendOTP custId " + custId + " isdn "
//					+ isdn + " type " + type);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (this.prbreason != null) {
//				this.prbreason.setVisibility(View.VISIBLE);
//			}
//			btnSendOTP.setEnabled(false);
//		}
//
//		@Override
//		protected ArrayList<Spin> doInBackground(String... params) {
//			return sendOTP();
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<Spin> result) {
//			if (this.prbreason != null) {
//				this.prbreason.setVisibility(View.GONE);
//			}
//			if (errorCode.equalsIgnoreCase("0")) {
//				if (description == null || description.isEmpty()) {
//					description = context.getString(R.string.sendOTP_success);
//				}
//				CommonActivity.toast(activity, description);
//			} else {
//				btnSendOTP.setEnabled(true);
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private ArrayList<Spin> sendOTP() {
//			// Spin serviceItem = (Spin) spnService.getSelectedItem();
//			ArrayList<Spin> lstReasonPos = null;
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_sendOTP");
//				StringBuilder rawData = new StringBuilder();
//
//				rawData.append("<ws:sendOTP>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//
//				rawData.append("<paramCustomerInfoBO>");
//
//				rawData.append("<custId>").append(custId).append("</custId>");
//				rawData.append("<typeSystem>SYSTEM_SMARTPHONE</typeSystem>");
//				rawData.append("<isdn>").append(isdn).append("</isdn>");
//				rawData.append("<type>").append(type).append("</type>");
//
//				rawData.append("</paramCustomerInfoBO>");
//
//				rawData.append("</input>");
//				rawData.append("</ws:sendOTP>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity, "mbccs_sendOTP");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//				// parser
//				lstReasonPos = parserListGroupPos(original);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstReasonPos;
//		}
//
//		public ArrayList<Spin> parserListGroupPos(String original) {
//			ArrayList<Spin> lstReasonPos = new ArrayList<>();
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//			}
//			return lstReasonPos;
//		}
//
//	}
//
//	private class GetListGroupAdressAsyncTask extends
//			AsyncTask<String, Void, ArrayList<AreaObj>> {
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private ProgressBar prb_bar = null;
//		private final String streetBlock;
//
//		public GetListGroupAdressAsyncTask(Context context,
//				ProgressBar progessBar, String streetBlock) {
//			this.context = context;
//			this.prb_bar = progessBar;
//			this.streetBlock = streetBlock;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (prb_bar != null) {
//				prb_bar.setVisibility(View.VISIBLE);
//			}
//		}
//
//		@Override
//		protected ArrayList<AreaObj> doInBackground(String... params) {
//			return getListGroupAddress(params[0]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<AreaObj> result) {
//			super.onPostExecute(result);
//			if (prb_bar != null) {
//				prb_bar.setVisibility(View.GONE);
//			}
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					for (AreaObj areaObj : result) {
//						if (streetBlock.equalsIgnoreCase(areaObj.getAreaCode())) {
//							edtStreetBlock.setText(areaObj.getName());
//							break;
//						}
//					}
//				} else {
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.notStreetBlock),
//							getString(R.string.app_name)).show();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
//			ArrayList<AreaObj> listGroupAdress = null;
//
//			listGroupAdress = new CacheDatabaseManager(
//					MainActivity.getInstance())
//					.getListCacheStreetBlock(parentCode);
//
//			if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
//				errorCode = "0";
//				return listGroupAdress;
//			}
//
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListArea");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListArea>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<parentCode>").append(parentCode).append("</parentCode>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListArea>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity, "mbccs_getListArea");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Responseeeeeeeeee Original group", response);
//
//				// ==== parse xml list ip
//				listGroupAdress = parserListGroup(original);
//				listGroupAdress = parserListGroup(original);
//				if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
//					new CacheDatabaseManager(MainActivity.getInstance())
//							.insertCacheStreetBlock(listGroupAdress, parentCode);
//				}
//			} catch (Exception e) {
//				Log.d("getListIP", e.toString());
//			}
//			return listGroupAdress;
//		}
//
//		public ArrayList<AreaObj> parserListGroup(String original) {
//			ArrayList<AreaObj> listGroupAdress = new ArrayList<>();
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			NodeList nodechild = null;
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//				nodechild = doc.getElementsByTagName("lstArea");
//				for (int j = 0; j < nodechild.getLength(); j++) {
//					Element e1 = (Element) nodechild.item(j);
//					AreaObj areaObject = new AreaObj();
//					areaObject.setName(parse.getValue(e1, "name"));
//					Log.d("name area: ", areaObject.getName());
//					areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
//					listGroupAdress.add(areaObject);
//				}
//			}
//			return listGroupAdress;
//		}
//	}

}
