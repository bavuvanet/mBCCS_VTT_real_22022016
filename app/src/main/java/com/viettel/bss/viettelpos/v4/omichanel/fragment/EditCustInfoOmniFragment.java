package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Address;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Customer;
import com.viettel.bss.viettelpos.v4.customer.object.CusCommons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by thuandq on 09/09/2017.
 */

public class EditCustInfoOmniFragment extends FragmentCommon {

    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.editCustName)
    EditText editCustName;
    @BindView(R.id.editBirthDay)
    EditText editBirthDay;
    @BindView(R.id.editId)
    EditText editId;
    @BindView(R.id.editIdIssueDate)
    EditText editIdIssueDate;
    @BindView(R.id.editIdIssuePlace)
    EditText editIdIssuePlace;
    @BindView(R.id.editProvince)
    EditText editProvince;
    @BindView(R.id.editDistrict)
    EditText editDistrict;
    @BindView(R.id.editHomeNumber)
    EditText editHomeNumber;
    @BindView(R.id.editPrecinct)
    EditText editPrecinct;

    private Customer customer;
    private String orderType;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfV = new SimpleDateFormat("dd/MM/yyy");
    private Date birthDay = Calendar.getInstance().getTime();
    private Date issueDate = Calendar.getInstance().getTime();

    private ArrayList<AreaBean> arrProvince = new ArrayList<>();
    private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();

    private String provinceEdit = "";
    private String districtEdit = "";
    private String precinctEdit = "";

    public EditCustInfoOmniFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.layout_edit_cust_info_omichanel_fragment;
        ButterKnife.bind(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getActivity()
                .getString(R.string.edit_omichannel_info_customer));
    }

    @Override
    protected void unit(View v) {

        this.arrProvince = CusCommons.getProvince(getContext());
        Bundle bundle = getArguments();
        ConnectionOrder connectionOrder = (ConnectionOrder) bundle.getSerializable("connectionOrder");
        this.customer = connectionOrder.getCustomer();
        this.orderType = connectionOrder.getOrderType();
        if (CommonActivity.isNullOrEmpty(customer)) {
            customer = new Customer();
        }
        fillData(customer);
        if (!CommonActivity.isNullOrEmpty(customer.getIdNo())) {
            editId.setEnabled(false);
        }

        editBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(birthDayPickerListener, birthDay);
            }
        });

        editIdIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(issueDatePickerListener, issueDate);
            }
        });

        editProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                intent.putExtra("arrProvincesKey", arrProvince);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", Constant.LOCATION_CHECK_KEY.PROVINCE_KEY);
                intent.putExtras(mBundle);
                startActivityForResult(intent, Constant.LOCATION_REQUEST_CODE.PROVINCE_CODE);
            }
        });

        editDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonActivity.isNullOrEmpty(provinceEdit)) {
                    Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                    intent.putExtra("arrDistrictKey", arrDistrict);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("checkKey", Constant.LOCATION_CHECK_KEY.DISTRICT_KEY);
                    intent.putExtras(mBundle);
                    startActivityForResult(intent, Constant.LOCATION_REQUEST_CODE.DISTRICT_CODE);

                } else {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.message_pleass_input_province);
                }
            }
        });

        editPrecinct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonActivity.isNullOrEmpty(districtEdit)) {
                    Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
                    intent.putExtra("arrPrecinctKey", arrPrecinct);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("checkKey", Constant.LOCATION_CHECK_KEY.PRECINCT_KEY);
                    intent.putExtras(mBundle);
                    startActivityForResult(intent, Constant.LOCATION_REQUEST_CODE.PRECINCT_CODE);

                } else {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.message_pleass_input_distrist);
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    CommonActivity
                            .createDialog(getActivity(),
                                    getActivity().getResources().getString(R.string.omni_confirm_edit_info),
                                    getActivity().getResources().getString(R.string.app_name),
                                    getActivity().getResources().getString(R.string.cancel),
                                    getActivity().getResources().getString(R.string.buttonOk),
                                    null, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            doSaveInfo();
                                        }
                                    })
                            .show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void doSaveInfo() {
        Intent intent = new Intent(getContext(), EditCustInfoOmniFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer", customer);
        intent.putExtras(bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(), DetailOrderOmniFragment.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }

    @Override
    protected void setPermission() {

    }

    private void fillData(Customer customer) {
        try {
            arrProvince = CusCommons.getProvince(getContext());
            if (!CommonActivity.isNullOrEmpty(customer)) {
                editCustName.setText(customer.getName());
                if (!CommonActivity.isNullOrEmpty(customer.getBirthDate())) {
                    birthDay = sdf.parse(customer.getBirthDate());
                    editBirthDay.setText(sdfV.format(birthDay));
                } else {
                    Calendar cal = Calendar.getInstance();
                    birthDay = cal.getTime();
                    editBirthDay.setText(sdfV.format(birthDay));
                }
                editId.setText(customer.getIdNo());
                if (!CommonActivity.isNullOrEmpty(customer.getIdIssueDate())) {
                    issueDate = sdf.parse(customer.getIdIssueDate());
                    editIdIssueDate.setText(sdfV.format(issueDate));
                } else {
                    Calendar cal = Calendar.getInstance();
                    issueDate = cal.getTime();
                    editIdIssueDate.setText(sdfV.format(issueDate));
                }
                editIdIssuePlace.setText(customer.getIdIssuePlace());
                initZoneCurrentInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean validate() {
        GetAreaDal getAreaDal = null;
        try {
            String name = editCustName.getText().toString();
            if (CommonActivity.isNullOrEmpty(name)) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.checknameKH);
                return false;
            }
            String birthDay = editBirthDay.getText().toString();
            String idIssueDate = editIdIssueDate.getText().toString();
            if (!CommonActivity.isNullOrEmptyArray(birthDay, idIssueDate)) {
                // todo check lại ngày sinh, ngày cấp
                if (DateTimeUtils.daysBetween(sdfV.parse(birthDay), Calendar.getInstance().getTime()) <=0) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.ngaysinhnhohonngayhientai);
                    return false;
                } else if (DateTimeUtils.daysBetween(sdfV.parse(idIssueDate), Calendar.getInstance().getTime()) <0) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.checkngaycap);
                    return false;
                } else if (DateTimeUtils.yearsBetween(sdfV.parse(birthDay), sdfV.parse(idIssueDate)) < 14) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.checkcmtngaycap14);
                    return false;
                }

                String currentDate = sdfV.format(Calendar.getInstance().getTime());
                if (orderType.equals(Constant.ORD_TYPE_CONNECT_PREPAID)
                        || orderType.equals(Constant.ORD_TYPE_REGISTER_PREPAID)) {
                    if (DateTimeUtils.yearsBetween(sdfV.parse(birthDay), sdfV.parse(currentDate)) < 14) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.validate_above_age_14);
                        return false;
                    }
                } else {
                    if (DateTimeUtils.yearsBetween(sdfV.parse(birthDay), sdfV.parse(currentDate)) < 18) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.validate_above_age);
                        return false;
                    }
                }
            }
            String id = editId.getText().toString();
            if (CommonActivity.isNullOrEmpty(id)) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.check_input_idNumber);
                return false;
            }

            String idIssuePlace = editIdIssuePlace.getText().toString();
            if (CommonActivity.isNullOrEmpty(idIssuePlace)) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.checkissuepalceempty);
                return false;
            }
            String province = provinceEdit;
            if (CommonActivity.isNullOrEmpty(province)) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.message_pleass_input_province);
                return false;
            }
            String district = districtEdit;
            if (CommonActivity.isNullOrEmpty(district)) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.message_pleass_input_distrist);
                return false;
            }
            String precinct = precinctEdit;
            if (CommonActivity.isNullOrEmpty(precinct)) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.message_pleass_input_precint);
                return false;
            }

            customer.setIdNo(id);
            customer.setName(name.trim());
            customer.setBirthDate(sdf.format(sdfV.parse(birthDay)));
            customer.setIdIssueDate(sdf.format(sdfV.parse(idIssueDate)));
            customer.setIdIssuePlace(idIssuePlace.trim());
            Address address = new Address();
            getAreaDal = new GetAreaDal(getContext());
            getAreaDal.open();
            address.setProvince(province);
            address.setDistrict(district);
            address.setPrecinct(precinct);
            address.setAddress(editHomeNumber.getText().toString().trim());
            String fullAdress = CommonActivity.isNullOrEmpty(address.getAddress()) ? "" :  "số  " + address.getAddress() + " ";
            fullAdress += getAreaDal.getfulladddress(address.getAreaCode());
            address.setFullAddress(fullAdress);
            customer.setAddress(address);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            if(!CommonActivity.isNullOrEmpty(getAreaDal)){
                getAreaDal.close();
            }
        }
        return true;
    }

    private void changeDate(DatePickerDialog.OnDateSetListener listener, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, listener, cal.get(Calendar.YEAR)
                , cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        toDateDialog.show();
    }

    private final DatePickerDialog.OnDateSetListener birthDayPickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar cal = Calendar.getInstance();
            cal.set(selectedYear, selectedMonth, selectedDay);
            try {
                birthDay = sdf.parse(sdf.format(cal.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            editBirthDay.setText(sdfV.format(birthDay));
        }
    };
    private final DatePickerDialog.OnDateSetListener issueDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar cal = Calendar.getInstance();
            cal.set(selectedYear, selectedMonth, selectedDay);
            try {
                issueDate = sdf.parse(sdf.format(cal.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            editIdIssueDate.setText(sdfV.format(issueDate));
        }
    };

    private void initZoneCurrentInfo() {
        if (customer.getAddress() != null) {

            provinceEdit = customer.getAddress().getProvince();
            districtEdit = customer.getAddress().getDistrict();
            precinctEdit = customer.getAddress().getPrecinct();
            arrDistrict = CusCommons.getDistrict(provinceEdit, getContext());
            arrPrecinct = CusCommons.getPrecinct(provinceEdit, districtEdit, getContext());

            if (CusCommons.getProvinceFromId(provinceEdit, arrProvince) != null) {
                this.editProvince.setText(CusCommons.getProvinceFromId(provinceEdit, arrProvince));
            } else {
                this.editProvince.setText("");
                provinceEdit = "";
                districtEdit = "";
                precinctEdit = "";
                return;
            }

            if (CusCommons.getDistrictFromId(districtEdit, arrDistrict) != null) {
                this.editDistrict.setText(CusCommons.getDistrictFromId(districtEdit, arrDistrict));
            } else {
                this.editDistrict.setText("");
                districtEdit = "";
                precinctEdit = "";
                return;
            }

            if (CusCommons.getPrecinctFromId(precinctEdit, arrPrecinct) != null) {
                this.editPrecinct.setText(CusCommons.getPrecinctFromId(precinctEdit, arrPrecinct));
                this.editHomeNumber.setText(customer.getAddress().getAddress());
            } else {
                this.editPrecinct.setText("");
                precinctEdit = "";
                return;
            }
        } else {
            this.editProvince.setText("");
            this.editDistrict.setText("");
            this.editPrecinct.setText("");
            this.editHomeNumber.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.LOCATION_REQUEST_CODE.PROVINCE_CODE:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");

                        provinceEdit = areaBean.getProvince();
                        if (!CommonActivity.isNullOrEmpty(CusCommons.getDistrict(provinceEdit, getContext())))
                            arrDistrict = CusCommons.getDistrict(provinceEdit, getContext());
                        editProvince.setText(areaBean.getNameProvince());
                        editDistrict.setText("");
                        editPrecinct.setText("");
                        districtEdit = "";
                        precinctEdit = "";
                    }
                    break;
                case Constant.LOCATION_REQUEST_CODE.DISTRICT_CODE:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
                        districtEdit = areaBean.getDistrict();
                        if (!CommonActivity.isNullOrEmpty(CusCommons.getPrecinct(provinceEdit, districtEdit, getContext())))
                            arrPrecinct = CusCommons.getPrecinct(provinceEdit, districtEdit, getContext());
                        editDistrict.setText(areaBean.getNameDistrict());
                        editPrecinct.setText("");
                        precinctEdit = "";
                    }
                    break;

                case Constant.LOCATION_REQUEST_CODE.PRECINCT_CODE:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("precinctKey");
                        precinctEdit = areaBean.getPrecinct();
                        editPrecinct.setText(areaBean.getNamePrecint());
                    }
                    break;
            }
        }
    }
}
