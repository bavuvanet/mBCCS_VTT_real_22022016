package com.viettel.bss.viettelpos.v4.sale.object;

import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

@Root(name = "SubDepositDTO", strict = false)
public class SubDepositDTO {
	@Element(name = "deposit", required = false)
	private Long deposit;
	@Element(name = "numOfMonth", required = false)
	private Long numOfMonth;
	@Element(name = "createDatetime", required = false)
	private String createDatetime;
	@Element(name = "status", required = false)
	private String status;
	private Context context;
	@Element(name = "subDepositId", required = false)
	private String subDepositId;
	@Element(name = "payMethod", required = false)
	private String payMethod;
	@Element(name = "payMethodName", required = false)
	private String payMethodName;

	@Element(name = "changeDrawMethod", required = false)
	private boolean changeDrawMethod;

	public boolean isChangeDrawMethod() {
		return changeDrawMethod;
	}

	public void setChangeDrawMethod(boolean changeDrawMethod) {
		this.changeDrawMethod = changeDrawMethod;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}



	public String getPayMethodName() {
		return payMethodName;
	}

	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public String getSubDepositId() {
		return subDepositId;
	}

	public void setSubDepositId(String subDepositId) {
		this.subDepositId = subDepositId;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public Long getNumOfMonth() {
		return numOfMonth;
	}

	public void setNumOfMonth(Long numOfMonth) {
		this.numOfMonth = numOfMonth;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Date time = DateTimeUtils.convertDateFromSoap(getCreateDatetime());
		String strTime = context.getString(R.string.time_tick, DateTimeUtils
				.convertDateTimeToString(time, "dd/MM/yyyy HH:mm:ss"));
		String strDay = context.getString(R.string.day_tick, getNumOfMonth());
		String strMoney = context.getString(R.string.money_tick,
				StringUtils.formatMoney(getDeposit() + ""));
		String result = strTime;
		String methodDeposit = context.getString(R.string.methoddeposit,getPayMethodName());
		if (!CommonActivity.isNullOrEmpty(getNumOfMonth())) {
			result = result + "\n" + strDay;
		}
		if (!CommonActivity.isNullOrEmpty(getDeposit())) {
			result = result + "\n" + strMoney;
		}

		return strTime + "\n" + strDay + "\n" + strMoney + "\n" + methodDeposit;
	}
}
