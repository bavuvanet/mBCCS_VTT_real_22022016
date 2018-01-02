package com.viettel.bss.viettelpos.v4.hsdt.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 10/18/2017.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Activity activity;
    private ProfileBO profileBO;
    private List<ArrayList<RecordPrepaid>> arrayOfArrayList;
    private ProfileInfoDialogFragment profileInfoDialogFragment;
    private String groupType;
    private boolean isUseSig;
    private boolean isOmni;

    public ProfileAdapter(Activity activity,
                          ProfileBO profileBO,
                          List<ArrayList<RecordPrepaid>> arrayOfArrayList,
                          ProfileInfoDialogFragment profileInfoDialogFragment,
                          String groupType, boolean isUseSig, boolean isOmni) {

        this.profileBO = profileBO;
        this.isUseSig = isUseSig;
        this.isOmni = isOmni;
        this.activity = activity;
        this.arrayOfArrayList = arrayOfArrayList;
        this.profileInfoDialogFragment = profileInfoDialogFragment;
        this.groupType = groupType;
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

        final ArrayList<RecordPrepaid> recordPrepaids = arrayOfArrayList.get(position);
        final int imageId = Integer.parseInt(recordPrepaids.get(0).getId());


        Utils.setDataSpinner(activity, recordPrepaids, holder.spUploadImage);
        final int spnPosition = getPositionSelected(recordPrepaids);
        if(spnPosition > 0) {
            holder.spUploadImage.setSelection(getPositionSelected(recordPrepaids));
        }

        // set image thumnail
        ArrayList<FileObj> fileObjs = profileBO.getHashmapFileObj().get("" + imageId);
        if (CommonActivity.isNullOrEmpty(fileObjs)) {
            holder.ibUploadImage.setImageResource(R.drawable.ic_cam_hsdt);
        } else {
            String pathImage = fileObjs.get(0).getPath();
            if(!CommonActivity.isNullOrEmpty(pathImage)){
                if(holder.ibUploadImage.getWidth() == 0 && holder.ibUploadImage.getHeight() == 0){
                    int width = View.MeasureSpec.getSize((int)activity.getResources()
                            .getDimension(R.dimen.icon_height_layout_channel_manager));
                    Picasso.with(activity).load(new File(pathImage))
                            .centerCrop().resize(width, width)
                            .into(holder.ibUploadImage);
                } else {
                    Picasso.with(activity).load(new File(pathImage))
                            .centerCrop().resize(holder.ibUploadImage.getWidth(),
                            holder.ibUploadImage.getHeight()).into(holder.ibUploadImage);
                }
            }
        }

        holder.spUploadImage.setId(imageId);
        holder.ibUploadImage.setId(imageId);

        holder.ibUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordPrepaid record = (RecordPrepaid) holder.spUploadImage.getSelectedItem();
                profileInfoDialogFragment.doProcessImageClick(record, imageId);
            }
        });

        holder.btnViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordPrepaid record = (RecordPrepaid) holder.spUploadImage.getSelectedItem();
                profileInfoDialogFragment.doViewPdfFile(record);
            }
        });

        holder.spUploadImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RecordPrepaid record = (RecordPrepaid) holder.spUploadImage.getSelectedItem();

                if ("1".equals(record.getRequire())) {
                    holder.tvRequire.setVisibility(View.VISIBLE);
                } else {
                    holder.tvRequire.setVisibility(View.GONE);
                }

                // truong hop omni ko can check su dung chu ky
                if (isOmni) {
                    if (1 == record.getElectronicSign() && !"2".equals(groupType)) {
                        holder.btnViewPdf.setVisibility(View.VISIBLE);
                        holder.ibUploadImage.setVisibility(View.GONE);
                    } else {
                        holder.btnViewPdf.setVisibility(View.GONE);
                        holder.ibUploadImage.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (1 == record.getElectronicSign() && !"2".equals(groupType) && isUseSig) {
                        holder.btnViewPdf.setVisibility(View.VISIBLE);
                        holder.ibUploadImage.setVisibility(View.GONE);
                    } else {
                        holder.btnViewPdf.setVisibility(View.GONE);
                        holder.ibUploadImage.setVisibility(View.VISIBLE);
                    }
                }

                for (RecordPrepaid recordPrepaid : recordPrepaids) {
                    if (record.getCode().equals(recordPrepaid.getCode())) {
                        recordPrepaid.setIsSelected(true);
                    } else {
                        recordPrepaid.setIsSelected(false);
                    }
                }

                profileInfoDialogFragment.doProcessSpinSelect(record, imageId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int getPositionSelected(final ArrayList<RecordPrepaid> recordPrepaids){
        for(int i = 0; i < recordPrepaids.size(); i++){
            if(recordPrepaids.get(i).isSelected()){
                return i;
            }
        }
        return 0;
    }

    public boolean isUseSig() {
        return isUseSig;
    }

    public void setUseSig(boolean useSig) {
        isUseSig = useSig;
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
