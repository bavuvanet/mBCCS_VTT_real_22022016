package com.viettel.bss.viettelpos.v4.channel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.hsdt.listener.OnFinishSignature;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetStaffSignatureFile;
import com.viettel.bss.viettelpos.v4.task.AsyncTaskSetStaffSignatureFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static com.viettel.bss.viettelpos.v4.commons.Session.userName;

public class StaffInfoActivity extends AppCompatActivity {

    private ImageView signatureview;
    private TextView tvusername;
    private TextView tvstaffId;
    private static final String IMAGE_DIRECTORY = "/storage/emulated/0/DCIM/Camera";
    private int GALLERY = 1, CAMERA = 2;
    private Bitmap bitmapTempToPreview;
    private StaffSignatureDialogFragment signatureDialogFragment;

    private String type;
    private String staffSig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_info);

        tvusername = (TextView) findViewById(R.id.username);
        tvstaffId = (TextView) findViewById(R.id.staff_id);
        signatureview = (ImageView) findViewById(R.id.imageView);
        signatureDialogFragment = new StaffSignatureDialogFragment();

        if (Session.loginUser != null){
            tvusername.setText(Session.loginUser.getName());
            tvstaffId.setText("" + Session.loginUser.getStaffId());
        } else {
            Staff staff = StaffBusiness.getStaffByStaffCode(StaffInfoActivity.this,
                            Session.userName);
            if(staff != null){
                tvusername.setText(staff.getName());
                tvstaffId.setText(staff.getStaffId() + "");
            }
        }

        if (!CommonActivity.isNullOrEmpty(this.getIntent().getExtras())) {
            this.type = this.getIntent().getExtras().getString("type", "");
        } else {
            this.type = "";
        }

        this.staffSig = getSignatureStaff();
        if (CommonActivity.isNullOrEmpty(type)) {
            if (CommonActivity.isNullOrEmpty(staffSig)) {
                doGetSigStaff();
            } else {
                updateImageSig(staffSig);
            }
        }
    }

    private void doGetSigStaff() {
        new AsynTaskGetStaffSignatureFile(this, new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                if(!CommonActivity.isNullOrEmpty(result)){
                    try{
                        staffSig = result;
                        saveSignatureStaff(result);
                        updateImageSig(result);
                    } catch (Exception ex){
                        Log.d(Constant.TAG, "AsynTaskGetStaffSignatureFile", ex);
                    }
                } else {
                    CommonActivity.createAlertDialog(StaffInfoActivity.this,
                            R.string.sig_null, R.string.app_name).show();
                }
            }
        }, moveLogInAct, AsynTaskGetStaffSignatureFile.TYPE_GET_SIG).execute();
    }

    private void updateImageSig(String content) {
        byte[] fileByte = Base64.decode(content, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fileByte, 0, fileByte.length);
        signatureview.setImageBitmap(bitmap);
    }

    View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(StaffInfoActivity.this, "");
            dialog.show();
        }
    };

    public void onClickBack(View v){
        finish();
    }

    public void onClickToDrawing(View v){
        showSignatureFragment();
    }

    private void showSignatureFragment() {
        signatureDialogFragment.setOnFinishSignature(onFinishSignature);
        signatureDialogFragment.show(getSupportFragmentManager(), "show sig");
    }

    private OnFinishSignature onFinishSignature = new OnFinishSignature() {
        @Override
        public void onFinish(Bitmap bitmap) {
            signatureview.setImageBitmap(bitmap);
        }

        public void onFinish(Bitmap bitmap, int index) {
            // do nothing
        }
    };

    //  cap nhat bang chup anh
    public void onClickFromGalery(View v) {
        showPictureDialog();
    }

    private void showPictureDialog(){

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Tạo chữ kí mới");
        String[] pictureDialogItems = {
                "Chọn ảnh từ thư viện",
                "Chụp ảnh bằng camera",
                "Kí trực tiếp"};

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                showSignatureFragment();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap = getResizedBitmap(bitmap, 1024);
                    Toast.makeText(StaffInfoActivity.this, "Đã lấy từ thư viện!", Toast.LENGTH_SHORT).show();
                    signatureview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(StaffInfoActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            thumbnail = getResizedBitmap(thumbnail, 1024);
            signatureview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(StaffInfoActivity.this, "Đã chụp!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, Constant.DEFAULT_JPEG_QUALITY, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void onClickSave(View v){

        View.OnClickListener okClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskSetStaffSignatureFile setSignature = new AsyncTaskSetStaffSignatureFile(
                        StaffInfoActivity.this,
                        new OnPostExecuteListener<String>() {

                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            saveSignatureStaff(result);
                            Toast.makeText(StaffInfoActivity.this,
                                    "Lưu chữ ký thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StaffInfoActivity.this,
                                    description, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, moveLogInAct);
                bitmapTempToPreview = ((BitmapDrawable) signatureview.getDrawable()).getBitmap();
                setSignature.execute(bitmapTempToPreview);
            }
        };

        CommonActivity.createDialog(this,
                "Bạn chắc chắn muốn đổi chữ kí mới?",
                "Đổi chữ kí",
                getString(R.string.say_ko),
                getString(R.string.say_co), null, okClickListener).show();
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
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
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

    private String getSignatureStaff() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, this.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.SIGNATURE_STAFF_SAVE, "");
    }

    private void saveSignatureStaff(String contentSig) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, this.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.SIGNATURE_STAFF_SAVE, contentSig);
        edit.commit();
    }
}