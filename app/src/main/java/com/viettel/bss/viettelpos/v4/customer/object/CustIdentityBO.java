package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CustIdentityBO", strict = false)
public class CustIdentityBO {

	@Element(name = "customer", required = false)
	private CustomerBO customer;
	@Element(name = "createDatetime", required = false)
	private String createDatetime;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "custId", required = false)
	private Long custId;
	@Element(name = "custIdentityId", required = false)
	private Long custIdentityId;
	@Element(name = "idExpireDate", required = false)
	private String idExpireDate;
	@Element(name = "idIssueDate", required = false)
	private String idIssueDate;
	@Element(name = "idIssuePlace", required = false)
	private String idIssuePlace;
	@Element(name = "idNo", required = false)
	private String idNo;
	@Element(name = "idType", required = false)
	private String idType;
	@Element(name = "idTypeName", required = false)
	private String idTypeName;
	@Element(name = "identityTypeName", required = false)
	private String identityTypeName;
	@Element(name = "maxLength", required = false)
	private Long maxLength;
	@Element(name = "minLength", required = false)
	private Long minLength;
	@Element(name = "minQueryLength", required = false)
	private int minQueryLength;
	@Element(name = "numFound", required = false)
	private String numFound;
	@Element(name = "requiredMessage", required = false)
	private String requiredMessage;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "tablePk", required = false)
	private String tablePk;
	@Element(name = "updateDatetime", required = false)
	private String updateDatetime;
	@Element(name = "updateUser", required = false)
	private String updateUser;
	@Element(name = "validateMessage", required = false)
	private String validateMessage;
	public CustomerBO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerBO customer) {
		this.customer = customer;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getCustIdentityId() {
		return custIdentityId;
	}
	public void setCustIdentityId(Long custIdentityId) {
		this.custIdentityId = custIdentityId;
	}
	public String getIdExpireDate() {
		return idExpireDate;
	}
	public void setIdExpireDate(String idExpireDate) {
		this.idExpireDate = idExpireDate;
	}
	public String getIdIssueDate() {
		return idIssueDate;
	}
	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}
	public String getIdIssuePlace() {
		return idIssuePlace;
	}
	public void setIdIssuePlace(String idIssuePlace) {
		this.idIssuePlace = idIssuePlace;
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
	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}
	public String getIdentityTypeName() {
		return identityTypeName;
	}
	public void setIdentityTypeName(String identityTypeName) {
		this.identityTypeName = identityTypeName;
	}
	public Long getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}
	public Long getMinLength() {
		return minLength;
	}
	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}
	public int getMinQueryLength() {
		return minQueryLength;
	}
	public void setMinQueryLength(int minQueryLength) {
		this.minQueryLength = minQueryLength;
	}
	public String getNumFound() {
		return numFound;
	}
	public void setNumFound(String numFound) {
		this.numFound = numFound;
	}
	public String getRequiredMessage() {
		return requiredMessage;
	}
	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTablePk() {
		return tablePk;
	}
	public void setTablePk(String tablePk) {
		this.tablePk = tablePk;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getValidateMessage() {
		return validateMessage;
	}
	public void setValidateMessage(String validateMessage) {
		this.validateMessage = validateMessage;
	}
	
	
}
