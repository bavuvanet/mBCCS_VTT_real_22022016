package com.viettel.bss.viettelpos.v4.staff.kpi;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.BaseAdapter;
import com.viettel.bss.viettelpos.v4.adapter.BaseViewHolder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NHAT on 30/05/2017.
 */

public class KPIAdapter extends BaseAdapter<KPIObj.Data> {

    public KPIAdapter(@LayoutRes int resID) {
        super(resID);
    }

    @Override
    public BaseViewHolder<KPIObj.Data> getViewHolder(View view) {
        return new ItemHolder(view);
    }

    class ItemHolder extends BaseViewHolder<KPIObj.Data> {
        @BindView(R.id.tv_code)
        protected TextView mTVCode;
        @BindView(R.id.tv_name)
        protected TextView mTVName;
        @BindView(R.id.tv_total)
        protected TextView mTVTotal;

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
        public void setData(KPIObj.Data obj) {
            if (obj != null) {
                setCode(obj.getKpiCode());
                setName(obj.getKpiName());
                setTotal(obj.getKpiTarget());
            }
        }

        private void setCode(String code) {
            mTVCode.setText(code != null ? code : "");
        }

        private void setName(String name) {
            mTVName.setText(name != null ? name : "");
        }

        private void setTotal(String total) {
            String size = mTVTotal.getContext().getString(R.string.kpi_total_title, total != null ? total : "");
            mTVTotal.setText(size);
        }
    }
}