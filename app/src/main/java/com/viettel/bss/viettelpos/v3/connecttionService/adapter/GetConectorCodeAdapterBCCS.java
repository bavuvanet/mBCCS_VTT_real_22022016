package com.viettel.bss.viettelpos.v3.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetConectorCodeAdapterBCCS extends BaseAdapter{
	private  Context mContext;
	private ArrayList<ResultSurveyOnlineForBccs2Form> arrSurveyOnlines = new ArrayList<ResultSurveyOnlineForBccs2Form>();
	
	public GetConectorCodeAdapterBCCS(ArrayList<ResultSurveyOnlineForBccs2Form> arr, Context context) {
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
		ResultSurveyOnlineForBccs2Form surveyOnline = arrSurveyOnlines.get(arg0);
		holder.txtpstn.setText(surveyOnline.getConnectorCode());
		
		return mView;
	}

	
	
	
}
