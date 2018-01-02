package com.viettel.gem.screen.collectinfo.custom.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;
import com.viettel.gem.screen.collectinfo.InputValueDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by BaVV on 5/31/16.
 */
public class TextItemView
        extends CustomView {

    //NUMBER_FAMILY
    //CUS_INFO_JOB_GROUP

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.imvDown)
    ImageView imvDown;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.imvUp)
    ImageView imvUp;

    TextBoxView mTextBoxView;

    ProductSpecCharValueDTOList answerModel;

    private Callback mCallback;

    int count = 0;

    public TextItemView(Context context) {
        super(context);
    }

    public TextItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_text_box_item_view;
    }

    @Override
    public boolean validateView() {
        return count != 0;
    }

    public void build(TextBoxView textBoxView, ProductSpecCharValueDTOList answerModel, Callback callback) {
        mTextBoxView = textBoxView;
        this.answerModel = answerModel;
        mCallback = callback;
        tvName.setText(answerModel.getName());
        try {
            count = null == answerModel.getValueData() ? 0 : Integer.parseInt(answerModel.getValueData());
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
        count++;
        setCount(count, true);
    }

    @OnClick(R.id.tvCount)
    void changeCount() {
        new InputValueDialog(getContext()).setListener(new InputValueDialog.Callback() {
            @Override
            public void onValueChanged(String value) {
                try {
                    count = Integer.parseInt(value);
                } catch (Exception e) {
                    count = 0;
                    e.printStackTrace();
                }
                setCount(count, true);
            }
        }).setOldValue(count == 0 ? "" : tvCount.getText().toString()).show();
    }

    public String getInput() {
        return tvCount.getText().toString().trim();
    }

    private void setCount(int count, boolean setValue) {
        tvCount.setText(count + "");
        if (setValue)
            answerModel.setValueData(count + "");

        mTextBoxView.calCount();
    }

    public interface Callback {
    }
}
