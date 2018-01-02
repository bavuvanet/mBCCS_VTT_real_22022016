package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.SurveyOnline;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetConectorCodeAdapter extends BaseAdapter{
	private final Context mContext;
	private ArrayList<SurveyOnline> arrSurveyOnlines = new ArrayList<>();
	
	public GetConectorCodeAdapter(ArrayList<SurveyOnline> arr, Context context) {
		this.arrSurveyOnlines = arr;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrSurveyOnlines.size();
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
		TextView txtpstn;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_item_conectorcode, arg2,
					false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		SurveyOnline surveyOnline = arrSurveyOnlines.get(arg0);
		holder.txtpstn.setText(surveyOnline.getConnectorCode());
		
		return mView;
	}

	
	
	
}
