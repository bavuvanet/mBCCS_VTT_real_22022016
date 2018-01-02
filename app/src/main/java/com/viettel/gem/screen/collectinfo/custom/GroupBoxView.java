package com.viettel.gem.screen.collectinfo.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharUseDTO;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.common.Constants;
import com.viettel.gem.screen.collectinfo.custom.checkbox.CheckBoxView;
import com.viettel.gem.screen.collectinfo.custom.date.DateBoxView;
import com.viettel.gem.screen.collectinfo.custom.edittext.EditTextBoxView;
import com.viettel.gem.screen.collectinfo.custom.picture.PictureBoxView;
import com.viettel.gem.screen.collectinfo.custom.radio.RadioBoxView;
import com.viettel.gem.screen.collectinfo.custom.spinner.SpinnerBoxView;
import com.viettel.gem.screen.collectinfo.custom.textview.TextBoxView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by BaVV on 5/31/16.
 */
public class GroupBoxView
        extends CustomView {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.boxRoot)
    LinearLayout boxRoot;

    @BindView(R.id.boxGroup)
    LinearLayout boxGroup;

    private Activity activity;

    String isdn;
    String idno;
    String address;
    boolean createNew;

    private ProductSpecificationDTO mProductSpecificationDTO;

    //    private Map<String, CustomView> mSelectedViews;
    List<CustomView> mSelectedViews = new ArrayList<>();

    public GroupBoxView(Context context) {
        super(context);
    }

    public GroupBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_group_box_view;
    }

    public void build(ProductSpecificationDTO productSpecificationDTO, Activity activity) {
        this.activity = activity;
        boxGroup.removeAllViews();

        if (null != productSpecificationDTO) {
            mProductSpecificationDTO = productSpecificationDTO;

            tvName.setText(productSpecificationDTO.getName());
            List<ProductSpecCharUseDTO> lstProductSpecCharUseDTO = productSpecificationDTO.getLstProductSpecCharUseDTO();
            if (null != lstProductSpecCharUseDTO && !lstProductSpecCharUseDTO.isEmpty()) {
                //duyet danh sach cau hoi
                for (ProductSpecCharUseDTO productSpecCharUseDTO : lstProductSpecCharUseDTO) {
                    /*
                    * productSpecCharDTO: 1 loai cau hoi
                    * */
                    ProductSpecCharDTO productSpecCharDTO = productSpecCharUseDTO.getListProductSpecCharDTOs();
                    //type xac dinh loai giao dien cau hoi
                    /*
                    *   STRING            1
                        DIGIT             2
                        TIME              3
                        BOOLEAN           4
                        LIST_ONE          5
                        LIST_MANY         6
                        TEXT_AREA         7
                        EDITOR            8
                        LIST_OR_TEXT      9
                        IMAGE             10
                        TREE              11
                        RADIO             12
                        ADDRESS           13
                        LISTPLUS          14
                        LISTSPINPLUS      15
                        CHECKBOX          16
                        BIRTHDAY         17
                    * */
                    int valueType = productSpecCharDTO.getValueType();
                    switch (valueType) {
                        case Constants.STRING:
                        case Constants.DIGIT:
                            EditTextBoxView editTextView = new EditTextBoxView(getContext());
                            editTextView.setIsdn(isdn);
                            editTextView.setIdno(idno);
                            editTextView.setCreateNew(isCreateNew());
                            editTextView.build(productSpecCharDTO);
                            addCustomView(editTextView);
                            break;
                        case Constants.TIME:
                        case Constants.BIRTHDAY:
                            DateBoxView dateBoxView = new DateBoxView(getContext());
                            dateBoxView.build(productSpecCharDTO);
                            addCustomView(dateBoxView);
                            break;
                        case Constants.IMAGE:
                            PictureBoxView pictureBoxView = new PictureBoxView(getContext());
                            pictureBoxView.setIsdn(getIsdn());
                            pictureBoxView.build(productSpecCharDTO, activity);
                            addCustomView(pictureBoxView);
                            break;
                        case Constants.RADIO:
                            RadioBoxView radioBoxView = new RadioBoxView(getContext());
                            radioBoxView.build(productSpecCharDTO);
                            addCustomView(radioBoxView);
                            break;
                        case Constants.ADDRESS:
                            EditTextBoxView addressTextView = new EditTextBoxView(getContext());
                            addressTextView.setAdddress(address);
                            addressTextView.build(productSpecCharDTO);
                            addCustomView(addressTextView);
                            break;
                        case Constants.LISTPLUS:
                            TextBoxView textBoxView = new TextBoxView(getContext());
                            textBoxView.build(productSpecCharDTO);
                            addCustomView(textBoxView);
                            break;
                        case Constants.LISTSPINPLUS:
                            SpinnerBoxView spinnerBoxView = new SpinnerBoxView(getContext());
                            spinnerBoxView.build(productSpecCharDTO);
                            addCustomView(spinnerBoxView);
                            break;
                        case Constants.CHECKBOX:
                            CheckBoxView checkBoxView = new CheckBoxView(getContext());
                            checkBoxView.build(productSpecCharDTO);
                            addCustomView(checkBoxView);
                            break;
                        default:
                            break;
                    }
                }
            }

        }
    }

    public void addCustomView(CustomView customView) {
        boxGroup.addView(customView);
        mSelectedViews.add(customView);
    }

    public boolean validateView() {
        boolean flag = false;
        for (CustomView customView : mSelectedViews) {
            if (null != customView) {
                flag = customView.validateView();
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            this.requestFocus();
            boxRoot.setBackgroundResource(R.drawable.bg_group_view_error);
        } else {
            boxRoot.setBackgroundResource(R.drawable.bg_group_view_normal);
        }

        return flag;
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

    public GroupBoxView setIdno(String idno) {
        this.idno = idno;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public GroupBoxView setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isCreateNew() {
        return createNew;
    }

    public GroupBoxView setCreateNew(boolean createNew) {
        this.createNew = createNew;
        return this;
    }

    public void resultPicture(String filePath) {
        for (CustomView customView : mSelectedViews) {
            if (customView instanceof PictureBoxView) {
                ((PictureBoxView) customView).onResult(filePath);
            }
        }
    }
}
