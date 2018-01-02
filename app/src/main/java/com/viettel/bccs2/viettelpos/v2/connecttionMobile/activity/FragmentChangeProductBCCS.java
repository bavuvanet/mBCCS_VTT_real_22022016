package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
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

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.CheckSubChangeQueuePendingAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetHotChargeAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetIpAsyncTask;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetProductCodeByMapActiveInfoAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetPromotionsAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.GetInfoPackageAsyn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.SearchCodePromotionActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListPaymentDetailChargeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FragmentChangeProductBCCS extends FragmentCommon {
    // thong tin product
    private EditText edtselectproduct, edtselectreasonchangepro, edtselectpromotion;
    private Spinner spnCDT;
    private ProgressBar prbCuocdongtruoc;

    // view infor promotion
    private LinearLayout lnselectreason;
    private EditText edtselectreason;
    private EditText edit_ngayhieuluc;
    private Spinner spnDateAffect;

    // thong tin IP
    private LinearLayout lnselectip, lnCuocphatsinh;
    private Spinner spnIp;

    private Button btn_apply, btnInfoPromotion, btnInfoProduct;
    private LinearLayout lnttinphids;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String dateNowString = "";
    private Date dateNow = null;

    private ArrayList<PromotionTypeBeans> arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
    private String maKM = "";
    private PromotionTypeBeans promotionTypeBeans = null;
    private PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = null;

    private ArrayList<ReasonDTO> arrReasonDTO = new ArrayList<ReasonDTO>();
    private SubscriberDTO subscriberDTO = null;
    private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
    private String prepaidCode = "";
    private ReasonDTO reasonDTO = null;

    private ArrayList<ProductOfferingDTO> arrProductOfferingDTO = new ArrayList<ProductOfferingDTO>();

    private String TAG = FragmentChangeProductBCCS.class.getSimpleName();

    private ProductOfferingDTO productOfferingDTO = null;

    private ArrayList<ReasonDTO> arrReasonDTOChangeProduct = new ArrayList<ReasonDTO>();
    private ReasonDTO reasonDTOChangeProduct = null;

    private LinearLayout lnpromotion;
    private ArrayList<String> lstIp = new ArrayList<String>();
    private String ip = "";
    private TextView txterror, tvCuocphatsinh;
    private LinearLayout lnngayhieuluc;
    private Button btninfoprepaid;
    private String productCodeHotline;
    private String receiveRequestId;

    private ArrayList<String> lstDateAffect = new ArrayList<>();
    private String typeDateAffect = null;
    private static final String REQUIRE_DATE_AFFECT = "--Chọn ngày hiệu lực--";
    private static final String TODAY = "Ngày hiện tại";
    private String dateEffect = "";
    private Boolean checkCDT = false, checkDateEffect = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH) + 1;
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        String fromDateStr = "";
        if (fromDay < 10) {
            fromDateStr = "0" + fromDay;
        } else {
            fromDateStr = "" + fromDay;
        }
        String fromMonthStr = "";
        if (fromMonth < 10) {
            fromMonthStr = "0" + fromMonth;
        } else {
            fromMonthStr = "" + fromMonth;
        }
        dateNowString = fromDateStr + "/" + fromMonthStr + "/" + fromYear + "";
        try {
            dateNow = sdf.parse(dateNowString);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        Bundle mBundle = this.getArguments();
        if (mBundle != null) {
            subscriberDTO = (SubscriberDTO) mBundle.getSerializable("subscriberDTO");
            productCodeHotline = mBundle.getString("productCodeHotline", "");
            receiveRequestId = mBundle.getString("receiveRequestId", "");
        }

        idLayout = R.layout.activity_change_product_bccs;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getActivity().getString(R.string.Chuyendoigoicuoc));
        if (reasonDTO != null && !CommonActivity.isNullOrEmpty(reasonDTO.toString())) {
            edtselectreason.setText(reasonDTO.toString());

        } else {
            edtselectreason.setText("");
            edtselectreason.setHint(getActivity().getString(R.string.reasonkmdf));
        }

        if (reasonDTOChangeProduct != null && !CommonActivity.isNullOrEmpty(reasonDTOChangeProduct.toString())) {
            edtselectreasonchangepro.setText(reasonDTOChangeProduct.toString());

        } else {
            edtselectreasonchangepro.setText("");
            edtselectreasonchangepro.setHint(getActivity().getString(R.string.reasonproductdf));
        }
    }

    @Override
    public void unit(View v) {
        btninfoprepaid = (Button) v.findViewById(R.id.btninfoprepaid);
        btnInfoProduct = (Button) v.findViewById(R.id.btnInfoProduct);
        btninfoprepaid.setOnClickListener(this);
        lnngayhieuluc = (LinearLayout) v.findViewById(R.id.lnngayhieuluc);
//		lnngayhieuluc.setVisibility(View.GONE);
        txterror = (TextView) v.findViewById(R.id.txterror);
        edtselectproduct = (EditText) v.findViewById(R.id.edtselectproduct);
        edtselectproduct.setOnClickListener(this);
        edtselectreasonchangepro = (EditText) v.findViewById(R.id.edtselectreasonchangepro);
        edtselectreasonchangepro.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmptyArray(arrReasonDTOChangeProduct)) {
                    Intent intent = new Intent(getActivity(),
                            FragmentChooseReason.class);
                    intent.putExtra("arrReasonDTO", arrReasonDTOChangeProduct);
                    startActivityForResult(intent, 1235);
//					FragmentChooseReason fragmentChooseReason = new FragmentChooseReason();
//					Bundle mBundle = new Bundle();
//					mBundle.putSerializable("arrReasonDTO", arrReasonDTOChangeProduct);
//					fragmentChooseReason.setArguments(mBundle);
//					fragmentChooseReason.setTargetFragment(FragmentChangeProductBCCS.this, 1235);
//					ReplaceFragment.replaceFragment(getActivity(), fragmentChooseReason, false);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                            getActivity().getString(R.string.app_name)).show();
                }
            }
        });
        edtselectpromotion = (EditText) v.findViewById(R.id.edtselectpromotion);
        btnInfoPromotion = (Button) v.findViewById(R.id.btnInfoPromotion);
        edtselectpromotion.setOnClickListener(this);
        spnCDT = (Spinner) v.findViewById(R.id.spnCDT);
        spnCDT.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {
                    paymentPrePaidPromotionBeans = arrPaymentPrePaidPromotionBeans.get(arg2);
                    prepaidCode = arrPaymentPrePaidPromotionBeans.get(arg2).getPrePaidCode();
                    if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
                        showSelectCuocDongTruoc(arrPaymentPrePaidPromotionBeans.get(arg2));
                    }

                } else {
                    paymentPrePaidPromotionBeans = null;
                    prepaidCode = "";
                }


                if(!getString(R.string.defautcdt).equals(spnCDT.getSelectedItem().toString())){
                    checkCDT = true;
                }else{
                    checkCDT = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        prbCuocdongtruoc = (ProgressBar) v.findViewById(R.id.prbCuocdongtruoc);
        tvCuocphatsinh = (TextView) v.findViewById(R.id.tvCuocphatsinh);
        lnselectreason = (LinearLayout) v.findViewById(R.id.lnselectreason);
        edtselectreason = (EditText) v.findViewById(R.id.edtselectreason);
        edtselectreason.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmptyArray(arrReasonDTO)) {
                    Intent intent = new Intent(getActivity(),
                            FragmentChooseReason.class);
                    intent.putExtra("arrReasonDTO", arrReasonDTO);
                    startActivityForResult(intent, 1234);
//					FragmentChooseReason fragmentChooseReason = new FragmentChooseReason();
//					Bundle mBundle = new Bundle();
//					mBundle.putSerializable("arrReasonDTO", arrReasonDTO);
//					fragmentChooseReason.setArguments(mBundle);
//					fragmentChooseReason.setTargetFragment(FragmentChangeProductBCCS.this, 1234);
//					ReplaceFragment.replaceFragment(getActivity(), fragmentChooseReason, false);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                            getActivity().getString(R.string.app_name)).show();
                }
            }
        });
        edit_ngayhieuluc = (EditText) v.findViewById(R.id.edit_ngayhieuluc);
        edit_ngayhieuluc.setVisibility(View.VISIBLE);
        spnDateAffect = (Spinner) v.findViewById(R.id.spnDateAffect);

        // edit_ngayhieuluc.setText(dateNowString);
        edit_ngayhieuluc.setOnClickListener(editTextListener);
        lnselectip = (LinearLayout) v.findViewById(R.id.lnselectip);
        lnCuocphatsinh = (LinearLayout) v.findViewById(R.id.lnCuocphatsinh);
        lnselectip.setVisibility(View.GONE);
        spnIp = (Spinner) v.findViewById(R.id.spnIp);
        spnIp.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (!CommonActivity.isNullOrEmptyArray(lstIp)) {
                    ip = lstIp.get(arg2);
                } else {
                    ip = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btn_apply = (Button) v.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);
        lnttinphids = (LinearLayout) v.findViewById(R.id.lnttinphids);
        lnttinphids.setOnClickListener(this);
        lnpromotion = (LinearLayout) v.findViewById(R.id.lnpromotion);
        // lay danh sach goi cuoc dc phep chuyen doi
        if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {

            if ("A".equals(subscriberDTO.getTelecomServiceAlias()) || "F".equals(subscriberDTO.getTelecomServiceAlias())) {
                lnngayhieuluc.setVisibility(View.VISIBLE);
            } else {
                lnngayhieuluc.setVisibility(View.GONE);
            }

            CheckSubChangeQueuePendingAsyn checkSubChangeQueuePendingAsyn = new CheckSubChangeQueuePendingAsyn(
                    getActivity(), new OnPosCheckSubChange(), moveLogInAct);
            checkSubChangeQueuePendingAsyn.execute(subscriberDTO.getSubId() + "", "33");

            GetProductCodeByMapActiveInfoAsyn getProductCodeByMapActiveInfoAsyn = new GetProductCodeByMapActiveInfoAsyn(
                    getActivity(), new OnPostGetProductAsyn(), moveLogInAct, false);
            getProductCodeByMapActiveInfoAsyn.execute(subscriberDTO.getTelecomServiceId(), "1", "33", subscriberDTO.getOfferId());
        }

        checkTHS();
        if(spnDateAffect.getVisibility() == View.VISIBLE && CommonActivity.isNullOrEmpty(lstDateAffect)) {
            lstDateAffect = StringUtils.initNgayHieuLuc();

            ArrayAdapter<String> adtNgayhieuluc = new ArrayAdapter<>(
                    getActivity(), R.layout.layout_multi_textview,android.R.id.text1, lstDateAffect);

            spnDateAffect.setAdapter(adtNgayhieuluc);
            spnDateAffect.setOnItemSelectedListener(onDateAffectSelect);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 3333:
                    if (data != null) {
                        productOfferingDTO = (ProductOfferingDTO) data.getExtras().getSerializable("pakageChargeKey");
                        if (!CommonActivity.isNullOrEmpty(productOfferingDTO)) {
                            edtselectproduct.setText(productOfferingDTO.getCode() + "-" + productOfferingDTO.getName());
                            btnInfoProduct.setVisibility(View.VISIBLE);
                            btnInfoProduct.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    final GetInfoPackageAsyn getInfoPackageAsyn = new GetInfoPackageAsyn(act, new OnPostExecuteListener<ProductOfferCharacterClone>() {
                                        @Override
                                        public void onPostExecute(ProductOfferCharacterClone result, String errorCode, String description) {
                                            DialogDetailAtribute.showDialogPackageDetail(act, result);
                                        }
                                    }, null);

                                    getInfoPackageAsyn.execute(productOfferingDTO.getProductOfferingId()+"");
                                }
                            });


