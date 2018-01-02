package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetPromotionTypeDTOAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsynAdviserPromotion;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.CheckSubChangeQueuePendingAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetFilterByPricePromotionAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetHotChargeAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionOutput;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.SearchCodePromotionActivity;
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

public class FragmentChangePromotionBCCS extends FragmentCommon {

    private EditText edit_ngayhieuluc;
    private Spinner spnDateEffect;
    private EditText edtselectpromotion;
    private Spinner spnCDT;
    private EditText edtselectreason;
    private Button btn_apply, btnInfoPromotion;

    private ArrayList<ReasonDTO> arrReasonDTO = new ArrayList<ReasonDTO>();
    private SubscriberDTO subscriberDTO = null;
    private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
    private String prepaidCode = "";
    private ProgressBar prbCuocdongtruoc;
    private ReasonDTO reasonDTO = null;

    private ArrayList<PromotionTypeBeans> arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
    private String maKM = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String dateNowString = "";
    private Date dateNow = null;
    private PromotionTypeBeans promotionTypeBeans = null;
    private PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = null;

    private LinearLayout lnttinphids, lnCuocphatsinh;
    private TextView txterror, tvCuocphatsinh;

    private Button btninfo, btninfoprepaid;


    // dialog show tu van cuoc dong truoc
    private Spinner spnpromotionSuggess;
    private ProgressBar prbpromotionSuggess;
    private Button btnpromotionSuggess;

    private Spinner spnCDTSuggess;
    private ProgressBar prbCuocdongtruocSuggess;
    private Button btninfoprepaidSuggess;

    private LinearLayout lntvcdt;
    private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeansDialog = new ArrayList<PaymentPrePaidPromotionBeans>();

    private ArrayList<String> lstDateAffect = new ArrayList<>();
    public static final String REQUIRE_DATE_AFFECT = "--Chọn ngày hiệu lực--";
    public static final String TODAY = "Ngày hiện tại";
    private String dateEffect = "";
    private Boolean checkCDT = false, checkDateEffect = false;
    private String ACTION_CODE_PROMOTION = "537";


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
            Log.d("ex", e.toString());
        }

        Bundle mBundle = this.getArguments();
        if (mBundle != null) {
            subscriberDTO = (SubscriberDTO) mBundle.getSerializable("subscriberDTO");
            if (!CommonActivity.isNullOrEmpty(subscriberDTO.getLstSubInfrastructureDTO())) {
                if (CommonActivity.isNullOrEmpty(subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notprovince), getActivity().getString(R.string.app_name), onclickBack).show();
                } else if (CommonActivity.isNullOrEmpty(subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdistrict), getActivity().getString(R.string.app_name), onclickBack).show();
                } else if (CommonActivity.isNullOrEmpty(subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notprecint), getActivity().getString(R.string.app_name), onclickBack).show();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notaddresskm), getActivity().getString(R.string.app_name), onclickBack).show();
            }
        }

        idLayout = R.layout.activity_change_promotion_bccs;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    OnClickListener onclickBack = new OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getActivity().getString(R.string.chuyendoikm));
        if (reasonDTO != null) {
            edtselectreason.setText(reasonDTO.toString());

        } else {
            edtselectreason.setText("");
            edtselectreason.setHint(getActivity().getString(R.string.chonlydo));
        }

    }

    @Override
    public void unit(View v) {

        lnCuocphatsinh = (LinearLayout) v.findViewById(R.id.lnCuocphatsinh);
        lntvcdt = (LinearLayout) v.findViewById(R.id.lntvcdt);
        lntvcdt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonActivity.isNullOrEmpty(subscriberDTO.getLstSubInfrastructureDTO())) {
                    AsynAdviserPromotion asynAdviserPromotion = new AsynAdviserPromotion(getActivity(), new OnPosAdviserPromotion(), moveLogInAct);
                    asynAdviserPromotion.execute("537", subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getOfferId(),
                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict(),
                            subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct(), subscriberDTO.getSubId(), subscriberDTO.getFirstConnect());
                }
            }
        });
