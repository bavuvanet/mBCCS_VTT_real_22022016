package com.viettel.gem.screen.collectinfo.custom.edittext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.gem.base.view.CustomView;

import butterknife.BindView;


/**
 * Created by BaVV on 5/31/16.
 */
public class EditTextBoxView
        extends CustomView
        implements EditTextItemView.Callback {

    @BindView(R.id.boxInput)
    LinearLayout boxInput;

    @BindView(R.id.tvName)
    TextView tvName;

    String isdn;

    String idno;

    String adddress;

    boolean createNew = false;

    ProductSpecCharDTO productSpecCharDTO;

    private EditTextItemView mSelectedView;

    public EditTextBoxView(Context context) {
        super(context);
    }

    public EditTextBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_edittext_box_view;
    }

    @Override
    public boolean validateView() {
        return mSelectedView.validateView();
    }

    public void build(ProductSpecCharDTO productSpecCharDTO) {
        this.productSpecCharDTO = productSpecCharDTO;
        if (null == this.productSpecCharDTO) return;

        tvName.setText(this.productSpecCharDTO.getName());

        boxInput.removeAllViews();

        mSelectedView = new EditTextItemView(getContext());
        mSelectedView.setIsdn(isdn);
        mSelectedView.setIdno(idno);
        mSelectedView.setAddress(adddress);
        mSelectedView.setCreateNew(createNew);
        mSelectedView.build(this.productSpecCharDTO.getCode(), this.productSpecCharDTO.getName(), productSpecCharDTO.getValueData(), productSpecCharDTO.getValueType(), this);

        boxInput.addView(mSelectedView);
    }

    public ProductSpecCharDTO getSelectedAnswerModel() {
        if (null != mSelectedView) {
//            this.productSpecCharDTO.setInputValue(mSelectedView.getInput());

            return this.productSpecCharDTO;
        }

        return null;
    }

    public String getIsdn() {
        return isdn;
    }

    public EditTextBoxView setIsdn(String isdn) {
        this.isdn = isdn;
        return this;
    }

    public String getIdno() {
        return idno;
    }

    public EditTextBoxView setIdno(String idno) {
        this.idno = idno;
        return this;
    }

    public String getAdddress() {
        return adddress;
    }

    public EditTextBoxView setAdddress(String adddress) {
        this.adddress = adddress;
        return this;
    }

    public boolean isCreateNew() {
        return createNew;
    }

    public EditTextBoxView setCreateNew(boolean createNew) {
        this.createNew = createNew;
        return this;
    }

    @Override
    public void onTextChanged(String text) {
        productSpecCharDTO.setValueData(text);
    }
}
