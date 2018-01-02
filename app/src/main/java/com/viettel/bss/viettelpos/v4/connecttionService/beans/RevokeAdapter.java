package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class RevokeAdapter extends BaseAdapter {
	private final Context context;
	private List<SubStockModelRel> lstData = new ArrayList<>();
	private final OnCancelRevoke onCancelRevoke;
	private final OnCheckChange onCheckChange;
	public interface OnCancelRevoke {
		void onCancelRevoke(SubStockModelRel saleTrans);
	}
	
	public interface OnCheckChange{
		void onCheckChange(SubStockModelRel subStockModelRel);
	}
	

	public RevokeAdapter(Context context, List<SubStockModelRel> mlstData,
			OnCancelRevoke onChangeCheckedState, OnCheckChange oncheckchange) {
		this.context = context;
		this.lstData = mlstData;
		this.onCancelRevoke = onChangeCheckedState;
		this.onCheckChange = oncheckchange;
	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		} else {
			return lstData.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvitemcode;
		TextView tvtenhang;
		TextView tvserialRetryRevoke;
		TextView tvSerialRevoke;
		LinearLayout btndelete;
		CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.item_block_liquid,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tvitemcode = (TextView) row.findViewById(R.id.tvitemcode);
			holder.tvtenhang = (TextView) row.findViewById(R.id.tvtenhang);
			holder.tvserialRetryRevoke = (TextView) row.findViewById(R.id.tvserialRetryRevoke);
			holder.tvSerialRevoke = (TextView) row.findViewById(R.id.tvSerialRevoke);
			holder.btndelete = (LinearLayout) row.findViewById(R.id.btndelete);
			holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		final SubStockModelRel item = lstData.get(position);
		if (item != null) {
			holder.tvitemcode.setText(context.getResources().getString(R.string.mahang) + " " + item.getSubStockModelRelId());
			holder.tvtenhang.setText(context.getResources().getString(R.string.mathang) + " " + item.getStockModelName());
			holder.tvserialRetryRevoke.setText(context.getResources().getString(R.string.serialcanthuhoi) + " " + item.getSerial());
			
			if(!CommonActivity.isNullOrEmpty(item.getNewSerial())){
				holder.tvSerialRevoke.setText(context.getResources().getString(R.string.serialthuhoi) + " " + item.getNewSerial());
				holder.checkBox.setVisibility(View.GONE);
				holder.btndelete.setVisibility(View.VISIBLE);
			}else{
				holder.tvSerialRevoke.setText(context.getResources().getString(R.string.serialthuhoi) );
				holder.checkBox.setVisibility(View.VISIBLE);
				holder.btndelete.setVisibility(View.GONE);
			}
			
			holder.btndelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					onCancelRevoke.onCancelRevoke(item);
				}
			});
			holder.checkBox.setChecked(item.isChecked());
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onCheckChange.onCheckChange(item);
				}
			});
			
		}
		return row;
	}

}
