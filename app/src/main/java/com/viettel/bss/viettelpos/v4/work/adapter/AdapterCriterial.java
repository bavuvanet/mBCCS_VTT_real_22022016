package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;
	public class AdapterCriterial extends BaseAdapter{
		private final ArrayList<ObjectDetailGroup> arrayList;
		private final Context mContext;
		public AdapterCriterial (Context context,ArrayList<ObjectDetailGroup> array){
			this.mContext = context;
			this.arrayList = array;
			
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		static class ViewHolder{
			TextView tvCriteriaName;
			RadioButton raSelectMonth;
			ImageView imgRaw;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View mView = convertView;
			ViewHolder holder = null;
			if(mView == null){
				LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
				mView = inflater.inflate(R.layout.item_layout_collect_info, parent, false);
				holder = new ViewHolder();
				holder.tvCriteriaName = (TextView) mView.findViewById(R.id.tvCriteriaName);
				holder.imgRaw = (ImageView) mView.findViewById(R.id.imgRaw);
				mView.setTag(holder);
			}else{
				holder = (ViewHolder) mView.getTag();
			}
			ObjectDetailGroup mObject = arrayList.get(position);
			if(mObject!=null){
				holder.tvCriteriaName.setText(mObject.getName());
			}
			return mView;
		}
}
