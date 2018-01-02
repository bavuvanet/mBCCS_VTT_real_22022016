package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import android.content.Context;
import android.text.Html;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "ReasonPledgeDTO", strict = false)
public class ReasonPledgeDTO implements Serializable {
	@Element(name = "numMoney", required = false)
	private String numMoney;
	@Element(name = "numMonth", required = false)
	private String numMonth;

	public String getNumMoney() {
		return numMoney;
	}

	public void setNumMoney(String numMoney) {
		this.numMoney = numMoney;
	}

	public String getNumMonth() {
		return numMonth;
	}

	public void setNumMonth(String numMonth) {
		this.numMonth = numMonth;
	}

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

	@Override
	public String toString() {
		String des = "";
		if (!CommonActivity.isNullOrEmpty(numMoney) && !CommonActivity.isNullOrEmpty(numMonth)) {

			des = "Số Tiền: " + StringUtils.formatMoney(numMoney + "") + " - "
					+ "Số Tháng: " + numMonth;
		} else {
			des = numMonth;
		}
		return "" + Html.fromHtml(des);
	}

}
