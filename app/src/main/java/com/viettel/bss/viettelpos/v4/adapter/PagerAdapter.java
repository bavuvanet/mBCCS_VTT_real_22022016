package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.cc.fragment.CCFragment;
import com.viettel.bss.viettelpos.v4.charge.fragment.ChargeFragment;
import com.viettel.bss.viettelpos.v4.charge.fragment.PayChargeFragment;
import com.viettel.bss.viettelpos.v4.charge.fragment.VerifyCusFragment;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.contract.fragment.ContractFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.HomeServiceManagerFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.MobileDevFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.MobileServiceManagerFragment;
import com.viettel.bss.viettelpos.v4.guide.fragment.GuideConnectFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.fragment.InfrasFragment;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.OmniFragment;
import com.viettel.bss.viettelpos.v4.report.fragment.ReportFragment;
import com.viettel.bss.viettelpos.v4.sale.fragment.InventoryFragment;
import com.viettel.bss.viettelpos.v4.sale.fragment.SaleFragment;
import com.viettel.bss.viettelpos.v4.sale.fragment.UtilsFragment;
import com.viettel.bss.viettelpos.v4.work.fragment.JobFragment;

import java.util.List;

/**
 * Created by Toancx on 2/7/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<GridMenu> lstData;
    private final Context mContext;
    private SparseArray<String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public PagerAdapter(FragmentManager fragmentManager, Context mContext, List<GridMenu> lstData) {
        super(fragmentManager);
        this.mFragmentManager = mFragmentManager;
        this.lstData = lstData;
        this.mContext = mContext;
        mFragmentTags = new SparseArray<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if(obj instanceof Fragment){
            Fragment fragment = (Fragment) obj;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    @Override
    public Fragment getItem(int position) {
        return initFragment(position);
    }

    @Override
    public int getCount() {
        return lstData == null ? 0 : lstData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lstData.get(position).getTitle();
    }

    private Fragment initFragment(int position) {
        if (CommonActivity.isNullOrEmptyArray(lstData)) {
            return null;
        }

        GridMenu gridMenu = lstData.get(position);
        if (mContext.getString(R.string.sale).equalsIgnoreCase(gridMenu.getTitle())) {
            return SaleFragment.newInstance();
        } else if (mContext.getString(R.string.stock).equalsIgnoreCase(gridMenu.getTitle())) {
            return InventoryFragment.newInstance();
        } else if (mContext.getString(R.string.txt_tien_ich).equalsIgnoreCase(gridMenu.getTitle())) {
            return UtilsFragment.newInstance();
        } else if (mContext.getString(R.string.txt_dev_mobile).equalsIgnoreCase(gridMenu.getTitle())) {
            return MobileDevFragment.newInstance();
        } else if (mContext.getString(R.string.txt_mobile_service_manager).equalsIgnoreCase(gridMenu.getTitle())) {
            return MobileServiceManagerFragment.newInstance();
        } else if (mContext.getString(R.string.txt_homephone_service_manager).equalsIgnoreCase(gridMenu.getTitle())) {
            return HomeServiceManagerFragment.newInstance();
        } else if (mContext.getString(R.string.txt_thanh_toan_cuoc).equalsIgnoreCase(gridMenu.getTitle())) {
            return PayChargeFragment.newInstance();
        } else if (mContext.getString(R.string.chargeable).equalsIgnoreCase(gridMenu.getTitle())) {
            return ChargeFragment.newInstance();
        } else if (mContext.getString(R.string.txt_ver_cus_info).equalsIgnoreCase(gridMenu.getTitle())) {
            return VerifyCusFragment.newInstance();
        } else if (mContext.getString(R.string.customer2).equalsIgnoreCase(gridMenu.getTitle())) {
            return InfrasFragment.newInstance();
        } else if (mContext.getString(R.string.job).equalsIgnoreCase(gridMenu.getTitle())) {
            return JobFragment.newInstance();
        } else if (mContext.getString(R.string.report).equalsIgnoreCase(gridMenu.getTitle())) {
            return ReportFragment.newInstance();
        } else if (mContext.getString(R.string.cc_mamagement).equalsIgnoreCase(gridMenu.getTitle())) {
            return CCFragment.newInstance();
        } else if (mContext.getString(R.string.title_guide_connect_fix).equalsIgnoreCase(gridMenu.getTitle())) {
            return GuideConnectFragment.newInstance();
        } else if (mContext.getString(R.string.quanlihopdong).equalsIgnoreCase(gridMenu.getTitle())) {
            return ContractFragment.newInstance();
        }else if(mContext.getString(R.string.omni_chanel).equalsIgnoreCase(gridMenu.getTitle())){
            return OmniFragment.newInstance();
        }
        return null;
    }
}
