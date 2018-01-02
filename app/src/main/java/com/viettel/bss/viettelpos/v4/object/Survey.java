package com.viettel.bss.viettelpos.v4.object;

import java.util.List;

public class Survey {
	private String id;
	private String surveyName;
	private String description;
	private String required;
	private List<SurveyQuestion> lstQuestion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public List<SurveyQuestion> getLstQuestion() {
		return lstQuestion;
	}

	public void setLstQuestion(List<SurveyQuestion> lstQuestion) {
		this.lstQuestion = lstQuestion;
	}

}
