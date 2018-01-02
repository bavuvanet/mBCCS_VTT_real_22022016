package com.viettel.gem.model;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.FullAddressDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.LocationDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StaffDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "customerDTO", strict = false)
public class CustomerDTO implements Serializable{
	@Element (name = "birthDate", required = false)
	private String birthDate;
	@Element (name = "contactBillAddress", required = false)
	private String contactBillAddress;
	@Element (name = "contactEmail", required = false)
	private String contactEmail;
	@Element (name = "contactNumber", required = false)
	private String contactNumber;
	@Element (name = "createDatetime", required = false)
	private String createDatetime;
	@Element (name = "createUser", required = false)
	private String createUser;
	@Element (name = "custAdd", required = false)
	private FullAddressDTO custAdd;

	@Element (name = "custId", required = false)
	private Long custId;
	@Element (name = "custIdentityDTO", required = false)
	private CustIdentityDTO custIdentityDTO;
	@Element (name = "custType", required = false)
	private String custType;
	@Element (name = "custTypeDTO", required = false)
	private CustTypeDTO custTypeDTO;
	@Element (name = "custTypeName", required = false)
	private String custTypeName;
	@Element (name = "identityNo", required = false)
	private String identityNo;
	@Element (name = "identityTypeName", required = false)
	private String identityTypeName;
	@Element (name = "idnoTypeName", required = false)
	private String idnoTypeName;
	@ElementList(name = "listCustContact", entry = "listCustContact", required = false, inline = true)
	private List<CustIdentityDTO> listCustContact;
	@ElementList(name = "listCustIdentity", entry = "listCustIdentity", required = false, inline = true)
	private List<CustIdentityDTO> listCustIdentity;
	@ElementList(name = "listCustIdentityExist", entry = "listCustIdentityExist", required = false, inline = true)
	private List<CustIdentityDTO> listCustIdentityExist;
	@Element (name = "nationality", required = false)
	private String nationality;
	@Element (name = "otherIdentityNo", required = false)
	private String otherIdentityNo;
	@Element (name = "payType", required = false)
	private String payType;
	@Element (name = "referCustId", required = false)
	private Long referCustId;
	@Element (name = "representativeCust", required = false)
	private CustomerDTO representativeCust;
	@Element (name = "representativeCustContract", required = false)
	private CustomerDTO representativeCustContract;
	@Element (name = "sex", required = false)
	private String sex;
	@Element (name = "sexName", required = false)
	private String sexName;
	@Element (name = "staffDTO", required = false)
	private StaffDTO staffDTO;
	@Element (name = "street", required = false)
	private String street;
	@Element (name = "typeCustomer", required = false)
	private String typeCustomer;
	@Element (name = "updateDatetime", required = false)
	private String updateDatetime;
	@Element (name = "updateUser", required = false)
	private String updateUser;
	@Element (name = "vip", required = false)
	private String vip;
	@Element (name = "zoneDistrict", required = false)
	private LocationDTO zoneDistrict;
	@Element (name = "zoneProvice", required = false)
	private LocationDTO zoneProvice;
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "description", required = false)
	private String description;
	@Element (name = "status", required = false)
	private String status;
	@Element (name = "actionCode", required = false)
	private String actionCode;

	@Element (name = "areaCode", required = false)
	private String areaCode;
	@Element (name = "district", required = false)
	private String district;
	@Element (name = "home", required = false)
	private String home;
	@Element (name = "precinct", required = false)
	private String precinct;
	@Element (name = "province", required = false)
	private String province;
	@Element (name = "streetBlock", required = false)
	private String streetBlock;
	@Element (name = "streetBlockName", required = false)
	private String streetBlockName;
	@Element (name = "streetName", required = false)
	private String streetName;
	@Element (name = "address", required = false)
	private String address;
	private Boolean isCVT = false;
	private String isPSenTdO;
	@Element (name = "groupType", required = false)
	private String groupType;
	private List<SubscriberDTO> listSubscriber;

	private boolean isCopy;

	public boolean isCopy() {
		return isCopy;
	}

	public void setCopy(boolean copy) {
		isCopy = copy;
	}

	public List<SubscriberDTO> getListSubscriber() {
		return listSubscriber;
	}
	public void setListSubscriber(List<SubscriberDTO> listSubscriber) {
		this.listSubscriber = listSubscriber;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getPrecinct() {
		return precinct;
	}
	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getStreetBlock() {
		return streetBlock;
	}
	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}
	public String getStreetBlockName() {
		return streetBlockName;
	}
	public void setStreetBlockName(String streetBlockName) {
		this.streetBlockName = streetBlockName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public FullAddressDTO getCustAdd() {
		return custAdd;
	}
	public void setCustAdd(FullAddressDTO custAdd) {
		this.custAdd = custAdd;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public CustIdentityDTO getCustIdentityDTO() {
		return custIdentityDTO;
	}
	public void setCustIdentityDTO(CustIdentityDTO custIdentityDTO) {
		this.custIdentityDTO = custIdentityDTO;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public CustTypeDTO getCustTypeDTO() {
		return custTypeDTO;
	}
	public void setCustTypeDTO(CustTypeDTO custTypeDTO) {
		this.custTypeDTO = custTypeDTO;
	}
	public String getCustTypeName() {
		return custTypeName;
	}
	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
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
	public List<CustIdentityDTO> getListCustContact() {
		return listCustContact;
	}
	public void setListCustContact(List<CustIdentityDTO> listCustContact) {
		this.listCustContact = listCustContact;
	}
	public List<CustIdentityDTO> getListCustIdentity() {
		return listCustIdentity;
	}
	public void setListCustIdentity(List<CustIdentityDTO> listCustIdentity) {
		this.listCustIdentity = listCustIdentity;
	}
	public List<CustIdentityDTO> getListCustIdentityExist() {
		return listCustIdentityExist;
	}
	public void setListCustIdentityExist(List<CustIdentityDTO> listCustIdentityExist) {
		this.listCustIdentityExist = listCustIdentityExist;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getOtherIdentityNo() {
		return otherIdentityNo;
	}
	public void setOtherIdentityNo(String otherIdentityNo) {
		this.otherIdentityNo = otherIdentityNo;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Long getReferCustId() {
		return referCustId;
	}
	public void setReferCustId(Long referCustId) {
		this.referCustId = referCustId;
	}
	public CustomerDTO getRepresentativeCust() {
		return representativeCust;
	}
	public void setRepresentativeCust(CustomerDTO representativeCust) {
		this.representativeCust = representativeCust;
	}
	public CustomerDTO getRepresentativeCustContract() {
		return representativeCustContract;
	}
	public void setRepresentativeCustContract(CustomerDTO representativeCustContract) {
		this.representativeCustContract = representativeCustContract;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public StaffDTO getStaffDTO() {
		return staffDTO;
	}
	public void setStaffDTO(StaffDTO staffDTO) {
		this.staffDTO = staffDTO;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTypeCustomer() {
		return typeCustomer;
	}
	public void setTypeCustomer(String typeCustomer) {
		this.typeCustomer = typeCustomer;
	}


	public String getBirthDate() {

		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
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
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public LocationDTO getZoneDistrict() {
		return zoneDistrict;
	}
	public void setZoneDistrict(LocationDTO zoneDistrict) {
		this.zoneDistrict = zoneDistrict;
	}
	public LocationDTO getZoneProvice() {
		return zoneProvice;
	}
	public void setZoneProvice(LocationDTO zoneProvice) {
		this.zoneProvice = zoneProvice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getIsPSenTdO() {
		return isPSenTdO;
	}

	public void setIsPSenTdO(String isPSenTdO) {
		this.isPSenTdO = isPSenTdO;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Boolean getIsCVT() {
		return isCVT;
	}

	public void setIsCVT(Boolean isCVT) {
		this.isCVT = isCVT;
	}
	public  String toXmlUserInfo(){
		StringBuilder rawData = new StringBuilder();
		rawData.append("<userInfoDTO>");
		rawData.append("<coppy>" + isCopy());
		rawData.append("</coppy>");
		// truong hop khach hang cu
		if(!CommonActivity.isNullOrEmpty(getCustId()) && getCustId() != 0){
			rawData.append("<custId>" + "" + getCustId());
			rawData.append("</custId>");
			rawData.append("<isNewCustomer>" + false);
			rawData.append("</isNewCustomer>");
			rawData.append("<coppy>" + true);
			rawData.append("</coppy>");
			rawData.append("<custType>" + "" + getCustType());
			rawData.append("</custType>");
		}else{
			rawData.append("<custId>" + null);
			rawData.append("</custId>");
			rawData.append("<updateCustIdentity>" + true);
			rawData.append("</updateCustIdentity>");

			rawData.append("<isNewCustomer>" + true);
			rawData.append("</isNewCustomer>");
			rawData.append("<name>" + "" + StringUtils.escapeHtml(CommonActivity.getNormalText(getName())));
			rawData.append("</name>");
			rawData.append("<custType>" + "" + getCustType());
			rawData.append("</custType>");
			rawData.append("<custTypeDTO>");
			rawData.append("<custType>" + "" + getCustType());
			rawData.append("</custType>");
			rawData.append("<groupType>" + "" + getGroupType());
			rawData.append("</groupType>");
			rawData.append("</custTypeDTO>");

			rawData.append("<listCustIdentity>");
			rawData.append("<idNo>" + "" + getListCustIdentity().get(0).getIdNo());
			rawData.append("</idNo>");
			rawData.append("<idType>" + "" + getListCustIdentity().get(0).getIdType());
			rawData.append("</idType>");


			rawData.append("<idIssueDate>" + ""
						+ StringUtils.convertDateToString(getListCustIdentity().get(0).getIdIssueDate()) + "T00:00:00+07:00");
			rawData.append("</idIssueDate>");


			if(!CommonActivity.isNullOrEmpty(getListCustIdentity().get(0).getIdExpireDate())){
					rawData.append("<idExpireDate>" + ""
							+ StringUtils.convertDateToString(getListCustIdentity().get(0).getIdExpireDate()) + "T00:00:00+07:00");
					rawData.append("</idExpireDate>");
			}
			rawData.append("<idIssuePlace>" + "" + getListCustIdentity().get(0).getIdIssuePlace());
			rawData.append("</idIssuePlace>");
			rawData.append("<required>" + true);
			rawData.append("</required>");

			rawData.append("</listCustIdentity>");

			rawData.append("<sex>" + "" + getSex());
			rawData.append("</sex>");
			rawData.append("<birthDate>" + ""
					+ StringUtils.convertDateToString(getBirthDate())
					+ "T00:00:00+07:00");
			rawData.append("</birthDate>");

			if(!CommonActivity.isNullOrEmpty(getProvince()) && !CommonActivity.isNullOrEmpty(getDistrict()) && !CommonActivity.isNullOrEmpty(getPrecinct()) ){
				rawData.append("<custAdd>");

				rawData.append("<province>");
				rawData.append("<code>" + "" + getProvince());
				rawData.append("</code>");
				rawData.append("</province>");

				rawData.append("<district>");
				rawData.append("<code>" + "" + getDistrict());
				rawData.append("</code>");
				rawData.append("</district>");

				rawData.append("<precinct>");
				rawData.append("<code>" + "" + getPrecinct());
				rawData.append("</code>");
				rawData.append("</precinct>");
				if(!CommonActivity.isNullOrEmpty(getStreetBlock())){
					rawData.append("<streetBlock>");
					rawData.append("<code>" + "" + getStreetBlock());
					rawData.append("</code>");
					rawData.append("</streetBlock>");
				}

				if(!CommonActivity.isNullOrEmpty(getAreaCode())){
					rawData.append("<areaCode>" + "" + getAreaCode());
					rawData.append("</areaCode>");
				}
				if(!CommonActivity.isNullOrEmpty(getAddress())){
					rawData.append("<fullAddress>" + "" + getAddress());
					rawData.append("</fullAddress>");
				}


				rawData.append("</custAdd>");

				rawData.append("<province>" + "" + getProvince());
				rawData.append("</province>");
				rawData.append("<district>" + "" + getDistrict());
				rawData.append("</district>");
				rawData.append("<precinct>" + "" + getPrecinct());
				rawData.append("</precinct>");
				rawData.append("<streetBlock>" + "" + getStreetBlock());
				rawData.append("</streetBlock>");

				if (!CommonActivity.isNullOrEmpty(getHome())) {
					rawData.append("<home>" + "" + CommonActivity.getNormalText(getHome()));
					rawData.append("</home>");
				}

				if (!CommonActivity.isNullOrEmpty(getStreetName())) {
					rawData.append("<streetName>" + "" + StringUtils
							.escapeHtml(CommonActivity.getNormalText(getStreetName())));
					rawData.append("</streetName>");
				}
				rawData.append("<areaCode>" + "" + getAreaCode());
				rawData.append("</areaCode>");
				rawData.append("<address>" + "" + CommonActivity.getNormalText(getAddress()));
				rawData.append("</address>");
			}

			if (!CommonActivity.isNullOrEmpty(getNationality())) {
				rawData.append("<nationality>" + "" + getNationality());
				rawData.append("</nationality>");
			}



			if (!CommonActivity.isNullOrEmpty(getDescription())) {
				rawData.append("<description>" + "" + CommonActivity.getNormalText(getDescription()));
				rawData.append("</description>");
			}

			rawData.append("<identityNo>" + "" + getListCustIdentity().get(0).getIdNo());
			rawData.append("</identityNo>");
		}

		rawData.append("</userInfoDTO>");
		return rawData.toString();
	}
}



