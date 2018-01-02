package com.viettel.bss.viettelpos.v4.activity.slidingmenu.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

@SuppressLint("NewApi")
class CommonMenuListAdapter extends BaseAdapter {

	private final Context context;
	private final ArrayList<Manager> navDrawerItems;

	public CommonMenuListAdapter(ArrayList<Manager> navDrawerItems,
			Context context) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		// if (position == 0) {
		// imgIcon.setVisibility(View.INVISIBLE);
		// } else {
		// imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		// }
		final Manager item = this.navDrawerItems.get(position);
		txtTitle.setText(item.getNameManager());
//		imgIcon.setBackgroundDrawable(item.getResIcon());
		final int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			imgIcon.setBackgroundDrawable( context.getDrawable(item.getResIcon()) );
		} else {
			imgIcon.setBackground(context.getDrawable(item.getResIcon()));
		}

		
		
		imgIcon.setBackgroundResource(item.getResIcon());

		return convertView;
	}

}
