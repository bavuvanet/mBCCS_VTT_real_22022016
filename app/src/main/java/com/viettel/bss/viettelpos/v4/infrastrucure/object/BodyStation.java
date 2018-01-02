package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

//@Root
@Namespace(prefix = "S", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class BodyStation {
	@Element
	private SurveyBoxByLocationCodeResponse surveyBoxByLocationCodeResponse;

	public SurveyBoxByLocationCodeResponse getSurveyOnlineResponse() {
		return surveyBoxByLocationCodeResponse;
	}

	public void setSurveyOnlineResponse(
			SurveyBoxByLocationCodeResponse surveyOnlineResponse) {
		this.surveyBoxByLocationCodeResponse = surveyOnlineResponse;
	}

}
