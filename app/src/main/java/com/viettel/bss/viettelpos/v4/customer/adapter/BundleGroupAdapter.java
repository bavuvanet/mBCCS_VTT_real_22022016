package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.BundleGroup;

public class BundleGroupAdapter extends BaseAdapter {

	private final List<BundleGroup> lstData;
	private final OnDeleteGroup onDeleteGroup;

	private final OnViewGroupDetail onViewGroupDetail;

	private final Context mContext;

	public interface OnDeleteGroup {
		void onDeleteGroup(int position);
	}

	public interface OnViewGroupDetail {
		void onViewGroupDetail(int position);
	}

	public BundleGroupAdapter(List<BundleGroup> lstData, Context context,
			OnDeleteGroup onDeleteGroup, OnViewGroupDetail onViewGroupDetail) {
		this.lstData = lstData;
		this.onDeleteGroup = onDeleteGroup;
		this.onViewGroupDetail = onViewGroupDetail;
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lstData != null) {
			return lstData.size();
		} else {
			return 0;
		}

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (lstData != null && !lstData.isEmpty()) {
			return lstData.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView tvSub;
		TextView tvGroupName;
		TextView tvGroupCode;
		TextView tvStatus;
		View imgDelete;
		View lnItem;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final BundleGroup item = lstData.get(position);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.bundle_group_item, arg2, false);
			holder = new ViewHolder();
			holder.tvSub = (TextView) mView.findViewById(R.id.tvSub);
			holder.tvGroupName = (TextView) mView
					.findViewById(R.id.tvGroupName);
			holder.tvGroupCode = (TextView) mView
					.findViewById(R.id.tvGroupCode);
			holder.tvStatus = (TextView) mView.findViewById(R.id.tvStatus);

			holder.imgDelete = mView.findViewById(R.id.imgDelete);
			holder.lnItem = mView.findViewById(R.id.lnItem);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.tvSub.setText(item.getIsdn());
		holder.tvGroupCode.setText(item.getCode());
		holder.tvGroupName.setText(item.getName());
		String status = "";
		if ("1".equals(item.getStatus())) {
			status = mContext.getString(R.string.actived);
		} else if ("2".equals(item.getStatus())) {
			status = mContext.getString(R.string.inactived);
		}
		holder.tvStatus.setText(status);
		holder.imgDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onDeleteGroup.onDeleteGroup(position);
			}
		});
		holder.lnItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onViewGroupDetail.onViewGroupDetail(position);
			}
		});
		return mView;
	}

}
