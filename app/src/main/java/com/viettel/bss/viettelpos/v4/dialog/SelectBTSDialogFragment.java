package com.viettel.bss.viettelpos.v4.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.LstBTSAdapter;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Toancx on 6/9/2017.
 */
public class SelectBTSDialogFragment extends DialogFragment {
    private List<StationBO> lstStation = new ArrayList<>();
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtSearchBTS)
    EditText edtSearchBTS;
    LstBTSAdapter adapter;
    OnItemClickListener onItemClickListener;

    public static SelectBTSDialogFragment newInstance(List<StationBO> lstBTS) {
        SelectBTSDialogFragment fragment = new SelectBTSDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("lstStation", (Serializable) lstBTS);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static List<StationBO> convertToStationBO(List<String> lstBTS) {
        List<StationBO> lstStationBOs = new ArrayList<>();
        for (String bts : lstBTS) {
            StationBO stationBO = new StationBO();
            stationBO.setName(bts);
            lstStationBOs.add(stationBO);
        }
        return lstStationBOs;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_select_bts, container, false);
        ButterKnife.bind(this, v);

        lstStation = (List<StationBO>) getArguments().getSerializable("lstStation");
        adapter = new LstBTSAdapter(getActivity(), lstStation, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                initCheckBoxAll(lstStation);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (StationBO stationBO : lstStation) {
                    stationBO.setIsSelected(checkBox.isChecked());
                }
                adapter.notifyDataSetChanged();
            }
        });

        edtSearchBTS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String btsCode = s.toString().toUpperCase().trim();
                if(CommonActivity.isNullOrEmpty(btsCode)){
                    adapter = new LstBTSAdapter(getActivity(), lstStation, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object item) {
                            initCheckBoxAll(lstStation);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    return;
                }

                final List<StationBO> lstStationTmp = new ArrayList<StationBO>();
                if(!CommonActivity.isNullOrEmptyArray(lstStation)){
                    for(StationBO stationBO : lstStation){
                        if(stationBO.getName().toUpperCase().contains(btsCode)){
                            lstStationTmp.add(stationBO);
                        }
                    }

                }

                adapter = new LstBTSAdapter(getActivity(), lstStationTmp, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        initCheckBoxAll(lstStation);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        setCancelable(false);
        initCheckBoxAll(lstStation);
        return v;
    }

    private void initCheckBoxAll(List<StationBO> lstStation){
        Boolean isAll = true;
        if(!CommonActivity.isNullOrEmptyArray(lstStation)){
            for(StationBO stationBO : lstStation){
                if(!stationBO.getIsSelected()){
                    isAll = false;
                    break;
                }
            }
        }

        checkBox.setChecked(isAll);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @OnClick(R.id.btnAccept)
    public void btnAcceptOnClick() {
        getDialog().dismiss();
        onItemClickListener.onItemClick(lstStation);
    }
}
