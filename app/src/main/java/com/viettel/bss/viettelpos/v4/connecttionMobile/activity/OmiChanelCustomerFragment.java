
package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetListCustomerBccsAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SubTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.fragment.ActivityAccountInfo;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author thinhhq1 CONNECTION dau noi di dong tra truoc tra sau
 */
@SuppressLint("SimpleDateFormat")
public class OmiChanelCustomerFragment extends Fragment implements OnClickListener, OnItemClickListener {

    private final String tag = OmiChanelCustomerFragment.class.getName();
    private Activity activity;

    public static OmiChanelCustomerFragment newInstance(){
        OmiChanelCustomerFragment fragment = new OmiChanelCustomerFragment();
        return fragment;
    }

    // Define View
    private Spinner spinner_typethuebao, spinner_typepayper;
    private EditText edtloaikh, edit_sogiayto;
    private Button btnsearch, btnnhapmoi;
    private ExpandableHeightListView lvCustomer;

    private List<CustIdentityDTO> arrCustIdentityDTOs = new ArrayList<>();
    private GetListCustomerBccsAdapter adaGetListCustomerBccsAdapter;

    // get list Sub
    private final ArrayList<SubTypeBean> arrSubTypeBeans = new ArrayList<>();

    public static CustIdentityDTO custIdentityDTO = new CustIdentityDTO();
    private View mView = null;

    private ArrayList<SexBeans> arrSexBeans = new ArrayList<>();
    // private String sex = "";

    private Date dateNow;
    private String dateNowString;

    public static String subType = "";

    public static ArrayList<CustTypeDTO> arrCustTypeDTOs = new ArrayList<>();
    private ArrayList<CustTypeDTO> arrCusTypePr = new ArrayList<>();

    private CustTypeDTO custTypeDTO = null;

    private ProgressBar prbCustTypeMain, prbTypePaperMain, prbTypePaperDialog;
    private Button btnRefreshCustTypeMain, btnRefreshTypePaperMain, btnTypePaperDialog;

    // khai bao resource for dialog insert new customer

    private Dialog dialogInsertNew;
    private EditText edtloaikhNew;
    private EditText edit_tenKHNew;
    private EditText edit_socmndNew;
    private EditText edit_ngaycapNew;
    private EditText edit_noicapNew;
    private EditText edit_ngaysinhNew;
    private EditText edtprovinceNew;
    private EditText edtdistrictNew;
    private EditText edtprecinctNew;
    private EditText edtStreetBlockNew;
    private EditText edit_soGQNew;
    private EditText edt_tinNew;
    private EditText edt_streetNameNew;
    private EditText edtHomeNumberNew;
    private EditText edit_note ;

    private Spinner spinner_type_giay_to_new, spinner_sex_new, spinner_quoctichnew;
    private LinearLayout ln_sex_new, linearsoGPKDNew, linearCMTNew, ln_tinNew, lnsogiaytoNew, lnquoctichNew,
            lnngaycapnew, lnnoicapnew;
    private LinearLayout lnngayhethanNew ,lnAddressCusNew;
    private EditText   editngayhethanNew;
    // arrlist province
    private ArrayList<AreaBean> arrProvince = new ArrayList<>();
    private String province = "";
    // arrlist district
    private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
    private String district = "";
    // arrlist precinct
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
    private String precinct = "";

    private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<>();
    private String typePaper = "";
    private String streetBlock = "";

    private CustTypeDTO custTypeDTODialog = null;
    private CustTypeDTO custTypeDTODialogPR = null;

    private String typePaperDialog;
    private String typePaperDialogPR;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String provincePR = "";
    private String districtPR = "";
    private String precinctPR = "";
    private String streetBlockPR = "";

    // thong tin khach hang dai dien
    private CustIdentityDTO repreCustomer;

    // chon thong tin khach hang cu cho nguoi dai dien khach hang doanh nghiep
    private Dialog dialogCus;


    // define ssua doi thong tin khach hang cu ca nhan
    private Dialog dialogEditCus;

    private EditText edtloaikhEditCus, edit_tenKHEditCus, edit_ngaysinhEditCus, edit_socmndEditCus, edit_ngaycapEditCus,
            edit_noicapEditCus, edtprovinceEditCus, edtdistrictEditCus, edtprecinctEditCus, edtStreetBlockEditCus,
            edt_streetNameEditCus, edtHomeNumberEditCus, edit_noteEditCus, edtOTPIsdn, edtOTPCode;
    private ProgressBar prbCustTypeEditCus, prbreasonBtn, prbtypePaperEdit;

    private LinearLayout lnngayhethanEdit;
    private EditText edit_ngayhethanEdit;

    private Spinner spinner_gioitinhEditCus, spinner_type_giay_toEditCus, spn_reasonEditCus, spinner_quoctichnewEditCus;
    private Button btnRefreshStreetBlockEditCus, btnsuadoi, btncancel, btnSendOTP;

    private String provinceEdit = "";
    private String districtEdit = "";
    private String precinctEdit = "";
    private String streetBlockEdit = "";

    private String typePaperEditCus;

    private List<ReasonDTO> arrReasonChangeCus = new ArrayList<ReasonDTO>();
    private StringBuilder addressEditCus;

    // cau hinh thong tin an theo so giay to
    public  static ArrayList<Spin> arrMapusage = new ArrayList<>();
    private String typeMapusage = "";




