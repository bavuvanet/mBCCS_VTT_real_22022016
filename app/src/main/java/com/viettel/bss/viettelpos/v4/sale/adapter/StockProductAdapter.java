package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StockProductAdapter extends ArrayAdapter<StockModel> {
	
	public StockProductAdapter(Context context, List<StockModel> lst) {
	       super(context, R.layout.item_stock_product, lst);
	}
	
	private static class ViewHolder {
		TextView name;
		TextView quantity;
		TextView quantityIssue;
        TextView stateName;
        TextView stockModelCode;
        TextView stockTypeName;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
		StockModel obj = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_stock_product, parent, false);
          viewHolder.name = (TextView) convertView.findViewById(R.id.name);
          viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
          viewHolder.quantityIssue = (TextView) convertView.findViewById(R.id.quantityIssue);
          viewHolder.stateName = (TextView) convertView.findViewById(R.id.stateName);
          viewHolder.stockModelCode = (TextView) convertView.findViewById(R.id.stockModelCode);
          viewHolder.stockTypeName = (TextView) convertView.findViewById(R.id.stockTypeName);

          convertView.setTag(viewHolder);
       } else {
          viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.name.setText(obj.getStockModelName());
       viewHolder.quantity.setText(obj.getQuantity().toString());
       viewHolder.quantityIssue.setText(obj.getQuantityIssue().toString());
       viewHolder.stateName.setText(obj.getStateName());
       viewHolder.stockModelCode.setText(obj.getStockModelCode());
       viewHolder.stockTypeName.setText(obj.getStockTypeName());
	     
       // Return the completed view to render on screen
       return convertView;
   }
}
