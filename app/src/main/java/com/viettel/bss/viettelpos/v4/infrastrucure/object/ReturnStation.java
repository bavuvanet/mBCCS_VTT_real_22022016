package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class ReturnStation {
	@ElementList(inline = true)
	private ArrayList<Station> list;
	@Element
	private String errorCode;
	@Element
	private String token;

	public ArrayList<Station> getLstResultSurveyOnlineForm() {
		return list;
	}

	public void setLstResultSurveyOnlineForm(
			ArrayList<Station> lstResultSurveyOnlineForm) {
		this.list = lstResultSurveyOnlineForm;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