    //omni
    public ConnectionOrder connectionOrder = new ConnectionOrder();
    public  ArrayList<ImageBO> lstImageBOs;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null){
            connectionOrder = (ConnectionOrder) arguments.getSerializable("connectionOrder");
            lstImageBOs = (ArrayList<ImageBO>) arguments.getSerializable("lstImageBOs");
            Gson g = new Gson();
            Log.d("connectionOrder_FragmentInfoCustomerMobileNew: ", g.toJson(connectionOrder));
        }

        Calendar calendar = Calendar.getInstance();

        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH) + 1;
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            dateNow = calendar.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        if (mView == null) {
            mView = inflater.inflate(R.layout.connectionmobile_layout_info_customer_bccs2, container, false);

            UnitView(mView);
        }
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 2222:

                    AreaObj areaProvicialPR = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    AreaObj areaDistristPR = (AreaObj) data.getExtras().getSerializable("areaDistrist");

                    AreaObj areaPrecintPR = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    AreaObj areaGroupPR = (AreaObj) data.getExtras().getSerializable("areaGroup");

                    String areaFlowPR = data.getExtras().getString("areaFlow");
                    String areaHomeNumberPR = data.getExtras().getString("areaHomeNumber");

                    StringBuilder addressPR = new StringBuilder();

                    if (areaHomeNumberPR != null && areaHomeNumberPR.length() > 0) {
                        addressPR.append(areaHomeNumberPR).append(" ");
                    }
                    if (areaFlowPR != null && areaFlowPR.length() > 0) {
                        addressPR.append(areaFlowPR).append(" ");
                    }
                    if (areaGroupPR != null && areaGroupPR.getName() != null && areaGroupPR.getName().length() > 0) {
                        addressPR.append(areaGroupPR.getName()).append(" ");
                        streetBlockPR = areaGroupPR.getAreaCode();
                    } else {
                        streetBlockPR = "";
                    }
                    if (areaPrecintPR != null && areaPrecintPR.getName() != null && areaPrecintPR.getName().length() > 0) {

                        addressPR.append(areaPrecintPR.getName()).append(" ");
                        precinctPR = areaPrecintPR.getPrecinct();
                    } else {
                        precinctPR = "";
                    }
                    if (areaDistristPR != null && areaDistristPR.getName() != null
                            && areaDistristPR.getName().length() > 0) {

                        addressPR.append(areaDistristPR.getName()).append(" ");
                        districtPR = areaDistristPR.getDistrict();
                    } else {
                        districtPR = "";
                    }
                    if (areaProvicialPR != null && areaProvicialPR.getName() != null
                            && areaProvicialPR.getName().length() > 0) {

                        addressPR.append(areaProvicialPR.getName());
                        provincePR = areaProvicialPR.getProvince();
                    } else {
                        provincePR = "";
                    }
                    // Log.d("Log", "Check edit address null: " + txtdctbc +
                    // "adess :"
                    // + addressPR);
                    edtdiachiKHPR.setText(addressPR);

                    break;

                case 104:
                    if (data != null) {
                        custTypeDTO = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                        if (custTypeDTO != null && !CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
                            edtloaikh.setText(custTypeDTO.getName());
                            // lay danh sach loại giay to theo loai khach hang
                            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                                    getActivity(), prbTypePaperMain, btnRefreshTypePaperMain, spinner_typepayper, null);
                            getMappingCustIdentityUsageAsyn.execute(custTypeDTO.getCustType());
                        } else {
                            edtloaikh.setText("");
                        }
                    }
                    break;

                case 1010:
                    if (data != null) {
                        custTypeDTODialogPR = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                        if (custTypeDTODialogPR != null
                                && !CommonActivity.isNullOrEmpty(custTypeDTODialogPR.getCustType())) {
                            edtloaikhPR.setText(custTypeDTODialogPR.getName());
                            if(!CommonActivity.isNullOrEmpty(custTypeDTODialogPR) && "3".equals(custTypeDTODialogPR.getCusGroup())){
                                lnAddressCusPr.setVisibility(View.GONE);
                            }else{
                                lnAddressCusPr.setVisibility(View.VISIBLE);
                            }
                            // lay danh sach loại giay to theo loai khach hang
                            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                                    getActivity(), prbTypePaperPR, btnRefreshTypePR, spinner_type_giay_toPR, null);
                            getMappingCustIdentityUsageAsyn.execute(custTypeDTODialogPR.getCustType());
                            // neu la truong hop nhom khach hang la kh ca nhan nuoc ngoai
                            if(!CommonActivity.isNullOrEmpty(custTypeDTODialogPR.getCusGroup()) && "3".equals(custTypeDTODialogPR.getCusGroup())){
                                initNationly(spinner_quoctichpr,true);
                            }else{
                                initNationly(spinner_quoctichpr,false);
                            }
                        } else {
                            edtloaikhPR.setText("");
                        }
                    }
                    break;

                case 111:
                    if (data != null) {
                        custTypeDTODialog = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                        if (custTypeDTODialog != null && !CommonActivity.isNullOrEmpty(custTypeDTODialog.getCustType())) {
                            // Log.d("GROUPTYPEEEEEEEEEEEEEEEEEEEEEEEEEE",
                            // custTypeDTODialog.getGroupType());
                            // kh doanh nghiep
                            if ("2".equals(custTypeDTODialog.getGroupType())) {
                                linearsoGPKDNew.setVisibility(View.VISIBLE);
                                ln_tinNew.setVisibility(View.VISIBLE);
                                lnsogiaytoNew.setVisibility(View.GONE);
                                linearCMTNew.setVisibility(View.GONE);
                                ln_sex_new.setVisibility(View.GONE);
                                lnquoctichNew.setVisibility(View.GONE);
                                lnngaycapnew.setVisibility(View.GONE);
                                lnnoicapnew.setVisibility(View.GONE);
                                lnngayhethanNew.setVisibility(View.GONE);
                                lnAddressCusNew.setVisibility(View.VISIBLE);
                            } else {
                                linearsoGPKDNew.setVisibility(View.GONE);
                                ln_tinNew.setVisibility(View.GONE);
                                linearCMTNew.setVisibility(View.VISIBLE);
                                lnsogiaytoNew.setVisibility(View.VISIBLE);
                                ln_sex_new.setVisibility(View.VISIBLE);
                                lnquoctichNew.setVisibility(View.VISIBLE);
                                lnngaycapnew.setVisibility(View.VISIBLE);
                                lnnoicapnew.setVisibility(View.VISIBLE);
                                lnngayhethanNew.setVisibility(View.VISIBLE);

                                // neu la truong hop nhom khach hang la kh ca nhan nuoc ngoai
                                if(!CommonActivity.isNullOrEmpty(custTypeDTODialog.getCusGroup()) && "3".equals(custTypeDTODialog.getCusGroup())){
                                    initNationly(spinner_quoctichnew,true);
                                }else{
                                    initNationly(spinner_quoctichnew,false);
                                }


                            }
                            edtloaikhNew.setText(custTypeDTODialog.getName());
                            // lay danh sach loại giay to theo loai khach hang
                            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                                    getActivity(), prbTypePaperDialog, btnTypePaperDialog, spinner_type_giay_to_new, null);
                            getMappingCustIdentityUsageAsyn.execute(custTypeDTODialog.getCustType());
                        } else {
                            custTypeDTODialog = new CustTypeDTO();

                            edtloaikhNew.setHint(getActivity().getString(R.string.chonloaiKH));
                            edtloaikhNew.setText("");
                            typePaperDialog = "";
                        }
                    }
                    break;
                case 106:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");

                        province = areaBean.getProvince();
                        initDistrict(province);
                        edtprovinceNew.setText(areaBean.getNameProvince());
                        edtdistrictNew.setText("");
                        edtprecinctNew.setText("");
                        edtStreetBlockNew.setText("");
                    }
                    break;
                case 107:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
                        district = areaBean.getDistrict();
                        initPrecinct(province, district);
                        edtdistrictNew.setText(areaBean.getNameDistrict());
                        edtprecinctNew.setText("");
                        edtStreetBlockNew.setText("");
                    }
                    break;

                case 108:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("precinctKey");
                        precinct = areaBean.getPrecinct();
                        edtprecinctNew.setText(areaBean.getNamePrecint());
                        edtStreetBlockNew.setText("");
                    }
                    break;
                case 109:
                    if (data != null) {
                        AreaObj streetBlockKey = (AreaObj) data.getExtras().getSerializable("streetBlockKey");
                        streetBlock = streetBlockKey.getAreaCode();
                        edtStreetBlockNew.setText(streetBlockKey.getName());
                    }
                    break;
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
                case 44:
                    break;
                default:
                    break;
            }
        }
    }

    // UnitView
    private void UnitView(View v) {
        removeCus();

        // man hinh chinh
        prbCustTypeMain = (ProgressBar) v.findViewById(R.id.prbCustType);
        prbTypePaperMain = (ProgressBar) v.findViewById(R.id.prbTypePaper);
        btnRefreshCustTypeMain = (Button) v.findViewById(R.id.btnRefreshCustType);
        btnRefreshCustTypeMain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                arrCustTypeDTOs = new ArrayList<>();
                GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
                        getActivity(), prbCustTypeMain, btnRefreshCustTypeMain);
                getMappingChannelCustTypeAsyn.execute(subType);

            }
        });
        btnRefreshTypePaperMain = (Button) v.findViewById(R.id.btnRefreshTypePaper);
        btnRefreshTypePaperMain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                        getActivity(), prbTypePaperMain, btnRefreshTypePaperMain, spinner_typepayper, null);
                getMappingCustIdentityUsageAsyn.execute(custTypeDTO.getCustType());
            }
        });

        spinner_typepayper = (Spinner) v.findViewById(R.id.spinner_typepayper);
        spinner_typepayper.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arrTypePaper != null && arrTypePaper.size() > 0) {
                    typePaper = arrTypePaper.get(arg2).getIdType();
                    if("ID".equals(typePaper)){
                        edit_sogiayto.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiayto.setFilters(FilterArray);
                    }else if("MID".equals(typePaper)){
                        edit_sogiayto.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiayto.setFilters(FilterArray);
                    }else if("IDC".equals(typePaper)){
                        edit_sogiayto.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 15;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiayto.setFilters(FilterArray);
                    }else{
                        edit_sogiayto.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_sogiayto.setFilters(FilterArray);
                    }
                } else {
                    typePaper = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        edtloaikh = (EditText) v.findViewById(R.id.edtloaikh);
        edtloaikh.setOnClickListener(this);

        spinner_typethuebao = (Spinner) v.findViewById(R.id.spinner_typethuebao);
        edit_sogiayto = (EditText) v.findViewById(R.id.edit_sogiayto);

        if (!CommonActivity.isNullOrEmpty(connectionOrder)&&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())){
            edit_sogiayto.setText(connectionOrder.getCustomer().getIdNo());
            edit_sogiayto.setEnabled(false);
        }


        btnsearch = (Button) v.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(this);
        btnnhapmoi = (Button) v.findViewById(R.id.btnnhapmoimobile);
        btnnhapmoi.setOnClickListener(this);
        lvCustomer = (ExpandableHeightListView) v.findViewById(R.id.listcustomer);
        lvCustomer.setExpanded(true);
        lvCustomer.setOnItemClickListener(this);

        if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
            arrSubTypeBeans.clear();
        }

        initSub();
        spinner_typethuebao.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                reloadData();
                subType = arrSubTypeBeans.get(arg2).getValue();

                arrCustTypeDTOs = new ArrayList<>();
                custTypeDTO = new CustTypeDTO();
                arrTypePaper = new ArrayList<>();
                initTypePaper(arrTypePaper, spinner_typepayper, null);
                edtloaikh.setHint(getActivity().getString(R.string.chonloaiKH));
                edtloaikh.setText("");
                typePaper = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if(connectionOrder != null){

            if(Constant.ORD_TYPE_CHANGE_TO_POSPAID.equals(connectionOrder.getOrderType())){

                if (CreateConnectMobileOmiActivity.instance.subscriberDTO != null && !CreateConnectMobileOmiActivity.instance.subscriberDTO.isMarkNotOwner()) {
                    if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
                        arrCustIdentityDTOs = new ArrayList<>();
                    }
                    CustIdentityDTO item = new CustIdentityDTO();
                    item.setIdNo(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdNo());
                    item.setIdIssueDate(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdIssueDate());
                    item.setIdIssuePlace(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdIssuePlace());
                    item.setCustomer(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput());
                    arrCustIdentityDTOs.add(item);
                    adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(activity,
                            arrCustIdentityDTOs, imageListenner);
                    lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);
                    adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                    if(!CommonActivity.isNullOrEmpty(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput())){
                        edit_sogiayto.setText(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdNo());
                    }else{
                        edit_sogiayto.setText("");
                    }
                    disableView();
                    custIdentityDTO = item;
                    if("1".equals(subType)){
                        AsySearchAccountDebitInfo asySearchAccountDebitInfo = new AsySearchAccountDebitInfo(getActivity(),
                                custIdentityDTO);
                        asySearchAccountDebitInfo.execute();
                    }else{
                        AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(
                                getActivity(), false, null);
                        asynGetCustomerByCustIdParent.execute(custIdentityDTO.getCustomer().getCustId() + "");
                    }
                }else{
                    if(!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getCustId()) && connectionOrder.getCustomer().getCustId() != 0){
                        checkOmichanel();
                    }else{
                        showPopupInsertNewCus();
                        disableView();
                    }
                }
            }else{
                if(!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getCustId()) && connectionOrder.getCustomer().getCustId() != 0){
                    checkOmichanel();
                }else{
                    showPopupInsertNewCus();
                    disableView();
                }
            }
        }

    }

    private void reloadData() {

        if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
            arrCustIdentityDTOs = new ArrayList<>();
        }

        adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(getActivity(), arrCustIdentityDTOs, imageListenner);
        lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);

        if (adaGetListCustomerBccsAdapter != null) {
            adaGetListCustomerBccsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        Log.e(tag, "onResume");
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.servicemobile);
    }

    private void removeCus() {
        if (custIdentityDTO != null) {
            custIdentityDTO = null;
        }
        if (subType != null && !subType.isEmpty()) {
            subType = "";
        }

    }

    private void initSub() {
        if(connectionOrder != null && Constant.ORD_TYPE_CONNECT_PREPAID.equals(connectionOrder.getOrderType())){
            arrSubTypeBeans.add(new SubTypeBean(getString(R.string.subfirst), "2"));
        }else{
            arrSubTypeBeans.add(new SubTypeBean(getString(R.string.sublast), "1"));
        }
        if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SubTypeBean subTypeBean : arrSubTypeBeans) {
                adapter.add(subTypeBean.getName());
            }
            spinner_typethuebao.setAdapter(adapter);

        }

    }

    // lay danh sach loai khach hang
    private class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";
        final ProgressBar progressBarRefresh;
        final Button btnRefreshInit;

        public GetMappingChannelCustTypeAsyn(Context context, ProgressBar prBar, Button btnRefresh) {
            this.context = context;
            this.progressBarRefresh = prBar;
            this.btnRefreshInit = btnRefresh;
            progressBarRefresh.setVisibility(View.VISIBLE);
            btnRefreshInit.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<CustTypeDTO> doInBackground(String... params) {
            return getMappingChannelCustType(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustTypeDTO> result) {

            progressBarRefresh.setVisibility(View.GONE);
            btnRefreshInit.setVisibility(View.VISIBLE);
            if ("0".equals(errorCode)) {
                if (result != null && !result.isEmpty()) {
                    arrCustTypeDTOs = result;
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

    private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper, CustIdentityDTO custIdentityDTOExt) {
        if (lstTypePaper == null) {
            lstTypePaper = new ArrayList<>();
        }

        ArrayAdapter<String> adapter = null;
        // if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1);
        for (CustIdentityDTO custIdentityDTO : lstTypePaper) {
            adapter.add(custIdentityDTO.getIdTypeName());
        }
        spinerPaper.setAdapter(adapter);
        // }

        if (!CommonActivity.isNullOrEmptyArray(lstTypePaper) && !CommonActivity.isNullOrEmpty(custIdentityDTOExt) && !CommonActivity.isNullOrEmptyArray(custIdentityDTOExt.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTOExt.getCustomer().getListCustIdentity())) {
            for (CustIdentityDTO cusItem : lstTypePaper) {
                if (cusItem.getIdType().equals(custIdentityDTOExt.getCustomer().getListCustIdentity().get(0).getIdType())) {
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
        CustIdentityDTO custIdentityDTO;

        public GetMappingCustIdentityUsageAsyn(Context context, ProgressBar prb, Button btnres, Spinner spin, CustIdentityDTO
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
            arrTypePaper = new ArrayList<>();
            if ("0".equals(errorCode)) {
                // lay danh sach loai giay to
                arrTypePaper = result;
//                Gson g = new Gson();
//                Log.i("DATA","arrTypePaper: "+g.toJson(arrTypePaper));
                initTypePaper(arrTypePaper, spinerPaper, custIdentityDTO);
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

    private class SearchCustIdentityAsyn extends AsyncTask<String, Void, ParseOuput> {

        private final ProgressDialog progress;
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
            ArrayList<CustIdentityDTO> arrCustIdentityDTODialog = new ArrayList<>();
            if (!CommonActivity.isNullOrEmpty(check)) {
                arrCustIdentityDTODialog = new ArrayList<>();
            } else {
                arrCustIdentityDTOs = new ArrayList<>();
            }

            if(adaGetListCustomerBccsAdapter != null)



            if ("0".equals(errorCode)) {
                if (result != null) {

                    if (result.getLstCustIdentityDTOs() != null && result.getLstCustIdentityDTOs().size() > 0) {

                        if (!CommonActivity.isNullOrEmpty(check)) {
                            arrCustIdentityDTODialog = result.getLstCustIdentityDTOs();
                            showSelectCus(arrCustIdentityDTODialog);
                        } else {
                            arrCustIdentityDTOs = result.getLstCustIdentityDTOs();

                            for (CustIdentityDTO itemCus : arrCustIdentityDTOs) {
                                if ("BUS".equals(itemCus.getIdType()) || "TIN".equals(itemCus.getIdType())) {
                                    itemCus.setGroupType("2");
                                } else {
                                    itemCus.setGroupType(itemCus.getCustomer().getCustTypeDTO().getGroupType());
                                }
                            }
                        }

                    } else {
                        if (description != null && !description.isEmpty()) {
                            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                    getResources().getString(R.string.app_name));
                            dialog.show();
                        } else {



                            if(!CommonActivity.isNullOrEmpty(check)){
                                Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                        getResources().getString(R.string.notkh),
                                        getResources().getString(R.string.app_name));
                                dialog.show();
                            }else{

                                showPopupInsertNewCus();
                            }

                        }
                    }

                    adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(getActivity(), arrCustIdentityDTOs,
                            imageListenner);
                    lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);
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
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<custIdentitySearchDTO>");
                rawData.append("<idNo>").append(idNo.trim());
                rawData.append("</idNo>");
//                if (!CommonActivity.isNullOrEmpty(custType)) {
//                    rawData.append("<custType>").append(custType);
//                    rawData.append("</custType>");
//                }

                // 1 idType

//                if (!CommonActivity.isNullOrEmpty(typePaper)) {
//                    rawData.append("<idType>").append(typePaper);
//                    rawData.append("</idType>");
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

    private class CheckCustIdentityAsyn extends AsyncTask<String, Void, ParseOuput> {

        final ProgressDialog progress;
        private Context context = null;
        private String errorCode = "";
        private String description = "";

        private final String checkCus;

        public CheckCustIdentityAsyn(Context context, String mCheck) {
            this.context = context;
            this.checkCus = mCheck;
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
            if ("0".equals(errorCode)) {
                if (result != null) {

                    if (result.getLstCustIdentityDTOs() != null && result.getLstCustIdentityDTOs().size() > 0) {
                        Dialog dialog = CommonActivity.createAlertDialog(activity,
                                getResources().getString(R.string.checkcus),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    } else {

                        if (!CommonActivity.isNullOrEmpty(checkCus)) {

                            String nameCustomer = edit_tenKHPR.getText().toString().trim();
                            Log.d("nameCustomer", nameCustomer);
                            String socmt = edit_socmndPR.getText().toString().trim();
                            Log.d("socmt", socmt);

                            String birthDate = edit_ngaysinhPR.getText().toString();
                            Log.d("birthDate", birthDate);
                            String ngaycap = edit_ngaycapPR.getText().toString();
                            Log.d("ngaycap", ngaycap);
                            String noicap = edit_noicapPR.getText().toString().trim();
                            Log.d("noicap", noicap);

                            SexBeans sexBean = arrSexBeans.get(spinner_gioitinhPR.getSelectedItemPosition());

                            CustIdentityDTO item = new CustIdentityDTO();
                            if (!CommonActivity.isNullOrEmpty(typePaperDialogPR)) {
                                item.setIdType(typePaperDialogPR);
                            }

                            CustomerDTO cus = new CustomerDTO();
                            cus.setName(nameCustomer);

                            item.setIdNo(socmt.trim());
                            cus.setBirthDate(birthDate);
                            if(!CommonActivity.isNullOrEmpty(custTypeDTODialogPR) && "3".equals(custTypeDTODialogPR.getCusGroup())){

                            }else{
                                cus.setProvince(provincePR);
                                cus.setDistrict(districtPR);
                                cus.setPrecinct(precinctPR);
                                cus.setStreetBlock(streetBlockPR);
                                if(!CommonActivity.isNullOrEmpty(streetBlockPR)){
                                    cus.setAreaCode(provincePR + districtPR + precinctPR + streetBlockPR);
                                }else{
                                    cus.setAreaCode(provincePR + districtPR + precinctPR);
                                }
                                cus.setAddress(edtdiachiKHPR.getText().toString());
                            }


                            cus.setSex(sexBean.getValues());

                            cus.setCustType(custTypeDTODialogPR.getCustType());



                            Spin spin = (Spin) spinner_quoctichpr.getSelectedItem();
                            if (!CommonActivity.isNullOrEmpty(spin) && !CommonActivity.isNullOrEmpty(spin.getValue())) {
                                cus.setNationality(spin.getValue());
                            }
                            item.setIdIssueDate(ngaycap);
                            item.setIdIssuePlace(noicap);

                            item.setIdExpireDate(edit_ngayhethanPr.getText().toString());



                            // CustIdentityDTO custIdentityRe = new
                            // CustIdentityDTO();
                            // custIdentityRe.setRepreCustomer(item);
                            ArrayList<CustIdentityDTO> arrCusItem = new ArrayList<>();
                            arrCusItem.add(item);
                            cus.setListCustIdentity(arrCusItem);
                            item.setCustomer(cus);
                            custIdentityDTO.setRepreCustomer(item);

                            dialogParent.dismiss();
                            CreateConnectMobileOmiActivity.instance.tHost.setCurrentTab(1);

                        } else {
                            // add them thong tin khach hang moi
                            String nameCustomer = edit_tenKHNew.getText().toString();
                            Log.d("nameCustomer", nameCustomer);
                            String socmt = edit_socmndNew.getText().toString().trim();
                            Log.d("socmt", socmt);
                            String soGPKQ = edit_soGQNew.getText().toString().trim();
                            Log.d("soGPKQ", soGPKQ);
                            String birthDate = edit_ngaysinhNew.getText().toString();
                            Log.d("birthDate", birthDate);
                            String ngaycap = edit_ngaycapNew.getText().toString();
                            Log.d("ngaycap", ngaycap);
                            String noicap = edit_noicapNew.getText().toString().trim();
                            Log.d("noicap", noicap);
                            String dateExpire = editngayhethanNew.getText().toString().trim();

                            String tin = edt_tinNew.getText().toString().trim();
                            Log.d("Ma So Thue", tin);

                            SexBeans sexBean = arrSexBeans.get(spinner_sex_new.getSelectedItemPosition());

                            CustIdentityDTO item = new CustIdentityDTO();
                            if (!CommonActivity.isNullOrEmpty(typePaperDialog)) {
                                item.setIdType(typePaperDialog);
                            }

                            CustomerDTO cus = new CustomerDTO();
                            cus.setName(nameCustomer);
                            if ("2".equals(custTypeDTODialog.getGroupType())) {
                                item.setGroupType("2");
                                item.setIdType("BUS");
                                // khach hang doanh nghiep
                                item.setIdNo(edit_soGQNew.getText().toString().trim());
                                ArrayList<CustIdentityDTO> arrCustIdentityDTOs = new ArrayList<>();
                                CustIdentityDTO cusIdentityKD = new CustIdentityDTO();
                                cusIdentityKD.setIdNo(edit_soGQNew.getText().toString().trim());
                                cusIdentityKD.setIdType("BUS");
                                arrCustIdentityDTOs.add(cusIdentityKD);
                                CustIdentityDTO cusTin = new CustIdentityDTO();
                                cusTin.setIdNo(edt_tinNew.getText().toString().trim());
                                cusTin.setIdType("TIN");
                                arrCustIdentityDTOs.add(cusTin);
                                cus.setListCustIdentity(arrCustIdentityDTOs);
                            } else {
                                item.setIdNo(edit_socmndNew.getText().toString().trim());

                            }
                            cus.setBirthDate(birthDate);

                            if(!CommonActivity.isNullOrEmpty(custTypeDTODialog) && "3".equals(custTypeDTODialog.getCusGroup())){

                            }else{
                                cus.setProvince(province);
                                cus.setDistrict(district);
                                cus.setPrecinct(precinct);
                                cus.setStreetBlock(streetBlock);
                                if(!CommonActivity.isNullOrEmpty(streetBlock)){
                                    cus.setAreaCode(province + district + precinct + streetBlock);
                                }else{
                                    cus.setAreaCode(province + district + precinct);
                                }
                                try {
                                    GetAreaDal dal = new GetAreaDal(activity);
                                    dal.open();
                                    String fulladdress = "";
//                                if (!CommonActivity.isNullOrEmpty(edtHomeNumberNew.getText().toString())
//                                        && !CommonActivity.isNullOrEmpty(edtStreetBlockNew.getText().toString())) {
//                                    fulladdress = edtHomeNumberNew.getText().toString().trim() + " "
//                                            + edt_streetNameNew.getText().toString().trim() + " "
//                                            + edtStreetBlockNew.getText() + " ";
//                                }
//                                fulladdress = fulladdress + dal.getfulladddress(province + district + precinct);
                                    fulladdress = dal.getfulladddress(province + district + precinct);
                                    if(!CommonActivity.isNullOrEmpty(edtStreetBlockNew.getText().toString())){
                                        fulladdress = edtStreetBlockNew.getText().toString() + " " + fulladdress;
                                    }
                                    if(!CommonActivity.isNullOrEmpty(edt_streetNameNew.getText().toString().trim())){
                                        fulladdress = edt_streetNameNew.getText().toString() + " " + fulladdress;
                                    }
                                    if(!CommonActivity.isNullOrEmpty(edtHomeNumberNew.getText().toString())){
                                        fulladdress = edtHomeNumberNew.getText().toString() + " " + fulladdress;
                                    }
                                    cus.setAddress(fulladdress);
                                    dal.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                            cus.setSex(sexBean.getValues());
                            cus.setStreetName(edt_streetNameNew.getText().toString().trim());

                            Spin spin = (Spin) spinner_quoctichnew.getSelectedItem();
                            if (!CommonActivity.isNullOrEmpty(spin) && !CommonActivity.isNullOrEmpty(spin.getValue())) {
                                cus.setNationality(spin.getValue());
                            }
                            cus.setHome(edtHomeNumberNew.getText().toString().trim());
                            cus.setCustType(custTypeDTODialog.getCustType());

                            if (!CommonActivity.isNullOrEmpty(edit_note.getText().toString())) {
                                cus.setDescription(edit_note.getText().toString());
                            }

                            item.setGroupType(custTypeDTODialog.getGroupType());
                            item.setIdIssueDate(ngaycap);
                            item.setIdIssuePlace(noicap);
                            item.setIdExpireDate(dateExpire);

                            item.setCustomer(cus);

                            if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
                                arrCustIdentityDTOs = new ArrayList<>();
                            }

                            arrCustIdentityDTOs.add(item);
                            // if (adaGetListCustomerBccsAdapter != null) {
                            // adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                            // dialogInsertNew.dismiss();
                            // } else {
                            adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(activity,
                                    arrCustIdentityDTOs, imageListenner);
                            lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);
                            adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                            dialogInsertNew.dismiss();
                            // }
                            custIdentityDTO = item;
                            CreateConnectMobileOmiActivity.instance.tHost.setCurrentTab(1);
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
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<custIdentitySearchDTO>");
                rawData.append("<idNo>").append(idNo);
                rawData.append("</idNo>");
                rawData.append("<custType>").append(custType);
                rawData.append("</custType>");

                if (!CommonActivity.isNullOrEmpty(typePaperDialog)) {
                    rawData.append("<idType>").append(typePaperDialog);
                    rawData.append("</idType>");
                }

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

    private final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            activity.finish();
            MainActivity.getInstance().finish();

        }
    };

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int possition, long arg3) {
        if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
            custIdentityDTO = arrCustIdentityDTOs.get(possition);
        }

        if (custIdentityDTO != null && "2".equals(subType)) {
            // kh doanh nghiep

            if (("BUS".equals(custIdentityDTO.getIdType()) || ("TIN".equals(custIdentityDTO.getIdType()))
                    && "2".equals(custIdentityDTO.getGroupType()))){
                if (custIdentityDTO.getCustomer().getCustId() != null) {
                    AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(
                            getActivity(), true, null);
                    asynGetCustomerByCustIdParent.execute(custIdentityDTO.getCustomer().getCustId() + "");
                } else {
                    showPopupInsertParent(custIdentityDTO);
                }
            } else {
                if (custIdentityDTO.getCustomer().getCustId() != null) {
                    AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(
                            getActivity(), false, null);
                    asynGetCustomerByCustIdParent.execute(custIdentityDTO.getCustomer().getCustId() + "");
                } else {
                    setCurrentTabConnection(possition);
                }
            }
        } else {
            if(!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getListCustIdentity()))
            if ("BUS".equals(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType()) || "TIN".equals(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType())){
                custIdentityDTO.setGroupType("2");
            }

            setCurrentTabConnection(possition);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsearch:

                // if (custTypeDTO == null) {
                // CommonActivity.createAlertDialog(getActivity(),
                // getActivity().getString(R.string.confirmloaikh),
                // getActivity().getString(R.string.app_name)).show();
                // return;
                // }
                // if (CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
                // CommonActivity.createAlertDialog(getActivity(),
                // getActivity().getString(R.string.confirmloaikh),
                // getActivity().getString(R.string.app_name)).show();
                // return;
                // }

                if (CommonActivity.isNullOrEmpty(edit_sogiayto.getText().toString().trim())) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.must_input_idno_buspermitno),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return;
                }

                if(!CommonActivity.isNullOrEmpty(typePaper)){
                    if("ID".equals(typePaper)){
                        if(edit_sogiayto.getText().toString().trim().length() < 9){
                            CommonActivity
                                    .createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                                            getActivity().getString(R.string.app_name))
                                    .show();
                            return ;
                        }

                    }
                    if("MID".equals(typePaper)){
                        if(edit_sogiayto.getText().toString().trim().length() < 6){
                            CommonActivity
                                    .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtca),
                                            getActivity().getString(R.string.app_name))
                                    .show();
                            return;
                        }

                    }

                }

                if (CommonActivity.isNetworkConnected(getActivity())) {
                    if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
                        arrCustIdentityDTOs = new ArrayList<>();
                    }
                    if (adaGetListCustomerBccsAdapter != null) {
                        adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                    }

                    SearchCustIdentityAsyn searchCustIdentityAsyn = new SearchCustIdentityAsyn(getActivity(), "");
                    if (custTypeDTO != null) {
                        searchCustIdentityAsyn.execute(edit_sogiayto.getText().toString().trim(), "",
                                typePaper);
                    } else {
                        searchCustIdentityAsyn.execute(edit_sogiayto.getText().toString().trim(), custTypeDTO.getCustType(),
                                typePaper);
                    }


                }
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();

                break;

            case R.id.edtloaikh:
                Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
                intent.putExtra("arrCustTypeDTOsKey", arrCustTypeDTOs);
                Bundle mBundle = new Bundle();
                intent.putExtras(mBundle);
                startActivityForResult(intent, 104);
                break;

            case R.id.btnnhapmoimobile:

                showPopupInsertNewCus();

                break;
            default:
                break;
        }
    }

    // khai bao them moi thong tin khach hang dai dien cho mobile trả truoc
    private EditText edtloaikhPR, edit_tenKHPR, edit_socmndPR, edit_ngaycapPR, edit_noicapPR, edit_ngaysinhPR,
            edtdiachiKHPR;

    private Spinner spinner_type_giay_toPR, spinner_gioitinhPR, spinner_quoctichpr;
    private LinearLayout lnngayhethanPr ,lnAddressCusPr;
    private EditText edit_ngayhethanPr;
    private Dialog dialogParent = null;
    private Button btnRefreshTypePR;

    private ProgressBar prbTypePaperPR;

    private void showPopupInsertParent(final CustIdentityDTO custIdentityRe) {


        arrCusTypePr = new ArrayList<>();

        if (arrCustTypeDTOs != null && arrCustTypeDTOs.size() > 0) {
            for (CustTypeDTO item : arrCustTypeDTOs) {
                if ("2".equals(item.getGroupType())) {

                } else {
                    arrCusTypePr.add(item);
                }
            }
        }

        dialogParent = new Dialog(activity);
        dialogParent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogParent.setContentView(R.layout.connectionmobile_layout_insert_parent_bccs2);
        dialogParent.setCancelable(false);
        lnAddressCusPr = (LinearLayout) dialogParent.findViewById(R.id.lnngayhethanPr);
        lnngayhethanPr = (LinearLayout) dialogParent.findViewById(R.id.lnngayhethanPr);
        edit_ngayhethanPr = (EditText) dialogParent.findViewById(R.id.edit_ngayhethanPr);
        edit_ngayhethanPr.setOnClickListener(editTextListener);
        spinner_quoctichpr = (Spinner) dialogParent.findViewById(R.id.spinner_quoctichpr);
        initNationly(spinner_quoctichpr,false);

        edtloaikhPR = (EditText) dialogParent.findViewById(R.id.edtloaikh);
        prbTypePaperPR = (ProgressBar) dialogParent.findViewById(R.id.prbCustType);
        btnRefreshTypePR = (Button) dialogParent.findViewById(R.id.btnRefreshCustType);

        edtloaikhPR.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
//                intent.putExtra("arrCustTypeDTOsKey", arrCusTypePr);
                Bundle mBundle = new Bundle();
                mBundle.putString("GROUPKEY","GROUPKEY");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 1010);

            }
        });
        spinner_type_giay_toPR = (Spinner) dialogParent.findViewById(R.id.spinner_type_giay_to);

        spinner_type_giay_toPR.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
                    typePaperDialogPR = arrTypePaper.get(position).getIdType();
                    lnngayhethanPr.setVisibility(View.GONE);
//                    if(CommonActivity.isNullOrEmpty(arrMapusage)){
                        AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(),new OnPostGetOptionSetValue(),moveLogInAct);
                        asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
//                    }
                    if("ID".equals(typePaperDialogPR)){
                        edit_socmndPR.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndPR.setFilters(FilterArray);
                    }else if("MID".equals(typePaperDialogPR)){
                        edit_socmndPR.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndPR.setFilters(FilterArray);
                    }else if("IDC".equals(typePaperDialogPR)){
                        edit_socmndPR.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 15;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndPR.setFilters(FilterArray);
                    }if("PASS".equals(typePaperDialogPR)){
                        // truong hop ho chieu va nhom ca nhan nuoc ngoai thi dia chi an di ko can nhap
                        if(!CommonActivity.isNullOrEmpty(custTypeDTODialogPR) && "3".equals(custTypeDTODialogPR.getCusGroup())){
                            lnAddressCusPr.setVisibility(View.GONE);
                        }else{
                            lnAddressCusPr.setVisibility(View.VISIBLE);
                        }
                        edit_socmndPR.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndPR.setFilters(FilterArray);
                    }else{
                        edit_socmndPR.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndPR.setFilters(FilterArray);
                    }

                } else {
                    typePaperDialogPR = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        edit_socmndPR = (EditText) dialogParent.findViewById(R.id.edit_socmnd);
        edit_tenKHPR = (EditText) dialogParent.findViewById(R.id.edit_tenKH);
        edit_ngaysinhPR = (EditText) dialogParent.findViewById(R.id.edit_ngaysinh);

        edit_ngaysinhPR.setOnClickListener(editTextListener);
        spinner_gioitinhPR = (Spinner) dialogParent.findViewById(R.id.spinner_gioitinh);
        edit_ngaycapPR = (EditText) dialogParent.findViewById(R.id.edit_ngaycap);
        edit_ngaycapPR.setOnClickListener(editTextListener);
        edit_noicapPR = (EditText) dialogParent.findViewById(R.id.edit_noicap);
        edtdiachiKHPR = (EditText) dialogParent.findViewById(R.id.edtdiachilapdat);

        initSexPR();

        Button btnkiemtra = (Button) dialogParent.findViewById(R.id.btnkiemtra);
        btnkiemtra.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (CommonActivity.isNullOrEmpty(edit_socmndPR.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                            getActivity().getString(R.string.app_name)).show();
                    return;
                }
                SearchCustIdentityAsyn searchCustIdentityAsyn = new SearchCustIdentityAsyn(getActivity(), "1");
                if (custTypeDTODialogPR != null) {
                    searchCustIdentityAsyn.execute(edit_socmndPR.getText().toString().trim(),
                            custTypeDTODialogPR.getCustType(), typePaperDialogPR);
                } else {
                    searchCustIdentityAsyn.execute(edit_socmndPR.getText().toString().trim(), "", typePaperDialogPR);
                }

            }
        });

        edtdiachiKHPR.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String strProvince1 = Session.province;
                String strDistris1 = Session.district;

                Bundle mBundle1 = new Bundle();
                mBundle1.putString("strProvince", strProvince1);
                mBundle1.putString("strDistris", strDistris1);
                mBundle1.putBoolean("isCheckStreetBlock",false);
                mBundle1.putString("KEYPR", "1111");
                Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
                i1.putExtras(mBundle1);
                startActivityForResult(i1, 2222);

            }
        });

        LinearLayout lnGiaytodaidien = (LinearLayout) dialogParent.findViewById(R.id.lnGiaytodaidien);
        // lnGiaytodaidien.setVisibility(View.GONE);

        Button btnthem = (Button) dialogParent.findViewById(R.id.btnthemmoi);
        btnthem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    if (validateKHCNDaiDien()) {

                        CheckCustIdentityAsyn checkCustIdentityAsyn = new CheckCustIdentityAsyn(getActivity(), "1");
                        checkCustIdentityAsyn.execute(edit_socmndPR.getText().toString().trim(), "", "");

                    }
                } catch (Exception e) {
                    // validate thong tin dai dien
                    e.printStackTrace();
                }
            }
        });

        dialogParent.findViewById(R.id.btncancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogParent.dismiss();
            }
        });
