package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.work.object.SalePoint;

public class AdapterSalePoint extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<SalePoint> arrayList;
	private onMyCheckBoxChangeListenner boxChangeListenner;
	private onSelectListenner selectListenner;

    public interface onSelectListenner {
		void onSelectBox(int positionTask, int positonSalePoint);
	}

	public void setOnMySelectListenner(onSelectListenner listenner) {
		this.selectListenner = listenner;
	}
	
	public interface onMyCheckBoxChangeListenner {
		void onCheckChange(int position, boolean ischecked);
	}
	public void setOnMyCheckChange(onMyCheckBoxChangeListenner changeListenner) {
		this.boxChangeListenner = changeListenner;
	}

	public AdapterSalePoint(Context context, ArrayList<SalePoint> arraySalePoint) {
		this.arrayList = arraySalePoint;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView txtNameSalePoint;
		TextView txtCountSalePoint;
		TextView txtCountMoneySalePoint;
		CheckBox cbSalePoint;
		Spinner spTask;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
        View mView = arg1;
		
		SalePoint mSalePoint = arrayList.get(arg0);
        ViewHolder holder = null;
        if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(
					R.layout.item_staff_sale_point_job_manager_layout, arg2,
					false);
			holder = new ViewHolder();
			holder.txtNameSalePoint = (TextView) mView
					.findViewById(R.id.txtNameSalePoint);
			holder.txtCountSalePoint = (TextView) mView
					.findViewById(R.id.txtCountSalePoint);
			holder.txtCountMoneySalePoint = (TextView) mView
					.findViewById(R.id.txtCountMoneySalePoint);
			holder.cbSalePoint = (CheckBox) mView
					.findViewById(R.id.cbSalePoint);

			holder.spTask = (Spinner) mView.findViewById(R.id.spTaskItem);
			holder.cbSalePoint.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CheckBox mCheckBox = (CheckBox) v;
					// arrayList.get(arg0)
					// .setCheckSalePoint(mCheckBox.isChecked());
					int pos = (Integer) v.getTag();
					if(boxChangeListenner != null){
						Log.i("TAG", "SU KIEN CLICK");
						boxChangeListenner.onCheckChange(pos, mCheckBox.isChecked());
					}
					
					// notifyDataSetChanged();
				}
			});
			
			holder.spTask.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					
					 int pos = (Integer) parent.getTag();
					 if (position == parent.getCount()){
						 selectListenner.onSelectBox(-1, pos);
					 }else{
						 selectListenner.onSelectBox(position, pos);
					 }

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

	
		

		if (mSalePoint != null) {
			holder.txtNameSalePoint.setText(mSalePoint.getSalePointName());
			Long countVisit = mSalePoint.getVisitCount();
			countVisit = (countVisit != null) ? countVisit : 0L;
			holder.txtCountSalePoint.setText(mContext.getResources().getString(
					R.string.frequency_care)
					+ ": " + countVisit.toString());
			Long moneyBuyOfMonth = mSalePoint.getMoneyBuyOfMonth();
			moneyBuyOfMonth = (moneyBuyOfMonth != null) ? moneyBuyOfMonth : 0L;
			holder.txtCountMoneySalePoint.setText(mContext.getResources()
					.getString(R.string.money_buy_job)
					+ ": "
					+ StringUtils.formatMoney(moneyBuyOfMonth.toString()));
			holder.cbSalePoint.setChecked(mSalePoint.getCheck());
			holder.cbSalePoint.setTag(arg0);
			holder.spTask.setTag(arg0);
			if (holder.cbSalePoint.isChecked()) {
				holder.spTask.setVisibility(View.VISIBLE);
			} else {
				holder.spTask.setVisibility(View.GONE);
			}
			if (mSalePoint.getArrayTaskObjects() != null) {

				AdapterSpinner adapterSpinner = new AdapterSpinner(mContext,
						mSalePoint.getArrayTaskObjects());
				holder.spTask.setAdapter(adapterSpinner);
				if (mSalePoint.getPositionTask() == -1) {
					holder.spTask.setSelection(adapterSpinner.getCount());
				} else {
					holder.spTask.setSelection(mSalePoint.getPositionTask());
				}

			}

		}
		return mView;
	}
	
	

}
