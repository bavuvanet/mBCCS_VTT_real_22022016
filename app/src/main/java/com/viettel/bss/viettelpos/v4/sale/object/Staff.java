package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.io.Serializable;

public class Staff implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long staffId;
	private Long shopId;
	private String staffCode;
	private String name;
	private Long status;
	private Long channelTypeId;
	private Long pointOfSale;
	private Long type;
	
	private String pricePolicy;
	private String isdnAgent;

	private String birthday;

	private String id_no;

	private String id_issue_place;

	private String id_issue_date;

	private String province;

	private String district;

	private String precinct;
	
	private String address;



	private String street_block;
	private String street;
	private String home;

	private boolean isCheck;


	public Staff(String name) {
		this.name = name;
	}

	public Staff() {
	}
	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getStreet_block() {
		return street_block;
	}

	public void setStreet_block(String street_block) {
		this.street_block = street_block;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {

		return birthday;

	}

	public void setBirthday(String birthday) {

		this.birthday = birthday;

	}

	public String getId_no() {

		return id_no;

	}

	public void setId_no(String id_no) {

		this.id_no = id_no;

	}

	public String getId_issue_place() {

		return id_issue_place;

	}

	public void setId_issue_place(String id_issue_place) {

		this.id_issue_place = id_issue_place;

	}

	public String getId_issue_date() {

		return id_issue_date;

	}

	public void setId_issue_date(String id_issue_date) {

		this.id_issue_date = id_issue_date;

	}

	public String getProvince() {

		return province;

	}

	public void setProvince(String province) {

		this.province = province;

	}

	public String getDistrict() {

		return district;

	}

	public void setDistrict(String district) {

		this.district = district;

	}

	public String getPrecinct() {

		return precinct;

	}

	public void setPrecinct(String precinct) {

		this.precinct = precinct;

	}

	public String getIsdnAgent() {
		return isdnAgent;
	}

	public void setIsdnAgent(String isdnAgent) {
		this.isdnAgent = isdnAgent;
	}

	private String x;
	private String y;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getPricePolicy() {
		return pricePolicy;
	}

	public void setPricePolicy(String pricePolicy) {
		this.pricePolicy = pricePolicy;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getChannelTypeId() {
		return channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public Long getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(Long pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return staffCode + "-" + name;
	}
	public boolean isStaff(){
		if(CommonActivity.isNullOrEmpty(channelTypeId)){
			return false;
		}
		Long staffChannel = 14L;
		return staffChannel.compareTo(channelTypeId) ==0;
	}

	//Kiem tra co phai la diem ban hay ko
	public boolean isPOS(){
		if(CommonActivity.isNullOrEmpty(channelTypeId)){
			return false;
		}
		Long channelTypeId = 10L;
		Long pos = 1L;
		return pointOfSale!= null && channelTypeId.compareTo(channelTypeId) ==0 && pos.compareTo(pointOfSale) ==0;
	}

}
