package com.viettel.bss.viettelpos.v4.omichanel.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BillingPromotionDetailDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.GetAllDetailPromotionAysn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.listener.OnClickListener;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniAccountPrepaidInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniProductInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PaymentPrePaidDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 9/26/2017.
 */

public class SubPreChangeAdapter extends RecyclerView.Adapter<SubPreChangeAdapter.ViewHolder> {
    private Activity activity;
    private List<OmniAccountPrepaidInfo> accoutPrepaidRecords;
    OnPostExecute<OmniAccountPrepaidInfo> onPostExecute;
    private Integer index;
    private Long feeTranfer;
    View.OnClickListener moveLogInAct;

    public SubPreChangeAdapter(Activity activity, Long feeTranfer, View.OnClickListener moveLogInAct, List<OmniAccountPrepaidInfo> accoutPrepaidRecords, OnPostExecute<OmniAccountPrepaidInfo> onPostExecute) {
        this.activity = activity;
        this.accoutPrepaidRecords = accoutPrepaidRecords;
        this.onPostExecute = onPostExecute;
        this.feeTranfer = feeTranfer;
        this.moveLogInAct = moveLogInAct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_pre_charge, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == getItemCount() - 1) {
            holder.llTotalFee.setVisibility(View.VISIBLE);
            holder.cvDetail.setVisibility(View.GONE);
            if (!CommonActivity.isNullOrEmpty(feeTranfer) && feeTranfer > 0) {
                holder.llFeeTrans.setVisibility(View.VISIBLE);
                holder.tvFeeTrans.setText(StringUtils.formatMoney(feeTranfer + " VNĐ"));
            } else {
                holder.llFeeTrans.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmpty(accoutPrepaidRecords)) {
                Long totalFee = feeTranfer;
                for (OmniAccountPrepaidInfo prepaidInfo : accoutPrepaidRecords) {
                    if (!CommonActivity.isNullOrEmpty(prepaidInfo) &&
                            !CommonActivity.isNullOrEmpty(prepaidInfo.getProductInfo()) &&
                            !CommonActivity.isNullOrEmpty(prepaidInfo.getProductInfo().getTotalPrepaidAmount())) {
                        totalFee += prepaidInfo.getProductInfo().getTotalPrepaidAmount();
                    }
                }
                holder.tvTotalFrice.setText(StringUtils.formatMoney(totalFee + "") + " VNĐ");
            } else {
                holder.tvTotalFrice.setText("0 VNĐ");
            }
        } else {
            holder.llTotalFee.setVisibility(View.GONE);
            holder.cvDetail.setVisibility(View.VISIBLE);

            final OmniAccountPrepaidInfo prepaidInfo = accoutPrepaidRecords.get(position);
            if (CommonActivity.isNullOrEmpty(prepaidInfo)) {
                holder.cvDetail.setVisibility(View.GONE);
            } else {
                if (!CommonActivity.isNullOrEmpty(prepaidInfo.getAccount())) {
                    holder.llSub.setVisibility(View.VISIBLE);
                    holder.tvSub.setText(prepaidInfo.getAccount());
                } else {
                    holder.llSub.setVisibility(View.GONE);
                }
                if (!CommonActivity.isNullOrEmpty(prepaidInfo.getProductInfo())) {

                    final OmniProductInfo productInfo = prepaidInfo.getProductInfo();
                    if (!CommonActivity.isNullOrEmpty(productInfo.getProductName())) {
                        holder.llPageData.setVisibility(View.VISIBLE);
                        holder.tvPackData.setText(productInfo.getProductName());
                    } else {
                        holder.llPageData.setVisibility(View.GONE);
                    }
                    if (!CommonActivity.isNullOrEmptyArray(productInfo.getPrepaidCode(), productInfo.getOldPrepaidCode())) {
                        final String cdtNew = productInfo.getPrepaidCode() +
                                (CommonActivity.isNullOrEmpty(productInfo.getPrepaidMonth()) ? "" : " - " + productInfo.getPrepaidMonth() + " tháng");
                        String cdtOld = productInfo.getOldPrepaidCode() +
                                (CommonActivity.isNullOrEmpty(productInfo.getOldPrepaidMonth()) ? "" : " - " + productInfo.getOldPrepaidMonth() + " tháng");
                        holder.tvCDTNew.setText(cdtNew);
                        holder.tvCDTOld.setText(cdtOld);
                        holder.tvCDTNew.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!CommonActivity.isNullOrEmpty(prepaidInfo.getLstPaymentPrePaidDetailNew())) {
                                    PaymentPrePaidDetail prePaidNew = prepaidInfo.getLstPaymentPrePaidDetailNew().get(0);
                                    String tittle = activity.getString(R.string.prepaid_new_tittle, prepaidInfo.getAccount());
                                    String message = null;
                                    message = activity.getString(R.string.message_prepaid_new,
                                            cdtNew,
                                            prePaidNew.getMonthPrePaid(),
                                            StringUtils.formatMoney(prePaidNew.getPromAmount() + ""),
                                            StringUtils.formatMoney(prePaidNew.getTotalMoney() + ""),
//                                            prePaidNew.getDiscountAmount(),
                                            prePaidNew.getDiscountFromTo()
                                    ).replaceAll("->", " đến tháng ");
                                    CommonActivity.createDialog(activity,
                                            message,
                                            tittle,
                                            activity.getString(R.string.select_again),
                                            activity.getString(R.string.OK),
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    index = position;
                                                    onPostExecute.onPostExecute(prepaidInfo);
                                                }
                                            },
                                            null
                                    ).show();

                                } else {
                                    CommonActivity.showConfirmValidate(activity, R.string.validate_select_new_cdt);
                                }
                            }
                        });
                        holder.tvCDTOld.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!CommonActivity.isNullOrEmpty(prepaidInfo.getLstPaymentPrePaidDetailOld())) {
                                    SubPromotionPrepaidDTO prePaidOld = prepaidInfo.getLstPaymentPrePaidDetailOld().get(0);
                                    String tittle = activity.getString(R.string.prepaid_old_tittle, prepaidInfo.getAccount());
                                    String message = null;

                                    message = activity.getString(R.string.message_prepaid_old,
                                            prePaidOld.getPrepaidCode(),
                                            prePaidOld.getNumMonth(),
                                            StringUtils.formatMoney(prePaidOld.getPrepaidBilling() + ""),
                                            DateTimeUtils.convertDateTimeToString(new Date(prePaidOld.getCreateDate())),
                                            DateTimeUtils.convertDateTimeToString(new Date(prePaidOld.getEffectDate()))
                                    );
                                    showDialog(message, tittle);
                                } else {
                                    CommonActivity.showConfirmValidate(activity, R.string.validate_select_old_cdt);
                                }
                            }
                        });
                    }
                    if (CommonActivity.isNullOrEmpty(productInfo.getPromotionCode())) {
                        holder.tvCode.setText("");
                        holder.llCode.setVisibility(View.GONE);
                    } else {
                        holder.llCode.setVisibility(View.VISIBLE);
                        holder.tvCode.setText(productInfo.getPromotionCode());
                    }

                    holder.imgDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CommonActivity.isNullOrEmpty(productInfo.getPromotionCode())) {
                                CommonActivity.showConfirmValidate(activity, R.string.notkhuyenmai);
                            } else {
                                showDialogDetailPromotion(activity,productInfo.getPromotionCode(),
                                        productInfo.getMonthAmount()+"",productInfo.getProductCode());
                            }
                        }
                    });

                    if (!CommonActivity.isNullOrEmpty(productInfo.getMonthAmount())) {
                        holder.llMonthAmount.setVisibility(View.VISIBLE);
                        holder.tvMonthPromotion.setText(productInfo.getMonthAmount() + " tháng");
                    } else {
                        holder.llMonthAmount.setVisibility(View.GONE);
                    }
                    if (!CommonActivity.isNullOrEmpty(productInfo.getTotalPrepaidAmount())) {
                        holder.tvFee.setText(StringUtils.formatMoney(productInfo.getTotalPrepaidAmount() + "") + " VNĐ");
                    } else {
                        holder.tvFee.setText("0 VNĐ");
                    }
                } else {
                    holder.llSub.setVisibility(View.GONE);
                    holder.llPageData.setVisibility(View.GONE);
                    holder.llMonthAmount.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return accoutPrepaidRecords.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llTotalFee)
        RelativeLayout llTotalFee;
        @BindView(R.id.llFeeTrans)
        RelativeLayout llFeeTrans;
        @BindView(R.id.cvDetail)
        CardView cvDetail;
        @BindView(R.id.llSub)
        LinearLayout llSub;
        @BindView(R.id.llCode)
        LinearLayout llCode;
        @BindView(R.id.llPageData)
        LinearLayout llPageData;
        @BindView(R.id.llMonthAmount)
        LinearLayout llMonthAmount;
        @BindView(R.id.llFee)
        LinearLayout llFee;
        @BindView(R.id.tvTotalFee)
        TextView tvTotalFrice;
        @BindView(R.id.tvSub)
        TextView tvSub;
        @BindView(R.id.tvPackData)
        TextView tvPackData;
        @BindView(R.id.tvCDTOld)
        TextView tvCDTOld;
        @BindView(R.id.tvCDTNew)
        TextView tvCDTNew;
        @BindView(R.id.tvMonthPromotion)
        TextView tvMonthPromotion;
        @BindView(R.id.tvFee)
        TextView tvFee;
        @BindView(R.id.tvFeeTrans)
        TextView tvFeeTrans;
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.imgDetail)
        ImageView imgDetail;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void setNewPrepaid(PaymentPrePaidPromotionBeans newPrepaid) {
        if (!CommonActivity.isNullOrEmpty(index)) {
            List<OmniAccountPrepaidInfo> tmp = new ArrayList<>();
            for (int i = 0; i < accoutPrepaidRecords.size(); i++) {
                OmniAccountPrepaidInfo prepaidInfo = accoutPrepaidRecords.get(i);
                if (index.intValue() == i) {
                    OmniProductInfo productInfo = prepaidInfo.getProductInfo();

                    productInfo.setPrepaidCode(newPrepaid.getPrePaidCode());
                    productInfo.setPrepaidId(Long.valueOf(newPrepaid.getId()));
                    productInfo.setTotalPrepaidAmount(newPrepaid.getTotalMoney());
                    prepaidInfo.setProductInfo(productInfo);
                    PaymentPrePaidDetail detail = new PaymentPrePaidDetail();
                    if (!CommonActivity.isNullOrEmpty(newPrepaid.getLstDetailBeans())) {
                        List<PaymentPrePaidDetail> prePaidDetailNew = prepaidInfo.getLstPaymentPrePaidDetailNew();
                        PaymentPrePaidDetailBeans detailBeans = newPrepaid.getLstDetailBeans().get(0);

                        detail.setMoneyUnit(detailBeans.getMoneyUnit());
                        detail.setPromAmount(CommonActivity.isNullOrEmpty(detailBeans.getPromAmount()) ?
                                0 : Long.valueOf(detailBeans.getPromAmount()));
                        detail.setMonthPrePaid(CommonActivity.isNullOrEmpty(detailBeans.getSubMonth()) ?
                                0 : Long.valueOf(detailBeans.getSubMonth()));
                        detail.setTotalMoney(CommonActivity.isNullOrEmpty(detailBeans.getTotalMoney()) ?
                                0 : Long.valueOf(detailBeans.getTotalMoney()));
                        detail.setDiscountAmount(CommonActivity.isNullOrEmpty(detailBeans.getDiscountAmount())
                                ? "0" : detailBeans.getDiscountAmount());
                        detail.setDiscountFromTo(detailBeans.getStartMonth() + "->" + detailBeans.getEndMonth());

                        // set lai
                        prePaidDetailNew.set(0, detail);
                        prepaidInfo.setLstPaymentPrePaidDetailNew(prePaidDetailNew);
                    }
                }
                tmp.add(prepaidInfo);
            }
            accoutPrepaidRecords = tmp;
            notifyDataSetChanged();
        }
    }

    public List<OmniAccountPrepaidInfo> getAccoutPrepaidRecords() {
        return accoutPrepaidRecords;
    }

    private void showDialog(String message, String title) {
        CommonActivity.createAlertDialogInfo(activity, message, title, "ok").show();
    }

    public static void showDialogDetailPromotion(Activity activity, String promotionCOde,String monthAmount, String productCode) {

        final Dialog dialogContractDetail;
        GetAllDetailPromotionAysn getAllDetailPromotionAysn = null;
        dialogContractDetail = new Dialog(activity);
        dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContractDetail.setContentView(R.layout.dialog_layout_promotion_detail);

        TextView tvPromotionName = (TextView) dialogContractDetail.findViewById(R.id.tvPromotionName);
        tvPromotionName.setText(promotionCOde);

        TextView tvmothAmmount = (TextView) dialogContractDetail.findViewById(R.id.tvmothAmmount);
        tvmothAmmount.setText(monthAmount);

//        TextView tvAmmountCommit = (TextView) dialogContractDetail.findViewById(R.id.tvAmmountCommit);
//        tvAmmountCommit.setText(promotionTypeBeans.getMonthCommitment());
//
//
//        TextView tvEffectDate = (TextView) dialogContractDetail.findViewById(R.id.tvEffectDate);
//        tvEffectDate.setText(promotionTypeBeans.getEffectDate());
//
//
//
//        TextView tvDesc = (TextView) dialogContractDetail.findViewById(R.id.tvDesc);
//        tvDesc.setText(promotionTypeBeans.getDescription());

        final TextView tvDetail = (TextView) dialogContractDetail.findViewById(R.id.tvDetail);

        getAllDetailPromotionAysn = new GetAllDetailPromotionAysn(activity, new OnPostExecuteListener<ArrayList<BillingPromotionDetailDTO>>() {
            @Override
            public void onPostExecute(ArrayList<BillingPromotionDetailDTO> result, String errorCode, String description) {
                Collections.sort(result, new Comparator<BillingPromotionDetailDTO>() {
                    @Override
                    public int compare(BillingPromotionDetailDTO o1, BillingPromotionDetailDTO o2) {
                        return o1.getStartMonth().compareTo(o2.getStartMonth());
                    }
                });
                String detailPromotion = "";

                int sizeResult = result.size();
                for (int i = 0; i < sizeResult; i++) {
                    BillingPromotionDetailDTO obj = result.get(i);
                    TextView tvExpireDate = (TextView) dialogContractDetail.findViewById(R.id.tvEffectDate);
                    tvExpireDate.setText(DateTimeUtils.convertDateSoapToString(obj.getCreateDatetime()));
                    if (obj.getPromValueName().equals("100%")) {
                        detailPromotion += "Tháng " + obj.getStartMonth();
                        if (obj.getNumMonth() == 1) detailPromotion += ": Tháng tặng;\n";
                        else
                            detailPromotion += " đến " + (obj.getStartMonth() + obj.getNumMonth() - 1) + ": Tháng tặng;\n";
                    } else {
                        detailPromotion += "Tháng " + obj.getStartMonth();
                        if (obj.getNumMonth() == 1)
                            detailPromotion += ": " + obj.getPromValueName() + ";\n";
                        else
                            detailPromotion += " đến " + (obj.getStartMonth() + obj.getNumMonth() - 1) + ": " + obj.getPromValueName() + ";\n";
                    }
                }
                tvDetail.setText(detailPromotion);

                Button btn_cancel = (Button) dialogContractDetail.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialogContractDetail.cancel();

                    }
                });
                dialogContractDetail.show();

            }
        }, null);

        getAllDetailPromotionAysn.execute(promotionCOde, productCode);


    }
}
