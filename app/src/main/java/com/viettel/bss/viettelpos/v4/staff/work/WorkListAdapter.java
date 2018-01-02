package com.viettel.bss.viettelpos.v4.staff.work;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.BaseAdapter;
import com.viettel.bss.viettelpos.v4.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NHAT on 30/05/2017.
 */

public class WorkListAdapter extends BaseAdapter<WorkListObj.Data> {

    public WorkListAdapter(@LayoutRes int resID) {
        super(resID);
    }

    @Override
    public BaseViewHolder<WorkListObj.Data> getViewHolder(View view) {
        return new ItemHolder(view);
    }

    class ItemHolder extends BaseViewHolder<WorkListObj.Data> {
        @BindView(R.id.tv_assign)
        protected TextView mTVAssign;
        @BindView(R.id.tv_name)
        protected TextView mTVName;
        @BindView(R.id.tv_time_from_date)
        protected TextView mTVFromDate;
        @BindView(R.id.tv_time_to_date)
        protected TextView mTVToDate;
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
        public void setData(WorkListObj.Data obj) {
            if (obj != null) {
                updateToView(mTVAssign, R.string.staff_work_assign, obj.getWorkAssign());
                updateToView(mTVFromDate, R.string.staff_work_from_date, obj.getStartTime());
                updateToView(mTVToDate, R.string.staff_work_to_date, obj.getEndTime() + "");
                updateToView(mTVContent, R.string.staff_work_content, obj.getWorkName());
                setName(obj.getJobCode());
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