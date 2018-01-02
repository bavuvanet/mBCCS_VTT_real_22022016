package com.viettel.bss.viettelpos.v4.channel.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.viettel.bss.viettelpos.v4.R;

public class PreviewSignatureActivity extends AppCompatActivity {

    ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_signature);
        Intent intent = getIntent();
        String receiveimage = intent.getStringExtra("BitmapImage");
        byte[] bitmapdata = Base64.decode(receiveimage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        preview = (ImageView)findViewById(R.id.signaturePreview);
        preview.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
