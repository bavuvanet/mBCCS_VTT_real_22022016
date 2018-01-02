package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.report.object.SaleTransFull;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AddapterFilterInvoidPaymentOfStaff extends BaseAdapter implements OnClickListener{

	private final Context mContext;
	private final ArrayList<SaleTransFull> mListInvoice;
	private final ChexboxOnlistenerInterface chexboxInterface;
	
	public interface ChexboxOnlistenerInterface { 
		
		void onclickCheckboxApproval(int position, boolean isChecked);
		
	}
	
	
	public AddapterFilterInvoidPaymentOfStaff(ArrayList<SaleTransFull> mListInvoice, Context context,ChexboxOnlistenerInterface checkboxInterface) {
		this.mListInvoice = mListInvoice;
		this.mContext = context;
		this.chexboxInterface = checkboxInterface;
	}

	@Override
	public int getCount() {

		return mListInvoice.size();
	}

	@Override
	public Object getItem(int position) {

		return mListInvoice.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_info_payment_staff, parent, false);
			holder = new ViewHolder();
			holder.txtTradingCode = (TextView) mView.findViewById(R.id.tvTradingCode);
			holder.txtUserSell = (TextView) mView.findViewById(R.id.tvUserSell);
			holder.txtDateTranfer = (TextView) mView.findViewById(R.id.tvDateTranfer);
			holder.txtMoney = (TextView) mView.findViewById(R.id.tvMoney);
			holder.cbApproval = (CheckBox) mView.findViewById(R.id.cbApproval);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		} 

		SaleTransFull mObject = mListInvoice.get(position);
		if (mObject != null) {
			holder.txtTradingCode.setText(mObject.getSaleTransCode());
			holder.txtUserSell.setText(mObject.getStaffCode());
			holder.txtDateTranfer.setText(mObject.getSaleTransDate()); 
			String money = StringUtils.formatMoney(mObject.getAmountTax()); 
			holder.txtMoney.setText(money);
			holder.cbApproval.setChecked(mObject.isCheckApproval());

			holder.cbApproval.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SaleTransFull mObject = mListInvoice.get(position);
					mObject.setCheckApproval(!mObject.isCheckApproval());
					CheckBox checkboxItem = (CheckBox) v;
					checkboxItem.setChecked(mObject.isCheckApproval());
					
					chexboxInterface.onclickCheckboxApproval(position,mObject.isCheckApproval()); 
				}
			});
			
//			holder.cbApproval.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
//					
//				}
//			}); 
		}
		return mView;
	}

	class ViewHolder {
		TextView txtTradingCode;
		TextView txtUserSell;
		TextView txtDateTranfer;
		TextView txtMoney;
		CheckBox cbApproval;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
