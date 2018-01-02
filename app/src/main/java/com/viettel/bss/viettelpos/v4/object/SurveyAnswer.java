package com.viettel.bss.viettelpos.v4.object;

public class SurveyAnswer {
	private String id;
	private String questionId;
	private String content;
	private Boolean isChoose = false;
	
	public Boolean getIsChoose() {
		return isChoose;
	}

	public void setIsChoose(Boolean isChoose) {
		this.isChoose = isChoose;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
