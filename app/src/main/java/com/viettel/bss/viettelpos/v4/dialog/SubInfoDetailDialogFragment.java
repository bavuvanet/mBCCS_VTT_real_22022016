package com.viettel.bss.viettelpos.v4.dialog;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubscriberInfoAdapter;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubInfoDetailDialogFragment extends DialogFragment {

    @BindView(R.id.lv_accountList)
    ListView lv_accountList;
    @BindView(R.id.imgViewClose)
    ImageView imgViewClose;

    public static SubInfoDetailDialogFragment newInstance(List<SubscriberDTO> lstSubscriberDTOs){
        SubInfoDetailDialogFragment fragment = new SubInfoDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("lstSubscriberDTOs", (Serializable) lstSubscriberDTOs);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_info_detail_dialog, container, false);
        ButterKnife.bind(this, view);

        List<SubscriberDTO> lstSubscriberDTOs = (List<SubscriberDTO>)getArguments().getSerializable("lstSubscriberDTOs");
        SubscriberInfoAdapter subscriberInfoAdapter = new SubscriberInfoAdapter(getActivity(),
                lstSubscriberDTOs, null, null);
        lv_accountList.setAdapter(subscriberInfoAdapter);

        imgViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }


}
