package com.viettel.bss.viettelpos.v4.omichanel.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.OmiChanelCustomerFragment;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SubTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.GetCustomerByCustIdParentAsyn;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.GetMappingChannelCustTypeAsyn;
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
 * Created by hantt47 on 9/27/2017.
 */

public class EditCustomerDialog extends DialogFragment {
    public static ArrayList<CustTypeDTO> arrCustTypeDTOs = new ArrayList<>();
    // cau hinh thong tin an theo so giay to
    public static ArrayList<Spin> arrMapusage = new ArrayList<>();
    private final ArrayList<SubTypeBean> arrSubTypeBeans = new ArrayList<>();
     Context context;
//    private String custId;
    private EditText edtloaikhEditCus, edit_tenKHEditCus, edit_ngaysinhEditCus, edit_socmndEditCus, edit_ngaycapEditCus,
            edit_noicapEditCus, edtprovinceEditCus, edtdistrictEditCus, edtprecinctEditCus, edtStreetBlockEditCus,
            edt_streetNameEditCus, edtHomeNumberEditCus, edit_noteEditCus, edtOTPIsdn, edtOTPCode;
    private ProgressBar prbCustTypeEditCus, prbreasonBtn, prbtypePaperEdit;
    private LinearLayout lnngayhethanEdit;
    private LinearLayout lnngayhethanPr ,lnAddressCusPr, lnngayhethanNew;
    private EditText edit_ngayhethanEdit;
    private Spinner spinner_gioitinhEditCus, spinner_type_giay_toEditCus, spn_reasonEditCus, spinner_quoctichnewEditCus;
    private Button btnRefreshStreetBlockEditCus, btnsuadoi, btncancel, btnSendOTP;
    private String provinceEdit = "";
    private String districtEdit = "";
    private String precinctEdit = "";
    private String streetBlockEdit = "";
    private String typePaperEditCus;
    private List<ReasonDTO> arrReasonChangeCus = new ArrayList<ReasonDTO>();
    private View.OnClickListener moveLogInAct;
    private ConnectionOrder connectionOrder;
    private String subType = "";
    private CustIdentityDTO custIdentityDTO;
    private String isPSenTdO;
    private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<>();
    private ArrayList<AreaBean> arrProvince = new ArrayList<>();
    private String province = "";
    // arrlist district
    private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
    private String district = "";
    // arrlist precinct
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
    private String precinct = "";
    private ArrayList<SexBeans> arrSexBeans = new ArrayList<>();
    private String typeMapusage = "";
    private List<CustIdentityDTO> arrCustIdentityDTOs = new ArrayList<>();

    private Activity activity;
    private View mView = null;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateNow;
    private String dateNowString;
    private String typePaperDialog,typePaperDialogPR;





    public EditCustomerDialog(Context context,  View.OnClickListener moveLogInAct, ConnectionOrder connectionOrder, CustIdentityDTO custIdentityDTO) {
        this.context = context;
//        this.custId = custId;
        this.moveLogInAct = moveLogInAct;
        this.connectionOrder = connectionOrder;
        this.custIdentityDTO = custIdentityDTO;
    }

  


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
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

