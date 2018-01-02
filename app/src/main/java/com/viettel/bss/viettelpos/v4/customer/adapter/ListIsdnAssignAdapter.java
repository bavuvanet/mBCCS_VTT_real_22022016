package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.customer.object.AssignIsdnStaffBean;
import com.viettel.savelog.util.Log;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListIsdnAssignAdapter extends BaseAdapter {
	private final ArrayList<AssignIsdnStaffBean> lstData;
	private final Context mContext;
	private View.OnClickListener imageViewHistoryListener;
	private View.OnClickListener checkBoxSelectListener;
	private String statusId = "";
	private String careId = "";
	private boolean book = false;

	public ListIsdnAssignAdapter(ArrayList<AssignIsdnStaffBean> lstData,
			Context context) {
		this.lstData = lstData;
		mContext = context;
	}

	public ListIsdnAssignAdapter(ArrayList<AssignIsdnStaffBean> lstData,
			Context context, View.OnClickListener imageViewHistoryListener) {
		this.lstData = lstData;
		this.mContext = context;
		this.imageViewHistoryListener = imageViewHistoryListener;
	}

	public ListIsdnAssignAdapter(ArrayList<AssignIsdnStaffBean> lstData,
			Context context, View.OnClickListener imageViewHistoryListener,
			View.OnClickListener checkBoxSelectListener) {
		this.lstData = lstData;
		this.mContext = context;
		this.imageViewHistoryListener = imageViewHistoryListener;
		this.checkBoxSelectListener = checkBoxSelectListener;
	}

	@Override
	public int getCount() {
		if (CommonActivity.isNullOrEmpty(lstData)) {
			return 0;
		}
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (CommonActivity.isNullOrEmpty(lstData)) {
			return null;
		}
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvIsdn;
		TextView tvAddress;
		TextView tvStatus;
		TextView tvDiaChiTitle;
		LinearLayout llViewHistory;
		LinearLayout lnStatus;
		CheckBox cbSelect;

		TextView tvIsdnNgoaiMang;
		TextView tvIsdnViettel;
		TextView tvNgayTiepXuc;
		TextView tvNote;
		TextView tvStatusName;
		TextView tvUserAssign;
		TextView tvAssignDate;
		TextView tvStatusChangeSim;
		LinearLayout lnNgoaiMang;
		LinearLayout lnDetailNgoaiMang;
		LinearLayout lnNoiMang;
		LinearLayout lnAssignInfo;
		LinearLayout lnAddress;
		LinearLayout lnStatus4G;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		AssignIsdnStaffBean item = lstData.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		android.util.Log.d("BBBBBB", "" + arg0 + " : " + item.getIsdn());
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.layout_isdn_assign_staff, arg2,
					false);
			holder = new ViewHolder();
			holder.tvIsdn = (TextView) mView.findViewById(R.id.tvIsdn);
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			holder.tvStatus = (TextView) mView.findViewById(R.id.tvStatus);
			holder.tvDiaChiTitle = (TextView) mView
					.findViewById(R.id.TextView01);
			holder.llViewHistory = (LinearLayout) mView
					.findViewById(R.id.llViewHistory);
			holder.llViewHistory.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					imageViewHistoryListener.onClick(arg0);
				}
			});
			holder.cbSelect = (CheckBox) mView.findViewById(R.id.cbSelect);
			holder.cbSelect.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					checkBoxSelectListener.onClick(v);
				}
			});
			holder.lnStatus = (LinearLayout) mView.findViewById(R.id.lnStatus);

			holder.tvIsdnNgoaiMang = (TextView) mView
					.findViewById(R.id.tvIsdnNgoaiMang);
			holder.tvIsdnViettel = (TextView) mView
					.findViewById(R.id.tvIsdnViettel);
			holder.tvNgayTiepXuc = (TextView) mView
					.findViewById(R.id.tvNgayTiepXuc);
			holder.tvNote = (TextView) mView.findViewById(R.id.tvNote);
			holder.lnNgoaiMang = (LinearLayout) mView
					.findViewById(R.id.lnNgoaiMang);
			holder.lnDetailNgoaiMang = (LinearLayout) mView
					.findViewById(R.id.lnDetailNgoaiMang);
			holder.lnNoiMang = (LinearLayout) mView
					.findViewById(R.id.lnNoiMang);
			holder.tvStatusName = (TextView) mView.findViewById(R.id.tvStatusName);
			holder.lnAssignInfo = (LinearLayout) mView.findViewById(R.id.lnAssignInfo);
			holder.tvUserAssign = (TextView) mView.findViewById(R.id.tvUserAssign);
			holder.tvAssignDate = (TextView) mView.findViewById(R.id.tvAssignDate);
			holder.lnAddress = (LinearLayout) mView.findViewById(R.id.lnAddress);
			holder.lnStatus4G = (LinearLayout) mView.findViewById(R.id.lnStatus4G);
			holder.tvStatusChangeSim = (TextView) mView.findViewById(R.id.tvStatusChangeSim);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		if (!Constant.STATUS_HISTORY_SCHEDULE.equals(statusId)) {
//			if (isNoiMang) {
				holder.tvIsdn.setText(item.getIsdn());
				if(CommonActivity.isNullOrEmpty(item.getPrecinctName())
						&& CommonActivity.isNullOrEmpty(item.getPrecinctName())
						&& CommonActivity.isNullOrEmpty(item.getPrecinctName())){
					holder.lnAddress.setVisibility(View.GONE);
					holder.tvAddress.setText("");
				} else {
					holder.tvAddress.setText(item.getPrecinctName() + " - "
							+ item.getDistrictName() + " - "
							+ item.getProvinceName());
				}
				
//				String[] arrStatus = mContext.getResources().getStringArray(
//						R.array.arr_status_sub_assign);
//				if(item.getStatus() != null && item.getStatus().compareTo(arrStatus.length) <= 0 && item.getStatus().compareTo(1) >= 0){
					holder.tvStatus.setText(DataUtils.getStatusNameCSKH(mContext, String.valueOf(item.getStatus())));
//				} else {
//					holder.tvStatus.setText(mContext.getResources().getString(R.string.tvOtherToolShop));
//				}
				holder.llViewHistory.setTag(item);
				
//				if(Constant.CUS_CARE.NOI_MANG.equals(careId)
//						|| (Constant.CUS_CARE.DOI_4G.equals(careId) && (item.getIsSim4G() == null || !item.getIsSim4G().equals(1)))){
//					holder.cbSelect.setVisibility(View.VISIBLE);
//				} else {
//					holder.cbSelect.setVisibility(View.GONE);
//				}
				
				// trang thai da giao khong hien thi button lich su
				if ("1".equals(statusId)) {
					holder.llViewHistory.setVisibility(View.GONE);
					Log.d("book =>>>>>>>>>>>>>>>> " + book);
					if(Constant.CUS_CARE.NOI_MANG.equals(careId)
							|| (Constant.CUS_CARE.DOI_4G.equals(careId) && (item.getIsSim4G() == null || !item.getIsSim4G().equals(1)))){
						if (book) {
							holder.cbSelect.setVisibility(View.VISIBLE);
						} else {
							holder.cbSelect.setVisibility(View.GONE);
						}
					} else {
						holder.cbSelect.setVisibility(View.GONE);
					}
				} else {
					holder.llViewHistory.setVisibility(View.VISIBLE);
					holder.cbSelect.setVisibility(View.GONE);
				}
				
				
				if (item.isSelect()) {
					holder.cbSelect.setChecked(true);
				} else {
					holder.cbSelect.setChecked(false);
				}

				holder.cbSelect.setTag(item);
				holder.lnStatus.setVisibility(View.VISIBLE);
				
				if(Constant.CUS_CARE.DOI_4G.equals(careId)){
					holder.lnStatus4G.setVisibility(View.VISIBLE);
					if(item.getIsSim4G() != null && item.getIsSim4G().equals(1)){
						holder.tvStatusChangeSim.setText(mContext.getResources().getString(R.string.txt_da_doi_sim));
					} else {
						holder.tvStatusChangeSim.setText(mContext.getResources().getString(R.string.txt_chua_doi_sim));
					}
					
				} else {
					holder.lnStatus4G.setVisibility(View.GONE);
				}
//			} else {
//				holder.lnNoiMang.setVisibility(View.GONE);
//				holder.lnNgoaiMang.setVisibility(View.VISIBLE);
//
//				holder.tvIsdnNgoaiMang.setText(item.getIsdn());
//				holder.tvIsdnViettel.setText(item.getIsdnViettelSim());
//				holder.tvNgayTiepXuc.setText(item.getMeetDate());
//				holder.tvNote.setText(item.getNote());
//				holder.tvStatusName.setText(item.getReasonName());
//				holder.tvAssignDate.setText(item.getAssignDate());
//				holder.tvUserAssign.setText(item.getUserAssign());
//				
//				if(type == 0){
//					holder.lnDetailNgoaiMang.setVisibility(View.GONE);
//				}
//			}
		} else {
			holder.lnStatus4G.setVisibility(View.GONE);
			holder.lnStatus.setVisibility(View.GONE);
			holder.cbSelect.setVisibility(View.GONE);
			holder.tvDiaChiTitle.setText(mContext.getResources().getString(
					R.string.txt_thong_tin_dat_lich));
			holder.tvIsdn.setText(item.getIsdn());
			holder.tvAddress.setText(item.getIsdnInfor());
		}
		return mView;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getCareId() {
		return careId;
	}

	public void setCareId(String careId) {
		this.careId = careId;
	}

	public boolean isBook() {
		return book;
	}

	public void setBook(boolean book) {
		this.book = book;
	}

}