//        if(CommonActivity.isNullOrEmptyArray(arrMapusage)){
            AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(),new OnPostGetOptionSetValue(),moveLogInAct,typeMapusage);
            asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
//        }
        dialogParent.show();

    }

    private void showPopupInsertNewCus() {

        if(custTypeDTODialog != null){
            custTypeDTODialog = null;
        }

        dialogInsertNew = new Dialog(activity);
        dialogInsertNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInsertNew.setContentView(R.layout.connectionmobile_layout_insert_newinfo_bccs2);
        dialogInsertNew.setCancelable(true);
        lnngayhethanNew = (LinearLayout) dialogInsertNew.findViewById(R.id.lnngayhethan);
        lnngayhethanNew.setVisibility(View.GONE);
        editngayhethanNew = (EditText) dialogInsertNew.findViewById(R.id.edit_ngayhethan);

        dialogInsertNew.findViewById(R.id.btnRefreshStreetBlock).setVisibility(View.GONE);
        lnAddressCusNew = (LinearLayout) dialogInsertNew.findViewById(R.id.lnAddressCus);
        spinner_quoctichnew = (Spinner) dialogInsertNew.findViewById(R.id.spinner_quoctichnew);
//        initNationly(spinner_quoctichnew);
        edtloaikhNew = (EditText) dialogInsertNew.findViewById(R.id.edtloaikh);
        spinner_type_giay_to_new = (Spinner) dialogInsertNew.findViewById(R.id.spinner_type_giay_to);
        spinner_type_giay_to_new.setEnabled(false);
        linearCMTNew = (LinearLayout) dialogInsertNew.findViewById(R.id.linearCMT);
        edit_socmndNew = (EditText) dialogInsertNew.findViewById(R.id.edit_socmnd);
        edit_socmndNew.setEnabled(false);
        if(!CommonActivity.isNullOrEmpty(edit_sogiayto.getText().toString())){
            edit_socmndNew.setText(edit_sogiayto.getText().toString());
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder)&&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())){
            edtloaikhNew.setText(getString(R.string.tunhantrongnuoc));
            edtloaikhNew.setEnabled(false);
            custTypeDTODialog = new CustTypeDTO();
            custTypeDTODialog.setCustType("VIE");
            custTypeDTODialog.setGroupType("1");
            typePaperDialog = "ID";

            // lay danh sach loại giay to theo loai khach hang
            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                    getActivity(), prbTypePaperDialog, btnTypePaperDialog, spinner_type_giay_to_new, null);
            getMappingCustIdentityUsageAsyn.execute("VIE");
            linearCMTNew.setVisibility(View.VISIBLE);

            // neu la truong hop nhom khach hang la kh ca nhan nuoc ngoai
            if(!CommonActivity.isNullOrEmpty(custTypeDTODialog.getCusGroup()) && "3".equals(custTypeDTODialog.getCusGroup())){
                initNationly(spinner_quoctichnew,true);
            }else{
                initNationly(spinner_quoctichnew,false);
            }

        }

        edtloaikhNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
