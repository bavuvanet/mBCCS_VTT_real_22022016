package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetProductAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter.ViewHolder;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ConTractBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.PakageBundeBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.StockTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.StockTypeDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.GetContractHandler;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.HTHMMobileHandler;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.HTHMPREMobileHandler;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.PakageHanlder;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.StockTypeHanlderPos;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.StockTypeHanlderPre;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManageConnect;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetPstnAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PakageChargeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PriceBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.StockIsdnBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetSobaodanhAdapter;
import com.viettel.bss.viettelpos.v4.customer.manage.AsyncTaskUpdateImageOfline;
import com.viettel.bss.viettelpos.v4.customer.object.StudentBean;
import com.viettel.bss.viettelpos.v4.customview.adapter.DoiTuongObj;
import com.viettel.bss.viettelpos.v4.customview.adapter.DonViAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.ObjDoiTuongAdapter;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.customview.obj.SpecObject;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentActiveAccountPayment;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.picker.Config;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentConnectionMobileSmartSimConnect extends Fragment implements
		OnClickListener, OnItemClickListener {

	private final String logTag = FragmentConnectionMobileSmartSimConnect.class
			.getName();

	// define resource view
	// private Spinner spinner_province, spinner_quanhuyen, spinner_phuongxa,

	private EditText edtprovince, edtdistrict, edtprecinct;

	private Spinner spinner_loaithuebao, spinner_loaihd;
	private Spinner spinner_serviceMobile;
	private EditText txtisdn, txtserial, txtimsi, txtidcuahang, txtmanv,
			txtduong, txtsonha;
	private ExpandableHeightListView lvproduct;
	private LinearLayout lnkhuyenmai, lnloaithuebao, lnhopdong, lnsonha;
	private static LinearLayout lnButton;
    private TextView txtpakage;
	// get data service
	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<>();
	private String serviceType = "";
	private String telecomserviceId = "";
	private ArrayAdapter<String> adapterService = null;
	// arraylist pakage
	private ArrayList<PakageChargeBeans> arrPakageChargeBeans = new ArrayList<>();
	private String offerId = "";
	private String productCode = "";
	// arrlist HTHM
	private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<>();
	private String hthm = "";
	// arrlist htkm
	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans = new ArrayList<>();
	private ArrayAdapter<String> adapterHTKM;
	private String maKM = "";
	// arrlist province
	private ArrayList<AreaBean> arrProvince = new ArrayList<>();
	private String province = "";
	// arrlist district
	private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
	private String district = "";
	// arrlist precinct
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
	private String precinct = "";

	private ArrayList<SubTypeBeans> arrSubTypeBeans = new ArrayList<>();
	private String subTypeMobile = "";
	// ====== arrlist ========
	private ArrayList<ConTractBean> arrTractBeans = new ArrayList<>();
	private String contractNo = "";
	private String contractId = "";
	// arrlist ds hang hoa
	private final ArrayList<StockTypeBeans> arrStockTypeBeans = new ArrayList<>();
	private GetProductAdapter adapProductAdapter;

	// ==========map hang hoa =================
	private final Map<String, StockTypeDetailBeans> mapSubStockModelRelReq = new HashMap<>();

	// ======== show dialog insert Serial
	private Dialog dialogInsertSerial;
	private Dialog dialogShowDetailProduct;
	private EditText edtserial;
	private Bundle mBundle;
	private String subType = "";

	private PakageBundeBean pakageBundeBean = new PakageBundeBean();
    private PakageChargeBeans pakaChargeBeans = null;
	private CustommerByIdNoBean cusByIdNoBean = new CustommerByIdNoBean();
	public static CustommerByIdNoBean cusByIdNoBeancheck = new CustommerByIdNoBean();
	public static String subTypeCheck = "";
	private int positonservice = -1;
	private String isdn = "";

	private String tenduong = "";
	private String sonha = "";
	private String to = "";

	// define view form specical paper
	private LinearLayout lnTTGoiCuocDacBiet;
    private Spinner spDoiTuong;
    private EditText tvDonVi;
    private EditText edtMaGiayToDacBiet;
    private EditText edtNgayBD;
    private EditText edtNgayKT;

	private ArrayList<SpecObject> arrSpecObjects = new ArrayList<>();
	private ObjDoiTuongAdapter doiTuongAdapter;
	private String mCodeDoiTuong = "";

	// define time ngay bat dau va ngay het thuc
	private Calendar cal;
	private int day;
	private int month;
	private int year;

	private String mNgayBatDau = "";
	private String mNgayKetThuc = "";

	private String mCodeDonVi = "";
	private RelativeLayout rlchondonvi;

	private ArrayList<DoiTuongObj> mAratListDonVi = new ArrayList<>();
	private ListView lvListDonVi;
	private Dialog dialogDonVi = null;

	private final BCCSGateway mBccsGateway = new BCCSGateway();

    private Date dateBD = null;
	private Date dateNow = null;

	private View imgDeleteDV;

	private Dialog dialogSearchIsdn = null;
	private ListView lvsodienthoai;
	private GetPstnAdapter adapterIsdn = null;
	private ArrayList<String> arrIsdn = new ArrayList<>();

    private String objectCode = "";
	private Staff staff = null;

	private Spinner spinner_to;
	private ArrayList<AreaObj> arrTo = new ArrayList<>();

	private ListView lvUploadImage;
	private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
	private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;

	boolean isUpImage = false;

	private String reasonId;

	private Activity activity;

	private Date timeStart = null;
	private Date timeEnd = null;
	private EditText edt_hthm, edt_kmai;
    private ArrayList<FileObj> arrFileBackUp;

    private Button btnRefreshStreetBlock;
	private  View mView;
	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
        String dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
		try {
			dateNow = new Date(dateNowString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		if (pakaChargeBeans != null) {
			pakaChargeBeans = new PakageChargeBeans();
		}

		// TODO XU LY GET BUNDLE TU CHO NAY
		// cusByIdNoBean =
		// FragmentInfoCustomerMobileSmartSim.custommerByIdNoBean;
		// subType = FragmentInfoCustomerMobileSmartSim.subType;
		getdataBundle();
		if(mView == null){
			mView = inflater.inflate(
					R.layout.connectionmobile_layout_info_smartsim, container,
					false);
			unitView(mView);
		}

		return mView;
	}

	private void getdataBundle() {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			Log.i("Check", "Co du lieu");
			cusByIdNoBean = (CustommerByIdNoBean) mBundle
					.getSerializable("custommerKey");
			subType = mBundle.getString("subTypeKey", "");
			objectCode = mBundle.getString("objectCodeKey", objectCode);
			staff = (Staff) mBundle.getSerializable("staffKey");
			if (staff != null) {

				cusByIdNoBean.setProvince(staff.getProvince());
				cusByIdNoBean.setDistrict(staff.getDistrict());
				cusByIdNoBean.setPrecint(staff.getPrecinct());
				cusByIdNoBean.setAddreseCus(staff.getAddress());
				cusByIdNoBean.setStreet_block(staff.getStreet_block());
				cusByIdNoBean.setStreet(staff.getStreet());
				cusByIdNoBean.setHome(staff.getHome());
			}

		}
	}

	@Override
	public void onResume() {
		Log.e(logTag, "onResume");
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.daunoismartsim);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {

			case 3333:
				if (data != null) {
					pakaChargeBeans = (PakageChargeBeans) data.getExtras()
							.getSerializable("pakageChargeKey");
					if (pakaChargeBeans != null) {
						txtpakage.setText(Html.fromHtml("<u>"
								+ pakaChargeBeans.getOfferName() + "</u>"));
						offerId = pakaChargeBeans.getOfferId();
						productCode = pakaChargeBeans.getProductCode();
						if ("1".equals(subType)) {
							if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
								arrHthmBeans.clear();
							}

							if (arrSpecObjects != null
									&& !arrSpecObjects.isEmpty()) {
								arrSpecObjects.clear();
							}
							// lay hinh thuc hoa mang tra truoc
							if (CommonActivity
                                    .isNetworkConnected(getActivity())) {
								if (offerId != null && !offerId.isEmpty()) {

									// TODO LAY THONG TIN GOI CUOC
									if (offerId
											.equalsIgnoreCase(Constant.OFFERID_TSV)) {
										GetProductSpecInfoPreAsyn getInfoPreAsyn = new GetProductSpecInfoPreAsyn(
												getActivity());
										getInfoPreAsyn.execute(offerId,
												cusByIdNoBean.getIdNo());
									} else {
										spDoiTuong.setEnabled(true);
										mCodeDoiTuong = "";
										mCodeDonVi = "";
										lnTTGoiCuocDacBiet
												.setVisibility(View.GONE);
										if (arrSpecObjects != null
												&& !arrSpecObjects.isEmpty()) {
											arrSpecObjects.clear();
										}
										if (doiTuongAdapter != null) {
											doiTuongAdapter
													.notifyDataSetChanged();
										}
									}
									GetHTHMPREAsyn getHTHMPREAsyn = new GetHTHMPREAsyn(
											getActivity());
									getHTHMPREAsyn
											.execute(serviceType, offerId);
								}
							} else {
								CommonActivity.createAlertDialog(getActivity(),
										getString(R.string.errorNetwork),
										getString(R.string.app_name)).show();
							}
						} else {
							// lay hthm tra sau
							if (CommonActivity
                                    .isNetworkConnected(getActivity())) {
								if (arrHthmBeans != null
										&& arrHthmBeans.size() > 0) {
									arrHthmBeans.clear();
								}
								GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(
										getActivity());
								getHTHMAsyn.execute(serviceType, offerId);
								if (arrSubTypeBeans != null
										&& arrSubTypeBeans.size() > 0) {
									arrSubTypeBeans.clear();
								}
								GetListSubTypeAsyn getListSubTypeAsyn = new GetListSubTypeAsyn(
										getActivity());
								getListSubTypeAsyn.execute(serviceType,
										productCode);
							} else {
								CommonActivity.createAlertDialog(getActivity(),
										getString(R.string.errorNetwork),
										getString(R.string.app_name)).show();
							}

						}

					}
				}
				break;
			case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
				Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");

				Parcelable[] parcelableUris = data
						.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

				if (parcelableUris == null) {
					return;
				}
				// Java doesn't allow array casting, this is a little hack
				Uri[] uris = new Uri[parcelableUris.length];
				System.arraycopy(parcelableUris, 0, uris, 0,
						parcelableUris.length);

				int imageId = data.getExtras().getInt("imageId", -1);

				Log.d(Constant.TAG,
						"FragmentManageConnect onActivityResult() imageId: "
								+ imageId);

				if (uris != null && uris.length > 0 && imageId >= 0) {
					ViewHolder holder = null;
					for (int i = 0; i < lvUploadImage.getChildCount(); i++) {
						View rowView = lvUploadImage.getChildAt(i);
						ViewHolder h = (ViewHolder) rowView.getTag();
						if (h != null) {
							int id = h.ibUploadImage.getId();
							if (imageId == id) {
								holder = h;
								break;
							}
						}
					}
					if (holder != null) {
						Picasso.with(activity)
								.load(new File(uris[0].toString()))
								.centerCrop().resize(100, 100)
								.into(holder.ibUploadImage);

						int position = holder.spUploadImage
								.getSelectedItemPosition();

						if (position < 0) {
							position = 0;
						}

						ArrayList<RecordPrepaid> recordPrepaids = mapListRecordPrepaid
								.get(String.valueOf(imageId));

						if (recordPrepaids != null) {
							RecordPrepaid recordPrepaid = recordPrepaids
									.get(position);

							String spinnerCode = recordPrepaid.getCode();

							Log.i(Constant.TAG, "imageId: " + imageId
									+ " spinner position: " + position
									+ " spinnerCode: " + spinnerCode
									+ " uris: " + uris.length);

							ArrayList<FileObj> fileObjs = new ArrayList<>();
							for (int i = 0; i < uris.length; i++) {
								File uriFile = new File(uris[i].getPath());
								String fileNameServer = spinnerCode + "-"
										+ (i + 1) + ".jpg";
								FileObj obj = new FileObj(spinnerCode, uriFile,
										uris[i].getPath(), fileNameServer);
								fileObjs.add(obj);
							}
							hashmapFileObj.put(String.valueOf(imageId),
									fileObjs);
						} else {
							Log.d(Constant.TAG,
									"FragmentManageConnect onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
											+ mapListRecordPrepaid.size()
											+ " "
											+ mapListRecordPrepaid);
						}
					} else {
						Log.d(Constant.TAG,
								"FragmentManageConnect onActivityResult() holder NULL");
					}
				}
				break;

			case 101:
                HTHMBeans hthmBeans = (HTHMBeans) data.getExtras().getSerializable(
                        "HTHMBeans");
				reloadDataHTHM(hthmBeans);
				break;
			case 102:
				PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans) data
						.getExtras().getSerializable("PromotionTypeBeans");

				if (promotionTypeBeans.getName().equalsIgnoreCase(
						getString(R.string.selectMKM))) {
					maKM = "";
					edt_kmai.setText(getString(R.string.selectMKM));
				} else {
					edt_kmai.setText(promotionTypeBeans.getName());
					maKM = promotionTypeBeans.getPromProgramCode();
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
				}
				break;

			case 108:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras()
							.getSerializable("precinctKey");
					precinct = areaBean.getPrecinct();
					edtprecinct.setText(areaBean.getNamePrecint());

					if (arrTo != null && arrTo.size() > 0) {
						arrTo.clear();
					}
					if (province != null && district != null
							&& precinct != null) {
						new GetListGroupAdressAsyncTask(getActivity())
								.execute(province + district + precinct);
					}

				}
				break;

			default:
				break;
			}
		}

	}

	private void reloadDataHTHM(HTHMBeans hthmBeans) {
		if (hthmBeans.getName().equalsIgnoreCase(
				getResources().getString(R.string.chonhthm))) {

			edt_hthm.setText(getString(R.string.chonhthm));

			hthm = "";
			reasonId = "";
			if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0
					&& adapProductAdapter != null) {
				arrStockTypeBeans.clear();
				adapProductAdapter = new GetProductAdapter(arrStockTypeBeans,
						getActivity());
				lvproduct.setAdapter(adapProductAdapter);
				adapProductAdapter.notifyDataSetChanged();
			}
			if (mapSubStockModelRelReq != null
					&& mapSubStockModelRelReq.size() > 0) {
				mapSubStockModelRelReq.clear();
			}
			maKM = "";
			if (arrPromotionTypeBeans != null
					&& arrPromotionTypeBeans.size() > 0) {
				arrPromotionTypeBeans.clear();
				initInitListPromotionNotData();
			}

		} else {

			edt_hthm.setText(hthmBeans.toString());

			if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0) {
				arrStockTypeBeans.clear();
			}
			if (mapSubStockModelRelReq != null
					&& mapSubStockModelRelReq.size() > 0) {
				mapSubStockModelRelReq.clear();
			}

			if (arrPromotionTypeBeans != null
					&& arrPromotionTypeBeans.size() > 0) {
				arrPromotionTypeBeans.clear();
			}

			hthm = hthmBeans.getCode();
			reasonId = hthmBeans.getReasonId();

			if (serviceType != null && !serviceType.isEmpty()) {
				if ("1".equalsIgnoreCase(subType)) {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetListProductPreAsyn getListProductPreAsyn = new GetListProductPreAsyn(
								getActivity());
						getListProductPreAsyn.execute(hthm, productCode,
								serviceType);

						// if (mapListRecordPrepaid != null
						// && !mapListRecordPrepaid.isEmpty()) {
						// mapListRecordPrepaid.clear();
						// }
						if (adapProductAdapter != null) {
							adapProductAdapter.notifyDataSetChanged();
						}

						GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask(
								"" + reasonId, productCode, getActivity());
						getLisRecordPrepaidTask.execute();

					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}

				} else {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						if (arrPromotionTypeBeans != null
								&& arrPromotionTypeBeans.size() > 0) {
							arrPromotionTypeBeans.clear();
						}
						GetListProductPosAsyn getListProductPosAsyn = new GetListProductPosAsyn(
								getActivity());
						getListProductPosAsyn.execute(hthm, productCode,
								serviceType);

						GetPromotionByMap getPromotionByMap = new GetPromotionByMap(
								getActivity());
						getPromotionByMap.execute(serviceType, hthm, offerId,
								province, district, precinct);

						// if (mapListRecordPrepaid != null
						// && !mapListRecordPrepaid.isEmpty()) {
						// mapListRecordPrepaid.clear();
						// }
						if (adapProductAdapter != null) {
							adapProductAdapter.notifyDataSetChanged();
						}

						GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask(
								"" + reasonId, productCode, getActivity());
						getLisRecordPrepaidTask.execute();
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}

				}
			}
		}
	}

	private void unitView(View v) {

		// bo sung cam ket
        TextView tvcamketso = (TextView) v.findViewById(R.id.tvcamketso);
		tvcamketso.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CommonActivity.isNetworkConnected(getActivity())) {

					if (validateViewCommitment()) {
						if ("1".equals(subType)) {
							AsynctaskviewSubCommitmentPre asynctaskviewSubCommitmentPre = new AsynctaskviewSubCommitmentPre(
									getActivity());
							asynctaskviewSubCommitmentPre.execute(offerId,
									hthm, txtisdn.getText().toString().trim());
						} else {
							AsynctaskviewSubCommitmentPos asynctaskviewSubCommitmentPos = new AsynctaskviewSubCommitmentPos(
									getActivity());
							asynctaskviewSubCommitmentPos.execute(offerId,
									hthm, txtisdn.getText().toString().trim());
						}
					}
				}
			}
		});

		spinner_serviceMobile = (Spinner) v.findViewById(R.id.spinner_service);
		txtpakage = (TextView) v.findViewById(R.id.tvpakage);

		txtpakage.setOnClickListener(this);
		// ====TOSV1
		txtpakage.setText(Html.fromHtml("<u>"
				+ getString(R.string.chonpakage_mobile) + "</u>"));
		edt_hthm = (EditText) v.findViewById(R.id.edt_hthm);
		edt_kmai = (EditText) v.findViewById(R.id.edt_kmai);
		edt_kmai.setOnClickListener(this);
		// spinner_province = (Spinner) v.findViewById(R.id.spinner_province);
		// spinner_quanhuyen = (Spinner) v.findViewById(R.id.spinner_quanhuyen);
		// spinner_phuongxa = (Spinner) v.findViewById(R.id.spinner_phuongxa);
		// init province
		initProvince();
		edtprovince = (EditText) v.findViewById(R.id.edtprovince);
		btnRefreshStreetBlock = (Button) v
				.findViewById(R.id.btnRefreshStreetBlock);
		btnRefreshStreetBlock.setVisibility(View.GONE);

		if (!CommonActivity.isNullOrEmpty(Session.province)) {
			initDistrict(Session.province);
			try {
				GetAreaDal dal = new GetAreaDal(getActivity());
				dal.open();
				edtprovince.setText(dal.getNameProvince(Session.province));
				province = Session.province;
				dal.close();
			} catch (Exception ignored) {
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
		edtdistrict = (EditText) v.findViewById(R.id.edtdistrict);
		// edtdistrict.setText(Session.district);

		if (!CommonActivity.isNullOrEmpty(Session.province)
				&& !CommonActivity.isNullOrEmpty(Session.district)) {
			initPrecinct(Session.province, Session.district);
			try {
				GetAreaDal dal = new GetAreaDal(getActivity());
				dal.open();
				edtdistrict.setText(dal.getNameDistrict(Session.province,
						Session.district));
				district = Session.district;
				dal.close();
			} catch (Exception ignored) {
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

		btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new CacheDatabaseManager(MainActivity.getInstance())
						.insertCacheStreetBlock(null, province + district
								+ precinct);
				new GetListGroupAdressAsyncTask(getActivity()).execute(province
						+ district + precinct);
			}
		});
		edtprecinct = (EditText) v.findViewById(R.id.edtprecinct);
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

		spinner_loaihd = (Spinner) v.findViewById(R.id.spinner_loaihd);
		spinner_loaithuebao = (Spinner) v
				.findViewById(R.id.spinner_loaithuebao);
		lnkhuyenmai = (LinearLayout) v.findViewById(R.id.lnkhuyenmai);
		lnloaithuebao = (LinearLayout) v.findViewById(R.id.lnloaithuebao);
		lnhopdong = (LinearLayout) v.findViewById(R.id.lnhopdong);
		txtisdn = (EditText) v.findViewById(R.id.txtisdn);
		txtisdn.setOnClickListener(this);

		txtserial = (EditText) v.findViewById(R.id.txtserial);
		txtimsi = (EditText) v.findViewById(R.id.txtimsi);
		txtidcuahang = (EditText) v.findViewById(R.id.txtidcuahang);
		txtmanv = (EditText) v.findViewById(R.id.txtmanv);
		txtmanv.setVisibility(View.GONE);

		lnsonha = (LinearLayout) v.findViewById(R.id.lnsonha);
		spinner_to = (Spinner) v.findViewById(R.id.spinner_to);

		spinner_to.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arrTo != null && !arrTo.isEmpty()) {
					to = arrTo.get(arg2).getAreaCode();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		lvUploadImage = (ListView) v.findViewById(R.id.lvUploadImage);

		txtduong = (EditText) v.findViewById(R.id.txtduong);
		txtsonha = (EditText) v.findViewById(R.id.txtsonha);
		lvproduct = (ExpandableHeightListView) v.findViewById(R.id.lvproduct);
		lvproduct.setOnItemClickListener(this);
		lvproduct.setExpanded(true);
        Button btn_connection = (Button) v.findViewById(R.id.btn_connection);
		lnButton = (LinearLayout) v.findViewById(R.id.lnButton);

		// define specical paper

		lnTTGoiCuocDacBiet = (LinearLayout) v
				.findViewById(R.id.lnGoiCuocDacBiet);
		lnTTGoiCuocDacBiet.setVisibility(View.GONE);
        View viewSpec = v.findViewById(R.id.viewSpec);
        View viewSpec1 = v.findViewById(R.id.viewSpec1);
		spDoiTuong = (Spinner) v.findViewById(R.id.spDoiTuong);
        EditText edtQuocTich = (EditText) v.findViewById(R.id.edtQuocTich);
		edtQuocTich.setText(getString(R.string.viet_nam));
		edtQuocTich.setEnabled(false);
		tvDonVi = (EditText) v.findViewById(R.id.tvDonVi);
		edtMaGiayToDacBiet = (EditText) v.findViewById(R.id.edtMaGiayToDacBiet);
		edtNgayBD = (EditText) v.findViewById(R.id.edtNgayBD);
		edtNgayBD.setOnClickListener(this);
		edtNgayKT = (EditText) v.findViewById(R.id.edtNgayKT);
		rlchondonvi = (RelativeLayout) v.findViewById(R.id.rlchondonvi);
		edt_hthm = (EditText) v.findViewById(R.id.edt_hthm);

		edt_hthm.setOnClickListener(this);
		imgDeleteDV = v.findViewById(R.id.imgDeleteDonvi);
		imgDeleteDV.setVisibility(View.GONE);
		imgDeleteDV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tvDonVi.setText("");
				mCodeDonVi = "";
				imgDeleteDV.setVisibility(View.GONE);
			}
		});

		spDoiTuong.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mCodeDoiTuong = arrSpecObjects.get(arg2).getCode();
				// TOGO LAY THONG TIN SINH VIEN TU MA DOI TUONG
				// mCodeDoiTuong = arrSpecObjects.get(position).getCode();

				// check neu code = NEW_STU_2015 thi goi ws lay so bao danh va
				// an chon dv di
				if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
					// rlchondonvi.setVisibility(View.GONE);
					// rlquoctich.setVisibility(View.GONE);
					if (CommonActivity.isNetworkConnected(getActivity())) {

						CheckInfoCusSpecialAsyn checkInfoCusSpecialAsyn = new CheckInfoCusSpecialAsyn(
								getActivity());
						checkInfoCusSpecialAsyn.execute(cusByIdNoBean.getIdNo());
						// TODO XU LY
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				} else {
					// an linear donvi di
					rlchondonvi.setVisibility(View.VISIBLE);
					edtMaGiayToDacBiet.setEnabled(true);
					edtMaGiayToDacBiet.setText("");
					// rlquoctich.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		tvDonVi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				timKiemDonVi();
			}
		});

		btn_connection.setOnClickListener(this);
		CheckTBTraSau();

		// TODO MODIFY SUA SMARTSIM NGAY 20 THANG 10
		if (arrTelecomServiceBeans != null && arrTelecomServiceBeans.size() > 0) {
			arrTelecomServiceBeans.clear();
		}
		// if (cusByIdNoBean != null) {
		// if (cusByIdNoBean.getNameCustomer() != null
		// && !cusByIdNoBean.getNameCustomer().isEmpty()) {
		initTelecomService();
		// }
		//
		// }

		if (pakageBundeBean.getArrChargeBeans() != null
				&& pakageBundeBean.getArrChargeBeans().size() > 0) {
			pakageBundeBean = new PakageBundeBean();
		}

		if ("2".equalsIgnoreCase(subType)) {
			// init contract
			if (arrTractBeans != null && arrTractBeans.size() > 0) {
				arrTractBeans.clear();
			}
			if (cusByIdNoBean != null) {
				if (cusByIdNoBean.getCustId() != null
						&& !cusByIdNoBean.getCustId().isEmpty()) {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetConTractAsyn getConTractAsyn = new GetConTractAsyn(
								getActivity());
						getConTractAsyn.execute(cusByIdNoBean.getCustId(), "");
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				} else {
					if (cusByIdNoBean.getIdNo() != null
							&& !cusByIdNoBean.getIdNo().isEmpty()) {
						if (CommonActivity.isNetworkConnected(getActivity())) {
							GetConTractAsyn getConTractAsyn = new GetConTractAsyn(
									getActivity());
							getConTractAsyn
									.execute("", cusByIdNoBean.getIdNo());
						} else {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.errorNetwork),
									getString(R.string.app_name)).show();
						}
					}

				}

			}
		}

		spinner_serviceMobile
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						if (arg2 > 0 && positonservice != arg2) {
							if (arrPakageChargeBeans != null
									&& arrPakageChargeBeans.size() > 0) {
								arrPakageChargeBeans.clear();
								txtpakage.setText(Html.fromHtml("<u>"
										+ getString(R.string.chonpakage_mobile)
										+ "</u>"));
								productCode = "";
								offerId = "";
							}
							serviceType = arrTelecomServiceBeans.get(arg2)
									.getServiceAlias();
							telecomserviceId = arrTelecomServiceBeans.get(arg2)
									.getTelecomServiceId();
							if (CommonActivity
                                    .isNetworkConnected(getActivity())) {
								if (subType != null && !subType.isEmpty()) {
									if (telecomserviceId != null
											&& !telecomserviceId.isEmpty()) {
										GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(
												getActivity());
										getListPakageAsyn.execute(
												telecomserviceId, subType);
									}

								}

							} else {
								CommonActivity.createAlertDialog(getActivity(),
										getString(R.string.errorNetwork),
										getString(R.string.app_name)).show();
							}

						} else {
							// serviceType = "";
							// ReLoadData();

						}
						positonservice = arg2;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		// spinner_province
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// province = arrProvince.get(arg2).getProvince();
		// initDistrict(province);
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });
		// spinner_quanhuyen
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// district = arrDistrict.get(arg2).getDistrict();
		// initPrecinct(province, district);
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });
		//
		// spinner_phuongxa
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// precinct = arrPrecinct.get(arg2).getPrecinct();

		// if (arrTo != null && arrTo.size() > 0) {
		// arrTo.clear();
		// }
		// if (province != null && district != null && precinct
		// != null) {
		// new
		// GetListGroupAdressAsyncTask(getActivity()).execute(province
		// + district + precinct);
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });

		// spinner_hthm.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// if (arg2 > 0) {
		// if (arrStockTypeBeans != null
		// && arrStockTypeBeans.size() > 0) {
		// arrStockTypeBeans.clear();
		// }
		// if (mapSubStockModelRelReq != null
		// && mapSubStockModelRelReq.size() > 0) {
		// mapSubStockModelRelReq.clear();
		// }
		//
		// if (arrPromotionTypeBeans != null
		// && arrPromotionTypeBeans.size() > 0) {
		// arrPromotionTypeBeans.clear();
		// }
		//
		// hthm = arrHthmBeans.get(arg2).getCode();
		// reasonId = arrHthmBeans.get(arg2).getReasonId();
		//
		// if (serviceType != null && !serviceType.isEmpty()) {
		// if ("1".equalsIgnoreCase(subType)) {
		// if (CommonActivity
		// .isNetworkConnected(getActivity()) == true) {
		// GetListProductPreAsyn getListProductPreAsyn = new
		// GetListProductPreAsyn(
		// getActivity());
		// getListProductPreAsyn.execute(hthm,
		// productCode, serviceType);
		//
		// // if (mapListRecordPrepaid != null
		// // && !mapListRecordPrepaid.isEmpty()) {
		// // mapListRecordPrepaid.clear();
		// // }
		// if (adapProductAdapter != null) {
		// adapProductAdapter.notifyDataSetChanged();
		// }
		//
		// GetLisRecordPrepaidTask getLisRecordPrepaidTask = new
		// GetLisRecordPrepaidTask(
		// ""+reasonId, productCode, getActivity());
		// getLisRecordPrepaidTask.execute();
		//
		// } else {
		// CommonActivity.createAlertDialog(getActivity(),
		// getString(R.string.errorNetwork),
		// getString(R.string.app_name)).show();
		// }
		//
		// } else {
		// if (CommonActivity
		// .isNetworkConnected(getActivity()) == true) {
		// if (arrPromotionTypeBeans != null
		// && arrPromotionTypeBeans.size() > 0) {
		// arrPromotionTypeBeans.clear();
		// }
		// GetListProductPosAsyn getListProductPosAsyn = new
		// GetListProductPosAsyn(
		// getActivity());
		// getListProductPosAsyn.execute(hthm,
		// productCode, serviceType);
		//
		// GetPromotionByMap getPromotionByMap = new GetPromotionByMap(
		// getActivity());
		// getPromotionByMap.execute(serviceType, hthm,
		// offerId, province, district, precinct);
		//
		// // if (mapListRecordPrepaid != null
		// // && !mapListRecordPrepaid.isEmpty()) {
		// // mapListRecordPrepaid.clear();
		// // }
		// if (adapProductAdapter != null) {
		// adapProductAdapter.notifyDataSetChanged();
		// }
		//
		// GetLisRecordPrepaidTask getLisRecordPrepaidTask = new
		// GetLisRecordPrepaidTask(
		// ""+reasonId, productCode, getActivity());
		// getLisRecordPrepaidTask.execute();
		// } else {
		// CommonActivity.createAlertDialog(getActivity(),
		// getString(R.string.errorNetwork),
		// getString(R.string.app_name)).show();
		// }
		//
		// }
		// }
		// } else {
		// hthm = "";
		// reasonId = "";
		// if (arrStockTypeBeans != null
		// && arrStockTypeBeans.size() > 0
		// && adapProductAdapter != null) {
		// arrStockTypeBeans.clear();
		// adapProductAdapter = new GetProductAdapter(
		// arrStockTypeBeans, getActivity());
		// lvproduct.setAdapter(adapProductAdapter);
		// adapProductAdapter.notifyDataSetChanged();
		// }
		// if (mapSubStockModelRelReq != null
		// && mapSubStockModelRelReq.size() > 0) {
		// mapSubStockModelRelReq.clear();
		// }
		// maKM = "";
		// if (arrPromotionTypeBeans != null
		// && arrPromotionTypeBeans.size() > 0) {
		// arrPromotionTypeBeans.clear();
		// initInitListPromotionNotData();
		// }
		//
		// }
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });

		spinner_loaihd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0) {
					contractNo = "";
					contractId = "";
				} else {
					contractNo = arrTractBeans.get(arg2).getContractNo();
					contractId = arrTractBeans.get(arg2).getContractId();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spinner_loaithuebao
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 > 0) {
							subTypeMobile = arrSubTypeBeans.get(arg2)
									.getSubType();
						} else {
							subTypeMobile = "";
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		// spinner_kmai.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// if (arg2 > 0) {
		// maKM = arrPromotionTypeBeans.get(arg2).getPromProgramCode();
		// } else {
		// maKM = "";
		// }
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });
	}

	private void timKiemDonVi() {
		dialogLockBoxInfo();
	}

	private void dialogLockBoxInfo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// LocationService locationService = arrayListLocation.get(position);

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		@SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_don_vi, null);

		builder.setView(dialogView);
		dialogDonVi = builder.create();

		final EditText edtMaDonVi = (EditText) dialogView
				.findViewById(R.id.edtMaDonVi);
		final EditText edtTenDonVi = (EditText) dialogView
				.findViewById(R.id.edtTenDonVi);
		Button btnTimKiem = (Button) dialogView.findViewById(R.id.btnTimKiem);

		lvListDonVi = (ListView) dialogView.findViewById(R.id.lvLockBoxInfo);

		btnTimKiem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String maDonVi = edtMaDonVi.getText().toString();
				String tenDonVi = edtTenDonVi.getText().toString();

				hideKeyBoard();
				if (maDonVi.equals("") && tenDonVi.equals("")) {
					Toast.makeText(getActivity(),
							getString(R.string.chua_nhap_ma_dvi),
							Toast.LENGTH_LONG).show();
				} else {
					getDonVi(maDonVi, tenDonVi);
				}
			}
		});
		lvListDonVi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCodeDonVi = mAratListDonVi.get(position).getCode();
				tvDonVi.setText(mAratListDonVi.get(position).getCodeName());
				imgDeleteDV.setVisibility(View.VISIBLE);
				dialogDonVi.dismiss();
			}
		});

		dialogDonVi.show();

	}

	private void getDonVi(String maDonVi, String tenDonVi) {
		new GetLisDonVi(maDonVi, tenDonVi).execute();

	}

	public class GetLisDonVi extends AsyncTask<Void, Void, String> {

		private final String maDonVi;
		private final String tenDonVi;
		private final ProgressDialog dialog;

		public GetLisDonVi(String maDonVi, String tenDonVi) {
			this.maDonVi = maDonVi;
			this.tenDonVi = tenDonVi;
			dialog = new ProgressDialog(getActivity());
		}

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			this.dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String original = null;
			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {
				original = requestSeviceDonVi(maDonVi, tenDonVi, mCodeDoiTuong);
			} else {
			}

			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);
				mAratListDonVi = parseResultDonVi(result);
				Log.e("TAG6", "result List Don Vi : " + result);

				if (mAratListDonVi.size() > 0) {
					DonViAdapter adapter = new DonViAdapter(getActivity(),
							mAratListDonVi);
					lvListDonVi.setAdapter(adapter);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.ko_tim_thay_don_vi_nao),
							getString(R.string.app_name)).show();// ,getString(R.string.thu_lai)
				}
			}
			super.onPostExecute(result);
		}
	}

	private String requestSeviceDonVi(String maDonVi, String tenDonVi,
                                      String codeDoiTuong) {
		String reponse = null;
		String original = null;

		String xml = mBccsGateway.getXmlCustomer(
				createXMLDonVi(maDonVi, tenDonVi, codeDoiTuong),
				"mbccs_getListDepByObject");
		Log.e("TAG89", "xml Don Vi" + xml);
		try {
			reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
					getActivity(), "mbccs_getListDepByObject");
			Log.e("TAG8", "reponse Don Vi" + reponse);
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

	private String createXMLDonVi(String maDonVi, String tenDonVi,
			String codeDoiTuong) {
        return "<ws:getListDepByObject>" +
                "<cmInput>" +
                "<token>" + Session.getToken() + "</token>" +
                "<deptCode>" + maDonVi + "</deptCode>" +
                "<deptName>" + tenDonVi + "</deptName>" +
                "<objectCode>" + codeDoiTuong + "</objectCode>" +
                "</cmInput>" +
                "</ws:getListDepByObject>";
	}

	private ArrayList<DoiTuongObj> parseResultDonVi(String result) {

		ArrayList<DoiTuongObj> aratList = new ArrayList<>();
		if (result != null) {
			try {
				Log.e("TAG69 Don Vi ", result);
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);

				NodeList nodeListErrorCode = document
						.getElementsByTagName("lstDepartment");

				for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
					Node mNode = nodeListErrorCode.item(i);
					Element element = (Element) mNode;
					String id = domParse.getValue(element, "id");
					String code = domParse.getValue(element, "code");
					String codeName = domParse.getValue(element, "codeName");

					int ID = Integer.parseInt(id);

//					DoiTuongObj doiTuongObj = new DoiTuongObj(ID, codeName,
//							code);
//					aratList.add(doiTuongObj);
					// arrayOfArrayList1.add(arrayList);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return aratList;
	}

	private void parseResultError(String result) {
		if (result != null) {
			try {
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
								description, ";connect_smartsim;");
					} else if (errorCode.equals("0")) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void hideKeyBoard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.txtisdn:
			showPopupSelectIsdn();
			break;
		case R.id.edtNgayBD:
			showDatePickerDialog(edtNgayBD);
			break;

		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;

		case R.id.tvpakage:
			if (pakageBundeBean != null
					&& pakageBundeBean.getArrChargeBeans().size() > 0) {
				Intent intent = new Intent(getActivity(),
						FragmentSearchPakageMobile.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("PakageKey", pakageBundeBean);
				intent.putExtras(mBundle);
				startActivityForResult(intent, 3333);
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.notpakagemobile),
						getString(R.string.app_name)).show();
			}
			break;
		case R.id.btn_connection:
			isdn = txtisdn.getText().toString();
			if (isdn != null && !isdn.isEmpty()) {
				if (isdn.substring(0, 1).equalsIgnoreCase("0")) {
					isdn = isdn.substring(1, isdn.length());
				}
			}
			// to = txtto.getText().toString();
			// to.replace(" ", "");
			// to = to.trim();

			tenduong = txtduong.getText().toString();
			// tenduong = tenduong.replace(" ", "");
			// tenduong = tenduong.trim();

			sonha = txtsonha.getText().toString();
			// sonha = sonha.replace(" ", "");
			// sonha = sonha.trim();
			// thue bao tra truoc
			if ("1".equalsIgnoreCase(subType)) {
				validateSubPre();
			} else {
				// thue bao tra sau
				validateSubPos();
			}

			break;

		case R.id.edt_hthm:
			if (arrHthmBeans.size() > 0) {
				Intent intent = new Intent(getActivity(),
						SearchCodeHthmActivity.class);
				intent.putExtra("arrHthmBeans", arrHthmBeans);
				startActivityForResult(intent, 101);
			}
			break;
		case R.id.edt_kmai:
			if (arrPromotionTypeBeans.size() > 0) {
				Intent intent = new Intent(activity,
						SearchCodePromotionActivity.class);
				intent.putExtra("arrPromotionTypeBeans", arrPromotionTypeBeans);
				startActivityForResult(intent, 102);
			}
			break;
		default:
			break;
		}
	}

	private void validateSubPre() {
		if (serviceType != null && !serviceType.isEmpty()) {
			if (offerId != null && !offerId.isEmpty()) {

				if (offerId.equalsIgnoreCase(Constant.OFFERID_TSV)) {
					// TODO VALIDATE FORM THONG TIN DAC BIET
					// if(tvDonVi.getText() != null &&
					// !tvDonVi.getText().toString().isEmpty()){

					if (edtNgayBD.getText() != null
							&& !edtNgayBD.getText().toString().isEmpty()) {

						try {
							if (dateBD.after(dateNow)) {
								Toast.makeText(getActivity(),
										getString(R.string.chekngayBD1),
										Toast.LENGTH_SHORT).show();
							} else {

								if (hthm != null && !hthm.isEmpty()) {
									if (txtisdn.getText() != null
											&& !txtisdn.getText().toString()
													.isEmpty()) {
										if (txtserial.getText() != null
												&& !txtserial.getText()
														.toString().isEmpty()) {
											checkHangHoa();

										} else {
											Toast.makeText(
													getActivity(),
													getString(R.string.checkserial),
													Toast.LENGTH_SHORT).show();
										}

									} else {
										Toast.makeText(getActivity(),
												getString(R.string.checkisdn),
												Toast.LENGTH_SHORT).show();
									}
								} else {
									Toast.makeText(getActivity(),
											getString(R.string.checkhthm),
											Toast.LENGTH_SHORT).show();
								}

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						Toast.makeText(getActivity(),
								getString(R.string.chekngayBD),
								Toast.LENGTH_SHORT).show();
					}

					// }
					// else{
					// Toast.makeText(getActivity(),
					// getString(R.string.chekdonvi),
					// Toast.LENGTH_SHORT).show();
					// }

				} else {

					if (hthm != null && !hthm.isEmpty()) {
						if (txtisdn.getText() != null
								&& !txtisdn.getText().toString().isEmpty()) {
							if (txtserial.getText() != null
									&& !txtserial.getText().toString()
											.isEmpty()) {
								checkHangHoa();

							} else {
								Toast.makeText(getActivity(),
										getString(R.string.checkserial),
										Toast.LENGTH_SHORT).show();
							}

						} else {
							Toast.makeText(getActivity(),
									getString(R.string.checkisdn),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
								getString(R.string.checkhthm),
								Toast.LENGTH_SHORT).show();
					}

				}

			} else {
				Toast.makeText(getActivity(), getString(R.string.checkpakage),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getActivity(), getString(R.string.checkserviceType),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void validateSubPos() {
		if (serviceType != null && !serviceType.isEmpty()) {
			if (offerId != null && !offerId.isEmpty()) {
				if (subTypeMobile != null && !subTypeMobile.isEmpty()) {
					if (hthm != null && !hthm.isEmpty()) {
						if (txtisdn.getText() != null
								&& !txtisdn.getText().toString().isEmpty()) {
							if (txtserial.getText() != null
									&& !txtserial.getText().toString()
											.isEmpty()) {
								if (!StringUtils.CheckCharSpecical(txtserial
                                        .getText().toString())) {
									// if (to != null && !to.isEmpty()) {
									// if (tenduong != null
									// && !tenduong.toString()
									// .isEmpty()) {
									// if (sonha != null
									// && !sonha.toString()
									// .isEmpty()) {
									checkHangHoatrasau();
									// } else {
									// Toast.makeText(
									// getActivity(),
									// getString(R.string.checksonhamobile),
									// Toast.LENGTH_SHORT)
									// .show();
									// }
									//
									// } else {
									// Toast.makeText(
									// getActivity(),
									// getString(R.string.checkttduong),
									// Toast.LENGTH_SHORT).show();
									// }
									// } else {
									// Toast.makeText(getActivity(),
									// getString(R.string.checkto),
									// Toast.LENGTH_SHORT).show();
									// }

								} else {
									Toast.makeText(
											getActivity(),
											getString(R.string.checkspecialSerial),
											Toast.LENGTH_SHORT).show();
								}

							} else {
								Toast.makeText(getActivity(),
										getString(R.string.checkserial),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getActivity(),
									getString(R.string.checkisdn),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
								getString(R.string.checkhthm),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(),
							getString(R.string.checktypeSub),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), getString(R.string.checkpakage),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getActivity(), getString(R.string.checkserviceType),
					Toast.LENGTH_SHORT).show();
		}

	}

	private boolean isUploadImage() {
		Log.d(Constant.TAG,
				"isUploadImage() hashmapFileObj: " + hashmapFileObj.size()
						+ " " + hashmapFileObj);
		Log.d(Constant.TAG, "isUploadImage() mapListRecordPrepaid: "
				+ mapListRecordPrepaid.size() + " " + mapListRecordPrepaid);

        return !hashmapFileObj.isEmpty()
                && hashmapFileObj.size() == mapListRecordPrepaid.size();
	}

	private void checkHangHoa() {
		if (arrStockTypeBeans.size() > 0) {
			if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
				if (mapListRecordPrepaid != null
						&& !mapListRecordPrepaid.isEmpty()) {

					boolean isSelectIm = isUploadImage();// true;
					// for (int i = 0; i < mListIvO.size(); i++) {
					// boolean b = mListIvO.get(i).isSetIm();
					// if (b == false) {
					// isSelectIm = false;
					// // return;
					// }
					// }
					if (isSelectIm) {
						subConnectPre();
					} else {
						Toast.makeText(getActivity(),
								getString(R.string.chua_chon_het_anh),
								Toast.LENGTH_LONG).show();
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkthongtinchungtu),
							getString(R.string.app_name)).show();
				}
			} else {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.checkhanghoa),
						Toast.LENGTH_LONG).show();
			}

		} else {
			if (mapListRecordPrepaid != null && !mapListRecordPrepaid.isEmpty()) {

				boolean isSelectIm = isUploadImage();// true;
				// for (int i = 0; i < mListIvO.size(); i++) {
				// boolean b = mListIvO.get(i).isSetIm();
				// if (b == false) {
				// isSelectIm = false;
				// // return;
				// }
				// }
				if (isSelectIm) {
					subConnectPre();
				} else {
					Toast.makeText(getActivity(),
							getString(R.string.chua_chon_het_anh),
							Toast.LENGTH_LONG).show();
				}
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkthongtinchungtu),
						getString(R.string.app_name)).show();
			}
		}
	}

	private void checkHangHoatrasau() {
		if (arrStockTypeBeans.size() > 0) {
			if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
				if (mapListRecordPrepaid != null
						&& !mapListRecordPrepaid.isEmpty()) {

					boolean isSelectIm = isUploadImage();// true;
					// for (int i = 0; i < mListIvO.size(); i++) {
					// boolean b = mListIvO.get(i).isSetIm();
					// if (b == false) {
					// isSelectIm = false;
					// // return;
					// }
					// }
					if (isSelectIm) {
						subConnectPos();
					} else {
						Toast.makeText(getActivity(),
								getString(R.string.chua_chon_het_anh),
								Toast.LENGTH_LONG).show();
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkthongtinchungtu),
							getString(R.string.app_name)).show();
				}
			} else {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.checkhanghoa),
						Toast.LENGTH_LONG).show();
			}

		} else {
			if (mapListRecordPrepaid != null && !mapListRecordPrepaid.isEmpty()) {

				boolean isSelectIm = isUploadImage();// true;
				// for (int i = 0; i < mListIvO.size(); i++) {
				// boolean b = mListIvO.get(i).isSetIm();
				// if (b == false) {
				// isSelectIm = false;
				// // return;
				// }
				// }
				if (isSelectIm) {
					subConnectPos();
				} else {
					Toast.makeText(getActivity(),
							getString(R.string.chua_chon_het_anh),
							Toast.LENGTH_LONG).show();
				}
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkthongtinchungtu),
						getString(R.string.app_name)).show();
			}
		}
	}

	private void subConnectPre() {
		if (CommonActivity.isNetworkConnected(getActivity())) {
			CommonActivity.createDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checksubconnect),
					getActivity().getResources().getString(R.string.app_name),
					getActivity().getResources().getString(R.string.buttonOk),
					getActivity().getResources().getString(R.string.cancel),
					subConnectClickPre, null).show();

		} else {
			CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.errorNetwork),
					getActivity().getResources().getString(R.string.app_name))
					.show();
		}

	}

	private void subConnectPos() {
		if (CommonActivity.isNetworkConnected(getActivity())) {
			CommonActivity.createDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.checksubconnect),
					getActivity().getResources().getString(R.string.app_name),
					getActivity().getResources().getString(R.string.buttonOk),
					getActivity().getResources().getString(R.string.cancel),
					subConnectClickPos, null).show();

		} else {
			CommonActivity.createAlertDialog(
					getActivity(),
					getActivity().getResources().getString(
							R.string.errorNetwork),
					getActivity().getResources().getString(R.string.app_name))
					.show();
		}

	}

	private final OnClickListener subConnectClickPre = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				timeStart = new Date();

				SubConnectPreAsyn subConnectPreAsyn = new SubConnectPreAsyn(
						getActivity());
				subConnectPreAsyn.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	private final OnClickListener subConnectClickPos = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				timeStart = new Date();
				SubConnectPosAsyn subConnectPosAsyn = new SubConnectPosAsyn(
						getActivity());
				subConnectPosAsyn.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	// LOAD DATA THUE BAO TRA SAU
	private void CheckTBTraSau() {
		if (subType != null && !subType.equals("")) {

			if (subType.equals("2")) {
				// co kuyen mai == thue bao tra sau
				lnkhuyenmai.setVisibility(View.VISIBLE);
				lnloaithuebao.setVisibility(View.VISIBLE);
				lnhopdong.setVisibility(View.VISIBLE);
				lnsonha.setVisibility(View.VISIBLE);
			} else {
				lnsonha.setVisibility(View.GONE);
				lnkhuyenmai.setVisibility(View.GONE);
				lnloaithuebao.setVisibility(View.GONE);
				lnhopdong.setVisibility(View.GONE);
			}
		}
	}

	// RELOAD DATA ====

	private void ReLoadData() {
		// reload dich vu
		// positonservice = -1;

		// === reload goi cuoc =============
		offerId = "";
		productCode = "";
		txtpakage.setText(Html.fromHtml("<u>"
				+ getString(R.string.chonpakage_mobile) + "</u>"));
		pakaChargeBeans = new PakageChargeBeans();
		if (arrPakageChargeBeans != null && arrPakageChargeBeans.size() > 0) {
			arrPakageChargeBeans.clear();
		}
		if (pakageBundeBean.getArrChargeBeans() != null
				&& pakageBundeBean.getArrChargeBeans().size() > 0) {
			pakageBundeBean = new PakageBundeBean();
		}
		// ==== reload loai thue bao ========
		subTypeMobile = "";
		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
			arrSubTypeBeans.clear();
			initListSubTypeNotData();
		}
		// ==== reload hthm ===============
		hthm = "";
		reasonId = "";
		if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
			arrHthmBeans.clear();
			initHTHMNotData();
		}
		CheckTBTraSau();
		txtisdn.setText("");
		txtserial.setText("");
		txtimsi.setText("");
		hthm = "";
		reasonId = "";
		if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0
				&& adapProductAdapter != null) {
			arrStockTypeBeans.clear();
			adapProductAdapter = new GetProductAdapter(arrStockTypeBeans,
					getActivity());
			lvproduct.setAdapter(adapProductAdapter);
			adapProductAdapter.notifyDataSetChanged();
		}
		if (mapSubStockModelRelReq != null && mapSubStockModelRelReq.size() > 0) {
			mapSubStockModelRelReq.clear();
		}

	}

	// lay ma tinh/thanhpho

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
			// spinner_quanhuyen.setAdapter(adapter);
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
			// spinner_phuongxa.setAdapter(adapter);
			// }
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
		}
	}

	// lay dich vu
	private void initTelecomService() {
		try {

			GetServiceDal dal = new GetServiceDal(getActivity());
			dal.open();
			arrTelecomServiceBeans = dal.getlisServiceMobileSM();
			dal.close();
			TelecomServiceBeans serviceBeans = new TelecomServiceBeans();
			serviceBeans.setTele_service_name(getActivity().getResources()
					.getString(R.string.chondichvu));
			arrTelecomServiceBeans.add(0, serviceBeans);
			if (arrTelecomServiceBeans != null
					&& !arrTelecomServiceBeans.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					adapter.add(telecomServiceBeans.getTele_service_name());
				}
				spinner_serviceMobile.setAdapter(adapter);

				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					if ("M".equalsIgnoreCase(telecomServiceBeans
							.getServiceAlias())) {
						spinner_serviceMobile
								.setSelection(arrTelecomServiceBeans
										.indexOf(telecomServiceBeans));
						spinner_serviceMobile.setClickable(false);
						break;
					}
				}

			}
		} catch (Exception e) {
			Log.e("initTelecomService", e.toString());
		}
	}

	private final OnClickListener onclickBackScreen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("staffKey", staff);
			FragmentActiveAccountPayment fragmentActiveAccountPayment = new FragmentActiveAccountPayment();
			fragmentActiveAccountPayment.setArguments(bundle);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentActiveAccountPayment, false);

		}
	};

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// if (MainActivity.getInstance() != null) {
			// MainActivity.getInstance().finish();
			// }
			LoginDialog dialog = new LoginDialog(getActivity(),
					";connect_smartsim;");

			dialog.show();
		}
	};

	// ========= init spinner Contract
	private void initContract() {
		ConTractBean conTractBean1 = new ConTractBean();
		conTractBean1.setContractNo(getString(R.string.contractNew));
		conTractBean1.setContractId("");
		arrTractBeans.add(0, conTractBean1);
		if (arrTractBeans != null && arrTractBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (ConTractBean conTractBean : arrTractBeans) {
				adapter.add(conTractBean.getContractNo());
			}
			spinner_loaihd.setAdapter(adapter);
		}

	}

	// === hthm co data
	private void initHTHM() {
		edt_hthm.setText(getResources().getString(R.string.chonhthm));
		// HTHMBeans hthmBeans = new HTHMBeans();
		// hthmBeans.setName(getResources().getString(R.string.chonhthm));
		// arrHthmBeans.add(0, hthmBeans);

		// if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// getActivity(), R.layout.pakage_charge_item, R.id.txtpakage);
		// adapter.setDropDownViewResource(R.layout.pakage_charge_item);
		// for (HTHMBeans hBeans : arrHthmBeans) {
		// if (hBeans.getCode() == null) {
		// adapter.add(hBeans.getName());
		// } else {
		// adapter.add(hBeans.getCode() + "-" + hBeans.getName());
		// }
		// }
		// spinner_hthm.setAdapter(adapter);
		// }
	}

	// hthm ko data
	private void initHTHMNotData() {
		edt_hthm.setText(getResources().getString(R.string.chonhthm));

		// HTHMBeans hthmBeans = new HTHMBeans();
		// hthmBeans.setName(getResources().getString(R.string.chonhthm));
		// ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
		// lstHthmBeans.add(0, hthmBeans);

		// if (lstHthmBeans != null && lstHthmBeans.size() > 0) {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// getActivity(), android.R.layout.simple_dropdown_item_1line,
		// android.R.id.text1);
		// for (HTHMBeans hBeans : lstHthmBeans) {
		// adapter.add(hBeans.getName());
		// }
		// spinner_hthm.setAdapter(adapter);
		// }
	}

	// init spinner listsubPromotion

	private void initInitListPromotion() {

		PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
		promotionTypeBeans.setCodeName(getResources().getString(
				R.string.selectMKM));
		arrPromotionTypeBeans.add(0, promotionTypeBeans);

		edt_kmai.setText(getString(R.string.selectMKM));

		// if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() >
		// 0) {
		// adapterHTKM = new ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_dropdown_item_1line,
		// android.R.id.text1);
		// for (PromotionTypeBeans proBeans : arrPromotionTypeBeans) {
		// adapterHTKM.add(proBeans.getCodeName());
		// }
		// spinner_kmai.setAdapter(adapterHTKM);
		// }
	}

	// init spinner not data subpromotion
    private void initInitListPromotionNotData() {
		edt_kmai.setText(getString(R.string.selectMKM));
		// if (arrPromotionTypeBeans != null) {
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		// spinner_kmai.setAdapter(adapter);
		// }
	}

	// =========== webservice danh sach khuyen mai =================
	public class GetPromotionByMap extends
			AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetPromotionByMap(Context context) {
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
		protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
			return getPromotionTypeBeans(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {

					arrPromotionTypeBeans = result;
					initInitListPromotion();

				} else {
					initInitListPromotionNotData();
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.checkmakm),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private ArrayList<PromotionTypeBeans> getPromotionTypeBeans(
				String serviceType, String regType, String offerId) {
			ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListPromotions");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListPromotions>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<regType>").append(regType).append("</regType>");
				rawData.append("<offerId>").append(offerId).append("</offerId>");
				rawData.append("<province>").append("null").append("</province>");
				rawData.append("<district>").append("null").append("</district>");
				rawData.append("<precinct>").append("null").append("</precinct>");
				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
				rawData.append("<actionCode>" + "00" + "</actionCode>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListPromotions>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListPromotions");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ============parse xml in android=========
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstPromotionType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
						String codeName = parse.getValue(e1, "codeName");
						Log.d("codeName", codeName);
						promotionTypeBeans.setCodeName(codeName);

						String cycle = parse.getValue(e1, "cycle");
						Log.d("cycle", cycle);
						promotionTypeBeans.setCycle(cycle);

						String monthAmount = parse.getValue(e1, "monthAmount");
						Log.d("monthAmount", monthAmount);
						promotionTypeBeans.setMonthAmount(monthAmount);

						String monthCommitment = parse.getValue(e1,
								"monthCommitment");
						Log.d("monthCommitment", monthCommitment);
						promotionTypeBeans.setMonthCommitment(monthCommitment);

						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						promotionTypeBeans.setName(name);

						String promProgramCode = parse.getValue(e1,
								"promProgramCode");
						Log.d("promProgramCode", promProgramCode);
						promotionTypeBeans.setPromProgramCode(promProgramCode);

						String promType = parse.getValue(e1, "promType");
						Log.d("promType", promType);
						promotionTypeBeans.setPromType(promType);

						String staDate = parse.getValue(e1, "staDate");
						Log.d("staDate", staDate);
						promotionTypeBeans.setStaDate(staDate);

						String status = parse.getValue(e1, "status");
						Log.d("status", status);
						promotionTypeBeans.setStatus(status);

						String telService = parse.getValue(e1, "telService");
						Log.d("telService", telService);
						promotionTypeBeans.setTelService(telService);

						String type = parse.getValue(e1, "type");
						Log.d("type", type);
						promotionTypeBeans.setType(type);

						String descrip = parse.getValue(e1, "description");
						Log.d("descriponnnnn", descrip);
						if (CommonActivity.isNullOrEmpty(promotionTypeBeans
								.getDescription())) {
							promotionTypeBeans.setDescription(descrip);
						}
						lisPromotionTypeBeans.add(promotionTypeBeans);
					}
				}
			} catch (Exception ex) {
				Log.d("getPromotionTypeBeans", ex.toString());
			}

			return lisPromotionTypeBeans;
		}

	}

	// init spinner ListSubType
    private void initListSubType() {
		SubTypeBeans subTypeBean = new SubTypeBeans();
		subTypeBean.setName(this.getResources().getString(
				R.string.chonloaithuebao));
		arrSubTypeBeans.add(0, subTypeBean);
		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);

			for (SubTypeBeans subTyBeans : arrSubTypeBeans) {
				adapter.add(subTyBeans.getName());
			}
			spinner_loaithuebao.setAdapter(adapter);
		}
	}

	// init spinner ListSubTypeNotdate
    private void initListSubTypeNotData() {
		SubTypeBeans subTypeBeanNot = new SubTypeBeans();
		subTypeBeanNot.setName(this.getResources().getString(
				R.string.chonloaithuebao));
		ArrayList<SubTypeBeans> arrSubTypeBeanss = new ArrayList<>();
		arrSubTypeBeanss.add(0, subTypeBeanNot);
		if (arrSubTypeBeanss != null && arrSubTypeBeanss.size() > 0) {

			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);

			for (SubTypeBeans subTyBeans : arrSubTypeBeanss) {
				adapter.add(subTyBeans.getName());
			}
			spinner_loaithuebao.setAdapter(adapter);
		}
	}

	// ====================== Webserivce get list ========== DS loáº¡i
	// thuÃª bao
	private class GetListSubTypeAsyn extends
			AsyncTask<String, Void, ArrayList<SubTypeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListSubTypeAsyn(Context context) {
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
		protected ArrayList<SubTypeBeans> doInBackground(String... arg0) {
			return getListSubTypeBeans(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<SubTypeBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {

					arrSubTypeBeans = result;
					initListSubType();
				} else {
					initListSubTypeNotData();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private ArrayList<SubTypeBeans> getListSubTypeBeans(String serviceType,
				String productCode) {
			ArrayList<SubTypeBeans> lisSubTypeBeans = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListMobileSubType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListMobileSubType>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (serviceType != null && !serviceType.isEmpty()) {
					rawData.append("<serviceType>").append(serviceType);
					rawData.append("</serviceType>");
				}
				if (productCode != null && !productCode.isEmpty()) {
					rawData.append("<productCode>").append(productCode);
					rawData.append("</productCode>");
				}

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListMobileSubType>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListMobileSubType");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ===============parse xml =========================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstSubType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SubTypeBeans subTypeBeans = new SubTypeBeans();
						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						subTypeBeans.setName(name);

						String subGroupIdp = parse.getValue(e1, "subGroupId");
						Log.d("subGroupIdp", subGroupIdp);
						subTypeBeans.setSubGroupId(subGroupIdp);
						String subType = parse.getValue(e1, "subType");
						Log.d("subType", subType);
						subTypeBeans.setSubType(subType);
						// String telService = parse.getValue(e1, "telService");
						// Log.d("telService", telService);
						// subTypeBeans.setTelService(telService);
						// String updateDate = parse.getValue(e1, "updateDate");
						// Log.d("updateDate", updateDate);
						// subTypeBeans.setUpdateDate(updateDate);
						// String updateUser = parse.getValue(e1, "updateUser");
						// Log.d("updateUser", updateUser);
						// subTypeBeans.setUpdateUser(updateUser);

						lisSubTypeBeans.add(subTypeBeans);
					}
				}

			} catch (Exception e) {
				Log.d("getListSubTypeBeans", e.toString());
			}
			return lisSubTypeBeans;
		}
	}

	// get contract
	private class GetConTractAsyn extends
			AsyncTask<String, Void, ArrayList<ConTractBean>> {

		final ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetConTractAsyn(Context context) {
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
		protected ArrayList<ConTractBean> doInBackground(String... params) {
			return sendRequestGetLisContract(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<ConTractBean> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrTractBeans = result;
					initContract();
				} else {
					if (arrTractBeans != null && arrTractBeans.size() > 0) {
						arrTractBeans.clear();
					}
					initContract();
					// initHTHMNotData();
					// Dialog dialog = CommonActivity.createAlertDialog(
					// getActivity(), getResources()
					// .getString(R.string.notcontract),
					// getResources().getString(R.string.app_name));
					// dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private ArrayList<ConTractBean> sendRequestGetLisContract(String cusId,
				String idNo) {
			ArrayList<ConTractBean> lstTractBeans = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_findContractOld");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:findContractOld>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (cusId != null && !cusId.isEmpty()) {
					rawData.append("<custId>").append(cusId);
					rawData.append("</custId>");
				} else {
					rawData.append("<idNo>").append(idNo);
					rawData.append("</idNo>");
				}

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:findContractOld>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_findContractOld");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================
				GetContractHandler handler = (GetContractHandler) CommonActivity
						.parseXMLHandler(new GetContractHandler(), original);
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
				lstTractBeans = handler.getLstData();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return lstTractBeans;
		}
	}

	// ===========webservice danh sach hinh thuc hoa mang tra sau
	// ===============

	public class GetHTHMAsyn extends
			AsyncTask<String, Void, ArrayList<HTHMBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetHTHMAsyn(Context context) {
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
		protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
			return getListHTHM(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<HTHMBeans> result) {

			// get hinh thuc hoa mang
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrHthmBeans = result;
					initHTHM();
				} else {
					initHTHMNotData();
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.noththm),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		// ==input String offerId, String serviceType
		private ArrayList<HTHMBeans> getListHTHM(String serviceType,
				String offerId) {
			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListRegTypePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListRegTypePos>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<actionCode>" + "00");
				rawData.append("</actionCode>");

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");
				rawData.append("<province>").append("null");
				rawData.append("</province>");
				rawData.append("<district>").append("null");
				rawData.append("</district>");
				rawData.append("<precinct>").append("null");
				rawData.append("</precinct>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListRegTypePos>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListRegTypePos");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================

				HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
						.parseXMLHandler(new HTHMMobileHandler(), original);
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
				lstHthmBeans = handler.getLstData();
				// ====== parse xml ===================
			} catch (Exception e) {
				Log.d("getListHTHM", e.toString());
			}

			return lstHthmBeans;
		}

	}

	// ===========webservice danh sach hinh thuc hoa mang tra truoc
	// ===============

	public class GetHTHMPREAsyn extends
			AsyncTask<String, Void, ArrayList<HTHMBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetHTHMPREAsyn(Context context) {
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
		protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
			return getListPREHTHM(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<HTHMBeans> result) {

			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrHthmBeans = result;
					initHTHM();
				} else {
					initHTHMNotData();
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.noththm),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		// ==input String offerId, String serviceType
		private ArrayList<HTHMBeans> getListPREHTHM(String serviceType,
				String offerId) {
			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListRegTypePre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListRegTypePre>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<actionCode>" + "00");
				rawData.append("</actionCode>");

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListRegTypePre>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListRegTypePre");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================

				HTHMPREMobileHandler handler = (HTHMPREMobileHandler) CommonActivity
						.parseXMLHandler(new HTHMPREMobileHandler(), original);
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
				lstHthmBeans = handler.getLstData();
				// ====== parse xml ===================
			} catch (Exception e) {
				Log.d("getListHTHM", e.toString());
			}

			return lstHthmBeans;
		}

	}

	// ======= get list PakageCharge getListProductByTelecomService
	public class GetListPakageAsyn extends
			AsyncTask<String, Void, ArrayList<PakageChargeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
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
			return sendRequestGetListService(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<PakageChargeBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					arrPakageChargeBeans = result;
					pakageBundeBean.setArrChargeBeans(result);
					Intent intent = new Intent(getActivity(),
							FragmentSearchPakageMobile.class);
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("PakageKey", pakageBundeBean);
					intent.putExtras(mBundle);
					startActivityForResult(intent, 3333);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notgoicuoc),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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
				String telecomserviceId, String dbType) {
			String original = null;
			FileInputStream in = null;
			ArrayList<PakageChargeBeans> lisPakageChargeBeans = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				// input.addValidateGateway("username", Constant.BCCSGW_USER);
				// input.addValidateGateway("password", Constant.BCCSGW_PASS);
				// input.addValidateGateway("wscode",
				// "mbccs_getListProductByTelecomService");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

				rawData.append("<soapenv:Header/>");
				rawData.append("<soapenv:Body>");

				rawData.append("<ws:getListMobileProductByTelecomService>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<telecomServiceId>").append(telecomserviceId);
				rawData.append("</telecomServiceId>");

				rawData.append("<dbType>").append(dbType);
				rawData.append("</dbType>");

				rawData.append("<type>" + Constant.SMART_SIM);
				rawData.append("</type>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListMobileProductByTelecomService>");
				rawData.append("</soapenv:Body>");
				rawData.append("</soapenv:Envelope>");

				String envelope = rawData.toString();
				Log.e("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);

				String fileName = input.sendRequestWriteToFile(envelope,
						Constant.WS_PAKAGE_DATA_SMARTSIM, Constant.PAKAGE_FILE_NAME);
				if (fileName != null) {
					try {
						File file = new File(fileName);
						in = new FileInputStream(file);
						PakageHanlder handler = (PakageHanlder) CommonActivity
								.parseXMLHandler(new PakageHanlder(),
										new InputSource(in));
						// file.delete();
						if (handler != null) {
							if (handler.getItem().getToken() != null
									&& !handler.getItem().getToken().isEmpty()) {
								Session.setToken(handler.getItem().getToken());
							}

							if (handler.getItem().getErrorCode() != null) {
								errorCode = handler.getItem().getErrorCode();
							}
							if (handler.getItem().getDescription() != null) {
								description = handler.getItem()
										.getDescription();
							}
							lisPakageChargeBeans = handler.getLstData();
						}

					} catch (Exception e) {
						e.printStackTrace();
						Log.d("Exception : + ", e.toString());
					} finally {
						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisPakageChargeBeans;
		}

	}

	OnClickListener chuyenquamanhinhactive = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Bundle bundle = new Bundle();
			bundle.putSerializable("staffKey", staff);
			FragmentActiveAccountPayment fragmentActiveAccountPayment = new FragmentActiveAccountPayment();
			fragmentActiveAccountPayment.setArguments(bundle);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentActiveAccountPayment, false);
			// getActivity().finish();

		}
	};

	private class AsynZipFile extends
			AsyncTask<String, Void, ArrayList<FileObj>> {

		private final HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
		private final Context mContext;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";
		private ArrayList<String> lstFilePath = new ArrayList<>();
		private String actionAuditId = "";

		public AsynZipFile(Context context,
				HashMap<String, ArrayList<FileObj>> hasMapFile,
				ArrayList<String> mlstFilePath, String actionaudit) {
			this.mContext = context;
			this.mHashMapFileObj = hasMapFile;
			this.lstFilePath = mlstFilePath;
			this.actionAuditId = actionaudit;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(mContext);
			// check font
			this.progress.setMessage(mContext.getResources().getString(
					R.string.progress_zip));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<FileObj> doInBackground(String... arg0) {
			ArrayList<FileObj> arrFileBackUp1 = null;
			try {
				arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext,
						mHashMapFileObj, lstFilePath);
				errorCode = "0";
				return arrFileBackUp1;
			} catch (Exception e) {
				errorCode = "1";
				description = "Error when zip file: " + e.toString();
				return arrFileBackUp1;
			}
		}

		@Override
		protected void onPostExecute(ArrayList<FileObj> result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null && !result.isEmpty()) {
					for (FileObj fileObj : result) {
						fileObj.setActionCode("01");
						fileObj.setReasonId(reasonId);
						fileObj.setIsdn(isdn);
						fileObj.setActionAudit(actionAuditId);
						fileObj.setPageSize(0 + "");
						fileObj.setStatus(0);
					}
					AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(
							mContext, result, onclickBackScreen,
							getString(R.string.connectSuccess) + "\n");
					uploadImageAsy.execute();
				}
			} else {
				if (result != null && result.size() > 0) {
					for (FileObj fileObj : result) {
						File tmp = new File(fileObj.getPath());
						tmp.delete();
					}
				}

				CommonActivity.createAlertDialog(getActivity(), description,
						getActivity().getString(R.string.app_name)).show();
			}

		}
	}

	// ========== dau noi tra truoc==========

	private class SubConnectPreAsyn extends AsyncTask<String, String, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ArrayList<String> lstFilePath = new ArrayList<>();

		public SubConnectPreAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.subconnecting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {

					File folder = new File(
							Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
					if (!folder.exists()) {
						folder.mkdir();
					}
					Log.d("Log",
							"Folder zip file create: "
									+ folder.getAbsolutePath());
					for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
							.entrySet()) {
						ArrayList<FileObj> listFileObjs = entry.getValue();
						String zipFilePath = "";
						if (listFileObjs.size() > 1) {
							String spinnerCode = "";
							for (FileObj fileObj : listFileObjs) {
								spinnerCode = fileObj.getCodeSpinner();
							}
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_"
									+ spinnerCode + ".zip";
							lstFilePath.add(zipFilePath);
						} else if (listFileObjs.size() == 1) {
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_"
									+ listFileObjs.get(0).getCodeSpinner()
									+ ".jpg";
							lstFilePath.add(zipFilePath);
						}
					}
				}
                return subConectPre(lstFilePath);
			} catch (Exception e) {
				errorCode = "1";
				description = "Error when zip file: " + e.toString();
				return "1";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();


			if (errorCode.equals("0")) {
				lnButton.setVisibility(View.GONE);

				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
					AsynZipFile asynZipFile = new AsynZipFile(getActivity(),
							hashmapFileObj, lstFilePath, description);
					asynZipFile.execute();
				}
				// CommonActivity.createAlertDialog(getActivity(),
				// getString(R.string.daunoithanhcong),
				// getString(R.string.app_name), chuyenquamanhinhactive)
				// .show();
				//
				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
				// for (FileObj fileObj : arrFileBackUp) {
				// fileObj.setActionCode("00"); // đấu nối : 00 đăng
				// // ký thông tin 04
				// fileObj.setReasonId(reasonId);
				// fileObj.setIsdn(isdn);
				// fileObj.setActionAudit(description);
				// fileObj.setPageSize(0 + "");
				// fileObj.setStatus(0);
				// }
				// FileUtils.insertFileBackUpToDataBase(arrFileBackUp,
				// activity.getApplicationContext());
				// }

				// for (java.util.Map.Entry<String, ArrayList<FileObj>> entry :
				// hashmapFileObj.entrySet()) {
				// ArrayList<FileObj> listFileObjs = entry.getValue();
				//
				// for (int i = 0; i < listFileObjs.size(); i++) {
				// FileObj fileObj = listFileObjs.get(i);
				// fileObj.setActionCode("00"); // đấu nối : 00 đăng ký
				// thông tin 04
				// fileObj.setReasonId(reasonId);
				// fileObj.setIsdn(isdn);
				// fileObj.setActionAudit(description);
				// fileObj.setPageIndex(i + "");
				// fileObj.setPageSize(listFileObjs.size() + "");
				// fileObj.setStatus(0);
				// }
				// }
				//
				// FileUtils.backUpImageFile(hashmapFileObj,activity.getApplicationContext());

			} else {

				if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
					for (FileObj fileObj : arrFileBackUp) {
						File tmp = new File(fileObj.getPath());
						tmp.delete();
					}
				}

				lnButton.setVisibility(View.VISIBLE);
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					// Dialog dialog = CommonActivity
					// .createAlertDialog(
					// (Activity) context,
					// description,
					// context.getResources().getString(
					// R.string.app_name), moveLogInAct);
					// dialog.show();
					LoginDialog dialog = new LoginDialog(getActivity(),
							";connect_smartsim;");

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

		private String subConectPre(ArrayList<String> mlstFilePath) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_subscriberConnect");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:subscriberConnect>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				HashMap<String, String> param = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<shopCode>").append(txtidcuahang.getText().toString());
				rawData.append("</shopCode>");

				if (txtmanv.getText() != null
						&& !txtmanv.getText().toString().isEmpty()) {
					rawData.append("<staffCode>").append(txtmanv.getText().toString());
					rawData.append("</staffCode>");
				}

				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<>(
                        mapListRecordPrepaid.values());
				for (int j = 0; j < arrayOfArrayList.size(); j++) {
					ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList
							.get(j);
					View rowView = lvUploadImage.getChildAt(j);
					ViewHolder h = (ViewHolder) rowView.getTag();
					if (h != null) {
						int indexSelection = h.spUploadImage
								.getSelectedItemPosition();
						RecordPrepaid recordPrepaid = listRecordPrepaid
								.get(indexSelection);
						rawData.append("<lstRecordName>");
						rawData.append(recordPrepaid.getName());
						rawData.append("</lstRecordName>");
						rawData.append("<lstRecordCode>");
						rawData.append(recordPrepaid.getCode());
						rawData.append("</lstRecordCode>");
					}
				}

				for (String fileObj : mlstFilePath) {
					rawData.append("<lstFilePath>");
					rawData.append(fileObj);
					rawData.append("</lstFilePath>");
				}

				rawData.append("<fileContent>");
				rawData.append("");
				rawData.append("</fileContent>");

				if ("1".equalsIgnoreCase(subType)) {
					rawData.append("<isPospaid>" + false + "</isPospaid>");
				} else {
					rawData.append("<isPospaid>" + true + "</isPospaid>");
				}

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<subscriberConnectedBO>");

				rawData.append("<objectCode>").append(objectCode);
				rawData.append("</objectCode>");

				Log.d("Log", "Check object code: " + objectCode);

				rawData.append("<objectType>" + "2");
				rawData.append("</objectType>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<noSendFile>" + "NO_SEND_FILE");
				rawData.append("</noSendFile>");
				// ========customer ===============
				rawData.append("<customer>");

				rawData.append("<sex>").append(cusByIdNoBean.getSex());
				rawData.append("</sex>");
				rawData.append("<name>").append(StringUtils.escapeHtml(cusByIdNoBean
                        .getNameCustomer()));
				rawData.append("</name>");
				rawData.append("<address>" + StringUtils.escapeHtml(cusByIdNoBean.getAddreseCus()));
				rawData.append("</address>");
				rawData.append("<areaCode>").append(cusByIdNoBean.getAreaCode());
				rawData.append("</areaCode>");
				if (cusByIdNoBean.getCustId() != null
						&& !cusByIdNoBean.getCustId().isEmpty()) {
					rawData.append("<strBirthDate>").append(cusByIdNoBean.getBirthdayCus());
					rawData.append("</strBirthDate>");
				} else {
					rawData.append("<strBirthDate>").append(cusByIdNoBean.getBirthdayCusNew());
					rawData.append("</strBirthDate>");
				}
				if (cusByIdNoBean.getStrIdExpire() != null
						&& !cusByIdNoBean.getStrIdExpire().isEmpty()) {
					rawData.append("<strIdExpireDate>").append(cusByIdNoBean.getStrIdExpire());
					rawData.append("</strIdExpireDate>");
				}

				rawData.append("<busType>").append(cusByIdNoBean.getCusType());
				rawData.append("</busType>");
				rawData.append("<custId>").append(cusByIdNoBean.getCustId());
				rawData.append("</custId>");
				rawData.append("<province>").append(cusByIdNoBean.getProvince());
				rawData.append("</province>");
				rawData.append("<district>").append(cusByIdNoBean.getDistrict());
				rawData.append("</district>");
				rawData.append("<precinct>").append(cusByIdNoBean.getPrecint());
				rawData.append("</precinct>");
				rawData.append("<idNo>").append(cusByIdNoBean.getIdNo());
				rawData.append("</idNo>");
				rawData.append("<strIdIssueDate>").append(cusByIdNoBean.getIdIssueDate());
				rawData.append("</strIdIssueDate>");
				rawData.append("<idIssuePlace>").append(cusByIdNoBean.getIdIssuePlace());
				rawData.append("</idIssuePlace>");
				if (cusByIdNoBean.getIdType() != null) {
					rawData.append("<idType>").append(cusByIdNoBean.getIdType());
					rawData.append("</idType>");
				}

				rawData.append("</customer>");

				// ======= subMb =====thong tin thue bao

				if (serviceType.equalsIgnoreCase("H")) {
					rawData.append("<subHp>");
				} else {
					rawData.append("<subMb>");
				}

				rawData.append("<isdn>").append(standardIsdn(txtisdn.getText().toString().trim()));
				rawData.append("</isdn>");
				rawData.append("<serial>").append(txtserial.getText().toString());
				rawData.append("</serial>");
				rawData.append("<productCode>").append(productCode);
				rawData.append("</productCode>");
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");
				rawData.append("<province>").append(province);
				rawData.append("</province>");
				rawData.append("<district>").append(district);
				rawData.append("</district>");
				rawData.append("<precinct>").append(precinct);
				rawData.append("</precinct>");
				rawData.append("<regType>").append(hthm);
				rawData.append("</regType>");
				rawData.append("<regReasonId>").append(reasonId);
				rawData.append("</regReasonId>");
				rawData.append("<addressCode>").append(province).append(district).append(precinct);
				rawData.append("</addressCode>");
				try {
					GetAreaDal dal = new GetAreaDal(getActivity());
					dal.open();
					String fulladdress = dal.getfulladddress(province
							+ district + precinct);
					rawData.append("<address>").append(fulladdress);
					rawData.append("</address>");
					dal.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (serviceType.equalsIgnoreCase("H")) {
					rawData.append("</subHp>");
				} else {
					rawData.append("</subMb>");
				}

				// =============raw data add hang hoa
				ArrayList<StockTypeDetailBeans> arrApStockModelBeanBOs = new ArrayList<>();
				arrApStockModelBeanBOs.addAll(mapSubStockModelRelReq.values());
				if (arrApStockModelBeanBOs.size() > 0) {
					for (StockTypeDetailBeans objAPStockModelBeanBO : arrApStockModelBeanBOs) {
						HashMap<String, String> rawDataItem = new HashMap<>();
						rawDataItem.put("stockModelId",
								objAPStockModelBeanBO.getStockModelId());
						rawDataItem.put("stockModelName",
								objAPStockModelBeanBO.getStockModelName());
						rawDataItem.put("serial",
								objAPStockModelBeanBO.getSerial());
						rawDataItem.put("stockTypeId",
								objAPStockModelBeanBO.getStockTypeId());
						param.put("lstSubStockModelRel",
								input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));
					}
				}

				// form specical paper

				if (offerId.equalsIgnoreCase(Constant.OFFERID_TSV)) {
					rawData.append("<proSpecMgrFormPre>");
					rawData.append("<idNo>").append(cusByIdNoBean.getIdNo()).append("</idNo>");
					rawData.append("<departmentCode>").append(mCodeDonVi).append("</departmentCode>");
					rawData.append("<endDatetime>").append(mNgayKetThuc).append("</endDatetime>");
					rawData.append("<nationCode>" + "VIE" + "</nationCode>");
					rawData.append("<objectCode>").append(mCodeDoiTuong).append("</objectCode>");
					rawData.append("<orderNumber>").append(edtMaGiayToDacBiet.getText().toString()).append("</orderNumber>");
					rawData.append("<startDatetime>").append(mNgayBatDau).append("</startDatetime>");
					rawData.append("</proSpecMgrFormPre>");
				}

				rawData.append("</subscriberConnectedBO>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:subscriberConnect>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_subscriberConnect");
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
				e.printStackTrace();
			}
			return errorCode;

		}

	}

	// ========== dau noi tra truoc==========

	private class SubConnectPosAsyn extends AsyncTask<String, String, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ArrayList<String> lstFilePath = new ArrayList<>();

		public SubConnectPosAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.subconnecting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {

					File folder = new File(
							Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
					if (!folder.exists()) {
						folder.mkdir();
					}
					Log.d("Log",
							"Folder zip file create: "
									+ folder.getAbsolutePath());
					for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
							.entrySet()) {
						ArrayList<FileObj> listFileObjs = entry.getValue();
						String zipFilePath = "";
						if (listFileObjs.size() > 1) {
							String spinnerCode = "";
							for (FileObj fileObj : listFileObjs) {
								spinnerCode = fileObj.getCodeSpinner();
							}
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_"
									+ spinnerCode + ".zip";
							lstFilePath.add(zipFilePath);
						} else if (listFileObjs.size() == 1) {
							zipFilePath = folder.getPath() + File.separator
									+ System.currentTimeMillis() + "_"
									+ listFileObjs.get(0).getCodeSpinner()
									+ ".jpg";
							lstFilePath.add(zipFilePath);
						}
					}
				}
                return subConectPos(lstFilePath);
			} catch (Exception e) {
				errorCode = "1";
				description = "Error when zip file: " + e.toString();
				return "1";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();


			if (errorCode.equals("0")) {
				if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
					AsynZipFile asynZipFile = new AsynZipFile(getActivity(),
							hashmapFileObj, lstFilePath, description);
					asynZipFile.execute();
				}
				// lnButton.setVisibility(View.GONE);
				// CommonActivity.createAlertDialog(getActivity(),
				// getString(R.string.daunoithanhcong),
				// getString(R.string.app_name), chuyenquamanhinhactive)
				// .show();
				//
				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
				// for (FileObj fileObj : arrFileBackUp) {
				// fileObj.setActionCode("00"); // đấu nối : 00 đăng
				// // ký thông tin 04
				// fileObj.setReasonId(reasonId);
				// fileObj.setIsdn(isdn);
				// fileObj.setActionAudit(description);
				// fileObj.setPageSize(0 + "");
				// fileObj.setStatus(0);
				// }
				// FileUtils.insertFileBackUpToDataBase(arrFileBackUp,
				// activity.getApplicationContext());
				// }

				// for (java.util.Map.Entry<String, ArrayList<FileObj>> entry :
				// hashmapFileObj.entrySet()) {
				// ArrayList<FileObj> listFileObjs = entry.getValue();
				//
				// for (int i = 0; i < listFileObjs.size(); i++) {
				// FileObj fileObj = listFileObjs.get(i);
				// fileObj.setActionCode("00"); // đấu nối : 00 đăng ký
				// thông tin 04
				// fileObj.setReasonId(reasonId);
				// fileObj.setIsdn(isdn);
				// fileObj.setActionAudit(description);
				// fileObj.setPageIndex(i + "");
				// fileObj.setPageSize(listFileObjs.size() + "");
				// fileObj.setStatus(0);
				// }
				// }
				//
				// FileUtils.backUpImageFile(hashmapFileObj,activity.getApplicationContext());

			} else {

				// if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
				// for (FileObj fileObj : arrFileBackUp) {
				// File tmp = new File(fileObj.getPath());
				// tmp.delete();
				// }
				// }

				lnButton.setVisibility(View.VISIBLE);
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					// Dialog dialog = CommonActivityo
					// .createAlertDialog(
					// (Activity) context,
					// description,
					// context.getResources().getString(
					// R.string.app_name), moveLogInAct);
					// dialog.show();
					LoginDialog dialog = new LoginDialog(getActivity(),
							";connect_smartsim;");

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

		private String subConectPos(ArrayList<String> mlstFilePath) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_subscriberConnectSmartphone");

				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:subscriberConnectSmartphone>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				HashMap<String, String> param = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<shopCode>").append(txtidcuahang.getText().toString());
				rawData.append("</shopCode>");

				if (txtmanv.getText() != null
						&& !txtmanv.getText().toString().isEmpty()) {
					rawData.append("<staffCode>").append(txtmanv.getText().toString());
					rawData.append("</staffCode>");
				}

				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<>(
                        mapListRecordPrepaid.values());
				for (int j = 0; j < arrayOfArrayList.size(); j++) {
					ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList
							.get(j);
					View rowView = lvUploadImage.getChildAt(j);
					ViewHolder h = (ViewHolder) rowView.getTag();
					if (h != null) {
						int indexSelection = h.spUploadImage
								.getSelectedItemPosition();
						RecordPrepaid recordPrepaid = listRecordPrepaid
								.get(indexSelection);
						rawData.append("<lstRecordName>");
						rawData.append(recordPrepaid.getName());
						rawData.append("</lstRecordName>");
						rawData.append("<lstRecordCode>");
						rawData.append(recordPrepaid.getCode());
						rawData.append("</lstRecordCode>");
					}
				}

				for (String fileObj : mlstFilePath) {
					rawData.append("<lstFilePath>");
					rawData.append(fileObj);
					rawData.append("</lstFilePath>");
				}

				rawData.append("<fileContent>");
				rawData.append("");
				rawData.append("</fileContent>");

				if ("1".equalsIgnoreCase(subType)) {
					rawData.append("<isPospaid>" + false + "</isPospaid>");
				} else {
					rawData.append("<isPospaid>" + true + "</isPospaid>");
				}

				rawData.append("<isConnect>");
				rawData.append(true);
				rawData.append("</isConnect>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("<smartphoneInputInfoSub>");
				// Them objectCode
				rawData.append("<objectCode>").append(objectCode);
				rawData.append("</objectCode>");

				//
				rawData.append("<objectType>" + "2");
				rawData.append("</objectType>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("<noSendFile>" + "NO_SEND_FILE");
				rawData.append("</noSendFile>");
				// ========contract ==============
				rawData.append("<contract>");
				if (contractNo != null && !contractNo.isEmpty()) {
					rawData.append("<contractId>").append(contractId);
					rawData.append("</contractId>");
					rawData.append("<contractNo>").append(contractNo);
					rawData.append("</contractNo>");

				} else {
					rawData.append("<province>").append(province);
					rawData.append("</province>");
					rawData.append("<district>").append(district);
					rawData.append("</district>");
					rawData.append("<precinct>").append(precinct);
					rawData.append("</precinct>");
					try {
						GetAreaDal dal = new GetAreaDal(getActivity());
						dal.open();
						String fulladdress = dal.getfulladddress(province
								+ district + precinct);
						rawData.append("<address>").append(fulladdress);
						rawData.append("</address>");
						dal.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (to != null && !to.isEmpty()) {
						rawData.append("<streetBlockName>").append(to);
						rawData.append("</streetBlockName>");
					}
					if (tenduong != null && !tenduong.isEmpty()) {
						rawData.append("<streetName>").append(tenduong);
						rawData.append("</streetName>");
					}
					if (sonha != null && !sonha.isEmpty()) {
						rawData.append("<home>").append(sonha);
						rawData.append("</home>");
					}
					// rawData.append("<address>" +
					// cusByIdNoBean.getAddreseCus());
					// rawData.append("</address>");

				}

				rawData.append("</contract>");

				// ========customer ===============
				rawData.append("<customer>");
				rawData.append("<sex>").append(cusByIdNoBean.getSex());
				rawData.append("</sex>");
				rawData.append("<name>").append(cusByIdNoBean.getNameCustomer());
				rawData.append("</name>");
				rawData.append("<address>" + StringUtils.escapeHtml(cusByIdNoBean.getAddreseCus()));
				rawData.append("</address>");
				rawData.append("<areaCode>").append(cusByIdNoBean.getAreaCode());
				rawData.append("</areaCode>");
				if (cusByIdNoBean.getCustId() != null
						&& !cusByIdNoBean.getCustId().isEmpty()) {
					rawData.append("<birthDateStr>").append(cusByIdNoBean.getBirthdayCus());
					rawData.append("</birthDateStr>");
				} else {
					rawData.append("<birthDateStr>").append(cusByIdNoBean.getBirthdayCusNew());
					rawData.append("</birthDateStr>");
				}

				if (cusByIdNoBean.getStrIdExpire() != null
						&& !cusByIdNoBean.getStrIdExpire().isEmpty()) {
					rawData.append("<idExpireDateStr>").append(cusByIdNoBean.getStrIdExpire());
					rawData.append("</idExpireDateStr>");
				}

				rawData.append("<busType>").append(cusByIdNoBean.getCusType());
				rawData.append("</busType>");
				rawData.append("<custId>").append(cusByIdNoBean.getCustId());
				rawData.append("</custId>");
				rawData.append("<province>").append(cusByIdNoBean.getProvince());
				rawData.append("</province>");
				rawData.append("<district>").append(cusByIdNoBean.getDistrict());
				rawData.append("</district>");
				rawData.append("<precinct>").append(cusByIdNoBean.getPrecint());
				rawData.append("</precinct>");
				rawData.append("<idNo>").append(cusByIdNoBean.getIdNo());
				rawData.append("</idNo>");
				rawData.append("<idIssueDateStr>").append(cusByIdNoBean.getIdIssueDate());
				rawData.append("</idIssueDateStr>");
				rawData.append("<idIssuePlace>").append(cusByIdNoBean.getIdIssuePlace());
				rawData.append("</idIssuePlace>");
				if (cusByIdNoBean.getIdType() != null) {
					rawData.append("<idType>").append(cusByIdNoBean.getIdType());
					rawData.append("</idType>");
				}

				rawData.append("</customer>");

				// ======= subMb =====thong tin thue bao
				rawData.append("<subscriber>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				if (to != null && !to.isEmpty()) {
					rawData.append("<streetBlockName>").append(to);
					rawData.append("</streetBlockName>");
				}
				if (tenduong != null && !tenduong.isEmpty()) {
					rawData.append("<streetName>").append(tenduong);
					rawData.append("</streetName>");
				}
				if (sonha != null && !sonha.isEmpty()) {
					rawData.append("<home>").append(sonha);
					rawData.append("</home>");
				}

				rawData.append("<isdn>").append(standardIsdn(txtisdn.getText().toString().trim()));
				rawData.append("</isdn>");
				rawData.append("<serial>").append(txtserial.getText().toString());
				rawData.append("</serial>");
				rawData.append("<promotionCode>").append(maKM);
				rawData.append("</promotionCode>");
				rawData.append("<subType>").append(subTypeMobile);
				rawData.append("</subType>");
				rawData.append("<productCode>").append(productCode);
				rawData.append("</productCode>");
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");
				rawData.append("<province>").append(province);
				rawData.append("</province>");
				rawData.append("<district>").append(district);
				rawData.append("</district>");
				rawData.append("<precinct>").append(precinct);
				rawData.append("</precinct>");
				rawData.append("<regType>").append(hthm);
				rawData.append("</regType>");
				rawData.append("<regReasonId>").append(reasonId);
				rawData.append("</regReasonId>");
				rawData.append("<addressCode>").append(province).append(district).append(precinct);
				rawData.append("</addressCode>");
				try {
					GetAreaDal dal = new GetAreaDal(getActivity());
					dal.open();
					String fulladdress = dal.getfulladddress(province
							+ district + precinct);
					rawData.append("<address>").append(fulladdress);
					rawData.append("</address>");
					dal.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				rawData.append("</subscriber>");

				// =============raw data add hang hoa
				ArrayList<StockTypeDetailBeans> arrApStockModelBeanBOs = new ArrayList<>();
				arrApStockModelBeanBOs.addAll(mapSubStockModelRelReq.values());
				if (arrApStockModelBeanBOs.size() > 0) {
					for (StockTypeDetailBeans objAPStockModelBeanBO : arrApStockModelBeanBOs) {
						HashMap<String, String> rawDataItem = new HashMap<>();
						rawDataItem.put("stockModelId",
								objAPStockModelBeanBO.getStockModelId());
						rawDataItem.put("stockModelName",
								objAPStockModelBeanBO.getStockModelName());
						rawDataItem.put("serial",
								objAPStockModelBeanBO.getSerial());
						rawDataItem.put("stockTypeId",
								objAPStockModelBeanBO.getStockTypeId());
						param.put("lstSubStockModelRel",
								input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));
					}
				}

				rawData.append("</smartphoneInputInfoSub>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:subscriberConnectSmartphone>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_subscriberConnectSmartphone");
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
				e.printStackTrace();
			}
			return errorCode;

		}

	}

	OnClickListener onbackManagerClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			lnButton.setVisibility(View.GONE);
			// getActivity().onBackPressed();
			// if (ActivityCreateNewRequestMobileSmartSim.instance != null) {
			// ActivityCreateNewRequestMobileSmartSim.instance.onBackPressed();
			// }
			// getActivity().finish();

		}
	};

	// lay ds hang hoa
	// ===== ws danh sach hang hoa tra sau=============
	public class GetListProductPosAsyn extends
			AsyncTask<String, Void, ArrayList<StockTypeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListProductPosAsyn(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<StockTypeBeans> doInBackground(String... arg0) {
			return getListProduct(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<StockTypeBeans> result) {
			this.progress.dismiss();

			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrStockTypeBeans.addAll(result);
					lvproduct.setVisibility(View.VISIBLE);
					adapProductAdapter = new GetProductAdapter(result,
							getActivity());
					lvproduct.setAdapter(adapProductAdapter);
					// setListView(arrStockTypeBeans);

				} else {
					lvproduct.setVisibility(View.GONE);
				}

			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private ArrayList<StockTypeBeans> getListProduct(String regType,
				String productCode, String serviceType) {
			ArrayList<StockTypeBeans> arrayListThongTinHH = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStockTypePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockTypePos>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("<regType>").append(regType);
				rawData.append("</regType>");

				rawData.append("<productCode>").append(productCode);
				rawData.append("</productCode>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListStockTypePos>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStockTypePos");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.i("original 69696", "original :" + original);

				// ============parse xml in android=========
				StockTypeHanlderPos handler = (StockTypeHanlderPos) CommonActivity
						.parseXMLHandler(new StockTypeHanlderPos(), original);
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
				arrayListThongTinHH = handler.getLstData();

			} catch (Exception e) {
				Log.d("getListProduct", e.toString());
			}

			return arrayListThongTinHH;
		}
	}

	// ===== ws danh sach hang hoa tra truoc=============
	public class GetListProductPreAsyn extends
			AsyncTask<String, Void, ArrayList<StockTypeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListProductPreAsyn(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<StockTypeBeans> doInBackground(String... arg0) {
			return getListProduct(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<StockTypeBeans> result) {
			this.progress.dismiss();

			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrStockTypeBeans.addAll(result);
					lvproduct.setVisibility(View.VISIBLE);
					adapProductAdapter = new GetProductAdapter(result,
							getActivity());
					lvproduct.setAdapter(adapProductAdapter);

				} else {
					lvproduct.setVisibility(View.GONE);
				}

			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private ArrayList<StockTypeBeans> getListProduct(String regType,
				String productCode, String serviceType) {
			ArrayList<StockTypeBeans> arrayListThongTinHH = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStockTypePre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockTypePre>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("<regType>").append(regType);
				rawData.append("</regType>");

				rawData.append("<productCode>").append(productCode);
				rawData.append("</productCode>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListStockTypePre>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStockTypePre");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.i("original 69696", "original :" + original);

				// ============parse xml in android=========
				StockTypeHanlderPre handler = (StockTypeHanlderPre) CommonActivity
						.parseXMLHandler(new StockTypeHanlderPre(), original);
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
				arrayListThongTinHH = handler.getLstData();

			} catch (Exception e) {
				Log.d("getListProduct", e.toString());
			}

			return arrayListThongTinHH;
		}
	}

	// ws view detail hang hoa tra truoc

	private class ViewDetailProductPreAsyn extends
			AsyncTask<String, Void, StockTypeDetailBeans> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String stockTypeId = "";

		public ViewDetailProductPreAsyn(Context context, String stockTypId) {
			this.context = context;
			this.stockTypeId = stockTypId;
			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected StockTypeDetailBeans doInBackground(String... params) {
			return sendRequestViewDetaiPre(params[0], stockTypeId);
		}

		@Override
		protected void onPostExecute(StockTypeDetailBeans result) {
			this.progress.dismiss();
			CommonActivity.hideKeyboard(edtserial, context);
			if (errorCode.equals("0")) {
				if (result.getStockModelId() != null
						&& !result.getStockModelId().isEmpty()
						&& result.getSerial() != null
						&& !result.getSerial().isEmpty()) {
					showDialogShowDetailProduct(stockTypeId, result);
				} else {
					Dialog dialog = CommonActivity
							.createAlertDialog(getActivity(), getResources()
									.getString(R.string.notdetailhanghoa),
									getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private StockTypeDetailBeans sendRequestViewDetaiPre(String serial,
				String stockId) {
			StockTypeDetailBeans stockDetailBeans = new StockTypeDetailBeans();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListStockModelByStockTypeAndSerialPre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockModelByStockTypeAndSerialPre>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<stockTypeId>").append(stockId);
				rawData.append("</stockTypeId>");

				rawData.append("<serial>").append(serial);
				rawData.append("</serial>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListStockModelByStockTypeAndSerialPre>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStockModelByStockTypeAndSerialPre");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.i("original 69696", "original :" + original);

				// === parrse xml =====
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);

					nodechild = doc.getElementsByTagName("stockSerialBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String stockTypeCode = parse.getValue(e1,
								"stockTypeCode");
						stockDetailBeans.setStockTypeCode(stockTypeCode);
						String stockModelId = parse
								.getValue(e1, "stockModelId");
						stockDetailBeans.setStockModelId(stockModelId);
						String serialpas = parse.getValue(e1, "serial");
						stockDetailBeans.setSerial(serialpas);
						String partnerName = parse.getValue(e1, "partnerName");
						stockDetailBeans.setPartnerName(partnerName);
						String stockModelName = parse.getValue(e1,
								"stockModelName");
						stockDetailBeans.setStockModelName(stockModelName);
						String stockModelCode = parse.getValue(e1,
								"stockModelCode");
						stockDetailBeans.setStockModelCode(stockModelCode);
						String price = parse.getValue(e1, "price");
						stockDetailBeans.setPrice(price);
					}
				}
			} catch (Exception e) {
				Log.d("sendRequestViewDetaiPre", e.toString());
			}

			return stockDetailBeans;
		}

	}

	private void showDialogInsertSerial(final String stockTypeId) {
		dialogInsertSerial = new Dialog(getActivity());
		dialogInsertSerial.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogInsertSerial
				.setContentView(R.layout.connectionmobile_serial_dialog);
		final TextView texteror = (TextView) dialogInsertSerial
				.findViewById(R.id.texterror);
		edtserial = (EditText) dialogInsertSerial.findViewById(R.id.edtserial);
		dialogInsertSerial.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (edtserial.getText() != null
								&& !edtserial.getText().toString().isEmpty()) {
							if (!StringUtils.CheckCharSpecical(edtserial
                                    .getText().toString())) {
								// set object hang hoa

								if (CommonActivity
                                        .isNetworkConnected(getActivity())) {

									ViewDetailProductPreAsyn viewProductPreAsyn = new ViewDetailProductPreAsyn(
											getActivity(), stockTypeId);
									viewProductPreAsyn.execute(edtserial
											.getText().toString(), stockTypeId);

								} else {
									CommonActivity.createAlertDialog(
											getActivity(),
											getString(R.string.errorNetwork),
											getString(R.string.app_name))
											.show();
								}

							} else {
								texteror.setVisibility(View.VISIBLE);
								texteror.setText(getString(R.string.checkspecialSerial));
							}
						} else {
							texteror.setVisibility(View.VISIBLE);
							texteror.setText(getString(R.string.checkserial));
						}

					}
				});
		dialogInsertSerial.findViewById(R.id.btnViewSaleTrans)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialogInsertSerial.dismiss();
					}
				});
		dialogInsertSerial.show();

	}

	// lay ngay bat dau va ngay ket thuc
    private void showDatePickerDialog(final EditText tvNgay) {
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Date
				switch (tvNgay.getId()) {
				case R.id.edtNgayBD:
					mNgayBatDau = (dayOfMonth) + "/" + (monthOfYear + 1) + "/"
							+ year;
					edtNgayBD.setText(mNgayBatDau);
					try {
						dateBD = DateTimeUtils.convertStringToTime(mNgayBatDau,
								"dd/MM/yyyy");
					} catch (Exception e) {
						e.printStackTrace();
					}
					// TODO modify ngay 08/07/2015 === thinhhq1
					if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
						// neu la doi tuong tan sinh vien + 5 năm ngay bat dau
						// va enable
						String[] ngayketthuc = edtNgayBD.getText().toString()
								.split("/");
						if (ngayketthuc.length == 3) {
							int yearKT = Integer.parseInt(ngayketthuc[2]) + 5;
							mNgayKetThuc = ngayketthuc[0] + "/"
									+ ngayketthuc[1] + "/" + yearKT;
							edtNgayKT.setText(mNgayKetThuc);
							edtNgayKT.setEnabled(false);
						}

					} else {
						edtNgayKT.setEnabled(true);
					}
					break;
				case R.id.edtNgayKT:

					break;

				default:
					break;
				}
				cal.set(year, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, callback,
				year, month, day);
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	private boolean validateViewCommitment() {

		if (CommonActivity.isNullOrEmpty(offerId)) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkofferid),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(hthm)) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkregtype),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(txtisdn.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkisdncm),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		return true;
	}

	private void showDialogShowDetailProduct(final String stockTypeId,
			final StockTypeDetailBeans stockDetailBeans) {
		dialogShowDetailProduct = new Dialog(getActivity());
		dialogShowDetailProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogShowDetailProduct
				.setContentView(R.layout.connectionmobile_detailproduct_dialog);
		final EditText edtloaimathang = (EditText) dialogShowDetailProduct
				.findViewById(R.id.edtloaimathang);
		final EditText edtSerial = (EditText) dialogShowDetailProduct
				.findViewById(R.id.edtSerial);
		final EditText edttenmathang = (EditText) dialogShowDetailProduct
				.findViewById(R.id.edttenmathang);
		final EditText edtmamathang = (EditText) dialogShowDetailProduct
				.findViewById(R.id.edtmamathang);
		final EditText edtnhasx = (EditText) dialogShowDetailProduct
				.findViewById(R.id.edtnhasx);
		final EditText edtgia = (EditText) dialogShowDetailProduct
				.findViewById(R.id.edtgia);
		// if(stockDetailBeans.getStockTypeCode() != null &&
		// !stockDetailBeans.getStockTypeCode().isEmpty()){
		// edtloaimathang.setText(stockDetailBeans.getStockTypeCode());
		// }
		//
		if (stockDetailBeans.getSerial() != null
				&& !stockDetailBeans.getSerial().isEmpty()) {
			edtSerial.setText(stockDetailBeans.getSerial());
		}

		if (stockDetailBeans.getStockModelName() != null
				&& !stockDetailBeans.getStockModelName().isEmpty()) {
			edttenmathang.setText(stockDetailBeans.getStockModelName());
		}

		if (stockDetailBeans.getStockModelCode() != null
				&& !stockDetailBeans.getStockModelCode().isEmpty()) {
			edtmamathang.setText(stockDetailBeans.getStockModelCode());
		}

		if (stockDetailBeans.getPartnerName() != null
				&& !stockDetailBeans.getPartnerName().isEmpty()) {
			edtnhasx.setText(stockDetailBeans.getPartnerName());
		}

		// if(stockDetailBeans.getPrice() != null &&
		// !stockDetailBeans.getPrice().isEmpty()){
		// edtgia.setText(StringUtils.formatMoney(stockDetailBeans.getPrice()));
		// }
		Button btncamket = (Button) dialogShowDetailProduct
				.findViewById(R.id.btncamket);
		if ("2".equals(subType)) {
			btncamket.setVisibility(View.VISIBLE);
		} else {
			btncamket.setVisibility(View.GONE);
		}
		btncamket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNetworkConnected(getActivity())) {
					if (validateViewCommitment()) {
						AsynctaskviewSubCommitmentStock asynctaskviewSubCommitmentStock = new AsynctaskviewSubCommitmentStock(
								getActivity(), stockDetailBeans.getSerial());
						asynctaskviewSubCommitmentStock.execute(offerId, hthm,
								txtisdn.getText().toString().trim(),
								stockDetailBeans.getSerial(),
								stockDetailBeans.getStockModelId());
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}

			}
		});
		dialogShowDetailProduct.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						stockDetailBeans.setStockTypeId(stockTypeId);
						mapSubStockModelRelReq.put(stockTypeId,
								stockDetailBeans);

						if (dialogInsertSerial != null) {
							dialogInsertSerial.dismiss();
						}
						dialogShowDetailProduct.dismiss();

					}
				});
		dialogShowDetailProduct.findViewById(R.id.btnViewSaleTrans)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialogShowDetailProduct.dismiss();
					}
				});
		dialogShowDetailProduct.show();

	}

	// Ham lay thong tin doi duong tu offerid va idNo
	private class GetProductSpecInfoPreAsyn extends
			AsyncTask<String, Void, ArrayList<SpecObject>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetProductSpecInfoPreAsyn(Context context) {
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
		protected ArrayList<SpecObject> doInBackground(String... arg0) {
			return getLstObjectSpec(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<SpecObject> result) {
			progress.dismiss();

			// truong hop co giay to dac biet == danh sach cac doi duong
			if (errorCode.equalsIgnoreCase("0")) {
				if (result != null && !result.isEmpty()) {
					arrSpecObjects = result;

					// hien thi form thong tin dac biet len
					lnTTGoiCuocDacBiet.setVisibility(View.VISIBLE);
					// do du lieu vao spin doi tuong
					// mCodeDoiTuong = arrSpecObjects.get(0).getCode();
					doiTuongAdapter = new ObjDoiTuongAdapter(arrSpecObjects,
							getActivity());
					spDoiTuong.setAdapter(doiTuongAdapter);
					for (SpecObject specObject : arrSpecObjects) {
						if (Constant.CODE_TSV.equalsIgnoreCase(specObject
								.getCode())) {
							spDoiTuong.setSelection(arrSpecObjects
									.indexOf(specObject));
							spDoiTuong.setEnabled(false);
							break;
						}
					}
				} else {
					// Dialog dialog = CommonActivity.createAlertDialog(
					// getActivity(), description, getResources()
					// .getString(R.string.app_name));
					// dialog.show();
					// hide form thong tin dac biet di va reload thong tin dac
					// biet
					spDoiTuong.setEnabled(true);
					mCodeDoiTuong = "";
					mCodeDonVi = "";
					lnTTGoiCuocDacBiet.setVisibility(View.GONE);
					if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
						arrSpecObjects.clear();
					}
					if (doiTuongAdapter != null) {
						doiTuongAdapter.notifyDataSetChanged();
					}
				}
			} else {

				spDoiTuong.setEnabled(true);
				mCodeDoiTuong = "";
				mCodeDonVi = "";
				lnTTGoiCuocDacBiet.setVisibility(View.GONE);
				if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
					arrSpecObjects.clear();
				}
				if (doiTuongAdapter != null) {
					doiTuongAdapter.notifyDataSetChanged();
				}

				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else if (errorCode.equalsIgnoreCase("1")) {

					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes1);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
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

		private ArrayList<SpecObject> getLstObjectSpec(String offerId,
				String idNo) {
			ArrayList<SpecObject> arrayList = new ArrayList<>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getProductSpecInfoPre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getProductSpecInfoPre>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("<idNo>").append(idNo);
				rawData.append("</idNo>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getProductSpecInfoPre>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getProductSpecInfoPre");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.i("original 69696", "original :" + original);

				// parse xml
				// === parrse xml =====
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);

					nodechild = doc.getElementsByTagName("lstObjectSpecPre");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						String code = parse.getValue(e1, "code");
						String codeName = parse.getValue(e1, "codeName");
						String id = parse.getValue(e1, "id");
						int ID = Integer.parseInt(id);
//						SpecObject obj = new SpecObject(ID, code, codeName);
//						arrayList.add(obj);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return arrayList;
		}

	}

	private class CheckInfoCusSpecialAsyn extends
			AsyncTask<String, Void, ArrayList<StudentBean>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public CheckInfoCusSpecialAsyn(Context context) {
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
		protected ArrayList<StudentBean> doInBackground(String... arg0) {
			return sendReqCheckInfoCus(arg0[0]);
		}

		@Override
		protected void onPostExecute(final ArrayList<StudentBean> result) {
			progress.dismiss();
			int dem = 0;
			if ("0".equalsIgnoreCase(errorCode)) {
				if (result != null && !result.isEmpty()) {

					final Dialog dialog = new Dialog(getActivity());
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.connection_layout_lst_sobaodanh);

					ListView lvsobaodanh = (ListView) dialog
							.findViewById(R.id.lvsobaodanh);

					Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);

					GetSobaodanhAdapter getSobaodanhAdapter = new GetSobaodanhAdapter(
							result, getActivity());
					lvsobaodanh.setAdapter(getSobaodanhAdapter);

					lvsobaodanh
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									StudentBean studentBean = result.get(arg2);
									if (!CommonActivity
											.isNullOrEmpty(studentBean
													.getSoBD())) {
										edtMaGiayToDacBiet.setText(studentBean
												.getSoBD());
										edtMaGiayToDacBiet.setEnabled(false);
									}

									if (!CommonActivity
											.isNullOrEmpty(studentBean
													.getMaTruongDKTT())) {
										mCodeDonVi = studentBean
												.getMaTruongDKTT();
										tvDonVi.setText(mCodeDonVi);
									}

									dialog.dismiss();
								}
							});

					btnhuy.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});

					dialog.show();

					// for (StudentBean studentBean : result) {
					// if(studentBean.getMaTruongDKTT() != null &&
					// !studentBean.getMaTruongDKTT().isEmpty()){
					// if(studentBean.getSoBD() != null &&
					// !studentBean.getSoBD().isEmpty()){
					// edtMaGiayToDacBiet.setText(studentBean.getSoBD());
					// edtMaGiayToDacBiet.setEnabled(false);
					// dem ++;
					// rlchondonvi.setVisibility(View.VISIBLE);
					// mCodeDonVi = studentBean.getMaTruongDKTT();
					// tvDonVi.setText(mCodeDonVi);
					// tvDonVi.setEnabled(false);
					// break;
					// }
					// }
					// }
					//
					// // truong hop ko co ma truong thi cho chon don vi
					// if(dem == 0){
					// rlchondonvi.setVisibility(View.VISIBLE);
					// edtMaGiayToDacBiet.setText(result.get(0).getSoBD());
					// edtMaGiayToDacBiet.setEnabled(false);
					// tvDonVi.setEnabled(true);
					// }else{
					// }
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getString(R.string.checkinfocusnewst),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)
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

		private ArrayList<StudentBean> sendReqCheckInfoCus(String soCMT) {
			ArrayList<StudentBean> lstSoBD = new ArrayList<>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getThongTinThiSinh");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getThongTinThiSinh>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<soBD>" + "");
				rawData.append("</soBD>");
				rawData.append("<soCMT>").append(soCMT);
				rawData.append("</soCMT>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getThongTinThiSinh>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getThongTinThiSinh");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("ns3:ThiSinhModel");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						StudentBean studentBean = new StudentBean();
						String sobaodanh = parse.getValue(e1, "ns3:SoBaoDanh");
						if (sobaodanh != null && !sobaodanh.isEmpty()) {
							studentBean.setSoBD(sobaodanh);
							Log.d("sobaodanh", sobaodanh);
						}
						String matruongDKDT = parse.getValue(e1,
								"ns3:MaTruongDKDT");
						studentBean.setMaTruongDKTT(matruongDKDT);

						String hoten = parse.getValue(e1, "ns3:Hoten");
						studentBean.setHoten(hoten);

						String ngaysinh = parse.getValue(e1, "ns3:NgaySinh");
						studentBean.setNgaysinh(ngaysinh);

						String gioitinh = parse.getValue(e1, "ns3:GioiTinh");
						studentBean.setGioiTinh(gioitinh);

						String soCMND = parse.getValue(e1, "ns3:SoCMND");
						studentBean.setSoCMND(soCMND);

						String diachi = parse.getValue(e1, "ns3:DiaChi");
						studentBean.setDiaChi(diachi);

						lstSoBD.add(studentBean);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstSoBD;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		showDialogInsertSerial(arrStockTypeBeans.get(arg2).getStockId());
	}

	private int check = 1;

	private void showPopupSelectIsdn() {

		check = 1;

		dialogSearchIsdn = new Dialog(getActivity());
		dialogSearchIsdn.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogSearchIsdn.setContentView(R.layout.connection_layout_lst_isdn);

		final EditText edttuso = (EditText) dialogSearchIsdn
				.findViewById(R.id.txtsdt);
		lvsodienthoai = (ListView) dialogSearchIsdn
				.findViewById(R.id.lvsodienthoai);
        RadioGroup radiogroupcheck = (RadioGroup) dialogSearchIsdn
                .findViewById(R.id.radiogroupcheck);
		radiogroupcheck
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radiocuahang) {
							check = 1;
						} else {
							check = 2;
						}
					}
				});
		Button btntimkiem = (Button) dialogSearchIsdn
				.findViewById(R.id.btntimkiem);

		if (arrIsdn != null && !arrIsdn.isEmpty()) {
			adapterIsdn = new GetPstnAdapter(arrIsdn, getActivity());
			lvsodienthoai.setAdapter(adapterIsdn);
		}

		btntimkiem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (edttuso.getText() != null
						&& !edttuso.getText().toString().isEmpty()) {
					if (arrIsdn != null && !arrIsdn.isEmpty()) {
						arrIsdn.clear();
					}
					if (adapterIsdn != null) {
						adapterIsdn.notifyDataSetChanged();
					}
					LookupIsdnAsyn lookupIsdnAsyn = new LookupIsdnAsyn(
							getActivity());
					lookupIsdnAsyn.execute(standardIsdn(edttuso.getText()
							.toString().trim()), "" + check);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.validate_phone),
							getString(R.string.app_name)).show();
				}

			}
		});

		lvsodienthoai.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				txtisdn.setText(arrIsdn.get(arg2));
				dialogSearchIsdn.dismiss();
			}
		});

		Button btnhuy = (Button) dialogSearchIsdn.findViewById(R.id.btnhuy);
		btnhuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogSearchIsdn.dismiss();
			}
		});

		dialogSearchIsdn.show();
	}

	private class LookupIsdnAsyn extends
			AsyncTask<String, Void, ArrayList<String>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public LookupIsdnAsyn(Context context) {
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
		protected ArrayList<String> doInBackground(String... arg0) {
			return lookupIsdn(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {

					arrIsdn = result;

					adapterIsdn = new GetPstnAdapter(result, getActivity());
					lvsodienthoai.setAdapter(adapterIsdn);

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.no_data),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

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

		private ArrayList<String> lookupIsdn(String isdn, String stockType) {
			ArrayList<String> lstIsdn = new ArrayList<>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_lookupIsdn");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:lookupIsdn>");
				rawData.append("<input>");

				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");

				rawData.append("<isdn>").append(isdn);
				rawData.append("</isdn>");

				rawData.append("<stockType>").append(stockType);
				rawData.append("</stockType>");

				rawData.append("</input>");
				rawData.append("</ws:lookupIsdn>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_lookupIsdn");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstIsdn");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						String isdnPstn = XmlDomParse.getCharacterDataFromElement(e1);
						Log.d("isdnPstn", isdnPstn);
						lstIsdn.add(isdnPstn);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstIsdn;
		}

	}

	private String standardIsdn(String isdn) {
		if (isdn != null && !isdn.isEmpty()) {
			if (isdn.startsWith("00")) {
				isdn = isdn.substring(2);
			}
			if (isdn.startsWith("0")) {
				isdn = isdn.substring(1);
			}
			if (isdn.startsWith("+")) {
				isdn = isdn.substring(1);
			}
			if (isdn.startsWith("255")) {
				isdn = isdn.substring(3);
			}
		}
		return isdn;
	}

	@SuppressWarnings("unused")
	private class GetListGroupAdressAsyncTask extends
			AsyncTask<String, Void, ArrayList<AreaObj>> {

		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		@SuppressWarnings("unused")
		public GetListGroupAdressAsyncTask(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			if (btnRefreshStreetBlock != null) {
				btnRefreshStreetBlock.setVisibility(View.GONE);
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {

			return getListGroupAddress(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {

			super.onPostExecute(result);
			progress.dismiss();
			if (btnRefreshStreetBlock != null) {
				btnRefreshStreetBlock.setVisibility(View.VISIBLE);
			}
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {

					arrTo = result;
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					for (AreaObj areaBean : result) {
						adapter.add(areaBean.getName());
					}

					spinner_to.setAdapter(adapter);

				} else {
					arrTo = new ArrayList<>();
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					for (AreaObj areaBean : result) {
						adapter.add(areaBean.getName());
					}

					spinner_to.setAdapter(adapter);
					// CommonActivity.createAlertDialog(getActivity(),
					// getString(R.string.ko_co_dl_precint),
					// getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
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

		private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
			ArrayList<AreaObj> listGroupAdress = null;

			listGroupAdress = new CacheDatabaseManager(
					MainActivity.getInstance())
					.getListCacheStreetBlock(parentCode);
			if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
				errorCode = "0";
				return listGroupAdress;
			}
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListArea");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListArea>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<parentCode>").append(parentCode).append("</parentCode>");
				rawData.append("</input>");
				rawData.append("</ws:getListArea>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input
						.sendRequest(envelope, Constant.BCCS_GW_URL,
								getActivity(), "mbccs_getListArea");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
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
			ArrayList<AreaObj> listGroupAdress = new ArrayList<>();
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

	private final OnClickListener imageListenner = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.d(Constant.TAG, "view.getId() : " + view.getId());
			ImagePreviewActivity.pickImage(activity, hashmapFileObj,
					view.getId());
		}
	};

	// LAY THONG TIN FILE HO SO UP LEN

	public class GetLisRecordPrepaidTask extends AsyncTask<Void, Void, String> {

		private final String productCode;
		private final ProgressDialog dialog;
		private final Context context;
		private final String reasonId;

		public GetLisRecordPrepaidTask(String reasonId, String productCode,
				Context context) {
			this.productCode = productCode;
			this.reasonId = reasonId;
			this.context = context;
			dialog = new ProgressDialog(context);
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			// this.dialog.show();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String original = null;
			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {
				original = requestSevice(reasonId, productCode);
			} else {
			}
			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);

				mapListRecordPrepaid = FragmentManageConnect
						.parseResultListRecordPrepaid(result,null);

				hashmapFileObj.clear();
				// Tao spinner upload anh
				List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<>(
                        mapListRecordPrepaid.values());
				RecordPrepaidAdapter adapter = new RecordPrepaidAdapter(
						activity, arrayOfArrayList, imageListenner);
				lvUploadImage.setAdapter(adapter);

				UI.setListViewHeightBasedOnChildren(lvUploadImage);
			}
			Log.e("TAG6", "result List productCode : " + result);

			super.onPostExecute(result);
		}
	}

	private String requestSevice(String reasonId, String productCode) {

		String reponse = null;
		String original = null;

		String xml = mBccsGateway.getXmlCustomer(
				createXML(reasonId, productCode), "mbccs_getListRecordPrepaid");
		Log.e("TAG8", "xml getListRecordPrepaid" + xml);
		try {
			reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
					getActivity(), "mbccs_getListRecordPrepaid");
			Log.e("TAG8", "reponse getListRecordPrepaid" + reponse);
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

	private String createXML(String reasonId, String productCode) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ws:getListRecordPrepaid>");
		stringBuilder.append("<cmInput>");
		stringBuilder.append("<isConnect>");
		stringBuilder.append(true);
		stringBuilder.append("</isConnect>");
		if ("1".equalsIgnoreCase(subType)) {
			stringBuilder.append("<isPospaid>" + false + "</isPospaid>");
		} else {
			stringBuilder.append("<isPospaid>" + true + "</isPospaid>");
		}
		stringBuilder.append("<serviceType>").append(serviceType).append("</serviceType>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<productCode>").append(productCode).append("</productCode>");
		stringBuilder.append("<reasonId>").append(reasonId).append("</reasonId>");
		stringBuilder.append("</cmInput>");
		stringBuilder.append("</ws:getListRecordPrepaid>");
		return stringBuilder.toString();
	}

	private void pickImage(final int imageId) {
		Log.d(Constant.TAG, "pickImage() : " + imageId);

		if (hashmapFileObj.containsKey(String.valueOf(imageId))) {
			ArrayList<FileObj> fileObjs = hashmapFileObj.get(String
					.valueOf(imageId));

			Intent intent = new Intent(getActivity(),
					ImagePreviewActivity.class);
			intent.putExtra("imageId", imageId);
			intent.putExtra("fileObjs", fileObjs);

			startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
		} else {
			Config config = new Config.Builder().setSelectionLimit(4).build();

			Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
			intent.putExtra("imageId", imageId);

			ImagePickerActivity.setConfig(config);
			startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
		}
	}

	private File zipFileImage(List<FileObj> _files, String zipFilePath) {
		File zipfile = new File(zipFilePath);
		try {
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(
					zipfile));
			for (FileObj obj : _files) {
				ZipEntry entry = new ZipEntry(obj.getName());
				zout.putNextEntry(entry);

				long length = obj.getFile().length();
				int percent = length < Constant.MAX_SIZE_IMG ? 100
						: (int) (Constant.MAX_SIZE_IMG * 100 / length);
				// Bitmap bitmap =
				// BitmapFactory.decodeFile(obj.getFile().getPath());
				Bitmap bitmap = null;
				if (obj.getFile().length() < Constant.MAX_SIZE_IMG) {
					bitmap = BitmapFactory.decodeFile(obj.getFile().getPath());
				} else {
					bitmap = FileUtils.decodeBitmapFromFile(obj.getFile()
							.getPath(), 2000, 2000);
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, percent, baos);
				byte[] buffer = baos.toByteArray();
				Log.v("Compress", "Adding length: " + length + " percent: "
						+ percent + " buffer: " + buffer.length + " "
						+ obj.getFile().getPath());
				zout.write(buffer);
				baos.close();
			}
			try {
				zout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return zipfile;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void showDialogViewCommitment(StockIsdnBean stockIsdnBean) {

		final Dialog dialogViewCommitment = new Dialog(activity);
		dialogViewCommitment.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogViewCommitment.setContentView(R.layout.dialog_layout_commitment);

		TextView tvDialogTitle = (TextView) dialogViewCommitment
				.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(getActivity().getString(R.string.ttincamketso));
		TextView tvIsdn = (TextView) dialogViewCommitment
				.findViewById(R.id.tvIsdn);
		tvIsdn.setText(stockIsdnBean.getIsdn());
		TextView tvmoney = (TextView) dialogViewCommitment
				.findViewById(R.id.tvmoney);
		tvmoney.setText(StringUtils.formatMoney(stockIsdnBean.getPrice()));
		TextView tvStockModelCode = (TextView) dialogViewCommitment
				.findViewById(R.id.tvStockModelCode);
		tvStockModelCode.setText(stockIsdnBean.getStockModelCode());

		TextView tvStockmodelname = (TextView) dialogViewCommitment
				.findViewById(R.id.tvStockmodelname);
		tvStockmodelname.setText(stockIsdnBean.getStockModelName());

		TextView tvmoneycamket = (TextView) dialogViewCommitment
				.findViewById(R.id.tvmoneycamket);
		tvmoneycamket.setText(StringUtils.formatMoney(stockIsdnBean
				.getPricePledgeAmount()));

		TextView tvmoneycamketungtruoc = (TextView) dialogViewCommitment
				.findViewById(R.id.tvmoneycamketungtruoc);
		tvmoneycamketungtruoc.setText(StringUtils.formatMoney(stockIsdnBean
				.getPricePriorPay()));

		TextView tvcamketthang = (TextView) dialogViewCommitment
				.findViewById(R.id.tvcamketthang);
		tvcamketthang.setText(stockIsdnBean.getPricePledgeTime());

		dialogViewCommitment.findViewById(R.id.btnclose).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogViewCommitment.dismiss();

					}
				});

		dialogViewCommitment.show();

	}

	private void showSelectViewStock(PriceBean priceBean, String serial) {

		final Dialog dialogViewStock = new Dialog(activity);
		dialogViewStock.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogViewStock.setContentView(R.layout.dialog_layout_commitment_stock);

		TextView tvDialogTitle = (TextView) dialogViewStock
				.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(getActivity().getString(
				R.string.ttincamketthietbi));

		TextView tvSerial = (TextView) dialogViewStock
				.findViewById(R.id.tvSerial);
		tvSerial.setText(serial);

		TextView tvMoney = (TextView) dialogViewStock
				.findViewById(R.id.tvMoney);
		tvMoney.setText(StringUtils.formatMoney(priceBean.getPrice()));

		TextView tvmoneycamket = (TextView) dialogViewStock
				.findViewById(R.id.tvmoneycamket);
		tvmoneycamket.setText(StringUtils.formatMoney(priceBean
				.getPledgeAmount()));

		TextView tvmoneycamketungtruoc = (TextView) dialogViewStock
				.findViewById(R.id.tvmoneycamketungtruoc);
		tvmoneycamketungtruoc.setText(StringUtils.formatMoney(priceBean
				.getPriorPay()));

		TextView tvcamketthang = (TextView) dialogViewStock
				.findViewById(R.id.tvcamketthang);
		tvcamketthang.setText(priceBean.getPledgeTime());

		dialogViewStock.findViewById(R.id.btnclose).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogViewStock.dismiss();
					}
				});

		dialogViewStock.show();
	}

	private class AsynctaskviewSubCommitmentPre extends
			AsyncTask<String, Void, StockIsdnBean> {

		private Activity mActivity = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public AsynctaskviewSubCommitmentPre(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);

			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected StockIsdnBean doInBackground(String... params) {
			return viewSubCommitmentPre(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(StockIsdnBean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null
						&& !CommonActivity.isNullOrEmpty(result.getIsdn())
						&& !CommonActivity.isNullOrEmpty(result.getPrice())
						&& !CommonActivity.isNullOrEmpty(result
								.getPricePledgeAmount())) {

					showDialogViewCommitment(result);

					// showSelectViewStock(result, serial);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							activity,
							getActivity().getResources().getString(
									R.string.khongcottincamket), getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}

			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private StockIsdnBean viewSubCommitmentPre(String offerId,
				String regType, String isdn) {
			StockIsdnBean stockIsdnBean = new StockIsdnBean();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_viewSubCommitmentPre");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:viewSubCommitmentPre>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<offerId>").append(offerId).append("</offerId>");
				rawData.append("<regType>").append(regType).append("</regType>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:viewSubCommitmentPre>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_viewSubCommitmentPre");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("stockIsdnBeanPre");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						// <stockIsdnBean>
						// <isdn>999000125</isdn>
						// <isdnStatus>1</isdnStatus>
						// <isdnType>1</isdnType>
						// <price>5200000</price>
						// <priceId>300387</priceId>
						// <pricePledgeAmount>120000</pricePledgeAmount>
						// <pricePledgeTime>8</pricePledgeTime>
						// <pricePriorPay>500000</pricePriorPay>
						// <priceVat>10</priceVat>
						// <stockModelCode>M5200000</stockModelCode>
						// <stockModelId>11834</stockModelId>
						// <stockModelName>Số đẹp Mobile
						// 5,200,000</stockModelName>
						// </stockIsdnBean>

						String isdn1 = parse.getValue(e1, "isdn");
						stockIsdnBean.setIsdn(isdn1);

						stockIsdnBean.setIsdnStatus(parse.getValue(e1,
								"isdnStatus"));
						stockIsdnBean.setIsdnType(parse
								.getValue(e1, "isdnType"));
						stockIsdnBean.setPrice(parse.getValue(e1, "price"));
						stockIsdnBean.setPriceId(parse.getValue(e1, "priceId"));
						stockIsdnBean.setPricePledgeAmount(parse.getValue(e1,
								"pricePledgeAmount"));
						stockIsdnBean.setPricePledgeTime(parse.getValue(e1,
								"pricePledgeTime"));
						stockIsdnBean.setPricePriorPay(parse.getValue(e1,
								"pricePriorPay"));

						stockIsdnBean.setPriceVat(parse
								.getValue(e1, "priceVat"));
						stockIsdnBean.setStockModelCode(parse.getValue(e1,
								"stockModelCode"));
						stockIsdnBean.setStockModelId(parse.getValue(e1,
								"stockModelId"));
						stockIsdnBean.setStockModelName(parse.getValue(e1,
								"stockModelName"));
					}
				}
			} catch (Exception e) {
				Log.d("viewSubCommitmentStock", e.toString()
						+ "description error", e);
			}

			return stockIsdnBean;

		}

	}

	private class AsynctaskviewSubCommitmentPos extends
			AsyncTask<String, Void, StockIsdnBean> {

		private Activity mActivity = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public AsynctaskviewSubCommitmentPos(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);

			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected StockIsdnBean doInBackground(String... params) {
			return viewSubCommitmentPos(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(StockIsdnBean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null
						&& !CommonActivity.isNullOrEmpty(result.getIsdn())
						&& !CommonActivity.isNullOrEmpty(result.getPrice())
						&& !CommonActivity.isNullOrEmpty(result
								.getPricePledgeAmount())) {

					showDialogViewCommitment(result);

					// showSelectViewStock(result, serial);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							activity,
							getActivity().getResources().getString(
									R.string.khongcottincamket), getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}

			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private StockIsdnBean viewSubCommitmentPos(String offerId,
				String regType, String isdn) {
			StockIsdnBean stockIsdnBean = new StockIsdnBean();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_viewSubCommitment");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:viewSubCommitment>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<offerId>").append(offerId).append("</offerId>");
				rawData.append("<regType>").append(regType).append("</regType>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:viewSubCommitment>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_viewSubCommitment");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("stockIsdnBeanPos");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						// <stockIsdnBean>
						// <isdn>999000125</isdn>
						// <isdnStatus>1</isdnStatus>
						// <isdnType>1</isdnType>
						// <price>5200000</price>
						// <priceId>300387</priceId>
						// <pricePledgeAmount>120000</pricePledgeAmount>
						// <pricePledgeTime>8</pricePledgeTime>
						// <pricePriorPay>500000</pricePriorPay>
						// <priceVat>10</priceVat>
						// <stockModelCode>M5200000</stockModelCode>
						// <stockModelId>11834</stockModelId>
						// <stockModelName>Số đẹp Mobile
						// 5,200,000</stockModelName>
						// </stockIsdnBean>

						String isdn1 = parse.getValue(e1, "isdn");
						stockIsdnBean.setIsdn(isdn1);

						stockIsdnBean.setIsdnStatus(parse.getValue(e1,
								"isdnStatus"));
						stockIsdnBean.setIsdnType(parse
								.getValue(e1, "isdnType"));
						stockIsdnBean.setPrice(parse.getValue(e1, "price"));
						stockIsdnBean.setPriceId(parse.getValue(e1, "priceId"));
						stockIsdnBean.setPricePledgeAmount(parse.getValue(e1,
								"pricePledgeAmount"));
						stockIsdnBean.setPricePledgeTime(parse.getValue(e1,
								"pricePledgeTime"));
						stockIsdnBean.setPricePriorPay(parse.getValue(e1,
								"pricePriorPay"));

						stockIsdnBean.setPriceVat(parse
								.getValue(e1, "priceVat"));
						stockIsdnBean.setStockModelCode(parse.getValue(e1,
								"stockModelCode"));
						stockIsdnBean.setStockModelId(parse.getValue(e1,
								"stockModelId"));
						stockIsdnBean.setStockModelName(parse.getValue(e1,
								"stockModelName"));
					}
				}
			} catch (Exception e) {
				Log.d("viewSubCommitmentStock", e.toString()
						+ "description error", e);
			}

			return stockIsdnBean;

		}

	}

	private class AsynctaskviewSubCommitmentStock extends
			AsyncTask<String, Void, PriceBean> {

		private Activity mActivity = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;
		private String serial = "";

		public AsynctaskviewSubCommitmentStock(Activity mActivity, String serial) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.serial = serial;
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected PriceBean doInBackground(String... params) {
			return viewSubCommitmentStock(params[0], params[1], params[2],
					params[3], params[4]);
		}

		@Override
		protected void onPostExecute(PriceBean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null
						&& !CommonActivity.isNullOrEmpty(result.getPrice())
						&& !CommonActivity.isNullOrEmpty(result
								.getPledgeAmount())
						&& !CommonActivity.isNullOrEmpty(result.getPriorPay())) {
					showSelectViewStock(result, serial);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							activity,
							getActivity().getResources().getString(
									R.string.khongcottincamket), getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}

			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private PriceBean viewSubCommitmentStock(String offerId,
				String regType, String isdn, String serial, String stockModelId) {
			PriceBean priceBean = new PriceBean();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_viewSubCommitment");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:viewSubCommitment>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<offerId>").append(offerId).append("</offerId>");
				rawData.append("<regType>").append(regType).append("</regType>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<serial>").append(serial).append("</serial>");
				rawData.append("<stockModelId>").append(stockModelId).append("</stockModelId>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:viewSubCommitment>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, activity,
						"mbccs_viewSubCommitment");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("priceBean");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						// <pledgeAmount>550000</pledgeAmount>
						// <pledgeTime>18</pledgeTime>
						// <price>4950000</price>
						// <priceId>500553</priceId>
						// <priorPay>9000000</priorPay>
						// <stockModelId>21223</stockModelId>
						// <vat>10</vat>

						String pledgeAmount = parse
								.getValue(e1, "pledgeAmount");
						priceBean.setPledgeAmount(pledgeAmount);

						String pledgeTime = parse.getValue(e1, "pledgeTime");
						priceBean.setPledgeTime(pledgeTime);

						String price = parse.getValue(e1, "price");
						priceBean.setPrice(price);

						String priceId = parse.getValue(e1, "priceId");
						priceBean.setPriceId(priceId);

						String priorPay = parse.getValue(e1, "priorPay");
						priceBean.setPriorPay(priorPay);

						String stockModelId1 = parse.getValue(e1,
								"stockModelId");
						priceBean.setStockModelId(stockModelId1);

						String vat = parse.getValue(e1, "vat");
						priceBean.setVat(vat);

					}
				}
			} catch (Exception e) {
				Log.d("viewSubCommitmentStock", e.toString()
						+ "description error", e);
			}

			return priceBean;

		}

	}
}
