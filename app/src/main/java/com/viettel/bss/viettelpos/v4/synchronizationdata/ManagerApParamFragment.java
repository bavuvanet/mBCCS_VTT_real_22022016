package com.viettel.bss.viettelpos.v4.synchronizationdata;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.synchronizationdata.adapter.ApParamAdapter;
import com.viettel.bss.viettelpos.v4.synchronizationdata.asynctask.AsyncTaskSaveOrUpdate;
import com.viettel.bss.viettelpos.v4.synchronizationdata.asynctask.AsyncTaskSearchApParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hantt47 on 8/29/2017.
 */

public class ManagerApParamFragment extends FragmentCommon {

    private EditText editParamName, editParamType, editParamValue;
    private Button btnSearchApApram, btnCreateApParam;
    private Spinner spnStatus;
    private RecyclerView listApParam;
    private Dialog dialogApParam;
    private ApParamBO apParamBO;
    private List<ApParamBO> apParamBOList;
    private int status = -1;
    ArrayList<String> lstStatus;
    ApParamAdapter apParamAdapter;
    Boolean isUpdate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idLayout = R.layout.manager_ap_param_fragment;
        super.onCreateView(inflater, container, savedInstanceState);
        return mView;
    }

    @Override
    protected void unit(View v) {
        editParamName = (EditText) v.findViewById(R.id.editParamName);
        editParamType = (EditText) v.findViewById(R.id.editParamType);
        editParamValue = (EditText) v.findViewById(R.id.editParamValue);
        spnStatus = (Spinner) v.findViewById(R.id.spinner);
        btnSearchApApram = (Button) v.findViewById(R.id.btnSearchApApram);
        btnCreateApParam = (Button) v.findViewById(R.id.btnCreateApParam);
        listApParam = (RecyclerView) v.findViewById(R.id.listApParam);
        listApParam.setHasFixedSize(true);
        listApParam.setLayoutManager(new LinearLayoutManager(getActivity()));
        apParamAdapter = new ApParamAdapter(new ArrayList<ApParamBO>(), getContext());
        listApParam.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), listApParam, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ApParamBO paramBO = apParamBOList.get(position);
                showDiaLogApParam(paramBO);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        lstStatus = new ArrayList(Arrays.asList("--Chọn trạng thái--",
                getString(R.string.status_action), getString(R.string.status_not_action)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1, lstStatus);
        spnStatus.setAdapter(adapter);

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = lstStatus.get(position);
                if (getString(R.string.status_not_action).equals(select)) {
                    status = 0;
                } else if (getString(R.string.status_action).equals(select)) {
                    status = 1;
                } else {
                    status = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnCreateApParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogApParam(null);
            }
        });

        btnSearchApApram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchApParam();
            }
        });

    }

    @Override
    protected void setPermission() {

    }

    private void searchApParam() {
        ApParamBO paramBO = getparam(true);
        if (!CommonActivity.isNullOrEmpty(paramBO)) {
            AsyncTaskSearchApParam asyncTaskSearchApParam = new AsyncTaskSearchApParam(getActivity(), new OnPostSuccessExecute<List<ApParamBO>>() {
                @Override
                public void onPostSuccess(List<ApParamBO> result) {
                    if (CommonActivity.isNullOrEmpty(result)) {
                        apParamBOList = new ArrayList<ApParamBO>();
                        if (!isUpdate)
                            CommonActivity.showConfirmValidate(getActivity(), R.string.no_result_ap_param);
                    } else {
                        Collections.sort(result, new Comparator<ApParamBO>() {
                            @Override
                            public int compare(ApParamBO o1, ApParamBO o2) {
                                return o1.getId().compareTo(o2.getId());
                            }
                        });
                        apParamBOList = result;
                    }
                    apParamAdapter = new ApParamAdapter(apParamBOList, getContext());
                    listApParam.setAdapter(apParamAdapter);
                }
            }, moveLogInAct);
            asyncTaskSearchApParam.execute(paramBO);
        }
    }

    private void showDiaLogApParam(ApParamBO apParamBO) {

        dialogApParam = new Dialog(getActivity());
        dialogApParam.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogApParam.setContentView(R.layout.ap_param_dialog);
        dialogApParam.setCancelable(false);


        LinearLayout llIdApParam = (LinearLayout) dialogApParam.findViewById(R.id.llIdApParam);
        final EditText editId = (EditText) dialogApParam.findViewById(R.id.editId);
        final EditText editName = (EditText) dialogApParam.findViewById(R.id.editName);
        final EditText editType = (EditText) dialogApParam.findViewById(R.id.editType);
        final EditText editValue = (EditText) dialogApParam.findViewById(R.id.editValue);
        final EditText editDescription = (EditText) dialogApParam.findViewById(R.id.editDescription);
        final Spinner spnStatus = (Spinner) dialogApParam.findViewById(R.id.spnStatus);
        Button btCreate = (Button) dialogApParam.findViewById(R.id.btCreate);
        Button btnCancel = (Button) dialogApParam.findViewById(R.id.btnCancel);


        if (CommonActivity.isNullOrEmpty(apParamBO)) {
            apParamBO = getparam(false);
        }
        // fill data
        if (!CommonActivity.isNullOrEmpty(apParamBO)) {
            if (!CommonActivity.isNullOrEmptyArray(apParamBO.getId())) {
                llIdApParam.setVisibility(View.VISIBLE);
                editId.setHint(apParamBO.getId() + "");
                editId.setEnabled(false);
                btCreate.setText(getString(R.string.button_update));
            } else {
                btCreate.setText(getString(R.string.themmoi));
                llIdApParam.setVisibility(View.GONE);
            }
            editName.setText(apParamBO.getParName());
            editType.setText(apParamBO.getParType());
            editValue.setText(apParamBO.getParValue());
            editDescription.setText(apParamBO.getDescription());
        } else {
            btCreate.setText(getString(R.string.themmoi));
            llIdApParam.setVisibility(View.GONE);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1, lstStatus);
        spnStatus.setAdapter(adapter);
        if (!CommonActivity.isNullOrEmpty(apParamBO) && !CommonActivity.isNullOrEmpty(apParamBO.getStatus())) {

            if (apParamBO.getStatus() == 0) {
                spnStatus.setSelection(2);
            } else if (apParamBO.getStatus() == 1) {
                spnStatus.setSelection(1);
            } else {
                spnStatus.setSelection(0);
            }
        } else {
            spnStatus.setTop(0);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogApParam.dismiss();
            }
        });


        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = CommonActivity.isNullOrEmpty(editId.getHint()) ? "" : editId.getHint().toString();
                String type = editType.getText().toString();
                String name = editName.getText().toString();
                String value = editValue.getText().toString();
                String desc = editDescription.getText().toString();
                String stats = spnStatus.getSelectedItem().toString();
                if (CommonActivity.isNullOrEmptyArray(name)) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_name);
                } else if (CommonActivity.isNullOrEmpty(type)) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_type);
                } else if (CommonActivity.isNullOrEmpty(value)) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_value);
                } else {
                    if (name.trim().getBytes().length > 30) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_max_name);
                        return;
                    }
                    if (type.trim().getBytes().length > 30) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_max_type);
                        return;
                    }
                    if (value.trim().getBytes().length > 400) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_max_value);
                        return;
                    }
                    if (desc.trim().getBytes().length > 200) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm_max_desc);
                        return;
                    }

                    final ApParamBO paramBO = new ApParamBO();
                    paramBO.setParName(name);
                    paramBO.setParType(type);
                    paramBO.setParValue(value);
                    if (!CommonActivity.isNullOrEmpty(id)) {
                        paramBO.setId(Long.valueOf(id));
                    }
                    if (!CommonActivity.isNullOrEmpty(desc)) {
                        paramBO.setDescription(desc);
                    }
                    if (!CommonActivity.isNullOrEmpty(stats)) {
                        if (getString(R.string.status_not_action).equals(stats)) {
                            paramBO.setStatus(0);
                        } else if (getString(R.string.status_action).equals(stats)) {
                            paramBO.setStatus(1);
                        } else {
                            CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_require_status);
                            return;
                        }
                    }
                    String confirm = getString(R.string.message_confirm_update);
                    if (CommonActivity.isNullOrEmpty(paramBO.getId()))
                        confirm = getString(R.string.confirm_create);
                    CommonActivity.createDialog(
                            getActivity(),
                            confirm,
                            getResources().getString(
                                    R.string.app_name),
                            getResources().getString(R.string.say_ko),
                            getResources().getString(R.string.say_co),
                            null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AsyncTaskSaveOrUpdate asyncTaskSaveOrUpdate = new AsyncTaskSaveOrUpdate(getActivity(), new OnPostSuccessExecute<String>() {
                                        @Override
                                        public void onPostSuccess(String result) {
                                            if ("0".equals(result)) {
                                                isUpdate = true;
                                                if (!CommonActivity.isNullOrEmpty(paramBO.getId())) {
                                                    CommonActivity.showConfirmValidate(getActivity(), R.string.update_success);
                                                    dialogApParam.dismiss();
                                                } else {
                                                    CommonActivity.showConfirmValidate(getActivity(), R.string.create_success);
                                                }
                                                searchApParam();
                                            }
                                        }
                                    }, moveLogInAct);
                                    asyncTaskSaveOrUpdate.execute(paramBO);
                                }
                            }).show();
                }

            }
        });
        dialogApParam.show();
    }


    private ApParamBO getparam(Boolean isShowValidate) {
        String name = editParamName.getText().toString();
        String type = editParamType.getText().toString();
        String value = editParamValue.getText().toString();
        String stats = spnStatus.getSelectedItem().toString();
        if (CommonActivity.isNullOrEmptyArray(name) && CommonActivity.isNullOrEmpty(type) &&
                CommonActivity.isNullOrEmpty(value) && CommonActivity.isNullOrEmpty(stats) && status != -1) {
            if (isShowValidate)
                CommonActivity.showConfirmValidate(getActivity(), R.string.ap_param_validate_search_confirm);
            return null;
        }
        ApParamBO paramBO = new ApParamBO();
        paramBO.setParName(name);
        paramBO.setParType(type);
        paramBO.setParValue(value);
        if (status != -1)
            paramBO.setStatus(status);
        return paramBO;
    }


}
