package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

@Namespace(prefix ="ns2", reference = "http://ws.smartphonev2.bss.viettel.com/")
public class SurveyBoxByLocationCodeResponse {

	@Element (name = "return")
	private ReturnStation Return;

	public ReturnStation getReturn() {
		return Return;
	}

	public void setReturn(ReturnStation return1) {
		Return = return1;
	}
	
}
