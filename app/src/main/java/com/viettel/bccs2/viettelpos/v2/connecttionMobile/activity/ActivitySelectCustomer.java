package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.*;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncSearchCustIdentity;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
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
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentSearchCusTypeMobile;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterNewFragment;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinhhq1 on 7/17/2017.
 */
public class ActivitySelectCustomer extends GPSTracker implements View.OnClickListener{
    @BindView(R.id.edtloaikh)
    EditText edtloaikh;
    @BindView(R.id.spinner_type_giay_to)
    Spinner spinner_type_giay_to;
    @BindView(R.id.edit_socmnd)
    EditText edit_socmnd;
    @BindView(R.id.btnkiemtra)
    Button btnkiemtra;
    @BindView(R.id.btnedit)
    Button btnedit;
    @BindView(R.id.edit_tenKH)
    EditText edit_tenKH;
    @BindView(R.id.edit_ngaysinh)
    EditText edit_ngaysinh;
    @BindView(R.id.spinner_gioitinh)
    Spinner spinner_gioitinh;
    @BindView(R.id.edit_ngaycap)
    EditText edit_ngaycap;
    @BindView(R.id.lnngayhethan)
    LinearLayout lnngayhethan;
    @BindView(R.id.editngayhethan)
    EditText edit_ngayhethan;
    @BindView(R.id.edit_noicap)
    EditText edit_noicap;
    @BindView(R.id.spinner_quoctichpr)
    Spinner spinner_quoctichpr;
    @BindView(R.id.lnAddressCusPr)
    LinearLayout lnAddressCusPr;
    @BindView(R.id.edtdiachilapdat)
    EditText edtdiachilapdat;
    @BindView(R.id.btncancel)
    Button btncancel;
    @BindView(R.id.btnthemmoi)
    Button btnthemmoi;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // cau hinh thong tin an theo so giay to
    public static ArrayList<Spin> arrMapusage = new ArrayList<>();
    private String typeMapusage = "";

    private String typePaper = "";
    private ArrayList<SexBeans> arrSexBeans = new ArrayList<>();

    private Dialog dialogCus;

    private CustomerDTO customerDTO;
    private CustTypeDTO custTypeDTO;
    private Activity activity;

    private ArrayList<CustIdentityDTO> arrTypePaper;
    @BindView(R.id.prbTypePaper)
     ProgressBar prbTypePaperPR;
    @BindView(R.id.btnRefreshTypePaper)
     Button btnRefreshTypePR;
    private Date dateNow;
    private String dateNowString;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String provincePR = "";
    private String districtPR = "";
    private String precinctPR = "";
    private String streetBlockPR = "";
    private String home = "";
    private String streetName = "";

    private String subType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectionmobile_layout_select_customer);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subType = getIntent().getExtras().getString("subType","");
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
//        dateNowString=sdf.format(Calendar.getInstance().getTime());

        this.activity = ActivitySelectCustomer.this;
        lnngayhethan.setVisibility(View.GONE);

        edit_ngaycap.setOnClickListener(editTextListener);
        edit_ngaysinh.setOnClickListener(editTextListener);
        btnkiemtra.setOnClickListener(this);
        btnedit.setOnClickListener(this);
        btnthemmoi.setOnClickListener(this);
        edtdiachilapdat.setOnClickListener(this);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spinner_type_giay_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typePaper = arrTypePaper.get(position).getIdType();
                lnngayhethan.setVisibility(View.GONE);
//                if(CommonActivity.isNullOrEmpty(arrMapusage)){
                    AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(ActivitySelectCustomer.this,new OnPostGetOptionSetValue(),moveLogInAct);
                    asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
