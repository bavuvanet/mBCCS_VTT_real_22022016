package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.CollectedObjectInfo;
	public class AdapterCollectedInfo extends BaseAdapter{
		private final ArrayList<CollectedObjectInfo> arrayList;
		private final Context mContext;
		private onClickButtonListener mOnButtonListener;
		private onClickViewDetailListener mClickViewDetailListener;
		public AdapterCollectedInfo (Context context,ArrayList<CollectedObjectInfo> array){
			this.mContext = context;
			this.arrayList = array;
			
		}
		public AdapterCollectedInfo(ArrayList<CollectedObjectInfo> arrayList,
				Context mContext, onClickButtonListener mOnButtonListener) {
			super();
			this.arrayList = arrayList;
			this.mContext = mContext;
			this.mOnButtonListener = mOnButtonListener;
		}
		
		public onClickViewDetailListener getmClickViewDetailListener() {
			return mClickViewDetailListener;
		}
		public void setmClickViewDetailListener(
				onClickViewDetailListener mClickViewDetailListener) {
			this.mClickViewDetailListener = mClickViewDetailListener;
		}

		public interface onClickButtonListener{
			void onClickButton(int position);
		}
		public interface onClickViewDetailListener{
			void onClickView(int position);
		}
		
		public onClickButtonListener getmOnButtonListener() {
			return mOnButtonListener;
		}
		
		

		public void setmOnButtonListener(onClickButtonListener mOnButtonListener) {
			this.mOnButtonListener = mOnButtonListener;
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
			ImageView btDelete;
			RadioButton raSelectMonth;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View mView = convertView;
			final int pos = position;
			ViewHolder holder = null;
			if(mView == null){
				LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
				mView = inflater.inflate(R.layout.item_layout_criteria, parent, false);
				
				holder = new ViewHolder();
				holder.tvCriteriaName = (TextView) mView.findViewById(R.id.tvCriteriaName);
				
				holder.tvCriteriaName.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.e("eee", "pos = " + pos);
						mClickViewDetailListener.onClickView(pos);
					}
				});
				holder.btDelete = (ImageView) mView.findViewById(R.id.imgDelete);
				holder.btDelete.setVisibility(View.VISIBLE);
				holder.btDelete.setOnClickListener(new OnClickListener() {
					@Override
				
					public void onClick(View v) {
						Log.e("eee", "pos = " + pos);
						mOnButtonListener.onClickButton(pos);
				}
			});
				
				mView.setTag(holder);
			}else{
				holder = (ViewHolder) mView.getTag();
			}
			
			CollectedObjectInfo mObject = arrayList.get(position);
			
			if(mObject != null){
				holder.tvCriteriaName.setText(mObject.getValue());
				
			}
			return mView;
		}
}
