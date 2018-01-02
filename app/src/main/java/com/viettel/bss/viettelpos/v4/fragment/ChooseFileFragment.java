package com.viettel.bss.viettelpos.v4.fragment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.dialog.DialogSelectProfileFragment;
import com.viettel.bss.viettelpos.v4.listener.OnImageClickListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ChooseFileFragment extends FragmentCommon {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvUploadImage)
    ListView lvUploadImage;
    @BindView(R.id.btnAccept)
    Button btnAccept;

    ProfileBO input;
    RecordPrepaidAdapter adapter;
    RecordPrepaid recordPrepaid;
    int imgId;
    List<ArrayList<RecordPrepaid>> lstData;
    Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;
    HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<String, ArrayList<FileObj>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.fragment_choose_file;
    }

    @Override
    protected void unit(View v) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            input = (ProfileBO) bundle.getSerializable("input");
            AsynTaskGetListRecordProfile asynTask = new AsynTaskGetListRecordProfile(getActivity(), onGetListRecordListener, moveLogInAct, input);
            asynTask.execute();
        }
    }

    @Override
    protected void setPermission() {

    }

    OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>> onGetListRecordListener = new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
        @Override
        public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
            mapListRecordPrepaid = result;

            List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
                    mapListRecordPrepaid.values());
            adapter = new RecordPrepaidAdapter(getActivity(), arrayOfArrayList, imageListenner);
            lvUploadImage.setAdapter(adapter);

            UI.setListViewHeightBasedOnChildren(lvUploadImage);
        }
    };

    OnImageClickListener imageListenner = new OnImageClickListener() {
        @Override
        public void onClick(RecordPrepaid item, Integer imageId) {
            Log.d(Constant.TAG, "view.getId() : " + imageId);
            recordPrepaid = item;
            imgId = imageId;

            if("1".equals(item.getAlowReuser())){ //truong hop chung tu cho phep su dung lai ho so cu
//                DialogSelectProfileFragment fragment = new DialogSelectProfileFragment(hsCuListener, hsMoiListener, on);
//                fragment.show(getFragmentManager(), "Select profile");
            } else {
                ImagePreviewActivity.pickImage(getActivity(), hashmapFileObj, imageId);
            }
        }

        @Override
        public void onClickNew(RecordTypeScanDTO item, Integer imageId) {

        }
    };

    View.OnClickListener hsCuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener hsMoiListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImagePreviewActivity.pickImage(getActivity(), hashmapFileObj, imgId);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Constant.TAG, "ChooseFileFragment onActivityResult requestCode : " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");
                    Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                    if (parcelableUris == null) {
                        return;
                    }

                    // Java doesn't allow array casting, this is a little hack
                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                    int imageId = data.getExtras().getInt("imageId", -1);
                    Log.d(Constant.TAG, "ChooseFileFragment onActivityResult() imageId: " + imageId);
                    if (uris.length > 0 && imageId >= 0) {
                        RecordPrepaidAdapter.ViewHolder holder = null;
                        for (int i = 0; i < lvUploadImage.getChildCount(); i++) {
                            View rowView = lvUploadImage.getChildAt(i);
                            RecordPrepaidAdapter.ViewHolder h = (RecordPrepaidAdapter.ViewHolder) rowView.getTag();
                            if (h != null) {
                                int id = h.ibUploadImage.getId();
                                if (imageId == id) {
                                    holder = h;
                                    break;
                                }
                            }
                        }

                        if (holder != null) {
                            Picasso.with(getActivity()).load(new File(uris[0].toString())).centerCrop().resize(100, 100)
                                    .into(holder.ibUploadImage);

                            int position = holder.spUploadImage.getSelectedItemPosition();
                            if (position < 0) {
                                position = 0;
                            }

                            ArrayList<RecordPrepaid> recordPrepaids = mapListRecordPrepaid.get(String.valueOf(imageId));

                            if (recordPrepaids != null) {
                                RecordPrepaid recordPrepaid = recordPrepaids.get(position);
                                String spinnerCode = recordPrepaid.getCode();

                                Log.i(Constant.TAG, "imageId: " + imageId + " spinner position: " + position
                                        + " spinnerCode: " + spinnerCode + " uris: " + uris.length);

                                ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
                                for (int i = 0; i < uris.length; i++) {
                                    File uriFile = new File(uris[i].getPath());
                                    String fileNameServer = spinnerCode + "-" + (i + 1) + ".jpg";
                                    FileObj obj = new FileObj(spinnerCode, uriFile, uris[i].getPath(), fileNameServer);
                                    fileObjs.add(obj);
                                }
                                hashmapFileObj.put(String.valueOf(imageId), fileObjs);
                            } else {
                                Log.d(Constant.TAG,
                                        "ChooseFileFragment onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
                                                + mapListRecordPrepaid.size() + " " + mapListRecordPrepaid);
                            }
                        } else {
                            Log.d(Constant.TAG, "ChooseFileFragment onActivityResult() holder NULL");
                        }
                    } else {
                        Log.d(Constant.TAG, "ChooseFileFragment onActivityResult() uris NULL");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