        if(mView == null) {
            mView = inflater.inflate(R.layout.connectionmobile_layout_edit_customer_bccs2, container, false);
        }
        return mView;
    }

    public void unit() {
        initSub();
        arrTypePaper = new ArrayList<>();
        initTypePaper(arrTypePaper, spinner_type_giay_toEditCus, null);
        lnngayhethanPr = (LinearLayout) mView.findViewById(R.id.lnngayhethanPr);
        lnAddressCusPr = (LinearLayout) mView.findViewById(R.id.lnAddressCusPr);
        lnngayhethanNew = (LinearLayout) mView.findViewById(R.id.lnngayhethan);
        lnngayhethanNew.setVisibility(View.GONE);
        GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(context, new OnPostExecuteListener<ArrayList<CustTypeDTO>>() {
            @Override
            public void onPostExecute(ArrayList<CustTypeDTO> result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    if (result != null && !result.isEmpty()) {
                        arrCustTypeDTOs = result;
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(context,
                                context.getResources().getString(R.string.checkTypeCus),
                                context.getResources().getString(R.string.app_name));
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
                        Dialog dialog = CommonActivity.createAlertDialog(context, description,
                                context.getResources().getString(R.string.app_name));
                        dialog.show();

                    }
                }
            }
        });
        getMappingChannelCustTypeAsyn.execute(subType);

        GetCustomerByCustIdParentAsyn getCustomerByCustIdParentAsyn = new GetCustomerByCustIdParentAsyn(context, false, custIdentityDTO, new OnPostExecuteListener<ParseOuput>() {
            @Override
            public void onPostExecute(ParseOuput result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    // thong tin hop dong cu
                    CustomerDTO customerDTO = result.getCustomerDTO();
                    isPSenTdO = result.getIsPSenTdO();
                    if (result != null && customerDTO.getCustId() != null) {

                        // truong hop sua thong tin khach hang
                        if (custIdentityDTO != null) {
                            boolean isCheckCusType = false;
                            if (!CommonActivity.isNullOrEmptyArray(arrCustTypeDTOs)) {
                                for (CustTypeDTO custTypeDTO : arrCustTypeDTOs) {
                                    if (customerDTO.getCustType().equals(custTypeDTO.getCustType()) && "2".equals(custTypeDTO.getGroupType())) {
                                        isCheckCusType = true;
                                        break;
                                    }
                                }
                            }
                            if (isCheckCusType) {
                                CommonActivity.createAlertDialog(context, context.getString(R.string.edit_customer_busPermitNo), context.getString(R.string.app_name)).show();
                            } else {
                                // sua thong tin khach hang
                                custIdentityDTO.setCustomer(customerDTO);
                            }
                        }
                    } else {
                        CommonActivity.createAlertDialog(context, context.getString(R.string.notDetailCus),
                                context.getString(R.string.app_name)).show();
                    }

                } else {
                    if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                        Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                                context.getString(R.string.app_name), moveLogInAct);
                        dialog.show();
                    } else {
                        if (description == null || description.isEmpty()) {
                            description = context.getString(R.string.checkdes);
                        }
                        Dialog dialog = CommonActivity.createAlertDialog(context, description,
                                context.getString(R.string.app_name));
                        dialog.show();

                    }
                }
            }
        });

        getCustomerByCustIdParentAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "");

        lnngayhethanEdit = (LinearLayout) mView.findViewById(R.id.lnngayhethan);
        lnngayhethanEdit.setVisibility(View.GONE);
        edit_ngayhethanEdit = (EditText) mView.findViewById(R.id.edit_ngayhethan);

        LinearLayout lnotpChangeCus = (LinearLayout) mView.findViewById(R.id.lnotpChangeCus);

        if ("true".equals(isPSenTdO)) {
            lnotpChangeCus.setVisibility(View.VISIBLE);
        } else {
            lnotpChangeCus.setVisibility(View.GONE);
        }

        prbtypePaperEdit = (ProgressBar) mView.findViewById(R.id.prbtypePaperEdit);
        spinner_type_giay_toEditCus = (Spinner) mView.findViewById(R.id.spinner_type_giay_to);
        spinner_type_giay_toEditCus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
                    typePaperEditCus = arrTypePaper.get(position).getIdType();
                    lnngayhethanEdit.setVisibility(View.GONE);
                    AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue((Activity) context, new OnPostGetOptionSetValue(), moveLogInAct);
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
                    } else {
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
        edtloaikhEditCus = (EditText) mView.findViewById(R.id.edtloaikh);
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
                context, prbtypePaperEdit, null, spinner_type_giay_toEditCus, custIdentityDTO);
        getMappingCustIdentityUsageAsyn.execute(custIdentityDTO.getCustomer().getCustType());


        edit_tenKHEditCus = (EditText) mView.findViewById(R.id.edit_tenKH);
        edit_tenKHEditCus.setText(custIdentityDTO.getCustomer().getName());
        edit_ngaysinhEditCus = (EditText) mView.findViewById(R.id.edit_ngaysinh);
        edit_ngaysinhEditCus.setText(StringUtils.convertDate(custIdentityDTO.getCustomer().getBirthDate()));
        edit_ngaysinhEditCus.setOnClickListener(editTextListener);
        edit_socmndEditCus = (EditText) mView.findViewById(R.id.edit_socmnd);
        edit_socmndEditCus.setEnabled(false);
        edit_socmndEditCus.setText(custIdentityDTO.getIdNo());

        edit_ngaycapEditCus = (EditText) mView.findViewById(R.id.edit_ngaycap);
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmptyArray(custIdentityDTO.getCustomer().getListCustIdentity())) {
            edit_ngaycapEditCus.setText(StringUtils.convertDate(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdIssueDate()));
        }

        edit_ngaycapEditCus.setOnClickListener(editTextListener);
        edit_noicapEditCus = (EditText) mView.findViewById(R.id.edit_noicap);
        edit_noicapEditCus.setText(custIdentityDTO.getIdIssuePlace());
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmptyArray(custIdentityDTO.getCustomer().getListCustIdentity())) {
            edit_ngayhethanEdit.setText(StringUtils.convertDate(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdExpireDate()));
        }

        edit_ngayhethanEdit.setOnClickListener(editTextListener);
        initProvince();
        edtprovinceEditCus = (EditText) mView.findViewById(R.id.edtprovince);
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getProvince())) {
            initDistrict(custIdentityDTO.getCustomer().getProvince());
            try {
                GetAreaDal dal = new GetAreaDal(context);
                dal.open();
                edtprovinceEditCus.setText(dal.getNameProvince(custIdentityDTO.getCustomer().getProvince()));
                provinceEdit = custIdentityDTO.getCustomer().getProvince();
                dal.close();
            } catch (Exception e) {
            }
        }
        edtprovinceEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FragmentSearchLocation.class);
                intent.putExtra("arrProvincesKey", arrProvince);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "1");
                intent.putExtras(mBundle);
                activity.startActivityForResult(intent, 115);
            }
        });
        edtdistrictEditCus = (EditText) mView.findViewById(R.id.edtdistrict);
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
        edtdistrictEditCus.setOnClickListener(new View.OnClickListener() {
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

        edtprecinctEditCus = (EditText) mView.findViewById(R.id.edtprecinct);

        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getProvince()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getDistrict()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getPrecinct())) {
            try {
                GetAreaDal dal = new GetAreaDal(context);
                dal.open();
                edtprecinctEditCus.setText(dal.getNamePrecint(custIdentityDTO.getCustomer().getProvince(), custIdentityDTO.getCustomer().getDistrict(), custIdentityDTO.getCustomer().getPrecinct()));
                precinctEdit = custIdentityDTO.getCustomer().getPrecinct();
                dal.close();
            } catch (Exception e) {
            }
        }
        edtprecinctEditCus.setOnClickListener(new View.OnClickListener() {
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


        edtStreetBlockEditCus = (EditText) mView.findViewById(R.id.edtStreetBlock);
        streetBlockEdit = custIdentityDTO.getCustomer().getStreetBlock();
        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getStreetBlockName())) {
            edtStreetBlockEditCus.setText(custIdentityDTO.getCustomer().getStreetBlockName());
        } else {
            edtStreetBlockEditCus.setText(custIdentityDTO.getCustomer().getStreetBlock());
        }

        edtStreetBlockEditCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FragmentSearchLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "4");
                mBundle.putString("province", provinceEdit);
                mBundle.putString("district", districtEdit);
                mBundle.putString("precinct", precinctEdit);
                intent.putExtras(mBundle);
                startActivityForResult(intent, 118);
            }
        });


        edt_streetNameEditCus = (EditText) mView.findViewById(R.id.edt_streetName);
        edt_streetNameEditCus.setText(custIdentityDTO.getCustomer().getStreetName());

        edtHomeNumberEditCus = (EditText) mView.findViewById(R.id.edtHomeNumber);
        edtHomeNumberEditCus.setText(custIdentityDTO.getCustomer().getHome());

        edit_noteEditCus = (EditText) mView.findViewById(R.id.edit_note);
        edit_noteEditCus.setText(custIdentityDTO.getCustomer().getDescription());

        edtOTPIsdn = (EditText) mView.findViewById(R.id.edtOTPIsdn);
        edtOTPCode = (EditText) mView.findViewById(R.id.edtOTPCode);

        spinner_quoctichnewEditCus = (Spinner) mView.findViewById(R.id.spinner_quoctichnew);
        initNationlyChangeCus(spinner_quoctichnewEditCus, custIdentityDTO.getCustomer().getNationality());


        spinner_gioitinhEditCus = (Spinner) mView.findViewById(R.id.spinner_gioitinh);
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

        prbreasonBtn = (ProgressBar) mView.findViewById(R.id.prbreason);
        spn_reasonEditCus = (Spinner) mView.findViewById(R.id.spn_reason);
        btnRefreshStreetBlockEditCus = (Button) mView.findViewById(R.id.btnRefreshStreetBlock);
        GetReasonFullPM getReasonFullPM = new GetReasonFullPM(context, prbreasonBtn, "152");
        getReasonFullPM.execute(custIdentityDTO.getCustomer().getCustType());


        btncancel = (Button) mView.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSendOTP = (Button) mView.findViewById(R.id.btnSendOTP);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonActivity.isNullOrEmpty(edtOTPIsdn.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(context, context.getString(R.string.validateIsdn), context.getString(R.string.app_name)).show();
                } else {
                    View.OnClickListener confirm = new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {

                            SendOTPUtilAsyn sendOTPUtilAsyn = new SendOTPUtilAsyn(context);
                            sendOTPUtilAsyn.execute(edtOTPIsdn.getText().toString().trim());
                        }
                    };
                    CommonActivity
                            .createDialog((Activity) context, context.getString(R.string.sendotpds, edtOTPIsdn.getText().toString().trim()),
                                    context.getString(R.string.app_name), context.getString(R.string.OK),
                                    context.getString(R.string.cancel), confirm, null)
                            .show();

                }


            }
        });


        btnsuadoi = (Button) mView.findViewById(R.id.btnsuadoi);
        btnsuadoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateEditCus(isPSenTdO)) {
                        View.OnClickListener onclick = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DoUpdateCustomerV1Asyn doUpdateCustomerV1Asyn = new DoUpdateCustomerV1Asyn(context, custIdentityDTO);
                                doUpdateCustomerV1Asyn.execute();

                            }
                        };
                        CommonActivity.createDialog((Activity) context, context.getString(R.string.confirmChangeCus),
                                context.getString(R.string.app_name), context.getString(R.string.ok),
                                context.getString(R.string.cancel), onclick, null).show();
                    }
                } catch (Exception e) {
                    Log.d("btnsuadoi.setOnClickListener", e.toString());
                }


            }
        });


        if (connectionOrder != null && Constant.ORD_TYPE_CONNECT_PREPAID.equals(connectionOrder.getOrderType())) {
            subType = "2";
        } else {
            if (connectionOrder != null && Constant.ORD_TYPE_CONNECT_PREPAID.equals(connectionOrder.getOrderType())) {
                subType = "1";
            }
        }


    }

    private void initSub() {
        if (connectionOrder != null && Constant.ORD_TYPE_CONNECT_PREPAID.equals(connectionOrder.getOrderType())) {
            arrSubTypeBeans.add(new SubTypeBean(context.getString(R.string.subfirst), "2"));
        } else {
            arrSubTypeBeans.add(new SubTypeBean(context.getString(R.string.sublast), "1"));
        }
    }

    private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper, CustIdentityDTO custIdentityDTOExt) {
        if (lstTypePaper == null) {
            lstTypePaper = new ArrayList<>();
        }

        ArrayAdapter<String> adapter = null;
        // if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line,
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

    private boolean validateEditCus(String isPSenTdO) throws Exception {

        if (CommonActivity.isNullOrEmpty(edit_tenKHEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.chua_nhap_ten_kh), context.getString(R.string.app_name)).show();
            return false;
        }
        if (StringUtils.CheckCharSpecical(edit_tenKHEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.checkcharspecical),
                    context.getString(R.string.app_name)).show();
            return false;
        }
        if (edit_tenKHEditCus.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.namecust), context.getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edit_ngaysinhEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.ngaysinhempty), context.getString(R.string.app_name)).show();
            return false;
        }

        Date birthDate = sdf.parse(edit_ngaysinhEditCus.getText().toString());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.nsnhohonhtai),
                    context.getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.getDiffYears(birthDate, new Date()) < 14) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.khdd14),
                    context.getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edit_socmndEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.chua_nhap_giay_to),
                    context.getString(R.string.app_name)).show();
            return false;
        }
        if ("ID".equals(typePaperEditCus)) {

            if (edit_socmndEditCus.getText().toString().length() != 9 && edit_socmndEditCus.getText().toString().length() != 12) {
                CommonActivity.createAlertDialog(context, context.getString(R.string.checksoidno),
                        context.getString(R.string.app_name)).show();
                return false;
            }
        }

        if (CommonActivity.isNullOrEmpty(edit_ngaycapEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.checkissueempty),
                    context.getString(R.string.app_name)).show();
            return false;
        }

        Date datengaycap = sdf.parse(edit_ngaycapEditCus.getText().toString());

        if (datengaycap.after(dateNow)) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.ngaycapnhohonngayhientai),
                    context.getString(R.string.app_name)).show();
            return false;
        }

        if (datengaycap.before(birthDate)) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.checkcmtngaycap),
                    context.getString(R.string.app_name)).show();
            return false;
        }
        // validate ngay het han
        if (!CommonActivity.isNullOrEmpty(arrMapusage)) {
            for (Spin spin : arrMapusage) {
                if (CommonActivity.checkMapUsage(typePaperEditCus, spin)) {
                    // truong hop ma bat buoc nhap nhat het han thi phai validate
                    if (CommonActivity.isNullOrEmpty(edit_ngayhethanEdit.getText().toString())) {
                        CommonActivity.createAlertDialog(context, context.getString(R.string.validateExpiredate),
                                context.getString(R.string.app_name)).show();
                        return false;
                    }

                    Date datenExpired = sdf.parse(edit_ngayhethanEdit.getText().toString().trim());
                    if (datenExpired.before(datengaycap)) {
                        CommonActivity.createAlertDialog(context, context.getString(R.string.checkhethanngaycap),
                                context.getString(R.string.app_name)).show();
                        return false;
                    }
                    break;
                }
            }
        }
        if (CommonActivity.isNullOrEmpty(edit_noicapEditCus.getText().toString())) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.chua_nhap_noi_cap),
                    context.getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(provinceEdit)) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.message_pleass_input_province), context.getString(R.string.app_name)).show();
            return false;
        }
