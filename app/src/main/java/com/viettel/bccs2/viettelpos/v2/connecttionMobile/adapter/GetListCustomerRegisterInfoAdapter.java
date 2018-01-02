package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GetListCustomerRegisterInfoAdapter extends ArrayAdapter<CustomerDTO> {

    private final Activity activity;
    private View.OnClickListener imageListenner;

    public GetListCustomerRegisterInfoAdapter(
            Activity activity,
            List<CustomerDTO> arraylist,
            View.OnClickListener imageListenner) {

        super(activity, R.layout.connection_customer_reginfo_item, arraylist);
        this.activity = activity;
        this.imageListenner = imageListenner;
        if (this.imageListenner == null) {
            Log.d(this.getClass().getName(), "this.imageListenner NULL");
        } else {

        }
    }

    private class ViewHolder {
        TextView txtCustomerId;
        TextView txtName;
        TextView txtdatebirh;
        TextView txtAddress;
        TextView txtngaycap;
        TextView tvIdIssuePlace;
        ImageView imgEdit;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CustomerDTO cusDTO = getItem(position);

        ViewHolder holder;
        View mView = view;

        if (mView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            mView = inflater.inflate(
                    R.layout.connection_customer_reginfo_item, viewGroup,
                    false);
            holder = new ViewHolder();

            holder.txtCustomerId = (TextView) mView.findViewById(R.id.txtsocmt);
            holder.txtName = (TextView) mView.findViewById(R.id.txtnameCus);
            holder.txtdatebirh = (TextView) mView.findViewById(R.id.txtdatebirth);
            holder.txtAddress = (TextView) mView.findViewById(R.id.txtaddress);

          //  holder.imagecheck = (ImageView) mView.findViewById(R.id.chk_customer);
            holder.imgEdit = (ImageView) mView.findViewById(R.id.imgEdit);

            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }
        if (cusDTO != null && cusDTO.getName() != null) {
            holder.txtName.setText(cusDTO.getName());
            holder.txtCustomerId.setText(activity.getResources().getString(
                    R.string.cusId) + " " + cusDTO.getCustId());

            if (!CommonActivity.isNullOrEmpty(cusDTO.getBirthDate())) {
                if (cusDTO.getCustId() != null) {
                    holder.txtdatebirh.setText(activity.getResources().getString(
                            R.string.ngaysinh)
                            + " : " + StringUtils.convertDate(cusDTO.getBirthDate()));
                } else {
                    holder.txtdatebirh.setText(activity.getResources().getString(
                            R.string.ngaysinh)
                            + " : " + cusDTO.getBirthDate());
                }
            }

            if (!CommonActivity.isNullOrEmpty(cusDTO.getAddress())) {
                holder.txtAddress.setText(activity.getResources().getString(
                        R.string.diachi)
                        + " : " + cusDTO.getAddress());
            }

            if (imageListenner != null && cusDTO != null && !CommonActivity.isNullOrEmpty(cusDTO.getCustId())) {
                //menu.tdttkh
                SharedPreferences preferences = activity.getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
                String name = preferences.getString(Define.KEY_MENU_NAME, "");
                if (name.contains(";menu.tdttkh;")) {
                    holder.imgEdit.setVisibility(View.VISIBLE);
                    holder.imgEdit.setTag(cusDTO);
                    //holder.imgEdit.setOnClickListener(imageListenner);
                } else {
                    holder.imgEdit.setVisibility(View.GONE);
                }
            } else {
                holder.imgEdit.setVisibility(View.GONE);
            }
        }
        /*if (cusDTO.isIscheckCus()) {
            holder.imagecheck.setBackgroundResource(R.drawable.gachnoselected);
		} else {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselect);
		}*/
        return mView;
    }
}
