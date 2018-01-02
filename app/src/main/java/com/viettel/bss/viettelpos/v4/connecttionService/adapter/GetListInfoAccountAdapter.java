package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

import java.util.ArrayList;
import java.util.List;

public class GetListInfoAccountAdapter extends BaseAdapter {
	private Context mContext;
	private List<SubscriberDTO> arrInfoSubs = new ArrayList<SubscriberDTO>();

	public GetListInfoAccountAdapter(List<SubscriberDTO> arr, Context context) {
		this.arrInfoSubs = arr;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return arrInfoSubs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrInfoSubs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHoler {
		TextView txtisdnandaccount, txtcontract, txthoten, txttinhtrang, txtservive, txtctkmht, txtaddresslapdat,
				txttrangthai, txtsogiayto, txtgoicuoc, txtcongnghe;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		ViewHoler holder;
		View mView = arg1;
		if (mView == null) {

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_layout_accounttech_item, arg2, false);
			holder = new ViewHoler();
			holder.txtisdnandaccount = (TextView) mView.findViewById(R.id.txtisdnandaccount);
			holder.txthoten = (TextView) mView.findViewById(R.id.txthoten);
			holder.txttinhtrang = (TextView) mView.findViewById(R.id.txttinhtrang);
			holder.txtservive = (TextView) mView.findViewById(R.id.txtservive);
			holder.txtctkmht = (TextView) mView.findViewById(R.id.txtctkmht);
			holder.txtaddresslapdat = (TextView) mView.findViewById(R.id.txtaddresslapdat);
			holder.txttrangthai = (TextView) mView.findViewById(R.id.txtsogiayto);
			holder.txtsogiayto = (TextView) mView.findViewById(R.id.txtsogiayto);
			holder.txtgoicuoc = (TextView) mView.findViewById(R.id.txtgoicuoc);
			holder.txtcongnghe = (TextView) mView.findViewById(R.id.txtcongnghe);
			mView.setTag(holder);
		} else {
			holder = (ViewHoler) mView.getTag();
		}
		final SubscriberDTO infoSub = this.arrInfoSubs.get(arg0);
		
		
		holder.txtisdnandaccount.setText(infoSub.getIsdn());
		holder.txthoten.setText(infoSub.getCustomerDTOInput().getName());
		holder.txttinhtrang.setText(infoSub.getActStatus());
		holder.txtservive.setText(infoSub.getServiceTypeName());
		holder.txtctkmht.setText(infoSub.getPromotionName());
		holder.txtaddresslapdat.setText(infoSub.getSubInfrastructureDTO().getAddress());
		holder.txttrangthai.setText(infoSub.getStatus());
		holder.txtsogiayto.setText(infoSub.getCustomerDTOInput().getIdentityNo());
		holder.txtgoicuoc.setText(infoSub.getProductName());
		holder.txtcongnghe.setText(infoSub.getTechnologyText());
		return mView;
	}

}
