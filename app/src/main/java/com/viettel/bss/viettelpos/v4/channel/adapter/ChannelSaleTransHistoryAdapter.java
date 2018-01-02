package com.viettel.bss.viettelpos.v4.channel.adapter;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ArraySaleChannelOJ;
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransDetailOJ;
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransOJ;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

public class ChannelSaleTransHistoryAdapter extends BaseExpandableListAdapter {


    private final ArrayList<ArraySaleChannelOJ> childtems;
	private LayoutInflater inflater;
	private final ArrayList<SaleTransOJ> parentItems  ;

    public ChannelSaleTransHistoryAdapter(ArrayList<SaleTransOJ> parents, ArrayList<ArraySaleChannelOJ> childern) {
		this.parentItems = parents;
		this.childtems = childern;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
    }

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ArrayList<SaleTransDetailOJ> child = childtems.get(groupPosition).getSaleList();
		
		TextView textView = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_layout_sale_trans_details, null);
			LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.layout_sale_trans_detail);
		}
		
		SaleTransDetailOJ item = child.get(childPosition);
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.layout.item_tilte_detail_sale);
		
		TextView tv_name_stock = (TextView) convertView.findViewById(R.id.tv_name_stock);
		TextView tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
		TextView tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
		
		tv_name_stock.setText(item.getStockName());
		tv_quantity.setText("" + item.getQuantity());
		tv_amount.setText("" + StringUtils.formatMoney(item.getAmount()));
		
		if(childPosition == 0){
			tv_name_stock.setBackgroundColor(convertView.getResources().getColor(R.color.backgroundItemTitle));
			tv_quantity.setBackgroundColor(convertView.getResources().getColor(R.color.backgroundItemTitle));
			tv_amount.setBackgroundColor(convertView.getResources().getColor(R.color.backgroundItemTitle));
		}else{
			tv_name_stock.setBackgroundColor(convertView.getResources().getColor(R.color.backgroundItem));
			tv_quantity.setBackgroundColor(convertView.getResources().getColor(R.color.backgroundItem));
			tv_amount.setBackgroundColor(convertView.getResources().getColor(R.color.backgroundItem));
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
//				Toast.makeText(activity, child.get(childPosition),
//						Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_layout_sale_trans, null);
		}
		
		TextView txt_date_sale = (TextView) convertView.findViewById(R.id.txt_date_sale);
		TextView txt_amout_tax = (TextView) convertView.findViewById(R.id.txt_amout_tax);
		String formattedDate = "";
//		try {
//			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(parentItems.get(groupPosition).getSaleTransDate());
//		
//			// format the java.util.Date object to the desired format
//			formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
//		} catch (ParseException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		txt_date_sale.setText(parentItems.get(groupPosition).getSaleTransDate().substring(0,10));
		txt_amout_tax.setText(StringUtils.formatMoney(Long.toString(parentItems.get(groupPosition).getAmoutTax())) + "");

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childtems.get(groupPosition).getSaleList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

    @Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}

