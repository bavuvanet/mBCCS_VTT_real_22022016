package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asynctask.AsyncGetAllListRecordProfile;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValueV2;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.DoUHaveMoreThan3SubAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentInfoCustomerMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.manage.AsyncTaskUpdateImageOfline;
import com.viettel.bss.viettelpos.v4.customer.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SpinV2;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.ChooseFileDialogFragment;
import com.viettel.bss.viettelpos.v4.listener.OnClickListener;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetCustomerByCustId;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.task.AsynTaskZipFile;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.bss.viettelpos.v4.work.object.Action;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by thinhhq1 on 5/4/2017.
 */

public class FragmentEditCustomerBCCS extends FragmentCommon {
    @BindView(R.id.lntitle)
    LinearLayout lntitleEditCus;
    @BindView(R.id.txtinfo)
    TextView txtinfoEditCus;
    @BindView(R.id.viewhead)
    View viewheadEditCus;
    @BindView(R.id.edtloaikh)
    EditText edtloaikhEditCus;
    @BindView(R.id.edit_tenKH)
    EditText edit_tenKHEditCus;
    @BindView(R.id.edit_ngaysinh)
    EditText edit_ngaysinhEditCus;
    @BindView(R.id.spinner_gioitinh)
    Spinner spinnerGioitinhEditCus;
    @BindView(R.id.ln_sex)
    LinearLayout lnSexEditCus;
    @BindView(R.id.spinner_type_giay_to)
    Spinner spinnerTypeGiayToEditCus;
    @BindView(R.id.prbtypePaperEdit)
    ProgressBar prbtypePaperEditEditCus;
    @BindView(R.id.lnsogiayto)
    LinearLayout lnsogiaytoEditCus;
    @BindView(R.id.edit_socmnd)
    EditText edit_socmndEditCus;
    @BindView(R.id.linearCMT)
    LinearLayout linearCMTEditCus;
    @BindView(R.id.edit_soGQ)
    EditText editSoGQEditCus;
    @BindView(R.id.linearsoGPKD)
    LinearLayout linearsoGPKDEditCus;
    @BindView(R.id.edt_tin)
    EditText edtTinEditCus;
    @BindView(R.id.ln_tin)
    LinearLayout lnTinEditCus;
    @BindView(R.id.edit_ngaycap)
    EditText edit_ngaycapEditCus;
    @BindView(R.id.lnngaycapnew)
    LinearLayout lnngaycapEditCus;
    @BindView(R.id.edit_ngayhethan)
    EditText editNgayhethanEditCus;
    @BindView(R.id.lnngayhethan)
    LinearLayout lnngayhethanEditCus;
    @BindView(R.id.edit_noicap)
    EditText editNoicapEditCus;
    @BindView(R.id.lnnoicapnew)
    LinearLayout lnnoicapEditCus;
    @BindView(R.id.edtprovince)
    EditText edtprovinceEditCus;
    @BindView(R.id.edtdistrict)
    EditText edtdistrictEditCus;
    @BindView(R.id.edtprecinct)
    EditText edtprecinctEditCus;
    @BindView(R.id.edtStreetBlock)
    EditText edtStreetBlockEditCus;
    @BindView(R.id.btnRefreshStreetBlock)
    Button btnRefreshStreetBlockEditCus;
    @BindView(R.id.edt_streetName)
    EditText edtStreetNameEditCus;
    @BindView(R.id.edtHomeNumber)
    EditText edtHomeNumberEditCus;
    @BindView(R.id.spinner_quoctichnew)
    Spinner spinnerQuoctichEditCus;
    @BindView(R.id.lnquoctichdialog)
    LinearLayout lnquoctichdialog;
    @BindView(R.id.edit_note)
    EditText editNote;
    @BindView(R.id.spn_reason)
    Spinner spnReason;
    @BindView(R.id.prbreason)
    ProgressBar prbreason;
    @BindView(R.id.edtOTPIsdn)
    EditText edtOTPIsdn;
    @BindView(R.id.btnSendOTP)
    Button btnSendOTP;
    @BindView(R.id.prbreasonBtn)
    ProgressBar prbreasonBtn;
    @BindView(R.id.edtOTPCode)
    EditText edtOTPCode;
    @BindView(R.id.lnOTP)
    LinearLayout lnOTP;
    @BindView(R.id.lnotpChangeCus)
    LinearLayout lnotpChangeCus;
    @BindView(R.id.btnsuadoi)
    Button btnsuadoi;
    @BindView(R.id.btncancel)
    Button btncancel;
    @BindView(R.id.lnButton)
    LinearLayout lnButton;
    @BindView(R.id.lnhoso)
    LinearLayout lnhoso;
    @BindView(R.id.lvUploadImage)
    ListView lvUploadImage;
    Unbinder unbinder;
    @BindView(R.id.spnDoituong)
    Spinner spnDoituong;



    private CustomerDTO customerDTO;
    private String isPSenTdO;
    private String subType;
    private Boolean ignoreOTP = false;

    private String typePaperEditCus;
    private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<>();
    // arrlist province
    private ArrayList<AreaBean> arrProvince = new ArrayList<>();
    private String province = "";
    // arrlist district
    private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
    private String district = "";
    // arrlist precinct
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
    private String precinct = "";

    private String provinceEdit = "";
    private String districtEdit = "";
    private String precinctEdit = "";
    private String streetBlockEdit = "";
    private ArrayList<SexBeans> arrSexBeans = new ArrayList<>();

    private List<ReasonDTO> arrReasonChangeCus = new ArrayList<ReasonDTO>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Date dateNow;
    private String dateNowString;

    private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<String, ArrayList<FileObj>>();
    private HashMap<String, ArrayList<FileObj>> hashmapFileObjDuplicate = new HashMap<String, ArrayList<FileObj>>();
    private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;
    private String functionKey = "";
    private SubscriberDTO subscriberDTO;
    private ReasonDTO reasonDTO;
    private String custId;
    @BindView(R.id.lnSelectProfile)
    LinearLayout lnSelectProfile;
    ChooseFileDialogFragment fragment;
    @BindView(R.id.thumbnails)
    LinearLayout thumbnails;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    LayoutInflater inflater;
    ProfileBO profileBO = new ProfileBO();
    List<Object> lstReasonId = new ArrayList<>();
    List<Object> lstActionCode = new ArrayList<>();
    private String TDTTCVT = "TDTTCVT";
    private String type = "";
    private String menu_vsa = "";

    public static ArrayList<Spin> arrMapusage = new ArrayList<>();
    public String typeMapusage = "";
    public boolean isMoreThan = false;
    private String descriptionNotice = "";
    private   ArrayList<SpinV2> arrDoituong = new ArrayList<>();
    private  String subObject = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.connectionmobile_layout_edit_customer_bccs2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            Log.d(Constant.TAG, "error", e);
        }
        String fromMothStr;
        if (fromMonth < 10) {
            fromMothStr = "0" + fromMonth;
        } else {
            fromMothStr = "" + fromMonth;
        }

        String fromDayStr;
        if (fromDay < 10) {
            fromDayStr = "0" + fromDay;
        } else {
            fromDayStr = "" + fromDay;
        }

        dateNowString = fromDayStr + "/" + fromMothStr + "/" + fromYear + "";

//        idLayout = R.layout.connectionmobile_layout_edit_customer_bccs2;
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

