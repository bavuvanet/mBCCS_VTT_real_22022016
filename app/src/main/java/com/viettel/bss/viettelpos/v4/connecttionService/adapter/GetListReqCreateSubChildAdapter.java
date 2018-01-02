package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSubChild;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetListReqCreateSubChildAdapter  extends BaseAdapter{
	private final Context mContext;
	private ArrayList<InfoSubChild> arrInfoSubs = new ArrayList<>();
	
	private final OnCancelInfoSubChild onCancelInfoSubChild ;
	
	public GetListReqCreateSubChildAdapter(ArrayList<InfoSubChild> arr, Context context , OnCancelInfoSubChild onCancelInfoSubChild) {
		this.arrInfoSubs = arr;
		this.mContext = context;
		this.onCancelInfoSubChild = onCancelInfoSubChild;
	}
	
	public interface OnCancelInfoSubChild{
		void onCancelInfoSubChild(InfoSubChild infoSub);
	}
	
	
	
	@Override
	public int getCount() {
		return arrInfoSubs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHoler{
		TextView txtdichvu,txtaccount;
		LinearLayout btndelete;
	}
	
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHoler holder;
		View mView = arg1;
		if(mView == null){
			
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_layout_request_change_item, arg2,
					false);
			holder = new ViewHoler();
			holder.txtdichvu = (TextView) mView.findViewById(R.id.txtdichvu);
			holder.txtaccount = (TextView) mView.findViewById(R.id.txtaccount);
			holder.btndelete = (LinearLayout) mView.findViewById(R.id.btndelete);
			mView.setTag(holder);
		}else{
			holder = (ViewHoler) mView.getTag();
		}
		final InfoSubChild infoSub = this.arrInfoSubs.get(arg0);
		holder.btndelete.setVisibility(View.VISIBLE);
		holder.btndelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onCancelInfoSubChild.onCancelInfoSubChild(infoSub);
			}
		});
		if (infoSub.getServiceType() != null && !infoSub.getServiceType().isEmpty()) {
			try {
				GetServiceDal getServiceDal = new GetServiceDal(
						mContext);
				getServiceDal.open();
				String serviceName = getServiceDal
						.getDescription(infoSub.getServiceType());
				holder.txtdichvu.setText(serviceName);
				Log.d("serviceName", serviceName);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		holder.txtaccount.setText(infoSub.getAccount());
		return mView;
	}

}
