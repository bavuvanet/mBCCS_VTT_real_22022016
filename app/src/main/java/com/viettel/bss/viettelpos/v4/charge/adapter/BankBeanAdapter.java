package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.BankBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BankBeanAdapter extends ArrayAdapter<BankBean> {

	public BankBeanAdapter(Context context, List<BankBean> lst) {
	       super(context, R.layout.item_contract_bankbean, lst);
	}
	
	private static class ViewHolder {
		/**
		 *  bankBean.setIsdnBankPlus(parse.getValue(e1, "isdnBankPlus"));
			bankBean.setPaymentAmount(parse.getValue(e1, "paymentAmount"));
			bankBean.setCreateDate(parse.getValue(e1, "createDate"));
			bankBean.setReponseDate(parse.getValue(e1, "reponseDate"));
			bankBean.setBankCode(parse.getValue(e1, "bankCode"));
			bankBean.setTypePayment(parse.getValue(e1, "typePayment"));
			bankBean.setStatus(parse.getValue(e1, "status"));
		 */
		TextView bankPlusId;
        TextView isdnBankPlus;
        TextView paymentAmount;
        TextView createDate;
        TextView reponseDate;
        TextView bankCode;
        TextView typePayment;
        TextView status;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
	   BankBean bankBean = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_contract_bankbean, parent, false);
         
          viewHolder.bankPlusId = (TextView) convertView.findViewById(R.id.bankPlusId);
          viewHolder.isdnBankPlus = (TextView) convertView.findViewById(R.id.isdnBankPlus);
          viewHolder.paymentAmount = (TextView) convertView.findViewById(R.id.paymentAmount);
          viewHolder.createDate = (TextView) convertView.findViewById(R.id.createDate);
          viewHolder.reponseDate = (TextView) convertView.findViewById(R.id.reponseDate);
          viewHolder.bankCode = (TextView) convertView.findViewById(R.id.bankCode);
          viewHolder.typePayment = (TextView) convertView.findViewById(R.id.typePayment);
          viewHolder.status = (TextView) convertView.findViewById(R.id.status);

          convertView.setTag(viewHolder);
       } else {
          viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.bankPlusId.setText(bankBean.getBankPlusId().toString());
       viewHolder.isdnBankPlus.setText(bankBean.getIsdnBankPlus());
       viewHolder.paymentAmount.setText(bankBean.getPaymentAmount());
       viewHolder.createDate.setText(bankBean.getCreateDate());
       viewHolder.reponseDate.setText(bankBean.getReponseDate());
       viewHolder.bankCode.setText(bankBean.getBankCode());
       viewHolder.typePayment.setText(bankBean.getTypePayment());
       viewHolder.status.setText(bankBean.getStatus());

       // Return the completed view to render on screen
       return convertView;
   }
}
