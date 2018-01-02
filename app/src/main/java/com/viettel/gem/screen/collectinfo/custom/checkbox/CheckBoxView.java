package com.viettel.gem.screen.collectinfo.custom.checkbox;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by BaVV on 5/31/16.
 */
public class CheckBoxView
        extends CustomView
        implements CheckBoxItemView.Callback {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.boxCheckBox)
    FlexboxLayout boxCheckBox;

    private Map<String, CheckBoxItemView> mSelectedViews = new HashMap<>();

    public CheckBoxView(Context context) {
        super(context);
    }

    public CheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_check_box_view;
    }

    @Override
    public boolean validateView() {
        boolean flag = false;
        for (Map.Entry<String, CheckBoxItemView> entry : mSelectedViews.entrySet()) {
            CheckBoxItemView checkBoxItemView = entry.getValue();
            if (checkBoxItemView.validateView()) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    @Override
    public void onCheckedChanged(boolean checked, CheckBoxItemView checkBoxItemView) {
        if (checked) {
            addToSelectedViews(checkBoxItemView);
        } else {
            removeFromSelectedViews(checkBoxItemView);
        }
    }

    public void build(ProductSpecCharDTO productSpecCharDTO) {
        if (null == productSpecCharDTO) return;

        tvName.setText(productSpecCharDTO.getName());

        mSelectedViews = new HashMap<>();
        List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();

        boolean flag = false;
        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                CheckBoxItemView itemView = new CheckBoxItemView(getContext());

                itemView.build(specCharValueDTOList, this);
                boxCheckBox.addView(itemView);
                mSelectedViews.put(specCharValueDTOList.getId(), itemView);

                if (specCharValueDTOList.getValueData() != null && !"null".equalsIgnoreCase(specCharValueDTOList.getValueData()) && !flag) {
                    flag = true;
                }
            }
        }

    }

    private void addToSelectedViews(CheckBoxItemView checkBoxItemView) {
        ProductSpecCharValueDTOList answerModel = checkBoxItemView.getModel();
        if (null == mSelectedViews) {
            mSelectedViews = new HashMap<>();
        }
        checkBoxItemView.setValueData(1 + "");
        mSelectedViews.put(answerModel.getId(), checkBoxItemView);
    }

    private void removeFromSelectedViews(CheckBoxItemView checkBoxItemView) {
        checkBoxItemView.setValueData(null);
//        ProductSpecCharValueDTOList answerModel = checkBoxItemView.getModel();
//        if (null != mSelectedViews) {
//            mSelectedViews.remove(answerModel.getId());
//        }
    }
}
