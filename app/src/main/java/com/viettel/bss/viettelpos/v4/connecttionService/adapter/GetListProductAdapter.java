package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubStockModelRelReqBeans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetListProductAdapter  extends BaseAdapter{
	private final Context mContext;
	private ArrayList<SubStockModelRelReqBeans> arrModelRelReqBeans = new ArrayList<>();
	public GetListProductAdapter(ArrayList<SubStockModelRelReqBeans> arr, Context context) {
		this.arrModelRelReqBeans = arr;
		mContext = context;
	}
	@Override
	public int getCount() {
		return arrModelRelReqBeans.size();
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
		TextView txtloaihang,txtserial,txtammount,txtStockModelRelId,txthinhthuc;
	}
	
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHoler holder;
		View mView = arg1;
		if(mView == null){
			
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connnection_feeservice_item, arg2,
					false);
			holder = new ViewHoler();
			holder.txtloaihang = (TextView) mView.findViewById(R.id.txtloaihang);
			holder.txtserial = (TextView) mView.findViewById(R.id.txtserial);
			holder.txtammount = (TextView) mView.findViewById(R.id.txtammount);
			holder.txthinhthuc = (TextView)mView.findViewById(R.id.txthinhthuc);
			holder.txtStockModelRelId = (TextView) mView.findViewById(R.id.txtStockModelRelId);
			mView.setTag(holder);
		}else{
			holder = (ViewHoler) mView.getTag();
		}
		SubStockModelRelReqBeans subModelRelReqBeans = this.arrModelRelReqBeans.get(arg0);
		holder.txtloaihang.setText(mContext.getResources().getString(R.string.nameStock) + " " + subModelRelReqBeans.getStockModelName());
		holder.txtserial.setText(mContext.getResources().getString(R.string.serial) + " " + subModelRelReqBeans.getSerial());
		holder.txthinhthuc.setText(mContext.getResources().getString(R.string.hinhthuc) + " " + subModelRelReqBeans.getReclaimCommitmentName());
		holder.txtammount.setText(mContext.getResources().getString(R.string.amountservice) + " " +StringUtils.formatMoney(subModelRelReqBeans.getReclaimAmount()));
		holder.txtStockModelRelId.setText(mContext.getResources().getString(R.string.mayeucau) + " : " +subModelRelReqBeans.getSubStockModelRelId());
		return mView;
	}

}