//              intent.putExtra("arrCustTypeDTOsKey", arrCustTypeDTOs);
                Bundle mBundle = new Bundle();
                intent.putExtras(mBundle);
                startActivityForResult(intent, 111);

            }
        });
        edit_tenKHNew = (EditText) dialogInsertNew.findViewById(R.id.edit_tenKH);
        if (!CommonActivity.isNullOrEmpty(connectionOrder)&&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getName()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getName())){
            edit_tenKHNew.setText(connectionOrder.getCustomer().getName());
        }


        edit_ngaycapNew = (EditText) dialogInsertNew.findViewById(R.id.edit_ngaycap);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())&&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdIssueDate())){
            edit_ngaycapNew.setText(StringUtils.convertDate(connectionOrder.getCustomer().getIdIssueDate()));
        }
//        edit_ngaycapNew.setText(dateNow String);
        edit_noicapNew = (EditText) dialogInsertNew.findViewById(R.id.edit_noicap);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer()) &&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdIssuePlace())){
            edit_noicapNew.setText(connectionOrder.getCustomer().getIdIssuePlace());
        }
        edit_ngaysinhNew = (EditText) dialogInsertNew.findViewById(R.id.edit_ngaysinh);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())&&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getBirthDate())){
            edit_ngaysinhNew.setText(StringUtils.convertDate((connectionOrder.getCustomer().getBirthDate())));
        }
//        edit_ngaysinhNew.setText(dateNowString);

