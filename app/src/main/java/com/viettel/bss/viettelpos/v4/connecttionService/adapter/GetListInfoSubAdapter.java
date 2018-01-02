package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetListInfoSubAdapter  extends BaseAdapter{
	private final Context mContext;
	private ArrayList<InfoSub> arrInfoSubs = new ArrayList<>();
	
	private final OnCheckChangeInfoSub onCheckChangeInfoSub;
	private final OnCancelInfoSub onCancelInfoSub;
	
	public GetListInfoSubAdapter(ArrayList<InfoSub> arr, Context context,OnCheckChangeInfoSub onCheckChangeInfoSub,OnCancelInfoSub onCancelInfoSub) {
		this.arrInfoSubs = arr;
		this.mContext = context;
		this.onCheckChangeInfoSub = onCheckChangeInfoSub;
		this.onCancelInfoSub = onCancelInfoSub;
	}
	
	public interface OnCheckChangeInfoSub{
		void onCheckChangeInfoSub(InfoSub infoSub);
	}
	public interface OnCancelInfoSub{
		void onCancelInfoSub(InfoSub infoSub);
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
		TextView txtmathuebao,txtdichvu,txtaccount,txtparentSub,txttinhtrang,txttrangthai;
		CheckBox checkBox;
		LinearLayout btndelete,lncheck;
	}
	
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHoler holder;
		View mView = arg1;
		if(mView == null){
			
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_layout_changetech_item, arg2,
					false);
			holder = new ViewHoler();
			holder.txtmathuebao = (TextView) mView.findViewById(R.id.txtmathuebao);
			holder.txtdichvu = (TextView) mView.findViewById(R.id.txtdichvu);
			holder.txtaccount = (TextView) mView.findViewById(R.id.txtaccount);
			holder.txtparentSub = (TextView)mView.findViewById(R.id.txtparentSub);
			holder.txttinhtrang = (TextView) mView.findViewById(R.id.txttinhtrang);
			holder.txttrangthai = (TextView) mView.findViewById(R.id.txttrangthai);
			holder.checkBox = (CheckBox) mView.findViewById(R.id.checkBox);
			holder.btndelete = (LinearLayout) mView.findViewById(R.id.btndelete);
			holder.lncheck = (LinearLayout) mView.findViewById(R.id.lncheck);
			mView.setTag(holder);
		}else{
			holder = (ViewHoler) mView.getTag();
		}
		final InfoSub infoSub = this.arrInfoSubs.get(arg0);
		
		if(infoSub != null && infoSub.getLstInfoSubChilds() != null && !infoSub.getLstInfoSubChilds().isEmpty()){
			holder.lncheck.setVisibility(View.VISIBLE);
		}else{
			holder.lncheck.setVisibility(View.GONE);
		}
		
		holder.txtmathuebao.setText(infoSub.getSubId());
		
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
//		holder.txtparentSub.setText(infoSub.getAccount());
		holder.txttinhtrang.setText(infoSub.getStatus());
		

		
		if(infoSub.getChangeInfraReq() != null && !CommonActivity.isNullOrEmpty(infoSub.getChangeInfraReq().getInfraType())){
			holder.txttrangthai.setVisibility(View.VISIBLE);
			holder.btndelete.setVisibility(View.VISIBLE);
			
				holder.lncheck.setVisibility(View.GONE);
			
			
			holder.btndelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					onCancelInfoSub.onCancelInfoSub(infoSub);
				}
			});
			
		}else{
			if(infoSub != null && infoSub.getLstInfoSubChilds() != null && !infoSub.getLstInfoSubChilds().isEmpty()){
				holder.lncheck.setVisibility(View.VISIBLE);
			}else{
				holder.lncheck.setVisibility(View.GONE);
			}
			
			holder.checkBox.setVisibility(View.VISIBLE);
			holder.txttrangthai.setVisibility(View.GONE);
			holder.btndelete.setVisibility(View.GONE);
		}
		
		holder.checkBox.setChecked(infoSub.isCheck());
		holder.checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onCheckChangeInfoSub.onCheckChangeInfoSub(infoSub);
				
			}
		});
		return mView;
	}

}
