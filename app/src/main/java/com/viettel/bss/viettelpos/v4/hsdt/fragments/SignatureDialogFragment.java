package com.viettel.bss.viettelpos.v4.hsdt.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.kyanogen.signatureview.SignatureView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.hsdt.listener.OnFinishSignature;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 07/09/2017.
 */

public class SignatureDialogFragment extends DialogFragment {

    Activity activity;
    @BindView(R.id.signature_view)
    SignatureView signatureView;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.imgBtnClose)
    ImageButton imgBtnClose;

    private View mView;
    private Bitmap bitmap;
    private OnFinishSignature onFinishSignature;
    private int index;

    public SignatureDialogFragment() {
        super();
        bitmap = null;
    }

    public SignatureDialogFragment(Bitmap currentBitmap) {
        super();
        bitmap = currentBitmap;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Override
    public void onResume() {
        super.onResume();
        signatureView.setBitmap(bitmap);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.signature_dialog_fragment, container, false);
            ButterKnife.bind(this, mView);
            activity = getActivity();
            unit(mView);
        }
        return mView;
    }

    protected void unit(View v) {

        if (!CommonActivity.isNullOrEmpty(bitmap)) {
            signatureView.setBitmap(bitmap);
        }

        this.index = 1;
        Bundle bundle = getArguments();
        if (bundle != null) {
            String indexSig = bundle.getString("index");
            if ("2".equals(indexSig)) {
                this.index = 2;
            } else {
                this.index = 1;
            }
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signatureView.isBitmapEmpty()) {
                    CommonActivity.toast(getActivity(), R.string.sig_not_null);
                    return;
                }
                bitmap = signatureView.getSignatureBitmap();
                onFinishSignature.onFinish(bitmap, index);
                getDialog().dismiss();
            }
        });

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setOnFinishSignature(OnFinishSignature onFinishSignature) {
        this.onFinishSignature = onFinishSignature;
    }
}
