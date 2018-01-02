package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectCatOJ;

public class UpdateToolShopAdapter extends BaseAdapter {
	private final ArrayList<ObjectCatOJ> arrayListManager;
	private final Context mContext;

	public UpdateToolShopAdapter(ArrayList<ObjectCatOJ> arrayList,
			Context context) {
		this.arrayListManager = arrayList;
		this.mContext = context;
	}

	public interface onEdittextListenner {
		void onChangeEdittext(int positionTask, String edittextNumber,
				String edittextContent);
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
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView tvTypeUpdate;
		TextView txtNumberUpdate;
		TextView txtContentUpdate;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		final ViewHolder holder;
		if (mView == null) {
			Log.e("ABC", " TAO PHAN TU " + arg0);
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_update_tool_shop, arg2,
					false);
			holder = new ViewHolder();
			holder.tvTypeUpdate = (TextView) mView
					.findViewById(R.id.tv_update_tool_shop_type);
			holder.txtNumberUpdate = (TextView) mView
					.findViewById(R.id.txt_update_number_tool_shop);
			holder.txtContentUpdate = (TextView) mView
					.findViewById(R.id.txt_update_description_tool_shop);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		Log.e("cap nhat toolshop", "CAP NHAT TOOLSHOP");
		ObjectCatOJ obj = arrayListManager.get(arg0);
		if (obj != null) {
			Log.e("ABC", obj.getName() + " KHOI TAO PHAN TU " + arg0);
			// if (obj.getNote() == null || obj.getNote().equals(""))
			//
			// holder.txtContentUpdate.setHint("Cap nhat thong tin moi nhat");
			// else {
			holder.txtContentUpdate.setText(obj.getNote());
			// }
			// if (obj.getQyt() > 0)
			holder.txtNumberUpdate.setText(obj.getQyt() + "");
			// else {
			// holder.txtNumberUpdate.setHint("1");
			// }

			holder.tvTypeUpdate.setText(obj.getValue());
			holder.txtContentUpdate.setTag(arg2);
		}
		return mView;
	}

}
