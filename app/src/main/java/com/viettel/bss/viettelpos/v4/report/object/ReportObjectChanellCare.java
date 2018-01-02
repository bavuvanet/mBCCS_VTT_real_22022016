package com.viettel.bss.viettelpos.v4.report.object;

import java.util.ArrayList;

public class ReportObjectChanellCare {

	private long visit1 = 0L;
	private long visit2 = 0L;
	private long visit3 = 0L;
	private long visit4 = 0L;
	
	private ArrayList<ReportChanelCareDetail> lisChanelCareDetails = new ArrayList<>();
	
	public ArrayList<ReportChanelCareDetail> getLisChanelCareDetails() {
		return lisChanelCareDetails;
	}
	public void setLisChanelCareDetails(
			ArrayList<ReportChanelCareDetail> lisChanelCareDetails) {
		this.lisChanelCareDetails = lisChanelCareDetails;
	}
	public long getVisit1() {
		return visit1;
	}
	public void setVisit1(long visit1) {
		this.visit1 = visit1;
	}
	public long getVisit2() {
		return visit2;
	}
	public void setVisit2(long visit2) {
		this.visit2 = visit2;
	}
	public long getVisit3() {
		return visit3;
	}
	public void setVisit3(long visit3) {
		this.visit3 = visit3;
	}
	public long getVisit4() {
		return visit4;
	}
	public void setVisit4(long visit4) {
		this.visit4 = visit4;
	}

	
}
