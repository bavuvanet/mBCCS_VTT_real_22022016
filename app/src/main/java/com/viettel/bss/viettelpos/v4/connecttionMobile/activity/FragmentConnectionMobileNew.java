package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.ActivitySelectCustomer;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchPakageMobileBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.AdapterSpinerTTHHBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetListCustomerBccsAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.ThongTinHHAdapterBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.ThongTinHHAdapterBCCS.OnChangeSpinerProduct;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValueV2;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.DoUHaveMoreThan3SubAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountBankDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CategoryTBBundeBeanBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchCategoryTBBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PakageBundeBeanBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StockNumberDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerObjectAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.GetInfoPackageAsyn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.commons.auto.template.ActivityTemplate;
import com.viettel.bss.viettelpos.v4.commons.auto.template.CallbackObj;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter.OnChangeCheckedSub;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetSerialAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter.ViewHolder;
import com.viettel.bss.viettelpos.v4.connecttionMobile.asynctask.GetSubPreChargeAVGBySubIdAndNumMonthAsyncTask;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ContractOffersObj;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.HTHMMobileHandler;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.ProductOfferringHanlder;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.SubscriberDTOHanlder;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManageConnect;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListPaymentDetailChargeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PriceBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.StockIsdnBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TypePaperBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetTypePaperDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.manage.AsyncTaskUpdateImageOfline;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SpinV2;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.adapter.DoiTuongObj;
import com.viettel.bss.viettelpos.v4.customview.adapter.DonViAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.ObjDoiTuongAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.UploadImageAdapter;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.customview.obj.SpecObject;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.object.AddInfo;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.PlaceOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.ui.CustomEditText;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.NDSpinner;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharUseDTO;
import com.viettel.savelog.SaveLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.isDefault;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class FragmentConnectionMobileNew extends Fragment
        implements OnClickListener, OnItemClickListener, OnChangeSpinerProduct, OnChangeCheckedSub, AutoConst, View.OnTouchListener {

    // bo sung luong khuyen mai phi cao
    private Double avgCharge;

    private final String logTag = FragmentConnectionMobileNew.class.getName();

    // define resource view
    private EditText spinner_loaithuebao;// , spinner_loaihd;
    private NDSpinner spinner_loaihd;

    private Spinner spinner_serviceMobile;
    private EditText txtisdn, txtserial, txtimsi, txtmanv;
    private ExpandableHeightListView lvproduct;

    private LinearLayout lnkhuyenmai, lnloaithuebao, lnhopdong, lnMaNV;
    public static LinearLayout lnButton;
    private Button btn_connection;
    private TextView txtpakage;
    private Spinner spinnerCustomerInfo;
    // get data service
    private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
    private String serviceType = "";
    private String telecomserviceId = "";
    private ArrayAdapter<String> adapterService = null;
    // arraylist pakage
    private ArrayList<ProductOfferingDTO> arrPakageChargeBeans = new ArrayList<ProductOfferingDTO>();
    public static Map<String, ArrayList<ProductOfferingDTO>> mapPakage = new HashMap<String, ArrayList<ProductOfferingDTO>>();
    private String offerId = "";
    private String productCode = "";
    // arrlist HTHM
    private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<HTHMBeans>();
    private String hthm = "";
    // arrlist htkms
    private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
    private ArrayAdapter<String> adapterHTKM;
    private String maKM = "";
    // arrlist province
    private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();
    private String province = "";
    // arrlist district
    private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
    private String district = "";
    // arrlist precinct
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();
    private String precinct = "";

    private ArrayList<SubTypeBeans> arrSubTypeBeans = new ArrayList<SubTypeBeans>();
    private String subTypeMobile = "";
    // ====== arrlist ========
    private ArrayList<AccountDTO> arrTractBeans = new ArrayList<AccountDTO>();
    private String contractNo = "";
    private String contractId = "";
    // arrlist ds hang hoa
    private ArrayList<ProductOfferTypeDTO> arrStockTypeBeans = new ArrayList<ProductOfferTypeDTO>();
    private ThongTinHHAdapterBCCS adapProductAdapter;

    private ArrayList<FileObj> arrFileBackUp;

    // ==========map hang hoa =================
    private Map<String, ProductOfferingDTO> mapSubStockModelRelReq = new HashMap<String, ProductOfferingDTO>();

    // ======== show dialog insert Serial
    private Dialog dialogInsertSerial;
    private Dialog dialogShowDetailProduct;
    EditText edtserial;
    private Bundle mBundle;
    public static String subType = "";

    private PakageBundeBeanBCCS pakageBundeBean = new PakageBundeBeanBCCS();
    private View mView;
    private ProductOfferingDTO pakaChargeBeans = null;
    private CustIdentityDTO custIdentityDTO = new CustIdentityDTO();
    public static CustIdentityDTO custIdentityDTOCheck = new CustIdentityDTO();
    public static String subTypeCheck = "";
    private int positonservice = -1;
    private String isdn = "";

    private String tenduong = "";
    private String sonha = "";
    private String to = "";

    // define view form specical paper
    private LinearLayout lnTTGoiCuocDacBiet;
    private View viewSpec, viewSpec1;
    private Spinner spDoiTuong;
    private EditText edtQuocTich, tvDonVi, edtMaGiayToDacBiet, edtNgayBD, edtNgayKT;

    private ArrayList<SpecObject> arrSpecObjects = new ArrayList<SpecObject>();
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
    private LinearLayout rlchondonvi;

    private ArrayList<DoiTuongObj> mAratListDonVi = new ArrayList<DoiTuongObj>();
    private ListView lvListDonVi;
    private Dialog dialogDonVi = null;

    private BCCSGateway mBccsGateway = new BCCSGateway();

    private String dateNowString = null;
    private Date dateBD = null;
    private Date dateNow = null;
    private Date dateEnd = null;
    private View imgDeleteDV;

    private ListView lvUploadImage;
    private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<String, ArrayList<FileObj>>();
    private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;

    boolean isUpImage = false;

    private ArrayList<AreaObj> arrTo = new ArrayList<AreaObj>();
    private ArrayList<AreaObj> arrToDialog = new ArrayList<AreaObj>();
    private UploadImageAdapter adapter;

    private String reasonId;
    private Activity activity;

    private Date timeStart = null;
    private Date timeEnd = null;

    private EditText edt_hthm, edt_kmai;
    private HTHMBeans hthmBeans;

    private CheckBox cb_quota;
    private LinearLayout ln_quota;

    private ArrayList<ContractOffersObj> arrContractOffersObj = new ArrayList<ContractOffersObj>();

    private ProgressBar prb_contract;

    public static FragmentConnectionMobileNew instance = null;

    private Spinner spinner_cuocdongtruoc;
    private LinearLayout lncuocdongtruoc;
    private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
    private String prepaidCode = "-1";
    private String prepaidId = "";
    // bo sung chon serial
    // private static List<StockModel> mListStockModel = new
    // ArrayList<StockModel>();
    private List<StockModel> mListStockModel = new ArrayList<StockModel>();
    private GetSerialAdapter getSerialAdapter = null;

    private String saleServiceCode;

    private TextView tvcamketso;
    private Button btnRefreshStreetBlock;
    private Button btnRefreshStreetBlockDialog;

    private EditText edtdiachilapdat;
    private AreaObj areaProvicialPR;
    private AreaObj areaDistristPR;
    private AreaObj areaPrecintPR;
    private AreaObj areaGroupPR;
    private String areaFlowPR;
    private String areaHomeNumberPR;
    private StringBuilder addressPR;

    // thong tin han muc va dat con
    private LinearLayout lnquotaanddeposit;
    private Spinner spinner_quota, spinner_deposit;
    private ProgressBar prbqouta, prbdeposit;
    private Button btnRefreshqouta, btndeposit;

    private ArrayList<SexBeans> arrSexBeans = new ArrayList<SexBeans>();
    private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<CustIdentityDTO>();

    private ArrayList<CustTypeDTO> arrCusTypeDTO = new ArrayList<CustTypeDTO>();

    private AccountDTO accountDTO = new AccountDTO();

    // Contract

    private AreaObj areaProvicialContract;
    private AreaObj areaDistristContract;
    private AreaObj areaPrecintContract;
    private AreaObj areaGroupContract;
    private String areaFlowContract;
    private String areaHomeNumberContract;
    private StringBuilder addressContract;
    private String provinceContract;
    private String districtContract;
    private String precintContract;
    private String streetBlockContract;
    private String streetNameContract;
    private String homeContract;

    // dai dien hop dong
    private AreaObj areaProvicialContractPR;
    private AreaObj areaDistristContractPR;
    private AreaObj areaPrecintContractPR;
    private AreaObj areaGroupContractPR;
    private String areaFlowContractPR;
    private String areaHomeNumberContractPR;
    private StringBuilder addressContractPR;
    private String provinceContractPR;
    private String districtContractPR;
    private String precintContractPR;
    private String streetBlockContractPR;
    private String streetNameContractPR;
    private String homeContractPR;

    // doi tuong hop dong truyen doi voi dau noi mobile tra sau
    private AccountDTO accountDTOMain;

    private AccountDTO accountDTOMainNew;

    private CustTypeDTO custTypeDTOContractPR = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String typePaperDialogPR;

    private String checkPCProduct = "";

    // thong tin khach hang dai dien
    private ArrayList<CustIdentityDTO> arrCustIdentityDTODialog = new ArrayList<CustIdentityDTO>();
    private GetListCustomerBccsAdapter adaGetListCustomerBccsAdapterDialog;
    // private CustomerDTO customerDTODialog = null;
    private CustIdentityDTO repreCustomer;

    // chon thong tin khach hang cu cho nguoi dai dien khach hang doanh nghiep
    private Dialog dialogCus;

    private LinearLayout lnthuebaochinhchu;
    private ExpandableHeightListView lvsubparent;

    private CheckSubAdapter checkSubAdapter;
    private List<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();

    // bo sung thong tin dau noi goi hoc sinh
    private LinearLayout lnGoiCuocDacBietHs;
    private EditText edttenhs;
    private EditText edtngaysinhhs;

    private Date birthDateHs = null;

    private CustomerDTO customerInfo;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        activity = _activity;
    }

    private LinearLayout lndeposit, lnqouta;

    private ArrayList<Spin> arrQouta = new ArrayList<Spin>();

    private String checkIsSpec = "";

    private String limitUs;

    private LinearLayout lndiachi;

    private String checkCharRegtype = "";
    private String checkSpecRegtype = "";
    DonViAdapter donviadapter;

    private Spinner spinner_quoctichpr;
    private ArrayList<Spin> spinNation = new ArrayList<Spin>();

    private LinearLayout lnttinphidn;

    private String limitUsCommiton;

    // DEFINE VIEW CHUYEN DOI TRA TRUOC SANG TRA SAU

    // khai bao hashmap product
//    public static HashMap<String, ArrayList<ProductOfferingDTO>> hashmapProductOffer;

    private ArrayList<ProductOfferingDTO> lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();


    private LinearLayout lnScanbar;
    private LinearLayout lnTitleScanbar;
    private Button btnbar;
    private Button btnkiemtraserial;


    // define grouptype
    private String custypeKey;
    private String custypeKeyPr;

    // thientv7
    private String addInfo = "";
    private List<AddInfo> lstAddInfos = new ArrayList<>();
    private List<AddInfo> lstAddInfoOlds = new ArrayList<>();
    //    List<ProductSpecCharDTO> lstProductSpecCharDTOs;
    List<ProductSpecCharUseDTO> lstProductSpecCharUseDTOs;
    private String addInfoStr = "";

    // thong tin user
    private LinearLayout lnuserInfo;
    private LinearLayout lnuserInfoChild;
    private TextView tvuserinfo;
    private CheckBox cbUseRepCus;

    public boolean isMoreThan = false;
    private String descriptionNotice = "";
    private EditText edit_ngaykySub;
    private LinearLayout lnsigndate;
    // danh sach cau hinh du dung
    public static ArrayList<Spin> arrMapusage = new ArrayList<>();
    private String typeMapusage = "";
    // omni channel
    public ReceiveRequestBean receiveRequestBean = null;
    public HotLineReponseDetail hotLineReponseDetail = new HotLineReponseDetail();
    public ArrayList<ImageBO> lstImageBOs;

    //omni
    private String omniProcessId = "";
    private String omniTaskId = "";


    private Button btnInfoPromotion, btnInfoProduct;
    private String packageCode = "";
	//added by diepdc
    Map<String, String> mMapTemplate = new HashMap<String, String>();
    public static ArrayList<SpinV2> arrCustomerInfo = new ArrayList<>();
    private String subObject = null; //ma doi tuong
    // update profile dien tu
    @BindView(R.id.lnSelectProfile)
    LinearLayout lnSelectProfile;
    ProfileInfoDialogFragment fragment;
    @BindView(R.id.thumbnails)
    LinearLayout thumbnails;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.expandedImageView)
    ImageView expandedImageView;
    @BindView(R.id.frlMain)
    FrameLayout frlMain;
    ProfileBO profileBO = new ProfileBO();
    private Animator mCurrentAnimator;
    LayoutInflater inflater;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        inflater = LayoutInflater.from(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            // omni
            this.omniTaskId = arguments.getString("omniTaskId", omniTaskId);
            this.omniProcessId = arguments.getString("omniProcessId", omniProcessId);

            hotLineReponseDetail = (HotLineReponseDetail) arguments.getSerializable("hotLineReponseDetail");
            receiveRequestBean = (ReceiveRequestBean) arguments.getSerializable("receiveRequestBean");
            lstImageBOs = (ArrayList<ImageBO>) arguments.getSerializable("lstImageBOs");
            Gson g = new Gson();
            Log.d("hotLineReponseDetail_FragmentInfoCustomerMobileNew: ", g.toJson(hotLineReponseDetail));
        }
        arrCusTypeDTO = FragmentInfoCustomerMobileNew.arrCustTypeDTOs;
        instance = FragmentConnectionMobileNew.this;
        Calendar calendar = Calendar.getInstance();
        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH) + 1;
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
        String fromMothStr = "";
        if (fromMonth < 10) {
            fromMothStr = "0" + fromMonth;
        } else {
            fromMothStr = "" + fromMonth;
        }

        String fromDayStr = "";
        if (fromDay < 10) {
            fromDayStr = "0" + fromDay;
        } else {
            fromDayStr = "" + fromDay;
        }

        dateNowString = fromDayStr + "/" + fromMothStr + "/" + fromYear + "";
        try {
            dateNow = sdf.parse(dateNowString);
            dateBD = sdf.parse(dateNowString);
            dateEnd = sdf.parse(dateNowString);
            birthDateHs = sdf.parse(dateNowString);
        } catch (Exception e) {
            Log.e("Exception", e.toString(), e);
        }

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        if (pakaChargeBeans != null) {
            pakaChargeBeans = new ProductOfferingDTO();
        }
        custIdentityDTO = FragmentInfoCustomerMobileNew.custIdentityDTO;
        subType = FragmentInfoCustomerMobileNew.subType;

        // Log.d(logTag, "FragmentConnectionMobile onCreateView cusType: " +
        // custommerByIdNoBean.getCusType() + " subType: " + subType);
        custIdentityDTOCheck = FragmentInfoCustomerMobileNew.custIdentityDTO;

        if (custIdentityDTO != null) {
            if (custIdentityDTO.getCustomer() == null
                    || CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getName())) {
                CommonActivity.createAlertDialog(activity, getString(R.string.checkcusid), getString(R.string.app_name),
                        movetabInfoCus).show();
                return mView;

            }
        } else {
            CommonActivity.createAlertDialog(activity, getString(R.string.checkcusid), getString(R.string.app_name),
                    movetabInfoCus).show();
            return mView;
        }

        if (mView == null) {
            mView = inflater.inflate(R.layout.connectionmobile_layout_info_bccs2, container, false);
            ButterKnife.bind(this, mView);

            subTypeCheck = subType;

            unitView(mView);

        } else {

            reloadCustType();


            if (custIdentityDTO != null) {

                if ("2".equals(subType) && custIdentityDTO != null && "2".equals(custIdentityDTO.getGroupType()) && "M".equals(serviceType)) {
                    customerInfo = custIdentityDTO.getRepreCustomer().getCustomer();
                    customerInfo.setCopy(true);
                }
                if ("M".equals(serviceType) && "2".equals(subType) && custIdentityDTO != null && !"2".equals(custIdentityDTO.getGroupType())) {
                    DoUHaveMoreThan3SubAsyn doUHaveMoreThan3SubAsyn = new DoUHaveMoreThan3SubAsyn(getActivity(), new OnPostDoUHaveMorethan(), moveLogInAct);
                    if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())) {
                        doUHaveMoreThan3SubAsyn.execute(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdNo() + "", custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType() + "");
                    } else {
                        doUHaveMoreThan3SubAsyn.execute(custIdentityDTO.getIdNo() + "", custIdentityDTO.getIdType() + "");
                    }
                }
                if (!subTypeCheck.equalsIgnoreCase(subType)) {
                        mView = inflater.inflate(R.layout.connectionmobile_layout_info_bccs2, container, false);
                        unitView(mView);
                        subTypeCheck = subType;
                        custIdentityDTOCheck = custIdentityDTO;
                } else {
                    if (custIdentityDTOCheck != null && custIdentityDTO != null) {
                        if (custIdentityDTOCheck.getCustIdentityId() != null && custIdentityDTO.getCustIdentityId() != null) {
                            if (custIdentityDTOCheck.getIdNo().equalsIgnoreCase(custIdentityDTO.getIdNo())) {
                                subTypeCheck = subType;
                                custIdentityDTOCheck = custIdentityDTO;
                            } else {
                                mView = inflater.inflate(R.layout.connectionmobile_layout_info_bccs2, container, false);
                                unitView(mView);
                                subTypeCheck = subType;
                                custIdentityDTOCheck = custIdentityDTO;
                            }
                        } else {
                            subTypeCheck = subType;
                            custIdentityDTOCheck = custIdentityDTO;
                        }
                    } else {
                        mView = inflater.inflate(R.layout.connectionmobile_layout_info_bccs2, container, false);
                        unitView(mView);
                        subTypeCheck = subType;
                        custIdentityDTOCheck = custIdentityDTO;
                    }
                }
            } else {
                CommonActivity.createAlertDialog(activity, getString(R.string.checkcusid), getString(R.string.app_name),
                        movetabInfoCus).show();
            }


        }


        if (mBundle != null && receiveRequestBean == null) {
            receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("ReceiveRequestBeanKey");
        }

        return mView;
    }

    @Override
    public void onResume() {
        Log.e(logTag, "FragmentConnectionMobileNew onResume");
        MainActivity.getInstance().setTitleActionBar(R.string.servicemobile);
        super.onResume();
    }
	 private void setPackage(ProductOfferingDTO pakaChargeBeans) {
        if (pakaChargeBeans != null) {
            mMapTemplate.put(AutoConst.PACKAGE, pakaChargeBeans.getName());

            txtpakage.setText(Html.fromHtml("<u>" + pakaChargeBeans.getName() + "</u>"));
            offerId = pakaChargeBeans.getProductOfferingId() + "";
            productCode = pakaChargeBeans.getCode();
            lnGoiCuocDacBietHs.setVisibility(View.GONE);

            if ("2".equals(subType)) {
                if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
                    arrHthmBeans = new ArrayList<HTHMBeans>();
                }
                edt_hthm.setHint(getActivity().getString(R.string.chonhthm));
                if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
                    arrSpecObjects = new ArrayList<SpecObject>();
                }
                // lay hinh thuc hoa mang tra truoc
                if (CommonActivity.isNetworkConnected(activity)) {
                    if (offerId != null && !offerId.isEmpty()) {
                        System.out.println("12345 lay hinh thuc hoa mang tra truoc");

                        if (!CommonActivity.isNullOrEmptyArray(lstProductOfferingDTOHasAtrr)) {
                            for (ProductOfferingDTO item : lstProductOfferingDTOHasAtrr) {
                                if (item.getCode().equals(pakaChargeBeans.getCode())) {
                                    pakaChargeBeans.setLstProductSpecCharDTOs(item.getLstProductSpecCharDTOs());
                                    break;
                                }
                            }
                        }

                        // TODO LAY THONG TIN GOI CUOC
                        if (pakaChargeBeans.getLstProductSpecCharDTOs() != null
                                && pakaChargeBeans.getLstProductSpecCharDTOs().size() > 0) {


                            for (ProductSpecCharDTO item : pakaChargeBeans.getLstProductSpecCharDTOs()) {
                                if ("IS_SPECIAL_PRODUCT".equals(item.getCode())) {
                                    spDoiTuong.setEnabled(true);
                                    checkIsSpec = "IS_SPECIAL_PRODUCT";

                                    GetProductSpecInfoPreAsyn getInfoPreAsyn = new GetProductSpecInfoPreAsyn(
                                            activity);
                                    getInfoPreAsyn.execute(productCode);
                                    break;
                                } else {

                                    if ("SPEC_HISCL".equals(item.getCode())) {
                                        checkIsSpec = "SPEC_HISCL";
                                        lnGoiCuocDacBietHs.setVisibility(View.VISIBLE);
                                        break;
                                    }

                                    if ("SPEC_ELDER".equals(item.getCode())) {
                                        checkIsSpec = "SPEC_ELDER";
                                        break;
                                    }

                                    checkIsSpec = "";
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
                        }
                        if ("M".equals(serviceType)) {
                            CheckProductAsyn checkProductAsyn = new CheckProductAsyn(getActivity(),
                                    "isPcProduct");
                            checkProductAsyn.execute(productCode);
                        }
                        // lay thong tin ly do chuyen doi tu tra sau
                        // sang tra truoce
                        if (ActivityCreateNewRequestMobileNew.instance != null
                                && !CommonActivity.isNullOrEmpty(
                                ActivityCreateNewRequestMobileNew.instance.funtionType)
                                && Constant.CHANGE_POS_TO_PRE
                                .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {

                            GetReasonFullPM getReasonFullPMChangeSim = new GetReasonFullPM(getActivity(),
                                    "221");
                            getReasonFullPMChangeSim.execute();
                        } else {
                            GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(getActivity());
                            getHTHMAsyn.execute(offerId, serviceType, province, district, precinct,
                                    custIdentityDTO.getCustomer().getCustType(), "-1", subType);
                        }

                    }
                } else {
                    CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
                            getString(R.string.app_name)).show();
                }
            } else {
                // lay hthm tra sau
                System.out.println("12345 lay hthm tra sau");
                if (CommonActivity.isNetworkConnected(activity)) {

                    if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
                        arrSubTypeBeans = new ArrayList<SubTypeBeans>();
                    }

                    GetLimitUsageAsyn getLimitUsageAsyn = new GetLimitUsageAsyn(getActivity());
                    getLimitUsageAsyn.execute(productCode);
                    if ("M".equals(serviceType)) {
                        CheckProductAsyn checkProductAsyn = new CheckProductAsyn(getActivity(),
                                "isPcProductPost");
                        checkProductAsyn.execute(productCode);
                    }

                    GetListSubTypeAsyn getListSubTypeAsyn = new GetListSubTypeAsyn(activity);
                    getListSubTypeAsyn.execute(serviceType, productCode);

                    GetQuotaMapByTelecomServiceAsyn getQuotaMapByTelecomServiceAsyn = new GetQuotaMapByTelecomServiceAsyn(
                            getActivity());
                    getQuotaMapByTelecomServiceAsyn.execute(custIdentityDTO.getCustomer().getCustType(),
                            productCode, serviceType);

                } else {
                    CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
                            getString(R.string.app_name)).show();
                }

            }

        }
    }

    private void setPromotionTypeBean(PromotionTypeBeans promotionTypeBeans) {
        if (promotionTypeBeans != null) {
            System.out.println("12345 setPromotionTypeBean " + promotionTypeBeans.getName());
            if (!CommonActivity.isNullOrEmpty(promotionTypeBeans.getPromProgramCode())) {
                // if
                // (!promotionTypeBeans.getPromProgramCode().equals("-1"))
                // {
                maKM = promotionTypeBeans.getPromProgramCode();
                edt_kmai.setText(promotionTypeBeans.getName().toString());

                lncuocdongtruoc.setVisibility(View.VISIBLE);
                GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
                        getActivity());

                mMapTemplate.put(AutoConst.KM, promotionTypeBeans.getName().toString());

                if ("-2".equals(maKM)) {
                    getAllListPaymentPrePaidAsyn.execute("", productCode, province, dateNowString);
                } else {
                    getAllListPaymentPrePaidAsyn.execute(maKM, productCode, province, dateNowString);
                }
            } else {
                // maKM = "";
                // lncuocdongtruoc.setVisibility(View.GONE);
                // txtchonkm.setText(getString(R.string.selectMKM));
                maKM = "";
                edt_kmai.setText(getString(R.string.chonctkm1));
                lncuocdongtruoc.setVisibility(View.VISIBLE);
                GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
                        getActivity());
                getAllListPaymentPrePaidAsyn.execute(maKM, productCode, province, dateNowString);
            }
        } else {
            maKM = "";
            edt_kmai.setText(getString(R.string.chonctkm1));
            lncuocdongtruoc.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG 9", "FragmentConnectionMobileNew onActivityResult requestCode : " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 19877:
                    SubTypeBeans subTypeBeans = (SubTypeBeans) data.getExtras().getSerializable("loaithuebao");
                    if (subTypeBeans != null) {
                        setLoaithuebao(subTypeBeans);
                    }
                    break;
                case 3333:
                    if (data != null) {
                        checkIsSpec = "";
                        pakaChargeBeans = (ProductOfferingDTO) data.getExtras().getSerializable("pakageChargeKey");
                        setPackage(pakaChargeBeans);
                    }
                    break;
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");
                    fragment.onActivityResult(requestCode,resultCode,data);
                    break;
                case 1001:
                    hthmBeans = (HTHMBeans) data.getExtras().getSerializable("HTHMBeans");
                    hashmapFileObj = new HashMap<>();
                    profileBO = new ProfileBO();
                    profileBO.clearData();
                    reloadDataHTHM(hthmBeans);
                    break;

                case 102:
                    // PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans)
                    // data
                    // .getExtras().getSerializable("PromotionTypeBeans");
                    // maKM = promotionTypeBeans.getPromProgramCode();
                    // lncuocdongtruoc.setVisibility(View.VISIBLE);
                    // GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn =
                    // new GetAllListPaymentPrePaidAsyn(
                    // activity);
                    // getAllListPaymentPrePaidAsyn.execute(maKM, productCode,
                    // province, dateNowString);
                    //
                    // edt_kmai.setText(promotionTypeBeans.toString());

                    PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans) data.getExtras()
                            .getSerializable("PromotionTypeBeans");
                    setPromotionTypeBean(promotionTypeBeans);
                    break;

                case 11111:
                    areaProvicialPR = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    areaDistristPR = (AreaObj) data.getExtras().getSerializable("areaDistrist");

                    areaPrecintPR = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    areaGroupPR = (AreaObj) data.getExtras().getSerializable("areaGroup");

                    areaFlowPR = data.getExtras().getString("areaFlow");
                    areaHomeNumberPR = data.getExtras().getString("areaHomeNumber");

                    addressPR = new StringBuilder();

                    if (areaHomeNumberPR != null && areaHomeNumberPR.length() > 0) {
                        addressPR.append(areaHomeNumberPR + " ");
                    }
                    if (areaFlowPR != null && areaFlowPR.length() > 0) {
                        addressPR.append(areaFlowPR + " ");
                    }
                    if (areaGroupPR != null && areaGroupPR.getName() != null && areaGroupPR.getName().length() > 0) {
                        if (getActivity().getString(R.string.choose_street_block).equals(areaGroupPR.getName())) {
                            addressPR.append("");
                        } else {
                            addressPR.append(areaGroupPR.getName() + " ");
                            to = areaGroupPR.getAreaCode();
                        }

                    } else {
                        to = "";
                    }
                    if (areaPrecintPR != null && areaPrecintPR.getName() != null && areaPrecintPR.getName().length() > 0) {

                        addressPR.append(areaPrecintPR.getName() + " ");
                        precinct = areaPrecintPR.getPrecinct();
                    } else {
                        precinct = "";
                    }
                    if (areaDistristPR != null && areaDistristPR.getName() != null
                            && areaDistristPR.getName().length() > 0) {

                        addressPR.append(areaDistristPR.getName() + " ");
                        district = areaDistristPR.getDistrict();
                    } else {
                        district = "";
                    }
                    if (areaProvicialPR != null && areaProvicialPR.getName() != null
                            && areaProvicialPR.getName().length() > 0) {

                        addressPR.append(areaProvicialPR.getName());
                        province = areaProvicialPR.getProvince();
                    } else {
                        province = "";
                    }
                    // Log.d("Log", "Check edit address null: " + txtdctbc +
                    // "adess :"
                    // + addressPR);
                    edtdiachilapdat.setText(addressPR);

                    break;
                case 11112:
                    areaProvicialContract = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    areaDistristContract = (AreaObj) data.getExtras().getSerializable("areaDistrist");

                    areaPrecintContract = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    areaGroupContract = (AreaObj) data.getExtras().getSerializable("areaGroup");

                    areaFlowContract = data.getExtras().getString("areaFlow");
                    areaHomeNumberContract = data.getExtras().getString("areaHomeNumber");

                    addressContract = new StringBuilder();

                    if (areaHomeNumberContract != null && areaHomeNumberContract.length() > 0) {
                        homeContract = areaFlowContract;
                        addressContract.append(areaHomeNumberContract + " ");
                    }
                    if (areaFlowContract != null && areaFlowContract.length() > 0) {
                        streetNameContract = areaHomeNumberContract;
                        addressContract.append(areaFlowContract + " ");
                    }
                    if (areaGroupContract != null && areaGroupContract.getName() != null
                            && areaGroupContract.getName().length() > 0) {
                        addressContract.append(areaGroupContract.getName() + " ");
                        streetBlockContract = areaGroupContract.getAreaCode();
                    } else {
                        streetBlockContract = "";
                    }
                    if (areaPrecintContract != null && areaPrecintContract.getName() != null
                            && areaPrecintContract.getName().length() > 0) {

                        addressContract.append(areaPrecintContract.getName() + " ");
                        precintContract = areaPrecintContract.getPrecinct();
                    } else {
                        precintContract = "";
                    }
                    if (areaDistristContract != null && areaDistristContract.getName() != null
                            && areaDistristContract.getName().length() > 0) {

                        addressContract.append(areaDistristContract.getName() + " ");
                        districtContract = areaDistristContract.getDistrict();
                    } else {
                        districtContract = "";
                    }
                    if (areaProvicialContract != null && areaProvicialContract.getName() != null
                            && areaProvicialContract.getName().length() > 0) {

                        addressContract.append(areaProvicialContract.getName());
                        provinceContract = areaProvicialContract.getProvince();

                        // CUNG TINH HAN MUC BAT BUOC DUOC CHON
                        if ("M".equals(serviceType)) {
                            if (Session.province.equals(provinceContract)) {
                                lnqouta.setVisibility(View.VISIBLE);

                                // SharedPreferences preferences =
                                // getActivity().getSharedPreferences(Define.PRE_NAME,
                                // Activity.MODE_PRIVATE);
                                //
                                // String name =
                                // preferences.getString(Define.KEY_MENU_NAME, "");
                                // if(name.contains(";menu.deposit;")){
                                // lndeposit.setVisibility(View.VISIBLE);
                                // }else{
                                lndeposit.setVisibility(View.GONE);
                                // }

                                spinner_quota.setEnabled(true);
                            } else {
                                lnqouta.setVisibility(View.VISIBLE);
                                lndeposit.setVisibility(View.VISIBLE);
                                spinner_quota.setEnabled(false);
                                if (arrQouta != null && arrQouta.size() > 0) {

                                    for (int i = 0; i < arrQouta.size(); i++) {

                                        if (CommonActivity.isNullOrEmpty(arrQouta.get(i).getId())) {
                                            spinner_quota.setSelection(arrQouta.indexOf(arrQouta.get(i)));
                                            break;
                                        }
                                    }
                                }

                            }
                        } else {
                            lnqouta.setVisibility(View.VISIBLE);
                            lndeposit.setVisibility(View.GONE);
                            spinner_quota.setEnabled(true);
                            if (arrQouta != null && arrQouta.size() > 0) {

                                for (int i = 0; i < arrQouta.size(); i++) {

                                    if (CommonActivity.isNullOrEmpty(arrQouta.get(i).getId())) {
                                        spinner_quota.setSelection(arrQouta.indexOf(arrQouta.get(i)));
                                        break;
                                    }
                                }
                            }
                        }

                    } else {
                        provinceContract = "";
                    }
                    txtdctbc.setText(addressContract);
                    edtdchdcuoc.setText(addressContract);
                    break;

                case 11113:
                    areaProvicialContractPR = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    areaDistristContractPR = (AreaObj) data.getExtras().getSerializable("areaDistrist");

                    areaPrecintContractPR = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    areaGroupContractPR = (AreaObj) data.getExtras().getSerializable("areaGroup");

                    areaFlowContractPR = data.getExtras().getString("areaFlow");
                    areaHomeNumberContractPR = data.getExtras().getString("areaHomeNumber");

                    addressContractPR = new StringBuilder();

                    if (areaHomeNumberContractPR != null && areaHomeNumberContractPR.length() > 0) {
                        addressContractPR.append(areaHomeNumberContractPR + " ");
                    }
                    if (areaFlowContractPR != null && areaFlowContractPR.length() > 0) {
                        addressContractPR.append(areaFlowContractPR + " ");
                    }
                    if (areaGroupContractPR != null && areaGroupContractPR.getName() != null
                            && areaGroupContractPR.getName().length() > 0) {
                        addressContractPR.append(areaGroupContractPR.getName() + " ");
                        streetBlockContractPR = areaGroupContractPR.getAreaCode();
                    } else {
                        streetBlockContractPR = "";
                    }
                    if (areaPrecintContractPR != null && areaPrecintContractPR.getName() != null
                            && areaPrecintContractPR.getName().length() > 0) {

                        addressContractPR.append(areaPrecintContractPR.getName() + " ");
                        precintContractPR = areaPrecintContractPR.getPrecinct();
                    } else {
                        precintContractPR = "";
                    }
                    if (areaDistristContractPR != null && areaDistristContractPR.getName() != null
                            && areaDistristContractPR.getName().length() > 0) {

                        addressContractPR.append(areaDistristContractPR.getName() + " ");
                        districtContractPR = areaDistristContractPR.getDistrict();
                    } else {
                        districtContractPR = "";
                    }
                    if (areaProvicialContractPR != null && areaProvicialContractPR.getName() != null
                            && areaProvicialContract.getName().length() > 0) {

                        addressContractPR.append(areaProvicialContractPR.getName());
                        provinceContractPR = areaProvicialContractPR.getProvince();
                    } else {
                        provinceContractPR = "";
                    }
                    txtdcgtxmDD.setText(addressContractPR);

                    break;
                case 1444:
                    if (data != null) {
                        custTypeDTOContractPR = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                        if (custTypeDTOContractPR != null
                                && !CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())) {
                            edtloaikhDD.setText(custTypeDTOContractPR.getName());
                            // lay danh sach loáº¡i giay to theo loai khach hang
                            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                                    getActivity(), null, null, spinner_type_giay_to_parent);
                            getMappingCustIdentityUsageAsyn.execute(custTypeDTOContractPR.getCustType());
                        } else {
                            edtloaikhDD.setText("");
                        }
                    }

                    break;
                case 5555:

                    String contents = data.getStringExtra("SCAN_RESULT");
                    txtserial.setText(contents);
                    txtserial.requestFocus();

                    try {
                        SaveLog saveLog = new SaveLog(getActivity(),
                                Constant.SYSTEM_NAME, Session.userName,
                                "connectmobile_barcode", CommonActivity.findMyLocation(
                                getActivity()).getX(), CommonActivity
                                .findMyLocation(getActivity()).getY());
                        saveLog.saveLogRequest(
                                " barcode scan serial : " + contents,

                                new Date(), new Date(), Session.userName + "_"
                                        + CommonActivity.getDeviceId(getActivity())
                                        + "_" + System.currentTimeMillis());
                    } catch (Exception e) {
                        Log.d("saveLog", e.toString());
                    }

                    break;
                case 1511:
                    CustTypeDTO custTypeDTO = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                    if (!CommonActivity.isNullOrEmpty(custTypeDTO)) {
                        custypeKey = custTypeDTO.getCustType();
                    } else {
                        custypeKey = "";
                    }

                    break;
                case 1512:
                    CustTypeDTO custTypeDTOPR = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                    if (!CommonActivity.isNullOrEmpty(custTypeDTOPR)) {
                        custypeKeyPr = custTypeDTOPR.getCustType();
                    } else {
                        custypeKeyPr = "";
                    }
                    break;
                case 1287:
                    customerInfo = (CustomerDTO) data.getExtras().getSerializable("customerInfo");
                    if (customerInfo != null && !CommonActivity.isNullOrEmpty(customerInfo.getListCustIdentity()) && !CommonActivity.isNullOrEmpty(customerInfo.getListCustIdentity().get(0).getIdNo())) {
                        tvuserinfo.setText(getActivity().getString(R.string.selectcusted));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void reloadDataHTHM(HTHMBeans hthmBeans) {
        removeMapkey(AutoConst.HTHM);

        if (hthmBeans.getName().equalsIgnoreCase(getString(R.string.chonhthm))) {
            edt_hthm.setText(getString(R.string.chonhthm));

            hthm = "";
            reasonId = "";

            if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0 && adapProductAdapter != null) {
                arrStockTypeBeans = new ArrayList<ProductOfferTypeDTO>();
                adapProductAdapter = new ThongTinHHAdapterBCCS(arrStockTypeBeans, getActivity(),
                        FragmentConnectionMobileNew.this);
                lvproduct.setAdapter(adapProductAdapter);
                adapProductAdapter.notifyDataSetChanged();
            }
            if (mapSubStockModelRelReq != null && mapSubStockModelRelReq.size() > 0) {
                mapSubStockModelRelReq.clear();
            }
            maKM = "";
            prepaidCode = "-1";
            prepaidId = "";
            if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() > 0) {
                arrPromotionTypeBeans.clear();
                initInitListPromotionNotData();
            }


        } else {
            edt_hthm.setText(hthmBeans.toString());

            mMapTemplate.put(AutoConst.HTHM, hthmBeans.toString());
            System.out.println("12345 mMapTemplate.hthm " + hthmBeans.toString());

            if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0) {
                arrStockTypeBeans.clear();
            }
            if (mapSubStockModelRelReq != null && mapSubStockModelRelReq.size() > 0) {
                mapSubStockModelRelReq.clear();
            }

            if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() > 0) {
                arrPromotionTypeBeans.clear();
            }

            hthm = hthmBeans.getCode();
            reasonId = hthmBeans.getReasonId();

            if (ActivityCreateNewRequestMobileNew.instance != null
                    && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                txtisdn.setEnabled(false);
                if (Constant.CHANGE_PRE_TO_POS.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                    if (ActivityCreateNewRequestMobileNew.instance != null
                            && ActivityCreateNewRequestMobileNew.instance.subscriberDTO != null) {
                        txtisdn.setText(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getIsdn());

                        txtserial.setText(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getSerial());
                        if (hthmBeans != null && !CommonActivity.isNullOrEmpty(hthmBeans.getDescription()) && "$GS$".contains(hthmBeans.getDescription())) {
                            txtserial.setEnabled(false);
                            lnScanbar.setVisibility(View.GONE);
                            lnTitleScanbar.setVisibility(View.GONE);
                        } else {
                            lnScanbar.setVisibility(View.VISIBLE);
                            lnTitleScanbar.setVisibility(View.VISIBLE);
                            txtserial.setText("");
                            txtserial.setEnabled(true);
                        }
                    }
                } else {
                    if (Constant.CHANGE_POS_TO_PRE.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        if (ActivityCreateNewRequestMobileNew.instance != null
                                && ActivityCreateNewRequestMobileNew.instance.subscriberDTO != null) {
                            txtisdn.setText(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getIsdn());

                            txtserial.setText(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getSerial());
                            if (hthmBeans != null && !CommonActivity.isNullOrEmpty(hthmBeans.getDescription()) && "$GS$".contains(hthmBeans.getDescription())) {
                                txtserial.setEnabled(false);
                                lnScanbar.setVisibility(View.GONE);
                                lnTitleScanbar.setVisibility(View.GONE);
                            } else {
                                lnScanbar.setVisibility(View.VISIBLE);
                                lnTitleScanbar.setVisibility(View.VISIBLE);
                                txtserial.setText("");
                                txtserial.setEnabled(true);
                            }
                        }
                    }
                }

            } else {
                lnScanbar.setVisibility(View.VISIBLE);
                lnTitleScanbar.setVisibility(View.VISIBLE);
                txtisdn.setEnabled(true);
                txtserial.setEnabled(true);
            }

            if (serviceType != null && !serviceType.isEmpty()) {
                if ("2".equalsIgnoreCase(subType)) {
                    if (CommonActivity.isNetworkConnected(activity)) {
                        GetListProductPosAsyn getListProductPosAsyn = new GetListProductPosAsyn(activity);
                        getListProductPosAsyn.execute(hthm, productCode, serviceType);

                        if (adapProductAdapter != null) {
                            adapProductAdapter.notifyDataSetChanged();
                        }
                        if (!CommonActivity.isNullOrEmpty(reasonId)) {
                            new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                                @Override
                                public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
                                    if (CommonActivity.isNullOrEmpty(result)) {
                                        profileBO.setRequiredUploadImage(false);
                                        lnSelectProfile.setVisibility(View.GONE);
                                    } else {
                                        lnSelectProfile.setVisibility(View.VISIBLE);
                                        profileBO.setMapListRecordPrepaid(result);
                                        if (isMoreThan) {
                                            profileBO.removeRecordByCode("PYCTT");
                                        }
                                        mapListRecordPrepaid = result;
                                    }
                                }
                            }, null, initProfileBO()).execute();
                        } else {
                            profileBO.getHashmapFileObj().clear();
                            reasonId = null;
                        }
//                        if (adapter != null) {
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask("" + reasonId,
//                                productCode, activity, lstImageBOs);
//                        getLisRecordPrepaidTask.execute();

                    } else {
                        CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
                                getString(R.string.app_name)).show();
                    }

                } else {
                    if (CommonActivity.isNetworkConnected(activity) == true) {
                        if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() > 0) {
                            arrPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
                        }
                        maKM = "";
                        GetListProductPosAsyn getListProductPosAsyn = new GetListProductPosAsyn(activity);
                        getListProductPosAsyn.execute(hthm, productCode, serviceType);

                        checkCharRegtype = "";
                        checkSpecRegtype = "";

                        checkIsSpec = "";
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
                        GetReasonCharacterAsyn getReasonCharacterAsyn = new GetReasonCharacterAsyn(getActivity());
                        getReasonCharacterAsyn.execute(hthmBeans.getReasonId());

                        if (adapProductAdapter != null) {
                            adapProductAdapter.notifyDataSetChanged();
                        }
                        if (!CommonActivity.isNullOrEmpty(reasonId)) {
                            new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                                @Override
                                public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
                                    if (CommonActivity.isNullOrEmpty(result)) {
                                        profileBO.setRequiredUploadImage(false);
                                        lnSelectProfile.setVisibility(View.GONE);
                                    } else {
                                        lnSelectProfile.setVisibility(View.VISIBLE);
                                        profileBO.setMapListRecordPrepaid(result);
                                        mapListRecordPrepaid = result;
                                    }
                                }
                            }, null, initProfileBO()).execute();
                        } else {
                            profileBO.getHashmapFileObj().clear();
                            reasonId = null;
                        }
//                        if (adapter != null) {
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        GetLisRecordPrepaidTask getLisRecordPrepaidTask = new GetLisRecordPrepaidTask("" + reasonId,
//                                productCode, activity);
//                        getLisRecordPrepaidTask.execute();

                    } else {
                        CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
                                getString(R.string.app_name)).show();
                    }

                }
            }
        }
    }

    OnClickListener movetabInfoCus = new OnClickListener() {

        @Override
        public void onClick(View v) {
            ActivityCreateNewRequestMobileNew.instance.tHost.setCurrentTab(0);

        }
    };

    private void unitView(View v) {
        lnSelectProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reasonId == null) {
                    CommonActivity.toast(getActivity(), R.string.chua_chon_ly_do_dang_ky);
                    return;
                }

                String numberSize = "";
                if (ActivityCreateNewRequestMobileNew.instance != null
                        && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                    if (Constant.CHANGE_PRE_TO_POS
                            .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        if(ActivityCreateNewRequestMobileNew.instance.subscriberDTO != null
                                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getCustomerDTOInput())
                                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getCustomerDTOInput().getCustId())){
                            // khach hang moi
                            if(CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())){
                                numberSize = "2";
                            }else{
                                // khach hang cu nhung khac custId
                                if(!custIdentityDTO.getCustomer().getCustId().equals(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getCustomerDTOInput().getCustId())){
                                    numberSize = "2";
                                }
                            }
                        }else{
                            numberSize = "2";
                        }
                    }
                }

                if ("2".equalsIgnoreCase(subType)) {
                    if (validatePre()) {
                        if (!CommonActivity.isNullOrEmpty(profileBO)
                                && !CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())) {
                            fragment = new ProfileInfoDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("profileBO", profileBO);
                            bundle.putString("xmlSub", subXml(true));
                            bundle.putString("xmlCus", xmlCustPre());
                            bundle.putString("numberSig",numberSize);
                            bundle.putString("groupType",custIdentityDTO.getGroupType());
                            fragment.setOnFinishDSFListener(onFinishDSFListener);
                            fragment.setArguments(bundle);
                            fragment.show(getFragmentManager(), "show profile");
                        }
                    }
                }else{
                    if(validatePos()){
                        if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                            GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                    getActivity(),true);
                            getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                    telecomserviceId, reasonId, productCode, maKM);
                        }else{
                            fragment = new ProfileInfoDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("profileBO", profileBO);
                            bundle.putString("xmlSub", subXml(true));
                            bundle.putString("xmlCus", xmlCusPos());
                            bundle.putString("numberSig",numberSize);
                            bundle.putString("groupType",custIdentityDTO.getGroupType());
                            fragment.setOnFinishDSFListener(onFinishDSFListener);
                            fragment.setArguments(bundle);
                            fragment.show(getFragmentManager(), "show profile");
                        }

                    }
                }
            }
        });
		
        if (!CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance)
                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.subscriberDTO)
                && "1".equals(subType)) {
            doGetSubPreChargeAVG();
        }

        lnsigndate = (LinearLayout) v.findViewById(R.id.lnsigndate);
        if ("M".equals(serviceType)) {
            lnsigndate.setVisibility(View.VISIBLE);
        } else {
            lnsigndate.setVisibility(View.GONE);
        }
        spinnerCustomerInfo = (Spinner) v.findViewById(R.id.spCustomer);
        edit_ngaykySub = (EditText) v.findViewById(R.id.edit_ngayky);
        edit_ngaykySub.setText(dateNowString);
        edit_ngaykySub.setOnClickListener(editTextListener);
        lnuserInfo = (LinearLayout) v.findViewById(R.id.lnuserInfo);
        lnuserInfoChild = (LinearLayout) v.findViewById(R.id.lnuserInfoChild);
        lnuserInfo.setVisibility(View.VISIBLE);
        cbUseRepCus = (CheckBox) v.findViewById(R.id.cbUseRepCus);
        cbUseRepCus.setVisibility(View.VISIBLE);
        // bo sung them thong tin nguoi su dung trong truong hop khach hang doanh nghiep
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType()) && "M".equals(serviceType)) {
            lnuserInfo.setVisibility(View.VISIBLE);
            if (CommonActivity.isNullOrEmpty(accountDTOMain) && "1".equals(subType)) {
                cbUseRepCus.setVisibility(View.GONE);
            }
        } else {
            lnuserInfo.setVisibility(View.GONE);
            if ("M".equals(serviceType) && "2".equals(subType) && !"2".equals(custIdentityDTO.getGroupType())) {
                DoUHaveMoreThan3SubAsyn doUHaveMoreThan3SubAsyn = new DoUHaveMoreThan3SubAsyn(getActivity(), new OnPostDoUHaveMorethan(), moveLogInAct);
                if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())) {
                    doUHaveMoreThan3SubAsyn.execute(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdNo() + "", custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType() + "");
                } else {
                    doUHaveMoreThan3SubAsyn.execute(custIdentityDTO.getIdNo() + "", custIdentityDTO.getIdType() + "");
                }
            }
        }
        cbUseRepCus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if ("2".equals(subType) && "2".equals(custIdentityDTO.getGroupType()) && "M".equals(serviceType)) {
                        customerInfo = custIdentityDTO.getRepreCustomer().getCustomer();
                        customerInfo.setCopy(true);
                    } else {
                        if ("1".equals(subType) && "2".equals(custIdentityDTO.getGroupType()) && "M".equals(serviceType)) {
                            if (CommonActivity.isNullOrEmpty(accountDTOMain) || CommonActivity.isNullOrEmpty(accountDTOMain.getRefCustomer())) {
                                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notreaccount), getActivity().getString(R.string.app_name)).show();
                                cbUseRepCus.setChecked(false);
                                customerInfo = null;
                            } else {
                                customerInfo = accountDTOMain.getRefCustomer();
                                customerInfo.setCopy(true);
                            }

                        }
                    }
                    lnuserInfoChild.setVisibility(View.GONE);

                } else {
                    tvuserinfo.setText(getActivity().getString(R.string.userinfo));
                    customerInfo = null;
                    lnuserInfoChild.setVisibility(View.VISIBLE);
                }
            }
        });
        lnuserInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivitySelectCustomer.class);
                intent.putExtra("subType", subType);
                startActivityForResult(intent, 1287);
            }
        });
        tvuserinfo = (TextView) v.findViewById(R.id.tvuserinfo);

//        inflater = LayoutInflater.from(getActivity());
        lnScanbar = (LinearLayout) v.findViewById(R.id.lnScanbar);
        lnTitleScanbar = (LinearLayout) v.findViewById(R.id.lnTitleScanbar);
        btnbar = (Button) v.findViewById(R.id.btnbar);
        btnkiemtraserial = (Button) v.findViewById(R.id.btnkiemtraserial);
        btnkiemtraserial.setOnClickListener(this);
        btnbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Constant.ACTION_SCAN);
                    intent.putExtra("SCAN_FORMATS",
                            "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
                    startActivityForResult(intent, 5555);

                } catch (ActivityNotFoundException anfe) {

                    OnClickListener click = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            UpdateApkAsyn updateApkAsyn = new UpdateApkAsyn(
                                    getActivity());
                            updateApkAsyn.execute();

                        }
                    };
                    CommonActivity.createDialog(getActivity(),
                            getActivity().getString(R.string.confirmdlapk),
                            getActivity().getString(R.string.app_name),
                            getActivity().getString(R.string.cancel),
                            getActivity().getString(R.string.ok), null,
                            click).show();
                }
            }
        });


        lnttinphidn = (LinearLayout) v.findViewById(R.id.lnttinphidn);
        lnttinphidn.setOnClickListener(this);
        // khai bao thong tin dau noi goi dac biet hoc sinh
        lnGoiCuocDacBietHs = (LinearLayout) v.findViewById(R.id.lnGoiCuocDacBietHs);
        lnGoiCuocDacBietHs.setVisibility(View.GONE);
        edttenhs = (EditText) v.findViewById(R.id.edttenhs);
        edtngaysinhhs = (EditText) v.findViewById(R.id.edtngaysinhhs);
        edtngaysinhhs.setOnClickListener(this);

        //omni
        if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail) && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getStrDateOfBirth())) {
            edtngaysinhhs.setText(hotLineReponseDetail.getStrDateOfBirth());
        } else {
            edtngaysinhhs.setText(dateNowString);
        }


        // khai bao them thong tin tich hop khong chinh chu
        lnthuebaochinhchu = (LinearLayout) v.findViewById(R.id.lnthuebaochinhchu);
        lnthuebaochinhchu.setVisibility(View.GONE);
        lnthuebaochinhchu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())) {
                    if (!CommonActivity.isNullOrEmpty(arrSubscriberDTO)) {
                        showSelectIsdnOwner();
                    } else {
                        FindVerifiedOwnerByListIdNoAsyn findVerifiedOwnerByListIdNoAsyn = new FindVerifiedOwnerByListIdNoAsyn(
                                getActivity(), custIdentityDTO.getCustomer().getListCustIdentity());
                        findVerifiedOwnerByListIdNoAsyn.execute();
                    }

                }

            }
        });

        // if ("2".equals(subType) && "M".equals(serviceType)
        // &&
        // !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId()))
        // {
        //
        // FindVerifiedOwnerByListIdNoAsyn findVerifiedOwnerByListIdNoAsyn = new
        // FindVerifiedOwnerByListIdNoAsyn(
        // getActivity(), custIdentityDTO.getCustomer().getListCustIdentity());
        // findVerifiedOwnerByListIdNoAsyn.execute();
        // }

        lndiachi = (LinearLayout) v.findViewById(R.id.lndiachi);

        // khai bao han muc va dat coc
        lndeposit = (LinearLayout) v.findViewById(R.id.lndeposit);
        lndeposit.setVisibility(View.GONE);
        lnqouta = (LinearLayout) v.findViewById(R.id.lnqouta);
        lnqouta.setVisibility(View.GONE);
        // bo sung cam ket
        tvcamketso = (TextView) v.findViewById(R.id.tvcamketso);
        tvcamketso.setVisibility(View.GONE);
        tvcamketso.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (CommonActivity.isNetworkConnected(getActivity())) {

                    if (validateViewCommitment()) {

                        GetDataStockWsByReasonAsyn getDataStockWsByReasonAsyn = new GetDataStockWsByReasonAsyn(
                                getActivity());
                        getDataStockWsByReasonAsyn.execute(telecomserviceId, txtisdn.getText().toString().trim(),
                                saleServiceCode);

                        // if ("2".equals(subType)) {
                        // AsynctaskviewSubCommitmentPre
                        // asynctaskviewSubCommitmentPre = new
                        // AsynctaskviewSubCommitmentPre(
                        // getActivity());
                        // asynctaskviewSubCommitmentPre.execute(offerId, hthm,
                        // txtisdn.getText().toString().trim());
                        // } else {
                        // AsynctaskviewSubCommitmentPos
                        // asynctaskviewSubCommitmentPos = new
                        // AsynctaskviewSubCommitmentPos(
                        // getActivity());
                        // asynctaskviewSubCommitmentPos.execute(offerId, hthm,
                        // txtisdn.getText().toString().trim());
                        // }
                    }
                }
            }
        });
        // bo sung progress
        prb_contract = (ProgressBar) v.findViewById(R.id.prb_contract);

        spinner_serviceMobile = (Spinner) v.findViewById(R.id.spinner_service);
        spinner_serviceMobile.setOnTouchListener(this);

        txtpakage = (TextView) v.findViewById(R.id.tvpakage);
        txtpakage.setOnTouchListener(this);

        txtpakage.setOnClickListener(this);
        // ====TOSV1
        txtpakage.setText(Html.fromHtml("<u>" + getString(R.string.chonpakage_mobile) + "</u>"));
        edt_hthm = (EditText) v.findViewById(R.id.edt_hthm);
        edt_kmai = (EditText) v.findViewById(R.id.edt_kmai);

        if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail) && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getPromotionCode())) {
            edt_kmai.setText(hotLineReponseDetail.getPromotionCode());
        }


        edtdiachilapdat = (EditText) v.findViewById(R.id.edtdiachilapdat);
        edtdiachilapdat.setOnClickListener(this);

        spinner_loaihd = (NDSpinner) v.findViewById(R.id.spinner_loaihd);
        spinner_loaithuebao = (EditText) v.findViewById(R.id.spinner_loaithuebao);
        spinner_loaithuebao.setOnTouchListener(this);
        spinner_loaithuebao.setOnClickListener(this);
        v.findViewById(R.id.img_searchloaitb).setOnClickListener(this);

        lnMaNV = (LinearLayout) v.findViewById(R.id.lnMaNV);
        lnkhuyenmai = (LinearLayout) v.findViewById(R.id.lnkhuyenmai);
        lnloaithuebao = (LinearLayout) v.findViewById(R.id.lnloaithuebao);
        lnhopdong = (LinearLayout) v.findViewById(R.id.lnhopdong);

        // khai bao them phan chon han muc va dat coc cho tra sau
        lnquotaanddeposit = (LinearLayout) v.findViewById(R.id.lnquotaanddeposit);
        spinner_quota = (Spinner) v.findViewById(R.id.spinner_quota);
        spinner_deposit = (Spinner) v.findViewById(R.id.spinner_deposit);
        prbqouta = (ProgressBar) v.findViewById(R.id.prbqouta);
        prbdeposit = (ProgressBar) v.findViewById(R.id.prbdeposit);
        btnRefreshqouta = (Button) v.findViewById(R.id.btnRefreshqouta);
        btnRefreshqouta.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GetQuotaMapByTelecomServiceAsyn getQuotaMapByTelecomServiceAsyn = new GetQuotaMapByTelecomServiceAsyn(
                        getActivity());
                getQuotaMapByTelecomServiceAsyn.execute(custIdentityDTO.getCustomer().getCustType(), productCode,
                        serviceType);
            }
        });
        btndeposit = (Button) v.findViewById(R.id.btndeposit);
        btndeposit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GetListDepositAsyn getListDepositAsyn = new GetListDepositAsyn(getActivity());
                getListDepositAsyn.execute();
            }
        });
        txtisdn = (EditText) v.findViewById(R.id.txtisdn);

        if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail) && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getTelRegister())) {
            txtisdn.setText(hotLineReponseDetail.getTelRegister()); // dien thoai di
        }

        txtisdn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!CommonActivity.isNullOrEmpty(tvcamketso)) {
                    if (!CommonActivity.isNullOrEmpty(s.toString())) {
                        tvcamketso.setVisibility(View.VISIBLE);
                    } else {
                        tvcamketso.setVisibility(View.GONE);
                    }
                }
            }
        });
        txtserial = (EditText) v.findViewById(R.id.txtserial);
        txtimsi = (EditText) v.findViewById(R.id.txtimsi);
        txtmanv = (EditText) v.findViewById(R.id.txtmanv);
        // txtto = (EditText) v.findViewById(R.id.txtto);
        lvproduct = (ExpandableHeightListView) v.findViewById(R.id.lvproduct);
        lvproduct.setOnItemClickListener(this);
        lvproduct.setExpanded(true);
        btn_connection = (Button) v.findViewById(R.id.btn_connection);
        if (ActivityCreateNewRequestMobileNew.instance != null
                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
            btn_connection.setText(getActivity().getString(R.string.cdmobile));
        } else {
            btn_connection.setText(getActivity().getString(R.string.customer_connection_service_static));
        }
        lnButton = (LinearLayout) v.findViewById(R.id.lnButton);

        // define specical paper

        lnTTGoiCuocDacBiet = (LinearLayout) v.findViewById(R.id.lnGoiCuocDacBiet);
        lnTTGoiCuocDacBiet.setVisibility(View.GONE);
        viewSpec = (View) v.findViewById(R.id.viewSpec);
        viewSpec1 = (View) v.findViewById(R.id.viewSpec1);
        spDoiTuong = (Spinner) v.findViewById(R.id.spDoiTuong);
        edtQuocTich = (EditText) v.findViewById(R.id.edtQuocTich);
        edtQuocTich.setText(getString(R.string.viet_nam));
        edtQuocTich.setEnabled(false);
        tvDonVi = (EditText) v.findViewById(R.id.tvDonVi);
        edtMaGiayToDacBiet = (EditText) v.findViewById(R.id.edtMaGiayToDacBiet);
        edtNgayBD = (EditText) v.findViewById(R.id.edtNgayBD);
        edtNgayBD.setText(dateNowString);
        edtNgayBD.setOnClickListener(this);
        edtNgayKT = (EditText) v.findViewById(R.id.edtNgayKT);
        edtNgayKT.setText(dateNowString);
        edtNgayKT.setOnClickListener(this);
        rlchondonvi = (LinearLayout) v.findViewById(R.id.rlchondonvi);

        // bo song cuoc dong truoc
        spinner_cuocdongtruoc = (Spinner) v.findViewById(R.id.spinner_cuocdongtruoc);
        spinner_cuocdongtruoc.setOnTouchListener(this);

        lncuocdongtruoc = (LinearLayout) v.findViewById(R.id.lncuocdongtruoc);
        lncuocdongtruoc.setVisibility(View.GONE);
        spinner_cuocdongtruoc.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                removeMapkey(AutoConst.CDT);

                if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {
                    prepaidCode = arrPaymentPrePaidPromotionBeans.get(arg2).getPrePaidCode();
                    prepaidId = arrPaymentPrePaidPromotionBeans.get(arg2).getId();
                    if (!CommonActivity.isNullOrEmpty(prepaidCode) && !"-1".equals(prepaidCode)) {
                        if (!isFirst) {
                            System.out.println("12345 arrPaymentPrePaidPromotionBeans: " + arrPaymentPrePaidPromotionBeans.get(arg2));
                            showSelectCuocDongTruoc(arrPaymentPrePaidPromotionBeans.get(arg2));
                            //
                            String item = (String) arg0.getItemAtPosition(arg2);
                            String s = getResources().getString(R.string.notcdc);
                            if (!s.equals(item)) {
                                AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_CDT_SEARCH, item);

                                ArrayAdapter<String> adapterTB = (ArrayAdapter<String>) spinner_cuocdongtruoc.getAdapter();

                                mMapTemplate.put(AutoConst.CDT, item);

                                if (adapterTB != null) {
                                    ArrayList<String> list = new ArrayList<>();
                                    for (int i = 0; i < adapterTB.getCount(); i++) {
                                        list.add(adapterTB.getItem(i).toString());
                                    }
                                    AutoCompleteUtil.getInstance(getActivity()).sortCDTBySelectedCount(AUTO_CDT_SEARCH, list);

                                    adapterTB.clear();
                                    adapterTB.addAll(list);
                                    adapterTB.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                } else {
                    prepaidCode = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        imgDeleteDV = (LinearLayout) v.findViewById(R.id.imgDeleteDonvi);
        imgDeleteDV.setVisibility(View.GONE);
        imgDeleteDV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tvDonVi.setText("");
                mCodeDonVi = "";
                imgDeleteDV.setVisibility(View.GONE);
            }
        });

        lvUploadImage = (ListView) v.findViewById(R.id.lvUploadImage);

        spDoiTuong.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCodeDoiTuong = arrSpecObjects.get(arg2).getCode();

                // TOGO LAY THONG TIN SINH VIEN TU MA DOI TUONG
                // mCodeDoiTuong = arrSpecObjects.get(position).getCode();

                // check neu code = NEW_STU_2015 thi goi ws lay so bao danh va
                // an chon dv di
                if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
                    // rlchondonvi.setVisibility(View.GONE);
                    // rlquoctich.setVisibility(View.GONE);
                    if (CommonActivity.isNetworkConnected(activity)) {

                        // CheckInfoCusSpecialAsyn checkInfoCusSpecialAsyn = new
                        // CheckInfoCusSpecialAsyn(activity);
                        // checkInfoCusSpecialAsyn.execute(custIdentityDTO.getIdNo());
                        // TODO XU LY
                    } else {
                        CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
                                getString(R.string.app_name)).show();
                    }
                } else {
                    // an linear donvi di
                    rlchondonvi.setVisibility(View.VISIBLE);
                    edtMaGiayToDacBiet.setEnabled(true);
                    edtMaGiayToDacBiet.setText("");
                    tvDonVi.setText("");
                    mCodeDonVi = "";
                    imgDeleteDV.setVisibility(View.GONE);
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
        // init dich vu
        if (arrTelecomServiceBeans != null && arrTelecomServiceBeans.size() > 0) {
            arrTelecomServiceBeans.clear();
        }
        initTelecomService();
        // init province
        if (pakageBundeBean.getArrChargeBeans() != null && pakageBundeBean.getArrChargeBeans().size() > 0) {
            pakageBundeBean = new PakageBundeBeanBCCS();
        }

        if ("1".equalsIgnoreCase(subType)) {
            // init contract
            if (arrTractBeans != null && arrTractBeans.size() > 0) {
                arrTractBeans = new ArrayList<AccountDTO>();
            }
            if (custIdentityDTO != null) {
                if (CommonActivity.isNetworkConnected(activity)) {
                    GetConTractAsyn getConTractAsyn = new GetConTractAsyn(activity);
                    getConTractAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "", custIdentityDTO.getIdNo(),
                            custIdentityDTO.getIdNo());
                } else {
                    CommonActivity
                            .createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
                            .show();
                }
            }
        }

        spinner_serviceMobile.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String item = (String) arg0.getItemAtPosition(arg2);
                if (isFirst && mServiceMobile.equals(item)) {
                    return;
                }
                mServiceMobile = item;

                removeMapkey(AutoConst.SERVICE);


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
                reloadDataMobile();
                if (arg2 > 0 && positonservice != arg2) {
                    if (arrPakageChargeBeans != null && arrPakageChargeBeans.size() > 0) {
                        arrPakageChargeBeans.clear();
                        txtpakage.setText(Html.fromHtml("<u>" + getString(R.string.chonpakage_mobile) + "</u>"));
                        productCode = "";
                        offerId = "";
                    }
                    mMapTemplate.put(AutoConst.SERVICE, spinner_serviceMobile.getSelectedItem().toString());

                    serviceType = arrTelecomServiceBeans.get(arg2).getServiceAlias();
                    if ("M".equals(serviceType)) {
                        lnsigndate.setVisibility(View.VISIBLE);
                    } else {
                        lnsigndate.setVisibility(View.GONE);
                    }
                    telecomserviceId = arrTelecomServiceBeans.get(arg2).getTelecomServiceId();
                    if ("H".equals(serviceType)) {
                        lndiachi.setVisibility(View.VISIBLE);
                    } else {
                        if ("M".equals(serviceType) && "2".equals(subType) && custIdentityDTO != null && !"2".equals(custIdentityDTO.getGroupType())) {
                            DoUHaveMoreThan3SubAsyn doUHaveMoreThan3SubAsyn = new DoUHaveMoreThan3SubAsyn(getActivity(), new OnPostDoUHaveMorethan(), moveLogInAct);
                            if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())) {
                                doUHaveMoreThan3SubAsyn.execute(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdNo() + "", custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType() + "");
                            } else {
                                doUHaveMoreThan3SubAsyn.execute(custIdentityDTO.getIdNo() + "", custIdentityDTO.getIdType() + "");
                            }
                        }
                        lndiachi.setVisibility(View.GONE);
                    }

                    if ("2".equalsIgnoreCase(subType) && "M".equalsIgnoreCase(serviceType)) {
                        if (!CommonActivity.isNullOrEmpty(custIdentityDTO)) {
                            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
                                arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                                lnthuebaochinhchu.setVisibility(View.GONE);
                            } else {
                                if (custIdentityDTO.getCustomer().getCustId() != null) {
                                    lnthuebaochinhchu.setVisibility(View.VISIBLE);
                                } else {
                                    arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                                    lnthuebaochinhchu.setVisibility(View.GONE);
                                }
                            }
                        }

                        Log.d("Log", "unitView subType: " + subType + " serviceType: " + serviceType + " VISIBLE");
                        ln_quota.setVisibility(View.GONE);
                    } else {

                        arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                        lnthuebaochinhchu.setVisibility(View.GONE);
                        Log.d("Log", "unitView subType: " + subType + " serviceType: " + serviceType + " GONE");
                        ln_quota.setVisibility(View.GONE);
                    }

                    if ("2".equals(subType)) {
                        if ("M".equals(serviceType)) {
                            if (custIdentityDTO != null && "2".equals(custIdentityDTO.getGroupType())) {
                                lnuserInfo.setVisibility(View.VISIBLE);
                                GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(activity, false);
                                getListPakageAsyn.execute(telecomserviceId, subType);
                            } else {
                                CheckQuotaSubAsyn checkQuotaSubAsyn = new CheckQuotaSubAsyn(getActivity());
                                checkQuotaSubAsyn.execute(telecomserviceId, subType);
                                lnuserInfo.setVisibility(View.GONE);
                            }
                        } else {
                            GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(activity, false);
                            getListPakageAsyn.execute(telecomserviceId, subType);
                        }
                    } else {
                        if (!CommonActivity.isNullOrEmpty(custIdentityDTO)) {
                            if ("2".equals(custIdentityDTO.getGroupType())) {
                                lnuserInfo.setVisibility(View.VISIBLE);
                            } else {
                                lnuserInfo.setVisibility(View.GONE);
                            }
                        }
                        if ("M".equals(serviceType) && custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
                            GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(activity, false);
                            getListPakageAsyn.execute(telecomserviceId, subType);
                        } else {
                            CheckQuotaSubAsyn checkQuotaSubAsyn = new CheckQuotaSubAsyn(getActivity());
                            checkQuotaSubAsyn.execute(telecomserviceId, subType);
                        }
                    }

                } else {
                    cbUseRepCus.setChecked(false);
                    customerInfo = null;
                    lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
                    // serviceType = "";
                    // ReLoadData();
                    arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                    lnthuebaochinhchu.setVisibility(View.GONE);
                    lnGoiCuocDacBietHs.setVisibility(View.GONE);
                }
                positonservice = arg2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        edt_hthm.setOnClickListener(this);
        edt_hthm.setOnTouchListener(this);

        edt_kmai.setOnClickListener(this);
        edt_kmai.setOnTouchListener(this);

        spinner_loaihd.setOnTouchListener(this);
        spinner_loaihd.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Long accountId = arrTractBeans.get(position).getAccountId();

                if (position == 0) {
                    accountDTO = new AccountDTO();
                    contractNo = "";
                    contractId = "";
                } else if (position == 1) {
                    contractNo = "";
                    contractId = "";
                    // accountDTO = new AccountDTO();
                    if (dialogContract != null && dialogContract.isShowing()) {
                        dialogContract.cancel();
                    }

                    showPopupInsertInfoContractOffer(accountDTOMainNew, true);
                } else {
                    if (dialogContract != null && dialogContract.isShowing()) {
                        dialogContract.cancel();
                    }

                    GetAccountInforAsyn getAccountInforAsyn = new GetAccountInforAsyn(getActivity());
                    getAccountInforAsyn.execute(accountId + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int position = adapterView.getSelectedItemPosition();
                Log.d(Constant.TAG, "FragmentConnectionMobile onNothingSelected spinner_loaihd position " + position);
                if (position == 1) {
                    contractNo = "";
                    contractId = "";
                    // showPopupInsertInfoContract();
                    // show dialog
                }
            }
        });

//        spinner_loaithuebao.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String item = (String) arg0.getItemAtPosition(arg2);
//                setLoaithuebao(arg2, item);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

        ln_quota = (LinearLayout) v.findViewById(R.id.ln_quota);

        if ("2".equalsIgnoreCase(subType) && "M".equalsIgnoreCase(serviceType)) {
            Log.d("Log", "unitView subType: " + subType + " serviceType: " + serviceType + " VISIBLE");
            ln_quota.setVisibility(View.GONE);
        } else {
            Log.d("Log", "unitView subType: " + subType + " serviceType: " + serviceType + " GONE");
            ln_quota.setVisibility(View.GONE);
        }

        cb_quota = (CheckBox) v.findViewById(R.id.cb_quota);

        cb_quota.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    switch (isDeposit) {
                        case -1:
                            GetQuotaAsyn asyn = new GetQuotaAsyn(activity);
                            asyn.execute(custIdentityDTO.getCustomer().getCustTypeDTO().getCustType());
                            break;
                        case 0:
                            String message = String.format(getString(R.string.qupta), quota, moneyPre);
                            Dialog dialog0 = CommonActivity.createAlertDialog(activity, message,
                                    getString(R.string.app_name));
                            dialog0.show();
                            break;
                        default:
                            Dialog dialog1 = CommonActivity.createAlertDialog(activity, getString(R.string.not_quota),
                                    getString(R.string.app_name));
                            dialog1.show();
                            break;
                    }
                }
            }
        });

        if (Session.loginUser != null && Session.loginUser.getChannelTypeId() != 14) {
            lnMaNV.setVisibility(View.GONE);
            txtmanv.setText(Session.loginUser.getStaffCode());
            Log.d("Log", "check chanel value tvmanv" + txtmanv.getText().toString());
        }

        // chay ngam lay danh sach thue bao
        if ("1".equalsIgnoreCase(subType)) {
            AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(activity, TYPE_HTTHHD);
            asyntaskGetListAllCommon.execute();
        }

        initTypePaper(null, arrTypePaperBeans);

        btnInfoPromotion = (Button) v.findViewById(R.id.btnInfoPromotion);
        btnInfoProduct = (Button) v.findViewById(R.id.btnInfoProduct);

        // btnRefreshStreetBlock = (Button) mView
        // .findViewById(R.id.btnRefreshStreetBlock);
        // btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View arg0) {
        // new CacheDatabaseManager(MainActivity.getInstance())
        // .insertCacheStreetBlock(null, province + district
        // + precinct);
        // GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
        // activity, spinner_to, -1, null, prb_to, 1,
        // btnRefreshStreetBlock);
        // async.execute(province + district + precinct);
        // }
        // });
        reloadCustType();




        spinnerCustomerInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinV2 itemAtPosition = (SpinV2) parent.getItemAtPosition(position);
                if(!itemAtPosition.getValue().equals(activity.getString(R.string.chondoituong))) {
                    subObject = itemAtPosition.getId();
                }else{
                    subObject = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void doGetSubPreChargeAVG() {
        GetSubPreChargeAVGBySubIdAndNumMonthAsyncTask getSubPreChargeAVGBySubIdAndNumMonthAsyncTask =
                new GetSubPreChargeAVGBySubIdAndNumMonthAsyncTask(activity, new OnPostExecuteListener<Double>() {
                    @Override
                    public void onPostExecute(Double result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            avgCharge = result;
                        }
                    }
                }, moveLogInAct);
        getSubPreChargeAVGBySubIdAndNumMonthAsyncTask.execute(
                ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getSubId() + "");
    }

    /**
     * Thong tin chi tiet hop dong
     */
    protected EditText edt_contractNo; // so hop dong
    protected EditText edt_payer; // nguoi thanh toan
    protected Spinner spn_contractTypeCode; // loai hop dong
    protected EditText edt_signDate; // ngay ki
    protected EditText edt_effectDate; // ngay hieu luc
    protected EditText edt_endEffectDate; // ngay het han hop dong
    protected Spinner spn_payMethodCode; // hinh thuc thanh toan
    protected Spinner spn_billCycleFromCharging; // chu ky cuoc
    protected Spinner spn_printMethodCode, spn_noticeCharge; // in chi tiet

    /**
     * Dia chi hop dong
     */
    protected Spinner spn_province; // tinh
    protected Spinner spn_district; // huyen
    protected Spinner spn_precinct; // xa
    protected Spinner spn_streetBlock; // to
    protected EditText edt_streetName; // duong
    protected EditText edt_home; // so nha
    protected EditText edt_address; // dia chi

    protected int spn_province_pos = -1; // tinh
    protected int spn_district_pos = -1; // huyen
    protected int spn_precinct_pos = -1; // xa
    protected int spn_streetBlock_pos = -1; // to

    /**
     * Thong tin ngan hang
     */
    private LinearLayout lnBankAccount;
    private EditText edit_ngaynhothu;
    private EditText edthdnhothu;
    private EditText edittkoanhd;
    private EditText edttentkoan;
    private TextView txtnganhang;

    /**
     * Dialog dai dien hop dong cu
     *
     * @author Aloha
     */

    private ProgressBar prbhttthd, prbchukicuoc, prbinchitiet, prbhttbc;
    private Button btnhttthd, btnchukicuoc, btninchitiet, btnhttbc;

    private EditText edtemailtbc, edtdtcdtbc, edtdidongtbc, txtdctbc, edtdchdcuoc;

    // thong tin dai dien hop dong
    private LinearLayout lnttdaidienhd, lngiaytoxacminh, lnngayhethandd, lnParentAddress;
    private EditText edtloaikhDD, edit_sogiaytoDD, edit_ngaycapDD, edtnoicap, txtdcgtxmDD, edit_tenKHDD,
            edit_ngaysinhdDD, edit_ngayhethandd;
    private ProgressBar prbCustTypeDD;
    private Button btnRefreshCustTypeDD, btnkiemtra, btnedit;
    private Spinner spinner_type_giay_to_parent, spinner_gioitinhDD;
    private LinearLayout lnsoHD;

    private Dialog dialogContract;

    View dialogView;
    ViewGroup main;

    private void showPopupInsertInfoContractOffer(final AccountDTO accountDTO, final boolean isNew) {

        accountDTOMain = new AccountDTO();

        final ArrayList<CustTypeDTO> arrCusType = new ArrayList<CustTypeDTO>();
        if (arrCusTypeDTO != null && !arrCusTypeDTO.isEmpty()) {
            for (CustTypeDTO item : arrCusTypeDTO) {
                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(item.getGroupType())) {

                } else {
                    arrCusType.add(item);
                }
            }

        }

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialogView = inflater.inflate(R.layout.connectionmobile_contract, null, false);

        dialogContract = new Dialog(activity);
        dialogContract.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContract.setContentView(dialogView);
        dialogContract.setCancelable(false);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(new Date());
        lnParentAddress = (LinearLayout) dialogContract.findViewById(R.id.lnParentAddress);
        lnngayhethandd = (LinearLayout) dialogContract.findViewById(R.id.lnngayhethan);
        edit_ngayhethandd = (EditText) dialogContract.findViewById(R.id.edit_ngayhethan);
        edit_ngayhethandd.setOnClickListener(editTextListener);
        main = (ViewGroup) dialogContract.findViewById(R.id.main);
        prbhttthd = (ProgressBar) dialogContract.findViewById(R.id.prbhttthd);
        prbchukicuoc = (ProgressBar) dialogContract.findViewById(R.id.prbchukicuoc);
        prbinchitiet = (ProgressBar) dialogContract.findViewById(R.id.prbinchitiet);
        prbhttbc = (ProgressBar) dialogContract.findViewById(R.id.prbhttbc);
        btnhttthd = (Button) dialogContract.findViewById(R.id.btnhttthd);
        btnchukicuoc = (Button) dialogContract.findViewById(R.id.btnchukicuoc);
        btninchitiet = (Button) dialogContract.findViewById(R.id.btninchitiet);
        btnhttbc = (Button) dialogContract.findViewById(R.id.btnhttbc);

        lnsoHD = (LinearLayout) dialogContract.findViewById(R.id.lnsoHD);


        if (isNew) {
            lnsoHD.setVisibility(View.GONE);
        } else {
            lnsoHD.setVisibility(View.VISIBLE);
        }
        // so hop dong
        edt_contractNo = (EditText) dialogContract.findViewById(R.id.edtsohd);
        // ngay ky hop dong
        edt_signDate = (EditText) dialogContract.findViewById(R.id.edit_ngayky);
        edt_signDate.setText(dateNowString);
        edt_signDate.setOnClickListener(editTextListener);
        // chu ky cuoc
        spn_billCycleFromCharging = (Spinner) dialogContract.findViewById(R.id.spinner_chukicuoc);
        // hinh thuc thanh toan
        spn_payMethodCode = (Spinner) dialogContract.findViewById(R.id.spinner_httthd);
        // hinh thuc thong bao cuoc spinner_httbc
        spn_payMethodCode.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Spin item = (Spin) spn_payMethodCode.getItemAtPosition(arg2);

                if ("02".equalsIgnoreCase(item.getId()) || "03".equalsIgnoreCase(item.getId())) {
                    lnBankAccount.setVisibility(View.VISIBLE);
                } else {
                    lnBankAccount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // hinh thuc thong bao cuoc
        spn_noticeCharge = (Spinner) dialogContract.findViewById(R.id.spinner_httbc);

        /**
         * Thong tin ngan hang
         */
        lnBankAccount = (LinearLayout) dialogContract.findViewById(R.id.lnhinhthuctthd);
        lnBankAccount.setVisibility(View.GONE);
        edit_ngaynhothu = (EditText) dialogContract.findViewById(R.id.edit_ngaynhothu); // ngay
        edit_ngaynhothu.setText(dateNowString);
        edit_ngaynhothu.setOnClickListener(editTextListener);
        edthdnhothu = (EditText) dialogContract.findViewById(R.id.edthdnhothu); // hop
        edittkoanhd = (EditText) dialogContract.findViewById(R.id.edittkoanhd); // tai
        edttentkoan = (EditText) dialogContract.findViewById(R.id.edttentkoan); // ten
        txtnganhang = (TextView) dialogContract.findViewById(R.id.txtnganhang); // chon
        txtnganhang.setOnClickListener(this);
        spn_printMethodCode = (Spinner) dialogContract.findViewById(R.id.spinner_inchitiet); // in
        // chi
        // tiet

        edtemailtbc = (EditText) dialogContract.findViewById(R.id.edtemailtbc);
        edtdtcdtbc = (EditText) dialogContract.findViewById(R.id.edtdtcdtbc);
        edtdidongtbc = (EditText) dialogContract.findViewById(R.id.txtdtdidongtbc);
        txtdctbc = (EditText) dialogContract.findViewById(R.id.txtdctbc);
        Button btncancel = (Button) dialogContract.findViewById(R.id.btn_cancel);
        txtdctbc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String strProvince1 = Session.province;
                String strDistris1 = Session.district;

                Bundle mBundle1 = new Bundle();
                mBundle1.putString("strProvince", strProvince1);
                mBundle1.putString("strDistris", strDistris1);
                mBundle1.putString("KEYPR", "1111");
                mBundle1.putBoolean("isCheckStreetBlock", true);
                Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
                i1.putExtras(mBundle1);
                startActivityForResult(i1, 11112);
            }
        });
        edtdchdcuoc = (EditText) dialogContract.findViewById(R.id.edtdchdcuoc);

        // thong tin dai dien hop dong
        lnttdaidienhd = (LinearLayout) dialogContract.findViewById(R.id.lnttdaidienhd);
        // khach hang doanh nghiep
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
            lnttdaidienhd.setVisibility(View.VISIBLE);
        } else {
            lnttdaidienhd.setVisibility(View.GONE);
        }

        lngiaytoxacminh = (LinearLayout) dialogContract.findViewById(R.id.lngiaytoxacminh);

        edtloaikhDD = (EditText) dialogContract.findViewById(R.id.edtloaikh);

        if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer())) {
            if (!CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer().getCustTypeDTO())) {
                edtloaikhDD.setText(accountDTO.getRefCustomer().getCustTypeDTO().getName());
            } else {
                edtloaikhDD.setText(accountDTO.getRefCustomer().getCustTypeName());
            }

        }

        edtloaikhDD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
//                intent.putExtra("arrCustTypeDTOsKey", arrCusType);
                Bundle mBundle = new Bundle();
                mBundle.putString("GROUPKEY", "GROUPKEY");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 1444);

            }
        });

        edit_sogiaytoDD = (EditText) dialogContract.findViewById(R.id.edit_sogiaytoDD);
        edit_ngaycapDD = (EditText) dialogContract.findViewById(R.id.edit_ngaycap);
        edit_ngaycapDD.setText(dateNowString);
        edit_ngaycapDD.setOnClickListener(editTextListener);
        edtnoicap = (EditText) dialogContract.findViewById(R.id.edtnoicap);
        txtdcgtxmDD = (EditText) dialogContract.findViewById(R.id.txtdcgtxm);
        txtdcgtxmDD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String strProvince1 = Session.province;
                String strDistris1 = Session.district;

                Bundle mBundle1 = new Bundle();
                mBundle1.putString("strProvince", strProvince1);
                mBundle1.putString("strDistris", strDistris1);
                mBundle1.putString("KEYPR", "1111");
                mBundle1.putBoolean("isCheckStreetBlock", false);
                Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
                i1.putExtras(mBundle1);
                startActivityForResult(i1, 11113);
            }
        });
        edit_tenKHDD = (EditText) dialogContract.findViewById(R.id.edit_tenKHDD);
        edit_ngaysinhdDD = (EditText) dialogContract.findViewById(R.id.edit_ngaysinhd);
        edit_ngaysinhdDD.setText(dateNowString);
        edit_ngaysinhdDD.setOnClickListener(editTextListener);
        spinner_quoctichpr = (Spinner) dialogContract.findViewById(R.id.spinner_quoctichpr);
        initNationly(spinner_quoctichpr);
        prbCustTypeDD = (ProgressBar) dialogContract.findViewById(R.id.prbCustTypeDD);
        btnRefreshCustTypeDD = (Button) dialogContract.findViewById(R.id.btnRefreshCustType);
        btnkiemtra = (Button) dialogContract.findViewById(R.id.btnkiemtra);

        btnkiemtra.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString().trim())) {

                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                            getActivity().getString(R.string.app_name)).show();
                    return;

                }
                SearchCustIdentityAsyn searchCustIdentityAsyn = new SearchCustIdentityAsyn(getActivity(), "1");
                if (custTypeDTOContractPR != null) {
                    searchCustIdentityAsyn.execute(edit_sogiaytoDD.getText().toString().trim(),
                            custTypeDTOContractPR.getCustType(), typePaperDialogPR);
                } else {
                    searchCustIdentityAsyn.execute(edit_sogiaytoDD.getText().toString().trim(), "", typePaperDialogPR);
                }

            }
        });

        btnedit = (Button) dialogContract.findViewById(R.id.btnedit);

        btnedit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                disenableRepresentCus();

            }
        });
        if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer())
                && !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer().getCustId())) {
            btnkiemtra.setVisibility(View.GONE);
            btnedit.setVisibility(View.GONE);
        } else {
            btnkiemtra.setVisibility(View.VISIBLE);
            btnedit.setVisibility(View.GONE);
        }

        spinner_type_giay_to_parent = (Spinner) dialogContract.findViewById(R.id.spinner_type_giay_to_parent);
        spinner_type_giay_to_parent.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
                    typePaperDialogPR = arrTypePaper.get(position).getIdType();
                    lnngayhethandd.setVisibility(View.GONE);
//                    if(CommonActivity.isNullOrEmpty(arrMapusage)){
                    AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(), new OnPostGetOptionSetValue(), moveLogInAct);
                    asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
//                    }
                    if ("ID".equals(typePaperDialogPR)) {
                        edit_sogiaytoDD.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiaytoDD.setFilters(FilterArray);
                    } else if ("MID".equals(typePaperDialogPR)) {
                        edit_sogiaytoDD.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiaytoDD.setFilters(FilterArray);
                    } else if ("IDC".equals(typePaperDialogPR)) {
                        edit_sogiaytoDD.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 15;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiaytoDD.setFilters(FilterArray);
                    }
                    if ("PASS".equals(typePaperDialogPR)) {
                        // truong hop ho chieu va nhom ca nhan nuoc ngoai thi dia chi an di ko can nhap
                        if (!CommonActivity.isNullOrEmpty(custTypeDTOContractPR) && "3".equals(custTypeDTOContractPR.getCusGroup())) {
                            lnParentAddress.setVisibility(View.GONE);
                        } else {
                            lnParentAddress.setVisibility(View.VISIBLE);
                        }
                        edit_sogiaytoDD.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiaytoDD.setFilters(FilterArray);
                    } else {
                        edit_sogiaytoDD.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiaytoDD.setFilters(FilterArray);
                    }

                } else {
                    typePaperDialogPR = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinner_gioitinhDD = (Spinner) dialogContract.findViewById(R.id.spinner_gioitinh);

        if (!CommonActivity.isNullOrEmptyArray(arrSexBeans)) {
            arrSexBeans = new ArrayList<SexBeans>();
        }
        initSex();
        enableContract();

        if ("1".equalsIgnoreCase(subType) && isNew && !CommonActivity.isNullOrEmptyArray(arrHTTTHD)) {
            AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(activity, TYPE_HTTHHD);
            asyntaskGetListAllCommon.execute();
        }

        btncancel.setVisibility(View.VISIBLE);
        // conTractBean
        if (accountDTO != null) {
            btncancel.setVisibility(View.VISIBLE);
            AccountDTO contract = accountDTO;
            if (contract != null && contract.getAccountId() != null
                    && !CommonActivity.isNullOrEmpty(contract.getAccountNo())) {

                provinceContract = contract.getProvince();

                // CUNG TINH HAN MUC BAT BUOC DUOC CHON

                if ("M".equals(serviceType)) {
                    if (!CommonActivity.isNullOrEmpty(Session.province) && !CommonActivity.isNullOrEmpty(provinceContract) && Session.province.equals(provinceContract)) {
                        lnqouta.setVisibility(View.VISIBLE);

                        lndeposit.setVisibility(View.GONE);

                        spinner_quota.setEnabled(true);
                    } else {
                        lnqouta.setVisibility(View.VISIBLE);
                        lndeposit.setVisibility(View.VISIBLE);
                        spinner_quota.setEnabled(false);
                        if (arrQouta != null && arrQouta.size() > 0) {

                            for (int i = 0; i < arrQouta.size(); i++) {

                                if (CommonActivity.isNullOrEmpty(arrQouta.get(i).getId())) {
                                    spinner_quota.setSelection(arrQouta.indexOf(arrQouta.get(i)));
                                    break;
                                }
                            }
                        }

                    }
                } else {
                    lnqouta.setVisibility(View.VISIBLE);
                    lndeposit.setVisibility(View.GONE);
                    spinner_quota.setEnabled(true);
                    if (arrQouta != null && arrQouta.size() > 0) {

                        for (int i = 0; i < arrQouta.size(); i++) {

                            if (CommonActivity.isNullOrEmpty(arrQouta.get(i).getId())) {
                                spinner_quota.setSelection(arrQouta.indexOf(arrQouta.get(i)));
                                break;
                            }
                        }
                    }
                }

                if (isNew) {
                    enableContract();
                } else {
                    disableContract();
                }
                // init giao dien
                edt_contractNo.setText(contract.getAccountNo()); // so hop dong
            } else {
                enableContract();
                // init giao dien
                edt_contractNo.setText(""); // so hop dong
            }
            edt_signDate.setText(StringUtils.convertDate(contract.getSignDate())); // ngay

            if (!CommonActivity.isNullOrEmpty(contract.getPayMethod()) && arrHTTTHD != null) {
                Utils.setDataSpinner(activity, arrHTTTHD, spn_payMethodCode);
                for (int i = 0; i < arrHTTTHD.size(); i++) {
                    Spin spin = arrHTTTHD.get(i);
                    if (spin.getId().equalsIgnoreCase(contract.getPayMethod())) {
                        spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
                        break;
                    }
                }
            }

            if ("02".equalsIgnoreCase(contract.getPayMethod()) || "03".equalsIgnoreCase(contract.getPayMethod())) {
                lnBankAccount.setVisibility(View.VISIBLE);
            } else {
                lnBankAccount.setVisibility(View.GONE);
            }

            // chu ky cuoc
            if (contract.getBillCycleId() != null && arrCKC != null) {
                Utils.setDataSpinner(activity, arrCKC, spn_billCycleFromCharging);
                for (int i = 0; i < arrCKC.size(); i++) {
                    Spin spin = arrCKC.get(i);
                    if (spin.getId().equalsIgnoreCase(String.valueOf(contract.getBillCycleId()))) {
                        spn_billCycleFromCharging.setSelection(arrCKC.indexOf(spin));
                        break;
                    }
                }
            }
            // in chi tiet
            if (contract.getPrintMethod() != null && arrINCT != null) {
                Utils.setDataSpinner(activity, arrINCT, spn_printMethodCode);
                for (int i = 0; i < arrINCT.size(); i++) {
                    Spin spin = arrINCT.get(i);
                    if (spin.getId().equalsIgnoreCase(contract.getPrintMethod())) {
                        spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
                        break;
                    }
                }
            }

            // edt_streetName.setText(contract.getStreet());
            // edt_home.setText(contract.get()); // so nha
            if (!CommonActivity.isNullOrEmpty(contract.getAddressPrint())) {
                edtdchdcuoc.setText(contract.getAddressPrint());
            } else {
                edtdchdcuoc.setText(contract.getAddress());
            }

            edtdtcdtbc.setText(contract.getPhoneContact()); // dien thoai co
            // dinh
            if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail) && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getTelRegister())) {
                edtdidongtbc.setText(hotLineReponseDetail.getTelRegister()); // dien thoai di
                txtisdn.setText(hotLineReponseDetail.getTelRegister());
                // dong
            } else {
                edtdidongtbc.setText(contract.getTelMobile()); // dien thoai di
                // dong
            }

            edtemailtbc.setText(contract.geteMail()); // email

            // hinh thuc thong bao cuoc
            if (contract.getNoticeCharge() != null && arrHTTBC != null) {
                Utils.setDataSpinner(activity, arrHTTBC, spn_noticeCharge);
                for (int i = 0; i < arrHTTBC.size(); i++) {
                    Spin spin = arrHTTBC.get(i);
                    if (spin.getId().equalsIgnoreCase(contract.getNoticeCharge())) {
                        spn_noticeCharge.setSelection(arrHTTBC.indexOf(spin));
                        break;
                    }
                }
            }

            txtdctbc.setText(contract.getAddress()); // dia chi thong

            AccountBankDTO contractBank = contract.getAccountBank();
            if (contractBank != null) {
                edittkoanhd.setText(contractBank.getAccount()); // bank tai
                edttentkoan.setText(contractBank.getAccountName()); // bank
                txtnganhang.setText(contractBank.getBankName()); // bank ma
                edthdnhothu.setText(contractBank.getBankAccountNo());
                edit_ngaynhothu.setText(StringUtils.convertDate(contractBank.getBankAccountDate())); // bank
            }

            // xy ly thong tin dai dien hop dong cu
            if (contract.getRefCustomer() != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
                lnttdaidienhd.setVisibility(View.VISIBLE);
                lngiaytoxacminh.setVisibility(View.VISIBLE);

                edtloaikhDD.setText(contract.getRefCustomer().getCustTypeName());
                if (contract.getRefCustomer().getListCustIdentity() != null
                        && contract.getRefCustomer().getListCustIdentity().size() > 0) {
                    edit_sogiaytoDD.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdNo());
                    edit_ngaycapDD.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdIssueDate());
                    edtnoicap.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdIssuePlace());
                }

                txtdcgtxmDD.setText(contract.getRefCustomer().getAddress());
                edit_tenKHDD.setText(contract.getRefCustomer().getName());
                edit_ngaysinhdDD.setText(StringUtils.convertDate(contract.getRefCustomer().getBirthDate()));

                if (!CommonActivity.isNullOrEmptyArray(spinNation)
                        && !CommonActivity.isNullOrEmpty(contract.getRefCustomer().getNationality())) {
                    for (Spin spin : spinNation) {
                        if (spin.getId().equals(contract.getRefCustomer().getNationality())) {
                            spinner_quoctichpr.setSelection(spinNation.indexOf(spin));
                            break;
                        } else {
                            if ("VN".equals(spin.getId())) {
                                spinner_quoctichpr.setSelection(spinNation.indexOf(spin));
                                break;
                            }
                        }

                    }
                }

                spinner_type_giay_to_parent = (Spinner) dialogContract.findViewById(R.id.spinner_type_giay_to_parent);
                // spinner_gioitinhDD = (Spinner)
                // dialog.findViewById(R.id.spinner_gioitinh);
                if (!CommonActivity.isNullOrEmpty(contract.getRefCustomer().getSex())) {
                    for (SexBeans spin : arrSexBeans) {
                        if (contract.getRefCustomer().getSex().equals(spin.getValues())) {
                            spinner_gioitinhDD.setSelection(arrSexBeans.indexOf(spin));
                            spinner_gioitinhDD.setEnabled(false);
                            break;
                        }
                    }

                }
            }

        }

        if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
            if (accountDTO != null && accountDTO.getRefCustomer() != null && accountDTO.getRefCustomer().getCustId() != null) {
                enableRepresentCus(accountDTO.getRefCustomer());
            }
        }


        Button btn_insert_contract = (Button) dialogContract.findViewById(R.id.btn_insert_contract_offer);
        btn_insert_contract.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // xu ly them moi hop dong o day

                try {
                    if (validateContract(accountDTO)) {
                        // hop dong cu
                        if (accountDTO != null && accountDTO.getAccountId() != null
                                && !CommonActivity.isNullOrEmpty(accountDTO.getAccountNo())) {
                            accountDTOMain = accountDTO;
                        } else {
                            accountDTOMain.setSignDate(
                                    StringUtils.convertDateToString(edt_signDate.getText().toString().trim()));
                            Spin spinCharge = (Spin) spn_billCycleFromCharging.getSelectedItem();
                            accountDTOMain.setBillCycleId(Long.parseLong(spinCharge.getId()));
                            Spin spinPay = (Spin) spn_payMethodCode.getSelectedItem();
                            accountDTOMain.setPayMethod(spinPay.getId());
                            accountDTOMain.setPayMethodName(spinPay.getValue());
                            Spin spinNotice = (Spin) spn_noticeCharge.getSelectedItem();
                            accountDTOMain.setNoticeCharge(spinNotice.getId());
                            accountDTOMain.setNoticeChargeName(spinNotice.getValue());
                            if ("02".equalsIgnoreCase(spinPay.getId()) || "03".equalsIgnoreCase(spinPay.getId())) {

                                AccountBankDTO accountBank = new AccountBankDTO();

                                if (!CommonActivity.isNullOrEmpty(edittkoanhd.getText().toString().trim())) {
                                    accountBank.setAccount(edittkoanhd.getText().toString().trim());
                                }
                                if (!CommonActivity.isNullOrEmpty(edttentkoan.getText().toString().trim())) {
                                    accountBank.setAccountName(edttentkoan.getText().toString());
                                }
                                if (!CommonActivity.isNullOrEmpty(bankBean.getCode())) {
                                    accountBank.setBankCode(bankBean.getCode());
                                    accountBank.setBankName(bankBean.getName());
                                }
                                if (!CommonActivity.isNullOrEmpty(edit_ngaynhothu.getText().toString())) {
                                    accountBank.setBankAccountDate(
                                            StringUtils.convertDateToString(edit_ngaynhothu.getText().toString()));
                                }
                                if (!CommonActivity.isNullOrEmpty(edthdnhothu.getText().toString())) {
                                    accountBank.setBankAccountNo(edthdnhothu.getText().toString());
                                }
                                accountDTOMain.setAccountBank(accountBank);
                            }
                            Spin spininchitiet = (Spin) spn_printMethodCode.getSelectedItem();
                            accountDTOMain.setPrintMethod(spininchitiet.getId());
                            accountDTOMain.setPrintMethodName(spininchitiet.getValue());
                            if (!CommonActivity.isNullOrEmpty(edtemailtbc.getText().toString())) {
                                accountDTOMain.seteMail(edtemailtbc.getText().toString());
                            }
                            if (!CommonActivity.isNullOrEmpty(edtdidongtbc.getText().toString().trim())) {
                                accountDTOMain.setTelMobile(edtdidongtbc.getText().toString().trim());
                            }
                            accountDTOMain.setProvince(provinceContract);
                            accountDTOMain.setDistrict(districtContract);
                            accountDTOMain.setPrecinct(precintContract);
                            accountDTOMain.setStreetBlock(streetBlockContract);
                            accountDTOMain.setPhoneContact(edtdtcdtbc.getText().toString().trim());
                            if (!CommonActivity.isNullOrEmpty(streetNameContract)) {
                                accountDTOMain.setStreetName(streetNameContract);
                            }
                            if (!CommonActivity.isNullOrEmpty(homeContract)) {
                                accountDTOMain.setHome(homeContract);
                            }
                            accountDTOMain.setAreaCode(
                                    provinceContract + districtContract + precintContract + streetBlockContract);
                            accountDTOMain.setAddress(txtdctbc.getText().toString());
                            accountDTOMain.setBillAddress(txtdctbc.getText().toString());
                            accountDTOMain.setAddressPrint(edtdchdcuoc.getText().toString());
                            accountDTOMainNew = accountDTOMain;
                        }
                        // thong tin khach hang dai dien cu voi hop dong cu co
                        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
                            if (accountDTO != null && accountDTO.getRefCustomer() != null
                                    && accountDTO.getRefCustomer().getCustId() != null) {
                                accountDTOMain.setRefCustomer(accountDTO.getRefCustomer());
                                cbUseRepCus.setVisibility(View.VISIBLE);
                                if (accountDTOMainNew != null) {
                                    accountDTOMainNew.setRefCustomer(accountDTO.getRefCustomer());
                                }
                            } else {
                                // khi chon khach hang cu
                                if (accountDTOMain != null && accountDTOMain.getRefCustomer() != null
                                        && accountDTOMain.getRefCustomer().getCustId() != null) {
                                    dialogContract.dismiss();
                                    return;
                                } else {
                                    // thong tin khach hang dai dien moi
                                    CustomerDTO cusDTO = new CustomerDTO();
                                    cusDTO.setCustType(custTypeDTOContractPR.getCustType());
                                    ArrayList<CustIdentityDTO> arrCustIndentity = new ArrayList<CustIdentityDTO>();
                                    CustIdentityDTO cusIdentity = new CustIdentityDTO();
                                    cusIdentity.setIdNo(edit_sogiaytoDD.getText().toString().trim());
                                    cusIdentity.setIdType(typePaperDialogPR);
                                    cusIdentity.setIdIssueDate(edit_ngaycapDD.getText().toString());
                                    cusIdentity.setIdIssuePlace(edtnoicap.getText().toString());
                                    cusIdentity.setIdExpireDate(edit_ngayhethandd.getText().toString());
                                    arrCustIndentity.add(cusIdentity);

                                    cusDTO.setListCustIdentity(arrCustIndentity);

                                    if (!CommonActivity.isNullOrEmpty(custTypeDTOContractPR) && "3".equals(custTypeDTOContractPR.getCusGroup())) {

                                    } else {
                                        cusDTO.setProvince(provinceContractPR);
                                        cusDTO.setDistrict(districtContractPR);
                                        cusDTO.setPrecinct(precintContractPR);
                                        cusDTO.setStreetBlock(streetBlockContractPR);
                                        if (!CommonActivity.isNullOrEmpty(streetBlockContractPR)) {
                                            cusDTO.setAreaCode(provinceContractPR + districtContractPR + precintContractPR
                                                    + streetBlockContractPR);
                                            cusDTO.setAddress(txtdcgtxmDD.getText().toString());
                                            cusDTO.setStreetName(streetNameContractPR);
                                            cusDTO.setHome(homeContractPR);
                                        } else {
                                            cusDTO.setAreaCode(provinceContractPR + districtContractPR + precintContractPR
                                            );
                                            cusDTO.setAddress(txtdcgtxmDD.getText().toString());
                                            cusDTO.setStreetName(streetNameContractPR);
                                            cusDTO.setHome(homeContractPR);
                                        }

                                    }


                                    cusDTO.setName(edit_tenKHDD.getText().toString().trim());
                                    cusDTO.setBirthDate(edit_ngaysinhdDD.getText().toString());

                                    SexBeans sexBean = (SexBeans) arrSexBeans
                                            .get(spinner_gioitinhDD.getSelectedItemPosition());
                                    cusDTO.setSex(sexBean.getValues());

                                    Spin spin = (Spin) spinner_quoctichpr.getSelectedItem();
                                    if (!CommonActivity.isNullOrEmpty(spin)
                                            && !CommonActivity.isNullOrEmpty(spin.getId())) {
                                        cusDTO.setNationality(spin.getValue());
                                    }

                                    accountDTOMain.setRefCustomer(cusDTO);
                                    if (accountDTOMainNew != null) {
                                        accountDTOMainNew.setRefCustomer(cusDTO);
                                    }
                                    cbUseRepCus.setVisibility(View.VISIBLE);
                                }

                            }
                        }

                        // thientv7 luu thong tin moi can cap nhat
                        Log.d("DATA", "isNew: " + isNew);
                        if (isNew) {
                            if (validateValueFromView()) {
                                getDataFromViewGenerate(isNew);
                                dialogContract.dismiss();
                            }
                        } else {
                            getDataFromViewGenerate(isNew);
                            dialogContract.dismiss();
                        }

                        if (cbUseRepCus.isChecked()) {
                            customerInfo = accountDTOMain.getRefCustomer();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btncancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrTractBeans != null && arrTractBeans.size() > 0) {

                    for (int i = 0; i < arrTractBeans.size(); i++) {
                        if (arrTractBeans.get(i).getAccountNo().equals(getActivity().getString(R.string.txt_select))) {
                            spinner_loaihd.setSelection(arrTractBeans.indexOf(arrTractBeans.get(i)));
                            break;
                        }
                    }

                }
                accountDTOMain = null;

                dialogContract.dismiss();
            }
        });

        // show them thong tin so dien thoai thu 2


        if (!isNew) {
//            Log.i("DATA", "getAccountId: "+accountDTO.getAccountId());
//            Log.i("DATA", "getAddInfo: "+accountDTO.getAddInfo());
            addViewDataWithOldContract(addInfoStr);
        } else {
            GetInfoPlus getInfoPlus = new GetInfoPlus(getActivity(), accountDTOMain.getAccountNo());
            getInfoPlus.execute();
        }


        dialogContract.show();
    }

    // get thong tin nhap tu view tu sinh
    private boolean validateValueFromView() {
        for (int i = 0; i < lstProductSpecCharUseDTOs.size(); i++) {
            ProductSpecCharDTO ps = lstProductSpecCharUseDTOs.get(i).getListProductSpecCharDTOs();


//            String isDefault = ps.getProductSpecCharValueDTOList().getIsDefault();
            CustomEditText customEditText1 = lstEditTexts.get(i);
            CustomEditText customEditText = (CustomEditText) dialogContract.findViewById(i);
            String value = customEditText.getText().toString();
            int maxLength = customEditText1.getMaxCardinality();
            int minLength = customEditText1.getMinCardinality();
            Log.d("DATA", "maxLength: " + maxLength);
            Log.d("DATA", "minLength: " + minLength);
            Log.d("DATA", "isDefault: " + isDefault);
            Log.d("DATA", "value.length(): " + value.length());
            Log.d("DATA", "value: " + value);
            boolean isDefault = customEditText1.isRequired();
            Log.d("DATA", "isDefault: " + isDefault);
            if (isDefault) { // bat buoc

                if (CommonActivity.isNullOrEmpty(value)) {
                    Toast.makeText(activity, ps.getName() + " " + getString(R.string.checkValueInfo), Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (value.length() < minLength || value.length() > maxLength) {
                    String msg = ps.getName() + " " + getString(R.string.checkMinMaxlength, minLength, maxLength);
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                if (value.length() != 0) {
                    if (minLength == 0 && maxLength == 0) {

                    } else {
                        if (value.length() < minLength || value.length() > maxLength) {
                            String msg = ps.getName() + getString(R.string.checkMinMaxlength, minLength, maxLength);
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }

    private void getDataFromViewGenerate(boolean isNew) {
        lstAddInfos.clear();


        if (isNew) {
            for (int i = 0; i < lstProductSpecCharUseDTOs.size(); i++) {
                AddInfo addInfo = new AddInfo();

                ProductSpecCharDTO ps = lstProductSpecCharUseDTOs.get(i).getListProductSpecCharDTOs();


//            String value = lstEditTexts.get(i).getText().toString();

                CustomEditText customEditText = (CustomEditText) dialogContract.findViewById(i);
                String value = customEditText.getText().toString();

                addInfo.setContactType(ps.getCode());
                addInfo.setValue(value);
                addInfo.setContactName(ps.getName());

                lstAddInfos.add(addInfo);

            }
            Gson g = new Gson();
            addInfo = g.toJson(lstAddInfos);

            Log.i("DATA", "addInfo: " + addInfo);
        } else {
            Gson g = new Gson();
            addInfo = g.toJson(lstAddInfoOlds);

            Log.i("DATA", "lstAddInfoOlds: " + addInfo);
        }


    }

    // show data bo sung hopw dong cu
    private void showDataFromOld() {
        String addInfo = accountDTOMain.getAddInfo();
        if (null != addInfo) {

        }
    }

    private View.OnClickListener editTextListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            EditText edt = (EditText) view;
            Calendar cal = Calendar.getInstance();
            if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
                Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");
                cal.setTime(date);

            }

            DatePickerDialog datePicker = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            datePicker.getDatePicker().setTag(view);
            datePicker.show();
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            Object obj = view.getTag();
            if (obj != null && obj instanceof EditText) {

                String month = "";
                String day = "";
                if (selectedMonth + 1 < 10) {
                    month = "0" + (selectedMonth + 1);
                } else {
                    month = "" + (selectedMonth + 1);
                }
                if (selectedDay < 10) {
                    day = "0" + selectedDay;
                } else {
                    day = "" + selectedDay;
                }

                EditText editText = (EditText) obj;
                editText.setText(day + "/" + month + "/" + selectedYear);
            }
        }
    };

    // init typepaper
    private ArrayList<TypePaperBeans> arrTypePaperBeans = null;

    private ArrayAdapter<String> initTypePaper(Spinner spinner_type_giay_to,
                                               ArrayList<TypePaperBeans> _arrTypePaperBeans) {
        GetTypePaperDal dal = null;
        ArrayAdapter<String> adapter = null;
        try {

            if (_arrTypePaperBeans != null) {
                arrTypePaperBeans = _arrTypePaperBeans;
            } else {
                dal = new GetTypePaperDal(activity);
                dal.open();
                arrTypePaperBeans = dal.getLisTypepaper();
                dal.close();
            }
            if (arrTypePaperBeans != null && !arrTypePaperBeans.isEmpty()) {
                adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
                for (TypePaperBeans typePaperBeans : arrTypePaperBeans) {
                    adapter.add(typePaperBeans.getParValues());
                }
                if (spinner_type_giay_to != null) {
                    spinner_type_giay_to.setAdapter(adapter);
                }
            }
        } catch (Exception e) {
            Log.e("initTypePaper", e.toString(), e);
        } finally {
            if (dal != null) {
                dal.close();
            }
        }
        return adapter;
    }

    /**
     * tinh huyen xa to
     */
    private String dialogProvince;
    private String dialogDistrict;
    private String dialogPrecinct;
    private String dialogStreetBlock;

    private void parseResultError(String result) {
        if (result != null) {
            try {
                XmlDomParse domParse = new XmlDomParse();
                Document document = domParse.getDomElement(result);

                NodeList nodeListErrorCode = document.getElementsByTagName("return");

                for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
                    Node mNode = nodeListErrorCode.item(i);
                    Element element = (Element) mNode;
                    String errorCode = domParse.getValue(element, "errorCode");
                    String description = domParse.getValue(element, "description");
                    String token = domParse.getValue(element, "token");
                    if (token == null || token.equals("")) {

                    } else {
                        Session.setToken(token);
                    }
                    if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                        CommonActivity.BackFromLogin(activity, description);
                    } else if (errorCode.equals("0")) {
                    }
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }
        }
    }

    protected void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.edtdiachilapdat:

                String strProvince1 = Session.province;
                String strDistris1 = Session.district;

                Bundle mBundle1 = new Bundle();
                mBundle1.putString("strProvince", strProvince1);
                mBundle1.putString("strDistris", strDistris1);
                mBundle1.putString("checkPCProduct", checkPCProduct);
                mBundle1.putString("KEYPR", "1111");
                mBundle1.putBoolean("isCheckStreetBlock", true);
                Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
                i1.putExtras(mBundle1);
                startActivityForResult(i1, 11111);

                break;
            case R.id.edtNgayBD:
                showDatePickerDialog(edtNgayBD);
                break;
            case R.id.edtNgayKT:
                showDatePickerDialog(edtNgayKT);
                break;
            case R.id.edtngaysinhhs:
                showDatePickerDialog(edtngaysinhhs);
                break;
            case R.id.relaBackHome:
                activity.onBackPressed();
                break;

            case R.id.tvpakage:
                if (pakageBundeBean != null && pakageBundeBean.getArrChargeBeans().size() > 0) {
                    Intent intent = new Intent(activity, FragmentSearchPakageMobileBCCS.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("PakageKey", pakageBundeBean);
                    intent.putExtras(mBundle);
                    startActivityForResult(intent, 3333);
                } else {
                    CommonActivity
                            .createAlertDialog(activity, getString(R.string.notpakagemobile), getString(R.string.app_name))
                            .show();
                }
                break;
            case R.id.spinner_loaithuebao:
            case R.id.img_searchloaitb:
                if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
                    CategoryTBBundeBeanBCCS categoryTBBundeBeanBCCS = new CategoryTBBundeBeanBCCS();
                    categoryTBBundeBeanBCCS.setArrBeans(arrSubTypeBeans);

                    Intent intent = new Intent(activity, FragmentSearchCategoryTBBCCS.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("loaithuebao", categoryTBBundeBeanBCCS);
                    intent.putExtras(mBundle);
                    startActivityForResult(intent, 19877);
                } else {
                    CommonActivity
                            .createAlertDialog(activity, getString(R.string.notloaitb), getString(R.string.app_name))
                            .show();
                }
                break;
            case R.id.btn_connection:
                isdn = txtisdn.getText().toString();
                if (isdn != null && !isdn.isEmpty()) {
                    if (isdn.substring(0, 1).equalsIgnoreCase("0")) {
                        isdn = isdn.substring(1, isdn.length());
                    }
                }
                if ("2".equalsIgnoreCase(subType)) {
                    if (validatePre()) {
                        checkHangHoa();
                    }
                } else {
                    // thue bao tra sau
                    validateSubPos();
                }

                break;

            case R.id.edt_hthm:
                Log.d(Constant.TAG,
                        "FragmentConnectionMobile onClick R.id.edt_hthm arrHthmBeans.size() " + arrHthmBeans.size());

                if (arrHthmBeans.size() > 0) {
                    Intent intent = new Intent(activity, SearchCodeHthmActivity.class);
                    intent.putExtra("arrHthmBeans", arrHthmBeans);
                    startActivityForResult(intent, 1001);
                }

                break;
            case R.id.edt_kmai:
                if (arrPromotionTypeBeans.size() > 0) {
                    Intent intent = new Intent(activity, SearchCodePromotionActivity.class);
                    intent.putExtra("arrPromotionTypeBeans", arrPromotionTypeBeans);
                    intent.putExtra("productCode", packageCode);
                    startActivityForResult(intent, 102);
                }

                break;

            case R.id.txtnganhang:
                showDialogListBank();
                break;

            case R.id.btnhttthd:
                new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTHHD, null);
                isRefreshTYPE_HTTTHD = true;
                AsyntaskGetListAllCommon asnGetHHTH = new AsyntaskGetListAllCommon(activity, TYPE_HTTHHD);
                asnGetHHTH.execute();
                break;
            case R.id.btnchukicuoc:
                new CacheDatabaseManager(activity).insertCacheList(TYPE_CK_CUOC_HD, null);
                isRefreshTYPE_CK_CUOC_HD = true;
                AsyntaskGetListAllCommon asnGetBillCycle = new AsyntaskGetListAllCommon(activity, TYPE_CK_CUOC_HD);
                asnGetBillCycle.execute();
                break;
            case R.id.btninchitiet:
                new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTHHD, null);
                isRefreshTYPE_INCT_HD = true;
                AsyntaskGetListAllCommon asnInChiTiet = new AsyntaskGetListAllCommon(activity, TYPE_INCT_HD);
                asnInChiTiet.execute();
                break;
            case R.id.btnhttbc:
                new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTBC_HD, null);
                isRefreshTYPE_HTTBC_HD = true;
                AsyntaskGetListAllCommon asnHTTBC = new AsyntaskGetListAllCommon(activity, TYPE_HTTBC_HD);
                asnHTTBC.execute();
                break;
            case R.id.lnttinphidn:
                if (validateViewFee()) {
                    FindFeeByReasonTeleIdAsyn findFeeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(getActivity());
                    findFeeByReasonTeleIdAsyn.execute(telecomserviceId, reasonId, productCode);
                }
                break;
            case R.id.btnkiemtraserial:
                if (validateSerial()) {
                    CheckResourceAsyn checkResourceAsyn = new CheckResourceAsyn(getActivity(), null, null, null);
                    checkResourceAsyn.execute(txtserial.getText().toString().trim(), hthmBeans.getReasonId(), "");
                }
                break;
            case R.id.lnuserInfo:

                break;
            default:
                break;
        }
    }

    private boolean validateSerial() {
        if (CommonActivity.isNullOrEmpty(hthm)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkregtype),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(txtserial.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateserial),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        return true;
    }

    private boolean validateViewFee() {
        if (CommonActivity.isNullOrEmpty(telecomserviceId)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkserviceType),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(productCode)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkpakecharge),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonId)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkregtype),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        return true;
    }

    private boolean validatePre() {
        if (CommonActivity.isNullOrEmpty(serviceType)) {
            Toast.makeText(activity, getString(R.string.checkserviceType), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(offerId)) {
            Toast.makeText(activity, getString(R.string.checkpakage), Toast.LENGTH_SHORT).show();
            return false;
        }

        if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)) {

            if (CommonActivity.isNullOrEmpty(tvDonVi.getText().toString().trim())) {
                Toast.makeText(activity, getString(R.string.chekdonvi), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mCodeDonVi)) {
                Toast.makeText(activity, getString(R.string.chekdonvi), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(edtMaGiayToDacBiet.getText().toString().trim())) {
                Toast.makeText(activity, getString(R.string.checkidnospec), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(edtNgayBD.getText().toString())) {
                Toast.makeText(activity, getString(R.string.chekngayBD), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (dateBD.after(dateNow)) {
                Toast.makeText(activity, getString(R.string.chekngayBD1), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (dateEnd.before(dateBD)) {
                Toast.makeText(activity, getString(R.string.starttime_small_endtime), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if ("SPEC_HISCL".equals(checkIsSpec)) {
            if (CommonActivity.isNullOrEmpty(edttenhs.getText().toString())) {
                Toast.makeText(activity, getString(R.string.tenhsempty), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (edttenhs.getText().toString().trim().length() < 4
                    || edttenhs.getText().toString().trim().length() > 120) {
                Toast.makeText(activity, getString(R.string.tenhs4), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (birthDateHs.after(dateNow)) {
                Toast.makeText(activity, getString(R.string.ngaysinhnhohonngayhientai), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if ("H".equals(serviceType)) {
            if (CommonActivity.isNullOrEmpty(province) && CommonActivity.isNullOrEmpty(district)
                    && CommonActivity.isNullOrEmpty(precinct)) {
                Toast.makeText(activity, getString(R.string.addzoneempty), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!CommonActivity.isNullOrEmpty(checkPCProduct) && "true".equals(checkPCProduct)
                && CommonActivity.isNullOrEmpty(province) && CommonActivity.isNullOrEmpty(district)) {
            Toast.makeText(activity, getString(R.string.addzoneempty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(hthm)) {
            Toast.makeText(activity, getString(R.string.checkhthm), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(txtisdn.getText().toString())) {
            Toast.makeText(activity, getString(R.string.checkisdn), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CommonActivity.getStardardIsdnBCCS(txtisdn.getText().toString().trim()).length() < 9
                || CommonActivity.getStardardIsdnBCCS(txtisdn.getText().toString().trim()).length() > 11) {
            Toast.makeText(activity, getString(R.string.check_length_isdn), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(txtserial.getText().toString())) {
            Toast.makeText(activity, getString(R.string.checkserial), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtserial.getText().toString().trim().length() != 19) {
            Toast.makeText(activity, getString(R.string.checkserial19), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(subObject)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.doituong_not_select), getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if ("M".equals(serviceType)) {
            try {
                Date dateSign = sdf.parse(edit_ngaykySub.getText().toString());
                if (dateSign.after(dateNow)) {
                    Toast.makeText(activity, getString(R.string.signDateEmptyThan), Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
        }

        return true;
    }

    private void validateSubPos() {
        if(validatePos()){
            if (!StringUtils.CheckCharSpecical(txtserial.getText().toString())) {
                                        if (!CommonActivity.isNullOrEmpty(subObject)) {
                if (validateQoutaAndDeposit()) {
                    checkHangHoatrasau();
                }
            } else {
                                        Toast.makeText(activity, getString(R.string.doituong_not_select), Toast.LENGTH_SHORT).show();
                                    }
                Toast.makeText(activity, getString(R.string.checkspecialSerial),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validatePos(){
        if(CommonActivity.isNullOrEmpty(serviceType)){
            Toast.makeText(activity, getString(R.string.checkserviceType), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(CommonActivity.isNullOrEmpty(offerId)){
            Toast.makeText(activity, getString(R.string.checkpakage), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(CommonActivity.isNullOrEmpty(subTypeMobile)) {
            Toast.makeText(activity, getString(R.string.checktypeSub), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(CommonActivity.isNullOrEmpty(hthm)){
            Toast.makeText(activity, getString(R.string.checkhthm), Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(CommonActivity.isNullOrEmpty(maKM)){
            Toast.makeText(getActivity(), getResources().getString(R.string.checkselectmakm),
                    Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(CommonActivity.isNullOrEmpty(maKM)){
            Toast.makeText(getActivity(), getResources().getString(R.string.checkselectmakm),
                    Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(CommonActivity.isNullOrEmpty(txtisdn.getText().toString())){
            Toast.makeText(activity, getString(R.string.checkisdn), Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(CommonActivity.isNullOrEmpty(txtserial.getText().toString())){
            Toast.makeText(activity, getString(R.string.checkserial), Toast.LENGTH_SHORT)
                    .show();
            return  false;
        }
        if(CommonActivity.isNullOrEmpty(accountDTOMain)){
            Toast.makeText(activity, getString(R.string.chua_chon_hop_dong), Toast.LENGTH_SHORT)
                    .show();
            return  false;
        }


        return true;
    }

    // validate thong tin dat coc
    private boolean validateQoutaAndDeposit() {
        if ("REG_SPECIAL".equals(checkSpecRegtype)) {

            if (CommonActivity.isNullOrEmpty(tvDonVi.getText().toString().trim())) {
                Toast.makeText(activity, getString(R.string.chekdonvi), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mCodeDonVi)) {
                Toast.makeText(activity, getString(R.string.chekdonvi), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(edtMaGiayToDacBiet.getText().toString().trim())) {
                Toast.makeText(activity, getString(R.string.checkidnospec), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(edtNgayBD.getText().toString())) {
                Toast.makeText(activity, getString(R.string.chekngayBD), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (dateBD.after(dateNow)) {
                Toast.makeText(activity, getString(R.string.chekngayBD1), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (dateEnd.before(dateBD)) {
                Toast.makeText(activity, getString(R.string.starttime_small_endtime), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if ("H".equals(serviceType)) {
            if (CommonActivity.isNullOrEmpty(province) && CommonActivity.isNullOrEmpty(district)
                    && CommonActivity.isNullOrEmpty(precinct)) {
                Toast.makeText(activity, getString(R.string.addzoneempty), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!CommonActivity.isNullOrEmpty(checkPCProduct) && "true".equals(checkPCProduct)
                && CommonActivity.isNullOrEmpty(province) && CommonActivity.isNullOrEmpty(district)) {
            Toast.makeText(activity, getString(R.string.addzoneempty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if ("REGTYPE_IMIE".equals(checkCharRegtype)) {
            if ("-2".equals(maKM)) {
                Toast.makeText(getActivity(), getResources().getString(R.string.checkselectmakm), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }

        if (accountDTOMain == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.contractEmpty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        // if (CommonActivity.isNullOrEmpty(accountDTOMain.getAccountNo())) {
        // CommonActivity.createAlertDialog(getActivity(),
        // getActivity().getString(R.string.contractEmpty),
        // getActivity().getString(R.string.app_name)).show();
        // return false;
        // }
        Spin spinQuota = (Spin) spinner_quota.getSelectedItem();
        Spin spinDeposit = (Spin) spinner_deposit.getSelectedItem();

        if ("M".equals(serviceType)) {
            // cung tinh thi bat buon chon han muc va dat coc = 0
            if (!CommonActivity.isNullOrEmpty(Session.province) && !CommonActivity.isNullOrEmpty(provinceContract) && Session.province.equals(provinceContract)) {
                if (spinQuota == null) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.qoutaEmpty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(spinQuota.getId())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.qoutaEmpty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            } else {

                // khac tinh bat buoc truyen dat coc
                if (spinDeposit == null) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.depositEmpty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(spinDeposit.getId())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.depositEmpty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            }
        } else {
            if (spinQuota == null) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.qoutaEmpty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(spinQuota.getId())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.qoutaEmpty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

        // if (ActivityCreateNewRequestMobileNew.instance != null
        // &&
        // !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType))
        // {
        // ReasonDTO spin = (ReasonDTO) spinner_lycd.getSelectedItem();
        // if(spin == null){
        // CommonActivity.createAlertDialog(getActivity(),
        // getActivity().getString(R.string.validatecd),
        // getActivity().getString(R.string.app_name)).show();
        // return false;
        // }
        // if(CommonActivity.isNullOrEmpty(spin.getReasonId())){
        // CommonActivity.createAlertDialog(getActivity(),
        // getActivity().getString(R.string.validatecd),
        // getActivity().getString(R.string.app_name)).show();
        // return false;
        // }
        // }
        return true;
    }

    private boolean isUploadImage() {
        Log.d(Constant.TAG, "isUploadImage() hashmapFileObj: " + hashmapFileObj.size() + " " + hashmapFileObj);
        Log.d(Constant.TAG,
                "isUploadImage() mapListRecordPrepaid: " + mapListRecordPrepaid.size() + " " + mapListRecordPrepaid);

        boolean isUploadImage = !hashmapFileObj.isEmpty() && hashmapFileObj.size() == mapListRecordPrepaid.size();

        return isUploadImage;
    }

    private void checkHangHoa() {
        if (arrStockTypeBeans.size() > 0) {
            if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {

                if (mapListRecordPrepaid != null && !mapListRecordPrepaid.isEmpty()) {

//                    boolean isSelectIm = isUploadImage();// true;
                    if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                        subConnectPre();
                    } else {
                        Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                    }
                } else {
                    CommonActivity.createAlertDialog(activity, getString(R.string.checkthongtinchungtu),
                            getString(R.string.app_name)).show();
                }

            } else {
                Toast.makeText(activity, getResources().getString(R.string.checkhanghoa), Toast.LENGTH_LONG).show();
            }

        } else {

            if (mapListRecordPrepaid != null && !mapListRecordPrepaid.isEmpty()) {
//
//                boolean isSelectIm = isUploadImage();// true;
                if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                    subConnectPre();
                } else {
                    Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                }
            } else {
                CommonActivity.createAlertDialog(activity, getString(R.string.checkthongtinchungtu),
                        getString(R.string.app_name)).show();
            }
        }
    }

    private void checkHangHoatrasau() {
         if (arrPaymentPrePaidPromotionBeans != null && arrPaymentPrePaidPromotionBeans.size() > 1) {
            if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
                if (arrStockTypeBeans.size() > 0) {
                    if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
//                        boolean isSelectIm = isUploadImage();// true;
                        if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                            if ("M".equals(serviceType) && !Session.province.equals(provinceContract)) {
                                // String isdn, String telecomserviceId, Stringln
                                // reasonId,String productCode, String
                                // promotionCode
                                if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                                    GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                            getActivity());
                                    getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                            telecomserviceId, reasonId, productCode, maKM);
                                } else {
                                    subConnectPos();
                                }

                            } else {
                                subConnectPos();
                            }

                        } else {
                            Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.checkhanghoa), Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
//                    boolean isSelectIm = isUploadImage();// true;
                    if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                        if ("M".equals(serviceType) && !Session.province.equals(provinceContract)) {
                            // String isdn, String telecomserviceId, String
                            // reasonId,String productCode, String promotionCode
                            if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                                GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                        getActivity());
                                getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                        telecomserviceId, reasonId, productCode, maKM);
                            } else {
                                subConnectPos();
                            }
                        } else {
                            subConnectPos();
                        }

                    } else {
                        Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                    }
                }
            } else {

                if (arrStockTypeBeans.size() > 0) {
                    if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
//                        boolean isSelectIm = isUploadImage();// true;
                        if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                            if ("M".equals(serviceType) && !Session.province.equals(provinceContract)) {
                                // String isdn, String telecomserviceId, String
                                // reasonId,String productCode, String
                                // promotionCode
                                if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                                    GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                            getActivity());
                                    getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                            telecomserviceId, reasonId, productCode, maKM);
                                } else {
                                    subConnectPos();
                                }
                            } else {
                                subConnectPos();
                            }
                        } else {
                            Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.checkhanghoa), Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
//                    boolean isSelectIm = isUploadImage();// true;
                    if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                        if ("M".equals(serviceType) && !Session.province.equals(provinceContract)) {
                            // String isdn, String telecomserviceId, String
                            // reasonId,String productCode, String promotionCode
                            if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                                GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                        getActivity());
                                getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                        telecomserviceId, reasonId, productCode, maKM);
                            } else {
                                subConnectPos();
                            }
                        } else {
                            subConnectPos();
                        }
                    } else {
                        Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                    }
                }

            }

            // }

        } else {
            if (arrStockTypeBeans.size() > 0) {
                if (mapSubStockModelRelReq.size() == arrStockTypeBeans.size()) {
//                    boolean isSelectIm = isUploadImage();// true;
                    if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                        if ("M".equals(serviceType) && !Session.province.equals(provinceContract)) {
                            // String isdn, String telecomserviceId, String
                            // reasonId,String productCode, String promotionCode
                            if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                                GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                        getActivity());
                                getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                        telecomserviceId, reasonId, productCode, maKM);
                            } else {
                                subConnectPos();
                            }
                        } else {
                            subConnectPos();
                        }
                    } else {
                        Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.checkhanghoa), Toast.LENGTH_LONG).show();
                }

            } else {
//                boolean isSelectIm = isUploadImage();// true;
                if ((hashmapFileObj != null && hashmapFileObj.size() > 0) || profileBO.getProfileRecords() != null) {
                    if ("M".equals(serviceType) && !Session.province.equals(provinceContract)) {
                        // String isdn, String telecomserviceId, String
                        // reasonId,String productCode, String promotionCode
                        if (!CommonActivity.isNullOrEmpty(maKM) && !"-2".equals(maKM)) {

                            GetRequiredLimitUsageForCommitmentAsyn getRequiredLimitUsageForCommitmentAsyn = new GetRequiredLimitUsageForCommitmentAsyn(
                                    getActivity());
                            getRequiredLimitUsageForCommitmentAsyn.execute(txtisdn.getText().toString().trim(),
                                    telecomserviceId, reasonId, productCode, maKM);
                        } else {
                            subConnectPos();
                        }
                    } else {
                        subConnectPos();
                    }

                } else {
                    Toast.makeText(activity, getString(R.string.chua_chon_het_anh), Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void subConnectPre() {

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO)
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustTypeDTO())
                && !"1".equals(custIdentityDTO.getCustomer().getCustTypeDTO().getPlan())) {
            if (CommonActivity.isNullOrEmpty(custypeKey)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.updatecustype), getActivity().getString(R.string.app_name), onclickChangeCustype).show();
                return;
            }
        }


        if (!CommonActivity.isNullOrEmpty(custIdentityDTO)
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getRepreCustomer())
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getRepreCustomer().getCustomer().getCustId())
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getRepreCustomer().getCustomer().getCustTypeDTO())
                && !"1".equals(custIdentityDTO.getRepreCustomer().getCustomer().getCustTypeDTO().getPlan())) {
            if (CommonActivity.isNullOrEmpty(custypeKeyPr)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.updatecustypePr), getActivity().getString(R.string.app_name), onclickChangeCustypePr).show();
                return;
            }
        }

        if ("M".equals(serviceType) && "2".equals(custIdentityDTO.getGroupType()) && !"used_device".equals(checkIsSpec)) {
            if (customerInfo == null) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notselectpeopleused), getActivity().getString(R.string.app_name)).show();
                return;
            }
        }


//        if ("M".equals(serviceType) && "2".equals(subType) && isMoreThan) {
//            if (!validateProfileContract()) {
//                CommonActivity.createAlertDialog(getActivity(), descriptionNotice, getActivity().getString(R.string.app_name)).show();
//                return;
//            }
//        }

        if (CommonActivity.isNetworkConnected(activity)) {

            String des = activity.getResources().getString(R.string.confirm_connect_sub);
            if (ActivityCreateNewRequestMobileNew.instance != null
                    && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)
                    && Constant.CHANGE_POS_TO_PRE.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                des = getActivity().getString(R.string.confirmtsstt, txtisdn.getText().toString().trim());
            }


            CommonActivity.createDialog(activity, des, activity.getResources().getString(R.string.app_name),
                    activity.getResources().getString(R.string.cancel),
                    activity.getResources().getString(R.string.buttonOk), null, subConnectClickPre).show();

        } else {
            CommonActivity.createAlertDialog(activity, activity.getResources().getString(R.string.errorNetwork),
                    activity.getResources().getString(R.string.app_name)).show();
        }

    }

    OnClickListener onclickChangeCustype = new OnClickListener() {
        @Override
        public void onClick(View v) {
            saveTemplate();

            Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("GROUPTYPEKEY", custIdentityDTO.getCustomer().getCustTypeDTO().getGroupType());
            mBundle.putString("CUSTYPEKEY", custIdentityDTO.getCustomer().getCustTypeDTO().getCustType());
            intent.putExtras(mBundle);
            startActivityForResult(intent, 1511);
        }
    };
    OnClickListener onclickChangeCustypePr = new OnClickListener() {
        @Override
        public void onClick(View v) {
            saveTemplate();

            Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
            Bundle mBundle = new Bundle();
            if ("1".equals(subType)) {
                mBundle.putString("GROUPTYPEKEY", accountDTOMain.getRefCustomer().getCustTypeDTO().getGroupType());
                mBundle.putString("CUSTYPEKEY", accountDTOMain.getRefCustomer().getCustTypeDTO().getCustType());
            } else {
                mBundle.putString("GROUPTYPEKEY", custIdentityDTO.getRepreCustomer().getCustomer().getCustTypeDTO().getGroupType());
                mBundle.putString("CUSTYPEKEY", custIdentityDTO.getRepreCustomer().getCustomer().getCustTypeDTO().getCustType());
            }

            intent.putExtras(mBundle);
            startActivityForResult(intent, 1512);
        }
    };

    private void subConnectPos() {
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO)
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())
                && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustTypeDTO())
                && !"1".equals(custIdentityDTO.getCustomer().getCustTypeDTO().getPlan())) {
            if (CommonActivity.isNullOrEmpty(custypeKey)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.updatecustype), getActivity().getString(R.string.app_name), onclickChangeCustype).show();
                return;
            }
        }
        if (!CommonActivity.isNullOrEmpty(accountDTOMain)
                && !CommonActivity.isNullOrEmpty(accountDTOMain.getRefCustomer())
                && !CommonActivity.isNullOrEmpty(accountDTOMain.getRefCustomer().getCustId())
                && !CommonActivity.isNullOrEmpty(accountDTOMain.getRefCustomer().getCustTypeDTO())
                && !"1".equals(accountDTOMain.getRefCustomer().getCustTypeDTO().getPlan())) {
            if (CommonActivity.isNullOrEmpty(custypeKeyPr)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.updatecustypePr), getActivity().getString(R.string.app_name), onclickChangeCustypePr).show();
                return;
            }
        }
//        if("M".equals(serviceType) && "2".equals(custIdentityDTO.getGroupType()) && !"used_device".equals(checkIsSpec)){
//            if(customerInfo == null){
//                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notselectpeopleused), getActivity().getString(R.string.app_name)).show();
//                return;
//            }
//        }
        if ("M".equals(serviceType)) {
            try {
                Date dateSign = sdf.parse(edit_ngaykySub.getText().toString());
                if (dateSign.after(dateNow)) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.signDateEmptyThan), getActivity().getString(R.string.app_name)).show();
                    return;
                }
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
        }

        if (CommonActivity.isNetworkConnected(activity)) {
            String des = activity.getResources().getString(R.string.confirm_connect_sub);
            if (ActivityCreateNewRequestMobileNew.instance != null
                    && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)
                    && Constant.CHANGE_PRE_TO_POS.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                des = getActivity().getString(R.string.confirmttsts, txtisdn.getText().toString().trim());
            }
            CommonActivity.createDialog(activity, des, activity.getResources().getString(R.string.app_name),
                    activity.getResources().getString(R.string.cancel),
                    activity.getResources().getString(R.string.buttonOk), null, subConnectClickPos).show();

        } else {
            CommonActivity.createAlertDialog(activity, activity.getResources().getString(R.string.errorNetwork),
                    activity.getResources().getString(R.string.app_name)).show();
        }

    }

    OnClickListener subConnectClickPre = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (CommonActivity.isNetworkConnected(activity)) {
                saveTemplate();

                timeStart = new Date();
//                if (ActivityCreateNewRequestMobileNew.instance != null
//                        && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)
//                        && Constant.CHANGE_POS_TO_PRE.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
//                    SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, "changePosToPre");
//                    subConnectPreAsyn.execute();
//                } else {
//                    SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, "connectMobilePrePaid");
//                    subConnectPreAsyn.execute();
//                }
                submitProfile();
            } else {
                CommonActivity
                        .createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
                        .show();
            }
        }
    };

    OnClickListener subConnectClickPos = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (CommonActivity.isNetworkConnected(activity)) {
                saveTemplate();

                timeStart = new Date();
//                if (ActivityCreateNewRequestMobileNew.instance != null
//                        && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)
//                        && Constant.CHANGE_PRE_TO_POS.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
//                    SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, "changePreToPos");
//                    subConnectPreAsyn.execute();
//                } else {
//                    SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, "connectMobilePostPaid");
//                    subConnectPreAsyn.execute();
//                }
                submitProfile();

            } else {
                CommonActivity
                        .createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
                        .show();
            }
        }
    };

    // LOAD DATA THUE BAO TRA SAU
    private void CheckTBTraSau() {
        if (subType != null && !subType.equals("")) {

            if (subType.equals("1")) {
                // co kuyen mai == thue bao tra sau
                lnkhuyenmai.setVisibility(View.VISIBLE);
                lnloaithuebao.setVisibility(View.VISIBLE);
                lnhopdong.setVisibility(View.VISIBLE);
                lnquotaanddeposit.setVisibility(View.VISIBLE);
            } else {
                lnkhuyenmai.setVisibility(View.GONE);
                lnloaithuebao.setVisibility(View.GONE);
                lnhopdong.setVisibility(View.GONE);
                lnquotaanddeposit.setVisibility(View.GONE);
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
        txtpakage.setText(Html.fromHtml("<u>" + getString(R.string.chonpakage_mobile) + "</u>"));
        pakaChargeBeans = new ProductOfferingDTO();
        if (arrPakageChargeBeans != null && arrPakageChargeBeans.size() > 0) {
            arrPakageChargeBeans.clear();
        }
        if (pakageBundeBean.getArrChargeBeans() != null && pakageBundeBean.getArrChargeBeans().size() > 0) {
            pakageBundeBean = new PakageBundeBeanBCCS();
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
        if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0 && adapProductAdapter != null) {
            arrStockTypeBeans = new ArrayList<ProductOfferTypeDTO>();
            adapProductAdapter = new ThongTinHHAdapterBCCS(arrStockTypeBeans, getActivity(),
                    FragmentConnectionMobileNew.this);
            lvproduct.setAdapter(adapProductAdapter);
            adapProductAdapter.notifyDataSetChanged();
        }
        if (mapSubStockModelRelReq != null && mapSubStockModelRelReq.size() > 0) {
            mapSubStockModelRelReq.clear();
        }

    }

    // lay ma tinh/thanhpho

    private void initProvince(Spinner province, ArrayList<AreaBean> _arrProvince, int _spn_province_pos) {
        try {
            // if (_arrProvince != null) {
            // arrProvince = _arrProvince;
            // } else {
            GetAreaDal dal = new GetAreaDal(activity);
            dal.open();
            arrProvince = dal.getLstProvince();
            dal.close();
            // }

            AreaBean spinProvince = new AreaBean();
            spinProvince.setProvince("");
            spinProvince.setNameProvince(getString(R.string.txt_select));
            arrProvince.add(0, spinProvince);

            if (arrProvince != null && arrProvince.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                for (AreaBean areaBean : arrProvince) {
                    adapter.add(areaBean.getNameProvince());
                }
                province.setAdapter(adapter);
                // if(_spn_province_pos > 0 && _spn_province_pos <
                // arrProvince.size()) {
                province.setSelection(arrProvince.indexOf(arrProvince.get(0)));
                // }
            }
        } catch (Exception ex) {
            Log.e("initProvince", ex.toString());
        }
    }

    // lay huyen/quan theo tinh
    private void initDistrict(String province, Spinner district, int _spn_district_pos) {
        try {
            // boolean sameDistrict = false;
            // if(arrDistrict != null && !arrDistrict.isEmpty()) {
            // AreaBean areaBean = arrDistrict.get(0);
            // if(province.equalsIgnoreCase(areaBean.getProvince())) {
            // sameDistrict = true;
            // }
            // }

            if (!CommonActivity.isNullOrEmpty(province)) {
                GetAreaDal dal = new GetAreaDal(activity);
                dal.open();
                arrDistrict = dal.getLstDistrict(province);
                dal.close();
                Log.d(Constant.TAG, "initDistrict province: " + province + " spn_district_pos: " + _spn_district_pos);
            }

            AreaBean spinDistrict = new AreaBean();
            spinDistrict.setDistrict("");
            spinDistrict.setNameDistrict(getString(R.string.txt_select));
            arrDistrict.add(0, spinDistrict);

            if (arrDistrict != null && arrDistrict.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                for (AreaBean areaBean : arrDistrict) {
                    adapter.add(areaBean.getNameDistrict());
                }
                Log.d(this.getClass().getSimpleName(), "setAdapter" + arrDistrict.size());
                district.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                // if(_spn_district_pos > 0 && _spn_district_pos <
                // arrDistrict.size()) {
                // district.setSelection(arrDistrict.indexOf(arrDistrict.get(0)));
                // }
            }
        } catch (Exception ex) {
            Log.e("initDistrict", ex.toString());
        }
    }

    // lay phuong/xa theo tinh,qhuyen
    private void initPrecinct(String province, String district, Spinner precinct, int _spn_precinct_pos) {
        try {
            // boolean samePrecinct = false;
            // if(arrPrecinct != null && !arrPrecinct.isEmpty()) {
            // AreaBean areaBean = arrPrecinct.get(0);
            // if(province.equalsIgnoreCase(areaBean.getProvince()) &&
            // district.equalsIgnoreCase(areaBean.getDistrict())) {
            // samePrecinct = true;
            // }
            // }
            if (!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district)) {
                GetAreaDal dal = new GetAreaDal(activity);
                dal.open();
                arrPrecinct = dal.getLstPrecinct(province, district);
                dal.close();
            }
            AreaBean spinPrecinct = new AreaBean();
            spinPrecinct.setPrecinct("");
            spinPrecinct.setNamePrecint(getString(R.string.txt_select));
            arrPrecinct.add(0, spinPrecinct);

            if (arrPrecinct != null && arrPrecinct.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                for (AreaBean areaBean : arrPrecinct) {
                    adapter.add(areaBean.getNamePrecint());
                }
                precinct.setAdapter(adapter);
                // if(_spn_precinct_pos > 0 && _spn_precinct_pos <
                // arrPrecinct.size()) {
                precinct.setSelection(arrPrecinct.indexOf(arrPrecinct.get(0)));
                // }
            }
        } catch (Exception ex) {
            Log.e("initPrecinct", ex.toString(), ex);
        }
    }

    // lay dich vu
    private void initTelecomService() {
        try {

            GetServiceDal dal = new GetServiceDal(activity);
            dal.open();
            arrTelecomServiceBeans = dal.getlisServiceMobile();
            dal.close();
            TelecomServiceBeans serviceBeans = new TelecomServiceBeans();
            serviceBeans.setTele_service_name(activity.getResources().getString(R.string.chondichvu));
            arrTelecomServiceBeans.add(0, serviceBeans);
            if (arrTelecomServiceBeans != null && !arrTelecomServiceBeans.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                        android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
                    adapter.add(telecomServiceBeans.getTele_service_name());
                }
                spinner_serviceMobile.setAdapter(adapter);
            

                // omini
                if(!CommonActivity.isNullOrEmpty(hotLineReponseDetail) && arrTelecomServiceBeans.size() > 1){
                    spinner_serviceMobile.setSelection(1);
                }
            }




            if (ActivityCreateNewRequestMobileNew.instance != null
                    && ActivityCreateNewRequestMobileNew.instance.subscriberDTO != null
                    && !CommonActivity.isNullOrEmpty(
                    ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getTelecomServiceId())) {
                Long telecomserviceId = ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getTelecomServiceId();

                if (!CommonActivity.isNullOrEmptyArray(arrTelecomServiceBeans)) {
                    for (TelecomServiceBeans spin : arrTelecomServiceBeans) {
                        if (!CommonActivity.isNullOrEmpty(spin.getTelecomServiceId())) {
                            if (Long.parseLong(spin.getTelecomServiceId()) == telecomserviceId) {
                                spinner_serviceMobile.setSelection(arrTelecomServiceBeans.indexOf(spin));
                                spinner_serviceMobile.setEnabled(false);
                                break;
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            Log.e("initTelecomService", e.toString());
        }
    }

    private String mServiceMobile = "";
    private String mLoaithuebao = "";

    private void resetFlag() {
        isFirst = true;
        mServiceMobile = "";
        mLoaithuebao = "";
        positonservice = -1;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        System.out.println("1234 onDestroy EventBus");
        super.onDestroy();
    }

    @Subscribe
    public void onTemplateSelected(CallbackObj s) {
        if (pakaChargeBeans == null) {
            pakaChargeBeans = new ProductOfferingDTO();
        }

        if (s != null) {
            loadTemplate(s.map);
            if (!CommonActivity.isNullOrEmpty(telecomserviceId)) {
                GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(activity, false);
                getListPakageAsyn.execute(telecomserviceId, subType);
            }
        }
    }

    public void loadTemplate(Map<String, String> map) {
        System.out.println("12345 load tem");
        mMapTemplate = map;
        if (mMapTemplate == null) {
            mMapTemplate = new HashMap<>();
        }
        resetFlag();
        AutoCompleteUtil.getInstance(getActivity()).clearSpinnerSelectionState(spinner_serviceMobile);
        AutoCompleteUtil.getInstance(getActivity()).clearSpinnerSelectionState(spinner_cuocdongtruoc);
        AutoCompleteUtil.getInstance(getActivity()).setSpinnerSelection(spinner_serviceMobile, mMapTemplate.get(AutoConst.SERVICE), isFirst);
    }

    public void saveTemplate() {
        AutoCompleteUtil.getInstance(getActivity())
                .saveTemplate(PREF_MOBILE_NEW_SCREEN + FragmentInfoCustomerMobileNew.subType, mMapTemplate);
    }

    // move login
    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(activity, LoginActivity.class);
//            startActivity(intent);
//            activity.finish();
//            if (MainActivity.getInstance() != null) {
//                MainActivity.getInstance().finish();
//            }
            String permission = ";menu.connect.mobile2;";
            if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                if (Constant.CHANGE_PRE_TO_POS.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                    permission = ";change.pre.to.pos;";
                } else {
                    permission = ";change.pos.to.pre;";
                }
            }
            LoginDialog dialog = new LoginDialog(getActivity(),
                    permission);

            dialog.show();
        }
    };

    // ========= init spinner Contract
    private void initContract() {
        AccountDTO conTractBean0 = new AccountDTO();
        conTractBean0.setAccountNo(getActivity().getString(R.string.txt_select));
        conTractBean0.setAccountId(null);
        arrTractBeans.add(0, conTractBean0);

        AccountDTO conTractBean1 = new AccountDTO();
        conTractBean1.setAccountNo(getActivity().getString(R.string.contractNew));
        conTractBean1.setAccountId(null);
        arrTractBeans.add(1, conTractBean1);
        if (arrTractBeans != null && arrTractBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (AccountDTO conTractBean : arrTractBeans) {
                adapter.add(conTractBean.getAccountNo());
            }
            spinner_loaihd.setAdapter(adapter);
        }

    }

    // === hthm co data
    private void initHTHM() {
        edt_hthm.setText(getString(R.string.chonhthm));
        // HTHMBeans hthmBeans = new HTHMBeans();
        // hthmBeans.setName(getResources().getString(R.string.chonhthm));
        // arrHthmBeans.add(0, hthmBeans);

        // if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
        // ArrayAdapter<String> adapter = new
        // ArrayAdapter<String>(activity, R.layout.pakage_charge_item,
        // R.id.txtpakage);
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
        edt_hthm.setText(getString(R.string.chonhthm));
        // HTHMBeans hthmBeans = new HTHMBeans();
        // hthmBeans.setName(getResources().getString(R.string.chonhthm));
        // arrHthmBeans.add(0, hthmBeans);
        // HTHMBeans hthmBeans = new HTHMBeans();
        // hthmBeans.setName(getResources().getString(R.string.chonhthm));
        // ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
        // lstHthmBeans.add(0, hthmBeans);
        // if (lstHthmBeans != null && lstHthmBeans.size() > 0) {
        // ArrayAdapter<String> adapter = new
        // ArrayAdapter<String>(activity,
        // android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
        // for (HTHMBeans hBeans : lstHthmBeans) {
        // adapter.add(hBeans.getName());
        // }
        // spinner_hthm.setAdapter(adapter);
        // }
    }

    // init spinner listsubPromotion

    public void initInitListPromotion() {

        // PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
        // promotionTypeBeans.setCodeName(getResources().getString(R.string.selectMKM));
        // arrPromotionTypeBeans.add(0, promotionTypeBeans);
        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
        promotionTypeBeans.setCodeName(getResources().getString(R.string.chonctkm1));
        promotionTypeBeans.setName(getResources().getString(R.string.chonctkm1));
        promotionTypeBeans.setPromProgramCode("");
        arrPromotionTypeBeans.add(0, promotionTypeBeans);

        if ("REGTYPE_IMIE".equals(checkCharRegtype)) {

        } else {
            PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
            promotionTypeBeans1.setCodeName(getResources().getString(R.string.selectMKM));
            promotionTypeBeans1.setName(getResources().getString(R.string.selectMKM));
            promotionTypeBeans1.setPromProgramCode("-2");
            arrPromotionTypeBeans.add(1, promotionTypeBeans1);
        }
    }

    // init spinner not data subpromotion
    public void initInitListPromotionNotData() {

        edt_kmai.setText(getString(R.string.chonctkm1));
        maKM = "";
        prepaidCode = "-1";
        prepaidId = "";
    }

    // =========== webservice danh sach khuyen mai =================
    public class GetPromotionByMap extends AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetPromotionByMap(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
            if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                return getPromotions(arg0[0], arg0[1], arg0[2], arg0[6]);
            } else {
                return getPromotionTypeBeans(arg0[0], arg0[1], arg0[2]);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
            progress.dismiss();

            removeMapkey(AutoConst.KM);

            System.out.println("12345 Chon chuong trinh km");
            edt_kmai.setText(getString(R.string.chonctkm1));
            maKM = "";
            prepaidCode = "-1";
            prepaidId = "";
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    arrPromotionTypeBeans = result;
                    initInitListPromotion();
                    // set km
                    String km = mMapTemplate.get(AutoConst.KM);
                    System.out.println("12345 Chon chuong trinh km " + km);
                    if (!TextUtils.isEmpty(km)) {
                        for (int i = 0, n = arrPromotionTypeBeans.size(); i < n; i++) {
                            if (arrPromotionTypeBeans.get(i).getName().toString().equals(km)) {
                                setPromotionTypeBean(arrPromotionTypeBeans.get(i));
                                break;
                            }
                        }
                    }
                } else {
                    arrPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
                    PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
                    promotionTypeBeans.setCodeName(getResources().getString(R.string.chonctkm1));
                    promotionTypeBeans.setPromProgramCode("");
                    arrPromotionTypeBeans.add(0, promotionTypeBeans);
                    if ("REGTYPE_IMIE".equals(checkCharRegtype)) {

                    } else {
                        PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
                        promotionTypeBeans1.setCodeName(getResources().getString(R.string.selectMKM));
                        promotionTypeBeans1.setName(getResources().getString(R.string.selectMKM));
                        promotionTypeBeans1.setPromProgramCode("-2");
                        arrPromotionTypeBeans.add(1, promotionTypeBeans1);
                    }

                    // initInitListPromotionNotData();
                    // Dialog dialog = CommonActivity.createAlertDialog(
                    // activity,
                    // getResources().getString(R.string.checkmakm),
                    // getResources().getString(R.string.app_name));
                    // dialog.show();

                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ArrayList<PromotionTypeBeans> getPromotionTypeBeans(String serviceType, String regType,
                                                                    String offerId) {
            ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListPromotionByMapSP");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListPromotionByMapSP>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<regType>" + regType + "</regType>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<province>" + null + "</province>");
                rawData.append("<district>" + null + "</district>");
                rawData.append("<precinct>" + null + "</precinct>");
                rawData.append("<serviceType>" + serviceType + "</serviceType>");
                rawData.append("<custType>" + custIdentityDTO.getCustomer().getCustType() + "</custType>");
                rawData.append("<payType>" + 1 + "</payType>");
                rawData.append("<subType>" + subTypeMobile + "</subType>");
                // rawData.append("<actionCode>" + "00" + "</actionCode>");
                rawData.append("</input>");
                rawData.append("</ws:getListPromotionByMapSP>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getListPromotionByMapSP");
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
                    nodechild = doc.getElementsByTagName("lstDiscountPromotionDTO");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
                        String codeName = parse.getValue(e1, "codeName");
                        Log.d("codeName", codeName);
                        promotionTypeBeans.setCodeName(codeName);

                        String name = parse.getValue(e1, "name");
                        Log.d("name", name);
                        promotionTypeBeans.setName(name);

                        String promProgramCode = parse.getValue(e1, "code");
                        Log.d("code", promProgramCode);
                        promotionTypeBeans.setPromProgramCode(promProgramCode);

                        String descrip = parse.getValue(e1, "description");
                        Log.d("descriponnnnn", descrip);
                        if (CommonActivity.isNullOrEmpty(promotionTypeBeans.getDescription())) {
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

        private ArrayList<PromotionTypeBeans> getPromotions(String serviceType,
                                                            String regType,
                                                            String offerId,
                                                            String avgChargeValue) {
            ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getPromotions");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getPromotions>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<serviceType>" + serviceType + "</serviceType>");
                rawData.append("<custType>" + custIdentityDTO.getCustomer().getCustType() + "</custType>");
                rawData.append("<payType>" + 1 + "</payType>");
                String actionCode = "";
                if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                    if (Constant.CHANGE_PRE_TO_POS.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        actionCode = "220";
                    } else {
                        actionCode = "221";
                    }
                } else {
                    actionCode = "00";
                }
                rawData.append("<actionCode>" + actionCode + "</actionCode>");

                if (!CommonActivity.isNullOrEmpty(avgChargeValue)) {
                    rawData.append("<avgCharge>" + avgChargeValue + "</avgCharge>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getPromotions>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getPromotions");
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
                    nodechild = doc.getElementsByTagName("lstPromotionsBO");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
                        String codeName = parse.getValue(e1, "codeName");
                        Log.d("codeName", codeName);
                        promotionTypeBeans.setCodeName(codeName);

                        String name = parse.getValue(e1, "name");
                        Log.d("name", name);
                        promotionTypeBeans.setName(name);

                        String promProgramCode = parse.getValue(e1, "promProgramCode");
                        Log.d("code", promProgramCode);
                        promotionTypeBeans.setPromProgramCode(promProgramCode);

                        String descrip = parse.getValue(e1, "description");
                        Log.d("descriponnnnn", descrip);
                        if (CommonActivity.isNullOrEmpty(promotionTypeBeans.getDescription())) {
                            promotionTypeBeans.setDescription(descrip);
                        }
                        lisPromotionTypeBeans.add(promotionTypeBeans);
                    }
                }
            } catch (Exception ex) {
                Log.d("getPromotions", ex.toString());
            }

            return lisPromotionTypeBeans;
        }


    }

    // init spinner ListSubType
    public void initListSubType() {
       /* SubTypeBeans subTypeBean = new SubTypeBeans();
        subTypeBean.setName(this.getResources().getString(R.string.chonloaithuebao));
        arrSubTypeBeans.add(0, subTypeBean);*/

        if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);

            String loaiTB = mMapTemplate.get(AutoConst.LOAITB);

            //  ArrayList<String> list = new ArrayList<>();
            System.out.println("12345 initListSubType loaiTB : " + loaiTB);
            for (SubTypeBeans subTyBeans : arrSubTypeBeans) {
                String t = subTyBeans.getSubType() + " - " + subTyBeans.getName();
                System.out.println("12345 initListSubType t : " + t + " == " + loaiTB);
                if (!TextUtils.isEmpty(loaiTB)) {
                    if (loaiTB.equals(t) || loaiTB.equals(subTyBeans.getName())) {
                        setLoaithuebao(subTyBeans);
                        break;
                    }
                }
               /* if (!CommonActivity.isNullOrEmpty(subTyBeans.getSubType())
                        && !CommonActivity.isNullOrEmpty(subTyBeans.getName())) {
                    //adapter.add(subTyBeans.getSubType() + " - " + subTyBeans.getName());
                    list.add(subTyBeans.getSubType() + " - " + subTyBeans.getName());
                } else {
                    //adapter.add(subTyBeans.getName());
                    list.add(subTyBeans.getName());
                }*/
            }
            // AutoCompleteUtil.getInstance(getActivity()).sortCategoryTBBySelectedCount(AUTO_CATEGORY_TB, list);
            //    adapter.addAll(list);

            // AutoCompleteUtil.getInstance(getActivity()).setSpinnerSelection(spinner_loaithuebao, mMapTemplate.get(AutoConst.LOAITB), isFirst);
        }
    }

    // init spinner ListSubTypeNotdate
    public void initListSubTypeNotData() {
       /* SubTypeBeans subTypeBeanNot = new SubTypeBeans();
        subTypeBeanNot.setName(this.getResources().getString(R.string.chonloaithuebao));
        ArrayList<SubTypeBeans> arrSubTypeBeanss = new ArrayList<SubTypeBeans>();
        arrSubTypeBeanss.add(0, subTypeBeanNot);

        if (arrSubTypeBeanss != null && arrSubTypeBeanss.size() > 0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);

            for (SubTypeBeans subTyBeans : arrSubTypeBeanss) {
                adapter.add(subTyBeans.getName());
            }
            spinner_loaithuebao.setAdapter(adapter);
        }*/
        System.out.println("12345 initListSubTypeNotData");
        spinner_loaithuebao.setText(this.getResources().getString(R.string.chonloaithuebao));
    }

    // ====================== Webserivce get list ========== DS
    // loÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚ÂºÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¡i
    // thuÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Âª bao
    private class GetListSubTypeAsyn extends AsyncTask<String, Void, ArrayList<SubTypeBeans>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetListSubTypeAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
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
                if (result != null && result.size() > 0) {

                    arrSubTypeBeans = result;
                    initListSubType();
                } else {
                    initListSubTypeNotData();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<SubTypeBeans> getListSubTypeBeans(String telecomServiceId, String productCode) {
            ArrayList<SubTypeBeans> lisSubTypeBeans = new ArrayList<SubTypeBeans>();
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getLsSubTypesByTelService");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLsSubTypesByTelService>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                if (telecomServiceId != null && !telecomServiceId.isEmpty()) {
                    rawData.append("<telService>" + telecomServiceId);
                    rawData.append("</telService>");
                }
                if (productCode != null && !productCode.isEmpty()) {
                    rawData.append("<productCode>" + productCode);
                    rawData.append("</productCode>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getLsSubTypesByTelService>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getLsSubTypesByTelService");
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
                    nodechild = doc.getElementsByTagName("lstSubTypeDTOs");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        SubTypeBeans subTypeBeans = new SubTypeBeans();
                        String name = parse.getValue(e1, "name");
                        Log.d("name", name);
                        subTypeBeans.setName(name);
                        String subType = parse.getValue(e1, "subType");
                        Log.d("subType", subType);
                        subTypeBeans.setSubType(subType);

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
    private class GetConTractAsyn extends AsyncTask<String, Void, ArrayList<AccountDTO>> {

        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetConTractAsyn(Context context) {
            this.context = context;
            prb_contract.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<AccountDTO> doInBackground(String... params) {
            return sendRequestGetLisContract(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<AccountDTO> result) {
            prb_contract.setVisibility(View.GONE);
            // progress.dismiss();
            if (errorCode.equals("0")) {

                GetListDepositAsyn getListDepositAsyn = new GetListDepositAsyn(getActivity());
                getListDepositAsyn.execute();

                if (result != null && result.size() > 0) {
                    arrTractBeans = result;
                    initContract();
                } else {
                    if (arrTractBeans != null && arrTractBeans.size() > 0) {
                        arrTractBeans.clear();
                    }
                    initContract();

                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ArrayList<AccountDTO> sendRequestGetLisContract(String cusId, String idNo) {
            ArrayList<AccountDTO> lstTractBeans = new ArrayList<AccountDTO>();
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_searchAccount");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:searchAccount>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<inputForSearchContract>");
                rawData.append("<custId>" + cusId);
                rawData.append("</custId>");

                rawData.append("<idNo>" + idNo);
                rawData.append("</idNo>");
                rawData.append("</inputForSearchContract>");
                rawData.append("</input>");
                rawData.append("</ws:searchAccount>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_searchAccount");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstTractBeans = parseOuput.getLstAccountDTO();
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }

            return lstTractBeans;
        }
    }

    private void getHTHM() {
        Log.i("DATA", "call getHTHM------");
        GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(getActivity());
        getHTHMAsyn.execute(offerId, serviceType, province, district, precinct,
                custIdentityDTO.getCustomer().getCustType(), "-1", subType);
    }

    // ===========webservice danh sach hinh thuc hoa mang
    public class GetHTHMAsyn extends AsyncTask<String, Void, ArrayList<HTHMBeans>> {

        ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetHTHMAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
            return getListHTHM(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5], arg0[6], arg0[7]);

        }

        @Override
        protected void onPostExecute(ArrayList<HTHMBeans> result) {

            // TODO get hinh thuc hoa mang
            progress.dismiss();
            if (errorCode.equals("0")) {
                if (arrHthmBeans != null && !arrHthmBeans.isEmpty()) {
                    arrHthmBeans = new ArrayList<HTHMBeans>();
                }
                if (result != null && result.size() > 0) {
                    arrHthmBeans = result;
                    System.out.println("12345 thong tin hthm " + isFirst);
                    if (!isFirst) {
                        Intent intent = new Intent(getActivity(), SearchCodeHthmActivity.class);
                        intent.putExtra("arrHthmBeans", result);
                        startActivityForResult(intent, 1001);
                    } else {
                        String hthm = mMapTemplate.get(AutoConst.HTHM);
                        if (!TextUtils.isEmpty(hthm)) {
                            for (int i = 0, n = arrHthmBeans.size(); i < n; i++) {
                                if (arrHthmBeans.get(i).getCodeName().toString().equals(hthm)) {
                                    reloadDataHTHM(arrHthmBeans.get(i));
                                    return;
                                }
                            }
                        }
                    }
                } else {
                    removeMapkey(AutoConst.HTHM);

                    edt_hthm.setText(getString(R.string.chonhthm));
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.noththm), getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
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

        // ==input String offerId, String serviceType, String province ,String
        // district, String precinct)
        private ArrayList<HTHMBeans> getListHTHM(String offerId, String serviceType, String province, String district,
                                                 String precinct, String custType, String subType, String payType) {
            ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListRegTypeConnectSP");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListRegTypeConnectSP>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<offerId>" + offerId);
                rawData.append("</offerId>");
                rawData.append("<serviceType>" + serviceType);
                rawData.append("</serviceType>");

                if ("H".equals(serviceType)) {
                    rawData.append("<province>" + province);
                    rawData.append("</province>");
                    rawData.append("<district>" + district);
                    rawData.append("</district>");
                    rawData.append("<precinct>" + precinct);
                    rawData.append("</precinct>");
                }

                rawData.append("<custType>" + custType);
                rawData.append("</custType>");
                if ("2".equals(payType)) {
                    subType = "-1";
                }
                rawData.append("<subType>" + subType);
                rawData.append("</subType>");
                rawData.append("<payType>" + payType);
                rawData.append("</payType>");
                rawData.append("</input>");
                rawData.append("</ws:getListRegTypeConnectSP>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getListRegTypeConnectSP");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity.parseXMLHandler(new HTHMMobileHandler(),
                        original);
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
    public class GetListPakageAsyn extends AsyncTask<String, Void, ArrayList<ProductOfferingDTO>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        boolean isSmartSim = false;

        public GetListPakageAsyn(Context context, boolean isSmart) {
            this.isSmartSim = isSmart;
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductOfferingDTO> doInBackground(String... params) {
            return sendRequestGetListService(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductOfferingDTO> result) {
            progress.dismiss();
//            lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    arrPakageChargeBeans = result;
                    pakageBundeBean.setArrChargeBeans(result);

                    String packages = mMapTemplate.get(AutoConst.PACKAGE);
                    if (isFirst && !TextUtils.isEmpty(packages)) {
                        System.out.println("12345 packages " + packages);
                        for (int i = 0, n = pakageBundeBean.getArrChargeBeans().size(); i < n; i++) {
                            if (pakageBundeBean.getArrChargeBeans().get(i).getName().toString().equals(packages)) {
                                setPackage(pakageBundeBean.getArrChargeBeans().get(i));
                                return;
                            }
                        }
                    }

                    Intent intent = new Intent(activity, FragmentSearchPakageMobileBCCS.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("PakageKey", pakageBundeBean);
                    intent.putExtras(mBundle);
                    startActivityForResult(intent, 3333);
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            getResources().getString(R.string.notgoicuoc), getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        public ArrayList<ProductOfferingDTO> sendRequestGetListService(String telecomserviceId, String payType) {
            String original = null;
            ArrayList<ProductOfferingDTO> lisPakageChargeBeans = new ArrayList<ProductOfferingDTO>();
            FileInputStream in = null;
            lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
            try {

//                String key = subType + "-" + serviceType;
//                if (hashmapProductOffer != null && hashmapProductOffer.size() > 0) {
//                    if (hashmapProductOffer.get(key) != null && hashmapProductOffer.get(key).size() > 0) {
//                        lisPakageChargeBeans = hashmapProductOffer.get(key);
//                        errorCode = "0";
//                        return lisPakageChargeBeans;
//                    }
//                }else{
//                    hashmapProductOffer = new HashMap<>();
//                }

                BCCSGateway input = new BCCSGateway();
                // input.addValidateGateway("username", Constant.BCCSGW_USER);
                // input.addValidateGateway("password", Constant.BCCSGW_PASS);
                // input.addValidateGateway("wscode",
                // "mbccs_findProductOfferingByTelecomService");
                StringBuilder rawData = new StringBuilder();
                rawData.append(
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

                rawData.append("<soapenv:Header/>");
                rawData.append("<soapenv:Body>");
                rawData.append("<ws:getProductCodeByMapActiveInfo>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<telecomServiceId>" + telecomserviceId);
                rawData.append("</telecomServiceId>");
                rawData.append("<payType>" + payType);
                rawData.append("</payType>");

                if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                    if (Constant.CHANGE_PRE_TO_POS.equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        rawData.append("<actionCode>" + "220");
                        rawData.append("</actionCode>");
                    } else {
                        rawData.append("<actionCode>" + "221");
                        rawData.append("</actionCode>");
                    }
                } else {
                    rawData.append("<actionCode>" + "00");
                    rawData.append("</actionCode>");
                }


                rawData.append("<isSmart>" + isSmartSim);
                rawData.append("</isSmart>");

                rawData.append("</input>");
                rawData.append("</ws:getProductCodeByMapActiveInfo>");
                rawData.append("</soapenv:Body>");
                rawData.append("</soapenv:Envelope>");
                Log.i("RowData", rawData.toString());
                String envelope = rawData.toString();
                Log.e("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);

                String fileName = input.sendRequestWriteToFile(envelope, Constant.WS_PAKAGE_DATA,
                        Constant.PAKAGE_FILE_NAME);
                if (fileName != null) {

                    try {

                        File file = new File(fileName);
//                        if (!file.mkdirs()) {
//                            file.createNewFile();
//                        }
                        in = new FileInputStream(file);
                        ProductOfferringHanlder handler = (ProductOfferringHanlder) CommonActivity
                                .parseXMLHandler(new ProductOfferringHanlder(), new InputSource(in));
//                        file.delete();
                        if (handler != null) {
                            if (handler.getItem().getToken() != null && !handler.getItem().getToken().isEmpty()) {
                                Session.setToken(handler.getItem().getToken());
                            }

                            if (handler.getItem().getErrorCode() != null) {
                                errorCode = handler.getItem().getErrorCode();
                            }
                            if (handler.getItem().getDescription() != null) {
                                description = handler.getItem().getDescription();
                            }
                            lisPakageChargeBeans = handler.getLstData();

                            if (!CommonActivity.isNullOrEmpty(handler) && !CommonActivity.isNullOrEmptyArray(handler.getLstProductOfferingDTOHasAtrr())) {
                                lstProductOfferingDTOHasAtrr = handler.getLstProductOfferingDTOHasAtrr();
                            } else {
                                lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
                            }

//                            if (!CommonActivity.isNullOrEmptyArray(lisPakageChargeBeans)) {
//                                String key1 = subType + "-" + serviceType;
//                                hashmapProductOffer.put(key1, lisPakageChargeBeans);
//                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e.toString(), e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                Log.e("Exception", e.toString(), e);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }

            return lisPakageChargeBeans;
        }

        // =====method get list service ========================
        // public ArrayList<ProductOfferingDTO> sendRequestGetListService(String
        // telecomserviceId, String payType) {
        // String original = null;
        // ArrayList<ProductOfferingDTO> lisPakageChargeBeans = new
        // ArrayList<ProductOfferingDTO>();
        // try {
        // BCCSGateway input = new BCCSGateway();
        // input.addValidateGateway("username", Constant.BCCSGW_USER);
        // input.addValidateGateway("password", Constant.BCCSGW_PASS);
        // input.addValidateGateway("wscode",
        // "mbccs_findProductOfferingByTelecomService");
        // StringBuilder rawData = new StringBuilder();
        //
        // rawData.append("<ws:findProductOfferingByTelecomService>");
        // rawData.append("<input>");
        // HashMap<String, String> paramToken = new HashMap<String, String>();
        // paramToken.put("token", Session.getToken());
        // rawData.append(input.buildXML(paramToken));
        // rawData.append("<telecomServiceId>" + telecomserviceId);
        // rawData.append("</telecomServiceId>");
        // rawData.append("<payType>" + payType);
        // rawData.append("</payType>");
        //
        // rawData.append("<isSmart>" + isSmartSim);
        // rawData.append("</isSmart>");
        //
        // rawData.append("</input>");
        // rawData.append("</ws:findProductOfferingByTelecomService>");
        //
        // Log.i("RowData", rawData.toString());
        //
        // String envelope =
        // input.buildInputGatewayWithRawData(rawData.toString());
        // Log.d("Send evelop", envelope);
        // Log.i("LOG", Constant.BCCS_GW_URL);
        // String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
        // getActivity(),
        // "mbccs_searchCustIdentity");
        // Log.i("Responseeeeeeeeee", response);
        // CommonOutput output = input.parseGWResponse(response);
        // original = output.getOriginal();
        // Log.i("Responseeeeeeeeee", original);
        //
        // Serializer serializer = new Persister();
        // ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
        // if (parseOuput != null) {
        // errorCode = parseOuput.getErrorCode();
        // description = parseOuput.getDescription();
        // lisPakageChargeBeans = parseOuput.getLstProductOfferingDTO();
        // }
        //
        // } catch (Exception e) {
        // Log.e("Exception", e.toString(), e);
        // }
        //
        // return lisPakageChargeBeans;
        // }

    }

    private class AsynZipFile extends AsyncTask<String, Void, ArrayList<FileObj>> {

        private HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
        private Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private String description = "";
        private ArrayList<String> lstFilePath = new ArrayList<String>();
        private String actionAuditId = "";

        public AsynZipFile(Context context, HashMap<String, ArrayList<FileObj>> hasMapFile,
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
            if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                this.progress.setMessage(mContext.getResources().getString(R.string.progress_changeprofile));
            } else {
                this.progress.setMessage(mContext.getResources().getString(R.string.progress_zip));
            }

            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<FileObj> doInBackground(String... arg0) {
            ArrayList<FileObj> arrFileBackUp1 = null;
            try {
                arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext, mHashMapFileObj, lstFilePath);
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

                    String des = getActivity().getString(R.string.connectSuccess);
                    if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        des = getActivity().getString(R.string.chuyendoisuccess);
                    }

                    if ("M".equals(serviceType)) {
                        AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(mContext, result,
                                onclickBackScreen, des + "\n",
                                txtisdn.getText().toString().trim(), subType);
                        uploadImageAsy.execute();
                    } else {
                        AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(mContext, result,
                                onclickBackScreen, des + "\n");
                        uploadImageAsy.execute();
                    }

                }
            } else {
                if (result != null && result.size() > 0) {
                    for (FileObj fileObj : result) {
                        File tmp = new File(fileObj.getPath());
                        tmp.delete();
                    }
                }

                CommonActivity.createAlertDialog(activity, description, activity.getString(R.string.app_name)).show();
            }

        }
    }

    // ========== dau noi tra truoc==========

    OnClickListener onclickBackScreen = new OnClickListener() {

        @Override
        public void onClick(View v) {
//			activity.finish();
            btn_connection.setVisibility(View.GONE);
        }
    };

    // ========== dau noi tra truoc==========

    OnClickListener onbackManagerClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            lnButton.setVisibility(View.GONE);
            // activity.onBackPressed();
            // if (ActivityCreateNewRequestMobile.instance != null) {
            // ActivityCreateNewRequestMobile.instance.onBackPressed();
            // }
            // activity.finish();

        }
    };

    // lay ds hang hoa
    // ===== ws danh sach hang hoa tra sau=============
    public class GetListProductPosAsyn extends AsyncTask<String, Void, ArrayList<ProductOfferTypeDTO>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetListProductPosAsyn(Context context) {
            this.context = context;
            saleServiceCode = "";
            this.progress = new ProgressDialog(context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductOfferTypeDTO> doInBackground(String... arg0) {
            return getListProduct(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductOfferTypeDTO> result) {
            this.progress.dismiss();

            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    lvproduct.setVisibility(View.VISIBLE);
                    arrStockTypeBeans = new ArrayList<ProductOfferTypeDTO>();
                    arrStockTypeBeans.addAll(result);
                    if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0) {
                        for (ProductOfferTypeDTO item : arrStockTypeBeans) {
                            ProductOfferingDTO productOfferingDTO = new ProductOfferingDTO();
                            productOfferingDTO.setName(getActivity().getString(R.string.selectproduct));
                            productOfferingDTO.setProductOfferingId(0l);
                            item.getProductOfferings().add(0, productOfferingDTO);
                        }
                    }

                    adapProductAdapter = new ThongTinHHAdapterBCCS(arrStockTypeBeans, getActivity(),
                            FragmentConnectionMobileNew.this);
                    lvproduct.setAdapter(adapProductAdapter);
                } else {
                    lvproduct.setVisibility(View.GONE);
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }

            }

        }

        private ArrayList<ProductOfferTypeDTO> getListProduct(String regType, String productCode, String serviceType) {
            ArrayList<ProductOfferTypeDTO> arrayListThongTinHH = new ArrayList<ProductOfferTypeDTO>();
            String original = "";
            ArrayList<ProductOfferTypeDTO> lstReturn = new ArrayList<ProductOfferTypeDTO>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListStockTypeWSSP");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListStockTypeWSSP>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<serviceType>" + serviceType);
                rawData.append("</serviceType>");

                rawData.append("<regType>" + regType);
                rawData.append("</regType>");

                if (ActivityCreateNewRequestMobileNew.instance != null
                        && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {

                    if (Constant.CHANGE_PRE_TO_POS
                            .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        rawData.append("<actionCode>" + 220);
                        rawData.append("</actionCode>");
                    } else {
                        rawData.append("<actionCode>" + 221);
                        rawData.append("</actionCode>");
                    }
                } else {
                    rawData.append("<actionCode>" + "00");
                    rawData.append("</actionCode>");
                }

                rawData.append("<productCode>" + productCode);
                rawData.append("</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:getListStockTypeWSSP>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getListStockTypeWSSP");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // ============parse xml in android=========

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    arrayListThongTinHH = parseOuput.getLstProductOfferTypeDTO();

                    if (arrayListThongTinHH != null && arrayListThongTinHH.size() > 0) {
                        for (ProductOfferTypeDTO productOfferTypeDTO : arrayListThongTinHH) {
                            saleServiceCode = productOfferTypeDTO.getSaleServiceCode();

                            if ("1".equals(productOfferTypeDTO.getProductOfferTypeId())
                                    || "2".equals(productOfferTypeDTO.getProductOfferTypeId())
                                    || "4".equals(productOfferTypeDTO.getProductOfferTypeId())) {
                            } else {
                                lstReturn.add(productOfferTypeDTO);
                            }
                        }
                    }

                }
            } catch (Exception e) {
                Log.d("getListProduct", e.toString());
            }

            return lstReturn;
        }
    }

    // ws view detail hang hoa tra truoc

    // lay ngay bat dau va ngay ket thuc
    public void showDatePickerDialog(final EditText tvNgay) {
        OnDateSetListener callback = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Date
                switch (tvNgay.getId()) {
                    case R.id.edtNgayBD:

                        String fromMothStr = "";
                        if (monthOfYear + 1 < 10) {
                            fromMothStr = "0" + (monthOfYear + 1);
                        } else {
                            fromMothStr = "" + (monthOfYear + 1);
                        }

                        String fromDayStr = "";
                        if (dayOfMonth < 10) {
                            fromDayStr = "0" + dayOfMonth;
                        } else {
                            fromDayStr = "" + dayOfMonth;
                        }

                        mNgayBatDau = (fromDayStr) + "/" + (fromMothStr) + "/" + year;
                        edtNgayBD.setText(mNgayBatDau);
                        try {
                            dateBD = sdf.parse(mNgayBatDau);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString(), e);
                        }

                        // TODO modify ngay 08/07/2015 === thinhhq1
                        if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
                            // neu la doi tuong tan sinh vien + 5 nÃƒÆ’Ã¢â‚¬Å¾Ãƒâ€ Ã¢â‚¬â„¢m ngay
                            // bat
                            // dau
                            // va enable
                            String[] ngayketthuc = edtNgayBD.getText().toString().split("/");
                            if (ngayketthuc.length == 3) {
                                int yearKT = Integer.parseInt(ngayketthuc[2]) + 5;
                                mNgayKetThuc = ngayketthuc[0] + "/" + ngayketthuc[1] + "/" + yearKT;
                                edtNgayKT.setText(mNgayKetThuc);
                                edtNgayKT.setEnabled(true);
                            }

                        } else {
                            edtNgayKT.setEnabled(true);
                        }

                        break;
                    case R.id.edtNgayKT:

                        String fromMothStr1 = "";
                        if (monthOfYear + 1 < 10) {
                            fromMothStr1 = "0" + (monthOfYear + 1);
                        } else {
                            fromMothStr1 = "" + (monthOfYear + 1);
                        }

                        String fromDayStr1 = "";
                        if (dayOfMonth < 10) {
                            fromDayStr1 = "0" + dayOfMonth;
                        } else {
                            fromDayStr1 = "" + dayOfMonth;
                        }

                        mNgayKetThuc = (fromDayStr1) + "/" + (fromMothStr1) + "/" + year;
                        edtNgayKT.setText(mNgayKetThuc);
                        try {
                            dateEnd = sdf.parse(mNgayKetThuc);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString(), e);
                        }
                        break;
                    case R.id.edtngaysinhhs:

                        String fromMothStr2 = "";
                        if (monthOfYear + 1 < 10) {
                            fromMothStr2 = "0" + (monthOfYear + 1);
                        } else {
                            fromMothStr2 = "" + (monthOfYear + 1);
                        }

                        String fromDayStr2 = "";
                        if (dayOfMonth < 10) {
                            fromDayStr2 = "0" + dayOfMonth;
                        } else {
                            fromDayStr2 = "" + dayOfMonth;
                        }

                        String bithdatehs = (fromDayStr2) + "/" + (fromMothStr2) + "/" + year;
                        edtngaysinhhs.setText(bithdatehs);
                        try {
                            birthDateHs = sdf.parse(bithdatehs);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString(), e);
                        }
                        break;

                    default:
                        break;
                }
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        DatePickerDialog pic = null;
        if (!CommonActivity.isNullOrEmpty(tvNgay.getText().toString())) {
            Date date = DateTimeUtils.convertStringToTime(tvNgay.getText().toString(), "dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
        } else {
            pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, callback, year, month, day);
        }
        pic.setTitle(getString(R.string.chon_ngay));
        pic.show();
    }

    private boolean validateViewCommitment() {

        // if (CommonActivity.isNullOrEmpty(offerId)) {
        // CommonActivity.createAlertDialog(getActivity(),
        // getActivity().getString(R.string.checkofferid),
        // getActivity().getString(R.string.app_name)).show();
        // return false;
        // }
        if (CommonActivity.isNullOrEmpty(hthm)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkregtype),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(txtisdn.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkisdncm),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        return true;
    }

    // Ham lay thong tin doi duong tu offerid va idNo

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    private Dialog dialogLoadMore;
    private LoadMoreListView loadMoreListView;
    private StockModel stockModel = null;

    private OnClickListener imageListenner = new OnClickListener() {
        @Override
        public void onClick(View view) {

            List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
                    mapListRecordPrepaid.values());
//            for (int j = 0; j < arrayOfArrayList.size(); j++) {
            int pos = lvUploadImage.getPositionForView(view);
            ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList.get(pos);
            View rowView = lvUploadImage.getChildAt(pos);
            ViewHolder h = (ViewHolder) rowView.getTag();
            if (h != null) {
                int indexSelection = h.spUploadImage.getSelectedItemPosition();
                RecordPrepaid recordPrepaid = listRecordPrepaid.get(indexSelection);

                if (!CommonActivity.isNullOrEmpty(recordPrepaid) && !CommonActivity.isNullOrEmpty(recordPrepaid.getImageBO())) {
                    if (recordPrepaid.getImageBO().isDownload()) {

                        Log.d(Constant.TAG, "view.getId() : " + view.getId());
                        ImagePreviewActivity.pickImage(activity, hashmapFileObj, view.getId());
                    } else {
                        new SaveImageAsyn(getActivity(), view.getId()).execute(recordPrepaid.getImageBO().getImageLink());
                    }
                } else {
                    ImagePreviewActivity.pickImage(activity, hashmapFileObj, view.getId());
                }
            }
        }
    };

    // LAY THONG TIN FILE HO SO UP LEN

    public class GetLisRecordPrepaidTask extends AsyncTask<Void, Void, String> {

        private String productCode;
        private ProgressDialog dialog;
        private Context context;
        private String reasonId;
        private ArrayList<ImageBO> lstImageBOs;

        public GetLisRecordPrepaidTask(String reasonId, String productCode, Context context) {
            this.productCode = productCode;
            this.reasonId = reasonId;
            this.context = context;
            dialog = new ProgressDialog(context);
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            // this.dialog.show();
        }

        public GetLisRecordPrepaidTask(String reasonId, String productCode, Context context, ArrayList<ImageBO> arrImageBOs) {
            this.productCode = productCode;
            this.reasonId = reasonId;
            this.lstImageBOs = arrImageBOs;
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
            boolean isNetwork = CommonActivity.isNetworkConnected(activity);
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
                CommonActivity.createAlertDialog(activity, getResources().getString(R.string.errorNetwork),
                        getResources().getString(R.string.app_name)).show();
            } else {
                parseResultError(result);
                // Tao spinner upload anh
                mapListRecordPrepaid = FragmentManageConnect.parseResultListRecordPrepaid(result, lstImageBOs);
                if(isMoreThan){
                    CommonActivity.removeRecordByCode(mapListRecordPrepaid,"PYCTT");
                }
                hashmapFileObj.clear();

                List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
                        mapListRecordPrepaid.values());
                RecordPrepaidAdapter adapter = new RecordPrepaidAdapter(activity, arrayOfArrayList, imageListenner);
                lvUploadImage.setAdapter(adapter);

                UI.setListViewHeightBasedOnChildren(lvUploadImage);
            }
            Log.e("TAG6", "result List productCode : " + result);

            super.onPostExecute(result);
        }
    }

    public String requestSevice(String reasonId, String productCode) {

        String reponse = null;
        String original = null;

        String xml = mBccsGateway.getXmlCustomer(createXML(reasonId, productCode), "mbccs_getListRecordPrepaid");
        Log.e("TAG8", "xml getListRecordPrepaid" + xml);
        try {
            reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL, activity, "mbccs_getListRecordPrepaid");
            Log.e("TAG8", "reponse getListRecordPrepaid" + reponse);
        } catch (NotFoundException e) {
            Log.e("Exception", e.toString(), e);
        } catch (Exception e) {

            Log.e("Exception", e.toString(), e);
        }
        if (reponse != null) {
            CommonOutput commonOutput;
            try {
                commonOutput = mBccsGateway.parseGWResponse(reponse);
                original = commonOutput.getOriginal();
            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
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
        if ("2".equalsIgnoreCase(subType)) {
            stringBuilder.append("<isPospaid>" + false + "</isPospaid>");
            stringBuilder.append("<productCode>" + productCode + "</productCode>");
        } else {
            stringBuilder.append("<isPospaid>" + true + "</isPospaid>");
            stringBuilder.append("<productCode>" + subTypeMobile + "</productCode>");
        }

        if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity
                .isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
            if (Constant.CHANGE_PRE_TO_POS
                    .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                stringBuilder.append("<actionCode>" + "220");
                stringBuilder.append("</actionCode>");
            } else {
                stringBuilder.append("<actionCode>" + "221");
                stringBuilder.append("</actionCode>");
            }
        } else {
            stringBuilder.append("<actionCode>" + "00");
            stringBuilder.append("</actionCode>");
        }

        stringBuilder.append("<serviceType>" + serviceType + "</serviceType>");
        stringBuilder.append("<token>" + Session.getToken() + "</token>");

        stringBuilder.append("<reasonId>" + reasonId + "</reasonId>");
        stringBuilder.append("</cmInput>");
        stringBuilder.append("</ws:getListRecordPrepaid>");
        return stringBuilder.toString();
    }

    private ArrayList<Spin> arrHTTTHD = null;
    private ArrayList<Spin> arrCKC = null;
    private ArrayList<Spin> arrINCT = null;
    private ArrayList<Spin> arrHTTBC = null;

    // private ArrayList<Spin> arrBANK = null;

    public static final int TYPE_HTTHHD = 1;
    public static final int TYPE_CK_CUOC_HD = 2;
    public static final int TYPE_INCT_HD = 3;
    public static final int TYPE_HTTBC_HD = 4;
    // private int TYPE_PL_HD = 7;

    private Boolean isRefreshTYPE_HTTTHD = false;
    private Boolean isRefreshTYPE_CK_CUOC_HD = false;
    private Boolean isRefreshTYPE_INCT_HD = false;
    private Boolean isRefreshTYPE_HTTBC_HD = false;

    // Ham lay danh sach hinh thuc thanh toan
    private class AsyntaskGetListAllCommon extends AsyncTask<Void, Void, ArrayList<Spin>> {

        private Context context = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        private int type = 0;

        public AsyntaskGetListAllCommon(Context context, int type) {
            this.context = context;
            this.type = type;
            switch (type) {
                case TYPE_HTTHHD:
                    if (prbhttthd != null) {
                        prbhttthd.setVisibility(View.VISIBLE);
                    }
                    if (btnhttthd != null) {
                        btnhttthd.setVisibility(View.GONE);
                    }
                    break;
                case TYPE_CK_CUOC_HD:

                    if (prbchukicuoc != null) {
                        prbchukicuoc.setVisibility(View.VISIBLE);
                    }
                    if (btnchukicuoc != null) {
                        btnchukicuoc.setVisibility(View.GONE);
                    }
                    break;
                case TYPE_HTTBC_HD:
                    if (prbhttbc != null) {
                        prbhttbc.setVisibility(View.VISIBLE);
                    }
                    if (btnhttbc != null) {
                        btnhttbc.setVisibility(View.GONE);
                    }
                    break;
                case TYPE_INCT_HD:
                    if (prbinchitiet != null) {
                        prbinchitiet.setVisibility(View.VISIBLE);
                    }
                    if (btninchitiet != null) {
                        btninchitiet.setVisibility(View.GONE);
                    }
                    break;

                default:
                    break;
            }

        }

        @Override
        protected ArrayList<Spin> doInBackground(Void... arg0) {
            if (type == TYPE_HTTHHD) {
                return getListPayMethode();
            } else if (type == TYPE_CK_CUOC_HD) {
                return getListCurrBillCycleAll();
            } else if (type == TYPE_INCT_HD) {
                return getListPrintMethode();
            } else if (type == TYPE_HTTBC_HD) {
                return getListNoticeMethode();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Spin> result) {
            // progress.dismiss();
            if (errorCode.equalsIgnoreCase("0")) {
                if (type == TYPE_HTTHHD) {

                    if (prbhttthd != null) {
                        prbhttthd.setVisibility(View.GONE);
                    }
                    if (btnhttthd != null) {
                        btnhttthd.setVisibility(View.VISIBLE);
                    }

                    arrHTTTHD = new ArrayList<Spin>();
                    arrHTTTHD.addAll(result);
                    Utils.setDataSpinner(activity, result, spn_payMethodCode);

                    if (!CommonActivity.isNullOrEmptyArray(arrHTTTHD) && spn_payMethodCode != null) {
                        for (int i = 0; i < arrHTTTHD.size(); i++) {
                            Spin spin = arrHTTTHD.get(i);
                            if(accountDTOMainNew != null && !CommonActivity.isNullOrEmpty(accountDTOMainNew.getPayMethod())){
                                if (accountDTOMainNew.getPayMethod().equals(spin.getId())) {
                                    spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
                                    break;
                                }
                            }else{
                                if ("00".equals(spin.getId())) {
                                    spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
                                    break;
                                }
                            }

                        }
                    }

                    if (!isRefreshTYPE_CK_CUOC_HD) {
                        AsyntaskGetListAllCommon asyntaskGetListAllCommon4 = new AsyntaskGetListAllCommon(activity,
                                TYPE_CK_CUOC_HD);
                        asyntaskGetListAllCommon4.execute();
                    }
                }

                if (type == TYPE_CK_CUOC_HD) {

                    if (prbchukicuoc != null) {
                        prbchukicuoc.setVisibility(View.GONE);
                    }
                    if (btnchukicuoc != null) {
                        btnchukicuoc.setVisibility(View.VISIBLE);
                    }

                    arrCKC = new ArrayList<Spin>();
                    arrCKC.addAll(result);
                    Utils.setDataSpinner(activity, result, spn_billCycleFromCharging);

                    if (!isRefreshTYPE_INCT_HD) {
                        AsyntaskGetListAllCommon asyntaskGetListAllCommon5 = new AsyntaskGetListAllCommon(activity,
                                TYPE_INCT_HD);
                        asyntaskGetListAllCommon5.execute();
                    }

                }
                if (type == TYPE_INCT_HD) {

                    if (prbinchitiet != null) {
                        prbinchitiet.setVisibility(View.GONE);
                    }
                    if (btninchitiet != null) {
                        btninchitiet.setVisibility(View.VISIBLE);
                    }

                    arrINCT = new ArrayList<Spin>();
                    arrINCT.addAll(result);
                    Utils.setDataSpinner(activity, result, spn_printMethodCode);
                    // if(CommonActivity.isNullOrEmpty(accountDTO)){
                    if (!CommonActivity.isNullOrEmptyArray(arrINCT) && spn_printMethodCode != null) {
                        for (int i = 0; i < arrINCT.size(); i++) {
                            Spin spin = arrINCT.get(i);
                            if(accountDTOMainNew != null && !CommonActivity.isNullOrEmpty(accountDTOMainNew.getPrintMethod())){
                                if (accountDTOMainNew.getPrintMethod().equals(spin.getId())) {
                                    spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
                                    break;
                                }
                            }else {
                                if ("2".equals(spin.getId())) {
                                    spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
                                    break;
                                }
                            }
                        }
                    }
                    // }

                    // TODO THINHHQ1 tbc
                    if (!isRefreshTYPE_HTTBC_HD) {
                        AsyntaskGetListAllCommon asyntaskGetListAllCommon6 = new AsyntaskGetListAllCommon(activity,
                                TYPE_HTTBC_HD);
                        asyntaskGetListAllCommon6.execute();
                    }

                }
                if (type == TYPE_HTTBC_HD) {

                    if (prbhttbc != null) {
                        prbhttbc.setVisibility(View.GONE);
                    }
                    if (btnhttbc != null) {
                        btnhttbc.setVisibility(View.VISIBLE);
                    }

                    arrHTTBC = new ArrayList<Spin>();
                    arrHTTBC.addAll(result);
                    Utils.setDataSpinner(activity, result, spn_noticeCharge);

                }

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    result = new ArrayList<Spin>();
                    if (type == TYPE_HTTHHD) {
                        arrHTTTHD = new ArrayList<Spin>();
                        arrHTTTHD.addAll(result);
                        Utils.setDataSpinner(activity, result, spn_payMethodCode);
                    }

                    if (type == TYPE_CK_CUOC_HD) {
                        arrCKC = new ArrayList<Spin>();
                        arrCKC.addAll(result);
                        Utils.setDataSpinner(activity, result, spn_billCycleFromCharging);
                    }
                    if (type == TYPE_INCT_HD) {
                        arrINCT = new ArrayList<Spin>();
                        arrINCT.addAll(result);
                        Utils.setDataSpinner(activity, result, spn_printMethodCode);
                    }
                    if (type == TYPE_HTTBC_HD) {
                        arrHTTBC = new ArrayList<Spin>();
                        arrHTTBC.addAll(result);
                        Utils.setDataSpinner(activity, result, spn_noticeCharge);
                    }

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        // lay danh sach hinh thuc thanh toan hop dong
        private ArrayList<Spin> getListPayMethode() {
            ArrayList<Spin> lstpaymethod = new ArrayList<Spin>();
            String original = "";
            try {
                lstpaymethod = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_HTTHHD);
                if (lstpaymethod != null && !lstpaymethod.isEmpty()) {
                    errorCode = "0";
                    return lstpaymethod;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLsOptionSetValueByCode>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<code>" + "MPOS_PAY_METHOD" + "</code>");

                rawData.append("</input>");
                rawData.append("</ws:getLsOptionSetValueByCode>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getLsOptionSetValueByCode");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser
                lstpaymethod = parserListGroup(original);
                new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_HTTHHD, lstpaymethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstpaymethod;
        }

        // lay danh sach in chi tiet cuoc
        private ArrayList<Spin> getListPrintMethode() {
            ArrayList<Spin> lstprintmethod = new ArrayList<Spin>();
            String original = "";
            try {
                lstprintmethod = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_INCT_HD);
                if (lstprintmethod != null && !lstprintmethod.isEmpty()) {
                    errorCode = "0";
                    return lstprintmethod;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLsOptionSetValueByCode>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<code>" + "MPOS_PRINT_METHOD" + "</code>");
                rawData.append("</input>");
                rawData.append("</ws:getLsOptionSetValueByCode>");

                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getLsOptionSetValueByCode");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                lstprintmethod = parserListGroup(original);
                new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_INCT_HD, lstprintmethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstprintmethod;
        }

        // lay danh sach hinh thuc thong bao cuoc
        private ArrayList<Spin> getListNoticeMethode() {
            ArrayList<Spin> lstNoticeMethod = new ArrayList<Spin>();
            String original = "";
            try {
                lstNoticeMethod = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_HTTBC_HD);
                if (lstNoticeMethod != null && !lstNoticeMethod.isEmpty()) {
                    errorCode = "0";
                    return lstNoticeMethod;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLsOptionSetValueByCode>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<code>" + "MPOS_NOTICE_CHARGE" + "</code>");

                rawData.append("</input>");
                rawData.append("</ws:getLsOptionSetValueByCode>");

                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getLsOptionSetValueByCode");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                lstNoticeMethod = parserListGroup(original);
                new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_HTTBC_HD, lstNoticeMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstNoticeMethod;
        }

        // lay danh sach chu ky cuoc
        private ArrayList<Spin> getListCurrBillCycleAll() {
            ArrayList<Spin> lstCurrBillCycle = new ArrayList<Spin>();
            String original = "";
            try {
                lstCurrBillCycle = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_CK_CUOC_HD);
                if (lstCurrBillCycle != null && !lstCurrBillCycle.isEmpty()) {
                    errorCode = "0";
                    return lstCurrBillCycle;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getAllBillCycleType");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getAllBillCycleType>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<status>" + 1 + "</status>");
                rawData.append("</input>");
                rawData.append("</ws:getAllBillCycleType>");

                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getAllBillCycleType");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                lstCurrBillCycle = parserBillCycle(original);
                new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_CK_CUOC_HD, lstCurrBillCycle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstCurrBillCycle;
        }

        public ArrayList<Spin> parserBillCycle(String original) {
            ArrayList<Spin> lstReason = new ArrayList<Spin>();
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            NodeList nodechild = null;
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                description = parse.getValue(e2, "description");
                Log.d("errorCode", errorCode);
                nodechild = doc.getElementsByTagName("lstBillCycleTypeDTO");
                for (int j = 0; j < nodechild.getLength(); j++) {
                    Element e1 = (Element) nodechild.item(j);
                    Spin spin = new Spin();
                    spin.setValue(parse.getValue(e1, "billCycleId"));

                    spin.setId(parse.getValue(e1, "billCycleId"));

                    lstReason.add(spin);
                }
            }
            return lstReason;
        }

        public ArrayList<Spin> parserListGroup(String original) {
            ArrayList<Spin> lstReason = new ArrayList<Spin>();
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            NodeList nodechild = null;
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                description = parse.getValue(e2, "description");
                Log.d("errorCode", errorCode);
                nodechild = doc.getElementsByTagName("lstOptionSetValueDTOs");
                for (int j = 0; j < nodechild.getLength(); j++) {
                    Element e1 = (Element) nodechild.item(j);
                    Spin spin = new Spin();
                    spin.setValue(parse.getValue(e1, "name"));

                    spin.setId(parse.getValue(e1, "value"));

                    lstReason.add(spin);
                }
            }
            return lstReason;
        }
    }

    /**
     * lay quata cua dau noi
     */

    private int isDeposit = -1;
    private long quota = -1;
    private long moneyPre = -1;

    public class GetQuotaAsyn extends AsyncTask<String, Void, Integer> {

        private ProgressDialog progress;
        private Activity context = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";

        public GetQuotaAsyn(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected Integer doInBackground(String... arg0) {
            return getBusTypeMoneyPre(arg0[0]);
        }

        @Override
        protected void onPostExecute(Integer _isDeposit) {

            // TODO get hinh thuc hoa mang
            progress.dismiss();
            if (errorCode.equals("0")) {
                if (_isDeposit == 0) {
                    String message = String.format(getString(R.string.qupta), quota, moneyPre);
                    Dialog dialog0 = CommonActivity.createAlertDialog(activity, message, getString(R.string.app_name));
                    dialog0.show();
                } else if (_isDeposit == -1) {
                    Dialog dialog = CommonActivity.createAlertDialog(activity, getString(R.string.not_quota_fail),
                            getString(R.string.app_name));
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity, getString(R.string.not_quota),
                            getString(R.string.app_name));
                    dialog.show();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private Integer getBusTypeMoneyPre(String busType) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getBusTypeMoneyPre");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getBusTypeMoneyPre>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<busType>" + busType);
                rawData.append("</busType>");
                rawData.append("</input>");
                rawData.append("</ws:getBusTypeMoneyPre>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getBusTypeMoneyPre");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);

                // ====== parse xml ===================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");

                    isDeposit = Integer.parseInt(parse.getValue(e2, "isDeposit"));
                    quota = Long.parseLong(parse.getValue(e2, "quota"));
                    moneyPre = Long.parseLong(parse.getValue(e2, "moneyPre"));

                    Log.d(Constant.TAG, "getBusTypeMoneyPre errorCode: " + errorCode + " description: " + description
                            + " isDeposit: " + isDeposit + " quota: " + quota + " moneyPre: " + moneyPre);

                    // nodechild =
                    // doc.getElementsByTagName("smartphoneInfoResponseBO");
                    // if (nodechild.getLength() > 0) {
                    // Element e1 = (Element) nodechild.item(0);
                    //
                    // isDeposit = Integer.parseInt(parse.getValue(e1,
                    // "isDeposit"));
                    // quota = Long.parseLong(parse.getValue(e1, "quota"));
                    // moneyPre = Long.parseLong(parse.getValue(e1,
                    // "moneyPre"));
                    // }
                }
            } catch (Exception e) {
                Log.d("getBusTypeMoneyPre", e.toString());
            }
            return Integer.valueOf(isDeposit);
        }

    }

    private LoadMoreListView lvListObjectCustomer;
    private Button btnSearchCustomerObject;
    private Button btnDeleteCustomerObject;
    private EditText edtCusCode;
    private EditText edtCusName;

    private ContractFormMngtBean searchBean;

    private CustomerObjectAdapter customerObjectAdapter;
    private ContractFormMngtBean bankBean = null;

    private List<ContractFormMngtBean> lstContractFormMngtBean = new ArrayList<ContractFormMngtBean>();

    private void showDialogListBank() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_object_bank);
        lvListObjectCustomer = (LoadMoreListView) dialog.findViewById(R.id.lvListObjectCustomer);
        btnSearchCustomerObject = (Button) dialog.findViewById(R.id.btnSearch);
        btnDeleteCustomerObject = (Button) dialog.findViewById(R.id.btnDelete);
        edtCusCode = (EditText) dialog.findViewById(R.id.edtCodeCustomer);
        edtCusName = (EditText) dialog.findViewById(R.id.edtNameCustomer);

        lvListObjectCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankBean = lstContractFormMngtBean.get(position);
                txtnganhang.setText(bankBean.getName());
                dialog.dismiss();
            }
        });

        lvListObjectCustomer.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

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
                CommonActivity.hideKeyboard(v, activity);
                String customerName = edtCusName.getText().toString().trim();
                String customerCode = edtCusCode.getText().toString().trim();

                if (!CommonActivity.isNullOrEmpty(customerName) || !CommonActivity.isNullOrEmpty(customerCode)) {
                    if (CommonActivity.isNetworkConnected(activity)) {
                        searchBean = new ContractFormMngtBean();
                        searchBean.setCode(edtCusCode.getText().toString().trim());
                        searchBean.setName(edtCusName.getText().toString().trim());

                        lstContractFormMngtBean.clear();
                        AsyncBank asynctaskContractFotmMngt = new AsyncBank(searchBean, lstContractFormMngtBean.size(),
                                lstContractFormMngtBean.size() + Constant.PAGE_SIZE);
                        asynctaskContractFotmMngt.execute();
                    } else {
                        CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
                                getString(R.string.app_name)).show();
                    }
                } else {
                    CommonActivity.createAlertDialog(activity,
                            getString(R.string.message_not_input_customer_name_or_name), getString(R.string.app_name))
                            .show();

                }
            }
        });

        btnDeleteCustomerObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankBean = null;
                txtnganhang.setText(getActivity().getString(R.string.chonnganhang));
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private class AsyncBank extends AsyncTask<String, Void, List<ContractFormMngtBean>> {

        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        private ProgressDialog progress;
        private ContractFormMngtBean searchBean;

        private int pageIndex;
        private int pageSize;

        public AsyncBank(ContractFormMngtBean _searchBean, int _pageIndex, int _pageSize) {
            this.searchBean = _searchBean;
            this.pageIndex = _pageIndex;
            this.pageSize = _pageSize;

            this.progress = new ProgressDialog(activity);
            this.progress.setCancelable(false);

            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        ;

        @Override
        protected List<ContractFormMngtBean> doInBackground(String... params) {

            return getBankByCode();
        }

        @Override
        protected void onPostExecute(List<ContractFormMngtBean> result) {
            super.onPostExecute(result);

            progress.dismiss();
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    // them danh sach
                    lstContractFormMngtBean.addAll(result);
                    customerObjectAdapter = new CustomerObjectAdapter(activity, lstContractFormMngtBean);
                    lvListObjectCustomer.setAdapter(customerObjectAdapter);
                    customerObjectAdapter.notifyDataSetChanged();
                } else {
                    if (lstContractFormMngtBean.isEmpty()) {
                        customerObjectAdapter = new CustomerObjectAdapter(activity, lstContractFormMngtBean);
                        lvListObjectCustomer.setAdapter(customerObjectAdapter);
                        customerObjectAdapter.notifyDataSetChanged();
//                        lvListObjectCustomer.setAdapter(null);
                        CommonActivity.createAlertDialog(activity, getString(R.string.notnganhang),
                                getString(R.string.app_name)).show();
                    }
                }
                Log.d(Constant.TAG, "onPostExecute result.size(): " + result.size());
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            activity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = activity.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private List<ContractFormMngtBean> getBankByCode() {
            List<ContractFormMngtBean> lstBank = new ArrayList<ContractFormMngtBean>();
            String original = "";
            try {

                BhldDAL dal = new BhldDAL(getActivity());
                dal.open();
                lstBank = dal.getListBankFromBCCS(searchBean);
                errorCode = "0";
                dal.close();
                // BCCSGateway input = new BCCSGateway();
                // input.addValidateGateway("username", Constant.BCCSGW_USER);
                // input.addValidateGateway("password", Constant.BCCSGW_PASS);
                // input.addValidateGateway("wscode",
                // "mbccs_findActiveBankByCodeName");
                // StringBuilder rawData = new StringBuilder();
                // rawData.append("<ws:findActiveBankByCodeName>");
                // rawData.append("<input>");
                // rawData.append("<token>" + Session.getToken() + "</token>");
                //
                // rawData.append("<bankCodeOrName>" + searchBean.getCode() +
                // "</bankCodeOrName>");
                //
                // // rawData.append("<pageIndex>" + pageIndex +
                // "</pageIndex>");
                // // rawData.append("<pageSize>" + pageSize + "</pageSize>");
                //
                // rawData.append("</input>");
                // rawData.append("</ws:findActiveBankByCodeName>");
                // Log.i("RowData", rawData.toString());
                // String envelope =
                // input.buildInputGatewayWithRawData(rawData.toString());
                // Log.d("Send evelop", envelope);
                // Log.i("LOG", Constant.BCCS_GW_URL);
                // String response = input.sendRequest(envelope,
                // Constant.BCCS_GW_URL, activity,
                // "mbccs_findActiveBankByCodeName");
                // Log.i("Responseeeeeeeeee", response);
                // CommonOutput output = input.parseGWResponse(response);
                // original = output.getOriginal();
                // Log.i("Responseeeeeeeeee Original group", response);
                //
                // JSONObject jsonObject = null;
                // try {
                // jsonObject = XML.toJSONObject(original);
                // Log.i(Constant.TAG, jsonObject.toString());
                //
                // if (jsonObject.has("lstBank")) {
                // Log.i(Constant.TAG, "lstBank Key Found");
                // JSONArray jsonArray = jsonObject.getJSONArray("lstBank");
                // for (int i = 0; i < jsonArray.length(); i++) {
                // JSONObject obj = jsonArray.getJSONObject(i);
                // }
                // } else {
                // Log.i(Constant.TAG, "lstBank Key Not Found");
                // }
                // } catch (Exception e) {
                // Log.e(Constant.TAG, e.getMessage(), e);
                // }
                //
                // // ==== parse xml list ip
                // Document doc = parse.getDomElement(original);
                // NodeList nl = doc.getElementsByTagName("return");
                // NodeList nodechild = null;
                // for (int i = 0; i < nl.getLength(); i++) {
                // Element e2 = (Element) nl.item(i);
                // errorCode = parse.getValue(e2, "errorCode");
                // description = parse.getValue(e2, "description");
                // Log.d(Constant.TAG, "getBankByCode errorCode: " + errorCode +
                // " description: " + description);
                // nodechild = doc.getElementsByTagName("lstBankDTO");
                //
                // if (nodechild != null && nodechild.getLength() > 0) {
                // for (int j = 0; j < nodechild.getLength(); j++) {
                // Element e1 = (Element) nodechild.item(j);
                // ContractFormMngtBean obj = new ContractFormMngtBean();
                // obj.setCode(parse.getValue(e1, "bankCode"));
                // obj.setName(parse.getValue(e1, "name"));
                //
                // lstBank.add(obj);
                // }
                // } else {
                // Log.d(Constant.TAG, "getBankByCode nodechild lstBank NULL or
                // EMPTY");
                // }
                // }

            } catch (Exception e) {
                Log.e("getBankByCode", e.toString(), e);
            }
            return lstBank;
        }
    }

    private void hideBtnRefresh() {

        prbhttthd.setVisibility(View.GONE);
        prbchukicuoc.setVisibility(View.GONE);
        prbinchitiet.setVisibility(View.GONE);
        prbhttbc.setVisibility(View.GONE);
    }

    private void showBtnRefresh() {
        btnhttthd.setVisibility(View.VISIBLE);
        btnchukicuoc.setVisibility(View.VISIBLE);
        btninchitiet.setVisibility(View.VISIBLE);
        btnhttbc.setVisibility(View.VISIBLE);
    }

    // lay thong tin cuoc dong truoc
    private class GetAllListPaymentPrePaidAsyn
            extends AsyncTask<String, Void, ArrayList<PaymentPrePaidPromotionBeans>> {

        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetAllListPaymentPrePaidAsyn(Context context) {
            this.context = context;

            activity.findViewById(R.id.prbCuocdongtruoc).setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(String... arg0) {
            return getAllListPaymentPrePaid(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(ArrayList<PaymentPrePaidPromotionBeans> result) {

            activity.findViewById(R.id.prbCuocdongtruoc).setVisibility(View.GONE);
            arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
            if (errorCode.equals("0")) {
                if (CommonActivity.isNullOrEmptyArray(result)) {
                    PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
                    paymentPrePaidPromotionBeans.setName(getString(R.string.notcdc));
                    paymentPrePaidPromotionBeans.setPrePaidCode("-1");
                    paymentPrePaidPromotionBeans.setId("");
                    result.add(0, paymentPrePaidPromotionBeans);
                }


                arrPaymentPrePaidPromotionBeans.addAll(result);

                if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {
                    ArrayList<String> list = new ArrayList<>();

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                            android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                    for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                        //adapter.add(typePaperBeans.getName());
                        list.add(typePaperBeans.getName());
                    }
                    AutoCompleteUtil.getInstance(getActivity()).sortCDTBySelectedCount(AUTO_CDT_SEARCH, list);
                    adapter.clear();
                    adapter.addAll(list);
                    spinner_cuocdongtruoc.setAdapter(adapter);

                    System.out.println("12345 set spinner_cuocdongtruoc: " + mMapTemplate.get(CDT));
                    AutoCompleteUtil.getInstance(getActivity()).setSpinnerSelection(spinner_cuocdongtruoc, mMapTemplate.get(CDT), isFirst);
                }
                btn_connection.setVisibility(View.VISIBLE);
            } else {
                btn_connection.setVisibility(View.GONE);
                if (errorCode.equals(Constant.INVALID_TOKEN2) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    ArrayList<String> list = new ArrayList<>();

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                            android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                    for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                        //adapter.add(typePaperBeans.getName());
                        list.add(typePaperBeans.getName());
                    }
                    AutoCompleteUtil.getInstance(getActivity()).sortCDTBySelectedCount(AUTO_CDT_SEARCH, list);
                    adapter.clear();
                    adapter.addAll(list);
                    spinner_cuocdongtruoc.setAdapter(adapter);
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }

            }

        }

        private ArrayList<PaymentPrePaidPromotionBeans> getAllListPaymentPrePaid(String promProgramCode,
                                                                                 String packageId, String provinceCode, String today) {
            ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
            String original = null;
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getAllListPaymentPrePaid");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getAllListPaymentPrePaid>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<promProgramCode>" + promProgramCode);
                rawData.append("</promProgramCode>");

                rawData.append("<packageId>" + packageId);
                rawData.append("</packageId>");

                rawData.append("<type>" + "M");
                rawData.append("</type>");

                rawData.append("<today>" + today);
                rawData.append("</today>");

                rawData.append("</input>");
                rawData.append("</ws:getAllListPaymentPrePaid>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
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
                    nodepaymentPrePaidPromotionBeans = e2.getElementsByTagName("paymentPrePaidPromotionBeans");
                    for (int j = 0; j < nodepaymentPrePaidPromotionBeans.getLength(); j++) {
                        Element e1 = (Element) nodepaymentPrePaidPromotionBeans.item(j);
                        PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
                        String name = parse.getValue(e1, "name");
                        paymentPrePaidPromotionBeans.setName(name);
                        String id = parse.getValue(e1, "id");
                        paymentPrePaidPromotionBeans.setId(id);
                        String prePaidCode = parse.getValue(e1, "prePaidCode");
                        paymentPrePaidPromotionBeans.setPrePaidCode(prePaidCode);

                        ArrayList<PaymentPrePaidDetailBeans> lstPaymentPrePaidDetailBeans = new ArrayList<PaymentPrePaidDetailBeans>();

                        nodePaymentPrePaidDetailBeans = e1.getElementsByTagName("paymentPrePaidDetailBeans");
                        for (int k = 0; k < nodePaymentPrePaidDetailBeans.getLength(); k++) {
                            Element e0 = (Element) nodePaymentPrePaidDetailBeans.item(k);
                            PaymentPrePaidDetailBeans paymentPrePaidDetailBeans = new PaymentPrePaidDetailBeans();
                            String moneyUnit = parse.getValue(e0, "moneyUnit");
                            paymentPrePaidDetailBeans.setMoneyUnit(moneyUnit);
                            String promAmount = parse.getValue(e0, "promAmount");
                            paymentPrePaidDetailBeans.setPromAmount(promAmount);
                            String endMonth = parse.getValue(e0, "endMonth");
                            paymentPrePaidDetailBeans.setEndMonth(endMonth);
                            String startMonth = parse.getValue(e0, "startMonth");
                            paymentPrePaidDetailBeans.setStartMonth(startMonth);
                            String subMonth = parse.getValue(e0, "subMonth");
                            paymentPrePaidDetailBeans.setSubMonth(subMonth);
                            lstPaymentPrePaidDetailBeans.add(paymentPrePaidDetailBeans);
                        }

                        paymentPrePaidPromotionBeans.setLstDetailBeans(lstPaymentPrePaidDetailBeans);

                        lstPaymentPrePaidPromotionBeans.add(paymentPrePaidPromotionBeans);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstPaymentPrePaidPromotionBeans;
        }

    }

    private Dialog dialogCuocdongtruoc = null;

    private void showSelectCuocDongTruoc(PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans) {

        dialogCuocdongtruoc = new Dialog(activity);
        dialogCuocdongtruoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCuocdongtruoc.setContentView(R.layout.connection_layout_detail_precharge);

        ListView listdetail = (ListView) dialogCuocdongtruoc.findViewById(R.id.listdetail);

        EditText txttencuocdongtruoc = (EditText) dialogCuocdongtruoc.findViewById(R.id.txttencuocdongtruoc);
        txttencuocdongtruoc.setText(paymentPrePaidPromotionBeans.getName());

        EditText txtmacuocdongtruoc = (EditText) dialogCuocdongtruoc.findViewById(R.id.txtmacuocdongtruoc);
        txtmacuocdongtruoc.setText(paymentPrePaidPromotionBeans.getPrePaidCode());
        GetListPaymentDetailChargeAdapter getListPaymentDetailChargeAdapter = new GetListPaymentDetailChargeAdapter(
                paymentPrePaidPromotionBeans.getLstDetailBeans(), activity);
        listdetail.setAdapter(getListPaymentDetailChargeAdapter);

        Button btnchon = (Button) dialogCuocdongtruoc.findViewById(R.id.btnchon);
        btnchon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogCuocdongtruoc.dismiss();
            }
        });

        dialogCuocdongtruoc.show();
    }

    private void showDialogViewCommitment(StockIsdnBean stockIsdnBean) {

        final Dialog dialogViewCommitment = new Dialog(activity);
        dialogViewCommitment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogViewCommitment.setContentView(R.layout.dialog_layout_commitment);

        TextView tvDialogTitle = (TextView) dialogViewCommitment.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(getActivity().getString(R.string.ttincamketso));
        TextView tvIsdn = (TextView) dialogViewCommitment.findViewById(R.id.tvIsdn);
        tvIsdn.setText(stockIsdnBean.getIsdn());
        TextView tvmoney = (TextView) dialogViewCommitment.findViewById(R.id.tvmoney);
        tvmoney.setText(StringUtils.formatMoney(stockIsdnBean.getPrice()));
        TextView tvStockModelCode = (TextView) dialogViewCommitment.findViewById(R.id.tvStockModelCode);
        tvStockModelCode.setText(stockIsdnBean.getStockModelCode());

        TextView tvStockmodelname = (TextView) dialogViewCommitment.findViewById(R.id.tvStockmodelname);
        tvStockmodelname.setText(stockIsdnBean.getStockModelName());

        TextView tvmoneycamket = (TextView) dialogViewCommitment.findViewById(R.id.tvmoneycamket);
        tvmoneycamket.setText(StringUtils.formatMoney(stockIsdnBean.getPricePledgeAmount()));

        TextView tvmoneycamketungtruoc = (TextView) dialogViewCommitment.findViewById(R.id.tvmoneycamketungtruoc);
        tvmoneycamketungtruoc.setText(StringUtils.formatMoney(stockIsdnBean.getPricePriorPay()));

        TextView tvcamketthang = (TextView) dialogViewCommitment.findViewById(R.id.tvcamketthang);
        tvcamketthang.setText(stockIsdnBean.getPricePledgeTime());

        dialogViewCommitment.findViewById(R.id.btnclose).setOnClickListener(new OnClickListener() {

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

        TextView tvDialogTitle = (TextView) dialogViewStock.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(getActivity().getString(R.string.ttincamketthietbi));

        TextView tvSerial = (TextView) dialogViewStock.findViewById(R.id.tvSerial);
        tvSerial.setText(serial);

        TextView tvMoney = (TextView) dialogViewStock.findViewById(R.id.tvMoney);
        tvMoney.setText(StringUtils.formatMoney(priceBean.getPrice()));

        TextView tvmoneycamket = (TextView) dialogViewStock.findViewById(R.id.tvmoneycamket);
        tvmoneycamket.setText(StringUtils.formatMoney(priceBean.getPledgeAmount()));

        TextView tvmoneycamketungtruoc = (TextView) dialogViewStock.findViewById(R.id.tvmoneycamketungtruoc);
        tvmoneycamketungtruoc.setText(StringUtils.formatMoney(priceBean.getPriorPay()));

        TextView tvcamketthang = (TextView) dialogViewStock.findViewById(R.id.tvcamketthang);
        tvcamketthang.setText(priceBean.getPledgeTime());

        dialogViewStock.findViewById(R.id.btnclose).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogViewStock.dismiss();
            }
        });

        dialogViewStock.show();
    }

    private class AsynctaskviewSubCommitmentPre extends AsyncTask<String, Void, StockIsdnBean> {

        private Activity mActivity = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        private ProgressDialog progress;

        public AsynctaskviewSubCommitmentPre(Activity mActivity) {
            this.mActivity = mActivity;
            this.progress = new ProgressDialog(mActivity);

            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
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

                if (result != null && !CommonActivity.isNullOrEmpty(result.getIsdn())
                        && !CommonActivity.isNullOrEmpty(result.getPrice())
                        && !CommonActivity.isNullOrEmpty(result.getPricePledgeAmount())) {

                    showDialogViewCommitment(result);

                    // showSelectViewStock(result, serial);
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            getActivity().getResources().getString(R.string.khongcottincamket),
                            getActivity().getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                Log.d("Log", "description error update" + description);
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mActivity.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private StockIsdnBean viewSubCommitmentPre(String offerId, String regType, String isdn) {
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
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<regType>" + regType + "</regType>");
                rawData.append("<isdn>" + isdn + "</isdn>");

                rawData.append("</cmMobileInput>");
                rawData.append("</ws:viewSubCommitmentPre>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
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
                        // <stockModelName>Sá»‘ Ä‘áº¹p Mobile
                        // 5,200,000</stockModelName>
                        // </stockIsdnBean>

                        String isdn1 = parse.getValue(e1, "isdn");
                        stockIsdnBean.setIsdn(isdn1);

                        stockIsdnBean.setIsdnStatus(parse.getValue(e1, "isdnStatus"));
                        stockIsdnBean.setIsdnType(parse.getValue(e1, "isdnType"));
                        stockIsdnBean.setPrice(parse.getValue(e1, "price"));
                        stockIsdnBean.setPriceId(parse.getValue(e1, "priceId"));
                        stockIsdnBean.setPricePledgeAmount(parse.getValue(e1, "pricePledgeAmount"));
                        stockIsdnBean.setPricePledgeTime(parse.getValue(e1, "pricePledgeTime"));
                        stockIsdnBean.setPricePriorPay(parse.getValue(e1, "pricePriorPay"));

                        stockIsdnBean.setPriceVat(parse.getValue(e1, "priceVat"));
                        stockIsdnBean.setStockModelCode(parse.getValue(e1, "stockModelCode"));
                        stockIsdnBean.setStockModelId(parse.getValue(e1, "stockModelId"));
                        stockIsdnBean.setStockModelName(parse.getValue(e1, "stockModelName"));
                    }
                }
            } catch (Exception e) {
                Log.d("viewSubCommitmentStock", e.toString() + "description error", e);
            }

            return stockIsdnBean;

        }

    }

    private class AsynctaskviewSubCommitmentPos extends AsyncTask<String, Void, StockIsdnBean> {

        private Activity mActivity = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        private ProgressDialog progress;

        public AsynctaskviewSubCommitmentPos(Activity mActivity) {
            this.mActivity = mActivity;
            this.progress = new ProgressDialog(mActivity);

            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
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

                if (result != null && !CommonActivity.isNullOrEmpty(result.getIsdn())
                        && !CommonActivity.isNullOrEmpty(result.getPrice())
                        && !CommonActivity.isNullOrEmpty(result.getPricePledgeAmount())) {

                    showDialogViewCommitment(result);

                    // showSelectViewStock(result, serial);
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            getActivity().getResources().getString(R.string.khongcottincamket),
                            getActivity().getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                Log.d("Log", "description error update" + description);
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mActivity.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private StockIsdnBean viewSubCommitmentPos(String offerId, String regType, String isdn) {
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
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<regType>" + regType + "</regType>");
                rawData.append("<isdn>" + isdn + "</isdn>");

                rawData.append("</cmMobileInput>");
                rawData.append("</ws:viewSubCommitment>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_viewSubCommitment");
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
                        // <stockModelName>Sá»‘ Ä‘áº¹p Mobile
                        // 5,200,000</stockModelName>
                        // </stockIsdnBean>

                        String isdn1 = parse.getValue(e1, "isdn");
                        stockIsdnBean.setIsdn(isdn1);

                        stockIsdnBean.setIsdnStatus(parse.getValue(e1, "isdnStatus"));
                        stockIsdnBean.setIsdnType(parse.getValue(e1, "isdnType"));
                        stockIsdnBean.setPrice(parse.getValue(e1, "price"));
                        stockIsdnBean.setPriceId(parse.getValue(e1, "priceId"));
                        stockIsdnBean.setPricePledgeAmount(parse.getValue(e1, "pricePledgeAmount"));
                        stockIsdnBean.setPricePledgeTime(parse.getValue(e1, "pricePledgeTime"));
                        stockIsdnBean.setPricePriorPay(parse.getValue(e1, "pricePriorPay"));

                        stockIsdnBean.setPriceVat(parse.getValue(e1, "priceVat"));
                        stockIsdnBean.setStockModelCode(parse.getValue(e1, "stockModelCode"));
                        stockIsdnBean.setStockModelId(parse.getValue(e1, "stockModelId"));
                        stockIsdnBean.setStockModelName(parse.getValue(e1, "stockModelName"));
                    }
                }
            } catch (Exception e) {
                Log.d("viewSubCommitmentStock", e.toString() + "description error", e);
            }

            return stockIsdnBean;

        }

    }

    private class AsynctaskviewSubCommitmentStock extends AsyncTask<String, Void, PriceBean> {

        private Activity mActivity = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        private ProgressDialog progress;
        private String serial = "";

        public AsynctaskviewSubCommitmentStock(Activity mActivity, String serial) {
            this.mActivity = mActivity;
            this.progress = new ProgressDialog(mActivity);
            this.serial = serial;
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected PriceBean doInBackground(String... params) {
            return viewSubCommitmentStock(params[0], params[1], params[2], params[3], params[4]);
        }

        @Override
        protected void onPostExecute(PriceBean result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (errorCode.equals("0")) {

                if (result != null && !CommonActivity.isNullOrEmpty(result.getPrice())
                        && !CommonActivity.isNullOrEmpty(result.getPledgeAmount())
                        && !CommonActivity.isNullOrEmpty(result.getPriorPay())) {
                    showSelectViewStock(result, serial);
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            getActivity().getResources().getString(R.string.khongcottincamket),
                            getActivity().getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                Log.d("Log", "description error update" + description);
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mActivity.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private PriceBean viewSubCommitmentStock(String offerId, String regType, String isdn, String serial,
                                                 String stockModelId) {
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
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<regType>" + regType + "</regType>");
                rawData.append("<isdn>" + isdn + "</isdn>");
                rawData.append("<serial>" + serial + "</serial>");
                rawData.append("<stockModelId>" + stockModelId + "</stockModelId>");
                rawData.append("</cmMobileInput>");
                rawData.append("</ws:viewSubCommitment>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_viewSubCommitment");
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

                        String pledgeAmount = parse.getValue(e1, "pledgeAmount");
                        priceBean.setPledgeAmount(pledgeAmount);

                        String pledgeTime = parse.getValue(e1, "pledgeTime");
                        priceBean.setPledgeTime(pledgeTime);

                        String price = parse.getValue(e1, "price");
                        priceBean.setPrice(price);

                        String priceId = parse.getValue(e1, "priceId");
                        priceBean.setPriceId(priceId);

                        String priorPay = parse.getValue(e1, "priorPay");
                        priceBean.setPriorPay(priorPay);

                        String stockModelId1 = parse.getValue(e1, "stockModelId");
                        priceBean.setStockModelId(stockModelId1);

                        String vat = parse.getValue(e1, "vat");
                        priceBean.setVat(vat);

                    }
                }
            } catch (Exception e) {
                Log.d("viewSubCommitmentStock", e.toString() + "description error", e);
            }

            return priceBean;

        }

    }

    AdapterSpinerTTHHBCCS adapterSpinerTTHHBCCS = null;

    private void showDialogInsertSerial(final ProductOfferTypeDTO productOfferTypeDTO,
                                        final ProductOfferingDTO productOfferingDTO, final Spinner spinner,
                                        final AdapterSpinerTTHHBCCS mAdapterSpinerTTHH, final int pos) {
        adapterSpinerTTHHBCCS = mAdapterSpinerTTHH;
        dialogInsertSerial = new Dialog(getActivity());
        dialogInsertSerial.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInsertSerial.setContentView(R.layout.connectionmobile_serial_dialog);
        dialogInsertSerial.setCancelable(false);
        final TextView texteror = (TextView) dialogInsertSerial.findViewById(R.id.texterror);
        final EditText edtserial = (EditText) dialogInsertSerial.findViewById(R.id.edtserial);

        if (!CommonActivity.isNullOrEmpty(productOfferingDTO.getSerial())) {
            edtserial.setText(productOfferingDTO.getSerial());
        }

        dialogInsertSerial.findViewById(R.id.btnOk).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (edtserial.getText() != null && !edtserial.getText().toString().isEmpty()) {
                    if (StringUtils.CheckCharSpecical(edtserial.getText().toString()) == false) {
                        // set object hang hoa
                        productOfferingDTO
                                .setProductOfferTypeId(Long.parseLong(productOfferTypeDTO.getProductOfferTypeId()));
                        productOfferingDTO.setSerial(edtserial.getText().toString().trim());
                        mapSubStockModelRelReq.put(productOfferTypeDTO.getProductOfferTypeId(), productOfferingDTO);

                        if (spinner != null) {
                            for (ProductOfferingDTO item : productOfferTypeDTO.getProductOfferings()) {
                                if (item.getProductOfferingId() == productOfferingDTO.getProductOfferingId()) {

                                } else {
                                    item.setSerial("");
                                }
                            }
                            adapterSpinerTTHHBCCS.notifyDataSetChanged();
                            // adapterSpinerTTHHBCCS = new
                            // AdapterSpinerTTHHBCCS(productOfferTypeDTO.getProductOfferings(),
                            // getActivity());
                            // spinner.setAdapter(adapterSpinerTTHHBCCS);
                            spinner.setSelection(productOfferTypeDTO.getProductOfferings().indexOf(productOfferingDTO));
                            // adapterSpinerTTHHBCCS.notifyDataSetChanged();

                        }
                        mAdapterSpinerTTHH.notifyDataSetChanged();
                        if (dialogInsertSerial != null) {
                            dialogInsertSerial.cancel();
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
        dialogInsertSerial.findViewById(R.id.btnkiemtra).setVisibility(View.VISIBLE);
        dialogInsertSerial.findViewById(R.id.btnkiemtra).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!CommonActivity.isNullOrEmpty(edtserial.getText().toString())) {
                    CheckResourceAsyn checkResourceAsyn = new CheckResourceAsyn(getActivity(), productOfferTypeDTO,
                            productOfferingDTO, edtserial);
                    checkResourceAsyn.execute(edtserial.getText().toString().trim(), reasonId,
                            productOfferingDTO.getProductOfferingId() + "");
                } else {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.checkspecialSerial),
                                    getActivity().getString(R.string.app_name))
                            .show();
                }

            }
        });
        dialogInsertSerial.findViewById(R.id.btnViewSaleTrans).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!CommonActivity.isNullOrEmpty(productOfferTypeDTO)
                        && !CommonActivity.isNullOrEmpty(productOfferTypeDTO.getProductOfferings())) {
                    for (ProductOfferingDTO spin : productOfferTypeDTO.getProductOfferings()) {

                        if (spin.getProductOfferingId() == 0) {
                            spinner.setSelection(productOfferTypeDTO.getProductOfferings().indexOf(spin));
                            break;
                        }
                    }
                }

                if (adapProductAdapter != null) {
                    adapProductAdapter.notifyDataSetChanged();
                }
                dialogInsertSerial.dismiss();

            }
        });
        dialogInsertSerial.show();

    }

    private class SubConnectAsyn extends AsyncTask<String, String, String> {

        private ProgressDialog progress;
        private Context context = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        ArrayList<String> lstFilePath = new ArrayList<String>();
        private String methodName;

        public SubConnectAsyn(Context context, String method ) {
            this.context = context;
            this.methodName = method;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = new ProgressDialog(this.context);
            // check font
            if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                this.progress.setMessage(context.getResources().getString(R.string.progessing_changesub));
            } else {
                this.progress.setMessage(context.getResources().getString(R.string.progress_SubConnect));
            }

            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        // @Override
        // protected void onProgressUpdate(String... values) {
        // super.onProgressUpdate(values);
        // progress.setMessage(String.valueOf(values[0]));
        // Log.i("makemachine",
        // "onProgressUpdate(): " + String.valueOf(values[0]));
        // }

        @Override
        protected String doInBackground(String... params) {
            try {
                // publishProgress(context.getResources().getString(
                // R.string.subconnecting));

                if (hashmapFileObj != null && hashmapFileObj.size() > 0) {

                    File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
                    for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                        ArrayList<FileObj> listFileObjs = entry.getValue();
                        String zipFilePath = "";
                        if (listFileObjs.size() > 1) {
                            String spinnerCode = "";
                            for (FileObj fileObj : listFileObjs) {
                                spinnerCode = fileObj.getCodeSpinner();
                            }
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + spinnerCode + ".zip";
                            lstFilePath.add(zipFilePath);
                        } else if (listFileObjs.size() == 1) {
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + listFileObjs.get(0).getCodeSpinner() + ".jpg";
                            lstFilePath.add(zipFilePath);
                        }
                    }
                }
                String subConect = subConect(lstFilePath);
                return subConect;
            } catch (Exception e) {
                errorCode = "1";
                description = "Error when zip file: " + e.toString();
                return "1";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();

//			try {
//				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME, Session.userName, "mbccs_" + methodName,
//						CommonActivity.findMyLocation(context).getX(), CommonActivity.findMyLocation(context).getY());
//				timeEnd = new Date();
//
//				saveLog.saveLogRequest(errorCode, timeStart, timeEnd, Session.userName + "_"
//						+ CommonActivity.getDeviceId(context) + "_" + System.currentTimeMillis());
//			} catch (Exception e) {
//				Log.e("Exception", e.toString(), e);
//			}

            if (errorCode.equals("0")) {
                if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
                    AsynZipFile asynZipFile = new AsynZipFile(activity, hashmapFileObj, lstFilePath, description);
                    asynZipFile.execute();
                }else{
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.connectSuccess),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                lnButton.setVisibility(View.VISIBLE);
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private String subConect(ArrayList<String> mlstFilePath) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + methodName + ">");
                rawData.append("<input>");


                //omni
                if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail) && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getReciveRequestId())) {
                    rawData.append("<hotlineInput>");
                    rawData.append("<receiveRequestId>" + hotLineReponseDetail.getReciveRequestId() + "</receiveRequestId>");
                    rawData.append("</hotlineInput>");
                }

                HashMap<String, String> paramToken = new HashMap<String, String>();

                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                if(!CommonActivity.isNullOrEmpty(mlstFilePath)){
                    for (String fileObj : mlstFilePath) {
                        rawData.append("<lstFilePath>");
                        rawData.append(fileObj);
                        rawData.append("</lstFilePath>");
                    }
                }

                if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
                    for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                        ArrayList<FileObj> listFileObjs = entry.getValue();
                        rawData.append("<lstRecordName>");
                        rawData.append(listFileObjs.get(0).getRecodeName());
                        rawData.append("</lstRecordName>");
                        rawData.append("<lstRecordCode>");
                        rawData.append(listFileObjs.get(0).getCodeSpinner());
                        rawData.append("</lstRecordCode>");
                    }
                }

                if (!CommonActivity.isNullOrEmpty(custypeKey)) {
                    rawData.append("<custType>");
                    rawData.append(custypeKey);
                    rawData.append("</custType>");
                }
                if (!CommonActivity.isNullOrEmpty(custypeKeyPr)) {
                    rawData.append("<custTypeRepresent>");
                    rawData.append(custypeKeyPr);
                    rawData.append("</custTypeRepresent>");
                }
//                }
                rawData.append("<fileContent>");
                rawData.append("");
                rawData.append("</fileContent>");

                rawData.append("<noSendFile>" + "NO_SEND_FILE");
                rawData.append("</noSendFile>");
                // ========customer ===============

                rawData.append("<requiredRoleMap>");
                SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME,
                        Activity.MODE_PRIVATE);

                String name = preferences.getString(Define.KEY_MENU_NAME, "");
                rawData.append("<values>" + 1);
                rawData.append("</values>");

                rawData.append("</requiredRoleMap>");
                rawData.append("<actionUserDTO>");
                rawData.append("<systemType>" + "MBCCS");
                rawData.append("</systemType>");

                rawData.append("</actionUserDTO>");


                // tra truoc thi truyen thong tin khach hang con tra sau thi
                // khong
                if ("2".equals(subType)) {

                    if (ActivityCreateNewRequestMobileNew.instance != null
                            && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)
                            && Constant.CHANGE_POS_TO_PRE
                            .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        if (hthmBeans != null && !CommonActivity.isNullOrEmpty(hthmBeans.getDescription()) && !"$GS$".contains(hthmBeans.getDescription())) {

                        } else {
                            rawData.append("<newSerial>" + txtserial.getText().toString().trim());
                            rawData.append("</newSerial>");
                        }
                    }

                    rawData.append("<customerDTO>");
                    rawData.append(xmlCustPre());
                    rawData.append("</customerDTO>");
                }

                rawData.append("<subscriberDTO>");
                rawData.append(subXml(false));
                //truyen loai doi tuong
                rawData.append("<subObject>" + subObject + "</subObject>");
                rawData.append("</subscriberDTO>");

                rawData.append("</input>");
                rawData.append("</ws:" + methodName + ">");


                String rawDta = rawData.toString().replace(">null<", "><");
                Log.i("RowData", rawDta);
                String envelope = input.buildInputGatewayWithRawData(rawDta);
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_" + methodName);
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

            } catch (

                    Exception e) {
                Log.e("Exception", e.toString(), e);
            }
            return errorCode;

        }

    }

    @Override
    public void OnChangeSpinerSelectProduct(ProductOfferTypeDTO productOfferTypeDTO,
                                            ProductOfferingDTO productOfferingDTO, int arg2, final Spinner spinner,
                                            AdapterSpinerTTHHBCCS mAdapterSpinerTTHH) {

        // mat hang no serial
        if (arg2 == 0) {
            mapSubStockModelRelReq = new HashMap<String, ProductOfferingDTO>();
        } else {
            if ("11".equals(productOfferTypeDTO.getProductOfferTypeId())) {
                productOfferingDTO.setProductOfferTypeId(Long.parseLong(productOfferTypeDTO.getProductOfferTypeId()));
                productOfferingDTO.setSerial("");
                mapSubStockModelRelReq.put(productOfferTypeDTO.getProductOfferTypeId(), productOfferingDTO);
            } else {
                showDialogInsertSerial(productOfferTypeDTO, productOfferingDTO, spinner, mAdapterSpinerTTHH, arg2);
            }
        }

    }

    // lay danh sach han muc
    private class GetQuotaMapByTelecomServiceAsyn extends AsyncTask<String, Void, ArrayList<Spin>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private XmlDomParse parse = new XmlDomParse();

        public GetQuotaMapByTelecomServiceAsyn(Context context) {

            this.mContext = context;
            prbqouta.setVisibility(View.VISIBLE);
            btnRefreshqouta.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Spin> doInBackground(String... arg0) {

            return getQoutaByTelecomService(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<Spin> result) {
            super.onPostExecute(result);
            prbqouta.setVisibility(View.GONE);
            btnRefreshqouta.setVisibility(View.VISIBLE);
            if (errorCode.equalsIgnoreCase("0")) {
                arrQouta = new ArrayList<Spin>();
                arrQouta = result;
                try {
                    Spin spin = new Spin();
                    spin.setId("");
                    spin.setValue(getActivity().getString(R.string.txt_select));
                    arrQouta.add(0, spin);
                    Utils.setDataSpinner(getActivity(), arrQouta, spinner_quota);
                } catch (Exception e) {

                }

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }
                    Utils.setDataSpinner(getActivity(), result, spinner_quota);
                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }
        }

        private ArrayList<Spin> getQoutaByTelecomService(String custType, String productCode, String serviceType) {
            ArrayList<Spin> lstQouta = new ArrayList<Spin>();
            String original = "";
            try {
                lstQouta = new CacheDatabaseManager(mContext).getListQoutaInCache(telecomserviceId, offerId);
                if (lstQouta != null && !lstQouta.isEmpty()) {
                    errorCode = "0";
                    return lstQouta;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getQuotaMapByTelecomService");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getQuotaMapByTelecomService>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<custType>" + custType + "</custType>");
                rawData.append("<productCode>" + productCode + "</productCode>");
                rawData.append("<serviceType>" + serviceType + "</serviceType>");

                rawData.append("</input>");
                rawData.append("</ws:getQuotaMapByTelecomService>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getQuotaMapByTelecomService");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);
                    nodechild = doc.getElementsByTagName("lstOptionSetValueDTOs");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        Spin spin = new Spin();
                        spin.setValue(parse.getValue(e1, "name"));
                        Log.d("LOG", "value: " + spin.getValue());
                        spin.setId(parse.getValue(e1, "value"));
                        Log.d("LOG", "Idddd: " + spin.getId());
                        lstQouta.add(spin);
                    }
                }

                new CacheDatabaseManager(mContext).insertQoutaCache(telecomserviceId, offerId, lstQouta);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstQouta;
        }

    }

    private class GetListDepositAsyn extends AsyncTask<String, Void, ArrayList<Spin>> {
        private XmlDomParse parse = new XmlDomParse();
        private Context mContext;
        private String errorCode;
        private String description;

        public GetListDepositAsyn(Context context) {
            this.mContext = context;
            prbdeposit.setVisibility(View.VISIBLE);
            btndeposit.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Spin> doInBackground(String... arg0) {
            return getListDeposit();
        }

        @Override
        protected void onPostExecute(ArrayList<Spin> result) {
            super.onPostExecute(result);
            prbdeposit.setVisibility(View.GONE);
            btndeposit.setVisibility(View.VISIBLE);
            if (errorCode.equalsIgnoreCase("0")) {
                try {
                    Spin spin = new Spin();
                    spin.setId("");
                    spin.setValue(getActivity().getString(R.string.txt_select));
                    result.add(0, spin);
                    Utils.setDataSpinner(getActivity(), result, spinner_deposit);
                } catch (Exception e) {
                }

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }
                    Utils.setDataSpinner(getActivity(), result, spinner_deposit);
                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private ArrayList<Spin> getListDeposit() {
            ArrayList<Spin> lstDeposit = new ArrayList<Spin>();
            String original = "";
            try {
                lstDeposit = new CacheDatabaseManager(mContext).getListDepositInCache();
                if (lstDeposit != null && !lstDeposit.isEmpty()) {
                    errorCode = "0";
                    return lstDeposit;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListDeposit");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListDeposit>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("</input>");
                rawData.append("</ws:getListDeposit>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_getListDeposit");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);
                    nodechild = doc.getElementsByTagName("lstOptionSetValueDTOs");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        Spin spin = new Spin();
                        spin.setValue(parse.getValue(e1, "name"));
                        Log.d("LOG", "value: " + spin.getValue());
                        spin.setId(parse.getValue(e1, "value"));
                        Log.d("LOG", "Idddd: " + spin.getId());
                        lstDeposit.add(spin);
                    }
                }

                new CacheDatabaseManager(mContext).insertDepositCache(lstDeposit);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lstDeposit;
        }

    }

    // lay thong tin hop dong cu
    private class GetAccountInforAsyn extends AsyncTask<String, Void, AccountDTO> {
        private ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetAccountInforAsyn(Context context) {
            this.context = context;

            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected AccountDTO doInBackground(String... params) {
            return getAccountInfo(params[0]);
        }

        @Override
        protected void onPostExecute(AccountDTO result) {
            progress.dismiss();
            accountDTO = new AccountDTO();
            if ("0".equals(errorCode)) {
                // thong tin hop dong cu
                if (result != null) {
                    accountDTO = result;
                    addInfoStr = accountDTO.getAddInfo();
                    showPopupInsertInfoContractOffer(result, false);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notcontract),
                            getActivity().getString(R.string.app_name)).show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private AccountDTO getAccountInfo(String accountId) {
            AccountDTO accountDTO = new AccountDTO();
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getAccountInfor");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getAccountInfor>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<accountId>" + accountId);
                rawData.append("</accountId>");

                rawData.append("</input>");
                rawData.append("</ws:getAccountInfor>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_getAccountInfor");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    accountDTO = parseOuput.getAccountDTO();
                    Log.i("DATA", "AddInfo: " + accountDTO.getAddInfo());
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }

            return accountDTO;
        }
    }

    // init gioi tinh
    private void initSex() {
        arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SexBeans sexBeans : arrSexBeans) {
                adapter.add(sexBeans.getName());
            }
            spinner_gioitinhDD.setAdapter(adapter);
        }
    }

    private class GetMappingCustIdentityUsageAsyn extends AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";
        ProgressBar prbarCus;
        Button btnRefresh;
        Spinner spinerPaper;

        public GetMappingCustIdentityUsageAsyn(Context context, ProgressBar prb, Button btnres, Spinner spin) {
            this.context = context;
            this.prbarCus = prb;
            this.btnRefresh = btnres;
            this.spinerPaper = spin;
            if (prbarCus != null) {
                prbarCus.setVisibility(View.VISIBLE);
            }
            if (btnRefresh != null) {
                btnRefresh.setVisibility(View.GONE);
            }

        }

        @Override
        protected ArrayList<CustIdentityDTO> doInBackground(String... params) {
            return getMappingCustIdentityUsage(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustIdentityDTO> result) {
            if (prbarCus != null) {
                prbarCus.setVisibility(View.GONE);
            }
            if (btnRefresh != null) {
                btnRefresh.setVisibility(View.VISIBLE);
            }
            arrTypePaper = new ArrayList<CustIdentityDTO>();
            if ("0".equals(errorCode)) {
                // lay danh sach loai giay to
                arrTypePaper = result;
                initTypePaper(arrTypePaper, spinerPaper);
                if (result != null && !result.isEmpty()) {
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            getResources().getString(R.string.notpapaer), getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.equals("")) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustIdentityDTO> getMappingCustIdentityUsage(String currCusType) {
            ArrayList<CustIdentityDTO> lstTypePaper = new ArrayList<CustIdentityDTO>();
            String original = null;
            try {
                lstTypePaper = new CacheDatabaseManager(context).getListTypePaperFromMap(currCusType);
                if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
                    errorCode = "0";
                    return lstTypePaper;
                }

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_mappingCustIdentityUsage");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:mappingCustIdentityUsage>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<currCustType>" + currCusType);
                rawData.append("</currCustType>");
                rawData.append("</input>");
                rawData.append("</ws:mappingCustIdentityUsage>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_mappingCustIdentityUsage");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ========parser xml get employ from server
                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstTypePaper = parseOuput.getLstCustIdentityDTOs();
                }

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }
            new CacheDatabaseManager(context).insertTypePaper(currCusType, lstTypePaper);

            return lstTypePaper;
        }
    }

    private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper) {
        if (lstTypePaper == null) {
            lstTypePaper = new ArrayList<CustIdentityDTO>();
        }

        ArrayAdapter<String> adapter = null;
        // if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1);
        for (CustIdentityDTO custIdentityDTO : lstTypePaper) {
            adapter.add(custIdentityDTO.getIdTypeName());
        }
        spinerPaper.setAdapter(adapter);
        // }
    }

    private class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetMappingChannelCustTypeAsyn(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<CustTypeDTO> doInBackground(String... params) {
            return getMappingChannelCustType(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustTypeDTO> result) {
            arrCusTypeDTO = new ArrayList<CustTypeDTO>();
            if ("0".equals(errorCode)) {
                if (result != null && !result.isEmpty()) {
                    arrCusTypeDTO = result;
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            getResources().getString(R.string.checkTypeCus),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.equals("")) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustTypeDTO> getMappingChannelCustType(String payType) {
            ArrayList<CustTypeDTO> lstCustTypeDTOs = new ArrayList<CustTypeDTO>();
            String original = null;
            try {
                // tra truoc
                if ("2".equals(payType)) {
                    lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPre");
                } else {
                    // tra sau
                    lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPos");
                }

                if (lstCustTypeDTOs != null && !lstCustTypeDTOs.isEmpty()) {
                    errorCode = "0";
                    return lstCustTypeDTOs;
                }

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getMappingChannelCustType");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getMappingChannelCustType>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<payType>" + payType);
                rawData.append("</payType>");
                rawData.append("</input>");
                rawData.append("</ws:getMappingChannelCustType>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getMappingChannelCustType");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ========parser xml get employ from server
                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstCustTypeDTOs = parseOuput.getLstCustTypeDTO();
                }

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }
            if ("2".equals(payType)) {
                new CacheDatabaseManager(context).insertCusType("cusTypeDTOPre", lstCustTypeDTOs);
            } else {
                new CacheDatabaseManager(context).insertCusType("cusTypeDTOPos", lstCustTypeDTOs);
            }

            return lstCustTypeDTOs;
        }
    }

    private void enableContract() {
        edt_contractNo.setEnabled(true);
        // ngay ky hop dong
        edt_signDate.setEnabled(true);
        edt_signDate.setText(dateNowString);
        // chu ky cuoc
        spn_billCycleFromCharging.setEnabled(true);
        // hinh thuc thanh toan
        spn_payMethodCode.setEnabled(true);
        // hinh thuc thong bao cuoc
        spn_noticeCharge.setEnabled(true);
        /**
         * Thong tin ngan hang
         */
        lnBankAccount.setVisibility(View.GONE);
        edit_ngaynhothu.setEnabled(true);
        edit_ngaynhothu.setText(dateNowString);// ngay
        edthdnhothu.setText("");
        edthdnhothu.setEnabled(true);
        edittkoanhd.setText("");
        edittkoanhd.setEnabled(true); // tai
        edttentkoan.setText("");
        edttentkoan.setEnabled(true); // ten
        txtnganhang.setEnabled(true); // chon
        spn_printMethodCode.setEnabled(true); // in

        if (!CommonActivity.isNullOrEmptyArray(arrINCT)) {
            for (int i = 0; i < arrINCT.size(); i++) {
                Spin spin = arrINCT.get(i);
                if ("2".equals(spin.getId())) {
                    spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
                    break;
                }
            }
        }

        edtemailtbc.setEnabled(true);
        edtdtcdtbc.setEnabled(true);
        edtdidongtbc.setEnabled(true);
        txtdctbc.setEnabled(true);
        edtdchdcuoc.setEnabled(true);
        // thong tin dai dien hop dong
        edtloaikhDD.setText("");
        edtloaikhDD.setHint(getActivity().getString(R.string.chonloaiKH));
        edtloaikhDD.setEnabled(true);
        edit_sogiaytoDD.setText("");
        edit_sogiaytoDD.setEnabled(true);
        edit_ngaycapDD.setEnabled(true);
        edit_ngaycapDD.setText(dateNowString);
        edtnoicap.setText("");
        edtnoicap.setEnabled(true);
        txtdcgtxmDD.setText(getActivity().getString(R.string.sellect_address));
        txtdcgtxmDD.setEnabled(true);
        edit_tenKHDD.setText("");
        edit_tenKHDD.setEnabled(true);
        edit_ngaysinhdDD.setEnabled(true);
        edit_ngaysinhdDD.setText(dateNowString);
        spinner_quoctichpr.setEnabled(true);
        spinner_type_giay_to_parent.setEnabled(true);
        spinner_gioitinhDD.setEnabled(true);
    }

    private void disableContract() {

        btnchukicuoc.setVisibility(View.GONE);
        btnhttbc.setVisibility(View.GONE);
        btnhttthd.setVisibility(View.GONE);
        btninchitiet.setVisibility(View.GONE);

        prbchukicuoc.setVisibility(View.GONE);
        prbhttbc.setVisibility(View.GONE);
        prbhttthd.setVisibility(View.GONE);
        prbinchitiet.setVisibility(View.GONE);

        edt_contractNo.setEnabled(false);
        // ngay ky hop dong
        edt_signDate.setEnabled(false);
        // chu ky cuoc
        spn_billCycleFromCharging.setEnabled(false);
        // hinh thuc thanh toan
        spn_payMethodCode.setEnabled(false);
        // hinh thuc thong bao cuoc
        spn_noticeCharge.setEnabled(false);
        /**
         * Thong tin ngan hang
         */
        lnBankAccount.setVisibility(View.GONE);
        edit_ngaynhothu.setEnabled(false);
        edit_ngaynhothu.setText(dateNowString);// ngay
        edthdnhothu.setEnabled(false);
        edittkoanhd.setEnabled(false); // tai
        edttentkoan.setEnabled(false); // ten
        txtnganhang.setEnabled(false); // chon
        spn_printMethodCode.setEnabled(false); // in

        edtemailtbc.setEnabled(false);
        edtdtcdtbc.setEnabled(false);
        edtdidongtbc.setEnabled(false);
        txtdctbc.setEnabled(false);
        edtdchdcuoc.setEnabled(false);
        // thong tin dai dien hop dong

        edtloaikhDD.setEnabled(false);
        edit_sogiaytoDD.setEnabled(false);
        edit_ngaycapDD.setEnabled(false);
        edtnoicap.setEnabled(false);
        txtdcgtxmDD.setEnabled(false);
        edit_tenKHDD.setEnabled(false);
        edit_ngaysinhdDD.setEnabled(false);
        edit_ngaysinhdDD.setText(dateNowString);
        spinner_quoctichpr.setEnabled(false);
        spinner_type_giay_to_parent.setEnabled(false);
        spinner_gioitinhDD.setEnabled(false);
    }

    private boolean validateContract(final AccountDTO item) throws Exception {

        if (item != null && item.getAccountId() != null && !CommonActivity.isNullOrEmpty(item.getAccountNo())) {
            if (!CommonActivity.isNullOrEmpty(item.getRefCustomer())
                    && !CommonActivity.isNullOrEmpty(item.getRefCustomer().getCustId())) {
                repreCustomer = new CustIdentityDTO();
                repreCustomer.setCustomer(item.getRefCustomer());
            }

            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
                // validate kh cu
                if (repreCustomer != null && repreCustomer.getCustomer().getCustId() != null) {
                    return true;
                } else {
                    // validate kh moi
                    if (custTypeDTOContractPR == null) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    if (CommonActivity.isNullOrEmpty(edit_tenKHDD.getText().toString())) {

                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checknameKH),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if (StringUtils.CheckCharSpecical(edit_tenKHDD.getText().toString())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(edit_ngaysinhdDD.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.message_pleass_input_birth_day),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    Date birthDate = sdf.parse(edit_ngaysinhdDD.getText().toString().trim());
                    if (birthDate.after(dateNow)) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    int diffYears = CommonActivity.getDiffYearsMain(birthDate, dateNow);
                    Log.d("diffYears", "diffYears = " + diffYears);
                    if (diffYears < 18) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.khdd18),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }


                    if ("ID".equals(typePaperDialogPR)) {

                        if (edit_sogiaytoDD.getText().toString().length() != 9
                                && edit_sogiaytoDD.getText().toString().length() != 12) {
                            CommonActivity
                                    .createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                                            getActivity().getString(R.string.app_name))
                                    .show();
                            return false;
                        }
                    }

                    if (CommonActivity.isNullOrEmpty(edit_ngaycapDD.getText().toString())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }

                    Date datengaycap = sdf.parse(edit_ngaycapDD.getText().toString().trim());

                    if (datengaycap.after(dateNow)) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.ngaycapnhohonngayhientai),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    if (datengaycap.before(birthDate)) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }

                    if ("ID".equals(typePaperDialogPR)) {
                        if (CommonActivity.getDiffYearsMain(birthDate, datengaycap) < 14) {
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap14),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }
                        if (CommonActivity.getDiffYears(datengaycap, dateNow) > 15) {
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap15),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }
                    }


                    if (CommonActivity.isNullOrEmpty(edtnoicap.getText().toString().trim())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }

                    // validate ngay het han
                    if (!CommonActivity.isNullOrEmpty(arrMapusage)) {
                        for (Spin spin : arrMapusage) {
                            if (CommonActivity.checkMapUsage(typePaperDialogPR, spin)) {
                                // truong hop ma bat buoc nhap nhat het han thi phai validate
                                if (CommonActivity.isNullOrEmpty(edit_ngayhethandd.getText().toString())) {
                                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateExpiredate),
                                            getActivity().getString(R.string.app_name)).show();
                                    return false;
                                }

                                Date datenExpired = sdf.parse(edit_ngayhethandd.getText().toString().trim());
                                if (datenExpired.before(datengaycap)) {
                                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanngaycap),
                                            getActivity().getString(R.string.app_name)).show();
                                    return false;
                                }
                                if (datenExpired.before(dateNow)) {
                                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanhientai),
                                            getActivity().getString(R.string.app_name)).show();
                                    return false;
                                }
                                break;
                            }
                        }
                    }
                    if (!CommonActivity.isNullOrEmpty(custTypeDTOContractPR) && "3".equals(custTypeDTOContractPR.getCusGroup())) {

                    } else {
                        if (CommonActivity.isNullOrEmpty(provinceContractPR)
                                && CommonActivity.isNullOrEmpty(districtContractPR)
                                && CommonActivity.isNullOrEmpty(precintContractPR)
                                && CommonActivity.isNullOrEmpty(streetBlockContractPR)) {

                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }
                    }

                }
            }

        }

        if (CommonActivity.isNullOrEmpty(edt_signDate.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.signDateEmpty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        Spin spinCharge = (Spin) spn_billCycleFromCharging.getSelectedItem();
        if (spinCharge == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.ckcnotempty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        Spin spinPay = (Spin) spn_payMethodCode.getSelectedItem();
        if (spinPay == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.paymethodEmpty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        Spin spinMethod = (Spin) spn_noticeCharge.getSelectedItem();
        if (spinMethod == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.noticeEmpty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        // 1 va 5
        if ("1".equals(spinMethod.getId()) || "5".equals(spinMethod.getId())) {

            if (CommonActivity.isNullOrEmpty(edtemailtbc.getText().toString().trim())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.emailempty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }

        }

        if ("02".equalsIgnoreCase(spinPay.getId()) || "03".equalsIgnoreCase(spinPay.getId())) {
            if (CommonActivity.isNullOrEmpty(edittkoanhd.getText().toString().trim())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.accountnotempty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(edttentkoan.getText().toString().trim())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.tentknotempty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }

            if (item != null && !CommonActivity.isNullOrEmpty(item.getAccountBank())) {

            } else {
                if (bankBean == null) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nganhangnotempty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(bankBean.getCode())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nganhangnotempty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            }


        }

        Spin spinPrinMethod = (Spin) spn_printMethodCode.getSelectedItem();
        if (spinPrinMethod == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.inchitietEmpty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        // if
        // (CommonActivity.isNullOrEmpty(edtemailtbc.getText().toString().trim()))
        // {
        // CommonActivity.createAlertDialog(getActivity(),
        // getActivity().getString(R.string.emailempty),
        // getActivity().getString(R.string.app_name)).show();
        // return false;
        // }
        if (CommonActivity.isNullOrEmpty(edtdidongtbc.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.phoneempty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(provinceContract) && CommonActivity.isNullOrEmpty(districtContract)
                && CommonActivity.isNullOrEmpty(precintContract)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.billAdressNotEmpty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edtdchdcuoc.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addresschargenotempty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        // validate thong tin khach hang dai dien doanh nghiep
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {

            // validate kh cu
            if (repreCustomer != null && repreCustomer.getCustomer().getCustId() != null) {
                return true;
            } else {
                // validate kh moi
                if (custTypeDTOContractPR == null) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                if (CommonActivity.isNullOrEmpty(edit_tenKHDD.getText().toString())) {

                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checknameKH),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (StringUtils.CheckCharSpecical(edit_tenKHDD.getText().toString())) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(edit_ngaysinhdDD.getText().toString())) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.message_pleass_input_birth_day),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                Date birthDate = sdf.parse(edit_ngaysinhdDD.getText().toString().trim());
                if (birthDate.after(dateNow)) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                int diffYears = CommonActivity.getDiffYearsMain(birthDate, dateNow);
                Log.d("diffYears", "diffYears = " + diffYears);
                if (diffYears < 18) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.khdd18),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString())) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return false;
                }


                if ("ID".equals(typePaperDialogPR)) {

                    if (edit_sogiaytoDD.getText().toString().length() != 9
                            && edit_sogiaytoDD.getText().toString().length() != 12) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }
                }

                if (CommonActivity.isNullOrEmpty(edit_ngaycapDD.getText().toString())) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return false;
                }

                Date datengaycap = sdf.parse(edit_ngaycapDD.getText().toString().trim());

                if (datengaycap.after(dateNow)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.ngaycapnhohonngayhientai),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                if (datengaycap.before(birthDate)) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return false;
                }

                if ("ID".equals(typePaperDialogPR)) {
                    if (CommonActivity.getDiffYearsMain(birthDate, datengaycap) < 14) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap14),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if (CommonActivity.getDiffYears(datengaycap, dateNow) > 15) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap15),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }


                if (CommonActivity.isNullOrEmpty(edtnoicap.getText().toString().trim())) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return false;
                }

                // validate ngay het han
                if (!CommonActivity.isNullOrEmpty(arrMapusage)) {
                    for (Spin spin : arrMapusage) {
                        if (CommonActivity.checkMapUsage(typePaperDialogPR, spin)) {
                            // truong hop ma bat buoc nhap nhat het han thi phai validate
                            if (CommonActivity.isNullOrEmpty(edit_ngayhethandd.getText().toString())) {
                                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateExpiredate),
                                        getActivity().getString(R.string.app_name)).show();
                                return false;
                            }

                            Date datenExpired = sdf.parse(edit_ngayhethandd.getText().toString().trim());
                            if (datenExpired.before(datengaycap)) {
                                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanngaycap),
                                        getActivity().getString(R.string.app_name)).show();
                                return false;
                            }
                            if (datenExpired.before(dateNow)) {
                                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanhientai),
                                        getActivity().getString(R.string.app_name)).show();
                                return false;
                            }
                            break;
                        }
                    }
                }
                if (!CommonActivity.isNullOrEmpty(custTypeDTOContractPR) && "3".equals(custTypeDTOContractPR.getCusGroup())) {

                } else {
                    if (CommonActivity.isNullOrEmpty(provinceContractPR)
                            && CommonActivity.isNullOrEmpty(districtContractPR)
                            && CommonActivity.isNullOrEmpty(precintContractPR)
                            && CommonActivity.isNullOrEmpty(streetBlockContractPR)) {

                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // check thong tin goi cuoc
    public class CheckProductAsyn extends AsyncTask<String, Void, String> {

        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        private String methodName;
        private Context mContext;

        public CheckProductAsyn(Context context, String method) {
            this.mContext = context;
            this.methodName = method;
        }

        @Override
        protected String doInBackground(String... arg0) {
            return checkProduct(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            checkPCProduct = "";
            if ("0".equals(errorCode)) {
                checkPCProduct = result;
                if ("true".equals(checkPCProduct)) {
                    lndiachi.setVisibility(View.VISIBLE);
                } else {

                    if ("H".equals(serviceType)) {
                        lndiachi.setVisibility(View.VISIBLE);
                    } else {
                        lndiachi.setVisibility(View.GONE);
                    }

                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private String checkProduct(String productCode) {
            String original = "";
            String checkProduct = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + methodName + ">");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<productCode>" + productCode);
                rawData.append("</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:" + methodName + ">");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_" + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    checkProduct = parse.getValue(e2, "isCheckPrduct");
                }
                return checkProduct;
            } catch (Exception e) {
            }
            return checkProduct;
        }

    }

    private class SearchCustIdentityAsyn extends AsyncTask<String, Void, ParseOuput> {

        private ProgressDialog progress;
        private Context context = null;
        private String errorCode = "";
        private String description = "";

        private String check = "";

        public SearchCustIdentityAsyn(Context context, String mCheck) {
            this.context = context;
            this.check = mCheck;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ParseOuput doInBackground(String... arg0) {
            return searchCus(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(final ParseOuput result) {

            progress.dismiss();
            arrCustIdentityDTODialog = new ArrayList<CustIdentityDTO>();
            if ("0".equals(errorCode)) {
                if (result != null) {

                    if (result.getLstCustIdentityDTOs() != null && result.getLstCustIdentityDTOs().size() > 0) {
                        arrCustIdentityDTODialog = result.getLstCustIdentityDTOs();

                        if (!CommonActivity.isNullOrEmpty(check)) {
                            showSelectCus(arrCustIdentityDTODialog);
                        }

                    } else {
                        if (description != null && !description.isEmpty()) {
                            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                    getResources().getString(R.string.app_name));
                            dialog.show();
                        } else {
                            Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                    getResources().getString(R.string.notkh),
                                    getResources().getString(R.string.app_name));
                            dialog.show();
                        }
                    }

                } else {
                    if (description != null && !description.isEmpty()) {
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

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

        private ParseOuput searchCus(String idNo, String custType, String idType) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_searchCustIdentity");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:searchCustIdentity>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<custIdentitySearchDTO>");
                rawData.append("<idNo>" + idNo.trim());
                rawData.append("</idNo>");
//                if (!CommonActivity.isNullOrEmpty(custType)) {
//                    rawData.append("<custType>" + custType);
//                    rawData.append("</custType>");
//                }

                rawData.append("</custIdentitySearchDTO>");
                rawData.append("</input>");
                rawData.append("</ws:searchCustIdentity>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_searchCustIdentity");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                }

                return parseOuput;
            } catch (Exception e) {
                Log.i("SearchCustIdentity", e.toString());
            }
            return null;
        }
    }

    private void showSelectCus(final ArrayList<CustIdentityDTO> lstCusIdentity) {
        dialogCus = new Dialog(getActivity());
        dialogCus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCus.setContentView(R.layout.connection_layout_select_customer);
        dialogCus.setCancelable(false);
        ListView lvcustomerDiaLog = (ListView) dialogCus.findViewById(R.id.listcustomer);
        adaGetListCustomerBccsAdapterDialog = new GetListCustomerBccsAdapter(getActivity(), lstCusIdentity, null);
        lvcustomerDiaLog.setAdapter(adaGetListCustomerBccsAdapterDialog);

        lvcustomerDiaLog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                repreCustomer = lstCusIdentity.get(arg2);
                AsynGetCustomerByCustId asynGetCustomerByCustId = new AsynGetCustomerByCustId(getActivity(),
                        repreCustomer);
                asynGetCustomerByCustId.execute(repreCustomer.getCustomer().getCustId() + "");

            }
        });

        Button btnCancel = (Button) dialogCus.findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogCus.cancel();
            }
        });

        dialogCus.show();
    }

    private class AsynGetCustomerByCustId extends AsyncTask<String, Void, CustomerDTO> {

        private String errorCode;
        private String description;
        private Context context;
        private ProgressDialog progress;
        private CustIdentityDTO reCustIdentityDTO;

        public AsynGetCustomerByCustId(Context mContext, CustIdentityDTO custIdentityDTO) {
            this.context = mContext;
            this.reCustIdentityDTO = custIdentityDTO;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected CustomerDTO doInBackground(String... params) {
            return getCustomerByCustId(params[0]);
        }

        @Override
        protected void onPostExecute(CustomerDTO result) {
            progress.dismiss();
            super.onPostExecute(result);
            if ("0".equals(errorCode)) {
                // thong tin hop dong cu
                // customerDTODialog = new CustomerDTO();
                if (result != null && result.getCustId() != null) {
                    // customerDTODialog = result;
                    // xu ly thong tin khach hang dai dien cu cho nay
                    // reCustIdentityDTO.setCustomer(customerDTODialog);
                    btnkiemtra.setVisibility(View.GONE);
                    btnedit.setVisibility(View.VISIBLE);
                    enableRepresentCus(result);
                    accountDTOMain.setRefCustomer(result);

                    // if (dialogContract != null) {
                    // dialogContract.dismiss();
                    // }
                    if (dialogCus != null) {
                        dialogCus.dismiss();
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notDetailCus),
                            getActivity().getString(R.string.app_name)).show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private CustomerDTO getCustomerByCustId(String custId) {

            CustomerDTO customerDTO = new CustomerDTO();
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getCustomerByCustId");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getCustomerByCustId>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<custId>" + custId);
                rawData.append("</custId>");

                rawData.append("</input>");
                rawData.append("</ws:getCustomerByCustId>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getCustomerByCustId");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    customerDTO = parseOuput.getCustomerDTO();
                }

                return customerDTO;

            } catch (Exception e) {
                Log.e("getCustomerByCustId + exception", e.toString(), e);
            }
            return customerDTO;

        }
    }

    private void enableRepresentCus(CustomerDTO customerDTO) {
        btnkiemtra.setVisibility(View.GONE);
        btnedit.setVisibility(View.VISIBLE);
        if (!CommonActivity.isNullOrEmptyArray(customerDTO.getListCustIdentity())) {
            edit_sogiaytoDD.setText(customerDTO.getListCustIdentity().get(0).getIdNo());
            edit_ngaycapDD.setText(customerDTO.getListCustIdentity().get(0).getIdIssueDate());
            edtnoicap.setText(customerDTO.getListCustIdentity().get(0).getIdIssuePlace());
        }

        edit_sogiaytoDD.setEnabled(false);


        edit_ngaycapDD.setEnabled(false);


        edtnoicap.setEnabled(false);

        txtdcgtxmDD.setText(customerDTO.getAddress());
        txtdcgtxmDD.setEnabled(false);

        edit_tenKHDD.setText(customerDTO.getName());
        edit_tenKHDD.setEnabled(false);

        edit_ngaysinhdDD.setText(StringUtils.convertDate(customerDTO.getBirthDate()));
        edit_ngaysinhdDD.setEnabled(false);

        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            for (SexBeans item : arrSexBeans) {
                if (customerDTO.getSex().equals(item.getValues())) {
                    spinner_gioitinhDD.setSelection(arrSexBeans.indexOf(item));
                    spinner_gioitinhDD.setEnabled(false);
                    break;
                }
            }
        }

        if (!CommonActivity.isNullOrEmptyArray(spinNation)
                && !CommonActivity.isNullOrEmpty(customerDTO.getNationality())) {
            for (Spin spin : spinNation) {
                if (spin.getId().equals(customerDTO.getNationality())) {
                    spinner_quoctichpr.setSelection(spinNation.indexOf(spin));
                    break;
                }
            }
        }
        spinner_quoctichpr.setEnabled(false);
    }

    private void disenableRepresentCus() {

        btnkiemtra.setVisibility(View.VISIBLE);
        btnedit.setVisibility(View.GONE);
        if (accountDTOMain.getRefCustomer() != null) {
            accountDTOMain.setRefCustomer(null);
        }
        edit_sogiaytoDD.setText("");
        edit_sogiaytoDD.setEnabled(true);
        edit_ngaycapDD.setText(dateNowString);
        edit_ngaycapDD.setEnabled(true);
        edtnoicap.setText("");
        edtnoicap.setEnabled(true);

        txtdcgtxmDD.setText(getActivity().getString(R.string.sellect_address));
        txtdcgtxmDD.setEnabled(true);

        edit_tenKHDD.setText("");
        edit_tenKHDD.setEnabled(true);

        edit_ngaysinhdDD.setText(dateNowString);
        edit_ngaysinhdDD.setEnabled(true);

        spinner_gioitinhDD.setEnabled(true);

        // edit_quoctich.setText("");

        spinner_quoctichpr.setEnabled(true);
    }

    // Ham lay thong tin doi duong tu offerid va idNo
    private class GetProductSpecInfoPreAsyn extends AsyncTask<String, Void, ArrayList<SpecObject>> {
        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetProductSpecInfoPreAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<SpecObject> doInBackground(String... arg0) {
            return getLstObjectSpec(arg0[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<SpecObject> result) {
            progress.dismiss();

            // truong hop co giay to dac biet == danh sach cac doi duong
            if (errorCode.equalsIgnoreCase("0")) {
                if (result != null && !result.isEmpty()) {
                    arrSpecObjects = result;
                    for (SpecObject item : arrSpecObjects) {
                        if ("NEW_STU_15".equals(item.getCode())) {
                            arrSpecObjects.remove(item);
                            break;
                        }
                    }

                    // hien thi form thong tin dac biet len
                    lnTTGoiCuocDacBiet.setVisibility(View.VISIBLE);
                    // do du lieu vao spin doi tuong
                    // mCodeDoiTuong = arrSpecObjects.get(0).getCode();
                    doiTuongAdapter = new ObjDoiTuongAdapter(arrSpecObjects, activity);
                    spDoiTuong.setAdapter(doiTuongAdapter);

                    Log.d("checkIsSpeccheckIsSpec", checkIsSpec);

                    for (SpecObject specObject : arrSpecObjects) {
                        // if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)){
                        if ("SV_VN".equals(specObject.getCode())) {
                            spDoiTuong.setSelection(arrSpecObjects.indexOf(specObject));
                            spDoiTuong.setEnabled(true);

                            break;
                        }
                    }
                    doiTuongAdapter.notifyDataSetChanged();

                } else {
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

                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else if (errorCode.equalsIgnoreCase("1")) {

                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes1);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private ArrayList<SpecObject> getLstObjectSpec(String productCode) {
            ArrayList<SpecObject> arrayList = new ArrayList<SpecObject>();
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListObjectByProductCodeBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListObjectByProductCodeBccs2>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                if (!CommonActivity.isNullOrEmpty(subType) && "1".equals(subType)) {
                    rawData.append("<subType>" + "1");
                    rawData.append("</subType>");
                } else {
                    rawData.append("<subType>" + "");
                    rawData.append("</subType>");
                }

                rawData.append("<productCode>" + productCode);
                rawData.append("</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:getListObjectByProductCodeBccs2>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getListObjectByProductCodeBccs2");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // === parrse xml =====
                Serializer serializer = new Persister();
                SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

                if (saleOutput != null) {
                    errorCode = saleOutput.getErrorCode();
                    description = saleOutput.getDescription();

                    arrayList = saleOutput.getLstObject();

                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }

            return arrayList;
        }

    }

    protected void timKiemDonVi() {
        dialogLockBoxInfo();
    }

    private void dialogLockBoxInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // LocationService locationService = arrayListLocation.get(position);

        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.dialog_don_vi, null);

        builder.setView(dialogView);
        dialogDonVi = builder.create();

        final EditText edtMaDonVi = (EditText) dialogView.findViewById(R.id.edtMaDonVi);
        final EditText edtTenDonVi = (EditText) dialogView.findViewById(R.id.edtTenDonVi);
        Button btnTimKiem = (Button) dialogView.findViewById(R.id.btnTimKiem);

        lvListDonVi = (ListView) dialogView.findViewById(R.id.lvLockBoxInfo);

        btnTimKiem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String maDonVi = edtMaDonVi.getText().toString();
                String tenDonVi = edtTenDonVi.getText().toString();

                hideKeyBoard();
                if (maDonVi.equals("") && tenDonVi.equals("")) {
                    Toast.makeText(activity, getString(R.string.chua_nhap_ma_dvi), Toast.LENGTH_LONG).show();
                } else {

                    mAratListDonVi = new ArrayList<DoiTuongObj>();
                    if (donviadapter != null) {
                        donviadapter.notifyDataSetChanged();
                    }

                    getDonVi(maDonVi, tenDonVi);
                }
            }
        });
        lvListDonVi.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mAratListDonVi != null && mAratListDonVi.size() > 0) {
                    mCodeDonVi = mAratListDonVi.get(position).getCode();
                    tvDonVi.setText(mAratListDonVi.get(position).getCodeName());
                    imgDeleteDV.setVisibility(View.VISIBLE);
                    dialogDonVi.dismiss();
                }

            }
        });

        dialogDonVi.show();

    }

    protected void getDonVi(String maDonVi, String tenDonVi) {
        new GetLisDonVi(maDonVi, tenDonVi).execute();

    }

    public class GetLisDonVi extends AsyncTask<Void, Void, ArrayList<DoiTuongObj>> {

        private final String maDonVi;
        private final String tenDonVi;
        private final ProgressDialog dialog;

        public GetLisDonVi(String maDonVi, String tenDonVi) {
            this.maDonVi = maDonVi;
            this.tenDonVi = tenDonVi;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DoiTuongObj> doInBackground(Void... params) {

            return getDSDonvi(maDonVi, tenDonVi);
        }

        @Override
        protected void onPostExecute(ArrayList<DoiTuongObj> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(result);
            try {
                if (result == null || result.equals("")) {
                    mAratListDonVi = new ArrayList<DoiTuongObj>();
                    donviadapter = new DonViAdapter(getActivity(), mAratListDonVi);
                    lvListDonVi.setAdapter(donviadapter);
                    if (donviadapter != null) {
                        donviadapter.notifyDataSetChanged();
                    }

                    CommonActivity
                            .createAlertDialog(getActivity(), getResources().getString(R.string.ko_tim_thay_don_vi_nao),
                                    getResources().getString(R.string.app_name))
                            .show();
                } else {
                    mAratListDonVi.addAll(result);
                    donviadapter = new DonViAdapter(getActivity(), mAratListDonVi);
                    lvListDonVi.setAdapter(donviadapter);

                }
            } catch (Exception e) {
                // TODO: handle exception
                mAratListDonVi = new ArrayList<DoiTuongObj>();
                donviadapter = new DonViAdapter(getActivity(), mAratListDonVi);
                lvListDonVi.setAdapter(donviadapter);
                if (donviadapter != null) {
                    donviadapter.notifyDataSetChanged();
                }

                CommonActivity
                        .createAlertDialog(getActivity(), getResources().getString(R.string.ko_tim_thay_don_vi_nao),
                                getResources().getString(R.string.app_name))
                        .show();
            }

        }

        private ArrayList<DoiTuongObj> getDSDonvi(String depcode, String deptName) {
            ArrayList<DoiTuongObj> arrDoituong = new ArrayList<DoiTuongObj>();
            try {

                BhldDAL dal = new BhldDAL(getActivity());
                dal.open();
                arrDoituong = dal.getListDeptObjectBCCSFromCode(mCodeDoiTuong, depcode, deptName);
                dal.close();

                return arrDoituong;
            } catch (Exception e) {
                // TODO: handle exception
            }

            return null;
        }

    }

    public String requestSeviceDonVi(String maDonVi, String tenDonVi, String codeDoiTuong) {
        String reponse = null;
        String original = null;

        String xml = mBccsGateway.getXmlCustomer(createXMLDonVi(maDonVi, tenDonVi, codeDoiTuong),
                "mbccs_getListDepByObject");
        Log.e("TAG89", "xml Don Vi" + xml);
        try {
            reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL, activity, "mbccs_getListDepByObject");
            Log.e("TAG8", "reponse Don Vi" + reponse);
        } catch (NotFoundException e) {
            Log.e("Exception", e.toString(), e);
        } catch (Exception e) {

            Log.e("Exception", e.toString(), e);
        }
        if (reponse != null) {
            CommonOutput commonOutput;
            try {
                commonOutput = mBccsGateway.parseGWResponse(reponse);
                original = commonOutput.getOriginal();
            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }

        }
        return original;
    }

    private String createXMLDonVi(String maDonVi, String tenDonVi, String codeDoiTuong) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ws:getListDepByObject>");
        stringBuilder.append("<input>");
        stringBuilder.append("<token>" + Session.getToken() + "</token>");
        stringBuilder.append("<deptCode>" + maDonVi + "</deptCode>");
        stringBuilder.append("<deptName>" + tenDonVi + "</deptName>");
        stringBuilder.append("<objectCode>" + codeDoiTuong + "</objectCode>");
        stringBuilder.append("</input>");
        stringBuilder.append("</ws:getListDepByObject>");
        return stringBuilder.toString();
    }

    public ArrayList<DoiTuongObj> parseResultDonVi(String result) {

        ArrayList<DoiTuongObj> aratList = new ArrayList<DoiTuongObj>();
        if (result != null) {
            try {
                Log.e("TAG69 Don Vi ", result);
                XmlDomParse domParse = new XmlDomParse();
                Document document = domParse.getDomElement(result);

                NodeList nodeListErrorCode = document.getElementsByTagName("lstDepartment");

                for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
                    Node mNode = nodeListErrorCode.item(i);
                    Element element = (Element) mNode;
                    String id = domParse.getValue(element, "id");
                    String code = domParse.getValue(element, "code");
                    String codeName = domParse.getValue(element, "codeName");

                    int ID = Integer.parseInt(id);

                    // DoiTuongObj doiTuongObj = new DoiTuongObj(ID, codeName,
                    // code);
                    // aratList.add(doiTuongObj);
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }
        }
        return aratList;
    }

    private class GetLimitUsageAsyn extends AsyncTask<String, Void, String> {
        private XmlDomParse parse = new XmlDomParse();
        private Context mContext;
        private String errorCode;
        private String description;

        public GetLimitUsageAsyn(Context context) {
            this.mContext = context;

            limitUs = "";
        }

        @Override
        protected String doInBackground(String... arg0) {
            return getLimitUsage(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if ("0".equals(errorCode)) {
                limitUs = result;

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private String getLimitUsage(String productCode) {
            String limitUs = null;
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getLimitUsage");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLimitUsage>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<productCode>" + productCode + "</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:getLimitUsage>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_getLimitUsage");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);
                    limitUs = parse.getValue(e2, "limitPobas");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return limitUs;
        }

    }

    private class FindVerifiedOwnerByListIdNoAsyn extends AsyncTask<String, Void, ArrayList<SubscriberDTO>> {
        private Context mContext;
        private String errorCode;
        private String description;
        private List<CustIdentityDTO> custIdentityDTOs = new ArrayList<CustIdentityDTO>();
        private ProgressDialog progress;

        public FindVerifiedOwnerByListIdNoAsyn(Context context, List<CustIdentityDTO> mcustIdentityDTOs) {
            this.mContext = context;
            this.custIdentityDTOs = mcustIdentityDTOs;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<SubscriberDTO> doInBackground(String... arg0) {
            return findVerifiedOwnerByListIdNo();
        }

        @Override
        protected void onPostExecute(ArrayList<SubscriberDTO> result) {
            arrSubscriberDTO = new ArrayList<SubscriberDTO>();
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    lnthuebaochinhchu.setVisibility(View.VISIBLE);
                    arrSubscriberDTO.addAll(result);
                    showSelectIsdnOwner();
                } else {
                    // lnthuebaochinhchu.setVisibility(View.GONE);

                    if (checkSubAdapter != null) {
                        checkSubAdapter.notifyDataSetChanged();
                    }

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            mContext.getString(R.string.notmarkowner),
                            mContext.getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                lnthuebaochinhchu.setVisibility(View.GONE);
                if (checkSubAdapter != null) {
                    checkSubAdapter.notifyDataSetChanged();
                }
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private ArrayList<SubscriberDTO> findVerifiedOwnerByListIdNo() {
            ArrayList<SubscriberDTO> arrSub = new ArrayList<SubscriberDTO>();
            String original = "";
            FileInputStream in = null;
            try {
                BCCSGateway input = new BCCSGateway();
                // input.addValidateGateway("username", Constant.BCCSGW_USER);
                // input.addValidateGateway("password", Constant.BCCSGW_PASS);
                // input.addValidateGateway("wscode",
                // "mbccs_findVerifiedOwnerByListIdNo");
                StringBuilder rawData = new StringBuilder();
                rawData.append(
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

                rawData.append("<soapenv:Header/>");
                rawData.append("<soapenv:Body>");
                rawData.append("<ws:findVerifiedOwnerByListIdNo>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                if (custIdentityDTOs != null && custIdentityDTOs.size() > 0) {
                    for (CustIdentityDTO item : custIdentityDTOs) {
                        rawData.append("<lstCustIdentityDTO>");
                        rawData.append("<idNo>" + item.getIdNo() + "</idNo>");
                        rawData.append("<idType>" + item.getIdType() + "</idType>");
                        rawData.append("</lstCustIdentityDTO>");
                    }
                }
                rawData.append("</input>");
                rawData.append("</ws:findVerifiedOwnerByListIdNo>");
                rawData.append("</soapenv:Body>");
                rawData.append("</soapenv:Envelope>");

                String envelope = rawData.toString();
                Log.e("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);

                String fileName = input.sendRequestWriteToFile(envelope, Constant.WS_SUB_DATA, Constant.SUB_FILE_NAME);
                if (fileName != null) {

                    try {

                        File file = new File(fileName);
                        if (!file.mkdirs()) {
                            file.createNewFile();
                        }
                        in = new FileInputStream(file);
                        SubscriberDTOHanlder handler = (SubscriberDTOHanlder) CommonActivity
                                .parseXMLHandler(new SubscriberDTOHanlder(), new InputSource(in));
                        file.delete();
                        if (handler != null) {
                            if (handler.getItem().getToken() != null && !handler.getItem().getToken().isEmpty()) {
                                Session.setToken(handler.getItem().getToken());
                            }
                            if (handler.getItem().getErrorCode() != null) {
                                errorCode = handler.getItem().getErrorCode();
                            }
                            if (handler.getItem().getDescription() != null) {
                                description = handler.getItem().getDescription();
                            }
                            arrSub = handler.getLstData();
                        }

                    } catch (Exception e) {
                        Log.e("Exception", e.toString(), e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                Log.e("Exception", e.toString(), e);
                            }
                        }
                    }
                }

                // Log.i("LOG", "raw data" + rawData.toString());
                // String envelope =
                // input.buildInputGatewayWithRawData(rawData.toString());
                // Log.d("LOG", "Send evelop" + envelope);
                // Log.i("LOG", Constant.BCCS_GW_URL);
                // String response = input.sendRequest(envelope,
                // Constant.BCCS_GW_URL, activity,
                // "mbccs_findVerifiedOwnerByListIdNo");
                // Log.i("LOG", "Respone: " + response);
                // CommonOutput output = input.parseGWResponse(response);
                // original = output.getOriginal();
                // Log.i("LOG", "Responseeeeeeeeee Original " + original);
                //
                // // parser
                //
                // Serializer serializer = new Persister();
                // ParseOuput parseOuput = serializer.read(ParseOuput.class,
                // original);
                // if (parseOuput != null) {
                // errorCode = parseOuput.getErrorCode();
                // description = parseOuput.getDescription();
                // arrSub = parseOuput.getLstSubscriberDTO();
                // }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrSub;
        }

    }

    private class GetDataStockWsByReasonAsyn extends AsyncTask<String, Void, StockNumberDTO> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public GetDataStockWsByReasonAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected StockNumberDTO doInBackground(String... arg0) {
            return getDataStock(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(StockNumberDTO result) {
            super.onPostExecute(result);
            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {

                    if (!CommonActivity.isNullOrEmpty(result.getPrice())
                            && !CommonActivity.isNullOrEmpty(result.getPledgeTime())
                            && !CommonActivity.isNullOrEmpty(result.getPricePledgeAmount())
                            && !CommonActivity.isNullOrEmpty(result.getPriceVat())) {
                        showDialogViewDataStock(result, txtisdn.getText().toString().trim());
                    } else {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notviewcamket),
                                getActivity().getString(R.string.app_name)).show();
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notviewcamket),
                            getActivity().getString(R.string.app_name)).show();
                }

            } else {

                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private StockNumberDTO getDataStock(String stockTypeId, String isdn, String saleServiceCode) {
            String original = "";
            StockNumberDTO stockNumberDTO = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getDataStockWsByReason");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getDataStockWsByReason>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<stockTypeId>" + stockTypeId + "</stockTypeId>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("<saleServiceCode>" + saleServiceCode + "</saleServiceCode>");

                rawData.append("</input>");
                rawData.append("</ws:getDataStockWsByReason>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getDataStockWsByReason");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    stockNumberDTO = parseOuput.getStockNumberDTO();
                }

                return stockNumberDTO;

            } catch (Exception e) {
                Log.d("getDataStock", e.toString());
            }

            return stockNumberDTO;
        }

    }

    private void showDialogViewDataStock(StockNumberDTO stockNumberDTO, String isdn) {

        final Dialog dialogViewCommitment = new Dialog(activity);
        dialogViewCommitment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogViewCommitment.setContentView(R.layout.dialog_layout_commitment_bccs);

        TextView tvDialogTitle = (TextView) dialogViewCommitment.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(getActivity().getString(R.string.ttincamketso));
        TextView tvIsdn = (TextView) dialogViewCommitment.findViewById(R.id.tvIsdn);
        tvIsdn.setText(isdn);
        TextView tvmoney = (TextView) dialogViewCommitment.findViewById(R.id.tvmoney);
        tvmoney.setText(StringUtils.formatMoney(stockNumberDTO.getPrice()));

        TextView tvmoneycamket = (TextView) dialogViewCommitment.findViewById(R.id.tvmoneycamket);
        tvmoneycamket.setText(StringUtils.formatMoney(stockNumberDTO.getPricePledgeAmount()));

        // TextView tvmoneycamketungtruoc = (TextView)
        // dialogViewCommitment.findViewById(R.id.tvmoneycamketungtruoc);
        // tvmoneycamketungtruoc.setText(StringUtils.formatMoney(stockNumberDTO.getPricePriorPay()));

        TextView tvcamketthang = (TextView) dialogViewCommitment.findViewById(R.id.tvcamketthang);
        tvcamketthang.setText(stockNumberDTO.getPledgeTime());

        TextView tvpricevat = (TextView) dialogViewCommitment.findViewById(R.id.tvpricevat);
        tvpricevat.setText(stockNumberDTO.getPriceVat() + "%");
        dialogViewCommitment.findViewById(R.id.btnclose).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogViewCommitment.dismiss();

            }
        });

        dialogViewCommitment.show();

    }

    @Override
    public void onChangeChecked(SubscriberDTO subscriberDTO) {
        for (SubscriberDTO item : arrSubscriberDTO) {
            if (subscriberDTO.getSubId().equals(item.getSubId())) {
                item.setHasVerifiedOwner(!item.isHasVerifiedOwner());
                break;
            }
        }

    }

    private void showSelectIsdnOwner() {

        final Dialog dialogIsdn = new Dialog(activity);
        dialogIsdn.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogIsdn.setContentView(R.layout.dialog_search_isdn);
        lvsubparent = (ExpandableHeightListView) dialogIsdn.findViewById(R.id.lvsubparent);
        lvsubparent.setExpanded(true);

        checkSubAdapter = new CheckSubAdapter(getActivity(), arrSubscriberDTO, FragmentConnectionMobileNew.this);
        lvsubparent.setAdapter(checkSubAdapter);

        checkSubAdapter.notifyDataSetChanged();

        final EditText edtseach = (EditText) dialogIsdn.findViewById(R.id.edtseach);
        edtseach.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                String input = edtseach.getText().toString().toLowerCase(Locale.getDefault());

                checkSubAdapter = new CheckSubAdapter(getActivity(), arrSubscriberDTO,
                        FragmentConnectionMobileNew.this);
                checkSubAdapter.notifyDataSetChanged();
                if (checkSubAdapter != null) {
                    checkSubAdapter.SearchInput(input);
                    lvsubparent.setAdapter(checkSubAdapter);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        Button btnok = (Button) dialogIsdn.findViewById(R.id.btnok);
        btnok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogIsdn.cancel();
            }
        });

        dialogIsdn.show();

    }

    private class GetReasonCharacterAsyn extends AsyncTask<String, Void, ArrayList<String>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public GetReasonCharacterAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... arg0) {
            return getReasonCharater(arg0[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {

                    for (String item : result) {
                        checkCharRegtype = "";
                        if ("REGTYPE_IMIE".equals(item)) {
                            checkCharRegtype = item;
                            break;
                        }
                    }
                    for (String item : result) {
                        checkSpecRegtype = "";
                        if ("REG_SPECIAL".equals(item) && "M".equals(serviceType)) {
                            checkSpecRegtype = item;
                            GetProductSpecInfoPreAsyn getInfoPreAsyn = new GetProductSpecInfoPreAsyn(activity);
                            getInfoPreAsyn.execute(hthm);
                            break;
                        }
                    }

                } else {
                    checkSpecRegtype = "";
                    checkCharRegtype = "";
                    // CommonActivity.createAlertDialog(getActivity(),
                    // getActivity().getString(R.string.notviewcamket),
                    // getActivity().getString(R.string.app_name)).show();
                }


                GetPromotionByMap getPromotionByMap = new GetPromotionByMap(activity);
                getPromotionByMap.execute(serviceType, hthm, offerId, province, district, precinct, "" + avgCharge);
            } else {
                checkCharRegtype = "";
                checkSpecRegtype = "";
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private ArrayList<String> getReasonCharater(String reasonId) {
            String original = "";
            ArrayList<String> lstChar = new ArrayList<String>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getReasonCharacter");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonCharacter>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("</input>");
                rawData.append("</ws:getReasonCharacter>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getReasonCharacter");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstChar = parseOuput.getLstchar();
                }

                return lstChar;

            } catch (Exception e) {
                Log.d("getDataStock", e.toString());
            }

            return lstChar;
        }
    }

    private class CheckQuotaSubAsyn extends AsyncTask<String, Void, String> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public CheckQuotaSubAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return checkQuotaSub(arg0[0], arg0[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(telecomserviceId)) {
                    GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(activity, false);
                    getListPakageAsyn.execute(telecomserviceId, subType);
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }

                    // tra truoc
                    if ("2".equals(subType)) {

                        OnClickListener onclick = new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                if (!CommonActivity.isNullOrEmpty(telecomserviceId)) {
                                    GetListPakageAsyn getListPakageAsyn = new GetListPakageAsyn(activity, false);
                                    getListPakageAsyn.execute(telecomserviceId, subType);
                                }

                            }
                        };
                        if (description.contains("SALE2620")) {
                            description.replaceAll(getActivity().getString(R.string.checkreplace), "");
                        }

                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                getResources().getString(R.string.app_name), onclick);
                        dialog.show();

                    } else {
                        // tra sau
                        OnClickListener onclick = new OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                                if (!CommonActivity.isNullOrEmptyArray(arrTelecomServiceBeans)) {
                                    for (TelecomServiceBeans item : arrTelecomServiceBeans) {
                                        if (CommonActivity.isNullOrEmpty(item.getTelecomServiceId())) {
                                            spinner_serviceMobile.setSelection(arrTelecomServiceBeans.indexOf(item));
                                            break;
                                        }
                                    }
                                }

                                ActivityCreateNewRequestMobileNew.instance.tHost.setCurrentTab(0);

                            }
                        };
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                getResources().getString(R.string.app_name), onclick);
                        dialog.show();

                    }

                }
            }
        }

        private String checkQuotaSub(String telecomserviceID, String payType) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_checkQuotaSub");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:checkQuotaSub>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                // kh ca nhan

                if ("2".equals(subType)) {
                    // kh doanh nghiep tra truoc
                    if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType())) {
                        // 2. busPermitNo
                        // 3. repreIdNo
                        if (!CommonActivity.isNullOrEmptyArray(custIdentityDTO.getCustomer().getListCustIdentity())) {
                            for (CustIdentityDTO item : custIdentityDTO.getCustomer().getListCustIdentity()) {
                                if ("BUS".equals(item.getIdType())) {
                                    rawData.append("<busPermitNo>" + custIdentityDTO.getIdNo());
                                    rawData.append("</busPermitNo>");
                                    rawData.append("<repreIdNo>" + custIdentityDTO.getRepreCustomer().getIdNo());
                                    rawData.append("</repreIdNo>");
                                }
                            }
                        }
                    } else {
                        rawData.append("<idNo>" + custIdentityDTO.getIdNo());
                        rawData.append("</idNo>");
                    }

                } else {
                    rawData.append("<idNo>" + custIdentityDTO.getIdNo());
                    rawData.append("</idNo>");
                }

                // if
                // (custIdentityDTO.getCustomer().getListCustIdentity().size()
                // == 1) {
                // checkQuotaSubAsyn.execute(custIdentityDTO.getCustomer()
                // .getListCustIdentity().get(0).getIdNo(), telecomserviceId,
                // subType);
                // } else {
                // // khach hang doanh nghiep
                // for (CustIdentityDTO item : custIdentityDTO.getCustomer()
                // .getListCustIdentity()) {
                // if ("BUS".equals(item.getIdType()) ||
                // "TIN".equals(item.getIdType())) {
                // checkQuotaSubAsyn.execute(item.getIdNo(), telecomserviceId,
                // subType);
                // break;
                // }
                // }
                // }

                rawData.append("<telecomServiceId>" + telecomserviceID);
                rawData.append("</telecomServiceId>");
                rawData.append("<payType>" + payType);
                rawData.append("</payType>");

                rawData.append("</input>");
                rawData.append("</ws:checkQuotaSub>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_checkQuotaSub");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // === parrse xml =====
                Serializer serializer = new Persister();
                SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

                if (saleOutput != null) {
                    errorCode = saleOutput.getErrorCode();
                    description = saleOutput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("checkResource", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }
            return null;
        }
    }

    private void initNationly(Spinner spinquoctich) {
        try {
            BhldDAL dal = new BhldDAL(getActivity());
            dal.open();
            spinNation = dal.getNationaly();
            dal.close();
            Utils.setDataSpinner(getActivity(), spinNation, spinquoctich);

            if (!CommonActivity.isNullOrEmptyArray(spinNation)) {

                for (Spin spin : spinNation) {
                    if ("VN".equals(spin.getId())) {
                        spinquoctich.setSelection(spinNation.indexOf(spin));
                        break;
                    }
                }
            }

        } catch (Exception e) {
            Log.d("initNationly", e.toString());
        }

    }

    // check serial hop le hay khong
    private class CheckResourceAsyn extends AsyncTask<String, Void, String> {

        private Activity mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        private ProductOfferTypeDTO productOfferTypeDTO;
        private ProductOfferingDTO productOfferingDTO;
        private EditText edtserial;

        public CheckResourceAsyn(Activity context, ProductOfferTypeDTO mproductOfferTypeDTO,
                                 ProductOfferingDTO mproductOfferingDTO, EditText edtserial) {

            this.productOfferTypeDTO = mproductOfferTypeDTO;
            this.productOfferingDTO = mproductOfferingDTO;
            this.mContext = context;
            this.edtserial = edtserial;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return checkResource(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {

                Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.serialhople), getResources().getString(R.string.app_name));
                dialog.show();

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private String checkResource(String serial, String reason, String prodOfferId) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_checkResource");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:checkResource>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<productCode>" + productCode);
                rawData.append("</productCode>");
                rawData.append("<telecomServiceId>" + telecomserviceId);
                rawData.append("</telecomServiceId>");
                rawData.append("<newSerial>" + serial);
                rawData.append("</newSerial>");

                rawData.append("<reasonIdChangeSim>" + reason);
                rawData.append("</reasonIdChangeSim>");
                if (!CommonActivity.isNullOrEmpty(prodOfferId)) {
                    rawData.append("<prodOfferId>" + prodOfferId);
                    rawData.append("</prodOfferId>");
                }

                //
                // ReasonDTO reasonChangeSim = (ReasonDTO)
                // spinner_lydodoisim.getSelectedItem();
                // if (!CommonActivity.isNullOrEmpty(reasonChangeSim)
                // &&
                // !CommonActivity.isNullOrEmpty(reasonChangeSim.getReasonId()))
                // {
                // rawData.append("<reasonIdChangeSim>" +
                // reasonChangeSim.getReasonId());
                // rawData.append("</reasonIdChangeSim>");
                // }

                rawData.append("</input>");
                rawData.append("</ws:checkResource>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_checkResource");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // === parrse xml =====
                Serializer serializer = new Persister();
                SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

                if (saleOutput != null) {
                    errorCode = saleOutput.getErrorCode();
                    description = saleOutput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("checkResource", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return null;
        }
    }

    private class FindFeeByReasonTeleIdAsyn extends AsyncTask<String, Void, ArrayList<ProductPackageFeeDTO>> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public FindFeeByReasonTeleIdAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductPackageFeeDTO> doInBackground(String... arg0) {
            return getProductSpec(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductPackageFeeDTO> result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    showDialogGetFee(result);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notthogntinvi),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {
                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ArrayList<ProductPackageFeeDTO> getProductSpec(String telecomserviceId, String reasonId,
                                                               String productCode) {
            String original = "";
            ArrayList<ProductPackageFeeDTO> arrayList = new ArrayList<ProductPackageFeeDTO>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_findFeeByReasonTeleId");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:findFeeByReasonTeleId>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<telecomServiceId>" + telecomserviceId + "</telecomServiceId>");
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("<productCode>" + productCode + "</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:findFeeByReasonTeleId>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_sendOTPUtil");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    arrayList = parseOuput.getLstProductPackageFeeDTO();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("getProductSpec", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return arrayList;
        }
    }

    private void showDialogGetFee(ArrayList<ProductPackageFeeDTO> arrProductPakage) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_changesim_viewfee);
        dialog.setCancelable(false);
        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        GetFeeBCCSAdapter getFeeBCCSAdapter = new GetFeeBCCSAdapter(arrProductPakage, getActivity());
        listView.setAdapter(getFeeBCCSAdapter);

        dialog.findViewById(R.id.btncancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private class GetRequiredLimitUsageForCommitmentAsyn extends AsyncTask<String, Void, String> {
        private XmlDomParse parse = new XmlDomParse();
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private boolean ischeckCommit;
        public GetRequiredLimitUsageForCommitmentAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            limitUsCommiton = "";
        }
        public GetRequiredLimitUsageForCommitmentAsyn(Context context , boolean ischeck) {
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            ischeckCommit = ischeck;
            limitUsCommiton = "";
        }
        @Override
        protected String doInBackground(String... arg0) {
            return getLimitUsageCommit(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            this.progress.dismiss();
            if ("0".equals(errorCode)) {
                limitUsCommiton = result;
                if(ischeckCommit){
                    String numberSize = "";
                    if (ActivityCreateNewRequestMobileNew.instance != null
                            && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        if (Constant.CHANGE_PRE_TO_POS
                                .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                            if(ActivityCreateNewRequestMobileNew.instance.subscriberDTO != null
                                    && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getCustomerDTOInput())
                                    && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getCustomerDTOInput().getCustId())){
                                // khach hang moi
                                if(CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())){
                                    numberSize = "2";
                                }else{
                                    // khach hang cu nhung khac custId
                                    if(!custIdentityDTO.getCustomer().getCustId().equals(ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getCustomerDTOInput().getCustId())){
                                        numberSize = "2";
                                    }
                                }
                            }else{
                                numberSize = "2";
                            }
                        }
                    }
                    fragment = new ProfileInfoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profileBO", profileBO);
                    bundle.putString("xmlSub", subXml(true));
                    bundle.putString("xmlCus", xmlCusPos());
                    bundle.putString("numberSig",numberSize);
                    bundle.putString("groupType",custIdentityDTO.getGroupType());
                    fragment.setOnFinishDSFListener(onFinishDSFListener);
                    fragment.setArguments(bundle);
                    fragment.show(getFragmentManager(), "show profile");
                }else{
                    subConnectPos();
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private String getLimitUsageCommit(String isdn, String telecomserviceId, String reasonId, String productCode,
                                           String promotionCode) {
            String limitUsCommiton = null;
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getRequiredLimitUsageForCommitment");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getRequiredLimitUsageForCommitment>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("<productCode>" + productCode + "</productCode>");
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("<promotionCode>" + promotionCode + "</promotionCode>");
                rawData.append("<telecomServiceId>" + telecomserviceId + "</telecomServiceId>");

                ArrayList<ProductOfferingDTO> arrApStockModelBeanBOs1 = new ArrayList<ProductOfferingDTO>();

                arrApStockModelBeanBOs1.addAll(mapSubStockModelRelReq.values());
                if (arrApStockModelBeanBOs1.size() > 0) {
                    for (ProductOfferingDTO item : arrApStockModelBeanBOs1) {
                        rawData.append("<listSubGoodsDTO>");
                        rawData.append("<stockModelId>" + item.getProductOfferingId() + "");
                        rawData.append("</stockModelId>");
                        rawData.append("<serial>" + "" + item.getSerial());
                        rawData.append("</serial>");
                        rawData.append("<stockTypeId>" + "" + item.getProductOfferTypeId());
                        rawData.append("</stockTypeId>");

                        rawData.append("<stockModelName>" + "" + item.getName());
                        rawData.append("</stockModelName>");
                        if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity
                                .isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                            if (Constant.CHANGE_PRE_TO_POS
                                    .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                                rawData.append("<actionCode>" + "220");
                                rawData.append("</actionCode>");
                            } else {
                                rawData.append("<actionCode>" + "221");
                                rawData.append("</actionCode>");
                            }
                        } else {
                            rawData.append("<actionCode>" + "00");
                            rawData.append("</actionCode>");
                        }

                        rawData.append("</listSubGoodsDTO>");
                    }
                } else {
                    rawData.append("<listSubGoodsDTO>");
                    rawData.append("</listSubGoodsDTO>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getRequiredLimitUsageForCommitment>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
                        "mbccs_getRequiredLimitUsageForCommitment");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);
                    limitUsCommiton = parse.getValue(e2, "limitPobas");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return limitUsCommiton;
        }

    }

    // lay danh sach ly do chuyen doi thue bao
    private class GetReasonFullPM extends AsyncTask<String, Void, ArrayList<HTHMBeans>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private String actionCode = "";

        public GetReasonFullPM(Context context, String actioncode) {
            this.actionCode = actioncode;
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
            return getListReasonDTO();
        }

        @Override
        protected void onPostExecute(ArrayList<HTHMBeans> result) {
            super.onPostExecute(result);
            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (arrHthmBeans != null && !arrHthmBeans.isEmpty()) {
                    arrHthmBeans = new ArrayList<HTHMBeans>();
                }
                if (result != null && result.size() > 0) {
                    arrHthmBeans = result;
                    Intent intent = new Intent(getActivity(), SearchCodeHthmActivity.class);
                    intent.putExtra("arrHthmBeans", result);
                    startActivityForResult(intent, 1001);
                } else {
                    edt_hthm.setText(getString(R.string.chonhthm));
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.noththm), getResources().getString(R.string.app_name));
                    dialog.show();
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ArrayList<HTHMBeans> getListReasonDTO() {
            String original = null;
            List<ReasonDTO> lstReasonDTO = new ArrayList<ReasonDTO>();
            ArrayList<HTHMBeans> lstHTHMBeans = new ArrayList<HTHMBeans>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getReasonFullPM");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonFullPM>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<payType>" + subType + "</payType>");
                rawData.append("<offerId>" + offerId + "</offerId>");

                // 220 Chuyen doi tra truoc sang tra sau
                // 221 Chuyen doi TS sang TT

                rawData.append("<actionCode>" + actionCode + "</actionCode>");
                rawData.append("<serviceType>" + serviceType + "</serviceType>");

                // thay doi thong tin khach hang
                // String cusType = "";
                // if (subscriberDTO.isMarkNotOwner()) {
                // if (!CommonActivity.isNullOrEmpty(customerDTO)
                // && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                // cusType = customerDTO.getCustType();
                // } else {
                // cusType = custTypeDTO.getCustType();
                // }
                // } else {
                // cusType = subscriberDTO.getCustomerDTOInput().getCustType();
                // }
                if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())) {
                    rawData.append("<cusType>" + custIdentityDTO.getCustomer().getCustType() + "</cusType>");
                }

                rawData.append("<subType>" + subTypeMobile + "</subType>");

                rawData.append("</input>");
                rawData.append("</ws:getReasonFullPM>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getReasonFullPM");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstReasonDTO = parseOuput.getLstReasonDTO();

                    if (!CommonActivity.isNullOrEmpty(lstReasonDTO)) {
                        for (ReasonDTO item : lstReasonDTO) {

                            HTHMBeans hthmBeans = new HTHMBeans();
                            hthmBeans.setCode(item.getReasonCode());
                            hthmBeans.setDescription(item.getDescription());
                            hthmBeans.setReasonId(item.getReasonId());
                            hthmBeans.setName(item.getName());
                            hthmBeans.setCodeName(item.getCode() + "-" + item.getName());
                            lstHTHMBeans.add(hthmBeans);
                        }

                    }

                }

                return lstHTHMBeans;
            } catch (Exception e) {
                Log.d("getListReasonDTO", e.toString());
            }

            return null;
        }
    }

    private void reloadDataMobile() {
        edt_hthm.setText(getString(R.string.chonhthm));

        hthm = "";
        reasonId = "";

        if (arrStockTypeBeans != null && arrStockTypeBeans.size() > 0 && adapProductAdapter != null) {
            arrStockTypeBeans = new ArrayList<ProductOfferTypeDTO>();
            adapProductAdapter = new ThongTinHHAdapterBCCS(arrStockTypeBeans, getActivity(),
                    FragmentConnectionMobileNew.this);
            lvproduct.setAdapter(adapProductAdapter);
            adapProductAdapter.notifyDataSetChanged();
        }
        if (mapSubStockModelRelReq != null && mapSubStockModelRelReq.size() > 0) {
            mapSubStockModelRelReq.clear();
        }
        maKM = "";
        prepaidCode = "-1";
        prepaidId = "";
        if (arrPromotionTypeBeans != null && arrPromotionTypeBeans.size() > 0) {
            arrPromotionTypeBeans.clear();
            initInitListPromotionNotData();
        }
        spDoiTuong.setEnabled(true);
        mCodeDoiTuong = "";
        mCodeDonVi = "";
        tvDonVi.setText("");
        lnTTGoiCuocDacBiet.setVisibility(View.GONE);
        if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
            arrSpecObjects.clear();
        }
        if (doiTuongAdapter != null) {
            doiTuongAdapter.notifyDataSetChanged();
        }
        if (arrPakageChargeBeans != null && arrPakageChargeBeans.size() > 0) {
            arrPakageChargeBeans.clear();
            txtpakage.setText(Html.fromHtml("<u>" + getString(R.string.chonpakage_mobile) + "</u>"));
            productCode = "";
            offerId = "";
        }
        initListSubTypeNotData();
    }

    public class UpdateApkAsyn extends AsyncTask<String, Void, String> {

        String urlinstall = "";

        ProgressDialog progress;
        private Context context = null;

        public UpdateApkAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font

            this.progress.setMessage(context.getResources().getString(
                    R.string.processingdl));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return UpdateVersion(Session.getToken());
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equalsIgnoreCase(Constant.SUCCESS_CODE)) {
                progress.dismiss();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(urlinstall)),
//                        "application/vnd.android.package-archive");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(urlinstall)),
                            "application/vnd.android.package-archive");
//			intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(urlinstall)),
//					"application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Log.d(Constant.TAG, "fileApk path = " + urlinstall);
                    File fileApk = new File(urlinstall);
                    Uri apkUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", fileApk);
                    Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent.setDataAndType(apkUri,
                            "application/vnd.android.package-archive");;
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            } else {
                progress.dismiss();
                Dialog dialog = CommonActivity
                        .createAlertDialog((Activity) context, context
                                .getResources()
                                .getString(R.string.downloadfail), context
                                .getResources().getString(R.string.app_name));
                dialog.show();

            }
        }

        public String UpdateVersion(String token) {
            String result = "";
            String url = Constant.PATH_BARCODE + token;
            try {
                URL urlcontrol = new URL(url);
                Log.e("URL getVersion:", url);

                Log.i("Bo nho con trong", Double.toString(SdCardHelper
                        .getAvailableInternalMemorySize()));

                double availAbleMemory = SdCardHelper
                        .getAvailableInternalMemorySize();

                if (availAbleMemory > 50) {

                    InputStream input = new BufferedInputStream(
                            urlcontrol.openStream());
                    OutputStream output;

                    File file = new File(Constant.MBCCS_TEMP_FOLDER);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    output = new FileOutputStream(Constant.MBCCS_TEMP_FOLDER
                            + "barcode.apk");
                    urlinstall = Constant.MBCCS_TEMP_FOLDER + "barcode.apk";
                    Log.d("urlinstall", urlinstall);
                    byte data[] = new byte[1024];
                    int count = 0;

                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();

                    Log.e("FilePath", urlinstall);

                    Log.e("UPDATE VERSION", "End Download >>>>>>>>>>>>>>>> ");
                    result = Constant.SUCCESS_CODE;
                } else {
                    result = Constant.ERROR_CODE;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    // thientv7 them thong tin lien lac bo sung
    private class GetInfoPlus extends AsyncTask<String, Void, List<ProductSpecCharUseDTO>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private String actionCode = "";

        public GetInfoPlus(Context context, String actioncode) {
            this.actionCode = actioncode;
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected List<ProductSpecCharUseDTO> doInBackground(String... arg0) {
            return getListInfoPlus();
        }

        @Override
        protected void onPostExecute(List<ProductSpecCharUseDTO> result) {
            super.onPostExecute(result);
            progress.dismiss();

            if ("0".equals(errorCode)) {
                addViewWithDataFromProduct();

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private List<ProductSpecCharUseDTO> getListInfoPlus() {
            String original = null;
            lstProductSpecCharUseDTOs = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getConfigureInfoCusColectorByCode");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getConfigureInfoCusColectorByCode>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<code>HOPDONG_BOSUNG</code>");
                rawData.append("</input>");
                rawData.append("</ws:getConfigureInfoCusColectorByCode>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getConfigureInfoCusColectorByCode");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();

//                    Gson g = new Gson();
//                    Log.d("DATA", "parseOuput json: "+g.toJson(parseOuput));
                    if (!CommonActivity.isNullOrEmpty(parseOuput.getProductSpecificationDTO()) && !CommonActivity.isNullOrEmptyArray(parseOuput.getProductSpecificationDTO().getLstProductSpecCharUseDTO())) {

//                        lstProductSpecCharDTOs = parseOuput.getProductSpecificationDTO().getLstProductSpecCharUseDTO().get(0).getListProductSpecCharDTOs();
                        lstProductSpecCharUseDTOs = parseOuput.getProductSpecificationDTO().getLstProductSpecCharUseDTO();

                        Gson g = new Gson();
                        Log.d("DATA", "lstProductSpecCharUseDTOs json: " + g.toJson(lstProductSpecCharUseDTOs));
                    }



                    /*if (!CommonActivity.isNullOrEmpty(lstProductSpecCharDTOs)) {

                        for (int i = 0; i < lstProductSpecCharDTOs.size(); i++) {
                            ProductSpecCharMappingDTO productSpecCharMappingDTO = new ProductSpecCharMappingDTO();



                        }

                    }*/

                }

                return lstProductSpecCharUseDTOs;
            } catch (Exception e) {
                Log.d("getListReasonDTO", e.toString());
            }

            return null;
        }
    }


    private List<CustomEditText> lstEditTexts = new ArrayList<>();

    private void addViewWithDataFromProduct() {


        for (int i = 0; i < lstProductSpecCharUseDTOs.size(); i++) {


            ProductSpecCharUseDTO ps = lstProductSpecCharUseDTOs.get(i);

            ProductSpecCharDTO productSpecCharValueDTO = ps.getListProductSpecCharDTOs();

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            TextView titleView = new TextView(getActivity());
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.1f);
            titleView.setLayoutParams(lparams);


            String name = productSpecCharValueDTO.getName();
            String contactType = productSpecCharValueDTO.getCode();
            String required = "";
            try {
                if (!CommonActivity.isNullOrEmpty(productSpecCharValueDTO.getProductSpecCharValueDTOList().get(0).getIsDefault())) {

                    required = productSpecCharValueDTO.getProductSpecCharValueDTOList().get(0).getIsDefault();
                    if ("1".equals(required)) {
                        titleView.setText(Html.fromHtml("" + name + "<font color=\"red\">*</font>"));
                    } else if ("0".equals(required)) {
                        titleView.setText("" + name);
                    }


                } else {
                    titleView.setText("" + name);
                }
            } catch (NullPointerException e) {
                titleView.setText("" + name);
            }


            Log.d("DATA", "NAME: " + name);


            layout.addView(titleView);


            final CustomEditText editValue = new CustomEditText(getActivity());
            LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.9f);
            editValue.setHint("");
            editValue.setLayoutParams(lparams1);
            editValue.setId(i);
            editValue.setTag(i);


            editValue.setTitleName(name);

            editValue.setMaxCardinality(productSpecCharValueDTO.getMaxCardinality());
            editValue.setMinCardinality(productSpecCharValueDTO.getMinCardinality());

            Log.d("DATA", "setMaxCardinality: " + productSpecCharValueDTO.getMaxCardinality());
            Log.d("DATA", "setMinCardinality: " + productSpecCharValueDTO.getMinCardinality());

//            editValue.setMaxLength(productSpecCharValueDTO.getMaxCardinality());
            int valueType = productSpecCharValueDTO.getValueType();
            if (valueType == 1) {
                editValue.setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (valueType == 2) {
                editValue.setInputType(InputType.TYPE_CLASS_NUMBER);
            }


            if ("1".equals(required)) {
                editValue.setRequired(true);
            } else {
                editValue.setRequired(false);
            }


            // set gia tri mac dinh da nhap
            for (int j = 0; j < lstAddInfos.size(); j++) {
                AddInfo addInfo = lstAddInfos.get(j);
                if (contactType.equals(addInfo.getContactType())) {
                    editValue.setText(addInfo.getValue());
                }
            }


            lstEditTexts.add(editValue);
//            editValue.setText(""+ps.getName());
            layout.addView(editValue);


            main.addView(layout);
        }
    }

    private void addViewDataWithOldContract(String addInfos) {

//        List<AddInfo> lstAddInfos = new ArrayList<>();
        if (!CommonActivity.isNullOrEmpty(addInfos)) {
            try {
                JSONArray ja = new JSONArray(addInfos);
                lstAddInfoOlds.clear();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    AddInfo addInfo = new AddInfo();

                    String contactType = jo.has("contactType") ? jo.getString("contactType") : "";
                    String contactName = jo.has("contactName") ? jo.getString("contactName") : "";
                    String value = jo.has("value") ? jo.getString("value") : "";

                    int minLength = jo.has("minLength") ? jo.getInt("minLength") : 0;
                    int maxLength = jo.has("maxLength") ? jo.getInt("maxLength") : 0;
                    String valueType = jo.has("valueType") ? jo.getString("valueType") : "";
                    boolean required = jo.has("required") ? jo.getBoolean("required") : false;

                    addInfo.setContactType(contactType);
                    addInfo.setContactName(contactName);
                    addInfo.setValue(value);


                    //add view
                    LinearLayout layout = new LinearLayout(getActivity());
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    TextView titleView = new TextView(getActivity());
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.1f);
                    titleView.setLayoutParams(lparams);

                    Log.d("DATA", "NAME: " + contactName);

                    if (required) {
                        titleView.setText(Html.fromHtml("" + contactName + "<font color=\"red\">*</font>"));
                    } else {
                        titleView.setText("" + contactName);
                    }

                    layout.addView(titleView);


                    CustomEditText editValue = new CustomEditText(getActivity());
                    LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.9f);
                    editValue.setHint("");
                    editValue.setLayoutParams(lparams1);
                    editValue.setId(i);
                    editValue.setTag(i);

                    editValue.setTitleName(contactName);

                    // set type editext
                    if ("1".equals(valueType)) {
                        editValue.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if ("2".equals(valueType)) {
                        editValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    // set min/max length
//                editValue.setMaxLength(maxLength);

                    // set required
                    editValue.setRequired(false);
                    editValue.setText("" + value);

                    lstEditTexts.add(editValue);

                    layout.addView(editValue);


                    main.addView(layout);
                    lstAddInfoOlds.add(addInfo);

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class OnPostDoUHaveMorethan implements OnPostExecuteListener<ParseOuput> {
        @Override
        public void onPostExecute(ParseOuput result, String errorCode, String description) {
            isMoreThan = false;
            descriptionNotice = "";
            if (result != null) {
                isMoreThan = result.isMoreThan();
                descriptionNotice = description;
//                if(isMoreThan){
//                    CommonActivity.createAlertDialog(getActivity(),description,getActivity().getString(R.string.app_name)).show();
//                }
            }
        }
    }

    private boolean validateProfileContract() {
        List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
                mapListRecordPrepaid.values());
        for (int j = 0; j < arrayOfArrayList.size(); j++) {
            ArrayList<RecordPrepaid> listRecordPrepaid = arrayOfArrayList.get(j);
            View rowView = lvUploadImage.getChildAt(j);
            ViewHolder h = (ViewHolder) rowView.getTag();
            if (h != null) {
                int indexSelection = h.spUploadImage.getSelectedItemPosition();
                RecordPrepaid recordPrepaid = listRecordPrepaid.get(indexSelection);
                if ("HDMBTT".equals(recordPrepaid.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    // lay ra danh sach cau hinh so giay to
    private class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {

        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {

            if (lnngayhethandd != null) {
                lnngayhethandd.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrMapusage = result;
                for (Spin spin : arrMapusage) {
                    if (!CommonActivity.isNullOrEmpty(typePaperDialogPR) && CommonActivity.checkMapUsage(typePaperDialogPR, spin)) {
                        if (lnngayhethandd != null) {
                            lnngayhethandd.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            } else {
                arrMapusage = new ArrayList<>();
            }
        }
    }


    public class SaveImageAsyn extends AsyncTask<String, Void, String> {

        private final ProgressDialog progress;
        private Context context = null;
        private String pathImage = "";
        private int imageId;

        public SaveImageAsyn(Context context, int imageId
        ) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            this.progress.setCancelable(false);
            this.imageId = imageId;
            // check font
            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return saveFile(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (Constant.SUCCESS_CODE.equals(result) && !CommonActivity.isNullOrEmpty(pathImage)) {
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
                    Picasso.with(activity).load(new File(pathImage)).centerCrop().resize(100, 100)
                            .into(holder.ibUploadImage);
                    int position = holder.spUploadImage.getSelectedItemPosition();
                    if (position < 0) {
                        position = 0;
                    }
                    ArrayList<RecordPrepaid> recordPrepaids = mapListRecordPrepaid.get(String.valueOf(imageId));

                    if (recordPrepaids != null) {
                        RecordPrepaid recordPrepaid = recordPrepaids.get(position);
                        String spinnerCode = recordPrepaid.getCode();
                        ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
                        File uriFile = new File(pathImage);
                        String fileNameServer = spinnerCode + "-" + 1 + ".jpg";
                        FileObj obj = new FileObj(spinnerCode, uriFile, pathImage, fileNameServer);
                        recordPrepaid.getImageBO().setDownload(true);
                        fileObjs.add(obj);

                        hashmapFileObj.put(String.valueOf(imageId), fileObjs);
                        ImagePreviewActivity.pickImage(activity, hashmapFileObj, imageId);
                    } else {
                        Log.d(Constant.TAG,
                                "FragmentConnectionMobile onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
                                        + mapListRecordPrepaid.size() + " " + mapListRecordPrepaid);
                    }
                } else {
                    Log.d(Constant.TAG, "FragmentConnectionMobile onActivityResult() uris NULL");
                }
            } else {

                ImagePreviewActivity.pickImage(activity, hashmapFileObj, imageId);
            }
        }

        private String saveFile(String url) {
            String result = "";
            try {
                URL urlcontrol = new URL(url);
                Log.e("URL saveFile:", url);

                InputStream input = new BufferedInputStream(
                        urlcontrol.openStream());
                OutputStream output;

                File file = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "MBBCSCameraApp");
                if (!file.exists()) {
                    file.mkdirs();
                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
                pathImage = file.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg";
                Log.d("pathImage", pathImage);
                output = new FileOutputStream(pathImage);

                byte data[] = new byte[1024];
                int count = 0;

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                Log.e("FilePath", pathImage);

                Log.e("Save file", "End Download >>>>>>>>>>>>>>>> ");
                result = Constant.SUCCESS_CODE;
            } catch (Exception e) {
                try {
                    SaveLog saveLog = new SaveLog(context,
                            Constant.SYSTEM_NAME, Session.userName,
                            "upgradeversion_exception", CommonActivity
                            .findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context)
                                    .getY());
                    saveLog.saveLogException(e, new Date(), new Date(), Session.userName + "_"
                            + CommonActivity.getDeviceId(context) + "_"
                            + System.currentTimeMillis());
                } catch (Exception ex) {

                }
            }
            return result;
        }

    }
    private void setLoaithuebao(SubTypeBeans subTypeBeans) {
        String s = getResources().getString(R.string.chonloaithuebao);
        if (isFirst && subTypeBeans.getName().equals(mLoaithuebao)) { // check duplicate
            return;
        }
        System.out.println("12345 setLoaithuebao onItemSelected " + subTypeBeans.getName());
        removeMapkey(AutoConst.LOAITB);
        spinner_loaithuebao.setText(subTypeBeans.getName());

        mLoaithuebao = subTypeBeans.getName();

        if (!s.equals(subTypeBeans.getName())) {
            mMapTemplate.put(AutoConst.LOAITB, subTypeBeans.getName());

            AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_CATEGORY_TB, subTypeBeans.getName());
        }
        String def = this.getResources().getString(R.string.chonloaithuebao);
        if (!def.equals(subTypeBeans.getName())) {
            subTypeMobile = subTypeBeans.getSubType();
            if (arrHthmBeans != null && arrHthmBeans.size() > 0) {
                arrHthmBeans = new ArrayList<HTHMBeans>();
            }

            // chuyen doi tra truoc sang tra sau thi goi them ham lay ly
            // do chuyen doi
            if (ActivityCreateNewRequestMobileNew.instance != null
                    && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)
                    && Constant.CHANGE_PRE_TO_POS
                    .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {

                GetReasonFullPM getReasonFullPMChangeSim = new GetReasonFullPM(getActivity(), "220");
                getReasonFullPMChangeSim.execute();
            } else {
                GetHTHMAsyn getHTHMAsyn = new GetHTHMAsyn(getActivity());
                getHTHMAsyn.execute(offerId, serviceType, province, district, precinct,
                        custIdentityDTO.getCustomer().getCustType(), subTypeMobile, subType);
            }
        } else {
            subTypeMobile = "";
        }
    }
//    private boolean checkValidateImage(HashMap<String,ArrayList<FileObj>> hashmapFileObj){
//        if(CommonActivity.isNullOrEmpty(hashmapFileObj)){
//            return false;
//        }
//
//        return false;
//    }
	private long lastTouchDown;
    private static int CLICK_ACTION_THRESHHOLD = 200;
    private boolean isFirst = true;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchDown = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                    Log.w("App", "12345 You clicked!");
                    isFirst = false;
                }
                break;
        }
        return false;
    }

    private void removeMapkey(String key) {
        if (!isFirst) {
            mMapTemplate.remove(key);
        }
    }

    private ProfileBO initProfileBO() {
        horizontalScrollView.setVisibility(View.GONE);
        String actionCode = "00";
        if (ActivityCreateNewRequestMobileNew.instance != null
                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)){
            if (Constant.CHANGE_PRE_TO_POS
                    .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                actionCode = "220";
            } else {
                actionCode = "221";
            }
        }
        profileBO.setSigImageFullPath(null);
        if(!CommonActivity.isNullOrEmpty(profileBO.getLstActionCode())){
            profileBO.getLstActionCode().clear();
        }
        profileBO.getLstActionCode().add(actionCode);
        if(!CommonActivity.isNullOrEmpty(profileBO.getLstReasonId())){
            profileBO.getLstReasonId().clear();
        }
        profileBO.getLstReasonId().add(reasonId);
        profileBO.setParValue(Constant.POSTPAID.equals(subType)
                ? subType : productCode);
        profileBO.setPayType(subType);
        if(custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())){
            if(!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustTypeDTO())){
                profileBO.setCustType(custIdentityDTO.getCustomer().getCustTypeDTO().getCustType());
            }else{
                profileBO.setCustType(custIdentityDTO.getCustomer().getCustType());
            }
        }
        profileBO.setServiceType("M");
        profileBO.setIdNo(custIdentityDTO.getIdNo());
        return profileBO;
    }

    OnFinishDSFListener onFinishDSFListener = new OnFinishDSFListener() {
        @Override
        public void onFinish(ProfileBO output) {
            profileBO = output;
            if ((profileBO != null
                    && profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty())
                    || (profileBO != null && profileBO.getProfileRecords() != null && profileBO.getProfileRecords().size() > 0)) {
                thumbnails.removeAllViews();

                hashmapFileObj = profileBO.getHashmapFileObj();



                horizontalScrollView.setVisibility(View.VISIBLE);

                ArrayList<String> lstData = new ArrayList<>();
                for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                    for (FileObj fileObj : entry.getValue()) {
                        Log.d("onFinishDSFListener", fileObj.getFullPath());
                        lstData.add(fileObj.getFullPath());
                    }
                }
                if (profileBO.getSigImageFullPath() != null) {
                    lstData.add(profileBO.getSigImageFullPath());
                }

                if (profileBO.getSigImageFullPathTwo() != null) {
                    lstData.add(profileBO.getSigImageFullPathTwo());
                }

                ImageUtils.loadImageHorizontal(getActivity(), inflater, thumbnails, lstData, new com.viettel.bss.viettelpos.v4.listener.OnClickListener() {
                    @Override
                    public void onClick(Object... obj) {
                        View thumView = (View) obj[0];
                        String path = String.valueOf(obj[1]);

                        zoomImageFromThumb(thumView, path);
                    }
                });
            } else {
                horizontalScrollView.setVisibility(View.GONE);
            }
        }
    };
    public void zoomImageFromThumb(final View thumbView, String path) {
        final int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Bitmap myBitmap = BitmapFactory.decodeFile(path);

        // Load the high-resolution "zoomed-in" image.
        expandedImageView.setImageBitmap(myBitmap);
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        frlMain.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
    public String methodNameMain = "";
    private void submitProfile(){
        String nameCusCreateNew= custIdentityDTO.getCustomer().getName();

        String type = "";

        if (ActivityCreateNewRequestMobileNew.instance != null
                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)){
            if (Constant.CHANGE_PRE_TO_POS
                    .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                type = Constant.ORD_TYPE_CHANGE_TO_POSPAID;
                methodNameMain = "changePreToPos";
            } else {
                methodNameMain = "changePosToPre";
                type = Constant.CHANGE_TO_PREPAID;
            }
        }else{
            if("2".equals(subType)){
                methodNameMain = "connectMobilePrePaid";
                type = Constant.ORD_TYPE_CONNECT_PREPAID;
            }else{
                methodNameMain = "connectMobilePostPaid";
                type = Constant.ORD_TYPE_CONNECT_POSPAID;
            }
        }
        if(!CommonActivity.isNullOrEmpty(profileBO) && !CommonActivity.isNullOrEmpty(profileBO.getProfileRecords())){
            if(!CommonActivity.isNullOrEmpty(omniProcessId)){
                SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, methodNameMain);
                subConnectPreAsyn.execute();
            }else{
                PlaceOrderAsyncTask placeOrderAsyncTask = new PlaceOrderAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if("0".equals(errorCode)){
                            // neu thanh cong thi goi ham dau noi
                            if(!CommonActivity.isNullOrEmpty(result)){
                                omniProcessId = result;
                                // goi ham dau noi hoac chuyen doi o day
                                SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, methodNameMain);
                                subConnectPreAsyn.execute();
                            }else{
                                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdataomniprocessid),
                                        getActivity().getString(R.string.app_name)).show();
                            }
                        }else{
                            CommonActivity.createAlertDialog(getActivity(), description,
                                    getActivity().getString(R.string.app_name)).show();
                        }
                    }
                },null, "HSDT",profileBO.getProfileRecords());
                placeOrderAsyncTask.execute(nameCusCreateNew, type);
            }
        }else{
            SubConnectAsyn subConnectPreAsyn = new SubConnectAsyn(activity, methodNameMain);
            subConnectPreAsyn.execute();
        }

    }

    private String xmlCustPre(){
        StringBuilder rawData = new StringBuilder();
        // them thong tin nguoi su dung thue bao
//                    if(!CommonActivity.isNullOrEmpty(custIdentityDTO) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType()) && "M".equals(serviceType)){
        if (!CommonActivity.isNullOrEmpty(customerInfo) && !"used_device".equalsIgnoreCase(checkIsSpec)) {
            customerInfo.setCopy(cbUseRepCus.isChecked());
            rawData.append(customerInfo.toXmlUserInfo());
        }
//                    }

        // truyen thong tin cho goi cuoc PC
        if (!CommonActivity.isNullOrEmpty(checkPCProduct)) {
            if ("true".equals(checkPCProduct)) {
                rawData.append("<zoneProvice>");
                rawData.append("<code>" + province);
                rawData.append("</code>");
                rawData.append("<name>" + areaProvicialPR.getName());
                rawData.append("</name>");
                rawData.append("</zoneProvice>");
                rawData.append("<zoneDistrict>");
                rawData.append("<code>" + district);
                rawData.append("</code>");
                rawData.append("<name>" + areaDistristPR.getName());
                rawData.append("</name>");
                rawData.append("</zoneDistrict>");
                rawData.append("<isPCProduct >" + true);
                rawData.append("</isPCProduct >");
            }
        }

        if (custIdentityDTO.getCustId() != null) {
            rawData.append("<custId>" + "" + custIdentityDTO.getCustId());
            rawData.append("</custId>");
            rawData.append("<isNewCustomer>" + false);
            rawData.append("</isNewCustomer>");
        } else {
            if (custIdentityDTO.getCustomer().getCustId() != null) {
                rawData.append("<custId>" + "" + custIdentityDTO.getCustomer().getCustId());
                rawData.append("</custId>");
                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
                if ("M".equals(serviceType)) {
                    if (arrSubscriberDTO != null && arrSubscriberDTO.size() > 0) {
                        for (SubscriberDTO item : arrSubscriberDTO) {
                            rawData.append("<listSubscriber>");
                            rawData.append("<subId>" + "" + item.getSubId());
                            rawData.append("</subId>");
                            rawData.append("<hasVerifiedOwner>" + "" + item.isHasVerifiedOwner());
                            rawData.append("</hasVerifiedOwner>");
                            rawData.append("</listSubscriber>");
                        }
                    }
                }

            } else {
                rawData.append("<custId>" + null);
                rawData.append("</custId>");
                rawData.append("<updateCustIdentity>" + true);
                rawData.append("</updateCustIdentity>");

                rawData.append("<isNewCustomer>" + true);
                rawData.append("</isNewCustomer>");

            }
        }

        rawData.append("<name>" + "" + StringUtils.escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getName())));
        rawData.append("</name>");
        rawData.append("<custType>" + "" + custIdentityDTO.getCustomer().getCustType());
        rawData.append("</custType>");
        rawData.append("<custTypeDTO>");
        rawData.append("<custType>" + "" + custIdentityDTO.getCustomer().getCustType());
        rawData.append("</custType>");
        rawData.append("<groupType>" + "" + custIdentityDTO.getGroupType());
        rawData.append("</groupType>");
        rawData.append("</custTypeDTO>");
        // truong hop co nhieu loai giay to
        if (custIdentityDTO.getCustomer().getListCustIdentity() != null
                && custIdentityDTO.getCustomer().getListCustIdentity().size() > 1) {
            for (CustIdentityDTO item : custIdentityDTO.getCustomer().getListCustIdentity()) {
                rawData.append("<listCustIdentity>");
                rawData.append("<idNo>" + "" + item.getIdNo());
                rawData.append("</idNo>");
                rawData.append("<idType>" + "" + item.getIdType());
                rawData.append("</idType>");
                rawData.append("<required>" + true);
                rawData.append("</required>");

                rawData.append("</listCustIdentity>");
            }
        } else {
            rawData.append("<listCustIdentity>");
            rawData.append("<idNo>" + "" + custIdentityDTO.getIdNo());
            rawData.append("</idNo>");
            rawData.append("<idType>" + "" + custIdentityDTO.getIdType());
            rawData.append("</idType>");

            if (custIdentityDTO.getCustomer().getCustId() != null) {
                rawData.append("<idIssueDate>" + "" + custIdentityDTO.getIdIssueDate());
                rawData.append("</idIssueDate>");
            } else {
                rawData.append("<idIssueDate>" + ""
                        + StringUtils.convertDateToString(custIdentityDTO.getIdIssueDate()) + "T00:00:00+07:00");
                rawData.append("</idIssueDate>");


                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getIdExpireDate())) {
                    rawData.append("<idExpireDate>" + ""
                            + StringUtils.convertDateToString(custIdentityDTO.getIdExpireDate()) + "T00:00:00+07:00");
                    rawData.append("</idExpireDate>");
                }


            }
            rawData.append("<idIssuePlace>" + "" + custIdentityDTO.getIdIssuePlace());
            rawData.append("</idIssuePlace>");
            rawData.append("<required>" + true);
            rawData.append("</required>");

            rawData.append("</listCustIdentity>");

            rawData.append("<sex>" + "" + custIdentityDTO.getCustomer().getSex());
            rawData.append("</sex>");
        }

        // ngay sinh/ngay thanh lap

        if (custIdentityDTO.getCustId() != null || custIdentityDTO.getCustomer().getCustId() != null) {
            rawData.append("<birthDate>" + custIdentityDTO.getCustomer().getBirthDate());
            rawData.append("</birthDate>");
        } else {
            rawData.append("<birthDate>" + ""
                    + StringUtils.convertDateToString(custIdentityDTO.getCustomer().getBirthDate())
                    + "T00:00:00+07:00");
            rawData.append("</birthDate>");
        }

        rawData.append("<custAdd>");
        rawData.append("<province>");
        rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getProvince());
        rawData.append("</code>");
        rawData.append("</province>");

        rawData.append("<district>");
        rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getDistrict());
        rawData.append("</code>");
        rawData.append("</district>");

        rawData.append("<precinct>");
        rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getPrecinct());
        rawData.append("</code>");
        rawData.append("</precinct>");

        rawData.append("<streetBlock>");
        rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getStreetBlock());
        rawData.append("</code>");
        rawData.append("</streetBlock>");

        rawData.append("<areaCode>" + "" + custIdentityDTO.getCustomer().getAreaCode());
        rawData.append("</areaCode>");
        rawData.append("<fullAddress>" + "" + custIdentityDTO.getCustomer().getAddress());
        rawData.append("</fullAddress>");

        rawData.append("</custAdd>");

        rawData.append("<province>" + "" + custIdentityDTO.getCustomer().getProvince());
        rawData.append("</province>");
        rawData.append("<district>" + "" + custIdentityDTO.getCustomer().getDistrict());
        rawData.append("</district>");
        rawData.append("<precinct>" + "" + custIdentityDTO.getCustomer().getPrecinct());
        rawData.append("</precinct>");
        rawData.append("<streetBlock>" + "" + custIdentityDTO.getCustomer().getStreetBlock());
        rawData.append("</streetBlock>");

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getHome())) {
            rawData.append("<home>" + "" + CommonActivity.getNormalText(custIdentityDTO.getCustomer().getHome()));
            rawData.append("</home>");
        }

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getStreetName())) {
            rawData.append("<streetName>" + "" + StringUtils
                    .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getStreetName())));
            rawData.append("</streetName>");
        }

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getNationality())) {
            rawData.append("<nationality>" + "" + custIdentityDTO.getCustomer().getNationality());
            rawData.append("</nationality>");
        }

        rawData.append("<areaCode>" + "" + custIdentityDTO.getCustomer().getAreaCode());
        rawData.append("</areaCode>");
        rawData.append("<address>" + "" + CommonActivity.getNormalText(custIdentityDTO.getCustomer().getAddress()));
        rawData.append("</address>");

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getDescription())) {
            rawData.append("<description>" + "" + CommonActivity.getNormalText(custIdentityDTO.getCustomer().getDescription()));
            rawData.append("</description>");
        }

        rawData.append("<identityNo>" + "" + custIdentityDTO.getIdNo());
        rawData.append("</identityNo>");

        // thong tin kh dai dien ca nhan
        if ("2".equals(subType) && custIdentityDTO.getRepreCustomer() != null) {
            rawData.append("<representativeCust>");
            if (custIdentityDTO.getRepreCustomer().getCustId() != null) {
                rawData.append("<custId>" + "" + custIdentityDTO.getCustId());
                rawData.append("</custId>");
                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
            } else {
                if (custIdentityDTO.getRepreCustomer().getCustomer() != null
                        && custIdentityDTO.getRepreCustomer().getCustomer().getCustId() != null) {
                    rawData.append(
                            "<custId>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getCustId());
                    rawData.append("</custId>");
                    rawData.append("<isNewCustomer>" + false);
                    rawData.append("</isNewCustomer>");
                } else {

                    rawData.append("<custId>" + null);
                    rawData.append("</custId>");
                    rawData.append("<isNewCustomer>" + true);
                    rawData.append("</isNewCustomer>");

                    rawData.append("<newRepCustomer>" + true);
                    rawData.append("</newRepCustomer>");

                    rawData.append("<updateCustIdentity>" + true);
                    rawData.append("</updateCustIdentity>");

                }
            }
            if (custIdentityDTO.getRepreCustomer().getCustomer() != null) {
                rawData.append("<name>" + "" + StringUtils
                        .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getRepreCustomer().getCustomer().getName())));
                rawData.append("</name>");

            }

            rawData.append(
                    "<custType>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getCustType());
            rawData.append("</custType>");

            rawData.append("<custTypeDTO>");
            rawData.append(
                    "<custType>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getCustType());
            rawData.append("</custType>");
            rawData.append("<groupType>" + "" + 1);
            rawData.append("</groupType>");
            rawData.append("</custTypeDTO>");
            rawData.append("<identityNo>" + "" + custIdentityDTO.getRepreCustomer().getIdNo());
            rawData.append("</identityNo>");
            rawData.append("<listCustIdentity>");
            rawData.append("<idNo>" + "" + custIdentityDTO.getRepreCustomer().getIdNo());
            rawData.append("</idNo>");
            rawData.append("<idType>" + "" + custIdentityDTO.getRepreCustomer().getIdType());
            rawData.append("</idType>");
            if (custIdentityDTO.getRepreCustomer().getCustomer().getCustId() != null) {
                rawData.append("<idIssueDate>" + "" + custIdentityDTO.getRepreCustomer().getIdIssueDate());
            } else {
                rawData.append(
                        "<idIssueDate>" + ""
                                + StringUtils.convertDateToString(
                                custIdentityDTO.getRepreCustomer().getIdIssueDate())
                                + "T00:00:00+07:00");
            }

            rawData.append("</idIssueDate>");
            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getRepreCustomer().getIdExpireDate())) {
                rawData.append("<idExpireDate>" + ""
                        + StringUtils.convertDateToString(custIdentityDTO.getRepreCustomer().getIdExpireDate()) + "T00:00:00+07:00");
                rawData.append("</idExpireDate>");
            }
            rawData.append("<idIssuePlace>" + "" + CommonActivity.getNormalText(custIdentityDTO.getRepreCustomer().getIdIssuePlace()));
            rawData.append("</idIssuePlace>");
            rawData.append("<required>" + true);
            rawData.append("</required>");

            rawData.append("</listCustIdentity>");

            rawData.append("<sex>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getSex());
            rawData.append("</sex>");

            // ngay sinh/ngay thanh lap
            if (custIdentityDTO.getRepreCustomer().getCustomer().getCustId() != null) {
                rawData.append("<birthDate>" + ""
                        + custIdentityDTO.getRepreCustomer().getCustomer().getBirthDate());
                rawData.append("</birthDate>");
            } else {
                rawData.append("<birthDate>" + ""
                        + StringUtils.convertDateToString(
                        custIdentityDTO.getRepreCustomer().getCustomer().getBirthDate())
                        + "T00:00:00+07:00");
                rawData.append("</birthDate>");
            }

            rawData.append("<custAdd>");
            rawData.append("<province>");
            rawData.append("<code>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getProvince());
            rawData.append("</code>");
            rawData.append("</province>");

            rawData.append("<district>");
            rawData.append("<code>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getDistrict());
            rawData.append("</code>");
            rawData.append("</district>");

            rawData.append("<precinct>");
            rawData.append("<code>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getPrecinct());
            rawData.append("</code>");
            rawData.append("</precinct>");

            rawData.append("<streetBlock>");
            rawData.append(
                    "<code>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getStreetBlock());
            rawData.append("</code>");
            rawData.append("</streetBlock>");

            rawData.append(
                    "<areaCode>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getAreaCode());
            rawData.append("</areaCode>");
            rawData.append(
                    "<fullAddress>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getAddress());
            rawData.append("</fullAddress>");

            rawData.append("</custAdd>");

            rawData.append(
                    "<province>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getProvince());
            rawData.append("</province>");
            rawData.append(
                    "<district>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getDistrict());
            rawData.append("</district>");
            rawData.append(
                    "<precinct>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getPrecinct());
            rawData.append("</precinct>");
            rawData.append("<streetBlock>" + ""
                    + custIdentityDTO.getRepreCustomer().getCustomer().getStreetBlock());
            rawData.append("</streetBlock>");
            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getRepreCustomer().getCustomer().getHome())) {
                rawData.append("<home>" + "" + CommonActivity.getNormalText(custIdentityDTO.getRepreCustomer().getCustomer().getHome()));
                rawData.append("</home>");
            }
            if (!CommonActivity
                    .isNullOrEmpty(custIdentityDTO.getRepreCustomer().getCustomer().getStreetName())) {
                rawData.append("<streetName>" + ""
                        + StringUtils
                        .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getRepreCustomer().getCustomer().getStreetName())));
                rawData.append("</streetName>");
            }

            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getNationality())) {
                rawData.append("<nationality>" + ""
                        + custIdentityDTO.getRepreCustomer().getCustomer().getNationality());
                rawData.append("</nationality>");
            }

            rawData.append(
                    "<areaCode>" + "" + custIdentityDTO.getRepreCustomer().getCustomer().getAreaCode());
            rawData.append("</areaCode>");
            rawData.append(
                    "<address>" + "" + CommonActivity.getNormalText(custIdentityDTO.getRepreCustomer().getCustomer().getAddress()));
            rawData.append("</address>");
            rawData.append("</representativeCust>");
        }
        return rawData.toString();
    }
    private String xmlCusPos(){

        StringBuilder rawData = new StringBuilder();

        // them thong tin nguoi su dung thue bao
//                    if(!CommonActivity.isNullOrEmpty(custIdentityDTO) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()) && "2".equals(custIdentityDTO.getGroupType()) && "M".equals(serviceType)){
        if (!CommonActivity.isNullOrEmpty(customerInfo) && !"used_device".equalsIgnoreCase(checkIsSpec)) {
            customerInfo.setCopy(cbUseRepCus.isChecked());
            rawData.append(customerInfo.toXmlUserInfo());
        }
//                    }
        if (accountDTOMain != null) {
            // them dia chi thong bao cuoc cho phan hop dong
            rawData.append("<custContactDTO>");
            rawData.append("<billAddress>" + CommonActivity.getNormalText(accountDTOMain.getAddressPrint()));
            rawData.append("</billAddress>");

            if (!CommonActivity.isNullOrEmpty(accountDTOMain.geteMail())) {
                rawData.append("<email>" + accountDTOMain.geteMail());
                rawData.append("</email>");
            } else {
                rawData.append("<email>" + "");
                rawData.append("</email>");
            }


            rawData.append("<telephone>" + accountDTOMain.getTelMobile());
            rawData.append("</telephone>");

            rawData.append("</custContactDTO>");
        }
        if (!CommonActivity.isNullOrEmpty(checkPCProduct)) {
            if ("true".equals(checkPCProduct)) {
                rawData.append("<zoneProvice>");
                rawData.append("<code>" + province);
                rawData.append("</code>");
                rawData.append("<name>" + areaProvicialPR.getName());
                rawData.append("</name>");
                rawData.append("</zoneProvice>");
                rawData.append("<zoneDistrict>");
                rawData.append("<code>" + district);
                rawData.append("</code>");
                rawData.append("<name>" + areaDistristPR.getName());
                rawData.append("</name>");
                rawData.append("</zoneDistrict>");
                rawData.append("<isPCProduct>" + true);
                rawData.append("</isPCProduct >");
            }
        }
        if (custIdentityDTO.getCustId() != null) {
            rawData.append("<custId>" + "" + custIdentityDTO.getCustId());
            rawData.append("</custId>");
            rawData.append("<isNewCustomer>" + false);
            rawData.append("</isNewCustomer>");
        } else {
            if (custIdentityDTO.getCustomer().getCustId() != null) {
                rawData.append("<custId>" + "" + custIdentityDTO.getCustomer().getCustId());
                rawData.append("</custId>");
                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
            } else {
                rawData.append("<custId>" + null);
                rawData.append("</custId>");
                rawData.append("<updateCustIdentity>" + true);
                rawData.append("</updateCustIdentity>");
                rawData.append("<isNewCustomer>" + true);
                rawData.append("</isNewCustomer>");
                rawData.append(
                        "<name>" + "" + StringUtils.escapeHtml(custIdentityDTO.getCustomer().getName()));
                rawData.append("</name>");
                rawData.append("<custType>" + "" + custIdentityDTO.getCustomer().getCustType());
                rawData.append("</custType>");
                rawData.append("<custTypeDTO>");
                rawData.append("<custType>" + "" + custIdentityDTO.getCustomer().getCustType());
                rawData.append("</custType>");
                rawData.append("<groupType>" + "" + custIdentityDTO.getGroupType());
                rawData.append("</groupType>");
                rawData.append("</custTypeDTO>");

                rawData.append("<identityNo>" + "" + custIdentityDTO.getIdNo());
                rawData.append("</identityNo>");

                // truong hop co nhieu loai giay to
                if (custIdentityDTO.getCustomer().getListCustIdentity() != null
                        && custIdentityDTO.getCustomer().getListCustIdentity().size() > 1) {
                    for (CustIdentityDTO item : custIdentityDTO.getCustomer().getListCustIdentity()) {
                        rawData.append("<listCustIdentity>");
                        rawData.append("<idNo>" + "" + item.getIdNo());
                        rawData.append("</idNo>");
                        rawData.append("<idType>" + "" + item.getIdType());
                        rawData.append("</idType>");


                        rawData.append("<required>" + true);
                        rawData.append("</required>");

                        rawData.append("</listCustIdentity>");
                    }
                } else {
                    rawData.append("<listCustIdentity>");
                    rawData.append("<idNo>" + "" + custIdentityDTO.getIdNo());
                    rawData.append("</idNo>");
                    rawData.append("<idType>" + "" + custIdentityDTO.getIdType());
                    rawData.append("</idType>");

                    if (custIdentityDTO.getCustomer().getCustId() != null) {
                        rawData.append("<idIssueDate>" + "" + custIdentityDTO.getIdIssueDate());
                        rawData.append("</idIssueDate>");
                    } else {
                        rawData.append("<idIssueDate>" + ""
                                + StringUtils.convertDateToString(custIdentityDTO.getIdIssueDate())
                                + "T00:00:00+07:00");
                        rawData.append("</idIssueDate>");
                    }

                    rawData.append("<idIssuePlace>" + "" + CommonActivity.getNormalText(custIdentityDTO.getIdIssuePlace()));
                    rawData.append("</idIssuePlace>");
                    // }
                    rawData.append("<required>" + true);
                    rawData.append("</required>");

                    rawData.append("</listCustIdentity>");

                    rawData.append("<sex>" + "" + custIdentityDTO.getCustomer().getSex());
                    rawData.append("</sex>");
                }

                // ngay sinh/ngay thanh lap

                if (custIdentityDTO.getCustId() != null
                        || custIdentityDTO.getCustomer().getCustId() != null) {
                    rawData.append("<birthDate>" + custIdentityDTO.getCustomer().getBirthDate());
                    rawData.append("</birthDate>");
                } else {
                    rawData.append("<birthDate>" + ""
                            + StringUtils.convertDateToString(custIdentityDTO.getCustomer().getBirthDate())
                            + "T00:00:00+07:00");
                    rawData.append("</birthDate>");
                }

                rawData.append("<custAdd>");
                rawData.append("<province>");
                rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getProvince());
                rawData.append("</code>");
                rawData.append("</province>");

                rawData.append("<district>");
                rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getDistrict());
                rawData.append("</code>");
                rawData.append("</district>");

                rawData.append("<precinct>");
                rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getPrecinct());
                rawData.append("</code>");
                rawData.append("</precinct>");

                rawData.append("<streetBlock>");
                rawData.append("<code>" + "" + custIdentityDTO.getCustomer().getStreetBlock());
                rawData.append("</code>");
                rawData.append("</streetBlock>");

                rawData.append("<areaCode>" + "" + custIdentityDTO.getCustomer().getAreaCode());
                rawData.append("</areaCode>");
                rawData.append("<fullAddress>" + "" + StringUtils
                        .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getAddress())));
                rawData.append("</fullAddress>");

                rawData.append("</custAdd>");

                rawData.append("<province>" + "" + custIdentityDTO.getCustomer().getProvince());
                rawData.append("</province>");
                rawData.append("<district>" + "" + custIdentityDTO.getCustomer().getDistrict());
                rawData.append("</district>");
                rawData.append("<precinct>" + "" + custIdentityDTO.getCustomer().getPrecinct());
                rawData.append("</precinct>");
                rawData.append("<streetBlock>" + "" + custIdentityDTO.getCustomer().getStreetBlock());
                rawData.append("</streetBlock>");

                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getHome())) {
                    rawData.append("<home>" + "" + StringUtils
                            .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getHome())));
                    rawData.append("</home>");
                }

                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getStreetName())) {
                    rawData.append("<streetName>" + "" + StringUtils
                            .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getStreet())));
                    rawData.append("</streetName>");
                }

                rawData.append("<areaCode>" + "" + custIdentityDTO.getCustomer().getAreaCode());
                rawData.append("</areaCode>");
                rawData.append("<address>" + "" + StringUtils
                        .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getAddress())));
                rawData.append("</address>");

                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getNationality())) {
                    rawData.append("<nationality>" + "" + custIdentityDTO.getCustomer().getNationality());
                    rawData.append("</nationality>");
                }
                if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getDescription())) {
                    rawData.append("<description>" + "" + StringUtils
                            .escapeHtml(CommonActivity.getNormalText(custIdentityDTO.getCustomer().getDescription())));
                    rawData.append("</description>");
                }
            }
        }

        // thong tin kh dai dien ca nhan
        if ("1".equals(subType) && accountDTOMain.getRefCustomer() != null) {
            rawData.append("<representativeCustContract>");
            if (accountDTOMain.getRefCustomer().getCustId() != null) {
                rawData.append("<custId>" + "" + accountDTOMain.getRefCustomer().getCustId());
                rawData.append("</custId>");
                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
            } else {
                rawData.append("<updateCustIdentity>" + true);
                rawData.append("</updateCustIdentity>");
                rawData.append("<isNewCustomer>" + true);
                rawData.append("</isNewCustomer>");
                rawData.append("<newRepCustomer>" + true);
                rawData.append("</newRepCustomer>");

            }

            rawData.append(
                    "<name>" + "" + StringUtils.escapeHtml(CommonActivity.getNormalText(accountDTOMain.getRefCustomer().getName())));
            rawData.append("</name>");

            rawData.append("<custType>" + "" + accountDTOMain.getRefCustomer().getCustType());
            rawData.append("</custType>");

            rawData.append("<custTypeDTO>");
            rawData.append("<custType>" + "" + accountDTOMain.getRefCustomer().getCustType());
            rawData.append("</custType>");
            rawData.append("<groupType>" + "" + 1);
            rawData.append("</groupType>");
            rawData.append("</custTypeDTO>");

            if (accountDTOMain.getRefCustomer().getListCustIdentity() != null
                    && accountDTOMain.getRefCustomer().getListCustIdentity().size() > 0) {
                rawData.append("<identityNo>" + "" + accountDTOMain.getRefCustomer().getListCustIdentity().get(0).getIdNo());
                rawData.append("</identityNo>");
                rawData.append("<listCustIdentity>");
                rawData.append("<idNo>" + ""
                        + accountDTOMain.getRefCustomer().getListCustIdentity().get(0).getIdNo());
                rawData.append("</idNo>");
                rawData.append("<idType>" + ""
                        + accountDTOMain.getRefCustomer().getListCustIdentity().get(0).getIdType());
                rawData.append("</idType>");

                if (accountDTOMain.getRefCustomer().getCustId() != null) {
                    rawData.append("<idIssueDate>" + "" + accountDTOMain.getRefCustomer()
                            .getListCustIdentity().get(0).getIdIssueDate());
                    rawData.append("</idIssueDate>");
                } else {
                    rawData.append("<idIssueDate>" + "" + StringUtils.convertDateToString(
                            accountDTOMain.getRefCustomer().getListCustIdentity().get(0).getIdIssueDate())
                            + "T00:00:00+07:00");
                    rawData.append("</idIssueDate>");
                }

                if (!CommonActivity.isNullOrEmpty(edit_ngayhethandd.getText().toString())) {
                    rawData.append("<idExpireDate>" + "" + StringUtils.convertDateToString(
                            edit_ngayhethandd.getText().toString())
                            + "T00:00:00+07:00");
                    rawData.append("</idExpireDate>");
                }

                rawData.append("<idIssuePlace>" + ""
                        + CommonActivity.getNormalText(accountDTOMain.getRefCustomer().getListCustIdentity().get(0).getIdIssuePlace()));
                rawData.append("</idIssuePlace>");
                rawData.append("<required>" + true);
                rawData.append("</required>");

                rawData.append("</listCustIdentity>");
            }

            rawData.append("<sex>" + "" + accountDTOMain.getRefCustomer().getSex());
            rawData.append("</sex>");

            // ngay sinh/ngay thanh lap
            if (accountDTOMain.getRefCustomer().getCustId() != null) {
                rawData.append("<birthDate>" + "" + accountDTOMain.getRefCustomer().getBirthDate());
                rawData.append("</birthDate>");
            } else {
                rawData.append("<birthDate>" + ""
                        + StringUtils.convertDateToString(accountDTOMain.getRefCustomer().getBirthDate())
                        + "T00:00:00+07:00");
                rawData.append("</birthDate>");
            }

            rawData.append("<custAdd>");
            rawData.append("<province>");
            rawData.append("<code>" + "" + accountDTOMain.getRefCustomer().getProvince());
            rawData.append("</code>");
            rawData.append("</province>");

            rawData.append("<district>");
            rawData.append("<code>" + "" + accountDTOMain.getRefCustomer().getDistrict());
            rawData.append("</code>");
            rawData.append("</district>");

            rawData.append("<precinct>");
            rawData.append("<code>" + "" + accountDTOMain.getRefCustomer().getPrecinct());
            rawData.append("</code>");
            rawData.append("</precinct>");

            rawData.append("<streetBlock>");
            rawData.append("<code>" + "" + accountDTOMain.getRefCustomer().getStreetBlock());
            rawData.append("</code>");
            rawData.append("</streetBlock>");

            rawData.append("<areaCode>" + "" + accountDTOMain.getRefCustomer().getAreaCode());
            rawData.append("</areaCode>");
            rawData.append("<fullAddress>" + "" + accountDTOMain.getRefCustomer().getAddress());
            rawData.append("</fullAddress>");

            rawData.append("</custAdd>");

            rawData.append("<province>" + "" + accountDTOMain.getRefCustomer().getProvince());
            rawData.append("</province>");
            rawData.append("<district>" + "" + accountDTOMain.getRefCustomer().getDistrict());
            rawData.append("</district>");
            rawData.append("<precinct>" + "" + accountDTOMain.getRefCustomer().getPrecinct());
            rawData.append("</precinct>");
            rawData.append("<streetBlock>" + "" + accountDTOMain.getRefCustomer().getStreetBlock());
            rawData.append("</streetBlock>");
            if (!CommonActivity.isNullOrEmpty(accountDTOMain.getRefCustomer().getHome())) {
                rawData.append("<home>" + "" + CommonActivity.getNormalText(accountDTOMain.getRefCustomer().getHome()));
                rawData.append("</home>");
            }

            if (!CommonActivity.isNullOrEmpty(accountDTOMain.getRefCustomer().getStreetName())) {
                rawData.append("<streetName>" + "" + CommonActivity.getNormalText(accountDTOMain.getRefCustomer().getStreetName()));
                rawData.append("</streetName>");
            }

            rawData.append("<areaCode>" + "" + accountDTOMain.getRefCustomer().getAreaCode());
            rawData.append("</areaCode>");
            rawData.append("<address>" + "" + accountDTOMain.getRefCustomer().getAddress());
            rawData.append("</address>");
            rawData.append("<nationality>" + "" + accountDTOMain.getRefCustomer().getNationality());
            rawData.append("</nationality>");
            rawData.append("</representativeCustContract>");
        }

        return rawData.toString();
    }
    private String subXml(boolean isCheckProfile){
        HashMap<String, String> param = new HashMap<String, String>();
        StringBuilder rawData = new StringBuilder();
            String actionCode = "";
        if (ActivityCreateNewRequestMobileNew.instance != null
                && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
            if (isCheckProfile) {
                rawData.append("<oldSerial>" + ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getSerial());
                rawData.append("</oldSerial>");
                rawData.append("<serial>" + txtserial.getText().toString().trim());
                rawData.append("</serial>");
            }
            String requestType = "";
            if (Constant.CHANGE_PRE_TO_POS
                    .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                actionCode = "220";
                requestType = "CHANGE_TO_POSPAID";
            } else {
                requestType = "CHANGE_TO_PREPAID";
                actionCode = "221";
            }
            rawData.append("<orgProductCode>" + ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getProductCode());
            rawData.append("</orgProductCode>");
            rawData.append("<productCode>" + productCode);
            rawData.append("</productCode>");
            rawData.append("<actionCode>" + actionCode);
            rawData.append("</actionCode>");
            rawData.append("<requestType>" + requestType);
            rawData.append("</requestType>");

        } else {
            rawData.append("<serial>" + txtserial.getText().toString().trim());
            rawData.append("</serial>");
        }


        // bo sung omichanel
        if(!CommonActivity.isNullOrEmpty(omniProcessId)){
            rawData.append("<omniProcessId>" + omniProcessId);
            rawData.append("</omniProcessId>");
        }
        if(!CommonActivity.isNullOrEmpty(omniTaskId)){
            rawData.append("<omniTaskId>" + omniTaskId);
            rawData.append("</omniTaskId>");
        }

        rawData.append(
                "<signDate>" + StringUtils.convertDateToString(edit_ngaykySub.getText().toString().trim())
                        + "T00:00:00+07:00");
        rawData.append("</signDate>");


        // bo sung subId khi chuyen doi
        if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity.isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.subscriberDTO)) {
            rawData.append(
                    "<subId>" + "" + ActivityCreateNewRequestMobileNew.instance.subscriberDTO.getSubId());
            rawData.append("</subId>");
        }



        // build thong tin khach hang cho tra sau

        if ("1".equals(subType)) {
            rawData.append("<customerDTOInput>");
            rawData.append(xmlCusPos());
            rawData.append("</customerDTOInput>");

            // thong tin hop dong tra sau
            rawData.append("<accountDTOForInput>");
            rawData.append(accountDTOMain.toXml());
            // them thong tin bo sung
            rawData.append("<addInfo>" + addInfo + "</addInfo>");
            rawData.append(
                    "<signDateOfSub>" + StringUtils.convertDateToString(edit_ngaykySub.getText().toString().trim())
                            + "T00:00:00+07:00");
            rawData.append("</signDateOfSub>");

            rawData.append("</accountDTOForInput>");

            if (accountDTOMain != null && !CommonActivity.isNullOrEmpty(accountDTOMain.getAccountId())) {
                rawData.append("<contractDTOInput>");
                rawData.append("<contractId>" + "" + accountDTOMain.getAccountId());
                rawData.append("</contractId>");
                rawData.append("</contractDTOInput>");

            }

            // accountBankDTO
            if ("02".equals(accountDTOMain.getPayMethod()) || "03".equals(accountDTOMain.getPayMethod())) {
                // rawData.append("<accountBankDTO>");
                rawData.append(accountDTOMain.getAccountBank().toXml());
                // rawData.append("</accountBankDTO>");

            } else {
                rawData.append("<accountBankDTO>");
                rawData.append("</accountBankDTO>");
            }

            // them thong tin loai thue bao, khuyen mai, cuoc dong truoc
            // , han muc , dat coc
            rawData.append("<promotionCode>" + maKM);
            rawData.append("</promotionCode>");
            rawData.append("<subType>" + subTypeMobile);
            rawData.append("</subType>");

            // han muc subLimitUsageDTO


            // dat coc SubDepositDTO KHAC TINH

            if ("M".equals(serviceType)) {
                if (Session.province.equals(provinceContract)) {

                    Spin limitSpin = (Spin) spinner_quota.getSelectedItem();
                    if (limitSpin != null) {

                        if (!CommonActivity.isNullOrEmpty(limitUs) && !"0".equals(limitUs)) {
                            rawData.append("<limitUsage>" + limitUs);
                            rawData.append("</limitUsage>");
                            if (!CommonActivity.isNullOrEmpty(limitSpin.getId())) {
                                rawData.append("<subLimitUsageDTO>");
                                rawData.append("<amount>" + limitUs);
                                rawData.append("</amount>");
                                rawData.append("</subLimitUsageDTO>");
                            }
                            if (limitSpin != null) {
                                rawData.append("<subExtDTO>");
                                rawData.append("<extKey>" + "CONFIRMED_QUOTA");
                                rawData.append("</extKey>");
                                rawData.append("<extValue>" + limitSpin.getId());
                                rawData.append("</extValue>");
                                rawData.append("</subExtDTO>");
                            }

                        } else {
                            if (!CommonActivity.isNullOrEmpty(limitSpin.getId())) {
                                rawData.append("<subLimitUsageDTO>");
                                rawData.append("<amount>" + limitSpin.getId());
                                rawData.append("</amount>");
                                rawData.append("</subLimitUsageDTO>");
                                rawData.append("<limitUsage>" + limitSpin.getId());
                                rawData.append("</limitUsage>");
                            }
                        }

                    }

                } else {

                    // them doan nay doi voi truong hop dau noi mobile
                    // ngoai tinh
                    // so sanh gia tri cua litmitUs
                    if (!CommonActivity.isNullOrEmpty(limitUsCommiton) && !"0".equals(limitUsCommiton)) {
                        // rawData.append("<limitUsage>" + limitUs);
                        // rawData.append("</limitUsage>");

                        rawData.append("<subLimitUsageDTO>");
                        rawData.append("<amount>" + limitUsCommiton);
                        rawData.append("</amount>");
                        rawData.append("</subLimitUsageDTO>");
                    } else {
                        rawData.append("<subLimitUsageDTO>");
                        rawData.append("<amount>" + "0");
                        rawData.append("</amount>");
                        rawData.append("</subLimitUsageDTO>");
                    }

                    // modify truyen them thong tin ngoai tinh
                    Spin depositSpin = (Spin) spinner_deposit.getSelectedItem();
                    if (depositSpin != null) {
                        if (!CommonActivity.isNullOrEmpty(depositSpin.getId())) {
                            rawData.append("<subDepositDTO>");
                            rawData.append("<deposit>" + depositSpin.getId());
                            rawData.append("</deposit>");
                            if ("M".equals(serviceType)) {
                                rawData.append("<reasonType>" + "3");
                                rawData.append("</reasonType>");
                            } else {
                                rawData.append("<reasonType>" + "6");
                                rawData.append("</reasonType>");
                            }
                            rawData.append("</subDepositDTO>");
                        }
                    }
                    if (depositSpin != null) {
                        rawData.append("<subExtDTO>");
                        rawData.append("<extKey>" + "CONNECT_DIFF_PROVINCE");
                        rawData.append("</extKey>");
                        rawData.append("<extValue>" + "1");
                        rawData.append("</extValue>");
                        rawData.append("</subExtDTO>");

                    }
                    if (depositSpin != null) {
                        rawData.append("<deposit>" + depositSpin.getId());
                        rawData.append("</deposit>");
                    }

                }

            } else {
                // modify ngay 15 thang 11 2016 homphone
                Spin limitSpin = (Spin) spinner_quota.getSelectedItem();
                if (limitSpin != null) {

                    if (!CommonActivity.isNullOrEmpty(limitUs) && !"0".equals(limitUs)) {
                        rawData.append("<limitUsage>" + limitUs);
                        rawData.append("</limitUsage>");

                        rawData.append("<subLimitUsageDTO>");
                        rawData.append("<amount>" + limitUs);
                        rawData.append("</amount>");
                        rawData.append("</subLimitUsageDTO>");

                        if (limitSpin != null) {
                            rawData.append("<subExtDTO>");
                            rawData.append("<extKey>" + "CONFIRMED_QUOTA");
                            rawData.append("</extKey>");
                            rawData.append("<extValue>" + limitSpin.getId());
                            rawData.append("</extValue>");
                            rawData.append("</subExtDTO>");
                        }

                    } else {
                        if (!CommonActivity.isNullOrEmpty(limitSpin.getId())) {
                            rawData.append("<subLimitUsageDTO>");
                            rawData.append("<amount>" + limitSpin.getId());
                            rawData.append("</amount>");
                            rawData.append("</subLimitUsageDTO>");
                            rawData.append("<limitUsage>" + limitSpin.getId());
                            rawData.append("</limitUsage>");
                        }
                    }

                }

                // rawData.append("<subLimitUsageDTO>");
                // rawData.append("<amount>" + "0");
                // rawData.append("</amount>");
                // rawData.append("</subLimitUsageDTO>");

            }

            // cuoc dong truoc PrepaidMonthBO

            rawData.append("<prepaidMonthBO>");
            rawData.append("<prepaidValue>" + prepaidCode);
            rawData.append("</prepaidValue>");
            rawData.append("<prepaidId>" + prepaidId);
            rawData.append("</prepaidId>");
            rawData.append("</prepaidMonthBO>");

        }
        rawData.append("<vasList>");
        rawData.append("</vasList>");

        rawData.append("<vasListConnect>");
        rawData.append("</vasListConnect>");

        // isdn,reasonId
        rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(txtisdn.getText().toString().trim()));
        rawData.append("</isdn>");
        rawData.append("<regType>" + hthm);
        rawData.append("</regType>");

        rawData.append("<reasonId>" + reasonId);
        rawData.append("</reasonId>");

        rawData.append("<regTypeId>" + reasonId);
        rawData.append("</regTypeId>");
        if(!isCheckProfile){
            rawData.append("<serial>" + txtserial.getText().toString());
            rawData.append("</serial>");
        }


        // truyen goi cuoc ro lang nhang
        rawData.append("<productCode>" + productCode);
        rawData.append("</productCode>");
        rawData.append("<offerId>" + offerId);
        rawData.append("</offerId>");
        rawData.append("<payType>" + subType);
        rawData.append("</payType>");

        rawData.append("<telecomServiceId>" + telecomserviceId + "");
        rawData.append("</telecomServiceId>");

        // doi voi dich vu homphone truyen them dia ban
        if ("H".equals(serviceType)) {
            rawData.append("<subInfrastructureDTO>");
            rawData.append("<province>" + "" + province);
            rawData.append("</province>");
            rawData.append("<district>" + "" + district);
            rawData.append("</district>");
            rawData.append("<precinct>" + "" + precinct);
            rawData.append("</precinct>");
            rawData.append("<streetBlock>" + "" + to);
            rawData.append("</streetBlock>");
            rawData.append("<home>" + "" + areaHomeNumberPR);
            rawData.append("</home>");
            rawData.append("<streetName>" + "" + areaFlowPR);
            rawData.append("</streetName>");
            rawData.append("<areaCode>" + "" + province + district + precinct);
            rawData.append("</areaCode>");
            rawData.append("<address>" + "" + edtdiachilapdat.getText().toString());
            rawData.append("</address>");
            rawData.append("</subInfrastructureDTO>");
        }

        // mat hang so

        rawData.append("<listStockIsdnBean>");
        rawData.append("<isdn>" + "" + CommonActivity.getStardardIsdnBCCS(txtisdn.getText().toString().trim()));
        rawData.append("</isdn>");
        rawData.append("<isdnType>" + "" + 1);
        rawData.append("</isdnType>");
        rawData.append("<prodOfferTypeId>" + "" + telecomserviceId);
        rawData.append("</prodOfferTypeId>");
        rawData.append("</listStockIsdnBean>");

        // mat hang sim
        if (!CommonActivity.isNullOrEmpty(txtserial.getText().toString().trim())) {

            rawData.append("<listStockSimBean>");
            rawData.append("<productOfferTypeId>" + "" + 4);
            rawData.append("</productOfferTypeId>");
            rawData.append("<serial>" + "" + txtserial.getText().toString().trim());
            rawData.append("</serial>");
            rawData.append("</listStockSimBean>");

        }

        // =============raw data add hang hoa
        ArrayList<ProductOfferingDTO> arrApStockModelBeanBOs = new ArrayList<ProductOfferingDTO>();

        Log.d("mapSubStockModelRelReq.size : ", mapSubStockModelRelReq.size() + "");
        arrApStockModelBeanBOs.addAll(mapSubStockModelRelReq.values());
        if (arrApStockModelBeanBOs.size() > 0) {
            for (ProductOfferingDTO objAPStockModelBeanBO : arrApStockModelBeanBOs) {
                // mat hang dinh kem
                rawData.append("<listStockSerialBean>");
                rawData.append("<serial>" + objAPStockModelBeanBO.getSerial());
                rawData.append("</serial>");
                rawData.append("<prodOfferId>" +  objAPStockModelBeanBO.getProductOfferingId() + "");
                rawData.append("</prodOfferId>");
                rawData.append("</listStockSerialBean>");
            }
        }

        // build thong tin hang hoa
        ArrayList<ProductOfferingDTO> arrApStockModelBeanBOs1 = new ArrayList<ProductOfferingDTO>();

        arrApStockModelBeanBOs1.addAll(mapSubStockModelRelReq.values());

        if (arrApStockModelBeanBOs1.size() > 0) {
            // rawData.append("<lsSubGoodsDTO>");
            for (ProductOfferingDTO item : arrApStockModelBeanBOs1) {
                rawData.append("<lsSubGoodsDTO>");
                rawData.append("<stockModelId>" + item.getProductOfferingId() + "");
                rawData.append("</stockModelId>");
                rawData.append("<serial>" + "" + item.getSerial());
                rawData.append("</serial>");
                rawData.append("<stockTypeId>" + "" + item.getProductOfferTypeId());
                rawData.append("</stockTypeId>");

                rawData.append("<stockModelName>" + "" + item.getName());
                rawData.append("</stockModelName>");

                if (ActivityCreateNewRequestMobileNew.instance != null && !CommonActivity
                        .isNullOrEmpty(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                    if (Constant.CHANGE_PRE_TO_POS
                            .equals(ActivityCreateNewRequestMobileNew.instance.funtionType)) {
                        rawData.append("<actionCode>" + "220");
                        rawData.append("</actionCode>");
                    } else {
                        rawData.append("<actionCode>" + "221");
                        rawData.append("</actionCode>");
                    }
                } else {
                    rawData.append("<actionCode>" + "00");
                    rawData.append("</actionCode>");
                }

                rawData.append("</lsSubGoodsDTO>");
            }
            // rawData.append("</lsSubGoodsDTO>");
        }

        rawData.append("<saleServicesCode>" + saleServiceCode);
        rawData.append("</saleServicesCode>");

        // truyen thong tin dia ban cho cuoc dong truoc
        if ("1".equals(subType)) {
            rawData.append("<areaCode>" + Session.province);
            rawData.append("</areaCode>");
        }

        if (pakaChargeBeans.getLstProductSpecCharDTOs() != null
                && pakaChargeBeans.getLstProductSpecCharDTOs().size() > 0) {

            // for (ProductSpecCharDTO item :
            // pakaChargeBeans.getLstProductSpecCharDTOs()) {
            if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)) {
                rawData.append("<productSpecInfoDTO>");

                rawData.append("<productCode>" + productCode + "</productCode>");

                rawData.append("<showProdSpecInfo>" + true + "</showProdSpecInfo>");

                rawData.append("<status>" + 1 + "</status>");

                rawData.append("<idNo>" + custIdentityDTO.getIdNo() + "</idNo>");
                rawData.append("<departmentCode>" + mCodeDonVi + "</departmentCode>");
                rawData.append(
                        "<endDatetime>" + StringUtils.convertDateToString(edtNgayKT.getText().toString().trim())
                                + "T00:00:00+07:00" + "</endDatetime>");
                rawData.append("<nationCode>" + "VIE" + "</nationCode>");
                rawData.append("<objectCode>" + mCodeDoiTuong + "</objectCode>");
                rawData.append("<orderNumber>" + edtMaGiayToDacBiet.getText().toString() + "</orderNumber>");
                rawData.append("<startDatetime>"
                        + StringUtils.convertDateToString(edtNgayBD.getText().toString().trim()) + "T00:00:00+07:00"
                        + "</startDatetime>");
                rawData.append("</productSpecInfoDTO>");
            }
            if ("REG_SPECIAL".equals(checkSpecRegtype)) {
                rawData.append("<productSpecInfoDTO>");

                rawData.append("<productCode>" + hthm + "</productCode>");

                rawData.append("<showProdSpecInfo>" + true + "</showProdSpecInfo>");

                rawData.append("<status>" + 1 + "</status>");

                rawData.append("<idNo>" + custIdentityDTO.getIdNo() + "</idNo>");
                rawData.append("<departmentCode>" + mCodeDonVi + "</departmentCode>");
                rawData.append(
                        "<endDatetime>" + StringUtils.convertDateToString(edtNgayKT.getText().toString().trim())
                                + "T00:00:00+07:00" + "</endDatetime>");
                rawData.append("<nationCode>" + "VIE" + "</nationCode>");
                rawData.append("<objectCode>" + mCodeDoiTuong + "</objectCode>");
                rawData.append("<orderNumber>" + edtMaGiayToDacBiet.getText().toString() + "</orderNumber>");
                rawData.append("<startDatetime>"
                        + StringUtils.convertDateToString(edtNgayBD.getText().toString().trim()) + "T00:00:00+07:00"
                        + "</startDatetime>");
                rawData.append("</productSpecInfoDTO>");
            }

            // dau noi goi hoc sinh
            if ("SPEC_HISCL".equals(checkIsSpec)) {
                rawData.append("<productSpecInfoDTO>");
                rawData.append("<productCode>" + productCode + "</productCode>");

                rawData.append("<showProdSpecInfo>" + true + "</showProdSpecInfo>");

                rawData.append("<status>" + 1 + "</status>");

                rawData.append("<idNo>" + custIdentityDTO.getIdNo() + "</idNo>");
                rawData.append(
                        "<subDob>" + StringUtils.convertDateToString(edtngaysinhhs.getText().toString().trim())
                                + "T00:00:00+07:00" + "</subDob>");
                rawData.append("<subName>" + edttenhs.getText().toString().trim() + "</subName>");
                rawData.append("</productSpecInfoDTO>");
            }

            if ("SPEC_ELDER".equals(checkIsSpec)) {
                rawData.append("<productSpecInfoDTO>");
                rawData.append("<productCode>" + productCode + "</productCode>");

                rawData.append("<showProdSpecInfo>" + true + "</showProdSpecInfo>");

                rawData.append("<status>" + 1 + "</status>");

                rawData.append("<idNo>" + custIdentityDTO.getIdNo() + "</idNo>");

                rawData.append("</productSpecInfoDTO>");
            }

        }
        // }

        // truyen thong tin OTPCODE doi voi truong hop vtshop
        rawData.append("<otpCode>" + custIdentityDTO.getIdNo() + "</otpCode>");

        if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec) || "SPEC_HISCL".equals(checkIsSpec)
                || "SPEC_ELDER".equals(checkIsSpec) || "REG_SPECIAL".equals(checkSpecRegtype)) {

            rawData.append("<isSpecialProduct>" + true + "</isSpecialProduct>");
        }
        return rawData.toString();
    }
    private class OnPostGetInfoCustomer implements OnPostExecuteListener<ArrayList<SpinV2>> {

        @Override
        public void onPostExecute(ArrayList<SpinV2> result, String errorCode, String description) {

            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrCustomerInfo = result;
                SpinV2 spin = new SpinV2();
                spin.setValue(activity.getString(R.string.chondoituong));
                arrCustomerInfo.add(0, spin);
                Utils.setDataSpinner(getActivity(),arrCustomerInfo,spinnerCustomerInfo);
            } else {
                CommonActivity.createAlertDialog(activity,
                        getString(R.string.doituong_null),
                        getString(R.string.app_name));
                return;
            }
        }
    }

    private void reloadCustType(){
        String groupType=null;
        if(!CommonActivity.isNullOrEmpty(custIdentityDTO.getGroupType()))
        {
            groupType = custIdentityDTO.getGroupType(); //KH moi
        }else {
            groupType = custIdentityDTO.getCustomer().getCustTypeDTO().getGroupType(); //KH cu
        }
        String code = "";
        if ("1".equals(groupType) || "3".equals(groupType) ){
            code = "INDIVIDUAL";
        }
        else if ("2".equals(groupType)){
            code = "BUSINESS";
        }
        AsyncGetOptionSetValueV2 asyncGetOptionSetValue = new AsyncGetOptionSetValueV2(getActivity(), new OnPostGetInfoCustomer(), moveLogInAct);
        asyncGetOptionSetValue.execute(code);
    }


}
