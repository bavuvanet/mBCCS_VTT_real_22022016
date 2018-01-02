package com.viettel.gem.screen.collectinfo.custom.radio;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by BaVV on 5/31/16.
 */
public class RadioBoxView
        extends CustomView
        implements RadioItemView.Callback {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.boxRadio)
    FlexboxLayout boxRadio;

    private Map<String, RadioItemView> mViewManager;

    private RadioItemView mSelectedView;

    public RadioBoxView(Context context) {
        super(context);
    }

    public RadioBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_radio_box_view;
    }

    @Override
    public boolean validateView() {
        boolean flag = true;
        for (Map.Entry<String, RadioItemView> entry : mViewManager.entrySet()) {
            RadioItemView radioItemView = entry.getValue();
            if (!radioItemView.validateView()) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    @Override
    public void onChecked(RadioItemView radioItemView) {
        ProductSpecCharValueDTOList answerModel = radioItemView.getModel();
        String id = answerModel.getId();

        for (RadioItemView itemView : mViewManager.values()) {
            if (!itemView.getModel().getId().equals(id)) {
                itemView.setChecked(false);
                itemView.setValueData(null);
            } else {
                mSelectedView = itemView;
                mSelectedView.setValueData(1 + "");
            }
        }
    }

    public void build(ProductSpecCharDTO productSpecCharDTO) {
        if (null == productSpecCharDTO) return;

        tvName.setText(productSpecCharDTO.getName());

        mViewManager = new HashMap<>();
        List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();

        boolean flag = false;
        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                RadioItemView itemView = new RadioItemView(getContext());

                itemView.build(productSpecCharDTO.getCode(), specCharValueDTOList, this);
                boxRadio.addView(itemView);
                mViewManager.put(specCharValueDTOList.getId(), itemView);

                if (specCharValueDTOList.getValueData() != null && !"null".equalsIgnoreCase(specCharValueDTOList.getValueData()) && !flag) {
                    flag = true;
                }
            }

            if (!flag) {
                if (boxRadio.getChildCount() > 0) {
                    RadioItemView itemView = (RadioItemView) boxRadio.getChildAt(0);
                    itemView.setChecked(true);
                }
            }
        }

    }
}
