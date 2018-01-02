package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.content.Context;

@Root(name = "ReasonPledgeDTO", strict = false)
public class ReasonPledgeDTO implements Serializable{
	@Element(name = "numMoney", required = false)
	private Long numMoney;
	@Element(name = "numMonth", required = false)
	private Long numMonth;

	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Long getNumMoney() {
		return numMoney;
	}

	public void setNumMoney(Long numMoney) {
		this.numMoney = numMoney;
	}

	public Long getNumMonth() {
		return numMonth;
	}

	public void setNumMonth(Long numMonth) {
		this.numMonth = numMonth;
	}

	@Override
	public String toString() {
		if (numMoney == null && numMonth == null) {
			return context.getString(R.string.not_deposit);
		} else {
			return context.getString(R.string.deposit_info, numMonth,
					StringUtils.formatMoney(numMoney + ""));
		}

	}
}