//        btninfo = (Button) v.findViewById(R.id.btnInfoPromotion);
//        btninfo.setOnClickListener(this);
        btninfoprepaid = (Button) v.findViewById(R.id.btninfoprepaid);
        btninfoprepaid.setOnClickListener(this);
        txterror = (TextView) v.findViewById(R.id.txterror);
        tvCuocphatsinh = (TextView) v.findViewById(R.id.tvCuocphatsinh);

        lnttinphids = (LinearLayout) v.findViewById(R.id.lnttinphids);
        lnttinphids.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (reasonDTO != null && !CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                    FindFeeByReasonTeleIdAsyn findFeeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(getActivity());
                    findFeeByReasonTeleIdAsyn.execute(subscriberDTO.getTelecomServiceId() + "", reasonDTO.getReasonId(),
                            subscriberDTO.getProductCode());
                } else {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.validate_reason_changepromotion),
                            getActivity().getString(R.string.app_name)).show();
                }

            }
        });
        edit_ngayhieuluc = (EditText) v.findViewById(R.id.edit_ngayhieuluc);
        edit_ngayhieuluc.setVisibility(View.VISIBLE);
        spnDateEffect = (Spinner) v.findViewById(R.id.spnDateEffect);
//        checkTHS();

        edit_ngayhieuluc.setText(dateNowString);
        edit_ngayhieuluc.setOnClickListener(editTextListener);
        edtselectpromotion = (EditText) v.findViewById(R.id.edtselectpromotion);
        btnInfoPromotion = (Button) v.findViewById(R.id.btnInfoPromotion);
        prbCuocdongtruoc = (ProgressBar) v.findViewById(R.id.prbCuocdongtruoc);