//                }
                if("ID".equals(typePaper)){
                    edit_socmnd.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int maxLength = 12;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmnd.setFilters(FilterArray);
                }else if("MID".equals(typePaper)){
                    edit_socmnd.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 12;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmnd.setFilters(FilterArray);
                }else if("IDC".equals(typePaper)){
                    edit_socmnd.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int maxLength = 20;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmnd.setFilters(FilterArray);
                }if("PASS".equals(typePaper)){
                    // truong hop ho chieu va nhom ca nhan nuoc ngoai thi dia chi an di ko can nhap
                    if(!CommonActivity.isNullOrEmpty(custTypeDTO) && "3".equals(custTypeDTO.getCusGroup())){
                        lnAddressCusPr.setVisibility(View.GONE);
                    }else{
                        lnAddressCusPr.setVisibility(View.VISIBLE);
                    }
                    edit_socmnd.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 20;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmnd.setFilters(FilterArray);
                }else{
                    edit_socmnd.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 20;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edit_socmnd.setFilters(FilterArray);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        edtloaikh.setOnClickListener(this);
        // them phan gioi tinh
        if(!CommonActivity.isNullOrEmpty(arrSexBeans)){
            arrSexBeans = new ArrayList<>();
        }
        initSex(spinner_gioitinh);

//        if(CommonActivity.isNullOrEmptyArray(arrMapusage)){
            AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(activity,new OnPostGetOptionSetValue(),moveLogInAct,typeMapusage);
            asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
//        }
//        edit_ngayhethan.setOnClickListener(editTextListener);
        edit_ngayhethan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonActivity.showDatePickerDialog(ActivitySelectCustomer.this, edit_ngayhethan);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.selectCust);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // chon loai khach hang
                case 111:
                    if(data != null){
                        custTypeDTO = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                        if (custTypeDTO != null
                                && !CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
                            if(!CommonActivity.isNullOrEmpty(custTypeDTO) && "3".equals(custTypeDTO.getCusGroup())){
                                lnAddressCusPr.setVisibility(View.GONE);
                            }else{
                                lnAddressCusPr.setVisibility(View.VISIBLE);
                            }
                            edtloaikh.setText(custTypeDTO.getName());
                            // lay danh sach loại giay to theo loai khach hang
                            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                                    activity, prbTypePaperPR, btnRefreshTypePR, spinner_type_giay_to, null);
                            getMappingCustIdentityUsageAsyn.execute(custTypeDTO.getCustType());
                            // neu la truong hop nhom khach hang la kh ca nhan nuoc ngoai
                            if(!CommonActivity.isNullOrEmpty(custTypeDTO.getCusGroup()) && "3".equals(custTypeDTO.getCusGroup())){
                                initNationly(spinner_quoctichpr,true);
                            }else{
                                initNationly(spinner_quoctichpr,false);
                            }
                        } else {
                            edtloaikh.setText("");
                        }
                    }
                    break;
                case 2222:

                    AreaObj areaProvicialPR = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    AreaObj areaDistristPR = (AreaObj) data.getExtras().getSerializable("areaDistrist");

                    AreaObj areaPrecintPR = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    AreaObj areaGroupPR = (AreaObj) data.getExtras().getSerializable("areaGroup");

                    String areaFlowPR = data.getExtras().getString("areaFlow");
                    String areaHomeNumberPR = data.getExtras().getString("areaHomeNumber");

                    StringBuilder addressPR = new StringBuilder();

                    if (areaHomeNumberPR != null && areaHomeNumberPR.length() > 0) {
                        home = areaHomeNumberPR;
                        addressPR.append(areaHomeNumberPR).append(" ");
                    }
                    if (areaFlowPR != null && areaFlowPR.length() > 0) {
                        streetName = areaFlowPR;
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
                    edtdiachilapdat.setText(addressPR);

                    break;
            }
        }
    }

    private final View.OnClickListener moveLogInAct = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            MainActivity.getInstance().finish();
        }
    };

    private class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {
        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if(lnngayhethan != null){
                lnngayhethan.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrMapusage = result;
                for (Spin spin: arrMapusage) {
                    if(CommonActivity.checkMapUsage(typePaper,spin)){
                        if(lnngayhethan != null){
                            lnngayhethan.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            } else {
                arrMapusage = new ArrayList<>();
            }
        }
    }
    // ham lay ra thong tin chi tiet khach hang
    private class OnPosGetCustomerByCustId implements  OnPostExecuteListener<CustomerDTO>{
        @Override
        public void onPostExecute(CustomerDTO result, String errorCode, String description) {

            if(dialogCus != null){
                dialogCus.cancel();
            }
            if(!CommonActivity.isNullOrEmpty(result) && !CommonActivity.isNullOrEmpty(result.getCustId())){

                customerDTO = result;
                disableCus(customerDTO);
            }else{
                enableCus();
                customerDTO = new CustomerDTO();

            }

        }
    }
    // ham lay danh sach khach hang
    private class OnPostSearchCustIdentity implements  OnPostExecuteListener<ParseOuput>{
        @Override
        public void onPostExecute(ParseOuput result, String errorCode, String description) {
            if(!CommonActivity.isNullOrEmpty(result) && !CommonActivity.isNullOrEmptyArray(result.getLstCustIdentityDTOs()) ){
                showSelectCus(result.getLstCustIdentityDTOs());
            }else{
                CommonActivity.createAlertDialog(ActivitySelectCustomer.this,
                        ActivitySelectCustomer.this.getString(R.string.not_data_cus),ActivitySelectCustomer.this.getString(R.string.app_name)).show();
            }
        }
    }
    // init gioi tinh
    private void initSex(Spinner spinnerSex) {
        arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivitySelectCustomer.this,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SexBeans sexBeans : arrSexBeans) {
                adapter.add(sexBeans.getName());
            }
            spinnerSex.setAdapter(adapter);
        }
    }

    private void showSelectCus(final ArrayList<CustIdentityDTO> lstCusIdentity) {
        dialogCus = new Dialog(ActivitySelectCustomer.this);
        dialogCus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCus.setContentView(R.layout.connection_layout_select_customer);
        dialogCus.setCancelable(false);
        ListView lvcustomerDiaLog = (ListView) dialogCus.findViewById(R.id.listcustomer);
        GetListCustomerBccsAdapter adaGetListCustomerBccsAdapterDialog = new GetListCustomerBccsAdapter(ActivitySelectCustomer.this, lstCusIdentity, null);
        lvcustomerDiaLog.setAdapter(adaGetListCustomerBccsAdapterDialog);

        lvcustomerDiaLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CustIdentityDTO repreCustomer = lstCusIdentity.get(arg2);
                if(!CommonActivity.isNullOrEmpty(repreCustomer) && !CommonActivity.isNullOrEmpty(repreCustomer.getCustomer())){
                    new AsynGetCustomerByCustId(ActivitySelectCustomer.this,new OnPosGetCustomerByCustId(),moveLogInAct).execute(repreCustomer.getCustomer().getCustId() + "");
                }

            }
        });

        Button btnCancel = (Button) dialogCus.findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogCus.cancel();
            }
        });

        dialogCus.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnkiemtra:
                if(!CommonActivity.isNullOrEmpty(edit_socmnd.getText().toString().trim())){
                    AsyncSearchCustIdentity asyncSearchCustIdentity = new AsyncSearchCustIdentity(activity,new OnPostSearchCustIdentity(),moveLogInAct);
                    asyncSearchCustIdentity.execute(edit_socmnd.getText().toString().trim(),"","");
                }else{
                    CommonActivity.createAlertDialog(activity, activity.getString(R.string.chua_nhap_giay_to),
                            activity.getString(R.string.app_name)).show();
                }
                break;
            case R.id.btnedit:
                enableCus();
                break;
            case R.id.btnthemmoi:
                // truong hop khach hang cu
                if(!CommonActivity.isNullOrEmpty(customerDTO) && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())){
                    Intent data = new Intent();
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("customerInfo", customerDTO);
                    data.putExtras(mBundle);
                    setResult(RESULT_OK, data);
                    finish();
                }else{
                    // truong hop khach hang moi
                    try{
                        if(validateKHCN()){
                            CheckCustIdentityAsyn checkCustIdentityAsyn = new CheckCustIdentityAsyn(activity);
                            checkCustIdentityAsyn.execute(edit_socmnd.getText().toString().trim(),"","");
                        }
                    }catch (Exception e){
                        Log.d("Exception validate",e.toString());
                    }

                }
                break;
