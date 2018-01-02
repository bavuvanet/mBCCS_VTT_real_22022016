package com.viettel.bss.viettelpos.v4.customer.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.customer.object.DeviceWarrantyBO;

import java.util.ArrayList;

public class DeviceWarrantyAdapter extends BaseAdapter {
    private ArrayList<DeviceWarrantyBO> lstData = new ArrayList<>();
    private final Context mContext;
    private final OnSelectWarrantyDevice onSelectWarrantyDevice;
    private int warrantyType = Constant.WARRANTY_TYPE.WARRANTY_RECEIVE_OR_REPAIR;
    private int userReceive = 2;

    public interface OnSelectWarrantyDevice {
        void onSelectWarrantyDevice(DeviceWarrantyBO item);

        void onSelectWarrantyDeviceReturn(DeviceWarrantyBO item);

        void onCancelWarrantyDevice(DeviceWarrantyBO item);
    }

    public DeviceWarrantyAdapter(Context mContext, ArrayList<DeviceWarrantyBO> lstData, OnSelectWarrantyDevice onSelectWarrantyDevice, int warrantyType, int userReceive) {
        this.mContext = mContext;
        this.lstData = lstData;
        this.onSelectWarrantyDevice = onSelectWarrantyDevice;
        this.warrantyType = warrantyType;
        this.userReceive = userReceive;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (CommonActivity.isNullOrEmpty(lstData)) {
            return 0;
        }
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (CommonActivity.isNullOrEmpty(lstData)) {
            return null;
        }
        return lstData.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View view = null;
        final ViewHolder holder;
        final DeviceWarrantyBO item;
        String TAG = "DeviceWarrantyAdapter";
        android.util.Log.d(TAG, "getView with positon = " + position);
        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.item_device_warranty_info, arg2,
                    false);
            holder = new ViewHolder();
            holder.tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            holder.tvCustomerName = (TextView) view.findViewById(R.id.tvCustomerName);
            holder.tvDeviceName = (TextView) view.findViewById(R.id.tvDeviceName);
            holder.tvImei = (TextView) view.findViewById(R.id.tvImei);
            holder.tvIsdn = (TextView) view.findViewById(R.id.tvIsdn);
            holder.tvReceiveDate = (TextView) view.findViewById(R.id.tvReceiveDate);
//			holder.tvSerial = (TextView) view.findViewById(R.id.tvSerial);
            holder.tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            holder.tvWarrantyDate = (TextView) view.findViewById(R.id.tvWarrantyDate);
            holder.cbSelect = (CheckBox) view.findViewById(R.id.cbSelect);
            holder.rbSelect = (RadioButton) view.findViewById(R.id.rbSelect);
            holder.tvReceiveNo = (TextView) view.findViewById(R.id.tvReceiveNo);
            holder.tvError = (TextView) view.findViewById(R.id.tvError);
            holder.tvChannelCode = (TextView) view.findViewById(R.id.tvChannelCode);
            holder.imgDelete = view.findViewById(R.id.imgDelete);
            item = lstData.get(position);
            holder.cbSelect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    android.util.Log.d("holder.cbSelect.setOnClickListener", "isChecked = " + holder.cbSelect.isChecked());
                    item.setSelect(holder.cbSelect.isChecked());

                    onSelectWarrantyDevice.onSelectWarrantyDeviceReturn(item);
                }
            });

            holder.rbSelect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    item.setSelect(true);
                    rbSelectResetData(item);

                    onSelectWarrantyDevice.onSelectWarrantyDevice(item);
                }
            });
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectWarrantyDevice.onCancelWarrantyDevice(item);
                }
            });
            holder.position = position;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            item = lstData.get(holder.position);
        }


        if (Constant.WARRANTY_TYPE.WARRANTY_RECEIVE_OR_REPAIR == warrantyType) {
            //truong hop tiep nhan bao hanh hoac sua chua
            holder.tvCustomerName.setVisibility(View.GONE);
            holder.tvIsdn.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
            holder.tvReceiveDate.setVisibility(View.GONE);
            holder.cbSelect.setVisibility(View.GONE);
            holder.tvReceiveNo.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
            holder.tvChannelCode.setVisibility(View.GONE);
            if (Constant.WARRANTY_SATUS.WARRANTY.equals(item.getReceiceBHVT())
                    || Constant.WARRANTY_SATUS.REPAIR.equals(item.getReceiceSCDV())) {
                holder.rbSelect.setVisibility(View.VISIBLE);
            } else {
                holder.rbSelect.setVisibility(View.GONE);
            }

            if (item.isSelect()) {
                holder.rbSelect.setChecked(true);
            } else {
                holder.rbSelect.setChecked(false);
            }

            holder.tvImei.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_imei_serial) + ": " + "<font color=\"#2196F3\">" + item.getImei() + "</font>"));
            holder.tvDeviceName.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_device_name) + ": " + "<font color=\"#2196F3\">" + item.getStockModelName() + "</font>"));
