package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetSubscriberInfoTVBH;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/10/2017.
 */

public class SearchAdvisoryFragment extends FragmentCommon {

    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.edtNumberValue)
    EditText edtNumberValue;

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getActivity().getString(R.string.advisory_customers));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.search_advisory_layout;
    }

    @Override
    protected void unit(View v) {

        //this.edtNumberValue.setText("973674067");

        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ReplaceFragment.replaceFragment(getActivity(), new MainAdvisoryFragment(new CCOutput()), false);

                new AsynTaskGetSubscriberInfoTVBH(getActivity(), new OnPostExecuteListener<CCOutput>() {
                    @Override
                    public void onPostExecute(CCOutput result, String errorCode, String description) {
                        if(!CommonActivity.isNullOrEmpty(result)){
                            ReplaceFragment.replaceFragment(getActivity(), new MainAdvisoryFragment(result), false);
                        } else {
                            CommonActivity.toast(getActivity(), R.string.no_data);
                        }
                    }
                }, moveLogInAct).execute(StringUtils.formatIsdn(edtNumberValue.getText().toString()));
            }
        });
    }

    @Override
    protected void setPermission() {

    }
}