//        getDataBundle();
//        process();
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void getDataBundle() {
        Bundle mBundle = getArguments();
        customerDTO = (CustomerDTO) mBundle.getSerializable("customerDTO");
        isPSenTdO = mBundle.getString("isPSenTdO");
        subType = mBundle.getString("subType");
        functionKey = mBundle.getString("functionKey", "");
        subscriberDTO = (SubscriberDTO) mBundle.getSerializable("subscriberDTO");
        if (subscriberDTO == null) {
            subscriberDTO = new SubscriberDTO();
        }
        custId = mBundle.getString("custId");
        type = mBundle.getString("type", "");
        ignoreOTP = mBundle.getBoolean("ignoreOTP", false);
        if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {
            edtOTPIsdn.setText(subscriberDTO.getIsdn());
        }
    }

    private void process() {


        if(CommonActivity.isNullOrEmptyArray(arrDoituong)){
            if(!CommonActivity.isNullOrEmpty(customerDTO) && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO())){
                String groupType = customerDTO.getCustTypeDTO().getGroupType();

                String code = "";
                if ("1".equals(groupType) || "3".equals(groupType)){
                    code = "INDIVIDUAL";
                }
                else if ("2".equals(groupType)){
                    code = "BUSINESS";
                }
                AsyncGetOptionSetValueV2 asyncGetOptionSetValue = new AsyncGetOptionSetValueV2(getActivity(), new OnPostGetInfoCustomer(), moveLogInAct);
                asyncGetOptionSetValue.execute(code);
            }
        }

        spnDoituong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinV2 itemAtPosition = (SpinV2) parent.getItemAtPosition(position);
                if(!itemAtPosition.getValue().equals(act.getString(R.string.chondoituong))) {
                    subObject = itemAtPosition.getId();
                }else{
                    subObject = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (CommonActivity.isNullOrEmpty(menu_vsa)) {
            SharedPreferences preferences = getActivity().getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);

            menu_vsa = preferences.getString(Define.KEY_MENU_NAME, "");
        }

        if (!CommonActivity.isNullOrEmpty(customerDTO)
                && !CommonActivity.isNullOrEmpty(customerDTO.getIsPSenTdO())) {
            isPSenTdO = customerDTO.getIsPSenTdO();
            Log.d("isPSenTdO", "=>>>>>>>>>>>>>>>>>>>> isPSenTdO = " + isPSenTdO);
        }

        if (subscriberDTO != null && subscriberDTO.getTelecomServiceId() != null
                && subscriberDTO.getTelecomServiceId().compareTo(2L) == 0) {
            isPSenTdO = "false";
        }
        lntitleEditCus.setVisibility(View.GONE);
        if ("true".equals(isPSenTdO)) {
            lnotpChangeCus.setVisibility(View.VISIBLE);
        } else {
            lnotpChangeCus.setVisibility(View.GONE);
        }

        spinnerTypeGiayToEditCus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typePaperEditCus = arrTypePaper.get(position).getIdType();
                lnngayhethanEditCus.setVisibility(View.GONE);
                AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(), new OnPostGetOptionSetValue(), moveLogInAct);
                asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
                if ("ID".equals(typePaperEditCus)) {
                    edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int maxLength = 12;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmndEditCus.setFilters(FilterArray);
                } else if ("MID".equals(typePaperEditCus)) {
                    edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 12;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmndEditCus.setFilters(FilterArray);
                } else if ("IDC".equals(typePaperEditCus)) {
                    edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int maxLength = 15;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmndEditCus.setFilters(FilterArray);
                }
                if ("PASS".equals(typePaperEditCus)) {
                    // truong hop ho chieu va nhom ca nhan nuoc ngoai thi dia chi an di ko can nhap
//                        if(!CommonActivity.isNullOrEmpty(cus) && "3".equals(custTypeDTODialog.getCusGroup())){
//                            lnAddressCusNew.setVisibility(View.GONE);
//                        }else{
//                            lnAddressCusNew.setVisibility(View.VISIBLE);
//                        }
                    edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 20;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmndEditCus.setFilters(FilterArray);
                } else {
                    edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 20;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmndEditCus.setFilters(FilterArray);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (Constant.GROUP_TYPE.KH_DN.equals(customerDTO.getCustTypeDTO().getGroupType())) {
            spinnerTypeGiayToEditCus.setEnabled(false);
        }

        edtloaikhEditCus.setEnabled(false);

        if (!CommonActivity.isNullOrEmpty(customerDTO) && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO())) {
            GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(getActivity());
            getMappingChannelCustTypeAsyn.execute("1");
        }


        // lay thong tin loai giay to
        GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                getActivity(), prbtypePaperEditEditCus, null, spinnerTypeGiayToEditCus, customerDTO);
        getMappingCustIdentityUsageAsyn.execute(customerDTO.getCustTypeDTO().getCustType());
        prbtypePaperEditEditCus.setVisibility(View.GONE);
        edit_tenKHEditCus.setText(customerDTO.getName());
        edit_ngaysinhEditCus.setText(StringUtils.convertDate(customerDTO.getBirthDate()));
        edit_ngaysinhEditCus.setOnClickListener(editTextListener);
        edit_socmndEditCus.setEnabled(false);
        edit_socmndEditCus.setText(customerDTO.getListCustIdentity().get(0).getIdNo());

        edit_ngaycapEditCus.setText(StringUtils.convertDate(customerDTO.getListCustIdentity().get(0).getIdIssueDate()));
        edit_ngaycapEditCus.setOnClickListener(editTextListener);
        editNoicapEditCus.setText(customerDTO.getListCustIdentity().get(0).getIdIssuePlace());

        initProvince();
        if (!CommonActivity.isNullOrEmpty(customerDTO.getProvince())) {
            initDistrict(customerDTO.getProvince());
            try {
                GetAreaDal dal = new GetAreaDal(getActivity());
                dal.open();
                edtprovinceEditCus.setText(dal.getNameProvince(customerDTO.getProvince()));
                provinceEdit = customerDTO.getProvince();
                dal.close();
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
            }
        }
        edtprovinceEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                intent.putExtra("arrProvincesKey", arrProvince);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "1");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 115);
            }
        });
        if (!CommonActivity.isNullOrEmpty(customerDTO.getProvince()) && !CommonActivity.isNullOrEmpty(customerDTO.getDistrict())) {
            initPrecinct(customerDTO.getProvince(), customerDTO.getDistrict());
            try {
                GetAreaDal dal = new GetAreaDal(getActivity());
                dal.open();
                edtdistrictEditCus.setText(dal.getNameDistrict(customerDTO.getProvince(), customerDTO.getDistrict()));
                districtEdit = customerDTO.getDistrict();
                dal.close();
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
            }
        }
        edtdistrictEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                intent.putExtra("arrDistrictKey", arrDistrict);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "2");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 116);
            }
        });


        if (!CommonActivity.isNullOrEmpty(customerDTO.getProvince()) && !CommonActivity.isNullOrEmpty(customerDTO.getDistrict()) && !CommonActivity.isNullOrEmpty(customerDTO.getPrecinct())) {
            try {
                GetAreaDal dal = new GetAreaDal(getActivity());
                dal.open();
                edtprecinctEditCus.setText(dal.getNamePrecint(customerDTO.getProvince(), customerDTO.getDistrict(), customerDTO.getPrecinct()));
                precinctEdit = customerDTO.getPrecinct();
                dal.close();
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
            }
        }
        edtprecinctEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                intent.putExtra("arrPrecinctKey", arrPrecinct);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "3");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 117);
            }
        });

        streetBlockEdit = customerDTO.getStreetBlock();
        if (!CommonActivity.isNullOrEmpty(customerDTO.getStreetBlockName())) {
            edtStreetBlockEditCus.setText(customerDTO.getStreetBlockName());
        } else {
            edtStreetBlockEditCus.setText(customerDTO.getStreetBlock());
        }

        edtStreetBlockEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "4");
                mBundle.putString("province", provinceEdit);
                mBundle.putString("district", districtEdit);
                mBundle.putString("precinct", precinctEdit);
                intent.putExtras(mBundle);
                startActivityForResult(intent, 118);
            }
        });
        edtStreetNameEditCus.setText(customerDTO.getStreetName());
        edtHomeNumberEditCus.setText(customerDTO.getHome());
        editNote.setText(customerDTO.getDescription());

        initNationlyChangeCus(spinnerQuoctichEditCus, customerDTO.getNationality());

        if (!CommonActivity.isNullOrEmptyArray(arrSexBeans)) {
            arrSexBeans = new ArrayList<SexBeans>();
        }

        initSex(spinnerGioitinhEditCus);

        if (!CommonActivity.isNullOrEmpty(arrSexBeans) && !CommonActivity.isNullOrEmpty(customerDTO.getSex())) {
            for (SexBeans item : arrSexBeans) {
                if (item.getValues().equals(customerDTO.getSex())) {
                    spinnerGioitinhEditCus.setSelection(arrSexBeans.indexOf(item));
                    break;
                }
            }
        }

        GetReasonFullPM getReasonFullPM = new GetReasonFullPM(getActivity(), prbreason, Constant.ACTION_CODE.UPDATE_CUSTOMER);
        getReasonFullPM.execute(customerDTO.getCustTypeDTO().getCustType());


        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonActivity.isNullOrEmpty(edtOTPIsdn.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateIsdn), getActivity().getString(R.string.app_name)).show();
                } else {
                    View.OnClickListener confirm = new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            SendOTPUtilAsyn sendOTPUtilAsyn = new SendOTPUtilAsyn(getActivity());
                            sendOTPUtilAsyn.execute(edtOTPIsdn.getText().toString().trim());
                        }
                    };
                    CommonActivity
                            .createDialog(getActivity(), getActivity().getString(R.string.sendotpds, edtOTPIsdn.getText().toString().trim()),
                                    getActivity().getString(R.string.app_name), getActivity().getString(R.string.OK),
                                    getActivity().getString(R.string.cancel), confirm, null)
                            .show();

                }


            }
        });


        btnsuadoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateEditCus(isPSenTdO)) {
                        View.OnClickListener onclick = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DoUpdateCustomerV1Asyn doUpdateCustomerV1Asyn = new DoUpdateCustomerV1Asyn(getActivity());
                                doUpdateCustomerV1Asyn.execute();

                            }
                        };
                        CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.confirmChangeCus),
                                getActivity().getString(R.string.app_name),
                                getActivity().getString(R.string.cancel), getActivity().getString(R.string.ok), null, onclick).show();
                    }
                } catch (Exception e) {
                    Log.d(Constant.TAG, "error", e);
                }


            }
        });

        Log.d(Constant.TAG, "custId = " + (custId == null ? "null" : custId));
