package com.viettel.bss.viettelpos.v4.sale.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.GetSubscriberAgeAsyncTask;
import com.viettel.bss.viettelpos.v4.bo.AgeSubscriberDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.GetInfoPackageAsyn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.dialog.AgeSubscriberDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubscriberInfoAdapter extends BaseAdapter {
    private final Activity context;
    private final OnSelectSubscriber onSelectSubscriber;
    public OnClickListener moveLogInAct = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent intent = new Intent(getActivity(), LoginActivity.class);
            // startActivity(intent);
            // getActivity().finish();
            String permission = "";
            LoginDialog dialog = new LoginDialog((Activity) context, permission);
            dialog.show();

        }
    };
    OnViewSubscriberChild onViewSubscriberChild;
    TextView txtketcuoi;
    private List<SubscriberDTO> subscriberDTOs = new ArrayList();

    public SubscriberInfoAdapter(Activity context, List<SubscriberDTO> lstData,
                                 OnSelectSubscriber onSelectSubscriber,
                                 OnViewSubscriberChild onViewSubscriberChild) {

        this.context = context;
        this.subscriberDTOs = lstData;
        this.onSelectSubscriber = onSelectSubscriber;
        this.onViewSubscriberChild = onViewSubscriberChild;
    }

    @Override
    public int getCount() {
        if (subscriberDTOs == null) {
            return 0;
        } else {
            return subscriberDTOs.size();
        }
    }

    @Override
    public Object getItem(int arg0) {
        return subscriberDTOs.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, final View convertview,
                        ViewGroup viewGroup) {
        View row = convertview;
        final ViewHoder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_subscriber_row, viewGroup,
                    false);
            holder = new ViewHoder();
            holder.tv_isdnAccount = (TextView) row
                    .findViewById(R.id.tv_isdnAccount);
            holder.tv_serviceType = (TextView) row
                    .findViewById(R.id.tv_serviceType);
            holder.ll_wholeRow = (LinearLayout) row
                    .findViewById(R.id.ll_wholeRow);
            holder.tvCusName = (TextView) row.findViewById(R.id.tvCusName);
            holder.tvIdNo = (TextView) row.findViewById(R.id.tvIdNo);
            holder.txtidhopdong = (TextView) row
                    .findViewById(R.id.txtidhopdong);
            holder.txttinhtrang = (TextView) row
                    .findViewById(R.id.txttinhtrang);
            holder.txttrangthai = (TextView) row
                    .findViewById(R.id.txttrangthai);
            holder.txtgoicuoc = (TextView) row.findViewById(R.id.txtgoicuoc);
            holder.txtctkmht = (TextView) row.findViewById(R.id.txtctkmht);
            holder.txtcdthientai = (TextView) row
                    .findViewById(R.id.txtcdthientai);
            holder.txtcongnghe = (TextView) row.findViewById(R.id.txtcongnghe);
            holder.txthoten = (TextView) row.findViewById(R.id.txthoten);
            holder.txtBirthDay = (TextView) row.findViewById(R.id.txtBirthDay);
            holder.txtsogiayto = (TextView) row.findViewById(R.id.txtsogiayto);
            holder.txtnoicap = (TextView) row.findViewById(R.id.txtnoicap);
            holder.txtaddress = (TextView) row.findViewById(R.id.txtaddress);
            holder.btninfo = (Button) row.findViewById(R.id.btninfo);
            holder.btnInfoProduct = (Button) row.findViewById(R.id.btnInfoProduct);
            holder.btninfopreapaid = (Button) row
                    .findViewById(R.id.btninfopreapaid);
            holder.lnttinkh = (LinearLayout) row.findViewById(R.id.lnttinkh);
            holder.lnaccountsame = (LinearLayout) row
                    .findViewById(R.id.lnaccountsame);
            holder.btnplusminus = (Button) row.findViewById(R.id.btnplusminus);
            holder.lnsubdetail = (LinearLayout) row.findViewById(R.id.lnsubdetail);
            holder.txtketcuoi = (TextView) row.findViewById(R.id.txtketcuoi);
            holder.tvViewAgeSub = (TextView) row.findViewById(R.id.tvViewAgeSub);
            row.setTag(holder);
        } else {
            holder = (ViewHoder) row.getTag();
        }

        final SubscriberDTO item = subscriberDTOs.get(position);

        if (subscriberDTOs.size() > 1) {
            holder.lnsubdetail.setVisibility(View.GONE);
            holder.btnplusminus.setVisibility(View.VISIBLE);

            holder.btnplusminus.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (holder.lnsubdetail.isShown()) {
                        holder.lnsubdetail.setVisibility(View.GONE);
                        holder.btnplusminus.setBackgroundResource(R.drawable.plus);
                    } else {
                        holder.btnplusminus.setBackgroundResource(R.drawable.minus);
                        holder.lnsubdetail.setVisibility(View.VISIBLE);
                    }
                }
            });

        } else {
            holder.lnsubdetail.setVisibility(View.VISIBLE);
            holder.btnplusminus.setVisibility(View.GONE);
        }


        if (position == 0) {
            holder.lnttinkh.setVisibility(View.VISIBLE);
        } else {
            holder.lnttinkh.setVisibility(View.GONE);
        }


        if (item != null) {
            holder.tvViewAgeSub.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetSubscriberAgeAsyncTask getSubscriberAgeAsyncTask = new GetSubscriberAgeAsyncTask(context, new OnPostExecuteListener<AgeSubscriberDTO>() {
                        @Override
                        public void onPostExecute(AgeSubscriberDTO result, String errorCode, String description) {
                            if (!CommonActivity.isNullOrEmpty(result)) {
                                AgeSubscriberDialog ageSubscriberDialog = new AgeSubscriberDialog();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("ageSubscriberDTO", (Serializable) result);
                                ageSubscriberDialog.setArguments(bundle);
                                ReplaceFragment.replaceFragment(context, ageSubscriberDialog, false);
                            } else {
                                CommonActivity.showConfirmValidate(context, R.string.not_data_total_charge);
                            }
                        }
                    }, moveLogInAct);
                    getSubscriberAgeAsyncTask.execute(item.getSubId());
                }
            });
            holder.tv_isdnAccount.setText(item.getAccount());
            holder.tv_serviceType.setText(context.getString(R.string.dichvu)
                    + ": " + item.getServiceTypeName());
            holder.txttrangthai.setText(item.getStatusView());
            holder.txttinhtrang.setText(item.getActStatusText());
            holder.txtgoicuoc.setText(item.getProductName());
            holder.txtidhopdong.setText(item.getAccountNo());

            holder.txtctkmht.setText(item.getPromotionCode());
            if (!CommonActivity.isNullOrEmptyArray(item
                    .getLstSubPromotionPrepaidDTO())) {
                holder.txtcdthientai
                        .setText(item.getLstSubPromotionPrepaidDTO().get(0)
                                .getPrepaidCode());
            }
            if (!CommonActivity.isNullOrEmpty(item)
                    && !CommonActivity.isNullOrEmptyArray(item
                    .getLstSubPromotionDTO())) {
                holder.btninfo.setVisibility(View.VISIBLE);
            } else {
                holder.btninfo.setVisibility(View.GONE);
            }
            holder.btninfo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (!CommonActivity.isNullOrEmpty(item)
                            && !CommonActivity.isNullOrEmptyArray(item
                            .getLstSubPromotionDTO())) {
                        showDialogContractPromotionDetail(item);
                    }

                }
            });

            if (!CommonActivity.isNullOrEmpty(item)
                    && !CommonActivity.isNullOrEmpty(item
                    .getOfferId())) {
                holder.btnInfoProduct.setVisibility(View.VISIBLE);
            }
            holder.btnInfoProduct.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    final GetInfoPackageAsyn getInfoPackageAsyn = new GetInfoPackageAsyn((Activity) context, new OnPostExecuteListener<ProductOfferCharacterClone>() {

                        @Override
                        public void onPostExecute(ProductOfferCharacterClone result, String errorCode, String description) {
                            DialogDetailAtribute.showDialogPackageDetail((Activity) context, result);
                        }
                    }, null);

                    getInfoPackageAsyn.execute(item.getOfferId());
                }
            });


            if (!CommonActivity.isNullOrEmpty(item)
                    && !CommonActivity.isNullOrEmptyArray(item
                    .getLstSubPromotionPrepaidDTO())) {
                if (!CommonActivity.isNullOrEmpty(item
                        .getLstSubPromotionPrepaidDTO().get(0).getEffectDate())) {
                    holder.btninfopreapaid.setVisibility(View.VISIBLE);
                } else {
                    holder.btninfopreapaid.setVisibility(View.GONE);
                }

            } else {
                holder.btninfopreapaid.setVisibility(View.GONE);
            }

            holder.btninfopreapaid
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            if (!CommonActivity.isNullOrEmpty(item)
                                    && !CommonActivity.isNullOrEmptyArray(item
                                    .getLstSubPromotionPrepaidDTO())) {
                                showDialogContractPrepaidDetail(item);
                            }
                        }
                    });

            if (!CommonActivity.isNullOrEmpty(item)
                    && !CommonActivity.isNullOrEmptyArray(item
                    .getLstSubInfrastructureDTO())) {
            }
            if (!CommonActivity.isNullOrEmpty(item) && !CommonActivity.isNullOrEmptyArray(item.getLstSubInfrastructureDTO())) {
                holder.txtaddress.setText(item.getLstSubInfrastructureDTO().get(0).getAddress());
                holder.txtketcuoi.setText(item.getLstSubInfrastructureDTO().get(0).getCableBoxCode());
            }

            holder.txthoten.setText(item.getCustomerName());
            holder.txtsogiayto.setText(item.getIdNo());
            holder.txtBirthDay.setText(item.getBirthDate());
            holder.txtcongnghe.setText(item.getTechnologyText());
            holder.ll_wholeRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (onSelectSubscriber != null) {
                        onSelectSubscriber.onSelect(item, position);
                    }
                }
            });

            holder.lnaccountsame.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (onViewSubscriberChild != null) {
                        onViewSubscriberChild.OnViewSubscriberDetail(item);
                    }
                }
            });

        }

        return row;
    }

    private void showDialogContractPromotionDetail(SubscriberDTO subscriberDTO) {
        //
        final Dialog dialogContractDetail;
        dialogContractDetail = new Dialog(context);
        dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContractDetail
                .setContentView(R.layout.dialog_layout_changepromotion);
        TextView tvname = (TextView) dialogContractDetail
                .findViewById(R.id.tvname);
        tvname.setText(context.getString(R.string.promotion_name));
        TextView tvPromotionName = (TextView) dialogContractDetail
                .findViewById(R.id.tvPromotionName);
        TextView tvDialogTitle = (TextView) dialogContractDetail
                .findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(context.getString(R.string.detailkm));
        tvPromotionName.setText(subscriberDTO.getPromotionCode());
        TextView tvStartDate = (TextView) dialogContractDetail
                .findViewById(R.id.tvStartDate);
        TextView tvEndDate = (TextView) dialogContractDetail
                .findViewById(R.id.tvEndDate);
        if (!CommonActivity.isNullOrEmpty(subscriberDTO)
                && !CommonActivity.isNullOrEmptyArray(subscriberDTO
                .getLstSubPromotionDTO())) {
            tvStartDate.setText(StringUtils.convertDate(subscriberDTO
                    .getLstSubPromotionDTO().get(0).getStaDatetime()));
            tvEndDate.setText(StringUtils.convertDate(subscriberDTO
                    .getLstSubPromotionDTO().get(0).getExpireDatetime()));
        }
        Button btn_cancel = (Button) dialogContractDetail
                .findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogContractDetail.cancel();

            }
        });
        dialogContractDetail.show();

    }

    private void showDialogContractPrepaidDetail(SubscriberDTO subscriberDTO) {
        //
        final Dialog dialogContractDetail;
        dialogContractDetail = new Dialog(context);
        dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogContractDetail
                .setContentView(R.layout.dialog_layout_changepromotion);
        TextView tvname = (TextView) dialogContractDetail
                .findViewById(R.id.tvname);
        tvname.setText(context.getString(R.string.namecdt));
        TextView tvPromotionName = (TextView) dialogContractDetail
                .findViewById(R.id.tvPromotionName);
        TextView tvDialogTitle = (TextView) dialogContractDetail
                .findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(context.getString(R.string.detailcdc));

        TextView tvStartDate = (TextView) dialogContractDetail
                .findViewById(R.id.tvStartDate);
        TextView tvEndDate = (TextView) dialogContractDetail
                .findViewById(R.id.tvEndDate);
        if (!CommonActivity.isNullOrEmpty(subscriberDTO)
                && !CommonActivity.isNullOrEmptyArray(subscriberDTO
                .getLstSubPromotionPrepaidDTO())) {
            tvStartDate.setText(StringUtils.convertDate(subscriberDTO
                    .getLstSubPromotionPrepaidDTO().get(0).getEffectDate()));
            tvEndDate.setText(StringUtils.convertDate(subscriberDTO
                    .getLstSubPromotionPrepaidDTO().get(0).getEndDate()));
            tvPromotionName.setText(subscriberDTO
                    .getLstSubPromotionPrepaidDTO().get(0).getPrepaidCode());
        }
        Button btn_cancel = (Button) dialogContractDetail
                .findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogContractDetail.cancel();

            }
        });
        dialogContractDetail.show();

    }

    public interface OnSelectSubscriber {
        void onSelect(SubscriberDTO subscriberDTO, int position);
    }

    public interface OnViewSubscriberChild {
        public void OnViewSubscriberDetail(SubscriberDTO subscriberDTO);
    }

    static class ViewHoder {
        TextView tv_isdnAccount;
        TextView tv_serviceType, txtidhopdong, txttinhtrang, txttrangthai,
                txtgoicuoc, txtcongnghe, txthoten, txtBirthDay, txtsogiayto,
                txtnoicap, txtaddress, txtctkmht, txtcdthientai;
        Button btninfo, btninfopreapaid, btnplusminus, btnInfoProduct;
        LinearLayout ll_wholeRow, lnttinkh, lnaccountsame, lnsubdetail;
        TextView tvCusName;
        TextView tvIdNo;
        TextView txtketcuoi;
        TextView tvViewAgeSub;
    }
}
