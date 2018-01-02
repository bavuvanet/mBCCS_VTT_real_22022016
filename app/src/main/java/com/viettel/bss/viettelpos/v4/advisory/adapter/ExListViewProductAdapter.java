package com.viettel.bss.viettelpos.v4.advisory.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.data.ProductBean;
import com.viettel.bss.viettelpos.v4.advisory.screen.FullProductFragment;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCareLostSub;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListOfferConsultTVBH;
import com.viettel.bss.viettelpos.v4.task.AsynTaskRegisterProductTVBH;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hantt47 on 7/18/2017.
 */

public class ExListViewProductAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private Context context;
    private List<String> headers; // header titles
    private HashMap<String, List<ProductBean>> listHashMapThree;
    private HashMap<String, List<ProductBean>> listHashMapFull;

    private String isdn;
    private String prepaid;

    private SparseBooleanArray expandState = new SparseBooleanArray();
    private String permision;

    public ExListViewProductAdapter(Context context, Activity activity, List<String> listDataHeader,
                                    HashMap<String, List<ProductBean>> listHashMapThree,
                                    HashMap<String, List<ProductBean>> listHashMapFull,
                                    String isdn, String prepaid) {
        this.activity = activity;
        this.context = context;
        this.headers = listDataHeader;
        this.listHashMapThree = listHashMapThree;
        this.listHashMapFull = listHashMapFull;
        this.isdn = isdn;
        this.prepaid = prepaid;

        for (int i = 0; i < headers.size(); i++) {
            expandState.append(i, false);
        }

        SharedPreferences preferences = context.getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        permision = preferences.getString(Define.KEY_MENU_NAME, "");
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listHashMapThree.get(this.headers.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ProductBean productBean = (ProductBean) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.advisory_expand_item_view, null);
        }

        TextView tvCode = (TextView) convertView.findViewById(R.id.tvCode);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        LinearLayout contentLayout = (LinearLayout) convertView.findViewById(R.id.contentLayout);
        final Button btAdvisory = (Button) convertView.findViewById(R.id.btAdvisory);
        final Button btRegister = (Button) convertView.findViewById(R.id.btRegister);

        if(!permision.contains(Constant.VSAMenu.PERMISSION_ADVISORY_CUSTOMER)){
            btAdvisory.setVisibility(View.INVISIBLE);
        }

        if(!permision.contains(Constant.VSAMenu.PERMISSION_REGISTER_CUSTOMER)){
            btRegister.setVisibility(View.INVISIBLE);
        }

        contentLayout.setBackgroundResource(R.color.gray_8);

        btAdvisory.setPaintFlags(btAdvisory.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btRegister.setPaintFlags(btRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvCode.setText("" + productBean.getCode());
        tvDesc.setText("" + productBean.getDesc());

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
                        btAdvisory.setVisibility(View.INVISIBLE);
                    }
                }, null).execute(productBean.getCode(), productBean.getType(), isdn, prepaid, "false");
            }
        };

        btAdvisory.setOnClickListener(new View.OnClickListener() {
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

        btRegister.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listHashMapThree.get(this.headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.advisory_expand_title_view, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.tvHeader);
        ImageView ivGroupIndicator = (ImageView) convertView.findViewById(R.id.ivGroupIndicator);
        ImageButton btPlus = (ImageButton) convertView.findViewById(R.id.btPlus);

        btPlus.setFocusable(false);

        final String prductName = headerTitle;
        final int position = groupPosition;
        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullProductFragment fullProductFragment = new FullProductFragment(
                        prductName, isdn, prepaid, listHashMapFull.get(headers.get(position)));
                ReplaceFragment.replaceFragment(activity, fullProductFragment, false);
            }
        });

        ivGroupIndicator.setSelected(isExpanded);
//        if (isExpanded && !expandState.get(groupPosition)) {
//            animateExpand(ivGroupIndicator);
//            expandState.put(groupPosition, true);
//        } else if(!isExpanded && expandState.get(groupPosition)) {
//            animateCollapse(ivGroupIndicator);
//            expandState.put(groupPosition, false);
//        }

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    private void animateExpand(View v) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", 0, -90);
        anim.setDuration(500);
        anim.start();
    }

    private void animateCollapse(View v) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", -90, 0);
        anim.setDuration(500);
        anim.start();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void removeItem(ProductBean productBean) {
        this.listHashMapThree.get(productBean.getType()).remove(productBean);
        this.listHashMapFull.get(productBean.getType()).remove(productBean);
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