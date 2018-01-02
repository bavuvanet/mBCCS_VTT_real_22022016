package com.viettel.bss.viettelpos.v4.cc.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "SummaryBo", strict = false)
public class SummaryBo {

	@Element(name = "remain", required = false)
	private String remain;
	@Element(name = "remainToday", required = false)
	private String remainToday;
	@Element(name = "totalToday", required = false)
	private String totalToday;
	@Element(name = "total", required = false)
	private String total;

	public String getRemain() {
		return remain;
	}

	public void setRemain(String remain) {
		this.remain = remain;
	}

	public String getRemainToday() {
		return remainToday;
	}

	public void setRemainToday(String remainToday) {
		this.remainToday = remainToday;
	}

	public String getTotalToday() {
		return totalToday;
	}

	public void setTotalToday(String totalToday) {
		this.totalToday = totalToday;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
