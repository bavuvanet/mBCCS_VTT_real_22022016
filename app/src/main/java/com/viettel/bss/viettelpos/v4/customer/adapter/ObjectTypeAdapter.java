package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.ObjectProperty;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class ObjectTypeAdapter extends BaseAdapter{
	private ArrayList<ObjectProperty> lstData = new ArrayList<>();
	private final Context mContext;

	public ObjectTypeAdapter(Context mContext, ArrayList<ObjectProperty> lstData){
		this.mContext = mContext;
		this.lstData = lstData;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ObjectProperty item = lstData.get(position);
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(R.layout.boc_item_property, arg2, false);
			
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.edtValue = (EditText) convertView.findViewById(R.id.edtValue);
			holder.spnValue = (Spinner) convertView.findViewById(R.id.spnValue);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.position = position;
		holder.tvName.setText(item.getName());
		
		if(1 == item.getOptionType()){
			holder.tvName.setText(Html.fromHtml(item.getName() + "<font color=\"#EE0000\">(*)</font>"));
		}
		
		holder.edtValue.setEnabled(item.isEnable());
		
		if(item.getDataType().equals(1)){
			holder.edtValue.setVisibility(View.VISIBLE);
			holder.edtValue.setText(item.getValue());
			
			
			
			holder.edtValue.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable editable) {
					// TODO Auto-generated method stub
					Log.d(getClass().getSimpleName(), "afterTextChanged = " + editable.toString());
					ObjectTypeAdapter.this.lstData.get(holder.position).setValue(editable.toString());
				}
			});
			
		} else {
			holder.spnValue.setVisibility(View.VISIBLE);
			String[] lstData = new String[item.getLstDataProperty().size()];
			int selectSpn = 0;
			for(int i =0; i< item.getLstDataProperty().size(); i++){
				lstData[i] = item.getLstDataProperty().get(i).getName();
				if(lstData[i].equals(ObjectTypeAdapter.this.lstData.get(holder.position).getValue())){
					selectSpn = i;
				}
			}
			
			Utils.setDataSpinnerWithListObject(mContext, lstData, holder.spnValue);
			holder.spnValue.setSelection(selectSpn);
			holder.spnValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View v,
						int position, long arg3) {
					// TODO Auto-generated method stub					
					Log.d(getClass().getSimpleName(), "onItemSelected with position = " + holder.position + ", value = " + holder.spnValue.getSelectedItem().toString());
					ObjectTypeAdapter.this.lstData.get(holder.position).setValue(holder.spnValue.getSelectedItem().toString());
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return convertView;
	}
	
	
	
	public ArrayList<ObjectProperty> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ObjectProperty> lstData) {
		this.lstData = lstData;
	}

	private class ViewHolder {
		TextView tvName;
		Spinner spnValue;
		EditText edtValue;
		int position;
	}

}
