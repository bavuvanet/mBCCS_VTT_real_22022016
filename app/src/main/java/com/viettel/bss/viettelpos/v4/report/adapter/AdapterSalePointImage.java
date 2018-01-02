package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.SalePointImageObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AdapterSalePointImage extends BaseAdapter {
	private final Activity mActivity;
	private final ArrayList<SalePointImageObject> arrSalePointImage;

	public AdapterSalePointImage(ArrayList<SalePointImageObject> arrSalePointImage, Activity mActivity) {
		this.mActivity = mActivity;
		this.arrSalePointImage = arrSalePointImage; 
	}

	@Override
	public int getCount() { 
		return arrSalePointImage.size();
	}

	@Override
	public Object getItem(int arg0) { 
		return arrSalePointImage.get(arg0);
	}

	@Override
	public long getItemId(int arg0) { 
		return 0;
	}

	static class ViewHolder {
		TextView tv_sale_point_name;
		TextView tv_number_sale;
		TextView tv_status; 
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) { 
		final SalePointImageObject salePointImageObject = arrSalePointImage.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_sale_point_image, arg2, false);
			holder = new ViewHolder();

			holder.tv_sale_point_name = (TextView) mView.findViewById(R.id.tv_sale_point_name);
			holder.tv_number_sale = (TextView) mView.findViewById(R.id.tv_number_sale);
			holder.tv_status = (TextView) mView.findViewById(R.id.tv_status); 
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.tv_sale_point_name.setText(salePointImageObject.getSaleName());
		holder.tv_number_sale.setText(salePointImageObject.getQuality());
		holder.tv_status.setText(salePointImageObject.getStatus()); 
		
		return mView;
	}
}
