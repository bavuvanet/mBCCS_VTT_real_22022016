package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

public class ChannelContentCareOJ implements Serializable {
	private int content_care_id;
	private String contentCare;
	private String contentDate;
	private Boolean checked = false;
	public  ChannelContentCareOJ(int content_id, String contentCare, String date) {
		this.content_care_id = content_id;
		this.contentCare = contentCare;
		// TODO Auto-generated method stub
		this.contentDate = date;

	}
	public String getContentDate(){
		return contentDate;
	}
	public void setContentDate(String date){
		this.contentDate = date;
	}
	
	public int getContent_care_id() {
		return content_care_id;
	}

	public void setContent_care_id(int content_care_id) {
		this.content_care_id = content_care_id;
	}

	public String getContentCare() {
		return contentCare;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setContentCare(String contentCare) {
		this.contentCare = contentCare;
	}


}
