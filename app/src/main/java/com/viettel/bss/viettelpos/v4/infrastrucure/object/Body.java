package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

@Namespace(prefix = "S", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class Body {
	@Element
	private SurveyOnlineResponse surveyOnlineResponse;

	public SurveyOnlineResponse getSurveyOnlineResponse() {
		return surveyOnlineResponse;
	}

	public void setSurveyOnlineResponse(
			SurveyOnlineResponse surveyOnlineResponse) {
		this.surveyOnlineResponse = surveyOnlineResponse;
	}

}
