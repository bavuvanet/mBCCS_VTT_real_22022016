package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionRecordBean;

import java.util.ArrayList;

public class AdapterUpdateImageProfile extends BaseAdapter {

	private final Context mContext;
	private ArrayList<ActionRecordBean> lsActionRecordBeans = new ArrayList<>();

	//

	private final OnChangeImageProfile onChangeImageProfile;

	public interface OnChangeImageProfile {
		void onChangeImageProfile(ActionRecordBean actionRecordBean,
                                  int possition);
	}

	public AdapterUpdateImageProfile(Context context,
			ArrayList<ActionRecordBean> arrActionRecordBeans,
			OnChangeImageProfile onChangeImageProfile) {
		this.mContext = context;
		this.lsActionRecordBeans = arrActionRecordBeans;
		this.onChangeImageProfile = onChangeImageProfile;
	}

	@Override
	public int getCount() {
		return lsActionRecordBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (lsActionRecordBeans != null && !lsActionRecordBeans.isEmpty()) {
			return lsActionRecordBeans.get(arg0);
		} else {
			return 0;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		private TextView txtnameDB;
		private ImageView imgDB;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {

		View mView = convertView;
		ViewHolder hoder = null;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_chanel_image_sign, arg2,
					false);
			hoder = new ViewHolder();
			hoder.txtnameDB = (TextView) mView.findViewById(R.id.nameDiemBan);
			hoder.imgDB = (ImageView) mView.findViewById(R.id.imageDiemban);
			mView.setTag(hoder);
		} else {
			hoder = (ViewHolder) mView.getTag();
		}

		final ActionRecordBean actionRecordBean = this.lsActionRecordBeans
				.get(position);

		hoder.txtnameDB.setText("(" + actionRecordBean.getGroupId() + ")"
				+ actionRecordBean.getRecordName());

		if (actionRecordBean.getBmpImage() != null) {
			Drawable drawable = new BitmapDrawable(mContext.getResources(),
					actionRecordBean.getBmpImage());
			hoder.imgDB.setBackground(drawable);
		} else {

			hoder.imgDB.setBackgroundResource(R.drawable.logo_vt);
		}

		hoder.imgDB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onChangeImageProfile.onChangeImageProfile(actionRecordBean,
						position);

			}
		});

		return mView;
	}

}
