package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.hsdt.listener.OnFinishSignature;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinhhq1 on 10/24/2017.
 */
public class StaffSignatureDialogFragment extends DialogFragment {
    Activity activity;
    @BindView(R.id.signature_view)
    SignatureView signatureView;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.save)
    Button save;

    Bitmap bitmap;
    String staffName;
    View mView;

    OnFinishSignature onFinishSignature;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_staff_signature_fragment, container, false);
            ButterKnife.bind(this, mView);
            activity = getActivity();

            unit(mView);

            if (!CommonActivity.isNullOrEmpty(Session.loginUser))
                staffName = Session.loginUser.getName();
            else
                staffName = "Chưa có tên";
        }else{
            ((ViewGroup) mView.getParent()).removeAllViews();
        }
        return mView;
    }

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
                Bitmap fromSignatureView = signatureView.getSignatureBitmap();
                Bitmap emptyBitmap = Bitmap.createBitmap(fromSignatureView.getWidth(), fromSignatureView.getHeight(), fromSignatureView.getConfig());
                if (fromSignatureView.sameAs(emptyBitmap)) {
                    // myBitmap is empty/blank
                    Toast.makeText(v.getContext(), getString(R.string.sig_not_null),
                            Toast.LENGTH_LONG).show();
                } else {
                    Bitmap drawString = drawText(staffName, Color.TRANSPARENT);
                    bitmap = mergeTwoBitmap(fromSignatureView, drawString);
                    onFinishSignature.onFinish(bitmap);
                    getDialog().dismiss();
                }
            }
        });
    }

    public OnFinishSignature getOnFinishSignature() {
        return onFinishSignature;
    }

    public void setOnFinishSignature(OnFinishSignature onFinishSignature) {
        this.onFinishSignature = onFinishSignature;
    }

    //    draw image from text
    public Bitmap drawText(String text, int color) {
        Rect bounds = new Rect();
        Paint tpaint = new Paint();
        tpaint.setTextSize(250);
        tpaint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.width();

        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextSize(230);
        textPaint.setLinearText(true);

//        int width = textPaint.measureText(text);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(width, mTextLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        // Draw background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        c.drawPaint(paint);

        // Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }

    public Bitmap mergeTwoBitmap(Bitmap top, Bitmap bottom){
        int vw = top.getWidth();
        int vh = top.getHeight();
        int sw = bottom.getWidth();
        int sh = bottom.getHeight();
        int bitmapWidth;
        if (sw<vw)
            bitmapWidth = vw;
        else
            bitmapWidth = sw;

        Bitmap bmOverlay = Bitmap.createBitmap(bitmapWidth, vh+sh, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmOverlay);
        if (sw<vw){
            canvas.drawBitmap(top, 0, 0, null);
            canvas.drawBitmap(bottom, (vw-sw)/2, vh, null);
        }
        else {
            canvas.drawBitmap(top, (sw-vw)/2, 0, null);
            canvas.drawBitmap(bottom, 0, vh, null);
        }
        return bmOverlay;
    }
}
