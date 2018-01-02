package com.viettel.bss.viettelpos.v4.staff.warning;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.BaseAdapter;
import com.viettel.bss.viettelpos.v4.adapter.BaseViewHolder;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NHAT on 30/05/2017.
 */

public class AlarmAdapter extends BaseAdapter<AlarmObj.Data> {

    public AlarmAdapter(@LayoutRes int resID) {
        super(resID);
    }

    @Override
    public BaseViewHolder<AlarmObj.Data> getViewHolder(View view) {
        return new ItemHolder(view);
    }

    class ItemHolder extends BaseViewHolder<AlarmObj.Data> {
        @BindView(R.id.tv_code)
        protected TextView mTVCode;
        @BindView(R.id.tv_name)
        protected TextView mTVName;
        @BindView(R.id.tv_time_start)
        protected TextView mTVTimeStart;
        @BindView(R.id.tv_time_accrued)
        protected TextView mTVTimeAccrued;
        @BindView(R.id.tv_time_nok)
        protected TextView mTVTimeNOk;
        @BindView(R.id.tv_content)
        protected TextView mTVContent;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemHolderClick(ItemHolder.this);
                }
            });
        }

        @Override
        public void setData(AlarmObj.Data obj) {
            if (obj != null) {
                updateToView(mTVCode, R.string.alarm_code, obj.getKpiCode());
                updateToView(mTVTimeStart, R.string.alarm_time_start, obj.getStartTime());
                updateToView(mTVTimeAccrued, R.string.alarm_time_accrued, obj.getAccruedTime() + "");
                updateToView(mTVTimeNOk, R.string.alarm_time_nok, obj.getCountDayNok() + "");
                updateToView(mTVContent, R.string.alarm_content, obj.getAlarmContent());
                setName(obj.getAlarmName());
            }
        }

        private void updateToView(TextView tv, @StringRes int strID, String data) {
            String c = tv.getContext().getString(strID, data != null ? data : "");
            tv.setText(c);
        }

        private void setName(String name) {
            mTVName.setText(name != null ? name : "");
        }
    }
}