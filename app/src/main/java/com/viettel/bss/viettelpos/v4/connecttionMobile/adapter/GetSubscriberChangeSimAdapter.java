package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.GetSubscriberAgeAsyncTask;
import com.viettel.bss.viettelpos.v4.bo.AgeSubscriberDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.GetInfoPackageAsyn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCheckUsuallyCall;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentViewOcsHlr;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.dialog.AgeSubscriberDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetSubscriberChangeSimAdapter extends BaseAdapter {
    private final Activity mContext;
    private final OnChangeCustomer onChangeCustomer;
    View.OnClickListener moveLogInAct;
    private List<SubscriberDTO> arrInfoSubs = new ArrayList<>();
    private boolean isEditCus = false;

    public GetSubscriberChangeSimAdapter(List<SubscriberDTO> arr,
                                         Activity context, OnChangeCustomer onChangeCustomer, View.OnClickListener moveLogInAct) {
        this.arrInfoSubs = arr;
        this.mContext = context;
        this.onChangeCustomer = onChangeCustomer;
        this.moveLogInAct = moveLogInAct;
    }

    @Override
    public int getCount() {
        return arrInfoSubs.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arrInfoSubs.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        ViewHoler holder;
        View mView = arg1;
        if (mView == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mView = inflater.inflate(R.layout.layout_sub_changesim_item, arg2,
                    false);
            holder = new ViewHoler();
            holder.txtisdnandaccount = (TextView) mView
                    .findViewById(R.id.txtisdnandaccount);
            holder.txtserialchangesim = (TextView) mView
                    .findViewById(R.id.txtserialchangesim);
            holder.txtservive = (TextView) mView.findViewById(R.id.txtservive);
            holder.txtctkmht = (TextView) mView.findViewById(R.id.txtctkmht);
            holder.txttinhtrang = (TextView) mView
                    .findViewById(R.id.txttinhtrang);
            holder.txttrangthai = (TextView) mView
                    .findViewById(R.id.txttrangthai);
            holder.txtsogiayto = (TextView) mView
                    .findViewById(R.id.txtsogiayto);
            holder.txtgoicuoc = (TextView) mView.findViewById(R.id.txtgoicuoc);
            holder.btnInfoProduct = (Button) mView.findViewById(R.id.btnInfoProduct);
            holder.txthoten = (TextView) mView.findViewById(R.id.txthoten);

            holder.txtaddress = (TextView) mView.findViewById(R.id.txtaddress);
            holder.txtloaikh = (TextView) mView.findViewById(R.id.txtloaikh);
            holder.txtBirthDay = (TextView) mView
                    .findViewById(R.id.tvBirthDay);
            holder.imgEdit = (ImageView) mView.findViewById(R.id.imgEdit);
            holder.txtnoicap = (TextView) mView.findViewById(R.id.txtnoicap);
            holder.txtngaycap = (TextView) mView.findViewById(R.id.txtngaycap);
            holder.txtngaykichhoat = (TextView) mView
                    .findViewById(R.id.txtngaykichhoat);
            holder.txttypetb = (TextView) mView.findViewById(R.id.txttypetb);
            holder.btnCheckUsuallyCall = mView
                    .findViewById(R.id.btnCheckUsuallyCall);
            holder.btnViewOCS = mView.findViewById(R.id.btnViewOCS);
            holder.tvViewAgeSub = (TextView) mView.findViewById(R.id.tvViewAgeSub);
            holder.tvDoituong = (TextView) mView.findViewById(R.id.tvDoituong);
            mView.setTag(holder);
        } else {
            holder = (ViewHoler) mView.getTag();
        }
        final SubscriberDTO infoSub = this.arrInfoSubs.get(arg0);

        holder.tvViewAgeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSubscriberAgeAsyncTask getSubscriberAgeAsyncTask = new GetSubscriberAgeAsyncTask(mContext, new OnPostExecuteListener<AgeSubscriberDTO>() {
                    @Override
                    public void onPostExecute(AgeSubscriberDTO result, String errorCode, String description) {
                        if (!CommonActivity.isNullOrEmpty(result)) {
                            AgeSubscriberDialog ageSubscriberDialog = new AgeSubscriberDialog();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ageSubscriberDTO", (Serializable) result);
                            ageSubscriberDialog.setArguments(bundle);
                            ReplaceFragment.replaceFragment(mContext, ageSubscriberDialog, false);
                        } else {
                            CommonActivity.showConfirmValidate(mContext, R.string.not_data_total_charge);
                        }
                    }
                }, moveLogInAct);
                getSubscriberAgeAsyncTask.execute(infoSub.getSubId() + "");
            }
        });
        holder.txtngaykichhoat.setText(StringUtils.convertDate(infoSub
                .getStaDatetime()));
        holder.txtisdnandaccount.setText(infoSub.getIsdn());
        if (infoSub.isSim4G()) {
            holder.txtserialchangesim.setText(infoSub.getSerial() + "-"
                    + mContext.getString(R.string.sim4G));
        } else {
            holder.txtserialchangesim.setText(infoSub.getSerial());
        }
        if ("2".equals(infoSub.getPayType())) {
            holder.txttypetb.setText(mContext.getString(R.string.tv_tra_truoc));
        } else {
            holder.txttypetb.setText(mContext.getString(R.string.tv_tra_sau));
        }

        if (!CommonActivity.isNullOrEmpty(infoSub.getCustomerDTOInput())) {
            holder.txthoten.setText(infoSub.getCustomerDTOInput().getName());
        }
        holder.txttinhtrang.setText(infoSub.getActStatusText());
        holder.txtservive.setText(infoSub.getTelecomServiceName());
        if (!CommonActivity.isNullOrEmptyArray(infoSub.getLstSubPromotionDTO())) {
            holder.txtctkmht.setText(infoSub.getLstSubPromotionDTO().get(0)
                    .getPromotionName());
        }

        if (!CommonActivity.isNullOrEmpty(infoSub.getCustomerDTOInput())) {
            holder.txtaddress.setText(infoSub.getCustomerDTOInput()
                    .getAddress());
            holder.txtBirthDay.setText(StringUtils.convertDate(infoSub
                    .getCustomerDTOInput().getBirthDate()));

            if (!CommonActivity.isNullOrEmptyArray(infoSub
                    .getCustomerDTOInput().getListCustIdentity())) {
                holder.txtngaycap.setText(DateTimeUtils.convertDate(infoSub.getCustomerDTOInput()
                        .getListCustIdentity().get(0).getIdIssueDate()));
                holder.txtnoicap.setText(infoSub.getCustomerDTOInput()
                        .getListCustIdentity().get(0).getIdIssuePlace());
            }

        }
        holder.txttrangthai.setText(infoSub.getStatus());

        if (!CommonActivity.isNullOrEmpty(infoSub.getOfferId())) {
            holder.btnInfoProduct.setVisibility(View.VISIBLE);
            holder.btnInfoProduct.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    final GetInfoPackageAsyn getInfoPackageAsyn = new GetInfoPackageAsyn((Activity) mContext, new OnPostExecuteListener<ProductOfferCharacterClone>() {

                        @Override
                        public void onPostExecute(ProductOfferCharacterClone result, String errorCode, String description) {
                            DialogDetailAtribute.showDialogPackageDetail((Activity) mContext, result);
                        }
                    }, null);

                    getInfoPackageAsyn.execute(infoSub.getOfferId() + "");
                }
            });


        }
        holder.txtgoicuoc.setText(infoSub.getProductName());


        if (!CommonActivity.isNullOrEmpty(infoSub.getCustomerDTOInput())) {
            holder.txtloaikh.setText(infoSub.getCustomerDTOInput()
                    .getCustTypeDTO().getCustType()
                    + "-"
                    + infoSub.getCustomerDTOInput().getCustTypeDTO().getName());
            if (!CommonActivity.isNullOrEmptyArray(infoSub
                    .getCustomerDTOInput().getListCustIdentity())) {
                holder.txtsogiayto.setText(infoSub.getCustomerDTOInput()
                        .getListCustIdentity().get(0).getIdNo());
            }
        }

