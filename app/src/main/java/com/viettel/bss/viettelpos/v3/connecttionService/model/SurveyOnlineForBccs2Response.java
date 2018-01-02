package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "surveyOnlineForBccs2Response", strict = false)
public class SurveyOnlineForBccs2Response {

	@ElementList(name = "return", entry = "return", required = false, inline = true)
	    private ArrayList<ResultSurveyOnlineForBccs2Form> _return;

	public ArrayList<ResultSurveyOnlineForBccs2Form> get_return() {
		return _return;
	}

	public void set_return(ArrayList<ResultSurveyOnlineForBccs2Form> _return) {
		this._return = _return;
	}
	
	
	
}
