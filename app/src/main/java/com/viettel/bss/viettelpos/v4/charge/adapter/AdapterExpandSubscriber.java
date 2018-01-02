package com.viettel.bss.viettelpos.v4.charge.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynGetSubGoodDTO;
import com.viettel.bss.viettelpos.v4.charge.object.AccountObj;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.util.ArrayList;
import java.util.List;

public class AdapterExpandSubscriber extends BaseExpandableListAdapter {
    private final LayoutInflater inflater;
    private final Activity activity;
    private SubscriberDTO subCheck;
    private AccountObj accountObj;

    public AdapterExpandSubscriber(Activity activity,
                                   AccountObj accountObj) {
        this.accountObj = accountObj;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SubscriberDTO getChild(int groupPosition, int childPosition) {
        return this.accountObj.getSubscriberDTOList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final SubscriberDTO subscriberDTO = getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_subscriber_info, null);
        }
        TextView txtIsdn = (TextView) convertView.findViewById(R.id.txtSub);
        TextView txtService = (TextView) convertView.findViewById(R.id.txtService);
        TextView txtStatusLiquidation = (TextView) convertView.findViewById(R.id.txtLiquidation);
        final CheckBox checkSub = (CheckBox) convertView.findViewById(R.id.cbSubscriber);
//        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
        checkSub.setChecked(subscriberDTO.getChecker());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCheck(childPosition,!checkSub.isChecked());
            }
        });
        checkSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCheck(childPosition,checkSub.isChecked());
            }
        });
        txtIsdn.setText(subscriberDTO.getIsdn());
        txtService.setText(subscriberDTO.getTelecomServiceName());
        txtStatusLiquidation.setText(subscriberDTO.getActStatusView());
//        txtStatus.setText(subscriberDTO.getStatusView());


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.accountObj.getSubscriberDTOList().size();
    }

    @Override
    public AccountObj getGroup(int groupPosition) {
        return this.accountObj;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

//    @SuppressLint("InflateParams")
//    @SuppressWarnings("unused")
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        // set group header
        AccountObj accountObj = getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_contract_detail, null);
        }
        TextView txtContractNo = (TextView) convertView.findViewById(R.id.txtContractNo);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        TextView notifi_choose = (TextView) convertView.findViewById(R.id.notifi_choose);
//        CheckBox checkAll = (CheckBox) convertView.findViewById(R.id.imContract);
        notifi_choose.setPaintFlags(notifi_choose.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtContractNo.setText(accountObj.getAccountNo());
        txtDate.setText(StringUtils.convertDate(accountObj.getSignDate()));
//        checkAll.setChecked(accountObj.getChecker());
//        checkAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                accountObj.setChecker(false);
//                List<AccountObj> accountObjs = new ArrayList<AccountObj>();
//                List<SubscriberDTO> subscriberDTOs = new ArrayList<>();
//
//                for (SubscriberDTO subscriberDTO : accountObj.getSubscriberDTOList()) {
//                    subscriberDTO.setChecker(accountObj.getChecker());
//                    subscriberDTOs.add(subscriberDTO);
//                }
//                accountObj.setSubscriberDTOList(subscriberDTOs);
//                accountObjs.add(accountObj);
//                setData(accountObjs);
//                notifyDataSetChanged();
//            }
//        });
        return convertView;
    }

    private void subCheck(int childPosition,boolean checkTarget){
        List<SubscriberDTO> tmp=new ArrayList<>();
        List<SubscriberDTO> subs=accountObj.getSubscriberDTOList();
        SubscriberDTO dto=subs.get(childPosition);
        dto.setChecker(checkTarget);
        tmp.set(childPosition,dto);
        accountObj.setSubscriberDTOList(tmp);
        notifyDataSetChanged();
    }
    private void setData( AccountObj accountObj){
        this.accountObj=accountObj;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<SubscriberDTO> getSubCheck() {
        List<SubscriberDTO> result=new ArrayList<>();
        if(!CommonActivity.isNullOrEmpty(subCheck)) result.add(subCheck);
        return result;
    }
}
