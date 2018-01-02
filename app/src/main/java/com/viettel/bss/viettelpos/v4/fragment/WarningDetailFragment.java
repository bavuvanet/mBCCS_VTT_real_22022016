package com.viettel.bss.viettelpos.v4.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.WarningDetailAdapter;
import com.viettel.bss.viettelpos.v4.object.WarningStaffBO;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 4/19/2017.
 */

public class WarningDetailFragment extends FragmentCommon{
    @BindView(R.id.tvTotalWarning)
    TextView tvTotalWarning;
    @BindView(R.id.lvWarning)
    RecyclerView lvWarning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.fragment_warning_detail;
    }

    @Override
    protected void unit(View v) {
        ArrayList<WarningStaffBO> lstWarningStaffBOs = (ArrayList<WarningStaffBO>)getArguments().getSerializable("lstWarningStaffBOs");
        WarningDetailAdapter adapter = new WarningDetailAdapter(getActivity(), lstWarningStaffBOs);

        lvWarning.setHasFixedSize(true);
        lvWarning.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvWarning.setAdapter(adapter);

        tvTotalWarning.setText(getString(R.string.totalWarning, lstWarningStaffBOs.size()));
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.warningIndicators);
    }
}
