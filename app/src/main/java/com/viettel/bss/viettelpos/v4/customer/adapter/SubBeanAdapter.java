package com.viettel.bss.viettelpos.v4.customer.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.SubBeanBO;

import java.util.ArrayList;

public class SubBeanAdapter extends BaseAdapter {
    private final ArrayList<SubBeanBO> lstSubBeanBOs;
    private final Context mContext;
    public SubBeanAdapter(Context mContext,
                          ArrayList<SubBeanBO> lstSubBeanBOs) {
        this.mContext = mContext;
        this.lstSubBeanBOs = lstSubBeanBOs;
    }

    @Override
    public int getCount() {
        if (CommonActivity.isNullOrEmpty(lstSubBeanBOs)) {
            return 0;
        }
        return lstSubBeanBOs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        if (CommonActivity.isNullOrEmpty(lstSubBeanBOs)) {
            return null;
        }
        return lstSubBeanBOs.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        SubBeanBO item = lstSubBeanBOs.get(arg0);
        View mView = arg1;
        ViewHolder holder = null;
        if (mView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mView = inflater.inflate(R.layout.item_debit_detail, arg2,
                    false);
            holder = new ViewHolder();
            holder.tvAccount = (TextView) mView.findViewById(R.id.tvAccount);
            holder.tvService = (TextView) mView.findViewById(R.id.tvService);
            holder.tvCuocNong = (TextView) mView.findViewById(R.id.tvCuocNong);
            holder.tvNoTruoc = (TextView) mView.findViewById(R.id.tvNoTruoc);
            holder.tvNoPhatSinh = (TextView) mView.findViewById(R.id.tvNoPhatSinh);
            holder.tvDaTT = (TextView) mView.findViewById(R.id.tvDaTT);
            holder.tvPhaiTT = (TextView) mView.findViewById(R.id.tvPhaiTT);
            holder.tvKHTT = (TextView) mView.findViewById(R.id.tvKHTT);
            holder.tvDeployArea = (TextView) mView.findViewById(R.id.tvDeployArea);

            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }

        holder.tvAccount.setText(mContext.getResources().getString(R.string.soTB, item.getIsdn()));
        holder.tvService.setText(item.getTelServiceName());

        holder.tvCuocNong.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_cuoc_nong) + ": " + "<font color=\"#EE0000\">" + StringUtils.formatMoney(item.getTotCharge().toString()) + "</font>"));
        holder.tvNoTruoc.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_no_truoc) + ": " + "<font color=\"#EE0000\">" + StringUtils.formatMoney(item.getPriorDebit().toString()) + "</font>"));
        holder.tvNoPhatSinh.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_no_phat_sinh) + ": " + "<font color=\"#EE0000\">" + StringUtils.formatMoney(item.getAmountDebit().toString()) + "</font>"));
        holder.tvDaTT.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_da_thanh_toan) + ": " + "<font color=\"#EE0000\">" + StringUtils.formatMoney(item.getPayment().toString()) + "</font>"));
        holder.tvPhaiTT.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_phai_thanh_toan) + ": " + "<font color=\"#EE0000\">" + StringUtils.formatMoney(item.getStaOfCycle().toString()) + "</font>"));
        holder.tvKHTT.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_kh_thanh_toan) + ": " + "<font color=\"#EE0000\">" + StringUtils.formatMoney(item.getPayNew().toString()) + "</font>"));
        if (!CommonActivity.isNullOrEmpty(item.getDeployArea())) {
            holder.tvDeployArea.setVisibility(View.VISIBLE);
            holder.tvDeployArea.setText(mContext.getString(R.string.deploy_area,item.getDeployArea()));
        }else{
            holder.tvDeployArea.setVisibility(View.GONE);
        }
        return mView;
    }

    class ViewHolder {
        TextView tvAccount;
        TextView tvService;
        TextView tvCuocNong;
        TextView tvNoTruoc;
        TextView tvNoPhatSinh;
        TextView tvDaTT;
        TextView tvPhaiTT;
        TextView tvKHTT;
        TextView tvDeployArea;
    }

}