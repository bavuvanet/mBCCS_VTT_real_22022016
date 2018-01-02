package com.viettel.bss.viettelpos.v4.charge.wizardpager;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.pager.ChannelCarePager;
import com.viettel.bss.viettelpos.v4.channel.pager.ChannelInfoPager;
import com.viettel.bss.viettelpos.v4.channel.pager.InsertImageToolPager;
import com.viettel.bss.viettelpos.v4.channel.pager.SaleDepositPager;
import com.viettel.bss.viettelpos.v4.channel.pager.SaleSalingPager;
import com.viettel.bss.viettelpos.v4.channel.pager.UpdateImageMonthPager;
import com.viettel.bss.viettelpos.v4.channel.pager.UpdateImageNewPager;
import com.viettel.bss.viettelpos.v4.channel.pager.UpdateImageToolPager;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.AbstractWizardModel;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.BranchPage;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.PageList;

public class CusCareWizardModel extends AbstractWizardModel {

	public CusCareWizardModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PageList onNewRootPageList() {
		// TODO Auto-generated method stub
		return new PageList(new ChannelInfoPager(this, mContext.getString(R.string.info_chanel)),
				new BranchPage(this, mContext.getString(R.string.btn_apply))
						.addBranch(mContext.getString(R.string.capnhathinhanhcongcudungcu),
								new UpdateImageToolPager(this,
										mContext.getString(R.string.capnhathinhanhcongcudungcu)),
								new ChannelCarePager(this, mContext.getResources().getString(R.string.buttonSupport)).setRequired(true),
								new BranchPage(this, mContext.getString(R.string.sale))
									.addBranch(
											mContext.getString(R.string.sale_saling),
											new SaleSalingPager(this,
													mContext.getString(R.string.sale_saling)))
									.addBranch(mContext.getString(R.string.sale_deposit),
											new SaleDepositPager(this,
													mContext.getString(R.string.sale_deposit))))
						.addBranch(mContext.getString(R.string.capnhathinhanhmoi), 
								new UpdateImageNewPager(this,
										mContext.getString(R.string.capnhathinhanhmoi))
												.setRequired(true),
								new ChannelCarePager(this, mContext.getResources().getString(R.string.buttonSupport)).setRequired(true),
								new BranchPage(this, mContext.getString(R.string.sale))
									.addBranch(
											mContext.getString(R.string.sale_saling),
											new SaleSalingPager(this,
													mContext.getString(R.string.sale_saling)))
									.addBranch(mContext.getString(R.string.sale_deposit),
											new SaleDepositPager(this,
													mContext.getString(R.string.sale_deposit))))
						.addBranch(mContext.getString(R.string.capnhathinhanhhangthang), 
								new UpdateImageMonthPager(this,
										mContext.getString(R.string.capnhathinhanhhangthang))
												.setRequired(true),
								new ChannelCarePager(this, mContext.getString(R.string.buttonSupport)).setRequired(true),
								new BranchPage(this, mContext.getString(R.string.sale))
									.addBranch(
											mContext.getString(R.string.sale_saling),
											new SaleSalingPager(this,
													mContext.getString(R.string.sale_saling)))
									.addBranch(mContext.getString(R.string.sale_deposit),
											new SaleDepositPager(this,
													mContext.getString(R.string.sale_deposit))))
						.addBranch(mContext.getString(R.string.themmoiccdc), 
								new InsertImageToolPager(this,
										mContext.getResources().getString(R.string.themmoiccdc))
												.setRequired(true),
								new ChannelCarePager(this, mContext.getResources().getString(R.string.buttonSupport)).setRequired(true),
								new BranchPage(this, mContext.getString(R.string.sale))
									.addBranch(
											mContext.getString(R.string.sale_saling),
											new SaleSalingPager(this,
													mContext.getString(R.string.sale_saling)))
									.addBranch(mContext.getString(R.string.sale_deposit),
											new SaleDepositPager(this,
													mContext.getString(R.string.sale_deposit))))
						.addBranch(mContext.getString(R.string.buttonSupport), 
								new ChannelCarePager(this, mContext.getString(R.string.buttonSupport)).setRequired(true),
								new BranchPage(this, mContext.getString(R.string.sale))
									.addBranch(
											mContext.getString(R.string.sale_saling),
											new SaleSalingPager(this,
													mContext.getResources().getString(R.string.sale_saling)))
									.addBranch(mContext.getString(R.string.sale_deposit),
											new SaleDepositPager(this,
													mContext.getResources().getString(R.string.sale_deposit))))

						.setRequired(true));
	}

}
