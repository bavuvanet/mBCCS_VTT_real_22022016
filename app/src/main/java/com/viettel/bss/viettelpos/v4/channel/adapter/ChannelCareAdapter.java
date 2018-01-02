package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelContentCareOJ;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSalePoint.onMyCheckBoxChangeListenner;

public class ChannelCareAdapter extends BaseAdapter {
	private final ArrayList<ChannelContentCareOJ> arrayListManager;
	private final Context mContext;
	private onMyCheckBoxChangeListenner boxChangeListenner;

    public ChannelCareAdapter(ArrayList<ChannelContentCareOJ> arrayList,
			Context context) {
		this.arrayListManager = arrayList;
		this.mContext = context;
	}

	private interface onSelectListenner {
		void onSelect(int positionTask, int positonSalePoint);
	}

	public void setOnMySelectListenner(onSelectListenner listenner) {
    }

	public void setOnMyCheckChange(onMyCheckBoxChangeListenner changeListenner) {
		this.boxChangeListenner = changeListenner;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayListManager.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		CheckBox checkBox;
		TextView txtViewContent;
		TextView txtViewDate;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		final ChannelContentCareOJ contentCareOBJ = arrayListManager.get(arg0);
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_content_care, arg2, false);
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) mView
					.findViewById(R.id.chk_channelCare);
			holder.txtViewDate = (TextView) mView
					.findViewById(R.id.txt_content_date);

			holder.txtViewContent = (TextView) mView
					.findViewById(R.id.txt_content_care);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CheckBox mCheckBox = (CheckBox) v;

				int pos = contentCareOBJ.getContent_care_id();

				boxChangeListenner.onCheckChange(pos, mCheckBox.isChecked());

			}
		});
		if (contentCareOBJ != null) {
			// holder.checkBox.setId(contentCareOBJ.getContent_care_id());
			holder.checkBox.setChecked(contentCareOBJ.getChecked());
			holder.txtViewContent.setText(contentCareOBJ.getContentCare());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String date = df.format(Calendar.getInstance().getTime());
			holder.txtViewDate.setText(date);
		}
		return mView;
	}

}
