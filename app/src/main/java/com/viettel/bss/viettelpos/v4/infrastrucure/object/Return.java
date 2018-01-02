package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Return {
	@ElementList(inline = true)
	private ArrayList<SurveyOnline> list;
	@Element
	private String errorCode;

	public ArrayList<SurveyOnline> getLstResultSurveyOnlineForm() {
		return list;
	}

	public void setLstResultSurveyOnlineForm(
			ArrayList<SurveyOnline> lstResultSurveyOnlineForm) {
		this.list = lstResultSurveyOnlineForm;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