//		edit_quoctichNew = (EditText) dialogInsertNew.findViewById(R.id.edit_quoctich);
        edit_note = (EditText) dialogInsertNew.findViewById(R.id.edit_note);
        initProvince();

        edtprovinceNew = (EditText) dialogInsertNew.findViewById(R.id.edtprovince);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())&& !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getProvince())){
            initDistrict(connectionOrder.getCustomer().getAddress().getProvince());
            try {
                GetAreaDal dal = new GetAreaDal(activity);
                dal.open();
                edtprovinceNew.setText(dal.getNameProvince(connectionOrder.getCustomer().getAddress().getProvince()));
                province = connectionOrder.getCustomer().getAddress().getProvince();
                dal.close();
            } catch (Exception ignored) {
            }
        } else {
            if (!CommonActivity.isNullOrEmpty(Session.province)) {
                initDistrict(Session.province);
                try {
                    GetAreaDal dal = new GetAreaDal(activity);
                    dal.open();
                    edtprovinceNew.setText(dal.getNameProvince(Session.province));
                    province = Session.province;
                    dal.close();
                } catch (Exception ignored) {
                }
            }
        }


        edtprovinceNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                intent.putExtra("arrProvincesKey", arrProvince);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "1");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 106);

            }
        });
        edtdistrictNew = (EditText) dialogInsertNew.findViewById(R.id.edtdistrict);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())&& !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getDistrict())){
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getProvince()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getDistrict())) {
                initPrecinct(connectionOrder.getCustomer().getAddress().getProvince(), connectionOrder.getCustomer().getAddress().getDistrict());
                try {
                    GetAreaDal dal = new GetAreaDal(activity);
                    dal.open();
                    edtdistrictNew.setText(dal.getNameDistrict(connectionOrder.getCustomer().getAddress().getProvince(), connectionOrder.getCustomer().getAddress().getDistrict()));
                    district = connectionOrder.getCustomer().getAddress().getDistrict();
                    dal.close();
                } catch (Exception ignored) {
                }
            }
        } else {
            if (!CommonActivity.isNullOrEmpty(Session.province) && !CommonActivity.isNullOrEmpty(Session.district)) {
                initPrecinct(Session.province, Session.district);
                try {
                    GetAreaDal dal = new GetAreaDal(activity);
                    dal.open();
                    edtdistrictNew.setText(dal.getNameDistrict(Session.province, Session.district));
                    district = Session.district;
                    dal.close();
                } catch (Exception ignored) {
                }
            }
        }

        edtdistrictNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                intent.putExtra("arrDistrictKey", arrDistrict);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "2");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 107);

            }
        });
        edtprecinctNew = (EditText) dialogInsertNew.findViewById(R.id.edtprecinct);

        if(!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())&& !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getDistrict())){
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getProvince()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getDistrict()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getPrecinct())) {

                try {
                    GetAreaDal dal = new GetAreaDal(activity);
                    dal.open();
                    edtprecinctNew.setText(dal.getNamePrecint(connectionOrder.getCustomer().getAddress().getProvince(), connectionOrder.getCustomer().getAddress().getDistrict(), connectionOrder.getCustomer().getAddress().getPrecinct()));
                    precinct = connectionOrder.getCustomer().getAddress().getPrecinct();
                    dal.close();
                } catch (Exception ignored) {
                }
            }
        }

        edtprecinctNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                intent.putExtra("arrPrecinctKey", arrPrecinct);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "3");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 108);
            }
        });

        edtStreetBlockNew = (EditText) dialogInsertNew.findViewById(R.id.edtStreetBlock); // to
        edt_streetNameNew = (EditText) dialogInsertNew.findViewById(R.id.edt_streetName); // duong
        edtHomeNumberNew = (EditText) dialogInsertNew.findViewById(R.id.edtHomeNumber); // so
        // nha

        edtStreetBlockNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "4");
                mBundle.putString("province", province);
                mBundle.putString("district", district);
                mBundle.putString("precinct", precinct);
                intent.putExtras(mBundle);
                startActivityForResult(intent, 109);
            }
        });


        spinner_sex_new = (Spinner) dialogInsertNew.findViewById(R.id.spinner_gioitinh);
        ln_sex_new = (LinearLayout) dialogInsertNew.findViewById(R.id.ln_sex);
        // bo sung giay phep kinh doanh cho khach hang doanh nghiep
        linearsoGPKDNew = (LinearLayout) dialogInsertNew.findViewById(R.id.linearsoGPKD);

        edit_soGQNew = (EditText) dialogInsertNew.findViewById(R.id.edit_soGQ);
        linearsoGPKDNew.setVisibility(View.GONE);
        if(!CommonActivity.isNullOrEmpty(edit_soGQNew.getText().toString())){
            edit_soGQNew.setText(edit_sogiayto.getText().toString());
        }
        lnngaycapnew = (LinearLayout) dialogInsertNew.findViewById(R.id.lnngaycapnew);
        lnnoicapnew = (LinearLayout) dialogInsertNew.findViewById(R.id.lnnoicapnew);
        ln_tinNew = (LinearLayout) dialogInsertNew.findViewById(R.id.ln_tin);

        lnquoctichNew = (LinearLayout) dialogInsertNew.findViewById(R.id.lnquoctichdialog);

        edt_tinNew = (EditText) dialogInsertNew.findViewById(R.id.edt_tin);
        lnsogiaytoNew = (LinearLayout) dialogInsertNew.findViewById(R.id.lnsogiayto);

        btnTypePaperDialog = (Button) dialogInsertNew.findViewById(R.id.btnRefreshCustType);

        prbTypePaperDialog = (ProgressBar) dialogInsertNew.findViewById(R.id.prbCustType);

        btnTypePaperDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                arrCustTypeDTOs = new ArrayList<>();
                GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
                        getActivity(), prbTypePaperDialog, btnTypePaperDialog);
                getMappingChannelCustTypeAsyn.execute(subType);

            }
        });

        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            arrSexBeans.clear();
        }
        initSex(spinner_sex_new);

        edit_ngaysinhNew.setOnClickListener(editTextListener);
        edit_ngaycapNew.setOnClickListener(editTextListener);


        editngayhethanNew.setOnClickListener(editTextListener);
        spinner_type_giay_to_new.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
                    typePaperDialog = arrTypePaper.get(position).getIdType();
                    lnngayhethanNew.setVisibility(View.GONE);
                    AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(),new OnPostGetOptionSetValue(),moveLogInAct);
                    asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
                    if("ID".equals(typePaperDialog)){
                        edit_socmndNew.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndNew.setFilters(FilterArray);
                    }else if("MID".equals(typePaperDialog)){
                        edit_socmndNew.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndNew.setFilters(FilterArray);
                    }else if("IDC".equals(typePaperDialog)){
                        edit_socmndNew.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 15;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndNew.setFilters(FilterArray);
                    }if("PASS".equals(typePaperDialog)){
                        // truong hop ho chieu va nhom ca nhan nuoc ngoai thi dia chi an di ko can nhap
                        if(!CommonActivity.isNullOrEmpty(custTypeDTODialog) && "3".equals(custTypeDTODialog.getCusGroup())){
                            lnAddressCusNew.setVisibility(View.GONE);
                        }else{
                            lnAddressCusNew.setVisibility(View.VISIBLE);
                        }
                        edit_socmndNew.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndNew.setFilters(FilterArray);
                    }else{
                        edit_socmndNew.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndNew.setFilters(FilterArray);
                    }


                } else {
                    typePaperDialog = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Button btnInserNew = (Button) dialogInsertNew.findViewById(R.id.btnthemmoi);
        btnInserNew.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    if (validateKHCN()) {

                        CheckCustIdentityAsyn checkCustIdentityAsyn = new CheckCustIdentityAsyn(getActivity(), "");

                        if ("2".equals(custTypeDTODialog.getGroupType())) {
                            checkCustIdentityAsyn.execute(edit_soGQNew.getText().toString().trim(),
                                    custTypeDTODialog.getCustType(), "BUS");
                        } else {
                            checkCustIdentityAsyn.execute(edit_socmndNew.getText().toString().trim(),
                                    custTypeDTODialog.getCustType(), typePaperDialog);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Button btnCancel = (Button) dialogInsertNew.findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogInsertNew.dismiss();
            }
        });

        if(!CommonActivity.isNullOrEmpty(custTypeDTO) && !CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
            custTypeDTODialog = custTypeDTO;
            if (custTypeDTODialog != null && !CommonActivity.isNullOrEmpty(custTypeDTODialog.getCustType())) {
                // Log.d("GROUPTYPEEEEEEEEEEEEEEEEEEEEEEEEEE",
                // custTypeDTODialog.getGroupType());
                // kh doanh nghiep
                if ("2".equals(custTypeDTODialog.getGroupType())) {
                    linearsoGPKDNew.setVisibility(View.VISIBLE);
                    ln_tinNew.setVisibility(View.VISIBLE);
                    lnsogiaytoNew.setVisibility(View.GONE);
                    linearCMTNew.setVisibility(View.GONE);
                    ln_sex_new.setVisibility(View.GONE);
                    lnquoctichNew.setVisibility(View.GONE);
                    lnngaycapnew.setVisibility(View.GONE);
                    lnnoicapnew.setVisibility(View.GONE);
                } else {
                    linearsoGPKDNew.setVisibility(View.GONE);
                    ln_tinNew.setVisibility(View.GONE);
                    linearCMTNew.setVisibility(View.VISIBLE);
                    lnsogiaytoNew.setVisibility(View.VISIBLE);
                    ln_sex_new.setVisibility(View.VISIBLE);
                    lnquoctichNew.setVisibility(View.VISIBLE);
                    lnngaycapnew.setVisibility(View.VISIBLE);
                    lnnoicapnew.setVisibility(View.VISIBLE);
                }

                if(!CommonActivity.isNullOrEmpty(custTypeDTODialog.getName())) {
                    edtloaikhNew.setText(custTypeDTODialog.getName());

                    // neu la truong hop nhom khach hang la kh ca nhan nuoc ngoai
                    if(!CommonActivity.isNullOrEmpty(custTypeDTODialog.getCusGroup()) && "3".equals(custTypeDTODialog.getCusGroup())){
                        initNationly(spinner_quoctichnew,true);
                    }else{
                        initNationly(spinner_quoctichnew,false);
                    }
                }
                // lay danh sach loại giay to theo loai khach hang
                Log.i("DATA","custTypeDTODialog.getCustType(): "+custTypeDTODialog.getCustType());
                GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                        getActivity(), prbTypePaperDialog, btnTypePaperDialog, spinner_type_giay_to_new, null);
                getMappingCustIdentityUsageAsyn.execute(custTypeDTODialog.getCustType());
            }

        }
        // lay danh thuoc tinh
        typeMapusage = "INSERT_NEW_CUS";

        AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(),new OnPostGetOptionSetValue(),moveLogInAct,typeMapusage);
        asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
        dialogInsertNew.show();
    }

    private final OnClickListener editTextListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            EditText edt = (EditText) view;
            Calendar cal = Calendar.getInstance();
            if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
                Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");

                cal.setTime(date);

            }
            DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            pic.getDatePicker().setTag(view);
            pic.show();
        }
    };

    private void setCurrentTabConnection(int position) {
        // CHUYEN SANG MAN HINH DAU NOI
        for (CustIdentityDTO item : arrCustIdentityDTOs) {
            item.setIscheckCus(false);
        }
        arrCustIdentityDTOs.get(position).setIscheckCus(true);
        adaGetListCustomerBccsAdapter.notifyDataSetChanged();
        for (CustIdentityDTO cusBean : arrCustIdentityDTOs) {
            if (cusBean.isIscheckCus()) {
                custIdentityDTO = cusBean;
                if ("1".equals(subType) && custIdentityDTO.getCustomer().getCustId() != null) {

                    // check no cuoc
                    AsySearchAccountDebitInfo asySearchAccountDebitInfo = new AsySearchAccountDebitInfo(getActivity(),
                            custIdentityDTO);
                    asySearchAccountDebitInfo.execute();

                } else {
//					if (custTypeDTO != null) {
//						custIdentityDTO.getCustomer().setCustType(custTypeDTO.getCustType());
//					}
                    CreateConnectMobileOmiActivity.instance.tHost.setCurrentTab(1);
                }

                break;
            }
        }
    }

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

    // lay ma tinh/thanhpho
    private void initProvince() {
        try {
            GetAreaDal dal = new GetAreaDal(activity);
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
            GetAreaDal dal = new GetAreaDal(activity);
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
            GetAreaDal dal = new GetAreaDal(activity);
            dal.open();
            arrPrecinct = dal.getLstPrecinct(province, district);
            dal.close();
        } catch (Exception ex) {
            Log.e("initPrecinct", ex.toString());
        }
    }

    // init gioi tinh
    private void initSex(Spinner spinnerSex) {
        arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SexBeans sexBeans : arrSexBeans) {
                adapter.add(sexBeans.getName());
            }
            spinnerSex.setAdapter(adapter);
        }
    }

    // init gioi tinh
    private void initSexPR() {
        arrSexBeans = new ArrayList<>();

        arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SexBeans sexBeans : arrSexBeans) {
                adapter.add(sexBeans.getName());
            }
            spinner_gioitinhPR.setAdapter(adapter);
        }
    }

    // validate thong tin khach hang dai dien
    private boolean validateKHCNDaiDien() throws Exception {

        if (custTypeDTODialogPR == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(custTypeDTODialogPR.getCustType())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edit_tenKHPR.getText().toString())) {

            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checknameKH),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (edit_tenKHPR.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.namecust),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (StringUtils.CheckCharSpecical(edit_tenKHPR.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edit_ngaysinhPR.getText().toString())) {
            CommonActivity
                    .createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_birth_day),
                            getActivity().getString(R.string.app_name))
                    .show();
            return false;
        }

        Date birthDate = sdf.parse(edit_ngaysinhPR.getText().toString().trim());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.getDiffYearsMain(birthDate, new Date()) < 18) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.khdd18),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edit_socmndPR.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if ("ID".equals(typePaperDialogPR)) {

            if (edit_socmndPR.getText().toString().length() != 9 && edit_socmndPR.getText().toString().length() != 12) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

        if (CommonActivity.isNullOrEmpty(edit_ngaycapPR.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        Date datengaycap = sdf.parse(edit_ngaycapPR.getText().toString().trim());

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
        if (CommonActivity.isNullOrEmpty(edit_noicapPR.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        // validate ngay het han
        if(!CommonActivity.isNullOrEmpty(arrMapusage)){
            for (Spin spin: arrMapusage) {
                if(CommonActivity.checkMapUsage(typePaperDialogPR,spin)){
                    // truong hop ma bat buoc nhap nhat het han thi phai validate
                    if (CommonActivity.isNullOrEmpty(edit_ngayhethanPr.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateExpiredate),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    Date datenExpired = sdf.parse(edit_ngayhethanPr.getText().toString().trim());
                    if (datenExpired.before(datengaycap)) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanngaycap),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if(datenExpired.before(dateNow)){
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanhientai),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    break;
                }
            }
        }

        // validate thong tin khach hang dai dien
        if(!CommonActivity.isNullOrEmpty(custTypeDTODialogPR) && "3".equals(custTypeDTODialogPR.getCusGroup())) {

        }else{
            if (CommonActivity.isNullOrEmpty(provincePR) || CommonActivity.isNullOrEmpty(districtPR)
                    || CommonActivity.isNullOrEmpty(precinctPR)) {

                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
                        getActivity().getString(R.string.app_name)).show();
                return false;

            }
        }

        return true;

    }

    // validate thong tin them moi khach hang

    private boolean validateKHCN() throws Exception {

        if (custTypeDTODialog == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                    getActivity().getString(R.string.app_name)).show();
            edtloaikhNew.requestFocus();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(custTypeDTODialog.getCustType())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
                    getActivity().getString(R.string.app_name)).show();
            edtloaikhNew.requestFocus();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edit_tenKHNew.getText().toString())) {

            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checknameKH),
                    getActivity().getString(R.string.app_name)).show();
            edit_tenKHNew.requestFocus();

            return false;
        }
        if (StringUtils.CheckCharSpecical(edit_tenKHNew.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
                    getActivity().getString(R.string.app_name)).show();
            edit_tenKHNew.requestFocus();
            return false;
        }
        if (edit_tenKHNew.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.namecust),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edit_ngaysinhNew.getText().toString())) {
            CommonActivity
                    .createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_birth_day),
                            getActivity().getString(R.string.app_name))
                    .show();
            return false;
        }

        Date birthDate = sdf.parse(edit_ngaysinhNew.getText().toString().trim());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        // khach hang doanh nghiep validate thong tin
        if ("2".equals(custTypeDTODialog.getGroupType())) {
            if (CommonActivity.isNullOrEmpty(edit_soGQNew.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checksogpkd),
                        getActivity().getString(R.string.app_name)).show();
                edit_soGQNew.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(edt_tinNew.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.message_pleass_input_taxcode),
                        getActivity().getString(R.string.app_name)).show();
                edt_tinNew.requestFocus();
                return false;
            }
        } else {



            if (CommonActivity.isNullOrEmpty(edit_socmndNew.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if ("ID".equals(typePaperDialog)) {

                if (edit_socmndNew.getText().toString().length() != 9
                        && edit_socmndNew.getText().toString().length() != 12) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                            getActivity().getString(R.string.app_name)).show();
                    edit_socmndNew.requestFocus();
                    return false;
                }


            }
//            if (!DateTimeUtils.compareAge(birthDate, 14)) {
//                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatebirh),
//                        getActivity().getString(R.string.app_name)).show();
//                edit_socmndNew.requestFocus();
//                return false;
//
//            }

            // TODO check ngay sinh tra truoc phai lon 14 va tra sau phai lon hon 18
            if("1".equals(subType)){
                if (CommonActivity.getDiffYearsMain(birthDate, dateNow) < 18) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.khhm18),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            }else{
                if (CommonActivity.getDiffYearsMain(birthDate, dateNow) < 14) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.khdd14),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            }



            if("MID".equals(typePaperDialog)){
                if(!CommonActivity.isNullOrEmpty(edit_socmndNew.getText().toString().trim()) && StringUtils.CheckCharSpecical(edit_socmndNew.getText().toString().trim())){
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.cmtqd),
                            getActivity().getString(R.string.app_name)).show();
                    edit_socmndNew.requestFocus();
                    return false;
                }

            }


            if (CommonActivity.isNullOrEmpty(edit_ngaycapNew.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }

            Date datengaycap = sdf.parse(edit_ngaycapNew.getText().toString().trim());

            if (datengaycap.after(dateNow)) {
                CommonActivity
                        .createAlertDialog(getActivity(), getActivity().getString(R.string.ngaycapnhohonngayhientai),
                                getActivity().getString(R.string.app_name))
                        .show();
                return false;
            }

            if (datengaycap.before(birthDate)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }

            if ("ID".equals(typePaperDialog)) {
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

            if ("MID".equals(typePaperDialog)) {
                if (edit_socmndNew.getText().toString().trim().length() < 6
                        || edit_socmndNew.getText().toString().trim().length() > 12) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtca),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            }
            // validate ngay het han
            if(!CommonActivity.isNullOrEmpty(arrMapusage)){
                for (Spin spin: arrMapusage) {
                    if(CommonActivity.checkMapUsage(typePaperDialog,spin)){
                    // truong hop ma bat buoc nhap nhat het han thi phai validate
                        if (CommonActivity.isNullOrEmpty(editngayhethanNew.getText().toString())) {
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateExpiredate),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }

                        Date datenExpired = sdf.parse(editngayhethanNew.getText().toString().trim());
                        if (datenExpired.before(datengaycap)) {
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanngaycap),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }
                        if(datenExpired.before(dateNow)){
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanhientai),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }
                        break;
                    }
                }
            }
            if (CommonActivity.isNullOrEmpty(edit_noicapNew.getText().toString().trim())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                        getActivity().getString(R.string.app_name)).show();
                edit_noicapNew.requestFocus();
                return false;
            }



        }
        if(!CommonActivity.isNullOrEmpty(custTypeDTODialog) && "3".equals(custTypeDTODialog.getCusGroup())){

        }else{
            if (CommonActivity.isNullOrEmpty(edtprovinceNew.getText().toString().trim())) {
                CommonActivity
                        .createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_province),
                                getActivity().getString(R.string.app_name))
                        .show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(edtdistrictNew.getText().toString().trim())) {
                CommonActivity
                        .createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_distrist),
                                getActivity().getString(R.string.app_name))
                        .show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(edtprecinctNew.getText().toString().trim())) {
                CommonActivity
                        .createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_precint),
                                getActivity().getString(R.string.app_name))
                        .show();
                return false;
            }
