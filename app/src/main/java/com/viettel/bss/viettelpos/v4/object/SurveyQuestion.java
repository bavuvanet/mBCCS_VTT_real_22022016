package com.viettel.bss.viettelpos.v4.object;

import java.util.List;

public class SurveyQuestion {
	private String question;
	private String id;
	private String surveyId;
	private String type;
	private List<SurveyAnswer> lstAnswer;
	private String required;
	private String anwser;

	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnwser() {
		return anwser;
	}

	public void setAnwser(String anwser) {
		this.anwser = anwser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SurveyAnswer> getLstAnswer() {
		return lstAnswer;
	}

	public void setLstAnswer(List<SurveyAnswer> lstAnswer) {
		this.lstAnswer = lstAnswer;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

}
