package com.viettel.gem.screen.collectinfo.custom.spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by BaVV on 5/31/16.
 */
public class SpinnerBoxView
        extends CustomView
        implements SpinnerItemView.Callback {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.imvAdd)
    ImageView imvAdd;

    @BindView(R.id.boxSpinner)
    LinearLayout boxSpinner;

    ProductSpecCharDTO productSpecCharDTO;

    List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = new ArrayList<>();

    private Map<String, SpinnerItemView> mViewManager;

    public SpinnerBoxView(Context context) {
        super(context);
    }

    public SpinnerBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_spinner_box_view;
    }

    @Override
    public boolean validateView() {

        if (null != productSpecCharDTO && "CUS_INFO_JOB_GROUP".equals(productSpecCharDTO.getCode())) {
            return (CollectCustomerInfoPresenter.mTotalJob == CollectCustomerInfoPresenter.mTotalMember)
                    && (CollectCustomerInfoPresenter.mTotalJob != 0 && CollectCustomerInfoPresenter.mTotalMember != 0);
        }

        boolean flag = false;
        for (Map.Entry<String, SpinnerItemView> entry : mViewManager.entrySet()) {
            String key = entry.getKey();
            SpinnerItemView spinnerItemView = entry.getValue();
            if (spinnerItemView.validateView()) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public void build(ProductSpecCharDTO productSpecCharDTO) {
        if (null == productSpecCharDTO) return;

        this.productSpecCharDTO = productSpecCharDTO;

        tvName.setText(productSpecCharDTO.getName());

        mViewManager = new HashMap<>();
        productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();

        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            boolean flag = false;
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                try {
                    if (null != specCharValueDTOList.getValueData() && Integer.parseInt(specCharValueDTOList.getValueData()) > 0) {
                        flag = true;
                        addChildView(specCharValueDTOList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!flag) {
                for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                    if (productSpecCharValueDTOList.indexOf(specCharValueDTOList) < 2) {
                        addChildView(specCharValueDTOList);
                    }
                }
            }
        }
    }

    @OnClick(R.id.imvAdd)
    void addRow() {
        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                if (null != mViewManager && !mViewManager.containsKey(specCharValueDTOList.getId())) {
                    addChildView(specCharValueDTOList);
                    break;
                }
            }
        }
    }

    void addChildView(ProductSpecCharValueDTOList specCharValueDTOList) {
        SpinnerItemView itemView = new SpinnerItemView(getContext());
        mViewManager.put(specCharValueDTOList.getId(), itemView);
        itemView.setViewManager(mViewManager);
        itemView.build(this, specCharValueDTOList, productSpecCharValueDTOList, this);
        boxSpinner.addView(itemView);
    }

    @Override
    public void onSelected(String oldId, String id, SpinnerItemView spinnerItemView) {
        if (null == mViewManager) return;

        if (mViewManager.containsKey(oldId)) mViewManager.remove(oldId);
        mViewManager.put(id, spinnerItemView);
    }

    public boolean calCount() {
        int totalCount = 0;
        if (null == productSpecCharDTO || !"CUS_INFO_JOB_GROUP".equals(productSpecCharDTO.getCode()))
            return false;
        List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();

        if (null != productSpecCharValueDTOList && !productSpecCharValueDTOList.isEmpty()) {
            for (ProductSpecCharValueDTOList specCharValueDTOList : productSpecCharValueDTOList) {
                String valueData = specCharValueDTOList.getValueData();
                try {
                    if (null != valueData && Integer.parseInt(valueData) > 0) {
                        int count = 0;
                        count = null == valueData ? 0 : Integer.parseInt(valueData);
                        totalCount += count;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//        if (totalCount <= CollectCustomerInfoPresenter.mTotalMember) {
        CollectCustomerInfoPresenter.mTotalJob = totalCount;
//            return true;
//        }

        return false;
    }
}
