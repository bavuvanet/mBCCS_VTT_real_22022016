package com.viettel.gem.screen.collectinfo.custom.spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;
import com.viettel.gem.screen.collectinfo.InputValueDialog;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by BaVV on 5/31/16.
 */
public class SpinnerItemView
        extends CustomView {

    @BindView(R.id.spnName)
    Spinner spnName;

    @BindView(R.id.imvDown)
    ImageView imvDown;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.imvUp)
    ImageView imvUp;

    SpinnerBoxView mSpinnerBoxView;

    int count = 0;

    List<ProductSpecCharValueDTOList> mProductSpecCharValueDTOList;

    ProductSpecCharValueDTOList mSpecCharValueDTOList;

    private Map<String, SpinnerItemView> mViewManager;

    private Callback mCallback;

    public SpinnerItemView(Context context) {
        super(context);
    }

    public SpinnerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_spinner_box_item_view;
    }

    @Override
    public boolean validateView() {
        return count != 0;
    }

    public void setViewManager(Map<String, SpinnerItemView> mViewManager) {
        this.mViewManager = mViewManager;
    }

    public void build(SpinnerBoxView spinnerBoxView, ProductSpecCharValueDTOList specCharValueDTOList, List<ProductSpecCharValueDTOList> productSpecCharValueDTOList, Callback callback) {
        mSpinnerBoxView = spinnerBoxView;
        this.mSpecCharValueDTOList = specCharValueDTOList;
        this.mProductSpecCharValueDTOList = productSpecCharValueDTOList;
        mCallback = callback;

        initSpinner();
    }

    private void initSpinner() {
        spnName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                try {
                    ProductSpecCharValueDTOList specCharValueDTOList = mProductSpecCharValueDTOList.get(position);
                    if (null != mCallback &&
                            null != specCharValueDTOList &&
                            null != mViewManager &&
                            !mViewManager.containsKey(specCharValueDTOList.getId())) {
                        specCharValueDTOList.setValueData(mSpecCharValueDTOList.getValueData());
                        mSpecCharValueDTOList.setValueData(null);
                        mCallback.onSelected(mSpecCharValueDTOList.getId(), specCharValueDTOList.getId(), SpinnerItemView.this);
                    } else {
                        spnName.setSelection(mProductSpecCharValueDTOList.indexOf(mSpecCharValueDTOList));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (null != this.mProductSpecCharValueDTOList && !this.mProductSpecCharValueDTOList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(), R.layout.adapter_spinner_item_view, R.id.tvContent);
            adapter.setDropDownViewResource(R.layout.adapter_spinner_dropdown_view);

            for (ProductSpecCharValueDTOList specCharValueDTOList : mProductSpecCharValueDTOList) {
                adapter.add(specCharValueDTOList.getName());
            }
            spnName.setAdapter(adapter);
            spnName.setSelection(mProductSpecCharValueDTOList.indexOf(mSpecCharValueDTOList));
        }

        try {
            count = null == this.mSpecCharValueDTOList.getValueData() ? 0 : Integer.parseInt(this.mSpecCharValueDTOList.getValueData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCount(count, false);
    }

    @OnClick(R.id.imvDown)
    void onClickDown() {
        if (count == 0) return;
        count--;
        setCount(count, true);
    }

    @OnClick(R.id.imvUp)
    void onClickUp() {
//        if (!canCount()) return;
//        int c = count + 1;
//        if (setCount(c, true)) {
//            count = c;
//        }
        count++;
        setCount(count, true);
    }

    @OnClick(R.id.tvCount)
    void changeCount() {
        new InputValueDialog(getContext()).setListener(new InputValueDialog.Callback() {
            @Override
            public void onValueChanged(String value) {
                int c = 0;
                try {
                    c = Integer.parseInt(value);
                } catch (Exception e) {
                    c = 0;
                    e.printStackTrace();
                }
//                if (setCount(c, true)) {
                count = c;
//                }
                setCount(count, true);
            }
        }).setOldValue(count == 0 ? "" : tvCount.getText().toString()).show();
    }

//    private boolean canCount() {
//        return (CollectCustomerInfoPresenter.mTotalJob < CollectCustomerInfoPresenter.mTotalMember) || (CollectCustomerInfoPresenter.mTotalJob == 0 && CollectCustomerInfoPresenter.mTotalMember == 0);
//    }

    private boolean setCount(int count, boolean setValue) {
        tvCount.setText(count + "");
        if (setValue)
            mSpecCharValueDTOList.setValueData(count + "");

        return mSpinnerBoxView.calCount();
    }

    public ProductSpecCharValueDTOList getAnswerModel() {
        return mSpecCharValueDTOList;
    }

    public String getName() {
        return (String) spnName.getSelectedItem();
    }

    public String getCount() {
        return tvCount.getText().toString().trim();
    }

    public interface Callback {
        void onSelected(String oldId, String id, SpinnerItemView spinnerItemView);
    }
}
