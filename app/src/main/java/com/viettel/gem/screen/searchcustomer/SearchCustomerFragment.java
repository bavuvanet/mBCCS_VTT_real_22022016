package com.viettel.gem.screen.searchcustomer;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.gem.base.viper.ViewFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 21/11/2017.
 */

public class SearchCustomerFragment extends ViewFragment<SearchCustomerContract.Presenter>
        implements SearchCustomerContract.View {


    @BindView(R.id.check_btn)
    Button mCheckBtn;

    @BindView(R.id.number_exhibit_edt)
    EditText mNumberExhibitEdt;

    @BindView(R.id.number_acc_edt)
    EditText mNumberAccEdt;

    public static SearchCustomerFragment getInstance() {
        return new SearchCustomerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_infor;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @OnClick({R.id.check_btn, R.id.back_iv})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                mPresenter.back();
                break;
            case R.id.check_btn:
                checkCondition();
                break;
        }
    }

    void checkCondition() {
        String isdn = mNumberAccEdt.getText().toString().trim();
        String idno = mNumberExhibitEdt.getText().toString().trim();

        if ("".equals(isdn) &&
                "".equals(idno)) {
            Toast.makeText(getViewContext(), R.string.check_condition_toast, Toast.LENGTH_SHORT).show();
        } else {
            if (isMatch(isdn) && isMatch(idno))
                mPresenter.doSearch(isdn, idno);
            else
                Toast.makeText(getViewContext(), R.string.msg_isdn_or_idno_invalid, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isMatch(String s) {
        if (s == null || s.trim().isEmpty()) {
            return true;
        }
        Pattern p = Pattern.compile("^[A-Za-z0-9_]{1,50}$");
        Matcher m = p.matcher(s);

        return m.matches();

    }
}