//						resetValueChild();
                            if (!CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
                                GetPromotionsAsyn getPromotionsAsyn = new GetPromotionsAsyn(getActivity(),
                                        new OnPostGetPromotionsAsyn(), moveLogInAct);
                                getPromotionsAsyn.execute("537", subscriberDTO.getTelecomServiceAlias(),
                                        productOfferingDTO.getProductOfferingId() + "",
                                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
                                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict(),
                                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct());
                            }

                            // getListIp(String offderId, String province, String
                            // telemcomServiceId, String productCode, String
                            // serviceType)
                            if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
                                GetIpAsyncTask getIpAsyncTask = new GetIpAsyncTask(getActivity(), new OnPostGetIpAsyn(),
                                        moveLogInAct);
                                getIpAsyncTask.execute(productOfferingDTO.getProductOfferingId() + "",
                                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
                                        subscriberDTO.getTelecomServiceId() + "", productOfferingDTO.getCode(),
                                        subscriberDTO.getTelecomServiceAlias());
                            }

                            // String offerId, String actionCode, String
                            // serviceType, String
                            // cusType, String subType

                            // if(!CommonActivity.isNullOrEmpty(maKM)){
                            GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(),
                                    new OnPostGetReasonFullChangeProduct(), moveLogInAct);
                            getReasonFullPMAsyn.execute(productOfferingDTO.getProductOfferingId() + "", "33",
                                    subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                    subscriberDTO.getSubType(), "", "");

                        } else {
                            resetValue();
                        }
                    }
                    break;

                case 102:
                    promotionTypeBeans = (PromotionTypeBeans) data.getExtras().getSerializable("PromotionTypeBeans");
                    if (promotionTypeBeans != null) {
                        maKM = promotionTypeBeans.getPromProgramCode();
                        edtselectpromotion.setText(
                                promotionTypeBeans.getPromProgramCode() + "-" + promotionTypeBeans.getName().toString());

                        btnInfoPromotion.setVisibility(View.VISIBLE);
                        btnInfoPromotion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                String productCode = productOfferingDTO.getCode();
                                DialogDetailAtribute.showDialogDetailPromotion(act, promotionTypeBeans,productCode);
                            }
                        });

                        if (!CommonActivity.isNullOrEmpty(maKM)) {
                            String areacode = subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince()
                                    + subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict()
                                    + subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct();
                            GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
                                    getActivity());

                            getAllListPaymentPrePaidAsyn.execute(maKM, productOfferingDTO.getCode(), areacode, dateNowString);
                            if (!CommonActivity.isNullOrEmpty(productOfferingDTO)) {
                                GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(),
                                        new OnPostGetReasonFull(), moveLogInAct);
                                getReasonFullPMAsyn.execute(productOfferingDTO.getProductOfferingId() + "", "537",
                                        subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                        subscriberDTO.getSubType(), subscriberDTO.getTechnology(), maKM);
                            }

                            lnpromotion.setVisibility(View.VISIBLE);
                        } else {
                            resetValuePro();
                            lnpromotion.setVisibility(View.GONE);
                        }

                    } else {
                        lnpromotion.setVisibility(View.GONE);
                    }

                    break;
                case 1234:
                    reasonDTO = (ReasonDTO) data.getExtras().getSerializable("reasonDTO");
                    if (reasonDTO != null) {
                        edtselectreason.setText(reasonDTO.toString());
                    } else {
                        edtselectreason.setText("");
                        edtselectreason.setHint(getActivity().getString(R.string.reasonkmdf));
                    }
                    break;
                case 1235:
                    reasonDTOChangeProduct = (ReasonDTO) data.getExtras().getSerializable("reasonDTO");
                    if (reasonDTOChangeProduct != null) {
                        edtselectreasonchangepro.setText(reasonDTOChangeProduct.toString());

                    } else {
                        edtselectreasonchangepro.setText("");
                        edtselectreasonchangepro.setHint(getActivity().getString(R.string.reasonproductdf));
                    }
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * nếu dịch vụ là THS và cước đóng trước khai báo giảm trừ từ tháng đầu tiên thì set date
     */
    private void checkTHS() {

        if ("U".equals(subscriberDTO.getTelecomServiceAlias()) || "2".equals(subscriberDTO.getTelecomServiceAlias())) {
            lnngayhieuluc.setVisibility(View.VISIBLE);
            edit_ngayhieuluc.setVisibility(View.GONE);
            spnDateAffect.setVisibility(View.VISIBLE);
        }

    }

    private OnItemSelectedListener onDateAffectSelect = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

             dateEffect =  spnDateAffect.getSelectedItem().toString();

            if(dateEffect.equals(TODAY)) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                dateEffect = sdfDate.format(Calendar.getInstance().getTime());
                checkDateEffect = true;

            }else{
                checkDateEffect = false;
            }

            if(checkCDT && checkDateEffect){
                lnCuocphatsinh.setVisibility(View.VISIBLE);
                GetHotChargeAsyn getHotChargeAsyn = new GetHotChargeAsyn(getActivity(), new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        tvCuocphatsinh.setText(StringUtils.formatMoney(result));
                    }
                }, null);
                getHotChargeAsyn.execute(subscriberDTO.getSubId(), subscriberDTO.getTelecomServiceId());
            }else{
                lnCuocphatsinh.setVisibility(View.GONE);
            }


    }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };


    private boolean validateChangeProduct() {

        if (CommonActivity.isNullOrEmpty(productOfferingDTO)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkpakecharge),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(productOfferingDTO.getProductOfferingId())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkpakecharge),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(reasonDTOChangeProduct)) {
            CommonActivity
                    .createAlertDialog(getActivity(), getActivity().getString(R.string.validate_reason_changeproduct),
                            getActivity().getString(R.string.app_name))
                    .show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonDTOChangeProduct.getReasonId())) {
            CommonActivity
                    .createAlertDialog(getActivity(), getActivity().getString(R.string.validate_reason_changeproduct),
                            getActivity().getString(R.string.app_name))
                    .show();
            return false;
        }
        if (!CommonActivity.isNullOrEmpty(maKM)) {

            if (CommonActivity.isNullOrEmpty(reasonDTO)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatekm),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatekm),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (!CommonActivity.isNullOrEmptyArray(arrPaymentPrePaidPromotionBeans) && arrPaymentPrePaidPromotionBeans.size() > 1) {
                if (CommonActivity.isNullOrEmpty(prepaidCode)) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcuocdongtruoc),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return false;
                }
            }
        }

        if (edit_ngayhieuluc.getVisibility() == View.VISIBLE) {
            if ("A".equals(subscriberDTO.getTelecomServiceAlias()) || "F".equals(subscriberDTO.getTelecomServiceAlias())) {
                if (CommonActivity.isNullOrEmpty(edit_ngayhieuluc.getText().toString())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateDateEffect),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (!CommonActivity.isNullOrEmpty(edit_ngayhieuluc.getText().toString().trim())) {
                    Date dateEffect = null;
                    try {
                        dateEffect = sdf.parse(edit_ngayhieuluc.getText().toString().trim());
                    } catch (Exception e) {
                        Log.d("parse date ex", e.toString());
                    }
                    if (dateEffect.after(dateNow) || dateEffect.equals(dateNow)) {
                    } else {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.dateEffectvlid),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }
            } else {
                if (!CommonActivity.isNullOrEmpty(edit_ngayhieuluc.getText().toString().trim())) {
                    Date dateEffect = null;
                    try {
                        dateEffect = sdf.parse(edit_ngayhieuluc.getText().toString().trim());
                    } catch (Exception e) {
                        Log.d("parse date ex", e.toString());
                    }
                    if (dateEffect.after(dateNow) || dateEffect.equals(dateNow)) {
                    } else {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.dateEffectvlid),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }
            }
        }else {

            if (CommonActivity.isNullOrEmpty(dateEffect)  || (REQUIRE_DATE_AFFECT.equals(spnDateAffect.getSelectedItem().toString()))) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.requireDateEffect),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

        return true;
    }

    private void resetValue() {

        edtselectproduct.setText("");
        edtselectproduct.setHint(getActivity().getString(R.string.chongoicuoc));
        productOfferingDTO = new ProductOfferingDTO();
        // arrProductOfferingDTO = new ArrayList<ProductOfferingDTO>();
        lnpromotion.setVisibility(View.GONE);

        edtselectreasonchangepro.setText("");
        edtselectreasonchangepro.setHint(getActivity().getString(R.string.reasonproductdf));

        edtselectpromotion.setText("");
        edtselectpromotion.setHint(getActivity().getString(R.string.selectpromotioncd));
        promotionTypeBeans = new PromotionTypeBeans();
        maKM = "";
        paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
        prepaidCode = "";
        edtselectreason.setText("");
        edtselectreason.setHint(getActivity().getString(R.string.reasonkmdf));
        lnselectip.setVisibility(View.GONE);

        edit_ngayhieuluc.setText(dateNowString);

        lstIp = new ArrayList<String>();
        ip = "";
        initLstIpNotData();
        lnselectip.setVisibility(View.GONE);
    }

    private void resetValueChild() {

        // edtselectproduct.setText("");
        // edtselectproduct.setHint(getActivity().getString(R.string.chongoicuoc));
        // productOfferingDTO = new ProductOfferingDTO();
        // arrProductOfferingDTO = new ArrayList<ProductOfferingDTO>();
        lnpromotion.setVisibility(View.GONE);
        lnCuocphatsinh.setVisibility(View.GONE);
        edtselectreasonchangepro.setText("");
        edtselectreasonchangepro.setHint(getActivity().getString(R.string.reasonproductdf));

        reasonDTO = new ReasonDTO();
        reasonDTOChangeProduct = new ReasonDTO();

        edtselectpromotion.setText("");
        edtselectpromotion.setHint(getActivity().getString(R.string.selectpromotioncd));
        promotionTypeBeans = new PromotionTypeBeans();
        maKM = "";
        paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
        prepaidCode = "";

        edtselectreason.setText("");
        edtselectreason.setHint(getActivity().getString(R.string.reasonkmdf));

        lstIp = new ArrayList<String>();
        ip = "";
        initLstIpNotData();
        lnselectip.setVisibility(View.GONE);

        edit_ngayhieuluc.setText(dateNowString);
    }

    private void resetValuePro() {

        // edtselectproduct.setText("");
        // edtselectproduct.setHint(getActivity().getString(R.string.chongoicuoc));
        // productOfferingDTO = new ProductOfferingDTO();
        // arrProductOfferingDTO = new ArrayList<ProductOfferingDTO>();
        lnpromotion.setVisibility(View.GONE);


//		edtselectpromotion.setText("");
//		edtselectpromotion.setHint(getActivity().getString(R.string.selectpromotioncd));
        promotionTypeBeans = new PromotionTypeBeans();
        maKM = "";
        paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
        prepaidCode = "";
        reasonDTO = new ReasonDTO();

        edtselectreason.setText("");
        edtselectreason.setHint(getActivity().getString(R.string.reasonkmdf));

//		edtselectpromotion.setText("");
//		edtselectpromotion.setHint(getActivity().getString(R.string.selectpromotioncd));

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_apply:

                if (validateChangeProduct()) {
                    OnClickListener onclickChangeProduct = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            ChangeProductBaseServiceAsyn changeProductBaseServiceAsyn = new ChangeProductBaseServiceAsyn(
                                    getActivity());
                            changeProductBaseServiceAsyn.execute(ip);
                        }
                    };
                         CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.confirmchangeproduct),
                            getActivity().getString(R.string.app_name), getActivity().getString(R.string.cancel),
                            getActivity().getString(R.string.ok),  null,onclickChangeProduct).show();

                }

                break;
            case R.id.edtselectproduct:
                if (!CommonActivity.isNullOrEmptyArray(arrProductOfferingDTO)) {
                    resetValueChild();
                    // resetValue();
                    Intent intent = new Intent(getActivity(), FragmentSearchProductMobileBCCS.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("arrChargeBeans", arrProductOfferingDTO);
                    if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {
                        if (!CommonActivity.isNullOrEmpty(subscriberDTO.getNewProductCode())) {
                            mBundle.putString("newPackageChange", subscriberDTO.getNewProductCode());
                        }
                    }

                    intent.putExtras(mBundle);
                    startActivityForResult(intent, 3333);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdataproduct),
                            getActivity().getString(R.string.app_name)).show();
                }
                break;

            case R.id.edtselectpromotion:
                if (!CommonActivity.isNullOrEmpty(arrPromotionTypeBean)) {
                    Intent intent = new Intent(getActivity(), SearchCodePromotionActivity.class);
                    intent.putExtra("arrPromotionTypeBeans", arrPromotionTypeBean);
                    intent.putExtra("productCode", productOfferingDTO.getCode());
                    startActivityForResult(intent, 102);
                }
                break;
            case R.id.lnttinphids:
                if (!CommonActivity.isNullOrEmpty(productOfferingDTO)) {
                    if (reasonDTOChangeProduct != null
                            && !CommonActivity.isNullOrEmpty(reasonDTOChangeProduct.getReasonId())) {

                        FindFeeByReasonTeleIdAsyn findFeeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(getActivity());
                        findFeeByReasonTeleIdAsyn.execute(subscriberDTO.getTelecomServiceId() + "",
                                reasonDTOChangeProduct.getReasonId(), productOfferingDTO.getCode());
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.validate_reason_changeproduct),
                                getActivity().getString(R.string.app_name)).show();
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkpakecharge),
                            getActivity().getString(R.string.app_name)).show();
                }

                break;
            case R.id.btninfoprepaid:
                if (!CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans) && !CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans.getPrePaidCode())) {
                    showSelectCuocDongTruoc(paymentPrePaidPromotionBeans);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notinfodetail), getActivity().getString(R.string.app_name)).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setPermission() {
        permission = "menu.change.product.bccs";
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

            DatePickerDialog datePicker = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, datePickerListener,
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            datePicker.getDatePicker().setTag(view);
            datePicker.show();
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

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

                Calendar cal = Calendar.getInstance();
                cal.set(selectedYear, selectedMonth, selectedDay);

            }
        }
    };

    private class OnPosCheckSubChange implements OnPostExecuteListener<String> {

        @Override
        public void onPostExecute(String result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(description)) {
                    txterror.setText(description);
                    txterror.setVisibility(View.VISIBLE);
                } else {
                    txterror.setText("");
                    txterror.setVisibility(View.GONE);
                }
            } else {
                txterror.setText("");
                txterror.setVisibility(View.GONE);
            }

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
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_findFeeByReasonTeleId");
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

    private class OnPostGetIpAsyn implements OnPostExecuteListener<ArrayList<String>> {

        @Override
        public void onPostExecute(ArrayList<String> result, String errorCode, String description) {
            lstIp = new ArrayList<String>();
            if ("0".equals(errorCode)) {
                btn_apply.setVisibility(View.VISIBLE);
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    lstIp.addAll(result);
                    initLstIp();
                    lnselectip.setVisibility(View.VISIBLE);
                } else {
                    initLstIpNotData();
                    lnselectip.setVisibility(View.GONE);
                }
            } else {
                btn_apply.setVisibility(View.GONE);
                lstIp = new ArrayList<String>();
                ip = "";
                initLstIpNotData();
                lnselectip.setVisibility(View.GONE);
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getActivity().getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
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

    }

    private class OnPostGetProductAsyn implements OnPostExecuteListener<ArrayList<ProductOfferingDTO>> {

        @Override
        public void onPostExecute(ArrayList<ProductOfferingDTO> result, String errorCode, String description) {
            arrProductOfferingDTO = new ArrayList<ProductOfferingDTO>();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    arrProductOfferingDTO.addAll(result);
                    if (!CommonActivity.isNullOrEmpty(productCodeHotline)) {
                        for (ProductOfferingDTO item :
                                arrProductOfferingDTO) {
                            if (item.getCode().equals(productCodeHotline)) {
                                productOfferingDTO = item;
                                edtselectproduct.setText(productOfferingDTO.getCode() + "-" + productOfferingDTO.getName());
//						resetValueChild();
                                if (!CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
                                    GetPromotionsAsyn getPromotionsAsyn = new GetPromotionsAsyn(getActivity(),
                                            new OnPostGetPromotionsAsyn(), moveLogInAct);
                                    getPromotionsAsyn.execute("537", subscriberDTO.getTelecomServiceAlias(),
                                            productOfferingDTO.getProductOfferingId() + "",
                                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
                                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict(),
                                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct());
                                }
                                if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
                                    GetIpAsyncTask getIpAsyncTask = new GetIpAsyncTask(getActivity(), new OnPostGetIpAsyn(),
                                            moveLogInAct);
                                    getIpAsyncTask.execute(productOfferingDTO.getProductOfferingId() + "",
                                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
                                            subscriberDTO.getTelecomServiceId() + "", productOfferingDTO.getCode(),
                                            subscriberDTO.getTelecomServiceAlias());
                                }
                                GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(),
                                        new OnPostGetReasonFullChangeProduct(), moveLogInAct);
                                getReasonFullPMAsyn.execute(productOfferingDTO.getProductOfferingId() + "", "33",
                                        subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                        subscriberDTO.getSubType(), "", "");
                                break;
                            }
                        }
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdataproduct),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                arrProductOfferingDTO = new ArrayList<ProductOfferingDTO>();
                if (!CommonActivity.isNullOrEmpty(description)) {
                    CommonActivity
                            .createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name))
                            .show();
                }
            }
        }

    }

    private class OnPostGetPromotionsAsyn implements OnPostExecuteListener<ArrayList<PromotionTypeBeans>> {

        @Override
        public void onPostExecute(ArrayList<PromotionTypeBeans> result, String errorCode, String description) {
            arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
            if ("0".equals(errorCode)) {

                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    arrPromotionTypeBean.addAll(result);
                    PromotionTypeBeans promotionTypeBeans = new
                            PromotionTypeBeans();
                    promotionTypeBeans.setCodeName(getActivity().getString(R.string.selectMKM));
                    promotionTypeBeans.setName(getActivity().getString(R.string.selectMKM));
                    promotionTypeBeans.setPromProgramCode("");
                    arrPromotionTypeBean.add(0, promotionTypeBeans);


//                    if (!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode())) {
//                        for (PromotionTypeBeans item :
//                                arrPromotionTypeBean) {
//                            if (item.getPromProgramCode().equals(subscriberDTO.getPromotionCode())) {
//                                arrPromotionTypeBean.remove(item);
//                                break;
//                            }
//                        }
//                    }

                    //
                    // PromotionTypeBeans promotionTypeBeans1 = new
                    // PromotionTypeBeans();
                    // promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
                    // promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
                    // promotionTypeBeans1.setPromProgramCode("-2");
                    // arrPromotionTypeBean.add(promotionTypeBeans1);

                } else {
                    arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
                    // PromotionTypeBeans promotionTypeBeans = new
                    // PromotionTypeBeans();
                    // promotionTypeBeans.setCodeName(getActivity().getString(R.string.keepkm));
                    // promotionTypeBeans.setName(getActivity().getString(R.string.keepkm));
                    // promotionTypeBeans.setPromProgramCode("-2");
                    // arrPromotionTypeBean.add(promotionTypeBeans);
                    //
                    // PromotionTypeBeans promotionTypeBeans1 = new
                    // PromotionTypeBeans();
                    // promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
                    // promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
                    // promotionTypeBeans1.setPromProgramCode("");
                    // arrPromotionTypeBean.add(promotionTypeBeans1);
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatakm),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
                // PromotionTypeBeans promotionTypeBeans = new
                // PromotionTypeBeans();
                // promotionTypeBeans.setCodeName(getActivity().getString(R.string.keepkm));
                // promotionTypeBeans.setName(getActivity().getString(R.string.keepkm));
                // promotionTypeBeans.setPromProgramCode("-2");
                // arrPromotionTypeBean.add(promotionTypeBeans);
                //
                // PromotionTypeBeans promotionTypeBeans1 = new
                // PromotionTypeBeans();
                // promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
                // promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
                // promotionTypeBeans1.setPromProgramCode("");
                // arrPromotionTypeBean.add(promotionTypeBeans1);
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatakm),
                        getActivity().getString(R.string.app_name)).show();
            }

        }

    }

    // ly do chuyen doi goi cuoc
    private class OnPostGetReasonFullChangeProduct implements OnPostExecuteListener<List<ReasonDTO>> {
        @Override
        public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrReasonDTOChangeProduct.addAll(result);

                // edtselectreasonchangepro.setText(arrReasonDTOChangeProduct.get(0).getName());
                // reasonDTOChangeProduct = arrReasonDTOChangeProduct.get(0);
            } else {
                arrReasonDTOChangeProduct = new ArrayList<ReasonDTO>();

                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                        getActivity().getString(R.string.app_name)).show();

            }

        }
    }

    // ly do chuyen doi khuyen mai
    private class OnPostGetReasonFull implements OnPostExecuteListener<List<ReasonDTO>> {
        @Override
        public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
            reasonDTO = new ReasonDTO();
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrReasonDTO.addAll(result);

                // edtselectreason.setText(arrReasonDTO.get(0).getName());
                // reasonDTO = arrReasonDTO.get(0);
            } else {
                arrReasonDTO = new ArrayList<ReasonDTO>();
                edtselectreason.setText("");
                edtselectreason.setHint(getActivity().getString(R.string.reasonkmdf));
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                        getActivity().getString(R.string.app_name)).show();

            }

        }
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
            prbCuocdongtruoc.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(String... arg0) {
            return getAllListPaymentPrePaid(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(ArrayList<PaymentPrePaidPromotionBeans> result) {

            prbCuocdongtruoc.setVisibility(View.GONE);
            arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
            if ("0".equals(errorCode)) {

                PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
                paymentPrePaidPromotionBeans.setName(getString(R.string.defautcdt));
                paymentPrePaidPromotionBeans.setPrePaidCode("");
                result.add(0, paymentPrePaidPromotionBeans);
                arrPaymentPrePaidPromotionBeans.addAll(result);
                if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                    for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                        adapter.add(typePaperBeans.getName());
                    }
                    spnCDT.setAdapter(adapter);
                }

//				btn_apply.setVisibility(View.VISIBLE);

            } else {

                btn_apply.setVisibility(View.GONE);

                if (errorCode.equals(Constant.INVALID_TOKEN) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                    for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                        adapter.add(typePaperBeans.getName());
                    }
                    spnCDT.setAdapter(adapter);
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
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

                rawData.append("<provinceCode>" + provinceCode);
                rawData.append("</provinceCode>");

                rawData.append("<today>" + today);
                rawData.append("</today>");

                rawData.append("</input>");
                rawData.append("</ws:getAllListPaymentPrePaid>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
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
                            String promId = parse.getValue(e0, "promId");
                            paymentPrePaidDetailBeans.setPromId(promId);
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

        dialogCuocdongtruoc = new Dialog(getActivity());
        dialogCuocdongtruoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCuocdongtruoc.setContentView(R.layout.connection_layout_detail_precharge);

        ListView listdetail = (ListView) dialogCuocdongtruoc.findViewById(R.id.listdetail);

        EditText txttencuocdongtruoc = (EditText) dialogCuocdongtruoc.findViewById(R.id.txttencuocdongtruoc);
        txttencuocdongtruoc.setText(paymentPrePaidPromotionBeans.getName());

        EditText txtmacuocdongtruoc = (EditText) dialogCuocdongtruoc.findViewById(R.id.txtmacuocdongtruoc);
        txtmacuocdongtruoc.setText(paymentPrePaidPromotionBeans.getPrePaidCode());
        GetListPaymentDetailChargeAdapter getListPaymentDetailChargeAdapter = new GetListPaymentDetailChargeAdapter(
                paymentPrePaidPromotionBeans.getLstDetailBeans(), getActivity());
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

    public OnClickListener moveLogInAct = new OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
            dialog.show();
        }
    };

    private class ChangeProductBaseServiceAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public ChangeProductBaseServiceAsyn(Context context) {
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
        protected String doInBackground(String... arg0) {
            return changeProductBaseService(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                btn_apply.setVisibility(View.GONE);
                if (CommonActivity.isNullOrEmpty(description)) {
                    description = getActivity().getString(R.string.chuyendoigoisuccess);
                }
                CommonActivity.createAlertDialog(getActivity(), description,
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
        // â€¢ subId
        // â€¢ newProductCode
        // â€¢ newPromotionCode (MÃ£ CT khuyáº¿n máº¡i)
        // â€¢ prePaidCode (mÃ£ CT cÆ°á»›c Ä‘Ã³ng trÆ°á»›c
        // â€¢ promPrePaidId (id CT cÆ°á»›c Ä‘Ã³ng trÆ°á»›c)
        // â€¢ ipStatic (IP static vá»›i gÃ³i cÆ°á»›c cÃ³ cáº¥u hÃ¬nh há»— trá»£ IP bÃªn PRODUCT)
        // â€¢ strEffectDate (NgÃ y hiá»‡u lá»±c. Náº¿u lÃ  dá»‹ch vá»¥ A hoáº·c F thÃ¬ báº¯t buá»™c
        // truyá»�n Ä‘á»‹nh dáº¡ng dd/MM/yyyy)
        // â€¢ reasonIdChangeProduct (ID lÃ½ do Ä‘á»•i gÃ³i cÆ°á»›c)
        // â€¢ reasonIdChangePromotion (ID lÃ½ do Ä‘á»•i khuyáº¿n máº¡i (náº¿u cÃ³ nháº­p))

        private String changeProductBaseService(String ipStatic) {
            String original = "";
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdfDate.format(Calendar.getInstance().getTime());
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_changeProductBaseService");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:changeProductBaseService>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append(
                        "<telecomServiceAlias>" + subscriberDTO.getTelecomServiceAlias() + "</telecomServiceAlias>");
                rawData.append(
                        "<payType>" + subscriberDTO.getPayType() + "</payType>");
                rawData.append("<requestMbccs>");
                if (!CommonActivity.isNullOrEmpty(subscriberDTO.getSubId())) {
                    rawData.append("<subId>" + subscriberDTO.getSubId() + "</subId>");
                }
                if (!CommonActivity.isNullOrEmpty(productOfferingDTO)) {
                    rawData.append("<newProductCode>" + productOfferingDTO.getCode() + "</newProductCode>");
                }
                if (!CommonActivity.isNullOrEmpty(promotionTypeBeans)
                        && !CommonActivity.isNullOrEmpty(promotionTypeBeans.getPromProgramCode())) {
                    rawData.append(
                            "<newPromotionCode>" + promotionTypeBeans.getPromProgramCode() + "</newPromotionCode>");
                }
                if (!CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans)
                        && !CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans.getPrePaidCode())) {
                    rawData.append("<prePaidCode>" + paymentPrePaidPromotionBeans.getPrePaidCode() + "</prePaidCode>");
                } else {
                    rawData.append("<prePaidCode>" + "-1" + "</prePaidCode>");
                }
                if (!CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans)
                        && !CommonActivity.isNullOrEmptyArray(paymentPrePaidPromotionBeans.getLstDetailBeans()) && !CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans.getLstDetailBeans().get(0).getPromId())) {
                    rawData.append("<promPrePaidId>" + paymentPrePaidPromotionBeans.getLstDetailBeans().get(0).getPromId() + "</promPrePaidId>");
                } else {
                    rawData.append("<promPrePaidId>" + "1" + "</promPrePaidId>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonDTOChangeProduct)
                        && !CommonActivity.isNullOrEmpty(reasonDTOChangeProduct.getReasonId())) {
                    rawData.append("<reasonIdChangeProduct>" + reasonDTOChangeProduct.getReasonId()
                            + "</reasonIdChangeProduct>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonDTO)
                        && !CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                    rawData.append(
                            "<reasonIdChangePromotion>" + reasonDTO.getReasonId() + "</reasonIdChangePromotion>");
                }
                if ("A".equals(subscriberDTO.getTelecomServiceAlias()) || "F".equals(subscriberDTO.getTelecomServiceAlias())) {
                    if (!CommonActivity.isNullOrEmpty(edit_ngayhieuluc.getText().toString())) {
                        rawData.append("<strEffectDate>" + edit_ngayhieuluc.getText().toString() + "</strEffectDate>");
                    }
                }

                if ("U".equals(subscriberDTO.getTelecomServiceAlias()) || "2".equals(subscriberDTO.getTelecomServiceAlias())) {
                    if (!CommonActivity.isNullOrEmpty(dateEffect)) {
                        rawData.append("<strEffectDate>" + dateEffect + "</strEffectDate>");
                    }
                }


                if (!CommonActivity.isNullOrEmpty(currentDate)) {
                    rawData.append("<currentDate>" + currentDate + "</currentDate>");
                }

                if (!CommonActivity.isNullOrEmpty(ipStatic)) {
                    rawData.append("<ipStatic>" + ipStatic + "</ipStatic>");
                }

                rawData.append("</requestMbccs>");

                if (!CommonActivity.isNullOrEmpty(subscriberDTO.getRawData())) {
                    rawData.append("<bocInput>");
                    rawData.append(subscriberDTO.getRawData());

                    rawData.append("</bocInput>");
                }
                if (!CommonActivity.isNullOrEmpty(receiveRequestId)) {
                    rawData.append("<hotlineInput>");
                    rawData.append("<receiveRequestId>").append(receiveRequestId);
                    rawData.append("</receiveRequestId>");
                    rawData.append("</hotlineInput>");
                }
                rawData.append("</input>");
                rawData.append("</ws:changeProductBaseService>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_changeProductBaseService");
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
                Log.d("changePromotion", e.toString());
            }
            return errorCode;
        }
    }

    public void initLstIp() {
        if (lstIp != null && lstIp.size() >= 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
            for (String ip : lstIp) {
                adapter.add(ip);
            }
            spnIp.setAdapter(adapter);
        }
    }

    // == init lstIp
    public void initLstIpNotData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
        spnIp.setAdapter(adapter);
    }
}
