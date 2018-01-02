package com.viettel.gem.screen.collectinfo.custom.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.common.Constants;

import butterknife.BindView;


/**
 * Created by BaVV on 5/31/16.
 */
public class EditTextItemView
        extends CustomView {

    private static final String ISDN = "ISDN";
    private static final String ID_NO = "ID_NO";
    private static final String ADDRESS = "CUST_INSTRUCTRUE";

    @BindView(R.id.edtInput)
    EditText edtInput;

    String mTitle;

    String mCode;

    String isdn;

    String idno;

    String address;

    boolean createNew = false;

    private Callback mCallback;

    public EditTextItemView(Context context) {
        super(context);
    }

    public EditTextItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_edittext_box_item_view;
    }

    @Override
    public boolean validateView() {
        String content = edtInput.getText().toString().trim();
        if (content.isEmpty()) {
            edtInput.setError("Bạn chưa nhập " + mTitle);
            return false;
        } else {
            edtInput.setError(null);
            edtInput.clearFocus();
        }
        return true;
    }

    public void build(String code, String name, final Callback callback) {

        mCallback = callback;

        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != callback) {
                    callback.onTextChanged(edtInput.getText().toString());
                }
            }
        });

        if (ISDN.equals(code) || ID_NO.equals(code) || ADDRESS.equals(code)) {
            if (isCreateNew() || null == name || name.trim().isEmpty()) {
                if (ISDN.equals(code)) {
                    edtInput.setText(isdn);
                    if (!CommonActivity.isNullOrEmpty(isdn)) {
                        edtInput.setEnabled(false);
                    } else {
                        edtInput.setEnabled(true);
                    }
                } else if (ID_NO.equals(code)) {
                    edtInput.setText(idno);
                    if (!CommonActivity.isNullOrEmpty(idno)) {
                        edtInput.setEnabled(false);
                    } else {
                        edtInput.setEnabled(true);
                    }
                } else if (ADDRESS.equals(code)) {
                    edtInput.setText(address);
                    if (!CommonActivity.isNullOrEmpty(address)) {
                        edtInput.setEnabled(false);
                    } else {
                        edtInput.setEnabled(true);
                    }
                }
            } else {
                edtInput.setText(name);
                if (!CommonActivity.isNullOrEmpty(name)) {
                    edtInput.setEnabled(false);
                } else {
                    edtInput.setEnabled(true);
                }
            }
        } else {
            edtInput.setText(name);
        }

    }

    public void build(String code, String title, String name, int type, Callback callback) {
        mTitle = title;
        mCode = code;
        if (Constants.DIGIT == type) {
            edtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            edtInput.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        build(code, name, callback);
    }

    public String getInput() {
        return edtInput.getText().toString().trim();
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getAddress() {
        return address;
    }

    public EditTextItemView setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isCreateNew() {
        return createNew;
    }

    public void setCreateNew(boolean createNew) {
        this.createNew = createNew;
    }

    public interface Callback {
        void onTextChanged(String text);
    }
}