//        btnsuadoi.setVisibility(View.GONE);
        if (!CommonActivity.isNullOrEmpty(customerDTO)) {
//            if (menu_vsa.contains(Constant.VSAMenu.PERMISSION_EDIT_CUSTOMER_OWNER)){
//                if(Session.userName.equalsIgnoreCase(customerDTO.getCreateUser())){
//                    btnsuadoi.setVisibility(View.VISIBLE);
//                } else {
//                    txtinfoEditCus.setText(getString(R.string.notPermissionEditCustomer));
//                    txtinfoEditCus.setTextColor(getResources().getColor(R.color.red));
//                    CommonActivity.toast(getActivity(), getString(R.string.notPermissionEditCustomer));
//                }
//            } else {
//                txtinfoEditCus.setText(getString(R.string.notPermissionEditCustomer));
//                CommonActivity.toast(getActivity(), getString(R.string.notPermissionEditCustomer));
//            }

            //truong hop khach hang doanh nghiep khong cho phep sua
            if (Constant.GROUP_TYPE.KH_DN.equals(customerDTO.getGroupType())) {
                txtinfoEditCus.setText(getString(R.string.notPermissionEditKHDN));
                txtinfoEditCus.setTextColor(getResources().getColor(R.color.red));
                btnsuadoi.setVisibility(View.GONE);
            }
        }


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        if ("FMP_EDT_CUST".equals(type)) {
            btncancel.setVisibility(View.GONE);
        }

        if (!Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER.equals(functionKey)) {
            lnhoso.setVisibility(View.GONE);
        }

        spnReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lnSelectProfile.setVisibility(View.GONE);
                if (Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER.equals(functionKey)) {
                    if (position == 0) {
                        return;
                    }

                    profileBO = new ProfileBO();
                    lstReasonId = new ArrayList<Object>();
                    lstActionCode = new ArrayList<Object>();

                    reasonDTO = (ReasonDTO) spnReason
                            .getSelectedItem();
                    lstReasonId.add(reasonDTO.getReasonId());
                    lstActionCode.add(Constant.ACTION_CODE.UPDATE_CUSTOMER);

                    new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                        @Override
                        public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
                            if (CommonActivity.isNullOrEmpty(result)) {
                                lnSelectProfile.setVisibility(View.GONE);
                            } else {
                                lnSelectProfile.setVisibility(View.VISIBLE);
                                profileBO.setMapListRecordPrepaid(result);
                            }
                        }
                    }, null, initProfileBO()).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lnSelectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonActivity.isNullOrEmpty(lstReasonId)) {
                    CommonActivity.toast(getActivity(), R.string.chua_chon_ly_do_dang_ky);
                    return;
                }
                if (!CommonActivity.isNullOrEmpty(profileBO)
                        && !CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())) {
                    fragment = ChooseFileDialogFragment.newInstance(profileBO);
                    fragment.setOnFinishDSFListener(onFinishDSFListener);
                    fragment.show(getFragmentManager(), "Choose file");
                }
            }
        });