//			holder.tvSerial.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_serial) + ": " + "<font color=\"#2196F3\">" + item.getImei() + "</font>"));
            if (!CommonActivity.isNullOrEmpty(item.getWarrantyExpriedDateStr())
                    && !CommonActivity.isNullOrEmpty(item.getWarrantyRegistedDateStr())) {
                holder.tvWarrantyDate.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_warranty_date) + ": " + "<font color=\"#2196F3\">" + item.getWarrantyRegistedDateStr() + "-" + item.getWarrantyExpriedDateStr() + "</font>"));
            } else {
                holder.tvWarrantyDate.setText("");
            }
            holder.tvStatus.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_warranty_status) + ": " + "<font color=\"#2196F3\">" + item.getDescription() + "</font>"));
        } else {
            if (userReceive != 1) {
                holder.imgDelete.setVisibility(View.GONE);
            } else {
                String actionType = "";
                if(!CommonActivity.isNullOrEmpty(item.getActionType())){
                    actionType = item.getActionType();
                }
                if("0".equals(actionType)){
                    holder.imgDelete.setVisibility(View.VISIBLE);
                }else{
                    holder.imgDelete.setVisibility(View.GONE);
                }

            }
            holder.tvWarrantyDate.setVisibility(View.GONE);
            holder.rbSelect.setVisibility(View.GONE);
            if (Constant.WARRANTY_TYPE.WARRANTY_RETURN == warrantyType) {
                holder.cbSelect.setVisibility(View.VISIBLE);
            } else {
                holder.cbSelect.setVisibility(View.GONE);
            }
            holder.tvChannelCode.setVisibility(View.VISIBLE);
            holder.tvImei.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_imei_serial) + ": " + "<font color=\"#2196F3\">" + item.getImei() + "</font>"));
//			holder.tvSerial.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_serial) + ": " + "<font color=\"#2196F3\">" + item.getImei() + "</font>"));
            holder.tvDeviceName.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_device_name) + ": " + "<font color=\"#2196F3\">" + item.getStockModelName() + "</font>"));
            holder.tvCustomerName.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_customer) + ": " + "<font color=\"#2196F3\">" + item.getcName() + "</font>"));
            holder.tvIsdn.setText(Html.fromHtml(mContext.getResources().getString(R.string.text_phone_number) + ": " + "<font color=\"#2196F3\">" + item.getcIsdn() + "</font>"));
            holder.tvAddress.setText(Html.fromHtml(mContext.getResources().getString(R.string.text_dia_chi) + ": " + "<font color=\"#2196F3\">" + item.getcAddress() + "</font>"));
            holder.tvReceiveDate.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_date_receive) + ": " + "<font color=\"#2196F3\">" + item.getCreateDatemBccsStr() + "</font>"));
            holder.tvStatus.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_warranty_status) + ": " + "<font color=\"#2196F3\">" + item.getActionTypeName() + "</font>"));
            holder.tvReceiveNo.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_receive_no_warranty) + ": " + "<font color=\"#2196F3\">" + item.getReceiptNomBccs() + "</font>"));
            holder.tvError.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_warranty_error_description) + ": " + "<font color=\"#2196F3\">" + (CommonActivity.isNullOrEmpty(item.getErrorDesc()) ? "" : item.getErrorDesc()) + "</font>"));
            String channelCode = "";
            if (!CommonActivity.isNullOrEmpty(item.getCodeAgency())) {
                channelCode = item.getCodeAgency();
            }
            holder.tvChannelCode.setText(Html.fromHtml(mContext.getString(R.string.channel_receive_warranty) +": " +"<font color=\"#2196F3\">" + channelCode + "</font>"));
            if (item.isSelect()) {
                holder.cbSelect.setChecked(true);
            } else {
                holder.cbSelect.setChecked(false);
            }
        }
        return view;
    }

    public ArrayList<DeviceWarrantyBO> getLstData() {
        return lstData;
    }


    public void setLstData(ArrayList<DeviceWarrantyBO> lstData) {
        this.lstData = lstData;
    }

    private void rbSelectResetData(DeviceWarrantyBO item) {
        for (DeviceWarrantyBO deviceWarrantyBO : lstData) {
            if (deviceWarrantyBO.getImei().equals(item.getImei())
                    && deviceWarrantyBO.getStockModelCode().equals(item.getStockModelCode())) {
            } else {
                deviceWarrantyBO.setSelect(false);
            }
        }
    }


    class ViewHolder {
        TextView tvImei;
        //		TextView tvSerial;
        TextView tvDeviceName;
        TextView tvCustomerName;
        TextView tvIsdn;
        TextView tvAddress;
        TextView tvReceiveDate;
        TextView tvStatus;
        TextView tvWarrantyDate;
        TextView tvReceiveNo;
        TextView tvError;
        View imgDelete;
        CheckBox cbSelect;
        RadioButton rbSelect;
        TextView tvChannelCode;
        int position;
    }

}
