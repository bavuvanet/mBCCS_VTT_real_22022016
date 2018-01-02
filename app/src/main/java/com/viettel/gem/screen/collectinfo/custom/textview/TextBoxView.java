package com.viettel.gem.screen.collectinfo.custom.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by BaVV on 11/21/17.
 */
public class TextBoxView
        extends CustomView
        implements TextItemView.Callback {

    @BindView(R.id.boxInput)
    LinearLayout boxInput;

    @BindView(R.id.tvName)
    TextView tvName;

    List<TextItemView> textItemViewList = new ArrayList<>();

    ProductSpecCharDTO productSpecCharDTO;

    public TextBoxView(Context context) {
        super(context);
    }

    public TextBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_text_box_view;
    }

    @Override
    public boolean validateView() {

        if (null != productSpecCharDTO && "NUMBER_FAMILY".equals(productSpecCharDTO.getCode())) {
            return (CollectCustomerInfoPresenter.mTotalJob == CollectCustomerInfoPresenter.mTotalMember)
                    && (CollectCustomerInfoPresenter.mTotalJob != 0 && CollectCustomerInfoPresenter.mTotalMember != 0);
        }

        boolean flag = false;
        for (TextItemView textItemView : textItemViewList) {
            if (textItemView.validateView()) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public void build(ProductSpecCharDTO productSpecCharDTO) {
        this.productSpecCharDTO = productSpecCharDTO;
        if (null == this.productSpecCharDTO) return;

        tvName.setText(this.productSpecCharDTO.getName());

        boxInput.removeAllViews();

        List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();

        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                TextItemView itemView = new TextItemView(getContext());

                itemView.build(this, specCharValueDTOList, this);
                boxInput.addView(itemView);
                textItemViewList.add(itemView);
            }
        }
    }

    public void calCount() {
        int totalCount = 0;
        if (null == productSpecCharDTO || !"NUMBER_FAMILY".equals(productSpecCharDTO.getCode()))
            return;
        List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();

        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                int count = 0;
                try {
                    count = null == specCharValueDTOList.getValueData() ? 0 : Integer.parseInt(specCharValueDTOList.getValueData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                totalCount += count;
            }
        }
        CollectCustomerInfoPresenter.mTotalMember = totalCount;
    }
}
