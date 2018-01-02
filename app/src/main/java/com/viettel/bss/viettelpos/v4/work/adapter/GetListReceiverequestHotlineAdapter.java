package com.viettel.bss.viettelpos.v4.work.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.ActivityCreateNewRequestHotLine;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.utils.Log;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentRequestChangeHotLine;

import java.util.ArrayList;

public class GetListReceiverequestHotlineAdapter extends BaseAdapter implements OnClickListener{
    private final Context mContext;
    private ArrayList<ReceiveRequestBean> arrRequestBeans = new ArrayList<>();

    private boolean isUpdate = false;

	private AdapterView.OnItemSelectedListener itemSelected;
	private View.OnClickListener onClickChange;

    public GetListReceiverequestHotlineAdapter(ArrayList<ReceiveRequestBean> arr, Context context) {
        this.arrRequestBeans = arr;
        mContext = context;
    }

    public GetListReceiverequestHotlineAdapter(ArrayList<ReceiveRequestBean> arr, Context context, boolean isUpdate) {
        this.arrRequestBeans = arr;
        mContext = context;
        this.isUpdate = isUpdate;
    }

    @Override
    public int getCount() {
        if (arrRequestBeans != null) {
            return arrRequestBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int arg0) {
        if (arrRequestBeans != null && !arrRequestBeans.isEmpty()) {
            return arrRequestBeans.get(arg0);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

	public void onClick(View v) {
		if (onClickChange != null) {
			onClickChange.onClick(v);
		}

	}

	public View.OnClickListener getOnClickChange() {
		return onClickChange;
	}

	public void setOnClickChange(View.OnClickListener onClickChange) {
		this.onClickChange = onClickChange;
	}

    private class ViewHolder {
        TextView txtaccount;
        TextView txtnamecus;
        TextView txtservice;
        TextView txtsodienthoai;
        TextView txtdatereceive;
        LinearLayout btnmovescreen;
        TextView txtyeucau;

        TextView txtRequestType;

		Button btnViewHistory;
    }

    @Override
    public View getView(int possition, View view, ViewGroup arg2) {

        View mView = view;
        ViewHolder holder = null;
        if (mView == null) {
            LayoutInflater layoutInflater = ((Activity) mContext)
                    .getLayoutInflater();
            mView = layoutInflater.inflate(R.layout.connection_change_infra_hotline,
                    arg2, false);
            holder = new ViewHolder();
            holder.txtnamecus = (TextView) mView
                    .findViewById(R.id.txtnamecus);
            holder.txtservice = (TextView) mView.findViewById(R.id.txtservice);
            holder.txtsodienthoai = (TextView) mView.findViewById(R.id.txtsodienthoai);
            holder.txtdatereceive = (TextView) mView.findViewById(R.id.txtdatereceive);
            holder.btnmovescreen = (LinearLayout) mView.findViewById(R.id.btnmovescreen);
            holder.txtaccount = (TextView) mView.findViewById(R.id.txtaccount);
            holder.txtyeucau = (TextView) mView.findViewById(R.id.txtyeucau);

			// thientv7 them history
            holder.txtRequestType = (TextView) mView.findViewById(R.id.txtRequestType);
			holder.btnViewHistory = (Button) mView.findViewById(R.id.btn_view_history);

            if (isUpdate) {
                holder.btnmovescreen.setVisibility(View.GONE);
            }
            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }

        final ReceiveRequestBean receiveRequestBean = arrRequestBeans.get(possition);


        if ("1".equals(receiveRequestBean.getRequestType())){
            holder.txtRequestType.setVisibility(View.VISIBLE);
            if ("240".equals(receiveRequestBean.getServiceTypeId())||"241".equals(receiveRequestBean.getServiceTypeId())){
                holder.txtRequestType.setText(mContext.getResources().getString(
                        R.string.loaiyeucau)+" : "+mContext.getResources().getString(R.string.dnBtnDauNoi));
            } else if ("445".equals(receiveRequestBean.getServiceTypeId())||"446".equals(receiveRequestBean.getServiceTypeId())){
                holder.txtRequestType.setText(mContext.getResources().getString(
                        R.string.loaiyeucau)+" : "+mContext.getResources().getString(R.string.chuyendoi));
            }

            holder.btnmovescreen.setVisibility(View.GONE);

        } else {
            holder.txtRequestType.setVisibility(View.GONE);
        }

        holder.txtnamecus.setText(receiveRequestBean.getCustomerName());
        holder.txtservice.setText(mContext.getResources().getString(
                R.string.dichvu)
                + " : " + receiveRequestBean.getTelecomServiceName());
        holder.txtsodienthoai.setText(mContext.getResources().getString(
                R.string.isdncus)
                + " " + receiveRequestBean.getTel());

        holder.txtdatereceive.setText(mContext.getResources().getString(
                R.string.datereq)
                + " " + receiveRequestBean.getReciveDate());


        holder.txtaccount.setText(mContext.getResources().getString(
                R.string.accountChange)
                + " " + receiveRequestBean.getAccountChange());
        android.util.Log.i("DATA","receiveRequestBean.getRequestType(): "+receiveRequestBean.getRequestType());
        if ("2".equals(receiveRequestBean.getRequestType())) {
            holder.txtyeucau.setText(mContext.getString(R.string.yeucaumoi));
        } else if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange()) && "7".equals(receiveRequestBean.getRequestType())) {
            holder.txtyeucau.setText(mContext.getString(R.string.yeucaucdcn));
        } else if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange()) && "12".equals(receiveRequestBean.getRequestType())) {
            holder.txtyeucau.setText(mContext.getString(R.string.yeucaucdgc));
        } else if ("1".equals(receiveRequestBean.getRequestType())&&("240".equals(receiveRequestBean.getRequestType())||"241".equals(receiveRequestBean.getRequestType()))){
            holder.txtyeucau.setText(mContext.getString(R.string.yeucaudaunoi));
        } else if ("1".equals(receiveRequestBean.getRequestType())&&("445".equals(receiveRequestBean.getRequestType())||"446".equals(receiveRequestBean.getRequestType()))){
            holder.txtyeucau.setText(mContext.getString(R.string.yeucaucdgc));
        }

		
		holder.btnViewHistory.setTag(possition);
		holder.btnViewHistory.setOnClickListener(this);
        holder.btnmovescreen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange()) && "7".equals(receiveRequestBean.getRequestType())) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                    FragmentManagerChangeTech mListMenuManager = new FragmentManagerChangeTech();
                    mListMenuManager.setArguments(bundle);
                    ReplaceFragment.replaceFragment((Activity) mContext,
                            mListMenuManager, false);

                } else if ("2".equals(receiveRequestBean.getRequestType())) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(mContext, ActivityCreateNewRequestHotLine.class);
                    bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange()) && "12".equals(receiveRequestBean.getRequestType())) {
                    ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy2 = new ChangeProductFragmentStrategy();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("fragmentStrategy",
                            manageSubscriberFragmentStrategy2);
                    bundle2.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                    FragmentManageSubscriber fragmentManageSubscriber2 = new FragmentManageSubscriber();
                    fragmentManageSubscriber2.setArguments(bundle2);
//                    fragmentManageSubscriber2.setTargetFragment((Activity) mContext, 100);
                    ReplaceFragment.replaceFragment((Activity) mContext,
                            fragmentManageSubscriber2, false);
                }
            }
        });


//		holder.lnupload.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(requestBeans.getActionProfileStatus().equalsIgnoreCase("0")){
//					Bundle mbunBundle = new Bundle();
//					FragmentUpLoadImage fragmentUpLoadImage = new FragmentUpLoadImage();
//					mbunBundle.putSerializable("RequestBeanKey", requestBeans);
//					fragmentUpLoadImage.setArguments(mbunBundle);
//					ReplaceFragment.replaceFragment((Activity)mContext, fragmentUpLoadImage, true);
//				}else{
//					CommonActivity.createAlertDialog(mContext,mContext.getString(R.string.requestisupdate),mContext.getString(R.string.app_name)).show();
//				}
//			
//			}
//		});

        return mView;
    }

    // move login
    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
            MainActivity.getInstance().finish();

        }
    };

}
