package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

public class MenuAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Manager> data;

	public MenuAdapter(ArrayList<Manager> data, Context context) {
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (data != null && !data.isEmpty()) {
			return data.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View gridView = arg1;
		Manager item = data.get(arg0);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		gridView = inflater.inflate(R.layout.layout_bankplus_menu_item, arg2,
				false);
		ImageView img = (ImageView) gridView.findViewById(R.id.imgIcon);
		TextView tvFunctionName = (TextView) gridView
				.findViewById(R.id.tvFunctionName);
		TextView tvFunctionDescription = (TextView) gridView
				.findViewById(R.id.tvFunctionDescription);
		Drawable drawable = VectorDrawableCompat.create(context.getResources(), item.getResIcon(), context.getTheme());
		img.setImageDrawable(drawable);

		tvFunctionName.setText(item.getNameManager());
		if (CommonActivity.isNullOrEmpty(item.getContent())) {
			tvFunctionDescription.setVisibility(View.GONE);

		} else {
			tvFunctionDescription.setVisibility(View.VISIBLE);
			tvFunctionDescription.setText(item.getContent());
		}

		return gridView;
	}
}
