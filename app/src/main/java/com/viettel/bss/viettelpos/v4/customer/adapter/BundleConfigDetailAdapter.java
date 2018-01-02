package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.customer.object.ProductBundleGroupsConfigDetail;

public class BundleConfigDetailAdapter extends BaseAdapter {

	private final List<ProductBundleGroupsConfigDetail> lstData;
	private final OnDeleteSub onDeleteSub;
	private final OnAddSub onAddSub;
	private final OnChangeSub onChangeSub;

	private final Context mContext;

	public interface OnDeleteSub {
		void onDeleteSub(int position);
	}

	public interface OnAddSub {
		void onAddSub(int position);
	}

	public interface OnChangeSub {
		void onChangeSub(int position);
	}

	public BundleConfigDetailAdapter(
			List<ProductBundleGroupsConfigDetail> lstData, Context context,
			OnDeleteSub onDeleteSub, OnAddSub onAddSub, OnChangeSub onChangeSub) {
		this.lstData = lstData;
		this.onAddSub = onAddSub;
		this.onChangeSub = onChangeSub;
		this.onDeleteSub = onDeleteSub;
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
		TextView tvService;
		TextView tvShortNumber;
		CheckBox cbRequire;
		RadioButton cbGropuBoss;
		View imgAdd;
		View imgDelete;
		View lnItem;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ProductBundleGroupsConfigDetail item = lstData.get(position);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.layout_bundle_group_item, arg2,
					false);
			holder = new ViewHolder();
			holder.tvSub = (TextView) mView.findViewById(R.id.tvSub);
			holder.tvService = (TextView) mView.findViewById(R.id.tvService);
			holder.tvShortNumber = (TextView) mView
					.findViewById(R.id.tvShortNumber);
			holder.cbRequire = (CheckBox) mView.findViewById(R.id.cbRequire);
			holder.cbGropuBoss = (RadioButton) mView
					.findViewById(R.id.cbGroupBoss);
			holder.imgAdd = mView.findViewById(R.id.imgAdd);
			holder.imgDelete = mView.findViewById(R.id.imgDelete);
			holder.lnItem = mView.findViewById(R.id.lnItem);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		String serviceName = "";
		// Mul 1 chieu
		if ("35".equals(item.getTelecomeServiceId())) {
			serviceName = mContext.getString(R.string.mul_1_c);
		} else if ("45".equals(item.getTelecomeServiceId())) {
			serviceName = mContext.getString(R.string.mul_2_c);
		} else {
			GetServiceDal dal = new GetServiceDal(mContext);

			try {
				serviceName = dal.getTelecomServiceCMById(
						item.getTelecomeServiceId()).getTele_service_name();
			} catch (Exception ignored) {
			}
		}

		if (item.getTelecomeServiceId().equals("1")
				|| item.getTelecomeServiceId().equals("2")) {
			String serviceType = "";
			if (item.getSubType().equals("1")) {
				serviceType = mContext.getString(R.string.tv_tra_sau);
			} else {
				serviceType = mContext.getString(R.string.tv_tra_truoc);
			}
			serviceName = serviceName + " " + serviceType;
		}

		String isdnAccount = item.getIsdnAccount();
		if (CommonActivity.isNullOrEmpty(isdnAccount)) {
			isdnAccount = mContext.getString(R.string.no_input);
		}
		holder.tvSub.setText(isdnAccount);
		holder.tvService.setText(serviceName);
		int require = 0;
		try {
			require = Integer.parseInt(item.getFromValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (require == 0) {
			holder.cbRequire.setChecked(false);
		} else {

			int index = 0;
			for (int i = 0; i <= position; i++) {
				ProductBundleGroupsConfigDetail config = lstData.get(i);
				if (config.getTelecomeServiceId().equals(
						item.getTelecomeServiceId())) {
					index++;
				}
			}
			if (index <= require) {
				holder.cbRequire.setChecked(true);
			} else {
				holder.cbRequire.setChecked(false);
			}

		}
		holder.cbGropuBoss.setChecked(item.getIsBoss());
		holder.tvShortNumber.setText(item.getShortNumberInput());
		holder.imgDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onDeleteSub.onDeleteSub(position);
			}
		});
		holder.imgAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onAddSub.onAddSub(position);
			}
		});
		holder.lnItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onChangeSub.onChangeSub(position);
			}
		});
		// if (BundleBusiness.checkAdd(lstData, item)) {
		// holder.imgAdd.setVisibility(View.VISIBLE);
		// } else {
		// holder.imgAdd.setVisibility(View.INVISIBLE);
		// }
		// if (BundleBusiness.checkDel(lstData, item)) {
		// holder.imgDelete.setVisibility(View.VISIBLE);
		// } else {
		// holder.imgDelete.setVisibility(View.INVISIBLE);
		// }
		return mView;
	}

}
