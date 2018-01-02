package com.viettel.bss.viettelpos.v4.advisory.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.data.ProductBean;
import com.viettel.bss.viettelpos.v4.advisory.data.RefillInfoData;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.task.AsynTaskRegisterProductTVBH;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hantt47 on 7/19/2017.
 */

public class ProductAdapter extends ArrayAdapter<ProductBean> {

    private ArrayList<ProductBean> productBeanArrayList;
    private ArrayList<ProductBean> productBeanArrayListBackup;
    Context context;
    Activity activity;
    private String isdn;
    private String prepaid;

    // View lookup cache
    private static class ViewHolder {
        TextView tvCode;
        TextView tvDesc;
        Button btAdvisory;
        Button btRegister;
        View headerView;
        LinearLayout contentLayout;
    }

    public ProductAdapter(String isdn, String prepaid, ArrayList<ProductBean> productBeanArrayList,
                          Activity activity, Context context) {
        super(context, R.layout.advisory_expand_item_view, productBeanArrayList);

        this.productBeanArrayList = productBeanArrayList;
        this.productBeanArrayListBackup = new ArrayList<>();
        this.productBeanArrayListBackup.addAll(productBeanArrayList);
        this.context = context;
        this.activity = activity;
        this.isdn = isdn;
        this.prepaid = prepaid;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductBean productBean = getItem(position);
        final ProductAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ProductAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.advisory_expand_item_view, parent, false);

            viewHolder.tvCode = (TextView) convertView.findViewById(R.id.tvCode);
            viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
            viewHolder.btAdvisory = (Button) convertView.findViewById(R.id.btAdvisory);
            viewHolder.btRegister = (Button) convertView.findViewById(R.id.btRegister);
            viewHolder.contentLayout = (LinearLayout) convertView.findViewById(R.id.contentLayout);

            viewHolder.btAdvisory.setPaintFlags(
                    viewHolder.btAdvisory.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            viewHolder.btRegister.setPaintFlags(
                    viewHolder.btRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        if (position % 2 == 0) {
            viewHolder.contentLayout.setBackgroundResource(R.color.gray_7);
        } else {
            viewHolder.contentLayout.setBackgroundResource(R.color.gray_8);
        }

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.vas_up_from_bottom : R.anim.vas_down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tvCode.setText("" + productBean.getCode());
        viewHolder.tvDesc.setText("" + productBean.getDesc());

        final View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new AsynTaskRegisterProductTVBH(activity, new OnPostExecuteListener<CCOutput>() {
                    @Override
                    public void onPostExecute(CCOutput result, String errorCode, String description) {
                        CommonActivity.toast(activity, "Đăng ký thành công");
                        removeItem(productBean);
                    }
                }, null).execute(productBean.getCode(), productBean.getType(), isdn, prepaid, "true");
            }
        };

        final View.OnClickListener advisoryListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new AsynTaskRegisterProductTVBH(activity, new OnPostExecuteListener<CCOutput>() {
                    @Override
                    public void onPostExecute(CCOutput result, String errorCode, String description) {
                        CommonActivity.toast(activity, "Tư vấn thành công");
                        viewHolder.btAdvisory.setVisibility(View.INVISIBLE);
                    }
                }, null).execute(productBean.getCode(), productBean.getType(), isdn, prepaid, "false");
            }
        };

        viewHolder.btAdvisory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonActivity.createDialog(activity, "Bạn có đồng ý tư vấn gói "
                                + productBean.getCode() + " cho thuê bao "
                                + isdn + " không?",
                        context.getString(R.string.app_name),
                        context.getString(R.string.cancel),
                        context.getString(R.string.ok),
                        null, advisoryListener).show();
            }
        });

        viewHolder.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonActivity.createDialog(activity, "Bạn có đồng ý đăng ký gói "
                                + productBean.getCode() + " cho thuê bao "
                                + isdn + " không?",
                        context.getString(R.string.app_name),
                        context.getString(R.string.cancel),
                        context.getString(R.string.ok),
                        null, registerListener).show();
            }
        });

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productBeanArrayList.clear();
        if (charText.length() == 0) {
            productBeanArrayList.addAll(productBeanArrayListBackup);
        } else {
            for (ProductBean productBean : productBeanArrayListBackup) {
                if (productBean.getCode().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    productBeanArrayList.add(productBean);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void removeItem(ProductBean productBean) {
        this.productBeanArrayList.remove(productBean);
        this.productBeanArrayListBackup.remove(productBean);
        notifyDataSetChanged();
//        if (this.listHashMapThree.get(type).size() == 0) {
//            this.listHashMapThree.remove(type);
//            this.headers.remove(type);
//        }
//        if (this.listHashMapFull.get(type).size() == 0) {
//            this.listHashMapFull.remove(type);
//        }
    }
}