//		if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
//			edtselectpromotion.setText(subscriberDTO.getPromotionCode());
//			maKM = subscriberDTO.getPromotionCode();
//
//			String areacode = subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince()
//					+ subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict()
//					+ subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct();
//			GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(getActivity());
//			getAllListPaymentPrePaidAsyn.execute(maKM, subscriberDTO.getProductCode(), areacode, dateNowString);
//
//		}

        edtselectpromotion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmpty(arrPromotionTypeBean)) {
                    Intent intent = new Intent(getActivity(), SearchCodePromotionActivity.class);
                    intent.putExtra("arrPromotionTypeBeans", arrPromotionTypeBean);
                    intent.putExtra("productCode", subscriberDTO.getProductCode());
                    startActivityForResult(intent, 102);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatakm),
                            getActivity().getString(R.string.app_name)).show();
                }

            }
        });
        spnCDT = (Spinner) v.findViewById(R.id.spnCDT);
        spnCDT.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {

                    paymentPrePaidPromotionBeans = arrPaymentPrePaidPromotionBeans.get(arg2);
                    prepaidCode = arrPaymentPrePaidPromotionBeans.get(arg2).getPrePaidCode();


                } else {
                    paymentPrePaidPromotionBeans = null;
                    prepaidCode = "";
                }

                if (!getString(R.string.defautcdt).equals(spnCDT.getSelectedItem().toString())) {
                    checkCDT = true;
                } else {
                    checkCDT = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        edtselectreason = (EditText) v.findViewById(R.id.edtselectreason);
        btn_apply = (Button) v.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);

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
//					fragmentChooseReason.setTargetFragment(FragmentChangePromotionBCCS.this, 1234);
//					ReplaceFragment.replaceFragment(getActivity(), fragmentChooseReason, false);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                            getActivity().getString(R.string.app_name)).show();
                }

            }
        });

        if (subscriberDTO != null) {

            CheckSubChangeQueuePendingAsyn checkSubChangeQueuePendingAsyn = new CheckSubChangeQueuePendingAsyn(
                    getActivity(), new OnPosCheckSubChange(), moveLogInAct);
            checkSubChangeQueuePendingAsyn.execute(subscriberDTO.getSubId() + "", "537");

            // String actionCode,String serviceType, String offerId, String
            // province, String district, String precint
            // lay chuong trinh khuyen mai
//            if (!CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
//                GetPromotionsAsyn getPromotionsAsyn = new GetPromotionsAsyn(getActivity(),
//                        new OnPostGetPromotionsAsyn(), moveLogInAct);
//                getPromotionsAsyn.execute("537", subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getOfferId(),
//                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
//                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict(),
//                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct());
//            } else {
//
//            }

            //lay chuong trinh khuyen mai theo ham loc
            if (!CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubInfrastructureDTO())) {
                GetFilterByPricePromotionAsyn getPromotionsAsyn = new GetFilterByPricePromotionAsyn(getActivity(),
                        new OnPostGetPromotionsAsyn(), moveLogInAct);
                getPromotionsAsyn.execute(ACTION_CODE_PROMOTION, subscriberDTO.getTelecomServiceAlias(),
                        subscriberDTO.getOfferId(), subscriberDTO.getTelecomServiceId(), subscriberDTO.getPromotionCode(),
                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince(),
                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict(),
                        subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct());
            } else {

            }


        }


        if (spnDateEffect.getVisibility() == View.VISIBLE && CommonActivity.isNullOrEmpty(lstDateAffect)) {
            lstDateAffect = StringUtils.initNgayHieuLuc();

            ArrayAdapter<String> adtNgayhieuluc = new ArrayAdapter<>(
                    getActivity(), R.layout.layout_multi_textview, android.R.id.text1, lstDateAffect);

            spnDateEffect.setAdapter(adtNgayhieuluc);
            spnDateEffect.setOnItemSelectedListener(onDateEffectSelect);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1234:
                    reasonDTO = (ReasonDTO) data.getExtras().getSerializable("reasonDTO");
                    if (reasonDTO != null) {
                        edtselectreason.setText(reasonDTO.toString());

                    } else {
                        edtselectreason.setText("");
                        edtselectreason.setHint(getActivity().getString(R.string.chonlydo));
                    }
                    break;

                case 102:
                    promotionTypeBeans = (PromotionTypeBeans) data.getExtras().getSerializable("PromotionTypeBeans");
                    if (promotionTypeBeans != null) {
                        // if
                        // (!CommonActivity.isNullOrEmpty(promotionTypeBeans.getPromProgramCode()))
                        // {
                        // if
                        // (!promotionTypeBeans.getPromProgramCode().equals("-1")) {


                        btnInfoPromotion.setVisibility(View.VISIBLE);
                        btnInfoPromotion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                DialogDetailAtribute.showDialogDetailPromotion(act, promotionTypeBeans, subscriberDTO.getProductCode());
                            }
                        });

                        maKM = promotionTypeBeans.getPromProgramCode();
                        edtselectpromotion.setText(promotionTypeBeans.getName().toString());
                        String areacode = subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince()
                                + subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict()
                                + subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct();
                        GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
                                getActivity(), "");
                        getAllListPaymentPrePaidAsyn.execute(maKM, subscriberDTO.getProductCode(), areacode, dateNowString);

                        reasonDTO = new ReasonDTO();
                        edtselectreason.setText("");
                        edtselectreason.setHint(getActivity().getString(R.string.chonlydo));

                        GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(),
                                new OnPostGetReasonFull(), moveLogInAct);
                        if (!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode()) && !subscriberDTO.getPromotionCode().equals(maKM)) {

                            // etListReasonDTO(String offerId, String actionCode,
                            // String
                            // serviceType, String cusType, String subType)
                            getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(), "537",
                                    subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                    subscriberDTO.getSubType(), subscriberDTO.getTechnology(), maKM);
                        } else {
                            if (CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode()) && !CommonActivity.isNullOrEmpty(maKM)) {
                                getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(), "537",
                                        subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                    subscriberDTO.getSubType(), subscriberDTO.getTechnology(), maKM);
                            } else {
                                getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(), "537",
                                        subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                        subscriberDTO.getSubType(), "", "");
                            }
                        }
