package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.NodeCodeDetail;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.util.ArrayList;
import java.util.List;

public class NodeChildAdapter extends BaseAdapter {
	Context context;
	List<NodeCodeDetail> subscriberDTOs = new ArrayList<NodeCodeDetail>();

	public NodeChildAdapter(Context context, List<NodeCodeDetail> lstData) {
		this.context = context;
		this.subscriberDTOs = lstData;
	}

	@Override
	public int getCount() {
		if (subscriberDTOs == null) {
			return 0;
		} else {
			return subscriberDTOs.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return subscriberDTOs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tv_isdnAccount;
		TextView txttenkh, tv_cus_tel, txtaddress, txttrangthai, txtdatenthu;

	}

	@Override
	public View getView(final int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.item_node_child_row, viewGroup, false);

			holder = new ViewHoder();
			holder.tv_isdnAccount = (TextView) row.findViewById(R.id.tv_isdnAccount);
			holder.txttenkh = (TextView) row.findViewById(R.id.txttenkh);
			holder.tv_cus_tel = (TextView) row.findViewById(R.id.tv_cus_tel);
			holder.txtaddress = (TextView) row.findViewById(R.id.txtaddress);
			holder.txttrangthai = (TextView) row.findViewById(R.id.txttrangthai);
			holder.txtdatenthu = (TextView) row.findViewById(R.id.txtdatenthu);

			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}

		final NodeCodeDetail item = subscriberDTOs.get(position);

		if (item != null) {
			holder.tv_isdnAccount.setText(item.getIsdn());
			holder.txttenkh.setText(item.getNameCus());
			if(!CommonActivity.isNullOrEmpty(item.getActStatus())){
				holder.txttrangthai.setText(CommonActivity.getActStatusString((Activity)context,item.getActStatus(),"1"));
			}
			holder.txtaddress.setText(item.getAddress());
			holder.txtdatenthu.setText(item.getDateNthu());
			holder.tv_cus_tel.setText(item.getTel());
		}
		return row;
	}
}
