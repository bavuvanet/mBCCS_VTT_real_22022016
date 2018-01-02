package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListSubByIsdnAdapter;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListSubByIsdn;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by toancx on 2/21/2017.
 */

public class LookupComplainFragment extends FragmentCommon {

    @BindView(R.id.edtFromDate)
    EditText edtFromDate;
    @BindView(R.id.edtToDate)
    EditText edtToDate;
    @BindView(R.id.edtIsdnComplain)
    EditText edtIsdnComplain;
    @BindView(R.id.rvTransComplain)
    RecyclerView rvTransComplain;
    @BindView(R.id.fabAddComplain)
    FloatingActionButton fabAddComplain;
    @BindView(R.id.lnHeader)
    LinearLayout lnHeader;

    ListSubByIsdnAdapter listSubByIsdnAdapter;

    public static LookupComplainFragment newInstance() {
        LookupComplainFragment fragment = new LookupComplainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_lookup_complain;
    }

    @Override
    public void onResume() {
        setTitleActionBar(R.string.txt_lookup_complain);
        super.onResume();
    }

    @Override
    protected void unit(View v) {
        rvTransComplain.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvTransComplain, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SubscriberDTO subscriberDTO = listSubByIsdnAdapter.getLstData().get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subscriberDTO", subscriberDTO);
                bundle.putString("fromDate", edtFromDate.getText().toString());
                bundle.putString("toDate", edtToDate.getText().toString());

                ListComplainFragment fragment = new ListComplainFragment();
                fragment.setArguments(bundle);

                ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        edtFromDate.setText(DateTimeUtils.convertDateTimeToString(new Date(), "dd/MM/yyyy"));
        edtToDate.setText(DateTimeUtils.convertDateTimeToString(new Date(), "dd/MM/yyyy"));

        new DateTimeDialogWrapper(edtFromDate, getActivity());
        new DateTimeDialogWrapper(edtToDate, getActivity());

        edtIsdnComplain.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!validateLookup()){
                        return false;
                    }

                    new AsynTaskGetListSubByIsdn(getActivity(), new OnPostGetListSubByIsdn(), moveLogInAct).execute(edtIsdnComplain.getText().toString());
                    return true;
                }
                return false;
            }
        });

        edtIsdnComplain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lnHeader.setVisibility(View.GONE);
                rvTransComplain.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void setPermission() {

    }

    @OnClick(R.id.fabAddComplain)
    public void fabAddComplainOnClick(){
        ReplaceFragment.replaceFragment(getActivity(), ComplainReceiveFragment.newInstance(), false);
    }

    @OnClick(R.id.btnLookup)
    public void btnLookupOnClick(){
        lnHeader.setVisibility(View.GONE);
        rvTransComplain.setVisibility(View.GONE);

        if(!validateLookup()){
            return;
        }

        new AsynTaskGetListSubByIsdn(getActivity(), new OnPostGetListSubByIsdn(), moveLogInAct)
                .execute(edtIsdnComplain.getText().toString());
    }

    private boolean validateLookup(){
        if(CommonActivity.isNullOrEmpty(edtIsdnComplain.getText().toString())){
            CommonActivity
                    .createAlertDialog(
                            getActivity(),
                            getString(R.string.txt_isdn_complain_required),
                            getString(R.string.app_name))
                    .show();
            return false;
        }

        if (StringUtils.CheckCharSpecical(edtIsdnComplain.getText()
                .toString())) {
            CommonActivity.createAlertDialog(
                    getActivity(),
                    getResources().getString(
                            R.string.txt_isdn_complain_contain_special),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        return DateTimeUtils.validateData(getActivity(), edtFromDate, edtToDate, 31);
    }

    private class OnPostGetListSubByIsdn implements OnPostExecuteListener<List<SubscriberDTO>> {
        @Override
        public void onPostExecute(List<SubscriberDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    lnHeader.setVisibility(View.VISIBLE);
                    rvTransComplain.setVisibility(View.VISIBLE);
                    rvTransComplain.setHasFixedSize(true);
                    rvTransComplain.setLayoutManager(new LinearLayoutManager(getActivity()));

                    listSubByIsdnAdapter = new ListSubByIsdnAdapter(getActivity(), result);
                    rvTransComplain.setAdapter(listSubByIsdnAdapter);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }
}