//		if (!infoSub.isMarkNotOwner()) {
//			if(!CommonActivity.isNullOrEmpty(infoSub.getCustomerDTOInput()) && !CommonActivity.isNullOrEmpty(infoSub.getCustomerDTOInput().getCustTypeDTO()) && !CommonActivity.isNullOrEmpty(infoSub.getCustomerDTOInput().getCustTypeDTO().getGroupType()) && "1".equals(infoSub.getCustomerDTOInput().getCustTypeDTO().getGroupType())){
//				holder.imgEdit.setVisibility(View.VISIBLE);
//			}else{
//				holder.imgEdit.setVisibility(View.GONE);
//			}
//
//		} else {
        holder.imgEdit.setVisibility(View.GONE);
//		}

//		holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				onChangeCustomer.onChangeCustomerListener(infoSub);
//
//			}
//		});

        holder.btnCheckUsuallyCall
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("isdnKey", infoSub.getIsdn());

                        Fragment f = new FragmentCheckUsuallyCall();
                        f.setArguments(bundle);
                        ReplaceFragment.replaceFragment((Activity) mContext, f,
                                true);

                    }
                });
        holder.btnViewOCS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("isdnKey", infoSub.getIsdn());

                Fragment f = new FragmentViewOcsHlr();
                f.setArguments(bundle);
                ReplaceFragment.replaceFragment((Activity) mContext, f, true);

            }
        });

        holder.tvDoituong.setText(infoSub.getSubObject());

        return mView;
    }

    public interface OnChangeCustomer {
        void onChangeCustomerListener(SubscriberDTO subscriberDTO);
    }

    class ViewHoler {
        TextView txtisdnandaccount, txtserialchangesim, txtsogiayto,
                txtservive, txttinhtrang, txtctkmht, txttrangthai, txtgoicuoc,
                txthoten, txtaddress, txtloaikh, txtBirthDay, txtnoicap,
                txtngaycap, txtngaykichhoat, txttypetb, tvViewAgeSub, tvDoituong;
        Button btnInfoProduct;
        ImageView imgEdit;
        View btnViewOCS;
        View btnCheckUsuallyCall;
    }

}
