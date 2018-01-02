package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;

public class RegisterAdapter extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<CustomerDTO> arrayList;

	public RegisterAdapter(Context mContext,
			ArrayList<CustomerDTO> arrayListCustomer) {
		this.mContext = mContext;
		this.arrayList = arrayListCustomer;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
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

	static class ViewHorder {
		TextView txtNameKH;
		TextView txtNgaySinh;
		Button btnChon;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHorder horder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.register_item, arg2, false);
			horder = new ViewHorder();
			horder.txtNameKH = (TextView) mView.findViewById(R.id.tvNameKH);
			horder.txtNgaySinh = (TextView) mView.findViewById(R.id.tvNgaySinh);
			// horder.btnChon = (Button) mView.findViewById(R.id.btnChon);
			mView.setTag(horder);
		} else {
			horder = (ViewHorder) mView.getTag();
		}

		CustomerDTO obj = arrayList.get(arg0);

		horder.txtNameKH.setText(obj.getName());

		String strDate = obj.getBirthDate();

		try {
			Date date = DateTimeUtils.convertStringToTime(strDate,
					"yyyy-MM-dd'T'hh:mm:ss");
			strDate = DateTimeUtils.convertDateTimeToString(date, "dd/MM/yyyy");
		} catch (Exception e) {
			// TODO: handle exception
		}
		horder.txtNgaySinh.setText(strDate);
		return mView;
	}
}
