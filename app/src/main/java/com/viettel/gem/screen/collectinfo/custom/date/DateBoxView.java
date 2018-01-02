package com.viettel.gem.screen.collectinfo.custom.date;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;

import butterknife.BindView;


/**
 * Created by BaVV on 5/31/16.
 */
public class DateBoxView
        extends CustomView
        implements DateItemView.Callback {

    @BindView(R.id.boxDate)
    LinearLayout boxDate;

    @BindView(R.id.tvName)
    TextView tvName;

    private DateItemView mSelectedView;

    ProductSpecCharDTO productSpecCharDTO;

    public DateBoxView(Context context) {
        super(context);
    }

    public DateBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_date_box_view;
    }

    @Override
    public boolean validateView() {

        if (null != productSpecCharDTO && "CUST_EXP_CDT".equals(productSpecCharDTO.getCode()) && CollectCustomerInfoPresenter.selectedOtherCustCDT) {
            return true;
        }

        return mSelectedView.validateView();
    }

    public void build(ProductSpecCharDTO productSpecCharDTO) {
        this.productSpecCharDTO = productSpecCharDTO;
        if (null == this.productSpecCharDTO) return;

        tvName.setText(this.productSpecCharDTO.getName());

        boxDate.removeAllViews();

        mSelectedView = new DateItemView(getContext());
        mSelectedView.build(productSpecCharDTO.getCode(), productSpecCharDTO.getValueType(), productSpecCharDTO.getValueData(), this);
        boxDate.addView(mSelectedView);
    }

    @Override
    public void onDateChanged(String text) {
        if (null != productSpecCharDTO) productSpecCharDTO.setValueData(text);
    }
}
