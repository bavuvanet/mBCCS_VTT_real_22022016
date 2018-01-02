package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SubRelPro;
public class VasAdapter extends BaseAdapter {
	private final Context context;
	
	private final List<SubRelPro> lstRelPros = new ArrayList<>();
	private final List<SubRelPro> lstTmp = new ArrayList<>();
	private final OnChangeCheckedVas onChangeCheckedVas;
	
	public interface OnChangeCheckedVas {
		void onChangeCheckedVas(SubRelPro subRelPro);
	}

	public VasAdapter(Context context, List<SubRelPro> lstData,
			OnChangeCheckedVas onChangeCheckedVas) {
		this.context = context;
		if (lstData != null) {
			this.lstRelPros.addAll(lstData);
			this.lstTmp.addAll(lstData);
		}
		this.onChangeCheckedVas = onChangeCheckedVas;
	}

	@Override
	public int getCount() {
		if (lstRelPros == null) {
			return 0;
		} else {
			return lstRelPros.size();
		}
	}
	@Override
	public Object getItem(int arg0) {
		return lstRelPros.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvCode;
		TextView tvnamevas;
		TextView tvcheckdky;
		CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.vas_row,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tvCode = (TextView) row.findViewById(R.id.tvCode);
			holder.tvnamevas = (TextView) row.findViewById(R.id.tvnamevas);
			holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			holder.tvcheckdky = (TextView) row.findViewById(R.id.tvcheckdky);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		
		
		final SubRelPro item = lstRelPros.get(position);
		
		holder.tvCode.setText(context.getString(R.string.mavas) + item.getRelProductCode());
		holder.tvnamevas.setText(context.getString(R.string.tenvas) + item.getRelProductName());
		if("1".equals(item.getVasDefault())){
			holder.tvcheckdky.setVisibility(View.VISIBLE);
			item.setChecked(true);
		}else{
			holder.tvcheckdky.setVisibility(View.GONE);
			item.setChecked(false);
		}
		
		if (item != null) {
			holder.checkBox.setChecked(item.isChecked());
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onChangeCheckedVas.onChangeCheckedVas(item);
				}
			});
		}
		return row;
	}

	public void filter(String charText) {
		lstRelPros.clear();
		if (charText.isEmpty()) {
			lstRelPros.addAll(lstTmp);
		} else {
			for (SubRelPro item : lstTmp) {
				if (item.getRelProductCode().toLowerCase()
						.contains(charText.toLowerCase())) {
					lstRelPros.add(item);
				}
			}
		}
		notifyDataSetChanged();
	}
	
}
