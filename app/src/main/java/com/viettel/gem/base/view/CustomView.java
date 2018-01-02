package com.viettel.gem.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

/**
 * Created by BaVV on 11/21/17.
 */
public abstract class CustomView
    extends LinearLayout {

    public CustomView(Context context) {
        super(context);

        injectView(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        injectView(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        injectView(context);
    }

    protected abstract int getLayoutId();

    public abstract boolean validateView();

    protected void initUI() {
    }

    private void injectView(Context context) {
        View view = inflateView(context);
        ButterKnife.bind(this, view);

        initUI();
    }

    private View inflateView(Context context) {
        return inflate(context, getLayoutId(), this);
    }
}
