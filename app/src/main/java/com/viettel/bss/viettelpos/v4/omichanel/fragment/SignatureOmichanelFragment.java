package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kyanogen.signatureview.SignatureView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 07/09/2017.
 */

public class SignatureOmichanelFragment extends FragmentCommon {
    Activity activity;
    @BindView(R.id.signature_view)
    SignatureView signatureView;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.save)
    Button save;

    Bitmap bitmap;
    String path;
    private static final String IMAGE_DIRECTORY = "/signature";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.layout_signature_omichanel_fragment;
        ButterKnife.bind(getActivity());
        activity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void unit(View v) {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                Intent intent = new Intent(getContext(), DetailOrderOmniFragment.class);
                intent.putExtra("bitmap", bitmap);
                getTargetFragment().onActivityResult(getTargetRequestCode(),
                        DetailOrderOmniFragment.RESULT_OK, intent);
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected void setPermission() {

    }
}
