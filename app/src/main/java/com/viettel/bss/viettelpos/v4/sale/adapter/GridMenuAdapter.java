package com.viettel.bss.viettelpos.v4.sale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.ArrayList;

public class GridMenuAdapter extends BaseAdapter {

	private final Context context;
	private final ArrayList<Manager> data;

	public GridMenuAdapter(ArrayList<Manager> data, Context context) {
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
//		if (gridView == null) {
			Manager item = data.get(arg0);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = inflater.inflate(R.layout.item_grid_menu_layout, arg2,
					false);
			ImageView img = (ImageView) gridView.findViewById(R.id.imgIcon);
			TextView tv = (TextView) gridView.findViewById(R.id.tvTitle);
			img.setImageResource(item.getResIcon());
			if (data.size() <= 2) {
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView
						.findViewById(R.id.viewColumn).getLayoutParams();
				int margin = (int) context.getResources().getDimension(
						R.dimen.activity_vertical_margin);
				params.setMargins(0, margin, 0, margin);
			} else {
				if (arg0 == 0) {

					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView
							.findViewById(R.id.viewColumn).getLayoutParams();
					int marginTop = (int) context.getResources().getDimension(
							R.dimen.activity_vertical_margin);
					params.setMargins(0, marginTop, 0, 0);
				}
				if (data.size() % 2 == 0) {
					if (arg0 == data.size() - 2) {
						LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView
								.findViewById(R.id.viewColumn)
								.getLayoutParams();
						int margin = (int) context.getResources().getDimension(
								R.dimen.activity_vertical_margin);
						params.setMargins(0, 0, 0, margin);
					}
				} else {
					if (arg0 == data.size() - 1) {
						LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView
								.findViewById(R.id.viewColumn)
								.getLayoutParams();
						int margin = (int) context.getResources().getDimension(
								R.dimen.activity_vertical_margin);
						params.setMargins(0, 0, 0, margin);
					}
				}
			}

			if (arg0 % 2 == 1) {
				gridView.findViewById(R.id.viewColumn).setVisibility(View.GONE);
			}
			if (arg0 == data.size() - 1) {
				gridView.findViewById(R.id.viewLine).setVisibility(View.GONE);
			}
			if (data.size() % 2 == 0 && arg0 == data.size() - 2) {
				gridView.findViewById(R.id.viewLine).setVisibility(View.GONE);
			}
			tv.setText(item.getNameManager());
//		}

		return gridView;
	}
}
