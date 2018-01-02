package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;

import java.util.ArrayList;
import java.util.List;

public class UpdateModifyProfileAdapter extends BaseAdapter implements
		OnClickListener {
	private final Context mContext;
	private List<RecordBean> lstRecordBeans = new ArrayList<>();
	private View.OnClickListener onClickDownload;
	private View.OnClickListener onClickSelectImage;

	public View.OnClickListener getOnClickDownload() {
		return onClickDownload;
	}

	public void setOnClickDownload(View.OnClickListener onClickDownload) {
		this.onClickDownload = onClickDownload;
	}

	public View.OnClickListener getOnClickSelectImage() {
		return onClickSelectImage;
	}

	public void setOnClickSelectImage(View.OnClickListener onClickSelectImage) {
		this.onClickSelectImage = onClickSelectImage;
	}

	public UpdateModifyProfileAdapter(Context context,
			List<RecordBean> lstRecordBeans) {
		this.mContext = context;
		this.lstRecordBeans = lstRecordBeans;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstRecordBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstRecordBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		RecordBean recordBean = lstRecordBeans.get(position);
		if (v == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			v = inflater.inflate(R.layout.layout_modify_profile_update, parent,
					false);

			holder = new ViewHolder();
			holder.tvRecordName = (TextView) v.findViewById(R.id.tvRecordName);
			holder.tvDownloadImage = (TextView) v
					.findViewById(R.id.tvDownloadImage);
			holder.ibUploadImage = (ImageButton) v
					.findViewById(R.id.ibUploadImage);

			holder.tvDownloadImage.setOnClickListener(this);
			holder.ibUploadImage.setOnClickListener(this);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		if (recordBean != null) {
			holder.tvRecordName.setText(recordBean.getRecordCode() + "-"
					+ recordBean.getRecordName());
			holder.tvDownloadImage.setText(recordBean.getRecordNameScan());
		}
		holder.tvDownloadImage.setTag(position);
		holder.ibUploadImage.setTag(position);
        if(!CommonActivity.isNullOrEmpty(recordBean.getRecordId())) {
            holder.ibUploadImage.setId(recordBean.getRecordId().intValue());
        }
		return v;
	}

	public class ViewHolder {
		public TextView tvRecordName;
		public TextView tvDownloadImage;
		public ImageButton ibUploadImage;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvDownloadImage:
			if (onClickDownload != null) {
				onClickDownload.onClick(v);
			}
			break;

		default:
			if (onClickSelectImage != null) {
				onClickSelectImage.onClick(v);
			}
			break;
		}

	}

}