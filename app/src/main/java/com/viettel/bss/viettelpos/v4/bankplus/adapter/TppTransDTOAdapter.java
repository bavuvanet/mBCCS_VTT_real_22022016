package com.viettel.bss.viettelpos.v4.bankplus.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.TppTransDTO;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/10/2017.
 */

public class TppTransDTOAdapter extends ArrayAdapter<TppTransDTO> {

    private ArrayList<TppTransDTO> tppTransDTOs;
    Context context;
    Activity activity;

    // View lookup cache
    private static class ViewHolder {
        TextView tvIsdn;
        TextView tvDate;
        TextView tvTransCode;
        TextView tvAppCode;
        TextView tvTransId;
        TextView tvTransType;
        TextView tvPrice;
        TextView tvSmsNumber;
        ImageButton resendButton;
        RelativeLayout layoutView;
    }

    public TppTransDTOAdapter(ArrayList<TppTransDTO> tppTransDTOs, Context context, Activity activity) {
        super(context, R.layout.bankplus_pin_code_trans_item_view, tppTransDTOs);
        this.tppTransDTOs = tppTransDTOs;
        this.context = context;
        this.activity = activity;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TppTransDTO tppTransDTO = getItem(position);
        TppTransDTOAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new TppTransDTOAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.bankplus_pin_code_trans_item_view, parent, false);
            viewHolder.tvIsdn = (TextView) convertView.findViewById(R.id.tvIsdn);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvTransCode = (TextView) convertView.findViewById(R.id.tvTransCode);
            viewHolder.tvAppCode = (TextView) convertView.findViewById(R.id.tvAppCode);
            viewHolder.tvTransId = (TextView) convertView.findViewById(R.id.tvTransId);
            viewHolder.tvTransType = (TextView) convertView.findViewById(R.id.tvTransType);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            viewHolder.tvSmsNumber = (TextView) convertView.findViewById(R.id.tvSmsNumber);
            viewHolder.resendButton = (ImageButton) convertView.findViewById(R.id.resendButton);
            viewHolder.layoutView = (RelativeLayout) convertView.findViewById(R.id.layoutView);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TppTransDTOAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tvIsdn.setText("0" + tppTransDTO.getIsdn());
        viewHolder.tvDate.setText("" + tppTransDTO.getStrCreateDate());

        viewHolder.tvTransCode.setText("" + tppTransDTO.getId());
        viewHolder.tvAppCode.setText("" + tppTransDTO.getAppName());
        viewHolder.tvTransId.setText("" + tppTransDTO.getRequestId());
        viewHolder.tvTransType.setText("" + tppTransDTO.getRequestType());
        viewHolder.tvPrice.setText(StringUtils.formatMoney("" + tppTransDTO.getAmount()));
        viewHolder.tvSmsNumber.setText("" + tppTransDTO.getNumSendSms());

        viewHolder.resendButton.setFocusable(true);

        viewHolder.resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSendSms(tppTransDTO.getId(), tppTransDTO.getIsdn());
            }
        });

        if (position % 2 == 0) {
            viewHolder.layoutView.setBackgroundResource(R.color.gray_6);
            viewHolder.resendButton.setBackgroundResource(R.color.gray_6);
        } else {
            viewHolder.layoutView.setBackgroundResource(R.color.gray_8);
            viewHolder.resendButton.setBackgroundResource(R.color.gray_8);
        }

        return convertView;
    }

    private void alertSendSms(final String tppTransId,final String isdn) {
        final View.OnClickListener sendSmsListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doSendSms(tppTransId, isdn);
            }
        };

        CommonActivity.createDialog(activity, "Bạn có đồng ý gửi lại mã thẻ cào của giao dịch "
                        + tppTransId + " tới số thuê bao 0" + isdn + " không?",
                activity.getString(R.string.app_name),
                activity.getString(R.string.cancel),
                activity.getString(R.string.ok),
                null, sendSmsListener).show();
    }

    private void doSendSms(String tppTransId, String isdn) {
        String token = Session.getToken();
        StringBuilder data = new StringBuilder();
        data.append(token).append(Constant.STANDARD_CONNECT_CHAR);

        data.append(BPConstant.COMMAND_SEND_SMS_PIN_CODE_TRANS)
                .append(Constant.STANDARD_CONNECT_CHAR);
        data.append(tppTransId).append(Constant.STANDARD_CONNECT_CHAR);

        new CreateBankPlusAsyncTask(data.toString(), activity,
                new OnPostExecuteListener<BankPlusOutput>() {
                    @Override
                    public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
                        // TODO Auto-generated method stub
                        if ("0".equals(errorCode)) {
                            Toast.makeText(context, "Đã gửi lại mã thẻ cào!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, description, Toast.LENGTH_LONG).show();
                        }
                    }
                }, null).execute();
    }
}