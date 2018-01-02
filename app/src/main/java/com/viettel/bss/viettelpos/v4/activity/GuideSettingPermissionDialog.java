package com.viettel.bss.viettelpos.v4.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.adapter.SubWarningInfoAdapter;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/22/2017.
 */

public class GuideSettingPermissionDialog extends Dialog {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private Context context;
    private Button btOk;
    private ViewFlipper viewflipper;
    private View.OnClickListener onClickListener;

    private final GestureDetector detector =
            new GestureDetector(context, new SwipeGestureDetector());

    public GuideSettingPermissionDialog(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.onClickListener = onClickListener;

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide_setting_permission_dialog);

        this.btOk = (Button) findViewById(R.id.btOk);
        this.viewflipper = (ViewFlipper) findViewById(R.id.viewflipper);

        viewflipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                viewflipper.setAutoStart(false);
                viewflipper.stopFlipping();
                detector.onTouchEvent(event);
                return true;
            }
        });

        this.btOk.setOnClickListener(onClickListener);
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewflipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.image_in_right));
                    viewflipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.image_out_left));
                    viewflipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewflipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.image_in_left));
                    viewflipper.setOutAnimation(AnimationUtils.loadAnimation(context,R.anim.image_out_right));
                    viewflipper.showPrevious();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}