package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by thinhhq1 on 8/12/2017.
 */
public class DialogDetailAtribute {

   static  String detailPromotion = "";
    public static  void showDialogDetailPromotion(Activity activity,PromotionTypeBeans promotionTypeBeans, String productCode) {

        final Dialog dialogContractDetail;
        GetAllDetailPromotionAysn getAllDetailPromotionAysn = null;
        dialogContractDetail = new Dialog(activity);
        dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContractDetail.setContentView(R.layout.dialog_layout_promotion_detail);

        TextView tvPromotionName = (TextView) dialogContractDetail.findViewById(R.id.tvPromotionName);
        tvPromotionName.setText(promotionTypeBeans.getCodeName());

        TextView tvmothAmmount = (TextView) dialogContractDetail.findViewById(R.id.tvmothAmmount);
        tvmothAmmount.setText(promotionTypeBeans.getMonthAmount());

        TextView tvAmmountCommit = (TextView) dialogContractDetail.findViewById(R.id.tvAmmountCommit);
        tvAmmountCommit.setText(promotionTypeBeans.getMonthCommitment());


        TextView tvEffectDate = (TextView) dialogContractDetail.findViewById(R.id.tvEffectDate);
        tvEffectDate.setText(promotionTypeBeans.getEffectDate());

        TextView tvExpireDate = (TextView) dialogContractDetail.findViewById(R.id.tvExpireDate);
        tvExpireDate.setText(promotionTypeBeans.getExpireDate());

        TextView tvDesc = (TextView) dialogContractDetail.findViewById(R.id.tvDesc);
        tvDesc.setText(promotionTypeBeans.getDescription());

        final TextView tvDetail = (TextView) dialogContractDetail.findViewById(R.id.tvDetail);

            detailPromotion = "";
             getAllDetailPromotionAysn = new GetAllDetailPromotionAysn(activity, new OnPostExecuteListener<ArrayList<BillingPromotionDetailDTO>>() {
                @Override
                public void onPostExecute(ArrayList<BillingPromotionDetailDTO> result, String errorCode, String description) {
                    Collections.sort(result, new Comparator<BillingPromotionDetailDTO>() {
                        @Override
                        public int compare(BillingPromotionDetailDTO o1, BillingPromotionDetailDTO o2) {
                            return o1.getStartMonth().compareTo(o2.getStartMonth());
                        }
                    });

                    int sizeResult = result.size();
                    for (int i = 0; i < sizeResult; i++) {
                        BillingPromotionDetailDTO obj = result.get(i);
                        if (obj.getPromValueName().equals("100%")) {
                            detailPromotion += "Tháng " + obj.getStartMonth() ;
                            if(obj.getNumMonth() == 1) detailPromotion+= ": Tháng tặng;\n";
                            else detailPromotion+= " đến " + (obj.getStartMonth() + obj.getNumMonth() - 1) + ": Tháng tặng;\n";
                        } else {
                            detailPromotion += "Tháng " + obj.getStartMonth();
                            if(obj.getNumMonth()==1) detailPromotion+=": " + obj.getPromValueName() + ";\n";
                                else detailPromotion+=" đến " + (obj.getStartMonth() + obj.getNumMonth() - 1) + ": " + obj.getPromValueName() + ";\n";
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

            getAllDetailPromotionAysn.execute(promotionTypeBeans.getPromProgramCode(), productCode);


    }

    public static  void showDialogPackageDetail(Activity activity,ProductOfferCharacterClone productOfferCharacter) {

        final Dialog dialogPackageDetail;
        dialogPackageDetail = new Dialog(activity);
        dialogPackageDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPackageDetail.setContentView(R.layout.dialog_layout_package_detail);


        TextView tvPrice = (TextView) dialogPackageDetail.findViewById(R.id.tvPrice);
        tvPrice.setText(StringUtils.formatMoney(productOfferCharacter.getListingPrice()));

        TextView tvSpeedDown = (TextView) dialogPackageDetail.findViewById(R.id.tvSpeedDown);
        tvSpeedDown.setText(StringUtils.formatMoney(productOfferCharacter.getDownLoadSpeed()));

        TextView tvSpeedUp = (TextView) dialogPackageDetail.findViewById(R.id.tvSpeedUp);
        tvSpeedUp.setText(StringUtils.formatMoney(productOfferCharacter.getUpLoadSpeed()));


        Button btn_cancel = (Button) dialogPackageDetail.findViewById(R.id.btnCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogPackageDetail.cancel();

            }
        });
        dialogPackageDetail.show();

    }
}
