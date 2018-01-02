package com.viettel.bss.viettelpos.v4.bankplus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.dialog.DestroyTransDialog;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.bankplus.object.TransBank;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

public class TransHistoryAdapter extends BaseAdapter {
	private Context mContext;
	private Activity activity;
	private List<TransBank> lstTransBanks = new ArrayList<TransBank>();
	private int lastPosition = -1;

	public TransHistoryAdapter(Context context, Activity activity, List<TransBank> lstTransBanks){
		this.mContext = context;
		this.activity = activity;
		this.lstTransBanks = lstTransBanks;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstTransBanks.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstTransBanks.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		final TransBank transBank = lstTransBanks.get(position);

		if(v == null){
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			v = inflater.inflate(R.layout.bankplus_row_trans_history, parent, false);
			holder = new ViewHolder();
			holder.tvProcessName = (TextView) v.findViewById(R.id.tvProcessName);
			holder.tvCreateDate = (TextView) v.findViewById(R.id.tvCreateDate);
			holder.tvAmount = (TextView) v.findViewById(R.id.tvAmount);
			holder.tvFee = (TextView) v.findViewById(R.id.tvFee);
			holder.tvResult = (TextView) v.findViewById(R.id.tvResult);
			holder.imgbtDestroyTrans = (ImageButton) v.findViewById(R.id.imgbtDestroyTrans);
			holder.linContent = (LinearLayout) v.findViewById(R.id.linContent);
			holder.tvIsdn = (TextView) v.findViewById(R.id.tvIsdn);
			holder.imgbtResend = (ImageButton) v.findViewById(R.id.imgbtResend);

			v.setTag(holder);
		}else {
			holder = (ViewHolder) v.getTag();
		}

		if(transBank != null){
			holder.tvProcessName.setText(transBank.getProcessName());
			holder.tvCreateDate.setText(transBank.getRequestDate());
			holder.tvAmount.setText(StringUtils.formatMoney(transBank.getAmount()));
			holder.tvFee.setText(transBank.getFee());
			holder.tvResult.setText(transBank.getErrorName());
			holder.tvIsdn.setText("Isdn/Account: " + transBank.getIsdn());

			holder.imgbtDestroyTrans.setVisibility(View.INVISIBLE);
			holder.imgbtResend.setVisibility(View.INVISIBLE);

			if ((transBank.getProcessCode().contains("PAY") || transBank.getProcessCode().contains("TOP"))
					&& ("1".equals(transBank.getErrorCode()) || "00".equals(transBank.getErrorCode()))) {
				holder.imgbtDestroyTrans.setVisibility(View.VISIBLE);
			} else {
				holder.imgbtDestroyTrans.setVisibility(View.INVISIBLE);
			}

			Animation animation = AnimationUtils.loadAnimation(mContext,
					(position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
			v.startAnimation(animation);
			lastPosition = position;

			final ImageButton imageButton = holder.imgbtDestroyTrans;
            holder.imgbtDestroyTrans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
					DestroyTransDialog dialog = new DestroyTransDialog(
							imageButton, mContext,
							activity, transBank.getRequestId(), transBank.getIsdn());
					dialog.show();
                }
            });

			holder.imgbtResend.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					alertSendSms(transBank.getRequestId(), transBank.getIsdn());
				}
			});
		}
		return v;
	}

	private void alertSendSms(final String tppTransId,final String isdn) {
		final View.OnClickListener sendSmsListener = new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doSendSms(tppTransId, isdn);
			}
		};

		CommonActivity.createDialog(activity,
				activity.getString(R.string.bankplus_confirm_resend_code_card, tppTransId, isdn),
				activity.getString(R.string.app_name),
				activity.getString(R.string.cancel),
				activity.getString(R.string.ok),
				null, sendSmsListener).show();
	}

	private void doSendSms(String tppTransId, String isdn) {
		String token = Session.getToken();
		StringBuilder data = new StringBuilder();
		data.append(token).append(Constant.STANDARD_CONNECT_CHAR);

		data.append(BPConstant.COMMAND_SEND_SMS_PIN_CODE_TRANS)
				.append(Constant.STANDARD_CONNECT_CHAR);
		data.append(tppTransId).append(Constant.STANDARD_CONNECT_CHAR);

		new CreateBankPlusAsyncTask(data.toString(), activity,
				new OnPostExecuteListener<BankPlusOutput>() {
					@Override
					public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
						// TODO Auto-generated method stub
						if ("0".equals(errorCode)) {
							CommonActivity.createAlertDialog(activity,
									activity.getString(R.string.bankplus_resend_code_card_success),
									activity.getString(R.string.app_name)).show();
						} else {
							Toast.makeText(mContext, description, Toast.LENGTH_LONG).show();
						}
					}
				}, null).execute();
	}
	
	private class ViewHolder {
		TextView tvProcessName;
		TextView tvCreateDate;
		TextView tvAmount;
		TextView tvFee;
		TextView tvResult;
		TextView tvIsdn;

		ImageButton imgbtDestroyTrans;
		ImageButton imgbtResend;
		LinearLayout linContent;
	}
}