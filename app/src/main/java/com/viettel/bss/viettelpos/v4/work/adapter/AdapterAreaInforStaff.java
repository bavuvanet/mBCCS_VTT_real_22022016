package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.AreaInforStaff;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterAreaInforStaff extends ArrayAdapter<AreaInforStaff> {

	private final View.OnClickListener editBtnListener;
	private String check = "";

	public AdapterAreaInforStaff(Context context, List<AreaInforStaff> objects, View.OnClickListener _editBtnListener, String mcheck) {
		super(context, R.layout.item_area_info_staff, objects);
		this.editBtnListener = _editBtnListener;
		this.check = mcheck;
	}

	class ViewHolder {
		TextView area;
		TextView province;
		TextView district;
		TextView precinct;
		TextView streetBlock;
		ImageView editBtn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_area_info_staff, parent, false);
			holder = new ViewHolder();
			holder.area = (TextView) convertView.findViewById(R.id.area);
			holder.province = (TextView) convertView.findViewById(R.id.province);
			holder.district = (TextView) convertView.findViewById(R.id.district);
			holder.precinct = (TextView) convertView.findViewById(R.id.precinct);
			holder.streetBlock = (TextView) convertView.findViewById(R.id.streetBlock);
			holder.editBtn = (ImageView) convertView.findViewById(R.id.editBtn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AreaInforStaff obj = getItem(position);
		
		if("1".equalsIgnoreCase(check)){
			holder.editBtn.setVisibility(View.GONE);
		}else{
			holder.editBtn.setVisibility(View.VISIBLE);
		}
		if (obj != null) {
			holder.area.setText(obj.getFullName());
			holder.province.setText(obj.getProvinceCode());
			holder.district.setText(obj.getDistrictCode());
			holder.precinct.setText(obj.getPrecinctCode());
			holder.streetBlock.setText(obj.getStreetBlockCode());
			holder.editBtn.setTag(obj);
			holder.editBtn.setOnClickListener(editBtnListener);
		}

		return convertView;
	}
}
