package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

@Namespace(prefix ="ns2", reference = "http://ws.smartphonev2.bss.viettel.com/")
public class SurveyOnlineResponse {

	@Element (name = "return")
	private Return Return;

	public Return getReturn() {
		return Return;
	}

	public void setReturn(Return return1) {
		Return = return1;
	}
	
}