//        if ("2".equals(subType) && customerDTO != null && !"2".equals(customerDTO.getGroupType())) {
//            DoUHaveMoreThan3SubAsyn doUHaveMoreThan3SubAsyn = new DoUHaveMoreThan3SubAsyn(getActivity(), new OnPostDoUHaveMorethan(), moveLogInAct);
//            doUHaveMoreThan3SubAsyn.execute(customerDTO.getListCustIdentity().get(0).getIdNo() + "", customerDTO.getListCustIdentity().get(0).getIdType() + "");
//
//        }
    }

    private class OnPostDoUHaveMorethan implements OnPostExecuteListener<ParseOuput> {
        @Override
        public void onPostExecute(ParseOuput result, String errorCode, String description) {
            isMoreThan = false;
            descriptionNotice = "";
            if (result != null) {
                isMoreThan = result.isMoreThan();
                descriptionNotice = description;
            }
        }
    }

    private ProfileBO initProfileBO() {
        horizontalScrollView.setVisibility(View.GONE);
        profileBO.setLstActionCode(lstActionCode);
        profileBO.setLstReasonId(lstReasonId);
        profileBO.setParValue(Constant.POSTPAID.equals(subscriberDTO.getPayType()) ? subscriberDTO.getSubType() : subscriberDTO.getProductCode());
        profileBO.setPayType(subscriberDTO.getPayType());
        String cusType = subscriberDTO.getCustomerDTOInput().getCustType();
        profileBO.setCustType(cusType);
        profileBO.setServiceType("M");
        profileBO.setIdNo(edit_socmndEditCus.getText().toString().trim());
        return profileBO;
    }


    OnFinishDSFListener onFinishDSFListener = new OnFinishDSFListener() {
        @Override
        public void onFinish(ProfileBO output) {
            profileBO = output;
            if (profileBO != null
                    && profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty()) {
                thumbnails.removeAllViews();
                horizontalScrollView.setVisibility(View.VISIBLE);

                ArrayList<String> lstData = new ArrayList<>();
                for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                    for (FileObj fileObj : entry.getValue()) {
                        Log.d("onFinishDSFListener", fileObj.getFullPath());
                        lstData.add(fileObj.getFullPath());
                    }
                }

                ImageUtils.loadImageHorizontal(getContext(), inflater, thumbnails, lstData, new OnClickListener() {
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

    private OnPostExecute<SaleOutput> onPostGetRecordCode = new OnPostExecute<com.viettel.bss.viettelpos.v4.customer.object.SaleOutput>() {

        @Override
        public void onPostExecute(
                com.viettel.bss.viettelpos.v4.customer.object.SaleOutput result) {
            if (!"0".equals(result.getErrorCode())) {
                String description = result.getDescription();
                if (CommonActivity.isNullOrEmpty(description)) {
                    description = getString(R.string.errorNetwork);
                }
                CommonActivity.createAlertDialog(getActivity(), description,
                        getString(R.string.app_name)).show();
            } else {
                // Tao spinner upload anh
                mapListRecordPrepaid = new HashMap<String, ArrayList<RecordPrepaid>>();
                List<FormBean> lstFormBean = result.getLstRecordProfile();
                if (!CommonActivity.isNullOrEmpty(lstFormBean)) {
                    hashmapFileObjDuplicate.clear();
                    hashmapFileObj.clear();
                    for (FormBean item : lstFormBean) {
                        String id = item.getId();
                        String code = item.getCode();
                        String name = item.getName();
                        String actions = item.getActions();
                        RecordPrepaid recordPrepaid = new RecordPrepaid(id,
                                code, name, actions);
                        if (mapListRecordPrepaid.containsKey(id)) {
                            ArrayList<RecordPrepaid> arrayList = mapListRecordPrepaid
                                    .get(id);
                            arrayList.add(recordPrepaid);
                            mapListRecordPrepaid.put(id, arrayList);
                        } else {
                            ArrayList<RecordPrepaid> arrayList = new ArrayList<RecordPrepaid>();
                            arrayList.add(recordPrepaid);
                            mapListRecordPrepaid.put(id, arrayList);
                        }
                    }
                    List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
                            mapListRecordPrepaid.values());
                    RecordPrepaidAdapter adapter = new RecordPrepaidAdapter(
                            getActivity(), arrayOfArrayList, imageListenner);
                    lvUploadImage.setAdapter(adapter);
                    UI.setListViewHeightBasedOnChildren(lvUploadImage);
                }

            }

        }
    };

    private void initNationlyChangeCus(Spinner spinquoctich, String nation) {
        try {
            BhldDAL dal = new BhldDAL(getActivity());
            dal.open();
            ArrayList<Spin> spinNation = dal.getNationaly();
            dal.close();
            Utils.setDataSpinner(getActivity(), spinNation, spinquoctich);

            if (!CommonActivity.isNullOrEmptyArray(spinNation)) {

                for (Spin spin : spinNation) {
                    if (nation.equals(spin.getId())) {
                        spinquoctich.setSelection(spinNation.indexOf(spin));
                        break;
                    } else {
                        if ("VN".equals(spin.getId())) {
                            spinquoctich.setSelection(spinNation.indexOf(spin));
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }

    }

    private void initProvince() {
        try {
            GetAreaDal dal = new GetAreaDal(getActivity());
            dal.open();
            arrProvince = dal.getLstProvince();
            dal.close();
        } catch (Exception ex) {
            Log.d(Constant.TAG, "error", ex);
        }
    }

    // lay huyen/quan theo tinh
    private void initDistrict(String province) {
        try {
            GetAreaDal dal = new GetAreaDal(getActivity());
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
            GetAreaDal dal = new GetAreaDal(getActivity());
            dal.open();
            arrPrecinct = dal.getLstPrecinct(province, district);
            dal.close();
        } catch (Exception ex) {
            Log.e("initPrecinct", ex.toString());
        }
    }

    private final View.OnClickListener editTextListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            EditText edt = (EditText) view;
            Calendar cal = Calendar.getInstance();
            if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
                Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");

                cal.setTime(date);

            }
            DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            pic.getDatePicker().setTag(view);
            pic.show();
        }
    };
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            Object obj = view.getTag();
            if (obj != null && obj instanceof EditText) {
                EditText editText = (EditText) obj;
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

                editText.setText(day + "/" + month + "/" + selectedYear);

                int id = editText.getId();

                Calendar cal = Calendar.getInstance();
                cal.set(selectedYear, selectedMonth, selectedDay);
                Date date = cal.getTime();

            }
        }
    };

    @Override
    protected void unit(View v) {
        inflater = LayoutInflater.from(getActivity());
        editNgayhethanEditCus.setOnClickListener(editTextListener);
        getDataBundle();
        if (CommonActivity.isNullOrEmpty(custId)) {
            process();
        } else {
            new AsynTaskGetCustomerByCustId(getActivity(), new OnPostExecuteListener<CustomerDTO>() {
                @Override
                public void onPostExecute(CustomerDTO result, String errorCode, String description) {
                    customerDTO = result;
                    isPSenTdO = customerDTO.getIsPSenTdO();
                    Log.d("isPSenTdO", "isPSenTdO = " + isPSenTdO);

                    process();
                }
            }, moveLogInAct, custId, "").execute();
        }

        if(!CommonActivity.isNullOrEmpty(customerDTO)
                && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO())){
            String groupType = customerDTO.getCustTypeDTO().getGroupType();

            String code = "";
            if ("1".equals(groupType) || "3".equals(groupType)){
                code = "INDIVIDUAL";
            }
            else if ("2".equals(groupType)){
                code = "BUSINESS";
            }
            AsyncGetOptionSetValueV2 asyncGetOptionSetValue = new AsyncGetOptionSetValueV2(getActivity(), new OnPostGetInfoCustomer(), moveLogInAct);
            asyncGetOptionSetValue.execute(code);





        }
    }

    private View.OnClickListener imageListenner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(Constant.TAG, "view.getId() : " + view.getId());
            ImagePreviewActivity.pickImage(getActivity(), hashmapFileObj, view.getId());
        }
    };

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER.equals(functionKey)) {
            setTitleActionBar(R.string.cus_management);
        } else {
            setTitleActionBar(getActivity().getString(R.string.editcuscommon));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 115:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");

                        provinceEdit = areaBean.getProvince();
                        initDistrict(provinceEdit);
                        edtprovinceEditCus.setText(areaBean.getNameProvince());
                        edtdistrictEditCus.setText("");
                        edtprecinctEditCus.setText("");
                        edtStreetBlockEditCus.setText("");
                        districtEdit = "";
                        precinctEdit = "";
                        streetBlockEdit = "";
                    }
                    break;
                case 116:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
                        districtEdit = areaBean.getDistrict();
                        initPrecinct(provinceEdit, districtEdit);
                        edtdistrictEditCus.setText(areaBean.getNameDistrict());
                        edtprecinctEditCus.setText("");
                        edtStreetBlockEditCus.setText("");
                        precinctEdit = "";
                        streetBlockEdit = "";
                    }
                    break;

                case 117:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("precinctKey");
                        precinctEdit = areaBean.getPrecinct();
                        edtprecinctEditCus.setText(areaBean.getNamePrecint());
                        edtStreetBlockEditCus.setText("");
                        streetBlockEdit = "";
                    }
                    break;
                case 118:
                    if (data != null) {
                        AreaObj streetBlockKey = (AreaObj) data.getExtras().getSerializable("streetBlockKey");
                        streetBlockEdit = streetBlockKey.getAreaCode();
                        edtStreetBlockEditCus.setText(streetBlockKey.getName());

                    }
                    break;
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");
                    fragment.onActivityResult(requestCode, resultCode, data);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper, CustIdentityDTO custIdentityDTOExt) {
        if (lstTypePaper == null) {
            lstTypePaper = new ArrayList<>();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1);
        for (CustIdentityDTO custIdentityDTO : lstTypePaper) {
            adapter.add(custIdentityDTO.getIdTypeName());
        }
        spinerPaper.setAdapter(adapter);
        // }

        if (!CommonActivity.isNullOrEmptyArray(lstTypePaper) && !CommonActivity.isNullOrEmpty(custIdentityDTOExt)) {
            for (CustIdentityDTO cusItem : lstTypePaper) {
                if (cusItem.getIdType().equals(custIdentityDTOExt.getIdType())) {
                    spinerPaper.setSelection(lstTypePaper.indexOf(cusItem));
                    break;
                }
            }
        }

    }

    private class GetMappingCustIdentityUsageAsyn extends AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";
        final ProgressBar prbarCus;
        final Button btnRefresh;
        final Spinner spinerPaper;
        CustomerDTO custIdentityDTO;

        public GetMappingCustIdentityUsageAsyn(Context context, ProgressBar prb, Button btnres, Spinner spin, CustomerDTO
                custIdentityDTOEdit) {
            this.context = context;
            this.prbarCus = prb;
            this.btnRefresh = btnres;
            this.spinerPaper = spin;
            this.custIdentityDTO = custIdentityDTOEdit;
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
            if (!CommonActivity.isNullOrEmpty(prbtypePaperEditEditCus)) {
                prbtypePaperEditEditCus.setVisibility(View.GONE);
            }
            arrTypePaper = new ArrayList<>();
            if (!getActivity().isFinishing()) {
                if ("0".equals(errorCode)) {
                    // lay danh sach loai giay to
                    arrTypePaper = result;
                    initTypePaper(arrTypePaper, spinerPaper, custIdentityDTO.getListCustIdentity().get(0));
                    if (result != null && !result.isEmpty()) {
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                getResources().getString(R.string.notpapaer), getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                } else {
                    if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                        CommonActivity.BackFromLogin(getActivity(), description, ";cm.connect_sub_CD;");
                    } else {
                        if (description == null || "".equals(description)) {
                            description = context.getString(R.string.checkdes);
                        }
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                getResources().getString(R.string.app_name));
                        dialog.show();

                    }
                }
            }
        }

        private ArrayList<CustIdentityDTO> getMappingCustIdentityUsage(String currCusType) {
            ArrayList<CustIdentityDTO> lstTypePaper = new ArrayList<>();
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
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<currCustType>").append(currCusType);
                rawData.append("</currCustType>");
                rawData.append("</input>");
                rawData.append("</ws:mappingCustIdentityUsage>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
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

    private void initSex(Spinner spinnerSex) {
        arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SexBeans sexBeans : arrSexBeans) {
                adapter.add(sexBeans.getName());
            }
            spinnerSex.setAdapter(adapter);
        }
    }

    private class GetReasonFullPM extends AsyncTask<String, Void, List<ReasonDTO>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressBar progress;
        private String actionCode = "";

        public GetReasonFullPM(Context context, ProgressBar progressBar, String actioncode) {
            this.actionCode = actioncode;
            this.mContext = context;
            this.progress = progressBar;
            this.progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<ReasonDTO> doInBackground(String... arg0) {
            return getListReasonDTO(arg0[0]);
        }

        @Override
        protected void onPostExecute(List<ReasonDTO> result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);

//                if (Constant.ACTION_CODE.UPDATE_CUSTOMER.equals(actionCode)) {
            arrReasonChangeCus = new ArrayList<ReasonDTO>();
//                }
            try {


                if (!getActivity().isFinishing()) {
                    if ("0".equals(errorCode)) {
                        if (!CommonActivity.isNullOrEmpty(result) && !CommonActivity.isNullOrEmptyArray(result)) {
                            ReasonDTO item = new ReasonDTO();
                            item.setReasonId("");
                            item.setName(getActivity().getString(R.string.txt_select));
                            arrReasonChangeCus.add(0, item);

                            if (subscriberDTO != null && (subscriberDTO.getIgnoreOTPCTV() || subscriberDTO.isCustomerInfoInvalid())) {
                                List<ReasonDTO> lstReasonCVT = new ArrayList<>();
                                lstReasonCVT.add(item);

                                for (ReasonDTO reasonDTO : result) {
                                    if (reasonDTO.getLstCharacterCode() != null
                                            && ((subscriberDTO.getIgnoreOTPCTV()
                                            && reasonDTO.getLstCharacterCode().contains(Constant.PRODUCT_LYDOCVT))
                                            || (subscriberDTO.isCustomerInfoInvalid()
                                            && reasonDTO.getLstCharacterCode().contains(Constant.PRODUCT_LYDOSAITT)))) {

                                        lstReasonCVT.add(reasonDTO); //chi hien thi ly do cvt va sai thong tin
                                    }
                                }

                                arrReasonChangeCus.clear();
                                arrReasonChangeCus.addAll(lstReasonCVT);
                                Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spnReason);
                            } else {


                                for (int i= 0 ; i < result.size() ; i++){
                                    if (result.get(i).getLstCharacterCode() != null
                                            && (result.get(i).getLstCharacterCode().contains(Constant.PRODUCT_LYDOCVT)
                                            ||  result.get(i).getLstCharacterCode().contains(Constant.PRODUCT_LYDOSAITT))) {
                                        result.remove(i); //bo thuoc tinh ly do cvt va ly do sai thong tin
                                        i--;
                                    }
                                }
//                                for (ReasonDTO reasonDTO : result) {
//                                    if (reasonDTO.getLstCharacterCode() != null
//                                            && reasonDTO.getLstCharacterCode().contains(Constant.PRODUCT_LYDOCVT)) {
//                                        result.remove(reasonDTO); //bo thuoc tinh ly do cvt
//                                        break;
//                                    }
//                                }

                                arrReasonChangeCus.addAll(result);
                                Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spnReason);
                            }
                        } else {
                            if (Constant.ACTION_CODE.UPDATE_CUSTOMER.equals(actionCode)) {
                                arrReasonChangeCus = new ArrayList<ReasonDTO>();
                                Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spnReason);
                            }
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
                                    getActivity().getString(R.string.app_name)).show();
                        }
                    } else {
                        if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                            CommonActivity.BackFromLogin(getActivity(), description, ";cm.connect_sub_CD;");
                        } else {
                            if (description == null || description.isEmpty()) {
                                description = getActivity().getString(R.string.checkdes);
                            }
                            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                    getActivity().getResources().getString(R.string.app_name));
                            dialog.show();

                        }
                    }
                }
            }catch (Exception e){

            }
        }

        private List<ReasonDTO> getListReasonDTO(String cusType) {
            String original = null;
            List<ReasonDTO> lstReasonDTO = new ArrayList<ReasonDTO>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getReasonFullPMTemp");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonFullPM>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<payType>" + subType + "</payType>");
                // doi thong tin khach hang 152
                rawData.append("<actionCode>" + actionCode + "</actionCode>");
                rawData.append("<getReasonCharUse>true</getReasonCharUse>");
