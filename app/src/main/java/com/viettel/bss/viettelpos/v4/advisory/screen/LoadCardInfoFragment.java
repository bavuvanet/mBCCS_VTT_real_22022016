package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.adapter.RefillInfoAdapter;
import com.viettel.bss.viettelpos.v4.advisory.data.RefillInfoData;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;

import java.util.ArrayList;

/**
 * Created by admin_pmvt on 7/3/2017.
 */

public class LoadCardInfoFragment extends Fragment {

    private ListView lvLoadCard;
    private TextView tvNoData;
    private TableRow tbrHeader;

    private CCOutput ccOutput;

    public LoadCardInfoFragment(CCOutput ccOutput) {
        super();
        this.ccOutput = ccOutput;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.load_card_info_fragment, container, false);

        this.lvLoadCard = (ListView) view.findViewById(R.id.lvLoadCard);
        this.tvNoData = (TextView) view.findViewById(R.id.tvNoData);
        this.tbrHeader = (TableRow) view.findViewById(R.id.tbrHeader);

        // list view vas:
        if (ccOutput.getLstRefillInfos().size() > 0) {
            ArrayList<RefillInfoData> refillInfoDatas = new ArrayList<>(ccOutput.getLstRefillInfos());
            RefillInfoAdapter refillInfoAdapter = new RefillInfoAdapter(
                    refillInfoDatas, getActivity());
            this.lvLoadCard.setAdapter(refillInfoAdapter);

            this.tvNoData.setVisibility(View.GONE);
            this.tbrHeader.setVisibility(View.VISIBLE);
        } else {
            this.tvNoData.setVisibility(View.VISIBLE);
            this.tbrHeader.setVisibility(View.GONE);
        }

        this.lvLoadCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //DataModel dataModel= dataModels.get(position);
            }
        });

        return view;
    }
}