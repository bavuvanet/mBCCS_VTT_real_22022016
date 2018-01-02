package com.viettel.bss.viettelpos.v4.hsdt.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoOmniFragment;
import com.viettel.bss.viettelpos.v4.listener.OnImageClickListener;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.AsyncTaskGetImageOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 10/19/2017.
 */

public class ProfileOmniAdapter extends RecyclerView.Adapter<ProfileOmniAdapter.ViewHolder> {

    private Activity activity;
    private List<ArrayList<RecordTypeScanDTO>> arrayOfArrayList;
    private OnImageClickListener imageListenner;
    private ProfileInfoOmniFragment profileInfoOmniFragment;
    private View.OnClickListener moveLogInAct;

    public ProfileOmniAdapter(Activity activity,
                              List<ArrayList<RecordTypeScanDTO>> arrayOfArrayList,
                              OnImageClickListener imageListenner,
                              ProfileInfoOmniFragment profileInfoOmniFragment,
                              View.OnClickListener moveLogInAct) {

        this.activity = activity;
        this.arrayOfArrayList = arrayOfArrayList;
        this.imageListenner = imageListenner;
        this.profileInfoOmniFragment = profileInfoOmniFragment;
        this.moveLogInAct = moveLogInAct;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.btnViewPdf.setVisibility(View.GONE);

        final ArrayList<RecordTypeScanDTO> recordTypeScanDTOs = arrayOfArrayList.get(position);

        final int imageId = (int) recordTypeScanDTOs.get(0).getSourceId().longValue();

        Utils.setDataSpinner(activity, recordTypeScanDTOs, holder.spUploadImage);
        final int spnPosition = getPositionSelected(recordTypeScanDTOs);
        if (spnPosition > 0) {
            holder.spUploadImage.setSelection(getPositionSelected(recordTypeScanDTOs));
        }
        holder.spUploadImage.setId(imageId);
        holder.ibUploadImage.setId(imageId);
        holder.ibUploadImage.setImageResource(R.drawable.ic_cam_hsdt);

        //hien thi anh da co
        for (RecordTypeScanDTO recordTypeScanDTO : recordTypeScanDTOs) {
            if (!CommonActivity.isNullOrEmpty(recordTypeScanDTO.getProfileRecordMap())) {

                final ProfileRecord profileRecord = recordTypeScanDTO.getProfileRecordMap()
                        .get(recordTypeScanDTO.getRecordCode());

                if (!CommonActivity.isNullOrEmpty(profileRecord)
                        && !CommonActivity.isNullOrEmpty(profileRecord.getUrl())) {
                    ArrayList<ProfileRecord> profileRecords = new ArrayList<ProfileRecord>();
                    profileRecords.add(new ProfileRecord(profileRecord.getCode(), profileRecord.getUrl()));
                    AsyncTaskGetImageOrder asyncTaskGetImageOrder = new AsyncTaskGetImageOrder(
                            profileRecords, activity, new OnPostExecuteListener<List<ProfileRecord>>() {
                        @Override
                        public void onPostExecute(List<ProfileRecord> result, String errorCode, String description) {
                            if (!CommonActivity.isNullOrEmpty(result)) {
                                processShowIcon(result, profileRecord, holder);
                            }
                        }
                    }, moveLogInAct);
                    asyncTaskGetImageOrder.execute();
                }
            }
        }

        holder.ibUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordTypeScanDTO recordTypeScanDTO = (RecordTypeScanDTO) holder.spUploadImage.getSelectedItem();
                imageListenner.onClickNew(recordTypeScanDTO, imageId);
            }
        });

        holder.btnViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordTypeScanDTO record = (RecordTypeScanDTO) holder.spUploadImage.getSelectedItem();
                profileInfoOmniFragment.doViewPdfFile(record);
            }
        });

        holder.spUploadImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RecordTypeScanDTO recordTypeScanDTO = (RecordTypeScanDTO) holder.spUploadImage.getSelectedItem();

                if ("1".equals(recordTypeScanDTO.getReqScan() + "")) {
                    holder.tvRequire.setVisibility(View.VISIBLE);
                } else {
                    holder.tvRequire.setVisibility(View.GONE);
                }

                if ("1".equals(recordTypeScanDTO.getElectronicSign())) {
                    holder.btnViewPdf.setVisibility(View.VISIBLE);
                    holder.ibUploadImage.setVisibility(View.GONE);
                } else {
                    holder.btnViewPdf.setVisibility(View.GONE);
                    holder.ibUploadImage.setVisibility(View.VISIBLE);
                }

                for (RecordTypeScanDTO record : recordTypeScanDTOs) {
                    if (record.getRecordCode().equals(recordTypeScanDTO.getRecordCode())) {
                        recordTypeScanDTO.setSelected(true);
                    } else {
                        recordTypeScanDTO.setSelected(false);
                    }
                }
                profileInfoOmniFragment.doProcessSpinSelect(recordTypeScanDTO, imageId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getPositionSelected(final ArrayList<RecordTypeScanDTO> recordTypeScanDTOs) {
        for (int i = 0; i < recordTypeScanDTOs.size(); i++) {
            if (recordTypeScanDTOs.get(i).isSelected()) {
                return i;
            }
        }
        return 0;
    }

    private void processShowIcon(List<ProfileRecord> result, ProfileRecord recordOrg, ViewHolder holder) {

        ProfileRecord record = result.get(0);
        String imageId = recordOrg.getType();
        String code = recordOrg.getCode();
        ArrayList<FileObj> fileObjs = new ArrayList<>();

        if (!CommonActivity.isNullOrEmpty(record.getBitmap())) {
            holder.ibUploadImage.setImageBitmap(record.getBitmap());
            int i = 0;
            for (ProfileRecord p : result) {
                File uriFile = new File(p.getPath());
                String fileNameServer = code + "-" + (i + 1) + ".jpg";
                FileObj obj = new FileObj(code, uriFile, p.getPath(), fileNameServer);
                obj.setFullPath(p.getPath());
                obj.setRecodeName(code);
                obj.setElectronicSign("0");
                obj.setStatus(-1);
                obj.setFileExtention(CommonActivity.getFileExtension(uriFile.getPath()));
                obj.setArrOrderInfo(recordOrg.getArrOrderInfo());
                fileObjs.add(obj);
                i++;
            }
        } else if (!CommonActivity.isNullOrEmpty(record.getUnZipPath())) {
            Bitmap bitmap = BitmapFactory.decodeFile(record.getUnZipPath().get(0));
            holder.ibUploadImage.setImageBitmap(bitmap);
            int i = 0;
            for (String path : record.getUnZipPath()) {
                File uriFile = new File(path);
                String fileNameServer = code + "-" + (i + 1) + ".jpg";
                FileObj obj = new FileObj(code, uriFile, path, fileNameServer);
                obj.setFullPath(path);
                obj.setRecodeName(code);
                obj.setElectronicSign("0");
                obj.setStatus(-1);
                obj.setFileExtention(CommonActivity.getFileExtension(uriFile.getPath()));
                obj.setArrOrderInfo(recordOrg.getArrOrderInfo());
                fileObjs.add(obj);
                i++;
            }
        } else if (!CommonActivity.isNullOrEmpty(record.getSymbolicLink())
                && record.getFileExtension().toLowerCase().equals("pdf")) { // others file
            holder.ibUploadImage.setImageResource(R.drawable.pdf2);
            int i = 0;
            for (ProfileRecord p : result) {
                File uriFile = null;
                String fileNameServer = code + "-" + (i + 1) + ".pdf";
                FileObj obj = new FileObj(code, uriFile, p.getPath(), fileNameServer);
                obj.setFullPath(p.getPath());
                obj.setRecodeName(code);
                obj.setElectronicSign("0");
                obj.setStatus(-1); //!-1 la chua chon lai, thi khong day len
                obj.setUrl(p.getSymbolicLink());
                obj.setFileExtention(CommonActivity.getFileExtension(p.getSymbolicLink()));
                obj.setArrOrderInfo(recordOrg.getArrOrderInfo());
                fileObjs.add(obj);
                i++;
            }
        }
        profileInfoOmniFragment.putHashmapFileObj(imageId, fileObjs);
    }

    @Override
    public int getItemCount() {
        return arrayOfArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.spUploadImage)
        public Spinner spUploadImage;
        @BindView(R.id.ibUploadImage)
        public ImageButton ibUploadImage;
        @BindView(R.id.btnViewPdf)
        public Button btnViewPdf;
        @BindView(R.id.tvRequire)
        public TextView tvRequire;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setTag(this);
        }
    }
}