//                rawData.append("<serviceType>" + "M" + "</serviceType>");

                // thay doi thong tin khach hang


                rawData.append("<cusType>" + cusType + "</cusType>");


                rawData.append("</input>");
                rawData.append("</ws:getReasonFullPM>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getReasonFullPMTemp");
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
                }

                return lstReasonDTO;
            } catch (Exception e) {
                Log.d("getListSubscriber", e.toString());
            }

            return null;
        }
    }

    private class SendOTPUtilAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public SendOTPUtilAsyn(Context context) {
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
            return sendOtpUntil(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {

                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.confirmotpds, edtOTPIsdn.getText().toString().trim()),
                        getActivity().getString(R.string.app_name)).show();

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    CommonActivity.BackFromLogin(getActivity(), description, ";cm.connect_sub_CD;");
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

        private String sendOtpUntil(String isdn) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_sendOTPUtil4UpdateCustomer");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:sendOTPUtil4UpdateCustomer>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<custId>" + customerDTO.getCustId() + "</custId>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("<actionCode>" + Constant.ACTION_CODE.UPDATE_CUSTOMER + "</actionCode>");
                rawData.append("</input>");
                rawData.append("</ws:sendOTPUtil4UpdateCustomer>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_sendOTPUtil4UpdateCustomer");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("sendOtpUntil", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return errorCode;
        }
    }

    private boolean validateEditCus(String isPSenTdO) throws Exception {



        if (CommonActivity.isNullOrEmpty(edit_tenKHEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_ten_kh), getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (StringUtils.CheckCharSpecical(edit_tenKHEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (edit_tenKHEditCus.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.namecust), getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edit_ngaysinhEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.ngaysinhempty), getActivity().getString(R.string.app_name)).show();
            return false;
        }

        Date birthDate = sdf.parse(edit_ngaysinhEditCus.getText().toString());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.getYear(birthDate, new Date()) < 14) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.khdd14),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edit_socmndEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if ("ID".equals(typePaperEditCus)) {

            if (edit_socmndEditCus.getText().toString().length() != 9 && edit_socmndEditCus.getText().toString().length() != 12) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

        if (CommonActivity.isNullOrEmpty(edit_ngaycapEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        Date datengaycap = sdf.parse(edit_ngaycapEditCus.getText().toString());

        if (datengaycap.after(dateNow)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.ngaycapnhohonngayhientai),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (datengaycap.before(birthDate)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (!CommonActivity.isNullOrEmpty(arrMapusage)) {
            for (Spin spin : arrMapusage) {
                if (CommonActivity.checkMapUsage(typePaperEditCus, spin)) {
                    // truong hop ma bat buoc nhap nhat het han thi phai validate
                    if (CommonActivity.isNullOrEmpty(editNgayhethanEditCus.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateExpiredate),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    Date datenExpired = sdf.parse(editNgayhethanEditCus.getText().toString().trim());
                    if (datenExpired.before(datengaycap)) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanngaycap),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    break;
                }
            }
        }
        if (CommonActivity.isNullOrEmpty(editNoicapEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(provinceEdit)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_province), getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(districtEdit)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_distrist), getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(precinctEdit)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_precint), getActivity().getString(R.string.app_name)).show();
            return false;
        }
//        if (CommonActivity.isNullOrEmpty(streetBlockEdit)) {
//            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_group), getActivity().getString(R.string.app_name)).show();
//            return false;
//        }

        ReasonDTO reasonDTO = (ReasonDTO) spnReason.getSelectedItem();
        if (reasonDTO == null) {
            CommonActivity.createAlertDialog(getActivity(),
                    getActivity().getString(R.string.validate_reason_changecus),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getActivity().getString(R.string.validate_reason_changecus),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (!CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())
                && (fragment == null || !fragment.isFullFile())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.checkthieuanhhoso),
                    getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(subObject)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.doituong_not_select), getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if ("true".equals(isPSenTdO)) {
            if (CommonActivity.isNullOrEmpty(edtOTPIsdn.getText().toString().trim())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateIsdn), getActivity().getString(R.string.app_name)).show();
                return false;
            }


            if (CommonActivity.isNullOrEmpty(edtOTPCode.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateotp),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

//        if ("2".equals(subType) && isMoreThan) {
//            if (!validateProfileContract()) {
//                CommonActivity.createAlertDialog(getActivity(), descriptionNotice, getActivity().getString(R.string.app_name)).show();
//                return false;
//            }
//        }
        return true;
    }

    private boolean validateProfileContract() {
        if (profileBO != null && profileBO.getMapRecordSelected() != null) {
            for (RecordPrepaid recordPrepaid : profileBO.getMapRecordSelected().values()) {
                if ("HDMBTT".equals(recordPrepaid.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String fulladdress = "";

    // thay doi thong tin khach hang
    private class DoUpdateCustomerV1Asyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private ArrayList<String> lstFilePath = new ArrayList<String>();
        private ArrayList<String> lstRecordName = new ArrayList<String>();
        private ArrayList<String> lstString = new ArrayList<>();
        private String actionAuditId = "";

        public DoUpdateCustomerV1Asyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.processing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return doUpdateCus();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.progress.dismiss();
            // truong hop thanh cong khi cap nhat thong tin khach hang
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(lstFilePath)
                        && profileBO.getHashmapFileObjDuplicate() != null && profileBO.getHashmapFileObjDuplicate().size() > 0) {
                    new AsynTaskZipFile(getActivity(),
                            onPostExecuteListener,
                            moveLogInAct,
                            profileBO.getHashmapFileObjDuplicate(),
                            lstFilePath).execute();
                } else {

                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.changeCusSucess),
                            getActivity().getString(R.string.app_name), onclick).show();
                }


            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    CommonActivity.BackFromLogin(getActivity(), description, ";cm.connect_sub_CD;");
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

        OnPostExecuteListener<ArrayList<FileObj>> onPostExecuteListener = new OnPostExecuteListener<ArrayList<FileObj>>() {
            @Override
            public void onPostExecute(ArrayList<FileObj> result, String errorCode, String description) {
                if (Constant.RESPONSE_CODE.SUCCESS.equals(errorCode)) {
                    if (!CommonActivity.isNullOrEmpty(result)) {
                        for (FileObj fileObj : result) {
                            fileObj.setActionCode(Constant.ACTION_CODE.UPDATE_CUSTOMER);
                            fileObj.setReasonId(reasonDTO.getReasonId());
                            fileObj.setIsdn(subscriberDTO.getIsdn());
                            fileObj.setActionAudit(actionAuditId);
                            fileObj.setPageSize(0 + "");
                            fileObj.setStatus(0);
                        }
                        String des = getString(R.string.changeCusSucess);
                        new AsyncTaskUpdateImageOfline(getActivity(), result,
                                null, des + "\n").execute();
                    }
                }
            }
        };

        private void initBeforeUploadProfile() throws Exception {
            if (profileBO.getHashmapFileObjDuplicate() != null
                    && profileBO.getHashmapFileObjDuplicate().size() > 0) {
                FileUtils.createFilePaths(profileBO.getHashmapFileObjDuplicate(), lstFilePath, lstRecordName, lstString);
            }
        }

        private String doUpdateCus() {
            String original = null;
            try {
                initBeforeUploadProfile();

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_doUpdateCustomerV1");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:doUpdateCustomerV1>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");


                ReasonDTO reasonChangeCus = (ReasonDTO) spnReason.getSelectedItem();
                if (reasonChangeCus != null) {
                    rawData.append("<reasonIdUpdateCustomer>" + reasonChangeCus.getReasonId()
                            + "</reasonIdUpdateCustomer>");
                }


                rawData.append("<customerDTO>");
                rawData.append("<updateCustIdentity>" + true);
                rawData.append("</updateCustIdentity>");
                if (!CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                    rawData.append("<custId>" + "" + customerDTO.getCustId());
                    rawData.append("</custId>");
                }


                rawData.append("<name>" + "" + edit_tenKHEditCus.getText().toString().trim());
                rawData.append("</name>");
                rawData.append("<custType>" + "" + customerDTO.getCustType());
                rawData.append("</custType>");
                rawData.append("<custTypeDTO>");
                rawData.append("<custType>" + "" + customerDTO.getCustTypeDTO().getCustType());
                rawData.append("</custType>");
                rawData.append("<groupType>" + "" + customerDTO.getCustTypeDTO().getGroupType());
                rawData.append("</groupType>");
                rawData.append("</custTypeDTO>");


//                            CustIdentityDTO spinTypePayper = (CustIdentityDTO) spinner_type_giay_toEditCus.getSelectedItem();
                rawData.append("<listCustIdentity>");

                rawData.append("<idNo>" + "" + edit_socmndEditCus.getText().toString().trim());
                rawData.append("</idNo>");
                rawData.append("<idType>" + "" + typePaperEditCus);
                rawData.append("</idType>");
                rawData.append("<idIssueDate>" + ""
                        + StringUtils.convertDateToString(edit_ngaycapEditCus.getText().toString())
                        + "T00:00:00Z");
                rawData.append("</idIssueDate>");
                rawData.append("<idIssuePlace>" + "" + CommonActivity.getNormalText(editNoicapEditCus.getText().toString()));
                rawData.append("</idIssuePlace>");
                rawData.append("<required>" + true);
                rawData.append("</required>");
                if (!CommonActivity.isNullOrEmpty(editNgayhethanEditCus.getText().toString())) {
                    rawData.append("<idExpireDate>" + ""
                            + StringUtils.convertDateToString(editNgayhethanEditCus.getText().toString()) + "T00:00:00+07:00");
                    rawData.append("</idExpireDate>");
                }

                rawData.append("<status>" + "" + customerDTO.getListCustIdentity().get(0).getStatus());
                rawData.append("</status>");


                rawData.append("<createuser>" + "" + customerDTO.getListCustIdentity().get(0).getCreateUser());
                rawData.append("</createuser>");
                rawData.append("<createdatetime>" + "" + customerDTO.getListCustIdentity().get(0).getCreateDatetime());
                rawData.append("</createdatetime>");


                if (!CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                    rawData.append("<custId>" + "" + customerDTO.getCustId());
                    rawData.append("</custId>");
                }

                rawData.append("<custIdentityId>" + "" + customerDTO.getListCustIdentity().get(0).getCustIdentityId());
                rawData.append("</custIdentityId>");

                rawData.append("</listCustIdentity>");

                SexBeans sexBean = arrSexBeans.get(spinnerGioitinhEditCus.getSelectedItemPosition());
                rawData.append("<sex>" + "" + sexBean.getValues());
                rawData.append("</sex>");
                rawData.append("<birthDate>" + ""
                        + StringUtils.convertDateToString(edit_ngaysinhEditCus.getText().toString())
                        + "T00:00:00Z");
                rawData.append("</birthDate>");
                rawData.append("<custAdd>");
                rawData.append("<province>");
                rawData.append("<code>" + "" + provinceEdit);
                rawData.append("</code>");
                rawData.append("</province>");

                rawData.append("<district>");
                rawData.append("<code>" + "" + districtEdit);
                rawData.append("</code>");
                rawData.append("</district>");

                rawData.append("<precinct>");
                rawData.append("<code>" + "" + precinctEdit);
                rawData.append("</code>");
                rawData.append("</precinct>");


                if (!CommonActivity.isNullOrEmpty(streetBlockEdit) && !CommonActivity.isNullOrEmpty(edtStreetBlockEditCus.getText().toString())) {
                    rawData.append("<streetBlock>");
                    rawData.append("<code>" + "" + streetBlockEdit);
                    rawData.append("</code>");
                    rawData.append("<name>" + "" + CommonActivity.getNormalText(edtStreetBlockEditCus.getText().toString()));
                    rawData.append("</name>");
                    rawData.append("</streetBlock>");

                    rawData.append("<areaCode>" + "" + provinceEdit + districtEdit + precinctEdit + streetBlockEdit);
                    rawData.append("</areaCode>");
                } else {
                    rawData.append("<areaCode>" + "" + provinceEdit + districtEdit + precinctEdit);
                    rawData.append("</areaCode>");
                }

                if (!CommonActivity.isNullOrEmpty(fulladdress)) {
                    fulladdress = "";
                }
                try {
                    GetAreaDal dal = new GetAreaDal(getActivity());
                    dal.open();

                    if (!CommonActivity.isNullOrEmpty(edtHomeNumberEditCus.getText().toString())) {
                        fulladdress = edtHomeNumberEditCus.getText().toString().trim() + " ";
                    }
                    if (!CommonActivity.isNullOrEmpty(edtStreetNameEditCus.getText().toString())) {
                        fulladdress = fulladdress + edtStreetNameEditCus.getText().toString().trim() + " ";
                    }
                    if (!CommonActivity.isNullOrEmpty(edtStreetBlockEditCus.getText().toString())) {
                        fulladdress = fulladdress + edtStreetBlockEditCus.getText().toString().trim() + " ";
                    }

                    fulladdress = fulladdress + dal.getfulladddress(provinceEdit + districtEdit + precinctEdit);
                    rawData.append("<fullAddress>" + "" + CommonActivity.getNormalText(fulladdress));
                    rawData.append("</fullAddress>");
                    dal.close();
                } catch (Exception e) {
                    Log.d(Constant.TAG, "error", e);
                }


                rawData.append("</custAdd>");

                rawData.append("<province>" + "" + provinceEdit);
                rawData.append("</province>");
                rawData.append("<district>" + "" + districtEdit);
                rawData.append("</district>");
                rawData.append("<precinct>" + "" + precinctEdit);
                rawData.append("</precinct>");
                if (!CommonActivity.isNullOrEmpty(streetBlockEdit) && !CommonActivity.isNullOrEmpty(edtStreetBlockEditCus.getText().toString())) {
                    rawData.append("<streetBlock>" + "" + streetBlockEdit);
                    rawData.append("</streetBlock>");
                    rawData.append("<streetBlockName>" + "" + CommonActivity.getNormalText(edtStreetBlockEditCus.getText().toString()));
                    rawData.append("</streetBlockName>");
                    rawData.append("<areaCode>" + "" + provinceEdit + districtEdit + precinctEdit + streetBlockEdit);
                    rawData.append("</areaCode>");
                } else {
                    rawData.append("<areaCode>" + "" + provinceEdit + districtEdit + precinctEdit);
                    rawData.append("</areaCode>");
                }


                rawData.append("<home>" + "" + CommonActivity.getNormalText(edtHomeNumberEditCus.getText().toString()));
                rawData.append("</home>");


                rawData.append("<streetName>" + "" + CommonActivity.getNormalText(edtStreetNameEditCus.getText().toString()));
                rawData.append("</streetName>");


                Spin spination = (Spin) spinnerQuoctichEditCus.getSelectedItem();
                if (!CommonActivity.isNullOrEmpty(spination)
                        && !CommonActivity.isNullOrEmpty(spination.getValue())) {
                    rawData.append("<nationality>" + "" + spination.getValue());
                    rawData.append("</nationality>");
                }


                rawData.append("<address>" + "" + CommonActivity.getNormalText(fulladdress));
                rawData.append("</address>");


                rawData.append("<description>" + "" + CommonActivity.getNormalText(editNote.getText().toString()));
                rawData.append("</description>");

                rawData.append("<identityNo>" + "" + edit_socmndEditCus.getText().toString());
                rawData.append("</identityNo>");


                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
                rawData.append("</customerDTO>");


                if (!CommonActivity.isNullOrEmptyArray(lstFilePath)) {
                    for (String fileObj : lstFilePath) {
                        rawData.append("<lstFilePath>");
                        rawData.append(fileObj);
                        rawData.append("</lstFilePath>");
                    }

                    if (!CommonActivity.isNullOrEmptyArray(lstRecordName)) {
                        for (String fileObj : lstRecordName) {
                            rawData.append("<lstRecordName>");
                            rawData.append(fileObj);
                            rawData.append("</lstRecordName>");
                        }
                    }

                    if (!CommonActivity.isNullOrEmptyArray(lstString)) {
                        for (String fileObj : lstString) {
                            rawData.append("<lstString>");
                            rawData.append(fileObj);
                            rawData.append("</lstString>");
                        }
                    }

                    rawData.append("<fileContent>");
                    rawData.append("");
                    rawData.append("</fileContent>");

                    rawData.append("<noSendFile>" + "NO_SEND_FILE");
                    rawData.append("</noSendFile>");
                }

                rawData.append("<value>" + edtOTPCode.getText().toString().trim() + "</value>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(edtOTPIsdn.getText().toString().trim()) + "</isdn>");

                if ("true".equals(isPSenTdO)) {
//                    if(subscriberDTO.getIgnoreOTPCTV()){
//                        rawData.append("<isIsPSenTdO>false</isIsPSenTdO>");
//                    }else{
                    rawData.append("<isIsPSenTdO>true</isIsPSenTdO>");
//                    }

                } else {
                    rawData.append("<isIsPSenTdO>false</isIsPSenTdO>");
                }

                //them loai doi tuong
                rawData.append("<subObject>");
                rawData.append(subObject);
                rawData.append("</subObject>");

                //them subid
                if(!CommonActivity.isNullOrEmpty(subscriberDTO)) {
                    rawData.append("<subId>");
                    rawData.append(subscriberDTO.getSubId());
                    rawData.append("</subId>");
                }

                rawData.append("</input>");
                rawData.append("</ws:doUpdateCustomerV1>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_doUpdateCustomerV1");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                com.viettel.bss.viettelpos.v4.customer.object.SaleOutput parseOuput = serializer
                        .read(com.viettel.bss.viettelpos.v4.customer.object.SaleOutput.class,
                                original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    actionAuditId = description;
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("doUpdateCustomerV1", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return null;
        }
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!"FMP_EDT_CUST".equals(type)) {
                customerDTO.setAddress(fulladdress);
                customerDTO.getListCustIdentity().get(0).setIdIssueDate(edit_ngaycapEditCus.getText().toString());
                customerDTO.getListCustIdentity().get(0).setIdIssuePlace(editNoicapEditCus.getText().toString());
                customerDTO.setName(edit_tenKHEditCus.getText().toString());
                customerDTO.setBirthDate(StringUtils.convertDateToString(edit_ngaysinhEditCus.getText().toString()));

                Intent i = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("customerDTO", customerDTO);
                i.putExtras(mBundle);
                getTargetFragment().onActivityResult(getTargetRequestCode(),
                        Activity.RESULT_OK, i);
                getActivity().onBackPressed();
            } else { //truong hop man hinh sua sai thong tin khach hang sua sai thong tin ho so
                btnsuadoi.setVisibility(View.GONE);
            }
        }
    };


    public class AsynZipFile extends AsyncTask<String, Void, ArrayList<FileObj>> {

        private HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
        private Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private String description = "";
        private ArrayList<String> lstFilePath = new ArrayList<String>();
        private List<Action> lstAction;

        public AsynZipFile(Context context, HashMap<String, ArrayList<FileObj>> hasMapFile,
                           ArrayList<String> mlstFilePath, List<Action> lstAction) {
            this.mContext = context;
            this.mHashMapFileObj = hasMapFile;
            this.lstFilePath = mlstFilePath;
            this.lstAction = lstAction;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setMessage(mContext.getResources().getString(R.string.edit_cust_progress_zip));
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
                    ArrayList<FileObj> lstTmp = new ArrayList<FileObj>();

                    Action actionCus = lstAction.get(0);
                    for (FileObj fileObj : result) {
                        if (fileObj.getActions().equals(actionCus.getActionCode())) {
                            fileObj.setActionCode(actionCus.getActionCode());
                            fileObj.setReasonId(reasonDTO.getReasonId());
                            fileObj.setIsdn(subscriberDTO.getIsdn());
                            fileObj.setActionAudit(actionCus.getActionAuditId());
                            fileObj.setPageSize(0 + "");
                            fileObj.setStatus(0);
                        }
                    }

                    AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(
                            mContext, result, onclick,
                            getString(R.string.changeCusSucess) + "\n");
                    uploadImageAsy.execute();
                }
            } else {
                if (result != null && result.size() > 0) {
                    for (FileObj fileObj : result) {
                        File tmp = new File(fileObj.getPath());
                        tmp.delete();
                    }
                }

                CommonActivity.createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name))
                        .show();
            }

        }
    }

    // lay danh sach loai khach hang
    public class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";
        private ProgressDialog progress;

        public GetMappingChannelCustTypeAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(getActivity());
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<CustTypeDTO> doInBackground(String... params) {
            return getMappingChannelCustType(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustTypeDTO> result) {
            this.progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    if (!CommonActivity.isNullOrEmpty(customerDTO) && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO())) {
                        for (CustTypeDTO item : result) {
                            if (item.getCustType().equals(customerDTO.getCustTypeDTO().getCustType())) {
                                edtloaikhEditCus.setText(item.getName());
                                edtloaikhEditCus.setEnabled(false);
                                break;
                            }
                        }
                    }
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
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
                    if (description == null || "".equals(description)) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustTypeDTO> getMappingChannelCustType(String payType) {
            ArrayList<CustTypeDTO> lstCustTypeDTOs = new ArrayList<>();
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
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<payType>").append(payType);
                rawData.append("</payType>");
                rawData.append("</input>");
                rawData.append("</ws:getMappingChannelCustType>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
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

    public class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {

        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if (lnngayhethanEditCus != null) {
                lnngayhethanEditCus.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrMapusage = result;
                for (Spin spin : arrMapusage) {
                    if (!CommonActivity.isNullOrEmpty(typePaperEditCus) && CommonActivity.checkMapUsage(typePaperEditCus, spin)) {
                        if (lnngayhethanEditCus != null) {
                            lnngayhethanEditCus.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            } else {
                arrMapusage = new ArrayList<>();
            }
        }
    }

    public void zoomImageFromThumb(final View thumbView, String path) {
        final int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Bitmap myBitmap = BitmapFactory.decodeFile(path);

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) getActivity().findViewById(
                R.id.expanded_image);
        expandedImageView.setImageBitmap(myBitmap);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        getActivity().findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
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
    private class OnPostGetInfoCustomer implements OnPostExecuteListener<ArrayList<SpinV2>> {

        @Override
        public void onPostExecute(ArrayList<SpinV2> result, String errorCode, String description) {

            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrDoituong = result;
                SpinV2 spin = new SpinV2();
                spin.setValue(act.getString(R.string.chondoituong));
                arrDoituong.add(0, spin);
                Utils.setDataSpinner(getActivity(),arrDoituong,spnDoituong);
            } else {
                CommonActivity.createAlertDialog(act,
                        getString(R.string.doituong_null),
                        getString(R.string.app_name));
                return;
            }
        }
    }
}
