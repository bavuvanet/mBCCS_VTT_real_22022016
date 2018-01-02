package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class FragmentConnectionMobile extends Fragment {
//
//	private final String logTag = FragmentConnectionMobile.class.getName();
//
//    private Spinner spinner_quanhuyen;
//    private Spinner spinner_phuongxa;
//    private Spinner spinner_loaithuebao;// ,
//								// spinner_loaihd;
//	private NDSpinner spinner_loaihd;
//
//	private Spinner spinner_serviceMobile;
//	private EditText txtisdn, txtserial, txtimsi, txtidcuahang, txtmanv,
//			txtduong, txtsonha;
//	private ExpandableHeightListView lvproduct;
//
//	private LinearLayout lnkhuyenmai;
//    private LinearLayout lnloaithuebao;
//    private LinearLayout lnhopdong;
//    private LinearLayout lnsonha;
//    public static LinearLayout lnButton;
//	private Button btn_connection;
//	private TextView txtpakage;
//	// get data service
//	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<>();
//	private String serviceType = "";
//	private String telecomserviceId = "";
//	// arraylist pakage
//	private ArrayList<PakageChargeBeans> arrPakageChargeBeans = new ArrayList<>();
//	private String offerId = "";
//	private String productCode = "";
//	// arrlist HTHM
//	private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<>();
//	private String hthm = "";
//	// arrlist htkms
//	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans = new ArrayList<>();
//	private String maKM = "-1";
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
//	private ArrayList<SubTypeBeans> arrSubTypeBeans = new ArrayList<>();
//	private String subTypeMobile = "";
//	// ====== arrlist ========
//	private ArrayList<ConTractBean> arrTractBeans = new ArrayList<>();
//	private String contractNo = "";
//	private String contractId = "";
//	// arrlist ds hang hoa
//	private final ArrayList<StockTypeBeans> arrStockTypeBeans = new ArrayList<>();
//	private GetProductAdapter adapProductAdapter;
//	// ==========map hang hoa =================
//	private final Map<String, StockTypeDetailBeans> mapSubStockModelRelReq = new HashMap<>();
//
//	// ======== show dialog insert Serial
//	private Dialog dialogInsertSerial;
//	private Dialog dialogShowDetailProduct;
//	private EditText edtserial;
//	private static String subType = "";
//
//	private PakageBundeBean pakageBundeBean = new PakageBundeBean();
//	private View mView;
//	private PakageChargeBeans pakaChargeBeans = null;
//	private CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
//	public static CustommerByIdNoBean custommerByIdNoBeanCheck = new CustommerByIdNoBean();
//	public static String subTypeCheck = "";
//	private int positonservice = -1;
//	private String isdn = "";
//
//	private String tenduong = "";
//	private String sonha = "";
//	private String to = "";
//
//	// define view form specical paper
//	private LinearLayout lnTTGoiCuocDacBiet;
//	private Spinner spDoiTuong;
//    private EditText tvDonVi;
//    private EditText edtMaGiayToDacBiet;
//    private EditText edtNgayBD;
//    private EditText edtNgayKT;
//
//	private ArrayList<SpecObject> arrSpecObjects = new ArrayList<>();
//	private ObjDoiTuongAdapter doiTuongAdapter;
//	private String mCodeDoiTuong = "";
//
//	// define time ngay bat dau va ngay het thuc
//	private Calendar cal;
//	private int day;
//	private int month;
//	private int year;
//
//	private String mNgayBatDau = "";
//	private String mNgayKetThuc = "";
//
//	private String mCodeDonVi = "";
//	private RelativeLayout rlchondonvi;
//
//	private ArrayList<DoiTuongObj> mAratListDonVi = new ArrayList<>();
//	private ListView lvListDonVi;
//	private Dialog dialogDonVi = null;
//
//	private final BCCSGateway mBccsGateway = new BCCSGateway();
//
//	private String dateNowString = null;
//	private Date dateBD = null;
//	private Date dateNow = null;
//	private Date dateEnd = null;
//
//	private View imgDeleteDV;
//
//	private ListView lvUploadImage;
//	private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
//	private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;
//
//	boolean isUpImage = false;
//
//	private Spinner spinner_to;
//	private ArrayList<AreaObj> arrTo = new ArrayList<>();
//	private ArrayList<AreaObj> arrToDialog = new ArrayList<>();
//	private UploadImageAdapter adapter;
//
//	private String reasonId;
//
//	private Activity activity;
//
//	private Date timeStart = null;
//	private Date timeEnd = null;
//
//	private EditText edt_hthm, edt_kmai;
//
//    private LinearLayout ln_quota;
//
//	private ArrayList<ContractOffersObj> arrContractOffersObj = new ArrayList<>();
//
//	private ProgressBar prb_contract, prb_to, prb_todialog, prb_todialog1;
//
//	private static FragmentConnectionMobile instance = null;
//
//	private Spinner spinner_cuocdongtruoc;
//	private LinearLayout lncuocdongtruoc;
//	private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeans = new ArrayList<>();
//	private String prepaidCode = "";
//
//	// bo sung chon serial
//	// private static List<StockModel> mListStockModel = new
//	// ArrayList<StockModel>();
//	private final List<StockModel> mListStockModel = new ArrayList<>();
//
//    private Button btnRefreshStreetBlock;
//	private Button btnRefreshStreetBlockDialog;
//
//	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//	@Override
//	public void onAttach(Activity _activity) {
//		super.onAttach(_activity);
//		activity = _activity;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		instance = FragmentConnectionMobile.this;
//		Calendar calendar = Calendar.getInstance();
//		int fromYear = calendar.get(Calendar.YEAR);
//		int fromMonth = calendar.get(Calendar.MONTH) + 1;
//		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
//		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
//		try {
//			dateNow = sdf.parse(dateNowString);
//			dateBD = sdf.parse(dateNowString);
//			dateEnd = sdf.parse(dateNowString);
//		} catch (Exception e) {
//			Log.e("Exception", e.toString(), e);
//		}
//
//		cal = Calendar.getInstance();
//		day = cal.get(Calendar.DAY_OF_MONTH);
//		month = cal.get(Calendar.MONTH);
//		year = cal.get(Calendar.YEAR);
//
//		if (pakaChargeBeans != null) {
//			pakaChargeBeans = new PakageChargeBeans();
//		}
//		custommerByIdNoBean = FragmentInfoCustomerMobile.custommerByIdNoBean;
//		subType = FragmentInfoCustomerMobile.subType;
//
//		// Log.d(logTag, "FragmentConnectionMobile onCreateView cusType: " +
//		// custommerByIdNoBean.getCusType() + " subType: " + subType);
//
//		custommerByIdNoBeanCheck = FragmentInfoCustomerMobile.custommerByIdNoBean;
//		if (custommerByIdNoBean != null) {
//			if (custommerByIdNoBean.getNameCustomer() != null
//					&& !custommerByIdNoBean.getNameCustomer().isEmpty()) {
//
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.checkcusid),
//						getString(R.string.app_name), movetabInfoCus).show();
//			}
//		} else {
//			CommonActivity.createAlertDialog(activity,
//					getString(R.string.checkcusid),
//					getString(R.string.app_name), movetabInfoCus).show();
//		}
//		if (mView == null) {
//			mView = inflater.inflate(R.layout.connectionmobile_layout_info,
//					container, false);
//			subTypeCheck = subType;
//
//			unitView(mView);
//		} else {
//			if (!subTypeCheck.equalsIgnoreCase(subType)) {
//				if (custommerByIdNoBeanCheck != null
//						&& custommerByIdNoBean != null) {
//					mView = inflater.inflate(
//							R.layout.connectionmobile_layout_info, container,
//							false);
//					unitView(mView);
//					subTypeCheck = subType;
//					custommerByIdNoBeanCheck = custommerByIdNoBean;
//				} else {
//					mView = inflater.inflate(
//							R.layout.connectionmobile_layout_info, container,
//							false);
//					unitView(mView);
//					subTypeCheck = subType;
//					custommerByIdNoBeanCheck = custommerByIdNoBean;
//				}
//			} else {
//				if (custommerByIdNoBeanCheck != null
//						&& custommerByIdNoBean != null) {
//					if (custommerByIdNoBeanCheck.getIdNo() != null
//							&& custommerByIdNoBean.getIdNo() != null) {
//						if (custommerByIdNoBeanCheck
//								.getIdNo()
//								.equalsIgnoreCase(custommerByIdNoBean.getIdNo())) {
//							((ViewGroup) mView.getParent()).removeAllViews();
//							subTypeCheck = subType;
//							custommerByIdNoBeanCheck = custommerByIdNoBean;
//						} else {
//							mView = inflater.inflate(
//									R.layout.connectionmobile_layout_info,
//									container, false);
//							unitView(mView);
//							subTypeCheck = subType;
//							custommerByIdNoBeanCheck = custommerByIdNoBean;
//						}
//					} else {
//						((ViewGroup) mView.getParent()).removeAllViews();
//						subTypeCheck = subType;
//						custommerByIdNoBeanCheck = custommerByIdNoBean;
//					}
//				} else {
//					((ViewGroup) mView.getParent()).removeAllViews();
//					subTypeCheck = subType;
//					custommerByIdNoBeanCheck = custommerByIdNoBean;
//				}
//			}
//		}
//
//		return mView;
//	}
//
//	@Override
//	public void onResume() {
//		Log.e(logTag, "FragmentConnectionMobile onResume");
//		super.onResume();
//        MainActivity.getInstance().setTitleActionBar(R.string.servicemobile);
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		Log.d("TAG 9",
//				"FragmentConnectionMobile onActivityResult requestCode : "
//						+ requestCode);
//		if (resultCode == Activity.RESULT_OK) {
//			switch (requestCode) {
//
//			case 3333:
//				if (data != null) {
//					pakaChargeBeans = (PakageChargeBeans) data.getExtras()
//							.getSerializable("pakageChargeKey");
//					if (pakaChargeBeans != null) {
//						txtpakage.setText(Html.fromHtml("<u>"
//								+ pakaChargeBeans.getOfferName() + "</u>"));
//						offerId = pakaChargeBeans.getOfferId();
//						productCode = pakaChargeBeans.getProductCode();
//						if ("1".equals(subType)) {
//							if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
//								arrHthmBeans.clear();
//							}
//
//							if (arrSpecObjects != null
//									&& !arrSpecObjects.isEmpty()) {
//								arrSpecObjects.clear();
//							}
//							// lay hinh thuc hoa mang tra truoc
//							if (CommonActivity.isNetworkConnected(activity)) {
//								if (offerId != null && !offerId.isEmpty()) {
//
//									// TODO LAY THONG TIN GOI CUOC
//									if (Constant.OFFERID_TSV_SPEC
//											.contains(offerId)) {
//										GetProductSpecInfoPreAsyn getInfoPreAsyn = new GetProductSpecInfoPreAsyn(
//												activity);
//										getInfoPreAsyn.execute(offerId,
//												custommerByIdNoBean.getIdNo());
//									} else {
//										spDoiTuong.setEnabled(true);
//										mCodeDoiTuong = "";
//										mCodeDonVi = "";
//										lnTTGoiCuocDacBiet
//												.setVisibility(View.GONE);
//										if (arrSpecObjects != null
//												&& !arrSpecObjects.isEmpty()) {
//											arrSpecObjects.clear();
//										}
//										if (doiTuongAdapter != null) {
//											doiTuongAdapter
//													.notifyDataSetChanged();
//										}
//									}
//									GetHTHMPREAsyn getHTHMPREAsyn = new GetHTHMPREAsyn(
//											activity);
//									getHTHMPREAsyn
//											.execute(serviceType, offerId);
//								}
//							} else {
//								CommonActivity.createAlertDialog(activity,
//										getString(R.string.errorNetwork),
//										getString(R.string.app_name)).show();
//							}
//						} else {
//							// lay hthm tra sau
//							if (CommonActivity.isNetworkConnected(activity)) {
//								if (arrHthmBeans != null
//										&& arrHthmBeans.size() > 0) {
//									arrHthmBeans.clear();
//								}
//								GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(
//										activity);
//								getHTHMAsyn.execute(serviceType, offerId);
//								if (arrSubTypeBeans != null
//										&& arrSubTypeBeans.size() > 0) {
//									arrSubTypeBeans.clear();
//								}
//								GetListSubTypeAsyn getListSubTypeAsyn = new GetListSubTypeAsyn(
//										activity);
//								getListSubTypeAsyn.execute(serviceType,
//										productCode);
//							} else {
//								CommonActivity.createAlertDialog(activity,
//										getString(R.string.errorNetwork),
//										getString(R.string.app_name)).show();
//							}
//
//						}
//
//					}
//				}
//				break;
//
//			case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
//				Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");
//
//				Parcelable[] parcelableUris = data
//						.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
//
//				if (parcelableUris == null) {
//					return;
//				}
//				// Java doesn't allow array casting, this is a little hack
//				Uri[] uris = new Uri[parcelableUris.length];
//				System.arraycopy(parcelableUris, 0, uris, 0,
//						parcelableUris.length);
//
//				int imageId = data.getExtras().getInt("imageId", -1);
//
//				Log.d(Constant.TAG,
//						"FragmentConnectionMobile onActivityResult() imageId: "
//								+ imageId);
//
//				if (uris != null && uris.length > 0 && imageId >= 0) {
//					ViewHolder holder = null;
//					for (int i = 0; i < lvUploadImage.getChildCount(); i++) {
//						View rowView = lvUploadImage.getChildAt(i);
//						ViewHolder h = (ViewHolder) rowView.getTag();
//						if (h != null) {
//							int id = h.ibUploadImage.getId();
//							if (imageId == id) {
//								holder = h;
//								break;
//							}
//						}
//					}
//					if (holder != null) {
//						Picasso.with(activity)
//								.load(new File(uris[0].toString()))
//								.centerCrop().resize(100, 100)
//								.into(holder.ibUploadImage);
//
//						int position = holder.spUploadImage
//								.getSelectedItemPosition();
//
//						if (position < 0) {
//							position = 0;
//						}
//
//						ArrayList<RecordPrepaid> recordPrepaids = mapListRecordPrepaid
//								.get(String.valueOf(imageId));
//
//						if (recordPrepaids != null) {
//							RecordPrepaid recordPrepaid = recordPrepaids
//									.get(position);
//							String spinnerCode = recordPrepaid.getCode();
//
//							Log.i(Constant.TAG, "imageId: " + imageId
//									+ " spinner position: " + position
//									+ " spinnerCode: " + spinnerCode
//									+ " uris: " + uris.length);
//
//							ArrayList<FileObj> fileObjs = new ArrayList<>();
//							for (int i = 0; i < uris.length; i++) {
//								File uriFile = new File(uris[i].getPath());
//								String fileNameServer = spinnerCode + "-"
//										+ (i + 1) + ".jpg";
//								FileObj obj = new FileObj(spinnerCode, uriFile,
//										uris[i].getPath(), fileNameServer);
//								fileObjs.add(obj);
//							}
//							hashmapFileObj.put(String.valueOf(imageId),
//									fileObjs);
//						} else {
//							Log.d(Constant.TAG,
//									"FragmentConnectionMobile onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
//											+ mapListRecordPrepaid.size()
//											+ " "
//											+ mapListRecordPrepaid);
//						}
//					} else {
//						Log.d(Constant.TAG,
//								"FragmentConnectionMobile onActivityResult() holder NULL");
//					}
//				} else {
//					Log.d(Constant.TAG,
//							"FragmentConnectionMobile onActivityResult() uris NULL");
//				}
//				break;
//			case 101:
//                HTHMBeans hthmBeans = (HTHMBeans) data.getExtras().getSerializable(
//                        "HTHMBeans");
//				reloadDataHTHM(hthmBeans);
//				break;
//
//			case 102:
//				// PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans)
//				// data
//				// .getExtras().getSerializable("PromotionTypeBeans");
//				// maKM = promotionTypeBeans.getPromProgramCode();
//				// lncuocdongtruoc.setVisibility(View.VISIBLE);
//				// GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn =
//				// new GetAllListPaymentPrePaidAsyn(
//				// activity);
//				// getAllListPaymentPrePaidAsyn.execute(maKM, productCode,
//				// province, dateNowString);
//				//
//				// edt_kmai.setText(promotionTypeBeans.toString());
//
//				PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans) data
//						.getExtras().getSerializable("PromotionTypeBeans");
//				if (promotionTypeBeans != null) {
//					if (!CommonActivity.isNullOrEmpty(promotionTypeBeans
//							.getPromProgramCode())) {
//						if (!promotionTypeBeans.getPromProgramCode().equals(
//								"-1")) {
//							maKM = promotionTypeBeans.getPromProgramCode();
//							edt_kmai.setText(promotionTypeBeans.getName()
//									.toString());
//							lncuocdongtruoc.setVisibility(View.VISIBLE);
//							GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
//									getActivity());
//							getAllListPaymentPrePaidAsyn.execute(maKM,
//									productCode, province, dateNowString);
//						} else {
//							maKM = "-1";
//							edt_kmai.setText(getString(R.string.chonctkm1));
//							lncuocdongtruoc.setVisibility(View.GONE);
//							// maKM = promotionTypeBeans.getPromProgramCode();
//							// txtchonkm.setText(getString(R.string.chonctkm1));
//							// lncuocdongtruoc.setVisibility(View.VISIBLE);
//							// GetAllListPaymentPrePaidAsyn
//							// getAllListPaymentPrePaidAsyn = new
//							// GetAllListPaymentPrePaidAsyn(
//							// FragmentConnectionInfoSetting.this);
//							// getAllListPaymentPrePaidAsyn.execute(maKM,
//							// productCode, txttinhTP.getText().toString()
//							// .trim(), dateNowString);
//						}
//
//					} else {
//						// maKM = "";
//						// lncuocdongtruoc.setVisibility(View.GONE);
//						// txtchonkm.setText(getString(R.string.selectMKM));
//						maKM = "";
//						edt_kmai.setText(getString(R.string.selectMKM));
//						lncuocdongtruoc.setVisibility(View.VISIBLE);
//						GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
//								getActivity());
//						getAllListPaymentPrePaidAsyn.execute(maKM, productCode,
//								province, dateNowString);
//					}
//				} else {
//					maKM = "-1";
//					edt_kmai.setText(getString(R.string.chonctkm1));
//					lncuocdongtruoc.setVisibility(View.GONE);
//				}
//
//				break;
//
//			default:
//				break;
//			}
//		}
//	}
//
//	private void reloadDataHTHM(HTHMBeans hthmBeans) {
//
//		if (hthmBeans.getName().equalsIgnoreCase(getString(R.string.chonhthm))) {
//			edt_hthm.setText(getString(R.string.chonhthm));
//
//			hthm = "";
//			reasonId = "";
//
//			if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0
//					&& adapProductAdapter != null) {
//				arrStockTypeBeans.clear();
//				adapProductAdapter = new GetProductAdapter(arrStockTypeBeans,
//						activity);
//				lvproduct.setAdapter(adapProductAdapter);
//				adapProductAdapter.notifyDataSetChanged();
//			}
//			if (mapSubStockModelRelReq != null
//					&& mapSubStockModelRelReq.size() > 0) {
//				mapSubStockModelRelReq.clear();
//			}
//			maKM = "-1";
//			prepaidCode = "";
//			if (arrPromotionTypeBeans != null
//					&& arrPromotionTypeBeans.size() > 0) {
//				arrPromotionTypeBeans.clear();
//				initInitListPromotionNotData();
//			}
//
//		} else {
//			edt_hthm.setText(hthmBeans.toString());
//
//			if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0) {
//				arrStockTypeBeans.clear();
//			}
//			if (mapSubStockModelRelReq != null
//					&& mapSubStockModelRelReq.size() > 0) {
//				mapSubStockModelRelReq.clear();
//			}
//
//			if (arrPromotionTypeBeans != null
//					&& arrPromotionTypeBeans.size() > 0) {
//				arrPromotionTypeBeans.clear();
//			}
//
//			hthm = hthmBeans.getCode();
//			reasonId = hthmBeans.getReasonId();
//
//			if (serviceType != null && !serviceType.isEmpty()) {
//				if ("1".equalsIgnoreCase(subType)) {
//					if (CommonActivity.isNetworkConnected(activity)) {
//						GetListProductPreAsyn getListProductPreAsyn = new GetListProductPreAsyn(
//								activity);
//						getListProductPreAsyn.execute(hthm, productCode,
//								serviceType);
//
//						if (adapProductAdapter != null) {
//							adapProductAdapter.notifyDataSetChanged();
//						}
//						if (adapter != null) {
//							adapter.notifyDataSetChanged();
//						}
//
//						GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask(
//								"" + reasonId, productCode, activity);
//						getLisRecordPrepaidTask.execute();
//
//					} else {
//						CommonActivity.createAlertDialog(activity,
//								getString(R.string.errorNetwork),
//								getString(R.string.app_name)).show();
//					}
//
//				} else {
//					if (CommonActivity.isNetworkConnected(activity)) {
//						if (arrPromotionTypeBeans != null
//								&& arrPromotionTypeBeans.size() > 0) {
//							arrPromotionTypeBeans = new ArrayList<>();
//						}
//						maKM = "-1";
//						GetListProductPosAsyn getListProductPosAsyn = new GetListProductPosAsyn(
//								activity);
//						getListProductPosAsyn.execute(hthm, productCode,
//								serviceType);
//
//						GetPromotionByMap getPromotionByMap = new GetPromotionByMap(
//								activity);
//						getPromotionByMap.execute(serviceType, hthm, offerId,
//								province, district, precinct);
//
//						if (adapProductAdapter != null) {
//							adapProductAdapter.notifyDataSetChanged();
//						}
//
//						if (adapter != null) {
//							adapter.notifyDataSetChanged();
//						}
//
//						GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask(
//								"" + reasonId, productCode, activity);
//						getLisRecordPrepaidTask.execute();
//
//					} else {
//						CommonActivity.createAlertDialog(activity,
//								getString(R.string.errorNetwork),
//								getString(R.string.app_name)).show();
//					}
//
//				}
//			}
//		}
//	}
//
//	private final OnClickListener movetabInfoCus = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			ActivityCreateNewRequestMobile.instance.tHost.setCurrentTab(0);
//
//		}
//	};
//
//	private void unitView(View v) {
//
//		// bo sung cam ket
//        TextView tvcamketso = (TextView) v.findViewById(R.id.tvcamketso);
//		tvcamketso.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if (CommonActivity.isNetworkConnected(getActivity())) {
//
//					if (validateViewCommitment()) {
//						if ("1".equals(subType)) {
//							AsynctaskviewSubCommitmentPre asynctaskviewSubCommitmentPre = new AsynctaskviewSubCommitmentPre(
//									getActivity());
//							asynctaskviewSubCommitmentPre.execute(offerId,
//									hthm, txtisdn.getText().toString().trim());
//						} else {
//							AsynctaskviewSubCommitmentPos asynctaskviewSubCommitmentPos = new AsynctaskviewSubCommitmentPos(
//									getActivity());
//							asynctaskviewSubCommitmentPos.execute(offerId,
//									hthm, txtisdn.getText().toString().trim());
//						}
//					}
//				}
//			}
//		});
//		// bo sung progress
//		prb_contract = (ProgressBar) v.findViewById(R.id.prb_contract);
//		prb_to = (ProgressBar) v.findViewById(R.id.prb_to);
//
//		spinner_serviceMobile = (Spinner) v.findViewById(R.id.spinner_service);
//		txtpakage = (TextView) v.findViewById(R.id.tvpakage);
//
//		txtpakage.setOnClickListener(this);
//		// ====TOSV1
//		txtpakage.setText(Html.fromHtml("<u>"
//				+ getString(R.string.chonpakage_mobile) + "</u>"));
//		edt_hthm = (EditText) v.findViewById(R.id.edt_hthm);
//		edt_kmai = (EditText) v.findViewById(R.id.edt_kmai);
//        Spinner spinner_province = (Spinner) v.findViewById(R.id.spinner_province);
//		spinner_quanhuyen = (Spinner) v.findViewById(R.id.spinner_district);
//		spinner_phuongxa = (Spinner) v.findViewById(R.id.spinner_precint);
//		spinner_loaihd = (NDSpinner) v.findViewById(R.id.spinner_loaihd);
//		spinner_loaithuebao = (Spinner) v
//				.findViewById(R.id.spinner_loaithuebao);
//        LinearLayout lnMaNV = (LinearLayout) v.findViewById(R.id.lnMaNV);
//		lnkhuyenmai = (LinearLayout) v.findViewById(R.id.lnkhuyenmai);
//		lnloaithuebao = (LinearLayout) v.findViewById(R.id.lnloaithuebao);
//		lnhopdong = (LinearLayout) v.findViewById(R.id.lnhopdong);
//		txtisdn = (EditText) v.findViewById(R.id.txtisdn);
//		txtserial = (EditText) v.findViewById(R.id.txtserial);
//		txtimsi = (EditText) v.findViewById(R.id.txtimsi);
//		txtidcuahang = (EditText) v.findViewById(R.id.txtidcuahang);
//		txtmanv = (EditText) v.findViewById(R.id.txtmanv);
//		lnsonha = (LinearLayout) v.findViewById(R.id.lnsonha);
//		// txtto = (EditText) v.findViewById(R.id.txtto);
//		txtduong = (EditText) v.findViewById(R.id.txtduong);
//		txtsonha = (EditText) v.findViewById(R.id.txtsonha);
//		lvproduct = (ExpandableHeightListView) v.findViewById(R.id.lvproduct);
//		lvproduct.setOnItemClickListener(this);
//		lvproduct.setExpanded(true);
//		btn_connection = (Button) v.findViewById(R.id.btn_connection);
//		lnButton = (LinearLayout) v.findViewById(R.id.lnButton);
//
//		// define specical paper
//
//		lnTTGoiCuocDacBiet = (LinearLayout) v
//				.findViewById(R.id.lnGoiCuocDacBiet);
//		lnTTGoiCuocDacBiet.setVisibility(View.GONE);
//		spDoiTuong = (Spinner) v.findViewById(R.id.spDoiTuong);
//        EditText edtQuocTich = (EditText) v.findViewById(R.id.edtQuocTich);
//		edtQuocTich.setText(getString(R.string.viet_nam));
//		edtQuocTich.setEnabled(false);
//		tvDonVi = (EditText) v.findViewById(R.id.tvDonVi);
//		edtMaGiayToDacBiet = (EditText) v.findViewById(R.id.edtMaGiayToDacBiet);
//		edtNgayBD = (EditText) v.findViewById(R.id.edtNgayBD);
//		edtNgayBD.setText(dateNowString);
//		edtNgayBD.setOnClickListener(this);
//		edtNgayKT = (EditText) v.findViewById(R.id.edtNgayKT);
//		edtNgayKT.setText(dateNowString);
//		edtNgayKT.setOnClickListener(this);
//		rlchondonvi = (RelativeLayout) v.findViewById(R.id.rlchondonvi);
//
//		// bo song cuoc dong truoc
//		spinner_cuocdongtruoc = (Spinner) v
//				.findViewById(R.id.spinner_cuocdongtruoc);
//		lncuocdongtruoc = (LinearLayout) v.findViewById(R.id.lncuocdongtruoc);
//		lncuocdongtruoc.setVisibility(View.GONE);
//		spinner_cuocdongtruoc
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//						if (arrPaymentPrePaidPromotionBeans != null
//								&& !arrPaymentPrePaidPromotionBeans.isEmpty()) {
//							prepaidCode = arrPaymentPrePaidPromotionBeans.get(
//									arg2).getPrePaidCode();
//							if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
//								showSelectCuocDongTruoc(arrPaymentPrePaidPromotionBeans
//										.get(arg2));
//							}
//
//						} else {
//							prepaidCode = "";
//						}
//
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//				});
//
//		imgDeleteDV = v.findViewById(R.id.imgDeleteDonvi);
//		imgDeleteDV.setVisibility(View.GONE);
//		imgDeleteDV.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				tvDonVi.setText("");
//				mCodeDonVi = "";
//				imgDeleteDV.setVisibility(View.GONE);
//			}
//		});
//
//		spinner_to = (Spinner) v.findViewById(R.id.spinner_to);
//
//		spinner_to.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				if (arrTo != null && !arrTo.isEmpty()) {
//					to = arrTo.get(arg2).getAreaCode();
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//		lvUploadImage = (ListView) v.findViewById(R.id.lvUploadImage);
//
//		spDoiTuong.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				mCodeDoiTuong = arrSpecObjects.get(arg2).getCode();
//				// TOGO LAY THONG TIN SINH VIEN TU MA DOI TUONG
//				// mCodeDoiTuong = arrSpecObjects.get(position).getCode();
//
//				// check neu code = NEW_STU_2015 thi goi ws lay so bao danh va
//				// an chon dv di
//				if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
//					// rlchondonvi.setVisibility(View.GONE);
//					// rlquoctich.setVisibility(View.GONE);
//					if (CommonActivity.isNetworkConnected(activity)) {
//
//						CheckInfoCusSpecialAsyn checkInfoCusSpecialAsyn = new CheckInfoCusSpecialAsyn(
//								activity);
//						checkInfoCusSpecialAsyn.execute(custommerByIdNoBean
//								.getIdNo());
//						// TODO XU LY
//					} else {
//						CommonActivity.createAlertDialog(activity,
//								getString(R.string.errorNetwork),
//								getString(R.string.app_name)).show();
//					}
//				} else {
//					// an linear donvi di
//					rlchondonvi.setVisibility(View.VISIBLE);
//					edtMaGiayToDacBiet.setEnabled(true);
//					edtMaGiayToDacBiet.setText("");
//					// rlquoctich.setVisibility(View.VISIBLE);
//				}
//
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
//
//		tvDonVi.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				timKiemDonVi();
//			}
//		});
//
//		btn_connection.setOnClickListener(this);
//		CheckTBTraSau();
//		// init dich vu
//		if (arrTelecomServiceBeans != null && arrTelecomServiceBeans.size() > 0) {
//			arrTelecomServiceBeans.clear();
//		}
//		initTelecomService();
//		// init province
//		initProvince(spinner_province, null, -1);
//		if (pakageBundeBean.getArrChargeBeans() != null
//				&& pakageBundeBean.getArrChargeBeans().size() > 0) {
//			pakageBundeBean = new PakageBundeBean();
//		}
//
//		if ("2".equalsIgnoreCase(subType)) {
//			// init contract
//			if (arrTractBeans != null && arrTractBeans.size() > 0) {
//				arrTractBeans.clear();
//			}
//			if (custommerByIdNoBean != null) {
//				if (CommonActivity.isNetworkConnected(activity)) {
//					GetConTractAsyn getConTractAsyn = new GetConTractAsyn(
//							activity);
//					getConTractAsyn.execute(custommerByIdNoBean.getCustId(),
//							custommerByIdNoBean.getIdNo(),
//							custommerByIdNoBean.getBusPermitNo());
//				} else {
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.errorNetwork),
//							getString(R.string.app_name)).show();
//				}
//			}
//		}
//
//		spinner_serviceMobile
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//
//						if (arg2 > 0 && positonservice != arg2) {
//							if (arrPakageChargeBeans != null
//									&& arrPakageChargeBeans.size() > 0) {
//								arrPakageChargeBeans.clear();
//								txtpakage.setText(Html.fromHtml("<u>"
//										+ getString(R.string.chonpakage_mobile)
//										+ "</u>"));
//								productCode = "";
//								offerId = "";
//							}
//							serviceType = arrTelecomServiceBeans.get(arg2)
//									.getServiceAlias();
//
//							if ("2".equalsIgnoreCase(subType)
//									&& "M".equalsIgnoreCase(serviceType)) {
//								Log.d("Log", "unitView subType: " + subType
//										+ " serviceType: " + serviceType
//										+ " VISIBLE");
//								ln_quota.setVisibility(View.GONE);
//							} else {
//								Log.d("Log", "unitView subType: " + subType
//										+ " serviceType: " + serviceType
//										+ " GONE");
//								ln_quota.setVisibility(View.GONE);
//							}
//
//							telecomserviceId = arrTelecomServiceBeans.get(arg2)
//									.getTelecomServiceId();
//							if (CommonActivity.isNetworkConnected(activity)) {
//								if (subType != null && !subType.isEmpty()) {
//									if (telecomserviceId != null
//											&& !telecomserviceId.isEmpty()) {
//										GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(
//												activity);
//										getListPakageAsyn.execute(
//												telecomserviceId, subType);
//									}
//
//								}
//
//							} else {
//								CommonActivity.createAlertDialog(activity,
//										getString(R.string.errorNetwork),
//										getString(R.string.app_name)).show();
//							}
//
//						} else {
//							// serviceType = "";
//							// ReLoadData();
//
//						}
//						positonservice = arg2;
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//
//					}
//				});
//
//		spinner_province
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//						if (arg2 > 0) {
//							province = arrProvince.get(arg2).getProvince();
//							initDistrict(province, spinner_quanhuyen, -1);
//						} else {
//							if (arrDistrict != null && !arrDistrict.isEmpty()) {
//								arrDistrict = new ArrayList<>();
//							}
//							initDistrict(province, spinner_quanhuyen, -1);
//							if (arrPrecinct != null && !arrPrecinct.isEmpty()) {
//								arrPrecinct = new ArrayList<>();
//							}
//							initPrecinct(province, district, spinner_phuongxa,
//									-1);
//						}
//
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//				});
//		spinner_quanhuyen
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//
//						if (arg2 > 0) {
//							district = arrDistrict.get(arg2).getDistrict();
//							initPrecinct(province, district, spinner_phuongxa,
//									-1);
//						} else {
//							if (arrPrecinct != null && !arrPrecinct.isEmpty()) {
//								arrPrecinct = new ArrayList<>();
//							}
//							initPrecinct(province, district, spinner_phuongxa,
//									-1);
//						}
//
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//				});
//
//		spinner_phuongxa
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//
//						if (arg2 > 0) {
//							precinct = arrPrecinct.get(arg2).getPrecinct();
//							if (arrTo != null && arrTo.size() > 0) {
//								arrTo.clear();
//							}
//							if ("2".equals(subType)) {
//								if (province != null && district != null
//										&& precinct != null) {
//									GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//											activity, spinner_to, -1, null,
//											prb_to, 1, btnRefreshStreetBlock);
//									async.execute(province + district
//											+ precinct);
//								}
//							}
//
//						} else {
//							btnRefreshStreetBlock.setVisibility(View.GONE);
//						}
//
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//
//					}
//				});
//
//		edt_hthm.setOnClickListener(this);
//		edt_kmai.setOnClickListener(this);
//
//		spinner_loaihd.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view,
//					int position, long id) {
//				Log.d(Constant.TAG,
//						"FragmentConnectionMobile onItemSelected spinner_loaihd position "
//								+ position + " "
//								+ arrTractBeans.get(position).getContractNo());
//
//				if (position == 0) {
//					contractNo = getActivity().getString(R.string.txt_select);
//					contractId = "";
//				} else if (position == 1) {
//					contractNo = "";
//					contractId = "";
//					showPopupInsertInfoContract();
//					// show dialog
//				} else {
//					contractNo = arrTractBeans.get(position).getContractNo();
//					contractId = arrTractBeans.get(position).getContractId();
//					showPopupInsertInfoContractOffer(arrTractBeans
//							.get(position));
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//				int position = adapterView.getSelectedItemPosition();
//				Log.d(Constant.TAG,
//						"FragmentConnectionMobile onNothingSelected spinner_loaihd position "
//								+ position);
//				if (position == 1) {
//					contractNo = "";
//					contractId = "";
//					showPopupInsertInfoContract();
//					// show dialog
//				}
//			}
//		});
//
//		spinner_loaithuebao
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//						if (arg2 > 0) {
//							subTypeMobile = arrSubTypeBeans.get(arg2)
//									.getSubType();
//						} else {
//							subTypeMobile = "";
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//				});
//
//		ln_quota = (LinearLayout) v.findViewById(R.id.ln_quota);
//
//		if ("2".equalsIgnoreCase(subType) && "M".equalsIgnoreCase(serviceType)) {
//			Log.d("Log", "unitView subType: " + subType + " serviceType: "
//					+ serviceType + " VISIBLE");
//			ln_quota.setVisibility(View.GONE);
//		} else {
//			Log.d("Log", "unitView subType: " + subType + " serviceType: "
//					+ serviceType + " GONE");
//			ln_quota.setVisibility(View.GONE);
//		}
//
//        CheckBox cb_quota = (CheckBox) v.findViewById(R.id.cb_quota);
//
//		cb_quota.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				// TODO Auto-generated method stub
//				if (isChecked) {
//					switch (isDeposit) {
//					case -1:
//						GetQuotaAsyn asyn = new GetQuotaAsyn(activity);
//						asyn.execute(custommerByIdNoBean.getCusType());
//						break;
//					case 0:
//						String message = String.format(
//								getString(R.string.qupta), quota, moneyPre);
//						Dialog dialog0 = CommonActivity
//								.createAlertDialog(activity, message,
//										getString(R.string.app_name));
//						dialog0.show();
//						break;
//					default:
//						Dialog dialog1 = CommonActivity.createAlertDialog(
//								activity, getString(R.string.not_quota),
//								getString(R.string.app_name));
//						dialog1.show();
//						break;
//					}
//				}
//			}
//		});
//
//		if (Session.loginUser != null
//				&& Session.loginUser.getChannelTypeId() != 14) {
//			lnMaNV.setVisibility(View.GONE);
//			txtmanv.setText(Session.loginUser.getStaffCode());
//			Log.d("Log", "check chanel value tvmanv"
//					+ txtmanv.getText().toString());
//		}
//
//		// chay ngam lay danh sach thue bao
//		if ("2".equalsIgnoreCase(subType)) {
//			AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(
//					activity, TYPE_HTTHHD);
//			asyntaskGetListAllCommon.execute();
//		}
//
//		initTypePaper(null, arrTypePaperBeans);
//
//		btnRefreshStreetBlock = (Button) mView
//				.findViewById(R.id.btnRefreshStreetBlock);
//		btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				new CacheDatabaseManager(MainActivity.getInstance())
//						.insertCacheStreetBlock(null, province + district
//								+ precinct);
//				GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//						activity, spinner_to, -1, null, prb_to, 1,
//						btnRefreshStreetBlock);
//				async.execute(province + district + precinct);
//			}
//		});
//
//	}
//
//	/**
//	 * Thong tin chi tiet hop dong
//	 */
//    private EditText edt_contractNo; // so hop dong
//	private EditText edt_payer; // nguoi thanh toan
//	private Spinner spn_contractTypeCode; // loai hop dong
//	private EditText edt_signDate; // ngay ki
//	private EditText edt_effectDate; // ngay hieu luc
//	private EditText edt_endEffectDate; // ngay het han hop dong
//	private Spinner spn_payMethodCode; // hinh thuc thanh toan
//	private Spinner spn_billCycleFromCharging; // chu ky cuoc
//	private Spinner spn_printMethodCode; // in chi tiet
//
//	/**
//	 * Dia chi hop dong
//	 */
//    private Spinner spn_province; // tinh
//	private Spinner spn_district; // huyen
//	private Spinner spn_precinct; // xa
//	private Spinner spn_streetBlock; // to
//	private EditText edt_streetName; // duong
//	private EditText edt_home; // so nha
//	private EditText edt_address; // dia chi
//
//	private final int spn_province_pos = -1; // tinh
//	protected int spn_district_pos = -1; // huyen
//	protected int spn_precinct_pos = -1; // xa
//	private int spn_streetBlock_pos = -1; // to
//
//    private EditText edt_contactName; // nguoi dai dien
//	private EditText edt_contactTitle; // chuc vu
//	private EditText edt_telFax; // dien thoai co dinh
//	private EditText edt_telMobile; // dien thoai di dong
//	private EditText edt_idNo;// chung minh thu
//
//	/**
//	 * ThÃ´ng bÃ¡o cÆ°á»›c
//	 */
//    private Spinner spn_noticeCharge; // hinh thuc thong bao cuoc
//	private Spinner spn_contractPrint; // thong tin bo sung
//	private EditText edt_email; // email
//	private EditText edt_billAddress; // dia chi thong bao cuoc
//
//    private EditText edt_contractOffer_represent; // nguoi dai dien
//	private EditText edt_contractOffer_representPosition; // chuc vu
//	private EditText edt_contractOffer_signDatetime;// ngay ky
//	private Spinner spn_contractOffer_idType;// loai giay to
//	private EditText edt_contractOffer_idNo;// ma giai to
//	private EditText edt_contractOffer_issueDatetime;// ngay cap
//	private EditText edt_contractOffer_issuePlace;// noi cap
//
//	/**
//	 * Thong tin ngan hang
//	 */
//	private LinearLayout lnBankAccount;
//	private EditText edt_account;
//	private EditText edt_accountName;
//	private EditText edt_bankContractNo;
//	private EditText edt_strBankContractDate;
//	private EditText edt_bankCode;
//
//    private Spinner spinner_plhopdong;
//	private LinearLayout ln_contractOffer_Id_p;
//	private EditText edt_contractOffer_Id_p;
//	private EditText edt_contractOffer_represent_p; // nguoi dai dien
//	private EditText edt_contractOffer_representPosition_p; // chuc vu
//	private EditText edt_contractOffer_signDatetime_p;// ngay ky
//	private Spinner spn_contractOffer_idType_p;// loai giay to
//	private EditText edt_contractOffer_idNo_p;// ma giai to
//	private EditText edt_contractOffer_issueDatetime_p;// ngay cap
//	private EditText edt_contractOffer_issuePlace_p;// noi cap
//
//    private ProgressBar prbloaihopdong, prbhttthd, prbchukicuoc, prbinchitiet,
//			prbhttbc, prbttbosung;
//	private Button btnloaihopdong, btnhttthd, btnchukicuoc, btninchitiet,
//			btnhttbc, btnttbosung;
//
//	private void showPopupInsertInfoContract() {
//		LayoutInflater inflater = (LayoutInflater) activity
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		@SuppressLint("InflateParams") final View dialogView = inflater.inflate(
//				R.layout.connection_info_contract, null, false);
//
//		final Dialog dialog = new Dialog(activity);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(dialogView);
//		// dialog.setContentView(R.layout.connection_info_contract);
//		dialog.setCancelable(false);
//
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String day = sdf.format(new Date());
//
//		// xu ly hop dong
//		prbloaihopdong = (ProgressBar) dialog.findViewById(R.id.prbloaihopdong);
//		btnRefreshStreetBlockDialog = (Button) dialog
//				.findViewById(R.id.btnRefreshStreetBlockDialog);
//		prbhttthd = (ProgressBar) dialog.findViewById(R.id.prbhttthd);
//		prbchukicuoc = (ProgressBar) dialog.findViewById(R.id.prbchukicuoc);
//		prbinchitiet = (ProgressBar) dialog.findViewById(R.id.prbinchitiet);
//		prbhttbc = (ProgressBar) dialog.findViewById(R.id.prbhttbc);
//		prbttbosung = (ProgressBar) dialog.findViewById(R.id.prbttbosung);
//
//		btnloaihopdong = (Button) dialog.findViewById(R.id.btnloaihopdong);
//		btnloaihopdong.setOnClickListener(this);
//		btnhttthd = (Button) dialog.findViewById(R.id.btnhttthd);
//		btnhttthd.setOnClickListener(this);
//		btnchukicuoc = (Button) dialog.findViewById(R.id.btnchukicuoc);
//		btnchukicuoc.setOnClickListener(this);
//		btninchitiet = (Button) dialog.findViewById(R.id.btninchitiet);
//		btninchitiet.setOnClickListener(this);
//		btnhttbc = (Button) dialog.findViewById(R.id.btnhttbc);
//		btnhttbc.setOnClickListener(this);
//		btnttbosung = (Button) dialog.findViewById(R.id.btnttbosung);
//		btnttbosung.setOnClickListener(this);
//
//		hideBtnRefresh();
//		showBtnRefresh();
//
//		// bo sung progess
//		prb_todialog = (ProgressBar) dialog.findViewById(R.id.prb_todialog);
//		// edt_contractNo = (EditText) dialog.findViewById(R.id.edt_contractNo);
//		// // so hop dong
//		edt_payer = (EditText) dialog.findViewById(R.id.edt_payer); // nguoi
//																	// thanh
//																	// toan
//		spn_contractTypeCode = (Spinner) dialog
//				.findViewById(R.id.spn_contractTypeCode); // loai
//															// hop
//															// dong
//
//		edt_signDate = (EditText) dialog.findViewById(R.id.edt_signDate); // ngay
//																			// ki
//		edt_signDate.setText(day);
//		// edt_signDate.setClickable(true);
//		edt_signDate.setOnClickListener(editTextListener);
//
//		edt_effectDate = (EditText) dialog.findViewById(R.id.edt_effectDate); // ngay
//																				// hieu
//																				// luc
//		edt_effectDate.setText(day);
//		// edt_effectDate.setClickable(true);
//		edt_effectDate.setOnClickListener(editTextListener);
//
//		edt_endEffectDate = (EditText) dialog
//				.findViewById(R.id.edt_endEffectDate); // ngay
//														// het
//														// han
//														// hop
//														// dong
//		edt_endEffectDate.setOnClickListener(editTextListener);
//
//		spn_payMethodCode = (Spinner) dialog
//				.findViewById(R.id.spn_payMethodCode); // hinh
//														// thuc
//														// thanh
//														// toan
//
//		/**
//		 * Thong tin ngan hang
//		 */
//		lnBankAccount = (LinearLayout) dialog.findViewById(R.id.lnBankAccount);
//		lnBankAccount.setVisibility(View.GONE);
//		edt_account = (EditText) dialog.findViewById(R.id.edt_account); // bank
//		edt_accountName = (EditText) dialog.findViewById(R.id.edt_accountName); // bank
//		edt_bankContractNo = (EditText) dialog
//				.findViewById(R.id.edt_bankContractNo); // bank
//		edt_strBankContractDate = (EditText) dialog
//				.findViewById(R.id.edt_strBankContractDate); // bank
//		edt_strBankContractDate.setOnClickListener(editTextListener);
//		edt_bankCode = (EditText) dialog.findViewById(R.id.edt_bankCode);
//		edt_bankCode.setOnClickListener(this);
//
//		spn_payMethodCode
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//					@Override
//					public void onItemSelected(AdapterView<?> adapterView,
//							View view, int position, long id) {
//						Spin spin = (Spin) adapterView
//								.getItemAtPosition(position);
//						if ("02".equalsIgnoreCase(spin.getId())
//								|| "03".equalsIgnoreCase(spin.getId())) {
//							lnBankAccount.setVisibility(View.VISIBLE);
//						} else {
//							lnBankAccount.setVisibility(View.GONE);
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> adapterView) {
//					}
//				});
//
//		spn_billCycleFromCharging = (Spinner) dialog
//				.findViewById(R.id.spn_billCycleFromCharging); // chu
//																// ky
//																// cuoc
//		spn_printMethodCode = (Spinner) dialog
//				.findViewById(R.id.spinner_printMethodCode); // in
//																// chi
//																// tiet
//		spn_printMethodCode
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//					@Override
//					public void onItemSelected(AdapterView<?> adapterView,
//							View view, int position, long id) {
//						Spin spin = (Spin) adapterView.getSelectedItem();
//						Log.d(Constant.TAG,
//								"spn_printMethodCode " + spin.getId() + " "
//										+ spin.getValue());
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> adapterView) {
//					}
//				});
//
//		/**
//		 * Ä�á»‹a chá»‰ há»£p Ä‘á»“ng<
//		 */
//		spn_province = (Spinner) dialog.findViewById(R.id.spn_province); // tinh
//		spn_district = (Spinner) dialog.findViewById(R.id.spn_district); // huyen
//		spn_precinct = (Spinner) dialog.findViewById(R.id.spn_precinct); // xa
//		spn_streetBlock = (Spinner) dialog.findViewById(R.id.spn_streetBlock); // to
//
//		spn_province.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view,
//					int position, long id) {
//
//				if (position > 0) {
//					dialogProvince = arrProvince.get(position).getProvince();
//					initDistrict(dialogProvince, spn_district, -1);
//					edt_address.setText(initAddress());
//					edt_billAddress.setText(initAddress());
//				} else {
//					if (arrDistrict != null && !arrDistrict.isEmpty()) {
//						arrDistrict = new ArrayList<>();
//					}
//					initDistrict(dialogProvince, spn_district, -1);
//					if (arrPrecinct != null && !arrPrecinct.isEmpty()) {
//						arrPrecinct = new ArrayList<>();
//					}
//					initPrecinct(dialogProvince, dialogDistrict, spn_precinct,
//							-1);
//				}
//
//				// dialogProvince = arrProvince.get(position).getProvince();
//				// spn_province_pos = position;
//				//
//				// edt_address.setText(initAddress());
//				// edt_billAddress.setText(initAddress());
//				//
//				// initDistrict(dialogProvince, spn_district, spn_district_pos);
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//			}
//		});
//
//		spn_district.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view,
//					int position, long id) {
//
//				if (position > 0) {
//					dialogDistrict = arrDistrict.get(position).getDistrict();
//					initPrecinct(dialogProvince, dialogDistrict, spn_precinct,
//							-1);
//					edt_address.setText(initAddress());
//					edt_billAddress.setText(initAddress());
//				} else {
//					if (arrPrecinct != null && !arrPrecinct.isEmpty()) {
//						arrPrecinct = new ArrayList<>();
//					}
//					initPrecinct(dialogProvince, dialogDistrict, spn_precinct,
//							-1);
//					edt_address.setText("");
//					edt_billAddress.setText("");
//				}
//
//				// dialogDistrict = arrDistrict.get(position).getDistrict();
//				// spn_district_pos = position;
//				// initPrecinct(dialogProvince, dialogDistrict, spn_precinct,
//				// spn_precinct_pos);
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//			}
//		});
//
//		spn_precinct.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view,
//					int position, long id) {
//
//				if (position > 0) {
//					dialogPrecinct = arrPrecinct.get(position).getPrecinct();
//					if (arrTo != null && arrTo.size() > 0) {
//						arrTo.clear();
//					}
//					if ("2".equals(subType)) {
//						if (dialogProvince != null && dialogDistrict != null
//								&& dialogPrecinct != null) {
//							GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//									activity, spn_streetBlock, -1, null,
//									prb_todialog, 2,
//									btnRefreshStreetBlockDialog);
//							async.execute(dialogProvince + dialogDistrict
//									+ dialogPrecinct);
//						}
//					}
//
//				} else {
//					btnRefreshStreetBlockDialog.setVisibility(View.GONE);
//				}
//
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//			}
//		});
//		btnRefreshStreetBlockDialog
//				.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						new CacheDatabaseManager(MainActivity.getInstance())
//								.insertCacheStreetBlock(null, dialogProvince
//										+ dialogDistrict + dialogPrecinct);
//						GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//								activity, spn_streetBlock, -1, null,
//								prb_todialog, 2, btnRefreshStreetBlockDialog);
//						async.execute(dialogProvince + dialogDistrict
//								+ dialogPrecinct);
//					}
//				});
//		spn_streetBlock.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view,
//					int position, long id) {
//				if (arrToDialog != null && !arrToDialog.isEmpty()) {
//					dialogStreetBlock = arrToDialog.get(position).getAreaCode();
//					spn_streetBlock_pos = position;
//
//					edt_address.setText(initAddress());
//					edt_billAddress.setText(initAddress());
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//			}
//		});
//
//		edt_streetName = (EditText) dialog.findViewById(R.id.edt_streetName); // duong
//		edt_streetName.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				edt_address.setText(initAddress());
//				edt_billAddress.setText(initAddress());
//			}
//		});
//
//		// so nha
//		edt_home = (EditText) dialog.findViewById(R.id.edt_home);
//		edt_home.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				edt_address.setText(initAddress());
//				edt_billAddress.setText(initAddress());
//			}
//		});
//		// dia chi
//		edt_address = (EditText) dialog.findViewById(R.id.edt_address);
//
//		/**
//		 * ThÃ´ng tin Ä‘áº¡i hiá»‡n HÄ�
//		 */
//		/*
//	  TT Ä�áº¡i diá»‡n há»£p Ä‘á»“ng
//	 */
//        LinearLayout lnDaidienHopDong = (LinearLayout) dialog
//                .findViewById(R.id.lnDaidienHopDong);
//		// nguoi dai dien
//		edt_contactName = (EditText) dialog.findViewById(R.id.edt_contactName);
//		// chuc vu
//		edt_contactTitle = (EditText) dialog
//				.findViewById(R.id.edt_contactTitle);
//		// dien thoai co ding
//		edt_telFax = (EditText) dialog.findViewById(R.id.edt_telFax);
//		// dien thoai di dong
//		edt_telMobile = (EditText) dialog.findViewById(R.id.edt_telMobile);
//		// chung minh thu
//		edt_idNo = (EditText) dialog.findViewById(R.id.edt_idNo);
//
//		/**
//		 * Ä�á»‹a chá»‰ TBC vÃ  thanh toÃ¡n cÆ°á»›c
//		 */
//		// hinh thuc thong bao cuoc
//		spn_noticeCharge = (Spinner) dialog.findViewById(R.id.spn_noticeCharge);
//		spn_noticeCharge
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//					@Override
//					public void onItemSelected(AdapterView<?> adapterView,
//							View view, int position, long id) {
//						Spin spin = (Spin) adapterView.getSelectedItem();
//						if ("1".equalsIgnoreCase(spin.getId())
//								|| "2".equalsIgnoreCase(spin.getId())
//								|| "5".equalsIgnoreCase(spin.getId())) {
//							for (int i = 0; i < arrINCT.size(); i++) {
//								Spin itemINCT = arrINCT.get(i);
//								if ("2".equalsIgnoreCase(itemINCT.getId())) {
//									spn_printMethodCode.setSelection(i);
//									spn_printMethodCode.setEnabled(false);
//									break;
//								}
//							}
//						} else {
//							spn_printMethodCode.setEnabled(true);
//						}
//						Log.d(Constant.TAG, "spn_noticeCharge " + spin.getId()
//								+ " " + spin.getValue());
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> adapterView) {
//					}
//				});
//
//		// thong tin bo sung
//		spn_contractPrint = (Spinner) dialog
//				.findViewById(R.id.spn_contractPrint);
//		// email
//		edt_email = (EditText) dialog.findViewById(R.id.edt_email);
//		// dia chithong bao cuoc
//		edt_billAddress = (EditText) dialog.findViewById(R.id.edt_billAddress);
//
//		/**
//		 * TT phu luc hop dong
//		 */
//		/*
//	  Thong tin phu luc hop dong
//	 */
//        LinearLayout lnPhuLucHopDong = (LinearLayout) dialog
//                .findViewById(R.id.lnPhuLucHopDong);
//		if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//				.getBusPermitNo().isEmpty())) {
//			lnPhuLucHopDong.setVisibility(View.VISIBLE);
//			lnDaidienHopDong.setVisibility(View.VISIBLE);
//		} else {
//			lnPhuLucHopDong.setVisibility(View.GONE);
//			lnDaidienHopDong.setVisibility(View.GONE);
//		}
//
//		edt_contractOffer_represent = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_represent); // nguoi
//																	// dai
//																	// dien
//		edt_contractOffer_representPosition = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_representPosition); // chuc
//																			// vu
//		edt_contractOffer_signDatetime = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_signDatetime);// ngay
//																	// ky
//		edt_contractOffer_signDatetime.setText(day);
//		edt_contractOffer_signDatetime.setOnClickListener(editTextListener);
//		spn_contractOffer_idType = (Spinner) dialog
//				.findViewById(R.id.spn_contractOffer_idType);// loai
//																// giay
//																// to
//		edt_contractOffer_idNo = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_idNo);// ma
//															// giai
//															// to
//		edt_contractOffer_issueDatetime = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_issueDatetime);// ngay
//																	// cap
//		edt_contractOffer_issueDatetime.setOnClickListener(editTextListener);
//		edt_contractOffer_issuePlace = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_issuePlace);// noi
//																	// cap
//
//		if (arrHTTTHD == null || arrLOAIHD == null || arrCKC == null
//				|| arrINCT == null || arrHTTBC == null || arrTTBSHD == null) {
//			AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(
//					activity, TYPE_HTTHHD);
//			asyntaskGetListAllCommon.execute();
//		} else {
//			Utils.setDataSpinner(activity, arrHTTTHD, spn_payMethodCode); // hinh
//																			// thuc
//																			// thanh
//																			// toan
//			Utils.setDataSpinner(activity, arrLOAIHD, spn_contractTypeCode); // loai
//																				// hop
//																				// dong
//			Utils.setDataSpinner(activity, arrCKC, spn_billCycleFromCharging); // chu
//																				// ky
//																				// cuoc
//			Utils.setDataSpinner(activity, arrINCT, spn_printMethodCode); // in
//																			// chi
//																			// tiet
//			Utils.setDataSpinner(activity, arrHTTBC, spn_noticeCharge,
//					arrHTTBC.size() - 1); // hinh
//											// thuc
//											// thong
//											// bao
//											// cuoc
//			Utils.setDataSpinner(activity, arrTTBSHD, spn_contractPrint); // thong
//																			// tin
//																			// bo
//																			// sung
//		}
//
//		initProvince(spn_province, arrProvince, spn_province_pos);
//
//		initTypePaper(spn_contractOffer_idType, arrTypePaperBeans);
//
//		Contract contract = custommerByIdNoBean.getContract();
//		if (contract != null) {
//			// init giao dien
//			// edt_contractNo.setText(contract.getContractNo()); // so hop dong
//			// HIDE
//			edt_payer.setText(contract.getPayer()); // nguoi thanh toan
//
//			// loai hop dong
//			if (contract.getContractTypeCode() != null && arrLOAIHD != null) {
//				for (int i = 0; i < arrLOAIHD.size(); i++) {
//					Spin spin = arrLOAIHD.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							contract.getContractTypeCode())) {
//						spn_contractTypeCode.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			edt_signDate.setText(contract.getSignDate()); // ngay ki
//			edt_effectDate.setText(contract.getEffectDate()); // ngay hieu luc
//			edt_endEffectDate.setText(contract.getEndEffectDate()); // ngay het
//																	// han hop
//																	// dong
//			// hinh thucthanh toan
//			if (contract.getPayMethodCode() != null && arrHTTTHD != null) {
//				for (int i = 0; i < arrHTTTHD.size(); i++) {
//					Spin spin = arrHTTTHD.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							contract.getPayMethodCode())) {
//						spn_payMethodCode.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			// chu ky cuoc
//			if (contract.getBillCycleFromCharging() != null && arrCKC != null) {
//				for (int i = 0; i < arrCKC.size(); i++) {
//					Spin spin = arrCKC.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							contract.getBillCycleFromCharging())) {
//						spn_billCycleFromCharging.setSelection(i);
//						break;
//					}
//				}
//			}
//			// in chi tiet
//			if (contract.getPrintMethodCode() != null && arrINCT != null) {
//				for (int i = 0; i < arrINCT.size(); i++) {
//					Spin spin = arrINCT.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							contract.getPrintMethodCode())) {
//						spn_printMethodCode.setSelection(i);
//						break;
//					}
//				}
//			}
//			// spn_province.setText(contract.getContractNo()); // tinh
//			// spn_district.setText(contract.getContractNo()); // huyen
//			// spn_precinct.setText(contract.getContractNo()); // xa
//			// spn_streetBlockName.setText(contract.getStreetBlock()); // to
//			edt_streetName.setText(contract.getStreetName()); // duong
//			edt_home.setText(contract.getHome()); // so nha
//			edt_address.setText(contract.getAddress()); // dia chi
//
//			edt_contactName.setText(contract.getContactName()); // nguoi dai
//																// dien
//			edt_contactTitle.setText(contract.getContactTitle()); // chuc vu
//
//			edt_telFax.setText(contract.getTelFax()); // dien thoai co dinh
//			edt_telMobile.setText(contract.getTelMobile()); // dien thoai di
//															// dong
//			edt_idNo.setText(contract.getIdNo());// chung minh thu
//
//			edt_email.setText(contract.getEmail()); // email
//			// hinh thuc thong bao cuoc
//			if (contract.getNoticeCharge() != null && arrHTTBC != null) {
//				for (int i = 0; i < arrHTTBC.size(); i++) {
//					Spin spin = arrHTTBC.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							contract.getNoticeCharge())) {
//						spn_noticeCharge.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			// thong tin bo sung
//			if (contract.getContractPrint() != null && arrTTBSHD != null) {
//				for (int i = 0; i < arrTTBSHD.size(); i++) {
//					Spin spin = arrTTBSHD.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							contract.getContractPrint())) {
//						spn_contractPrint.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			edt_billAddress.setText(contract.getBillAddress()); // dia chi thong
//																// bao cuoc
//
//			/**
//			 * phu luc hop dong
//			 */
//			edt_contractOffer_represent.setText(contract.getContractOffer()
//					.getRepresent()); // nguoi
//										// dai
//										// dien
//			edt_contractOffer_representPosition.setText(contract
//					.getContractOffer().getRepresentPosition()); // chuc
//																	// vu
//			edt_contractOffer_signDatetime.setText(contract.getContractOffer()
//					.getSignDatetime());// ngay/
//										// ky
//			// spn_contractOffer_idType;// loai giay to
//			if (contract.getContractOffer().getIdType() != null
//					&& arrTypePaperBeans != null) {
//				for (int i = 0; i < arrTypePaperBeans.size(); i++) {
//					TypePaperBeans typePaperBean = arrTypePaperBeans.get(i);
//					if (typePaperBean.getParType().equalsIgnoreCase(
//							contract.getContractOffer().getIdType())) {
//						spn_contractOffer_idType.setSelection(i);
//						break;
//					}
//				}
//			}
//			edt_contractOffer_idNo.setText(contract.getContractOffer()
//					.getIdNo());// ma
//								// giai
//								// to
//			edt_contractOffer_issueDatetime.setText(contract.getContractOffer()
//					.getIssueDatetime());// ngay
//											// cap
//			edt_contractOffer_issuePlace.setText(contract.getContractOffer()
//					.getIssuePlace());// noi
//										// cap
//
//			ContractBank contractBank = contract.getContractBank();
//			if (contractBank != null) {
//				edt_account.setText(contractBank.getAccount()); // bank tai
//																// khoan
//				edt_accountName.setText(contractBank.getAccountName()); // bank
//																		// ten
//																		// tai
//																		// khoan
//				edt_bankCode.setText(contractBank.getBankCode()); // bank ma
//																	// ngan hang
//				edt_bankContractNo.setText(contractBank.getBankContractNo()); // bank
//																				// hop
//																				// dong
//				edt_strBankContractDate.setText(contractBank
//						.getStrBankContractDate()); // bank
//			}
//		} else {
//			edt_contractOffer_represent.setText(custommerByIdNoBean
//					.getNameCustomer()); // nguoi
//											// dai
//											// dien
//			// edt_contractOffer_representPosition.setText(contract.getContractOffer().getRepresentPosition());
//			// // chuc vu
//			// edt_contractOffer_signDatetime.setText(contract.getContractOffer().getSignDatetime());//
//			// ngay/ ky
//			// spn_contractOffer_idType;// loai giay to
//			// if(contract.getContractOffer().getIdType() != null &&
//			// arrTypePaperBeans != null) {
//			// for (int i = 0; i < arrTypePaperBeans.size(); i++) {
//			// TypePaperBeans typePaperBean = arrTypePaperBeans.get(i);
//			// if(typePaperBean.getParType().equalsIgnoreCase(contract.getContractOffer().getIdType()))
//			// {
//			// spn_contractOffer_idType.setSelection(i);
//			// break;
//			// }
//			// }
//			// }
//			edt_contractOffer_idNo.setText(custommerByIdNoBean.getIdNo());// ma
//																			// giai
//																			// to
//			edt_contractOffer_issueDatetime.setText(custommerByIdNoBean
//					.getIdIssueDate());// ngay
//										// cap
//			edt_contractOffer_issuePlace.setText(custommerByIdNoBean
//					.getIdIssuePlace());// noi
//										// cap
//		}
//
//		Log.d(Constant.TAG, "showPopupInsertInfoContract "
//				+ custommerByIdNoBean.toString());
//
//		Button btn_insert_contract = (Button) dialog
//				.findViewById(R.id.btn_insert_contract);
//		btn_insert_contract.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				// set giao dien vao doi tuong contract
//				Spin item;
//				Contract contract = new Contract();
//				// contract.setContractNo(edt_contractNo.getText().toString());
//				// // so hop dong
//				contract.setPayer(edt_payer.getText().toString()); // nguoi
//																	// thanh
//																	// toan
//
//				item = (Spin) spn_contractTypeCode.getSelectedItem(); // loai
//																		// hop
//																		// dong
//				contract.setContractTypeCode(item.getId());
//
//				contract.setSignDate(edt_signDate.getText().toString()); // ngay
//																			// ki
//				contract.setEffectDate(edt_effectDate.getText().toString()); // ngay
//																				// hieu
//																				// luc
//				contract.setEndEffectDate(edt_endEffectDate.getText()
//						.toString()); // ngay
//										// het
//										// han
//										// hop
//										// dong
//
//				item = (Spin) spn_payMethodCode.getSelectedItem(); // hinh thuc
//																	// thanh
//																	// toan
//				contract.setPayMethodCode(item.getId());
//
//				item = (Spin) spn_billCycleFromCharging.getSelectedItem(); // chu
//																			// ky
//																			// cuoc
//				contract.setBillCycleFromCharging(item.getId());
//
//				item = (Spin) spn_printMethodCode.getSelectedItem(); // in chi
//																		// tiet
//				contract.setPrintMethodCode(item.getId());
//
//				int pos = spn_province.getSelectedItemPosition();
//				contract.setProvince(arrProvince.get(pos).getProvince());
//				// item = (Spin) spn_province.getSelectedItem(); // tinh
//				// contract.setProvince(item.getId());
//
//				pos = spn_district.getSelectedItemPosition();
//				contract.setDistrict(arrDistrict.get(pos).getDistrict());
//				// item = (Spin) spn_district.getSelectedItem(); // huyen
//				// contract.setDistrict(item.getId());
//
//				pos = spn_precinct.getSelectedItemPosition();
//				contract.setPrecinct(arrPrecinct.get(pos).getPrecinct());
//				// item = (Spin) spn_precinct.getSelectedItem(); // xa
//				// contract.setPrecinct(item.getId());
//
//				pos = spn_streetBlock.getSelectedItemPosition();
//				if (arrToDialog != null && !arrToDialog.isEmpty()) {
//					contract.setStreetBlock(arrToDialog.get(pos).getAreaCode());
//				}
//
//				// item = (Spin) spn_streetBlock.getSelectedItem(); // to
//				// contract.setStreetBlockName(item.getId());
//
//				contract.setStreetName(edt_streetName.getText().toString()); // duong
//				contract.setHome(edt_home.getText().toString()); // so nha
//				contract.setAddress(edt_address.getText().toString()); // dia
//																		// chi
//
//				contract.setContactName(edt_contactName.getText().toString()); // nguoi
//																				// dai
//																				// dien
//				contract.setContactTitle(edt_contactTitle.getText().toString()); // chuc
//																					// vu
//
//				contract.setTelFax(edt_telFax.getText().toString()); // dien
//																		// thoai
//																		// co
//																		// dinh
//				contract.setTelMobile(edt_telMobile.getText().toString()); // dien
//																			// thoai
//																			// di
//																			// dong
//				contract.setEmail(edt_email.getText().toString()); // email
//				contract.setIdNo(edt_idNo.getText().toString());// chung minh
//																// thu
//
//				item = (Spin) spn_noticeCharge.getSelectedItem(); // hinh thuc
//																	// thong bao
//																	// cuoc
//				contract.setNoticeCharge(item.getId());
//
//				item = (Spin) spn_contractPrint.getSelectedItem();
//				contract.setContractPrint(item.getId()); // thong tin bo sung
//															// spinner gi day
//
//				contract.setBillAddress(edt_billAddress.getText().toString()); // dia
//																				// chi
//																				// thong
//																				// bao
//																				// cuoc
//
//				ContractOffer contractOffer = new ContractOffer();
//
//				contractOffer.setRepresent(edt_contractOffer_represent
//						.getText().toString()); // nguoi
//												// dai
//												// dien
//				contractOffer
//						.setRepresentPosition(edt_contractOffer_representPosition
//								.getText().toString()); // chuc
//														// vu
//				contractOffer.setSignDatetime(edt_contractOffer_signDatetime
//						.getText().toString());// ngay
//												// ky
//				// loai giay to
//
//				pos = spn_contractOffer_idType.getSelectedItemPosition();
//
//				contractOffer
//						.setIdType(arrTypePaperBeans.get(pos).getParType());
//
//				contractOffer.setIdNo(edt_contractOffer_idNo.getText()
//						.toString());// ma
//										// giai
//										// to
//				contractOffer.setIssueDatetime(edt_contractOffer_issueDatetime
//						.getText().toString());// ngay
//												// cap
//				contractOffer.setIssuePlace(edt_contractOffer_issuePlace
//						.getText().toString());// noi
//												// cap
//
//				contract.setContractOffer(contractOffer);
//
//				if (contract.getPayMethodCode() != null
//						&& ("02".equalsIgnoreCase(contract.getPayMethodCode()) || "03"
//								.equalsIgnoreCase(contract.getPayMethodCode()))) {
//					ContractBank contractBank = new ContractBank();
//
//					contractBank.setAccount(edt_account.getText().toString()); // bank
//																				// tai
//																				// khoan
//					contractBank.setAccountName(edt_accountName.getText()
//							.toString()); // bank
//											// ten
//											// tai
//											// khoan
//					if (bankBean != null) {
//						contractBank.setBankCode(bankBean.getCode()); // bank ma
//																		// ngan
//																		// hang
//					}
//					contractBank.setBankContractNo(edt_bankContractNo.getText()
//							.toString()); // bank
//											// hop
//											// dong
//					contractBank.setStrBankContractDate(edt_strBankContractDate
//							.getText().toString()); // bank
//
//					contract.setContractBank(contractBank);
//				}
//
//				custommerByIdNoBean.setContract(contract);
//
//				String message = null;
//
//				boolean valid = true;// UI.validateInput(dialogView);
//
//				if ("".equalsIgnoreCase(edt_payer.getText().toString().trim())) {
//					valid = false;
//					edt_payer.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.checknguoithanhtoan),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edt_signDate.getText()
//						.toString().trim())) {
//					valid = false;
//					edt_signDate.requestFocus();
//					Toast.makeText(activity, getString(R.string.ngaykyhdCD),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edt_effectDate.getText()
//						.toString().trim())) {
//					valid = false;
//					edt_effectDate.requestFocus();
//					Toast.makeText(activity, getString(R.string.ngayhieulucCD),
//							Toast.LENGTH_LONG).show();
//					// } else if
//					// ("".equalsIgnoreCase(edt_endEffectDate.getText().toString().trim()))
//					// {
//					// valid = false;
//					// edt_endEffectDate.requestFocus();
//					// Toast.makeText(activity,
//					// getString(R.string.ngayhethanCD),
//					// Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edt_streetName.getText()
//						.toString().trim())) {
//					valid = false;
//					edt_streetName.requestFocus();
//					Toast.makeText(activity, getString(R.string.checkttduong),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edt_home.getText().toString()
//						.trim())) {
//					valid = false;
//					edt_home.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.checksonhamobile),
//							Toast.LENGTH_LONG).show();
//				} else if ("".equalsIgnoreCase(edt_billAddress.getText()
//						.toString().trim())) {
//					valid = false;
//					edt_billAddress.requestFocus();
//					Toast.makeText(activity,
//							getString(R.string.billAdressNotEmpty),
//							Toast.LENGTH_LONG).show();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contactName.getText()
//								.toString().trim())) {
//					valid = false;
//					edt_contactName.requestFocus();
//					Toast.makeText(activity, getString(R.string.txtUserName),
//							Toast.LENGTH_LONG).show();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contactTitle.getText()
//								.toString().trim())) {
//					valid = false;
//					edt_contactTitle.requestFocus();
//					// } else if
//					// ("".equalsIgnoreCase(edt_telFax.getText().toString().trim()))
//					// {
//					// valid = false;
//					// edt_telFax.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_telMobile.getText()
//								.toString().trim())) {
//					valid = false;
//					edt_telMobile.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_idNo.getText().toString()
//								.trim())) {
//					valid = false;
//					edt_idNo.requestFocus();
//				} else if ("".equalsIgnoreCase(edt_email.getText().toString()
//						.trim())) {
//					Spin spin = (Spin) spn_noticeCharge.getSelectedItem();
//					if (spin != null && "1".equalsIgnoreCase(spin.getId())) {
//						valid = false;
//						edt_email.requestFocus();
//						message = getString(R.string.require_email);
//					}
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_represent
//								.getText().toString().trim())) {
//					valid = false;
//					edt_contractOffer_represent.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_representPosition
//								.getText().toString().trim())) {
//					valid = false;
//					edt_contractOffer_representPosition.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_signDatetime
//								.getText().toString().trim())) {
//					valid = false;
//					edt_contractOffer_signDatetime.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_represent
//								.getText().toString().trim())) {
//					valid = false;
//					edt_contractOffer_represent.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_idNo.getText()
//								.toString().trim())) {
//					valid = false;
//					edt_contractOffer_idNo.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_issueDatetime
//								.getText().toString().trim())) {
//					valid = false;
//					edt_contractOffer_issueDatetime.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "".equalsIgnoreCase(edt_contractOffer_issuePlace
//								.getText().toString().trim())) {
//					valid = false;
//					edt_contractOffer_issuePlace.requestFocus();
//				} else if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//						.getBusPermitNo().isEmpty())
//						&& "1".equalsIgnoreCase(contract.getContractOffer()
//								.getIdType())
//						&& (edt_contractOffer_idNo.getText().toString().trim()
//								.length() != 9 && edt_contractOffer_idNo
//								.getText().toString().trim().length() != 12)) {
//					valid = false;
//					edt_contractOffer_idNo.requestFocus();
//					message = getString(R.string.checksoidno);
//				} else {
//					if (contract.getPayMethodCode() != null
//							&& ("02".equalsIgnoreCase(contract
//									.getPayMethodCode()) || "03"
//									.equalsIgnoreCase(contract
//											.getPayMethodCode()))) {
//						if ("".equalsIgnoreCase(edt_account.getText()
//								.toString().trim())) {
//							valid = false;
//							edt_account.requestFocus();
//						} else if ("".equalsIgnoreCase(edt_accountName
//								.getText().toString().trim())) {
//							valid = false;
//							edt_accountName.requestFocus();
//						} else if ("".equalsIgnoreCase(edt_strBankContractDate
//								.getText().toString().trim())) {
//							valid = false;
//							edt_strBankContractDate.requestFocus();
//						} else if ("".equalsIgnoreCase(edt_bankCode.getText()
//								.toString().trim())) {
//							valid = false;
//							edt_bankCode.requestFocus();
//						}
//					}
//				}
//
//				if (valid) {
//					dialog.dismiss();
//				} else {
//					if (message == null) {
//						message = getString(R.string.title_input);
//					}
//					CommonActivity.createAlertDialog(activity, message,
//							getString(R.string.app_name)).show();
//
//					Log.d(Constant.TAG,
//							"showPopupInsertInfoContract validateInput "
//									+ valid);
//				}
//				Log.d(Constant.TAG,
//						"showPopupInsertInfoContract btn_insert_contract "
//								+ custommerByIdNoBean.toString());
//			}
//		});
//
//		Button btncancel = (Button) dialog.findViewById(R.id.btn_cancel);
//		btncancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//
//				contractId = "";
//				contractNo = getActivity().getString(R.string.txt_select);
//				if (arrTractBeans != null && arrTractBeans.size() > 0) {
//
//					for (int i = 0; i < arrTractBeans.size(); i++) {
//						if (arrTractBeans
//								.get(i)
//								.getContractNo()
//								.equals(getActivity().getString(
//										R.string.txt_select))) {
//							spinner_loaihd.setSelection(arrTractBeans
//									.indexOf(arrTractBeans.get(i)));
//							break;
//						}
//					}
//				}
//
//				dialog.dismiss();
//			}
//		});
//
//		dialog.show();
//	}
//
//	private void showPopupInsertInfoContractOffer(
//			final ConTractBean conTractBean) {
//
//		LayoutInflater inflater = (LayoutInflater) activity
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		@SuppressLint("InflateParams") final View dialogView = inflater.inflate(
//				R.layout.connection_info_contract_offer, null, false);
//
//		final Dialog dialog = new Dialog(activity);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(dialogView);
//		// dialog.setContentView(R.layout.connection_info_contract);
//		dialog.setCancelable(false);
//
//		btnRefreshStreetBlockDialog = (Button) dialogView
//				.findViewById(R.id.btnRefreshStreetBlockDialog);
//		btnRefreshStreetBlockDialog
//				.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//
//						new CacheDatabaseManager(MainActivity.getInstance())
//								.insertCacheStreetBlock(null,
//										conTractBean.getProvince()
//												+ conTractBean.getDistrict()
//												+ conTractBean.getPrecinct());
//						GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//								activity, spn_streetBlock, -1, conTractBean
//										.getStreetBlock(), prb_todialog1, 2,
//								btnRefreshStreetBlockDialog);
//						async.execute(conTractBean.getProvince()
//								+ conTractBean.getDistrict()
//								+ conTractBean.getPrecinct());
//					}
//				});
//
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String day = sdf.format(new Date());
//
//		prb_todialog1 = (ProgressBar) dialogView
//				.findViewById(R.id.prb_todialog1);
//		// so hop dong
//		edt_contractNo = (EditText) dialog.findViewById(R.id.edt_contractNo);
//		edt_payer = (EditText) dialog.findViewById(R.id.edt_payer); // nguoi
//																	// thanh
//																	// toan
//		spn_contractTypeCode = (Spinner) dialog
//				.findViewById(R.id.spn_contractTypeCode); // loai
//															// hop
//															// dong
//		edt_signDate = (EditText) dialog.findViewById(R.id.edt_signDate); // ngay
//																			// ki
//		edt_effectDate = (EditText) dialog.findViewById(R.id.edt_effectDate); // ngay
//																				// hieu
//																				// luc
//		edt_endEffectDate = (EditText) dialog
//				.findViewById(R.id.edt_endEffectDate); // ngay
//														// het
//														// han
//														// hop
//														// dong
//		spn_payMethodCode = (Spinner) dialog
//				.findViewById(R.id.spn_payMethodCode); // hinh
//														// thuc
//														// thanh
//														// toan
//
//		/**
//		 * Thong tin ngan hang
//		 */
//		lnBankAccount = (LinearLayout) dialog.findViewById(R.id.lnBankAccount);
//		lnBankAccount.setVisibility(View.GONE);
//		edt_account = (EditText) dialog.findViewById(R.id.edt_account); // bank
//		edt_accountName = (EditText) dialog.findViewById(R.id.edt_accountName); // bank
//		edt_bankContractNo = (EditText) dialog
//				.findViewById(R.id.edt_bankContractNo); // bank
//		edt_strBankContractDate = (EditText) dialog
//				.findViewById(R.id.edt_strBankContractDate); // bank
//		edt_bankCode = (EditText) dialog.findViewById(R.id.edt_bankCode);
//
//		spn_billCycleFromCharging = (Spinner) dialog
//				.findViewById(R.id.spn_billCycleFromCharging); // chu
//																// ky
//																// cuoc
//		spn_printMethodCode = (Spinner) dialog
//				.findViewById(R.id.spinner_printMethodCode); // in
//																// chi
//																// tiet
//
//		spn_province = (Spinner) dialog.findViewById(R.id.spn_province); // tinh
//		spn_district = (Spinner) dialog.findViewById(R.id.spn_district); // huyen
//		spn_precinct = (Spinner) dialog.findViewById(R.id.spn_precinct); // xa
//		spn_streetBlock = (Spinner) dialog.findViewById(R.id.spn_streetBlock); // to
//		edt_streetName = (EditText) dialog.findViewById(R.id.edt_streetName); // duong
//		edt_home = (EditText) dialog.findViewById(R.id.edt_home); // so nha
//		edt_address = (EditText) dialog.findViewById(R.id.edt_address); // dia
//																		// chi
//
//		/**
//		 * ThÃ´ng tin Ä‘áº¡i hiá»‡n HÄ�
//		 */
//		/*
//	  Dialog dai dien hop dong cu
//
//	  */
//        LinearLayout lnDaidienHopDong_p = (LinearLayout) dialog
//                .findViewById(R.id.lnDaidienHopDong_p);
//		edt_contactName = (EditText) dialog.findViewById(R.id.edt_contactName); // nguoi
//																				// dai
//																				// dien
//		edt_contactTitle = (EditText) dialog
//				.findViewById(R.id.edt_contactTitle); // chuc
//														// vu
//		edt_telFax = (EditText) dialog.findViewById(R.id.edt_telFax); // dien
//																		// thoai
//																		// co
//																		// dinh
//		edt_telMobile = (EditText) dialog.findViewById(R.id.edt_telMobile); // dien
//																			// thoai
//																			// di
//																			// dong
//		edt_email = (EditText) dialog.findViewById(R.id.edt_email); // email
//		edt_idNo = (EditText) dialog.findViewById(R.id.edt_idNo);// chung minh
//																	// thu
//
//		// hinh thuc thong bao cuoc
//		spn_noticeCharge = (Spinner) dialog.findViewById(R.id.spn_noticeCharge);
//		// thong tin bo sung
//		spn_contractPrint = (Spinner) dialog
//				.findViewById(R.id.spn_contractPrint);
//		// dia chithong bao cuoc
//		edt_billAddress = (EditText) dialog.findViewById(R.id.edt_billAddress);
//
//		// conTractBean
//        if (conTractBean != null) {
//			Log.d(Constant.TAG, "showPopupInsertInfoContractOffer "
//					+ conTractBean.toString());
//
//			// init giao dien
//			edt_contractNo.setText(conTractBean.getContractNo()); // so hop dong
//																// HIDE
//			edt_payer.setText(conTractBean.getPayer()); // nguoi thanh toan
//
//			// loai hop dong
//			if (conTractBean.getContractType() != null && arrLOAIHD != null) {
//				Utils.setDataSpinner(activity, arrLOAIHD, spn_contractTypeCode);
//				for (int i = 0; i < arrLOAIHD.size(); i++) {
//					Spin spin = arrLOAIHD.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							conTractBean.getContractType())) {
//						spn_contractTypeCode.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			edt_signDate.setText(conTractBean.getSignDate()); // ngay ki
//			edt_effectDate.setText(conTractBean.getEffectDate()); // ngay hieu luc
//			// edt_endEffectDate.setText(contract.getEndEffectDate()); // ngay
//			// het han hop dong
//			// hinh thucthanh toan
//
//			if (conTractBean.getPayMethodCode() != null && arrHTTTHD != null) {
//				Utils.setDataSpinner(activity, arrHTTTHD, spn_payMethodCode);
//				for (int i = 0; i < arrHTTTHD.size(); i++) {
//					Spin spin = arrHTTTHD.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							conTractBean.getPayMethodCode())) {
//						spn_payMethodCode.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			if ("02".equalsIgnoreCase(conTractBean.getPayMethodCode())
//					|| "03".equalsIgnoreCase(conTractBean.getPayMethodCode())) {
//				lnBankAccount.setVisibility(View.VISIBLE);
//			} else {
//				lnBankAccount.setVisibility(View.GONE);
//			}
//
//			// chu ky cuoc
//			if (conTractBean.getBillCycleFromCharging() != null && arrCKC != null) {
//				Utils.setDataSpinner(activity, arrCKC,
//						spn_billCycleFromCharging);
//				for (int i = 0; i < arrCKC.size(); i++) {
//					Spin spin = arrCKC.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							conTractBean.getBillCycleFromCharging())) {
//						spn_billCycleFromCharging.setSelection(i);
//						break;
//					}
//				}
//			}
//			// in chi tiet
//			if (conTractBean.getPrintMethodCode() != null && arrINCT != null) {
//				Utils.setDataSpinner(activity, arrINCT, spn_printMethodCode);
//				for (int i = 0; i < arrINCT.size(); i++) {
//					Spin spin = arrINCT.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							conTractBean.getPrintMethodCode())) {
//						spn_printMethodCode.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			initProvince(spn_province, null, -1);
//			for (int i = 0; i < arrProvince.size(); i++) {
//				AreaBean spin = arrProvince.get(i);
//				if (spin.getProvince().equalsIgnoreCase(conTractBean.getProvince())) {
//					spn_province.setSelection(i);
//					break;
//				}
//			}
//
//			initDistrict(conTractBean.getProvince(), spn_district, -1);
//			for (int i = 0; i < arrDistrict.size(); i++) {
//				AreaBean spin = arrDistrict.get(i);
//				if (spin.getDistrict().equalsIgnoreCase(conTractBean.getDistrict())) {
//					spn_district.setSelection(i);
//					break;
//				}
//			}
//
//			initPrecinct(conTractBean.getProvince(), conTractBean.getDistrict(),
//					spn_precinct, -1);
//			for (int i = 0; i < arrPrecinct.size(); i++) {
//				AreaBean spin = arrPrecinct.get(i);
//				if (spin.getPrecinct().equalsIgnoreCase(conTractBean.getPrecinct())) {
//					spn_precinct.setSelection(i);
//					break;
//				}
//			}
//
//			if (conTractBean.getStreetBlock() != null
//					&& !conTractBean.getStreetBlock().isEmpty()) {
//				if (arrToDialog != null && arrToDialog.size() > 0) {
//					arrToDialog.clear();
//				}
//				if (conTractBean.getProvince() != null
//						&& conTractBean.getDistrict() != null
//						&& conTractBean.getPrecinct() != null) {
//					GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
//							activity, spn_streetBlock, -1,
//							conTractBean.getStreetBlock(), prb_todialog1, 2,
//							btnRefreshStreetBlockDialog);
//					async.execute(conTractBean.getProvince()
//							+ conTractBean.getDistrict() + conTractBean.getPrecinct());
//				}
//
//			}
//
//			// spn_province.setText(contract.getContractNo()); // tinh
//			// spn_district.setText(contract.getContractNo()); // huyen
//			// spn_precinct.setText(contract.getContractNo()); // xa
//			// spn_streetBlockName.setText(contract.getStreetBlock()); // to
//			edt_streetName.setText(conTractBean.getStreetName()); // duong
//			edt_home.setText(conTractBean.getHome()); // so nha
//			edt_address.setText(conTractBean.getAddress()); // dia chi
//
//			edt_contactName.setText(conTractBean.getContactName()); // nguoi dai
//																// dien
//			edt_contactTitle.setText(conTractBean.getContactTitle()); // chuc vu
//
//			edt_telFax.setText(conTractBean.getTelFax()); // dien thoai co dinh
//			edt_telMobile.setText(conTractBean.getTelMobile()); // dien thoai di
//															// dong
//			edt_email.setText(conTractBean.getEmail()); // email
//			edt_idNo.setText(conTractBean.getIdNo());// chung minh thu
//
//			// hinh thuc thong bao cuoc
//			if (conTractBean.getNoticeCharge() != null && arrHTTBC != null) {
//				Utils.setDataSpinner(activity, arrHTTBC, spn_noticeCharge);
//				for (int i = 0; i < arrHTTBC.size(); i++) {
//					Spin spin = arrHTTBC.get(i);
//					if (spin.getId().equalsIgnoreCase(
//							conTractBean.getNoticeCharge())) {
//						spn_noticeCharge.setSelection(i);
//						break;
//					}
//				}
//			}
//
//			// thong tin bo sung
//			// Utils.setDefaultSpinner(activity, contract.getContractPrint(),
//			// spn_contractPrint);
//			// if(contract.getContractPrint() != null && arrTTBSHD != null) {
//			// for (int i = 0; i < arrTTBSHD.size(); i++) {
//			// Spin spin = arrTTBSHD.get(i);
//			// if(spin.getId().equalsIgnoreCase(contract.getContractPrint())) {
//			// spn_contractPrint.setSelection(i);
//			// break;
//			// }
//			// }
//			// }
//
//			edt_billAddress.setText(conTractBean.getBillAddress()); // dia chi
//																// thongbao cuoc
//
//			/**
//			 * phu luc hop dong
//			 */
//
//			// edt_contractOffer_represent.setText(contract.getContractOffer()
//			// .getRepresent()); // nguoi dai dien
//			// edt_contractOffer_representPosition.setText(contract
//			// .getContractOffer().getRepresentPosition()); // chuc vu
//			// edt_contractOffer_signDatetime.setText(contract.getContractOffer()
//			// .getSignDatetime());// ngay
//			// // ky
//			// // spn_contractOffer_idType;// loai giay to
//			// if(contract.getContractOffer().getIdType() != null &&
//			// arrTypePaperBeans != null) {
//			// for (int i = 0; i < arrTypePaperBeans.size(); i++) {
//			// TypePaperBeans typePaperBean = arrTypePaperBeans.get(i);
//			// if(typePaperBean.getParType().equalsIgnoreCase(contract.getContractOffer().getIdType()))
//			// {
//			// spn_contractOffer_idType.setSelection(i);
//			// break;
//			// }
//			// }
//			// }
//			//
//			// edt_contractOffer_idNo.setText(contract.getContractOffer()
//			// .getIdNo());// ma giai to
//			// edt_contractOffer_issueDatetime.setText(contract.getContractOffer()
//			// .getIssueDatetime());// ngay cap
//			// edt_contractOffer_issuePlace.setText(contract.getContractOffer()
//			// .getIssuePlace());// noi cap
//
//			ContractBank contractBank = conTractBean.getContractBank();
//			if (contractBank != null) {
//				edt_account.setText(contractBank.getAccount()); // bank tai
//																// khoan
//				edt_accountName.setText(contractBank.getAccountName()); // bank
//																		// ten
//																		// tai
//																		// khoan
//				edt_bankCode.setText(contractBank.getName()); // bank ma
//																// ngan hang
//				edt_bankContractNo.setText(contractBank.getBankContractNo()); // bank
//																				// hop
//																				// dong
//				edt_strBankContractDate.setText(contractBank
//						.getStrBankContractDate()); // bank
//			}
//		}
//
//		Button btn_insert_contract_offer = (Button) dialog
//				.findViewById(R.id.btn_insert_contract_offer);
//
//		/**
//		 * TT phu luc hop dong
//		 */
//		/*
//	  Dialog Phu luc hop dong cu
//
//	  */
//        LinearLayout lnPhuLucHopDong_p = (LinearLayout) dialog
//                .findViewById(R.id.lnPhuLucHopDong_p);
//		if ((custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean
//				.getBusPermitNo().isEmpty())) {
//			lnDaidienHopDong_p.setVisibility(View.VISIBLE);
//			lnPhuLucHopDong_p.setVisibility(View.VISIBLE);
//			btn_insert_contract_offer.setVisibility(View.VISIBLE);
//		} else {
//			lnDaidienHopDong_p.setVisibility(View.GONE);
//			lnPhuLucHopDong_p.setVisibility(View.GONE);
//			btn_insert_contract_offer.setVisibility(View.GONE);
//		}
//
//		spinner_plhopdong = (Spinner) dialog
//				.findViewById(R.id.spinner_plhopdong);
//		ln_contractOffer_Id_p = (LinearLayout) dialog
//				.findViewById(R.id.ln_contractOffer_Id_p);
//		edt_contractOffer_Id_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_Id_p);
//
//		edt_contractOffer_represent_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_represent); // nguoi
//																	// dai
//																	// dien
//		edt_contractOffer_representPosition_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_representPosition); // chuc
//																			// vu
//		edt_contractOffer_signDatetime_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_signDatetime);// ngay
//																	// ky
//		edt_contractOffer_signDatetime_p.setText(day);
//		edt_contractOffer_signDatetime_p.setOnClickListener(editTextListener);
//		spn_contractOffer_idType_p = (Spinner) dialog
//				.findViewById(R.id.spn_contractOffer_idType);// loai
//																// giay
//																// to
//		edt_contractOffer_idNo_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_idNo);// ma
//															// giai
//															// to
//		edt_contractOffer_issueDatetime_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_issueDatetime);// ngay
//																	// cap
//		edt_contractOffer_issueDatetime_p.setOnClickListener(editTextListener);
//		edt_contractOffer_issuePlace_p = (EditText) dialog
//				.findViewById(R.id.edt_contractOffer_issuePlace);// noi
//																	// cap
//
//		initTypePaper(spn_contractOffer_idType_p, arrTypePaperBeans);
//
//		spinner_plhopdong
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//					@Override
//					public void onItemSelected(AdapterView<?> adapterView,
//							View view, int position, long id) {
//						if (position == 0) {
//							Log.d(Constant.TAG,
//									"spinner_plhopdong onItemSelected "
//											+ position
//											+ " Nhap phu luc hop dong moi");
//
//							ln_contractOffer_Id_p.setVisibility(View.GONE);
//
//							edt_contractOffer_Id_p.setText("");
//							edt_contractOffer_represent_p.setText("");
//							edt_contractOffer_representPosition_p.setText("");
//							edt_contractOffer_signDatetime_p.setText("");
//							edt_contractOffer_idNo_p.setText("");
//							edt_contractOffer_issueDatetime_p.setText("");
//							edt_contractOffer_issuePlace_p.setText("");
//
//							edt_contractOffer_Id_p.setEnabled(true);
//							edt_contractOffer_represent_p.setEnabled(true);
//							edt_contractOffer_representPosition_p
//									.setEnabled(true);
//							edt_contractOffer_signDatetime_p.setEnabled(true);
//							spn_contractOffer_idType_p.setEnabled(true);
//							edt_contractOffer_idNo_p.setEnabled(true);
//							edt_contractOffer_issueDatetime_p.setEnabled(true);
//							edt_contractOffer_issuePlace_p.setEnabled(true);
//
//						} else {
//							ContractOffersObj contractOffersObj = arrContractOffersObj
//									.get(position);
//							Log.d(Constant.TAG,
//									"spinner_plhopdong onItemSelected "
//											+ position
//											+ " Chon phu luc hop dong cu "
//											+ contractOffersObj.toString());
//
//							ln_contractOffer_Id_p.setVisibility(View.VISIBLE);
//
//							edt_contractOffer_Id_p.setText(contractOffersObj
//									.getContractOfferId());
//							edt_contractOffer_Id_p.setEnabled(false);
//							edt_contractOffer_represent_p
//									.setText(contractOffersObj.getRepresent());
//							edt_contractOffer_represent_p.setEnabled(false);
//							edt_contractOffer_representPosition_p
//									.setText(contractOffersObj
//											.getRepresentPosition());
//							edt_contractOffer_representPosition_p
//									.setEnabled(false);
//							edt_contractOffer_signDatetime_p
//									.setText(StringUtils
//											.convertDate(contractOffersObj
//													.getStrSignDatetime()));
//							edt_contractOffer_signDatetime_p.setEnabled(false);
//
//							if (!CommonActivity.isNullOrEmpty(contractOffersObj
//									.getIdType())) {
//								if (arrTypePaperBeans != null
//										&& !arrTypePaperBeans.isEmpty()) {
//									for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
//										if (contractOffersObj.getIdType()
//												.equals(typePaperBeans
//														.getParType())) {
//											spn_contractOffer_idType_p
//													.setSelection(arrTypePaperBeans
//															.indexOf(typePaperBeans));
//											break;
//										}
//									}
//								}
//							}
//							spn_contractOffer_idType_p.setEnabled(false);
//
//							edt_contractOffer_idNo_p.setText(contractOffersObj
//									.getIdNo());
//							edt_contractOffer_idNo_p.setEnabled(false);
//							edt_contractOffer_issueDatetime_p
//									.setText(StringUtils
//											.convertDate(contractOffersObj
//													.getStrIssueDatetime()));
//							edt_contractOffer_issueDatetime_p.setEnabled(false);
//							edt_contractOffer_issuePlace_p
//									.setText(contractOffersObj.getIssuePlace());
//							edt_contractOffer_issuePlace_p.setEnabled(false);
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//				});
//
//		btn_insert_contract_offer.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view) {
//
//				int pos_spinner_plhopdong = spinner_plhopdong
//						.getSelectedItemPosition();
//
//				if (pos_spinner_plhopdong > 0) {
//					ContractOffersObj contractOffersObj = arrContractOffersObj
//							.get(pos_spinner_plhopdong);
//
//					Contract contract = new Contract();
//					ContractOffer contractOffer = new ContractOffer();
//					contractOffer.setContractOfferId(Long
//							.parseLong(contractOffersObj.getContractOfferId()));
//
//					contract.setContractOffer(contractOffer);
//
//					custommerByIdNoBean.setContract(contract);
//
//					dialog.dismiss();
//				} else {
//					Contract contract = new Contract();
//
//					ContractOffer contractOffer = new ContractOffer();
//
//					contractOffer.setRepresent(edt_contractOffer_represent_p
//							.getText().toString()); // nguoi
//													// dai
//													// dien
//					contractOffer
//							.setRepresentPosition(edt_contractOffer_representPosition_p
//									.getText().toString()); // chucvu
//					contractOffer
//							.setSignDatetime(edt_contractOffer_signDatetime_p
//									.getText().toString()); // ngay
//															// ky
//					// loai giay to
//					int pos_contractOffer_idType_p = spn_contractOffer_idType_p
//							.getSelectedItemPosition();
//					contractOffer.setIdType(arrTypePaperBeans.get(
//							pos_contractOffer_idType_p).getParType());
//
//					contractOffer.setIdNo(edt_contractOffer_idNo_p.getText()
//							.toString());// ma
//											// giai
//											// to
//					contractOffer
//							.setIssueDatetime(edt_contractOffer_issueDatetime_p
//									.getText().toString());// ngay
//															// cap
//					contractOffer.setIssuePlace(edt_contractOffer_issuePlace_p
//							.getText().toString());// noi
//													// cap
//
//					contract.setContractOffer(contractOffer);
//
//					custommerByIdNoBean.setContract(contract);
//
//					String message = null;
//
//					boolean valid = true; // UI.validateInput(dialogView);
//
//					if ("".equalsIgnoreCase(edt_contractOffer_represent_p
//							.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_represent_p.requestFocus();
//					} else if (""
//							.equalsIgnoreCase(edt_contractOffer_representPosition_p
//									.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_representPosition_p.requestFocus();
//					} else if (""
//							.equalsIgnoreCase(edt_contractOffer_signDatetime_p
//									.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_signDatetime_p.requestFocus();
//					} else if (""
//							.equalsIgnoreCase(edt_contractOffer_represent_p
//									.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_represent_p.requestFocus();
//					} else if ("".equalsIgnoreCase(edt_contractOffer_idNo_p
//							.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_idNo_p.requestFocus();
//					} else if (""
//							.equalsIgnoreCase(edt_contractOffer_issueDatetime_p
//									.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_issueDatetime_p.requestFocus();
//					} else if (""
//							.equalsIgnoreCase(edt_contractOffer_issuePlace_p
//									.getText().toString().trim())) {
//						valid = false;
//						edt_contractOffer_issuePlace_p.requestFocus();
//					} else if ("1".equalsIgnoreCase(contract.getContractOffer()
//							.getIdType())
//							&& (edt_contractOffer_idNo_p.getText().toString()
//									.trim().length() != 9 && edt_contractOffer_idNo_p
//									.getText().toString().trim().length() != 12)) {
//						valid = false;
//						edt_contractOffer_idNo_p.requestFocus();
//						message = getString(R.string.checksoidno);
//					}
//
//					if (valid) {
//						dialog.dismiss();
//					} else {
//						if (message == null) {
//							message = getString(R.string.title_input);
//						}
//						CommonActivity.createAlertDialog(activity, message,
//								getString(R.string.app_name)).show();
//						Log.d(Constant.TAG,
//								"showPopupInsertInfoContractOffer validateInput "
//										+ valid);
//					}
//				}
//			}
//		});
//
//		Button btncancel = (Button) dialog.findViewById(R.id.btn_cancel);
//		btncancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view) {
//
//				// contractId = "";
//				// contractNo = getActivity().getString(R.string.txt_select);
//				// if (arrTractBeans != null && arrTractBeans.size() > 0) {
//				//
//				// for (int i = 0; i < arrTractBeans.size(); i++) {
//				// if
//				// (arrTractBeans.get(i).getContractNo().equals(getActivity().getString(R.string.txt_select)))
//				// {
//				// spinner_loaihd.setSelection(arrTractBeans.indexOf(arrTractBeans.get(i)));
//				// break;
//				// }
//				// }
//				// }
//
//				dialog.dismiss();
//			}
//		});
//
//		GetContractOfferByContractIdAsynTask getContractOfferByContractIdAsynTask = new GetContractOfferByContractIdAsynTask(
//				activity);
//		getContractOfferByContractIdAsynTask.execute(conTractBean
//				.getContractId());
//
//		dialog.show();
//	}
//
//	private String initAddress() {
//		StringBuilder address = new StringBuilder();
//		if (!"".equalsIgnoreCase(edt_home.getText().toString().trim())) {
//			address.append(" ");
//			address.append(edt_home.getText().toString().trim());
//			address.append(" ");
//		}
//		if (!"".equalsIgnoreCase(edt_streetName.getText().toString().trim())) {
//			address.append(" ");
//			address.append(edt_streetName.getText().toString().trim());
//			address.append(" ");
//		}
//
//		if (spn_streetBlock.getSelectedItemPosition() >= 0
//				&& !"".equalsIgnoreCase(spn_streetBlock.getSelectedItem()
//						.toString())) {
//			address.append(spn_streetBlock.getSelectedItem().toString());
//			address.append(" ");
//		}
//		if (spn_precinct.getSelectedItemPosition() >= 0
//				&& !"".equalsIgnoreCase(spn_precinct.getSelectedItem()
//						.toString())) {
//			address.append(spn_precinct.getSelectedItem().toString());
//			address.append(" ");
//		}
//		if (spn_district.getSelectedItemPosition() >= 0
//				&& !"".equalsIgnoreCase(spn_district.getSelectedItem()
//						.toString())) {
//			address.append(spn_district.getSelectedItem().toString());
//			address.append(" ");
//		}
//		if (spn_province.getSelectedItemPosition() >= 0
//				&& !"".equalsIgnoreCase(spn_province.getSelectedItem()
//						.toString())) {
//			address.append(spn_province.getSelectedItem().toString());
//		}
//
//		return address.toString();
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
//				cal.setTime(date);
//
//			}
//
//			DatePickerDialog datePicker = new DatePickerDialog(activity,
//					AlertDialog.THEME_HOLO_LIGHT,datePickerListener, cal.get(Calendar.YEAR),
//					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
//
//			datePicker.getDatePicker().setTag(view);
//			datePicker.show();
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
//			}
//		}
//	};
//
//	// init typepaper
//	private ArrayList<TypePaperBeans> arrTypePaperBeans = null;
//
//	private ArrayAdapter<String> initTypePaper(Spinner spinner_type_giay_to,
//			ArrayList<TypePaperBeans> _arrTypePaperBeans) {
//		GetTypePaperDal dal = null;
//		ArrayAdapter<String> adapter = null;
//		try {
//
//			if (_arrTypePaperBeans != null) {
//				arrTypePaperBeans = _arrTypePaperBeans;
//			} else {
//				dal = new GetTypePaperDal(activity);
//				dal.open();
//				arrTypePaperBeans = dal.getLisTypepaper();
//				dal.close();
//			}
//			if (arrTypePaperBeans != null && !arrTypePaperBeans.isEmpty()) {
//				adapter = new ArrayAdapter<>(activity,
//                        android.R.layout.simple_dropdown_item_1line,
//                        android.R.id.text1);
//				for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
//					adapter.add(typePaperBeans.getParValues());
//				}
//				if (spinner_type_giay_to != null) {
//					spinner_type_giay_to.setAdapter(adapter);
//				}
//			}
//		} catch (Exception e) {
//			Log.e("initTypePaper", e.toString(), e);
//		} finally {
//			if (dal != null) {
//				dal.close();
//			}
//		}
//		return adapter;
//	}
//
//	/**
//	 * tinh huyen xa to
//	 */
//	private String dialogProvince;
//	private String dialogDistrict;
//	private String dialogPrecinct;
//	private String dialogStreetBlock;
//
//	private void timKiemDonVi() {
//		dialogLockBoxInfo();
//	}
//
//	private void dialogLockBoxInfo() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		// LocationService locationService = arrayListLocation.get(position);
//
//		LayoutInflater inflater = LayoutInflater.from(activity);
//		@SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_don_vi, null);
//
//		builder.setView(dialogView);
//		dialogDonVi = builder.create();
//
//		final EditText edtMaDonVi = (EditText) dialogView
//				.findViewById(R.id.edtMaDonVi);
//		final EditText edtTenDonVi = (EditText) dialogView
//				.findViewById(R.id.edtTenDonVi);
//		Button btnTimKiem = (Button) dialogView.findViewById(R.id.btnTimKiem);
//
//		lvListDonVi = (ListView) dialogView.findViewById(R.id.lvLockBoxInfo);
//
//		btnTimKiem.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				String maDonVi = edtMaDonVi.getText().toString();
//				String tenDonVi = edtTenDonVi.getText().toString();
//
//				hideKeyBoard();
//				if (maDonVi.equals("") && tenDonVi.equals("")) {
//					Toast.makeText(activity,
//							getString(R.string.chua_nhap_ma_dvi),
//							Toast.LENGTH_LONG).show();
//				} else {
//					getDonVi(maDonVi, tenDonVi);
//				}
//			}
//		});
//		lvListDonVi.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				mCodeDonVi = mAratListDonVi.get(position).getCode();
//				tvDonVi.setText(mAratListDonVi.get(position).getCodeName());
//				imgDeleteDV.setVisibility(View.VISIBLE);
//				dialogDonVi.dismiss();
//			}
//		});
//
//		dialogDonVi.show();
//
//	}
//
//	private void getDonVi(String maDonVi, String tenDonVi) {
//		new GetLisDonVi(maDonVi, tenDonVi).execute();
//
//	}
//
//	public class GetLisDonVi extends AsyncTask<Void, Void, String> {
//
//		private final String maDonVi;
//		private final String tenDonVi;
//		private final ProgressDialog dialog;
//
//		public GetLisDonVi(String maDonVi, String tenDonVi) {
//			this.maDonVi = maDonVi;
//			this.tenDonVi = tenDonVi;
//			dialog = new ProgressDialog(activity);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			this.dialog.setMessage(getResources().getString(R.string.waitting));
//			this.dialog.show();
//			super.onPreExecute();
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			String original = null;
//			boolean isNetwork = CommonActivity.isNetworkConnected(activity);
//			if (isNetwork) {
//				original = requestSeviceDonVi(maDonVi, tenDonVi, mCodeDoiTuong);
//			} else {
//			}
//
//			return original;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			if (dialog.isShowing()) {
//				dialog.dismiss();
//			}
//			if (result == null || result.equals("")) {
//				CommonActivity.createAlertDialog(activity,
//						getResources().getString(R.string.errorNetwork),
//						getResources().getString(R.string.app_name)).show();
//			} else {
//				parseResultError(result);
//				mAratListDonVi = parseResultDonVi(result);
//				Log.e("TAG6", "result List Don Vi : " + result);
//
//				if (mAratListDonVi.size() > 0) {
//					DonViAdapter adapter = new DonViAdapter(activity,
//							mAratListDonVi);
//					lvListDonVi.setAdapter(adapter);
//				} else {
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.ko_tim_thay_don_vi_nao),
//							getString(R.string.app_name)).show();// ,getString(R.string.thu_lai)
//				}
//			}
//			super.onPostExecute(result);
//		}
//	}
//
//	private String requestSeviceDonVi(String maDonVi, String tenDonVi,
//                                      String codeDoiTuong) {
//		String reponse = null;
//		String original = null;
//
//		String xml = mBccsGateway.getXmlCustomer(
//				createXMLDonVi(maDonVi, tenDonVi, codeDoiTuong),
//				"mbccs_getListDepByObject");
//		Log.e("TAG89", "xml Don Vi" + xml);
//		try {
//			reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
//					activity, "mbccs_getListDepByObject");
//			Log.e("TAG8", "reponse Don Vi" + reponse);
//		} catch (Exception e) {
//
//			Log.e("Exception", e.toString(), e);
//		}
//		if (reponse != null) {
//			CommonOutput commonOutput;
//			try {
//				commonOutput = mBccsGateway.parseGWResponse(reponse);
//				original = commonOutput.getOriginal();
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//
//		}
//		return original;
//	}
//
//	private String createXMLDonVi(String maDonVi, String tenDonVi,
//			String codeDoiTuong) {
//        return "<ws:getListDepByObject>" +
//                "<cmInput>" +
//                "<token>" + Session.getToken() + "</token>" +
//                "<deptCode>" + maDonVi + "</deptCode>" +
//                "<deptName>" + tenDonVi + "</deptName>" +
//                "<objectCode>" + codeDoiTuong + "</objectCode>" +
//                "</cmInput>" +
//                "</ws:getListDepByObject>";
//	}
//
//	private ArrayList<DoiTuongObj> parseResultDonVi(String result) {
//
//		ArrayList<DoiTuongObj> aratList = new ArrayList<>();
//		if (result != null) {
//			try {
//				Log.e("TAG69 Don Vi ", result);
//				XmlDomParse domParse = new XmlDomParse();
//				Document document = domParse.getDomElement(result);
//
//				NodeList nodeListErrorCode = document
//						.getElementsByTagName("lstDepartment");
//
//				for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
//					Node mNode = nodeListErrorCode.item(i);
//					Element element = (Element) mNode;
//					String id = domParse.getValue(element, "id");
//					String code = domParse.getValue(element, "code");
//					String codeName = domParse.getValue(element, "codeName");
//
//					int ID = Integer.parseInt(id);
//
//					DoiTuongObj doiTuongObj = new DoiTuongObj(ID, codeName,
//							code);
//					aratList.add(doiTuongObj);
//				}
//
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//		}
//		return aratList;
//	}
//
//	private void parseResultError(String result) {
//		if (result != null) {
//			try {
//				XmlDomParse domParse = new XmlDomParse();
//				Document document = domParse.getDomElement(result);
//
//				NodeList nodeListErrorCode = document
//						.getElementsByTagName("return");
//
//				for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
//					Node mNode = nodeListErrorCode.item(i);
//					Element element = (Element) mNode;
//					String errorCode = domParse.getValue(element, "errorCode");
//					String description = domParse.getValue(element,
//							"description");
//					String token = domParse.getValue(element, "token");
//					if (token == null || token.equals("")) {
//
//					} else {
//						Session.setToken(token);
//					}
//					if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
////						CommonActivity.BackFromLogin(activity, description,
////								";connect_mobile;");
//						LoginDialog dialog = new LoginDialog(getActivity(),
//								";connect_mobile;");
//
//						dialog.show();
//					} else if (errorCode.equals("0")) {
//					}
//				}
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//		}
//	}
//
//	private void hideKeyBoard() {
//		InputMethodManager imm = (InputMethodManager) activity
//				.getSystemService(Activity.INPUT_METHOD_SERVICE);
//		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//
//		case R.id.edtNgayBD:
//			showDatePickerDialog(edtNgayBD);
//			break;
//		case R.id.edtNgayKT:
//			showDatePickerDialog(edtNgayKT);
//			break;
//		case R.id.relaBackHome:
//			activity.onBackPressed();
//			break;
//
//		case R.id.tvpakage:
//			if (pakageBundeBean != null
//					&& pakageBundeBean.getArrChargeBeans().size() > 0) {
//				Intent intent = new Intent(activity,
//						FragmentSearchPakageMobile.class);
//				Bundle mBundle = new Bundle();
//				mBundle.putSerializable("PakageKey", pakageBundeBean);
//				intent.putExtras(mBundle);
//				startActivityForResult(intent, 3333);
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.notpakagemobile),
//						getString(R.string.app_name)).show();
//			}
//			break;
//		case R.id.btn_connection:
//			isdn = txtisdn.getText().toString();
//			if (isdn != null && !isdn.isEmpty()) {
//				if (isdn.substring(0, 1).equalsIgnoreCase("0")) {
//					isdn = isdn.substring(1, isdn.length());
//				}
//			}
//			// to = txtto.getText().toString();
//			// to.replace(" ", "");
//			// to = to.trim();
//
//			tenduong = txtduong.getText().toString();
//			// tenduong = tenduong.replace(" ", "");
//			// tenduong = tenduong.trim();
//
//			sonha = txtsonha.getText().toString();
//			// sonha = sonha.replace(" ", "");
//			// sonha = sonha.trim();
//			// thue bao tra truoc
//			if ("1".equalsIgnoreCase(subType)) {
//				validateSubPre();
//			} else {
//				// thue bao tra sau
//				validateSubPos();
//			}
//
//			break;
//
//		case R.id.edt_hthm:
//			Log.d(Constant.TAG,
//					"FragmentConnectionMobile onClick R.id.edt_hthm arrHthmBeans.size() "
//							+ arrHthmBeans.size());
//
//			if (arrHthmBeans.size() > 0) {
//				Intent intent = new Intent(activity,
//						SearchCodeHthmActivity.class);
//				intent.putExtra("arrHthmBeans", arrHthmBeans);
//				startActivityForResult(intent, 101);
//			}
//
//			break;
//		case R.id.edt_kmai:
//			if (arrPromotionTypeBeans.size() > 0) {
//				Intent intent = new Intent(activity,
//						SearchCodePromotionActivity.class);
//				intent.putExtra("arrPromotionTypeBeans", arrPromotionTypeBeans);
//				startActivityForResult(intent, 102);
//			}
//
//			break;
//
//		case R.id.edt_bankCode:
//			showDialogListBank();
//			break;
//
//		case R.id.btnloaihopdong:
//			new CacheDatabaseManager(activity).insertCacheList(TYPE_LOAI_HD,
//					null);
//			isRefreshTYPE_LOAI_HD = true;
//			AsyntaskGetListAllCommon asyntaskGetListAllCommon2 = new AsyntaskGetListAllCommon(
//					activity, TYPE_LOAI_HD);
//			asyntaskGetListAllCommon2.execute();
//			break;
//		case R.id.btnhttthd:
//			new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTHHD,
//					null);
//			isRefreshTYPE_HTTTHD = true;
//			AsyntaskGetListAllCommon asnGetHHTH = new AsyntaskGetListAllCommon(
//					activity, TYPE_HTTHHD);
//			asnGetHHTH.execute();
//			break;
//		case R.id.btnchukicuoc:
//			new CacheDatabaseManager(activity).insertCacheList(TYPE_CK_CUOC_HD,
//					null);
//			isRefreshTYPE_CK_CUOC_HD = true;
//			AsyntaskGetListAllCommon asnGetBillCycle = new AsyntaskGetListAllCommon(
//					activity, TYPE_CK_CUOC_HD);
//			asnGetBillCycle.execute();
//			break;
//		case R.id.btninchitiet:
//			new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTHHD,
//					null);
//			isRefreshTYPE_HTTTHD = true;
//			AsyntaskGetListAllCommon asnInChiTiet = new AsyntaskGetListAllCommon(
//					activity, TYPE_INCT_HD);
//			asnInChiTiet.execute();
//			break;
//		case R.id.btnhttbc:
//			new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTBC_HD,
//					null);
//			isRefreshTYPE_HTTBC_HD = true;
//			AsyntaskGetListAllCommon asnHTTBC = new AsyntaskGetListAllCommon(
//					activity, TYPE_HTTBC_HD);
//			asnHTTBC.execute();
//			break;
//		case R.id.btnttbosung:
//			new CacheDatabaseManager(activity).insertCacheList(TYPE_TTBS_HD,
//					null);
//            Boolean isRefreshTYPE_TTBS_HD = true;
//			AsyntaskGetListAllCommon asnTTBS = new AsyntaskGetListAllCommon(
//					activity, TYPE_TTBS_HD);
//			asnTTBS.execute();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void validateSubPre() {
//		if (serviceType != null && !serviceType.isEmpty()) {
//			if (offerId != null && !offerId.isEmpty()) {
//
//				if (Constant.OFFERID_TSV_SPEC.contains(offerId)) {
//					// TODO VALIDATE FORM THONG TIN DAC BIET
//					// if(tvDonVi.getText() != null &&
//					// !tvDonVi.getText().toString().isEmpty()){
//
//					if (!CommonActivity.isNullOrEmpty(edtNgayBD.getText()
//							.toString())) {
//
//						try {
//							if (dateBD.after(dateNow)) {
//								Toast.makeText(activity,
//										getString(R.string.chekngayBD1),
//										Toast.LENGTH_SHORT).show();
//							} else if (dateEnd.before(dateBD)) {
//								Toast.makeText(
//										activity,
//										getString(R.string.Todate_bigger_Fromdate),
//										Toast.LENGTH_SHORT).show();
//							} else if (CommonActivity
//									.isNullOrEmpty(edtMaGiayToDacBiet.getText()
//											.toString())) {
//								Toast.makeText(activity,
//										getString(R.string.checkidnospec),
//										Toast.LENGTH_SHORT).show();
//							} else {
//								if (hthm != null && !hthm.isEmpty()) {
//									if (txtisdn.getText() != null
//											&& !txtisdn.getText().toString()
//													.isEmpty()) {
//										if (txtserial.getText() != null
//												&& !txtserial.getText()
//														.toString().isEmpty()) {
//											checkHangHoa();
//
//										} else {
//											Toast.makeText(
//													activity,
//													getString(R.string.checkserial),
//													Toast.LENGTH_SHORT).show();
//										}
//
//									} else {
//										Toast.makeText(activity,
//												getString(R.string.checkisdn),
//												Toast.LENGTH_SHORT).show();
//									}
//								} else {
//									Toast.makeText(activity,
//											getString(R.string.checkhthm),
//											Toast.LENGTH_SHORT).show();
//								}
//
//							}
//						} catch (Exception e) {
//							Log.e("Exception", e.toString(), e);
//						}
//
//					} else {
//						Toast.makeText(activity,
//								getString(R.string.chekngayBD),
//								Toast.LENGTH_SHORT).show();
//					}
//
//					// }
//					// else{
//					// Toast.makeText(activity,
//					// getString(R.string.chekdonvi),
//					// Toast.LENGTH_SHORT).show();
//					// }
//
//				} else {
//
//					if (hthm != null && !hthm.isEmpty()) {
//						if (txtisdn.getText() != null
//								&& !txtisdn.getText().toString().isEmpty()) {
//							if (txtserial.getText() != null
//									&& !txtserial.getText().toString()
//											.isEmpty()) {
//								checkHangHoa();
//
//							} else {
//								Toast.makeText(activity,
//										getString(R.string.checkserial),
//										Toast.LENGTH_SHORT).show();
//							}
//
//						} else {
//							Toast.makeText(activity,
//									getString(R.string.checkisdn),
//									Toast.LENGTH_SHORT).show();
//						}
//					} else {
//						Toast.makeText(activity, getString(R.string.checkhthm),
//								Toast.LENGTH_SHORT).show();
//					}
//
//				}
//
//			} else {
//				Toast.makeText(activity, getString(R.string.checkpakage),
//						Toast.LENGTH_SHORT).show();
//			}
//		} else {
//			Toast.makeText(activity, getString(R.string.checkserviceType),
//					Toast.LENGTH_SHORT).show();
//		}
//
//	}
//
//	private void validateSubPos() {
//		if (serviceType != null && !serviceType.isEmpty()) {
//			if (offerId != null && !offerId.isEmpty()) {
//				if (subTypeMobile != null && !subTypeMobile.isEmpty()) {
//					if (hthm != null && !hthm.isEmpty()) {
//						if (!maKM.equals("-1")) {
//							if (txtisdn.getText() != null
//									&& !txtisdn.getText().toString().isEmpty()) {
//								if (txtserial.getText() != null
//										&& !txtserial.getText().toString()
//												.isEmpty()) {
//									if (!StringUtils.CheckCharSpecical(txtserial
//                                            .getText().toString())) {
//										if (to != null && !to.isEmpty()) {
//											if (tenduong != null
//													&& !tenduong.toString()
//															.isEmpty()) {
//												if (sonha != null
//														&& !sonha.toString()
//																.isEmpty()) {
//
//													if (getActivity()
//															.getString(
//																	R.string.txt_select)
//															.equals(contractNo)) {
//														Toast.makeText(
//																activity,
//																getString(R.string.notemptycontract),
//																Toast.LENGTH_SHORT)
//																.show();
//													} else {
//														checkHangHoatrasau();
//													}
//
//												} else {
//													Toast.makeText(
//															activity,
//															getString(R.string.checksonhamobile),
//															Toast.LENGTH_SHORT)
//															.show();
//												}
//
//											} else {
//												Toast.makeText(
//														activity,
//														getString(R.string.checkttduong),
//														Toast.LENGTH_SHORT)
//														.show();
//											}
//										} else {
//											Toast.makeText(
//													activity,
//													getString(R.string.checkto),
//													Toast.LENGTH_SHORT).show();
//										}
//
//									} else {
//										Toast.makeText(
//												activity,
//												getString(R.string.checkspecialSerial),
//												Toast.LENGTH_SHORT).show();
//									}
//
//								} else {
//									Toast.makeText(activity,
//											getString(R.string.checkserial),
//											Toast.LENGTH_SHORT).show();
//								}
//							} else {
//								Toast.makeText(activity,
//										getString(R.string.checkisdn),
//										Toast.LENGTH_SHORT).show();
//							}
//						} else {
//							Toast.makeText(
//									getActivity(),
//									getResources().getString(
//											R.string.checkselectmakm),
//									Toast.LENGTH_SHORT).show();
//						}
//
//					} else {
//						Toast.makeText(activity, getString(R.string.checkhthm),
//								Toast.LENGTH_SHORT).show();
//					}
//				} else {
//					Toast.makeText(activity, getString(R.string.checktypeSub),
//							Toast.LENGTH_SHORT).show();
//				}
//			} else {
//				Toast.makeText(activity, getString(R.string.checkpakage),
//						Toast.LENGTH_SHORT).show();
//			}
//		} else {
//			Toast.makeText(activity, getString(R.string.checkserviceType),
//					Toast.LENGTH_SHORT).show();
//		}
//
//	}
//
//	private boolean isUploadImage() {
//		Log.d(Constant.TAG,
//				"isUploadImage() hashmapFileObj: " + hashmapFileObj.size()
//						+ " " + hashmapFileObj);
//		Log.d(Constant.TAG, "isUploadImage() mapListRecordPrepaid: "
//				+ mapListRecordPrepaid.size() + " " + mapListRecordPrepaid);
//
//        return !hashmapFileObj.isEmpty()
//                && hashmapFileObj.size() == mapListRecordPrepaid.size();
//	}
//
//	private void checkHangHoa() {
//		if (arrStockTypeBeans.size() > 0) {
//			if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
//
//				if (mapListRecordPrepaid != null
//						&& !mapListRecordPrepaid.isEmpty()) {
//
//					boolean isSelectIm = isUploadImage();// true;
//					// for (int i = 0; i < mListIvO.size(); i++) {
//					// boolean b = mListIvO.get(i).isSetIm();
//					// if (b == false) {
//					// isSelectIm = false;
//					// // return;
//					// }
//					// }
//					if (isSelectIm) {
//						subConnectPre();
//					} else {
//						Toast.makeText(activity,
//								getString(R.string.chua_chon_het_anh),
//								Toast.LENGTH_LONG).show();
//					}
//				} else {
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.checkthongtinchungtu),
//							getString(R.string.app_name)).show();
//				}
//
//			} else {
//				Toast.makeText(activity,
//						getResources().getString(R.string.checkhanghoa),
//						Toast.LENGTH_LONG).show();
//			}
//
//		} else {
//
//			if (mapListRecordPrepaid != null && !mapListRecordPrepaid.isEmpty()) {
//
//				boolean isSelectIm = isUploadImage();// true;
//				// for (int i = 0; i < mListIvO.size(); i++) {
//				// boolean b = mListIvO.get(i).isSetIm();
//				// if (b == false) {
//				// isSelectIm = false;
//				// // return;
//				// }
//				// }
//				if (isSelectIm) {
//					subConnectPre();
//				} else {
//					Toast.makeText(activity,
//							getString(R.string.chua_chon_het_anh),
//							Toast.LENGTH_LONG).show();
//				}
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.checkthongtinchungtu),
//						getString(R.string.app_name)).show();
//			}
//		}
//	}
//
//	private void checkHangHoatrasau() {
//		if (arrPaymentPrePaidPromotionBeans != null
//				&& arrPaymentPrePaidPromotionBeans.size() > 1) {
//			if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
//				if (arrStockTypeBeans.size() > 0) {
//					if (mapSubStockModelRelReq.size() == arrStockTypeBeans
//							.size()) {
//						boolean isSelectIm = isUploadImage();// true;
//						// for (int i = 0; i < mListIvO.size(); i++) {
//						// boolean b = mListIvO.get(i).isSetIm();
//						// if (b == false) {
//						// isSelectIm = false;
//						// // return;
//						// }
//						// }
//						if (isSelectIm) {
//							subConnectPos();
//						} else {
//							Toast.makeText(activity,
//									getString(R.string.chua_chon_het_anh),
//									Toast.LENGTH_LONG).show();
//						}
//					} else {
//						Toast.makeText(
//								activity,
//								getResources().getString(R.string.checkhanghoa),
//								Toast.LENGTH_LONG).show();
//					}
//
//				} else {
//					boolean isSelectIm = isUploadImage();// true;
//					// for (int i = 0; i < mListIvO.size(); i++) {
//					// boolean b = mListIvO.get(i).isSetIm();
//					// if (b == false) {
//					// isSelectIm = false;
//					// // return;
//					// }
//					// }
//					if (isSelectIm) {
//						subConnectPos();
//
//					} else {
//						Toast.makeText(activity,
//								getString(R.string.chua_chon_het_anh),
//								Toast.LENGTH_LONG).show();
//					}
//				}
//			} else {
//
//				if (!CommonActivity.isNullOrEmpty(maKM)) {
//					CommonActivity.createAlertDialog(getActivity(),
//							getString(R.string.checkcuocdongtruoc),
//							getString(R.string.app_name)).show();
//				} else {
//					if (arrStockTypeBeans.size() > 0) {
//						if (mapSubStockModelRelReq.size() == arrStockTypeBeans
//								.size()) {
//							boolean isSelectIm = isUploadImage();// true;
//							// for (int i = 0; i < mListIvO.size(); i++) {
//							// boolean b = mListIvO.get(i).isSetIm();
//							// if (b == false) {
//							// isSelectIm = false;
//							// // return;
//							// }
//							// }
//							if (isSelectIm) {
//								subConnectPos();
//							} else {
//								Toast.makeText(activity,
//										getString(R.string.chua_chon_het_anh),
//										Toast.LENGTH_LONG).show();
//							}
//						} else {
//							Toast.makeText(
//									activity,
//									getResources().getString(
//											R.string.checkhanghoa),
//									Toast.LENGTH_LONG).show();
//						}
//					} else {
//						boolean isSelectIm = isUploadImage();// true;
//						// for (int i = 0; i < mListIvO.size(); i++) {
//						// boolean b = mListIvO.get(i).isSetIm();
//						// if (b == false) {
//						// isSelectIm = false;
//						// // return;
//						// }
//						// }
//						if (isSelectIm) {
//							subConnectPos();
//						} else {
//							Toast.makeText(activity,
//									getString(R.string.chua_chon_het_anh),
//									Toast.LENGTH_LONG).show();
//						}
//					}
//
//				}
//
//			}
//
//		} else {
//			if (arrStockTypeBeans.size() > 0) {
//				if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
//					boolean isSelectIm = isUploadImage();// true;
//					// for (int i = 0; i < mListIvO.size(); i++) {
//					// boolean b = mListIvO.get(i).isSetIm();
//					// if (b == false) {
//					// isSelectIm = false;
//					// // return;
//					// }
//					// }
//					if (isSelectIm) {
//						subConnectPos();
//					} else {
//						Toast.makeText(activity,
//								getString(R.string.chua_chon_het_anh),
//								Toast.LENGTH_LONG).show();
//					}
//				} else {
//					Toast.makeText(activity,
//							getResources().getString(R.string.checkhanghoa),
//							Toast.LENGTH_LONG).show();
//				}
//
//			} else {
//				boolean isSelectIm = isUploadImage();// true;
//				// for (int i = 0; i < mListIvO.size(); i++) {
//				// boolean b = mListIvO.get(i).isSetIm();
//				// if (b == false) {
//				// isSelectIm = false;
//				// // return;
//				// }
//				// }
//				if (isSelectIm) {
//					subConnectPos();
//
//				} else {
//					Toast.makeText(activity,
//							getString(R.string.chua_chon_het_anh),
//							Toast.LENGTH_LONG).show();
//				}
//			}
//		}
//
//	}
//
//	private void subConnectPre() {
//		if (CommonActivity.isNetworkConnected(activity)) {
//
//			CommonActivity
//					.createDialog(
//							activity,
//							activity.getResources().getString(
//									R.string.checksubconnect),
//							activity.getResources()
//									.getString(R.string.app_name),
//							activity.getResources()
//									.getString(R.string.buttonOk),
//							activity.getResources().getString(R.string.cancel),
//							subConnectClickPre, null).show();
//
//		} else {
//			CommonActivity.createAlertDialog(activity,
//					activity.getResources().getString(R.string.errorNetwork),
//					activity.getResources().getString(R.string.app_name))
//					.show();
//		}
//
//	}
//
//	private void subConnectPos() {
//		if (CommonActivity.isNetworkConnected(activity)) {
//
//			CommonActivity
//					.createDialog(
//							activity,
//							activity.getResources().getString(
//									R.string.checksubconnect),
//							activity.getResources()
//									.getString(R.string.app_name),
//							activity.getResources()
//									.getString(R.string.buttonOk),
//							activity.getResources().getString(R.string.cancel),
//							subConnectClickPos, null).show();
//
//		} else {
//			CommonActivity.createAlertDialog(activity,
//					activity.getResources().getString(R.string.errorNetwork),
//					activity.getResources().getString(R.string.app_name))
//					.show();
//		}
//
//	}
//
//	private final OnClickListener subConnectClickPre = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			if (CommonActivity.isNetworkConnected(activity)) {
//				timeStart = new Date();
//				SubConnectPreAsyn subConnectPreAsyn = new SubConnectPreAsyn(
//						activity);
//				subConnectPreAsyn.execute();
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.errorNetwork),
//						getString(R.string.app_name)).show();
//			}
//		}
//	};
//
//	private final OnClickListener subConnectClickPos = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			if (CommonActivity.isNetworkConnected(activity)) {
//				timeStart = new Date();
//				SubConnectPosAsyn subConnectPosAsyn = new SubConnectPosAsyn(
//						activity, custommerByIdNoBean.getContract());
//				subConnectPosAsyn.execute();
//			} else {
//				CommonActivity.createAlertDialog(activity,
//						getString(R.string.errorNetwork),
//						getString(R.string.app_name)).show();
//			}
//		}
//	};
//
//	// LOAD DATA THUE BAO TRA SAU
//	private void CheckTBTraSau() {
//		if (subType != null && !subType.equals("")) {
//
//			if (subType.equals("2")) {
//				// co kuyen mai == thue bao tra sau
//				lnkhuyenmai.setVisibility(View.VISIBLE);
//				lnloaithuebao.setVisibility(View.VISIBLE);
//				lnhopdong.setVisibility(View.VISIBLE);
//				lnsonha.setVisibility(View.VISIBLE);
//			} else {
//				lnsonha.setVisibility(View.GONE);
//				lnkhuyenmai.setVisibility(View.GONE);
//				lnloaithuebao.setVisibility(View.GONE);
//				lnhopdong.setVisibility(View.GONE);
//			}
//		}
//	}
//
//	// RELOAD DATA ====
//
//	private void ReLoadData() {
//		// reload dich vu
//		// positonservice = -1;
//
//		// === reload goi cuoc =============
//		offerId = "";
//		productCode = "";
//		txtpakage.setText(Html.fromHtml("<u>"
//				+ getString(R.string.chonpakage_mobile) + "</u>"));
//		pakaChargeBeans = new PakageChargeBeans();
//		if (arrPakageChargeBeans != null && arrPakageChargeBeans.size() > 0) {
//			arrPakageChargeBeans.clear();
//		}
//		if (pakageBundeBean.getArrChargeBeans() != null
//				&& pakageBundeBean.getArrChargeBeans().size() > 0) {
//			pakageBundeBean = new PakageBundeBean();
//		}
//		// ==== reload loai thue bao ========
//		subTypeMobile = "";
//		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
//			arrSubTypeBeans.clear();
//			initListSubTypeNotData();
//		}
//		// ==== reload hthm ===============
//		hthm = "";
//		reasonId = "";
//		if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
//			arrHthmBeans.clear();
//			initHTHMNotData();
//		}
//		CheckTBTraSau();
//		txtisdn.setText("");
//		txtserial.setText("");
//		txtimsi.setText("");
//		hthm = "";
//		if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0
//				&& adapProductAdapter != null) {
//			arrStockTypeBeans.clear();
//			adapProductAdapter = new GetProductAdapter(arrStockTypeBeans,
//					activity);
//			lvproduct.setAdapter(adapProductAdapter);
//			adapProductAdapter.notifyDataSetChanged();
//		}
//		if (mapSubStockModelRelReq != null && mapSubStockModelRelReq.size() > 0) {
//			mapSubStockModelRelReq.clear();
//		}
//
//	}
//
//	// lay ma tinh/thanhpho
//
//	private void initProvince(Spinner province,
//			ArrayList<AreaBean> _arrProvince, int _spn_province_pos) {
//		try {
//			// if (_arrProvince != null) {
//			// arrProvince = _arrProvince;
//			// } else {
//			GetAreaDal dal = new GetAreaDal(activity);
//			dal.open();
//			arrProvince = dal.getLstProvince();
//			dal.close();
//			// }
//
//			AreaBean spinProvince = new AreaBean();
//			spinProvince.setProvince("");
//			spinProvince.setNameProvince(getString(R.string.txt_select));
//			arrProvince.add(0, spinProvince);
//
//			if (arrProvince != null && arrProvince.size() > 0) {
//				ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                        activity, android.R.layout.simple_dropdown_item_1line,
//                        android.R.id.text1);
//				for (AreaBean areaBean : arrProvince) {
//					adapter.add(areaBean.getNameProvince());
//				}
//				province.setAdapter(adapter);
//				// if(_spn_province_pos > 0 && _spn_province_pos <
//				// arrProvince.size()) {
//				province.setSelection(arrProvince.indexOf(arrProvince.get(0)));
//				// }
//			}
//		} catch (Exception ex) {
//			Log.e("initProvince", ex.toString());
//		}
//	}
//
//	// lay huyen/quan theo tinh
//	private void initDistrict(String province, Spinner district,
//			int _spn_district_pos) {
//		try {
//			// boolean sameDistrict = false;
//			// if(arrDistrict != null && !arrDistrict.isEmpty()) {
//			// AreaBean areaBean = arrDistrict.get(0);
//			// if(province.equalsIgnoreCase(areaBean.getProvince())) {
//			// sameDistrict = true;
//			// }
//			// }
//
//			if (!CommonActivity.isNullOrEmpty(province)) {
//				GetAreaDal dal = new GetAreaDal(activity);
//				dal.open();
//				arrDistrict = dal.getLstDistrict(province);
//				dal.close();
//				Log.d(Constant.TAG, "initDistrict province: " + province
//						+ " spn_district_pos: " + _spn_district_pos);
//			}
//
//			AreaBean spinDistrict = new AreaBean();
//			spinDistrict.setDistrict("");
//			spinDistrict.setNameDistrict(getString(R.string.txt_select));
//			arrDistrict.add(0, spinDistrict);
//
//			if (arrDistrict != null && arrDistrict.size() > 0) {
//				ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                        activity, android.R.layout.simple_dropdown_item_1line,
//                        android.R.id.text1);
//				for (AreaBean areaBean : arrDistrict) {
//					adapter.add(areaBean.getNameDistrict());
//				}
//				Log.d(this.getClass().getSimpleName(), "setAdapter"
//						+ arrDistrict.size());
//				district.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
//				// if(_spn_district_pos > 0 && _spn_district_pos <
//				// arrDistrict.size()) {
//				// district.setSelection(arrDistrict.indexOf(arrDistrict.get(0)));
//				// }
//			}
//		} catch (Exception ex) {
//			Log.e("initDistrict", ex.toString());
//		}
//	}
//
//	// lay phuong/xa theo tinh,qhuyen
//	private void initPrecinct(String province, String district,
//			Spinner precinct, int _spn_precinct_pos) {
//		try {
//			// boolean samePrecinct = false;
//			// if(arrPrecinct != null && !arrPrecinct.isEmpty()) {
//			// AreaBean areaBean = arrPrecinct.get(0);
//			// if(province.equalsIgnoreCase(areaBean.getProvince()) &&
//			// district.equalsIgnoreCase(areaBean.getDistrict())) {
//			// samePrecinct = true;
//			// }
//			// }
//			if (!CommonActivity.isNullOrEmpty(province)
//					&& !CommonActivity.isNullOrEmpty(district)) {
//				GetAreaDal dal = new GetAreaDal(activity);
//				dal.open();
//				arrPrecinct = dal.getLstPrecinct(province, district);
//				dal.close();
//			}
//			AreaBean spinPrecinct = new AreaBean();
//			spinPrecinct.setPrecinct("");
//			spinPrecinct.setNamePrecint(getString(R.string.txt_select));
//			arrPrecinct.add(0, spinPrecinct);
//
//			if (arrPrecinct != null && arrPrecinct.size() > 0) {
//				ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                        activity, android.R.layout.simple_dropdown_item_1line,
//                        android.R.id.text1);
//				for (AreaBean areaBean : arrPrecinct) {
//					adapter.add(areaBean.getNamePrecint());
//				}
//				precinct.setAdapter(adapter);
//				// if(_spn_precinct_pos > 0 && _spn_precinct_pos <
//				// arrPrecinct.size()) {
//				precinct.setSelection(arrPrecinct.indexOf(arrPrecinct.get(0)));
//				// }
//			}
//		} catch (Exception ex) {
//			Log.e("initPrecinct", ex.toString(), ex);
//		}
//	}
//
//	// lay dich vu
//	private void initTelecomService() {
//		try {
//
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
//				spinner_serviceMobile.setAdapter(adapter);
//			}
//		} catch (Exception e) {
//			Log.e("initTelecomService", e.toString());
//		}
//	}
//
//	// move login
//    private final OnClickListener moveLogInAct = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			// Intent intent = new Intent(activity, LoginActivity.class);
//			// startActivity(intent);
//			// activity.finish();
//			// if (MainActivity.getInstance() != null) {
//			// MainActivity.getInstance().finish();
//			// }
//			LoginDialog dialog = new LoginDialog(getActivity(),
//					";connect_mobile;");
//
//			dialog.show();
//		}
//	};
//
//	// ========= init spinner Contract
//	private void initContract() {
//		ConTractBean conTractBean0 = new ConTractBean();
//		conTractBean0.setContractNo(getString(R.string.txt_select));
//		conTractBean0.setContractId("");
//		arrTractBeans.add(0, conTractBean0);
//
//		ConTractBean conTractBean1 = new ConTractBean();
//		conTractBean1.setContractNo(getString(R.string.contractNew));
//		conTractBean1.setContractId("");
//		arrTractBeans.add(1, conTractBean1);
//		if (arrTractBeans != null && arrTractBeans.size() > 0) {
//			ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                    android.R.layout.simple_dropdown_item_1line,
//                    android.R.id.text1);
//			for (ConTractBean conTractBean : arrTractBeans) {
//				adapter.add(conTractBean.getContractNo());
//			}
//			spinner_loaihd.setAdapter(adapter);
//		}
//
//	}
//
//	// === hthm co data
//	private void initHTHM() {
//		edt_hthm.setText(getString(R.string.chonhthm));
//		// HTHMBeans hthmBeans = new HTHMBeans();
//		// hthmBeans.setName(getResources().getString(R.string.chonhthm));
//		// arrHthmBeans.add(0, hthmBeans);
//
//		// if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
//		// ArrayAdapter<String> adapter = new
//		// ArrayAdapter<String>(activity, R.layout.pakage_charge_item,
//		// R.id.txtpakage);
//		// adapter.setDropDownViewResource(R.layout.pakage_charge_item);
//		// for (HTHMBeans hBeans : arrHthmBeans) {
//		// if (hBeans.getCode() == null) {
//		// adapter.add(hBeans.getName());
//		// } else {
//		// adapter.add(hBeans.getCode() + "-" + hBeans.getName());
//		// }
//		// }
//		// spinner_hthm.setAdapter(adapter);
//		// }
//	}
//
//	// hthm ko data
//	private void initHTHMNotData() {
//		edt_hthm.setText(getString(R.string.chonhthm));
//		// HTHMBeans hthmBeans = new HTHMBeans();
//		// hthmBeans.setName(getResources().getString(R.string.chonhthm));
//		// arrHthmBeans.add(0, hthmBeans);
//		// HTHMBeans hthmBeans = new HTHMBeans();
//		// hthmBeans.setName(getResources().getString(R.string.chonhthm));
//		// ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
//		// lstHthmBeans.add(0, hthmBeans);
//		// if (lstHthmBeans != null && lstHthmBeans.size() > 0) {
//		// ArrayAdapter<String> adapter = new
//		// ArrayAdapter<String>(activity,
//		// android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
//		// for (HTHMBeans hBeans : lstHthmBeans) {
//		// adapter.add(hBeans.getName());
//		// }
//		// spinner_hthm.setAdapter(adapter);
//		// }
//	}
//
//	// init spinner listsubPromotion
//
//	private void initInitListPromotion() {
//
//		// PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
//		// promotionTypeBeans.setCodeName(getResources().getString(R.string.selectMKM));
//		// arrPromotionTypeBeans.add(0, promotionTypeBeans);
//		PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
//		promotionTypeBeans.setCodeName(getResources().getString(
//				R.string.chonctkm1));
//		promotionTypeBeans.setPromProgramCode("-1");
//		arrPromotionTypeBeans.add(0, promotionTypeBeans);
//
//		// if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() >
//		// 1) {
//		//
//		// }else{
//		PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
//		promotionTypeBeans1.setCodeName(getResources().getString(
//				R.string.selectMKM));
//		promotionTypeBeans1.setPromProgramCode("");
//		arrPromotionTypeBeans.add(1, promotionTypeBeans1);
//		// }
//		// edt_kmai.setText(getString(R.string.chonctkm1));
//		// maKM = "";
//		// prepaidCode = "";
//
//		// if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() >
//		// 0) {
//		// adapterHTKM = new ArrayAdapter<String>(activity,
//		// android.R.layout.simple_dropdown_item_1line,
//		// android.R.id.text1);
//		// for (PromotionTypeBeans proBeans : arrPromotionTypeBeans) {
//		// adapterHTKM.add(proBeans.getCodeName());
//		// }
//		// spinner_kmai.setAdapter(adapterHTKM);
//		// }
//	}
//
//	// init spinner not data subpromotion
//    private void initInitListPromotionNotData() {
//
//		edt_kmai.setText(getString(R.string.chonctkm1));
//		maKM = "-1";
//		prepaidCode = "";
//		// if (arrPromotionTypeBeans != null) {
//		// ArrayAdapter<String> adapter = new
//		// ArrayAdapter<String>(activity,
//		// android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
//		// spinner_kmai.setAdapter(adapter);
//		// }
//	}
//
//	// =========== webservice danh sach khuyen mai =================
//	public class GetPromotionByMap extends
//			AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetPromotionByMap(Context context) {
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
//		protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
//			return getPromotionTypeBeans(arg0[0], arg0[1], arg0[2]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
//			progress.dismiss();
//			edt_kmai.setText(getString(R.string.chonctkm1));
//			maKM = "-1";
//			prepaidCode = "";
//			if (errorCode.equals("0")) {
//				if (result.size() > 0) {
//					arrPromotionTypeBeans = result;
//					initInitListPromotion();
//
//				} else {
//					arrPromotionTypeBeans = new ArrayList<>();
//					PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
//					promotionTypeBeans.setCodeName(getResources().getString(
//							R.string.chonctkm1));
//					promotionTypeBeans.setPromProgramCode("-1");
//					arrPromotionTypeBeans.add(0, promotionTypeBeans);
//					PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
//					promotionTypeBeans1.setCodeName(getResources().getString(
//							R.string.selectMKM));
//					promotionTypeBeans1.setPromProgramCode("");
//					arrPromotionTypeBeans.add(1, promotionTypeBeans1);
//
//					// initInitListPromotionNotData();
//					// Dialog dialog = CommonActivity.createAlertDialog(
//					// activity,
//					// getResources().getString(R.string.checkmakm),
//					// getResources().getString(R.string.app_name));
//					// dialog.show();
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//
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
//
//				}
//			}
//		}
//
//		private ArrayList<PromotionTypeBeans> getPromotionTypeBeans(
//				String serviceType, String regType, String offerId) {
//			ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListPromotions");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListPromotions>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				rawData.append("<regType>").append(regType).append("</regType>");
//				rawData.append("<offerId>").append(offerId).append("</offerId>");
//				rawData.append("<province>").append("null").append("</province>");
//				rawData.append("<district>").append("null").append("</district>");
//				rawData.append("<precinct>").append("null").append("</precinct>");
//				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
//				rawData.append("<actionCode>" + "00" + "</actionCode>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListPromotions>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListPromotions");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//
//				// ============parse xml in android=========
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstPromotionType");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
//						String codeName = parse.getValue(e1, "codeName");
//						Log.d("codeName", codeName);
//						promotionTypeBeans.setCodeName(codeName);
//
//						String cycle = parse.getValue(e1, "cycle");
//						Log.d("cycle", cycle);
//						promotionTypeBeans.setCycle(cycle);
//
//						String monthAmount = parse.getValue(e1, "monthAmount");
//						Log.d("monthAmount", monthAmount);
//						promotionTypeBeans.setMonthAmount(monthAmount);
//
//						String monthCommitment = parse.getValue(e1,
//								"monthCommitment");
//						Log.d("monthCommitment", monthCommitment);
//						promotionTypeBeans.setMonthCommitment(monthCommitment);
//
//						String name = parse.getValue(e1, "name");
//						Log.d("name", name);
//						promotionTypeBeans.setName(name);
//
//						String promProgramCode = parse.getValue(e1,
//								"promProgramCode");
//						Log.d("promProgramCode", promProgramCode);
//						promotionTypeBeans.setPromProgramCode(promProgramCode);
//
//						String promType = parse.getValue(e1, "promType");
//						Log.d("promType", promType);
//						promotionTypeBeans.setPromType(promType);
//
//						String staDate = parse.getValue(e1, "staDate");
//						Log.d("staDate", staDate);
//						promotionTypeBeans.setStaDate(staDate);
//
//						String status = parse.getValue(e1, "status");
//						Log.d("status", status);
//						promotionTypeBeans.setStatus(status);
//
//						String telService = parse.getValue(e1, "telService");
//						Log.d("telService", telService);
//						promotionTypeBeans.setTelService(telService);
//
//						String type = parse.getValue(e1, "type");
//						Log.d("type", type);
//						promotionTypeBeans.setType(type);
//
//						String descrip = parse.getValue(e1, "description");
//						Log.d("descriponnnnn", descrip);
//						if (CommonActivity.isNullOrEmpty(promotionTypeBeans
//								.getDescription())) {
//							promotionTypeBeans.setDescription(descrip);
//						}
//						lisPromotionTypeBeans.add(promotionTypeBeans);
//					}
//				}
//			} catch (Exception ex) {
//				Log.d("getPromotionTypeBeans", ex.toString());
//			}
//
//			return lisPromotionTypeBeans;
//		}
//
//	}
//
//	// init spinner ListSubType
//    private void initListSubType() {
//		SubTypeBeans subTypeBean = new SubTypeBeans();
//		subTypeBean.setName(this.getResources().getString(
//				R.string.chonloaithuebao));
//		arrSubTypeBeans.add(0, subTypeBean);
//		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
//			ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                    android.R.layout.simple_dropdown_item_1line,
//                    android.R.id.text1);
//
//			for (SubTypeBeans subTyBeans : arrSubTypeBeans) {
//				adapter.add(subTyBeans.getName());
//			}
//			spinner_loaithuebao.setAdapter(adapter);
//		}
//	}
//
//	// init spinner ListSubTypeNotdate
//    private void initListSubTypeNotData() {
//		SubTypeBeans subTypeBeanNot = new SubTypeBeans();
//		subTypeBeanNot.setName(this.getResources().getString(
//				R.string.chonloaithuebao));
//		ArrayList<SubTypeBeans> arrSubTypeBeanss = new ArrayList<>();
//		arrSubTypeBeanss.add(0, subTypeBeanNot);
//		if (arrSubTypeBeanss != null && arrSubTypeBeanss.size() > 0) {
//
//			ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                    android.R.layout.simple_dropdown_item_1line,
//                    android.R.id.text1);
//
//			for (SubTypeBeans subTyBeans : arrSubTypeBeanss) {
//				adapter.add(subTyBeans.getName());
//			}
//			spinner_loaithuebao.setAdapter(adapter);
//		}
//	}
//
//	// ====================== Webserivce get list ========== DS
//	// loÃƒÆ’Ã‚Â¡Ãƒâ€šÃ‚ÂºÃƒâ€šÃ‚Â¡i
//	// thuÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Âª bao
//	private class GetListSubTypeAsyn extends
//			AsyncTask<String, Void, ArrayList<SubTypeBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListSubTypeAsyn(Context context) {
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
//		protected ArrayList<SubTypeBeans> doInBackground(String... arg0) {
//			return getListSubTypeBeans(arg0[0], arg0[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<SubTypeBeans> result) {
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result.size() > 0) {
//
//					arrSubTypeBeans = result;
//					initListSubType();
//				} else {
//					initListSubTypeNotData();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//
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
//
//				}
//			}
//
//		}
//
//		private ArrayList<SubTypeBeans> getListSubTypeBeans(String serviceType,
//				String productCode) {
//			ArrayList<SubTypeBeans> lisSubTypeBeans = new ArrayList<>();
//			String original = "";
//			try {
//
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListMobileSubType");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListMobileSubType>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				if (serviceType != null && !serviceType.isEmpty()) {
//					rawData.append("<serviceType>").append(serviceType);
//					rawData.append("</serviceType>");
//				}
//				if (productCode != null && !productCode.isEmpty()) {
//					rawData.append("<productCode>").append(productCode);
//					rawData.append("</productCode>");
//				}
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListMobileSubType>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListMobileSubType");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//
//				// ===============parse xml =========================
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodechild = doc.getElementsByTagName("lstSubType");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						SubTypeBeans subTypeBeans = new SubTypeBeans();
//						String name = parse.getValue(e1, "name");
//						Log.d("name", name);
//						subTypeBeans.setName(name);
//
//						String subGroupIdp = parse.getValue(e1, "subGroupId");
//						Log.d("subGroupIdp", subGroupIdp);
//						subTypeBeans.setSubGroupId(subGroupIdp);
//						String subType = parse.getValue(e1, "subType");
//						Log.d("subType", subType);
//						subTypeBeans.setSubType(subType);
//						// String telService = parse.getValue(e1, "telService");
//						// Log.d("telService", telService);
//						// subTypeBeans.setTelService(telService);
//						// String updateDate = parse.getValue(e1, "updateDate");
//						// Log.d("updateDate", updateDate);
//						// subTypeBeans.setUpdateDate(updateDate);
//						// String updateUser = parse.getValue(e1, "updateUser");
//						// Log.d("updateUser", updateUser);
//						// subTypeBeans.setUpdateUser(updateUser);
//
//						lisSubTypeBeans.add(subTypeBeans);
//					}
//				}
//
//			} catch (Exception e) {
//				Log.d("getListSubTypeBeans", e.toString());
//			}
//			return lisSubTypeBeans;
//		}
//	}
//
//	// get contract
//	private class GetConTractAsyn extends
//			AsyncTask<String, Void, ArrayList<ConTractBean>> {
//
//		ProgressDialog progress;
//		private Context context = null;
//		String errorCode = "";
//		String description = "";
//
//		public GetConTractAsyn(Context context) {
//			this.context = context;
//			// this.progress = new ProgressDialog(this.context);
//			// // check font
//			// this.progress.setCancelable(false);
//			// this.progress.setMessage(context.getResources().getString(
//			// R.string.getdataing));
//			// if (!this.progress.isShowing()) {
//			// this.progress.show();
//			// }
//			prb_contract.setVisibility(View.VISIBLE);
//		}
//
//		@Override
//		protected ArrayList<ConTractBean> doInBackground(String... params) {
//			return sendRequestGetLisContract(params[0], params[1], params[2]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<ConTractBean> result) {
//			prb_contract.setVisibility(View.GONE);
//			// progress.dismiss();
//			if (errorCode.equals("0")) {
//
//				if (result.size() > 0) {
//					arrTractBeans = result;
//					initContract();
//				} else {
//					if (arrTractBeans != null && arrTractBeans.size() > 0) {
//						arrTractBeans.clear();
//					}
//					initContract();
//					// initHTHMNotData();
//					// Dialog dialog = CommonActivity.createAlertDialog(
//					// activity, getResources()
//					// .getString(R.string.notcontract),
//					// getResources().getString(R.string.app_name));
//					// dialog.show();
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//
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
//
//				}
//			}
//		}
//
//		private ArrayList<ConTractBean> sendRequestGetLisContract(String cusId,
//				String idNo, String busPermitNo) {
//			ArrayList<ConTractBean> lstTractBeans = new ArrayList<>();
//			String original = "";
//			try {
//
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_findContractOld");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:findContractOld>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<custId>").append(cusId);
//				rawData.append("</custId>");
//
//				rawData.append("<idNo>").append(idNo);
//				rawData.append("</idNo>");
//				rawData.append("<busPermitNo>").append(busPermitNo);
//				rawData.append("</busPermitNo>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:findContractOld>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity, "mbccs_findContractOld");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.d("original", original);
//				// ====== parse xml ===================
//				GetContractHandler handler = (GetContractHandler) CommonActivity
//						.parseXMLHandler(new GetContractHandler(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				lstTractBeans = handler.getLstData();
//
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//
//			return lstTractBeans;
//		}
//	}
//
//	// ===========webservice danh sach hinh thuc hoa mang tra sau
//	// ===============
//
//	public class GetHTHMAsyn extends
//			AsyncTask<String, Void, ArrayList<HTHMBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetHTHMAsyn(Context context) {
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
//		protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
//			return getListHTHM(arg0[0], arg0[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<HTHMBeans> result) {
//			// get hinh thuc hoa mang
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//
//				if (result.size() > 0) {
//					arrHthmBeans = result;
//					edt_hthm.setText(getString(R.string.chonhthm));
//					// initHTHM();
//				} else {
//					edt_hthm.setText(getString(R.string.chonhthm));
//					// initHTHMNotData();
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.noththm),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//
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
//
//				}
//			}
//
//		}
//
//		// ==input String offerId, String serviceType
//		private ArrayList<HTHMBeans> getListHTHM(String serviceType,
//				String offerId) {
//			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListRegTypePos");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListRegTypePos>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//				rawData.append("<actionCode>" + "00");
//				rawData.append("</actionCode>");
//
//				rawData.append("<offerId>").append(offerId);
//				rawData.append("</offerId>");
//				rawData.append("<province>").append("null");
//				rawData.append("</province>");
//				rawData.append("<district>").append("null");
//				rawData.append("</district>");
//				rawData.append("<precinct>").append("null");
//				rawData.append("</precinct>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListRegTypePos>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListRegTypePos");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.d("original", original);
//				// ====== parse xml ===================
//
//				HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
//						.parseXMLHandler(new HTHMMobileHandler(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				lstHthmBeans = handler.getLstData();
//
//				// ====== parse xml ===================
//			} catch (Exception e) {
//				Log.d("getListHTHM", e.toString());
//			}
//
//			return lstHthmBeans;
//		}
//
//	}
//
//	// ===========webservice danh sach hinh thuc hoa mang tra truoc
//	// ===============
//
//	public class GetHTHMPREAsyn extends
//			AsyncTask<String, Void, ArrayList<HTHMBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetHTHMPREAsyn(Context context) {
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
//		protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
//			return getListPREHTHM(arg0[0], arg0[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<HTHMBeans> result) {
//
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//
//				if (result.size() > 0) {
//					arrHthmBeans = result;
//					initHTHM();
//				} else {
//					initHTHMNotData();
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.noththm),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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
//		// ==input String offerId, String serviceType
//		private ArrayList<HTHMBeans> getListPREHTHM(String serviceType,
//				String offerId) {
//			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListRegTypePre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListRegTypePre>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//				rawData.append("<actionCode>" + "00");
//				rawData.append("</actionCode>");
//
//				rawData.append("<offerId>").append(offerId);
//				rawData.append("</offerId>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListRegTypePre>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListRegTypePre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.d("original", original);
//				// ====== parse xml ===================
//
//				HTHMPREMobileHandler handler = (HTHMPREMobileHandler) CommonActivity
//						.parseXMLHandler(new HTHMPREMobileHandler(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				lstHthmBeans = handler.getLstData();
//				// ====== parse xml ===================
//			} catch (Exception e) {
//				Log.d("getListHTHM", e.toString());
//			}
//
//			return lstHthmBeans;
//		}
//
//	}
//
//	// ======= get list PakageCharge getListProductByTelecomService
//	public class GetListPakageAsyn extends
//			AsyncTask<String, Void, ArrayList<PakageChargeBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListPakageAsyn(Context context) {
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
//		protected ArrayList<PakageChargeBeans> doInBackground(String... params) {
//			return sendRequestGetListService(params[0], params[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<PakageChargeBeans> result) {
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					arrPakageChargeBeans = result;
//					pakageBundeBean.setArrChargeBeans(result);
//					Intent intent = new Intent(activity,
//							FragmentSearchPakageMobile.class);
//					Bundle mBundle = new Bundle();
//					mBundle.putSerializable("PakageKey", pakageBundeBean);
//					intent.putExtras(mBundle);
//					startActivityForResult(intent, 3333);
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getResources().getString(R.string.notgoicuoc),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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
//		// =====method get list service ========================
//		public ArrayList<PakageChargeBeans> sendRequestGetListService(
//				String telecomserviceId, String dbType) {
//			String original = null;
//			FileInputStream in = null;
//			ArrayList<PakageChargeBeans> lisPakageChargeBeans = new ArrayList<>();
//			try {
//				BCCSGateway input = new BCCSGateway();
//				// input.addValidateGateway("username", Constant.BCCSGW_USER);
//				// input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				// input.addValidateGateway("wscode",
//				// "mbccs_getListProductByTelecomService");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");
//
//				rawData.append("<soapenv:Header/>");
//				rawData.append("<soapenv:Body>");
//
//				rawData.append("<ws:getListMobileProductByTelecomService>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				rawData.append("<telecomServiceId>").append(telecomserviceId);
//				rawData.append("</telecomServiceId>");
//				rawData.append("<dbType>").append(dbType);
//				rawData.append("</dbType>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListMobileProductByTelecomService>");
//				rawData.append("</soapenv:Body>");
//				rawData.append("</soapenv:Envelope>");
//
//				String envelope = rawData.toString();
//				Log.e("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//
//				String fileName = input.sendRequestWriteToFile(envelope,
//						Constant.WS_PAKAGE_DATA, Constant.PAKAGE_FILE_NAME);
//				if (fileName != null) {
//
//					try {
//
//						File file = new File(fileName);
////						if (!file.mkdirs()) {
////							file.createNewFile();
////						}
//						in = new FileInputStream(file);
//						PakageHanlder handler = (PakageHanlder) CommonActivity
//								.parseXMLHandler(new PakageHanlder(),
//										new InputSource(in));
//						// file.delete();
//						if (handler != null) {
//							if (handler.getItem().getToken() != null
//									&& !handler.getItem().getToken().isEmpty()) {
//								Session.setToken(handler.getItem().getToken());
//							}
//
//							if (handler.getItem().getErrorCode() != null) {
//								errorCode = handler.getItem().getErrorCode();
//							}
//							if (handler.getItem().getDescription() != null) {
//								description = handler.getItem()
//										.getDescription();
//							}
//							lisPakageChargeBeans = handler.getLstData();
//						}
//
//					} catch (Exception e) {
//						Log.e("Exception", e.toString(), e);
//					} finally {
//						if (in != null) {
//							try {
//								in.close();
//							} catch (IOException e) {
//								Log.e("Exception", e.toString(), e);
//							}
//						}
//					}
//				}
//
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//
//			return lisPakageChargeBeans;
//		}
//
//	}
//
//	private class AsynZipFile extends
//			AsyncTask<String, Void, ArrayList<FileObj>> {
//
//		private final HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
//		private final Context mContext;
//		private ProgressDialog progress;
//		private String errorCode = "";
//		private String description = "";
//		private ArrayList<String> lstFilePath = new ArrayList<>();
//		private String actionAuditId = "";
//		private String balance = "";
//
//		public AsynZipFile(Context context,
//				HashMap<String, ArrayList<FileObj>> hasMapFile,
//				ArrayList<String> mlstFilePath, String actionaudit,
//				String balance) {
//			this.mContext = context;
//			this.mHashMapFileObj = hasMapFile;
//			this.lstFilePath = mlstFilePath;
//			this.actionAuditId = actionaudit;
//			this.balance = balance;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			this.progress = new ProgressDialog(mContext);
//			// check font
//			this.progress.setMessage(mContext.getResources().getString(
//					R.string.progress_zip));
//			this.progress.setCancelable(false);
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<FileObj> doInBackground(String... arg0) {
//			ArrayList<FileObj> arrFileBackUp1 = null;
//			try {
//				arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext,
//						mHashMapFileObj, lstFilePath);
//				errorCode = "0";
//				return arrFileBackUp1;
//			} catch (Exception e) {
//				errorCode = "1";
//				description = "Error when zip file: " + e.toString();
//				return arrFileBackUp1;
//			}
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<FileObj> result) {
//			progress.dismiss();
//			if ("0".equals(errorCode)) {
//				if (result != null && !result.isEmpty()) {
//					for (FileObj fileObj : result) {
//						fileObj.setActionCode("01");
//						fileObj.setReasonId(reasonId);
//						fileObj.setIsdn(isdn);
//						fileObj.setActionAudit(actionAuditId);
//						fileObj.setPageSize(0 + "");
//						fileObj.setStatus(0);
//					}
//
//					String noticeSuccess = "";
//					if (CommonActivity.isNullOrEmpty(balance)) {
//						noticeSuccess = getActivity().getString(
//								R.string.connectSuccess)
//								+ "\n";
//					} else {
//						noticeSuccess = balance;
//
//						SharedPreferences preferences = getActivity()
//								.getSharedPreferences(Define.PRE_NAME,
//										getActivity().MODE_MULTI_PROCESS);
//						SharedPreferences.Editor editor = preferences.edit();
//						editor.putString(Define.KEY_NOTICE_SERVICE,
//								noticeSuccess);
//						editor.commit();
//
//					}
//
//					if ("M".equals(serviceType)) {
//						AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(
//								mContext, result, onclickBackScreen,
//								noticeSuccess, txtisdn.getText().toString()
//										.trim(), subType);
//						uploadImageAsy.execute();
//					} else {
//						AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(
//								mContext, result, onclickBackScreen,
//								noticeSuccess);
//						uploadImageAsy.execute();
//					}
//
//				}
//			} else {
//				if (result != null && result.size() > 0) {
//					for (FileObj fileObj : result) {
//						File tmp = new File(fileObj.getPath());
//						tmp.delete();
//					}
//				}
//
//				CommonActivity.createAlertDialog(activity, description,
//						activity.getString(R.string.app_name)).show();
//			}
//
//		}
//	}
//
//	// ========== dau noi tra truoc==========
//
//	private class SubConnectPreAsyn extends AsyncTask<String, String, String> {
//
//		private ProgressDialog progress;
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		final ArrayList<String> lstFilePath = new ArrayList<>();
//		private String balance = "";
//
//		public SubConnectPreAsyn(Context context) {
//			this.context = context;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setMessage(context.getResources().getString(
//					R.string.progress_SubConnect));
//			this.progress.setCancelable(false);
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		// @Override
//		// protected void onProgressUpdate(String... values) {
//		// super.onProgressUpdate(values);
//		// progress.setMessage(String.valueOf(values[0]));
//		// Log.i("makemachine",
//		// "onProgressUpdate(): " + String.valueOf(values[0]));
//		// }
//
//		@Override
//		protected String doInBackground(String... params) {
//			try {
//				// publishProgress(context.getResources().getString(
//				// R.string.subconnecting));
//
//				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
//
//					File folder = new File(
//							Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
//					if (!folder.exists()) {
//						folder.mkdir();
//					}
//					Log.d("Log",
//							"Folder zip file create: "
//									+ folder.getAbsolutePath());
//					for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
//							.entrySet()) {
//						ArrayList<FileObj> listFileObjs = entry.getValue();
//						String zipFilePath = "";
//						if (listFileObjs.size() > 1) {
//							String spinnerCode = "";
//							for (FileObj fileObj : listFileObjs) {
//								spinnerCode = fileObj.getCodeSpinner();
//							}
//							zipFilePath = folder.getPath() + File.separator
//									+ System.currentTimeMillis() + "_"
//									+ spinnerCode + ".zip";
//							lstFilePath.add(zipFilePath);
//						} else if (listFileObjs.size() == 1) {
//							zipFilePath = folder.getPath() + File.separator
//									+ System.currentTimeMillis() + "_"
//									+ listFileObjs.get(0).getCodeSpinner()
//									+ ".jpg";
//							lstFilePath.add(zipFilePath);
//						}
//					}
//				}
//                return subConectPre(lstFilePath);
//			} catch (Exception e) {
//				errorCode = "1";
//				description = "Error when zip file: " + e.toString();
//				return "1";
//			}
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			progress.dismiss();
//
//
//			if (errorCode.equals("0")) {
//				// //lnButton.setVisibility(View.GONE);
//				// //CommonActivity.createAlertDialog(activity,
//				// //getString(R.string.daunoithanhcong),
//				// //getString(R.string.app_name), onclickBackScreen).show();
//				//
//				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
//				// for (FileObj fileObj : arrFileBackUp) {
//				// fileObj.setActionCode("01"); // Ä‘áº¥u ná»‘i : 00 Ä‘Äƒng
//				// // kÃ½
//				// // thÃ´ng tin 04
//				// fileObj.setReasonId(reasonId);
//				// fileObj.setIsdn(isdn);
//				// fileObj.setActionAudit(description);
//				// fileObj.setPageSize(0 + "");
//				// fileObj.setStatus(0);
//				// }
//				// // FileUtils.insertFileBackUpToDataBase(arrFileBackUp,
//				// // activity.getApplicationContext());
//				//
//				// AsyncTaskUpdateImageOfline uploadImageAsy = new
//				// AsyncTaskUpdateImageOfline(
//				// context, arrFileBackUp, onclickBackScreen,
//				// getString(R.string.connectSuccess) + "\n");
//				// uploadImageAsy.execute();
//				// }
//
//				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
//					AsynZipFile asynZipFile = new AsynZipFile(activity,
//							hashmapFileObj, lstFilePath, description, balance);
//					asynZipFile.execute();
//				}
//
//			} else {
//				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
//				// for (FileObj fileObj : arrFileBackUp) {
//				// File tmp = new File(fileObj.getPath());
//				// tmp.delete();
//				// }
//				// }
//				lnButton.setVisibility(View.VISIBLE);
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					// Dialog dialog =
//					// CommonActivity.createAlertDialog((Activity) context,
//					// description,
//					// context.getResources().getString(R.string.app_name),
//					// moveLogInAct);
//					// dialog.show();
//					LoginDialog dialog = new LoginDialog(getActivity(),
//							";connect_mobile;");
//
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
//		private String subConectPre(ArrayList<String> mlstFilePath) {
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_subscriberConnect");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:subscriberConnect>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				HashMap<String, String> param = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				// List<FileObj> arrFileObjs = new ArrayList<FileObj>();
//				// arrFileObjs.addAll(hashmapFileObj.values());
//				for (String fileObj : mlstFilePath) {
//					rawData.append("<lstFilePath>");
//					rawData.append(fileObj);
//					rawData.append("</lstFilePath>");
//				}
//
//				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<>(
//                        mapListRecordPrepaid.values());
//				for (int j = 0; j < arrayOfArrayList.size(); j++) {
//					ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList
//							.get(j);
//					View rowView = lvUploadImage.getChildAt(j);
//					ViewHolder h = (ViewHolder) rowView.getTag();
//					if (h != null) {
//						int indexSelection = h.spUploadImage
//								.getSelectedItemPosition();
//						RecordPrepaid recordPrepaid = listRecordPrepaid
//								.get(indexSelection);
//						rawData.append("<lstRecordName>");
//						rawData.append(recordPrepaid.getName());
//						rawData.append("</lstRecordName>");
//						rawData.append("<lstRecordCode>");
//						rawData.append(recordPrepaid.getCode());
//						rawData.append("</lstRecordCode>");
//					}
//				}
//
//				rawData.append("<fileContent>");
//				rawData.append("");
//				rawData.append("</fileContent>");
//
//				if ("1".equalsIgnoreCase(subType)) {
//					rawData.append("<isPospaid>" + false + "</isPospaid>");
//				} else {
//					rawData.append("<isPospaid>" + true + "</isPospaid>");
//				}
//
//				rawData.append("<isConnect>");
//				rawData.append(true);
//				rawData.append("</isConnect>");
//
//				rawData.append("<shopCode>").append(txtidcuahang.getText().toString());
//				rawData.append("</shopCode>");
//				rawData.append("<staffCode>").append(txtmanv.getText().toString());
//				rawData.append("</staffCode>");
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//				rawData.append("<subscriberConnectedBO>");
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//				rawData.append("<noSendFile>" + "NO_SEND_FILE");
//				rawData.append("</noSendFile>");
//				// ========customer ===============
//				rawData.append("<customer>");
//				// TuanTD7
//				if (custommerByIdNoBean.getTin() != null
//						&& !custommerByIdNoBean.getTin().isEmpty()) {
//					rawData.append("<tin>").append(custommerByIdNoBean.getTin());
//					rawData.append("</tin>");
//				}
//
//				if (custommerByIdNoBean.getBusPermitNo() == null
//						|| custommerByIdNoBean.getBusPermitNo().isEmpty()) {
//					rawData.append("<sex>").append(custommerByIdNoBean.getSex());
//					rawData.append("</sex>");
//				}
//				rawData.append("<name>"
//						+ StringUtils.escapeHtml(custommerByIdNoBean
//								.getNameCustomer()));
//				rawData.append("</name>");
//				rawData.append("<address>"
//						+ StringUtils.escapeHtml(custommerByIdNoBean.getAddreseCus()));
//				rawData.append("</address>");
//				rawData.append("<areaCode>").append(custommerByIdNoBean.getAreaCode());
//				rawData.append("</areaCode>");
//				if (custommerByIdNoBean.getCustId() != null
//						&& !custommerByIdNoBean.getCustId().isEmpty()) {
//					rawData.append("<strBirthDate>").append(custommerByIdNoBean.getBirthdayCus());
//					rawData.append("</strBirthDate>");
//				} else {
//					rawData.append("<strBirthDate>").append(custommerByIdNoBean.getBirthdayCusNew());
//					rawData.append("</strBirthDate>");
//				}
//				if (custommerByIdNoBean.getStrIdExpire() != null
//						&& !custommerByIdNoBean.getStrIdExpire().isEmpty()) {
//					rawData.append("<strIdExpireDate>").append(custommerByIdNoBean.getStrIdExpire());
//					rawData.append("</strIdExpireDate>");
//				}
//				rawData.append("<busType>").append(custommerByIdNoBean.getCusType());
//				rawData.append("</busType>");
//				rawData.append("<custId>").append(custommerByIdNoBean.getCustId());
//				rawData.append("</custId>");
//				rawData.append("<province>").append(custommerByIdNoBean.getProvince());
//				rawData.append("</province>");
//				rawData.append("<district>").append(custommerByIdNoBean.getDistrict());
//				rawData.append("</district>");
//				rawData.append("<precinct>").append(custommerByIdNoBean.getPrecint());
//				rawData.append("</precinct>");
//
//				rawData.append("<streetBlock>").append(custommerByIdNoBean.getStreet_block());
//				rawData.append("</streetBlock>");
//				rawData.append("<streetName>" + StringUtils.escapeHtml(custommerByIdNoBean.getStreet()));
//				rawData.append("</streetName>");
//				rawData.append("<home>" + StringUtils.escapeHtml(custommerByIdNoBean.getHome()));
//				rawData.append("</home>");
//
//				// rawData.append("<areaCode>" +
//				// custommerByIdNoBean.getProvince()+custommerByIdNoBean.getDistrict()+custommerByIdNoBean.getPrecint());
//				// rawData.append("</areaCode>");
//
//				// if (custommerByIdNoBean.getIdNo() != null
//				// && !custommerByIdNoBean.getIdNo().isEmpty()) {
//				// rawData.append("<idNo>" + custommerByIdNoBean.getIdNo());
//				// rawData.append("</idNo>");
//				// }
//
//				rawData.append("<strIdIssueDate>").append(custommerByIdNoBean.getIdIssueDate());
//				rawData.append("</strIdIssueDate>");
//				rawData.append("<idIssuePlace>").append(StringUtils.escapeHtml(custommerByIdNoBean
//								.getIdIssuePlace()));
//				rawData.append("</idIssuePlace>");
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
//				if (busPermitNo != null && !busPermitNo.isEmpty()) {
//					rawData.append("<busPermitNo>").append(busPermitNo);
//					rawData.append("</busPermitNo>");
//				} else {
//					rawData.append("<idNo>").append(custommerByIdNoBean.getIdNo());
//					rawData.append("</idNo>");
//				}
//
//				CustomerAttribute customerAttribute = custommerByIdNoBean
//						.getCustomerAttribute();
//				if (customerAttribute != null) {
//					// rawData.append("<customerAttribute>");
//
//					rawData.append("<customerAttributeName>"
//							+ StringUtils.escapeHtml(customerAttribute
//									.getName()) + "</customerAttributeName>");
//					rawData.append("<customerAttributeBirthDate>").append(customerAttribute.getBirthDate()).append("</customerAttributeBirthDate>");
//					rawData.append("<customerAttributeIdType>").append(customerAttribute.getIdType()).append("</customerAttributeIdType>");
//					rawData.append("<customerAttributeIdNo>").append(customerAttribute.getIdNo()).append("</customerAttributeIdNo>");
//					rawData.append("<customerAttributeIssuePlace>").append(StringUtils.escapeHtml(customerAttribute
//									.getIssuePlace())).append("</customerAttributeIssuePlace>");
//					rawData.append("<customerAttributeIssueDate>").append(customerAttribute.getIssueDate()).append("</customerAttributeIssueDate>");
//
//					// rawData.append("</customerAttribute>");
//					// them linh tinh thue bao dai dien vao day
//				}
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getNotes())) {
//					rawData.append("<notes>" +  StringUtils.escapeHtml(custommerByIdNoBean.getNotes()));
//					rawData.append("</notes>");
//				}
//
//				rawData.append("</customer>");
//
//				// ======= subMb =====thong tin thue bao
//
//				if (serviceType.equalsIgnoreCase("H")) {
//					rawData.append("<subHp>");
//				} else {
//					rawData.append("<subMb>");
//				}
//
//				rawData.append("<isdn>").append(isdn);
//				rawData.append("</isdn>");
//				rawData.append("<serial>").append(txtserial.getText().toString());
//				rawData.append("</serial>");
//				rawData.append("<productCode>").append(productCode);
//				rawData.append("</productCode>");
//				rawData.append("<offerId>").append(offerId);
//				rawData.append("</offerId>");
//				rawData.append("<province>").append(province);
//				rawData.append("</province>");
//				rawData.append("<district>").append(district);
//				rawData.append("</district>");
//				rawData.append("<precinct>").append(precinct);
//				rawData.append("</precinct>");
//				rawData.append("<regType>").append(hthm);
//				rawData.append("</regType>");
//
//				rawData.append("<regReasonId>").append(reasonId);
//				rawData.append("</regReasonId>");
//
//				rawData.append("<addressCode>").append(province).append(district).append(precinct);
//				rawData.append("</addressCode>");
//				try {
//					GetAreaDal dal = new GetAreaDal(activity);
//					dal.open();
//					String fulladdress = dal.getfulladddress(province
//							+ district + precinct);
//					rawData.append("<address>" + StringUtils.escapeHtml(fulladdress));
//					rawData.append("</address>");
//					dal.close();
//				} catch (Exception e) {
//					Log.e("Exception", e.toString(), e);
//				}
//
//				if (serviceType.equalsIgnoreCase("H")) {
//					rawData.append("</subHp>");
//				} else {
//					rawData.append("</subMb>");
//				}
//
//				// =============raw data add hang hoa
//				ArrayList<StockTypeDetailBeans> arrApStockModelBeanBOs = new ArrayList<>();
//				arrApStockModelBeanBOs.addAll(mapSubStockModelRelReq.values());
//				if (arrApStockModelBeanBOs.size() > 0) {
//					for (StockTypeDetailBeans objAPStockModelBeanBO : arrApStockModelBeanBOs) {
//						HashMap<String, String> rawDataItem = new HashMap<>();
//						rawDataItem.put("stockModelId",
//								objAPStockModelBeanBO.getStockModelId() + "");
//						rawDataItem.put("stockModelName",
//								objAPStockModelBeanBO.getStockModelName());
//						rawDataItem.put("serial",
//								objAPStockModelBeanBO.getSerial());
//						rawDataItem.put("stockTypeId",
//								objAPStockModelBeanBO.getStockTypeId() + "");
//						param.put("lstSubStockModelRel",
//								input.buildXML(rawDataItem));
//						rawData.append(input.buildXML(param));
//					}
//				}
//
//				// form specical paper
//
//				if (Constant.OFFERID_TSV_SPEC.contains(offerId)) {
//					rawData.append("<proSpecMgrFormPre>");
//					rawData.append("<idNo>").append(custommerByIdNoBean.getIdNo()).append("</idNo>");
//					rawData.append("<departmentCode>").append(mCodeDonVi).append("</departmentCode>");
//					rawData.append("<endDatetime>").append(mNgayKetThuc).append("</endDatetime>");
//					rawData.append("<nationCode>" + "VIE" + "</nationCode>");
//					rawData.append("<objectCode>").append(mCodeDoiTuong).append("</objectCode>");
//					rawData.append("<orderNumber>").append(edtMaGiayToDacBiet.getText().toString()).append("</orderNumber>");
//					rawData.append("<startDatetime>").append(mNgayBatDau).append("</startDatetime>");
//					rawData.append("</proSpecMgrFormPre>");
//				}
//
//				rawData.append("</subscriberConnectedBO>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:subscriberConnect>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_subscriberConnect");
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
//					balance = parse.getValue(e2, "balance");
//				}
//
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//			return errorCode;
//
//		}
//
//	}
//
//	private final OnClickListener onclickBackScreen = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			activity.finish();
//		}
//	};
//
//	// ========== dau noi tra truoc==========
//
//	private class SubConnectPosAsyn extends AsyncTask<String, String, String> {
//
//		ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//		private final Contract contract;
//		final ArrayList<String> lstFilePath = new ArrayList<>();
//
//		public SubConnectPosAsyn(Context context, Contract contract) {
//			this.context = context;
//			this.contract = contract;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setMessage(context.getResources().getString(
//					R.string.progress_SubConnect));
//			this.progress.setCancelable(false);
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		// @Override
//		// protected void onProgressUpdate(String... values) {
//		// super.onProgressUpdate(values);
//		// progress.setMessage(String.valueOf(values[0]));
//		// Log.i("makemachine",
//		// "onProgressUpdate(): " + String.valueOf(values[0]));
//		// }
//
//		@Override
//		protected String doInBackground(String... params) {
//			try {
//				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
//
//					File folder = new File(
//							Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
//					if (!folder.exists()) {
//						folder.mkdir();
//					}
//					Log.d("Log",
//							"Folder zip file create: "
//									+ folder.getAbsolutePath());
//					for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
//							.entrySet()) {
//						ArrayList<FileObj> listFileObjs = entry.getValue();
//						String zipFilePath = "";
//						if (listFileObjs.size() > 1) {
//							String spinnerCode = "";
//							for (FileObj fileObj : listFileObjs) {
//								spinnerCode = fileObj.getCodeSpinner();
//							}
//							zipFilePath = folder.getPath() + File.separator
//									+ System.currentTimeMillis() + "_"
//									+ spinnerCode + ".zip";
//							lstFilePath.add(zipFilePath);
//						} else if (listFileObjs.size() == 1) {
//							zipFilePath = folder.getPath() + File.separator
//									+ System.currentTimeMillis() + "_"
//									+ listFileObjs.get(0).getCodeSpinner()
//									+ ".jpg";
//							lstFilePath.add(zipFilePath);
//						}
//					}
//				}
//                return subConectPos(lstFilePath);
//			} catch (Exception e) {
//				errorCode = "1";
//				description = "Error when zip file: " + e.toString();
//				return "1";
//			}
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			progress.dismiss();
//
//			if (errorCode.equals("0")) {
//				// lnButton.setVisibility(View.GONE);
//				// // CommonActivity.createAlertDialog(activity,
//				// // getString(R.string.daunoithanhcong),
//				// // getString(R.string.app_name), onclickBackScreen).show();
//				//
//				// // for (java.util.Map.Entry<String, ArrayList<FileObj>> entry
//				// :
//				// // hashmapFileObj
//				// // .entrySet()) {
//				// // ArrayList<FileObj> listFileObjs = entry.getValue();
//				// //
//				// // for (int i = 0; i < listFileObjs.size(); i++) {
//				// // FileObj fileObj = listFileObjs.get(i);
//				// // fileObj.setActionCode("02"); // Ã„â€˜Ã¡ÂºÂ¥u nÃ¡Â»â€˜i :
//				// // // 00 Ã„â€˜Ã„Æ’ng
//				// // // kÃƒÂ½
//				// // // thÃƒÂ´ng tin 04
//				// // fileObj.setReasonId(reasonId);
//				// // fileObj.setIsdn(isdn);
//				// // fileObj.setActionAudit(description);
//				// // fileObj.setPageIndex(i + "");
//				// // fileObj.setPageSize(listFileObjs.size() + "");
//				// // fileObj.setStatus(0);
//				// // }
//				// // }
//				// // FileUtils.backUpImageFile(hashmapFileObj,
//				// // activity.getApplicationContext());
//				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
//				// for (FileObj fileObj2 : arrFileBackUp) {
//				// fileObj2.setActionCode("03"); // Ä‘áº¥u ná»‘i : 00
//				// fileObj2.setReasonId(reasonId);
//				// fileObj2.setIsdn(isdn);
//				// fileObj2.setActionAudit(description);
//				// fileObj2.setPageSize(0 + "");
//				// }
//				// AsyncTaskUpdateImageOfline uploadImageAsy = new
//				// AsyncTaskUpdateImageOfline(
//				// context, arrFileBackUp, onclickBackScreen,
//				// getString(R.string.connectSuccess) + "\n");
//				// uploadImageAsy.execute();
//				// }
//				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
//					AsynZipFile asynZipFile = new AsynZipFile(activity,
//							hashmapFileObj, lstFilePath, description, "");
//					asynZipFile.execute();
//				}
//			} else {
//				lnButton.setVisibility(View.VISIBLE);
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					LoginDialog dialog = new LoginDialog(getActivity(),
//							";connect_mobile;");
//
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
//		private String subConectPos(ArrayList<String> mlstFilePath) {
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_subscriberConnectSmartphone");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:subscriberConnectSmartphone>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				HashMap<String, String> param = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
//				// for (FileObj fileObj : arrFileBackUp) {
//				// rawData.append("<lstFilePath>");
//				// rawData.append(fileObj.getPath());
//				// rawData.append("</lstFilePath>");
//				// }
//				// }
//
//				for (String fileObj : mlstFilePath) {
//					rawData.append("<lstFilePath>");
//					rawData.append(fileObj);
//					rawData.append("</lstFilePath>");
//				}
//
//				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<>(
//                        mapListRecordPrepaid.values());
//				for (int j = 0; j < arrayOfArrayList.size(); j++) {
//					ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList
//							.get(j);
//					View rowView = lvUploadImage.getChildAt(j);
//					ViewHolder h = (ViewHolder) rowView.getTag();
//					if (h != null) {
//						int indexSelection = h.spUploadImage
//								.getSelectedItemPosition();
//						RecordPrepaid recordPrepaid = listRecordPrepaid
//								.get(indexSelection);
//						rawData.append("<lstRecordName>");
//						rawData.append(recordPrepaid.getName());
//						rawData.append("</lstRecordName>");
//						rawData.append("<lstRecordCode>");
//						rawData.append(recordPrepaid.getCode());
//						rawData.append("</lstRecordCode>");
//					}
//				}
//
//				rawData.append("<fileContent>");
//				rawData.append("");
//				rawData.append("</fileContent>");
//
//				if ("1".equalsIgnoreCase(subType)) {
//					rawData.append("<isPospaid>" + false + "</isPospaid>");
//				} else {
//					rawData.append("<isPospaid>" + true + "</isPospaid>");
//				}
//
//				rawData.append("<isConnect>");
//				rawData.append(true);
//				rawData.append("</isConnect>");
//				rawData.append("<shopCode>").append(txtidcuahang.getText().toString());
//				rawData.append("</shopCode>");
//				rawData.append("<staffCode>").append(txtmanv.getText().toString());
//				rawData.append("</staffCode>");
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//
//				rawData.append("<smartphoneInputInfoSub>");
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//
//				rawData.append("<noSendFile>" + "NO_SEND_FILE");
//				rawData.append("</noSendFile>");
//				// ========contract ==============
//				rawData.append("<contract>");
//				if (contractNo != null && !contractNo.isEmpty()) {
//					// hop dong cu
//					rawData.append("<contractId>").append(contractId);
//					rawData.append("</contractId>");
//					rawData.append("<contractNo>").append(contractNo);
//					rawData.append("</contractNo>");
//
//					// them phu luc hop dong linh tinh vao day
//
//					if (contract != null) {
//						ContractOffer contractOffer = contract
//								.getContractOffer();
//						if (contractOffer != null) {
//							rawData.append("<contractOffer>");
//
//							if (contractOffer.getContractOfferId() != null) {
//								rawData.append("<contractOfferId>").append(contractOffer.getContractOfferId()).append("</contractOfferId>");
//							} else {
//								rawData.append("<represent>")
//										.append(StringUtils.escapeHtml(contractOffer.getRepresent()))
//										.append("</represent>");
//								rawData.append("<representPosition>").append(contractOffer.getRepresentPosition()).append("</representPosition>");
//								rawData.append("<strSignDatetime>").append(contractOffer.getSignDatetime()).append("</strSignDatetime>");
//								rawData.append("<idType>").append(contractOffer.getIdType()).append("</idType>");
//								rawData.append("<idNo>").append(contractOffer.getIdNo()).append("</idNo>");
//								rawData.append("<strIssueDatetime>").append(contractOffer.getIssueDatetime()).append("</strIssueDatetime>");
//								rawData.append("<issuePlace>").append(StringUtils.escapeHtml(contractOffer
//                                        .getIssuePlace())).append("</issuePlace>");
//							}
//							rawData.append("</contractOffer>");
//						} else {
//							Log.d(Constant.TAG,
//									"SubConnectPosAsyn subConectPos Khach hang ca nhan, hop dong cu, khong can phai nhap them phu luc hop dong");
//						}
//					}
//
//				} else {
//					// them Contract linh tinh vao day
//					if (contract != null) {
//						// hop dong moi edt_contractNo; // so hop dong // NULL
//						rawData.append("<payer>"
//								+ StringUtils.escapeHtml(contract.getPayer())
//								+ "</payer>"); // nguoi
//												// thanh
//												// toan
//						rawData.append("<contractType>").append(contract.getContractTypeCode()).append("</contractType>"); // loai
//						// hop
//						// dong
//						rawData.append("<strSignDate>").append(contract.getSignDate()).append("</strSignDate>"); // ngay
//														// ky
//						rawData.append("<strEffectDate>").append(contract.getEffectDate()).append("</strEffectDate>"); // ngay
//																					// hieu
//																					// luc
//						rawData.append("<strEndEffectDate>").append(contract.getEndEffectDate()).append("</strEndEffectDate>"); // ngay
//															// het
//															// han
//															// hop
//															// dong
//						rawData.append("<payMethodCode>").append(contract.getPayMethodCode()).append("</payMethodCode>"); // hinh
//														// thuc
//														// thanh
//														// toan
//						rawData.append("<billCycleFromCharging>").append(contract.getBillCycleFromCharging()).append("</billCycleFromCharging>"); // chu ky cuoc
//						rawData.append("<printMethodCode>").append(contract.getPrintMethodCode()).append("</printMethodCode>"); // in
//															// chi
//															// tiet
//
//						rawData.append("<province>").append(contract.getProvince());
//						rawData.append("</province>"); // tinh
//						rawData.append("<district>").append(contract.getDistrict());
//						rawData.append("</district>"); // huyen
//						rawData.append("<precinct>").append(contract.getPrecinct());
//						rawData.append("</precinct>"); // xa
//						rawData.append("<streetBlock>").append(contract.getStreetBlock());
//						rawData.append("</streetBlock>"); // to
//
//						rawData.append("<payAreaCode>").append(contract.getProvince()).append(contract.getDistrict()).append(contract.getPrecinct()).append(contract.getStreetBlock());
//						rawData.append("</payAreaCode>");
//
//						rawData.append("<streetName>"
//								+ StringUtils.escapeHtml(contract.getStreetName()));
//						rawData.append("</streetName>"); // ten duong
//						rawData.append("<home>" + StringUtils.escapeHtml(contract.getHome()));
//						rawData.append("</home>"); // so nha
//
//						rawData.append("<address>" + StringUtils.escapeHtml(contract.getAddress()));
//						rawData.append("</address>");
//
//						rawData.append("<contactName>"
//								+ StringUtils.escapeHtml(contract
//										.getContactName()));
//						rawData.append("</contactName>"); // nguoi dai dien
//
//						rawData.append("<contactTitle>").append(contract.getContactTitle());
//						rawData.append("</contactTitle>"); // chuc vu
//
//						rawData.append("<telFax>").append(contract.getTelFax()).append("</telFax>"); // chu
//												// ky
//												// cuoc
//						rawData.append("<telMobile>").append(contract.getTelMobile()).append("</telMobile>"); // chu
//													// ky
//													// cuoc
//						rawData.append("<email>").append(contract.getEmail()).append("</email>"); // chu
//												// ky
//												// cuoc
//						rawData.append("<idNo>").append(contract.getIdNo()).append("</idNo>"); // chu
//												// ky
//												// cuoc
//						rawData.append("<noticeCharge>").append(contract.getNoticeCharge()).append("</noticeCharge>"); // chu
//														// ky
//														// cuoc
//						rawData.append("<contractPrint>").append(contract.getContractPrint()).append("</contractPrint>"); // thong
//														// tin
//														// bo
//														// sung
//						rawData.append("<billAddress>"
//								+ StringUtils.escapeHtml(contract.getBillAddress()) + "</billAddress>"); // dia
//																					// chi
//																					// thong
//																					// bao
//																					// cuoc
//
//						ContractOffer contractOffer = contract
//								.getContractOffer();
//						if (contractOffer != null) {
//							rawData.append("<contractOffer>");
//
//							rawData.append("<represent>"
//									+ StringUtils.escapeHtml(contractOffer
//											.getRepresent()) + "</represent>");
//							rawData.append("<representPosition>").append(contractOffer.getRepresentPosition()).append("</representPosition>");
//							rawData.append("<strSignDatetime>").append(contractOffer.getSignDatetime()).append("</strSignDatetime>");
//							rawData.append("<idType>").append(contractOffer.getIdType()).append("</idType>");
//							rawData.append("<idNo>").append(contractOffer.getIdNo()).append("</idNo>");
//							rawData.append("<strIssueDatetime>").append(contractOffer.getIssueDatetime()).append("</strIssueDatetime>");
//							rawData.append("<issuePlace>"
//									+ StringUtils.escapeHtml(contractOffer
//											.getIssuePlace()) + "</issuePlace>");
//
//							rawData.append("</contractOffer>");
//						}
//
//						ContractBank contractBank = contract.getContractBank();
//						if (contractBank != null) {
//							rawData.append("<contractBank>");
//
//							rawData.append("<account>").append(contractBank.getAccount()).append("</account>");
//							rawData.append("<accountName>").append(StringUtils.escapeHtml(contractBank.getAccountName()))
//									.append("</accountName>");
//							rawData.append("<bankCode>").append(contractBank.getBankCode()).append("</bankCode>");
//							rawData.append("<bankContractNo>").append(contractBank.getBankContractNo()).append("</bankContractNo>");
//							rawData.append("<strBankContractDate>").append(contractBank.getStrBankContractDate()).append("</strBankContractDate>");
//
//							rawData.append("</contractBank>");
//						}
//					} else {
//						Log.e(Constant.TAG,
//								"EEEEEEEEEEEEEEEE khong thay contract");
//					}
//				}
//
//				rawData.append("</contract>");
//
//				// ========customer ===============
//				rawData.append("<customer>");
//				// TuanTD7
//				if (custommerByIdNoBean.getTin() != null
//						&& !custommerByIdNoBean.getTin().isEmpty()) {
//					rawData.append("<tin>").append(custommerByIdNoBean.getTin());
//					rawData.append("</tin>");
//				}
//
//				if (custommerByIdNoBean.getBusPermitNo() == null
//						|| custommerByIdNoBean.getBusPermitNo().isEmpty()) {
//					rawData.append("<sex>").append(custommerByIdNoBean.getSex());
//					rawData.append("</sex>");
//				}
//
//				rawData.append("<name>"
//						+ StringUtils.escapeHtml(custommerByIdNoBean
//								.getNameCustomer()));
//				rawData.append("</name>");
//				rawData.append("<address>"
//						+ StringUtils.escapeHtml(custommerByIdNoBean.getAddreseCus()));
//				rawData.append("</address>");
//				rawData.append("<areaCode>").append(custommerByIdNoBean.getAreaCode());
//				rawData.append("</areaCode>");
//				if (custommerByIdNoBean.getCustId() != null
//						&& !custommerByIdNoBean.getCustId().isEmpty()) {
//					rawData.append("<birthDateStr>").append(custommerByIdNoBean.getBirthdayCus());
//					rawData.append("</birthDateStr>");
//				} else {
//					rawData.append("<birthDateStr>").append(custommerByIdNoBean.getBirthdayCusNew());
//					rawData.append("</birthDateStr>");
//				}
//
//				if (custommerByIdNoBean != null
//						&& !CommonActivity.isNullOrEmpty(custommerByIdNoBean
//								.getIdType())) {
//					if ("3".equalsIgnoreCase(custommerByIdNoBean.getIdType())) {
//						if (custommerByIdNoBean.getStrIdExpire() != null
//								&& !custommerByIdNoBean.getStrIdExpire()
//										.isEmpty()) {
//							rawData.append("<idExpireDateStr>").append(custommerByIdNoBean.getStrIdExpire());
//							rawData.append("</idExpireDateStr>");
//						}
//					}
//				}
//
//				rawData.append("<busType>").append(custommerByIdNoBean.getCusType());
//				rawData.append("</busType>");
//				rawData.append("<custId>").append(custommerByIdNoBean.getCustId());
//				rawData.append("</custId>");
//				rawData.append("<province>").append(custommerByIdNoBean.getProvince());
//				rawData.append("</province>");
//				rawData.append("<district>").append(custommerByIdNoBean.getDistrict());
//				rawData.append("</district>");
//				rawData.append("<precinct>").append(custommerByIdNoBean.getPrecint());
//				rawData.append("</precinct>");
//
//				rawData.append("<streetBlock>").append(custommerByIdNoBean.getStreet_block());
//				rawData.append("</streetBlock>");
//				rawData.append("<streetName>" + StringUtils.escapeHtml(custommerByIdNoBean.getStreet()));
//				rawData.append("</streetName>");
//				rawData.append("<home>" + StringUtils.escapeHtml(custommerByIdNoBean.getHome()));
//				rawData.append("</home>");
//
//				if (custommerByIdNoBean.getIdNo() != null
//						&& !custommerByIdNoBean.getIdNo().isEmpty()
//						&& (contractNo == null || contractNo.isEmpty())) {
//					rawData.append("<idNo>").append(custommerByIdNoBean.getIdNo());
//					rawData.append("</idNo>");
//				}
//
//				if (custommerByIdNoBean.getIdIssueDate() != null) {
//					rawData.append("<idIssueDateStr>").append(custommerByIdNoBean.getIdIssueDate());
//					rawData.append("</idIssueDateStr>");
//				}
//				if (custommerByIdNoBean.getIdIssuePlace() != null) {
//					rawData.append("<idIssuePlace>"
//							+ StringUtils.escapeHtml(custommerByIdNoBean
//									.getIdIssuePlace()));
//					rawData.append("</idIssuePlace>");
//				}
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
//				if (busPermitNo != null && !busPermitNo.isEmpty()) {
//					rawData.append("<busPermitNo>").append(busPermitNo);
//					rawData.append("</busPermitNo>");
//				}
//
//				CustomerAttribute customerAttribute = custommerByIdNoBean
//						.getCustomerAttribute();
//				if (customerAttribute != null) {
//					rawData.append("<customerAttribute>");
//
//					rawData.append("<name>"
//							+ StringUtils.escapeHtml(customerAttribute
//									.getName()) + "</name>");
//					rawData.append("<birthDateStr>").append(customerAttribute.getBirthDate()).append("</birthDateStr>");
//					rawData.append("<idType>").append(customerAttribute.getIdType()).append("</idType>");
//					rawData.append("<idNo>").append(customerAttribute.getIdNo()).append("</idNo>");
//					rawData.append("<issuePlace>"
//							+ StringUtils.escapeHtml(customerAttribute
//									.getIssuePlace()) + "</issuePlace>");
//					rawData.append("<issueDateStr>"
//							+ StringUtils.escapeHtml(customerAttribute.getIssueDate())
//							+ "</issueDateStr>");
//
//					rawData.append("</customerAttribute>");
//					// them linh tinh thue bao dai dien vao day
//				}
//				if (!CommonActivity.isNullOrEmpty(custommerByIdNoBean
//						.getNotes())) {
//					rawData.append("<notes>" + StringUtils.escapeHtml(custommerByIdNoBean.getNotes()));
//					rawData.append("</notes>");
//				}
//				rawData.append("</customer>");
//
//				// ======= subMb =====thong tin thue bao
//				rawData.append("<subscriber>");
//
//				rawData.append("<prePaidCode>").append(prepaidCode);
//				rawData.append("</prePaidCode>");
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//
//				if (to != null && !to.isEmpty()) {
//					rawData.append("<streetBlock>").append(to);
//					rawData.append("</streetBlock>");
//				}
//				if (tenduong != null && !tenduong.isEmpty()) {
//					rawData.append("<streetName>" + StringUtils.escapeHtml(tenduong));
//					rawData.append("</streetName>");
//				}
//				if (sonha != null && !sonha.isEmpty()) {
//					rawData.append("<home>" + StringUtils.escapeHtml(sonha));
//					rawData.append("</home>");
//				}
//
//				rawData.append("<isdn>").append(isdn);
//				rawData.append("</isdn>");
//				rawData.append("<serial>").append(txtserial.getText().toString());
//				rawData.append("</serial>");
//				rawData.append("<promotionCode>").append(maKM);
//				rawData.append("</promotionCode>");
//				rawData.append("<subType>").append(subTypeMobile);
//				rawData.append("</subType>");
//				rawData.append("<productCode>").append(productCode);
//				rawData.append("</productCode>");
//				rawData.append("<offerId>").append(offerId);
//				rawData.append("</offerId>");
//				rawData.append("<province>").append(province);
//				rawData.append("</province>");
//				rawData.append("<district>").append(district);
//				rawData.append("</district>");
//				rawData.append("<precinct>").append(precinct);
//				rawData.append("</precinct>");
//				rawData.append("<regType>").append(hthm);
//				rawData.append("</regType>");
//				rawData.append("<regReasonId>").append(reasonId);
//				rawData.append("</regReasonId>");
//				rawData.append("<addressCode>").append(province).append(district).append(precinct).append(to);
//				rawData.append("</addressCode>");
//				try {
//					GetAreaDal dal = new GetAreaDal(activity);
//					dal.open();
//					String fulladdress = dal.getfulladddress(province
//							+ district + precinct);
//					rawData.append("<address>" + StringUtils.escapeHtml(fulladdress));
//					rawData.append("</address>");
//					dal.close();
//				} catch (Exception e) {
//					Log.e("Exception", e.toString(), e);
//				}
//				// === bo sung han muc thinhhq1===
//				if ("M".equalsIgnoreCase(serviceType)) {
//					if ("2".equalsIgnoreCase(subType)
//							&& "M".equalsIgnoreCase(serviceType)
//							&& isDeposit == 0) {
//						rawData.append("<quota>").append(quota).append("</quota>");
//					} else {
//						rawData.append("<quota>" + 500000 + "</quota>");
//					}
//				}
//				rawData.append("</subscriber>");
//				// =============raw data add hang hoa
//				List<StockTypeDetailBeans> arrApStockModelBeanBOs = new ArrayList<>();
//				arrApStockModelBeanBOs.addAll(mapSubStockModelRelReq.values());
//				if (arrApStockModelBeanBOs.size() > 0) {
//					for (StockTypeDetailBeans objAPStockModelBeanBO : arrApStockModelBeanBOs) {
//						HashMap<String, String> rawDataItem = new HashMap<>();
//						rawDataItem.put("stockModelId",
//								objAPStockModelBeanBO.getStockModelId() + "");
//						rawDataItem.put("stockModelName",
//								objAPStockModelBeanBO.getStockModelName());
//						rawDataItem.put("serial",
//								objAPStockModelBeanBO.getSerial());
//						rawDataItem.put("stockTypeId",
//								objAPStockModelBeanBO.getStockTypeId() + "");
//						param.put("lstSubStockModelRel",
//								input.buildXML(rawDataItem));
//						rawData.append(input.buildXML(param));
//					}
//				}
//				rawData.append("</smartphoneInputInfoSub>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:subscriberConnectSmartphone>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_subscriberConnectSmartphone");
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
//
//		}
//
//	}
//
//	OnClickListener onbackManagerClick = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			lnButton.setVisibility(View.GONE);
//			// activity.onBackPressed();
//			// if (ActivityCreateNewRequestMobile.instance != null) {
//			// ActivityCreateNewRequestMobile.instance.onBackPressed();
//			// }
//			// activity.finish();
//
//		}
//	};
//
//	// lay ds hang hoa
//	// ===== ws danh sach hang hoa tra sau=============
//	public class GetListProductPosAsyn extends
//			AsyncTask<String, Void, ArrayList<StockTypeBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListProductPosAsyn(Context context) {
//			this.context = context;
//
//			this.progress = new ProgressDialog(context);
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
//		protected ArrayList<StockTypeBeans> doInBackground(String... arg0) {
//			return getListProduct(arg0[0], arg0[1], arg0[2]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<StockTypeBeans> result) {
//			this.progress.dismiss();
//
//			if (errorCode.equals("0")) {
//				if (result.size() > 0) {
//					arrStockTypeBeans.addAll(result);
//					lvproduct.setVisibility(View.VISIBLE);
//					adapProductAdapter = new GetProductAdapter(result, activity);
//					lvproduct.setAdapter(adapProductAdapter);
//					// setListView(arrStockTypeBeans);
//				} else {
//					lvproduct.setVisibility(View.GONE);
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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
//
//				}
//
//			}
//
//		}
//
//		private ArrayList<StockTypeBeans> getListProduct(String regType,
//				String productCode, String serviceType) {
//			ArrayList<StockTypeBeans> arrayListThongTinHH = new ArrayList<>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListStockTypePos");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListStockTypePos>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//
//				rawData.append("<regType>").append(regType);
//				rawData.append("</regType>");
//
//				rawData.append("<productCode>").append(productCode);
//				rawData.append("</productCode>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListStockTypePos>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListStockTypePos");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//
//				original = output.getOriginal();
//				Log.i("original 69696", "original :" + original);
//
//				// ============parse xml in android=========
//				StockTypeHanlderPos handler = (StockTypeHanlderPos) CommonActivity
//						.parseXMLHandler(new StockTypeHanlderPos(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				arrayListThongTinHH = handler.getLstData();
//
//				for (StockTypeBeans stockTypeBeans : arrayListThongTinHH) {
//					stockTypeBeans.setSaleServiceCode(description);
//				}
//			} catch (Exception e) {
//				Log.d("getListProduct", e.toString());
//			}
//
//			return arrayListThongTinHH;
//		}
//	}
//
//	// ===== ws danh sach hang hoa tra truoc=============
//	public class GetListProductPreAsyn extends
//			AsyncTask<String, Void, ArrayList<StockTypeBeans>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetListProductPreAsyn(Context context) {
//			this.context = context;
//
//			this.progress = new ProgressDialog(context);
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
//		protected ArrayList<StockTypeBeans> doInBackground(String... arg0) {
//			return getListProduct(arg0[0], arg0[1], arg0[2]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<StockTypeBeans> result) {
//			this.progress.dismiss();
//
//			if (errorCode.equals("0")) {
//
//				if (result.size() > 0) {
//					arrStockTypeBeans.addAll(result);
//					lvproduct.setVisibility(View.VISIBLE);
//					adapProductAdapter = new GetProductAdapter(result, activity);
//					lvproduct.setAdapter(adapProductAdapter);
//
//				} else {
//					lvproduct.setVisibility(View.GONE);
//				}
//
//			} else {
//
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//
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
//
//				}
//
//			}
//
//		}
//
//		private ArrayList<StockTypeBeans> getListProduct(String regType,
//				String productCode, String serviceType) {
//			ArrayList<StockTypeBeans> arrayListThongTinHH = new ArrayList<>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListStockTypePre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListStockTypePre>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<serviceType>").append(serviceType);
//				rawData.append("</serviceType>");
//
//				rawData.append("<regType>").append(regType);
//				rawData.append("</regType>");
//
//				rawData.append("<productCode>").append(productCode);
//				rawData.append("</productCode>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListStockTypePre>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListStockTypePre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//
//				original = output.getOriginal();
//				Log.i("original 69696", "original :" + original);
//
//				// ============parse xml in android=========
//				StockTypeHanlderPre handler = (StockTypeHanlderPre) CommonActivity
//						.parseXMLHandler(new StockTypeHanlderPre(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				arrayListThongTinHH = handler.getLstData();
//				for (StockTypeBeans stockTypeBeans : arrayListThongTinHH) {
//					stockTypeBeans.setSaleServiceCode(description);
//				}
//			} catch (Exception e) {
//				Log.d("getListProduct", e.toString());
//			}
//
//			return arrayListThongTinHH;
//		}
//	}
//
//	// ws view detail hang hoa tra truoc
//	private class ViewDetailProductPreAsyn extends
//			AsyncTask<String, Void, StockTypeDetailBeans> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//		String stockTypeId = "";
//		private String serial = "";
//		private final StockModel _stockModel;
//
//		public ViewDetailProductPreAsyn(Context context, String stockTypeId,
//				StockModel stockModel, String serial) {
//			this.context = context;
//			this.stockTypeId = stockTypeId;
//			_stockModel = stockModel;
//			this.serial = serial;
//			this.progress = new ProgressDialog(context);
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
//		protected StockTypeDetailBeans doInBackground(String... params) {
//			StockTypeDetailBeans stockDetailBeans = new StockTypeDetailBeans();
//
//			stockDetailBeans.setStockTypeCode(_stockModel.getStockTypeCode());
//			stockDetailBeans
//					.setStockModelId(_stockModel.getStockModelId() == null ? ""
//							: _stockModel.getStockModelId().toString());
//			stockDetailBeans.setSerial(this.serial);
//			stockDetailBeans.setPartnerName(_stockModel.getTableName());
//			stockDetailBeans.setStockModelName(_stockModel.getStockModelName());
//			stockDetailBeans.setStockModelCode(_stockModel.getStockModelCode());
//			stockDetailBeans.setPrice(_stockModel.getPrice() == null ? ""
//					: _stockModel.getPrice().toString());
//			errorCode = "0";
//
//			Log.i("StockTypeDetailBeans", "stockDetailBeans :"
//					+ stockDetailBeans);
//
//			return stockDetailBeans;
//			// return sendRequestViewDetaiPre(params[0], stockTypeId);
//		}
//
//		@Override
//		protected void onPostExecute(StockTypeDetailBeans result) {
//			this.progress.dismiss();
//			CommonActivity.hideKeyboard(edtserial, context);
//			if (errorCode.equals("0")) {
//				if (result.getStockModelId() != null
//						&& !result.getStockModelId().isEmpty()
//						&& result.getSerial() != null
//						&& !result.getSerial().isEmpty()) {
//					showDialogShowDetailProduct(stockTypeId, result);
//				} else {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, getResources()
//									.getString(R.string.notdetailhanghoa),
//									getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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
//		private StockTypeDetailBeans sendRequestViewDetaiPre(String serial,
//				String stockId) {
//			StockTypeDetailBeans stockDetailBeans = new StockTypeDetailBeans();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getListStockModelByStockTypeAndSerialPre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListStockModelByStockTypeAndSerialPre>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<stockTypeId>").append(stockId);
//				rawData.append("</stockTypeId>");
//
//				rawData.append("<serial>").append(serial);
//				rawData.append("</serial>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getListStockModelByStockTypeAndSerialPre>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListStockModelByStockTypeAndSerialPre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//
//				original = output.getOriginal();
//				Log.i("original 69696", "original :" + original);
//
//				// === parrse xml =====
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//
//					nodechild = doc.getElementsByTagName("stockSerialBean");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						String stockTypeCode = parse.getValue(e1,
//								"stockTypeCode");
//						stockDetailBeans.setStockTypeCode(stockTypeCode);
//						String stockModelId = parse
//								.getValue(e1, "stockModelId");
//						stockDetailBeans.setStockModelId(stockModelId);
//						String serialpas = parse.getValue(e1, "serial");
//						stockDetailBeans.setSerial(serialpas);
//						String partnerName = parse.getValue(e1, "partnerName");
//						stockDetailBeans.setPartnerName(partnerName);
//						String stockModelName = parse.getValue(e1,
//								"stockModelName");
//						stockDetailBeans.setStockModelName(stockModelName);
//						String stockModelCode = parse.getValue(e1,
//								"stockModelCode");
//						stockDetailBeans.setStockModelCode(stockModelCode);
//						String price = parse.getValue(e1, "price");
//						stockDetailBeans.setPrice(price);
//					}
//				}
//			} catch (Exception e) {
//				Log.d("sendRequestViewDetaiPre", e.toString());
//			}
//			return stockDetailBeans;
//		}
//	}
//
//	private class AsynctaskGetListStockModel extends
//			AsyncTask<String, Void, ArrayList<StockModel>> {
//
//		private Activity mActivity = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final ProgressDialog progress;
//
//		public AsynctaskGetListStockModel(Activity mActivity) {
//			this.mActivity = mActivity;
//			this.progress = new ProgressDialog(mActivity);
//			this.progress.setCancelable(false);
//			this.progress.setMessage(mActivity.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<StockModel> doInBackground(String... params) {
//			return getListStockModel(params[0], params[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<StockModel> result) {
//			super.onPostExecute(result);
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result == null || result.size() == 0) {
//					CommonActivity.createAlertDialog(mActivity,
//							getString(R.string.no_stock_model_item),
//							getString(R.string.app_name)).show();
//				} else {
//					mListStockModel.addAll(result);
//					StockProductAdapter adapter = new StockProductAdapter(
//							mActivity, mListStockModel);
//					if (dialogLoadMore == null) {
//						showDialogLoadMoreListView(adapter);
//					} else if (!dialogLoadMore.isShowing()) {
//						showDialogLoadMoreListView(adapter);
//					} else {
//						loadMoreListView.setAdapter(adapter);
//					}
//				}
//			} else {
//				Log.d("Log", "description error update" + description);
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							mActivity,
//							description,
//							mActivity.getResources().getString(
//									R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = mActivity.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private ArrayList<StockModel> getListStockModel(String _stockTypeId,
//				String _saleServiceCode) {
//			ArrayList<StockModel> listStockModel = new ArrayList<>();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getStockStaffDetail");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getStockStaffDetail>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<saleServiceCode>").append(_saleServiceCode).append("</saleServiceCode>");
//				rawData.append("<stockTypeId>").append(_stockTypeId).append("</stockTypeId>");
//				rawData.append("</input>");
//				rawData.append("</ws:getStockStaffDetail>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getStockStaffDetail");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Response Original", original);
//
//				// parser
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				NodeList nodechildListSerial = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					Log.d("errorCode", errorCode);
//					nodechild = doc.getElementsByTagName("lstStockModel");
//
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//
//						StockModel stockModel = new StockModel();
//						nodechildListSerial = e1
//								.getElementsByTagName("lstSerial");
//						Log.d("Log", "node list serial: " + nodechildListSerial);
//						for (int k = 0; k < nodechildListSerial.getLength(); k++) {
//							Element e4 = (Element) nodechildListSerial.item(k);
//							Serial serial = new Serial();
//							serial.setFromSerial(parse.getValue(e4,
//									"fromSerial"));
//							serial.setToSerial(parse.getValue(e4, "toSerial"));
//							stockModel.getmListSerial().add(serial);
//						}
//
//						for (Serial serial : stockModel.getmListSerial()) {
//							stockModel.getmListSerialSelection().add(serial);
//							try {
//								Long fromSerial = Long.parseLong(serial
//										.getFromSerial());
//								Long toSerial = Long.parseLong(serial
//										.getToSerial());
//								for (Long k = fromSerial + 1; k <= toSerial; k++) {
//									Serial numberSerial = new Serial(
//											k.toString(), k.toString());
//									stockModel.getmListSerialSelection().add(
//											numberSerial);
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//
//						Log.d("Log", "parser list" + e1);
//
//						String stockModelId = parse
//								.getValue(e1, "stockModelId");
//
//						if (stockModelId != null && !stockModel.equals("")) {
//							stockModel.setStockModelId(Long
//									.parseLong(stockModelId));
//						}
//
//						String stockModelCode = parse.getValue(e1,
//								"stockModelCode");
//
//						if (stockModelCode != null
//								&& !stockModelCode.equals("")) {
//							stockModel.setStockModelCode(stockModelCode);
//						}
//
//						stockModel
//								.setStockModelName(parse.getValue(e1, "name"));
//						stockModel
//								.setStateName(parse.getValue(e1, "stateName"));
//
//						String stockTypeId = parse.getValue(e1, "stockTypeId");
//						if (stockTypeId != null && !stockTypeId.equals("")) {
//							stockModel.setStockTypeId(Long
//									.parseLong(stockTypeId));
//						}
//
//						String telecomServiceId = parse.getValue(e1,
//								"telecomServiceId");
//
//						if (telecomServiceId != null
//								&& !telecomServiceId.equals("")) {
//							stockModel.setTelecomServiceId(Long
//									.parseLong(telecomServiceId));
//						}
//
//						String stateId = parse.getValue(e1, "stateId");
//
//						if (stateId != null && !stateId.equals("")) {
//							stockModel.setStateId(Long.parseLong(stateId));
//						}
//
//						String price = parse.getValue(e1, "price");
//						if (price != null && !price.equals("")) {
//							stockModel.setPrice(Long.parseLong(price));
//						}
//
//						String quantityIssue = parse.getValue(e1,
//								"quantityIssue");
//						if (quantityIssue != null && !quantityIssue.equals("")) {
//							stockModel.setQuantityIssue(Long
//									.parseLong(quantityIssue));
//						}
//
//						String checkSerial = parse.getValue(e1, "checkSerial");
//
//						if (checkSerial != null && !checkSerial.equals("")) {
//							stockModel.setCheckSerial(Long
//									.parseLong(checkSerial));
//						}
//
//						NodeList noList = e1.getChildNodes();
//						for (int k = 0; k < noList.getLength(); k++) {
//							Node node = noList.item(k);
//							if ("quantity".equals(node.getNodeName())) {
//								stockModel.setQuantity(Long.parseLong(node
//										.getTextContent().trim()));
//								break;
//							}
//						}
//
//						stockModel.setStockTypeName(parse.getValue(e1,
//								"stockTypeName"));
//
//						stockModel.setStockTypeCode(parse.getValue(e1,
//								"stockTypeCode"));
//
//						listStockModel.add(stockModel);
//					}
//				}
//			} catch (Exception e) {
//				Log.d("getListIP", e.toString() + "description error", e);
//			}
//
//			return listStockModel;
//
//		}
//
//	}
//
//	// lay ngay bat dau va ngay ket thuc
//    private void showDatePickerDialog(final EditText tvNgay) {
//		OnDateSetListener callback = new OnDateSetListener() {
//			public void onDateSet(DatePicker view, int year, int monthOfYear,
//					int dayOfMonth) {
//				// Date
//				switch (tvNgay.getId()) {
//				case R.id.edtNgayBD:
//					mNgayBatDau = (dayOfMonth) + "/" + (monthOfYear + 1) + "/"
//							+ year;
//					edtNgayBD.setText(mNgayBatDau);
//					try {
//						dateBD = sdf.parse(mNgayBatDau);
//					} catch (Exception e) {
//						Log.e("Exception", e.toString(), e);
//					}
//
//					// TODO modify ngay 08/07/2015 === thinhhq1
//					if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
//						// neu la doi tuong tan sinh vien + 5 nÃƒâ€žÃ†â€™m ngay
//						// bat
//						// dau
//						// va enable
//						String[] ngayketthuc = edtNgayBD.getText().toString()
//								.split("/");
//						if (ngayketthuc.length == 3) {
//							int yearKT = Integer.parseInt(ngayketthuc[2]) + 5;
//							mNgayKetThuc = ngayketthuc[0] + "/"
//									+ ngayketthuc[1] + "/" + yearKT;
//							edtNgayKT.setText(mNgayKetThuc);
//							edtNgayKT.setEnabled(true);
//						}
//
//					} else {
//						edtNgayKT.setEnabled(true);
//					}
//					break;
//				case R.id.edtNgayKT:
//
//					mNgayKetThuc = (dayOfMonth) + "/" + (monthOfYear + 1) + "/"
//							+ year;
//					edtNgayKT.setText(mNgayKetThuc);
//					try {
//						dateEnd = sdf.parse(mNgayKetThuc);
//					} catch (Exception e) {
//						Log.e("Exception", e.toString(), e);
//					}
//					break;
//
//				default:
//					break;
//				}
//				cal.set(year, monthOfYear, dayOfMonth);
//			}
//		};
//		DatePickerDialog pic = null;
//		if (!CommonActivity.isNullOrEmpty(tvNgay.getText().toString())) {
//			Date date = DateTimeUtils.convertStringToTime(tvNgay.getText()
//					.toString(), "dd/MM/yyyy");
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//
//			pic = new DatePickerDialog(activity,AlertDialog.THEME_HOLO_LIGHT, callback,
//					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
//					cal.get(Calendar.DAY_OF_MONTH));
//		} else {
//			pic = new DatePickerDialog(activity,AlertDialog.THEME_HOLO_LIGHT, callback, year, month, day);
//		}
//		pic.setTitle(getString(R.string.chon_ngay));
//		pic.show();
//	}
//
//	private boolean validateViewCommitment() {
//
//		if (CommonActivity.isNullOrEmpty(offerId)) {
//			CommonActivity.createAlertDialog(getActivity(),
//					getActivity().getString(R.string.checkofferid),
//					getActivity().getString(R.string.app_name)).show();
//			return false;
//		}
//		if (CommonActivity.isNullOrEmpty(hthm)) {
//			CommonActivity.createAlertDialog(getActivity(),
//					getActivity().getString(R.string.checkregtype),
//					getActivity().getString(R.string.app_name)).show();
//			return false;
//		}
//		if (CommonActivity.isNullOrEmpty(txtisdn.getText().toString().trim())) {
//			CommonActivity.createAlertDialog(getActivity(),
//					getActivity().getString(R.string.checkisdncm),
//					getActivity().getString(R.string.app_name)).show();
//			return false;
//		}
//		return true;
//	}
//
//	private void showDialogShowDetailProduct(final String stockTypeId,
//			final StockTypeDetailBeans stockDetailBeans) {
//		dialogShowDetailProduct = new Dialog(activity);
//		dialogShowDetailProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogShowDetailProduct
//				.setContentView(R.layout.connectionmobile_detailproduct_dialog);
//		final EditText edtloaimathang = (EditText) dialogShowDetailProduct
//				.findViewById(R.id.edtloaimathang);
//		final EditText edtSerial = (EditText) dialogShowDetailProduct
//				.findViewById(R.id.edtSerial);
//		final EditText edttenmathang = (EditText) dialogShowDetailProduct
//				.findViewById(R.id.edttenmathang);
//		final EditText edtmamathang = (EditText) dialogShowDetailProduct
//				.findViewById(R.id.edtmamathang);
//		final EditText edtnhasx = (EditText) dialogShowDetailProduct
//				.findViewById(R.id.edtnhasx);
//		final EditText edtgia = (EditText) dialogShowDetailProduct
//				.findViewById(R.id.edtgia);
//
//		if (stockDetailBeans.getSerial() != null
//				&& !stockDetailBeans.getSerial().isEmpty()) {
//			edtSerial.setText(stockDetailBeans.getSerial());
//		}
//
//		if (stockDetailBeans.getStockModelName() != null
//				&& !stockDetailBeans.getStockModelName().isEmpty()) {
//			edttenmathang.setText(stockDetailBeans.getStockModelName());
//		}
//
//		if (stockDetailBeans.getStockModelCode() != null
//				&& !stockDetailBeans.getStockModelCode().isEmpty()) {
//			edtmamathang.setText(stockDetailBeans.getStockModelCode());
//		}
//
//		if (stockDetailBeans.getPartnerName() != null
//				&& !stockDetailBeans.getPartnerName().isEmpty()) {
//			edtnhasx.setText(stockDetailBeans.getPartnerName());
//		}
//
//		Button btncamket = (Button) dialogShowDetailProduct
//				.findViewById(R.id.btncamket);
//		if ("2".equals(subType)) {
//			btncamket.setVisibility(View.VISIBLE);
//		} else {
//			btncamket.setVisibility(View.GONE);
//		}
//		btncamket.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				if (CommonActivity.isNetworkConnected(getActivity())) {
//					if (validateViewCommitment()) {
//						AsynctaskviewSubCommitmentStock asynctaskviewSubCommitmentStock = new AsynctaskviewSubCommitmentStock(
//								getActivity(), stockDetailBeans.getSerial());
//						asynctaskviewSubCommitmentStock.execute(offerId, hthm,
//								txtisdn.getText().toString().trim(),
//								stockDetailBeans.getSerial(),
//								stockDetailBeans.getStockModelId());
//					}
//				} else {
//					CommonActivity.createAlertDialog(getActivity(),
//							getActivity().getString(R.string.errorNetwork),
//							getActivity().getString(R.string.app_name)).show();
//				}
//
//			}
//		});
//
//		dialogShowDetailProduct.findViewById(R.id.btnOk).setOnClickListener(
//				new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						stockDetailBeans.setStockTypeId(stockTypeId);
//						mapSubStockModelRelReq.put(stockTypeId,
//								stockDetailBeans);
//
//						if (dialogInsertSerial != null) {
//							dialogInsertSerial.dismiss();
//						}
//						dialogShowDetailProduct.dismiss();
//
//					}
//				});
//		dialogShowDetailProduct.findViewById(R.id.btnViewSaleTrans)
//				.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						dialogShowDetailProduct.dismiss();
//					}
//				});
//		dialogShowDetailProduct.show();
//
//	}
//
//	// Ham lay thong tin doi duong tu offerid va idNo
//	private class GetProductSpecInfoPreAsyn extends
//			AsyncTask<String, Void, ArrayList<SpecObject>> {
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetProductSpecInfoPreAsyn(Context context) {
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
//		protected ArrayList<SpecObject> doInBackground(String... arg0) {
//			return getLstObjectSpec(arg0[0], arg0[1]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<SpecObject> result) {
//			progress.dismiss();
//
//			// truong hop co giay to dac biet == danh sach cac doi duong
//			if (errorCode.equalsIgnoreCase("0")) {
//				if (result != null && !result.isEmpty()) {
//					arrSpecObjects = result;
//
//					// hien thi form thong tin dac biet len
//					lnTTGoiCuocDacBiet.setVisibility(View.VISIBLE);
//					// do du lieu vao spin doi tuong
//					// mCodeDoiTuong = arrSpecObjects.get(0).getCode();
//					doiTuongAdapter = new ObjDoiTuongAdapter(arrSpecObjects,
//							activity);
//					spDoiTuong.setAdapter(doiTuongAdapter);
//					for (SpecObject specObject : arrSpecObjects) {
//						if (Constant.CODE_TSV.equalsIgnoreCase(specObject
//								.getCode())) {
//							spDoiTuong.setSelection(arrSpecObjects
//									.indexOf(specObject));
//							spDoiTuong.setEnabled(false);
//							break;
//						}
//					}
//				} else {
//					// hide form thong tin dac biet di va reload thong tin dac
//					// biet
//					spDoiTuong.setEnabled(true);
//					mCodeDoiTuong = "";
//					mCodeDonVi = "";
//					lnTTGoiCuocDacBiet.setVisibility(View.GONE);
//					if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
//						arrSpecObjects.clear();
//					}
//					if (doiTuongAdapter != null) {
//						doiTuongAdapter.notifyDataSetChanged();
//					}
//				}
//			} else {
//
//				spDoiTuong.setEnabled(true);
//				mCodeDoiTuong = "";
//				mCodeDonVi = "";
//				lnTTGoiCuocDacBiet.setVisibility(View.GONE);
//				if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
//					arrSpecObjects.clear();
//				}
//				if (doiTuongAdapter != null) {
//					doiTuongAdapter.notifyDataSetChanged();
//				}
//
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else if (errorCode.equalsIgnoreCase("1")) {
//
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes1);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
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
//		private ArrayList<SpecObject> getLstObjectSpec(String offerId,
//				String idNo) {
//			ArrayList<SpecObject> arrayList = new ArrayList<>();
//			String original = null;
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getProductSpecInfoPre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getProductSpecInfoPre>");
//				rawData.append("<cmMobileInput>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//
//				rawData.append("<offerId>").append(offerId);
//				rawData.append("</offerId>");
//
//				rawData.append("<idNo>").append(idNo);
//				rawData.append("</idNo>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getProductSpecInfoPre>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getProductSpecInfoPre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//
//				original = output.getOriginal();
//				Log.i("original 69696", "original :" + original);
//
//				// parse xml
//				// === parrse xml =====
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//
//					nodechild = doc.getElementsByTagName("lstObjectSpecPre");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//
//						String code = parse.getValue(e1, "code");
//						String codeName = parse.getValue(e1, "codeName");
//						String id = parse.getValue(e1, "id");
//						int ID = Integer.parseInt(id);
////						SpecObject obj = new SpecObject(ID, code, codeName);
////						arrayList.add(obj);
//					}
//				}
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//
//			return arrayList;
//		}
//
//	}
//
//	private class CheckInfoCusSpecialAsyn extends
//			AsyncTask<String, Void, ArrayList<StudentBean>> {
//
//		final ProgressDialog progress;
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public CheckInfoCusSpecialAsyn(Context context) {
//			this.context = context;
//			this.progress = new ProgressDialog(this.context);
//			// check font
//			this.progress.setCancelable(false);
//			this.progress.setMessage(context.getResources().getString(
//					R.string.waitting));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected ArrayList<StudentBean> doInBackground(String... arg0) {
//			return sendReqCheckInfoCus(arg0[0]);
//		}
//
//		@Override
//		protected void onPostExecute(final ArrayList<StudentBean> result) {
//			progress.dismiss();
//			int dem = 0;
//			if ("0".equalsIgnoreCase(errorCode)) {
//				if (result != null && !result.isEmpty()) {
//
//					final Dialog dialog = new Dialog(activity);
//					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//					dialog.setContentView(R.layout.connection_layout_lst_sobaodanh);
//
//					ListView lvsobaodanh = (ListView) dialog
//							.findViewById(R.id.lvsobaodanh);
//
//					Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
//
//					GetSobaodanhAdapter getSobaodanhAdapter = new GetSobaodanhAdapter(
//							result, activity);
//					lvsobaodanh.setAdapter(getSobaodanhAdapter);
//
//					lvsobaodanh
//							.setOnItemClickListener(new OnItemClickListener() {
//
//								@Override
//								public void onItemClick(AdapterView<?> arg0,
//										View arg1, int arg2, long arg3) {
//									StudentBean studentBean = result.get(arg2);
//									if (!CommonActivity
//											.isNullOrEmpty(studentBean
//													.getSoBD())) {
//										edtMaGiayToDacBiet.setText(studentBean
//												.getSoBD());
//										edtMaGiayToDacBiet.setEnabled(false);
//									}
//
//									if (!CommonActivity
//											.isNullOrEmpty(studentBean
//													.getMaTruongDKTT())) {
//										mCodeDonVi = studentBean
//												.getMaTruongDKTT();
//										tvDonVi.setText(mCodeDonVi);
//									}
//
//									dialog.dismiss();
//								}
//							});
//
//					btnhuy.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View arg0) {
//							dialog.dismiss();
//						}
//					});
//
//					dialog.show();
//
//					// for (StudentBean studentBean : result) {
//					// if(studentBean.getMaTruongDKTT() != null &&
//					// !studentBean.getMaTruongDKTT().isEmpty()){
//					// if(studentBean.getSoBD() != null &&
//					// !studentBean.getSoBD().isEmpty()){
//					// edtMaGiayToDacBiet.setText(studentBean.getSoBD());
//					// edtMaGiayToDacBiet.setEnabled(false);
//					// dem ++;
//					// rlchondonvi.setVisibility(View.VISIBLE);
//					// mCodeDonVi = studentBean.getMaTruongDKTT();
//					// tvDonVi.setText(mCodeDonVi);
//					// tvDonVi.setEnabled(false);
//					// break;
//					// }
//					// }
//					// }
//					//
//					// // truong hop ko co ma truong thi cho chon don vi
//					// if(dem == 0){
//					// rlchondonvi.setVisibility(View.VISIBLE);
//					// edtMaGiayToDacBiet.setText(result.get(0).getSoBD());
//					// edtMaGiayToDacBiet.setEnabled(false);
//					// tvDonVi.setEnabled(true);
//					// }else{
//					// }
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getString(R.string.checkinfocusnewst),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)
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
//				}
//			}
//		}
//
//		private ArrayList<StudentBean> sendReqCheckInfoCus(String soCMT) {
//			ArrayList<StudentBean> lstSoBD = new ArrayList<>();
//			String original = null;
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getThongTinThiSinh");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getThongTinThiSinh>");
//				rawData.append("<cmMobileInput>");
//				rawData.append("<token>").append(Session.getToken());
//				rawData.append("</token>");
//				rawData.append("<soBD>" + "");
//				rawData.append("</soBD>");
//				rawData.append("<soCMT>").append(soCMT);
//				rawData.append("</soCMT>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:getThongTinThiSinh>");
//
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getThongTinThiSinh");
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
//					nodechild = doc.getElementsByTagName("ns3:ThiSinhModel");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						StudentBean studentBean = new StudentBean();
//						String sobaodanh = parse.getValue(e1, "ns3:SoBaoDanh");
//						if (sobaodanh != null && !sobaodanh.isEmpty()) {
//							studentBean.setSoBD(sobaodanh);
//							Log.d("sobaodanh", sobaodanh);
//						}
//						String matruongDKDT = parse.getValue(e1,
//								"ns3:MaTruongDKDT");
//						studentBean.setMaTruongDKTT(matruongDKDT);
//
//						String hoten = parse.getValue(e1, "ns3:Hoten");
//						studentBean.setHoten(hoten);
//
//						String ngaysinh = parse.getValue(e1, "ns3:NgaySinh");
//						studentBean.setNgaysinh(ngaysinh);
//
//						String gioitinh = parse.getValue(e1, "ns3:GioiTinh");
//						studentBean.setGioiTinh(gioitinh);
//
//						String soCMND = parse.getValue(e1, "ns3:SoCMND");
//						studentBean.setSoCMND(soCMND);
//
//						String diachi = parse.getValue(e1, "ns3:DiaChi");
//						studentBean.setDiaChi(diachi);
//
//						lstSoBD.add(studentBean);
//					}
//				}
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//			return lstSoBD;
//		}
//
//	}
//
//	// @Override
//	// public void onItemClick(AdapterView<?> adapterView, View view, int
//	// position, long id) {
//	// StockTypeBeans stockTypeBeans = (StockTypeBeans)
//	// arrStockTypeBeans.get(position);
//	// saleServiceCode = stockTypeBeans.getSaleServiceCode();
//	//
//	// if(saleServiceCode == null) {
//	// saleServiceCode = "";
//	// }
//	//
//	// String stockModelId = stockTypeBeans.getStockId();
//	//
//	// Log.d(this.getClass().getSimpleName(), "stockModelId " + stockModelId +
//	// " " + mListStockModel.size());
//	// if(!mListStockModel.isEmpty() ) {
//	// Log.d(this.getClass().getSimpleName(), "stockModelId " + stockModelId +
//	// " " + mListStockModel.size() + " " +
//	// mListStockModel.get(0).getStockModelId() + " " +
//	// mListStockModel.get(0).getStockTypeId());
//	// }
//	//
//	// if(!mListStockModel.isEmpty() &&
//	// stockModelId.equalsIgnoreCase(mListStockModel.get(0).getStockTypeId().toString()))
//	// {
//	// StockProductAdapter adapter = new StockProductAdapter(activity,
//	// mListStockModel);
//	// if(dialogLoadMore == null) {
//	// showDialogLoadMoreListView(adapter);
//	// } else if(!dialogLoadMore.isShowing()) {
//	// showDialogLoadMoreListView(adapter);
//	// } else {
//	// loadMoreListView.setAdapter(adapter);
//	// }
//	// } else {
//	// mListStockModel.clear();
//	// Log.d(this.getClass().getSimpleName(), "onItemClick saleServiceCode " +
//	// saleServiceCode + " StockTypeBeans " + stockTypeBeans);
//	// AsynctaskGetListStockModel async = new
//	// AsynctaskGetListStockModel(activity);
//	// async.execute(stockModelId, saleServiceCode);
//	// }
//	// }
//
//	@Override
//	public void onItemClick(AdapterView<?> adapterView, View view,
//			int position, long id) {
//		StockTypeBeans stockTypeBeans = arrStockTypeBeans
//				.get(position);
//        String saleServiceCode = stockTypeBeans.getSaleServiceCode();
//
//		if (saleServiceCode == null) {
//			saleServiceCode = "";
//		}
//
//		String stockModelId = stockTypeBeans.getStockId();
//
//		Log.d(this.getClass().getSimpleName(), "stockModelId " + stockModelId
//				+ " " + mListStockModel.size());
//		if (!mListStockModel.isEmpty()) {
//			Log.d(this.getClass().getSimpleName(), "stockModelId "
//					+ stockModelId + " " + mListStockModel.size() + " "
//					+ mListStockModel.get(0).getStockModelId() + " "
//					+ mListStockModel.get(0).getStockTypeId());
//		}
//
//		mListStockModel.clear();
//		Log.d(this.getClass().getSimpleName(), "onItemClick saleServiceCode "
//				+ saleServiceCode + " StockTypeBeans " + stockTypeBeans);
//		AsynctaskGetListStockModel async = new AsynctaskGetListStockModel(
//				activity);
//		async.execute(stockModelId, saleServiceCode);
//	}
//
//	private Dialog dialogLoadMore;
//	private LoadMoreListView loadMoreListView;
//	private StockModel stockModel = null;
//
//	private void showDialogLoadMoreListView(final ArrayAdapter adapter) {
//		StockModel firstStockModel = null;
//		StockTypeDetailBeans stockTypeDetailBeans = null;
//		String stockTypeId = null;
//		if (adapter.getCount() > 0) {
//			firstStockModel = (StockModel) adapter.getItem(0);
//			stockTypeId = firstStockModel.getStockTypeId().toString();
//			if (mapSubStockModelRelReq.containsKey(stockTypeId)) {
//				stockTypeDetailBeans = mapSubStockModelRelReq.get(stockTypeId);
//			}
//		}
//
//		dialogLoadMore = new Dialog(activity);
//		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogLoadMore.setContentView(R.layout.dialog_listview_stockmodel);
//		dialogLoadMore.setCancelable(true);
//		dialogLoadMore.setTitle(getString(R.string.title_detail_tranffer));
//
//		loadMoreListView = (LoadMoreListView) dialogLoadMore
//				.findViewById(R.id.loadMoreListView);
//
//		final Button btnCancel = (Button) dialogLoadMore
//				.findViewById(R.id.btnCancel);
//		btnCancel.setTag(1);
//		final TextView tvHeader = (TextView) dialogLoadMore
//				.findViewById(R.id.tvHeader);
//		tvHeader.setVisibility(View.VISIBLE);
//		tvHeader.setText(getString(R.string.title_detail_tranffer));
//
//		LinearLayout ln_stock_select = (LinearLayout) dialogLoadMore
//				.findViewById(R.id.ln_stock_select);
//		EditText edt_stock_select = (EditText) dialogLoadMore
//				.findViewById(R.id.edt_stock_select);
//		LinearLayout ln_serial_select = (LinearLayout) dialogLoadMore
//				.findViewById(R.id.ln_serial_select);
//		EditText edt_serial_select = (EditText) dialogLoadMore
//				.findViewById(R.id.edt_serial_select);
//		if (stockTypeDetailBeans == null) {
//			ln_stock_select.setVisibility(View.GONE);
//			ln_serial_select.setVisibility(View.GONE);
//		} else {
//			ln_stock_select.setVisibility(View.VISIBLE);
//			ln_serial_select.setVisibility(View.VISIBLE);
//			edt_stock_select.setText(stockTypeDetailBeans.getStockModelName());
//			edt_serial_select.setText(stockTypeDetailBeans.getSerial());
//		}
//
//		loadMoreListView.setAdapter(adapter);
//		loadMoreListView
//				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
//
//					@Override
//					public void onLoadMore() {
//						Log.i(this.getClass().getSimpleName(),
//								"loadMoreListView onLoadMore");
//					}
//				});
//
//		final OnItemClickListener SerialListener = new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				String serial = (String) parent.getAdapter().getItem(position);
//				dialogLoadMore.dismiss();
//				Log.d(this.getClass().getSimpleName(),
//						"onItemClick stockModel " + stockModel.toJson());
//				ViewDetailProductPreAsyn viewProductPreAsyn = new ViewDetailProductPreAsyn(
//						activity, stockModel.getStockTypeId().toString(),
//						stockModel, serial);
//				viewProductPreAsyn.execute(serial, stockModel.getStockTypeId()
//						.toString());
//			}
//		};
//
//		final OnItemClickListener StockModelListener = new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view,
//					int position, long id) {
//				stockModel = (StockModel) adapterView.getAdapter().getItem(
//						position);
//				ArrayList<Serial> mListSerial = stockModel.getmListSerial();
//				ArrayList<Serial> mListSerialSelection = stockModel
//						.getmListSerialSelection();
//
//				Log.d(this.getClass().getSimpleName(),
//						"onItemClick stockModel " + stockModel.toJson()
//								+ " mListSerial: " + mListSerial.size()
//								+ " mListSerialSelection: "
//								+ mListSerialSelection.size());
//
//				List<String> lstSerial = new ArrayList<>();
//				for (Serial serial : mListSerialSelection) {
//					lstSerial.add(serial.getFromSerial());
//				}
//
//				tvHeader.setText(getString(R.string.title_list_serial));
//				dialogLoadMore.setTitle(getString(R.string.title_list_serial));
//				ArrayAdapter<String> serialAdapter = new ArrayAdapter<>(
//                        activity, R.layout.simple_list_item_background,
//                        android.R.id.text1, lstSerial);
//				btnCancel.setTag(2);
//				loadMoreListView.setAdapter(serialAdapter);
//				serialAdapter.notifyDataSetChanged();
//				loadMoreListView.setOnItemClickListener(SerialListener);
//			}
//		};
//
//		btnCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				int tag = (Integer) v.getTag();
//				if (tag == 2) {
//					v.setTag(1);
//					// tag = 1;
//					loadMoreListView.setAdapter(adapter);
//					adapter.notifyDataSetChanged();
//					loadMoreListView.setOnItemClickListener(StockModelListener);
//				} else {
//					dialogLoadMore.dismiss();
//				}
//			}
//		});
//
//		loadMoreListView.setOnItemClickListener(StockModelListener);
//
//		dialogLoadMore.show();
//	}
//
//	private final OnClickListener imageListenner = new OnClickListener() {
//		@Override
//		public void onClick(View view) {
//			Log.d(Constant.TAG, "view.getId() : " + view.getId());
//			ImagePreviewActivity.pickImage(activity, hashmapFileObj,
//					view.getId());
//		}
//	};
//
//	// LAY THONG TIN FILE HO SO UP LEN
//
//	public class GetLisRecordPrepaidTask extends AsyncTask<Void, Void, String> {
//
//		private final String productCode;
//		private final ProgressDialog dialog;
//		private final Context context;
//		private final String reasonId;
//
//		public GetLisRecordPrepaidTask(String reasonId, String productCode,
//				Context context) {
//			this.productCode = productCode;
//			this.reasonId = reasonId;
//			this.context = context;
//			dialog = new ProgressDialog(context);
//			this.dialog.setMessage(getResources().getString(R.string.waitting));
//			// this.dialog.show();
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			String original = null;
//			boolean isNetwork = CommonActivity.isNetworkConnected(activity);
//			if (isNetwork) {
//				original = requestSevice(reasonId, productCode);
//			} else {
//			}
//			return original;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			if (dialog.isShowing()) {
//				dialog.dismiss();
//			}
//
//			if (result == null || result.equals("")) {
//				CommonActivity.createAlertDialog(activity,
//						getResources().getString(R.string.errorNetwork),
//						getResources().getString(R.string.app_name)).show();
//			} else {
//				parseResultError(result);
//				// Tao spinner upload anh
//				mapListRecordPrepaid = FragmentManageConnect
//						.parseResultListRecordPrepaid(result);
//
//				hashmapFileObj.clear();
//
//				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<>(
//                        mapListRecordPrepaid.values());
//				RecordPrepaidAdapter adapter = new RecordPrepaidAdapter(
//						activity, arrayOfArrayList, imageListenner);
//				lvUploadImage.setAdapter(adapter);
//
//				UI.setListViewHeightBasedOnChildren(lvUploadImage);
//			}
//			Log.e("TAG6", "result List productCode : " + result);
//
//			super.onPostExecute(result);
//		}
//	}
//
//	private String requestSevice(String reasonId, String productCode) {
//
//		String reponse = null;
//		String original = null;
//
//		String xml = mBccsGateway.getXmlCustomer(
//				createXML(reasonId, productCode), "mbccs_getListRecordPrepaid");
//		Log.e("TAG8", "xml getListRecordPrepaid" + xml);
//		try {
//			reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
//					activity, "mbccs_getListRecordPrepaid");
//			Log.e("TAG8", "reponse getListRecordPrepaid" + reponse);
//		} catch (Exception e) {
//
//			Log.e("Exception", e.toString(), e);
//		}
//		if (reponse != null) {
//			CommonOutput commonOutput;
//			try {
//				commonOutput = mBccsGateway.parseGWResponse(reponse);
//				original = commonOutput.getOriginal();
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}
//
//		}
//		return original;
//	}
//
//	private String createXML(String reasonId, String productCode) {
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("<ws:getListRecordPrepaid>");
//		stringBuilder.append("<cmInput>");
//		stringBuilder.append("<isConnect>");
//		stringBuilder.append(true);
//		stringBuilder.append("</isConnect>");
//		if ("1".equalsIgnoreCase(subType)) {
//			stringBuilder.append("<isPospaid>" + false + "</isPospaid>");
//		} else {
//			stringBuilder.append("<isPospaid>" + true + "</isPospaid>");
//		}
//		stringBuilder.append("<serviceType>").append(serviceType).append("</serviceType>");
//		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
//		stringBuilder.append("<productCode>").append(productCode).append("</productCode>");
//		stringBuilder.append("<reasonId>").append(reasonId).append("</reasonId>");
//		stringBuilder.append("</cmInput>");
//		stringBuilder.append("</ws:getListRecordPrepaid>");
//		return stringBuilder.toString();
//	}
//
//	// private void initTo(ArrayList<AreaObj> areaCode){
//	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//	// activity,
//	// android.R.layout.simple_dropdown_item_1line,
//	// android.R.id.text1);
//	// for (AreaObj areaBean : areaCode) {
//	// adapter.add(areaBean.getName());
//	// }
//	// spin_streetBlock.setAdapter(adapter);
//	// }
//
//	@SuppressWarnings("unused")
//	private class GetListGroupAdressAsyncTask extends
//			AsyncTask<String, Void, ArrayList<AreaObj>> {
//
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private ProgressDialog progress;
//		private final Spinner spin_streetBlock;
//		private int spn_streetBlock_pos = -1;
//		private final String streetBlock;
//		private ProgressBar prb_bar = null;
//		private int typeTo = 0;
//		private final Button btnRefresh;
//
//		@SuppressWarnings("unused")
//		public GetListGroupAdressAsyncTask(Context context,
//				Spinner spin_streetBlock, int _spn_streetBlock_pos,
//				String _streetBlock, ProgressBar progessBar, int type,
//				Button btnRefresh) {
//			this.context = context;
//			this.spin_streetBlock = spin_streetBlock;
//			this.spn_streetBlock_pos = _spn_streetBlock_pos;
//			this.streetBlock = _streetBlock;
//			this.prb_bar = progessBar;
//			this.typeTo = type;
//			this.btnRefresh = btnRefresh;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//
//			// this.progress = new ProgressDialog(this.context);
//			// this.progress.setCancelable(false);
//			// this.progress.setMessage(context.getResources().getString(
//			// R.string.getdataing));
//			// if (!this.progress.isShowing()) {
//			// this.progress.show();
//			// }
//			if (prb_bar != null) {
//				prb_bar.setVisibility(View.VISIBLE);
//			}
//
//			if (btnRefresh != null) {
//				btnRefresh.setVisibility(View.GONE);
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
//
//			// progress.dismiss();
//			if (prb_bar != null) {
//				prb_bar.setVisibility(View.GONE);
//			}
//			if (btnRefresh != null) {
//				btnRefresh.setVisibility(View.VISIBLE);
//			}
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					if (typeTo == 1) {
//						arrTo = result;
//						ArrayAdapter<String> adapterTo = new ArrayAdapter<>(
//                                activity,
//                                android.R.layout.simple_dropdown_item_1line,
//                                android.R.id.text1);
//						for (AreaObj areaBean : result) {
//							adapterTo.add(areaBean.getName());
//						}
//						spin_streetBlock.setAdapter(adapterTo);
//						if (spn_streetBlock_pos >= 0
//								&& spn_streetBlock_pos < arrTo.size()) {
//							spin_streetBlock.setSelection(spn_streetBlock_pos);
//						} else if (streetBlock != null
//								&& !streetBlock.isEmpty()) {
//							for (int i = 0; i < arrTo.size(); i++) {
//								AreaObj areaObj = arrTo.get(i);
//								if (streetBlock.equalsIgnoreCase(areaObj
//										.getAreaCode())) {
//									spin_streetBlock.setSelection(i);
//									break;
//								}
//							}
//						}
//					} else {
//						arrToDialog = result;
//						ArrayAdapter<String> adapterTo = new ArrayAdapter<>(
//                                activity,
//                                android.R.layout.simple_dropdown_item_1line,
//                                android.R.id.text1);
//						for (AreaObj areaBean : result) {
//							adapterTo.add(areaBean.getName());
//						}
//						spin_streetBlock.setAdapter(adapterTo);
//						if (spn_streetBlock_pos >= 0
//								&& spn_streetBlock_pos < arrTo.size()) {
//							spin_streetBlock.setSelection(spn_streetBlock_pos);
//						} else if (streetBlock != null
//								&& !streetBlock.isEmpty()) {
//							for (int i = 0; i < arrToDialog.size(); i++) {
//								AreaObj areaObj = arrToDialog.get(i);
//								if (streetBlock.equalsIgnoreCase(areaObj
//										.getAreaCode())) {
//									spin_streetBlock.setSelection(i);
//									break;
//								}
//							}
//						}
//					}
//
//				} else {
//					if (typeTo == 1) {
//						arrTo = new ArrayList<>();
//					} else {
//						arrToDialog = new ArrayList<>();
//					}
//
//					// arrTo = new ArrayList<AreaObj>();
//					ArrayAdapter<String> adapterTo = new ArrayAdapter<>(
//                            activity,
//                            android.R.layout.simple_dropdown_item_1line,
//                            android.R.id.text1);
//					spin_streetBlock.setAdapter(adapterTo);
//
//					CommonActivity.createAlertDialog(activity,
//							getString(R.string.notStreetBlock),
//							getString(R.string.app_name)).show();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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
//			String original = "";
//			try {
//
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
//				Log.i("Response Original group", response);
//
//				// ==== parse xml list ip
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
//
//		// public void updatePrecintSpinner(ArrayList<AreaObj> result) {
//		// mListGroup = result;
//		// AdapterProvinceSpinner adapterGroup = new
//		// AdapterProvinceSpinner(mListGroup, activity);
//		// streetBlock.setAdapter(adapterGroup);
//		//
//		// if(type == 1){
//		// if (areaGroup != null) {
//		// for (int i = 0; i < mListGroup.size(); i++) {
//		// AreaObj areaGroupObject = mListGroup.get(i);
//		// if (areaGroupObject.getAreaCode().equals(areaGroup.getAreaCode())) {
//		// streetBlock.setSelection(i);
//		// }
//		// }
//		// }
//		// }else{
//		//
//		// if (areaGroup != null) {
//		// for (int i = 0; i < mListGroup.size(); i++) {
//		// AreaObj areaGroupObject = mListGroup.get(i);
//		// if (areaGroupObject.getName().equals(areaGroup.getName())) {
//		// streetBlock.setSelection(i);
//		// }
//		// }
//		// }
//		// }
//		//
//		// streetBlock.setOnItemSelectedListener(new OnItemSelectedListener() {
//		// @Override
//		// public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//		// long arg3) {
//		// }
//		// @Override
//		// public void onNothingSelected(AdapterView<?> arg0) {
//		// }
//		// });
//		// }
//
//	}
//
//	private ArrayList<Spin> arrHTTTHD = null;
//	private ArrayList<Spin> arrLOAIHD = null;
//	private ArrayList<Spin> arrCKC = null;
//	private ArrayList<Spin> arrINCT = null;
//	private ArrayList<Spin> arrHTTBC = null;
//	private ArrayList<Spin> arrTTBSHD = null;
//
//	// private ArrayList<Spin> arrBANK = null;
//
//	private static final int TYPE_HTTHHD = 1;
//	private static final int TYPE_LOAI_HD = 2;
//	private static final int TYPE_CK_CUOC_HD = 3;
//	private static final int TYPE_INCT_HD = 4;
//	private static final int TYPE_HTTBC_HD = 5;
//	private static final int TYPE_TTBS_HD = 6;
//	// private int TYPE_PL_HD = 7;
//
//	private Boolean isRefreshTYPE_HTTTHD = false;
//	private Boolean isRefreshTYPE_LOAI_HD = false;
//	private Boolean isRefreshTYPE_CK_CUOC_HD = false;
//    private Boolean isRefreshTYPE_HTTBC_HD = false;
//
//    // Ham lay danh sach hinh thuc thanh toan
//	private class AsyntaskGetListAllCommon extends
//			AsyncTask<Void, Void, ArrayList<Spin>> {
//
//		private ProgressDialog progress;
//		private Context context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private int type = 0;
//
//		public AsyntaskGetListAllCommon(Context context, int type) {
//			this.context = context;
//			this.type = type;
//			switch (type) {
//			case TYPE_HTTHHD:
//				if (prbhttthd != null) {
//					prbhttthd.setVisibility(View.VISIBLE);
//				}
//				if (btnhttthd != null) {
//					btnhttthd.setVisibility(View.GONE);
//				}
//				break;
//			case TYPE_CK_CUOC_HD:
//
//				if (prbchukicuoc != null) {
//					prbchukicuoc.setVisibility(View.VISIBLE);
//				}
//				if (btnchukicuoc != null) {
//					btnchukicuoc.setVisibility(View.GONE);
//				}
//				break;
//			case TYPE_HTTBC_HD:
//				if (prbhttbc != null) {
//					prbhttbc.setVisibility(View.VISIBLE);
//				}
//				if (btnhttbc != null) {
//					btnhttbc.setVisibility(View.GONE);
//				}
//				break;
//			case TYPE_INCT_HD:
//				if (prbinchitiet != null) {
//					prbinchitiet.setVisibility(View.VISIBLE);
//				}
//				if (btninchitiet != null) {
//					btninchitiet.setVisibility(View.GONE);
//				}
//				break;
//			case TYPE_LOAI_HD:
//				if (prbloaihopdong != null) {
//					prbloaihopdong.setVisibility(View.VISIBLE);
//				}
//				if (btnloaihopdong != null) {
//					btnloaihopdong.setVisibility(View.GONE);
//				}
//				break;
//			case TYPE_TTBS_HD:
//
//				if (prbttbosung != null) {
//					prbttbosung.setVisibility(View.VISIBLE);
//				}
//				if (btnttbosung != null) {
//					btnttbosung.setVisibility(View.GONE);
//				}
//				break;
//
//			default:
//				break;
//			}
//
//		}
//
//		@Override
//		protected ArrayList<Spin> doInBackground(Void... arg0) {
//			if (type == TYPE_HTTHHD) {
//				return getListPayMethode();
//			} else if (type == TYPE_LOAI_HD) {
//				return getListContractType();
//			} else if (type == TYPE_CK_CUOC_HD) {
//				return getListCurrBillCycleAll();
//			} else if (type == TYPE_INCT_HD) {
//				return getListPrintMethode();
//			} else if (type == TYPE_HTTBC_HD) {
//				return getListNoticeMethode();
//			} else if (type == TYPE_TTBS_HD) {
//				return getContractPrintList();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<Spin> result) {
//			// progress.dismiss();
//			if (errorCode.equalsIgnoreCase("0")) {
//				if (type == TYPE_HTTHHD) {
//
//					if (prbhttthd != null) {
//						prbhttthd.setVisibility(View.GONE);
//					}
//					if (btnhttthd != null) {
//						btnhttthd.setVisibility(View.VISIBLE);
//					}
//
//					arrHTTTHD = new ArrayList<>();
//					arrHTTTHD.addAll(result);
//					Utils.setDataSpinner(activity, result, spn_payMethodCode);
//					// AsyntaskGetListAllCommon asyntaskGetListAllCommon2 = new
//					// AsyntaskGetListAllCommon(
//					// activity, TYPE_LOAI_HD);
//					// asyntaskGetListAllCommon2.execute();
//
//					if (!isRefreshTYPE_HTTTHD) {
//						AsyntaskGetListAllCommon asyntaskGetListAllCommon2 = new AsyntaskGetListAllCommon(
//								activity, TYPE_LOAI_HD);
//						asyntaskGetListAllCommon2.execute();
//					}
//
//				}
//				if (type == TYPE_LOAI_HD) {
//
//					if (prbloaihopdong != null) {
//						prbloaihopdong.setVisibility(View.GONE);
//					}
//					if (btnloaihopdong != null) {
//						btnloaihopdong.setVisibility(View.VISIBLE);
//					}
//
//					arrLOAIHD = new ArrayList<>();
//					arrLOAIHD.addAll(result);
//					Utils.setDataSpinner(activity, result, spn_contractTypeCode);
//					// AsyntaskGetListAllCommonFist asyntaskGetListAllCommon3 =
//					// new AsyntaskGetListAllCommonFist(
//					// activity, TYPE_CK_CUOC_HD);
//					// asyntaskGetListAllCommon3.execute();
//					if (!isRefreshTYPE_LOAI_HD) {
//						AsyntaskGetListAllCommon asyntaskGetListAllCommon3 = new AsyntaskGetListAllCommon(
//								activity, TYPE_CK_CUOC_HD);
//						asyntaskGetListAllCommon3.execute();
//					}
//
//				}
//
//				if (type == TYPE_CK_CUOC_HD) {
//
//					if (prbchukicuoc != null) {
//						prbchukicuoc.setVisibility(View.GONE);
//					}
//					if (btnchukicuoc != null) {
//						btnchukicuoc.setVisibility(View.VISIBLE);
//					}
//
//					arrCKC = new ArrayList<>();
//					arrCKC.addAll(result);
//					Utils.setDataSpinner(activity, result,
//							spn_billCycleFromCharging);
//
//					// AsyntaskGetListAllCommonFist asyntaskGetListAllCommon4 =
//					// new AsyntaskGetListAllCommonFist(
//					// activity, TYPE_INCT_HD);
//					// asyntaskGetListAllCommon4.execute();
//					if (!isRefreshTYPE_CK_CUOC_HD) {
//						AsyntaskGetListAllCommon asyntaskGetListAllCommon4 = new AsyntaskGetListAllCommon(
//								activity, TYPE_INCT_HD);
//						asyntaskGetListAllCommon4.execute();
//					}
//				}
//				if (type == TYPE_INCT_HD) {
//
//					if (prbinchitiet != null) {
//						prbinchitiet.setVisibility(View.GONE);
//					}
//					if (btninchitiet != null) {
//						btninchitiet.setVisibility(View.VISIBLE);
//					}
//
//					arrINCT = new ArrayList<>();
//					arrINCT.addAll(result);
//					Utils.setDataSpinner(activity, result, spn_printMethodCode);
//					// TODO THINHHQ1 tbc
//					// AsyntaskGetListAllCommonFist asyntaskGetListAllCommon5 =
//					// new AsyntaskGetListAllCommonFist(
//					// activity, TYPE_HTTBC_HD);
//					// asyntaskGetListAllCommon5.execute();
//                    Boolean isRefreshTYPE_INCT_HD = false;
//                    if (!isRefreshTYPE_INCT_HD) {
//						AsyntaskGetListAllCommon asyntaskGetListAllCommon5 = new AsyntaskGetListAllCommon(
//								activity, TYPE_HTTBC_HD);
//						asyntaskGetListAllCommon5.execute();
//					}
//
//				}
//				if (type == TYPE_HTTBC_HD) {
//
//					if (prbhttbc != null) {
//						prbhttbc.setVisibility(View.GONE);
//					}
//					if (btnhttbc != null) {
//						btnhttbc.setVisibility(View.VISIBLE);
//					}
//
//					arrHTTBC = new ArrayList<>();
//					arrHTTBC.addAll(result);
//					Utils.setDataSpinner(activity, result, spn_noticeCharge);
//					// AsyntaskGetListAllCommonFist asyntaskGetListAllCommon6 =
//					// new AsyntaskGetListAllCommonFist(
//					// activity, TYPE_TTBS_HD);
//					// asyntaskGetListAllCommon6.execute();
//					if (!isRefreshTYPE_HTTBC_HD) {
//						AsyntaskGetListAllCommon asyntaskGetListAllCommon6 = new AsyntaskGetListAllCommon(
//								activity, TYPE_TTBS_HD);
//						asyntaskGetListAllCommon6.execute();
//					}
//				}
//
//				if (type == TYPE_TTBS_HD) {
//
//					if (prbttbosung != null) {
//						prbttbosung.setVisibility(View.GONE);
//					}
//					if (btnttbosung != null) {
//						btnttbosung.setVisibility(View.VISIBLE);
//					}
//
//					arrTTBSHD = new ArrayList<>();
//					arrTTBSHD.add(new Spin("", getString(R.string.txt_select)));
//					arrTTBSHD.addAll(result);
//
//					Utils.setDataSpinner(activity, arrTTBSHD, spn_contractPrint);
//					// GetListSubGroupAsyn getListSubGroupAsyn = new
//					// GetListSubGroupAsyn(
//					// activity);
//					// getListSubGroupAsyn.execute();
//				}
//
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					result = new ArrayList<>();
//					if (type == TYPE_HTTHHD) {
//						arrHTTTHD = new ArrayList<>();
//						arrHTTTHD.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_payMethodCode);
//					}
//					if (type == TYPE_LOAI_HD) {
//						arrLOAIHD = new ArrayList<>();
//						arrLOAIHD.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_contractTypeCode);
//					}
//
//					if (type == TYPE_CK_CUOC_HD) {
//						arrCKC = new ArrayList<>();
//						arrCKC.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_billCycleFromCharging);
//					}
//					if (type == TYPE_INCT_HD) {
//						arrINCT = new ArrayList<>();
//						arrINCT.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_printMethodCode);
//					}
//					if (type == TYPE_HTTBC_HD) {
//						arrHTTBC = new ArrayList<>();
//						arrHTTBC.addAll(result);
//						Utils.setDataSpinner(activity, result, spn_noticeCharge);
//					}
//
//					if (type == TYPE_TTBS_HD) {
//						arrTTBSHD = new ArrayList<>();
//						arrTTBSHD.add(new Spin("",
//								getString(R.string.txt_select)));
//						arrTTBSHD.addAll(result);
//
//						Utils.setDataSpinner(activity, arrTTBSHD,
//								spn_contractPrint);
//					}
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					result = new ArrayList<>();
//					if (type == TYPE_HTTHHD) {
//						arrHTTTHD = new ArrayList<>();
//						arrHTTTHD.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_payMethodCode);
//					}
//					if (type == TYPE_LOAI_HD) {
//						arrLOAIHD = new ArrayList<>();
//						arrLOAIHD.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_contractTypeCode);
//					}
//
//					if (type == TYPE_CK_CUOC_HD) {
//						arrCKC = new ArrayList<>();
//						arrCKC.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_billCycleFromCharging);
//					}
//					if (type == TYPE_INCT_HD) {
//						arrINCT = new ArrayList<>();
//						arrINCT.addAll(result);
//						Utils.setDataSpinner(activity, result,
//								spn_printMethodCode);
//					}
//					if (type == TYPE_HTTBC_HD) {
//						arrHTTBC = new ArrayList<>();
//						arrHTTBC.addAll(result);
//						Utils.setDataSpinner(activity, result, spn_noticeCharge);
//					}
//
//					if (type == TYPE_TTBS_HD) {
//						arrTTBSHD = new ArrayList<>();
//						arrTTBSHD.add(new Spin("",
//								getString(R.string.txt_select)));
//						arrTTBSHD.addAll(result);
//
//						Utils.setDataSpinner(activity, arrTTBSHD,
//								spn_contractPrint);
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
//		// lay danh sach hinh thuc thanh toan hop dong
//		private ArrayList<Spin> getListPayMethode() {
//			ArrayList<Spin> lstpaymethod = new ArrayList<>();
//			String original = "";
//			try {
//				lstpaymethod = new CacheDatabaseManager(context)
//						.getListInCache(TYPE_HTTHHD);
//				if (lstpaymethod != null && !lstpaymethod.isEmpty()) {
//					errorCode = "0";
//					return lstpaymethod;
//				}
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListPayMethode");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListPayMethode>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListPayMethode>");
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListPayMethode");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//
//				// parser
//				lstpaymethod = parserListGroup(original);
//				new CacheDatabaseManager(context).insertCacheList(TYPE_HTTHHD,
//						lstpaymethod);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstpaymethod;
//		}
//
//		// lay danh sach in chi tiet cuoc
//		private ArrayList<Spin> getListPrintMethode() {
//			ArrayList<Spin> lstprintmethod = new ArrayList<>();
//			String original = "";
//			try {
//				lstprintmethod = new CacheDatabaseManager(context)
//						.getListInCache(TYPE_INCT_HD);
//				if (lstprintmethod != null && !lstprintmethod.isEmpty()) {
//					errorCode = "0";
//					return lstprintmethod;
//				}
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListPrintMethode");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListPrintMethode>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListPrintMethode>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListPrintMethode");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//
//				// parser
//
//				lstprintmethod = parserListGroup(original);
//				new CacheDatabaseManager(context).insertCacheList(TYPE_INCT_HD,
//						lstprintmethod);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstprintmethod;
//		}
//
//		// lay danh sach hinh thuc thong bao cuoc
//		private ArrayList<Spin> getListNoticeMethode() {
//			ArrayList<Spin> lstNoticeMethod = new ArrayList<>();
//			String original = "";
//			try {
//				lstNoticeMethod = new CacheDatabaseManager(context)
//						.getListInCache(TYPE_HTTBC_HD);
//				if (lstNoticeMethod != null && !lstNoticeMethod.isEmpty()) {
//					errorCode = "0";
//					return lstNoticeMethod;
//				}
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListNoticeMethode");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListNoticeMethode>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListNoticeMethode>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListNoticeMethode");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//
//				// parser
//
//				lstNoticeMethod = parserListGroup(original);
//				new CacheDatabaseManager(context).insertCacheList(
//						TYPE_HTTBC_HD, lstNoticeMethod);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstNoticeMethod;
//		}
//
//		// lay danh sach chu ky cuoc
//		private ArrayList<Spin> getListCurrBillCycleAll() {
//			ArrayList<Spin> lstCurrBillCycle = new ArrayList<>();
//			String original = "";
//			try {
//				lstCurrBillCycle = new CacheDatabaseManager(context)
//						.getListInCache(TYPE_CK_CUOC_HD);
//				if (lstCurrBillCycle != null && !lstCurrBillCycle.isEmpty()) {
//					errorCode = "0";
//					return lstCurrBillCycle;
//				}
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getListCurrBillCycleAll");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListCurrBillCycleAll>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListCurrBillCycleAll>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListCurrBillCycleAll");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//
//				// parser
//
//				lstCurrBillCycle = parserBillCycle(original);
//				new CacheDatabaseManager(context).insertCacheList(
//						TYPE_CK_CUOC_HD, lstCurrBillCycle);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstCurrBillCycle;
//		}
//
//		// lay danh sach loai hop dong
//		private ArrayList<Spin> getListContractType() {
//			ArrayList<Spin> lstContractType = new ArrayList<>();
//			String original = "";
//			try {
//				lstContractType = new CacheDatabaseManager(context)
//						.getListInCache(TYPE_LOAI_HD);
//				if (lstContractType != null && !lstContractType.isEmpty()) {
//					errorCode = "0";
//					return lstContractType;
//				}
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getListContractType");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getListContractType>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getListContractType>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getListContractType");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//
//				// parser
//
//				lstContractType = parserListGroup(original);
//				new CacheDatabaseManager(context).insertCacheList(TYPE_LOAI_HD,
//						lstContractType);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstContractType;
//		}
//
//		// lay danh sach thong tin in bo sung
//		private ArrayList<Spin> getContractPrintList() {
//			ArrayList<Spin> lstprintmethod = new ArrayList<>();
//			String original = "";
//			try {
//				lstprintmethod = new CacheDatabaseManager(context)
//						.getListInCache(TYPE_TTBS_HD);
//				if (lstprintmethod != null && !lstprintmethod.isEmpty()) {
//					errorCode = "0";
//					return lstprintmethod;
//				}
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getContractPrintList");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getContractPrintList>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("</input>");
//				rawData.append("</ws:getContractPrintList>");
//
//				Log.i("LOG", "raw data" + rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("LOG", "Send evelop" + envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getContractPrintList");
//				Log.i("LOG", "Respone:  " + response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("LOG", "Responseeeeeeeeee Original  " + response);
//
//				// parser
//
//				lstprintmethod = parserListGroup(original);
//				new CacheDatabaseManager(context).insertCacheList(TYPE_TTBS_HD,
//						lstprintmethod);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstprintmethod;
//		}
//
//		public ArrayList<Spin> parserListGroup(String original) {
//			ArrayList<Spin> lstReason = new ArrayList<>();
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			NodeList nodechild = null;
//			NodeList nodeAppram = null;
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//				nodechild = doc.getElementsByTagName("lstAppDomains");
//				for (int j = 0; j < nodechild.getLength(); j++) {
//					Element e1 = (Element) nodechild.item(j);
//					Spin spin = new Spin();
//					spin.setValue(parse.getValue(e1, "name"));
//
//					nodeAppram = e1.getElementsByTagName("id");
//					for (int k = 0; k < nodeAppram.getLength(); k++) {
//						Element e3 = (Element) nodeAppram.item(k);
//
//						spin.setId(parse.getValue(e3, "code"));
//						if (spin != null && spin.getId() != null) {
//							Log.d("LOG", "value: " + spin.getId());
//						}
//					}
//
//					lstReason.add(spin);
//				}
//			}
//
//			return lstReason;
//		}
//
//		public ArrayList<Spin> parserBillCycle(String original) {
//			ArrayList<Spin> lstReason = new ArrayList<>();
//			Document doc = parse.getDomElement(original);
//			NodeList nl = doc.getElementsByTagName("return");
//			NodeList nodechild = null;
//			for (int i = 0; i < nl.getLength(); i++) {
//				Element e2 = (Element) nl.item(i);
//				errorCode = parse.getValue(e2, "errorCode");
//				description = parse.getValue(e2, "description");
//				Log.d("errorCode", errorCode);
//				nodechild = doc.getElementsByTagName("lstCurrBillCycles");
//				for (int j = 0; j < nodechild.getLength(); j++) {
//					Element e1 = (Element) nodechild.item(j);
//					Spin spin = new Spin();
//					spin.setValue(parse.getValue(e1, "billCycleFrom"));
//
//					spin.setId(parse.getValue(e1, "billCycleFrom"));
//
//					lstReason.add(spin);
//				}
//			}
//
//			return lstReason;
//		}
//	}
//
//	/**
//	 * lay quata cua dau noi
//	 */
//
//	private int isDeposit = -1;
//	private long quota = -1;
//	private long moneyPre = -1;
//
//	public class GetQuotaAsyn extends AsyncTask<String, Void, Integer> {
//
//		private ProgressDialog progress;
//		private Activity context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//
//		public GetQuotaAsyn(Activity context) {
//			this.context = context;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
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
//		protected Integer doInBackground(String... arg0) {
//			return getBusTypeMoneyPre(arg0[0]);
//		}
//
//		@Override
//		protected void onPostExecute(Integer _isDeposit) {
//
//			// TODO get hinh thuc hoa mang
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (_isDeposit == 0) {
//					String message = String.format(getString(R.string.qupta),
//							quota, moneyPre);
//					Dialog dialog0 = CommonActivity.createAlertDialog(activity,
//							message, getString(R.string.app_name));
//					dialog0.show();
//				} else if (_isDeposit == -1) {
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getString(R.string.not_quota_fail),
//							getString(R.string.app_name));
//					dialog.show();
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							getString(R.string.not_quota),
//							getString(R.string.app_name));
//					dialog.show();
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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
//		private Integer getBusTypeMoneyPre(String busType) {
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getBusTypeMoneyPre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getBusTypeMoneyPre>");
//				rawData.append("<input>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				rawData.append("<busType>").append(busType);
//				rawData.append("</busType>");
//				rawData.append("</input>");
//				rawData.append("</ws:getBusTypeMoneyPre>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getBusTypeMoneyPre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.d("original", original);
//
//				// ====== parse xml ===================
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//
//					isDeposit = Integer.parseInt(parse
//							.getValue(e2, "isDeposit"));
//					quota = Long.parseLong(parse.getValue(e2, "quota"));
//					moneyPre = Long.parseLong(parse.getValue(e2, "moneyPre"));
//
//					Log.d(Constant.TAG, "getBusTypeMoneyPre errorCode: "
//							+ errorCode + " description: " + description
//							+ " isDeposit: " + isDeposit + " quota: " + quota
//							+ " moneyPre: " + moneyPre);
//
//					// nodechild =
//					// doc.getElementsByTagName("smartphoneInfoResponseBO");
//					// if (nodechild.getLength() > 0) {
//					// Element e1 = (Element) nodechild.item(0);
//					//
//					// isDeposit = Integer.parseInt(parse.getValue(e1,
//					// "isDeposit"));
//					// quota = Long.parseLong(parse.getValue(e1, "quota"));
//					// moneyPre = Long.parseLong(parse.getValue(e1,
//					// "moneyPre"));
//					// }
//				}
//			} catch (Exception e) {
//				Log.d("getBusTypeMoneyPre", e.toString());
//			}
//			return isDeposit;
//		}
//
//	}
//
//	private LoadMoreListView lvListObjectCustomer;
//    private EditText edtCusCode;
//	private EditText edtCusName;
//
//	private ContractFormMngtBean searchBean;
//
//    private ContractFormMngtBean bankBean = null;
//
//	private final List<ContractFormMngtBean> lstContractFormMngtBean = new ArrayList<>();
//
//	private void showDialogListBank() {
//		final Dialog dialog = new Dialog(activity);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(R.layout.dialog_layout_object_bank);
//		lvListObjectCustomer = (LoadMoreListView) dialog
//				.findViewById(R.id.lvListObjectCustomer);
//        Button btnSearchCustomerObject = (Button) dialog.findViewById(R.id.btnSearch);
//        Button btnDeleteCustomerObject = (Button) dialog.findViewById(R.id.btnDelete);
//		edtCusCode = (EditText) dialog.findViewById(R.id.edtCodeCustomer);
//		edtCusName = (EditText) dialog.findViewById(R.id.edtNameCustomer);
//
//		lvListObjectCustomer
//				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,
//							int position, long id) {
//						bankBean = lstContractFormMngtBean.get(position);
//						edt_bankCode.setText(bankBean.getName());
//						dialog.dismiss();
//					}
//				});
//
//		lvListObjectCustomer
//				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
//
//					@Override
//					public void onLoadMore() {
//						// TODO Auto-generated method stub
//						Log.i(Constant.TAG, "lvListObjectCustomer onLoadMore");
//
//						// AsyncContractFormMngt asynctaskContractFotmMngt = new
//						// AsyncContractFormMngt(
//						// searchBean, lstContractFormMngtBean.size(),
//						// lstContractFormMngtBean.size() + Constant.PAGE_SIZE);
//						// asynctaskContractFotmMngt.execute();
//					}
//				});
//
//		btnSearchCustomerObject.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				CommonActivity.hideKeyboard(v, activity);
//				String customerName = edtCusName.getText().toString().trim();
//				String customerCode = edtCusCode.getText().toString().trim();
//
//				if (!CommonActivity.isNullOrEmpty(customerName)
//						|| !CommonActivity.isNullOrEmpty(customerCode)) {
//					if (CommonActivity.isNetworkConnected(activity)) {
//						searchBean = new ContractFormMngtBean();
//						searchBean.setCode(edtCusCode.getText().toString()
//								.trim());
//						searchBean.setName(edtCusName.getText().toString()
//								.trim());
//
//						lstContractFormMngtBean.clear();
//						AsyncBank asynctaskContractFotmMngt = new AsyncBank(
//								searchBean, lstContractFormMngtBean.size(),
//								lstContractFormMngtBean.size()
//										+ Constant.PAGE_SIZE);
//						asynctaskContractFotmMngt.execute();
//					} else {
//						CommonActivity.createAlertDialog(activity,
//								getString(R.string.errorNetwork),
//								getString(R.string.app_name)).show();
//					}
//				} else {
//					CommonActivity
//							.createAlertDialog(
//									activity,
//									getString(R.string.message_not_input_customer_name_or_name),
//									getString(R.string.app_name)).show();
//
//				}
//			}
//		});
//
//		btnDeleteCustomerObject.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				bankBean = null;
//				edt_bankCode.setText("");
//				dialog.dismiss();
//			}
//		});
//
//		dialog.show();
//
//	}
//
//	private class AsyncBank extends
//			AsyncTask<String, Void, List<ContractFormMngtBean>> {
//
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final ProgressDialog progress;
//		private final ContractFormMngtBean searchBean;
//
//		private final int pageIndex;
//		private final int pageSize;
//
//		public AsyncBank(ContractFormMngtBean _searchBean, int _pageIndex,
//				int _pageSize) {
//			this.searchBean = _searchBean;
//			this.pageIndex = _pageIndex;
//			this.pageSize = _pageSize;
//
//			this.progress = new ProgressDialog(activity);
//			this.progress.setCancelable(false);
//			this.progress.setMessage(activity.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//        @Override
//		protected List<ContractFormMngtBean> doInBackground(String... params) {
//
//			return getBankByCode();
//		}
//
//		@Override
//		protected void onPostExecute(List<ContractFormMngtBean> result) {
//			super.onPostExecute(result);
//
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				if (result != null && result.size() > 0) {
//					// them danh sach
//					lstContractFormMngtBean.addAll(result);
//                    CustomerObjectAdapter customerObjectAdapter = new CustomerObjectAdapter(activity,
//                            lstContractFormMngtBean);
//					lvListObjectCustomer.setAdapter(customerObjectAdapter);
//				} else {
//					if (lstContractFormMngtBean.isEmpty()) {
//						lvListObjectCustomer.setAdapter(null);
//						CommonActivity
//								.createAlertDialog(
//										activity,
//										getString(R.string.message_not_result_customer),
//										getString(R.string.app_name)).show();
//					}
//				}
//				Log.d(Constant.TAG,
//						"onPostExecute result.size(): " + result.size());
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(activity, description, activity
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = activity.getString(R.string.checkdes);
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
//		private List<ContractFormMngtBean> getBankByCode() {
//			List<ContractFormMngtBean> lstBank = new ArrayList<>();
//			String original = "";
//			try {
//
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_getBankByCode");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getBankByCode>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//
//				rawData.append("<bankCode>").append(searchBean.getCode()).append("</bankCode>");
//
//				rawData.append("<pageIndex>").append(pageIndex).append("</pageIndex>");
//				rawData.append("<pageSize>").append(pageSize).append("</pageSize>");
//
//				rawData.append("</input>");
//				rawData.append("</ws:getBankByCode>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity, "mbccs_getBankByCode");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Response Original group", response);
//
//				JSONObject jsonObject = null;
//				try {
//					jsonObject = XML.toJSONObject(original);
//					Log.i(Constant.TAG, jsonObject.toString());
//
//					if (jsonObject.has("lstBank")) {
//						Log.i(Constant.TAG, "lstBank Key Found");
//						JSONArray jsonArray = jsonObject
//								.getJSONArray("lstBank");
//						for (int i = 0; i < jsonArray.length(); i++) {
//							JSONObject obj = jsonArray.getJSONObject(i);
//						}
//					} else {
//						Log.i(Constant.TAG, "lstBank Key Not Found");
//					}
//				} catch (Exception e) {
//					Log.e(Constant.TAG, e.getMessage(), e);
//				}
//
//				// ==== parse xml list ip
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					Log.d(Constant.TAG, "getBankByCode errorCode: " + errorCode
//							+ " description: " + description);
//					nodechild = doc.getElementsByTagName("lstBank");
//
//					if (nodechild != null && nodechild.getLength() > 0) {
//						for (int j = 0; j < nodechild.getLength(); j++) {
//							Element e1 = (Element) nodechild.item(j);
//							ContractFormMngtBean obj = new ContractFormMngtBean();
//							obj.setCode(parse.getValue(e1, "bankCode"));
//							obj.setName(parse.getValue(e1, "name"));
//
//							lstBank.add(obj);
//						}
//					} else {
//						Log.d(Constant.TAG,
//								"getBankByCode nodechild lstBank NULL or EMPTY");
//					}
//				}
//
//			} catch (Exception e) {
//				Log.e("getBankByCode", e.toString(), e);
//			}
//			return lstBank;
//		}
//	}
//
//	// ===== webservice getCustomerTypeByCustGroupId === lay theo custGroupId
//	public class GetContractOfferByContractIdAsynTask extends
//			AsyncTask<String, Void, ArrayList<ContractOffersObj>> {
//
//		private ProgressDialog progress;
//		private Activity context = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//
//		public GetContractOfferByContractIdAsynTask(Activity context) {
//			this.context = context;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
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
//		protected ArrayList<ContractOffersObj> doInBackground(String... params) {
//			return sendRequestGetContractOfferByContractId(params[0]);
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<ContractOffersObj> result) {
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//				arrContractOffersObj = new ArrayList<>();
//				// if (result.size() > 0) {
//				arrContractOffersObj = result;
//
//				ContractOffersObj conOffersObj = new ContractOffersObj();
//				conOffersObj.setContractOfferId("");
//				conOffersObj.setContractOfferNo(getString(R.string.phulucmoi));
//				arrContractOffersObj.add(0, conOffersObj);
//
//				if (arrContractOffersObj != null
//						&& !arrContractOffersObj.isEmpty()) {
//					ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                            activity,
//                            android.R.layout.simple_dropdown_item_1line,
//                            android.R.id.text1);
//					for (ContractOffersObj contractOffersObj : arrContractOffersObj) {
//						adapter.add(contractOffersObj.getContractOfferNo());
//					}
//					spinner_plhopdong.setAdapter(adapter);
//
//					if (arrContractOffersObj.size() > 1) {
//						ln_contractOffer_Id_p.setVisibility(View.VISIBLE);
//					} else {
//						ln_contractOffer_Id_p.setVisibility(View.GONE);
//					}
//				}
//			} else {
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					Dialog dialog = CommonActivity
//							.createAlertDialog(context, description, context
//									.getResources()
//									.getString(R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.equals("")) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(context,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		// =====method get list service ========================
//		public ArrayList<ContractOffersObj> sendRequestGetContractOfferByContractId(
//				String strCosntractId) {
//			ArrayList<ContractOffersObj> listContractOffersObj = new ArrayList<>();
//
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getContractOfferByContractId");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getContractOfferByContractId>");
//				rawData.append("<input>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<contractId>").append(strCosntractId).append("</contractId>");
//				rawData.append("</input>");
//				rawData.append("</ws:getContractOfferByContractId>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, context,
//						"mbccs_getContractOfferByContractId");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Response Original", response);
//
//				// parser
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					Log.d("errorCode", errorCode);
//					nodechild = doc.getElementsByTagName("lstContractOffers");
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//						ContractOffersObj contractOffersObj = new ContractOffersObj();
//						contractOffersObj.setContractOfferId(parse.getValue(e1,
//								"contractOfferId"));
//						// represent
//						contractOffersObj.setContractOfferNo(parse.getValue(e1,
//								"contractOfferNo"));
//						contractOffersObj.setRepresent(parse.getValue(e1,
//								"represent"));
//						contractOffersObj.setStrSignDatetime(parse.getValue(e1,
//								"signDatetime"));
//						contractOffersObj.setStrIssueDatetime(parse.getValue(
//								e1, "issueDatetime"));
//						contractOffersObj.setRepresentPosition(parse.getValue(
//								e1, "representPosition"));
//						contractOffersObj.setIdType(parse
//								.getValue(e1, "idType"));
//						contractOffersObj.setIdNo(parse.getValue(e1, "idNo"));
//						contractOffersObj.setIssuePlace(parse.getValue(e1,
//								"issuePlace"));
//						listContractOffersObj.add(contractOffersObj);
//					}
//				}
//			} catch (Exception e) {
//				Log.d("getListIP", e.toString());
//			}
//			return listContractOffersObj;
//		}
//
//	}
//
//	private void hideBtnRefresh() {
//
//		prbloaihopdong.setVisibility(View.GONE);
//		prbhttthd.setVisibility(View.GONE);
//		prbchukicuoc.setVisibility(View.GONE);
//		prbinchitiet.setVisibility(View.GONE);
//		prbhttbc.setVisibility(View.GONE);
//		prbttbosung.setVisibility(View.GONE);
//	}
//
//	private void showBtnRefresh() {
//		// btnloaihopdong = (Button) dialog.findViewById(R.id.btnloaihopdong);
//		// btnhttthd = (Button) dialog.findViewById(R.id.btnhttthd);
//		// btnchukicuoc = (Button) dialog.findViewById(R.id.btnchukicuoc);
//		// btninchitiet = (Button) dialog.findViewById(R.id.btninchitiet);
//		// btnhttbc = (Button) dialog.findViewById(R.id.btnhttbc);
//		// btnttbosung = (Button) dialog.findViewById(R.id.btnttbosung);
//		btnloaihopdong.setVisibility(View.VISIBLE);
//		btnhttthd.setVisibility(View.VISIBLE);
//		btnchukicuoc.setVisibility(View.VISIBLE);
//		btninchitiet.setVisibility(View.VISIBLE);
//		btnhttbc.setVisibility(View.VISIBLE);
//		btnttbosung.setVisibility(View.VISIBLE);
//	}
//
//	// lay thong tin cuoc dong truoc
//	private class GetAllListPaymentPrePaidAsyn extends
//			AsyncTask<String, Void, ArrayList<PaymentPrePaidPromotionBeans>> {
//
//		private Context context = null;
//		final XmlDomParse parse = new XmlDomParse();
//		String errorCode = "";
//		String description = "";
//
//		public GetAllListPaymentPrePaidAsyn(Context context) {
//			this.context = context;
//
//			activity.findViewById(R.id.prbCuocdongtruoc).setVisibility(
//					View.VISIBLE);
//
//		}
//
//		@Override
//		protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(
//				String... arg0) {
//			return getAllListPaymentPrePaid(arg0[0], arg0[1], arg0[2], arg0[3]);
//		}
//
//		@Override
//		protected void onPostExecute(
//				ArrayList<PaymentPrePaidPromotionBeans> result) {
//
//			activity.findViewById(R.id.prbCuocdongtruoc).setVisibility(
//					View.GONE);
//			arrPaymentPrePaidPromotionBeans = new ArrayList<>();
//			if (errorCode.equals("0")) {
//
//				PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
//				paymentPrePaidPromotionBeans
//						.setName(getString(R.string.txt_select));
//				paymentPrePaidPromotionBeans.setPrePaidCode("");
//				result.add(0, paymentPrePaidPromotionBeans);
//				arrPaymentPrePaidPromotionBeans.addAll(result);
//				if (arrPaymentPrePaidPromotionBeans != null
//						&& !arrPaymentPrePaidPromotionBeans.isEmpty()) {
//					ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                            activity,
//                            android.R.layout.simple_dropdown_item_1line,
//                            android.R.id.text1);
//					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
//						adapter.add(typePaperBeans.getName());
//					}
//					spinner_cuocdongtruoc.setAdapter(adapter);
//				}
//				btn_connection.setVisibility(View.VISIBLE);
//			} else {
//				btn_connection.setVisibility(View.GONE);
//				if (errorCode.equals(Constant.INVALID_TOKEN)
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
//					ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                            activity,
//                            android.R.layout.simple_dropdown_item_1line,
//                            android.R.id.text1);
//					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
//						adapter.add(typePaperBeans.getName());
//					}
//					spinner_cuocdongtruoc.setAdapter(adapter);
//					if (description == null || description.isEmpty()) {
//						description = context.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//
//				}
//
//			}
//
//		}
//
//		private ArrayList<PaymentPrePaidPromotionBeans> getAllListPaymentPrePaid(
//				String promProgramCode, String packageId, String provinceCode,
//				String today) {
//			ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans = new ArrayList<>();
//			String original = null;
//			try {
//
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode",
//						"mbccs_getAllListPaymentPrePaid");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:getAllListPaymentPrePaid>");
//				rawData.append("<input>");
//				HashMap<String, String> paramToken = new HashMap<>();
//				paramToken.put("token", Session.getToken());
//				rawData.append(input.buildXML(paramToken));
//				rawData.append("<promProgramCode>").append(promProgramCode);
//				rawData.append("</promProgramCode>");
//
//				rawData.append("<packageId>").append(packageId);
//				rawData.append("</packageId>");
//
//				rawData.append("<type>" + "M");
//				rawData.append("</type>");
//
//				rawData.append("<today>").append(today);
//				rawData.append("</today>");
//
//				rawData.append("</input>");
//				rawData.append("</ws:getAllListPaymentPrePaid>");
//				Log.i("RowData", rawData.toString());
//
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_getAllListPaymentPrePaid");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Responseeeeeeeeee", original);
//
//				// parse xml
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodepaymentPrePaidPromotionBeans = null;
//				NodeList nodePaymentPrePaidDetailBeans = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					nodepaymentPrePaidPromotionBeans = e2
//							.getElementsByTagName("paymentPrePaidPromotionBeans");
//					for (int j = 0; j < nodepaymentPrePaidPromotionBeans
//							.getLength(); j++) {
//						Element e1 = (Element) nodepaymentPrePaidPromotionBeans
//								.item(j);
//						PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
//						String name = parse.getValue(e1, "name");
//						paymentPrePaidPromotionBeans.setName(name);
//
//						String prePaidCode = parse.getValue(e1, "prePaidCode");
//						paymentPrePaidPromotionBeans
//								.setPrePaidCode(prePaidCode);
//
//						ArrayList<PaymentPrePaidDetailBeans> lstPaymentPrePaidDetailBeans = new ArrayList<>();
//
//						nodePaymentPrePaidDetailBeans = e1
//								.getElementsByTagName("paymentPrePaidDetailBeans");
//						for (int k = 0; k < nodePaymentPrePaidDetailBeans
//								.getLength(); k++) {
//							Element e0 = (Element) nodePaymentPrePaidDetailBeans
//									.item(k);
//							PaymentPrePaidDetailBeans paymentPrePaidDetailBeans = new PaymentPrePaidDetailBeans();
//							String moneyUnit = parse.getValue(e0, "moneyUnit");
//							paymentPrePaidDetailBeans.setMoneyUnit(moneyUnit);
//							String promAmount = parse
//									.getValue(e0, "promAmount");
//							paymentPrePaidDetailBeans.setPromAmount(promAmount);
//							String endMonth = parse.getValue(e0, "endMonth");
//							paymentPrePaidDetailBeans.setEndMonth(endMonth);
//							String startMonth = parse
//									.getValue(e0, "startMonth");
//							paymentPrePaidDetailBeans.setStartMonth(startMonth);
//							String subMonth = parse.getValue(e0, "subMonth");
//							paymentPrePaidDetailBeans.setSubMonth(subMonth);
//							lstPaymentPrePaidDetailBeans
//									.add(paymentPrePaidDetailBeans);
//						}
//
//						paymentPrePaidPromotionBeans
//								.setLstDetailBeans(lstPaymentPrePaidDetailBeans);
//
//						lstPaymentPrePaidPromotionBeans
//								.add(paymentPrePaidPromotionBeans);
//					}
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return lstPaymentPrePaidPromotionBeans;
//		}
//
//	}
//
//	private Dialog dialogCuocdongtruoc = null;
//
//	private void showSelectCuocDongTruoc(
//			PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans) {
//
//		dialogCuocdongtruoc = new Dialog(activity);
//		dialogCuocdongtruoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogCuocdongtruoc
//				.setContentView(R.layout.connection_layout_detail_precharge);
//
//		ListView listdetail = (ListView) dialogCuocdongtruoc
//				.findViewById(R.id.listdetail);
//
//		EditText txttencuocdongtruoc = (EditText) dialogCuocdongtruoc
//				.findViewById(R.id.txttencuocdongtruoc);
//		txttencuocdongtruoc.setText(paymentPrePaidPromotionBeans.getName());
//
//		EditText txtmacuocdongtruoc = (EditText) dialogCuocdongtruoc
//				.findViewById(R.id.txtmacuocdongtruoc);
//		txtmacuocdongtruoc.setText(paymentPrePaidPromotionBeans
//				.getPrePaidCode());
//		GetListPaymentDetailChargeAdapter getListPaymentDetailChargeAdapter = new GetListPaymentDetailChargeAdapter(
//				paymentPrePaidPromotionBeans.getLstDetailBeans(), activity);
//		listdetail.setAdapter(getListPaymentDetailChargeAdapter);
//
//		Button btnchon = (Button) dialogCuocdongtruoc
//				.findViewById(R.id.btnchon);
//		btnchon.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				dialogCuocdongtruoc.dismiss();
//			}
//		});
//
//		dialogCuocdongtruoc.show();
//	}
//
//	private void showDialogViewCommitment(StockIsdnBean stockIsdnBean) {
//
//		final Dialog dialogViewCommitment = new Dialog(activity);
//		dialogViewCommitment.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogViewCommitment.setContentView(R.layout.dialog_layout_commitment);
//
//		TextView tvDialogTitle = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvDialogTitle);
//		tvDialogTitle.setText(getActivity().getString(R.string.ttincamketso));
//		TextView tvIsdn = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvIsdn);
//		tvIsdn.setText(stockIsdnBean.getIsdn());
//		TextView tvmoney = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvmoney);
//		tvmoney.setText(StringUtils.formatMoney(stockIsdnBean.getPrice()));
//		TextView tvStockModelCode = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvStockModelCode);
//		tvStockModelCode.setText(stockIsdnBean.getStockModelCode());
//
//		TextView tvStockmodelname = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvStockmodelname);
//		tvStockmodelname.setText(stockIsdnBean.getStockModelName());
//
//		TextView tvmoneycamket = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvmoneycamket);
//		tvmoneycamket.setText(StringUtils.formatMoney(stockIsdnBean
//				.getPricePledgeAmount()));
//
//		TextView tvmoneycamketungtruoc = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvmoneycamketungtruoc);
//		tvmoneycamketungtruoc.setText(StringUtils.formatMoney(stockIsdnBean
//				.getPricePriorPay()));
//
//		TextView tvcamketthang = (TextView) dialogViewCommitment
//				.findViewById(R.id.tvcamketthang);
//		tvcamketthang.setText(stockIsdnBean.getPricePledgeTime());
//
//		dialogViewCommitment.findViewById(R.id.btnclose).setOnClickListener(
//				new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						dialogViewCommitment.dismiss();
//
//					}
//				});
//
//		dialogViewCommitment.show();
//
//	}
//
//	private void showSelectViewStock(PriceBean priceBean, String serial) {
//
//		final Dialog dialogViewStock = new Dialog(activity);
//		dialogViewStock.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogViewStock.setContentView(R.layout.dialog_layout_commitment_stock);
//
//		TextView tvDialogTitle = (TextView) dialogViewStock
//				.findViewById(R.id.tvDialogTitle);
//		tvDialogTitle.setText(getActivity().getString(
//				R.string.ttincamketthietbi));
//
//		TextView tvSerial = (TextView) dialogViewStock
//				.findViewById(R.id.tvSerial);
//		tvSerial.setText(serial);
//
//		TextView tvMoney = (TextView) dialogViewStock
//				.findViewById(R.id.tvMoney);
//		tvMoney.setText(StringUtils.formatMoney(priceBean.getPrice()));
//
//		TextView tvmoneycamket = (TextView) dialogViewStock
//				.findViewById(R.id.tvmoneycamket);
//		tvmoneycamket.setText(StringUtils.formatMoney(priceBean
//				.getPledgeAmount()));
//
//		TextView tvmoneycamketungtruoc = (TextView) dialogViewStock
//				.findViewById(R.id.tvmoneycamketungtruoc);
//		tvmoneycamketungtruoc.setText(StringUtils.formatMoney(priceBean
//				.getPriorPay()));
//
//		TextView tvcamketthang = (TextView) dialogViewStock
//				.findViewById(R.id.tvcamketthang);
//		tvcamketthang.setText(priceBean.getPledgeTime());
//
//		dialogViewStock.findViewById(R.id.btnclose).setOnClickListener(
//				new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						dialogViewStock.dismiss();
//					}
//				});
//
//		dialogViewStock.show();
//	}
//
//	private class AsynctaskviewSubCommitmentPre extends
//			AsyncTask<String, Void, StockIsdnBean> {
//
//		private Activity mActivity = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final ProgressDialog progress;
//
//		public AsynctaskviewSubCommitmentPre(Activity mActivity) {
//			this.mActivity = mActivity;
//			this.progress = new ProgressDialog(mActivity);
//
//			this.progress.setCancelable(false);
//			this.progress.setMessage(mActivity.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected StockIsdnBean doInBackground(String... params) {
//			return viewSubCommitmentPre(params[0], params[1], params[2]);
//		}
//
//		@Override
//		protected void onPostExecute(StockIsdnBean result) {
//			super.onPostExecute(result);
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//
//				if (result != null
//						&& !CommonActivity.isNullOrEmpty(result.getIsdn())
//						&& !CommonActivity.isNullOrEmpty(result.getPrice())
//						&& !CommonActivity.isNullOrEmpty(result
//								.getPricePledgeAmount())) {
//
//					showDialogViewCommitment(result);
//
//					// showSelectViewStock(result, serial);
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							activity,
//							getActivity().getResources().getString(
//									R.string.khongcottincamket), getActivity()
//									.getResources()
//									.getString(R.string.app_name));
//					dialog.show();
//				}
//
//			} else {
//				Log.d("Log", "description error update" + description);
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							mActivity,
//							description,
//							mActivity.getResources().getString(
//									R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = mActivity.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private StockIsdnBean viewSubCommitmentPre(String offerId,
//				String regType, String isdn) {
//			StockIsdnBean stockIsdnBean = new StockIsdnBean();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_viewSubCommitmentPre");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:viewSubCommitmentPre>");
//				rawData.append("<cmMobileInput>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<offerId>").append(offerId).append("</offerId>");
//				rawData.append("<regType>").append(regType).append("</regType>");
//				rawData.append("<isdn>").append(isdn).append("</isdn>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:viewSubCommitmentPre>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_viewSubCommitmentPre");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Response Original", original);
//
//				// parser
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					Log.d("errorCode", errorCode);
//					nodechild = doc.getElementsByTagName("stockIsdnBeanPre");
//
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//
//						// <stockIsdnBean>
//						// <isdn>999000125</isdn>
//						// <isdnStatus>1</isdnStatus>
//						// <isdnType>1</isdnType>
//						// <price>5200000</price>
//						// <priceId>300387</priceId>
//						// <pricePledgeAmount>120000</pricePledgeAmount>
//						// <pricePledgeTime>8</pricePledgeTime>
//						// <pricePriorPay>500000</pricePriorPay>
//						// <priceVat>10</priceVat>
//						// <stockModelCode>M5200000</stockModelCode>
//						// <stockModelId>11834</stockModelId>
//						// <stockModelName>Số đẹp Mobile
//						// 5,200,000</stockModelName>
//						// </stockIsdnBean>
//
//						String isdn1 = parse.getValue(e1, "isdn");
//						stockIsdnBean.setIsdn(isdn1);
//
//						stockIsdnBean.setIsdnStatus(parse.getValue(e1,
//								"isdnStatus"));
//						stockIsdnBean.setIsdnType(parse
//								.getValue(e1, "isdnType"));
//						stockIsdnBean.setPrice(parse.getValue(e1, "price"));
//						stockIsdnBean.setPriceId(parse.getValue(e1, "priceId"));
//						stockIsdnBean.setPricePledgeAmount(parse.getValue(e1,
//								"pricePledgeAmount"));
//						stockIsdnBean.setPricePledgeTime(parse.getValue(e1,
//								"pricePledgeTime"));
//						stockIsdnBean.setPricePriorPay(parse.getValue(e1,
//								"pricePriorPay"));
//
//						stockIsdnBean.setPriceVat(parse
//								.getValue(e1, "priceVat"));
//						stockIsdnBean.setStockModelCode(parse.getValue(e1,
//								"stockModelCode"));
//						stockIsdnBean.setStockModelId(parse.getValue(e1,
//								"stockModelId"));
//						stockIsdnBean.setStockModelName(parse.getValue(e1,
//								"stockModelName"));
//					}
//				}
//			} catch (Exception e) {
//				Log.d("viewSubCommitmentStock", e.toString()
//						+ "description error", e);
//			}
//
//			return stockIsdnBean;
//
//		}
//
//	}
//
//	private class AsynctaskviewSubCommitmentPos extends
//			AsyncTask<String, Void, StockIsdnBean> {
//
//		private Activity mActivity = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final ProgressDialog progress;
//
//		public AsynctaskviewSubCommitmentPos(Activity mActivity) {
//			this.mActivity = mActivity;
//			this.progress = new ProgressDialog(mActivity);
//
//			this.progress.setCancelable(false);
//			this.progress.setMessage(mActivity.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected StockIsdnBean doInBackground(String... params) {
//			return viewSubCommitmentPos(params[0], params[1], params[2]);
//		}
//
//		@Override
//		protected void onPostExecute(StockIsdnBean result) {
//			super.onPostExecute(result);
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//
//				if (result != null
//						&& !CommonActivity.isNullOrEmpty(result.getIsdn())
//						&& !CommonActivity.isNullOrEmpty(result.getPrice())
//						&& !CommonActivity.isNullOrEmpty(result
//								.getPricePledgeAmount())) {
//
//					showDialogViewCommitment(result);
//
//					// showSelectViewStock(result, serial);
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							activity,
//							getActivity().getResources().getString(
//									R.string.khongcottincamket), getActivity()
//									.getResources()
//									.getString(R.string.app_name));
//					dialog.show();
//				}
//
//			} else {
//				Log.d("Log", "description error update" + description);
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							mActivity,
//							description,
//							mActivity.getResources().getString(
//									R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = mActivity.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private StockIsdnBean viewSubCommitmentPos(String offerId,
//				String regType, String isdn) {
//			StockIsdnBean stockIsdnBean = new StockIsdnBean();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_viewSubCommitment");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:viewSubCommitment>");
//				rawData.append("<cmMobileInput>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<offerId>").append(offerId).append("</offerId>");
//				rawData.append("<regType>").append(regType).append("</regType>");
//				rawData.append("<isdn>").append(isdn).append("</isdn>");
//
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:viewSubCommitment>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_viewSubCommitment");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Response Original", original);
//
//				// parser
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					Log.d("errorCode", errorCode);
//					nodechild = doc.getElementsByTagName("stockIsdnBeanPos");
//
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//
//						// <stockIsdnBean>
//						// <isdn>999000125</isdn>
//						// <isdnStatus>1</isdnStatus>
//						// <isdnType>1</isdnType>
//						// <price>5200000</price>
//						// <priceId>300387</priceId>
//						// <pricePledgeAmount>120000</pricePledgeAmount>
//						// <pricePledgeTime>8</pricePledgeTime>
//						// <pricePriorPay>500000</pricePriorPay>
//						// <priceVat>10</priceVat>
//						// <stockModelCode>M5200000</stockModelCode>
//						// <stockModelId>11834</stockModelId>
//						// <stockModelName>Số đẹp Mobile
//						// 5,200,000</stockModelName>
//						// </stockIsdnBean>
//
//						String isdn1 = parse.getValue(e1, "isdn");
//						stockIsdnBean.setIsdn(isdn1);
//
//						stockIsdnBean.setIsdnStatus(parse.getValue(e1,
//								"isdnStatus"));
//						stockIsdnBean.setIsdnType(parse
//								.getValue(e1, "isdnType"));
//						stockIsdnBean.setPrice(parse.getValue(e1, "price"));
//						stockIsdnBean.setPriceId(parse.getValue(e1, "priceId"));
//						stockIsdnBean.setPricePledgeAmount(parse.getValue(e1,
//								"pricePledgeAmount"));
//						stockIsdnBean.setPricePledgeTime(parse.getValue(e1,
//								"pricePledgeTime"));
//						stockIsdnBean.setPricePriorPay(parse.getValue(e1,
//								"pricePriorPay"));
//
//						stockIsdnBean.setPriceVat(parse
//								.getValue(e1, "priceVat"));
//						stockIsdnBean.setStockModelCode(parse.getValue(e1,
//								"stockModelCode"));
//						stockIsdnBean.setStockModelId(parse.getValue(e1,
//								"stockModelId"));
//						stockIsdnBean.setStockModelName(parse.getValue(e1,
//								"stockModelName"));
//					}
//				}
//			} catch (Exception e) {
//				Log.d("viewSubCommitmentStock", e.toString()
//						+ "description error", e);
//			}
//
//			return stockIsdnBean;
//
//		}
//
//	}
//
//	private class AsynctaskviewSubCommitmentStock extends
//			AsyncTask<String, Void, PriceBean> {
//
//		private Activity mActivity = null;
//		private final XmlDomParse parse = new XmlDomParse();
//		private String errorCode = "";
//		private String description = "";
//		private final ProgressDialog progress;
//		private String serial = "";
//
//		public AsynctaskviewSubCommitmentStock(Activity mActivity, String serial) {
//			this.mActivity = mActivity;
//			this.progress = new ProgressDialog(mActivity);
//			this.serial = serial;
//			this.progress.setCancelable(false);
//			this.progress.setMessage(mActivity.getResources().getString(
//					R.string.getdataing));
//			if (!this.progress.isShowing()) {
//				this.progress.show();
//			}
//		}
//
//		@Override
//		protected PriceBean doInBackground(String... params) {
//			return viewSubCommitmentStock(params[0], params[1], params[2],
//					params[3], params[4]);
//		}
//
//		@Override
//		protected void onPostExecute(PriceBean result) {
//			super.onPostExecute(result);
//			progress.dismiss();
//			if (errorCode.equals("0")) {
//
//				if (result != null
//						&& !CommonActivity.isNullOrEmpty(result.getPrice())
//						&& !CommonActivity.isNullOrEmpty(result
//								.getPledgeAmount())
//						&& !CommonActivity.isNullOrEmpty(result.getPriorPay())) {
//					showSelectViewStock(result, serial);
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							activity,
//							getActivity().getResources().getString(
//									R.string.khongcottincamket), getActivity()
//									.getResources()
//									.getString(R.string.app_name));
//					dialog.show();
//				}
//
//			} else {
//				Log.d("Log", "description error update" + description);
//				if (errorCode.equals(Constant.INVALID_TOKEN)) {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							mActivity,
//							description,
//							mActivity.getResources().getString(
//									R.string.app_name), moveLogInAct);
//					dialog.show();
//				} else {
//					if (description == null || description.isEmpty()) {
//						description = mActivity.getString(R.string.checkdes);
//					}
//					Dialog dialog = CommonActivity.createAlertDialog(activity,
//							description,
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
//			}
//		}
//
//		private PriceBean viewSubCommitmentStock(String offerId,
//				String regType, String isdn, String serial, String stockModelId) {
//			PriceBean priceBean = new PriceBean();
//			String original = "";
//			try {
//				BCCSGateway input = new BCCSGateway();
//				input.addValidateGateway("username", Constant.BCCSGW_USER);
//				input.addValidateGateway("password", Constant.BCCSGW_PASS);
//				input.addValidateGateway("wscode", "mbccs_viewSubCommitment");
//				StringBuilder rawData = new StringBuilder();
//				rawData.append("<ws:viewSubCommitment>");
//				rawData.append("<cmMobileInput>");
//				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<offerId>").append(offerId).append("</offerId>");
//				rawData.append("<regType>").append(regType).append("</regType>");
//				rawData.append("<isdn>").append(isdn).append("</isdn>");
//				rawData.append("<serial>").append(serial).append("</serial>");
//				rawData.append("<stockModelId>").append(stockModelId).append("</stockModelId>");
//				rawData.append("</cmMobileInput>");
//				rawData.append("</ws:viewSubCommitment>");
//				Log.i("RowData", rawData.toString());
//				String envelope = input.buildInputGatewayWithRawData(rawData
//						.toString());
//				Log.d("Send evelop", envelope);
//				Log.i("LOG", Constant.BCCS_GW_URL);
//				String response = input.sendRequest(envelope,
//						Constant.BCCS_GW_URL, activity,
//						"mbccs_viewSubCommitment");
//				Log.i("Responseeeeeeeeee", response);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				Log.i("Response Original", original);
//
//				// parser
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				NodeList nodechild = null;
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					description = parse.getValue(e2, "description");
//					Log.d("errorCode", errorCode);
//					nodechild = doc.getElementsByTagName("priceBean");
//
//					for (int j = 0; j < nodechild.getLength(); j++) {
//						Element e1 = (Element) nodechild.item(j);
//
//						// <pledgeAmount>550000</pledgeAmount>
//						// <pledgeTime>18</pledgeTime>
//						// <price>4950000</price>
//						// <priceId>500553</priceId>
//						// <priorPay>9000000</priorPay>
//						// <stockModelId>21223</stockModelId>
//						// <vat>10</vat>
//
//						String pledgeAmount = parse
//								.getValue(e1, "pledgeAmount");
//						priceBean.setPledgeAmount(pledgeAmount);
//
//						String pledgeTime = parse.getValue(e1, "pledgeTime");
//						priceBean.setPledgeTime(pledgeTime);
//
//						String price = parse.getValue(e1, "price");
//						priceBean.setPrice(price);
//
//						String priceId = parse.getValue(e1, "priceId");
//						priceBean.setPriceId(priceId);
//
//						String priorPay = parse.getValue(e1, "priorPay");
//						priceBean.setPriorPay(priorPay);
//
//						String stockModelId1 = parse.getValue(e1,
//								"stockModelId");
//						priceBean.setStockModelId(stockModelId1);
//
//						String vat = parse.getValue(e1, "vat");
//						priceBean.setVat(vat);
//
//					}
//				}
//			} catch (Exception e) {
//				Log.d("viewSubCommitmentStock", e.toString()
//						+ "description error", e);
//			}
//
//			return priceBean;
//
//		}
//
//	}

}
