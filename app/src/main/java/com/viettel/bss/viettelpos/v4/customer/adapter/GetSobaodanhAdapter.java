package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.StudentBean;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetSobaodanhAdapter extends BaseAdapter{
	private final Context mContext;
	private ArrayList<StudentBean> arrStudentBeans = new ArrayList<>();
	
	public GetSobaodanhAdapter(ArrayList<StudentBean> arr, Context context) {
		this.arrStudentBeans = arr;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrStudentBeans.size();
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
		TextView txtsobaodanh;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_item_sobaodanh, arg2,
					false);
			holder = new ViewHolder();
			holder.txtsobaodanh = (TextView) mView.findViewById(R.id.tvsobaodanh);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		final StudentBean studentBean = arrStudentBeans.get(arg0);
		
		holder.txtsobaodanh.setText(mContext.getString(R.string.hotenTSV) + "  "+studentBean.getHoten() + "\n" +
				mContext.getString(R.string.ngaysinhTSV) +"  "+ studentBean.getNgaysinh() + "\n" +
				mContext.getString(R.string.soCMNDTSV) +"  "+ studentBean.getSoCMND() + "\n" +
				mContext.getString(R.string.soBDTSV) + "  "+studentBean.getSoBD() + "\n"+
				mContext.getString(R.string.matruongTSV) +"  "+ studentBean.getMaTruongDKTT() + "\n" +
				mContext.getString(R.string.diachiTSV) +"  "+ studentBean.getDiaChi() + "\n");
		
		return mView;
	}

	
	
	
}
