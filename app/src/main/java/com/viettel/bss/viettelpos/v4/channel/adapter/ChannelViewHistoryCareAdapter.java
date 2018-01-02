package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelViewHistoryOJ;

public class ChannelViewHistoryCareAdapter extends BaseAdapter {
	private final ArrayList<ChannelViewHistoryOJ> arrayListManager;
	private final Context mContext;

	public ChannelViewHistoryCareAdapter(
			ArrayList<ChannelViewHistoryOJ> arrayList, Context context) {
		this.arrayListManager = arrayList;
		this.mContext = context;
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

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_list_view_history_care,
					arg2, false);
			holder = new ViewHolder();

			holder.txtViewContent = (TextView) mView
					.findViewById(R.id.txt_view_content);
			holder.txtview_date = (TextView) mView
					.findViewById(R.id.txt_view_date);
			holder.txtTitle = (TextView) mView.findViewById(R.id.txt_view_title);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ChannelViewHistoryOJ contentHistoryOBJ = arrayListManager.get(arg0);
		if (contentHistoryOBJ != null) {
			holder.txtview_date.setText(contentHistoryOBJ.getDate());
			holder.txtViewContent.setText(contentHistoryOBJ.getContent());
			holder.txtTitle.setText(contentHistoryOBJ.getTaskName());
		}
		return mView;
	}

	static class ViewHolder {
		TextView txtview_date;
		TextView txtViewContent;
		TextView txtTitle;
	}

}
