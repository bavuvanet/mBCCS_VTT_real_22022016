package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "BillCycleTypeDTO", strict = false)
public class BillCycleTypeDTO {
	@Element (name = "billCycleFromCharging", required = false)
	private String billCycleFromCharging;

	public String getBillCycleFromCharging() {
		return billCycleFromCharging;
	}

	public void setBillCycleFromCharging(String billCycleFromCharging) {
		this.billCycleFromCharging = billCycleFromCharging;
	}
	
}
