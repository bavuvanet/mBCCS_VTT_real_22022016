package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListProblemProcessAdapter;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.ProblemHistoryAdapter;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.object.ProblemHistory;
import com.viettel.bss.viettelpos.v4.object.ProblemProcessDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.task.AsynTaskFindProblem;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListProblemProcess;

import java.util.List;

import butterknife.BindView;

/**
 * Created by toancx on 2/22/2017.
 */

public class ListComplainFragment extends FragmentCommon{
    @BindView(R.id.rvListComplain)
    RecyclerView rvListComplain;

    ProblemHistoryAdapter problemHistoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_list_complain;
    }


    @Override
    public void onResume() {
        setTitleActionBar(R.string.txt_lookup_complain);
        super.onResume();
    }

    @Override
    protected void unit(View v) {
        Bundle bundle = getArguments();
        if(bundle != null){
            SubscriberDTO subscriberDTO = (SubscriberDTO) bundle.get("subscriberDTO");
            String fromDate = String.valueOf(bundle.get("fromDate"));
            String toDate = String.valueOf(bundle.get("toDate"));

            new AsynTaskFindProblem(getActivity(), new OnPostFindProblem(), moveLogInAct)
                    .execute(fromDate + " 00:00:00", toDate + " 23:59:59",
                            subscriberDTO.getIsdn(), subscriberDTO.getSubId());
        }

        rvListComplain.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvListComplain, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ProblemHistory problemHistory = problemHistoryAdapter.getLstData().get(position);
                new AsynTaskGetListProblemProcess(getActivity(), new OnPostGetListProblemProcess(), moveLogInAct)
                        .execute(problemHistory.getProblemId());
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private class OnPostGetListProblemProcess implements OnPostExecuteListener<List<ProblemProcessDTO>> {
        @Override
        public void onPostExecute(List<ProblemProcessDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    showComplainDetail(result);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    private void showComplainDetail(List<ProblemProcessDTO> lstData){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_item_complain_detail);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        // This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        RecyclerView rvComplainDetail = (RecyclerView) dialog.findViewById(R.id.rvComplainDetail);
        rvComplainDetail.setVisibility(View.VISIBLE);
        rvComplainDetail.setHasFixedSize(true);
        rvComplainDetail.setLayoutManager(new LinearLayoutManager(getActivity()));

        ListProblemProcessAdapter adapter = new ListProblemProcessAdapter(getActivity(), lstData);
        rvComplainDetail.setAdapter(adapter);

        Button btnOke = (Button) dialog.findViewById(R.id.btnOke);
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void setPermission() {

    }

    private class OnPostFindProblem implements OnPostExecuteListener<List<ProblemHistory>> {
        @Override
        public void onPostExecute(List<ProblemHistory> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    rvListComplain.setVisibility(View.VISIBLE);
                    rvListComplain.setHasFixedSize(true);
                    rvListComplain.setLayoutManager(new LinearLayoutManager(getActivity()));

                    problemHistoryAdapter = new ProblemHistoryAdapter(getActivity(), result);
                    rvListComplain.setAdapter(problemHistoryAdapter);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }
}
