package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

public class adapterManagerWork extends BaseAdapter {
	private final ArrayList<Manager> arrayListManager;
	private final Context mContext;

	public adapterManagerWork(ArrayList<Manager> arrayList, Context context) {
		this.arrayListManager = arrayList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayListManager.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stubm
		return 0;
	}

	/**
	 * 
	 * @author os_chinhnq luu cac view de khi cuon listview ko phai findviewid
	 *         nhieu lan
	 */
	static class ViewHolder {
		ImageView imvIcon;
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
			mView = inflater.inflate(R.layout.item_new_info, arg2, false);
			holder = new ViewHolder();
			holder.imvIcon = (ImageView) mView.findViewById(R.id.imvIconManager);
			holder.txtName = (TextView) mView.findViewById(R.id.txtNameManager);
			holder.txtCount = (TextView) mView.findViewById(R.id.txt_notification_manager);
			holder.txtbacklog = (TextView) mView.findViewById(R.id.txtbacklog);
			holder.txtbacklog1 = (TextView) mView.findViewById(R.id.txtbacklog1);
			holder.txtbacklog2 = (TextView) mView.findViewById(R.id.txtbacklog2);
			holder.txtterm = (TextView) mView.findViewById(R.id.txtterm);
			holder.txtterm1 = (TextView) mView.findViewById(R.id.txtterm1);
			holder.txtterm2 = (TextView) mView.findViewById(R.id.txtterm2);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		Manager manager = arrayListManager.get(arg0);
		if (manager != null) {
			if (!Define.NOTIFY.equals(manager.getKeyMenuName())) {
				holder.imvIcon.setImageResource(manager.getResIcon());
				holder.txtName.setText(manager.getNameManager());
				holder.txtCount.setText(manager.getCountManager() + "");
				if (manager.getCountManager() == 0) {
					holder.txtCount.setVisibility(View.GONE);
				} else {
					holder.txtCount.setVisibility(View.VISIBLE);
				}

				if (manager.getBacklog() > 0) {
					holder.txtbacklog.setText(manager.getBacklog() + "");
					holder.txtbacklog.setVisibility(View.VISIBLE);
					holder.txtbacklog1.setVisibility(View.VISIBLE);
					holder.txtbacklog2.setVisibility(View.VISIBLE);
					if (manager.getKeyMenuName().equalsIgnoreCase(Define.MENU_SALE_POLICY_INFO)) {
						holder.txtbacklog2.setHint(mContext.getResources().getString(R.string.policylatetime));
					}
				} else {
					holder.txtbacklog.setVisibility(View.GONE);
					holder.txtbacklog1.setVisibility(View.GONE);
					holder.txtbacklog2.setVisibility(View.GONE);
				}
				if (manager.getTerm() > 0) {
					holder.txtterm.setText(manager.getTerm() + "");
					holder.txtterm.setVisibility(View.VISIBLE);
					holder.txtterm1.setVisibility(View.VISIBLE);
					holder.txtterm2.setVisibility(View.VISIBLE);
					if (manager.getKeyMenuName().equalsIgnoreCase(Define.MENU_SALE_POLICY_INFO)) {
						holder.txtterm2.setHint(mContext.getResources().getString(R.string.policycomingtime));
					}
				} else {
					holder.txtterm.setVisibility(View.GONE);
					holder.txtterm1.setVisibility(View.GONE);
					holder.txtterm2.setVisibility(View.GONE);
				}
			} else {
				holder.imvIcon.setImageResource(manager.getResIcon());
				holder.txtName.setText(manager.getHeader());
				holder.txtbacklog1.setText(manager.getContent());
				
				holder.txtbacklog1.setVisibility(View.VISIBLE);
				holder.txtName.setVisibility(View.VISIBLE);
				holder.txtCount.setText(null);
				holder.txtCount.setVisibility(View.VISIBLE);
				holder.txtbacklog.setVisibility(View.GONE);
				holder.txtbacklog2.setVisibility(View.GONE);
				holder.txtterm.setVisibility(View.GONE);
				holder.txtterm1.setVisibility(View.GONE);
				holder.txtterm2.setVisibility(View.GONE);
			}
		}

		return mView;
	}
}
