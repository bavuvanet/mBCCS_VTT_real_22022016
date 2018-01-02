package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CustomerBO", strict = false)
public class CustomerBO {
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "actionCode", required = false)
	private String actionCode;
	@Element(name = "birthDate", required = false)
	private String birthDate;
	@Element(name = "busPermitNo", required = false)
	private String busPermitNo;
	@Element(name = "contactBillAddress", required = false)
	private String contactBillAddress;
	@Element(name = "contactEmail", required = false)
	private String contactEmail;
	@Element(name = "contactNumber", required = false)
	private String contactNumber;
	@Element(name = "createDatetime", required = false)
	private String createDatetime;
	@Element(name = "createUser", required = false)
	private String createUser;
	@Element(name = "custId", required = false)
	private Long custId;
	@Element(name = "custType", required = false)
	private String custType;
	@Element(name = "custTypeName", required = false)
	private String custTypeName;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "idNo", required = false)
	private String idNo;
	@Element(name = "identityNo", required = false)
	private String identityNo;
	@Element(name = "identityTypeName", required = false)
	private String identityTypeName;
	@Element(name = "idnoTypeName", required = false)
	private String idnoTypeName;
	@Element(name = "address", required = false)
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBusPermitNo() {
		return busPermitNo;
	}

	public void setBusPermitNo(String busPermitNo) {
		this.busPermitNo = busPermitNo;
	}

	public String getContactBillAddress() {
		return contactBillAddress;
	}

	public void setContactBillAddress(String contactBillAddress) {
		this.contactBillAddress = contactBillAddress;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getIdentityTypeName() {
		return identityTypeName;
	}

	public void setIdentityTypeName(String identityTypeName) {
		this.identityTypeName = identityTypeName;
	}

	public String getIdnoTypeName() {
		return idnoTypeName;
	}

	public void setIdnoTypeName(String idnoTypeName) {
		this.idnoTypeName = idnoTypeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
