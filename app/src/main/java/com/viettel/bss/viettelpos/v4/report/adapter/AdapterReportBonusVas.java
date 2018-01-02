package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.report.object.BonusVasObject;

public class AdapterReportBonusVas extends BaseAdapter {

	private final Activity mActivity;
	private ArrayList<BonusVasObject> lstData;
	
	
	public ArrayList<BonusVasObject> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<BonusVasObject> lstData) {
		this.lstData = lstData;
	}

	public AdapterReportBonusVas(Activity mActivity,
			ArrayList<BonusVasObject> lstData) {
		super();
		this.mActivity = mActivity;
		this.lstData = lstData;
	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		}
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (lstData == null) {
			return null;
		}
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvGoiCuoc;
		TextView tvPrice;
		TextView tvTyLePhi;
		TextView tvMoney;
		TextView tvServiceId;
		TextView tvLoaiGoi;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.bonus_vas_item, parent, false);
			holder = new ViewHolder();
			holder.tvGoiCuoc = (TextView) mView.findViewById(R.id.tvGoiCuoc);
			holder.tvPrice = (TextView) mView.findViewById(R.id.tvPrice);
			holder.tvTyLePhi = (TextView) mView.findViewById(R.id.tvTyLePhi);
			holder.tvMoney = (TextView) mView.findViewById(R.id.tvMoney);
			holder.tvServiceId = (TextView) mView
					.findViewById(R.id.tvServiceId);
			holder.tvLoaiGoi = (TextView) mView.findViewById(R.id.tvLoaiGoi);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		BonusVasObject item = lstData.get(position);
			holder.tvGoiCuoc.setText(item.getVasCode());
			holder.tvPrice.setText(StringUtils.formatMoney(item.getVasPrice()));
			holder.tvTyLePhi.setText(item.getBonusRate() +"%");
			holder.tvMoney.setText(StringUtils.formatMoney(item.getBonus()));
			holder.tvServiceId.setText(item.getServiceCode());
			holder.tvLoaiGoi.setText(item.getCycleName());
		return mView;
	}
}
