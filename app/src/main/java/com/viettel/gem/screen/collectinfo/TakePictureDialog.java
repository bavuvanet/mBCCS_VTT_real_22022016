package com.viettel.gem.screen.collectinfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


import com.viettel.bss.viettelpos.v4.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BaVV on 11/23/17.
 */

public class TakePictureDialog extends Dialog {
    private GetAvatarListener listener;

    public TakePictureDialog setListener(GetAvatarListener listener) {
        this.listener = listener;
        return this;
    }

    public TakePictureDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_take_picture);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }

    @Override
    public void show() {
//        Window window = getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.BOTTOM;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);
        super.show();
    }

    @OnClick(R.id.from_camera)
    public void fromCam() {
        if (listener != null) listener.fromCam();
        dismiss();
    }

    @OnClick(R.id.from_galary)
    public void fromGal() {
        if (listener != null) listener.fromGal();
        dismiss();
    }

    public interface GetAvatarListener {
        void fromCam();

        void fromGal();
    }
}