//            if (CommonActivity.isNullOrEmpty(edtStreetBlockNew.getText().toString().trim())) {
//                CommonActivity
//                        .createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_group),
//                                getActivity().getString(R.string.app_name))
//                        .show();
//                return false;
//            }
        }


        if ("2".equals(custTypeDTODialog.getGroupType())) {

        } else {
            Spin spin = (Spin) spinner_quoctichnew.getSelectedItem();

            if (CommonActivity.isNullOrEmpty(spin)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nationlyEmpty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(spin.getId())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nationlyEmpty),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

        return true;
    }

    private class AsynGetCustomerByCustId extends AsyncTask<String, Void, CustomerDTO> {

        private String errorCode;
        private String description;
        private final Context context;
        private final ProgressDialog progress;
        private final CustIdentityDTO reCustIdentityDTO;

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
                CustomerDTO customerDTODialog = new CustomerDTO();
                if (result != null && result.getCustId() != null) {
                    customerDTODialog = result;
                    // xu ly thong tin khach hang dai dien cu cho nay
                    reCustIdentityDTO.setCustomer(customerDTODialog);
                    custIdentityDTO.setRepreCustomer(reCustIdentityDTO);

                    CreateConnectMobileOmiActivity.instance.tHost.setCurrentTab(1);

                    if (dialogParent != null) {
                        dialogParent.dismiss();
                    }
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
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<custId>").append(custId);
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

    private void showSelectCus(final ArrayList<CustIdentityDTO> lstCusIdentity) {
        dialogCus = new Dialog(getActivity());
        dialogCus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCus.setContentView(R.layout.connection_layout_select_customer);
        dialogCus.setCancelable(false);
        ListView lvcustomerDiaLog = (ListView) dialogCus.findViewById(R.id.listcustomer);
        GetListCustomerBccsAdapter adaGetListCustomerBccsAdapterDialog = new GetListCustomerBccsAdapter(getActivity(), lstCusIdentity, null);
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

    private class AsynGetCustomerByCustIdParent extends AsyncTask<String, Void, CustomerDTO> {

        private String errorCode;
        private String description;
        private final Context context;
        private final ProgressDialog progress;
        private boolean ischeck = false;

        private CustIdentityDTO mCustIdentityDTO;
        private String isPSenTdO;

        public AsynGetCustomerByCustIdParent(Context mContext, boolean mischeck, CustIdentityDTO custIdentityDTOEdit) {
            this.context = mContext;
            this.ischeck = mischeck;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.mCustIdentityDTO = custIdentityDTOEdit;
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
                if (result != null && result.getCustId() != null) {

                    // truong hop sua thong tin khach hang
                    if (mCustIdentityDTO != null) {
                        boolean isCheckCusType = false;
                        if (!CommonActivity.isNullOrEmptyArray(arrCustTypeDTOs)) {
                            for (CustTypeDTO custTypeDTO : arrCustTypeDTOs) {
                                if (result.getCustType().equals(custTypeDTO.getCustType()) && "2".equals(custTypeDTO.getGroupType())) {
                                    isCheckCusType = true;
                                    break;
                                }
                            }
                        }
                        if (isCheckCusType) {
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.edit_customer_busPermitNo), getActivity().getString(R.string.app_name)).show();
                        } else {
                            // sua thong tin khach hang
                            mCustIdentityDTO.setCustomer(result);
                            showEditCustomer(mCustIdentityDTO, isPSenTdO);
                        }
                    } else {
                        // xu ly thong tin khach hang dai dien cu cho nay
                        // truong hop dai dien
                        if (ischeck) {
                            custIdentityDTO.setCustomer(result);
                            showPopupInsertParent(custIdentityDTO);
                        } else {
                            // truogn hop ko phai dai dien
                            custIdentityDTO.setCustomer(result);
                            // truyen thong tin qua tab thong tin hop dong va thue
                            // bao
                            if(!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getListCustIdentity()))
                                if ("BUS".equals(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType()) || "TIN".equals(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType())){
                                    custIdentityDTO.setGroupType("2");
                                }
                            if(adaGetListCustomerBccsAdapter != null){
                                adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                            }
                            CreateConnectMobileOmiActivity.instance.tHost.setCurrentTab(1);
                        }
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
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<custId>").append(custId);
                rawData.append("</custId>");

//                KHCH_TT
                if (!CommonActivity.isNullOrEmpty(mCustIdentityDTO)) {
                    rawData.append("<type>" + "KHCH_TT");
                    rawData.append("</type>");
                } else {
                    rawData.append("<type>" + "");
                    rawData.append("</type>");
                }

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
                    isPSenTdO = parseOuput.getIsPSenTdO();
                    customerDTO = parseOuput.getCustomerDTO();
                }

                return customerDTO;

            } catch (Exception e) {
                Log.e("getCustomerByCustId + exception", e.toString(), e);
            }
            return customerDTO;

        }
    }

    private class AsySearchAccountDebitInfo extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        private final ProgressDialog progress;

        private final CustIdentityDTO custIdentityBO;

        public AsySearchAccountDebitInfo(Activity mActivity, CustIdentityDTO mCustIdentityDTO) {

            this.mActivity = mActivity;

            this.custIdentityBO = mCustIdentityDTO;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected BOCOutput doInBackground(String... params) {
            return searchAccountDebitInfo();
        }

        @Override
        protected void onPostExecute(final BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                if(adaGetListCustomerBccsAdapter != null){
                    adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                }
                // neu khong no cuoc
                if (CommonActivity.isNullOrEmpty(result.getLstAccountDTOs())) {

                    AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(
                            getActivity(), false, null);
                    asynGetCustomerByCustIdParent.execute(custIdentityDTO.getCustomer().getCustId() + "");
                } else {
                    OnClickListener onClick = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("LST_ACCOUNT_INFO", result.getLstAccountDTOs());
                            Intent intent = new Intent(getActivity(), ActivityAccountInfo.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 44);

//                            FragmentAccountInfo fragmentAccountInfo = new FragmentAccountInfo();
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("LST_ACCOUNT_INFO",
//                                    result.getLstAccountDTOs());
////                            bundle.putString(Constant.FUNCTION, funtionType);
//                            fragmentAccountInfo.setArguments(bundle);
//
//                            ReplaceFragment.replaceFragment(getActivity(),
//                                    fragmentAccountInfo, true);
                        }
                    };
                    // chuyen qua man hinh check no cuoc
                    CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.checknocuoc, custIdentityDTO.getIdNo()),
                            getActivity().getString(R.string.app_name), getActivity().getString(R.string.ok),
                            getActivity().getString(R.string.cancel), onClick, null).show();

                }

            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (result.getDescription() == null || result.getDescription().isEmpty()) {
                        result.setDescription(mActivity.getResources().getString(R.string.checkdes));
                    }

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private BOCOutput searchAccountDebitInfo() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "doSearchInfoPay";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                rawData.append("<documentNo>").append(custIdentityBO.getIdNo()).append("</documentNo>");
                rawData.append("<custId>").append(custIdentityBO.getCustomer().getCustId()).append("</custId>");

                rawData.append("<token>").append(Session.getToken()).append("</token>");

                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");

                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());

                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_" + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput.setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "doSearchInfoPay", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private void initNationly(Spinner spinquoctich , boolean check) {
        try {
            BhldDAL dal = new BhldDAL(getActivity());
            dal.open();
            ArrayList<Spin> spinNation = dal.getNationaly();
            dal.close();
            Utils.setDataSpinner(getActivity(), spinNation, spinquoctich);
            if(check){
                for (Spin spin : spinNation) {
                    if ("VN".equals(spin.getId())) {
                        spinNation.remove(spin);
                        spinquoctich.setEnabled(true);
                        break;
                    }
                }
            }
            if (!CommonActivity.isNullOrEmptyArray(spinNation)) {

                for (Spin spin : spinNation) {
                    if ("VN".equals(spin.getId())) {
                        spinquoctich.setSelection(spinNation.indexOf(spin));
                        spinquoctich.setEnabled(false);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            Log.d("initNationly", e.toString());
        }

    }

    private void checkChangePreToPos() {
        // truong hop chuyen doi tu tra truoc sang tra sau
        if (CreateConnectMobileOmiActivity.instance != null) {
            spinner_typethuebao.setEnabled(false);

            if (Constant.CHANGE_PRE_TO_POS
                    .equals(CreateConnectMobileOmiActivity.instance.funtionType)) {
                if (arrSubTypeBeans != null && !arrSubTypeBeans.isEmpty()) {
                    spinner_typethuebao.setSelection(arrSubTypeBeans.indexOf(arrSubTypeBeans.get(1)));
                    spinner_typethuebao.setEnabled(false);
                    subType = "1";
                }
            }
            if (Constant.CHANGE_POS_TO_PRE
                    .equals(CreateConnectMobileOmiActivity.instance.funtionType)) {
                if (arrSubTypeBeans != null && !arrSubTypeBeans.isEmpty()) {
                    spinner_typethuebao.setSelection(arrSubTypeBeans.indexOf(arrSubTypeBeans.get(0)));
                    spinner_typethuebao.setEnabled(false);
                    subType = "2";
                }
            }

            // truong hop chinh chu
            if (CreateConnectMobileOmiActivity.instance.subscriberDTO != null && !CreateConnectMobileOmiActivity.instance.subscriberDTO.isMarkNotOwner()) {

                if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
                    arrCustIdentityDTOs = new ArrayList<>();
                }
                CustIdentityDTO item = new CustIdentityDTO();
                item.setIdNo(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdNo());
                item.setIdIssueDate(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdIssueDate());
                item.setIdIssuePlace(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdIssuePlace());
                item.setCustomer(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput());
                arrCustIdentityDTOs.add(item);
                adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(activity,
                        arrCustIdentityDTOs, imageListenner);
                lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);
                adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                if(!CommonActivity.isNullOrEmpty(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput())){
                    edit_sogiayto.setText(CreateConnectMobileOmiActivity.instance.subscriberDTO.getCustomerDTOInput().getListCustIdentity().get(0).getIdNo());
                }else{
                    edit_sogiayto.setText("");
                }


            } else {
                edit_sogiayto.setText("");
                // truong hop khong chinh chu
                enableView();
            }
        } else {
            edit_sogiayto.setText("");
            spinner_typethuebao.setEnabled(true);
            enableView();
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())&&!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())){
            edit_sogiayto.setText(connectionOrder.getCustomer().getIdNo());
            edit_sogiayto.setEnabled(false);
        }

    }

    private void enableView() {
        edtloaikh.setEnabled(true);
        btnRefreshCustTypeMain.setEnabled(true);
        spinner_typepayper.setEnabled(true);
        btnRefreshTypePaperMain.setEnabled(true);
        edit_sogiayto.setEnabled(true);
        btnsearch.setEnabled(true);
        btnnhapmoi.setEnabled(true);
    }

    private void disableView() {
        edtloaikh.setEnabled(false);
        btnRefreshCustTypeMain.setEnabled(false);
        spinner_typepayper.setEnabled(false);
        btnRefreshTypePaperMain.setEnabled(false);
        edit_sogiayto.setEnabled(false);
        btnsearch.setEnabled(true);
        btnnhapmoi.setEnabled(false);
    }

    // sua thong tin khach hang dau noi mobile
    private void showEditCustomer(final CustIdentityDTO mcustIdentityDTO, final String isPSenTdO) {
        custIdentityDTO = mcustIdentityDTO;
        dialogEditCus = new Dialog(activity);
        dialogEditCus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEditCus.setContentView(R.layout.connectionmobile_layout_edit_customer_bccs2);
        dialogEditCus.setCancelable(true);

        lnngayhethanEdit = (LinearLayout) dialogEditCus.findViewById(R.id.lnngayhethan);
        lnngayhethanEdit.setVisibility(View.GONE);
        edit_ngayhethanEdit = (EditText) dialogEditCus.findViewById(R.id.edit_ngayhethan);

        LinearLayout lnotpChangeCus = (LinearLayout) dialogEditCus.findViewById(R.id.lnotpChangeCus);
        if ("true".equals(isPSenTdO)) {
            lnotpChangeCus.setVisibility(View.VISIBLE);
        } else {
            lnotpChangeCus.setVisibility(View.GONE);
        }

        prbtypePaperEdit = (ProgressBar) dialogEditCus.findViewById(R.id.prbtypePaperEdit);
        spinner_type_giay_toEditCus = (Spinner) dialogEditCus.findViewById(R.id.spinner_type_giay_to);
        spinner_type_giay_toEditCus.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
                    typePaperEditCus = arrTypePaper.get(position).getIdType();
                    lnngayhethanEdit.setVisibility(View.GONE);
                    AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(),new OnPostGetOptionSetValue(),moveLogInAct);
                    asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
                    if("ID".equals(typePaperEditCus)){
                        edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndEditCus.setFilters(FilterArray);
                    }else if("MID".equals(typePaperEditCus)){
                        edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 12;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndEditCus.setFilters(FilterArray);
                    }else if("IDC".equals(typePaperEditCus)){
                        edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 15;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndEditCus.setFilters(FilterArray);
                    }if("PASS".equals(typePaperEditCus)){
                        // truong hop ho chieu va nhom ca nhan nuoc ngoai thi dia chi an di ko can nhap
//                        if(!CommonActivity.isNullOrEmpty(custTypeDTODialog) && "3".equals(custTypeDTODialog.getCusGroup())){
//                            lnAddressCusNew.setVisibility(View.GONE);
//                        }else{
//                            lnAddressCusNew.setVisibility(View.VISIBLE);
//                        }
                        edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndEditCus.setFilters(FilterArray);
                    }else{
                        edit_socmndEditCus.setInputType(InputType.TYPE_CLASS_TEXT);
                        int maxLength = 20;
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                        edit_socmndEditCus.setFilters(FilterArray);
                    }
                } else {
                    typePaperEditCus = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtloaikhEditCus = (EditText) dialogEditCus.findViewById(R.id.edtloaikh);
        edtloaikhEditCus.setEnabled(false);
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())) {
            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustTypeName())) {
                edtloaikhEditCus.setText(custIdentityDTO.getCustomer().getCustTypeName());
            } else {
                if (!CommonActivity.isNullOrEmptyArray(arrCustTypeDTOs)) {
                    for (CustTypeDTO custTypeDTO : arrCustTypeDTOs) {
                        if (custTypeDTO.getCustType().equals(custIdentityDTO.getCustomer().getCustType())) {
                            edtloaikhEditCus.setText(custTypeDTO.getName());
                            break;
                        }
                    }
                } else {
                    edtloaikhEditCus.setText(custIdentityDTO.getCustomer().getCustType());
                }
            }
        }
        // lay thong tin loai giay to
        GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                getActivity(), prbtypePaperEdit, null, spinner_type_giay_toEditCus, custIdentityDTO);
        getMappingCustIdentityUsageAsyn.execute(custIdentityDTO.getCustomer().getCustType());


        edit_tenKHEditCus = (EditText) dialogEditCus.findViewById(R.id.edit_tenKH);
        edit_tenKHEditCus.setText(custIdentityDTO.getCustomer().getName());
        edit_ngaysinhEditCus = (EditText) dialogEditCus.findViewById(R.id.edit_ngaysinh);
        edit_ngaysinhEditCus.setText(StringUtils.convertDate(custIdentityDTO.getCustomer().getBirthDate()));
        edit_ngaysinhEditCus.setOnClickListener(editTextListener);
        edit_socmndEditCus = (EditText) dialogEditCus.findViewById(R.id.edit_socmnd);
        edit_socmndEditCus.setEnabled(false);
        edit_socmndEditCus.setText(custIdentityDTO.getIdNo());

        edit_ngaycapEditCus = (EditText) dialogEditCus.findViewById(R.id.edit_ngaycap);
        if(!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmptyArray(custIdentityDTO.getCustomer().getListCustIdentity())){
            edit_ngaycapEditCus.setText(StringUtils.convertDate(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdIssueDate()));
        }

        edit_ngaycapEditCus.setOnClickListener(editTextListener);
        edit_noicapEditCus = (EditText) dialogEditCus.findViewById(R.id.edit_noicap);
        edit_noicapEditCus.setText(custIdentityDTO.getIdIssuePlace());
        if(!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmptyArray(custIdentityDTO.getCustomer().getListCustIdentity())){
            edit_ngayhethanEdit.setText(StringUtils.convertDate(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdExpireDate()));
        }

        edit_ngayhethanEdit.setOnClickListener(editTextListener);
        initProvince();
        edtprovinceEditCus = (EditText) dialogEditCus.findViewById(R.id.edtprovince);
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getProvince())) {
            initDistrict(custIdentityDTO.getCustomer().getProvince());
            try {
                GetAreaDal dal = new GetAreaDal(activity);
                dal.open();
                edtprovinceEditCus.setText(dal.getNameProvince(custIdentityDTO.getCustomer().getProvince()));
                provinceEdit = custIdentityDTO.getCustomer().getProvince();
                dal.close();
            } catch (Exception e) {
            }
        }
        edtprovinceEditCus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                intent.putExtra("arrProvincesKey", arrProvince);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "1");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 115);
            }
        });
        edtdistrictEditCus = (EditText) dialogEditCus.findViewById(R.id.edtdistrict);
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getProvince()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getDistrict())) {
            initPrecinct(custIdentityDTO.getCustomer().getProvince(), custIdentityDTO.getCustomer().getDistrict());
            try {
                GetAreaDal dal = new GetAreaDal(activity);
                dal.open();
                edtdistrictEditCus.setText(dal.getNameDistrict(custIdentityDTO.getCustomer().getProvince(), custIdentityDTO.getCustomer().getDistrict()));
                districtEdit = custIdentityDTO.getCustomer().getDistrict();
                dal.close();
            } catch (Exception e) {
            }
        }
        edtdistrictEditCus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                intent.putExtra("arrDistrictKey", arrDistrict);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "2");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 116);
            }
        });

        edtprecinctEditCus = (EditText) dialogEditCus.findViewById(R.id.edtprecinct);

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getProvince()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getDistrict()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getPrecinct())) {
            try {
                GetAreaDal dal = new GetAreaDal(activity);
                dal.open();
                edtprecinctEditCus.setText(dal.getNamePrecint(custIdentityDTO.getCustomer().getProvince(), custIdentityDTO.getCustomer().getDistrict(), custIdentityDTO.getCustomer().getPrecinct()));
                precinctEdit = custIdentityDTO.getCustomer().getPrecinct();
                dal.close();
            } catch (Exception e) {
            }
        }
        edtprecinctEditCus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                intent.putExtra("arrPrecinctKey", arrPrecinct);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "3");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 117);
            }
        });


        edtStreetBlockEditCus = (EditText) dialogEditCus.findViewById(R.id.edtStreetBlock);
        streetBlockEdit = custIdentityDTO.getCustomer().getStreetBlock();
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getStreetBlockName())) {
            edtStreetBlockEditCus.setText(custIdentityDTO.getCustomer().getStreetBlockName());
        } else {
            edtStreetBlockEditCus.setText(custIdentityDTO.getCustomer().getStreetBlock());
        }

        edtStreetBlockEditCus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FragmentSearchLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "4");
                mBundle.putString("province", provinceEdit);
                mBundle.putString("district", districtEdit);
                mBundle.putString("precinct", precinctEdit);
                intent.putExtras(mBundle);
                startActivityForResult(intent, 118);
            }
        });


        edt_streetNameEditCus = (EditText) dialogEditCus.findViewById(R.id.edt_streetName);
        edt_streetNameEditCus.setText(custIdentityDTO.getCustomer().getStreetName());

        edtHomeNumberEditCus = (EditText) dialogEditCus.findViewById(R.id.edtHomeNumber);
        edtHomeNumberEditCus.setText(custIdentityDTO.getCustomer().getHome());

        edit_noteEditCus = (EditText) dialogEditCus.findViewById(R.id.edit_note);
        edit_noteEditCus.setText(custIdentityDTO.getCustomer().getDescription());

        edtOTPIsdn = (EditText) dialogEditCus.findViewById(R.id.edtOTPIsdn);
        edtOTPCode = (EditText) dialogEditCus.findViewById(R.id.edtOTPCode);

        spinner_quoctichnewEditCus = (Spinner) dialogEditCus.findViewById(R.id.spinner_quoctichnew);
        initNationlyChangeCus(spinner_quoctichnewEditCus, custIdentityDTO.getCustomer().getNationality());


        spinner_gioitinhEditCus = (Spinner) dialogEditCus.findViewById(R.id.spinner_gioitinh);
        if (!CommonActivity.isNullOrEmptyArray(arrSexBeans)) {
            arrSexBeans = new ArrayList<SexBeans>();
        }

        initSex(spinner_gioitinhEditCus);

        if (!CommonActivity.isNullOrEmpty(arrSexBeans) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getSex())) {
            for (SexBeans item : arrSexBeans) {
                if (item.getValues().equals(custIdentityDTO.getCustomer().getSex())) {
                    spinner_gioitinhEditCus.setSelection(arrSexBeans.indexOf(item));
                    break;
                }
            }
        }

        prbreasonBtn = (ProgressBar) dialogEditCus.findViewById(R.id.prbreason);
        spn_reasonEditCus = (Spinner) dialogEditCus.findViewById(R.id.spn_reason);
        btnRefreshStreetBlockEditCus = (Button) dialogEditCus.findViewById(R.id.btnRefreshStreetBlock);
        GetReasonFullPM getReasonFullPM = new GetReasonFullPM(getActivity(), prbreasonBtn, "152");
        getReasonFullPM.execute(custIdentityDTO.getCustomer().getCustType());


        btncancel = (Button) dialogEditCus.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditCus.cancel();
            }
        });
        btnSendOTP = (Button) dialogEditCus.findViewById(R.id.btnSendOTP);
        btnSendOTP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonActivity.isNullOrEmpty(edtOTPIsdn.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateIsdn), getActivity().getString(R.string.app_name)).show();
                } else {
                    OnClickListener confirm = new OnClickListener() {
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


        btnsuadoi = (Button) dialogEditCus.findViewById(R.id.btnsuadoi);
        btnsuadoi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateEditCus(isPSenTdO)) {
                        OnClickListener onclick = new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DoUpdateCustomerV1Asyn doUpdateCustomerV1Asyn = new DoUpdateCustomerV1Asyn(getActivity(), custIdentityDTO);
                                doUpdateCustomerV1Asyn.execute();

                            }
                        };
                        CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.confirmChangeCus),
                                getActivity().getString(R.string.app_name), getActivity().getString(R.string.ok),
                                getActivity().getString(R.string.cancel), onclick, null).show();
                    }
                } catch (Exception e) {
                    Log.d("btnsuadoi.setOnClickListener", e.toString());
                }


            }
        });


        dialogEditCus.show();
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

        if (CommonActivity.getDiffYears(birthDate, new Date()) < 14) {
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
        // validate ngay het han
        if(!CommonActivity.isNullOrEmpty(arrMapusage)){
            for (Spin spin: arrMapusage) {
                if(CommonActivity.checkMapUsage(typePaperEditCus,spin)){
                    // truong hop ma bat buoc nhap nhat het han thi phai validate
                    if (CommonActivity.isNullOrEmpty(edit_ngayhethanEdit.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateExpiredate),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    Date datenExpired = sdf.parse(edit_ngayhethanEdit.getText().toString().trim());
                    if (datenExpired.before(datengaycap)) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkhethanngaycap),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    break;
                }
            }
        }
        if (CommonActivity.isNullOrEmpty(edit_noicapEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(provinceEdit)){
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.message_pleass_input_province), getActivity().getString(R.string.app_name)).show();
            return false;
        }