//        <string name="message_pleass_input_distrist">Bạn chưa nhập Quận/Huyện,Vui lòng nhập Quận/Huyện</string>
//        <string name="message_pleass_input_precint">Bạn chưa nhập Phư�?ng/xã,Vui lòng nhập Phư�?ng/xã</string>
//        <string name="message_pleass_input_group">Bạn chưa nhập Tổ/Thôn,Vui lòng nhập Tổ/thôn</string>
        if (CommonActivity.isNullOrEmpty(districtEdit)) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.message_pleass_input_distrist), context.getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(precinctEdit)) {
            CommonActivity.createAlertDialog(context, context.getString(R.string.message_pleass_input_precint), context.getString(R.string.app_name)).show();
            return false;
        }
//        if (CommonActivity.isNullOrEmpty(streetBlockEdit)) {
//            CommonActivity.createAlertDialog(context, context.getString(R.string.message_pleass_input_group), context.getString(R.string.app_name)).show();
//            return false;
//        }

        ReasonDTO reasonDTO = (ReasonDTO) spn_reasonEditCus.getSelectedItem();
        if (reasonDTO == null) {
            CommonActivity.createAlertDialog(context,
                    context.getString(R.string.validate_reason_changecus),
                    context.getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
            CommonActivity.createAlertDialog(context,
                    context.getString(R.string.validate_reason_changecus),
                    context.getString(R.string.app_name)).show();
            return false;
        }
        if ("true".equals(isPSenTdO)) {
            if (CommonActivity.isNullOrEmpty(edtOTPIsdn.getText().toString().trim())) {
                CommonActivity.createAlertDialog(context, context.getString(R.string.validateIsdn), context.getString(R.string.app_name)).show();
                return false;
            }


            if (CommonActivity.isNullOrEmpty(edtOTPCode.getText().toString())) {
                CommonActivity.createAlertDialog(context, context.getString(R.string.validateotp),
                        context.getString(R.string.app_name)).show();
                return false;
            }
        }
        return true;
    }

    // lay ma tinh/thanhpho
    private void initProvince() {
        try {
            GetAreaDal dal = new GetAreaDal(context);
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
            GetAreaDal dal = new GetAreaDal(context);
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
            GetAreaDal dal = new GetAreaDal(context);
            dal.open();
            arrPrecinct = dal.getLstPrecinct(province, district);
            dal.close();
        } catch (Exception ex) {
            Log.e("initPrecinct", ex.toString());
        }
    }

    private void initNationlyChangeCus(Spinner spinquoctich, String nation) {
        try {
            BhldDAL dal = new BhldDAL(context);
            dal.open();
            ArrayList<Spin> spinNation = dal.getNationaly();
            dal.close();
            Utils.setDataSpinner(context, spinNation, spinquoctich);

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
            Log.d("initNationly", e.toString());
        }
    }

    // init gioi tinh
    private void initSex(Spinner spinnerSex) {
        arrSexBeans.add(new SexBeans(context.getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(context.getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            for (SexBeans sexBeans : arrSexBeans) {
                adapter.add(sexBeans.getName());
            }
            spinnerSex.setAdapter(adapter);
        }
    }

    private class GetMappingCustIdentityUsageAsyn extends AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
        final ProgressBar prbarCus;
        final Button btnRefresh;
        final Spinner spinerPaper;
        String errorCode = "";
        String description = "";
        CustIdentityDTO custIdentityDTO;
        private Context context = null;

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
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            context.getResources().getString(R.string.notpapaer), context.getResources().getString(R.string.app_name));
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
                    Dialog dialog = CommonActivity.createAlertDialog(context, description,
                            context.getResources().getString(R.string.app_name));
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
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
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

    // thay doi thong tin khach hang
    private class DoUpdateCustomerV1Asyn extends AsyncTask<String, Void, String> {
        String fulladdress = "";
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private CustIdentityDTO mCustIdentityDTO;

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

                if (getShowsDialog()) {
                    dismiss();
                }

                if (!CommonActivity.isNullOrEmpty(arrCustIdentityDTOs) && !CommonActivity.isNullOrEmptyArray(arrCustIdentityDTOs)) {

                    for (CustIdentityDTO item : arrCustIdentityDTOs) {
                        if (!CommonActivity.isNullOrEmpty(mCustIdentityDTO.getCustIdentityId()) && item.getCustIdentityId().equals(mCustIdentityDTO.getCustIdentityId())) {
                            item.getCustomer().setAddress(fulladdress);
                            item.setIdIssueDate(edit_ngaycapEditCus.getText().toString());
                            item.setIdIssuePlace(edit_noicapEditCus.getText().toString());
                            item.getCustomer().setName(edit_tenKHEditCus.getText().toString());
                            item.getCustomer().setBirthDate(StringUtils.convertDateToString(edit_ngaysinhEditCus.getText().toString()));
//                            arrCustIdentityDTOs.add(mCustIdentityDTO);

                            break;
                        }
                    }
                }


                CommonActivity.createAlertDialog(context, context.getString(R.string.changeCusSucess), context.getString(R.string.app_name)).show();
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context, description,
                            context.getResources().getString(R.string.app_name));
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

                if (!CommonActivity.isNullOrEmpty(edit_ngayhethanEdit.getText().toString())) {
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

                SexBeans sexBean = arrSexBeans.get(spinner_gioitinhEditCus.getSelectedItemPosition());
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
                    GetAreaDal dal = new GetAreaDal(context);
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
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context, "mbccs_doUpdateCustomerV1");
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
                    description = context.getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("doUpdateCustomerV1", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = context.getString(R.string.no_data_return);
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

                CommonActivity.createAlertDialog(context,
                        context.getString(R.string.confirmotpds, edtOTPIsdn.getText().toString().trim()),
                        context.getString(R.string.app_name)).show();

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context, description,
                            context.getResources().getString(R.string.app_name));
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
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context, "mbccs_sendOTPUtil4UpdateCustomer");
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
                    description = context.getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("sendOtpUntil", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = context.getString(R.string.no_data_return);
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
                        item.setName(context.getString(R.string.txt_select));
                        arrReasonChangeCus.add(0, item);
                        Utils.setDataReasonDTO(context, arrReasonChangeCus, spn_reasonEditCus);
                    }

                } else {


                    if ("152".equals(actionCode)) {
                        arrReasonChangeCus = new ArrayList<ReasonDTO>();
                        Utils.setDataReasonDTO(context, arrReasonChangeCus, spn_reasonEditCus);
                    }
                    CommonActivity.createAlertDialog(context, context.getString(R.string.no_data),
                            context.getString(R.string.app_name)).show();
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
                    Dialog dialog = CommonActivity.createAlertDialog(context, description,
                            context.getString(R.string.app_name));
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
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
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

    // lay ra danh sach cau hinh so giay to
    private class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {

        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if (lnngayhethanNew != null) {
                lnngayhethanNew.setVisibility(View.GONE);
            }
            if (lnngayhethanPr != null) {
                lnngayhethanPr.setVisibility(View.GONE);
            }
            if (lnngayhethanEdit != null) {
                lnngayhethanEdit.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrMapusage = result;
                for (Spin spin : arrMapusage) {
                    if (!CommonActivity.isNullOrEmpty(typePaperDialog) && CommonActivity.checkMapUsage(typePaperDialog, spin)) {
                        if (lnngayhethanNew != null) {
                            lnngayhethanNew.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    if (!CommonActivity.isNullOrEmpty(typePaperDialogPR) && CommonActivity.checkMapUsage(typePaperDialogPR, spin)) {
                        if (lnngayhethanPr != null) {
                            lnngayhethanPr.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    if (!CommonActivity.isNullOrEmpty(typePaperEditCus) && CommonActivity.checkMapUsage(typePaperEditCus, spin)) {
                        if (lnngayhethanEdit != null) {
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

                default:
                    break;
            }
        }
    }
}

