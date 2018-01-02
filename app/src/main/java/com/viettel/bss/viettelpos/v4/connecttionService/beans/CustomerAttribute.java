package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;

public class CustomerAttribute implements Serializable{
	// map with
//	protected String customerAttributeName;
//  protected String customerAttributeBirthDate;
//  protected Long customerAttributeIdType;
//  protected String customerAttributeIdNo;
//  protected String customerAttributeIssuePlace;
//  protected String customerAttributeIssueDate;

	private String birthDate;
	private String custId;
	private String id;
	private String idNo;
	private String idType;
	private String issueDate;
	private String issuePlace;
	private String name;
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getIssuePlace() {
		return issuePlace;
	}
	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return String.format(
				"{\"CustomerAttribute\":{\"birthDate\":\"%s\", \"custId\":\"%s\", \"id\":\"%s\", \"idNo\":\"%s\", \"idType\":\"%s\", \"issueDate\":\"%s\", \"issuePlace\":\"%s\", \"name\":\"%s\"}}",
				birthDate, custId, id, idNo, idType, issueDate, issuePlace, name);
	}
}
