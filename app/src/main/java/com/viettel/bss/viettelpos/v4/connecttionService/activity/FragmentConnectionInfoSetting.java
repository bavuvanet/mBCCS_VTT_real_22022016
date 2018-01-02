package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.SearchCodeHthmActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.SearchCodePromotionActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetContractAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActivesObj;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ConTractBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ContractOffersObj;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.GetContractHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetBankBundleAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListCusAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListPaymentDetailChargeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetPrecintAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetPstnAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.ThongTinHHAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.ThongTinHHAdapter.OnChangeSpiner;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Bank;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.BankMain;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Contract;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerTypeByCustGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTTTBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoConnectionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.NotifyBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjAPStockModelBeanBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjThongTinHH;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReasonPledgeDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubReqDeployment;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubRequest;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubStockModelRelReq;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyBaseBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyInforBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyMethodBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyProgramBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TypePaperBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetTypePaperDal;
import com.viettel.bss.viettelpos.v4.connecttionService.handler.GetBankHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.handler.HTHMHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.handler.SubTypeHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.SurveyOnline;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NewApi")
public class FragmentConnectionInfoSetting extends GPSTracker implements
		OnClickListener, OnChangeSpiner, OnTouchListener {
	private Button btnkyhopdong;
    private Spinner spinner_nhomthuebao, spinner_loaithuebao, spinner_hthm,
			spinner_makm, spinner_iptinh, spinner_mucdocanhbao;
	private EditText editmatkhau, editisdnoracc, edtcodinh, edtdidong,
			txtsonha, txtduong;
	private Spinner spinner_nhomkh, spinner_type_giay_to_parent,
			spinner_type_giay_to_offer;

    private TextView txtphuongxa;
    private TextView txtdiachidaydu;
    private TextView edit_sogiaytoDD;
	private EditText edit_ngaycap;
	private EditText edtnoicap;
	private EditText txthoten, edtmaKHCD, edit_tenKHDD, edit_ngaysinhd,
			edit_quoctich;
	private LinearLayout lnIptinh;
    private ArrayList<SubGroupBeans> arrSubGroupBeans = new ArrayList<>();
	private ArrayList<SubTypeBeans> arrSubTypeBeans = new ArrayList<>();
	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans = new ArrayList<>();
	private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<>();
	private ArrayList<String> arrListIp = new ArrayList<>();
	private ArrayList<String> arrListIsdnPstn = new ArrayList<>();
	private ArrayList<String> arrListIsdnNGN = new ArrayList<>();
	private final ArrayList<NotifyBean> arrNotifyBeans = new ArrayList<>();
	private ArrayList<AreaBean> arrPrecint = new ArrayList<>();
	private final ArrayList<HTTTBean> arrHtttBeans = new ArrayList<>();

    private String idSubGroup = "";
	private String telecomServiceId = "";
	private String subType = "", regType = "";

	private String serviceType = "";
	private String account = "";
	private String locationCode = "";
	private String offerId = "";
	private String lineType = "";
	private String productCode = "";
	private String techology = "";
	private String notifyStatus = "";
	private AreaBean areaBean = new AreaBean();

	private ExpandableHeightListView lvproduct;
	private String maKM = "-1";
    private String ipStatic = "";
	private ThongTinHHAdapter adapter = null;
	private SurveyOnline surveyOnline = new SurveyOnline();
	private CustommerByIdNoBean custommer = new CustommerByIdNoBean();
	private final ArrayList<ObjThongTinHH> mArrayListThongTinHH = new ArrayList<>();
	// private SupplyInforBean supplyInforBean = new SupplyInforBean();
	public static FragmentConnectionInfoSetting instance = null;

	private Spinner spinner_htcungcap;
	private Spinner spinner_chuongtrinh;
	private Spinner spinner_sothang;
	private Spinner spinner_httt;
	private EditText edit_sotien;

	private String htcc;
	private String programCode;
	private String programMonth;
	private String httt;
	private final InfoConnectionBeans infoConnectionBeans = new InfoConnectionBeans();
    private ArrayAdapter<String> adapterMonths;
	private ArrayAdapter<String> adapterHTCC;

	// init list map
	private ArrayList<SupplyProgramBean> arrMapProgramBeans = new ArrayList<>();
	private List<SupplyBaseBean> arrMapBaseBeans = new ArrayList<>();

	// init list stock model for sign contract
	private final Map<String, SubStockModelRelReq> mapSubStockModelRelReq = new HashMap<>();

	// luu tong so tien
	// luu chuong trinh
	// private String reclaimProCode = "";
	//
	private String reclaimCommitmentCode = "";

	private String stockModelId = "";
	private String saleServiceId = "";
	private String stockTypeId = "";

	private String gponGroupAccountId = "";
	private String relationType = "";
	private String mainSubId = "";

	private String parentSubId = "";

    private String tenduong = "";
	private String sonha = "";

	private String reasonId;

    private LinearLayout lnsophuluc;
	private EditText editisdnoraccPPPOE, editmatkhauPPPOE;

    // BO SUNG LUONG KHDN
	private TextView txthopdong;
	private Spinner spinner_loaihopdong;
	private Spinner spinner_httthd;
	private Spinner spinner_chukicuoc;
	private Spinner spinner_inchitiet;
	private Spinner spinner_httbc;
	private Spinner spinner_ttbosung;
	private Spinner spinner_plhopdong;
	private EditText edtsohd, edtsoinHD, edtnguoithanhtoan, edit_ngayky,
			edit_ngayhieuluc, edit_ngayhethan, edtdtcdtbc, txtdtdidongtbc,
			edtplhdong, edtcvplhd, edit_ngaykyhd, edtmagiaytoCD,
			edit_ngaycappl, edtmanoicapCD;

	private Spinner spinner_loaikh;
	private Spinner spinner_lvhd, spinner_gioitinh;

	private EditText txtdctbc;
	private LinearLayout lnsoHD, lnsoinHD;

	public static final int TYPE_HTTTHD = 1;
	public static final int TYPE_LOAI_HD = 2;
	private static final int TYPE_CK_CUOC_HD = 3;
	public static final int TYPE_INCT_HD = 4;
	public static final int TYPE_HTTBC_HD = 5;
	public static final int TYPE_TTBS_HD = 6;
	public static final int TYPE_PL_HD = 7;

	private Boolean isRefreshTYPE_HTTTHD = false;
	private Boolean isRefreshTYPE_LOAI_HD = false;
	private Boolean isRefreshTYPE_CK_CUOC_HD = false;
    private Boolean isRefreshTYPE_HTTBC_HD = false;
	private Boolean isRefreshTYPE_TTBS_HD = false;

	private ArrayList<ConTractBean> arrTractBeans = new ArrayList<>();
	private ConTractBean conTractBean = new ConTractBean();

	private ArrayList<Spin> arrHTTTHD = new ArrayList<>();
	private ArrayList<Spin> arrLOAIHD = new ArrayList<>();
	private ArrayList<Spin> arrCKC = new ArrayList<>();
	private ArrayList<Spin> arrINCT = new ArrayList<>();
	private ArrayList<Spin> arrHTTBC = new ArrayList<>();
	private ArrayList<Spin> arrTTBSHD = new ArrayList<>();
	private ArrayList<CustomerGroupBeans> arrCustomerGroupBeans = new ArrayList<>();
	private ArrayList<CustomerTypeByCustGroupBeans> arrCustomerTypeByCustGroupBeans = new ArrayList<>();
	private ArrayList<ContractOffersObj> arrContractOffersObj = new ArrayList<>();
	private ArrayList<ActivesObj> arrActivesObjObj = new ArrayList<>();

	private AreaObj areaProvicial;
	private AreaObj areaDistrist;
	private AreaObj areaPrecint;
	private AreaObj areaGroup;
	private String areaFlow;
	private String areaHomeNumber;
	private StringBuilder address;

	private AreaObj areaProvicialPR;
	private AreaObj areaDistristPR;
	private AreaObj areaPrecintPR;
	private AreaObj areaGroupPR;
	private String areaFlowPR;
	private String areaHomeNumberPR;

    private ArrayList<TypePaperBeans> arrTypePaperBeans = new ArrayList<>();
	private ArrayList<TypePaperBeans> arrTypePaperBeansOffer = new ArrayList<>();
	private final ArrayList<AreaObj> listSex = new ArrayList<>();

	private EditText edtemailtbc;
	private String cusGroupId = "";
	private String busType = "";
	private ActivesObj fieldAcObj = new ActivesObj();
	private AreaObj gioitinh = new AreaObj();
	private TypePaperBeans typePaperBeans = new TypePaperBeans();
	private TypePaperBeans typePaperBeansOffer = new TypePaperBeans();
	private SimpleDateFormat dbUpdateDateTime = new SimpleDateFormat(
			"dd/MM/yyyy");

	private Calendar calsignDate;
	private int daysignDate;
	private int monthsignDate;
	private int yearsignDate;

	private Calendar caleffectDate;
	private int dayeffectDate;
	private int montheffectDate;
	private int yeareffectDate;

	private Calendar calexpireDate;
	private int dayexpireDate;
	private int monthexpireDate;
	private int yearexpireDate;

	private Calendar calesignDateContractOffer;
	private int dayesignDateContractOffer;
	private int monthsignDateContractOffer;
	private int yearsignDateContractOffer;

	private Calendar calbirhDateDD;
	private int daybirhDateDD;
	private int monbirhDateDD;
	private int yearbirhDateDD;

	private Calendar calissueDateOffer;
	private int dayissueDateOffer;
	private int monissueDateOffer;
	private int yearissueDateOffer;

	private Calendar calissueDateDD;
	private int dayissueDateDD;
	private int monissueDateDD;
	private int yearissueDateDD;

    private EditText txtdcgtxm;
	private EditText edtdaidienpl;
	private String dateNowString = "";

	// thong tin ngan hang

	private EditText edit_ngaynhothu;
	private EditText edthdnhothu;
	private EditText edttkoanhd;
	private EditText edttentkoan;
	private TextView txtnganhang;
	private LinearLayout lnhinhthuctthd;

	private BankMain bankMain = new BankMain();

    private Calendar calngaynhothu;
	private int dayngaynhothu;
	private int monngaynhothu;
	private int yearngaynhothu;

    private EditText edtdchdcuoc;

	private TextView txththm, txtchonkm;
	private LinearLayout lnngayhethan;

	private Button btnkiemtra, btnedit;
	private LinearLayout lngiaytoxacminh;

    private boolean checkKiemtra = false;
	private Boolean isRefreshCusType = false;

	private Spinner spinner_cuocdongtruoc;
	private LinearLayout lncuocdongtruoc;
	private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeans = new ArrayList<>();
	private String prepaidCode = "";
    private Spinner spinner_kmtrainghiem;
	private ArrayList<Spin> arrPromotionUnit = new ArrayList<>();

	private LinearLayout lndatcoc;
	private Spinner spinner_deposit;
	private ProgressBar prbdeposit;
	private HTHMBeans hthmBeans;

	private EditText edtprovince, edtdistrict, edtprecinct;
	// arrlist province
	private ArrayList<AreaBean> arrProvince = new ArrayList<>();
	private String provinceMain = "";
	// arrlist district
	private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
	private String districtMain = "";
	// arrlist precinct
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
	private String precinctMain = "";
	
	
	private String checkIp = "";

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!CommonActivity.isNullOrEmptyArray(arrProvince)) {
			arrProvince = new ArrayList<>();
		}
		initProvince();

		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";
		calsignDate = Calendar.getInstance();
		daysignDate = calsignDate.get(Calendar.DAY_OF_MONTH);
		monthsignDate = calsignDate.get(Calendar.MONTH);
		yearsignDate = calsignDate.get(Calendar.YEAR);

		caleffectDate = Calendar.getInstance();
		dayeffectDate = caleffectDate.get(Calendar.DAY_OF_MONTH);
		montheffectDate = caleffectDate.get(Calendar.MONTH);
		yeareffectDate = caleffectDate.get(Calendar.YEAR);

		calexpireDate = Calendar.getInstance();
		dayexpireDate = calexpireDate.get(Calendar.DAY_OF_MONTH);
		monthexpireDate = calexpireDate.get(Calendar.MONTH);
		yearexpireDate = calexpireDate.get(Calendar.YEAR);

		calesignDateContractOffer = Calendar.getInstance();
		dayesignDateContractOffer = calesignDateContractOffer
				.get(Calendar.DAY_OF_MONTH);
		monthsignDateContractOffer = calesignDateContractOffer
				.get(Calendar.MONTH);
		yearsignDateContractOffer = calesignDateContractOffer
				.get(Calendar.YEAR);

		calissueDateOffer = Calendar.getInstance();
		dayissueDateOffer = calissueDateOffer.get(Calendar.DAY_OF_MONTH);
		monissueDateOffer = calissueDateOffer.get(Calendar.MONTH);
		yearissueDateOffer = calissueDateOffer.get(Calendar.YEAR);

		calbirhDateDD = Calendar.getInstance();
		daybirhDateDD = calbirhDateDD.get(Calendar.DAY_OF_MONTH);
		monbirhDateDD = calbirhDateDD.get(Calendar.MONTH);
		yearbirhDateDD = calbirhDateDD.get(Calendar.YEAR);

		calissueDateDD = Calendar.getInstance();
		dayissueDateDD = calissueDateDD.get(Calendar.DAY_OF_MONTH);
		monissueDateDD = calissueDateDD.get(Calendar.MONTH);
		yearissueDateDD = calissueDateDD.get(Calendar.YEAR);

		calngaynhothu = Calendar.getInstance();
		dayngaynhothu = calngaynhothu.get(Calendar.DAY_OF_MONTH);
		monngaynhothu = calngaynhothu.get(Calendar.MONTH);
		yearngaynhothu = calissueDateDD.get(Calendar.YEAR);

		instance = this;

		setContentView(R.layout.connection_info_setting);
		ButterKnife.bind(this);
		hideBtnRefresh();
		MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.create_new_request_title));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		unitView();
	}

	@Override
	protected void onResume() {
		if (infoConnectionBeans.getIdRequest() != null
				&& !infoConnectionBeans.getIdRequest().isEmpty()) {
			btnkyhopdong.setVisibility(View.GONE);
		} else {
			btnkyhopdong.setVisibility(View.VISIBLE);
		}
		super.onResume();

	}

	private boolean validateInputContract() {
		if (!CommonActivity.isNullOrEmpty(conTractBean.getContractId())) {

			return true;

		} else {
			if (CommonActivity.isNullOrEmpty(txtdctbc.getText().toString())) {
				CommonActivity.createAlertDialog(
						FragmentConnectionInfoSetting.this,
						getString(R.string.checkdctbc),
						getString(R.string.app_name)).show();
				txtdctbc.requestFocus();
				txtdctbc.requestFocusFromTouch();
				return false;
			}

			if (txtdctbc.getText().toString()
					.equalsIgnoreCase(getString(R.string.sellect_address))) {
				CommonActivity.createAlertDialog(
						FragmentConnectionInfoSetting.this,
						getString(R.string.checkdctbc),
						getString(R.string.app_name)).show();
				txtdctbc.requestFocus();
				txtdctbc.requestFocusFromTouch();
				return false;
			}

			if (custommer.getCustomerAttribute() != null
					&& !(CommonActivity.isNullOrEmpty(custommer
							.getCustomerAttribute().getIdNo()))) {
				if (!checkKiemtra) {
					CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this,
							getString(R.string.checkttgiayto),
							getString(R.string.app_name)).show();
					return false;
				}
				if (custommerRepre == null) {
					if (CommonActivity.isNullOrEmpty(txtdcgtxm.getText()
							.toString())) {
						txtdcgtxm.requestFocus();
						txtdcgtxm.requestFocusFromTouch();

						CommonActivity.createAlertDialog(
								FragmentConnectionInfoSetting.this,
								getString(R.string.checkaddressttindd),
								getString(R.string.app_name)).show();
						return false;
					}
					if (txtdcgtxm
							.getText()
							.toString()
							.equalsIgnoreCase(
									getString(R.string.sellect_address))) {
						CommonActivity.createAlertDialog(
								FragmentConnectionInfoSetting.this,
								getString(R.string.checkaddressttindd),
								getString(R.string.app_name)).show();
						txtdcgtxm.requestFocus();
						txtdcgtxm.requestFocusFromTouch();
						return false;
					}
				}

			} else {
				return true;
			}
		}

		return true;
	}

	private void showDatePickerSign(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);
				calsignDate.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearsignDate, monthsignDate, daysignDate);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	// private Date effectDate = null;
	private void showDatePickerEffectDate(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);
				caleffectDate.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yeareffectDate, montheffectDate, dayeffectDate);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	// private Date expireDate = null;

	private void showDatePickerExpireDate(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);
				calexpireDate.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT ,callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearexpireDate, monthexpireDate, dayexpireDate);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	// private Date signDateContractOffer = null;
	private void showDatePickerSignContractOffer(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);

				calesignDateContractOffer.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearsignDateContractOffer,
					monthsignDateContractOffer, dayesignDateContractOffer);

		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	// private Date issueDateOffer = null;
	private void showDatePickerIssueDateOffer(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);

				calissueDateOffer.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearissueDateOffer, monissueDateOffer,
					dayissueDateOffer);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	// private Date birhDateDD = null;

	private void showDatePickerbirhDateDD(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);
				calbirhDateDD.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearbirhDateDD, monbirhDateDD, daybirhDateDD);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
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
				calissueDateDD.set(year1, monthOfYear, dayOfMonth);
			}
		};

		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			pic = new FixedHoloDatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearissueDateDD, monissueDateDD, dayissueDateDD);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	private void showDatePickerNgaynhothu(final EditText edtngaycap) {
		OnDateSetListener callback = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year1, int monthOfYear,
					int dayOfMonth) {
				String mNgaycaidat = (dayOfMonth) + "/" + (monthOfYear + 1)
						+ "/" + year1;
				edtngaycap.setText(mNgaycaidat);
				calngaynhothu.set(year1, monthOfYear, dayOfMonth);
			}
		};
		DatePickerDialog pic = null;
		if (!CommonActivity.isNullOrEmpty(edtngaycap.getText().toString())) {
			Date date = DateTimeUtils.convertStringToTime(edtngaycap.getText()
					.toString(), "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			pic = new FixedHoloDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,callback, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} else {
			pic = new FixedHoloDatePickerDialog(FragmentConnectionInfoSetting.this,
					AlertDialog.THEME_HOLO_LIGHT,callback, yearngaynhothu, monngaynhothu, dayngaynhothu);
		}
		pic.setTitle(getString(R.string.chon_ngay));
		pic.show();
	}

	private void unitView() {

		// them thong tin dat coc
		lndatcoc = (LinearLayout) findViewById(R.id.lndatcoc);
		lndatcoc.setVisibility(View.GONE);
		spinner_deposit = (Spinner) findViewById(R.id.spinner_deposit);
		prbdeposit = (ProgressBar) findViewById(R.id.prbdeposit);

		lnngayhethan = (LinearLayout) findViewById(R.id.lnngayhethan);
		if (adapter != null && mArrayListThongTinHH.size() > 0) {
			mArrayListThongTinHH.clear();
		}
		account = FragmentInfoCustomer.account;
		Log.d("account", account);

		custommer = FragmentInfoCustomer.custommer;
        String typePaper = custommer.getIdType();
		// get bunder
		getDataBundle();
		// thong tin dia chi lap dat thue bao
		edtprovince = (EditText) findViewById(R.id.edtprovince);
		edtprovince.setOnClickListener(this);

		if (!CommonActivity.isNullOrEmpty(provinceMain)) {
			initDistrict(provinceMain);

			try {
				GetAreaDal dal = new GetAreaDal(
						FragmentConnectionInfoSetting.this);
				dal.open();
				edtprovince.setText(dal.getNameProvince(provinceMain));
				dal.close();
			} catch (Exception e) {
				Log.d("getNameProvince", e.toString());
			}
		} else {
			if (areaBean != null
					&& !CommonActivity.isNullOrEmpty(areaBean.getProvince())) {

				initDistrict(areaBean.getProvince());

				try {
					GetAreaDal dal = new GetAreaDal(
							FragmentConnectionInfoSetting.this);
					dal.open();
					edtprovince.setText(dal.getNameProvince(areaBean
							.getProvince()));
					provinceMain = areaBean.getProvince();
					dal.close();
				} catch (Exception e) {
					Log.d("getNameProvince", e.toString());
				}
			}
		}

		edtdistrict = (EditText) findViewById(R.id.edtdistrict);

		if (!CommonActivity.isNullOrEmpty(provinceMain)
				&& !CommonActivity.isNullOrEmpty(districtMain)) {
			initPrecinct(provinceMain, districtMain);
			try {
				GetAreaDal dal = new GetAreaDal(
						FragmentConnectionInfoSetting.this);
				dal.open();
				edtdistrict.setText(dal.getNameDistrict(provinceMain,
						districtMain));
				dal.close();
			} catch (Exception e) {
				Log.d("getNameDistrict", e.toString());
			}

		} else {
			if (areaBean != null
					&& !CommonActivity.isNullOrEmpty(areaBean.getProvince())
					&& !CommonActivity.isNullOrEmpty(areaBean.getDistrict())) {
				initPrecinct(areaBean.getProvince(), areaBean.getDistrict());
				try {
					GetAreaDal dal = new GetAreaDal(
							FragmentConnectionInfoSetting.this);
					dal.open();
					edtdistrict.setText(dal.getNameDistrict(
							areaBean.getProvince(), areaBean.getDistrict()));
					provinceMain = areaBean.getProvince();
					districtMain = areaBean.getDistrict();
					dal.close();
				} catch (Exception e) {
					Log.d("getNameDistrict", e.toString());
				}
			}
		}
		edtdistrict.setOnClickListener(this);
		edtprecinct = (EditText) findViewById(R.id.edtprecinct);
		if (!CommonActivity.isNullOrEmpty(provinceMain)
				&& !CommonActivity.isNullOrEmpty(districtMain)
				&& !CommonActivity.isNullOrEmpty(precinctMain)) {
			try {
				GetAreaDal dal = new GetAreaDal(
						FragmentConnectionInfoSetting.this);
				dal.open();
				edtprecinct.setText(dal.getNamePrecint(provinceMain,
						districtMain, precinctMain));
				dal.close();
			} catch (Exception e) {
				Log.d("getNameDistrict", e.toString());
			}
		} else {
			if (areaBean != null
					&& !CommonActivity.isNullOrEmpty(areaBean.getProvince())
					&& !CommonActivity.isNullOrEmpty(areaBean.getDistrict())
					&& !CommonActivity.isNullOrEmpty(areaBean.getPrecinct())) {
				try {
					GetAreaDal dal = new GetAreaDal(
							FragmentConnectionInfoSetting.this);
					dal.open();
					edtprecinct.setText(dal.getNamePrecint(
							areaBean.getProvince(), areaBean.getDistrict(),
							areaBean.getPrecinct()));
					provinceMain = areaBean.getProvince();
					districtMain = areaBean.getDistrict();
					precinctMain = areaBean.getPrecinct();
					dal.close();
				} catch (Exception e) {
					Log.d("getNameDistrict", e.toString());
				}
			}
		}

		edtprecinct.setOnClickListener(this);

		edtprovince.setEnabled(false);
		if (!CommonActivity.isNullOrEmpty(parentSubId)
				|| !CommonActivity.isNullOrEmpty(mainSubId)) {
			edtprecinct.setEnabled(true);
			edtdistrict.setEnabled(true);

		} else {

			edtprecinct.setEnabled(true);
			edtdistrict.setEnabled(true);
		}

		lnIptinh = (LinearLayout) findViewById(R.id.lnIptinh);
		editmatkhau = (EditText) findViewById(R.id.editmatkhau);
        LinearLayout lnmatkhau = (LinearLayout) findViewById(R.id.lnmatkhau);

		// bo sung NGN PPPOE
        LinearLayout lnPPPOE = (LinearLayout) findViewById(R.id.lnPPPOE);
		editisdnoraccPPPOE = (EditText) findViewById(R.id.editisdnoraccPPPOE);
		editmatkhauPPPOE = (EditText) findViewById(R.id.editmatkhauPPPOE);
        Button btnsinhaccPPPOE = (Button) findViewById(R.id.btnsinhaccPPPOE);
		btnsinhaccPPPOE.setOnClickListener(this);
		spinner_nhomkh = (Spinner) findViewById(R.id.spinner_nhomkh);
		spinner_loaikh = (Spinner) findViewById(R.id.spinner_loaikh);
		spinner_lvhd = (Spinner) findViewById(R.id.spinner_lvhd);
		lnsophuluc = (LinearLayout) findViewById(R.id.lnsophuluc);
		edtmaKHCD = (EditText) findViewById(R.id.edtmaKHCD);
		edit_tenKHDD = (EditText) findViewById(R.id.edit_tenKHDD);
		// edit_tenKHDD.setTypeface(Typeface.createFromAsset(
		// getAssets(), "fonts/TIMES.TTF"));
		edit_ngaysinhd = (EditText) findViewById(R.id.edit_ngaysinhd);

		// bo song cuoc dong truoc
		spinner_cuocdongtruoc = (Spinner) findViewById(R.id.spinner_cuocdongtruoc);
		lncuocdongtruoc = (LinearLayout) findViewById(R.id.lncuocdongtruoc);
		lncuocdongtruoc.setVisibility(View.GONE);
		spinner_cuocdongtruoc
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arrPaymentPrePaidPromotionBeans != null
								&& !arrPaymentPrePaidPromotionBeans.isEmpty()) {
							prepaidCode = arrPaymentPrePaidPromotionBeans.get(
									arg2).getPrePaidCode();
							if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
								showSelectCuocDongTruoc(arrPaymentPrePaidPromotionBeans
										.get(arg2));
							}

						} else {
							prepaidCode = "";
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		edit_ngaysinhd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerbirhDateDD(edit_ngaysinhd);
			}
		});
		edit_quoctich = (EditText) findViewById(R.id.edit_quoctich);
		edit_sogiaytoDD = (EditText) findViewById(R.id.edit_sogiaytoDD);
		edit_ngaycap = (EditText) findViewById(R.id.edit_ngaycap);
		// edit_ngaycap.setText(custommer.getIdIssueDate());
		edit_ngaycap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerissueDateDD(edit_ngaycap);
			}
		});
		edtnoicap = (EditText) findViewById(R.id.edtnoicap);

		// edtnoicap.setTypeface(Typeface.createFromAsset(
		// getAssets(), "fonts/TIMES.TTF"));
		spinner_type_giay_to_offer = (Spinner) findViewById(R.id.spinner_type_giay_to_offer);
		spinner_type_giay_to_offer
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						typePaperBeansOffer = arrTypePaperBeansOffer.get(arg2);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		spinner_type_giay_to_parent = (Spinner) findViewById(R.id.spinner_type_giay_to_parent);
		spinner_type_giay_to_parent
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						typePaperBeans = arrTypePaperBeans.get(arg2);
						if (typePaperBeans.getParType().equals("")
								|| "3".equals(typePaperBeans.getParType())) {
							lnngayhethan.setVisibility(View.VISIBLE);
						} else {
							lnngayhethan.setVisibility(View.GONE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		spinner_gioitinh = (Spinner) findViewById(R.id.spinner_gioitinh);
		spinner_gioitinh
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						gioitinh = listSex.get(arg2);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		if (serviceType.equals("P") || serviceType.equals("N")) {
			lnmatkhau.setVisibility(View.GONE);
		} else {
			lnmatkhau.setVisibility(View.VISIBLE);
		}

		// hien thi NGN
		if ("N".equalsIgnoreCase(serviceType)
				&& "4".equalsIgnoreCase(techology)) {
			lnPPPOE.setVisibility(View.VISIBLE);
		} else {
			lnPPPOE.setVisibility(View.GONE);
		}

		if (account != null && !account.isEmpty()) {
			editmatkhau.setText(CommonActivity
					.genPasswordAuto(FragmentInfoCustomer.account));
		}
		btnkyhopdong = (Button) findViewById(R.id.btnkyhopdong);
		btnkyhopdong.setOnClickListener(this);
		edtcodinh = (EditText) findViewById(R.id.txtdtcodinh);
		edtdidong = (EditText) findViewById(R.id.txtdtdidong);
		spinner_nhomthuebao = (Spinner) findViewById(R.id.spinner_nhomthuebao);
		spinner_loaithuebao = (Spinner) findViewById(R.id.spinner_loaithuebao);

		txththm = (TextView) findViewById(R.id.txththm);
		txththm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arrHthmBeans.size() > 0) {
					Intent intent = new Intent(
							FragmentConnectionInfoSetting.this,
							SearchCodeHthmActivity.class);
					intent.putExtra("arrHthmBeans", arrHthmBeans);
					startActivityForResult(intent, 1001);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.noththm),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		});
		txtchonkm = (TextView) findViewById(R.id.txtchonkm);
		txtchonkm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arrPromotionTypeBeans.size() > 0) {
					Intent intent = new Intent(
							FragmentConnectionInfoSetting.this,
							SearchCodePromotionActivity.class);
					intent.putExtra("arrPromotionTypeBeans",
							arrPromotionTypeBeans);
					startActivityForResult(intent, 1002);
				}

			}
		});

		spinner_iptinh = (Spinner) findViewById(R.id.spinner_iptinh);
		spinner_mucdocanhbao = (Spinner) findViewById(R.id.spinner_mucdocanhbao);
        TextView txttinhTP = (TextView) findViewById(R.id.txttinhTP);
        TextView txtquanhuyen = (TextView) findViewById(R.id.txtquanhuyen);
		txtphuongxa = (TextView) findViewById(R.id.txtphuongxa);
		txtdiachidaydu = (TextView) findViewById(R.id.txtfulladdress);
		txtsonha = (EditText) findViewById(R.id.txtsonha);
		txtduong = (EditText) findViewById(R.id.txtduong);
		txtsonha.setText(custommer.getHome());
		txtduong.setText(custommer.getStreet());
		// txtduong.setTypeface(Typeface.createFromAsset(
		// getAssets(), "fonts/TIMES.TTF"));

		editisdnoracc = (EditText) findViewById(R.id.editisdnoracc);
        Button btnsinhacc = (Button) findViewById(R.id.btnsinhacc);
		btnsinhacc.setOnClickListener(this);
		lvproduct = (ExpandableHeightListView) findViewById(R.id.lvproduct);
		lvproduct.setExpanded(true);
		txthoten = (EditText) findViewById(R.id.txthoten);

		txthoten.setText(account);
		// userUsing = txthoten.getText().toString();
		// set tp
		txttinhTP.setText(areaBean.getProvince());
		txtquanhuyen.setText(areaBean.getDistrict());
		txtphuongxa.setText(areaBean.getPrecinct());
		// TODO BO SUNG KHUYEN MAI TRAI NGHIEM
        LinearLayout lnkmtrainghiem = (LinearLayout) findViewById(R.id.lnkmtrainghiem);
		spinner_kmtrainghiem = (Spinner) findViewById(R.id.spinner_kmtrainghiem);
		if (serviceType.equals("U")) {
			lnkmtrainghiem.setVisibility(View.VISIBLE);

			AsynGetPromotionUnit asynGetPromotionUnit = new AsynGetPromotionUnit(
					FragmentConnectionInfoSetting.this);
			asynGetPromotionUnit.execute(provinceMain, productCode);

		} else {
			lnkmtrainghiem.setVisibility(View.GONE);
		}
		// kiem tra khach hang cu
		btnkiemtra = (Button) findViewById(R.id.btnkiemtra);
		lngiaytoxacminh = (LinearLayout) findViewById(R.id.lngiaytoxacminh);
		lngiaytoxacminh.setVisibility(View.GONE);
        LinearLayout lnttgiaytoxacminh = (LinearLayout) findViewById(R.id.lnttgiaytoxacminh);
        LinearLayout lnViewddhd = (LinearLayout) findViewById(R.id.lnViewddhd);
        LinearLayout lnviewgtxm = (LinearLayout) findViewById(R.id.lnviewgtxm);
		if (custommer.getCustomerAttribute() != null
				&& !CommonActivity.isNullOrEmpty(custommer
						.getCustomerAttribute().getIdNo())) {
			lnttgiaytoxacminh.setVisibility(View.VISIBLE);
			lnViewddhd.setVisibility(View.VISIBLE);
			lnviewgtxm.setVisibility(View.VISIBLE);
		} else {
			lnttgiaytoxacminh.setVisibility(View.GONE);
			lnViewddhd.setVisibility(View.GONE);
			lnviewgtxm.setVisibility(View.GONE);
		}
		// if (custommer.isBusinessCustomer()) {
		// findViewById(R.id.ln_chuc_vu).setVisibility(View.VISIBLE);
		// } else {
		// findViewById(R.id.ln_chuc_vu).setVisibility(View.GONE);
		// }
		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText()
						.toString().trim())) {
					checkKiemtra = true;
					GetListCustomerAsyn getListCustomerAsyn = new GetListCustomerAsyn(
							FragmentConnectionInfoSetting.this);
					getListCustomerAsyn.execute(edit_sogiaytoDD.getText()
							.toString().trim());

				} else {
					CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this,
							getString(R.string.chua_nhap_giay_to),
							getString(R.string.app_name)).show();
				}

			}
		});

		btnedit = (Button) findViewById(R.id.btnedit);
		btnedit.setVisibility(View.GONE);
		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				checkKiemtra = true;

				btnkiemtra.setVisibility(View.VISIBLE);
				btnedit.setVisibility(View.GONE);

				edit_tenKHDD.setText("");
				edit_tenKHDD.setEnabled(true);

				custommerRepre = null;
				lngiaytoxacminh.setVisibility(View.VISIBLE);

				spinner_type_giay_to_parent.setEnabled(true);
				edit_ngaycap.setText(custommer.getIdIssueDate());
				edit_ngaycap.setEnabled(true);

				edtnoicap.setText(custommer.getIdIssuePlace());
				edtnoicap.setEnabled(true);

				edit_ngayhethan.setText(dateNowString);
				edit_ngayhethan.setEnabled(true);

				txtdcgtxm.setText(getString(R.string.sellect_address));
				txtdcgtxm.setEnabled(true);
				spinner_nhomkh.setEnabled(true);
				spinner_loaikh.setEnabled(true);
				spinner_lvhd.setEnabled(true);
				edtmaKHCD.setText("");
				edtmaKHCD.setEnabled(true);
				edit_ngaysinhd.setText(dateNowString);
				edit_ngaysinhd.setEnabled(true);

				spinner_gioitinh.setEnabled(true);
				edit_quoctich.setText("");
				edit_quoctich.setEnabled(true);
				edit_sogiaytoDD.setText("");
				edit_sogiaytoDD.setEnabled(true);

			}
		});

		txtphuongxa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (areaBean.getProvince() != null
						&& !areaBean.getProvince().isEmpty()
						&& areaBean.getDistrict() != null
						&& !areaBean.getDistrict().isEmpty()) {
					// 1
					try {

						GetAreaDal getAreaDal = new GetAreaDal(
								FragmentConnectionInfoSetting.this);
						getAreaDal.open();
						arrPrecint = getAreaDal.getLstPrecinct(
								areaBean.getProvince(), areaBean.getDistrict());
						getAreaDal.close();
						if (arrPrecint != null && arrPrecint.size() > 0) {
							showPrecint(arrPrecint);
						}

					} catch (Exception e) {
						Log.d("getprecint", e.toString());
					}

				}

			}
		});

		txtdiachidaydu.setText(areaBean.getFullAddress());
		// initValue();
		lvproduct.setVisibility(View.GONE);
		if (arrNotifyBeans != null && arrNotifyBeans.size() > 0) {
			arrNotifyBeans.clear();
		}
		// muc do canh bao
		initSpinerStatus();

		spinner_mucdocanhbao
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						notifyStatus = arrNotifyBeans.get(arg2).getStatus();
						Log.d("notifyStatus", notifyStatus);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		if (CommonActivity
                .isNetworkConnected(FragmentConnectionInfoSetting.this)) {

			if (serviceType.equals("F")) {
				lnIptinh.setVisibility(View.VISIBLE);
				checkIpAsyn checkAsyn = new checkIpAsyn(
						FragmentConnectionInfoSetting.this);
				checkAsyn.execute(offerId);
			} else {
				lnIptinh.setVisibility(View.GONE);
			}
			//
			// }

			// Thinhhq1 bo sung phan lay danh sach hop dong

			GetConTractAsyn getConTractAsyn = new GetConTractAsyn(
					FragmentConnectionInfoSetting.this);
			getConTractAsyn.execute(custommer.getCustId(), custommer.getIdNo(),
					custommer.getBusPermitNo());

			// private int TYPE_HTTHHD = 1;
			// private int TYPE_LOAI_HD = 2;
			// private int TYPE_CK_CUOC_HD = 3;
			// private int TYPE_INCT_HD = 4;
			// private int TYPE_HTTBC_HD = 5;
			// private int TYPE_TTBS_HD = 6;

		} else {
			CommonActivity.createAlertDialog(
					FragmentConnectionInfoSetting.this,
					this.getResources().getString(R.string.errorNetwork),
					this.getResources().getString(R.string.app_name)).show();
		}

		// spinner_makm.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// if (arg2 > 0) {
		// maKM = arrPromotionTypeBeans.get(arg2).getPromProgramCode();
		// } else {
		// maKM = "";
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		spinner_nhomthuebao
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						if (arg2 > 0) {
							idSubGroup = arrSubGroupBeans.get(arg2)
									.getIdSubGroup();
							Log.d("idSubGroup", idSubGroup);
							if (idSubGroup != null && !idSubGroup.isEmpty()
									&& telecomServiceId != null
									&& !telecomServiceId.isEmpty()) {
								if (CommonActivity
										.isNetworkConnected(FragmentConnectionInfoSetting.this)) {
									GetListSubTypeAsyn getListSubTypeAsyn = new GetListSubTypeAsyn(
											FragmentConnectionInfoSetting.this);
									getListSubTypeAsyn.execute(
											telecomServiceId, idSubGroup);
								} else {
									CommonActivity.createAlertDialog(
											FragmentConnectionInfoSetting.this,
											getResources().getString(
													R.string.errorNetwork),
											getResources().getString(
													R.string.app_name)).show();
								}
							} else {
								Toast.makeText(
										FragmentConnectionInfoSetting.this,
										getResources().getString(
												R.string.checknotdata),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							idSubGroup = "";
							findViewById(R.id.prbSubType).setVisibility(
									View.GONE);
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
							subType = arrSubTypeBeans.get(arg2).getSubType();
							Log.d("subType", subType);
							if (CommonActivity
                                    .isNetworkConnected(FragmentConnectionInfoSetting.this)) {
								GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(
										FragmentConnectionInfoSetting.this);
								getHTHMAsyn.execute(offerId, serviceType,
										provinceMain, districtMain,
										precinctMain,
										custommer.getCusGroupId(),
										custommer.getCusType(), idSubGroup,
										subType);
							} else {
								CommonActivity.createAlertDialog(
										FragmentConnectionInfoSetting.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
							}
						} else {
							subType = "";
							if (mArrayListThongTinHH != null
									&& mArrayListThongTinHH.size() > 0
									&& adapter != null) {
								mArrayListThongTinHH.clear();
								adapter = new ThongTinHHAdapter(
										mArrayListThongTinHH,
										FragmentConnectionInfoSetting.this,
										FragmentConnectionInfoSetting.this);
								lvproduct.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}
							if (mapSubStockModelRelReq != null
									&& mapSubStockModelRelReq.size() > 0) {
								mapSubStockModelRelReq.clear();
							}
							// ipStatic = "";
							// if(arrListIp != null && arrListIp.size() > 0){
							// arrListIp.clear();
							// initLstIp();
							// }
							maKM = "-1";
							// txtchonkm.setText(getString(R.string.selectMKM));
							// TODO modify refresh km
							if (arrPromotionTypeBeans != null
									&& arrPromotionTypeBeans.size() > 0) {
								arrPromotionTypeBeans.clear();
								maKM = "-1";
								txtchonkm
										.setText(getString(R.string.chonctkm1));
								// initInitListPromotionNotData();
							}
							saleServiceId = "";
							stockModelId = "";
							stockTypeId = "";
							if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
								arrHthmBeans = new ArrayList<>();
								initHTHM();
								txththm.setText(getString(R.string.chonhthm));
							}

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		spinner_iptinh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arrListIp != null && arrListIp.size() >= 0) {
					ipStatic = arrListIp.get(arg2);
				}
				// else {
				// ipStatic = "";
				// }

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		txthopdong = (TextView) findViewById(R.id.txthopdong);
		spinner_loaihopdong = (Spinner) findViewById(R.id.spinner_loaihopdong);
		spinner_httthd = (Spinner) findViewById(R.id.spinner_httthd);
		spinner_chukicuoc = (Spinner) findViewById(R.id.spinner_chukicuoc);
		spinner_inchitiet = (Spinner) findViewById(R.id.spinner_inchitiet);
		spinner_httbc = (Spinner) findViewById(R.id.spinner_httbc);
		spinner_ttbosung = (Spinner) findViewById(R.id.spinner_ttbosung);
		spinner_plhopdong = (Spinner) findViewById(R.id.spinner_plhopdong);

		edtsohd = (EditText) findViewById(R.id.edtsohd);
		edtsoinHD = (EditText) findViewById(R.id.edtsoinHD);
		edtnguoithanhtoan = (EditText) findViewById(R.id.edtnguoithanhtoan);
		edtnguoithanhtoan.setText(custommer.getNameCustomer());
		edit_ngayky = (EditText) findViewById(R.id.edit_ngayky);

		// bo sung ngan hang doi voi hinh thuc thong bao uy nhiem thu
		edit_ngaynhothu = (EditText) findViewById(R.id.edit_ngaynhothu);
		edit_ngaynhothu.setText(dateNowString);
		edit_ngaynhothu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerNgaynhothu(edit_ngaynhothu);

			}
		});
		edthdnhothu = (EditText) findViewById(R.id.edthdnhothu);
		edttkoanhd = (EditText) findViewById(R.id.edittkoanhd);
		edttentkoan = (EditText) findViewById(R.id.edttentkoan);
		txtnganhang = (TextView) findViewById(R.id.txtnganhang);
		lnhinhthuctthd = (LinearLayout) findViewById(R.id.lnhinhthuctthd);

        LinearLayout lninchitiet = (LinearLayout) findViewById(R.id.lninchitiet);
		if ("P".equals(serviceType) || "N".equals(serviceType)) {
			lninchitiet.setVisibility(View.VISIBLE);
		} else {
			lninchitiet.setVisibility(View.GONE);
		}

		txtnganhang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDialogListObjectCustommer();

			}
		});
        LinearLayout lnParentContract = (LinearLayout) findViewById(R.id.lnParentContract);
		txtdcgtxm = (EditText) findViewById(R.id.txtdcgtxm);
		txtdcgtxm.setOnClickListener(this);
		if (!CommonActivity.isNullOrEmpty(custommer.getBusPermitNo())) {
			lnParentContract.setVisibility(View.VISIBLE);
		} else {
			lnParentContract.setVisibility(View.GONE);
		}
		edtdaidienpl = (EditText) findViewById(R.id.edtdaidienpl);

		edtdaidienpl.setText(custommer.getNameCustomer());
		edit_ngayky.setText(dateNowString);
		edit_ngayky.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerSign(edit_ngayky);
			}
		});
		edit_ngayhieuluc = (EditText) findViewById(R.id.edit_ngayhieuluc);
		edit_ngayhieuluc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerEffectDate(edit_ngayhieuluc);
			}
		});
		edit_ngayhethan = (EditText) findViewById(R.id.edit_ngayhethan);
		edit_ngayhethan.setText(dateNowString);
		edit_ngayhethan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerExpireDate(edit_ngayhethan);

			}
		});
		edtdtcdtbc = (EditText) findViewById(R.id.edtdtcdtbc);
		txtdtdidongtbc = (EditText) findViewById(R.id.txtdtdidongtbc);
		edtplhdong = (EditText) findViewById(R.id.edtplhdong);
		edtcvplhd = (EditText) findViewById(R.id.edtcvplhd);
		edit_ngaykyhd = (EditText) findViewById(R.id.edit_ngaykyhd);
		edit_ngaykyhd.setText(dateNowString);
		edit_ngaykyhd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerSignContractOffer(edit_ngaykyhd);
			}
		});

		edtmagiaytoCD = (EditText) findViewById(R.id.edtmagiaytoCD);
		edit_ngaycappl = (EditText) findViewById(R.id.edit_ngaycappl);
		edit_ngaycappl.setText(dateNowString);
		edit_ngaycappl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePickerIssueDateOffer(edit_ngaycappl);
			}
		});
		edtmanoicapCD = (EditText) findViewById(R.id.edtmanoicapCD);
		edtemailtbc = (EditText) findViewById(R.id.edtemailtbc);
		txtdctbc = (EditText) findViewById(R.id.txtdctbc);
		edtdchdcuoc = (EditText) findViewById(R.id.edtdchdcuoc);
		lnsoHD = (LinearLayout) findViewById(R.id.lnsoHD);
		lnsoinHD = (LinearLayout) findViewById(R.id.lnsoinHD);

		txtdctbc.setOnClickListener(this);

		// chon hop dong
		txthopdong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arrTractBeans != null && !arrTractBeans.isEmpty()) {
					showDialogSelectContract(arrTractBeans);
				}
			}
		});

		// chon hinh thuc thanh toan hop dong
		spinner_httthd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				if (item != null) {
					if ("02".equals(item.getId()) || "03".equals(item.getId())) {
						lnhinhthuctthd.setVisibility(View.VISIBLE);
					} else {
						lnhinhthuctthd.setVisibility(View.GONE);
					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// in chi tiet hop dong
		spinner_inchitiet
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Spin item = (Spin) arg0.getItemAtPosition(arg2);
						if (item != null) {
							if (item.getId().equals("1")
									|| item.getId().equals("2")
									|| item.getId().equals("5")) {
								if (arrTTBSHD != null && arrTTBSHD.size() > 0) {
									for (Spin item1 : arrTTBSHD) {
										if (item1.getId().equals("2")) {
											spinner_ttbosung
													.setSelection(arrTTBSHD
															.indexOf(item1));
											spinner_ttbosung.setEnabled(false);
											break;
										}
									}

								} else {
									spinner_ttbosung.setEnabled(true);
								}
							} else {
								spinner_ttbosung.setEnabled(true);
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		// thong tin pl hop dong
		spinner_plhopdong
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 == 0) {
							edtplhdong.setText("");
							lnsophuluc.setVisibility(View.GONE);
							edtcvplhd.setText("");
							edit_ngaykyhd.setText(dateNowString);
							if (!CommonActivity.isNullOrEmpty(custommer
									.getIdNo())) {
								edtmagiaytoCD.setText(custommer.getIdNo());
							} else {
								edtmagiaytoCD.setText(custommer
										.getBusPermitNo());
							}

							edit_ngaycappl.setText(custommer.getIdIssueDate());
							edtmanoicapCD.setText(custommer.getIdIssuePlace());
							edtdaidienpl.setText(custommer.getNameCustomer());
							edtdaidienpl.setEnabled(true);
							edtplhdong.setEnabled(true);
							edtcvplhd.setEnabled(true);
							edit_ngaykyhd.setEnabled(true);
							edtmagiaytoCD.setEnabled(true);
							edit_ngaycappl.setEnabled(true);
							edtmanoicapCD.setEnabled(true);

							if (!CommonActivity.isNullOrEmpty(custommer
									.getIdType())) {
								if (arrTypePaperBeansOffer != null
										&& !arrTypePaperBeansOffer.isEmpty()) {
									for (TypePaperBeans typePaperBeans : arrTypePaperBeansOffer) {
										if (custommer.getIdType().equals(
												typePaperBeans.getParType())) {
											spinner_type_giay_to_offer
													.setSelection(arrTypePaperBeansOffer
															.indexOf(typePaperBeans));
										}
									}
								}
							}
							spinner_type_giay_to_offer.setEnabled(true);

						} else {
							ContractOffersObj contractOffersObj = arrContractOffersObj
									.get(arg2);
							lnsophuluc.setVisibility(View.VISIBLE);
							edtdaidienpl.setText(contractOffersObj
									.getRepresent());
							edtdaidienpl.setEnabled(false);
							edtplhdong.setText(contractOffersObj
									.getContractOfferId());
							edtplhdong.setEnabled(false);
							edtcvplhd.setText(contractOffersObj
									.getRepresentPosition());
							edtcvplhd.setEnabled(false);
							edit_ngaykyhd.setText(StringUtils
									.convertDate(contractOffersObj
											.getStrSignDatetime()));
							edit_ngaykyhd.setEnabled(false);

							if (!CommonActivity.isNullOrEmpty(contractOffersObj
									.getIdType())) {
								if (arrTypePaperBeansOffer != null
										&& !arrTypePaperBeansOffer.isEmpty()) {
									for (TypePaperBeans typePaperBeans : arrTypePaperBeansOffer) {
										if (contractOffersObj.getIdType()
												.equals(typePaperBeans
														.getParType())) {
											spinner_type_giay_to_offer
													.setSelection(arrTypePaperBeansOffer
															.indexOf(typePaperBeans));
											spinner_type_giay_to_offer
													.setEnabled(false);
											break;
										}
									}
								}
							}

							edtmagiaytoCD.setText(contractOffersObj.getIdNo());
							edtmagiaytoCD.setEnabled(false);
							edit_ngaycappl.setText(StringUtils
									.convertDate(contractOffersObj
											.getStrIssueDatetime()));
							edit_ngaycappl.setEnabled(false);
							edtmanoicapCD.setText(contractOffersObj
									.getIssuePlace());
							edtmanoicapCD.setEnabled(false);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		// chon nhom KH
		spinner_nhomkh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				cusGroupId = arrCustomerGroupBeans.get(arg2).getIdCusGroup();
				GetCustomerTypeByCustGroupIdAsyn getCustomerTypeByCustGroupIdAsyn = new GetCustomerTypeByCustGroupIdAsyn(
						FragmentConnectionInfoSetting.this);
				getCustomerTypeByCustGroupIdAsyn.execute(cusGroupId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinner_loaikh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				busType = arrCustomerTypeByCustGroupBeans.get(arg2).getCode();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinner_lvhd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				fieldAcObj = arrActivesObjObj.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// spinner_plhopdong.setAdapter(adapter);
		initTypePaper();

		initTypePaperOffer();

		AreaObj areaObjectM = new AreaObj();
		areaObjectM.setName(getString(R.string.gt_nam));
		areaObjectM.setAreaCode("M");
		listSex.add(areaObjectM);

		AreaObj areaObjectF = new AreaObj();
		areaObjectF.setName(getString(R.string.gt_nu));
		areaObjectF.setAreaCode("F");
		listSex.add(areaObjectF);

		AdapterProvinceSpinner adapterTp = new AdapterProvinceSpinner(listSex,
				FragmentConnectionInfoSetting.this);
		spinner_gioitinh.setAdapter(adapterTp);

		findViewById(R.id.btnloaihopdong).setOnClickListener(this);
		findViewById(R.id.btnhttthd).setOnClickListener(this);
		findViewById(R.id.btnchukicuoc).setOnClickListener(this);
		findViewById(R.id.btninchitiet).setOnClickListener(this);
		findViewById(R.id.btnhttbc).setOnClickListener(this);
		findViewById(R.id.btnttbosung).setOnClickListener(this);
		findViewById(R.id.btnnhomkh).setOnClickListener(this);
		findViewById(R.id.btnloaikh).setOnClickListener(this);
		findViewById(R.id.btnlvhd).setOnClickListener(this);
		findViewById(R.id.btnSubGroup).setOnClickListener(this);
		findViewById(R.id.btnSubType).setOnClickListener(this);

		// HuyPQ15: set dia chi thong bao cuoc la dia chi giay to khach hang

		setDCTBC(null);

	}

	private void reloadHDNew() {
		showBtnRefresh();
		txtnganhang.setText(getString(R.string.nganhang));

		edit_ngayhethan.setEnabled(true);
		edit_ngayhethan.setText(dateNowString);
		edtdchdcuoc.setText("");
		edtdchdcuoc.setEnabled(true);
		edit_ngaynhothu.setText(dateNowString);
		edthdnhothu.setText("");
		edttkoanhd.setText("");
		edttentkoan.setText("");
		edit_ngaynhothu.setEnabled(true);
		edthdnhothu.setEnabled(true);
		edttkoanhd.setEnabled(true);
		edttentkoan.setEnabled(true);
		txtnganhang.setEnabled(true);

		edtsohd.setText("");
		edtsoinHD.setText("");
		edtnguoithanhtoan.setText(custommer.getNameCustomer());
		edit_ngayky.setText(dateNowString);
		edit_ngayhieuluc.setText(dateNowString);
		edit_ngayhethan.setText("");

		edtdtcdtbc.setText("");
		txtdtdidongtbc.setText("");

		lnsoHD.setVisibility(View.GONE);
		lnsoinHD.setVisibility(View.GONE);
		edtsohd.setEnabled(true);
		edtsoinHD.setEnabled(true);
		edtnguoithanhtoan.setEnabled(true);
		edit_ngayky.setEnabled(true);
		edit_ngayhieuluc.setEnabled(true);
		edit_ngayhethan.setEnabled(true);
		spinner_loaihopdong.setEnabled(true);
		spinner_httthd.setEnabled(true);
		spinner_chukicuoc.setEnabled(true);
		spinner_inchitiet.setEnabled(true);

		// txtdctbc.setText(getString(R.string.chondctbcCD));
		txtdctbc.setEnabled(true);

		txtdcgtxm.setText(getString(R.string.sellect_address));
		txtdcgtxm.setEnabled(true);

		edtemailtbc.setText("");
		edtemailtbc.setEnabled(true);

		edtmaKHCD.setEnabled(true);
		edit_tenKHDD.setEnabled(true);
		edit_ngaysinhd.setEnabled(true);

		edit_quoctich.setEnabled(true);
		edit_sogiaytoDD.setEnabled(true);
		edit_ngaycap.setEnabled(true);
		edtnoicap.setEnabled(true);

		spinner_gioitinh.setEnabled(true);
		spinner_type_giay_to_parent.setEnabled(true);
		edit_ngaycap.setEnabled(true);
		edtnoicap.setEnabled(true);

		txtdctbc.setText(getString(R.string.sellect_address));
		edtmaKHCD.setText("");
		edit_tenKHDD.setText("");
		edit_ngaysinhd.setText("");

		edit_quoctich.setText("");
		edit_sogiaytoDD.setText("");
		edit_ngaycap.setText(custommer.getIdIssueDate());
		edtnoicap.setText(custommer.getIdIssuePlace());
		spinner_gioitinh.setSelection(0);
		if (arrTypePaperBeans.size() > 0) {
			spinner_type_giay_to_parent.setSelection(0);
		}

		// edit_ngaycap.setText("");
		// edtnoicap.setText("");

		// phu luc hop dong
		edtplhdong.setText("");
		edtplhdong.setEnabled(true);
		lnsophuluc.setVisibility(View.GONE);
		edtcvplhd.setText("");
		edtcvplhd.setEnabled(true);
		edit_ngaykyhd.setText(dateNowString);
		edit_ngaykyhd.setEnabled(true);
		edtmagiaytoCD.setText("");
		edtmagiaytoCD.setEnabled(true);
		edit_ngaycappl.setText(dateNowString);
		edit_ngaycappl.setEnabled(true);
		edtmanoicapCD.setText("");
		edtmanoicapCD.setEnabled(true);

		txtdctbc.setEnabled(true);
		edtmaKHCD.setEnabled(true);
		edit_tenKHDD.setEnabled(true);
		edit_ngaysinhd.setEnabled(true);

		edit_quoctich.setEnabled(true);
		edit_sogiaytoDD.setEnabled(true);
		edit_ngaycap.setEnabled(true);
		edtnoicap.setEnabled(true);

		spinner_gioitinh.setEnabled(true);
		spinner_type_giay_to_parent.setEnabled(true);
		edit_ngaycap.setEnabled(true);
		edtnoicap.setEnabled(true);
		spinner_lvhd.setEnabled(true);

		spinner_httbc.setEnabled(true);
		edtdtcdtbc.setEnabled(true);
		txtdtdidongtbc.setEnabled(true);
		spinner_ttbosung.setEnabled(true);
		spinner_nhomkh.setEnabled(true);
		spinner_loaikh.setEnabled(true);
		custommerRepre = null;
		lngiaytoxacminh.setVisibility(View.GONE);
		btnkiemtra.setVisibility(View.VISIBLE);
		btnedit.setVisibility(View.GONE);
		spinner_lvhd.setVisibility(View.VISIBLE);
		findViewById(R.id.btnlvhd).setVisibility(View.VISIBLE);
		findViewById(R.id.prblvhd).setVisibility(View.VISIBLE);
		setDCTBC(null);
	}

	private void reloadHDOld(ConTractBean conTractBeanPr) {
		hideBtnRefresh();
		Log.d(Constant.TAG,
				"FragmentConnectionInfoSetting reloadHDOld ConTractBean: "
						+ conTractBeanPr.toString());

		try {

			custommerRepre = null;
			lngiaytoxacminh.setVisibility(View.VISIBLE);
			btnkiemtra.setVisibility(View.GONE);
			btnedit.setVisibility(View.GONE);

			edtdchdcuoc.setText(conTractBeanPr.getBillAddress());
			edtdchdcuoc.setEnabled(false);
			lnsoHD.setVisibility(View.VISIBLE);
			lnsoinHD.setVisibility(View.VISIBLE);
			edtsohd.setText(conTractBeanPr.getContractNo());
			edtsoinHD.setText(conTractBeanPr.getContractNo());
			edtnguoithanhtoan.setText(conTractBeanPr.getPayer());
			edit_ngayky.setText(conTractBeanPr.getSignDate());
			edit_ngayhieuluc.setText(conTractBeanPr.getEffectDate());
			edit_ngayhethan.setText("");

			edtsohd.setEnabled(false);
			edtsoinHD.setEnabled(false);
			edtnguoithanhtoan.setEnabled(false);

			edit_ngayky.setEnabled(false);
			edit_ngayhieuluc.setEnabled(false);
			edit_ngayhethan.setEnabled(false);

			if (conTractBeanPr.getContractBank() != null) {
				edit_ngaynhothu.setText(conTractBeanPr.getContractBank()
						.getStrBankContractDate());
				edthdnhothu.setText(conTractBeanPr.getContractBank()
						.getBankContractNo());
				edttkoanhd.setText(conTractBeanPr.getContractBank()
						.getAccount());
				edttentkoan.setText(conTractBeanPr.getContractBank()
						.getAccountName());
				txtnganhang.setText(conTractBeanPr.getContractBank().getName());
			}
			edit_ngaynhothu.setEnabled(false);
			edthdnhothu.setEnabled(false);
			edttkoanhd.setEnabled(false);
			edttentkoan.setEnabled(false);
			txtnganhang.setEnabled(false);

			spinner_loaihopdong.setEnabled(false);
			if (!CommonActivity.isNullOrEmpty(conTractBeanPr.getContractType())) {
				if (arrLOAIHD != null && !arrLOAIHD.isEmpty()) {

					for (Spin loaiHD : arrLOAIHD) {
						if (conTractBeanPr.getContractType().equalsIgnoreCase(
								loaiHD.getId())) {
							spinner_loaihopdong.setSelection(arrLOAIHD
									.indexOf(loaiHD));
							break;
						}
					}
				}
			}

			spinner_httthd.setEnabled(false);
			//
			if (!CommonActivity
					.isNullOrEmpty(conTractBeanPr.getPayMethodCode())) {
				if (arrHTTTHD != null && !arrHTTTHD.isEmpty()) {
					for (Spin payMethod : arrHTTTHD) {
						if (conTractBeanPr.getPayMethodCode().equalsIgnoreCase(
								payMethod.getId())) {
							spinner_httthd.setSelection(arrHTTTHD
									.indexOf(payMethod));
							break;
						}
					}
				}
			}
			spinner_chukicuoc.setEnabled(false);
			if (!CommonActivity
					.isNullOrEmpty(conTractBeanPr.getBillCycleFrom())) {
				if (arrCKC != null && !arrCKC.isEmpty()) {
					for (Spin ckc : arrCKC) {
						if (conTractBeanPr.getBillCycleFrom().equalsIgnoreCase(
								ckc.getId())) {
							spinner_chukicuoc.setSelection(arrCKC.indexOf(ckc));
							break;
						}
					}
				}
			}
			spinner_inchitiet.setEnabled(false);

			if (!CommonActivity.isNullOrEmpty(conTractBeanPr
					.getPrintMethodCode())) {
				if (arrINCT != null && !arrINCT.isEmpty()) {
					for (Spin inCT : arrINCT) {
						if (conTractBeanPr.getPrintMethodCode()
								.equalsIgnoreCase(inCT.getId())) {
							spinner_inchitiet.setSelection(arrINCT
									.indexOf(inCT));
							break;
						}
					}
				}
			}

			// dia chi thue bao
			for (int i = 0; i < arrHTTBC.size(); i++) {
				Spin spin = arrHTTBC.get(i);
				if (spin.getId().equalsIgnoreCase(
						conTractBeanPr.getNoticeCharge())) {
					spinner_httbc.setSelection(i);
					break;
				}
			}
			spinner_httbc.setEnabled(false);

			for (int i = 0; i < arrTTBSHD.size(); i++) {
				Spin spin = arrTTBSHD.get(i);
				if (spin.getId().equalsIgnoreCase(
						conTractBeanPr.getContractPrint())) {
					spinner_ttbosung.setSelection(i);
					break;
				}
			}
			spinner_ttbosung.setEnabled(false);

			edtdtcdtbc.setText(conTractBeanPr.getTelFax());
			edtdtcdtbc.setEnabled(false);
			txtdtdidongtbc.setText(conTractBeanPr.getTelMobile());
			txtdtdidongtbc.setEnabled(false);
			address = new StringBuilder();

			if (conTractBeanPr.getHome() != null) {
				address.append(conTractBeanPr.getHome()).append(" - ");
			}
			if (conTractBeanPr.getStreetName() != null) {
				address.append(conTractBeanPr.getStreetName()).append(" - ");
			}
			if (conTractBeanPr.getStreetBlock() != null) {
				address.append(conTractBeanPr.getStreetBlock()).append(" - ");
			}
			if (conTractBeanPr.getPrecinct() != null) {

				address.append(conTractBeanPr.getPrecinct()).append(" - ");
			}
			if (conTractBeanPr.getDistrict() != null) {
				address.append(conTractBeanPr.getDistrict()).append(" - ");
			}
			if (conTractBeanPr.getProvince() != null) {
				address.append(conTractBeanPr.getProvince());
			}
			Log.d("Log", "Check edit address null: " + txtdctbc + "adess :"
					+ address);

			txtdctbc.setText(conTractBeanPr.getAddress());
			txtdctbc.setEnabled(false);

			if (conTractBeanPr.getRepresentativeCustObj() != null) {
				txtdcgtxm.setText(conTractBeanPr.getRepresentativeCustObj()
						.getAddress());
				txtdcgtxm.setEnabled(false);
			}

			edtemailtbc.setText(conTractBeanPr.getEmail());
			edtemailtbc.setEnabled(false);

			edtmaKHCD.setText(conTractBeanPr.getCustId());
			edtmaKHCD.setEnabled(false);

			// thong tin phu luc
			// spinner_plhopdong.setEnabled(false);
			lnsophuluc.setVisibility(View.VISIBLE);
			edtplhdong.setEnabled(false);
			edtcvplhd.setEnabled(false);
			edit_ngaykyhd.setEnabled(false);
			edtmagiaytoCD.setEnabled(false);
			edit_ngaycappl.setEnabled(false);
			edtmanoicapCD.setEnabled(false);

			spinner_nhomkh.setEnabled(false);
			if (conTractBeanPr.getRepresentativeCustObj() != null) {
				if (conTractBeanPr.getRepresentativeCustObj()
						.getCustomerGroup() != null) {
					if (!CommonActivity.isNullOrEmpty(conTractBeanPr
							.getRepresentativeCustObj().getCustomerGroup()
							.getCode())) {
						Log.d("NHOM KHACH HANG", conTractBeanPr
								.getRepresentativeCustObj().getCustomerGroup()
								.getCode());
						if (arrCustomerGroupBeans != null
								&& !arrCustomerGroupBeans.isEmpty()) {
							findViewById(R.id.btnnhomkh).setVisibility(
									View.GONE);
							for (CustomerGroupBeans cusGroupBeans : arrCustomerGroupBeans) {
								if (conTractBeanPr.getRepresentativeCustObj()
										.getCustomerGroup().getCode()
										.equals(cusGroupBeans.getCode())) {
									spinner_nhomkh
											.setSelection(arrCustomerGroupBeans
													.indexOf(cusGroupBeans));
									break;
								}
							}
						}
					}
				}
			}

			spinner_loaikh.setEnabled(false);
			if (conTractBeanPr.getRepresentativeCustObj() != null) {
				if (conTractBeanPr.getRepresentativeCustObj().getCustomerType() != null) {
					Log.d("LOAI KHACH HANG", conTractBeanPr
							.getRepresentativeCustObj().getCustomerType()
							.getCode());
					if (!CommonActivity.isNullOrEmpty(conTractBeanPr
							.getRepresentativeCustObj().getCustomerType()
							.getCode())) {
						if (arrCustomerTypeByCustGroupBeans != null
								&& !arrCustomerTypeByCustGroupBeans.isEmpty()) {
							findViewById(R.id.btnloaikh).setVisibility(
									View.GONE);
							for (CustomerTypeByCustGroupBeans customerTypeByCustGroupBeans : arrCustomerTypeByCustGroupBeans) {
								if (conTractBeanPr
										.getRepresentativeCustObj()
										.getCustomerType()
										.getCode()
										.equals(customerTypeByCustGroupBeans
												.getCode())) {
									spinner_loaikh
											.setSelection(arrCustomerTypeByCustGroupBeans
													.indexOf(customerTypeByCustGroupBeans
															.getCode()));
									break;
								}

							}
						}
					}
				}
			}
			spinner_loaikh.setEnabled(false);

			if (conTractBeanPr.getRepresentativeCustObj() != null) {
				edit_tenKHDD.setText(conTractBeanPr.getRepresentativeCustObj()
						.getName());
				edit_ngaysinhd.setText(conTractBeanPr
						.getRepresentativeCustObj().getBirthDate());
				edit_quoctich.setText(conTractBeanPr.getRepresentativeCustObj()
						.getNationality());
				if (conTractBeanPr.getRepresentativeCustObj().getIdType()
						.equals("")) {
					edit_sogiaytoDD.setText(conTractBeanPr
							.getRepresentativeCustObj().getPopNo());
					edit_ngaycap.setText(conTractBeanPr
							.getRepresentativeCustObj().getPopIssueDateStr());
					edtnoicap.setText(conTractBeanPr.getRepresentativeCustObj()
							.getPopIssuePlace());
				}
				if (conTractBeanPr.getRepresentativeCustObj().getIdType()
						.equals("1")) {
					edit_sogiaytoDD.setText(conTractBeanPr
							.getRepresentativeCustObj().getIdNo());
					edit_ngaycap.setText(conTractBeanPr
							.getRepresentativeCustObj().getIdIssueDate());
					edtnoicap.setText(conTractBeanPr.getRepresentativeCustObj()
							.getIdIssuePlace());
				}
				// ho chieu
				if (conTractBeanPr.getRepresentativeCustObj().getIdType()
						.equals("3")) {
					edit_sogiaytoDD.setText(conTractBeanPr
							.getRepresentativeCustObj().getHc());
					edit_ngaycap.setText(conTractBeanPr
							.getRepresentativeCustObj().getHcIssueDateStr());
					edit_ngayhethan.setText(conTractBeanPr
							.getRepresentativeCustObj().getHcExpireDateStr());
					edtnoicap.setText(conTractBeanPr.getRepresentativeCustObj()
							.getHcIssuePlace());
				}
				// cmt quan doi
				if (conTractBeanPr.getRepresentativeCustObj().getIdType()
						.equals("4")) {
					edit_sogiaytoDD.setText(conTractBeanPr
							.getRepresentativeCustObj().getIdNoAM());
					edit_ngaycap
							.setText(conTractBeanPr.getRepresentativeCustObj()
									.getIdNoAMIssueDateStr());
					edtnoicap.setText(conTractBeanPr.getRepresentativeCustObj()
							.getIdNoAMIssuePlace());
				}
				edit_ngayhethan.setEnabled(false);

				if (conTractBeanPr.getRepresentativeCustObj().getSex()
						.equals("F")) {
					spinner_gioitinh.setSelection(1);
				} else {
					spinner_gioitinh.setSelection(0);
				}

				for (int j = 0; j < arrTypePaperBeans.size(); j++) {
					TypePaperBeans typePaperBeans = arrTypePaperBeans.get(j);
					if (conTractBeanPr.getRepresentativeCustObj().getIdType()
							.equals(typePaperBeans.getParType())) {
						spinner_type_giay_to_parent.setSelection(j);
						break;
					}
				}
			}

			txtdctbc.setEnabled(false);
			edtmaKHCD.setEnabled(false);
			edit_tenKHDD.setEnabled(false);
			edit_ngaysinhd.setEnabled(false);

			edit_quoctich.setEnabled(false);
			edit_sogiaytoDD.setEnabled(false);
			edit_ngaycap.setEnabled(false);
			edtnoicap.setEnabled(false);

			spinner_gioitinh.setEnabled(false);
			spinner_type_giay_to_parent.setEnabled(false);
			edit_ngaycap.setEnabled(false);
			edtnoicap.setEnabled(false);
			spinner_lvhd.setEnabled(false);
			spinner_lvhd.setVisibility(View.GONE);
			findViewById(R.id.btnlvhd).setVisibility(View.GONE);
			findViewById(R.id.prblvhd).setVisibility(View.GONE);

		} catch (Exception e) {
			Log.e(Constant.TAG,
					"FragmentConnectionInfoSetting reloadHDOld Exception: ", e);
		}

	}

	private void showPrecint(final ArrayList<AreaBean> arrAreaBeans) {
		final Dialog dialog = new Dialog(FragmentConnectionInfoSetting.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_layout_lst_pstn);
		TextView txtinfo = (TextView) dialog.findViewById(R.id.txtinfo);
		txtinfo.setText("");
		ListView lstpstn = (ListView) dialog.findViewById(R.id.lstpstn);

		GetPrecintAdapter adapPrecintAdapter = new GetPrecintAdapter(
				arrAreaBeans, FragmentConnectionInfoSetting.this);
		lstpstn.setAdapter(adapPrecintAdapter);
		lstpstn.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				txtphuongxa.setText(arrAreaBeans.get(arg2).getPrecinct());
				locationCode = areaBean.getProvince() + areaBean.getDistrict()
						+ txtphuongxa.getText().toString();

				// if(!"-1".equals(maKM) &&
				// !CommonActivity.isNullOrEmpty(productCode)){
				// lncuocdongtruoc.setVisibility(View.VISIBLE);
				// GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn =
				// new GetAllListPaymentPrePaidAsyn(
				// FragmentConnectionInfoSetting.this);
				// getAllListPaymentPrePaidAsyn.execute(maKM,
				// productCode, txttinhTP.getText().toString()
				// .trim()
				// + txtquanhuyen.getText().toString().trim()
				// + txtphuongxa.getText().toString().trim(),
				// dateNowString);
				// }

				if (regType != null && !regType.isEmpty()) {
					maKM = "-1";
					arrPromotionTypeBeans = new ArrayList<>();
					GetPromotionByMap getPromotionByMap = new GetPromotionByMap(
							FragmentConnectionInfoSetting.this);
					getPromotionByMap.execute(regType, offerId,
							provinceMain.trim(), districtMain.trim(),
							precinctMain.trim(), custommer.getCusGroupId(),

							custommer.getCusType(), idSubGroup, subType,
							serviceType);

					arrPaymentPrePaidPromotionBeans = new ArrayList<>();
					prepaidCode = "";
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FragmentConnectionInfoSetting.this,
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
						adapter.add(typePaperBeans.getName());
					}
					spinner_cuocdongtruoc.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					lncuocdongtruoc.setVisibility(View.GONE);
				}
				if (locationCode != null || !locationCode.isEmpty()) {
					try {
						GetAreaDal dal = new GetAreaDal(
								FragmentConnectionInfoSetting.this);
						dal.open();
						areaBean = dal.getAreaDal(locationCode);
						if (areaBean.getProvince() == null
								|| areaBean.getProvince().isEmpty()) {
							areaBean = dal.getAreaDal(locationCode.substring(0,
									locationCode.length() - 3));
							if (areaBean.getProvince() == null
									|| areaBean.getProvince().isEmpty()) {
								areaBean = dal.getAreaDal(locationCode
										.substring(0, locationCode.length() - 4));
							}
						}
						txtdiachidaydu.setText(areaBean.getFullAddress());
						dal.close();

					} catch (Exception e) {
						e.toString();
					}
				}
				dialog.dismiss();
			}
		});

		Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
		btnhuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				editisdnoracc.setText("");

			}
		});
		dialog.show();

	}

	// else {
	// Dialog dialog = CommonActivity.createAlertDialog(
	// FragmentConnectionInfoSetting.this, getResources()
	// .getString(R.string.checknotisdn),
	// getResources().getString(R.string.app_name));
	// dialog.show();
	// }
	// }

	private void getDataBundle() {

		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {

			provinceMain = mBundle.getString("province", "");
			districtMain = mBundle.getString("district", "");
			precinctMain = mBundle.getString("precinct", "");

			surveyOnline = (SurveyOnline) this.getIntent()
					.getSerializableExtra("ServeyKeyUpdate");
			lineType = mBundle.getString("ExamineJoin", "");
			Log.d("lineType", lineType);
			telecomServiceId = mBundle.getString("TeleServiceKey");
			Log.d("telecomServiceId", telecomServiceId);

			gponGroupAccountId = mBundle.getString("idDuongDayKey", "");
			relationType = mBundle.getString("relationTypeKey", "");
			mainSubId = mBundle.getString("mainSubIdKey", "");

			parentSubId = mBundle.getString("accHostAccKey", "");

			techology = mBundle.getString("techologyKey");
			Log.d("techology", techology);

			productCode = mBundle.getString("productCodeKey", "");
			if (mBundle.getString("locationCodeKey") != null) {
				locationCode = mBundle.getString("locationCodeKey", "");
				Log.d("locationCode", locationCode);
			}
			if (locationCode == null || locationCode.equals("")) {
				if (surveyOnline != null) {
					if (surveyOnline.getLocationCode() != null) {
						locationCode = surveyOnline.getLocationCode();
					}
				}

				// locationCode = "H004007002";
			}
			// productCode = mBundle.getString("productCodeKey", "");
			// Log.d("productCode", productCode);
			serviceType = mBundle.getString("serviceTypeKey", "");
			Log.d("serviceType", serviceType);
			offerId = mBundle.getString("offerIdKey", "");
		}
		if (locationCode != null || !locationCode.isEmpty()) {
			try {
				GetAreaDal dal = new GetAreaDal(this);
				dal.open();

				areaBean = dal.getAreaDal(locationCode);
				if (areaBean.getProvince() == null
						|| areaBean.getProvince().isEmpty()) {
					areaBean = dal.getAreaDal(locationCode.substring(0,
							locationCode.length() - 3));
					if (areaBean.getProvince() == null
							|| areaBean.getProvince().isEmpty()) {
						areaBean = dal.getAreaDal(locationCode.substring(0,
								locationCode.length() - 4));
					}
				}
				dal.close();

			} catch (Exception e) {
				e.toString();
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			onBackPressed();
			break;

		case R.id.btnkyhopdong:

			tenduong = txtduong.getText().toString();
			// tenduong = tenduong.replace(" ", "");
			// tenduong = tenduong.trim();
			sonha = txtsonha.getText().toString();
			// sonha = sonha.replace(" ", "");
			// sonha = sonha.trim();
			if (validateInputContract()) {
				if (txthoten.getText().toString() != null
						&& !txthoten.getText().toString().isEmpty()) {
					if (StringUtils.CheckCharSpecical(txthoten.getText()
                            .toString())) {
						Toast.makeText(
								FragmentConnectionInfoSetting.this,
								getResources()
										.getString(R.string.hotenspecical),
								Toast.LENGTH_SHORT).show();
					} else {
						if ((edtcodinh.getText().toString() != null && !edtcodinh
								.getText().toString().isEmpty())
								|| (edtdidong.getText().toString() != null && !edtdidong
										.getText().toString().isEmpty())) {
							// if (sonha != null && !sonha.isEmpty()) {
							// if (tenduong != null && !tenduong.isEmpty()) {
							if (idSubGroup != null && !idSubGroup.isEmpty()) {
								if (subType != null && !subType.isEmpty()) {
									if (regType != null && !regType.isEmpty()) {
										if (arrPromotionUnit != null
												&& arrPromotionUnit.size() > 1) {

											Spin spin = (Spin) spinner_kmtrainghiem
													.getSelectedItem();
											if (spin != null
													&& !CommonActivity
															.isNullOrEmpty(spin
																	.getId())) {
												validateInputSignContract();
											} else {
												Toast.makeText(
														FragmentConnectionInfoSetting.this,
														getResources()
																.getString(
																		R.string.checkchonmatrainghiem),
														Toast.LENGTH_SHORT)
														.show();

											}
										} else {
											validateInputSignContract();
										}
									} else {
										Toast.makeText(
												FragmentConnectionInfoSetting.this,
												getResources().getString(
														R.string.checkhthm),
												Toast.LENGTH_SHORT).show();
									}

								} else {
									Toast.makeText(
											FragmentConnectionInfoSetting.this,
											getResources().getString(
													R.string.checkloaithuebao),
											Toast.LENGTH_SHORT).show();
								}

							} else {
								Toast.makeText(
										FragmentConnectionInfoSetting.this,
										getResources().getString(
												R.string.checkgroupthuebao),
										Toast.LENGTH_SHORT).show();

							}

							// } else {
							// Toast.makeText(
							// FragmentConnectionInfoSetting.this,
							// getResources().getString(
							// R.string.checkduong),
							// Toast.LENGTH_SHORT).show();
							// }
							//
							// } else {
							// Toast.makeText(
							// FragmentConnectionInfoSetting.this,
							// getResources().getString(
							// R.string.checksonha),
							// Toast.LENGTH_SHORT).show();
							//
							// }

						} else {
							Toast.makeText(
									FragmentConnectionInfoSetting.this,
									getResources().getString(
											R.string.checkphoneNumber),
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(FragmentConnectionInfoSetting.this,
							getResources().getString(R.string.checkhoten),
							Toast.LENGTH_SHORT).show();
				}
			}

			break;
		case R.id.btnsinhacc:

			if (CommonActivity
                    .isNetworkConnected(FragmentConnectionInfoSetting.this)) {
				if (serviceType.equals("P")) {
					if (CommonActivity
                            .isNetworkConnected(FragmentConnectionInfoSetting.this)) {
						GetListPstnAsyn getListPstnAsyn = new GetListPstnAsyn(
								FragmentConnectionInfoSetting.this);
						getListPstnAsyn.execute(regType, offerId,
								surveyOnline.getStationId());
					} else {
						CommonActivity
								.createAlertDialog(
										FragmentConnectionInfoSetting.this,
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name)).show();
					}

				} else {
					if (serviceType.equals("N")) {

						if (CommonActivity
                                .isNetworkConnected(FragmentConnectionInfoSetting.this)) {
							GetListNGNAsyn getListNGNAsyn = new GetListNGNAsyn(
									FragmentConnectionInfoSetting.this);
							getListNGNAsyn.execute(regType, offerId,
									provinceMain);
						} else {
							CommonActivity
									.createAlertDialog(
											FragmentConnectionInfoSetting.this,
											getResources().getString(
													R.string.errorNetwork),
											getResources().getString(
													R.string.app_name)).show();
						}

					} else {

						if (CommonActivity
                                .isNetworkConnected(FragmentConnectionInfoSetting.this)) {

							GeneraAccountAsyn generaAccAsyn = new GeneraAccountAsyn(
									FragmentConnectionInfoSetting.this);
							generaAccAsyn.execute(serviceType,
									FragmentInfoCustomer.custommer
											.getNameCustomer(), provinceMain);
						} else {
							CommonActivity
									.createAlertDialog(
											FragmentConnectionInfoSetting.this,
											getResources().getString(
													R.string.errorNetwork),
											getResources().getString(
													R.string.app_name)).show();
						}
					}

				}

			} else {
				Dialog dialog = CommonActivity.createAlertDialog(
						FragmentConnectionInfoSetting.this, getResources()
								.getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

			break;
		case R.id.btnsinhaccPPPOE:

			if (CommonActivity
					.isNetworkConnected(FragmentConnectionInfoSetting.this)) {

				GeneraAccountPPPOEAsyn generaAccPPPOEAsyn = new GeneraAccountPPPOEAsyn(
						FragmentConnectionInfoSetting.this);
				generaAccPPPOEAsyn.execute(serviceType,
						FragmentInfoCustomer.custommer.getNameCustomer(),
						provinceMain);

			} else {
				CommonActivity.createAlertDialog(
						FragmentConnectionInfoSetting.this,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}

			break;

		case R.id.txtdctbc:

			String strProvince = Session.province;
			String strDistris = Session.district;

			if (areaDistrist != null
					&& !CommonActivity
							.isNullOrEmpty(areaDistrist.getDistrict())) {

				strDistris = areaDistrist.getDistrict();
			}
			if (areaProvicial != null
					&& !CommonActivity.isNullOrEmpty(areaProvicial
							.getProvince())) {
				strProvince = areaProvicial.getProvince();
			}

			Bundle mBundle = new Bundle();
			mBundle.putString("strProvince", strProvince);
			mBundle.putString("strDistris", strDistris);
			mBundle.putString("areaFlow", areaFlow);
			mBundle.putString("areaHomeNumber", areaHomeNumber);
			mBundle.putSerializable("areaPrecint", areaPrecint);
			mBundle.putSerializable("areaGroup", areaGroup);

			Intent i = new Intent(FragmentConnectionInfoSetting.this,
					CreateAddressCommon.class);
			i.putExtras(mBundle);
			startActivityForResult(i, 100);
			break;

		case R.id.txtdcgtxm:

			String strProvince1 = Session.province;
			String strDistris1 = Session.district;

			// if (strProvince1 == null || strProvince1.length() == 0
			// || strDistris1 == null || strDistris1.length() == 0) {
			// CommonActivity.createAlertDialog(
			// FragmentConnectionInfoSetting.this,
			// getString(R.string.message_address_staff_error),
			// getString(R.string.app_name)).show();
			// return;
			// }

			Bundle mBundle1 = new Bundle();
			mBundle1.putString("strProvince", strProvince1);
			mBundle1.putString("strDistris", strDistris1);
			mBundle1.putString("areaFlow", areaFlowPR);
			mBundle1.putString("areaHomeNumber", areaHomeNumberPR);
			mBundle1.putSerializable("areaPrecint", areaPrecintPR);
			mBundle1.putSerializable("areaGroup", areaGroupPR);
			mBundle1.putString("KEYPR", "1111");
			Intent i1 = new Intent(FragmentConnectionInfoSetting.this,
					CreateAddressCommon.class);
			i1.putExtras(mBundle1);
			startActivityForResult(i1, 101);
			break;

		case R.id.btnloaihopdong:
			new CacheDatabaseManager(instance).insertCacheList(TYPE_LOAI_HD,
					null);
			isRefreshTYPE_LOAI_HD = true;
			AsyntaskGetListAllCommon asyntaskGetListAllCommon2 = new AsyntaskGetListAllCommon(
					FragmentConnectionInfoSetting.this, TYPE_LOAI_HD);
			asyntaskGetListAllCommon2.execute();
			break;
		case R.id.btnhttthd:
			new CacheDatabaseManager(instance).insertCacheList(TYPE_HTTTHD,
					null);
			isRefreshTYPE_HTTTHD = true;
			AsyntaskGetListAllCommon asnGetHHTH = new AsyntaskGetListAllCommon(
					FragmentConnectionInfoSetting.this, TYPE_HTTTHD);
			asnGetHHTH.execute();
			break;
		case R.id.btnchukicuoc:
			new CacheDatabaseManager(instance).insertCacheList(TYPE_CK_CUOC_HD,
					null);
			isRefreshTYPE_CK_CUOC_HD = true;
			AsyntaskGetListAllCommon asnGetBillCycle = new AsyntaskGetListAllCommon(
					FragmentConnectionInfoSetting.this, TYPE_CK_CUOC_HD);
			asnGetBillCycle.execute();
			break;
		case R.id.btninchitiet:
			new CacheDatabaseManager(instance).insertCacheList(TYPE_HTTTHD,
					null);
			isRefreshTYPE_HTTTHD = true;
			AsyntaskGetListAllCommon asnInChiTiet = new AsyntaskGetListAllCommon(
					FragmentConnectionInfoSetting.this, TYPE_HTTTHD);
			asnInChiTiet.execute();
			break;
		case R.id.btnhttbc:
			new CacheDatabaseManager(instance).insertCacheList(TYPE_HTTBC_HD,
					null);
			isRefreshTYPE_HTTBC_HD = true;
			AsyntaskGetListAllCommon asnHTTBC = new AsyntaskGetListAllCommon(
					FragmentConnectionInfoSetting.this, TYPE_HTTBC_HD);
			asnHTTBC.execute();
			break;
		case R.id.btnttbosung:
			new CacheDatabaseManager(instance).insertCacheList(TYPE_TTBS_HD,
					null);
			isRefreshTYPE_TTBS_HD = true;
			AsyntaskGetListAllCommon asnTTBS = new AsyntaskGetListAllCommon(
					FragmentConnectionInfoSetting.this, TYPE_TTBS_HD);
			asnTTBS.execute();
			break;
		case R.id.btnnhomkh:
			if (custommer.getCustomerAttribute() != null
					&& !CommonActivity.isNullOrEmpty(custommer
							.getCustomerAttribute().getIdNo())) {
				GetListCusGroupAsyn getListCusGroupAsyn = new GetListCusGroupAsyn(
						FragmentConnectionInfoSetting.this);
				getListCusGroupAsyn.execute();
			}
			break;
		case R.id.btnloaikh:
			isRefreshCusType = true;
			GetCustomerTypeByCustGroupIdAsyn getCustomerTypeByCustGroupIdAsyn = new GetCustomerTypeByCustGroupIdAsyn(
					FragmentConnectionInfoSetting.this);
			getCustomerTypeByCustGroupIdAsyn.execute(cusGroupId);
			break;
		case R.id.btnlvhd:
			GetListFieldActivesAsynTask getListFieldActivesAsynTask = new GetListFieldActivesAsynTask(
					FragmentConnectionInfoSetting.this);
			getListFieldActivesAsynTask.execute();
			break;
		case R.id.btnSubGroup:
			GetListSubGroupAsyn getListSubGroupAsyn = new GetListSubGroupAsyn(
					FragmentConnectionInfoSetting.this);
			getListSubGroupAsyn.execute();
			break;
		case R.id.btnSubType:
			GetListSubTypeAsyn getListSubTypeAsyn = new GetListSubTypeAsyn(
					FragmentConnectionInfoSetting.this);
			getListSubTypeAsyn.execute(telecomServiceId, idSubGroup);
			break;

		case R.id.edtprovince:
			Intent intent = new Intent(getApplicationContext(),
					FragmentSearchLocation.class);
			intent.putExtra("arrProvincesKey", arrProvince);
			Bundle mBundleProvince = new Bundle();
			mBundleProvince.putString("checkKey", "1");
			intent.putExtras(mBundleProvince);
			startActivityForResult(intent, 106);
			break;
		case R.id.edtdistrict:
			Intent intent1 = new Intent(getApplicationContext(),
					FragmentSearchLocation.class);
			intent1.putExtra("arrDistrictKey", arrDistrict);
			Bundle mBundle2 = new Bundle();
			mBundle2.putString("checkKey", "2");
			intent1.putExtras(mBundle2);
			startActivityForResult(intent1, 107);
			break;
		case R.id.edtprecinct:
			Intent intent2 = new Intent(getApplicationContext(),
					FragmentSearchLocation.class);
			intent2.putExtra("arrPrecinctKey", arrPrecinct);
			Bundle mBundle3 = new Bundle();
			mBundle3.putString("checkKey", "3");
			intent2.putExtras(mBundle3);
			startActivityForResult(intent2, 108);
			break;
		default:
			break;
		}
	}

	private void checkHangHoa() {

		// validate dat coc
		// if (!CommonActivity.isNullOrEmpty(hthmBeans.getDescription())
		// && hthmBeans.getDescription().contains("$datcoc$")) {
		//
		// SharedPreferences preferences =
		// FragmentConnectionInfoSetting.this.getSharedPreferences(Define.PRE_NAME,
		// Activity.MODE_PRIVATE);
		// String name = preferences.getString(Define.KEY_MENU_NAME, "");
		// if (!name.contains(";menu.deposit;")) {
		// CommonActivity.createAlertDialog(FragmentConnectionInfoSetting.this,
		// getResources().getString(R.string.permissiondeposit),
		// getResources().getString(R.string.app_name)).show();
		// return;
		// }
		//
		// ReasonPledgeDTO item = (ReasonPledgeDTO)
		// spinner_deposit.getSelectedItem();
		//
		// if (CommonActivity.isNullOrEmpty(item)) {
		// CommonActivity.createAlertDialog(FragmentConnectionInfoSetting.this,
		// getResources().getString(R.string.validatedatcoc),
		// getResources().getString(R.string.app_name))
		// .show();
		// return;
		// }
		//
		// if (CommonActivity.isNullOrEmpty(item.getNumMoney()) ||
		// CommonActivity.isNullOrEmpty(item.getNumMonth())) {
		// CommonActivity.createAlertDialog(FragmentConnectionInfoSetting.this,
		// getResources().getString(R.string.validatedatcoc),
		// getResources().getString(R.string.app_name))
		// .show();
		// return;
		// }
		// }

		if (arrPaymentPrePaidPromotionBeans != null
				&& arrPaymentPrePaidPromotionBeans.size() > 1) {
			if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
				if (mArrayListThongTinHH.size() > 0) {
					if (mapSubStockModelRelReq.size() == mArrayListThongTinHH
							.size()) {
						checkInputSignContract();
					} else {
						Toast.makeText(
								FragmentConnectionInfoSetting.this,
								getResources().getString(R.string.checkhanghoa),
								Toast.LENGTH_LONG).show();
					}

				} else {
					checkInputSignContract();
				}
			} else {

				if (!CommonActivity.isNullOrEmpty(maKM)) {
					CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this,
							getString(R.string.checkcuocdongtruoc),
							getString(R.string.app_name)).show();
				} else {
					if (mArrayListThongTinHH.size() > 0) {
						if (mapSubStockModelRelReq.size() == mArrayListThongTinHH
								.size()) {
							checkInputSignContract();
						} else {
							Toast.makeText(
									FragmentConnectionInfoSetting.this,
									getResources().getString(
											R.string.checkhanghoa),
									Toast.LENGTH_LONG).show();
						}

					} else {
						checkInputSignContract();
					}
				}

			}

		} else {
			if (mArrayListThongTinHH.size() > 0) {
				if (mapSubStockModelRelReq.size() == mArrayListThongTinHH
						.size()) {
					checkInputSignContract();
				} else {
					Toast.makeText(FragmentConnectionInfoSetting.this,
							getResources().getString(R.string.checkhanghoa),
							Toast.LENGTH_LONG).show();
				}

			} else {
				checkInputSignContract();
			}
		}

	}

	private void checkInputSignContract() {

		if (CommonActivity
                .isNetworkConnected(FragmentConnectionInfoSetting.this)) {

			CommonActivity.createDialog(
					FragmentConnectionInfoSetting.this,
					FragmentConnectionInfoSetting.this.getResources()
							.getString(R.string.checksignContract),
					FragmentConnectionInfoSetting.this.getResources()
							.getString(R.string.app_name),
					FragmentConnectionInfoSetting.this.getResources()
							.getString(R.string.buttonOk),
					FragmentConnectionInfoSetting.this.getResources()
							.getString(R.string.cancel), signContractCListener,
					null).show();
		} else {
			CommonActivity.createAlertDialog(
					FragmentConnectionInfoSetting.this,
					this.getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name)).show();
		}

	}

	private final OnClickListener signContractCListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			SignContractAsyn signContractAsyn = new SignContractAsyn(
					FragmentConnectionInfoSetting.this);
			signContractAsyn.execute();

		}
	};

	// == init lstIp
    private void initLstIp() {
		// String ipBean = this.getResources().getString(R.string.chonip);
		// arrListIp.add(0, ipBean);
		if (arrListIp != null && arrListIp.size() >= 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (String ip : arrListIp) {
				adapter.add(ip);
			}
			spinner_iptinh.setAdapter(adapter);
		}
	}

	// == init lstIp
    private void initLstIpNotData() {
		// String ipBean = this.getResources().getString(R.string.chonip);
		// ArrayList<String> lstIp = new ArrayList<String>();
		// lstIp.add(0, ipBean);
		// if (lstIp != null && lstIp.size() > 0) {
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		// for (String ip : lstIp) {
		// adapter.add(ip);
		// }
		spinner_iptinh.setAdapter(adapter);
		// }
	}

	// === hthm co data
    private void initHTHM() {
		HTHMBeans hthmBeans = new HTHMBeans();
		hthmBeans.setCodeName(getResources().getString(R.string.chonhthm));
		arrHthmBeans.add(0, hthmBeans);
		// if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.pakage_charge_item, R.id.txtpakage);
		// adapter.setDropDownViewResource(R.layout.pakage_charge_item);
		// for (HTHMBeans hBeans : arrHthmBeans) {
		// adapter.add(hBeans.getCodeName());
		// }
		// spinner_hthm.setAdapter(adapter);
		// }
	}

	// hthm ko data
	private void initHTHMNotData() {
		HTHMBeans hthmBeans = new HTHMBeans();
		hthmBeans.setName(getResources().getString(R.string.chonhthm));
		ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<>();
		lstHthmBeans.add(0, hthmBeans);
		if (lstHthmBeans != null && lstHthmBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (HTHMBeans hBeans : lstHthmBeans) {
				adapter.add(hBeans.getName());
			}
			spinner_hthm.setAdapter(adapter);
		}
	}

	// init spiner ListSubGroup
	private void initListSubGroup() {
		SubGroupBeans subBeans = new SubGroupBeans();
		subBeans.setName(this.getResources()
				.getString(R.string.chonnhomthuebao));
		arrSubGroupBeans.add(0, subBeans);
		if (arrSubGroupBeans != null && arrSubGroupBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (SubGroupBeans subGroupBeans : arrSubGroupBeans) {
				adapter.add(subGroupBeans.getName());
			}
			spinner_nhomthuebao.setAdapter(adapter);
		}
	}

	// init spinner ListSubType
    private void initListSubType() {
		SubTypeBeans subTypeBean = new SubTypeBeans();
		subTypeBean.setName(this.getResources().getString(
				R.string.chonloaithuebao));
		arrSubTypeBeans.add(0, subTypeBean);
		if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line,
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
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (SubTypeBeans subTyBeans : arrSubTypeBeanss) {
				adapter.add(subTyBeans.getName());
			}
			spinner_loaithuebao.setAdapter(adapter);
		}
	}

	// init spinner listsubPromotion

	private void initInitListPromotion() {

		PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
		promotionTypeBeans.setCodeName(getResources().getString(
				R.string.chonctkm1));
		promotionTypeBeans.setPromProgramCode("-1");
		arrPromotionTypeBeans.add(0, promotionTypeBeans);

		if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() > 1) {

		} else {
			PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
			promotionTypeBeans1.setCodeName(getResources().getString(
					R.string.selectMKM));
			promotionTypeBeans1.setPromProgramCode("");
			arrPromotionTypeBeans.add(1, promotionTypeBeans1);
		}

		// if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() >
		// 0) {
		// adapterHTKM = new ArrayAdapter<String>(this,
		// android.R.layout.simple_dropdown_item_1line,
		// android.R.id.text1);
		// for (PromotionTypeBeans proBeans : arrPromotionTypeBeans) {
		// adapterHTKM.add(proBeans.getCodeName());
		// }
		// spinner_makm.setAdapter(adapterHTKM);
		// }
	}

	// init spinner not data subpromotion
	public void initInitListPromotionNotData() {

		// PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
		// promotionTypeBeans.setCodeName(getResources().getString(
		// R.string.selectMKM));
		// ArrayList<PromotionTypeBeans> arrPromotionTypeBeans1 = new
		// ArrayList<PromotionTypeBeans>();
		// arrPromotionTypeBeans1.add(0, promotionTypeBeans);
		if (arrPromotionTypeBeans != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			// for (PromotionTypeBeans proBeans : arrPromotionTypeBeans1) {
			// adapter.add(proBeans.getCodeName());
			// }
			spinner_makm.setAdapter(adapter);
		}
	}

	// move login
	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(FragmentConnectionInfoSetting.this,
//					LoginActivity.class);
//			startActivity(intent);
//			FragmentConnectionInfoSetting.this.finish();
//			ActivityCreateNewRequest.instance.finish();
//			MainActivity.getInstance().finish();
			 LoginDialog dialog = new LoginDialog(FragmentConnectionInfoSetting.this,
                     ";cm.connect_sub_CD;");

             dialog.show();

		}
	};

	// =========== ws ky hop dong ===============================
	public class SignContractAsyn extends AsyncTask<String, Void, Void> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public SignContractAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.kyhopdong1));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(String... arg0) {

			CustommerByIdNoBean custommerByIdNoBean = FragmentInfoCustomer.custommer;
			Contract contract = null;
			SubReqDeployment subReqDeployment = null;
			// if(serviceType.equals("P")){
			// subReqDeployment = new SubReqDeployment(
			// "1000",
			// "355476",
			// "H-HNI0062/222A",
			// "HNI0062", "HNI09");
			// }else{
			subReqDeployment = new SubReqDeployment(
					surveyOnline.getSurfaceRadius(),
					surveyOnline.getConnectorId(),
					surveyOnline.getConnectorCode(),
					surveyOnline.getStationCode(), surveyOnline.getDeptCode(),
					surveyOnline.getVendorCode(),
					surveyOnline.getConnectorType());

			// }

			if (lineType.equals("A+P") || lineType.equals("A/P")
					|| lineType.equals("P/A")) {

			} else {
				lineType = serviceType;
			}

            String fromDate = "";
            SubRequest subRequest = new SubRequest(lineType, editmatkhau.getText()
                    .toString(), editisdnoracc.getText().toString(), "",
					idSubGroup, subType, "", maKM, regType, ipStatic, CommonActivity.getNormalText(txthoten
							.getText().toString()), serviceType, productCode,
                    offerId, techology, notifyStatus, edtcodinh.getText()
                    .toString(), edtdidong.getText().toString(), "",
                    fromDate, "", "", "");

			List<SubStockModelRelReq> lstSubStockModelRelReq = new ArrayList<>();
			if (mapSubStockModelRelReq != null
					&& !mapSubStockModelRelReq.isEmpty()) {
				lstSubStockModelRelReq.addAll(mapSubStockModelRelReq.values());
			}
			String fullAdrr = edtprecinct.getText().toString().trim() + " "
					+ edtdistrict.getText().toString().trim() + " "
					+ edtprovince.getText().toString().trim();
			if (!CommonActivity.isNullOrEmpty(tenduong)) {
				fullAdrr = tenduong.trim() + " " + fullAdrr;
			}

			if (!CommonActivity.isNullOrEmpty(sonha)) {
				fullAdrr = sonha.trim() + " " + fullAdrr;
			}

			luuHopDong(provinceMain, districtMain, precinctMain, fullAdrr,
					gponGroupAccountId, custommerByIdNoBean, contract,
                    subRequest, subReqDeployment, lstSubStockModelRelReq);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			progress.dismiss();
			if (errorCode.equals("0")) {

				if (infoConnectionBeans.getIdRequest() != null
						&& !infoConnectionBeans.getIdRequest().isEmpty()) {
					Intent intent = new Intent(
							FragmentConnectionInfoSetting.this,
							FragmentConnectFromSignContract.class);
					Bundle mBundle = new Bundle();
					mBundle.putString("serviceTypeKey", serviceType);
					mBundle.putString("productCodeKey", productCode);
					mBundle.putString("reasonIdKey", "" + reasonId);
					mBundle.putSerializable("ConnectKey", infoConnectionBeans);
					intent.putExtras(mBundle);
					startActivity(intent);
					// btnkyhopdong.setEnabled(false);
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					LoginDialog dialog = new LoginDialog(
							FragmentConnectionInfoSetting.this,
							";cm.connect_sub_CD;");

					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private void luuHopDong(String province, String district,
				String precinct, String fullAddress, String gponGroupAccountId,
				CustommerByIdNoBean custommerByIdNoBean, Contract contract,
				SubRequest subRequest, SubReqDeployment subReqDeployment,
				List<SubStockModelRelReq> arrSubStockModelRelReq) {
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_signContractSmartphone");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:signContractSmartphone>");
				HashMap<String, String> paramToken = new HashMap<>();
				HashMap<String, String> param = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<smartphoneInputBO>");

				rawData.append("<province>").append(province);
				rawData.append("</province>");

				rawData.append("<district>").append(district);
				rawData.append("</district>");

				rawData.append("<precint>").append(precinct);
				rawData.append("</precint>");

				rawData.append("<address>"
						+ StringUtils.escapeHtml(fullAddress));
				rawData.append("</address>");
				if (tenduong != null && !tenduong.isEmpty()) {
					rawData.append("<streetName>"
							+ StringUtils.escapeHtml(tenduong));
					rawData.append("</streetName>");
				}

				if (sonha != null && !sonha.isEmpty()) {
					rawData.append("<home>" + StringUtils.escapeHtml(sonha));
					rawData.append("</home>");
				}

				rawData.append("<customerPosition>").append(surveyOnline.getCustomerPossition());
				rawData.append("</customerPosition>");

				rawData.append("<cableBoxPosition>").append(surveyOnline.getCableBoxPosition());
				rawData.append("</cableBoxPosition>");

				rawData.append("<polylineData>").append(surveyOnline.getPolylineData());
				rawData.append("</polylineData>");
				// rawData.append("<lng>" + surveyOnline.getLng());
				// rawData.append("</lng>");

				if (gponGroupAccountId != null
						&& !gponGroupAccountId.equals("")) {
					rawData.append("<gponGroupAccountId>").append(gponGroupAccountId);
					rawData.append("</gponGroupAccountId>");
				}

				if (relationType != null && !relationType.equals("")) {
					rawData.append("<relationType>").append(relationType);
					rawData.append("</relationType>");
				}

				// thue bao chinh
				if (mainSubId != null && !mainSubId.equals("")) {
					rawData.append("<mainSubId>").append(mainSubId);
					rawData.append("</mainSubId>");

				}

				// HashMap<String, String> paramCustomer = new HashMap<String,
				// String>();
				rawData.append("<customer>");

				if (custommer.getCustId() != null
						&& !custommer.getCustId().isEmpty()) {
					rawData.append("<custId>").append(custommer.getCustId());
					rawData.append("</custId>");
					if (!CommonActivity.isNullOrEmpty(custommer
							.getBusPermitNo())) {
						rawData.append("<busPermitNo>").append(custommer.getBusPermitNo());
						rawData.append("</busPermitNo>");

					} else {
						rawData.append("<idNo>").append(custommer.getIdNo());
						rawData.append("</idNo>");
					}
				} else {
					if (custommer.getAddreseCus() != null
							&& !custommer.getAddreseCus().isEmpty()) {
						rawData.append("<address>" + StringUtils.escapeHtml(custommer.getAddreseCus()));
						rawData.append("</address>");
						// paramCustomer.put("address",
						// custommer.getAddreseCus());
					}
					rawData.append("<name>" + StringUtils.escapeHtml(custommer.getNameCustomer()));
					rawData.append("</name>");
					// paramCustomer.put("name", custommer.getNameCustomer());
					rawData.append("<busType>").append(custommer.getCusType());
					rawData.append("</busType>");

					rawData.append("<province>").append(custommer.getProvince());
					rawData.append("</province>");
					rawData.append("<district>").append(custommer.getDistrict());
					rawData.append("</district>");
					rawData.append("<precinct>").append(custommer.getPrecint());
					rawData.append("</precinct>");

					rawData.append("<areaCode>").append(custommer.getProvince()).append(custommer.getDistrict()).append(custommer.getPrecint()).append(custommer.getStreet_block());
					rawData.append("</areaCode>");

					rawData.append("<streetBlock>").append(custommer.getStreet_block());
					rawData.append("</streetBlock>");
					rawData.append("<streetName>" + StringUtils.escapeHtml(custommer.getStreet()));
					rawData.append("</streetName>");
					rawData.append("<home>" + StringUtils.escapeHtml(custommer.getHome()));
					rawData.append("</home>");

					// paramCustomer.put("name", custommer.getNameCustomer());
					rawData.append("<address>" + StringUtils.escapeHtml(custommer.getAddreseCus()));
					rawData.append("</address>");

					// paramCustomer.put("busType", custommer.getCusType());

					if (!CommonActivity.isNullOrEmpty(custommer
							.getBusPermitNo())) {
						rawData.append("<busPermitNo>").append(custommer.getBusPermitNo());
						rawData.append("</busPermitNo>");

						rawData.append("<tin>").append(custommer.getTin());
						rawData.append("</tin>");
						// paramCustomer
						// .put("busPermitNo", custommer.busPermitNo());
					} else {
						rawData.append("<idNo>").append(custommer.getIdNo());
						rawData.append("</idNo>");
						rawData.append("<sex>").append(custommer.getSex());
						rawData.append("</sex>");
					}

					if (!CommonActivity.isNullOrEmpty(custommer
							.getBusPermitNo())) {

						rawData.append("<idType>" + "");
						rawData.append("</idType>");

					} else {
						if (custommer.getIdType() != null
								&& !custommer.getIdType().isEmpty()) {
							rawData.append("<idType>").append(custommer.getIdType());
							rawData.append("</idType>");
							if ("3".equals(custommer.getIdType())) {
								rawData.append("<idExpireDateStr>").append(custommer.getStrIdExpire());
								rawData.append("</idExpireDateStr>");
							}
						}
					}

					if (custommer.getIdIssueDate() != null
							&& !custommer.getIdIssueDate().isEmpty()) {
						rawData.append("<idIssueDateStr>").append(custommer.getIdIssueDate());
						rawData.append("</idIssueDateStr>");
					}

					if (custommer.getIdIssuePlace() != null
							&& !custommer.getIdIssuePlace().isEmpty()) {
						rawData.append("<idIssuePlace>"
								+ StringUtils.escapeHtml(custommer
										.getIdIssuePlace()));
						rawData.append("</idIssuePlace>");
					}

					if (custommer.getBirthdayCus() != null
							&& !custommer.getBirthdayCus().isEmpty()) {
						rawData.append("<birthDateStr>").append(custommer.getBirthdayCus());
						rawData.append("</birthDateStr>");
					}

				}

				// bo sung khach hang dai dien cho khach hang doanh nghiep
				if (custommer.getCustomerAttribute() != null) {
					if (!CommonActivity.isNullOrEmpty(custommer
							.getCustomerAttribute().getIdNo())) {

						rawData.append("<customerAttribute>");
						rawData.append("<birthDateStr>").append(custommer.getCustomerAttribute()
                                .getBirthDate());
						rawData.append("</birthDateStr>");
						rawData.append("<idType>").append(custommer.getCustomerAttribute().getIdType());
						rawData.append("</idType>");
						rawData.append("<idNo>").append(custommer.getCustomerAttribute().getIdNo());
						rawData.append("</idNo>");
						rawData.append("<issueDateStr>").append(custommer.getCustomerAttribute()
                                .getIssueDate());
						rawData.append("</issueDateStr>");
						rawData.append("<issuePlace>"
								+ StringUtils
										.escapeHtml(custommer
												.getCustomerAttribute()
												.getIssuePlace()));
						rawData.append("</issuePlace>");
						rawData.append("<name>"
								+ StringUtils.escapeHtml(custommer.getCustomerAttribute().getName()));
						rawData.append("</name>");
						rawData.append("</customerAttribute>");
					}
				}
				rawData.append("</customer>");
				// paramCustomer.put("customer", input.buildXML(paramCustomer));
				// rawData.append(input.buildXML(paramCustomer));

				// contract

				if (!CommonActivity.isNullOrEmpty(conTractBean.getContractId())) {
					rawData.append("<contract>");

					rawData.append("<contractId>").append(conTractBean.getContractId());
					rawData.append("</contractId>");

					rawData.append("<contractOffer>");
					if (!CommonActivity.isNullOrEmpty(edtplhdong.getText()
							.toString().trim())) {
						rawData.append("<contractOfferId>").append(edtplhdong.getText().toString().trim());
						rawData.append("</contractOfferId>");
					} else {

						rawData.append("<represent>"
								+ StringUtils.escapeHtml(edtdaidienpl.getText().toString().trim()));
						rawData.append("</represent>");
						if (typePaperBeansOffer != null) {
							rawData.append("<idType>").append(typePaperBeansOffer.getParType());
							rawData.append("</idType>");
						}

						rawData.append("<representPosition>"
								+ StringUtils.escapeHtml(edtcvplhd.getText().toString().trim()));
						rawData.append("</representPosition>");
						rawData.append("<strSignDatetime>").append(edit_ngaykyhd.getText().toString().trim());
						rawData.append("</strSignDatetime>");
						rawData.append("<idNo>").append(edtmagiaytoCD.getText().toString().trim());
						rawData.append("</idNo>");
						rawData.append("<strIssueDatetime>").append(edit_ngaycappl.getText().toString().trim());
						rawData.append("</strIssueDatetime>");
						rawData.append("<issuePlace>"
								+ StringUtils.escapeHtml(edtmanoicapCD
										.getText().toString().trim()));
						rawData.append("</issuePlace>");
					}
					rawData.append("</contractOffer>");

					rawData.append("</contract>");
				} else {
					rawData.append("<contract>");

					// THONG TIN CONTRACT BANK
					// "account",
					// "accountName",
					// "bankCode",
					// "bankContractDate",
					// "bankContractNo",
					// "contractId",
					// "description",
					// "id",
					// "status",
					// "strBankContractDate"
					rawData.append("<contractBank>");

					rawData.append("<strBankContractDate>").append(edit_ngaynhothu.getText().toString());
					rawData.append("</strBankContractDate>");
					rawData.append("<bankContractNo>").append(edthdnhothu.getText().toString().trim());
					rawData.append("</bankContractNo>");
					rawData.append("<account>"
							+ StringUtils.escapeHtml(edttkoanhd.getText().toString().trim()));
					rawData.append("</account>");
					rawData.append("<accountName>"
							+  StringUtils.escapeHtml(edttentkoan.getText().toString().trim()));
					rawData.append("</accountName>");
					if (searchBank != null) {
						rawData.append("<bankCode>").append(searchBank.getBankCode());
						rawData.append("</bankCode>");
					}

					rawData.append("</contractBank>");

					rawData.append("<payer>"
							+ StringUtils.escapeHtml(edtnguoithanhtoan
									.getText().toString().trim()));
					rawData.append("</payer>");
					Spin spinloaihd = (Spin) spinner_loaihopdong
							.getSelectedItem();
					if (spinloaihd != null) {
						rawData.append("<contractType>").append(spinloaihd.getId());
						rawData.append("</contractType>");
						rawData.append("<contractTypeCode>").append(spinloaihd.getId());
						rawData.append("</contractTypeCode>");
					}

					rawData.append("<strSignDate>").append(edit_ngayky.getText().toString());
					rawData.append("</strSignDate>");
					rawData.append("<strEffectDate>").append(edit_ngayhieuluc.getText().toString());
					rawData.append("</strEffectDate>");
					rawData.append("<strEndEffectDate>").append(edit_ngayhethan.getText().toString());
					rawData.append("</strEndEffectDate>");

					Spin spinHTTThd = (Spin) spinner_httthd.getSelectedItem();
					if (spinHTTThd != null) {
						rawData.append("<payMethodCode>").append(spinHTTThd.getId());
						rawData.append("</payMethodCode>");
					}

					Spin spinCKC = (Spin) spinner_chukicuoc.getSelectedItem();
					if (spinCKC != null) {
						rawData.append("<billCycleFrom>").append(spinCKC.getId());
						rawData.append("</billCycleFrom>");
						rawData.append("<billCycleFromCharging>").append(spinCKC.getId());
						rawData.append("</billCycleFromCharging>");
					}
					rawData.append("<billAddress>"
							+ StringUtils.escapeHtml(edtdchdcuoc.getText().toString().trim()));
					rawData.append("</billAddress>");
					// thong tin in chi tiet
					Spin spinINCT = (Spin) spinner_inchitiet.getSelectedItem();
					if (spinINCT != null) {
						// if("P".equals(serviceType) ||
						// "N".equals(serviceType)){
						rawData.append("<printMethodCode>").append(spinINCT.getId());
						rawData.append("</printMethodCode>");
						// }
					}

					// thong tin thong bao cuoc
					Spin spinHTTBC = (Spin) spinner_httbc.getSelectedItem();
					if (spinHTTBC != null) {
						rawData.append("<noticeCharge>").append(spinHTTBC.getId());
						rawData.append("</noticeCharge>");
					}

					rawData.append("<email>").append(StringUtils.escapeHtml(edtemailtbc.getText().toString().trim()));
					rawData.append("</email>");

					rawData.append("<telFax>").append(edtdtcdtbc.getText().toString().trim());
					rawData.append("</telFax>");

					rawData.append("<telMobile>").append(txtdtdidongtbc.getText().toString().trim());
					rawData.append("</telMobile>");
					if (areaProvicial != null) {
						rawData.append("<province>").append(areaProvicial.getProvince());
						rawData.append("</province>");
					}
					if (areaPrecint != null) {
						rawData.append("<precinct>").append(areaPrecint.getPrecinct());
						rawData.append("</precinct>");
					}
					if (areaDistrist != null) {
						rawData.append("<district>").append(areaDistrist.getDistrict());
						rawData.append("</district>");
					}

					if (areaGroup != null) {
						rawData.append("<streetBlock>").append(areaGroup.getAreaCode());
						rawData.append("</streetBlock>");
					}
					if (areaProvicial != null && areaDistrist != null
							&& areaPrecint != null && areaGroup != null) {
						rawData.append("<payAreaCode>").append((areaProvicial.getProvince()
                                + areaDistrist.getDistrict()
                                + areaPrecint.getPrecinct() + areaGroup
                                .getAreaCode()).replace("null", ""));
						rawData.append("</payAreaCode>");
					}

					rawData.append("<streetName>" + StringUtils.escapeHtml(areaFlow));
					rawData.append("</streetName>");
					rawData.append("<home>" + StringUtils.escapeHtml(areaHomeNumber));
					rawData.append("</home>");

					if (!CommonActivity.isNullOrEmpty(txtdctbc.getText()
							.toString())) {
						if (!txtdctbc
								.getText()
								.toString()
								.trim()
								.equalsIgnoreCase(
										getString(R.string.sellect_address))) {
							rawData.append("<address>"
									+ StringUtils.escapeHtml(txtdctbc.getText().toString()));
							rawData.append("</address>");
						} else {
							rawData.append("<address>" + "");
							rawData.append("</address>");
						}
					}

					// thong tin in hop dong
					Spin spinINBS = (Spin) spinner_ttbosung.getSelectedItem();
					if (spinINBS != null) {
						rawData.append("<contractPrint>").append(spinINBS.getId());
						rawData.append("</contractPrint>");
					}

					// them phu luc hop dong
					rawData.append("<contractOffer>");
					if (!CommonActivity.isNullOrEmpty(edtplhdong.getText()
							.toString().trim())) {
						rawData.append("<contractOfferId>").append(edtplhdong.getText().toString().trim());
						rawData.append("</contractOfferId>");
					} else {

						rawData.append("<represent>"
								+ StringUtils.escapeHtml(edtdaidienpl.getText().toString().trim()));
						rawData.append("</represent>");
						rawData.append("<idType>").append(typePaperBeansOffer.getParType());
						rawData.append("</idType>");
						rawData.append("<representPosition>"
								+ StringUtils.escapeHtml(edtcvplhd.getText().toString().trim()));
						rawData.append("</representPosition>");
						rawData.append("<strSignDatetime>").append(edit_ngaykyhd.getText().toString().trim());
						rawData.append("</strSignDatetime>");
						rawData.append("<idNo>").append(edtmagiaytoCD.getText().toString().trim());
						rawData.append("</idNo>");
						rawData.append("<strIssueDatetime>").append(edit_ngaycappl.getText().toString().trim());
						rawData.append("</strIssueDatetime>");
						rawData.append("<issuePlace>"
								+ StringUtils.escapeHtml(edtmanoicapCD
										.getText().toString().trim()));
						rawData.append("</issuePlace>");
					}
					rawData.append("</contractOffer>");

					// thong tin dai dien hop dong
					rawData.append("<representativeCust>");
					if (custommerRepre != null
							&& !CommonActivity.isNullOrEmpty(custommerRepre
									.getCustId())) {
						// dai dien khach hang cu
						rawData.append("<custId>").append(custommerRepre.getCustId());
						rawData.append("</custId>");
						rawData.append("<idNo>").append(custommerRepre.getIdNo());
						rawData.append("</idNo>");
					} else {
						rawData.append("<custGroupId>").append(cusGroupId);
						rawData.append("</custGroupId>");
						rawData.append("<busType>").append(busType);
						rawData.append("</busType>");
						if (fieldAcObj != null) {
							rawData.append("<fieldActiveId>").append(fieldAcObj.getFieldActiveId());
							rawData.append("</fieldActiveId>");
						}

						rawData.append("<custId>").append(edtmaKHCD.getText().toString().trim());
						rawData.append("</custId>");

						// thong tin khach hang dai dien
						rawData.append("<name>"
								+ StringUtils.escapeHtml(edit_tenKHDD.getText().toString().trim()));
						rawData.append("</name>");
						rawData.append("<birthDateStr>").append(edit_ngaysinhd.getText().toString().trim());
						rawData.append("</birthDateStr>");

						rawData.append("<sex>").append(gioitinh.getAreaCode());
						rawData.append("</sex>");

						rawData.append("<nationality>").append(edit_quoctich.getText().toString());
						rawData.append("</nationality>");

						if (typePaperBeans != null) {
							rawData.append("<idType>").append(typePaperBeans.getParType());
							rawData.append("</idType>");
							if (typePaperBeans.getParType() != null) {
								// if (typePaperBeans.getParType().equals("")) {
								// rawData.append("<popNo>"
								// + edit_sogiaytoDD.getText()
								// .toString());
								// rawData.append("</popNo>");
								//
								// rawData.append("<popIssueDateStr>"
								// + edit_ngaycap.getText().toString());
								// rawData.append("</popIssueDateStr>");
								//
								// rawData.append("<popIssuePlace>"
								// + edtnoicap.getText().toString());
								// rawData.append("</popIssuePlace>");
								// }
								// if (typePaperBeans.getParType().equals("1"))
								// {
								rawData.append("<idNo>").append(edit_sogiaytoDD.getText().toString());
								rawData.append("</idNo>");

								rawData.append("<idIssueDateStr>").append(edit_ngaycap.getText().toString());
								rawData.append("</idIssueDateStr>");

								rawData.append("<idIssuePlace>").append(StringUtils.escapeHtml(edtnoicap
                                        .getText().toString()));
								rawData.append("</idIssuePlace>");
								// }
								if ("3".equals(typePaperBeans.getParType())
										|| !typePaperBeans.getParType()
												.isEmpty()) {
									// rawData.append("<hc>"
									// + edit_sogiaytoDD.getText()
									// .toString());
									// rawData.append("</hc>");
									//
									// rawData.append("<hcIssueDateStr>"
									// + edit_ngaycap.getText().toString());
									// rawData.append("</hcIssueDateStr>");
									//
									// rawData.append("<hcIssuePlace>"
									// + edtnoicap.getText().toString());
									// rawData.append("</hcIssuePlace>");

									rawData.append("<idExpireDateStr>").append(edit_ngayhethan.getText()
                                            .toString());
									rawData.append("</idExpireDateStr>");

								}
								// if (typePaperBeans.getParType().equals("4"))
								// {
								// rawData.append("<idNoAM>"
								// + edit_sogiaytoDD.getText()
								// .toString());
								// rawData.append("</idNoAM>");
								//
								// rawData.append("<idNoAMIssueDateStr>"
								// + edit_ngaycap.getText().toString());
								// rawData.append("</idNoAMIssueDateStr>");
								//
								// rawData.append("<idNoAMIssuePlace>"
								// + edtnoicap.getText().toString());
								// rawData.append("</idNoAMIssuePlace>");
								// }
							}

						}

						if (areaProvicialPR != null) {
							rawData.append("<province>").append(areaProvicialPR.getProvince());
							rawData.append("</province>");
						}

						if (areaPrecintPR != null) {
							rawData.append("<precinct>").append(areaPrecintPR.getPrecinct());
							rawData.append("</precinct>");
						}

						if (areaDistristPR != null) {
							rawData.append("<district>").append(areaDistristPR.getDistrict());
							rawData.append("</district>");
						}

						if (areaGroupPR != null) {
							rawData.append("<streetBlock>").append(areaGroupPR.getAreaCode());
							rawData.append("</streetBlock>");

						}
						if (areaProvicialPR != null && areaDistristPR != null
								&& areaPrecintPR != null && areaGroupPR != null) {
							rawData.append("<areaCode>").append((areaProvicialPR.getProvince()
                                    + areaDistristPR.getDistrict()
                                    + areaPrecintPR.getPrecinct() + areaGroupPR
                                    .getAreaCode()).replace("null",
                                    ""));
							rawData.append("</areaCode>");
						}
						rawData.append("<streetName>" + StringUtils.escapeHtml(areaFlowPR));
						rawData.append("</streetName>");
						rawData.append("<home>" + StringUtils.escapeHtml(areaHomeNumberPR));
						rawData.append("</home>");
						if (!CommonActivity.isNullOrEmpty(txtdcgtxm.getText()
								.toString())) {
							if (!txtdcgtxm
									.getText()
									.toString()
									.trim()
									.equalsIgnoreCase(
											getString(R.string.sellect_address))) {
								rawData.append("<address>"
										+ StringUtils.escapeHtml(txtdcgtxm.getText().toString()));
								rawData.append("</address>");
							} else {
								rawData.append("<address>" + "");
								rawData.append("</address>");
							}
						}

					}

					rawData.append("</representativeCust>");

					rawData.append("</contract>");
				}

				// subRequest
				rawData.append("<subRequest>");

				// bo sung thong tin dat coc
				// menu.deposit

				SharedPreferences preferences = FragmentConnectionInfoSetting.this
						.getSharedPreferences(Define.PRE_NAME,
								Activity.MODE_PRIVATE);
				String name = preferences.getString(Define.KEY_MENU_NAME, "");
				if (name.contains(";menu.deposit;")) {
					if (!CommonActivity.isNullOrEmpty(hthmBeans)) {
						if (!CommonActivity.isNullOrEmpty(hthmBeans
								.getDescription())
								&& hthmBeans.getDescription().contains(
										"$datcoc$")) {
							ReasonPledgeDTO item = (ReasonPledgeDTO) spinner_deposit
									.getSelectedItem();
							if (!CommonActivity.isNullOrEmpty(item)) {
								rawData.append("<deposit>").append(item.getNumMoney());
								rawData.append("</deposit>");
								rawData.append("<nummonthDeposit>").append(item.getNumMonth());
								rawData.append("</nummonthDeposit>");
								rawData.append("<isPermissionDeposit>" + true);
								rawData.append("</isPermissionDeposit>");
							}
						}
					}
				}
				// thong tin trai nghiem
				// thong tin thong bao cuoc
				Spin spinKMTN = (Spin) spinner_kmtrainghiem.getSelectedItem();
				if (spinKMTN != null) {
					rawData.append("<promotionCodeUnit>").append(spinKMTN.getId());
					rawData.append("</promotionCodeUnit>");
				}
				rawData.append("<prepaidCode>").append(prepaidCode);
				rawData.append("</prepaidCode>");
				if ("F".equals(serviceType)
						|| "N".equals(serviceType)
						|| "I".equals(serviceType)
						|| ("U".equals(serviceType)
								&& FragmentInfoExamine.groupProductId
										.equals("23") && techology.equals("4"))) {
					if (!CommonActivity
							.isNullOrEmpty(FragmentInfoExamine.mohinh)) {
						rawData.append("<deployModelOntSfu>").append(FragmentInfoExamine.mohinh);
						rawData.append("</deployModelOntSfu>");
					}
				} else {
					rawData.append("<deployModelOntSfu>" + "");
					rawData.append("</deployModelOntSfu>");
				}

				// thue bao chu
				if (parentSubId != null && !parentSubId.equals("")) {
					rawData.append("<parentSubId>").append(parentSubId);
					rawData.append("</parentSubId>");

				}

				// accountPPPOE
				// passwordPPPOE

				rawData.append("<accountPPPOE>").append(editisdnoraccPPPOE.getText().toString().trim()).append("</accountPPPOE>");
				rawData.append("<passwordPPPOE>").append(editmatkhauPPPOE.getText().toString().trim()).append("</passwordPPPOE>");
				rawData.append("<account>").append(subRequest.getAccount()).append("</account>");

				rawData.append("<lineType>").append(subRequest.getLineType()).append("</lineType>");
				rawData.append("<password>").append(subRequest.getPassword()).append("</password>");
				rawData.append("<account>").append(subRequest.getAccount()).append("</account>");
				rawData.append("<subgroup>").append(subRequest.getSubgroup()).append("</subgroup>");
				rawData.append("<subType>").append(subRequest.getSubType()).append("</subType>");

				if (subRequest.getPromotionCode() != null
						&& !subRequest.getPromotionCode().isEmpty()) {
					rawData.append("<promotionCode>").append(subRequest.getPromotionCode()).append("</promotionCode>");
				} else {
					rawData.append("<promotionCode>" + "" + "</promotionCode>");
				}

				rawData.append("<regType>").append(subRequest.getRegType()).append("</regType>");
				rawData.append("<ipStatic>").append(subRequest.getIpStatic()).append("</ipStatic>");
				rawData.append("<userUsing>").append(StringUtils.escapeHtml(subRequest.getUserUsing())).append("</userUsing>");
				rawData.append("<serviceType>").append(subRequest.getServiceType()).append("</serviceType>");
				if (subRequest.getProductCode() != null
						&& !subRequest.getProductCode().isEmpty()) {
					rawData.append("<productCode>").append(subRequest.getProductCode()).append("</productCode>");
				} else {
					rawData.append("<productCode>" + "" + "</productCode>");
				}

				rawData.append("<offerId>").append(subRequest.getOfferId()).append("</offerId>");
				rawData.append("<technology>").append(subRequest.getTechnology()).append("</technology>");
				rawData.append("<notificationLevel>").append(subRequest.getNotificationLevel()).append("</notificationLevel>");
				rawData.append("<telFax>").append(subRequest.getTelFax()).append("</telFax>");
				rawData.append("<telMobile>").append(subRequest.getTelMobile()).append("</telMobile>");
				rawData.append("<limitDate>").append(subRequest.getLimitDate()).append("</limitDate>");

				rawData.append("</subRequest>");

				// subReqDeployment
				rawData.append("<subReqDeployment>");

				rawData.append("<cableBoxType>").append(subReqDeployment.getConectorType()).append("</cableBoxType>");
				rawData.append("<radiusCust>").append(subReqDeployment.getRadiusCust()).append("</radiusCust>");
				rawData.append("<cableBoxId>").append(subReqDeployment.getCableBoxId()).append("</cableBoxId>");
				rawData.append("<cableBoxCode>").append(subReqDeployment.getCableBoxCode()).append("</cableBoxCode>");
				rawData.append("<stationCode>").append(subReqDeployment.getStationCode()).append("</stationCode>");
				if (subReqDeployment.getTeamCode() != null
						&& !subReqDeployment.getTeamCode().isEmpty()) {
					rawData.append("<teamCode>").append(subReqDeployment.getTeamCode()).append("</teamCode>");
				}

				// TODO BO COMMENT VENDOR NGAY 18122015
				if (subReqDeployment.getVendor() != null
						&& !subReqDeployment.getVendor().isEmpty()) {
					rawData.append("<vendor>").append(subReqDeployment.getVendor());
					rawData.append("</vendor>");
				}

				rawData.append("</subReqDeployment>");

				// SubStockModelRelReq add listproduct
				if (arrSubStockModelRelReq != null
						&& arrSubStockModelRelReq.size() > 0) {
					for (SubStockModelRelReq subStockModelRelReq : arrSubStockModelRelReq) {
						HashMap<String, String> rawDataItem = new HashMap<>();
						rawDataItem.put("reclaimAmount",
								subStockModelRelReq.getReclaimAmount());
						rawDataItem.put("reclaimProCode",
								subStockModelRelReq.getReclaimProCode());
						rawDataItem.put("reclaimPayMethod",
								subStockModelRelReq.getReclaimPayMethod());
						rawDataItem.put("reclaimCommitmentCode",
								subStockModelRelReq.getReclaimCommitmentCode());
						rawDataItem.put("stockModelId",
								subStockModelRelReq.getStockModelId());
						rawDataItem.put("saleServiceId",
								subStockModelRelReq.getSaleServiceId());
						rawDataItem.put("stockTypeId",
								subStockModelRelReq.getStockTypeId());
						rawDataItem.put("reclaimDatetime",
								subStockModelRelReq.getReclaimDatetime());
						param.put("lstSubStockModelRelReq",
								input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));
					}

				}

				rawData.append("</smartphoneInputBO>");
				rawData.append("</ws:signContractSmartphone>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_signContractSmartphone");
				Log.i("Responseeeeeeeeee0f0a", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("original123", original);

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					String subReqId = parse.getValue(e2, "subRegId");
					Log.d("subRegId", subReqId);
					infoConnectionBeans.setIdRequest(subReqId);

					String contractNo = parse.getValue(e2, "contractNo");
					Log.d("contractNo", contractNo);
					infoConnectionBeans.setIdContractNo(contractNo);
					String isdn = parse.getValue(e2, "isdn");
					Log.d("isdn", isdn);
					String account = parse.getValue(e2, "account");
					Log.d("account", account);
					if (account != null && !account.isEmpty()) {
						infoConnectionBeans.setIsdnOrAccount(account);
					} else {
						infoConnectionBeans.setIsdnOrAccount(isdn);
					}
					String productCode = parse.getValue(e2, "productCode");
					Log.d("productCode", productCode);
					infoConnectionBeans.setPakageCharge(productCode);
					String serviceType = parse.getValue(e2, "serviceType");
					if (serviceType != null && !serviceType.isEmpty()) {
						try {
							GetServiceDal getServiceDal = new GetServiceDal(
									FragmentConnectionInfoSetting.this);
							getServiceDal.open();
							String serviceName = getServiceDal
									.getDescription(serviceType);
							Log.d("serviceName", serviceName);
							infoConnectionBeans.setServiceName(serviceName);
							getServiceDal.close();
						} catch (Exception e) {
							Log.e("getlistRequest", e.toString(), e);
						}
					}
				}
			} catch (Exception e) {
				Log.e("Exception", e.toString(), e);
			}

		}

	}

	// get contract
	private class GetConTractAsyn extends
			AsyncTask<String, Void, ArrayList<ConTractBean>> {
		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
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
			return sendRequestGetLisContract(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<ConTractBean> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(
						FragmentConnectionInfoSetting.this, TYPE_HTTTHD);
				asyntaskGetListAllCommon.execute();

				if (custommer.getCustomerAttribute() != null
						&& !CommonActivity.isNullOrEmpty(custommer
								.getCustomerAttribute().getIdNo())) {
					GetListCusGroupAsyn getListCusGroupAsyn = new GetListCusGroupAsyn(
							FragmentConnectionInfoSetting.this);
					getListCusGroupAsyn.execute();
				}

				if (arrTractBeans != null && arrTractBeans.size() > 0) {
					arrTractBeans = new ArrayList<>();
				}

				if (result.size() > 0) {
					arrTractBeans = result;
					if (!CommonActivity.isNullOrEmpty(parentSubId)
							|| !CommonActivity.isNullOrEmpty(mainSubId)) {

					} else {
						initContract();
					}

					showDialogSelectContract(arrTractBeans);
				} else {

					if (!CommonActivity.isNullOrEmpty(parentSubId)
							|| !CommonActivity.isNullOrEmpty(mainSubId)) {
						CommonActivity.createAlertDialog(
								FragmentConnectionInfoSetting.this,
								getString(R.string.notcontract),
								getString(R.string.app_name),
								onClickListenerBack).show();

					} else {
						initContract();
						showDialogSelectContract(arrTractBeans);
					}

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<ConTractBean> sendRequestGetLisContract(String cusId,
				String idNo, String busPermitNo) {
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

				if (!CommonActivity.isNullOrEmpty(cusId)) {
					rawData.append("<custId>").append(cusId);
					rawData.append("</custId>");
				}
				if (!CommonActivity.isNullOrEmpty(idNo)) {
					rawData.append("<idNo>").append(idNo);
					rawData.append("</idNo>");
				}
				if (!CommonActivity.isNullOrEmpty(busPermitNo)) {
					rawData.append("<busPermitNo>").append(busPermitNo);
					rawData.append("</busPermitNo>");
				}

				if (!CommonActivity.isNullOrEmpty(parentSubId)) {
					rawData.append("<subId>").append(parentSubId);
					rawData.append("</subId>");
				} else {
					if (!CommonActivity.isNullOrEmpty(mainSubId)) {
						rawData.append("<subId>").append(mainSubId);
						rawData.append("</subId>");
					} else {
						rawData.append("<subId>" + "");
						rawData.append("</subId>");
					}
				}

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:findContractOld>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("MBCCS", "Send evelop findContractOld " + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_findContractOld");
				Log.i("Responseeeeeeeeee", response);
				Log.d("Log", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("MBCCS", "Responseeeeeeeeee original findContractOld" + original);
				Log.d("Log", original);
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

	// ========= init spinner Contract
	private void initContract() {
		ConTractBean conTractBean1 = new ConTractBean();
		conTractBean1.setContractNo(getString(R.string.contractNew));
		conTractBean1.setContractId("");
		arrTractBeans.add(0, conTractBean1);
		// if (arrTractBeans != null && arrTractBeans.size() > 0) {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// FragmentConnectionInfoSetting.this,
		// android.R.layout.simple_dropdown_item_1line,
		// android.R.id.text1);
		// for (ConTractBean conTractBean : arrTractBeans) {
		// adapter.add(conTractBean.getContractNo());
		// }
		// spinner_hopdong.setAdapter(adapter);
		// }

	}

	private final OnClickListener onClickListenerBack = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			onBackPressed();

		}
	};
	// Ham lay danh sach hinh thuc thanh toan
	private boolean isFirst = false;

	private class AsyntaskGetListAllCommon extends
			AsyncTask<Void, Void, ArrayList<Spin>> {
		// ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		int type = 0;

		public AsyntaskGetListAllCommon(Context context, int type) {
			this.context = context;
			this.type = type;
			switch (type) {
			case TYPE_HTTTHD:
				findViewById(R.id.prbhttthd).setVisibility(View.VISIBLE);
				findViewById(R.id.btnhttthd).setVisibility(View.GONE);
				break;
			case TYPE_CK_CUOC_HD:
				findViewById(R.id.prbchukicuoc).setVisibility(View.VISIBLE);
				findViewById(R.id.btnchukicuoc).setVisibility(View.GONE);
				break;
			case TYPE_HTTBC_HD:
				findViewById(R.id.prbhttbc).setVisibility(View.VISIBLE);
				findViewById(R.id.btnhttbc).setVisibility(View.GONE);
				break;
			case TYPE_INCT_HD:
				findViewById(R.id.prbinchitiet).setVisibility(View.VISIBLE);
				findViewById(R.id.btninchitiet).setVisibility(View.GONE);
				break;
			case TYPE_LOAI_HD:
				findViewById(R.id.prbloaihopdong).setVisibility(View.VISIBLE);
				findViewById(R.id.btnloaihopdong).setVisibility(View.GONE);
				break;
			case TYPE_TTBS_HD:
				findViewById(R.id.prbttbosung).setVisibility(View.VISIBLE);
				findViewById(R.id.btnttbosung).setVisibility(View.GONE);
				break;

			default:
				break;
			}
			// this.progress = new ProgressDialog(this.context);
			// // check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.waitting));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }

		}

		@Override
		protected ArrayList<Spin> doInBackground(Void... arg0) {
			// private int TYPE_HTTHHD = 1;
			// private int TYPE_LOAI_HD = 2;
			// private int TYPE_CK_CUOC_HD = 3;
			// private int TYPE_INCT_HD = 4;
			// private int TYPE_HTTBC_HD = 5;
			// private int TYPE_TTBS_HD = 6;
			if (type == TYPE_HTTTHD) {
				return getListPayMethode();
			} else if (type == TYPE_LOAI_HD) {
				return getListContractType();

			} else

			if (type == TYPE_CK_CUOC_HD) {
				return getListCurrBillCycleAll();
			} else

			if (type == TYPE_INCT_HD) {
				return getListPrintMethode();
			} else

			if (type == TYPE_HTTBC_HD) {
				return getListNoticeMethode();
			} else

			if (type == TYPE_TTBS_HD) {
				return getContractPrintList();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			// progress.dismiss();

			if (errorCode.equalsIgnoreCase("0")) {
				isFirst = true;
				if (type == TYPE_HTTTHD) {
					findViewById(R.id.btnhttthd).setVisibility(View.VISIBLE);
					findViewById(R.id.prbhttthd).setVisibility(View.GONE);

					arrHTTTHD = new ArrayList<>();
					arrHTTTHD.addAll(result);
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							result, spinner_httthd);
					if (!CommonActivity.isNullOrEmpty(conTractBean
							.getContractId())) {
						findViewById(R.id.btnhttthd).setVisibility(View.GONE);
						if (conTractBean != null
								&& !CommonActivity.isNullOrEmpty(conTractBean
										.getPayMethodCode())) {
							if (arrHTTTHD != null && !arrHTTTHD.isEmpty()) {
								for (Spin payMethod : arrHTTTHD) {
									if (conTractBean
											.getPayMethodCode()
											.equalsIgnoreCase(payMethod.getId())) {
										spinner_httthd.setSelection(arrHTTTHD
												.indexOf(payMethod));
										break;
									}
								}
							}
						}
					}
					if (!isRefreshTYPE_HTTTHD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon2 = new AsyntaskGetListAllCommon(
								FragmentConnectionInfoSetting.this,
								TYPE_LOAI_HD);
						asyntaskGetListAllCommon2.execute();
					}

				} else if (type == TYPE_LOAI_HD) {
					findViewById(R.id.btnloaihopdong).setVisibility(
							View.VISIBLE);
					findViewById(R.id.prbloaihopdong).setVisibility(View.GONE);
					arrLOAIHD = new ArrayList<>();
					arrLOAIHD.addAll(result);
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							result, spinner_loaihopdong);
					if (conTractBean != null
							&& !CommonActivity.isNullOrEmpty(conTractBean
									.getContractType())) {
						findViewById(R.id.btnloaihopdong).setVisibility(
								View.GONE);
						if (arrLOAIHD != null && !arrLOAIHD.isEmpty()) {
							for (Spin loaiHD : arrLOAIHD) {
								if (conTractBean.getContractType()
										.equalsIgnoreCase(loaiHD.getId())) {
									spinner_loaihopdong.setSelection(arrLOAIHD
											.indexOf(loaiHD));
									break;
								}
							}
						}
					}

					if (!isRefreshTYPE_LOAI_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon3 = new AsyntaskGetListAllCommon(
								FragmentConnectionInfoSetting.this,
								TYPE_CK_CUOC_HD);
						asyntaskGetListAllCommon3.execute();
					}
				} else if (type == TYPE_CK_CUOC_HD) {
					findViewById(R.id.btnchukicuoc).setVisibility(View.VISIBLE);
					findViewById(R.id.prbchukicuoc).setVisibility(View.GONE);
					arrCKC = new ArrayList<>();
					arrCKC.addAll(result);
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							result, spinner_chukicuoc);

					if (conTractBean != null
							&& !CommonActivity.isNullOrEmpty(conTractBean
									.getBillCycleFrom())) {
						findViewById(R.id.btnchukicuoc)
								.setVisibility(View.GONE);
						if (arrCKC != null && !arrCKC.isEmpty()) {
							for (Spin ckc : arrCKC) {
								if (conTractBean.getBillCycleFrom()
										.equalsIgnoreCase(ckc.getId())) {
									spinner_chukicuoc.setSelection(arrCKC
											.indexOf(ckc));
									break;
								}
							}
						}
					}
					if (!isRefreshTYPE_CK_CUOC_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon4 = new AsyntaskGetListAllCommon(
								FragmentConnectionInfoSetting.this,
								TYPE_INCT_HD);
						asyntaskGetListAllCommon4.execute();
					}
				} else if (type == TYPE_INCT_HD) {
					findViewById(R.id.btninchitiet).setVisibility(View.VISIBLE);
					findViewById(R.id.prbinchitiet).setVisibility(View.GONE);
					arrINCT = new ArrayList<>();
					arrINCT.addAll(result);
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							result, spinner_inchitiet);

					if (conTractBean != null
							&& !CommonActivity.isNullOrEmpty(conTractBean
									.getPrintMethodCode())) {
						findViewById(R.id.btninchitiet)
								.setVisibility(View.GONE);
						if (arrINCT != null && !arrINCT.isEmpty()) {
							for (Spin inCT : arrINCT) {
								if (conTractBean.getPrintMethodCode()
										.equalsIgnoreCase(inCT.getId())) {
									spinner_inchitiet.setSelection(arrINCT
											.indexOf(inCT));
									break;
								}
							}
						}
					}

                    Boolean isRefreshTYPE_INCT_HD = false;
                    if (!isRefreshTYPE_INCT_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon5 = new AsyntaskGetListAllCommon(
								FragmentConnectionInfoSetting.this,
								TYPE_HTTBC_HD);
						asyntaskGetListAllCommon5.execute();
					}
				} else if (type == TYPE_HTTBC_HD) {
					findViewById(R.id.btnhttbc).setVisibility(View.VISIBLE);
					findViewById(R.id.prbhttbc).setVisibility(View.GONE);
					arrHTTBC = new ArrayList<>();
					arrHTTBC.addAll(result);
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							result, spinner_httbc);
					//
					if (conTractBean != null
							&& !CommonActivity.isNullOrEmpty(conTractBean
									.getNoticeCharge())) {
						findViewById(R.id.btnhttbc).setVisibility(View.GONE);
						for (int i = 0; i < arrTTBSHD.size(); i++) {
							Spin spin = arrHTTBC.get(i);
							if (spin.getId().equalsIgnoreCase(
									conTractBean.getNoticeCharge())) {
								spinner_ttbosung.setSelection(i);
								break;
							}
						}
					}

					if (!isRefreshTYPE_HTTBC_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon6 = new AsyntaskGetListAllCommon(
								FragmentConnectionInfoSetting.this,
								TYPE_TTBS_HD);
						asyntaskGetListAllCommon6.execute();
					}
				} else if (type == TYPE_TTBS_HD) {

					findViewById(R.id.btnttbosung).setVisibility(View.VISIBLE);
					findViewById(R.id.prbttbosung).setVisibility(View.GONE);
					arrTTBSHD = new ArrayList<>();
					Spin spin = new Spin();
					spin.setId("");
					spin.setValue(getString(R.string.chonttinbsung));
					result.add(0, spin);
					arrTTBSHD.addAll(result);
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							result, spinner_ttbosung);
					if (conTractBean != null
							&& !CommonActivity.isNullOrEmpty(conTractBean
									.getPrintMethodCode())) {
						findViewById(R.id.btnttbosung).setVisibility(View.GONE);
						for (int i = 0; i < arrTTBSHD.size(); i++) {
							Spin tmp = arrHTTBC.get(i);
							if (tmp.getId().equalsIgnoreCase(
									conTractBean.getPrintMethodCode())) {
								spinner_ttbosung.setSelection(i);
								break;
							}
						}
					}

					if (!isRefreshTYPE_TTBS_HD) {
						GetListSubGroupAsyn getListSubGroupAsyn = new GetListSubGroupAsyn(
								FragmentConnectionInfoSetting.this);
						getListSubGroupAsyn.execute();
					}
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					result = new ArrayList<>();

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					result = new ArrayList<>();
					if (type == TYPE_HTTTHD) {
						arrHTTTHD = new ArrayList<>();
						arrHTTTHD.addAll(result);
						Utils.setDataSpinner(
								FragmentConnectionInfoSetting.this, result,
								spinner_httthd);

					}
					if (type == TYPE_LOAI_HD) {
						arrLOAIHD = new ArrayList<>();
						arrLOAIHD.addAll(result);
						Utils.setDataSpinner(
								FragmentConnectionInfoSetting.this, result,
								spinner_loaihopdong);

					}

					if (type == TYPE_CK_CUOC_HD) {
						arrCKC = new ArrayList<>();
						arrCKC.addAll(result);
						Utils.setDataSpinner(
								FragmentConnectionInfoSetting.this, result,
								spinner_chukicuoc);
					}
					if (type == TYPE_INCT_HD) {
						arrINCT = new ArrayList<>();
						arrINCT.addAll(result);
						Utils.setDataSpinner(
								FragmentConnectionInfoSetting.this, result,
								spinner_inchitiet);
					}
					if (type == TYPE_HTTBC_HD) {
						arrHTTBC = new ArrayList<>();
						arrHTTBC.addAll(result);
						Utils.setDataSpinner(
								FragmentConnectionInfoSetting.this, result,
								spinner_httbc);
					}

					if (type == TYPE_TTBS_HD) {
						arrTTBSHD = new ArrayList<>();
						arrTTBSHD.addAll(result);
						Utils.setDataSpinner(
								FragmentConnectionInfoSetting.this, result,
								spinner_ttbosung);
					}
					description = type + description;
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		// lay danh sach hinh thuc thanh toan hop dong
		private ArrayList<Spin> getListPayMethode() {
			ArrayList<Spin> lstpaymethod = new ArrayList<>();
			String original = "";
			try {
				lstpaymethod = new CacheDatabaseManager(context)
						.getListInCache(TYPE_HTTTHD);
				if (lstpaymethod != null && !lstpaymethod.isEmpty()) {
					errorCode = "0";
					return lstpaymethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListPayMethode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListPayMethode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListPayMethode>");
				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListPayMethode");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstpaymethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheList(TYPE_HTTTHD,
						lstpaymethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstpaymethod;
		}

		// lay danh sach in chi tiet cuoc
		private ArrayList<Spin> getListPrintMethode() {
			ArrayList<Spin> lstprintmethod = new ArrayList<>();
			String original = "";
			try {
				lstprintmethod = new CacheDatabaseManager(context)
						.getListInCache(TYPE_INCT_HD);
				if (lstprintmethod != null && !lstprintmethod.isEmpty()) {
					errorCode = "0";
					return lstprintmethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListPrintMethode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListPrintMethode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListPrintMethode>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListPrintMethode");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstprintmethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheList(TYPE_INCT_HD,
						lstprintmethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstprintmethod;
		}

		// lay danh sach hinh thuc thong bao cuoc
		private ArrayList<Spin> getListNoticeMethode() {
			ArrayList<Spin> lstNoticeMethod = new ArrayList<>();
			String original = "";
			try {
				lstNoticeMethod = new CacheDatabaseManager(context)
						.getListInCache(TYPE_HTTBC_HD);
				if (lstNoticeMethod != null && !lstNoticeMethod.isEmpty()) {
					errorCode = "0";
					return lstNoticeMethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListNoticeMethode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListNoticeMethode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListNoticeMethode>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListNoticeMethode");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstNoticeMethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheList(
						TYPE_HTTBC_HD, lstNoticeMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstNoticeMethod;
		}

		// lay danh sach chu ky cuoc
		private ArrayList<Spin> getListCurrBillCycleAll() {
			ArrayList<Spin> lstCurrBillCycle = new ArrayList<>();
			String original = "";
			try {
				lstCurrBillCycle = new CacheDatabaseManager(context)
						.getListInCache(TYPE_CK_CUOC_HD);
				if (lstCurrBillCycle != null && !lstCurrBillCycle.isEmpty()) {
					errorCode = "0";
					return lstCurrBillCycle;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListCurrBillCycleAll");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListCurrBillCycleAll>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListCurrBillCycleAll>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListCurrBillCycleAll");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstCurrBillCycle = parserBillCycle(original);
				new CacheDatabaseManager(context).insertCacheList(
						TYPE_CK_CUOC_HD, lstCurrBillCycle);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstCurrBillCycle;
		}

		// lay danh sach loai hop dong
		private ArrayList<Spin> getListContractType() {
			ArrayList<Spin> lstContractType = new ArrayList<>();
			String original = "";
			try {
				lstContractType = new CacheDatabaseManager(context)
						.getListInCache(TYPE_LOAI_HD);
				if (lstContractType != null && !lstContractType.isEmpty()) {
					errorCode = "0";
					return lstContractType;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListContractType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListContractType>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListContractType>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListContractType");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstContractType = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheList(TYPE_LOAI_HD,
						lstContractType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstContractType;
		}

		// lay danh sach thong tin in bo sung
		private ArrayList<Spin> getContractPrintList() {
			ArrayList<Spin> lstprintmethod = new ArrayList<>();
			String original = "";
			try {
				lstprintmethod = new CacheDatabaseManager(context)
						.getListInCache(TYPE_TTBS_HD);
				if (lstprintmethod != null && !lstprintmethod.isEmpty()) {
					errorCode = "0";
					return lstprintmethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getContractPrintList");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getContractPrintList>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getContractPrintList>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getContractPrintList");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstprintmethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheList(TYPE_TTBS_HD,
						lstprintmethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstprintmethod;
		}

		public ArrayList<Spin> parserListGroup(String original) {
			ArrayList<Spin> lstReason = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			NodeList nodeAppram = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstAppDomains");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "name"));

					nodeAppram = e1.getElementsByTagName("id");
					for (int k = 0; k < nodeAppram.getLength(); k++) {
						Element e3 = (Element) nodeAppram.item(k);

						spin.setId(parse.getValue(e3, "code"));
						if (spin != null && spin.getId() != null) {
							Log.d("LOG", "value: " + spin.getId());
						}
					}

					lstReason.add(spin);
				}
			}

			return lstReason;
		}

		public ArrayList<Spin> parserBillCycle(String original) {
			ArrayList<Spin> lstReason = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstCurrBillCycles");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "billCycleFrom"));

					spin.setId(parse.getValue(e1, "billCycleFrom"));

					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	// =========== webservice danh sach PSTN ==================
	public class GetListPstnAsyn extends
			AsyncTask<String, Void, ArrayList<String>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListPstnAsyn(Context context) {
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
			return getListPstn(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrListIsdnPstn = result;
					final Dialog dialog = new Dialog(
							FragmentConnectionInfoSetting.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.connection_layout_lst_pstn);
					ListView lstpstn = (ListView) dialog
							.findViewById(R.id.lstpstn);

					GetPstnAdapter adapGetPstnAdapter = new GetPstnAdapter(
							arrListIsdnPstn, FragmentConnectionInfoSetting.this);
					lstpstn.setAdapter(adapGetPstnAdapter);
					lstpstn.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							editisdnoracc.setText(arrListIsdnPstn.get(arg2));
							dialog.dismiss();
						}
					});

					Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
					btnhuy.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
							editisdnoracc.setText("");

						}
					});
					dialog.show();

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.checknotisdn),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// input : ma goi cuoc va ma dia ban
		private ArrayList<String> getListPstn(String regType, String offerId,
				String stationId) {
			ArrayList<String> lisPstn = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListIsdnPstn");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListIsdnPstn>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("<regType>").append(regType);
				rawData.append("</regType>");

				rawData.append("<stationId>").append(stationId);
				rawData.append("</stationId>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListIsdnPstn>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListIsdnPstn");
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
					nodechild = doc.getElementsByTagName("lstIsdnPstn");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						String isdnPstn = XmlDomParse.getCharacterDataFromElement(e1);
						Log.d("isdnPstn", isdnPstn);
						lisPstn.add(isdnPstn);
					}
				}

			} catch (Exception e) {
				Log.d("getListPstn", e.toString());
			}

			return lisPstn;
		}

	}

	// =========== webservice danh sach PSTN ==================
	public class GetListNGNAsyn extends
			AsyncTask<String, Void, ArrayList<String>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListNGNAsyn(Context context) {
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
			return getListIsdnNGN(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrListIsdnNGN = result;
					final Dialog dialog = new Dialog(
							FragmentConnectionInfoSetting.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.connection_layout_lst_pstn);
					ListView lstpstn = (ListView) dialog
							.findViewById(R.id.lstpstn);
					TextView txtinfo = (TextView) dialog
							.findViewById(R.id.txtinfo);
					txtinfo.setText(getResources()
							.getString(R.string.dsisdnNGN));
					GetPstnAdapter adapGetPstnAdapter = new GetPstnAdapter(
							arrListIsdnNGN, FragmentConnectionInfoSetting.this);
					lstpstn.setAdapter(adapGetPstnAdapter);
					lstpstn.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							editisdnoracc.setText(arrListIsdnNGN.get(arg2));
							dialog.dismiss();
						}
					});

					Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
					btnhuy.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
							editisdnoracc.setText("");

						}
					});
					dialog.show();

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.checknotisdn),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// input : ma goi cuoc va ma dia ban
		private ArrayList<String> getListIsdnNGN(String regType,
				String offerId, String province) {
			ArrayList<String> listIsdnNGN = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListIsdnNgnByProvince");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListIsdnNgnByProvince>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("<regType>").append(regType);
				rawData.append("</regType>");

				rawData.append("<province>").append(province);
				rawData.append("</province>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListIsdnNgnByProvince>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListIsdnNgnByProvince");
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
					nodechild = doc.getElementsByTagName("lstStockIsdnPstn");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						String isdnNGNG = parse.getValue(e1, "isdn");
						Log.d("isdnNGNG", isdnNGNG);
						listIsdnNGN.add(isdnNGNG);
					}
				}

			} catch (Exception e) {
				Log.e("getListPstn", e.toString(), e);
			}

			return listIsdnNGN;
		}

	}

	// ====ws check ip theo goi cuoc
	public class checkIpAsyn extends AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public checkIpAsyn(Context context) {
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
		protected String doInBackground(String... arg0) {
			return checkIp(arg0[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result.equals("0")) {
				checkIp = description;
				if ("1".equals(description)) {
					lnIptinh.setVisibility(View.VISIBLE);
					
					if (!CommonActivity.isNullOrEmpty(offerId)) {
						String locationLD = "";
						if(!CommonActivity.isNullOrEmpty(provinceMain)){
							locationLD = provinceMain;
						}
						if(!CommonActivity.isNullOrEmpty(districtMain)){
							locationLD = locationLD+districtMain;
						}
						if(!CommonActivity.isNullOrEmpty(precinctMain)){
							locationLD = locationLD + precinctMain;
						}
						if(!CommonActivity.isNullOrEmpty(locationLD)){
							new GetListIpAsyn(FragmentConnectionInfoSetting.this)
							.execute(offerId, locationLD);
						}else{
							new GetListIpAsyn(FragmentConnectionInfoSetting.this)
							.execute(offerId, locationCode);
						}
					}
				} else {
					if ("0".equals(description)) {
						lnIptinh.setVisibility(View.GONE);
					}
				}

			} else {
				checkIp = "";
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		public String checkIp(String offerId) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_isSupportStaticIp");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:isSupportStaticIp>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:isSupportStaticIp>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_isSupportStaticIp");
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

				Log.e("checkIp", e.toString(), e);
			}

			return errorCode;
		}

	}

	// ===== ws danh sach hang hoa =============
	public class GetListProductAsyn extends
			AsyncTask<String, Void, ArrayList<ObjThongTinHH>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListProductAsyn(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				// this.progress.show();
			}
		}

		@Override
		protected ArrayList<ObjThongTinHH> doInBackground(String... arg0) {
			return getListProduct(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<ObjThongTinHH> result) {
			this.progress.dismiss();

			if (errorCode.equals("0")) {
				Log.d("TAG 69", "mArrayListThongTinHH.size() :"
						+ mArrayListThongTinHH.size());
				if (mArrayListThongTinHH.size() > 0) {
					lvproduct.setVisibility(View.VISIBLE);
					setListView(mArrayListThongTinHH);

				} else {
					lvproduct.setVisibility(View.GONE);
					// if(mArrayListThongTinHH != null &&
					// mArrayListThongTinHH.size() > 0 && adapter!= null){
					// mArrayListThongTinHH.clear();
					// adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
					// FragmentConnectionInfoSetting.this,
					// FragmentConnectionInfoSetting.this);
					// lvproduct.setAdapter(adapter);
					// adapter.notifyDataSetChanged();
					// }
					// CommonActivity.createAlertDialog(FragmentConnectionInfoSetting.this,
					// getResources().getString(R.string.checknothanghoa),
					// getResources().getString(R.string.app_name)).show();
				}

			} else {
				txththm.setText(getString(R.string.chonhthm));
				regType = "";
				if (arrPromotionTypeBeans != null
						&& !arrPromotionTypeBeans.isEmpty()) {
					arrPromotionTypeBeans = new ArrayList<>();
				}
				initInitListPromotion();
				maKM = "-1";
				txtchonkm.setText(getString(R.string.chonctkm1));
				
				txththm.setText(getString(R.string.chonhthm));
				regType = "";
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}

			}

		}

		private ArrayList<ObjThongTinHH> getListProduct(String regType,
				String productCode, String serviceType) {
			ArrayList<ObjThongTinHH> arrayListThongTinHH = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStockType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockType>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");

				rawData.append("<regType>").append(regType);
				rawData.append("</regType>");

				rawData.append("<productCode>").append(productCode);
				rawData.append("</productCode>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListStockType>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListStockType");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.i("original 69696", "original :" + original);

				// ============parse xml in android=========
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechildlstApSaleModelBeanBO = null;
				NodeList nodechildlstAPSaleModelBeanBO = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					// // parent
					nodechildlstApSaleModelBeanBO = e2
							.getElementsByTagName("lstApSaleModelBeanBO");

					String saleServiceCode = parse.getValue(e2,
							"saleServiceCode");
					Log.d("saleServiceCode", saleServiceCode);

					String saleServicesModelId = "";
					String stockTypeId = "";
					String stockTypeName = "";

					for (int j = 0; j < nodechildlstApSaleModelBeanBO
							.getLength(); j++) {

						ArrayList<ObjAPStockModelBeanBO> arrayAPStockModelBeanBO = new ArrayList<>();

						Log.d("MBCCS", "nodechildlstApSaleModelBeanBO.length: " + nodechildlstApSaleModelBeanBO.getLength() + "");
						Element e1 = (Element) nodechildlstApSaleModelBeanBO
								.item(j);

						saleServicesModelId = parse.getValue(e1,
								"saleServicesModelId");
						stockTypeId = parse.getValue(e1, "stockTypeId");
						Log.d("stockTypeId", stockTypeId);
						stockTypeName = parse.getValue(e1, "stockTypeName");
						nodechildlstAPSaleModelBeanBO = e1
								.getElementsByTagName("lstAPStockModelBeanBO");

						for (int k = 0; k < nodechildlstAPSaleModelBeanBO
								.getLength(); k++) {
							Element element1 = (Element) nodechildlstAPSaleModelBeanBO
									.item(k);
							String stockTypeIdModelBeanBO = parse.getValue(
									element1, "stockTypeId");
							String stockModelId = parse.getValue(element1,
									"stockModelId");
							String stockModelType = parse.getValue(element1,
									"stockModelType");
							String stockModelName = parse.getValue(element1,
									"stockModelName");
							String quantity = parse.getValue(element1,
									"quantity");
							String saleTransDetailId = parse.getValue(element1,
									"saleTransDetailId");
							String saleServicesDetailId = parse.getValue(
									element1, "saleServicesDetailId");
							String saleTransId = parse.getValue(element1,
									"saleTransId");
							ObjAPStockModelBeanBO objAPStockModelBeanBO = new ObjAPStockModelBeanBO(
									stockTypeIdModelBeanBO, stockModelId,
									stockModelType, stockModelName, quantity,
									saleTransDetailId, saleServicesDetailId,
									saleTransId);
							arrayAPStockModelBeanBO.add(objAPStockModelBeanBO);
						}

						ObjAPStockModelBeanBO objAPStockModelBeanBO1 = new ObjAPStockModelBeanBO(
								"", "", "", getResources().getString(
										R.string.selectproduct), "", "", "", "");
						arrayAPStockModelBeanBO.add(0, objAPStockModelBeanBO1);
						ObjThongTinHH objThongTinHH = new ObjThongTinHH(
								saleServicesModelId, stockTypeId,
								stockTypeName, saleServiceCode,
								arrayAPStockModelBeanBO, false);
						mArrayListThongTinHH.add(objThongTinHH);
					}
				}

			} catch (Exception e) {
				Log.e("getListProduct", e.toString(), e);
			}

			return arrayListThongTinHH;
		}
	}

	private void setListView(ArrayList<ObjThongTinHH> arrayListTTHH) {

		adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
				FragmentConnectionInfoSetting.this,
				FragmentConnectionInfoSetting.this);
		lvproduct.setAdapter(adapter);

	}

	// =========== webservice view detail product =============
	public class ViewDetailProductAsyn extends
			AsyncTask<String, Void, SupplyInforBean> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String stockTypeId = "";

		public ViewDetailProductAsyn(Context context) {
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
		protected SupplyInforBean doInBackground(String... arg0) {
			stockTypeId = arg0[2];
			return parseXmlDetailProduct(arg0[0], arg0[1], stockTypeId);
		}

		@Override
		protected void onPostExecute(SupplyInforBean result) {
			this.progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null && result.getLstSupplyMethod().size() > 0) {
					showDiaLogDeTailProduct(result, stockTypeId);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.notdetailhanghoa),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}

			}

		}

		// =============show dia log detail Product ==================
		private void showDiaLogDeTailProduct(final SupplyInforBean result,
				final String stockTypeId) {

			final Dialog dialog = new Dialog(FragmentConnectionInfoSetting.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.connection_layout_detail_product);
			final LinearLayout lnct = (LinearLayout) dialog
					.findViewById(R.id.lnchuongtrinh);
			final LinearLayout lnsothang = (LinearLayout) dialog
					.findViewById(R.id.lnsothang);
			spinner_htcungcap = (Spinner) dialog
					.findViewById(R.id.spinner_htcungcap);
			spinner_chuongtrinh = (Spinner) dialog
					.findViewById(R.id.spinner_chuongtrinh);
			spinner_sothang = (Spinner) dialog
					.findViewById(R.id.spinner_sothang);
			spinner_httt = (Spinner) dialog.findViewById(R.id.spinner_httt);
			edit_sotien = (EditText) dialog.findViewById(R.id.edit_sotien);
			Button btnchon = (Button) dialog.findViewById(R.id.btnchon);
			Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);

			initSpinnerHTCC(result.getLstSupplyMethod());

			if (arrHtttBeans != null && arrHtttBeans.size() > 0) {
				arrHtttBeans.clear();
			}

			spinner_htcungcap
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							// lay chuong trinh theo htcc
							htcc = result.getLstSupplyMethod().get(arg2)
									.getValue();
							if (htcc.equalsIgnoreCase("CT")) {
								reclaimCommitmentCode = "1";
							}
							if (htcc.equalsIgnoreCase("DC")) {
								reclaimCommitmentCode = "2";
							}
							if (htcc.equalsIgnoreCase("BD")) {
								reclaimCommitmentCode = "3";
							}
							if (htcc.equals("CT") || htcc.equals("DC")) {
								if (arrHtttBeans != null
										&& arrHtttBeans.size() > 0) {
									arrHtttBeans.clear();
								}
								initHTTT();
								initSpinnerHTTT();
								lnct.setVisibility(View.VISIBLE);
								lnsothang.setVisibility(View.VISIBLE);
								edit_sotien.setText("");
								MapProgramFolowHTCC(htcc,
										result.getLstSupplyProgramBean());
								spinner_httt.setEnabled(true);

							} else {

								if (arrHtttBeans != null
										&& arrHtttBeans.size() > 0) {
									arrHtttBeans.clear();
								}
								initHTTTBD();
								initSpinnerHTTTBD();
								lnct.setVisibility(View.GONE);
								lnsothang.setVisibility(View.GONE);
								spinner_httt.setEnabled(false);
								MapMoneyFolowhtcc(htcc,
										result.getLstSupplyProgramBean());
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

			spinner_chuongtrinh
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							if (arg2 > 0) {
								programCode = arrMapProgramBeans.get(arg2)
										.getProgramCode();
								Log.d("programCode", programCode);
								MapMonthFolowProgram(programCode,
										arrMapProgramBeans);
							} else {
								programCode = "";
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});

			spinner_sothang
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							if (arg2 > 0) {
								programMonth = arrMapBaseBeans.get(arg2)
										.getProgramMonth();
								selectMoneyFolowMoth(programMonth,
										arrMapBaseBeans);
							} else {
								programMonth = "";
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});

			spinner_httt
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							httt = arrHtttBeans.get(arg2).getValue();
							Log.d("httt", httt);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

			btnchon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					// chon hang hoa
					if (htcc.equals("CT") || htcc.equals("DC")) {
						if (reclaimCommitmentCode != null
								&& !reclaimCommitmentCode.equals("")) {
							if (programCode != null && !programCode.equals("")) {

								if (programMonth != null
										&& !programMonth.isEmpty()) {
									if (edit_sotien.getText().toString() != null
											&& !edit_sotien.getText()
													.toString().isEmpty()) {
										SubStockModelRelReq subStockModelRelReq = new SubStockModelRelReq(
												saleServiceId, stockModelId,
												"", stockTypeId,
												reclaimCommitmentCode,
												programCode, edit_sotien
														.getText().toString(),
												httt, programMonth);
										mapSubStockModelRelReq.put(stockTypeId,
												subStockModelRelReq);
										dialog.dismiss();
									} else {
										Toast.makeText(
												FragmentConnectionInfoSetting.this,
												getResources().getString(
														R.string.checkmoney),
												Toast.LENGTH_SHORT).show();
									}
								} else {
									Toast.makeText(
											FragmentConnectionInfoSetting.this,
											getResources().getString(
													R.string.checksothang),
											Toast.LENGTH_SHORT).show();
								}

							} else {
								Toast.makeText(
										FragmentConnectionInfoSetting.this,
										getResources().getString(
												R.string.checkct),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(
									FragmentConnectionInfoSetting.this,
									getResources()
											.getString(R.string.checkhtcc),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						if (edit_sotien.getText().toString() != null
								&& !edit_sotien.getText().toString().isEmpty()) {
							// TODO CHECK ADD HANG HOA
							SubStockModelRelReq subStockModelRelReq = new SubStockModelRelReq(
									saleServiceId, stockModelId, "",
									stockTypeId, reclaimCommitmentCode,
									programCode, edit_sotien.getText()
											.toString(), httt, programMonth);

							// lstSubStockModelRelReq.add(subStockModelRelReq);
							mapSubStockModelRelReq.put(stockTypeId,
									subStockModelRelReq);
							dialog.dismiss();
						} else {
							Toast.makeText(
									FragmentConnectionInfoSetting.this,
									getResources().getString(
											R.string.checkmoney),
									Toast.LENGTH_SHORT).show();
						}
					}

				}
			});

			btnhuy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					dialog.dismiss();

				}
			});

			dialog.show();
		}

		// ===== select so tien hinh thuc ban dut
		private void MapMoneyFolowhtcc(String supplyMethod,
				List<SupplyProgramBean> arrSupplyProgramBeans) {

			for (SupplyProgramBean supplyProgramBean : arrSupplyProgramBeans) {
				if (supplyProgramBean.getSupplyMethod().equalsIgnoreCase(
						supplyMethod)) {
					if (supplyProgramBean.getLstSupplyBaseBean().get(0)
							.getPrice() != null
							&& !supplyProgramBean.getLstSupplyBaseBean().get(0)
									.getPrice().isEmpty()) {
						edit_sotien.setText(supplyProgramBean
								.getLstSupplyBaseBean().get(0).getPrice());
						break;
					} else {
						edit_sotien.setText("");
					}

				}
			}

		}

		// ==== select chuong trinh theo htcc ================
		private void MapProgramFolowHTCC(String supplyMethod,
				List<SupplyProgramBean> arrSupplyProgramBeans) {
			ArrayList<SupplyProgramBean> lstProgramBeans = new ArrayList<>();

			for (SupplyProgramBean supplyProgramBean : arrSupplyProgramBeans) {
				if (supplyProgramBean.getSupplyMethod().equalsIgnoreCase(
						supplyMethod)) {
					lstProgramBeans.add(supplyProgramBean);
				}
			}

			arrMapProgramBeans = lstProgramBeans;

			if (arrMapProgramBeans.size() > 0) {
				initSpinnerProgram(arrMapProgramBeans);
			} else {
				CommonActivity.createAlertDialog(
						FragmentConnectionInfoSetting.this,
						getResources().getString(R.string.notProgram),
						getResources().getString(R.string.app_name)).show();
				if (arrMapProgramBeans != null && arrMapProgramBeans.size() > 0) {
					arrMapProgramBeans.clear();
					adapterHTCC.notifyDataSetChanged();
				}
			}

		}

		// ===== select moth folow program =============
		private void MapMonthFolowProgram(String programmeCode,
				List<SupplyProgramBean> arrSupplyProgramBeans) {
			List<SupplyBaseBean> lstSupplyBaseBeans = new ArrayList<>();
			for (SupplyProgramBean supplyProgramBean : arrSupplyProgramBeans) {
				if (supplyProgramBean.getProgramCode() != null) {
					if (supplyProgramBean.getProgramCode().equalsIgnoreCase(
							programmeCode)) {
						lstSupplyBaseBeans = supplyProgramBean
								.getLstSupplyBaseBean();
					}
				}
			}

			arrMapBaseBeans = lstSupplyBaseBeans;

			if (arrMapBaseBeans.size() > 0) {
				initSpinnerMonths(arrMapBaseBeans);
			} else {
				CommonActivity.createAlertDialog(
						FragmentConnectionInfoSetting.this,
						getResources().getString(R.string.notmonth),
						getResources().getString(R.string.app_name)).show();
				if (arrMapBaseBeans != null && arrMapBaseBeans.size() > 0) {
					arrMapBaseBeans.clear();
					adapterMonths.notifyDataSetChanged();
				}
			}
		}

		// ===== select money folow month ================
		private void selectMoneyFolowMoth(String month,
				List<SupplyBaseBean> lstSupplyBaseBeans) {
			String money = "";
			for (SupplyBaseBean supplyBaseBean : lstSupplyBaseBeans) {
				if (supplyBaseBean.getProgramMonth() != null) {
					if (supplyBaseBean.getProgramMonth()
							.equalsIgnoreCase(month)) {
						money = supplyBaseBean.getPrice();
					}
				}
			}
			if (money != null && !money.equals("")) {
				edit_sotien.setText(money);
			} else {
				edit_sotien.setText("");
			}

		}

		// ==== init spinner program =====================
		private void initSpinnerProgram(
				ArrayList<SupplyProgramBean> lstProgramBeans) {
			SupplyProgramBean subProgramBean = new SupplyProgramBean();
			subProgramBean.setProgramName(getResources().getString(
					R.string.selectprogram));
			lstProgramBeans.add(0, subProgramBean);
			if (lstProgramBeans != null && lstProgramBeans.size() > 0) {
                ArrayAdapter<String> adapterProgram = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (SupplyProgramBean supplyProgramBean : lstProgramBeans) {
					adapterProgram.add(supplyProgramBean.getProgramName());
				}
				spinner_chuongtrinh.setAdapter(adapterProgram);
			}
		}

		// ===== init spinner months ============

		private void initSpinnerMonths(List<SupplyBaseBean> lstSupplyBaseBeans) {
			SupplyBaseBean supplyBaseBean = new SupplyBaseBean();
			supplyBaseBean.setProgramMonth(getResources().getString(
					R.string.selectsothang));
			lstSupplyBaseBeans.add(0, supplyBaseBean);
			if (lstSupplyBaseBeans != null && lstSupplyBaseBeans.size() > 0) {
				adapterMonths = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (SupplyBaseBean supplyBaseBean2 : lstSupplyBaseBeans) {
					adapterMonths.add(supplyBaseBean2.getProgramMonth());
				}
				spinner_sothang.setAdapter(adapterMonths);
			}

		}

		// ==== init spinner chon ht cung cap =============
		private void initSpinnerHTCC(List<SupplyMethodBean> arrSupplyMethodBeans) {
			if (arrSupplyMethodBeans != null && arrSupplyMethodBeans.size() > 0) {
				adapterHTCC = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (SupplyMethodBean spMethodBean : arrSupplyMethodBeans) {
					adapterHTCC.add(spMethodBean.getName());
				}
				spinner_htcungcap.setAdapter(adapterHTCC);
			}
		}

		// ===== init chon ht thanh toan
		private void initHTTT() {
			arrHtttBeans.add(new HTTTBean(getResources().getString(
					R.string.trangay), "1"));
			arrHtttBeans.add(new HTTTBean(getResources().getString(
					R.string.tratt), "0"));
		}

		// // ===== init chon ht thanh toan
		private void initHTTTBD() {
			arrHtttBeans.add(0,
					new HTTTBean(getResources().getString(R.string.trangay),
							"1"));
		}

		// === init spinner HTTT
		private void initSpinnerHTTTBD() {
			if (arrHtttBeans != null && arrHtttBeans.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (HTTTBean htttBean : arrHtttBeans) {
					adapter.add(htttBean.getName());
				}
				spinner_httt.setAdapter(adapter);
			}
		}

		// === init spinner HTTT
		private void initSpinnerHTTT() {
			if (arrHtttBeans != null && arrHtttBeans.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (HTTTBean htttBean : arrHtttBeans) {
					adapter.add(htttBean.getName());
				}
				spinner_httt.setAdapter(adapter);
			}
		}

		public SupplyInforBean parseXmlDetailProduct(String saleServiceCode,
				String stockModelId, String stockTypeId) {

			String original = "";
			SupplyInforBean supplyInforBean = null;
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getSupplyInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSupplyInfo>");
				rawData.append("<imFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<saleServiceCode>").append(saleServiceCode);
				rawData.append("</saleServiceCode>");
				rawData.append("<stockModelId>").append(stockModelId);
				rawData.append("</stockModelId>");
				rawData.append("</imFixServiceInput>");
				rawData.append("</ws:getSupplyInfo>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getSupplyInfo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ==== parse xml detail product detail
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodelstSupplyMethod = null;
				NodeList nodelstSupplyProgramBean = null;
				NodeList nodelstSupplyBaseBean = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					ArrayList<SupplyMethodBean> arrSupplyMethod = new ArrayList<>();
					nodechild = doc.getElementsByTagName("supplyInforBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						nodelstSupplyMethod = doc
								.getElementsByTagName("lstSupplyMethod");

						for (int k = 0; k < nodelstSupplyMethod.getLength(); k++) {
							SupplyMethodBean supplyMethodBean = new SupplyMethodBean();
							Element e3 = (Element) nodelstSupplyMethod.item(k);
							String lstSupplyMethod = XmlDomParse
									.getCharacterDataFromElement(e3);
							Log.d("lstSupplyMethod", lstSupplyMethod);
							supplyMethodBean.setValue(lstSupplyMethod);
							if (supplyMethodBean.getValue().equalsIgnoreCase(
									"CT")) {
								supplyMethodBean.setName(getResources()
										.getString(R.string.CT));
							}
							if (supplyMethodBean.getValue().equalsIgnoreCase(
									"BD")) {
								supplyMethodBean.setName(getResources()
										.getString(R.string.BD));
							}
							if (supplyMethodBean.getValue().equalsIgnoreCase(
									"DC")) {
								supplyMethodBean.setName(getResources()
										.getString(R.string.DC));
							}
							if (supplyMethodBean.getValue().equalsIgnoreCase(
									"BD")) {
								arrSupplyMethod.add(0, supplyMethodBean);
							} else {
								arrSupplyMethod.add(supplyMethodBean);
							}

						}
						nodelstSupplyProgramBean = doc
								.getElementsByTagName("lstSupplyProgramBean");
						ArrayList<SupplyProgramBean> arrSupplyProgramBeans = new ArrayList<>();
						for (int l = 0; l < nodelstSupplyProgramBean
								.getLength(); l++) {
							Element e4 = (Element) nodelstSupplyProgramBean
									.item(l);
							String programName = parse.getValue(e4,
									"programName");
							Log.d("programName", programName);
							String supplyMethod = parse.getValue(e4,
									"supplyMethod");
							Log.d("supplyMethod", supplyMethod);
							String programCode = parse.getValue(e4,
									"programCode");
							Log.d("programCode", programCode);
							ArrayList<SupplyBaseBean> arrsupplyBaseBean = new ArrayList<>();
							nodelstSupplyBaseBean = e4
									.getElementsByTagName("lstSupplyBaseBean");
							for (int m = 0; m < nodelstSupplyBaseBean
									.getLength(); m++) {
								Element e5 = (Element) nodelstSupplyBaseBean
										.item(m);
								String price = parse.getValue(e5, "price");
								Log.d("price", price);
								String programMonth = parse.getValue(e5,
										"programMonth");
								Log.d("programMonth", programMonth);

								SupplyBaseBean supplyBaseBean = new SupplyBaseBean(
										price, programMonth);
								arrsupplyBaseBean.add(supplyBaseBean);
							}
							SupplyProgramBean supplyProgramBean = new SupplyProgramBean(
									programName, programCode, supplyMethod,
									arrsupplyBaseBean);
							arrSupplyProgramBeans.add(supplyProgramBean);
						}
						supplyInforBean = new SupplyInforBean(arrSupplyMethod,
								arrSupplyProgramBeans);
					}
					return supplyInforBean;
				}
			} catch (Exception e) {
				Log.d("ViewDetailProductAsyn", e.toString());

			}
			return supplyInforBean;

		}
	}

	// =========== webservice genera account=========================
	public class GeneraAccountAsyn extends AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GeneraAccountAsyn(Context context) {
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
		protected String doInBackground(String... arg0) {
			return generaAccount(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && !result.isEmpty()) {
					editisdnoracc.setText(result);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.checknotgeneraAcc),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		public String generaAccount(String serviceType, String cusName,
				String province) {
			String accountParse = "";
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_createAccount");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:createAccount>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				// idNoOrBusPermitNo
				if (FragmentInfoCustomer.custommer != null) {
					if (FragmentInfoCustomer.custommer.getIdNo() != null
							&& !FragmentInfoCustomer.custommer.getIdNo()
									.isEmpty()) {
						rawData.append("<idNo>").append(FragmentInfoCustomer.custommer.getIdNo());
						rawData.append("</idNo>");
					} else {
						if (FragmentInfoCustomer.custommer.getBusPermitNo() != null
								&& !FragmentInfoCustomer.custommer
										.getBusPermitNo().isEmpty()) {
							rawData.append("<busPermitNo>").append(FragmentInfoCustomer.custommer
                                    .getBusPermitNo());
							rawData.append("</busPermitNo>");
						}
					}
				}
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				if (cusName != null && !cusName.equals("")) {
					rawData.append("<custName>").append(StringUtils.escapeHtml(cusName));
					rawData.append("</custName>");
				} else {
					rawData.append("<custName>" + "");
					rawData.append("</custName>");
				}

				rawData.append("<province>").append(province);
				rawData.append("</province>");

				if (gponGroupAccountId != null && !gponGroupAccountId.isEmpty()) {
					rawData.append("<gponGroupAccountId>").append(gponGroupAccountId);
					rawData.append("</gponGroupAccountId>");
				}
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:createAccount>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_createAccount");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();

				// ===== parse original ===========
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);

					accountParse = parse.getValue(e2, "account");
					Log.d("account", account);

				}

			} catch (Exception e) {
				Log.d("generaAccount", e.toString());
			}
			return accountParse;
		}

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
			return getPromotionTypeBeans(arg0[0], arg0[1], arg0[2], arg0[3],
					arg0[4], arg0[5], arg0[6], arg0[7], arg0[8], arg0[9]);
		}

		@Override
		protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {

				txtchonkm.setText(getString(R.string.chonctkm1));
				if (arrPromotionTypeBeans != null
						&& !arrPromotionTypeBeans.isEmpty()) {
					arrPromotionTypeBeans = new ArrayList<>();
				}

				if (result.size() > 0) {
					arrPromotionTypeBeans = result;
					initInitListPromotion();

				} else {
					if (arrPromotionTypeBeans != null
							&& !arrPromotionTypeBeans.isEmpty()) {
						arrPromotionTypeBeans = new ArrayList<>();
					}
					initInitListPromotion();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<PromotionTypeBeans> getPromotionTypeBeans(
				String regType, String offerId, String province,
				String district, String precint, String custGroup,
				String custType, String subGroupId, String subType,
				String serviceType) {
			ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListPromotion");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListPromotion>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<regType>").append(regType).append("</regType>");
				rawData.append("<offerId>").append(offerId).append("</offerId>");
				rawData.append("<province>").append(province).append("</province>");
				rawData.append("<district>").append(district).append("</district>");
				rawData.append("<precinct>").append(precint).append("</precinct>");
				rawData.append("<custGroupId>").append(custGroup).append("</custGroupId>");
				rawData.append("<busType>").append(custType).append("</busType>");

				rawData.append("<subGroupId>").append(subGroupId).append("</subGroupId>");
				rawData.append("<subType>").append(subType).append("</subType>");
				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListPromotion>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListPromotion");
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

	// =========== webservice danh sach ip tinh ==================
	public class GetListIpAsyn extends
			AsyncTask<String, Void, ArrayList<String>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListIpAsyn(Context context) {
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
			return getListIP(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrListIp = result;
					initLstIp();
				} else {
					initLstIpNotData();
					// Dialog dialog = CommonActivity.createAlertDialog(
					// FragmentConnectionInfoSetting.this, getResources()
					// .getString(R.string.notip), getResources()
					// .getString(R.string.app_name));
					// dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// input : ma goi cuoc va ma dia ban
		private ArrayList<String> getListIP(String offerId, String areaCode) {
			ArrayList<String> lisIp = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListIp");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListIp>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("<areaCode>").append(areaCode);
				rawData.append("</areaCode>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListIp>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this, "mbccs_getListIp");
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
					nodechild = doc.getElementsByTagName("lstIP");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						String ip = XmlDomParse.getCharacterDataFromElement(e1);
						Log.d("ip", ip);
						lisIp.add(ip);
					}
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return lisIp;
		}

	}

	// ===========webservice danh sach hinh thuc hoa mang ===============

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
			return getListHTHM(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4],
					arg0[5], arg0[6], arg0[7], arg0[8]);

		}

		@Override
		protected void onPostExecute(ArrayList<HTHMBeans> result) {

			// TODO get hinh thuc hoa mang
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (arrHthmBeans != null && !arrHthmBeans.isEmpty()) {
					arrHthmBeans = new ArrayList<>();
				}
				if (result.size() > 0) {
					arrHthmBeans = result;
					Intent intent = new Intent(
							FragmentConnectionInfoSetting.this,
							SearchCodeHthmActivity.class);
					intent.putExtra("arrHthmBeans", result);
					startActivityForResult(intent, 1001);
				} else {
					txththm.setText(getString(R.string.chonhthm));
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.noththm),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

				// if (result.size() > 0) {
				// arrHthmBeans = result;
				// initHTHM();
				// } else {
				// initHTHMNotData();
				// Dialog dialog = CommonActivity.createAlertDialog(
				// FragmentConnectionInfoSetting.this, getResources()
				// .getString(R.string.noththm),
				// getResources().getString(R.string.app_name));
				// dialog.show();
				// }
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		// ==input String offerId, String serviceType, String province ,String
		// district, String precinct)
		private ArrayList<HTHMBeans> getListHTHM(String offerId,
				String serviceType, String province, String district,
				String precinct, String custGroup, String custType,
				String subGroupId, String subType) {
			ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListRegTypeConnect");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListRegTypeConnect>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");
				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<province>").append(province);
				rawData.append("</province>");
				rawData.append("<district>").append(district);
				rawData.append("</district>");
				rawData.append("<precinct>").append(precinct);
				rawData.append("</precinct>");

				rawData.append("<custGroupId>").append(custGroup);
				rawData.append("</custGroupId>");
				rawData.append("<busType>").append(custType);
				rawData.append("</busType>");
				rawData.append("<subGroupId>").append(subGroupId);
				rawData.append("</subGroupId>");
				rawData.append("<subType>").append(subType);
				rawData.append("</subType>");

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListRegTypeConnect>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListRegTypeConnect");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================

				HTHMHandler handler = (HTHMHandler) CommonActivity
						.parseXMLHandler(new HTHMHandler(), original);
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

	// ====================== Webserivce get list ========== DS loại
	// thuê bao
	private class GetListSubTypeAsyn extends
			AsyncTask<String, Void, ArrayList<SubTypeBeans>> {

		// ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListSubTypeAsyn(Context context) {
			this.context = context;
			findViewById(R.id.prbSubType).setVisibility(View.VISIBLE);
			findViewById(R.id.btnSubType).setVisibility(View.GONE);
			// this.progress = new ProgressDialog(this.context);
			// // check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		@Override
		protected ArrayList<SubTypeBeans> doInBackground(String... arg0) {
			return getListSubTypeBeans(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<SubTypeBeans> result) {
			CommonActivity.hideKeyboard(edtcodinh, context);
			CommonActivity.hideKeyboard(edtdidong, context);
			CommonActivity.hideKeyboard(txthoten, context);
			CommonActivity.hideKeyboard(txtsonha, context);
			CommonActivity.hideKeyboard(txtduong, context);
			// progress.dismiss();
			findViewById(R.id.prbSubType).setVisibility(View.GONE);
			findViewById(R.id.btnSubType).setVisibility(View.VISIBLE);
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
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<SubTypeBeans> getListSubTypeBeans(
				String telecomServiceId, String subGroupId) {
			ArrayList<SubTypeBeans> lisSubTypeBeans = new ArrayList<>();
			String original = "";
			try {
				lisSubTypeBeans = new CacheDatabaseManager(context)
						.getListSubTypeInCache(telecomServiceId, subGroupId);
				if (lisSubTypeBeans != null && !lisSubTypeBeans.isEmpty()) {
					errorCode = "0";
					return lisSubTypeBeans;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListSubType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListSubType>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (telecomServiceId != null && !telecomServiceId.isEmpty()) {
					rawData.append("<telecomServiceId>").append(telecomServiceId);
					rawData.append("</telecomServiceId>");
				}
				if (subGroupId != null && !subGroupId.isEmpty()) {
					rawData.append("<subGroupId>").append(subGroupId);
					rawData.append("</subGroupId>");
				}

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListSubType>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListSubType");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ===============parse xml =========================
				SubTypeHandler handler = (SubTypeHandler) CommonActivity
						.parseXMLHandler(new SubTypeHandler(), original);
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
				lisSubTypeBeans = handler.getLstData();
				new CacheDatabaseManager(context).insertSubTypeCache(
						telecomServiceId, subGroupId, lisSubTypeBeans);
			} catch (Exception e) {
				Log.d("getListSubTypeBeans", e.toString());
			}
			return lisSubTypeBeans;
		}
	}

	// =====================Webservice thuê bao===============
	private class GetListSubGroupAsyn extends
			AsyncTask<Void, Void, ArrayList<SubGroupBeans>> {

		// ProgressDialog progress;
		private Context context = null;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListSubGroupAsyn(Context context) {
			this.context = context;
			findViewById(R.id.prbSubGroup).setVisibility(View.VISIBLE);
			findViewById(R.id.btnSubGroup).setVisibility(View.GONE);
		}

		@Override
		protected ArrayList<SubGroupBeans> doInBackground(Void... arg0) {
			return getLisGroupBeans();
		}

		@Override
		protected void onPostExecute(ArrayList<SubGroupBeans> result) {
			// progress.dismiss();
			findViewById(R.id.prbSubGroup).setVisibility(View.GONE);
			findViewById(R.id.btnSubGroup).setVisibility(View.VISIBLE);
			CommonActivity.hideKeyboard(edtcodinh, context);
			CommonActivity.hideKeyboard(edtdidong, context);
			CommonActivity.hideKeyboard(txthoten, context);
			CommonActivity.hideKeyboard(txtsonha, context);
			CommonActivity.hideKeyboard(txtduong, context);
			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					arrSubGroupBeans = result;
					initListSubGroup();

					// ====== get list promotion ==== ds khuyen mai ===
					// GetListPromotionAsyn getListPromotionAsyn = new
					// GetListPromotionAsyn(
					// FragmentConnectionInfoSetting.this);
					// getListPromotionAsyn.execute();

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.notnhomthuebao),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		// ====== method getList SubGroupBeans ============
		private ArrayList<SubGroupBeans> getLisGroupBeans() {
			ArrayList<SubGroupBeans> lisSubGroupBeans = new ArrayList<>();
			String original = "";
			try {
				lisSubGroupBeans = new CacheDatabaseManager(context)
						.getListSubGroupInCache();
				if (lisSubGroupBeans != null && !lisSubGroupBeans.isEmpty()) {
					errorCode = "0";
					return lisSubGroupBeans;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListSubGroup");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListSubGroup>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListSubGroup>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getListSubGroup");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ====== parse xml ===================

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstSubGroup");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SubGroupBeans subGroupBeans = new SubGroupBeans();
						String code = parse.getValue(e1, "code");
						Log.d("code", code);
						subGroupBeans.setCode(code);
						String description = parse.getValue(e1, "description");
						Log.d("description", description);
						subGroupBeans.setDescription(description);
						String idRequest = parse.getValue(e1, "id");
						Log.d("idRequest", idRequest);
						subGroupBeans.setIdSubGroup(idRequest);
						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						subGroupBeans.setName(name);
						String status = parse.getValue(e1, "status");
						Log.d("status", status);
						subGroupBeans.setStatus(status);
						String updateDate = parse.getValue(e1, "updateDate");
						Log.d("updateDate", updateDate);
						subGroupBeans.setUpdateDate(updateDate);
						String updateUser = parse.getValue(e1, "updateUser");
						Log.d("updateUser", updateUser);
						subGroupBeans.setUpdateUser(updateUser);
						lisSubGroupBeans.add(subGroupBeans);
					}
				}
			} catch (Exception e) {
				Log.d("GetListSubGroupAsyn", e.toString());

			}
			new CacheDatabaseManager(context).insertSubGroup(lisSubGroupBeans);
			return lisSubGroupBeans;
		}

	}

	// =========== webservice genera account PPPOE =========================
	public class GeneraAccountPPPOEAsyn extends AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String accountPPPOE = "";
		String passPPPOE = "";

		public GeneraAccountPPPOEAsyn(Context context) {
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
		protected String doInBackground(String... arg0) {
			return generaAccountPPPOE(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (!CommonActivity.isNullOrEmpty(accountPPPOE)
						&& !CommonActivity.isNullOrEmpty(passPPPOE)) {
					editisdnoraccPPPOE.setText(accountPPPOE);
					editmatkhauPPPOE.setText(passPPPOE);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.checknotgeneraAcc),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		public String generaAccountPPPOE(String serviceType, String cusName,
				String province) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_createAccountPPPOE");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:createAccountPPPOE>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<custId>").append(custommer.getCustId());
				rawData.append("</custId>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				if (cusName != null && !cusName.equals("")) {
					rawData.append("<custName>").append(StringUtils.escapeHtml(cusName));
					rawData.append("</custName>");
				} else {
					rawData.append("<custName>" + "");
					rawData.append("</custName>");
				}

				rawData.append("<province>").append(province);
				rawData.append("</province>");

				if (gponGroupAccountId != null && !gponGroupAccountId.isEmpty()) {
					rawData.append("<gponGroupAccountId>").append(gponGroupAccountId);
					rawData.append("</gponGroupAccountId>");
				}
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:createAccountPPPOE>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_createAccountPPPOE");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();

				// ===== parse original ===========
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);

					accountPPPOE = parse.getValue(e2, "accountPPPOE");
					passPPPOE = parse.getValue(e2, "passPPPOE");

				}

			} catch (Exception e) {
				Log.d("generaAccount", e.toString());
			}
			return errorCode;
		}

	}

	// ==== add list status =====
	private void addStatus() {
		arrNotifyBeans.add(new NotifyBean("0", getResources().getString(
				R.string.mucdocanhbao1)));
		// arrNotifyBeans.add(new NotifyBean("1", getResources().getString(
		// R.string.mucdocanhbao2)));
		// arrNotifyBeans.add(new NotifyBean("2", getResources().getString(
		// R.string.mucdocanhbao3)));

	}

	// init spiner status
	private void initSpinerStatus() {
		addStatus();
		if (arrNotifyBeans != null && arrNotifyBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    FragmentConnectionInfoSetting.this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
			for (NotifyBean notifyBean : arrNotifyBeans) {
				adapter.add(notifyBean.getName());
			}
			spinner_mucdocanhbao.setAdapter(adapter);
		}

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		arg0.getParent().requestDisallowInterceptTouchEvent(true);
		return false;
	}

	@Override
	public void OnChangeSpinerSelect(ObjThongTinHH objThongTinHH, int arg2) {

		if (objThongTinHH.getListStockModelBeanBO().get(arg2)
				.getSaleServicesDetailId() != null) {
			saleServiceId = objThongTinHH.getListStockModelBeanBO().get(arg2)
					.getSaleServicesDetailId();
			Log.d("saleServiceId", saleServiceId);
		}
		if (objThongTinHH.getListStockModelBeanBO().get(arg2).getStockTypeId() != null) {
			stockTypeId = objThongTinHH.getListStockModelBeanBO().get(arg2)
					.getStockTypeId();
			Log.d("stockTypeId", stockTypeId);
		}
		if (objThongTinHH.getListStockModelBeanBO().get(arg2).getStockModelId() != null) {
			stockModelId = objThongTinHH.getListStockModelBeanBO().get(arg2)
					.getStockModelId();
			Log.d("stockModelId", stockModelId);
		}

		if (CommonActivity
                .isNetworkConnected(FragmentConnectionInfoSetting.this)) {
			ViewDetailProductAsyn viewDetailProductAsyn = new ViewDetailProductAsyn(
					FragmentConnectionInfoSetting.this);
			viewDetailProductAsyn.execute(objThongTinHH.getSaleServiceCode(),
					objThongTinHH.getListStockModelBeanBO().get(arg2)
							.getStockModelId(),
					objThongTinHH.getSaleServicesModelId());
		} else {
			CommonActivity.createAlertDialog(
					FragmentConnectionInfoSetting.this, getResources()
							.getString(R.string.errorNetwork), getResources()
							.getString(R.string.app_name));
		}

	}

	// ================ get list group cusname ================
	public class GetListCusGroupAsyn extends
			AsyncTask<Void, Void, ArrayList<CustomerGroupBeans>> {

		// ProgressDialog progress;
		private Activity context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListCusGroupAsyn(Activity context) {
			this.context = context;
			findViewById(R.id.prbnhomkh).setVisibility(View.VISIBLE);
			findViewById(R.id.btnnhomkh).setVisibility(View.GONE);

		}

		@Override
		protected ArrayList<CustomerGroupBeans> doInBackground(Void... params) {
			return sendRequestGetNameProduct();
		}

		@Override
		protected void onPostExecute(ArrayList<CustomerGroupBeans> result) {
			// progress.dismiss();
			findViewById(R.id.prbnhomkh).setVisibility(View.GONE);
			findViewById(R.id.btnnhomkh).setVisibility(View.VISIBLE);
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrCustomerGroupBeans = result;
					if (arrCustomerGroupBeans != null
							&& arrCustomerGroupBeans.size() > 0) {
						ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                FragmentConnectionInfoSetting.this,
                                android.R.layout.simple_dropdown_item_1line,
                                android.R.id.text1);
						for (CustomerGroupBeans customerGroupBeans : arrCustomerGroupBeans) {
							adapter.add(customerGroupBeans.getNameCusGroup());
						}
						spinner_nhomkh.setAdapter(adapter);

						if (conTractBean != null
								&& conTractBean.getRepresentativeCustObj() != null) {
							if (conTractBean.getRepresentativeCustObj()
									.getCustomerGroup() != null) {
								if (!CommonActivity.isNullOrEmpty(conTractBean
										.getRepresentativeCustObj()
										.getCustomerGroup().getCode())) {
									Log.d("NHOM KHACH HANG", conTractBean
											.getRepresentativeCustObj()
											.getCustomerGroup().getCode());
									if (arrCustomerGroupBeans != null
											&& !arrCustomerGroupBeans.isEmpty()) {
										findViewById(R.id.btnnhomkh)
												.setVisibility(View.GONE);
										for (CustomerGroupBeans cusGroupBeans : arrCustomerGroupBeans) {
											if (conTractBean
													.getRepresentativeCustObj()
													.getCustomerGroup()
													.getCode()
													.equals(cusGroupBeans
															.getCode())) {
												spinner_nhomkh
														.setSelection(arrCustomerGroupBeans
																.indexOf(cusGroupBeans));
												break;
											}
										}
									}
								}
							}
						}

						String cusGroupId = arrCustomerGroupBeans.get(0)
								.getIdCusGroup();
						GetCustomerTypeByCustGroupIdAsyn getCustomerTypeByCustGroupIdAsyn = new GetCustomerTypeByCustGroupIdAsyn(
								FragmentConnectionInfoSetting.this);
						getCustomerTypeByCustGroupIdAsyn.execute(cusGroupId);
					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
                                    context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// =====method get list CustomerGroupBeans ========================
		public ArrayList<CustomerGroupBeans> sendRequestGetNameProduct() {
			String original = null;
			ArrayList<CustomerGroupBeans> liCustomerGroupBeans = new ArrayList<>();
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
				HashMap<String, String> paramToken = new HashMap<>();
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
						Constant.BCCS_GW_URL, context, "mbccs_getCustomerGroup");
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
		private Activity context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetCustomerTypeByCustGroupIdAsyn(Activity context) {
			this.context = context;
			findViewById(R.id.prbloaikh).setVisibility(View.VISIBLE);
			findViewById(R.id.btnloaikh).setVisibility(View.GONE);
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
			findViewById(R.id.prbloaikh).setVisibility(View.GONE);
			findViewById(R.id.btnloaikh).setVisibility(View.VISIBLE);
			if (errorCode.equals("0")) {
				if (!isRefreshCusType) {
					GetListFieldActivesAsynTask getListFieldActivesAsynTask = new GetListFieldActivesAsynTask(
							FragmentConnectionInfoSetting.this);
					getListFieldActivesAsynTask.execute();
				}

				if (result.size() > 0 && !result.isEmpty()) {
					arrCustomerTypeByCustGroupBeans = result;

					if (arrCustomerTypeByCustGroupBeans != null
							&& arrCustomerTypeByCustGroupBeans.size() > 0) {
						ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                FragmentConnectionInfoSetting.this,
                                android.R.layout.simple_dropdown_item_1line,
                                android.R.id.text1);
						for (CustomerTypeByCustGroupBeans customerTypeByCustGroupBeans : arrCustomerTypeByCustGroupBeans) {
							adapter.add(customerTypeByCustGroupBeans.getName());
						}
						spinner_loaikh.setAdapter(adapter);

						if (conTractBean != null
								&& conTractBean.getRepresentativeCustObj() != null) {
							findViewById(R.id.btnloaikh).setVisibility(
									View.GONE);
							if (conTractBean.getRepresentativeCustObj()
									.getCustomerType() != null) {
								Log.d("LOAI KHACH HANG", conTractBean
										.getRepresentativeCustObj()
										.getCustomerType().getCode());
								if (!CommonActivity.isNullOrEmpty(conTractBean
										.getRepresentativeCustObj()
										.getCustomerType().getCode())) {
									if (arrCustomerTypeByCustGroupBeans != null
											&& !arrCustomerTypeByCustGroupBeans
													.isEmpty()) {

										for (CustomerTypeByCustGroupBeans customerTypeByCustGroupBeans : arrCustomerTypeByCustGroupBeans) {
											if (conTractBean
													.getRepresentativeCustObj()
													.getCustomerType()
													.getCode()
													.equals(customerTypeByCustGroupBeans
															.getCode())) {
												spinner_loaikh
														.setSelection(arrCustomerTypeByCustGroupBeans
																.indexOf(customerTypeByCustGroupBeans
																		.getCode()));
												break;
											}

										}
									}
								}
							}
						}
					}
				} else {
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FragmentConnectionInfoSetting.this,
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					spinner_loaikh.setAdapter(adapter);
					Dialog dialog = CommonActivity.createAlertDialog(context,
							getResources().getString(R.string.notinfokh),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
                                    context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// =====method get list service ========================
		public ArrayList<CustomerTypeByCustGroupBeans> sendRequestCustomerTypeByCustGroupBeans(
				String custGroupId) {
			String original = null;
			ArrayList<CustomerTypeByCustGroupBeans> lisCustomerTypeByCustGroupBeans = new ArrayList<>();
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
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<custGroupId>").append(custGroupId);
				rawData.append("</custGroupId>");
				rawData.append("</getCustomerTypeByCustGroupId>");
				rawData.append("</ws:getCustomerTypeByCustGroupId>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
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

	// ===== webservice getCustomerTypeByCustGroupId === lay theo custGroupId
	public class GetContractOfferByContractIdAsynTask extends
			AsyncTask<String, Void, ArrayList<ContractOffersObj>> {

		// ProgressDialog progress;
		private Activity context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetContractOfferByContractIdAsynTask(Activity context) {
			this.context = context;
			// this.progress = new ProgressDialog(this.context);
			// // check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
			findViewById(R.id.prbContractOffer).setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<ContractOffersObj> doInBackground(String... params) {
			return sendRequestGetContractOfferByContractId(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ContractOffersObj> result) {
			// progress.dismiss();
			findViewById(R.id.prbContractOffer).setVisibility(View.GONE);
			if (errorCode.equals("0")) {
				arrContractOffersObj = new ArrayList<>();
				// if (result.size() > 0) {
				arrContractOffersObj = result;
				// ContractOffersObj contractOffersObj = arrContractOffersObj
				// .get(0);
				// lnsophuluc.setVisibility(View.VISIBLE);
				// edtplhdong.setText(contractOffersObj.getContractOfferId());
				// edtcvplhd.setText(contractOffersObj.getRepresentPosition());
				// edit_ngaykyhd
				// .setText(StringUtils.convertDate(contractOffersObj
				// .getStrSignDatetime()));
				// edtmagiaytoCD.setText(contractOffersObj.get());
				//
				// edit_ngaycappl.setText(StringUtils
				// .convertDate(contractOffersObj
				// .getStrIssueDatetime()));
				// edtmanoicapCD.setText(contractOffersObj.getIssuePlace());
				ContractOffersObj conOffersObj = new ContractOffersObj();
				conOffersObj.setContractOfferId("");
				conOffersObj.setContractOfferNo(getString(R.string.phulucmoi));
				arrContractOffersObj.add(0, conOffersObj);

				if (arrContractOffersObj != null
						&& arrContractOffersObj.size() > 0) {
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FragmentConnectionInfoSetting.this,
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					for (ContractOffersObj contractOffersObj : arrContractOffersObj) {
						adapter.add(contractOffersObj.getContractOfferNo());
					}
					spinner_plhopdong.setAdapter(adapter);

					if (result.size() > 1) {
						lnsophuluc.setVisibility(View.VISIBLE);
					} else {
						lnsophuluc.setVisibility(View.GONE);
					}
				}
				// }
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
                                    context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// =====method get list service ========================
		public ArrayList<ContractOffersObj> sendRequestGetContractOfferByContractId(
				String strCosntractId) {
			ArrayList<ContractOffersObj> listContractOffersObj = new ArrayList<>();
			// ContractOffersObj contractOffersObjSelect = new
			// ContractOffersObj();
			// contractOffersObjSelect.setContractOfferId("-1");
			// contractOffersObjSelect
			// .setContractOfferNo(getString(R.string.chonphulucHDCD));
			// listContractOffersObj.add(contractOffersObjSelect);

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getContractOfferByContractId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getContractOfferByContractId>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<contractId>").append(strCosntractId).append("</contractId>");
				rawData.append("</input>");
				rawData.append("</ws:getContractOfferByContractId>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
						"mbccs_getContractOfferByContractId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Response Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstContractOffers");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ContractOffersObj contractOffersObj = new ContractOffersObj();
						contractOffersObj.setContractOfferId(parse.getValue(e1,
								"contractOfferId"));
						// represent
						contractOffersObj.setRepresent(parse.getValue(e1,
								"represent"));
						contractOffersObj.setContractOfferNo(parse.getValue(e1,
								"contractOfferNo"));
						contractOffersObj.setStrSignDatetime(parse.getValue(e1,
								"signDatetime"));
						contractOffersObj.setStrIssueDatetime(parse.getValue(
								e1, "issueDatetime"));
						contractOffersObj.setRepresentPosition(parse.getValue(
								e1, "representPosition"));
						contractOffersObj.setIdType(parse
								.getValue(e1, "idType"));
						contractOffersObj.setIdNo(parse.getValue(e1, "idNo"));
						contractOffersObj.setIssuePlace(parse.getValue(e1,
								"issuePlace"));
						listContractOffersObj.add(contractOffersObj);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return listContractOffersObj;
		}

	}

	public class GetListFieldActivesAsynTask extends
			AsyncTask<String, Void, ArrayList<ActivesObj>> {

		// ProgressDialog progress;
		private Activity context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListFieldActivesAsynTask(Activity context) {
			this.context = context;
			findViewById(R.id.prblvhd).setVisibility(View.VISIBLE);
			findViewById(R.id.btnlvhd).setVisibility(View.GONE);
			// this.progress = new ProgressDialog(this.context);
			// // check font
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		@Override
		protected ArrayList<ActivesObj> doInBackground(String... params) {
			return sendRequestGetListFieldActives();
		}

		@Override
		protected void onPostExecute(ArrayList<ActivesObj> result) {
			// progress.dismiss();
			findViewById(R.id.prblvhd).setVisibility(View.GONE);
			findViewById(R.id.btnlvhd).setVisibility(View.VISIBLE);
			if (errorCode.equals("0")) {
				if (result.size() > 0 && !result.isEmpty()) {
					arrActivesObjObj = result;
					if (arrActivesObjObj != null && arrActivesObjObj.size() > 0) {
						ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                FragmentConnectionInfoSetting.this,
                                android.R.layout.simple_dropdown_item_1line,
                                android.R.id.text1);
						for (ActivesObj activesObj : arrActivesObjObj) {
							adapter.add(activesObj.getName());
						}
						spinner_lvhd.setAdapter(adapter);
					}
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
                                    context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		// =====method get list service ========================
		public ArrayList<ActivesObj> sendRequestGetListFieldActives() {
			ArrayList<ActivesObj> listActivesObj = new ArrayList<>();

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListFieldActives");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListFieldActives>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListFieldActives>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context,
						"mbccs_getListFieldActives");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Response Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstActives");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ActivesObj activesObj = new ActivesObj();
						activesObj.setCreateDate(parse.getValue(e1,
								"createDate"));
						activesObj.setFieldActiveCode(parse.getValue(e1,
								"fieldActiveCode"));
						activesObj.setFieldActiveId(parse.getValue(e1,
								"fieldActiveId"));
						activesObj.setGroupCustomerId(parse.getValue(e1,
								"groupCustomerId"));
						activesObj.setName(parse.getValue(e1, "name"));
						activesObj.setStatus(parse.getValue(e1, "status"));
						activesObj.setUserName(parse.getValue(e1, "userName"));
						listActivesObj.add(activesObj);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return listActivesObj;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// Chon dia chi thong bao cuoc
		if (requestCode == 100) {

			if (resultCode == Activity.RESULT_OK) {
				setDCTBC(data);
			}
		}

		if (requestCode == 101) {
			if (resultCode == Activity.RESULT_OK) {
				areaProvicialPR = (AreaObj) data.getExtras().getSerializable(
						"areaProvicial");
				areaDistristPR = (AreaObj) data.getExtras().getSerializable(
						"areaDistrist");
				areaPrecintPR = (AreaObj) data.getExtras().getSerializable(
						"areaPrecint");
				areaGroupPR = (AreaObj) data.getExtras().getSerializable(
						"areaGroup");

				areaFlowPR = data.getExtras().getString("areaFlow");
				areaHomeNumberPR = data.getExtras().getString("areaHomeNumber");

                StringBuilder addressPR = new StringBuilder();

				if (areaHomeNumberPR != null && areaHomeNumberPR.length() > 0) {
					addressPR.append(areaHomeNumberPR).append(" ");
				}
				if (areaFlowPR != null && areaFlowPR.length() > 0) {
					addressPR.append(areaFlowPR).append(" ");
				}
				if (areaGroupPR != null && areaGroupPR.getName() != null
						&& areaGroupPR.getName().length() > 0) {
					addressPR.append(areaGroupPR.getName()).append(" ");
				}
				if (areaPrecintPR != null && areaPrecintPR.getName() != null
						&& areaPrecintPR.getName().length() > 0) {

					addressPR.append(areaPrecintPR.getName()).append(" ");
				}
				if (areaDistristPR != null && areaDistristPR.getName() != null
						&& areaDistristPR.getName().length() > 0) {

					addressPR.append(areaDistristPR.getName()).append(" ");
				}
				if (areaProvicialPR != null
						&& areaProvicialPR.getName() != null
						&& areaProvicialPR.getName().length() > 0) {

					addressPR.append(areaProvicialPR.getName());
				}
				// Log.d("Log", "Check edit address null: " + txtdctbc +
				// "adess :"
				// + addressPR);
				txtdcgtxm.setText(addressPR);

			}
		}

		if (requestCode == 3333) {
			if (resultCode == Activity.RESULT_OK) {

				if (data != null) {
                    Bank bank = (Bank) data.getExtras().getSerializable("bankPrKey");
					if (bank != null) {
						txtnganhang.setText(bank.getName());
					} else {
						txtnganhang.setText(getString(R.string.nganhang));
					}
				}
			}
		}

		// lay hinh thuc hoa mang
		if (requestCode == 1001) {
			if (resultCode == Activity.RESULT_OK) {

				hthmBeans = (HTHMBeans) data.getExtras().getSerializable(
						"HTHMBeans");
				if (hthmBeans != null) {
					regType = hthmBeans.getCode();
					Log.d("regType", regType);
					reasonId = hthmBeans.getReasonId();

					if (regType != null && !regType.isEmpty()) {
						txththm.setText(hthmBeans.getCodeName());
						if (CommonActivity
								.isNetworkConnected(FragmentConnectionInfoSetting.this)) {

							GetPromotionByMap getPromotionByMap = new GetPromotionByMap(
									FragmentConnectionInfoSetting.this);
							getPromotionByMap.execute(regType, offerId,
									provinceMain, districtMain, precinctMain,
									custommer.getCusGroupId(),

									custommer.getCusType(), idSubGroup,

									subType, serviceType);

							lndatcoc.setVisibility(View.GONE);

							List<ReasonPledgeDTO> lsPledgeDTOs = new ArrayList<>();
							Utils.setDataPlege(
									FragmentConnectionInfoSetting.this,
									lsPledgeDTOs, spinner_deposit);

							if (!CommonActivity.isNullOrEmpty(hthmBeans
									.getDescription())
									&& hthmBeans.getDescription().contains(
											"$datcoc$")) {
								GetReasonFullInfoAsyn getReasonFullInfoAsyn = new GetReasonFullInfoAsyn(
										FragmentConnectionInfoSetting.this);
								getReasonFullInfoAsyn.execute(reasonId);
							}

						} else {
							CommonActivity
									.createAlertDialog(
											FragmentConnectionInfoSetting.this,
											getResources().getString(
													R.string.errorNetwork),
											getResources().getString(
													R.string.app_name)).show();
						}

					}
					//
					if (mArrayListThongTinHH != null
							&& mArrayListThongTinHH.size() > 0
							&& adapter != null) {
						mArrayListThongTinHH.clear();
						adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
								FragmentConnectionInfoSetting.this,
								FragmentConnectionInfoSetting.this);
						lvproduct.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					if (mapSubStockModelRelReq != null
							&& mapSubStockModelRelReq.size() > 0) {
						mapSubStockModelRelReq.clear();
					}

					GetListProductAsyn getProductAsyn = new GetListProductAsyn(
							FragmentConnectionInfoSetting.this);
					getProductAsyn.execute(regType, productCode, serviceType);

				} else {

					txththm.setText(getString(R.string.chonhthm));

					regType = "";
					reasonId = "";
					if (mArrayListThongTinHH != null
							&& mArrayListThongTinHH.size() > 0
							&& adapter != null) {
						mArrayListThongTinHH.clear();
						adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
								FragmentConnectionInfoSetting.this,
								FragmentConnectionInfoSetting.this);
						lvproduct.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					if (mapSubStockModelRelReq != null
							&& mapSubStockModelRelReq.size() > 0) {
						mapSubStockModelRelReq.clear();
					}

					// TODO modify refresh km
					if (arrPromotionTypeBeans != null
							&& arrPromotionTypeBeans.size() > 0) {
						arrPromotionTypeBeans.clear();
						maKM = "-1";
						txtchonkm.setText(getString(R.string.chonctkm1));
						// initInitListPromotionNotData();
					}
					saleServiceId = "";
					stockModelId = "";
					stockTypeId = "";

				}
			}

			// if (arg2 > 0) {
			// maKM = arrPromotionTypeBeans.get(arg2).getPromProgramCode();
			// } else {
			// maKM = "";
			// }

		}
		if (requestCode == 1002) {
			if (resultCode == Activity.RESULT_OK) {
				PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans) data
						.getExtras().getSerializable("PromotionTypeBeans");
				if (promotionTypeBeans != null) {
					if (!CommonActivity.isNullOrEmpty(promotionTypeBeans
							.getPromProgramCode())) {
						if (!promotionTypeBeans.getPromProgramCode().equals(
								"-1")) {
							maKM = promotionTypeBeans.getPromProgramCode();
							txtchonkm.setText(promotionTypeBeans.getName()
									.toString());
							lncuocdongtruoc.setVisibility(View.VISIBLE);
							GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
									FragmentConnectionInfoSetting.this);
							getAllListPaymentPrePaidAsyn.execute(maKM,
									productCode, provinceMain.trim()
											+ districtMain.trim()
											+ precinctMain.trim(),
									dateNowString);
						} else {
							maKM = "-1";
							txtchonkm.setText(getString(R.string.chonctkm1));
							lncuocdongtruoc.setVisibility(View.GONE);
							// maKM = promotionTypeBeans.getPromProgramCode();
							// txtchonkm.setText(getString(R.string.chonctkm1));
							// lncuocdongtruoc.setVisibility(View.VISIBLE);
							// GetAllListPaymentPrePaidAsyn
							// getAllListPaymentPrePaidAsyn = new
							// GetAllListPaymentPrePaidAsyn(
							// FragmentConnectionInfoSetting.this);
							// getAllListPaymentPrePaidAsyn.execute(maKM,
							// productCode, txttinhTP.getText().toString()
							// .trim(), dateNowString);
						}

					} else {
						// maKM = "";
						// lncuocdongtruoc.setVisibility(View.GONE);
						// txtchonkm.setText(getString(R.string.selectMKM));
						maKM = "";
						txtchonkm.setText(getString(R.string.selectMKM));
						lncuocdongtruoc.setVisibility(View.VISIBLE);
						GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
								FragmentConnectionInfoSetting.this);
						getAllListPaymentPrePaidAsyn.execute(maKM, productCode,
								provinceMain.trim() + districtMain.trim()
										+ precinctMain.trim(), dateNowString);
					}
				} else {
					maKM = "-1";
					txtchonkm.setText(getString(R.string.chonctkm1));
					lncuocdongtruoc.setVisibility(View.GONE);
				}

			}
		}

		if (requestCode == 106) {
			if (data != null) {
				AreaBean areaBean = (AreaBean) data.getExtras()
						.getSerializable("provinceKey");

				provinceMain = areaBean.getProvince();
				initDistrict(provinceMain);
				edtprovince.setText(areaBean.getNameProvince());
				edtdistrict.setText("");
				edtprecinct.setText("");
			}
		}

		if (requestCode == 107) {
			if (data != null) {
				AreaBean areaBean = (AreaBean) data.getExtras()
						.getSerializable("districtKey");
				districtMain = areaBean.getDistrict();
				initPrecinct(provinceMain, districtMain);
				edtdistrict.setText(areaBean.getNameDistrict());
				edtprecinct.setText("");
				edtprecinct.setHint(FragmentConnectionInfoSetting.this
						.getString(R.string.tv_select_precint));
				precinctMain = "";
				// reload lai tat ca nhung ham lien quan den province

				if (mArrayListThongTinHH != null
						&& mArrayListThongTinHH.size() > 0 && adapter != null) {
					mArrayListThongTinHH.clear();
					adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
							FragmentConnectionInfoSetting.this,
							FragmentConnectionInfoSetting.this);
					lvproduct.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
				if (mapSubStockModelRelReq != null
						&& mapSubStockModelRelReq.size() > 0) {
					mapSubStockModelRelReq.clear();
				}
				 ipStatic = "";
				 if(arrListIp != null && arrListIp.size() > 0){
				 arrListIp = new ArrayList<String>();
				 initLstIp();
				 }
				maKM = "-1";
				// txtchonkm.setText(getString(R.string.selectMKM));
				// TODO modify refresh km
				if (arrPromotionTypeBeans != null
						&& arrPromotionTypeBeans.size() > 0) {
					arrPromotionTypeBeans.clear();
					maKM = "-1";
					txtchonkm.setText(getString(R.string.chonctkm1));
					// initInitListPromotionNotData();
				}
				saleServiceId = "";
				stockModelId = "";
				stockTypeId = "";
				if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
					arrHthmBeans = new ArrayList<>();
					initHTHM();
					txththm.setText(getString(R.string.chonhthm));
				}

			}
		}

		if (requestCode == 108) {
			if (data != null) {
				AreaBean areaBean = (AreaBean) data.getExtras()
						.getSerializable("precinctKey");
				precinctMain = areaBean.getPrecinct();
				edtprecinct.setText(areaBean.getNamePrecint());
				if(!CommonActivity.isNullOrEmpty(checkIp) && "1".equals(checkIp)){
					String locationLD = "";
					if(!CommonActivity.isNullOrEmpty(provinceMain)){
						locationLD = provinceMain;
					}
					if(!CommonActivity.isNullOrEmpty(districtMain)){
						locationLD = locationLD+districtMain;
					}
					if(!CommonActivity.isNullOrEmpty(precinctMain)){
						locationLD = locationLD + precinctMain;
					}
					if(!CommonActivity.isNullOrEmpty(locationLD)){
						new GetListIpAsyn(FragmentConnectionInfoSetting.this)
						.execute(offerId, locationLD);
					}
				}
				// cap nhat thong tin
				if (!CommonActivity.isNullOrEmpty(idSubGroup)
						&& !CommonActivity.isNullOrEmpty(subType)) {
					GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(
							FragmentConnectionInfoSetting.this);
					getHTHMAsyn.execute(offerId, serviceType, provinceMain,
							districtMain, precinctMain,
							custommer.getCusGroupId(), custommer.getCusType(),
							idSubGroup, subType);
				} else {
					if (mArrayListThongTinHH != null
							&& mArrayListThongTinHH.size() > 0
							&& adapter != null) {
						mArrayListThongTinHH.clear();
						adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
								FragmentConnectionInfoSetting.this,
								FragmentConnectionInfoSetting.this);
						lvproduct.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					if (mapSubStockModelRelReq != null
							&& mapSubStockModelRelReq.size() > 0) {
						mapSubStockModelRelReq.clear();
					}
					// ipStatic = "";
					// if(arrListIp != null && arrListIp.size() > 0){
					// arrListIp.clear();
					// initLstIp();
					// }
					maKM = "-1";
					// txtchonkm.setText(getString(R.string.selectMKM));
					// TODO modify refresh km
					if (arrPromotionTypeBeans != null
							&& arrPromotionTypeBeans.size() > 0) {
						arrPromotionTypeBeans.clear();
						maKM = "-1";
						txtchonkm.setText(getString(R.string.chonctkm1));
						// initInitListPromotionNotData();
					}
					saleServiceId = "";
					stockModelId = "";
					stockTypeId = "";
					if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
						arrHthmBeans = new ArrayList<>();
						initHTHM();
						txththm.setText(getString(R.string.chonhthm));
					}
				}
			} else {
				if (mArrayListThongTinHH != null
						&& mArrayListThongTinHH.size() > 0 && adapter != null) {
					mArrayListThongTinHH.clear();
					adapter = new ThongTinHHAdapter(mArrayListThongTinHH,
							FragmentConnectionInfoSetting.this,
							FragmentConnectionInfoSetting.this);
					lvproduct.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
				if (mapSubStockModelRelReq != null
						&& mapSubStockModelRelReq.size() > 0) {
					mapSubStockModelRelReq.clear();
				}
				// ipStatic = "";
				// if(arrListIp != null && arrListIp.size() > 0){
				// arrListIp.clear();
				// initLstIp();
				// }
				maKM = "-1";
				// txtchonkm.setText(getString(R.string.selectMKM));
				// TODO modify refresh km
				if (arrPromotionTypeBeans != null
						&& arrPromotionTypeBeans.size() > 0) {
					arrPromotionTypeBeans.clear();
					maKM = "-1";
					txtchonkm.setText(getString(R.string.chonctkm1));
					// initInitListPromotionNotData();
				}
				saleServiceId = "";
				stockModelId = "";
				stockTypeId = "";
				if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
					arrHthmBeans = new ArrayList<>();
					initHTHM();
					txththm.setText(getString(R.string.chonhthm));
				}
			}
		}

	}// onActi

	// init typepaper
	private void initTypePaper() {
		GetTypePaperDal dal = null;
		try {
			dal = new GetTypePaperDal(FragmentConnectionInfoSetting.this);
			dal.open();
			arrTypePaperBeans = dal.getLisTypepaper();
			dal.close();
			TypePaperBeans typePaperBeans1 = new TypePaperBeans();
			typePaperBeans1.setParType("");
			typePaperBeans1.setParValues(getString(R.string.hokhau));
			arrTypePaperBeans.add(typePaperBeans1);

			if (arrTypePaperBeans != null && !arrTypePaperBeans.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
					adapter.add(typePaperBeans.getParValues());
				}
				spinner_type_giay_to_parent.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		} finally {
			if (dal != null) {
				dal.close();
			}
		}
	}

	// init typepaper
	private void initTypePaperOffer() {
		GetTypePaperDal dal = null;
		try {
			dal = new GetTypePaperDal(FragmentConnectionInfoSetting.this);
			dal.open();
			arrTypePaperBeansOffer = dal.getLisTypepaper();
			dal.close();
			if (arrTypePaperBeansOffer != null
					&& !arrTypePaperBeansOffer.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        FragmentConnectionInfoSetting.this,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (TypePaperBeans typePaperBeans : arrTypePaperBeansOffer) {
					adapter.add(typePaperBeans.getParValues());
				}
				spinner_type_giay_to_offer.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		} finally {
			if (dal != null) {
				dal.close();
			}
		}
	}

	// ===========webservice danh sach ngan hang ===============

	public class GetBankAsyn extends AsyncTask<String, Void, ArrayList<Bank>> {

		final ProgressDialog progress;
		private Activity context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetBankAsyn(Activity context) {
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
		protected ArrayList<Bank> doInBackground(String... arg0) {
			return getListBank(arg0[0]);

		}

		@Override
		protected void onPostExecute(ArrayList<Bank> result) {

			// TODO get hinh thuc hoa mang
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrBanks = result;
					getBankBundleAdapter = new GetBankBundleAdapter(arrBanks,
							FragmentConnectionInfoSetting.this);
					lvListObjectCustomer.setAdapter(getBankBundleAdapter);
					getBankBundleAdapter.notifyDataSetChanged();

				} else {
					arrBanks = new ArrayList<>();

					if (getBankBundleAdapter != null) {
						getBankBundleAdapter.notifyDataSetChanged();
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, getResources()
									.getString(R.string.notnganhang),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					arrBanks = new ArrayList<>();

					if (getBankBundleAdapter != null) {
						getBankBundleAdapter.notifyDataSetChanged();
					}
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		// ==input String offerId, String serviceType, String province ,String
		// district, String precinct)
		private ArrayList<Bank> getListBank(String bankCode) {
			ArrayList<Bank> lstBank = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getBankByCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getBankByCode>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				String bankCodeUtf = convertToUTF8(bankCode);
				rawData.append("<bankCode>").append(bankCodeUtf);
				rawData.append("</bankCode>");
				rawData.append("</input>");
				rawData.append("</ws:getBankByCode>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getBankByCode");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);
				// ====== parse xml ===================

				GetBankHandler handler = (GetBankHandler) CommonActivity
						.parseXMLHandler(new GetBankHandler(), original);
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
				lstBank = handler.getLstData();
				// ====== parse xml ===================
			} catch (Exception e) {
				Log.d("getBankByCode", e.toString());
			}

			return lstBank;
		}
	}

	private LoadMoreListView lvListObjectCustomer;
    private EditText edtCusName;
	private EditText edtCusCode;
	private GetBankBundleAdapter getBankBundleAdapter = null;
	private ArrayList<Bank> arrBanks = new ArrayList<>();
	private Bank searchBank;

	private void showDialogListObjectCustommer() {
		final Dialog dialog = new Dialog(FragmentConnectionInfoSetting.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout_object_bank);
		lvListObjectCustomer = (LoadMoreListView) dialog
				.findViewById(R.id.lvListObjectCustomer);
        Button btnSearchCustomerObject = (Button) dialog.findViewById(R.id.btnSearch);
        Button btnDeleteCustomerObject = (Button) dialog.findViewById(R.id.btnDelete);
		edtCusName = (EditText) dialog.findViewById(R.id.edtNameCustomer);
		// edtCusName.setTypeface(Typeface.createFromAsset(
		// getAssets(), "fonts/TIMES.TTF"));
		edtCusCode = (EditText) dialog.findViewById(R.id.edtCodeCustomer);

		lvListObjectCustomer
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						searchBank = arrBanks.get(position);
						if (searchBank != null) {
							txtnganhang.setText(searchBank.getName());
						} else {
							txtnganhang.setText(getString(R.string.nganhang));
						}

						dialog.dismiss();
					}
				});

		lvListObjectCustomer
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

					@Override
					public void onLoadMore() {
						// TODO Auto-generated method stub
						Log.i(Constant.TAG, "lvListObjectCustomer onLoadMore");

						// AsyncContractFormMngt asynctaskContractFotmMngt = new
						// AsyncContractFormMngt(
						// searchBean, lstContractFormMngtBean.size(),
						// lstContractFormMngtBean.size() + Constant.PAGE_SIZE);
						// asynctaskContractFotmMngt.execute();
					}
				});

		btnSearchCustomerObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(v,
						FragmentConnectionInfoSetting.this);
				String customerName = edtCusName.getText().toString().trim();
				String customerCode = edtCusCode.getText().toString().trim();

				if (!CommonActivity.isNullOrEmpty(customerName)
						|| !CommonActivity.isNullOrEmpty(customerCode)) {
					if (CommonActivity
							.isNetworkConnected(FragmentConnectionInfoSetting.this)) {

						if (!CommonActivity.isNullOrEmpty(customerCode)
								&& !CommonActivity.isNullOrEmpty(customerName)) {
							GetBankAsyn getBankAsyn = new GetBankAsyn(
									FragmentConnectionInfoSetting.this);
							getBankAsyn.execute(customerCode);
						} else {
							if (!CommonActivity.isNullOrEmpty(customerCode)) {
								GetBankAsyn getBankAsyn = new GetBankAsyn(
										FragmentConnectionInfoSetting.this);
								getBankAsyn.execute(customerCode);
							} else {
								GetBankAsyn getBankAsyn = new GetBankAsyn(
										FragmentConnectionInfoSetting.this);
								getBankAsyn.execute(customerName);
							}
						}

					} else {
						CommonActivity.createAlertDialog(
								getApplicationContext(),
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				} else {
					CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									getString(R.string.message_not_input_customer_name_or_name),
									getString(R.string.app_name)).show();

				}
			}
		});

		btnDeleteCustomerObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchBank = null;
				txtnganhang.setText(getString(R.string.nganhang));
				dialog.dismiss();
			}
		});

        dialog.show();

	}

	private String convertToUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}

	// show dia log tim kiem hop dong
	private EditText edtsearch;
	private ListView lvContract;
	private GetContractAdapter getContractAdapter = null;
	private ArrayList<ConTractBean> arrTractBeansDialog = new ArrayList<>();

	private void showDialogSelectContract(
			final ArrayList<ConTractBean> arrConTractBeans) {
		final Dialog dialog = new Dialog(FragmentConnectionInfoSetting.this);

		arrTractBeansDialog = new ArrayList<>();
		arrTractBeansDialog = arrConTractBeans;
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_layout_lst_contract);
		dialog.setCancelable(false);
		edtsearch = (EditText) dialog.findViewById(R.id.edtsearch);
		lvContract = (ListView) dialog.findViewById(R.id.lvContract);

		getContractAdapter = new GetContractAdapter(arrTractBeansDialog,
				FragmentConnectionInfoSetting.this);
		lvContract.setAdapter(getContractAdapter);

		edtsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				if (getContractAdapter != null) {
					arrTractBeansDialog = getContractAdapter.SearchInput(input);
					lvContract.setAdapter(getContractAdapter);
					getContractAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		lvContract.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// neu chon hop dong moi
				conTractBean = arrTractBeansDialog.get(arg2);
				txthopdong.setText(conTractBean.getContractNo());

				if (!isFirst) {
					AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(
							FragmentConnectionInfoSetting.this, TYPE_HTTTHD);
					asyntaskGetListAllCommon.execute();
					if (custommer.getCustomerAttribute() != null
							&& !CommonActivity.isNullOrEmpty(custommer
									.getCustomerAttribute().getIdNo())) {
						GetListCusGroupAsyn getListCusGroupAsyn = new GetListCusGroupAsyn(
								FragmentConnectionInfoSetting.this);
						getListCusGroupAsyn.execute();
					}

				}

				if (conTractBean != null && arg2 > 0) {
					if (!CommonActivity.isNullOrEmpty(conTractBean
							.getContractId())) {
						reloadHDOld(conTractBean);
						GetContractOfferByContractIdAsynTask getContractOfferByContractIdAsynTask = new GetContractOfferByContractIdAsynTask(
								FragmentConnectionInfoSetting.this);
						getContractOfferByContractIdAsynTask
								.execute(conTractBean.getContractId());

					} else {

					}
				} else {

					if (conTractBean != null
							&& !CommonActivity.isNullOrEmpty(conTractBean
									.getContractId())) {
						reloadHDOld(conTractBean);
						GetContractOfferByContractIdAsynTask getContractOfferByContractIdAsynTask = new GetContractOfferByContractIdAsynTask(
								FragmentConnectionInfoSetting.this);
						getContractOfferByContractIdAsynTask
								.execute(conTractBean.getContractId());
					} else {
						conTractBean = new ConTractBean();

						arrContractOffersObj = new ArrayList<>();
						ContractOffersObj conOffersObj = new ContractOffersObj();
						conOffersObj.setContractOfferId("");
						conOffersObj
								.setContractOfferNo(getString(R.string.phulucmoi));
						arrContractOffersObj.add(0, conOffersObj);

						if (arrContractOffersObj != null
								&& arrContractOffersObj.size() > 0) {
							ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    FragmentConnectionInfoSetting.this,
                                    android.R.layout.simple_dropdown_item_1line,
                                    android.R.id.text1);
							for (ContractOffersObj contractOffersObj : arrContractOffersObj) {
								adapter.add(contractOffersObj
										.getContractOfferNo());
							}
							spinner_plhopdong.setAdapter(adapter);

							if (arrContractOffersObj.size() > 1) {
								lnsophuluc.setVisibility(View.VISIBLE);
							} else {
								lnsophuluc.setVisibility(View.GONE);
							}
						}

						reloadHDNew();
					}

				}

				dialog.dismiss();

			}
		});

		dialog.show();

	}

	public class GetListCustomerAsyn extends
			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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
			return getListCustomerIdNo(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				lngiaytoxacminh.setVisibility(View.VISIBLE);
				findViewById(R.id.btnloaikh).setVisibility(View.VISIBLE);
				findViewById(R.id.btnnhomkh).setVisibility(View.VISIBLE);
				if (result != null && result.size() > 0) {
					showDiaLogSelectCus(result);
					// arrCustommerByIdNoBeans = result;
					// adaGetListCusAdapter = new GetListCusAdapter(
					// arrCustommerByIdNoBeans, getActivity());
					// lvCustomer.setAdapter(adaGetListCusAdapter);
					// btnnhapmoi.setVisibility(View.GONE);

					// show len popup chon khach hang

				} else {
					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								FragmentConnectionInfoSetting.this,
								description,
								getResources().getString(R.string.app_name));
						dialog.show();
					} else {

						Dialog dialog = CommonActivity.createAlertDialog(
								FragmentConnectionInfoSetting.this,
								getResources().getString(R.string.notkhCD),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

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
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<CustommerByIdNoBean> getListCustomerIdNo(String idNo) {
			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<>();
			String original = null;
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustomerByIdNo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerByIdNo>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				if (idNo != null && !idNo.isEmpty()) {
					rawData.append("<idNo>").append(idNo);
					rawData.append("</idNo>");
				}
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getCustomerByIdNo>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getCustomerByIdNo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);
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
						String idType = parse.getValue(e1, "idType");
						custommerByIdNoBean.setIdType(idType);
						String tin = parse.getValue(e1, "tin");
						custommerByIdNoBean.setTin(tin);
						custommerByIdNoBean.setIdNo(idNoCus);
						String busPermitNo1 = parse.getValue(e1, "busPermitNo");
						Log.d("busPermitNo1", busPermitNo1);
						custommerByIdNoBean.setBusPermitNo(busPermitNo1);
						String birthDate = parse.getValue(e1, "birthDate");
						Log.d("birthDate", birthDate);
						custommerByIdNoBean.setBirthdayCus(StringUtils
								.convertDate(birthDate));

						String busType = parse.getValue(e1, "busType");
						Log.d("busType", busType);
						custommerByIdNoBean.setCusType(busType);

						String custGroupId = parse.getValue(e1, "custGroupId");
						Log.d("custGroupId", custGroupId);
						custommerByIdNoBean.setCusGroupId(custGroupId);
						String idIssueDate = parse.getValue(e1, "idIssueDate");
						custommerByIdNoBean.setIdIssueDate(StringUtils
								.convertDate(idIssueDate));
						String idExpireDate = parse
								.getValue(e1, "idExpireDate");
						custommerByIdNoBean.setIdExpireDate(idExpireDate);

						String idIssuePlace = parse
								.getValue(e1, "idIssuePlace");
						custommerByIdNoBean.setIdIssuePlace(idIssuePlace);

						String nationality = parse.getValue(e1, "nationality");
						custommerByIdNoBean.setNationality(nationality);

						String sex = parse.getValue(e1, "sex");
						custommerByIdNoBean.setSex(sex);
						// "sex",
						// "nationality",

						lisCustommerByIdNoBeans.add(custommerByIdNoBean);

					}
					return lisCustommerByIdNoBeans;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lisCustommerByIdNoBeans;
		}
	}

    private CustommerByIdNoBean custommerRepre = null;

	// man hinh show thong tin chon khach hang cu
	private void showDiaLogSelectCus(
			final ArrayList<CustommerByIdNoBean> arrCustommerByIdNoBeans) {
		final Dialog dialogCus = new Dialog(FragmentConnectionInfoSetting.this);
		dialogCus.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogCus.setContentView(R.layout.connection_layout_select_customer);
		dialogCus.setCancelable(false);
		ListView listcustomer = (ListView) dialogCus
				.findViewById(R.id.listcustomer);

        GetListCusAdapter adaGetListCusAdapter = new GetListCusAdapter(arrCustommerByIdNoBeans,
                FragmentConnectionInfoSetting.this);
		listcustomer.setAdapter(adaGetListCusAdapter);
		listcustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				custommerRepre = arrCustommerByIdNoBeans.get(arg2);
				lngiaytoxacminh.setVisibility(View.VISIBLE);

				if (custommerRepre != null) {
					// selection idtype
					// if(!CommonActivity.isNullOrEmpty(custommerRepre.getIdType())){
					if (arrTypePaperBeans != null
							&& !arrTypePaperBeans.isEmpty()) {
						for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
							if (custommerRepre.getIdType().equals(
									typePaperBeans.getParType())) {
								spinner_type_giay_to_parent
										.setSelection(arrTypePaperBeans
												.indexOf(typePaperBeans));
								break;
							}
						}
					}
					// }
					spinner_type_giay_to_parent.setEnabled(false);
					edit_ngaycap.setText(custommerRepre.getIdIssueDate());
					edit_ngaycap.setEnabled(false);

					edtnoicap.setText(custommerRepre.getIdIssuePlace());
					edtnoicap.setEnabled(false);

					edit_ngayhethan.setText(custommerRepre.getIdExpireDate());
					edit_ngayhethan.setEnabled(false);

					txtdcgtxm.setText(custommerRepre.getAddreseCus());
					txtdcgtxm.setEnabled(false);

					if (!CommonActivity.isNullOrEmpty(custommerRepre
							.getCusGroupId())) {

						if (arrCustomerGroupBeans != null
								&& !arrCustomerGroupBeans.isEmpty()) {
							findViewById(R.id.btnnhomkh).setVisibility(
									View.GONE);
							for (CustomerGroupBeans customerGroupBeans : arrCustomerGroupBeans) {
								if (custommerRepre.getCusGroupId().equals(
										customerGroupBeans.getIdCusGroup())) {
									spinner_nhomkh.setSelection(arrCustomerGroupBeans
											.indexOf(customerGroupBeans));
									break;
								}
							}
						}
					}
					spinner_nhomkh.setEnabled(false);
					if (!CommonActivity.isNullOrEmpty(custommerRepre
							.getCusType())) {
						if (arrCustomerTypeByCustGroupBeans != null
								&& !arrCustomerTypeByCustGroupBeans.isEmpty()) {
							findViewById(R.id.btnloaikh).setVisibility(
									View.GONE);
							for (CustomerTypeByCustGroupBeans customerTypeByCustGroupBeans : arrCustomerTypeByCustGroupBeans) {

								if (custommerRepre.getCusType()
										.equalsIgnoreCase(
												customerTypeByCustGroupBeans
														.getCode())) {
									spinner_loaikh
											.setSelection(arrCustomerTypeByCustGroupBeans
													.indexOf(customerTypeByCustGroupBeans));
									break;
								}

							}

						}
					}
					edit_tenKHDD.setText(custommerRepre.getNameCustomer());
					edit_tenKHDD.setEnabled(false);
					spinner_loaikh.setEnabled(false);

					spinner_lvhd.setEnabled(false);

					edtmaKHCD.setText(custommerRepre.getCustId());
					edtmaKHCD.setEnabled(false);

					edit_ngaysinhd.setText(custommerRepre.getBirthdayCus());
					edit_ngaysinhd.setEnabled(false);

					if (!CommonActivity.isNullOrEmpty(custommerRepre.getSex())) {
						if (listSex != null && !listSex.isEmpty()) {
							for (AreaObj sex : listSex) {
								if (custommerRepre.getSex().equals(
										sex.getAreaCode())) {
									spinner_gioitinh.setSelection(listSex
											.indexOf(sex));
									break;
								}
							}
						}
					}
					spinner_gioitinh.setEnabled(false);
					edit_quoctich.setText(custommerRepre.getNationality());
					edit_quoctich.setEnabled(false);
					edit_sogiaytoDD.setEnabled(false);

					btnkiemtra.setVisibility(View.GONE);
					btnedit.setVisibility(View.VISIBLE);

					// edit_quoctich.setText(custommerRepre.)

				}

				dialogCus.dismiss();
			}
		});

		dialogCus.findViewById(R.id.btncancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogCus.dismiss();
					}
				});

		dialogCus.show();

	}

	// private void hideProgress(int type){
	// findViewById(R.id.prbloaihopdong).setVisibility(View.GONE);
	// find
	// // private int TYPE_HTTHHD = 1;
	// // private int TYPE_LOAI_HD = 2;
	// // private int TYPE_CK_CUOC_HD = 3;
	// // private int TYPE_INCT_HD = 4;
	// // private int TYPE_HTTBC_HD = 5;
	// // private int TYPE_TTBS_HD = 6;
	// switch (type) {
	// case TYPE_HTTHHD:
	// fin
	// break;
	//
	// default:
	// break;
	// }
	// }
	private void hideBtnRefresh() {
		findViewById(R.id.btnloaihopdong).setVisibility(View.GONE);
		findViewById(R.id.btnhttthd).setVisibility(View.GONE);
		findViewById(R.id.btnchukicuoc).setVisibility(View.GONE);
		findViewById(R.id.btninchitiet).setVisibility(View.GONE);
		findViewById(R.id.btnhttbc).setVisibility(View.GONE);
		findViewById(R.id.btnttbosung).setVisibility(View.GONE);
		findViewById(R.id.btnnhomkh).setVisibility(View.GONE);
		findViewById(R.id.btnloaikh).setVisibility(View.GONE);
		findViewById(R.id.btnlvhd).setVisibility(View.GONE);
		// findViewById(R.id.btnSubGroup).setVisibility(View.GONE);
		// findViewById(R.id.btnSubType).setVisibility(View.GONE);
	}

	private void showBtnRefresh() {
		findViewById(R.id.btnloaihopdong).setVisibility(View.VISIBLE);
		findViewById(R.id.btnhttthd).setVisibility(View.VISIBLE);
		findViewById(R.id.btnchukicuoc).setVisibility(View.VISIBLE);
		findViewById(R.id.btninchitiet).setVisibility(View.VISIBLE);
		findViewById(R.id.btnhttbc).setVisibility(View.VISIBLE);
		findViewById(R.id.btnttbosung).setVisibility(View.VISIBLE);
		findViewById(R.id.btnnhomkh).setVisibility(View.VISIBLE);
		findViewById(R.id.btnloaikh).setVisibility(View.VISIBLE);
		findViewById(R.id.btnlvhd).setVisibility(View.VISIBLE);
		findViewById(R.id.btnSubGroup).setVisibility(View.VISIBLE);
		findViewById(R.id.btnSubType).setVisibility(View.VISIBLE);
	}

	// lay thong tin cuoc dong truoc
	private class GetAllListPaymentPrePaidAsyn extends
			AsyncTask<String, Void, ArrayList<PaymentPrePaidPromotionBeans>> {

		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetAllListPaymentPrePaidAsyn(Context context) {
			this.context = context;

			findViewById(R.id.prbCuocdongtruoc).setVisibility(View.VISIBLE);

		}

		@Override
		protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(
				String... arg0) {
			return getAllListPaymentPrePaid(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(
				ArrayList<PaymentPrePaidPromotionBeans> result) {

			findViewById(R.id.prbCuocdongtruoc).setVisibility(View.GONE);
			arrPaymentPrePaidPromotionBeans = new ArrayList<>();
			if (errorCode.equals("0")) {

				PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
				paymentPrePaidPromotionBeans
						.setName(getString(R.string.txt_select));
				paymentPrePaidPromotionBeans.setPrePaidCode("");
				result.add(0, paymentPrePaidPromotionBeans);
				arrPaymentPrePaidPromotionBeans.addAll(result);
				if (arrPaymentPrePaidPromotionBeans != null
						&& !arrPaymentPrePaidPromotionBeans.isEmpty()) {
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FragmentConnectionInfoSetting.this,
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
						adapter.add(typePaperBeans.getName());
					}
					spinner_cuocdongtruoc.setAdapter(adapter);
				}
				btnkyhopdong.setVisibility(View.VISIBLE);

			} else {

				btnkyhopdong.setVisibility(View.GONE);

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
					ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FragmentConnectionInfoSetting.this,
                            android.R.layout.simple_dropdown_item_1line,
                            android.R.id.text1);
					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
						adapter.add(typePaperBeans.getName());
					}
					spinner_cuocdongtruoc.setAdapter(adapter);
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}

			}

		}

		private ArrayList<PaymentPrePaidPromotionBeans> getAllListPaymentPrePaid(
				String promProgramCode, String packageId, String provinceCode,
				String today) {
			ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans = new ArrayList<>();
			String original = null;
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getAllListPaymentPrePaid");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAllListPaymentPrePaid>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<promProgramCode>").append(promProgramCode);
				rawData.append("</promProgramCode>");

				rawData.append("<packageId>").append(packageId);
				rawData.append("</packageId>");

				rawData.append("<provinceCode>").append(provinceCode);
				rawData.append("</provinceCode>");

				rawData.append("<today>").append(today);
				rawData.append("</today>");

				rawData.append("</input>");
				rawData.append("</ws:getAllListPaymentPrePaid>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getAllListPaymentPrePaid");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodepaymentPrePaidPromotionBeans = null;
				NodeList nodePaymentPrePaidDetailBeans = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodepaymentPrePaidPromotionBeans = e2
							.getElementsByTagName("paymentPrePaidPromotionBeans");
					for (int j = 0; j < nodepaymentPrePaidPromotionBeans
							.getLength(); j++) {
						Element e1 = (Element) nodepaymentPrePaidPromotionBeans
								.item(j);
						PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
						String name = parse.getValue(e1, "name");
						paymentPrePaidPromotionBeans.setName(name);

						String prePaidCode = parse.getValue(e1, "prePaidCode");
						paymentPrePaidPromotionBeans
								.setPrePaidCode(prePaidCode);

						ArrayList<PaymentPrePaidDetailBeans> lstPaymentPrePaidDetailBeans = new ArrayList<>();

						nodePaymentPrePaidDetailBeans = e1
								.getElementsByTagName("paymentPrePaidDetailBeans");
						for (int k = 0; k < nodePaymentPrePaidDetailBeans
								.getLength(); k++) {
							Element e0 = (Element) nodePaymentPrePaidDetailBeans
									.item(k);
							PaymentPrePaidDetailBeans paymentPrePaidDetailBeans = new PaymentPrePaidDetailBeans();
							String moneyUnit = parse.getValue(e0, "moneyUnit");
							paymentPrePaidDetailBeans.setMoneyUnit(moneyUnit);
							String promAmount = parse
									.getValue(e0, "promAmount");
							paymentPrePaidDetailBeans.setPromAmount(promAmount);
							String endMonth = parse.getValue(e0, "endMonth");
							paymentPrePaidDetailBeans.setEndMonth(endMonth);
							String startMonth = parse
									.getValue(e0, "startMonth");
							paymentPrePaidDetailBeans.setStartMonth(startMonth);
							String subMonth = parse.getValue(e0, "subMonth");
							paymentPrePaidDetailBeans.setSubMonth(subMonth);
							lstPaymentPrePaidDetailBeans
									.add(paymentPrePaidDetailBeans);
						}

						paymentPrePaidPromotionBeans
								.setLstDetailBeans(lstPaymentPrePaidDetailBeans);

						lstPaymentPrePaidPromotionBeans
								.add(paymentPrePaidPromotionBeans);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstPaymentPrePaidPromotionBeans;
		}

	}

	private Dialog dialogCuocdongtruoc = null;

	private void showSelectCuocDongTruoc(
			PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans) {

		dialogCuocdongtruoc = new Dialog(FragmentConnectionInfoSetting.this);
		dialogCuocdongtruoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogCuocdongtruoc
				.setContentView(R.layout.connection_layout_detail_precharge);

		ListView listdetail = (ListView) dialogCuocdongtruoc
				.findViewById(R.id.listdetail);

		EditText txttencuocdongtruoc = (EditText) dialogCuocdongtruoc
				.findViewById(R.id.txttencuocdongtruoc);
		txttencuocdongtruoc.setText(paymentPrePaidPromotionBeans.getName());

		EditText txtmacuocdongtruoc = (EditText) dialogCuocdongtruoc
				.findViewById(R.id.txtmacuocdongtruoc);
		txtmacuocdongtruoc.setText(paymentPrePaidPromotionBeans
				.getPrePaidCode());
		GetListPaymentDetailChargeAdapter getListPaymentDetailChargeAdapter = new GetListPaymentDetailChargeAdapter(
				paymentPrePaidPromotionBeans.getLstDetailBeans(),
				FragmentConnectionInfoSetting.this);
		listdetail.setAdapter(getListPaymentDetailChargeAdapter);

		Button btnchon = (Button) dialogCuocdongtruoc
				.findViewById(R.id.btnchon);
		btnchon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogCuocdongtruoc.dismiss();
			}
		});

		dialogCuocdongtruoc.show();

	}

	// lay danh sach khuyen mai trai nghiem
	private class AsynGetPromotionUnit extends
			AsyncTask<String, Void, ArrayList<Spin>> {

		private Context mContext = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynGetPromotionUnit(Context context) {
			this.mContext = context;
			findViewById(R.id.prbProunit).setVisibility(View.VISIBLE);

		}

		@Override
		protected ArrayList<Spin> doInBackground(String... arg0) {
			return getLstPromotionUnit(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			super.onPostExecute(result);
			findViewById(R.id.prbProunit).setVisibility(View.GONE);
			arrPromotionUnit = new ArrayList<>();
			if (errorCode.equalsIgnoreCase("0")) {
				arrPromotionUnit.addAll(result);
				Spin spin = new Spin();
				spin.setValue(getString(R.string.txt_select));
				spin.setId("");
				arrPromotionUnit.add(0, spin);

				Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
						arrPromotionUnit, spinner_kmtrainghiem);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN)) {
					result = new ArrayList<>();

					Dialog dialog = CommonActivity
							.createAlertDialog(
									FragmentConnectionInfoSetting.this,
									description, mContext.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}
					arrPromotionUnit = new ArrayList<>();
					Utils.setDataSpinner(FragmentConnectionInfoSetting.this,
							arrPromotionUnit, spinner_kmtrainghiem);

					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		// lay danh sach loai hop dong
		private ArrayList<Spin> getLstPromotionUnit(String province,
				String productCode) {
			ArrayList<Spin> lstPromotionunit = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getPromotionsUnit");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getPromotionsUnit>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<productCode>").append(productCode).append("</productCode>");
				rawData.append("<province>").append(province).append("</province>");
				rawData.append("</input>");
				rawData.append("</ws:getPromotionsUnit>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getPromotionsUnit");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				// parser

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstPromotionType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setValue(parse.getValue(e1, "codeName"));

						spin.setId(parse.getValue(e1, "promProgramCode"));

						lstPromotionunit.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstPromotionunit;
		}

	}

	private void validateInputSignContract() {

		if (!maKM.equals("-1")) {
			if (editisdnoracc.getText().toString() != null
					&& !editisdnoracc.getText().toString().isEmpty()) {
				// validate NGN modify ngay
				// 13/11/2015
				if ("N".equalsIgnoreCase(serviceType)
						&& "4".equalsIgnoreCase(techology)) {

					if (!CommonActivity.isNullOrEmpty(editisdnoraccPPPOE
							.getText().toString().trim())
							&& !CommonActivity.isNullOrEmpty(editmatkhauPPPOE
									.getText().toString().trim())) {
						checkHangHoa();
					} else {
						CommonActivity.createAlertDialog(
								FragmentConnectionInfoSetting.this,
								getString(R.string.checkAccPPOE),
								getString(R.string.app_name)).show();

					}

				} else {
					checkHangHoa();
				}

				// checkInputSignContract();
			} else {
				Toast.makeText(FragmentConnectionInfoSetting.this,
						getResources().getString(R.string.checkaccorisdn),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(FragmentConnectionInfoSetting.this,
					getResources().getString(R.string.checkselectmakm),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void setDCTBC(Intent data) {

		if (data != null) {
			areaProvicial = (AreaObj) data.getExtras().getSerializable(
					"areaProvicial");
			areaDistrist = (AreaObj) data.getExtras().getSerializable(
					"areaDistrist");
			areaPrecint = (AreaObj) data.getExtras().getSerializable(
					"areaPrecint");
			areaGroup = (AreaObj) data.getExtras().getSerializable("areaGroup");

			areaFlow = data.getExtras().getString("areaFlow");
			areaHomeNumber = data.getExtras().getString("areaHomeNumber");
			address = new StringBuilder();
			if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
				address.append(areaHomeNumber).append(" ");
			}
			if (areaFlow != null && areaFlow.length() > 0) {
				address.append(areaFlow).append(" ");
			}
			if (areaGroup != null && areaGroup.getName() != null
					&& areaGroup.getName().length() > 0) {
				address.append(areaGroup.getName()).append(" ");
			}
			if (areaPrecint != null && areaPrecint.getName() != null
					&& areaPrecint.getName().length() > 0) {

				address.append(areaPrecint.getName()).append(" ");
			}
			if (areaDistrist != null && areaDistrist.getName() != null
					&& areaDistrist.getName().length() > 0) {

				address.append(areaDistrist.getName()).append(" ");
			}
			if (areaProvicial != null && areaProvicial.getName() != null
					&& areaProvicial.getName().length() > 0) {

				address.append(areaProvicial.getName());
			}
			txtdctbc.setText(address);
			edtdchdcuoc.setText(address);
			Log.d("Log", "Check edit address null: " + txtdctbc + "adess :"
					+ address);
		} else {
			AreaDal dal = null;
			try {

				String province = FragmentInfoCustomer.custommer.getProvince();
				String district = FragmentInfoCustomer.custommer.getDistrict();
				String precinct = FragmentInfoCustomer.custommer.getPrecint();
				String streetBlock = FragmentInfoCustomer.custommer
						.getStreet_block();
				if (CommonActivity.isNullOrEmptyArray(province, district,
						precinct, streetBlock)) {
					return;
				}
				dal = new AreaDal(this);
				dal.open();
				String streetName = FragmentInfoCustomer.custommer.getStreet();
				String home = FragmentInfoCustomer.custommer.getHome();

				areaProvicial = dal.getArea(province);
				areaDistrist = dal.getArea(province + district);
				areaPrecint = dal.getArea(province + district + precinct);
				areaGroup = new AreaObj();
				areaGroup.setAreaCode(streetBlock);
				areaGroup.setName(streetBlock);

				areaFlow = streetName;
				areaHomeNumber = home;
				txtdctbc.setText(FragmentInfoCustomer.custommer.getAddreseCus());
				edtdchdcuoc.setText(FragmentInfoCustomer.custommer
						.getAddreseCus());
			} catch (

			Exception e) {
				Log.e("Error", e.toString(), e);

			} finally {
				if (dal != null) {
					dal.close();
				}
			}
		}
	}

	// lay danh sach dat coc
	private class GetReasonFullInfoAsyn extends
			AsyncTask<String, Void, List<ReasonPledgeDTO>> {

		private final Context context;
		private String errorCode = "";
		private String description = "";

		public GetReasonFullInfoAsyn(Context mContext) {
			this.context = mContext;
			prbdeposit.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<ReasonPledgeDTO> doInBackground(String... arg0) {
			return getReasonFullInfo(arg0[0]);
		}

		@Override
		protected void onPostExecute(List<ReasonPledgeDTO> result) {
			super.onPostExecute(result);
			prbdeposit.setVisibility(View.GONE);
			if ("0".equalsIgnoreCase(errorCode)) {
				if (!CommonActivity.isNullOrEmpty(result)) {
					ReasonPledgeDTO reasonPledgeDTO = new ReasonPledgeDTO();
					reasonPledgeDTO.setNumMonth(getResources().getString(
							R.string.txt_select));
					reasonPledgeDTO.setNumMoney("");
					result.add(0, reasonPledgeDTO);
					Utils.setDataPlege(FragmentConnectionInfoSetting.this,
							result, spinner_deposit);
					lndatcoc.setVisibility(View.VISIBLE);
				} else {
					lndatcoc.setVisibility(View.GONE);
					result = new ArrayList<>();
					Utils.setDataPlege(FragmentConnectionInfoSetting.this,
							result, spinner_deposit);

					CommonActivity.createAlertDialog(
							FragmentConnectionInfoSetting.this,
							getResources().getString(R.string.notdatadeposit),
							getResources().getString(R.string.app_name)).show();
				}

			} else {
				if (Constant.INVALID_TOKEN.equals(errorCode)
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
							FragmentConnectionInfoSetting.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private List<ReasonPledgeDTO> getReasonFullInfo(String reasonId) {

			String original = null;
			List<ReasonPledgeDTO> lsReasonPledgeDTOs = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getReasonFullInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReasonFullInfo>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<reasonId>").append(reasonId);
				rawData.append("</reasonId>");

				rawData.append("</input>");
				rawData.append("</ws:getReasonFullInfo>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,
						FragmentConnectionInfoSetting.this,
						"mbccs_getReasonFullInfo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();

					if (parseOuput.getLstReasonPledgeDTOs() == null) {
						lsReasonPledgeDTOs = new ArrayList<>();
					} else {
						lsReasonPledgeDTOs = parseOuput
								.getLstReasonPledgeDTOs();
					}

				}

				return lsReasonPledgeDTOs;

			} catch (Exception e) {
				Log.d("getReasonFullInfo", e.toString());
			}

			return null;
		}

	}

	private void initProvince() {
		try {
			GetAreaDal dal = new GetAreaDal(getApplicationContext());
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
			GetAreaDal dal = new GetAreaDal(getApplicationContext());
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
			GetAreaDal dal = new GetAreaDal(getApplicationContext());
			dal.open();
			arrPrecinct = dal.getLstPrecinct(province, district);
			dal.close();
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
		}
	}
}