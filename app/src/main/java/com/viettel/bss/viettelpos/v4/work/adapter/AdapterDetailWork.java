package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ItemDetailWork;

public class AdapterDetailWork extends BaseAdapter {
	private ArrayList<ItemDetailWork> lisDetailWorks = new ArrayList<>();
	private final Context mContext;

	public AdapterDetailWork(ArrayList<ItemDetailWork> arrayList,
			Context context) {
		this.lisDetailWorks = arrayList;
		this.mContext = context;
	}
	@Override
	public int getCount() {
		return lisDetailWorks.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	static class ViewHolder {
		TextView txtName;
		TextView txtCount;
		TextView txtbacklog;
		TextView txtbacklog1;
		TextView txtbacklog2;
		TextView txtterm;
		TextView txtterm1;
		TextView txtterm2;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_new_info2, arg2, false);
			holder = new ViewHolder();
			holder.txtName = (TextView) mView.findViewById(R.id.txtNameManager);
			holder.txtCount = (TextView) mView
					.findViewById(R.id.txt_notification_manager);
			holder.txtbacklog = (TextView) mView.findViewById(R.id.txtbacklog);
			holder.txtbacklog1 = (TextView) mView
					.findViewById(R.id.txtbacklog1);
			holder.txtbacklog2 = (TextView) mView
					.findViewById(R.id.txtbacklog2);
			holder.txtterm = (TextView) mView.findViewById(R.id.txtterm);
			holder.txtterm1 = (TextView) mView.findViewById(R.id.txtterm1);
			holder.txtterm2 = (TextView) mView.findViewById(R.id.txtterm2);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		ItemDetailWork itemDetailWork = lisDetailWorks.get(arg0);
		if (itemDetailWork != null) {
			holder.txtName.setText(itemDetailWork.getName());
			if (itemDetailWork.getTotal() > 0) {
				holder.txtCount.setText("" + itemDetailWork.getTotal());
				holder.txtCount.setVisibility(View.VISIBLE);
				if (itemDetailWork.getBackLog() > 0) {
					holder.txtbacklog.setText(itemDetailWork.getBackLog() + "");
					holder.txtbacklog.setVisibility(View.VISIBLE);
					holder.txtbacklog1.setVisibility(View.VISIBLE);
					holder.txtbacklog2.setVisibility(View.VISIBLE);
				} else {
					holder.txtbacklog.setVisibility(View.GONE);
					holder.txtbacklog1.setVisibility(View.GONE);
					holder.txtbacklog2.setVisibility(View.GONE);
				}
				if (itemDetailWork.getTerm() > 0) {
					holder.txtterm.setText(itemDetailWork.getTerm() + "");
					holder.txtterm.setVisibility(View.VISIBLE);
					holder.txtterm1.setVisibility(View.VISIBLE);
					holder.txtterm2.setVisibility(View.VISIBLE);
				} else {
					holder.txtterm.setVisibility(View.GONE);
					holder.txtterm1.setVisibility(View.GONE);
					holder.txtterm2.setVisibility(View.GONE);
				}
			} else {
				holder.txtCount.setVisibility(View.INVISIBLE);
			}
		}
		return mView;
	}

}