//
//                        if (!CommonActivity.isNullOrEmpty(promotionTypeBeans) && !CommonActivity.isNullOrEmpty(promotionTypeBeans.getMonthAmount())) {
//                            btninfo.setVisibility(View.VISIBLE);
//                        } else {
//                            btninfo.setVisibility(View.GONE);
//                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void setPermission() {
        permission = "menu.change.promotion.bccs";
    }


    /**
     * nếu dịch vụ là THS và cước đóng trước khai báo giảm trừ từ tháng đầu tiên thì set date
     */
    private void checkTHS() {
        if ("U".equals(subscriberDTO.getTelecomServiceAlias()) || "2".equals(subscriberDTO.getTelecomServiceAlias())) {
            edit_ngayhieuluc.setVisibility(View.GONE);
            spnDateEffect.setVisibility(View.VISIBLE);
        }
    }


    private OnItemSelectedListener onDateEffectSelect = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            dateEffect = (String) spnDateEffect.getSelectedItem();


            if (dateEffect.equals(TODAY)) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                dateEffect = sdfDate.format(Calendar.getInstance().getTime());
                checkDateEffect = true;

            } else {
                checkDateEffect = false;
            }

            if (checkCDT && checkDateEffect) {
                lnCuocphatsinh.setVisibility(View.VISIBLE);
                GetHotChargeAsyn getHotChargeAsyn = new GetHotChargeAsyn(getActivity(), new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        tvCuocphatsinh.setText(StringUtils.formatMoney(result));
                    }
                }, null);
                getHotChargeAsyn.execute(subscriberDTO.getSubId(), subscriberDTO.getTelecomServiceId());
            } else {
                lnCuocphatsinh.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };

    public OnClickListener moveLogInAct = new OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
            dialog.show();
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

    private class OnPostGetPromotionsAsyn implements OnPostExecuteListener<ArrayList<PromotionTypeBeans>> {

        @Override
        public void onPostExecute(ArrayList<PromotionTypeBeans> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {

//                    if (!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode())) {
//                        for (PromotionTypeBeans item : result) {
//                            if (subscriberDTO.getPromotionCode().equals(item.getPromProgramCode())) {
//                                result.remove(item);
//                                break;
//                            }
//                        }
//
//                    }


                    arrPromotionTypeBean.addAll(result);

                    if (!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode())) {
//                        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
//                        promotionTypeBeans
//                                .setCodeName(getActivity().getString(R.string.keepkm, subscriberDTO.getPromotionCode()));
//                        promotionTypeBeans
//                                .setName(getActivity().getString(R.string.keepkm, subscriberDTO.getPromotionCode()));
//                        promotionTypeBeans.setPromProgramCode(subscriberDTO.getPromotionCode());
//                        arrPromotionTypeBean.add(0, promotionTypeBeans);


                        PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
                        promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
                        promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
                        promotionTypeBeans1.setPromProgramCode("-2");
                        arrPromotionTypeBean.add(0, promotionTypeBeans1);
                    }
//					else{
//						PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
//						promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
//						promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
//						promotionTypeBeans1.setPromProgramCode("-2");
//						arrPromotionTypeBean.add(0,promotionTypeBeans1);
//					}


                } else {
                    arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
                    if (!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode())) {
//                        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
//                        promotionTypeBeans
//                                .setCodeName(getActivity().getString(R.string.keepkm, subscriberDTO.getPromotionCode()));
//                        promotionTypeBeans
//                                .setName(getActivity().getString(R.string.keepkm, subscriberDTO.getPromotionCode()));
//                        promotionTypeBeans.setPromProgramCode(subscriberDTO.getPromotionCode());
//                        arrPromotionTypeBean.add(0, promotionTypeBeans);


                        PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
                        promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
                        promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
                        promotionTypeBeans1.setPromProgramCode("-2");
                        arrPromotionTypeBean.add(0, promotionTypeBeans1);
                    }
//					else{
//						PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
//						promotionTypeBeans1.setCodeName(getActivity().getString(R.string.huykm));
//						promotionTypeBeans1.setName(getActivity().getString(R.string.huykm));
//						promotionTypeBeans1.setPromProgramCode("-2");
//						arrPromotionTypeBean.add(0,promotionTypeBeans1);
//					}
                }
            } else {
                arrPromotionTypeBean = new ArrayList<PromotionTypeBeans>();
                String des = getActivity().getString(R.string.notdatakm);
                if (!CommonActivity.isNullOrEmpty(description)) {
                    des = description;
                }
                CommonActivity.createAlertDialog(getActivity(), des, getActivity().getString(R.string.app_name)).show();
            }

        }

    }

    private class OnPostGetReasonFull implements OnPostExecuteListener<List<ReasonDTO>> {
        @Override
        public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
            arrReasonDTO = new ArrayList<ReasonDTO>();
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrReasonDTO.addAll(result);

                // edtselectreason.setText(arrReasonDTO.get(0).getName());
                // reasonDTO = arrReasonDTO.get(0);
            } else {
                arrReasonDTO = new ArrayList<ReasonDTO>();
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
        String typeMain;

        public GetAllListPaymentPrePaidAsyn(Context context, String type) {
            this.context = context;
            this.typeMain = type;
            if (!CommonActivity.isNullOrEmpty(type)) {
                prbCuocdongtruocSuggess.setVisibility(View.VISIBLE);
            } else {
                prbCuocdongtruoc.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(String... arg0) {
            return getAllListPaymentPrePaid(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(ArrayList<PaymentPrePaidPromotionBeans> result) {
            if (!CommonActivity.isNullOrEmpty(typeMain)) {
                prbCuocdongtruocSuggess.setVisibility(View.GONE);
                arrPaymentPrePaidPromotionBeansDialog = new ArrayList<PaymentPrePaidPromotionBeans>();
            } else {
                prbCuocdongtruoc.setVisibility(View.GONE);
                arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
            }

            if ("0".equals(errorCode)) {

                PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
                paymentPrePaidPromotionBeans.setName(getString(R.string.defautcdt));
                paymentPrePaidPromotionBeans.setPrePaidCode("");
                result.add(0, paymentPrePaidPromotionBeans);
                if (!CommonActivity.isNullOrEmpty(typeMain)) {
                    arrPaymentPrePaidPromotionBeansDialog.addAll(result);
                    if (arrPaymentPrePaidPromotionBeansDialog != null && !arrPaymentPrePaidPromotionBeansDialog.isEmpty()) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                        for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeansDialog) {
                            adapter.add(typePaperBeans.getName());
                        }
                        spnCDTSuggess.setAdapter(adapter);
                    }
                } else {
                    arrPaymentPrePaidPromotionBeans.addAll(result);
                    if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                        for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                            adapter.add(typePaperBeans.getName());
                        }
                        spnCDT.setAdapter(adapter);
                    }
                }

                btn_apply.setVisibility(View.VISIBLE);

            } else {

                btn_apply.setVisibility(View.GONE);

                if (errorCode.equals(Constant.INVALID_TOKEN) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (!CommonActivity.isNullOrEmpty(typeMain)) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                        for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeansDialog) {
                            adapter.add(typePaperBeans.getName());
                        }
                        spnCDTSuggess.setAdapter(adapter);
                    } else {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                        for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                            adapter.add(typePaperBeans.getName());
                        }
                        spnCDT.setAdapter(adapter);
                    }

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

    private class ChangePromotionAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public ChangePromotionAsyn(Context context) {
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
            return changePromotion(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                btn_apply.setVisibility(View.GONE);
                CommonActivity
                        .createAlertDialog(getActivity(), getActivity().getString(R.string.changePromotionSuccess),
                                getActivity().getString(R.string.app_name))
                        .show();

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
        // • isdn
        // • strEffectDate (dd/mm/yyyy (= sysdate là đổi ONLINE, > sysdate là
        // đổi OFFLINE))
        // • reasonIdChangePromotion: ID lý do đổi KM
        // • newPromotionCode => Mã KM mới
        // • prePaidCode => Mã cước đóng trước
        // • promPrePaidId => ID cước đóng trước

        private String changePromotion(String isdn, String strEffectDate, String reasonIdChangePromotion,
                                       String newPromotionCode, String prePaidCode, String promPrePaidId) {
            String original = "";
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdfDate.format(Calendar.getInstance().getTime());
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_changePromotion");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:changePromotion>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append(
                        "<telecomServiceAlias>" + subscriberDTO.getTelecomServiceAlias() + "</telecomServiceAlias>");
                rawData.append(
                        "<payType>" + subscriberDTO.getPayType() + "</payType>");
                rawData.append("<requestMbccs>");
                if (!CommonActivity.isNullOrEmpty(isdn)) {
                    rawData.append("<isdn>" + isdn + "</isdn>");
                }
                rawData.append("<subId>" + subscriberDTO.getSubId() + "</subId>");

                if (!CommonActivity.isNullOrEmpty(strEffectDate)) {
                    rawData.append("<strEffectDate>" + strEffectDate + "</strEffectDate>");
                }

                if (!CommonActivity.isNullOrEmpty(currentDate)) {
                    rawData.append("<currentDate>" + currentDate + "</currentDate>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonIdChangePromotion)) {
                    rawData.append(
                            "<reasonIdChangePromotion>" + reasonIdChangePromotion + "</reasonIdChangePromotion>");
                }
                if (!CommonActivity.isNullOrEmpty(newPromotionCode)) {
                    Log.d("newPromotionCodeeeeeeeeeeee", newPromotionCode);
                    // if(newPromotionCode.equals(subscriberDTO.getPromotionCode())){
                    // rawData.append("<newPromotionCode>" + "" +
                    // "</newPromotionCode>");
                    // }else{
                    rawData.append("<newPromotionCode>" + newPromotionCode + "</newPromotionCode>");
                    // }

                }
                if (!CommonActivity.isNullOrEmpty(prePaidCode)) {
                    rawData.append("<prePaidCode>" + prePaidCode + "</prePaidCode>");
                } else {
                    rawData.append("<prePaidCode>" + "-1" + "</prePaidCode>");
                }
                if (!CommonActivity.isNullOrEmpty(promPrePaidId)) {
                    rawData.append("<promPrePaidId>" + promPrePaidId + "</promPrePaidId>");
                } else {
                    rawData.append("<promPrePaidId>" + "1" + "</promPrePaidId>");
                }
                rawData.append("</requestMbccs>");
                rawData.append("</input>");
                rawData.append("</ws:changePromotion>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_changePromotion");
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_apply:
                // changePromotion(String isdn, String strEffectDate, String
                // reasonIdChangePromotion,
                // String newPromotionCode, String prePaidCode, String
                // promPrePaidId) {
                if (validateCDKM()) {


                    OnClickListener onclick = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            String ngayhieuluc = "";
                            if (edit_ngayhieuluc.getVisibility() == View.VISIBLE) {
                                ngayhieuluc = edit_ngayhieuluc.getText().toString();
                            } else {
                                if (spnDateEffect.getVisibility() == View.VISIBLE) {
                                    ngayhieuluc = dateEffect;
                                }
                            }

                            ChangePromotionAsyn changePromotionAsyn = new ChangePromotionAsyn(getActivity());

                            if (!CommonActivity.isNullOrEmptyArray(paymentPrePaidPromotionBeans.getLstDetailBeans())) {
                                changePromotionAsyn.execute(subscriberDTO.getAccount(),
                                        ngayhieuluc, reasonDTO.getReasonId(), maKM, prepaidCode,
                                        paymentPrePaidPromotionBeans.getLstDetailBeans().get(0).getPromId());
                            } else {
                                changePromotionAsyn.execute(subscriberDTO.getAccount(),
                                        ngayhieuluc, reasonDTO.getReasonId(), maKM, prepaidCode,
                                        "");
                            }

                        }
                    };

                    CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.confirmChangePromotion),
                            getActivity().getString(R.string.app_name), getActivity().getString(R.string.ok),
                            getActivity().getString(R.string.cancel), onclick, null).show();

                }

                break;
