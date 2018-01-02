package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.ReportChanelCareDetail;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterReportListChanelDetail extends BaseAdapter {

	private final Context mContext;
	private final ArrayList<ReportChanelCareDetail> lisHeaders;

	public AdapterReportListChanelDetail(Context mContext,
			ArrayList<ReportChanelCareDetail> lisHeaders) {
		super();
		this.mContext = mContext;
		this.lisHeaders = lisHeaders;
	}

	@Override
	public int getCount() {
		return lisHeaders.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvmaten;
		TextView tvloaidb;
		TextView tvdiachi;
		TextView tvcsgannhat;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.report_layout_child, parent,
					false);
			holder = new ViewHolder();
			 holder.tvmaten = (TextView) mView.findViewById(R.id.tvmaten);
			 holder.tvloaidb = (TextView) mView.findViewById(R.id.tvloaidiemban);
			 holder.tvdiachi = (TextView) mView.findViewById(R.id.tvdiachi);
			 holder.tvcsgannhat = (TextView) mView.findViewById(R.id.tvcsgannhat);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ReportChanelCareDetail reCareDetail = lisHeaders.get(position);
		if(reCareDetail != null){
			holder.tvmaten.setText(reCareDetail.getNameChanel());
			holder.tvmaten.setMovementMethod(new ScrollingMovementMethod());
			holder.tvloaidb.setText(reCareDetail.getChanelType());
			
			holder.tvdiachi.setText(reCareDetail.getAddress());
			holder.tvdiachi.setMovementMethod(new ScrollingMovementMethod());
			holder.tvcsgannhat.setText(reCareDetail.getCsgannhat());
		}
		return mView;
	}
}
