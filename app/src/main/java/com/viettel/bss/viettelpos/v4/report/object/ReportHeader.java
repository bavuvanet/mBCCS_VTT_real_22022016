package com.viettel.bss.viettelpos.v4.report.object;

import java.util.ArrayList;

public class ReportHeader {
	private String name = "";
	private ArrayList<ReportChanelCareDetail> lisChanelCareDetails = new ArrayList<>();
	private String key_menu = "";

	public ReportHeader() {
	}

    public ReportHeader(String name, String keymenu) {
		this.name = name;
		this.key_menu = keymenu;
	}

	public ReportHeader(String name,
			ArrayList<ReportChanelCareDetail> lisChanelCareDetails) {
		super();
		this.name = name;
		this.lisChanelCareDetails = lisChanelCareDetails;
	}

	public ReportHeader(String name,
			ArrayList<ReportChanelCareDetail> lisChanelCareDetails,
			String keymenu) {
		this.name = name;
		this.lisChanelCareDetails = lisChanelCareDetails;
		this.key_menu = keymenu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ReportChanelCareDetail> getLisChanelCareDetails() {
		return lisChanelCareDetails;
	}

	public void setLisChanelCareDetails(
			ArrayList<ReportChanelCareDetail> lisChanelCareDetails) {
		this.lisChanelCareDetails = lisChanelCareDetails;
	}

}
