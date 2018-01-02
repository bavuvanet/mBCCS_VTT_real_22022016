package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.AdapterInvalidInfoSubcriber;
import com.viettel.bss.viettelpos.v4.customer.asynctask.SearchInvalidInfoSubcriberAsyncTask;
import com.viettel.bss.viettelpos.v4.customer.object.SubInvalidDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuandq on 11/18/2017.
 */

public class FragmentSearchInvalidInfoSubcriber extends FragmentCommon {
    private ListView lv;
    private EditText editText;
    private Button btnSearch;
    private List<SubInvalidDTO> listSubInvalidDTO;
    private List<String> lstisdn = new ArrayList<>();
    private AdapterInvalidInfoSubcriber adapterInvalidInfoSubcriber;
    private boolean failInfo = false;
    private LinearLayout lnInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.search_invalid_info_subcriber;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.search_invalid_info_subcriber, container, false);
//        return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.invalid_info_subs);
    }

    @Override
    protected void unit(View v) {

        lv = (ListView) v.findViewById(R.id.lvfileInfo);
        editText = (EditText) v.findViewById(R.id.edtnumberPhone);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lnInfo.setVisibility(View.GONE);
            }
        });

        btnSearch = (Button) v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editText).getText().toString().equals("")) {
                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.isdnnotempty), "mBCCS2").show();
                } else {
//                    listSubInvalidDTO = new ArrayList<SubInvalidDTO>();
//                    adapterInvalidInfoSubcriber = new AdapterInvalidInfoSubcriber(getActivity(), listSubInvalidDTO, false);
//                    lv.setAdapter(adapterInvalidInfoSubcriber);
//                    adapterInvalidInfoSubcriber.notifyDataSetChanged();
                    searchInfo();
                }
            }
        });
        lnInfo = (LinearLayout) v.findViewById(R.id.lnInfo);
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void searchInfo() {
        lstisdn = new ArrayList<>();
        String isdn = StringUtils.formatIsdn(editText.getText().toString().trim());
        if (!StringUtils.isViettelMobile(isdn)) {
            CommonActivity.showConfirmValidate(getActivity(), R.string.phone_number_invalid_format);
            return;
        }
        lstisdn.add(isdn);
        lnInfo.setVisibility(View.GONE);

        final SearchInvalidInfoSubcriberAsyncTask searchInvalidInfoSubcriberAsyncTask = new SearchInvalidInfoSubcriberAsyncTask(getActivity(), lstisdn, new OnPostExecuteListener<List<SubInvalidDTO>>() {
            @Override
            public void onPostExecute(List<SubInvalidDTO> result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    if (CommonActivity.isNullOrEmpty(result)) {
                        listSubInvalidDTO = new ArrayList<>();
                        CommonActivity.createAlertDialog(getActivity(), CommonActivity.isNullOrEmpty(description) ?
                                        getActivity().getString(R.string.checkdes) : description,
                                getActivity().getString(R.string.app_name)).show();
                        return;
                    } else {
                        lnInfo.setVisibility(View.VISIBLE);
                        if ("1".equals(result.get(0).getStatus())) {
                            failInfo = true;
                        } else {
                            failInfo = false;
                        }
                    }

                    listSubInvalidDTO = result;
                } else {
                    listSubInvalidDTO = new ArrayList<>();
                    CommonActivity.createAlertDialog(getActivity(), CommonActivity.isNullOrEmpty(description) ?
                                    getActivity().getString(R.string.checkdes) : description,
                            getActivity().getString(R.string.app_name)).show();
                }
                adapterInvalidInfoSubcriber = new AdapterInvalidInfoSubcriber(getActivity(), listSubInvalidDTO, failInfo);
                lv.setAdapter(adapterInvalidInfoSubcriber);
//                adapterInvalidInfoSubcriber.notifyDataSetChanged();

            }
        }
                , moveLogInAct);
        searchInvalidInfoSubcriberAsyncTask.execute();


        // searh
    }
}