//        <string name="message_pleass_input_distrist">Bạn chưa nhập Quận/Huyện,Vui lòng nhập Quận/Huyện</string>
//        <string name="message_pleass_input_precint">Bạn chưa nhập Phư�?ng/xã,Vui lòng nhập Phư�?ng/xã</string>
//        <string name="message_pleass_input_group">Bạn chưa nhập Tổ/Thôn,Vui lòng nhập Tổ/thôn</string>
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

        ReasonDTO reasonDTO = (ReasonDTO) spn_reasonEditCus.getSelectedItem();
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
        return true;
    }


    private OnClickListener imageListenner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Object obj = v.getTag();
            if (obj instanceof CustIdentityDTO) {
                CustIdentityDTO custIdentityDTO = (CustIdentityDTO) obj;
                if (custIdentityDTO != null) {
                    AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(
                            getActivity(), false, custIdentityDTO);
                    asynGetCustomerByCustIdParent.execute(custIdentityDTO.getCustomer().getCustId() + "");
                }
            }
        }
    };

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
                rawData.append("<custId>" + custIdentityDTO.getCustomer().getCustId() + "</custId>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("<actionCode>" + 152 + "</actionCode>");
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

            if ("152".equals(actionCode)) {
                arrReasonChangeCus = new ArrayList<ReasonDTO>();
            }
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    if ("152".equals(actionCode)) {
                        arrReasonChangeCus = result;
                        ReasonDTO item = new ReasonDTO();
                        item.setReasonId("");
                        item.setName(getActivity().getString(R.string.txt_select));
                        arrReasonChangeCus.add(0, item);
                        Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spn_reasonEditCus);
                    }

                } else {


                    if ("152".equals(actionCode)) {
                        arrReasonChangeCus = new ArrayList<ReasonDTO>();
                        Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spn_reasonEditCus);
                    }
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
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

    // thay doi thong tin khach hang
    private class DoUpdateCustomerV1Asyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private CustIdentityDTO mCustIdentityDTO;
        String fulladdress = "";

        public DoUpdateCustomerV1Asyn(Context context, CustIdentityDTO custIdentityDTO) {
            this.mCustIdentityDTO = custIdentityDTO;
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

                if (dialogEditCus != null) {
                    dialogEditCus.dismiss();
                }

//                if (arrCustIdentityDTOs != null && arrCustIdentityDTOs.size() > 0) {
//                    arrCustIdentityDTOs = new ArrayList<CustIdentityDTO>();
//                }
                if(!CommonActivity.isNullOrEmpty(arrCustIdentityDTOs) && !CommonActivity.isNullOrEmptyArray(arrCustIdentityDTOs)){

                    for (CustIdentityDTO item: arrCustIdentityDTOs) {
                        if(!CommonActivity.isNullOrEmpty(mCustIdentityDTO.getCustIdentityId()) && item.getCustIdentityId().equals(mCustIdentityDTO.getCustIdentityId())){
                            item.getCustomer().setAddress(fulladdress);
                            item.setIdIssueDate(edit_ngaycapEditCus.getText().toString());
                            item.setIdIssuePlace(edit_noicapEditCus.getText().toString());
                            item.getCustomer().setName(edit_tenKHEditCus.getText().toString());
                            item.getCustomer().setBirthDate(StringUtils.convertDateToString(edit_ngaysinhEditCus.getText().toString()));
//                            arrCustIdentityDTOs.add(mCustIdentityDTO);
                            if (adaGetListCustomerBccsAdapter != null) {
                                adaGetListCustomerBccsAdapter.notifyDataSetChanged();
                            }
                            break;
                        }
                    }
                }




                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.changeCusSucess), getActivity().getString(R.string.app_name)).show();
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

        private String doUpdateCus() {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_doUpdateCustomerV1");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:doUpdateCustomerV1>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");


                ReasonDTO reasonChangeCus = (ReasonDTO) spn_reasonEditCus.getSelectedItem();
                if (reasonChangeCus != null) {
                    rawData.append("<reasonIdUpdateCustomer>" + reasonChangeCus.getReasonId()
                            + "</reasonIdUpdateCustomer>");
                }


                rawData.append("<customerDTO>");
                rawData.append("<updateCustIdentity>" + true);
                rawData.append("</updateCustIdentity>");
                if (!CommonActivity.isNullOrEmpty(mCustIdentityDTO.getCustomer().getCustId())) {
                    rawData.append("<custId>" + "" + mCustIdentityDTO.getCustomer().getCustId());
                    rawData.append("</custId>");
                } else {
                    rawData.append("<custId>" + "" + mCustIdentityDTO.getCustId());
                    rawData.append("</custId>");
                }


                rawData.append("<name>" + "" + edit_tenKHEditCus.getText().toString().trim());
                rawData.append("</name>");
                rawData.append("<custType>" + "" + mCustIdentityDTO.getCustomer().getCustType());
                rawData.append("</custType>");
                rawData.append("<custTypeDTO>");
                rawData.append("<custType>" + "" + mCustIdentityDTO.getCustomer().getCustType());
                rawData.append("</custType>");
                rawData.append("<groupType>" + "" + mCustIdentityDTO.getCustomer().getCustTypeDTO().getGroupType());
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
                rawData.append("<idIssuePlace>" + "" + CommonActivity.getNormalText(edit_noicapEditCus.getText().toString()));
                rawData.append("</idIssuePlace>");

                if(!CommonActivity.isNullOrEmpty(edit_ngayhethanEdit.getText().toString())){
                    rawData.append("<idExpireDate>" + ""
                            + StringUtils.convertDateToString(edit_ngayhethanEdit.getText().toString()) + "T00:00:00+07:00");
                    rawData.append("</idExpireDate>");
                }

                rawData.append("<required>" + true);
                rawData.append("</required>");

                rawData.append("<status>" + "" + mCustIdentityDTO.getCustomer().getListCustIdentity().get(0).getStatus());
                rawData.append("</status>");


                rawData.append("<createUser>" + "" + mCustIdentityDTO.getCustomer().getListCustIdentity().get(0).getCreateUser());
                rawData.append("</createUser>");
                rawData.append("<createDatetime>" + "" + mCustIdentityDTO.getCustomer().getListCustIdentity().get(0).getCreateDatetime());
                rawData.append("</createDatetime>");


                if (!CommonActivity.isNullOrEmpty(mCustIdentityDTO.getCustomer().getCustId())) {
                    rawData.append("<custId>" + "" + mCustIdentityDTO.getCustomer().getCustId());
                    rawData.append("</custId>");
                } else {
                    rawData.append("<custId>" + "" + mCustIdentityDTO.getCustId());
                    rawData.append("</custId>");
                }

                rawData.append("<custIdentityId>" + "" + mCustIdentityDTO.getCustomer().getListCustIdentity().get(0).getCustIdentityId());
                rawData.append("</custIdentityId>");
