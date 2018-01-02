package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBean;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetCatagoryBeanFamilyAdapter extends
		ArrayAdapter<CatagoryInforBeans> {
	private final Context mContext;

	private final OnDeleteCatagoryBeans onDeleteCatagoryBeans;
	private final OnAddCategotyBeans onAddCategotyBeans;

	public interface OnDeleteCatagoryBeans {
		void onDeleteCatagoryBeans(CatagoryInforBeans catagoryInforBeans);
	}

	public interface OnAddCategotyBeans {
		void onAddCatagoryBeans(CatagoryInforBeans catagoryInforBeans);
	}

	public GetCatagoryBeanFamilyAdapter(
			List<CatagoryInforBeans> lstCatagoryInfors, Context context, OnDeleteCatagoryBeans onDeleteCatagoryBeans, OnAddCategotyBeans onAddCategotyBeans) {
		super(context, R.layout.category_item, lstCatagoryInfors);
		mContext = context;
		this.onAddCategotyBeans = onAddCategotyBeans;
		this.onDeleteCatagoryBeans = onDeleteCatagoryBeans;
	}

	public class ViewHolder {
		TextView txtpstn;
		TextView tvvalue;
		View view;
		LinearLayout imgAdd;
		LinearLayout imgDelete;
		View view2;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.category_item, arg2, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			holder.tvvalue = (TextView) mView.findViewById(R.id.tvvalue);
			holder.view = mView.findViewById(R.id.view1);
			holder.imgAdd = (LinearLayout) mView.findViewById(R.id.imgAdd);
			holder.imgDelete = (LinearLayout) mView
					.findViewById(R.id.imgDelete);
			holder.view2 = mView.findViewById(R.id.view2);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final CatagoryInforBeans catagoryInforBeans = getItem(position);

		if ("1".equals(catagoryInforBeans.getType())) {
			holder.imgAdd.setVisibility(View.VISIBLE);
			holder.imgDelete.setVisibility(View.VISIBLE);
			holder.view2.setVisibility(View.VISIBLE);
		} else {
			holder.imgAdd.setVisibility(View.GONE);
			holder.imgDelete.setVisibility(View.GONE);
			holder.view2.setVisibility(View.GONE);
		}

		holder.imgDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onDeleteCatagoryBeans.onDeleteCatagoryBeans(catagoryInforBeans);

			}
		});
		holder.imgAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onAddCategotyBeans.onAddCatagoryBeans(catagoryInforBeans);
			}
		});

		if ("1".equals(catagoryInforBeans.getIsRequire())) {
			holder.txtpstn
					.setText(Html.fromHtml(catagoryInforBeans.getInforName()
							+ mContext.getString(R.string.checkrequire)));
		} else {
			holder.txtpstn.setText(catagoryInforBeans.getInforName());
		}

		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getValue())) {
			holder.view.setVisibility(View.VISIBLE);
			holder.tvvalue.setVisibility(View.VISIBLE);
			if ("COMBOBOX".equals(catagoryInforBeans.getDataType())) {
				if (!CommonActivity.isNullOrEmpty(catagoryInforBeans
						.getValue())) {
					if(catagoryInforBeans.getLstDataCombo() != null && catagoryInforBeans.getLstDataCombo().size() > 0){
						for (DataComboboxBean item : catagoryInforBeans.getLstDataCombo()) {
							if(catagoryInforBeans.getValue().equals(item.getCode())){
								holder.view.setVisibility(View.VISIBLE);
								holder.tvvalue.setVisibility(View.VISIBLE);
								holder.tvvalue.setText(item.getName());
							}
						}
					}
				} else {
					holder.view.setVisibility(View.GONE);
					holder.tvvalue.setVisibility(View.GONE);
				}
			} else {
				holder.tvvalue.setText(catagoryInforBeans.getValue());
			}

		} else {
			holder.tvvalue.setVisibility(View.GONE);
			holder.view.setVisibility(View.GONE);
		}
		
		
		
		return mView;
	}

}
