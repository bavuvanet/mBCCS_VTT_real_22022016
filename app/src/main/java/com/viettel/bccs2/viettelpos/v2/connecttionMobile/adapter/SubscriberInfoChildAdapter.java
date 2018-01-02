package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubscriberInfoChildAdapter extends BaseAdapter {
	Context context;
	List<SubscriberDTO> subscriberDTOs = new ArrayList<SubscriberDTO>();

	public interface OnSelectSubscriber {
		public void onSelect(SubscriberDTO subscriberDTO, int position);
	}

	public SubscriberInfoChildAdapter(Context context, List<SubscriberDTO> lstData) {
		this.context = context;
		this.subscriberDTOs = lstData;
	}

	@Override
	public int getCount() {
		if (subscriberDTOs == null) {
			return 0;
		} else {
			return subscriberDTOs.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return subscriberDTOs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tv_isdnAccount;
		TextView tv_serviceType, txtidhopdong, txttinhtrang, txttrangthai, txtgoicuoc, txtcongnghe, txthoten,
				txtBirthDay, txtsogiayto, txtnoicap, txtaddress ,txtctkmht ,txtcdthientai;
		Button btninfo,btninfopreapaid;
		LinearLayout ll_wholeRow ,lnttinkh;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_subscriber_child_row, viewGroup, false);

			holder = new ViewHoder();
			holder.tv_isdnAccount = (TextView) row.findViewById(R.id.tv_isdnAccount);
			holder.tv_serviceType = (TextView) row.findViewById(R.id.tv_serviceType);
			holder.ll_wholeRow = (LinearLayout) row.findViewById(R.id.ll_wholeRow);
			holder.txtidhopdong = (TextView) row.findViewById(R.id.txtidhopdong);
			holder.txttinhtrang = (TextView) row.findViewById(R.id.txttinhtrang);
			holder.txttrangthai = (TextView) row.findViewById(R.id.txttrangthai);
			holder.txtgoicuoc = (TextView) row.findViewById(R.id.txtgoicuoc);
			holder.txtctkmht = (TextView) row.findViewById(R.id.txtctkmht);
			holder.txtcdthientai = (TextView) row.findViewById(R.id.txtcdthientai);
			holder.txtcongnghe = (TextView) row.findViewById(R.id.txtcongnghe);
			holder.txthoten = (TextView) row.findViewById(R.id.txthoten);
			holder.txtBirthDay = (TextView) row.findViewById(R.id.tvBirthDay);
			holder.txtsogiayto = (TextView) row.findViewById(R.id.txtsogiayto);
			holder.txtnoicap = (TextView) row.findViewById(R.id.txtnoicap);
			holder.txtaddress = (TextView) row.findViewById(R.id.txtaddress);
			holder.btninfo = (Button) row.findViewById(R.id.btninfo);
			holder.btninfopreapaid = (Button) row.findViewById(R.id.btninfopreapaid);
			holder.lnttinkh = (LinearLayout) row.findViewById(R.id.lnttinkh);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}

		final SubscriberDTO item = subscriberDTOs.get(position);
		
		
		if (item != null) {
			holder.tv_isdnAccount.setText(item.getAccount());
			holder.tv_serviceType.setText(item.getTelecomServiceName());
			holder.txttrangthai.setText(item.getStatusView());
			holder.txttinhtrang.setText(item.getActStatusText());
			holder.txtgoicuoc.setText(item.getProductCode());
			holder.txtidhopdong.setText(item.getAccountNo());
			holder.txtcongnghe.setText(item.getTechnologyText());
//			holder.txtctkmht.setText(item.getPromotionCode());
//			if(!CommonActivity.isNullOrEmptyArray(item.getLstSubPromotionPrepaidDTO())){
//				holder.txtcdthientai.setText(item.getLstSubPromotionPrepaidDTO().get(0).getPrepaidCode());
//			}
			
		
		}

		return row;
	}


	private void showDialogContractPromotionDetail(SubscriberDTO subscriberDTO) {
		//
		final Dialog dialogContractDetail;
		dialogContractDetail = new Dialog(context);
		dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogContractDetail
				.setContentView(R.layout.dialog_layout_changepromotion);
		TextView tvname = (TextView) dialogContractDetail.findViewById(R.id.tvname);
		tvname.setText(context.getString(R.string.promotion_name));
		TextView tvPromotionName =  (TextView)dialogContractDetail.findViewById(R.id.tvPromotionName);
		TextView tvDialogTitle = (TextView) dialogContractDetail.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(context.getString(R.string.detailkm));
		tvPromotionName.setText(subscriberDTO.getPromotionCode());
		TextView tvStartDate = (TextView) dialogContractDetail.findViewById(R.id.tvStartDate);
		TextView tvEndDate = (TextView) dialogContractDetail.findViewById(R.id.tvEndDate);
		if(!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubPromotionDTO())){
			tvStartDate.setText(StringUtils.convertDate(subscriberDTO.getLstSubPromotionDTO().get(0).getStaDatetime()));
			tvEndDate.setText(StringUtils.convertDate(subscriberDTO.getLstSubPromotionDTO().get(0).getExpireDatetime()));
		}
		Button btn_cancel = (Button) dialogContractDetail.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialogContractDetail.cancel();
				
			}
		});
		dialogContractDetail.show();
		
	}
	
	private void showDialogContractPrepaidDetail(SubscriberDTO subscriberDTO) {
		//
		final Dialog dialogContractDetail;
		dialogContractDetail = new Dialog(context);
		dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogContractDetail
				.setContentView(R.layout.dialog_layout_changepromotion);
		TextView tvname = (TextView) dialogContractDetail.findViewById(R.id.tvname);
		tvname.setText(context.getString(R.string.namecdt));
		TextView tvPromotionName =  (TextView)dialogContractDetail.findViewById(R.id.tvPromotionName);
		TextView tvDialogTitle = (TextView) dialogContractDetail.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(context.getString(R.string.detailcdc));
		
		TextView tvStartDate = (TextView) dialogContractDetail.findViewById(R.id.tvStartDate);
		TextView tvEndDate = (TextView) dialogContractDetail.findViewById(R.id.tvEndDate);
		if(!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmptyArray(subscriberDTO.getLstSubPromotionPrepaidDTO())){
			tvStartDate.setText(StringUtils.convertDate(subscriberDTO.getLstSubPromotionPrepaidDTO().get(0).getEffectDate()));
			tvEndDate.setText(StringUtils.convertDate(subscriberDTO.getLstSubPromotionPrepaidDTO().get(0).getEndDate()));
			tvPromotionName.setText(subscriberDTO.getLstSubPromotionPrepaidDTO().get(0).getPrepaidCode());
		}
		Button btn_cancel = (Button) dialogContractDetail.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialogContractDetail.cancel();
				
			}
		});
		dialogContractDetail.show();
		
	}
	
}
