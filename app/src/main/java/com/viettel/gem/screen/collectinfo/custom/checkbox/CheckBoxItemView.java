package com.viettel.gem.screen.collectinfo.custom.checkbox;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * Created by BaVV on 5/31/16.
 */
public class CheckBoxItemView
        extends CustomView {

    private static final String OTHER = "OTHER";

    @BindView(R.id.cboValue)
    CheckBox cboValue;

    @BindView(R.id.edtInput)
    EditText edtInput;

    private ProductSpecCharValueDTOList mAnswerModel;

    private String mId;

    private Callback mCallback;

    public CheckBoxItemView(Context context) {
        super(context);
    }

    public CheckBoxItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_check_box_item_view;
    }

    @Override
    public boolean validateView() {

        if (cboValue.isChecked() && OTHER.equals(mId)) {
            return !edtInput.getText().toString().trim().isEmpty();
        }

        return cboValue.isChecked();
    }

    public void build(ProductSpecCharValueDTOList answerModel, Callback callback) {
        mId = answerModel.getId();
        mAnswerModel = answerModel;
        mCallback = callback;
        cboValue.setText(answerModel.getName());
        if (null != answerModel.getValueData()) {
            cboValue.setChecked(true);
        } else {
            cboValue.setChecked(false);
        }

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

        if (cboValue.isChecked() && OTHER.equals(mId)) {
            edtInput.setText(answerModel.getValueData());
        }
    }

    public void setChecked(boolean checked) {
        cboValue.setChecked(checked);
    }

    public void setValueData(String valueData) {
        mAnswerModel.setValueData(valueData);
    }

    @OnCheckedChanged(R.id.cboValue)
    public void onCheckedChanged(boolean checked) {
        if (null != mCallback) {
            mCallback.onCheckedChanged(checked, this);
        }

        if (checked && OTHER.equals(mId)) {
            edtInput.setVisibility(VISIBLE);
        } else {
            edtInput.setVisibility(GONE);
        }
    }

    public String getInput() {
        return edtInput.getText().toString();
    }

    public ProductSpecCharValueDTOList getModel() {
        return mAnswerModel;
    }

    public interface Callback {
        void onCheckedChanged(boolean checked, CheckBoxItemView checkBoxItemView);
    }
}
