package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class ReportCusCareBean {
	@Element(name = "remainSub", required = false)
	private Long remainSub;
	@Element(name = "totalDay", required = false)
	private Long totalDay;
	@Element(name = "totalMonth", required = false)
	private Long totalMonth;
	@Element(name = "totalSub", required = false)
	private Long totalSub;
	@Element(name = "cusApp", required = false)
	private Long cusApp;
	@Element(name = "totalSubOtherViettelSim", required = false)
	private Long totalSubOtherViettelSim = 0L;
	@Element(name = "totalDayOtherViettelSim", required = false)
	private Long totalDayOtherViettelSim = 0L;
	@Element(name = "totalMonthOtherViettelSim", required = false)
	private Long totalMonthOtherViettelSim = 0L;
	@Element(name = "remainSubOtherViettelSim", required = false)
	private Long remainSubOtherViettelSim = 0L;
	@Element(name = "totalSubOtherViettelVL", required = false)
	private Long totalSubOtherViettelVL;
	@Element(name = "totalSubViettelVL", required = false)
	private Long totalSubViettelVL;

	public Long getRemainSub() {
		return remainSub;
	}

	public void setRemainSub(Long remainSub) {
		this.remainSub = remainSub;
	}

	public Long getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(Long totalDay) {
		this.totalDay = totalDay;
	}

	public Long getTotalMonth() {
		return totalMonth;
	}

	public void setTotalMonth(Long totalMonth) {
		this.totalMonth = totalMonth;
	}

	public Long getTotalSub() {
		return totalSub;
	}

	public void setTotalSub(Long totalSub) {
		this.totalSub = totalSub;
	}

	public Long getCusApp() {
		return cusApp;
	}

	public void setCusApp(Long cusApp) {
		this.cusApp = cusApp;
	}

	public Long getTotalSubOtherViettelSim() {
		return totalSubOtherViettelSim;
	}

	public void setTotalSubOtherViettelSim(Long totalSubOtherViettelSim) {
		this.totalSubOtherViettelSim = totalSubOtherViettelSim;
	}

	public Long getTotalDayOtherViettelSim() {
		return totalDayOtherViettelSim;
	}

	public void setTotalDayOtherViettelSim(Long totalDayOtherViettelSim) {
		this.totalDayOtherViettelSim = totalDayOtherViettelSim;
	}

	public Long getTotalMonthOtherViettelSim() {
		return totalMonthOtherViettelSim;
	}

	public void setTotalMonthOtherViettelSim(Long totalMonthOtherViettelSim) {
		this.totalMonthOtherViettelSim = totalMonthOtherViettelSim;
	}

	public Long getRemainSubOtherViettelSim() {
		return remainSubOtherViettelSim;
	}

	public void setRemainSubOtherViettelSim(Long remainSubOtherViettelSim) {
		this.remainSubOtherViettelSim = remainSubOtherViettelSim;
	}

	public Long getTotalSubOtherViettelVL() {
		return totalSubOtherViettelVL;
	}

	public void setTotalSubOtherViettelVL(Long totalSubOtherViettelVL) {
		this.totalSubOtherViettelVL = totalSubOtherViettelVL;
	}

	public Long getTotalSubViettelVL() {
		return totalSubViettelVL;
	}

	public void setTotalSubViettelVL(Long totalSubViettelVL) {
		this.totalSubViettelVL = totalSubViettelVL;
	}
	
	

}