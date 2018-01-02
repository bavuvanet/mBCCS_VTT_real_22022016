package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Customer;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by thuandq on 07/09/2017.
 */

public class EditImageIdFragment extends FragmentCommon {

    @BindView(R.id.txtChooseBefore)
    TextView txtChooseBefore;
    @BindView(R.id.txtChooseAfter)
    TextView txtChooseAfter;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.imageAfter)
    ImageView imageAfter;
    @BindView(R.id.imageBefore)
    ImageView imageBefore;
    @BindView(R.id.imageRotateRightBefore)
    ImageView imageRotateRightBefore;
    @BindView(R.id.imageRotateRightAfter)
    ImageView imageRotateRightAfter;
    @BindView(R.id.tvNameCus)
    TextView tvNameCus;
    @BindView(R.id.tvBirthDay)
    TextView tvBirthDay;
    @BindView(R.id.tvCmt)
    TextView tvCmt;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    private Bitmap bitmapAfter;
    private Bitmap bitmapBefore;
    private ConnectionOrder connectionOrder = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.edit_image_id_fragment;
        ButterKnife.bind(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void unit(View v) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            connectionOrder = (ConnectionOrder) bundle.getSerializable("connectionOrder");
            byte[] byteArrayBefore = bundle.getByteArray("bitmapBefore");
            byte[] byteArrayAfter = bundle.getByteArray("bitmapAfter");

            if (!CommonActivity.isNullOrEmpty(byteArrayBefore)) {
                bitmapBefore = getBitmapFromByteArray(byteArrayBefore);
                imageBefore.setImageBitmap(bitmapBefore);
            }

            if (!CommonActivity.isNullOrEmpty(byteArrayAfter)) {
                bitmapAfter = getBitmapFromByteArray(byteArrayAfter);
                imageAfter.setImageBitmap(bitmapAfter);
            }

            if(!CommonActivity.isNullOrEmpty(connectionOrder)) {
                fillCusInfo(connectionOrder);
            }
        }

        txtChooseAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupSelectImage(1235);
            }
        });

        txtChooseBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupSelectImage(1236);
            }
        });

        imageAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupSelectImage(1235);
            }
        });

        imageBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupSelectImage(1236);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            public static final int RESULT_OK = 0;
            @Override
            public void onClick(View v) {

                if (CommonActivity.isNullOrEmpty(bitmapBefore)) {
                    CommonActivity.toast(getActivity(), R.string.order_validate_cmt_mt);
                    return;
                }
                if (CommonActivity.isNullOrEmpty(bitmapAfter)) {
                    CommonActivity.toast(getActivity(), R.string.order_validate_cmt_ms);
                    return;
                }

                Intent intent = new Intent(getContext(), EditImageIdFragment.class);
                intent.putExtra("before", bitmapBefore);
                intent.putExtra("after", bitmapAfter);
                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                getFragmentManager().popBackStack();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        imageRotateRightBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapBefore != null) {
                    bitmapBefore = rotateBitmap(bitmapBefore, 90);
                    imageBefore.setImageBitmap(bitmapBefore);
//                    imageBefore.setImageBitmap(Bitmap.createScaledBitmap(bitmapBefore,
//                            imageBefore.getWidth(), imageBefore.getHeight(), false));
                }
            }
        });
        imageRotateRightAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapAfter != null) {
                    bitmapAfter = rotateBitmap(bitmapAfter, 90);
                    imageAfter.setImageBitmap(bitmapAfter);
                }
            }
        });
    }

    private void showPopupSelectImage(int requestCode) {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Chọn ảnh hoặc chụp mới";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        startActivityForResult(chooserIntent, requestCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.order_edit_cmt_image));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1235:
                    Bitmap bmp = getImage(data);
                    if (CommonActivity.isNullOrEmpty(bmp)) {
                        return;
                    }
                    imageAfter.setImageBitmap(bmp);
                    bitmapAfter = bmp;
                    break;
                case 1236:
                    bmp = getImage(data);
                    if (CommonActivity.isNullOrEmpty(bmp)) {
                        return;
                    }
                    imageBefore.setImageBitmap(bmp);
                    bitmapBefore = bmp;
                    break;
                default:
                    break;
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0,
                source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap getImage(Intent data) {
        Bitmap bmp = null;
        try {
            //Uri uri = data.getData();
            if (!CommonActivity.isNullOrEmpty(data.getData())) {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                bmp = BitmapFactory.decodeStream(inputStream);
            } else {
                Bundle extras = data.getExtras();
                bmp = (Bitmap) extras.get("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    private void fillCusInfo(ConnectionOrder connectionOrder) {
        Customer customer = connectionOrder.getCustomer();

        String idInfo = "";
        if (!CommonActivity.isNullOrEmpty(customer)) {
            idInfo += CommonActivity.isNullOrEmpty(customer.getIdNo()) ? ""
                    : customer.getIdNo() + "\n";
            idInfo += CommonActivity.isNullOrEmpty(customer.getIdIssueDate()) ? ""
                    : "Cấp ngày " + DateTimeUtils.getDateFromFullString(customer.getIdIssueDate());
            idInfo += CommonActivity.isNullOrEmpty(customer.getIdIssuePlace()) ? ""
                    : " tại " + customer.getIdIssuePlace();
            tvCmt.setText(idInfo);
            tvNameCus.setText(customer.getName());
            tvBirthDay.setText(DateTimeUtils.getDateFromFullString(customer.getBirthDate()));
            if (!CommonActivity.isNullOrEmpty(customer.getAddress()))
                tvAddress.setText(customer.getAddress().getFullAddress());
        }

        tvPhone.setText(connectionOrder.getRecipientPhone());
    }

    private Bitmap getBitmapFromByteArray(byte[] byteArray) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bmp;
    }
}
