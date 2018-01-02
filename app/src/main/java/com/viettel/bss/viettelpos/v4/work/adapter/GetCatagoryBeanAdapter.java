package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateAreaDetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GetCatagoryBeanAdapter extends ArrayAdapter<CatagoryInforBeans> {
	private final Context mContext;

	private View.OnClickListener deleteBtnListener;

	public GetCatagoryBeanAdapter(List<CatagoryInforBeans> lstCatagoryInfors, Context context) {
		super(context, R.layout.item_area_update, lstCatagoryInfors);
		mContext = context;
	}

	// public GetCatagoryBeanAdapter(List<CatagoryInforBeans> lstCatagoryInfors,
	// Context context, View.OnClickListener deleteBtnListener) {
	// super(context, R.layout.item_area_update, lstCatagoryInfors);
	// mContext = context;
	// this.deleteBtnListener = deleteBtnListener;
	// }

	public class ViewHolder {
		public TextView tvKey;
		public TextView tvValue;
		public ImageView imgDelete;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_area_update, arg2, false);
			holder = new ViewHolder();
			holder.tvKey = (TextView) mView.findViewById(R.id.tvKey);
			holder.tvValue = (TextView) mView.findViewById(R.id.tvValue);

			holder.imgDelete = (ImageView) mView.findViewById(R.id.imgDelete);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		CatagoryInforBeans catagoryInforBeans = getItem(position);

		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getInforName())) {
			String key = "1".equalsIgnoreCase(catagoryInforBeans.getIsRequire()) ? catagoryInforBeans.getInforName() + "* "
					: catagoryInforBeans.getInforName();
			holder.tvKey.setText(key);
		}

		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getInforName())) {
			if (catagoryInforBeans.getNameValue() == null || catagoryInforBeans.getNameValue().isEmpty()) {
				holder.tvValue.setText(catagoryInforBeans.getValue());
			} else {
				holder.tvValue.setText(catagoryInforBeans.getNameValue());
			}
		}

		if ("1".equalsIgnoreCase(catagoryInforBeans.getIsRequire())) {
			holder.imgDelete.setVisibility(View.GONE);
		} else {
			
			holder.imgDelete.setVisibility(View.VISIBLE);
			holder.imgDelete.setTag(position);

			if (catagoryInforBeans.isDeleted()) {
				String uri = "@drawable/add";
				int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
				Drawable res = getContext().getResources().getDrawable(imageResource);
				holder.imgDelete.setImageDrawable(res);
			
				holder.tvKey.setVisibility(View.GONE);
				holder.tvValue.setVisibility(View.GONE);
			} else {
				String uri = "@drawable/delete";
				int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
				Drawable res = getContext().getResources().getDrawable(imageResource);
				holder.imgDelete.setImageDrawable(res);
			
				holder.tvKey.setVisibility(View.VISIBLE);
				holder.tvValue.setVisibility(View.VISIBLE);
			}

			holder.imgDelete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int pos = Integer.parseInt(v.getTag().toString());
					CatagoryInforBeans obj = getItem(pos);
					obj.setDeleted(!obj.isDeleted());

					if(obj.isDeleted()) {
						FragmentUpdateAreaDetail.hashMap.put(obj.getInforCode(), obj);
					}
					
					notifyDataSetChanged();
				}
			});
			
		}

		return mView;
	}

}
