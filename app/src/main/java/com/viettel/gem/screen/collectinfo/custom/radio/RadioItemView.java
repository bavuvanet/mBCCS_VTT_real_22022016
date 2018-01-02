package com.viettel.gem.screen.collectinfo.custom.radio;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * Created by BaVV on 5/31/16.
 */
public class RadioItemView
        extends CustomView {

    private static final String OTHER = "OTHER";
    private static final String CUST_CDT = "CUST_CDT";

    @BindView(R.id.rbtnValue)
    RadioButton rbtnValue;

    @BindView(R.id.edtInput)
    EditText edtInput;

    private Callback mCallback;

    private ProductSpecCharValueDTOList mAnswerModel;

    private String mId;
    private String mCode;

    public RadioItemView(Context context) {
        super(context);
    }

    public RadioItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_radio_box_item_view;
    }

    @Override
    public boolean validateView() {

        if (rbtnValue.isChecked() && OTHER.equals(mId)) {
            return !edtInput.getText().toString().trim().isEmpty();
        }

        return true;
    }

    public void build(String code, ProductSpecCharValueDTOList answerModel, Callback callback) {
        mCode = code;
        mId = answerModel.getId();
        mAnswerModel = answerModel;
        mCallback = callback;
        rbtnValue.setText(answerModel.getName());
        rbtnValue.setChecked(null != answerModel.getValueData());

        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setValueData(getInput());
            }
        });

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth() - 100;

        setLayoutParams(new LinearLayout.LayoutParams((int) (width / 2), LinearLayout.LayoutParams.WRAP_CONTENT));

        if (rbtnValue.isChecked() && OTHER.equals(mId)) {
            edtInput.setText(answerModel.getValueData());
        }
    }

    public void setChecked(boolean checked) {
        rbtnValue.setChecked(checked);
    }

    @OnCheckedChanged(R.id.rbtnValue)
    public void onChecked(boolean checked) {
        Log.e(getClass().getName(), "checked => " + checked);
        if (null != mCallback && checked) {
            mCallback.onChecked(this);
        }

        if (CUST_CDT.equals(mCode)) {
            CollectCustomerInfoPresenter.selectedOtherCustCDT = false;
        }

        if (checked && OTHER.equals(mId)) {
            edtInput.setVisibility(VISIBLE);
            if (CUST_CDT.equals(mCode)) {
                CollectCustomerInfoPresenter.selectedOtherCustCDT = true;
            }
        } else {
            edtInput.setVisibility(GONE);
        }
    }

    public void setValueData(String valueData) {
        mAnswerModel.setValueData(valueData);
    }

    public ProductSpecCharValueDTOList getModel() {
        return mAnswerModel;
    }

    public String getInput() {
        return edtInput.getText().toString();
    }

    public interface Callback {
        void onChecked(RadioItemView radioItemView);
    }
}