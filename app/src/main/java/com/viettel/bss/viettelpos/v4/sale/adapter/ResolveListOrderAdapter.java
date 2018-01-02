package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;



import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObjectMerge;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResolveListOrderAdapter extends BaseAdapter{
	
	private final Context context;
	private final OncacelObjectMerge oncacelObjectMerge;
	private final OnChangeSoLuong onChangeSoLuong;
	private final ArrayList<GetOrderObjectMerge> lisOrderObjectMerges = new ArrayList<>();
	
	

	public ArrayList<GetOrderObjectMerge> getLisGetOrderObjectDetails() {
		return lisOrderObjectMerges;
	}

	public void setLisGetOrderObjectDetails(
			ArrayList<GetOrderObjectMerge> lisGetOrderObjectDetails) {
		this.lisOrderObjectMerges.clear();
		this.lisOrderObjectMerges.addAll(lisGetOrderObjectDetails);
	}

	public interface OncacelObjectMerge {
		void onCancelOjectMergeListenner(
                GetOrderObjectMerge objeOjectMerge);
	}

	public interface OnChangeSoLuong {
		void onChangeSoluongListener(GetOrderObjectMerge viOjectMerge);
	}
	public ResolveListOrderAdapter(Context context,
			ArrayList<GetOrderObjectMerge> lisInfoOjects,
			OncacelObjectMerge oncacelObjectMerge,
			OnChangeSoLuong onChangeSoLuong) {
		this.context = context;
		if (lisInfoOjects != null) {
			this.lisOrderObjectMerges.addAll(lisInfoOjects);
		}
		this.oncacelObjectMerge = oncacelObjectMerge;
		this.onChangeSoLuong = onChangeSoLuong;
	}

	@Override
	public int getCount() {
		if (lisOrderObjectMerges != null) {
			return lisOrderObjectMerges.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (lisOrderObjectMerges != null && !lisOrderObjectMerges.isEmpty()) {
			return lisOrderObjectMerges.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder hoder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater
					.inflate(R.layout.resolve_listorder_item2, parent, false);
			hoder = new ViewHolder();
			hoder.textloaihang = (TextView) mView
					.findViewById(R.id.textloaihang);
			hoder.textsluongkhocaptren = (TextView) mView
					.findViewById(R.id.textsoluongkhocaptren);
			hoder.editsoluong = (TextView) mView
					.findViewById(R.id.editdatsoluong);
			hoder.deleteitem = (LinearLayout) mView.findViewById(R.id.btndelete);
			mView.setTag(hoder);
		}else {
			hoder = (ViewHolder) mView.getTag();
		}
		final GetOrderObjectMerge getOrderObjectMerge = this.lisOrderObjectMerges.get(position);
			if(getOrderObjectMerge != null){
				hoder.textloaihang.setText(getOrderObjectMerge.getNameorder());
				hoder.textsluongkhocaptren.setText(getOrderObjectMerge.getQuantityOrderTH());
				if(Long.parseLong(getOrderObjectMerge.getQuantityOrder()) > 0){
					hoder.editsoluong.setText(getOrderObjectMerge.getQuantityOrder());
					hoder.deleteitem.setVisibility(View.VISIBLE);
					hoder.deleteitem.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							oncacelObjectMerge.onCancelOjectMergeListenner(getOrderObjectMerge);				
						}
					});
				}else {
					hoder.editsoluong.setText("");
					hoder.deleteitem.setVisibility(View.INVISIBLE);
				}
				hoder.editsoluong.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						onChangeSoLuong.onChangeSoluongListener(getOrderObjectMerge);
					}
				});
			}
			
		return mView;
	}
	class ViewHolder {
		private TextView textloaihang;
		private TextView textsluongkhocaptren;
		private TextView editsoluong;
		private LinearLayout deleteitem;
	}

}