//                custId = null
//                custIdentityId = null
//                idNo = "555111888"
//                idType = "PASS"
//                status = "null"
//                createDatetime = null
//                createUser = null
//                updateDatetime = null
//                updateUser = null


                rawData.append("</listCustIdentity>");

                SexBeans sexBean = (SexBeans) arrSexBeans.get(spinner_gioitinhEditCus.getSelectedItemPosition());
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

                rawData.append("<streetBlock>");
                rawData.append("<code>" + "" + streetBlockEdit);
                rawData.append("</code>");
                rawData.append("<name>" + "" + CommonActivity.getNormalText(edtStreetBlockEditCus.getText().toString()));
                rawData.append("</name>");
                rawData.append("</streetBlock>");

                rawData.append("<areaCode>" + "" + provinceEdit + districtEdit + precinctEdit + streetBlockEdit);
                rawData.append("</areaCode>");

                try {
                    GetAreaDal dal = new GetAreaDal(activity);
                    dal.open();

                    if (!CommonActivity.isNullOrEmpty(edtHomeNumberEditCus.getText().toString())) {
                        fulladdress = edtHomeNumberEditCus.getText().toString().trim() + " ";
                    }
                    if (!CommonActivity.isNullOrEmpty(edt_streetNameEditCus.getText().toString())) {
                        fulladdress = fulladdress + edt_streetNameEditCus.getText().toString().trim() + " ";
                    }
                    if (!CommonActivity.isNullOrEmpty(edtStreetBlockEditCus.getText().toString())) {
                        fulladdress = fulladdress + edtStreetBlockEditCus.getText().toString().trim() + " ";
                    }

                    fulladdress = fulladdress + dal.getfulladddress(provinceEdit + districtEdit + precinctEdit);
                    rawData.append("<fullAddress>" + "" + CommonActivity.getNormalText(fulladdress));
                    rawData.append("</fullAddress>");
                    dal.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                rawData.append("</custAdd>");

                rawData.append("<province>" + "" + provinceEdit);
                rawData.append("</province>");
                rawData.append("<district>" + "" + districtEdit);
                rawData.append("</district>");
                rawData.append("<precinct>" + "" + precinctEdit);
                rawData.append("</precinct>");
                rawData.append("<streetBlock>" + "" + streetBlockEdit);
                rawData.append("</streetBlock>");
                rawData.append("<streetBlockName>" + "" + CommonActivity.getNormalText(edtStreetBlockEditCus.getText().toString()));
                rawData.append("</streetBlockName>");

                rawData.append("<home>" + "" + CommonActivity.getNormalText(edtHomeNumberEditCus.getText().toString()));
                rawData.append("</home>");


                rawData.append("<streetName>" + "" + CommonActivity.getNormalText(edt_streetNameEditCus.getText().toString()));
                rawData.append("</streetName>");


                Spin spination = (Spin) spinner_quoctichnewEditCus.getSelectedItem();
                if (!CommonActivity.isNullOrEmpty(spination)
                        && !CommonActivity.isNullOrEmpty(spination.getId())) {
                    rawData.append("<nationality>" + "" + spination.getId());
                    rawData.append("</nationality>");
                }

                rawData.append("<areaCode>" + "" + provinceEdit + districtEdit + precinctEdit + streetBlockEdit);
                rawData.append("</areaCode>");


                rawData.append("<address>" + "" + CommonActivity.getNormalText(fulladdress));
                rawData.append("</address>");


                rawData.append("<description>" + "" + CommonActivity.getNormalText(edit_noteEditCus.getText().toString()));
                rawData.append("</description>");

                rawData.append("<identityNo>" + "" + edit_socmndEditCus.getText().toString());
                rawData.append("</identityNo>");


                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
                rawData.append("</customerDTO>");


                rawData.append("<value>" + edtOTPCode.getText().toString().trim() + "</value>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(edtOTPIsdn.getText().toString().trim()) + "</isdn>");

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
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();

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
                    }else{
                        if ("VN".equals(spin.getId())) {
                            spinquoctich.setSelection(spinNation.indexOf(spin));
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.d("initNationly", e.toString());
        }
    }

    // lay ra danh sach cau hinh so giay to
    private class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {

        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if(lnngayhethanNew != null){
                lnngayhethanNew.setVisibility(View.GONE);
            }
            if(lnngayhethanPr != null){
                lnngayhethanPr.setVisibility(View.GONE);
            }
            if(lnngayhethanEdit != null){
                lnngayhethanEdit.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrMapusage = result;
                for (Spin spin: arrMapusage) {
                    if(!CommonActivity.isNullOrEmpty(typePaperDialog) && CommonActivity.checkMapUsage(typePaperDialog,spin)){
                        if(lnngayhethanNew != null){
                            lnngayhethanNew.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    if(!CommonActivity.isNullOrEmpty(typePaperDialogPR) && CommonActivity.checkMapUsage(typePaperDialogPR,spin)){
                        if(lnngayhethanPr != null){
                            lnngayhethanPr.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    if(!CommonActivity.isNullOrEmpty(typePaperEditCus) && CommonActivity.checkMapUsage(typePaperEditCus,spin)){
                        if(lnngayhethanEdit != null){
                            lnngayhethanEdit.setVisibility(View.VISIBLE);
                        }
                        break;
                    }

                }
            } else {
                arrMapusage = new ArrayList<>();
            }
        }
    }

    private void checkOmichanel() {
        // truong hop chuyen doi tu tra truoc sang tra sau
        if (connectionOrder != null) {
            spinner_typethuebao.setEnabled(false);

            if (arrSubTypeBeans != null && !arrSubTypeBeans.isEmpty()) {
                spinner_typethuebao.setSelection(arrSubTypeBeans.indexOf(arrSubTypeBeans.get(0)));
                spinner_typethuebao.setEnabled(false);

            }


            // truong hop chinh chu
            if (connectionOrder != null && connectionOrder.getCustomer() != null && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getCustId()) && connectionOrder.getCustomer().getCustId() != 0) {


                arrCustIdentityDTOs = new ArrayList<>();

                CustIdentityDTO item = new CustIdentityDTO();
                item.setIdNo(connectionOrder.getCustomer().getIdNo());
                item.setIdIssueDate(connectionOrder.getCustomer().getIdIssueDate());
                item.setIdIssuePlace(connectionOrder.getCustomer().getIdIssuePlace());
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setBirthDate(StringUtils.convertDate(connectionOrder.getCustomer().getBirthDate()));
                customerDTO.setCustId(connectionOrder.getCustomer().getCustId());
                if(!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress())){
                    customerDTO.setAddress(connectionOrder.getCustomer().getAddress().getAddress());
                }

                item.setCustomer(customerDTO);
                arrCustIdentityDTOs.add(item);

                adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(activity,
                        arrCustIdentityDTOs, imageListenner);
                lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);


                edit_sogiayto.setText(connectionOrder.getCustomer().getIdNo());

                disableView();
                custIdentityDTO = item;
                if("1".equals(subType)){
                    AsySearchAccountDebitInfo asySearchAccountDebitInfo = new AsySearchAccountDebitInfo(getActivity(),
                            custIdentityDTO);
                    asySearchAccountDebitInfo.execute();
                }else{
                    AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(
                            getActivity(), false, null);
                    asynGetCustomerByCustIdParent.execute(custIdentityDTO.getCustomer().getCustId() + "");
                }

            }
        }
    }



}