//            case R.id.btninfo:
//                if (!CommonActivity.isNullOrEmpty(promotionTypeBeans)
//                        && !CommonActivity.isNullOrEmpty(promotionTypeBeans.getMonthAmount())) {
////                    showDialogDetailPromotion(promotionTypeBeans);
//                    DialogDetailAtribute.showDialogDetailPromotion(getActivity(), promotionTypeBeans);
//                }
//                break;
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

    private void showDialogDetailPromotion(PromotionTypeBeans promotionTypeBeans) {
        //
        final Dialog dialogContractDetail;
        dialogContractDetail = new Dialog(getActivity());
        dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContractDetail.setContentView(R.layout.dialog_layout_promotion_detail);

        TextView tvPromotionName = (TextView) dialogContractDetail.findViewById(R.id.tvPromotionName);
        tvPromotionName.setText(promotionTypeBeans.getCodeName());

        TextView tvmothAmmount = (TextView) dialogContractDetail.findViewById(R.id.tvmothAmmount);
        tvmothAmmount.setText(promotionTypeBeans.getMonthAmount());

        TextView tvAmmountCommit = (TextView) dialogContractDetail.findViewById(R.id.tvAmmountCommit);
        tvAmmountCommit.setText(promotionTypeBeans.getMonthCommitment());


        TextView tvStartDate = (TextView) dialogContractDetail.findViewById(R.id.tvStartDate);
        tvStartDate.setText(promotionTypeBeans.getStartDate());

        TextView tvEffectDate = (TextView) dialogContractDetail.findViewById(R.id.tvEffectDate);
        tvEffectDate.setText(promotionTypeBeans.getEffectDate());

        TextView tvExpireDate = (TextView) dialogContractDetail.findViewById(R.id.tvExpireDate);
        tvExpireDate.setText(promotionTypeBeans.getExpireDate());


        Button btn_cancel = (Button) dialogContractDetail.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogContractDetail.cancel();

            }
        });
        dialogContractDetail.show();

    }

    private boolean validateCDKM() {

        if (edit_ngayhieuluc.getVisibility() == View.VISIBLE) {
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
        } else {
            if (CommonActivity.isNullOrEmpty(dateEffect) || (REQUIRE_DATE_AFFECT.equals(spnDateEffect.getSelectedItem().toString()))) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.requireDateEffect),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }

        }


        if (CommonActivity.isNullOrEmpty(maKM)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkkmmoi),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (!CommonActivity.isNullOrEmpty(maKM) && !CommonActivity.isNullOrEmptyArray(arrPaymentPrePaidPromotionBeans)
                && arrPaymentPrePaidPromotionBeans.size() > 1) {
            if (CommonActivity.isNullOrEmpty(prepaidCode)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcuocdongtruoc),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            // if(!CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubPromotionPrepaidDTO())){
            // if(!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode())){
            // if(maKM.equals(subscriberDTO.getPromotionCode()) &&
            // prepaidCode.equals(subscriberDTO.getLstSubPromotionPrepaidDTO().get(0).getPrepaidCode())){
            // CommonActivity.createAlertDialog(getActivity(),
            // getActivity().getString(R.string.validatecdtold),
            // getActivity().getString(R.string.app_name)).show();
            // return false;
            // }
            // }
            // }

        }
        if (CommonActivity.isNullOrEmpty(reasonDTO)) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatekm),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonCode())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatekm),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }


        return true;
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

    // tu van cuoc dong truoc
    private void showDialogSuggessChargePre(final PromotionOutput promotionOutput) {
        final Dialog dialogSuggess = new Dialog(getActivity());
        dialogSuggess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuggess.setContentView(R.layout.dialog_support_chargedel);
        dialogSuggess.setCancelable(false);
//        dialogSuggess.setTitle(getActivity().getString(R.string.suggesscdt));
        spnpromotionSuggess = (Spinner) dialogSuggess.findViewById(R.id.spnpromotion);

//        ArrayAdapter<PromotionTypeBeans> adapter = new ArrayAdapter<PromotionTypeBeans>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                android.R.id.text1);
//        adapter.addAll(promotionOutput.getLstPromotionTypeBeanses())
        GetPromotionTypeDTOAdapter adapter = new GetPromotionTypeDTOAdapter(promotionOutput.getLstPromotionTypeBeanses(), getActivity());
        spnpromotionSuggess.setAdapter(adapter);


        prbpromotionSuggess = (ProgressBar) dialogSuggess.findViewById(R.id.prbpromotion);
        btnpromotionSuggess = (Button) dialogSuggess.findViewById(R.id.btnpromotion);
        TextView tvcuoctb = (TextView) dialogSuggess.findViewById(R.id.tvcuoctb);
        tvcuoctb.setText(getActivity().getString(R.string.chargetb, promotionOutput.getAvgSubCharge() + ""));
        spnCDTSuggess = (Spinner) dialogSuggess.findViewById(R.id.spnCDT);
        prbCuocdongtruocSuggess = (ProgressBar) dialogSuggess.findViewById(R.id.prbCuocdongtruoc);
        btninfoprepaidSuggess = (Button) dialogSuggess.findViewById(R.id.btninfoprepaid);

        Button btnpromotion = (Button) dialogSuggess.findViewById(R.id.btnpromotion);
        btnpromotion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonActivity.isNullOrEmpty(promotionTypeBeans)) {
//                    showDialogDetailPromotion(promotionTypeBeans);
                    DialogDetailAtribute.showDialogDetailPromotion(getActivity(), promotionTypeBeans, subscriberDTO.getProductCode());
                }
            }
        });
        Button btninfoprepaid = (Button) dialogSuggess.findViewById(R.id.btninfoprepaid);
        btninfoprepaid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans) && !CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans.getPrePaidCode())) {
                    showSelectCuocDongTruoc(paymentPrePaidPromotionBeans);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notinfodetail), getActivity().getString(R.string.app_name)).show();
                }
            }
        });
        spnpromotionSuggess.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                promotionTypeBeans = (PromotionTypeBeans) promotionOutput.getLstPromotionTypeBeanses().get(position);

                maKM = promotionTypeBeans.getPromProgramCode();

                String areacode = subscriberDTO.getLstSubInfrastructureDTO().get(0).getProvince()
                        + subscriberDTO.getLstSubInfrastructureDTO().get(0).getDistrict()
                        + subscriberDTO.getLstSubInfrastructureDTO().get(0).getPrecinct();
                // lay thong tin cuoc dong truoc trong man hinh tu van
                GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn2 = new GetAllListPaymentPrePaidAsyn(
                        getActivity(), "1");
                getAllListPaymentPrePaidAsyn2.execute(maKM, subscriberDTO.getProductCode(), areacode, dateNowString);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCDTSuggess.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrPaymentPrePaidPromotionBeansDialog != null && !arrPaymentPrePaidPromotionBeansDialog.isEmpty()) {
                    paymentPrePaidPromotionBeans = arrPaymentPrePaidPromotionBeansDialog.get(position);
                    prepaidCode = arrPaymentPrePaidPromotionBeansDialog.get(position).getPrePaidCode();
                    if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
                        showSelectCuocDongTruoc(arrPaymentPrePaidPromotionBeansDialog.get(position));
                    }

                } else {
                    paymentPrePaidPromotionBeans = null;
                    prepaidCode = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btnok = (Button) dialogSuggess.findViewById(R.id.btnok);
        btnok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (promotionTypeBeans != null) {
                    edtselectpromotion.setText(promotionTypeBeans.getName().toString());
                }

                reasonDTO = new ReasonDTO();
                edtselectreason.setText("");
                edtselectreason.setHint(getActivity().getString(R.string.chonlydo));

                GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(),
                        new OnPostGetReasonFull(), moveLogInAct);
                if (!CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode()) && !subscriberDTO.getPromotionCode().equals(maKM)) {

                    // etListReasonDTO(String offerId, String actionCode,
                    // String
                    // serviceType, String cusType, String subType)
                    getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(), "537",
                            subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                            subscriberDTO.getSubType(), subscriberDTO.getTechnology(), maKM);
                } else {
                    if (CommonActivity.isNullOrEmpty(subscriberDTO.getPromotionCode()) && !CommonActivity.isNullOrEmpty(maKM)) {
                        getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(), "537",
                                subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                subscriberDTO.getSubType(), subscriberDTO.getTechnology(), maKM);
                    } else {
                        // modify lai
                        getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(), "537",
                                subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                                subscriberDTO.getSubType(), "", "");
                    }
                }