//            case R.id.btncacel:
//                onBackPressed();
//                break;
            case R.id.edtloaikh:
                Intent intent = new Intent(ActivitySelectCustomer.this, FragmentSearchCusTypeMobile.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("GROUPKEY","GROUPKEY");
                intent.putExtras(mBundle);

                startActivityForResult(intent, 111);
                break;
            case R.id.edtdiachilapdat:
                if(CommonActivity.isNullOrEmpty(provincePR)){
                    provincePR = Session.province;
                }
                if(CommonActivity.isNullOrEmpty(districtPR)){
                    districtPR = Session.district;
                }

                Bundle mBundle1 = new Bundle();
                mBundle1.putString("strProvince", provincePR);
                mBundle1.putString("strDistris", districtPR);
                mBundle1.putString("strPrecint",precinctPR);
                mBundle1.putString("strStreetBlock",streetBlockPR);
                mBundle1.putString("areaHomeNumber",home);
                mBundle1.putString("areaFlow",streetName);
                mBundle1.putBoolean("isCheckStreetBlock",false);
                mBundle1.putString("KEYPR", "1111");
                Intent i1 = new Intent(activity, CreateAddressCommon.class);
                i1.putExtras(mBundle1);
                startActivityForResult(i1, 2222);
                break;
            default:
                break;
        }
    }

    private void initNationly(Spinner spinquoctich , boolean check) {
        try {
            BhldDAL dal = new BhldDAL(ActivitySelectCustomer.this);
            dal.open();
            ArrayList<Spin> spinNation = dal.getNationaly();
            dal.close();
            Utils.setDataSpinner(ActivitySelectCustomer.this, spinNation, spinquoctich);
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
    private class GetMappingCustIdentityUsageAsyn extends android.os.AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
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
    private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper, CustIdentityDTO custIdentityDTOExt) {
        if (lstTypePaper == null) {
            lstTypePaper = new ArrayList<>();
        }

        ArrayAdapter<String> adapter = null;
        // if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line,
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
    private void enableCus() {
        customerDTO = new CustomerDTO();
        custTypeDTO = null;
        edtloaikh.setText("");
        edtloaikh.setHint(activity.getString(R.string.chonloaiKH));
        arrTypePaper = new ArrayList<>();
        initTypePaper(arrTypePaper, spinner_type_giay_to, null);
        btnedit.setVisibility(View.GONE);
        btnkiemtra.setVisibility(View.VISIBLE);
        edit_socmnd.setText("");
        edit_socmnd.setEnabled(true);
        edit_tenKH.setEnabled(true);
        edit_tenKH.setText("");
        edit_ngaysinh.setEnabled(true);
        lnngayhethan.setVisibility(View.GONE);
        edit_ngayhethan.setText("");
        edit_ngaysinh.setText("");
        edit_ngaycap.setEnabled(true);
        edit_ngaycap.setText("");
        edit_noicap.setEnabled(true);
        edit_noicap.setText("");
        spinner_quoctichpr.setEnabled(true);

        spinner_gioitinh.setEnabled(true);

        edtdiachilapdat.setEnabled(true);
        edtdiachilapdat.setText("");
    }

    private void disableCus(CustomerDTO customerDTO) {
        initNationly(spinner_quoctichpr,false);
        edtloaikh.setEnabled(false);
        if (!CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO()) && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO().getName())) {
            edtloaikh.setText(customerDTO.getCustTypeDTO().getName());
            if (CommonActivity.isNullOrEmptyArray(arrTypePaper)) {
                GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                        activity, prbTypePaperPR, btnRefreshTypePR, spinner_type_giay_to ,customerDTO.getListCustIdentity().get(0));
                getMappingCustIdentityUsageAsyn.execute(customerDTO.getCustType());
            }
        }
        spinner_type_giay_to.setEnabled(false);
        if (!CommonActivity.isNullOrEmptyArray(customerDTO.getListCustIdentity())) {
            if (!CommonActivity.isNullOrEmptyArray(arrTypePaper)) {
                for (CustIdentityDTO custIdentityDTO : arrTypePaper) {
                    if (customerDTO.getListCustIdentity().get(0).getIdNo().equals(custIdentityDTO.getIdNo())) {
                        spinner_type_giay_to.setSelection(arrTypePaper.indexOf(custIdentityDTO));
                        break;
                    }
                }
            }
        }
        btnedit.setVisibility(View.VISIBLE);
        btnkiemtra.setVisibility(View.GONE);
        edit_socmnd.setText(customerDTO.getListCustIdentity().get(0).getIdNo());
        edit_socmnd.setEnabled(false);
        edit_tenKH.setText(customerDTO.getName());
        edit_tenKH.setEnabled(false);
        edit_ngaysinh.setText(StringUtils.convertDate(customerDTO.getBirthDate()));
        if(!CommonActivity.isNullOrEmpty(customerDTO.getListCustIdentity().get(0))){
            if(!CommonActivity.isNullOrEmpty(customerDTO.getListCustIdentity().get(0).getIdIssueDate())){
                edit_ngaycap.setText(customerDTO.getListCustIdentity().get(0).getIdIssueDate());
            }
            edit_ngaycap.setEnabled(false);
            if(!CommonActivity.isNullOrEmpty(customerDTO.getListCustIdentity().get(0).getIdIssuePlace())){
                edit_noicap.setText(customerDTO.getListCustIdentity().get(0).getIdIssuePlace());
            }
            edit_noicap.setEnabled(false);
            if(!CommonActivity.isNullOrEmpty(customerDTO.getListCustIdentity().get(0).getIdExpireDate())){
                edit_ngayhethan.setText(StringUtils.convertDate(customerDTO.getListCustIdentity().get(0).getIdExpireDate()));
                lnngayhethan.setVisibility(View.VISIBLE);
            }
            edit_ngayhethan.setEnabled(false);
        }
        if(!CommonActivity.isNullOrEmpty(customerDTO.getNationality())){
            try {
                BhldDAL dal = new BhldDAL(ActivitySelectCustomer.this);
                dal.open();
                ArrayList<Spin> spinNation = dal.getNationaly();
                dal.close();
                if(!CommonActivity.isNullOrEmptyArray(spinNation) ){
                    for (Spin item: spinNation){
                        if(item.getId().equals(customerDTO.getNationality())){
                            spinner_quoctichpr.setSelection(spinNation.indexOf(item));
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        spinner_quoctichpr.setEnabled(false);
        edtdiachilapdat.setText(customerDTO.getAddress());
        edtdiachilapdat.setEnabled(false);
    }

    private boolean validateKHCN() throws Exception {

        if (custTypeDTO == null) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.confirmloaikh),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.confirmloaikh),
                    activity.getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edit_tenKH.getText().toString())) {

            CommonActivity.createAlertDialog(activity, activity.getString(R.string.checknameKH),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        if (StringUtils.CheckCharSpecical(edit_tenKH.getText().toString())) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkcharspecical),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        if (edit_tenKH.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.namecust),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edit_ngaysinh.getText().toString())) {
            CommonActivity
                    .createAlertDialog(activity, activity.getString(R.string.message_pleass_input_birth_day),
                            activity.getString(R.string.app_name))
                    .show();
            return false;
        }

        Date birthDate = sdf.parse(edit_ngaysinh.getText().toString().trim());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.nsnhohonhtai),
                    activity.getString(R.string.app_name)).show();
            return false;
        }

        if("1".equals(subType)){
            if (CommonActivity.getDiffYearsMain(birthDate, dateNow) < 18) {
                CommonActivity.createAlertDialog(activity, activity.getString(R.string.khhm18),
                        activity.getString(R.string.app_name)).show();
                return false;
            }
        }else{
            if (CommonActivity.getDiffYearsMain(birthDate, dateNow) < 14) {
                CommonActivity.createAlertDialog(activity, activity.getString(R.string.khdd14),
                        activity.getString(R.string.app_name)).show();
                return false;
            }
        }

        if (CommonActivity.isNullOrEmpty(edit_socmnd.getText().toString())) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.chua_nhap_giay_to),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        if ("ID".equals(typePaper)) {

            if (edit_socmnd.getText().toString().length() != 9 && edit_socmnd.getText().toString().length() != 12) {
                CommonActivity.createAlertDialog(activity, activity.getString(R.string.checksoidno),
                        activity.getString(R.string.app_name)).show();
                return false;
            }

        }
        if("MID".equals(typePaper)){
            if(!CommonActivity.isNullOrEmpty(edit_socmnd.getText().toString().trim()) && StringUtils.CheckCharSpecical(edit_socmnd.getText().toString().trim())){
                CommonActivity.createAlertDialog(activity, activity.getString(R.string.cmtqd),
                        activity.getString(R.string.app_name)).show();
                edit_socmnd.requestFocus();
                return false;
            }

        }
        if (CommonActivity.isNullOrEmpty(edit_ngaycap.getText().toString())) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkissueempty),
                    activity.getString(R.string.app_name)).show();
            return false;
        }

        Date datengaycap = sdf.parse(edit_ngaycap.getText().toString().trim());

        if (datengaycap.after(dateNow)) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.ngaycapnhohonngayhientai),
                    activity.getString(R.string.app_name)).show();
            return false;
        }

        if (datengaycap.before(birthDate)) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkcmtngaycap),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        if("ID".equals(typePaper)){
            if (CommonActivity.getDiffYearsMain(birthDate, datengaycap) < 14) {
                CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkcmtngaycap14),
                        activity.getString(R.string.app_name)).show();
                return false;
            }
            if (CommonActivity.getDiffYears(datengaycap, dateNow) > 15) {
                CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkcmtngaycap15),
                        activity.getString(R.string.app_name)).show();
                return false;
            }
        }
        if (CommonActivity.isNullOrEmpty(edit_noicap.getText().toString().trim())) {
            CommonActivity.createAlertDialog(activity, activity.getString(R.string.chua_nhap_noi_cap),
                    activity.getString(R.string.app_name)).show();
            return false;
        }
        // validate ngay het han
        if(!CommonActivity.isNullOrEmpty(arrMapusage)){
            for (Spin spin: arrMapusage) {
                if(CommonActivity.checkMapUsage(typePaper,spin)){
                    // truong hop ma bat buoc nhap nhat het han thi phai validate
                    if (CommonActivity.isNullOrEmpty(edit_ngayhethan.getText().toString())) {
                        CommonActivity.createAlertDialog(activity, activity.getString(R.string.validateExpiredate),
                                activity.getString(R.string.app_name)).show();
                        return false;
                    }

                    Date datenExpired = sdf.parse(edit_ngayhethan.getText().toString().trim());
                    if (datenExpired.before(datengaycap)) {
                        CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkhethanngaycap),
                                activity.getString(R.string.app_name)).show();
                        return false;
                    }
                    if(datenExpired.before(dateNow)){
                        CommonActivity.createAlertDialog(this, this.getString(R.string.checkhethanhientai),
                                this.getString(R.string.app_name)).show();
                        return false;
                    }
                    break;
                }
            }
        }

        // validate thong tin khach hang dai dien
        if(!CommonActivity.isNullOrEmpty(custTypeDTO) && "3".equals(custTypeDTO.getCusGroup())) {

        }else{
            if (CommonActivity.isNullOrEmpty(provincePR) || CommonActivity.isNullOrEmpty(districtPR)
                    || CommonActivity.isNullOrEmpty(precinctPR)) {

                CommonActivity.createAlertDialog(activity, activity.getString(R.string.addKHempty),
                        activity.getString(R.string.app_name)).show();
                return false;

            }
        }

        return true;

    }
    private class CheckCustIdentityAsyn extends AsyncTask<String, Void, ParseOuput> {

        final ProgressDialog progress;
        private Context context = null;
        private String errorCode = "";
        private String description = "";
        public CheckCustIdentityAsyn(Context context) {
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


                            String nameCustomer = edit_tenKH.getText().toString().trim();
                            Log.d("nameCustomer", nameCustomer);
                            String socmt = edit_socmnd.getText().toString().trim();
                            Log.d("socmt", socmt);

                            String birthDate = edit_ngaysinh.getText().toString();
                            Log.d("birthDate", birthDate);
                            String ngaycap = edit_ngaycap.getText().toString();
                            Log.d("ngaycap", ngaycap);
                            String noicap = edit_noicap.getText().toString().trim();
                            Log.d("noicap", noicap);

                            SexBeans sexBean = arrSexBeans.get(spinner_gioitinh.getSelectedItemPosition());

                            CustIdentityDTO item = new CustIdentityDTO();
                            if (!CommonActivity.isNullOrEmpty(typePaper)) {
                                item.setIdType(typePaper);
                            }

                            CustomerDTO cus = new CustomerDTO();
                            cus.setName(nameCustomer);

                            item.setIdNo(socmt.trim());

                            cus.setBirthDate(birthDate);
                            cus.setProvince(provincePR);
                            cus.setDistrict(districtPR);
                            cus.setPrecinct(precinctPR);
                            if(!CommonActivity.isNullOrEmpty(streetBlockPR)){
                                cus.setStreetBlock(streetBlockPR);
                            }
                            if(!CommonActivity.isNullOrEmpty(home)){
                                cus.setHome(home);
                            }
                            if(!CommonActivity.isNullOrEmpty(streetName)){
                                cus.setStreet(streetName);
                                cus.setStreetName(streetName);
                            }
                            if(!CommonActivity.isNullOrEmpty(streetBlockPR)){
                                cus.setAreaCode(provincePR + districtPR + precinctPR + streetBlockPR);
                            }else{
                                cus.setAreaCode(provincePR + districtPR + precinctPR);
                            }

                            cus.setSex(sexBean.getValues());

                            cus.setCustType(custTypeDTO.getCustType());
                            cus.setCustTypeName(custTypeDTO.getName());
                            cus.setGroupType(custTypeDTO.getGroupType());
                            cus.setAddress(edtdiachilapdat.getText().toString());

                            Spin spin = (Spin) spinner_quoctichpr.getSelectedItem();
                            if (!CommonActivity.isNullOrEmpty(spin) && !CommonActivity.isNullOrEmpty(spin.getId())) {
                                cus.setNationality(spin.getId());
                            }
                            item.setIdIssueDate(ngaycap);
                            item.setIdIssuePlace(noicap);
                            item.setIdExpireDate(edit_ngayhethan.getText().toString());
                            ArrayList<CustIdentityDTO> lstCustIdentityDTOs = new ArrayList<>();
                            lstCustIdentityDTOs.add(item);
                            cus.setListCustIdentity(lstCustIdentityDTOs);
                            // quay ve man hinh thong tin thue bao
                            Intent data = new Intent();
                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable("customerInfo", cus);
                            data.putExtras(mBundle);
                            setResult(RESULT_OK, data);
                            finish();
                    }
                } else {
                    if (description != null && !description.isEmpty()) {
                        Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                                activity.getResources().getString(R.string.app_name));
                        dialog.show();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(activity,
                                activity.getResources().getString(R.string.no_data),
                                activity.getResources().getString(R.string.app_name));
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
                    Dialog dialog = CommonActivity.createAlertDialog(activity, description,
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

                if (!CommonActivity.isNullOrEmpty(idType)) {
                    rawData.append("<idType>").append(idType);
                    rawData.append("</idType>");
                }

                rawData.append("</custIdentitySearchDTO>");
                rawData.append("</input>");
                rawData.append("</ws:searchCustIdentity>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
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

    private final View.OnClickListener editTextListener = new View.OnClickListener() {

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
    public static void showDatePickerDialog(Context ctx, final EditText edt) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                edt.setText(DateTimeUtils.convertDateTimeToString(
                        cal.getTime(), "dd/MM/yyyy"));
            }
        };

        Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(),
                "dd/MM/yyyy");
        if(date != null){
            cal.setTime(date);
        }

        DatePickerDialog pic = new FixedHoloDatePickerDialog(ctx, AlertDialog.THEME_HOLO_LIGHT,callback,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        pic.setTitle(ctx.getString(R.string.chon_ngay));
        pic.show();
    }
}
