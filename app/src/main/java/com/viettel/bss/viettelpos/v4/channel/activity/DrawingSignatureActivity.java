package com.viettel.bss.viettelpos.v4.channel.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;

import com.viettel.bss.viettelpos.v4.R;

import java.io.Serializable;

public class DrawingSignatureActivity extends AppCompatActivity implements Serializable {

    String staffName;
    View drawingSignatureView;
    ImageView usernameStringView;
    private Bitmap bitmapNameTemp;
    private Bitmap bitmapViewTemp;
    private Bitmap bitmapTempToPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_signature);
        drawingSignatureView = (View)findViewById(R.id.drawingSignatureView);
        Intent intent = getIntent();
        usernameStringView = (ImageView) findViewById(R.id.image_name);
        staffName = intent.getStringExtra("StaffName");
        drawName();
    }

    public void onClickBack(View v){
        finish();
    }

    public void onClickOk(View v){
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Bitmap bm = ((BitmapDrawable) usernameStringView.getDrawable()).getBitmap();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        Intent intent = new Intent(DrawingSignatureActivity.this, StaffInfoActivity.this);
//        intent.putExtra("SignatureImage", byteArray);
//        setResult(this.RESULT_OK, intent);
//        this.finish();
        bitmapViewTemp = getBitmapFromView(drawingSignatureView);
//        bitmapViewTemp = getResizedBitmap(bitmapNameTemp, 500);
//        Bitmap bm = ((BitmapDrawable) usernameStringView.getDrawable()).getBitmap();
        Intent intent = new Intent();
        intent.putExtra("SignatureImage", bitmapViewTemp);
        setResult(this.RESULT_OK, intent);
        this.finish();

    }

    //    draw image from text
    public Bitmap drawText(String text, int textWidth, int color) {

        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextSize(50);

        StaticLayout mTextLayout = new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.ARGB_8888);
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

    public void drawName(){
        //background color transparent
        int color=Color.TRANSPARENT;
//        int w = usernameStringView.getWidth();
        bitmapNameTemp=drawText(staffName, 800, color);
        usernameStringView.setImageBitmap(bitmapNameTemp);
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