//                if (!CommonActivity.isNullOrEmpty(promotionTypeBeans) && !CommonActivity.isNullOrEmpty(promotionTypeBeans.getMonthAmount())) {
//                    btninfo.setVisibility(View.VISIBLE);
//                } else {
//                    btninfo.setVisibility(View.GONE);
//                }

                // set lai thong tin cuoc dong truoc
                if (!CommonActivity.isNullOrEmptyArray(arrPaymentPrePaidPromotionBeansDialog)) {
                    arrPaymentPrePaidPromotionBeans = arrPaymentPrePaidPromotionBeansDialog;

                    if (arrPaymentPrePaidPromotionBeans != null && !arrPaymentPrePaidPromotionBeans.isEmpty()) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
                        for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
                            adapter.add(typePaperBeans.getName());
                        }
                        spnCDT.setAdapter(adapter);
                        if (!CommonActivity.isNullOrEmpty(paymentPrePaidPromotionBeans)) {
                            for (PaymentPrePaidPromotionBeans item : arrPaymentPrePaidPromotionBeans) {
                                if (item.getPrePaidCode().equals(paymentPrePaidPromotionBeans.getPrePaidCode())) {
                                    spnCDT.setSelection(arrPaymentPrePaidPromotionBeans.indexOf(item));
                                    prbCuocdongtruoc.setVisibility(View.GONE);
                                    break;
                                }

                            }
                        }
                    }
                }
                dialogSuggess.cancel();
            }
        });
        Button btncancel = (Button) dialogSuggess.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                promotionTypeBeans = new PromotionTypeBeans();
                maKM = "";
                paymentPrePaidPromotionBeans = null;
                prepaidCode = "";
                dialogSuggess.dismiss();
            }
        });

        dialogSuggess.show();
    }

    private class OnPosAdviserPromotion implements OnPostExecuteListener<PromotionOutput> {

        @Override
        public void onPostExecute(PromotionOutput result, String errorCode, String description) {
            if (!CommonActivity.isNullOrEmpty(result) && !CommonActivity.isNullOrEmpty(result.getAvgSubCharge()) && !CommonActivity.isNullOrEmptyArray(result.getLstPromotionTypeBeanses())) {
                showDialogSuggessChargePre(result);
            } else {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdataadvisier), getActivity().getString(R.string.app_name)).show();
            }
        }
    }


}
